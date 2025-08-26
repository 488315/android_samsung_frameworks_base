package com.android.wm.shell.splitscreen;

import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;

/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda0 implements SplitScreenTransitions.TransitionConsumedCallback, SplitScreenTransitions.TransitionFinishedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda0(int i, StageCoordinator stageCoordinator) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionConsumedCallback
    public void onConsumed() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", true, false);
                break;
            case 1:
                this.f$0.mSplitLayout.setDividerInteractive("onSplitResizeConsumed", true, false);
                break;
            default:
                this.f$0.mSplitLayout.setDividerInteractive("swapParallelStageTasks", true, false);
                break;
        }
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
    public void onFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        switch (this.$r8$classId) {
            case 2:
                StageCoordinator stageCoordinator = this.f$0;
                stageCoordinator.mSplitLayout.setDividerInteractive("onSplitResizeFinish", true, false);
                stageCoordinator.mSplitLayout.populateTouchZones();
                break;
            case 3:
            default:
                this.f$0.prepareExitSplitScreen(-1, 0, windowContainerTransaction, true);
                break;
            case 4:
                this.f$0.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", true, false);
                break;
            case 5:
                this.f$0.mSplitLayout.setDividerInteractive("swapParallelStageTasks", true, false);
                break;
        }
    }
}
