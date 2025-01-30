package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NonResizeableAppsResult implements AppResult {
    @Override // com.android.wm.shell.draganddrop.AppResult
    public final String getContentType() {
        return null;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final ApplicationInfo getDragAppApplicationInfo() {
        throw new UnsupportedOperationException("getDragAppApplicationInfo not implemented by NonResizeableAppsResult");
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
