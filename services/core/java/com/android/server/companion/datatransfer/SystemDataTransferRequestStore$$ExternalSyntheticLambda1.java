package com.android.server.companion.datatransfer;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemDataTransferRequestStore$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SystemDataTransferRequestStore f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ArrayList f$2;

    public /* synthetic */ SystemDataTransferRequestStore$$ExternalSyntheticLambda1(
            SystemDataTransferRequestStore systemDataTransferRequestStore,
            int i,
            ArrayList arrayList,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = systemDataTransferRequestStore;
        this.f$1 = i;
        this.f$2 = arrayList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.writeRequestsToStore(this.f$1, this.f$2);
                break;
            default:
                this.f$0.writeRequestsToStore(this.f$1, this.f$2);
                break;
        }
    }
}
