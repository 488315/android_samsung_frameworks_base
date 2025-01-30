package com.android.server.credentials.metrics;

import android.util.Slog;
import com.android.server.credentials.metrics.shared.ResponseCollective;
import java.util.Map;

/* loaded from: classes.dex */
public class CandidatePhaseMetric {
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

    public void setServiceBeganTimeNanoseconds(long j) {
        this.mServiceBeganTimeNanoseconds = j;
    }

    public void setStartQueryTimeNanoseconds(long j) {
        this.mStartQueryTimeNanoseconds = j;
    }

    public void setQueryFinishTimeNanoseconds(long j) {
        this.mQueryFinishTimeNanoseconds = j;
    }

    public long getServiceBeganTimeNanoseconds() {
        return this.mServiceBeganTimeNanoseconds;
    }

    public long getStartQueryTimeNanoseconds() {
        return this.mStartQueryTimeNanoseconds;
    }

    public long getQueryFinishTimeNanoseconds() {
        return this.mQueryFinishTimeNanoseconds;
    }

    public int getQueryLatencyMicroseconds() {
        return (int) ((getQueryFinishTimeNanoseconds() - getStartQueryTimeNanoseconds()) / 1000);
    }

    public int getTimestampFromReferenceStartMicroseconds(long j) {
        long j2 = this.mServiceBeganTimeNanoseconds;
        if (j < j2) {
            Slog.i("CandidateProviderMetric", "The timestamp is before service started, falling back to default int");
            return -1;
        }
        return (int) ((j - j2) / 1000);
    }

    public void setProviderQueryStatus(int i) {
        this.mProviderQueryStatus = i;
    }

    public int getProviderQueryStatus() {
        return this.mProviderQueryStatus;
    }

    public void setCandidateUid(int i) {
        this.mCandidateUid = i;
    }

    public int getCandidateUid() {
        return this.mCandidateUid;
    }

    public int getSessionIdProvider() {
        return this.mSessionIdProvider;
    }

    public void setQueryReturned(boolean z) {
        this.mQueryReturned = z;
    }

    public boolean isQueryReturned() {
        return this.mQueryReturned;
    }

    public void setHasException(boolean z) {
        this.mHasException = z;
    }

    public boolean isHasException() {
        return this.mHasException;
    }

    public void setResponseCollective(ResponseCollective responseCollective) {
        this.mResponseCollective = responseCollective;
    }

    public ResponseCollective getResponseCollective() {
        return this.mResponseCollective;
    }

    public void setFrameworkException(String str) {
        this.mFrameworkException = str;
    }

    public String getFrameworkException() {
        return this.mFrameworkException;
    }

    public void setPrimary(boolean z) {
        this.mIsPrimary = z;
    }

    public boolean isPrimary() {
        return this.mIsPrimary;
    }
}
