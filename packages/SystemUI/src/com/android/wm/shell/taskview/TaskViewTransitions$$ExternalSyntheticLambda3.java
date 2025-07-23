package com.android.wm.shell.taskview;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.window.WindowContainerTransaction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TaskViewTransitions$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ TaskViewTransitions f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ ActivityOptions f$3;
    public final /* synthetic */ TaskViewTaskController f$4;

    public /* synthetic */ TaskViewTransitions$$ExternalSyntheticLambda3(TaskViewTransitions taskViewTransitions, PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, TaskViewTaskController taskViewTaskController) {
        this.f$0 = taskViewTransitions;
        this.f$1 = pendingIntent;
        this.f$2 = intent;
        this.f$3 = activityOptions;
        this.f$4 = taskViewTaskController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TaskViewTransitions taskViewTransitions = this.f$0;
                PendingIntent pendingIntent = (PendingIntent) this.f$1;
                Intent intent = (Intent) this.f$2;
                ActivityOptions activityOptions = this.f$3;
                TaskViewTaskController taskViewTaskController = this.f$4;
                taskViewTransitions.getClass();
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.sendPendingIntent(pendingIntent, intent, activityOptions.toBundle());
                taskViewTransitions.startTaskView(windowContainerTransaction, taskViewTaskController, activityOptions.getLaunchCookie());
                break;
            default:
                TaskViewTransitions taskViewTransitions2 = this.f$0;
                Context context = (Context) this.f$1;
                ShortcutInfo shortcutInfo = (ShortcutInfo) this.f$2;
                ActivityOptions activityOptions2 = this.f$3;
                TaskViewTaskController taskViewTaskController2 = this.f$4;
                taskViewTransitions2.getClass();
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.startShortcut(context.getPackageName(), shortcutInfo, activityOptions2.toBundle());
                taskViewTransitions2.startTaskView(windowContainerTransaction2, taskViewTaskController2, activityOptions2.getLaunchCookie());
                break;
        }
    }

    public /* synthetic */ TaskViewTransitions$$ExternalSyntheticLambda3(TaskViewTransitions taskViewTransitions, Context context, ShortcutInfo shortcutInfo, ActivityOptions activityOptions, TaskViewTaskController taskViewTaskController) {
        this.f$0 = taskViewTransitions;
        this.f$1 = context;
        this.f$2 = shortcutInfo;
        this.f$3 = activityOptions;
        this.f$4 = taskViewTaskController;
    }
}
