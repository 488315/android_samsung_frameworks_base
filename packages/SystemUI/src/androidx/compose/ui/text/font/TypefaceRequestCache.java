package androidx.compose.ui.text.font;

import androidx.collection.LruCache;
import androidx.compose.ui.text.platform.SynchronizedObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TypefaceRequestCache {
    public final SynchronizedObject lock = new SynchronizedObject();
    public final LruCache resultCache = new LruCache(16);
}
