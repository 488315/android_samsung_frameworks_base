package com.android.server.credentials.metrics;

import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.credentials.ProviderSession;
import com.android.server.credentials.metrics.shared.ResponseCollective;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CandidateAggregateMetric {
    public final int mSessionIdProvider;
    public boolean mQueryReturned = false;
    public long mServiceBeganTimeNanoseconds = -1;
    public int mNumProviders = 0;
    public boolean mAuthReturned = false;
    public int mNumAuthEntriesTapped = 0;
    public ResponseCollective mAggregateCollectiveQuery = new ResponseCollective(Map.of(), Map.of());
    public ResponseCollective mAggregateCollectiveAuth = new ResponseCollective(Map.of(), Map.of());
    public long mMinProviderTimestampNanoseconds = -1;
    public long mMaxProviderTimestampNanoseconds = -1;
    public int mTotalQueryFailures = 0;
    public Map mExceptionCountQuery = new LinkedHashMap();
    public int mTotalAuthFailures = 0;
    public Map mExceptionCountAuth = new LinkedHashMap();

    public CandidateAggregateMetric(int i) {
        this.mSessionIdProvider = i;
    }

    public int getSessionIdProvider() {
        return this.mSessionIdProvider;
    }

    public void collectAverages(Map map) {
        collectQueryAggregates(map);
        collectAuthAggregates(map);
    }

    public final void collectQueryAggregates(Map map) {
        this.mNumProviders = map.size();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator it = map.values().iterator();
        long j = Long.MAX_VALUE;
        long j2 = Long.MIN_VALUE;
        while (it.hasNext()) {
            CandidatePhaseMetric candidatePhasePerProviderMetric = ((ProviderSession) it.next()).getProviderSessionMetric().getCandidatePhasePerProviderMetric();
            if (candidatePhasePerProviderMetric.getCandidateUid() == -1) {
                this.mNumProviders--;
            } else {
                if (this.mServiceBeganTimeNanoseconds == -1) {
                    this.mServiceBeganTimeNanoseconds = candidatePhasePerProviderMetric.getServiceBeganTimeNanoseconds();
                }
                this.mQueryReturned = this.mQueryReturned || candidatePhasePerProviderMetric.isQueryReturned();
                ResponseCollective responseCollective = candidatePhasePerProviderMetric.getResponseCollective();
                ResponseCollective.combineTypeCountMaps(linkedHashMap, responseCollective.getResponseCountsMap());
                ResponseCollective.combineTypeCountMaps(linkedHashMap2, responseCollective.getEntryCountsMap());
                j = Math.min(j, candidatePhasePerProviderMetric.getStartQueryTimeNanoseconds());
                j2 = Math.max(j2, candidatePhasePerProviderMetric.getQueryFinishTimeNanoseconds());
                this.mTotalQueryFailures += candidatePhasePerProviderMetric.isHasException() ? 1 : 0;
                if (!candidatePhasePerProviderMetric.getFrameworkException().isEmpty()) {
                    this.mExceptionCountQuery.put(candidatePhasePerProviderMetric.getFrameworkException(), Integer.valueOf(((Integer) this.mExceptionCountQuery.getOrDefault(candidatePhasePerProviderMetric.getFrameworkException(), 0)).intValue() + 1));
                }
            }
        }
        this.mMinProviderTimestampNanoseconds = j;
        this.mMaxProviderTimestampNanoseconds = j2;
        this.mAggregateCollectiveQuery = new ResponseCollective(linkedHashMap, linkedHashMap2);
    }

    public final void collectAuthAggregates(Map map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            for (BrowsedAuthenticationMetric browsedAuthenticationMetric : ((ProviderSession) it.next()).getProviderSessionMetric().getBrowsedAuthenticationMetric()) {
                if (browsedAuthenticationMetric.getProviderUid() != -1) {
                    this.mNumAuthEntriesTapped++;
                    this.mAuthReturned = this.mAuthReturned || browsedAuthenticationMetric.isAuthReturned();
                    ResponseCollective authEntryCollective = browsedAuthenticationMetric.getAuthEntryCollective();
                    ResponseCollective.combineTypeCountMaps(linkedHashMap, authEntryCollective.getResponseCountsMap());
                    ResponseCollective.combineTypeCountMaps(linkedHashMap2, authEntryCollective.getEntryCountsMap());
                    this.mTotalQueryFailures += browsedAuthenticationMetric.isHasException() ? 1 : 0;
                    if (!browsedAuthenticationMetric.getFrameworkException().isEmpty()) {
                        this.mExceptionCountQuery.put(browsedAuthenticationMetric.getFrameworkException(), Integer.valueOf(((Integer) this.mExceptionCountQuery.getOrDefault(browsedAuthenticationMetric.getFrameworkException(), 0)).intValue() + 1));
                    }
                }
            }
        }
        this.mAggregateCollectiveAuth = new ResponseCollective(linkedHashMap, linkedHashMap2);
    }

    public int getNumProviders() {
        return this.mNumProviders;
    }

    public boolean isQueryReturned() {
        return this.mQueryReturned;
    }

    public int getNumAuthEntriesTapped() {
        return this.mNumAuthEntriesTapped;
    }

    public ResponseCollective getAggregateCollectiveQuery() {
        return this.mAggregateCollectiveQuery;
    }

    public ResponseCollective getAggregateCollectiveAuth() {
        return this.mAggregateCollectiveAuth;
    }

    public boolean isAuthReturned() {
        return this.mAuthReturned;
    }

    public long getMaxProviderTimestampNanoseconds() {
        return this.mMaxProviderTimestampNanoseconds;
    }

    public long getMinProviderTimestampNanoseconds() {
        return this.mMinProviderTimestampNanoseconds;
    }

    public int getTotalQueryFailures() {
        return this.mTotalQueryFailures;
    }

    public String[] getUniqueExceptionStringsQuery() {
        String[] strArr = new String[this.mExceptionCountQuery.keySet().size()];
        this.mExceptionCountQuery.keySet().toArray(strArr);
        return strArr;
    }

    public int[] getUniqueExceptionCountsQuery() {
        return this.mExceptionCountQuery.values().stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public String[] getUniqueExceptionStringsAuth() {
        String[] strArr = new String[this.mExceptionCountAuth.keySet().size()];
        this.mExceptionCountAuth.keySet().toArray(strArr);
        return strArr;
    }

    public int[] getUniqueExceptionCountsAuth() {
        return this.mExceptionCountAuth.values().stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public long getServiceBeganTimeNanoseconds() {
        return this.mServiceBeganTimeNanoseconds;
    }

    public int getTotalAuthFailures() {
        return this.mTotalAuthFailures;
    }
}
