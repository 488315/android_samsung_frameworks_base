package com.android.server.appfunctions;

import android.app.appsearch.AppSearchSession;

import java.util.function.BiConsumer;

public final /* synthetic */ class FutureAppSearchSessionImpl$$ExternalSyntheticLambda2
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AppSearchSession appSearchSession = (AppSearchSession) obj;
        if (appSearchSession != null) {
            appSearchSession.close();
        }
    }
}
