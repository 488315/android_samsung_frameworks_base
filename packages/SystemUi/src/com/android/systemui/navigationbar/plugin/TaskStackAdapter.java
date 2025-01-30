package com.android.systemui.navigationbar.plugin;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TaskStackAdapter implements TaskStackAdapterBase {
    public TaskStackAdapter$addTaskStackListener$2 callback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.navigationbar.plugin.TaskStackAdapter$addTaskStackListener$2, com.android.systemui.shared.system.TaskStackChangeListener] */
    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final void addTaskStackListener(final Runnable runnable) {
        TaskStackAdapter$addTaskStackListener$2 taskStackAdapter$addTaskStackListener$2 = this.callback;
        if (taskStackAdapter$addTaskStackListener$2 != null) {
            TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(taskStackAdapter$addTaskStackListener$2);
        }
        ?? r0 = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.plugin.TaskStackAdapter$addTaskStackListener$2
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskStackChanged() {
                runnable.run();
            }
        };
        this.callback = r0;
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(r0);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final List getRecentComponents(int i) {
        ArrayList arrayList = new ArrayList();
        ActivityManagerWrapper activityManagerWrapper = ActivityManagerWrapper.sInstance;
        for (ActivityManager.RecentTaskInfo recentTaskInfo : activityManagerWrapper.mAtm.getRecentTasks(i, 2, UserHandle.myUserId())) {
            ComponentName componentName = recentTaskInfo.origActivity;
            if (componentName == null) {
                componentName = recentTaskInfo.realActivity;
            }
            arrayList.add(componentName);
        }
        return arrayList;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final List getRecentTasks(int i) {
        ActivityManagerWrapper activityManagerWrapper = ActivityManagerWrapper.sInstance;
        return activityManagerWrapper.mAtm.getRecentTasks(i, 2, UserHandle.myUserId());
    }

    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final void removeTaskStackListener() {
        TaskStackAdapter$addTaskStackListener$2 taskStackAdapter$addTaskStackListener$2 = this.callback;
        if (taskStackAdapter$addTaskStackListener$2 != null) {
            TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(taskStackAdapter$addTaskStackListener$2);
        }
    }
}
