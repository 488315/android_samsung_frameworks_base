package com.android.systemui.globalactions.presentation.view;

import android.content.Context;
import android.os.Build;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.globalactions.GlobalActionsComponent;
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
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SamsungGlobalActionsDialog extends SamsungGlobalActionsDialogBase {
    public final CoverUtilWrapper mCoverUtilWrapper;
    public final SysUiState mSysUiState;

    public SamsungGlobalActionsDialog(Context context, GlobalActions.GlobalActionsManager globalActionsManager) {
        super(context, new SystemUIResourceFactory());
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
            conditionChecker = systemUIConditionChecker;
        } else {
            ConditionChecker fakeConditionChecker = new FakeConditionChecker(((SamsungGlobalActionsDialogBase) this).mContext, systemUIConditionChecker, ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
            globalActionFeatures = new FakeFeatures(((SamsungGlobalActionsDialogBase) this).mContext, globalActionFeatures, ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
            conditionChecker = fakeConditionChecker;
        }
        DefaultActionViewModelFactory defaultActionViewModelFactory = new DefaultActionViewModelFactory(defaultUtilFactory, ((SamsungGlobalActionsDialogBase) this).mResourceFactory, conditionChecker, samsungGlobalActionsAnalyticsImpl);
        ActionViewModelFactoryDecorator actionViewModelFactoryDecorator = new ActionViewModelFactoryDecorator(defaultActionViewModelFactory, systemUIUtilFactory, ((SamsungGlobalActionsDialogBase) this).mResourceFactory, conditionChecker, samsungGlobalActionsAnalyticsImpl);
        ConditionChecker conditionChecker2 = conditionChecker;
        GlobalActionsFeatureFactory globalActionsFeatureFactory = new GlobalActionsFeatureFactory(((SamsungGlobalActionsDialogBase) this).mContext, this, systemUIUtilFactory, actionViewModelFactoryDecorator, globalActionFeatures, conditionChecker2, ((SamsungGlobalActionsDialogBase) this).mLogWrapper);
        ((SamsungGlobalActionsDialogBase) this).mFeatureFactory = globalActionsFeatureFactory;
        defaultActionViewModelFactory.setFeatureFactory(globalActionsFeatureFactory);
        actionViewModelFactoryDecorator.mFeatureFactory = ((SamsungGlobalActionsDialogBase) this).mFeatureFactory;
        ((SamsungGlobalActionsDialogBase) this).mPresenter = new SamsungGlobalActionsPresenter(this, ((SamsungGlobalActionsDialogBase) this).mFeatureFactory, actionViewModelFactoryDecorator, systemUIGlobalActionsManager, (BroadcastManager) defaultUtilFactory.get(BroadcastManager.class), (SystemController) defaultUtilFactory.get(SystemController.class), conditionChecker2, ((SamsungGlobalActionsDialogBase) this).mLogWrapper, (ThemeChecker) defaultUtilFactory.get(ThemeChecker.class), (ContentObserverWrapper) defaultUtilFactory.get(ContentObserverWrapper.class), samsungGlobalActionsAnalyticsImpl);
        ((SamsungGlobalActionsDialogBase) this).mConditionChecker = conditionChecker2;
        this.mSysUiState = (SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class);
    }

    public static void showGlobalActionsfromQuickPanel() {
        ((GlobalActionsComponent) Dependency.sDependency.getDependencyInner(GlobalActionsComponent.class)).handleShowGlobalActionsMenu(2);
        if (BasicRune.GLOBALACTIONS_BLUR) {
            if (((StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class)).getState() != 0) {
                ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).mShadeSurface.closeQsIfPossible();
            } else {
                ((ShadeController) Dependency.sDependency.getDependencyInner(ShadeController.class)).animateCollapseShadeForcedDelayed();
            }
        }
    }

    public final void dismiss() {
        SysUiState flag = this.mSysUiState.setFlag(32768L, false);
        ((SamsungGlobalActionsDialogBase) this).mContext.getDisplayId();
        ((SysUiStateImpl) flag).commitUpdate();
        super.dismiss();
    }

    public final void showDialog() {
        ((SamsungGlobalActionsDialogBase) this).mContentViewFactory = new ContentViewFactory(((SamsungGlobalActionsDialogBase) this).mContext, this, ((SamsungGlobalActionsDialogBase) this).mFeatureFactory, ((SamsungGlobalActionsDialogBase) this).mConditionChecker, ((SamsungGlobalActionsDialogBase) this).mWindowManagerUtil, ((SamsungGlobalActionsDialogBase) this).mResourceFactory, this.mCoverUtilWrapper, ((SamsungGlobalActionsDialogBase) this).mLogWrapper, ((SamsungGlobalActionsDialogBase) this).mHandlerUtil, ((SamsungGlobalActionsDialogBase) this).mToastController, ((SamsungGlobalActionsDialogBase) this).mPresenter, ((SamsungGlobalActionsDialogBase) this).mFromSystemServer);
        SysUiState flag = this.mSysUiState.setFlag(32768L, true);
        ((SamsungGlobalActionsDialogBase) this).mContext.getDisplayId();
        ((SysUiStateImpl) flag).commitUpdate();
        super.showDialog();
    }
}
