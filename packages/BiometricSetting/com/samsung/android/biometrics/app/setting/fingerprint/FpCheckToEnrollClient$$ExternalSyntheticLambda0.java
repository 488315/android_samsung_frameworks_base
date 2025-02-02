package com.samsung.android.biometrics.app.setting.fingerprint;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class FpCheckToEnrollClient$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FpCheckToEnrollClient f$0;

    public /* synthetic */ FpCheckToEnrollClient$$ExternalSyntheticLambda0(FpCheckToEnrollClient fpCheckToEnrollClient, int i) {
        this.$r8$classId = i;
        this.f$0 = fpCheckToEnrollClient;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FpCheckToEnrollClient.m89$r8$lambda$WvyY0SpZfQt5u82QIjxp2KRyEc(this.f$0);
                break;
            default:
                this.f$0.onPromptError(3);
                break;
        }
    }
}
