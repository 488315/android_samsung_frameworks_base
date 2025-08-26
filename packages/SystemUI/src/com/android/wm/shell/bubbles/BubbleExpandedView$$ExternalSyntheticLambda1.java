package com.android.wm.shell.bubbles;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.TouchDelegate;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BubbleExpandedView;
import com.android.wm.shell.taskview.TaskView;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleExpandedView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleExpandedView f$0;

    public /* synthetic */ BubbleExpandedView$$ExternalSyntheticLambda1(BubbleExpandedView bubbleExpandedView, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleExpandedView;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        int i = this.$r8$classId;
        BubbleExpandedView bubbleExpandedView = this.f$0;
        switch (i) {
            case 0:
                BubbleExpandedView.AnonymousClass1 anonymousClass1 = BubbleExpandedView.BOTTOM_CLIP_PROPERTY;
                int dimensionPixelSize = bubbleExpandedView.getResources().getDimensionPixelSize(R.dimen.bubble_manage_button_touch_area_height);
                Rect rect = new Rect();
                bubbleExpandedView.mManageButton.getHitRect(rect);
                int iHeight = (dimensionPixelSize - rect.height()) / 2;
                rect.top -= iHeight;
                rect.bottom += iHeight;
                bubbleExpandedView.setTouchDelegate(new TouchDelegate(rect, bubbleExpandedView.mManageButton));
                break;
            case 1:
                TaskView taskView = bubbleExpandedView.mTaskView;
                if (taskView != null) {
                    taskView.getBoundsOnScreen(taskView.mTmpRect);
                    taskView.mTaskViewController.setTaskBounds(taskView.mTaskViewTaskController, taskView.mTmpRect);
                    break;
                }
                break;
            default:
                bubbleExpandedView.mOverflowView.show();
                break;
        }
    }
}
