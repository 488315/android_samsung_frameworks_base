package com.android.server.logcat;

import android.os.SystemClock;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LogcatManagerService$Injector$$ExternalSyntheticLambda0
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(SystemClock.uptimeMillis());
    }
}
