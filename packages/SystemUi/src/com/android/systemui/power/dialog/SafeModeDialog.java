package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        Context context = this.mContext;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mCancelable = false;
        alertParams.mTitle = context.getString(R.string.safe_mode_alert_title);
        alertParams.mMessage = DeviceType.isTablet() ? context.getString(R.string.safe_mode_alert_body_tablet) : context.getString(R.string.safe_mode_alert_body);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(R.string.safe_mode_alert_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.dialog.SafeModeDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Intent intent = new Intent("android.intent.action.REBOOT");
                    intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
                    intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    SafeModeDialog.this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                    Log.d("PowerUI.Dialog", "showSafeModePopUp() - Request Reboot");
                } catch (Exception e) {
                    Log.e("PowerUI.Dialog", "Can't Request Reboot by unknown reason");
                    e.printStackTrace();
                }
            }
        });
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}
