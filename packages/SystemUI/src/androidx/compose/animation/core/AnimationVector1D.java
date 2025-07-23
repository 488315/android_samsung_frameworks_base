package androidx.compose.animation.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimationVector1D extends AnimationVector {
    public final int size;
    public float value;

    public AnimationVector1D(float f) {
        super(null);
        this.value = f;
        this.size = 1;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof AnimationVector1D) && ((AnimationVector1D) obj).value == this.value;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final float get$animation_core(int i) {
        if (i == 0) {
            return this.value;
        }
        return 0.0f;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final int getSize$animation_core() {
        return this.size;
    }

    public final int hashCode() {
        return Float.hashCode(this.value);
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final AnimationVector newVector$animation_core() {
        return new AnimationVector1D(0.0f);
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final void reset$animation_core() {
        this.value = 0.0f;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final void set$animation_core(float f, int i) {
        if (i == 0) {
            this.value = f;
        }
    }

    public final String toString() {
        return "AnimationVector1D: value = " + this.value;
    }
}
