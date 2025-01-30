package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(int i, String str, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricError(this.f$0, this.f$1, BiometricSourceType.FACE);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onBiometricHelp(this.f$0, this.f$1, BiometricSourceType.FINGERPRINT);
                break;
        }
    }
}
