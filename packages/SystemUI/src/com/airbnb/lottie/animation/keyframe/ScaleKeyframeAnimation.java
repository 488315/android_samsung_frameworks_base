package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ScaleKeyframeAnimation extends KeyframeAnimation {
    public final ScaleXY scaleXY;

    public ScaleKeyframeAnimation(List<Keyframe> list) {
        super(list);
        this.scaleXY = new ScaleXY();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        Object obj;
        float f2;
        Object obj2 = keyframe.startValue;
        if (obj2 == null || (obj = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        ScaleXY scaleXY = (ScaleXY) obj2;
        ScaleXY scaleXY2 = (ScaleXY) obj;
        LottieValueCallback lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            f2 = f;
            ScaleXY scaleXY3 = (ScaleXY) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), scaleXY, scaleXY2, f2, getLinearCurrentKeyframeProgress(), this.progress);
            if (scaleXY3 != null) {
                return scaleXY3;
            }
        } else {
            f2 = f;
        }
        float lerp = MiscUtils.lerp(scaleXY.scaleX, scaleXY2.scaleX, f2);
        float lerp2 = MiscUtils.lerp(scaleXY.scaleY, scaleXY2.scaleY, f2);
        ScaleXY scaleXY4 = this.scaleXY;
        scaleXY4.scaleX = lerp;
        scaleXY4.scaleY = lerp2;
        return scaleXY4;
    }
}
