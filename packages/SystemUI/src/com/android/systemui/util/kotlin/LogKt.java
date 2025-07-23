package com.android.systemui.util.kotlin;

import android.util.Log;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LogKt {
    public static final void logD(String str, Function0 function0) {
        if (Log.isLoggable(str, 3)) {
            Log.d(str, (String) function0.invoke());
        }
    }

    public static final void logI(String str, Function0 function0) {
        if (Log.isLoggable(str, 4)) {
            Log.i(str, (String) function0.invoke());
        }
    }

    public static final void logV(String str, Function0 function0) {
    }
}
