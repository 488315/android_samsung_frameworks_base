package com.android.wm.shell.splitscreen;

import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageTaskListener$$ExternalSyntheticLambda3 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageTaskListener f$0;

    public /* synthetic */ StageTaskListener$$ExternalSyntheticLambda3(StageTaskListener stageTaskListener, int i) {
        this.$r8$classId = i;
        this.f$0 = stageTaskListener;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        StageTaskListener stageTaskListener = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                SurfaceControl build = new SurfaceControl.Builder().setParent(stageTaskListener.mRootLeash).setColorLayer().setName("Dim layer").setCallsite("SurfaceUtils.makeColorLayer").build();
                transaction.setLayer(build, Integer.MAX_VALUE).setColor(build, new float[]{0.0f, 0.0f, 0.0f});
                stageTaskListener.mDimLayer = build;
                break;
            default:
                transaction.remove(stageTaskListener.mDimLayer);
                stageTaskListener.mSplitDecorManager.release(transaction);
                break;
        }
    }
}
