package com.android.server.utils.quota;

import android.util.ArrayMap;
import android.util.SparseArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UptcMap {
    public final SparseArrayMap mData = new SparseArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface UptcDataConsumer {
        void accept(int i, Object obj, String str, String str2);
    }

    public final void forEach(UptcDataConsumer uptcDataConsumer) {
        int numMaps = this.mData.numMaps();
        for (int i = 0; i < numMaps; i++) {
            int keyAt = this.mData.keyAt(i);
            int numElementsForKey = this.mData.numElementsForKey(keyAt);
            for (int i2 = 0; i2 < numElementsForKey; i2++) {
                String str = (String) this.mData.keyAt(i, i2);
                ArrayMap arrayMap = (ArrayMap) this.mData.get(keyAt, str);
                int size = arrayMap != null ? arrayMap.size() : 0;
                for (int i3 = 0; i3 < size; i3++) {
                    String str2 = (String) ((ArrayMap) this.mData.valueAt(i, i2)).keyAt(i3);
                    uptcDataConsumer.accept(keyAt, get(keyAt, str, str2), str, str2);
                }
            }
        }
    }

    public final Object get(int i, String str, String str2) {
        ArrayMap arrayMap = (ArrayMap) this.mData.get(i, str);
        if (arrayMap != null) {
            return arrayMap.get(str2);
        }
        return null;
    }
}
