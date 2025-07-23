package com.android.systemui.pluginlock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockMediatorImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PluginLockMediatorImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PluginLockMediatorImpl$$ExternalSyntheticLambda2(PluginLockMediatorImpl pluginLockMediatorImpl, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = pluginLockMediatorImpl;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$updateWindowSecureState$2(this.f$1);
                break;
            case 1:
                this.f$0.lambda$setScreenOrientation$8(this.f$1);
                break;
            case 2:
                this.f$0.lambda$updateOverlayUserTimeout$3(this.f$1);
                break;
            default:
                this.f$0.lambda$updateBiometricRecognition$4(this.f$1);
                break;
        }
    }
}
