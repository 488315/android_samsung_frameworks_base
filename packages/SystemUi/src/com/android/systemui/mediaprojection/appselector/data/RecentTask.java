package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RecentTask {
    public final ComponentName baseIntentComponent;
    public final Integer colorBackground;
    public final int taskId;
    public final ComponentName topActivityComponent;
    public final int userId;

    public RecentTask(int i, int i2, ComponentName componentName, ComponentName componentName2, Integer num) {
        this.taskId = i;
        this.userId = i2;
        this.topActivityComponent = componentName;
        this.baseIntentComponent = componentName2;
        this.colorBackground = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RecentTask)) {
            return false;
        }
        RecentTask recentTask = (RecentTask) obj;
        return this.taskId == recentTask.taskId && this.userId == recentTask.userId && Intrinsics.areEqual(this.topActivityComponent, recentTask.topActivityComponent) && Intrinsics.areEqual(this.baseIntentComponent, recentTask.baseIntentComponent) && Intrinsics.areEqual(this.colorBackground, recentTask.colorBackground);
    }

    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.userId, Integer.hashCode(this.taskId) * 31, 31);
        ComponentName componentName = this.topActivityComponent;
        int hashCode = (m42m + (componentName == null ? 0 : componentName.hashCode())) * 31;
        ComponentName componentName2 = this.baseIntentComponent;
        int hashCode2 = (hashCode + (componentName2 == null ? 0 : componentName2.hashCode())) * 31;
        Integer num = this.colorBackground;
        return hashCode2 + (num != null ? num.hashCode() : 0);
    }

    public final String toString() {
        return "RecentTask(taskId=" + this.taskId + ", userId=" + this.userId + ", topActivityComponent=" + this.topActivityComponent + ", baseIntentComponent=" + this.baseIntentComponent + ", colorBackground=" + this.colorBackground + ")";
    }
}
