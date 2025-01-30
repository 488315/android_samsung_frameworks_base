package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda9(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricAcquired(BiometricSourceType.FINGERPRINT, this.f$0);
                break;
            case 1:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricAcquired(BiometricSourceType.FINGERPRINT, this.f$0);
                break;
            case 2:
                ((KeyguardUpdateMonitorCallback) obj).onTrustChanged(this.f$0);
                break;
            case 3:
                ((KeyguardUpdateMonitorCallback) obj).onEmergencyStateChanged(this.f$0);
                break;
            case 4:
                ((KeyguardUpdateMonitorCallback) obj).onDualDARInnerLockscreenRequirementChanged(this.f$0);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onDlsViewModeChanged(this.f$0);
                break;
        }
    }
}
