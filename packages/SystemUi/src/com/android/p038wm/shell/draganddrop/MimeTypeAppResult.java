package com.android.p038wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import com.android.p038wm.shell.draganddrop.ExecutableAppHolder;
import com.samsung.android.multiwindow.MultiWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MimeTypeAppResult extends BaseAppResult {
    public final ActivityInfo mActivityInfo;

    public MimeTypeAppResult(ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, ActivityInfo activityInfo, String str) {
        super(multiInstanceBlockList, multiInstanceAllowList, str);
        this.mActivityInfo = activityInfo;
    }

    @Override // com.android.p038wm.shell.draganddrop.AppResult
    public final ApplicationInfo getDragAppApplicationInfo() {
        ActivityInfo activityInfo = this.mActivityInfo;
        if (activityInfo == null) {
            return null;
        }
        return activityInfo.applicationInfo;
    }

    @Override // com.android.p038wm.shell.draganddrop.AppResult
    public final boolean hasResizableResolveInfo() {
        ActivityInfo activityInfo = this.mActivityInfo;
        return activityInfo == null || (MultiWindowManager.getInstance().getSupportedMultiWindowModes(activityInfo) & 3) != 0;
    }

    @Override // com.android.p038wm.shell.draganddrop.AppResult
    public final boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks) {
        ActivityInfo activityInfo = this.mActivityInfo;
        if (activityInfo == null) {
            return false;
        }
        return isVisibleSingleInstance(visibleTasks.getFullscreenTasks(), activityInfo);
    }

    @Override // com.android.p038wm.shell.draganddrop.AppResult
    public final boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks) {
        ActivityInfo activityInfo = this.mActivityInfo;
        if (activityInfo == null) {
            return false;
        }
        return isVisibleSingleInstance(visibleTasks.getVisibleTasks(), activityInfo);
    }

    @Override // com.android.p038wm.shell.draganddrop.AppResult
    public final AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks) {
        ActivityInfo activityInfo = this.mActivityInfo;
        if (activityInfo == null) {
            return new AppInfo(null, null, false);
        }
        if (isVisibleSingleInstance(visibleTasks.getTasksException(i), activityInfo)) {
            return null;
        }
        return new AppInfo(null, null, false);
    }
}
