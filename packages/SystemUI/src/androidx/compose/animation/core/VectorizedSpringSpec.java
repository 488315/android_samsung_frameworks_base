package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class VectorizedSpringSpec<V extends AnimationVector> implements VectorizedFiniteAnimationSpec<V> {
    public final /* synthetic */ VectorizedFloatAnimationSpec $$delegate_0;

    private VectorizedSpringSpec(float f, float f2, Animations animations) {
        this.$$delegate_0 = new VectorizedFloatAnimationSpec(animations);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getDurationNanos(animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getEndVelocity(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getEndVelocity(animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getValueFromNanos(j, animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public final AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.$$delegate_0.getVelocityFromNanos(j, animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedFiniteAnimationSpec, androidx.compose.animation.core.VectorizedAnimationSpec
    public final boolean isInfinite() {
        this.$$delegate_0.getClass();
        return false;
    }

    public /* synthetic */ VectorizedSpringSpec(float f, float f2, AnimationVector animationVector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, (i & 2) != 0 ? 1500.0f : f2, (i & 4) != 0 ? null : animationVector);
    }

    public VectorizedSpringSpec(final float f, final float f2, final V v) {
        Animations animations;
        int[] iArr = VectorizedAnimationSpecKt.EmptyIntArray;
        if (v != null) {
            animations = new Animations(v, f, f2) { // from class: androidx.compose.animation.core.VectorizedAnimationSpecKt$createSpringAnimations$1
                public final FloatSpringSpec[] anims;

                {
                    int size$animation_core = v.getSize$animation_core();
                    FloatSpringSpec[] floatSpringSpecArr = new FloatSpringSpec[size$animation_core];
                    for (int i = 0; i < size$animation_core; i++) {
                        floatSpringSpecArr[i] = new FloatSpringSpec(f, f2, v.get$animation_core(i));
                    }
                    this.anims = floatSpringSpecArr;
                }

                @Override // androidx.compose.animation.core.Animations
                public final FloatAnimationSpec get(int i) {
                    return this.anims[i];
                }
            };
        } else {
            animations = new Animations(f, f2) { // from class: androidx.compose.animation.core.VectorizedAnimationSpecKt$createSpringAnimations$2
                public final FloatSpringSpec anim;

                {
                    this.anim = new FloatSpringSpec(f, f2, 0.0f, 4, null);
                }

                @Override // androidx.compose.animation.core.Animations
                public final FloatAnimationSpec get(int i) {
                    return this.anim;
                }
            };
        }
        this(f, f2, animations);
    }
}
