package com.android.systemui.popup.viewmodel;

import android.content.Intent;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIIntentWrapper;
import com.android.systemui.popup.view.MWOverheatWarningDialog;
import com.android.systemui.popup.view.PopupUIAlertDialogFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MWOverheatWarningViewModel implements PopupUIViewModel {
    public final PopupUIAlertDialogFactory mDialogFactory;
    public final PopupUIIntentWrapper mIntentWrapper;
    public final LogWrapper mLogWrapper;
    public MWOverheatWarningDialog mMWOverheatWarningDialog;

    public MWOverheatWarningViewModel(PopupUIAlertDialogFactory popupUIAlertDialogFactory, LogWrapper logWrapper, PopupUIIntentWrapper popupUIIntentWrapper) {
        this.mDialogFactory = popupUIAlertDialogFactory;
        this.mLogWrapper = logWrapper;
        this.mIntentWrapper = popupUIIntentWrapper;
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final void dismiss() {
        MWOverheatWarningDialog mWOverheatWarningDialog = this.mMWOverheatWarningDialog;
        if (mWOverheatWarningDialog != null) {
            mWOverheatWarningDialog.dismiss();
        }
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final String getAction() {
        return "com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED";
    }

    @Override // com.android.systemui.popup.viewmodel.PopupUIViewModel
    public final void show(Intent intent) {
        MWOverheatWarningDialog mWOverheatWarningDialog;
        this.mIntentWrapper.getClass();
        if (intent.getAction().equals("com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED")) {
            String stringExtra = intent.getStringExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLE_REQUESTER");
            boolean booleanExtra = intent.getBooleanExtra("com.samsung.android.extra.MULTI_WINDOW_ENABLED", true);
            boolean booleanExtra2 = intent.getBooleanExtra("com.samsung.android.extra.IN_MULTI_WINDOW_MODE", false);
            LogWrapper logWrapper = this.mLogWrapper;
            logWrapper.m98d("MWOverheatWarningViewModel", "show : " + stringExtra + ", " + booleanExtra + ", " + booleanExtra2);
            PopupUIAlertDialogFactory popupUIAlertDialogFactory = this.mDialogFactory;
            popupUIAlertDialogFactory.initializeDialog();
            if ("SSRM".equals(stringExtra) && !booleanExtra && booleanExtra2) {
                mWOverheatWarningDialog = new MWOverheatWarningDialog(popupUIAlertDialogFactory.mContext, popupUIAlertDialogFactory.mLogWrapper);
                popupUIAlertDialogFactory.mPopupUIAlertDialog = mWOverheatWarningDialog;
            } else {
                mWOverheatWarningDialog = null;
            }
            this.mMWOverheatWarningDialog = mWOverheatWarningDialog;
            if (mWOverheatWarningDialog != null) {
                mWOverheatWarningDialog.show();
            } else {
                logWrapper.m98d("MWOverheatWarningViewModel", "show() invalid AlertDialog");
            }
        }
    }
}
