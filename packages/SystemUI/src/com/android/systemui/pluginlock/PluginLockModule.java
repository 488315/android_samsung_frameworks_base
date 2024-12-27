package com.android.systemui.pluginlock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class PluginLockModule {
    public abstract PluginLockData bindPluginLockData(PluginLockDataImpl pluginLockDataImpl);

    public abstract PluginLockManager bindPluginLockManager(PluginLockManagerImpl pluginLockManagerImpl);

    public abstract PluginLockMediator bindPluginLockMediator(PluginLockMediatorImpl pluginLockMediatorImpl);

    public abstract PluginWallpaperManager bindPluginWallpaperManager(PluginWallpaperManagerImpl pluginWallpaperManagerImpl);
}
