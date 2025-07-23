package com.android.wm.shell.back;

import android.os.Bundle;
import android.window.BackNavigationInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda10;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BackAnimationController$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BackAnimationController$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BackAnimationController.AnonymousClass1 anonymousClass1 = (BackAnimationController.AnonymousClass1) this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                if (!anonymousClass1.this$0.mBackGestureStarted || bundle == null || !bundle.getBoolean("TouchGestureTransferred")) {
                    BackAnimationController backAnimationController = anonymousClass1.this$0;
                    if (backAnimationController.mBackGestureStarted && !backAnimationController.mPostCommitAnimationInProgress) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[2]) {
                            ProtoLogImpl_1771455215.i(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -4676570246121374196L, 0, null);
                        }
                        anonymousClass1.this$0.setTriggerBack(false);
                        BackAnimationController.BackTransitionHandler backTransitionHandler = anonymousClass1.this$0.mBackTransitionHandler;
                        if (!backTransitionHandler.mCloseTransitionRequested && backTransitionHandler.mPrepareOpenTransition != null) {
                            backTransitionHandler.createClosePrepareTransition();
                        }
                        Runnable runnable = backTransitionHandler.mOnAnimationFinishCallback;
                        if (runnable != null) {
                            runnable.run();
                            backTransitionHandler.mOnAnimationFinishCallback = null;
                        }
                        anonymousClass1.this$0.resetTouchTracker();
                        BackAnimationController backAnimationController2 = anonymousClass1.this$0;
                        ((HandlerExecutor) backAnimationController2.mShellExecutor).removeCallbacks(backAnimationController2.mAnimationTimeoutRunnable);
                        break;
                    }
                } else {
                    BackNavigationInfo backNavigationInfo = anonymousClass1.this$0.mBackNavigationInfo;
                    if (backNavigationInfo != null) {
                        backNavigationInfo.disableAppProgressGenerationAllowed();
                    }
                    anonymousClass1.this$0.tryPilferPointers();
                    break;
                }
                break;
            case 1:
                ((BackAnimationController.BackAnimationImpl) this.f$0).this$0.mPilferPointerCallback = (Runnable) this.f$1;
                break;
            default:
                ((BackAnimationController.BackAnimationImpl) this.f$0).this$0.mRequestTopUiCallback = (EdgeBackGestureHandler$$ExternalSyntheticLambda10) this.f$1;
                break;
        }
    }
}
