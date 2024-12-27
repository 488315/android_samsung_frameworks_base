package com.android.server.appwidget;

import android.os.SystemClock;

import java.util.function.LongSupplier;

public final /* synthetic */ class AppWidgetServiceImpl$ApiCounter$$ExternalSyntheticLambda0
        implements LongSupplier {
    @Override // java.util.function.LongSupplier
    public final long getAsLong() {
        return SystemClock.elapsedRealtime();
    }
}
