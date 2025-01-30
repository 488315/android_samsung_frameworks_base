package com.android.settingslib.users;

import android.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UserCreatingDialog extends ProgressDialog {
    public UserCreatingDialog(Context context) {
        this(context, false);
    }

    public UserCreatingDialog(Context context, boolean z) {
        super(context, (context.getResources().getConfiguration().uiMode & 48) == 32 ? R.style.Theme.DeviceDefault.Dialog.Alert : R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        setCancelable(false);
        View inflate = LayoutInflater.from(getContext()).inflate(com.android.systemui.R.layout.user_creation_progress_dialog, (ViewGroup) null);
        String string = getContext().getString(z ? com.android.systemui.R.string.creating_new_guest_dialog_message : com.android.systemui.R.string.creating_new_user_dialog_message);
        setMessage(string);
        inflate.setAccessibilityPaneTitle(string);
        ((TextView) inflate.findViewById(com.android.systemui.R.id.message)).setText(string);
        setView(inflate);
        getWindow().setType(2010);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.privateFlags = 272;
        getWindow().setAttributes(attributes);
    }
}
