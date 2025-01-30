package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.Log;
import com.samsung.android.multiwindow.IDexSnappingCallback;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MultiWindowManagerWrapper {
    private static final String TAG = "[DS]MultiWindowManagerWrapper";
    private static final MultiWindowManagerWrapper sInstance = new MultiWindowManagerWrapper();
    private ArrayList<DexSnappingCallback> mCallbacks = new ArrayList<>();
    private final String DEX_LAUNCHER_HOME_ACTIVITY_TASK = "com.sec.android.app.desktoplauncher/com.android.launcher3.Launcher";
    private final IDexSnappingCallback mDexSnappingCallback = new IDexSnappingCallback.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.MultiWindowManagerWrapper.1
        public void onWindowSnappingChanged(int i, Rect rect) {
            Log.d(MultiWindowManagerWrapper.TAG, "onWindowSnappingChanged , taskId = " + i + ", othersBounds = " + rect);
            Iterator it = MultiWindowManagerWrapper.this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((DexSnappingCallback) it.next()).DeXWindowSnappingChanged(i, rect);
            }
        }
    };
    private final MultiWindowManager mMWm = new MultiWindowManager();

    private MultiWindowManagerWrapper() {
    }

    private void addRegisterDexSnappingCallback() {
        this.mMWm.registerDexSnappingCallback(this.mDexSnappingCallback);
    }

    public static MultiWindowManagerWrapper getInstance() {
        return sInstance;
    }

    private boolean isDeXHome(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.baseActivity.flattenToString().contains("com.sec.android.app.desktoplauncher/com.android.launcher3.Launcher");
    }

    private void removeUnregisterDexSnappingCallback() {
        this.mMWm.unregisterDexSnappingCallback(this.mDexSnappingCallback);
    }

    public void addCallback(DexSnappingCallback dexSnappingCallback) {
        this.mCallbacks.add(dexSnappingCallback);
        addRegisterDexSnappingCallback();
    }

    public void clearCallback() {
        this.mCallbacks.clear();
        removeUnregisterDexSnappingCallback();
    }

    public List<ActivityManager.RunningTaskInfo> getVisibleTasks() {
        return this.mMWm.getVisibleTasks();
    }

    public List<ActivityManager.RunningTaskInfo> getVisibleTasksbyDisplayId(int i) {
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : this.mMWm.getVisibleTasks()) {
            if (runningTaskInfo.displayId == i && !arrayList.contains(runningTaskInfo) && !isDeXHome(runningTaskInfo)) {
                arrayList.add(runningTaskInfo);
            }
        }
        return arrayList;
    }

    public boolean hasMinimizedToggleTasks() {
        return this.mMWm.hasMinimizedToggleTasks();
    }

    public boolean minimizeAllTasksForLauncher() {
        return this.mMWm.minimizeAllTasks(0);
    }

    public boolean minimizeTaskById(int i) {
        this.mMWm.minimizeTaskById(i);
        return true;
    }

    public void removeCallback(DexSnappingCallback dexSnappingCallback) {
        this.mCallbacks.remove(dexSnappingCallback);
    }

    public void updateTaskPositionInTaskBar(Map map) {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DexSnappingCallback {
        default void DeXWindowSnappingChanged(int i, Rect rect) {
        }
    }
}
