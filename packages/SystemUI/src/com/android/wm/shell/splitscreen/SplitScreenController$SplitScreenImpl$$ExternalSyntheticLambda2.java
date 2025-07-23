package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda2(SplitScreenController.SplitScreenImpl splitScreenImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
        switch (i) {
            case 0:
                SplitScreenController splitScreenController = SplitScreenController.this;
                if (splitScreenController.mStageCoordinator.isSplitScreenVisible()) {
                    splitScreenController.exitSplitScreen(splitScreenController.getFocusedStageTaskIdWithoutAppsEdgeActivity(), 14);
                    break;
                }
                break;
            case 1:
                SplitScreenController.this.enterSplitScreen$1();
                break;
            default:
                SplitScreenController.this.toggleSplitScreen(1);
                break;
        }
    }
}
