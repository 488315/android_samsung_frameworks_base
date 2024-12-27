package com.android.server.permission.access.immutable;

import android.util.ArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
