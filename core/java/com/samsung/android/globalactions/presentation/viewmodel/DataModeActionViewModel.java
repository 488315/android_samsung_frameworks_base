package com.samsung.android.globalactions.presentation.viewmodel;

import com.android.internal.C4337R;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ActionInteractionStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.util.AlertDialogFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.SystemController;
import java.util.List;

/* loaded from: classes5.dex */
public class DataModeActionViewModel implements ActionViewModel {
  private final AlertDialogFactory mAlertDialogFactory;
  private ConditionChecker mConditionChecker;
  private final FeatureFactory mFeatureFactory;
  private final SamsungGlobalActions mGlobalActions;
  private ActionInfo mInfo;
  private final KeyGuardManagerWrapper mKeyguardManagerWrapper;
  private final ResourcesWrapper mResourcesWrapper;
  private final SamsungGlobalActionsAnalytics mSAnalytics;
  private final SystemController mSystemController;
  private ActionViewModel.ToggleState mToggleState;

  public DataModeActionViewModel(
      SamsungGlobalActions globalActions,
      ConditionChecker conditionChecker,
      SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics,
      SystemController systemController,
      AlertDialogFactory alertDialogFactory,
      FeatureFactory featureFactory,
      ResourcesWrapper resourcesWrapper,
      KeyGuardManagerWrapper keyGuardManagerWrapper) {
    this.mGlobalActions = globalActions;
    this.mConditionChecker = conditionChecker;
    this.mSAnalytics = samsungGlobalActionsAnalytics;
    this.mSystemController = systemController;
    this.mAlertDialogFactory = alertDialogFactory;
    this.mFeatureFactory = featureFactory;
    this.mResourcesWrapper = resourcesWrapper;
    this.mKeyguardManagerWrapper = keyGuardManagerWrapper;
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
    if (this.mConditionChecker.isEnabled(SystemConditions.GET_MOBILE_DATA_ENABLED)
        && this.mConditionChecker.isEnabled(SystemConditions.HAS_ANY_SIM)
        && !this.mConditionChecker.isEnabled(SystemConditions.IS_AIRPLANE_MODE)) {
      this.mToggleState = ActionViewModel.ToggleState.on;
      this.mInfo.setStateLabel(
          this.mResourcesWrapper.getString(C4337R.string.global_action_datamode_on_status));
    } else {
      this.mToggleState = ActionViewModel.ToggleState.off;
      this.mInfo.setStateLabel(
          this.mResourcesWrapper.getString(C4337R.string.global_action_datamode_off_status));
    }
  }

  @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
  public void onPress() {
    List<ActionInteractionStrategy> strategies =
        this.mFeatureFactory.createActionInteractionStrategies(this.mInfo.getName());
    for (ActionInteractionStrategy strategy : strategies) {
      if (strategy.onPressDataModeAction()) {
        return;
      }
    }
    if (this.mConditionChecker.isEnabled(SystemConditions.IS_AIRPLANE_MODE)
        || this.mConditionChecker.isEnabled(SystemConditions.IS_FMM_LOCKED)) {
      return;
    }
    if (!this.mConditionChecker.isEnabled(SystemConditions.HAS_ANY_SIM)) {
      this.mAlertDialogFactory.getInsertSimCardDialog().show();
      return;
    }
    if (this.mToggleState == ActionViewModel.ToggleState.on) {
      this.mToggleState = ActionViewModel.ToggleState.off;
    } else {
      this.mToggleState = ActionViewModel.ToggleState.on;
    }
    if (isNeedSecureConfirm()) {
      boolean isNeedToRegister = true;
      List<SecureConfirmStrategy> secureConfirmStrategies =
          this.mFeatureFactory.createSecureConfirmStrategy(
              this.mGlobalActions, this.mInfo.getName());
      for (SecureConfirmStrategy strategy2 : secureConfirmStrategies) {
        isNeedToRegister &= strategy2.doActionBeforeSecureConfirm(this, this.mGlobalActions);
      }
      if (isNeedToRegister) {
        this.mGlobalActions.registerSecureConfirmAction(this);
        this.mKeyguardManagerWrapper.setPendingIntentAfterUnlock("");
        this.mGlobalActions.hideDialogOnSecureConfirm();
        return;
      }
    }
    toggleDataState();
  }

  @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
  public void onPressSecureConfirm() {
    toggleDataState();
  }

  public void toggleDataState() {
    this.mSAnalytics.sendEventLog(
        SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS,
        SamsungGlobalActionsAnalytics.EID_DEVICE_OPTIONS,
        SamsungGlobalActionsAnalytics.DID_MOBILE_DATA,
        5L);
    this.mSystemController.setDataEnabled(this.mToggleState == ActionViewModel.ToggleState.on);
    this.mGlobalActions.dismissDialog(true);
  }

  private boolean isNeedSecureConfirm() {
    return !this.mConditionChecker.isEnabled(SystemConditions.IS_RMM_LOCKED)
        && !this.mConditionChecker.isEnabled(SystemConditions.IS_SIM_LOCK)
        && this.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD)
        && this.mConditionChecker.isEnabled(SystemConditions.IS_LOCK_NETWORK_AND_SECURITY);
  }

  @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
  public void setState(ActionViewModel.ToggleState state) {
    this.mToggleState = state;
  }

  @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
  public ActionViewModel.ToggleState getState() {
    return this.mToggleState;
  }

  @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
  public boolean showBeforeProvisioning() {
    return false;
  }
}
