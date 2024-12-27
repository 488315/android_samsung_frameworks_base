package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda19 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda19(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
        switch (i) {
            case 0:
                keyguardUpdateMonitorCallback.onTrustChanged(i2);
                break;
            case 1:
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT, i2);
                break;
            case 2:
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT, i2);
                break;
            case 3:
                keyguardUpdateMonitorCallback.onTrustChanged(i2);
                break;
            case 4:
                keyguardUpdateMonitorCallback.onDlsViewModeChanged(i2);
                break;
            case 5:
                keyguardUpdateMonitorCallback.onDualDARInnerLockscreenRequirementChanged(i2);
                break;
            default:
                keyguardUpdateMonitorCallback.onEmergencyStateChanged(i2);
                break;
        }
    }
}
