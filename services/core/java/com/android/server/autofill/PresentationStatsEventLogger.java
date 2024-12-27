package com.android.server.autofill;

import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PresentationStatsEventLogger {
    public final int mCallingAppUid;
    public Optional mEventInternal = Optional.empty();
    public final int mSessionId;
    public final long mSessionStartTimestamp;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PresentationStatsEventInternal {
        public ArraySet mAlreadyFilledAutofillIds;
        public int mAuthenticationResult;
        public int mAuthenticationType;
        public ArraySet mAutofillIdsAttemptedAutofill;
        public int mAutofillServiceUid;
        public int mAutofilledTimestampMs;
        public int mAvailableCount;
        public int mAvailablePccCount;
        public int mAvailablePccOnlyCount;
        public int mCountShown;
        public int mDetectionPreference;
        public boolean mDialogDismissed;
        public int mDisplayPresentationType;
        public int mFieldClassificationRequestId;
        public int mFieldFirstLength;
        public int mFieldLastLength;
        public int mFieldModifiedFirstTimestampMs;
        public int mFieldModifiedLastTimestampMs;
        public int mFillRequestSentTimestampMs;
        public int mFillResponseReceivedTimestampMs;
        public int mFocusedId;
        public int mFocusedVirtualAutofillId;
        public int mInlineSuggestionHostUid;
        public boolean mIsCredentialRequest;
        public boolean mIsDatasetAvailable;
        public boolean mIsRequestTriggered;
        public boolean mNegativeCtaButtonClicked;
        public int mNoPresentationReason;
        public boolean mPositiveCtaButtonClicked;
        public int mRequestId;
        public int mSelectedDatasetId;
        public int mSelectedDatasetPickedReason;
        public int mSelectionTimestamp;
        public int mSuggestionPresentedLastTimestampMs;
        public int mSuggestionPresentedTimestampMs;
        public int mSuggestionSentTimestampMs;
        public int mViewFillFailureCount;
        public int mViewFillSuccessCount;
        public int mViewFillableTotalCount;
        public int mViewFilledButUnexpectedCount;
        public boolean mWebviewRequestedCredential;
        public boolean shouldResetShownCount;
    }

    public PresentationStatsEventLogger(int i, int i2, long j) {
        this.mSessionId = i;
        this.mCallingAppUid = i2;
        this.mSessionStartTimestamp = j;
    }

    public final void logAndEndEvent() {
        if (!this.mEventInternal.isPresent()) {
            Slog.w(
                    "PresentationStatsEventLogger",
                    "Shouldn't be logging AutofillPresentationEventReported again for same event");
            return;
        }
        PresentationStatsEventInternal presentationStatsEventInternal =
                (PresentationStatsEventInternal) this.mEventInternal.get();
        if (Helper.sVerbose) {
            StringBuilder sb =
                    new StringBuilder("Log AutofillPresentationEventReported: requestId=");
            sb.append(presentationStatsEventInternal.mRequestId);
            sb.append(" sessionId=");
            sb.append(this.mSessionId);
            sb.append(" mNoPresentationEventReason=");
            sb.append(presentationStatsEventInternal.mNoPresentationReason);
            sb.append(" mAvailableCount=");
            sb.append(presentationStatsEventInternal.mAvailableCount);
            sb.append(" mCountShown=");
            sb.append(presentationStatsEventInternal.mCountShown);
            sb.append(
                    " mCountFilteredUserTyping=0 mCountNotShownImePresentationNotDrawn=0"
                        + " mCountNotShownImeUserNotSeen=0 mDisplayPresentationType=");
            sb.append(presentationStatsEventInternal.mDisplayPresentationType);
            sb.append(" mAutofillServiceUid=");
            sb.append(presentationStatsEventInternal.mAutofillServiceUid);
            sb.append(" mInlineSuggestionHostUid=");
            sb.append(presentationStatsEventInternal.mInlineSuggestionHostUid);
            sb.append(" mIsRequestTriggered=");
            sb.append(presentationStatsEventInternal.mIsRequestTriggered);
            sb.append(" mFillRequestSentTimestampMs=");
            sb.append(presentationStatsEventInternal.mFillRequestSentTimestampMs);
            sb.append(" mFillResponseReceivedTimestampMs=");
            sb.append(presentationStatsEventInternal.mFillResponseReceivedTimestampMs);
            sb.append(" mSuggestionSentTimestampMs=");
            sb.append(presentationStatsEventInternal.mSuggestionSentTimestampMs);
            sb.append(" mSuggestionPresentedTimestampMs=");
            sb.append(presentationStatsEventInternal.mSuggestionPresentedTimestampMs);
            sb.append(" mSelectedDatasetId=");
            sb.append(presentationStatsEventInternal.mSelectedDatasetId);
            sb.append(" mDialogDismissed=");
            sb.append(presentationStatsEventInternal.mDialogDismissed);
            sb.append(" mNegativeCtaButtonClicked=");
            sb.append(presentationStatsEventInternal.mNegativeCtaButtonClicked);
            sb.append(" mPositiveCtaButtonClicked=");
            sb.append(presentationStatsEventInternal.mPositiveCtaButtonClicked);
            sb.append(" mAuthenticationType=");
            sb.append(presentationStatsEventInternal.mAuthenticationType);
            sb.append(" mAuthenticationResult=");
            sb.append(presentationStatsEventInternal.mAuthenticationResult);
            sb.append(
                    " mLatencyAuthenticationUiDisplayMillis=-1 mLatencyDatasetDisplayMillis=-1"
                        + " mAvailablePccCount=");
            sb.append(presentationStatsEventInternal.mAvailablePccCount);
            sb.append(" mAvailablePccOnlyCount=");
            sb.append(presentationStatsEventInternal.mAvailablePccOnlyCount);
            sb.append(" mSelectedDatasetPickedReason=");
            sb.append(presentationStatsEventInternal.mSelectedDatasetPickedReason);
            sb.append(" mDetectionPreference=");
            sb.append(presentationStatsEventInternal.mDetectionPreference);
            sb.append(" mFieldClassificationRequestId=");
            sb.append(presentationStatsEventInternal.mFieldClassificationRequestId);
            sb.append(" mAppPackageUid=");
            sb.append(this.mCallingAppUid);
            sb.append(" mIsCredentialRequest=");
            sb.append(presentationStatsEventInternal.mIsCredentialRequest);
            sb.append(" mWebviewRequestedCredential=");
            sb.append(presentationStatsEventInternal.mWebviewRequestedCredential);
            sb.append(" mViewFillableTotalCount=");
            sb.append(presentationStatsEventInternal.mViewFillableTotalCount);
            sb.append(" mViewFillFailureCount=");
            sb.append(presentationStatsEventInternal.mViewFillFailureCount);
            sb.append(" mFocusedId=");
            sb.append(presentationStatsEventInternal.mFocusedId);
            sb.append(" mViewFillSuccessCount=");
            sb.append(presentationStatsEventInternal.mViewFillSuccessCount);
            sb.append(" mViewFilledButUnexpectedCount=");
            sb.append(presentationStatsEventInternal.mViewFilledButUnexpectedCount);
            sb.append(" event.mSelectionTimestamp=");
            sb.append(presentationStatsEventInternal.mSelectionTimestamp);
            sb.append(" event.mAutofilledTimestampMs=");
            sb.append(presentationStatsEventInternal.mAutofilledTimestampMs);
            sb.append(" event.mFieldModifiedFirstTimestampMs=");
            sb.append(presentationStatsEventInternal.mFieldModifiedFirstTimestampMs);
            sb.append(" event.mFieldModifiedLastTimestampMs=");
            sb.append(presentationStatsEventInternal.mFieldModifiedLastTimestampMs);
            sb.append(" event.mSuggestionPresentedLastTimestampMs=");
            sb.append(presentationStatsEventInternal.mSuggestionPresentedLastTimestampMs);
            sb.append(" event.mFocusedVirtualAutofillId=");
            sb.append(presentationStatsEventInternal.mFocusedVirtualAutofillId);
            sb.append(" event.mFieldFirstLength=");
            sb.append(presentationStatsEventInternal.mFieldFirstLength);
            sb.append(" event.mFieldLastLength=");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb,
                    presentationStatsEventInternal.mFieldLastLength,
                    "PresentationStatsEventLogger");
        }
        if (!presentationStatsEventInternal.mIsDatasetAvailable) {
            this.mEventInternal = Optional.empty();
            return;
        }
        long j = -1;
        FrameworkStatsLog.write(
                FrameworkStatsLog.AUTOFILL_PRESENTATION_EVENT_REPORTED,
                presentationStatsEventInternal.mRequestId,
                this.mSessionId,
                presentationStatsEventInternal.mNoPresentationReason,
                presentationStatsEventInternal.mAvailableCount,
                presentationStatsEventInternal.mCountShown,
                0,
                0,
                0,
                presentationStatsEventInternal.mDisplayPresentationType,
                presentationStatsEventInternal.mAutofillServiceUid,
                presentationStatsEventInternal.mInlineSuggestionHostUid,
                presentationStatsEventInternal.mIsRequestTriggered,
                presentationStatsEventInternal.mFillRequestSentTimestampMs,
                presentationStatsEventInternal.mFillResponseReceivedTimestampMs,
                presentationStatsEventInternal.mSuggestionSentTimestampMs,
                presentationStatsEventInternal.mSuggestionPresentedTimestampMs,
                presentationStatsEventInternal.mSelectedDatasetId,
                presentationStatsEventInternal.mDialogDismissed,
                presentationStatsEventInternal.mNegativeCtaButtonClicked,
                presentationStatsEventInternal.mPositiveCtaButtonClicked,
                presentationStatsEventInternal.mAuthenticationType,
                presentationStatsEventInternal.mAuthenticationResult,
                j,
                j,
                presentationStatsEventInternal.mAvailablePccCount,
                presentationStatsEventInternal.mAvailablePccOnlyCount,
                presentationStatsEventInternal.mSelectedDatasetPickedReason,
                presentationStatsEventInternal.mDetectionPreference,
                presentationStatsEventInternal.mFieldClassificationRequestId,
                this.mCallingAppUid,
                presentationStatsEventInternal.mIsCredentialRequest,
                presentationStatsEventInternal.mWebviewRequestedCredential,
                presentationStatsEventInternal.mViewFillableTotalCount,
                presentationStatsEventInternal.mViewFillFailureCount,
                presentationStatsEventInternal.mFocusedId,
                presentationStatsEventInternal.mViewFillSuccessCount,
                presentationStatsEventInternal.mViewFilledButUnexpectedCount,
                presentationStatsEventInternal.mSelectionTimestamp,
                presentationStatsEventInternal.mAutofilledTimestampMs,
                presentationStatsEventInternal.mFieldModifiedFirstTimestampMs,
                presentationStatsEventInternal.mFieldModifiedLastTimestampMs,
                presentationStatsEventInternal.mSuggestionPresentedLastTimestampMs,
                presentationStatsEventInternal.mFocusedVirtualAutofillId,
                presentationStatsEventInternal.mFieldFirstLength,
                presentationStatsEventInternal.mFieldLastLength);
        this.mEventInternal = Optional.empty();
    }

    public final void maybeSetAuthenticationResult(int i) {
        this.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(i, 4));
    }

    public final void maybeSetNoPresentationEventReason(int i) {
        this.mEventInternal.ifPresent(
                new PresentationStatsEventLogger$$ExternalSyntheticLambda1(i, 14));
    }

    public final void startNewEvent() {
        if (this.mEventInternal.isPresent()) {
            Slog.e(
                    "PresentationStatsEventLogger",
                    "Failed to start new event because already have active event.");
            return;
        }
        PresentationStatsEventInternal presentationStatsEventInternal =
                new PresentationStatsEventInternal();
        presentationStatsEventInternal.mNoPresentationReason = 0;
        presentationStatsEventInternal.mCountShown = 0;
        presentationStatsEventInternal.mDisplayPresentationType = 0;
        presentationStatsEventInternal.mAutofillServiceUid = -1;
        presentationStatsEventInternal.mInlineSuggestionHostUid = -1;
        presentationStatsEventInternal.mFillRequestSentTimestampMs = -1;
        presentationStatsEventInternal.mFillResponseReceivedTimestampMs = -1;
        presentationStatsEventInternal.mSuggestionSentTimestampMs = -1;
        presentationStatsEventInternal.mSuggestionPresentedTimestampMs = -1;
        presentationStatsEventInternal.mSelectedDatasetId = -1;
        presentationStatsEventInternal.mDialogDismissed = false;
        presentationStatsEventInternal.mNegativeCtaButtonClicked = false;
        presentationStatsEventInternal.mPositiveCtaButtonClicked = false;
        presentationStatsEventInternal.mAuthenticationType = 0;
        presentationStatsEventInternal.mAuthenticationResult = 0;
        presentationStatsEventInternal.mAvailablePccCount = -1;
        presentationStatsEventInternal.mAvailablePccOnlyCount = -1;
        presentationStatsEventInternal.mSelectedDatasetPickedReason = 0;
        presentationStatsEventInternal.mDetectionPreference = 0;
        presentationStatsEventInternal.mFieldClassificationRequestId = -1;
        presentationStatsEventInternal.mIsCredentialRequest = false;
        presentationStatsEventInternal.mWebviewRequestedCredential = false;
        presentationStatsEventInternal.mViewFillableTotalCount = -1;
        presentationStatsEventInternal.mViewFillFailureCount = -1;
        presentationStatsEventInternal.mFocusedId = -1;
        presentationStatsEventInternal.mSelectionTimestamp = -1;
        presentationStatsEventInternal.mAutofilledTimestampMs = -1;
        presentationStatsEventInternal.mFieldModifiedFirstTimestampMs = -1;
        presentationStatsEventInternal.mFieldModifiedLastTimestampMs = -1;
        presentationStatsEventInternal.mSuggestionPresentedLastTimestampMs = -1;
        presentationStatsEventInternal.mFocusedVirtualAutofillId = -1;
        presentationStatsEventInternal.mFieldFirstLength = -1;
        presentationStatsEventInternal.mFieldLastLength = -1;
        presentationStatsEventInternal.mViewFillSuccessCount = 0;
        presentationStatsEventInternal.mViewFilledButUnexpectedCount = 0;
        presentationStatsEventInternal.mAlreadyFilledAutofillIds = new ArraySet();
        presentationStatsEventInternal.shouldResetShownCount = false;
        this.mEventInternal = Optional.of(presentationStatsEventInternal);
    }
}
