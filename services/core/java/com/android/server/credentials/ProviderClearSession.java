package com.android.server.credentials;

import android.credentials.ClearCredentialStateException;
import android.credentials.selection.ProviderData;
import android.credentials.selection.ProviderPendingIntentResponse;
import android.os.ICancellationSignal;
import android.service.credentials.ClearCredentialStateRequest;
import android.util.Slog;
import com.android.server.credentials.ProviderSession;
import com.android.server.credentials.metrics.ProviderSessionMetric;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProviderClearSession extends ProviderSession {
    @Override // com.android.server.credentials.ProviderSession
    public final void invokeSession() {
        RemoteCredentialService remoteCredentialService = this.mRemoteCredentialService;
        if (remoteCredentialService != null) {
            startCandidateMetrics();
            remoteCredentialService.setCallback(this);
            remoteCredentialService.onClearCredentialState((ClearCredentialStateRequest) this.mProviderRequest);
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderCancellable(ICancellationSignal iCancellationSignal) {
        this.mProviderCancellationSignal = iCancellationSignal;
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseFailure(Exception exc) {
        boolean z = exc instanceof ClearCredentialStateException;
        ProviderSessionMetric providerSessionMetric = this.mProviderSessionMetric;
        if (z) {
            providerSessionMetric.collectCandidateFrameworkException(((ClearCredentialStateException) exc).getType());
        }
        providerSessionMetric.collectCandidateExceptionStatus();
        updateStatusAndInvokeCallback(ProviderSession.Status.CANCELED, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderResponseSuccess(Object obj) {
        Slog.i("CredentialManager", "Remote provider responded with a valid response: " + this.mComponentName);
        this.mProviderResponseSet = Boolean.TRUE;
        updateStatusAndInvokeCallback(ProviderSession.Status.COMPLETE, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public final void onProviderServiceDied(RemoteCredentialService remoteCredentialService) {
        if (remoteCredentialService.getComponentName().equals(this.mComponentName)) {
            updateStatusAndInvokeCallback(ProviderSession.Status.SERVICE_DEAD, ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            Slog.w("CredentialManager", "Component names different in onProviderServiceDied - this should not happen");
        }
    }

    @Override // com.android.server.credentials.ProviderSession
    public final void onUiEntrySelected(String str, String str2, ProviderPendingIntentResponse providerPendingIntentResponse) {
    }

    @Override // com.android.server.credentials.ProviderSession
    public final ProviderData prepareUiData() {
        return null;
    }
}
