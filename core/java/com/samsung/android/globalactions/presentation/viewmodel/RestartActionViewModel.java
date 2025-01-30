package com.samsung.android.globalactions.presentation.viewmodel;

import com.android.internal.C4337R;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.strategies.SoftwareUpdateStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.UsageStatsWrapper;
import java.util.List;

/* loaded from: classes5.dex */
public class RestartActionViewModel implements ActionViewModel {
    private final ConditionChecker mConditionChecker;
    private String mExtraInfo;
    private final FeatureFactory mFeatureFactory;
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
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
    private boolean mIsEncyptionStatusActive = false;
    private boolean mIsCalledFromSecureLock = false;

    public RestartActionViewModel(SamsungGlobalActions globalActions, ConditionChecker conditionChecker, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics, SamsungGlobalActionsManager windowManagerFuncs, FeatureFactory featureFactory, ToastController toastController, KeyGuardManagerWrapper keyguardManagerWrapper, ResourcesWrapper resourceWrapper, UsageStatsWrapper usageStatsWrapper) {
        this.mGlobalActions = globalActions;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mWindowManagerFuncs = windowManagerFuncs;
        this.mConditionChecker = conditionChecker;
        this.mFeatureFactory = featureFactory;
        this.mToastController = toastController;
        this.mKeyguardManagerWrapper = keyguardManagerWrapper;
        this.mResourcesWrapper = resourceWrapper;
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
        List<ActionInteractionStrategy> strategies = this.mFeatureFactory.createActionInteractionStrategies(this.mInfo.getName());
        for (ActionInteractionStrategy strategy : strategies) {
            if (strategy.onPressRestartAction()) {
                return;
            }
        }
        if (!this.mGlobalActions.isActionConfirming()) {
            if (this.mConditionChecker.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
                this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_FRONT_COVER_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_FRONT_COVER_RESTART);
            }
            this.mGlobalActions.confirmAction(this);
            return;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED)) {
            if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                res = C4337R.string.globalactions_unable_restart_msg_fmm_tablet;
            } else {
                res = C4337R.string.globalactions_unable_restart_msg_fmm;
            }
            this.mToastController.showToast(this.mResourcesWrapper.getString(res), 1);
            return;
        }
        List<SoftwareUpdateStrategy> updateStrategies = this.mFeatureFactory.createSoftwareUpdateStrategy(this.mGlobalActions, DefaultActionNames.ACTION_RESTART);
        for (SoftwareUpdateStrategy strategy2 : updateStrategies) {
            if (strategy2.onUpdate()) {
                strategy2.update();
                this.mGlobalActions.dismissDialog(false);
                return;
            }
        }
        List<SecureConfirmStrategy> secureConfirmStrategies = this.mFeatureFactory.createSecureConfirmStrategy(this.mGlobalActions, this.mInfo.getName());
        boolean hasCondition = false;
        for (SecureConfirmStrategy strategy3 : secureConfirmStrategies) {
            hasCondition |= strategy3.hasSecureConfirmCondition();
        }
        if (hasCondition) {
            needSecureConfirm = true;
            for (SecureConfirmStrategy strategy4 : secureConfirmStrategies) {
                needSecureConfirm &= strategy4.isNeedSecureConfirm();
            }
        } else {
            needSecureConfirm = isNeedSecureConfirm();
        }
        if (needSecureConfirm) {
            boolean isNeedToRegister = true;
            for (SecureConfirmStrategy strategy5 : secureConfirmStrategies) {
                isNeedToRegister &= strategy5.doActionBeforeSecureConfirm(this, this.mGlobalActions);
            }
            if (isNeedToRegister) {
                this.mGlobalActions.registerSecureConfirmAction(this);
                this.mKeyguardManagerWrapper.setPendingIntentAfterUnlock("reboot");
                this.mGlobalActions.hideDialogOnSecureConfirm();
                if (this.mConditionChecker.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
                    this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_FRONT_COVER_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_FRONT_COVER_SECURE_LOCK_NOTI);
                    return;
                }
                return;
            }
        }
        reboot();
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public boolean showBeforeProvisioning() {
        return true;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPressSecureConfirm() {
        this.mIsCalledFromSecureLock = true;
        reboot();
    }

    public void reboot() {
        List<WindowManagerFunctionStrategy> strategies = this.mFeatureFactory.createWindowManagerFunctionStrategy(this.mGlobalActions, WindowManagerFunctionStrategy.REBOOT);
        for (WindowManagerFunctionStrategy strategy : strategies) {
            strategy.onReboot();
        }
        this.mExtraInfo = "";
        this.mExtraInfo += (this.mIsRMMLocked ? "(RMM" : "(rmm");
        this.mExtraInfo += (this.mIsSIMLocked ? " SIM" : " sim");
        this.mExtraInfo += (this.mIsSecureKeyguard ? " SECURE" : " secure");
        this.mExtraInfo += (this.mIsLockNetworkAndSecurity ? " NAS" : " nas");
        this.mExtraInfo += (this.mIsEncyptionStatusActive ? " ENCYP" : " encyp");
        this.mExtraInfo += (this.mIsCalledFromSecureLock ? " LOCK)" : " lock)");
        this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.DID_RESTART, 2L);
        this.mWindowManagerFuncs.reboot(false);
    }

    private boolean isNeedSecureConfirm() {
        this.mIsRMMLocked = this.mConditionChecker.isEnabled(SystemConditions.IS_RMM_LOCKED);
        this.mIsSIMLocked = this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK);
        this.mIsSecureKeyguard = this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD);
        this.mIsLockNetworkAndSecurity = this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_NETWORK_AND_SECURITY);
        boolean isEnabled = this.mConditionChecker.isEnabled(SystemConditions.IS_ENCRYPTION_STATUS_ACTIVE);
        this.mIsEncyptionStatusActive = isEnabled;
        return !this.mIsRMMLocked && !this.mIsSIMLocked && this.mIsSecureKeyguard && this.mIsLockNetworkAndSecurity && isEnabled;
    }
}
