package androidx.compose.material3.tokens;

import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.font.GenericFontFamily;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TypefaceTokens {
    public static final GenericFontFamily Brand;
    public static final TypefaceTokens INSTANCE = new TypefaceTokens();
    public static final GenericFontFamily Plain;
    public static final FontWeight WeightBold;
    public static final FontWeight WeightMedium;
    public static final FontWeight WeightRegular;

    static {
        FontFamily.Companion companion = FontFamily.Companion;
        companion.getClass();
        GenericFontFamily genericFontFamily = FontFamily.SansSerif;
        Brand = genericFontFamily;
        companion.getClass();
        Plain = genericFontFamily;
        FontWeight.Companion companion2 = FontWeight.Companion;
        companion2.getClass();
        WeightBold = FontWeight.Bold;
        companion2.getClass();
        WeightMedium = FontWeight.Medium;
        companion2.getClass();
        WeightRegular = FontWeight.Normal;
    }

    private TypefaceTokens() {
    }
}
