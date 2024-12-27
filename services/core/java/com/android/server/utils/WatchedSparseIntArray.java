package com.android.server.utils;

import android.util.SparseIntArray;

public final class WatchedSparseIntArray extends WatchableImpl implements Snappable {
    public final SparseIntArray mStorage;

    public WatchedSparseIntArray() {
        this.mStorage = new SparseIntArray();
    }

    public WatchedSparseIntArray(WatchedSparseIntArray watchedSparseIntArray) {
        this.mStorage = watchedSparseIntArray.mStorage.clone();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WatchedSparseIntArray) {
            return this.mStorage.equals(((WatchedSparseIntArray) obj).mStorage);
        }
        return false;
    }

    public final int hashCode() {
        return this.mStorage.hashCode();
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedSparseIntArray watchedSparseIntArray = new WatchedSparseIntArray(this);
        watchedSparseIntArray.seal();
        return watchedSparseIntArray;
    }

    public final String toString() {
        return this.mStorage.toString();
    }
}
