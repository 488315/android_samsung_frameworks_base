package com.samsung.android.multiwindow;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import com.android.internal.C4337R;
import java.util.List;

/* loaded from: classes5.dex */
public class MultiWindowEdgeDetector {
  public static final int EDGE_LEFT_TOP = 5;
  public static final int EDGE_NONE = 0;
  public static final int EDGE_RIGHT_TOP = 9;
  private static final int MAX_EFFECTIVE_DEGREES = 70;
  private static final int MIN_EFFECTIVE_DEGREES = 20;
  private static final int STRAIGHT_ANGLE = 180;
  public static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_DEFAULT_LEVEL = 2;
  private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_1 = 20;
  private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_2 = 24;
  private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_3 = 33;
  private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_4 = 42;
  private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_5 = 51;
  private static final float WIDTH_SCALE_FOR_LANDSCAPE_CORNER_R = 1.25f;
  private static int sHeight;
  private static int sWidth;
  private final Context mContext;
  private int mCornerRadius;
  private int mGestureThreshold;
  private final String mPrefixLog;
  private int mScreenHeight;
  private int mScreenWidth;
  private int mStartX;
  private int mStartY;
  private static final String TAG = MultiWindowEdgeDetector.class.getSimpleName();
  private static final boolean SAFE_DEBUG = Debug.semIsProductDev();
  private int mMinDegrees = 20;
  private int mMaxDegrees = 70;
  private int mScreenOrientation = 0;
  private int mEdgeFlags = 0;
  private boolean mIsGestureDetected = false;

  public MultiWindowEdgeDetector(Context context, String caller) {
    this.mContext = context;
    this.mPrefixLog = NavigationBarInflaterView.SIZE_MOD_START + caller + "] ";
    loadResources();
    updateScreenInfo();
  }

  public void onConfigurationChanged() {
    reset();
    loadResources();
    updateScreenInfo();
  }

  public void reset() {
    this.mEdgeFlags = 0;
  }

  private void loadResources() {
    updateCustomBoundsIfNeeded();
    this.mCornerRadius =
        this.mContext
            .getResources()
            .getDimensionPixelSize(C4337R.dimen.multiwindow_freeform_gesture_guide_corner_radius);
    this.mGestureThreshold =
        this.mContext
            .getResources()
            .getDimensionPixelSize(C4337R.dimen.multiwindow_freeform_gesture_threshold);
    if (SAFE_DEBUG) {
      updateFromSystemProperties();
    }
  }

  public static int getCornerGestureCustomValue(int level) {
    int val;
    float physicalDensity = DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f;
    switch (level) {
      case 1:
        val = 20;
        break;
      case 2:
        val = 24;
        break;
      case 3:
        val = 33;
        break;
      case 4:
        val = 42;
        break;
      case 5:
        val = 51;
        break;
      default:
        val = 24;
        break;
    }
    return Utils.dipToPixel(val, physicalDensity);
  }

  public static void updateCustomBoundsIfNeeded() {
    if (MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE > 0
        && sWidth != MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE) {
      int i = MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE;
      sHeight = i;
      sWidth = i;
    }
  }

  private void updateScreenInfo() {
    Display display = this.mContext.getDisplay();
    if (display == null) {
      Slog.m121w(TAG, this.mPrefixLog + "display is null, mContext=" + this.mContext);
      return;
    }
    DisplayInfo displayInfo = new DisplayInfo();
    display.getDisplayInfo(displayInfo);
    boolean changed =
        (this.mScreenWidth == displayInfo.logicalWidth
                && this.mScreenHeight == displayInfo.logicalHeight)
            ? false
            : true;
    boolean needUpdate = changed || this.mScreenOrientation == 0;
    if (needUpdate) {
      this.mScreenWidth = displayInfo.logicalWidth;
      int i = displayInfo.logicalHeight;
      this.mScreenHeight = i;
      int i2 = this.mScreenWidth > i ? 2 : 1;
      this.mScreenOrientation = i2;
      if (i2 == 2) {
        sWidth = (int) ((sWidth * WIDTH_SCALE_FOR_LANDSCAPE_CORNER_R) + 0.5f);
      }
      if (SAFE_DEBUG) {
        Slog.m117i(
            TAG,
            this.mPrefixLog
                + "updateScreenInfo: mScreenWidth="
                + this.mScreenWidth
                + ", mScreenHeight="
                + this.mScreenHeight
                + ", mScreenOrientation="
                + this.mScreenOrientation
                + ", sWidth="
                + sWidth);
      }
    }
  }

  private void ensureScreenInfo() {
    int currentWidth = this.mContext.getResources().getDisplayMetrics().widthPixels;
    int currentHeight = this.mContext.getResources().getDisplayMetrics().heightPixels;
    int currentOrientation = currentWidth > currentHeight ? 2 : 1;
    if (this.mScreenOrientation != currentOrientation) {
      Slog.m121w(
          TAG,
          this.mPrefixLog
              + "ensureScreenInfo: ScreenInfo is wrong, mScreenOr="
              + this.mScreenOrientation
              + ", currentOr="
              + currentOrientation);
      updateScreenInfo();
    }
  }

  public boolean onTouchEvent(MotionEvent ev) {
    if (!MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED) {
      return false;
    }
    boolean onTouchEvent = isEdge();
    switch (ev.getActionMasked()) {
      case 0:
        ensureScreenInfo();
        this.mEdgeFlags = getEdgeFlags(ev);
        if (isNotSupportEdge(ev) || isTopTaskHomeOrRecents()) {
          reset();
        }
        return isEdge();
      case 1:
      case 3:
        reset();
        return onTouchEvent;
      case 2:
      default:
        return onTouchEvent;
    }
  }

  private boolean isTopTaskHomeOrRecents() {
    ActivityManager.RunningTaskInfo topTaskInfo = getTopFullscreenTaskInfo();
    if (topTaskInfo == null) {
      return false;
    }
    if (topTaskInfo.getActivityType() != 2 && topTaskInfo.getActivityType() != 3) {
      return false;
    }
    Log.m98i(TAG, this.mPrefixLog + "isTopTaskHomeOrRecents");
    return true;
  }

  private ActivityManager.RunningTaskInfo getTopFullscreenTaskInfo() {
    List<ActivityManager.RunningTaskInfo> taskInfos =
        MultiWindowManager.getInstance().getVisibleTasks();
    if (taskInfos == null || taskInfos.isEmpty()) {
      return null;
    }
    for (ActivityManager.RunningTaskInfo taskInfo : taskInfos) {
      if (taskInfo.getWindowingMode() == 1) {
        return taskInfo;
      }
    }
    return null;
  }

  public boolean isEdge() {
    int i = this.mEdgeFlags;
    return i == 5 || i == 9;
  }

  private boolean isNotSupportEdge(MotionEvent ev) {
    return (ev == null || (ev.getButtonState() & 2) == 0) ? false : true;
  }

  public int getEdgeFlags() {
    return this.mEdgeFlags;
  }

  private int getEdgeFlags(MotionEvent ev) {
    float x = ev.getRawX();
    float y = ev.getRawY();
    if (y > sHeight) {
      return 0;
    }
    int flags = 1;
    if (x < sWidth) {
      flags = 1 | 4;
    } else if (x > this.mScreenWidth - r3) {
      flags = 1 | 8;
    }
    Log.m98i(
        TAG,
        this.mPrefixLog
            + "checkEdgeFlags: "
            + Utils.edgeFlagToString(flags)
            + ", ["
            + x
            + ","
            + y
            + "], w="
            + sWidth
            + ", h="
            + sHeight
            + ", screenWidth="
            + this.mScreenWidth);
    return flags;
  }

  public boolean readyToFreeform(int x, int y) {
    int i = this.mCornerRadius;
    int radius = i * i;
    int distance = 0;
    switch (this.mEdgeFlags) {
      case 5:
        distance = (x * x) + (y * y);
        break;
      case 9:
        int i2 = this.mScreenWidth;
        distance = ((i2 - x) * (i2 - x)) + (y * y);
        break;
    }
    return radius < distance;
  }

  public boolean isValidGesture(int dx, int dy) {
    return isExceedThreshold(dx, dy) && isEffectiveAngle(dx, dy);
  }

  private boolean isExceedThreshold(int dx, int dy) {
    int distance = Math.abs(dx) + Math.abs(dy);
    boolean result = distance >= this.mGestureThreshold;
    Log.m98i(
        TAG,
        this.mPrefixLog
            + "isExceedThreshold: "
            + result
            + ", dx="
            + Math.abs(dx)
            + ", dy="
            + Math.abs(dy)
            + ", distance="
            + distance
            + ", threshold="
            + this.mGestureThreshold);
    return result;
  }

  private boolean isEffectiveAngle(int dx, int dy) {
    int degrees;
    switch (this.mEdgeFlags) {
      case 5:
        degrees = (int) Math.toDegrees(Math.atan2(dy, dx));
        break;
      case 9:
        degrees = 180 - ((int) Math.toDegrees(Math.atan2(dy, dx)));
        break;
      default:
        degrees = 0;
        break;
    }
    boolean result = degrees >= this.mMinDegrees && degrees <= this.mMaxDegrees;
    Log.m98i(TAG, this.mPrefixLog + "isEffectiveAngle: " + result + ", degrees=" + degrees);
    return result;
  }

  private void updateFromSystemProperties() {
    if (SAFE_DEBUG) {
      int sysWidth = SystemProperties.getInt("persist.dev.freeform.gesture.w", -1);
      int sysHeight = SystemProperties.getInt("persist.dev.freeform.gesture.h", -1);
      int sysCornerRadius = SystemProperties.getInt("persist.dev.freeform.gesture.r", -1);
      int sysDegrees = SystemProperties.getInt("persist.dev.freeform.gesture.dr", -1);
      boolean changed = false;
      if (sysWidth >= 0 && sWidth != sysWidth) {
        sWidth = sysWidth;
        changed = true;
      }
      if (sysHeight >= 0 && sHeight != sysHeight) {
        sHeight = sysHeight;
        changed = true;
      }
      if (sysCornerRadius >= 0 && this.mCornerRadius != sysCornerRadius) {
        this.mCornerRadius = sysCornerRadius;
        changed = true;
      }
      if (sysDegrees >= 0 && this.mMaxDegrees != sysDegrees) {
        this.mMaxDegrees = sysDegrees;
        changed = true;
      }
      if (changed) {
        Log.m98i(
            TAG,
            this.mPrefixLog
                + "updateFromSystemProperties: sWidth="
                + sWidth
                + ", sHeight="
                + sHeight
                + ", mCornerRadius="
                + this.mCornerRadius
                + ", mMaxDegrees="
                + this.mMaxDegrees);
      }
    }
  }

  public static class Utils {
    public static int dipToPixel(int dip, float density) {
      return (int) (dip * density);
    }

    public static String edgeFlagToString(int flag) {
      switch (flag) {
        case 5:
          return "EDGE_LEFT_TOP";
        case 9:
          return "EDGE_RIGHT_TOP";
        default:
          return "INVALID";
      }
    }

    public static void applyResizeRect(Rect outBounds, int edgeFlags, int x, int y) {
      if (outBounds == null) {}
      switch (edgeFlags) {
        case 5:
          outBounds.left = x;
          outBounds.top = y;
          break;
        case 9:
          outBounds.right = x;
          outBounds.top = y;
          break;
      }
    }

    public static boolean adjustMinimalTaskBounds(
        Rect outBounds, int edgeFlags, int minWidth, int minHeight) {
      if (outBounds == null || minWidth < 1 || minHeight < 1) {
        return false;
      }
      boolean adjustWidth = outBounds.width() < minWidth;
      boolean adjustHeight = outBounds.height() < minHeight;
      switch (edgeFlags) {
        case 5:
          if (adjustWidth) {
            outBounds.left = outBounds.right - minWidth;
          }
          if (adjustHeight) {
            outBounds.top = outBounds.bottom - minHeight;
            break;
          }
          break;
        case 9:
          if (adjustWidth) {
            outBounds.right = outBounds.left + minWidth;
          }
          if (adjustHeight) {
            outBounds.top = outBounds.bottom - minHeight;
            break;
          }
          break;
      }
      return adjustWidth && adjustHeight;
    }
  }

  public boolean interceptTouchForCornerGesture(MotionEvent event) {
    if (!MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED) {
      return false;
    }
    updateCustomBoundsIfNeeded();
    boolean intercepted = false;
    if (!onTouchEvent(event)) {
      return false;
    }
    int x = (int) event.getX();
    int y = (int) event.getY();
    switch (event.getAction()) {
      case 0:
        this.mStartX = x;
        this.mStartY = y;
        return false;
      case 1:
        intercepted = this.mIsGestureDetected;
        break;
      case 2:
        if (this.mIsGestureDetected || !readyToFreeform(x, y)) {
          return true;
        }
        if (isValidGesture(x - this.mStartX, y - this.mStartY)) {
          Slog.m113d(TAG, this.mPrefixLog + "Gesture valid");
          this.mIsGestureDetected = true;
          return true;
        }
        Slog.m113d(TAG, this.mPrefixLog + "Gesture invalid, reset");
        reset();
        return false;
      case 3:
        break;
      case 4:
      default:
        return false;
      case 5:
      case 6:
        boolean intercepted2 = this.mIsGestureDetected;
        return intercepted2;
    }
    reset();
    this.mIsGestureDetected = false;
    return intercepted;
  }

  public boolean isGestureDetected() {
    return this.mIsGestureDetected;
  }
}
