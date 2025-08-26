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
import com.android.internal.R;
import java.util.List;

/* loaded from: classes6.dex */
public class MultiWindowEdgeDetector {
    public static final int EDGE_LEFT_TOP = 5;
    public static final int EDGE_NONE = 0;
    public static final int EDGE_RIGHT_TOP = 9;
    private static final int MAX_EFFECTIVE_DEGREES = 70;
    private static final int MIN_EFFECTIVE_DEGREES = 20;
    private static final boolean SAFE_DEBUG = Debug.semIsProductDev();
    private static final int STRAIGHT_ANGLE = 180;
    public static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_DEFAULT_LEVEL = 2;
    private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_1 = 20;
    private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_2 = 24;
    private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_3 = 33;
    private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_4 = 42;
    private static final int SWIPE_FOR_POPUP_VIEW_CORNER_AREA_LEVEL_5 = 51;
    private static final String TAG = "MultiWindowEdgeDetector";
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
    private int mMinDegrees = 20;
    private int mMaxDegrees = 70;
    private int mScreenOrientation = 0;
    private int mEdgeFlags = 0;
    private boolean mIsGestureDetected = false;

    public MultiWindowEdgeDetector(Context context, String str) {
        this.mContext = context;
        this.mPrefixLog = NavigationBarInflaterView.SIZE_MOD_START + str + "] ";
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
        this.mCornerRadius = this.mContext.getResources().getDimensionPixelSize(R.dimen.multiwindow_freeform_gesture_guide_corner_radius);
        this.mGestureThreshold = this.mContext.getResources().getDimensionPixelSize(R.dimen.multiwindow_freeform_gesture_threshold);
        if (SAFE_DEBUG) {
            updateFromSystemProperties();
        }
    }

    public static int getCornerGestureCustomValue(int i) {
        int i2;
        float f = DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f;
        if (i != 1) {
            i2 = 24;
            if (i != 2) {
                if (i == 3) {
                    i2 = 33;
                } else if (i == 4) {
                    i2 = 42;
                } else if (i == 5) {
                    i2 = 51;
                }
            }
        } else {
            i2 = 20;
        }
        return Utils.dipToPixel(i2, f);
    }

    public static void updateCustomBoundsIfNeeded() {
        if (MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE <= 0 || sWidth == MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE) {
            return;
        }
        int i = MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_CUSTOM_VALUE;
        sHeight = i;
        sWidth = i;
    }

    private void updateScreenInfo() {
        Display display = this.mContext.getDisplay();
        if (display == null) {
            Slog.w(TAG, this.mPrefixLog + "display is null, mContext=" + this.mContext);
            return;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (this.mScreenWidth == displayInfo.logicalWidth && this.mScreenHeight == displayInfo.logicalHeight && this.mScreenOrientation != 0) {
            return;
        }
        this.mScreenWidth = displayInfo.logicalWidth;
        int i = displayInfo.logicalHeight;
        this.mScreenHeight = i;
        int i2 = this.mScreenWidth > i ? 2 : 1;
        this.mScreenOrientation = i2;
        if (i2 == 2) {
            sWidth = (int) ((sWidth * WIDTH_SCALE_FOR_LANDSCAPE_CORNER_R) + 0.5f);
        }
        if (SAFE_DEBUG) {
            Slog.i(TAG, this.mPrefixLog + "updateScreenInfo: mScreenWidth=" + this.mScreenWidth + ", mScreenHeight=" + this.mScreenHeight + ", mScreenOrientation=" + this.mScreenOrientation + ", sWidth=" + sWidth);
        }
    }

    private void ensureScreenInfo() {
        int i = this.mContext.getResources().getDisplayMetrics().widthPixels > this.mContext.getResources().getDisplayMetrics().heightPixels ? 2 : 1;
        if (this.mScreenOrientation != i) {
            Slog.w(TAG, this.mPrefixLog + "ensureScreenInfo: ScreenInfo is wrong, mScreenOr=" + this.mScreenOrientation + ", currentOr=" + i);
            updateScreenInfo();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED) {
            return false;
        }
        boolean zIsEdge = isEdge();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1 && actionMasked != 3) {
                return zIsEdge;
            }
            reset();
            return zIsEdge;
        }
        ensureScreenInfo();
        this.mEdgeFlags = getEdgeFlags(motionEvent);
        if (isNotSupportEdge(motionEvent) || isTopTaskHomeOrRecents()) {
            reset();
        }
        return isEdge();
    }

    private boolean isTopTaskHomeOrRecents() {
        ActivityManager.RunningTaskInfo topFullscreenTaskInfo = getTopFullscreenTaskInfo();
        if (topFullscreenTaskInfo == null) {
            return false;
        }
        if (topFullscreenTaskInfo.getActivityType() != 2 && topFullscreenTaskInfo.getActivityType() != 3) {
            return false;
        }
        Log.i(TAG, this.mPrefixLog + "isTopTaskHomeOrRecents");
        return true;
    }

    private ActivityManager.RunningTaskInfo getTopFullscreenTaskInfo() {
        List<ActivityManager.RunningTaskInfo> visibleTasks = MultiWindowManager.getInstance().getVisibleTasks(10);
        if (visibleTasks != null && !visibleTasks.isEmpty()) {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : visibleTasks) {
                if (runningTaskInfo.getWindowingMode() == 1) {
                    return runningTaskInfo;
                }
            }
        }
        return null;
    }

    public boolean isEdge() {
        int i = this.mEdgeFlags;
        return i == 5 || i == 9;
    }

    private boolean isNotSupportEdge(MotionEvent motionEvent) {
        return (motionEvent == null || (motionEvent.getButtonState() & 2) == 0) ? false : true;
    }

    public int getEdgeFlags() {
        return this.mEdgeFlags;
    }

    private int getEdgeFlags(MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        if (rawY > sHeight) {
            return 0;
        }
        int i = sWidth;
        int i2 = rawX < ((float) i) ? 5 : rawX > ((float) (this.mScreenWidth - i)) ? 9 : 1;
        Log.i(TAG, this.mPrefixLog + "checkEdgeFlags: " + Utils.edgeFlagToString(i2) + ", [" + rawX + "," + rawY + "], w=" + sWidth + ", h=" + sHeight + ", screenWidth=" + this.mScreenWidth);
        return i2;
    }

    public boolean readyToFreeform(int i, int i2) {
        int i3;
        int i4 = this.mCornerRadius;
        int i5 = i4 * i4;
        int i6 = this.mEdgeFlags;
        if (i6 == 5) {
            i3 = (i * i) + (i2 * i2);
        } else if (i6 != 9) {
            i3 = 0;
        } else {
            int i7 = this.mScreenWidth;
            i3 = ((i7 - i) * (i7 - i)) + (i2 * i2);
        }
        return i5 < i3;
    }

    public boolean isValidGesture(int i, int i2) {
        return isExceedThreshold(i, i2) && isEffectiveAngle(i, i2);
    }

    private boolean isExceedThreshold(int i, int i2) {
        int iAbs = Math.abs(i) + Math.abs(i2);
        boolean z = iAbs >= this.mGestureThreshold;
        Log.i(TAG, this.mPrefixLog + "isExceedThreshold: " + z + ", dx=" + Math.abs(i) + ", dy=" + Math.abs(i2) + ", distance=" + iAbs + ", threshold=" + this.mGestureThreshold);
        return z;
    }

    private boolean isEffectiveAngle(int i, int i2) {
        int degrees;
        int i3 = this.mEdgeFlags;
        boolean z = false;
        if (i3 == 5) {
            degrees = (int) Math.toDegrees(Math.atan2(i2, i));
        } else {
            degrees = i3 != 9 ? 0 : 180 - ((int) Math.toDegrees(Math.atan2(i2, i)));
        }
        if (degrees >= this.mMinDegrees && degrees <= this.mMaxDegrees) {
            z = true;
        }
        Log.i(TAG, this.mPrefixLog + "isEffectiveAngle: " + z + ", degrees=" + degrees);
        return z;
    }

    private void updateFromSystemProperties() {
        boolean z;
        if (SAFE_DEBUG) {
            int i = SystemProperties.getInt("persist.dev.freeform.gesture.w", -1);
            int i2 = SystemProperties.getInt("persist.dev.freeform.gesture.h", -1);
            int i3 = SystemProperties.getInt("persist.dev.freeform.gesture.r", -1);
            int i4 = SystemProperties.getInt("persist.dev.freeform.gesture.dr", -1);
            boolean z2 = true;
            if (i < 0 || sWidth == i) {
                z = false;
            } else {
                sWidth = i;
                z = true;
            }
            if (i2 >= 0 && sHeight != i2) {
                sHeight = i2;
                z = true;
            }
            if (i3 >= 0 && this.mCornerRadius != i3) {
                this.mCornerRadius = i3;
                z = true;
            }
            if (i4 < 0 || this.mMaxDegrees == i4) {
                z2 = z;
            } else {
                this.mMaxDegrees = i4;
            }
            if (z2) {
                Log.i(TAG, this.mPrefixLog + "updateFromSystemProperties: sWidth=" + sWidth + ", sHeight=" + sHeight + ", mCornerRadius=" + this.mCornerRadius + ", mMaxDegrees=" + this.mMaxDegrees);
            }
        }
    }

    public static class Utils {
        public static int dipToPixel(int i, float f) {
            return (int) (i * f);
        }

        public static String edgeFlagToString(int i) {
            if (i == 5) {
                return "EDGE_LEFT_TOP";
            }
            if (i == 9) {
                return "EDGE_RIGHT_TOP";
            }
            return "INVALID";
        }

        public static void applyResizeRect(Rect rect, int i, int i2, int i3) {
            if (rect == null) {
                return;
            }
            if (i == 5) {
                rect.left = i2;
                rect.top = i3;
            } else {
                if (i != 9) {
                    return;
                }
                rect.right = i2;
                rect.top = i3;
            }
        }

        public static boolean adjustMinimalTaskBounds(Rect rect, int i, int i2, int i3) {
            if (rect != null && i2 >= 1 && i3 >= 1) {
                boolean z = rect.width() < i2;
                boolean z2 = rect.height() < i3;
                if (i == 5) {
                    if (z) {
                        rect.left = rect.right - i2;
                    }
                    if (z2) {
                        rect.top = rect.bottom - i3;
                    }
                } else if (i == 9) {
                    if (z) {
                        rect.right = rect.left + i2;
                    }
                    if (z2) {
                        rect.top = rect.bottom - i3;
                    }
                }
                if (z && z2) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean interceptTouchForCornerGesture(MotionEvent motionEvent) {
        boolean z;
        if (!MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED) {
            return false;
        }
        updateCustomBoundsIfNeeded();
        if (onTouchEvent(motionEvent)) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mStartX = x;
                this.mStartY = y;
            } else {
                if (action == 1) {
                    z = this.mIsGestureDetected;
                } else {
                    if (action == 2) {
                        if (this.mIsGestureDetected || !readyToFreeform(x, y)) {
                            return true;
                        }
                        if (isValidGesture(x - this.mStartX, y - this.mStartY)) {
                            Slog.d(TAG, this.mPrefixLog + "Gesture valid");
                            this.mIsGestureDetected = true;
                            return true;
                        }
                        Slog.d(TAG, this.mPrefixLog + "Gesture invalid, reset");
                        reset();
                        return false;
                    }
                    if (action == 3) {
                        z = false;
                    } else if (action == 5 || action == 6) {
                        return this.mIsGestureDetected;
                    }
                }
                reset();
                this.mIsGestureDetected = false;
                return z;
            }
        }
        return false;
    }

    public boolean isGestureDetected() {
        return this.mIsGestureDetected;
    }
}
