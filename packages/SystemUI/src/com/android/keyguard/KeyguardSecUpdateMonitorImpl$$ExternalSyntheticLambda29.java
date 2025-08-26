package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import com.samsung.android.bio.face.SemBioFaceManager;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29(int i, String str, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                String str = this.f$1;
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onBiometricHelp(i, str, BiometricSourceType.FINGERPRINT);
                break;
            default:
                int i2 = this.f$0;
                String str2 = this.f$1;
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onBiometricError(i2, str2, BiometricSourceType.FACE);
                break;
        }
    }
}
