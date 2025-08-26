package com.android.systemui.globalactions.presentation.viewmodel;

import android.R;
import android.content.Context;
import com.android.systemui.globalactions.util.ActivityStarterWrapper;
import com.android.systemui.globalactions.util.ProKioskManagerWrapper;
import com.android.systemui.globalactions.util.ScreenCapturePopupController;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.samsung.android.globalactions.util.AlertDialogFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.InputMethodManagerWrapper;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.UtilFactory;

/* loaded from: classes2.dex */
public class ActionViewModelFactoryDecorator implements ActionViewModelFactory {
    public final ConditionChecker mConditionChecker;
    public final ActionViewModelFactory mDecoratedFactory;
    public FeatureFactory mFeatureFactory;
    public final ResourceFactory mResourceFactory;
    public final SamsungGlobalActionsAnalytics mSAnalytics;
    public final UtilFactory mUtilFactory;

    public ActionViewModelFactoryDecorator(ActionViewModelFactory actionViewModelFactory, UtilFactory utilFactory, ResourceFactory resourceFactory, ConditionChecker conditionChecker, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
        this.mDecoratedFactory = actionViewModelFactory;
        this.mUtilFactory = utilFactory;
        this.mResourceFactory = resourceFactory;
        this.mConditionChecker = conditionChecker;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ActionViewModel createActionViewModel(SamsungGlobalActions samsungGlobalActions, String str) {
        ActionInfo actionInfo;
        actionInfo = new ActionInfo();
        str.getClass();
        switch (str) {
            case "pro_kiosk":
                ProKioskActionViewModel proKioskActionViewModel = new ProKioskActionViewModel(samsungGlobalActions, (AlertDialogFactory) this.mUtilFactory.get(AlertDialogFactory.class), (SystemController) this.mUtilFactory.get(SystemController.class), (ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class), (InputMethodManagerWrapper) this.mUtilFactory.get(InputMethodManagerWrapper.class), this.mConditionChecker, (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), this.mSAnalytics);
                actionInfo.setName("pro_kiosk");
                actionInfo.setViewType(ViewType.CENTER_ICON_8P_VIEW);
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_PROKIOSK));
                proKioskActionViewModel.mInfo = actionInfo;
                proKioskActionViewModel.setState(ActionViewModel.ToggleState.on);
                return proKioskActionViewModel;
            case "screen_capture_popup":
                ScreenCapturePopupActionViewModel screenCapturePopupActionViewModel = new ScreenCapturePopupActionViewModel((ScreenCapturePopupController) this.mUtilFactory.get(ScreenCapturePopupController.class));
                actionInfo.setName("screen_capture_popup");
                actionInfo.setViewType(ViewType.BOTTOM_POPUP_VIEW);
                screenCapturePopupActionViewModel.mInfo = actionInfo;
                return screenCapturePopupActionViewModel;
            case "side_key_settings":
                SideKeyActionViewModel sideKeyActionViewModel = new SideKeyActionViewModel((Context) this.mUtilFactory.get(Context.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), this.mFeatureFactory, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ActivityStarterWrapper) this.mUtilFactory.get(ActivityStarterWrapper.class), this.mConditionChecker, this.mSAnalytics);
                actionInfo.setName("side_key_settings");
                actionInfo.setStateLabel(((Context) this.mUtilFactory.get(Context.class)).getResources().getString(R.string.mmcc_authentication_reject_msim_template));
                actionInfo.setIcon(-1);
                actionInfo.setViewType(ViewType.KEY_SETTINGS_VIEW);
                sideKeyActionViewModel.mInfo = actionInfo;
                return sideKeyActionViewModel;
            case "knox_custom":
                KnoxCustomActionViewModel knoxCustomActionViewModel = new KnoxCustomActionViewModel((Context) this.mUtilFactory.get(Context.class), samsungGlobalActions);
                actionInfo.setName("knox_custom");
                actionInfo.setViewType(ViewType.CENTER_ICON_CUSTOM_VIEW);
                knoxCustomActionViewModel.mActionInfo = actionInfo;
                return knoxCustomActionViewModel;
            default:
                return this.mDecoratedFactory.createActionViewModel(samsungGlobalActions, str);
        }
    }
}
