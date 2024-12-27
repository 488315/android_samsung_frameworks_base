package com.android.server.ambientcontext;

import android.os.IBinder;
import android.service.wearable.IWearableSensingService;

import java.util.function.Function;

public final /* synthetic */ class RemoteWearableSensingService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IWearableSensingService.Stub.asInterface((IBinder) obj);
    }
}
