package com.android.server;

import android.app.ActivityManagerInternal;
import android.content.Context;

import com.android.internal.os.BinderCallsStats;

public final class CustomizedBinderCallsStatsInternal {
    public ActivityManagerInternal mAm;
    public final BinderCallsStats mBinderCallsStats;
    public final Context mContext;
    public long mLastWriteTime = 0;
    public long mLastStoreTime = 0;
    public long mLastNotifyTime = 0;

    public CustomizedBinderCallsStatsInternal(BinderCallsStats binderCallsStats, Context context) {
        this.mBinderCallsStats = binderCallsStats;
        this.mContext = context;
    }
}
