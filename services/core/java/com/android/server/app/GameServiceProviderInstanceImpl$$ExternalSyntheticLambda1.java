package com.android.server.app;

import android.service.games.IGameService;

import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GameServiceProviderInstanceImpl$$ExternalSyntheticLambda1
        implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        ((IGameService) obj).disconnected();
    }
}
