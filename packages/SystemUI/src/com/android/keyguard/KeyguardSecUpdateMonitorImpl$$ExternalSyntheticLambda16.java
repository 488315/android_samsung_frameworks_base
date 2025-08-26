package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import com.samsung.android.bio.face.SemBioFaceManager;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(int i, int i2) {
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
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onTrustChanged(i2);
                break;
            case 1:
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onDualDARInnerLockscreenRequirementChanged(i2);
                break;
            case 2:
                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT, i2);
                break;
            case 3:
                SemBioFaceManager semBioFaceManager4 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT, i2);
                break;
            case 4:
                SemBioFaceManager semBioFaceManager5 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onDlsViewModeChanged(i2);
                break;
            default:
                SemBioFaceManager semBioFaceManager6 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onEmergencyStateChanged(i2);
                break;
        }
    }
}
