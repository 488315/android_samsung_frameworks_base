package com.android.server.permission.access.immutable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class IndexedMapExtensionsKt {
    public static final Object getWithDefault(IndexedMap indexedMap, Object obj, Object obj2) {
        int indexOfKey;
        return (indexedMap != null && (indexOfKey = indexedMap.map.indexOfKey(obj)) >= 0) ? indexedMap.map.valueAt(indexOfKey) : obj2;
    }

    public static final void putWithDefault(MutableIndexedMap mutableIndexedMap, Object obj, Object obj2, Object obj3) {
        int indexOfKey = mutableIndexedMap.map.indexOfKey(obj);
        if (indexOfKey < 0) {
            if (obj2.equals(obj3)) {
                return;
            }
            mutableIndexedMap.put(obj, obj2);
        } else {
            if (obj2.equals(mutableIndexedMap.map.valueAt(indexOfKey))) {
                return;
            }
            if (obj2.equals(obj3)) {
                mutableIndexedMap.map.removeAt(indexOfKey);
            } else {
                mutableIndexedMap.map.setValueAt(indexOfKey, obj2);
            }
        }
    }
}
