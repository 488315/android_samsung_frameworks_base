package com.android.wm.shell.compatui.coverlauncher;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.TaskInfo;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.wm.shell.compatui.CompatUIController;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverLauncherAppCompatUIController {
    public final AccessibilityManager mAccessibilityManager;
    public final CompatUIController mController;
    public final CoverLauncherAppCompatUIWindowManager mCoverLauncherAppCompatUIWindowManager;
    public TaskInfo mTaskInfo;
    public int mAlignment = 17;
    public final IActivityTaskManager mActivityTaskManager = ActivityTaskManager.getService();
    public final Handler mHandler = new Handler(Looper.myLooper());

    public CoverLauncherAppCompatUIController(Context context, CoverLauncherAppCompatUIWindowManager coverLauncherAppCompatUIWindowManager, TaskInfo taskInfo, CompatUIController compatUIController) {
        this.mCoverLauncherAppCompatUIWindowManager = coverLauncherAppCompatUIWindowManager;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mTaskInfo = taskInfo;
        this.mController = compatUIController;
    }

    public final void setCoverLauncherAppCompatAlignment(int i) {
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_UI) {
            int i2 = this.mAlignment;
            int i3 = i2 & 112;
            if (i == 3) {
                this.mAlignment = (i2 & 5) == 5 ? i3 | 1 : i3 | 3;
            } else {
                if (i != 5) {
                    Log.i("CoverLauncherAppCompatUIController", "Nothing to do");
                    return;
                }
                this.mAlignment = (i2 & 3) == 3 ? i3 | 1 : i3 | 5;
            }
            try {
                this.mActivityTaskManager.setCoverLauncherAppCompatAlignment(this.mAlignment);
            } catch (RemoteException e) {
                Log.e("CoverLauncherAppCompatUIController", "Failed to set app compat alignment", e);
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CoverLauncherAppCompatUIController{taskId=");
        sb.append(this.mTaskInfo.taskId);
        sb.append("(");
        sb.append(this.mTaskInfo.baseActivity.getPackageName());
        sb.append("), taskConfig=");
        sb.append(this.mTaskInfo.getConfiguration());
        sb.append(", activityBounds=");
        sb.append(this.mTaskInfo.appCompatTaskInfo.topActivityBounds);
        sb.append(", alignment=0x");
        sb.append(Integer.toHexString(this.mAlignment));
        sb.append(", resizeable=");
        sb.append(this.mTaskInfo.isResizeable);
        sb.append(", visible=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mTaskInfo.isVisible, "}");
    }
}
