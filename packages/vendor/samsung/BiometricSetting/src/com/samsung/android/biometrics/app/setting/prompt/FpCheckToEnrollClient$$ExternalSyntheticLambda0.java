package com.samsung.android.biometrics.app.setting.prompt;

import android.hardware.fingerprint.FingerprintManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class FpCheckToEnrollClient$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FpCheckToEnrollClient f$0;

    public /* synthetic */ FpCheckToEnrollClient$$ExternalSyntheticLambda0(
            FpCheckToEnrollClient fpCheckToEnrollClient, int i) {
        this.$r8$classId = i;
        this.f$0 = fpCheckToEnrollClient;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        FpCheckToEnrollClient fpCheckToEnrollClient = this.f$0;
        switch (i) {
            case 0:
                BiometricPromptWindow biometricPromptWindow = fpCheckToEnrollClient.mPromptWindow;
                biometricPromptWindow.mPromptGuiHelper.handleAuthenticationHelp(
                        0, FingerprintManager.getErrorString(fpCheckToEnrollClient.mContext, 3, 0));
                fpCheckToEnrollClient.mHandler.postDelayed(
                        new FpCheckToEnrollClient$$ExternalSyntheticLambda0(
                                fpCheckToEnrollClient, 2),
                        2000L);
                break;
            case 1:
                fpCheckToEnrollClient.sendEvent(1001, 0);
                fpCheckToEnrollClient.restartTimeout();
                break;
            default:
                fpCheckToEnrollClient.onPromptError(3);
                break;
        }
    }
}
