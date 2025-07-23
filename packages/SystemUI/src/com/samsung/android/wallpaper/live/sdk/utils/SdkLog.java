package com.samsung.android.wallpaper.live.sdk.utils;

import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdkLog {
    private static ILog sDefaultLogInterface;
    private static ILog sLogImpl;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ILog {
    }

    static {
        ILog iLog = new ILog() { // from class: com.samsung.android.wallpaper.live.sdk.utils.SdkLog.1
        };
        sDefaultLogInterface = iLog;
        sLogImpl = iLog;
    }

    public static void d(String str, String str2) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.e(str, str2);
        }
    }

    public static void i(String str, String str2) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.i(str, str2);
        }
    }

    public static void setLogDispatcher(ILog iLog) {
        sLogImpl = iLog;
    }

    public static void v(String str, String str2) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            iLog.getClass();
        }
    }

    public static void w(String str, String str2) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.w(str, str2);
        }
    }

    public static void v(String str, String str2, Throwable th) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            iLog.getClass();
        }
    }

    public static void d(String str, String str2, Throwable th) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.d(str, str2, th);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.e(str, str2, th);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.i(str, str2, th);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        ILog iLog = sLogImpl;
        if (iLog != null) {
            ((AnonymousClass1) iLog).getClass();
            Log.w(str, str2, th);
        }
    }
}
