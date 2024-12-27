package com.android.server.ondeviceintelligence;

import android.service.ondeviceintelligence.IOnDeviceIntelligenceService;

import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OnDeviceIntelligenceManagerService$4$$ExternalSyntheticLambda0
        implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        ((IOnDeviceIntelligenceService) obj).notifyInferenceServiceConnected();
    }
}
