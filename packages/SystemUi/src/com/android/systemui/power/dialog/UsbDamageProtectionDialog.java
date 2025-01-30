package com.android.systemui.power.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        Context context = this.mContext;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(context, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.notice_text);
        String str = context.getString(R.string.usb_damage_protection_alert_body1) + "\n\n";
        textView.setText(DeviceType.isTablet() ? KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.usb_damage_protection_alert_body2_tablet, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str)) : KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.usb_damage_protection_alert_body2_phone, ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str)));
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.guide_image);
        imageView.setImageResource(R.drawable.image_popup_remove);
        imageView.setVisibility(0);
        String string = context.getString(R.string.usb_damage_protection_alert_title);
        AlertController.AlertParams alertParams = builder.f0P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), (DialogInterface.OnClickListener) null);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        create.getWindow().setGravity(80);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
    }
}
