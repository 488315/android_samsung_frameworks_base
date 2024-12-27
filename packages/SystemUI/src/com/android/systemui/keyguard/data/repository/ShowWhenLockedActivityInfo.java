package com.android.systemui.keyguard.data.repository;

import android.app.ActivityManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShowWhenLockedActivityInfo {
    public final boolean isOnTop;
    public final ActivityManager.RunningTaskInfo taskInfo;

    public ShowWhenLockedActivityInfo(boolean z, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.isOnTop = z;
        this.taskInfo = runningTaskInfo;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShowWhenLockedActivityInfo)) {
            return false;
        }
        ShowWhenLockedActivityInfo showWhenLockedActivityInfo = (ShowWhenLockedActivityInfo) obj;
        return this.isOnTop == showWhenLockedActivityInfo.isOnTop && Intrinsics.areEqual(this.taskInfo, showWhenLockedActivityInfo.taskInfo);
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.isOnTop) * 31;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.taskInfo;
        return hashCode + (runningTaskInfo == null ? 0 : runningTaskInfo.hashCode());
    }

    public final String toString() {
        return "ShowWhenLockedActivityInfo(isOnTop=" + this.isOnTop + ", taskInfo=" + this.taskInfo + ")";
    }

    public /* synthetic */ ShowWhenLockedActivityInfo(boolean z, ActivityManager.RunningTaskInfo runningTaskInfo, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? null : runningTaskInfo);
    }
}
