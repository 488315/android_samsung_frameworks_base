package com.android.server.knox.dar.sdp;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SDPLog {
    public static final boolean DEBUG;

    static {
        DEBUG =
                "userdebug".equals(SystemProperties.get("ro.build.type"))
                        || "eng".equals(SystemProperties.get("ro.build.type"));
    }

    public static void d(String str, String str2) {
        SDPLogger.enqMessage(SDPLogUtil.makeDebugMessage(str2));
        if (str2 != null) {
            if (str == null) {
                str = "SDPLog";
            }
            Log.d(str, str2);
        }
    }

    public static void e(Exception exc, String str, String str2) {
        String str3 = SDPLogUtil.getCurrentTime() + ",E,";
        String[] strArr = new String[0];
        try {
            StackTraceElement[] stackTrace = exc.getStackTrace();
            int i = 1;
            strArr = new String[(str2 != null ? 1 : 0) + 1 + stackTrace.length];
            if (str2 != null) {
                strArr[0] = str3 + str2;
            } else {
                i = 0;
            }
            int i2 = i + 1;
            strArr[i] = str3 + exc.toString();
            int length = stackTrace.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = i2 + 1;
                strArr[i2] = str3 + stackTrace[i3].toString();
                i3++;
                i2 = i4;
            }
        } catch (Exception unused) {
        }
        for (String str4 : strArr) {
            SDPLogger.enqMessage(str4);
        }
        if (str2 != null) {
            if (str == null) {
                str = "SDPLog";
            }
            Log.e(str, str2);
            exc.printStackTrace();
        }
    }

    public static void i(String str) {
        i(null, str, new Throwable());
    }

    public static void i(String str, String str2, Throwable th) {
        String str3;
        String str4 = SDPLogUtil.getCurrentTime() + ",I,";
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            str3 =
                    str4
                            + SDPLogUtil.makePairs(
                                    "UserId",
                                    String.valueOf(Binder.getCallingUserHandle().getIdentifier()),
                                    "UID",
                                    String.valueOf(Binder.getCallingUid()),
                                    "PID",
                                    String.valueOf(Binder.getCallingPid()),
                                    "TID",
                                    String.valueOf(Process.myTid()),
                                    "Curr",
                                    stackTrace.length > 1
                                            ? stackTrace[1].getClassName()
                                                    + "."
                                                    + stackTrace[1].getMethodName()
                                                    + "()"
                                            : "()",
                                    "Prev",
                                    stackTrace.length > 2
                                            ? stackTrace[2].getClassName()
                                                    + "."
                                                    + stackTrace[2].getMethodName()
                                                    + "()"
                                            : "()");
        } catch (ArrayIndexOutOfBoundsException unused) {
            str3 = str4;
        }
        for (String str5 :
                str2 == null
                        ? new String[] {str3}
                        : new String[] {
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str4, str2),
                            str3
                        }) {
            SDPLogger.enqMessage(str5);
        }
        if (str2 != null) {
            Log.i(str != null ? str : "SDPLog", str2);
        }
    }

    public static void p(Object... objArr) {
        if (DEBUG) {
            String currentTime = SDPLogUtil.getCurrentTime();
            String makePairs = SDPLogUtil.makePairs(objArr);
            SDPLogger.enqMessage(currentTime + ",P," + makePairs);
            Log.d("SDPLog.p", makePairs);
        }
    }
}
