package com.android.server.credentials.metrics;

import android.util.Slog;

import com.android.server.credentials.metrics.shared.ResponseCollective;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ChosenProviderFinalPhaseMetric {
    public final int mSessionIdCaller;
    public final int mSessionIdProvider;
    public boolean mUiReturned = false;
    public int mChosenUid = -1;
    public long mServiceBeganTimeNanoseconds = -1;
    public long mQueryStartTimeNanoseconds = -1;
    public long mQueryEndTimeNanoseconds = -1;
    public long mUiCallStartTimeNanoseconds = -1;
    public long mUiCallEndTimeNanoseconds = -1;
    public long mFinalFinishTimeNanoseconds = -1;
    public int mOemUiUid = -1;
    public int mFallbackUiUid = -1;
    public OemUiUsageStatus mOemUiUsageStatus = OemUiUsageStatus.UNKNOWN;
    public int mChosenProviderStatus = -1;
    public boolean mHasException = false;
    public String mFrameworkException = "";
    public ResponseCollective mResponseCollective = new ResponseCollective(Map.of(), Map.of());
    public boolean mIsPrimary = false;

    public ChosenProviderFinalPhaseMetric(int i, int i2) {
        this.mSessionIdCaller = i;
        this.mSessionIdProvider = i2;
    }

    public final int getTimestampFromReferenceStartMicroseconds(long j) {
        long j2 = this.mServiceBeganTimeNanoseconds;
        if (j >= j2) {
            return (int) ((j - j2) / 1000);
        }
        Slog.i(
                "ChosenFinalPhaseMetric",
                "The timestamp is before service started, falling back to default int");
        return -1;
    }
}
