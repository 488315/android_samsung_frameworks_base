package com.android.systemui.popup.view;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.p014qp.SubscreenQsPanelController;
import com.android.systemui.popup.util.DisplayManagerWrapper;
import com.android.systemui.popup.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.popup.util.PopupUIUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PopupUIAlertDialogFactory {
    public final Context mContext;
    public final KeyguardUpdateMonitorWrapper mKeyguardUpdateMonitorWrapper;
    public final LogWrapper mLogWrapper;
    public PopupUIAlertDialog mPopupUIAlertDialog;
    public final Context mSubscreenContext;
    public final PopupUIUtil mUtil;
    public final RunnableC19251 mShowingDialog = new Runnable() { // from class: com.android.systemui.popup.view.PopupUIAlertDialogFactory.1
        @Override // java.lang.Runnable
        public final void run() {
            PopupUIAlertDialogFactory.this.mKeyguardUpdateMonitorWrapper.getClass();
        }
    };
    public final RunnableC19262 mDismissDialog = new Runnable() { // from class: com.android.systemui.popup.view.PopupUIAlertDialogFactory.2
        @Override // java.lang.Runnable
        public final void run() {
            PopupUIAlertDialogFactory.this.mKeyguardUpdateMonitorWrapper.getClass();
        }
    };

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.popup.view.PopupUIAlertDialogFactory$1] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.popup.view.PopupUIAlertDialogFactory$2] */
    public PopupUIAlertDialogFactory(Context context, PopupUIUtil popupUIUtil, LogWrapper logWrapper, KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper, DisplayManagerWrapper displayManagerWrapper) {
        this.mContext = context;
        if (BasicRune.POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG) {
            ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getClass();
            this.mSubscreenContext = SubscreenQsPanelController.mContext;
        }
        this.mUtil = popupUIUtil;
        this.mLogWrapper = logWrapper;
        this.mKeyguardUpdateMonitorWrapper = keyguardUpdateMonitorWrapper;
        this.mPopupUIAlertDialog = null;
    }

    public final void initializeDialog() {
        PopupUIAlertDialog popupUIAlertDialog = this.mPopupUIAlertDialog;
        if (popupUIAlertDialog != null) {
            if (popupUIAlertDialog.isShowing()) {
                this.mPopupUIAlertDialog.dismiss();
            }
            this.mPopupUIAlertDialog = null;
        }
    }
}
