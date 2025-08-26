package com.android.systemui.power.dialog;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* loaded from: classes2.dex */
public class WillOverheatShutdownDialog extends PowerUiDialog {
    public WillOverheatShutdownDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        String strM;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCancelable = false;
        boolean z = PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW;
        alertParams.mTitle = z ? this.mContext.getString(R.string.overheat_shutdown_warning_title) : this.mContext.getString(R.string.overheat_poweroff_warning_title);
        if (z) {
            strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.overheat_poweroff_warning_text_append, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(DeviceType.isTablet() ? this.mContext.getString(R.string.overheat_shutdown_warning_text_tablet) : this.mContext.getString(R.string.overheat_shutdown_warning_text), " "));
        } else {
            strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.overheat_poweroff_warning_text_append, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(this.mContext.getString(R.string.overheat_poweroff_warning_text_new, 10), " "));
        }
        alertParams.mMessage = strM;
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2009);
        return alertDialogCreate;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
