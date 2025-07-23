package com.android.systemui.power.dialog;

import android.content.Context;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class HappenedOverheatShutdownDialog extends PowerUiDialog {
    public HappenedOverheatShutdownDialog(Context context) {
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
        boolean z = PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW;
        alertParams.mTitle = z ? DeviceType.isTablet() ? this.mContext.getString(R.string.overheat_shutdown_notice_title_tablet) : this.mContext.getString(R.string.overheat_shutdown_notice_title) : this.mContext.getString(R.string.overheat_poweroff_notice_title);
        alertParams.mMessage = z ? DeviceType.isTablet() ? this.mContext.getString(R.string.overheat_shutdown_notice_text_tablet) : this.mContext.getString(R.string.overheat_shutdown_notice_text) : this.mContext.getString(R.string.overheat_poweroff_notice_text);
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), new HappenedOverheatShutdownDialog$$ExternalSyntheticLambda0());
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
