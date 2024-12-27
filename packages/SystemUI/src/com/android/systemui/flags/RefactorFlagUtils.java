package com.android.systemui.flags;

import android.os.Build;
import android.util.Log;

public final class RefactorFlagUtils {
    public static final RefactorFlagUtils INSTANCE = new RefactorFlagUtils();

    private RefactorFlagUtils() {
    }

    public static void assertOnEngBuild(String str) {
        if (Log.isLoggable("RefactorFlagAssert", 7)) {
            Log.wtf("RefactorFlagAssert", str, Build.isDebuggable() ? new IllegalStateException(str) : null);
        } else if (Log.isLoggable("RefactorFlag", 5)) {
            Log.w("RefactorFlag", str);
        }
    }
}
