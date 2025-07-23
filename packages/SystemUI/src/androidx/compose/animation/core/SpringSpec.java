package androidx.compose.animation.core;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SpringSpec<T> implements FiniteAnimationSpec<T> {
    public final float dampingRatio;
    public final float stiffness;
    public final Object visibilityThreshold;

    public SpringSpec() {
        this(0.0f, 0.0f, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SpringSpec) {
            SpringSpec springSpec = (SpringSpec) obj;
            if (springSpec.dampingRatio == this.dampingRatio && springSpec.stiffness == this.stiffness && Intrinsics.areEqual(springSpec.visibilityThreshold, this.visibilityThreshold)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        Object obj = this.visibilityThreshold;
        return Float.hashCode(this.stiffness) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dampingRatio, (obj != null ? obj.hashCode() : 0) * 31, 31);
    }

    @Override // androidx.compose.animation.core.AnimationSpec
    public final VectorizedAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        Object obj = this.visibilityThreshold;
        return new VectorizedSpringSpec(this.dampingRatio, this.stiffness, obj == null ? null : (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo779invoke(obj));
    }

    public SpringSpec(float f, float f2, T t) {
        this.dampingRatio = f;
        this.stiffness = f2;
        this.visibilityThreshold = t;
    }

    public /* synthetic */ SpringSpec(float f, float f2, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, (i & 2) != 0 ? 1500.0f : f2, (i & 4) != 0 ? null : obj);
    }

    @Override // androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.AnimationSpec
    public final VectorizedFiniteAnimationSpec vectorize(TwoWayConverter twoWayConverter) {
        Object obj = this.visibilityThreshold;
        return new VectorizedSpringSpec(this.dampingRatio, this.stiffness, obj == null ? null : (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo779invoke(obj));
    }
}
