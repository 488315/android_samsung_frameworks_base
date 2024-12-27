package com.android.server.autofill;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FillResponseEventLogger$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ FillResponseEventLogger$$ExternalSyntheticLambda0() {
        this.$r8$classId = 0;
        this.f$0 = 1;
    }

    public /* synthetic */ FillResponseEventLogger$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        FillResponseEventLogger.FillResponseEventInternal fillResponseEventInternal =
                (FillResponseEventLogger.FillResponseEventInternal) obj;
        switch (i) {
            case 0:
                fillResponseEventInternal.mSaveUiTriggerIds = i2;
                break;
            case 1:
                fillResponseEventInternal.mAvailableCount = i2;
                break;
            case 2:
                fillResponseEventInternal.mAppPackageUid = i2;
                break;
            case 3:
                fillResponseEventInternal.mResponseStatus = i2;
                break;
            case 4:
                fillResponseEventInternal.mDetectionPref = i2;
                break;
            case 5:
                fillResponseEventInternal.mRequestId = i2;
                break;
            case 6:
                if (fillResponseEventInternal.mTotalDatasetsProvided == -1) {
                    fillResponseEventInternal.mTotalDatasetsProvided = i2;
                    break;
                }
                break;
            default:
                fillResponseEventInternal.mLatencyFillResponseReceivedMillis = i2;
                break;
        }
    }
}
