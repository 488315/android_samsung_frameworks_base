package com.android.server.trust;

import android.hardware.location.ISignificantPlaceProvider;
import android.os.IBinder;

import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;

public final /* synthetic */ class TrustManagerService$2$$ExternalSyntheticLambda0
        implements ServiceWatcher$BinderOperation {
    @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
    public final void run(IBinder iBinder) {
        ISignificantPlaceProvider.Stub.asInterface(iBinder).onSignificantPlaceCheck();
    }
}
