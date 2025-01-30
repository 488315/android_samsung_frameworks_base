package com.android.server.wm;

import android.util.ArraySet;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class WindowProcessControllerMap {
    public final SparseArray mPidMap = new SparseArray();
    public final Map mUidMap = new HashMap();

    public WindowProcessController getProcess(int i) {
        return (WindowProcessController) this.mPidMap.get(i);
    }

    public ArraySet getProcesses(int i) {
        return (ArraySet) this.mUidMap.get(Integer.valueOf(i));
    }

    public SparseArray getPidMap() {
        return this.mPidMap;
    }

    public void put(int i, WindowProcessController windowProcessController) {
        WindowProcessController windowProcessController2 = (WindowProcessController) this.mPidMap.get(i);
        if (windowProcessController2 != null) {
            removeProcessFromUidMap(windowProcessController2);
        }
        this.mPidMap.put(i, windowProcessController);
        int i2 = windowProcessController.mUid;
        ArraySet arraySet = (ArraySet) this.mUidMap.getOrDefault(Integer.valueOf(i2), new ArraySet());
        arraySet.add(windowProcessController);
        this.mUidMap.put(Integer.valueOf(i2), arraySet);
    }

    public void remove(int i) {
        WindowProcessController windowProcessController = (WindowProcessController) this.mPidMap.get(i);
        if (windowProcessController != null) {
            if (windowProcessController.getDisplayId() == 2) {
                windowProcessController.setOverrideDisplayId(-1);
            }
            this.mPidMap.remove(i);
            removeProcessFromUidMap(windowProcessController);
            windowProcessController.destroy();
        }
    }

    public final void removeProcessFromUidMap(WindowProcessController windowProcessController) {
        if (windowProcessController == null) {
            return;
        }
        int i = windowProcessController.mUid;
        ArraySet arraySet = (ArraySet) this.mUidMap.get(Integer.valueOf(i));
        if (arraySet != null) {
            arraySet.remove(windowProcessController);
            if (arraySet.isEmpty()) {
                this.mUidMap.remove(Integer.valueOf(i));
            }
        }
    }
}
