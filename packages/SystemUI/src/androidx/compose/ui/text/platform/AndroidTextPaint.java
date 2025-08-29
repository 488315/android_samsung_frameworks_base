package androidx.compose.ui.text.platform;

import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Paint;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDrawStyleKt;
import kotlin.ULong;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

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
    public final void m783setBlendModes9anfk8(int i) {
        int i2 = this.backingBlendMode;
        BlendMode.Companion companion = BlendMode.Companion;
        if (i == i2) {
            return;
        }
        ((AndroidPaint) getComposePaint()).m439setBlendModes9anfk8(i);
        this.backingBlendMode = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0038  */
    /* renamed from: setBrush-12SF9DM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m784setBrush12SF9DM(final Brush brush, final long j, float f) {
        if (brush == null) {
            this.shaderState = null;
            this.brush = null;
            this.brushSize = null;
            setShader(null);
            return;
        }
        if (brush instanceof SolidColor) {
            m785setColor8_81llA(TextDrawStyleKt.m811modulateDxMtmZc(f, ((SolidColor) brush).value));
            return;
        }
        if (brush instanceof ShaderBrush) {
            if (Intrinsics.areEqual(this.brush, brush)) {
                Size size = this.brushSize;
                if (!(size == null ? false : Size.m416equalsimpl0(size.packedValue, j))) {
                }
            } else {
                if (j != 9205357640488583168L) {
                    this.brush = brush;
                    this.brushSize = Size.m415boximpl(j);
                    this.shaderState = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.ui.text.platform.AndroidTextPaint$setBrush$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return ((ShaderBrush) brush).mo453createShaderuvyYCjk(j);
                        }
                    });
                }
            }
            Paint composePaint = getComposePaint();
            State state = this.shaderState;
            ((AndroidPaint) composePaint).setShader(state != null ? (Shader) state.getValue() : null);
            this.lastColor = null;
            AndroidTextPaint_androidKt.setAlpha(this, f);
        }
    }

    /* renamed from: setColor-8_81llA, reason: not valid java name */
    public final void m785setColor8_81llA(long j) {
        Color color = this.lastColor;
        if (color == null ? false : ULong.m3446equalsimpl0(color.value, j)) {
            return;
        }
        if (j != 16) {
            this.lastColor = Color.m456boximpl(j);
            setColor(ColorKt.m469toArgb8_81llA(j));
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
            ((AndroidPaint) composePaint).m444setStylek9PVt8s(PaintingStyle.Stroke);
            Stroke stroke = (Stroke) drawStyle;
            ((AndroidPaint) getComposePaint()).setStrokeWidth(stroke.width);
            ((AndroidPaint) getComposePaint()).internalPaint.setStrokeMiter(stroke.miter);
            ((AndroidPaint) getComposePaint()).m443setStrokeJoinWw9F2mQ(stroke.join);
            ((AndroidPaint) getComposePaint()).m442setStrokeCapBeK7IIE(stroke.cap);
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
        setShadowLayer(f, Float.intBitsToFloat((int) (shadow2.offset >> 32)), Float.intBitsToFloat((int) (this.shadow.offset & 4294967295L)), ColorKt.m469toArgb8_81llA(this.shadow.color));
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
