package androidx.compose.animation.core;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class StartDelayAnimationSpec<T> implements AnimationSpec<T> {
    public final AnimationSpec animationSpec;
    public final long startDelayNanos;

    public StartDelayAnimationSpec(AnimationSpec<T> animationSpec, long j) {
        this.animationSpec = animationSpec;
        this.startDelayNanos = j;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof StartDelayAnimationSpec)) {
            return false;
        }
        StartDelayAnimationSpec startDelayAnimationSpec = (StartDelayAnimationSpec) obj;
        return startDelayAnimationSpec.startDelayNanos == this.startDelayNanos && Intrinsics.areEqual(startDelayAnimationSpec.animationSpec, this.animationSpec);
    }

    public final int hashCode() {
        return Long.hashCode(this.startDelayNanos) + (this.animationSpec.hashCode() * 31);
    }

    @Override // androidx.compose.animation.core.AnimationSpec
    public final VectorizedAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new StartDelayVectorizedAnimationSpec(this.animationSpec.vectorize(twoWayConverter), this.startDelayNanos);
    }
}
