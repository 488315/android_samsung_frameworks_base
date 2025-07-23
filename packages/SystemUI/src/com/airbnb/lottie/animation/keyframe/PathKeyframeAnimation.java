package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PathKeyframeAnimation extends KeyframeAnimation {
    public final PathMeasure pathMeasure;
    public PathKeyframe pathMeasureKeyframe;
    public final PointF point;
    public final float[] pos;

    public PathKeyframeAnimation(List<? extends Keyframe> list) {
        super(list);
        this.point = new PointF();
        this.pos = new float[2];
        this.pathMeasure = new PathMeasure();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        float f2;
        PathKeyframe pathKeyframe = (PathKeyframe) keyframe;
        Path path = pathKeyframe.path;
        if (path == null) {
            return (PointF) keyframe.startValue;
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null) {
            f2 = f;
            PointF pointF = (PointF) lottieValueCallback.getValueInternal(pathKeyframe.startFrame, pathKeyframe.endFrame.floatValue(), (PointF) pathKeyframe.startValue, (PointF) pathKeyframe.endValue, getLinearCurrentKeyframeProgress(), f2, this.progress);
            if (pointF != null) {
                return pointF;
            }
        } else {
            f2 = f;
        }
        if (this.pathMeasureKeyframe != pathKeyframe) {
            this.pathMeasure.setPath(path, false);
            this.pathMeasureKeyframe = pathKeyframe;
        }
        PathMeasure pathMeasure = this.pathMeasure;
        float length = pathMeasure.getLength() * f2;
        float[] fArr = this.pos;
        pathMeasure.getPosTan(length, fArr, null);
        this.point.set(fArr[0], fArr[1]);
        return this.point;
    }
}
