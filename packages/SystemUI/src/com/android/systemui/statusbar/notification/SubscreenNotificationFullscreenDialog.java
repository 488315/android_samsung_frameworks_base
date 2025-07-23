package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenNotificationFullscreenDialog extends SystemUIDialog {
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
