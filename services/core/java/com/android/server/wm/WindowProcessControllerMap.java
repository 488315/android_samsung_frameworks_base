package com.android.server.wm;

import android.util.ArraySet;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowProcessControllerMap {
    public final SparseArray mPidMap = new SparseArray();
    public final Map mUidMap = new HashMap();

    public final WindowProcessController getProcess(int i) {
        return (WindowProcessController) this.mPidMap.get(i);
    }

    public final void put(int i, WindowProcessController windowProcessController) {
        WindowProcessController windowProcessController2 = (WindowProcessController) this.mPidMap.get(i);
        if (windowProcessController2 != null) {
            removeProcessFromUidMap(windowProcessController2);
        }
        this.mPidMap.put(i, windowProcessController);
        int i2 = windowProcessController.mUid;
        ArraySet arraySet = (ArraySet) ((HashMap) this.mUidMap).getOrDefault(Integer.valueOf(i2), new ArraySet());
        arraySet.add(windowProcessController);
        ((HashMap) this.mUidMap).put(Integer.valueOf(i2), arraySet);
    }

    public final void removeProcessFromUidMap(WindowProcessController windowProcessController) {
        Map map = this.mUidMap;
        int i = windowProcessController.mUid;
        ArraySet arraySet = (ArraySet) ((HashMap) map).get(Integer.valueOf(i));
        if (arraySet != null) {
            arraySet.remove(windowProcessController);
            if (arraySet.isEmpty()) {
                ((HashMap) this.mUidMap).remove(Integer.valueOf(i));
            }
        }
    }
}
