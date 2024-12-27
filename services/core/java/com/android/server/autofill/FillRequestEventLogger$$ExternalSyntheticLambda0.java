package com.android.server.autofill;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FillRequestEventLogger$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ FillRequestEventLogger$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        FillRequestEventLogger.FillRequestEventInternal fillRequestEventInternal =
                (FillRequestEventLogger.FillRequestEventInternal) obj;
        switch (i) {
            case 0:
                fillRequestEventInternal.mAutofillServiceUid = i2;
                break;
            case 1:
                fillRequestEventInternal.mAppPackageUid = i2;
                break;
            case 2:
                fillRequestEventInternal.mRequestTriggerReason = i2;
                break;
            case 3:
                fillRequestEventInternal.mRequestId = i2;
                break;
            case 4:
                fillRequestEventInternal.mFlags = i2;
                break;
            default:
                fillRequestEventInternal.mLatencyFillRequestSentMillis = i2;
                break;
        }
    }
}
