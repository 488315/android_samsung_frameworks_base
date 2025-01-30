package com.android.p038wm.shell.windowdecor;

import android.graphics.Rect;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.windowdecor.TaskMotionAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskMotionController$$ExternalSyntheticLambda2 implements TaskMotionAnimator.OnAnimationFinishedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TaskMotionController f$0;

    public /* synthetic */ TaskMotionController$$ExternalSyntheticLambda2(TaskMotionController taskMotionController, int i) {
        this.$r8$classId = i;
        this.f$0 = taskMotionController;
    }

    @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.OnAnimationFinishedCallback
    public final void onAnimationFinished(Rect rect) {
        int i = this.$r8$classId;
        TaskMotionController taskMotionController = this.f$0;
        switch (i) {
            case 0:
                taskMotionController.removeMotionAnimator(0);
                taskMotionController.mFreeformStashState.mAnimating = false;
                break;
            default:
                FreeformStashState freeformStashState = taskMotionController.mFreeformStashState;
                freeformStashState.mAnimating = false;
                if (!freeformStashState.isStashed()) {
                    taskMotionController.postAnimationFinished(1, taskMotionController.mWindowDecoration.mTaskInfo, rect, new WindowContainerTransaction());
                    break;
                } else {
                    taskMotionController.removeMotionAnimator(1);
                    break;
                }
        }
    }
}
