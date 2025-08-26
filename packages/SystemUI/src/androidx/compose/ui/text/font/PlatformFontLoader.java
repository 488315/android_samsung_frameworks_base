package androidx.compose.ui.text.font;

import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public interface PlatformFontLoader {
    Object awaitLoad(Font font, Continuation continuation);

    Object getCacheKey();

    Object loadBlocking(Font font);
}
