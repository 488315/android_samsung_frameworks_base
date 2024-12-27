package com.android.server;

import com.android.server.utils.TimingsTraceAndSlog;

public abstract /* synthetic */ class SystemServer$$ExternalSyntheticOutline0 {
    public static void m(
            SystemServiceManager systemServiceManager,
            Class cls,
            TimingsTraceAndSlog timingsTraceAndSlog,
            String str) {
        systemServiceManager.startService(cls);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin(str);
    }
}
