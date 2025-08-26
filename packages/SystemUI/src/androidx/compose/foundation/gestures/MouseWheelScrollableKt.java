package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class MouseWheelScrollableKt {
    public static final float AnimationSpeed;
    public static final float AnimationThreshold;

    static {
        Dp.Companion companion = Dp.Companion;
        AnimationThreshold = 6;
        AnimationSpeed = 1;
    }

    public static final boolean access$isLowScrollingDelta(float f) {
        return Float.isNaN(f) || Math.abs(f) < 0.5f;
    }
}
