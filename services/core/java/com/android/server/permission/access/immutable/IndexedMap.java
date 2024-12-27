package com.android.server.permission.access.immutable;

import android.util.ArrayMap;

public abstract class IndexedMap implements Immutable {
    public final ArrayMap map;

    public IndexedMap(ArrayMap arrayMap) {
        this.map = arrayMap;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        return new MutableIndexedMap(new ArrayMap(this.map));
    }

    public final String toString() {
        return this.map.toString();
    }
}
