package com.android.server.credentials.metrics;

import android.content.ComponentName;
import android.content.Context;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialRequest;
import android.credentials.selection.IntentCreationResult;
import android.util.Slog;

import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;
import com.android.server.credentials.MetricUtilities;
import com.android.server.credentials.ProviderSession;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RequestSessionMetric {
    public final CandidateAggregateMetric mCandidateAggregateMetric;
    public final ChosenProviderFinalPhaseMetric mChosenProviderFinalPhaseMetric;
    public final InitialPhaseMetric mInitialPhaseMetric;
    public final int mSessionIdTrackTwo;
    public int mSequenceCounter = 0;
    public final List mCandidateBrowsingPhaseMetric = new ArrayList();

    public RequestSessionMetric(int i, int i2) {
        this.mSessionIdTrackTwo = i2;
        this.mInitialPhaseMetric = new InitialPhaseMetric(i);
        this.mCandidateAggregateMetric = new CandidateAggregateMetric(i);
        this.mChosenProviderFinalPhaseMetric = new ChosenProviderFinalPhaseMetric(i, i2);
    }

    public final void collectChosenMetricViaCandidateTransfer(
            CandidatePhaseMetric candidatePhaseMetric) {
        ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric =
                this.mChosenProviderFinalPhaseMetric;
        try {
            chosenProviderFinalPhaseMetric.mChosenUid = candidatePhaseMetric.mCandidateUid;
            chosenProviderFinalPhaseMetric.mIsPrimary = false;
            chosenProviderFinalPhaseMetric.mServiceBeganTimeNanoseconds =
                    candidatePhaseMetric.mServiceBeganTimeNanoseconds;
            chosenProviderFinalPhaseMetric.mQueryStartTimeNanoseconds =
                    candidatePhaseMetric.mStartQueryTimeNanoseconds;
            chosenProviderFinalPhaseMetric.mQueryEndTimeNanoseconds =
                    candidatePhaseMetric.mQueryFinishTimeNanoseconds;
            chosenProviderFinalPhaseMetric.mResponseCollective =
                    candidatePhaseMetric.mResponseCollective;
            chosenProviderFinalPhaseMetric.mFinalFinishTimeNanoseconds = System.nanoTime();
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during metric candidate to final transfer: ",
                    "RequestSessionMetric");
        }
    }

    public final void collectChosenProviderStatus(int i) {
        try {
            this.mChosenProviderFinalPhaseMetric.mChosenProviderStatus = i;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error setting chosen provider status metric: ",
                    "RequestSessionMetric");
        }
    }

    public final void collectFinalPhaseProviderMetricStatus(
            boolean z, ProviderStatusForMetrics providerStatusForMetrics) {
        try {
            ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric =
                    this.mChosenProviderFinalPhaseMetric;
            chosenProviderFinalPhaseMetric.mHasException = z;
            chosenProviderFinalPhaseMetric.mChosenProviderStatus =
                    providerStatusForMetrics.getMetricCode();
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during final phase provider status metric logging: ",
                    "RequestSessionMetric");
        }
    }

    public final void collectFrameworkException(String str) {
        try {
            this.mChosenProviderFinalPhaseMetric.mFrameworkException =
                    str.substring(str.length() - 30);
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error during metric logging: ", "RequestSessionMetric");
        }
    }

    public final void collectGetFlowInitialMetricInfo(GetCredentialRequest getCredentialRequest) {
        InitialPhaseMetric initialPhaseMetric = this.mInitialPhaseMetric;
        try {
            initialPhaseMetric.mOriginSpecified = getCredentialRequest.getOrigin() != null;
            final LinkedHashMap linkedHashMap = new LinkedHashMap();
            try {
                getCredentialRequest
                        .getCredentialOptions()
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.credentials.metrics.RequestSessionMetric$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        Map map = linkedHashMap;
                                        String substring =
                                                ((CredentialOption) obj)
                                                        .getType()
                                                        .substring(r2.length() - 20);
                                        map.put(
                                                substring,
                                                Integer.valueOf(
                                                        ((Integer) map.getOrDefault(substring, 0))
                                                                        .intValue()
                                                                + 1));
                                    }
                                });
            } catch (Exception e) {
                Slog.i(
                        "RequestSessionMetric",
                        "Unexpected error during get request count map metric logging: " + e);
            }
            initialPhaseMetric.mRequestCounts = linkedHashMap;
        } catch (Exception e2) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e2,
                    "Unexpected error collecting get flow initial metric: ",
                    "RequestSessionMetric");
        }
    }

    public final void collectUiConfigurationResults(
            Context context, IntentCreationResult intentCreationResult, int i) {
        ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric =
                this.mChosenProviderFinalPhaseMetric;
        try {
            chosenProviderFinalPhaseMetric.mOemUiUid =
                    MetricUtilities.getPackageUid(
                            context, intentCreationResult.getOemUiPackageName(), i);
            chosenProviderFinalPhaseMetric.mFallbackUiUid =
                    MetricUtilities.getPackageUid(
                            context, intentCreationResult.getFallbackUiPackageName(), i);
            int i2 =
                    OemUiUsageStatus.AnonymousClass1
                            .$SwitchMap$android$credentials$selection$IntentCreationResult$OemUiUsageStatus[
                            intentCreationResult.getOemUiUsageStatus().ordinal()];
            OemUiUsageStatus oemUiUsageStatus = OemUiUsageStatus.UNKNOWN;
            if (i2 != 1) {
                if (i2 == 2) {
                    oemUiUsageStatus = OemUiUsageStatus.SUCCESS;
                } else if (i2 == 3) {
                    oemUiUsageStatus = OemUiUsageStatus.FAILURE_NOT_SPECIFIED;
                } else if (i2 == 4) {
                    oemUiUsageStatus = OemUiUsageStatus.FAILURE_SPECIFIED_BUT_NOT_FOUND;
                } else if (i2 == 5) {
                    oemUiUsageStatus = OemUiUsageStatus.FAILURE_SPECIFIED_BUT_NOT_ENABLED;
                }
            }
            chosenProviderFinalPhaseMetric.mOemUiUsageStatus = oemUiUsageStatus;
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during ui configuration result collection: ",
                    "RequestSessionMetric");
        }
    }

    public final void collectUiResponseData(long j) {
        try {
            ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric =
                    this.mChosenProviderFinalPhaseMetric;
            chosenProviderFinalPhaseMetric.mUiReturned = true;
            chosenProviderFinalPhaseMetric.mUiCallEndTimeNanoseconds = j;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Unexpected error collecting ui response metric: ", "RequestSessionMetric");
        }
    }

    public final void logCandidateAggregateMetrics(Map map) {
        CandidateAggregateMetric candidateAggregateMetric = this.mCandidateAggregateMetric;
        try {
            candidateAggregateMetric.collectAverages(map);
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            MetricUtilities.logApiCalledAggregateCandidate(candidateAggregateMetric, i);
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during aggregate candidate logging ",
                    "RequestSessionMetric");
        }
    }

    public final void updateMetricsOnResponseReceived(Map map, ComponentName componentName) {
        try {
            ProviderSession providerSession =
                    (ProviderSession) map.get(componentName.flattenToString());
            if (providerSession != null) {
                collectChosenMetricViaCandidateTransfer(
                        providerSession.mProviderSessionMetric.mCandidatePhasePerProviderMetric);
            }
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Exception upon candidate to chosen metric transfer: ",
                    "RequestSessionMetric");
        }
    }
}
