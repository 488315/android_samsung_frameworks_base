package com.android.systemui.pluginlock;

import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockManagerImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockManagerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PluginLockManagerImpl$$ExternalSyntheticLambda3(PluginLockManagerImpl pluginLockManagerImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = pluginLockManagerImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PluginLockManagerImpl pluginLockManagerImpl = this.f$0;
                int i = this.f$1;
                pluginLockManagerImpl.getClass();
                Log.d("PluginLockManagerImpl", "onUserSwitchComplete for " + i);
                pluginLockManagerImpl.mIsSwitchingToSub = false;
                break;
            default:
                PluginLockManagerImpl pluginLockManagerImpl2 = this.f$0;
                int i2 = this.f$1;
                pluginLockManagerImpl2.updatePluginLockMode(i2, pluginLockManagerImpl2.isEnabledFromSettingHelper(i2), false);
                break;
        }
    }
}
