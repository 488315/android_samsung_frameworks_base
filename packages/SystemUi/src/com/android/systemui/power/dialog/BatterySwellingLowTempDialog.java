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
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BatterySwellingLowTempDialog extends PowerUiDialog {
    public BatterySwellingLowTempDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        SharedPreferences sharedPreferences = this.mSharedPref;
        if (sharedPreferences == null || !sharedPreferences.getBoolean(this.mDoNotShowTag, false)) {
            return true;
        }
        Log.w("PowerUI.Dialog.BatterySwellingLowTemp", "Battery Swelling Pop up do not show again");
        return false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        Context context = this.mContext;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(context, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((LinearLayout) inflate.findViewById(R.id.do_not_show_again_layout)).setVisibility(0);
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.do_not_show_again);
        ((TextView) inflate.findViewById(R.id.notice_text)).setText((DeviceType.isTablet() ? context.getString(R.string.battery_swelling_mode_low_temp_popup_text_tablet) : context.getString(R.string.battery_swelling_mode_low_temp_popup_text)) + "\n\n");
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        builder.f0P.mTitle = context.getString(R.string.battery_swelling_mode_low_temp_popup_title);
        builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.dialog.BatterySwellingLowTempDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences;
                BatterySwellingLowTempDialog batterySwellingLowTempDialog = BatterySwellingLowTempDialog.this;
                CheckBox checkBox2 = checkBox;
                batterySwellingLowTempDialog.getClass();
                if (!checkBox2.isChecked() || (sharedPreferences = batterySwellingLowTempDialog.mSharedPref) == null) {
                    return;
                }
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean(batterySwellingLowTempDialog.mDoNotShowTag, true);
                edit.apply();
            }
        });
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mSharedPref = this.mContext.getSharedPreferences("com.android.systemui.power_battery_swelling_low_temp", 0);
        this.mDoNotShowTag = "DoNotShowBatterySwellingLowTemp";
    }
}
