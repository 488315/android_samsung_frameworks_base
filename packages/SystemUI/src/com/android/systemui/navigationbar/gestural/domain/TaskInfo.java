package com.android.systemui.navigationbar.gestural.domain;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TaskInfo {
    public final ComponentName topActivity;
    public final int topActivityType;

    public TaskInfo(ComponentName componentName, int i) {
        this.topActivity = componentName;
        this.topActivityType = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof TaskInfo)) {
            return false;
        }
        TaskInfo taskInfo = (TaskInfo) obj;
        return taskInfo.topActivityType == this.topActivityType && Intrinsics.areEqual(taskInfo.topActivity, this.topActivity);
    }

    public final int hashCode() {
        ComponentName componentName = this.topActivity;
        return Integer.hashCode(this.topActivityType) + ((componentName == null ? 0 : componentName.hashCode()) * 31);
    }

    public final String toString() {
        return "TaskInfo(topActivity=" + this.topActivity + ", topActivityType=" + this.topActivityType + ")";
    }
}
