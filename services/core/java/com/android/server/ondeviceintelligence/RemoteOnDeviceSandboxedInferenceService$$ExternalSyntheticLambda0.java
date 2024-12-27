package com.android.server.ondeviceintelligence;

import android.os.IBinder;
import android.service.ondeviceintelligence.IOnDeviceSandboxedInferenceService;

import java.util.function.Function;

public final /* synthetic */ class RemoteOnDeviceSandboxedInferenceService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IOnDeviceSandboxedInferenceService.Stub.asInterface((IBinder) obj);
    }
}
