package com.samsung.android.globalactions.presentation.viewmodel;

import android.content.Context;
import com.android.internal.R;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsManager;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.util.AlertDialogFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.LockPatternUtilsWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.SystemPropertiesWrapper;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.UsageStatsWrapper;
import com.samsung.android.globalactions.util.UtilFactory;

/* loaded from: classes6.dex */
public class DefaultActionViewModelFactory implements ActionViewModelFactory {
    private final ConditionChecker mConditionChecker;
    FeatureFactory mFeatureFactory;
    private final ResourceFactory mResourceFactory;
    private final SamsungGlobalActionsAnalytics mSAnalytics;
    UtilFactory mUtilFactory;

    public DefaultActionViewModelFactory(UtilFactory utilFactory, ResourceFactory resourceFactory, ConditionChecker conditionChecker, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
        this.mUtilFactory = utilFactory;
        this.mResourceFactory = resourceFactory;
        this.mConditionChecker = conditionChecker;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
    }

    public void setFeatureFactory(FeatureFactory featureFactory) {
        this.mFeatureFactory = featureFactory;
    }

    public String getResString(int i) {
        return ((Context) this.mUtilFactory.get(Context.class)).getResources().getString(i);
    }

    public String getResString(int i, int i2) {
        return ((Context) this.mUtilFactory.get(Context.class)).getResources().getString(i, Integer.valueOf(((Context) this.mUtilFactory.get(Context.class)).getResources().getInteger(i2)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory
    public ActionViewModel createActionViewModel(SamsungGlobalActions samsungGlobalActions, String str) {
        char c;
        ActionInfo actionInfo = new ActionInfo();
        boolean isEnabled = this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE);
        str.hashCode();
        switch (str.hashCode()) {
            case -1250803636:
                if (str.equals(DefaultActionNames.ACTION_EMERGENCY_CALL)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1131224299:
                if (str.equals(DefaultActionNames.ACTION_SAFE_MODE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1097329270:
                if (str.equals(DefaultActionNames.ACTION_LOGOUT)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -562960116:
                if (str.equals(DefaultActionNames.ACTION_LOCKDOWN_MODE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -363578088:
                if (str.equals(DefaultActionNames.ACTION_DATA_MODE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 106858757:
                if (str.equals("power")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 916977052:
                if (str.equals(DefaultActionNames.ACTION_MEDICAL_INFO)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1097506319:
                if (str.equals(DefaultActionNames.ACTION_RESTART)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1629013393:
                if (str.equals("emergency")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 2092525919:
                if (str.equals("bug_report")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 2146922627:
                if (str.equals(DefaultActionNames.ACTION_FORCE_RESTART_MESSAGE)) {
                    c = '\n';
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
                EmergencyCallActionViewModel emergencyCallActionViewModel = new EmergencyCallActionViewModel((Context) this.mUtilFactory.get(Context.class), samsungGlobalActions, this.mSAnalytics, (SystemController) this.mUtilFactory.get(SystemController.class));
                actionInfo.setName(DefaultActionNames.ACTION_EMERGENCY_CALL);
                actionInfo.setLabel(getResString(R.string.global_action_emergency_call));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_EMERGENCY_CALL));
                actionInfo.setViewType(ViewType.CENTER_ICON_4P_VIEW);
                emergencyCallActionViewModel.setActionInfo(actionInfo);
                return emergencyCallActionViewModel;
            case 1:
                SafeModeActionViewModel safeModeActionViewModel = new SafeModeActionViewModel(samsungGlobalActions, (SamsungGlobalActionsManager) this.mUtilFactory.get(SamsungGlobalActionsManager.class), this.mConditionChecker, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), this.mSAnalytics);
                actionInfo.setName(DefaultActionNames.ACTION_SAFE_MODE);
                actionInfo.setLabel(getResString(R.string.global_action_safemode));
                actionInfo.setDescription(getResString(isEnabled ? R.string.global_action_confirm_msg_safemode_tablet : R.string.global_action_confirm_msg_safemode));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_SAFEMODE));
                actionInfo.setViewType(ViewType.CENTER_ICON_1P_VIEW);
                safeModeActionViewModel.setActionInfo(actionInfo);
                return safeModeActionViewModel;
            case 2:
                LogoutActionViewModel logoutActionViewModel = new LogoutActionViewModel(samsungGlobalActions, (HandlerUtil) this.mUtilFactory.get(HandlerUtil.class), (LogWrapper) this.mUtilFactory.get(LogWrapper.class));
                actionInfo.setName(DefaultActionNames.ACTION_LOGOUT);
                actionInfo.setLabel(getResString(R.string.global_action_logout));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_ENDSESSION));
                actionInfo.setViewType(ViewType.CENTER_ICON_3P_VIEW);
                logoutActionViewModel.setActionInfo(actionInfo);
                return logoutActionViewModel;
            case 3:
                LockdownModeActionViewModel lockdownModeActionViewModel = new LockdownModeActionViewModel(this.mSAnalytics, (LockPatternUtilsWrapper) this.mUtilFactory.get(LockPatternUtilsWrapper.class), samsungGlobalActions);
                actionInfo.setName(DefaultActionNames.ACTION_LOCKDOWN_MODE);
                actionInfo.setLabel(getResString(R.string.global_action_lockdown_mode));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_LOCKDOWN));
                actionInfo.setViewType(ViewType.CENTER_ICON_5P_VIEW);
                lockdownModeActionViewModel.setActionInfo(actionInfo);
                return lockdownModeActionViewModel;
            case 4:
                DataModeActionViewModel dataModeActionViewModel = new DataModeActionViewModel(samsungGlobalActions, this.mConditionChecker, this.mSAnalytics, (SystemController) this.mUtilFactory.get(SystemController.class), (AlertDialogFactory) this.mUtilFactory.get(AlertDialogFactory.class), this.mFeatureFactory, (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class));
                actionInfo.setName(DefaultActionNames.ACTION_DATA_MODE);
                actionInfo.setLabel(getResString(R.string.global_action_toggle_data_mode));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_DATAMODE));
                actionInfo.setViewType(ViewType.CENTER_ICON_2P_VIEW);
                dataModeActionViewModel.setActionInfo(actionInfo);
                return dataModeActionViewModel;
            case 5:
                PowerActionViewModel powerActionViewModel = new PowerActionViewModel(samsungGlobalActions, this.mConditionChecker, this.mSAnalytics, (SamsungGlobalActionsManager) this.mUtilFactory.get(SamsungGlobalActionsManager.class), this.mFeatureFactory, (ToastController) this.mUtilFactory.get(ToastController.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), (UsageStatsWrapper) this.mUtilFactory.get(UsageStatsWrapper.class));
                actionInfo.setName("power");
                actionInfo.setLabel(getResString(R.string.samsung_global_action_power_off));
                actionInfo.setDescription(getResString(isEnabled ? R.string.global_action_confirm_msg_poweroff_tablet : R.string.global_action_confirm_msg_poweroff));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_POWEROFF));
                actionInfo.setViewType(ViewType.CENTER_ICON_1P_VIEW);
                powerActionViewModel.setActionInfo(actionInfo);
                return powerActionViewModel;
            case 6:
                MedicalInfoActionViewModel medicalInfoActionViewModel = new MedicalInfoActionViewModel((Context) this.mUtilFactory.get(Context.class), samsungGlobalActions, this.mSAnalytics, (SystemController) this.mUtilFactory.get(SystemController.class));
                actionInfo.setName(DefaultActionNames.ACTION_MEDICAL_INFO);
                actionInfo.setLabel(getResString(R.string.global_action_medical_info));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_MEDICAL_INFO));
                actionInfo.setViewType(ViewType.CENTER_ICON_5P_VIEW);
                medicalInfoActionViewModel.setActionInfo(actionInfo);
                return medicalInfoActionViewModel;
            case 7:
                RestartActionViewModel restartActionViewModel = new RestartActionViewModel(samsungGlobalActions, this.mConditionChecker, this.mSAnalytics, (SamsungGlobalActionsManager) this.mUtilFactory.get(SamsungGlobalActionsManager.class), this.mFeatureFactory, (ToastController) this.mUtilFactory.get(ToastController.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), (UsageStatsWrapper) this.mUtilFactory.get(UsageStatsWrapper.class));
                actionInfo.setName(DefaultActionNames.ACTION_RESTART);
                actionInfo.setLabel(getResString(R.string.samsung_global_action_restart));
                actionInfo.setDescription(getResString(isEnabled ? R.string.global_action_confirm_msg_restart_tablet : R.string.global_action_confirm_msg_restart));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_RESTART));
                actionInfo.setViewType(ViewType.CENTER_ICON_3P_VIEW);
                restartActionViewModel.setActionInfo(actionInfo);
                return restartActionViewModel;
            case '\b':
                EmergencyActionViewModel emergencyActionViewModel = new EmergencyActionViewModel(samsungGlobalActions, this.mConditionChecker, this.mSAnalytics, (SystemController) this.mUtilFactory.get(SystemController.class), this.mFeatureFactory, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class));
                actionInfo.setName("emergency");
                actionInfo.setLabel(getResString(R.string.global_action_toggle_emergency_mode));
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_EMERGENCY));
                actionInfo.setViewType(ViewType.CENTER_ICON_4P_VIEW);
                emergencyActionViewModel.setActionInfo(actionInfo);
                return emergencyActionViewModel;
            case '\t':
                BugReportActionViewModel bugReportActionViewModel = new BugReportActionViewModel(samsungGlobalActions, (SystemController) this.mUtilFactory.get(SystemController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class));
                actionInfo.setName("bug_report");
                actionInfo.setLabel(getResString(R.string.samsung_bugreport_title));
                actionInfo.setStateLabel(((SystemPropertiesWrapper) this.mUtilFactory.get(SystemPropertiesWrapper.class)).getBugReportStatus());
                actionInfo.setIcon(-1);
                actionInfo.setViewType(ViewType.BOTTOM_BTN_LIST_VIEW);
                bugReportActionViewModel.setActionInfo(actionInfo);
                return bugReportActionViewModel;
            case '\n':
                ForceRestartMessageActionViewModel forceRestartMessageActionViewModel = new ForceRestartMessageActionViewModel();
                actionInfo.setName(DefaultActionNames.ACTION_FORCE_RESTART_MESSAGE);
                actionInfo.setStateLabel(getResString(R.string.global_action_force_restart_message, this.mResourceFactory.get(ResourceType.INTEGER_FORCE_RESTART_TIME)));
                actionInfo.setIcon(-1);
                actionInfo.setViewType(ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW);
                forceRestartMessageActionViewModel.setActionInfo(actionInfo);
                return forceRestartMessageActionViewModel;
            default:
                return null;
        }
    }
}
