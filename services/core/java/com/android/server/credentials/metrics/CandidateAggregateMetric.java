package com.android.server.credentials.metrics;

import com.android.server.credentials.ProviderSession;
import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CandidateAggregateMetric {
    public final int mSessionIdProvider;
    public boolean mQueryReturned = false;
    public long mServiceBeganTimeNanoseconds = -1;
    public int mNumProviders = 0;
    public boolean mAuthReturned = false;
    public int mNumAuthEntriesTapped = 0;
    public ResponseCollective mAggregateCollectiveQuery =
            new ResponseCollective(Map.of(), Map.of());
    public ResponseCollective mAggregateCollectiveAuth = new ResponseCollective(Map.of(), Map.of());
    public long mMinProviderTimestampNanoseconds = -1;
    public long mMaxProviderTimestampNanoseconds = -1;
    public int mTotalQueryFailures = 0;
    public final Map mExceptionCountQuery = new LinkedHashMap();
    public final Map mExceptionCountAuth = new LinkedHashMap();

    public CandidateAggregateMetric(int i) {
        this.mSessionIdProvider = i;
    }

    public final void collectAverages(Map map) {
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) map;
        this.mNumProviders = concurrentHashMap.size();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator it = concurrentHashMap.values().iterator();
        long j = Long.MAX_VALUE;
        long j2 = Long.MIN_VALUE;
        while (it.hasNext()) {
            CandidatePhaseMetric candidatePhaseMetric =
                    ((ProviderSession) it.next())
                            .mProviderSessionMetric
                            .mCandidatePhasePerProviderMetric;
            if (candidatePhaseMetric.mCandidateUid == -1) {
                this.mNumProviders--;
            } else {
                if (this.mServiceBeganTimeNanoseconds == -1) {
                    this.mServiceBeganTimeNanoseconds =
                            candidatePhaseMetric.mServiceBeganTimeNanoseconds;
                }
                this.mQueryReturned = this.mQueryReturned || candidatePhaseMetric.mQueryReturned;
                ResponseCollective responseCollective = candidatePhaseMetric.mResponseCollective;
                ResponseCollective.combineTypeCountMaps(
                        linkedHashMap,
                        Collections.unmodifiableMap(responseCollective.mResponseCounts));
                ResponseCollective.combineTypeCountMaps(
                        linkedHashMap2,
                        Collections.unmodifiableMap(responseCollective.mEntryCounts));
                j = Math.min(j, candidatePhaseMetric.mStartQueryTimeNanoseconds);
                j2 = Math.max(j2, candidatePhaseMetric.mQueryFinishTimeNanoseconds);
                this.mTotalQueryFailures += candidatePhaseMetric.mHasException ? 1 : 0;
                if (!candidatePhaseMetric.mFrameworkException.isEmpty()) {
                    Map map2 = this.mExceptionCountQuery;
                    String str = candidatePhaseMetric.mFrameworkException;
                    map2.put(
                            str,
                            Integer.valueOf(
                                    ((Integer) ((LinkedHashMap) map2).getOrDefault(str, 0))
                                                    .intValue()
                                            + 1));
                }
            }
        }
        this.mMinProviderTimestampNanoseconds = j;
        this.mMaxProviderTimestampNanoseconds = j2;
        this.mAggregateCollectiveQuery = new ResponseCollective(linkedHashMap, linkedHashMap2);
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        LinkedHashMap linkedHashMap4 = new LinkedHashMap();
        Iterator it2 = concurrentHashMap.values().iterator();
        while (it2.hasNext()) {
            Iterator it3 =
                    ((ArrayList)
                                    ((ProviderSession) it2.next())
                                            .mProviderSessionMetric
                                            .mBrowsedAuthenticationMetric)
                            .iterator();
            while (it3.hasNext()) {
                BrowsedAuthenticationMetric browsedAuthenticationMetric =
                        (BrowsedAuthenticationMetric) it3.next();
                if (browsedAuthenticationMetric.mProviderUid != -1) {
                    this.mNumAuthEntriesTapped++;
                    this.mAuthReturned =
                            this.mAuthReturned || browsedAuthenticationMetric.mAuthReturned;
                    ResponseCollective responseCollective2 =
                            browsedAuthenticationMetric.mAuthEntryCollective;
                    ResponseCollective.combineTypeCountMaps(
                            linkedHashMap3,
                            Collections.unmodifiableMap(responseCollective2.mResponseCounts));
                    ResponseCollective.combineTypeCountMaps(
                            linkedHashMap4,
                            Collections.unmodifiableMap(responseCollective2.mEntryCounts));
                    this.mTotalQueryFailures += browsedAuthenticationMetric.mHasException ? 1 : 0;
                    String str2 = browsedAuthenticationMetric.mFrameworkException;
                    if (!str2.isEmpty()) {
                        Map map3 = this.mExceptionCountQuery;
                        map3.put(
                                str2,
                                Integer.valueOf(
                                        ((Integer) ((LinkedHashMap) map3).getOrDefault(str2, 0))
                                                        .intValue()
                                                + 1));
                    }
                }
            }
        }
        this.mAggregateCollectiveAuth = new ResponseCollective(linkedHashMap3, linkedHashMap4);
    }
}
