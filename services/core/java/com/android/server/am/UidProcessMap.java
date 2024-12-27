package com.android.server.am;

import android.util.ArrayMap;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UidProcessMap {
    public final SparseArray mMap = new SparseArray();

    public final void clear() {
        this.mMap.clear();
    }

    public final Object get(int i, String str) {
        ArrayMap arrayMap = (ArrayMap) this.mMap.get(i);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str);
    }

    public final void put(String str, int i, Object obj) {
        ArrayMap arrayMap = (ArrayMap) this.mMap.get(i);
        if (arrayMap == null) {
            arrayMap = new ArrayMap(2);
            this.mMap.put(i, arrayMap);
        }
        arrayMap.put(str, obj);
    }
}
