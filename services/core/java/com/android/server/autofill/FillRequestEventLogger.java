package com.android.server.autofill;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FillRequestEventLogger {
    public Optional mEventInternal = Optional.empty();
    public final int mSessionId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FillRequestEventInternal {
        public int mAppPackageUid;
        public int mAutofillServiceUid;
        public int mFlags;
        public int mInlineSuggestionHostUid;
        public boolean mIsAugmented;
        public boolean mIsFillDialogEligible;
        public int mLatencyFillRequestSentMillis;
        public int mRequestId;
        public int mRequestTriggerReason;
    }

    public FillRequestEventLogger(int i) {
        this.mSessionId = i;
    }

    public final void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w(
                    "FillRequestEventLogger",
                    "Shouldn't be logging AutofillFillRequestReported again for same event");
            return;
        }
        FillRequestEventInternal fillRequestEventInternal =
                (FillRequestEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            StringBuilder sb = new StringBuilder("Log AutofillFillRequestReported: requestId=");
            sb.append(fillRequestEventInternal.mRequestId);
            sb.append(" sessionId=");
            sb.append(this.mSessionId);
            sb.append(" mAutofillServiceUid=");
            sb.append(fillRequestEventInternal.mAutofillServiceUid);
            sb.append(" mInlineSuggestionHostUid=");
            sb.append(fillRequestEventInternal.mInlineSuggestionHostUid);
            sb.append(" mIsAugmented=");
            sb.append(fillRequestEventInternal.mIsAugmented);
            sb.append(" mIsClientSuggestionFallback=false mIsFillDialogEligible=");
            sb.append(fillRequestEventInternal.mIsFillDialogEligible);
            sb.append(" mRequestTriggerReason=");
            sb.append(fillRequestEventInternal.mRequestTriggerReason);
            sb.append(" mFlags=");
            sb.append(fillRequestEventInternal.mFlags);
            sb.append(" mLatencyFillRequestSentMillis=");
            sb.append(fillRequestEventInternal.mLatencyFillRequestSentMillis);
            sb.append(" mAppPackageUid=");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb, fillRequestEventInternal.mAppPackageUid, "FillRequestEventLogger");
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED,
                fillRequestEventInternal.mRequestId,
                this.mSessionId,
                fillRequestEventInternal.mAutofillServiceUid,
                fillRequestEventInternal.mInlineSuggestionHostUid,
                fillRequestEventInternal.mIsAugmented,
                false,
                fillRequestEventInternal.mIsFillDialogEligible,
                fillRequestEventInternal.mRequestTriggerReason,
                fillRequestEventInternal.mFlags,
                fillRequestEventInternal.mLatencyFillRequestSentMillis,
                fillRequestEventInternal.mAppPackageUid);
        this.mEventInternal = Optional.empty();
    }

    public final void maybeSetRequestTriggerReason(int i) {
        this.mEventInternal.ifPresent(new FillRequestEventLogger$$ExternalSyntheticLambda0(i, 2));
    }

    public final void startLogForNewRequest() {
        if (!this.mEventInternal.isEmpty()) {
            Slog.w(
                    "FillRequestEventLogger",
                    "FillRequestEventLogger is not empty before starting for a new request");
        }
        FillRequestEventInternal fillRequestEventInternal = new FillRequestEventInternal();
        fillRequestEventInternal.mAppPackageUid = -1;
        fillRequestEventInternal.mAutofillServiceUid = -1;
        fillRequestEventInternal.mInlineSuggestionHostUid = -1;
        fillRequestEventInternal.mIsAugmented = false;
        fillRequestEventInternal.mIsFillDialogEligible = false;
        fillRequestEventInternal.mRequestTriggerReason = 0;
        fillRequestEventInternal.mFlags = -1;
        fillRequestEventInternal.mLatencyFillRequestSentMillis = -1;
        this.mEventInternal = Optional.of(fillRequestEventInternal);
    }
}
