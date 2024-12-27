package com.android.server.utils;

import java.util.ArrayList;

public final class WatchedArrayList extends WatchableImpl implements Snappable {
    public final ArrayList mStorage;
    public volatile boolean mWatching = false;
    public final AnonymousClass1 mObserver =
            new Watcher() { // from class: com.android.server.utils.WatchedArrayList.1
                @Override // com.android.server.utils.Watcher
                public final void onChange(Watchable watchable) {
                    WatchedArrayList.this.dispatchChange(watchable);
                }
            };

    public WatchedArrayList(int i) {
        this.mStorage = new ArrayList(i);
    }

    public final void clear() {
        if (this.mWatching) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                Object obj = this.mStorage.get(i);
                if (this.mWatching && (obj instanceof Watchable)) {
                    ((Watchable) obj).unregisterObserver(this.mObserver);
                }
            }
        }
        this.mStorage.clear();
        dispatchChange(this);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WatchedArrayList) {
            return this.mStorage.equals(((WatchedArrayList) obj).mStorage);
        }
        return false;
    }

    public final int hashCode() {
        return this.mStorage.hashCode();
    }

    public final void registerChild(Object obj) {
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).registerObserver(this.mObserver);
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        super.registerObserver(watcher);
        if (this.mObservers.size() == 1) {
            this.mWatching = true;
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                registerChild(this.mStorage.get(i));
            }
        }
    }

    public final void set(int i, Object obj) {
        Object obj2 = this.mStorage.set(i, obj);
        if (obj != obj2) {
            if (this.mWatching && (obj2 instanceof Watchable) && !this.mStorage.contains(obj2)) {
                ((Watchable) obj2).unregisterObserver(this.mObserver);
            }
            registerChild(obj);
            dispatchChange(this);
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedArrayList watchedArrayList = new WatchedArrayList(this.mStorage.size());
        if (watchedArrayList.mStorage.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = this.mStorage.size();
        watchedArrayList.mStorage.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            watchedArrayList.mStorage.add(Snapshots.maybeSnapshot(this.mStorage.get(i)));
        }
        watchedArrayList.seal();
        return watchedArrayList;
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        super.unregisterObserver(watcher);
        if (this.mObservers.size() == 0) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                Object obj = this.mStorage.get(i);
                if (this.mWatching && (obj instanceof Watchable)) {
                    ((Watchable) obj).unregisterObserver(this.mObserver);
                }
            }
            this.mWatching = false;
        }
    }
}
