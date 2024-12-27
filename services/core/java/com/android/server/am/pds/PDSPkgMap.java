package com.android.server.am.pds;

import android.util.ArrayMap;
import android.util.SparseArray;

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
