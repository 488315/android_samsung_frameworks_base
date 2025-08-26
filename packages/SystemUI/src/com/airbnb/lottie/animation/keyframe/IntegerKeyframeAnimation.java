package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class IntegerKeyframeAnimation extends KeyframeAnimation {
    public IntegerKeyframeAnimation(List<Keyframe> list) {
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
            f2 = f;
            Integer num = (Integer) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), (Integer) obj, (Integer) keyframe.endValue, f2, getLinearCurrentKeyframeProgress(), this.progress);
            if (num != null) {
                return num.intValue();
            }
        } else {
            f2 = f;
        }
        if (keyframe.startValueInt == 784923401) {
            keyframe.startValueInt = ((Integer) obj).intValue();
        }
        int i = keyframe.startValueInt;
        if (keyframe.endValueInt == 784923401) {
            keyframe.endValueInt = ((Integer) keyframe.endValue).intValue();
        }
        int i2 = keyframe.endValueInt;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        return (int) (((i2 - i) * f2) + i);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }
}
