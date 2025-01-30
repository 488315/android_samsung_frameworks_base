package com.android.p038wm.shell.controlpanel.action;

import android.content.Context;
import android.content.Intent;
import com.android.p038wm.shell.controlpanel.GridUIManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FlexPanelSettingsAction extends MenuActionType {
    public final Context mContext;

    private FlexPanelSettingsAction(Context context) {
        this.mContext = context;
    }

    public static FlexPanelSettingsAction createAction(Context context) {
        return new FlexPanelSettingsAction(context);
    }

    @Override // com.android.p038wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, GridUIManager gridUIManager) {
        this.mContext.startActivity(new Intent("com.samsung.settings.FLEX_PANEL_SETTINGS").setFlags(268468224));
    }
}
