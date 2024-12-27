package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.os.Build;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.globalactions.presentation.SystemUIGlobalActionsManager;
import com.android.systemui.globalactions.presentation.features.FakeFeatures;
import com.android.systemui.globalactions.presentation.features.GlobalActionFeatures;
import com.android.systemui.globalactions.presentation.features.GlobalActionsFeatureFactory;
import com.android.systemui.globalactions.presentation.viewmodel.ActionViewModelFactoryDecorator;
import com.android.systemui.globalactions.util.FakeConditionChecker;
import com.android.systemui.globalactions.util.SamsungGlobalActionsAnalyticsImpl;
import com.android.systemui.globalactions.util.SystemUIConditionChecker;
import com.android.systemui.globalactions.util.SystemUIUtilFactory;
import com.android.systemui.model.SysUiState;
import com.android.systemui.plugins.GlobalActions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsDialogBase;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionViewModelFactory;
import com.samsung.android.globalactions.util.BroadcastManager;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.ContentObserverWrapper;
import com.samsung.android.globalactions.util.DefaultUtilFactory;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SettingsWrapper;
import com.samsung.android.globalactions.util.SystemConditionChecker;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.SystemPropertiesWrapper;
import com.samsung.android.globalactions.util.ThemeChecker;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.WindowManagerUtils;

public final class SamsungGlobalActionsDialog extends SamsungGlobalActionsDialogBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoverUtilWrapper mCoverUtilWrapper;
    public final SysUiState mSysUiState;

    public SamsungGlobalActionsDialog(Context context, GlobalActions.GlobalActionsManager globalActionsManager) {
        super(context, new SystemUIResourceFactory());
        Features features;
        ConditionChecker conditionChecker;
        ((SamsungGlobalActionsDialogBase) this).mDialogStyle = R.style.Theme_SystemUI_Dialog_GlobalActions;
        SystemUIGlobalActionsManager systemUIGlobalActionsManager = new SystemUIGlobalActionsManager(globalActionsManager);
        DefaultUtilFactory defaultUtilFactory = new DefaultUtilFactory(((SamsungGlobalActionsDialogBase) this).mContext, systemUIGlobalActionsManager);
        SystemUIUtilFactory systemUIUtilFactory = new SystemUIUtilFactory(((SamsungGlobalActionsDialogBase) this).mContext, globalActionsManager, defaultUtilFactory);
        this.mCoverUtilWrapper = (CoverUtilWrapper) systemUIUtilFactory.get(CoverUtilWrapper.class);
        LogWrapper logWrapper = (LogWrapper) defaultUtilFactory.get(LogWrapper.class);
        ((SamsungGlobalActionsDialogBase) this).mLogWrapper = logWrapper;
        logWrapper.setPackageTag("[SystemUI]");
        ((SamsungGlobalActionsDialogBase) this).mHandlerUtil = (HandlerUtil) defaultUtilFactory.get(HandlerUtil.class);
        ((SamsungGlobalActionsDialogBase) this).mWindowManagerUtil = (WindowManagerUtils) defaultUtilFactory.get(WindowManagerUtils.class);
        ((SamsungGlobalActionsDialogBase) this).mToastController = (ToastController) defaultUtilFactory.get(ToastController.class);
        Features globalActionFeatures = new GlobalActionFeatures(((SamsungGlobalActionsDialogBase) this).mContext, (SettingsWrapper) defaultUtilFactory.get(SettingsWrapper.class), (SystemPropertiesWrapper) defaultUtilFactory.get(SystemPropertiesWrapper.class), ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
        ConditionChecker systemUIConditionChecker = new SystemUIConditionChecker(systemUIUtilFactory, new SystemConditionChecker(defaultUtilFactory, globalActionFeatures, ((SamsungGlobalActionsDialogBase) this).mLogWrapper), ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
        SamsungGlobalActionsAnalyticsImpl samsungGlobalActionsAnalyticsImpl = new SamsungGlobalActionsAnalyticsImpl();
        samsungGlobalActionsAnalyticsImpl.sendEventLog("611", "6111");
        if ("user".equals(Build.TYPE)) {
            features = globalActionFeatures;
            conditionChecker = systemUIConditionChecker;
        } else {
            ConditionChecker fakeConditionChecker = new FakeConditionChecker(((SamsungGlobalActionsDialogBase) this).mContext, systemUIConditionChecker, ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
            features = new FakeFeatures(((SamsungGlobalActionsDialogBase) this).mContext, globalActionFeatures, ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
            conditionChecker = fakeConditionChecker;
        }
        DefaultActionViewModelFactory defaultActionViewModelFactory = new DefaultActionViewModelFactory(defaultUtilFactory, ((SamsungGlobalActionsDialogBase) this).mResourceFactory, conditionChecker, samsungGlobalActionsAnalyticsImpl);
        ActionViewModelFactoryDecorator actionViewModelFactoryDecorator = new ActionViewModelFactoryDecorator(defaultActionViewModelFactory, systemUIUtilFactory, ((SamsungGlobalActionsDialogBase) this).mResourceFactory, conditionChecker, samsungGlobalActionsAnalyticsImpl);
        GlobalActionsFeatureFactory globalActionsFeatureFactory = new GlobalActionsFeatureFactory(((SamsungGlobalActionsDialogBase) this).mContext, this, systemUIUtilFactory, actionViewModelFactoryDecorator, features, conditionChecker, ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
        ((SamsungGlobalActionsDialogBase) this).mFeatureFactory = globalActionsFeatureFactory;
        defaultActionViewModelFactory.setFeatureFactory(globalActionsFeatureFactory);
        actionViewModelFactoryDecorator.mFeatureFactory = ((SamsungGlobalActionsDialogBase) this).mFeatureFactory;
        ((SamsungGlobalActionsDialogBase) this).mPresenter = new SamsungGlobalActionsPresenter(this, ((SamsungGlobalActionsDialogBase) this).mFeatureFactory, actionViewModelFactoryDecorator, systemUIGlobalActionsManager, (BroadcastManager) defaultUtilFactory.get(BroadcastManager.class), (SystemController) defaultUtilFactory.get(SystemController.class), conditionChecker, ((SamsungGlobalActionsDialogBase) this).mLogWrapper, (ThemeChecker) defaultUtilFactory.get(ThemeChecker.class), (ContentObserverWrapper) defaultUtilFactory.get(ContentObserverWrapper.class), samsungGlobalActionsAnalyticsImpl);
        ((SamsungGlobalActionsDialogBase) this).mConditionChecker = conditionChecker;
        this.mSysUiState = (SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class);
    }

    public final void dismiss() {
        SysUiState sysUiState = this.mSysUiState;
        sysUiState.setFlag(32768L, false);
        sysUiState.commitUpdate(((SamsungGlobalActionsDialogBase) this).mContext.getDisplayId());
        super.dismiss();
    }

    public final void showDialog() {
        ((SamsungGlobalActionsDialogBase) this).mContentViewFactory = new ContentViewFactory(((SamsungGlobalActionsDialogBase) this).mContext, this, ((SamsungGlobalActionsDialogBase) this).mFeatureFactory, ((SamsungGlobalActionsDialogBase) this).mConditionChecker, ((SamsungGlobalActionsDialogBase) this).mWindowManagerUtil, ((SamsungGlobalActionsDialogBase) this).mResourceFactory, this.mCoverUtilWrapper, ((SamsungGlobalActionsDialogBase) this).mLogWrapper, ((SamsungGlobalActionsDialogBase) this).mHandlerUtil, ((SamsungGlobalActionsDialogBase) this).mToastController, ((SamsungGlobalActionsDialogBase) this).mPresenter, ((SamsungGlobalActionsDialogBase) this).mFromSystemServer);
        SysUiState sysUiState = this.mSysUiState;
        sysUiState.setFlag(32768L, true);
        sysUiState.commitUpdate(((SamsungGlobalActionsDialogBase) this).mContext.getDisplayId());
        super.showDialog();
    }
}
