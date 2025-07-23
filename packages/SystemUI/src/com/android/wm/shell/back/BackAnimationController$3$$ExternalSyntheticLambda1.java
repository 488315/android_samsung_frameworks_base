package com.android.wm.shell.back;

import android.window.BackNavigationInfo;
import com.android.wm.shell.back.BackAnimationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BackAnimationController$3$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BackAnimationController$3$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BackAnimationController.AnonymousClass3 anonymousClass3 = (BackAnimationController.AnonymousClass3) obj;
                BackAnimationController backAnimationController = anonymousClass3.this$0;
                ShellBackAnimationRegistry shellBackAnimationRegistry = backAnimationController.mShellBackAnimationRegistry;
                BackNavigationInfo backNavigationInfo = backAnimationController.mBackNavigationInfo;
                BackAnimationRunner backAnimationRunner = (BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(backNavigationInfo != null ? backNavigationInfo.getType() : backAnimationController.mPreviousNavigationType);
                if (backAnimationRunner != null) {
                    backAnimationRunner.mWaitingAnimation = false;
                    backAnimationRunner.mAnimationCancelled = true;
                    BackAnimationController backAnimationController2 = anonymousClass3.this$0;
                    if (!backAnimationController2.mBackGestureStarted) {
                        backAnimationController2.invokeOrCancelBack(backAnimationController2.mCurrentTracker);
                        break;
                    }
                }
                break;
            default:
                BackAnimationController.BackTransitionHandler backTransitionHandler = (BackAnimationController.BackTransitionHandler) obj;
                backTransitionHandler.applyFinishOpenTransition();
                backTransitionHandler.mCloseTransitionRequested = false;
                break;
        }
    }
}
