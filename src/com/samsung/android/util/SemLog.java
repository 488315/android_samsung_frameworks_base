package com.samsung.android.util;

import android.os.SemSystemProperties;
import android.util.Log;
import android.util.Slog;

/* loaded from: classes6.dex */
public final class SemLog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    private static final int D_FLAG = -268435216;
    private static final int ENABLE_LOG = -268435456;
    public static final int ERROR = 6;
    private static final int E_FLAG = -267452416;
    public static final int INFO = 4;
    private static final int I_FLAG = -268431616;
    private static final int SAVE_FILE = -16777216;
    public static final int VERBOSE = 2;
    private static final int V_FLAG = -268435441;
    public static final int WARN = 5;
    private static final int WTF_FLAG = -252706816;
    private static final int W_FLAG = -268374016;
    private static boolean mEnabledD = false;
    private static boolean mEnabledE = false;
    private static boolean mEnabledGlobal = false;
    private static boolean mEnabledI = false;
    private static boolean mEnabledV = false;
    private static boolean mEnabledW = false;
    private static boolean mEnabledWtf = false;

    private SemLog() {
    }

    static {
        boolean z = false;
        boolean z2 = SemSystemProperties.getInt("persist.log.seclevel", 0) == 1;
        int i = (int) SemSystemProperties.getLong("persist.log.level", -1L);
        mEnabledGlobal = z2;
        mEnabledV = (i & V_FLAG) == V_FLAG && z2;
        mEnabledD = (i & D_FLAG) == D_FLAG && z2;
        mEnabledI = (i & I_FLAG) == I_FLAG && z2;
        mEnabledW = (i & W_FLAG) == W_FLAG && z2;
        mEnabledE = (i & E_FLAG) == E_FLAG && z2;
        if ((i & WTF_FLAG) == WTF_FLAG && z2) {
            z = true;
        }
        mEnabledWtf = z;
    }

    public static int v(String str, String str2) {
        if (mEnabledV) {
            return Log.v(str, str2);
        }
        return 0;
    }

    public static int v(String str, String str2, Throwable th) {
        if (mEnabledV) {
            return Log.v(str, str2, th);
        }
        return 0;
    }

    public static int d(String str, String str2) {
        if (mEnabledD) {
            return Log.d(str, str2);
        }
        return 0;
    }

    public static int d(String str, String str2, Throwable th) {
        if (mEnabledD) {
            return Log.d(str, str2, th);
        }
        return 0;
    }

    public static int i(String str, String str2) {
        if (mEnabledI) {
            return Log.i(str, str2);
        }
        return 0;
    }

    public static int i(String str, String str2, Throwable th) {
        if (mEnabledI) {
            return Log.i(str, str2, th);
        }
        return 0;
    }

    public static int w(String str, String str2) {
        if (mEnabledW) {
            return Log.w(str, str2);
        }
        return 0;
    }

    public static int w(String str, Throwable th) {
        if (mEnabledW) {
            return Log.w(str, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int w(String str, String str2, Throwable th) {
        if (mEnabledW) {
            return Log.w(str, str2, th);
        }
        return 0;
    }

    public static int e(String str, String str2) {
        if (mEnabledE) {
            return Log.e(str, str2);
        }
        return 0;
    }

    public static int e(String str, String str2, Throwable th) {
        if (mEnabledE) {
            return Log.e(str, str2, th);
        }
        return 0;
    }

    public static int wtf(String str, String str2) {
        if (mEnabledWtf) {
            return Slog.wtf(str, str2);
        }
        return 0;
    }

    public static int wtf(String str, String str2, Throwable th) {
        if (mEnabledWtf) {
            return Slog.wtf(str, str2, th);
        }
        return 0;
    }

    public static boolean isLoggable(String str, int i) {
        return Log.isLoggable(str, i);
    }

    public static String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    public static int println(int i, String str, String str2) {
        if (mEnabledGlobal) {
            return Log.println(i, str, str2);
        }
        return 0;
    }

    public static int secV(String str, String str2) {
        return v(str, str2);
    }

    public static int secV(String str, String str2, Throwable th) {
        return v(str, str2, th);
    }

    public static int secD(String str, String str2) {
        return d(str, str2);
    }

    public static int secD(String str, String str2, Throwable th) {
        return d(str, str2, th);
    }

    public static int secI(String str, String str2) {
        return i(str, str2);
    }

    public static int secI(String str, String str2, Throwable th) {
        return i(str, str2, th);
    }

    public static int secW(String str, String str2) {
        return w(str, str2);
    }

    public static int secW(String str, String str2, Throwable th) {
        return w(str, str2, th);
    }

    public static int secE(String str, String str2) {
        return e(str, str2);
    }

    public static int secE(String str, String str2, Throwable th) {
        return e(str, str2, th);
    }
}
