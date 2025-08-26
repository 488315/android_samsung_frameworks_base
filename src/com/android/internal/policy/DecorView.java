package com.android.internal.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.WindowConfiguration;
import android.app.jank.AppJankStats;
import android.app.jank.JankTracker;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.opengl.GLES30;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.util.Size;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.InputQueue;
import android.view.InsetsState;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PendingInsetsController;
import android.view.RoundedCorners;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOutlineProvider;
import android.view.ViewRootImpl;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowCallbacks;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.DesktopModeFlags;
import com.android.internal.R;
import com.android.internal.app.ChooserActivity;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.StandaloneActionMode;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.BackgroundFallback;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.android.util.SemViewUtils;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks {
    private static final boolean DEBUG_KNOX = false;
    private static final String DEBUG_KNOX_TAG = "DecorView_KNOX";
    private static final boolean DEBUG_MEASURE = false;
    public static final int DECOR_SHADOW_FOCUSED_HEIGHT_IN_DIP = 20;
    public static final int DECOR_SHADOW_UNFOCUSED_HEIGHT_IN_DIP = 5;
    private static final int FRAME_COLOR_POPOVER_DARK = 1721342361;
    private static final int FRAME_COLOR_POPOVER_LIGHT = -3355444;
    public static final ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES;
    private static final ViewOutlineProvider PIP_OUTLINE_PROVIDER = new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };
    private static final int POP_OVER_ANIM_DELAY_TIME = 100;
    private static final int POP_OVER_BACKGROUND_ANIM_DURATION = 200;
    private static final int POP_OVER_CONTENTS_ANIM_DURATION = 100;
    private static final float POP_OVER_CORNER_RADIUS = 26.0f;
    private static final int POP_OVER_ELEVATION_IN_DIP = 32;
    private static final int SCRIM_ALPHA = -872415232;
    private static final int SCRIM_LIGHT = -419430401;
    private static final int SEM_ROUNDED_CORNER_BOTTOM = 12;
    private static final int SEM_ROUNDED_CORNER_LEFT = 5;
    private static final int SEM_ROUNDED_CORNER_RIGHT = 10;
    private static final int SEM_ROUNDED_CORNER_TOP = 3;
    public static final ColorViewAttributes STATUS_BAR_COLOR_VIEW_ATTRIBUTES;
    private static final float STROKE_WIDTH_POPOVER_DARK = 2.0f;
    private static final float STROKE_WIDTH_POPOVER_LIGHT = 1.0f;
    private static final boolean SWEEP_OPEN_MENU = false;
    private static final String TAG = "DecorView";
    private static int sKnoxBadgeRightCutout;
    private final FloatProperty<DecorView> POP_OVER_BACKGROUND_ALPHA;
    private final FloatProperty<DecorView> POP_OVER_CONTENT_ALPHA;
    private final ViewOutlineProvider POP_OVER_OUTLINE_PROVIDER;
    private boolean mAllowUpdateElevation;
    private AppJankStatsCallback mAppJankStatsCallback;
    private boolean mApplyFloatingHorizontalInsets;
    private boolean mApplyFloatingVerticalInsets;
    private BackgroundBlurDrawable mBackgroundBlurDrawable;
    private final ViewTreeObserver.OnPreDrawListener mBackgroundBlurOnPreDrawListener;
    private int mBackgroundBlurRadius;
    private final BackgroundFallback mBackgroundFallback;
    private Insets mBackgroundInsets;
    private final Rect mBackgroundPadding;
    private final int mBarEnterExitDuration;
    private boolean mCalledDisplayCutoutBackgroundColor;
    private ValueAnimator mCaptionPinnedAnimator;
    private boolean mChanging;
    ViewGroup mContentRoot;
    private boolean mCrossWindowBlurEnabled;
    private Consumer<Boolean> mCrossWindowBlurEnabledListener;
    int mDefaultOpacity;
    private int mDensityForKnoxBadge;
    private float mDensityRatio;
    private int mDeviceRoundedCornerBottomRadius;
    private int mDeviceRoundedCornerTopRadius;
    private int mDisplayCutoutBackgroundColor;
    private View mDisplayCutoutBackgroundView;
    private int mDisplayRotationForRoundedCorner;
    private int mDownY;
    private boolean mDrawLegacyNavigationBarBackground;
    private boolean mDrawLegacyNavigationBarBackgroundHandled;
    private final Rect mDrawingBounds;
    private boolean mElevationAdjustedForStack;
    private ObjectAnimator mFadeAnim;
    private final int mFeatureId;
    private ActionMode mFloatingActionMode;
    private View mFloatingActionModeOriginatingView;
    private final Rect mFloatingInsets;
    private FloatingToolbar mFloatingToolbar;
    private ViewTreeObserver.OnPreDrawListener mFloatingToolbarPreDrawListener;
    private boolean mForceHideRoundedCorner;
    private boolean mForceRoundedCorner;
    final boolean mForceWindowDrawsBarBackgrounds;
    private final Rect mFrameOffsets;
    private final Rect mFramePadding;
    private boolean mGestureHintEnabled;
    private boolean mGestureNavBarEnabled;
    private boolean mHasDisplayCutout;
    private boolean mHasWindowFocusInTask;
    private final Interpolator mHideInterpolator;
    private boolean mIsDexEnabled;
    private boolean mIsDialog;
    private boolean mIsFullViewShown;
    private boolean mIsInPictureInPictureMode;
    private boolean mIsKeyboardShown;
    private boolean mIsKnoxActivity;
    private boolean mIsPopOver;
    private boolean mIsPopOverWithoutOutlineEffect;
    private boolean mIsShowNavigationBar;
    private Drawable mKnoxBadge;
    private Runnable mKnoxBadgeDisplayRunnable;
    private Insets mKnoxBadgeInsets;
    private int mKnoxBadgeStartX;
    private int mKnoxBadgeStartY;
    private View mKnoxBadgeView;
    private ViewGroupOverlay mKnoxBadgeViewGroupOverlay;
    private int mKnoxLayoutBottom;
    private int mKnoxLayoutLeft;
    private int mKnoxLayoutRight;
    private BackgroundBlurDrawable mLastBackgroundBlurDrawable;
    private Insets mLastBackgroundInsets;
    private int mLastBackgroundResource;
    private int mLastBottomInset;
    private int mLastCaptionType;
    private int mLastDisplayDeviceType;
    private int mLastDockingState;
    private boolean mLastDrawLegacyNavigationBarBackground;
    private boolean mLastForceConsumingOpaqueCaptionBar;
    private int mLastForceConsumingTypes;
    private boolean mLastHasBottomStableInset;
    private boolean mLastHasLeftStableInset;
    private boolean mLastHasRightStableInset;
    private boolean mLastHasTopStableInset;
    private WindowInsets mLastInsets;
    private int mLastLeftInset;
    private Drawable mLastOriginalBackgroundDrawable;
    private ViewOutlineProvider mLastOutlineProvider;
    private int mLastRightInset;
    private int mLastSmallestScreenWidthDp;
    private int mLastSuppressScrimTypes;
    private int mLastTopInset;
    private int mLastWindowFlags;
    private final Paint mLegacyNavigationBarBackgroundPaint;
    String mLogTag;
    private Drawable mMenuBackground;
    private int mMultiWindowRoundedCornerRadius;
    private final ColorViewState mNavigationColorViewState;
    private int mOriginalBackgroundBlurRadius;
    private Drawable mOriginalBackgroundDrawable;
    private final Rect mOverrideRoundedCornerBounds;
    private PackageManager mPackageManagerForKnoxBadge;
    private PendingInsetsController mPendingInsetsController;
    private Drawable mPendingWindowBackground;
    private float mPopOverBackgroundAlpha;
    private int mPopOverBackgroundColor;
    private final Path mPopOverClipOutPath;
    private float mPopOverContentAlpha;
    private final Paint mPopOverFramePaint;
    private final Paint mPopOverPaint;
    private boolean mPreventPopOverElevation;
    ActionMode mPrimaryActionMode;
    private PopupWindow mPrimaryActionModePopup;
    private ActionBarContextView mPrimaryActionModeView;
    private Drawable mReverseKnoxBadge;
    private int mRootScrollY;
    private int mRotationForRoundedCorner;
    private int mRoundedCornerMode;
    private int mRoundedCornerRadius;
    private int mRoundedCornerRadiusForAppsCoverLauncherLetterBox;
    private int mRoundedCornerRadiusForLetterBox;
    private final int mSemiTransparentBarColor;
    private final Interpolator mShowInterpolator;
    private boolean mShowPopOver;
    private Runnable mShowPrimaryActionModePopup;
    private final ColorViewState mStatusColorViewState;
    private View mStatusGuard;
    private boolean mStayFocus;
    private Rect mTempRect;
    private final Rect mTmpColorViewBounds;
    private int mUserId;
    private boolean mWatchingForMenu;
    private final WearGestureInterceptionDetector mWearGestureInterceptionDetector;
    private PhoneWindow mWindow;
    private boolean mWindowResizeCallbacksAdded;
    private int mWindowingMode;
    private WindowManager mWm;

    public interface AppJankStatsCallback {
        JankTracker getAppJankTracker();
    }

    private int getRoundedCornersInLandscapeMode(int i, int i2) {
        if (i != 1 && i2 != 0) {
            i = (i2 & 1) != 0 ? 5 : 0;
            if ((i2 & 2) != 0) {
                return i | 10;
            }
        }
        return i;
    }

    public static boolean isNavBarToLeftEdge(int i, int i2) {
        return i == 0 && i2 > 0;
    }

    public static boolean isNavBarToRightEdge(int i, int i2) {
        return i == 0 && i2 > 0;
    }

    @Override // android.view.View
    public int getAccessibilityViewId() {
        return 2147483646;
    }

    @Override // android.view.ViewGroup
    public boolean isTransitionGroup() {
        return false;
    }

    @Override // android.view.WindowCallbacks
    public boolean onContentDrawn(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override // android.view.WindowCallbacks
    public void onWindowSizeIsChanging(Rect rect, boolean z, Rect rect2, Rect rect3) {
    }

    static {
        int i = 5;
        STATUS_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(67108864, 48, 3, i, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, 16908335, WindowInsets.Type.statusBars());
        NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(134217728, 80, i, 3, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, 16908336, WindowInsets.Type.navigationBars());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        updateBackgroundBlurCorners();
        return true;
    }

    DecorView(Context context, int i, PhoneWindow phoneWindow, WindowManager.LayoutParams layoutParams) {
        WindowConfiguration windowConfiguration;
        super(context);
        boolean z = false;
        this.mDensityForKnoxBadge = 0;
        this.mKnoxBadge = null;
        this.mReverseKnoxBadge = null;
        this.mDensityRatio = 1.0f;
        this.mPackageManagerForKnoxBadge = null;
        this.mKnoxBadgeView = null;
        this.mKnoxBadgeViewGroupOverlay = null;
        this.mWm = null;
        this.mKnoxBadgeInsets = null;
        this.mLastDockingState = 0;
        this.mAllowUpdateElevation = false;
        this.mElevationAdjustedForStack = false;
        this.mDefaultOpacity = -1;
        this.mDrawingBounds = new Rect();
        this.mBackgroundPadding = new Rect();
        this.mFramePadding = new Rect();
        this.mFrameOffsets = new Rect();
        this.mStatusColorViewState = new ColorViewState(STATUS_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mNavigationColorViewState = new ColorViewState(NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mBackgroundFallback = new BackgroundFallback();
        this.mLastTopInset = 0;
        this.mLastBottomInset = 0;
        this.mLastRightInset = 0;
        this.mLastLeftInset = 0;
        this.mLastInsets = null;
        this.mLastHasTopStableInset = false;
        this.mLastHasBottomStableInset = false;
        this.mLastHasRightStableInset = false;
        this.mLastHasLeftStableInset = false;
        this.mLastWindowFlags = 0;
        this.mLastForceConsumingTypes = 0;
        this.mLastForceConsumingOpaqueCaptionBar = false;
        this.mLastSuppressScrimTypes = 0;
        this.mRootScrollY = 0;
        this.mWindowResizeCallbacksAdded = false;
        this.mLogTag = TAG;
        this.mFloatingInsets = new Rect();
        this.mApplyFloatingVerticalInsets = false;
        this.mApplyFloatingHorizontalInsets = false;
        Paint paint = new Paint();
        this.mLegacyNavigationBarBackgroundPaint = paint;
        this.mBackgroundInsets = Insets.NONE;
        this.mLastBackgroundInsets = Insets.NONE;
        this.mPendingInsetsController = new PendingInsetsController();
        this.mOriginalBackgroundBlurRadius = 0;
        this.mBackgroundBlurRadius = 0;
        this.mBackgroundBlurOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.policy.DecorView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return this.f$0.lambda$new$0();
            }
        };
        this.mWindowingMode = 0;
        this.mIsDexEnabled = false;
        this.POP_OVER_OUTLINE_PROVIDER = new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setPath(SemViewUtils.getSmoothCornerRectPath(DecorView.this.dpToPixel(DecorView.POP_OVER_CORNER_RADIUS), 0.0f, 0.0f, view.getWidth(), view.getHeight()));
            }
        };
        this.POP_OVER_BACKGROUND_ALPHA = new FloatProperty<DecorView>(this, "backgroundAlpha") { // from class: com.android.internal.policy.DecorView.3
            @Override // android.util.FloatProperty
            public void setValue(DecorView decorView, float f) {
                decorView.setBackgroundAlpha(f);
            }

            @Override // android.util.Property
            public Float get(DecorView decorView) {
                return Float.valueOf(decorView.getBackgroundAlpha());
            }
        };
        this.POP_OVER_CONTENT_ALPHA = new FloatProperty<DecorView>(this, "contentAlpha") { // from class: com.android.internal.policy.DecorView.4
            @Override // android.util.FloatProperty
            public void setValue(DecorView decorView, float f) {
                decorView.setContentAlpha(f);
            }

            @Override // android.util.Property
            public Float get(DecorView decorView) {
                return Float.valueOf(decorView.getContentAlpha());
            }
        };
        this.mPopOverPaint = new Paint();
        this.mPopOverFramePaint = new Paint();
        this.mPopOverClipOutPath = new Path();
        this.mIsPopOverWithoutOutlineEffect = false;
        this.mPreventPopOverElevation = false;
        this.mShowPopOver = true;
        this.mPopOverBackgroundColor = -1;
        this.mPopOverBackgroundAlpha = 1.0f;
        this.mPopOverContentAlpha = 1.0f;
        this.mHasDisplayCutout = false;
        this.mForceRoundedCorner = false;
        this.mRoundedCornerMode = 0;
        this.mOverrideRoundedCornerBounds = new Rect();
        this.mTmpColorViewBounds = new Rect();
        this.mDeviceRoundedCornerTopRadius = -1;
        this.mDeviceRoundedCornerBottomRadius = -1;
        this.mLastBackgroundResource = 0;
        this.mIsDialog = false;
        this.mDisplayCutoutBackgroundColor = 0;
        this.mCalledDisplayCutoutBackgroundColor = false;
        this.mForceHideRoundedCorner = false;
        this.mFeatureId = i;
        this.mShowInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mHideInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mBarEnterExitDuration = context.getResources().getInteger(R.integer.dock_enter_exit_duration);
        this.mForceWindowDrawsBarBackgrounds = context.getResources().getBoolean(R.bool.config_forceWindowDrawsStatusBarBackground) && layoutParams.type != 2011 && context.getApplicationInfo().targetSdkVersion >= 24;
        this.mSemiTransparentBarColor = context.getResources().getColor(R.color.system_bar_background_semi_transparent, null);
        setWindow(phoneWindow);
        updateLogTag(layoutParams);
        paint.setColor(this.mWindow.getDeviceDefaultNavigationBarColor());
        this.mWearGestureInterceptionDetector = WearGestureInterceptionDetector.isEnabled(context) ? new WearGestureInterceptionDetector(context, this) : null;
        Resources resources = context.getResources();
        if (this.mWindow.mActivityCurrentConfig != null) {
            windowConfiguration = this.mWindow.mActivityCurrentConfig.windowConfiguration;
        } else {
            windowConfiguration = resources.getConfiguration().windowConfiguration;
        }
        this.mWindowingMode = windowConfiguration.getWindowingMode();
        this.mIsShowNavigationBar = resources.getBoolean(R.bool.config_showNavigationBar);
        try {
            int identifier = resources.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
            String string = identifier > 0 ? resources.getString(identifier) : null;
            if (string != null && !TextUtils.isEmpty(string)) {
                z = true;
            }
            this.mHasDisplayCutout = z;
            if (!z) {
                this.mHasDisplayCutout = !TextUtils.isEmpty(resources.getIdentifier("config_subBuiltInDisplayCutout", "string", "android") > 0 ? resources.getString(r14) : null);
            }
        } catch (Exception e) {
            Log.w(this.mLogTag, "Can not update hasDisplayCutout. " + e.toString());
        }
        this.mRoundedCornerRadius = resources.getDimensionPixelSize(R.dimen.sem_rounded_corner_radius);
        this.mRoundedCornerRadiusForLetterBox = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_for_letterbox);
        Display displayNoVerify = context.getDisplayNoVerify();
        if (displayNoVerify != null && displayNoVerify.getDisplayId() == 0) {
            String uniqueId = displayNoVerify.getUniqueId();
            this.mDeviceRoundedCornerTopRadius = RoundedCorners.getRoundedCornerTopRadius(resources, uniqueId);
            this.mDeviceRoundedCornerBottomRadius = RoundedCorners.getRoundedCornerBottomRadius(resources, uniqueId);
        } else {
            int i2 = this.mRoundedCornerRadiusForLetterBox;
            this.mDeviceRoundedCornerTopRadius = i2;
            this.mDeviceRoundedCornerBottomRadius = i2;
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT) {
            this.mRoundedCornerRadiusForAppsCoverLauncherLetterBox = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_for_apps_cover_launcher_letterbox);
        }
        this.mMultiWindowRoundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(context);
        this.mIsPopOver = resources.getConfiguration().windowConfiguration.isPopOver();
        this.mLastDisplayDeviceType = resources.getConfiguration().semDisplayDeviceType;
        if (windowConfiguration.isPopOverWithoutOutlineEffect()) {
            this.mIsPopOverWithoutOutlineEffect = true;
        }
        if (this.mIsPopOver && !this.mIsPopOverWithoutOutlineEffect) {
            if (this.mWindow.getContext() instanceof ChooserActivity) {
                this.mPopOverBackgroundColor = resources.getColor(R.color.sem_resolver_bg_color);
            } else if (this.mPopOverBackgroundColor == -1) {
                this.mPopOverBackgroundColor = resources.getColor(SemViewUtils.isLightTheme(this.mContext) ? R.color.sem_app_bar_bg_color : R.color.sem_app_bar_bg_color_dark, null);
            }
            Log.i(TAG, "mPopOverBackgroundColor=" + Integer.toHexString(this.mPopOverBackgroundColor));
        }
        if (CoreRune.MW_CAPTION_TYPE) {
            this.mLastCaptionType = -1;
        }
    }

    void setBackgroundFallback(Drawable drawable) {
        this.mBackgroundFallback.setDrawable(drawable);
        setWillNotDraw(getBackground() == null && !this.mBackgroundFallback.hasFallback());
    }

    public Drawable getBackgroundFallback() {
        return this.mBackgroundFallback.getDrawable();
    }

    View getStatusBarBackgroundView() {
        return this.mStatusColorViewState.view;
    }

    View getNavigationBarBackgroundView() {
        return this.mNavigationColorViewState.view;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mBackgroundFallback.draw(this, this.mContentRoot, canvas, this.mWindow.mContentParent, this.mStatusColorViewState.view, this.mNavigationColorViewState.view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (z && keyEvent.getRepeatCount() == 0) {
            if (this.mWindow.mPanelChordingKey > 0 && this.mWindow.mPanelChordingKey != keyCode && dispatchKeyShortcutEvent(keyEvent)) {
                return true;
            }
            if (this.mWindow.mPreparedPanel != null && this.mWindow.mPreparedPanel.isOpen) {
                PhoneWindow phoneWindow = this.mWindow;
                if (phoneWindow.performPanelShortcut(phoneWindow.mPreparedPanel, keyCode, keyEvent, 0)) {
                    return true;
                }
            }
        }
        if (!this.mWindow.isDestroyed()) {
            Window.Callback callback = this.mWindow.getCallback();
            if ((callback == null || this.mFeatureId >= 0) ? super.dispatchKeyEvent(keyEvent) : callback.dispatchKeyEvent(keyEvent)) {
                return true;
            }
        }
        if (z) {
            return this.mWindow.onKeyDown(this.mFeatureId, keyEvent.getKeyCode(), keyEvent);
        }
        return this.mWindow.onKeyUp(this.mFeatureId, keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (this.mWindow.mPreparedPanel != null) {
            PhoneWindow phoneWindow = this.mWindow;
            if (phoneWindow.performPanelShortcut(phoneWindow.mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
                if (this.mWindow.mPreparedPanel != null) {
                    this.mWindow.mPreparedPanel.isHandled = true;
                }
                return true;
            }
        }
        Window.Callback callback = this.mWindow.getCallback();
        if ((callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchKeyShortcutEvent(keyEvent) : callback.dispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        PhoneWindow.PanelFeatureState panelState = this.mWindow.getPanelState(0, false);
        if (panelState != null && this.mWindow.mPreparedPanel == null) {
            this.mWindow.preparePanel(panelState, keyEvent);
            boolean zPerformPanelShortcut = this.mWindow.performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent, 1);
            panelState.isPrepared = false;
            if (zPerformPanelShortcut) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        return (callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTouchEvent(motionEvent) : callback.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        return (callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTrackballEvent(motionEvent) : callback.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        return (callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchGenericMotionEvent(motionEvent) : callback.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            int action = keyEvent.getAction();
            ActionMode actionMode = this.mPrimaryActionMode;
            if (actionMode != null) {
                if (action == 1) {
                    actionMode.finish();
                }
                return true;
            }
        }
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return getViewRootImpl() != null && getViewRootImpl().dispatchUnhandledKeyEvent(keyEvent);
    }

    public boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
        return super.dispatchTrackballEvent(motionEvent);
    }

    public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return onInterceptTouchEvent(motionEvent);
    }

    private boolean isOutOfInnerBounds(int i, int i2) {
        return i < 0 || i2 < 0 || i > getWidth() || i2 > getHeight();
    }

    private boolean isOutOfBounds(int i, int i2) {
        return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        WearGestureInterceptionDetector wearGestureInterceptionDetector;
        int action = motionEvent.getAction();
        if (this.mFeatureId >= 0 && action == 0 && isOutOfBounds((int) motionEvent.getX(), (int) motionEvent.getY())) {
            this.mWindow.closePanel(this.mFeatureId);
            return true;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.getOnBackInvokedDispatcher().onMotionEvent(motionEvent);
            if (viewRootImpl.getOnBackInvokedDispatcher().isBackGestureInProgress()) {
                return true;
            }
        }
        if (viewRootImpl == null || (wearGestureInterceptionDetector = this.mWearGestureInterceptionDetector) == null) {
            return false;
        }
        boolean zIsIntercepting = wearGestureInterceptionDetector.isIntercepting();
        boolean zOnInterceptTouchEvent = this.mWearGestureInterceptionDetector.onInterceptTouchEvent(motionEvent);
        if (zIsIntercepting != zOnInterceptTouchEvent) {
            viewRootImpl.updateDecorViewGestureInterception(zOnInterceptTouchEvent);
        }
        return zOnInterceptTouchEvent;
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int i) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            int i2 = this.mFeatureId;
            if ((i2 == 0 || i2 == 6 || i2 == 2 || i2 == 5) && getChildCount() == 1) {
                getChildAt(0).sendAccessibilityEvent(i);
            } else {
                super.sendAccessibilityEvent(i);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        if (callback == null || this.mWindow.isDestroyed() || !callback.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
            return super.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (frame) {
            Rect rect = this.mDrawingBounds;
            getDrawingRect(rect);
            Drawable foreground = getForeground();
            if (foreground != null) {
                Rect rect2 = this.mFrameOffsets;
                rect.left += rect2.left;
                rect.top += rect2.top;
                rect.right -= rect2.right;
                rect.bottom -= rect2.bottom;
                foreground.setBounds(rect);
                Rect rect3 = this.mFramePadding;
                rect.left += rect3.left - rect2.left;
                rect.top += rect3.top - rect2.top;
                rect.right -= rect3.right - rect2.right;
                rect.bottom -= rect3.bottom - rect2.bottom;
            }
            Drawable background = super.getBackground();
            if (background != null) {
                background.setBounds(rect);
            }
        }
        return frame;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0194  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int iMakeMeasureSpec;
        boolean z;
        int iMakeMeasureSpec2;
        float fApplyDimension;
        int i3;
        float fraction;
        TypedValue typedValue;
        int i4;
        float fraction2;
        int i5;
        float fraction3;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        boolean z2 = false;
        boolean z3 = getResources().getConfiguration().orientation == 1;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        this.mApplyFloatingHorizontalInsets = false;
        if (mode == Integer.MIN_VALUE) {
            PhoneWindow phoneWindow = this.mWindow;
            TypedValue typedValue2 = z3 ? phoneWindow.mFixedWidthMinor : phoneWindow.mFixedWidthMajor;
            if (typedValue2 == null || typedValue2.type == 0) {
                iMakeMeasureSpec = i;
            } else {
                if (typedValue2.type == 5) {
                    fraction3 = typedValue2.getDimension(displayMetrics);
                } else if (typedValue2.type == 6) {
                    fraction3 = typedValue2.getFraction(displayMetrics.widthPixels, displayMetrics.widthPixels);
                } else {
                    i5 = 0;
                    int size = View.MeasureSpec.getSize(i);
                    if (i5 <= 0) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.min(i5, size), 1073741824);
                        z = true;
                    } else {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec((size - this.mFloatingInsets.left) - this.mFloatingInsets.right, Integer.MIN_VALUE);
                        this.mApplyFloatingHorizontalInsets = true;
                    }
                }
                i5 = (int) fraction3;
                int size2 = View.MeasureSpec.getSize(i);
                if (i5 <= 0) {
                }
            }
            z = false;
        }
        this.mApplyFloatingVerticalInsets = false;
        if (mode2 != Integer.MIN_VALUE) {
            iMakeMeasureSpec2 = i2;
        } else {
            if (z3) {
                typedValue = this.mWindow.mFixedHeightMajor;
            } else {
                typedValue = this.mWindow.mFixedHeightMinor;
            }
            if (typedValue != null && typedValue.type != 0) {
                if (typedValue.type == 5) {
                    fraction2 = typedValue.getDimension(displayMetrics);
                } else if (typedValue.type == 6) {
                    fraction2 = typedValue.getFraction(displayMetrics.heightPixels, displayMetrics.heightPixels);
                } else {
                    i4 = 0;
                    int size3 = View.MeasureSpec.getSize(i2);
                    if (i4 <= 0) {
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min(i4, size3), 1073741824);
                    } else if ((this.mWindow.getAttributes().flags & 256) == 0) {
                        iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((size3 - this.mFloatingInsets.top) - this.mFloatingInsets.bottom, Integer.MIN_VALUE);
                        this.mApplyFloatingVerticalInsets = true;
                    }
                }
                i4 = (int) fraction2;
                int size32 = View.MeasureSpec.getSize(i2);
                if (i4 <= 0) {
                }
            }
        }
        super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec2);
        int measuredWidth = getMeasuredWidth();
        int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824);
        if (!z && mode == Integer.MIN_VALUE) {
            int i6 = getResources().getConfiguration().smallestScreenWidthDp;
            if (this.mLastSmallestScreenWidthDp != i6) {
                Resources.Theme theme = getContext().getTheme();
                theme.resolveAttribute(16843607, this.mWindow.mMinWidthMinor, true);
                theme.resolveAttribute(16843606, this.mWindow.mMinWidthMajor, true);
                this.mLastSmallestScreenWidthDp = i6;
            }
            TypedValue typedValue3 = z3 ? this.mWindow.mMinWidthMinor : this.mWindow.mMinWidthMajor;
            WindowInsets rootWindowInsets = getRootWindowInsets();
            Flags.insetsDecoupledConfiguration();
            if (this.mWindow.mEdgeToEdgeEnforced && rootWindowInsets != null && getConfiguration().windowConfiguration.getStage() != 0) {
                Size frame = rootWindowInsets.getFrame();
                Insets insets = getRootWindowInsets().getInsets(WindowInsets.Type.displayCutout() | WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                fApplyDimension = (frame.getWidth() - insets.left) - insets.right;
            } else {
                fApplyDimension = TypedValue.applyDimension(1, r1.getConfiguration().screenWidthDp, displayMetrics);
            }
            if (typedValue3.type != 0) {
                if (typedValue3.type == 5) {
                    if (this.mIsDialog) {
                        fraction = Math.min(typedValue3.getDimension(displayMetrics), fApplyDimension);
                    } else {
                        fraction = typedValue3.getDimension(displayMetrics);
                    }
                } else if (typedValue3.type == 6) {
                    fraction = typedValue3.getFraction(fApplyDimension, fApplyDimension);
                } else {
                    i3 = 0;
                    if (measuredWidth < i3) {
                        iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
                        z2 = true;
                    }
                }
                i3 = (int) fraction;
                if (measuredWidth < i3) {
                }
            }
        }
        if (z2) {
            super.onMeasure(iMakeMeasureSpec3, iMakeMeasureSpec2);
        }
    }

    public void semSetIsDialog() {
        this.mIsDialog = true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mApplyFloatingVerticalInsets) {
            offsetTopAndBottom(this.mFloatingInsets.top);
        }
        if (this.mApplyFloatingHorizontalInsets) {
            offsetLeftAndRight(this.mFloatingInsets.left);
        }
        updateElevation();
        this.mAllowUpdateElevation = true;
        if (z && this.mDrawLegacyNavigationBarBackground) {
            getViewRootImpl().requestInvalidateRootRenderNode();
        }
        if (this.mIsPopOver) {
            boolean z2 = (this.mWindow.getAttributes().samsungFlags & 2) == 0;
            if (this.mShowPopOver != z2) {
                this.mShowPopOver = z2;
                if (z2) {
                    showPopOver();
                } else {
                    hidePopOver();
                }
            }
            if (z) {
                getViewRootImpl().requestInvalidateRootRenderNode();
            }
        }
        if (this.mKnoxBadgeViewGroupOverlay != null) {
            this.mKnoxLayoutLeft = i;
            this.mKnoxLayoutRight = i3;
            this.mKnoxLayoutBottom = i4;
            post(this.mKnoxBadgeDisplayRunnable);
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        updateRoundedCornerStateIfNeeded();
        if (this.mPopOverBackgroundAlpha < 1.0f) {
            int width = getWidth();
            int height = getHeight();
            this.mPopOverClipOutPath.reset();
            float f = width;
            float f2 = height;
            this.mPopOverClipOutPath.addPath(SemViewUtils.getSmoothCornerRectPath(dpToPixel(POP_OVER_CORNER_RADIUS), 0.0f, 0.0f, f, f2));
            canvas.clipPath(this.mPopOverClipOutPath);
            this.mPopOverPaint.reset();
            this.mPopOverPaint.setColor(Color.argb(this.mPopOverBackgroundAlpha, Color.red(this.mPopOverBackgroundColor) / 255.0f, Color.green(this.mPopOverBackgroundColor) / 255.0f, Color.blue(this.mPopOverBackgroundColor) / 255.0f));
            this.mPopOverPaint.setBlendMode(BlendMode.SRC);
            canvas.drawRect(new RectF(0.0f, 0.0f, f, f2), this.mPopOverPaint);
            canvas.clipOutPath(this.mPopOverClipOutPath);
        }
        super.draw(canvas);
        Drawable drawable = this.mMenuBackground;
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mPopOverContentAlpha < 1.0f) {
            int saveCount = canvas.getSaveCount();
            canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), (int) (this.mPopOverContentAlpha * 255.0f));
            super.dispatchDraw(canvas);
            canvas.restoreToCount(saveCount);
            return;
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View view) {
        return showContextMenuForChildInternal(view, Float.NaN, Float.NaN);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return showContextMenuForChildInternal(view, f, f2);
    }

    private boolean showContextMenuForChildInternal(View view, float f, float f2) {
        MenuHelper menuHelperShowDialog;
        if (this.mWindow.mContextMenuHelper != null) {
            this.mWindow.mContextMenuHelper.dismiss();
            this.mWindow.mContextMenuHelper = null;
        }
        PhoneWindow.PhoneWindowMenuCallback phoneWindowMenuCallback = this.mWindow.mContextMenuCallback;
        if (this.mWindow.mContextMenu == null) {
            this.mWindow.mContextMenu = new ContextMenuBuilder(getContext());
            this.mWindow.mContextMenu.setCallback(phoneWindowMenuCallback);
        } else {
            this.mWindow.mContextMenu.clearAll();
        }
        boolean z = (Float.isNaN(f) || Float.isNaN(f2)) ? false : true;
        if (z) {
            menuHelperShowDialog = this.mWindow.mContextMenu.showPopup(view.getContext(), view, f, f2);
        } else {
            menuHelperShowDialog = this.mWindow.mContextMenu.showDialog(view, view.getWindowToken());
        }
        if (menuHelperShowDialog != null) {
            phoneWindowMenuCallback.setShowDialogForSubmenu(!z);
            menuHelperShowDialog.setPresenterCallback(phoneWindowMenuCallback);
        }
        this.mWindow.mContextMenuHelper = menuHelperShowDialog;
        return menuHelperShowDialog != null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return startActionModeForChild(view, callback, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        return startActionMode(view, callback, i);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int i) {
        return startActionMode(this, callback, i);
    }

    private ActionMode startActionMode(View view, ActionMode.Callback callback, int i) {
        ActionMode actionModeOnWindowStartingActionMode;
        ActionModeCallback2Wrapper actionModeCallback2Wrapper = new ActionModeCallback2Wrapper(callback);
        ActionMode actionMode = null;
        if (this.mWindow.getCallback() == null || this.mWindow.isDestroyed()) {
            actionModeOnWindowStartingActionMode = null;
        } else {
            try {
                actionModeOnWindowStartingActionMode = this.mWindow.getCallback().onWindowStartingActionMode(actionModeCallback2Wrapper, i);
            } catch (AbstractMethodError unused) {
                if (i == 0) {
                    try {
                        actionModeOnWindowStartingActionMode = this.mWindow.getCallback().onWindowStartingActionMode(actionModeCallback2Wrapper);
                    } catch (AbstractMethodError unused2) {
                    }
                }
            }
        }
        if (actionModeOnWindowStartingActionMode != null) {
            if (actionModeOnWindowStartingActionMode.getType() == 0) {
                cleanupPrimaryActionMode();
                this.mPrimaryActionMode = actionModeOnWindowStartingActionMode;
            } else if (actionModeOnWindowStartingActionMode.getType() == 1 || actionModeOnWindowStartingActionMode.getType() == 99) {
                ActionMode actionMode2 = this.mFloatingActionMode;
                if (actionMode2 != null) {
                    actionMode2.finish();
                }
                this.mFloatingActionMode = actionModeOnWindowStartingActionMode;
            }
            actionMode = actionModeOnWindowStartingActionMode;
        } else {
            ActionMode actionModeCreateActionMode = createActionMode(i, actionModeCallback2Wrapper, view);
            if (actionModeCreateActionMode != null && actionModeCallback2Wrapper.onCreateActionMode(actionModeCreateActionMode, actionModeCreateActionMode.getMenu())) {
                setHandledActionMode(actionModeCreateActionMode);
                actionMode = actionModeCreateActionMode;
            }
        }
        if (actionMode != null && this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
            try {
                this.mWindow.getCallback().onActionModeStarted(actionMode);
            } catch (AbstractMethodError unused3) {
            }
        }
        return actionMode;
    }

    private void cleanupPrimaryActionMode() {
        ActionMode actionMode = this.mPrimaryActionMode;
        if (actionMode != null) {
            actionMode.finish();
            this.mPrimaryActionMode = null;
        }
        ActionBarContextView actionBarContextView = this.mPrimaryActionModeView;
        if (actionBarContextView != null) {
            actionBarContextView.killMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupFloatingActionModeViews() {
        FloatingToolbar floatingToolbar = this.mFloatingToolbar;
        if (floatingToolbar != null) {
            floatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        View view = this.mFloatingActionModeOriginatingView;
        if (view != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                view.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    void startChanging() {
        this.mChanging = true;
    }

    void finishChanging() {
        this.mChanging = false;
        drawableChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setWindowBackground(Drawable drawable) {
        boolean z = drawable instanceof ColorDrawable;
        if (z) {
            this.mPopOverBackgroundColor = ((ColorDrawable) drawable).getColor();
        } else if (drawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) drawable;
            if (gradientDrawable.getColor() != null) {
                this.mPopOverBackgroundColor = gradientDrawable.getColor().getDefaultColor();
            } else {
                this.mPopOverBackgroundColor = getResources().getColor(SemViewUtils.isLightTheme(this.mContext) ? R.color.sem_app_bar_bg_color : R.color.sem_app_bar_bg_color_dark, null);
            }
        }
        Log.i(TAG, "setWindowBackground: isPopOver=" + this.mIsPopOver + " color=" + Integer.toHexString(this.mPopOverBackgroundColor) + " d=" + drawable);
        if (this.mWindow == null) {
            this.mPendingWindowBackground = drawable;
            return;
        }
        if (this.mOriginalBackgroundDrawable != drawable) {
            this.mOriginalBackgroundDrawable = drawable;
            updateBackgroundDrawable();
            if (this.mWindow.mEdgeToEdgeEnforced && !this.mWindow.mNavigationBarColorSpecified && z) {
                int color = ((ColorDrawable) drawable).getColor();
                getWindowInsetsController().setSystemBarsAppearance((Color.valueOf(color).luminance() > 0.5f ? 1 : (Color.valueOf(color).luminance() == 0.5f ? 0 : -1)) > 0 ? 512 : 0, 512);
                this.mWindow.mNavigationBarColor = color;
                updateColorViews(null, false);
            }
            if (drawable != null) {
                drawable.getPadding(this.mBackgroundPadding);
            } else if (this.mWindow.mBackgroundDrawable != null) {
                this.mWindow.mBackgroundDrawable.getPadding(this.mBackgroundPadding);
            } else if (this.mWindow.mBackgroundFallbackDrawable != null) {
                this.mWindow.mBackgroundFallbackDrawable.getPadding(this.mBackgroundPadding);
            } else {
                this.mBackgroundPadding.setEmpty();
            }
            if (View.sBrokenWindowBackground) {
                return;
            }
            drawableChanged();
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        setWindowBackground(drawable);
    }

    public void setWindowFrame(Drawable drawable) {
        if (getForeground() != drawable) {
            setForeground(drawable);
            if (drawable != null) {
                drawable.getPadding(this.mFramePadding);
            } else {
                this.mFramePadding.setEmpty();
            }
            drawableChanged();
        }
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        updateColorViews(null, true);
        View view = this.mStatusGuard;
        if (view != null && view.getVisibility() == 0) {
            updateStatusGuardColor();
        }
        try {
            if (this.mKnoxBadgeViewGroupOverlay != null) {
                int systemUiVisibility = getSystemUiVisibility() | getWindowSystemUiVisibility();
                if ((systemUiVisibility & GLES30.GL_COLOR) == 0 || (systemUiVisibility & 6) == 0) {
                    return;
                }
                hideKnoxBadge();
            }
        } catch (Exception unused) {
            Log.d(DEBUG_KNOX_TAG, "failed to remove knox badge");
        }
    }

    @Override // android.view.View
    public void onSystemBarAppearanceChanged(int i) {
        updateColorViews(null, true);
        PhoneWindow phoneWindow = this.mWindow;
        if (phoneWindow != null) {
            phoneWindow.dispatchOnSystemBarAppearanceChanged(i);
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        this.mFloatingInsets.setEmpty();
        if ((attributes.flags & 256) == 0) {
            if (attributes.height == -2) {
                this.mFloatingInsets.top = windowInsets.getSystemWindowInsetTop();
                this.mFloatingInsets.bottom = windowInsets.getSystemWindowInsetBottom();
                windowInsets = windowInsets.inset(0, windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
            }
            if (this.mWindow.getAttributes().width == -2) {
                this.mFloatingInsets.left = windowInsets.getSystemWindowInsetTop();
                this.mFloatingInsets.right = windowInsets.getSystemWindowInsetBottom();
                windowInsets = windowInsets.inset(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), 0);
            }
        }
        this.mFrameOffsets.set(windowInsets.getSystemWindowInsetsAsRect());
        WindowInsets windowInsetsUpdateStatusGuard = updateStatusGuard(updateColorViews(windowInsets, true));
        if (getForeground() != null) {
            drawableChanged();
        }
        updateDisplayCutoutBackground(windowInsetsUpdateStatusGuard);
        return windowInsetsUpdateStatusGuard;
    }

    public static int getNavBarSize(int i, int i2, int i3) {
        return isNavBarToRightEdge(i, i2) ? i2 : isNavBarToLeftEdge(i, i3) ? i3 : i;
    }

    public static void getNavigationBarRect(int i, int i2, Rect rect, Rect rect2, float f) {
        int i3 = (int) (rect.bottom * f);
        int i4 = (int) (rect.left * f);
        int i5 = (int) (rect.right * f);
        int navBarSize = getNavBarSize(i3, i5, i4);
        if (isNavBarToRightEdge(i3, i5)) {
            rect2.set(i - navBarSize, 0, i, i2);
        } else if (isNavBarToLeftEdge(i3, i4)) {
            rect2.set(0, 0, navBarSize, i2);
        } else {
            rect2.set(0, i2 - navBarSize, i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0206 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    WindowInsets updateColorViews(WindowInsets windowInsets, boolean z) {
        int systemBarsAppearance;
        int i;
        boolean z2;
        int i2;
        boolean z3;
        int i3;
        boolean z4;
        int i4;
        int i5;
        Insets insetsMin;
        WindowInsets windowInsetsInset;
        WindowInsets windowInsets2;
        int i6;
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        int windowSystemUiVisibility = attributes.systemUiVisibility | getWindowSystemUiVisibility();
        ViewRootImpl viewRootImpl = getViewRootImpl();
        WindowInsetsController windowInsetsController = getWindowInsetsController();
        int requestedVisibleTypes = windowInsetsController.getRequestedVisibleTypes();
        if (viewRootImpl != null) {
            systemBarsAppearance = viewRootImpl.mWindowAttributes.insetsFlags.appearance;
        } else {
            systemBarsAppearance = windowInsetsController.getSystemBarsAppearance();
        }
        int i7 = systemBarsAppearance;
        boolean z5 = this.mWindow.getAttributes().type == 2011;
        if (!this.mWindow.mIsFloating || z5) {
            boolean z6 = (!isLaidOut()) | (((this.mLastWindowFlags ^ attributes.flags) & Integer.MIN_VALUE) != 0);
            this.mLastWindowFlags = attributes.flags;
            if (windowInsets != null) {
                this.mLastInsets = windowInsets;
                this.mLastForceConsumingTypes = windowInsets.getForceConsumingTypes();
                this.mLastForceConsumingOpaqueCaptionBar = windowInsets.isForceConsumingOpaqueCaptionBar();
                if (CoreRune.MW_CAPTION_TYPE && this.mLastCaptionType == -1 && this.mLastInsets.getConsumedCaptionType() != -1) {
                    this.mLastCaptionType = this.mLastInsets.getConsumedCaptionType();
                }
                boolean zClearsCompatInsets = InsetsState.clearsCompatInsets(attributes.type, attributes.flags, getResources().getConfiguration().windowConfiguration.getActivityType(), this.mLastForceConsumingTypes);
                int iSystemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
                Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
                if (zClearsCompatInsets) {
                    insetsMin = Insets.NONE;
                } else {
                    insetsMin = Insets.min(windowInsets.getInsets(iSystemBars), insetsIgnoringVisibility);
                }
                this.mLastTopInset = insetsMin.top;
                this.mLastBottomInset = insetsMin.bottom;
                this.mLastRightInset = insetsMin.right;
                this.mLastLeftInset = insetsMin.left;
                this.mKnoxBadgeInsets = windowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                boolean z7 = insetsIgnoringVisibility.top != 0;
                boolean z8 = z6 | (z7 != this.mLastHasTopStableInset);
                this.mLastHasTopStableInset = z7;
                boolean z9 = insetsIgnoringVisibility.bottom != 0;
                boolean z10 = z8 | (z9 != this.mLastHasBottomStableInset);
                this.mLastHasBottomStableInset = z9;
                boolean z11 = insetsIgnoringVisibility.right != 0;
                boolean z12 = z10 | (z11 != this.mLastHasRightStableInset);
                this.mLastHasRightStableInset = z11;
                boolean z13 = insetsIgnoringVisibility.left != 0;
                z6 = z12 | (z13 != this.mLastHasLeftStableInset);
                this.mLastHasLeftStableInset = z13;
                this.mLastSuppressScrimTypes = windowInsets.getSuppressScrimTypes();
            } else {
                updateDisplayCutoutBackground(null);
            }
            boolean z14 = z6;
            boolean zIsNavBarToRightEdge = isNavBarToRightEdge(this.mLastBottomInset, this.mLastRightInset);
            boolean zIsNavBarToLeftEdge = isNavBarToLeftEdge(this.mLastBottomInset, this.mLastLeftInset);
            int navBarSize = getNavBarSize(this.mLastBottomInset, this.mLastRightInset, this.mLastLeftInset);
            ColorViewState colorViewState = this.mNavigationColorViewState;
            int iCalculateNavigationBarColor = calculateNavigationBarColor(i7);
            int i8 = this.mWindow.mNavigationBarDividerColor;
            if (zIsNavBarToRightEdge || zIsNavBarToLeftEdge) {
                i = -1;
                z2 = true;
            } else {
                i = -1;
                z2 = false;
            }
            if (!z || z14) {
                i2 = i;
                z3 = false;
            } else {
                i2 = i;
                z3 = true;
            }
            i3 = i2;
            updateColorViewInt(colorViewState, iCalculateNavigationBarColor, i8, navBarSize, z2, zIsNavBarToLeftEdge, 0, z3, this.mForceWindowDrawsBarBackgrounds, requestedVisibleTypes);
            boolean z15 = this.mDrawLegacyNavigationBarBackground;
            this.mDrawLegacyNavigationBarBackground = ((this.mLastForceConsumingTypes | requestedVisibleTypes) & WindowInsets.Type.navigationBars()) != 0 && (this.mWindow.getAttributes().flags & Integer.MIN_VALUE) == 0 && navBarSize > 0;
            View view = this.mNavigationColorViewState.view;
            if (z15 != this.mDrawLegacyNavigationBarBackground) {
                this.mDrawLegacyNavigationBarBackgroundHandled = this.mWindow.onDrawLegacyNavigationBarBackgroundChanged(this.mDrawLegacyNavigationBarBackground);
                if (viewRootImpl != null) {
                    viewRootImpl.requestInvalidateRootRenderNode();
                }
                z4 = !zIsNavBarToRightEdge && this.mNavigationColorViewState.present;
                boolean z16 = !zIsNavBarToLeftEdge && this.mNavigationColorViewState.present;
                if (!z4) {
                    i5 = this.mLastRightInset;
                } else if (z16) {
                    i5 = this.mLastLeftInset;
                } else {
                    i4 = 0;
                    updateColorViewInt(this.mStatusColorViewState, calculateStatusBarColor(i7), 0, this.mLastTopInset, false, z16, i4, z && !z14, this.mForceWindowDrawsBarBackgrounds, requestedVisibleTypes);
                }
                i4 = i5;
                if (z) {
                    updateColorViewInt(this.mStatusColorViewState, calculateStatusBarColor(i7), 0, this.mLastTopInset, false, z16, i4, z && !z14, this.mForceWindowDrawsBarBackgrounds, requestedVisibleTypes);
                }
            } else {
                if (view != null) {
                    if ((view.getVisibility() == 0) != this.mNavigationColorViewState.visible || (this.mDrawLegacyNavigationBarBackground && view.getHeight() != navBarSize)) {
                    }
                }
                if (zIsNavBarToRightEdge) {
                    if (zIsNavBarToLeftEdge) {
                        if (!z4) {
                        }
                        i4 = i5;
                    }
                }
            }
        } else {
            i3 = -1;
        }
        boolean z17 = (windowSystemUiVisibility & 2) != 0 || (WindowInsets.Type.navigationBars() & requestedVisibleTypes) == 0;
        boolean z18 = this.mWindow.mDecorFitsSystemWindows;
        boolean z19 = (windowSystemUiVisibility & 512) == 0 && z18 && !z17;
        boolean z20 = ((this.mForceWindowDrawsBarBackgrounds || this.mDrawLegacyNavigationBarBackgroundHandled) && (attributes.flags & Integer.MIN_VALUE) == 0 && z19) || ((this.mLastForceConsumingTypes & WindowInsets.Type.navigationBars()) != 0 && z17);
        int iNavigationBars = (((attributes.flags & Integer.MIN_VALUE) == 0 || !z19) && !z20) ? 0 : WindowInsets.Type.navigationBars();
        boolean z21 = ((windowSystemUiVisibility & 4) == 0 && (attributes.flags & 1024) == 0) ? false : true;
        boolean z22 = z21 || (WindowInsets.Type.statusBars() & requestedVisibleTypes) == 0;
        if (((windowSystemUiVisibility & 1024) == 0 && z18 && (attributes.flags & 256) == 0 && (attributes.flags & 65536) == 0 && this.mForceWindowDrawsBarBackgrounds && this.mLastTopInset != 0) || ((this.mLastForceConsumingTypes & WindowInsets.Type.statusBars()) != 0 && z22)) {
            iNavigationBars |= WindowInsets.Type.statusBars();
        }
        boolean z23 = DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION.isTrue() && (this.mLastForceConsumingTypes & WindowInsets.Type.captionBar()) != 0 && (z21 || (WindowInsets.Type.captionBar() & requestedVisibleTypes) == 0);
        boolean z24 = DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS.isTrue() && this.mLastForceConsumingOpaqueCaptionBar && (android.view.flags.Flags.customizableWindowHeaders() && (i7 & 128) == 0);
        if (z23 || z24) {
            iNavigationBars |= WindowInsets.Type.captionBar();
        }
        WindowInsets windowInsets3 = this.mLastInsets;
        final Insets insets = windowInsets3 != null ? windowInsets3.getInsets(iNavigationBars) : Insets.NONE;
        ViewGroup viewGroup = this.mContentRoot;
        if (viewGroup == null || !(viewGroup.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            windowInsetsInset = windowInsets;
        } else {
            final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mContentRoot.getLayoutParams();
            if (marginLayoutParams.topMargin != insets.top || marginLayoutParams.rightMargin != insets.right || marginLayoutParams.bottomMargin != insets.bottom || marginLayoutParams.leftMargin != insets.left) {
                if (CoreRune.MW_CAPTION_TYPE && this.mContext.getDisplayId() == 0 && isFreeformMode() && (windowInsets2 = this.mLastInsets) != null) {
                    int consumedCaptionType = windowInsets2.getConsumedCaptionType();
                    if (marginLayoutParams.topMargin != insets.top && (i6 = this.mLastCaptionType) != i3 && consumedCaptionType != i3 && i6 != consumedCaptionType) {
                        this.mLastCaptionType = consumedCaptionType;
                        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(marginLayoutParams.topMargin, insets.top);
                        this.mCaptionPinnedAnimator = valueAnimatorOfInt;
                        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.policy.DecorView$$ExternalSyntheticLambda2
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                this.f$0.lambda$updateColorViews$1(marginLayoutParams, insets, valueAnimator);
                            }
                        });
                        this.mCaptionPinnedAnimator.setInterpolator(InterpolatorUtils.ONE_EASING);
                        this.mCaptionPinnedAnimator.setDuration(400L);
                        this.mCaptionPinnedAnimator.start();
                    } else {
                        ValueAnimator valueAnimator = this.mCaptionPinnedAnimator;
                        if (valueAnimator == null || !valueAnimator.isRunning()) {
                            marginLayoutParams.topMargin = insets.top;
                        }
                        marginLayoutParams.rightMargin = insets.right;
                        marginLayoutParams.bottomMargin = insets.bottom;
                        marginLayoutParams.leftMargin = insets.left;
                        this.mContentRoot.setLayoutParams(marginLayoutParams);
                    }
                } else {
                    marginLayoutParams.topMargin = insets.top;
                    marginLayoutParams.rightMargin = insets.right;
                    marginLayoutParams.bottomMargin = insets.bottom;
                    marginLayoutParams.leftMargin = insets.left;
                    this.mContentRoot.setLayoutParams(marginLayoutParams);
                }
                if (windowInsets == null) {
                    requestApplyInsets();
                }
            }
            if (windowInsets != null && !Insets.NONE.equals(insets)) {
                windowInsetsInset = windowInsets.inset(insets);
            }
        }
        if (z20 && !z17 && !this.mDrawLegacyNavigationBarBackgroundHandled) {
            this.mBackgroundInsets = Insets.of(this.mLastLeftInset, 0, this.mLastRightInset, this.mLastBottomInset);
        } else {
            this.mBackgroundInsets = Insets.NONE;
        }
        updateBackgroundDrawable();
        return windowInsetsInset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateColorViews$1(ViewGroup.MarginLayoutParams marginLayoutParams, Insets insets, ValueAnimator valueAnimator) {
        marginLayoutParams.topMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        marginLayoutParams.rightMargin = insets.right;
        marginLayoutParams.bottomMargin = insets.bottom;
        marginLayoutParams.leftMargin = insets.left;
        this.mContentRoot.setLayoutParams(marginLayoutParams);
    }

    private void updateBackgroundDrawable() {
        DecorView decorView;
        if (this.mBackgroundInsets == null) {
            this.mBackgroundInsets = Insets.NONE;
        }
        if (this.mBackgroundInsets.equals(this.mLastBackgroundInsets) && this.mBackgroundBlurDrawable == this.mLastBackgroundBlurDrawable && this.mLastOriginalBackgroundDrawable == this.mOriginalBackgroundDrawable) {
            return;
        }
        Drawable layerDrawable = this.mOriginalBackgroundDrawable;
        if (this.mBackgroundBlurDrawable != null) {
            layerDrawable = new LayerDrawable(new Drawable[]{this.mBackgroundBlurDrawable, this.mOriginalBackgroundDrawable});
        }
        Drawable drawable = layerDrawable;
        if (drawable == null || this.mBackgroundInsets.equals(Insets.NONE)) {
            decorView = this;
        } else {
            decorView = this;
            drawable = new InsetDrawable(decorView, drawable, this.mBackgroundInsets.left, this.mBackgroundInsets.top, this.mBackgroundInsets.right, this.mBackgroundInsets.bottom) { // from class: com.android.internal.policy.DecorView.5
                @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
                public boolean getPadding(Rect rect) {
                    return getDrawable().getPadding(rect);
                }
            };
        }
        super.setBackgroundDrawable(drawable);
        decorView.mLastBackgroundInsets = decorView.mBackgroundInsets;
        decorView.mLastBackgroundBlurDrawable = decorView.mBackgroundBlurDrawable;
        decorView.mLastOriginalBackgroundDrawable = decorView.mOriginalBackgroundDrawable;
    }

    private void updateBackgroundBlurCorners() {
        if (this.mBackgroundBlurDrawable == null) {
            return;
        }
        float radius = 0.0f;
        if (this.mBackgroundBlurRadius != 0 && this.mOriginalBackgroundDrawable != null) {
            Outline outline = new Outline();
            this.mOriginalBackgroundDrawable.getOutline(outline);
            if (outline.mMode == 1) {
                radius = outline.getRadius();
            }
        }
        this.mBackgroundBlurDrawable.setCornerRadius(radius);
    }

    private void updateBackgroundBlurRadius() {
        if (getViewRootImpl() == null) {
            return;
        }
        int i = (this.mCrossWindowBlurEnabled && this.mWindow.isTranslucent()) ? this.mOriginalBackgroundBlurRadius : 0;
        this.mBackgroundBlurRadius = i;
        if (this.mBackgroundBlurDrawable == null && i > 0) {
            this.mBackgroundBlurDrawable = getViewRootImpl().createBackgroundBlurDrawable();
            updateBackgroundDrawable();
        }
        BackgroundBlurDrawable backgroundBlurDrawable = this.mBackgroundBlurDrawable;
        if (backgroundBlurDrawable != null) {
            backgroundBlurDrawable.setBlurRadius(this.mBackgroundBlurRadius);
        }
    }

    void setBackgroundBlurRadius(int i) {
        Executor handlerExecutor;
        this.mOriginalBackgroundBlurRadius = i;
        if (i > 0) {
            if (this.mCrossWindowBlurEnabledListener == null) {
                this.mCrossWindowBlurEnabledListener = new Consumer() { // from class: com.android.internal.policy.DecorView$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$setBackgroundBlurRadius$2((Boolean) obj);
                    }
                };
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    handlerExecutor = getContext().getMainExecutor();
                } else {
                    handlerExecutor = new HandlerExecutor(new Handler(Looper.myLooper()));
                }
                ((WindowManager) getContext().getSystemService(WindowManager.class)).addCrossWindowBlurEnabledListener(handlerExecutor, this.mCrossWindowBlurEnabledListener);
                getViewTreeObserver().addOnPreDrawListener(this.mBackgroundBlurOnPreDrawListener);
                return;
            }
            updateBackgroundBlurRadius();
            return;
        }
        if (this.mCrossWindowBlurEnabledListener != null) {
            updateBackgroundBlurRadius();
            removeBackgroundBlurDrawable();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBackgroundBlurRadius$2(Boolean bool) {
        this.mCrossWindowBlurEnabled = bool.booleanValue();
        updateBackgroundBlurRadius();
    }

    void removeBackgroundBlurDrawable() {
        if (this.mCrossWindowBlurEnabledListener != null) {
            ((WindowManager) getContext().getSystemService(WindowManager.class)).removeCrossWindowBlurEnabledListener(this.mCrossWindowBlurEnabledListener);
            this.mCrossWindowBlurEnabledListener = null;
        }
        getViewTreeObserver().removeOnPreDrawListener(this.mBackgroundBlurOnPreDrawListener);
        this.mBackgroundBlurDrawable = null;
        updateBackgroundDrawable();
    }

    @Override // android.view.View
    public Drawable getBackground() {
        return this.mOriginalBackgroundDrawable;
    }

    private int calculateStatusBarColor(int i) {
        return calculateBarColor(this.mWindow.getAttributes().flags, 67108864, this.mSemiTransparentBarColor, this.mWindow.mStatusBarColor, i, 8, this.mWindow.mEnsureStatusBarContrastWhenTransparent && (this.mLastSuppressScrimTypes & WindowInsets.Type.statusBars()) == 0, false);
    }

    private int calculateNavigationBarColor(int i) {
        return calculateBarColor(this.mWindow.getAttributes().flags, 134217728, this.mSemiTransparentBarColor, this.mWindow.mNavigationBarColor, i, 16, this.mWindow.mEnsureNavigationBarContrastWhenTransparent && (this.mLastSuppressScrimTypes & WindowInsets.Type.navigationBars()) == 0, this.mWindow.mEdgeToEdgeEnforced, this.mWindow.getDeviceDefaultNavigationBarColor());
    }

    public static int calculateBarColor(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        return calculateBarColor(i, i2, i3, i4, i5, i6, z, z2, -16777216);
    }

    public static int calculateBarColor(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, int i7) {
        if ((i2 & i) != 0) {
            return i3;
        }
        if ((i & Integer.MIN_VALUE) == 0) {
            return i7;
        }
        if (z) {
            if (Color.alpha(i4) == 0) {
                return (i5 & i6) != 0 ? SCRIM_LIGHT : i3;
            }
            if (z2) {
                return (16777215 & i4) | SCRIM_ALPHA;
            }
        } else if (z2) {
            return 0;
        }
        return i4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.view.ViewParent] */
    private int indexOfChildToRoot(View view) {
        if (view == null) {
            return -1;
        }
        View parent = view.getParent();
        if (parent == this) {
            return indexOfChild(view);
        }
        while (parent != 0 && (parent instanceof View)) {
            View view2 = parent;
            parent = parent.getParent();
            if (parent == this) {
                return indexOfChild(view2);
            }
        }
        return -1;
    }

    private int getCurrentColor(ColorViewState colorViewState) {
        if (colorViewState.visible) {
            return colorViewState.color;
        }
        return 0;
    }

    private void updateColorViewInt(final ColorViewState colorViewState, int i, int i2, int i3, boolean z, boolean z2, int i4, boolean z3, boolean z4, int i5) {
        int i6;
        boolean z5;
        boolean z6;
        int i7 = i4;
        int i8 = colorViewState.attributes.insetsType;
        colorViewState.present = colorViewState.attributes.isPresent(((i5 & i8) == 0 && (this.mLastForceConsumingTypes & i8) == 0) ? false : true, this.mWindow.getAttributes().flags, z4);
        boolean zIsVisible = colorViewState.attributes.isVisible(colorViewState.present, i, this.mWindow.getAttributes().flags, z4);
        boolean z7 = zIsVisible && i3 > 0;
        if (i8 == WindowInsets.Type.navigationBars() && getResources().getConfiguration().windowConfiguration.isPopOver()) {
            z7 = false;
        }
        View view = colorViewState.view;
        int i9 = z ? -1 : i3;
        int i10 = z ? i3 : -1;
        if (z) {
            ColorViewAttributes colorViewAttributes = colorViewState.attributes;
            i6 = z2 ? colorViewAttributes.seascapeGravity : colorViewAttributes.horizontalGravity;
        } else {
            i6 = colorViewState.attributes.verticalGravity;
        }
        if (view != null) {
            int i11 = z7 ? 0 : 4;
            boolean z8 = colorViewState.targetVisibility != i11;
            colorViewState.targetVisibility = i11;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            int i12 = z2 ? 0 : i7;
            if (!z2) {
                i7 = 0;
            }
            z5 = z7;
            if (layoutParams.height != i9 || layoutParams.width != i10 || layoutParams.gravity != i6 || layoutParams.rightMargin != i12 || layoutParams.leftMargin != i7) {
                layoutParams.height = i9;
                layoutParams.width = i10;
                layoutParams.gravity = i6;
                layoutParams.rightMargin = i12;
                layoutParams.leftMargin = i7;
                view.setLayoutParams(layoutParams);
            }
            if (z5) {
                setColor(view, i, i2, z, z2);
            }
            z6 = z8;
        } else if (z7) {
            view = new View(this.mContext);
            colorViewState.view = view;
            setColor(view, i, i2, z, z2);
            view.setTransitionName(colorViewState.attributes.transitionName);
            view.setId(colorViewState.attributes.id);
            view.setVisibility(4);
            colorViewState.targetVisibility = 0;
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(i10, i9, i6);
            if (z2) {
                layoutParams2.leftMargin = i7;
            } else {
                layoutParams2.rightMargin = i7;
            }
            addView(view, layoutParams2);
            updateColorViewTranslations();
            z5 = z7;
            z6 = true;
        } else {
            z5 = z7;
            z6 = false;
        }
        if (z6) {
            view.animate().cancel();
            if (!z3) {
                view.setAlpha(1.0f);
                view.setVisibility(z5 ? 0 : 4);
            } else if (z5) {
                if (view.getVisibility() != 0) {
                    view.setVisibility(0);
                    view.setAlpha(0.0f);
                }
                view.animate().alpha(1.0f).setInterpolator(this.mShowInterpolator).setDuration(this.mBarEnterExitDuration);
            } else {
                final View view2 = colorViewState.view;
                view.animate().alpha(0.0f).setInterpolator(this.mHideInterpolator).setDuration(this.mBarEnterExitDuration).withEndAction(new Runnable(this) { // from class: com.android.internal.policy.DecorView.6
                    @Override // java.lang.Runnable
                    public void run() {
                        view2.setAlpha(1.0f);
                        view2.setVisibility(4);
                    }
                });
            }
        }
        colorViewState.visible = zIsVisible;
        colorViewState.color = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void setColor(View view, int i, int i2, boolean z, boolean z2) {
        if (i2 != 0) {
            Pair pair = (Pair) view.getTag();
            if (pair == null || ((Boolean) pair.first).booleanValue() != z || ((Boolean) pair.second).booleanValue() != z2) {
                int iRound = Math.round(TypedValue.applyDimension(1, 1.0f, view.getContext().getResources().getDisplayMetrics()));
                view.setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(i2), new InsetDrawable((Drawable) new ColorDrawable(i), (!z || z2) ? 0 : iRound, !z ? iRound : 0, (z && z2) ? iRound : 0, 0)}));
                view.setTag(new Pair(Boolean.valueOf(z), Boolean.valueOf(z2)));
                return;
            } else {
                LayerDrawable layerDrawable = (LayerDrawable) view.getBackground();
                ((ColorDrawable) ((InsetDrawable) layerDrawable.getDrawable(1)).getDrawable()).setColor(i);
                ((ColorDrawable) layerDrawable.getDrawable(0)).setColor(i2);
                return;
            }
        }
        view.setTag(null);
        view.setBackgroundColor(i);
    }

    private void updateColorViewTranslations() {
        int i = this.mRootScrollY;
        if (this.mStatusColorViewState.view != null) {
            this.mStatusColorViewState.view.setTranslationY(i > 0 ? i : 0.0f);
        }
        if (this.mNavigationColorViewState.view != null) {
            this.mNavigationColorViewState.view.setTranslationY(i < 0 ? i : 0.0f);
        }
    }

    private WindowInsets updateStatusGuard(WindowInsets windowInsets) {
        boolean z;
        boolean z2;
        ActionBarContextView actionBarContextView = this.mPrimaryActionModeView;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mPrimaryActionModeView.getLayoutParams();
            if (this.mPrimaryActionModeView.isShown()) {
                if (this.mTempRect == null) {
                    this.mTempRect = new Rect();
                }
                WindowInsets windowInsetsComputeSystemWindowInsets = this.mWindow.mContentParent.computeSystemWindowInsets(windowInsets, this.mTempRect);
                int systemWindowInsetTop = windowInsetsComputeSystemWindowInsets.getSystemWindowInsetTop();
                int systemWindowInsetLeft = windowInsetsComputeSystemWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = windowInsetsComputeSystemWindowInsets.getSystemWindowInsetRight();
                WindowInsets rootWindowInsets = getRootWindowInsets();
                int systemWindowInsetLeft2 = rootWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight2 = rootWindowInsets.getSystemWindowInsetRight();
                if (marginLayoutParams.topMargin == systemWindowInsetTop && marginLayoutParams.leftMargin == systemWindowInsetLeft && marginLayoutParams.rightMargin == systemWindowInsetRight) {
                    z2 = false;
                } else {
                    marginLayoutParams.topMargin = systemWindowInsetTop;
                    marginLayoutParams.leftMargin = systemWindowInsetLeft;
                    marginLayoutParams.rightMargin = systemWindowInsetRight;
                    z2 = true;
                }
                if (systemWindowInsetTop > 0 && this.mStatusGuard == null) {
                    View view = new View(this.mContext);
                    this.mStatusGuard = view;
                    view.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = systemWindowInsetLeft2;
                    layoutParams.rightMargin = systemWindowInsetRight2;
                    addView(this.mStatusGuard, indexOfChild(this.mStatusColorViewState.view), layoutParams);
                } else {
                    View view2 = this.mStatusGuard;
                    if (view2 != null) {
                        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view2.getLayoutParams();
                        if (layoutParams2.height != marginLayoutParams.topMargin || layoutParams2.leftMargin != systemWindowInsetLeft2 || layoutParams2.rightMargin != systemWindowInsetRight2) {
                            layoutParams2.height = marginLayoutParams.topMargin;
                            layoutParams2.leftMargin = systemWindowInsetLeft2;
                            layoutParams2.rightMargin = systemWindowInsetRight2;
                            this.mStatusGuard.setLayoutParams(layoutParams2);
                        }
                    }
                }
                View view3 = this.mStatusGuard;
                z = view3 != null;
                if (z && view3.getVisibility() != 0) {
                    updateStatusGuardColor();
                }
                if ((this.mWindow.getLocalFeaturesPrivate() & 1024) == 0 && z) {
                    windowInsets = windowInsets.inset(0, windowInsets.getSystemWindowInsetTop(), 0, 0);
                }
                boolean z3 = z;
                z = z2;
                z = z3;
            } else if (marginLayoutParams.topMargin == 0 && marginLayoutParams.leftMargin == 0 && marginLayoutParams.rightMargin == 0) {
                z = false;
                z = false;
            } else {
                marginLayoutParams.topMargin = 0;
                z = false;
            }
            if (z) {
                this.mPrimaryActionModeView.setLayoutParams(marginLayoutParams);
            }
        }
        View view4 = this.mStatusGuard;
        if (view4 != null) {
            view4.setVisibility(z ? 0 : 8);
        }
        return windowInsets;
    }

    private void updateStatusGuardColor() {
        int color;
        boolean z = (getWindowSystemUiVisibility() & 8192) != 0;
        View view = this.mStatusGuard;
        if (z) {
            color = this.mContext.getColor(R.color.decor_view_status_guard_light);
        } else {
            color = this.mContext.getColor(R.color.decor_view_status_guard);
        }
        view.setBackgroundColor(color);
    }

    public void updatePictureInPictureOutlineProvider(boolean z) {
        if (this.mIsInPictureInPictureMode == z) {
            return;
        }
        if (z) {
            Window.WindowControllerCallback windowControllerCallback = this.mWindow.getWindowControllerCallback();
            if (windowControllerCallback != null && windowControllerCallback.isTaskRoot()) {
                super.setOutlineProvider(PIP_OUTLINE_PROVIDER);
            }
        } else {
            ViewOutlineProvider outlineProvider = getOutlineProvider();
            ViewOutlineProvider viewOutlineProvider = this.mLastOutlineProvider;
            if (outlineProvider != viewOutlineProvider) {
                setOutlineProvider(viewOutlineProvider);
            }
        }
        this.mIsInPictureInPictureMode = z;
    }

    @Override // android.view.View
    public void setOutlineProvider(ViewOutlineProvider viewOutlineProvider) {
        super.setOutlineProvider(viewOutlineProvider);
        this.mLastOutlineProvider = viewOutlineProvider;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void drawableChanged() {
        if (this.mChanging) {
            return;
        }
        Rect rect = this.mFramePadding;
        if (rect == null) {
            rect = new Rect();
        }
        Rect rect2 = this.mBackgroundPadding;
        if (rect2 == null) {
            rect2 = new Rect();
        }
        setPadding(rect.left + rect2.left, rect.top + rect2.top, rect.right + rect2.right, rect.bottom + rect2.bottom);
        requestLayout();
        invalidate();
        WindowConfiguration windowConfiguration = getResources().getConfiguration().windowConfiguration;
        boolean z = this.mWindow.mRenderShadowsInCompositor;
        int opacity = -3;
        if ((!windowConfiguration.hasWindowShadow() || z) && !windowConfiguration.isPopOver()) {
            Drawable background = getBackground();
            Drawable foreground = getForeground();
            if (background == null) {
                opacity = -1;
            } else if (foreground == null) {
                opacity = background.getOpacity();
            } else if (rect.left <= 0 && rect.top <= 0 && rect.right <= 0 && rect.bottom <= 0) {
                int opacity2 = foreground.getOpacity();
                int opacity3 = background.getOpacity();
                if (opacity2 != -1 && opacity3 != -1) {
                    if (opacity2 == 0) {
                        opacity = opacity3;
                    } else {
                        if (opacity3 != 0) {
                            opacity2 = Drawable.resolveOpacity(opacity2, opacity3);
                        }
                        opacity = opacity2;
                    }
                }
            }
        }
        this.mDefaultOpacity = opacity;
        if (this.mFeatureId < 0) {
            this.mWindow.setDefaultWindowFormat(opacity);
        }
    }

    public boolean hasWindowFocusInTask() {
        return this.mHasWindowFocusInTask;
    }

    public void onWindowFocusInTaskChanged(boolean z) {
        if (this.mHasWindowFocusInTask != z) {
            this.mHasWindowFocusInTask = z;
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (this.mWindow.hasFeature(0) && !z && this.mWindow.mPanelChordingKey != 0) {
            this.mWindow.closePanel(0);
        }
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED && (((callback instanceof Activity) || (this.mWindow.getContext() instanceof Activity)) && !isActivityHomeOrRecent())) {
                if (!this.mStayFocus && z) {
                    callback.onWindowFocusChanged(true);
                    this.mStayFocus = true;
                } else {
                    ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
                    if (this.mStayFocus && activityThreadCurrentActivityThread != null && !activityThreadCurrentActivityThread.mayStayActivityFocus(this.mWindow.getAttributes().token)) {
                        callback.onWindowFocusChanged(false);
                        this.mStayFocus = false;
                    }
                }
            } else {
                this.mStayFocus = false;
                callback.onWindowFocusChanged(z);
            }
        }
        ActionMode actionMode = this.mPrimaryActionMode;
        if (actionMode != null) {
            actionMode.onWindowFocusChanged(z);
        }
        ActionMode actionMode2 = this.mFloatingActionMode;
        if (actionMode2 != null) {
            actionMode2.onWindowFocusChanged(z);
        }
        updateElevation();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mUserId = this.mContext.getUserId();
        Context context = this.mWindow.getContext();
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if ((context instanceof Activity) && attributes.isFullscreen()) {
            this.mIsKnoxActivity = true;
        }
        boolean z = false;
        this.mIsKeyboardShown = attributes.type == 2011;
        if (attributes.type == 2 && attributes.isFullscreen()) {
            z = true;
        }
        this.mIsFullViewShown = z;
        try {
            if ((this.mIsKeyboardShown || z || this.mIsKnoxActivity) && (SemPersonaManager.isKnoxId(this.mUserId) || SemDualAppManager.isDualAppId(this.mUserId))) {
                setKnoxBadge();
                setKnoxBadgePosition();
            }
        } catch (Exception unused) {
            Log.d(DEBUG_KNOX_TAG, "failed to set knox badge");
        }
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            callback.onAttachedToWindow();
        }
        if (this.mFeatureId == -1) {
            this.mWindow.openPanelsAfterRestore();
        }
        if (!this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().addWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = true;
        }
        updateBackgroundBlurRadius();
        this.mWindow.onViewRootImplSet(getViewRootImpl());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && this.mFeatureId < 0) {
            callback.onDetachedFromWindow();
        }
        if (this.mWindow.mDecorContentParent != null) {
            this.mWindow.mDecorContentParent.dismissPopups();
        }
        if (this.mPrimaryActionModePopup != null) {
            removeCallbacks(this.mShowPrimaryActionModePopup);
            if (this.mPrimaryActionModePopup.isShowing()) {
                this.mPrimaryActionModePopup.dismiss();
            }
            this.mPrimaryActionModePopup = null;
        }
        FloatingToolbar floatingToolbar = this.mFloatingToolbar;
        if (floatingToolbar != null) {
            floatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        removeBackgroundBlurDrawable();
        PhoneWindow.PanelFeatureState panelState = this.mWindow.getPanelState(0, false);
        if (panelState != null && panelState.menu != null && this.mFeatureId < 0) {
            panelState.menu.close();
        }
        if (this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().removeWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = false;
        }
        this.mPendingInsetsController.detach();
        if (this.mKnoxBadgeViewGroupOverlay != null) {
            removeCallbacks(this.mKnoxBadgeDisplayRunnable);
            removeKnoxBadge();
        }
        int i = this.mLastBackgroundResource;
        if (i == 17304910 || i == 17304907 || i == 17304908) {
            setWindowBackground(null);
        }
    }

    @Override // android.view.View
    public void onCloseSystemDialogs(String str) {
        if (this.mFeatureId >= 0) {
            this.mWindow.closeAllPanels();
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public SurfaceHolder.Callback2 willYouTakeTheSurface() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeSurfaceCallback;
        }
        return null;
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public InputQueue.Callback willYouTakeTheInputQueue() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeInputQueueCallback;
        }
        return null;
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceType(int i) {
        this.mWindow.setType(i);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceFormat(int i) {
        this.mWindow.setFormat(i);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceKeepScreenOn(boolean z) {
        if (z) {
            this.mWindow.addFlags(128);
        } else {
            this.mWindow.clearFlags(128);
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void onRootViewScrollYChanged(int i) {
        this.mRootScrollY = i;
        updateColorViewTranslations();
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public PendingInsetsController providePendingInsetsController() {
        return this.mPendingInsetsController;
    }

    private ActionMode createActionMode(int i, ActionMode.Callback2 callback2, View view) {
        if (i != 1 && i != 99) {
            return createStandaloneActionMode(callback2);
        }
        ActionMode actionModeCreateFloatingActionMode = createFloatingActionMode(view, callback2);
        actionModeCreateFloatingActionMode.setType(i);
        return actionModeCreateFloatingActionMode;
    }

    private void setHandledActionMode(ActionMode actionMode) {
        if (actionMode.getType() == 0) {
            setHandledPrimaryActionMode(actionMode);
        } else if (actionMode.getType() == 1 || actionMode.getType() == 99) {
            setHandledFloatingActionMode(actionMode);
        }
    }

    private ActionMode createStandaloneActionMode(ActionMode.Callback callback) {
        Context contextThemeWrapper;
        endOnGoingFadeAnimation();
        cleanupPrimaryActionMode();
        ActionBarContextView actionBarContextView = this.mPrimaryActionModeView;
        if (actionBarContextView == null || !actionBarContextView.isAttachedToWindow()) {
            if (this.mWindow.isFloating()) {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = this.mContext.getTheme();
                theme.resolveAttribute(16843825, typedValue, true);
                if (typedValue.resourceId != 0) {
                    Resources.Theme themeNewTheme = this.mContext.getResources().newTheme();
                    themeNewTheme.setTo(theme);
                    themeNewTheme.applyStyle(typedValue.resourceId, true);
                    contextThemeWrapper = new ContextThemeWrapper(this.mContext, 0);
                    contextThemeWrapper.getTheme().setTo(themeNewTheme);
                } else {
                    contextThemeWrapper = this.mContext;
                }
                this.mPrimaryActionModeView = new ActionBarContextView(contextThemeWrapper);
                PopupWindow popupWindow = new PopupWindow(contextThemeWrapper, (AttributeSet) null, R.attr.actionModePopupWindowStyle);
                this.mPrimaryActionModePopup = popupWindow;
                popupWindow.setWindowLayoutType(2);
                this.mPrimaryActionModePopup.setContentView(this.mPrimaryActionModeView);
                this.mPrimaryActionModePopup.setWidth(-1);
                contextThemeWrapper.getTheme().resolveAttribute(16843499, typedValue, true);
                this.mPrimaryActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, contextThemeWrapper.getResources().getDisplayMetrics()));
                this.mPrimaryActionModePopup.setHeight(-2);
                this.mShowPrimaryActionModePopup = new Runnable() { // from class: com.android.internal.policy.DecorView.7
                    @Override // java.lang.Runnable
                    public void run() {
                        DecorView.this.mPrimaryActionModePopup.showAtLocation(DecorView.this.mPrimaryActionModeView.getApplicationWindowToken(), 55, 0, 0);
                        DecorView.this.endOnGoingFadeAnimation();
                        if (DecorView.this.shouldAnimatePrimaryActionModeView()) {
                            DecorView decorView = DecorView.this;
                            decorView.mFadeAnim = ObjectAnimator.ofFloat(decorView.mPrimaryActionModeView, (Property<ActionBarContextView, Float>) View.ALPHA, 0.0f, 1.0f);
                            DecorView.this.mFadeAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.7.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationStart(Animator animator) {
                                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator) {
                                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                                    DecorView.this.mFadeAnim = null;
                                }
                            });
                            DecorView.this.mFadeAnim.start();
                            return;
                        }
                        DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                        DecorView.this.mPrimaryActionModeView.setVisibility(0);
                    }
                };
            } else {
                ViewStub viewStub = (ViewStub) findViewById(R.id.action_mode_bar_stub);
                if (viewStub != null) {
                    this.mPrimaryActionModeView = (ActionBarContextView) viewStub.inflate();
                    this.mPrimaryActionModePopup = null;
                }
            }
        }
        ActionBarContextView actionBarContextView2 = this.mPrimaryActionModeView;
        if (actionBarContextView2 == null) {
            return null;
        }
        actionBarContextView2.killMode();
        return new StandaloneActionMode(this.mPrimaryActionModeView.getContext(), this.mPrimaryActionModeView, callback, this.mPrimaryActionModePopup == null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endOnGoingFadeAnimation() {
        ObjectAnimator objectAnimator = this.mFadeAnim;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
    }

    private void setHandledPrimaryActionMode(ActionMode actionMode) {
        endOnGoingFadeAnimation();
        this.mPrimaryActionMode = actionMode;
        actionMode.invalidate();
        this.mPrimaryActionModeView.initForMode(this.mPrimaryActionMode);
        if (this.mPrimaryActionModePopup != null) {
            post(this.mShowPrimaryActionModePopup);
        } else if (shouldAnimatePrimaryActionModeView()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mPrimaryActionModeView, (Property<ActionBarContextView, Float>) View.ALPHA, 0.0f, 1.0f);
            this.mFadeAnim = objectAnimatorOfFloat;
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.8
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                    DecorView.this.mFadeAnim = null;
                }
            });
            this.mFadeAnim.start();
        } else {
            this.mPrimaryActionModeView.setAlpha(1.0f);
            this.mPrimaryActionModeView.setVisibility(0);
        }
        this.mPrimaryActionModeView.sendAccessibilityEvent(32);
    }

    boolean shouldAnimatePrimaryActionModeView() {
        return isLaidOut();
    }

    private ActionMode createFloatingActionMode(View view, ActionMode.Callback2 callback2) {
        ActionMode actionMode = this.mFloatingActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mWindow);
        final FloatingActionMode floatingActionMode = new FloatingActionMode(this.mContext, callback2, view, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = view;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.android.internal.policy.DecorView.9
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                floatingActionMode.updateViewLocationInWindow();
                return true;
            }
        };
        return floatingActionMode;
    }

    private void setHandledFloatingActionMode(ActionMode actionMode) {
        this.mFloatingActionMode = actionMode;
        View view = this.mFloatingActionModeOriginatingView;
        FloatingToolbar floatingToolbar = new FloatingToolbar(this.mWindow, actionMode.getType() == 99 || (view instanceof TextView ? ((TextView) view).isThemeDeviceDefault() : false));
        this.mFloatingToolbar = floatingToolbar;
        ((FloatingActionMode) this.mFloatingActionMode).setFloatingToolbar(floatingToolbar);
        this.mFloatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
    }

    void setWindow(PhoneWindow phoneWindow) {
        this.mWindow = phoneWindow;
        Context context = getContext();
        if (context instanceof DecorContext) {
            ((DecorContext) context).setPhoneWindow(this.mWindow);
        }
        Drawable drawable = this.mPendingWindowBackground;
        if (drawable != null) {
            this.mPendingWindowBackground = null;
            setWindowBackground(drawable);
        }
    }

    @Override // android.view.View
    public Resources getResources() {
        return getContext().getResources();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        StateListDrawable stateListDrawable;
        int[] state;
        StateListDrawable stateListDrawable2;
        int[] state2;
        Drawable drawable;
        super.onConfigurationChanged(configuration);
        int i = this.mLastDisplayDeviceType;
        int i2 = configuration.semDisplayDeviceType;
        this.mLastDisplayDeviceType = i2;
        boolean z = i2 != i;
        WindowConfiguration windowConfiguration = configuration.windowConfiguration;
        boolean zIsPopOver = windowConfiguration.isPopOver();
        if (this.mIsPopOver != zIsPopOver) {
            this.mIsPopOver = zIsPopOver;
            if (!zIsPopOver) {
                removePopOverElevation();
            }
            z = true;
        }
        boolean zIsPopOverWithoutOutlineEffect = windowConfiguration.isPopOverWithoutOutlineEffect();
        if (this.mIsPopOverWithoutOutlineEffect != zIsPopOverWithoutOutlineEffect) {
            this.mIsPopOverWithoutOutlineEffect = zIsPopOverWithoutOutlineEffect;
            z = true;
        }
        if (z) {
            drawableChanged();
        }
        Drawable drawable2 = null;
        if (this.mIsPopOver && !this.mIsPopOverWithoutOutlineEffect) {
            Resources resources = getResources();
            if (this.mWindow.getContext() instanceof ChooserActivity) {
                this.mPopOverBackgroundColor = resources.getColor(R.color.sem_resolver_bg_color);
            } else if (this.mPopOverBackgroundColor == -1) {
                this.mPopOverBackgroundColor = resources.getColor(SemViewUtils.isLightTheme(this.mContext) ? R.color.sem_app_bar_bg_color : R.color.sem_app_bar_bg_color_dark, null);
            }
            Log.i(TAG, "mPopOverBackgroundColor=" + Integer.toHexString(this.mPopOverBackgroundColor));
            if (getViewRootImpl() != null) {
                getViewRootImpl().requestInvalidateRootRenderNode();
            }
        }
        updateOutlineProvider();
        initializeElevation();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(16843607, this.mWindow.mMinWidthMinor, true);
        theme.resolveAttribute(16843606, this.mWindow.mMinWidthMajor, true);
        this.mLastSmallestScreenWidthDp = configuration.smallestScreenWidthDp;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.getOnBackInvokedDispatcher().onConfigurationChanged(configuration);
        }
        refreshGestureNavBarSettings();
        try {
            if ((this.mIsKeyboardShown || this.mIsFullViewShown || this.mIsKnoxActivity) && ((SemPersonaManager.isKnoxId(this.mUserId) || SemDualAppManager.isDualAppId(this.mUserId)) && configuration.densityDpi != this.mDensityForKnoxBadge)) {
                setBadgeResource();
                this.mDensityForKnoxBadge = configuration.densityDpi;
            }
        } catch (Exception unused) {
            Log.d(this.mLogTag, "failed to remove knox badge");
        }
        this.mWindow.updateDeviceDefaultNavigationBarColor();
        this.mLegacyNavigationBarBackgroundPaint.setColor(this.mWindow.getDeviceDefaultNavigationBarColor());
        this.mWindow.updateDefaultNavigationBarColor();
        int i3 = this.mLastBackgroundResource;
        if (i3 == 17304910) {
            Drawable background = getBackground();
            if (!(background instanceof StateListDrawable) || (state2 = (stateListDrawable2 = (StateListDrawable) background).getState()) == null || state2.length <= 0 || !(stateListDrawable2.getStateDrawable(0) instanceof BitmapDrawable) || (drawable = getContext().getDrawable(this.mLastBackgroundResource)) == null) {
                return;
            }
            setWindowBackground(drawable);
            return;
        }
        if (i3 == 17304907 || i3 == 17304908) {
            Drawable background2 = getBackground();
            if (background2 instanceof BitmapDrawable) {
                drawable2 = getContext().getResources().getDrawable(this.mLastBackgroundResource);
            } else if ((background2 instanceof StateListDrawable) && (state = (stateListDrawable = (StateListDrawable) background2).getState()) != null && state.length > 0 && (stateListDrawable.getStateDrawable(0) instanceof BitmapDrawable)) {
                drawable2 = getContext().getResources().getDrawable(this.mLastBackgroundResource);
            }
            if (drawable2 != null) {
                setWindowBackground(drawable2);
            }
        }
    }

    @Override // android.view.View
    public void onMovedToDisplay(int i, Configuration configuration) {
        super.onMovedToDisplay(i, configuration);
        getContext().updateDisplay(i);
    }

    private boolean isFillingScreen(Configuration configuration) {
        if (configuration.windowConfiguration.getWindowingMode() == 1) {
            return ((getSystemUiVisibility() | getWindowSystemUiVisibility()) & 4) != 0;
        }
        return false;
    }

    void onResourcesLoaded(LayoutInflater layoutInflater, int i) {
        this.mLastOutlineProvider = getOutlineProvider();
        updateOutlineProvider();
        View viewInflate = layoutInflater.inflate(i, (ViewGroup) null);
        addView(viewInflate, 0, new ViewGroup.LayoutParams(-1, -1));
        this.mContentRoot = (ViewGroup) viewInflate;
        initializeElevation();
        this.mLastSmallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
    }

    void clearContentView() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt != this.mStatusColorViewState.view && childAt != this.mNavigationColorViewState.view && childAt != this.mStatusGuard) {
                removeViewAt(childCount);
            }
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeStart(Rect rect, boolean z, Rect rect2, Rect rect3) {
        if (this.mWindow.isDestroyed()) {
            return;
        }
        getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeEnd() {
        updateColorViews(null, false);
        getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override // android.view.WindowCallbacks
    public void onRequestDraw(boolean z) {
        if (z && isAttachedToWindow()) {
            getViewRootImpl().reportDrawFinish();
        }
    }

    @Override // android.view.WindowCallbacks
    public void onPostDraw(RecordingCanvas recordingCanvas) {
        drawLegacyNavigationBarBackground(recordingCanvas);
        if ((isActivity() || isFullSize()) && this.mIsPopOver) {
            PhoneWindow phoneWindow = this.mWindow;
            if (phoneWindow == null || !phoneWindow.isFloating()) {
                this.mPopOverFramePaint.setStyle(Paint.Style.STROKE);
                this.mPopOverFramePaint.setStrokeJoin(Paint.Join.ROUND);
                this.mPopOverFramePaint.setStrokeCap(Paint.Cap.ROUND);
                this.mPopOverFramePaint.setAntiAlias(true);
                boolean z = (getResources().getConfiguration().uiMode & 48) == 32;
                float f = z ? 2.0f : 1.0f;
                this.mPopOverFramePaint.setColor(z ? FRAME_COLOR_POPOVER_DARK : -3355444);
                this.mPopOverFramePaint.setStrokeWidth(f);
                recordingCanvas.drawPath(SemViewUtils.getSmoothCornerRectPath(dpToPixel(POP_OVER_CORNER_RADIUS), 0.0f, 0.0f, getWidth(), getHeight()), this.mPopOverFramePaint);
            }
        }
    }

    private void drawLegacyNavigationBarBackground(RecordingCanvas recordingCanvas) {
        View view;
        boolean z = this.mLastDrawLegacyNavigationBarBackground;
        boolean z2 = this.mDrawLegacyNavigationBarBackground;
        if (z != z2) {
            this.mLastDrawLegacyNavigationBarBackground = z2;
            this.mWindow.updateForceLightNavigationBar();
        }
        if (!this.mDrawLegacyNavigationBarBackground || this.mDrawLegacyNavigationBarBackgroundHandled || (view = this.mNavigationColorViewState.view) == null) {
            return;
        }
        recordingCanvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), this.mLegacyNavigationBarBackgroundPaint);
    }

    private void initializeElevation() {
        this.mAllowUpdateElevation = false;
        updateElevation();
    }

    public boolean isNonFullscreenWindowInFreeform() {
        return isFreeformMode() && isActivity() && !isFullSize() && this.mWindow.getAttributes().type >= 1 && this.mWindow.getAttributes().type <= 99;
    }

    private void updateElevation() {
        int windowingMode = getResources().getConfiguration().windowConfiguration.getWindowingMode();
        boolean z = this.mWindow.mRenderShadowsInCompositor;
        boolean zIsPopOverState = isPopOverState();
        if (!z || zIsPopOverState) {
            boolean z2 = this.mElevationAdjustedForStack;
            float fDipToPx = 0.0f;
            if (this.mIsPopOver && (this.mIsPopOverWithoutOutlineEffect || this.mPreventPopOverElevation)) {
                this.mElevationAdjustedForStack = true;
            } else if (isPopOverState()) {
                fDipToPx = dipToPx(32.0f);
                this.mElevationAdjustedForStack = true;
            } else if (windowingMode == 5) {
                fDipToPx = dipToPx(this.mAllowUpdateElevation ? hasWindowFocus() ? 20.0f : 5.0f : 20.0f);
                this.mElevationAdjustedForStack = true;
            } else {
                this.mElevationAdjustedForStack = false;
            }
            if ((z2 || this.mElevationAdjustedForStack) && getElevation() != fDipToPx) {
                this.mWindow.setElevation(fDipToPx);
            }
        }
    }

    private float dipToPx(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    private static String getTitleSuffix(WindowManager.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return "";
        }
        String[] strArrSplit = layoutParams.getTitle().toString().split("\\.");
        if (strArrSplit.length <= 0) {
            return "";
        }
        return strArrSplit[strArrSplit.length - 1];
    }

    void updateLogTag(WindowManager.LayoutParams layoutParams) {
        this.mLogTag = "DecorView[" + getTitleSuffix(layoutParams) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.view.View
    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int i) {
        PhoneWindow.PanelFeatureState panelState = this.mWindow.getPanelState(0, false);
        MenuBuilder menuBuilder = panelState != null ? panelState.menu : null;
        if (this.mWindow.isDestroyed() || this.mWindow.getCallback() == null) {
            return;
        }
        this.mWindow.getCallback().onProvideKeyboardShortcuts(list, menuBuilder, i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchPointerCaptureChanged(boolean z) {
        super.dispatchPointerCaptureChanged(z);
        if (this.mWindow.isDestroyed() || this.mWindow.getCallback() == null) {
            return;
        }
        this.mWindow.getCallback().onPointerCaptureChanged(z);
    }

    @Override // android.view.View
    public WindowInsetsController getWindowInsetsController() {
        if (isAttachedToWindow()) {
            return super.getWindowInsetsController();
        }
        return this.mPendingInsetsController;
    }

    public void setAppJankStatsCallback(AppJankStatsCallback appJankStatsCallback) {
        this.mAppJankStatsCallback = appJankStatsCallback;
    }

    @Override // android.view.View
    public void reportAppJankStats(AppJankStats appJankStats) {
        JankTracker appJankTracker;
        AppJankStatsCallback appJankStatsCallback = this.mAppJankStatsCallback;
        if (appJankStatsCallback == null || (appJankTracker = appJankStatsCallback.getAppJankTracker()) == null) {
            return;
        }
        appJankTracker.mergeAppJankStats(appJankStats);
    }

    @Override // android.view.View
    public JankTracker getJankTracker() {
        AppJankStatsCallback appJankStatsCallback = this.mAppJankStatsCallback;
        if (appJankStatsCallback != null) {
            return appJankStatsCallback.getAppJankTracker();
        }
        return null;
    }

    @Override // android.view.View
    public String toString() {
        return super.toString() + NavigationBarInflaterView.SIZE_MOD_START + getTitleSuffix(this.mWindow.getAttributes()) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private boolean isActivity() {
        PhoneWindow phoneWindow = this.mWindow;
        return (phoneWindow == null || phoneWindow.getWindowControllerCallback() == null) ? false : true;
    }

    private final Configuration getConfiguration() {
        PhoneWindow phoneWindow = this.mWindow;
        if (phoneWindow != null && phoneWindow.mActivityCurrentConfig != null) {
            return this.mWindow.mActivityCurrentConfig;
        }
        return getResources().getConfiguration();
    }

    public int getWindowingMode() {
        int i = this.mWindowingMode;
        return i != 0 ? i : getConfiguration().windowConfiguration.getWindowingMode();
    }

    private int getStagePosition() {
        return getConfiguration().windowConfiguration.getStagePosition();
    }

    public boolean isFullscreenMode() {
        return getWindowingMode() == 1;
    }

    public boolean isFreeformMode() {
        return getWindowingMode() == 5;
    }

    public boolean isSplitMode() {
        return WindowConfiguration.isSplitScreenWindowingMode(getConfiguration().windowConfiguration);
    }

    public boolean isFullSize() {
        PhoneWindow phoneWindow = this.mWindow;
        return phoneWindow != null && phoneWindow.getAttributes().isFullscreen();
    }

    private boolean isImmersiveMode() {
        int windowSystemUiVisibility = getWindowSystemUiVisibility() | getSystemUiVisibility();
        return ((windowSystemUiVisibility & GLES30.GL_COLOR) == 0 || (windowSystemUiVisibility & 2) == 0) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateRoundedCornerStateIfNeeded() {
        boolean zShouldDrawRoundedCornerInPortraitMode;
        int i;
        if (this.mWindow.mActivityCurrentConfig == null) {
            return;
        }
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        boolean zIsFullscreen = attributes.isFullscreen();
        Configuration configuration = getConfiguration();
        this.mRotationForRoundedCorner = configuration.windowConfiguration.getRotation();
        this.mDisplayRotationForRoundedCorner = configuration.windowConfiguration.getDisplayRotation();
        if (configuration.windowConfiguration.isPopOver()) {
            zShouldDrawRoundedCornerInPortraitMode = false;
        } else {
            zShouldDrawRoundedCornerInPortraitMode = true;
            if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APPS_CUTOUT && this.mContext.getDisplayId() == 1 && this.mHasDisplayCutout) {
                ViewRootImpl viewRootImpl = getViewRootImpl();
                int i2 = viewRootImpl != null ? viewRootImpl.mRequestedLetterboxDirection : 0;
                int i3 = (i2 & 4) != 0 ? 3 : 0;
                if ((i2 & 8) != 0) {
                    i3 |= 12;
                }
                if (i3 != 0) {
                    super.semSetRoundedCorners(i3, this.mRoundedCornerRadiusForAppsCoverLauncherLetterBox);
                    super.semSetRoundedCornerColor(i3, -16777216);
                }
            } else if (isFullscreenMode() && configuration.orientation == 2 && this.mHasDisplayCutout && zIsFullscreen) {
                ViewRootImpl viewRootImpl2 = getViewRootImpl();
                int i4 = viewRootImpl2 != null ? viewRootImpl2.mRequestedLetterboxDirection : 0;
                int roundedCornersInLandscapeMode = getRoundedCornersInLandscapeMode(attributes.layoutInDisplayCutoutMode, i4);
                if (roundedCornersInLandscapeMode != 0) {
                    if ((i4 & 1) != 0) {
                        i = this.mDeviceRoundedCornerTopRadius;
                    } else if ((i4 & 2) != 0) {
                        i = this.mDeviceRoundedCornerBottomRadius;
                    } else {
                        i = this.mRoundedCornerRadiusForLetterBox;
                    }
                    super.semSetRoundedCorners(roundedCornersInLandscapeMode, i);
                    super.semSetRoundedCornerColor(roundedCornersInLandscapeMode, -16777216);
                }
            } else if (isFullscreenMode() && configuration.orientation == 1 && this.mIsShowNavigationBar && attributes.type != 2037) {
                zShouldDrawRoundedCornerInPortraitMode = shouldDrawRoundedCornerInPortraitMode(zIsFullscreen);
                if (zShouldDrawRoundedCornerInPortraitMode) {
                    super.semSetRoundedCorners(12, this.mRoundedCornerRadius);
                    super.semSetRoundedCornerColor(12, getCurrentColor(this.mNavigationColorViewState));
                }
            } else if (isSplitMode() && zIsFullscreen) {
                Rect currentBounds = getCurrentBounds(this.mContext);
                if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER && !this.mWindow.isFloating()) {
                    updateRoundedCornerForMultiSplit(this.mContext);
                } else if (!this.mWindow.mIsFloating || (currentBounds.width() <= getWidth() && currentBounds.height() <= getHeight())) {
                    updateRoundedCornerForSplit(this.mContext);
                }
                this.mForceRoundedCorner = false;
            }
        }
        if (zShouldDrawRoundedCornerInPortraitMode) {
            boolean zIsSplitMode = isSplitMode();
            int i5 = this.mRotationForRoundedCorner;
            if ((i5 == 0 || i5 == 2) && !zIsSplitMode) {
                this.mOverrideRoundedCornerBounds.set(this.mLastLeftInset, this.mLastTopInset, getWidth() - this.mLastRightInset, getHeight() - this.mLastBottomInset);
                return;
            } else {
                this.mOverrideRoundedCornerBounds.set(this.mLastLeftInset, 0, getWidth() - this.mLastRightInset, getHeight());
                return;
            }
        }
        super.semSetRoundedCorners(0);
        this.mOverrideRoundedCornerBounds.setEmpty();
    }

    private int getFlipCoverScreenRoundedCorner(WindowManager.LayoutParams layoutParams, int i, int i2) {
        if ((layoutParams.flags & 1048576) != 0) {
            return (i == 0 && layoutParams.layoutInDisplayCutoutMode == 3 && i2 == 2) ? 3 : 0;
        }
        int i3 = (i & 4) == 0 ? 0 : 3;
        return (i & 8) != 0 ? i3 | 12 : i3;
    }

    private boolean shouldDrawRoundedCornerInPortraitMode(boolean z) {
        if (this.mGestureNavBarEnabled && !this.mGestureHintEnabled) {
            return false;
        }
        if ((z && (this.mWindow.getContext() instanceof Activity)) || this.mForceHideRoundedCorner) {
            return false;
        }
        if (this.mForceRoundedCorner) {
            return this.mNavigationColorViewState.visible && !(Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.SEM_TASK_BAR, 0) == 1) && (this.mRoundedCornerMode & 12) == 12;
        }
        if (View.sIsSamsungBasicInteraction && this.mNavigationColorViewState.visible) {
            return this.mContentRoot == null || this.mStatusColorViewState.view == null || this.mNavigationColorViewState.view == null || 3 >= getChildCount() || !isChildIntersectsWith(this.mNavigationColorViewState.view);
        }
        return false;
    }

    @Override // android.view.View
    public void semSetRoundedCorners(int i) {
        this.mForceRoundedCorner = true;
        this.mRoundedCornerMode = i;
        super.semSetRoundedCorners(i);
    }

    @Override // android.view.View
    public boolean setOverrideRoundedCornerBounds(Rect rect) {
        if (rect == null || this.mOverrideRoundedCornerBounds.isEmpty() || this.mRotationForRoundedCorner != this.mDisplayRotationForRoundedCorner) {
            return false;
        }
        rect.set(this.mOverrideRoundedCornerBounds);
        return true;
    }

    private boolean isChildIntersectsWith(View view) {
        this.mTmpColorViewBounds.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (this.mContentRoot != childAt && this.mNavigationColorViewState.view != childAt && this.mTmpColorViewBounds.intersects(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom())) {
                return true;
            }
        }
        return false;
    }

    private void refreshGestureNavBarSettings() {
        this.mGestureNavBarEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) != 0;
        this.mGestureHintEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.NAVIGATIONBAR_GESTURE_HINT, 1) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateRoundedCornerForSplit(Context context) {
        Configuration configuration = getConfiguration();
        int rotation = configuration.windowConfiguration.getRotation();
        int roundedCornerColor = MultiWindowUtils.getRoundedCornerColor(context);
        int stagePosition = getStagePosition();
        int i = 10;
        int i2 = 3;
        if (CoreRune.MW_EMBED_ACTIVITY && configuration.windowConfiguration.isEmbedded()) {
            int embedActivityMode = configuration.windowConfiguration.getEmbedActivityMode();
            if (embedActivityMode == 2) {
                i = 5;
            } else if (embedActivityMode != 3) {
            }
            i2 = i;
        } else if (stagePosition == 16) {
            i2 = 12;
        } else if (stagePosition != 64) {
            if (stagePosition == 8) {
                if (rotation == 1) {
                    i = 15;
                }
                i2 = i;
            } else {
                if (stagePosition != 32) {
                    Log.e(TAG, "updateRoundedCornerForSplit: Invalid position 0x" + stagePosition);
                    return;
                }
                i2 = rotation != 1 ? 15 : 5;
            }
        }
        super.semSetRoundedCorners(i2, this.mMultiWindowRoundedCornerRadius);
        super.semSetRoundedCornerColor(i2, roundedCornerColor);
    }

    private Rect getCurrentBounds(Context context) {
        if (this.mWm == null) {
            this.mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return this.mWm.getCurrentWindowMetrics().getBounds();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateRoundedCornerForMultiSplit(Context context) {
        int i;
        if (CoreRune.MW_EMBED_ACTIVITY) {
            Configuration configuration = getConfiguration();
            if (configuration.windowConfiguration.isEmbedded()) {
                int embedActivityMode = configuration.windowConfiguration.getEmbedActivityMode();
                if (embedActivityMode == 2) {
                    i = 5;
                } else if (embedActivityMode == 3) {
                    i = 10;
                }
            } else {
                i = 15;
            }
        }
        super.semSetRoundedCorners(i, this.mMultiWindowRoundedCornerRadius);
        super.semSetRoundedCornerColor(i, MultiWindowUtils.getRoundedCornerColor(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float dpToPixel(float f) {
        return f * (this.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }

    public void updateElevationIfNeeded() {
        if (this.mIsPopOver) {
            updateElevation();
        }
    }

    public void removePopOverElevation() {
        setElevation(0.0f);
    }

    private boolean isPopOverState() {
        return this.mIsPopOver && !this.mPreventPopOverElevation && isActivity() && isFullSize();
    }

    public void preventPopOverElevation() {
        this.mPreventPopOverElevation = true;
        setElevation(0.0f);
    }

    public boolean isDialogInPopOver() {
        if (!this.mPreventPopOverElevation) {
            this.mPreventPopOverElevation = getResources().getConfiguration().windowConfiguration.isPopOver() && (!isActivity() || (!isFullSize() && isDimBehind()));
        }
        return this.mPreventPopOverElevation;
    }

    private boolean isDimBehind() {
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        return (attributes.flags & 2) != 0 && attributes.dimAmount > 0.0f && attributes.dimAmount < 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBackgroundAlpha(float f) {
        if (this.mPopOverBackgroundAlpha != f) {
            this.mPopOverBackgroundAlpha = f;
            Log.d(TAG, "changed bg alpha=" + f);
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getBackgroundAlpha() {
        return this.mPopOverBackgroundAlpha;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentAlpha(float f) {
        if (this.mPopOverContentAlpha != f) {
            this.mPopOverContentAlpha = f;
            Log.d(TAG, "changed content alpha=" + f);
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getContentAlpha() {
        return this.mPopOverContentAlpha;
    }

    private void showPopOver() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, this.POP_OVER_BACKGROUND_ALPHA, 1.0f);
        objectAnimatorOfFloat.setDuration(200L);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, this.POP_OVER_CONTENT_ALPHA, 1.0f);
        objectAnimatorOfFloat2.setDuration(100L);
        objectAnimatorOfFloat2.setStartDelay(100L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.start();
    }

    private void hidePopOver() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, this.POP_OVER_BACKGROUND_ALPHA, 0.2f);
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.setStartDelay(100L);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, this.POP_OVER_CONTENT_ALPHA, 0.0f);
        objectAnimatorOfFloat2.setDuration(100L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
    }

    private void updateOutlineProvider() {
        if (isActivity()) {
            if (this.mIsPopOver && (!this.mIsPopOverWithoutOutlineEffect || !isDialogInPopOver())) {
                setOutlineProvider(this.POP_OVER_OUTLINE_PROVIDER);
                return;
            }
            if (!this.mIsPopOver && getOutlineProvider() == this.POP_OVER_OUTLINE_PROVIDER) {
                setOutlineProvider(null);
                requestInvalidateRenderNode("updateOutlineProvider");
                return;
            }
            ViewOutlineProvider outlineProvider = getOutlineProvider();
            ViewOutlineProvider viewOutlineProvider = this.mLastOutlineProvider;
            if (outlineProvider != viewOutlineProvider) {
                setOutlineProvider(viewOutlineProvider);
                return;
            }
            return;
        }
        if (!this.mIsPopOver || getContext().getActivityToken() == null) {
            return;
        }
        setOutlineProvider(this.POP_OVER_OUTLINE_PROVIDER);
    }

    private void requestInvalidateRenderNode(String str) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestInvalidateRootRenderNode();
        }
    }

    private static class ColorViewState {
        final ColorViewAttributes attributes;
        int color;
        boolean visible;
        View view = null;
        int targetVisibility = 4;
        boolean present = false;

        ColorViewState(ColorViewAttributes colorViewAttributes) {
            this.attributes = colorViewAttributes;
        }
    }

    public static class ColorViewAttributes {
        final int horizontalGravity;
        final int id;
        final int insetsType;
        final int seascapeGravity;
        final String transitionName;
        final int translucentFlag;
        final int verticalGravity;

        public boolean isPresent(boolean z, int i, boolean z2) {
            if (z) {
                return (Integer.MIN_VALUE & i) != 0 || z2;
            }
            return false;
        }

        private ColorViewAttributes(int i, int i2, int i3, int i4, String str, int i5, int i6) {
            this.id = i5;
            this.translucentFlag = i;
            this.verticalGravity = i2;
            this.horizontalGravity = i3;
            this.seascapeGravity = i4;
            this.transitionName = str;
            this.insetsType = i6;
        }

        public boolean isVisible(boolean z, int i, int i2, boolean z2) {
            if (!z || Color.alpha(i) == 0) {
                return false;
            }
            return (this.translucentFlag & i2) == 0 || z2;
        }

        public boolean isVisible(int i, int i2, int i3, boolean z) {
            return isVisible(isPresent((i & this.insetsType) != 0, i3, z), i2, i3, z);
        }
    }

    private class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            DecorView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (actionMode == DecorView.this.mFloatingActionMode && DecorView.this.mFloatingToolbar.isDiscardTouch()) {
                return true;
            }
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            boolean z;
            boolean z2;
            this.mWrapped.onDestroyActionMode(actionMode);
            if (DecorView.this.mContext.getApplicationInfo().targetSdkVersion >= 23) {
                z = actionMode == DecorView.this.mPrimaryActionMode;
                z2 = actionMode == DecorView.this.mFloatingActionMode;
                if (!z && actionMode.getType() == 0) {
                    Log.e(DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_PRIMARY; " + actionMode + " was not the current primary action mode! Expected " + DecorView.this.mPrimaryActionMode);
                }
                if (!z2 && actionMode.getType() == 1) {
                    Log.e(DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_FLOATING; " + actionMode + " was not the current floating action mode! Expected " + DecorView.this.mFloatingActionMode);
                }
            } else {
                z = actionMode.getType() == 0;
                z2 = actionMode.getType() == 1;
            }
            if (z) {
                if (DecorView.this.mPrimaryActionModePopup != null) {
                    DecorView decorView = DecorView.this;
                    decorView.removeCallbacks(decorView.mShowPrimaryActionModePopup);
                }
                if (DecorView.this.mPrimaryActionModeView != null) {
                    DecorView.this.endOnGoingFadeAnimation();
                    final ActionBarContextView actionBarContextView = DecorView.this.mPrimaryActionModeView;
                    DecorView decorView2 = DecorView.this;
                    decorView2.mFadeAnim = ObjectAnimator.ofFloat(decorView2.mPrimaryActionModeView, (Property<ActionBarContextView, Float>) View.ALPHA, 1.0f, 0.0f);
                    DecorView.this.mFadeAnim.addListener(new Animator.AnimatorListener() { // from class: com.android.internal.policy.DecorView.ActionModeCallback2Wrapper.1
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (actionBarContextView == DecorView.this.mPrimaryActionModeView) {
                                actionBarContextView.setVisibility(8);
                                if (DecorView.this.mPrimaryActionModePopup != null) {
                                    DecorView.this.mPrimaryActionModePopup.dismiss();
                                }
                                actionBarContextView.killMode();
                                DecorView.this.mFadeAnim = null;
                                DecorView.this.requestApplyInsets();
                            }
                        }
                    });
                    DecorView.this.mFadeAnim.start();
                }
                DecorView.this.mPrimaryActionMode = null;
            } else if (z2) {
                if (DecorView.this.mFloatingToolbar != null) {
                    DecorView.this.mFloatingToolbar.setIsMovingStarted(false);
                }
                DecorView.this.cleanupFloatingActionModeViews();
                DecorView.this.mFloatingActionMode = null;
            }
            if (DecorView.this.mWindow.getCallback() != null && !DecorView.this.mWindow.isDestroyed()) {
                try {
                    DecorView.this.mWindow.getCallback().onActionModeFinished(actionMode);
                } catch (AbstractMethodError unused) {
                }
            }
            DecorView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            ActionMode.Callback callback = this.mWrapped;
            if (callback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) callback).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }
    }

    public void setDisplayCutoutBackgroundColor(int i) {
        this.mCalledDisplayCutoutBackgroundColor = true;
        this.mDisplayCutoutBackgroundColor = i;
        requestApplyInsets();
    }

    private void updateDisplayCutoutBackground(WindowInsets windowInsets) {
        int i;
        View view;
        boolean z = true;
        if (CoreRune.FW_CHANGE_DISPLAY_CUTOUT_MODE && this.mWindow.getAttributes().type == 2011 && this.mWindow.getAttributes().layoutInDisplayCutoutMode == 1) {
            Log.d(TAG, "IME does not need CutoutBackgroundView");
            return;
        }
        if (!this.mCalledDisplayCutoutBackgroundColor && (View.sIsSamsungBasicInteraction || View.sIsDisplayCutoutBackground)) {
            this.mDisplayCutoutBackgroundColor = getCurrentColor(this.mNavigationColorViewState);
        }
        if (windowInsets == null && (view = this.mDisplayCutoutBackgroundView) != null) {
            view.setBackgroundColor(this.mDisplayCutoutBackgroundColor);
            return;
        }
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        int i2 = 3;
        if ((attributes.layoutInDisplayCutoutMode == 1 || attributes.layoutInDisplayCutoutMode == 3) && windowInsets != null && windowInsets.getDisplayCutout() != null && windowInsets.hasSystemWindowInsets()) {
            DisplayCutout displayCutout = windowInsets.getDisplayCutout();
            int safeInsetLeft = displayCutout.getSafeInsetLeft();
            int safeInsetTop = displayCutout.getSafeInsetTop();
            int safeInsetRight = displayCutout.getSafeInsetRight();
            int safeInsetBottom = displayCutout.getSafeInsetBottom();
            sKnoxBadgeRightCutout = safeInsetRight;
            int i3 = 0;
            if (safeInsetLeft + safeInsetRight != 0) {
                if (safeInsetLeft > 0 && windowInsets.getSystemWindowInsetLeft() > 0) {
                    View view2 = this.mStatusColorViewState.visible ? this.mStatusColorViewState.view : null;
                    int i4 = (view2 == null || view2.getVisibility() != 0) ? 0 : view2.getLayoutParams().height;
                    View view3 = this.mNavigationColorViewState.visible ? this.mNavigationColorViewState.view : null;
                    if (view3 != null && view3.getVisibility() == 0 && view3.getLayoutParams().height != -1) {
                        i3 = view3.getLayoutParams().height;
                    }
                    int i5 = i3;
                    i3 = i4;
                    i = i5;
                } else if (safeInsetRight > 0 && windowInsets.getSystemWindowInsetRight() > 0) {
                    View view4 = this.mStatusColorViewState.visible ? this.mStatusColorViewState.view : null;
                    i2 = 5;
                    i3 = (view4 == null || view4.getVisibility() != 0) ? 0 : view4.getLayoutParams().height;
                    i = 0;
                    safeInsetLeft = safeInsetRight;
                } else {
                    if (safeInsetTop <= 0 || windowInsets.getSystemWindowInsetTop() <= 0) {
                        if (safeInsetBottom > 0 && windowInsets.getSystemWindowInsetBottom() > 0) {
                            i2 = 80;
                            safeInsetTop = safeInsetBottom;
                        }
                        i = 0;
                        z = false;
                        safeInsetLeft = 0;
                        i2 = 0;
                        safeInsetTop = 0;
                    } else {
                        i2 = 48;
                    }
                    safeInsetLeft = -1;
                    i = 0;
                }
                safeInsetTop = -1;
            } else {
                i = 0;
                z = false;
                safeInsetLeft = 0;
                i2 = 0;
                safeInsetTop = 0;
            }
            if (z && this.mDisplayCutoutBackgroundColor != 0) {
                View view5 = this.mDisplayCutoutBackgroundView;
                if (view5 == null) {
                    View view6 = new View(getContext());
                    this.mDisplayCutoutBackgroundView = view6;
                    addView(view6);
                } else if (view5.getParent() != this) {
                    View view7 = new View(getContext());
                    this.mDisplayCutoutBackgroundView = view7;
                    addView(view7);
                }
                if (this.mDisplayCutoutBackgroundView.getTag() == null) {
                    this.mDisplayCutoutBackgroundView.setTag("DisplayCutoutBackgroundView");
                    this.mDisplayCutoutBackgroundView.setElevation(-1.0f);
                }
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mDisplayCutoutBackgroundView.getLayoutParams();
                layoutParams.gravity = i2;
                layoutParams.width = safeInsetLeft;
                layoutParams.height = safeInsetTop;
                layoutParams.topMargin = i3;
                layoutParams.bottomMargin = i;
                this.mDisplayCutoutBackgroundView.setBackgroundColor(this.mDisplayCutoutBackgroundColor);
                this.mDisplayCutoutBackgroundView.requestLayout();
                return;
            }
            View view8 = this.mDisplayCutoutBackgroundView;
            if (view8 == null || view8.getParent() != this) {
                return;
            }
            removeView(this.mDisplayCutoutBackgroundView);
            this.mDisplayCutoutBackgroundView = null;
            return;
        }
        View view9 = this.mDisplayCutoutBackgroundView;
        if (view9 != null) {
            removeView(view9);
            this.mDisplayCutoutBackgroundView = null;
        }
    }

    boolean isDrawLegacyNavigationBarBackground() {
        return this.mDrawLegacyNavigationBarBackground;
    }

    private void removeKnoxBadge() {
        if (this.mKnoxBadgeViewGroupOverlay != null) {
            hideKnoxBadge();
            this.mKnoxBadgeViewGroupOverlay = null;
            this.mKnoxBadgeView = null;
            this.mKnoxBadge = null;
            this.mReverseKnoxBadge = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideKnoxBadge() {
        ViewGroupOverlay viewGroupOverlay = this.mKnoxBadgeViewGroupOverlay;
        if (viewGroupOverlay != null) {
            viewGroupOverlay.remove(this.mKnoxBadgeView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addKnoxBadge() {
        if (this.mKnoxBadgeViewGroupOverlay == null) {
            setKnoxBadge();
        }
        this.mKnoxBadgeViewGroupOverlay.add(this.mKnoxBadgeView);
    }

    private void setBadgeResource() {
        this.mKnoxBadge = this.mPackageManagerForKnoxBadge.getUserBadgeForDensity(new UserHandle(this.mUserId), 0);
        Drawable customReverseBadgeForCustomContainer = SemPersonaManager.getCustomReverseBadgeForCustomContainer(new UserHandle(this.mUserId), 0, this.mContext);
        this.mReverseKnoxBadge = customReverseBadgeForCustomContainer;
        if (customReverseBadgeForCustomContainer == null) {
            customReverseBadgeForCustomContainer = this.mKnoxBadge;
        }
        this.mReverseKnoxBadge = customReverseBadgeForCustomContainer;
    }

    private void setKnoxBadge() {
        this.mKnoxBadgeViewGroupOverlay = getOverlay();
        if (this.mPackageManagerForKnoxBadge == null) {
            this.mPackageManagerForKnoxBadge = this.mContext.getPackageManager();
        }
        setBadgeResource();
        if (this.mKnoxBadgeView == null) {
            this.mKnoxBadgeView = new View(this.mContext);
        }
        this.mKnoxBadgeView.setBackgroundDrawable(this.mKnoxBadge);
        this.mWm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        this.mDensityForKnoxBadge = this.mContext.getResources().getConfiguration().densityDpi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldHideProfileBadge(boolean z, boolean z2, int i) {
        if (((getParent() instanceof ViewGroup) && this.mKnoxBadgeView != null) || isPopOverState()) {
            return true;
        }
        if (z) {
            if (i != 0) {
                return true;
            }
            if (!z2 && i == 0) {
                return true;
            }
        }
        if (isFullscreenMode() && isImmersiveMode()) {
            return true;
        }
        return isSplitMode() && MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
    }

    private void setKnoxBadgePosition() {
        this.mKnoxBadgeDisplayRunnable = new Runnable() { // from class: com.android.internal.policy.DecorView.10
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
                boolean z = true;
                boolean z2 = Settings.Global.getInt(DecorView.this.mContext.getContentResolver(), Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) != 0;
                boolean z3 = Settings.Global.getInt(DecorView.this.mContext.getContentResolver(), Settings.Global.NAVIGATIONBAR_GESTURE_HINT, 1) != 0;
                boolean z4 = z2 && Settings.Global.getInt(DecorView.this.mContext.getContentResolver(), Settings.Global.NAVIGATIONBAR_GESTURES_DETAIL_TYPE, 0) == 1;
                boolean z5 = z2 && !z3;
                boolean z6 = Settings.Global.getInt(DecorView.this.getContext().getContentResolver(), Settings.Global.SEM_TASK_BAR, 0) == 1;
                int i = DecorView.this.getContext().getResources().getConfiguration().semDisplayDeviceType;
                if (DecorView.this.shouldHideProfileBadge(z5, z6, i)) {
                    DecorView.this.hideKnoxBadge();
                    return;
                }
                int rotation = DecorView.this.mWm.getDefaultDisplay().getRotation();
                boolean z7 = DecorView.this.getWindowingMode() == 6;
                boolean z8 = rotation == 1;
                boolean z9 = rotation == 3;
                if (i != 0 && !CoreRune.IS_TABLET_DEVICE) {
                    z = false;
                }
                if (DecorView.this.mIsDexEnabled) {
                    DecorView.this.mDensityRatio = DecorView.this.getContext().getResources().getConfiguration().densityDpi / DecorView.this.getContext().getApplicationContext().getResources().getConfiguration().densityDpi;
                }
                int intrinsicWidth = (int) (DecorView.this.mKnoxBadge.getIntrinsicWidth() * DecorView.this.mDensityRatio);
                int intrinsicHeight = (int) (DecorView.this.mKnoxBadge.getIntrinsicHeight() * DecorView.this.mDensityRatio);
                WindowInsets rootWindowInsets = DecorView.this.getRootWindowInsets();
                if (rootWindowInsets != null) {
                    DecorView.this.mKnoxBadgeInsets = rootWindowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                    DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
                    if (displayCutout != null) {
                        DecorView.sKnoxBadgeRightCutout = displayCutout.getSafeInsetRight();
                    }
                }
                int navBarSizeForBadge = DecorView.this.mKnoxBadgeInsets != null ? DecorView.getNavBarSizeForBadge(DecorView.this.mKnoxBadgeInsets.left, DecorView.this.mKnoxBadgeInsets.right, DecorView.this.mKnoxBadgeInsets.bottom) : 0;
                DecorView.this.addKnoxBadge();
                if (z) {
                    DecorView decorView = DecorView.this;
                    decorView.mKnoxBadgeStartX = decorView.mKnoxLayoutRight - intrinsicWidth;
                    DecorView decorView2 = DecorView.this;
                    decorView2.mKnoxBadgeStartY = (decorView2.mKnoxLayoutBottom - intrinsicHeight) - navBarSizeForBadge;
                    if (z9) {
                        DecorView.this.mKnoxBadgeStartX -= DecorView.sKnoxBadgeRightCutout;
                    }
                    DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mKnoxBadge);
                } else {
                    int stagePosition = DecorView.this.getResources().getConfiguration().windowConfiguration.getStagePosition();
                    if (z9) {
                        if (z4) {
                            DecorView decorView3 = DecorView.this;
                            decorView3.mKnoxBadgeStartX = decorView3.mKnoxLayoutLeft;
                            DecorView decorView4 = DecorView.this;
                            decorView4.mKnoxBadgeStartY = (decorView4.mKnoxLayoutBottom - intrinsicHeight) - navBarSizeForBadge;
                        } else {
                            DecorView decorView5 = DecorView.this;
                            decorView5.mKnoxBadgeStartX = decorView5.mKnoxLayoutLeft + navBarSizeForBadge;
                            DecorView decorView6 = DecorView.this;
                            decorView6.mKnoxBadgeStartY = decorView6.mKnoxLayoutBottom - intrinsicHeight;
                        }
                        if (z7 && stagePosition == 32) {
                            DecorView.this.mKnoxBadgeStartX = 0;
                        }
                        DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mReverseKnoxBadge);
                    } else if (z8) {
                        if (z4) {
                            DecorView decorView7 = DecorView.this;
                            decorView7.mKnoxBadgeStartX = decorView7.mKnoxLayoutRight - intrinsicWidth;
                            DecorView decorView8 = DecorView.this;
                            decorView8.mKnoxBadgeStartY = (decorView8.mKnoxLayoutBottom - intrinsicHeight) - navBarSizeForBadge;
                        } else {
                            DecorView decorView9 = DecorView.this;
                            decorView9.mKnoxBadgeStartX = (decorView9.mKnoxLayoutRight - intrinsicWidth) - navBarSizeForBadge;
                            DecorView decorView10 = DecorView.this;
                            decorView10.mKnoxBadgeStartY = decorView10.mKnoxLayoutBottom - intrinsicHeight;
                        }
                        if (z7 && stagePosition == 8) {
                            DecorView decorView11 = DecorView.this;
                            decorView11.mKnoxBadgeStartX = decorView11.mKnoxLayoutRight - intrinsicWidth;
                        }
                        DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mKnoxBadge);
                    } else {
                        DecorView decorView12 = DecorView.this;
                        decorView12.mKnoxBadgeStartX = decorView12.mKnoxLayoutRight - intrinsicWidth;
                        DecorView decorView13 = DecorView.this;
                        decorView13.mKnoxBadgeStartY = (decorView13.mKnoxLayoutBottom - intrinsicHeight) - navBarSizeForBadge;
                        DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mKnoxBadge);
                    }
                }
                int i2 = DecorView.this.mKnoxBadgeStartX + intrinsicWidth;
                int i3 = DecorView.this.mKnoxBadgeStartY + intrinsicHeight;
                DecorView.this.mKnoxBadgeView.setLeft(DecorView.this.mKnoxBadgeStartX);
                DecorView.this.mKnoxBadgeView.setTop(DecorView.this.mKnoxBadgeStartY);
                DecorView.this.mKnoxBadgeView.setRight(i2);
                DecorView.this.mKnoxBadgeView.setBottom(i3);
            }
        };
    }

    public static int getNavBarSizeForBadge(int i, int i2, int i3) {
        return isNavBarToRightEdge(i3, i2 - sKnoxBadgeRightCutout) ? i2 : isNavBarToLeftEdge(i3, i) ? i : i3;
    }

    public void setLastBackgroundResource(int i) {
        this.mLastBackgroundResource = i;
    }

    @Override // android.view.View
    public int getLastBackgroundResource() {
        return this.mLastBackgroundResource;
    }

    private boolean isActivityHomeOrRecent() {
        int activityType = getResources().getConfiguration().windowConfiguration.getActivityType();
        return activityType == 2 || activityType == 3;
    }

    public void releaseActivityFocusIfNeeded() {
        Window.Callback callback = this.mWindow.getCallback();
        if (this.mStayFocus && callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            callback.onWindowFocusChanged(false);
        }
        this.mStayFocus = false;
    }

    public void onWindowingModeChanged(int i, boolean z) {
        this.mWindowingMode = i;
        int i2 = this.mLastDisplayDeviceType;
        int i3 = getResources().getConfiguration().semDisplayDeviceType;
        this.mLastDisplayDeviceType = i3;
        boolean z2 = true;
        boolean z3 = i3 != i2;
        WindowConfiguration windowConfiguration = getResources().getConfiguration().windowConfiguration;
        boolean zIsPopOver = windowConfiguration.isPopOver();
        if (this.mIsPopOver != zIsPopOver) {
            this.mIsPopOver = zIsPopOver;
            z3 = true;
        }
        boolean zIsPopOverWithoutOutlineEffect = windowConfiguration.isPopOverWithoutOutlineEffect();
        if (this.mIsPopOverWithoutOutlineEffect != zIsPopOverWithoutOutlineEffect) {
            this.mIsPopOverWithoutOutlineEffect = zIsPopOverWithoutOutlineEffect;
        } else {
            z2 = z3;
        }
        if (z2) {
            drawableChanged();
            updateOutlineProvider();
        }
        requestInvalidateRenderNode("window_mode_changed");
    }

    public void onDexTaskDockingChanged(int i) {
        if (this.mLastDockingState != i) {
            this.mLastDockingState = i;
            requestInvalidateRenderNode("Dex docking state Changed");
        }
    }

    public int getDexTaskDockingState() {
        return this.mLastDockingState;
    }

    public void hidden_semSetForceHideRoundedCorner(boolean z) {
        this.mForceHideRoundedCorner = z;
        Log.i(TAG, "hidden_semSetForceHideRoundedCorner() : " + z);
        super.semSetRoundedCorners(0);
    }

    public boolean shouldConsumeCaptionInsets() {
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if (this.mWindow.getAttributes().isFullscreen() || !this.mWindow.mIsFloating) {
            return false;
        }
        return attributes.type == 1 || attributes.type == 2 || attributes.type == 4;
    }
}
