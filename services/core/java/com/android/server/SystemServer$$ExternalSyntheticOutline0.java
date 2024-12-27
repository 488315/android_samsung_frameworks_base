package com.android.server;

import com.android.server.utils.TimingsTraceAndSlog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class SystemServer$$ExternalSyntheticOutline0 {
    public static void m(SystemServiceManager systemServiceManager, Class cls, TimingsTraceAndSlog timingsTraceAndSlog, String str) {
        systemServiceManager.startService(cls);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin(str);
    }
}
