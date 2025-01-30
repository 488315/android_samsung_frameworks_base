package com.samsung.android.globalactions.presentation.viewmodel;

import android.hardware.usb.UsbManager;
import com.android.internal.C4337R;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;

/* loaded from: classes5.dex */
public class SafeModeActionViewModel implements ActionViewModel {
    private final ConditionChecker mConditionChecker;
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
    private final KeyGuardManagerWrapper mKeyguardManagerWrapper;
    private final ResourcesWrapper mResourcesWrapper;
    private final SamsungGlobalActionsAnalytics mSAnalytics;
    private final ToastController mToastController;
    private final SamsungGlobalActionsManager mWindowManagerFuncs;

    public SafeModeActionViewModel(SamsungGlobalActions samsungGlobalActions, SamsungGlobalActionsManager windowManagerFuncs, ConditionChecker conditionChecker, KeyGuardManagerWrapper keyGuardManagerWrapper, ResourcesWrapper resourcesWrapper, ToastController toastController, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
        this.mGlobalActions = samsungGlobalActions;
        this.mWindowManagerFuncs = windowManagerFuncs;
        this.mConditionChecker = conditionChecker;
        this.mKeyguardManagerWrapper = keyGuardManagerWrapper;
        this.mResourcesWrapper = resourcesWrapper;
        this.mToastController = toastController;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo info) {
        this.mInfo = info;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED)) {
            this.mToastController.showToast(this.mResourcesWrapper.getString(C4337R.string.globalactions_unable_restart_msg_fmm), 1);
        } else {
            if (isNeedSecureConfirm()) {
                this.mGlobalActions.registerSecureConfirmAction(this);
                this.mKeyguardManagerWrapper.setPendingIntentAfterUnlock(UsbManager.USB_FUNCTION_SHUTDOWN);
                this.mGlobalActions.hideDialogOnSecureConfirm();
                return;
            }
            safeMode();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPressSecureConfirm() {
        safeMode();
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public boolean showBeforeProvisioning() {
        return true;
    }

    public void safeMode() {
        this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_SAFE_MODE, SamsungGlobalActionsAnalytics.EID_SAFE_MODE);
        this.mWindowManagerFuncs.reboot(true);
    }

    private boolean isNeedSecureConfirm() {
        return !this.mConditionChecker.isEnabled(SystemConditions.IS_RMM_LOCKED) && !this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK) && this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD) && this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_NETWORK_AND_SECURITY) && this.mConditionChecker.isEnabled(SystemConditions.IS_ENCRYPTION_STATUS_ACTIVE);
    }
}
