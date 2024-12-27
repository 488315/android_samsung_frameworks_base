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
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UsbDamageProtectionDialog extends PowerUiDialog {
    public UsbDamageProtectionDialog(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        String m;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.notice_text);
        String str = this.mContext.getString(R.string.usb_damage_protection_alert_body1) + "\n\n";
        if (DeviceType.isTablet()) {
            m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.usb_damage_protection_alert_body2_tablet, EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str));
        } else {
            m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.usb_damage_protection_alert_body2_phone, EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str));
        }
        textView.setText(m);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.guide_image);
        imageView.setImageResource(R.drawable.image_popup_remove);
        imageView.setVisibility(0);
        String string = this.mContext.getString(R.string.usb_damage_protection_alert_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        create.getWindow().setGravity(80);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
    }
}
