package com.samsung.android.globalactions.features;

import android.hardware.usb.UsbManager;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes6.dex */
public class FrontDisplayStrategy implements SecureConfirmStrategy, DisposingStrategy {
    private static final String TAG = "FrontDisplayStrategy";
    private static SemWindowManager.FoldStateListener sFoldStateListener;
    private final SamsungGlobalActions mGlobalActions;
    private final KeyGuardManagerWrapper mKeyguardManagerWrapper;
    private final LogWrapper mLogWrapper;

    public FrontDisplayStrategy(SamsungGlobalActions samsungGlobalActions, LogWrapper logWrapper, KeyGuardManagerWrapper keyGuardManagerWrapper) {
        this.mGlobalActions = samsungGlobalActions;
        this.mLogWrapper = logWrapper;
        this.mKeyguardManagerWrapper = keyGuardManagerWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean doActionBeforeSecureConfirm(final ActionViewModel actionViewModel, SamsungGlobalActions samsungGlobalActions) {
        String name = actionViewModel.getActionInfo().getName();
        name.hashCode();
        if (name.equals("power") || name.equals(DefaultActionNames.ACTION_RESTART)) {
            final String str = actionViewModel.getActionInfo().getName() == "power" ? UsbManager.USB_FUNCTION_SHUTDOWN : "reboot";
            if (isFoldedState()) {
                sFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.globalactions.features.FrontDisplayStrategy.1
                    @Override // com.samsung.android.view.SemWindowManager.FoldStateListener
                    public void onTableModeChanged(boolean z) {
                    }

                    @Override // com.samsung.android.view.SemWindowManager.FoldStateListener
                    public void onFoldStateChanged(boolean z) {
                        if (z) {
                            return;
                        }
                        FrontDisplayStrategy.this.mLogWrapper.i(FrontDisplayStrategy.TAG, "registerSecureConfirm by FoldStateChangedListener");
                        FrontDisplayStrategy.this.mKeyguardManagerWrapper.setRegisterState(false);
                        FrontDisplayStrategy.this.mGlobalActions.registerSecureConfirmAction(actionViewModel);
                        FrontDisplayStrategy.this.mKeyguardManagerWrapper.setPendingIntentAfterUnlock(str);
                    }
                };
                SemWindowManager.getInstance().registerFoldStateListener(sFoldStateListener, null);
                this.mKeyguardManagerWrapper.setRegisterState(true);
            }
        }
        return true;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.DisposingStrategy
    public void onDispose() {
        this.mKeyguardManagerWrapper.setRegisterState(false);
        if (sFoldStateListener != null) {
            this.mLogWrapper.i(TAG, "unregisterFoldStateListener");
            SemWindowManager.getInstance().unregisterFoldStateListener(sFoldStateListener);
        }
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean isFoldedState() {
        return SemWindowManager.getInstance().isFolded();
    }
}
