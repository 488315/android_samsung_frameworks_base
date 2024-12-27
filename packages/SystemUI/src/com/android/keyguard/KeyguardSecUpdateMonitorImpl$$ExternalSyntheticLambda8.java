package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda8(int i, String str, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricHelp(this.f$0, this.f$1, BiometricSourceType.FINGERPRINT);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricError(this.f$0, this.f$1, BiometricSourceType.FACE);
                break;
        }
    }
}
