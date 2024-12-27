package com.android.systemui.pluginlock;

import android.util.Log;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.samsung.android.cover.CoverState;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class DisabledPluginLockManager implements PluginLockManager {
    public DisabledPluginLockManager() {
        Log.i("DisabledPluginLockManager", "## DisabledPluginLockManager ##, " + this);
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public String getDynamicLockData() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public String getLockStarItemLocationInfo(String str) {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public PluginLock getPluginLock() {
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public boolean getShortcutTaskState(String str) {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void removeShortcutTaskListener() {
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void onCoverStateChanged(CoverState coverState) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void onFolderStateChanged(boolean z) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void registerSystemUIViewCallback(PluginLockListener.State state) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void removeSystemUIViewCallback(PluginLockListener.State state) {
    }

    @Override // com.android.systemui.pluginlock.PluginLockManager
    public void updateShortcutTaskState(String str) {
    }
}
