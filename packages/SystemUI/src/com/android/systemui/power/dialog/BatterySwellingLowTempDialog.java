package com.android.systemui.power.dialog;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

public final class BatterySwellingLowTempDialog extends PowerUiDialog {
    public BatterySwellingLowTempDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        View inflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.notice_text)).setText((DeviceType.isTablet() ? this.mContext.getString(R.string.battery_swelling_mode_low_temp_popup_text_tablet) : this.mContext.getString(R.string.battery_swelling_mode_low_temp_popup_text)) + "\n\n");
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        builder.P.mTitle = this.mContext.getString(R.string.battery_swelling_mode_low_temp_popup_title);
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mSharedPref = this.mContext.getSharedPreferences("com.android.systemui.power_battery_swelling_low_temp", 0);
    }
}
