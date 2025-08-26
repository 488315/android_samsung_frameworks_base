package androidx.compose.ui.text.font;

import androidx.collection.LruCache;
import androidx.compose.ui.text.platform.SynchronizedObject;

/* loaded from: classes.dex */
public final class TypefaceRequestCache {
    public final SynchronizedObject lock = new SynchronizedObject();
    public final LruCache resultCache = new LruCache(16);
}
