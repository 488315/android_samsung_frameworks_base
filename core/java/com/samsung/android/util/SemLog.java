package com.samsung.android.util;

import android.p009os.SemSystemProperties;
import android.util.Log;
import android.util.Slog;

/* loaded from: classes5.dex */
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
    private static boolean mEnabledD;
    private static boolean mEnabledE;
    private static boolean mEnabledGlobal;
    private static boolean mEnabledI;
    private static boolean mEnabledV;
    private static boolean mEnabledW;
    private static boolean mEnabledWtf;

    static {
        boolean z = false;
        mEnabledGlobal = false;
        mEnabledV = false;
        mEnabledD = false;
        mEnabledI = false;
        mEnabledW = false;
        mEnabledE = false;
        mEnabledWtf = false;
        boolean secLevel = SemSystemProperties.getInt("persist.log.seclevel", 0) == 1;
        int semLevel = (int) SemSystemProperties.getLong("persist.log.level", -1L);
        mEnabledGlobal = secLevel;
        mEnabledV = (semLevel & V_FLAG) == V_FLAG && secLevel;
        mEnabledD = (semLevel & D_FLAG) == D_FLAG && secLevel;
        mEnabledI = (semLevel & I_FLAG) == I_FLAG && secLevel;
        mEnabledW = (semLevel & W_FLAG) == W_FLAG && secLevel;
        mEnabledE = (semLevel & E_FLAG) == E_FLAG && secLevel;
        if ((semLevel & WTF_FLAG) == WTF_FLAG && secLevel) {
            z = true;
        }
        mEnabledWtf = z;
    }

    private SemLog() {
    }

    /* renamed from: v */
    public static int m484v(String tag, String msg) {
        if (mEnabledV) {
            return Log.m100v(tag, msg);
        }
        return 0;
    }

    /* renamed from: v */
    public static int m485v(String tag, String msg, Throwable tr) {
        if (mEnabledV) {
            return Log.m101v(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m478d(String tag, String msg) {
        if (mEnabledD) {
            return Log.m94d(tag, msg);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m479d(String tag, String msg, Throwable tr) {
        if (mEnabledD) {
            return Log.m95d(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m482i(String tag, String msg) {
        if (mEnabledI) {
            return Log.m98i(tag, msg);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m483i(String tag, String msg, Throwable tr) {
        if (mEnabledI) {
            return Log.m99i(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m486w(String tag, String msg) {
        if (mEnabledW) {
            return Log.m102w(tag, msg);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m488w(String tag, Throwable tr) {
        if (mEnabledW) {
            return Log.m102w(tag, Log.getStackTraceString(tr));
        }
        return 0;
    }

    /* renamed from: w */
    public static int m487w(String tag, String msg, Throwable tr) {
        if (mEnabledW) {
            return Log.m103w(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m480e(String tag, String msg) {
        if (mEnabledE) {
            return Log.m96e(tag, msg);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m481e(String tag, String msg, Throwable tr) {
        if (mEnabledE) {
            return Log.m97e(tag, msg, tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg) {
        if (mEnabledWtf) {
            return Slog.wtf(tag, msg);
        }
        return 0;
    }

    public static int wtf(String tag, Throwable tr) {
        if (mEnabledWtf) {
            return Slog.wtf(tag, tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (mEnabledWtf) {
            return Slog.wtf(tag, msg, tr);
        }
        return 0;
    }

    public static boolean isLoggable(String tag, int level) {
        return Log.isLoggable(tag, level);
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    public static int println(int priority, String tag, String msg) {
        if (mEnabledGlobal) {
            return Log.println(priority, tag, msg);
        }
        return 0;
    }

    public static int wtfStack(String tag, String msg) {
        if (mEnabledWtf) {
            return Slog.wtfStack(tag, msg);
        }
        return 0;
    }

    public static int secV(String tag, String msg) {
        return m484v(tag, msg);
    }

    public static int secV(String tag, String msg, Throwable tr) {
        return m485v(tag, msg, tr);
    }

    public static int secD(String tag, String msg) {
        return m478d(tag, msg);
    }

    public static int secD(String tag, String msg, Throwable tr) {
        return m479d(tag, msg, tr);
    }

    public static int secI(String tag, String msg) {
        return m482i(tag, msg);
    }

    public static int secI(String tag, String msg, Throwable tr) {
        return m483i(tag, msg, tr);
    }

    public static int secW(String tag, String msg) {
        return m486w(tag, msg);
    }

    public static int secW(String tag, String msg, Throwable tr) {
        return m487w(tag, msg, tr);
    }

    public static int secW(String tag, Throwable tr) {
        return m488w(tag, tr);
    }

    public static int secE(String tag, String msg) {
        return m480e(tag, msg);
    }

    public static int secE(String tag, String msg, Throwable tr) {
        return m481e(tag, msg, tr);
    }

    public static int secWtf(String tag, String msg) {
        return wtf(tag, msg);
    }

    public static int secWtfStack(String tag, String msg) {
        return wtfStack(tag, msg);
    }

    public static int secWtf(String tag, Throwable tr) {
        return wtf(tag, tr);
    }

    public static int secWtf(String tag, String msg, Throwable tr) {
        return wtf(tag, msg, tr);
    }
}
