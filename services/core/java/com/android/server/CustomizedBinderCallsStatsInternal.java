package com.android.server;

import android.app.ActivityManagerInternal;
import android.content.Context;

import com.android.internal.os.BinderCallsStats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
