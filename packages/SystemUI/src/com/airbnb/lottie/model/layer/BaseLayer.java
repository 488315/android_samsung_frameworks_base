package com.airbnb.lottie.model.layer;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.PerformanceTracker;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.content.DrawingContent;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.utils.MeanCalculator;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BaseLayer implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement {
    public final List animations;
    public BlurMaskFilter blurMaskFilter;
    public float blurMaskFilterRadius;
    public final Matrix boundsMatrix;
    public final RectF canvasBounds;
    public final LPaint clearPaint;
    public final LPaint dstInPaint;
    public final LPaint dstOutPaint;
    public final FloatKeyframeAnimation inOutAnimation;
    public final Layer layerModel;
    public final LottieDrawable lottieDrawable;
    public final MaskKeyframeAnimation mask;
    public final RectF maskBoundsRect;
    public final RectF matteBoundsRect;
    public BaseLayer matteLayer;
    public final LPaint mattePaint;
    public boolean outlineMasksAndMattes;
    public LPaint outlineMasksAndMattesPaint;
    public BaseLayer parentLayer;
    public List parentLayers;
    public final RectF rect;
    public final RectF tempMaskBoundsRect;
    public final TransformKeyframeAnimation transform;
    public boolean visible;
    public final Path path = new Path();
    public final Matrix matrix = new Matrix();
    public final Matrix canvasMatrix = new Matrix();
    public final LPaint contentPaint = new LPaint(1);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.airbnb.lottie.model.layer.BaseLayer$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode;
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType;

        static {
            int[] iArr = new int[Mask.MaskMode.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode = iArr;
            try {
                iArr[Mask.MaskMode.MASK_MODE_NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[Mask.MaskMode.MASK_MODE_SUBTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[Mask.MaskMode.MASK_MODE_INTERSECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[Mask.MaskMode.MASK_MODE_ADD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Layer.LayerType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType = iArr2;
            try {
                iArr2[Layer.LayerType.SHAPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.PRE_COMP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.SOLID.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.IMAGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.NULL.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.TEXT.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType[Layer.LayerType.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public BaseLayer(LottieDrawable lottieDrawable, Layer layer) {
        PorterDuff.Mode mode = PorterDuff.Mode.DST_IN;
        this.dstInPaint = new LPaint(1, mode);
        PorterDuff.Mode mode2 = PorterDuff.Mode.DST_OUT;
        this.dstOutPaint = new LPaint(1, mode2);
        LPaint lPaint = new LPaint(1);
        this.mattePaint = lPaint;
        this.clearPaint = new LPaint(PorterDuff.Mode.CLEAR);
        this.rect = new RectF();
        this.canvasBounds = new RectF();
        this.maskBoundsRect = new RectF();
        this.matteBoundsRect = new RectF();
        this.tempMaskBoundsRect = new RectF();
        this.boundsMatrix = new Matrix();
        this.animations = new ArrayList();
        this.visible = true;
        this.blurMaskFilterRadius = 0.0f;
        this.lottieDrawable = lottieDrawable;
        this.layerModel = layer;
        String str = layer.layerName;
        if (layer.matteType == Layer.MatteType.INVERT) {
            lPaint.setXfermode(new PorterDuffXfermode(mode2));
        } else {
            lPaint.setXfermode(new PorterDuffXfermode(mode));
        }
        AnimatableTransform animatableTransform = layer.transform;
        animatableTransform.getClass();
        TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(animatableTransform);
        this.transform = transformKeyframeAnimation;
        transformKeyframeAnimation.addListener(this);
        List list = layer.masks;
        if (list != null && !list.isEmpty()) {
            MaskKeyframeAnimation maskKeyframeAnimation = new MaskKeyframeAnimation(layer.masks);
            this.mask = maskKeyframeAnimation;
            ArrayList arrayList = (ArrayList) maskKeyframeAnimation.maskAnimations;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((BaseKeyframeAnimation) obj).addUpdateListener(this);
            }
            ArrayList arrayList2 = (ArrayList) this.mask.opacityAnimations;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                BaseKeyframeAnimation baseKeyframeAnimation = (BaseKeyframeAnimation) obj2;
                addAnimation(baseKeyframeAnimation);
                baseKeyframeAnimation.addUpdateListener(this);
            }
        }
        Layer layer2 = this.layerModel;
        if (layer2.inOutKeyframes.isEmpty()) {
            if (true != this.visible) {
                this.visible = true;
                this.lottieDrawable.invalidateSelf();
                return;
            }
            return;
        }
        FloatKeyframeAnimation floatKeyframeAnimation = new FloatKeyframeAnimation(layer2.inOutKeyframes);
        this.inOutAnimation = floatKeyframeAnimation;
        floatKeyframeAnimation.isDiscrete = true;
        floatKeyframeAnimation.addUpdateListener(new BaseKeyframeAnimation.AnimationListener() { // from class: com.airbnb.lottie.model.layer.BaseLayer$$ExternalSyntheticLambda0
            @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
            public final void onValueChanged() {
                BaseLayer baseLayer = BaseLayer.this;
                boolean z = baseLayer.inOutAnimation.getFloatValue() == 1.0f;
                if (z != baseLayer.visible) {
                    baseLayer.visible = z;
                    baseLayer.lottieDrawable.invalidateSelf();
                }
            }
        });
        boolean z = ((Float) this.inOutAnimation.getValue()).floatValue() == 1.0f;
        if (z != this.visible) {
            this.visible = z;
            this.lottieDrawable.invalidateSelf();
        }
        addAnimation(this.inOutAnimation);
    }

    public final void addAnimation(BaseKeyframeAnimation baseKeyframeAnimation) {
        if (baseKeyframeAnimation == null) {
            return;
        }
        this.animations.add(baseKeyframeAnimation);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        this.transform.applyValueCallback(lottieValueCallback, obj);
    }

    public final void buildParentLayerListIfNeeded() {
        if (this.parentLayers != null) {
            return;
        }
        if (this.parentLayer == null) {
            this.parentLayers = Collections.EMPTY_LIST;
            return;
        }
        this.parentLayers = new ArrayList();
        for (BaseLayer baseLayer = this.parentLayer; baseLayer != null; baseLayer = baseLayer.parentLayer) {
            this.parentLayers.add(baseLayer);
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        float f;
        LPaint lPaint;
        float f2;
        int i2;
        char c;
        Integer num;
        int i3 = 1;
        if (this.visible) {
            Layer layer = this.layerModel;
            if (layer.hidden) {
                return;
            }
            buildParentLayerListIfNeeded();
            this.matrix.reset();
            this.matrix.set(matrix);
            for (int size = this.parentLayers.size() - 1; size >= 0; size--) {
                this.matrix.preConcat(((BaseLayer) this.parentLayers.get(size)).transform.getMatrix());
            }
            TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
            BaseKeyframeAnimation baseKeyframeAnimation = transformKeyframeAnimation.opacity;
            int intValue = (int) ((((i / 255.0f) * ((baseKeyframeAnimation == null || (num = (Integer) baseKeyframeAnimation.getValue()) == null) ? 100 : num.intValue())) / 100.0f) * 255.0f);
            if (!(this.matteLayer != null) && !hasMasksOnThisLayer()) {
                this.matrix.preConcat(transformKeyframeAnimation.getMatrix());
                drawLayer(canvas, this.matrix, intValue);
                recordRenderTime();
                return;
            }
            getBounds(this.rect, this.matrix, false);
            RectF rectF = this.rect;
            if (this.matteLayer != null) {
                if (layer.matteType != Layer.MatteType.INVERT) {
                    this.matteBoundsRect.set(0.0f, 0.0f, 0.0f, 0.0f);
                    this.matteLayer.getBounds(this.matteBoundsRect, matrix, true);
                    if (!rectF.intersect(this.matteBoundsRect)) {
                        rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                }
            }
            this.matrix.preConcat(transformKeyframeAnimation.getMatrix());
            RectF rectF2 = this.rect;
            Matrix matrix2 = this.matrix;
            this.maskBoundsRect.set(0.0f, 0.0f, 0.0f, 0.0f);
            boolean hasMasksOnThisLayer = hasMasksOnThisLayer();
            MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
            int i4 = 4;
            int i5 = 3;
            int i6 = 2;
            if (hasMasksOnThisLayer) {
                int size2 = maskKeyframeAnimation.masks.size();
                int i7 = 0;
                while (true) {
                    if (i7 < size2) {
                        Mask mask = (Mask) maskKeyframeAnimation.masks.get(i7);
                        Path path = (Path) ((BaseKeyframeAnimation) ((ArrayList) maskKeyframeAnimation.maskAnimations).get(i7)).getValue();
                        if (path != null) {
                            this.path.set(path);
                            this.path.transform(matrix2);
                            int i8 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[mask.maskMode.ordinal()];
                            if (i8 == 1 || i8 == i6 || ((i8 == i5 || i8 == i4) && mask.inverted)) {
                                break;
                            }
                            this.path.computeBounds(this.tempMaskBoundsRect, false);
                            if (i7 == 0) {
                                this.maskBoundsRect.set(this.tempMaskBoundsRect);
                            } else {
                                RectF rectF3 = this.maskBoundsRect;
                                rectF3.set(Math.min(rectF3.left, this.tempMaskBoundsRect.left), Math.min(this.maskBoundsRect.top, this.tempMaskBoundsRect.top), Math.max(this.maskBoundsRect.right, this.tempMaskBoundsRect.right), Math.max(this.maskBoundsRect.bottom, this.tempMaskBoundsRect.bottom));
                            }
                        }
                        i7++;
                        i4 = 4;
                        i5 = 3;
                        i6 = 2;
                    } else if (!rectF2.intersect(this.maskBoundsRect)) {
                        f = 0.0f;
                        rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                }
                f = 0.0f;
            } else {
                f = 0.0f;
            }
            this.canvasBounds.set(f, f, canvas.getWidth(), canvas.getHeight());
            canvas.getMatrix(this.canvasMatrix);
            if (!this.canvasMatrix.isIdentity()) {
                Matrix matrix3 = this.canvasMatrix;
                matrix3.invert(matrix3);
                this.canvasMatrix.mapRect(this.canvasBounds);
            }
            if (!this.rect.intersect(this.canvasBounds)) {
                this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            }
            float f3 = 1.0f;
            if (this.rect.width() >= 1.0f && this.rect.height() >= 1.0f) {
                LPaint lPaint2 = this.contentPaint;
                lPaint2.setAlpha(255);
                RectF rectF4 = this.rect;
                Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
                canvas.saveLayer(rectF4, lPaint2);
                RectF rectF5 = this.rect;
                float f4 = rectF5.left - 1.0f;
                float f5 = rectF5.top - 1.0f;
                float f6 = rectF5.right + 1.0f;
                float f7 = rectF5.bottom + 1.0f;
                LPaint lPaint3 = this.clearPaint;
                canvas.drawRect(f4, f5, f6, f7, lPaint3);
                drawLayer(canvas, this.matrix, intValue);
                if (hasMasksOnThisLayer()) {
                    Matrix matrix4 = this.matrix;
                    RectF rectF6 = this.rect;
                    LPaint lPaint4 = this.dstInPaint;
                    canvas.saveLayer(rectF6, lPaint4);
                    int i9 = 0;
                    while (i9 < maskKeyframeAnimation.masks.size()) {
                        Mask mask2 = (Mask) maskKeyframeAnimation.masks.get(i9);
                        BaseKeyframeAnimation baseKeyframeAnimation2 = (BaseKeyframeAnimation) ((ArrayList) maskKeyframeAnimation.maskAnimations).get(i9);
                        BaseKeyframeAnimation baseKeyframeAnimation3 = (BaseKeyframeAnimation) ((ArrayList) maskKeyframeAnimation.opacityAnimations).get(i9);
                        float f8 = f3;
                        int i10 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[mask2.maskMode.ordinal()];
                        if (i10 != i3) {
                            i2 = i3;
                            LPaint lPaint5 = this.dstOutPaint;
                            boolean z = mask2.inverted;
                            if (i10 == 2) {
                                if (i9 == 0) {
                                    lPaint2.setColor(-16777216);
                                    lPaint2.setAlpha(255);
                                    canvas.drawRect(this.rect, lPaint2);
                                }
                                if (z) {
                                    RectF rectF7 = this.rect;
                                    Utils.AnonymousClass1 anonymousClass12 = Utils.threadLocalPathMeasure;
                                    canvas.saveLayer(rectF7, lPaint5);
                                    canvas.drawRect(this.rect, lPaint2);
                                    lPaint5.setAlpha((int) (((Integer) baseKeyframeAnimation3.getValue()).intValue() * 2.55f));
                                    this.path.set((Path) baseKeyframeAnimation2.getValue());
                                    this.path.transform(matrix4);
                                    canvas.drawPath(this.path, lPaint5);
                                    canvas.restore();
                                } else {
                                    this.path.set((Path) baseKeyframeAnimation2.getValue());
                                    this.path.transform(matrix4);
                                    canvas.drawPath(this.path, lPaint5);
                                }
                            } else if (i10 != 3) {
                                if (i10 == 4) {
                                    if (z) {
                                        RectF rectF8 = this.rect;
                                        Utils.AnonymousClass1 anonymousClass13 = Utils.threadLocalPathMeasure;
                                        canvas.saveLayer(rectF8, lPaint2);
                                        canvas.drawRect(this.rect, lPaint2);
                                        this.path.set((Path) baseKeyframeAnimation2.getValue());
                                        this.path.transform(matrix4);
                                        lPaint2.setAlpha((int) (((Integer) baseKeyframeAnimation3.getValue()).intValue() * 2.55f));
                                        canvas.drawPath(this.path, lPaint5);
                                        canvas.restore();
                                    } else {
                                        this.path.set((Path) baseKeyframeAnimation2.getValue());
                                        this.path.transform(matrix4);
                                        lPaint2.setAlpha((int) (((Integer) baseKeyframeAnimation3.getValue()).intValue() * 2.55f));
                                        canvas.drawPath(this.path, lPaint2);
                                    }
                                }
                            } else if (z) {
                                RectF rectF9 = this.rect;
                                Utils.AnonymousClass1 anonymousClass14 = Utils.threadLocalPathMeasure;
                                canvas.saveLayer(rectF9, lPaint4);
                                canvas.drawRect(this.rect, lPaint2);
                                lPaint5.setAlpha((int) (((Integer) baseKeyframeAnimation3.getValue()).intValue() * 2.55f));
                                this.path.set((Path) baseKeyframeAnimation2.getValue());
                                this.path.transform(matrix4);
                                canvas.drawPath(this.path, lPaint5);
                                canvas.restore();
                            } else {
                                RectF rectF10 = this.rect;
                                Utils.AnonymousClass1 anonymousClass15 = Utils.threadLocalPathMeasure;
                                canvas.saveLayer(rectF10, lPaint4);
                                this.path.set((Path) baseKeyframeAnimation2.getValue());
                                this.path.transform(matrix4);
                                lPaint2.setAlpha((int) (((Integer) baseKeyframeAnimation3.getValue()).intValue() * 2.55f));
                                canvas.drawPath(this.path, lPaint2);
                                canvas.restore();
                            }
                        } else {
                            i2 = i3;
                            if (!((ArrayList) maskKeyframeAnimation.maskAnimations).isEmpty()) {
                                for (int i11 = 0; i11 < maskKeyframeAnimation.masks.size(); i11++) {
                                    if (((Mask) maskKeyframeAnimation.masks.get(i11)).maskMode == Mask.MaskMode.MASK_MODE_NONE) {
                                    }
                                }
                                c = 255;
                                lPaint2.setAlpha(255);
                                canvas.drawRect(this.rect, lPaint2);
                                i9++;
                                i3 = i2;
                                f3 = f8;
                            }
                        }
                        c = 255;
                        i9++;
                        i3 = i2;
                        f3 = f8;
                    }
                    f2 = f3;
                    canvas.restore();
                } else {
                    f2 = 1.0f;
                }
                if (this.matteLayer != null) {
                    canvas.saveLayer(this.rect, this.mattePaint);
                    RectF rectF11 = this.rect;
                    canvas.drawRect(rectF11.left - f2, rectF11.top - f2, rectF11.right + f2, rectF11.bottom + f2, lPaint3);
                    this.matteLayer.draw(canvas, matrix, intValue);
                    canvas.restore();
                }
                canvas.restore();
            }
            if (this.outlineMasksAndMattes && (lPaint = this.outlineMasksAndMattesPaint) != null) {
                lPaint.setStyle(Paint.Style.STROKE);
                this.outlineMasksAndMattesPaint.setColor(-251901);
                this.outlineMasksAndMattesPaint.setStrokeWidth(4.0f);
                canvas.drawRect(this.rect, this.outlineMasksAndMattesPaint);
                this.outlineMasksAndMattesPaint.setStyle(Paint.Style.FILL);
                this.outlineMasksAndMattesPaint.setColor(1357638635);
                canvas.drawRect(this.rect, this.outlineMasksAndMattesPaint);
            }
            recordRenderTime();
        }
    }

    public abstract void drawLayer(Canvas canvas, Matrix matrix, int i);

    public BlurEffect getBlurEffect() {
        return this.layerModel.blurEffect;
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        buildParentLayerListIfNeeded();
        this.boundsMatrix.set(matrix);
        if (z) {
            List list = this.parentLayers;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.boundsMatrix.preConcat(((BaseLayer) this.parentLayers.get(size)).transform.getMatrix());
                }
            } else {
                BaseLayer baseLayer = this.parentLayer;
                if (baseLayer != null) {
                    this.boundsMatrix.preConcat(baseLayer.transform.getMatrix());
                }
            }
        }
        this.boundsMatrix.preConcat(this.transform.getMatrix());
    }

    public DropShadowEffect getDropShadowEffect() {
        return this.layerModel.dropShadowEffect;
    }

    public final boolean hasMasksOnThisLayer() {
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        return (maskKeyframeAnimation == null || ((ArrayList) maskKeyframeAnimation.maskAnimations).isEmpty()) ? false : true;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public final void recordRenderTime() {
        PerformanceTracker performanceTracker = this.lottieDrawable.composition.performanceTracker;
        String str = this.layerModel.layerName;
        if (performanceTracker.enabled) {
            MeanCalculator meanCalculator = (MeanCalculator) ((HashMap) performanceTracker.layerRenderTimes).get(str);
            if (meanCalculator == null) {
                meanCalculator = new MeanCalculator();
                ((HashMap) performanceTracker.layerRenderTimes).put(str, meanCalculator);
            }
            int i = meanCalculator.n + 1;
            meanCalculator.n = i;
            if (i == Integer.MAX_VALUE) {
                meanCalculator.n = i / 2;
            }
            if (str.equals("__container")) {
                ArraySet arraySet = performanceTracker.frameListeners;
                arraySet.getClass();
                ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                if (elementIterator.hasNext()) {
                    elementIterator.next().getClass();
                    throw new ClassCastException();
                }
            }
        }
    }

    public final void removeAnimation(BaseKeyframeAnimation baseKeyframeAnimation) {
        ((ArrayList) this.animations).remove(baseKeyframeAnimation);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        BaseLayer baseLayer = this.matteLayer;
        Layer layer = this.layerModel;
        if (baseLayer != null) {
            KeyPath addKey = keyPath2.addKey(baseLayer.layerModel.layerName);
            if (keyPath.fullyResolvesTo(i, this.matteLayer.layerModel.layerName)) {
                ((ArrayList) list).add(addKey.resolve(this.matteLayer));
            }
            if (keyPath.propagateToChildren(i, layer.layerName)) {
                this.matteLayer.resolveChildKeyPath(keyPath, keyPath.incrementDepthBy(i, this.matteLayer.layerModel.layerName) + i, list, addKey);
            }
        }
        if (keyPath.matches(i, layer.layerName)) {
            String str = layer.layerName;
            if (!"__container".equals(str)) {
                keyPath2 = keyPath2.addKey(str);
                if (keyPath.fullyResolvesTo(i, str)) {
                    ((ArrayList) list).add(keyPath2.resolve(this));
                }
            }
            if (keyPath.propagateToChildren(i, str)) {
                resolveChildKeyPath(keyPath, keyPath.incrementDepthBy(i, str) + i, list, keyPath2);
            }
        }
    }

    public void setOutlineMasksAndMattes(boolean z) {
        if (z && this.outlineMasksAndMattesPaint == null) {
            this.outlineMasksAndMattesPaint = new LPaint();
        }
        this.outlineMasksAndMattes = z;
    }

    public void setProgress(float f) {
        TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
        BaseKeyframeAnimation baseKeyframeAnimation = transformKeyframeAnimation.opacity;
        if (baseKeyframeAnimation != null) {
            baseKeyframeAnimation.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation2 = transformKeyframeAnimation.startOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation3 = transformKeyframeAnimation.endOpacity;
        if (baseKeyframeAnimation3 != null) {
            baseKeyframeAnimation3.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation4 = transformKeyframeAnimation.anchorPoint;
        if (baseKeyframeAnimation4 != null) {
            baseKeyframeAnimation4.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation5 = transformKeyframeAnimation.position;
        if (baseKeyframeAnimation5 != null) {
            baseKeyframeAnimation5.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation6 = transformKeyframeAnimation.scale;
        if (baseKeyframeAnimation6 != null) {
            baseKeyframeAnimation6.setProgress(f);
        }
        BaseKeyframeAnimation baseKeyframeAnimation7 = transformKeyframeAnimation.rotation;
        if (baseKeyframeAnimation7 != null) {
            baseKeyframeAnimation7.setProgress(f);
        }
        FloatKeyframeAnimation floatKeyframeAnimation = transformKeyframeAnimation.skew;
        if (floatKeyframeAnimation != null) {
            floatKeyframeAnimation.setProgress(f);
        }
        FloatKeyframeAnimation floatKeyframeAnimation2 = transformKeyframeAnimation.skewAngle;
        if (floatKeyframeAnimation2 != null) {
            floatKeyframeAnimation2.setProgress(f);
        }
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        if (maskKeyframeAnimation != null) {
            for (int i = 0; i < ((ArrayList) maskKeyframeAnimation.maskAnimations).size(); i++) {
                ((BaseKeyframeAnimation) ((ArrayList) maskKeyframeAnimation.maskAnimations).get(i)).setProgress(f);
            }
        }
        FloatKeyframeAnimation floatKeyframeAnimation3 = this.inOutAnimation;
        if (floatKeyframeAnimation3 != null) {
            floatKeyframeAnimation3.setProgress(f);
        }
        BaseLayer baseLayer = this.matteLayer;
        if (baseLayer != null) {
            baseLayer.setProgress(f);
        }
        ((ArrayList) this.animations).size();
        for (int i2 = 0; i2 < ((ArrayList) this.animations).size(); i2++) {
            ((BaseKeyframeAnimation) ((ArrayList) this.animations).get(i2)).setProgress(f);
        }
        ((ArrayList) this.animations).size();
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
    }

    public void resolveChildKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
    }
}
