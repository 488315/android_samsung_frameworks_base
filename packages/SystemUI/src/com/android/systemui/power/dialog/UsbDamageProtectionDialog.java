package com.android.systemui.power.dialog;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* loaded from: classes2.dex */
public class UsbDamageProtectionDialog extends PowerUiDialog {
    public UsbDamageProtectionDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        String strM;
        View viewInflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.notice_text);
        String str = this.mContext.getString(R.string.usb_damage_protection_alert_body1) + "\n\n";
        if (DeviceType.isTablet()) {
            strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.usb_damage_protection_alert_body2_tablet, PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str));
        } else {
            strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.usb_damage_protection_alert_body2_phone, PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str));
        }
        textView.setText(strM);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.guide_image);
        imageView.setImageResource(R.drawable.image_popup_remove);
        imageView.setVisibility(0);
        String string = this.mContext.getString(R.string.usb_damage_protection_alert_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        builder.setView(viewInflate);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2009);
        alertDialogCreate.getWindow().setGravity(80);
        return alertDialogCreate;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
