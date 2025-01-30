package com.android.systemui.pluginlock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                this.f$0.setDynamicLockDataInternal(this.f$1);
                break;
            default:
                PluginLockDelegateSysUi pluginLockDelegateSysUi = this.f$0;
                ((PluginLockMediatorImpl) pluginLockDelegateSysUi.mMediator).updateDynamicLockData(this.f$1);
                break;
        }
    }
}
