package com.android.wm.shell.controlpanel.action;

import android.app.SemStatusBarManager;
import android.content.Context;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QuickSettingsAction extends MenuActionType {
    public final Context mContext;

    private QuickSettingsAction(Context context) {
        this.mContext = context;
    }

    public static QuickSettingsAction createAction(FlexPanelActivity flexPanelActivity) {
        return new QuickSettingsAction(flexPanelActivity);
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) {
        Context context = this.mContext;
        String str2 = ControlPanelUtils.TALKBACK_SERVICE;
        SemStatusBarManager semStatusBarManager = (SemStatusBarManager) context.getSystemService("sem_statusbar");
        if (semStatusBarManager != null) {
            semStatusBarManager.expandQuickSettingsPanel();
        }
    }
}
