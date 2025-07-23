package androidx.compose.animation.core;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TweenSpec<T> implements DurationBasedAnimationSpec<T> {
    public final int delay;
    public final int durationMillis;
    public final Easing easing;

    public TweenSpec() {
        this(0, 0, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TweenSpec) {
            TweenSpec tweenSpec = (TweenSpec) obj;
            if (tweenSpec.durationMillis == this.durationMillis && tweenSpec.delay == this.delay && Intrinsics.areEqual(tweenSpec.easing, this.easing)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.easing.hashCode() + (this.durationMillis * 31)) * 31) + this.delay;
    }

    @Override // androidx.compose.animation.core.AnimationSpec
    public final VectorizedAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedTweenSpec(this.durationMillis, this.delay, this.easing);
    }

    public TweenSpec(int i, int i2, Easing easing, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 300 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? EasingKt.FastOutSlowInEasing : easing);
    }

    @Override // androidx.compose.animation.core.DurationBasedAnimationSpec, androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.AnimationSpec
    public final VectorizedDurationBasedAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedTweenSpec(this.durationMillis, this.delay, this.easing);
    }

    @Override // androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.AnimationSpec
    public final VectorizedFiniteAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        return new VectorizedTweenSpec(this.durationMillis, this.delay, this.easing);
    }

    public TweenSpec(int i, int i2, Easing easing) {
        this.durationMillis = i;
        this.delay = i2;
        this.easing = easing;
    }
}
