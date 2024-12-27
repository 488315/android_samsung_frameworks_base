package com.android.systemui.pluginlock;

import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.samsung.android.cover.CoverState;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginLockManager {
    String getDynamicLockData();

    String getLockStarItemLocationInfo(String str);

    PluginLock getPluginLock();

    boolean getShortcutTaskState(String str);

    void onCoverStateChanged(CoverState coverState);

    void onFolderStateChanged(boolean z);

    void registerSystemUIViewCallback(PluginLockListener.State state);

    void removeShortcutTaskListener();

    void removeSystemUIViewCallback(PluginLockListener.State state);

    void updateShortcutTaskState(String str);
}
