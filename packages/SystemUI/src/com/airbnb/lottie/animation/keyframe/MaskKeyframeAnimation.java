package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.Mask;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MaskKeyframeAnimation {
    public final List maskAnimations;
    public final List masks;
    public final List opacityAnimations;

    public MaskKeyframeAnimation(List<Mask> list) {
        this.masks = list;
        this.maskAnimations = new ArrayList(list.size());
        this.opacityAnimations = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            ((ArrayList) this.maskAnimations).add(new ShapeKeyframeAnimation(list.get(i).maskPath.keyframes));
            AnimatableIntegerValue animatableIntegerValue = list.get(i).opacity;
            ((ArrayList) this.opacityAnimations).add(animatableIntegerValue.createAnimation());
        }
    }
}
