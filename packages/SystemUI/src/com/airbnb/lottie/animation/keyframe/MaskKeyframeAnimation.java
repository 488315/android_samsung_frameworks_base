package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.Mask;
import java.util.ArrayList;
import java.util.List;

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
