package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GradientStrokeContent extends BaseStrokeContent {
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GradientStrokeContent(com.airbnb.lottie.LottieDrawable r11, com.airbnb.lottie.model.layer.BaseLayer r12, com.airbnb.lottie.model.content.GradientStroke r13) {
        /*
            r10 = this;
            com.airbnb.lottie.model.content.ShapeStroke$LineCapType r0 = r13.capType
            r0.getClass()
            int[] r1 = com.airbnb.lottie.model.content.ShapeStroke.AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineCapType
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 2
            r2 = 1
            if (r0 == r2) goto L1a
            if (r0 == r1) goto L17
            android.graphics.Paint$Cap r0 = android.graphics.Paint.Cap.SQUARE
        L15:
            r3 = r0
            goto L1d
        L17:
            android.graphics.Paint$Cap r0 = android.graphics.Paint.Cap.ROUND
            goto L15
        L1a:
            android.graphics.Paint$Cap r0 = android.graphics.Paint.Cap.BUTT
            goto L15
        L1d:
            com.airbnb.lottie.model.content.ShapeStroke$LineJoinType r0 = r13.joinType
            r0.getClass()
            int[] r4 = com.airbnb.lottie.model.content.ShapeStroke.AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineJoinType
            int r0 = r0.ordinal()
            r0 = r4[r0]
            if (r0 == r2) goto L3a
            if (r0 == r1) goto L37
            r1 = 3
            if (r0 == r1) goto L34
            r0 = 0
        L32:
            r4 = r0
            goto L3d
        L34:
            android.graphics.Paint$Join r0 = android.graphics.Paint.Join.ROUND
            goto L32
        L37:
            android.graphics.Paint$Join r0 = android.graphics.Paint.Join.MITER
            goto L32
        L3a:
            android.graphics.Paint$Join r0 = android.graphics.Paint.Join.BEVEL
            goto L32
        L3d:
            java.util.List r8 = r13.lineDashPattern
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r7 = r13.width
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r9 = r13.dashOffset
            float r5 = r13.miterLimit
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r6 = r13.opacity
            r0 = r10
            r1 = r11
            r2 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            androidx.collection.LongSparseArray r1 = new androidx.collection.LongSparseArray
            r1.<init>()
            r10.linearGradientCache = r1
            androidx.collection.LongSparseArray r1 = new androidx.collection.LongSparseArray
            r1.<init>()
            r10.radialGradientCache = r1
            android.graphics.RectF r1 = new android.graphics.RectF
            r1.<init>()
            r10.boundsRect = r1
            java.lang.String r1 = r13.name
            r10.name = r1
            com.airbnb.lottie.model.content.GradientType r1 = r13.gradientType
            r10.type = r1
            boolean r1 = r13.hidden
            r10.hidden = r1
            com.airbnb.lottie.LottieComposition r1 = r11.composition
            float r1 = r1.getDuration()
            r3 = 1107296256(0x42000000, float:32.0)
            float r1 = r1 / r3
            int r1 = (int) r1
            r10.cacheSteps = r1
            com.airbnb.lottie.model.animatable.AnimatableGradientColorValue r1 = r13.gradientColor
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r1 = r1.createAnimation()
            r3 = r1
            com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation r3 = (com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation) r3
            r10.colorAnimation = r3
            r1.addUpdateListener(r10)
            r12.addAnimation(r1)
            com.airbnb.lottie.model.animatable.AnimatablePointValue r1 = r13.startPoint
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r1 = r1.createAnimation()
            r3 = r1
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r3 = (com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation) r3
            r10.startPointAnimation = r3
            r1.addUpdateListener(r10)
            r12.addAnimation(r1)
            com.airbnb.lottie.model.animatable.AnimatablePointValue r1 = r13.endPoint
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r1 = r1.createAnimation()
            r3 = r1
            com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation r3 = (com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation) r3
            r10.endPointAnimation = r3
            r1.addUpdateListener(r10)
            r12.addAnimation(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.GradientStrokeContent.<init>(com.airbnb.lottie.LottieDrawable, com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.content.GradientStroke):void");
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
        Shader radialGradient;
        if (this.hidden) {
            return;
        }
        getBounds(this.boundsRect, matrix, false);
        GradientType gradientType = GradientType.LINEAR;
        GradientColorKeyframeAnimation gradientColorKeyframeAnimation = this.colorAnimation;
        PointKeyframeAnimation pointKeyframeAnimation = this.endPointAnimation;
        PointKeyframeAnimation pointKeyframeAnimation2 = this.startPointAnimation;
        if (this.type == gradientType) {
            long gradientHash = getGradientHash();
            LongSparseArray longSparseArray = this.linearGradientCache;
            shader = (LinearGradient) longSparseArray.get(gradientHash);
            if (shader == null) {
                PointF pointF = (PointF) pointKeyframeAnimation2.getValue();
                PointF pointF2 = (PointF) pointKeyframeAnimation.getValue();
                GradientColor gradientColor = (GradientColor) gradientColorKeyframeAnimation.getValue();
                radialGradient = new LinearGradient(pointF.x, pointF.y, pointF2.x, pointF2.y, applyDynamicColorsIfNeeded(gradientColor.colors), gradientColor.positions, Shader.TileMode.CLAMP);
                longSparseArray.put(gradientHash, radialGradient);
                shader = radialGradient;
            }
            shader.setLocalMatrix(matrix);
            this.paint.setShader(shader);
            super.draw(canvas, matrix, i);
        }
        long gradientHash2 = getGradientHash();
        LongSparseArray longSparseArray2 = this.radialGradientCache;
        shader = (RadialGradient) longSparseArray2.get(gradientHash2);
        if (shader == null) {
            PointF pointF3 = (PointF) pointKeyframeAnimation2.getValue();
            PointF pointF4 = (PointF) pointKeyframeAnimation.getValue();
            GradientColor gradientColor2 = (GradientColor) gradientColorKeyframeAnimation.getValue();
            int[] applyDynamicColorsIfNeeded = applyDynamicColorsIfNeeded(gradientColor2.colors);
            radialGradient = new RadialGradient(pointF3.x, pointF3.y, (float) Math.hypot(pointF4.x - r10, pointF4.y - r11), applyDynamicColorsIfNeeded, gradientColor2.positions, Shader.TileMode.CLAMP);
            longSparseArray2.put(gradientHash2, radialGradient);
            shader = radialGradient;
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
}
