package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;

/* loaded from: classes.dex */
public final class ImageLayer extends BaseLayer {
    private ValueCallbackKeyframeAnimation colorFilterAnimation;
    private final Rect dst;
    private final LPaint paint;
    private final Rect src;

    ImageLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        this.paint = new LPaint(3);
        this.src = new Rect();
        this.dst = new Rect();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.COLOR_FILTER) {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
            } else {
                this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            }
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Bitmap imageAsset = this.lottieDrawable.getImageAsset(this.layerModel.getRefId());
        if (imageAsset == null || imageAsset.isRecycled()) {
            return;
        }
        float dpScale = Utils.dpScale();
        LPaint lPaint = this.paint;
        lPaint.setAlpha(i);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            lPaint.setColorFilter((ColorFilter) valueCallbackKeyframeAnimation.getValue());
        }
        canvas.save();
        canvas.concat(matrix);
        int width = imageAsset.getWidth();
        int height = imageAsset.getHeight();
        Rect rect = this.src;
        rect.set(0, 0, width, height);
        Rect rect2 = this.dst;
        rect2.set(0, 0, (int) (imageAsset.getWidth() * dpScale), (int) (imageAsset.getHeight() * dpScale));
        canvas.drawBitmap(imageAsset, rect, rect2, lPaint);
        canvas.restore();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        if (this.lottieDrawable.getImageAsset(this.layerModel.getRefId()) != null) {
            rectF.set(0.0f, 0.0f, Utils.dpScale() * r3.getWidth(), Utils.dpScale() * r3.getHeight());
            this.boundsMatrix.mapRect(rectF);
        }
    }
}
