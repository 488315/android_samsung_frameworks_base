package com.android.wm.shell.bubbles;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.os.UserHandle;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$IBubblesImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController.IBubblesImpl f$0;

    public /* synthetic */ BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(BubbleController.IBubblesImpl iBubblesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = iBubblesImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BubbleController.IBubblesImpl iBubblesImpl = this.f$0;
        switch (i) {
            case 0:
                iBubblesImpl.mController.removeAllBubbles(1);
                break;
            case 1:
                BubbleViewProvider bubbleViewProvider = BubbleController.this.mBubbleData.mSelectedBubble;
                if (!(bubbleViewProvider instanceof Bubble) || ((Bubble) bubbleViewProvider).mPreparingTransition == null) {
                    iBubblesImpl.mController.collapseStack();
                    break;
                }
                break;
            case 2:
                iBubblesImpl.mController.getClass();
                break;
            case 3:
                iBubblesImpl.mController.getClass();
                break;
            case 4:
                BubbleController.this.getClass();
                break;
            default:
                iBubblesImpl.mController.getClass();
                break;
        }
    }

    public /* synthetic */ BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(BubbleController.IBubblesImpl iBubblesImpl, Intent intent, UserHandle userHandle, BubbleBarLocation bubbleBarLocation) {
        this.$r8$classId = 2;
        this.f$0 = iBubblesImpl;
    }

    public /* synthetic */ BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(BubbleController.IBubblesImpl iBubblesImpl, ShortcutInfo shortcutInfo, BubbleBarLocation bubbleBarLocation) {
        this.$r8$classId = 5;
        this.f$0 = iBubblesImpl;
    }

    public /* synthetic */ BubbleController$IBubblesImpl$$ExternalSyntheticLambda2(BubbleController.IBubblesImpl iBubblesImpl, BubbleBarLocation bubbleBarLocation, int i) {
        this.$r8$classId = 3;
        this.f$0 = iBubblesImpl;
    }
}
