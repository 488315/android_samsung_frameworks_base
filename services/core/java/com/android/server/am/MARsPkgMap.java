package com.android.server.am;

import android.util.ArrayMap;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class MARsPkgMap {
    public final ArrayMap mMap = new ArrayMap();

    public Object get(String str, int i) {
        SparseArray sparseArray = (SparseArray) this.mMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    public Object put(String str, int i, Object obj) {
        SparseArray sparseArray = (SparseArray) this.mMap.get(str);
        if (sparseArray == null) {
            sparseArray = new SparseArray(2);
            this.mMap.put(str, sparseArray);
        }
        sparseArray.put(i, obj);
        return obj;
    }

    public Object remove(String str, int i) {
        SparseArray sparseArray = (SparseArray) this.mMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        Object removeReturnOld = sparseArray.removeReturnOld(i);
        if (sparseArray.size() == 0) {
            this.mMap.remove(str);
        }
        return removeReturnOld;
    }

    public ArrayMap getMap() {
        return this.mMap;
    }

    public int size() {
        return this.mMap.size();
    }

    public void clear() {
        this.mMap.clear();
    }

    public int totalSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.mMap.size(); i2++) {
            for (int i3 = 0; i3 < ((SparseArray) this.mMap.valueAt(i2)).size(); i3++) {
                i++;
            }
        }
        return i;
    }
}
