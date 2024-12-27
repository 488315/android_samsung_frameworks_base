package com.android.systemui.qp;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;

public final class SubscreenQsPanelDialog extends SystemUIDialog {
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
