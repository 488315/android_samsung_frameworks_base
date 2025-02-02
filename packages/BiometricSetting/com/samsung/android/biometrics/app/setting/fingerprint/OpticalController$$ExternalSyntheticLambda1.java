package com.samsung.android.biometrics.app.setting.fingerprint;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class OpticalController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OpticalController f$0;

    public /* synthetic */ OpticalController$$ExternalSyntheticLambda1(OpticalController opticalController, int i) {
        this.$r8$classId = i;
        this.f$0 = opticalController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OpticalController.$r8$lambda$ghgrl0VfKVTeKIFvHiccu8fkgPU(this.f$0);
                break;
            default:
                this.f$0.turnOnCalibrationLightSource();
                break;
        }
    }
}
