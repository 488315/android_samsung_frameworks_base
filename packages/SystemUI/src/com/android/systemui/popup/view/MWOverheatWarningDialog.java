package com.android.systemui.popup.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.data.MWOverheatWarningData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class MWOverheatWarningDialog implements PopupUIAlertDialog {
    private static final String TAG = "MWOverheatWarningDialog";
    private Context mContext;
    private AlertDialog mDialog = createDialog(new MWOverheatWarningData());
    private LogWrapper mLogWrapper;

    public MWOverheatWarningDialog(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
    }

    private AlertDialog createDialog(MWOverheatWarningData mWOverheatWarningData) {
        String string = this.mContext.getResources().getString(mWOverheatWarningData.getTitle());
        String string2 = this.mContext.getResources().getString(mWOverheatWarningData.getBody());
        String string3 = this.mContext.getResources().getString(mWOverheatWarningData.getPButton());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.Theme_SystemUI_Dialog);
        builder.setTitle(string);
        builder.setMessage(string2);
        builder.setPositiveButton(string3, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.getWindow().getAttributes().setTitle(TAG);
        this.mDialog.getWindow().setType(2008);
        return this.mDialog;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void dismiss() {
        if (this.mDialog == null || !isShowing()) {
            return;
        }
        this.mDialog.dismiss();
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public boolean isShowing() {
        return this.mDialog.isShowing();
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void show() {
        try {
            if (this.mDialog != null) {
                dismiss();
                this.mDialog.setCancelable(false);
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            this.mLogWrapper.v(TAG);
        }
    }
}
