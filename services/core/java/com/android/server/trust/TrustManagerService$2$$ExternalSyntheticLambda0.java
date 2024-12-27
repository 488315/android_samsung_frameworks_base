package com.android.server.trust;

import android.hardware.location.ISignificantPlaceProvider;
import android.os.IBinder;

import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TrustManagerService$2$$ExternalSyntheticLambda0
        implements ServiceWatcher$BinderOperation {
    @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
    public final void run(IBinder iBinder) {
        ISignificantPlaceProvider.Stub.asInterface(iBinder).onSignificantPlaceCheck();
    }
}
