package com.android.server.permission.access.immutable;

import android.util.ArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MutableIndexedReferenceMap implements Immutable {
    public final ArrayMap map;

    public /* synthetic */ MutableIndexedReferenceMap() {
        this(new ArrayMap());
    }

    public MutableIndexedReferenceMap(ArrayMap arrayMap) {
        this.map = arrayMap;
    }

    public final Immutable get(Object obj) {
        MutableReference mutableReference = (MutableReference) this.map.get(obj);
        if (mutableReference != null) {
            return mutableReference.immutable;
        }
        return null;
    }

    public final void put(Object obj, MutableIndexedMap mutableIndexedMap) {}

    public final void remove$1(Object obj) {}

    @Override // com.android.server.permission.access.immutable.Immutable
    public final Object toMutable() {
        ArrayMap arrayMap = new ArrayMap(this.map);
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            arrayMap.setValueAt(i, ((MutableReference) arrayMap.valueAt(i)).toImmutable());
        }
        return new MutableIndexedReferenceMap(arrayMap);
    }

    public final String toString() {
        return this.map.toString();
    }

    public final Immutable valueAt(int i) {
        return ((MutableReference) this.map.valueAt(i)).immutable;
    }
}
