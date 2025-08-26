package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ScaleKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

/* loaded from: classes.dex */
public class AnimatableScaleValue extends BaseAnimatableValue {
    public AnimatableScaleValue(ScaleXY scaleXY) {
        super(scaleXY);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final BaseKeyframeAnimation createAnimation() {
        return new ScaleKeyframeAnimation(this.keyframes);
    }

    public AnimatableScaleValue(List<Keyframe> list) {
        super(list);
    }
}
