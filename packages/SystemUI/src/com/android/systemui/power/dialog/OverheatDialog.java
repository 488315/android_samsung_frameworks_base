package com.android.systemui.power.dialog;

import android.content.Context;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OverheatDialog extends PowerUiDialog {
    public OverheatDialog(Context context) {
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
        alertParams.mTitle = z ? this.mContext.getString(R.string.cooling_title_vzw) : this.mContext.getString(R.string.cooling_popup_title);
        alertParams.mMessage = z ? DeviceType.isTablet() ? this.mContext.getString(R.string.cooling_body_tablet_vzw) : this.mContext.getString(R.string.cooling_body_vzw) : this.mContext.getString(R.string.cooling_body);
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
