package com.android.server.credentials;

import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Slog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RequestSession$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ProviderSession providerSession = (ProviderSession) obj;
        providerSession.getClass();
        try {
            ICancellationSignal iCancellationSignal = providerSession.mProviderCancellationSignal;
            if (iCancellationSignal != null) {
                iCancellationSignal.cancel();
            }
            providerSession.mStatus = ProviderSession.Status.CANCELED;
        } catch (RemoteException e) {
            Slog.e("CredentialManager", "Issue while cancelling provider session: ", e);
        }
    }
}
