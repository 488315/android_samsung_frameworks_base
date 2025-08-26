package com.android.wm.shell.bubbles;

import android.content.res.Resources;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda10(BubbleController.BubblesImpl bubblesImpl, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = bubblesImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        switch (this.$r8$classId) {
            case 0:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                boolean z = this.f$1;
                BubbleStackView bubbleStackView = BubbleController.this.mStackView;
                if (bubbleStackView != null) {
                    bubbleStackView.mSensitiveNotificationProtectionActive = z;
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 5894965654239984660L, 3, Boolean.valueOf(z));
                        break;
                    }
                }
                break;
            case 1:
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) this.f$0;
                boolean z2 = this.f$1;
                BubbleController bubbleController = BubbleController.this;
                BubbleStackView bubbleStackView2 = bubbleController.mStackView;
                if (bubbleStackView2 != null && bubbleStackView2.mIsExpanded) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -3454367840069733869L, 3, Boolean.valueOf(z2));
                    }
                    if (!z2) {
                        bubbleController.mStackView.startMonitoringSwipeUpGesture();
                        break;
                    } else {
                        bubbleController.mStackView.stopMonitoringSwipeUpGesture();
                        break;
                    }
                }
                break;
            case 2:
                BubbleController.BubblesImpl bubblesImpl3 = (BubbleController.BubblesImpl) this.f$0;
                boolean z3 = this.f$1;
                BubbleController bubbleController2 = BubbleController.this;
                BubbleStackView bubbleStackView3 = bubbleController2.mStackView;
                if (bubbleStackView3 != null) {
                    BubbleData bubbleData = bubbleController2.mBubbleData;
                    boolean z4 = (z3 || bubbleData.mExpanded) ? false : true;
                    bubbleStackView3.mTemporarilyInvisible = z4;
                    bubbleStackView3.updateTemporarilyInvisibleAnimation(z4);
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 3679537687983308475L, 15, Boolean.valueOf(z3), Boolean.valueOf(bubbleData.mExpanded));
                        break;
                    }
                }
                break;
            case 3:
                BubbleController.this.onStatusBarStateChanged(this.f$1);
                break;
            default:
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this.f$0;
                if (!this.f$1) {
                    BubbleController.this.getClass();
                    break;
                } else {
                    BubbleController.this.ensureBubbleViewsAndWindowCreated();
                    break;
                }
        }
    }

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda10(BubbleController.IBubblesImpl iBubblesImpl, boolean z, BubbleBarLocation bubbleBarLocation) {
        this.$r8$classId = 4;
        this.f$0 = iBubblesImpl;
        this.f$1 = z;
    }
}
