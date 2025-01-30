package com.android.p038wm.shell.controlpanel.action;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.p038wm.shell.controlpanel.GridUIManager;
import com.android.p038wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.multiwindow.MultiWindowUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitScreenAction extends MenuActionType {
    public final Context mContext;

    private SplitScreenAction(Context context) {
        this.mContext = context;
    }

    public static SplitScreenAction createAction(Context context) {
        return new SplitScreenAction(context);
    }

    @Override // com.android.p038wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, GridUIManager gridUIManager) {
        gridUIManager.getClass();
        Context context = this.mContext;
        ActivityManager.RunningTaskInfo runningTaskExcept = ControlPanelUtils.getRunningTaskExcept(context);
        ComponentName componentName = runningTaskExcept != null ? runningTaskExcept.baseActivity : new ComponentName("", "");
        int topTaskUserId = ControlPanelUtils.getTopTaskUserId(context);
        ActivityManager.RunningTaskInfo runningTaskExcept2 = ControlPanelUtils.getRunningTaskExcept(context);
        context.startActivityAsUser(MultiWindowUtils.getEdgeAllAppsActivityIntent(componentName, topTaskUserId, runningTaskExcept2 != null ? runningTaskExcept2.taskId : -1), UserHandle.CURRENT);
    }
}
