package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.credentials.CreateCredentialRequest;
import android.credentials.CreateCredentialResponse;
import android.credentials.ICreateCredentialCallback;
import android.credentials.selection.RequestInfo;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.service.credentials.CallingAppInfo;
import android.util.Slog;

import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric;
import com.android.server.credentials.metrics.InitialPhaseMetric;
import com.android.server.credentials.metrics.ProviderStatusForMetrics;
import com.android.server.credentials.metrics.RequestSessionMetric;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CreateRequestSession extends RequestSession
        implements ProviderSession.ProviderInternalCallback {
    public final Set mPrimaryProviders;

    public CreateRequestSession(
            Context context,
            CredentialManagerService.SessionManager sessionManager,
            Object obj,
            int i,
            int i2,
            CreateCredentialRequest createCredentialRequest,
            ICreateCredentialCallback iCreateCredentialCallback,
            CallingAppInfo callingAppInfo,
            Set set,
            Set set2,
            CancellationSignal cancellationSignal,
            long j) {
        super(
                context,
                sessionManager,
                obj,
                i,
                i2,
                createCredentialRequest,
                iCreateCredentialCallback,
                "android.credentials.selection.TYPE_CREATE",
                callingAppInfo,
                set,
                cancellationSignal,
                j,
                true);
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        boolean z = createCredentialRequest.getOrigin() != null;
        requestSessionMetric.getClass();
        try {
            InitialPhaseMetric initialPhaseMetric = requestSessionMetric.mInitialPhaseMetric;
            initialPhaseMetric.mOriginSpecified = z;
            initialPhaseMetric.mRequestCounts =
                    Map.of(createCredentialRequest.getType().substring(r2.length() - 20), 1);
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error collecting create flow metric: ", "RequestSessionMetric");
        }
        this.mPrimaryProviders = set2;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
    @Override // com.android.server.credentials.RequestSession
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.credentials.ProviderSession initiateProviderSession(
            android.credentials.CredentialProviderInfo r12,
            com.android.server.credentials.RemoteCredentialService r13) {
        /*
            r11 = this;
            android.content.Context r1 = r11.mContext
            java.util.List r0 = r12.getCapabilities()
            java.lang.Object r2 = r11.mClientRequest
            android.credentials.CreateCredentialRequest r2 = (android.credentials.CreateCredentialRequest) r2
            android.service.credentials.CallingAppInfo r3 = r11.mClientAppInfo
            boolean r4 = r12.isSystemProvider()
            boolean r5 = r2.isSystemProviderRequired()
            r6 = 0
            if (r5 == 0) goto L1b
            if (r4 != 0) goto L1b
        L19:
            r7 = r6
            goto L2f
        L1b:
            java.lang.String r4 = r2.getType()
            boolean r0 = r0.contains(r4)
            if (r0 == 0) goto L19
            android.service.credentials.CreateCredentialRequest r0 = new android.service.credentials.CreateCredentialRequest
            android.os.Bundle r5 = r2.getCredentialData()
            r0.<init>(r3, r4, r5)
            r7 = r0
        L2f:
            java.lang.String r9 = "CredentialManager"
            if (r7 == 0) goto L5f
            com.android.server.credentials.ProviderCreateSession r10 = new com.android.server.credentials.ProviderCreateSession
            java.lang.String r0 = r2.getType()
            android.os.Bundle r3 = r2.getCandidateQueryData()
            android.service.credentials.CallingAppInfo r4 = r11.mClientAppInfo
            boolean r2 = r2.alwaysSendAppInfoToProvider()
            if (r2 == 0) goto L4c
            android.service.credentials.BeginCreateCredentialRequest r2 = new android.service.credentials.BeginCreateCredentialRequest
            r2.<init>(r0, r3, r4)
        L4a:
            r6 = r2
            goto L52
        L4c:
            android.service.credentials.BeginCreateCredentialRequest r2 = new android.service.credentials.BeginCreateCredentialRequest
            r2.<init>(r0, r3)
            goto L4a
        L52:
            java.lang.String r8 = r11.mHybridService
            int r4 = r11.mUserId
            r0 = r10
            r2 = r12
            r3 = r11
            r5 = r13
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r6 = r10
            goto L74
        L5f:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r0 = "Unable to create provider session for: "
            r13.<init>(r0)
            android.content.ComponentName r0 = r12.getComponentName()
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            android.util.Slog.i(r9, r13)
        L74:
            if (r6 == 0) goto L98
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r0 = "Provider session created and being added for: "
            r13.<init>(r0)
            android.content.ComponentName r12 = r12.getComponentName()
            r13.append(r12)
            java.lang.String r12 = r13.toString()
            android.util.Slog.i(r9, r12)
            java.util.Map r11 = r11.mProviders
            android.content.ComponentName r12 = r6.mComponentName
            java.lang.String r12 = r12.flattenToString()
            java.util.concurrent.ConcurrentHashMap r11 = (java.util.concurrent.ConcurrentHashMap) r11
            r11.put(r12, r6)
        L98:
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.credentials.CreateRequestSession.initiateProviderSession(android.credentials.CredentialProviderInfo,"
                    + " com.android.server.credentials.RemoteCredentialService):com.android.server.credentials.ProviderSession");
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackError(String str, String str2) {
        ((ICreateCredentialCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void invokeClientCallbackSuccess(Object obj) {
        ((ICreateCredentialCallback) this.mClientCallback)
                .onResponse((CreateCredentialResponse) obj);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void launchUiWithProviderData(ArrayList arrayList) {
        long nanoTime = System.nanoTime();
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.getClass();
        ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric =
                requestSessionMetric.mChosenProviderFinalPhaseMetric;
        try {
            chosenProviderFinalPhaseMetric.mUiCallStartTimeNanoseconds = nanoTime;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error collecting ui start metric: ", "RequestSessionMetric");
        }
        CredentialManagerUi.UiStatus uiStatus = CredentialManagerUi.UiStatus.USER_INTERACTION;
        CredentialManagerUi credentialManagerUi = this.mCredentialManagerUi;
        credentialManagerUi.mStatus = uiStatus;
        PendingIntent pendingIntent = this.mPendingIntent;
        if (pendingIntent != null) {
            try {
                pendingIntent.cancel();
                this.mPendingIntent = null;
            } catch (Exception e2) {
                Slog.e("CredentialManager", "Unable to cancel existing pending intent", e2);
            }
        }
        try {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = this.mPrimaryProviders.iterator();
            while (it.hasNext()) {
                arrayList2.add(((ComponentName) it.next()).flattenToString());
            }
            PendingIntent createPendingIntent =
                    credentialManagerUi.createPendingIntent(
                            RequestInfo.newCreateRequestInfo(
                                    this.mRequestId,
                                    (CreateCredentialRequest) this.mClientRequest,
                                    this.mClientAppInfo.getPackageName(),
                                    false,
                                    arrayList2,
                                    false),
                            arrayList,
                            requestSessionMetric);
            this.mPendingIntent = createPendingIntent;
            ((ICreateCredentialCallback) this.mClientCallback).onPendingIntent(createPendingIntent);
        } catch (RemoteException unused) {
            try {
                chosenProviderFinalPhaseMetric.mUiReturned = false;
            } catch (Exception e3) {
                PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                        e3,
                        "Unexpected error collecting ui end time metric: ",
                        "RequestSessionMetric");
            }
            credentialManagerUi.mStatus = CredentialManagerUi.UiStatus.TERMINATED;
            respondToClientWithErrorAndFinish(
                    "android.credentials.CreateCredentialException.TYPE_UNKNOWN",
                    "Unable to invoke selector");
        }
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onFinalResponseReceived(ComponentName componentName, Object obj) {
        Slog.i(
                "CredentialManager",
                "Final credential received from: " + componentName.flattenToString());
        long nanoTime = System.nanoTime();
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.collectUiResponseData(nanoTime);
        Map map = this.mProviders;
        requestSessionMetric.updateMetricsOnResponseReceived(map, componentName);
        requestSessionMetric.collectChosenProviderStatus(
                ProviderStatusForMetrics.FINAL_SUCCESS.getMetricCode());
        respondToClientWithResponseAndFinish((CreateCredentialResponse) obj);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public final void onProviderStatusChanged(
            ProviderSession.Status status,
            ComponentName componentName,
            ProviderSession.CredentialsSource credentialsSource) {
        Slog.i(
                "CredentialManager",
                "Provider status changed: " + status + ", and source: " + credentialsSource);
        if (isAnyProviderPending()) {
            return;
        }
        if (isUiInvocationNeeded()) {
            Slog.i("CredentialManager", "Provider status changed - ui invocation is needed");
            getProviderDataAndInitiateUi();
        } else {
            this.mRequestSessionMetric.collectFrameworkException(
                    "android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
            respondToClientWithErrorAndFinish(
                    "android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS",
                    "No create options available.");
        }
    }

    @Override // com.android.server.credentials.RequestSession
    public final void onUiCancellation(boolean z) {
        String str;
        String str2;
        if (z) {
            str = "android.credentials.CreateCredentialException.TYPE_USER_CANCELED";
            str2 = "User cancelled the selector";
        } else {
            str = "android.credentials.CreateCredentialException.TYPE_INTERRUPTED";
            str2 = "The UI was interrupted - please try again.";
        }
        this.mRequestSessionMetric.collectFrameworkException(str);
        respondToClientWithErrorAndFinish(str, str2);
    }

    @Override // com.android.server.credentials.RequestSession
    public final void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException(
                "android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
        respondToClientWithErrorAndFinish(
                "android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS",
                "No create options available.");
    }
}
