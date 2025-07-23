package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorizedInfiniteRepeatableSpec<V extends AnimationVector> implements VectorizedAnimationSpec<V> {
    public final VectorizedDurationBasedAnimationSpec animation;
    public final long durationNanos;
    public final long initialOffsetNanos;
    public final RepeatMode repeatMode;

    public /* synthetic */ VectorizedInfiniteRepeatableSpec(VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(vectorizedDurationBasedAnimationSpec, repeatMode, j);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return Long.MAX_VALUE;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.animation.getValueFromNanos(repetitionPlayTimeNanos$1(j), animationVector, animationVector2, repetitionStartVelocity$1(j, animationVector, animationVector3, animationVector2));
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.animation.getVelocityFromNanos(repetitionPlayTimeNanos$1(j), animationVector, animationVector2, repetitionStartVelocity$1(j, animationVector, animationVector3, animationVector2));
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final boolean isInfinite() {
        return true;
    }

    public final long repetitionPlayTimeNanos$1(long j) {
        long j2 = this.initialOffsetNanos;
        if (j + j2 <= 0) {
            return 0L;
        }
        long j3 = j + j2;
        long j4 = this.durationNanos;
        long j5 = j3 / j4;
        return (this.repeatMode == RepeatMode.Restart || j5 % ((long) 2) == 0) ? j3 - (j5 * j4) : ((j5 + 1) * j4) - j3;
    }

    public final AnimationVector repetitionStartVelocity$1(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        long j2 = this.initialOffsetNanos;
        long j3 = j + j2;
        long j4 = this.durationNanos;
        return j3 > j4 ? this.animation.getVelocityFromNanos(j4 - j2, animationVector, animationVector3, animationVector2) : animationVector2;
    }

    private VectorizedInfiniteRepeatableSpec(VectorizedDurationBasedAnimationSpec<V> vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, long j) {
        this.animation = vectorizedDurationBasedAnimationSpec;
        this.repeatMode = repeatMode;
        this.durationNanos = (vectorizedDurationBasedAnimationSpec.getDurationMillis() + vectorizedDurationBasedAnimationSpec.getDelayMillis()) * 1000000;
        this.initialOffsetNanos = j * 1000000;
    }

    public /* synthetic */ VectorizedInfiniteRepeatableSpec(VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(vectorizedDurationBasedAnimationSpec, (i & 2) != 0 ? RepeatMode.Restart : repeatMode, (i & 4) != 0 ? StartOffset.m12constructorimpl$default(0) : j, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ VectorizedInfiniteRepeatableSpec(VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(vectorizedDurationBasedAnimationSpec, (i & 2) != 0 ? RepeatMode.Restart : repeatMode);
    }

    public /* synthetic */ VectorizedInfiniteRepeatableSpec(VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode) {
        this(vectorizedDurationBasedAnimationSpec, repeatMode, StartOffset.m12constructorimpl$default(0), (DefaultConstructorMarker) null);
    }
}
