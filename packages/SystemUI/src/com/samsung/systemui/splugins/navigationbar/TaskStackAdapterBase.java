package com.samsung.systemui.splugins.navigationbar;

import android.app.ActivityManager;
import android.content.ComponentName;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface TaskStackAdapterBase {
    void addTaskStackListener(Runnable runnable);

    List<ComponentName> getRecentComponents(int i);

    List<ActivityManager.RecentTaskInfo> getRecentTasks(int i);

    void removeTaskStackListener();
}
