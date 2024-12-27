package com.android.server.appwidget;

import android.os.SystemClock;

import java.util.function.LongSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppWidgetServiceImpl$ApiCounter$$ExternalSyntheticLambda0
        implements LongSupplier {
    @Override // java.util.function.LongSupplier
    public final long getAsLong() {
        return SystemClock.elapsedRealtime();
    }
}
