package android.view;

import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.input.InputManagerGlobal;
import android.os.RemoteException;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.flags.Flags;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class ViewConfiguration {
    private static final int A11Y_SHORTCUT_KEY_TIMEOUT = 3000;
    private static final int A11Y_SHORTCUT_KEY_TIMEOUT_AFTER_CONFIRMATION = 1000;
    private static final long ACTION_MODE_HIDE_DURATION_DEFAULT = 2000;
    private static final float AMBIGUOUS_GESTURE_MULTIPLIER = 2.0f;
    private static final int BIT_COUNT_DENSITY = 10;
    private static final int BIT_COUNT_WIDTH_HEIGHT = 11;
    private static final int DEFAULT_KEY_REPEAT_DELAY_MS = 50;
    private static final int DEFAULT_KEY_REPEAT_TIMEOUT_MS = 400;
    public static final int DEFAULT_LONG_PRESS_TIMEOUT = 400;
    private static final int DEFAULT_MULTI_PRESS_TIMEOUT = 300;
    private static final int DOUBLE_TAP_MIN_TIME = 40;
    private static final int DOUBLE_TAP_SLOP = 100;
    private static final int DOUBLE_TAP_TIMEOUT = 300;
    private static final int DOUBLE_TAP_TOUCH_SLOP = 8;
    private static final int EDGE_SLOP = 12;
    private static final int FADING_EDGE_LENGTH = 12;
    private static final int GLOBAL_ACTIONS_KEY_TIMEOUT = 500;
    private static final int HANDWRITING_GESTURE_LINE_MARGIN = 16;
    private static final int HANDWRITING_SLOP = 2;
    private static final int HAS_PERMANENT_MENU_KEY_AUTODETECT = 0;
    private static final int HAS_PERMANENT_MENU_KEY_FALSE = 2;
    private static final int HAS_PERMANENT_MENU_KEY_TRUE = 1;
    private static final float HORIZONTAL_SCROLL_FACTOR = 64.0f;
    private static final int HOVER_TAP_SLOP = 20;
    private static final int HOVER_TAP_TIMEOUT = 150;
    private static final int HOVER_TOOLTIP_HIDE_SHORT_TIMEOUT = 3000;
    private static final int HOVER_TOOLTIP_HIDE_TIMEOUT = 15000;
    private static final int HOVER_TOOLTIP_SHOW_TIMEOUT = 500;
    private static final int JUMP_TAP_TIMEOUT = 500;
    private static final int LONG_PRESS_TOOLTIP_HIDE_TIMEOUT = 1500;

    @Deprecated
    private static final int MAXIMUM_DRAWING_CACHE_SIZE = 1536000;
    private static final int MAXIMUM_FLING_VELOCITY = 8000;
    private static final int MINIMUM_FLING_VELOCITY = 50;
    private static final int MIN_SCROLLBAR_TOUCH_TARGET = 48;
    private static final int NO_FLING_MAX_VELOCITY = Integer.MIN_VALUE;
    private static final int NO_FLING_MIN_VELOCITY = Integer.MAX_VALUE;
    public static final int NO_HAPTIC_SCROLL_TICK_INTERVAL = Integer.MAX_VALUE;
    private static final int OVERFLING_DISTANCE = 6;
    private static final int OVERSCROLL_DISTANCE = 0;
    private static final int PAGING_TOUCH_SLOP = 16;
    private static final int PRESSED_STATE_DURATION = 64;
    private static final int SCREENSHOT_CHORD_KEY_TIMEOUT = 0;
    private static final int SCROLL_BAR_DEFAULT_DELAY = 300;
    private static final int SCROLL_BAR_FADE_DURATION = 250;
    private static final int SCROLL_BAR_SIZE = 4;
    private static final float SCROLL_FRICTION = 0.015f;
    private static final long SEND_RECURRING_ACCESSIBILITY_EVENTS_INTERVAL_MILLIS = 100;
    private static final int SMART_SELECTION_INITIALIZED_TIMEOUT_IN_MILLISECOND = 200;
    private static final int SMART_SELECTION_INITIALIZING_TIMEOUT_IN_MILLISECOND = 500;
    private static final int TAP_TIMEOUT = 100;
    private static final int TOUCH_SLOP = 8;
    private static final float VERTICAL_SCROLL_FACTOR = 64.0f;
    private static final int WINDOW_TOUCH_SLOP = 16;
    private static final int ZOOM_CONTROLS_TIMEOUT = 3000;
    private final float mAmbiguousGestureMultiplier;
    private final boolean mConstructedWithContext;
    private final int mDoubleTapSlop;
    private final int mDoubleTapTouchSlop;
    private final int mEdgeSlop;
    private final int mFadingEdgeLength;
    private final boolean mFadingMarqueeEnabled;
    private final long mGlobalActionsKeyTimeout;
    private final int mHandwritingGestureLineMargin;
    private final int mHandwritingSlop;
    private final float mHorizontalScrollFactor;
    private final int mHoverSlop;
    private final int mMaximumDrawingCacheSize;
    private final int mMaximumFlingVelocity;
    private final int mMaximumRotaryEncoderFlingVelocity;
    private final int mMinScalingSpan;
    private final int mMinScrollbarTouchTarget;
    private final int mMinimumFlingVelocity;
    private final int mMinimumRotaryEncoderFlingVelocity;
    private final int mOverflingDistance;
    private final int mOverscrollDistance;
    private final int mPagingTouchSlop;
    private final boolean mPreferKeepClearForFocusEnabled;
    private final boolean mRotaryEncoderHapticScrollFeedbackEnabled;
    private final int mRotaryEncoderHapticScrollFeedbackTickIntervalPixels;
    private final long mScreenshotChordKeyTimeout;
    private final int mScrollbarSize;
    private final boolean mShowMenuShortcutsWhenKeyboardPresent;
    private final int mSmartSelectionInitializedTimeout;
    private final int mSmartSelectionInitializingTimeout;
    private final int mTouchSlop;
    private final float mVerticalScrollFactor;
    private final boolean mViewBasedRotaryEncoderScrollHapticsEnabledConfig;
    private final boolean mViewTouchScreenHapticScrollFeedbackEnabled;
    private final int mWindowTouchSlop;
    private boolean sHasPermanentMenuKey;
    private boolean sHasPermanentMenuKeySet;
    private static ResourceCache sResourceCache = new ResourceCache();
    static final SparseArray<ViewConfiguration> sConfigurations = new SparseArray<>(2);

    @Deprecated
    public static float getAmbiguousGestureMultiplier() {
        return 2.0f;
    }

    @Deprecated
    public static int getDoubleTapSlop() {
        return 100;
    }

    @Deprecated
    public static int getEdgeSlop() {
        return 12;
    }

    @Deprecated
    public static int getFadingEdgeLength() {
        return 12;
    }

    @Deprecated
    public static long getGlobalActionKeyTimeout() {
        return 500L;
    }

    public static int getHoverTooltipHideShortTimeout() {
        return 3000;
    }

    public static int getHoverTooltipHideTimeout() {
        return 15000;
    }

    public static int getHoverTooltipShowTimeout() {
        return 500;
    }

    public static int getLongPressTooltipHideTimeout() {
        return 1500;
    }

    @Deprecated
    public static int getMaximumDrawingCacheSize() {
        return MAXIMUM_DRAWING_CACHE_SIZE;
    }

    @Deprecated
    public static int getMaximumFlingVelocity() {
        return 8000;
    }

    @Deprecated
    public static int getMinimumFlingVelocity() {
        return 50;
    }

    public static int getScrollBarFadeDuration() {
        return 250;
    }

    @Deprecated
    public static int getScrollBarSize() {
        return 4;
    }

    public static int getScrollDefaultDelay() {
        return 300;
    }

    public static long getSendRecurringAccessibilityEventsInterval() {
        return SEND_RECURRING_ACCESSIBILITY_EVENTS_INTERVAL_MILLIS;
    }

    @Deprecated
    public static int getTouchSlop() {
        return 8;
    }

    @Deprecated
    public static int getWindowTouchSlop() {
        return 16;
    }

    public long getAccessibilityShortcutKeyTimeout() {
        return 3000L;
    }

    public long getAccessibilityShortcutKeyTimeoutAfterConfirmation() {
        return 1000L;
    }

    @Deprecated
    public ViewConfiguration() {
        this.mConstructedWithContext = false;
        this.mEdgeSlop = 12;
        this.mFadingEdgeLength = 12;
        this.mMinimumFlingVelocity = 50;
        this.mMaximumFlingVelocity = 8000;
        this.mMinimumRotaryEncoderFlingVelocity = 50;
        this.mMaximumRotaryEncoderFlingVelocity = 8000;
        this.mRotaryEncoderHapticScrollFeedbackEnabled = false;
        this.mRotaryEncoderHapticScrollFeedbackTickIntervalPixels = Integer.MAX_VALUE;
        this.mViewBasedRotaryEncoderScrollHapticsEnabledConfig = false;
        this.mScrollbarSize = 4;
        this.mTouchSlop = 8;
        this.mHandwritingSlop = 2;
        this.mHoverSlop = 4;
        this.mMinScrollbarTouchTarget = 48;
        this.mDoubleTapTouchSlop = 8;
        this.mPagingTouchSlop = 16;
        this.mDoubleTapSlop = 100;
        this.mWindowTouchSlop = 16;
        this.mHandwritingGestureLineMargin = 16;
        this.mAmbiguousGestureMultiplier = 2.0f;
        this.mMaximumDrawingCacheSize = MAXIMUM_DRAWING_CACHE_SIZE;
        this.mOverscrollDistance = 0;
        this.mOverflingDistance = 6;
        this.mFadingMarqueeEnabled = true;
        this.mGlobalActionsKeyTimeout = 500L;
        this.mHorizontalScrollFactor = 64.0f;
        this.mVerticalScrollFactor = 64.0f;
        this.mShowMenuShortcutsWhenKeyboardPresent = false;
        this.mScreenshotChordKeyTimeout = 0L;
        this.mMinScalingSpan = 0;
        this.mSmartSelectionInitializedTimeout = 200;
        this.mSmartSelectionInitializingTimeout = 500;
        this.mPreferKeepClearForFocusEnabled = false;
        this.mViewTouchScreenHapticScrollFeedbackEnabled = false;
    }

    private ViewConfiguration(Context context) throws Resources.NotFoundException {
        this.mConstructedWithContext = true;
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        float f = displayMetrics.density;
        f = configuration.isLayoutSizeAtLeast(4) ? f * 1.5f : f;
        int i = (int) ((12.0f * f) + 0.5f);
        this.mEdgeSlop = i;
        this.mFadingEdgeLength = i;
        this.mScrollbarSize = resources.getDimensionPixelSize(R.dimen.config_scrollbarSize);
        this.mDoubleTapSlop = (int) ((100.0f * f) + 0.5f);
        this.mWindowTouchSlop = (int) ((16.0f * f) + 0.5f);
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.config_ambiguousGestureMultiplier, typedValue, true);
        this.mAmbiguousGestureMultiplier = Math.max(1.0f, typedValue.getFloat());
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        this.mMaximumDrawingCacheSize = maxBounds.width() * 4 * maxBounds.height();
        this.mOverscrollDistance = (int) ((0.0f * f) + 0.5f);
        this.mOverflingDistance = (int) ((f * 6.0f) + 0.5f);
        if (!this.sHasPermanentMenuKeySet) {
            int integer = resources.getInteger(R.integer.config_overrideHasPermanentMenuKey);
            if (integer == 1) {
                this.sHasPermanentMenuKey = true;
                this.sHasPermanentMenuKeySet = true;
            } else if (integer != 2) {
                try {
                    this.sHasPermanentMenuKey = !WindowManagerGlobal.getWindowManagerService().hasNavigationBar(context.getDisplayId());
                    this.sHasPermanentMenuKeySet = true;
                } catch (RemoteException unused) {
                    this.sHasPermanentMenuKey = false;
                }
            } else {
                this.sHasPermanentMenuKey = false;
                this.sHasPermanentMenuKeySet = true;
            }
        }
        this.mFadingMarqueeEnabled = resources.getBoolean(R.bool.config_ui_enableFadingMarquee);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.config_viewConfigurationTouchSlop);
        this.mTouchSlop = dimensionPixelSize;
        this.mHandwritingSlop = resources.getDimensionPixelSize(R.dimen.config_viewConfigurationHandwritingSlop);
        this.mHoverSlop = resources.getDimensionPixelSize(R.dimen.config_viewConfigurationHoverSlop);
        this.mMinScrollbarTouchTarget = resources.getDimensionPixelSize(R.dimen.config_minScrollbarTouchTarget);
        this.mPagingTouchSlop = dimensionPixelSize * 2;
        this.mDoubleTapTouchSlop = dimensionPixelSize;
        this.mHandwritingGestureLineMargin = resources.getDimensionPixelSize(17104906);
        this.mMinimumFlingVelocity = resources.getDimensionPixelSize(R.dimen.config_viewMinFlingVelocity);
        this.mMaximumFlingVelocity = resources.getDimensionPixelSize(R.dimen.config_viewMaxFlingVelocity);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.config_viewMinRotaryEncoderFlingVelocity);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.config_viewMaxRotaryEncoderFlingVelocity);
        if (dimensionPixelSize2 < 0 || dimensionPixelSize3 < 0) {
            this.mMinimumRotaryEncoderFlingVelocity = Integer.MAX_VALUE;
            this.mMaximumRotaryEncoderFlingVelocity = Integer.MIN_VALUE;
        } else {
            this.mMinimumRotaryEncoderFlingVelocity = dimensionPixelSize2;
            this.mMaximumRotaryEncoderFlingVelocity = dimensionPixelSize3;
        }
        int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.config_rotaryEncoderAxisScrollTickInterval);
        this.mRotaryEncoderHapticScrollFeedbackTickIntervalPixels = dimensionPixelSize4 > 0 ? dimensionPixelSize4 : Integer.MAX_VALUE;
        this.mRotaryEncoderHapticScrollFeedbackEnabled = resources.getBoolean(R.bool.config_viewRotaryEncoderHapticScrollFedbackEnabled);
        this.mGlobalActionsKeyTimeout = resources.getInteger(R.integer.config_globalActionsKeyTimeout);
        this.mHorizontalScrollFactor = resources.getDimensionPixelSize(R.dimen.config_horizontalScrollFactor);
        this.mVerticalScrollFactor = resources.getDimensionPixelSize(R.dimen.config_verticalScrollFactor);
        this.mShowMenuShortcutsWhenKeyboardPresent = resources.getBoolean(R.bool.config_showMenuShortcutsWhenKeyboardPresent);
        this.mMinScalingSpan = resources.getDimensionPixelSize(R.dimen.config_minScalingSpan);
        this.mScreenshotChordKeyTimeout = resources.getInteger(R.integer.config_screenshotChordKeyTimeout);
        this.mSmartSelectionInitializedTimeout = resources.getInteger(R.integer.config_smartSelectionInitializedTimeoutMillis);
        this.mSmartSelectionInitializingTimeout = resources.getInteger(R.integer.config_smartSelectionInitializingTimeoutMillis);
        this.mPreferKeepClearForFocusEnabled = resources.getBoolean(R.bool.config_preferKeepClearForFocus);
        this.mViewBasedRotaryEncoderScrollHapticsEnabledConfig = resources.getBoolean(R.bool.config_viewBasedRotaryEncoderHapticsEnabled);
        this.mViewTouchScreenHapticScrollFeedbackEnabled = Flags.enableScrollFeedbackForTouch() ? resources.getBoolean(R.bool.config_viewTouchScreenHapticScrollFeedbackEnabled) : false;
    }

    public static ViewConfiguration get(Context context) {
        StrictMode.assertConfigurationContext(context, "ViewConfiguration");
        int displayDensity = getDisplayDensity(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayDensity | ((displayMetrics.heightPixels / 10) << 10) | ((displayMetrics.widthPixels / 10) << 21);
        SparseArray<ViewConfiguration> sparseArray = sConfigurations;
        ViewConfiguration viewConfiguration = sparseArray.get(i);
        if (viewConfiguration != null) {
            return viewConfiguration;
        }
        ViewConfiguration viewConfiguration2 = new ViewConfiguration(context);
        sparseArray.put(i, viewConfiguration2);
        return viewConfiguration2;
    }

    public static void resetCacheForTesting() {
        sConfigurations.clear();
        sResourceCache = new ResourceCache();
    }

    public static void setInstanceForTesting(Context context, ViewConfiguration viewConfiguration) {
        sConfigurations.put(getDisplayDensity(context), viewConfiguration);
    }

    public int getScaledScrollBarSize() {
        return this.mScrollbarSize;
    }

    public int getScaledMinScrollbarTouchTarget() {
        return this.mMinScrollbarTouchTarget;
    }

    public int getScaledFadingEdgeLength() {
        return this.mFadingEdgeLength;
    }

    public static int getPressedStateDuration() {
        return sResourceCache.getPressedStateDuration();
    }

    public static int getLongPressTimeout() {
        return AppGlobals.getIntCoreSetting("long_press_timeout", 400);
    }

    public static int getMultiPressTimeout() {
        return AppGlobals.getIntCoreSetting(Settings.Secure.MULTI_PRESS_TIMEOUT, 300);
    }

    public static int getKeyRepeatTimeout() {
        return AppGlobals.getIntCoreSetting(Settings.Secure.KEY_REPEAT_TIMEOUT_MS, 400);
    }

    public static int getKeyRepeatDelay() {
        return AppGlobals.getIntCoreSetting(Settings.Secure.KEY_REPEAT_DELAY_MS, 50);
    }

    public static int getTapTimeout() {
        return sResourceCache.getTapTimeout();
    }

    public static int getJumpTapTimeout() {
        return sResourceCache.getJumpTapTimeout();
    }

    public static int getDoubleTapTimeout() {
        return sResourceCache.getDoubleTapTimeout();
    }

    public static int getDoubleTapMinTime() {
        return sResourceCache.getDoubleTapMinTime();
    }

    public static int getHoverTapTimeout() {
        return sResourceCache.getHoverTapTimeout();
    }

    public static int getHoverTapSlop() {
        return sResourceCache.getHoverTapSlop();
    }

    public int getScaledEdgeSlop() {
        return this.mEdgeSlop;
    }

    public int getScaledTouchSlop() {
        return this.mTouchSlop;
    }

    public int getScaledHandwritingSlop() {
        return this.mHandwritingSlop;
    }

    public int getScaledHoverSlop() {
        return this.mHoverSlop;
    }

    public int getScaledDoubleTapTouchSlop() {
        return this.mDoubleTapTouchSlop;
    }

    public int getScaledPagingTouchSlop() {
        return this.mPagingTouchSlop;
    }

    public int getScaledDoubleTapSlop() {
        return this.mDoubleTapSlop;
    }

    public int getScaledHandwritingGestureLineMargin() {
        return this.mHandwritingGestureLineMargin;
    }

    public int getScaledWindowTouchSlop() {
        return this.mWindowTouchSlop;
    }

    public int getScaledMinimumFlingVelocity() {
        return this.mMinimumFlingVelocity;
    }

    public int getScaledMaximumFlingVelocity() {
        return this.mMaximumFlingVelocity;
    }

    public int getScaledScrollFactor() {
        return (int) this.mVerticalScrollFactor;
    }

    public float getScaledHorizontalScrollFactor() {
        return this.mHorizontalScrollFactor;
    }

    public float getScaledVerticalScrollFactor() {
        return this.mVerticalScrollFactor;
    }

    public int getScaledMaximumDrawingCacheSize() {
        return this.mMaximumDrawingCacheSize;
    }

    public int getScaledOverscrollDistance() {
        return this.mOverscrollDistance;
    }

    public int getScaledOverflingDistance() {
        return this.mOverflingDistance;
    }

    public static long getZoomControlsTimeout() {
        return sResourceCache.getZoomControlsTimeout();
    }

    public long getDeviceGlobalActionKeyTimeout() {
        return this.mGlobalActionsKeyTimeout;
    }

    public long getScreenshotChordKeyTimeout() {
        return this.mScreenshotChordKeyTimeout;
    }

    public static float getScrollFriction() {
        return sResourceCache.getScrollFriction();
    }

    public static long getDefaultActionModeHideDuration() {
        return sResourceCache.getDefaultActionModeHideDuration();
    }

    public float getScaledAmbiguousGestureMultiplier() {
        return this.mAmbiguousGestureMultiplier;
    }

    public boolean hasPermanentMenuKey() {
        return this.sHasPermanentMenuKey;
    }

    public int getScaledMinimumFlingVelocity(int i, int i2, int i3) {
        if (!isInputDeviceInfoValid(i, i2, i3)) {
            return Integer.MAX_VALUE;
        }
        if (i3 == 4194304) {
            return this.mMinimumRotaryEncoderFlingVelocity;
        }
        return this.mMinimumFlingVelocity;
    }

    public int getScaledMaximumFlingVelocity(int i, int i2, int i3) {
        if (!isInputDeviceInfoValid(i, i2, i3)) {
            return Integer.MIN_VALUE;
        }
        if (i3 == 4194304) {
            return this.mMaximumRotaryEncoderFlingVelocity;
        }
        return this.mMaximumFlingVelocity;
    }

    public boolean isHapticScrollFeedbackEnabled(int i, int i2, int i3) {
        if (!isInputDeviceInfoValid(i, i2, i3)) {
            return false;
        }
        if (i3 == 4194304 && i2 == 26) {
            return this.mRotaryEncoderHapticScrollFeedbackEnabled;
        }
        if ((i3 & 4098) != 0) {
            return this.mViewTouchScreenHapticScrollFeedbackEnabled;
        }
        return false;
    }

    public int getHapticScrollFeedbackTickInterval(int i, int i2, int i3) {
        if (this.mRotaryEncoderHapticScrollFeedbackEnabled && isInputDeviceInfoValid(i, i2, i3) && i3 == 4194304 && i2 == 26) {
            return this.mRotaryEncoderHapticScrollFeedbackTickIntervalPixels;
        }
        return Integer.MAX_VALUE;
    }

    public boolean isViewBasedRotaryEncoderHapticScrollFeedbackEnabled() {
        return this.mViewBasedRotaryEncoderScrollHapticsEnabledConfig && Flags.useViewBasedRotaryEncoderScrollHaptics();
    }

    private static boolean isInputDeviceInfoValid(int i, int i2, int i3) {
        InputDevice inputDevice = InputManagerGlobal.getInstance().getInputDevice(i);
        return (inputDevice == null || inputDevice.getMotionRange(i2, i3) == null) ? false : true;
    }

    public boolean shouldShowMenuShortcutsWhenKeyboardPresent() {
        return this.mShowMenuShortcutsWhenKeyboardPresent;
    }

    public int getScaledMinimumScalingSpan() {
        if (!this.mConstructedWithContext) {
            throw new IllegalStateException("Min scaling span cannot be determined when this method is called on a ViewConfiguration that was instantiated using a constructor with no Context parameter");
        }
        return this.mMinScalingSpan;
    }

    public boolean isFadingMarqueeEnabled() {
        return this.mFadingMarqueeEnabled;
    }

    public int getSmartSelectionInitializedTimeout() {
        return this.mSmartSelectionInitializedTimeout;
    }

    public int getSmartSelectionInitializingTimeout() {
        return this.mSmartSelectionInitializingTimeout;
    }

    public boolean isPreferKeepClearForFocusEnabled() {
        return this.mPreferKeepClearForFocusEnabled;
    }

    private static int getDisplayDensity(Context context) {
        return (int) (context.getResources().getDisplayMetrics().density * 100.0f);
    }

    private static final class ResourceCache {
        private long mDefaultActionModeHideDuration;
        private int mDoubleTapMinTime;
        private int mDoubleTapTimeout;
        private int mHoverTapSlop;
        private int mHoverTapTimeout;
        private int mJumpTapTimeout;
        private int mPressedStateDuration;
        private float mScrollFriction;
        private int mTapTimeout;
        private long mZoomControlsTimeout;

        private ResourceCache() {
            this.mPressedStateDuration = -1;
            this.mTapTimeout = -1;
            this.mJumpTapTimeout = -1;
            this.mDoubleTapTimeout = -1;
            this.mDoubleTapMinTime = -1;
            this.mHoverTapTimeout = -1;
            this.mHoverTapSlop = -1;
            this.mZoomControlsTimeout = -1L;
            this.mScrollFriction = -1.0f;
            this.mDefaultActionModeHideDuration = -1L;
        }

        public int getPressedStateDuration() {
            if (this.mPressedStateDuration < 0) {
                Resources currentResources = getCurrentResources();
                this.mPressedStateDuration = currentResources != null ? currentResources.getInteger(R.integer.config_pressedStateDurationMillis) : 64;
            }
            return this.mPressedStateDuration;
        }

        public int getTapTimeout() {
            if (this.mTapTimeout < 0) {
                Resources currentResources = getCurrentResources();
                this.mTapTimeout = currentResources != null ? currentResources.getInteger(R.integer.config_tapTimeoutMillis) : 100;
            }
            return this.mTapTimeout;
        }

        public int getJumpTapTimeout() {
            if (this.mJumpTapTimeout < 0) {
                Resources currentResources = getCurrentResources();
                this.mJumpTapTimeout = currentResources != null ? currentResources.getInteger(R.integer.config_jumpTapTimeoutMillis) : 500;
            }
            return this.mJumpTapTimeout;
        }

        public int getDoubleTapTimeout() {
            if (this.mDoubleTapTimeout < 0) {
                Resources currentResources = getCurrentResources();
                this.mDoubleTapTimeout = currentResources != null ? currentResources.getInteger(R.integer.config_doubleTapTimeoutMillis) : 300;
            }
            return this.mDoubleTapTimeout;
        }

        public int getDoubleTapMinTime() {
            if (this.mDoubleTapMinTime < 0) {
                Resources currentResources = getCurrentResources();
                this.mDoubleTapMinTime = currentResources != null ? currentResources.getInteger(R.integer.config_doubleTapMinTimeMillis) : 40;
            }
            return this.mDoubleTapMinTime;
        }

        public int getHoverTapTimeout() {
            if (this.mHoverTapTimeout < 0) {
                Resources currentResources = getCurrentResources();
                this.mHoverTapTimeout = currentResources != null ? currentResources.getInteger(R.integer.config_hoverTapTimeoutMillis) : 150;
            }
            return this.mHoverTapTimeout;
        }

        public int getHoverTapSlop() {
            if (this.mHoverTapSlop < 0) {
                Resources currentResources = getCurrentResources();
                this.mHoverTapSlop = currentResources != null ? currentResources.getDimensionPixelSize(R.dimen.config_hoverTapSlop) : 20;
            }
            return this.mHoverTapSlop;
        }

        public long getZoomControlsTimeout() {
            if (this.mZoomControlsTimeout < 0) {
                this.mZoomControlsTimeout = getCurrentResources() != null ? r0.getInteger(R.integer.config_zoomControlsTimeoutMillis) : 3000L;
            }
            return this.mZoomControlsTimeout;
        }

        public float getScrollFriction() {
            if (this.mScrollFriction < 0.0f) {
                Resources currentResources = getCurrentResources();
                this.mScrollFriction = currentResources != null ? currentResources.getFloat(R.dimen.config_scrollFriction) : ViewConfiguration.SCROLL_FRICTION;
            }
            return this.mScrollFriction;
        }

        public long getDefaultActionModeHideDuration() {
            if (this.mDefaultActionModeHideDuration < 0) {
                this.mDefaultActionModeHideDuration = getCurrentResources() != null ? r0.getInteger(R.integer.config_defaultActionModeHideDurationMillis) : ViewConfiguration.ACTION_MODE_HIDE_DURATION_DEFAULT;
            }
            return this.mDefaultActionModeHideDuration;
        }

        private static Resources getCurrentResources() {
            if (!com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.Flags.migrateViewconfigurationConstantsToResources()) {
                return null;
            }
            Application applicationCurrentApplication = ActivityThread.currentApplication();
            Context applicationContext = applicationCurrentApplication != null ? applicationCurrentApplication.getApplicationContext() : null;
            if (applicationContext != null) {
                return applicationContext.getResources();
            }
            return null;
        }
    }
}
