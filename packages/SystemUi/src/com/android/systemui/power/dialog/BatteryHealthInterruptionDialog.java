package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
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
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        Log.e("PowerUI.Dialog.BatteryHealthInterruption", "status is NotCharging but health is wrong value");
        return false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        Context context = this.mContext;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(context, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.notice_text)).setText(this.mBatteryHealth == 6 ? DeviceType.isTablet() ? context.getString(R.string.battery_health_interruption_by_terminal_open_text_tablet) : context.getString(R.string.battery_health_interruption_by_terminal_open_text) : DeviceType.isTablet() ? context.getString(R.string.f771x22cee7d3) : context.getString(R.string.battery_health_interruption_by_limit_high_temperature_text_phone));
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mCancelable = false;
        alertParams.mTitle = this.mBatteryHealth == 6 ? context.getString(R.string.battery_health_interruption_title) : context.getString(R.string.battery_health_interruption_by_limit_high_temperature_title);
        if (this.mBatteryHealth == 6) {
            builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), (DialogInterface.OnClickListener) null);
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
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mBatteryHealth = secBatteryStatsSnapshot.batteryHealth;
    }
}
