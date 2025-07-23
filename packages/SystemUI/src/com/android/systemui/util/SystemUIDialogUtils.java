package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.qp.SubscreenQsPanelDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SystemUIDialogUtils {
    protected static final boolean DEBUG = false;

    public static SystemUIDialog createSystemUIDialogUtils(Context context, int i) {
        return context.getDisplayId() != 0 ? new SubscreenQsPanelDialog(context, i) : new SystemUIDialog(context, i);
    }
}
