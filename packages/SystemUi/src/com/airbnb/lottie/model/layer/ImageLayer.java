package com.airbnb.lottie.model.layer;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.io.IOException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ImageLayer extends BaseLayer {
    public ValueCallbackKeyframeAnimation colorFilterAnimation;
    public final Rect dst;
    public ValueCallbackKeyframeAnimation imageAnimation;
    public final LottieImageAsset lottieImageAsset;
    public final LPaint paint;
    public final Rect src;

    public ImageLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        this.paint = new LPaint(3);
        this.src = new Rect();
        this.dst = new Rect();
        String str = layer.refId;
        LottieComposition lottieComposition = lottieDrawable.composition;
        this.lottieImageAsset = lottieComposition == null ? null : (LottieImageAsset) lottieComposition.images.get(str);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.COLOR_FILTER) {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
                return;
            } else {
                this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
                return;
            }
        }
        if (obj == LottieProperty.IMAGE) {
            if (lottieValueCallback == null) {
                this.imageAnimation = null;
            } else {
                this.imageAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0156  */
    @Override // com.airbnb.lottie.model.layer.BaseLayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Bitmap bitmap;
        Bitmap createScaledBitmap;
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.imageAnimation;
        LottieImageAsset lottieImageAsset = this.lottieImageAsset;
        LottieDrawable lottieDrawable = this.lottieDrawable;
        if (valueCallbackKeyframeAnimation == null || (bitmap = (Bitmap) valueCallbackKeyframeAnimation.getValue()) == null) {
            String str = this.layerModel.refId;
            ImageAssetManager imageAssetManager = lottieDrawable.imageAssetManager;
            if (imageAssetManager != null) {
                Drawable.Callback callback = lottieDrawable.getCallback();
                Context context = (callback != null && (callback instanceof View)) ? ((View) callback).getContext() : null;
                Context context2 = imageAssetManager.context;
                if (context2 instanceof Application) {
                    context = context.getApplicationContext();
                }
                if (!(context == context2)) {
                    lottieDrawable.imageAssetManager = null;
                }
            }
            if (lottieDrawable.imageAssetManager == null) {
                lottieDrawable.imageAssetManager = new ImageAssetManager(lottieDrawable.getCallback(), lottieDrawable.imageAssetsFolder, null, lottieDrawable.composition.images);
            }
            ImageAssetManager imageAssetManager2 = lottieDrawable.imageAssetManager;
            if (imageAssetManager2 != null) {
                String str2 = imageAssetManager2.imagesFolder;
                LottieImageAsset lottieImageAsset2 = (LottieImageAsset) imageAssetManager2.imageAssets.get(str);
                if (lottieImageAsset2 != null) {
                    Bitmap bitmap2 = lottieImageAsset2.bitmap;
                    if (bitmap2 != null) {
                        bitmap = bitmap2;
                    } else {
                        Context context3 = imageAssetManager2.context;
                        if (context3 != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inScaled = true;
                            options.inDensity = 160;
                            String str3 = lottieImageAsset2.fileName;
                            if (!str3.startsWith("data:") || str3.indexOf("base64,") <= 0) {
                                try {
                                    if (TextUtils.isEmpty(str2)) {
                                        throw new IllegalStateException("You must set an images folder before loading an image. Set it with LottieComposition#setImagesFolder or LottieDrawable#setImagesFolder");
                                    }
                                    try {
                                        Bitmap decodeStream = BitmapFactory.decodeStream(context3.getAssets().open(str2 + str3), null, options);
                                        if (decodeStream == null) {
                                            Logger.warning("Decoded image `" + str + "` is null.");
                                        } else {
                                            int i2 = lottieImageAsset2.width;
                                            int i3 = lottieImageAsset2.height;
                                            Utils.C06051 c06051 = Utils.threadLocalPathMeasure;
                                            if (decodeStream.getWidth() == i2 && decodeStream.getHeight() == i3) {
                                                createScaledBitmap = decodeStream;
                                            } else {
                                                createScaledBitmap = Bitmap.createScaledBitmap(decodeStream, i2, i3, true);
                                                decodeStream.recycle();
                                            }
                                            synchronized (ImageAssetManager.bitmapHashLock) {
                                                ((LottieImageAsset) imageAssetManager2.imageAssets.get(str)).bitmap = createScaledBitmap;
                                            }
                                        }
                                    } catch (IllegalArgumentException e) {
                                        Logger.warning("Unable to decode image `" + str + "`.", e);
                                    }
                                } catch (IOException e2) {
                                    Logger.warning("Unable to open asset.", e2);
                                }
                            } else {
                                try {
                                    byte[] decode = Base64.decode(str3.substring(str3.indexOf(44) + 1), 0);
                                    createScaledBitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length, options);
                                    synchronized (ImageAssetManager.bitmapHashLock) {
                                        ((LottieImageAsset) imageAssetManager2.imageAssets.get(str)).bitmap = createScaledBitmap;
                                    }
                                } catch (IllegalArgumentException e3) {
                                    Logger.warning("data URL did not have correct base64 format.", e3);
                                }
                            }
                            bitmap = createScaledBitmap;
                        }
                    }
                    if (bitmap == null) {
                        bitmap = lottieImageAsset != null ? lottieImageAsset.bitmap : null;
                    }
                }
            }
            bitmap = null;
            if (bitmap == null) {
            }
        }
        if (bitmap == null || bitmap.isRecycled() || lottieImageAsset == null) {
            return;
        }
        float dpScale = Utils.dpScale();
        LPaint lPaint = this.paint;
        lPaint.setAlpha(i);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = this.colorFilterAnimation;
        if (valueCallbackKeyframeAnimation2 != null) {
            lPaint.setColorFilter((ColorFilter) valueCallbackKeyframeAnimation2.getValue());
        }
        canvas.save();
        canvas.concat(matrix);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect rect = this.src;
        rect.set(0, 0, width, height);
        lottieDrawable.getClass();
        Rect rect2 = this.dst;
        rect2.set(0, 0, (int) (bitmap.getWidth() * dpScale), (int) (bitmap.getHeight() * dpScale));
        canvas.drawBitmap(bitmap, rect, rect2, lPaint);
        canvas.restore();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        if (this.lottieImageAsset != null) {
            float dpScale = Utils.dpScale();
            rectF.set(0.0f, 0.0f, r3.width * dpScale, r3.height * dpScale);
            this.boundsMatrix.mapRect(rectF);
        }
    }
}
