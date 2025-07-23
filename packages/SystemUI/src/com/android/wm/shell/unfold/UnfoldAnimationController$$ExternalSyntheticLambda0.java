package com.android.wm.shell.unfold;

import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.unfold.animation.UnfoldTaskAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UnfoldAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UnfoldAnimationController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                UnfoldAnimationController unfoldAnimationController = (UnfoldAnimationController) obj;
                ShellUnfoldProgressProvider shellUnfoldProgressProvider = unfoldAnimationController.mUnfoldProgressProvider;
                ShellExecutor shellExecutor = unfoldAnimationController.mExecutor;
                shellUnfoldProgressProvider.addListener(shellExecutor, unfoldAnimationController);
                for (int i2 = 0; i2 < unfoldAnimationController.mAnimators.size(); i2++) {
                    UnfoldTaskAnimator unfoldTaskAnimator = (UnfoldTaskAnimator) unfoldAnimationController.mAnimators.get(i2);
                    unfoldTaskAnimator.init();
                    ((HandlerExecutor) shellExecutor).executeDelayed(new UnfoldAnimationController$$ExternalSyntheticLambda0(unfoldTaskAnimator, 1), 0L);
                }
                break;
            default:
                ((UnfoldTaskAnimator) obj).start();
                break;
        }
    }
}
