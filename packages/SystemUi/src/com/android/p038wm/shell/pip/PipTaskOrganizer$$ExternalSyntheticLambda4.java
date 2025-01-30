package com.android.p038wm.shell.pip;

import android.view.SurfaceControl;
import com.android.p038wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda4 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((Runnable) obj).run();
                break;
            default:
                transaction.merge((SurfaceControl.Transaction) obj);
                break;
        }
    }
}
