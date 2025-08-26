package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;

/* loaded from: classes.dex */
final class VectorizedFloatDecaySpec<V extends AnimationVector> implements VectorizedDecayAnimationSpec<V> {
    public final float absVelocityThreshold;
    public final FloatDecayAnimationSpec floatDecaySpec;
    public AnimationVector targetVector;
    public AnimationVector valueVector;
    public AnimationVector velocityVector;

    public VectorizedFloatDecaySpec(FloatDecayAnimationSpec floatDecayAnimationSpec) {
        this.floatDecaySpec = floatDecayAnimationSpec;
        this.absVelocityThreshold = floatDecayAnimationSpec.getAbsVelocityThreshold();
    }

    public final AnimationVector getTargetValue(AnimationVector animationVector, AnimationVector animationVector2) {
        if (this.targetVector == null) {
            this.targetVector = animationVector.newVector$animation_core();
        }
        AnimationVector animationVector3 = this.targetVector;
        if (animationVector3 == null) {
            animationVector3 = null;
        }
        int size$animation_core = animationVector3.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            AnimationVector animationVector4 = this.targetVector;
            if (animationVector4 == null) {
                animationVector4 = null;
            }
            animationVector4.set$animation_core(this.floatDecaySpec.getTargetValue(animationVector.get$animation_core(i), animationVector2.get$animation_core(i)), i);
        }
        AnimationVector animationVector5 = this.targetVector;
        if (animationVector5 == null) {
            return null;
        }
        return animationVector5;
    }

    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2) {
        if (this.velocityVector == null) {
            this.velocityVector = animationVector.newVector$animation_core();
        }
        AnimationVector animationVector3 = this.velocityVector;
        if (animationVector3 == null) {
            animationVector3 = null;
        }
        int size$animation_core = animationVector3.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            AnimationVector animationVector4 = this.velocityVector;
            if (animationVector4 == null) {
                animationVector4 = null;
            }
            animationVector.getClass();
            animationVector4.set$animation_core(this.floatDecaySpec.getVelocityFromNanos(animationVector2.get$animation_core(i), j), i);
        }
        AnimationVector animationVector5 = this.velocityVector;
        if (animationVector5 == null) {
            return null;
        }
        return animationVector5;
    }
}
