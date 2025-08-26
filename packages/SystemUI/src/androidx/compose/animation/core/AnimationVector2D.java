package androidx.compose.animation.core;

/* loaded from: classes.dex */
public final class AnimationVector2D extends AnimationVector {
    public final int size;
    public float v1;
    public float v2;

    public AnimationVector2D(float f, float f2) {
        super(null);
        this.v1 = f;
        this.v2 = f2;
        this.size = 2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AnimationVector2D)) {
            return false;
        }
        AnimationVector2D animationVector2D = (AnimationVector2D) obj;
        return animationVector2D.v1 == this.v1 && animationVector2D.v2 == this.v2;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final float get$animation_core(int i) {
        if (i == 0) {
            return this.v1;
        }
        if (i != 1) {
            return 0.0f;
        }
        return this.v2;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final int getSize$animation_core() {
        return this.size;
    }

    public final int hashCode() {
        return Float.hashCode(this.v2) + (Float.hashCode(this.v1) * 31);
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final AnimationVector newVector$animation_core() {
        return new AnimationVector2D(0.0f, 0.0f);
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final void reset$animation_core() {
        this.v1 = 0.0f;
        this.v2 = 0.0f;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final void set$animation_core(float f, int i) {
        if (i == 0) {
            this.v1 = f;
        } else {
            if (i != 1) {
                return;
            }
            this.v2 = f;
        }
    }

    public final String toString() {
        return "AnimationVector2D: v1 = " + this.v1 + ", v2 = " + this.v2;
    }
}
