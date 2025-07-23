package androidx.lifecycle.viewmodel.internal;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewModelImpl {
    public final Set closeables;
    public volatile boolean isCleared;
    public final Map keyToCloseables;
    public final SynchronizedObject lock;

    public ViewModelImpl() {
        this.lock = new SynchronizedObject();
        this.keyToCloseables = new LinkedHashMap();
        this.closeables = new LinkedHashSet();
    }

    public final void addCloseable(String str, AutoCloseable autoCloseable) {
        AutoCloseable autoCloseable2;
        if (this.isCleared) {
            try {
                autoCloseable.close();
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (this.lock) {
            autoCloseable2 = (AutoCloseable) this.keyToCloseables.put(str, autoCloseable);
        }
        if (autoCloseable2 != null) {
            try {
                autoCloseable2.close();
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public ViewModelImpl(CoroutineScope coroutineScope) {
        this.lock = new SynchronizedObject();
        this.keyToCloseables = new LinkedHashMap();
        this.closeables = new LinkedHashSet();
        addCloseable("androidx.lifecycle.viewmodel.internal.ViewModelCoroutineScope.JOB_KEY", new CloseableCoroutineScope(coroutineScope));
    }

    public ViewModelImpl(AutoCloseable... autoCloseableArr) {
        this.lock = new SynchronizedObject();
        this.keyToCloseables = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.closeables = linkedHashSet;
        CollectionsKt__MutableCollectionsKt.addAll(linkedHashSet, autoCloseableArr);
    }

    public ViewModelImpl(CoroutineScope coroutineScope, AutoCloseable... autoCloseableArr) {
        this.lock = new SynchronizedObject();
        this.keyToCloseables = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.closeables = linkedHashSet;
        addCloseable("androidx.lifecycle.viewmodel.internal.ViewModelCoroutineScope.JOB_KEY", new CloseableCoroutineScope(coroutineScope));
        CollectionsKt__MutableCollectionsKt.addAll(linkedHashSet, autoCloseableArr);
    }
}
