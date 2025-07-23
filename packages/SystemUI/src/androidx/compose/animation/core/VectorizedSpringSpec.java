package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorizedSpringSpec<V extends AnimationVector> implements VectorizedFiniteAnimationSpec<V> {
    public final /* synthetic */ VectorizedFloatAnimationSpec $$delegate_0;

    private VectorizedSpringSpec(float f, float f2, Animations animations) {
        this.$$delegate_0 = new VectorizedFloatAnimationSpec(animations);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getDurationNanos(animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getEndVelocity(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getEndVelocity(animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getValueFromNanos(j, animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getVelocityFromNanos(j, animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedFiniteAnimationSpec, androidx.compose.animation.core.VectorizedAnimationSpec
    public final boolean isInfinite() {
        this.$$delegate_0.getClass();
        return false;
    }

    public /* synthetic */ VectorizedSpringSpec(float f, float f2, AnimationVector animationVector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, (i & 2) != 0 ? 1500.0f : f2, (i & 4) != 0 ? null : animationVector);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public VectorizedSpringSpec(final float r2, final float r3, final V r4) {
        /*
            r1 = this;
            int[] r0 = androidx.compose.animation.core.VectorizedAnimationSpecKt.EmptyIntArray
            if (r4 == 0) goto La
            androidx.compose.animation.core.VectorizedAnimationSpecKt$createSpringAnimations$1 r0 = new androidx.compose.animation.core.VectorizedAnimationSpecKt$createSpringAnimations$1
            r0.<init>(r4, r2, r3)
            goto Lf
        La:
            androidx.compose.animation.core.VectorizedAnimationSpecKt$createSpringAnimations$2 r0 = new androidx.compose.animation.core.VectorizedAnimationSpecKt$createSpringAnimations$2
            r0.<init>(r2, r3)
        Lf:
            r1.<init>(r2, r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.VectorizedSpringSpec.<init>(float, float, androidx.compose.animation.core.AnimationVector):void");
    }
}
