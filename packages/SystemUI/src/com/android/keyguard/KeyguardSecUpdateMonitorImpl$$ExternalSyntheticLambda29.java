package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29(int i, boolean z, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = (KeyguardSecUpdateMonitorImpl) this.f$0;
                ((KeyguardUpdateMonitorCallback) obj).onBiometricAuthenticated(((UserTrackerImpl) keyguardSecUpdateMonitorImpl.mUserTracker).getUserId(), BiometricSourceType.FACE, this.f$1);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onPackageRemoved((String) this.f$0, this.f$1);
                break;
        }
    }
}
