package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import com.airbnb.lottie.C0179L;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.DrawingContent;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseLayer implements DrawingContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement {
    private final List<BaseKeyframeAnimation<?, ?>> animations;
    final Matrix boundsMatrix;
    private final LPaint clearPaint;
    private final String drawTraceName;
    private FloatKeyframeAnimation inOutAnimation;
    final Layer layerModel;
    final LottieDrawable lottieDrawable;
    private MaskKeyframeAnimation mask;
    private final RectF maskBoundsRect;
    private final RectF matteBoundsRect;
    private BaseLayer matteLayer;
    private final LPaint mattePaint;
    private boolean outlineMasksAndMattes;
    private LPaint outlineMasksAndMattesPaint;
    private BaseLayer parentLayer;
    private List<BaseLayer> parentLayers;
    private final RectF rect;
    private final RectF tempMaskBoundsRect;
    final TransformKeyframeAnimation transform;
    private boolean visible;
    private final Path path = new Path();
    private final Matrix matrix = new Matrix();
    private final LPaint contentPaint = new LPaint(1);
    private final LPaint dstInPaint = new LPaint(PorterDuff.Mode.DST_IN, 0);
    private final LPaint dstOutPaint = new LPaint(PorterDuff.Mode.DST_OUT, 0);

    BaseLayer(LottieDrawable lottieDrawable, Layer layer) {
        LPaint lPaint = new LPaint(1);
        this.mattePaint = lPaint;
        this.clearPaint = new LPaint(PorterDuff.Mode.CLEAR);
        this.rect = new RectF();
        this.maskBoundsRect = new RectF();
        this.matteBoundsRect = new RectF();
        this.tempMaskBoundsRect = new RectF();
        this.boundsMatrix = new Matrix();
        this.animations = new ArrayList();
        this.visible = true;
        this.lottieDrawable = lottieDrawable;
        this.layerModel = layer;
        this.drawTraceName = layer.getName() + "#draw";
        if (layer.getMatteType() == Layer.MatteType.INVERT) {
            lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        } else {
            lPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        AnimatableTransform transform = layer.getTransform();
        transform.getClass();
        TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(transform);
        this.transform = transformKeyframeAnimation;
        transformKeyframeAnimation.addListener(this);
        if (layer.getMasks() != null && !layer.getMasks().isEmpty()) {
            MaskKeyframeAnimation maskKeyframeAnimation = new MaskKeyframeAnimation(layer.getMasks());
            this.mask = maskKeyframeAnimation;
            Iterator it = ((ArrayList) maskKeyframeAnimation.getMaskAnimations()).iterator();
            while (it.hasNext()) {
                ((BaseKeyframeAnimation) it.next()).addUpdateListener(this);
            }
            Iterator it2 = ((ArrayList) this.mask.getOpacityAnimations()).iterator();
            while (it2.hasNext()) {
                BaseKeyframeAnimation<?, ?> baseKeyframeAnimation = (BaseKeyframeAnimation) it2.next();
                addAnimation(baseKeyframeAnimation);
                baseKeyframeAnimation.addUpdateListener(this);
            }
        }
        Layer layer2 = this.layerModel;
        if (layer2.getInOutKeyframes().isEmpty()) {
            if (true != this.visible) {
                this.visible = true;
                this.lottieDrawable.invalidateSelf();
                return;
            }
            return;
        }
        FloatKeyframeAnimation floatKeyframeAnimation = new FloatKeyframeAnimation(layer2.getInOutKeyframes());
        this.inOutAnimation = floatKeyframeAnimation;
        floatKeyframeAnimation.setIsDiscrete();
        this.inOutAnimation.addUpdateListener(new BaseKeyframeAnimation.AnimationListener() { // from class: com.airbnb.lottie.model.layer.BaseLayer.1
            @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
            public final void onValueChanged() {
                BaseLayer baseLayer = BaseLayer.this;
                BaseLayer.access$100(baseLayer, baseLayer.inOutAnimation.getFloatValue() == 1.0f);
            }
        });
        boolean z = this.inOutAnimation.getValue().floatValue() == 1.0f;
        if (z != this.visible) {
            this.visible = z;
            this.lottieDrawable.invalidateSelf();
        }
        addAnimation(this.inOutAnimation);
    }

    static void access$100(BaseLayer baseLayer, boolean z) {
        if (z != baseLayer.visible) {
            baseLayer.visible = z;
            baseLayer.lottieDrawable.invalidateSelf();
        }
    }

    private void buildParentLayerListIfNeeded() {
        if (this.parentLayers != null) {
            return;
        }
        if (this.parentLayer == null) {
            this.parentLayers = Collections.emptyList();
            return;
        }
        this.parentLayers = new ArrayList();
        for (BaseLayer baseLayer = this.parentLayer; baseLayer != null; baseLayer = baseLayer.parentLayer) {
            this.parentLayers.add(baseLayer);
        }
    }

    private void clearCanvas(Canvas canvas) {
        RectF rectF = this.rect;
        canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.clearPaint);
        C0179L.endSection();
    }

    public final void addAnimation(BaseKeyframeAnimation<?, ?> baseKeyframeAnimation) {
        if (baseKeyframeAnimation == null) {
            return;
        }
        this.animations.add(baseKeyframeAnimation);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        this.transform.applyValueCallback(lottieValueCallback, obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0242  */
    @Override // com.airbnb.lottie.animation.content.DrawingContent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        float f;
        int i2;
        Layer layer;
        LottieDrawable lottieDrawable;
        LPaint lPaint;
        LottieDrawable lottieDrawable2;
        boolean z;
        if (this.visible) {
            Layer layer2 = this.layerModel;
            if (!layer2.isHidden()) {
                buildParentLayerListIfNeeded();
                Matrix matrix2 = this.matrix;
                matrix2.reset();
                matrix2.set(matrix);
                int i3 = 1;
                for (int size = this.parentLayers.size() - 1; size >= 0; size--) {
                    matrix2.preConcat(this.parentLayers.get(size).transform.getMatrix());
                }
                C0179L.endSection();
                TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
                int intValue = (int) ((((i / 255.0f) * (transformKeyframeAnimation.getOpacity() == null ? 100 : transformKeyframeAnimation.getOpacity().getValue().intValue())) / 100.0f) * 255.0f);
                boolean z2 = this.matteLayer != null;
                LottieDrawable lottieDrawable3 = this.lottieDrawable;
                if (!z2 && !hasMasksOnThisLayer()) {
                    matrix2.preConcat(transformKeyframeAnimation.getMatrix());
                    drawLayer(canvas, matrix2, intValue);
                    C0179L.endSection();
                    C0179L.endSection();
                    lottieDrawable3.getComposition().getPerformanceTracker().recordRenderTime(layer2.getName());
                    return;
                }
                RectF rectF = this.rect;
                getBounds(rectF, matrix2, false);
                if ((this.matteLayer != null) && layer2.getMatteType() != Layer.MatteType.INVERT) {
                    RectF rectF2 = this.matteBoundsRect;
                    rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
                    this.matteLayer.getBounds(rectF2, matrix, true);
                    if (!rectF.intersect(rectF2)) {
                        rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
                    }
                }
                matrix2.preConcat(transformKeyframeAnimation.getMatrix());
                RectF rectF3 = this.maskBoundsRect;
                rectF3.set(0.0f, 0.0f, 0.0f, 0.0f);
                boolean hasMasksOnThisLayer = hasMasksOnThisLayer();
                Path path = this.path;
                int i4 = 2;
                if (hasMasksOnThisLayer) {
                    int size2 = this.mask.getMasks().size();
                    int i5 = 0;
                    while (true) {
                        if (i5 < size2) {
                            Mask mask = this.mask.getMasks().get(i5);
                            path.set((Path) ((BaseKeyframeAnimation) ((ArrayList) this.mask.getMaskAnimations()).get(i5)).getValue());
                            path.transform(matrix2);
                            int ordinal = mask.getMaskMode().ordinal();
                            if (ordinal != 0) {
                                if (ordinal == i3) {
                                    break;
                                }
                                if (ordinal != i4) {
                                    if (ordinal == 3) {
                                        break;
                                    }
                                    RectF rectF4 = this.tempMaskBoundsRect;
                                    path.computeBounds(rectF4, false);
                                    if (i5 != 0) {
                                        rectF3.set(rectF4);
                                        i2 = size2;
                                    } else {
                                        i2 = size2;
                                        rectF3.set(Math.min(rectF3.left, rectF4.left), Math.min(rectF3.top, rectF4.top), Math.max(rectF3.right, rectF4.right), Math.max(rectF3.bottom, rectF4.bottom));
                                    }
                                    i5++;
                                    size2 = i2;
                                    i3 = 1;
                                    i4 = 2;
                                }
                            }
                            if (mask.isInverted()) {
                                break;
                            }
                            RectF rectF42 = this.tempMaskBoundsRect;
                            path.computeBounds(rectF42, false);
                            if (i5 != 0) {
                            }
                            i5++;
                            size2 = i2;
                            i3 = 1;
                            i4 = 2;
                        } else if (!rectF.intersect(rectF3)) {
                            f = 0.0f;
                            rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
                        }
                    }
                    f = 0.0f;
                } else {
                    f = 0.0f;
                }
                if (!rectF.intersect(f, f, canvas.getWidth(), canvas.getHeight())) {
                    rectF.set(f, f, f, f);
                }
                C0179L.endSection();
                if (rectF.isEmpty()) {
                    layer = layer2;
                    lottieDrawable = lottieDrawable3;
                } else {
                    LPaint lPaint2 = this.contentPaint;
                    lPaint2.setAlpha(255);
                    int i6 = Utils.$r8$clinit;
                    canvas.saveLayer(rectF, lPaint2);
                    C0179L.endSection();
                    C0179L.endSection();
                    clearCanvas(canvas);
                    drawLayer(canvas, matrix2, intValue);
                    C0179L.endSection();
                    if (hasMasksOnThisLayer()) {
                        LPaint lPaint3 = this.dstInPaint;
                        canvas.saveLayer(rectF, lPaint3);
                        C0179L.endSection();
                        C0179L.endSection();
                        int i7 = 0;
                        while (i7 < this.mask.getMasks().size()) {
                            Mask mask2 = this.mask.getMasks().get(i7);
                            BaseKeyframeAnimation baseKeyframeAnimation = (BaseKeyframeAnimation) ((ArrayList) this.mask.getMaskAnimations()).get(i7);
                            BaseKeyframeAnimation baseKeyframeAnimation2 = (BaseKeyframeAnimation) ((ArrayList) this.mask.getOpacityAnimations()).get(i7);
                            int ordinal2 = mask2.getMaskMode().ordinal();
                            Layer layer3 = layer2;
                            LPaint lPaint4 = this.dstOutPaint;
                            if (ordinal2 != 0) {
                                lottieDrawable2 = lottieDrawable3;
                                if (ordinal2 == 1) {
                                    if (i7 == 0) {
                                        lPaint2.setColor(-16777216);
                                        lPaint2.setAlpha(255);
                                        canvas.drawRect(rectF, lPaint2);
                                    }
                                    if (mask2.isInverted()) {
                                        int i8 = Utils.$r8$clinit;
                                        canvas.saveLayer(rectF, lPaint4);
                                        C0179L.endSection();
                                        canvas.drawRect(rectF, lPaint2);
                                        lPaint4.setAlpha((int) (((Integer) baseKeyframeAnimation2.getValue()).intValue() * 2.55f));
                                        path.set((Path) baseKeyframeAnimation.getValue());
                                        path.transform(matrix2);
                                        canvas.drawPath(path, lPaint4);
                                        canvas.restore();
                                    } else {
                                        path.set((Path) baseKeyframeAnimation.getValue());
                                        path.transform(matrix2);
                                        canvas.drawPath(path, lPaint4);
                                    }
                                } else if (ordinal2 != 2) {
                                    if (ordinal2 == 3) {
                                        if (!((ArrayList) this.mask.getMaskAnimations()).isEmpty()) {
                                            for (int i9 = 0; i9 < this.mask.getMasks().size(); i9++) {
                                                if (this.mask.getMasks().get(i9).getMaskMode() == Mask.MaskMode.MASK_MODE_NONE) {
                                                }
                                            }
                                            z = true;
                                            if (z) {
                                                lPaint2.setAlpha(255);
                                                canvas.drawRect(rectF, lPaint2);
                                            }
                                        }
                                        z = false;
                                        if (z) {
                                        }
                                    }
                                } else if (mask2.isInverted()) {
                                    int i10 = Utils.$r8$clinit;
                                    canvas.saveLayer(rectF, lPaint3);
                                    C0179L.endSection();
                                    canvas.drawRect(rectF, lPaint2);
                                    lPaint4.setAlpha((int) (((Integer) baseKeyframeAnimation2.getValue()).intValue() * 2.55f));
                                    path.set((Path) baseKeyframeAnimation.getValue());
                                    path.transform(matrix2);
                                    canvas.drawPath(path, lPaint4);
                                    canvas.restore();
                                } else {
                                    int i11 = Utils.$r8$clinit;
                                    canvas.saveLayer(rectF, lPaint3);
                                    C0179L.endSection();
                                    path.set((Path) baseKeyframeAnimation.getValue());
                                    path.transform(matrix2);
                                    lPaint2.setAlpha((int) (((Integer) baseKeyframeAnimation2.getValue()).intValue() * 2.55f));
                                    canvas.drawPath(path, lPaint2);
                                    canvas.restore();
                                }
                            } else {
                                lottieDrawable2 = lottieDrawable3;
                                if (mask2.isInverted()) {
                                    int i12 = Utils.$r8$clinit;
                                    canvas.saveLayer(rectF, lPaint2);
                                    C0179L.endSection();
                                    canvas.drawRect(rectF, lPaint2);
                                    path.set((Path) baseKeyframeAnimation.getValue());
                                    path.transform(matrix2);
                                    lPaint2.setAlpha((int) (((Integer) baseKeyframeAnimation2.getValue()).intValue() * 2.55f));
                                    canvas.drawPath(path, lPaint4);
                                    canvas.restore();
                                } else {
                                    path.set((Path) baseKeyframeAnimation.getValue());
                                    path.transform(matrix2);
                                    lPaint2.setAlpha((int) (((Integer) baseKeyframeAnimation2.getValue()).intValue() * 2.55f));
                                    canvas.drawPath(path, lPaint2);
                                }
                            }
                            i7++;
                            layer2 = layer3;
                            lottieDrawable3 = lottieDrawable2;
                        }
                        layer = layer2;
                        lottieDrawable = lottieDrawable3;
                        canvas.restore();
                        C0179L.endSection();
                    } else {
                        layer = layer2;
                        lottieDrawable = lottieDrawable3;
                    }
                    if (this.matteLayer != null) {
                        canvas.saveLayer(rectF, this.mattePaint);
                        C0179L.endSection();
                        C0179L.endSection();
                        clearCanvas(canvas);
                        this.matteLayer.draw(canvas, matrix, intValue);
                        canvas.restore();
                        C0179L.endSection();
                        C0179L.endSection();
                    }
                    canvas.restore();
                    C0179L.endSection();
                }
                if (this.outlineMasksAndMattes && (lPaint = this.outlineMasksAndMattesPaint) != null) {
                    lPaint.setStyle(Paint.Style.STROKE);
                    this.outlineMasksAndMattesPaint.setColor(-251901);
                    this.outlineMasksAndMattesPaint.setStrokeWidth(4.0f);
                    canvas.drawRect(rectF, this.outlineMasksAndMattesPaint);
                    this.outlineMasksAndMattesPaint.setStyle(Paint.Style.FILL);
                    this.outlineMasksAndMattesPaint.setColor(1357638635);
                    canvas.drawRect(rectF, this.outlineMasksAndMattesPaint);
                }
                C0179L.endSection();
                lottieDrawable.getComposition().getPerformanceTracker().recordRenderTime(layer.getName());
                return;
            }
        }
        C0179L.endSection();
    }

    abstract void drawLayer(Canvas canvas, Matrix matrix, int i);

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        buildParentLayerListIfNeeded();
        Matrix matrix2 = this.boundsMatrix;
        matrix2.set(matrix);
        if (z) {
            List<BaseLayer> list = this.parentLayers;
            if (list != null) {
                int size = list.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    } else {
                        matrix2.preConcat(this.parentLayers.get(size).transform.getMatrix());
                    }
                }
            } else {
                BaseLayer baseLayer = this.parentLayer;
                if (baseLayer != null) {
                    matrix2.preConcat(baseLayer.transform.getMatrix());
                }
            }
        }
        matrix2.preConcat(this.transform.getMatrix());
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.layerModel.getName();
    }

    final boolean hasMasksOnThisLayer() {
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        return (maskKeyframeAnimation == null || ((ArrayList) maskKeyframeAnimation.getMaskAnimations()).isEmpty()) ? false : true;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public final void removeAnimation(BaseKeyframeAnimation<?, ?> baseKeyframeAnimation) {
        ((ArrayList) this.animations).remove(baseKeyframeAnimation);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        if (keyPath.matches(i, getName())) {
            if (!"__container".equals(getName())) {
                keyPath2 = keyPath2.addKey(getName());
                if (keyPath.fullyResolvesTo(i, getName())) {
                    ((ArrayList) list).add(keyPath2.resolve(this));
                }
            }
            if (keyPath.propagateToChildren(i, getName())) {
                resolveChildKeyPath(keyPath, keyPath.incrementDepthBy(i, getName()) + i, list, keyPath2);
            }
        }
    }

    final void setMatteLayer(BaseLayer baseLayer) {
        this.matteLayer = baseLayer;
    }

    void setOutlineMasksAndMattes(boolean z) {
        if (z && this.outlineMasksAndMattesPaint == null) {
            this.outlineMasksAndMattesPaint = new LPaint();
        }
        this.outlineMasksAndMattes = z;
    }

    final void setParentLayer(BaseLayer baseLayer) {
        this.parentLayer = baseLayer;
    }

    void setProgress(float f) {
        this.transform.setProgress(f);
        int i = 0;
        if (this.mask != null) {
            for (int i2 = 0; i2 < ((ArrayList) this.mask.getMaskAnimations()).size(); i2++) {
                ((BaseKeyframeAnimation) ((ArrayList) this.mask.getMaskAnimations()).get(i2)).setProgress(f);
            }
        }
        Layer layer = this.layerModel;
        if (layer.getTimeStretch() != 0.0f) {
            f /= layer.getTimeStretch();
        }
        FloatKeyframeAnimation floatKeyframeAnimation = this.inOutAnimation;
        if (floatKeyframeAnimation != null) {
            floatKeyframeAnimation.setProgress(f / layer.getTimeStretch());
        }
        BaseLayer baseLayer = this.matteLayer;
        if (baseLayer != null) {
            this.matteLayer.setProgress(baseLayer.layerModel.getTimeStretch() * f);
        }
        while (true) {
            List<BaseKeyframeAnimation<?, ?>> list = this.animations;
            if (i >= ((ArrayList) list).size()) {
                return;
            }
            ((BaseKeyframeAnimation) ((ArrayList) list).get(i)).setProgress(f);
            i++;
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List<Content> list, List<Content> list2) {
    }

    void resolveChildKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
    }
}
