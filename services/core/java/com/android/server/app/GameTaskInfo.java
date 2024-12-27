package com.android.server.app;

import android.content.ComponentName;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GameTaskInfo {
    public final ComponentName mComponentName;
    public final boolean mIsGameTask;
    public final int mTaskId;

    public GameTaskInfo(int i, ComponentName componentName, boolean z) {
        this.mTaskId = i;
        this.mIsGameTask = z;
        this.mComponentName = componentName;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameTaskInfo)) {
            return false;
        }
        GameTaskInfo gameTaskInfo = (GameTaskInfo) obj;
        return this.mTaskId == gameTaskInfo.mTaskId && this.mIsGameTask == gameTaskInfo.mIsGameTask && this.mComponentName.equals(gameTaskInfo.mComponentName);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTaskId), Boolean.valueOf(this.mIsGameTask), this.mComponentName);
    }

    public final String toString() {
        return "GameTaskInfo{mTaskId=" + this.mTaskId + ", mIsGameTask=" + this.mIsGameTask + ", mComponentName=" + this.mComponentName + '}';
    }
}
