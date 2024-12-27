package com.android.server.credentials.metrics;

import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.Map;

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
