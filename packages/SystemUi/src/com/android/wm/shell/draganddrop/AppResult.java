package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface AppResult {
    String getContentType();

    ApplicationInfo getDragAppApplicationInfo();

    boolean hasResizableResolveInfo();

    boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks);

    boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks);

    AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks);
}
