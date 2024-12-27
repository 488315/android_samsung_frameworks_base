package com.android.systemui.power.dialog;

import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

public final class BatteryHealthInterruptionDialog extends PowerUiDialog {
    public int mBatteryHealth;

    public BatteryHealthInterruptionDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        int i = this.mBatteryHealth;
        if (i == 6 || i == 8) {
            return true;
        }
        Log.w("PowerUI.Dialog.BatteryHealthInterruption", "status is NotCharging but health is wrong value");
        return false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        View inflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.notice_text)).setText(this.mBatteryHealth == 6 ? DeviceType.isTablet() ? this.mContext.getString(R.string.battery_health_interruption_by_terminal_open_text_tablet) : this.mContext.getString(R.string.battery_health_interruption_by_terminal_open_text) : DeviceType.isTablet() ? this.mContext.getString(R.string.battery_health_interruption_by_limit_high_temperature_text_tablet) : this.mContext.getString(R.string.battery_health_interruption_by_limit_high_temperature_text_phone));
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCancelable = false;
        alertParams.mTitle = this.mBatteryHealth == 6 ? this.mContext.getString(R.string.battery_health_interruption_title) : this.mContext.getString(R.string.battery_health_interruption_by_limit_high_temperature_title);
        if (this.mBatteryHealth == 6) {
            builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        }
        if (this.mBatteryHealth == 8) {
            ImageView imageView = (ImageView) inflate.findViewById(R.id.guide_image);
            imageView.setImageResource(R.drawable.image_popup_remove);
            imageView.setVisibility(0);
        }
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mBatteryHealth = secBatterySnapshot.batteryHealth;
    }
}
