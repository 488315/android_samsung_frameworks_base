package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivityManagerWrapper {
    public static final ActivityManagerWrapper sInstance = new ActivityManagerWrapper();
    public final ActivityTaskManager mAtm = ActivityTaskManager.getInstance();

    private ActivityManagerWrapper() {
    }

    public static int getCurrentUserId() {
        try {
            UserInfo currentUser = ActivityManager.getService().getCurrentUser();
            if (currentUser != null) {
                return currentUser.id;
            }
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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
