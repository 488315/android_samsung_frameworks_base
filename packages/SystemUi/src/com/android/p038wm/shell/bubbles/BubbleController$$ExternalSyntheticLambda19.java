package com.android.p038wm.shell.bubbles;

import android.view.WindowManager;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda19 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda19(int i, Object obj, boolean z) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BubbleStackView bubbleStackView;
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) this.f$0;
                boolean z = this.f$1;
                WindowManager windowManager = bubbleController.mWindowManager;
                if (windowManager != null && (bubbleStackView = bubbleController.mStackView) != null && bubbleController.mWmLayoutParams != null && bubbleStackView.isAttachedToWindow()) {
                    if (z) {
                        bubbleController.mWmLayoutParams.semAddExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    } else {
                        bubbleController.mWmLayoutParams.semClearExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    }
                    windowManager.updateViewLayout(bubbleController.mStackView, bubbleController.mWmLayoutParams);
                    break;
                }
                break;
            case 1:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                boolean z2 = this.f$1;
                BubbleController bubbleController2 = BubbleController.this;
                BubbleStackView bubbleStackView2 = bubbleController2.mStackView;
                if (bubbleStackView2 != null) {
                    boolean z3 = (z2 || bubbleController2.isStackExpanded()) ? false : true;
                    bubbleStackView2.mTemporarilyInvisible = z3;
                    bubbleStackView2.updateTemporarilyInvisibleAnimation(z3);
                    break;
                }
                break;
            case 2:
                BubbleController.this.onStatusBarStateChanged(this.f$1);
                break;
            default:
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) this.f$0;
                boolean z4 = this.f$1;
                BubbleStackView bubbleStackView3 = BubbleController.this.mStackView;
                if (bubbleStackView3 != null && bubbleStackView3.mIsExpanded) {
                    if (!z4) {
                        bubbleStackView3.startMonitoringSwipeUpGesture();
                        break;
                    } else {
                        bubbleStackView3.stopMonitoringSwipeUpGestureInternal();
                        break;
                    }
                }
                break;
        }
    }
}
