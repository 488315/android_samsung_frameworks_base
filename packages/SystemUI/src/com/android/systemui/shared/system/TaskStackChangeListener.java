package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.content.ComponentName;

public interface TaskStackChangeListener {
    default void onTaskMovedToFront() {
    }

    default void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        onTaskMovedToFront();
    }

    default void onTaskFocusChanged() {
    }

    default void onTaskRemoved() {
    }

    default void onTaskStackChanged() {
    }

    default void onTaskStackChangedBackground() {
    }

    default void onTaskWindowingModeChanged() {
    }

    default void onActivityRequestedOrientationChanged(int i) {
    }

    default void onLockTaskModeChanged(int i) {
    }

    default void onTaskCreated(ComponentName componentName) {
    }

    default void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
    }
}
