package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Animation<T, V extends AnimationVector> {
    long getDurationNanos();

    Object getTargetValue();

    TwoWayConverter getTypeConverter();

    Object getValueFromNanos(long j);

    AnimationVector getVelocityVectorFromNanos(long j);

    default boolean isFinishedFromNanos(long j) {
        return j >= getDurationNanos();
    }

    boolean isInfinite();
}
