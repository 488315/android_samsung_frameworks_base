package androidx.compose.ui.text.platform;

import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class URLSpanCache {
    public final WeakHashMap spansByAnnotation = new WeakHashMap();
    public final WeakHashMap urlSpansByAnnotation = new WeakHashMap();
    public final WeakHashMap linkSpansWithListenerByAnnotation = new WeakHashMap();
}
