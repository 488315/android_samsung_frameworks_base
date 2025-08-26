package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.animation.core.VectorizedFiniteAnimationSpec;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes.dex */
final class VectorizedCombinedSpec<V extends AnimationVector> implements VectorizedFiniteAnimationSpec<V> {
    public final List animations;

    public VectorizedCombinedSpec(List<? extends Pair<Long, ? extends VectorizedFiniteAnimationSpec<V>>> list) {
        this.animations = list;
    }

    public final Pair chooseAnimation(long j) {
        Object objPrevious;
        List list = this.animations;
        ListIterator listIterator = list.listIterator(list.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            if (((Number) ((Pair) objPrevious).component1()).longValue() <= j) {
                break;
            }
        }
        Pair pair = (Pair) objPrevious;
        return pair == null ? (Pair) CollectionsKt___CollectionsKt.first(this.animations) : pair;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        Pair pair = (Pair) CollectionsKt___CollectionsKt.last(this.animations);
        return ((VectorizedFiniteAnimationSpec) pair.component2()).getDurationNanos(animationVector, animationVector2, animationVector3) + ((Number) pair.component1()).longValue();
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        Pair pairChooseAnimation = chooseAnimation(j);
        return ((VectorizedFiniteAnimationSpec) pairChooseAnimation.component2()).getValueFromNanos(j - ((Number) pairChooseAnimation.component1()).longValue(), animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        Pair pairChooseAnimation = chooseAnimation(j);
        return ((VectorizedFiniteAnimationSpec) pairChooseAnimation.component2()).getVelocityFromNanos(j - ((Number) pairChooseAnimation.component1()).longValue(), animationVector, animationVector2, animationVector3);
    }
}
