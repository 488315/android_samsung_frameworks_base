package com.android.systemui.doze;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

public final class DozeAuthRemover implements DozeMachine.Part {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SelectedUserInteractor mSelectedUserInteractor;

    public DozeAuthRemover(KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor) {
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (state2 == DozeMachine.State.DOZE || state2 == DozeMachine.State.DOZE_AOD) {
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(selectedUserId)) {
                keyguardUpdateMonitor.clearFingerprintRecognized();
            }
        }
    }
}
