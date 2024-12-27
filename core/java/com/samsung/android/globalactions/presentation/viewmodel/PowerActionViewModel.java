package com.samsung.android.globalactions.presentation.viewmodel;

import android.hardware.usb.UsbManager;

import com.android.internal.R;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.UsageStatsWrapper;

import java.util.List;

public class PowerActionViewModel implements ActionViewModel {
    private final ConditionChecker mConditionChecker;
    private String mExtraInfo;
    private final FeatureFactory mFeatureFactory;
    private final SamsungGlobalActions mGlobalActions;
    ActionInfo mInfo;
    private final KeyGuardManagerWrapper mKeyguardManagerWrapper;
    private final ResourcesWrapper mResourcesWrapper;
    private final SamsungGlobalActionsAnalytics mSAnalytics;
    private final ToastController mToastController;
    private final UsageStatsWrapper mUsageStatsWrapper;
    private final SamsungGlobalActionsManager mWindowManagerFuncs;
    private boolean mIsRMMLocked = false;
    private boolean mIsSIMLocked = false;
    private boolean mIsSecureKeyguard = false;
    private boolean mIsLockNetworkAndSecurity = false;
    private boolean mIsCalledFromSecureLock = false;

    public PowerActionViewModel(
            SamsungGlobalActions globalActions,
            ConditionChecker conditionChecker,
            SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics,
            SamsungGlobalActionsManager windowManagerFuncs,
            FeatureFactory featureFactory,
            ToastController toastController,
            KeyGuardManagerWrapper keyguardManagerWrapper,
            ResourcesWrapper resourcesWrapper,
            UsageStatsWrapper usageStatsWrapper) {
        this.mGlobalActions = globalActions;
        this.mConditionChecker = conditionChecker;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mWindowManagerFuncs = windowManagerFuncs;
        this.mFeatureFactory = featureFactory;
        this.mToastController = toastController;
        this.mKeyguardManagerWrapper = keyguardManagerWrapper;
        this.mResourcesWrapper = resourcesWrapper;
        this.mUsageStatsWrapper = usageStatsWrapper;
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
        boolean needSecureConfirm;
        int res;
        int res2;
        List<ActionInteractionStrategy> strategies =
                this.mFeatureFactory.createActionInteractionStrategies(this.mInfo.getName());
        for (ActionInteractionStrategy strategy : strategies) {
            if (strategy.onPressPowerAction()) {
                return;
            }
        }
        if (!this.mGlobalActions.isActionConfirming()) {
            if (this.mConditionChecker.isEnabled(
                            SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER)
                    && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
                this.mSAnalytics.sendEventLog(
                        SamsungGlobalActionsAnalytics.SID_FRONT_COVER_DEVICE_OPTIONS,
                        SamsungGlobalActionsAnalytics.EID_FRONT_COVER_POWER_OFF);
            }
            this.mGlobalActions.confirmAction(this);
            return;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED)) {
            if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                res2 = R.string.globalactions_unable_power_off_msg_fmm_tablet;
            } else {
                res2 = R.string.globalactions_unable_power_off_msg_fmm;
            }
            this.mToastController.showToast(this.mResourcesWrapper.getString(res2), 1);
            return;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK)) {
            if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                res = R.string.globalactions_unable_power_off_msg_sim_card_locked_tablet;
            } else {
                res = R.string.globalactions_unable_power_off_msg_sim_card_locked;
            }
            this.mToastController.showToast(this.mResourcesWrapper.getString(res), 1);
            return;
        }
        List<SecureConfirmStrategy> secureConfirmStrategies =
                this.mFeatureFactory.createSecureConfirmStrategy(
                        this.mGlobalActions, this.mInfo.getName());
        boolean hasCondition = false;
        for (SecureConfirmStrategy strategy2 : secureConfirmStrategies) {
            hasCondition |= strategy2.hasSecureConfirmCondition();
        }
        if (hasCondition) {
            needSecureConfirm = true;
            for (SecureConfirmStrategy strategy3 : secureConfirmStrategies) {
                needSecureConfirm &= strategy3.isNeedSecureConfirm();
            }
        } else {
            needSecureConfirm = isNeedSecureConfirm();
        }
        if (needSecureConfirm) {
            boolean isNeedToRegister = true;
            for (SecureConfirmStrategy strategy4 : secureConfirmStrategies) {
                isNeedToRegister &=
                        strategy4.doActionBeforeSecureConfirm(this, this.mGlobalActions);
            }
            if (isNeedToRegister) {
                this.mGlobalActions.registerSecureConfirmAction(this);
                this.mKeyguardManagerWrapper.setPendingIntentAfterUnlock(
                        UsbManager.USB_FUNCTION_SHUTDOWN);
                this.mGlobalActions.hideDialogOnSecureConfirm();
                if (this.mConditionChecker.isEnabled(
                                SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER)
                        && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
                    this.mSAnalytics.sendEventLog(
                            SamsungGlobalActionsAnalytics.SID_FRONT_COVER_DEVICE_OPTIONS,
                            SamsungGlobalActionsAnalytics.EID_FRONT_COVER_SECURE_LOCK_NOTI);
                    return;
                }
                return;
            }
        }
        shutdown();
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onLongPress() {
        List<ActionInteractionStrategy> strategies =
                this.mFeatureFactory.createActionInteractionStrategies(this.mInfo.getName());
        for (ActionInteractionStrategy strategy : strategies) {
            if (strategy.onLongPressPowerAction()) {
                return;
            }
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_ALLOWED_SAFE_BOOT)) {
            int index = getActionInfo().getViewIndex();
            this.mGlobalActions.confirmSafeMode(index);
        }
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public boolean showBeforeProvisioning() {
        return true;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPressSecureConfirm() {
        this.mIsCalledFromSecureLock = true;
        shutdown();
    }

    private void shutdown() {
        List<WindowManagerFunctionStrategy> strategies =
                this.mFeatureFactory.createWindowManagerFunctionStrategy(
                        this.mGlobalActions, WindowManagerFunctionStrategy.SHUTDOWN);
        for (WindowManagerFunctionStrategy strategy : strategies) {
            strategy.onShutdown();
        }
        this.mExtraInfo = "";
        this.mExtraInfo += (this.mIsRMMLocked ? "(RMM" : "(rmm");
        this.mExtraInfo += (this.mIsSIMLocked ? " SIM" : " sim");
        this.mExtraInfo += (this.mIsSecureKeyguard ? " SECURE" : " secure");
        this.mExtraInfo += (this.mIsLockNetworkAndSecurity ? " NAS" : " nas");
        this.mExtraInfo += (this.mIsCalledFromSecureLock ? " LOCK)" : " lock)");
        this.mSAnalytics.sendEventLog(
                SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS,
                SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS,
                SamsungGlobalActionsAnalytics.DID_POWER_OFF,
                1L);
        this.mWindowManagerFuncs.shutdown();
    }

    private boolean isNeedSecureConfirm() {
        this.mIsRMMLocked = this.mConditionChecker.isEnabled(SystemConditions.IS_RMM_LOCKED);
        this.mIsSIMLocked = this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK);
        this.mIsSecureKeyguard =
                this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD);
        this.mIsLockNetworkAndSecurity =
                this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_NETWORK_AND_SECURITY);
        return !this.mIsRMMLocked
                && !this.mIsSIMLocked
                && this.mIsSecureKeyguard
                && this.mIsLockNetworkAndSecurity;
    }
}
