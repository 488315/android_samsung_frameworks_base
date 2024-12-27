package com.android.systemui.globalactions.presentation.viewmodel;

import android.R;
import android.app.AlertDialog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.util.ProKioskManagerWrapper;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.viewmodel.ActionInfo;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.AlertDialogFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.InputMethodManagerWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SamsungGlobalActionsAnalytics;
import com.samsung.android.globalactions.util.SystemController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ProKioskActionViewModel implements ActionViewModel {
    public final AlertDialogFactory mAlertDialogFactory;
    public final ConditionChecker mConditionChecker;
    public final SamsungGlobalActions mGlobalActions;
    public ActionInfo mInfo;
    public final InputMethodManagerWrapper mInputMethodManagerWrapper;
    public final ProKioskManagerWrapper mProKioskManagerWrapper;
    public final ResourcesWrapper mResourcesWrapper;
    public final SamsungGlobalActionsAnalytics mSAnalytics;
    public final SystemController mSystemController;
    public ActionViewModel.ToggleState mToggleState;

    public ProKioskActionViewModel(SamsungGlobalActions samsungGlobalActions, AlertDialogFactory alertDialogFactory, SystemController systemController, ProKioskManagerWrapper proKioskManagerWrapper, InputMethodManagerWrapper inputMethodManagerWrapper, ConditionChecker conditionChecker, ResourcesWrapper resourcesWrapper, SamsungGlobalActionsAnalytics samsungGlobalActionsAnalytics) {
        this.mAlertDialogFactory = alertDialogFactory;
        this.mSystemController = systemController;
        this.mProKioskManagerWrapper = proKioskManagerWrapper;
        this.mInputMethodManagerWrapper = inputMethodManagerWrapper;
        this.mGlobalActions = samsungGlobalActions;
        this.mConditionChecker = conditionChecker;
        this.mResourcesWrapper = resourcesWrapper;
        this.mSAnalytics = samsungGlobalActionsAnalytics;
    }

    public final ActionInfo getActionInfo() {
        return this.mInfo;
    }

    public final void onPress() {
        if (this.mConditionChecker.isEnabled(SystemUIConditions.GET_PROKIOSK_STATE)) {
            this.mSAnalytics.sendEventLog("611", "6111", "Pro kiosk", 7L);
            final int i = 0;
            final int i2 = 1;
            AlertDialog proKioskModeDialog = this.mAlertDialogFactory.getProKioskModeDialog(new Runnable(this) { // from class: com.android.systemui.globalactions.presentation.viewmodel.ProKioskActionViewModel$$ExternalSyntheticLambda0
                public final /* synthetic */ ProKioskActionViewModel f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i3;
                    int i4 = i;
                    ProKioskActionViewModel proKioskActionViewModel = this.f$0;
                    switch (i4) {
                        case 0:
                            String proKioskPasswordText = proKioskActionViewModel.mAlertDialogFactory.getProKioskPasswordText();
                            ProKioskManagerWrapper proKioskManagerWrapper = proKioskActionViewModel.mProKioskManagerWrapper;
                            try {
                                i3 = proKioskManagerWrapper.mProKioskManager.stopProKioskMode(proKioskPasswordText);
                            } catch (Exception e) {
                                proKioskManagerWrapper.mLogWrapper.e("ProKioskManagerWrapper", "setProKioskState() : Exception = " + e);
                                i3 = -1;
                            }
                            proKioskActionViewModel.mInputMethodManagerWrapper.hideSoftInputFromWindow(proKioskActionViewModel.mAlertDialogFactory.getProKioskPasswordWindowToken());
                            if (i3 == 0) {
                                proKioskActionViewModel.mSystemController.goToHome();
                                break;
                            }
                            break;
                        default:
                            proKioskActionViewModel.mInputMethodManagerWrapper.hideSoftInputFromWindow(proKioskActionViewModel.mAlertDialogFactory.getProKioskPasswordWindowToken());
                            break;
                    }
                }
            }, new Runnable(this) { // from class: com.android.systemui.globalactions.presentation.viewmodel.ProKioskActionViewModel$$ExternalSyntheticLambda0
                public final /* synthetic */ ProKioskActionViewModel f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i3;
                    int i4 = i2;
                    ProKioskActionViewModel proKioskActionViewModel = this.f$0;
                    switch (i4) {
                        case 0:
                            String proKioskPasswordText = proKioskActionViewModel.mAlertDialogFactory.getProKioskPasswordText();
                            ProKioskManagerWrapper proKioskManagerWrapper = proKioskActionViewModel.mProKioskManagerWrapper;
                            try {
                                i3 = proKioskManagerWrapper.mProKioskManager.stopProKioskMode(proKioskPasswordText);
                            } catch (Exception e) {
                                proKioskManagerWrapper.mLogWrapper.e("ProKioskManagerWrapper", "setProKioskState() : Exception = " + e);
                                i3 = -1;
                            }
                            proKioskActionViewModel.mInputMethodManagerWrapper.hideSoftInputFromWindow(proKioskActionViewModel.mAlertDialogFactory.getProKioskPasswordWindowToken());
                            if (i3 == 0) {
                                proKioskActionViewModel.mSystemController.goToHome();
                                break;
                            }
                            break;
                        default:
                            proKioskActionViewModel.mInputMethodManagerWrapper.hideSoftInputFromWindow(proKioskActionViewModel.mAlertDialogFactory.getProKioskPasswordWindowToken());
                            break;
                    }
                }
            });
            String exitUI = this.mProKioskManagerWrapper.mProKioskManager.getExitUI(221);
            String exitUI2 = this.mProKioskManagerWrapper.mProKioskManager.getExitUI(222);
            ActionViewModel.ToggleState toggleState = this.mToggleState;
            ActionViewModel.ToggleState toggleState2 = ActionViewModel.ToggleState.on;
            if (toggleState == toggleState2) {
                this.mToggleState = ActionViewModel.ToggleState.off;
            } else {
                this.mToggleState = toggleState2;
            }
            if (exitUI == null || exitUI2 == null || exitUI.isEmpty() || exitUI2.isEmpty()) {
                proKioskModeDialog.show();
            } else {
                if (!exitUI2.startsWith(exitUI)) {
                    exitUI2 = exitUI2.startsWith(".") ? exitUI.concat(exitUI2) : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(exitUI, ".", exitUI2);
                }
                if (!this.mSystemController.startProKioskExitUI(exitUI, exitUI2)) {
                    proKioskModeDialog.show();
                }
            }
            this.mGlobalActions.dismissDialog(true);
        }
    }

    public final void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }

    public final void setState(ActionViewModel.ToggleState toggleState) {
        this.mToggleState = toggleState;
        String proKioskString = this.mProKioskManagerWrapper.mProKioskManager.getProKioskString(111);
        String proKioskString2 = this.mProKioskManagerWrapper.mProKioskManager.getProKioskString(112);
        String proKioskString3 = this.mProKioskManagerWrapper.mProKioskManager.getProKioskString(113);
        if (proKioskString != null) {
            this.mInfo.setLabel(proKioskString);
        } else {
            this.mInfo.setLabel(this.mResourcesWrapper.getString(R.string.mediasize_na_arch_e));
        }
        if (this.mConditionChecker.isEnabled(SystemUIConditions.GET_PROKIOSK_STATE)) {
            if (proKioskString2 != null) {
                this.mInfo.setStateLabel(proKioskString2);
                return;
            } else {
                this.mInfo.setStateLabel(this.mResourcesWrapper.getString(R.string.mediasize_japanese_jis_exec));
                return;
            }
        }
        if (proKioskString3 != null) {
            this.mInfo.setStateLabel(proKioskString3);
        } else {
            this.mInfo.setStateLabel(this.mResourcesWrapper.getString(R.string.mediasize_japanese_jis_b9));
        }
    }

    public final boolean showBeforeProvisioning() {
        return true;
    }
}
