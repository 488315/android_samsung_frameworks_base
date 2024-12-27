package com.android.server.autofill;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FillRequestEventLogger$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ FillRequestEventLogger$$ExternalSyntheticLambda2() {
        this.$r8$classId = 1;
        this.f$0 = true;
    }

    public /* synthetic */ FillRequestEventLogger$$ExternalSyntheticLambda2(boolean z) {
        this.$r8$classId = 0;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        FillRequestEventLogger.FillRequestEventInternal fillRequestEventInternal =
                (FillRequestEventLogger.FillRequestEventInternal) obj;
        switch (i) {
            case 0:
                fillRequestEventInternal.mIsFillDialogEligible = z;
                break;
            default:
                fillRequestEventInternal.mIsAugmented = z;
                break;
        }
    }
}
