package com.android.server.utils;

import android.util.SparseBooleanArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WatchedSparseBooleanArray extends WatchableImpl implements Snappable {
    public final SparseBooleanArray mStorage;

    public WatchedSparseBooleanArray() {
        this.mStorage = new SparseBooleanArray();
    }

    public WatchedSparseBooleanArray(WatchedSparseBooleanArray watchedSparseBooleanArray) {
        this.mStorage = watchedSparseBooleanArray.mStorage.clone();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WatchedSparseBooleanArray) {
            return this.mStorage.equals(((WatchedSparseBooleanArray) obj).mStorage);
        }
        return false;
    }

    public final int hashCode() {
        return this.mStorage.hashCode();
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedSparseBooleanArray watchedSparseBooleanArray = new WatchedSparseBooleanArray(this);
        watchedSparseBooleanArray.seal();
        return watchedSparseBooleanArray;
    }

    public final String toString() {
        return this.mStorage.toString();
    }
}
