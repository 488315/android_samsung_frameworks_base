package androidx.compose.ui.text.platform;

import android.graphics.Paint;
import android.text.TextPaint;
import androidx.compose.runtime.State;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.style.TextDecoration;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidTextPaint extends TextPaint {
    public int backingBlendMode;
    public AndroidPaint backingComposePaint;
    public Brush brush;
    public Size brushSize;
    public DrawStyle drawStyle;
    public Color lastColor;
    public State shaderState;
    public Shadow shadow;
    public TextDecoration textDecoration;

    public AndroidTextPaint(int i, float f) {
        super(i);
        ((TextPaint) this).density = f;
        TextDecoration.Companion.getClass();
        this.textDecoration = TextDecoration.None;
        DrawScope.Companion.getClass();
        this.backingBlendMode = DrawScope.Companion.DefaultBlendMode;
        Shadow.Companion.getClass();
        this.shadow = Shadow.None;
    }

    public final Paint getComposePaint() {
        AndroidPaint androidPaint = this.backingComposePaint;
        if (androidPaint != null) {
            return androidPaint;
        }
        AndroidPaint androidPaint2 = new AndroidPaint(this);
        this.backingComposePaint = androidPaint2;
        return androidPaint2;
    }

    /* renamed from: setBlendMode-s9anfk8, reason: not valid java name */
    public final void m781setBlendModes9anfk8(int i) {
        int i2 = this.backingBlendMode;
        BlendMode.Companion companion = BlendMode.Companion;
        if (i == i2) {
            return;
        }
        ((AndroidPaint) getComposePaint()).m437setBlendModes9anfk8(i);
        this.backingBlendMode = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
    
        if ((r1 == null ? false : androidx.compose.ui.geometry.Size.m414equalsimpl0(r1.packedValue, r7)) == false) goto L19;
     */
    /* renamed from: setBrush-12SF9DM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m782setBrush12SF9DM(final androidx.compose.ui.graphics.Brush r6, final long r7, float r9) {
        /*
            r5 = this;
            r0 = 0
            if (r6 != 0) goto Ld
            r5.shaderState = r0
            r5.brush = r0
            r5.brushSize = r0
            r5.setShader(r0)
            return
        Ld:
            boolean r1 = r6 instanceof androidx.compose.ui.graphics.SolidColor
            if (r1 == 0) goto L1d
            androidx.compose.ui.graphics.SolidColor r6 = (androidx.compose.ui.graphics.SolidColor) r6
            long r6 = r6.value
            long r6 = androidx.compose.ui.text.style.TextDrawStyleKt.m809modulateDxMtmZc(r9, r6)
            r5.m783setColor8_81llA(r6)
            return
        L1d:
            boolean r1 = r6 instanceof androidx.compose.ui.graphics.ShaderBrush
            if (r1 == 0) goto L71
            androidx.compose.ui.graphics.Brush r1 = r5.brush
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r6)
            r2 = 0
            if (r1 == 0) goto L38
            androidx.compose.ui.geometry.Size r1 = r5.brushSize
            if (r1 != 0) goto L30
            r1 = r2
            goto L36
        L30:
            long r3 = r1.packedValue
            boolean r1 = androidx.compose.ui.geometry.Size.m414equalsimpl0(r3, r7)
        L36:
            if (r1 != 0) goto L57
        L38:
            r3 = 9205357640488583168(0x7fc000007fc00000, double:2.247117487993712E307)
            int r1 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r1 == 0) goto L42
            r2 = 1
        L42:
            if (r2 == 0) goto L57
            r5.brush = r6
            androidx.compose.ui.geometry.Size r1 = androidx.compose.ui.geometry.Size.m413boximpl(r7)
            r5.brushSize = r1
            androidx.compose.ui.text.platform.AndroidTextPaint$setBrush$1 r1 = new androidx.compose.ui.text.platform.AndroidTextPaint$setBrush$1
            r1.<init>()
            androidx.compose.runtime.State r6 = androidx.compose.runtime.SnapshotStateKt.derivedStateOf(r1)
            r5.shaderState = r6
        L57:
            androidx.compose.ui.graphics.Paint r6 = r5.getComposePaint()
            androidx.compose.runtime.State r7 = r5.shaderState
            if (r7 == 0) goto L66
            java.lang.Object r7 = r7.getValue()
            android.graphics.Shader r7 = (android.graphics.Shader) r7
            goto L67
        L66:
            r7 = r0
        L67:
            androidx.compose.ui.graphics.AndroidPaint r6 = (androidx.compose.ui.graphics.AndroidPaint) r6
            r6.setShader(r7)
            r5.lastColor = r0
            androidx.compose.ui.text.platform.AndroidTextPaint_androidKt.setAlpha(r5, r9)
        L71:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.platform.AndroidTextPaint.m782setBrush12SF9DM(androidx.compose.ui.graphics.Brush, long, float):void");
    }

    /* renamed from: setColor-8_81llA, reason: not valid java name */
    public final void m783setColor8_81llA(long j) {
        Color color = this.lastColor;
        if (color == null ? false : ULong.m3427equalsimpl0(color.value, j)) {
            return;
        }
        if (j != 16) {
            this.lastColor = Color.m454boximpl(j);
            setColor(ColorKt.m467toArgb8_81llA(j));
            this.shaderState = null;
            this.brush = null;
            this.brushSize = null;
            setShader(null);
        }
    }

    public final void setDrawStyle(DrawStyle drawStyle) {
        if (drawStyle == null || Intrinsics.areEqual(this.drawStyle, drawStyle)) {
            return;
        }
        this.drawStyle = drawStyle;
        if (drawStyle.equals(Fill.INSTANCE)) {
            setStyle(Paint.Style.FILL);
            return;
        }
        if (drawStyle instanceof Stroke) {
            androidx.compose.ui.graphics.Paint composePaint = getComposePaint();
            PaintingStyle.Companion.getClass();
            ((AndroidPaint) composePaint).m442setStylek9PVt8s(PaintingStyle.Stroke);
            Stroke stroke = (Stroke) drawStyle;
            ((AndroidPaint) getComposePaint()).setStrokeWidth(stroke.width);
            ((AndroidPaint) getComposePaint()).internalPaint.setStrokeMiter(stroke.miter);
            ((AndroidPaint) getComposePaint()).m441setStrokeJoinWw9F2mQ(stroke.join);
            ((AndroidPaint) getComposePaint()).m440setStrokeCapBeK7IIE(stroke.cap);
            ((AndroidPaint) getComposePaint()).setPathEffect(stroke.pathEffect);
        }
    }

    public final void setShadow(Shadow shadow) {
        if (shadow == null || Intrinsics.areEqual(this.shadow, shadow)) {
            return;
        }
        this.shadow = shadow;
        Shadow.Companion.getClass();
        if (shadow.equals(Shadow.None)) {
            clearShadowLayer();
            return;
        }
        Shadow shadow2 = this.shadow;
        float f = shadow2.blurRadius;
        if (f == 0.0f) {
            f = Float.MIN_VALUE;
        }
        setShadowLayer(f, Float.intBitsToFloat((int) (shadow2.offset >> 32)), Float.intBitsToFloat((int) (this.shadow.offset & 4294967295L)), ColorKt.m467toArgb8_81llA(this.shadow.color));
    }

    public final void setTextDecoration(TextDecoration textDecoration) {
        if (textDecoration == null || Intrinsics.areEqual(this.textDecoration, textDecoration)) {
            return;
        }
        this.textDecoration = textDecoration;
        TextDecoration.Companion companion = TextDecoration.Companion;
        companion.getClass();
        setUnderlineText(textDecoration.contains(TextDecoration.Underline));
        TextDecoration textDecoration2 = this.textDecoration;
        companion.getClass();
        setStrikeThruText(textDecoration2.contains(TextDecoration.LineThrough));
    }
}
