package com.airbnb.lottie.animation.content;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class TrimPathContent implements Content, BaseKeyframeAnimation.AnimationListener {
    private final FloatKeyframeAnimation endAnimation;
    private final boolean hidden;
    private final List<BaseKeyframeAnimation.AnimationListener> listeners = new ArrayList();
    private final FloatKeyframeAnimation offsetAnimation;
    private final FloatKeyframeAnimation startAnimation;
    private final ShapeTrimPath.Type type;

    public TrimPathContent(BaseLayer baseLayer, ShapeTrimPath shapeTrimPath) {
        shapeTrimPath.getClass();
        this.hidden = shapeTrimPath.isHidden();
        this.type = shapeTrimPath.getType();
        BaseKeyframeAnimation<Float, Float> createAnimation = shapeTrimPath.getStart().createAnimation();
        this.startAnimation = (FloatKeyframeAnimation) createAnimation;
        BaseKeyframeAnimation<Float, Float> createAnimation2 = shapeTrimPath.getEnd().createAnimation();
        this.endAnimation = (FloatKeyframeAnimation) createAnimation2;
        BaseKeyframeAnimation<Float, Float> createAnimation3 = shapeTrimPath.getOffset().createAnimation();
        this.offsetAnimation = (FloatKeyframeAnimation) createAnimation3;
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
    }

    final void addListener(BaseKeyframeAnimation.AnimationListener animationListener) {
        ((ArrayList) this.listeners).add(animationListener);
    }

    public final FloatKeyframeAnimation getEnd() {
        return this.endAnimation;
    }

    public final FloatKeyframeAnimation getOffset() {
        return this.offsetAnimation;
    }

    public final FloatKeyframeAnimation getStart() {
        return this.startAnimation;
    }

    final ShapeTrimPath.Type getType() {
        return this.type;
    }

    public final boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
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

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List<Content> list, List<Content> list2) {
    }
}
