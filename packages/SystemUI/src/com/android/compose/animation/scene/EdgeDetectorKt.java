package com.android.compose.animation.scene;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class EdgeDetectorKt {
    public static final FixedSizeEdgeDetector DefaultEdgeDetector;

    static {
        Dp.Companion companion = Dp.Companion;
        DefaultEdgeDetector = new FixedSizeEdgeDetector(40, null);
    }
}
