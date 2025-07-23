package com.android.wm.shell.controlpanel.action;

import android.content.Context;
import android.content.Intent;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
