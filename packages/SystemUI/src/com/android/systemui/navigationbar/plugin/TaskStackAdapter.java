package com.android.systemui.navigationbar.plugin;

import com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase;
import java.util.List;

public final class TaskStackAdapter implements TaskStackAdapterBase {
    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final List getRecentComponents(int i) {
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final List getRecentTasks(int i) {
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final void removeTaskStackListener() {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase
    public final void addTaskStackListener(Runnable runnable) {
    }
}
