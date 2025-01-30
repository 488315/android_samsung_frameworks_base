package com.android.wm.shell.common;

import android.app.ActivityManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface TaskStackListenerCallback {
    default void onActivityDismissingSplitTask(String str) {
    }

    default void onTaskMovedToFront(int i) {
    }

    default void onActivityUnpinned() {
    }

    default void onRecentTaskListUpdated() {
    }

    default void onTaskCreated() {
    }

    default void onTaskStackChanged() {
    }

    default void onActivityPinned(String str, int i) {
    }

    default void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
    }

    default void onActivityForcedResizable(String str, int i, int i2) {
    }
}
