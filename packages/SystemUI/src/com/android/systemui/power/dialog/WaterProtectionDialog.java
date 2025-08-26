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
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.DeviceType;

/* loaded from: classes2.dex */
public class WaterProtectionDialog extends PowerUiDialog {
    public boolean mIsHiccupState;

    public WaterProtectionDialog(Context context) {
        super(context);
        this.mIsHiccupState = false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        return true;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        View viewInflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.notice_text)).setText(DeviceType.isTablet() ? this.mContext.getString(R.string.water_protection_notification_body_tablet) : PowerUiRune.WIRELESS_CHARGING ? this.mContext.getString(R.string.water_protection_notification_body_support_wireless_charging) : this.mContext.getString(R.string.water_protection_notification_body));
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.power_ui_dialog_theme);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.guide_image);
        imageView.setImageResource(R.drawable.image_popup_remove);
        imageView.setVisibility(0);
        String string = this.mContext.getString(R.string.water_protection_notification_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        alertParams.mCancelable = false;
        if (this.mIsHiccupState) {
            builder.setPositiveButton(this.mContext.getString(R.string.dialog_button_text_ok), null);
        }
        builder.setView(viewInflate);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2009);
        alertDialogCreate.getWindow().setGravity(80);
        return alertDialogCreate;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mIsHiccupState = secBatterySnapshot.isHiccupState;
    }
}
