package com.android.internal.os;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;

/* loaded from: classes5.dex */
public class ChildZygoteInit {
    private static final String TAG = "ChildZygoteInit";

    static String parseSocketNameFromArgs(String[] strArr) {
        for (String str : strArr) {
            if (str.startsWith(Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG)) {
                return str.substring(16);
            }
        }
        return null;
    }

    static String parseAbiListFromArgs(String[] strArr) {
        for (String str : strArr) {
            if (str.startsWith(Zygote.CHILD_ZYGOTE_ABI_LIST_ARG)) {
                return str.substring(11);
            }
        }
        return null;
    }

    static int parseIntFromArg(String[] strArr, String str) {
        int i = -1;
        for (String str2 : strArr) {
            if (str2.startsWith(str)) {
                String substring = str2.substring(str2.indexOf(61) + 1);
                try {
                    i = Integer.parseInt(substring);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid int argument: " + substring, e);
                }
            }
        }
        return i;
    }

    static void runZygoteServer(ZygoteServer zygoteServer, String[] strArr) {
        String parseSocketNameFromArgs = parseSocketNameFromArgs(strArr);
        if (parseSocketNameFromArgs == null) {
            throw new NullPointerException("No socketName specified");
        }
        String parseAbiListFromArgs = parseAbiListFromArgs(strArr);
        if (parseAbiListFromArgs == null) {
            throw new NullPointerException("No abiList specified");
        }
        try {
            Os.prctl(OsConstants.PR_SET_NO_NEW_PRIVS, 1L, 0L, 0L, 0L);
            int parseIntFromArg = parseIntFromArg(strArr, Zygote.CHILD_ZYGOTE_UID_RANGE_START);
            int parseIntFromArg2 = parseIntFromArg(strArr, Zygote.CHILD_ZYGOTE_UID_RANGE_END);
            if (parseIntFromArg == -1 || parseIntFromArg2 == -1) {
                throw new RuntimeException("Couldn't parse UID range start/end");
            }
            if (parseIntFromArg > parseIntFromArg2) {
                throw new RuntimeException("Passed in UID range is invalid, min > max.");
            }
            if (parseIntFromArg < 90000) {
                throw new RuntimeException("Passed in UID range does not map to isolated processes.");
            }
            Zygote.nativeInstallSeccompUidGidFilter(parseIntFromArg, parseIntFromArg2);
            try {
                try {
                    zygoteServer.registerServerSocketAtAbstractName(parseSocketNameFromArgs);
                    Zygote.nativeAllowFileAcrossFork("ABSTRACT/" + parseSocketNameFromArgs);
                    Runnable runSelectLoop = zygoteServer.runSelectLoop(parseAbiListFromArgs);
                    zygoteServer.closeServerSocket();
                    if (runSelectLoop != null) {
                        runSelectLoop.run();
                    }
                } catch (RuntimeException e) {
                    Log.e(TAG, "Fatal exception:", e);
                    throw e;
                }
            } catch (Throwable th) {
                zygoteServer.closeServerSocket();
                throw th;
            }
        } catch (ErrnoException e2) {
            throw new RuntimeException("Failed to set PR_SET_NO_NEW_PRIVS", e2);
        }
    }
}
