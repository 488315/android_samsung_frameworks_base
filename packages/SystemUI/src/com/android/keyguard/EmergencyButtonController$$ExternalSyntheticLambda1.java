package com.android.keyguard;

import android.telecom.TelecomManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda1(EmergencyButtonController emergencyButtonController, int i) {
        this.$r8$classId = i;
        this.f$0 = emergencyButtonController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final EmergencyButtonController emergencyButtonController = this.f$0;
        switch (i) {
            case 0:
                emergencyButtonController.updateEmergencyCallButton();
                break;
            default:
                TelecomManager telecomManager = emergencyButtonController.mTelecomManager;
                final boolean z = telecomManager != null && telecomManager.isInCall();
                emergencyButtonController.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmergencyButtonController.$r8$lambda$05evS2cO47UZK0SrphgxT6aEUMU(EmergencyButtonController.this, z);
                    }
                });
                break;
        }
    }
}
