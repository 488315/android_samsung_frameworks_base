package com.android.server.wm;

import android.util.ArraySet;

public final class ActivityServiceConnectionsHolder {
    public final ActivityRecord mActivity;
    public ArraySet mConnections;
    public volatile boolean mIsDisconnecting;

    public ActivityServiceConnectionsHolder(ActivityRecord activityRecord) {
        this.mActivity = activityRecord;
    }

    public final String toString() {
        return String.valueOf(this.mConnections);
    }
}
