package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class ColorKeyframeAnimation extends KeyframeAnimation {
    public ColorKeyframeAnimation(List<Keyframe> list) {
        super(list);
    }

    public final int getIntValue(Keyframe keyframe, float f) {
        float f2;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj = keyframe.startValue;
        if (lottieValueCallback != null) {
            Integer num = (Integer) keyframe.endValue;
            float linearCurrentKeyframeProgress = getLinearCurrentKeyframeProgress();
            float f3 = this.progress;
            f2 = f;
            Integer num2 = (Integer) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), (Integer) obj, num, f2, linearCurrentKeyframeProgress, f3);
            if (num2 != null) {
                return num2.intValue();
            }
        } else {
            f2 = f;
        }
        return GammaEvaluator.evaluate(MiscUtils.clamp(f2, 0.0f, 1.0f), ((Integer) obj).intValue(), ((Integer) keyframe.endValue).intValue());
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }
}
