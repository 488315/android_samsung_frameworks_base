package com.airbnb.lottie.animation.keyframe;

import android.graphics.Color;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;

/* loaded from: classes.dex */
public class DropShadowKeyframeAnimation implements BaseKeyframeAnimation.AnimationListener {
    public final ColorKeyframeAnimation color;
    public final FloatKeyframeAnimation direction;
    public final FloatKeyframeAnimation distance;
    public boolean isDirty = true;
    public final BaseKeyframeAnimation.AnimationListener listener;
    public final FloatKeyframeAnimation opacity;
    public final FloatKeyframeAnimation radius;

    public DropShadowKeyframeAnimation(BaseKeyframeAnimation.AnimationListener animationListener, BaseLayer baseLayer, DropShadowEffect dropShadowEffect) {
        this.listener = animationListener;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = dropShadowEffect.color.createAnimation();
        this.color = (ColorKeyframeAnimation) baseKeyframeAnimationCreateAnimation;
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = dropShadowEffect.opacity.createAnimation();
        this.opacity = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation2;
        baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation2);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = dropShadowEffect.direction.createAnimation();
        this.direction = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation3;
        baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation3);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = dropShadowEffect.distance.createAnimation();
        this.distance = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation4;
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation4);
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation5 = dropShadowEffect.radius.createAnimation();
        this.radius = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation5;
        baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation5);
    }

    public final void applyTo(LPaint lPaint) {
        if (this.isDirty) {
            this.isDirty = false;
            double dFloatValue = ((Float) this.direction.getValue()).floatValue() * 0.017453292519943295d;
            float fFloatValue = ((Float) this.distance.getValue()).floatValue();
            float fSin = ((float) Math.sin(dFloatValue)) * fFloatValue;
            float fCos = ((float) Math.cos(dFloatValue + 3.141592653589793d)) * fFloatValue;
            int iIntValue = ((Integer) this.color.getValue()).intValue();
            lPaint.setShadowLayer(((Float) this.radius.getValue()).floatValue(), fSin, fCos, Color.argb(Math.round(((Float) this.opacity.getValue()).floatValue()), Color.red(iIntValue), Color.green(iIntValue), Color.blue(iIntValue)));
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
