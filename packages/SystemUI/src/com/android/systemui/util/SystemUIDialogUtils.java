package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.qp.SubscreenQsPanelDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;

public abstract class SystemUIDialogUtils {
    protected static final boolean DEBUG = false;

    public static SystemUIDialog createSystemUIDialogUtils(Context context, int i) {
        return context.getDisplayId() != 0 ? new SubscreenQsPanelDialog(context, i) : new SystemUIDialog(context, i);
    }
}
