package com.android.wm.shell.splitscreen;

import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            default:
                this.f$0.mSplitLayout.setDividerInteractive("onSplitResizeConsumed", true, false);
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
            default:
                this.f$0.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", true, false);
                break;
        }
    }
}
