package com.android.systemui.util.time;

import kotlin.jvm.functions.Function0;

public final class MeasureTimeUtilKt {
    public static final long measureTimeMillis(SystemClock systemClock, Function0 function0) {
        long currentTimeMillis = systemClock.currentTimeMillis();
        function0.invoke();
        return systemClock.currentTimeMillis() - currentTimeMillis;
    }
}
