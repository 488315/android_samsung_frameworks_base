package com.android.server.credentials;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.credentials.CredentialProviderInfo;
import android.credentials.GetCandidateCredentialsResponse;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.credentials.IGetCandidateCredentialsCallback;
import android.credentials.selection.IntentCreationResult;
import android.credentials.selection.IntentFactory;
import android.credentials.selection.ProviderData;
import android.credentials.selection.RequestInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.credentials.BeginGetCredentialRequest;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.PermissionUtils;
import android.util.Slog;

import com.android.server.credentials.metrics.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GetCandidateRequestSession extends RequestSession
        implements ProviderSession.ProviderInternalCallback {
    public final ResultReceiver mAutofillCallback;
    public final int mAutofillRequestId;
    public final int mAutofillSessionId;
    public final IBinder mClientBinder;
    public ComponentName mPrimaryProviderComponentName;

    public GetCandidateRequestSession(
            Context context,
            CredentialManagerService.SessionManager sessionManager,
            Object obj,
            int i,
            int i2,
            IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback,
            GetCredentialRequest getCredentialRequest,
            CallingAppInfo callingAppInfo,
            Set set,
            CancellationSignal cancellationSignal,
            IBinder iBinder) {
        super(
                context,
                sessionManager,
                obj,
                i,
                i2,
                getCredentialRequest,
                iGetCandidateCredentialsCallback,
                "android.credentials.selection.TYPE_GET",
                callingAppInfo,
                set,
                cancellationSignal,
                0L,
                false);
        this.mPrimaryProviderComponentName = null;
        this.mClientBinder = iBinder;
        this.mAutofillSessionId = getCredentialRequest.getData().getInt("autofill_session_id", -1);
        this.mAutofillRequestId = getCredentialRequest.getData().getInt("autofill_request_id", -1);
        this.mAutofillCallback =
                (ResultReceiver)
                        getCredentialRequest
                                .getData()
                                .getParcelable(
                                        "android.credentials.AUTOFILL_RESULT_RECEIVER",
                                        ResultReceiver.class);
        if (iBinder != null) {
            setUpClientCallbackListener(iBinder);
        }
    }

    @Override // com.android.server.credentials.RequestSession
    public final ProviderSession initiateProviderSession(
            CredentialProviderInfo credentialProviderInfo,
            RemoteCredentialService remoteCredentialService) {
        ProviderGetSession providerGetSession;
        Context context = this.mContext;
        List capabilities = credentialProviderInfo.getCapabilities();
        GetCredentialRequest getCredentialRequest = (GetCredentialRequest) this.mClientRequest;
        GetCredentialRequest filterOptions =
                ProviderGetSession.filterOptions(
                        capabilities,
                        getCredentialRequest,
                        credentialProviderInfo,
                        this.mHybridService);
        if (filterOptions != null) {
            HashMap hashMap = new HashMap();
            CallingAppInfo callingAppInfo = this.mClientAppInfo;
            boolean alwaysSendAppInfoToProvider =
                    getCredentialRequest.alwaysSendAppInfoToProvider();
            BeginGetCredentialRequest.Builder builder = new BeginGetCredentialRequest.Builder();
            filterOptions
                    .getCredentialOptions()
                    .forEach(new ProviderGetSession$$ExternalSyntheticLambda0(builder, hashMap));
            if (alwaysSendAppInfoToProvider) {
                builder.setCallingAppInfo(callingAppInfo);
            }
            providerGetSession =
                    new ProviderGetSession(
                            context,
                            credentialProviderInfo,
                            this,
                            this.mUserId,
                            remoteCredentialService,
                            builder.build(),
                            filterOptions,
                            this.mClientAppInfo,
                            hashMap,
                            this.mHybridService);
        } else {
            Slog.i(
                    "CredentialManager",
                    "Unable to create provider session for: "
                            + credentialProviderInfo.getComponentName());
            providerGetSession = null;
        }
        if (providerGetSession != null) {
            Slog.d(
                    "CredentialManager",
                    "In startProviderSession - provider session created and being added for: "
                            + credentialProviderInfo.getComponentName());
            ComponentName componentName = providerGetSession.mComponentName;
            if (credentialProviderInfo.isPrimary()) {
                this.mPrimaryProviderComponentName = componentName;
            }
            ((ConcurrentHashMap) this.mProviders)
                    .put(componentName.flattenToString(), providerGetSession);
        }
        return providerGetSession;
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackError(String str, String str2) {
        ((IGetCandidateCredentialsCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackSuccess(Object obj) {
        ((IGetCandidateCredentialsCallback) this.mClientCallback)
                .onResponse((GetCandidateCredentialsResponse) obj);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void launchUiWithProviderData(ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            respondToClientWithErrorAndFinish(
                    "android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL",
                    "No credentials found");
            return;
        }
        RequestInfo newGetRequestInfo =
                RequestInfo.newGetRequestInfo(
                        this.mRequestId,
                        (GetCredentialRequest) this.mClientRequest,
                        this.mClientAppInfo.getPackageName(),
                        PermissionUtils.hasPermission(
                                this.mContext,
                                this.mClientAppInfo.getPackageName(),
                                "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"),
                        true);
        CredentialManagerUi credentialManagerUi = this.mCredentialManagerUi;
        IntentCreationResult createCredentialSelectorIntentForAutofill =
                IntentFactory.createCredentialSelectorIntentForAutofill(
                        credentialManagerUi.mContext,
                        newGetRequestInfo,
                        new ArrayList(),
                        credentialManagerUi.mResultReceiver);
        this.mRequestSessionMetric.collectUiConfigurationResults(
                credentialManagerUi.mContext,
                createCredentialSelectorIntentForAutofill,
                credentialManagerUi.mUserId);
        Intent intent = createCredentialSelectorIntentForAutofill.getIntent();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((ProviderData) it.next());
        }
        try {
            ((IGetCandidateCredentialsCallback) this.mClientCallback)
                    .onResponse(
                            new GetCandidateCredentialsResponse(
                                    arrayList2, intent, this.mPrimaryProviderComponentName));
        } catch (RemoteException e) {
            Slog.e("CredentialManager", "Issue while responding to client with error : " + e);
        }
    }

    @Override // com.android.server.credentials.RequestSession,
              // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onFinalErrorReceived(String str, String str2) {
        Slog.d("CredentialManager", "onFinalErrorReceived");
        if ("android.credentials.GetCredentialException.TYPE_USER_CANCELED".equals(str)) {
            Slog.d("CredentialManager", "User canceled but session is not being terminated");
            return;
        }
        if (this.mRequestSessionStatus == RequestSession.RequestSessionStatus.COMPLETE) {
            Slog.w("CredentialManager", "Request has already been completed. This is strange.");
            return;
        }
        if (this.mAutofillCallback != null) {
            Bundle bundle = new Bundle();
            bundle.putStringArray(
                    "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION",
                    new String[] {str, str2});
            this.mAutofillCallback.send(-1, bundle);
        } else {
            Slog.w("CredentialManager", "onUiCancellation called but mAutofillCallback not found");
        }
        finishSession(ApiStatus.FAILURE.getMetricCode(), false);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onFinalResponseReceived(ComponentName componentName, Object obj) {
        GetCredentialResponse getCredentialResponse = (GetCredentialResponse) obj;
        Slog.d("CredentialManager", "onFinalResponseReceived");
        RequestSession.RequestSessionStatus requestSessionStatus = this.mRequestSessionStatus;
        RequestSession.RequestSessionStatus requestSessionStatus2 =
                RequestSession.RequestSessionStatus.COMPLETE;
        if (requestSessionStatus == requestSessionStatus2) {
            Slog.w("CredentialManager", "Request has already been completed. This is strange.");
            return;
        }
        if (requestSessionStatus == requestSessionStatus2) {
            Slog.w("CredentialManager", "Request has already been completed. This is strange.");
            return;
        }
        if (this.mAutofillCallback == null) {
            Slog.w(
                    "CredentialManager",
                    "onFinalResponseReceived result receiver not found for pinned entry");
            finishSession(ApiStatus.FAILURE.getMetricCode(), false);
            return;
        }
        Slog.d("CredentialManager", "onFinalResponseReceived sending through final receiver");
        Bundle bundle = new Bundle();
        bundle.putParcelable(
                "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE", getCredentialResponse);
        this.mAutofillCallback.send(0, bundle);
        finishSession(ApiStatus.SUCCESS.getMetricCode(), false);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onProviderStatusChanged(
            ProviderSession.Status status,
            ComponentName componentName,
            ProviderSession.CredentialsSource credentialsSource) {
        Slog.d(
                "CredentialManager",
                "in onStatusChanged with status: " + status + ", and source: " + credentialsSource);
        if (isAnyProviderPending()) {
            return;
        }
        if (!isUiInvocationNeeded()) {
            respondToClientWithErrorAndFinish(
                    "android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL",
                    "No credentials available");
        } else {
            Slog.d("CredentialManager", "in onProviderStatusChanged - isUiInvocationNeeded");
            getProviderDataAndInitiateUi();
        }
    }

    @Override // com.android.server.credentials.RequestSession
    public final void onUiCancellation(boolean z) {
        Slog.d("CredentialManager", "User canceled but session is not being terminated");
    }

    @Override // com.android.server.credentials.RequestSession
    public final void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException(
                "android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL");
    }
}
