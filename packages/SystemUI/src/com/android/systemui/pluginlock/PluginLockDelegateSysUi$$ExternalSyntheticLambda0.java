package com.android.systemui.pluginlock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockDelegateSysUi$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockDelegateSysUi f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ PluginLockDelegateSysUi$$ExternalSyntheticLambda0(PluginLockDelegateSysUi pluginLockDelegateSysUi, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginLockDelegateSysUi;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$setDynamicLockData$0(this.f$1);
                break;
            default:
                this.f$0.lambda$updateDynamicLockData$1(this.f$1);
                break;
        }
    }
}
