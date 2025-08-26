package com.android.keyguard;

import android.telecom.TelecomManager;

/* loaded from: classes.dex */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda2(EmergencyButtonController emergencyButtonController, int i) {
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
                emergencyButtonController.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmergencyButtonController.$r8$lambda$e6ceecPxW3wEM4fHjkBzcKaihjE(emergencyButtonController, z);
                    }
                });
                break;
        }
    }
}
