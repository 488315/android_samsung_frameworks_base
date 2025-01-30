package com.android.server.wm;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class DisplayRotationReversionController {
  public final DisplayContent mDisplayContent;
  public int mUserRotationModeOverridden;
  public int mUserRotationOverridden = -1;
  public final boolean[] mSlots = new boolean[3];

  public DisplayRotationReversionController(DisplayContent displayContent) {
    this.mDisplayContent = displayContent;
  }

  public boolean isRotationReversionEnabled() {
    DisplayContent displayContent = this.mDisplayContent;
    return (displayContent.mDisplayRotationCompatPolicy == null
            && displayContent.getDisplayRotation().mFoldController == null
            && !this.mDisplayContent.getIgnoreOrientationRequest())
        ? false
        : true;
  }

  public void beforeOverrideApplied(int i) {
    if (this.mSlots[i]) {
      return;
    }
    maybeSaveUserRotation();
    this.mSlots[i] = true;
  }

  public boolean isOverrideActive(int i) {
    return this.mSlots[i];
  }

  public boolean[] getSlotsCopy() {
    if (isRotationReversionEnabled()) {
      return (boolean[]) this.mSlots.clone();
    }
    return null;
  }

  public void updateForNoSensorOverride() {
    if (!this.mSlots[0]) {
      if (isTopFullscreenActivityNoSensor()) {
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_ORIENTATION, -1639406696, 0, (String) null, (Object[]) null);
        }
        beforeOverrideApplied(0);
        return;
      }
      return;
    }
    if (isTopFullscreenActivityNoSensor()) {
      return;
    }
    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ORIENTATION, 138097009, 0, (String) null, (Object[]) null);
    }
    revertOverride(0);
  }

  public boolean isAnyOverrideActive() {
    for (int i = 0; i < 3; i++) {
      if (this.mSlots[i]) {
        return true;
      }
    }
    return false;
  }

  public boolean revertOverride(int i) {
    boolean[] zArr = this.mSlots;
    if (!zArr[i]) {
      return false;
    }
    zArr[i] = false;
    if (isAnyOverrideActive()) {
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION, -1397175017, 0, (String) null, (Object[]) null);
      }
      return false;
    }
    if (!this.mDisplayContent.getDisplayRotation().isRotationFrozen()) {
      return false;
    }
    this.mDisplayContent
        .getDisplayRotation()
        .setUserRotation(this.mUserRotationModeOverridden, this.mUserRotationOverridden);
    return true;
  }

  public final void maybeSaveUserRotation() {
    if (isAnyOverrideActive()) {
      return;
    }
    this.mUserRotationModeOverridden =
        this.mDisplayContent.getDisplayRotation().getUserRotationMode();
    this.mUserRotationOverridden = this.mDisplayContent.getDisplayRotation().getUserRotation();
  }

  public final boolean isTopFullscreenActivityNoSensor() {
    ActivityRecord activityRecord;
    Task task =
        this.mDisplayContent.getTask(
            new Predicate() { // from class:
                              // com.android.server.wm.DisplayRotationReversionController$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$isTopFullscreenActivityNoSensor$0;
                lambda$isTopFullscreenActivityNoSensor$0 =
                    DisplayRotationReversionController.lambda$isTopFullscreenActivityNoSensor$0(
                        (Task) obj);
                return lambda$isTopFullscreenActivityNoSensor$0;
              }
            });
    return (task == null
            || (activityRecord = task.topRunningActivity()) == null
            || activityRecord.getOrientation() != 5)
        ? false
        : true;
  }

  public static /* synthetic */ boolean lambda$isTopFullscreenActivityNoSensor$0(Task task) {
    return task.getWindowingMode() == 1;
  }
}
