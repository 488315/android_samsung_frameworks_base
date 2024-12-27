package com.android.server.autofill;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda1() {
        this.$r8$classId = 3;
        this.f$0 = 3;
    }

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal =
                (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
        switch (i) {
            case 0:
                presentationStatsEventInternal.mFieldClassificationRequestId = i2;
                break;
            case 1:
                presentationStatsEventInternal.mAuthenticationType = i2;
                break;
            case 2:
                presentationStatsEventInternal.mAutofillServiceUid = i2;
                break;
            case 3:
                if (presentationStatsEventInternal.mCountShown == 0
                        && presentationStatsEventInternal.mNoPresentationReason == 0) {
                    presentationStatsEventInternal.mNoPresentationReason = i2;
                    break;
                }
                break;
            case 4:
                presentationStatsEventInternal.mAuthenticationResult = i2;
                break;
            case 5:
                presentationStatsEventInternal.mSelectedDatasetId = i2;
                break;
            case 6:
                presentationStatsEventInternal.mRequestId = i2;
                break;
            case 7:
                presentationStatsEventInternal.mCountShown = i2;
                break;
            case 8:
                presentationStatsEventInternal.mFillRequestSentTimestampMs = i2;
                break;
            case 9:
                if (presentationStatsEventInternal.mSuggestionPresentedTimestampMs == -1) {
                    presentationStatsEventInternal.mSuggestionPresentedTimestampMs = i2;
                }
                presentationStatsEventInternal.mSuggestionPresentedLastTimestampMs = i2;
                break;
            case 10:
                presentationStatsEventInternal.mFillResponseReceivedTimestampMs = i2;
                break;
            case 11:
                presentationStatsEventInternal.mViewFillFailureCount = i2;
                break;
            case 12:
                presentationStatsEventInternal.mDetectionPreference = i2;
                break;
            case 13:
                presentationStatsEventInternal.mSuggestionSentTimestampMs = i2;
                break;
            case 14:
                if (presentationStatsEventInternal.mCountShown == 0) {
                    presentationStatsEventInternal.mNoPresentationReason = i2;
                    break;
                }
                break;
            default:
                int i3 = 1;
                if (i2 != 1) {
                    i3 = 2;
                    if (i2 != 2) {
                        i3 = 3;
                        if (i2 != 3) {
                            i3 = 0;
                        }
                    }
                }
                presentationStatsEventInternal.mDisplayPresentationType = i3;
                break;
        }
    }
}
