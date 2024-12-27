package com.android.server.remoteappmode;

import android.app.ActivityManager;
import android.app.ITaskStackListener;
import android.content.ComponentName;
import android.os.Message;
import android.window.TaskSnapshot;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteAppTaskWatcher extends ITaskStackListener.Stub {
    public TaskChangeNotifier mCallback;
    public AnonymousClass1 mHandler;
    public boolean mNeedToNotifyRecentTaskListUpdated;
    public boolean mNeedToNotifyTaskDisplayChanged;

    public final void onActivityDismissingDockedTask() {}

    public final void onActivityDismissingSplitTask(String str) {}

    public final void onActivityForcedResizable(String str, int i, int i2) {}

    public final void onActivityLaunchOnSecondaryDisplayFailed(
            ActivityManager.RunningTaskInfo runningTaskInfo, int i) {}

    public final void onActivityLaunchOnSecondaryDisplayRerouted(
            ActivityManager.RunningTaskInfo runningTaskInfo, int i) {}

    public final void onActivityPinned(String str, int i, int i2, int i3) {}

    public final void onActivityRequestedOrientationChanged(int i, int i2) {}

    public final void onActivityRestartAttempt(
            ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {}

    public final void onActivityRotation(int i) {}

    public final void onActivityUnpinned() {}

    public final void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {}

    public final void onLockTaskModeChanged(int i) {}

    public final void onRecentTaskListFrozenChanged(boolean z) {}

    public final void onRecentTaskListUpdated() {}

    public final void onTaskCreated(int i, ComponentName componentName) {
        Message message = new Message();
        message.what = 0;
        message.arg1 = i;
        sendMessage(message);
    }

    public final void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {}

    public final void onTaskDisplayChanged(int i, int i2) {
        if (this.mNeedToNotifyTaskDisplayChanged) {
            Message message = new Message();
            message.what = 4;
            message.arg1 = i;
            message.arg2 = i2;
            sendMessage(message);
        }
    }

    public final void onTaskFocusChanged(int i, boolean z) {}

    public final void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {}

    public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {}

    public final void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {}

    public final void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) {}

    public final void onTaskRemoved(int i) {
        Message message = new Message();
        message.what = 3;
        message.arg1 = i;
        sendMessage(message);
    }

    public final void onTaskRequestedOrientationChanged(int i, int i2) {}

    public final void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) {
        if (this.mNeedToNotifyRecentTaskListUpdated) {
            Message message = new Message();
            message.what = 5;
            sendMessage(message);
        }
    }

    public final void onTaskSnapshotInvalidated(int i) {}

    public final void onTaskStackChanged() {
        if (this.mNeedToNotifyRecentTaskListUpdated) {
            Message message = new Message();
            message.what = 5;
            sendMessage(message);
        }
    }

    public final void onTaskWindowingModeChanged(int i) {}

    public final void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z) {}
}
