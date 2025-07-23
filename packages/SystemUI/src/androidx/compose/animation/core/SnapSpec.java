package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapSpec<T> implements DurationBasedAnimationSpec<T> {
    public final int delay;

    public SnapSpec() {
        this(0, 1, null);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof SnapSpec) && ((SnapSpec) obj).delay == this.delay;
    }

    public final int hashCode() {
        return this.delay;
    }

    public SnapSpec(int i) {
        this.delay = i;
    }

    @Override // androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.AnimationSpec
    public final VectorizedDurationBasedAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedSnapSpec(this.delay);
    }

    public /* synthetic */ SnapSpec(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }
}
