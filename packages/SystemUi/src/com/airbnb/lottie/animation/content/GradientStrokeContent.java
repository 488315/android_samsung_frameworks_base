package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GradientStrokeContent extends BaseStrokeContent {
    public final RectF boundsRect;
    public final int cacheSteps;
    public final GradientColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorCallbackAnimation;
    public final PointKeyframeAnimation endPointAnimation;
    public final boolean hidden;
    public final LongSparseArray linearGradientCache;
    public final String name;
    public final LongSparseArray radialGradientCache;
    public final PointKeyframeAnimation startPointAnimation;
    public final GradientType type;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public GradientStrokeContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, GradientStroke gradientStroke) {
        super(lottieDrawable, baseLayer, r3, r0 != 1 ? r0 != 2 ? r0 != 3 ? null : Paint.Join.ROUND : Paint.Join.MITER : Paint.Join.BEVEL, gradientStroke.miterLimit, gradientStroke.opacity, gradientStroke.width, gradientStroke.lineDashPattern, gradientStroke.dashOffset);
        ShapeStroke.LineCapType lineCapType = gradientStroke.capType;
        lineCapType.getClass();
        int i = ShapeStroke.AbstractC05971.f184xd9891597[lineCapType.ordinal()];
        Paint.Cap cap = i != 1 ? i != 2 ? Paint.Cap.SQUARE : Paint.Cap.ROUND : Paint.Cap.BUTT;
        ShapeStroke.LineJoinType lineJoinType = gradientStroke.joinType;
        lineJoinType.getClass();
        int i2 = ShapeStroke.AbstractC05971.f185x8c4dd79[lineJoinType.ordinal()];
        this.linearGradientCache = new LongSparseArray();
        this.radialGradientCache = new LongSparseArray();
        this.boundsRect = new RectF();
        this.name = gradientStroke.name;
        this.type = gradientStroke.gradientType;
        this.hidden = gradientStroke.hidden;
        this.cacheSteps = (int) (lottieDrawable.composition.getDuration() / 32.0f);
        BaseKeyframeAnimation createAnimation = gradientStroke.gradientColor.createAnimation();
        this.colorAnimation = (GradientColorKeyframeAnimation) createAnimation;
        createAnimation.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation);
        BaseKeyframeAnimation createAnimation2 = gradientStroke.startPoint.createAnimation();
        this.startPointAnimation = (PointKeyframeAnimation) createAnimation2;
        createAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation2);
        BaseKeyframeAnimation createAnimation3 = gradientStroke.endPoint.createAnimation();
        this.endPointAnimation = (PointKeyframeAnimation) createAnimation3;
        createAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(createAnimation3);
    }

    @Override // com.airbnb.lottie.animation.content.BaseStrokeContent, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.GRADIENT_COLOR) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
            BaseLayer baseLayer = this.layer;
            if (valueCallbackKeyframeAnimation != null) {
                baseLayer.removeAnimation(valueCallbackKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            baseLayer.addAnimation(this.colorCallbackAnimation);
        }
    }

    public final int[] applyDynamicColorsIfNeeded(int[] iArr) {
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

    @Override // com.airbnb.lottie.animation.content.BaseStrokeContent, com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        Shader shader;
        if (this.hidden) {
            return;
        }
        getBounds(this.boundsRect, matrix, false);
        GradientType gradientType = GradientType.LINEAR;
        GradientType gradientType2 = this.type;
        GradientColorKeyframeAnimation gradientColorKeyframeAnimation = this.colorAnimation;
        PointKeyframeAnimation pointKeyframeAnimation = this.endPointAnimation;
        PointKeyframeAnimation pointKeyframeAnimation2 = this.startPointAnimation;
        if (gradientType2 == gradientType) {
            long gradientHash = getGradientHash();
            LongSparseArray longSparseArray = this.linearGradientCache;
            shader = (LinearGradient) longSparseArray.get(gradientHash);
            if (shader == null) {
                PointF pointF = (PointF) pointKeyframeAnimation2.getValue();
                PointF pointF2 = (PointF) pointKeyframeAnimation.getValue();
                GradientColor gradientColor = (GradientColor) gradientColorKeyframeAnimation.getValue();
                shader = new LinearGradient(pointF.x, pointF.y, pointF2.x, pointF2.y, applyDynamicColorsIfNeeded(gradientColor.colors), gradientColor.positions, Shader.TileMode.CLAMP);
                longSparseArray.put(gradientHash, shader);
            }
        } else {
            long gradientHash2 = getGradientHash();
            LongSparseArray longSparseArray2 = this.radialGradientCache;
            shader = (RadialGradient) longSparseArray2.get(gradientHash2);
            if (shader == null) {
                PointF pointF3 = (PointF) pointKeyframeAnimation2.getValue();
                PointF pointF4 = (PointF) pointKeyframeAnimation.getValue();
                GradientColor gradientColor2 = (GradientColor) gradientColorKeyframeAnimation.getValue();
                int[] applyDynamicColorsIfNeeded = applyDynamicColorsIfNeeded(gradientColor2.colors);
                float[] fArr = gradientColor2.positions;
                shader = new RadialGradient(pointF3.x, pointF3.y, (float) Math.hypot(pointF4.x - r10, pointF4.y - r11), applyDynamicColorsIfNeeded, fArr, Shader.TileMode.CLAMP);
                longSparseArray2.put(gradientHash2, shader);
            }
        }
        shader.setLocalMatrix(matrix);
        this.paint.setShader(shader);
        super.draw(canvas, matrix, i);
    }

    public final int getGradientHash() {
        float f = this.startPointAnimation.progress;
        float f2 = this.cacheSteps;
        int round = Math.round(f * f2);
        int round2 = Math.round(this.endPointAnimation.progress * f2);
        int round3 = Math.round(this.colorAnimation.progress * f2);
        int i = round != 0 ? round * 527 : 17;
        if (round2 != 0) {
            i = i * 31 * round2;
        }
        return round3 != 0 ? i * 31 * round3 : i;
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }
}
