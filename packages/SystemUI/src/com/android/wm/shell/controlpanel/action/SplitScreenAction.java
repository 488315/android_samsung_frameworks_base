package com.android.wm.shell.controlpanel.action;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.samsung.android.multiwindow.MultiWindowUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplitScreenAction extends MenuActionType {
    public final Context mContext;

    private SplitScreenAction(Context context) {
        this.mContext = context;
    }

    public static SplitScreenAction createAction(FlexPanelActivity flexPanelActivity) {
        return new SplitScreenAction(flexPanelActivity);
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) {
        ActivityManager.RunningTaskInfo runningTaskExcept = ControlPanelUtils.getRunningTaskExcept(this.mContext);
        ComponentName componentName = runningTaskExcept != null ? runningTaskExcept.baseActivity : new ComponentName("", "");
        ActivityManager.RunningTaskInfo runningTaskExcept2 = ControlPanelUtils.getRunningTaskExcept(this.mContext);
        int i = runningTaskExcept2 != null ? runningTaskExcept2.userId : 0;
        ActivityManager.RunningTaskInfo runningTaskExcept3 = ControlPanelUtils.getRunningTaskExcept(this.mContext);
        this.mContext.startActivityAsUser(MultiWindowUtils.getEdgeAllAppsActivityIntent(componentName, i, runningTaskExcept3 != null ? runningTaskExcept3.taskId : -1), UserHandle.CURRENT);
    }
}
