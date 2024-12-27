package com.android.systemui.pluginlock;

public abstract class PluginLockModule {
    public abstract PluginLockData bindPluginLockData(PluginLockDataImpl pluginLockDataImpl);

    public abstract PluginLockManager bindPluginLockManager(PluginLockManagerImpl pluginLockManagerImpl);

    public abstract PluginLockMediator bindPluginLockMediator(PluginLockMediatorImpl pluginLockMediatorImpl);

    public abstract PluginWallpaperManager bindPluginWallpaperManager(PluginWallpaperManagerImpl pluginWallpaperManagerImpl);
}
