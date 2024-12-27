package com.android.server.appfunctions;

import android.app.appsearch.AppSearchSession;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FutureAppSearchSessionImpl$$ExternalSyntheticLambda2 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AppSearchSession appSearchSession = (AppSearchSession) obj;
        if (appSearchSession != null) {
            appSearchSession.close();
        }
    }
}
