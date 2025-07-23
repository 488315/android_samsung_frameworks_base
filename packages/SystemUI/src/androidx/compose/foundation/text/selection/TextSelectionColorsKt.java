package androidx.compose.foundation.text.selection;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextSelectionColorsKt {
    public static final TextSelectionColors DefaultTextSelectionColors;
    public static final DynamicProvidableCompositionLocal LocalTextSelectionColors = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.foundation.text.selection.TextSelectionColorsKt$LocalTextSelectionColors$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TextSelectionColorsKt.DefaultTextSelectionColors;
        }
    });

    static {
        long Color;
        long Color2 = ColorKt.Color(4282550004L);
        Color = ColorKt.Color(Color.m461getRedimpl(Color2), Color.m460getGreenimpl(Color2), Color.m458getBlueimpl(Color2), 0.4f, Color.m459getColorSpaceimpl(Color2));
        DefaultTextSelectionColors = new TextSelectionColors(Color2, Color, null);
    }
}
