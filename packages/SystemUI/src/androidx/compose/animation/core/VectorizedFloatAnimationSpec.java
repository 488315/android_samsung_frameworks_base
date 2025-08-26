package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;

/* loaded from: classes.dex */
public final class VectorizedFloatAnimationSpec<V extends AnimationVector> implements VectorizedFiniteAnimationSpec<V> {
    public final Animations anims;
    public AnimationVector endVelocityVector;
    public AnimationVector valueVector;
    public AnimationVector velocityVector;

    public VectorizedFloatAnimationSpec(Animations animations) {
        this.anims = animations;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        int size$animation_core = animationVector.getSize$animation_core();
        long jMax = 0;
        for (int i = 0; i < size$animation_core; i++) {
            jMax = Math.max(jMax, this.anims.get(i).getDurationNanos(animationVector.get$animation_core(i), animationVector2.get$animation_core(i), animationVector3.get$animation_core(i)));
        }
        return jMax;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getEndVelocity(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        if (this.endVelocityVector == null) {
            this.endVelocityVector = animationVector3.newVector$animation_core();
        }
        AnimationVector animationVector4 = this.endVelocityVector;
        if (animationVector4 == null) {
            animationVector4 = null;
        }
        int size$animation_core = animationVector4.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            AnimationVector animationVector5 = this.endVelocityVector;
            if (animationVector5 == null) {
                animationVector5 = null;
            }
            animationVector5.set$animation_core(this.anims.get(i).getEndVelocity(animationVector.get$animation_core(i), animationVector2.get$animation_core(i), animationVector3.get$animation_core(i)), i);
        }
        AnimationVector animationVector6 = this.endVelocityVector;
        if (animationVector6 == null) {
            return null;
        }
        return animationVector6;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        if (this.valueVector == null) {
            this.valueVector = animationVector.newVector$animation_core();
        }
        AnimationVector animationVector4 = this.valueVector;
        if (animationVector4 == null) {
            animationVector4 = null;
        }
        int size$animation_core = animationVector4.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            AnimationVector animationVector5 = this.valueVector;
            if (animationVector5 == null) {
                animationVector5 = null;
            }
            animationVector5.set$animation_core(this.anims.get(i).getValueFromNanos(j, animationVector.get$animation_core(i), animationVector2.get$animation_core(i), animationVector3.get$animation_core(i)), i);
        }
        AnimationVector animationVector6 = this.valueVector;
        if (animationVector6 == null) {
            return null;
        }
        return animationVector6;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        if (this.velocityVector == null) {
            this.velocityVector = animationVector3.newVector$animation_core();
        }
        AnimationVector animationVector4 = this.velocityVector;
        if (animationVector4 == null) {
            animationVector4 = null;
        }
        int size$animation_core = animationVector4.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            AnimationVector animationVector5 = this.velocityVector;
            if (animationVector5 == null) {
                animationVector5 = null;
            }
            animationVector5.set$animation_core(this.anims.get(i).getVelocityFromNanos(j, animationVector.get$animation_core(i), animationVector2.get$animation_core(i), animationVector3.get$animation_core(i)), i);
        }
        AnimationVector animationVector6 = this.velocityVector;
        if (animationVector6 == null) {
            return null;
        }
        return animationVector6;
    }

    public VectorizedFloatAnimationSpec(final FloatAnimationSpec floatAnimationSpec) {
        this(new Animations() { // from class: androidx.compose.animation.core.VectorizedFloatAnimationSpec.1
            @Override // androidx.compose.animation.core.Animations
            public final FloatAnimationSpec get(int i) {
                return floatAnimationSpec;
            }
        });
    }
}
