package com.android.wm.shell.pip;

import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda11 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                ((Runnable) obj).run();
                break;
            default:
                int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                transaction.merge((SurfaceControl.Transaction) obj);
                break;
        }
    }
}
