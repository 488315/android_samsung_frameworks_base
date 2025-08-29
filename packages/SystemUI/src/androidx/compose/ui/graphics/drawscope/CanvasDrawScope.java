package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.FilterQuality;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathEffect;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class CanvasDrawScope implements DrawScope {
    public AndroidPaint fillPaint;
    public AndroidPaint strokePaint;
    public final DrawParams drawParams = new DrawParams(null, null, null, 0, 15, null);
    public final CanvasDrawScope$drawContext$1 drawContext = new CanvasDrawScope$drawContext$1(this);

    public final class DrawParams {
        public Canvas canvas;
        public Density density;
        public LayoutDirection layoutDirection;
        public long size;

        public /* synthetic */ DrawParams(Density density, LayoutDirection layoutDirection, Canvas canvas, long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(density, layoutDirection, canvas, j);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrawParams)) {
                return false;
            }
            DrawParams drawParams = (DrawParams) obj;
            return Intrinsics.areEqual(this.density, drawParams.density) && this.layoutDirection == drawParams.layoutDirection && Intrinsics.areEqual(this.canvas, drawParams.canvas) && Size.m416equalsimpl0(this.size, drawParams.size);
        }

        public final int hashCode() {
            int iHashCode = (this.canvas.hashCode() + ((this.layoutDirection.hashCode() + (this.density.hashCode() * 31)) * 31)) * 31;
            long j = this.size;
            Size.Companion companion = Size.Companion;
            return Long.hashCode(j) + iHashCode;
        }

        public final String toString() {
            return "DrawParams(density=" + this.density + ", layoutDirection=" + this.layoutDirection + ", canvas=" + this.canvas + ", size=" + ((Object) Size.m421toStringimpl(this.size)) + ')';
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public DrawParams(Density density, LayoutDirection layoutDirection, Canvas canvas, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
            Density density2 = (i & 1) != 0 ? DrawContextKt.DefaultDensity : density;
            LayoutDirection layoutDirection2 = (i & 2) != 0 ? LayoutDirection.Ltr : layoutDirection;
            Canvas canvas2 = (i & 4) != 0 ? EmptyCanvas.INSTANCE : canvas;
            if ((i & 8) != 0) {
                Size.Companion.getClass();
                j = 0;
            }
            this(density2, layoutDirection2, canvas2, j, null);
        }

        private DrawParams(Density density, LayoutDirection layoutDirection, Canvas canvas, long j) {
            this.density = density;
            this.layoutDirection = layoutDirection;
            this.canvas = canvas;
            this.size = j;
        }
    }

    /* renamed from: configurePaint-2qPWKa0$default, reason: not valid java name */
    public static Paint m515configurePaint2qPWKa0$default(CanvasDrawScope canvasDrawScope, long j, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i) {
        DrawScope.Companion.getClass();
        int i2 = DrawScope.Companion.DefaultFilterQuality;
        Paint paintSelectPaint = canvasDrawScope.selectPaint(drawStyle);
        if (f != 1.0f) {
            j = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), Color.m459getAlphaimpl(j) * f, Color.m461getColorSpaceimpl(j));
        }
        AndroidPaint androidPaint = (AndroidPaint) paintSelectPaint;
        long jColor = ColorKt.Color(androidPaint.internalPaint.getColor());
        Color.Companion companion = Color.Companion;
        if (!ULong.m3446equalsimpl0(jColor, j)) {
            androidPaint.m440setColor8_81llA(j);
        }
        if (androidPaint.internalShader != null) {
            androidPaint.setShader(null);
        }
        if (!Intrinsics.areEqual(androidPaint.internalColorFilter, colorFilter)) {
            androidPaint.setColorFilter(colorFilter);
        }
        int i3 = androidPaint._blendMode;
        BlendMode.Companion companion2 = BlendMode.Companion;
        if (i3 != i) {
            androidPaint.m439setBlendModes9anfk8(i);
        }
        int iM436getFilterQualityfv9h1I = androidPaint.m436getFilterQualityfv9h1I();
        FilterQuality.Companion companion3 = FilterQuality.Companion;
        if (iM436getFilterQualityfv9h1I == i2) {
            return paintSelectPaint;
        }
        androidPaint.m441setFilterQualityvDHp3xo(i2);
        return paintSelectPaint;
    }

    /* renamed from: configurePaint-swdJneE$default, reason: not valid java name */
    public static Paint m516configurePaintswdJneE$default(CanvasDrawScope canvasDrawScope, Brush brush, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i) {
        DrawScope.Companion.getClass();
        return canvasDrawScope.m517configurePaintswdJneE(brush, drawStyle, f, colorFilter, i, DrawScope.Companion.DefaultFilterQuality);
    }

    /* renamed from: configurePaint-swdJneE, reason: not valid java name */
    public final Paint m517configurePaintswdJneE(Brush brush, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i, int i2) {
        Paint paintSelectPaint = selectPaint(drawStyle);
        if (brush != null) {
            brush.mo451applyToPq9zytI(f, mo547getSizeNHjbRc(), paintSelectPaint);
        } else {
            AndroidPaint androidPaint = (AndroidPaint) paintSelectPaint;
            if (androidPaint.internalShader != null) {
                androidPaint.setShader(null);
            }
            long jColor = ColorKt.Color(androidPaint.internalPaint.getColor());
            Color.Companion.getClass();
            long j = Color.Black;
            if (!ULong.m3446equalsimpl0(jColor, j)) {
                androidPaint.m440setColor8_81llA(j);
            }
            if (androidPaint.internalPaint.getAlpha() / 255.0f != f) {
                androidPaint.setAlpha(f);
            }
        }
        AndroidPaint androidPaint2 = (AndroidPaint) paintSelectPaint;
        if (!Intrinsics.areEqual(androidPaint2.internalColorFilter, colorFilter)) {
            androidPaint2.setColorFilter(colorFilter);
        }
        int i3 = androidPaint2._blendMode;
        BlendMode.Companion companion = BlendMode.Companion;
        if (i3 != i) {
            androidPaint2.m439setBlendModes9anfk8(i);
        }
        int iM436getFilterQualityfv9h1I = androidPaint2.m436getFilterQualityfv9h1I();
        FilterQuality.Companion companion2 = FilterQuality.Companion;
        if (iM436getFilterQualityfv9h1I == i2) {
            return paintSelectPaint;
        }
        androidPaint2.m441setFilterQualityvDHp3xo(i2);
        return paintSelectPaint;
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawArc-yD3GUKo, reason: not valid java name */
    public final void mo518drawArcyD3GUKo(long j, float f, float f2, long j2, long j3, DrawStyle drawStyle, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.drawParams.canvas.drawArc(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i3), f, f2, m515configurePaint2qPWKa0$default(this, j, drawStyle, 1.0f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-V9BoPsw, reason: not valid java name */
    public final void mo519drawCircleV9BoPsw(Brush brush, float f, long j, float f2, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.mo428drawCircle9KIMszo(f, j, m516configurePaintswdJneE$default(this, brush, drawStyle, f2, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-VaOC9Bg, reason: not valid java name */
    public final void mo520drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.mo428drawCircle9KIMszo(f, j2, m515configurePaint2qPWKa0$default(this, j, drawStyle, f2, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawImage-AZ2fEMs, reason: not valid java name */
    public final void mo521drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2) {
        this.drawParams.canvas.mo430drawImageRectHPBpro0(imageBitmap, j, j2, j3, m517configurePaintswdJneE(null, drawStyle, f, colorFilter, i, i2));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawLine-NGM6Ib0, reason: not valid java name */
    public final void mo522drawLineNGM6Ib0(long j, long j2, long j3, float f, int i, float f2, int i2) {
        Canvas canvas = this.drawParams.canvas;
        StrokeJoin.Companion.getClass();
        DrawScope.Companion.getClass();
        int i3 = DrawScope.Companion.DefaultFilterQuality;
        Paint paintObtainStrokePaint = obtainStrokePaint();
        if (f2 != 1.0f) {
            j = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), Color.m459getAlphaimpl(j) * f2, Color.m461getColorSpaceimpl(j));
        }
        AndroidPaint androidPaint = (AndroidPaint) paintObtainStrokePaint;
        long jColor = ColorKt.Color(androidPaint.internalPaint.getColor());
        Color.Companion companion = Color.Companion;
        if (!ULong.m3446equalsimpl0(jColor, j)) {
            androidPaint.m440setColor8_81llA(j);
        }
        if (androidPaint.internalShader != null) {
            androidPaint.setShader(null);
        }
        if (!Intrinsics.areEqual(androidPaint.internalColorFilter, (Object) null)) {
            androidPaint.setColorFilter(null);
        }
        int i4 = androidPaint._blendMode;
        BlendMode.Companion companion2 = BlendMode.Companion;
        if (i4 != i2) {
            androidPaint.m439setBlendModes9anfk8(i2);
        }
        if (androidPaint.internalPaint.getStrokeWidth() != f) {
            androidPaint.setStrokeWidth(f);
        }
        if (androidPaint.internalPaint.getStrokeMiter() != 4.0f) {
            androidPaint.internalPaint.setStrokeMiter(4.0f);
        }
        int iM437getStrokeCapKaPHkGw = androidPaint.m437getStrokeCapKaPHkGw();
        StrokeCap.Companion companion3 = StrokeCap.Companion;
        if (iM437getStrokeCapKaPHkGw != i) {
            androidPaint.m442setStrokeCapBeK7IIE(i);
        }
        if (androidPaint.m438getStrokeJoinLxFBmk8() != 0) {
            androidPaint.m443setStrokeJoinWw9F2mQ(0);
        }
        if (!Intrinsics.areEqual(androidPaint.pathEffect, (Object) null)) {
            androidPaint.setPathEffect(null);
        }
        int iM436getFilterQualityfv9h1I = androidPaint.m436getFilterQualityfv9h1I();
        FilterQuality.Companion companion4 = FilterQuality.Companion;
        if (iM436getFilterQualityfv9h1I != i3) {
            androidPaint.m441setFilterQualityvDHp3xo(i3);
        }
        canvas.mo431drawLineWko1d7g(j2, j3, paintObtainStrokePaint);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-GBMwjPU, reason: not valid java name */
    public final void mo523drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.drawPath(path, m516configurePaintswdJneE$default(this, brush, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-LG529CI, reason: not valid java name */
    public final void mo524drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.drawPath(path, m515configurePaint2qPWKa0$default(this, j, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRect-n-J9OG0, reason: not valid java name */
    public final void mo525drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.drawParams.canvas.drawRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i3), m515configurePaint2qPWKa0$default(this, j, drawStyle, f, colorFilter, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-ZuiqVtQ, reason: not valid java name */
    public final void mo526drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, int i) {
        int i2 = (int) (j >> 32);
        int i3 = (int) (j & 4294967295L);
        this.drawParams.canvas.drawRoundRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j2 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j2 & 4294967295L)) + Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)), Float.intBitsToFloat((int) (j3 & 4294967295L)), m516configurePaintswdJneE$default(this, brush, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-u-Aw5IA, reason: not valid java name */
    public final void mo527drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.drawParams.canvas.drawRoundRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j4 >> 32)), Float.intBitsToFloat((int) (j4 & 4294967295L)), m515configurePaint2qPWKa0$default(this, j, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.drawParams.density.getDensity();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    public final CanvasDrawScope$drawContext$1 getDrawContext() {
        return this.drawContext;
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.drawParams.density.getFontScale();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    public final LayoutDirection getLayoutDirection() {
        return this.drawParams.layoutDirection;
    }

    public final Paint obtainStrokePaint() {
        AndroidPaint androidPaint = this.strokePaint;
        if (androidPaint != null) {
            return androidPaint;
        }
        AndroidPaint androidPaint2 = new AndroidPaint();
        PaintingStyle.Companion.getClass();
        androidPaint2.m444setStylek9PVt8s(PaintingStyle.Stroke);
        this.strokePaint = androidPaint2;
        return androidPaint2;
    }

    public final Paint selectPaint(DrawStyle drawStyle) {
        if (Intrinsics.areEqual(drawStyle, Fill.INSTANCE)) {
            AndroidPaint androidPaint = this.fillPaint;
            if (androidPaint != null) {
                return androidPaint;
            }
            AndroidPaint androidPaint2 = new AndroidPaint();
            PaintingStyle.Companion.getClass();
            androidPaint2.m444setStylek9PVt8s(0);
            this.fillPaint = androidPaint2;
            return androidPaint2;
        }
        if (!(drawStyle instanceof Stroke)) {
            throw new NoWhenBranchMatchedException();
        }
        Paint paintObtainStrokePaint = obtainStrokePaint();
        AndroidPaint androidPaint3 = (AndroidPaint) paintObtainStrokePaint;
        float strokeWidth = androidPaint3.internalPaint.getStrokeWidth();
        Stroke stroke = (Stroke) drawStyle;
        float f = stroke.width;
        if (strokeWidth != f) {
            androidPaint3.setStrokeWidth(f);
        }
        int iM437getStrokeCapKaPHkGw = androidPaint3.m437getStrokeCapKaPHkGw();
        int i = stroke.cap;
        if (iM437getStrokeCapKaPHkGw != i) {
            androidPaint3.m442setStrokeCapBeK7IIE(i);
        }
        float strokeMiter = androidPaint3.internalPaint.getStrokeMiter();
        float f2 = stroke.miter;
        if (strokeMiter != f2) {
            androidPaint3.internalPaint.setStrokeMiter(f2);
        }
        int iM438getStrokeJoinLxFBmk8 = androidPaint3.m438getStrokeJoinLxFBmk8();
        int i2 = stroke.join;
        if (iM438getStrokeJoinLxFBmk8 != i2) {
            androidPaint3.m443setStrokeJoinWw9F2mQ(i2);
        }
        PathEffect pathEffect = androidPaint3.pathEffect;
        PathEffect pathEffect2 = stroke.pathEffect;
        if (!Intrinsics.areEqual(pathEffect, pathEffect2)) {
            androidPaint3.setPathEffect(pathEffect2);
        }
        return paintObtainStrokePaint;
    }
}
