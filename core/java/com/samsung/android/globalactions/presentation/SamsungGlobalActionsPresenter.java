package com.samsung.android.globalactions.presentation;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.view.KeyEvent;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ActionUpdateStrategy;
import com.samsung.android.globalactions.presentation.strategies.ActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.presentation.strategies.OnKeyListenerStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.util.BroadcastManager;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.ContentObserverWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.ThemeChecker;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class SamsungGlobalActionsPresenter implements SamsungGlobalActions {
  private static final String TAG = "SamsungGlobalActionsPresenter";
  public ActionViewModel mActionConfirming;
  public List<ActionViewModel> mActions = new ArrayList();
  BroadcastManager mBroadcastManager;
  private final ContentObserverWrapper mContentObserverWrapper;
  FeatureFactory mFactory;
  public boolean mIsDeviceProvisioned;
  public boolean mIsDisabled;
  boolean mIsKeyguardShowing;
  boolean mIsOverrideDefaultActions;
  boolean mIsRegistered;
  public boolean mIsShowing;
  private final LogWrapper mLogWrapper;
  SamsungGlobalActionsAnalytics mSamsungGlobalActionsAnalytics;
  int mSideKeyType;
  ConditionChecker mSystemCondition;
  SystemController mSystemController;
  private final ThemeChecker mThemeChecker;
  ExtendableGlobalActionsView mView;
  ActionViewModelFactory mViewModelFactory;
  SamsungGlobalActionsManager mWindowManagerFuncs;
  private static int NOT_SIDE_KEY_MODELS = -1;
  public static Comparator<ActionViewModel> sViewPositionComparator =
      new Comparator() { // from class:
                         // com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda4
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
          return SamsungGlobalActionsPresenter.lambda$static$4(
              (ActionViewModel) obj, (ActionViewModel) obj2);
        }
      };

  public SamsungGlobalActionsPresenter(
      ExtendableGlobalActionsView view,
      FeatureFactory factory,
      ActionViewModelFactory viewModelFactory,
      SamsungGlobalActionsManager windowManagerFuncs,
      BroadcastManager broadcastManager,
      SystemController systemController,
      ConditionChecker conditionChecker,
      LogWrapper logWrapper,
      ThemeChecker themeChecker,
      ContentObserverWrapper contentObserverWrapper,
      SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
    this.mView = view;
    this.mFactory = factory;
    this.mViewModelFactory = viewModelFactory;
    this.mWindowManagerFuncs = windowManagerFuncs;
    this.mLogWrapper = logWrapper;
    this.mBroadcastManager = broadcastManager;
    this.mSystemController = systemController;
    this.mSystemCondition = conditionChecker;
    this.mThemeChecker = themeChecker;
    this.mContentObserverWrapper = contentObserverWrapper;
    this.mSamsungGlobalActionsAnalytics = samsungGlobalActionsAnalytics;
  }

  public void initialize() {
    this.mIsShowing = false;
    this.mIsOverrideDefaultActions = false;
    this.mIsRegistered = false;
    this.mActionConfirming = null;
    this.mActions.clear();
    List<InitializationStrategy> initStrategies =
        this.mFactory.createInitializationStrategies(this);
    for (InitializationStrategy decorator : initStrategies) {
      decorator.onInitialize(this.mIsKeyguardShowing);
    }
  }

  public void createActions() {
    List<ActionsCreationStrategy> actionsCreationStrategies =
        this.mFactory.createActionsCreationStrategies(this);
    for (ActionsCreationStrategy decorator : actionsCreationStrategies) {
      decorator.onCreateActions(this);
    }
    if (!this.mIsOverrideDefaultActions) {
      createDefaultActions();
    }
    List<ActionUpdateStrategy> actionUpdateStrategies =
        this.mFactory.createActionUpdateStrategies();
    for (ActionUpdateStrategy strategy : actionUpdateStrategies) {
      for (ActionViewModel viewModel : this.mActions) {
        strategy.onUpdateAction(viewModel);
      }
    }
  }

  public void createDefaultActions() {
    this.mLogWrapper.m265i(TAG, "createDefaultActions()");
    addAction(this.mViewModelFactory.createActionViewModel(this, "power"));
    addAction(
        this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_RESTART));
    List<DefaultActionsCreationStrategy> creationStrategies =
        this.mFactory.createDefaultActionsCreationStrategy(this, "bug_report");
    if (this.mSystemCondition.isEnabled(SystemConditions.IS_BUG_REPORT_MODE)) {
      boolean skipBugReport = false;
      Iterator<DefaultActionsCreationStrategy> it = creationStrategies.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        DefaultActionsCreationStrategy strategy = it.next();
        if (!strategy.onCreateBugReportAction()) {
          skipBugReport = true;
          break;
        }
      }
      if (!skipBugReport) {
        addAction(this.mViewModelFactory.createActionViewModel(this, "bug_report"));
      }
    }
    if (this.mSystemCondition.isEnabled(SystemConditions.IS_LOGOUT_ENABLED)
        && !this.mSystemCondition.isEnabled(SystemConditions.IS_DEVICE_OWNER)) {
      addAction(
          this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_LOGOUT));
    }
    if (this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_EMERGENCY_CALL)) {
      List<DefaultActionsCreationStrategy> creationStrategies2 =
          this.mFactory.createDefaultActionsCreationStrategy(
              this, DefaultActionNames.ACTION_EMERGENCY_CALL);
      boolean skipEmergencyCall = false;
      Iterator<DefaultActionsCreationStrategy> it2 = creationStrategies2.iterator();
      while (true) {
        if (!it2.hasNext()) {
          break;
        }
        DefaultActionsCreationStrategy strategy2 = it2.next();
        if (!strategy2.onCreateEmergencyCallAction()) {
          skipEmergencyCall = true;
          break;
        }
      }
      if (!skipEmergencyCall) {
        addAction(
            this.mViewModelFactory.createActionViewModel(
                this, DefaultActionNames.ACTION_EMERGENCY_CALL));
      }
    }
    if (this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_MEDICAL_INFO)
        && this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_EMERGENCY_CALL)) {
      List<DefaultActionsCreationStrategy> creationStrategies3 =
          this.mFactory.createDefaultActionsCreationStrategy(
              this, DefaultActionNames.ACTION_MEDICAL_INFO);
      boolean skipMedicalInfo = false;
      Iterator<DefaultActionsCreationStrategy> it3 = creationStrategies3.iterator();
      while (true) {
        if (!it3.hasNext()) {
          break;
        }
        DefaultActionsCreationStrategy strategy3 = it3.next();
        if (!strategy3.onCreateMedicalInfoAction()) {
          skipMedicalInfo = true;
          break;
        }
      }
      if (!skipMedicalInfo) {
        addAction(
            this.mViewModelFactory.createActionViewModel(
                this, DefaultActionNames.ACTION_MEDICAL_INFO));
      }
    }
    if (this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_EMERGENCY_MODE)) {
      List<DefaultActionsCreationStrategy> creationStrategies4 =
          this.mFactory.createDefaultActionsCreationStrategy(this, "emergency");
      boolean skipEmergencyMode = false;
      Iterator<DefaultActionsCreationStrategy> it4 = creationStrategies4.iterator();
      while (true) {
        if (!it4.hasNext()) {
          break;
        }
        DefaultActionsCreationStrategy strategy4 = it4.next();
        if (!strategy4.onCreateEmergencyAction()) {
          skipEmergencyMode = true;
          break;
        }
      }
      if (!skipEmergencyMode) {
        addAction(this.mViewModelFactory.createActionViewModel(this, "emergency"));
      }
    }
  }

  public boolean createOnKeyListenerActions(KeyEvent event, int keyCode) {
    List<OnKeyListenerStrategy> strategies = this.mFactory.createOnKeyListenerStrategy(this);
    boolean doAtLestOneAction = false;
    for (OnKeyListenerStrategy strategy : strategies) {
      if (strategy.onKeyListenerAction(event.getAction(), keyCode)) {
        doAtLestOneAction = true;
      }
    }
    return doAtLestOneAction;
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public ExtendableGlobalActionsView getGlobalActionsView() {
    return this.mView;
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public boolean isDeviceProvisioned() {
    return this.mIsDeviceProvisioned;
  }

  public boolean isDisabled() {
    return this.mIsDisabled;
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void setDisabled() {
    this.mIsDisabled = true;
  }

  public boolean isKeyguardShowing() {
    return this.mIsKeyguardShowing;
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void setKeyguardShowing(boolean keyguardShowing) {
    this.mIsKeyguardShowing = keyguardShowing;
  }

  public boolean onStart(
      boolean keyguardShowing,
      boolean deviceProvisioned,
      boolean fromSystemServer,
      int sideKeyType) {
    this.mLogWrapper.m265i(TAG, "onStart()");
    this.mSideKeyType = sideKeyType;
    if (this.mIsShowing) {
      this.mWindowManagerFuncs.onGlobalActionsHidden();
      this.mWindowManagerFuncs.onGlobalActionsShown();
      if (fromSystemServer) {
        dismissDialog(false);
      } else {
        dismissDialog(true);
      }
      return false;
    }
    this.mIsKeyguardShowing = keyguardShowing;
    this.mIsDeviceProvisioned = deviceProvisioned;
    initialize();
    if (isDisabled()) {
      this.mIsDisabled = false;
      dispose();
      this.mWindowManagerFuncs.onGlobalActionsShown();
      this.mWindowManagerFuncs.onGlobalActionsHidden();
      return false;
    }
    createActions();
    return true;
  }

  public void onPrepareWindow() {
    List<WindowDecorationStrategy> strategies =
        this.mFactory.createWindowDecorationStrategies(this);
    for (WindowDecorationStrategy strategy : strategies) {
      this.mView.addWindowDecorator(strategy);
    }
  }

  public void onDismiss() {
    dispose();
    this.mWindowManagerFuncs.onGlobalActionsHidden();
    this.mIsShowing = false;
    this.mBroadcastManager.unregisterDismissBroadcastReceiver();
    this.mBroadcastManager.unregisterSecureConfirmBroadcastReceiver();
    this.mThemeChecker.reset();
    this.mContentObserverWrapper.unregisterObservers();
  }

  public void dispose() {
    List<DisposingStrategy> strategies = this.mFactory.createDisposingStrategies(this);
    for (DisposingStrategy strategy : strategies) {
      strategy.onDispose();
    }
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void addAction(ActionViewModel action) {
    if (action != null) {
      this.mActions.add(action);
      this.mLogWrapper.m265i(
          TAG,
          "addAction ("
              + action.getActionInfo().getName()
              + NavigationBarInflaterView.KEY_CODE_END);
    }
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void setOverrideDefaultActions(boolean overrideDefault) {
    this.mIsOverrideDefaultActions = overrideDefault;
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void clearActions(final String actionName) {
    Predicate<ActionViewModel> actionPredicate =
        new Predicate() { // from class:
                          // com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda3
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean equals;
            equals = ((ActionViewModel) obj).getActionInfo().getName().equals(actionName);
            return equals;
          }
        };
    this.mActions.removeIf(actionPredicate);
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public boolean isActionConfirming() {
    return this.mActionConfirming != null;
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void onShowDialog() {
    this.mLogWrapper.m265i(TAG, "onShowDialog()");
    if (this.mSideKeyType != NOT_SIDE_KEY_MODELS) {
      if (this.mSystemCondition.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER)
          && this.mSystemCondition.isEnabled(SystemConditions.IS_FOLDED)) {
        this.mSamsungGlobalActionsAnalytics.sendEventLog(
            SamsungGlobalActionsAnalytics.SID_FRONT_COVER_DEVICE_OPTIONS,
            SamsungGlobalActionsAnalytics.EID_FRONT_COVER_DEVICE_OPTIONS);
      } else {
        this.mSamsungGlobalActionsAnalytics.sendEventLog(
            SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS,
            SamsungGlobalActionsAnalytics.EID_SIDE_KEY_TYPE,
            SamsungGlobalActionsAnalytics.DID_SIDE_KEY_TYPE,
            this.mSideKeyType);
      }
    }
    this.mWindowManagerFuncs.onGlobalActionsShown();
    this.mIsShowing = true;
    this.mBroadcastManager.registerDismissActions(
        new Runnable() { // from class:
                         // com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SamsungGlobalActionsPresenter.this.lambda$onShowDialog$1();
          }
        },
        new Runnable() { // from class:
                         // com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SamsungGlobalActionsPresenter.this.lambda$onShowDialog$2();
          }
        });
    hideQuickPanel();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onShowDialog$1() {
    this.mView.dismiss();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onShowDialog$2() {
    this.mView.dismissWithAnimation();
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void registerSecureConfirmAction(final ActionViewModel viewModel) {
    if (!this.mIsRegistered) {
      this.mIsRegistered = true;
      this.mBroadcastManager.registerSecureConfirmAction(
          new Runnable() { // from class:
                           // com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              ActionViewModel.this.onPressSecureConfirm();
            }
          });
    }
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void confirmAction(ActionViewModel viewModel) {
    this.mView.showActionConfirming(viewModel);
    this.mActionConfirming = viewModel;
    hideQuickPanel("GlobalActions$ConfirmDialog");
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void onCancelDialog() {
    this.mLogWrapper.m265i(TAG, "onCancelDialog()");
    if (isActionConfirming()) {
      this.mActionConfirming = null;
      this.mView.cancelConfirming();
      this.mBroadcastManager.unregisterSecureConfirmBroadcastReceiver();
      this.mIsRegistered = false;
      return;
    }
    dismissDialog(true);
    clearCoverStateChange();
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void dismissDialog(boolean withAnimation) {
    if (withAnimation) {
      this.mView.dismissWithAnimation();
    } else {
      this.mIsRegistered = false;
      this.mView.dismiss();
    }
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void confirmSafeMode(int index) {
    ActionViewModel safeModeViewModel =
        this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_SAFE_MODE);
    if (safeModeViewModel != null) {
      safeModeViewModel.getActionInfo().setViewIndex(index);
      confirmAction(safeModeViewModel);
    }
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void hideDialogOnSecureConfirm() {
    this.mView.hideDialogOnSecureConfirm();
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public void registerContentObserver(Uri uri, Runnable runnable) {
    this.mContentObserverWrapper.registerObserver(uri, runnable);
  }

  static /* synthetic */ int lambda$static$4(ActionViewModel p1, ActionViewModel p2) {
    return p1.getActionInfo().getViewType().getValue() < p2.getActionInfo().getViewType().getValue()
        ? -1
        : 1;
  }

  public List<ActionViewModel> getValidActions() {
    List<ActionViewModel> ret = new ArrayList<>();
    for (ActionViewModel vm : this.mActions) {
      if (this.mIsDeviceProvisioned || vm.showBeforeProvisioning()) {
        ret.add(vm);
      }
    }
    ret.sort(sViewPositionComparator);
    return ret;
  }

  public void hideQuickPanel() {
    hideQuickPanel("GlobalActions");
  }

  public void hideQuickPanel(String sender) {
    this.mLogWrapper.m266v(
        TAG, "hideQuickPanelBackground(" + sender + NavigationBarInflaterView.KEY_CODE_END);
    this.mSystemController.hideQuickPanel(sender);
  }

  public void clearCoverStateChange() {
    this.mLogWrapper.m266v(TAG, "clearCoverStateChange()");
    this.mSystemController.clearCoverStateChange();
  }

  @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
  public int getSideKeyType() {
    return this.mSideKeyType;
  }
}
