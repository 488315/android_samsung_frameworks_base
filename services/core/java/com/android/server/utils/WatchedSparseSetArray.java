package com.android.server.utils;

import android.util.SparseSetArray;

public final class WatchedSparseSetArray extends WatchableImpl implements Snappable {
    public final SparseSetArray mStorage;

    public WatchedSparseSetArray() {
        this.mStorage = new SparseSetArray();
    }

    public WatchedSparseSetArray(SparseSetArray sparseSetArray) {
        this.mStorage = sparseSetArray;
    }

    public WatchedSparseSetArray(WatchedSparseSetArray watchedSparseSetArray) {
        this.mStorage = new SparseSetArray(watchedSparseSetArray.mStorage);
    }

    public final boolean add(int i, Object obj) {
        boolean add = this.mStorage.add(i, obj);
        dispatchChange(this);
        return add;
    }

    public final void remove(int i) {
        this.mStorage.remove(i);
        dispatchChange(this);
    }

    public final void remove(int i, Object obj) {
        if (this.mStorage.remove(i, obj)) {
            dispatchChange(this);
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedSparseSetArray watchedSparseSetArray = new WatchedSparseSetArray(this);
        watchedSparseSetArray.seal();
        return watchedSparseSetArray;
    }
}
