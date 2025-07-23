package com.android.systemui.flags;

import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RefactorFlagUtils {
    public static final RefactorFlagUtils INSTANCE = new RefactorFlagUtils();

    private RefactorFlagUtils() {
    }

    public static void assertOnEngBuild(String str) {
        Log.w("RefactorFlag", str);
    }
}
