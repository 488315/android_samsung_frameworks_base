package com.android.server.appfunctions;

import android.util.Slog;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppFunctionManagerServiceImpl$$ExternalSyntheticLambda1
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        if (((Throwable) obj2) == null && bool.booleanValue()) {
            return;
        }
        Slog.e("AppFunctionManagerServiceImpl", "Sync was not successful");
    }
}
