package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PathKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AnimatablePathValue implements AnimatableValue {
    public final List keyframes;

    public AnimatablePathValue(List<Keyframe> list) {
        this.keyframes = list;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final BaseKeyframeAnimation createAnimation() {
        return ((Keyframe) this.keyframes.get(0)).isStatic() ? new PointKeyframeAnimation(this.keyframes) : new PathKeyframeAnimation(this.keyframes);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final List getKeyframes() {
        return this.keyframes;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final boolean isStatic() {
        return this.keyframes.size() == 1 && ((Keyframe) this.keyframes.get(0)).isStatic();
    }
}
