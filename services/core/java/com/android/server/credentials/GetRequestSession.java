package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.credentials.CredentialProviderInfo;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.credentials.IGetCredentialCallback;
import android.credentials.selection.RequestInfo;
import android.os.Binder;
import android.os.RemoteException;
import android.service.credentials.BeginGetCredentialRequest;
import android.service.credentials.CallingAppInfo;
import android.service.credentials.PermissionUtils;
import android.util.Slog;

import com.android.internal.util.FunctionalUtils;
import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.ProviderStatusForMetrics;
import com.android.server.credentials.metrics.RequestSessionMetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class GetRequestSession extends RequestSession
        implements ProviderSession.ProviderInternalCallback {
    public final Set mPrimaryProviders;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GetRequestSession(
            android.content.Context r17,
            com.android.server.credentials.CredentialManagerService.SessionManager r18,
            java.lang.Object r19,
            int r20,
            int r21,
            android.credentials.IGetCredentialCallback r22,
            android.credentials.GetCredentialRequest r23,
            android.service.credentials.CallingAppInfo r24,
            java.util.Set r25,
            java.util.Set r26,
            android.os.CancellationSignal r27,
            long r28) {
        /*
            r16 = this;
            r15 = r16
            java.util.List r0 = r23.getCredentialOptions()
            java.util.Iterator r0 = r0.iterator()
        La:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L26
            java.lang.Object r1 = r0.next()
            android.credentials.CredentialOption r1 = (android.credentials.CredentialOption) r1
            android.os.Bundle r1 = r1.getCredentialRetrievalData()
            java.lang.String r2 = "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"
            java.util.ArrayList r1 = r1.getStringArrayList(r2)
            if (r1 == 0) goto La
            java.lang.String r0 = "android.credentials.selection.TYPE_GET_VIA_REGISTRY"
        L24:
            r8 = r0
            goto L29
        L26:
            java.lang.String r0 = "android.credentials.selection.TYPE_GET"
            goto L24
        L29:
            r14 = 1
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            r6 = r23
            r7 = r22
            r9 = r24
            r10 = r25
            r11 = r27
            r12 = r28
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r14)
            com.android.server.credentials.metrics.RequestSessionMetric r0 = r15.mRequestSessionMetric
            r1 = r23
            r0.collectGetFlowInitialMetricInfo(r1)
            r0 = r26
            r15.mPrimaryProviders = r0
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.credentials.GetRequestSession.<init>(android.content.Context,"
                    + " com.android.server.credentials.CredentialManagerService$SessionManager,"
                    + " java.lang.Object, int, int, android.credentials.IGetCredentialCallback,"
                    + " android.credentials.GetCredentialRequest,"
                    + " android.service.credentials.CallingAppInfo, java.util.Set, java.util.Set,"
                    + " android.os.CancellationSignal, long):void");
    }

    public final void handleEmptyAuthenticationSelection(final ComponentName componentName) {
        ((ConcurrentHashMap) this.mProviders)
                .keySet()
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.server.credentials.GetRequestSession$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                GetRequestSession getRequestSession = GetRequestSession.this;
                                ComponentName componentName2 = componentName;
                                ProviderGetSession providerGetSession =
                                        (ProviderGetSession)
                                                ((ConcurrentHashMap) getRequestSession.mProviders)
                                                        .get((String) obj);
                                if (providerGetSession.mComponentName.equals(componentName2)) {
                                    return;
                                }
                                providerGetSession.mProviderResponseDataHandler
                                        .updatePreviousMostRecentAuthEntry();
                            }
                        });
        getProviderDataAndInitiateUi();
        Iterator it = ((ConcurrentHashMap) this.mProviders).keySet().iterator();
        while (it.hasNext()) {
            ProviderGetSession.ProviderResponseDataHandler providerResponseDataHandler =
                    ((ProviderGetSession)
                                    ((ConcurrentHashMap) this.mProviders).get((String) it.next()))
                            .mProviderResponseDataHandler;
            if (!((HashMap) providerResponseDataHandler.mUiCredentialEntries).isEmpty()
                    || providerResponseDataHandler.mUiRemoteEntry != null
                    || !((HashMap) providerResponseDataHandler.mUiAuthenticationEntries)
                            .values().stream()
                                    .allMatch(
                                            new ProviderGetSession$$ExternalSyntheticLambda1(0))) {
                return;
            }
        }
        this.mRequestSessionMetric.collectFrameworkException(
                "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
        respondToClientWithErrorAndFinish(
                "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL",
                "No credentials available");
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
            Slog.i(
                    "CredentialManager",
                    "Provider session created and being added for: "
                            + credentialProviderInfo.getComponentName());
            ((ConcurrentHashMap) this.mProviders)
                    .put(providerGetSession.mComponentName.flattenToString(), providerGetSession);
        }
        return providerGetSession;
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackError(String str, String str2) {
        ((IGetCredentialCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackSuccess(Object obj) {
        ((IGetCredentialCallback) this.mClientCallback).onResponse((GetCredentialResponse) obj);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void launchUiWithProviderData(final ArrayList arrayList) {
        long nanoTime = System.nanoTime();
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.getClass();
        try {
            requestSessionMetric.mChosenProviderFinalPhaseMetric.mUiCallStartTimeNanoseconds =
                    nanoTime;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error collecting ui start metric: ", "RequestSessionMetric");
        }
        this.mCredentialManagerUi.mStatus = CredentialManagerUi.UiStatus.USER_INTERACTION;
        Binder.withCleanCallingIdentity(
                new FunctionalUtils.ThrowingRunnable() { // from class:
                    // com.android.server.credentials.GetRequestSession$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        GetRequestSession getRequestSession = GetRequestSession.this;
                        ArrayList arrayList2 = arrayList;
                        CredentialManagerUi credentialManagerUi =
                                getRequestSession.mCredentialManagerUi;
                        RequestSessionMetric requestSessionMetric2 =
                                getRequestSession.mRequestSessionMetric;
                        try {
                            ArrayList arrayList3 = new ArrayList();
                            Iterator it = getRequestSession.mPrimaryProviders.iterator();
                            while (it.hasNext()) {
                                arrayList3.add(((ComponentName) it.next()).flattenToString());
                            }
                            PendingIntent pendingIntent = getRequestSession.mPendingIntent;
                            if (pendingIntent != null) {
                                try {
                                    pendingIntent.cancel();
                                    getRequestSession.mPendingIntent = null;
                                } catch (Exception e2) {
                                    Slog.e(
                                            "CredentialManager",
                                            "Unable to cancel existing pending intent",
                                            e2);
                                }
                            }
                            PendingIntent createPendingIntent =
                                    credentialManagerUi.createPendingIntent(
                                            RequestInfo.newGetRequestInfo(
                                                    getRequestSession.mRequestId,
                                                    (GetCredentialRequest)
                                                            getRequestSession.mClientRequest,
                                                    getRequestSession.mClientAppInfo
                                                            .getPackageName(),
                                                    PermissionUtils.hasPermission(
                                                            getRequestSession.mContext,
                                                            getRequestSession.mClientAppInfo
                                                                    .getPackageName(),
                                                            "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"),
                                                    arrayList3,
                                                    false),
                                            arrayList2,
                                            requestSessionMetric2);
                            getRequestSession.mPendingIntent = createPendingIntent;
                            ((IGetCredentialCallback) getRequestSession.mClientCallback)
                                    .onPendingIntent(createPendingIntent);
                        } catch (RemoteException unused) {
                            requestSessionMetric2.getClass();
                            try {
                                requestSessionMetric2.mChosenProviderFinalPhaseMetric.mUiReturned =
                                        false;
                            } catch (Exception e3) {
                                PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                                        e3,
                                        "Unexpected error collecting ui end time metric: ",
                                        "RequestSessionMetric");
                            }
                            credentialManagerUi.mStatus = CredentialManagerUi.UiStatus.TERMINATED;
                            requestSessionMetric2.collectFrameworkException(
                                    "android.credentials.GetCredentialException.TYPE_UNKNOWN");
                            getRequestSession.respondToClientWithErrorAndFinish(
                                    "android.credentials.GetCredentialException.TYPE_UNKNOWN",
                                    "Unable to instantiate selector");
                        }
                    }
                });
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onFinalResponseReceived(
            ComponentName componentName, GetCredentialResponse getCredentialResponse) {
        Slog.i(
                "CredentialManager",
                "onFinalResponseReceived from: " + componentName.flattenToString());
        long nanoTime = System.nanoTime();
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.collectUiResponseData(nanoTime);
        Map map = this.mProviders;
        requestSessionMetric.updateMetricsOnResponseReceived(map, componentName);
        requestSessionMetric.collectChosenProviderStatus(
                ProviderStatusForMetrics.FINAL_SUCCESS.getMetricCode());
        respondToClientWithResponseAndFinish(getCredentialResponse);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(
            ProviderSession.Status status,
            ComponentName componentName,
            ProviderSession.CredentialsSource credentialsSource) {
        Slog.i(
                "CredentialManager",
                "Status changed for: "
                        + componentName
                        + ", with status: "
                        + status
                        + ", and source: "
                        + credentialsSource);
        if (status == ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY) {
            handleEmptyAuthenticationSelection(componentName);
            return;
        }
        if (isAnyProviderPending()) {
            return;
        }
        if (isUiInvocationNeeded()) {
            Slog.i("CredentialManager", "Provider status changed - ui invocation is needed");
            getProviderDataAndInitiateUi();
        } else {
            this.mRequestSessionMetric.collectFrameworkException(
                    "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            respondToClientWithErrorAndFinish(
                    "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL",
                    "No credentials available");
        }
    }

    @Override // com.android.server.credentials.RequestSession
    public final void onUiCancellation(boolean z) {
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

    @Override // com.android.server.credentials.RequestSession
    public final void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException(
                "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
        respondToClientWithErrorAndFinish(
                "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL",
                "No credentials available.");
    }
}
