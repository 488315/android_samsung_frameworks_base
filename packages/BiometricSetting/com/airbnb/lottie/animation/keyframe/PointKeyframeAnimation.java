package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public final class PointKeyframeAnimation extends KeyframeAnimation<PointF> {
    private final PointF point;

    public PointKeyframeAnimation(List<Keyframe<PointF>> list) {
        super(list);
        this.point = new PointF();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        T t;
        PointF pointF;
        T t2 = keyframe.startValue;
        if (t2 == 0 || (t = keyframe.endValue) == 0) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        PointF pointF2 = (PointF) t2;
        PointF pointF3 = (PointF) t;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != 0 && (pointF = (PointF) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), pointF2, pointF3, f, getLinearCurrentKeyframeProgress(), this.progress)) != null) {
            return pointF;
        }
        PointF pointF4 = this.point;
        float f2 = pointF2.x;
        float m3m = RunGroup$$ExternalSyntheticOutline0.m3m(pointF3.x, f2, f, f2);
        float f3 = pointF2.y;
        pointF4.set(m3m, ((pointF3.y - f3) * f) + f3);
        return pointF4;
    }
}
