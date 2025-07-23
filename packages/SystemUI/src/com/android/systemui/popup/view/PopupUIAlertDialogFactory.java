package com.android.systemui.popup.view;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.popup.util.DisplayManagerWrapper;
import com.android.systemui.popup.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.qp.SubscreenQsPanelController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PopupUIAlertDialogFactory {
    private static final String TAG = "PopupUIAlertDialogFactory";
    private Context mContext;
    private DisplayManagerWrapper mDisplayManagerWrapper;
    private KeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;
    private LogWrapper mLogWrapper;
    private PopupUIAlertDialog mPopupUIAlertDialog;
    private Context mSubscreenContext;
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

    public PopupUIAlertDialogFactory(Context context, PopupUIUtil popupUIUtil, LogWrapper logWrapper, KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper, DisplayManagerWrapper displayManagerWrapper) {
        this.mContext = context;
        if (BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP) {
            this.mSubscreenContext = ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext;
        }
        this.mUtil = popupUIUtil;
        this.mLogWrapper = logWrapper;
        this.mKeyguardUpdateMonitorWrapper = keyguardUpdateMonitorWrapper;
        this.mDisplayManagerWrapper = displayManagerWrapper;
        this.mPopupUIAlertDialog = null;
    }

    private Context getContext() {
        return (!BasicRune.POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG || this.mSubscreenContext == null || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? this.mContext : this.mSubscreenContext;
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
    
        if (r9 != 4) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.systemui.popup.view.PopupUIAlertDialog getDataConnectionDialog(int r9, boolean r10, android.app.PendingIntent r11) {
        /*
            r8 = this;
            r8.initializeDialog()
            r0 = -1
            if (r9 == r0) goto L34
            if (r9 == 0) goto L21
            r0 = 1
            if (r9 == r0) goto L21
            r0 = 2
            if (r9 == r0) goto L21
            r0 = 3
            if (r9 == r0) goto L15
            r0 = 4
            if (r9 == r0) goto L21
            goto L37
        L15:
            com.android.systemui.popup.view.DataConnectionDataLimitDialog r9 = new com.android.systemui.popup.view.DataConnectionDataLimitDialog
            android.content.Context r10 = r8.mContext
            com.android.systemui.basic.util.LogWrapper r11 = r8.mLogWrapper
            r9.<init>(r10, r11)
            r8.mPopupUIAlertDialog = r9
            goto L37
        L21:
            com.android.systemui.popup.view.DataConnectionErrorDialog r0 = new com.android.systemui.popup.view.DataConnectionErrorDialog
            android.content.Context r1 = r8.mContext
            com.android.systemui.basic.util.LogWrapper r2 = r8.mLogWrapper
            java.lang.Runnable r3 = r8.mShowingDialog
            java.lang.Runnable r4 = r8.mDismissDialog
            r5 = r9
            r6 = r10
            r7 = r11
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r8.mPopupUIAlertDialog = r0
            goto L37
        L34:
            r9 = 0
            r8.mPopupUIAlertDialog = r9
        L37:
            com.android.systemui.popup.view.PopupUIAlertDialog r8 = r8.mPopupUIAlertDialog
            return r8
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
        SimTrayProtectionDialog simTrayProtectionDialog = new SimTrayProtectionDialog(getContext(), this.mLogWrapper, i, z, i2);
        this.mPopupUIAlertDialog = simTrayProtectionDialog;
        return simTrayProtectionDialog;
    }
}
