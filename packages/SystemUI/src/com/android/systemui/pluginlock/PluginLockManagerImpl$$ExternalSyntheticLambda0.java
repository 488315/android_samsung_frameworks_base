package com.android.systemui.pluginlock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
