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
import androidx.appcompat.app.AlertDialog;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IncompleteChargerDialog extends PowerUiDialog {
    public boolean mAutomaticTestMode;

    public IncompleteChargerDialog(Context context) {
        super(context);
        this.mAutomaticTestMode = false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final boolean checkCondition() {
        if (PowerUiRune.IS_LDU_OR_UNPACK_BINARY || DeviceState.isShopDemo(this.mContext)) {
            Log.w("PowerUI.Dialog.IncompleteCharger", "IS LDU or RDU binary, so don't show incomplete charging popup");
            return false;
        }
        if (!this.mIsFactoryBinary && !this.mAutomaticTestMode) {
            return true;
        }
        Log.w("PowerUI.Dialog.IncompleteCharger", "Factory binary or automatic test mode, so don't show incomplete charging popup");
        return false;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final AlertDialog getDialog() {
        Context context = this.mContext;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(context, R.style.power_ui_dialog_theme)).inflate(R.layout.power_ui_dialog, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.guide_image);
        imageView.setImageResource(R.drawable.image_popup_check);
        imageView.setVisibility(0);
        ((TextView) inflate.findViewById(R.id.notice_text)).setText(context.getString(R.string.battery_not_fully_connected_charging_popup_text_connection));
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.power_ui_dialog_theme);
        builder.f0P.mTitle = context.getString(R.string.battery_not_fully_connected_charging_popup_title);
        builder.setPositiveButton(context.getString(R.string.dialog_button_text_ok), (DialogInterface.OnClickListener) null);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        create.getWindow().getAttributes().semAddPrivateFlags(64);
        return create;
    }

    @Override // com.android.systemui.power.dialog.PowerUiDialog
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mAutomaticTestMode = secBatteryStatsSnapshot.automaticTestMode;
    }
}
