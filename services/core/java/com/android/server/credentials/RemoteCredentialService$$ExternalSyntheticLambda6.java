package com.android.server.credentials;

import android.os.IBinder;
import android.service.credentials.ICredentialProviderService;

import java.util.function.Function;

public final /* synthetic */ class RemoteCredentialService$$ExternalSyntheticLambda6
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ICredentialProviderService.Stub.asInterface((IBinder) obj);
    }
}
