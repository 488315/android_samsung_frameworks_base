package com.android.server.credentials.metrics;

import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CandidatePhaseMetric {
    public final int mSessionIdProvider;
    public boolean mQueryReturned = false;
    public int mCandidateUid = -1;
    public long mServiceBeganTimeNanoseconds = -1;
    public long mStartQueryTimeNanoseconds = -1;
    public long mQueryFinishTimeNanoseconds = -1;
    public int mProviderQueryStatus = -1;
    public boolean mHasException = false;
    public String mFrameworkException = "";
    public ResponseCollective mResponseCollective = new ResponseCollective(Map.of(), Map.of());
    public boolean mIsPrimary = false;

    public CandidatePhaseMetric(int i) {
        this.mSessionIdProvider = i;
    }
}
