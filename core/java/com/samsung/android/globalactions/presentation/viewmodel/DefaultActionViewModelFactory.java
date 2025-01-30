package com.samsung.android.globalactions.presentation.viewmodel;

import android.content.Context;
import com.android.internal.C4337R;
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

/* loaded from: classes5.dex */
public class DefaultActionViewModelFactory implements ActionViewModelFactory {
  private final ConditionChecker mConditionChecker;
  FeatureFactory mFeatureFactory;
  private final ResourceFactory mResourceFactory;
  private final SamsungGlobalActionsAnalytics mSAnalytics;
  UtilFactory mUtilFactory;

  public DefaultActionViewModelFactory(
      UtilFactory defaultUtilFactory,
      ResourceFactory resourceFactory,
      ConditionChecker conditionChecker,
      SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
    this.mUtilFactory = defaultUtilFactory;
    this.mResourceFactory = resourceFactory;
    this.mConditionChecker = conditionChecker;
    this.mSAnalytics = samsungGlobalActionsAnalytics;
  }

  public void setFeatureFactory(FeatureFactory featureFactory) {
    this.mFeatureFactory = featureFactory;
  }

  public String getResString(int resID) {
    return ((Context) this.mUtilFactory.get(Context.class)).getResources().getString(resID);
  }

  public String getResString(int stringID, int intID) {
    int resInt = ((Context) this.mUtilFactory.get(Context.class)).getResources().getInteger(intID);
    return ((Context) this.mUtilFactory.get(Context.class))
        .getResources()
        .getString(stringID, Integer.valueOf(resInt));
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory
  public ActionViewModel createActionViewModel(SamsungGlobalActions globalActions, String type) {
    char c;
    ActionInfo actionInfo = new ActionInfo();
    boolean isTabletDevice = this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE);
    switch (type.hashCode()) {
      case -1250803636:
        if (type.equals(DefaultActionNames.ACTION_EMERGENCY_CALL)) {
          c = 3;
          break;
        }
        c = 65535;
        break;
      case -1131224299:
        if (type.equals(DefaultActionNames.ACTION_SAFE_MODE)) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case -1097329270:
        if (type.equals(DefaultActionNames.ACTION_LOGOUT)) {
          c = '\n';
          break;
        }
        c = 65535;
        break;
      case -562960116:
        if (type.equals(DefaultActionNames.ACTION_LOCKDOWN_MODE)) {
          c = '\t';
          break;
        }
        c = 65535;
        break;
      case -363578088:
        if (type.equals(DefaultActionNames.ACTION_DATA_MODE)) {
          c = '\b';
          break;
        }
        c = 65535;
        break;
      case 106858757:
        if (type.equals("power")) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      case 916977052:
        if (type.equals(DefaultActionNames.ACTION_MEDICAL_INFO)) {
          c = 4;
          break;
        }
        c = 65535;
        break;
      case 1097506319:
        if (type.equals(DefaultActionNames.ACTION_RESTART)) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case 1629013393:
        if (type.equals("emergency")) {
          c = 5;
          break;
        }
        c = 65535;
        break;
      case 2092525919:
        if (type.equals("bug_report")) {
          c = 6;
          break;
        }
        c = 65535;
        break;
      case 2146922627:
        if (type.equals(DefaultActionNames.ACTION_FORCE_RESTART_MESSAGE)) {
          c = 7;
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
        ActionViewModel viewModel =
            new PowerActionViewModel(
                globalActions,
                this.mConditionChecker,
                this.mSAnalytics,
                (SamsungGlobalActionsManager)
                    this.mUtilFactory.get(SamsungGlobalActionsManager.class),
                this.mFeatureFactory,
                (ToastController) this.mUtilFactory.get(ToastController.class),
                (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class),
                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class),
                (UsageStatsWrapper) this.mUtilFactory.get(UsageStatsWrapper.class));
        actionInfo.setName("power");
        actionInfo.setLabel(getResString(C4337R.string.samsung_global_action_power_off));
        int descriptionResID =
            isTabletDevice
                ? C4337R.string.global_action_confirm_msg_poweroff_tablet
                : C4337R.string.global_action_confirm_msg_poweroff;
        actionInfo.setDescription(getResString(descriptionResID));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_POWEROFF));
        actionInfo.setViewType(ViewType.CENTER_ICON_1P_VIEW);
        viewModel.setActionInfo(actionInfo);
        return viewModel;
      case 1:
        ActionViewModel viewModel2 =
            new SafeModeActionViewModel(
                globalActions,
                (SamsungGlobalActionsManager)
                    this.mUtilFactory.get(SamsungGlobalActionsManager.class),
                this.mConditionChecker,
                (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class),
                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class),
                (ToastController) this.mUtilFactory.get(ToastController.class),
                this.mSAnalytics);
        actionInfo.setName(DefaultActionNames.ACTION_SAFE_MODE);
        actionInfo.setLabel(getResString(C4337R.string.global_action_safemode));
        int descriptionResID2 =
            isTabletDevice
                ? C4337R.string.global_action_confirm_msg_safemode_tablet
                : C4337R.string.global_action_confirm_msg_safemode;
        actionInfo.setDescription(getResString(descriptionResID2));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_SAFEMODE));
        actionInfo.setViewType(ViewType.CENTER_ICON_1P_VIEW);
        viewModel2.setActionInfo(actionInfo);
        return viewModel2;
      case 2:
        ActionViewModel viewModel3 =
            new RestartActionViewModel(
                globalActions,
                this.mConditionChecker,
                this.mSAnalytics,
                (SamsungGlobalActionsManager)
                    this.mUtilFactory.get(SamsungGlobalActionsManager.class),
                this.mFeatureFactory,
                (ToastController) this.mUtilFactory.get(ToastController.class),
                (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class),
                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class),
                (UsageStatsWrapper) this.mUtilFactory.get(UsageStatsWrapper.class));
        actionInfo.setName(DefaultActionNames.ACTION_RESTART);
        actionInfo.setLabel(getResString(C4337R.string.samsung_global_action_restart));
        int descriptionResID3 =
            isTabletDevice
                ? C4337R.string.global_action_confirm_msg_restart_tablet
                : C4337R.string.global_action_confirm_msg_restart;
        actionInfo.setDescription(getResString(descriptionResID3));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_RESTART));
        actionInfo.setViewType(ViewType.CENTER_ICON_3P_VIEW);
        viewModel3.setActionInfo(actionInfo);
        return viewModel3;
      case 3:
        ActionViewModel viewModel4 =
            new EmergencyCallActionViewModel(
                (Context) this.mUtilFactory.get(Context.class),
                globalActions,
                this.mSAnalytics,
                (SystemController) this.mUtilFactory.get(SystemController.class));
        actionInfo.setName(DefaultActionNames.ACTION_EMERGENCY_CALL);
        actionInfo.setLabel(getResString(C4337R.string.global_action_emergency_call));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_EMERGENCY_CALL));
        actionInfo.setViewType(ViewType.CENTER_ICON_4P_VIEW);
        viewModel4.setActionInfo(actionInfo);
        return viewModel4;
      case 4:
        ActionViewModel viewModel5 =
            new MedicalInfoActionViewModel(
                (Context) this.mUtilFactory.get(Context.class),
                globalActions,
                this.mSAnalytics,
                (SystemController) this.mUtilFactory.get(SystemController.class));
        actionInfo.setName(DefaultActionNames.ACTION_MEDICAL_INFO);
        actionInfo.setLabel(getResString(C4337R.string.global_action_medical_info));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_MEDICAL_INFO));
        actionInfo.setViewType(ViewType.CENTER_ICON_5P_VIEW);
        viewModel5.setActionInfo(actionInfo);
        return viewModel5;
      case 5:
        ActionViewModel viewModel6 =
            new EmergencyActionViewModel(
                globalActions,
                this.mConditionChecker,
                this.mSAnalytics,
                (SystemController) this.mUtilFactory.get(SystemController.class),
                this.mFeatureFactory,
                (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class),
                (ToastController) this.mUtilFactory.get(ToastController.class),
                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class));
        actionInfo.setName("emergency");
        actionInfo.setLabel(getResString(C4337R.string.global_action_toggle_emergency_mode));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_EMERGENCY));
        actionInfo.setViewType(ViewType.CENTER_ICON_4P_VIEW);
        viewModel6.setActionInfo(actionInfo);
        return viewModel6;
      case 6:
        ActionViewModel viewModel7 =
            new BugReportActionViewModel(
                globalActions,
                (SystemController) this.mUtilFactory.get(SystemController.class),
                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class));
        actionInfo.setName("bug_report");
        actionInfo.setLabel(getResString(C4337R.string.samsung_bugreport_title));
        actionInfo.setStateLabel(
            ((SystemPropertiesWrapper) this.mUtilFactory.get(SystemPropertiesWrapper.class))
                .getBugReportStatus());
        actionInfo.setIcon(-1);
        actionInfo.setViewType(ViewType.BOTTOM_BTN_LIST_VIEW);
        viewModel7.setActionInfo(actionInfo);
        return viewModel7;
      case 7:
        ActionViewModel viewModel8 = new ForceRestartMessageActionViewModel();
        actionInfo.setName(DefaultActionNames.ACTION_FORCE_RESTART_MESSAGE);
        actionInfo.setStateLabel(
            getResString(
                C4337R.string.global_action_force_restart_message,
                this.mResourceFactory.get(ResourceType.INTEGER_FORCE_RESTART_TIME)));
        actionInfo.setIcon(-1);
        actionInfo.setViewType(ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW);
        viewModel8.setActionInfo(actionInfo);
        return viewModel8;
      case '\b':
        ActionViewModel viewModel9 =
            new DataModeActionViewModel(
                globalActions,
                this.mConditionChecker,
                this.mSAnalytics,
                (SystemController) this.mUtilFactory.get(SystemController.class),
                (AlertDialogFactory) this.mUtilFactory.get(AlertDialogFactory.class),
                this.mFeatureFactory,
                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class),
                (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class));
        actionInfo.setName(DefaultActionNames.ACTION_DATA_MODE);
        actionInfo.setLabel(getResString(C4337R.string.global_action_toggle_data_mode));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_DATAMODE));
        actionInfo.setViewType(ViewType.CENTER_ICON_2P_VIEW);
        viewModel9.setActionInfo(actionInfo);
        return viewModel9;
      case '\t':
        ActionViewModel viewModel10 =
            new LockdownModeActionViewModel(
                this.mSAnalytics,
                (LockPatternUtilsWrapper) this.mUtilFactory.get(LockPatternUtilsWrapper.class),
                globalActions);
        actionInfo.setName(DefaultActionNames.ACTION_LOCKDOWN_MODE);
        actionInfo.setLabel(getResString(C4337R.string.global_action_lockdown_mode));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_LOCKDOWN));
        actionInfo.setViewType(ViewType.CENTER_ICON_5P_VIEW);
        viewModel10.setActionInfo(actionInfo);
        return viewModel10;
      case '\n':
        ActionViewModel viewModel11 =
            new LogoutActionViewModel(
                globalActions,
                (HandlerUtil) this.mUtilFactory.get(HandlerUtil.class),
                (LogWrapper) this.mUtilFactory.get(LogWrapper.class));
        actionInfo.setName(DefaultActionNames.ACTION_LOGOUT);
        actionInfo.setLabel(getResString(C4337R.string.global_action_logout));
        actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_ENDSESSION));
        actionInfo.setViewType(ViewType.CENTER_ICON_3P_VIEW);
        viewModel11.setActionInfo(actionInfo);
        return viewModel11;
      default:
        return null;
    }
  }
}
