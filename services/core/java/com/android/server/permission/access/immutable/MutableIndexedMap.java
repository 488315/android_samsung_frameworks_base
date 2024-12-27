package com.android.server.permission.access.immutable;

import android.util.ArrayMap;

public final class MutableIndexedMap extends IndexedMap {
    public MutableIndexedMap() {
        super(new ArrayMap());
    }

    public final void put(Object obj, Object obj2) {
        this.map.put(obj, obj2);
    }
}
