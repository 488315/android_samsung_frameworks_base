package com.android.systemui.popup.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.view.PopupUIAlertDialog;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DataConnectionViewModel implements PopupUIViewModel {
    private static final String TAG = "DataConnectionViewModel";
    private Context mContext;
    private PopupUIAlertDialog mDataConnectionErrorDialog;
    private PopupUIAlertDialogFactory mDialogFactory;
    private boolean mHasMobileDataFeature;
    private boolean mHasVoiceCallingFeature;
    private PopupUIIntentWrapper mIntentWrapper;
    private LogWrapper mLogWrapper;
    private PopupUIToastWrapper mToastWrapper;
    private PopupUIUtil mUtil;

    public DataConnectionViewModel(Context context, PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper, PopupUIUtil popupUIUtil, PopupUIAlertDialogFactory popupUIAlertDialogFactory) {
        this.mContext = context;
        this.mToastWrapper = popupUIToastWrapper;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
        this.mUtil = popupUIUtil;
        this.mDialogFactory = popupUIAlertDialogFactory;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void dismiss() {
        PopupUIAlertDialog popupUIAlertDialog = this.mDataConnectionErrorDialog;
        if (popupUIAlertDialog != null) {
            popupUIAlertDialog.dismiss();
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public String getAction() {
        return PopupUIUtil.ACTION_DATA_CONNECTION_ERROR;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void show(Intent intent) {
        if (this.mIntentWrapper.getAction(intent).equals(getAction())) {
            this.mHasVoiceCallingFeature = ((TelephonyManager) this.mContext.getSystemService("phone")).getPhoneType() != 0;
            if (this.mUtil.isNoReadySim() || !(this.mHasMobileDataFeature || this.mHasVoiceCallingFeature)) {
                this.mLogWrapper.d(TAG, "Not ready to show DataConnectionErrorDialog()");
                this.mToastWrapper.makeToast(R.string.data_connection_error_toast_notification);
                return;
            }
            int intExtra = this.mIntentWrapper.getIntExtra(intent, "type", -1);
            boolean booleanExtra = this.mIntentWrapper.getBooleanExtra(intent, PopupUIUtil.EXTRA_DATA_CONNECTION_ERROR_NO_SIGNAL_RETRY_ENABLE, false);
            PendingIntent parcelableExtra = this.mIntentWrapper.getParcelableExtra(intent, PopupUIUtil.EXTRA_DATA_CONNECTION_ERROR_NO_SIGNAL_RETRY_PENDING_INTENT);
            LogWrapper logWrapper = this.mLogWrapper;
            StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("show : ", intExtra, ", ", booleanExtra, ", ");
            m.append(parcelableExtra != null);
            logWrapper.d(TAG, m.toString());
            PopupUIAlertDialog dataConnectionDialog = this.mDialogFactory.getDataConnectionDialog(intExtra, booleanExtra, parcelableExtra);
            this.mDataConnectionErrorDialog = dataConnectionDialog;
            if (dataConnectionDialog != null) {
                dataConnectionDialog.show();
            } else {
                this.mLogWrapper.d(TAG, "show() invalid AlertDialog");
            }
        }
    }
}
