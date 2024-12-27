package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.content.ComponentName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
