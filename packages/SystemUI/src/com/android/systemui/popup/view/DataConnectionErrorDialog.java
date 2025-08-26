package com.android.systemui.popup.view;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.data.DataConnectionErrorData;

/* loaded from: classes2.dex */
public class DataConnectionErrorDialog implements PopupUIAlertDialog {
    private static final int INVALID_TYPE = -1;
    private static final String TAG = "DataConnectionErrorDialog";
    private Context mContext;
    private AlertDialog mDialog;
    private LogWrapper mLogWrapper;

    public DataConnectionErrorDialog(Context context, LogWrapper logWrapper, Runnable runnable, Runnable runnable2, int i, boolean z, PendingIntent pendingIntent) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mDialog = createDialog(new DataConnectionErrorData(logWrapper), runnable, runnable2, i, z, pendingIntent);
    }

    private AlertDialog createDialog(DataConnectionErrorData dataConnectionErrorData, Runnable runnable, Runnable runnable2, int i, boolean z, PendingIntent pendingIntent) {
        String string = dataConnectionErrorData.getTitle(i) != -1 ? this.mContext.getResources().getString(dataConnectionErrorData.getTitle(i)) : null;
        String string2 = dataConnectionErrorData.getBody(i) != -1 ? this.mContext.getResources().getString(dataConnectionErrorData.getBody(i)) : null;
        String string3 = dataConnectionErrorData.getPButton(i, z) != -1 ? this.mContext.getResources().getString(dataConnectionErrorData.getPButton(i, z)) : null;
        String string4 = dataConnectionErrorData.getNButton(i, z) != -1 ? this.mContext.getResources().getString(dataConnectionErrorData.getNButton(i, z)) : null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.Theme_SystemUI_POPUPUI);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mMessage = string2;
        builder.setPositiveButton(string3, getPButtonClickListener(dataConnectionErrorData, i, z, pendingIntent));
        if (string4 != null) {
            alertParams.mNegativeButtonText = string4;
            alertParams.mNegativeButtonListener = null;
        }
        AlertDialog alertDialogCreate = builder.create();
        this.mDialog = alertDialogCreate;
        alertDialogCreate.getWindow().getAttributes().setTitle(TAG);
        this.mDialog.getWindow().setType(2008);
        AlertDialog alertDialog = this.mDialog;
        alertDialog.mAlert.mIsBlurEnabled = true;
        return alertDialog;
    }

    private DialogInterface.OnClickListener getPButtonClickListener(DataConnectionErrorData dataConnectionErrorData, int i, boolean z, PendingIntent pendingIntent) {
        final Runnable pButtonClickListener = dataConnectionErrorData.getPButtonClickListener(this.mContext, i, z, pendingIntent);
        if (pButtonClickListener == null) {
            return null;
        }
        return new DialogInterface.OnClickListener() { // from class: com.android.systemui.popup.view.DataConnectionErrorDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                pButtonClickListener.run();
            }
        };
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void dismiss() {
        if (this.mDialog == null || !isShowing()) {
            return;
        }
        this.mLogWrapper.v(TAG);
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
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            this.mLogWrapper.v(TAG);
        }
    }
}
