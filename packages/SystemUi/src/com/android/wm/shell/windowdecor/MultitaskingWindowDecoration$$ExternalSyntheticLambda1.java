package com.android.wm.shell.windowdecor;

import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultitaskingWindowDecoration$$ExternalSyntheticLambda1 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SurfaceControl.Transaction f$0;

    public /* synthetic */ MultitaskingWindowDecoration$$ExternalSyntheticLambda1(int i, SurfaceControl.Transaction transaction) {
        this.$r8$classId = i;
        this.f$0 = transaction;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        int i = this.$r8$classId;
        SurfaceControl.Transaction transaction2 = this.f$0;
        switch (i) {
            case 0:
                transaction.merge(transaction2);
                transaction2.close();
                break;
            case 1:
                transaction.merge(transaction2);
                transaction2.close();
                break;
            case 2:
                transaction.merge(transaction2);
                transaction2.close();
                break;
            case 3:
                transaction.merge(transaction2);
                transaction2.close();
                break;
            default:
                transaction.merge(transaction2);
                transaction2.close();
                break;
        }
    }
}
