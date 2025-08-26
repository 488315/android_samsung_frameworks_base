package com.samsung.android.service.ProtectedATCommand;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Slog;
import com.samsung.android.service.ProtectedATCommand.list.ATCommands;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes6.dex */
public class PACMClassifier {
    public static final int NOK = 0;
    public static final int OK = 1;
    protected static final String TAG = "PACMClassifier";

    private static boolean checkNullParameter(Object... objArr) {
        int i = 1;
        boolean z = false;
        for (Object obj : objArr) {
            if (obj == null) {
                Slog.e(TAG, NavigationBarInflaterView.SIZE_MOD_START + Thread.currentThread().getStackTrace()[2].getMethodName() + "] Parameter(" + i + ") is null.");
                z = true;
            }
            i++;
        }
        return z;
    }

    private static ATCommands findATCommands(LinkedHashMap<String, LinkedHashSet<ATCommands>> linkedHashMap, String str, ATCommands aTCommands) {
        if (checkNullParameter(linkedHashMap, str, aTCommands)) {
            return null;
        }
        Iterator<ATCommands> it = linkedHashMap.get(str).iterator();
        while (it.hasNext()) {
            ATCommands next = it.next();
            if (aTCommands.equals(next)) {
                return next;
            }
        }
        Slog.i(TAG, "findATCommands Failed to find command.");
        return null;
    }

    private static String getName(String str, String str2) {
        if (str == null) {
            Slog.e(TAG, "getName cmd is null.");
            return null;
        }
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(str);
            stringTokenizer.nextToken(str2);
            if (stringTokenizer.hasMoreTokens()) {
                String str3 = stringTokenizer.nextToken().split("=")[0];
                if (str3 != null) {
                    return str3;
                }
            }
        } catch (Exception e) {
            Slog.e(TAG, "getName error occured.");
            e.printStackTrace();
        }
        return null;
    }

    public static ATCommands getCommand(LinkedHashMap<String, LinkedHashSet<ATCommands>> linkedHashMap, String str) {
        String name;
        Slog.i(TAG, "getCommand() is called.");
        if (checkNullParameter(linkedHashMap, str)) {
            return new ATCommands();
        }
        try {
            String[] strArr = {"+", "$", "^", "#"};
            int i = 0;
            while (true) {
                if (i >= 4) {
                    name = str;
                    break;
                }
                String str2 = strArr[i];
                if (str.indexOf("AT" + str2) == 0) {
                    name = getName(str, str2);
                    break;
                }
                i++;
            }
            if (name == null) {
                Slog.e(TAG, "Failed to get cmd name(" + str + NavigationBarInflaterView.KEY_CODE_END);
                return new ATCommands();
            }
            LinkedHashSet<ATCommands> linkedHashSet = linkedHashMap.get(name);
            if (linkedHashSet == null) {
                Slog.i(TAG, "This cmd(" + name + ") is not registered");
                return new ATCommands();
            }
            ATCommands aTCommands = new ATCommands(name, str.getBytes());
            if (linkedHashSet.contains(aTCommands)) {
                ATCommands aTCommandsFindATCommands = findATCommands(linkedHashMap, name, aTCommands);
                if (aTCommandsFindATCommands != null) {
                    return aTCommandsFindATCommands;
                }
                Slog.e(TAG, "Failed to find AT Commands");
                return new ATCommands();
            }
            Slog.i(TAG, "This cmd(" + name + ") is not registered.");
            return new ATCommands();
        } catch (Exception e) {
            e.printStackTrace();
            return new ATCommands();
        }
    }

    public static int putCommandList(LinkedHashMap<String, LinkedHashSet<ATCommands>> linkedHashMap, String str, ATCommands aTCommands) {
        LinkedHashSet<ATCommands> linkedHashSet;
        if (checkNullParameter(linkedHashMap, str, aTCommands)) {
            return -268435456;
        }
        if (linkedHashMap.containsKey(str)) {
            linkedHashSet = linkedHashMap.get(str);
            linkedHashMap.remove(str);
        } else {
            linkedHashSet = null;
        }
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet<>();
        }
        linkedHashSet.add(aTCommands);
        linkedHashMap.put(str, linkedHashSet);
        return 1;
    }

    public static int putCommandList(LinkedHashMap<String, LinkedHashSet<ATCommands>> linkedHashMap, List<ATCommands> list) {
        if (checkNullParameter(linkedHashMap, list)) {
            return -268435456;
        }
        for (ATCommands aTCommands : list) {
            putCommandList(linkedHashMap, aTCommands.getName(), aTCommands);
        }
        return 1;
    }
}
