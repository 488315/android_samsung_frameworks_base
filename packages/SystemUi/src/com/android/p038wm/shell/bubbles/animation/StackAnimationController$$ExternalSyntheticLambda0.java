package com.android.p038wm.shell.bubbles.animation;

import android.view.View;
import com.android.p038wm.shell.bubbles.animation.StackAnimationController;
import com.android.p038wm.shell.common.FloatingContentCoordinator;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StackAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StackAnimationController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((View) this.f$0).setTag(R.id.reorder_animator_tag, null);
                break;
            default:
                StackAnimationController stackAnimationController = (StackAnimationController) this.f$0;
                stackAnimationController.setStackPosition(stackAnimationController.mPositioner.getRestingPosition());
                stackAnimationController.mStackMovedToStartPosition = true;
                stackAnimationController.mLayout.setVisibility(0);
                if (stackAnimationController.mLayout.getChildCount() > 0) {
                    FloatingContentCoordinator floatingContentCoordinator = stackAnimationController.mFloatingContentCoordinator;
                    floatingContentCoordinator.updateContentBounds();
                    Map map = floatingContentCoordinator.allContentBounds;
                    StackAnimationController.C38431 c38431 = stackAnimationController.mStackFloatingContent;
                    ((HashMap) map).put(c38431, c38431.getFloatingBoundsOnScreen());
                    floatingContentCoordinator.maybeMoveConflictingContent(c38431);
                    stackAnimationController.animateInBubble(stackAnimationController.mLayout.getChildAt(0), 0);
                    break;
                }
                break;
        }
    }
}
