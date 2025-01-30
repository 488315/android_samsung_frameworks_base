package com.android.server.autofill;

import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.autofill.SaveEventLogger;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class SaveEventLogger {
    public Optional mEventInternal = Optional.of(new SaveEventInternal());
    public final int mSessionId;

    public final class SaveEventInternal {
        public int mRequestId;
        public int mAppPackageUid = -1;
        public int mSaveUiTriggerIds = -1;
        public long mFlag = -1;
        public boolean mIsNewField = false;
        public int mSaveUiShownReason = 0;
        public int mSaveUiNotShownReason = 0;
        public boolean mSaveButtonClicked = false;
        public boolean mCancelButtonClicked = false;
        public boolean mDialogDismissed = false;
        public boolean mIsSaved = false;
        public long mLatencySaveUiDisplayMillis = 0;
        public long mLatencySaveRequestMillis = 0;
        public long mLatencySaveFinishMillis = 0;
        public boolean mIsFrameworkCreatedSaveInfo = false;
    }

    public SaveEventLogger(int i) {
        this.mSessionId = i;
    }

    public static SaveEventLogger forSessionId(int i) {
        return new SaveEventLogger(i);
    }

    public void maybeSetRequestId(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mRequestId = i;
            }
        });
    }

    public void maybeSetAppPackageUid(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mAppPackageUid = i;
            }
        });
    }

    public void maybeSetSaveUiTriggerIds(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mSaveUiTriggerIds = i;
            }
        });
    }

    public void maybeSetFlag(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SaveEventLogger.lambda$maybeSetFlag$3(i, (SaveEventLogger.SaveEventInternal) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$maybeSetFlag$3(int i, SaveEventInternal saveEventInternal) {
        saveEventInternal.mFlag = i;
    }

    public void maybeSetIsNewField(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mIsNewField = z;
            }
        });
    }

    public void maybeSetSaveUiShownReason(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mSaveUiShownReason = i;
            }
        });
    }

    public void maybeSetSaveUiNotShownReason(final int i) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mSaveUiNotShownReason = i;
            }
        });
    }

    public void maybeSetSaveButtonClicked(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mSaveButtonClicked = z;
            }
        });
    }

    public void maybeSetCancelButtonClicked(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mCancelButtonClicked = z;
            }
        });
    }

    public void maybeSetDialogDismissed(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mDialogDismissed = z;
            }
        });
    }

    public void maybeSetIsSaved(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mIsSaved = z;
            }
        });
    }

    public void maybeSetLatencySaveUiDisplayMillis(final long j) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mLatencySaveUiDisplayMillis = j;
            }
        });
    }

    public void maybeSetLatencySaveRequestMillis(final long j) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mLatencySaveRequestMillis = j;
            }
        });
    }

    public void maybeSetLatencySaveFinishMillis(final long j) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mLatencySaveFinishMillis = j;
            }
        });
    }

    public void maybeSetIsFrameworkCreatedSaveInfo(final boolean z) {
        this.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.SaveEventLogger$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SaveEventLogger.SaveEventInternal) obj).mIsFrameworkCreatedSaveInfo = z;
            }
        });
    }

    public void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w("SaveEventLogger", "Shouldn't be logging AutofillSaveEventReported again for same event");
            return;
        }
        SaveEventInternal saveEventInternal = (SaveEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            Slog.v("SaveEventLogger", "Log AutofillSaveEventReported: requestId=" + saveEventInternal.mRequestId + " sessionId=" + this.mSessionId + " mAppPackageUid=" + saveEventInternal.mAppPackageUid + " mSaveUiTriggerIds=" + saveEventInternal.mSaveUiTriggerIds + " mFlag=" + saveEventInternal.mFlag + " mIsNewField=" + saveEventInternal.mIsNewField + " mSaveUiShownReason=" + saveEventInternal.mSaveUiShownReason + " mSaveUiNotShownReason=" + saveEventInternal.mSaveUiNotShownReason + " mSaveButtonClicked=" + saveEventInternal.mSaveButtonClicked + " mCancelButtonClicked=" + saveEventInternal.mCancelButtonClicked + " mDialogDismissed=" + saveEventInternal.mDialogDismissed + " mIsSaved=" + saveEventInternal.mIsSaved + " mLatencySaveUiDisplayMillis=" + saveEventInternal.mLatencySaveUiDisplayMillis + " mLatencySaveRequestMillis=" + saveEventInternal.mLatencySaveRequestMillis + " mLatencySaveFinishMillis=" + saveEventInternal.mLatencySaveFinishMillis + " mIsFrameworkCreatedSaveInfo=" + saveEventInternal.mIsFrameworkCreatedSaveInfo);
        }
        FrameworkStatsLog.write(FrameworkStatsLog.AUTOFILL_SAVE_EVENT_REPORTED, saveEventInternal.mRequestId, this.mSessionId, saveEventInternal.mAppPackageUid, saveEventInternal.mSaveUiTriggerIds, saveEventInternal.mFlag, saveEventInternal.mIsNewField, saveEventInternal.mSaveUiShownReason, saveEventInternal.mSaveUiNotShownReason, saveEventInternal.mSaveButtonClicked, saveEventInternal.mCancelButtonClicked, saveEventInternal.mDialogDismissed, saveEventInternal.mIsSaved, saveEventInternal.mLatencySaveUiDisplayMillis, saveEventInternal.mLatencySaveRequestMillis, saveEventInternal.mLatencySaveFinishMillis, saveEventInternal.mIsFrameworkCreatedSaveInfo);
        this.mEventInternal = Optional.empty();
    }
}
