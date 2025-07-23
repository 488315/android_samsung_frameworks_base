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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CanvasDrawScope implements DrawScope {
    public AndroidPaint fillPaint;
    public AndroidPaint strokePaint;
    public final DrawParams drawParams = new DrawParams(null, null, null, 0, 15, null);
    public final CanvasDrawScope$drawContext$1 drawContext = new CanvasDrawScope$drawContext$1(this);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return Intrinsics.areEqual(this.density, drawParams.density) && this.layoutDirection == drawParams.layoutDirection && Intrinsics.areEqual(this.canvas, drawParams.canvas) && Size.m414equalsimpl0(this.size, drawParams.size);
        }

        public final int hashCode() {
            int hashCode = (this.canvas.hashCode() + ((this.layoutDirection.hashCode() + (this.density.hashCode() * 31)) * 31)) * 31;
            long j = this.size;
            Size.Companion companion = Size.Companion;
            return Long.hashCode(j) + hashCode;
        }

        public final String toString() {
            return "DrawParams(density=" + this.density + ", layoutDirection=" + this.layoutDirection + ", canvas=" + this.canvas + ", size=" + ((Object) Size.m419toStringimpl(this.size)) + ')';
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public DrawParams(androidx.compose.ui.unit.Density r8, androidx.compose.ui.unit.LayoutDirection r9, androidx.compose.ui.graphics.Canvas r10, long r11, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
            /*
                r7 = this;
                r14 = r13 & 1
                if (r14 == 0) goto L6
                androidx.compose.ui.unit.Density r8 = androidx.compose.ui.graphics.drawscope.DrawContextKt.DefaultDensity
            L6:
                r1 = r8
                r8 = r13 & 2
                if (r8 == 0) goto Ld
                androidx.compose.ui.unit.LayoutDirection r9 = androidx.compose.ui.unit.LayoutDirection.Ltr
            Ld:
                r2 = r9
                r8 = r13 & 4
                if (r8 == 0) goto L14
                androidx.compose.ui.graphics.drawscope.EmptyCanvas r10 = androidx.compose.ui.graphics.drawscope.EmptyCanvas.INSTANCE
            L14:
                r3 = r10
                r8 = r13 & 8
                if (r8 == 0) goto L20
                androidx.compose.ui.geometry.Size$Companion r8 = androidx.compose.ui.geometry.Size.Companion
                r8.getClass()
                r11 = 0
            L20:
                r4 = r11
                r6 = 0
                r0 = r7
                r0.<init>(r1, r2, r3, r4, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.drawscope.CanvasDrawScope.DrawParams.<init>(androidx.compose.ui.unit.Density, androidx.compose.ui.unit.LayoutDirection, androidx.compose.ui.graphics.Canvas, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        private DrawParams(Density density, LayoutDirection layoutDirection, Canvas canvas, long j) {
            this.density = density;
            this.layoutDirection = layoutDirection;
            this.canvas = canvas;
            this.size = j;
        }
    }

    /* renamed from: configurePaint-2qPWKa0$default, reason: not valid java name */
    public static Paint m513configurePaint2qPWKa0$default(CanvasDrawScope canvasDrawScope, long j, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i) {
        DrawScope.Companion.getClass();
        int i2 = DrawScope.Companion.DefaultFilterQuality;
        Paint selectPaint = canvasDrawScope.selectPaint(drawStyle);
        if (f != 1.0f) {
            j = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), Color.m457getAlphaimpl(j) * f, Color.m459getColorSpaceimpl(j));
        }
        AndroidPaint androidPaint = (AndroidPaint) selectPaint;
        long Color = ColorKt.Color(androidPaint.internalPaint.getColor());
        Color.Companion companion = Color.Companion;
        if (!ULong.m3427equalsimpl0(Color, j)) {
            androidPaint.m438setColor8_81llA(j);
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
            androidPaint.m437setBlendModes9anfk8(i);
        }
        int m434getFilterQualityfv9h1I = androidPaint.m434getFilterQualityfv9h1I();
        FilterQuality.Companion companion3 = FilterQuality.Companion;
        if (m434getFilterQualityfv9h1I == i2) {
            return selectPaint;
        }
        androidPaint.m439setFilterQualityvDHp3xo(i2);
        return selectPaint;
    }

    /* renamed from: configurePaint-swdJneE$default, reason: not valid java name */
    public static Paint m514configurePaintswdJneE$default(CanvasDrawScope canvasDrawScope, Brush brush, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i) {
        DrawScope.Companion.getClass();
        return canvasDrawScope.m515configurePaintswdJneE(brush, drawStyle, f, colorFilter, i, DrawScope.Companion.DefaultFilterQuality);
    }

    /* renamed from: configurePaint-swdJneE, reason: not valid java name */
    public final Paint m515configurePaintswdJneE(Brush brush, DrawStyle drawStyle, float f, ColorFilter colorFilter, int i, int i2) {
        Paint selectPaint = selectPaint(drawStyle);
        if (brush != null) {
            brush.mo449applyToPq9zytI(f, mo545getSizeNHjbRc(), selectPaint);
        } else {
            AndroidPaint androidPaint = (AndroidPaint) selectPaint;
            if (androidPaint.internalShader != null) {
                androidPaint.setShader(null);
            }
            long Color = ColorKt.Color(androidPaint.internalPaint.getColor());
            Color.Companion.getClass();
            long j = Color.Black;
            if (!ULong.m3427equalsimpl0(Color, j)) {
                androidPaint.m438setColor8_81llA(j);
            }
            if (androidPaint.internalPaint.getAlpha() / 255.0f != f) {
                androidPaint.setAlpha(f);
            }
        }
        AndroidPaint androidPaint2 = (AndroidPaint) selectPaint;
        if (!Intrinsics.areEqual(androidPaint2.internalColorFilter, colorFilter)) {
            androidPaint2.setColorFilter(colorFilter);
        }
        int i3 = androidPaint2._blendMode;
        BlendMode.Companion companion = BlendMode.Companion;
        if (i3 != i) {
            androidPaint2.m437setBlendModes9anfk8(i);
        }
        int m434getFilterQualityfv9h1I = androidPaint2.m434getFilterQualityfv9h1I();
        FilterQuality.Companion companion2 = FilterQuality.Companion;
        if (m434getFilterQualityfv9h1I == i2) {
            return selectPaint;
        }
        androidPaint2.m439setFilterQualityvDHp3xo(i2);
        return selectPaint;
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawArc-yD3GUKo, reason: not valid java name */
    public final void mo516drawArcyD3GUKo(long j, float f, float f2, long j2, long j3, DrawStyle drawStyle, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.drawParams.canvas.drawArc(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i3), f, f2, m513configurePaint2qPWKa0$default(this, j, drawStyle, 1.0f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-V9BoPsw, reason: not valid java name */
    public final void mo517drawCircleV9BoPsw(Brush brush, float f, long j, float f2, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.mo426drawCircle9KIMszo(f, j, m514configurePaintswdJneE$default(this, brush, drawStyle, f2, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-VaOC9Bg, reason: not valid java name */
    public final void mo518drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.mo426drawCircle9KIMszo(f, j2, m513configurePaint2qPWKa0$default(this, j, drawStyle, f2, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawImage-AZ2fEMs, reason: not valid java name */
    public final void mo519drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2) {
        this.drawParams.canvas.mo428drawImageRectHPBpro0(imageBitmap, j, j2, j3, m515configurePaintswdJneE(null, drawStyle, f, colorFilter, i, i2));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawLine-NGM6Ib0, reason: not valid java name */
    public final void mo520drawLineNGM6Ib0(long j, long j2, long j3, float f, int i, float f2, int i2) {
        Canvas canvas = this.drawParams.canvas;
        StrokeJoin.Companion.getClass();
        DrawScope.Companion.getClass();
        int i3 = DrawScope.Companion.DefaultFilterQuality;
        Paint obtainStrokePaint = obtainStrokePaint();
        if (f2 != 1.0f) {
            j = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), Color.m457getAlphaimpl(j) * f2, Color.m459getColorSpaceimpl(j));
        }
        AndroidPaint androidPaint = (AndroidPaint) obtainStrokePaint;
        long Color = ColorKt.Color(androidPaint.internalPaint.getColor());
        Color.Companion companion = Color.Companion;
        if (!ULong.m3427equalsimpl0(Color, j)) {
            androidPaint.m438setColor8_81llA(j);
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
            androidPaint.m437setBlendModes9anfk8(i2);
        }
        if (androidPaint.internalPaint.getStrokeWidth() != f) {
            androidPaint.setStrokeWidth(f);
        }
        if (androidPaint.internalPaint.getStrokeMiter() != 4.0f) {
            androidPaint.internalPaint.setStrokeMiter(4.0f);
        }
        int m435getStrokeCapKaPHkGw = androidPaint.m435getStrokeCapKaPHkGw();
        StrokeCap.Companion companion3 = StrokeCap.Companion;
        if (m435getStrokeCapKaPHkGw != i) {
            androidPaint.m440setStrokeCapBeK7IIE(i);
        }
        if (androidPaint.m436getStrokeJoinLxFBmk8() != 0) {
            androidPaint.m441setStrokeJoinWw9F2mQ(0);
        }
        if (!Intrinsics.areEqual(androidPaint.pathEffect, (Object) null)) {
            androidPaint.setPathEffect(null);
        }
        int m434getFilterQualityfv9h1I = androidPaint.m434getFilterQualityfv9h1I();
        FilterQuality.Companion companion4 = FilterQuality.Companion;
        if (m434getFilterQualityfv9h1I != i3) {
            androidPaint.m439setFilterQualityvDHp3xo(i3);
        }
        canvas.mo429drawLineWko1d7g(j2, j3, obtainStrokePaint);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-GBMwjPU, reason: not valid java name */
    public final void mo521drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.drawPath(path, m514configurePaintswdJneE$default(this, brush, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-LG529CI, reason: not valid java name */
    public final void mo522drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, int i) {
        this.drawParams.canvas.drawPath(path, m513configurePaint2qPWKa0$default(this, j, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRect-n-J9OG0, reason: not valid java name */
    public final void mo523drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.drawParams.canvas.drawRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i3), m513configurePaint2qPWKa0$default(this, j, drawStyle, f, colorFilter, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-ZuiqVtQ, reason: not valid java name */
    public final void mo524drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, int i) {
        int i2 = (int) (j >> 32);
        int i3 = (int) (j & 4294967295L);
        this.drawParams.canvas.drawRoundRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j2 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j2 & 4294967295L)) + Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)), Float.intBitsToFloat((int) (j3 & 4294967295L)), m514configurePaintswdJneE$default(this, brush, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-u-Aw5IA, reason: not valid java name */
    public final void mo525drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, int i) {
        int i2 = (int) (j2 >> 32);
        int i3 = (int) (j2 & 4294967295L);
        this.drawParams.canvas.drawRoundRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j4 >> 32)), Float.intBitsToFloat((int) (j4 & 4294967295L)), m513configurePaint2qPWKa0$default(this, j, drawStyle, f, null, i));
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
        androidPaint2.m442setStylek9PVt8s(PaintingStyle.Stroke);
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
            androidPaint2.m442setStylek9PVt8s(0);
            this.fillPaint = androidPaint2;
            return androidPaint2;
        }
        if (!(drawStyle instanceof Stroke)) {
            throw new NoWhenBranchMatchedException();
        }
        Paint obtainStrokePaint = obtainStrokePaint();
        AndroidPaint androidPaint3 = (AndroidPaint) obtainStrokePaint;
        float strokeWidth = androidPaint3.internalPaint.getStrokeWidth();
        Stroke stroke = (Stroke) drawStyle;
        float f = stroke.width;
        if (strokeWidth != f) {
            androidPaint3.setStrokeWidth(f);
        }
        int m435getStrokeCapKaPHkGw = androidPaint3.m435getStrokeCapKaPHkGw();
        int i = stroke.cap;
        if (m435getStrokeCapKaPHkGw != i) {
            androidPaint3.m440setStrokeCapBeK7IIE(i);
        }
        float strokeMiter = androidPaint3.internalPaint.getStrokeMiter();
        float f2 = stroke.miter;
        if (strokeMiter != f2) {
            androidPaint3.internalPaint.setStrokeMiter(f2);
        }
        int m436getStrokeJoinLxFBmk8 = androidPaint3.m436getStrokeJoinLxFBmk8();
        int i2 = stroke.join;
        if (m436getStrokeJoinLxFBmk8 != i2) {
            androidPaint3.m441setStrokeJoinWw9F2mQ(i2);
        }
        PathEffect pathEffect = androidPaint3.pathEffect;
        PathEffect pathEffect2 = stroke.pathEffect;
        if (!Intrinsics.areEqual(pathEffect, pathEffect2)) {
            androidPaint3.setPathEffect(pathEffect2);
        }
        return obtainStrokePaint;
    }
}
