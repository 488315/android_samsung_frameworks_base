package com.android.server.autofill;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SaveEventLogger$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ SaveEventLogger$$ExternalSyntheticLambda1() {
        this.$r8$classId = 5;
        this.f$0 = 1;
    }

    public /* synthetic */ SaveEventLogger$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        SaveEventLogger.SaveEventInternal saveEventInternal =
                (SaveEventLogger.SaveEventInternal) obj;
        switch (i) {
            case 0:
                saveEventInternal.mAppPackageUid = i2;
                break;
            case 1:
                saveEventInternal.mFlag = i2;
                break;
            case 2:
                saveEventInternal.mRequestId = i2;
                break;
            case 3:
                saveEventInternal.mSaveUiShownReason = i2;
                break;
            case 4:
                saveEventInternal.mSaveUiNotShownReason = i2;
                break;
            case 5:
                saveEventInternal.mSaveUiTriggerIds = i2;
                break;
            default:
                saveEventInternal.mServiceUid = i2;
                break;
        }
    }
}
