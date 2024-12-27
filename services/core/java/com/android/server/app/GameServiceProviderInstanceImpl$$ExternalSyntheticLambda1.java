package com.android.server.app;

import android.service.games.IGameService;

import com.android.internal.infra.ServiceConnector;

public final /* synthetic */ class GameServiceProviderInstanceImpl$$ExternalSyntheticLambda1
        implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        ((IGameService) obj).disconnected();
    }
}
