package com.android.server.credentials.metrics;

import android.content.ComponentName;
import android.credentials.CreateCredentialRequest;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialRequest;
import android.credentials.ui.UserSelectionDialogResult;
import android.util.Slog;
import com.android.server.credentials.MetricUtilities;
import com.android.server.credentials.ProviderSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class RequestSessionMetric {
    public final CandidateAggregateMetric mCandidateAggregateMetric;
    public final ChosenProviderFinalPhaseMetric mChosenProviderFinalPhaseMetric;
    public final InitialPhaseMetric mInitialPhaseMetric;
    public final int mSessionIdTrackTwo;
    public int mSequenceCounter = 0;
    public List mCandidateBrowsingPhaseMetric = new ArrayList();

    public RequestSessionMetric(int i, int i2) {
        this.mSessionIdTrackTwo = i2;
        this.mInitialPhaseMetric = new InitialPhaseMetric(i);
        this.mCandidateAggregateMetric = new CandidateAggregateMetric(i);
        this.mChosenProviderFinalPhaseMetric = new ChosenProviderFinalPhaseMetric(i, i2);
    }

    public int returnIncrementSequence() {
        int i = this.mSequenceCounter + 1;
        this.mSequenceCounter = i;
        return i;
    }

    public InitialPhaseMetric getInitialPhaseMetric() {
        return this.mInitialPhaseMetric;
    }

    public void collectInitialPhaseMetricInfo(long j, int i, int i2) {
        try {
            this.mInitialPhaseMetric.setCredentialServiceStartedTimeNanoseconds(j);
            this.mInitialPhaseMetric.setCallerUid(i);
            this.mInitialPhaseMetric.setApiName(i2);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting initial phase metric start info: " + e);
        }
    }

    public void collectUiReturnedFinalPhase(boolean z) {
        try {
            this.mChosenProviderFinalPhaseMetric.setUiReturned(z);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting ui end time metric: " + e);
        }
    }

    public void collectUiCallStartTime(long j) {
        try {
            this.mChosenProviderFinalPhaseMetric.setUiCallStartTimeNanoseconds(j);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting ui start metric: " + e);
        }
    }

    public void collectUiResponseData(boolean z, long j) {
        try {
            this.mChosenProviderFinalPhaseMetric.setUiReturned(z);
            this.mChosenProviderFinalPhaseMetric.setUiCallEndTimeNanoseconds(j);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting ui response metric: " + e);
        }
    }

    public void collectChosenProviderStatus(int i) {
        try {
            this.mChosenProviderFinalPhaseMetric.setChosenProviderStatus(i);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error setting chosen provider status metric: " + e);
        }
    }

    public void collectCreateFlowInitialMetricInfo(boolean z, CreateCredentialRequest createCredentialRequest) {
        try {
            this.mInitialPhaseMetric.setOriginSpecified(z);
            this.mInitialPhaseMetric.setRequestCounts(Map.of(MetricUtilities.generateMetricKey(createCredentialRequest.getType(), 20), 1));
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting create flow metric: " + e);
        }
    }

    public final Map getRequestCountMap(GetCredentialRequest getCredentialRequest) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            getCredentialRequest.getCredentialOptions().forEach(new Consumer() { // from class: com.android.server.credentials.metrics.RequestSessionMetric$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RequestSessionMetric.lambda$getRequestCountMap$0(linkedHashMap, (CredentialOption) obj);
                }
            });
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during get request count map metric logging: " + e);
        }
        return linkedHashMap;
    }

    public static /* synthetic */ void lambda$getRequestCountMap$0(Map map, CredentialOption credentialOption) {
        String generateMetricKey = MetricUtilities.generateMetricKey(credentialOption.getType(), 20);
        map.put(generateMetricKey, Integer.valueOf(((Integer) map.getOrDefault(generateMetricKey, 0)).intValue() + 1));
    }

    public void collectGetFlowInitialMetricInfo(GetCredentialRequest getCredentialRequest) {
        try {
            this.mInitialPhaseMetric.setOriginSpecified(getCredentialRequest.getOrigin() != null);
            this.mInitialPhaseMetric.setRequestCounts(getRequestCountMap(getCredentialRequest));
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting get flow initial metric: " + e);
        }
    }

    public void collectMetricPerBrowsingSelect(UserSelectionDialogResult userSelectionDialogResult, CandidatePhaseMetric candidatePhaseMetric) {
        try {
            CandidateBrowsingPhaseMetric candidateBrowsingPhaseMetric = new CandidateBrowsingPhaseMetric();
            candidateBrowsingPhaseMetric.setEntryEnum(EntryEnum.getMetricCodeFromString(userSelectionDialogResult.getEntryKey()));
            candidateBrowsingPhaseMetric.setProviderUid(candidatePhaseMetric.getCandidateUid());
            this.mCandidateBrowsingPhaseMetric.add(candidateBrowsingPhaseMetric);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error collecting browsing metric: " + e);
        }
    }

    public final void setHasExceptionFinalPhase(boolean z) {
        try {
            this.mChosenProviderFinalPhaseMetric.setHasException(z);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error setting final exception metric: " + e);
        }
    }

    public void collectFrameworkException(String str) {
        try {
            this.mChosenProviderFinalPhaseMetric.setFrameworkException(MetricUtilities.generateMetricKey(str, 30));
        } catch (Exception e) {
            Slog.w("RequestSessionMetric", "Unexpected error during metric logging: " + e);
        }
    }

    public void collectFinalPhaseProviderMetricStatus(boolean z, ProviderStatusForMetrics providerStatusForMetrics) {
        try {
            this.mChosenProviderFinalPhaseMetric.setHasException(z);
            this.mChosenProviderFinalPhaseMetric.setChosenProviderStatus(providerStatusForMetrics.getMetricCode());
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during final phase provider status metric logging: " + e);
        }
    }

    public void updateMetricsOnResponseReceived(Map map, ComponentName componentName, boolean z) {
        try {
            ProviderSession providerSession = (ProviderSession) map.get(componentName.flattenToString());
            if (providerSession != null) {
                collectChosenMetricViaCandidateTransfer(providerSession.getProviderSessionMetric().getCandidatePhasePerProviderMetric(), z);
            }
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Exception upon candidate to chosen metric transfer: " + e);
        }
    }

    public void collectChosenMetricViaCandidateTransfer(CandidatePhaseMetric candidatePhaseMetric, boolean z) {
        try {
            this.mChosenProviderFinalPhaseMetric.setChosenUid(candidatePhaseMetric.getCandidateUid());
            this.mChosenProviderFinalPhaseMetric.setPrimary(z);
            this.mChosenProviderFinalPhaseMetric.setQueryPhaseLatencyMicroseconds(candidatePhaseMetric.getQueryLatencyMicroseconds());
            this.mChosenProviderFinalPhaseMetric.setServiceBeganTimeNanoseconds(candidatePhaseMetric.getServiceBeganTimeNanoseconds());
            this.mChosenProviderFinalPhaseMetric.setQueryStartTimeNanoseconds(candidatePhaseMetric.getStartQueryTimeNanoseconds());
            this.mChosenProviderFinalPhaseMetric.setQueryEndTimeNanoseconds(candidatePhaseMetric.getQueryFinishTimeNanoseconds());
            this.mChosenProviderFinalPhaseMetric.setResponseCollective(candidatePhaseMetric.getResponseCollective());
            this.mChosenProviderFinalPhaseMetric.setFinalFinishTimeNanoseconds(System.nanoTime());
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during metric candidate to final transfer: " + e);
        }
    }

    public void logFailureOrUserCancel(boolean z) {
        try {
            if (z) {
                setHasExceptionFinalPhase(false);
                logApiCalledAtFinish(ApiStatus.USER_CANCELED.getMetricCode());
            } else {
                logApiCalledAtFinish(ApiStatus.FAILURE.getMetricCode());
            }
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during final metric failure emit: " + e);
        }
    }

    public void logCandidatePhaseMetrics(Map map) {
        try {
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            MetricUtilities.logApiCalledCandidatePhase(map, i, this.mInitialPhaseMetric);
            if (this.mInitialPhaseMetric.getApiName() == ApiName.GET_CREDENTIAL.getMetricCode() || this.mInitialPhaseMetric.getApiName() == ApiName.GET_CREDENTIAL_VIA_REGISTRY.getMetricCode()) {
                MetricUtilities.logApiCalledCandidateGetMetric(map, this.mSequenceCounter);
            }
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during candidate metric emit: " + e);
        }
    }

    public void logCandidateAggregateMetrics(Map map) {
        try {
            this.mCandidateAggregateMetric.collectAverages(map);
            CandidateAggregateMetric candidateAggregateMetric = this.mCandidateAggregateMetric;
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            MetricUtilities.logApiCalledAggregateCandidate(candidateAggregateMetric, i);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during aggregate candidate logging " + e);
        }
    }

    public void logAuthEntry(BrowsedAuthenticationMetric browsedAuthenticationMetric) {
        try {
            if (browsedAuthenticationMetric.getProviderUid() == -1) {
                Slog.v("RequestSessionMetric", "An authentication entry was not clicked");
                return;
            }
            int i = this.mSequenceCounter + 1;
            this.mSequenceCounter = i;
            MetricUtilities.logApiCalledAuthenticationMetric(browsedAuthenticationMetric, i);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during auth entry metric emit: " + e);
        }
    }

    public void logApiCalledAtFinish(int i) {
        try {
            ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric = this.mChosenProviderFinalPhaseMetric;
            List list = this.mCandidateBrowsingPhaseMetric;
            int i2 = this.mSequenceCounter + 1;
            this.mSequenceCounter = i2;
            MetricUtilities.logApiCalledFinalPhase(chosenProviderFinalPhaseMetric, list, i, i2);
            ChosenProviderFinalPhaseMetric chosenProviderFinalPhaseMetric2 = this.mChosenProviderFinalPhaseMetric;
            List list2 = this.mCandidateBrowsingPhaseMetric;
            int i3 = this.mSequenceCounter + 1;
            this.mSequenceCounter = i3;
            MetricUtilities.logApiCalledNoUidFinal(chosenProviderFinalPhaseMetric2, list2, i, i3);
        } catch (Exception e) {
            Slog.i("RequestSessionMetric", "Unexpected error during final metric emit: " + e);
        }
    }

    public int getSessionIdTrackTwo() {
        return this.mSessionIdTrackTwo;
    }
}
