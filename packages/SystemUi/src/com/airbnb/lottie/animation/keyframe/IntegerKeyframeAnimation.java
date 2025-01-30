package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class IntegerKeyframeAnimation extends KeyframeAnimation {
    public IntegerKeyframeAnimation(List<Keyframe> list) {
        super(list);
    }

    public final int getIntValue(Keyframe keyframe, float f) {
        Integer num;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        Object obj = keyframe.startValue;
        if (lottieValueCallback != null && (num = (Integer) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), (Integer) obj, (Integer) keyframe.endValue, f, getLinearCurrentKeyframeProgress(), this.progress)) != null) {
            return num.intValue();
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
        return (int) ((f * (i2 - i)) + i);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }
}
