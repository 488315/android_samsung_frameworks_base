package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.AnimationVector1D;
import java.util.concurrent.CancellationException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ItemFoundInScroll extends CancellationException {
    private final int itemOffset;
    private final AnimationState<Float, AnimationVector1D> previousAnimation;

    public ItemFoundInScroll(int i, AnimationState<Float, AnimationVector1D> animationState) {
        this.itemOffset = i;
        this.previousAnimation = animationState;
    }

    public final int getItemOffset() {
        return this.itemOffset;
    }

    public final AnimationState getPreviousAnimation() {
        return this.previousAnimation;
    }
}
