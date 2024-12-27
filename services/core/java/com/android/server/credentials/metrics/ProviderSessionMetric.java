package com.android.server.credentials.metrics;

import android.service.credentials.BeginCreateCredentialResponse;
import android.service.credentials.BeginGetCredentialResponse;
import android.service.credentials.CreateEntry;
import android.util.Slog;

import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProviderSessionMetric {
    public final List mBrowsedAuthenticationMetric;
    public final CandidatePhaseMetric mCandidatePhasePerProviderMetric;

    public ProviderSessionMetric(int i) {
        ArrayList arrayList = new ArrayList();
        this.mBrowsedAuthenticationMetric = arrayList;
        this.mCandidatePhasePerProviderMetric = new CandidatePhaseMetric(i);
        arrayList.add(new BrowsedAuthenticationMetric(i));
    }

    public final void beginCreateCredentialResponseCollectionCandidateEntryMetrics(
            BeginCreateCredentialResponse beginCreateCredentialResponse,
            InitialPhaseMetric initialPhaseMetric) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<CreateEntry> createEntries = beginCreateCredentialResponse.getCreateEntries();
        int i = beginCreateCredentialResponse.getRemoteCreateEntry() == null ? 0 : 1;
        int size = createEntries.size();
        linkedHashMap.put(EntryEnum.REMOTE_ENTRY, Integer.valueOf(i));
        linkedHashMap.put(EntryEnum.CREDENTIAL_ENTRY, Integer.valueOf(size));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        String[] uniqueRequestStrings =
                initialPhaseMetric == null
                        ? new String[0]
                        : initialPhaseMetric.getUniqueRequestStrings();
        if (uniqueRequestStrings.length > 0) {
            linkedHashMap2.put(
                    uniqueRequestStrings[0],
                    Integer.valueOf(initialPhaseMetric.getUniqueRequestCounts()[0]));
        }
        this.mCandidatePhasePerProviderMetric.mResponseCollective =
                new ResponseCollective(linkedHashMap2, linkedHashMap);
    }

    public final void beginGetCredentialResponseCollectionCandidateEntryMetrics(
            BeginGetCredentialResponse beginGetCredentialResponse, boolean z) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        int size = beginGetCredentialResponse.getCredentialEntries().size();
        int size2 = beginGetCredentialResponse.getActions().size();
        int size3 = beginGetCredentialResponse.getAuthenticationActions().size();
        linkedHashMap.put(
                EntryEnum.REMOTE_ENTRY,
                Integer.valueOf(
                        beginGetCredentialResponse.getRemoteCredentialEntry() != null ? 0 : 1));
        linkedHashMap.put(EntryEnum.CREDENTIAL_ENTRY, Integer.valueOf(size));
        linkedHashMap.put(EntryEnum.ACTION_ENTRY, Integer.valueOf(size2));
        linkedHashMap.put(EntryEnum.AUTHENTICATION_ENTRY, Integer.valueOf(size3));
        beginGetCredentialResponse
                .getCredentialEntries()
                .forEach(new ProviderSessionMetric$$ExternalSyntheticLambda0(1, linkedHashMap2));
        ResponseCollective responseCollective =
                new ResponseCollective(linkedHashMap2, linkedHashMap);
        if (!z) {
            this.mCandidatePhasePerProviderMetric.mResponseCollective = responseCollective;
        } else {
            ArrayList arrayList = (ArrayList) this.mBrowsedAuthenticationMetric;
            ((BrowsedAuthenticationMetric) arrayList.get(arrayList.size() - 1))
                            .mAuthEntryCollective =
                    responseCollective;
        }
    }

    public final void collectCandidateEntryMetrics(
            Object obj, boolean z, InitialPhaseMetric initialPhaseMetric) {
        try {
            if (obj instanceof BeginGetCredentialResponse) {
                beginGetCredentialResponseCollectionCandidateEntryMetrics(
                        (BeginGetCredentialResponse) obj, z);
            } else if (obj instanceof BeginCreateCredentialResponse) {
                beginCreateCredentialResponseCollectionCandidateEntryMetrics(
                        (BeginCreateCredentialResponse) obj, initialPhaseMetric);
            } else {
                Slog.i(
                        "ProviderSessionMetric",
                        "Your response type is unsupported for candidate metric logging");
            }
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during candidate entry metric logging: ",
                    "ProviderSessionMetric");
        }
    }

    public final void collectCandidateExceptionStatus() {
        try {
            this.mCandidatePhasePerProviderMetric.mHasException = true;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e, "Error while setting candidate metric exception ", "ProviderSessionMetric");
        }
    }

    public final void collectCandidateFrameworkException(String str) {
        try {
            this.mCandidatePhasePerProviderMetric.mFrameworkException = str;
        } catch (Exception e) {
            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(
                    e,
                    "Unexpected error during candidate exception metric logging: ",
                    "ProviderSessionMetric");
        }
    }
}
