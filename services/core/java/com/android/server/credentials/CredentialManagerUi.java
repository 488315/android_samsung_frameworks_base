package com.android.server.credentials;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.credentials.selection.IntentCreationResult;
import android.credentials.selection.IntentFactory;
import android.credentials.selection.RequestInfo;
import android.credentials.selection.UserSelectionDialogResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.service.credentials.CredentialProviderInfoFactory;
import android.util.Slog;

import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.ApiStatus;
import com.android.server.credentials.metrics.BrowsedAuthenticationMetric;
import com.android.server.credentials.metrics.CandidateBrowsingPhaseMetric;
import com.android.server.credentials.metrics.CandidatePhaseMetric;
import com.android.server.credentials.metrics.EntryEnum;
import com.android.server.credentials.metrics.ProviderSessionMetric;
import com.android.server.credentials.metrics.RequestSessionMetric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CredentialManagerUi {
    public final RequestSession mCallbacks;
    public final Context mContext;
    public final Set mEnabledProviders;
    public final AnonymousClass1 mResultReceiver =
            new ResultReceiver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.android.server.credentials.CredentialManagerUi.1
                @Override // android.os.ResultReceiver
                public final void onReceiveResult(int i, Bundle bundle) {
                    CredentialManagerUi credentialManagerUi = CredentialManagerUi.this;
                    UiStatus uiStatus = UiStatus.TERMINATED;
                    RequestSession requestSession = credentialManagerUi.mCallbacks;
                    if (i == 0) {
                        credentialManagerUi.mStatus = uiStatus;
                        requestSession.onUiCancellation(true);
                        return;
                    }
                    if (i == 1) {
                        credentialManagerUi.mStatus = uiStatus;
                        requestSession.onUiCancellation(false);
                        return;
                    }
                    UiStatus uiStatus2 = UiStatus.IN_PROGRESS;
                    if (i != 2) {
                        if (i != 3) {
                            credentialManagerUi.mStatus = uiStatus2;
                            requestSession.onUiSelectorInvocationFailure();
                            return;
                        } else {
                            credentialManagerUi.mStatus = uiStatus;
                            requestSession.onUiSelectorInvocationFailure();
                            return;
                        }
                    }
                    credentialManagerUi.mStatus = uiStatus2;
                    UserSelectionDialogResult fromResultData =
                            UserSelectionDialogResult.fromResultData(bundle);
                    if (fromResultData != null) {
                        if (requestSession.mRequestSessionStatus
                                == RequestSession.RequestSessionStatus.COMPLETE) {
                            Slog.w(
                                    "CredentialManager",
                                    "Request has already been completed. This is strange.");
                            return;
                        }
                        if (requestSession.mCancellationSignal.isCanceled()) {
                            requestSession.finishSession(
                                    ApiStatus.CLIENT_CANCELED.getMetricCode(), true);
                            return;
                        }
                        ProviderSession providerSession =
                                (ProviderSession)
                                        ((ConcurrentHashMap) requestSession.mProviders)
                                                .get(fromResultData.getProviderId());
                        if (providerSession == null) {
                            Slog.w(
                                    "CredentialManager",
                                    "providerSession not found in onUiSelection. This is strange.");
                            return;
                        }
                        ProviderSessionMetric providerSessionMetric =
                                providerSession.mProviderSessionMetric;
                        int size =
                                ((ArrayList) providerSessionMetric.mBrowsedAuthenticationMetric)
                                        .size();
                        CandidatePhaseMetric candidatePhaseMetric =
                                providerSessionMetric.mCandidatePhasePerProviderMetric;
                        RequestSessionMetric requestSessionMetric =
                                requestSession.mRequestSessionMetric;
                        requestSessionMetric.getClass();
                        try {
                            CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric =
                                    new CandidateBrowsingPhaseMetric();
                            candidateBrowsingPhaseMetric.mEntryEnum =
                                    EntryEnum.UNKNOWN.getMetricCode();
                            candidateBrowsingPhaseMetric.mProviderUid = -1;
                            candidateBrowsingPhaseMetric.mEntryEnum =
                                    EntryEnum.getMetricCodeFromString(fromResultData.getEntryKey());
                            candidateBrowsingPhaseMetric.mProviderUid =
                                    candidatePhaseMetric.mCandidateUid;
                            ((ArrayList) requestSessionMetric.mCandidateBrowsingPhaseMetric)
                                    .add(candidateBrowsingPhaseMetric);
                        } catch (Exception e) {
                            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                                    e,
                                    "Unexpected error collecting browsing metric: ",
                                    "RequestSessionMetric");
                        }
                        providerSession.onUiEntrySelected(
                                fromResultData.getEntryKey(),
                                fromResultData.getEntrySubkey(),
                                fromResultData.getPendingIntentProviderResponse());
                        int size2 =
                                ((ArrayList) providerSessionMetric.mBrowsedAuthenticationMetric)
                                        .size();
                        if (size2 - size == 1) {
                            BrowsedAuthenticationMetric browsedAuthenticationMetric =
                                    (BrowsedAuthenticationMetric)
                                            ((ArrayList)
                                                            providerSessionMetric
                                                                    .mBrowsedAuthenticationMetric)
                                                    .get(size2 - 1);
                            try {
                                if (browsedAuthenticationMetric.mProviderUid == -1) {
                                    Slog.v(
                                            "RequestSessionMetric",
                                            "An authentication entry was not clicked");
                                } else {
                                    int i2 = requestSessionMetric.mSequenceCounter + 1;
                                    requestSessionMetric.mSequenceCounter = i2;
                                    MetricUtilities.logApiCalledAuthenticationMetric(
                                            browsedAuthenticationMetric, i2);
                                }
                            } catch (Exception e2) {
                                PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                                        e2,
                                        "Unexpected error during auth entry metric emit: ",
                                        "RequestSessionMetric");
                            }
                        }
                    }
                }
            };
    public UiStatus mStatus = UiStatus.IN_PROGRESS;
    public final int mUserId;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class UiStatus {
        public static final /* synthetic */ UiStatus[] $VALUES;
        public static final UiStatus IN_PROGRESS;
        public static final UiStatus TERMINATED;
        public static final UiStatus USER_INTERACTION;

        static {
            UiStatus uiStatus = new UiStatus("IN_PROGRESS", 0);
            IN_PROGRESS = uiStatus;
            UiStatus uiStatus2 = new UiStatus("USER_INTERACTION", 1);
            USER_INTERACTION = uiStatus2;
            UiStatus uiStatus3 = new UiStatus("NOT_STARTED", 2);
            UiStatus uiStatus4 = new UiStatus("TERMINATED", 3);
            TERMINATED = uiStatus4;
            $VALUES = new UiStatus[] {uiStatus, uiStatus2, uiStatus3, uiStatus4};
        }

        public static UiStatus valueOf(String str) {
            return (UiStatus) Enum.valueOf(UiStatus.class, str);
        }

        public static UiStatus[] values() {
            return (UiStatus[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.credentials.CredentialManagerUi$1] */
    public CredentialManagerUi(Context context, int i, RequestSession requestSession, Set set) {
        this.mContext = context;
        this.mUserId = i;
        this.mCallbacks = requestSession;
        this.mEnabledProviders = set;
    }

    public final PendingIntent createPendingIntent(
            RequestInfo requestInfo,
            ArrayList arrayList,
            RequestSessionMetric requestSessionMetric) {
        Context context = this.mContext;
        Set set = this.mEnabledProviders;
        HashSet hashSet = new HashSet();
        int i = this.mUserId;
        IntentCreationResult createCredentialSelectorIntentForCredMan =
                IntentFactory.createCredentialSelectorIntentForCredMan(
                        this.mContext,
                        requestInfo,
                        arrayList,
                        new ArrayList(
                                CredentialProviderInfoFactory.getCredentialProviderServices(
                                                context, i, 2, set, hashSet)
                                        .stream()
                                        .filter(new CredentialManagerUi$$ExternalSyntheticLambda0())
                                        .map(new CredentialManagerUi$$ExternalSyntheticLambda1())
                                        .toList()),
                        this.mResultReceiver);
        requestSessionMetric.collectUiConfigurationResults(
                this.mContext, createCredentialSelectorIntentForCredMan, i);
        Intent intent = createCredentialSelectorIntentForCredMan.getIntent();
        intent.setAction(UUID.randomUUID().toString());
        return PendingIntent.getActivityAsUser(
                this.mContext, 0, intent, 33554432, null, UserHandle.of(i));
    }
}
