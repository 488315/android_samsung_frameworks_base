package com.samsung.android.globalactions.presentation.viewmodel;

import com.android.internal.R;
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
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
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
    private boolean mIsKnoxKeyGuardLocked = false;
    private boolean mIsSIMLocked = false;
    private boolean mIsSecureKeyguard = false;
    private boolean mIsLockNetworkAndSecurity = false;
    private boolean mIsEncyptionStatusActive = false;
    private boolean mIsCalledFromSecureLock = false;

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public boolean showBeforeProvisioning() {
        return true;
    }

    public RestartActionViewModel(SamsungGlobalActions samsungGlobalActions, ConditionChecker conditionChecker, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics, SamsungGlobalActionsManager samsungGlobalActionsManager, FeatureFactory featureFactory, ToastController toastController, KeyGuardManagerWrapper keyGuardManagerWrapper, ResourcesWrapper resourcesWrapper, UsageStatsWrapper usageStatsWrapper) {
        this.mGlobalActions = samsungGlobalActions;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
        this.mWindowManagerFuncs = samsungGlobalActionsManager;
        this.mConditionChecker = conditionChecker;
        this.mFeatureFactory = featureFactory;
        this.mToastController = toastController;
        this.mKeyguardManagerWrapper = keyGuardManagerWrapper;
        this.mResourcesWrapper = resourcesWrapper;
        this.mUsageStatsWrapper = usageStatsWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        boolean zIsNeedSecureConfirm;
        SoftwareUpdateStrategy next;
        Iterator<ActionInteractionStrategy> it = this.mFeatureFactory.createActionInteractionStrategies(this.mInfo.getName()).iterator();
        while (it.hasNext()) {
            if (it.next().onPressRestartAction()) {
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
        boolean zDoActionBeforeSecureConfirm = true;
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED)) {
            this.mToastController.showToast(this.mResourcesWrapper.getString(this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE) ? R.string.globalactions_unable_restart_msg_fmm_tablet : R.string.globalactions_unable_restart_msg_fmm), 1);
            return;
        }
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK)) {
            this.mToastController.showToast(this.mResourcesWrapper.getString(this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE) ? R.string.globalactions_unable_restart_msg_sim_card_locked_tablet : R.string.globalactions_unable_restart_msg_sim_card_locked), 1);
            return;
        }
        Iterator<SoftwareUpdateStrategy> it2 = this.mFeatureFactory.createSoftwareUpdateStrategy(this.mGlobalActions, DefaultActionNames.ACTION_RESTART).iterator();
        do {
            boolean zHasSecureConfirmCondition = false;
            if (!it2.hasNext()) {
                List<SecureConfirmStrategy> listCreateSecureConfirmStrategy = this.mFeatureFactory.createSecureConfirmStrategy(this.mGlobalActions, this.mInfo.getName());
                Iterator<SecureConfirmStrategy> it3 = listCreateSecureConfirmStrategy.iterator();
                while (it3.hasNext()) {
                    zHasSecureConfirmCondition |= it3.next().hasSecureConfirmCondition();
                }
                if (zHasSecureConfirmCondition) {
                    Iterator<SecureConfirmStrategy> it4 = listCreateSecureConfirmStrategy.iterator();
                    zIsNeedSecureConfirm = true;
                    while (it4.hasNext()) {
                        zIsNeedSecureConfirm &= it4.next().isNeedSecureConfirm();
                    }
                } else {
                    zIsNeedSecureConfirm = isNeedSecureConfirm();
                }
                if (zIsNeedSecureConfirm) {
                    Iterator<SecureConfirmStrategy> it5 = listCreateSecureConfirmStrategy.iterator();
                    while (it5.hasNext()) {
                        zDoActionBeforeSecureConfirm &= it5.next().doActionBeforeSecureConfirm(this, this.mGlobalActions);
                    }
                    if (zDoActionBeforeSecureConfirm) {
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
                return;
            }
            next = it2.next();
        } while (!next.onUpdate());
        next.update();
        this.mGlobalActions.dismissDialog(false);
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPressSecureConfirm() {
        this.mIsCalledFromSecureLock = true;
        reboot();
    }

    public void reboot() {
        Iterator<WindowManagerFunctionStrategy> it = this.mFeatureFactory.createWindowManagerFunctionStrategy(this.mGlobalActions, WindowManagerFunctionStrategy.REBOOT).iterator();
        while (it.hasNext()) {
            it.next().onReboot();
        }
        this.mExtraInfo = "";
        StringBuilder sb = new StringBuilder();
        sb.append(this.mExtraInfo);
        sb.append(this.mIsKnoxKeyGuardLocked ? "(KNOX" : "(knox");
        this.mExtraInfo = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mExtraInfo);
        sb2.append(this.mIsSIMLocked ? " SIM" : " sim");
        this.mExtraInfo = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mExtraInfo);
        sb3.append(this.mIsSecureKeyguard ? " SECURE" : " secure");
        this.mExtraInfo = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.mExtraInfo);
        sb4.append(this.mIsLockNetworkAndSecurity ? " NAS" : " nas");
        this.mExtraInfo = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(this.mExtraInfo);
        sb5.append(this.mIsEncyptionStatusActive ? " ENCYP" : " encyp");
        this.mExtraInfo = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(this.mExtraInfo);
        sb6.append(this.mIsCalledFromSecureLock ? " LOCK)" : " lock)");
        this.mExtraInfo = sb6.toString();
        this.mSAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.DID_RESTART, 2L);
        this.mWindowManagerFuncs.reboot(false);
    }

    private boolean isNeedSecureConfirm() {
        this.mIsKnoxKeyGuardLocked = this.mConditionChecker.isEnabled(SystemConditions.IS_KNOXGUARD_LOCKED);
        this.mIsSIMLocked = this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK);
        this.mIsSecureKeyguard = this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD);
        this.mIsLockNetworkAndSecurity = this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_NETWORK_AND_SECURITY);
        boolean zIsEnabled = this.mConditionChecker.isEnabled(SystemConditions.IS_ENCRYPTION_STATUS_ACTIVE);
        this.mIsEncyptionStatusActive = zIsEnabled;
        return !this.mIsKnoxKeyGuardLocked && !this.mIsSIMLocked && this.mIsSecureKeyguard && this.mIsLockNetworkAndSecurity && zIsEnabled;
    }
}
