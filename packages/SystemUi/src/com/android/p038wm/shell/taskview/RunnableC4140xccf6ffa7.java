package com.android.p038wm.shell.taskview;

import android.content.Context;
import com.android.p038wm.shell.taskview.TaskViewFactoryController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.taskview.TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4140xccf6ffa7 implements Runnable {
    public final /* synthetic */ TaskViewFactoryController.TaskViewFactoryImpl f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ Executor f$2;
    public final /* synthetic */ Consumer f$3;

    public /* synthetic */ RunnableC4140xccf6ffa7(TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl, Context context, DelayableExecutor delayableExecutor, Consumer consumer) {
        this.f$0 = taskViewFactoryImpl;
        this.f$1 = context;
        this.f$2 = delayableExecutor;
        this.f$3 = consumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl = this.f$0;
        Context context = this.f$1;
        Executor executor = this.f$2;
        final Consumer consumer = this.f$3;
        TaskViewFactoryController taskViewFactoryController = TaskViewFactoryController.this;
        taskViewFactoryController.getClass();
        final TaskView taskView = new TaskView(context, new TaskViewTaskController(context, taskViewFactoryController.mTaskOrganizer, taskViewFactoryController.mTaskViewTransitions, taskViewFactoryController.mSyncQueue));
        executor.execute(new Runnable() { // from class: com.android.wm.shell.taskview.TaskViewFactoryController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(taskView);
            }
        });
    }
}
