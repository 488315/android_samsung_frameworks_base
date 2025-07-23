package com.android.systemui.qp;

import android.content.Context;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SubscreenQsPanelDialog extends SystemUIDialog implements ViewRootImpl.ConfigChangedCallback {
    public SubscreenQsPanelDialog(Context context) {
        this(context, R.style.Theme_SystemUI_Dialog, true);
    }

    public SubscreenQsPanelDialog(Context context, int i) {
        this(context, i, true);
    }

    public SubscreenQsPanelDialog(Context context, boolean z) {
        this(context, R.style.Theme_SystemUI_Dialog, z);
    }

    public SubscreenQsPanelDialog(Context context, int i, boolean z) {
        super(context, i);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 80;
        getWindow().setAttributes(attributes);
    }
}
