package com.airbnb.lottie.utils;

import android.util.Log;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Logger {
    public static final LogcatLogger INSTANCE = new LogcatLogger();

    public static void debug() {
        INSTANCE.getClass();
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

    public static void warning(String str) {
        INSTANCE.getClass();
        HashSet hashSet = (HashSet) LogcatLogger.loggedMessages;
        if (hashSet.contains(str)) {
            return;
        }
        Log.w("LOTTIE", str, null);
        hashSet.add(str);
    }
}
