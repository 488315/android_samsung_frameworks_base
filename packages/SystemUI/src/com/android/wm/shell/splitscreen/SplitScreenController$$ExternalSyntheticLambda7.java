package com.android.wm.shell.splitscreen;

import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda7(SplitScreenController splitScreenController, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SplitScreenController splitScreenController = this.f$0;
        switch (i) {
            case 0:
                splitScreenController.onInit();
                break;
            case 1:
                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                stageCoordinator.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7075086966267693690L, 0, null);
                }
                if (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                    if (stageCoordinator.mBreakOnNextWake) {
                        stageCoordinator.dismissSplitKeepingLastActiveStage(3);
                        break;
                    }
                } else if (!stageCoordinator.mMainStage.mIsActive) {
                    if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                        stageCoordinator.updateSplitDivisionIfNeeded();
                        break;
                    }
                } else if (!stageCoordinator.mKeyguardActive && stageCoordinator.isApplyFoldingPolicy(false)) {
                    stageCoordinator.mTopStageAfterFold = -1;
                    if (stageCoordinator.updateCoverDisplaySplitLayoutIfNeeded()) {
                        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                            stageCoordinator.updateSplitDivisionIfNeeded();
                        }
                        stageCoordinator.mSplitLayout.update(null, true);
                        stageCoordinator.onLayoutSizeChanged(stageCoordinator.mSplitLayout, null);
                        break;
                    }
                }
                break;
            case 2:
                splitScreenController.mStageCoordinator.recordLastActiveStage(false);
                break;
            default:
                StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                if (stageCoordinator2.mMainStage.mIsActive) {
                    boolean z = false;
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2439376142264352980L, 0, null);
                    }
                    if (!stageCoordinator2.mMainStage.isFocused() ? stageCoordinator2.mSideStagePosition == 0 : stageCoordinator2.mSideStagePosition == 1) {
                        z = true;
                    }
                    stageCoordinator2.mSplitLayout.flingDividerToDismiss(11, z);
                    break;
                }
                break;
        }
    }
}
