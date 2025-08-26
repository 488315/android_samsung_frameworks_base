package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTaskController;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleTransitions.ConvertToBubble f$0;

    public /* synthetic */ BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda2(BubbleTransitions.ConvertToBubble convertToBubble, int i) {
        this.$r8$classId = i;
        this.f$0 = convertToBubble;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BubbleTransitions.ConvertToBubble convertToBubble = this.f$0;
        switch (i) {
            case 0:
                TaskViewTaskController taskViewTaskController = convertToBubble.mBubble.mBubbleTaskView.taskView.mTaskViewTaskController;
                TaskViewRepository taskViewRepository = BubbleTransitions.this.mRepository;
                int iFindAndPrune = taskViewRepository.findAndPrune(taskViewTaskController);
                TaskViewRepository.TaskViewState taskViewState = iFindAndPrune < 0 ? null : (TaskViewRepository.TaskViewState) taskViewRepository.mTaskViews.get(iFindAndPrune);
                if (taskViewState != null) {
                    taskViewState.mVisible = true;
                    BubbleTransitions.ConvertToBubble.TransitionProgress transitionProgress = convertToBubble.mTransitionProgress;
                    if (transitionProgress.mTransitionReady && transitionProgress.mSurfaceReady) {
                        convertToBubble.playAnimation(true);
                        break;
                    }
                }
                break;
            default:
                convertToBubble.mFinishCb.onTransitionFinished(convertToBubble.mFinishWct);
                convertToBubble.mFinishCb = null;
                break;
        }
    }
}
