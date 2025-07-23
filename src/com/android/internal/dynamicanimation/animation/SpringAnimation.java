package com.android.internal.dynamicanimation.animation;

import android.hardware.scontext.SContextConstants;
import android.util.AndroidRuntimeException;
import android.util.FloatProperty;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;

/* loaded from: classes5.dex */
public final class SpringAnimation extends DynamicAnimation<SpringAnimation> {
    private static final float UNSET = Float.MAX_VALUE;
    private boolean mEndRequested;
    private float mPendingPosition;
    private SpringForce mSpring;

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    void setValueThreshold(float f) {
    }

    public SpringAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public SpringAnimation(FloatValueHolder floatValueHolder, float f) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new SpringForce(f);
    }

    public <K> SpringAnimation(K k, FloatProperty<K> floatProperty) {
        super(k, floatProperty);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public <K> SpringAnimation(K k, FloatProperty<K> floatProperty, float f) {
        super(k, floatProperty);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new SpringForce(f);
    }

    public SpringForce getSpring() {
        return this.mSpring;
    }

    public SpringAnimation setSpring(SpringForce springForce) {
        this.mSpring = springForce;
        return this;
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    public void start() {
        sanityCheck();
        this.mSpring.setValueThreshold(getValueThreshold());
        super.start();
    }

    public void animateToFinalPosition(float f) {
        if (isRunning()) {
            this.mPendingPosition = f;
            return;
        }
        if (this.mSpring == null) {
            this.mSpring = new SpringForce(f);
        }
        this.mSpring.setFinalPosition(f);
        start();
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    public void cancel() {
        super.cancel();
        float f = this.mPendingPosition;
        if (f != Float.MAX_VALUE) {
            SpringForce springForce = this.mSpring;
            if (springForce == null) {
                this.mSpring = new SpringForce(this.mPendingPosition);
            } else {
                springForce.setFinalPosition(f);
            }
            this.mPendingPosition = Float.MAX_VALUE;
        }
    }

    public void skipToEnd() {
        if (!canSkipToEnd()) {
            throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        }
        if (!isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        if (this.mRunning) {
            this.mEndRequested = true;
        }
    }

    public boolean canSkipToEnd() {
        return this.mSpring.mDampingRatio > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    private void sanityCheck() {
        SpringForce springForce = this.mSpring;
        if (springForce == null) {
            throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
        }
        double finalPosition = springForce.getFinalPosition();
        if (finalPosition > this.mMaxValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        if (finalPosition < this.mMinValue) {
            throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
        }
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    boolean updateValueAndVelocity(long j) {
        if (this.mEndRequested) {
            float f = this.mPendingPosition;
            if (f != Float.MAX_VALUE) {
                this.mSpring.setFinalPosition(f);
                this.mPendingPosition = Float.MAX_VALUE;
            }
            this.mValue = this.mSpring.getFinalPosition();
            this.mVelocity = 0.0f;
            this.mEndRequested = false;
            return true;
        }
        if (this.mPendingPosition != Float.MAX_VALUE) {
            long j2 = j / 2;
            DynamicAnimation.MassState updateValues = this.mSpring.updateValues(this.mValue, this.mVelocity, j2);
            this.mSpring.setFinalPosition(this.mPendingPosition);
            this.mPendingPosition = Float.MAX_VALUE;
            DynamicAnimation.MassState updateValues2 = this.mSpring.updateValues(updateValues.mValue, updateValues.mVelocity, j2);
            this.mValue = updateValues2.mValue;
            this.mVelocity = updateValues2.mVelocity;
        } else {
            DynamicAnimation.MassState updateValues3 = this.mSpring.updateValues(this.mValue, this.mVelocity, j);
            this.mValue = updateValues3.mValue;
            this.mVelocity = updateValues3.mVelocity;
        }
        this.mValue = Math.max(this.mValue, this.mMinValue);
        this.mValue = Math.min(this.mValue, this.mMaxValue);
        if (!isAtEquilibrium(this.mValue, this.mVelocity)) {
            return false;
        }
        this.mValue = this.mSpring.getFinalPosition();
        this.mVelocity = 0.0f;
        return true;
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    float getAcceleration(float f, float f2) {
        return this.mSpring.getAcceleration(f, f2);
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    boolean isAtEquilibrium(float f, float f2) {
        return this.mSpring.isAtEquilibrium(f, f2);
    }
}
