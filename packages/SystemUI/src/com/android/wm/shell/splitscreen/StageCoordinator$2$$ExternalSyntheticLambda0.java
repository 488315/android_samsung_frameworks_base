package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.StageCoordinator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator.AnonymousClass2 f$0;

    public /* synthetic */ StageCoordinator$2$$ExternalSyntheticLambda0(StageCoordinator.AnonymousClass2 anonymousClass2, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StageCoordinator.AnonymousClass2 anonymousClass2 = this.f$0;
        switch (i) {
            case 0:
                StageCoordinator stageCoordinator = StageCoordinator.this;
                int childCount = stageCoordinator.mSideStage.getChildCount();
                StageCoordinator stageCoordinator2 = StageCoordinator.this;
                stageCoordinator.exitSplitScreen(childCount == 0 ? stageCoordinator2.mMainStage : stageCoordinator2.mSideStage, 0);
                break;
            default:
                StageCoordinator.this.exitSplitScreen(null, 0);
                break;
        }
    }
}
