package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.ColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class StrokeContent extends BaseStrokeContent {
    public final ColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorFilterAnimation;
    public final boolean hidden;
    public final BaseLayer layer;
    public final String name;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StrokeContent(com.airbnb.lottie.LottieDrawable r11, com.airbnb.lottie.model.layer.BaseLayer r12, com.airbnb.lottie.model.content.ShapeStroke r13) {
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
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r9 = r13.offset
            float r5 = r13.miterLimit
            com.airbnb.lottie.model.animatable.AnimatableIntegerValue r6 = r13.opacity
            r0 = r10
            r1 = r11
            r2 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r10.layer = r12
            java.lang.String r1 = r13.name
            r10.name = r1
            boolean r1 = r13.hidden
            r10.hidden = r1
            com.airbnb.lottie.model.animatable.AnimatableColorValue r1 = r13.color
            com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation r1 = r1.createAnimation()
            r3 = r1
            com.airbnb.lottie.animation.keyframe.ColorKeyframeAnimation r3 = (com.airbnb.lottie.animation.keyframe.ColorKeyframeAnimation) r3
            r10.colorAnimation = r3
            r1.addUpdateListener(r10)
            r12.addAnimation(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.StrokeContent.<init>(com.airbnb.lottie.LottieDrawable, com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.content.ShapeStroke):void");
    }

    @Override // com.airbnb.lottie.animation.content.BaseStrokeContent, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        PointF pointF = LottieProperty.TRANSFORM_ANCHOR_POINT;
        ColorKeyframeAnimation colorKeyframeAnimation = this.colorAnimation;
        if (obj == 2) {
            colorKeyframeAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.COLOR_FILTER) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
            BaseLayer baseLayer = this.layer;
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
            baseLayer.addAnimation(colorKeyframeAnimation);
        }
    }

    @Override // com.airbnb.lottie.animation.content.BaseStrokeContent, com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        if (this.hidden) {
            return;
        }
        LPaint lPaint = this.paint;
        ColorKeyframeAnimation colorKeyframeAnimation = this.colorAnimation;
        lPaint.setColor(colorKeyframeAnimation.getIntValue(colorKeyframeAnimation.keyframesWrapper.getCurrentKeyframe(), colorKeyframeAnimation.getInterpolatedCurrentKeyframeProgress()));
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            lPaint.setColorFilter((ColorFilter) valueCallbackKeyframeAnimation.getValue());
        }
        super.draw(canvas, matrix, i);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }
}
