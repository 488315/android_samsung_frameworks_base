package com.android.server.inputmethod;

import android.R;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import android.view.textservice.SpellCheckerInfo;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import com.android.server.textservices.TextServicesManagerInternal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

public abstract class InputMethodUtils {
    public static String concatEnabledImeIds(String str, String... strArr) {
        ArraySet arraySet = new ArraySet();
        StringJoiner stringJoiner = new StringJoiner(Character.toString(':'));
        if (!TextUtils.isEmpty(str)) {
            splitEnabledImeStr(str, new InputMethodUtils$$ExternalSyntheticLambda0(arraySet));
            stringJoiner.add(str);
        }
        for (String str2 : strArr) {
            if (!arraySet.contains(str2)) {
                stringJoiner.add(str2);
                arraySet.add(str2);
            }
        }
        return stringJoiner.toString();
    }

    public static int[] resolveUserId(int i, int i2, PrintWriter printWriter) {
        UserManagerInternal userManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        if (i == -1) {
            return userManagerInternal.getUserIds();
        }
        if (i == -2) {
            i = i2;
        } else {
            if (i < 0) {
                if (printWriter != null) {
                    printWriter.print("Pseudo user ID ");
                    printWriter.print(i);
                    printWriter.println(" is not supported.");
                }
                return new int[0];
            }
            if (!userManagerInternal.exists(i)) {
                if (printWriter != null) {
                    printWriter.print("User #");
                    printWriter.print(i);
                    printWriter.println(" does not exit.");
                }
                return new int[0];
            }
        }
        return new int[] {i};
    }

    public static void setNonSelectedSystemImesDisabledUntilUsed(
            PackageManager packageManager, List list) {
        String[] stringArray =
                Resources.getSystem().getStringArray(R.array.config_tether_wifi_regexs);
        if (stringArray == null || stringArray.length == 0) {
            return;
        }
        TextServicesManagerInternal textServicesManagerInternal =
                (TextServicesManagerInternal)
                        LocalServices.getService(TextServicesManagerInternal.class);
        if (textServicesManagerInternal == null) {
            textServicesManagerInternal = TextServicesManagerInternal.NOP;
        }
        SpellCheckerInfo currentSpellCheckerForUser =
                textServicesManagerInternal.getCurrentSpellCheckerForUser(
                        packageManager.getUserId());
        for (String str : stringArray) {
            int i = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) list;
                if (i < arrayList.size()) {
                    if (str.equals(((InputMethodInfo) arrayList.get(i)).getPackageName())) {
                        break;
                    } else {
                        i++;
                    }
                } else if (currentSpellCheckerForUser == null
                        || !str.equals(currentSpellCheckerForUser.getPackageName())) {
                    try {
                        ApplicationInfo applicationInfo =
                                packageManager.getApplicationInfo(
                                        str, PackageManager.ApplicationInfoFlags.of(32768L));
                        if (applicationInfo != null && (applicationInfo.flags & 1) != 0) {
                            try {
                                int applicationEnabledSetting =
                                        packageManager.getApplicationEnabledSetting(str);
                                if (applicationEnabledSetting == 0
                                        || applicationEnabledSetting == 1) {
                                    try {
                                        packageManager.setApplicationEnabledSetting(str, 4, 0);
                                    } catch (IllegalArgumentException e) {
                                        StringBuilder m =
                                                DumpUtils$$ExternalSyntheticOutline0.m(
                                                        "setApplicationEnabledSetting failed."
                                                            + " packageName=",
                                                        str,
                                                        " userId=");
                                        m.append(packageManager.getUserId());
                                        Slog.w("InputMethodUtils", m.toString(), e);
                                    }
                                }
                            } catch (IllegalArgumentException e2) {
                                StringBuilder m2 =
                                        DumpUtils$$ExternalSyntheticOutline0.m(
                                                "getApplicationEnabledSetting failed. packageName=",
                                                str,
                                                " userId=");
                                m2.append(packageManager.getUserId());
                                Slog.w("InputMethodUtils", m2.toString(), e2);
                            }
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
        }
    }

    public static void splitEnabledImeStr(String str, Consumer consumer) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        TextUtils.SimpleStringSplitter simpleStringSplitter2 =
                new TextUtils.SimpleStringSplitter(';');
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            simpleStringSplitter2.setString(simpleStringSplitter.next());
            if (simpleStringSplitter2.hasNext()) {
                consumer.accept(simpleStringSplitter2.next());
            }
        }
    }
}
