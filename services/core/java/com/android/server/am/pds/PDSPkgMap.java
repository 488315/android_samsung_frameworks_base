package com.android.server.am.pds;

import android.util.ArrayMap;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PDSPkgMap {
    public final ArrayMap mMap = new ArrayMap();

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
