package com.android.server.am;

import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FreecessPkgMap {
    public final SparseArray mUidMap = new SparseArray();
    public final ArrayMap mUserIdMap = new ArrayMap();

    public final Object getByUserId(int i, String str) {
        SparseArray sparseArray = (SparseArray) this.mUserIdMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    public final void put(String str, int i, Object obj) {
        this.mUidMap.put(i, obj);
        int userId = UserHandle.getUserId(i);
        SparseArray sparseArray = (SparseArray) this.mUserIdMap.get(str);
        if (sparseArray == null) {
            sparseArray = new SparseArray(2);
            this.mUserIdMap.put(str, sparseArray);
        }
        sparseArray.put(userId, obj);
    }

    public final void remove(int i, String str) {
        this.mUidMap.remove(i);
        int userId = UserHandle.getUserId(i);
        SparseArray sparseArray = (SparseArray) this.mUserIdMap.get(str);
        if (sparseArray != null) {
            sparseArray.removeReturnOld(userId);
            if (sparseArray.size() == 0) {
                this.mUserIdMap.remove(str);
            }
        }
    }

    public final int totalSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.mUserIdMap.size(); i2++) {
            for (int i3 = 0; i3 < ((SparseArray) this.mUserIdMap.valueAt(i2)).size(); i3++) {
                i++;
            }
        }
        return i;
    }
}
