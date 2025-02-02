package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

/* loaded from: classes.dex */
public final class PathKeyframe extends Keyframe<PointF> {
    private Path path;
    private final Keyframe<PointF> pointKeyFrame;

    public PathKeyframe(LottieComposition lottieComposition, Keyframe<PointF> keyframe) {
        super(lottieComposition, keyframe.startValue, keyframe.endValue, keyframe.interpolator, keyframe.startFrame, keyframe.endFrame);
        this.pointKeyFrame = keyframe;
        createPath();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void createPath() {
        T t = this.endValue;
        T t2 = this.startValue;
        boolean z = (t == 0 || t2 == 0 || !((PointF) t2).equals(((PointF) t).x, ((PointF) t).y)) ? false : true;
        T t3 = this.endValue;
        if (t3 == 0 || z) {
            return;
        }
        PointF pointF = (PointF) t2;
        PointF pointF2 = (PointF) t3;
        Keyframe<PointF> keyframe = this.pointKeyFrame;
        PointF pointF3 = keyframe.pathCp1;
        PointF pointF4 = keyframe.pathCp2;
        int i = Utils.$r8$clinit;
        Path path = new Path();
        path.moveTo(pointF.x, pointF.y);
        if (pointF3 == null || pointF4 == null || (pointF3.length() == 0.0f && pointF4.length() == 0.0f)) {
            path.lineTo(pointF2.x, pointF2.y);
        } else {
            float f = pointF3.x + pointF.x;
            float f2 = pointF.y + pointF3.y;
            float f3 = pointF2.x;
            float f4 = f3 + pointF4.x;
            float f5 = pointF2.y;
            path.cubicTo(f, f2, f4, f5 + pointF4.y, f3, f5);
        }
        this.path = path;
    }

    final Path getPath() {
        return this.path;
    }
}
