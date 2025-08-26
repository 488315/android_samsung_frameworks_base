package com.android.systemui.flags;

import android.util.Log;

/* loaded from: classes2.dex */
public final class RefactorFlagUtils {
    public static final RefactorFlagUtils INSTANCE = new RefactorFlagUtils();

    private RefactorFlagUtils() {
    }

    public static void assertOnEngBuild(String str) {
        Log.w("RefactorFlag", str);
    }
}
