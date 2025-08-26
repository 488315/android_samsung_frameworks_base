package com.android.internal.widget.remotecompose.core.operations.layout;

import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.utilities.easing.FloatAnimation;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;

/* loaded from: classes6.dex */
public class AnimatableValue implements Serializable {
    boolean mAnimate;
    float mAnimateDuration;
    long mAnimateTargetTime;
    boolean mAnimateValueChanges;
    int mId;
    boolean mIsVariable;
    long mLastUpdate;
    FloatAnimation mMotionEasing;
    int mMotionEasingType;
    float mStartRotationX;
    float mTargetRotationX;
    float mValue;

    public AnimatableValue(float f, boolean z) {
        this.mIsVariable = false;
        this.mId = 0;
        this.mValue = 0.0f;
        this.mAnimate = false;
        this.mAnimateTargetTime = 0L;
        this.mAnimateDuration = 300.0f;
        this.mLastUpdate = 0L;
        this.mMotionEasingType = 1;
        this.mAnimateValueChanges = z;
        if (Utils.isVariable(f)) {
            this.mId = Utils.idFromNan(f);
            this.mIsVariable = true;
        } else {
            this.mValue = f;
        }
    }

    public AnimatableValue(float f) {
        this(f, true);
    }

    public float getValue() {
        return this.mValue;
    }

    public float evaluate(PaintContext paintContext) {
        if (!this.mIsVariable) {
            return this.mValue;
        }
        float f = paintContext.getContext().mRemoteComposeState.getFloat(this.mId);
        if (f != this.mValue) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = this.mLastUpdate;
            if (jCurrentTimeMillis - j > this.mAnimateDuration && j != 0) {
                this.mAnimateValueChanges = true;
            } else {
                this.mAnimateValueChanges = false;
            }
            this.mLastUpdate = jCurrentTimeMillis;
        }
        if (!this.mAnimateValueChanges) {
            this.mValue = f;
        } else {
            float f2 = this.mValue;
            if (f != f2 && !this.mAnimate) {
                this.mStartRotationX = f2;
                this.mTargetRotationX = f;
                this.mAnimate = true;
                this.mAnimateTargetTime = System.currentTimeMillis();
                FloatAnimation floatAnimation = new FloatAnimation(this.mMotionEasingType, this.mAnimateDuration / 1000.0f, null, 0.0f, Float.NaN);
                this.mMotionEasing = floatAnimation;
                floatAnimation.setTargetValue(1.0f);
            }
            if (this.mAnimate) {
                float f3 = this.mMotionEasing.get((System.currentTimeMillis() - this.mAnimateTargetTime) / this.mAnimateDuration);
                this.mValue = ((1.0f - f3) * this.mStartRotationX) + (this.mTargetRotationX * f3);
                if (f3 >= 1.0f) {
                    this.mAnimate = false;
                }
            } else {
                this.mValue = this.mTargetRotationX;
            }
        }
        return this.mValue;
    }

    public String toString() {
        return "AnimatableValue{mId=" + this.mId + "}";
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType("AnimatableValue").add("id", Integer.valueOf(this.mId));
    }
}
