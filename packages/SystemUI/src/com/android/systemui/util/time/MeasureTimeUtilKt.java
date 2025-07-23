package com.android.systemui.util.time;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MeasureTimeUtilKt {
    public static final long measureTimeMillis(SystemClock systemClock, Function0 function0) {
        long currentTimeMillis = systemClock.currentTimeMillis();
        function0.invoke();
        return systemClock.currentTimeMillis() - currentTimeMillis;
    }
}
