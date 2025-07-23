package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.FilterQuality;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface DrawScope extends Density {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int DefaultBlendMode;
        public static final int DefaultFilterQuality;

        static {
            BlendMode.Companion.getClass();
            DefaultBlendMode = BlendMode.SrcOver;
            FilterQuality.Companion.getClass();
            DefaultFilterQuality = FilterQuality.Low;
        }

        private Companion() {
        }
    }

    /* renamed from: drawCircle-V9BoPsw$default, reason: not valid java name */
    static void m531drawCircleV9BoPsw$default(DrawScope drawScope, Brush brush, float f, long j, float f2, int i, int i2) {
        if ((i2 & 8) != 0) {
            f2 = 1.0f;
        }
        float f3 = f2;
        Fill fill = Fill.INSTANCE;
        if ((i2 & 64) != 0) {
            Companion.getClass();
            i = Companion.DefaultBlendMode;
        }
        drawScope.mo517drawCircleV9BoPsw(brush, f, j, f3, fill, i);
    }

    /* renamed from: drawCircle-VaOC9Bg$default, reason: not valid java name */
    static void m532drawCircleVaOC9Bg$default(DrawScope drawScope, long j, float f, long j2, float f2, DrawStyle drawStyle, int i, int i2) {
        int i3;
        if ((i2 & 2) != 0) {
            f = Size.m416getMinDimensionimpl(drawScope.mo545getSizeNHjbRc()) / 2.0f;
        }
        float f3 = f;
        if ((i2 & 4) != 0) {
            j2 = drawScope.mo544getCenterF1C5BW0();
        }
        long j3 = j2;
        float f4 = (i2 & 8) != 0 ? 1.0f : f2;
        DrawStyle drawStyle2 = (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle;
        if ((i2 & 64) != 0) {
            Companion.getClass();
            i3 = Companion.DefaultBlendMode;
        } else {
            i3 = i;
        }
        drawScope.mo518drawCircleVaOC9Bg(j, f3, j3, f4, drawStyle2, i3);
    }

    /* renamed from: drawImage-AZ2fEMs$default, reason: not valid java name */
    static void m533drawImageAZ2fEMs$default(DrawScope drawScope, ImageBitmap imageBitmap, long j, long j2, long j3, float f, ColorFilter colorFilter, int i, int i2) {
        long j4;
        int i3;
        if ((i2 & 2) != 0) {
            IntOffset.Companion.getClass();
            j4 = 0;
        } else {
            j4 = j;
        }
        IntOffset.Companion.getClass();
        long j5 = (i2 & 16) != 0 ? j2 : j3;
        float f2 = (i2 & 32) != 0 ? 1.0f : f;
        Fill fill = Fill.INSTANCE;
        Companion companion = Companion;
        companion.getClass();
        int i4 = Companion.DefaultBlendMode;
        if ((i2 & 512) != 0) {
            companion.getClass();
            i3 = Companion.DefaultFilterQuality;
        } else {
            i3 = i;
        }
        drawScope.mo519drawImageAZ2fEMs(imageBitmap, j4, j2, j5, f2, fill, colorFilter, i4, i3);
    }

    /* renamed from: drawImage-gbVJVH8$default, reason: not valid java name */
    static void m534drawImagegbVJVH8$default(LayoutNodeDrawScope layoutNodeDrawScope, ImageBitmap imageBitmap, ColorFilter colorFilter) {
        Offset.Companion.getClass();
        Fill fill = Fill.INSTANCE;
        Companion.getClass();
        int i = Companion.DefaultBlendMode;
        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
        canvasDrawScope.drawParams.canvas.mo427drawImaged4ec7I(imageBitmap, CanvasDrawScope.m514configurePaintswdJneE$default(canvasDrawScope, null, fill, 1.0f, colorFilter, i));
    }

    /* renamed from: drawLine-NGM6Ib0$default, reason: not valid java name */
    static void m535drawLineNGM6Ib0$default(DrawScope drawScope, long j, long j2, long j3, float f, int i, float f2, int i2) {
        int i3;
        if ((i2 & 16) != 0) {
            Stroke.Companion.getClass();
            i3 = 0;
        } else {
            i3 = i;
        }
        float f3 = (i2 & 64) != 0 ? 1.0f : f2;
        Companion.getClass();
        drawScope.mo520drawLineNGM6Ib0(j, j2, j3, f, i3, f3, Companion.DefaultBlendMode);
    }

    /* renamed from: drawPath-GBMwjPU$default, reason: not valid java name */
    static void m536drawPathGBMwjPU$default(DrawScope drawScope, Path path, Brush brush, float f, Stroke stroke, int i) {
        int i2;
        if ((i & 4) != 0) {
            f = 1.0f;
        }
        float f2 = f;
        DrawStyle drawStyle = stroke;
        if ((i & 8) != 0) {
            drawStyle = Fill.INSTANCE;
        }
        DrawStyle drawStyle2 = drawStyle;
        if ((i & 32) != 0) {
            Companion.getClass();
            i2 = Companion.DefaultBlendMode;
        } else {
            i2 = 0;
        }
        drawScope.mo521drawPathGBMwjPU(path, brush, f2, drawStyle2, i2);
    }

    /* renamed from: drawPath-LG529CI$default, reason: not valid java name */
    static void m537drawPathLG529CI$default(DrawScope drawScope, Path path, long j, int i) {
        Fill fill = Fill.INSTANCE;
        Companion.getClass();
        drawScope.mo522drawPathLG529CI(path, j, 1.0f, fill, Companion.DefaultBlendMode);
    }

    /* renamed from: drawRect-AsUm42w$default, reason: not valid java name */
    static void m538drawRectAsUm42w$default(ContentDrawScope contentDrawScope, Brush brush, long j, long j2, float f, DrawStyle drawStyle, int i, int i2) {
        int i3;
        if ((i2 & 2) != 0) {
            Offset.Companion.getClass();
            j = 0;
        }
        long j3 = j;
        long m542offsetSizePENXr5M = (i2 & 4) != 0 ? m542offsetSizePENXr5M(((LayoutNodeDrawScope) contentDrawScope).canvasDrawScope.mo545getSizeNHjbRc(), j3) : j2;
        float f2 = (i2 & 8) != 0 ? 1.0f : f;
        DrawStyle drawStyle2 = (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle;
        if ((i2 & 64) != 0) {
            Companion.getClass();
            i3 = Companion.DefaultBlendMode;
        } else {
            i3 = i;
        }
        ((LayoutNodeDrawScope) contentDrawScope).m645drawRectAsUm42w(brush, j3, m542offsetSizePENXr5M, f2, drawStyle2, i3);
    }

    /* renamed from: drawRect-n-J9OG0$default, reason: not valid java name */
    static void m539drawRectnJ9OG0$default(DrawScope drawScope, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2) {
        long j4;
        int i3;
        if ((i2 & 2) != 0) {
            Offset.Companion.getClass();
            j4 = 0;
        } else {
            j4 = j2;
        }
        long m542offsetSizePENXr5M = (i2 & 4) != 0 ? m542offsetSizePENXr5M(drawScope.mo545getSizeNHjbRc(), j4) : j3;
        float f2 = (i2 & 8) != 0 ? 1.0f : f;
        DrawStyle drawStyle2 = (i2 & 16) != 0 ? Fill.INSTANCE : drawStyle;
        ColorFilter colorFilter2 = (i2 & 32) != 0 ? null : colorFilter;
        if ((i2 & 64) != 0) {
            Companion.getClass();
            i3 = Companion.DefaultBlendMode;
        } else {
            i3 = i;
        }
        drawScope.mo523drawRectnJ9OG0(j, j4, m542offsetSizePENXr5M, f2, drawStyle2, colorFilter2, i3);
    }

    /* renamed from: drawRoundRect-ZuiqVtQ$default, reason: not valid java name */
    static void m540drawRoundRectZuiqVtQ$default(DrawScope drawScope, Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, int i) {
        long j4;
        if ((i & 2) != 0) {
            Offset.Companion.getClass();
            j4 = 0;
        } else {
            j4 = j;
        }
        long m542offsetSizePENXr5M = (i & 4) != 0 ? m542offsetSizePENXr5M(drawScope.mo545getSizeNHjbRc(), j4) : j2;
        float f2 = (i & 16) != 0 ? 1.0f : f;
        DrawStyle drawStyle2 = (i & 32) != 0 ? Fill.INSTANCE : drawStyle;
        Companion.getClass();
        drawScope.mo524drawRoundRectZuiqVtQ(brush, j4, m542offsetSizePENXr5M, j3, f2, drawStyle2, Companion.DefaultBlendMode);
    }

    /* renamed from: drawRoundRect-u-Aw5IA$default, reason: not valid java name */
    static void m541drawRoundRectuAw5IA$default(DrawScope drawScope, long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, int i) {
        long j5;
        if ((i & 2) != 0) {
            Offset.Companion.getClass();
            j5 = 0;
        } else {
            j5 = j2;
        }
        long m542offsetSizePENXr5M = (i & 4) != 0 ? m542offsetSizePENXr5M(drawScope.mo545getSizeNHjbRc(), j5) : j3;
        DrawStyle drawStyle2 = (i & 16) != 0 ? Fill.INSTANCE : drawStyle;
        float f2 = (i & 32) != 0 ? 1.0f : f;
        Companion.getClass();
        drawScope.mo525drawRoundRectuAw5IA(j, j5, m542offsetSizePENXr5M, j4, drawStyle2, f2, Companion.DefaultBlendMode);
    }

    /* renamed from: offsetSize-PENXr5M, reason: not valid java name */
    static long m542offsetSizePENXr5M(long j, long j2) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - Float.intBitsToFloat((int) (j2 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - Float.intBitsToFloat((int) (j2 & 4294967295L));
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: drawArc-yD3GUKo */
    void mo516drawArcyD3GUKo(long j, float f, float f2, long j2, long j3, DrawStyle drawStyle, int i);

    /* renamed from: drawCircle-V9BoPsw */
    void mo517drawCircleV9BoPsw(Brush brush, float f, long j, float f2, DrawStyle drawStyle, int i);

    /* renamed from: drawCircle-VaOC9Bg */
    void mo518drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, int i);

    /* renamed from: drawImage-AZ2fEMs */
    void mo519drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2);

    /* renamed from: drawLine-NGM6Ib0 */
    void mo520drawLineNGM6Ib0(long j, long j2, long j3, float f, int i, float f2, int i2);

    /* renamed from: drawPath-GBMwjPU */
    void mo521drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, int i);

    /* renamed from: drawPath-LG529CI */
    void mo522drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, int i);

    /* renamed from: drawRect-n-J9OG0 */
    void mo523drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i);

    /* renamed from: drawRoundRect-ZuiqVtQ */
    void mo524drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, int i);

    /* renamed from: drawRoundRect-u-Aw5IA */
    void mo525drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, int i);

    /* renamed from: getCenter-F1C5BW0, reason: not valid java name */
    default long mo544getCenterF1C5BW0() {
        return SizeKt.m420getCenteruvyYCjk(getDrawContext().m526getSizeNHjbRc());
    }

    CanvasDrawScope$drawContext$1 getDrawContext();

    LayoutDirection getLayoutDirection();

    /* renamed from: getSize-NH-jbRc, reason: not valid java name */
    default long mo545getSizeNHjbRc() {
        return getDrawContext().m526getSizeNHjbRc();
    }
}
