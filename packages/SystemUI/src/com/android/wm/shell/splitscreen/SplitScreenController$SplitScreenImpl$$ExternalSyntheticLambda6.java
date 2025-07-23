package com.android.wm.shell.splitscreen;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda6(SplitScreenController.SplitScreenImpl splitScreenImpl, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
                boolean z = this.f$1;
                SplitScreenController.this.mDividerResizeController.mUseGuideViewByMultiStar = z;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("setDividerResizeMode: useGuideView=", "DividerResizeController", z);
                break;
            default:
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = this.f$0;
                boolean z2 = this.f$1;
                StageCoordinator stageCoordinator = SplitScreenController.this.mStageCoordinator;
                if (stageCoordinator.mMainStage.mIsActive) {
                    stageCoordinator.grantFocusToPosition(z2);
                    break;
                }
                break;
        }
    }
}
