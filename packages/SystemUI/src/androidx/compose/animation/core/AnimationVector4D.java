package androidx.compose.animation.core;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimationVector4D extends AnimationVector {
    public final int size;
    public float v1;
    public float v2;
    public float v3;
    public float v4;

    public AnimationVector4D(float f, float f2, float f3, float f4) {
        super(null);
        this.v1 = f;
        this.v2 = f2;
        this.v3 = f3;
        this.v4 = f4;
        this.size = 4;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AnimationVector4D)) {
            return false;
        }
        AnimationVector4D animationVector4D = (AnimationVector4D) obj;
        return animationVector4D.v1 == this.v1 && animationVector4D.v2 == this.v2 && animationVector4D.v3 == this.v3 && animationVector4D.v4 == this.v4;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final float get$animation_core(int i) {
        if (i == 0) {
            return this.v1;
        }
        if (i == 1) {
            return this.v2;
        }
        if (i == 2) {
            return this.v3;
        }
        if (i != 3) {
            return 0.0f;
        }
        return this.v4;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final int getSize$animation_core() {
        return this.size;
    }

    public final int hashCode() {
        return Float.hashCode(this.v4) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.v3, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.v2, Float.hashCode(this.v1) * 31, 31), 31);
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final AnimationVector newVector$animation_core() {
        return new AnimationVector4D(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final void reset$animation_core() {
        this.v1 = 0.0f;
        this.v2 = 0.0f;
        this.v3 = 0.0f;
        this.v4 = 0.0f;
    }

    @Override // androidx.compose.animation.core.AnimationVector
    public final void set$animation_core(float f, int i) {
        if (i == 0) {
            this.v1 = f;
            return;
        }
        if (i == 1) {
            this.v2 = f;
        } else if (i == 2) {
            this.v3 = f;
        } else {
            if (i != 3) {
                return;
            }
            this.v4 = f;
        }
    }

    public final String toString() {
        return "AnimationVector4D: v1 = " + this.v1 + ", v2 = " + this.v2 + ", v3 = " + this.v3 + ", v4 = " + this.v4;
    }
}
