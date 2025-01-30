package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SplitDimensionPathKeyframeAnimation extends BaseKeyframeAnimation {
    public final PointF point;
    public final PointF pointWithCallbackValues;
    public final BaseKeyframeAnimation xAnimation;
    public LottieValueCallback xValueCallback;
    public final BaseKeyframeAnimation yAnimation;
    public LottieValueCallback yValueCallback;

    public SplitDimensionPathKeyframeAnimation(BaseKeyframeAnimation baseKeyframeAnimation, BaseKeyframeAnimation baseKeyframeAnimation2) {
        super(Collections.emptyList());
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
        int i = 0;
        while (true) {
            List list = this.listeners;
            if (i >= ((ArrayList) list).size()) {
                return;
            }
            ((BaseKeyframeAnimation.AnimationListener) ((ArrayList) list).get(i)).onValueChanged();
            i++;
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue() {
        return getValue(0.0f);
    }

    public final PointF getValue(float f) {
        Float f2;
        BaseKeyframeAnimation baseKeyframeAnimation;
        Keyframe currentKeyframe;
        BaseKeyframeAnimation baseKeyframeAnimation2;
        Keyframe currentKeyframe2;
        Float f3 = null;
        if (this.xValueCallback == null || (currentKeyframe2 = (baseKeyframeAnimation2 = this.xAnimation).getCurrentKeyframe()) == null) {
            f2 = null;
        } else {
            float interpolatedCurrentKeyframeProgress = baseKeyframeAnimation2.getInterpolatedCurrentKeyframeProgress();
            Float f4 = currentKeyframe2.endFrame;
            LottieValueCallback lottieValueCallback = this.xValueCallback;
            float f5 = currentKeyframe2.startFrame;
            f2 = (Float) lottieValueCallback.getValueInternal(f5, f4 == null ? f5 : f4.floatValue(), (Float) currentKeyframe2.startValue, (Float) currentKeyframe2.endValue, f, f, interpolatedCurrentKeyframeProgress);
        }
        if (this.yValueCallback != null && (currentKeyframe = (baseKeyframeAnimation = this.yAnimation).getCurrentKeyframe()) != null) {
            float interpolatedCurrentKeyframeProgress2 = baseKeyframeAnimation.getInterpolatedCurrentKeyframeProgress();
            Float f6 = currentKeyframe.endFrame;
            LottieValueCallback lottieValueCallback2 = this.yValueCallback;
            float f7 = currentKeyframe.startFrame;
            f3 = (Float) lottieValueCallback2.getValueInternal(f7, f6 == null ? f7 : f6.floatValue(), (Float) currentKeyframe.startValue, (Float) currentKeyframe.endValue, f, f, interpolatedCurrentKeyframeProgress2);
        }
        PointF pointF = this.point;
        PointF pointF2 = this.pointWithCallbackValues;
        if (f2 == null) {
            pointF2.set(pointF.x, 0.0f);
        } else {
            pointF2.set(f2.floatValue(), 0.0f);
        }
        if (f3 == null) {
            pointF2.set(pointF2.x, pointF.y);
        } else {
            pointF2.set(pointF2.x, f3.floatValue());
        }
        return pointF2;
    }
}
