package com.android.internal.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.WindowConfiguration;
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
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.InputQueue;
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

import com.android.internal.R;
import com.android.internal.app.ChooserActivity;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.StandaloneActionMode;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.BackgroundFallback;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.FrameDrawHelper;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.SemViewUtils;

import java.util.List;
import java.util.function.Consumer;

public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks {
    private static final boolean DEBUG_KNOX = false;
    private static final String DEBUG_KNOX_TAG = "DecorView_KNOX";
    private static final boolean DEBUG_MEASURE = false;
    public static final int DECOR_SHADOW_FOCUSED_HEIGHT_IN_DIP = 20;
    public static final int DECOR_SHADOW_UNFOCUSED_HEIGHT_IN_DIP = 5;
    private static final int FRAME_COLOR_POPOVER_DARK = 1721342361;
    private static final int FRAME_COLOR_POPOVER_LIGHT = -3355444;
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
    private static final float STROKE_WIDTH_POPOVER_DARK = 2.0f;
    private static final float STROKE_WIDTH_POPOVER_LIGHT = 1.0f;
    private static final boolean SWEEP_OPEN_MENU = false;
    private static final String TAG = "DecorView";
    private static int sKnoxBadgeRightCutout;
    private final FloatProperty<DecorView> POP_OVER_BACKGROUND_ALPHA;
    private final FloatProperty<DecorView> POP_OVER_CONTENT_ALPHA;
    private final ViewOutlineProvider POP_OVER_OUTLINE_PROVIDER;
    private boolean mAllowUpdateElevation;
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
    private ColorDrawable mCaptionBarBackgroundDrawable;
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
    private FrameDrawHelper mFrameDrawHelper;
    private final Rect mFrameOffsets;
    private final Rect mFramePadding;
    private int mFreeformOutlineColor;
    private boolean mGestureHintEnabled;
    private boolean mGestureNavBarEnabled;
    private boolean mHasDisplayCutout;
    private boolean mHasWindowFocusInTask;
    private final Interpolator mHideInterpolator;
    private boolean mIsDexEnabled;
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
    private ColorDrawable mLastCaptionBarBackgroundDrawable;
    private int mLastCaptionHeight;
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
    private ValueAnimator mPinnedHeaderAnimator;
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
    public static final ColorViewAttributes STATUS_BAR_COLOR_VIEW_ATTRIBUTES =
            new ColorViewAttributes(
                    67108864,
                    48,
                    3,
                    5,
                    Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME,
                    16908335,
                    WindowInsets.Type.statusBars());
    public static final ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES =
            new ColorViewAttributes(
                    134217728,
                    80,
                    5,
                    3,
                    Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME,
                    16908336,
                    WindowInsets.Type.navigationBars());
    private static final ViewOutlineProvider PIP_OUTLINE_PROVIDER =
            new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRect(0, 0, view.getWidth(), view.getHeight());
                    outline.setAlpha(1.0f);
                }
            };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        updateBackgroundBlurCorners();
        return true;
    }

    DecorView(
            Context context, int featureId, PhoneWindow window, WindowManager.LayoutParams params) {
        super(context);
        boolean z;
        WearGestureInterceptionDetector wearGestureInterceptionDetector;
        WindowConfiguration winConfig;
        int i;
        boolean z2 = false;
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
        this.mLegacyNavigationBarBackgroundPaint = new Paint();
        this.mBackgroundInsets = Insets.NONE;
        this.mLastBackgroundInsets = Insets.NONE;
        this.mPendingInsetsController = new PendingInsetsController();
        this.mOriginalBackgroundBlurRadius = 0;
        this.mBackgroundBlurRadius = 0;
        this.mBackgroundBlurOnPreDrawListener =
                new ViewTreeObserver
                        .OnPreDrawListener() { // from class:
                                               // com.android.internal.policy.DecorView$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public final boolean onPreDraw() {
                        boolean lambda$new$0;
                        lambda$new$0 = DecorView.this.lambda$new$0();
                        return lambda$new$0;
                    }
                };
        this.mWindowingMode = 0;
        this.mIsDexEnabled = false;
        this.POP_OVER_OUTLINE_PROVIDER =
                new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.2
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        Path path =
                                SemViewUtils.getSmoothCornerRectPath(
                                        DecorView.this.dpToPixel(DecorView.POP_OVER_CORNER_RADIUS),
                                        0.0f,
                                        0.0f,
                                        view.getWidth(),
                                        view.getHeight());
                        outline.setPath(path);
                    }
                };
        this.POP_OVER_BACKGROUND_ALPHA =
                new FloatProperty<DecorView>(
                        "backgroundAlpha") { // from class: com.android.internal.policy.DecorView.3
                    @Override // android.util.FloatProperty
                    public void setValue(DecorView object, float value) {
                        object.setBackgroundAlpha(value);
                    }

                    @Override // android.util.Property
                    public Float get(DecorView object) {
                        return Float.valueOf(object.getBackgroundAlpha());
                    }
                };
        this.POP_OVER_CONTENT_ALPHA =
                new FloatProperty<DecorView>(
                        "contentAlpha") { // from class: com.android.internal.policy.DecorView.4
                    @Override // android.util.FloatProperty
                    public void setValue(DecorView object, float value) {
                        object.setContentAlpha(value);
                    }

                    @Override // android.util.Property
                    public Float get(DecorView object) {
                        return Float.valueOf(object.getContentAlpha());
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
        this.mLastCaptionType = -1;
        this.mDisplayCutoutBackgroundColor = 0;
        this.mCalledDisplayCutoutBackgroundColor = false;
        this.mForceHideRoundedCorner = false;
        this.mFeatureId = featureId;
        this.mShowInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mHideInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mBarEnterExitDuration =
                context.getResources().getInteger(R.integer.dock_enter_exit_duration);
        if (!context.getResources().getBoolean(R.bool.config_forceWindowDrawsStatusBarBackground)
                || params.type == 2011
                || context.getApplicationInfo().targetSdkVersion < 24) {
            z = false;
        } else {
            z = true;
        }
        this.mForceWindowDrawsBarBackgrounds = z;
        this.mSemiTransparentBarColor =
                context.getResources()
                        .getColor(R.color.system_bar_background_semi_transparent, null);
        setWindow(window);
        updateLogTag(params);
        this.mLegacyNavigationBarBackgroundPaint.setColor(
                this.mWindow.getDeviceDefaultNavigationBarColor());
        if (WearGestureInterceptionDetector.isEnabled(context)) {
            wearGestureInterceptionDetector = new WearGestureInterceptionDetector(context, this);
        } else {
            wearGestureInterceptionDetector = null;
        }
        this.mWearGestureInterceptionDetector = wearGestureInterceptionDetector;
        Resources res = context.getResources();
        if (this.mWindow.mActivityCurrentConfig != null) {
            winConfig = this.mWindow.mActivityCurrentConfig.windowConfiguration;
        } else {
            winConfig = res.getConfiguration().windowConfiguration;
        }
        this.mWindowingMode = winConfig.getWindowingMode();
        this.mIsShowNavigationBar = res.getBoolean(R.bool.config_showNavigationBar);
        try {
            int resId = res.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
            String spec = resId > 0 ? res.getString(resId) : null;
            if (spec != null && !TextUtils.isEmpty(spec)) {
                z2 = true;
            }
            this.mHasDisplayCutout = z2;
            if (!this.mHasDisplayCutout) {
                int subResId =
                        res.getIdentifier("config_subBuiltInDisplayCutout", "string", "android");
                this.mHasDisplayCutout =
                        !TextUtils.isEmpty(subResId > 0 ? res.getString(subResId) : null);
            }
        } catch (Exception e) {
            Log.w(this.mLogTag, "Can not update hasDisplayCutout. " + e.toString());
        }
        this.mRoundedCornerRadius = res.getDimensionPixelSize(R.dimen.sem_rounded_corner_radius);
        this.mRoundedCornerRadiusForLetterBox =
                res.getDimensionPixelSize(R.dimen.rounded_corner_radius_for_letterbox);
        Display display = context.getDisplayNoVerify();
        if (display == null || display.getDisplayId() != 0) {
            this.mDeviceRoundedCornerTopRadius = this.mRoundedCornerRadiusForLetterBox;
            this.mDeviceRoundedCornerBottomRadius = this.mRoundedCornerRadiusForLetterBox;
        } else {
            String displayUniqueId = display.getUniqueId();
            this.mDeviceRoundedCornerTopRadius =
                    RoundedCorners.getRoundedCornerTopRadius(res, displayUniqueId);
            this.mDeviceRoundedCornerBottomRadius =
                    RoundedCorners.getRoundedCornerBottomRadius(res, displayUniqueId);
        }
        this.mMultiWindowRoundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(context);
        if (CoreRune.MW_CAPTION_SHELL_DEX) {
            this.mIsDexEnabled =
                    this.mContext.getResources().getConfiguration().isDesktopModeEnabled();
        }
        this.mIsPopOver = res.getConfiguration().windowConfiguration.isPopOver();
        this.mLastDisplayDeviceType = res.getConfiguration().semDisplayDeviceType;
        if (winConfig.isPopOverWithoutOutlineEffect()) {
            this.mIsPopOverWithoutOutlineEffect = true;
        }
        if (this.mIsPopOver && !this.mIsPopOverWithoutOutlineEffect) {
            if (!(this.mWindow.getContext() instanceof ChooserActivity)) {
                if (this.mPopOverBackgroundColor == -1) {
                    if (SemViewUtils.isLightTheme(this.mContext)) {
                        i = R.color.sem_app_bar_bg_color;
                    } else {
                        i = R.color.sem_app_bar_bg_color_dark;
                    }
                    this.mPopOverBackgroundColor = res.getColor(i, null);
                }
            } else {
                this.mPopOverBackgroundColor = res.getColor(R.color.sem_resolver_bg_color);
            }
            Log.i(
                    TAG,
                    "mPopOverBackgroundColor=" + Integer.toHexString(this.mPopOverBackgroundColor));
        }
        if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && !this.mIsDexEnabled) {
            this.mLastCaptionType = getCaptionType();
        }
    }

    void setBackgroundFallback(Drawable fallbackDrawable) {
        this.mBackgroundFallback.setDrawable(fallbackDrawable);
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
    public void onDraw(Canvas c) {
        super.onDraw(c);
        this.mBackgroundFallback.draw(
                this,
                this.mContentRoot,
                c,
                this.mWindow.mContentParent,
                this.mStatusColorViewState.view,
                this.mNavigationColorViewState.view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int action = event.getAction();
        boolean isDown = action == 0;
        if (isDown && event.getRepeatCount() == 0) {
            if (this.mWindow.mPanelChordingKey > 0 && this.mWindow.mPanelChordingKey != keyCode) {
                boolean handled = dispatchKeyShortcutEvent(event);
                if (handled) {
                    return true;
                }
            }
            if (this.mWindow.mPreparedPanel != null
                    && this.mWindow.mPreparedPanel.isOpen
                    && this.mWindow.performPanelShortcut(
                            this.mWindow.mPreparedPanel, keyCode, event, 0)) {
                return true;
            }
        }
        if (!this.mWindow.isDestroyed()) {
            Window.Callback cb = this.mWindow.getCallback();
            boolean handled2 =
                    (cb == null || this.mFeatureId >= 0)
                            ? super.dispatchKeyEvent(event)
                            : cb.dispatchKeyEvent(event);
            if (handled2) {
                return true;
            }
        }
        return isDown
                ? this.mWindow.onKeyDown(this.mFeatureId, event.getKeyCode(), event)
                : this.mWindow.onKeyUp(this.mFeatureId, event.getKeyCode(), event);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyShortcutEvent(KeyEvent ev) {
        if (this.mWindow.mPreparedPanel != null) {
            boolean handled =
                    this.mWindow.performPanelShortcut(
                            this.mWindow.mPreparedPanel, ev.getKeyCode(), ev, 1);
            if (handled) {
                if (this.mWindow.mPreparedPanel != null) {
                    this.mWindow.mPreparedPanel.isHandled = true;
                }
                return true;
            }
        }
        Window.Callback cb = this.mWindow.getCallback();
        boolean handled2 =
                (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0)
                        ? super.dispatchKeyShortcutEvent(ev)
                        : cb.dispatchKeyShortcutEvent(ev);
        if (handled2) {
            return true;
        }
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        if (st != null && this.mWindow.mPreparedPanel == null) {
            this.mWindow.preparePanel(st, ev);
            boolean handled3 = this.mWindow.performPanelShortcut(st, ev.getKeyCode(), ev, 1);
            st.isPrepared = false;
            if (handled3) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0)
                ? super.dispatchTouchEvent(ev)
                : cb.dispatchTouchEvent(ev);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0)
                ? super.dispatchTrackballEvent(ev)
                : cb.dispatchTrackballEvent(ev);
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        Window.Callback cb = this.mWindow.getCallback();
        return (cb == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0)
                ? super.dispatchGenericMotionEvent(ev)
                : cb.dispatchGenericMotionEvent(ev);
    }

    public boolean superDispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 4) {
            int action = event.getAction();
            if (this.mPrimaryActionMode != null) {
                if (action == 1) {
                    this.mPrimaryActionMode.finish();
                }
                return true;
            }
        }
        if (super.dispatchKeyEvent(event)) {
            return true;
        }
        return getViewRootImpl() != null && getViewRootImpl().dispatchUnhandledKeyEvent(event);
    }

    public boolean superDispatchKeyShortcutEvent(KeyEvent event) {
        return super.dispatchKeyShortcutEvent(event);
    }

    public boolean superDispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public boolean superDispatchTrackballEvent(MotionEvent event) {
        return super.dispatchTrackballEvent(event);
    }

    public boolean superDispatchGenericMotionEvent(MotionEvent event) {
        return super.dispatchGenericMotionEvent(event);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return onInterceptTouchEvent(event);
    }

    private boolean isOutOfInnerBounds(int x, int y) {
        return x < 0 || y < 0 || x > getWidth() || y > getHeight();
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (this.mFeatureId >= 0 && action == 0) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (isOutOfBounds(x, y)) {
                this.mWindow.closePanel(this.mFeatureId);
                return true;
            }
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.getOnBackInvokedDispatcher().onMotionEvent(event);
            if (viewRootImpl.getOnBackInvokedDispatcher().isBackGestureInProgress()) {
                return true;
            }
        }
        if (viewRootImpl != null && this.mWearGestureInterceptionDetector != null) {
            boolean wasIntercepting = this.mWearGestureInterceptionDetector.isIntercepting();
            boolean intercepting =
                    this.mWearGestureInterceptionDetector.onInterceptTouchEvent(event);
            if (wasIntercepting != intercepting) {
                viewRootImpl.updateDecorViewGestureInterception(intercepting);
            }
            return intercepting;
        }
        return false;
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int eventType) {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return;
        }
        if ((this.mFeatureId == 0
                        || this.mFeatureId == 6
                        || this.mFeatureId == 2
                        || this.mFeatureId == 5)
                && getChildCount() == 1) {
            getChildAt(0).sendAccessibilityEvent(eventType);
        } else {
            super.sendAccessibilityEvent(eventType);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null
                && !this.mWindow.isDestroyed()
                && cb.dispatchPopulateAccessibilityEvent(event)) {
            return true;
        }
        return super.dispatchPopulateAccessibilityEventInternal(event);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            Rect drawingBounds = this.mDrawingBounds;
            getDrawingRect(drawingBounds);
            Drawable fg = getForeground();
            if (fg != null) {
                Rect frameOffsets = this.mFrameOffsets;
                drawingBounds.left += frameOffsets.left;
                drawingBounds.top += frameOffsets.top;
                drawingBounds.right -= frameOffsets.right;
                drawingBounds.bottom -= frameOffsets.bottom;
                fg.setBounds(drawingBounds);
                Rect framePadding = this.mFramePadding;
                drawingBounds.left += framePadding.left - frameOffsets.left;
                drawingBounds.top += framePadding.top - frameOffsets.top;
                drawingBounds.right -= framePadding.right - frameOffsets.right;
                drawingBounds.bottom -= framePadding.bottom - frameOffsets.bottom;
            }
            Drawable bg = super.getBackground();
            if (bg != null) {
                bg.setBounds(drawingBounds);
            }
        }
        return changed;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r18, int r19) {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.internal.policy.DecorView.onMeasure(int,"
                    + " int):void");
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mApplyFloatingVerticalInsets) {
            offsetTopAndBottom(this.mFloatingInsets.top);
        }
        if (this.mApplyFloatingHorizontalInsets) {
            offsetLeftAndRight(this.mFloatingInsets.left);
        }
        updateElevation();
        this.mAllowUpdateElevation = true;
        if (changed && this.mDrawLegacyNavigationBarBackground) {
            getViewRootImpl().requestInvalidateRootRenderNode();
        }
        if (this.mIsPopOver) {
            boolean showPopOver = (this.mWindow.getAttributes().samsungFlags & 2) == 0;
            if (this.mShowPopOver != showPopOver) {
                this.mShowPopOver = showPopOver;
                if (this.mShowPopOver) {
                    showPopOver();
                } else {
                    hidePopOver();
                }
            }
            if (changed) {
                getViewRootImpl().requestInvalidateRootRenderNode();
            }
        }
        if (this.mKnoxBadgeViewGroupOverlay != null) {
            this.mKnoxLayoutLeft = left;
            this.mKnoxLayoutRight = right;
            this.mKnoxLayoutBottom = bottom;
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
            this.mPopOverClipOutPath.addPath(
                    SemViewUtils.getSmoothCornerRectPath(
                            dpToPixel(POP_OVER_CORNER_RADIUS), 0.0f, 0.0f, width, height));
            canvas.clipPath(this.mPopOverClipOutPath);
            this.mPopOverPaint.reset();
            this.mPopOverPaint.setColor(
                    Color.argb(
                            this.mPopOverBackgroundAlpha,
                            Color.red(this.mPopOverBackgroundColor) / 255.0f,
                            Color.green(this.mPopOverBackgroundColor) / 255.0f,
                            Color.blue(this.mPopOverBackgroundColor) / 255.0f));
            this.mPopOverPaint.setBlendMode(BlendMode.SRC);
            canvas.drawRect(new RectF(0.0f, 0.0f, width, height), this.mPopOverPaint);
            canvas.clipOutPath(this.mPopOverClipOutPath);
        }
        super.draw(canvas);
        if (this.mMenuBackground != null) {
            this.mMenuBackground.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mPopOverContentAlpha < 1.0f) {
            int saveCount = canvas.getSaveCount();
            int width = getWidth();
            int height = getHeight();
            canvas.saveLayerAlpha(
                    0.0f, 0.0f, width, height, (int) (this.mPopOverContentAlpha * 255.0f));
            super.dispatchDraw(canvas);
            canvas.restoreToCount(saveCount);
            return;
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View originalView) {
        return showContextMenuForChildInternal(originalView, Float.NaN, Float.NaN);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return showContextMenuForChildInternal(originalView, x, y);
    }

    private boolean showContextMenuForChildInternal(View originalView, float x, float y) {
        MenuHelper helper;
        if (this.mWindow.mContextMenuHelper != null) {
            this.mWindow.mContextMenuHelper.dismiss();
            this.mWindow.mContextMenuHelper = null;
        }
        PhoneWindow.PhoneWindowMenuCallback callback = this.mWindow.mContextMenuCallback;
        if (this.mWindow.mContextMenu == null) {
            this.mWindow.mContextMenu = new ContextMenuBuilder(getContext());
            this.mWindow.mContextMenu.setCallback(callback);
        } else {
            this.mWindow.mContextMenu.clearAll();
        }
        boolean isPopup = (Float.isNaN(x) || Float.isNaN(y)) ? false : true;
        if (isPopup) {
            helper =
                    this.mWindow.mContextMenu.showPopup(
                            originalView.getContext(), originalView, x, y);
        } else {
            helper =
                    this.mWindow.mContextMenu.showDialog(
                            originalView, originalView.getWindowToken());
        }
        if (helper != null) {
            callback.setShowDialogForSubmenu(!isPopup);
            helper.setPresenterCallback(callback);
        }
        this.mWindow.mContextMenuHelper = helper;
        return helper != null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return startActionModeForChild(originalView, callback, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View child, ActionMode.Callback callback, int type) {
        return startActionMode(child, callback, type);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        return startActionMode(this, callback, type);
    }

    private ActionMode startActionMode(
            View originatingView, ActionMode.Callback callback, int type) {
        ActionMode.Callback2 wrappedCallback = new ActionModeCallback2Wrapper(callback);
        ActionMode mode = null;
        if (this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
            try {
                mode = this.mWindow.getCallback().onWindowStartingActionMode(wrappedCallback, type);
            } catch (AbstractMethodError e) {
                if (type == 0) {
                    try {
                        mode =
                                this.mWindow
                                        .getCallback()
                                        .onWindowStartingActionMode(wrappedCallback);
                    } catch (AbstractMethodError e2) {
                    }
                }
            }
        }
        if (mode != null) {
            if (mode.getType() == 0) {
                cleanupPrimaryActionMode();
                this.mPrimaryActionMode = mode;
            } else if (mode.getType() == 1 || mode.getType() == 99) {
                if (this.mFloatingActionMode != null) {
                    this.mFloatingActionMode.finish();
                }
                this.mFloatingActionMode = mode;
            }
        } else {
            mode = createActionMode(type, wrappedCallback, originatingView);
            if (mode != null && wrappedCallback.onCreateActionMode(mode, mode.getMenu())) {
                setHandledActionMode(mode);
            } else {
                mode = null;
            }
        }
        if (mode != null && this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
            try {
                this.mWindow.getCallback().onActionModeStarted(mode);
            } catch (AbstractMethodError e3) {
            }
        }
        return mode;
    }

    private void cleanupPrimaryActionMode() {
        if (this.mPrimaryActionMode != null) {
            this.mPrimaryActionMode.finish();
            this.mPrimaryActionMode = null;
        }
        if (this.mPrimaryActionModeView != null) {
            this.mPrimaryActionModeView.killMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupFloatingActionModeViews() {
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        if (this.mFloatingActionModeOriginatingView != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                this.mFloatingActionModeOriginatingView
                        .getViewTreeObserver()
                        .removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
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

    public void setWindowBackground(Drawable drawable) {
        int i;
        if (drawable instanceof ColorDrawable) {
            this.mPopOverBackgroundColor = ((ColorDrawable) drawable).getColor();
        } else if ((drawable instanceof GradientDrawable)
                && ((GradientDrawable) drawable).getColor() != null) {
            this.mPopOverBackgroundColor =
                    ((GradientDrawable) drawable).getColor().getDefaultColor();
        } else {
            Resources resources = getResources();
            if (SemViewUtils.isLightTheme(this.mContext)) {
                i = R.color.sem_app_bar_bg_color;
            } else {
                i = R.color.sem_app_bar_bg_color_dark;
            }
            this.mPopOverBackgroundColor = resources.getColor(i, null);
        }
        Log.i(
                TAG,
                "setWindowBackground: isPopOver="
                        + this.mIsPopOver
                        + " color="
                        + Integer.toHexString(this.mPopOverBackgroundColor)
                        + " d="
                        + drawable);
        if (this.mWindow == null) {
            this.mPendingWindowBackground = drawable;
            return;
        }
        if (this.mOriginalBackgroundDrawable != drawable) {
            this.mOriginalBackgroundDrawable = drawable;
            updateBackgroundDrawable();
            if (this.mWindow.mEdgeToEdgeEnforced
                    && !this.mWindow.mNavigationBarColorSpecified
                    && (drawable instanceof ColorDrawable)) {
                int color = ((ColorDrawable) drawable).getColor();
                boolean lightBar = Color.valueOf(color).luminance() > 0.5f;
                getWindowInsetsController().setSystemBarsAppearance(lightBar ? 512 : 0, 512);
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
            if (!View.sBrokenWindowBackground) {
                drawableChanged();
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable background) {
        setWindowBackground(background);
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
    public void onWindowSystemUiVisibilityChanged(int visible) {
        updateColorViews(null, true);
        if (this.mStatusGuard != null && this.mStatusGuard.getVisibility() == 0) {
            updateStatusGuardColor();
        }
        try {
            if (this.mKnoxBadgeViewGroupOverlay != null) {
                int sysuiVis = getSystemUiVisibility() | getWindowSystemUiVisibility();
                if ((sysuiVis & GLES30.GL_COLOR) != 0 && (sysuiVis & 6) != 0) {
                    hideKnoxBadge();
                }
            }
        } catch (Exception e) {
            Log.d(DEBUG_KNOX_TAG, "failed to remove knox badge");
        }
    }

    @Override // android.view.View
    public void onSystemBarAppearanceChanged(int appearance) {
        updateColorViews(null, true);
        if (this.mWindow != null) {
            this.mWindow.dispatchOnSystemBarAppearanceChanged(appearance);
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        this.mFloatingInsets.setEmpty();
        if ((attrs.flags & 256) == 0) {
            if (attrs.height == -2) {
                this.mFloatingInsets.top = insets.getSystemWindowInsetTop();
                this.mFloatingInsets.bottom = insets.getSystemWindowInsetBottom();
                insets =
                        insets.inset(
                                0,
                                insets.getSystemWindowInsetTop(),
                                0,
                                insets.getSystemWindowInsetBottom());
            }
            if (this.mWindow.getAttributes().width == -2) {
                this.mFloatingInsets.left = insets.getSystemWindowInsetTop();
                this.mFloatingInsets.right = insets.getSystemWindowInsetBottom();
                insets =
                        insets.inset(
                                insets.getSystemWindowInsetLeft(),
                                0,
                                insets.getSystemWindowInsetRight(),
                                0);
            }
        }
        this.mFrameOffsets.set(insets.getSystemWindowInsetsAsRect());
        WindowInsets insets2 = updateStatusGuard(updateColorViews(insets, true));
        if (getForeground() != null) {
            drawableChanged();
        }
        updateDisplayCutoutBackground(insets2);
        return insets2;
    }

    @Override // android.view.ViewGroup
    public boolean isTransitionGroup() {
        return false;
    }

    public static boolean isNavBarToRightEdge(int bottomInset, int rightInset) {
        return bottomInset == 0 && rightInset > 0;
    }

    public static boolean isNavBarToLeftEdge(int bottomInset, int leftInset) {
        return bottomInset == 0 && leftInset > 0;
    }

    public static int getNavBarSize(int bottomInset, int rightInset, int leftInset) {
        return isNavBarToRightEdge(bottomInset, rightInset)
                ? rightInset
                : isNavBarToLeftEdge(bottomInset, leftInset) ? leftInset : bottomInset;
    }

    public static void getNavigationBarRect(
            int canvasWidth, int canvasHeight, Rect systemBarInsets, Rect outRect, float scale) {
        int bottomInset = (int) (systemBarInsets.bottom * scale);
        int leftInset = (int) (systemBarInsets.left * scale);
        int rightInset = (int) (systemBarInsets.right * scale);
        int size = getNavBarSize(bottomInset, rightInset, leftInset);
        if (isNavBarToRightEdge(bottomInset, rightInset)) {
            outRect.set(canvasWidth - size, 0, canvasWidth, canvasHeight);
        } else if (isNavBarToLeftEdge(bottomInset, leftInset)) {
            outRect.set(0, 0, size, canvasHeight);
        } else {
            outRect.set(0, canvasHeight - size, canvasWidth, canvasHeight);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:263:0x01b9, code lost:

       if (r9.getHeight() != r8) goto L93;
    */
    /* JADX WARN: Removed duplicated region for block: B:161:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x01f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    android.view.WindowInsets updateColorViews(android.view.WindowInsets r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 1302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.policy.DecorView.updateColorViews(android.view.WindowInsets,"
                    + " boolean):android.view.WindowInsets");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateColorViews$1(
            ViewGroup.MarginLayoutParams lp,
            int consumedRight,
            int consumedBottom,
            int consumedLeft,
            ValueAnimator animation) {
        int progress = ((Integer) animation.getAnimatedValue()).intValue();
        lp.topMargin = progress;
        lp.rightMargin = consumedRight;
        lp.bottomMargin = consumedBottom;
        lp.leftMargin = consumedLeft;
        this.mContentRoot.setLayoutParams(lp);
    }

    private void updateBackgroundDrawable() {
        if (this.mBackgroundInsets == null) {
            this.mBackgroundInsets = Insets.NONE;
        }
        if (this.mBackgroundInsets.equals(this.mLastBackgroundInsets)
                && ((!CoreRune.MW_CAPTION_SHELL_INSETS
                                || this.mCaptionBarBackgroundDrawable
                                        == this.mLastCaptionBarBackgroundDrawable)
                        && this.mBackgroundBlurDrawable == this.mLastBackgroundBlurDrawable
                        && this.mLastOriginalBackgroundDrawable
                                == this.mOriginalBackgroundDrawable)) {
            return;
        }
        Drawable destDrawable = this.mOriginalBackgroundDrawable;
        if (this.mBackgroundBlurDrawable != null) {
            destDrawable =
                    new LayerDrawable(
                            new Drawable[] {
                                this.mBackgroundBlurDrawable, this.mOriginalBackgroundDrawable
                            });
        }
        if (destDrawable != null && !this.mBackgroundInsets.equals(Insets.NONE)) {
            destDrawable =
                    new InsetDrawable(
                            destDrawable,
                            this.mBackgroundInsets.left,
                            this.mBackgroundInsets.top,
                            this.mBackgroundInsets.right,
                            this.mBackgroundInsets
                                    .bottom) { // from class:
                                               // com.android.internal.policy.DecorView.6
                        @Override // android.graphics.drawable.InsetDrawable,
                                  // android.graphics.drawable.DrawableWrapper,
                                  // android.graphics.drawable.Drawable
                        public boolean getPadding(Rect padding) {
                            return getDrawable().getPadding(padding);
                        }
                    };
        }
        if (CoreRune.MW_CAPTION_SHELL_INSETS
                && destDrawable != null
                && this.mCaptionBarBackgroundDrawable != null) {
            destDrawable =
                    new LayerDrawable(
                            new Drawable[] {this.mCaptionBarBackgroundDrawable, destDrawable});
        }
        super.setBackgroundDrawable(destDrawable);
        this.mLastBackgroundInsets = this.mBackgroundInsets;
        this.mLastBackgroundBlurDrawable = this.mBackgroundBlurDrawable;
        this.mLastOriginalBackgroundDrawable = this.mOriginalBackgroundDrawable;
        if (CoreRune.MW_CAPTION_SHELL_INSETS) {
            this.mLastCaptionBarBackgroundDrawable = this.mCaptionBarBackgroundDrawable;
        }
    }

    private void updateBackgroundBlurCorners() {
        if (this.mBackgroundBlurDrawable == null) {
            return;
        }
        float cornerRadius = 0.0f;
        if (this.mBackgroundBlurRadius != 0 && this.mOriginalBackgroundDrawable != null) {
            Outline outline = new Outline();
            this.mOriginalBackgroundDrawable.getOutline(outline);
            cornerRadius = outline.mMode == 1 ? outline.getRadius() : 0.0f;
        }
        this.mBackgroundBlurDrawable.setCornerRadius(cornerRadius);
    }

    private void updateBackgroundBlurRadius() {
        if (getViewRootImpl() == null) {
            return;
        }
        this.mBackgroundBlurRadius =
                (this.mCrossWindowBlurEnabled && this.mWindow.isTranslucent())
                        ? this.mOriginalBackgroundBlurRadius
                        : 0;
        if (this.mBackgroundBlurDrawable == null && this.mBackgroundBlurRadius > 0) {
            this.mBackgroundBlurDrawable = getViewRootImpl().createBackgroundBlurDrawable();
            updateBackgroundDrawable();
        }
        if (this.mBackgroundBlurDrawable != null) {
            this.mBackgroundBlurDrawable.setBlurRadius(this.mBackgroundBlurRadius);
        }
    }

    void setBackgroundBlurRadius(int blurRadius) {
        this.mOriginalBackgroundBlurRadius = blurRadius;
        if (blurRadius > 0) {
            if (this.mCrossWindowBlurEnabledListener == null) {
                this.mCrossWindowBlurEnabledListener =
                        new Consumer() { // from class:
                                         // com.android.internal.policy.DecorView$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DecorView.this.lambda$setBackgroundBlurRadius$2((Boolean) obj);
                            }
                        };
                ((WindowManager) getContext().getSystemService(WindowManager.class))
                        .addCrossWindowBlurEnabledListener(this.mCrossWindowBlurEnabledListener);
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
    public /* synthetic */ void lambda$setBackgroundBlurRadius$2(Boolean enabled) {
        this.mCrossWindowBlurEnabled = enabled.booleanValue();
        updateBackgroundBlurRadius();
    }

    void removeBackgroundBlurDrawable() {
        if (this.mCrossWindowBlurEnabledListener != null) {
            ((WindowManager) getContext().getSystemService(WindowManager.class))
                    .removeCrossWindowBlurEnabledListener(this.mCrossWindowBlurEnabledListener);
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

    private int calculateStatusBarColor(int appearance) {
        return calculateBarColor(
                this.mWindow.getAttributes().flags,
                67108864,
                this.mSemiTransparentBarColor,
                this.mWindow.mStatusBarColor,
                appearance,
                8,
                this.mWindow.mEnsureStatusBarContrastWhenTransparent
                        && (this.mLastSuppressScrimTypes & WindowInsets.Type.statusBars()) == 0,
                false);
    }

    private int calculateNavigationBarColor(int appearance) {
        return calculateBarColor(
                this.mWindow.getAttributes().flags,
                134217728,
                this.mSemiTransparentBarColor,
                this.mWindow.mNavigationBarColor,
                appearance,
                16,
                this.mWindow.mEnsureNavigationBarContrastWhenTransparent
                        && (this.mLastSuppressScrimTypes & WindowInsets.Type.navigationBars()) == 0,
                this.mWindow.mEdgeToEdgeEnforced,
                this.mWindow.getDeviceDefaultNavigationBarColor());
    }

    public static int calculateBarColor(
            int flags,
            int translucentFlag,
            int semiTransparentBarColor,
            int barColor,
            int appearance,
            int lightAppearanceFlag,
            boolean ensuresContrast,
            boolean movesBarColorToScrim) {
        return calculateBarColor(
                flags,
                translucentFlag,
                semiTransparentBarColor,
                barColor,
                appearance,
                lightAppearanceFlag,
                ensuresContrast,
                movesBarColorToScrim,
                -16777216);
    }

    public static int calculateBarColor(
            int flags,
            int translucentFlag,
            int semiTransparentBarColor,
            int barColor,
            int appearance,
            int lightAppearanceFlag,
            boolean ensuresContrast,
            boolean movesBarColorToScrim,
            int deviceDefaultColor) {
        if ((flags & translucentFlag) != 0) {
            return semiTransparentBarColor;
        }
        if ((Integer.MIN_VALUE & flags) == 0) {
            return deviceDefaultColor;
        }
        if (ensuresContrast) {
            int alpha = Color.alpha(barColor);
            if (alpha == 0) {
                boolean light = (appearance & lightAppearanceFlag) != 0;
                return light ? SCRIM_LIGHT : semiTransparentBarColor;
            }
            if (movesBarColorToScrim) {
                return (16777215 & barColor) | SCRIM_ALPHA;
            }
        } else if (movesBarColorToScrim) {
            return 0;
        }
        return barColor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.view.ViewParent] */
    private int indexOfChildToRoot(View child) {
        if (child == null) {
            return -1;
        }
        View parent = child.getParent();
        if (parent == this) {
            return indexOfChild(child);
        }
        while (parent != 0 && (parent instanceof View)) {
            View v = parent;
            parent = parent.getParent();
            if (parent == this) {
                return indexOfChild(v);
            }
        }
        return -1;
    }

    private int getCurrentColor(ColorViewState state) {
        if (state.visible) {
            return state.color;
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0122, code lost:

       if (r6.leftMargin != r13) goto L75;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateColorViewInt(
            final com.android.internal.policy.DecorView.ColorViewState r21,
            int r22,
            int r23,
            int r24,
            boolean r25,
            boolean r26,
            int r27,
            boolean r28,
            boolean r29,
            int r30) {
        /*
            Method dump skipped, instructions count: 424
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.policy.DecorView.updateColorViewInt(com.android.internal.policy.DecorView$ColorViewState,"
                    + " int, int, int, boolean, boolean, int, boolean, boolean, int):void");
    }

    private static void setColor(
            View v, int color, int dividerColor, boolean verticalBar, boolean seascape) {
        if (dividerColor != 0) {
            Pair<Boolean, Boolean> dir = (Pair) v.getTag();
            if (dir == null
                    || dir.first.booleanValue() != verticalBar
                    || dir.second.booleanValue() != seascape) {
                int size =
                        Math.round(
                                TypedValue.applyDimension(
                                        1,
                                        1.0f,
                                        v.getContext().getResources().getDisplayMetrics()));
                v.setBackground(
                        new LayerDrawable(
                                new Drawable[] {
                                    new ColorDrawable(dividerColor),
                                    new InsetDrawable(
                                            (Drawable) new ColorDrawable(color),
                                            (!verticalBar || seascape) ? 0 : size,
                                            !verticalBar ? size : 0,
                                            (verticalBar && seascape) ? size : 0,
                                            0)
                                }));
                v.setTag(new Pair(Boolean.valueOf(verticalBar), Boolean.valueOf(seascape)));
                return;
            } else {
                LayerDrawable d = (LayerDrawable) v.getBackground();
                InsetDrawable inset = (InsetDrawable) d.getDrawable(1);
                ((ColorDrawable) inset.getDrawable()).setColor(color);
                ((ColorDrawable) d.getDrawable(0)).setColor(dividerColor);
                return;
            }
        }
        v.setTag(null);
        v.setBackgroundColor(color);
    }

    private void updateColorViewTranslations() {
        int rootScrollY = this.mRootScrollY;
        if (this.mStatusColorViewState.view != null) {
            this.mStatusColorViewState.view.setTranslationY(rootScrollY > 0 ? rootScrollY : 0.0f);
        }
        if (this.mNavigationColorViewState.view != null) {
            this.mNavigationColorViewState.view.setTranslationY(
                    rootScrollY < 0 ? rootScrollY : 0.0f);
        }
    }

    private WindowInsets updateStatusGuard(WindowInsets insets) {
        boolean showStatusGuard;
        int i;
        WindowInsets insets2 = insets;
        if (this.mPrimaryActionModeView == null) {
            showStatusGuard = false;
            i = 0;
        } else if (!(this.mPrimaryActionModeView.getLayoutParams()
                instanceof ViewGroup.MarginLayoutParams)) {
            showStatusGuard = false;
            i = 0;
        } else {
            ViewGroup.MarginLayoutParams mlp =
                    (ViewGroup.MarginLayoutParams) this.mPrimaryActionModeView.getLayoutParams();
            boolean mlpChanged = false;
            if (this.mPrimaryActionModeView.isShown()) {
                if (this.mTempRect == null) {
                    this.mTempRect = new Rect();
                }
                Rect rect = this.mTempRect;
                WindowInsets innerInsets =
                        this.mWindow.mContentParent.computeSystemWindowInsets(insets2, rect);
                int newTopMargin = innerInsets.getSystemWindowInsetTop();
                int newLeftMargin = innerInsets.getSystemWindowInsetLeft();
                int newRightMargin = innerInsets.getSystemWindowInsetRight();
                WindowInsets rootInsets = getRootWindowInsets();
                int newGuardLeftMargin = rootInsets.getSystemWindowInsetLeft();
                int newGuardRightMargin = rootInsets.getSystemWindowInsetRight();
                if (mlp.topMargin != newTopMargin
                        || mlp.leftMargin != newLeftMargin
                        || mlp.rightMargin != newRightMargin) {
                    mlpChanged = true;
                    mlp.topMargin = newTopMargin;
                    mlp.leftMargin = newLeftMargin;
                    mlp.rightMargin = newRightMargin;
                }
                if (newTopMargin > 0 && this.mStatusGuard == null) {
                    this.mStatusGuard = new View(this.mContext);
                    this.mStatusGuard.setVisibility(8);
                    FrameLayout.LayoutParams lp =
                            new FrameLayout.LayoutParams(-1, mlp.topMargin, 51);
                    lp.leftMargin = newGuardLeftMargin;
                    lp.rightMargin = newGuardRightMargin;
                    addView(this.mStatusGuard, indexOfChild(this.mStatusColorViewState.view), lp);
                } else if (this.mStatusGuard != null) {
                    FrameLayout.LayoutParams lp2 =
                            (FrameLayout.LayoutParams) this.mStatusGuard.getLayoutParams();
                    if (lp2.height != mlp.topMargin
                            || lp2.leftMargin != newGuardLeftMargin
                            || lp2.rightMargin != newGuardRightMargin) {
                        lp2.height = mlp.topMargin;
                        lp2.leftMargin = newGuardLeftMargin;
                        lp2.rightMargin = newGuardRightMargin;
                        this.mStatusGuard.setLayoutParams(lp2);
                    }
                }
                boolean showStatusGuard2 = this.mStatusGuard != null;
                if (showStatusGuard2 && this.mStatusGuard.getVisibility() != 0) {
                    updateStatusGuardColor();
                }
                boolean nonOverlay = (this.mWindow.getLocalFeaturesPrivate() & 1024) == 0;
                if (nonOverlay && showStatusGuard2) {
                    insets2 = insets2.inset(0, insets.getSystemWindowInsetTop(), 0, 0);
                }
                showStatusGuard = showStatusGuard2;
                i = 0;
            } else {
                showStatusGuard = false;
                if (mlp.topMargin == 0 && mlp.leftMargin == 0 && mlp.rightMargin == 0) {
                    i = 0;
                } else {
                    mlpChanged = true;
                    i = 0;
                    mlp.topMargin = 0;
                }
            }
            if (mlpChanged) {
                this.mPrimaryActionModeView.setLayoutParams(mlp);
            }
        }
        if (this.mStatusGuard != null) {
            this.mStatusGuard.setVisibility(showStatusGuard ? i : 8);
        }
        return insets2;
    }

    private void updateStatusGuardColor() {
        int color;
        boolean lightStatusBar = (getWindowSystemUiVisibility() & 8192) != 0;
        View view = this.mStatusGuard;
        if (lightStatusBar) {
            color = this.mContext.getColor(R.color.decor_view_status_guard_light);
        } else {
            color = this.mContext.getColor(R.color.decor_view_status_guard);
        }
        view.setBackgroundColor(color);
    }

    public void updatePictureInPictureOutlineProvider(boolean isInPictureInPictureMode) {
        if (this.mIsInPictureInPictureMode == isInPictureInPictureMode) {
            return;
        }
        if (isInPictureInPictureMode) {
            Window.WindowControllerCallback callback = this.mWindow.getWindowControllerCallback();
            if (callback != null && callback.isTaskRoot()) {
                super.setOutlineProvider(PIP_OUTLINE_PROVIDER);
            }
        } else if (getOutlineProvider() != this.mLastOutlineProvider) {
            setOutlineProvider(this.mLastOutlineProvider);
        }
        this.mIsInPictureInPictureMode = isInPictureInPictureMode;
    }

    @Override // android.view.View
    public void setOutlineProvider(ViewOutlineProvider provider) {
        super.setOutlineProvider(provider);
        this.mLastOutlineProvider = provider;
    }

    private void drawableChanged() {
        if (this.mChanging) {
            return;
        }
        Rect framePadding = this.mFramePadding != null ? this.mFramePadding : new Rect();
        Rect backgroundPadding =
                this.mBackgroundPadding != null ? this.mBackgroundPadding : new Rect();
        setPadding(
                framePadding.left + backgroundPadding.left,
                framePadding.top + backgroundPadding.top,
                framePadding.right + backgroundPadding.right,
                framePadding.bottom + backgroundPadding.bottom);
        requestLayout();
        invalidate();
        int opacity = -1;
        WindowConfiguration winConfig = getResources().getConfiguration().windowConfiguration;
        boolean renderShadowsInCompositor = this.mWindow.mRenderShadowsInCompositor;
        if (winConfig.hasWindowShadow() && !renderShadowsInCompositor) {
            opacity = -3;
        } else if (winConfig.isPopOver()) {
            opacity = -3;
        } else {
            Drawable bg = getBackground();
            Drawable fg = getForeground();
            if (bg != null) {
                if (fg == null) {
                    opacity = bg.getOpacity();
                } else if (framePadding.left <= 0
                        && framePadding.top <= 0
                        && framePadding.right <= 0
                        && framePadding.bottom <= 0) {
                    int fop = fg.getOpacity();
                    int bop = bg.getOpacity();
                    if (fop == -1 || bop == -1) {
                        opacity = -1;
                    } else if (fop == 0) {
                        opacity = bop;
                    } else if (bop == 0) {
                        opacity = fop;
                    } else {
                        opacity = Drawable.resolveOpacity(fop, bop);
                    }
                } else {
                    opacity = -3;
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

    public void onWindowFocusInTaskChanged(boolean hasWindowFocusInTask) {
        if (this.mHasWindowFocusInTask != hasWindowFocusInTask) {
            this.mHasWindowFocusInTask = hasWindowFocusInTask;
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (this.mWindow.hasFeature(0) && !hasWindowFocus && this.mWindow.mPanelChordingKey != 0) {
            this.mWindow.closePanel(0);
        }
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            if (MultiWindowCoreState.MW_MULTISTAR_STAY_FOCUS_ACTIVITY_DYNAMIC_ENABLED
                    && (((cb instanceof Activity)
                                    || (this.mWindow.getContext() instanceof Activity))
                            && !isActivityHomeOrRecent())) {
                if (!this.mStayFocus && hasWindowFocus) {
                    cb.onWindowFocusChanged(true);
                    this.mStayFocus = true;
                } else {
                    ActivityThread thread = ActivityThread.currentActivityThread();
                    if (this.mStayFocus
                            && thread != null
                            && !thread.mayStayActivityFocus(this.mWindow.getAttributes().token)) {
                        cb.onWindowFocusChanged(false);
                        this.mStayFocus = false;
                    }
                }
            } else {
                this.mStayFocus = false;
                cb.onWindowFocusChanged(hasWindowFocus);
            }
        }
        if (this.mPrimaryActionMode != null) {
            this.mPrimaryActionMode.onWindowFocusChanged(hasWindowFocus);
        }
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.onWindowFocusChanged(hasWindowFocus);
        }
        updateElevation();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mUserId = this.mContext.getUserId();
        Context winContext = this.mWindow.getContext();
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        if ((winContext instanceof Activity) && attrs.isFullscreen()) {
            this.mIsKnoxActivity = true;
        }
        boolean z = false;
        this.mIsKeyboardShown = attrs.type == 2011;
        if (attrs.type == 2 && attrs.isFullscreen()) {
            z = true;
        }
        this.mIsFullViewShown = z;
        try {
            if ((this.mIsKeyboardShown || this.mIsFullViewShown || this.mIsKnoxActivity)
                    && (SemPersonaManager.isKnoxId(this.mUserId)
                            || SemDualAppManager.isDualAppId(this.mUserId))) {
                setKnoxBadge();
                setKnoxBadgePosition();
            }
        } catch (Exception e) {
            Log.d(DEBUG_KNOX_TAG, "failed to set knox badge");
        }
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            cb.onAttachedToWindow();
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
        Window.Callback cb = this.mWindow.getCallback();
        if (cb != null && this.mFeatureId < 0) {
            cb.onDetachedFromWindow();
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
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        removeBackgroundBlurDrawable();
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        if (st != null && st.menu != null && this.mFeatureId < 0) {
            st.menu.close();
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
        if (this.mLastBackgroundResource == 17304794
                || this.mLastBackgroundResource == 17304791
                || this.mLastBackgroundResource == 17304792) {
            setWindowBackground(null);
        }
    }

    @Override // android.view.View
    public void onCloseSystemDialogs(String reason) {
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
    public void setSurfaceType(int type) {
        this.mWindow.setType(type);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceFormat(int format) {
        this.mWindow.setFormat(format);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceKeepScreenOn(boolean keepOn) {
        if (keepOn) {
            this.mWindow.addFlags(128);
        } else {
            this.mWindow.clearFlags(128);
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void onRootViewScrollYChanged(int rootScrollY) {
        this.mRootScrollY = rootScrollY;
        updateColorViewTranslations();
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public PendingInsetsController providePendingInsetsController() {
        return this.mPendingInsetsController;
    }

    private ActionMode createActionMode(
            int type, ActionMode.Callback2 callback, View originatingView) {
        switch (type) {
            case 1:
            case 99:
                ActionMode mode = createFloatingActionMode(originatingView, callback);
                mode.setType(type);
                return mode;
            default:
                return createStandaloneActionMode(callback);
        }
    }

    private void setHandledActionMode(ActionMode mode) {
        if (mode.getType() == 0) {
            setHandledPrimaryActionMode(mode);
        } else if (mode.getType() == 1 || mode.getType() == 99) {
            setHandledFloatingActionMode(mode);
        }
    }

    private ActionMode createStandaloneActionMode(ActionMode.Callback callback) {
        Context actionBarContext;
        endOnGoingFadeAnimation();
        cleanupPrimaryActionMode();
        if (this.mPrimaryActionModeView == null
                || !this.mPrimaryActionModeView.isAttachedToWindow()) {
            if (this.mWindow.isFloating()) {
                TypedValue outValue = new TypedValue();
                Resources.Theme baseTheme = this.mContext.getTheme();
                baseTheme.resolveAttribute(16843825, outValue, true);
                if (outValue.resourceId != 0) {
                    Resources.Theme actionBarTheme = this.mContext.getResources().newTheme();
                    actionBarTheme.setTo(baseTheme);
                    actionBarTheme.applyStyle(outValue.resourceId, true);
                    actionBarContext = new ContextThemeWrapper(this.mContext, 0);
                    actionBarContext.getTheme().setTo(actionBarTheme);
                } else {
                    actionBarContext = this.mContext;
                }
                this.mPrimaryActionModeView = new ActionBarContextView(actionBarContext);
                this.mPrimaryActionModePopup =
                        new PopupWindow(
                                actionBarContext,
                                (AttributeSet) null,
                                R.attr.actionModePopupWindowStyle);
                this.mPrimaryActionModePopup.setWindowLayoutType(2);
                this.mPrimaryActionModePopup.setContentView(this.mPrimaryActionModeView);
                this.mPrimaryActionModePopup.setWidth(-1);
                actionBarContext.getTheme().resolveAttribute(16843499, outValue, true);
                int height =
                        TypedValue.complexToDimensionPixelSize(
                                outValue.data, actionBarContext.getResources().getDisplayMetrics());
                this.mPrimaryActionModeView.setContentHeight(height);
                this.mPrimaryActionModePopup.setHeight(-2);
                this.mShowPrimaryActionModePopup =
                        new Runnable() { // from class: com.android.internal.policy.DecorView.8
                            @Override // java.lang.Runnable
                            public void run() {
                                DecorView.this.mPrimaryActionModePopup.showAtLocation(
                                        DecorView.this.mPrimaryActionModeView
                                                .getApplicationWindowToken(),
                                        55,
                                        0,
                                        0);
                                DecorView.this.endOnGoingFadeAnimation();
                                if (DecorView.this.shouldAnimatePrimaryActionModeView()) {
                                    DecorView.this.mFadeAnim =
                                            ObjectAnimator.ofFloat(
                                                    DecorView.this.mPrimaryActionModeView,
                                                    (Property<ActionBarContextView, Float>)
                                                            View.ALPHA,
                                                    0.0f,
                                                    1.0f);
                                    DecorView.this.mFadeAnim.addListener(
                                            new AnimatorListenerAdapter() { // from class:
                                                                            // com.android.internal.policy.DecorView.8.1
                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public void onAnimationStart(Animator animation) {
                                                    DecorView.this.mPrimaryActionModeView
                                                            .setVisibility(0);
                                                }

                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public void onAnimationEnd(Animator animation) {
                                                    DecorView.this.mPrimaryActionModeView.setAlpha(
                                                            1.0f);
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
                ViewStub stub = (ViewStub) findViewById(R.id.action_mode_bar_stub);
                if (stub != null) {
                    this.mPrimaryActionModeView = (ActionBarContextView) stub.inflate();
                    this.mPrimaryActionModePopup = null;
                }
            }
        }
        if (this.mPrimaryActionModeView == null) {
            return null;
        }
        this.mPrimaryActionModeView.killMode();
        ActionMode mode =
                new StandaloneActionMode(
                        this.mPrimaryActionModeView.getContext(),
                        this.mPrimaryActionModeView,
                        callback,
                        this.mPrimaryActionModePopup == null);
        return mode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.end();
        }
    }

    private void setHandledPrimaryActionMode(ActionMode mode) {
        endOnGoingFadeAnimation();
        this.mPrimaryActionMode = mode;
        this.mPrimaryActionMode.invalidate();
        this.mPrimaryActionModeView.initForMode(this.mPrimaryActionMode);
        if (this.mPrimaryActionModePopup != null) {
            post(this.mShowPrimaryActionModePopup);
        } else if (shouldAnimatePrimaryActionModeView()) {
            this.mFadeAnim =
                    ObjectAnimator.ofFloat(
                            this.mPrimaryActionModeView,
                            (Property<ActionBarContextView, Float>) View.ALPHA,
                            0.0f,
                            1.0f);
            this.mFadeAnim.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.android.internal.policy.DecorView.9
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation) {
                            DecorView.this.mPrimaryActionModeView.setVisibility(0);
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
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

    private ActionMode createFloatingActionMode(
            View originatingView, ActionMode.Callback2 callback) {
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mWindow);
        final FloatingActionMode mode =
                new FloatingActionMode(
                        this.mContext, callback, originatingView, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = originatingView;
        this.mFloatingToolbarPreDrawListener =
                new ViewTreeObserver
                        .OnPreDrawListener() { // from class:
                                               // com.android.internal.policy.DecorView.10
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public boolean onPreDraw() {
                        mode.updateViewLocationInWindow();
                        return true;
                    }
                };
        return mode;
    }

    private void setHandledFloatingActionMode(ActionMode mode) {
        this.mFloatingActionMode = mode;
        boolean isDeviceDefaultThemeTextView = false;
        if (this.mFloatingActionModeOriginatingView instanceof TextView) {
            isDeviceDefaultThemeTextView =
                    ((TextView) this.mFloatingActionModeOriginatingView).isThemeDeviceDefault();
        }
        boolean isSemTypeFloating = mode.getType() == 99 || isDeviceDefaultThemeTextView;
        this.mFloatingToolbar = new FloatingToolbar(this.mWindow, isSemTypeFloating);
        ((FloatingActionMode) this.mFloatingActionMode).setFloatingToolbar(this.mFloatingToolbar);
        this.mFloatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView
                .getViewTreeObserver()
                .addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
    }

    void setWindow(PhoneWindow phoneWindow) {
        this.mWindow = phoneWindow;
        Context context = getContext();
        if (context instanceof DecorContext) {
            DecorContext decorContext = (DecorContext) context;
            decorContext.setPhoneWindow(this.mWindow);
        }
        if (this.mPendingWindowBackground != null) {
            Drawable background = this.mPendingWindowBackground;
            this.mPendingWindowBackground = null;
            setWindowBackground(background);
        }
    }

    @Override // android.view.View
    public Resources getResources() {
        return getContext().getResources();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        StateListDrawable statefulWindowBackground;
        int[] states;
        StateListDrawable statefulWindowBackground2;
        int[] states2;
        Drawable newBackground;
        int i;
        int i2;
        super.onConfigurationChanged(newConfig);
        boolean updateWindowFormat = false;
        int oldDisplayDeviceType = this.mLastDisplayDeviceType;
        this.mLastDisplayDeviceType = newConfig.semDisplayDeviceType;
        if (this.mLastDisplayDeviceType != oldDisplayDeviceType) {
            updateWindowFormat = true;
        }
        WindowConfiguration winConfig = newConfig.windowConfiguration;
        boolean isPopPover = winConfig.isPopOver();
        if (this.mIsPopOver != isPopPover) {
            this.mIsPopOver = isPopPover;
            updateWindowFormat = true;
            if (!this.mIsPopOver) {
                removePopOverElevation();
            }
        }
        boolean isPopOverWithoutOutlineEffect = winConfig.isPopOverWithoutOutlineEffect();
        if (this.mIsPopOverWithoutOutlineEffect != isPopOverWithoutOutlineEffect) {
            this.mIsPopOverWithoutOutlineEffect = isPopOverWithoutOutlineEffect;
            updateWindowFormat = true;
        }
        if (updateWindowFormat) {
            drawableChanged();
        }
        if (this.mIsPopOver && !this.mIsPopOverWithoutOutlineEffect) {
            Resources res = getResources();
            if (this.mWindow.getContext() instanceof ChooserActivity) {
                this.mPopOverBackgroundColor = res.getColor(R.color.sem_resolver_bg_color);
            } else if (this.mPopOverBackgroundColor == -1) {
                if (SemViewUtils.isLightTheme(this.mContext)) {
                    i2 = R.color.sem_app_bar_bg_color;
                } else {
                    i2 = R.color.sem_app_bar_bg_color_dark;
                }
                this.mPopOverBackgroundColor = res.getColor(i2, null);
            }
            Log.i(
                    TAG,
                    "mPopOverBackgroundColor=" + Integer.toHexString(this.mPopOverBackgroundColor));
            if (getViewRootImpl() != null) {
                getViewRootImpl().requestInvalidateRootRenderNode();
            }
        }
        updateOutlineProvider();
        boolean updateWindowFormat2 = isFreeformMode();
        if (updateWindowFormat2) {
            boolean nightMode = (getResources().getConfiguration().uiMode & 48) == 32;
            Resources resources = this.mContext.getResources();
            if (nightMode) {
                i = R.color.sec_decor_caption_color_dark;
            } else {
                i = R.color.sec_decor_caption_color_light;
            }
            this.mFreeformOutlineColor = resources.getColor(i);
        }
        boolean nightMode2 = CoreRune.MW_CAPTION_SHELL_DEX;
        if (nightMode2) {
            this.mIsDexEnabled = newConfig.isDesktopModeEnabled();
        }
        updateOutlineProvider();
        initializeElevation();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(16843607, this.mWindow.mMinWidthMinor, true);
        theme.resolveAttribute(16843606, this.mWindow.mMinWidthMajor, true);
        this.mLastSmallestScreenWidthDp = newConfig.smallestScreenWidthDp;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.getOnBackInvokedDispatcher().onConfigurationChanged(newConfig);
        }
        refreshGestureNavBarSettings();
        try {
            if ((this.mIsKeyboardShown || this.mIsFullViewShown || this.mIsKnoxActivity)
                    && ((SemPersonaManager.isKnoxId(this.mUserId)
                                    || SemDualAppManager.isDualAppId(this.mUserId))
                            && newConfig.densityDpi != this.mDensityForKnoxBadge)) {
                setBadgeResource();
                this.mDensityForKnoxBadge = newConfig.densityDpi;
            }
        } catch (Exception e) {
            Log.d(this.mLogTag, "failed to remove knox badge");
        }
        this.mWindow.updateDeviceDefaultNavigationBarColor();
        this.mLegacyNavigationBarBackgroundPaint.setColor(
                this.mWindow.getDeviceDefaultNavigationBarColor());
        this.mWindow.updateDefaultNavigationBarColor();
        if (this.mLastBackgroundResource == 17304794) {
            Drawable currWindowBackground = getBackground();
            if ((currWindowBackground instanceof StateListDrawable)
                    && (states2 =
                                    (statefulWindowBackground2 =
                                                    (StateListDrawable) currWindowBackground)
                                            .getState())
                            != null
                    && states2.length > 0
                    && (statefulWindowBackground2.getStateDrawable(0) instanceof BitmapDrawable)
                    && (newBackground = getContext().getDrawable(this.mLastBackgroundResource))
                            != null) {
                setWindowBackground(newBackground);
                return;
            }
            return;
        }
        if (this.mLastBackgroundResource == 17304791 || this.mLastBackgroundResource == 17304792) {
            Drawable currWindowBackground2 = getBackground();
            Drawable newBackground2 = null;
            if (currWindowBackground2 instanceof BitmapDrawable) {
                newBackground2 =
                        getContext().getResources().getDrawable(this.mLastBackgroundResource);
            } else if ((currWindowBackground2 instanceof StateListDrawable)
                    && (states =
                                    (statefulWindowBackground =
                                                    (StateListDrawable) currWindowBackground2)
                                            .getState())
                            != null
                    && states.length > 0
                    && (statefulWindowBackground.getStateDrawable(0) instanceof BitmapDrawable)) {
                newBackground2 =
                        getContext().getResources().getDrawable(this.mLastBackgroundResource);
            }
            if (newBackground2 != null) {
                setWindowBackground(newBackground2);
            }
        }
    }

    @Override // android.view.View
    public void onMovedToDisplay(int displayId, Configuration config) {
        super.onMovedToDisplay(displayId, config);
        getContext().updateDisplay(displayId);
    }

    private boolean isFillingScreen(Configuration config) {
        boolean isFullscreen = config.windowConfiguration.getWindowingMode() == 1;
        return isFullscreen && ((getWindowSystemUiVisibility() | getSystemUiVisibility()) & 4) != 0;
    }

    void onResourcesLoaded(LayoutInflater inflater, int layoutResource) {
        this.mLastOutlineProvider = getOutlineProvider();
        updateOutlineProvider();
        View root = inflater.inflate(layoutResource, (ViewGroup) null);
        addView(root, 0, new ViewGroup.LayoutParams(-1, -1));
        this.mContentRoot = (ViewGroup) root;
        initializeElevation();
        this.mLastSmallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
    }

    void clearContentView() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View v = getChildAt(i);
            if (v != this.mStatusColorViewState.view
                    && v != this.mNavigationColorViewState.view
                    && v != this.mStatusGuard) {
                removeViewAt(i);
            }
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowSizeIsChanging(
            Rect newBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {}

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeStart(
            Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
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
    public boolean onContentDrawn(int offsetX, int offsetY, int sizeX, int sizeY) {
        return false;
    }

    @Override // android.view.WindowCallbacks
    public void onRequestDraw(boolean reportNextDraw) {
        if (reportNextDraw && isAttachedToWindow()) {
            getViewRootImpl().reportDrawFinish();
        }
    }

    @Override // android.view.WindowCallbacks
    public void onPostDraw(RecordingCanvas canvas) {
        drawLegacyNavigationBarBackground(canvas);
        if ((isActivity() || isFullSize()) && this.mIsPopOver) {
            if (this.mWindow != null && this.mWindow.isFloating()) {
                return;
            }
            this.mPopOverFramePaint.setStyle(Paint.Style.STROKE);
            this.mPopOverFramePaint.setStrokeJoin(Paint.Join.ROUND);
            this.mPopOverFramePaint.setStrokeCap(Paint.Cap.ROUND);
            this.mPopOverFramePaint.setAntiAlias(true);
            boolean nightMode = (getResources().getConfiguration().uiMode & 48) == 32;
            float popOverThickness = nightMode ? 2.0f : 1.0f;
            this.mPopOverFramePaint.setColor(nightMode ? FRAME_COLOR_POPOVER_DARK : -3355444);
            this.mPopOverFramePaint.setStrokeWidth(popOverThickness);
            int width = getWidth();
            int height = getHeight();
            canvas.drawPath(
                    SemViewUtils.getSmoothCornerRectPath(
                            dpToPixel(POP_OVER_CORNER_RADIUS), 0.0f, 0.0f, width, height),
                    this.mPopOverFramePaint);
            return;
        }
        drawFrameIfNeeded(canvas);
    }

    private void drawLegacyNavigationBarBackground(RecordingCanvas canvas) {
        View v;
        if (this.mLastDrawLegacyNavigationBarBackground
                != this.mDrawLegacyNavigationBarBackground) {
            this.mLastDrawLegacyNavigationBarBackground = this.mDrawLegacyNavigationBarBackground;
            this.mWindow.updateForceLightNavigationBar();
        }
        if (!this.mDrawLegacyNavigationBarBackground
                || this.mDrawLegacyNavigationBarBackgroundHandled
                || (v = this.mNavigationColorViewState.view) == null) {
            return;
        }
        canvas.drawRect(
                v.getLeft(),
                v.getTop(),
                v.getRight(),
                v.getBottom(),
                this.mLegacyNavigationBarBackgroundPaint);
    }

    private void initializeElevation() {
        this.mAllowUpdateElevation = false;
        updateElevation();
    }

    public boolean isNonFullscreenWindowInFreeform() {
        return isFreeformMode()
                && isActivity()
                && !isFullSize()
                && this.mWindow.getAttributes().type >= 1
                && this.mWindow.getAttributes().type <= 99;
    }

    private void updateElevation() {
        int windowingMode =
                getResources().getConfiguration().windowConfiguration.getWindowingMode();
        boolean renderShadowsInCompositor = this.mWindow.mRenderShadowsInCompositor;
        boolean forceSetElevation = isPopOverState();
        if (renderShadowsInCompositor && !forceSetElevation) {
            return;
        }
        float elevation = 0.0f;
        boolean wasAdjustedForStack = this.mElevationAdjustedForStack;
        if (this.mIsPopOver
                && (this.mIsPopOverWithoutOutlineEffect || this.mPreventPopOverElevation)) {
            elevation = 0.0f;
            this.mElevationAdjustedForStack = true;
        } else if (isPopOverState()) {
            elevation = dipToPx(32.0f);
            this.mElevationAdjustedForStack = true;
        } else if (windowingMode == 5) {
            float elevation2 = hasWindowFocus() ? 20.0f : 5.0f;
            if (!this.mAllowUpdateElevation) {
                elevation2 = 20.0f;
            }
            elevation = dipToPx(elevation2);
            this.mElevationAdjustedForStack = true;
        } else {
            this.mElevationAdjustedForStack = false;
        }
        if ((wasAdjustedForStack || this.mElevationAdjustedForStack)
                && getElevation() != elevation) {
            this.mWindow.setElevation(elevation);
        }
    }

    private float dipToPx(float dip) {
        return TypedValue.applyDimension(1, dip, getResources().getDisplayMetrics());
    }

    private static String getTitleSuffix(WindowManager.LayoutParams params) {
        if (params == null) {
            return "";
        }
        String[] split = params.getTitle().toString().split("\\.");
        if (split.length <= 0) {
            return "";
        }
        return split[split.length - 1];
    }

    void updateLogTag(WindowManager.LayoutParams params) {
        this.mLogTag =
                "DecorView[" + getTitleSuffix(params) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.view.View
    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int deviceId) {
        PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
        Menu menu = st != null ? st.menu : null;
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onProvideKeyboardShortcuts(list, menu, deviceId);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchPointerCaptureChanged(boolean hasCapture) {
        super.dispatchPointerCaptureChanged(hasCapture);
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onPointerCaptureChanged(hasCapture);
        }
    }

    @Override // android.view.View
    public int getAccessibilityViewId() {
        return 2147483646;
    }

    @Override // android.view.View
    public WindowInsetsController getWindowInsetsController() {
        if (isAttachedToWindow()) {
            return super.getWindowInsetsController();
        }
        return this.mPendingInsetsController;
    }

    @Override // android.view.View
    public String toString() {
        return super.toString()
                + NavigationBarInflaterView.SIZE_MOD_START
                + getTitleSuffix(this.mWindow.getAttributes())
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private boolean isActivity() {
        return (this.mWindow == null || this.mWindow.getWindowControllerCallback() == null)
                ? false
                : true;
    }

    private final Configuration getConfiguration() {
        if (this.mWindow != null && this.mWindow.mActivityCurrentConfig != null) {
            return this.mWindow.mActivityCurrentConfig;
        }
        return getResources().getConfiguration();
    }

    public int getWindowingMode() {
        if (this.mWindowingMode != 0) {
            return this.mWindowingMode;
        }
        return getConfiguration().windowConfiguration.getWindowingMode();
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
        return WindowConfiguration.isSplitScreenWindowingMode(
                getConfiguration().windowConfiguration);
    }

    public boolean isFullSize() {
        return this.mWindow != null && this.mWindow.getAttributes().isFullscreen();
    }

    private boolean isImmersiveMode() {
        int systemUiVis = getSystemUiVisibility() | getWindowSystemUiVisibility();
        if ((systemUiVis & GLES30.GL_COLOR) != 0 && (systemUiVis & 2) != 0) {
            return true;
        }
        return false;
    }

    private void updateRoundedCornerStateIfNeeded() {
        int roundRadius;
        if (this.mWindow.mActivityCurrentConfig == null) {
            return;
        }
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        boolean isFullscreen = attrs.isFullscreen();
        Configuration config = getConfiguration();
        this.mRotationForRoundedCorner = config.windowConfiguration.getRotation();
        this.mDisplayRotationForRoundedCorner = config.windowConfiguration.getDisplayRotation();
        boolean applyRoundedCorner = false;
        if ((!CoreRune.MW_SPLIT_FLEX_PANEL_MODE || !config.windowConfiguration.isFlexPanelEnabled())
                && !config.windowConfiguration.isPopOver()) {
            if (isFullscreenMode()
                    && config.orientation == 2
                    && this.mHasDisplayCutout
                    && isFullscreen) {
                ViewRootImpl viewRootImpl = getViewRootImpl();
                int letterboxDirection =
                        viewRootImpl != null ? viewRootImpl.mRequestedLetterboxDirection : 0;
                int corners =
                        getRoundedCornersInLandscapeMode(
                                attrs.layoutInDisplayCutoutMode, letterboxDirection);
                if (corners != 0) {
                    if ((letterboxDirection & 1) != 0) {
                        roundRadius = this.mDeviceRoundedCornerTopRadius;
                    } else {
                        int roundRadius2 = letterboxDirection & 2;
                        if (roundRadius2 != 0) {
                            roundRadius = this.mDeviceRoundedCornerBottomRadius;
                        } else {
                            roundRadius = this.mRoundedCornerRadiusForLetterBox;
                        }
                    }
                    super.semSetRoundedCorners(corners, roundRadius);
                    super.semSetRoundedCornerColor(corners, -16777216);
                    applyRoundedCorner = true;
                }
            } else if (isFullscreenMode()
                    && config.orientation == 1
                    && this.mIsShowNavigationBar
                    && attrs.type != 2037) {
                applyRoundedCorner = shouldDrawRoundedCornerInPortraitMode(isFullscreen);
                if (applyRoundedCorner) {
                    super.semSetRoundedCorners(12, this.mRoundedCornerRadius);
                    super.semSetRoundedCornerColor(
                            12, getCurrentColor(this.mNavigationColorViewState));
                }
            } else if (isSplitMode() && isFullscreen) {
                Rect windowBounds = getCurrentBounds(this.mContext);
                if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER && !this.mWindow.isFloating()) {
                    updateRoundedCornerForMultiSplit(this.mContext);
                } else if (!this.mWindow.mIsFloating
                        || (windowBounds.width() <= getWidth()
                                && windowBounds.height() <= getHeight())) {
                    updateRoundedCornerForSplit(this.mContext);
                }
                applyRoundedCorner = true;
                this.mForceRoundedCorner = false;
            } else if (isFreeformMode() && isFullscreen) {
                super.semSetRoundedCorners(15, this.mMultiWindowRoundedCornerRadius);
                super.semSetRoundedCornerColor(15, this.mFreeformOutlineColor);
                applyRoundedCorner = true;
            }
        }
        if (applyRoundedCorner) {
            boolean isInSplitMode = isSplitMode();
            if ((this.mRotationForRoundedCorner == 0 || this.mRotationForRoundedCorner == 2)
                    && !isInSplitMode) {
                this.mOverrideRoundedCornerBounds.set(
                        this.mLastLeftInset,
                        this.mLastTopInset,
                        getWidth() - this.mLastRightInset,
                        getHeight() - this.mLastBottomInset);
                return;
            } else {
                this.mOverrideRoundedCornerBounds.set(
                        this.mLastLeftInset, 0, getWidth() - this.mLastRightInset, getHeight());
                return;
            }
        }
        super.semSetRoundedCorners(0);
        this.mOverrideRoundedCornerBounds.setEmpty();
    }

    private int getFlipCoverScreenRoundedCorner(
            WindowManager.LayoutParams attrs, int letterboxDirection, int rotation) {
        int corners = 0;
        if ((attrs.flags & 1048576) != 0) {
            if (letterboxDirection != 0 || attrs.layoutInDisplayCutoutMode != 3 || rotation != 2) {
                return 0;
            }
            int corners2 = 0 | 3;
            return corners2;
        }
        if ((letterboxDirection & 4) != 0) {
            corners = 0 | 3;
        }
        if ((letterboxDirection & 8) != 0) {
            return corners | 12;
        }
        return corners;
    }

    private int getRoundedCornersInLandscapeMode(int cutoutMode, int letterboxDirection) {
        if (cutoutMode == 1 || letterboxDirection == 0) {
            return 0;
        }
        int corners = 0;
        if ((letterboxDirection & 1) != 0) {
            corners = 0 | 5;
        }
        if ((letterboxDirection & 2) != 0) {
            return corners | 10;
        }
        return corners;
    }

    private boolean shouldDrawRoundedCornerInPortraitMode(boolean isFullscreen) {
        if (this.mGestureNavBarEnabled && !this.mGestureHintEnabled) {
            return false;
        }
        if ((isFullscreen && (this.mWindow.getContext() instanceof Activity))
                || this.mForceHideRoundedCorner) {
            return false;
        }
        if (this.mForceRoundedCorner) {
            boolean hasTaskBar =
                    Settings.Global.getInt(
                                    this.mContext.getContentResolver(),
                                    Settings.Global.SEM_TASK_BAR,
                                    0)
                            == 1;
            return this.mNavigationColorViewState.visible
                    && !hasTaskBar
                    && (this.mRoundedCornerMode & 12) == 12;
        }
        boolean hasTaskBar2 = View.sIsSamsungBasicInteraction;
        if (!hasTaskBar2 || !this.mNavigationColorViewState.visible) {
            return false;
        }
        if (this.mContentRoot == null
                || this.mStatusColorViewState.view == null
                || this.mNavigationColorViewState.view == null) {
            return true;
        }
        return 3 >= getChildCount() || !isChildIntersectsWith(this.mNavigationColorViewState.view);
    }

    @Override // android.view.View
    public void semSetRoundedCorners(int corners) {
        this.mForceRoundedCorner = true;
        this.mRoundedCornerMode = corners;
        super.semSetRoundedCorners(corners);
    }

    @Override // android.view.View
    public boolean setOverrideRoundedCornerBounds(Rect outRoundedCornerBounds) {
        if (outRoundedCornerBounds != null
                && !this.mOverrideRoundedCornerBounds.isEmpty()
                && this.mRotationForRoundedCorner == this.mDisplayRotationForRoundedCorner) {
            outRoundedCornerBounds.set(this.mOverrideRoundedCornerBounds);
            return true;
        }
        return false;
    }

    private boolean isChildIntersectsWith(View colorView) {
        this.mTmpColorViewBounds.set(
                colorView.getLeft(),
                colorView.getTop(),
                colorView.getRight(),
                colorView.getBottom());
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (this.mContentRoot != child
                    && this.mNavigationColorViewState.view != child
                    && this.mTmpColorViewBounds.intersects(
                            child.getLeft(), child.getTop(), child.getRight(), child.getBottom())) {
                return true;
            }
        }
        return false;
    }

    private void refreshGestureNavBarSettings() {
        this.mGestureNavBarEnabled =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN,
                                0)
                        != 0;
        this.mGestureHintEnabled =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                Settings.Global.NAVIGATIONBAR_GESTURE_HINT,
                                1)
                        != 0;
    }

    private void updateRoundedCornerForSplit(Context context) {
        Configuration config = getConfiguration();
        int rotation = config.windowConfiguration.getRotation();
        int color = MultiWindowUtils.getRoundedCornerColor(context);
        int stagePosition = getStagePosition();
        int roundedCorner = 15;
        if (CoreRune.MW_EMBED_ACTIVITY && config.windowConfiguration.isEmbedded()) {
            int embedActivityMode = config.windowConfiguration.getEmbedActivityMode();
            if (embedActivityMode == 2) {
                roundedCorner = 5;
            } else if (embedActivityMode == 3) {
                roundedCorner = 10;
            }
        } else if (stagePosition == 16) {
            roundedCorner = 12;
        } else if (stagePosition == 64) {
            roundedCorner = 3;
        } else {
            if (stagePosition == 8) {
                roundedCorner = rotation != 1 ? 10 : 15;
            } else if (stagePosition == 32) {
                roundedCorner = rotation == 1 ? 5 : 15;
            } else {
                Log.e(TAG, "updateRoundedCornerForSplit: Invalid position 0x" + stagePosition);
                return;
            }
        }
        super.semSetRoundedCorners(roundedCorner, this.mMultiWindowRoundedCornerRadius);
        super.semSetRoundedCornerColor(roundedCorner, color);
    }

    private Rect getCurrentBounds(Context context) {
        if (this.mWm == null) {
            this.mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return this.mWm.getCurrentWindowMetrics().getBounds();
    }

    private void updateRoundedCornerForMultiSplit(Context context) {
        int roundedCorner = 15;
        if (CoreRune.MW_EMBED_ACTIVITY) {
            Configuration config = getConfiguration();
            boolean isEmbedded = config.windowConfiguration.isEmbedded();
            if (isEmbedded) {
                int embedActivityMode = config.windowConfiguration.getEmbedActivityMode();
                if (embedActivityMode == 2) {
                    roundedCorner = 5;
                } else if (embedActivityMode == 3) {
                    roundedCorner = 10;
                }
            }
        }
        super.semSetRoundedCorners(roundedCorner, this.mMultiWindowRoundedCornerRadius);
        super.semSetRoundedCornerColor(
                roundedCorner, MultiWindowUtils.getRoundedCornerColor(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float dpToPixel(float dp) {
        return (this.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f) * dp;
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
            this.mPreventPopOverElevation =
                    getResources().getConfiguration().windowConfiguration.isPopOver()
                            && (this.mIsDexEnabled || isFullscreenMode() || isSplitMode())
                            && (!isActivity() || (!isFullSize() && isDimBehind()));
        }
        return this.mPreventPopOverElevation;
    }

    private boolean isDimBehind() {
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        return (attrs.flags & 2) != 0 && attrs.dimAmount > 0.0f && attrs.dimAmount < 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBackgroundAlpha(float alpha) {
        if (this.mPopOverBackgroundAlpha != alpha) {
            this.mPopOverBackgroundAlpha = alpha;
            Log.d(TAG, "changed bg alpha=" + alpha);
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getBackgroundAlpha() {
        return this.mPopOverBackgroundAlpha;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentAlpha(float alpha) {
        if (this.mPopOverContentAlpha != alpha) {
            this.mPopOverContentAlpha = alpha;
            Log.d(TAG, "changed content alpha=" + alpha);
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getContentAlpha() {
        return this.mPopOverContentAlpha;
    }

    private void showPopOver() {
        ObjectAnimator animBackground =
                ObjectAnimator.ofFloat(this, this.POP_OVER_BACKGROUND_ALPHA, 1.0f);
        animBackground.setDuration(200L);
        ObjectAnimator animContent =
                ObjectAnimator.ofFloat(this, this.POP_OVER_CONTENT_ALPHA, 1.0f);
        animContent.setDuration(100L);
        animContent.setStartDelay(100L);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(animBackground, animContent);
        animSet.start();
    }

    private void hidePopOver() {
        ObjectAnimator animBackground =
                ObjectAnimator.ofFloat(this, this.POP_OVER_BACKGROUND_ALPHA, 0.2f);
        animBackground.setDuration(200L);
        animBackground.setStartDelay(100L);
        ObjectAnimator animContent =
                ObjectAnimator.ofFloat(this, this.POP_OVER_CONTENT_ALPHA, 0.0f);
        animContent.setDuration(100L);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(animContent, animBackground);
        animSet.start();
    }

    private static class ColorViewState {
        final ColorViewAttributes attributes;
        int color;
        boolean visible;
        View view = null;
        int targetVisibility = 4;
        boolean present = false;

        ColorViewState(ColorViewAttributes attributes) {
            this.attributes = attributes;
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

        private ColorViewAttributes(
                int translucentFlag,
                int verticalGravity,
                int horizontalGravity,
                int seascapeGravity,
                String transitionName,
                int id,
                int insetsType) {
            this.id = id;
            this.translucentFlag = translucentFlag;
            this.verticalGravity = verticalGravity;
            this.horizontalGravity = horizontalGravity;
            this.seascapeGravity = seascapeGravity;
            this.transitionName = transitionName;
            this.insetsType = insetsType;
        }

        public boolean isPresent(boolean requestedVisible, int windowFlags, boolean force) {
            return requestedVisible && ((Integer.MIN_VALUE & windowFlags) != 0 || force);
        }

        public boolean isVisible(boolean present, int color, int windowFlags, boolean force) {
            return present
                    && Color.alpha(color) != 0
                    && ((this.translucentFlag & windowFlags) == 0 || force);
        }

        public boolean isVisible(
                int requestedVisibleTypes, int color, int windowFlags, boolean force) {
            boolean requestedVisible = (this.insetsType & requestedVisibleTypes) != 0;
            boolean present = isPresent(requestedVisible, windowFlags, force);
            return isVisible(present, color, windowFlags, force);
        }
    }

    private class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback wrapped) {
            this.mWrapped = wrapped;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onCreateActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            DecorView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (mode == DecorView.this.mFloatingActionMode
                    && DecorView.this.mFloatingToolbar.isDiscardTouch()) {
                return true;
            }
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            boolean isPrimary;
            boolean isFloating;
            this.mWrapped.onDestroyActionMode(mode);
            boolean isMncApp = DecorView.this.mContext.getApplicationInfo().targetSdkVersion >= 23;
            if (isMncApp) {
                isPrimary = mode == DecorView.this.mPrimaryActionMode;
                isFloating = mode == DecorView.this.mFloatingActionMode;
                if (!isPrimary && mode.getType() == 0) {
                    Log.e(
                            DecorView.this.mLogTag,
                            "Destroying unexpected ActionMode instance of TYPE_PRIMARY; "
                                    + mode
                                    + " was not the current primary action mode! Expected "
                                    + DecorView.this.mPrimaryActionMode);
                }
                if (!isFloating && mode.getType() == 1) {
                    Log.e(
                            DecorView.this.mLogTag,
                            "Destroying unexpected ActionMode instance of TYPE_FLOATING; "
                                    + mode
                                    + " was not the current floating action mode! Expected "
                                    + DecorView.this.mFloatingActionMode);
                }
            } else {
                isPrimary = mode.getType() == 0;
                isFloating = mode.getType() == 1;
            }
            if (isPrimary) {
                if (DecorView.this.mPrimaryActionModePopup != null) {
                    DecorView.this.removeCallbacks(DecorView.this.mShowPrimaryActionModePopup);
                }
                if (DecorView.this.mPrimaryActionModeView != null) {
                    DecorView.this.endOnGoingFadeAnimation();
                    final ActionBarContextView lastActionModeView =
                            DecorView.this.mPrimaryActionModeView;
                    DecorView.this.mFadeAnim =
                            ObjectAnimator.ofFloat(
                                    DecorView.this.mPrimaryActionModeView,
                                    (Property<ActionBarContextView, Float>) View.ALPHA,
                                    1.0f,
                                    0.0f);
                    DecorView.this.mFadeAnim.addListener(
                            new Animator
                                    .AnimatorListener() { // from class:
                                                          // com.android.internal.policy.DecorView.ActionModeCallback2Wrapper.1
                                @Override // android.animation.Animator.AnimatorListener
                                public void onAnimationStart(Animator animation) {}

                                @Override // android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animation) {
                                    if (lastActionModeView
                                            == DecorView.this.mPrimaryActionModeView) {
                                        lastActionModeView.setVisibility(8);
                                        if (DecorView.this.mPrimaryActionModePopup != null) {
                                            DecorView.this.mPrimaryActionModePopup.dismiss();
                                        }
                                        lastActionModeView.killMode();
                                        DecorView.this.mFadeAnim = null;
                                        DecorView.this.requestApplyInsets();
                                    }
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public void onAnimationCancel(Animator animation) {}

                                @Override // android.animation.Animator.AnimatorListener
                                public void onAnimationRepeat(Animator animation) {}
                            });
                    DecorView.this.mFadeAnim.start();
                }
                DecorView.this.mPrimaryActionMode = null;
            } else if (isFloating) {
                if (DecorView.this.mFloatingToolbar != null) {
                    DecorView.this.mFloatingToolbar.setIsMovingStarted(false);
                }
                DecorView.this.cleanupFloatingActionModeViews();
                DecorView.this.mFloatingActionMode = null;
            }
            if (DecorView.this.mWindow.getCallback() != null
                    && !DecorView.this.mWindow.isDestroyed()) {
                try {
                    DecorView.this.mWindow.getCallback().onActionModeFinished(mode);
                } catch (AbstractMethodError e) {
                }
            }
            DecorView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (this.mWrapped instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) this.mWrapped).onGetContentRect(mode, view, outRect);
            } else {
                super.onGetContentRect(mode, view, outRect);
            }
        }
    }

    public void setDisplayCutoutBackgroundColor(int color) {
        this.mCalledDisplayCutoutBackgroundColor = true;
        this.mDisplayCutoutBackgroundColor = color;
        requestApplyInsets();
    }

    private void updateDisplayCutoutBackground(WindowInsets insets) {
        if (!this.mCalledDisplayCutoutBackgroundColor
                && (View.sIsSamsungBasicInteraction || View.sIsDisplayCutoutBackground)) {
            this.mDisplayCutoutBackgroundColor = getCurrentColor(this.mNavigationColorViewState);
        }
        if (insets == null && this.mDisplayCutoutBackgroundView != null) {
            this.mDisplayCutoutBackgroundView.setBackgroundColor(
                    this.mDisplayCutoutBackgroundColor);
            return;
        }
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        if ((attrs.layoutInDisplayCutoutMode == 1 || attrs.layoutInDisplayCutoutMode == 3)
                && insets != null) {
            if (insets.getDisplayCutout() != null && insets.hasSystemWindowInsets()) {
                DisplayCutout cutout = insets.getDisplayCutout();
                int leftCutout = cutout.getSafeInsetLeft();
                int topCutout = cutout.getSafeInsetTop();
                int rightCutout = cutout.getSafeInsetRight();
                int bottomCutout = cutout.getSafeInsetBottom();
                sKnoxBadgeRightCutout = rightCutout;
                boolean needBackground = false;
                int gravity = 0;
                int width = 0;
                int height = 0;
                int topMargin = 0;
                int bottomMargin = 0;
                if (leftCutout + rightCutout != 0) {
                    if (leftCutout > 0 && insets.getSystemWindowInsetLeft() > 0) {
                        needBackground = true;
                        gravity = 3;
                        width = leftCutout;
                        height = -1;
                        View statusBg =
                                this.mStatusColorViewState.visible
                                        ? this.mStatusColorViewState.view
                                        : null;
                        if (statusBg != null && statusBg.getVisibility() == 0) {
                            topMargin = statusBg.getLayoutParams().height;
                        }
                        View naviBg =
                                this.mNavigationColorViewState.visible
                                        ? this.mNavigationColorViewState.view
                                        : null;
                        if (naviBg != null && naviBg.getVisibility() == 0) {
                            if (naviBg.getLayoutParams().height != -1) {
                                bottomMargin = naviBg.getLayoutParams().height;
                            }
                        }
                    } else if (rightCutout > 0 && insets.getSystemWindowInsetRight() > 0) {
                        needBackground = true;
                        gravity = 5;
                        width = rightCutout;
                        height = -1;
                        View statusBg2 =
                                this.mStatusColorViewState.visible
                                        ? this.mStatusColorViewState.view
                                        : null;
                        if (statusBg2 != null && statusBg2.getVisibility() == 0) {
                            topMargin = statusBg2.getLayoutParams().height;
                        }
                    } else if (topCutout > 0 && insets.getSystemWindowInsetTop() > 0) {
                        needBackground = true;
                        gravity = 48;
                        width = -1;
                        height = topCutout;
                    } else if (bottomCutout > 0 && insets.getSystemWindowInsetBottom() > 0) {
                        needBackground = true;
                        gravity = 80;
                        width = -1;
                        height = bottomCutout;
                    }
                }
                if (needBackground && this.mDisplayCutoutBackgroundColor != 0) {
                    if (this.mDisplayCutoutBackgroundView == null) {
                        this.mDisplayCutoutBackgroundView = new View(getContext());
                        addView(this.mDisplayCutoutBackgroundView);
                    } else if (this.mDisplayCutoutBackgroundView.getParent() != this) {
                        this.mDisplayCutoutBackgroundView = new View(getContext());
                        addView(this.mDisplayCutoutBackgroundView);
                    }
                    if (this.mDisplayCutoutBackgroundView.getTag() == null) {
                        this.mDisplayCutoutBackgroundView.setTag("DisplayCutoutBackgroundView");
                        this.mDisplayCutoutBackgroundView.setElevation(-1.0f);
                    }
                    FrameLayout.LayoutParams param =
                            (FrameLayout.LayoutParams)
                                    this.mDisplayCutoutBackgroundView.getLayoutParams();
                    param.gravity = gravity;
                    param.width = width;
                    param.height = height;
                    param.topMargin = topMargin;
                    param.bottomMargin = bottomMargin;
                    this.mDisplayCutoutBackgroundView.setBackgroundColor(
                            this.mDisplayCutoutBackgroundColor);
                    this.mDisplayCutoutBackgroundView.requestLayout();
                    return;
                }
                if (this.mDisplayCutoutBackgroundView != null
                        && this.mDisplayCutoutBackgroundView.getParent() == this) {
                    removeView(this.mDisplayCutoutBackgroundView);
                    this.mDisplayCutoutBackgroundView = null;
                    return;
                }
                return;
            }
        }
        if (this.mDisplayCutoutBackgroundView != null) {
            removeView(this.mDisplayCutoutBackgroundView);
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
        if (this.mKnoxBadgeViewGroupOverlay != null) {
            this.mKnoxBadgeViewGroupOverlay.remove(this.mKnoxBadgeView);
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
        this.mKnoxBadge =
                this.mPackageManagerForKnoxBadge.getUserBadgeForDensity(
                        new UserHandle(this.mUserId), 0);
        this.mReverseKnoxBadge =
                SemPersonaManager.getCustomReverseBadgeForCustomContainer(
                        new UserHandle(this.mUserId), 0, this.mContext);
        this.mReverseKnoxBadge =
                this.mReverseKnoxBadge == null ? this.mKnoxBadge : this.mReverseKnoxBadge;
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
    public boolean shouldHideProfileBadge(
            boolean isGestureHintOff, boolean taskbarEnabled, int displayType) {
        if (((getParent() instanceof ViewGroup) && this.mKnoxBadgeView != null)
                || isPopOverState()) {
            return true;
        }
        if (isGestureHintOff) {
            if (displayType != 0) {
                return true;
            }
            if (!taskbarEnabled && displayType == 0) {
                return true;
            }
        }
        if (isFullscreenMode() && isImmersiveMode()) {
            return true;
        }
        return isSplitMode() && MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
    }

    private void setKnoxBadgePosition() {
        this.mKnoxBadgeDisplayRunnable =
                new Runnable() { // from class: com.android.internal.policy.DecorView.11
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean isLargeDisplay = true;
                        boolean gestureNavBarEnabled =
                                Settings.Global.getInt(
                                                DecorView.this.mContext.getContentResolver(),
                                                Settings.Global.NAVIGATION_BAR_GESTURE_WHILE_HIDDEN,
                                                0)
                                        != 0;
                        boolean gestureHintState =
                                Settings.Global.getInt(
                                                DecorView.this.mContext.getContentResolver(),
                                                Settings.Global.NAVIGATIONBAR_GESTURE_HINT,
                                                1)
                                        != 0;
                        boolean isGestureNavBarInCenter =
                                gestureNavBarEnabled
                                        && Settings.Global.getInt(
                                                        DecorView.this.mContext
                                                                .getContentResolver(),
                                                        Settings.Global
                                                                .NAVIGATIONBAR_GESTURES_DETAIL_TYPE,
                                                        0)
                                                == 1;
                        boolean isGestureHintOff = gestureNavBarEnabled && !gestureHintState;
                        boolean taskbarEnabled =
                                Settings.Global.getInt(
                                                DecorView.this.getContext().getContentResolver(),
                                                Settings.Global.SEM_TASK_BAR,
                                                0)
                                        == 1;
                        int displayType =
                                DecorView.this
                                        .getContext()
                                        .getResources()
                                        .getConfiguration()
                                        .semDisplayDeviceType;
                        if (DecorView.this.shouldHideProfileBadge(
                                isGestureHintOff, taskbarEnabled, displayType)) {
                            DecorView.this.hideKnoxBadge();
                            return;
                        }
                        int rotation = DecorView.this.mWm.getDefaultDisplay().getRotation();
                        boolean isMultiWindow = DecorView.this.getWindowingMode() == 6;
                        boolean isRotation_90 = rotation == 1;
                        boolean isRotation_270 = rotation == 3;
                        if (displayType != 0 && !CoreRune.IS_TABLET_DEVICE) {
                            isLargeDisplay = false;
                        }
                        if (DecorView.this.mIsDexEnabled) {
                            Context candiateContext =
                                    DecorView.this.getContext().getApplicationContext();
                            int appDensityDpi =
                                    candiateContext.getResources().getConfiguration().densityDpi;
                            int densityDpi =
                                    DecorView.this
                                            .getContext()
                                            .getResources()
                                            .getConfiguration()
                                            .densityDpi;
                            DecorView.this.mDensityRatio = densityDpi / appDensityDpi;
                        }
                        int badgeW =
                                (int)
                                        (DecorView.this.mKnoxBadge.getIntrinsicWidth()
                                                * DecorView.this.mDensityRatio);
                        int badgeH =
                                (int)
                                        (DecorView.this.mKnoxBadge.getIntrinsicHeight()
                                                * DecorView.this.mDensityRatio);
                        int navigation_bar_height = 0;
                        WindowInsets rootInsets = DecorView.this.getRootWindowInsets();
                        if (rootInsets != null) {
                            DecorView.this.mKnoxBadgeInsets =
                                    rootInsets.getInsetsIgnoringVisibility(
                                            WindowInsets.Type.systemBars()
                                                    | WindowInsets.Type.displayCutout());
                            DisplayCutout cutout = rootInsets.getDisplayCutout();
                            if (cutout != null) {
                                DecorView.sKnoxBadgeRightCutout = cutout.getSafeInsetRight();
                            }
                        }
                        if (DecorView.this.mKnoxBadgeInsets != null) {
                            navigation_bar_height =
                                    DecorView.getNavBarSizeForBadge(
                                            DecorView.this.mKnoxBadgeInsets.left,
                                            DecorView.this.mKnoxBadgeInsets.right,
                                            DecorView.this.mKnoxBadgeInsets.bottom);
                        }
                        DecorView.this.addKnoxBadge();
                        if (isLargeDisplay) {
                            DecorView.this.mKnoxBadgeStartX =
                                    DecorView.this.mKnoxLayoutRight - badgeW;
                            DecorView.this.mKnoxBadgeStartY =
                                    (DecorView.this.mKnoxLayoutBottom - badgeH)
                                            - navigation_bar_height;
                            if (isRotation_270) {
                                DecorView.this.mKnoxBadgeStartX -= DecorView.sKnoxBadgeRightCutout;
                            }
                            DecorView.this.mKnoxBadgeView.setBackgroundDrawable(
                                    DecorView.this.mKnoxBadge);
                        } else {
                            int position =
                                    DecorView.this
                                            .getResources()
                                            .getConfiguration()
                                            .windowConfiguration
                                            .getStagePosition();
                            if (isRotation_270) {
                                if (isGestureNavBarInCenter) {
                                    DecorView.this.mKnoxBadgeStartX =
                                            DecorView.this.mKnoxLayoutLeft;
                                    DecorView.this.mKnoxBadgeStartY =
                                            (DecorView.this.mKnoxLayoutBottom - badgeH)
                                                    - navigation_bar_height;
                                } else {
                                    DecorView.this.mKnoxBadgeStartX =
                                            DecorView.this.mKnoxLayoutLeft + navigation_bar_height;
                                    DecorView.this.mKnoxBadgeStartY =
                                            DecorView.this.mKnoxLayoutBottom - badgeH;
                                }
                                if (isMultiWindow && position == 32) {
                                    DecorView.this.mKnoxBadgeStartX = 0;
                                }
                                DecorView.this.mKnoxBadgeView.setBackgroundDrawable(
                                        DecorView.this.mReverseKnoxBadge);
                            } else if (!isRotation_90) {
                                DecorView.this.mKnoxBadgeStartX =
                                        DecorView.this.mKnoxLayoutRight - badgeW;
                                DecorView.this.mKnoxBadgeStartY =
                                        (DecorView.this.mKnoxLayoutBottom - badgeH)
                                                - navigation_bar_height;
                                DecorView.this.mKnoxBadgeView.setBackgroundDrawable(
                                        DecorView.this.mKnoxBadge);
                            } else {
                                if (isGestureNavBarInCenter) {
                                    DecorView.this.mKnoxBadgeStartX =
                                            DecorView.this.mKnoxLayoutRight - badgeW;
                                    DecorView.this.mKnoxBadgeStartY =
                                            (DecorView.this.mKnoxLayoutBottom - badgeH)
                                                    - navigation_bar_height;
                                } else {
                                    DecorView.this.mKnoxBadgeStartX =
                                            (DecorView.this.mKnoxLayoutRight - badgeW)
                                                    - navigation_bar_height;
                                    DecorView.this.mKnoxBadgeStartY =
                                            DecorView.this.mKnoxLayoutBottom - badgeH;
                                }
                                if (isMultiWindow && position == 8) {
                                    DecorView.this.mKnoxBadgeStartX =
                                            DecorView.this.mKnoxLayoutRight - badgeW;
                                }
                                DecorView.this.mKnoxBadgeView.setBackgroundDrawable(
                                        DecorView.this.mKnoxBadge);
                            }
                        }
                        int finishX = DecorView.this.mKnoxBadgeStartX + badgeW;
                        int finishY = DecorView.this.mKnoxBadgeStartY + badgeH;
                        DecorView.this.mKnoxBadgeView.setLeft(DecorView.this.mKnoxBadgeStartX);
                        DecorView.this.mKnoxBadgeView.setTop(DecorView.this.mKnoxBadgeStartY);
                        DecorView.this.mKnoxBadgeView.setRight(finishX);
                        DecorView.this.mKnoxBadgeView.setBottom(finishY);
                    }
                };
    }

    public static int getNavBarSizeForBadge(int leftInset, int rightInset, int bottomInset) {
        return isNavBarToRightEdge(bottomInset, rightInset - sKnoxBadgeRightCutout)
                ? rightInset
                : isNavBarToLeftEdge(bottomInset, leftInset) ? leftInset : bottomInset;
    }

    public void setLastBackgroundResource(int redId) {
        this.mLastBackgroundResource = redId;
    }

    @Override // android.view.View
    public int getLastBackgroundResource() {
        return this.mLastBackgroundResource;
    }

    private boolean shouldConsumeCaptionBar() {
        if (this.mLastTopInset > 0
                && this.mLastTopInset == this.mLastCaptionHeight
                && CoreRune.MW_CAPTION_SHELL_DEX
                && this.mIsDexEnabled
                && !this.mWindow.isTranslucent()) {
            return (isFullscreenMode() || isSplitMode()) && !isImmersiveMode();
        }
        return false;
    }

    public boolean shouldConsumeCaptionInsets(WindowInsets insets) {
        if (!insets.isForceConsumingOpaqueCaptionBar()) {
            return false;
        }
        WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
        if (!this.mWindow.getAttributes().isFullscreen() || this.mWindow.mIsFloating) {
            return attrs.type == 1 || attrs.type == 2 || attrs.type == 4;
        }
        return false;
    }

    public void drawFrameIfNeeded(Canvas canvas) {
        WindowInsetsController insetsController;
        if (!isFreeformMode()
                || !isFullSize()
                || this.mWindow.mIsFloating
                || (getParent() instanceof ViewGroup)) {
            return;
        }
        if (this.mFrameDrawHelper == null) {
            this.mFrameDrawHelper = new FrameDrawHelper(this);
        }
        this.mFrameDrawHelper.updateResources(isActivity() ? getConfiguration() : null);
        int captionHeight = this.mLastCaptionHeight;
        if (CoreRune.MW_CAPTION_SHELL_CUSTOMIZABLE_WINDOW_HEADERS
                && (insetsController = getWindowInsetsController()) != null
                && (insetsController.getSystemBarsAppearance() & 128) != 0) {
            captionHeight = 0;
        }
        this.mFrameDrawHelper.drawFrame(canvas, captionHeight);
    }

    public static float getDensity(View view) {
        Configuration config = view.getResources().getConfiguration();
        Context context = view.getContext();
        float densityDpi = config.densityDpi;
        if (config.isDesktopModeEnabled()) {
            densityDpi = 160.0f;
            SemDesktopModeManager desktopModeManager =
                    (SemDesktopModeManager)
                            context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
            if (desktopModeManager != null
                    && desktopModeManager.getDesktopModeState().getDisplayType() == 101) {
                densityDpi = 280.0f;
            }
            Log.i(TAG, "updateDisplayMetrics: isDexEnabled=true, densityDpi=" + densityDpi);
        }
        float density = densityDpi / 160.0f;
        try {
            ActivityThread.currentActivityThread();
            String packageName = context.getPackageName();
            Log.i(TAG, "updateDisplayMetrics: packageName=" + packageName + ", dsf=1.0");
            if (1.0f != 1.0f) {
                return Math.round((density * 1.0f) * 10000.0f) / 10000.0f;
            }
            return density;
        } catch (Exception e) {
            Log.e(TAG, "updateDisplayMetrics: error while getting dsf. e=" + e);
            return density;
        }
    }

    public void updateCaptionHeightIfNeeded(WindowInsets insets) {
        int captionHeight = insets.getInsetsIgnoringVisibility(WindowInsets.Type.captionBar()).top;
        if (captionHeight != 0) {
            this.mLastCaptionHeight = captionHeight;
            requestInvalidateRenderNode("updateCaptionHeightIfNeeded");
        }
    }

    private void requestInvalidateRenderNode(String msg) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestInvalidateRootRenderNode();
            if (DEBUG_DRAW) {
                Log.i(TAG, "requestInvalidateRootRenderNode: msg=" + msg);
            }
        }
    }

    private void updateOutlineProvider() {
        if (isActivity()) {
            if (this.mIsPopOver && (!this.mIsPopOverWithoutOutlineEffect || !isDialogInPopOver())) {
                setOutlineProvider(this.POP_OVER_OUTLINE_PROVIDER);
                return;
            }
            if (isSplitMode() || this.mWindowingMode == 5) {
                boolean needInvalidate = false;
                if (!this.mIsPopOver && getOutlineProvider() == this.POP_OVER_OUTLINE_PROVIDER) {
                    needInvalidate = true;
                    setOutlineProvider(null);
                }
                if (this.mWindowingMode == 5 || needInvalidate) {
                    requestInvalidateRenderNode("updateOutlineProvider");
                    return;
                }
                return;
            }
            if (!this.mIsPopOver && getOutlineProvider() == this.POP_OVER_OUTLINE_PROVIDER) {
                setOutlineProvider(null);
                requestInvalidateRenderNode("updateOutlineProvider");
                return;
            } else {
                if (getOutlineProvider() != this.mLastOutlineProvider) {
                    setOutlineProvider(this.mLastOutlineProvider);
                    return;
                }
                return;
            }
        }
        if (this.mIsPopOver && getContext().getActivityToken() != null) {
            setOutlineProvider(this.POP_OVER_OUTLINE_PROVIDER);
        }
    }

    private boolean isActivityHomeOrRecent() {
        switch (getResources().getConfiguration().windowConfiguration.getActivityType()) {
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public void releaseActivityFocusIfNeeded() {
        Window.Callback cb = this.mWindow.getCallback();
        if (this.mStayFocus && cb != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            cb.onWindowFocusChanged(false);
        }
        this.mStayFocus = false;
    }

    public void onWindowingModeChanged(int windowingMode, boolean split) {
        this.mWindowingMode = windowingMode;
        boolean updateWindowFormat = false;
        int oldDisplayDeviceType = this.mLastDisplayDeviceType;
        this.mLastDisplayDeviceType = getResources().getConfiguration().semDisplayDeviceType;
        if (this.mLastDisplayDeviceType != oldDisplayDeviceType) {
            updateWindowFormat = true;
        }
        WindowConfiguration winConfig = getResources().getConfiguration().windowConfiguration;
        boolean isPopPover = winConfig.isPopOver();
        if (this.mIsPopOver != isPopPover) {
            this.mIsPopOver = isPopPover;
            updateWindowFormat = true;
        }
        boolean isPopOverWithoutOutlineEffect = winConfig.isPopOverWithoutOutlineEffect();
        if (this.mIsPopOverWithoutOutlineEffect != isPopOverWithoutOutlineEffect) {
            this.mIsPopOverWithoutOutlineEffect = isPopOverWithoutOutlineEffect;
            updateWindowFormat = true;
        }
        if (updateWindowFormat) {
            drawableChanged();
            updateOutlineProvider();
        }
        requestInvalidateRenderNode("window_mode_changed");
    }

    public void onDexTaskDockingChanged(int state) {
        if (this.mLastDockingState != state) {
            this.mLastDockingState = state;
            requestInvalidateRenderNode("Dex docking state Changed");
        }
    }

    public int getDexTaskDockingState() {
        return this.mLastDockingState;
    }

    public void hidden_semSetForceHideRoundedCorner(boolean hide) {
        this.mForceHideRoundedCorner = hide;
        Log.i(TAG, "hidden_semSetForceHideRoundedCorner() : " + hide);
        super.semSetRoundedCorners(0);
    }

    private int getCaptionType() {
        if (this.mIsDexEnabled) {
            return 1;
        }
        return Settings.Global.getInt(
                this.mContext.getContentResolver(), Settings.Global.FREEFORM_CAPTION_TYPE, 0);
    }
}
