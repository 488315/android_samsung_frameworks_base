package com.android.keyguard;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;

public final /* synthetic */ class KeyguardUpdateMonitor$2$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardUpdateMonitor$2$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD);
                break;
            case 1:
                KeyguardUpdateMonitor.AnonymousClass20 anonymousClass20 = (KeyguardUpdateMonitor.AnonymousClass20) obj;
                anonymousClass20.getClass();
                KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED);
                break;
            default:
                KeyguardUpdateMonitor.AnonymousClass20 anonymousClass202 = (KeyguardUpdateMonitor.AnonymousClass20) obj;
                anonymousClass202.getClass();
                KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED);
                break;
        }
    }
}
