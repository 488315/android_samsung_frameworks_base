package com.android.wm.shell.taskview;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.Rect;
import android.window.WindowContainerToken;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface TaskViewController {
    boolean isUsingShellTransitions();

    void moveTaskViewToFullscreen(TaskViewTaskController taskViewTaskController);

    void registerTaskView(TaskViewTaskController taskViewTaskController);

    void removeTaskView(TaskViewTaskController taskViewTaskController, WindowContainerToken windowContainerToken);

    void setTaskBounds(TaskViewTaskController taskViewTaskController, Rect rect);

    void setTaskViewVisible(TaskViewTaskController taskViewTaskController, boolean z);

    void startActivity(TaskViewTaskController taskViewTaskController, PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, Rect rect);

    void startShortcutActivity(TaskViewTaskController taskViewTaskController, ShortcutInfo shortcutInfo, ActivityOptions activityOptions, Rect rect);

    void unregisterTaskView(TaskViewTaskController taskViewTaskController);
}
