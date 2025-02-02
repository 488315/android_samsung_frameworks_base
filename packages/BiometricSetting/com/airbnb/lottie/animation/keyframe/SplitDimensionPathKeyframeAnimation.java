package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class SplitDimensionPathKeyframeAnimation extends BaseKeyframeAnimation<PointF, PointF> {
    private final PointF point;
    private final BaseKeyframeAnimation<Float, Float> xAnimation;
    private final BaseKeyframeAnimation<Float, Float> yAnimation;

    public SplitDimensionPathKeyframeAnimation(FloatKeyframeAnimation floatKeyframeAnimation, FloatKeyframeAnimation floatKeyframeAnimation2) {
        super(Collections.emptyList());
        this.point = new PointF();
        this.xAnimation = floatKeyframeAnimation;
        this.yAnimation = floatKeyframeAnimation2;
        setProgress(this.progress);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final PointF getValue() {
        return this.point;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final void setProgress(float f) {
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.xAnimation;
        baseKeyframeAnimation.setProgress(f);
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation2 = this.yAnimation;
        baseKeyframeAnimation2.setProgress(f);
        this.point.set(baseKeyframeAnimation.getValue().floatValue(), baseKeyframeAnimation2.getValue().floatValue());
        int i = 0;
        while (true) {
            List<BaseKeyframeAnimation.AnimationListener> list = this.listeners;
            if (i >= ((ArrayList) list).size()) {
                return;
            }
            ((BaseKeyframeAnimation.AnimationListener) ((ArrayList) list).get(i)).onValueChanged();
            i++;
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    final PointF getValue(Keyframe<PointF> keyframe, float f) {
        return this.point;
    }
}
