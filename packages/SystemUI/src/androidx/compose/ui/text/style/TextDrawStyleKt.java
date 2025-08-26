package androidx.compose.ui.text.style;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.text.SpanStyleKt;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.util.MathHelpersKt;

/* loaded from: classes.dex */
public abstract class TextDrawStyleKt {
    public static final TextForegroundStyle lerp(TextForegroundStyle textForegroundStyle, TextForegroundStyle textForegroundStyle2, float f) {
        boolean z = textForegroundStyle instanceof BrushStyle;
        if (!z && !(textForegroundStyle2 instanceof BrushStyle)) {
            TextForegroundStyle.Companion companion = TextForegroundStyle.Companion;
            long jM467lerpjxsXWHM = ColorKt.m467lerpjxsXWHM(textForegroundStyle.mo794getColor0d7_KjU(), textForegroundStyle2.mo794getColor0d7_KjU(), f);
            companion.getClass();
            return TextForegroundStyle.Companion.m812from8_81llA(jM467lerpjxsXWHM);
        }
        if (!z || !(textForegroundStyle2 instanceof BrushStyle)) {
            return (TextForegroundStyle) SpanStyleKt.lerpDiscrete(f, textForegroundStyle, textForegroundStyle2);
        }
        TextForegroundStyle.Companion companion2 = TextForegroundStyle.Companion;
        Brush brush = (Brush) SpanStyleKt.lerpDiscrete(f, ((BrushStyle) textForegroundStyle).value, ((BrushStyle) textForegroundStyle2).value);
        float fLerp = MathHelpersKt.lerp(((BrushStyle) textForegroundStyle).alpha, ((BrushStyle) textForegroundStyle2).alpha, f);
        companion2.getClass();
        return TextForegroundStyle.Companion.from(fLerp, brush);
    }

    /* renamed from: modulate-DxMtmZc, reason: not valid java name */
    public static final long m811modulateDxMtmZc(float f, long j) {
        return (Float.isNaN(f) || f >= 1.0f) ? j : ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), Color.m459getAlphaimpl(j) * f, Color.m461getColorSpaceimpl(j));
    }
}
