package com.android.systemui.doze;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DozeAuthRemover implements DozeMachine.Part {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SelectedUserInteractor mSelectedUserInteractor;

    public DozeAuthRemover(KeyguardUpdateMonitor keyguardUpdateMonitor, SelectedUserInteractor selectedUserInteractor) {
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (state2 == DozeMachine.State.DOZE || state2 == DozeMachine.State.DOZE_AOD) {
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(selectedUserId)) {
                keyguardUpdateMonitor.clearFingerprintRecognized();
            }
        }
    }
}
