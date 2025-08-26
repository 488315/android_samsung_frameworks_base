package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class PointKeyframeAnimation extends KeyframeAnimation {
    public final PointF point;

    public PointKeyframeAnimation(List<Keyframe> list) {
        super(list);
        this.point = new PointF();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        return getValue(keyframe, f, f, f);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final PointF getValue(Keyframe keyframe, float f, float f2, float f3) {
        Object obj;
        Object obj2 = keyframe.startValue;
        if (obj2 != null && (obj = keyframe.endValue) != null) {
            PointF pointF = (PointF) obj2;
            PointF pointF2 = (PointF) obj;
            LottieValueCallback lottieValueCallback = this.valueCallback;
            if (lottieValueCallback != null) {
                PointF pointF3 = (PointF) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), pointF, pointF2, f, getLinearCurrentKeyframeProgress(), this.progress);
                if (pointF3 != null) {
                    return pointF3;
                }
            }
            PointF pointF4 = this.point;
            float f4 = pointF.x;
            float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF2.x, f4, f2, f4);
            float f5 = pointF.y;
            pointF4.set(fM$1, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF2.y, f5, f3, f5));
            return this.point;
        }
        throw new IllegalStateException("Missing values for keyframe.");
    }
}
