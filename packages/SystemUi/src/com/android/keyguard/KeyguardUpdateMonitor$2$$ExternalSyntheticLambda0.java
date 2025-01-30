package com.android.keyguard;

import com.android.keyguard.KeyguardUpdateMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitor.C08052) this.f$0).this$0.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD);
                break;
            case 1:
                KeyguardUpdateMonitor.C080318 c080318 = (KeyguardUpdateMonitor.C080318) this.f$0;
                c080318.getClass();
                c080318.this$0.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED);
                break;
            default:
                KeyguardUpdateMonitor.C080318 c0803182 = (KeyguardUpdateMonitor.C080318) this.f$0;
                c0803182.getClass();
                c0803182.this$0.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED);
                break;
        }
    }
}
