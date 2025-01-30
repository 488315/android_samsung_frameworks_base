package com.android.server.wm;

/* loaded from: classes3.dex */
public interface SizeCompatPolicySupports {
  boolean supportsFreeform();

  default boolean supportsFullScreen() {
    return true;
  }

  boolean supportsIgnoreOrientationRequest();

  boolean supportsMockFullScreen();

  boolean supportsSandboxDisplay(ActivityRecord activityRecord);

  default boolean supportsSandboxInsetsHint(ActivityRecord activityRecord) {
    return false;
  }

  boolean supportsSandboxMotionEvent(ActivityRecord activityRecord);

  boolean supportsSandboxViewBounds(ActivityRecord activityRecord);
}
