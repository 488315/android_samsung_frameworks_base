package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;

/* loaded from: classes3.dex */
public class DefaultAppResult extends BaseAppResult {
    public DefaultAppResult(ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, String str) {
        super(multiInstanceBlockList, multiInstanceAllowList, str);
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final ActivityInfo getDragAppActivityInfo() {
        return null;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResizableResolveInfo() {
        return true;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks) {
        return false;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks) {
        return false;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks) {
        return new AppInfo(null, null, false);
    }
}
