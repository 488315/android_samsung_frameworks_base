package androidx.compose.ui.graphics;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Fill;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public abstract class OutlineKt {
    /* renamed from: drawOutline-wDX37Ww$default, reason: not valid java name */
    public static void m492drawOutlinewDX37Ww$default(DrawScope drawScope, Outline outline, long j, float f, Fill fill, int i) {
        float f2 = (i & 4) != 0 ? 1.0f : f;
        Fill fill2 = (i & 8) != 0 ? Fill.INSTANCE : fill;
        DrawScope.Companion.getClass();
        int i2 = DrawScope.Companion.DefaultBlendMode;
        if (outline instanceof Outline.Rectangle) {
            Rect rect = ((Outline.Rectangle) outline).rect;
            Offset.Companion companion = Offset.Companion;
            drawScope.mo525drawRectnJ9OG0(j, (Float.floatToRawIntBits(rect.left) << 32) | (Float.floatToRawIntBits(rect.top) & 4294967295L), size(rect), f2, fill2, null, i2);
            return;
        }
        if (!(outline instanceof Outline.Rounded)) {
            if (!(outline instanceof Outline.Generic)) {
                throw new NoWhenBranchMatchedException();
            }
            drawScope.mo524drawPathLG529CI(((Outline.Generic) outline).path, j, f2, fill2, i2);
            return;
        }
        Outline.Rounded rounded = (Outline.Rounded) outline;
        AndroidPath androidPath = rounded.roundRectPath;
        if (androidPath != null) {
            drawScope.mo524drawPathLG529CI(androidPath, j, f2, fill2, i2);
            return;
        }
        float fIntBitsToFloat = Float.intBitsToFloat((int) (rounded.roundRect.bottomLeftCornerRadius >> 32));
        long jFloatToRawIntBits = (Float.floatToRawIntBits(r0.left) << 32) | (Float.floatToRawIntBits(r0.top) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(r0.getWidth()) << 32) | (Float.floatToRawIntBits(r0.getHeight()) & 4294967295L);
        Size.Companion companion3 = Size.Companion;
        drawScope.mo527drawRoundRectuAw5IA(j, jFloatToRawIntBits, jFloatToRawIntBits2, (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L), fill2, f2, i2);
    }

    public static final long size(Rect rect) {
        float f = rect.right - rect.left;
        float f2 = rect.bottom - rect.top;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return jFloatToRawIntBits;
    }
}
