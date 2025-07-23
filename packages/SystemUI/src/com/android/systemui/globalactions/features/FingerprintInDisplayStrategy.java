package com.android.systemui.globalactions.features;

import com.android.systemui.globalactions.util.KeyguardUpdateMonitorWrapper;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FingerprintInDisplayStrategy implements ActionsCreationStrategy, DisposingStrategy, SecureConfirmStrategy {
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
