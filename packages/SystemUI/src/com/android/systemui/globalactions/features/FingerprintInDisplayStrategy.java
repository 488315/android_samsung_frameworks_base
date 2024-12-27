package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.KeyguardUpdateMonitorWrapper;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

public final class FingerprintInDisplayStrategy implements ActionsCreationStrategy, DisposingStrategy, SecureConfirmStrategy {
    public final KeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;

    public FingerprintInDisplayStrategy(KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper) {
        this.mKeyguardUpdateMonitorWrapper = keyguardUpdateMonitorWrapper;
    }

    public final boolean doActionBeforeSecureConfirm(ActionViewModel actionViewModel, SamsungGlobalActions samsungGlobalActions) {
        this.mKeyguardUpdateMonitorWrapper.getClass();
        return true;
    }

    public final void onCreateActions(SamsungGlobalActions samsungGlobalActions) {
        this.mKeyguardUpdateMonitorWrapper.getClass();
    }

    public final void onDispose() {
        this.mKeyguardUpdateMonitorWrapper.getClass();
    }
}
