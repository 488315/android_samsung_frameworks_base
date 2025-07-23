package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TargetBasedAnimation<T, V extends AnimationVector> implements Animation<T, V> {
    public long _durationNanos;
    public AnimationVector _endVelocity;
    public final VectorizedAnimationSpec animationSpec;
    public AnimationVector initialValueVector;
    public final AnimationVector initialVelocityVector;
    public Object mutableInitialValue;
    public Object mutableTargetValue;
    public AnimationVector targetValueVector;
    public final TwoWayConverter typeConverter;

    public TargetBasedAnimation(VectorizedAnimationSpec<V> vectorizedAnimationSpec, TwoWayConverter<T, V> twoWayConverter, T t, T t2, V v) {
        this.animationSpec = vectorizedAnimationSpec;
        this.typeConverter = twoWayConverter;
        this.mutableTargetValue = t2;
        this.mutableInitialValue = t;
        TwoWayConverterImpl twoWayConverterImpl = (TwoWayConverterImpl) twoWayConverter;
        this.initialValueVector = (AnimationVector) twoWayConverterImpl.convertToVector.mo779invoke(t);
        this.targetValueVector = (AnimationVector) twoWayConverterImpl.convertToVector.mo779invoke(t2);
        this.initialVelocityVector = v != null ? AnimationVectorsKt.copy(v) : ((AnimationVector) twoWayConverterImpl.convertToVector.mo779invoke(t)).newVector$animation_core();
        this._durationNanos = -1L;
    }

    @Override // androidx.compose.animation.core.Animation
    public final long getDurationNanos() {
        if (this._durationNanos < 0) {
            this._durationNanos = this.animationSpec.getDurationNanos(this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        }
        return this._durationNanos;
    }

    @Override // androidx.compose.animation.core.Animation
    public final Object getTargetValue() {
        return this.mutableTargetValue;
    }

    @Override // androidx.compose.animation.core.Animation
    public final TwoWayConverter getTypeConverter() {
        return this.typeConverter;
    }

    @Override // androidx.compose.animation.core.Animation
    public final Object getValueFromNanos(long j) {
        if (isFinishedFromNanos(j)) {
            return this.mutableTargetValue;
        }
        AnimationVector valueFromNanos = this.animationSpec.getValueFromNanos(j, this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        int size$animation_core = valueFromNanos.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            if (Float.isNaN(valueFromNanos.get$animation_core(i))) {
                PreconditionsKt.throwIllegalStateException("AnimationVector cannot contain a NaN. " + valueFromNanos + ". Animation: " + this + ", playTimeNanos: " + j);
            }
        }
        return ((TwoWayConverterImpl) this.typeConverter).convertFromVector.mo779invoke(valueFromNanos);
    }

    @Override // androidx.compose.animation.core.Animation
    public final AnimationVector getVelocityVectorFromNanos(long j) {
        if (!isFinishedFromNanos(j)) {
            return this.animationSpec.getVelocityFromNanos(j, this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        }
        AnimationVector animationVector = this._endVelocity;
        if (animationVector != null) {
            return animationVector;
        }
        AnimationVector endVelocity = this.animationSpec.getEndVelocity(this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        this._endVelocity = endVelocity;
        return endVelocity;
    }

    @Override // androidx.compose.animation.core.Animation
    public final boolean isInfinite() {
        return this.animationSpec.isInfinite();
    }

    public final void setMutableInitialValue$animation_core(Object obj) {
        if (Intrinsics.areEqual(obj, this.mutableInitialValue)) {
            return;
        }
        this.mutableInitialValue = obj;
        this.initialValueVector = (AnimationVector) ((TwoWayConverterImpl) this.typeConverter).convertToVector.mo779invoke(obj);
        this._endVelocity = null;
        this._durationNanos = -1L;
    }

    public final void setMutableTargetValue$animation_core(Object obj) {
        if (Intrinsics.areEqual(this.mutableTargetValue, obj)) {
            return;
        }
        this.mutableTargetValue = obj;
        this.targetValueVector = (AnimationVector) ((TwoWayConverterImpl) this.typeConverter).convertToVector.mo779invoke(obj);
        this._endVelocity = null;
        this._durationNanos = -1L;
    }

    public final String toString() {
        return "TargetBasedAnimation: " + this.mutableInitialValue + " -> " + this.mutableTargetValue + ",initial velocity: " + this.initialVelocityVector + ", duration: " + (getDurationNanos() / 1000000) + " ms,animationSpec: " + this.animationSpec;
    }

    public /* synthetic */ TargetBasedAnimation(VectorizedAnimationSpec vectorizedAnimationSpec, TwoWayConverter twoWayConverter, Object obj, Object obj2, AnimationVector animationVector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((VectorizedAnimationSpec<AnimationVector>) vectorizedAnimationSpec, (TwoWayConverter<Object, AnimationVector>) twoWayConverter, obj, obj2, (i & 16) != 0 ? null : animationVector);
    }

    public /* synthetic */ TargetBasedAnimation(AnimationSpec animationSpec, TwoWayConverter twoWayConverter, Object obj, Object obj2, AnimationVector animationVector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((AnimationSpec<Object>) animationSpec, (TwoWayConverter<Object, AnimationVector>) twoWayConverter, obj, obj2, (i & 16) != 0 ? null : animationVector);
    }

    public TargetBasedAnimation(AnimationSpec<T> animationSpec, TwoWayConverter<T, V> twoWayConverter, T t, T t2, V v) {
        this(animationSpec.vectorize(twoWayConverter), twoWayConverter, t, t2, v);
    }
}
