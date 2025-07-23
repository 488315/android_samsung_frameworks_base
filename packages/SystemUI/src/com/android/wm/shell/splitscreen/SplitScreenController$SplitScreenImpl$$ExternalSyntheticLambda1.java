package com.android.wm.shell.splitscreen;

import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.unfold.animation.SplitTaskUnfoldAnimator;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1(SplitScreenController.SplitScreenImpl splitScreenImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenImpl;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
                splitScreenImpl.mExecutors.remove((SplitTaskUnfoldAnimator) this.f$1);
                if (splitScreenImpl.mExecutors.size() == 0) {
                    ((ArrayList) SplitScreenController.this.mStageCoordinator.mListeners).remove(splitScreenImpl.mListener);
                    break;
                }
                break;
            case 1:
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = this.f$0;
                SplitScreenController.this.mStageCoordinator.sendStatusToListener((SplitTaskUnfoldAnimator) this.f$1);
                break;
            default:
                SplitScreenController.this.mIsKeyguardOccludedAndShowingSupplier = (WMShell$$ExternalSyntheticLambda4) this.f$1;
                break;
        }
    }
}
