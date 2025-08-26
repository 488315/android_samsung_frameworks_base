package androidx.compose.ui.text.font;

import androidx.compose.ui.platform.AndroidFontResourceLoader;
import androidx.compose.ui.text.font.Font;
import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public final class DelegatingFontLoaderForDeprecatedUsage implements PlatformFontLoader {
    public final Object cacheKey = new Object();
    public final Font.ResourceLoader loader;

    public DelegatingFontLoaderForDeprecatedUsage(Font.ResourceLoader resourceLoader) {
        this.loader = resourceLoader;
    }

    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    public final Object awaitLoad(Font font, Continuation continuation) {
        return ((AndroidFontResourceLoader) this.loader).load(font);
    }

    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    public final Object getCacheKey() {
        return this.cacheKey;
    }

    @Override // androidx.compose.ui.text.font.PlatformFontLoader
    public final Object loadBlocking(Font font) {
        return ((AndroidFontResourceLoader) this.loader).load(font);
    }
}
