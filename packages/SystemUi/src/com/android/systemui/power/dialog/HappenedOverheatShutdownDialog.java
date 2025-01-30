package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HappenedOverheatShutdownDialog extends PowerUiDialog {
    public HappenedOverheatShutdownDialog(Context context) {
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
        boolean z = PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW;
        alertParams.mTitle = z ? DeviceType.isTablet() ? context.getString(R.string.overheat_shutdown_notice_title_tablet) : context.getString(R.string.overheat_shutdown_notice_title) : context.getString(R.string.overheat_poweroff_notice_title);
        alertParams.mMessage = z ? DeviceType.isTablet() ? context.getString(R.string.overheat_shutdown_notice_text_tablet) : context.getString(R.string.overheat_shutdown_notice_text) : context.getString(R.string.overheat_poweroff_notice_text);
        builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.power.dialog.HappenedOverheatShutdownDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
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
