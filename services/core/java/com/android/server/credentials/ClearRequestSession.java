package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.credentials.CredentialProviderInfo;
import android.credentials.IClearCredentialStateCallback;
import android.service.credentials.ClearCredentialStateRequest;
import android.util.Slog;

import com.android.server.credentials.metrics.RequestSessionMetric;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ClearRequestSession extends RequestSession
        implements ProviderSession.ProviderInternalCallback {
    @Override // com.android.server.credentials.RequestSession
    public final ProviderSession initiateProviderSession(
            CredentialProviderInfo credentialProviderInfo,
            RemoteCredentialService remoteCredentialService) {
        Context context = this.mContext;
        ClearCredentialStateRequest clearCredentialStateRequest =
                new ClearCredentialStateRequest(
                        this.mClientAppInfo,
                        ((android.credentials.ClearCredentialStateRequest) this.mClientRequest)
                                .getData());
        ComponentName componentName = credentialProviderInfo.getComponentName();
        ProviderClearSession providerClearSession =
                new ProviderClearSession(
                        context,
                        clearCredentialStateRequest,
                        this,
                        componentName,
                        this.mUserId,
                        remoteCredentialService);
        providerClearSession.mStatus = ProviderSession.Status.PENDING;
        Slog.i(
                "CredentialManager",
                "Provider session created and being added for: "
                        + credentialProviderInfo.getComponentName());
        ((ConcurrentHashMap) this.mProviders)
                .put(componentName.flattenToString(), providerClearSession);
        return providerClearSession;
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackError(String str, String str2) {
        ((IClearCredentialStateCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackSuccess(Object obj) {
        ((IClearCredentialStateCallback) this.mClientCallback).onSuccess();
    }

    @Override // com.android.server.credentials.RequestSession
    public final void launchUiWithProviderData(ArrayList arrayList) {}

    @Override // com.android.server.credentials.RequestSession,
              // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onFinalErrorReceived(String str, String str2) {}

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onFinalResponseReceived(ComponentName componentName, Object obj) {
        long nanoTime = System.nanoTime();
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.collectUiResponseData(nanoTime);
        Map map = this.mProviders;
        requestSessionMetric.updateMetricsOnResponseReceived(map, componentName);
        respondToClientWithResponseAndFinish(null);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onProviderStatusChanged(
            ProviderSession.Status status,
            ComponentName componentName,
            ProviderSession.CredentialsSource credentialsSource) {
        Slog.i(
                "CredentialManager",
                "Provider changed with status: " + status + ", and source: " + credentialsSource);
        boolean z =
                status == ProviderSession.Status.CANCELED
                        || status == ProviderSession.Status.SERVICE_DEAD;
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        if (!z) {
            if (status == ProviderSession.Status.COMPLETE
                    || status == ProviderSession.Status.EMPTY_RESPONSE) {
                Slog.i("CredentialManager", "Provider has completion status");
                if (isAnyProviderPending()) {
                    return;
                }
                requestSessionMetric.collectUiResponseData(System.nanoTime());
                Map map = this.mProviders;
                requestSessionMetric.updateMetricsOnResponseReceived(map, componentName);
                respondToClientWithResponseAndFinish(null);
                return;
            }
            return;
        }
        Slog.i("CredentialManager", "Provider terminating status");
        if (isAnyProviderPending()) {
            return;
        }
        for (ProviderSession providerSession : ((ConcurrentHashMap) this.mProviders).values()) {
            if (providerSession.mProviderResponse != null
                    || providerSession.mProviderResponseSet.booleanValue()) {
                respondToClientWithResponseAndFinish(null);
                return;
            }
        }
        requestSessionMetric.collectFrameworkException(
                "android.credentials.ClearCredentialStateException.TYPE_UNKNOWN");
        respondToClientWithErrorAndFinish(
                "android.credentials.ClearCredentialStateException.TYPE_UNKNOWN",
                "All providers failed");
    }

    @Override // com.android.server.credentials.RequestSession
    public final void onUiCancellation(boolean z) {}

    @Override // com.android.server.credentials.RequestSession
    public final void onUiSelectorInvocationFailure() {}
}
