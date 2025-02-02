package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.C0179L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class CompositionLayer extends BaseLayer {
    private Paint layerPaint;
    private final List<BaseLayer> layers;
    private final RectF newClipRect;
    private final RectF rect;
    private BaseKeyframeAnimation<Float, Float> timeRemapping;

    public CompositionLayer(LottieDrawable lottieDrawable, Layer layer, List<Layer> list, LottieComposition lottieComposition) {
        super(lottieDrawable, layer);
        int i;
        BaseLayer baseLayer;
        BaseLayer compositionLayer;
        this.layers = new ArrayList();
        this.rect = new RectF();
        this.newClipRect = new RectF();
        this.layerPaint = new Paint();
        AnimatableFloatValue timeRemapping = layer.getTimeRemapping();
        if (timeRemapping != null) {
            BaseKeyframeAnimation<Float, Float> createAnimation = timeRemapping.createAnimation();
            this.timeRemapping = createAnimation;
            addAnimation(createAnimation);
            this.timeRemapping.addUpdateListener(this);
        } else {
            this.timeRemapping = null;
        }
        LongSparseArray longSparseArray = new LongSparseArray(lottieComposition.getLayers().size());
        int size = list.size() - 1;
        BaseLayer baseLayer2 = null;
        while (true) {
            if (size < 0) {
                break;
            }
            Layer layer2 = list.get(size);
            int ordinal = layer2.getLayerType().ordinal();
            if (ordinal == 0) {
                compositionLayer = new CompositionLayer(lottieDrawable, layer2, lottieComposition.getPrecomps(layer2.getRefId()), lottieComposition);
            } else if (ordinal == 1) {
                compositionLayer = new SolidLayer(lottieDrawable, layer2);
            } else if (ordinal == 2) {
                compositionLayer = new ImageLayer(lottieDrawable, layer2);
            } else if (ordinal == 3) {
                compositionLayer = new NullLayer(lottieDrawable, layer2);
            } else if (ordinal == 4) {
                compositionLayer = new ShapeLayer(lottieDrawable, layer2);
            } else if (ordinal != 5) {
                Logger.warning("Unknown layer type " + layer2.getLayerType());
                compositionLayer = null;
            } else {
                compositionLayer = new TextLayer(lottieDrawable, layer2);
            }
            if (compositionLayer != null) {
                longSparseArray.put(compositionLayer.layerModel.getId(), compositionLayer);
                if (baseLayer2 != null) {
                    baseLayer2.setMatteLayer(compositionLayer);
                    baseLayer2 = null;
                } else {
                    ((ArrayList) this.layers).add(0, compositionLayer);
                    int ordinal2 = layer2.getMatteType().ordinal();
                    if (ordinal2 == 1 || ordinal2 == 2) {
                        baseLayer2 = compositionLayer;
                    }
                }
            }
            size--;
        }
        for (i = 0; i < longSparseArray.size(); i++) {
            BaseLayer baseLayer3 = (BaseLayer) longSparseArray.get(longSparseArray.keyAt(i));
            if (baseLayer3 != null && (baseLayer = (BaseLayer) longSparseArray.get(baseLayer3.layerModel.getParentId())) != null) {
                baseLayer3.setParentLayer(baseLayer);
            }
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.TIME_REMAP) {
            if (lottieValueCallback == null) {
                BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.timeRemapping;
                if (baseKeyframeAnimation != null) {
                    baseKeyframeAnimation.setValueCallback(null);
                    return;
                }
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.timeRemapping = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            addAnimation(this.timeRemapping);
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        RectF rectF = this.newClipRect;
        Layer layer = this.layerModel;
        rectF.set(0.0f, 0.0f, layer.getPreCompWidth(), layer.getPreCompHeight());
        matrix.mapRect(rectF);
        boolean isApplyingOpacityToLayersEnabled = this.lottieDrawable.isApplyingOpacityToLayersEnabled();
        List<BaseLayer> list = this.layers;
        boolean z = isApplyingOpacityToLayersEnabled && ((ArrayList) list).size() > 1 && i != 255;
        if (z) {
            this.layerPaint.setAlpha(i);
            Paint paint = this.layerPaint;
            int i2 = Utils.$r8$clinit;
            canvas.saveLayer(rectF, paint);
            C0179L.endSection();
        } else {
            canvas.save();
        }
        if (z) {
            i = 255;
        }
        ArrayList arrayList = (ArrayList) list;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (!rectF.isEmpty() ? canvas.clipRect(rectF) : true) {
                ((BaseLayer) arrayList.get(size)).draw(canvas, matrix, i);
            }
        }
        canvas.restore();
        C0179L.endSection();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        ArrayList arrayList = (ArrayList) this.layers;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            RectF rectF2 = this.rect;
            rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
            ((BaseLayer) arrayList.get(size)).getBounds(rectF2, this.boundsMatrix, true);
            rectF.union(rectF2);
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    protected final void resolveChildKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        int i2 = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.layers;
            if (i2 >= arrayList.size()) {
                return;
            }
            ((BaseLayer) arrayList.get(i2)).resolveKeyPath(keyPath, i, list, keyPath2);
            i2++;
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void setOutlineMasksAndMattes(boolean z) {
        super.setOutlineMasksAndMattes(z);
        Iterator<BaseLayer> it = this.layers.iterator();
        while (it.hasNext()) {
            it.next().setOutlineMasksAndMattes(z);
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void setProgress(float f) {
        super.setProgress(f);
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.timeRemapping;
        Layer layer = this.layerModel;
        if (baseKeyframeAnimation != null) {
            f = ((layer.getComposition().getFrameRate() * this.timeRemapping.getValue().floatValue()) - layer.getComposition().getStartFrame()) / (this.lottieDrawable.getComposition().getDurationFrames() + 0.01f);
        }
        if (this.timeRemapping == null) {
            f -= layer.getStartProgress();
        }
        if (layer.getTimeStretch() != 0.0f) {
            f /= layer.getTimeStretch();
        }
        ArrayList arrayList = (ArrayList) this.layers;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                ((BaseLayer) arrayList.get(size)).setProgress(f);
            }
        }
    }
}
