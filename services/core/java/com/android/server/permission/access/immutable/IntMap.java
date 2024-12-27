package com.android.server.permission.access.immutable;

import android.util.SparseArray;

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
