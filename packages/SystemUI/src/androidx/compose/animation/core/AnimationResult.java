package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimationResult<T, V extends AnimationVector> {
    public final AnimationEndReason endReason;
    public final AnimationState endState;

    public AnimationResult(AnimationState<T, V> animationState, AnimationEndReason animationEndReason) {
        this.endState = animationState;
        this.endReason = animationEndReason;
    }

    public final String toString() {
        return "AnimationResult(endReason=" + this.endReason + ", endState=" + this.endState + ')';
    }
}
