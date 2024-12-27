package com.samsung.android.server.continuity.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.SemSystemProperties;
import android.os.UserHandle;

import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import com.samsung.android.server.continuity.sem.SemWrapper;

import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Utils {
    public static final boolean DEBUG;
    public static int mIsHighPowerConsumptionChipset;
    public static int mIsWatch;

    static {
        UserHandle userHandle = SemWrapper.SEM_ALL;
        DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);
        mIsHighPowerConsumptionChipset = -1;
        mIsWatch = -1;
    }

    public static void clearHighPowerConsumptionChipset() {
        mIsHighPowerConsumptionChipset = -1;
    }

    public static boolean isHighPowerConsumptionChipset() {
        if (mIsHighPowerConsumptionChipset == -1) {
            UserHandle userHandle = SemWrapper.SEM_ALL;
            String str = SemSystemProperties.get("vendor.bluetooth_fw_ver");
            if (str != null) {
                str = str.toUpperCase(Locale.ENGLISH);
            }
            String substring =
                    (str == null || str.length() <= 5 || !str.startsWith("UTC"))
                            ? null
                            : str.substring(4);
            if (substring == null
                    || !(substring.startsWith("LEMAN")
                            || substring.startsWith("LASSEN")
                            || substring.startsWith("NEUS"))) {
                mIsHighPowerConsumptionChipset = 0;
            } else {
                mIsHighPowerConsumptionChipset = 1;
            }
        }
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("isHighPowerConsumptionChipset : "),
                mIsHighPowerConsumptionChipset,
                "[MCF_DS_SYS]_Utils");
        return mIsHighPowerConsumptionChipset == 1;
    }

    public static boolean isPackageInstalled(Context context, String str) {
        Iterator<ApplicationInfo> it =
                context.getPackageManager().getInstalledApplications(128).iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWatch() {
        if (mIsWatch == -1) {
            UserHandle userHandle = SemWrapper.SEM_ALL;
            String str = SemSystemProperties.get("ro.build.characteristics");
            if (str == null || !str.contains("watch")) {
                mIsWatch = 0;
            } else {
                mIsWatch = 1;
            }
        }
        return mIsWatch == 1;
    }

    public static String secureMac(String str) {
        if (str == null) {
            return "";
        }
        if (DEBUG) {
            return str;
        }
        if (!(str.isEmpty()
                ? false
                : Pattern.compile("^([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])$")
                        .matcher(str)
                        .find())) {
            return str;
        }
        return "($m)" + str.substring(9, 14);
    }

    public static String secureName(String str) {
        if (str == null) {
            return "";
        }
        if (DEBUG) {
            return str;
        }
        int length = str.length();
        if (length > 10) {
            return str.charAt(0) + "*" + str.charAt(3) + "*" + str.substring(length - 3, length);
        }
        if (length > 7) {
            return str.charAt(0) + "*" + str.charAt(3) + "*" + str.substring(length - 2, length);
        }
        if (length > 5) {
            return str.charAt(0) + "*" + str.substring(length - 2, length);
        }
        if (length <= 3) {
            return str;
        }
        return str.charAt(0) + "*";
    }
}
