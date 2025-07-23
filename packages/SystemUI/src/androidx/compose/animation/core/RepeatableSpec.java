package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RepeatableSpec<T> implements FiniteAnimationSpec<T> {
    public final DurationBasedAnimationSpec animation;
    public final long initialStartOffset;
    public final int iterations;
    public final RepeatMode repeatMode;

    public /* synthetic */ RepeatableSpec(int i, DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, durationBasedAnimationSpec, repeatMode, j);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof RepeatableSpec) {
            RepeatableSpec repeatableSpec = (RepeatableSpec) obj;
            if (repeatableSpec.iterations == this.iterations && Intrinsics.areEqual(repeatableSpec.animation, this.animation) && repeatableSpec.repeatMode == this.repeatMode && repeatableSpec.initialStartOffset == this.initialStartOffset) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.initialStartOffset) + ((this.repeatMode.hashCode() + ((this.animation.hashCode() + (this.iterations * 31)) * 31)) * 31);
    }

    private RepeatableSpec(int i, DurationBasedAnimationSpec<T> durationBasedAnimationSpec, RepeatMode repeatMode, long j) {
        this.iterations = i;
        this.animation = durationBasedAnimationSpec;
        this.repeatMode = repeatMode;
        this.initialStartOffset = j;
    }

    @Override // androidx.compose.animation.core.AnimationSpec
    public final VectorizedFiniteAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedRepeatableSpec(this.iterations, this.animation.vectorize(twoWayConverter), this.repeatMode, this.initialStartOffset, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ RepeatableSpec(int i, DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, durationBasedAnimationSpec, (i2 & 4) != 0 ? RepeatMode.Restart : repeatMode, (i2 & 8) != 0 ? StartOffset.m12constructorimpl$default(0) : j, (DefaultConstructorMarker) null);
    }

    public /* synthetic */ RepeatableSpec(int i, DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, durationBasedAnimationSpec, (i2 & 4) != 0 ? RepeatMode.Restart : repeatMode);
    }

    public /* synthetic */ RepeatableSpec(int i, DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode) {
        this(i, durationBasedAnimationSpec, repeatMode, StartOffset.m12constructorimpl$default(0), (DefaultConstructorMarker) null);
    }
}
