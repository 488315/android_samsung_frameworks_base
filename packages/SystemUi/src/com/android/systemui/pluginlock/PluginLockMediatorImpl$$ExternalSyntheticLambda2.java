package com.android.systemui.pluginlock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockMediatorImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockMediatorImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PluginLockMediatorImpl$$ExternalSyntheticLambda2(PluginLockMediatorImpl pluginLockMediatorImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = pluginLockMediatorImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PluginLockMediatorImpl pluginLockMediatorImpl = this.f$0;
                pluginLockMediatorImpl.mWindowListener.onViewModeChanged(this.f$1);
                break;
            default:
                PluginLockMediatorImpl pluginLockMediatorImpl2 = this.f$0;
                pluginLockMediatorImpl2.mBasicListener.onBarStateChanged(this.f$1);
                break;
        }
    }
}
