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
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        Log.d("PowerUI.Dialog.PdChargerAlert", "PD charging pop up doesn't show again");
        return false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        String str;
        Context context = this.mContext;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(context, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((LinearLayout) inflate.findViewById(R.id.do_not_show_again_layout)).setVisibility(0);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.do_not_show_again);
        TextView textView = (TextView) inflate.findViewById(R.id.notice_text);
        if (DeviceType.isTablet()) {
            str = context.getString(R.string.pd_charger_dialog_text_pd_charger_tablet) + "\n\n" + context.getString(R.string.pd_charger_dialog_text_faster_charging_tablet);
        } else {
            str = context.getString(R.string.pd_charger_dialog_text_pd_charger) + "\n\n" + context.getString(R.string.pd_charger_dialog_text_faster_charging);
        }
        textView.setText(str);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        String string = context.getString(R.string.pd_charger_dialog_title);
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.dialog.PdChargerAlertDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (checkBox.isChecked()) {
                    SharedPreferences.Editor edit = PdChargerAlertDialog.this.mSharedPref.edit();
                    edit.putBoolean(PdChargerAlertDialog.this.mDoNotShowTag, true);
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
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mSharedPref = this.mContext.getSharedPreferences("com.android.systemui.pd_charging", 0);
        this.mDoNotShowTag = "DoNotShowPdChargerWarning";
    }
}
