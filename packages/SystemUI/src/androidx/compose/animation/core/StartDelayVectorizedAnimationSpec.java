package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class StartDelayVectorizedAnimationSpec<V extends AnimationVector> implements VectorizedAnimationSpec<V> {
    public final long startDelayNanos;
    public final VectorizedAnimationSpec vectorizedAnimationSpec;

    public StartDelayVectorizedAnimationSpec(VectorizedAnimationSpec<V> vectorizedAnimationSpec, long j) {
        this.vectorizedAnimationSpec = vectorizedAnimationSpec;
        this.startDelayNanos = j;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof StartDelayVectorizedAnimationSpec)) {
            return false;
        }
        StartDelayVectorizedAnimationSpec startDelayVectorizedAnimationSpec = (StartDelayVectorizedAnimationSpec) obj;
        return startDelayVectorizedAnimationSpec.startDelayNanos == this.startDelayNanos && Intrinsics.areEqual(startDelayVectorizedAnimationSpec.vectorizedAnimationSpec, this.vectorizedAnimationSpec);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.vectorizedAnimationSpec.getDurationNanos(animationVector, animationVector2, animationVector3) + this.startDelayNanos;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        long j2 = this.startDelayNanos;
        return j < j2 ? animationVector : this.vectorizedAnimationSpec.getValueFromNanos(j - j2, animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        long j2 = this.startDelayNanos;
        return j < j2 ? animationVector3 : this.vectorizedAnimationSpec.getVelocityFromNanos(j - j2, animationVector, animationVector2, animationVector3);
    }

    public final int hashCode() {
        return Long.hashCode(this.startDelayNanos) + (this.vectorizedAnimationSpec.hashCode() * 31);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final boolean isInfinite() {
        return this.vectorizedAnimationSpec.isInfinite();
    }
}
