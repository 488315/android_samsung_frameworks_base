package com.android.server.display.mode;

import android.os.Trace;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VotesStatsReporter {
    public final boolean mFrameworkStatsLogReportingEnabled;
    public final boolean mIgnoredRenderRate;
    public int mLastMinPriorityReported = 25;

    public VotesStatsReporter(boolean z, boolean z2) {
        this.mIgnoredRenderRate = z;
        this.mFrameworkStatsLogReportingEnabled = z2;
    }

    public static int getMaxRefreshRate(Vote vote, boolean z) {
        float f;
        if (vote instanceof RefreshRateVote.PhysicalVote) {
            f = ((RefreshRateVote.PhysicalVote) vote).mMaxRefreshRate;
        } else {
            if (z || !(vote instanceof RefreshRateVote.RenderVote)) {
                if (vote instanceof SupportedRefreshRatesVote) {
                    Iterator it = ((SupportedRefreshRatesVote) vote).mRefreshRates.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        i =
                                Math.max(
                                        i,
                                        (int)
                                                ((SupportedRefreshRatesVote.RefreshRates) it.next())
                                                        .mPeakRefreshRate);
                    }
                    return i;
                }
                int i2 = 1000;
                if (vote instanceof CombinedVote) {
                    Iterator it2 = ((CombinedVote) vote).mVotes.iterator();
                    while (it2.hasNext()) {
                        i2 = Math.min(i2, getMaxRefreshRate((Vote) it2.next(), z));
                    }
                }
                return i2;
            }
            f = ((RefreshRateVote.RenderVote) vote).mMaxRefreshRate;
        }
        return (int) f;
    }

    public final void reportVoteChanged(int i, int i2, Vote vote) {
        boolean z = this.mFrameworkStatsLogReportingEnabled;
        if (vote == null) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "VotesStatsReporter.", ":");
            m.append(Vote.priorityToString(i2));
            Trace.traceCounter(131072L, m.toString(), -1);
            if (z) {
                FrameworkStatsLog.write(
                        FrameworkStatsLog.DISPLAY_MODE_DIRECTOR_VOTE_CHANGED, i, i2, 3, -1, -1);
                return;
            }
            return;
        }
        int maxRefreshRate = getMaxRefreshRate(vote, this.mIgnoredRenderRate);
        StringBuilder m2 =
                BatteryService$$ExternalSyntheticOutline0.m(i, "VotesStatsReporter.", ":");
        m2.append(Vote.priorityToString(i2));
        Trace.traceCounter(131072L, m2.toString(), maxRefreshRate);
        if (z) {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.DISPLAY_MODE_DIRECTOR_VOTE_CHANGED,
                    i,
                    i2,
                    1,
                    maxRefreshRate,
                    -1);
        }
    }
}
