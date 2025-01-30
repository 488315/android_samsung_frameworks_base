package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import com.android.wm.shell.recents.RecentTasksController;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                List tasks = ((RecentTasksController) obj).mActivityTaskManager.getTasks(1, false);
                if (tasks.isEmpty()) {
                    return null;
                }
                return (ActivityManager.RunningTaskInfo) tasks.get(0);
            default:
                List tasks2 = ((RecentTasksController) obj).mActivityTaskManager.getTasks(1, false);
                if (tasks2.isEmpty()) {
                    return null;
                }
                return (ActivityManager.RunningTaskInfo) tasks2.get(0);
        }
    }
}
