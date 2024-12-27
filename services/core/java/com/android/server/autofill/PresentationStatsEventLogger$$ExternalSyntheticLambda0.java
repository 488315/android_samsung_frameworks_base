package com.android.server.autofill;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PresentationStatsEventLogger$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
        this.f$0 = true;
    }

    public /* synthetic */ PresentationStatsEventLogger$$ExternalSyntheticLambda0(
            int i, boolean z) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        PresentationStatsEventLogger.PresentationStatsEventInternal presentationStatsEventInternal =
                (PresentationStatsEventLogger.PresentationStatsEventInternal) obj;
        switch (i) {
            case 0:
                presentationStatsEventInternal.mWebviewRequestedCredential = z;
                break;
            case 1:
                presentationStatsEventInternal.mIsRequestTriggered = z;
                break;
            case 2:
                presentationStatsEventInternal.mIsCredentialRequest = z;
                break;
            case 3:
                presentationStatsEventInternal.mNegativeCtaButtonClicked = z;
                break;
            case 4:
                presentationStatsEventInternal.mDialogDismissed = z;
                break;
            default:
                presentationStatsEventInternal.mPositiveCtaButtonClicked = z;
                break;
        }
    }
}
