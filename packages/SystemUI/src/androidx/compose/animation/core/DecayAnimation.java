package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DecayAnimation<T, V extends AnimationVector> implements Animation<T, V> {
    public final VectorizedDecayAnimationSpec animationSpec;
    public final long durationNanos;
    public final AnimationVector endVelocity;
    public final Object initialValue;
    public final AnimationVector initialValueVector;
    public final AnimationVector initialVelocityVector;
    public final Object targetValue;
    public final TwoWayConverter typeConverter;

    public DecayAnimation(VectorizedDecayAnimationSpec<V> vectorizedDecayAnimationSpec, TwoWayConverter<T, V> twoWayConverter, T t, V v) {
        this.animationSpec = vectorizedDecayAnimationSpec;
        this.typeConverter = twoWayConverter;
        this.initialValue = t;
        TwoWayConverterImpl twoWayConverterImpl = (TwoWayConverterImpl) twoWayConverter;
        AnimationVector animationVector = (AnimationVector) twoWayConverterImpl.convertToVector.mo779invoke(t);
        this.initialValueVector = animationVector;
        this.initialVelocityVector = AnimationVectorsKt.copy(v);
        VectorizedFloatDecaySpec vectorizedFloatDecaySpec = (VectorizedFloatDecaySpec) vectorizedDecayAnimationSpec;
        this.targetValue = twoWayConverterImpl.convertFromVector.mo779invoke(vectorizedFloatDecaySpec.getTargetValue(animationVector, v));
        if (vectorizedFloatDecaySpec.velocityVector == null) {
            vectorizedFloatDecaySpec.velocityVector = animationVector.newVector$animation_core();
        }
        AnimationVector animationVector2 = vectorizedFloatDecaySpec.velocityVector;
        int size$animation_core = (animationVector2 == null ? null : animationVector2).getSize$animation_core();
        long j = 0;
        for (int i = 0; i < size$animation_core; i++) {
            animationVector.getClass();
            j = Math.max(j, vectorizedFloatDecaySpec.floatDecaySpec.getDurationNanos(v.get$animation_core(i)));
        }
        this.durationNanos = j;
        AnimationVector copy = AnimationVectorsKt.copy(((VectorizedFloatDecaySpec) this.animationSpec).getVelocityFromNanos(j, this.initialValueVector, v));
        this.endVelocity = copy;
        int size$animation_core2 = copy.getSize$animation_core();
        for (int i2 = 0; i2 < size$animation_core2; i2++) {
            AnimationVector animationVector3 = this.endVelocity;
            float f = animationVector3.get$animation_core(i2);
            float f2 = ((VectorizedFloatDecaySpec) this.animationSpec).absVelocityThreshold;
            animationVector3.set$animation_core(RangesKt___RangesKt.coerceIn(f, -f2, f2), i2);
        }
    }

    @Override // androidx.compose.animation.core.Animation
    public final long getDurationNanos() {
        return this.durationNanos;
    }

    @Override // androidx.compose.animation.core.Animation
    public final Object getTargetValue() {
        return this.targetValue;
    }

    @Override // androidx.compose.animation.core.Animation
    public final TwoWayConverter getTypeConverter() {
        return this.typeConverter;
    }

    @Override // androidx.compose.animation.core.Animation
    public final Object getValueFromNanos(long j) {
        if (isFinishedFromNanos(j)) {
            return this.targetValue;
        }
        Function1 function1 = ((TwoWayConverterImpl) this.typeConverter).convertFromVector;
        VectorizedFloatDecaySpec vectorizedFloatDecaySpec = (VectorizedFloatDecaySpec) this.animationSpec;
        AnimationVector animationVector = vectorizedFloatDecaySpec.valueVector;
        AnimationVector animationVector2 = this.initialValueVector;
        if (animationVector == null) {
            vectorizedFloatDecaySpec.valueVector = animationVector2.newVector$animation_core();
        }
        AnimationVector animationVector3 = vectorizedFloatDecaySpec.valueVector;
        if (animationVector3 == null) {
            animationVector3 = null;
        }
        int size$animation_core = animationVector3.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            AnimationVector animationVector4 = vectorizedFloatDecaySpec.valueVector;
            if (animationVector4 == null) {
                animationVector4 = null;
            }
            animationVector4.set$animation_core(vectorizedFloatDecaySpec.floatDecaySpec.getValueFromNanos(animationVector2.get$animation_core(i), this.initialVelocityVector.get$animation_core(i), j), i);
        }
        AnimationVector animationVector5 = vectorizedFloatDecaySpec.valueVector;
        return function1.mo779invoke(animationVector5 != null ? animationVector5 : null);
    }

    @Override // androidx.compose.animation.core.Animation
    public final AnimationVector getVelocityVectorFromNanos(long j) {
        if (isFinishedFromNanos(j)) {
            return this.endVelocity;
        }
        return ((VectorizedFloatDecaySpec) this.animationSpec).getVelocityFromNanos(j, this.initialValueVector, this.initialVelocityVector);
    }

    @Override // androidx.compose.animation.core.Animation
    public final boolean isInfinite() {
        return false;
    }

    public DecayAnimation(DecayAnimationSpec<T> decayAnimationSpec, TwoWayConverter<T, V> twoWayConverter, T t, V v) {
        this(new VectorizedFloatDecaySpec(((DecayAnimationSpecImpl) decayAnimationSpec).floatDecaySpec), twoWayConverter, t, v);
    }

    public DecayAnimation(DecayAnimationSpec<T> decayAnimationSpec, TwoWayConverter<T, V> twoWayConverter, T t, T t2) {
        this(new VectorizedFloatDecaySpec(((DecayAnimationSpecImpl) decayAnimationSpec).floatDecaySpec), twoWayConverter, t, (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo779invoke(t2));
    }
}
