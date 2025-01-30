package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class DexCompatBoundsProvider {
  public boolean mIsPortrait;
  public final Rect mStableBounds = new Rect();
  public Task mTask;

  public static boolean isDefaultSizeCompatible(int i) {
    return i <= 0 || i > 1400;
  }

  public boolean setInitialState(Task task, int i) {
    if (task.getDisplayContent() == null) {
      return false;
    }
    this.mTask = task;
    this.mIsPortrait = isPortrait(i);
    this.mTask.getDisplayContent().getStableRect(this.mStableBounds);
    return true;
  }

  public boolean isPortrait(int i) {
    int convertToConfigurationOrientation =
        DexCompatController.convertToConfigurationOrientation(i);
    if (convertToConfigurationOrientation == 0) {
      Task task = this.mTask;
      convertToConfigurationOrientation =
          task.mAtmService.mDexCompatController.getOrientationFromTaskBounds(task);
    }
    return convertToConfigurationOrientation == 1;
  }

  public static int getDefaultOrientation() {
    return CoreRune.IS_TABLET_DEVICE ? 2 : 1;
  }

  public void getBounds(Rect rect) {
    int defaultWidth = getDefaultWidth(getDexMode(), getDisplayShortSize());
    int defaultHeight = getDefaultHeight(getDexMode(), getDisplayShortSize());
    if (defaultWidth <= 0 || defaultHeight <= 0) {
      Slog.w(
          "DexCompatBoundsProvider",
          "getBounds: Invalid default size, " + defaultWidth + "x" + defaultHeight);
      return;
    }
    if (this.mIsPortrait) {
      rect.set(0, 0, Math.min(defaultWidth, defaultHeight), Math.max(defaultWidth, defaultHeight));
    } else {
      rect.set(0, 0, Math.max(defaultWidth, defaultHeight), Math.min(defaultWidth, defaultHeight));
    }
    if (CoreRune.MD_DEX_COMPAT_CAPTION_WINDOW) {
      rect.bottom += getDecorCaptionHeight();
    }
  }

  public static int getDefaultWidth(int i, int i2) {
    if (i == 1) {
      return isDefaultSizeCompatible(i2) ? 1400 : 1010;
    }
    if (i != 2) {
      return -1;
    }
    if (CoreRune.IS_TABLET_DEVICE) {
      return 800;
    }
    return FrameworkStatsLog.CAMERA_COMPAT_CONTROL_EVENT_REPORTED;
  }

  public static int getDefaultHeight(int i, int i2) {
    if (i == 1) {
      return isDefaultSizeCompatible(i2) ? 1050 : 757;
    }
    if (i == 2) {
      return CoreRune.IS_TABLET_DEVICE ? 600 : 731;
    }
    return -1;
  }

  public int getDexMode() {
    return this.mTask.mAtmService.mDexController.getDexModeLocked();
  }

  public int getDisplayShortSize() {
    return Math.min(this.mStableBounds.width(), this.mStableBounds.height());
  }

  public int getDecorCaptionHeight() {
    Task task = this.mTask;
    return task.mAtmService.mDexCompatController.getDecorCaptionHeight(
        task.getDisplayId(), this.mTask.getWindowingMode());
  }

  public final class CustomDexCompatBoundsProvider extends DexCompatBoundsProvider {
    @Override // com.android.server.wm.DexCompatBoundsProvider
    public void getBounds(Rect rect) {
      Point point = this.mTask.mDexCompatCustomSize;
      if (point == null) {
        Slog.w(
            "DexCompatBoundsProvider",
            "getBounds: Cannot find custom size, use default, " + this.mTask);
        super.getBounds(rect);
        return;
      }
      int max = Math.max(point.x, point.y);
      Point point2 = this.mTask.mDexCompatCustomSize;
      int min = Math.min(point2.x, point2.y);
      if (this.mIsPortrait) {
        rect.set(0, 0, min, max);
      } else {
        rect.set(0, 0, max, min);
      }
      if (CoreRune.MD_DEX_COMPAT_CAPTION_WINDOW) {
        rect.bottom += getDecorCaptionHeight();
      }
    }
  }

  public class FullscreenDexCompatBoundsProvider extends DexCompatBoundsProvider {
    @Override // com.android.server.wm.DexCompatBoundsProvider
    public void getBounds(Rect rect) {
      if (!this.mIsPortrait) {
        rect.setEmpty();
        return;
      }
      int defaultWidth =
          DexCompatBoundsProvider.getDefaultWidth(getDexMode(), getDisplayShortSize());
      int defaultHeight =
          DexCompatBoundsProvider.getDefaultHeight(getDexMode(), getDisplayShortSize());
      if (defaultWidth <= 0 || defaultHeight <= 0) {
        Slog.w(
            "DexCompatBoundsProvider",
            "getBounds: invalid value, " + defaultWidth + "x" + defaultHeight);
        return;
      }
      float f =
          defaultWidth < defaultHeight
              ? defaultWidth / defaultHeight
              : defaultHeight / defaultWidth;
      Rect appBounds = this.mTask.getDisplayContent().getWindowConfiguration().getAppBounds();
      if (appBounds != null) {
        rect.set(
            0,
            0,
            (int) ((appBounds.height() * f) + 0.5f),
            appBounds.height() - getDecorCaptionHeight());
        return;
      }
      int height = (int) ((this.mTask.getDisplayContent().getBounds().height() * 0.9f) + 0.5f);
      rect.set(0, 0, (int) ((height * f) + 0.5f), height);
      if (CoreRune.MD_DEX_COMPAT_CAPTION_WINDOW) {
        rect.bottom += getDecorCaptionHeight();
      }
    }
  }
}
