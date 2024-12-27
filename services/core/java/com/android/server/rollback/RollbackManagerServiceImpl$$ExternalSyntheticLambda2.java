package com.android.server.rollback;

import android.content.pm.ParceledListSlice;

import java.util.ArrayList;
import java.util.function.Supplier;

public final /* synthetic */ class RollbackManagerServiceImpl$$ExternalSyntheticLambda2
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RollbackManagerServiceImpl f$0;

    public /* synthetic */ RollbackManagerServiceImpl$$ExternalSyntheticLambda2(
            RollbackManagerServiceImpl rollbackManagerServiceImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = rollbackManagerServiceImpl;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        RollbackManagerServiceImpl rollbackManagerServiceImpl = this.f$0;
        switch (i) {
            case 0:
                rollbackManagerServiceImpl.assertInWorkerThread();
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0;
                        i2 < ((ArrayList) rollbackManagerServiceImpl.mRollbacks).size();
                        i2++) {
                    Rollback rollback =
                            (Rollback) ((ArrayList) rollbackManagerServiceImpl.mRollbacks).get(i2);
                    if (rollback.isCommitted()) {
                        arrayList.add(rollback.info);
                    }
                }
                return new ParceledListSlice(arrayList);
            default:
                rollbackManagerServiceImpl.assertInWorkerThread();
                ArrayList arrayList2 = new ArrayList();
                for (int i3 = 0;
                        i3 < ((ArrayList) rollbackManagerServiceImpl.mRollbacks).size();
                        i3++) {
                    Rollback rollback2 =
                            (Rollback) ((ArrayList) rollbackManagerServiceImpl.mRollbacks).get(i3);
                    if (rollback2.isAvailable()) {
                        arrayList2.add(rollback2.info);
                    }
                }
                return new ParceledListSlice(arrayList2);
        }
    }
}
