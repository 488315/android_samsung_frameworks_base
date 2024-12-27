package com.android.systemui.pluginlock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
