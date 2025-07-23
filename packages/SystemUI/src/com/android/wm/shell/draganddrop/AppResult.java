package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ActivityInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface AppResult {
    String getContentType();

    ActivityInfo getDragAppActivityInfo();

    boolean hasResizableResolveInfo();

    boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks);

    boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks);

    AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks);
}
