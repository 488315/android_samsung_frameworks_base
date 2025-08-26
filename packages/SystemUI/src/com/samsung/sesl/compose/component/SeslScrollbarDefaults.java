package com.samsung.sesl.compose.component;

import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;

/* loaded from: classes4.dex */
public final class SeslScrollbarDefaults {
    public static final SeslScrollbarDefaults INSTANCE = new SeslScrollbarDefaults();

    static {
        CubicBezierEasing cubicBezierEasing = EasingKt.FastOutSlowInEasing;
    }

    private SeslScrollbarDefaults() {
    }
}
