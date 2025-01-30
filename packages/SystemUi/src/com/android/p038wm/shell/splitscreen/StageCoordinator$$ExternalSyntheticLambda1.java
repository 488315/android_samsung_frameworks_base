package com.android.p038wm.shell.splitscreen;

import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.splitscreen.SplitScreenTransitions;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda1 implements SplitScreenTransitions.TransitionFinishedCallback, SplitScreenTransitions.TransitionConsumedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda1(StageCoordinator stageCoordinator, int i) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
    public final void onFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        int i = this.$r8$classId;
        StageCoordinator stageCoordinator = this.f$0;
        switch (i) {
            case 0:
                stageCoordinator.mSplitLayout.setDividerInteractive("onSplitResizeFinish", true, false);
                break;
            default:
                stageCoordinator.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", true, false);
                break;
        }
    }
}
