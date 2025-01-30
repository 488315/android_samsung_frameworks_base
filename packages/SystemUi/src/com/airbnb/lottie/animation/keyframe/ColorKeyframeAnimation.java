package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ColorKeyframeAnimation extends KeyframeAnimation {
    public ColorKeyframeAnimation(List<Keyframe> list) {
        super(list);
    }

    public final int getIntValue(Keyframe keyframe, float f) {
        Integer num;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj = keyframe.startValue;
        return (lottieValueCallback == null || (num = (Integer) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), (Integer) obj, (Integer) keyframe.endValue, f, getLinearCurrentKeyframeProgress(), this.progress)) == null) ? GammaEvaluator.evaluate(MiscUtils.clamp(f, 0.0f, 1.0f), ((Integer) obj).intValue(), ((Integer) keyframe.endValue).intValue()) : num.intValue();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }
}
