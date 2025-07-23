package androidx.compose.ui.platform;

import android.graphics.Matrix;
import android.graphics.RenderNode;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.CompositingStrategy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RenderNodeApi29 implements DeviceRenderNode {
    public int blendMode;
    public ColorFilter colorFilter;
    public int internalCompositingStrategy;
    public AndroidPaint layerPaint;
    public final RenderNode renderNode = new RenderNode("Compose");

    public RenderNodeApi29(AndroidComposeView androidComposeView) {
        CompositingStrategy.Companion.getClass();
        this.internalCompositingStrategy = 0;
        BlendMode.Companion.getClass();
        this.blendMode = BlendMode.SrcOver;
    }

    /* renamed from: applyCompositingStrategy-Qu9p0E8, reason: not valid java name */
    public final void m709applyCompositingStrategyQu9p0E8(RenderNode renderNode, int i) {
        CompositingStrategy.Companion.getClass();
        if (i == CompositingStrategy.Offscreen) {
            AndroidPaint androidPaint = this.layerPaint;
            renderNode.setUseCompositingLayer(true, androidPaint != null ? androidPaint.internalPaint : null);
            renderNode.setHasOverlappingRendering(true);
        } else if (i == CompositingStrategy.ModulateAlpha) {
            renderNode.setUseCompositingLayer(false, null);
            renderNode.setHasOverlappingRendering(false);
        } else {
            renderNode.setUseCompositingLayer(false, null);
            renderNode.setHasOverlappingRendering(true);
        }
    }

    @Override // androidx.compose.ui.platform.DeviceRenderNode
    public final void getMatrix(Matrix matrix) {
        this.renderNode.getMatrix(matrix);
    }

    public final void updateLayerProperties$1() {
        int i = this.internalCompositingStrategy;
        CompositingStrategy.Companion companion = CompositingStrategy.Companion;
        companion.getClass();
        int i2 = CompositingStrategy.Offscreen;
        if (i != i2) {
            int i3 = this.blendMode;
            BlendMode.Companion.getClass();
            if (i3 == BlendMode.SrcOver && this.colorFilter == null) {
                m709applyCompositingStrategyQu9p0E8(this.renderNode, this.internalCompositingStrategy);
                return;
            }
        }
        RenderNode renderNode = this.renderNode;
        companion.getClass();
        m709applyCompositingStrategyQu9p0E8(renderNode, i2);
    }
}
