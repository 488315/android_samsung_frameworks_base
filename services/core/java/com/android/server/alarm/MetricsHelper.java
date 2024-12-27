package com.android.server.alarm;

import android.content.Context;

public final class MetricsHelper {
    public final Context mContext;
    public final Object mLock;

    public MetricsHelper(Context context, Object obj) {
        this.mContext = context;
        this.mLock = obj;
    }
}
