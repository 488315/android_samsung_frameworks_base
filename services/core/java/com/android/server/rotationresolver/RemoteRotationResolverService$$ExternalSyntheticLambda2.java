package com.android.server.rotationresolver;

import android.os.IBinder;
import android.service.rotationresolver.IRotationResolverService;

import java.util.function.Function;

public final /* synthetic */ class RemoteRotationResolverService$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IRotationResolverService.Stub.asInterface((IBinder) obj);
    }
}
