package com.airbnb.lottie.utils;

import android.util.Log;
import java.util.HashSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Logger {
    public static final LogcatLogger INSTANCE = new LogcatLogger();

    public static void debug() {
        INSTANCE.getClass();
    }

    public static void warning(String str) {
        INSTANCE.getClass();
        HashSet hashSet = (HashSet) LogcatLogger.loggedMessages;
        if (hashSet.contains(str)) {
            return;
        }
        Log.w("LOTTIE", str, null);
        hashSet.add(str);
    }

    public static void warning(String str, Throwable th) {
        INSTANCE.getClass();
        HashSet hashSet = (HashSet) LogcatLogger.loggedMessages;
        if (hashSet.contains(str)) {
            return;
        }
        Log.w("LOTTIE", str, th);
        hashSet.add(str);
    }
}
