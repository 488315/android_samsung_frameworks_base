package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$Impl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SplitScreenController$Impl$$ExternalSyntheticLambda0(SplitScreenController.Impl impl, boolean z, int i) {
        this.f$0 = impl;
        this.f$1 = z;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.Impl impl = (SplitScreenController.Impl) this.f$0;
                boolean z = this.f$1;
                int i = this.f$2;
                if (!z) {
                    int i2 = SplitScreenController.Impl.$r8$clinit;
                    impl.getClass();
                    break;
                } else {
                    SplitScreenController splitScreenController = SplitScreenController.this;
                    ActivityManager.RunningTaskInfo runningTaskInfo = splitScreenController.mTaskOrganizer.getRunningTaskInfo(i);
                    int stagePosition = runningTaskInfo != null ? runningTaskInfo.configuration.windowConfiguration.getStagePosition() : 0;
                    if (splitScreenController.mFocusedTaskPosition != stagePosition) {
                        splitScreenController.mFocusedTaskPosition = stagePosition;
                        break;
                    }
                }
                break;
            default:
                SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1 = (SplitScreenController.SplitScreenImpl.AnonymousClass1) this.f$0;
                ((SplitScreen.SplitScreenListener) SplitScreenController.SplitScreenImpl.this.mExecutors.keyAt(this.f$2)).onSplitVisibilityChanged(this.f$1);
                break;
        }
    }

    public /* synthetic */ SplitScreenController$Impl$$ExternalSyntheticLambda0(SplitScreenController.SplitScreenImpl.AnonymousClass1 anonymousClass1, int i, boolean z) {
        this.f$0 = anonymousClass1;
        this.f$2 = i;
        this.f$1 = z;
    }
}
