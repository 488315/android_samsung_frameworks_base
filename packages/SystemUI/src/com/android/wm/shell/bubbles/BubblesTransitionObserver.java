package com.android.wm.shell.bubbles;

import android.app.ActivityManager;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.Transitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubblesTransitionObserver implements Transitions.TransitionObserver {
    public final BubbleController mBubbleController;
    public final BubbleData mBubbleData;

    public BubblesTransitionObserver(BubbleController bubbleController, BubbleData bubbleData) {
        this.mBubbleController = bubbleController;
        this.mBubbleData = bubbleData;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        BubbleStackView bubbleStackView;
        BubbleViewProvider bubbleViewProvider;
        int taskId;
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null && taskInfo.taskId != -1 && TransitionUtil.isOpeningType(change.getMode()) && ((bubbleStackView = this.mBubbleController.mStackView) == null || (!bubbleStackView.mIsExpansionAnimating && !bubbleStackView.mIsBubbleSwitchAnimating))) {
                BubbleData bubbleData = this.mBubbleData;
                if (bubbleData.mExpanded && (bubbleViewProvider = bubbleData.mSelectedBubble) != null && (taskId = bubbleViewProvider.getTaskId()) != -1 && taskId != taskInfo.taskId) {
                    bubbleData.setExpanded(false);
                }
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}
