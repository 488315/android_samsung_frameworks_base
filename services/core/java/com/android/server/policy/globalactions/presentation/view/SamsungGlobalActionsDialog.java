package com.android.server.policy.globalactions.presentation.view;

import android.content.Context;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.policy.globalactions.presentation.PolicyGlobalActionsManager;
import com.android.server.policy.globalactions.presentation.features.GlobalActionFeatures;
import com.android.server.policy.globalactions.presentation.features.GlobalActionsFeatureFactory;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.view.ContentViewFactory;
import com.samsung.android.globalactions.presentation.view.DefaultResourceFactory;
import com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionViewModelFactory;
import com.samsung.android.globalactions.util.BroadcastManager;
import com.samsung.android.globalactions.util.ContentObserverWrapper;
import com.samsung.android.globalactions.util.DefaultUtilFactory;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalyticsImpl;
import com.samsung.android.globalactions.util.SettingsWrapper;
import com.samsung.android.globalactions.util.SystemConditionChecker;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.ThemeChecker;
import com.samsung.android.globalactions.util.WindowManagerUtils;

/* loaded from: classes3.dex */
public class SamsungGlobalActionsDialog extends SamsungGlobalActionsDialogBase {
  public final Runnable mOnDismiss;

  public SamsungGlobalActionsDialog(
      Context context,
      WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs,
      Runnable runnable) {
    super(context, new DefaultResourceFactory());
    this.mOnDismiss = runnable;
    initDependencies(windowManagerFuncs);
  }

  public final void initDependencies(WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
    PolicyGlobalActionsManager policyGlobalActionsManager =
        new PolicyGlobalActionsManager(windowManagerFuncs);
    DefaultUtilFactory defaultUtilFactory =
        new DefaultUtilFactory(
            ((SamsungGlobalActionsDialogBase) this).mContext, policyGlobalActionsManager);
    GlobalActionFeatures globalActionFeatures =
        new GlobalActionFeatures(
            ((SamsungGlobalActionsDialogBase) this).mContext,
            (SettingsWrapper) defaultUtilFactory.get(SettingsWrapper.class));
    LogWrapper logWrapper = (LogWrapper) defaultUtilFactory.get(LogWrapper.class);
    ((SamsungGlobalActionsDialogBase) this).mLogWrapper = logWrapper;
    logWrapper.setPackageTag("[Service]");
    ((SamsungGlobalActionsDialogBase) this).mHandlerUtil =
        (HandlerUtil) defaultUtilFactory.get(HandlerUtil.class);
    ((SamsungGlobalActionsDialogBase) this).mWindowManagerUtil =
        (WindowManagerUtils) defaultUtilFactory.get(WindowManagerUtils.class);
    SamsungGlobalActionsAnalyticsImpl samsungGlobalActionsAnalyticsImpl =
        new SamsungGlobalActionsAnalyticsImpl();
    ((SamsungGlobalActionsDialogBase) this).mConditionChecker =
        new SystemConditionChecker(
            defaultUtilFactory,
            globalActionFeatures,
            ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
    DefaultActionViewModelFactory defaultActionViewModelFactory =
        new DefaultActionViewModelFactory(
            defaultUtilFactory,
            ((SamsungGlobalActionsDialogBase) this).mResourceFactory,
            ((SamsungGlobalActionsDialogBase) this).mConditionChecker,
            samsungGlobalActionsAnalyticsImpl);
    GlobalActionsFeatureFactory globalActionsFeatureFactory =
        new GlobalActionsFeatureFactory(
            ((SamsungGlobalActionsDialogBase) this).mContext,
            this,
            defaultUtilFactory,
            defaultActionViewModelFactory,
            globalActionFeatures,
            ((SamsungGlobalActionsDialogBase) this).mConditionChecker);
    ((SamsungGlobalActionsDialogBase) this).mFeatureFactory = globalActionsFeatureFactory;
    defaultActionViewModelFactory.setFeatureFactory(globalActionsFeatureFactory);
    ((SamsungGlobalActionsDialogBase) this).mPresenter =
        new SamsungGlobalActionsPresenter(
            this,
            ((SamsungGlobalActionsDialogBase) this).mFeatureFactory,
            defaultActionViewModelFactory,
            policyGlobalActionsManager,
            (BroadcastManager) defaultUtilFactory.get(BroadcastManager.class),
            (SystemController) defaultUtilFactory.get(SystemController.class),
            ((SamsungGlobalActionsDialogBase) this).mConditionChecker,
            ((SamsungGlobalActionsDialogBase) this).mLogWrapper,
            (ThemeChecker) defaultUtilFactory.get(ThemeChecker.class),
            (ContentObserverWrapper) defaultUtilFactory.get(ContentObserverWrapper.class),
            samsungGlobalActionsAnalyticsImpl);
  }

  public void showDialog() {
    ((SamsungGlobalActionsDialogBase) this).mContentViewFactory =
        new ContentViewFactory(
            ((SamsungGlobalActionsDialogBase) this).mContext,
            this,
            ((SamsungGlobalActionsDialogBase) this).mFeatureFactory,
            ((SamsungGlobalActionsDialogBase) this).mConditionChecker,
            ((SamsungGlobalActionsDialogBase) this).mWindowManagerUtil,
            ((SamsungGlobalActionsDialogBase) this).mResourceFactory,
            ((SamsungGlobalActionsDialogBase) this).mLogWrapper,
            ((SamsungGlobalActionsDialogBase) this).mHandlerUtil,
            ((SamsungGlobalActionsDialogBase) this).mToastController,
            ((SamsungGlobalActionsDialogBase) this).mPresenter,
            ((SamsungGlobalActionsDialogBase) this).mFromSystemServer);
    super.showDialog();
  }

  public void dismiss() {
    Runnable runnable;
    if (((SamsungGlobalActionsDialogBase) this).mDialog != null
        && (runnable = this.mOnDismiss) != null) {
      runnable.run();
    }
    super.dismiss();
  }
}
