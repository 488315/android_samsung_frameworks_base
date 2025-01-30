package com.android.wm.shell.splitscreen;

import android.window.WindowContainerTransaction;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda3(int i, SplitScreenController splitScreenController) {
        this.$r8$classId = i;
        this.f$0 = splitScreenController;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.wm.shell.splitscreen.SplitScreenTransitions] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.wm.shell.splitscreen.SideStage, com.android.wm.shell.splitscreen.StageTaskListener] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.wm.shell.splitscreen.StageCoordinator, com.android.wm.shell.transition.Transitions$TransitionHandler] */
    @Override // java.lang.Runnable
    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onInit();
                break;
            case 1:
                this.f$0.onInit();
                break;
            case 2:
                ?? r8 = this.f$0.mStageCoordinator;
                MainStage mainStage = r8.mMainStage;
                if (!mainStage.mIsActive) {
                    if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                        r8.updateSplitDivisionIfNeeded();
                        break;
                    }
                } else {
                    boolean z2 = mainStage.mRootTaskInfo.isVisible;
                    ?? r4 = r8.mSideStage;
                    if ((z2 != r4.mRootTaskInfo.isVisible) != false) {
                        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                            int i = !z2 ? 1 : 0;
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            r8.prepareExitSplitScreen(i, windowContainerTransaction, true);
                            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                                r8.prepareSplitDismissChangeTransition(windowContainerTransaction, i ^ 1, null, false);
                            }
                            r8.mSplitTransitions.startDismissTransition(windowContainerTransaction, r8, i, 8);
                        } else {
                            if (!z2) {
                                mainStage = r4;
                            }
                            r8.exitSplitScreen(mainStage, 8);
                        }
                    }
                    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && !r8.mKeyguardShowing && r8.mTopStageAfterFoldDismiss != -1) {
                        r8.mTopStageAfterFoldDismiss = -1;
                        if (r8.updateCoverDisplaySplitLayoutIfNeeded()) {
                            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                                r8.updateSplitDivisionIfNeeded();
                            }
                            r8.mSplitLayout.update(null);
                            r8.onLayoutSizeChanged(r8.mSplitLayout, null);
                            break;
                        }
                    }
                }
                break;
            default:
                StageCoordinator stageCoordinator = this.f$0.mStageCoordinator;
                if (!stageCoordinator.mSideStage.isFocused() ? stageCoordinator.mSideStagePosition == 1 : stageCoordinator.mSideStagePosition == 0) {
                    z = true;
                }
                stageCoordinator.mSplitLayout.flingDividerToDismiss(11, !z);
                break;
        }
    }
}
