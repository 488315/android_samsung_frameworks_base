package com.android.systemui.pluginlock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockMediatorImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockMediatorImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PluginLockMediatorImpl$$ExternalSyntheticLambda1(PluginLockMediatorImpl pluginLockMediatorImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = pluginLockMediatorImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onBarStateChanged$5(this.f$1);
                break;
            default:
                this.f$0.lambda$onViewModeChanged$0(this.f$1);
                break;
        }
    }
}
