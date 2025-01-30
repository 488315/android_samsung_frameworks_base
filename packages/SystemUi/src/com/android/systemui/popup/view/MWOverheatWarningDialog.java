package com.android.systemui.popup.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.data.MWOverheatWarningData;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MWOverheatWarningDialog implements PopupUIAlertDialog {
    public AlertDialog mDialog;
    public final LogWrapper mLogWrapper;

    public MWOverheatWarningDialog(Context context, LogWrapper logWrapper) {
        this.mLogWrapper = logWrapper;
        new MWOverheatWarningData();
        String string = context.getResources().getString(R.string.multiwindow_overheat_warning_dialog_title);
        String string2 = context.getResources().getString(DeviceType.isTablet() ? R.string.multiwindow_overheat_warning_dialog_body_tablet : R.string.multiwindow_overheat_warning_dialog_body_phone);
        String string3 = context.getResources().getString(R.string.yes);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2132018540);
        builder.setTitle(string);
        builder.setMessage(string2);
        builder.setPositiveButton(string3, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.getWindow().getAttributes().setTitle("MWOverheatWarningDialog");
        this.mDialog.getWindow().setType(2008);
        this.mDialog = this.mDialog;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final void dismiss() {
        if (this.mDialog == null || !isShowing()) {
            return;
        }
        this.mDialog.dismiss();
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final boolean isShowing() {
        return this.mDialog.isShowing();
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final void show() {
        try {
            if (this.mDialog != null) {
                dismiss();
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            this.mLogWrapper.m103v("MWOverheatWarningDialog");
        }
    }
}
