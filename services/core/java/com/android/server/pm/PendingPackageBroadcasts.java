package com.android.server.pm;

import android.util.ArrayMap;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class PendingPackageBroadcasts {
    public final Object mLock = new PackageManagerTracedLock();
    public final SparseArray mUidMap = new SparseArray(2);

    public void addComponent(int i, String str, String str2) {
        synchronized (this.mLock) {
            ArrayList orAllocate = getOrAllocate(i, str);
            if (!orAllocate.contains(str2)) {
                orAllocate.add(str2);
            }
        }
    }

    public void addComponents(int i, String str, List list) {
        synchronized (this.mLock) {
            ArrayList orAllocate = getOrAllocate(i, str);
            for (int i2 = 0; i2 < list.size(); i2++) {
                String str2 = (String) list.get(i2);
                if (!orAllocate.contains(str2)) {
                    orAllocate.add(str2);
                }
            }
        }
    }

    public void remove(int i, String str) {
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mUidMap.get(i);
            if (arrayMap != null) {
                arrayMap.remove(str);
            }
        }
    }

    public void remove(int i) {
        synchronized (this.mLock) {
            this.mUidMap.remove(i);
        }
    }

    public SparseArray copiedMap() {
        SparseArray sparseArray;
        synchronized (this.mLock) {
            sparseArray = new SparseArray();
            for (int i = 0; i < this.mUidMap.size(); i++) {
                ArrayMap arrayMap = (ArrayMap) this.mUidMap.valueAt(i);
                ArrayMap arrayMap2 = new ArrayMap();
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    arrayMap2.put((String) arrayMap.keyAt(i2), new ArrayList((Collection) arrayMap.valueAt(i2)));
                }
                sparseArray.put(this.mUidMap.keyAt(i), arrayMap2);
            }
        }
        return sparseArray;
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mUidMap.clear();
        }
    }

    public final ArrayList getOrAllocate(int i, String str) {
        ArrayList arrayList;
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mUidMap.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mUidMap.put(i, arrayMap);
            }
            arrayList = (ArrayList) arrayMap.computeIfAbsent(str, new Function() { // from class: com.android.server.pm.PendingPackageBroadcasts$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    ArrayList lambda$getOrAllocate$0;
                    lambda$getOrAllocate$0 = PendingPackageBroadcasts.lambda$getOrAllocate$0((String) obj);
                    return lambda$getOrAllocate$0;
                }
            });
        }
        return arrayList;
    }

    public static /* synthetic */ ArrayList lambda$getOrAllocate$0(String str) {
        return new ArrayList();
    }
}
