package com.airbnb.lottie.utils;

/* loaded from: classes.dex */
public final class Logger {
    private static LogcatLogger INSTANCE = new LogcatLogger();

    public static void debug() {
        INSTANCE.getClass();
    }

    public static void error() {
        INSTANCE.getClass();
    }

    public static void warning(String str) {
        INSTANCE.warning(str, null);
    }

    public static void warning(String str, Throwable th) {
        INSTANCE.warning(str, th);
    }
}
