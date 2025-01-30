package com.android.systemui.doze;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.doze.DozeMachine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeAuthRemover implements DozeMachine.Part {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public DozeAuthRemover(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (state2 == DozeMachine.State.DOZE || state2 == DozeMachine.State.DOZE_AOD) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(currentUser)) {
                keyguardUpdateMonitor.clearBiometricRecognized();
            }
        }
    }
}
