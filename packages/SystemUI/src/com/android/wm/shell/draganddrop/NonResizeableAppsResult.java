package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;

/* loaded from: classes3.dex */
public class NonResizeableAppsResult implements AppResult {
    @Override // com.android.wm.shell.draganddrop.AppResult
    public final String getContentType() {
        return null;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final ActivityInfo getDragAppActivityInfo() {
        throw new UnsupportedOperationException("getDragAppActivityInfo not implemented by NonResizeableAppsResult");
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResizableResolveInfo() {
        return false;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks) {
        throw new UnsupportedOperationException("hasResolveInfoInFullscreenOnly not implemented by NonResizeableAppsResult");
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks) {
        throw new UnsupportedOperationException("isAlreadyRunningSingleInstanceTask not implemented by NonResizeableAppsResult");
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks) {
        throw new UnsupportedOperationException("makeExecutableApp not implemented by NonResizeableAppsResult");
    }
}
