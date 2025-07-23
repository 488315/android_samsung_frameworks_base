package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.PointF getValue(float r12) {
        /*
            r11 = this;
            com.airbnb.lottie.value.LottieValueCallback r0 = r11.xValueCallback
            r1 = 0
            if (r0 == 0) goto L36
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r0 = r11.xAnimation
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation$KeyframesWrapper r2 = r0.keyframesWrapper
            com.airbnb.lottie.value.Keyframe r2 = r2.getCurrentKeyframe()
            if (r2 == 0) goto L36
            float r10 = r0.getInterpolatedCurrentKeyframeProgress()
            java.lang.Float r0 = r2.endFrame
            com.airbnb.lottie.value.LottieValueCallback r3 = r11.xValueCallback
            float r4 = r2.startFrame
            if (r0 != 0) goto L1d
            r5 = r4
            goto L22
        L1d:
            float r0 = r0.floatValue()
            r5 = r0
        L22:
            java.lang.Object r0 = r2.startValue
            r6 = r0
            java.lang.Float r6 = (java.lang.Float) r6
            java.lang.Object r0 = r2.endValue
            r7 = r0
            java.lang.Float r7 = (java.lang.Float) r7
            r9 = r12
            r8 = r12
            java.lang.Object r12 = r3.getValueInternal(r4, r5, r6, r7, r8, r9, r10)
            r7 = r8
            java.lang.Float r12 = (java.lang.Float) r12
            goto L38
        L36:
            r7 = r12
            r12 = r1
        L38:
            com.airbnb.lottie.value.LottieValueCallback r0 = r11.yValueCallback
            if (r0 == 0) goto L6c
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r0 = r11.yAnimation
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation$KeyframesWrapper r2 = r0.keyframesWrapper
            com.airbnb.lottie.value.Keyframe r2 = r2.getCurrentKeyframe()
            if (r2 == 0) goto L6c
            float r9 = r0.getInterpolatedCurrentKeyframeProgress()
            java.lang.Float r0 = r2.endFrame
            r1 = r2
            com.airbnb.lottie.value.LottieValueCallback r2 = r11.yValueCallback
            float r3 = r1.startFrame
            if (r0 != 0) goto L55
            r4 = r3
            goto L5a
        L55:
            float r0 = r0.floatValue()
            r4 = r0
        L5a:
            java.lang.Object r0 = r1.startValue
            r5 = r0
            java.lang.Float r5 = (java.lang.Float) r5
            java.lang.Object r0 = r1.endValue
            r6 = r0
            java.lang.Float r6 = (java.lang.Float) r6
            r8 = r7
            java.lang.Object r0 = r2.getValueInternal(r3, r4, r5, r6, r7, r8, r9)
            r1 = r0
            java.lang.Float r1 = (java.lang.Float) r1
        L6c:
            r0 = 0
            if (r12 != 0) goto L79
            android.graphics.PointF r12 = r11.pointWithCallbackValues
            android.graphics.PointF r2 = r11.point
            float r2 = r2.x
            r12.set(r2, r0)
            goto L82
        L79:
            android.graphics.PointF r2 = r11.pointWithCallbackValues
            float r12 = r12.floatValue()
            r2.set(r12, r0)
        L82:
            if (r1 != 0) goto L90
            android.graphics.PointF r12 = r11.pointWithCallbackValues
            float r0 = r12.x
            android.graphics.PointF r1 = r11.point
            float r1 = r1.y
            r12.set(r0, r1)
            goto L9b
        L90:
            android.graphics.PointF r12 = r11.pointWithCallbackValues
            float r0 = r12.x
            float r1 = r1.floatValue()
            r12.set(r0, r1)
        L9b:
            android.graphics.PointF r11 = r11.pointWithCallbackValues
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.keyframe.SplitDimensionPathKeyframeAnimation.getValue(float):android.graphics.PointF");
    }
}
