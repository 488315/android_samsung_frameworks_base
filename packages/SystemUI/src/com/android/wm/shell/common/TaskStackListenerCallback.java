package com.android.wm.shell.common;

import android.app.ActivityManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
