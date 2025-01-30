package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CompositionLayer extends BaseLayer {
    public boolean clipToCompositionBounds;
    public final List layers;
    public final RectF newClipRect;
    public float progress;
    public final RectF rect;
    public BaseKeyframeAnimation timeRemapping;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.airbnb.lottie.model.layer.CompositionLayer$1 */
    public abstract /* synthetic */ class AbstractC05991 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType;

        static {
            int[] iArr = new int[Layer.MatteType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = iArr;
            try {
                iArr[Layer.MatteType.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[Layer.MatteType.INVERT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public CompositionLayer(LottieDrawable lottieDrawable, Layer layer, List<Layer> list, LottieComposition lottieComposition) {
        super(lottieDrawable, layer);
        BaseLayer baseLayer;
        BaseLayer shapeLayer;
        this.layers = new ArrayList();
        this.rect = new RectF();
        this.newClipRect = new RectF();
        new Paint();
        this.clipToCompositionBounds = true;
        AnimatableFloatValue animatableFloatValue = layer.timeRemapping;
        if (animatableFloatValue != null) {
            BaseKeyframeAnimation createAnimation = animatableFloatValue.createAnimation();
            this.timeRemapping = createAnimation;
            addAnimation(createAnimation);
            this.timeRemapping.addUpdateListener(this);
        } else {
            this.timeRemapping = null;
        }
        LongSparseArray longSparseArray = new LongSparseArray(lottieComposition.layers.size());
        int size = list.size() - 1;
        BaseLayer baseLayer2 = null;
        while (true) {
            if (size < 0) {
                for (int i = 0; i < longSparseArray.size(); i++) {
                    BaseLayer baseLayer3 = (BaseLayer) longSparseArray.get(longSparseArray.keyAt(i));
                    if (baseLayer3 != null && (baseLayer = (BaseLayer) longSparseArray.get(baseLayer3.layerModel.parentId)) != null) {
                        baseLayer3.parentLayer = baseLayer;
                    }
                }
                return;
            }
            Layer layer2 = list.get(size);
            switch (BaseLayer.AbstractC05981.$SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[layer2.layerType.ordinal()]) {
                case 1:
                    shapeLayer = new ShapeLayer(lottieDrawable, layer2, this, lottieComposition);
                    break;
                case 2:
                    shapeLayer = new CompositionLayer(lottieDrawable, layer2, (List) lottieComposition.precomps.get(layer2.refId), lottieComposition);
                    break;
                case 3:
                    shapeLayer = new SolidLayer(lottieDrawable, layer2);
                    break;
                case 4:
                    shapeLayer = new ImageLayer(lottieDrawable, layer2);
                    break;
                case 5:
                    shapeLayer = new NullLayer(lottieDrawable, layer2);
                    break;
                case 6:
                    shapeLayer = new TextLayer(lottieDrawable, layer2);
                    break;
                default:
                    StringBuilder sb = new StringBuilder("Unknown layer type ");
                    sb.append(layer2.layerType);
                    Logger.warning(sb.toString());
                    shapeLayer = null;
                    break;
            }
            if (shapeLayer != null) {
                longSparseArray.put(shapeLayer.layerModel.layerId, shapeLayer);
                if (baseLayer2 != null) {
                    baseLayer2.matteLayer = shapeLayer;
                    baseLayer2 = null;
                } else {
                    ((ArrayList) this.layers).add(0, shapeLayer);
                    int i2 = AbstractC05991.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[layer2.matteType.ordinal()];
                    if (i2 == 1 || i2 == 2) {
                        baseLayer2 = shapeLayer;
                    }
                }
            }
            size--;
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.TIME_REMAP) {
            if (lottieValueCallback == null) {
                BaseKeyframeAnimation baseKeyframeAnimation = this.timeRemapping;
                if (baseKeyframeAnimation != null) {
                    baseKeyframeAnimation.setValueCallback(null);
                    return;
                }
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.timeRemapping = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            addAnimation(this.timeRemapping);
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        RectF rectF = this.newClipRect;
        Layer layer = this.layerModel;
        rectF.set(0.0f, 0.0f, layer.preCompWidth, layer.preCompHeight);
        matrix.mapRect(rectF);
        this.lottieDrawable.getClass();
        canvas.save();
        ArrayList arrayList = (ArrayList) this.layers;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((!this.clipToCompositionBounds && "__container".equals(layer.layerName)) || rectF.isEmpty()) ? true : canvas.clipRect(rectF)) {
                ((BaseLayer) arrayList.get(size)).draw(canvas, matrix, i);
            }
        }
        canvas.restore();
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
    public final void resolveChildKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
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
    public final void setProgress(float f) {
        this.progress = f;
        super.setProgress(f);
        BaseKeyframeAnimation baseKeyframeAnimation = this.timeRemapping;
        Layer layer = this.layerModel;
        if (baseKeyframeAnimation != null) {
            LottieComposition lottieComposition = this.lottieDrawable.composition;
            f = ((((Float) baseKeyframeAnimation.getValue()).floatValue() * layer.composition.frameRate) - layer.composition.startFrame) / ((lottieComposition.endFrame - lottieComposition.startFrame) + 0.01f);
        }
        if (this.timeRemapping == null) {
            LottieComposition lottieComposition2 = layer.composition;
            f -= layer.startFrame / (lottieComposition2.endFrame - lottieComposition2.startFrame);
        }
        if (layer.timeStretch != 0.0f && !"__container".equals(layer.layerName)) {
            f /= layer.timeStretch;
        }
        List list = this.layers;
        for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
            ((BaseLayer) ((ArrayList) list).get(size)).setProgress(f);
        }
    }
}
