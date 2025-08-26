package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class TextKeyframeAnimation extends KeyframeAnimation {
    public TextKeyframeAnimation(List<Keyframe> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        Object obj;
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj2 = keyframe.startValue;
        if (lottieValueCallback == null) {
            return (f != 1.0f || (obj = keyframe.endValue) == null) ? (DocumentData) obj2 : (DocumentData) obj;
        }
        Float f2 = keyframe.endFrame;
        float fFloatValue = f2 == null ? Float.MAX_VALUE : f2.floatValue();
        DocumentData documentData = (DocumentData) obj2;
        Object obj3 = keyframe.endValue;
        return (DocumentData) lottieValueCallback.getValueInternal(keyframe.startFrame, fFloatValue, documentData, obj3 == null ? documentData : (DocumentData) obj3, f, getInterpolatedCurrentKeyframeProgress(), this.progress);
    }
}
