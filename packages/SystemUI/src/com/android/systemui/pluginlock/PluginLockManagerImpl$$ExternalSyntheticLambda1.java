package com.android.systemui.pluginlock;

public final /* synthetic */ class PluginLockManagerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockManagerImpl f$0;

    public /* synthetic */ PluginLockManagerImpl$$ExternalSyntheticLambda1(PluginLockManagerImpl pluginLockManagerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginLockManagerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PluginLockManagerImpl pluginLockManagerImpl = this.f$0;
        switch (i) {
            case 0:
                pluginLockManagerImpl.lambda$onUserSwitchComplete$2();
                break;
            case 1:
                pluginLockManagerImpl.lambda$setPluginInstanceState$4();
                break;
            default:
                pluginLockManagerImpl.disableByMode();
                break;
        }
    }
}
