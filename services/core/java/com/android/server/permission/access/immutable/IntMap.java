package com.android.server.permission.access.immutable;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class IntMap implements Immutable {
    public final SparseArray array;

    public IntMap(SparseArray sparseArray) {
        this.array = sparseArray;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableIntMap(this.array.clone());
    }

    public final String toString() {
        return this.array.toString();
    }
}
