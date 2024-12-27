package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PdChargerAlertDialog extends PowerUiDialog {
    public PdChargerAlertDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        SharedPreferences sharedPreferences = this.mSharedPref;
        if (sharedPreferences == null || !sharedPreferences.getBoolean(this.mDoNotShowTag, false)) {
            return true;
        }
        Log.w("PowerUI.Dialog.PdChargerAlert", "PD charging pop up doesn't show again");
        return false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        String m;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((LinearLayout) inflate.findViewById(R.id.do_not_show_again_layout)).setVisibility(0);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.do_not_show_again);
        TextView textView = (TextView) inflate.findViewById(R.id.notice_text);
        if (DeviceType.isTablet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getString(R.string.pd_charger_dialog_text_pd_charger_tablet));
            sb.append("\n\n");
            m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.pd_charger_dialog_text_faster_charging_tablet, sb);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mContext.getString(R.string.pd_charger_dialog_text_pd_charger));
            sb2.append("\n\n");
            m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.pd_charger_dialog_text_faster_charging, sb2);
        }
        textView.setText(m);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        String string = this.mContext.getString(R.string.pd_charger_dialog_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.dialog.PdChargerAlertDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PdChargerAlertDialog pdChargerAlertDialog = PdChargerAlertDialog.this;
                CheckBox checkBox2 = checkBox;
                pdChargerAlertDialog.getClass();
                if (checkBox2.isChecked()) {
                    SharedPreferences.Editor edit = pdChargerAlertDialog.mSharedPref.edit();
                    edit.putBoolean(pdChargerAlertDialog.mDoNotShowTag, true);
                    edit.commit();
                }
            }
        });
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mSharedPref = this.mContext.getSharedPreferences("com.android.systemui.pd_charging", 0);
        this.mDoNotShowTag = "DoNotShowPdChargerWarning";
    }
}
