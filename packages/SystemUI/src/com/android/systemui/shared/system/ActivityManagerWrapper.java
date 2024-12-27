package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityManagerWrapper {
    public static final ActivityManagerWrapper sInstance = new ActivityManagerWrapper();
    public final ActivityTaskManager mAtm = ActivityTaskManager.getInstance();

    private ActivityManagerWrapper() {
    }

    public static void removeTask(int i) {
        try {
            ActivityTaskManager.getService().removeTask(i);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", "Failed to remove task=" + i, e);
        }
    }

    public final ActivityManager.RunningTaskInfo getRunningTask() {
        List tasks = this.mAtm.getTasks(1, false);
        if (tasks.isEmpty()) {
            return null;
        }
        return (ActivityManager.RunningTaskInfo) tasks.get(0);
    }
}
