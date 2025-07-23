package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
