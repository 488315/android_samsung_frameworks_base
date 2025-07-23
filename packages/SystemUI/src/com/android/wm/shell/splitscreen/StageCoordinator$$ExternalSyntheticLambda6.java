package com.android.wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.wmshell.WMShell;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda6(int i, StageCoordinator stageCoordinator) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setLaunchAdjacentDisabled(false);
                break;
            case 1:
                WMShell wMShell = WMShell.this;
                SysUiState flag = wMShell.mSysUiState.setFlag(4096L, false);
                wMShell.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag).commitUpdate();
                break;
            case 2:
                this.f$0.notifySplitAnimationFinished();
                break;
            case 3:
                StageCoordinator stageCoordinator = this.f$0;
                if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                    stageCoordinator.updateCornerRadiusForStages(null);
                }
                stageCoordinator.handleLayoutSizeChange(stageCoordinator.mSplitLayout, true);
                break;
            case 4:
                StageCoordinator stageCoordinator2 = this.f$0;
                if (stageCoordinator2.mMainStage.mIsActive && stageCoordinator2.mSideStage.getChildCount() == 0) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    stageCoordinator2.prepareExitSplitScreen(0, 0, windowContainerTransaction, true);
                    stageCoordinator2.mSplitTransitions.startDismissTransition(windowContainerTransaction, stageCoordinator2, 0, 2, false);
                    break;
                }
                break;
            default:
                this.f$0.onTransitionAnimationComplete();
                break;
        }
    }
}
