package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WillOverheatShutdownDialog extends PowerUiDialog {
    public WillOverheatShutdownDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        String m73m;
        Context context = this.mContext;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mCancelable = false;
        boolean z = PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW;
        alertParams.mTitle = z ? context.getString(R.string.overheat_shutdown_warning_title) : context.getString(R.string.overheat_poweroff_warning_title);
        if (z) {
            m73m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.overheat_poweroff_warning_text_append, AbstractC0000x2c234b15.m2m(DeviceType.isTablet() ? context.getString(R.string.overheat_shutdown_warning_text_tablet) : context.getString(R.string.overheat_shutdown_warning_text), " "));
        } else {
            m73m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.overheat_poweroff_warning_text_append, AbstractC0000x2c234b15.m2m(context.getString(R.string.overheat_poweroff_warning_text_new, 10), " "));
        }
        alertParams.mMessage = m73m;
        builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}
