package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.DropShadowEffect;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShapeLayer extends BaseLayer {
    public final CompositionLayer compositionLayer;
    public final ContentGroup contentGroup;

    public ShapeLayer(LottieDrawable lottieDrawable, Layer layer, CompositionLayer compositionLayer, LottieComposition lottieComposition) {
        super(lottieDrawable, layer);
        this.compositionLayer = compositionLayer;
        ContentGroup contentGroup = new ContentGroup(lottieDrawable, this, new ShapeGroup("__container", layer.shapes, false), lottieComposition);
        this.contentGroup = contentGroup;
        contentGroup.setContents(Collections.emptyList(), Collections.emptyList());
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        this.contentGroup.draw(canvas, matrix, i);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final BlurEffect getBlurEffect() {
        BlurEffect blurEffect = this.layerModel.blurEffect;
        return blurEffect != null ? blurEffect : this.compositionLayer.layerModel.blurEffect;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        this.contentGroup.getBounds(rectF, this.boundsMatrix, z);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final DropShadowEffect getDropShadowEffect() {
        DropShadowEffect dropShadowEffect = this.layerModel.dropShadowEffect;
        return dropShadowEffect != null ? dropShadowEffect : this.compositionLayer.layerModel.dropShadowEffect;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public final void resolveChildKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        this.contentGroup.resolveKeyPath(keyPath, i, list, keyPath2);
    }
}
