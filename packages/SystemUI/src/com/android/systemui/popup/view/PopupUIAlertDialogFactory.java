package com.android.systemui.popup.view;

import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.popup.util.PopupUIUtil;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PopupUIAlertDialogFactory {
    private static final String TAG = "PopupUIAlertDialogFactory";
    private Context mContext;
    private KeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;
    private LogWrapper mLogWrapper;
    private PopupUIUtil mUtil;
    private Runnable mShowingDialog = new Runnable() { // from class: com.android.systemui.popup.view.PopupUIAlertDialogFactory.1
        @Override // java.lang.Runnable
        public void run() {
            PopupUIAlertDialogFactory.this.mKeyguardUpdateMonitorWrapper.setDialogStateForInDisplayFingerprint(true);
        }
    };
    private Runnable mDismissDialog = new Runnable() { // from class: com.android.systemui.popup.view.PopupUIAlertDialogFactory.2
        @Override // java.lang.Runnable
        public void run() {
            PopupUIAlertDialogFactory.this.mKeyguardUpdateMonitorWrapper.setDialogStateForInDisplayFingerprint(false);
        }
    };
    private PopupUIAlertDialog mPopupUIAlertDialog = null;

    public PopupUIAlertDialogFactory(Context context, PopupUIUtil popupUIUtil, LogWrapper logWrapper, KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper) {
        this.mContext = context;
        this.mUtil = popupUIUtil;
        this.mLogWrapper = logWrapper;
        this.mKeyguardUpdateMonitorWrapper = keyguardUpdateMonitorWrapper;
    }

    private void initializeDialog() {
        PopupUIAlertDialog popupUIAlertDialog = this.mPopupUIAlertDialog;
        if (popupUIAlertDialog != null) {
            if (popupUIAlertDialog.isShowing()) {
                this.mPopupUIAlertDialog.dismiss();
            }
            this.mPopupUIAlertDialog = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0012, code lost:
    
        if (r10 != 4) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.systemui.popup.view.PopupUIAlertDialog getDataConnectionDialog(int r10, boolean r11, android.app.PendingIntent r12) {
        /*
            r9 = this;
            r9.initializeDialog()
            r0 = -1
            if (r10 == r0) goto L35
            if (r10 == 0) goto L21
            r0 = 1
            if (r10 == r0) goto L21
            r0 = 2
            if (r10 == r0) goto L21
            r0 = 3
            if (r10 == r0) goto L15
            r0 = 4
            if (r10 == r0) goto L21
            goto L38
        L15:
            com.android.systemui.popup.view.DataConnectionDataLimitDialog r10 = new com.android.systemui.popup.view.DataConnectionDataLimitDialog
            android.content.Context r11 = r9.mContext
            com.android.systemui.basic.util.LogWrapper r12 = r9.mLogWrapper
            r10.<init>(r11, r12)
            r9.mPopupUIAlertDialog = r10
            goto L38
        L21:
            com.android.systemui.popup.view.DataConnectionErrorDialog r8 = new com.android.systemui.popup.view.DataConnectionErrorDialog
            android.content.Context r1 = r9.mContext
            com.android.systemui.basic.util.LogWrapper r2 = r9.mLogWrapper
            java.lang.Runnable r3 = r9.mShowingDialog
            java.lang.Runnable r4 = r9.mDismissDialog
            r0 = r8
            r5 = r10
            r6 = r11
            r7 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r9.mPopupUIAlertDialog = r8
            goto L38
        L35:
            r10 = 0
            r9.mPopupUIAlertDialog = r10
        L38:
            com.android.systemui.popup.view.PopupUIAlertDialog r9 = r9.mPopupUIAlertDialog
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.popup.view.PopupUIAlertDialogFactory.getDataConnectionDialog(int, boolean, android.app.PendingIntent):com.android.systemui.popup.view.PopupUIAlertDialog");
    }

    public PopupUIAlertDialog getOverheatWarningDialog(String str, boolean z, boolean z2) {
        initializeDialog();
        if (!PopupUIUtil.ACTION_MULTI_WINDOW_ENABLE_VALID_REQUESTER.equals(str) || z || !z2) {
            return null;
        }
        MWOverheatWarningDialog mWOverheatWarningDialog = new MWOverheatWarningDialog(this.mContext, this.mLogWrapper);
        this.mPopupUIAlertDialog = mWOverheatWarningDialog;
        return mWOverheatWarningDialog;
    }

    public PopupUIAlertDialog getSimTrayProtectionDialog(int i, boolean z, int i2) {
        initializeDialog();
        SimTrayProtectionDialog simTrayProtectionDialog = new SimTrayProtectionDialog(this.mContext, this.mLogWrapper, i, z, i2);
        this.mPopupUIAlertDialog = simTrayProtectionDialog;
        return simTrayProtectionDialog;
    }
}
