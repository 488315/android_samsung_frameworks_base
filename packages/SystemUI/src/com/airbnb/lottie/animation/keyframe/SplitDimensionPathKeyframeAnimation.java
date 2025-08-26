package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes.dex */
public class SplitDimensionPathKeyframeAnimation extends BaseKeyframeAnimation {
    public final PointF point;
    public final PointF pointWithCallbackValues;
    public final BaseKeyframeAnimation xAnimation;
    public LottieValueCallback xValueCallback;
    public final BaseKeyframeAnimation yAnimation;
    public LottieValueCallback yValueCallback;

    public SplitDimensionPathKeyframeAnimation(BaseKeyframeAnimation baseKeyframeAnimation, BaseKeyframeAnimation baseKeyframeAnimation2) {
        super(Collections.EMPTY_LIST);
        this.point = new PointF();
        this.pointWithCallbackValues = new PointF();
        this.xAnimation = baseKeyframeAnimation;
        this.yAnimation = baseKeyframeAnimation2;
        setProgress(this.progress);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final /* bridge */ /* synthetic */ Object getValue(Keyframe keyframe, float f) {
        return getValue(f);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final void setProgress(float f) {
        BaseKeyframeAnimation baseKeyframeAnimation = this.xAnimation;
        baseKeyframeAnimation.setProgress(f);
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.yAnimation;
        baseKeyframeAnimation2.setProgress(f);
        this.point.set(((Float) baseKeyframeAnimation.getValue()).floatValue(), ((Float) baseKeyframeAnimation2.getValue()).floatValue());
        for (int i = 0; i < ((ArrayList) this.listeners).size(); i++) {
            ((BaseKeyframeAnimation.AnimationListener) ((ArrayList) this.listeners).get(i)).onValueChanged();
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue() {
        return getValue(0.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PointF getValue(float f) {
        float f2;
        Float f3;
        Float f4 = null;
        if (this.xValueCallback != null) {
            BaseKeyframeAnimation baseKeyframeAnimation = this.xAnimation;
            Keyframe currentKeyframe = baseKeyframeAnimation.keyframesWrapper.getCurrentKeyframe();
            if (currentKeyframe != null) {
                float interpolatedCurrentKeyframeProgress = baseKeyframeAnimation.getInterpolatedCurrentKeyframeProgress();
                Float f5 = currentKeyframe.endFrame;
                LottieValueCallback lottieValueCallback = this.xValueCallback;
                float f6 = currentKeyframe.startFrame;
                f2 = f;
                f3 = (Float) lottieValueCallback.getValueInternal(f6, f5 == null ? f6 : f5.floatValue(), (Float) currentKeyframe.startValue, (Float) currentKeyframe.endValue, f, f, interpolatedCurrentKeyframeProgress);
            } else {
                f2 = f;
                f3 = null;
            }
        }
        if (this.yValueCallback != null) {
            BaseKeyframeAnimation baseKeyframeAnimation2 = this.yAnimation;
            Keyframe currentKeyframe2 = baseKeyframeAnimation2.keyframesWrapper.getCurrentKeyframe();
            if (currentKeyframe2 != null) {
                float interpolatedCurrentKeyframeProgress2 = baseKeyframeAnimation2.getInterpolatedCurrentKeyframeProgress();
                Float f7 = currentKeyframe2.endFrame;
                LottieValueCallback lottieValueCallback2 = this.yValueCallback;
                float f8 = currentKeyframe2.startFrame;
                f4 = (Float) lottieValueCallback2.getValueInternal(f8, f7 == null ? f8 : f7.floatValue(), (Float) currentKeyframe2.startValue, (Float) currentKeyframe2.endValue, f2, f2, interpolatedCurrentKeyframeProgress2);
            }
        }
        if (f3 == null) {
            this.pointWithCallbackValues.set(this.point.x, 0.0f);
        } else {
            this.pointWithCallbackValues.set(f3.floatValue(), 0.0f);
        }
        if (f4 == null) {
            PointF pointF = this.pointWithCallbackValues;
            pointF.set(pointF.x, this.point.y);
        } else {
            PointF pointF2 = this.pointWithCallbackValues;
            pointF2.set(pointF2.x, f4.floatValue());
        }
        return this.pointWithCallbackValues;
    }
}
