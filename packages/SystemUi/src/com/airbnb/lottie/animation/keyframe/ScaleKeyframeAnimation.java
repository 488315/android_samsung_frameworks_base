package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScaleKeyframeAnimation extends KeyframeAnimation {
    public final ScaleXY scaleXY;

    public ScaleKeyframeAnimation(List<Keyframe> list) {
        super(list);
        this.scaleXY = new ScaleXY();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        Object obj;
        ScaleXY scaleXY;
        Object obj2 = keyframe.startValue;
        if (obj2 == null || (obj = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        ScaleXY scaleXY2 = (ScaleXY) obj2;
        ScaleXY scaleXY3 = (ScaleXY) obj;
        LottieValueCallback lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null && (scaleXY = (ScaleXY) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), scaleXY2, scaleXY3, f, getLinearCurrentKeyframeProgress(), this.progress)) != null) {
            return scaleXY;
        }
        float f2 = scaleXY2.scaleX;
        float f3 = scaleXY3.scaleX;
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f3, f2, f, f2);
        float f4 = scaleXY2.scaleY;
        float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(scaleXY3.scaleY, f4, f, f4);
        ScaleXY scaleXY4 = this.scaleXY;
        scaleXY4.scaleX = m20m;
        scaleXY4.scaleY = m20m2;
        return scaleXY4;
    }
}
