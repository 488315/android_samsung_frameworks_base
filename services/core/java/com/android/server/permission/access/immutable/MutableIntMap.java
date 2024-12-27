package com.android.server.permission.access.immutable;

import android.util.SparseArray;

public final class MutableIntMap extends IntMap {
    public MutableIntMap() {
        super(new SparseArray());
    }
}
