package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Fill;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OutlineKt {
    /* renamed from: drawOutline-wDX37Ww$default, reason: not valid java name */
    public static void m490drawOutlinewDX37Ww$default(DrawScope drawScope, Outline outline, long j, float f, Fill fill, int i) {
        float f2 = (i & 4) != 0 ? 1.0f : f;
        Fill fill2 = (i & 8) != 0 ? Fill.INSTANCE : fill;
        DrawScope.Companion.getClass();
        int i2 = DrawScope.Companion.DefaultBlendMode;
        if (outline instanceof Outline.Rectangle) {
            Rect rect = ((Outline.Rectangle) outline).rect;
            Offset.Companion companion = Offset.Companion;
            drawScope.mo523drawRectnJ9OG0(j, (Float.floatToRawIntBits(rect.left) << 32) | (Float.floatToRawIntBits(rect.top) & 4294967295L), size(rect), f2, fill2, null, i2);
            return;
        }
        if (!(outline instanceof Outline.Rounded)) {
            if (!(outline instanceof Outline.Generic)) {
                throw new NoWhenBranchMatchedException();
            }
            drawScope.mo522drawPathLG529CI(((Outline.Generic) outline).path, j, f2, fill2, i2);
            return;
        }
        Outline.Rounded rounded = (Outline.Rounded) outline;
        AndroidPath androidPath = rounded.roundRectPath;
        if (androidPath != null) {
            drawScope.mo522drawPathLG529CI(androidPath, j, f2, fill2, i2);
            return;
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (rounded.roundRect.bottomLeftCornerRadius >> 32));
        long floatToRawIntBits = (Float.floatToRawIntBits(r0.left) << 32) | (Float.floatToRawIntBits(r0.top) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        long floatToRawIntBits2 = (Float.floatToRawIntBits(r0.getWidth()) << 32) | (Float.floatToRawIntBits(r0.getHeight()) & 4294967295L);
        Size.Companion companion3 = Size.Companion;
        drawScope.mo525drawRoundRectuAw5IA(j, floatToRawIntBits, floatToRawIntBits2, (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L), fill2, f2, i2);
    }

    public static final long size(Rect rect) {
        float f = rect.right - rect.left;
        float f2 = rect.bottom - rect.top;
        long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }
}
