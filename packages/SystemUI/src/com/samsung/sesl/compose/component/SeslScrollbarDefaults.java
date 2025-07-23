package com.samsung.sesl.compose.component;

import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslScrollbarDefaults {
    public static final SeslScrollbarDefaults INSTANCE = new SeslScrollbarDefaults();

    static {
        CubicBezierEasing cubicBezierEasing = EasingKt.FastOutSlowInEasing;
    }

    private SeslScrollbarDefaults() {
    }
}
