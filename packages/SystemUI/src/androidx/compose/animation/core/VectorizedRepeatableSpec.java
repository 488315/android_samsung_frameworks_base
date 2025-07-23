package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorizedRepeatableSpec<V extends AnimationVector> implements VectorizedFiniteAnimationSpec<V> {
    public final VectorizedDurationBasedAnimationSpec animation;
    public final long durationNanos;
    public final long initialOffsetNanos;
    public final int iterations;
    public final RepeatMode repeatMode;

    public /* synthetic */ VectorizedRepeatableSpec(int i, VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, vectorizedDurationBasedAnimationSpec, repeatMode, j);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return (this.iterations * this.durationNanos) - this.initialOffsetNanos;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.animation.getValueFromNanos(repetitionPlayTimeNanos(j), animationVector, animationVector2, repetitionStartVelocity(j, animationVector, animationVector3, animationVector2));
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.animation.getVelocityFromNanos(repetitionPlayTimeNanos(j), animationVector, animationVector2, repetitionStartVelocity(j, animationVector, animationVector3, animationVector2));
    }

    public final long repetitionPlayTimeNanos(long j) {
        long j2 = this.initialOffsetNanos;
        if (j + j2 <= 0) {
            return 0L;
        }
        long j3 = j + j2;
        long j4 = this.durationNanos;
        long min = Math.min(j3 / j4, this.iterations - 1);
        return (this.repeatMode == RepeatMode.Restart || min % ((long) 2) == 0) ? j3 - (min * j4) : ((min + 1) * j4) - j3;
    }

    public final AnimationVector repetitionStartVelocity(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        long j2 = this.initialOffsetNanos;
        long j3 = j + j2;
        long j4 = this.durationNanos;
        return j3 > j4 ? getVelocityFromNanos(j4 - j2, animationVector, animationVector2, animationVector3) : animationVector2;
    }

    private VectorizedRepeatableSpec(int i, VectorizedDurationBasedAnimationSpec<V> vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, long j) {
        this.iterations = i;
        this.animation = vectorizedDurationBasedAnimationSpec;
        this.repeatMode = repeatMode;
        if (i < 1) {
            throw new IllegalArgumentException("Iterations count can't be less than 1");
        }
        this.durationNanos = (vectorizedDurationBasedAnimationSpec.getDurationMillis() + vectorizedDurationBasedAnimationSpec.getDelayMillis()) * 1000000;
        this.initialOffsetNanos = j * 1000000;
    }

    public /* synthetic */ VectorizedRepeatableSpec(int i, VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, vectorizedDurationBasedAnimationSpec, (i2 & 4) != 0 ? RepeatMode.Restart : repeatMode, (i2 & 8) != 0 ? StartOffset.m12constructorimpl$default(0) : j, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ VectorizedRepeatableSpec(int i, VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, vectorizedDurationBasedAnimationSpec, (i2 & 4) != 0 ? RepeatMode.Restart : repeatMode);
    }

    public /* synthetic */ VectorizedRepeatableSpec(int i, VectorizedDurationBasedAnimationSpec vectorizedDurationBasedAnimationSpec, RepeatMode repeatMode) {
        this(i, vectorizedDurationBasedAnimationSpec, repeatMode, StartOffset.m12constructorimpl$default(0), (DefaultConstructorMarker) null);
    }
}
