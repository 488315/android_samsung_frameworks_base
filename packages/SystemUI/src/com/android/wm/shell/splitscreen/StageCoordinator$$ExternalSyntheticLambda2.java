package com.android.wm.shell.splitscreen;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda2 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        switch (this.$r8$classId) {
            case 0:
                StageCoordinator stageCoordinator = (StageCoordinator) this.f$0;
                SurfaceControl surfaceControl = stageCoordinator.mSideStage.mRootLeash;
                Rect rect = stageCoordinator.mTempRect1;
                transaction.setPosition(surfaceControl, rect.left, rect.top);
                break;
            case 1:
                StageCoordinator stageCoordinator2 = (StageCoordinator) this.f$0;
                SurfaceControl surfaceControl2 = stageCoordinator2.mSideStage.mRootLeash;
                Rect rect2 = stageCoordinator2.mTempRect1;
                transaction.setPosition(surfaceControl2, rect2.left, rect2.right);
                break;
            case 2:
                StageCoordinator stageCoordinator3 = (StageCoordinator) this.f$0;
                stageCoordinator3.updateSurfaceBounds(stageCoordinator3.mSplitLayout, transaction, false);
                break;
            default:
                StageCoordinator.this.applyDividerVisibility(transaction);
                break;
        }
    }
}
