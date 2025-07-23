package androidx.compose.ui.platform;

import android.graphics.Matrix;
import android.graphics.RenderNode;
import android.view.ViewParent;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CanvasHolder;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RenderEffect;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.unit.IntOffset;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RenderNodeLayer implements OwnedLayer {
    public static final Function2 getMatrix;
    public Function2 drawBlock;
    public boolean drawnWithZ;
    public Function0 invalidateParentLayer;
    public boolean isDestroyed;
    public boolean isDirty;
    public int mutatedFields;
    public final AndroidComposeView ownerView;
    public final RenderNodeApi29 renderNode;
    public AndroidPaint softwareLayerPaint;
    public long transformOrigin;
    public final OutlineResolver outlineResolver = new OutlineResolver();
    public final LayerMatrixCache matrixCache = new LayerMatrixCache(getMatrix);
    public final CanvasHolder canvasHolder = new CanvasHolder();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        getMatrix = new Function2() { // from class: androidx.compose.ui.platform.RenderNodeLayer$Companion$getMatrix$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((DeviceRenderNode) obj).getMatrix((Matrix) obj2);
                return Unit.INSTANCE;
            }
        };
    }

    public RenderNodeLayer(AndroidComposeView androidComposeView, Function2 function2, Function0 function0) {
        this.ownerView = androidComposeView;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        TransformOrigin.Companion.getClass();
        this.transformOrigin = TransformOrigin.Center;
        RenderNodeApi29 renderNodeApi29 = new RenderNodeApi29(androidComposeView);
        renderNodeApi29.renderNode.setHasOverlappingRendering(true);
        renderNodeApi29.renderNode.setClipToBounds(false);
        this.renderNode = renderNodeApi29;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void destroy() {
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        if (renderNodeApi29.renderNode.hasDisplayList()) {
            renderNodeApi29.renderNode.discardDisplayList();
        }
        this.drawBlock = null;
        this.invalidateParentLayer = null;
        this.isDestroyed = true;
        setDirty$1(false);
        AndroidComposeView androidComposeView = this.ownerView;
        androidComposeView.observationClearRequested = true;
        androidComposeView.recycle$ui_release(this);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void drawLayer(Canvas canvas, GraphicsLayer graphicsLayer) {
        android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        android.graphics.Canvas canvas3 = ((AndroidCanvas) canvas).internalCanvas;
        boolean isHardwareAccelerated = canvas3.isHardwareAccelerated();
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        if (isHardwareAccelerated) {
            updateDisplayList();
            boolean z = renderNodeApi29.renderNode.getElevation() > 0.0f;
            this.drawnWithZ = z;
            if (z) {
                canvas.enableZ();
            }
            canvas3.drawRenderNode(renderNodeApi29.renderNode);
            if (this.drawnWithZ) {
                canvas.disableZ();
                return;
            }
            return;
        }
        float left = renderNodeApi29.renderNode.getLeft();
        float top = renderNodeApi29.renderNode.getTop();
        float right = renderNodeApi29.renderNode.getRight();
        float bottom = renderNodeApi29.renderNode.getBottom();
        if (renderNodeApi29.renderNode.getAlpha() < 1.0f) {
            AndroidPaint androidPaint = this.softwareLayerPaint;
            if (androidPaint == null) {
                androidPaint = new AndroidPaint();
                this.softwareLayerPaint = androidPaint;
            }
            androidPaint.setAlpha(renderNodeApi29.renderNode.getAlpha());
            canvas3.saveLayer(left, top, right, bottom, androidPaint.internalPaint);
        } else {
            canvas.save();
        }
        canvas.translate(left, top);
        canvas.mo425concat58bKbWc(this.matrixCache.m706calculateMatrixGrdbGEg(renderNodeApi29));
        if (renderNodeApi29.renderNode.getClipToOutline() || renderNodeApi29.renderNode.getClipToBounds()) {
            this.outlineResolver.clipToOutline(canvas);
        }
        Function2 function2 = this.drawBlock;
        if (function2 != null) {
            function2.invoke(canvas, null);
        }
        canvas.restore();
        setDirty$1(false);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: getUnderlyingMatrix-sQKQjiQ */
    public final float[] mo682getUnderlyingMatrixsQKQjiQ() {
        return this.matrixCache.m706calculateMatrixGrdbGEg(this.renderNode);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void invalidate() {
        if (this.isDirty || this.isDestroyed) {
            return;
        }
        this.ownerView.invalidate();
        setDirty$1(true);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: inverseTransform-58bKbWc */
    public final void mo683inverseTransform58bKbWc(float[] fArr) {
        float[] m705calculateInverseMatrixbWbORWo = this.matrixCache.m705calculateInverseMatrixbWbORWo(this.renderNode);
        if (m705calculateInverseMatrixbWbORWo != null) {
            androidx.compose.ui.graphics.Matrix.m487timesAssign58bKbWc(fArr, m705calculateInverseMatrixbWbORWo);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: isInLayer-k-4lQ0M */
    public final boolean mo684isInLayerk4lQ0M(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (4294967295L & j));
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        if (renderNodeApi29.renderNode.getClipToBounds()) {
            return 0.0f <= intBitsToFloat && intBitsToFloat < ((float) renderNodeApi29.renderNode.getWidth()) && 0.0f <= intBitsToFloat2 && intBitsToFloat2 < ((float) renderNodeApi29.renderNode.getHeight());
        }
        if (renderNodeApi29.renderNode.getClipToOutline()) {
            return this.outlineResolver.m707isInOutlinek4lQ0M(j);
        }
        return true;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void mapBounds(MutableRect mutableRect, boolean z) {
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        LayerMatrixCache layerMatrixCache = this.matrixCache;
        if (!z) {
            float[] m706calculateMatrixGrdbGEg = layerMatrixCache.m706calculateMatrixGrdbGEg(renderNodeApi29);
            if (layerMatrixCache.isIdentity) {
                return;
            }
            androidx.compose.ui.graphics.Matrix.m483mapimpl(m706calculateMatrixGrdbGEg, mutableRect);
            return;
        }
        float[] m705calculateInverseMatrixbWbORWo = layerMatrixCache.m705calculateInverseMatrixbWbORWo(renderNodeApi29);
        if (m705calculateInverseMatrixbWbORWo != null) {
            if (layerMatrixCache.isIdentity) {
                return;
            }
            androidx.compose.ui.graphics.Matrix.m483mapimpl(m705calculateInverseMatrixbWbORWo, mutableRect);
        } else {
            mutableRect.left = 0.0f;
            mutableRect.top = 0.0f;
            mutableRect.right = 0.0f;
            mutableRect.bottom = 0.0f;
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: mapOffset-8S9VItk */
    public final long mo685mapOffset8S9VItk(long j, boolean z) {
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        LayerMatrixCache layerMatrixCache = this.matrixCache;
        if (z) {
            float[] m705calculateInverseMatrixbWbORWo = layerMatrixCache.m705calculateInverseMatrixbWbORWo(renderNodeApi29);
            if (m705calculateInverseMatrixbWbORWo == null) {
                Offset.Companion.getClass();
                return Offset.Infinite;
            }
            if (!layerMatrixCache.isIdentity) {
                return androidx.compose.ui.graphics.Matrix.m482mapMKHz9U(j, m705calculateInverseMatrixbWbORWo);
            }
        } else {
            float[] m706calculateMatrixGrdbGEg = layerMatrixCache.m706calculateMatrixGrdbGEg(renderNodeApi29);
            if (!layerMatrixCache.isIdentity) {
                return androidx.compose.ui.graphics.Matrix.m482mapMKHz9U(j, m706calculateMatrixGrdbGEg);
            }
        }
        return j;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: move--gyyYBs */
    public final void mo686movegyyYBs(long j) {
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        int left = renderNodeApi29.renderNode.getLeft();
        int top = renderNodeApi29.renderNode.getTop();
        IntOffset.Companion companion = IntOffset.Companion;
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        if (left == i && top == i2) {
            return;
        }
        if (left != i) {
            renderNodeApi29.renderNode.offsetLeftAndRight(i - left);
        }
        if (top != i2) {
            renderNodeApi29.renderNode.offsetTopAndBottom(i2 - top);
        }
        WrapperRenderNodeLayerHelperMethods.INSTANCE.getClass();
        AndroidComposeView androidComposeView = this.ownerView;
        ViewParent parent = androidComposeView.getParent();
        if (parent != null) {
            parent.onDescendantInvalidated(androidComposeView, androidComposeView);
        }
        this.matrixCache.invalidate();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: resize-ozmzZPI */
    public final void mo687resizeozmzZPI(long j) {
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        float m503getPivotFractionXimpl = TransformOrigin.m503getPivotFractionXimpl(this.transformOrigin) * i;
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        renderNodeApi29.renderNode.setPivotX(m503getPivotFractionXimpl);
        renderNodeApi29.renderNode.setPivotY(TransformOrigin.m504getPivotFractionYimpl(this.transformOrigin) * i2);
        if (renderNodeApi29.renderNode.setPosition(renderNodeApi29.renderNode.getLeft(), renderNodeApi29.renderNode.getTop(), renderNodeApi29.renderNode.getLeft() + i, renderNodeApi29.renderNode.getTop() + i2)) {
            renderNodeApi29.renderNode.setOutline(this.outlineResolver.getAndroidOutline());
            if (!this.isDirty && !this.isDestroyed) {
                this.ownerView.invalidate();
                setDirty$1(true);
            }
            this.matrixCache.invalidate();
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void reuseLayer(Function2 function2, Function0 function0) {
        LayerMatrixCache layerMatrixCache = this.matrixCache;
        layerMatrixCache.isDirty = false;
        layerMatrixCache.isInverseDirty = false;
        layerMatrixCache.isIdentity = true;
        layerMatrixCache.isInverseValid = true;
        androidx.compose.ui.graphics.Matrix.m484resetimpl(layerMatrixCache.matrixCache);
        androidx.compose.ui.graphics.Matrix.m484resetimpl(layerMatrixCache.inverseMatrixCache);
        setDirty$1(false);
        this.isDestroyed = false;
        this.drawnWithZ = false;
        TransformOrigin.Companion.getClass();
        this.transformOrigin = TransformOrigin.Center;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
    }

    public final void setDirty$1(boolean z) {
        if (z != this.isDirty) {
            this.isDirty = z;
            this.ownerView.notifyLayerIsDirty$ui_release(this, z);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: transform-58bKbWc */
    public final void mo688transform58bKbWc(float[] fArr) {
        androidx.compose.ui.graphics.Matrix.m487timesAssign58bKbWc(fArr, this.matrixCache.m706calculateMatrixGrdbGEg(this.renderNode));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0029  */
    @Override // androidx.compose.ui.node.OwnedLayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDisplayList() {
        /*
            r7 = this;
            boolean r0 = r7.isDirty
            androidx.compose.ui.platform.RenderNodeApi29 r1 = r7.renderNode
            if (r0 != 0) goto L10
            android.graphics.RenderNode r0 = r1.renderNode
            boolean r0 = r0.hasDisplayList()
            if (r0 != 0) goto Lf
            goto L10
        Lf:
            return
        L10:
            android.graphics.RenderNode r0 = r1.renderNode
            boolean r0 = r0.getClipToOutline()
            if (r0 == 0) goto L24
            androidx.compose.ui.platform.OutlineResolver r0 = r7.outlineResolver
            boolean r2 = r0.usePathForClip
            if (r2 == 0) goto L24
            r0.updateCache()
            androidx.compose.ui.graphics.Path r0 = r0.outlinePath
            goto L25
        L24:
            r0 = 0
        L25:
            kotlin.jvm.functions.Function2 r2 = r7.drawBlock
            if (r2 == 0) goto L55
            androidx.compose.ui.platform.RenderNodeLayer$updateDisplayList$1$1 r3 = new androidx.compose.ui.platform.RenderNodeLayer$updateDisplayList$1$1
            r3.<init>()
            android.graphics.RenderNode r2 = r1.renderNode
            android.graphics.RecordingCanvas r2 = r2.beginRecording()
            androidx.compose.ui.graphics.CanvasHolder r4 = r7.canvasHolder
            androidx.compose.ui.graphics.AndroidCanvas r5 = r4.androidCanvas
            android.graphics.Canvas r6 = r5.internalCanvas
            r5.internalCanvas = r2
            if (r0 == 0) goto L44
            r5.save()
            androidx.compose.ui.graphics.Canvas.m452clipPathmtrdDE$default(r5, r0)
        L44:
            r3.mo779invoke(r5)
            if (r0 == 0) goto L4c
            r5.restore()
        L4c:
            androidx.compose.ui.graphics.AndroidCanvas r0 = r4.androidCanvas
            r0.internalCanvas = r6
            android.graphics.RenderNode r0 = r1.renderNode
            r0.endRecording()
        L55:
            r0 = 0
            r7.setDirty$1(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.RenderNodeLayer.updateDisplayList():void");
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope) {
        Function0 function0;
        int i = reusableGraphicsLayerScope.mutatedFields | this.mutatedFields;
        int i2 = i & 4096;
        if (i2 != 0) {
            this.transformOrigin = reusableGraphicsLayerScope.transformOrigin;
        }
        RenderNodeApi29 renderNodeApi29 = this.renderNode;
        boolean clipToOutline = renderNodeApi29.renderNode.getClipToOutline();
        OutlineResolver outlineResolver = this.outlineResolver;
        boolean z = false;
        boolean z2 = clipToOutline && outlineResolver.usePathForClip;
        if ((i & 1) != 0) {
            renderNodeApi29.renderNode.setScaleX(reusableGraphicsLayerScope.scaleX);
        }
        if ((i & 2) != 0) {
            renderNodeApi29.renderNode.setScaleY(reusableGraphicsLayerScope.scaleY);
        }
        if ((i & 4) != 0) {
            renderNodeApi29.renderNode.setAlpha(reusableGraphicsLayerScope.alpha);
        }
        if ((i & 8) != 0) {
            renderNodeApi29.renderNode.setTranslationX(reusableGraphicsLayerScope.translationX);
        }
        if ((i & 16) != 0) {
            renderNodeApi29.renderNode.setTranslationY(reusableGraphicsLayerScope.translationY);
        }
        if ((i & 32) != 0) {
            renderNodeApi29.renderNode.setElevation(reusableGraphicsLayerScope.shadowElevation);
        }
        if ((i & 64) != 0) {
            renderNodeApi29.renderNode.setAmbientShadowColor(ColorKt.m467toArgb8_81llA(reusableGraphicsLayerScope.ambientShadowColor));
        }
        if ((i & 128) != 0) {
            renderNodeApi29.renderNode.setSpotShadowColor(ColorKt.m467toArgb8_81llA(reusableGraphicsLayerScope.spotShadowColor));
        }
        if ((i & 1024) != 0) {
            renderNodeApi29.renderNode.setRotationZ(reusableGraphicsLayerScope.rotationZ);
        }
        if ((i & 256) != 0) {
            renderNodeApi29.renderNode.setRotationX(reusableGraphicsLayerScope.rotationX);
        }
        if ((i & 512) != 0) {
            renderNodeApi29.renderNode.setRotationY(reusableGraphicsLayerScope.rotationY);
        }
        if ((i & 2048) != 0) {
            renderNodeApi29.renderNode.setCameraDistance(reusableGraphicsLayerScope.cameraDistance);
        }
        if (i2 != 0) {
            renderNodeApi29.renderNode.setPivotX(TransformOrigin.m503getPivotFractionXimpl(this.transformOrigin) * renderNodeApi29.renderNode.getWidth());
            renderNodeApi29.renderNode.setPivotY(TransformOrigin.m504getPivotFractionYimpl(this.transformOrigin) * renderNodeApi29.renderNode.getHeight());
        }
        boolean z3 = reusableGraphicsLayerScope.clip && reusableGraphicsLayerScope.shape != RectangleShapeKt.RectangleShape;
        if ((i & 24576) != 0) {
            renderNodeApi29.renderNode.setClipToOutline(z3);
            renderNodeApi29.renderNode.setClipToBounds(reusableGraphicsLayerScope.clip && reusableGraphicsLayerScope.shape == RectangleShapeKt.RectangleShape);
        }
        if ((131072 & i) != 0) {
            RenderEffect renderEffect = reusableGraphicsLayerScope.renderEffect;
            renderNodeApi29.getClass();
            RenderNodeApi29VerificationHelper renderNodeApi29VerificationHelper = RenderNodeApi29VerificationHelper.INSTANCE;
            RenderNode renderNode = renderNodeApi29.renderNode;
            renderNodeApi29VerificationHelper.getClass();
            renderNode.setRenderEffect(renderEffect != null ? renderEffect.asAndroidRenderEffect() : null);
        }
        if ((262144 & i) != 0) {
            ColorFilter colorFilter = reusableGraphicsLayerScope.colorFilter;
            renderNodeApi29.colorFilter = colorFilter;
            AndroidPaint androidPaint = renderNodeApi29.layerPaint;
            if (androidPaint == null) {
                androidPaint = new AndroidPaint();
                renderNodeApi29.layerPaint = androidPaint;
            }
            androidPaint.setColorFilter(colorFilter);
            renderNodeApi29.updateLayerProperties$1();
        }
        if ((524288 & i) != 0) {
            int i3 = reusableGraphicsLayerScope.blendMode;
            renderNodeApi29.blendMode = i3;
            AndroidPaint androidPaint2 = renderNodeApi29.layerPaint;
            if (androidPaint2 == null) {
                androidPaint2 = new AndroidPaint();
                renderNodeApi29.layerPaint = androidPaint2;
            }
            androidPaint2.m437setBlendModes9anfk8(i3);
            renderNodeApi29.updateLayerProperties$1();
        }
        if ((32768 & i) != 0) {
            renderNodeApi29.internalCompositingStrategy = reusableGraphicsLayerScope.compositingStrategy;
            renderNodeApi29.updateLayerProperties$1();
        }
        boolean m708updateS_szKao = this.outlineResolver.m708updateS_szKao(reusableGraphicsLayerScope.outline, reusableGraphicsLayerScope.alpha, z3, reusableGraphicsLayerScope.shadowElevation, reusableGraphicsLayerScope.size);
        if (outlineResolver.cacheIsDirty) {
            renderNodeApi29.renderNode.setOutline(outlineResolver.getAndroidOutline());
        }
        if (z3 && outlineResolver.usePathForClip) {
            z = true;
        }
        AndroidComposeView androidComposeView = this.ownerView;
        if (z2 == z && (!z || !m708updateS_szKao)) {
            WrapperRenderNodeLayerHelperMethods.INSTANCE.getClass();
            ViewParent parent = androidComposeView.getParent();
            if (parent != null) {
                parent.onDescendantInvalidated(androidComposeView, androidComposeView);
            }
        } else if (!this.isDirty && !this.isDestroyed) {
            androidComposeView.invalidate();
            setDirty$1(true);
        }
        if (!this.drawnWithZ && renderNodeApi29.renderNode.getElevation() > 0.0f && (function0 = this.invalidateParentLayer) != null) {
            function0.invoke();
        }
        if ((i & 7963) != 0) {
            this.matrixCache.invalidate();
        }
        this.mutatedFields = reusableGraphicsLayerScope.mutatedFields;
    }
}
