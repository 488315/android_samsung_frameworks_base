package androidx.compose.ui.graphics.layer;

import android.graphics.Outline;
import android.graphics.RectF;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawContextKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GraphicsLayer {
    public Outline androidOutline;
    public final ChildLayerDependenciesTracker childDependenciesTracker;
    public boolean clip;
    public final GraphicsLayerImpl impl;
    public androidx.compose.ui.graphics.Outline internalOutline;
    public boolean isReleased;
    public final LayerManager layerManager;
    public Path outlinePath;
    public int parentLayerUsages;
    public RectF pathBounds;
    public long pivotOffset;
    public AndroidPath roundRectClipPath;
    public float roundRectCornerRadius;
    public long roundRectOutlineSize;
    public long roundRectOutlineTopLeft;
    public long size;
    public CanvasDrawScope softwareDrawScope;
    public AndroidPaint softwareLayerPaint;
    public long topLeft;
    public boolean usePathForClip;
    public Density density = DrawContextKt.DefaultDensity;
    public LayoutDirection layoutDirection = LayoutDirection.Ltr;
    public Lambda drawBlock = new Function1() { // from class: androidx.compose.ui.graphics.layer.GraphicsLayer$drawBlock$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };
    public final Function1 clipDrawBlock = new GraphicsLayer$clipDrawBlock$1(this);
    public boolean outlineDirty = true;

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
        LayerManager.Companion.getClass();
        if (LayerManager.isRobolectric) {
            int i = LayerSnapshotV21.$r8$clinit;
        } else {
            int i2 = LayerSnapshotV28.$r8$clinit;
        }
    }

    public GraphicsLayer(GraphicsLayerImpl graphicsLayerImpl, LayerManager layerManager) {
        this.impl = graphicsLayerImpl;
        this.layerManager = layerManager;
        Offset.Companion.getClass();
        this.roundRectOutlineTopLeft = 0L;
        Size.Companion.getClass();
        this.roundRectOutlineSize = Size.Unspecified;
        this.childDependenciesTracker = new ChildLayerDependenciesTracker();
        graphicsLayerImpl.setClip(false);
        IntOffset.Companion.getClass();
        this.topLeft = 0L;
        IntSize.Companion.getClass();
        this.size = 0L;
        this.pivotOffset = Offset.Unspecified;
    }

    public final void configureOutlineAndClip() {
        if (this.outlineDirty) {
            boolean z = this.clip;
            GraphicsLayerImpl graphicsLayerImpl = this.impl;
            if (z || graphicsLayerImpl.getShadowElevation() > 0.0f) {
                Path path = this.outlinePath;
                if (path != null) {
                    RectF rectF = this.pathBounds;
                    if (rectF == null) {
                        rectF = new RectF();
                        this.pathBounds = rectF;
                    }
                    boolean z2 = path instanceof AndroidPath;
                    if (!z2) {
                        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                    }
                    AndroidPath androidPath = (AndroidPath) path;
                    androidPath.internalPath.computeBounds(rectF, false);
                    Outline outline = this.androidOutline;
                    if (outline == null) {
                        outline = new Outline();
                        this.androidOutline = outline;
                    }
                    OutlineVerificationHelper.INSTANCE.getClass();
                    if (!z2) {
                        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
                    }
                    outline.setPath(androidPath.internalPath);
                    this.usePathForClip = !outline.canClip();
                    this.outlinePath = path;
                    outline.setAlpha(graphicsLayerImpl.getAlpha());
                    int round = Math.round(rectF.width());
                    IntSize.Companion companion = IntSize.Companion;
                    graphicsLayerImpl.mo556setOutlineO0kMr_c(outline, (Math.round(rectF.height()) & 4294967295L) | (round << 32));
                    if (this.usePathForClip && this.clip) {
                        graphicsLayerImpl.setClip(false);
                        graphicsLayerImpl.discardDisplayList();
                    } else {
                        graphicsLayerImpl.setClip(this.clip);
                    }
                } else {
                    graphicsLayerImpl.setClip(this.clip);
                    Size.Companion.getClass();
                    Outline outline2 = this.androidOutline;
                    if (outline2 == null) {
                        outline2 = new Outline();
                        this.androidOutline = outline2;
                    }
                    Outline outline3 = outline2;
                    long m864toSizeozmzZPI = IntSizeKt.m864toSizeozmzZPI(this.size);
                    long j = this.roundRectOutlineTopLeft;
                    long j2 = this.roundRectOutlineSize;
                    long j3 = j2 == 9205357640488583168L ? m864toSizeozmzZPI : j2;
                    int i = (int) (j >> 32);
                    int i2 = (int) (j & 4294967295L);
                    outline3.setRoundRect(Math.round(Float.intBitsToFloat(i)), Math.round(Float.intBitsToFloat(i2)), Math.round(Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i)), Math.round(Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i2)), this.roundRectCornerRadius);
                    outline3.setAlpha(graphicsLayerImpl.getAlpha());
                    int round2 = Math.round(Float.intBitsToFloat((int) (j3 >> 32)));
                    IntSize.Companion companion2 = IntSize.Companion;
                    graphicsLayerImpl.mo556setOutlineO0kMr_c(outline3, (Math.round(Float.intBitsToFloat((int) (j3 & 4294967295L))) & 4294967295L) | (round2 << 32));
                }
            } else {
                graphicsLayerImpl.setClip(false);
                IntSize.Companion.getClass();
                graphicsLayerImpl.mo556setOutlineO0kMr_c(null, 0L);
            }
        }
        this.outlineDirty = false;
    }

    public final void discardContentIfReleasedAndHaveNoParentLayerUsages() {
        if (this.isReleased && this.parentLayerUsages == 0) {
            LayerManager layerManager = this.layerManager;
            if (layerManager != null) {
                layerManager.release(this);
            } else {
                discardDisplayList$ui_graphics_release();
            }
        }
    }

    public final void discardDisplayList$ui_graphics_release() {
        ChildLayerDependenciesTracker childLayerDependenciesTracker = this.childDependenciesTracker;
        GraphicsLayer graphicsLayer = childLayerDependenciesTracker.dependency;
        if (graphicsLayer != null) {
            graphicsLayer.parentLayerUsages--;
            graphicsLayer.discardContentIfReleasedAndHaveNoParentLayerUsages();
            childLayerDependenciesTracker.dependency = null;
        }
        MutableScatterSet mutableScatterSet = childLayerDependenciesTracker.dependenciesSet;
        if (mutableScatterSet != null) {
            Object[] objArr = mutableScatterSet.elements;
            long[] jArr = mutableScatterSet.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                r11.parentLayerUsages--;
                                ((GraphicsLayer) objArr[(i << 3) + i3]).discardContentIfReleasedAndHaveNoParentLayerUsages();
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        }
                    }
                    if (i == length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            mutableScatterSet.clear();
        }
        this.impl.discardDisplayList();
    }

    public final androidx.compose.ui.graphics.Outline getOutline() {
        androidx.compose.ui.graphics.Outline rectangle;
        androidx.compose.ui.graphics.Outline outline = this.internalOutline;
        Path path = this.outlinePath;
        if (outline != null) {
            return outline;
        }
        if (path != null) {
            Outline.Generic generic = new Outline.Generic(path);
            this.internalOutline = generic;
            return generic;
        }
        long m864toSizeozmzZPI = IntSizeKt.m864toSizeozmzZPI(this.size);
        long j = this.roundRectOutlineTopLeft;
        long j2 = this.roundRectOutlineSize;
        if (j2 != 9205357640488583168L) {
            m864toSizeozmzZPI = j2;
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (m864toSizeozmzZPI >> 32)) + intBitsToFloat;
        float intBitsToFloat4 = Float.intBitsToFloat((int) (m864toSizeozmzZPI & 4294967295L)) + intBitsToFloat2;
        if (this.roundRectCornerRadius > 0.0f) {
            long floatToRawIntBits = (Float.floatToRawIntBits(r0) << 32) | (4294967295L & Float.floatToRawIntBits(r0));
            CornerRadius.Companion companion = CornerRadius.Companion;
            rectangle = new Outline.Rounded(RoundRectKt.m412RoundRectgG7oq9Y(intBitsToFloat, intBitsToFloat2, intBitsToFloat3, intBitsToFloat4, floatToRawIntBits));
        } else {
            rectangle = new Outline.Rectangle(new Rect(intBitsToFloat, intBitsToFloat2, intBitsToFloat3, intBitsToFloat4));
        }
        this.internalOutline = rectangle;
        return rectangle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: record-mL-hObY, reason: not valid java name */
    public final void m547recordmLhObY(Density density, LayoutDirection layoutDirection, long j, Function1 function1) {
        boolean m861equalsimpl0 = IntSize.m861equalsimpl0(this.size, j);
        GraphicsLayerImpl graphicsLayerImpl = this.impl;
        if (!m861equalsimpl0) {
            this.size = j;
            long j2 = this.topLeft;
            IntOffset.Companion companion = IntOffset.Companion;
            graphicsLayerImpl.mo558setPositionH0pRuoY((int) (j2 >> 32), (int) (j2 & 4294967295L), j);
            if (this.roundRectOutlineSize == 9205357640488583168L) {
                this.outlineDirty = true;
                configureOutlineAndClip();
            }
        }
        this.density = density;
        this.layoutDirection = layoutDirection;
        this.drawBlock = (Lambda) function1;
        graphicsLayerImpl.getClass();
        recordInternal();
    }

    public final void recordInternal() {
        ChildLayerDependenciesTracker childLayerDependenciesTracker = this.childDependenciesTracker;
        childLayerDependenciesTracker.oldDependency = childLayerDependenciesTracker.dependency;
        MutableScatterSet mutableScatterSet = childLayerDependenciesTracker.dependenciesSet;
        if (mutableScatterSet != null && mutableScatterSet.isNotEmpty()) {
            MutableScatterSet mutableScatterSet2 = childLayerDependenciesTracker.oldDependenciesSet;
            if (mutableScatterSet2 == null) {
                mutableScatterSet2 = ScatterSetKt.mutableScatterSetOf();
                childLayerDependenciesTracker.oldDependenciesSet = mutableScatterSet2;
            }
            mutableScatterSet2.plusAssign((ScatterSet) mutableScatterSet);
            mutableScatterSet.clear();
        }
        childLayerDependenciesTracker.trackingInProgress = true;
        this.impl.record(this.density, this.layoutDirection, this, this.clipDrawBlock);
        childLayerDependenciesTracker.trackingInProgress = false;
        GraphicsLayer graphicsLayer = childLayerDependenciesTracker.oldDependency;
        if (graphicsLayer != null) {
            graphicsLayer.parentLayerUsages--;
            graphicsLayer.discardContentIfReleasedAndHaveNoParentLayerUsages();
        }
        MutableScatterSet mutableScatterSet3 = childLayerDependenciesTracker.oldDependenciesSet;
        if (mutableScatterSet3 == null || !mutableScatterSet3.isNotEmpty()) {
            return;
        }
        Object[] objArr = mutableScatterSet3.elements;
        long[] jArr = mutableScatterSet3.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            r10.parentLayerUsages--;
                            ((GraphicsLayer) objArr[(i << 3) + i3]).discardContentIfReleasedAndHaveNoParentLayerUsages();
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                } else {
                    i++;
                }
            }
        }
        mutableScatterSet3.clear();
    }

    public final void resetOutlineParams() {
        this.internalOutline = null;
        this.outlinePath = null;
        Size.Companion.getClass();
        this.roundRectOutlineSize = Size.Unspecified;
        Offset.Companion.getClass();
        this.roundRectOutlineTopLeft = 0L;
        this.roundRectCornerRadius = 0.0f;
        this.outlineDirty = true;
        this.usePathForClip = false;
    }

    public final void setAlpha(float f) {
        GraphicsLayerImpl graphicsLayerImpl = this.impl;
        if (graphicsLayerImpl.getAlpha() == f) {
            return;
        }
        graphicsLayerImpl.setAlpha(f);
    }

    /* renamed from: setRoundRectOutline-TNW_H78, reason: not valid java name */
    public final void m548setRoundRectOutlineTNW_H78(long j, long j2, float f) {
        if (Offset.m396equalsimpl0(this.roundRectOutlineTopLeft, j) && Size.m414equalsimpl0(this.roundRectOutlineSize, j2) && this.roundRectCornerRadius == f && this.outlinePath == null) {
            return;
        }
        resetOutlineParams();
        this.roundRectOutlineTopLeft = j;
        this.roundRectOutlineSize = j2;
        this.roundRectCornerRadius = f;
        configureOutlineAndClip();
    }
}
