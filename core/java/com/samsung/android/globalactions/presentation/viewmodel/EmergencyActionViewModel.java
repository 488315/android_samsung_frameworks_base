package com.samsung.android.globalactions.presentation.viewmodel;

import com.android.internal.R;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.ToastController;

import java.util.List;

/* loaded from: classes6.dex */
public class EmergencyActionViewModel implements ActionViewModel {
    private final ConditionChecker mConditionChecker;
    private final FeatureFactory mFeatureFactory;
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
    private final KeyGuardManagerWrapper mKeyguardManagerWrapper;
    private final ResourcesWrapper mResourcesWrapper;
    private final SamsungGlobalActionsAnalytics mSAnalytics;
    private final SystemController mSystemController;
    private final ToastController mToastController;
    private ActionViewModel.ToggleState mToggleState;

    public EmergencyActionViewModel(
            SamsungGlobalActions globalActions,
            ConditionChecker conditionChecker,
            SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics,
            SystemController systemController,
            FeatureFactory featureFactory,
            KeyGuardManagerWrapper keyGuardManagerWrapper,
            ToastController toastController,
            ResourcesWrapper resourcesWrapper) {
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mSystemController = systemController;
        this.mConditionChecker = conditionChecker;
        this.mFeatureFactory = featureFactory;
        this.mKeyguardManagerWrapper = keyGuardManagerWrapper;
        this.mGlobalActions = globalActions;
        this.mToastController = toastController;
        this.mResourcesWrapper = resourcesWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo info) {
        this.mInfo = info;
        setStateLabel();
    }

    public void setStateLabel() {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_EMERGENCY_MODE)) {
            this.mToggleState = ActionViewModel.ToggleState.on;
            this.mInfo.setStateLabel(
                    this.mResourcesWrapper.getString(
                            R.string.global_action_emergency_mode_on_status));
        } else {
            this.mToggleState = ActionViewModel.ToggleState.off;
            this.mInfo.setStateLabel(
                    this.mResourcesWrapper.getString(
                            R.string.global_action_emergency_mode_off_status));
        }
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setState(ActionViewModel.ToggleState toggleState) {
        this.mToggleState = toggleState;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionViewModel.ToggleState getState() {
        return this.mToggleState;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        int res;
        List<ActionInteractionStrategy> strategies =
                this.mFeatureFactory.createActionInteractionStrategies(this.mInfo.getName());
        for (ActionInteractionStrategy strategy : strategies) {
            if (strategy.onPressEmergencyModeAction()) {
                return;
            }
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED)) {
            if (this.mToggleState == ActionViewModel.ToggleState.off) {
                if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                    res = R.string.globalactions_unable_emergency_msg_fmm_tablet;
                } else {
                    res = R.string.globalactions_unable_emergency_msg_fmm;
                }
            } else if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                res = R.string.globalactions_unable_emergency_off_msg_fmm_tablet;
            } else {
                res = R.string.globalactions_unable_emergency_off_msg_fmm;
            }
            this.mToastController.showToast(this.mResourcesWrapper.getString(res), 1);
            return;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_BIKE_MODE)
                && !this.mConditionChecker.isEnabled(
                        SystemConditions.IS_EMERGENCY_MODE_USER_AGREEMENT)) {
            String emergencyModeText =
                    this.mResourcesWrapper.getString(R.string.global_action_toggle_emergency_mode);
            String sBikeModeText =
                    this.mResourcesWrapper.getString(R.string.globalactions_bikemode);
            this.mToastController.showToast(
                    this.mResourcesWrapper.getString(
                            R.string.globalactions_unable_emergency_mode_msg_in_bike_mode,
                            emergencyModeText,
                            sBikeModeText),
                    1);
        } else {
            if (isNeedSecureConfirm()) {
                List<SecureConfirmStrategy> secureConfirmStrategies =
                        this.mFeatureFactory.createSecureConfirmStrategy(
                                this.mGlobalActions, this.mInfo.getName());
                for (SecureConfirmStrategy strategy2 : secureConfirmStrategies) {
                    strategy2.doActionBeforeSecureConfirm(this, this.mGlobalActions);
                }
                this.mGlobalActions.registerSecureConfirmAction(this);
                this.mKeyguardManagerWrapper.setPendingIntentAfterUnlock("emergencymode");
                this.mGlobalActions.hideDialogOnSecureConfirm();
                return;
            }
            this.mGlobalActions.dismissDialog(true);
            this.mSAnalytics.sendEventLog(
                    SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS,
                    SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS,
                    SamsungGlobalActionsAnalytics.DID_EMERGENCY_MODE,
                    3L);
            this.mSystemController.toggleEmergencyMode();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPressSecureConfirm() {
        this.mGlobalActions.dismissDialog(false);
        this.mSAnalytics.sendEventLog(
                SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS,
                SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS,
                SamsungGlobalActionsAnalytics.DID_EMERGENCY_MODE,
                3L);
        this.mSystemController.toggleEmergencyMode();
    }

    private boolean isNeedSecureConfirm() {
        return !this.mConditionChecker.isEnabled(SystemConditions.IS_RMM_LOCKED)
                && !this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK)
                && this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD)
                && this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_NETWORK_AND_SECURITY);
    }
}
