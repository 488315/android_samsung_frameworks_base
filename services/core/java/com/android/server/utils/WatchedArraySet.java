package com.android.server.utils;

import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WatchedArraySet extends WatchableImpl implements Snappable {
    public final AnonymousClass1 mObserver;
    public final ArraySet mStorage;
    public volatile boolean mWatching;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.utils.WatchedArraySet$1] */
    public WatchedArraySet() {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedArraySet.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedArraySet.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new ArraySet(0, false);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.utils.WatchedArraySet$1] */
    public WatchedArraySet(WatchedArraySet watchedArraySet) {
        this.mWatching = false;
        this.mObserver =
                new Watcher() { // from class: com.android.server.utils.WatchedArraySet.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        WatchedArraySet.this.dispatchChange(watchable);
                    }
                };
        this.mStorage = new ArraySet(watchedArraySet.mStorage);
    }

    public final boolean add(Object obj) {
        boolean add = this.mStorage.add(obj);
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).registerObserver(this.mObserver);
        }
        dispatchChange(this);
        return add;
    }

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

    public final boolean equals(Object obj) {
        return obj instanceof WatchedArraySet
                ? this.mStorage.equals(((WatchedArraySet) obj).mStorage)
                : this.mStorage.equals(obj);
    }

    public final int hashCode() {
        return this.mStorage.hashCode();
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

    public final boolean remove(Object obj) {
        if (!this.mStorage.remove(obj)) {
            return false;
        }
        if (this.mWatching && (obj instanceof Watchable) && !this.mStorage.contains(obj)) {
            ((Watchable) obj).unregisterObserver(this.mObserver);
        }
        dispatchChange(this);
        return true;
    }

    @Override // com.android.server.utils.Snappable
    public final WatchedArraySet snapshot() {
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        if (watchedArraySet.mStorage.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = this.mStorage.size();
        watchedArraySet.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            watchedArraySet.mStorage.append(Snapshots.maybeSnapshot(this.mStorage.valueAt(i)));
        }
        watchedArraySet.seal();
        return watchedArraySet;
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
