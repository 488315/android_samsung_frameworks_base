package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnimationResult<T, V extends AnimationVector> {
    public final AnimationState currentAnimationState;
    public final Object remainingOffset;

    public AnimationResult(T t, AnimationState<T, V> animationState) {
        this.remainingOffset = t;
        this.currentAnimationState = animationState;
    }
}
