package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SafeModeDialog extends PowerUiDialog {
    public SafeModeDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCancelable = false;
        alertParams.mTitle = this.mContext.getString(R.string.safe_mode_alert_title);
        alertParams.mMessage = DeviceType.isTablet() ? this.mContext.getString(R.string.safe_mode_alert_body_tablet) : this.mContext.getString(R.string.safe_mode_alert_body);
        alertParams.mNegativeButtonText = alertParams.mContext.getText(android.R.string.cancel);
        alertParams.mNegativeButtonListener = null;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.dialog.SafeModeDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SafeModeDialog safeModeDialog = SafeModeDialog.this;
                safeModeDialog.getClass();
                try {
                    Intent intent = new Intent("android.intent.action.REBOOT");
                    intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
                    intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    safeModeDialog.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                    Log.d("PowerUI.Dialog", "showSafeModePopUp() - Request Reboot");
                } catch (Exception e) {
                    Log.e("PowerUI.Dialog", "Can't Request Reboot by unknown reason", e);
                }
            }
        };
        alertParams.mPositiveButtonText = alertParams.mContext.getText(R.string.safe_mode_alert_positive_button);
        alertParams.mPositiveButtonListener = onClickListener;
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
