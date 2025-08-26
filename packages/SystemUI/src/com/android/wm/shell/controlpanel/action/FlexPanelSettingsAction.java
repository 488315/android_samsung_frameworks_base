package com.android.wm.shell.controlpanel.action;

import android.content.Context;
import android.content.Intent;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;

/* loaded from: classes3.dex */
public class FlexPanelSettingsAction extends MenuActionType {
    public final Context mContext;

    private FlexPanelSettingsAction(Context context) {
        this.mContext = context;
    }

    public static FlexPanelSettingsAction createAction(FlexPanelActivity flexPanelActivity) {
        return new FlexPanelSettingsAction(flexPanelActivity);
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) {
        this.mContext.startActivity(new Intent("com.samsung.settings.FLEX_PANEL_SETTINGS").setFlags(268468224));
    }
}
