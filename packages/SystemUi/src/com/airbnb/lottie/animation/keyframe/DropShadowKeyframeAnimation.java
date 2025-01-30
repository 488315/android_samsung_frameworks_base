package com.airbnb.lottie.animation.keyframe;

import android.graphics.Color;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DropShadowKeyframeAnimation implements BaseKeyframeAnimation.AnimationListener {
    public final ColorKeyframeAnimation color;
    public final FloatKeyframeAnimation direction;
    public final FloatKeyframeAnimation distance;
    public boolean isDirty = true;
    public final BaseKeyframeAnimation.AnimationListener listener;
    public final FloatKeyframeAnimation opacity;
    public final FloatKeyframeAnimation radius;

    public DropShadowKeyframeAnimation(BaseKeyframeAnimation.AnimationListener animationListener, BaseLayer baseLayer, DropShadowEffect dropShadowEffect) {
        this.listener = animationListener;
        BaseKeyframeAnimation createAnimation = dropShadowEffect.color.createAnimation();
        this.color = (ColorKeyframeAnimation) createAnimation;
        createAnimation.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation);
        BaseKeyframeAnimation createAnimation2 = dropShadowEffect.opacity.createAnimation();
        this.opacity = (FloatKeyframeAnimation) createAnimation2;
        createAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation2);
        BaseKeyframeAnimation createAnimation3 = dropShadowEffect.direction.createAnimation();
        this.direction = (FloatKeyframeAnimation) createAnimation3;
        createAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation3);
        BaseKeyframeAnimation createAnimation4 = dropShadowEffect.distance.createAnimation();
        this.distance = (FloatKeyframeAnimation) createAnimation4;
        createAnimation4.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation4);
        BaseKeyframeAnimation createAnimation5 = dropShadowEffect.radius.createAnimation();
        this.radius = (FloatKeyframeAnimation) createAnimation5;
        createAnimation5.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation5);
    }

    public final void applyTo(LPaint lPaint) {
        if (this.isDirty) {
            this.isDirty = false;
            double floatValue = ((Float) this.direction.getValue()).floatValue() * 0.017453292519943295d;
            float floatValue2 = ((Float) this.distance.getValue()).floatValue();
            float sin = ((float) Math.sin(floatValue)) * floatValue2;
            float cos = ((float) Math.cos(floatValue + 3.141592653589793d)) * floatValue2;
            int intValue = ((Integer) this.color.getValue()).intValue();
            lPaint.setShadowLayer(((Float) this.radius.getValue()).floatValue(), sin, cos, Color.argb(Math.round(((Float) this.opacity.getValue()).floatValue()), Color.red(intValue), Color.green(intValue), Color.blue(intValue)));
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.isDirty = true;
        this.listener.onValueChanged();
    }

    public final void setOpacityCallback(final LottieValueCallback lottieValueCallback) {
        FloatKeyframeAnimation floatKeyframeAnimation = this.opacity;
        if (lottieValueCallback == null) {
            floatKeyframeAnimation.setValueCallback(null);
        } else {
            floatKeyframeAnimation.setValueCallback(new LottieValueCallback(this) { // from class: com.airbnb.lottie.animation.keyframe.DropShadowKeyframeAnimation.1
                @Override // com.airbnb.lottie.value.LottieValueCallback
                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                    Float f = (Float) lottieValueCallback.getValue(lottieFrameInfo);
                    if (f == null) {
                        return null;
                    }
                    return Float.valueOf(f.floatValue() * 2.55f);
                }
            });
        }
    }
}
