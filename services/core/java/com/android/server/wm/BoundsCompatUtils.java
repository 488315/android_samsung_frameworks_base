package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.view.DisplayInfo;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;

/* compiled from: BoundsCompatController.java */
/* loaded from: classes3.dex */
public class BoundsCompatUtils {
  public final BoundsCompatAlignment mDefaultDisplayAlignment;
  public float mDefaultDisplayAspectRatio;
  public boolean mSupportsBoundsCompat;
  public final Rect mTmpStableBounds;

  /* compiled from: BoundsCompatController.java */
  public abstract class LazyHolder {
    public static BoundsCompatUtils sBoundsCompatUtils = new BoundsCompatUtils();
  }

  public static BoundsCompatUtils get() {
    return LazyHolder.sBoundsCompatUtils;
  }

  public static void setSupportsBoundsCompat(boolean z) {
    get().mSupportsBoundsCompat = z;
  }

  public static boolean isSupportsBoundsCompat() {
    return get().mSupportsBoundsCompat;
  }

  public BoundsCompatUtils() {
    this.mSupportsBoundsCompat = true;
    this.mDefaultDisplayAlignment = new BoundsCompatAlignment();
    this.mTmpStableBounds = new Rect();
  }

  public void onConfigurationChanged(DisplayContent displayContent, Configuration configuration) {
    if (displayContent.isDefaultDisplay) {
      this.mDefaultDisplayAlignment.setDisplayContent(displayContent);
      Rect appBounds = configuration.windowConfiguration.getAppBounds();
      if (appBounds == null) {
        appBounds =
            new Rect(0, 0, displayContent.mBaseDisplayWidth, displayContent.mBaseDisplayHeight);
      }
      this.mDefaultDisplayAspectRatio = ActivityRecord.computeAspectRatio(appBounds);
    }
  }

  public boolean shouldApplyMinAspectRatio(DisplayContent displayContent) {
    if (displayContent == null || !displayContent.isDefaultDisplay) {
      return false;
    }
    float f = this.mDefaultDisplayAspectRatio;
    return f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f < 1.86f;
  }

  public BoundsCompatAlignment getDefaultDisplayAlignment() {
    return this.mDefaultDisplayAlignment;
  }

  public float getDefaultDisplayAspectRatio() {
    return this.mDefaultDisplayAspectRatio;
  }

  public void adjustBoundsAsMinAspectRatio(
      ActivityRecord activityRecord, Configuration configuration) {
    Rect bounds = configuration.windowConfiguration.getBounds();
    Rect appBounds = configuration.windowConfiguration.getAppBounds();
    Configuration resolvedOverrideConfiguration = activityRecord.getResolvedOverrideConfiguration();
    Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
    Rect appBounds2 = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
    boolean z = resolvedOverrideConfiguration.orientation == 2;
    BoundsCompatAlignment globalBoundsCompatAlignmentLocked =
        BoundsCompatAlignmentController.getGlobalBoundsCompatAlignmentLocked();
    if (z) {
      bounds2.left = bounds.left;
      bounds2.right = bounds.right;
      boolean z2 = CoreRune.FW_BOUNDS_COMPAT_DISPLAY_CENTER_ALIGNMENT;
      if (z2 && activityRecord.mCompatRecord.mRestrictedBounds) {
        return;
      }
      if (!z2 || !globalBoundsCompatAlignmentLocked.isCenterVertical()) {
        bounds = appBounds;
      }
      int verticalOffset = globalBoundsCompatAlignmentLocked.getVerticalOffset(bounds, appBounds2);
      if (verticalOffset != 0) {
        bounds2.offset(0, verticalOffset);
        appBounds2.offset(0, verticalOffset);
        return;
      }
      return;
    }
    bounds2.top = bounds.top;
    bounds2.bottom = bounds.bottom;
    int horizontalOffset =
        globalBoundsCompatAlignmentLocked.getHorizontalOffset(appBounds, appBounds2);
    if (appBounds2.left != horizontalOffset) {
      bounds2.offsetTo(horizontalOffset, bounds2.top);
      appBounds2.offsetTo(horizontalOffset, appBounds2.top);
    }
  }

  public void adjustBoundsAsSizeCompatMode(
      ActivityRecord activityRecord, Configuration configuration) {
    int i;
    int i2;
    int i3;
    DisplayContent displayContent;
    boolean z = true;
    boolean z2 = activityRecord.getResolvedOverrideConfiguration().orientation == 2;
    if (z2 && !activityRecord.isDisplayCompatModeEnabled()) {
      z = false;
    }
    BoundsCompatAlignment globalBoundsCompatAlignmentLocked =
        BoundsCompatAlignmentController.getGlobalBoundsCompatAlignmentLocked();
    Rect bounds = configuration.windowConfiguration.getBounds();
    Rect appBounds = configuration.windowConfiguration.getAppBounds();
    Configuration resolvedOverrideConfiguration = activityRecord.getResolvedOverrideConfiguration();
    Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
    Rect appBounds2 = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
    Rect sizeCompatBounds = activityRecord.getSizeCompatBounds();
    int i4 = appBounds.left;
    if (!CoreRune.FW_BOUNDS_COMPAT_DISPLAY_CENTER_ALIGNMENT
        || !z2
        || !globalBoundsCompatAlignmentLocked.isCenterVertical()) {
      bounds = appBounds;
    }
    int i5 = bounds.top;
    if (sizeCompatBounds != null) {
      sizeCompatBounds.offsetTo(i4, i5);
    }
    int i6 = z ? i4 - bounds2.left : 0;
    int i7 = z2 ? i5 - bounds2.top : 0;
    bounds2.offset(i6, i7);
    appBounds2.offset(i6, i7);
    if (z) {
      i = globalBoundsCompatAlignmentLocked.getHorizontalOffset(bounds, appBounds2);
      i2 =
          sizeCompatBounds != null
              ? globalBoundsCompatAlignmentLocked.getHorizontalOffset(bounds, sizeCompatBounds)
              : 0;
    } else {
      i = 0;
      i2 = 0;
    }
    if (z2) {
      r3 = globalBoundsCompatAlignmentLocked.getVerticalOffset(bounds, appBounds2);
      i3 =
          sizeCompatBounds != null
              ? globalBoundsCompatAlignmentLocked.getVerticalOffset(bounds, sizeCompatBounds)
              : 0;
    } else if (sizeCompatBounds == null
        || (displayContent = activityRecord.mDisplayContent) == null
        || (i3 = getStableBounds(displayContent).top) <= 0) {
      i3 = 0;
    } else {
      r3 = i3;
    }
    if (i != 0 || r3 != 0) {
      bounds2.offset(i, r3);
      appBounds2.offset(i, r3);
    }
    if (sizeCompatBounds != null) {
      if (i2 == 0 && i3 == 0) {
        return;
      }
      sizeCompatBounds.offset(i2, i3);
    }
  }

  public void adjustBoundsWithImeIfNeeded(WindowState windowState, Rect rect) {
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord == null) {
      return;
    }
    windowState.setCompatWindowMovedByIme(adjustBoundsWithImeIfNeeded(activityRecord, rect));
  }

  public final boolean adjustBoundsWithImeIfNeeded(ActivityRecord activityRecord, Rect rect) {
    if (activityRecord.mDisplayContent != null
        && activityRecord.mCompatRecord.isCompatModeEnabled()
        && ((!CoreRune.FW_BOUNDS_COMPAT_DISPLAY_CENTER_ALIGNMENT
                || !activityRecord.mCompatRecord.mRestrictedBounds)
            && (!CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL
                || !BoundsCompatAlignmentController.getGlobalBoundsCompatAlignmentLocked()
                    .isTopVertical()))) {
      DisplayContent displayContent = activityRecord.mDisplayContent;
      InputTarget imeInputTarget = displayContent.getImeInputTarget();
      WindowState windowState = imeInputTarget != null ? imeInputTarget.getWindowState() : null;
      if (windowState != null && windowState.mActivityRecord == activityRecord) {
        InsetsState rawInsetsState = displayContent.getInsetsStateController().getRawInsetsState();
        Insets calculateInsets =
            rawInsetsState.calculateInsets(
                rawInsetsState.getDisplayFrame(),
                WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(),
                false);
        if (calculateInsets.top >= rect.top) {
          return false;
        }
        InsetsSource peekSource = rawInsetsState.peekSource(InsetsSource.ID_IME);
        if (peekSource != null && peekSource.isVisible()) {
          Rect visibleFrame =
              peekSource.getVisibleFrame() != null
                  ? peekSource.getVisibleFrame()
                  : peekSource.getFrame();
          if (Rect.intersects(rect, visibleFrame)
              && displayContent.getInputMethodWindowVisibleHeight() > 0) {
            int i = calculateInsets.top;
            int height = rect.height();
            int i2 = visibleFrame.top;
            if (i2 - i > height) {
              i = i2 - height;
            }
            r0 = rect.top != i;
            if (r0) {
              rect.offsetTo(rect.left, i);
            }
          }
        }
      }
    }
    return r0;
  }

  public boolean restrictToBoundsForMinAspectRatioIfNeeded(
      ActivityRecord activityRecord, Rect rect) {
    Configuration fixedRotationTransformRotatedConfiguration =
        activityRecord.getFixedRotationTransformRotatedConfiguration();
    boolean z = fixedRotationTransformRotatedConfiguration != null;
    if (!shouldApplyMinAspectRatio(activityRecord.mDisplayContent)
        || (!(activityRecord.mDisplayContent.getConfiguration().orientation == 2 || z)
            || rect.width() <= rect.height())) {
      return false;
    }
    if (!z) {
      fixedRotationTransformRotatedConfiguration =
          activityRecord.mDisplayContent.getConfiguration();
    }
    Rect bounds = fixedRotationTransformRotatedConfiguration.windowConfiguration.getBounds();
    Rect appBounds = fixedRotationTransformRotatedConfiguration.windowConfiguration.getAppBounds();
    if (bounds.equals(appBounds)) {
      return false;
    }
    int max = Math.max(appBounds.top - bounds.top, bounds.bottom - appBounds.bottom);
    int height = bounds.height() - (max * 2);
    if (rect.height() <= height) {
      return false;
    }
    rect.top = max;
    rect.bottom = max + height;
    return true;
  }

  public final Rect getStableBounds(DisplayContent displayContent) {
    DisplayInfo displayInfo = displayContent.getDisplayInfo();
    this.mTmpStableBounds.set(
        displayContent
            .getDisplayPolicy()
            .getDecorInsetsInfo(
                displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight)
            .mConfigFrame);
    return this.mTmpStableBounds;
  }
}
