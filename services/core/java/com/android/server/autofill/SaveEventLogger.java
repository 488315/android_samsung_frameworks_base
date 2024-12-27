package com.android.server.autofill;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SaveEventLogger {
    public Optional mEventInternal;
    public final int mSessionId;
    public final long mSessionStartTimestamp;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SaveEventInternal {
        public int mAppPackageUid;
        public boolean mCancelButtonClicked;
        public boolean mDialogDismissed;
        public long mFlag;
        public boolean mIsFrameworkCreatedSaveInfo;
        public boolean mIsNewField;
        public boolean mIsSaved;
        public long mLatencySaveFinishMillis;
        public long mLatencySaveRequestMillis;
        public long mLatencySaveUiDisplayMillis;
        public int mRequestId;
        public boolean mSaveButtonClicked;
        public int mSaveUiNotShownReason;
        public int mSaveUiShownReason;
        public int mSaveUiTriggerIds;
        public int mServiceUid;
    }

    public SaveEventLogger(int i, long j) {
        this.mSessionId = i;
        SaveEventInternal saveEventInternal = new SaveEventInternal();
        saveEventInternal.mAppPackageUid = -1;
        saveEventInternal.mSaveUiTriggerIds = -1;
        saveEventInternal.mFlag = -1L;
        saveEventInternal.mIsNewField = false;
        saveEventInternal.mSaveUiShownReason = 0;
        saveEventInternal.mSaveUiNotShownReason = 0;
        saveEventInternal.mSaveButtonClicked = false;
        saveEventInternal.mCancelButtonClicked = false;
        saveEventInternal.mDialogDismissed = false;
        saveEventInternal.mIsSaved = false;
        saveEventInternal.mLatencySaveUiDisplayMillis = Long.MIN_VALUE;
        saveEventInternal.mLatencySaveRequestMillis = Long.MIN_VALUE;
        saveEventInternal.mLatencySaveFinishMillis = Long.MIN_VALUE;
        saveEventInternal.mIsFrameworkCreatedSaveInfo = false;
        saveEventInternal.mServiceUid = -1;
        this.mEventInternal = Optional.of(saveEventInternal);
        this.mSessionStartTimestamp = j;
    }

    public final void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w(
                    "SaveEventLogger",
                    "Shouldn't be logging AutofillSaveEventReported again for same event");
            return;
        }
        SaveEventInternal saveEventInternal = (SaveEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            StringBuilder sb = new StringBuilder("Log AutofillSaveEventReported: requestId=");
            sb.append(saveEventInternal.mRequestId);
            sb.append(" sessionId=");
            sb.append(this.mSessionId);
            sb.append(" mAppPackageUid=");
            sb.append(saveEventInternal.mAppPackageUid);
            sb.append(" mSaveUiTriggerIds=");
            sb.append(saveEventInternal.mSaveUiTriggerIds);
            sb.append(" mFlag=");
            sb.append(saveEventInternal.mFlag);
            sb.append(" mIsNewField=");
            sb.append(saveEventInternal.mIsNewField);
            sb.append(" mSaveUiShownReason=");
            sb.append(saveEventInternal.mSaveUiShownReason);
            sb.append(" mSaveUiNotShownReason=");
            sb.append(saveEventInternal.mSaveUiNotShownReason);
            sb.append(" mSaveButtonClicked=");
            sb.append(saveEventInternal.mSaveButtonClicked);
            sb.append(" mCancelButtonClicked=");
            sb.append(saveEventInternal.mCancelButtonClicked);
            sb.append(" mDialogDismissed=");
            sb.append(saveEventInternal.mDialogDismissed);
            sb.append(" mIsSaved=");
            sb.append(saveEventInternal.mIsSaved);
            sb.append(" mLatencySaveUiDisplayMillis=");
            sb.append(saveEventInternal.mLatencySaveUiDisplayMillis);
            sb.append(" mLatencySaveRequestMillis=");
            sb.append(saveEventInternal.mLatencySaveRequestMillis);
            sb.append(" mLatencySaveFinishMillis=");
            sb.append(saveEventInternal.mLatencySaveFinishMillis);
            sb.append(" mIsFrameworkCreatedSaveInfo=");
            sb.append(saveEventInternal.mIsFrameworkCreatedSaveInfo);
            sb.append(" mServiceUid=");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb, saveEventInternal.mServiceUid, "SaveEventLogger");
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.AUTOFILL_SAVE_EVENT_REPORTED,
                saveEventInternal.mRequestId,
                this.mSessionId,
                saveEventInternal.mAppPackageUid,
                saveEventInternal.mSaveUiTriggerIds,
                saveEventInternal.mFlag,
                saveEventInternal.mIsNewField,
                saveEventInternal.mSaveUiShownReason,
                saveEventInternal.mSaveUiNotShownReason,
                saveEventInternal.mSaveButtonClicked,
                saveEventInternal.mCancelButtonClicked,
                saveEventInternal.mDialogDismissed,
                saveEventInternal.mIsSaved,
                saveEventInternal.mLatencySaveUiDisplayMillis,
                saveEventInternal.mLatencySaveRequestMillis,
                saveEventInternal.mLatencySaveFinishMillis,
                saveEventInternal.mIsFrameworkCreatedSaveInfo,
                saveEventInternal.mServiceUid);
        this.mEventInternal = Optional.empty();
    }

    public final void maybeSetSaveUiNotShownReason(int i) {
        this.mEventInternal.ifPresent(new SaveEventLogger$$ExternalSyntheticLambda1(i, 4));
    }
}
