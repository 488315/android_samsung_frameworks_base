package com.airbnb.lottie.utils;

import android.util.Log;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class LogcatLogger {
    private static final Set<String> loggedMessages = new HashSet();

    public final void warning(String str, Throwable th) {
        Set<String> set = loggedMessages;
        if (((HashSet) set).contains(str)) {
            return;
        }
        Log.w("LOTTIE", str, th);
        ((HashSet) set).add(str);
    }
}
