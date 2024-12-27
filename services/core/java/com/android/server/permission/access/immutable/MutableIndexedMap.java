package com.android.server.permission.access.immutable;

import android.util.ArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MutableIndexedMap extends IndexedMap {
    public MutableIndexedMap() {
        super(new ArrayMap());
    }

    public final void put(Object obj, Object obj2) {
        this.map.put(obj, obj2);
    }
}
