package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface VectorizedAnimationSpec<V extends AnimationVector> {
    long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3);

    default AnimationVector getEndVelocity(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return getVelocityFromNanos(getDurationNanos(animationVector, animationVector2, animationVector3), animationVector, animationVector2, animationVector3);
    }

    AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3);

    AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3);

    boolean isInfinite();
}
