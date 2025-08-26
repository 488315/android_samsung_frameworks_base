package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.AnimationVector3D;
import androidx.compose.animation.core.AnimationVector4D;
import androidx.compose.animation.core.VectorizedFiniteAnimationSpec;

/* loaded from: classes.dex */
final class VectorizedReversedSpec<V extends AnimationVector> implements VectorizedFiniteAnimationSpec<V> {
    public final VectorizedFiniteAnimationSpec animation;
    public final long durationNanos;

    public VectorizedReversedSpec(VectorizedFiniteAnimationSpec<V> vectorizedFiniteAnimationSpec, long j) {
        this.animation = vectorizedFiniteAnimationSpec;
        this.durationNanos = j;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.durationNanos;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.animation.getValueFromNanos(this.durationNanos - j, animationVector2, animationVector, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        AnimationVector velocityFromNanos = this.animation.getVelocityFromNanos(this.durationNanos - j, animationVector2, animationVector, animationVector3);
        if (velocityFromNanos instanceof AnimationVector1D) {
            return new AnimationVector1D(((AnimationVector1D) velocityFromNanos).value * (-1));
        }
        if (velocityFromNanos instanceof AnimationVector2D) {
            AnimationVector2D animationVector2D = (AnimationVector2D) velocityFromNanos;
            float f = -1;
            return new AnimationVector2D(animationVector2D.v1 * f, animationVector2D.v2 * f);
        }
        if (velocityFromNanos instanceof AnimationVector3D) {
            AnimationVector3D animationVector3D = (AnimationVector3D) velocityFromNanos;
            float f2 = -1;
            return new AnimationVector3D(animationVector3D.v1 * f2, animationVector3D.v2 * f2, animationVector3D.v3 * f2);
        }
        if (velocityFromNanos instanceof AnimationVector4D) {
            AnimationVector4D animationVector4D = (AnimationVector4D) velocityFromNanos;
            float f3 = -1;
            return new AnimationVector4D(animationVector4D.v1 * f3, animationVector4D.v2 * f3, animationVector4D.v3 * f3, animationVector4D.v4 * f3);
        }
        throw new RuntimeException("Unknown AnimationVector: " + velocityFromNanos);
    }
}
