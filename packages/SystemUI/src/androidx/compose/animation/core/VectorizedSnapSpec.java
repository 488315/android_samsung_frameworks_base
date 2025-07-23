package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorizedSnapSpec<V extends AnimationVector> implements VectorizedDurationBasedAnimationSpec<V> {
    public final int delayMillis;

    public VectorizedSnapSpec() {
        this(0, 1, null);
    }

    @Override // androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec
    public final int getDelayMillis() {
        return this.delayMillis;
    }

    @Override // androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec
    public final int getDurationMillis() {
        return 0;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return j < ((long) this.delayMillis) * 1000000 ? animationVector : animationVector2;
    }

    public VectorizedSnapSpec(int i) {
        this.delayMillis = i;
    }

    public /* synthetic */ VectorizedSnapSpec(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return animationVector3;
    }
}
