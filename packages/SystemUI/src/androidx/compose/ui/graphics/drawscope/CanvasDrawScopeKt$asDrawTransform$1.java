package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.InlineClassHelperKt;

/* loaded from: classes.dex */
public final class CanvasDrawScopeKt$asDrawTransform$1 implements DrawTransform {
    public final /* synthetic */ DrawContext $this_asDrawTransform;

    public CanvasDrawScopeKt$asDrawTransform$1(DrawContext drawContext) {
        this.$this_asDrawTransform = drawContext;
    }

    /* renamed from: clipRect-N_I0leg, reason: not valid java name */
    public final void m530clipRectN_I0leg(float f, float f2, float f3, float f4, int i) {
        ((CanvasDrawScope$drawContext$1) this.$this_asDrawTransform).getCanvas().mo426clipRectN_I0leg(f, f2, f3, f4, i);
    }

    public final void inset(float f, float f2, float f3, float f4) {
        DrawContext drawContext = this.$this_asDrawTransform;
        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = (CanvasDrawScope$drawContext$1) drawContext;
        Canvas canvas = canvasDrawScope$drawContext$1.getCanvas();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (((CanvasDrawScope$drawContext$1) drawContext).m528getSizeNHjbRc() >> 32)) - (f3 + f);
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (((CanvasDrawScope$drawContext$1) drawContext).m528getSizeNHjbRc() & 4294967295L)) - (f4 + f2);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
        Size.Companion companion = Size.Companion;
        if (Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32)) < 0.0f || Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L)) < 0.0f) {
            InlineClassHelperKt.throwIllegalArgumentException("Width and height must be greater than or equal to zero");
        }
        canvasDrawScope$drawContext$1.m529setSizeuvyYCjk(jFloatToRawIntBits);
        canvas.translate(f, f2);
    }

    /* renamed from: rotate-Uv8p0NA, reason: not valid java name */
    public final void m531rotateUv8p0NA(float f, long j) {
        Canvas canvas = ((CanvasDrawScope$drawContext$1) this.$this_asDrawTransform).getCanvas();
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        canvas.translate(Float.intBitsToFloat(i), Float.intBitsToFloat(i2));
        canvas.rotate(f);
        canvas.translate(-Float.intBitsToFloat(i), -Float.intBitsToFloat(i2));
    }

    /* renamed from: scale-0AR0LA0, reason: not valid java name */
    public final void m532scale0AR0LA0(float f, float f2, long j) {
        Canvas canvas = ((CanvasDrawScope$drawContext$1) this.$this_asDrawTransform).getCanvas();
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        canvas.translate(Float.intBitsToFloat(i), Float.intBitsToFloat(i2));
        canvas.scale(f, f2);
        canvas.translate(-Float.intBitsToFloat(i), -Float.intBitsToFloat(i2));
    }

    public final void translate(float f, float f2) {
        ((CanvasDrawScope$drawContext$1) this.$this_asDrawTransform).getCanvas().translate(f, f2);
    }
}
