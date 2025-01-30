package com.android.systemui.globalactions.features;

import android.R;
import android.view.Window;
import android.view.WindowInsets;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.android.systemui.util.CoverUtil;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.strategies.DefaultActionsCreationStrategy;
import com.samsung.android.globalactions.presentation.strategies.DisposingStrategy;
import com.samsung.android.globalactions.presentation.strategies.InitializationStrategy;
import com.samsung.android.globalactions.presentation.strategies.SecureConfirmStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import java.util.HashMap;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverSupportStrategy implements DefaultActionsCreationStrategy, InitializationStrategy, DisposingStrategy, WindowManagerFunctionStrategy, SecureConfirmStrategy, WindowDecorationStrategy {
    public final ConditionChecker mConditionChecker;
    public final CoverUtilWrapper mCoverUtilWrapper;
    public final SamsungGlobalActions mGlobalActions;
    public final KeyGuardManagerWrapper mKeyGuardManagerWrapper;
    public final LogWrapper mLogWrapper;
    public final ResourcesWrapper mResourceWrapper;
    public final ToastController mToastController;

    public CoverSupportStrategy(ConditionChecker conditionChecker, CoverUtilWrapper coverUtilWrapper, SamsungGlobalActions samsungGlobalActions, LogWrapper logWrapper, KeyGuardManagerWrapper keyGuardManagerWrapper, ToastController toastController, ResourcesWrapper resourcesWrapper) {
        this.mConditionChecker = conditionChecker;
        this.mCoverUtilWrapper = coverUtilWrapper;
        this.mGlobalActions = samsungGlobalActions;
        this.mLogWrapper = logWrapper;
        this.mKeyGuardManagerWrapper = keyGuardManagerWrapper;
        this.mToastController = toastController;
        this.mResourceWrapper = resourcesWrapper;
    }

    public final boolean doActionBeforeSecureConfirm(final ActionViewModel actionViewModel, SamsungGlobalActions samsungGlobalActions) {
        final String str = actionViewModel.getActionInfo().getName() == "power" ? "shutdown" : "reboot";
        String name = actionViewModel.getActionInfo().getName();
        name.getClass();
        if (name.equals("power") || name.equals("restart")) {
            ConditionChecker conditionChecker = this.mConditionChecker;
            SystemUIConditions systemUIConditions = SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED;
            if (conditionChecker.isEnabled(systemUIConditions) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) {
                this.mToastController.showToast(this.mResourceWrapper.getString(actionViewModel.getActionInfo().getName() == "power" ? R.string.mediasize_iso_c9 : R.string.mediasize_japanese_chou4), 1);
            }
            if (this.mConditionChecker.isEnabled(systemUIConditions)) {
                this.mToastController.showToast(this.mResourceWrapper.getString(actionViewModel.getActionInfo().getName() == "power" ? R.string.mediasize_iso_b7 : R.string.mediasize_iso_b8), 1);
            }
            if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED) || this.mConditionChecker.isEnabled(systemUIConditions) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) {
                this.mCoverUtilWrapper.mActionBeforeSecureConfirm = new Runnable() { // from class: com.android.systemui.globalactions.features.CoverSupportStrategy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CoverSupportStrategy coverSupportStrategy = CoverSupportStrategy.this;
                        ActionViewModel actionViewModel2 = actionViewModel;
                        String str2 = str;
                        coverSupportStrategy.mKeyGuardManagerWrapper.setRegisterState(false);
                        coverSupportStrategy.mGlobalActions.registerSecureConfirmAction(actionViewModel2);
                        if (coverSupportStrategy.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD)) {
                            coverSupportStrategy.mKeyGuardManagerWrapper.setPendingIntentAfterUnlock(str2);
                        }
                    }
                };
                this.mKeyGuardManagerWrapper.setRegisterState(true);
            }
        }
        return true;
    }

    public final boolean onCreateBugReportAction() {
        return (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) ? false : true;
    }

    public final boolean onCreateEmergencyAction() {
        return (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) ? false : true;
    }

    public final boolean onCreateEmergencyCallAction() {
        return (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) ? false : true;
    }

    public final boolean onCreateMedicalInfoAction() {
        return (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) ? false : true;
    }

    public final void onDecorateWindow(Window window) {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED)) {
            window.getDecorView().getWindowInsetsController().hide(WindowInsets.Type.navigationBars());
        }
    }

    public final void onDispose() {
        CoverUtilWrapper coverUtilWrapper = this.mCoverUtilWrapper;
        ((HashMap) coverUtilWrapper.mListeners).remove(ModuleType.GLOBALACTIONS);
    }

    public final void onInitialize(boolean z) {
        CoverUtilWrapper coverUtilWrapper = this.mCoverUtilWrapper;
        CoverUtil coverUtil = coverUtilWrapper.mCoverUtil;
        if (coverUtil != null) {
            coverUtilWrapper.mCoverState = coverUtil.mCoverState;
        }
        ((HashMap) coverUtilWrapper.mListeners).put(ModuleType.GLOBALACTIONS, new BiConsumer() { // from class: com.android.systemui.globalactions.features.CoverSupportStrategy$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                CoverSupportStrategy.this.mGlobalActions.dismissDialog(false);
            }
        });
        this.mKeyGuardManagerWrapper.setRegisterState(false);
    }

    public final void onReboot() {
        if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED)) {
            this.mLogWrapper.v("CoverSupportStrategy", "onReboot");
        }
    }

    public final void onShutdown() {
        if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED)) {
            this.mLogWrapper.v("CoverSupportStrategy", "onShutdown");
        }
    }
}
