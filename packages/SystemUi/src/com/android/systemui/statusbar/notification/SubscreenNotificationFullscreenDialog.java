package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationFullscreenDialog extends SystemUIDialog {
    public SubscreenNotificationFullscreenDialog(Context context) {
        this(context, R.style.SubscreenNotificationFullscreenDialog);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final int getHeight() {
        return -1;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final int getWidth() {
        return -1;
    }

    public SubscreenNotificationFullscreenDialog(Context context, int i) {
        super(context, i);
        requestWindowFeature(1);
    }
}
