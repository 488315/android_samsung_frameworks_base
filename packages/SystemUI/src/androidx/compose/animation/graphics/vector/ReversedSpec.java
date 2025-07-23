package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorizedFiniteAnimationSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ReversedSpec<T> implements FiniteAnimationSpec<T> {
    public final int durationMillis;
    public final FiniteAnimationSpec spec;

    public ReversedSpec(FiniteAnimationSpec<T> finiteAnimationSpec, int i) {
        this.spec = finiteAnimationSpec;
        this.durationMillis = i;
    }

    @Override // androidx.compose.animation.core.AnimationSpec
    public final VectorizedFiniteAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedReversedSpec(this.spec.vectorize(twoWayConverter), this.durationMillis * 1000000);
    }
}
