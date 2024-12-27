package com.android.server.appfunctions;

import android.app.appsearch.GlobalSearchSession;

import java.util.function.BiConsumer;

public final /* synthetic */ class FutureGlobalSearchSession$$ExternalSyntheticLambda1
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        GlobalSearchSession globalSearchSession = (GlobalSearchSession) obj;
        if (globalSearchSession != null) {
            globalSearchSession.close();
        }
    }
}
