package com.android.server.appfunctions;

import android.util.Slog;

import java.util.function.BiConsumer;

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
