package com.android.server.utils;

import android.util.LongSparseArray;

/* loaded from: classes3.dex */
public class WatchedLongSparseArray extends WatchableImpl implements Snappable {
    public final Watcher mObserver;
    public final LongSparseArray mStorage;
    public volatile boolean mWatching;

    public final void onChanged() {
        dispatchChange(this);
    }

    public final void registerChild(Object obj) {
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).registerObserver(this.mObserver);
        }
    }

    public final void unregisterChild(Object obj) {
        if (this.mWatching && (obj instanceof Watchable)) {
            ((Watchable) obj).unregisterObserver(this.mObserver);
        }
    }

    public final void unregisterChildIf(Object obj) {
        if (this.mWatching && (obj instanceof Watchable) && this.mStorage.indexOfValue(obj) == -1) {
            ((Watchable) obj).unregisterObserver(this.mObserver);
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void registerObserver(Watcher watcher) {
        super.registerObserver(watcher);
        if (registeredObserverCount() == 1) {
            this.mWatching = true;
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                registerChild(this.mStorage.valueAt(i));
            }
        }
    }

    @Override // com.android.server.utils.WatchableImpl, com.android.server.utils.Watchable
    public void unregisterObserver(Watcher watcher) {
        super.unregisterObserver(watcher);
        if (registeredObserverCount() == 0) {
            int size = this.mStorage.size();
            for (int i = 0; i < size; i++) {
                unregisterChild(this.mStorage.valueAt(i));
            }
            this.mWatching = false;
        }
    }

    public WatchedLongSparseArray() {
        this.mWatching = false;
        this.mObserver = new Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                WatchedLongSparseArray.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new LongSparseArray();
    }

    public WatchedLongSparseArray(int i) {
        this.mWatching = false;
        this.mObserver = new Watcher() { // from class: com.android.server.utils.WatchedLongSparseArray.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                WatchedLongSparseArray.this.dispatchChange(watchable);
            }
        };
        this.mStorage = new LongSparseArray(i);
    }

    public Object get(long j) {
        return this.mStorage.get(j);
    }

    public void delete(long j) {
        Object obj = this.mStorage.get(j, null);
        this.mStorage.delete(j);
        unregisterChildIf(obj);
        onChanged();
    }

    public void remove(long j) {
        delete(j);
    }

    public void put(long j, Object obj) {
        Object obj2 = this.mStorage.get(j);
        this.mStorage.put(j, obj);
        unregisterChildIf(obj2);
        registerChild(obj);
        onChanged();
    }

    public int size() {
        return this.mStorage.size();
    }

    public long keyAt(int i) {
        return this.mStorage.keyAt(i);
    }

    public Object valueAt(int i) {
        return this.mStorage.valueAt(i);
    }

    public int indexOfKey(long j) {
        return this.mStorage.indexOfKey(j);
    }

    public String toString() {
        return this.mStorage.toString();
    }

    @Override // com.android.server.utils.Snappable
    public WatchedLongSparseArray snapshot() {
        WatchedLongSparseArray watchedLongSparseArray = new WatchedLongSparseArray(size());
        snapshot(watchedLongSparseArray, this);
        return watchedLongSparseArray;
    }

    public static void snapshot(WatchedLongSparseArray watchedLongSparseArray, WatchedLongSparseArray watchedLongSparseArray2) {
        if (watchedLongSparseArray.size() != 0) {
            throw new IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedLongSparseArray2.size();
        for (int i = 0; i < size; i++) {
            Object maybeSnapshot = Snapshots.maybeSnapshot(watchedLongSparseArray2.valueAt(i));
            watchedLongSparseArray.mStorage.put(watchedLongSparseArray2.keyAt(i), maybeSnapshot);
        }
        watchedLongSparseArray.seal();
    }
}
