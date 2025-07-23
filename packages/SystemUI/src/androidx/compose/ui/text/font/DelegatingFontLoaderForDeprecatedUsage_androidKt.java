package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.Font;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DelegatingFontLoaderForDeprecatedUsage_androidKt {
    public static final FontFamilyResolverImpl createFontFamilyResolver(Font.ResourceLoader resourceLoader) {
        return new FontFamilyResolverImpl(new DelegatingFontLoaderForDeprecatedUsage(resourceLoader), null, null, null, null, 30, null);
    }
}
