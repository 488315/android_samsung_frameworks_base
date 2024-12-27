package com.android.server.utils;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WatchedSparseArray extends WatchableImpl implements Snappable {
    public final AnonymousClass1 mObserver;
    public final SparseArray mStorage;
    public volatile boolean mWatching;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.utils.WatchedSparseArray$1] */
    public WatchedSparseArray() {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedSparseArray.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedSparseArray.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new SparseArray();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.utils.WatchedSparseArray$1] */
    public WatchedSparseArray(int i) {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedSparseArray.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedSparseArray.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new SparseArray(i);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.utils.WatchedSparseArray$1] */
    public WatchedSparseArray(WatchedSparseArray watchedSparseArray) {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedSparseArray.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedSparseArray.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = watchedSparseArray.mStorage.clone();
    }

    public static void snapshot(
            WatchedSparseArray watchedSparseArray, WatchedSparseArray watchedSparseArray2) {
        if (watchedSparseArray.mStorage.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedSparseArray2.mStorage.size();
        for (int i = 0; i < size; i++) {
            Object maybeSnapshot = Snapshots.maybeSnapshot(watchedSparseArray2.mStorage.valueAt(i));
            watchedSparseArray.mStorage.put(watchedSparseArray2.mStorage.keyAt(i), maybeSnapshot);
        }
        watchedSparseArray.seal();
    }

    public final void delete(int i) {
        Object obj = this.mStorage.get(i);
        this.mStorage.delete(i);
        unregisterChildIf$4(obj);
        dispatchChange(this);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WatchedSparseArray) {
            return this.mStorage.equals(((WatchedSparseArray) obj).mStorage);
        }
        return false;
    }

    public final int hashCode() {
        return this.mStorage.hashCode();
    }

    public final void put(int i, Object obj) {
        Object obj2 = this.mStorage.get(i);
        this.mStorage.put(i, obj);
        unregisterChildIf$4(obj2);
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).registerObserver(this.mObserver);
        }
        dispatchChange(this);
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

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray(this.mStorage.size());
        snapshot(watchedSparseArray, this);
        return watchedSparseArray;
    }

    public final String toString() {
        return this.mStorage.toString();
    }

    public final void unregisterChildIf$4(Object obj) {
        if (this.mWatching && (obj instanceof Watchable) && this.mStorage.indexOfValue(obj) == -1) {
            ((Watchable) obj).unregisterObserver(this.mObserver);
        }
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
}
