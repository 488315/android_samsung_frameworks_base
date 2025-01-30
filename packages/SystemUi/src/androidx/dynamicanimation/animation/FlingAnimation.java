package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FlingAnimation extends DynamicAnimation {
    public final DragForce mFlingForce;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DragForce {
        public float mFriction = -4.2f;
        public final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
        public float mVelocityThreshold;
    }

    public FlingAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        DragForce dragForce = new DragForce();
        this.mFlingForce = dragForce;
        dragForce.mVelocityThreshold = this.mMinVisibleChange * 0.75f * 62.5f;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final void setValueThreshold(float f) {
        this.mFlingForce.mVelocityThreshold = f * 62.5f;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0068 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0069 A[RETURN] */
    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateValueAndVelocity(long j) {
        boolean z;
        float f = this.mValue;
        float f2 = this.mVelocity;
        DragForce dragForce = this.mFlingForce;
        float exp = (float) (Math.exp((j / 1000.0f) * dragForce.mFriction) * f2);
        DynamicAnimation.MassState massState = dragForce.mMassState;
        massState.mVelocity = exp;
        massState.mValue = ((exp - f2) / dragForce.mFriction) + f;
        if (Math.abs(exp) < dragForce.mVelocityThreshold) {
            massState.mVelocity = 0.0f;
        }
        float f3 = massState.mValue;
        this.mValue = f3;
        float f4 = massState.mVelocity;
        this.mVelocity = f4;
        float f5 = this.mMinValue;
        if (f3 < f5) {
            this.mValue = f5;
            return true;
        }
        float f6 = this.mMaxValue;
        if (f3 > f6) {
            this.mValue = f6;
            return true;
        }
        if (f3 < f6 && f3 > f5) {
            if (!(Math.abs(f4) < dragForce.mVelocityThreshold)) {
                z = false;
                return !z;
            }
        }
        z = true;
        if (!z) {
        }
    }

    public <K> FlingAnimation(K k, FloatPropertyCompat floatPropertyCompat) {
        super(k, floatPropertyCompat);
        DragForce dragForce = new DragForce();
        this.mFlingForce = dragForce;
        dragForce.mVelocityThreshold = this.mMinVisibleChange * 0.75f * 62.5f;
    }
}
