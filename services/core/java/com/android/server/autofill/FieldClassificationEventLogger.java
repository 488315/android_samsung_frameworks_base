package com.android.server.autofill;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FieldClassificationEventLogger {
    public Optional mEventInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FieldClassificationEventInternal {
        public int mAppPackageUid;
        public int mCountClassifications;
        public boolean mIsSessionGc;
        public long mLatencyClassificationRequestMillis;
        public int mNextFillRequestId;
        public int mRequestId;
        public int mSessionId;
        public int mStatus;
    }

    public final void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w(
                    "FieldClassificationEventLogger",
                    "Shouldn't be logging AutofillFieldClassificationEventInternal again for same"
                        + " event");
            return;
        }
        FieldClassificationEventInternal fieldClassificationEventInternal =
                (FieldClassificationEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            StringBuilder sb =
                    new StringBuilder(
                            "Log AutofillFieldClassificationEventReported:"
                                + " mLatencyClassificationRequestMillis=");
            sb.append(fieldClassificationEventInternal.mLatencyClassificationRequestMillis);
            sb.append(" mCountClassifications=");
            sb.append(fieldClassificationEventInternal.mCountClassifications);
            sb.append(" mSessionId=");
            sb.append(fieldClassificationEventInternal.mSessionId);
            sb.append(" mRequestId=");
            sb.append(fieldClassificationEventInternal.mRequestId);
            sb.append(" mNextFillRequestId=");
            sb.append(fieldClassificationEventInternal.mNextFillRequestId);
            sb.append(" mAppPackageUid=");
            sb.append(fieldClassificationEventInternal.mAppPackageUid);
            sb.append(" mStatus=");
            sb.append(fieldClassificationEventInternal.mStatus);
            sb.append(" mIsSessionGc=");
            ProxyManager$$ExternalSyntheticOutline0.m(
                    "FieldClassificationEventLogger",
                    sb,
                    fieldClassificationEventInternal.mIsSessionGc);
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.AUTOFILL_FIELD_CLASSIFICATION_EVENT_REPORTED,
                fieldClassificationEventInternal.mLatencyClassificationRequestMillis,
                fieldClassificationEventInternal.mCountClassifications,
                fieldClassificationEventInternal.mSessionId,
                fieldClassificationEventInternal.mRequestId,
                fieldClassificationEventInternal.mNextFillRequestId,
                fieldClassificationEventInternal.mAppPackageUid,
                fieldClassificationEventInternal.mStatus,
                fieldClassificationEventInternal.mIsSessionGc);
        this.mEventInternal = Optional.empty();
    }

    public final void startNewLogForRequest() {
        if (!this.mEventInternal.isEmpty()) {
            Slog.w(
                    "FieldClassificationEventLogger",
                    "FieldClassificationEventLogger is not empty before starting for a new"
                        + " request");
        }
        FieldClassificationEventInternal fieldClassificationEventInternal =
                new FieldClassificationEventInternal();
        fieldClassificationEventInternal.mLatencyClassificationRequestMillis = -1L;
        fieldClassificationEventInternal.mCountClassifications = -1;
        fieldClassificationEventInternal.mSessionId = -1;
        fieldClassificationEventInternal.mRequestId = -1;
        fieldClassificationEventInternal.mNextFillRequestId = -1;
        fieldClassificationEventInternal.mAppPackageUid = -1;
        this.mEventInternal = Optional.of(fieldClassificationEventInternal);
    }
}
