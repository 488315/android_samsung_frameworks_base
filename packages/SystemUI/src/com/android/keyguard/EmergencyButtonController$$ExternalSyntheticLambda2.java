package com.android.keyguard;

import android.telecom.TelecomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        EmergencyButtonController.$r8$lambda$e6ceecPxW3wEM4fHjkBzcKaihjE(EmergencyButtonController.this, z);
                    }
                });
                break;
        }
    }
}
