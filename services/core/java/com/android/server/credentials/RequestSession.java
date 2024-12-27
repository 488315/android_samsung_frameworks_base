package com.android.server.credentials;

import android.R;
import android.app.PendingIntent;
import android.content.Context;
import android.credentials.CredentialProviderInfo;
import android.credentials.selection.IntentFactory;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.credentials.CallingAppInfo;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.ApiName;
import com.android.server.credentials.metrics.ApiStatus;
import com.android.server.credentials.metrics.ChosenProviderFinalPhaseMetric;
import com.android.server.credentials.metrics.InitialPhaseMetric;
import com.android.server.credentials.metrics.ProviderStatusForMetrics;
import com.android.server.credentials.metrics.RequestSessionMetric;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RequestSession {
    public final CancellationSignal mCancellationSignal;
    public final CallingAppInfo mClientAppInfo;
    public final Object mClientCallback;
    public final Object mClientRequest;
    public final Context mContext;
    public final CredentialManagerUi mCredentialManagerUi;
    public final String mHybridService;
    public final Object mLock;
    public PendingIntent mPendingIntent;
    public final IBinder mRequestId;
    public final RequestSessionMetric mRequestSessionMetric;
    public final CredentialManagerService.SessionManager mSessionCallback;
    public final int mUserId;
    public final Map mProviders = new ConcurrentHashMap();
    public final RequestSessionDeathRecipient mDeathRecipient = new RequestSessionDeathRecipient();
    public RequestSessionStatus mRequestSessionStatus = RequestSessionStatus.IN_PROGRESS;

    public final class RequestSessionDeathRecipient implements IBinder.DeathRecipient {
        public RequestSessionDeathRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.d("CredentialManager", "Client binder died - clearing session");
            RequestSession requestSession = RequestSession.this;
            requestSession.finishSession(
                    ApiStatus.CLIENT_CANCELED.getMetricCode(),
                    requestSession.mCredentialManagerUi.mStatus
                            == CredentialManagerUi.UiStatus.IN_PROGRESS);
        }
    }

    final class RequestSessionStatus {
        public static final /* synthetic */ RequestSessionStatus[] $VALUES;
        public static final RequestSessionStatus COMPLETE;
        public static final RequestSessionStatus IN_PROGRESS;

        static {
            RequestSessionStatus requestSessionStatus = new RequestSessionStatus("IN_PROGRESS", 0);
            IN_PROGRESS = requestSessionStatus;
            RequestSessionStatus requestSessionStatus2 = new RequestSessionStatus("CANCELLED", 1);
            RequestSessionStatus requestSessionStatus3 = new RequestSessionStatus("COMPLETE", 2);
            COMPLETE = requestSessionStatus3;
            $VALUES =
                    new RequestSessionStatus[] {
                        requestSessionStatus, requestSessionStatus2, requestSessionStatus3
                    };
        }

        public static RequestSessionStatus valueOf(String str) {
            return (RequestSessionStatus) Enum.valueOf(RequestSessionStatus.class, str);
        }

        public static RequestSessionStatus[] values() {
            return (RequestSessionStatus[]) $VALUES.clone();
        }
    }

    public RequestSession(
            Context context,
            CredentialManagerService.SessionManager sessionManager,
            Object obj,
            int i,
            int i2,
            Object obj2,
            Object obj3,
            String str,
            CallingAppInfo callingAppInfo,
            Set set,
            CancellationSignal cancellationSignal,
            long j,
            boolean z) {
        Object obj4;
        this.mContext = context;
        this.mLock = obj;
        this.mSessionCallback = sessionManager;
        this.mUserId = i;
        this.mClientRequest = obj2;
        this.mClientCallback = obj3;
        this.mClientAppInfo = callingAppInfo;
        this.mCancellationSignal = cancellationSignal;
        new Handler(Looper.getMainLooper(), null, true);
        this.mRequestId = new Binder();
        this.mCredentialManagerUi = new CredentialManagerUi(context, i, this, set);
        this.mHybridService = context.getResources().getString(R.string.date_time_done);
        RequestSessionMetric requestSessionMetric =
                new RequestSessionMetric(
                        new SecureRandom().nextInt(), new SecureRandom().nextInt());
        this.mRequestSessionMetric = requestSessionMetric;
        int metricCodeFromRequestInfo = ApiName.getMetricCodeFromRequestInfo(str);
        try {
            InitialPhaseMetric initialPhaseMetric = requestSessionMetric.mInitialPhaseMetric;
            initialPhaseMetric.mCredentialServiceStartedTimeNanoseconds = j;
            initialPhaseMetric.mCallerUid = i2;
            initialPhaseMetric.mApiName = metricCodeFromRequestInfo;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error collecting initial phase metric start info: ",
                    "RequestSessionMetric");
        }
        this.mCancellationSignal.setOnCancelListener(
                new CancellationSignal
                        .OnCancelListener() { // from class:
                                              // com.android.server.credentials.RequestSession$$ExternalSyntheticLambda1
                    @Override // android.os.CancellationSignal.OnCancelListener
                    public final void onCancel() {
                        boolean z2;
                        RequestSession requestSession = RequestSession.this;
                        requestSession.getClass();
                        Slog.d(
                                "CredentialManager",
                                "Cancellation invoked from the client - clearing session");
                        CredentialManagerUi credentialManagerUi =
                                requestSession.mCredentialManagerUi;
                        if (credentialManagerUi.mStatus
                                == CredentialManagerUi.UiStatus.USER_INTERACTION) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                requestSession.mContext.startActivityAsUser(
                                        IntentFactory.createCancelUiIntent(
                                                        credentialManagerUi.mContext,
                                                        requestSession.mRequestId,
                                                        true,
                                                        requestSession.mClientAppInfo
                                                                .getPackageName())
                                                .addFlags(268435456),
                                        UserHandle.of(requestSession.mUserId));
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                z2 = true;
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        } else {
                            z2 = false;
                        }
                        requestSession.finishSession(
                                ApiStatus.CLIENT_CANCELED.getMetricCode(), !z2);
                    }
                });
        if (z
                && Flags.clearSessionEnabled()
                && (obj4 = this.mClientCallback) != null
                && (obj4 instanceof IInterface)) {
            setUpClientCallbackListener(((IInterface) obj4).asBinder());
        }
    }

    public final void finishSession(int i, boolean z) {
        Slog.i("CredentialManager", "finishing session with propagateCancellation " + z);
        if (z) {
            ((ConcurrentHashMap) this.mProviders)
                    .values()
                    .forEach(new RequestSession$$ExternalSyntheticLambda0());
        }
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric =
                requestSessionMetric.mChosenProviderFinalPhaseMetric;
        try {
            List list = requestSessionMetric.mCandidateBrowsingPhaseMetric;
            int i2 = requestSessionMetric.mSequenceCounter + 1;
            requestSessionMetric.mSequenceCounter = i2;
            MetricUtilities.logApiCalledFinalPhase(chosenProviderFinalPhaseMetric, list, i, i2);
            List list2 = requestSessionMetric.mCandidateBrowsingPhaseMetric;
            int i3 = requestSessionMetric.mSequenceCounter + 1;
            requestSessionMetric.mSequenceCounter = i3;
            MetricUtilities.logApiCalledNoUidFinal(chosenProviderFinalPhaseMetric, list2, i, i3);
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error during final metric emit: ", "RequestSessionMetric");
        }
        this.mRequestSessionStatus = RequestSessionStatus.COMPLETE;
        ((ConcurrentHashMap) this.mProviders).clear();
        synchronized (this.mLock) {
            CredentialManagerService.SessionManager sessionManager = this.mSessionCallback;
            int i4 = this.mUserId;
            IBinder iBinder = this.mRequestId;
            CredentialManagerService credentialManagerService = CredentialManagerService.this;
            if (credentialManagerService.mRequestSessions.get(i4) != null) {
                ((Map) credentialManagerService.mRequestSessions.get(i4)).remove(iBinder);
            }
        }
    }

    public final void getProviderDataAndInitiateUi() {
        ArrayList providerDataForUi = getProviderDataForUi();
        if (providerDataForUi.isEmpty()) {
            return;
        }
        launchUiWithProviderData(providerDataForUi);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList getProviderDataForUi() {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "For ui, provider data size: "
            r0.<init>(r1)
            java.util.Map r1 = r7.mProviders
            java.util.concurrent.ConcurrentHashMap r1 = (java.util.concurrent.ConcurrentHashMap) r1
            int r1 = r1.size()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "CredentialManager"
            android.util.Slog.i(r1, r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Map r1 = r7.mProviders
            com.android.server.credentials.metrics.RequestSessionMetric r2 = r7.mRequestSessionMetric
            com.android.server.credentials.metrics.InitialPhaseMetric r3 = r2.mInitialPhaseMetric
            r4 = 1
            int r5 = r2.mSequenceCounter     // Catch: java.lang.Exception -> L44
            int r5 = r5 + r4
            r2.mSequenceCounter = r5     // Catch: java.lang.Exception -> L44
            com.android.server.credentials.MetricUtilities.logApiCalledCandidatePhase(r1, r5, r3)     // Catch: java.lang.Exception -> L44
            int r5 = r3.mApiName     // Catch: java.lang.Exception -> L44
            com.android.server.credentials.metrics.ApiName r6 = com.android.server.credentials.metrics.ApiName.GET_CREDENTIAL     // Catch: java.lang.Exception -> L44
            int r6 = r6.getMetricCode()     // Catch: java.lang.Exception -> L44
            if (r5 == r6) goto L46
            int r3 = r3.mApiName     // Catch: java.lang.Exception -> L44
            com.android.server.credentials.metrics.ApiName r5 = com.android.server.credentials.metrics.ApiName.GET_CREDENTIAL_VIA_REGISTRY     // Catch: java.lang.Exception -> L44
            int r5 = r5.getMetricCode()     // Catch: java.lang.Exception -> L44
            if (r3 != r5) goto L53
            goto L46
        L44:
            r1 = move-exception
            goto L4c
        L46:
            int r2 = r2.mSequenceCounter     // Catch: java.lang.Exception -> L44
            com.android.server.credentials.MetricUtilities.logApiCalledCandidateGetMetric(r2, r1)     // Catch: java.lang.Exception -> L44
            goto L53
        L4c:
            java.lang.String r2 = "Unexpected error during candidate metric emit: "
            java.lang.String r3 = "RequestSessionMetric"
            com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(r1, r2, r3)
        L53:
            android.os.CancellationSignal r1 = r7.mCancellationSignal
            boolean r1 = r1.isCanceled()
            if (r1 == 0) goto L65
            com.android.server.credentials.metrics.ApiStatus r1 = com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED
            int r1 = r1.getMetricCode()
            r7.finishSession(r1, r4)
            return r0
        L65:
            java.util.Map r7 = r7.mProviders
            java.util.concurrent.ConcurrentHashMap r7 = (java.util.concurrent.ConcurrentHashMap) r7
            java.util.Collection r7 = r7.values()
            java.util.Iterator r7 = r7.iterator()
        L71:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L87
            java.lang.Object r1 = r7.next()
            com.android.server.credentials.ProviderSession r1 = (com.android.server.credentials.ProviderSession) r1
            android.credentials.selection.ProviderData r1 = r1.prepareUiData()
            if (r1 == 0) goto L71
            r0.add(r1)
            goto L71
        L87:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.credentials.RequestSession.getProviderDataForUi():java.util.ArrayList");
    }

    public abstract ProviderSession initiateProviderSession(
            CredentialProviderInfo credentialProviderInfo,
            RemoteCredentialService remoteCredentialService);

    public abstract void invokeClientCallbackError(String str, String str2);

    public abstract void invokeClientCallbackSuccess(Object obj);

    public final boolean isAnyProviderPending() {
        Iterator it = ((ConcurrentHashMap) this.mProviders).values().iterator();
        while (it.hasNext()) {
            if (((ProviderSession) it.next()).mStatus == ProviderSession.Status.PENDING) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUiInvocationNeeded() {
        for (ProviderSession providerSession : ((ConcurrentHashMap) this.mProviders).values()) {
            if (ProviderSession.isUiInvokingStatus(providerSession.mStatus)) {
                return true;
            }
            if (providerSession.mStatus == ProviderSession.Status.PENDING) {
                break;
            }
        }
        return false;
    }

    public abstract void launchUiWithProviderData(ArrayList arrayList);

    public void onFinalErrorReceived(String str, String str2) {
        respondToClientWithErrorAndFinish(str, str2);
    }

    public abstract void onUiCancellation(boolean z);

    public abstract void onUiSelectorInvocationFailure();

    public final void respondToClientWithErrorAndFinish(String str, String str2) {
        Map map = this.mProviders;
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.logCandidateAggregateMetrics(map);
        requestSessionMetric.collectFinalPhaseProviderMetricStatus(
                true, ProviderStatusForMetrics.FINAL_FAILURE);
        if (this.mRequestSessionStatus == RequestSessionStatus.COMPLETE) {
            Slog.w("CredentialManager", "Request has already been completed. This is strange.");
            return;
        }
        if (this.mCancellationSignal.isCanceled()) {
            finishSession(ApiStatus.CLIENT_CANCELED.getMetricCode(), true);
            return;
        }
        try {
            invokeClientCallbackError(str, str2);
        } catch (RemoteException e) {
            Slog.e("CredentialManager", "Issue while responding to client with error : " + e);
        }
        if (!str.contains("TYPE_USER_CANCELED")) {
            finishSession(ApiStatus.FAILURE.getMetricCode(), false);
            return;
        }
        try {
            requestSessionMetric.mChosenProviderFinalPhaseMetric.mHasException = false;
        } catch (Exception e2) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e2,
                    "Unexpected error setting final exception metric: ",
                    "RequestSessionMetric");
        }
        finishSession(ApiStatus.USER_CANCELED.getMetricCode(), false);
    }

    public final void respondToClientWithResponseAndFinish(Object obj) {
        Map map = this.mProviders;
        RequestSessionMetric requestSessionMetric = this.mRequestSessionMetric;
        requestSessionMetric.logCandidateAggregateMetrics(map);
        requestSessionMetric.collectFinalPhaseProviderMetricStatus(
                false, ProviderStatusForMetrics.FINAL_SUCCESS);
        if (this.mRequestSessionStatus == RequestSessionStatus.COMPLETE) {
            Slog.w("CredentialManager", "Request has already been completed. This is strange.");
            return;
        }
        if (this.mCancellationSignal.isCanceled()) {
            finishSession(ApiStatus.CLIENT_CANCELED.getMetricCode(), true);
            return;
        }
        try {
            invokeClientCallbackSuccess(obj);
            finishSession(ApiStatus.SUCCESS.getMetricCode(), false);
        } catch (RemoteException e) {
            requestSessionMetric.collectFinalPhaseProviderMetricStatus(
                    true, ProviderStatusForMetrics.FINAL_FAILURE);
            Slog.e("CredentialManager", "Issue while responding to client with a response : " + e);
            finishSession(ApiStatus.FAILURE.getMetricCode(), false);
        }
    }

    public final void setUpClientCallbackListener(IBinder iBinder) {
        Object obj = this.mClientCallback;
        if (obj == null || !(obj instanceof IInterface)) {
            return;
        }
        try {
            iBinder.linkToDeath(this.mDeathRecipient, 0);
        } catch (RemoteException e) {
            Slog.e("CredentialManager", e.getMessage());
        }
    }
}
