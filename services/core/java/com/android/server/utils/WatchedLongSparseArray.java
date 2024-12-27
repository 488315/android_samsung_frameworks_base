package com.android.server.utils;

import android.util.LongSparseArray;

public final class WatchedLongSparseArray extends WatchableImpl implements Snappable {
    public final AnonymousClass1 mObserver;
    public final LongSparseArray mStorage;
    public volatile boolean mWatching;

    public WatchedLongSparseArray() {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedLongSparseArray.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new LongSparseArray();
    }

    public WatchedLongSparseArray(int i) {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedLongSparseArray.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new LongSparseArray(i);
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
        WatchedLongSparseArray watchedLongSparseArray =
                new WatchedLongSparseArray(this.mStorage.size());
        if (watchedLongSparseArray.mStorage.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = this.mStorage.size();
        for (int i = 0; i < size; i++) {
            Object maybeSnapshot = Snapshots.maybeSnapshot(this.mStorage.valueAt(i));
            watchedLongSparseArray.mStorage.put(this.mStorage.keyAt(i), maybeSnapshot);
        }
        watchedLongSparseArray.seal();
        return watchedLongSparseArray;
    }

    public final String toString() {
        return this.mStorage.toString();
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
