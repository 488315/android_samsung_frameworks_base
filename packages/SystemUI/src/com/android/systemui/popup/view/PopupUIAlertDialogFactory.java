package com.android.systemui.popup.view;

import android.app.PendingIntent;
import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.popup.util.DisplayManagerWrapper;
import com.android.systemui.popup.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.qp.SubscreenQsPanelController;

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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public PopupUIAlertDialog getDataConnectionDialog(int i, boolean z, PendingIntent pendingIntent) {
        initializeDialog();
        if (i == -1) {
            this.mPopupUIAlertDialog = null;
        } else if (i == 0 || i == 1 || i == 2) {
            this.mPopupUIAlertDialog = new DataConnectionErrorDialog(this.mContext, this.mLogWrapper, this.mShowingDialog, this.mDismissDialog, i, z, pendingIntent);
        } else if (i == 3) {
            this.mPopupUIAlertDialog = new DataConnectionDataLimitDialog(this.mContext, this.mLogWrapper);
        } else if (i == 4) {
        }
        return this.mPopupUIAlertDialog;
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
