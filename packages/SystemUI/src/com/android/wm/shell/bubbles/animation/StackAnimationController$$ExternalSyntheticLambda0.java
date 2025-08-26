package com.android.wm.shell.bubbles.animation;

import android.view.View;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                StackAnimationController stackAnimationController = (StackAnimationController) obj;
                stackAnimationController.setStackPosition(stackAnimationController.mPositioner.getRestingPosition());
                stackAnimationController.mStackMovedToStartPosition = true;
                stackAnimationController.mLayout.setVisibility(0);
                if (stackAnimationController.mLayout.getChildCount() > 0) {
                    stackAnimationController.mFloatingContentCoordinator.onContentAdded(stackAnimationController.mStackFloatingContent);
                    stackAnimationController.animateInBubble(stackAnimationController.mLayout.getChildAt(0), 0);
                    break;
                }
                break;
            default:
                ((View) obj).setTag(R.id.reorder_animator_tag, null);
                break;
        }
    }
}
