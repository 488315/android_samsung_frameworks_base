package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TextKeyframeAnimation extends KeyframeAnimation {
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
        float f2 = keyframe.startFrame;
        Float f3 = keyframe.endFrame;
        float floatValue = f3 == null ? Float.MAX_VALUE : f3.floatValue();
        DocumentData documentData = (DocumentData) obj2;
        Object obj3 = keyframe.endValue;
        return (DocumentData) lottieValueCallback.getValueInternal(f2, floatValue, documentData, obj3 == null ? documentData : (DocumentData) obj3, f, getInterpolatedCurrentKeyframeProgress(), this.progress);
    }
}
