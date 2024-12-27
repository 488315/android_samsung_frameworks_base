package com.android.server.ondeviceintelligence;

import android.os.IBinder;
import android.service.ondeviceintelligence.IOnDeviceIntelligenceService;

import java.util.function.Function;

public final /* synthetic */ class RemoteOnDeviceIntelligenceService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IOnDeviceIntelligenceService.Stub.asInterface((IBinder) obj);
    }
}
