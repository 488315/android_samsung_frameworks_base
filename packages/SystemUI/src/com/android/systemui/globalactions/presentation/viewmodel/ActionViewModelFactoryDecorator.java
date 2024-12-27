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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActionViewModelFactoryDecorator implements ActionViewModelFactory {
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
    public final ActionViewModel createActionViewModel(SamsungGlobalActions samsungGlobalActions, String str) {
        char c;
        ActionInfo actionInfo = new ActionInfo();
        str.getClass();
        switch (str.hashCode()) {
            case -1204036393:
                if (str.equals("pro_kiosk")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -404622368:
                if (str.equals("screen_capture_popup")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1091648491:
                if (str.equals("side_key_settings")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2137326724:
                if (str.equals("knox_custom")) {
                    c = 3;
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
                ProKioskActionViewModel proKioskActionViewModel = new ProKioskActionViewModel(samsungGlobalActions, (AlertDialogFactory) this.mUtilFactory.get(AlertDialogFactory.class), (SystemController) this.mUtilFactory.get(SystemController.class), (ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class), (InputMethodManagerWrapper) this.mUtilFactory.get(InputMethodManagerWrapper.class), this.mConditionChecker, (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), this.mSAnalytics);
                actionInfo.setName("pro_kiosk");
                actionInfo.setViewType(ViewType.CENTER_ICON_8P_VIEW);
                actionInfo.setIcon(this.mResourceFactory.get(ResourceType.DRAWABLE_PROKIOSK));
                proKioskActionViewModel.mInfo = actionInfo;
                proKioskActionViewModel.setState(ActionViewModel.ToggleState.on);
                return proKioskActionViewModel;
            case 1:
                ScreenCapturePopupActionViewModel screenCapturePopupActionViewModel = new ScreenCapturePopupActionViewModel((ScreenCapturePopupController) this.mUtilFactory.get(ScreenCapturePopupController.class));
                actionInfo.setName("screen_capture_popup");
                actionInfo.setViewType(ViewType.BOTTOM_POPUP_VIEW);
                screenCapturePopupActionViewModel.mInfo = actionInfo;
                return screenCapturePopupActionViewModel;
            case 2:
                SideKeyActionViewModel sideKeyActionViewModel = new SideKeyActionViewModel((Context) this.mUtilFactory.get(Context.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), this.mFeatureFactory, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ActivityStarterWrapper) this.mUtilFactory.get(ActivityStarterWrapper.class), this.mConditionChecker, this.mSAnalytics);
                actionInfo.setName("side_key_settings");
                actionInfo.setStateLabel(((Context) this.mUtilFactory.get(Context.class)).getResources().getString(R.string.mediasize_na_quarto));
                actionInfo.setIcon(-1);
                actionInfo.setViewType(ViewType.KEY_SETTINGS_VIEW);
                sideKeyActionViewModel.mInfo = actionInfo;
                return sideKeyActionViewModel;
            case 3:
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
