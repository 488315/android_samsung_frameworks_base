package com.android.systemui.pluginlock;

public class DisabledPluginLockData implements PluginLockData {
    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getBottom(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getCount(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getGravity(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getPaddingBottom(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getPaddingEnd(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getPaddingStart(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getTop(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public int getVisibility(int i) {
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public boolean isAvailable() {
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginLockData
    public boolean isAvailable(int i) {
        return false;
    }
}
