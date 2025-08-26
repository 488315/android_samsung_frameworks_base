package androidx.compose.ui.graphics.vector;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CanvasKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmapConfig;
import androidx.compose.ui.graphics.ImageBitmapKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
public final class VectorComponent extends VNode {
    public final DrawCache cacheDrawScope;
    public final Function1 drawVectorBlock;
    public final MutableState intrinsicColorFilter$delegate;
    public Lambda invalidateCallback;
    public boolean isDirty;
    public String name;
    public long previousDrawSize;
    public final GroupComponent root;
    public float rootScaleX;
    public float rootScaleY;
    public BlendModeColorFilter tintFilter;
    public final MutableState viewportSize$delegate;

    /* JADX WARN: Type inference failed for: r1v0, types: [androidx.compose.ui.graphics.vector.VectorComponent$1, kotlin.jvm.internal.Lambda] */
    public VectorComponent(GroupComponent groupComponent) {
        super(null);
        this.root = groupComponent;
        groupComponent.invalidateListener = new Function1() { // from class: androidx.compose.ui.graphics.vector.VectorComponent.1
            /* JADX WARN: Type inference failed for: r0v2, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                VectorComponent vectorComponent = VectorComponent.this;
                vectorComponent.isDirty = true;
                vectorComponent.invalidateCallback.invoke();
                return Unit.INSTANCE;
            }
        };
        this.name = "";
        this.isDirty = true;
        this.cacheDrawScope = new DrawCache();
        this.invalidateCallback = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorComponent$invalidateCallback$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        this.intrinsicColorFilter$delegate = SnapshotStateKt.mutableStateOf$default(null);
        Size.Companion.getClass();
        this.viewportSize$delegate = SnapshotStateKt.mutableStateOf$default(Size.m415boximpl(0L));
        this.previousDrawSize = Size.Unspecified;
        this.rootScaleX = 1.0f;
        this.rootScaleY = 1.0f;
        this.drawVectorBlock = new VectorComponent$drawVectorBlock$1(this);
    }

    @Override // androidx.compose.ui.graphics.vector.VNode
    public final void draw(DrawScope drawScope) {
        draw(drawScope, 1.0f, null);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Params: \tname: ");
        sb.append(this.name);
        sb.append("\n\tviewportWidth: ");
        MutableState mutableState = this.viewportSize$delegate;
        sb.append(Float.intBitsToFloat((int) (((Size) ((SnapshotMutableStateImpl) mutableState).getValue()).packedValue >> 32)));
        sb.append("\n\tviewportHeight: ");
        sb.append(Float.intBitsToFloat((int) (((Size) ((SnapshotMutableStateImpl) mutableState).getValue()).packedValue & 4294967295L)));
        sb.append("\n");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void draw(DrawScope drawScope, float f, ColorFilter colorFilter) {
        int i;
        MutableState mutableState;
        DrawScope drawScope2;
        ColorFilter colorFilter2;
        int iM432getConfig_sVssgQ;
        GroupComponent groupComponent = this.root;
        boolean z = groupComponent.isTintable;
        MutableState mutableState2 = this.intrinsicColorFilter$delegate;
        if (z && groupComponent.tintColor != 16 && VectorKt.tintableWithAlphaMask((ColorFilter) ((SnapshotMutableStateImpl) mutableState2).getValue()) && VectorKt.tintableWithAlphaMask(colorFilter)) {
            ImageBitmapConfig.Companion.getClass();
            i = ImageBitmapConfig.Alpha8;
        } else {
            ImageBitmapConfig.Companion.getClass();
            i = 0;
        }
        boolean z2 = this.isDirty;
        DrawCache drawCache = this.cacheDrawScope;
        if (z2 || !Size.m416equalsimpl0(this.previousDrawSize, drawScope.mo547getSizeNHjbRc())) {
            ImageBitmapConfig.Companion.getClass();
            this.tintFilter = i == ImageBitmapConfig.Alpha8 ? ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, groupComponent.tintColor) : null;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32));
            MutableState mutableState3 = this.viewportSize$delegate;
            this.rootScaleX = fIntBitsToFloat / Float.intBitsToFloat((int) (((Size) ((SnapshotMutableStateImpl) mutableState3).getValue()).packedValue >> 32));
            this.rootScaleY = Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)) / Float.intBitsToFloat((int) (((Size) ((SnapshotMutableStateImpl) mutableState3).getValue()).packedValue & 4294967295L));
            long jCeil = (((int) Math.ceil(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32)))) << 32) | (((int) Math.ceil(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L)))) & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
            LayoutDirection layoutDirection = drawScope.getLayoutDirection();
            Function1 function1 = this.drawVectorBlock;
            AndroidImageBitmap androidImageBitmapM481ImageBitmapx__hDU$default = drawCache.mCachedImage;
            AndroidCanvas androidCanvasCanvas = drawCache.cachedCanvas;
            if (androidImageBitmapM481ImageBitmapx__hDU$default == null || androidCanvasCanvas == null || ((int) (jCeil >> 32)) > androidImageBitmapM481ImageBitmapx__hDU$default.bitmap.getWidth() || ((int) (jCeil & 4294967295L)) > androidImageBitmapM481ImageBitmapx__hDU$default.bitmap.getHeight() || drawCache.config != i) {
                androidImageBitmapM481ImageBitmapx__hDU$default = ImageBitmapKt.m481ImageBitmapx__hDU$default((int) (jCeil >> 32), (int) (4294967295L & jCeil), i);
                androidCanvasCanvas = CanvasKt.Canvas(androidImageBitmapM481ImageBitmapx__hDU$default);
                drawCache.mCachedImage = androidImageBitmapM481ImageBitmapx__hDU$default;
                drawCache.cachedCanvas = androidCanvasCanvas;
                drawCache.config = i;
            }
            drawCache.size = jCeil;
            long jM866toSizeozmzZPI = IntSizeKt.m866toSizeozmzZPI(jCeil);
            CanvasDrawScope canvasDrawScope = drawCache.cacheScope;
            CanvasDrawScope.DrawParams drawParams = canvasDrawScope.drawParams;
            Density density = drawParams.density;
            LayoutDirection layoutDirection2 = drawParams.layoutDirection;
            Canvas canvas = drawParams.canvas;
            long j = drawParams.size;
            mutableState = mutableState2;
            drawScope2 = drawScope;
            drawParams.density = drawScope2;
            drawParams.layoutDirection = layoutDirection;
            drawParams.canvas = androidCanvasCanvas;
            drawParams.size = jM866toSizeozmzZPI;
            androidCanvasCanvas.save();
            Color.Companion.getClass();
            long j2 = Color.Black;
            BlendMode.Companion.getClass();
            DrawScope.m541drawRectnJ9OG0$default(canvasDrawScope, j2, 0L, 0L, 0.0f, null, null, 0, 62);
            ((VectorComponent$drawVectorBlock$1) function1).mo781invoke(canvasDrawScope);
            androidCanvasCanvas.restore();
            CanvasDrawScope.DrawParams drawParams2 = canvasDrawScope.drawParams;
            drawParams2.density = density;
            drawParams2.layoutDirection = layoutDirection2;
            drawParams2.canvas = canvas;
            drawParams2.size = j;
            androidImageBitmapM481ImageBitmapx__hDU$default.bitmap.prepareToDraw();
            this.isDirty = false;
            this.previousDrawSize = drawScope2.mo547getSizeNHjbRc();
        } else {
            AndroidImageBitmap androidImageBitmap = drawCache.mCachedImage;
            if (androidImageBitmap != null) {
                iM432getConfig_sVssgQ = androidImageBitmap.m432getConfig_sVssgQ();
            } else {
                ImageBitmapConfig.Companion.getClass();
                iM432getConfig_sVssgQ = 0;
            }
            if (i == iM432getConfig_sVssgQ) {
                mutableState = mutableState2;
                drawScope2 = drawScope;
            }
        }
        if (colorFilter != null) {
            colorFilter2 = colorFilter;
        } else {
            colorFilter2 = ((ColorFilter) ((SnapshotMutableStateImpl) mutableState).getValue()) != null ? (ColorFilter) ((SnapshotMutableStateImpl) mutableState).getValue() : this.tintFilter;
        }
        AndroidImageBitmap androidImageBitmap2 = drawCache.mCachedImage;
        if (androidImageBitmap2 == null) {
            InlineClassHelperKt.throwIllegalStateException("drawCachedImage must be invoked first before attempting to draw the result into another destination");
        }
        DrawScope.m535drawImageAZ2fEMs$default(drawScope2, androidImageBitmap2, 0L, drawCache.size, 0L, f, colorFilter2, 0, 858);
    }
}
