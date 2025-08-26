package com.android.wm.shell.windowdecor.common;

import android.app.ActivityManager;
import android.graphics.Point;
import android.graphics.Rect;
import com.android.wm.shell.splitscreen.SplitScreenController;

/* loaded from: classes3.dex */
public abstract class DesktopMenuPositionUtilityKt {
    public static final Point calculateMenuPosition(SplitScreenController splitScreenController, ActivityManager.RunningTaskInfo runningTaskInfo, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        if (runningTaskInfo.isFreeform()) {
            Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            return z ? new Point((bounds.right - i6) - i, bounds.top + i2) : new Point(bounds.left + i, bounds.top + i2);
        }
        Point point = new Point(((i5 / 2) + i3) - (i6 / 2), i4);
        if (runningTaskInfo.getWindowingMode() == 1) {
            return new Point(point.x, point.y + i2);
        }
        int splitPosition = splitScreenController.getSplitPosition(runningTaskInfo.taskId);
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        splitScreenController.getRefStageBounds(rect, rect2);
        if (splitScreenController.isLeftRightSplit()) {
            return new Point((splitPosition == 1 ? rect2.left : 0) + point.x, point.y + i2);
        }
        return new Point(point.x, point.y + (splitPosition == 1 ? rect2.top : 0) + i2);
    }
}
