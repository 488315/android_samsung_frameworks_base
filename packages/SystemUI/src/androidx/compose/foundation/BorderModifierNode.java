package androidx.compose.foundation;

import androidx.compose.ui.draw.CacheDrawModifierNode;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.RoundRectKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.ImageBitmapConfig;
import androidx.compose.ui.graphics.ImageBitmapKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathOperation;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt$asDrawTransform$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public final class BorderModifierNode extends DelegatingNode {
    public BorderCache borderCache;
    public Brush brush;
    public final CacheDrawModifierNode drawWithCacheModifierNode;
    public Shape shape;
    public float width;

    public /* synthetic */ BorderModifierNode(float f, Brush brush, Shape shape, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, brush, shape);
    }

    private BorderModifierNode(float f, Brush brush, Shape shape) {
        this.width = f;
        this.brush = brush;
        this.shape = shape;
        CacheDrawModifierNode CacheDrawModifierNode = DrawModifierKt.CacheDrawModifierNode(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawWithCacheModifierNode$1
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:48:0x01b1  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x0214  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj) throws Throwable {
                final long j;
                int i;
                BlendModeColorFilter blendModeColorFilterM465tintxETnrds$default;
                boolean z;
                BlendModeColorFilter blendModeColorFilter;
                Path path;
                long j2;
                ImageBitmap imageBitmap;
                CanvasDrawScope canvasDrawScope;
                float f2;
                float f3;
                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1;
                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$12;
                long jM528getSizeNHjbRc;
                CacheDrawScope cacheDrawScope = (CacheDrawScope) obj;
                if (cacheDrawScope.getDensity() * this.this$0.width < 0.0f || Size.m418getMinDimensionimpl(cacheDrawScope.cacheParams.mo361getSizeNHjbRc()) <= 0.0f) {
                    return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderKt$drawContentWithoutBorder$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            ((LayoutNodeDrawScope) ((ContentDrawScope) obj2)).drawContent();
                            return Unit.INSTANCE;
                        }
                    });
                }
                float f4 = this.this$0.width;
                Dp.Companion.getClass();
                float f5 = 2;
                final float fMin = Math.min(Dp.m838equalsimpl0(f4, 0.0f) ? 1.0f : (float) Math.ceil(cacheDrawScope.getDensity() * this.this$0.width), (float) Math.ceil(Size.m418getMinDimensionimpl(cacheDrawScope.cacheParams.mo361getSizeNHjbRc()) / f5));
                final float f6 = fMin / f5;
                final long jFloatToRawIntBits = (Float.floatToRawIntBits(f6) << 32) | (Float.floatToRawIntBits(f6) & 4294967295L);
                Offset.Companion companion = Offset.Companion;
                final long jFloatToRawIntBits2 = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() & 4294967295L)) - fMin) & 4294967295L) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() >> 32)) - fMin) << 32);
                float f7 = fMin * f5;
                boolean z2 = f7 > Size.m418getMinDimensionimpl(cacheDrawScope.cacheParams.mo361getSizeNHjbRc());
                Outline outlineMo41createOutlinePq9zytI = this.this$0.shape.mo41createOutlinePq9zytI(cacheDrawScope.cacheParams.mo361getSizeNHjbRc(), cacheDrawScope.cacheParams.getLayoutDirection(), cacheDrawScope);
                if (!(outlineMo41createOutlinePq9zytI instanceof Outline.Generic)) {
                    if (!(outlineMo41createOutlinePq9zytI instanceof Outline.Rounded)) {
                        boolean z3 = z2;
                        if (!(outlineMo41createOutlinePq9zytI instanceof Outline.Rectangle)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        final Brush brush2 = this.this$0.brush;
                        if (z3) {
                            Offset.Companion.getClass();
                            j = 0;
                        } else {
                            j = jFloatToRawIntBits;
                        }
                        final long jMo361getSizeNHjbRc = z3 ? cacheDrawScope.cacheParams.mo361getSizeNHjbRc() : jFloatToRawIntBits2;
                        final DrawStyle stroke = z3 ? Fill.INSTANCE : new Stroke(fMin, 0.0f, 0, 0, null, 30, null);
                        return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderKt$drawRectBorder$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                                layoutNodeDrawScope.drawContent();
                                DrawScope.m540drawRectAsUm42w$default(layoutNodeDrawScope, brush2, j, jMo361getSizeNHjbRc, 0.0f, stroke, 0, 104);
                                return Unit.INSTANCE;
                            }
                        });
                    }
                    BorderModifierNode borderModifierNode = this.this$0;
                    final Brush brush3 = borderModifierNode.brush;
                    Outline.Rounded rounded = (Outline.Rounded) outlineMo41createOutlinePq9zytI;
                    boolean zIsSimple = RoundRectKt.isSimple(rounded.roundRect);
                    RoundRect roundRect = rounded.roundRect;
                    if (zIsSimple) {
                        final long j3 = roundRect.topLeftCornerRadius;
                        final Stroke stroke2 = new Stroke(fMin, 0.0f, 0, 0, null, 30, null);
                        final boolean z4 = z2;
                        return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                                layoutNodeDrawScope.drawContent();
                                if (z4) {
                                    DrawScope.m542drawRoundRectZuiqVtQ$default(layoutNodeDrawScope, brush3, 0L, 0L, j3, 0.0f, null, IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode);
                                } else {
                                    float fIntBitsToFloat = Float.intBitsToFloat((int) (j3 >> 32));
                                    float f8 = f6;
                                    if (fIntBitsToFloat < f8) {
                                        float f9 = fMin;
                                        CanvasDrawScope canvasDrawScope2 = layoutNodeDrawScope.canvasDrawScope;
                                        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (canvasDrawScope2.mo547getSizeNHjbRc() >> 32)) - fMin;
                                        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (canvasDrawScope2.mo547getSizeNHjbRc() & 4294967295L)) - fMin;
                                        ClipOp.Companion.getClass();
                                        Brush brush4 = brush3;
                                        long j4 = j3;
                                        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$13 = canvasDrawScope2.drawContext;
                                        long jM528getSizeNHjbRc2 = canvasDrawScope$drawContext$13.m528getSizeNHjbRc();
                                        canvasDrawScope$drawContext$13.getCanvas().save();
                                        try {
                                            canvasDrawScope$drawContext$13.transform.m530clipRectN_I0leg(f9, f9, fIntBitsToFloat2, fIntBitsToFloat3, 0);
                                            DrawScope.m542drawRoundRectZuiqVtQ$default(layoutNodeDrawScope, brush4, 0L, 0L, j4, 0.0f, null, IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode);
                                        } finally {
                                            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$13, jM528getSizeNHjbRc2);
                                        }
                                    } else {
                                        DrawScope.m542drawRoundRectZuiqVtQ$default(layoutNodeDrawScope, brush3, jFloatToRawIntBits, jFloatToRawIntBits2, BorderKt.m30shrinkKibmq7A(f8, j3), 0.0f, stroke2, 208);
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        });
                    }
                    boolean z5 = z2;
                    if (borderModifierNode.borderCache == null) {
                        borderModifierNode.borderCache = new BorderCache(null, null, null, null, 15, null);
                    }
                    BorderCache borderCache = borderModifierNode.borderCache;
                    borderCache.getClass();
                    Path Path = borderCache.borderPath;
                    if (Path == null) {
                        Path = AndroidPath_androidKt.Path();
                        borderCache.borderPath = Path;
                    }
                    final AndroidPath androidPath = (AndroidPath) Path;
                    androidPath.reset();
                    Path.addRoundRect$default(androidPath, roundRect);
                    if (!z5) {
                        Path Path2 = AndroidPath_androidKt.Path();
                        Path.addRoundRect$default(Path2, new RoundRect(fMin, fMin, roundRect.getWidth() - fMin, roundRect.getHeight() - fMin, BorderKt.m30shrinkKibmq7A(fMin, roundRect.topLeftCornerRadius), BorderKt.m30shrinkKibmq7A(fMin, roundRect.topRightCornerRadius), BorderKt.m30shrinkKibmq7A(fMin, roundRect.bottomRightCornerRadius), BorderKt.m30shrinkKibmq7A(fMin, roundRect.bottomLeftCornerRadius), null));
                        PathOperation.Companion.getClass();
                        androidPath.m445opN5in7k0(androidPath, Path2, 0);
                    }
                    return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                            layoutNodeDrawScope.drawContent();
                            DrawScope.m538drawPathGBMwjPU$default(layoutNodeDrawScope, androidPath, brush3, 0.0f, null, 60);
                            return Unit.INSTANCE;
                        }
                    });
                }
                BorderModifierNode borderModifierNode2 = this.this$0;
                final Brush brush4 = borderModifierNode2.brush;
                final Outline.Generic generic = (Outline.Generic) outlineMo41createOutlinePq9zytI;
                if (z2) {
                    return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawGenericBorder$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                            layoutNodeDrawScope.drawContent();
                            DrawScope.m538drawPathGBMwjPU$default(layoutNodeDrawScope, generic.path, brush4, 0.0f, null, 60);
                            return Unit.INSTANCE;
                        }
                    });
                }
                if (brush4 instanceof SolidColor) {
                    ImageBitmapConfig.Companion.getClass();
                    i = ImageBitmapConfig.Alpha8;
                    ColorFilter.Companion companion2 = ColorFilter.Companion;
                    long j4 = ((SolidColor) brush4).value;
                    blendModeColorFilterM465tintxETnrds$default = ColorFilter.Companion.m465tintxETnrds$default(companion2, ColorKt.Color(Color.m463getRedimpl(j4), Color.m462getGreenimpl(j4), Color.m460getBlueimpl(j4), 1.0f, Color.m461getColorSpaceimpl(j4)));
                } else {
                    ImageBitmapConfig.Companion.getClass();
                    i = 0;
                    blendModeColorFilterM465tintxETnrds$default = null;
                }
                final Rect bounds = ((AndroidPath) generic.path).getBounds();
                if (borderModifierNode2.borderCache == null) {
                    borderModifierNode2.borderCache = new BorderCache(null, null, null, null, 15, null);
                }
                BorderCache borderCache2 = borderModifierNode2.borderCache;
                borderCache2.getClass();
                Path Path3 = borderCache2.borderPath;
                if (Path3 == null) {
                    Path3 = AndroidPath_androidKt.Path();
                    borderCache2.borderPath = Path3;
                }
                Path path2 = Path3;
                AndroidPath androidPath2 = (AndroidPath) path2;
                androidPath2.reset();
                Path.addRect$default(path2, bounds);
                PathOperation.Companion.getClass();
                androidPath2.m445opN5in7k0(androidPath2, generic.path, 0);
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                float f8 = bounds.right;
                float f9 = bounds.left;
                float f10 = bounds.bottom;
                float f11 = bounds.top;
                long jCeil = (((int) Math.ceil(f8 - f9)) << 32) | (((int) Math.ceil(f10 - f11)) & 4294967295L);
                IntSize.Companion companion3 = IntSize.Companion;
                BorderCache borderCache3 = borderModifierNode2.borderCache;
                borderCache3.getClass();
                ImageBitmap imageBitmap2 = borderCache3.imageBitmap;
                Canvas Canvas = borderCache3.canvas;
                ImageBitmapConfig imageBitmapConfigM480boximpl = imageBitmap2 != null ? ImageBitmapConfig.m480boximpl(((AndroidImageBitmap) imageBitmap2).m432getConfig_sVssgQ()) : null;
                ImageBitmapConfig.Companion.getClass();
                if (imageBitmapConfigM480boximpl != null && imageBitmapConfigM480boximpl.value == 0) {
                    z = true;
                } else {
                    ImageBitmapConfig imageBitmapConfigM480boximpl2 = imageBitmap2 != null ? ImageBitmapConfig.m480boximpl(((AndroidImageBitmap) imageBitmap2).m432getConfig_sVssgQ()) : null;
                    if (imageBitmapConfigM480boximpl2 == null || i != imageBitmapConfigM480boximpl2.value) {
                        z = false;
                    }
                }
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        if (imageBitmap2 != null && Canvas != null) {
                                            blendModeColorFilter = blendModeColorFilterM465tintxETnrds$default;
                                            AndroidImageBitmap androidImageBitmap = (AndroidImageBitmap) imageBitmap2;
                                            if (Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() >> 32)) <= androidImageBitmap.bitmap.getWidth()) {
                                                path = path2;
                                                j2 = jCeil;
                                                if (Float.intBitsToFloat((int) (cacheDrawScope.cacheParams.mo361getSizeNHjbRc() & 4294967295L)) <= androidImageBitmap.bitmap.getHeight() && z) {
                                                    imageBitmap = imageBitmap2;
                                                }
                                                Canvas canvas = Canvas;
                                                canvasDrawScope = borderCache3.canvasDrawScope;
                                                if (canvasDrawScope == null) {
                                                    canvasDrawScope = new CanvasDrawScope();
                                                    borderCache3.canvasDrawScope = canvasDrawScope;
                                                }
                                                CanvasDrawScope canvasDrawScope2 = canvasDrawScope;
                                                long jM866toSizeozmzZPI = IntSizeKt.m866toSizeozmzZPI(j2);
                                                LayoutDirection layoutDirection = cacheDrawScope.cacheParams.getLayoutDirection();
                                                CanvasDrawScope.DrawParams drawParams = canvasDrawScope2.drawParams;
                                                Density density = drawParams.density;
                                                LayoutDirection layoutDirection2 = drawParams.layoutDirection;
                                                Path path3 = path;
                                                Canvas canvas2 = drawParams.canvas;
                                                T t = imageBitmap;
                                                long j5 = drawParams.size;
                                                drawParams.density = cacheDrawScope;
                                                drawParams.layoutDirection = layoutDirection;
                                                drawParams.canvas = canvas;
                                                drawParams.size = jM866toSizeozmzZPI;
                                                canvas.save();
                                                Color.Companion.getClass();
                                                long j6 = Color.Black;
                                                BlendMode.Companion.getClass();
                                                DrawScope.m541drawRectnJ9OG0$default(canvasDrawScope2, j6, 0L, jM866toSizeozmzZPI, 0.0f, null, null, 0, 58);
                                                f2 = -f9;
                                                f3 = -f11;
                                                canvasDrawScope$drawContext$1 = canvasDrawScope2.drawContext;
                                                canvasDrawScope$drawContext$1.transform.translate(f2, f3);
                                                DrawScope.m538drawPathGBMwjPU$default(canvasDrawScope2, generic.path, brush4, 0.0f, new Stroke(f7, 0.0f, 0, 0, null, 30, null), 52);
                                                float f12 = 1;
                                                float fIntBitsToFloat = (Float.intBitsToFloat((int) (canvasDrawScope2.mo547getSizeNHjbRc() >> 32)) + f12) / Float.intBitsToFloat((int) (canvasDrawScope2.mo547getSizeNHjbRc() >> 32));
                                                float fIntBitsToFloat2 = (Float.intBitsToFloat((int) (canvasDrawScope2.mo547getSizeNHjbRc() & 4294967295L)) + f12) / Float.intBitsToFloat((int) (canvasDrawScope2.mo547getSizeNHjbRc() & 4294967295L));
                                                long jMo546getCenterF1C5BW0 = canvasDrawScope2.mo546getCenterF1C5BW0();
                                                jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                                                canvasDrawScope$drawContext$1.getCanvas().save();
                                                canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$1;
                                                canvasDrawScope$drawContext$12.transform.m532scale0AR0LA0(fIntBitsToFloat, fIntBitsToFloat2, jMo546getCenterF1C5BW0);
                                                DrawScope.m538drawPathGBMwjPU$default(canvasDrawScope2, path3, brush4, 0.0f, null, 28);
                                                canvasDrawScope$drawContext$12.getCanvas().restore();
                                                canvasDrawScope$drawContext$12.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
                                                canvasDrawScope$drawContext$12.transform.translate(-f2, -f3);
                                                canvas.restore();
                                                drawParams.density = density;
                                                drawParams.layoutDirection = layoutDirection2;
                                                drawParams.canvas = canvas2;
                                                drawParams.size = j5;
                                                ((AndroidImageBitmap) t).bitmap.prepareToDraw();
                                                ref$ObjectRef.element = t;
                                                final BlendModeColorFilter blendModeColorFilter2 = blendModeColorFilter;
                                                final long j7 = j2;
                                                return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawGenericBorder$3
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                                                        layoutNodeDrawScope.drawContent();
                                                        Rect rect = bounds;
                                                        float f13 = rect.left;
                                                        Ref$ObjectRef<ImageBitmap> ref$ObjectRef2 = ref$ObjectRef;
                                                        long j8 = j7;
                                                        ColorFilter colorFilter = blendModeColorFilter2;
                                                        CanvasDrawScope canvasDrawScope3 = layoutNodeDrawScope.canvasDrawScope;
                                                        CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = canvasDrawScope3.drawContext.transform;
                                                        float f14 = rect.top;
                                                        canvasDrawScopeKt$asDrawTransform$1.translate(f13, f14);
                                                        try {
                                                            DrawScope.m535drawImageAZ2fEMs$default(layoutNodeDrawScope, ref$ObjectRef2.element, 0L, j8, 0L, 0.0f, colorFilter, 0, 890);
                                                            canvasDrawScope3.drawContext.transform.translate(-f13, -f14);
                                                            return Unit.INSTANCE;
                                                        } catch (Throwable th) {
                                                            canvasDrawScope3.drawContext.transform.translate(-f13, -f14);
                                                            throw th;
                                                        }
                                                    }
                                                });
                                            }
                                            AndroidImageBitmap androidImageBitmapM481ImageBitmapx__hDU$default = ImageBitmapKt.m481ImageBitmapx__hDU$default((int) (j2 >> 32), (int) (j2 & 4294967295L), i);
                                            borderCache3.imageBitmap = androidImageBitmapM481ImageBitmapx__hDU$default;
                                            Canvas = androidx.compose.ui.graphics.CanvasKt.Canvas(androidImageBitmapM481ImageBitmapx__hDU$default);
                                            borderCache3.canvas = Canvas;
                                            imageBitmap = androidImageBitmapM481ImageBitmapx__hDU$default;
                                            Canvas canvas3 = Canvas;
                                            canvasDrawScope = borderCache3.canvasDrawScope;
                                            if (canvasDrawScope == null) {
                                            }
                                            CanvasDrawScope canvasDrawScope22 = canvasDrawScope;
                                            long jM866toSizeozmzZPI2 = IntSizeKt.m866toSizeozmzZPI(j2);
                                            LayoutDirection layoutDirection3 = cacheDrawScope.cacheParams.getLayoutDirection();
                                            CanvasDrawScope.DrawParams drawParams2 = canvasDrawScope22.drawParams;
                                            Density density2 = drawParams2.density;
                                            LayoutDirection layoutDirection22 = drawParams2.layoutDirection;
                                            Path path32 = path;
                                            Canvas canvas22 = drawParams2.canvas;
                                            T t2 = imageBitmap;
                                            long j52 = drawParams2.size;
                                            drawParams2.density = cacheDrawScope;
                                            drawParams2.layoutDirection = layoutDirection3;
                                            drawParams2.canvas = canvas3;
                                            drawParams2.size = jM866toSizeozmzZPI2;
                                            canvas3.save();
                                            Color.Companion.getClass();
                                            long j62 = Color.Black;
                                            BlendMode.Companion.getClass();
                                            DrawScope.m541drawRectnJ9OG0$default(canvasDrawScope22, j62, 0L, jM866toSizeozmzZPI2, 0.0f, null, null, 0, 58);
                                            f2 = -f9;
                                            f3 = -f11;
                                            canvasDrawScope$drawContext$1 = canvasDrawScope22.drawContext;
                                            canvasDrawScope$drawContext$1.transform.translate(f2, f3);
                                            DrawScope.m538drawPathGBMwjPU$default(canvasDrawScope22, generic.path, brush4, 0.0f, new Stroke(f7, 0.0f, 0, 0, null, 30, null), 52);
                                            float f122 = 1;
                                            float fIntBitsToFloat3 = (Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() >> 32)) + f122) / Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() >> 32));
                                            float fIntBitsToFloat22 = (Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() & 4294967295L)) + f122) / Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() & 4294967295L));
                                            long jMo546getCenterF1C5BW02 = canvasDrawScope22.mo546getCenterF1C5BW0();
                                            jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                                            canvasDrawScope$drawContext$1.getCanvas().save();
                                            canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$1;
                                            canvasDrawScope$drawContext$12.transform.m532scale0AR0LA0(fIntBitsToFloat3, fIntBitsToFloat22, jMo546getCenterF1C5BW02);
                                            DrawScope.m538drawPathGBMwjPU$default(canvasDrawScope22, path32, brush4, 0.0f, null, 28);
                                            canvasDrawScope$drawContext$12.getCanvas().restore();
                                            canvasDrawScope$drawContext$12.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
                                            canvasDrawScope$drawContext$12.transform.translate(-f2, -f3);
                                            canvas3.restore();
                                            drawParams2.density = density2;
                                            drawParams2.layoutDirection = layoutDirection22;
                                            drawParams2.canvas = canvas22;
                                            drawParams2.size = j52;
                                            ((AndroidImageBitmap) t2).bitmap.prepareToDraw();
                                            ref$ObjectRef.element = t2;
                                            final ColorFilter blendModeColorFilter22 = blendModeColorFilter;
                                            final long j72 = j2;
                                            return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawGenericBorder$3
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                                                    layoutNodeDrawScope.drawContent();
                                                    Rect rect = bounds;
                                                    float f13 = rect.left;
                                                    Ref$ObjectRef<ImageBitmap> ref$ObjectRef2 = ref$ObjectRef;
                                                    long j8 = j72;
                                                    ColorFilter colorFilter = blendModeColorFilter22;
                                                    CanvasDrawScope canvasDrawScope3 = layoutNodeDrawScope.canvasDrawScope;
                                                    CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = canvasDrawScope3.drawContext.transform;
                                                    float f14 = rect.top;
                                                    canvasDrawScopeKt$asDrawTransform$1.translate(f13, f14);
                                                    try {
                                                        DrawScope.m535drawImageAZ2fEMs$default(layoutNodeDrawScope, ref$ObjectRef2.element, 0L, j8, 0L, 0.0f, colorFilter, 0, 890);
                                                        canvasDrawScope3.drawContext.transform.translate(-f13, -f14);
                                                        return Unit.INSTANCE;
                                                    } catch (Throwable th) {
                                                        canvasDrawScope3.drawContext.transform.translate(-f13, -f14);
                                                        throw th;
                                                    }
                                                }
                                            });
                                        }
                                        blendModeColorFilter = blendModeColorFilterM465tintxETnrds$default;
                                        canvasDrawScope$drawContext$12.getCanvas().restore();
                                        canvasDrawScope$drawContext$12.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
                                        canvasDrawScope$drawContext$12.transform.translate(-f2, -f3);
                                        canvas3.restore();
                                        drawParams2.density = density2;
                                        drawParams2.layoutDirection = layoutDirection22;
                                        drawParams2.canvas = canvas22;
                                        drawParams2.size = j52;
                                        ((AndroidImageBitmap) t2).bitmap.prepareToDraw();
                                        ref$ObjectRef.element = t2;
                                        final ColorFilter blendModeColorFilter222 = blendModeColorFilter;
                                        final long j722 = j2;
                                        return cacheDrawScope.onDrawWithContent(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawGenericBorder$3
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj2) {
                                                LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) ((ContentDrawScope) obj2);
                                                layoutNodeDrawScope.drawContent();
                                                Rect rect = bounds;
                                                float f13 = rect.left;
                                                Ref$ObjectRef<ImageBitmap> ref$ObjectRef2 = ref$ObjectRef;
                                                long j8 = j722;
                                                ColorFilter colorFilter = blendModeColorFilter222;
                                                CanvasDrawScope canvasDrawScope3 = layoutNodeDrawScope.canvasDrawScope;
                                                CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = canvasDrawScope3.drawContext.transform;
                                                float f14 = rect.top;
                                                canvasDrawScopeKt$asDrawTransform$1.translate(f13, f14);
                                                try {
                                                    DrawScope.m535drawImageAZ2fEMs$default(layoutNodeDrawScope, ref$ObjectRef2.element, 0L, j8, 0L, 0.0f, colorFilter, 0, 890);
                                                    canvasDrawScope3.drawContext.transform.translate(-f13, -f14);
                                                    return Unit.INSTANCE;
                                                } catch (Throwable th) {
                                                    canvasDrawScope3.drawContext.transform.translate(-f13, -f14);
                                                    throw th;
                                                }
                                            }
                                        });
                                    } catch (Throwable th) {
                                        th = th;
                                        canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$12;
                                        canvasDrawScope$drawContext$12.transform.translate(-f2, -f3);
                                        throw th;
                                    }
                                    DrawScope.m538drawPathGBMwjPU$default(canvasDrawScope22, path32, brush4, 0.0f, null, 28);
                                } catch (Throwable th2) {
                                    th = th2;
                                    canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$12;
                                    canvasDrawScope$drawContext$12.getCanvas().restore();
                                    canvasDrawScope$drawContext$12.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
                                    throw th;
                                }
                                canvasDrawScope$drawContext$12.transform.m532scale0AR0LA0(fIntBitsToFloat3, fIntBitsToFloat22, jMo546getCenterF1C5BW02);
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                        }
                        DrawScope.m538drawPathGBMwjPU$default(canvasDrawScope22, generic.path, brush4, 0.0f, new Stroke(f7, 0.0f, 0, 0, null, 30, null), 52);
                        float f1222 = 1;
                        float fIntBitsToFloat32 = (Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() >> 32)) + f1222) / Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() >> 32));
                        float fIntBitsToFloat222 = (Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() & 4294967295L)) + f1222) / Float.intBitsToFloat((int) (canvasDrawScope22.mo547getSizeNHjbRc() & 4294967295L));
                        long jMo546getCenterF1C5BW022 = canvasDrawScope22.mo546getCenterF1C5BW0();
                        jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
                        canvasDrawScope$drawContext$1.getCanvas().save();
                        canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$1;
                    } catch (Throwable th5) {
                        th = th5;
                        canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$1;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    canvasDrawScope$drawContext$12 = canvasDrawScope$drawContext$1;
                }
                path = path2;
                j2 = jCeil;
                AndroidImageBitmap androidImageBitmapM481ImageBitmapx__hDU$default2 = ImageBitmapKt.m481ImageBitmapx__hDU$default((int) (j2 >> 32), (int) (j2 & 4294967295L), i);
                borderCache3.imageBitmap = androidImageBitmapM481ImageBitmapx__hDU$default2;
                Canvas = androidx.compose.ui.graphics.CanvasKt.Canvas(androidImageBitmapM481ImageBitmapx__hDU$default2);
                borderCache3.canvas = Canvas;
                imageBitmap = androidImageBitmapM481ImageBitmapx__hDU$default2;
                Canvas canvas32 = Canvas;
                canvasDrawScope = borderCache3.canvasDrawScope;
                if (canvasDrawScope == null) {
                }
                CanvasDrawScope canvasDrawScope222 = canvasDrawScope;
                long jM866toSizeozmzZPI22 = IntSizeKt.m866toSizeozmzZPI(j2);
                LayoutDirection layoutDirection32 = cacheDrawScope.cacheParams.getLayoutDirection();
                CanvasDrawScope.DrawParams drawParams22 = canvasDrawScope222.drawParams;
                Density density22 = drawParams22.density;
                LayoutDirection layoutDirection222 = drawParams22.layoutDirection;
                Path path322 = path;
                Canvas canvas222 = drawParams22.canvas;
                T t22 = imageBitmap;
                long j522 = drawParams22.size;
                drawParams22.density = cacheDrawScope;
                drawParams22.layoutDirection = layoutDirection32;
                drawParams22.canvas = canvas32;
                drawParams22.size = jM866toSizeozmzZPI22;
                canvas32.save();
                Color.Companion.getClass();
                long j622 = Color.Black;
                BlendMode.Companion.getClass();
                DrawScope.m541drawRectnJ9OG0$default(canvasDrawScope222, j622, 0L, jM866toSizeozmzZPI22, 0.0f, null, null, 0, 58);
                f2 = -f9;
                f3 = -f11;
                canvasDrawScope$drawContext$1 = canvasDrawScope222.drawContext;
                canvasDrawScope$drawContext$1.transform.translate(f2, f3);
            }
        });
        delegate(CacheDrawModifierNode);
        this.drawWithCacheModifierNode = CacheDrawModifierNode;
    }
}
