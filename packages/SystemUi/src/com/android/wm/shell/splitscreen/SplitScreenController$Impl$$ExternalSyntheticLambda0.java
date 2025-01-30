package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                SplitScreenController.SplitScreenImpl.C41111 c41111 = (SplitScreenController.SplitScreenImpl.C41111) this.f$0;
                ((SplitScreen.SplitScreenListener) SplitScreenController.SplitScreenImpl.this.mExecutors.keyAt(this.f$2)).onSplitVisibilityChanged(this.f$1);
                break;
        }
    }

    public /* synthetic */ SplitScreenController$Impl$$ExternalSyntheticLambda0(SplitScreenController.SplitScreenImpl.C41111 c41111, int i, boolean z) {
        this.f$0 = c41111;
        this.f$2 = i;
        this.f$1 = z;
    }
}
