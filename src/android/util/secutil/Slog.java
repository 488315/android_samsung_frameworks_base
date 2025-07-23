package android.util.secutil;

/* loaded from: classes4.dex */
public final class Slog {
    private Slog() {
    }

    public static int v(String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 2, str, str2);
        }
        return 0;
    }

    public static int secV(String str, String str2) {
        if (LogSwitcher.isShowingSecVLog) {
            return v(str, str2);
        }
        return 0;
    }

    public static int v(String str, String str2, Throwable th) {
        if (!LogSwitcher.isShowingGlobalLog) {
            return 0;
        }
        return Log.println_native(3, 2, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int secV(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecVLog) {
            return v(str, str2, th);
        }
        return 0;
    }

    public static int d(String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 3, str, str2);
        }
        return 0;
    }

    public static int secD(String str, String str2) {
        if (LogSwitcher.isShowingSecDLog) {
            return d(str, str2);
        }
        return 0;
    }

    public static int d(String str, String str2, Throwable th) {
        if (!LogSwitcher.isShowingGlobalLog) {
            return 0;
        }
        return Log.println_native(3, 3, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int secD(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecDLog) {
            return d(str, str2, th);
        }
        return 0;
    }

    public static int i(String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 4, str, str2);
        }
        return 0;
    }

    public static int secI(String str, String str2) {
        if (LogSwitcher.isShowingSecILog) {
            return i(str, str2);
        }
        return 0;
    }

    public static int i(String str, String str2, Throwable th) {
        if (!LogSwitcher.isShowingGlobalLog) {
            return 0;
        }
        return Log.println_native(3, 4, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int secI(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecILog) {
            return i(str, str2, th);
        }
        return 0;
    }

    public static int w(String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 5, str, str2);
        }
        return 0;
    }

    public static int secW(String str, String str2) {
        if (LogSwitcher.isShowingSecWLog) {
            return w(str, str2);
        }
        return 0;
    }

    public static int w(String str, String str2, Throwable th) {
        if (!LogSwitcher.isShowingGlobalLog) {
            return 0;
        }
        return Log.println_native(3, 5, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int secW(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecWLog) {
            return w(str, str2, th);
        }
        return 0;
    }

    public static int w(String str, Throwable th) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 5, str, Log.getStackTraceString(th));
        }
        return 0;
    }

    public static int secW(String str, Throwable th) {
        if (LogSwitcher.isShowingSecWLog) {
            return w(str, th);
        }
        return 0;
    }

    public static int e(String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, 6, str, str2);
        }
        return 0;
    }

    public static int secE(String str, String str2) {
        if (LogSwitcher.isShowingSecELog) {
            return e(str, str2);
        }
        return 0;
    }

    public static int e(String str, String str2, Throwable th) {
        if (!LogSwitcher.isShowingGlobalLog) {
            return 0;
        }
        return Log.println_native(3, 6, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static int secE(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecELog) {
            return e(str, str2, th);
        }
        return 0;
    }

    public static int wtf(String str, String str2) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, str, str2, null, false, true);
        }
        return 0;
    }

    public static int secWtf(String str, String str2) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(str, str2);
        }
        return 0;
    }

    public static void wtfQuiet(String str, String str2) {
        Log.wtfQuiet(3, str, str2, true);
    }

    public static int wtfStack(String str, String str2) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, str, str2, null, true, true);
        }
        return 0;
    }

    public static int secWtfStack(String str, String str2) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtfStack(str, str2);
        }
        return 0;
    }

    public static int wtf(String str, Throwable th) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, str, th.getMessage(), th, false, true);
        }
        return 0;
    }

    public static int secWtf(String str, Throwable th) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(str, th);
        }
        return 0;
    }

    public static int wtf(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return Log.wtf(3, str, str2, th, false, true);
        }
        return 0;
    }

    public static int secWtf(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(str, str2, th);
        }
        return 0;
    }

    public static int println(int i, String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return Log.println_native(3, i, str, str2);
        }
        return 0;
    }

    public static int secPrintln(int i, String str, String str2) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println(i, str, str2);
        }
        return 0;
    }
}
