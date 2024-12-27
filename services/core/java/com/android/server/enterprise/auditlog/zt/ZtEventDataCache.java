package com.android.server.enterprise.auditlog.zt;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ZtEventDataCache {
    public static final ConcurrentLinkedQueue ZT_EVENT_CACHE = new ConcurrentLinkedQueue();
}
