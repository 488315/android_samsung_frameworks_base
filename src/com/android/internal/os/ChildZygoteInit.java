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

    static int parseIntFromArg(String[] strArr, String str) throws NumberFormatException {
        int i = -1;
        for (String str2 : strArr) {
            if (str2.startsWith(str)) {
                String strSubstring = str2.substring(str2.indexOf(61) + 1);
                try {
                    i = Integer.parseInt(strSubstring);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid int argument: " + strSubstring, e);
                }
            }
        }
        return i;
    }

    static void runZygoteServer(ZygoteServer zygoteServer, String[] strArr) throws NumberFormatException, ErrnoException {
        String socketNameFromArgs = parseSocketNameFromArgs(strArr);
        if (socketNameFromArgs == null) {
            throw new NullPointerException("No socketName specified");
        }
        String abiListFromArgs = parseAbiListFromArgs(strArr);
        if (abiListFromArgs == null) {
            throw new NullPointerException("No abiList specified");
        }
        try {
            Os.prctl(OsConstants.PR_SET_NO_NEW_PRIVS, 1L, 0L, 0L, 0L);
            int intFromArg = parseIntFromArg(strArr, Zygote.CHILD_ZYGOTE_UID_RANGE_START);
            int intFromArg2 = parseIntFromArg(strArr, Zygote.CHILD_ZYGOTE_UID_RANGE_END);
            if (intFromArg == -1 || intFromArg2 == -1) {
                throw new RuntimeException("Couldn't parse UID range start/end");
            }
            if (intFromArg > intFromArg2) {
                throw new RuntimeException("Passed in UID range is invalid, min > max.");
            }
            if (intFromArg < 90000) {
                throw new RuntimeException("Passed in UID range does not map to isolated processes.");
            }
            Zygote.nativeInstallSeccompUidGidFilter(intFromArg, intFromArg2);
            try {
                try {
                    zygoteServer.registerServerSocketAtAbstractName(socketNameFromArgs);
                    Zygote.nativeAllowFileAcrossFork("ABSTRACT/" + socketNameFromArgs);
                    Runnable runnableRunSelectLoop = zygoteServer.runSelectLoop(abiListFromArgs);
                    zygoteServer.closeServerSocket();
                    if (runnableRunSelectLoop != null) {
                        runnableRunSelectLoop.run();
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
