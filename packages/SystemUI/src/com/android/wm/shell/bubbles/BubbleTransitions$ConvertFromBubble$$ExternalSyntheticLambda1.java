package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.taskview.TaskViewTaskController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ BubbleTransitions.ConvertFromBubble f$0;
    public final /* synthetic */ TaskViewTaskController f$1;

    public /* synthetic */ BubbleTransitions$ConvertFromBubble$$ExternalSyntheticLambda1(BubbleTransitions.ConvertFromBubble convertFromBubble, TaskViewTaskController taskViewTaskController) {
        this.f$0 = convertFromBubble;
        this.f$1 = taskViewTaskController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BubbleTransitions.ConvertFromBubble convertFromBubble = this.f$0;
        TaskViewTaskController taskViewTaskController = this.f$1;
        convertFromBubble.getClass();
        taskViewTaskController.notifyTaskRemovalStarted(taskViewTaskController.mTaskInfo);
        convertFromBubble.mBubble.mPreparingTransition = null;
        BubbleTransitions.this.mBubbleData.setExpanded(false);
    }
}
