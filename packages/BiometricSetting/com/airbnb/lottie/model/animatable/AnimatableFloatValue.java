package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class AnimatableFloatValue extends BaseAnimatableValue<Float, Float> {
    AnimatableFloatValue() {
        super(Collections.singletonList(new Keyframe(Float.valueOf(0.0f))));
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final BaseKeyframeAnimation<Float, Float> createAnimation() {
        return new FloatKeyframeAnimation(this.keyframes);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final List getKeyframes() {
        return this.keyframes;
    }

    public AnimatableFloatValue(List<Keyframe<Float>> list) {
        super(list);
    }
}
