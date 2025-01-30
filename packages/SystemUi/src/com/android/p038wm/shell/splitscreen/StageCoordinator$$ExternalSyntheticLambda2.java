package com.android.p038wm.shell.splitscreen;

import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.common.split.SplitLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda2 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda2(StageCoordinator stageCoordinator, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
        this.f$1 = obj;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        int i = this.$r8$classId;
        final StageCoordinator stageCoordinator = this.f$0;
        Object obj = this.f$1;
        switch (i) {
            case 0:
                stageCoordinator.updateSurfaceBounds((SplitLayout) obj, transaction, false);
                stageCoordinator.mMainStage.onResized(transaction);
                stageCoordinator.mSideStage.onResized(transaction);
                break;
            default:
                final StageTaskListener stageTaskListener = (StageTaskListener) obj;
                MainStage mainStage = stageCoordinator.mMainStage;
                SurfaceControl.Transaction windowCrop = transaction.setWindowCrop(mainStage.mRootLeash, null);
                SideStage sideStage = stageCoordinator.mSideStage;
                windowCrop.setWindowCrop(sideStage.mRootLeash, null);
                transaction.hide(mainStage.mDimLayer).hide(sideStage.mDimLayer);
                stageCoordinator.setDividerVisibility(false, transaction);
                if (stageTaskListener != null) {
                    Runnable runnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            StageCoordinator stageCoordinator2 = StageCoordinator.this;
                            StageTaskListener stageTaskListener2 = stageTaskListener;
                            stageCoordinator2.getClass();
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            stageCoordinator2.mIsExiting = false;
                            MainStage mainStage2 = stageCoordinator2.mMainStage;
                            mainStage2.deactivate(windowContainerTransaction, stageTaskListener2 == mainStage2);
                            SideStage sideStage2 = stageCoordinator2.mSideStage;
                            sideStage2.removeAllTasks(windowContainerTransaction, stageTaskListener2 == sideStage2, true);
                            windowContainerTransaction.reorder(stageCoordinator2.mRootTaskInfo.token, false);
                            stageCoordinator2.setRootForceTranslucent(windowContainerTransaction, true);
                            windowContainerTransaction.setBounds(sideStage2.mRootTaskInfo.token, stageCoordinator2.mTempRect1);
                            SyncTransactionQueue syncTransactionQueue = stageCoordinator2.mSyncQueue;
                            syncTransactionQueue.queue(windowContainerTransaction);
                            syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(stageCoordinator2, 2));
                            stageCoordinator2.onTransitionAnimationComplete();
                        }
                    };
                    if (stageTaskListener.mSplitDecorManager == null) {
                        runnable.run();
                        break;
                    } else {
                        runnable.run();
                        break;
                    }
                } else {
                    SurfaceControl surfaceControl = sideStage.mRootLeash;
                    Rect rect = stageCoordinator.mTempRect1;
                    transaction.setPosition(surfaceControl, rect.left, rect.right);
                    break;
                }
        }
    }
}
