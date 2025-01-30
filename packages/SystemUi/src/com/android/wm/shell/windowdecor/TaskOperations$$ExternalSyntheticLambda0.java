package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.util.Log;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskOperations$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TaskOperations$$ExternalSyntheticLambda0(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        this.f$0 = runningTaskInfo;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.f$0;
        int i = this.f$1;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        int i2 = runningTaskInfo2.displayId;
        Iterator it = MultiWindowManager.getInstance().getVisibleTasks().iterator();
        while (true) {
            if (!it.hasNext()) {
                runningTaskInfo = null;
                break;
            }
            runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
            if (runningTaskInfo.displayId == i2 && runningTaskInfo.getWindowingMode() == 1) {
                break;
            }
        }
        if (runningTaskInfo == null || runningTaskInfo.supportsMultiWindow) {
            splitScreenController.onFreeformToSplitRequested(runningTaskInfo2, runningTaskInfo != null && (runningTaskInfo.getActivityType() == 2 || runningTaskInfo.getActivityType() == 3), i, false, null, false);
            return;
        }
        Log.w("TaskOperations", "moveFreeformToSplit: failed, not support mw, top fullscreen t#" + runningTaskInfo.taskId);
    }
}
