package com.android.systemui.power.dialog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HvChargerEnableDialog extends PowerUiDialog {
    public HvChargerEnableDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        if (PowerUiRune.IS_LDU_OR_UNPACK_BINARY || DeviceState.isShopDemo(this.mContext)) {
            Log.w("PowerUI.Dialog.HvChargerEnable", "IS LDU or RDU binary, so don't show hv enable popup");
            return false;
        }
        if (this.mSharedPref != null) {
            String str = this.mDoNotShowTag + ":" + ActivityManager.getCurrentUser();
            int i = this.mSharedPref.getInt(str, 0);
            if (i >= 3) {
                Log.d("PowerUI.Dialog.HvChargerEnable", "Once AFC enable charging pop up was showed, so we doesn't show again");
                return false;
            }
            SharedPreferences.Editor edit = this.mSharedPref.edit();
            edit.putInt(str, i + 1);
            edit.remove("DoNotShowAfcEnablePopup");
            edit.commit();
        }
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        View inflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.notice_text);
        boolean z = PowerUiRune.SPECIFIC_POWER_REQUEST_BY_CHN;
        textView.setText(z ? this.mContext.getString(R.string.pd_hv_charger_dialog_description_chn) : this.mContext.getString(R.string.pd_hv_charger_dialog_description));
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        String string = z ? this.mContext.getString(R.string.pd_hv_charger_dialog_title_chn) : this.mContext.getString(R.string.pd_hv_charger_dialog_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.dialog.HvChargerEnableDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Settings.System.putIntForUser(HvChargerEnableDialog.this.mContext.getContentResolver(), "adaptive_fast_charging", 1, -2);
            }
        };
        alertParams.mPositiveButtonText = alertParams.mContext.getText(R.string.pd_hv_charger_dialog_turn_on_button);
        alertParams.mPositiveButtonListener = onClickListener;
        alertParams.mNegativeButtonText = alertParams.mContext.getText(android.R.string.cancel);
        alertParams.mNegativeButtonListener = null;
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mSharedPref = this.mContext.getSharedPreferences("com.android.systemui.afc_disable_charing", 0);
        this.mDoNotShowTag = "DoNotShowAfcEnablePopupCount";
    }
}
