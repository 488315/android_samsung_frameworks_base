package androidx.dynamicanimation.animation;

import android.util.AndroidRuntimeException;
import androidx.dynamicanimation.animation.DynamicAnimation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SpringAnimation extends DynamicAnimation {
    public boolean mEndRequested;
    public float mPendingPosition;
    public SpringForce mSpring;

    public SpringAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public final void animateToFinalPosition(float f) {
        if (this.mRunning) {
            this.mPendingPosition = f;
            return;
        }
        if (this.mSpring == null) {
            this.mSpring = new SpringForce(f);
        }
        this.mSpring.mFinalPosition = f;
        start();
    }

    public final boolean canSkipToEnd() {
        return this.mSpring.mDampingRatio > 0.0d;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final void cancel() {
        super.cancel();
        float f = this.mPendingPosition;
        if (f != Float.MAX_VALUE) {
            SpringForce springForce = this.mSpring;
            if (springForce == null) {
                this.mSpring = new SpringForce(f);
            } else {
                springForce.mFinalPosition = f;
            }
            this.mPendingPosition = Float.MAX_VALUE;
        }
    }

    public final void skipToEnd() {
        if (!canSkipToEnd()) {
            throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        }
        if (!DynamicAnimation.getAnimationHandler().mScheduler.isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        if (this.mRunning) {
            this.mEndRequested = true;
        }
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final void start() {
        SpringForce springForce = this.mSpring;
        if (springForce == null) {
            throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
        }
        double d = (float) springForce.mFinalPosition;
        if (d > this.mMaxValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        if (d < this.mMinValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
        }
        double d2 = this.mMinVisibleChange * 0.75f;
        springForce.getClass();
        double abs = Math.abs(d2);
        springForce.mValueThreshold = abs;
        springForce.mVelocityThreshold = abs * 62.5d;
        super.start();
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final boolean updateValueAndVelocity(long j) {
        if (this.mEndRequested) {
            float f = this.mPendingPosition;
            if (f != Float.MAX_VALUE) {
                this.mSpring.mFinalPosition = f;
                this.mPendingPosition = Float.MAX_VALUE;
            }
            this.mValue = (float) this.mSpring.mFinalPosition;
            this.mVelocity = 0.0f;
            this.mEndRequested = false;
            return true;
        }
        if (this.mPendingPosition != Float.MAX_VALUE) {
            long j2 = j / 2;
            DynamicAnimation.MassState updateValues = this.mSpring.updateValues(this.mValue, this.mVelocity, j2);
            SpringForce springForce = this.mSpring;
            springForce.mFinalPosition = this.mPendingPosition;
            this.mPendingPosition = Float.MAX_VALUE;
            DynamicAnimation.MassState updateValues2 = springForce.updateValues(updateValues.mValue, updateValues.mVelocity, j2);
            this.mValue = updateValues2.mValue;
            this.mVelocity = updateValues2.mVelocity;
        } else {
            DynamicAnimation.MassState updateValues3 = this.mSpring.updateValues(this.mValue, this.mVelocity, j);
            this.mValue = updateValues3.mValue;
            this.mVelocity = updateValues3.mVelocity;
        }
        float max = Math.max(this.mValue, this.mMinValue);
        this.mValue = max;
        float min = Math.min(max, this.mMaxValue);
        this.mValue = min;
        float f2 = this.mVelocity;
        SpringForce springForce2 = this.mSpring;
        springForce2.getClass();
        if (!(((double) Math.abs(f2)) < springForce2.mVelocityThreshold && ((double) Math.abs(min - ((float) springForce2.mFinalPosition))) < springForce2.mValueThreshold)) {
            return false;
        }
        this.mValue = (float) this.mSpring.mFinalPosition;
        this.mVelocity = 0.0f;
        return true;
    }

    public SpringAnimation(FloatValueHolder floatValueHolder, float f) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new SpringForce(f);
    }

    public <K> SpringAnimation(K k, FloatPropertyCompat floatPropertyCompat) {
        super(k, floatPropertyCompat);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public <K> SpringAnimation(K k, FloatPropertyCompat floatPropertyCompat, float f) {
        super(k, floatPropertyCompat);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new SpringForce(f);
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation
    public final void setValueThreshold(float f) {
    }
}
