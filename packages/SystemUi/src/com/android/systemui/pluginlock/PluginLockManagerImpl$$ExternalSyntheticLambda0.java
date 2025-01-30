package com.android.systemui.pluginlock;

import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockManagerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockManagerImpl f$0;

    public /* synthetic */ PluginLockManagerImpl$$ExternalSyntheticLambda0(PluginLockManagerImpl pluginLockManagerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginLockManagerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((PluginWallpaperManagerImpl) this.f$0.mPluginWallpaperManager).onLockWallpaperChanged(0);
                break;
            case 1:
                this.f$0.disableByMode();
                break;
            default:
                PluginLockManagerImpl pluginLockManagerImpl = this.f$0;
                pluginLockManagerImpl.getClass();
                Log.d("PluginLockManagerImpl", "onUserSwitchComplete for owner");
                pluginLockManagerImpl.setLatestPluginInstance(false);
                pluginLockManagerImpl.mIsSwitchingToSub = false;
                break;
        }
    }
}
