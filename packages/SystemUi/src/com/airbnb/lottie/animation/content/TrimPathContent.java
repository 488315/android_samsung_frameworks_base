package com.airbnb.lottie.animation.content;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TrimPathContent implements Content, BaseKeyframeAnimation.AnimationListener {
    public final FloatKeyframeAnimation endAnimation;
    public final boolean hidden;
    public final List listeners = new ArrayList();
    public final FloatKeyframeAnimation offsetAnimation;
    public final FloatKeyframeAnimation startAnimation;
    public final ShapeTrimPath.Type type;

    public TrimPathContent(BaseLayer baseLayer, ShapeTrimPath shapeTrimPath) {
        shapeTrimPath.getClass();
        this.hidden = shapeTrimPath.hidden;
        this.type = shapeTrimPath.type;
        BaseKeyframeAnimation createAnimation = shapeTrimPath.start.createAnimation();
        this.startAnimation = (FloatKeyframeAnimation) createAnimation;
        BaseKeyframeAnimation createAnimation2 = shapeTrimPath.end.createAnimation();
        this.endAnimation = (FloatKeyframeAnimation) createAnimation2;
        BaseKeyframeAnimation createAnimation3 = shapeTrimPath.offset.createAnimation();
        this.offsetAnimation = (FloatKeyframeAnimation) createAnimation3;
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
    }

    public final void addListener(BaseKeyframeAnimation.AnimationListener animationListener) {
        ((ArrayList) this.listeners).add(animationListener);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        int i = 0;
        while (true) {
            List list = this.listeners;
            if (i >= ((ArrayList) list).size()) {
                return;
            }
            ((BaseKeyframeAnimation.AnimationListener) ((ArrayList) list).get(i)).onValueChanged();
            i++;
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
    }
}
