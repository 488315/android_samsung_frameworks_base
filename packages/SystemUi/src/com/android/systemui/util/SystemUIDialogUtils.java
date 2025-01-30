package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.qp.SubscreenQsPanelDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SystemUIDialogUtils {
    public static SystemUIDialog createSystemUIDialogUtils(int i, Context context) {
        return context.getDisplayId() != 0 ? new SubscreenQsPanelDialog(context, i) : new SystemUIDialog(context, i);
    }
}
