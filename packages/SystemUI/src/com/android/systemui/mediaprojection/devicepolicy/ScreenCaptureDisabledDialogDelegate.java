package com.android.systemui.mediaprojection.devicepolicy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import com.android.systemui.R;

public final class ScreenCaptureDisabledDialogDelegate {
    public final Context context;
    public final Resources resources;

    public ScreenCaptureDisabledDialogDelegate(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    public final void initDialog(final AlertDialog alertDialog) {
        alertDialog.setTitle(this.resources.getString(R.string.screen_capturing_disabled_by_policy_dialog_title));
        alertDialog.setMessage(this.resources.getString(R.string.screen_capturing_disabled_by_policy_dialog_description));
        alertDialog.setIcon(R.drawable.ic_cast);
        alertDialog.setButton(-1, this.resources.getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate$initDialog$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.cancel();
            }
        });
    }
}
