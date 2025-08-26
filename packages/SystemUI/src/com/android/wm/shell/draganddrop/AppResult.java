package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;

/* loaded from: classes3.dex */
public interface AppResult {
    String getContentType();

    ActivityInfo getDragAppActivityInfo();

    boolean hasResizableResolveInfo();

    boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks);

    boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks);

    AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks);
}
