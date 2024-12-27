package com.android.server.pm;

import android.util.ArrayMap;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PendingPackageBroadcasts {
    public final PackageManagerTracedLock mLock = new PackageManagerTracedLock(null);
    public final SparseArray mUidMap = new SparseArray(2);

    public final void addComponent(int i, String str, String str2) {
        synchronized (this.mLock) {
            try {
                ArrayList orAllocate = getOrAllocate(i, str);
                if (!orAllocate.contains(str2)) {
                    orAllocate.add(str2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final SparseArray copiedMap() {
        SparseArray sparseArray;
        synchronized (this.mLock) {
            try {
                sparseArray = new SparseArray();
                for (int i = 0; i < this.mUidMap.size(); i++) {
                    ArrayMap arrayMap = (ArrayMap) this.mUidMap.valueAt(i);
                    ArrayMap arrayMap2 = new ArrayMap();
                    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                        arrayMap2.put((String) arrayMap.keyAt(i2), new ArrayList((Collection) arrayMap.valueAt(i2)));
                    }
                    sparseArray.put(this.mUidMap.keyAt(i), arrayMap2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sparseArray;
    }

    public final ArrayList getOrAllocate(int i, String str) {
        ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mUidMap.get(i);
                if (arrayMap == null) {
                    arrayMap = new ArrayMap();
                    this.mUidMap.put(i, arrayMap);
                }
                arrayList = (ArrayList) arrayMap.computeIfAbsent(str, new PendingPackageBroadcasts$$ExternalSyntheticLambda0());
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }
}
