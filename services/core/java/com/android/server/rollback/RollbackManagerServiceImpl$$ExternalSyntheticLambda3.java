package com.android.server.rollback;

import android.content.Context;

import com.android.internal.util.IndentingPrintWriter;

import java.util.ArrayList;
import java.util.Iterator;

public final /* synthetic */ class RollbackManagerServiceImpl$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RollbackManagerServiceImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RollbackManagerServiceImpl$$ExternalSyntheticLambda3(
            RollbackManagerServiceImpl rollbackManagerServiceImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = rollbackManagerServiceImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.expireRollbackForPackageInternal((String) this.f$1, "Expired by API");
                break;
            case 1:
                RollbackManagerServiceImpl rollbackManagerServiceImpl = this.f$0;
                IndentingPrintWriter indentingPrintWriter = (IndentingPrintWriter) this.f$1;
                rollbackManagerServiceImpl.assertInWorkerThread();
                Iterator it = ((ArrayList) rollbackManagerServiceImpl.mRollbacks).iterator();
                while (it.hasNext()) {
                    ((Rollback) it.next()).dump(indentingPrintWriter);
                }
                indentingPrintWriter.println();
                ArrayList arrayList =
                        (ArrayList)
                                RollbackStore.loadRollbacks(
                                        rollbackManagerServiceImpl
                                                .mRollbackStore
                                                .mRollbackHistoryDir);
                if (!arrayList.isEmpty()) {
                    indentingPrintWriter.println("Historical rollbacks:");
                    indentingPrintWriter.increaseIndent();
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        ((Rollback) it2.next()).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    break;
                }
                break;
            default:
                RollbackManagerServiceImpl rollbackManagerServiceImpl2 = this.f$0;
                Context context = (Context) this.f$1;
                ((ArrayList) rollbackManagerServiceImpl2.mRollbacks)
                        .addAll(
                                RollbackStore.loadRollbacks(
                                        rollbackManagerServiceImpl2
                                                .mRollbackStore
                                                .mRollbackDataDir));
                if (!context.getPackageManager().isDeviceUpgrading()) {
                    Iterator it3 = ((ArrayList) rollbackManagerServiceImpl2.mRollbacks).iterator();
                    while (it3.hasNext()) {
                        rollbackManagerServiceImpl2.mAllocatedRollbackIds.put(
                                ((Rollback) it3.next()).info.getRollbackId(), true);
                    }
                    break;
                } else {
                    Iterator it4 = ((ArrayList) rollbackManagerServiceImpl2.mRollbacks).iterator();
                    while (it4.hasNext()) {
                        rollbackManagerServiceImpl2.deleteRollback(
                                (Rollback) it4.next(), "Fingerprint changed");
                    }
                    ((ArrayList) rollbackManagerServiceImpl2.mRollbacks).clear();
                    break;
                }
        }
    }
}
