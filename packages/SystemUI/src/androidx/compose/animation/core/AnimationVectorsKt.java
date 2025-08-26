package androidx.compose.animation.core;

/* loaded from: classes.dex */
public abstract class AnimationVectorsKt {
    public static final AnimationVector copy(AnimationVector animationVector) {
        AnimationVector animationVectorNewVector$animation_core = animationVector.newVector$animation_core();
        int size$animation_core = animationVectorNewVector$animation_core.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            animationVectorNewVector$animation_core.set$animation_core(animationVector.get$animation_core(i), i);
        }
        return animationVectorNewVector$animation_core;
    }
}
