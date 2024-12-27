package com.android.systemui.util.time;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MeasureTimeUtilKt {
    public static final long measureTimeMillis(SystemClock systemClock, Function0 function0) {
        long currentTimeMillis = systemClock.currentTimeMillis();
        function0.invoke();
        return systemClock.currentTimeMillis() - currentTimeMillis;
    }
}
