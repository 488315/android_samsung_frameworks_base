package com.samsung.systemui.splugins.navigationbar;

import android.app.ActivityManager;
import android.content.ComponentName;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface TaskStackAdapterBase {
    void addTaskStackListener(Runnable runnable);

    List<ComponentName> getRecentComponents(int i);

    List<ActivityManager.RecentTaskInfo> getRecentTasks(int i);

    void removeTaskStackListener();
}
