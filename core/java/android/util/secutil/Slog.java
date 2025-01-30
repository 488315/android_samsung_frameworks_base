package android.util.secutil;

/* loaded from: classes4.dex */
public final class Slog {
    private Slog() {
    }

    /* renamed from: v */
    public static int m142v(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 2, tag, msg);
        }
        return 0;
    }

    public static int secV(String tag, String msg) {
        if (LogSwitcher.isShowingSecVLog) {
            return m142v(tag, msg);
        }
        return 0;
    }

    /* renamed from: v */
    public static int m143v(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 2, tag, msg + '\n' + Log.getStackTraceString(tr));
        }
        return 0;
    }

    public static int secV(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecVLog) {
            return m143v(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m136d(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 3, tag, msg);
        }
        return 0;
    }

    public static int secD(String tag, String msg) {
        if (LogSwitcher.isShowingSecDLog) {
            return m136d(tag, msg);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m137d(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 3, tag, msg + '\n' + Log.getStackTraceString(tr));
        }
        return 0;
    }

    public static int secD(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecDLog) {
            return m137d(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m140i(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 4, tag, msg);
        }
        return 0;
    }

    public static int secI(String tag, String msg) {
        if (LogSwitcher.isShowingSecILog) {
            return m140i(tag, msg);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m141i(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 4, tag, msg + '\n' + Log.getStackTraceString(tr));
        }
        return 0;
    }

    public static int secI(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecILog) {
            return m141i(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m144w(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 5, tag, msg);
        }
        return 0;
    }

    public static int secW(String tag, String msg) {
        if (LogSwitcher.isShowingSecWLog) {
            return m144w(tag, msg);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m145w(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 5, tag, msg + '\n' + Log.getStackTraceString(tr));
        }
        return 0;
    }

    public static int secW(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecWLog) {
            return m145w(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m146w(String tag, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 5, tag, Log.getStackTraceString(tr));
        }
        return 0;
    }

    public static int secW(String tag, Throwable tr) {
        if (LogSwitcher.isShowingSecWLog) {
            return m146w(tag, tr);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m138e(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 6, tag, msg);
        }
        return 0;
    }

    public static int secE(String tag, String msg) {
        if (LogSwitcher.isShowingSecELog) {
            return m138e(tag, msg);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m139e(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 6, tag, msg + '\n' + Log.getStackTraceString(tr));
        }
        return 0;
    }

    public static int secE(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecELog) {
            return m139e(tag, msg, tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, tag, msg, null, false, true);
        }
        return 0;
    }

    public static int secWtf(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(tag, msg);
        }
        return 0;
    }

    public static void wtfQuiet(String tag, String msg) {
        Log.wtfQuiet(3, tag, msg, true);
    }

    public static int wtfStack(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, tag, msg, null, true, true);
        }
        return 0;
    }

    public static int secWtfStack(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtfStack(tag, msg);
        }
        return 0;
    }

    public static int wtf(String tag, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, tag, tr.getMessage(), tr, false, true);
        }
        return 0;
    }

    public static int secWtf(String tag, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(tag, tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, tag, msg, tr, false, true);
        }
        return 0;
    }

    public static int secWtf(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(tag, msg, tr);
        }
        return 0;
    }

    public static int println(int priority, String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, priority, tag, msg);
        }
        return 0;
    }

    public static int secPrintln(int priority, String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println(priority, tag, msg);
        }
        return 0;
    }
}
