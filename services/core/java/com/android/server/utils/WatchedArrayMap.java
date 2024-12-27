package com.android.server.utils;

import android.util.ArrayMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WatchedArrayMap extends WatchableImpl implements Map, Snappable {
    public final AnonymousClass1 mObserver;
    public final ArrayMap mStorage;
    public volatile boolean mWatching;

    public WatchedArrayMap() {
        this(0);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.utils.WatchedArrayMap$1] */
    public WatchedArrayMap(int i) {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedArrayMap.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedArrayMap.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new ArrayMap(i, false);
    }

    public static void snapshot(WatchedArrayMap watchedArrayMap, WatchedArrayMap watchedArrayMap2) {
        if (watchedArrayMap.mStorage.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedArrayMap2.mStorage.size();
        watchedArrayMap.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            Object maybeSnapshot = Snapshots.maybeSnapshot(watchedArrayMap2.mStorage.valueAt(i));
            watchedArrayMap.mStorage.put(watchedArrayMap2.mStorage.keyAt(i), maybeSnapshot);
        }
        watchedArrayMap.seal();
    }

    @Override // java.util.Map
    public final void clear() {
        if (this.mWatching) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                Object valueAt = this.mStorage.valueAt(i);
                if (this.mWatching && (valueAt instanceof Watchable)) {
                    ((Watchable) valueAt).unregisterObserver(this.mObserver);
                }
            }
        }
        this.mStorage.clear();
        dispatchChange(this);
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.mStorage.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.mStorage.containsValue(obj);
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return Collections.unmodifiableSet(this.mStorage.entrySet());
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj instanceof WatchedArrayMap) {
            return this.mStorage.equals(((WatchedArrayMap) obj).mStorage);
        }
        return false;
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return this.mStorage.get(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.mStorage.hashCode();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.mStorage.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return Collections.unmodifiableSet(this.mStorage.keySet());
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        Object put = this.mStorage.put(obj, obj2);
        if (this.mWatching && (obj2 instanceof Watchable)) {
            ((Watchable) obj2).registerObserver(this.mObserver);
        }
        dispatchChange(this);
        return put;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        super.registerObserver(watcher);
        if (this.mObservers.size() == 1) {
            this.mWatching = true;
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                Object valueAt = this.mStorage.valueAt(i);
                if (this.mWatching && (valueAt instanceof Watchable)) {
                    ((Watchable) valueAt).registerObserver(this.mObserver);
                }
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        Object remove = this.mStorage.remove(obj);
        if (this.mWatching
                && (remove instanceof Watchable)
                && !this.mStorage.containsValue(remove)) {
            ((Watchable) remove).unregisterObserver(this.mObserver);
        }
        dispatchChange(this);
        return remove;
    }

    @Override // java.util.Map
    public final int size() {
        return this.mStorage.size();
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
        snapshot(watchedArrayMap, this);
        return watchedArrayMap;
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        super.unregisterObserver(watcher);
        if (this.mObservers.size() == 0) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                Object valueAt = this.mStorage.valueAt(i);
                if (this.mWatching && (valueAt instanceof Watchable)) {
                    ((Watchable) valueAt).unregisterObserver(this.mObserver);
                }
            }
            this.mWatching = false;
        }
    }

    @Override // java.util.Map
    public final Collection values() {
        return Collections.unmodifiableCollection(this.mStorage.values());
    }
}
