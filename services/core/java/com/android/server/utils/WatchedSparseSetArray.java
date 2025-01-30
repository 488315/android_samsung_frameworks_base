package com.android.server.utils;

import android.util.ArraySet;
import android.util.SparseSetArray;

/* loaded from: classes3.dex */
public class WatchedSparseSetArray extends WatchableImpl implements Snappable {
    public final SparseSetArray mStorage;

    public final void onChanged() {
        dispatchChange(this);
    }

    public WatchedSparseSetArray() {
        this.mStorage = new SparseSetArray();
    }

    public WatchedSparseSetArray(WatchedSparseSetArray watchedSparseSetArray) {
        this.mStorage = new SparseSetArray(watchedSparseSetArray.untrackedStorage());
    }

    public WatchedSparseSetArray(SparseSetArray sparseSetArray) {
        this.mStorage = sparseSetArray;
    }

    public SparseSetArray untrackedStorage() {
        return this.mStorage;
    }

    public boolean add(int i, Object obj) {
        boolean add = this.mStorage.add(i, obj);
        onChanged();
        return add;
    }

    public boolean contains(int i, Object obj) {
        return this.mStorage.contains(i, obj);
    }

    public ArraySet get(int i) {
        return this.mStorage.get(i);
    }

    public boolean remove(int i, Object obj) {
        if (!this.mStorage.remove(i, obj)) {
            return false;
        }
        onChanged();
        return true;
    }

    public void remove(int i) {
        this.mStorage.remove(i);
        onChanged();
    }

    public int size() {
        return this.mStorage.size();
    }

    public int keyAt(int i) {
        return this.mStorage.keyAt(i);
    }

    @Override // com.android.server.utils.Snappable
    public Object snapshot() {
        WatchedSparseSetArray watchedSparseSetArray = new WatchedSparseSetArray(this);
        watchedSparseSetArray.seal();
        return watchedSparseSetArray;
    }
}
