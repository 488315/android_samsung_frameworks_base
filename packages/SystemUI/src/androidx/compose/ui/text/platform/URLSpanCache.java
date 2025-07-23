package androidx.compose.ui.text.platform;

import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class URLSpanCache {
    public final WeakHashMap spansByAnnotation = new WeakHashMap();
    public final WeakHashMap urlSpansByAnnotation = new WeakHashMap();
    public final WeakHashMap linkSpansWithListenerByAnnotation = new WeakHashMap();
}
