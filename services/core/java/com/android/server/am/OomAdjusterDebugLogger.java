package com.android.server.am;

import android.app.StackTrace;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

public final class OomAdjusterDebugLogger {
    public final ActivityManagerConstants mConstants;
    public final OomAdjuster mOomAdjuster;

    public OomAdjusterDebugLogger(
            OomAdjuster oomAdjuster, ActivityManagerConstants activityManagerConstants) {
        this.mOomAdjuster = oomAdjuster;
        this.mConstants = activityManagerConstants;
    }

    public final void maybeLogStacktrace(String str) {
        if (this.mConstants.mEnableProcStateStacktrace) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ": ");
            m.append(OomAdjuster.oomAdjReasonToString(this.mOomAdjuster.mLastReason));
            Slog.i("am_stack", m.toString(), new StackTrace("Called here"));
        }
    }

    public final boolean shouldLog(int i) {
        SparseBooleanArray sparseBooleanArray = this.mConstants.mProcStateDebugUids;
        int size = sparseBooleanArray.size();
        if (size == 0) {
            return false;
        }
        if (size > 8) {
            return sparseBooleanArray.get(i, false);
        }
        for (int i2 = 0; i2 < size; i2++) {
            if (sparseBooleanArray.keyAt(i2) == i) {
                return sparseBooleanArray.valueAt(i2);
            }
        }
        return false;
    }
}
