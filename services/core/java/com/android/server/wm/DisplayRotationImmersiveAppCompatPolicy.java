package com.android.server.wm;

import android.view.WindowInsets;

/* loaded from: classes3.dex */
public final class DisplayRotationImmersiveAppCompatPolicy {
  public final DisplayContent mDisplayContent;
  public final DisplayRotation mDisplayRotation;
  public final LetterboxConfiguration mLetterboxConfiguration;

  public static DisplayRotationImmersiveAppCompatPolicy createIfNeeded(
      LetterboxConfiguration letterboxConfiguration,
      DisplayRotation displayRotation,
      DisplayContent displayContent) {
    if (letterboxConfiguration.isDisplayRotationImmersiveAppCompatPolicyEnabled(false)) {
      return new DisplayRotationImmersiveAppCompatPolicy(
          letterboxConfiguration, displayRotation, displayContent);
    }
    return null;
  }

  public DisplayRotationImmersiveAppCompatPolicy(
      LetterboxConfiguration letterboxConfiguration,
      DisplayRotation displayRotation,
      DisplayContent displayContent) {
    this.mDisplayRotation = displayRotation;
    this.mLetterboxConfiguration = letterboxConfiguration;
    this.mDisplayContent = displayContent;
  }

  public boolean isRotationLockEnforced(int i) {
    boolean isRotationLockEnforcedLocked;
    if (!this.mLetterboxConfiguration.isDisplayRotationImmersiveAppCompatPolicyEnabled(true)) {
      return false;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplayContent.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        isRotationLockEnforcedLocked = isRotationLockEnforcedLocked(i);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return isRotationLockEnforcedLocked;
  }

  public final boolean isRotationLockEnforcedLocked(int i) {
    ActivityRecord activityRecord;
    return (!this.mDisplayContent.getIgnoreOrientationRequest()
            || (activityRecord = this.mDisplayContent.topRunningActivity()) == null
            || !hasRequestedToHideStatusAndNavBars(activityRecord)
            || activityRecord.getTask() == null
            || activityRecord.getTask().getWindowingMode() != 1
            || activityRecord.areBoundsLetterboxed()
            || activityRecord.getRequestedConfigurationOrientation() == 0
            || activityRecord.getRequestedConfigurationOrientation()
                == surfaceRotationToConfigurationOrientation(i))
        ? false
        : true;
  }

  public final boolean hasRequestedToHideStatusAndNavBars(ActivityRecord activityRecord) {
    WindowState findMainWindow = activityRecord.findMainWindow();
    return findMainWindow != null
        && (findMainWindow.getRequestedVisibleTypes()
                & (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars()))
            == 0;
  }

  public final int surfaceRotationToConfigurationOrientation(int i) {
    if (this.mDisplayRotation.isAnyPortrait(i)) {
      return 1;
    }
    return this.mDisplayRotation.isLandscapeOrSeascape(i) ? 2 : 0;
  }
}
