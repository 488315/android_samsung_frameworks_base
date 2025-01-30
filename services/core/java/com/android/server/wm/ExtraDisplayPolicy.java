package com.android.server.wm;

/* loaded from: classes3.dex */
public interface ExtraDisplayPolicy {
  default int getOtherDisplayId(int i) {
    return -1;
  }

  default boolean hasCoverHome(int i) {
    return false;
  }

  default boolean isDisplayControlledByPolicy(int i) {
    return false;
  }

  default boolean shouldChooseDefaultTaskDisplayArea(int i) {
    return false;
  }

  default boolean shouldNotHandleForcedResizableTaskIfNeeded(int i, int i2) {
    return false;
  }

  default boolean shouldNotTopDisplay(int i) {
    return false;
  }

  default boolean shouldSkipAppTransition(int i) {
    return false;
  }
}
