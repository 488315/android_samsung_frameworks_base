package com.android.systemui.keyboard;

import android.content.Context;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BluetoothDialog extends SystemUIDialog {
    public BluetoothDialog(Context context) {
        super(context);
        getWindow().setType(2008);
        SystemUIDialog.setShowForAllUsers(this);
    }
}
