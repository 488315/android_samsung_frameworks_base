package com.android.server.companion.datatransfer;

import java.util.ArrayList;

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
