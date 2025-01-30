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

/* loaded from: classes5.dex */
public class FrontLargeDisplayStrategy implements SecureConfirmStrategy, DisposingStrategy {
    private static final String TAG = "FrontLargeDisplayStrategy";
    private static SemWindowManager.FoldStateListener sFoldStateListener;
    private final SamsungGlobalActions mGlobalActions;
    private final KeyGuardManagerWrapper mKeyguardManagerWrapper;
    private final LogWrapper mLogWrapper;

    public FrontLargeDisplayStrategy(SamsungGlobalActions globalActions, LogWrapper logWrapper, KeyGuardManagerWrapper keyguardManagerWrapper) {
        this.mGlobalActions = globalActions;
        this.mLogWrapper = logWrapper;
        this.mKeyguardManagerWrapper = keyguardManagerWrapper;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean doActionBeforeSecureConfirm(final ActionViewModel viewModel, SamsungGlobalActions globalActions) {
        char c;
        String name = viewModel.getActionInfo().getName();
        switch (name.hashCode()) {
            case 106858757:
                if (name.equals("power")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1097506319:
                if (name.equals(DefaultActionNames.ACTION_RESTART)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                final String extraUnlockType = viewModel.getActionInfo().getName() == "power" ? UsbManager.USB_FUNCTION_SHUTDOWN : "reboot";
                if (isFoldedState()) {
                    sFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.globalactions.features.FrontLargeDisplayStrategy.1
                        @Override // com.samsung.android.view.SemWindowManager.FoldStateListener
                        public void onFoldStateChanged(boolean isFolded) {
                            if (!isFolded) {
                                FrontLargeDisplayStrategy.this.mLogWrapper.m265i(FrontLargeDisplayStrategy.TAG, "registerSecureConfirm by FoldStateChangedListener-->Large");
                                FrontLargeDisplayStrategy.this.mKeyguardManagerWrapper.setRegisterState(false);
                                FrontLargeDisplayStrategy.this.mGlobalActions.registerSecureConfirmAction(viewModel);
                                FrontLargeDisplayStrategy.this.mKeyguardManagerWrapper.setPendingIntentAfterUnlockOnCover(extraUnlockType, false);
                                FrontLargeDisplayStrategy.this.mGlobalActions.dismissDialog(false);
                            }
                        }

                        @Override // com.samsung.android.view.SemWindowManager.FoldStateListener
                        public void onTableModeChanged(boolean b) {
                        }
                    };
                    this.mLogWrapper.m265i(TAG, "registerSecureConfirm by doActionBeforeSecureConfirm ViewModel");
                    this.mGlobalActions.registerSecureConfirmAction(viewModel);
                    this.mKeyguardManagerWrapper.setPendingIntentAfterUnlockOnCover(extraUnlockType, true);
                    this.mKeyguardManagerWrapper.setRegisterState(true);
                    this.mGlobalActions.dismissDialog(false);
                }
            default:
                return true;
        }
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.DisposingStrategy
    public void onDispose() {
        this.mKeyguardManagerWrapper.setRegisterState(false);
        if (sFoldStateListener != null) {
            this.mLogWrapper.m265i(TAG, "unregisterFoldStateListener");
            SemWindowManager.getInstance().unregisterFoldStateListener(sFoldStateListener);
        }
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy
    public boolean isFoldedState() {
        return SemWindowManager.getInstance().isFolded();
    }
}
