package com.android.systemui.popup.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.view.DataConnectionDataLimitDialog;
import com.android.systemui.popup.view.DataConnectionErrorDialog;
import com.android.systemui.popup.view.PopupUIAlertDialog;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DataConnectionViewModel implements PopupUIViewModel {
    public final Context mContext;
    public PopupUIAlertDialog mDataConnectionErrorDialog;
    public final PopupUIAlertDialogFactory mDialogFactory;
    public boolean mHasVoiceCallingFeature;
    public final PopupUIIntentWrapper mIntentWrapper;
    public final LogWrapper mLogWrapper;
    public final PopupUIToastWrapper mToastWrapper;
    public final PopupUIUtil mUtil;

    public DataConnectionViewModel(Context context, PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, PopupUIUtil popupUIUtil, PopupUIAlertDialogFactory popupUIAlertDialogFactory) {
        this.mContext = context;
        this.mToastWrapper = popupUIToastWrapper;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
        this.mUtil = popupUIUtil;
        this.mDialogFactory = popupUIAlertDialogFactory;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final void dismiss() {
        PopupUIAlertDialog popupUIAlertDialog = this.mDataConnectionErrorDialog;
        if (popupUIAlertDialog != null) {
            popupUIAlertDialog.dismiss();
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final String getAction() {
        return "com.samsung.systemui.popup.intent.DATA_CONNECTION_ERROR";
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00aa, code lost:
    
        if (r11 != 4) goto L44;
     */
    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(Intent intent) {
        this.mIntentWrapper.getClass();
        if (intent.getAction().equals("com.samsung.systemui.popup.intent.DATA_CONNECTION_ERROR")) {
            Context context = this.mContext;
            this.mHasVoiceCallingFeature = ((TelephonyManager) context.getSystemService("phone")).getPhoneType() != 0;
            this.mUtil.getClass();
            int i = 0;
            for (int i2 = 0; i2 < DeviceState.sPhoneCount; i2++) {
                String mSimSystemProperty = DeviceState.getMSimSystemProperty(i2, "gsm.sim.state", "NOT_READY");
                if ("READY".equals(mSimSystemProperty) || "LOADED".equals(mSimSystemProperty)) {
                    i++;
                }
            }
            boolean z = i == 0;
            LogWrapper logWrapper = this.mLogWrapper;
            if (z || !this.mHasVoiceCallingFeature) {
                logWrapper.m98d("DataConnectionViewModel", "Not ready to show DataConnectionErrorDialog()");
                Context context2 = this.mToastWrapper.mContext;
                Toast.makeText(context2, context2.getResources().getString(R.string.data_connection_error_toast_notification), 0).show();
                return;
            }
            int intExtra = intent.getIntExtra("type", -1);
            boolean booleanExtra = intent.getBooleanExtra("no_signal_retry_enable", false);
            PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("no_signal_retry_intent");
            StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("show : ", intExtra, ", ", booleanExtra, ", ");
            m76m.append(pendingIntent != null);
            logWrapper.m98d("DataConnectionViewModel", m76m.toString());
            PopupUIAlertDialogFactory popupUIAlertDialogFactory = this.mDialogFactory;
            popupUIAlertDialogFactory.initializeDialog();
            if (intExtra != -1) {
                if (intExtra != 0) {
                    if (intExtra == 1) {
                        popupUIAlertDialogFactory.mUtil.getClass();
                    } else if (intExtra != 2) {
                        if (intExtra == 3) {
                            popupUIAlertDialogFactory.mPopupUIAlertDialog = new DataConnectionDataLimitDialog(popupUIAlertDialogFactory.mContext, popupUIAlertDialogFactory.mLogWrapper);
                        }
                    }
                }
                popupUIAlertDialogFactory.mPopupUIAlertDialog = new DataConnectionErrorDialog(popupUIAlertDialogFactory.mContext, popupUIAlertDialogFactory.mLogWrapper, popupUIAlertDialogFactory.mShowingDialog, popupUIAlertDialogFactory.mDismissDialog, intExtra, booleanExtra, pendingIntent);
            } else {
                popupUIAlertDialogFactory.mPopupUIAlertDialog = null;
            }
            PopupUIAlertDialog popupUIAlertDialog = popupUIAlertDialogFactory.mPopupUIAlertDialog;
            this.mDataConnectionErrorDialog = popupUIAlertDialog;
            if (popupUIAlertDialog != null) {
                popupUIAlertDialog.show();
            } else {
                logWrapper.m98d("DataConnectionViewModel", "show() invalid AlertDialog");
            }
        }
    }
}
