package androidx.compose.ui.platform;

import android.view.ViewParent;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.MatrixKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.RenderEffect;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerImpl;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class GraphicsLayerOwnerLayer implements OwnedLayer {
    public final GraphicsContext context;
    public Density density;
    public Function2 drawBlock;
    public boolean drawnWithEnabledZ;
    public GraphicsLayer graphicsLayer;
    public Function0 invalidateParentLayer;
    public float[] inverseMatrixCache;
    public boolean isDestroyed;
    public boolean isDirty;
    public boolean isIdentity;
    public boolean isInverseMatrixDirty;
    public boolean isMatrixDirty;
    public LayoutDirection layoutDirection;
    public final float[] matrixCache;
    public int mutatedFields;
    public Outline outline;
    public final AndroidComposeView ownerView;
    public final Function1 recordLambda;
    public final CanvasDrawScope scope;
    public long size;
    public long transformOrigin;

    public GraphicsLayerOwnerLayer(GraphicsLayer graphicsLayer, GraphicsContext graphicsContext, AndroidComposeView androidComposeView, Function2 function2, Function0 function0) {
        this.graphicsLayer = graphicsLayer;
        this.context = graphicsContext;
        this.ownerView = androidComposeView;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        long j = Integer.MAX_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        this.size = (j & 4294967295L) | (j << 32);
        this.matrixCache = Matrix.m483constructorimpl$default();
        this.density = DensityKt.Density$default(1.0f);
        this.layoutDirection = LayoutDirection.Ltr;
        this.scope = new CanvasDrawScope();
        TransformOrigin.Companion.getClass();
        this.transformOrigin = TransformOrigin.Center;
        this.isIdentity = true;
        this.recordLambda = new Function1() { // from class: androidx.compose.ui.platform.GraphicsLayerOwnerLayer$recordLambda$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DrawScope drawScope = (DrawScope) obj;
                GraphicsLayerOwnerLayer graphicsLayerOwnerLayer = this.this$0;
                Canvas canvas = drawScope.getDrawContext().getCanvas();
                Function2 function22 = graphicsLayerOwnerLayer.drawBlock;
                if (function22 != null) {
                    function22.invoke(canvas, drawScope.getDrawContext().graphicsLayer);
                }
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void destroy() {
        this.drawBlock = null;
        this.invalidateParentLayer = null;
        this.isDestroyed = true;
        boolean z = this.isDirty;
        AndroidComposeView androidComposeView = this.ownerView;
        if (z) {
            this.isDirty = false;
            androidComposeView.notifyLayerIsDirty$ui_release(this, false);
        }
        GraphicsContext graphicsContext = this.context;
        if (graphicsContext != null) {
            graphicsContext.releaseGraphicsLayer(this.graphicsLayer);
            androidComposeView.recycle$ui_release(this);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void drawLayer(Canvas canvas, GraphicsLayer graphicsLayer) {
        updateDisplayList();
        this.drawnWithEnabledZ = this.graphicsLayer.impl.getShadowElevation() > 0.0f;
        CanvasDrawScope canvasDrawScope = this.scope;
        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
        canvasDrawScope$drawContext$1.setCanvas(canvas);
        canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer;
        GraphicsLayerKt.drawLayer(canvasDrawScope, this.graphicsLayer);
    }

    /* renamed from: getInverseMatrix-3i98HWw, reason: not valid java name */
    public final float[] m704getInverseMatrix3i98HWw() {
        float[] fArrM483constructorimpl$default = this.inverseMatrixCache;
        if (fArrM483constructorimpl$default == null) {
            fArrM483constructorimpl$default = Matrix.m483constructorimpl$default();
            this.inverseMatrixCache = fArrM483constructorimpl$default;
        }
        if (this.isInverseMatrixDirty) {
            this.isInverseMatrixDirty = false;
            float[] fArrM705getMatrixsQKQjiQ = m705getMatrixsQKQjiQ();
            if (this.isIdentity) {
                return fArrM705getMatrixsQKQjiQ;
            }
            if (!InvertMatrixKt.m706invertToJiSxe2E(fArrM705getMatrixsQKQjiQ, fArrM483constructorimpl$default)) {
                fArrM483constructorimpl$default[0] = Float.NaN;
                return null;
            }
        } else if (Float.isNaN(fArrM483constructorimpl$default[0])) {
            return null;
        }
        return fArrM483constructorimpl$default;
    }

    /* renamed from: getMatrix-sQKQjiQ, reason: not valid java name */
    public final float[] m705getMatrixsQKQjiQ() {
        boolean z = this.isMatrixDirty;
        float[] fArr = this.matrixCache;
        if (z) {
            GraphicsLayer graphicsLayer = this.graphicsLayer;
            long jM422getCenteruvyYCjk = graphicsLayer.pivotOffset;
            if ((9223372034707292159L & jM422getCenteruvyYCjk) == 9205357640488583168L) {
                jM422getCenteruvyYCjk = SizeKt.m422getCenteruvyYCjk(IntSizeKt.m866toSizeozmzZPI(this.size));
            }
            float fIntBitsToFloat = Float.intBitsToFloat((int) (jM422getCenteruvyYCjk >> 32));
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM422getCenteruvyYCjk & 4294967295L));
            GraphicsLayerImpl graphicsLayerImpl = graphicsLayer.impl;
            float translationX = graphicsLayerImpl.getTranslationX();
            float translationY = graphicsLayerImpl.getTranslationY();
            float rotationX = graphicsLayerImpl.getRotationX();
            float rotationY = graphicsLayerImpl.getRotationY();
            float rotationZ = graphicsLayerImpl.getRotationZ();
            float scaleX = graphicsLayerImpl.getScaleX();
            float scaleY = graphicsLayerImpl.getScaleY();
            int i = Matrix.$r8$clinit;
            double d = rotationX * 0.017453292519943295d;
            float fSin = (float) Math.sin(d);
            float fCos = (float) Math.cos(d);
            float f = -fSin;
            float f2 = (translationY * fCos) - (1.0f * fSin);
            float f3 = (1.0f * fCos) + (translationY * fSin);
            double d2 = rotationY * 0.017453292519943295d;
            float fSin2 = (float) Math.sin(d2);
            float fCos2 = (float) Math.cos(d2);
            float f4 = -fSin2;
            float f5 = fSin * fSin2;
            float f6 = fSin * fCos2;
            float f7 = fCos * fSin2;
            float f8 = fCos * fCos2;
            float f9 = (f3 * fSin2) + (translationX * fCos2);
            float f10 = (f3 * fCos2) + ((-translationX) * fSin2);
            double d3 = rotationZ * 0.017453292519943295d;
            float fSin3 = (float) Math.sin(d3);
            float fCos3 = (float) Math.cos(d3);
            float f11 = -fSin3;
            float f12 = (fCos3 * f5) + (f11 * fCos2);
            float f13 = ((f5 * fSin3) + (fCos2 * fCos3)) * scaleX;
            float f14 = fSin3 * fCos * scaleX;
            float f15 = ((fSin3 * f6) + (fCos3 * f4)) * scaleX;
            float f16 = f12 * scaleY;
            float f17 = fCos * fCos3 * scaleY;
            float f18 = ((fCos3 * f6) + (f11 * f4)) * scaleY;
            float f19 = f7 * 1.0f;
            float f20 = f * 1.0f;
            float f21 = f8 * 1.0f;
            if (fArr.length >= 16) {
                fArr[0] = f13;
                fArr[1] = f14;
                fArr[2] = f15;
                fArr[3] = 0.0f;
                fArr[4] = f16;
                fArr[5] = f17;
                fArr[6] = f18;
                fArr[7] = 0.0f;
                fArr[8] = f19;
                fArr[9] = f20;
                fArr[10] = f21;
                fArr[11] = 0.0f;
                float f22 = -fIntBitsToFloat;
                fArr[12] = ((f13 * f22) - (fIntBitsToFloat2 * f16)) + f9 + fIntBitsToFloat;
                fArr[13] = ((f14 * f22) - (fIntBitsToFloat2 * f17)) + f2 + fIntBitsToFloat2;
                fArr[14] = ((f22 * f15) - (fIntBitsToFloat2 * f18)) + f10;
                fArr[15] = 1.0f;
            }
            this.isMatrixDirty = false;
            this.isIdentity = MatrixKt.m491isIdentity58bKbWc(fArr);
        }
        return fArr;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: getUnderlyingMatrix-sQKQjiQ */
    public final float[] mo684getUnderlyingMatrixsQKQjiQ() {
        return m705getMatrixsQKQjiQ();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void invalidate() {
        if (this.isDirty || this.isDestroyed) {
            return;
        }
        AndroidComposeView androidComposeView = this.ownerView;
        androidComposeView.invalidate();
        if (true != this.isDirty) {
            this.isDirty = true;
            androidComposeView.notifyLayerIsDirty$ui_release(this, true);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: inverseTransform-58bKbWc */
    public final void mo685inverseTransform58bKbWc(float[] fArr) {
        float[] fArrM704getInverseMatrix3i98HWw = m704getInverseMatrix3i98HWw();
        if (fArrM704getInverseMatrix3i98HWw != null) {
            Matrix.m489timesAssign58bKbWc(fArr, fArrM704getInverseMatrix3i98HWw);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: isInLayer-k-4lQ0M */
    public final boolean mo686isInLayerk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        GraphicsLayer graphicsLayer = this.graphicsLayer;
        if (graphicsLayer.clip) {
            return ShapeContainingUtilKt.isInOutline(graphicsLayer.getOutline(), fIntBitsToFloat, fIntBitsToFloat2);
        }
        return true;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void mapBounds(MutableRect mutableRect, boolean z) {
        float[] fArrM704getInverseMatrix3i98HWw = z ? m704getInverseMatrix3i98HWw() : m705getMatrixsQKQjiQ();
        if (this.isIdentity) {
            return;
        }
        if (fArrM704getInverseMatrix3i98HWw != null) {
            Matrix.m485mapimpl(fArrM704getInverseMatrix3i98HWw, mutableRect);
            return;
        }
        mutableRect.left = 0.0f;
        mutableRect.top = 0.0f;
        mutableRect.right = 0.0f;
        mutableRect.bottom = 0.0f;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: mapOffset-8S9VItk */
    public final long mo687mapOffset8S9VItk(long j, boolean z) {
        float[] fArrM705getMatrixsQKQjiQ;
        if (z) {
            fArrM705getMatrixsQKQjiQ = m704getInverseMatrix3i98HWw();
            if (fArrM705getMatrixsQKQjiQ == null) {
                Offset.Companion.getClass();
                return Offset.Infinite;
            }
        } else {
            fArrM705getMatrixsQKQjiQ = m705getMatrixsQKQjiQ();
        }
        return this.isIdentity ? j : Matrix.m484mapMKHz9U(j, fArrM705getMatrixsQKQjiQ);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: move--gyyYBs */
    public final void mo688movegyyYBs(long j) {
        GraphicsLayer graphicsLayer = this.graphicsLayer;
        if (!IntOffset.m851equalsimpl0(graphicsLayer.topLeft, j)) {
            graphicsLayer.topLeft = j;
            long j2 = graphicsLayer.size;
            graphicsLayer.impl.mo560setPositionH0pRuoY((int) (j >> 32), (int) (j & 4294967295L), j2);
        }
        WrapperRenderNodeLayerHelperMethods.INSTANCE.getClass();
        AndroidComposeView androidComposeView = this.ownerView;
        ViewParent parent = androidComposeView.getParent();
        if (parent != null) {
            parent.onDescendantInvalidated(androidComposeView, androidComposeView);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: resize-ozmzZPI */
    public final void mo689resizeozmzZPI(long j) {
        if (IntSize.m863equalsimpl0(j, this.size)) {
            return;
        }
        this.size = j;
        if (this.isDirty || this.isDestroyed) {
            return;
        }
        AndroidComposeView androidComposeView = this.ownerView;
        androidComposeView.invalidate();
        if (true != this.isDirty) {
            this.isDirty = true;
            androidComposeView.notifyLayerIsDirty$ui_release(this, true);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void reuseLayer(Function2 function2, Function0 function0) {
        GraphicsContext graphicsContext = this.context;
        if (graphicsContext == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("currently reuse is only supported when we manage the layer lifecycle");
        }
        if (!this.graphicsLayer.isReleased) {
            InlineClassHelperKt.throwIllegalArgumentException("layer should have been released before reuse");
        }
        this.graphicsLayer = graphicsContext.createGraphicsLayer();
        this.isDestroyed = false;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        this.isMatrixDirty = false;
        this.isInverseMatrixDirty = false;
        this.isIdentity = true;
        Matrix.m486resetimpl(this.matrixCache);
        float[] fArr = this.inverseMatrixCache;
        if (fArr != null) {
            Matrix.m486resetimpl(fArr);
        }
        TransformOrigin.Companion.getClass();
        this.transformOrigin = TransformOrigin.Center;
        this.drawnWithEnabledZ = false;
        long j = Integer.MAX_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        this.size = (j & 4294967295L) | (j << 32);
        this.outline = null;
        this.mutatedFields = 0;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: transform-58bKbWc */
    public final void mo690transform58bKbWc(float[] fArr) {
        Matrix.m489timesAssign58bKbWc(fArr, m705getMatrixsQKQjiQ());
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void updateDisplayList() {
        if (this.isDirty) {
            long j = this.transformOrigin;
            TransformOrigin.Companion.getClass();
            if (!TransformOrigin.m504equalsimpl0(j, TransformOrigin.Center) && !IntSize.m863equalsimpl0(this.graphicsLayer.size, this.size)) {
                GraphicsLayer graphicsLayer = this.graphicsLayer;
                float fM505getPivotFractionXimpl = TransformOrigin.m505getPivotFractionXimpl(this.transformOrigin) * ((int) (this.size >> 32));
                float fM506getPivotFractionYimpl = TransformOrigin.m506getPivotFractionYimpl(this.transformOrigin) * ((int) (this.size & 4294967295L));
                long jFloatToRawIntBits = (Float.floatToRawIntBits(fM506getPivotFractionYimpl) & 4294967295L) | (Float.floatToRawIntBits(fM505getPivotFractionXimpl) << 32);
                Offset.Companion companion = Offset.Companion;
                if (!Offset.m398equalsimpl0(graphicsLayer.pivotOffset, jFloatToRawIntBits)) {
                    graphicsLayer.pivotOffset = jFloatToRawIntBits;
                    graphicsLayer.impl.mo559setPivotOffsetk4lQ0M(jFloatToRawIntBits);
                }
            }
            this.graphicsLayer.m549recordmLhObY(this.density, this.layoutDirection, this.size, this.recordLambda);
            if (this.isDirty) {
                this.isDirty = false;
                this.ownerView.notifyLayerIsDirty$ui_release(this, false);
            }
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope) {
        int i;
        Function0 function0;
        boolean z = true;
        int i2 = reusableGraphicsLayerScope.mutatedFields | this.mutatedFields;
        this.layoutDirection = reusableGraphicsLayerScope.layoutDirection;
        this.density = reusableGraphicsLayerScope.graphicsDensity;
        int i3 = i2 & 4096;
        if (i3 != 0) {
            this.transformOrigin = reusableGraphicsLayerScope.transformOrigin;
        }
        if ((i2 & 1) != 0) {
            GraphicsLayer graphicsLayer = this.graphicsLayer;
            float f = reusableGraphicsLayerScope.scaleX;
            GraphicsLayerImpl graphicsLayerImpl = graphicsLayer.impl;
            if (graphicsLayerImpl.getScaleX() != f) {
                graphicsLayerImpl.setScaleX(f);
            }
        }
        if ((i2 & 2) != 0) {
            GraphicsLayer graphicsLayer2 = this.graphicsLayer;
            float f2 = reusableGraphicsLayerScope.scaleY;
            GraphicsLayerImpl graphicsLayerImpl2 = graphicsLayer2.impl;
            if (graphicsLayerImpl2.getScaleY() != f2) {
                graphicsLayerImpl2.setScaleY(f2);
            }
        }
        if ((i2 & 4) != 0) {
            this.graphicsLayer.setAlpha(reusableGraphicsLayerScope.alpha);
        }
        if ((i2 & 8) != 0) {
            GraphicsLayer graphicsLayer3 = this.graphicsLayer;
            float f3 = reusableGraphicsLayerScope.translationX;
            GraphicsLayerImpl graphicsLayerImpl3 = graphicsLayer3.impl;
            if (graphicsLayerImpl3.getTranslationX() != f3) {
                graphicsLayerImpl3.setTranslationX(f3);
            }
        }
        if ((i2 & 16) != 0) {
            GraphicsLayer graphicsLayer4 = this.graphicsLayer;
            float f4 = reusableGraphicsLayerScope.translationY;
            GraphicsLayerImpl graphicsLayerImpl4 = graphicsLayer4.impl;
            if (graphicsLayerImpl4.getTranslationY() != f4) {
                graphicsLayerImpl4.setTranslationY(f4);
            }
        }
        if ((i2 & 32) != 0) {
            GraphicsLayer graphicsLayer5 = this.graphicsLayer;
            float f5 = reusableGraphicsLayerScope.shadowElevation;
            GraphicsLayerImpl graphicsLayerImpl5 = graphicsLayer5.impl;
            if (graphicsLayerImpl5.getShadowElevation() != f5) {
                graphicsLayerImpl5.setShadowElevation(f5);
                graphicsLayer5.outlineDirty = true;
                graphicsLayer5.configureOutlineAndClip();
            }
            if (reusableGraphicsLayerScope.shadowElevation > 0.0f && !this.drawnWithEnabledZ && (function0 = this.invalidateParentLayer) != null) {
                function0.invoke();
            }
        }
        if ((i2 & 64) != 0) {
            GraphicsLayer graphicsLayer6 = this.graphicsLayer;
            long j = reusableGraphicsLayerScope.ambientShadowColor;
            GraphicsLayerImpl graphicsLayerImpl6 = graphicsLayer6.impl;
            long jMo551getAmbientShadowColor0d7_KjU = graphicsLayerImpl6.mo551getAmbientShadowColor0d7_KjU();
            Color.Companion companion = Color.Companion;
            if (!ULong.m3446equalsimpl0(j, jMo551getAmbientShadowColor0d7_KjU)) {
                graphicsLayerImpl6.mo555setAmbientShadowColor8_81llA(j);
            }
        }
        if ((i2 & 128) != 0) {
            GraphicsLayer graphicsLayer7 = this.graphicsLayer;
            long j2 = reusableGraphicsLayerScope.spotShadowColor;
            GraphicsLayerImpl graphicsLayerImpl7 = graphicsLayer7.impl;
            long jMo554getSpotShadowColor0d7_KjU = graphicsLayerImpl7.mo554getSpotShadowColor0d7_KjU();
            Color.Companion companion2 = Color.Companion;
            if (!ULong.m3446equalsimpl0(j2, jMo554getSpotShadowColor0d7_KjU)) {
                graphicsLayerImpl7.mo561setSpotShadowColor8_81llA(j2);
            }
        }
        if ((i2 & 1024) != 0) {
            GraphicsLayer graphicsLayer8 = this.graphicsLayer;
            float f6 = reusableGraphicsLayerScope.rotationZ;
            GraphicsLayerImpl graphicsLayerImpl8 = graphicsLayer8.impl;
            if (graphicsLayerImpl8.getRotationZ() != f6) {
                graphicsLayerImpl8.setRotationZ(f6);
            }
        }
        if ((i2 & 256) != 0) {
            GraphicsLayer graphicsLayer9 = this.graphicsLayer;
            float f7 = reusableGraphicsLayerScope.rotationX;
            GraphicsLayerImpl graphicsLayerImpl9 = graphicsLayer9.impl;
            if (graphicsLayerImpl9.getRotationX() != f7) {
                graphicsLayerImpl9.setRotationX(f7);
            }
        }
        if ((i2 & 512) != 0) {
            GraphicsLayer graphicsLayer10 = this.graphicsLayer;
            float f8 = reusableGraphicsLayerScope.rotationY;
            GraphicsLayerImpl graphicsLayerImpl10 = graphicsLayer10.impl;
            if (graphicsLayerImpl10.getRotationY() != f8) {
                graphicsLayerImpl10.setRotationY(f8);
            }
        }
        if ((i2 & 2048) != 0) {
            GraphicsLayer graphicsLayer11 = this.graphicsLayer;
            float f9 = reusableGraphicsLayerScope.cameraDistance;
            GraphicsLayerImpl graphicsLayerImpl11 = graphicsLayer11.impl;
            if (graphicsLayerImpl11.getCameraDistance() != f9) {
                graphicsLayerImpl11.setCameraDistance(f9);
            }
        }
        if (i3 != 0) {
            long j3 = this.transformOrigin;
            TransformOrigin.Companion.getClass();
            if (TransformOrigin.m504equalsimpl0(j3, TransformOrigin.Center)) {
                GraphicsLayer graphicsLayer12 = this.graphicsLayer;
                Offset.Companion.getClass();
                long j4 = Offset.Unspecified;
                if (!Offset.m398equalsimpl0(graphicsLayer12.pivotOffset, j4)) {
                    graphicsLayer12.pivotOffset = j4;
                    graphicsLayer12.impl.mo559setPivotOffsetk4lQ0M(j4);
                }
            } else {
                GraphicsLayer graphicsLayer13 = this.graphicsLayer;
                float fM505getPivotFractionXimpl = TransformOrigin.m505getPivotFractionXimpl(this.transformOrigin) * ((int) (this.size >> 32));
                long jFloatToRawIntBits = (Float.floatToRawIntBits(TransformOrigin.m506getPivotFractionYimpl(this.transformOrigin) * ((int) (this.size & 4294967295L))) & 4294967295L) | (Float.floatToRawIntBits(fM505getPivotFractionXimpl) << 32);
                Offset.Companion companion3 = Offset.Companion;
                if (!Offset.m398equalsimpl0(graphicsLayer13.pivotOffset, jFloatToRawIntBits)) {
                    graphicsLayer13.pivotOffset = jFloatToRawIntBits;
                    graphicsLayer13.impl.mo559setPivotOffsetk4lQ0M(jFloatToRawIntBits);
                }
            }
        }
        if ((i2 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
            GraphicsLayer graphicsLayer14 = this.graphicsLayer;
            boolean z2 = reusableGraphicsLayerScope.clip;
            if (graphicsLayer14.clip != z2) {
                graphicsLayer14.clip = z2;
                graphicsLayer14.outlineDirty = true;
                graphicsLayer14.configureOutlineAndClip();
            }
        }
        if ((131072 & i2) != 0) {
            GraphicsLayer graphicsLayer15 = this.graphicsLayer;
            RenderEffect renderEffect = reusableGraphicsLayerScope.renderEffect;
            GraphicsLayerImpl graphicsLayerImpl12 = graphicsLayer15.impl;
            if (!Intrinsics.areEqual(graphicsLayerImpl12.getRenderEffect(), renderEffect)) {
                graphicsLayerImpl12.setRenderEffect(renderEffect);
            }
        }
        if ((262144 & i2) != 0) {
            GraphicsLayer graphicsLayer16 = this.graphicsLayer;
            ColorFilter colorFilter = reusableGraphicsLayerScope.colorFilter;
            GraphicsLayerImpl graphicsLayerImpl13 = graphicsLayer16.impl;
            if (!Intrinsics.areEqual(graphicsLayerImpl13.getColorFilter(), colorFilter)) {
                graphicsLayerImpl13.setColorFilter(colorFilter);
            }
        }
        if ((524288 & i2) != 0) {
            GraphicsLayer graphicsLayer17 = this.graphicsLayer;
            int i4 = reusableGraphicsLayerScope.blendMode;
            GraphicsLayerImpl graphicsLayerImpl14 = graphicsLayer17.impl;
            int iMo552getBlendMode0nO6VwU = graphicsLayerImpl14.mo552getBlendMode0nO6VwU();
            BlendMode.Companion companion4 = BlendMode.Companion;
            if (iMo552getBlendMode0nO6VwU != i4) {
                graphicsLayerImpl14.mo556setBlendModes9anfk8(i4);
            }
        }
        if ((32768 & i2) != 0) {
            GraphicsLayer graphicsLayer18 = this.graphicsLayer;
            int i5 = reusableGraphicsLayerScope.compositingStrategy;
            CompositingStrategy.Companion.getClass();
            if (i5 == 0) {
                androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.getClass();
                i = 0;
            } else if (i5 == CompositingStrategy.Offscreen) {
                androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.getClass();
                i = androidx.compose.ui.graphics.layer.CompositingStrategy.Offscreen;
            } else {
                if (i5 != CompositingStrategy.ModulateAlpha) {
                    throw new IllegalStateException("Not supported composition strategy");
                }
                androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.getClass();
                i = androidx.compose.ui.graphics.layer.CompositingStrategy.ModulateAlpha;
            }
            GraphicsLayerImpl graphicsLayerImpl15 = graphicsLayer18.impl;
            if (graphicsLayerImpl15.mo553getCompositingStrategyke2Ky5w() != i) {
                graphicsLayerImpl15.mo557setCompositingStrategyWpw9cng(i);
            }
        }
        if ((i2 & 7963) != 0) {
            this.isMatrixDirty = true;
            this.isInverseMatrixDirty = true;
        }
        if (Intrinsics.areEqual(this.outline, reusableGraphicsLayerScope.outline)) {
            z = false;
        } else {
            Outline outline = reusableGraphicsLayerScope.outline;
            this.outline = outline;
            if (outline != null) {
                GraphicsLayer graphicsLayer19 = this.graphicsLayer;
                if (outline instanceof Outline.Rectangle) {
                    Rect rect = ((Outline.Rectangle) outline).rect;
                    long jFloatToRawIntBits2 = Float.floatToRawIntBits(rect.left);
                    float f10 = rect.top;
                    long jFloatToRawIntBits3 = (jFloatToRawIntBits2 << 32) | (Float.floatToRawIntBits(f10) & 4294967295L);
                    Offset.Companion companion5 = Offset.Companion;
                    float f11 = rect.right - rect.left;
                    float f12 = rect.bottom - f10;
                    long jFloatToRawIntBits4 = Float.floatToRawIntBits(f11);
                    Size.Companion companion6 = Size.Companion;
                    graphicsLayer19.m550setRoundRectOutlineTNW_H78(jFloatToRawIntBits3, (4294967295L & Float.floatToRawIntBits(f12)) | (jFloatToRawIntBits4 << 32), 0.0f);
                } else if (outline instanceof Outline.Generic) {
                    graphicsLayer19.resetOutlineParams();
                    graphicsLayer19.outlinePath = ((Outline.Generic) outline).path;
                    graphicsLayer19.configureOutlineAndClip();
                } else if (outline instanceof Outline.Rounded) {
                    Outline.Rounded rounded = (Outline.Rounded) outline;
                    AndroidPath androidPath = rounded.roundRectPath;
                    if (androidPath != null) {
                        graphicsLayer19.resetOutlineParams();
                        graphicsLayer19.outlinePath = androidPath;
                        graphicsLayer19.configureOutlineAndClip();
                    } else {
                        RoundRect roundRect = rounded.roundRect;
                        Offset.Companion companion7 = Offset.Companion;
                        float width = roundRect.getWidth();
                        float height = roundRect.getHeight();
                        long jFloatToRawIntBits5 = Float.floatToRawIntBits(width);
                        Size.Companion companion8 = Size.Companion;
                        graphicsLayer19.m550setRoundRectOutlineTNW_H78((Float.floatToRawIntBits(roundRect.left) << 32) | (Float.floatToRawIntBits(roundRect.top) & 4294967295L), (4294967295L & Float.floatToRawIntBits(height)) | (jFloatToRawIntBits5 << 32), Float.intBitsToFloat((int) (roundRect.bottomLeftCornerRadius >> 32)));
                    }
                }
                boolean z3 = outline instanceof Outline.Generic;
            }
        }
        this.mutatedFields = reusableGraphicsLayerScope.mutatedFields;
        if (i2 != 0 || z) {
            WrapperRenderNodeLayerHelperMethods.INSTANCE.getClass();
            AndroidComposeView androidComposeView = this.ownerView;
            ViewParent parent = androidComposeView.getParent();
            if (parent != null) {
                parent.onDescendantInvalidated(androidComposeView, androidComposeView);
            }
        }
    }
}
