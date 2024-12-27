package com.android.server.permission.access.immutable;

import android.util.SparseBooleanArray;

public final class MutableIntSet implements Immutable {
    public final SparseBooleanArray array;

    public /* synthetic */ MutableIntSet() {
        this(new SparseBooleanArray());
    }

    public MutableIntSet(SparseBooleanArray sparseBooleanArray) {
        this.array = sparseBooleanArray;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableIntSet(this.array.clone());
    }

    public final String toString() {
        return this.array.toString();
    }
}
