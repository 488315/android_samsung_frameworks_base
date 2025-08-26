package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.Size;
import android.window.DesktopModeFlags;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopTaskPosition;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.samsung.android.multiwindow.MultiWindowUtils;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class DesktopModeUtils {
    public static final int DESKTOP_MODE_LANDSCAPE_APP_PADDING;

    static {
        SystemProperties.getInt("persist.wm.debug.desktop_mode_initial_bounds_scale", 75);
        DESKTOP_MODE_LANDSCAPE_APP_PADDING = SystemProperties.getInt("persist.wm.debug.desktop_mode_landscape_app_padding", 25);
    }

    public static final float calculateAspectRatio(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Rect appBounds;
        if (runningTaskInfo.appCompatTaskInfo.topActivityAppBounds.isEmpty()) {
            appBounds = runningTaskInfo.configuration.windowConfiguration.getAppBounds();
            if (appBounds == null) {
                appBounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            }
        } else {
            appBounds = runningTaskInfo.appCompatTaskInfo.topActivityAppBounds;
            appBounds.getClass();
        }
        return Math.max(appBounds.height(), appBounds.width()) / Math.min(appBounds.height(), appBounds.width());
    }

    public static final Rect calculateDefaultDesktopTaskBounds(DisplayLayout displayLayout) {
        int i = (int) (displayLayout.mWidth * MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX.x);
        int i2 = (int) (displayLayout.mHeight * MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX.y);
        int i3 = (displayLayout.mHeight - i2) / 2;
        int i4 = (displayLayout.mWidth - i) / 2;
        return new Rect(i4, i3, i + i4, i2 + i3);
    }

    public static Rect calculateInitialBounds$default(DisplayLayout displayLayout, ActivityManager.RunningTaskInfo runningTaskInfo, int i, Integer num, int i2) {
        if ((i2 & 8) != 0) {
            i = 0;
        }
        if ((i2 & 16) != 0) {
            num = null;
        }
        Rect rect = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        float fCalculateAspectRatio = calculateAspectRatio(runningTaskInfo);
        float fWidth = rect.width();
        PointF pointF = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX;
        Size size = new Size((int) (fWidth * pointF.x), (int) (rect.height() * pointF.y));
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
        int iIntValue = num != null ? num.intValue() : activityInfo.screenOrientation;
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo)) {
            Rect rect3 = new Rect();
            if (i == 0) {
                i = displayLayout.mCaptionInsets;
            }
            MultiWindowUtils.calculateDesktopCompatInitialBounds(rect3, iIntValue, displayLayout.mWidth, displayLayout.mHeight, i);
            return positionInScreen(rect2, new Size(rect3.width(), rect3.height()));
        }
        int i5 = runningTaskInfo.configuration.orientation;
        if (i5 == 1) {
            int iWidth = rect.width() - (DESKTOP_MODE_LANDSCAPE_APP_PADDING * 2);
            if (!((TaskInfo) runningTaskInfo).isResizeable || ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasMinAspectRatioOverride()) {
                size = ActivityInfo.isFixedOrientationLandscape(iIntValue) ? maximizeSizeGivenAspectRatio(runningTaskInfo, new Size(iWidth, size.getHeight()), fCalculateAspectRatio, i, Integer.valueOf(iIntValue)) : maximizeSizeGivenAspectRatio(runningTaskInfo, size, fCalculateAspectRatio, i, Integer.valueOf(iIntValue));
            } else if (ActivityInfo.isFixedOrientationLandscape(iIntValue)) {
                size = new Size(iWidth, runningTaskInfo.appCompatTaskInfo.topActivityAppBounds.height());
            }
        } else if (i5 == 2) {
            if (!((TaskInfo) runningTaskInfo).isResizeable || ((TaskInfo) runningTaskInfo).appCompatTaskInfo.hasMinAspectRatioOverride()) {
                size = maximizeSizeGivenAspectRatio(runningTaskInfo, size, fCalculateAspectRatio, i, Integer.valueOf(iIntValue));
            } else if (ActivityInfo.isFixedOrientationPortrait(iIntValue)) {
                size = new Size(runningTaskInfo.appCompatTaskInfo.topActivityAppBounds.width(), size.getHeight());
            }
        }
        return positionInScreen(rect2, size);
    }

    public static final Rect calculateMaximizeBounds(DisplayLayout displayLayout, ActivityManager.RunningTaskInfo runningTaskInfo) {
        Size sizeMaximizeSizeGivenAspectRatio;
        Rect rect = new Rect();
        displayLayout.getStableBoundsByInsetsVisibility(rect);
        if (runningTaskInfo.isResizeable) {
            return new Rect(rect);
        }
        float fCalculateAspectRatio = calculateAspectRatio(runningTaskInfo);
        Rect appBounds = runningTaskInfo.configuration.windowConfiguration.getAppBounds();
        int i = appBounds != null ? appBounds.top - runningTaskInfo.configuration.windowConfiguration.getBounds().top : 0;
        DesktopStateImpl.Companion.getClass();
        Integer num = null;
        if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo)) {
            Size size = new Size(rect.width(), rect.height());
            int i2 = displayLayout.mCaptionInsets;
            if (i2 != 0) {
                i = i2;
            }
            int i3 = runningTaskInfo.configuration.orientation;
            if (i3 == 1) {
                num = 1;
            } else if (i3 == 2) {
                num = 0;
            }
            sizeMaximizeSizeGivenAspectRatio = maximizeSizeGivenAspectRatio(runningTaskInfo, size, fCalculateAspectRatio, i, num);
        } else {
            sizeMaximizeSizeGivenAspectRatio = maximizeSizeGivenAspectRatio(runningTaskInfo, new Size(rect.width(), rect.height()), fCalculateAspectRatio, i, null);
        }
        return centerInArea(sizeMaximizeSizeGivenAspectRatio, rect, rect.left, rect.top);
    }

    public static final Rect centerInArea(Size size, Rect rect, int i, int i2) {
        int iHeight = (rect.height() - size.getHeight()) / 2;
        int iWidth = ((rect.width() - size.getWidth()) / 2) + i;
        int i3 = i2 + iHeight;
        return new Rect(iWidth, i3, size.getWidth() + iWidth, size.getHeight() + i3);
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
        Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        if (bounds.width() == displayLayout.mWidth && bounds.height() == displayLayout.mHeight) {
            return false;
        }
        displayLayout.getStableBoundsByInsetsVisibility(rect);
        Rect bounds2 = runningTaskInfo.configuration.windowConfiguration.getBounds();
        return runningTaskInfo.isResizeable ? Intrinsics.areEqual(bounds2, rect) : bounds2.width() == rect.width() || bounds2.height() == rect.height();
    }

    public static final Size maximizeSizeGivenAspectRatio(ActivityManager.RunningTaskInfo runningTaskInfo, Size size, float f, int i, Integer num) {
        int iCeil;
        double dCeil;
        int height = size.getHeight() - i;
        int width = size.getWidth();
        if (num == null) {
            ActivityInfo activityInfo = runningTaskInfo.topActivityInfo;
            num = activityInfo != null ? Integer.valueOf(activityInfo.screenOrientation) : null;
        }
        int iIntValue = num != null ? num.intValue() : -1;
        Rect appBounds = ((TaskInfo) runningTaskInfo).configuration.windowConfiguration.getAppBounds();
        if (iIntValue != -1 ? ActivityInfo.isFixedOrientationPortrait(iIntValue) : ((TaskInfo) runningTaskInfo).appCompatTaskInfo.isTopActivityLetterboxed() ? ((TaskInfo) runningTaskInfo).appCompatTaskInfo.isTopActivityPillarboxShaped() : appBounds != null ? appBounds.height() > appBounds.width() : ActivityInfo.isFixedOrientationPortrait(((TaskInfo) runningTaskInfo).configuration.orientation)) {
            iCeil = (int) Math.ceil(height / f);
            if (iCeil > width) {
                dCeil = Math.ceil(width * f);
                height = (int) dCeil;
            }
            width = iCeil;
        } else {
            iCeil = (int) Math.ceil(height * f);
            if (iCeil > width) {
                dCeil = Math.ceil(width / f);
                height = (int) dCeil;
            }
            width = iCeil;
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
