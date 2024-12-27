package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
