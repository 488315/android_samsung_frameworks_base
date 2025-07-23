package com.android.compose.animation.scene;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EdgeDetectorKt {
    public static final FixedSizeEdgeDetector DefaultEdgeDetector;

    static {
        Dp.Companion companion = Dp.Companion;
        DefaultEdgeDetector = new FixedSizeEdgeDetector(40, null);
    }
}
