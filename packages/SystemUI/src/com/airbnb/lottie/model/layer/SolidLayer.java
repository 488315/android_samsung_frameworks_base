package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.value.LottieValueCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SolidLayer extends BaseLayer {
    public ValueCallbackKeyframeAnimation colorFilterAnimation;
    public final Layer layerModel;
    public final LPaint paint;
    public final Path path;
    public final float[] points;
    public final RectF rect;

    public SolidLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        this.rect = new RectF();
        LPaint lPaint = new LPaint();
        this.paint = lPaint;
        this.points = new float[8];
        this.path = new Path();
        this.layerModel = layer;
        lPaint.setAlpha(0);
        lPaint.setStyle(Paint.Style.FILL);
        lPaint.setColor(layer.solidColor);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.COLOR_FILTER) {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
            } else {
                this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            }
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Layer layer = this.layerModel;
        int alpha = Color.alpha(layer.solidColor);
        if (alpha == 0) {
            return;
        }
        int intValue = (int) ((((alpha / 255.0f) * (this.transform.opacity == null ? 100 : ((Integer) r2.getValue()).intValue())) / 100.0f) * (i / 255.0f) * 255.0f);
        LPaint lPaint = this.paint;
        lPaint.setAlpha(intValue);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            lPaint.setColorFilter((ColorFilter) valueCallbackKeyframeAnimation.getValue());
        }
        if (intValue > 0) {
            float[] fArr = this.points;
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            float f = layer.solidWidth;
            fArr[2] = f;
            fArr[3] = 0.0f;
            fArr[4] = f;
            float f2 = layer.solidHeight;
            fArr[5] = f2;
            fArr[6] = 0.0f;
            fArr[7] = f2;
            matrix.mapPoints(fArr);
            this.path.reset();
            this.path.moveTo(fArr[0], fArr[1]);
            this.path.lineTo(fArr[2], fArr[3]);
            this.path.lineTo(fArr[4], fArr[5]);
            this.path.lineTo(fArr[6], fArr[7]);
            this.path.lineTo(fArr[0], fArr[1]);
            this.path.close();
            canvas.drawPath(this.path, lPaint);
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        RectF rectF2 = this.rect;
        Layer layer = this.layerModel;
        rectF2.set(0.0f, 0.0f, layer.solidWidth, layer.solidHeight);
        this.boundsMatrix.mapRect(this.rect);
        rectF.set(this.rect);
    }
}
