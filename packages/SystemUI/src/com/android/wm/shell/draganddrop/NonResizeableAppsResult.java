package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
