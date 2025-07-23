package com.android.wm.shell.compatui.api;

import android.app.TaskInfo;
import com.android.wm.shell.ShellTaskOrganizer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CompatUIInfo {
    public final ShellTaskOrganizer.TaskListener listener;
    public final TaskInfo taskInfo;

    public CompatUIInfo(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        this.taskInfo = taskInfo;
        this.listener = taskListener;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompatUIInfo)) {
            return false;
        }
        CompatUIInfo compatUIInfo = (CompatUIInfo) obj;
        return Intrinsics.areEqual(this.taskInfo, compatUIInfo.taskInfo) && Intrinsics.areEqual(this.listener, compatUIInfo.listener);
    }

    public final int hashCode() {
        int hashCode = this.taskInfo.hashCode() * 31;
        ShellTaskOrganizer.TaskListener taskListener = this.listener;
        return hashCode + (taskListener == null ? 0 : taskListener.hashCode());
    }

    public final String toString() {
        return "CompatUIInfo(taskInfo=" + this.taskInfo + ", listener=" + this.listener + ")";
    }
}
