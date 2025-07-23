package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FloatKeyframeAnimation extends KeyframeAnimation {
    public FloatKeyframeAnimation(List<Keyframe> list) {
        super(list);
    }

    public final float getFloatValue(Keyframe keyframe, float f) {
        float f2;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj = keyframe.startValue;
        if (lottieValueCallback != null) {
            Float f3 = (Float) keyframe.endValue;
            float linearCurrentKeyframeProgress = getLinearCurrentKeyframeProgress();
            float f4 = this.progress;
            f2 = f;
            Float f5 = (Float) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), (Float) obj, f3, f2, linearCurrentKeyframeProgress, f4);
            if (f5 != null) {
                return f5.floatValue();
            }
        } else {
            f2 = f;
        }
        if (keyframe.startValueFloat == -3987645.8f) {
            keyframe.startValueFloat = ((Float) obj).floatValue();
        }
        float f6 = keyframe.startValueFloat;
        if (keyframe.endValueFloat == -3987645.8f) {
            keyframe.endValueFloat = ((Float) keyframe.endValue).floatValue();
        }
        return MiscUtils.lerp(f6, keyframe.endValueFloat, f2);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return Float.valueOf(getFloatValue(keyframe, f));
    }

    public final float getFloatValue() {
        return getFloatValue(this.keyframesWrapper.getCurrentKeyframe(), getInterpolatedCurrentKeyframeProgress());
    }
}
