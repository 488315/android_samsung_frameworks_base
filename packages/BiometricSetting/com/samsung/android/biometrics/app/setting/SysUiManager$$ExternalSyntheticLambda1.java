package com.samsung.android.biometrics.app.setting;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class SysUiManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SysUiManager f$0;

    public /* synthetic */ SysUiManager$$ExternalSyntheticLambda1(SysUiManager sysUiManager, int i) {
        this.$r8$classId = i;
        this.f$0 = sysUiManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SysUiManager.m32$r8$lambda$vlAwftBEVxNmjPjEaZlziQbGck(this.f$0);
                break;
            case 1:
                this.f$0.mDisplayStateManager.updateDisplayStateInAuthenticationSucceeded();
                break;
            default:
                this.f$0.mOpticalController.turnOnCalibrationLightSource();
                break;
        }
    }
}
