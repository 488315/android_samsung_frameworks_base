package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ActivityManagerWrapper {
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
