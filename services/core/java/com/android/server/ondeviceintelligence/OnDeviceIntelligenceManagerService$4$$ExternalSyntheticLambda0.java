package com.android.server.ondeviceintelligence;

import android.service.ondeviceintelligence.IOnDeviceIntelligenceService;

import com.android.internal.infra.ServiceConnector;

public final /* synthetic */ class OnDeviceIntelligenceManagerService$4$$ExternalSyntheticLambda0
        implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        ((IOnDeviceIntelligenceService) obj).notifyInferenceServiceConnected();
    }
}
