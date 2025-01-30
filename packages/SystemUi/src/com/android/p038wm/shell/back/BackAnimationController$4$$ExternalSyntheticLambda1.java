package com.android.p038wm.shell.back;

import com.android.p038wm.shell.back.BackAnimationController;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BackAnimationController$4$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BackAnimationController$4$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BackAnimationController.C37804 c37804 = (BackAnimationController.C37804) this.f$0;
                BackAnimationController backAnimationController = BackAnimationController.this;
                BackAnimationRunner backAnimationRunner = (BackAnimationRunner) backAnimationController.mAnimationDefinition.get(backAnimationController.mBackNavigationInfo.getType());
                if (backAnimationRunner != null) {
                    backAnimationRunner.mWaitingAnimation = false;
                    backAnimationRunner.mAnimationCancelled = true;
                    BackAnimationController backAnimationController2 = BackAnimationController.this;
                    if (!backAnimationController2.mBackGestureStarted) {
                        backAnimationController2.invokeOrCancelBack();
                        break;
                    }
                }
                break;
            case 1:
                BackAnimationController backAnimationController3 = BackAnimationController.this;
                ((HandlerExecutor) backAnimationController3.mShellExecutor).execute(new BackAnimationController$$ExternalSyntheticLambda1(backAnimationController3, 2));
                break;
            default:
                BackAnimationController.C37771 c37771 = (BackAnimationController.C37771) this.f$0;
                BackAnimationController backAnimationController4 = BackAnimationController.this;
                if (backAnimationController4.mBackGestureStarted && !backAnimationController4.mPostCommitAnimationInProgress) {
                    if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                        ShellProtoLogImpl.m231i(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1757272348, "Navigation window gone.", null);
                    }
                    BackAnimationController backAnimationController5 = BackAnimationController.this;
                    if (!backAnimationController5.mPostCommitAnimationInProgress) {
                        backAnimationController5.mTriggerBack = false;
                        TouchTracker touchTracker = backAnimationController5.mTouchTracker;
                        if (touchTracker.mTriggerBack) {
                            touchTracker.mCancelled = true;
                        }
                        touchTracker.mTriggerBack = false;
                    }
                    backAnimationController5.onGestureFinished(false);
                    break;
                }
                break;
        }
    }
}
