package com.android.server.utils;

import android.util.SparseSetArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
