package com.android.server.autofill;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import java.util.Optional;

public final class FillResponseEventLogger {
    public final int mSessionId;
    public long startResponseProcessingTimestamp = -1;
    public Optional mEventInternal = Optional.empty();

    public final class FillResponseEventInternal {
        public int mAppPackageUid;
        public int mAvailableCount;
        public int mAvailablePccCount;
        public int mAvailablePccOnlyCount;
        public int mDetectionPref;
        public int mLatencyFillResponseReceivedMillis;
        public long mLatencyResponseProcessingMillis;
        public int mRequestId;
        public int mResponseStatus;
        public int mSaveUiTriggerIds;
        public int mTotalDatasetsProvided;
    }

    public FillResponseEventLogger(int i) {
        this.mSessionId = i;
    }

    public final void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w(
                    "FillResponseEventLogger",
                    "Shouldn't be logging AutofillFillRequestReported again for same event");
            return;
        }
        FillResponseEventInternal fillResponseEventInternal =
                (FillResponseEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            StringBuilder sb = new StringBuilder("Log AutofillFillResponseReported: requestId=");
            sb.append(fillResponseEventInternal.mRequestId);
            sb.append(" sessionId=");
            sb.append(this.mSessionId);
            sb.append(" mAppPackageUid=");
            sb.append(fillResponseEventInternal.mAppPackageUid);
            sb.append(" mDisplayPresentationType=0 mAvailableCount=");
            sb.append(fillResponseEventInternal.mAvailableCount);
            sb.append(" mSaveUiTriggerIds=");
            sb.append(fillResponseEventInternal.mSaveUiTriggerIds);
            sb.append(" mLatencyFillResponseReceivedMillis=");
            sb.append(fillResponseEventInternal.mLatencyFillResponseReceivedMillis);
            sb.append(
                    " mAuthenticationType=0 mAuthenticationResult=0 mAuthenticationFailureReason=-1"
                        + " mLatencyAuthenticationUiDisplayMillis=-1"
                        + " mLatencyDatasetDisplayMillis=-1 mResponseStatus=");
            sb.append(fillResponseEventInternal.mResponseStatus);
            sb.append(" mLatencyResponseProcessingMillis=");
            sb.append(fillResponseEventInternal.mLatencyResponseProcessingMillis);
            sb.append(" mAvailablePccCount=");
            sb.append(fillResponseEventInternal.mAvailablePccCount);
            sb.append(" mAvailablePccOnlyCount=");
            sb.append(fillResponseEventInternal.mAvailablePccOnlyCount);
            sb.append(" mTotalDatasetsProvided=");
            sb.append(fillResponseEventInternal.mTotalDatasetsProvided);
            sb.append(" mDetectionPref=");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb, fillResponseEventInternal.mDetectionPref, "FillResponseEventLogger");
        }
        long j = -1;
        FrameworkStatsLog.write(
                FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED,
                fillResponseEventInternal.mRequestId,
                this.mSessionId,
                fillResponseEventInternal.mAppPackageUid,
                0,
                fillResponseEventInternal.mAvailableCount,
                fillResponseEventInternal.mSaveUiTriggerIds,
                fillResponseEventInternal.mLatencyFillResponseReceivedMillis,
                0,
                0,
                j,
                j,
                j,
                fillResponseEventInternal.mResponseStatus,
                fillResponseEventInternal.mLatencyResponseProcessingMillis,
                fillResponseEventInternal.mAvailablePccCount,
                fillResponseEventInternal.mAvailablePccOnlyCount,
                fillResponseEventInternal.mTotalDatasetsProvided,
                fillResponseEventInternal.mDetectionPref);
        this.mEventInternal = Optional.empty();
    }

    public final void maybeSetResponseStatus(int i) {
        this.mEventInternal.ifPresent(new FillResponseEventLogger$$ExternalSyntheticLambda0(i, 3));
    }

    public final void startLogForNewResponse() {
        if (!this.mEventInternal.isEmpty()) {
            Slog.w(
                    "FillResponseEventLogger",
                    "FillResponseEventLogger is not empty before starting for a new request");
        }
        FillResponseEventInternal fillResponseEventInternal = new FillResponseEventInternal();
        fillResponseEventInternal.mRequestId = -1;
        fillResponseEventInternal.mAppPackageUid = -1;
        fillResponseEventInternal.mAvailableCount = 0;
        fillResponseEventInternal.mSaveUiTriggerIds = -1;
        fillResponseEventInternal.mLatencyFillResponseReceivedMillis = -1;
        fillResponseEventInternal.mResponseStatus = 0;
        fillResponseEventInternal.mLatencyResponseProcessingMillis = -1L;
        fillResponseEventInternal.mAvailablePccCount = -1;
        fillResponseEventInternal.mAvailablePccOnlyCount = -1;
        fillResponseEventInternal.mTotalDatasetsProvided = -1;
        fillResponseEventInternal.mDetectionPref = 0;
        this.mEventInternal = Optional.of(fillResponseEventInternal);
    }
}
