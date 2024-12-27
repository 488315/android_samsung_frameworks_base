package com.android.server.wm;

import android.view.WindowInsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayRotationImmersiveAppCompatPolicy {
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final DisplayContent mDisplayContent;
    public final DisplayRotation mDisplayRotation;

    public DisplayRotationImmersiveAppCompatPolicy(
            AppCompatConfiguration appCompatConfiguration,
            DisplayRotation displayRotation,
            DisplayContent displayContent) {
        this.mDisplayRotation = displayRotation;
        this.mAppCompatConfiguration = appCompatConfiguration;
        this.mDisplayContent = displayContent;
    }

    public final boolean isRotationLockEnforcedLocked(int i) {
        ActivityRecord activityRecord;
        WindowState findMainWindow;
        Task task;
        DisplayContent displayContent = this.mDisplayContent;
        if (!displayContent.getIgnoreOrientationRequest()
                || (activityRecord = displayContent.topRunningActivity(false)) == null
                || (findMainWindow = activityRecord.findMainWindow(true)) == null
                || (findMainWindow.mRequestedVisibleTypes
                                & (WindowInsets.Type.statusBars()
                                        | WindowInsets.Type.navigationBars()))
                        != 0
                || (task = activityRecord.task) == null
                || task.getWindowingMode() != 1
                || activityRecord.areBoundsLetterboxed()
                || activityRecord.getRequestedConfigurationOrientation() == 0) {
            return false;
        }
        int requestedConfigurationOrientation =
                activityRecord.getRequestedConfigurationOrientation();
        DisplayRotation displayRotation = this.mDisplayRotation;
        return requestedConfigurationOrientation
                != (displayRotation.isAnyPortrait(i)
                        ? 1
                        : displayRotation.isLandscapeOrSeascape(i) ? 2 : 0);
    }
}
