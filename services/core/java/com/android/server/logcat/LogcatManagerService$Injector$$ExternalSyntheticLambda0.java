package com.android.server.logcat;

import android.os.SystemClock;

import java.util.function.Supplier;

public final /* synthetic */ class LogcatManagerService$Injector$$ExternalSyntheticLambda0
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(SystemClock.uptimeMillis());
    }
}
