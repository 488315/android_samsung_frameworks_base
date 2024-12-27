package com.samsung.android.biometrics.app.setting.fingerprint;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class UdfpsKeyguardClient$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ UdfpsKeyguardClient f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ UdfpsKeyguardClient$$ExternalSyntheticLambda0(
            UdfpsKeyguardClient udfpsKeyguardClient, long j, boolean z) {
        this.f$0 = udfpsKeyguardClient;
        this.f$1 = j;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.showSensorIconDueToFodTouchWhenScreenIsOff(this.f$1, this.f$2);
    }
}
