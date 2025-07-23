package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MimeTypeAppResult extends BaseAppResult {
    public final ActivityInfo mActivityInfo;

    public MimeTypeAppResult(ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, ActivityInfo activityInfo, String str) {
        super(multiInstanceBlockList, multiInstanceAllowList, str);
        this.mActivityInfo = activityInfo;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final ActivityInfo getDragAppActivityInfo() {
        return this.mActivityInfo;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResizableResolveInfo() {
        ActivityInfo activityInfo = this.mActivityInfo;
        return activityInfo == null || (MultiWindowManager.getInstance().getSupportedMultiWindowModes(activityInfo) & 3) != 0;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks) {
        if (this.mActivityInfo == null) {
            return false;
        }
        return isVisibleSingleInstance(visibleTasks.getFullscreenTasks(), this.mActivityInfo, false);
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks) {
        if (this.mActivityInfo == null) {
            return false;
        }
        visibleTasks.getClass();
        List visibleTasks2 = MultiWindowManager.getInstance().getVisibleTasks();
        visibleTasks2.removeIf(new VisibleTasks$$ExternalSyntheticLambda0(visibleTasks));
        return isVisibleSingleInstance(visibleTasks2, this.mActivityInfo, false);
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks) {
        if (this.mActivityInfo == null) {
            return new AppInfo(null, null, false);
        }
        if (isVisibleSingleInstance(visibleTasks.getTasksException(i), this.mActivityInfo, false)) {
            return null;
        }
        return new AppInfo(null, null, false);
    }
}
