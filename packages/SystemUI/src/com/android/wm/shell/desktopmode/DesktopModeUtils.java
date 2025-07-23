package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.Size;
import android.view.InsetsState;
import android.view.WindowInsets;
import android.window.DesktopModeFlags;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopTaskPosition;
import com.samsung.android.multiwindow.MultiWindowUtils;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class DesktopModeUtils {
    public static final float DESKTOP_MODE_INITIAL_BOUNDS_SCALE = SystemProperties.getInt("persist.wm.debug.desktop_mode_initial_bounds_scale", 75) / 100.0f;
    public static final int DESKTOP_MODE_LANDSCAPE_APP_PADDING = SystemProperties.getInt("persist.wm.debug.desktop_mode_landscape_app_padding", 25);

    public static final float calculateAspectRatio(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Rect rect;
        if (runningTaskInfo.appCompatTaskInfo.topActivityAppBounds.isEmpty()) {
            rect = runningTaskInfo.configuration.windowConfiguration.getAppBounds();
            if (rect == null) {
                rect = runningTaskInfo.configuration.windowConfiguration.getBounds();
            }
        } else {
            rect = runningTaskInfo.appCompatTaskInfo.topActivityAppBounds;
            rect.getClass();
        }
        return Math.max(rect.height(), rect.width()) / Math.min(rect.height(), rect.width());
    }

    public static final Rect calculateDefaultDesktopTaskBounds(DisplayLayout displayLayout) {
        int i = displayLayout.mWidth;
        float f = DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        int i2 = (int) (i * f);
        int i3 = displayLayout.mHeight;
        int i4 = (int) (i3 * f);
        int i5 = (i3 - i4) / 2;
        int i6 = (i - i2) / 2;
        return new Rect(i6, i5, i2 + i6, i4 + i5);
    }

    public static Rect calculateInitialBounds$default(DisplayLayout displayLayout, ActivityManager.RunningTaskInfo runningTaskInfo, int i, Integer num, int i2) {
        if ((i2 & 8) != 0) {
            i = 0;
        }
        if ((i2 & 16) != 0) {
            num = null;
        }
        Rect rect = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        float calculateAspectRatio = calculateAspectRatio(runningTaskInfo);
        float width = rect.width();
        PointF pointF = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX;
        Size size = new Size((int) (width * pointF.x), (int) (rect.height() * pointF.y));
        Rect rect2 = new Rect();
        displayLayout.getStableBounds(rect2, false);
        int i3 = displayLayout.mNavBarFrameHeight;
        int i4 = displayLayout.mTaskbarFrameHeight;
        if (i3 != i4) {
            rect2.bottom = displayLayout.mHeight - i4;
        }
        if (runningTaskInfo.appCompatTaskInfo.isUserFullscreenOverrideEnabled() || runningTaskInfo.appCompatTaskInfo.isSystemFullscreenOverrideEnabled()) {
            return positionInScreen(rect2, size);
        }
        ActivityInfo activityInfo = runningTaskInfo.topActivityInfo;
        if (activityInfo == null) {
            return positionInScreen(rect2, size);
        }
        int intValue = num != null ? num.intValue() : activityInfo.screenOrientation;
        int i5 = runningTaskInfo.configuration.orientation;
        if (i5 == 1) {
            int width2 = rect.width() - (DESKTOP_MODE_LANDSCAPE_APP_PADDING * 2);
            if (!((TaskInfo) runningTaskInfo).isResizeable || ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasMinAspectRatioOverride()) {
                size = ActivityInfo.isFixedOrientationLandscape(intValue) ? maximizeSizeGivenAspectRatio(runningTaskInfo, new Size(width2, size.getHeight()), calculateAspectRatio, i, Integer.valueOf(intValue)) : maximizeSizeGivenAspectRatio(runningTaskInfo, size, calculateAspectRatio, i, Integer.valueOf(intValue));
            } else if (ActivityInfo.isFixedOrientationLandscape(intValue)) {
                size = new Size(width2, runningTaskInfo.appCompatTaskInfo.topActivityAppBounds.height());
            }
        } else if (i5 == 2) {
            if (!((TaskInfo) runningTaskInfo).isResizeable || ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasMinAspectRatioOverride()) {
                size = maximizeSizeGivenAspectRatio(runningTaskInfo, size, calculateAspectRatio, i, Integer.valueOf(intValue));
            } else if (ActivityInfo.isFixedOrientationPortrait(intValue)) {
                size = new Size(runningTaskInfo.appCompatTaskInfo.topActivityAppBounds.width(), size.getHeight());
            }
        }
        return positionInScreen(rect2, size);
    }

    public static final Rect calculateMaximizeBounds(DisplayLayout displayLayout, ActivityManager.RunningTaskInfo runningTaskInfo) {
        Rect rect = new Rect();
        InsetsState insetsState = displayLayout.mInsetsState;
        Insets calculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars(), true);
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        rect.inset(calculateInsets);
        if (runningTaskInfo.isResizeable) {
            return new Rect(rect);
        }
        float calculateAspectRatio = calculateAspectRatio(runningTaskInfo);
        Rect appBounds = runningTaskInfo.configuration.windowConfiguration.getAppBounds();
        Size maximizeSizeGivenAspectRatio = maximizeSizeGivenAspectRatio(runningTaskInfo, new Size(rect.width(), rect.height()), calculateAspectRatio, appBounds != null ? appBounds.top - runningTaskInfo.configuration.windowConfiguration.getBounds().top : 0, null);
        int i = rect.left;
        int i2 = rect.top;
        int height = (rect.height() - maximizeSizeGivenAspectRatio.getHeight()) / 2;
        int width = ((rect.width() - maximizeSizeGivenAspectRatio.getWidth()) / 2) + i;
        int i3 = i2 + height;
        return new Rect(width, i3, maximizeSizeGivenAspectRatio.getWidth() + width, maximizeSizeGivenAspectRatio.getHeight() + i3);
    }

    public static final Rect getInheritedExistingTaskBounds(DesktopRepository desktopRepository, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        Integer num;
        ActivityInfo activityInfo;
        if (!DesktopModeFlags.INHERIT_TASK_BOUNDS_FOR_TRAMPOLINE_TASK_LAUNCHES.isTrue() || (num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(desktopRepository.getExpandedTasksIdsInDeskOrdered(i))) == null) {
            return null;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(num.intValue());
        ComponentName componentName = runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null;
        ComponentName componentName2 = runningTaskInfo.topActivity;
        int flags = runningTaskInfo.baseIntent.getFlags();
        ActivityInfo activityInfo2 = runningTaskInfo.topActivityInfo;
        int i2 = activityInfo2 != null ? activityInfo2.launchMode : 0;
        if (componentName == null || componentName2 == null || !Intrinsics.areEqual(componentName.getPackageName(), componentName2.getPackageName())) {
            return null;
        }
        ActivityInfo activityInfo3 = runningTaskInfo.topActivityInfo;
        if ((activityInfo3 != null ? activityInfo3.targetActivity : null) == null) {
            if (((runningTaskInfo2 == null || (activityInfo = runningTaskInfo2.topActivityInfo) == null) ? null : activityInfo.targetActivity) != null || !runningTaskInfo.isAliasManaged) {
                if ((i2 == 2 || i2 == 3 || i2 == 4) && ((32768 & flags) != 0 || (134217728 & flags) == 0)) {
                    return runningTaskInfo2.configuration.windowConfiguration.getBounds();
                }
                return null;
            }
        }
        return null;
    }

    public static final boolean isTaskMaximized(ActivityManager.RunningTaskInfo runningTaskInfo, DisplayController displayController) {
        DisplayLayout displayLayout = displayController.getDisplayLayout(runningTaskInfo.displayId);
        if (displayLayout == null) {
            throw new IllegalStateException(("Could not get display layout for display=" + runningTaskInfo.displayId).toString());
        }
        Rect rect = new Rect();
        InsetsState insetsState = displayLayout.mInsetsState;
        Insets calculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars(), true);
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        rect.inset(calculateInsets);
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        return runningTaskInfo.isResizeable ? Intrinsics.areEqual(bounds, rect) : bounds.width() == rect.width() || bounds.height() == rect.height();
    }

    public static final Size maximizeSizeGivenAspectRatio(ActivityManager.RunningTaskInfo runningTaskInfo, Size size, float f, int i, Integer num) {
        int ceil;
        double ceil2;
        int height = size.getHeight() - i;
        int width = size.getWidth();
        if (num == null) {
            ActivityInfo activityInfo = runningTaskInfo.topActivityInfo;
            num = activityInfo != null ? Integer.valueOf(activityInfo.screenOrientation) : null;
        }
        int intValue = num != null ? num.intValue() : -1;
        Rect appBounds = ((TaskInfo) runningTaskInfo).configuration.windowConfiguration.getAppBounds();
        if (intValue != -1 ? ActivityInfo.isFixedOrientationPortrait(intValue) : ((TaskInfo) runningTaskInfo).appCompatTaskInfo.isTopActivityLetterboxed() ? ((TaskInfo) runningTaskInfo).appCompatTaskInfo.isTopActivityPillarboxShaped() : appBounds != null ? appBounds.height() > appBounds.width() : ActivityInfo.isFixedOrientationPortrait(((TaskInfo) runningTaskInfo).configuration.orientation)) {
            ceil = (int) Math.ceil(height / f);
            if (ceil > width) {
                ceil2 = Math.ceil(width * f);
                height = (int) ceil2;
            }
            width = ceil;
        } else {
            ceil = (int) Math.ceil(height * f);
            if (ceil > width) {
                ceil2 = Math.ceil(width / f);
                height = (int) ceil2;
            }
            width = ceil;
        }
        return new Size(width, height + i);
    }

    public static final Rect positionInScreen(Rect rect, Size size) {
        Rect rect2 = new Rect(0, 0, size.getWidth(), size.getHeight());
        Point topLeftCoordinates = DesktopTaskPosition.Center.INSTANCE.getTopLeftCoordinates(rect, rect2);
        rect2.offsetTo(topLeftCoordinates.x, topLeftCoordinates.y);
        return rect2;
    }
}
