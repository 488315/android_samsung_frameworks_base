package androidx.compose.foundation.text;

import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextFieldSize {
    public Density density;
    public FontFamily.Resolver fontFamilyResolver;
    public LayoutDirection layoutDirection;
    public long minSize;
    public TextStyle resolvedStyle;
    public Object typeface;

    public TextFieldSize(LayoutDirection layoutDirection, Density density, FontFamily.Resolver resolver, TextStyle textStyle, Object obj) {
        long computeSizeForDefaultText;
        this.layoutDirection = layoutDirection;
        this.density = density;
        this.fontFamilyResolver = resolver;
        this.resolvedStyle = textStyle;
        this.typeface = obj;
        computeSizeForDefaultText = TextFieldDelegateKt.computeSizeForDefaultText(textStyle, density, resolver, TextFieldDelegateKt.EmptyTextReplacement, 1);
        this.minSize = computeSizeForDefaultText;
    }
}
