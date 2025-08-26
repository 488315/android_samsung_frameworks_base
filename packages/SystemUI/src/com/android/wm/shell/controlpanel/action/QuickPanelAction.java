package com.android.wm.shell.controlpanel.action;

import android.app.SemStatusBarManager;
import android.content.Context;
import android.content.Intent;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;

/* loaded from: classes3.dex */
public class QuickPanelAction extends MenuActionType {
    public final Context mContext;

    private QuickPanelAction(Context context) {
        this.mContext = context;
    }

    public static QuickPanelAction createAction(FlexPanelActivity flexPanelActivity) {
        return new QuickPanelAction(flexPanelActivity);
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) {
        if (ControlPanelUtils.isQuickPanelPressAvailable(this.mContext, str)) {
            SemStatusBarManager semStatusBarManager = (SemStatusBarManager) this.mContext.getSystemService("sem_statusbar");
            if (semStatusBarManager.isPanelExpanded()) {
                semStatusBarManager.collapsePanels();
                return;
            }
            Context context = this.mContext;
            if (ControlPanelUtils.isKidsMode(context)) {
                Intent intent = new Intent("com.sec.kidsplat.quickpanel.PANEL_OPEN");
                intent.putExtra("open_from_menu", true);
                context.sendBroadcast(intent);
            } else {
                SemStatusBarManager semStatusBarManager2 = (SemStatusBarManager) context.getSystemService("sem_statusbar");
                if (semStatusBarManager2 != null) {
                    semStatusBarManager2.expandNotificationsPanel();
                }
            }
        }
    }
}
