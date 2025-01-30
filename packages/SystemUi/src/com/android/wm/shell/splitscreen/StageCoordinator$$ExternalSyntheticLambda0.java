package com.android.wm.shell.splitscreen;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda0 implements SyncTransactionQueue.TransactionRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                StageCoordinator stageCoordinator = (StageCoordinator) obj;
                SurfaceControl surfaceControl = stageCoordinator.mSideStage.mRootLeash;
                Rect rect = stageCoordinator.mTempRect1;
                transaction.setPosition(surfaceControl, rect.left, rect.top);
                break;
            case 1:
                StageCoordinator stageCoordinator2 = (StageCoordinator) obj;
                stageCoordinator2.updateSurfaceBounds(stageCoordinator2.mSplitLayout, transaction, false);
                break;
            case 2:
                StageCoordinator stageCoordinator3 = (StageCoordinator) obj;
                SurfaceControl surfaceControl2 = stageCoordinator3.mSideStage.mRootLeash;
                Rect rect2 = stageCoordinator3.mTempRect1;
                transaction.setPosition(surfaceControl2, rect2.left, rect2.right);
                break;
            case 3:
                StageCoordinator stageCoordinator4 = (StageCoordinator) obj;
                stageCoordinator4.updateSurfaceBounds(stageCoordinator4.mSplitLayout, transaction, false);
                break;
            case 4:
                StageCoordinator stageCoordinator5 = (StageCoordinator) obj;
                stageCoordinator5.updateSurfaceBounds(stageCoordinator5.mSplitLayout, transaction, false);
                break;
            case 5:
                StageCoordinator stageCoordinator6 = (StageCoordinator) obj;
                if (!stageCoordinator6.mIsDropEntering) {
                    stageCoordinator6.mShowDecorImmediately = true;
                    stageCoordinator6.mSplitLayout.flingDividerToCenter();
                    break;
                } else {
                    stageCoordinator6.updateSurfaceBounds(stageCoordinator6.mSplitLayout, transaction, false);
                    stageCoordinator6.mIsDropEntering = false;
                    break;
                }
            case 6:
                StageCoordinator stageCoordinator7 = (StageCoordinator) obj;
                if (!stageCoordinator7.mIsDropEntering) {
                    stageCoordinator7.mShowDecorImmediately = true;
                    stageCoordinator7.mSplitLayout.flingDividerToCenter();
                    break;
                } else {
                    stageCoordinator7.updateSurfaceBounds(stageCoordinator7.mSplitLayout, transaction, false);
                    stageCoordinator7.mIsDropEntering = false;
                    break;
                }
            default:
                StageCoordinator.this.applyDividerVisibility(transaction);
                break;
        }
    }
}
