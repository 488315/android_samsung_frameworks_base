package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.text.SpanStyleKt;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.util.MathHelpersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextDrawStyleKt {
    public static final TextForegroundStyle lerp(TextForegroundStyle textForegroundStyle, TextForegroundStyle textForegroundStyle2, float f) {
        boolean z = textForegroundStyle instanceof BrushStyle;
        if (!z && !(textForegroundStyle2 instanceof BrushStyle)) {
            TextForegroundStyle.Companion companion = TextForegroundStyle.Companion;
            long m465lerpjxsXWHM = ColorKt.m465lerpjxsXWHM(textForegroundStyle.mo792getColor0d7_KjU(), textForegroundStyle2.mo792getColor0d7_KjU(), f);
            companion.getClass();
            return TextForegroundStyle.Companion.m810from8_81llA(m465lerpjxsXWHM);
        }
        if (!z || !(textForegroundStyle2 instanceof BrushStyle)) {
            return (TextForegroundStyle) SpanStyleKt.lerpDiscrete(f, textForegroundStyle, textForegroundStyle2);
        }
        TextForegroundStyle.Companion companion2 = TextForegroundStyle.Companion;
        Brush brush = (Brush) SpanStyleKt.lerpDiscrete(f, ((BrushStyle) textForegroundStyle).value, ((BrushStyle) textForegroundStyle2).value);
        float lerp = MathHelpersKt.lerp(((BrushStyle) textForegroundStyle).alpha, ((BrushStyle) textForegroundStyle2).alpha, f);
        companion2.getClass();
        return TextForegroundStyle.Companion.from(lerp, brush);
    }

    /* renamed from: modulate-DxMtmZc, reason: not valid java name */
    public static final long m809modulateDxMtmZc(float f, long j) {
        long Color;
        if (Float.isNaN(f) || f >= 1.0f) {
            return j;
        }
        Color = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), Color.m457getAlphaimpl(j) * f, Color.m459getColorSpaceimpl(j));
        return Color;
    }
}
