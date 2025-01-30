package com.android.wm.shell.common.split;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Intent;
import com.android.internal.util.ArrayUtils;
import com.android.wm.shell.ShellTaskOrganizer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitScreenUtils {
    public static String getPackageName(Intent intent) {
        if (intent == null || intent.getComponent() == null) {
            return null;
        }
        return intent.getComponent().getPackageName();
    }

    public static boolean isValidToSplit(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo != null && runningTaskInfo.supportsMultiWindow && ArrayUtils.contains(SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, runningTaskInfo.getActivityType()) && ArrayUtils.contains(SplitScreenConstants.CONTROLLED_WINDOWING_MODES, runningTaskInfo.getWindowingMode());
    }

    public static int reverseSplitPosition(int i) {
        if (i != 0) {
            return i != 1 ? -1 : 0;
        }
        return 1;
    }

    public static boolean samePackage(int i, int i2, String str, String str2) {
        return str != null && str.equals(str2) && i == i2;
    }

    public static String getPackageName(PendingIntent pendingIntent) {
        if (pendingIntent == null || pendingIntent.getIntent() == null) {
            return null;
        }
        return getPackageName(pendingIntent.getIntent());
    }

    public static String getPackageName(int i, ShellTaskOrganizer shellTaskOrganizer) {
        ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo != null) {
            return getPackageName(runningTaskInfo.baseIntent);
        }
        return null;
    }
}
