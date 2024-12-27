package com.android.systemui.popup.viewmodel;

import android.content.Intent;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.view.PopupUIAlertDialog;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class MWOverheatWarningViewModel implements PopupUIViewModel {
    private static final String TAG = "MWOverheatWarningViewModel";
    private PopupUIAlertDialogFactory mDialogFactory;
    private PopupUIIntentWrapper mIntentWrapper;
    private LogWrapper mLogWrapper;
    private PopupUIAlertDialog mMWOverheatWarningDialog;

    public MWOverheatWarningViewModel(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        this.mDialogFactory = popupUIAlertDialogFactory;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void dismiss() {
        PopupUIAlertDialog popupUIAlertDialog = this.mMWOverheatWarningDialog;
        if (popupUIAlertDialog != null) {
            popupUIAlertDialog.dismiss();
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public String getAction() {
        return PopupUIUtil.ACTION_MULTI_WINDOW_ENABLE_CHANGED;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void show(Intent intent) {
        if (this.mIntentWrapper.getAction(intent).equals(getAction())) {
            String stringExtra = this.mIntentWrapper.getStringExtra(intent, PopupUIUtil.EXTRA_MULTI_WINDOW_ENABLE_REQUESTER);
            boolean booleanExtra = this.mIntentWrapper.getBooleanExtra(intent, PopupUIUtil.EXTRA_MULTI_WINDOW_ENABLED, true);
            boolean booleanExtra2 = this.mIntentWrapper.getBooleanExtra(intent, PopupUIUtil.EXTRA_IN_MULTI_WINDOW_MODE, false);
            LogWrapper logWrapper = this.mLogWrapper;
            StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("show : ", stringExtra, ", ", ", ", booleanExtra);
            m.append(booleanExtra2);
            logWrapper.d(TAG, m.toString());
            PopupUIAlertDialog overheatWarningDialog = this.mDialogFactory.getOverheatWarningDialog(stringExtra, booleanExtra, booleanExtra2);
            this.mMWOverheatWarningDialog = overheatWarningDialog;
            if (overheatWarningDialog != null) {
                overheatWarningDialog.show();
            } else {
                this.mLogWrapper.d(TAG, "show() invalid AlertDialog");
            }
        }
    }
}
