package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.qp.SubscreenQsPanelDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SystemUIDialogUtils {
    protected static final boolean DEBUG = false;

    public static SystemUIDialog createSystemUIDialogUtils(Context context, int i) {
        return context.getDisplayId() != 0 ? new SubscreenQsPanelDialog(context, i) : new SystemUIDialog(context, i);
    }
}
