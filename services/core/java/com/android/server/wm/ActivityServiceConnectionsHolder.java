package com.android.server.wm;

import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
