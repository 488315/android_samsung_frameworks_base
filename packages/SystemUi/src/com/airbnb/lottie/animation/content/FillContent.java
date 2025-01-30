package com.airbnb.lottie.animation.content;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.DropShadowKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeFill;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FillContent implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public BaseKeyframeAnimation blurAnimation;
    public float blurMaskFilterRadius;
    public final ColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorFilterAnimation;
    public final DropShadowKeyframeAnimation dropShadowAnimation;
    public final boolean hidden;
    public final BaseLayer layer;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final IntegerKeyframeAnimation opacityAnimation;
    public final LPaint paint;
    public final Path path;
    public final List paths;

    public FillContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, ShapeFill shapeFill) {
        AnimatableIntegerValue animatableIntegerValue;
        Path path = new Path();
        this.path = path;
        this.paint = new LPaint(1);
        this.paths = new ArrayList();
        this.layer = baseLayer;
        this.name = shapeFill.name;
        this.hidden = shapeFill.hidden;
        this.lottieDrawable = lottieDrawable;
        if (baseLayer.getBlurEffect() != null) {
            BaseKeyframeAnimation createAnimation = baseLayer.getBlurEffect().blurriness.createAnimation();
            this.blurAnimation = createAnimation;
            createAnimation.addUpdateListener(this);
            baseLayer.addAnimation(this.blurAnimation);
        }
        if (baseLayer.getDropShadowEffect() != null) {
            this.dropShadowAnimation = new DropShadowKeyframeAnimation(this, baseLayer, baseLayer.getDropShadowEffect());
        }
        AnimatableColorValue animatableColorValue = shapeFill.color;
        if (animatableColorValue == null || (animatableIntegerValue = shapeFill.opacity) == null) {
            this.colorAnimation = null;
            this.opacityAnimation = null;
            return;
        }
        path.setFillType(shapeFill.fillType);
        BaseKeyframeAnimation createAnimation2 = animatableColorValue.createAnimation();
        this.colorAnimation = (ColorKeyframeAnimation) createAnimation2;
        createAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation2);
        BaseKeyframeAnimation createAnimation3 = animatableIntegerValue.createAnimation();
        this.opacityAnimation = (IntegerKeyframeAnimation) createAnimation3;
        createAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation3);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        if (obj == LottieProperty.COLOR) {
            this.colorAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.OPACITY) {
            this.opacityAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        ColorFilter colorFilter = LottieProperty.COLOR_FILTER;
        BaseLayer baseLayer = this.layer;
        if (obj == colorFilter) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
            if (valueCallbackKeyframeAnimation != null) {
                baseLayer.removeAnimation(valueCallbackKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorFilterAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            baseLayer.addAnimation(this.colorFilterAnimation);
            return;
        }
        if (obj == LottieProperty.BLUR_RADIUS) {
            BaseKeyframeAnimation baseKeyframeAnimation = this.blurAnimation;
            if (baseKeyframeAnimation != null) {
                baseKeyframeAnimation.setValueCallback(lottieValueCallback);
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.blurAnimation = valueCallbackKeyframeAnimation3;
            valueCallbackKeyframeAnimation3.addUpdateListener(this);
            baseLayer.addAnimation(this.blurAnimation);
            return;
        }
        Integer num = LottieProperty.DROP_SHADOW_COLOR;
        DropShadowKeyframeAnimation dropShadowKeyframeAnimation = this.dropShadowAnimation;
        if (obj == num && dropShadowKeyframeAnimation != null) {
            dropShadowKeyframeAnimation.color.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.DROP_SHADOW_OPACITY && dropShadowKeyframeAnimation != null) {
            dropShadowKeyframeAnimation.setOpacityCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.DROP_SHADOW_DIRECTION && dropShadowKeyframeAnimation != null) {
            dropShadowKeyframeAnimation.direction.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.DROP_SHADOW_DISTANCE && dropShadowKeyframeAnimation != null) {
            dropShadowKeyframeAnimation.distance.setValueCallback(lottieValueCallback);
        } else {
            if (obj != LottieProperty.DROP_SHADOW_RADIUS || dropShadowKeyframeAnimation == null) {
                return;
            }
            dropShadowKeyframeAnimation.radius.setValueCallback(lottieValueCallback);
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        BlurMaskFilter blurMaskFilter;
        if (this.hidden) {
            return;
        }
        ColorKeyframeAnimation colorKeyframeAnimation = this.colorAnimation;
        int intValue = colorKeyframeAnimation.getIntValue(colorKeyframeAnimation.getCurrentKeyframe(), colorKeyframeAnimation.getInterpolatedCurrentKeyframeProgress());
        PointF pointF = MiscUtils.pathFromDataCurrentPoint;
        int i2 = 0;
        int max = (Math.max(0, Math.min(255, (int) ((((i / 255.0f) * ((Integer) this.opacityAnimation.getValue()).intValue()) / 100.0f) * 255.0f))) << 24) | (intValue & 16777215);
        LPaint lPaint = this.paint;
        lPaint.setColor(max);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            lPaint.setColorFilter((ColorFilter) valueCallbackKeyframeAnimation.getValue());
        }
        BaseKeyframeAnimation baseKeyframeAnimation = this.blurAnimation;
        if (baseKeyframeAnimation != null) {
            float floatValue = ((Float) baseKeyframeAnimation.getValue()).floatValue();
            if (floatValue == 0.0f) {
                lPaint.setMaskFilter(null);
            } else if (floatValue != this.blurMaskFilterRadius) {
                BaseLayer baseLayer = this.layer;
                if (baseLayer.blurMaskFilterRadius == floatValue) {
                    blurMaskFilter = baseLayer.blurMaskFilter;
                } else {
                    BlurMaskFilter blurMaskFilter2 = new BlurMaskFilter(floatValue / 2.0f, BlurMaskFilter.Blur.NORMAL);
                    baseLayer.blurMaskFilter = blurMaskFilter2;
                    baseLayer.blurMaskFilterRadius = floatValue;
                    blurMaskFilter = blurMaskFilter2;
                }
                lPaint.setMaskFilter(blurMaskFilter);
            }
            this.blurMaskFilterRadius = floatValue;
        }
        DropShadowKeyframeAnimation dropShadowKeyframeAnimation = this.dropShadowAnimation;
        if (dropShadowKeyframeAnimation != null) {
            dropShadowKeyframeAnimation.applyTo(lPaint);
        }
        Path path = this.path;
        path.reset();
        while (true) {
            ArrayList arrayList = (ArrayList) this.paths;
            if (i2 >= arrayList.size()) {
                canvas.drawPath(path, lPaint);
                return;
            } else {
                path.addPath(((PathContent) arrayList.get(i2)).getPath(), matrix);
                i2++;
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        Path path = this.path;
        path.reset();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.paths;
            if (i >= arrayList.size()) {
                path.computeBounds(rectF, false);
                rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
                return;
            } else {
                path.addPath(((PathContent) arrayList.get(i)).getPath(), matrix);
                i++;
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        for (int i = 0; i < list2.size(); i++) {
            Content content = (Content) list2.get(i);
            if (content instanceof PathContent) {
                this.paths.add((PathContent) content);
            }
        }
    }
}
