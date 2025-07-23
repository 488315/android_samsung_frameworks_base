package androidx.compose.ui.graphics.layer;

import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidBlendMode_androidKt;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CanvasHolder;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.RenderEffect;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.layer.CompositingStrategy;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GraphicsLayerV29 implements GraphicsLayerImpl {
    public float alpha;
    public long ambientShadowColor;
    public int blendMode;
    public float cameraDistance;
    public final CanvasDrawScope canvasDrawScope;
    public final CanvasHolder canvasHolder;
    public boolean clip;
    public boolean clipToBounds;
    public boolean clipToOutline;
    public ColorFilter colorFilter;
    public int compositingStrategy;
    public Paint layerPaint;
    public Matrix matrix;
    public boolean outlineIsProvided;
    public RenderEffect renderEffect;
    public final RenderNode renderNode;
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float scaleX;
    public float scaleY;
    public float shadowElevation;
    public long size;
    public long spotShadowColor;
    public float translationX;
    public float translationY;

    public GraphicsLayerV29(long j, CanvasHolder canvasHolder, CanvasDrawScope canvasDrawScope) {
        this.canvasHolder = canvasHolder;
        this.canvasDrawScope = canvasDrawScope;
        RenderNode renderNode = new RenderNode("graphicsLayer");
        this.renderNode = renderNode;
        Size.Companion.getClass();
        this.size = 0L;
        renderNode.setClipToBounds(false);
        CompositingStrategy.Companion companion = CompositingStrategy.Companion;
        companion.getClass();
        m560applyCompositingStrategyZ1X6vPc(renderNode, 0);
        this.alpha = 1.0f;
        BlendMode.Companion.getClass();
        this.blendMode = BlendMode.SrcOver;
        Offset.Companion.getClass();
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        Color.Companion companion2 = Color.Companion;
        companion2.getClass();
        long j2 = Color.Black;
        this.ambientShadowColor = j2;
        companion2.getClass();
        this.spotShadowColor = j2;
        this.cameraDistance = 8.0f;
        companion.getClass();
        this.compositingStrategy = 0;
    }

    public final void applyClip$1() {
        boolean z = this.clip;
        boolean z2 = false;
        boolean z3 = z && !this.outlineIsProvided;
        if (z && this.outlineIsProvided) {
            z2 = true;
        }
        if (z3 != this.clipToBounds) {
            this.clipToBounds = z3;
            this.renderNode.setClipToBounds(z3);
        }
        if (z2 != this.clipToOutline) {
            this.clipToOutline = z2;
            this.renderNode.setClipToOutline(z2);
        }
    }

    /* renamed from: applyCompositingStrategy-Z1X6vPc, reason: not valid java name */
    public final void m560applyCompositingStrategyZ1X6vPc(RenderNode renderNode, int i) {
        CompositingStrategy.Companion companion = CompositingStrategy.Companion;
        companion.getClass();
        if (i == CompositingStrategy.Offscreen) {
            renderNode.setUseCompositingLayer(true, this.layerPaint);
            renderNode.setHasOverlappingRendering(true);
            return;
        }
        companion.getClass();
        if (i == CompositingStrategy.ModulateAlpha) {
            renderNode.setUseCompositingLayer(false, this.layerPaint);
            renderNode.setHasOverlappingRendering(false);
        } else {
            renderNode.setUseCompositingLayer(false, this.layerPaint);
            renderNode.setHasOverlappingRendering(true);
        }
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final Matrix calculateMatrix() {
        Matrix matrix = this.matrix;
        if (matrix == null) {
            matrix = new Matrix();
            this.matrix = matrix;
        }
        this.renderNode.getMatrix(matrix);
        return matrix;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void discardDisplayList() {
        this.renderNode.discardDisplayList();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void draw(Canvas canvas) {
        android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        ((AndroidCanvas) canvas).internalCanvas.drawRenderNode(this.renderNode);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getAlpha() {
        return this.alpha;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: getAmbientShadowColor-0d7_KjU */
    public final long mo549getAmbientShadowColor0d7_KjU() {
        return this.ambientShadowColor;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: getBlendMode-0nO6VwU */
    public final int mo550getBlendMode0nO6VwU() {
        return this.blendMode;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getCameraDistance() {
        return this.cameraDistance;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: getCompositingStrategy-ke2Ky5w */
    public final int mo551getCompositingStrategyke2Ky5w() {
        return this.compositingStrategy;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final boolean getHasDisplayList() {
        return this.renderNode.hasDisplayList();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final RenderEffect getRenderEffect() {
        return this.renderEffect;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getRotationX() {
        return this.rotationX;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getRotationY() {
        return this.rotationY;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getRotationZ() {
        return this.rotationZ;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getScaleX() {
        return this.scaleX;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getScaleY() {
        return this.scaleY;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getShadowElevation() {
        return this.shadowElevation;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: getSpotShadowColor-0d7_KjU */
    public final long mo552getSpotShadowColor0d7_KjU() {
        return this.spotShadowColor;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getTranslationX() {
        return this.translationX;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final float getTranslationY() {
        return this.translationY;
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void record(Density density, LayoutDirection layoutDirection, GraphicsLayer graphicsLayer, Function1 function1) {
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        RecordingCanvas beginRecording = this.renderNode.beginRecording();
        try {
            CanvasHolder canvasHolder = this.canvasHolder;
            AndroidCanvas androidCanvas = canvasHolder.androidCanvas;
            android.graphics.Canvas canvas = androidCanvas.internalCanvas;
            androidCanvas.internalCanvas = beginRecording;
            CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
            canvasDrawScope$drawContext$1.setDensity(density);
            canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection);
            canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer;
            canvasDrawScope$drawContext$1.m527setSizeuvyYCjk(this.size);
            canvasDrawScope$drawContext$1.setCanvas(androidCanvas);
            ((GraphicsLayer$clipDrawBlock$1) function1).mo779invoke(canvasDrawScope);
            canvasHolder.androidCanvas.internalCanvas = canvas;
        } finally {
            this.renderNode.endRecording();
        }
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setAlpha(float f) {
        this.alpha = f;
        this.renderNode.setAlpha(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setAmbientShadowColor-8_81llA */
    public final void mo553setAmbientShadowColor8_81llA(long j) {
        this.ambientShadowColor = j;
        this.renderNode.setAmbientShadowColor(ColorKt.m467toArgb8_81llA(j));
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setBlendMode-s9anfk8 */
    public final void mo554setBlendModes9anfk8(int i) {
        this.blendMode = i;
        Paint paint = this.layerPaint;
        if (paint == null) {
            paint = new Paint();
            this.layerPaint = paint;
        }
        paint.setBlendMode(AndroidBlendMode_androidKt.m422toAndroidBlendModes9anfk8(i));
        updateLayerProperties$1();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setCameraDistance(float f) {
        this.cameraDistance = f;
        this.renderNode.setCameraDistance(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setClip(boolean z) {
        this.clip = z;
        applyClip$1();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        Paint paint = this.layerPaint;
        if (paint == null) {
            paint = new Paint();
            this.layerPaint = paint;
        }
        paint.setColorFilter(colorFilter != null ? colorFilter.nativeColorFilter : null);
        updateLayerProperties$1();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setCompositingStrategy-Wpw9cng */
    public final void mo555setCompositingStrategyWpw9cng(int i) {
        this.compositingStrategy = i;
        updateLayerProperties$1();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setOutline-O0kMr_c */
    public final void mo556setOutlineO0kMr_c(Outline outline, long j) {
        this.renderNode.setOutline(outline);
        this.outlineIsProvided = outline != null;
        applyClip$1();
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setPivotOffset-k-4lQ0M */
    public final void mo557setPivotOffsetk4lQ0M(long j) {
        if ((9223372034707292159L & j) == 9205357640488583168L) {
            this.renderNode.resetPivot();
        } else {
            this.renderNode.setPivotX(Float.intBitsToFloat((int) (j >> 32)));
            this.renderNode.setPivotY(Float.intBitsToFloat((int) (j & 4294967295L)));
        }
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setPosition-H0pRuoY */
    public final void mo558setPositionH0pRuoY(int i, int i2, long j) {
        this.renderNode.setPosition(i, i2, ((int) (j >> 32)) + i, ((int) (4294967295L & j)) + i2);
        this.size = IntSizeKt.m864toSizeozmzZPI(j);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setRenderEffect(RenderEffect renderEffect) {
        this.renderEffect = renderEffect;
        RenderNodeVerificationHelper renderNodeVerificationHelper = RenderNodeVerificationHelper.INSTANCE;
        RenderNode renderNode = this.renderNode;
        renderNodeVerificationHelper.getClass();
        renderNode.setRenderEffect(renderEffect != null ? renderEffect.asAndroidRenderEffect() : null);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setRotationX(float f) {
        this.rotationX = f;
        this.renderNode.setRotationX(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setRotationY(float f) {
        this.rotationY = f;
        this.renderNode.setRotationY(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setRotationZ(float f) {
        this.rotationZ = f;
        this.renderNode.setRotationZ(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setScaleX(float f) {
        this.scaleX = f;
        this.renderNode.setScaleX(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setScaleY(float f) {
        this.scaleY = f;
        this.renderNode.setScaleY(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setShadowElevation(float f) {
        this.shadowElevation = f;
        this.renderNode.setElevation(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    /* renamed from: setSpotShadowColor-8_81llA */
    public final void mo559setSpotShadowColor8_81llA(long j) {
        this.spotShadowColor = j;
        this.renderNode.setSpotShadowColor(ColorKt.m467toArgb8_81llA(j));
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setTranslationX(float f) {
        this.translationX = f;
        this.renderNode.setTranslationX(f);
    }

    @Override // androidx.compose.ui.graphics.layer.GraphicsLayerImpl
    public final void setTranslationY(float f) {
        this.translationY = f;
        this.renderNode.setTranslationY(f);
    }

    public final void updateLayerProperties$1() {
        int i = this.compositingStrategy;
        CompositingStrategy.Companion companion = CompositingStrategy.Companion;
        companion.getClass();
        int i2 = CompositingStrategy.Offscreen;
        if (i != i2) {
            int i3 = this.blendMode;
            BlendMode.Companion.getClass();
            if (i3 == BlendMode.SrcOver && this.colorFilter == null && this.renderEffect == null) {
                m560applyCompositingStrategyZ1X6vPc(this.renderNode, this.compositingStrategy);
                return;
            }
        }
        RenderNode renderNode = this.renderNode;
        companion.getClass();
        m560applyCompositingStrategyZ1X6vPc(renderNode, i2);
    }

    public /* synthetic */ GraphicsLayerV29(long j, CanvasHolder canvasHolder, CanvasDrawScope canvasDrawScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, (i & 2) != 0 ? new CanvasHolder() : canvasHolder, (i & 4) != 0 ? new CanvasDrawScope() : canvasDrawScope);
    }
}
