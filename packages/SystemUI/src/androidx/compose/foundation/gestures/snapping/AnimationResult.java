package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationVector;

/* loaded from: classes.dex */
final class AnimationResult<T, V extends AnimationVector> {
    public final AnimationState currentAnimationState;
    public final Object remainingOffset;

    public AnimationResult(T t, AnimationState<T, V> animationState) {
        this.remainingOffset = t;
        this.currentAnimationState = animationState;
    }
}
