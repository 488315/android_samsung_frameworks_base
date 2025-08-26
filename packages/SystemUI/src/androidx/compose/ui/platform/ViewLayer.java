package androidx.compose.ui.platform;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.CanvasHolder;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RenderEffect;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.unit.IntOffset;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ViewLayer extends View implements OwnedLayer {
    public static boolean hasRetrievedMethod;
    public static Field recreateDisplayList;
    public static boolean shouldUseDispatchDraw;
    public static Method updateDisplayListIfDirtyMethod;
    public final CanvasHolder canvasHolder;
    public Rect clipBoundsCache;
    public boolean clipToBounds;
    public final DrawChildContainer container;
    public Function2 drawBlock;
    public boolean drawnWithZ;
    public Function0 invalidateParentLayer;
    public boolean isInvalidated;
    public AndroidPaint layerPaint;
    public boolean mHasOverlappingRendering;
    public long mTransformOrigin;
    public final LayerMatrixCache matrixCache;
    public int mutatedFields;
    public final OutlineResolver outlineResolver;
    public final AndroidComposeView ownerView;
    public static final Companion Companion = new Companion(null);
    public static final Function2 getMatrix = new Function2() { // from class: androidx.compose.ui.platform.ViewLayer$Companion$getMatrix$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((Matrix) obj2).set(((View) obj).getMatrix());
            return Unit.INSTANCE;
        }
    };
    public static final ViewLayer$Companion$OutlineProvider$1 OutlineProvider = new ViewOutlineProvider() { // from class: androidx.compose.ui.platform.ViewLayer$Companion$OutlineProvider$1
        @Override // android.view.ViewOutlineProvider
        public final void getOutline(View view, Outline outline) {
            Outline androidOutline = ((ViewLayer) view).outlineResolver.getAndroidOutline();
            androidOutline.getClass();
            outline.set(androidOutline);
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void updateDisplayList(View view) {
            try {
                if (!ViewLayer.hasRetrievedMethod) {
                    ViewLayer.hasRetrievedMethod = true;
                    ViewLayer.updateDisplayListIfDirtyMethod = (Method) Class.class.getDeclaredMethod("getDeclaredMethod", String.class, new Class[0].getClass()).invoke(View.class, "updateDisplayListIfDirty", new Class[0]);
                    ViewLayer.recreateDisplayList = (Field) Class.class.getDeclaredMethod("getDeclaredField", String.class).invoke(View.class, "mRecreateDisplayList");
                    Method method = ViewLayer.updateDisplayListIfDirtyMethod;
                    if (method != null) {
                        method.setAccessible(true);
                    }
                    Field field = ViewLayer.recreateDisplayList;
                    if (field != null) {
                        field.setAccessible(true);
                    }
                }
                Field field2 = ViewLayer.recreateDisplayList;
                if (field2 != null) {
                    field2.setBoolean(view, true);
                }
                Method method2 = ViewLayer.updateDisplayListIfDirtyMethod;
                if (method2 != null) {
                    method2.invoke(view, null);
                }
            } catch (Throwable unused) {
                ViewLayer.shouldUseDispatchDraw = true;
            }
        }

        private Companion() {
        }
    }

    public ViewLayer(AndroidComposeView androidComposeView, DrawChildContainer drawChildContainer, Function2 function2, Function0 function0) {
        super(androidComposeView.getContext());
        this.ownerView = androidComposeView;
        this.container = drawChildContainer;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        this.outlineResolver = new OutlineResolver();
        this.canvasHolder = new CanvasHolder();
        this.matrixCache = new LayerMatrixCache(getMatrix);
        TransformOrigin.Companion.getClass();
        this.mTransformOrigin = TransformOrigin.Center;
        this.mHasOverlappingRendering = true;
        setWillNotDraw(false);
        drawChildContainer.addView(this);
        View.generateViewId();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void destroy() {
        setInvalidated(false);
        AndroidComposeView androidComposeView = this.ownerView;
        androidComposeView.observationClearRequested = true;
        this.drawBlock = null;
        this.invalidateParentLayer = null;
        androidComposeView.recycle$ui_release(this);
        this.container.removeViewInLayout(this);
    }

    @Override // android.view.View
    public final void dispatchDraw(Canvas canvas) {
        boolean z;
        CanvasHolder canvasHolder = this.canvasHolder;
        AndroidCanvas androidCanvas = canvasHolder.androidCanvas;
        Canvas canvas2 = androidCanvas.internalCanvas;
        androidCanvas.internalCanvas = canvas;
        if (getManualClipPath() == null && canvas.isHardwareAccelerated()) {
            z = false;
        } else {
            androidCanvas.save();
            this.outlineResolver.clipToOutline(androidCanvas);
            z = true;
        }
        Function2 function2 = this.drawBlock;
        if (function2 != null) {
            function2.invoke(androidCanvas, null);
        }
        if (z) {
            androidCanvas.restore();
        }
        canvasHolder.androidCanvas.internalCanvas = canvas2;
        setInvalidated(false);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void drawLayer(androidx.compose.ui.graphics.Canvas canvas, GraphicsLayer graphicsLayer) {
        boolean z = getElevation() > 0.0f;
        this.drawnWithZ = z;
        if (z) {
            canvas.enableZ();
        }
        this.container.drawChild$ui_release(canvas, this, getDrawingTime());
        if (this.drawnWithZ) {
            canvas.disableZ();
        }
    }

    public final Path getManualClipPath() {
        if (!getClipToOutline()) {
            return null;
        }
        OutlineResolver outlineResolver = this.outlineResolver;
        if (!outlineResolver.usePathForClip) {
            return null;
        }
        outlineResolver.updateCache();
        return outlineResolver.outlinePath;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: getUnderlyingMatrix-sQKQjiQ */
    public final float[] mo684getUnderlyingMatrixsQKQjiQ() {
        return this.matrixCache.m708calculateMatrixGrdbGEg(this);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    @Override // android.view.View, androidx.compose.ui.node.OwnedLayer
    public final void invalidate() {
        if (this.isInvalidated) {
            return;
        }
        setInvalidated(true);
        super.invalidate();
        this.ownerView.invalidate();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: inverseTransform-58bKbWc */
    public final void mo685inverseTransform58bKbWc(float[] fArr) {
        float[] fArrM707calculateInverseMatrixbWbORWo = this.matrixCache.m707calculateInverseMatrixbWbORWo(this);
        if (fArrM707calculateInverseMatrixbWbORWo != null) {
            androidx.compose.ui.graphics.Matrix.m489timesAssign58bKbWc(fArr, fArrM707calculateInverseMatrixbWbORWo);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: isInLayer-k-4lQ0M */
    public final boolean mo686isInLayerk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (4294967295L & j));
        if (this.clipToBounds) {
            return 0.0f <= fIntBitsToFloat && fIntBitsToFloat < ((float) getWidth()) && 0.0f <= fIntBitsToFloat2 && fIntBitsToFloat2 < ((float) getHeight());
        }
        if (getClipToOutline()) {
            return this.outlineResolver.m709isInOutlinek4lQ0M(j);
        }
        return true;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void mapBounds(MutableRect mutableRect, boolean z) {
        if (!z) {
            LayerMatrixCache layerMatrixCache = this.matrixCache;
            float[] fArrM708calculateMatrixGrdbGEg = layerMatrixCache.m708calculateMatrixGrdbGEg(this);
            if (layerMatrixCache.isIdentity) {
                return;
            }
            androidx.compose.ui.graphics.Matrix.m485mapimpl(fArrM708calculateMatrixGrdbGEg, mutableRect);
            return;
        }
        LayerMatrixCache layerMatrixCache2 = this.matrixCache;
        float[] fArrM707calculateInverseMatrixbWbORWo = layerMatrixCache2.m707calculateInverseMatrixbWbORWo(this);
        if (fArrM707calculateInverseMatrixbWbORWo != null) {
            if (layerMatrixCache2.isIdentity) {
                return;
            }
            androidx.compose.ui.graphics.Matrix.m485mapimpl(fArrM707calculateInverseMatrixbWbORWo, mutableRect);
        } else {
            mutableRect.left = 0.0f;
            mutableRect.top = 0.0f;
            mutableRect.right = 0.0f;
            mutableRect.bottom = 0.0f;
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: mapOffset-8S9VItk */
    public final long mo687mapOffset8S9VItk(long j, boolean z) {
        if (z) {
            LayerMatrixCache layerMatrixCache = this.matrixCache;
            float[] fArrM707calculateInverseMatrixbWbORWo = layerMatrixCache.m707calculateInverseMatrixbWbORWo(this);
            if (fArrM707calculateInverseMatrixbWbORWo == null) {
                Offset.Companion.getClass();
                return Offset.Infinite;
            }
            if (!layerMatrixCache.isIdentity) {
                return androidx.compose.ui.graphics.Matrix.m484mapMKHz9U(j, fArrM707calculateInverseMatrixbWbORWo);
            }
        } else {
            LayerMatrixCache layerMatrixCache2 = this.matrixCache;
            float[] fArrM708calculateMatrixGrdbGEg = layerMatrixCache2.m708calculateMatrixGrdbGEg(this);
            if (!layerMatrixCache2.isIdentity) {
                return androidx.compose.ui.graphics.Matrix.m484mapMKHz9U(j, fArrM708calculateMatrixGrdbGEg);
            }
        }
        return j;
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: move--gyyYBs */
    public final void mo688movegyyYBs(long j) {
        IntOffset.Companion companion = IntOffset.Companion;
        int i = (int) (j >> 32);
        if (i != getLeft()) {
            offsetLeftAndRight(i - getLeft());
            this.matrixCache.invalidate();
        }
        int i2 = (int) (j & 4294967295L);
        if (i2 != getTop()) {
            offsetTopAndBottom(i2 - getTop());
            this.matrixCache.invalidate();
        }
    }

    public final void resetClipBounds() {
        Rect rect;
        if (this.clipToBounds) {
            Rect rect2 = this.clipBoundsCache;
            if (rect2 == null) {
                this.clipBoundsCache = new Rect(0, 0, getWidth(), getHeight());
            } else {
                rect2.getClass();
                rect2.set(0, 0, getWidth(), getHeight());
            }
            rect = this.clipBoundsCache;
        } else {
            rect = null;
        }
        setClipBounds(rect);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: resize-ozmzZPI */
    public final void mo689resizeozmzZPI(long j) {
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        if (i == getWidth() && i2 == getHeight()) {
            return;
        }
        setPivotX(TransformOrigin.m505getPivotFractionXimpl(this.mTransformOrigin) * i);
        setPivotY(TransformOrigin.m506getPivotFractionYimpl(this.mTransformOrigin) * i2);
        setOutlineProvider(this.outlineResolver.getAndroidOutline() != null ? OutlineProvider : null);
        layout(getLeft(), getTop(), getLeft() + i, getTop() + i2);
        resetClipBounds();
        this.matrixCache.invalidate();
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void reuseLayer(Function2 function2, Function0 function0) {
        this.container.addView(this);
        LayerMatrixCache layerMatrixCache = this.matrixCache;
        layerMatrixCache.isDirty = false;
        layerMatrixCache.isInverseDirty = false;
        layerMatrixCache.isIdentity = true;
        layerMatrixCache.isInverseValid = true;
        androidx.compose.ui.graphics.Matrix.m486resetimpl(layerMatrixCache.matrixCache);
        androidx.compose.ui.graphics.Matrix.m486resetimpl(layerMatrixCache.inverseMatrixCache);
        this.clipToBounds = false;
        this.drawnWithZ = false;
        TransformOrigin.Companion.getClass();
        this.mTransformOrigin = TransformOrigin.Center;
        this.drawBlock = function2;
        this.invalidateParentLayer = function0;
        setInvalidated(false);
    }

    public final void setInvalidated(boolean z) {
        if (z != this.isInvalidated) {
            this.isInvalidated = z;
            this.ownerView.notifyLayerIsDirty$ui_release(this, z);
        }
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    /* renamed from: transform-58bKbWc */
    public final void mo690transform58bKbWc(float[] fArr) {
        androidx.compose.ui.graphics.Matrix.m489timesAssign58bKbWc(fArr, this.matrixCache.m708calculateMatrixGrdbGEg(this));
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void updateDisplayList() {
        if (!this.isInvalidated || shouldUseDispatchDraw) {
            return;
        }
        Companion.getClass();
        Companion.updateDisplayList(this);
        setInvalidated(false);
    }

    @Override // androidx.compose.ui.node.OwnedLayer
    public final void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope) {
        int i;
        Function0 function0;
        int i2 = reusableGraphicsLayerScope.mutatedFields | this.mutatedFields;
        if ((i2 & 4096) != 0) {
            long j = reusableGraphicsLayerScope.transformOrigin;
            this.mTransformOrigin = j;
            setPivotX(TransformOrigin.m505getPivotFractionXimpl(j) * getWidth());
            setPivotY(TransformOrigin.m506getPivotFractionYimpl(this.mTransformOrigin) * getHeight());
        }
        if ((i2 & 1) != 0) {
            setScaleX(reusableGraphicsLayerScope.scaleX);
        }
        if ((i2 & 2) != 0) {
            setScaleY(reusableGraphicsLayerScope.scaleY);
        }
        if ((i2 & 4) != 0) {
            setAlpha(reusableGraphicsLayerScope.alpha);
        }
        if ((i2 & 8) != 0) {
            setTranslationX(reusableGraphicsLayerScope.translationX);
        }
        if ((i2 & 16) != 0) {
            setTranslationY(reusableGraphicsLayerScope.translationY);
        }
        if ((i2 & 32) != 0) {
            setElevation(reusableGraphicsLayerScope.shadowElevation);
        }
        if ((i2 & 1024) != 0) {
            setRotation(reusableGraphicsLayerScope.rotationZ);
        }
        if ((i2 & 256) != 0) {
            setRotationX(reusableGraphicsLayerScope.rotationX);
        }
        if ((i2 & 512) != 0) {
            setRotationY(reusableGraphicsLayerScope.rotationY);
        }
        if ((i2 & 2048) != 0) {
            setCameraDistance(reusableGraphicsLayerScope.cameraDistance * getResources().getDisplayMetrics().densityDpi);
        }
        boolean z = true;
        boolean z2 = getManualClipPath() != null;
        boolean z3 = reusableGraphicsLayerScope.clip;
        boolean z4 = z3 && reusableGraphicsLayerScope.shape != RectangleShapeKt.RectangleShape;
        if ((i2 & 24576) != 0) {
            this.clipToBounds = z3 && reusableGraphicsLayerScope.shape == RectangleShapeKt.RectangleShape;
            resetClipBounds();
            setClipToOutline(z4);
        }
        boolean zM710updateS_szKao = this.outlineResolver.m710updateS_szKao(reusableGraphicsLayerScope.outline, reusableGraphicsLayerScope.alpha, z4, reusableGraphicsLayerScope.shadowElevation, reusableGraphicsLayerScope.size);
        OutlineResolver outlineResolver = this.outlineResolver;
        Paint paint = null;
        if (outlineResolver.cacheIsDirty) {
            setOutlineProvider(outlineResolver.getAndroidOutline() != null ? OutlineProvider : null);
        }
        boolean z5 = getManualClipPath() != null;
        if (z2 != z5 || (z5 && zM710updateS_szKao)) {
            invalidate();
        }
        if (!this.drawnWithZ && getElevation() > 0.0f && (function0 = this.invalidateParentLayer) != null) {
            function0.invoke();
        }
        if ((i2 & 7963) != 0) {
            this.matrixCache.invalidate();
        }
        if ((i2 & 64) != 0) {
            ViewLayerVerificationHelper28 viewLayerVerificationHelper28 = ViewLayerVerificationHelper28.INSTANCE;
            int iM469toArgb8_81llA = ColorKt.m469toArgb8_81llA(reusableGraphicsLayerScope.ambientShadowColor);
            viewLayerVerificationHelper28.getClass();
            setOutlineAmbientShadowColor(iM469toArgb8_81llA);
        }
        if ((i2 & 128) != 0) {
            ViewLayerVerificationHelper28 viewLayerVerificationHelper282 = ViewLayerVerificationHelper28.INSTANCE;
            int iM469toArgb8_81llA2 = ColorKt.m469toArgb8_81llA(reusableGraphicsLayerScope.spotShadowColor);
            viewLayerVerificationHelper282.getClass();
            setOutlineSpotShadowColor(iM469toArgb8_81llA2);
        }
        if ((131072 & i2) != 0) {
            ViewLayerVerificationHelper31 viewLayerVerificationHelper31 = ViewLayerVerificationHelper31.INSTANCE;
            RenderEffect renderEffect = reusableGraphicsLayerScope.renderEffect;
            viewLayerVerificationHelper31.getClass();
            setRenderEffect(renderEffect != null ? renderEffect.asAndroidRenderEffect() : null);
        }
        boolean z6 = ((262144 & i2) == 0 && (524288 & i2) == 0) ? false : true;
        if ((i2 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 || z6) {
            if (z6) {
                CompositingStrategy.Companion.getClass();
                i = CompositingStrategy.Offscreen;
            } else {
                i = reusableGraphicsLayerScope.compositingStrategy;
            }
            CompositingStrategy.Companion.getClass();
            if (i == CompositingStrategy.Offscreen) {
                if (z6) {
                    AndroidPaint androidPaint = this.layerPaint;
                    if (androidPaint == null) {
                        androidPaint = new AndroidPaint();
                        this.layerPaint = androidPaint;
                    }
                    androidPaint.setColorFilter(reusableGraphicsLayerScope.colorFilter);
                    androidPaint.m439setBlendModes9anfk8(reusableGraphicsLayerScope.blendMode);
                    paint = androidPaint.internalPaint;
                }
                setLayerType(2, paint);
            } else if (i == CompositingStrategy.ModulateAlpha) {
                setLayerType(0, null);
                z = false;
            } else {
                setLayerType(0, null);
            }
            this.mHasOverlappingRendering = z;
        }
        this.mutatedFields = reusableGraphicsLayerScope.mutatedFields;
    }

    @Override // android.view.View
    public final void forceLayout() {
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
