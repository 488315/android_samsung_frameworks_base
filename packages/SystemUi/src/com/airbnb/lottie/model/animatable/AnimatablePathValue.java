package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PathKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimatablePathValue implements AnimatableValue {
    public final List keyframes;

    public AnimatablePathValue(List<Keyframe> list) {
        this.keyframes = list;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final BaseKeyframeAnimation createAnimation() {
        List list = this.keyframes;
        return ((Keyframe) list.get(0)).isStatic() ? new PointKeyframeAnimation(list) : new PathKeyframeAnimation(list);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final List getKeyframes() {
        return this.keyframes;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final boolean isStatic() {
        List list = this.keyframes;
        return list.size() == 1 && ((Keyframe) list.get(0)).isStatic();
    }
}
