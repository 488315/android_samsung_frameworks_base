package androidx.compose.animation.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimationVectorsKt {
    public static final AnimationVector copy(AnimationVector animationVector) {
        AnimationVector newVector$animation_core = animationVector.newVector$animation_core();
        int size$animation_core = newVector$animation_core.getSize$animation_core();
        for (int i = 0; i < size$animation_core; i++) {
            newVector$animation_core.set$animation_core(animationVector.get$animation_core(i), i);
        }
        return newVector$animation_core;
    }
}
