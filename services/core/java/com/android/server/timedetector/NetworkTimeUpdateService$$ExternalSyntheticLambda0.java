package com.android.server.timedetector;

import android.os.SystemClock;

import java.util.function.Supplier;

public final /* synthetic */ class NetworkTimeUpdateService$$ExternalSyntheticLambda0
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(SystemClock.elapsedRealtime());
    }
}
