package androidx.compose.foundation.lazy.layout;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class LazyLayoutScrollDeltaBetweenPassesKt {
    public static final float DeltaThresholdForScrollAnimation;

    static {
        Dp.Companion companion = Dp.Companion;
        DeltaThresholdForScrollAnimation = 1;
    }
}
