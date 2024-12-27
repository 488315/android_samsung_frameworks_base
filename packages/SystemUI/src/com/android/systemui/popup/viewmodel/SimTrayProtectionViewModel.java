package com.android.systemui.popup.viewmodel;

import android.content.Intent;
import android.os.SemSystemProperties;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.view.PopupUIAlertDialog;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class SimTrayProtectionViewModel implements PopupUIViewModel, WakefulnessLifecycle.Observer {
    private static final String TAG = "SimTrayProtectionViewModel";
    private PopupUIAlertDialogFactory mDialogFactory;
    private PopupUIIntentWrapper mIntentWrapper;
    private LogWrapper mLogWrapper;
    private PopupUIAlertDialog mSimTrayProtectionDialog;
    private final WakefulnessLifecycle mWakefulnessLifeCycle;
    private boolean mRemoveSimTray = true;
    private final WakefulnessLifecycle.Observer mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.popup.viewmodel.SimTrayProtectionViewModel.1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onFinishedGoingToSleep() {
            if (SimTrayProtectionViewModel.this.mSimTrayProtectionDialog == null || !SimTrayProtectionViewModel.this.mSimTrayProtectionDialog.isShowing()) {
                return;
            }
            Log.d(SimTrayProtectionViewModel.TAG, "onFinishedGoingToSleep");
            SimTrayProtectionViewModel.this.updateRemoveSimTrayStatus(false);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onStartedGoingToSleep() {
            if (SimTrayProtectionViewModel.this.mSimTrayProtectionDialog == null || !SimTrayProtectionViewModel.this.mSimTrayProtectionDialog.isShowing()) {
                return;
            }
            Log.d(SimTrayProtectionViewModel.TAG, "onStartedGoingToSleep");
            SimTrayProtectionViewModel.this.updateRemoveSimTrayStatus(false);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onStartedWakingUp() {
            Log.d(SimTrayProtectionViewModel.TAG, "onStartedWakingUp");
            SimTrayProtectionViewModel.this.updateRemoveSimTrayStatus(true);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public /* bridge */ /* synthetic */ void onFinishedWakingUp() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public /* bridge */ /* synthetic */ void onPostFinishedWakingUp() {
        }
    };

    public SimTrayProtectionViewModel(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, WakefulnessLifecycle wakefulnessLifecycle) {
        this.mDialogFactory = popupUIAlertDialogFactory;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
        this.mWakefulnessLifeCycle = wakefulnessLifecycle;
    }

    private boolean isFoldedState() {
        return SemWindowManager.getInstance().isFolded();
    }

    private boolean isRemoveSimtray() {
        return this.mRemoveSimTray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoveSimTrayStatus(boolean z) {
        this.mRemoveSimTray = z;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void dismiss() {
        StringBuilder sb = new StringBuilder("dismiss : isFoldedState()");
        sb.append(isFoldedState());
        sb.append("isRemoveSimtray() : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, isRemoveSimtray(), TAG);
        if (this.mSimTrayProtectionDialog == null || !isRemoveSimtray()) {
            return;
        }
        this.mSimTrayProtectionDialog.dismiss();
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public String getAction() {
        return PopupUIUtil.ACTION_SIM_CARD_TRAY_PROTECTION_POPUP;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void show(Intent intent) {
        if (this.mIntentWrapper.getAction(intent).equals(getAction())) {
            boolean z = Feature.FEATURE_CONTEXTSERVICE_ENABLE_SURVEY;
            if ("factory".equalsIgnoreCase(SemSystemProperties.get("ro.factory.factory_binary", "Unknown")) && PopupUIUtil.ACTION_SIM_CARD_TRAY_PROTECTION_POPUP.equals(getAction())) {
                return;
            }
            PopupUIIntentWrapper popupUIIntentWrapper = this.mIntentWrapper;
            String str = PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS;
            boolean booleanExtra = popupUIIntentWrapper.getBooleanExtra(intent, PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS, false);
            if (!booleanExtra) {
                str = "show";
            }
            this.mLogWrapper.d(TAG, str);
            if (booleanExtra) {
                updateRemoveSimTrayStatus(true);
                dismiss();
                return;
            }
            int intExtra = this.mIntentWrapper.getIntExtra(intent, "type", 0);
            boolean booleanExtra2 = this.mIntentWrapper.getBooleanExtra(intent, PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_WATERPROOF, false);
            int intExtra2 = this.mIntentWrapper.getIntExtra(intent, PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_STYLE, 1);
            WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
            if (wakefulnessLifecycle != null) {
                wakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
            }
            PopupUIAlertDialog simTrayProtectionDialog = this.mDialogFactory.getSimTrayProtectionDialog(intExtra, booleanExtra2, intExtra2);
            this.mSimTrayProtectionDialog = simTrayProtectionDialog;
            simTrayProtectionDialog.show();
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public /* bridge */ /* synthetic */ void onFinishedGoingToSleep() {
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public /* bridge */ /* synthetic */ void onFinishedWakingUp() {
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public /* bridge */ /* synthetic */ void onPostFinishedWakingUp() {
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public /* bridge */ /* synthetic */ void onStartedGoingToSleep() {
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public /* bridge */ /* synthetic */ void onStartedWakingUp() {
    }
}
