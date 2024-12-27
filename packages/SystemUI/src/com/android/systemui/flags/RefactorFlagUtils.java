package com.android.systemui.flags;

import android.os.Build;
import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
