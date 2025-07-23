package com.airbnb.lottie.animation.content;

import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.DropShadowKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.GradientFill;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GradientFillContent implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public BaseKeyframeAnimation blurAnimation;
    public float blurMaskFilterRadius;
    public final RectF boundsRect;
    public final int cacheSteps;
    public final GradientColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorCallbackAnimation;
    public ValueCallbackKeyframeAnimation colorFilterAnimation;
    public final DropShadowKeyframeAnimation dropShadowAnimation;
    public final PointKeyframeAnimation endPointAnimation;
    public final boolean hidden;
    public final BaseLayer layer;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final IntegerKeyframeAnimation opacityAnimation;
    public final LPaint paint;
    public final Path path;
    public final List paths;
    public final PointKeyframeAnimation startPointAnimation;
    public final GradientType type;
    public final LongSparseArray linearGradientCache = new LongSparseArray();
    public final LongSparseArray radialGradientCache = new LongSparseArray();

    public GradientFillContent(LottieDrawable lottieDrawable, LottieComposition lottieComposition, BaseLayer baseLayer, GradientFill gradientFill) {
        Path path = new Path();
        this.path = path;
        this.paint = new LPaint(1);
        this.boundsRect = new RectF();
        this.paths = new ArrayList();
        this.blurMaskFilterRadius = 0.0f;
        this.layer = baseLayer;
        this.name = gradientFill.name;
        this.hidden = gradientFill.hidden;
        this.lottieDrawable = lottieDrawable;
        this.type = gradientFill.gradientType;
        path.setFillType(gradientFill.fillType);
        this.cacheSteps = (int) (lottieComposition.getDuration() / 32.0f);
        BaseKeyframeAnimation createAnimation = gradientFill.gradientColor.createAnimation();
        this.colorAnimation = (GradientColorKeyframeAnimation) createAnimation;
        createAnimation.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation);
        BaseKeyframeAnimation createAnimation2 = gradientFill.opacity.createAnimation();
        this.opacityAnimation = (IntegerKeyframeAnimation) createAnimation2;
        createAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation2);
        BaseKeyframeAnimation createAnimation3 = gradientFill.startPoint.createAnimation();
        this.startPointAnimation = (PointKeyframeAnimation) createAnimation3;
        createAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation3);
        BaseKeyframeAnimation createAnimation4 = gradientFill.endPoint.createAnimation();
        this.endPointAnimation = (PointKeyframeAnimation) createAnimation4;
        createAnimation4.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation4);
        if (baseLayer.getBlurEffect() != null) {
            BaseKeyframeAnimation createAnimation5 = baseLayer.getBlurEffect().blurriness.createAnimation();
            this.blurAnimation = createAnimation5;
            createAnimation5.addUpdateListener(this);
            baseLayer.addAnimation(this.blurAnimation);
        }
        if (baseLayer.getDropShadowEffect() != null) {
            this.dropShadowAnimation = new DropShadowKeyframeAnimation(this, baseLayer, baseLayer.getDropShadowEffect());
        }
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        PointF pointF = LottieProperty.TRANSFORM_ANCHOR_POINT;
        if (obj == 4) {
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
        if (obj == LottieProperty.GRADIENT_COLOR) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = this.colorCallbackAnimation;
            if (valueCallbackKeyframeAnimation3 != null) {
                baseLayer.removeAnimation(valueCallbackKeyframeAnimation3);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            this.linearGradientCache.clear();
            this.radialGradientCache.clear();
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation4;
            valueCallbackKeyframeAnimation4.addUpdateListener(this);
            baseLayer.addAnimation(this.colorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.BLUR_RADIUS) {
            BaseKeyframeAnimation baseKeyframeAnimation = this.blurAnimation;
            if (baseKeyframeAnimation != null) {
                baseKeyframeAnimation.setValueCallback(lottieValueCallback);
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.blurAnimation = valueCallbackKeyframeAnimation5;
            valueCallbackKeyframeAnimation5.addUpdateListener(this);
            baseLayer.addAnimation(this.blurAnimation);
            return;
        }
        DropShadowKeyframeAnimation dropShadowKeyframeAnimation = this.dropShadowAnimation;
        if (obj == 5 && dropShadowKeyframeAnimation != null) {
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

    public final int[] applyDynamicColorsIfNeeded$1(int[] iArr) {
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            Integer[] numArr = (Integer[]) valueCallbackKeyframeAnimation.getValue();
            int i = 0;
            if (iArr.length == numArr.length) {
                while (i < iArr.length) {
                    iArr[i] = numArr[i].intValue();
                    i++;
                }
            } else {
                iArr = new int[numArr.length];
                while (i < numArr.length) {
                    iArr[i] = numArr[i].intValue();
                    i++;
                }
            }
        }
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x011b  */
    @Override // com.airbnb.lottie.animation.content.DrawingContent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(android.graphics.Canvas r21, android.graphics.Matrix r22, int r23) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.GradientFillContent.draw(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        this.path.reset();
        for (int i = 0; i < ((ArrayList) this.paths).size(); i++) {
            this.path.addPath(((PathContent) ((ArrayList) this.paths).get(i)).getPath(), matrix);
        }
        this.path.computeBounds(rectF, false);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
    }

    public final int getGradientHash$1() {
        float f = this.startPointAnimation.progress;
        float f2 = this.cacheSteps;
        int round = Math.round(f * f2);
        int round2 = Math.round(this.endPointAnimation.progress * f2);
        int round3 = Math.round(this.colorAnimation.progress * f2);
        int i = round != 0 ? 527 * round : 17;
        if (round2 != 0) {
            i = i * 31 * round2;
        }
        return round3 != 0 ? i * 31 * round3 : i;
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
