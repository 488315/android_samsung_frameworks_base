package com.android.wm.shell.controlpanel.action;

import android.app.SemStatusBarManager;
import android.content.Context;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;

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
