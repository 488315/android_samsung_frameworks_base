package com.android.systemui.popup.viewmodel;

import android.content.Intent;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.util.PopupUIToastWrapper;
import com.android.systemui.popup.util.PopupUIUtil;

public class MobileDeviceWarningViewModel implements PopupUIViewModel {
    private static final String TAG = "MobileDeviceWarningViewModel";
    private PopupUIIntentWrapper mIntentWrapper;
    private LogWrapper mLogWrapper;
    boolean mPopupCompleted = false;
    private PopupUIToastWrapper mToastWrapper;

    public MobileDeviceWarningViewModel(PopupUIToastWrapper popupUIToastWrapper, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        this.mToastWrapper = popupUIToastWrapper;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public String getAction() {
        return PopupUIUtil.ACTION_BOOT_COMPLETED;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void show(Intent intent) {
        if (this.mIntentWrapper.getAction(intent).equals(getAction())) {
            if (this.mPopupCompleted) {
                this.mLogWrapper.d(TAG, "makeToast one only time");
            } else {
                this.mPopupCompleted = true;
                this.mToastWrapper.makeToast(R.string.mobile_device_warning_toast_notification);
            }
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public void dismiss() {
    }
}
