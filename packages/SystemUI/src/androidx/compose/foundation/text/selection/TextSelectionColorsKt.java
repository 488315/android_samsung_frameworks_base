package androidx.compose.foundation.text.selection;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.jvm.functions.Function0;

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
        long jColor = ColorKt.Color(4282550004L);
        DefaultTextSelectionColors = new TextSelectionColors(jColor, ColorKt.Color(Color.m463getRedimpl(jColor), Color.m462getGreenimpl(jColor), Color.m460getBlueimpl(jColor), 0.4f, Color.m461getColorSpaceimpl(jColor)), null);
    }
}
