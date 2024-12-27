package com.android.server.usage;

import com.android.server.ServiceThread;

public final class UsageStatsHandlerThread extends ServiceThread {
    public static UsageStatsHandlerThread sInstance;
    public static final Object sLock = new Object();
}
