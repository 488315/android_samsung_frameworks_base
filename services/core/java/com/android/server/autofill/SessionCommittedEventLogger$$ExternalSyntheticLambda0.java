package com.android.server.autofill;

import com.android.server.autofill.SessionCommittedEventLogger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SessionCommittedEventLogger$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ SessionCommittedEventLogger$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        SessionCommittedEventLogger.SessionCommittedEventInternal sessionCommittedEventInternal = (SessionCommittedEventLogger.SessionCommittedEventInternal) obj;
        switch (i) {
            case 0:
                sessionCommittedEventInternal.mComponentPackageUid = i2;
                break;
            case 1:
                sessionCommittedEventInternal.mCommitReason = i2;
                break;
            case 2:
                sessionCommittedEventInternal.mRequestCount = i2;
                break;
            case 3:
                sessionCommittedEventInternal.mSaveDataTypeCount = i2;
                break;
            case 4:
                sessionCommittedEventInternal.mSaveInfoCount = i2;
                break;
            default:
                sessionCommittedEventInternal.mServiceUid = i2;
                break;
        }
    }
}
