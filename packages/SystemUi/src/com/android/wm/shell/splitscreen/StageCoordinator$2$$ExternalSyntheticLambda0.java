package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.StageCoordinator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator.C41162 f$0;

    public /* synthetic */ StageCoordinator$2$$ExternalSyntheticLambda0(StageCoordinator.C41162 c41162, int i) {
        this.$r8$classId = i;
        this.f$0 = c41162;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StageCoordinator.this.exitSplitScreen(null, 0);
                break;
            default:
                StageCoordinator.C41162 c41162 = this.f$0;
                StageCoordinator stageCoordinator = StageCoordinator.this;
                int childCount = stageCoordinator.mSideStage.getChildCount();
                StageCoordinator stageCoordinator2 = StageCoordinator.this;
                stageCoordinator.exitSplitScreen(childCount == 0 ? stageCoordinator2.mMainStage : stageCoordinator2.mSideStage, 0);
                break;
        }
    }
}
