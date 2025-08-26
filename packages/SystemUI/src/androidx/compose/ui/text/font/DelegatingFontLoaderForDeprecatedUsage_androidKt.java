package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.Font;

/* loaded from: classes.dex */
public abstract class DelegatingFontLoaderForDeprecatedUsage_androidKt {
    public static final FontFamilyResolverImpl createFontFamilyResolver(Font.ResourceLoader resourceLoader) {
        return new FontFamilyResolverImpl(new DelegatingFontLoaderForDeprecatedUsage(resourceLoader), null, null, null, null, 30, null);
    }
}
