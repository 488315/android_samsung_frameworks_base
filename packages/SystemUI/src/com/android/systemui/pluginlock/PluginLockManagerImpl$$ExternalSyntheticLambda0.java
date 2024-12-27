package com.android.systemui.pluginlock;

public final /* synthetic */ class PluginLockManagerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockManagerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PluginLockManagerImpl$$ExternalSyntheticLambda0(PluginLockManagerImpl pluginLockManagerImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = pluginLockManagerImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$handleEnableStateChanged$1(this.f$1);
                break;
            default:
                this.f$0.lambda$onUserSwitchComplete$3(this.f$1);
                break;
        }
    }
}
