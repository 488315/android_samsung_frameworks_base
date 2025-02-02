package com.airbnb.lottie.animation.keyframe;

import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

/* loaded from: classes.dex */
public final class ScaleKeyframeAnimation extends KeyframeAnimation<ScaleXY> {
    private final ScaleXY scaleXY;

    public ScaleKeyframeAnimation(List<Keyframe<ScaleXY>> list) {
        super(list);
        this.scaleXY = new ScaleXY();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        T t;
        ScaleXY scaleXY;
        T t2 = keyframe.startValue;
        if (t2 == 0 || (t = keyframe.endValue) == 0) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        ScaleXY scaleXY2 = (ScaleXY) t2;
        ScaleXY scaleXY3 = (ScaleXY) t;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != 0 && (scaleXY = (ScaleXY) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), scaleXY2, scaleXY3, f, getLinearCurrentKeyframeProgress(), this.progress)) != null) {
            return scaleXY;
        }
        float scaleX = scaleXY2.getScaleX();
        float scaleX2 = scaleXY3.getScaleX();
        int i = MiscUtils.$r8$clinit;
        float m3m = RunGroup$$ExternalSyntheticOutline0.m3m(scaleX2, scaleX, f, scaleX);
        float scaleY = scaleXY2.getScaleY();
        float scaleY2 = ((scaleXY3.getScaleY() - scaleY) * f) + scaleY;
        ScaleXY scaleXY4 = this.scaleXY;
        scaleXY4.set(m3m, scaleY2);
        return scaleXY4;
    }
}
