package com.android.server.am;

import android.util.ArrayMap;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MARsPkgMap {
    public final ArrayMap mMap = new ArrayMap();

    public final Object get(int i, String str) {
        SparseArray sparseArray = (SparseArray) this.mMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    public final void put(String str, int i, MARsPackageInfo mARsPackageInfo) {
        SparseArray sparseArray = (SparseArray) this.mMap.get(str);
        if (sparseArray == null) {
            sparseArray = new SparseArray(2);
            this.mMap.put(str, sparseArray);
        }
        sparseArray.put(i, mARsPackageInfo);
    }

    public final void remove(int i, String str) {
        SparseArray sparseArray = (SparseArray) this.mMap.get(str);
        if (sparseArray != null) {
            sparseArray.removeReturnOld(i);
            if (sparseArray.size() == 0) {
                this.mMap.remove(str);
            }
        }
    }

    public final int totalSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.mMap.size(); i2++) {
            for (int i3 = 0; i3 < ((SparseArray) this.mMap.valueAt(i2)).size(); i3++) {
                i++;
            }
        }
        return i;
    }
}
