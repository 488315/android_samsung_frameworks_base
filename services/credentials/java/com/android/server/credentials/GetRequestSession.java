package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.credentials.CredentialOption;
import android.credentials.CredentialProviderInfo;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.credentials.IGetCredentialCallback;
import android.credentials.ui.RequestInfo;
import android.credentials.ui.UserSelectionDialogResult;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.PermissionUtils;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;
import com.android.server.credentials.CredentialManagerUi;
import com.android.server.credentials.ProviderSession;
import com.android.server.credentials.RequestSession;
import com.android.server.credentials.metrics.ProviderStatusForMetrics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class GetRequestSession extends RequestSession implements ProviderSession.ProviderInternalCallback {
    public Set mPrimaryProviders;

    @Override // com.android.server.credentials.RequestSession
    public /* bridge */ /* synthetic */ void addProviderSession(ComponentName componentName, ProviderSession providerSession) {
        super.addProviderSession(componentName, providerSession);
    }

    @Override // com.android.server.credentials.RequestSession, com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public /* bridge */ /* synthetic */ void onUiSelection(UserSelectionDialogResult userSelectionDialogResult) {
        super.onUiSelection(userSelectionDialogResult);
    }

    public GetRequestSession(Context context, RequestSession.SessionLifetime sessionLifetime, Object obj, int i, int i2, IGetCredentialCallback iGetCredentialCallback, GetCredentialRequest getCredentialRequest, CallingAppInfo callingAppInfo, Set set, Set set2, CancellationSignal cancellationSignal, long j) {
        super(context, sessionLifetime, obj, i, i2, getCredentialRequest, iGetCredentialCallback, getRequestInfoFromRequest(getCredentialRequest), callingAppInfo, set, cancellationSignal, j);
        this.mRequestSessionMetric.collectGetFlowInitialMetricInfo(getCredentialRequest);
        this.mPrimaryProviders = set2;
    }

    public static String getRequestInfoFromRequest(GetCredentialRequest getCredentialRequest) {
        Iterator<CredentialOption> it = getCredentialRequest.getCredentialOptions().iterator();
        while (it.hasNext()) {
            if (it.next().getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS") != null) {
                return "android.credentials.ui.TYPE_GET_VIA_REGISTRY";
            }
        }
        return "android.credentials.ui.TYPE_GET";
    }

    @Override // com.android.server.credentials.RequestSession
    public ProviderSession initiateProviderSession(CredentialProviderInfo credentialProviderInfo, RemoteCredentialService remoteCredentialService) {
        ProviderGetSession createNewSession = ProviderGetSession.createNewSession(this.mContext, this.mUserId, credentialProviderInfo, this, remoteCredentialService);
        if (createNewSession != null) {
            Slog.i("GetRequestSession", "Provider session created and being added for: " + credentialProviderInfo.getComponentName());
            this.mProviders.put(createNewSession.getComponentName().flattenToString(), createNewSession);
        }
        return createNewSession;
    }

    @Override // com.android.server.credentials.RequestSession
    public void launchUiWithProviderData(final ArrayList arrayList) {
        this.mRequestSessionMetric.collectUiCallStartTime(System.nanoTime());
        this.mCredentialManagerUi.setStatus(CredentialManagerUi.UiStatus.USER_INTERACTION);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.credentials.GetRequestSession$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                GetRequestSession.this.lambda$launchUiWithProviderData$0(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$launchUiWithProviderData$0(ArrayList arrayList) {
        try {
            cancelExistingPendingIntent();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = this.mPrimaryProviders.iterator();
            while (it.hasNext()) {
                arrayList2.add(((ComponentName) it.next()).flattenToString());
            }
            PendingIntent createPendingIntent = this.mCredentialManagerUi.createPendingIntent(RequestInfo.newGetRequestInfo(this.mRequestId, (GetCredentialRequest) this.mClientRequest, this.mClientAppInfo.getPackageName(), PermissionUtils.hasPermission(this.mContext, this.mClientAppInfo.getPackageName(), "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"), arrayList2), arrayList);
            this.mPendingIntent = createPendingIntent;
            ((IGetCredentialCallback) this.mClientCallback).onPendingIntent(createPendingIntent);
        } catch (RemoteException unused) {
            this.mRequestSessionMetric.collectUiReturnedFinalPhase(false);
            this.mCredentialManagerUi.setStatus(CredentialManagerUi.UiStatus.TERMINATED);
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_UNKNOWN");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_UNKNOWN", "Unable to instantiate selector");
        }
    }

    @Override // com.android.server.credentials.RequestSession
    public void invokeClientCallbackSuccess(GetCredentialResponse getCredentialResponse) {
        ((IGetCredentialCallback) this.mClientCallback).onResponse(getCredentialResponse);
    }

    @Override // com.android.server.credentials.RequestSession
    public void invokeClientCallbackError(String str, String str2) {
        ((IGetCredentialCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalResponseReceived(ComponentName componentName, GetCredentialResponse getCredentialResponse) {
        Slog.i("GetRequestSession", "onFinalResponseReceived from: " + componentName.flattenToString());
        this.mRequestSessionMetric.collectUiResponseData(true, System.nanoTime());
        this.mRequestSessionMetric.updateMetricsOnResponseReceived(this.mProviders, componentName, isPrimaryProviderViaProviderInfo(componentName));
        if (getCredentialResponse != null) {
            this.mRequestSessionMetric.collectChosenProviderStatus(ProviderStatusForMetrics.FINAL_SUCCESS.getMetricCode());
            respondToClientWithResponseAndFinish(getCredentialResponse);
        } else {
            this.mRequestSessionMetric.collectChosenProviderStatus(ProviderStatusForMetrics.FINAL_FAILURE.getMetricCode());
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "Invalid response from provider");
        }
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalErrorReceived(ComponentName componentName, String str, String str2) {
        respondToClientWithErrorAndFinish(str, str2);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiCancellation(boolean z) {
        String str;
        String str2;
        if (z) {
            str = "android.credentials.GetCredentialException.TYPE_USER_CANCELED";
            str2 = "User cancelled the selector";
        } else {
            str = "android.credentials.GetCredentialException.TYPE_INTERRUPTED";
            str2 = "The UI was interrupted - please try again.";
        }
        this.mRequestSessionMetric.collectFrameworkException(str);
        respondToClientWithErrorAndFinish(str, str2);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
        respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available.");
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(ProviderSession.Status status, ComponentName componentName, ProviderSession.CredentialsSource credentialsSource) {
        Slog.i("GetRequestSession", "Status changed for: " + componentName + ", with status: " + status + ", and source: " + credentialsSource);
        if (status == ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY) {
            handleEmptyAuthenticationSelection(componentName);
            return;
        }
        if (isAnyProviderPending()) {
            return;
        }
        if (isUiInvocationNeeded()) {
            Slog.i("GetRequestSession", "Provider status changed - ui invocation is needed");
            getProviderDataAndInitiateUi();
        } else {
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available");
        }
    }

    public void handleEmptyAuthenticationSelection(final ComponentName componentName) {
        this.mProviders.keySet().forEach(new Consumer() { // from class: com.android.server.credentials.GetRequestSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GetRequestSession.this.lambda$handleEmptyAuthenticationSelection$1(componentName, (String) obj);
            }
        });
        getProviderDataAndInitiateUi();
        if (providerDataContainsEmptyAuthEntriesOnly()) {
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEmptyAuthenticationSelection$1(ComponentName componentName, String str) {
        ProviderGetSession providerGetSession = (ProviderGetSession) this.mProviders.get(str);
        if (providerGetSession.mComponentName.equals(componentName)) {
            return;
        }
        providerGetSession.updateAuthEntriesStatusFromAnotherSession();
    }

    public final boolean providerDataContainsEmptyAuthEntriesOnly() {
        Iterator it = this.mProviders.keySet().iterator();
        while (it.hasNext()) {
            if (!((ProviderGetSession) this.mProviders.get((String) it.next())).containsEmptyAuthEntriesOnly()) {
                return false;
            }
        }
        return true;
    }
}
