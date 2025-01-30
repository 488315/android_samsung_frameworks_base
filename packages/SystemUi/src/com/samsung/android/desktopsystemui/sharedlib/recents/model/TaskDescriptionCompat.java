package com.samsung.android.desktopsystemui.sharedlib.recents.model;

import android.app.ActivityManager;
import android.graphics.Bitmap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TaskDescriptionCompat {
    private ActivityManager.TaskDescription mTaskDescription;

    public TaskDescriptionCompat(ActivityManager.TaskDescription taskDescription) {
        this.mTaskDescription = taskDescription;
    }

    public static Bitmap getIcon(ActivityManager.TaskDescription taskDescription, int i) {
        return taskDescription.getInMemoryIcon() != null ? taskDescription.getInMemoryIcon() : ActivityManager.TaskDescription.loadTaskDescriptionIcon(taskDescription.getIconFilename(), i);
    }

    public int getBackgroundColor() {
        ActivityManager.TaskDescription taskDescription = this.mTaskDescription;
        if (taskDescription != null) {
            return taskDescription.getBackgroundColor();
        }
        return 0;
    }

    public String getLabel() {
        ActivityManager.TaskDescription taskDescription = this.mTaskDescription;
        return taskDescription != null ? taskDescription.getLabel() : "";
    }

    public int getPrimaryColor() {
        ActivityManager.TaskDescription taskDescription = this.mTaskDescription;
        if (taskDescription != null) {
            return taskDescription.getPrimaryColor();
        }
        return 0;
    }
}
