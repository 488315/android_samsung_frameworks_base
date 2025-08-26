package com.samsung.systemui.splugins.navigationbar;

import android.app.ActivityManager;
import android.content.ComponentName;
import java.util.List;

/* loaded from: classes4.dex */
public interface TaskStackAdapterBase {
    void addTaskStackListener(Runnable runnable);

    List<ComponentName> getRecentComponents(int i);

    List<ActivityManager.RecentTaskInfo> getRecentTasks(int i);

    void removeTaskStackListener();
}
