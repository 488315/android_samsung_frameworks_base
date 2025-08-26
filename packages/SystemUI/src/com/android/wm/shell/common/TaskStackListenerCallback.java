package com.android.wm.shell.common;

import android.app.ActivityManager;

/* loaded from: classes3.dex */
public interface TaskStackListenerCallback {
    default void onTaskMovedToFront() {
    }

    default void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        onTaskMovedToFront();
    }

    default void onActivityDismissingSplitTask(String str) {
    }

    default void onRecentTaskRemoved(int i) {
    }

    default void onRecentTaskRemovedForAddTask(int i) {
    }

    default void onActivityUnpinned() {
    }

    default void onLockTaskModeChanged() {
    }

    default void onRecentTaskListUpdated() {
    }

    default void onTaskCreated() {
    }

    default void onTaskStackChanged() {
    }

    default void onActivityPinned(int i, String str) {
    }

    default void onActivityRequestedOrientationChanged(int i, int i2) {
    }

    default void onActivityForcedResizable(String str, int i, int i2) {
    }

    default void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
    }
}
