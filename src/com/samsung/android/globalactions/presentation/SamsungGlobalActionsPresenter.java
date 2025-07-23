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

/* loaded from: classes6.dex */
public class SamsungGlobalActionsPresenter implements SamsungGlobalActions {
    private static int NOT_SIDE_KEY_MODELS = -1;
    private static final String TAG = "SamsungGlobalActionsPresenter";
    public static Comparator<ActionViewModel> sViewPositionComparator = new Comparator() { // from class: com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return SamsungGlobalActionsPresenter.lambda$static$4((ActionViewModel) obj, (ActionViewModel) obj2);
        }
    };
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

    public SamsungGlobalActionsPresenter(ExtendableGlobalActionsView extendableGlobalActionsView, FeatureFactory featureFactory, ActionViewModelFactory actionViewModelFactory, SamsungGlobalActionsManager samsungGlobalActionsManager, BroadcastManager broadcastManager, SystemController systemController, ConditionChecker conditionChecker, LogWrapper logWrapper, ThemeChecker themeChecker, ContentObserverWrapper contentObserverWrapper, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
        this.mView = extendableGlobalActionsView;
        this.mFactory = featureFactory;
        this.mViewModelFactory = actionViewModelFactory;
        this.mWindowManagerFuncs = samsungGlobalActionsManager;
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
        Iterator<InitializationStrategy> it = this.mFactory.createInitializationStrategies(this).iterator();
        while (it.hasNext()) {
            it.next().onInitialize(this.mIsKeyguardShowing);
        }
    }

    public void createActions() {
        Iterator<ActionsCreationStrategy> it = this.mFactory.createActionsCreationStrategies(this).iterator();
        while (it.hasNext()) {
            it.next().onCreateActions(this);
        }
        if (!this.mIsOverrideDefaultActions) {
            createDefaultActions();
        }
        for (ActionUpdateStrategy actionUpdateStrategy : this.mFactory.createActionUpdateStrategies()) {
            Iterator<ActionViewModel> it2 = this.mActions.iterator();
            while (it2.hasNext()) {
                actionUpdateStrategy.onUpdateAction(it2.next());
            }
        }
    }

    public void createDefaultActions() {
        this.mLogWrapper.i(TAG, "createDefaultActions()");
        addAction(this.mViewModelFactory.createActionViewModel(this, "power"));
        addAction(this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_RESTART));
        List<DefaultActionsCreationStrategy> createDefaultActionsCreationStrategy = this.mFactory.createDefaultActionsCreationStrategy(this, "bug_report");
        if (this.mSystemCondition.isEnabled(SystemConditions.IS_BUG_REPORT_MODE)) {
            Iterator<DefaultActionsCreationStrategy> it = createDefaultActionsCreationStrategy.iterator();
            while (true) {
                if (!it.hasNext()) {
                    addAction(this.mViewModelFactory.createActionViewModel(this, "bug_report"));
                    break;
                } else if (!it.next().onCreateBugReportAction()) {
                    break;
                }
            }
        }
        if (this.mSystemCondition.isEnabled(SystemConditions.IS_LOGOUT_ENABLED) && !this.mSystemCondition.isEnabled(SystemConditions.IS_DEVICE_OWNER)) {
            addAction(this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_LOGOUT));
        }
        if (this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_EMERGENCY_CALL)) {
            Iterator<DefaultActionsCreationStrategy> it2 = this.mFactory.createDefaultActionsCreationStrategy(this, DefaultActionNames.ACTION_EMERGENCY_CALL).iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (!it2.next().onCreateEmergencyCallAction()) {
                        break;
                    }
                } else {
                    addAction(this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_EMERGENCY_CALL));
                    break;
                }
            }
        }
        if (this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_MEDICAL_INFO) && this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_EMERGENCY_CALL)) {
            Iterator<DefaultActionsCreationStrategy> it3 = this.mFactory.createDefaultActionsCreationStrategy(this, DefaultActionNames.ACTION_MEDICAL_INFO).iterator();
            while (true) {
                if (it3.hasNext()) {
                    if (!it3.next().onCreateMedicalInfoAction()) {
                        break;
                    }
                } else {
                    addAction(this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_MEDICAL_INFO));
                    break;
                }
            }
        }
        if (this.mSystemCondition.isEnabled(SystemConditions.IS_SUPPORT_EMERGENCY_MODE)) {
            Iterator<DefaultActionsCreationStrategy> it4 = this.mFactory.createDefaultActionsCreationStrategy(this, "emergency").iterator();
            while (it4.hasNext()) {
                if (!it4.next().onCreateEmergencyAction()) {
                    return;
                }
            }
            addAction(this.mViewModelFactory.createActionViewModel(this, "emergency"));
        }
    }

    public boolean createOnKeyListenerActions(KeyEvent keyEvent, int i) {
        Iterator<OnKeyListenerStrategy> it = this.mFactory.createOnKeyListenerStrategy(this).iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().onKeyListenerAction(keyEvent.getAction(), i)) {
                z = true;
            }
        }
        return z;
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
    public void setKeyguardShowing(boolean z) {
        this.mIsKeyguardShowing = z;
    }

    public boolean onStart(boolean z, boolean z2, boolean z3, int i) {
        this.mLogWrapper.i(TAG, "onStart()");
        this.mSideKeyType = i;
        if (this.mIsShowing) {
            this.mWindowManagerFuncs.onGlobalActionsHidden();
            this.mWindowManagerFuncs.onGlobalActionsShown();
            if (z3) {
                dismissDialog(false);
            } else {
                dismissDialog(true);
            }
            return false;
        }
        this.mIsKeyguardShowing = z;
        this.mIsDeviceProvisioned = z2;
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
        Iterator<WindowDecorationStrategy> it = this.mFactory.createWindowDecorationStrategies(this).iterator();
        while (it.hasNext()) {
            this.mView.addWindowDecorator(it.next());
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
        Iterator<DisposingStrategy> it = this.mFactory.createDisposingStrategies(this).iterator();
        while (it.hasNext()) {
            it.next().onDispose();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void addAction(ActionViewModel actionViewModel) {
        if (actionViewModel != null) {
            this.mActions.add(actionViewModel);
            this.mLogWrapper.i(TAG, "addAction (" + actionViewModel.getActionInfo().getName() + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void setOverrideDefaultActions(boolean z) {
        this.mIsOverrideDefaultActions = z;
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void clearActions(final String str) {
        this.mActions.removeIf(new Predicate() { // from class: com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = ((ActionViewModel) obj).getActionInfo().getName().equals(str);
                return equals;
            }
        });
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public boolean isActionConfirming() {
        return this.mActionConfirming != null;
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void onShowDialog() {
        this.mLogWrapper.i(TAG, "onShowDialog()");
        if (this.mSideKeyType != NOT_SIDE_KEY_MODELS) {
            if (this.mSystemCondition.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mSystemCondition.isEnabled(SystemConditions.IS_FOLDED)) {
                this.mSamsungGlobalActionsAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_FRONT_COVER_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_FRONT_COVER_DEVICE_OPTIONS);
            } else {
                this.mSamsungGlobalActionsAnalytics.sendEventLog(SamsungGlobalActionsAnalytics.SID_DEVICE_OPTIONS, SamsungGlobalActionsAnalytics.EID_SIDE_KEY_TYPE, SamsungGlobalActionsAnalytics.DID_SIDE_KEY_TYPE, this.mSideKeyType);
            }
        }
        this.mWindowManagerFuncs.onGlobalActionsShown();
        this.mIsShowing = true;
        this.mBroadcastManager.registerDismissActions(new Runnable() { // from class: com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SamsungGlobalActionsPresenter.this.lambda$onShowDialog$1();
            }
        }, new Runnable() { // from class: com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda2
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
    public void registerSecureConfirmAction(final ActionViewModel actionViewModel) {
        if (this.mIsRegistered) {
            return;
        }
        this.mIsRegistered = true;
        this.mBroadcastManager.registerSecureConfirmAction(new Runnable() { // from class: com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ActionViewModel.this.onPressSecureConfirm();
            }
        });
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void confirmAction(ActionViewModel actionViewModel) {
        this.mView.showActionConfirming(actionViewModel);
        this.mActionConfirming = actionViewModel;
        hideQuickPanel("GlobalActions$ConfirmDialog");
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void onCancelDialog() {
        this.mLogWrapper.i(TAG, "onCancelDialog()");
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
    public void dismissDialog(boolean z) {
        if (z) {
            this.mView.dismissWithAnimation();
        } else {
            this.mIsRegistered = false;
            this.mView.dismiss();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public void confirmSafeMode(int i) {
        ActionViewModel createActionViewModel = this.mViewModelFactory.createActionViewModel(this, DefaultActionNames.ACTION_SAFE_MODE);
        if (createActionViewModel != null) {
            createActionViewModel.getActionInfo().setViewIndex(i);
            confirmAction(createActionViewModel);
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

    static /* synthetic */ int lambda$static$4(ActionViewModel actionViewModel, ActionViewModel actionViewModel2) {
        return actionViewModel.getActionInfo().getViewType().getValue() < actionViewModel2.getActionInfo().getViewType().getValue() ? -1 : 1;
    }

    public List<ActionViewModel> getValidActions() {
        ArrayList arrayList = new ArrayList();
        for (ActionViewModel actionViewModel : this.mActions) {
            if (this.mIsDeviceProvisioned || actionViewModel.showBeforeProvisioning()) {
                arrayList.add(actionViewModel);
            }
        }
        arrayList.sort(sViewPositionComparator);
        return arrayList;
    }

    public void hideQuickPanel() {
        hideQuickPanel("GlobalActions");
    }

    public void hideQuickPanel(String str) {
        this.mLogWrapper.v(TAG, "hideQuickPanelBackground(" + str + NavigationBarInflaterView.KEY_CODE_END);
        this.mSystemController.hideQuickPanel(str);
    }

    public void clearCoverStateChange() {
        this.mLogWrapper.v(TAG, "clearCoverStateChange()");
        this.mSystemController.clearCoverStateChange();
    }

    @Override // com.samsung.android.globalactions.presentation.SamsungGlobalActions
    public int getSideKeyType() {
        return this.mSideKeyType;
    }
}
