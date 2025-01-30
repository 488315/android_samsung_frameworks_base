package com.android.systemui.popup.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.SemSystemProperties;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import com.android.systemui.popup.view.SimTrayProtectionDialog;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SimTrayProtectionViewModel implements PopupUIViewModel, WakefulnessLifecycle.Observer {
    public final PopupUIAlertDialogFactory mDialogFactory;
    public final PopupUIIntentWrapper mIntentWrapper;
    public final LogWrapper mLogWrapper;
    public SimTrayProtectionDialog mSimTrayProtectionDialog;
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public boolean mRemoveSimTray = true;
    public final C19281 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.popup.viewmodel.SimTrayProtectionViewModel.1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            SimTrayProtectionViewModel simTrayProtectionViewModel = SimTrayProtectionViewModel.this;
            SimTrayProtectionDialog simTrayProtectionDialog = simTrayProtectionViewModel.mSimTrayProtectionDialog;
            if (simTrayProtectionDialog == null || !simTrayProtectionDialog.isShowing()) {
                return;
            }
            Log.d("SimTrayProtectionViewModel", "onFinishedGoingToSleep");
            simTrayProtectionViewModel.mRemoveSimTray = false;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            SimTrayProtectionViewModel simTrayProtectionViewModel = SimTrayProtectionViewModel.this;
            SimTrayProtectionDialog simTrayProtectionDialog = simTrayProtectionViewModel.mSimTrayProtectionDialog;
            if (simTrayProtectionDialog == null || !simTrayProtectionDialog.isShowing()) {
                return;
            }
            Log.d("SimTrayProtectionViewModel", "onStartedGoingToSleep");
            simTrayProtectionViewModel.mRemoveSimTray = false;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            Log.d("SimTrayProtectionViewModel", "onStartedWakingUp");
            SimTrayProtectionViewModel.this.mRemoveSimTray = true;
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.popup.viewmodel.SimTrayProtectionViewModel$1] */
    public SimTrayProtectionViewModel(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mDialogFactory = popupUIAlertDialogFactory;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
        this.mWakefulnessLifeCycle = wakefulnessLifecycle;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final void dismiss() {
        StringBuilder sb = new StringBuilder("dismiss : isFoldedState()");
        sb.append(SemWindowManager.getInstance().isFolded());
        sb.append("isRemoveSimtray() : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mRemoveSimTray, "SimTrayProtectionViewModel");
        SimTrayProtectionDialog simTrayProtectionDialog = this.mSimTrayProtectionDialog;
        if (simTrayProtectionDialog == null || !this.mRemoveSimTray) {
            return;
        }
        simTrayProtectionDialog.dismiss();
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final String getAction() {
        return "com.samsung.systemui.popup.intent.SIM_CARD_TRAY_PROTECTION_POPUP";
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final void show(Intent intent) {
        Context context;
        this.mIntentWrapper.getClass();
        if (intent.getAction().equals("com.samsung.systemui.popup.intent.SIM_CARD_TRAY_PROTECTION_POPUP")) {
            boolean z = Feature.FEATURE_CONTEXTSERVICE_ENABLE_SURVEY;
            if ("factory".equalsIgnoreCase(SemSystemProperties.get("ro.factory.factory_binary", "Unknown"))) {
                return;
            }
            boolean booleanExtra = intent.getBooleanExtra("dismiss", false);
            this.mLogWrapper.m98d("SimTrayProtectionViewModel", booleanExtra ? "dismiss" : "show");
            if (booleanExtra) {
                this.mRemoveSimTray = true;
                dismiss();
                return;
            }
            int intExtra = intent.getIntExtra("type", 0);
            boolean booleanExtra2 = intent.getBooleanExtra("waterproof", false);
            int intExtra2 = intent.getIntExtra("tray", 1);
            WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
            if (wakefulnessLifecycle != null) {
                wakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
            }
            PopupUIAlertDialogFactory popupUIAlertDialogFactory = this.mDialogFactory;
            popupUIAlertDialogFactory.initializeDialog();
            if (!BasicRune.POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG || (context = popupUIAlertDialogFactory.mSubscreenContext) == null || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                context = popupUIAlertDialogFactory.mContext;
            }
            SimTrayProtectionDialog simTrayProtectionDialog = new SimTrayProtectionDialog(context, popupUIAlertDialogFactory.mLogWrapper, intExtra, booleanExtra2, intExtra2);
            popupUIAlertDialogFactory.mPopupUIAlertDialog = simTrayProtectionDialog;
            this.mSimTrayProtectionDialog = simTrayProtectionDialog;
            simTrayProtectionDialog.show();
        }
    }
}
