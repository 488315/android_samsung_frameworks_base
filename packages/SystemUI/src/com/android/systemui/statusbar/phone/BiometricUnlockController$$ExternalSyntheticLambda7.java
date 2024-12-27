package com.android.systemui.statusbar.phone;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BiometricUnlockController$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricUnlockController f$0;

    public /* synthetic */ BiometricUnlockController$$ExternalSyntheticLambda7(BiometricUnlockController biometricUnlockController, int i) {
        this.$r8$classId = i;
        this.f$0 = biometricUnlockController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BiometricUnlockController biometricUnlockController = this.f$0;
        switch (i) {
            case 0:
                biometricUnlockController.mKeyguardViewMediator.userActivity();
                break;
            default:
                UiEventLogger uiEventLogger = BiometricUnlockController.UI_EVENT_LOGGER;
                biometricUnlockController.updateBackgroundAuthToastForBiometrics();
                break;
        }
    }
}
