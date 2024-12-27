package com.android.systemui.keyguard;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.util.LogUtil;

public final class Log {
    public static void d(String str, String str2) {
        android.util.Log.d(str, str2);
        KeyguardDumpLog.log(str, LogLevel.DEBUG, str2, null);
    }

    public static void e(String str, String str2) {
        android.util.Log.e(str, str2);
        KeyguardDumpLog.log(str, LogLevel.ERROR, str2, null);
    }

    public static void i(String str, String str2) {
        android.util.Log.i(str, str2);
        KeyguardDumpLog.log(str, LogLevel.INFO, str2, null);
    }

    public static void w(String str, String str2) {
        android.util.Log.w(str, str2);
        KeyguardDumpLog.log(str, LogLevel.WARNING, str2, null);
    }

    public static void d(String str, String str2, Object... objArr) {
        d(str, LogUtil.getMsg(str2, objArr));
    }
}
