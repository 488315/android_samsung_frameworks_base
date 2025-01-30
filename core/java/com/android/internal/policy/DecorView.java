package com.android.internal.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.p002pm.PackageManager;
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
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.opengl.GLES30;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
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
import android.view.SurfaceHolder;
import android.view.ThreadedRenderer;
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
import com.android.internal.C4337R;
import com.android.internal.app.ChooserActivity;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.StandaloneActionMode;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.BackgroundFallback;
import com.android.internal.widget.DecorCaptionView;
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

/* loaded from: classes5.dex */
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
  private static final int POP_OVER_ELEVATION_IN_DIP = 32;
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
  private final ViewOutlineProvider FREEFORM_OUTLINE_PROVIDER;
  private final FloatProperty<DecorView> POP_OVER_BACKGROUND_ALPHA;
  private final FloatProperty<DecorView> POP_OVER_CONTENT_ALPHA;
  private final ViewOutlineProvider POP_OVER_OUTLINE_PROVIDER;
  private boolean mAllowUpdateElevation;
  private boolean mApplyFloatingHorizontalInsets;
  private boolean mApplyFloatingVerticalInsets;
  private BackdropFrameRenderer mBackdropFrameRenderer;
  private BackgroundBlurDrawable mBackgroundBlurDrawable;
  private final ViewTreeObserver.OnPreDrawListener mBackgroundBlurOnPreDrawListener;
  private int mBackgroundBlurRadius;
  private final BackgroundFallback mBackgroundFallback;
  private Insets mBackgroundInsets;
  private final Rect mBackgroundPadding;
  private final int mBarEnterExitDuration;
  private boolean mCalledDisplayCutoutBackgroundColor;
  private Drawable mCaptionBackgroundDrawable;
  private ColorDrawable mCaptionBarBackgroundDrawable;
  private boolean mChanging;
  ViewGroup mContentRoot;
  private boolean mCrossWindowBlurEnabled;
  private Consumer<Boolean> mCrossWindowBlurEnabledListener;
  private DecorCaptionView mDecorCaptionView;
  int mDefaultOpacity;
  private int mDensityForKnoxBadge;
  private float mDensityRatio;
  private int mDisplayCutoutBackgroundColor;
  private View mDisplayCutoutBackgroundView;
  private int mDisplayRotation;
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
  private boolean mHasCaption;
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
  private boolean mKeepCutoutInTentMode;
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
  private Drawable.Callback mLastBackgroundDrawableCb;
  private Insets mLastBackgroundInsets;
  private int mLastBackgroundResource;
  private int mLastBottomInset;
  private ColorDrawable mLastCaptionBarBackgroundDrawable;
  private int mLastCaptionHeight;
  private int mLastDisplayDeviceType;
  private int mLastDockingState;
  private boolean mLastDrawLegacyNavigationBarBackground;
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
  private Drawable mResizingBackgroundDrawable;
  private Drawable mReverseKnoxBadge;
  private int mRootScrollY;
  private int mRotation;
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
  private Region mTmpRegion;
  private Drawable mUserCaptionBackgroundDrawable;
  private int mUserId;
  private boolean mWatchingForMenu;
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

  DecorView(Context context, int featureId, PhoneWindow window, WindowManager.LayoutParams params) {
    super(context);
    boolean z;
    WindowConfiguration winConfig;
    boolean z2;
    int i;
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
    this.mHasCaption = false;
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
    this.mLastSuppressScrimTypes = 0;
    this.mRootScrollY = 0;
    this.mWindowResizeCallbacksAdded = false;
    this.mLastBackgroundDrawableCb = null;
    this.mBackdropFrameRenderer = null;
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
    this.mBackgroundBlurOnPreDrawListener =
        new ViewTreeObserver
            .OnPreDrawListener() { // from class:
                                   // com.android.internal.policy.DecorView$$ExternalSyntheticLambda0
          @Override // android.view.ViewTreeObserver.OnPreDrawListener
          public final boolean onPreDraw() {
            boolean lambda$new$0;
            lambda$new$0 = DecorView.this.lambda$new$0();
            return lambda$new$0;
          }
        };
    this.mWindowingMode = 0;
    this.mIsDexEnabled = false;
    this.mHasDisplayCutout = false;
    this.mForceRoundedCorner = false;
    this.mRoundedCornerMode = 0;
    this.mOverrideRoundedCornerBounds = new Rect();
    this.mTmpColorViewBounds = new Rect();
    this.mTmpRegion = new Region();
    this.mPopOverPaint = new Paint();
    this.mPopOverFramePaint = new Paint();
    this.mPopOverClipOutPath = new Path();
    this.mIsPopOverWithoutOutlineEffect = false;
    this.mPreventPopOverElevation = false;
    this.mShowPopOver = true;
    this.mPopOverBackgroundColor = -1;
    this.mPopOverBackgroundAlpha = 1.0f;
    this.mPopOverContentAlpha = 1.0f;
    this.mKeepCutoutInTentMode = false;
    this.POP_OVER_OUTLINE_PROVIDER =
        new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.2
          @Override // android.view.ViewOutlineProvider
          public void getOutline(View view, Outline outline) {
            Path path =
                SemViewUtils.getPopOverSmoothRoundedRect(
                    DecorView.this.mContext, view.getWidth(), view.getHeight());
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
    this.mLastBackgroundResource = 0;
    this.mDisplayCutoutBackgroundColor = 0;
    this.mCalledDisplayCutoutBackgroundColor = false;
    this.mForceHideRoundedCorner = false;
    this.FREEFORM_OUTLINE_PROVIDER =
        new ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.12
          @Override // android.view.ViewOutlineProvider
          public void getOutline(View view, Outline outline) {
            if (DecorView.this.isFreeformMode()) {
              int radius =
                  view.getResources()
                      .getDimensionPixelSize(C4337R.dimen.sem_decor_outline_radius_freeform);
              outline.setPath(
                  SemViewUtils.getSmoothRoundedRect(
                      view.getWidth(), view.getHeight(), 0, 0, radius));
            } else {
              outline.setRect(0, 0, view.getWidth(), view.getHeight());
            }
          }
        };
    this.mFeatureId = featureId;
    this.mShowInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
    this.mHideInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
    this.mBarEnterExitDuration =
        context.getResources().getInteger(C4337R.integer.dock_enter_exit_duration);
    if (!context.getResources().getBoolean(C4337R.bool.config_forceWindowDrawsStatusBarBackground)
        || params.type == 2011
        || context.getApplicationInfo().targetSdkVersion < 24) {
      z = false;
    } else {
      z = true;
    }
    this.mForceWindowDrawsBarBackgrounds = z;
    this.mSemiTransparentBarColor =
        context.getResources().getColor(C4337R.color.system_bar_background_semi_transparent, null);
    setWindow(window);
    updateLogTag(params);
    paint.setColor(this.mWindow.getDeviceDefaultNavigationBarColor());
    Resources res = context.getResources();
    if (this.mWindow.mActivityCurrentConfig != null) {
      winConfig = this.mWindow.mActivityCurrentConfig.windowConfiguration;
    } else {
      winConfig = res.getConfiguration().windowConfiguration;
    }
    this.mWindowingMode = winConfig.getWindowingMode();
    this.mIsPopOver = res.getConfiguration().windowConfiguration.isPopOver();
    this.mLastDisplayDeviceType = res.getConfiguration().semDisplayDeviceType;
    if (winConfig.isPopOverWithoutOutlineEffect()) {
      this.mIsPopOverWithoutOutlineEffect = true;
    }
    if (this.mIsPopOver && !this.mIsPopOverWithoutOutlineEffect) {
      if (!(this.mWindow.getContext() instanceof ChooserActivity)) {
        if (this.mPopOverBackgroundColor == -1) {
          if (SemViewUtils.isLightTheme(this.mContext)) {
            i = C4337R.color.sem_app_bar_bg_color;
          } else {
            i = C4337R.color.sem_app_bar_bg_color_dark;
          }
          this.mPopOverBackgroundColor = res.getColor(i, null);
        }
      } else {
        this.mPopOverBackgroundColor = res.getColor(C4337R.color.sem_resolver_bg_color);
      }
      Log.m98i(TAG, "mPopOverBackgroundColor=" + Integer.toHexString(this.mPopOverBackgroundColor));
    }
    this.mIsDexEnabled = res.getConfiguration().isDesktopModeEnabled();
    this.mIsShowNavigationBar = res.getBoolean(C4337R.bool.config_showNavigationBar);
    try {
      int resId = res.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
      String spec = resId > 0 ? res.getString(resId) : null;
      if (spec == null || TextUtils.isEmpty(spec)) {
        z2 = false;
      } else {
        z2 = true;
      }
      this.mHasDisplayCutout = z2;
      if (!z2) {
        int subResId = res.getIdentifier("config_subBuiltInDisplayCutout", "string", "android");
        this.mHasDisplayCutout =
            TextUtils.isEmpty(subResId > 0 ? res.getString(subResId) : null) ? false : true;
      }
    } catch (Exception e) {
      Log.m102w(this.mLogTag, "Can not update hasDisplayCutout. " + e.toString());
    }
    this.mRoundedCornerRadius = res.getDimensionPixelSize(C4337R.dimen.sem_rounded_corner_radius);
    this.mRoundedCornerRadiusForLetterBox =
        res.getDimensionPixelSize(C4337R.dimen.rounded_corner_radius_for_letterbox);
    this.mMultiWindowRoundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(context);
    if (CoreRune.MW_CAPTION_SHELL_DEX) {
      this.mIsDexEnabled = this.mContext.getResources().getConfiguration().isDesktopModeEnabled();
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

  @Override // android.view.ViewGroup, android.view.View
  public boolean gatherTransparentRegion(Region region) {
    boolean statusOpaque = gatherTransparentRegion(this.mStatusColorViewState, region);
    boolean navOpaque = gatherTransparentRegion(this.mNavigationColorViewState, region);
    boolean decorOpaque = super.gatherTransparentRegion(region);
    if (this.mKnoxBadgeViewGroupOverlay != null) {
      region.m20op(
          this.mKnoxBadgeStartX,
          this.mKnoxBadgeStartY,
          getWidth(),
          getHeight(),
          Region.EnumC0836Op.DIFFERENCE);
    }
    if (decorOpaque && isFreeformMode()) {
      getRoundedCornerRegion(this.mTmpRegion);
      if (!this.mTmpRegion.isEmpty()) {
        region.m23op(this.mTmpRegion, Region.EnumC0836Op.DIFFERENCE);
      }
    }
    return statusOpaque || navOpaque || decorOpaque;
  }

  boolean gatherTransparentRegion(ColorViewState colorViewState, Region region) {
    if (colorViewState.view != null && colorViewState.visible && isResizing()) {
      return colorViewState.view.gatherTransparentRegion(region);
    }
    return false;
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
      if (this.mWindow.mPreparedPanel != null && this.mWindow.mPreparedPanel.isOpen) {
        PhoneWindow phoneWindow = this.mWindow;
        if (phoneWindow.performPanelShortcut(phoneWindow.mPreparedPanel, keyCode, event, 0)) {
          return true;
        }
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
      PhoneWindow phoneWindow = this.mWindow;
      boolean handled =
          phoneWindow.performPanelShortcut(phoneWindow.mPreparedPanel, ev.getKeyCode(), ev, 1);
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
      ActionMode actionMode = this.mPrimaryActionMode;
      if (actionMode != null) {
        if (action == 1) {
          actionMode.finish();
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
    if (this.mHasCaption && isShowingCaption() && action == 0) {
      int x = (int) event.getX();
      int y = (int) event.getY();
      if (isOutOfInnerBounds(x, y)) {
        return true;
      }
    }
    int x2 = this.mFeatureId;
    if (x2 >= 0 && action == 0) {
      int x3 = (int) event.getX();
      int y2 = (int) event.getY();
      if (isOutOfBounds(x3, y2)) {
        this.mWindow.closePanel(this.mFeatureId);
        return true;
      }
      return false;
    }
    return false;
  }

  @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
  public void sendAccessibilityEvent(int eventType) {
    if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
      return;
    }
    int i = this.mFeatureId;
    if ((i == 0 || i == 6 || i == 2 || i == 5) && getChildCount() == 1) {
      getChildAt(0).sendAccessibilityEvent(eventType);
    } else {
      super.sendAccessibilityEvent(eventType);
    }
  }

  @Override // android.view.ViewGroup, android.view.View
  public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
    Window.Callback cb = this.mWindow.getCallback();
    if (cb != null && !this.mWindow.isDestroyed() && cb.dispatchPopulateAccessibilityEvent(event)) {
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
  /* JADX WARN: Removed duplicated region for block: B:36:0x0106  */
  /* JADX WARN: Removed duplicated region for block: B:39:0x0128  */
  /* JADX WARN: Removed duplicated region for block: B:42:0x013d  */
  /* JADX WARN: Removed duplicated region for block: B:51:0x012b  */
  /* JADX WARN: Removed duplicated region for block: B:53:0x015f  */
  /* JADX WARN: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
  @Override // android.widget.FrameLayout, android.view.View
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthMeasureSpec2;
    int heightMeasureSpec2;
    boolean measure;
    int currSmallestScreenWidthDp;
    TypedValue tv;
    int min;
    TypedValue tvh;
    int h;
    int w;
    Resources res = getContext().getResources();
    DisplayMetrics metrics = res.getDisplayMetrics();
    boolean isPortrait = getResources().getConfiguration().orientation == 1;
    int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
    boolean fixedWidth = false;
    this.mApplyFloatingHorizontalInsets = false;
    if (widthMode == Integer.MIN_VALUE) {
      PhoneWindow phoneWindow = this.mWindow;
      TypedValue tvw = isPortrait ? phoneWindow.mFixedWidthMinor : phoneWindow.mFixedWidthMajor;
      if (tvw != null && tvw.type != 0) {
        if (tvw.type == 5) {
          w = (int) tvw.getDimension(metrics);
        } else {
          int w2 = tvw.type;
          if (w2 == 6) {
            w = (int) tvw.getFraction(metrics.widthPixels, metrics.widthPixels);
          } else {
            w = 0;
          }
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (w <= 0) {
          widthMeasureSpec2 =
              View.MeasureSpec.makeMeasureSpec(
                  (widthSize - this.mFloatingInsets.left) - this.mFloatingInsets.right,
                  Integer.MIN_VALUE);
          this.mApplyFloatingHorizontalInsets = true;
        } else {
          widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min(w, widthSize), 1073741824);
          fixedWidth = true;
        }
        this.mApplyFloatingVerticalInsets = false;
        if (heightMode == Integer.MIN_VALUE) {
          if (isPortrait) {
            tvh = this.mWindow.mFixedHeightMajor;
          } else {
            tvh = this.mWindow.mFixedHeightMinor;
          }
          if (tvh != null && tvh.type != 0) {
            if (tvh.type == 5) {
              h = (int) tvh.getDimension(metrics);
            } else {
              int h2 = tvh.type;
              if (h2 == 6) {
                h = (int) tvh.getFraction(metrics.heightPixels, metrics.heightPixels);
              } else {
                h = 0;
              }
            }
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (h > 0) {
              heightMeasureSpec2 =
                  View.MeasureSpec.makeMeasureSpec(Math.min(h, heightSize), 1073741824);
            } else if ((this.mWindow.getAttributes().flags & 256) == 0) {
              heightMeasureSpec2 =
                  View.MeasureSpec.makeMeasureSpec(
                      (heightSize - this.mFloatingInsets.top) - this.mFloatingInsets.bottom,
                      Integer.MIN_VALUE);
              this.mApplyFloatingVerticalInsets = true;
            }
            super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
            int width = getMeasuredWidth();
            measure = false;
            int widthMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
            if (!fixedWidth && widthMode == Integer.MIN_VALUE) {
              currSmallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
              if (this.mLastSmallestScreenWidthDp != currSmallestScreenWidthDp) {
                Resources.Theme theme = getContext().getTheme();
                theme.resolveAttribute(16843607, this.mWindow.mMinWidthMinor, true);
                theme.resolveAttribute(16843606, this.mWindow.mMinWidthMajor, true);
                this.mLastSmallestScreenWidthDp = currSmallestScreenWidthDp;
              }
              PhoneWindow phoneWindow2 = this.mWindow;
              tv = !isPortrait ? phoneWindow2.mMinWidthMinor : phoneWindow2.mMinWidthMajor;
              float availableWidth =
                  TypedValue.applyDimension(1, res.getConfiguration().screenWidthDp, metrics);
              if (tv.type != 0) {
                if (tv.type == 5) {
                  min = (int) tv.getDimension(metrics);
                } else {
                  int min2 = tv.type;
                  if (min2 == 6) {
                    min = (int) tv.getFraction(availableWidth, availableWidth);
                  } else {
                    min = 0;
                  }
                }
                if (width < min) {
                  widthMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(min, 1073741824);
                  measure = true;
                }
              }
            }
            if (measure) {
              super.onMeasure(widthMeasureSpec3, heightMeasureSpec2);
              return;
            }
            return;
          }
        }
        heightMeasureSpec2 = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
        int width2 = getMeasuredWidth();
        measure = false;
        int widthMeasureSpec32 = View.MeasureSpec.makeMeasureSpec(width2, 1073741824);
        if (!fixedWidth) {
          currSmallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
          if (this.mLastSmallestScreenWidthDp != currSmallestScreenWidthDp) {}
          PhoneWindow phoneWindow22 = this.mWindow;
          if (!isPortrait) {}
          float availableWidth2 =
              TypedValue.applyDimension(1, res.getConfiguration().screenWidthDp, metrics);
          if (tv.type != 0) {}
        }
        if (measure) {}
      }
    }
    widthMeasureSpec2 = widthMeasureSpec;
    this.mApplyFloatingVerticalInsets = false;
    if (heightMode == Integer.MIN_VALUE) {}
    heightMeasureSpec2 = heightMeasureSpec;
    super.onMeasure(widthMeasureSpec2, heightMeasureSpec2);
    int width22 = getMeasuredWidth();
    measure = false;
    int widthMeasureSpec322 = View.MeasureSpec.makeMeasureSpec(width22, 1073741824);
    if (!fixedWidth) {}
    if (measure) {}
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
        if (showPopOver) {
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
          SemViewUtils.getPopOverSmoothRoundedRect(this.mContext, width, height));
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
    Drawable drawable = this.mMenuBackground;
    if (drawable != null) {
      drawable.draw(canvas);
    }
  }

  @Override // android.view.ViewGroup, android.view.View
  protected void dispatchDraw(Canvas canvas) {
    if (this.mPopOverContentAlpha < 1.0f) {
      int saveCount = canvas.getSaveCount();
      int width = getWidth();
      int height = getHeight();
      canvas.saveLayerAlpha(0.0f, 0.0f, width, height, (int) (this.mPopOverContentAlpha * 255.0f));
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
      helper = this.mWindow.mContextMenu.showPopup(getContext(), originalView, x, y);
    } else {
      helper = this.mWindow.mContextMenu.showDialog(originalView, originalView.getWindowToken());
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

  private ActionMode startActionMode(View originatingView, ActionMode.Callback callback, int type) {
    ActionMode.Callback2 wrappedCallback = new ActionModeCallback2Wrapper(callback);
    ActionMode mode = null;
    if (this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
      try {
        mode = this.mWindow.getCallback().onWindowStartingActionMode(wrappedCallback, type);
      } catch (AbstractMethodError e) {
        if (type == 0) {
          try {
            mode = this.mWindow.getCallback().onWindowStartingActionMode(wrappedCallback);
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
        ActionMode actionMode = this.mFloatingActionMode;
        if (actionMode != null) {
          actionMode.finish();
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

  public void setWindowBackground(Drawable drawable) {
    int i;
    if (drawable instanceof ColorDrawable) {
      this.mPopOverBackgroundColor = ((ColorDrawable) drawable).getColor();
    } else if ((drawable instanceof GradientDrawable)
        && ((GradientDrawable) drawable).getColor() != null) {
      this.mPopOverBackgroundColor = ((GradientDrawable) drawable).getColor().getDefaultColor();
    } else {
      Resources resources = getResources();
      if (SemViewUtils.isLightTheme(this.mContext)) {
        i = C4337R.color.sem_app_bar_bg_color;
      } else {
        i = C4337R.color.sem_app_bar_bg_color_dark;
      }
      this.mPopOverBackgroundColor = resources.getColor(i, null);
    }
    Log.m98i(
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
      if (drawable != null) {
        this.mResizingBackgroundDrawable =
            enforceNonTranslucentBackground(
                drawable, this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
      } else {
        this.mResizingBackgroundDrawable =
            getResizingBackgroundDrawable(
                this.mWindow.mBackgroundDrawable,
                this.mWindow.mBackgroundFallbackDrawable,
                this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
      }
      Drawable drawable2 = this.mResizingBackgroundDrawable;
      if (drawable2 != null) {
        drawable2.getPadding(this.mBackgroundPadding);
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
    updateDecorCaptionStatus(getResources().getConfiguration());
    View view = this.mStatusGuard;
    if (view != null && view.getVisibility() == 0) {
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
      Log.m94d(DEBUG_KNOX_TAG, "failed to remove knox badge");
    }
  }

  @Override // android.view.View
  public void onSystemBarAppearanceChanged(int appearance) {
    updateColorViews(null, true);
    PhoneWindow phoneWindow = this.mWindow;
    if (phoneWindow != null) {
      phoneWindow.dispatchOnSystemBarAppearanceChanged(appearance);
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
                0, insets.getSystemWindowInsetTop(), 0, insets.getSystemWindowInsetBottom());
      }
      if (this.mWindow.getAttributes().width == -2) {
        this.mFloatingInsets.left = insets.getSystemWindowInsetTop();
        this.mFloatingInsets.right = insets.getSystemWindowInsetBottom();
        insets =
            insets.inset(
                insets.getSystemWindowInsetLeft(), 0, insets.getSystemWindowInsetRight(), 0);
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

  public static int getNavBarSizeForBadge(int leftInset, int rightInset, int bottomInset) {
    return isNavBarToRightEdge(bottomInset, rightInset - sKnoxBadgeRightCutout)
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

  /* JADX WARN: Code restructure failed: missing block: B:204:0x01ac, code lost:

     if (r9.getHeight() == r10) goto L90;
  */
  /* JADX WARN: Removed duplicated region for block: B:217:0x01db  */
  /* JADX WARN: Removed duplicated region for block: B:224:0x0216  */
  /* JADX WARN: Removed duplicated region for block: B:225:0x0225  */
  /* JADX WARN: Removed duplicated region for block: B:227:0x01df  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  WindowInsets updateColorViews(WindowInsets insets, boolean animate) {
    int systemBarsAppearance;
    boolean disallowAnimate;
    WindowInsetsController controller;
    boolean statusBarNeedsRightInset;
    int statusBarSideInset;
    Insets systemInsets;
    WindowInsets insets2;
    int consumedTop;
    int appearance;
    int color;
    WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
    int sysUiVisibility = attrs.systemUiVisibility | getWindowSystemUiVisibility();
    WindowInsetsController controller2 = getWindowInsetsController();
    int requestedVisibleTypes = controller2.getRequestedVisibleTypes();
    boolean isImeWindow = this.mWindow.getAttributes().type == 2011;
    if (!this.mWindow.mIsFloating || isImeWindow) {
      boolean disallowAnimate2 =
          (!isLaidOut()) | (((this.mLastWindowFlags ^ attrs.flags) & Integer.MIN_VALUE) != 0);
      this.mLastWindowFlags = attrs.flags;
      ViewRootImpl viewRoot = getViewRootImpl();
      if (viewRoot != null) {
        systemBarsAppearance = viewRoot.mWindowAttributes.insetsFlags.appearance;
      } else {
        systemBarsAppearance = controller2.getSystemBarsAppearance();
      }
      int appearance2 = systemBarsAppearance;
      if (insets != null) {
        this.mLastForceConsumingTypes = insets.getForceConsumingTypes();
        boolean clearsCompatInsets =
            InsetsState.clearsCompatInsets(
                attrs.type,
                attrs.flags,
                getResources().getConfiguration().windowConfiguration.getActivityType(),
                this.mLastForceConsumingTypes);
        int compatInsetsTypes = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
        Insets stableBarInsets = insets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        if (clearsCompatInsets) {
          systemInsets = Insets.NONE;
        } else {
          systemInsets = Insets.min(insets.getInsets(compatInsetsTypes), stableBarInsets);
        }
        this.mLastTopInset = systemInsets.top;
        this.mLastBottomInset = systemInsets.bottom;
        this.mLastRightInset = systemInsets.right;
        this.mLastLeftInset = systemInsets.left;
        this.mKnoxBadgeInsets =
            insets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        boolean hasTopStableInset = stableBarInsets.top != 0;
        boolean disallowAnimate3 =
            disallowAnimate2 | (hasTopStableInset != this.mLastHasTopStableInset);
        this.mLastHasTopStableInset = hasTopStableInset;
        boolean hasBottomStableInset = stableBarInsets.bottom != 0;
        boolean disallowAnimate4 =
            disallowAnimate3 | (hasBottomStableInset != this.mLastHasBottomStableInset);
        this.mLastHasBottomStableInset = hasBottomStableInset;
        boolean hasRightStableInset = stableBarInsets.right != 0;
        boolean disallowAnimate5 =
            disallowAnimate4 | (hasRightStableInset != this.mLastHasRightStableInset);
        this.mLastHasRightStableInset = hasRightStableInset;
        boolean hasLeftStableInset = stableBarInsets.left != 0;
        boolean clearsCompatInsets2 = this.mLastHasLeftStableInset;
        boolean disallowAnimate6 = disallowAnimate5 | (hasLeftStableInset != clearsCompatInsets2);
        this.mLastHasLeftStableInset = hasLeftStableInset;
        this.mLastSuppressScrimTypes = insets.getSuppressScrimTypes();
        disallowAnimate = disallowAnimate6;
      } else {
        updateDisplayCutoutBackground(null);
        disallowAnimate = disallowAnimate2;
      }
      boolean navBarToRightEdge = isNavBarToRightEdge(this.mLastBottomInset, this.mLastRightInset);
      boolean navBarToLeftEdge = isNavBarToLeftEdge(this.mLastBottomInset, this.mLastLeftInset);
      int navBarSize =
          getNavBarSize(this.mLastBottomInset, this.mLastRightInset, this.mLastLeftInset);
      controller = controller2;
      updateColorViewInt(
          this.mNavigationColorViewState,
          calculateNavigationBarColor(appearance2),
          this.mWindow.mNavigationBarDividerColor,
          navBarSize,
          navBarToRightEdge || navBarToLeftEdge,
          navBarToLeftEdge,
          0,
          animate && !disallowAnimate,
          this.mForceWindowDrawsBarBackgrounds,
          requestedVisibleTypes);
      boolean oldDrawLegacy = this.mDrawLegacyNavigationBarBackground;
      this.mDrawLegacyNavigationBarBackground =
          (this.mWindow.getAttributes().flags & Integer.MIN_VALUE) == 0;
      View navigationView = this.mNavigationColorViewState.view;
      if (oldDrawLegacy == this.mDrawLegacyNavigationBarBackground) {
        if (navigationView != null) {
          if ((navigationView.getVisibility() == 0) == this.mNavigationColorViewState.visible) {
            if (this.mDrawLegacyNavigationBarBackground) {}
          }
        }
        statusBarNeedsRightInset = !navBarToRightEdge && this.mNavigationColorViewState.present;
        boolean statusBarNeedsLeftInset =
            !navBarToLeftEdge && this.mNavigationColorViewState.present;
        if (!statusBarNeedsRightInset) {
          statusBarSideInset = this.mLastRightInset;
        } else {
          statusBarSideInset = statusBarNeedsLeftInset ? this.mLastLeftInset : 0;
        }
        int statusBarColor = calculateStatusBarColor(appearance2);
        updateColorViewInt(
            this.mStatusColorViewState,
            statusBarColor,
            0,
            this.mLastTopInset,
            false,
            statusBarNeedsLeftInset,
            statusBarSideInset,
            (animate || disallowAnimate) ? false : true,
            this.mForceWindowDrawsBarBackgrounds,
            requestedVisibleTypes);
        if (!this.mHasCaption) {
          this.mDecorCaptionView.getCaption().setBackgroundColor(statusBarColor);
          updateDecorCaptionShade();
        }
      }
      this.mDrawLegacyNavigationBarBackgroundHandled =
          this.mWindow.onDrawLegacyNavigationBarBackgroundChanged(
              this.mDrawLegacyNavigationBarBackground);
      if (viewRoot != null) {
        viewRoot.requestInvalidateRootRenderNode();
      }
      statusBarNeedsRightInset = !navBarToRightEdge && this.mNavigationColorViewState.present;
      boolean statusBarNeedsLeftInset2 =
          !navBarToLeftEdge && this.mNavigationColorViewState.present;
      if (!statusBarNeedsRightInset) {}
      int statusBarColor2 = calculateStatusBarColor(appearance2);
      updateColorViewInt(
          this.mStatusColorViewState,
          statusBarColor2,
          0,
          this.mLastTopInset,
          false,
          statusBarNeedsLeftInset2,
          statusBarSideInset,
          (animate || disallowAnimate) ? false : true,
          this.mForceWindowDrawsBarBackgrounds,
          requestedVisibleTypes);
      if (!this.mHasCaption) {}
    } else {
      controller = controller2;
    }
    boolean hideNavigation =
        (sysUiVisibility & 2) != 0
            || (requestedVisibleTypes & WindowInsets.Type.navigationBars()) == 0;
    boolean decorFitsSystemWindows = this.mWindow.mDecorFitsSystemWindows;
    boolean forceConsumingNavBar =
        ((this.mForceWindowDrawsBarBackgrounds || this.mDrawLegacyNavigationBarBackgroundHandled)
                && (attrs.flags & Integer.MIN_VALUE) == 0
                && (sysUiVisibility & 512) == 0
                && decorFitsSystemWindows
                && !hideNavigation)
            || ((this.mLastForceConsumingTypes & WindowInsets.Type.navigationBars()) != 0
                && hideNavigation);
    boolean consumingNavBar =
        ((attrs.flags & Integer.MIN_VALUE) != 0
                && (sysUiVisibility & 512) == 0
                && decorFitsSystemWindows
                && !hideNavigation)
            || forceConsumingNavBar;
    boolean fullscreen =
        ((sysUiVisibility & 4) == 0
                && (attrs.flags & 1024) == 0
                && (requestedVisibleTypes & WindowInsets.Type.statusBars()) != 0)
            ? false
            : true;
    boolean consumingStatusBar =
        ((sysUiVisibility & 1024) == 0
                && decorFitsSystemWindows
                && (attrs.flags & 256) == 0
                && (attrs.flags & 65536) == 0
                && this.mForceWindowDrawsBarBackgrounds
                && this.mLastTopInset != 0)
            || ((this.mLastForceConsumingTypes & WindowInsets.Type.statusBars()) != 0
                && fullscreen);
    int consumedTop2 = consumingStatusBar ? this.mLastTopInset : 0;
    int consumedRight = consumingNavBar ? this.mLastRightInset : 0;
    int consumedBottom = consumingNavBar ? this.mLastBottomInset : 0;
    int consumedLeft = consumingNavBar ? this.mLastLeftInset : 0;
    if (CoreRune.MW_CAPTION_SHELL_INSETS) {
      boolean captionBarBackgroundDrawableNeeded = false;
      if (!consumingStatusBar && shouldConsumeCaptionBar()) {
        consumedTop2 = this.mLastTopInset;
        captionBarBackgroundDrawableNeeded =
            (isOpaque() || Color.alpha(this.mWindow.mStatusBarColor) == 255) ? false : true;
      }
      if (!captionBarBackgroundDrawableNeeded) {
        consumedTop = consumedTop2;
        this.mCaptionBarBackgroundDrawable = null;
      } else {
        ViewRootImpl viewRoot2 = getViewRootImpl();
        if (viewRoot2 != null) {
          appearance = viewRoot2.mWindowAttributes.insetsFlags.appearance;
        } else {
          appearance = controller.getSystemBarsAppearance();
        }
        boolean light = (appearance & 8) != 0;
        int color2 = light ? -1 : -16777216;
        ColorDrawable colorDrawable = this.mCaptionBarBackgroundDrawable;
        if (colorDrawable != null) {
          color = color2;
          if (colorDrawable.getColor() == color
              && this.mCaptionBarBackgroundDrawable.getBounds().height() == consumedTop2) {
            consumedTop = consumedTop2;
          }
        } else {
          color = color2;
        }
        final int captionHeight = consumedTop2;
        this.mCaptionBarBackgroundDrawable =
            new ColorDrawable(color) { // from class: com.android.internal.policy.DecorView.5
              @Override // android.graphics.drawable.Drawable
              public void setBounds(int left, int top, int right, int bottom) {
                super.setBounds(left, 0, right, captionHeight);
              }
            };
        if (CoreRune.SAFE_DEBUG) {
          consumedTop = consumedTop2;
          Log.m94d(
              this.mLogTag,
              "CaptionBarBackgroundDrawable is created. "
                  + this
                  + ", color=0x"
                  + Integer.toHexString(color)
                  + ", captionHeight="
                  + captionHeight);
        } else {
          consumedTop = consumedTop2;
        }
      }
      consumedTop2 = consumedTop;
    }
    ViewGroup viewGroup = this.mContentRoot;
    if (viewGroup == null) {
      insets2 = insets;
    } else if (!(viewGroup.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
      insets2 = insets;
    } else {
      ViewGroup.MarginLayoutParams lp =
          (ViewGroup.MarginLayoutParams) this.mContentRoot.getLayoutParams();
      if (lp.topMargin == consumedTop2
          && lp.rightMargin == consumedRight
          && lp.bottomMargin == consumedBottom
          && lp.leftMargin == consumedLeft) {
        insets2 = insets;
      } else {
        lp.topMargin = consumedTop2;
        lp.rightMargin = consumedRight;
        lp.bottomMargin = consumedBottom;
        lp.leftMargin = consumedLeft;
        this.mContentRoot.setLayoutParams(lp);
        insets2 = insets;
        if (insets2 == null) {
          requestApplyInsets();
        }
      }
      if (insets2 != null) {
        insets2 = insets2.inset(consumedLeft, consumedTop2, consumedRight, consumedBottom);
      }
    }
    if (!forceConsumingNavBar || hideNavigation || this.mDrawLegacyNavigationBarBackgroundHandled) {
      this.mBackgroundInsets = Insets.NONE;
    } else {
      this.mBackgroundInsets =
          Insets.m14of(this.mLastLeftInset, 0, this.mLastRightInset, this.mLastBottomInset);
    }
    updateBackgroundDrawable();
    return insets2;
  }

  private void updateBackgroundDrawable() {
    if (this.mBackgroundInsets == null) {
      this.mBackgroundInsets = Insets.NONE;
    }
    if (this.mBackgroundInsets.equals(this.mLastBackgroundInsets)
        && ((!CoreRune.MW_CAPTION_SHELL_INSETS
                || this.mCaptionBarBackgroundDrawable == this.mLastCaptionBarBackgroundDrawable)
            && this.mBackgroundBlurDrawable == this.mLastBackgroundBlurDrawable
            && this.mLastOriginalBackgroundDrawable == this.mOriginalBackgroundDrawable)) {
      return;
    }
    Drawable destDrawable = this.mOriginalBackgroundDrawable;
    if (this.mBackgroundBlurDrawable != null) {
      destDrawable =
          new LayerDrawable(
              new Drawable[] {this.mBackgroundBlurDrawable, this.mOriginalBackgroundDrawable});
    }
    if (destDrawable != null && !this.mBackgroundInsets.equals(Insets.NONE)) {
      destDrawable =
          new InsetDrawable(
              destDrawable,
              this.mBackgroundInsets.left,
              this.mBackgroundInsets.top,
              this.mBackgroundInsets.right,
              this.mBackgroundInsets
                  .bottom) { // from class: com.android.internal.policy.DecorView.6
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
          new LayerDrawable(new Drawable[] {this.mCaptionBarBackgroundDrawable, destDrawable});
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
    int i =
        (this.mCrossWindowBlurEnabled && this.mWindow.isTranslucent())
            ? this.mOriginalBackgroundBlurRadius
            : 0;
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

  void setBackgroundBlurRadius(int blurRadius) {
    this.mOriginalBackgroundBlurRadius = blurRadius;
    if (blurRadius > 0) {
      if (this.mCrossWindowBlurEnabledListener == null) {
        this.mCrossWindowBlurEnabledListener =
            new Consumer() { // from class:
                             // com.android.internal.policy.DecorView$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DecorView.this.lambda$setBackgroundBlurRadius$1((Boolean) obj);
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
  public /* synthetic */ void lambda$setBackgroundBlurRadius$1(Boolean enabled) {
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

  private boolean isTentModeWithRotation180() {
    return this.mContext.getDisplayId() == 1
        && getConfiguration().windowConfiguration.getRotation() == 2
        && this.mKeepCutoutInTentMode;
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
            && (this.mLastSuppressScrimTypes & WindowInsets.Type.statusBars()) == 0);
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
        this.mWindow.getDeviceDefaultNavigationBarColor());
  }

  public static int calculateBarColor(
      int flags,
      int translucentFlag,
      int semiTransparentBarColor,
      int barColor,
      int appearance,
      int lightAppearanceFlag,
      boolean scrimTransparent) {
    return calculateBarColor(
        flags,
        translucentFlag,
        semiTransparentBarColor,
        barColor,
        appearance,
        lightAppearanceFlag,
        scrimTransparent,
        -16777216);
  }

  public static int calculateBarColor(
      int flags,
      int translucentFlag,
      int semiTransparentBarColor,
      int barColor,
      int appearance,
      int lightAppearanceFlag,
      boolean scrimTransparent,
      int deviceDefaultColor) {
    if ((flags & translucentFlag) != 0) {
      return semiTransparentBarColor;
    }
    if ((Integer.MIN_VALUE & flags) == 0) {
      return deviceDefaultColor;
    }
    if (scrimTransparent && Color.alpha(barColor) == 0) {
      boolean light = (appearance & lightAppearanceFlag) != 0;
      return light ? SCRIM_LIGHT : semiTransparentBarColor;
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

  /* JADX WARN: Code restructure failed: missing block: B:82:0x012c, code lost:

     if (r6.leftMargin != r13) goto L79;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void updateColorViewInt(
      ColorViewState state,
      int color,
      int dividerColor,
      int size,
      boolean verticalBar,
      boolean seascape,
      int sideMargin,
      boolean animate,
      boolean force,
      int requestedVisibleTypes) {
    int resolvedGravity;
    int vis;
    boolean visibilityChanged;
    int leftMargin;
    int type = state.attributes.insetsType;
    state.present =
        state.attributes.isPresent(
            ((requestedVisibleTypes & type) == 0 && (this.mLastForceConsumingTypes & type) == 0)
                ? false
                : true,
            this.mWindow.getAttributes().flags,
            force);
    boolean show =
        state.attributes.isVisible(state.present, color, this.mWindow.getAttributes().flags, force);
    boolean showView = show && !isResizing() && !this.mHasCaption && size > 0;
    if (type == WindowInsets.Type.navigationBars()) {
      WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
      if (this.mContext.getResources().getConfiguration().isDexMode()
          && attrs != null
          && (attrs.type == 2011 || attrs.type == 2021)) {
        showView = false;
      }
    }
    boolean visibilityChanged2 = false;
    View view = state.view;
    int resolvedHeight = verticalBar ? -1 : size;
    int resolvedWidth = verticalBar ? size : -1;
    if (verticalBar) {
      ColorViewAttributes colorViewAttributes = state.attributes;
      resolvedGravity =
          seascape ? colorViewAttributes.seascapeGravity : colorViewAttributes.horizontalGravity;
    } else {
      resolvedGravity = state.attributes.verticalGravity;
    }
    if (view == null) {
      if (showView) {
        View view2 = new View(this.mContext);
        view = view2;
        state.view = view2;
        setColor(view, color, dividerColor, verticalBar, seascape);
        view.setTransitionName(state.attributes.transitionName);
        view.setId(state.attributes.f999id);
        visibilityChanged2 = true;
        view.setVisibility(4);
        state.targetVisibility = 0;
        FrameLayout.LayoutParams lp =
            new FrameLayout.LayoutParams(resolvedWidth, resolvedHeight, resolvedGravity);
        if (seascape) {
          lp.leftMargin = sideMargin;
        } else {
          lp.rightMargin = sideMargin;
        }
        addView(view, lp);
        updateColorViewTranslations();
      }
    } else {
      int vis2 = showView ? 0 : 4;
      boolean visibilityChanged3 = state.targetVisibility != vis2;
      state.targetVisibility = vis2;
      FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) view.getLayoutParams();
      int rightMargin = seascape ? 0 : sideMargin;
      int leftMargin2 = seascape ? sideMargin : 0;
      if (lp2.height == resolvedHeight
          && lp2.width == resolvedWidth
          && lp2.gravity == resolvedGravity) {
        vis = rightMargin;
        if (lp2.rightMargin == vis) {
          visibilityChanged = visibilityChanged3;
          leftMargin = leftMargin2;
        } else {
          visibilityChanged = visibilityChanged3;
          leftMargin = leftMargin2;
        }
      } else {
        vis = rightMargin;
        visibilityChanged = visibilityChanged3;
        leftMargin = leftMargin2;
      }
      lp2.height = resolvedHeight;
      lp2.width = resolvedWidth;
      lp2.gravity = resolvedGravity;
      lp2.rightMargin = vis;
      lp2.leftMargin = leftMargin;
      view.setLayoutParams(lp2);
      if (showView) {
        setColor(view, color, dividerColor, verticalBar, seascape);
      }
      visibilityChanged2 = visibilityChanged;
    }
    if (visibilityChanged2) {
      view.animate().cancel();
      if (!animate || isResizing()) {
        view.setAlpha(1.0f);
        view.setVisibility(showView ? 0 : 4);
      } else if (showView) {
        if (view.getVisibility() != 0) {
          view.setVisibility(0);
          view.setAlpha(0.0f);
        }
        view.animate()
            .alpha(1.0f)
            .setInterpolator(this.mShowInterpolator)
            .setDuration(this.mBarEnterExitDuration);
      } else {
        final View hideView = state.view;
        view.animate()
            .alpha(0.0f)
            .setInterpolator(this.mHideInterpolator)
            .setDuration(this.mBarEnterExitDuration)
            .withEndAction(
                new Runnable() { // from class: com.android.internal.policy.DecorView.7
                  @Override // java.lang.Runnable
                  public void run() {
                    hideView.setAlpha(1.0f);
                    hideView.setVisibility(4);
                  }
                });
      }
    }
    state.visible = show;
    state.color = color;
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
                    1, 1.0f, v.getContext().getResources().getDisplayMetrics()));
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
      this.mNavigationColorViewState.view.setTranslationY(rootScrollY < 0 ? rootScrollY : 0.0f);
    }
  }

  private WindowInsets updateStatusGuard(WindowInsets insets) {
    boolean showStatusGuard;
    int i;
    WindowInsets insets2 = insets;
    ActionBarContextView actionBarContextView = this.mPrimaryActionModeView;
    if (actionBarContextView == null) {
      showStatusGuard = false;
      i = 0;
    } else if (!(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
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
          View view = new View(this.mContext);
          this.mStatusGuard = view;
          view.setVisibility(8);
          FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, mlp.topMargin, 51);
          lp.leftMargin = newGuardLeftMargin;
          lp.rightMargin = newGuardRightMargin;
          addView(this.mStatusGuard, indexOfChild(this.mStatusColorViewState.view), lp);
        } else {
          View view2 = this.mStatusGuard;
          if (view2 != null) {
            FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) view2.getLayoutParams();
            if (lp2.height != mlp.topMargin
                || lp2.leftMargin != newGuardLeftMargin
                || lp2.rightMargin != newGuardRightMargin) {
              lp2.height = mlp.topMargin;
              lp2.leftMargin = newGuardLeftMargin;
              lp2.rightMargin = newGuardRightMargin;
              this.mStatusGuard.setLayoutParams(lp2);
            }
          }
        }
        View view3 = this.mStatusGuard;
        boolean showStatusGuard2 = view3 != null;
        if (showStatusGuard2 && view3.getVisibility() != 0) {
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
    View view4 = this.mStatusGuard;
    if (view4 != null) {
      view4.setVisibility(showStatusGuard ? i : 8);
    }
    return insets2;
  }

  private void updateStatusGuardColor() {
    int color;
    boolean lightStatusBar = (getWindowSystemUiVisibility() & 8192) != 0;
    View view = this.mStatusGuard;
    if (lightStatusBar) {
      color = this.mContext.getColor(C4337R.color.decor_view_status_guard_light);
    } else {
      color = this.mContext.getColor(C4337R.color.decor_view_status_guard);
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
    } else {
      ViewOutlineProvider outlineProvider = getOutlineProvider();
      ViewOutlineProvider viewOutlineProvider = this.mLastOutlineProvider;
      if (outlineProvider != viewOutlineProvider) {
        setOutlineProvider(viewOutlineProvider);
      }
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
    Rect framePadding = this.mFramePadding;
    if (framePadding == null) {
      framePadding = new Rect();
    }
    Rect backgroundPadding = this.mBackgroundPadding;
    if (backgroundPadding == null) {
      backgroundPadding = new Rect();
    }
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
      boolean hasWindowFocus = hasWindowFocus();
      boolean hasWindowFocus2 = hasWindowFocus | this.mHasWindowFocusInTask;
      DecorCaptionView decorCaptionView = this.mDecorCaptionView;
      if (decorCaptionView != null) {
        decorCaptionView.dispatchWindowFocusChanged(hasWindowFocus2);
      }
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
          && (((cb instanceof Activity) || (this.mWindow.getContext() instanceof Activity))
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
    ActionMode actionMode = this.mPrimaryActionMode;
    if (actionMode != null) {
      actionMode.onWindowFocusChanged(hasWindowFocus);
    }
    ActionMode actionMode2 = this.mFloatingActionMode;
    if (actionMode2 != null) {
      actionMode2.onWindowFocusChanged(hasWindowFocus);
    }
    updateElevation();
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
      if ((this.mIsKeyboardShown || z || this.mIsKnoxActivity)
          && (SemPersonaManager.isKnoxId(this.mUserId)
              || SemDualAppManager.isDualAppId(this.mUserId))) {
        setKnoxBadge();
        setKnoxBadgePosition();
      }
    } catch (Exception e) {
      Log.m94d(DEBUG_KNOX_TAG, "failed to set knox badge");
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
    } else {
      BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
      if (backdropFrameRenderer != null) {
        backdropFrameRenderer.onConfigurationChange();
      }
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
    FloatingToolbar floatingToolbar = this.mFloatingToolbar;
    if (floatingToolbar != null) {
      floatingToolbar.dismiss();
      this.mFloatingToolbar = null;
    }
    removeBackgroundBlurDrawable();
    PhoneWindow.PanelFeatureState st = this.mWindow.getPanelState(0, false);
    if (st != null && st.menu != null && this.mFeatureId < 0) {
      st.menu.close();
    }
    releaseThreadedRenderer();
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
    if (i == 17304565 || i == 17304562 || i == 17304563) {
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
    DecorCaptionView decorCaptionView = this.mDecorCaptionView;
    if (decorCaptionView != null) {
      decorCaptionView.onRootViewScrollYChanged(rootScrollY);
    }
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
    ActionBarContextView actionBarContextView = this.mPrimaryActionModeView;
    if (actionBarContextView == null || !actionBarContextView.isAttachedToWindow()) {
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
        PopupWindow popupWindow =
            new PopupWindow(
                actionBarContext, (AttributeSet) null, C4337R.attr.actionModePopupWindowStyle);
        this.mPrimaryActionModePopup = popupWindow;
        popupWindow.setWindowLayoutType(2);
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
                    DecorView.this.mPrimaryActionModeView.getApplicationWindowToken(), 55, 0, 0);
                DecorView.this.endOnGoingFadeAnimation();
                if (DecorView.this.shouldAnimatePrimaryActionModeView()) {
                  DecorView decorView = DecorView.this;
                  decorView.mFadeAnim =
                      ObjectAnimator.ofFloat(
                          decorView.mPrimaryActionModeView,
                          (Property<ActionBarContextView, Float>) View.ALPHA,
                          0.0f,
                          1.0f);
                  DecorView.this.mFadeAnim.addListener(
                      new AnimatorListenerAdapter() { // from class:
                                                      // com.android.internal.policy.DecorView.8.1
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
                  DecorView.this.mFadeAnim.start();
                  return;
                }
                DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                DecorView.this.mPrimaryActionModeView.setVisibility(0);
              }
            };
      } else {
        ViewStub stub = (ViewStub) findViewById(C4337R.id.action_mode_bar_stub);
        if (stub != null) {
          this.mPrimaryActionModeView = (ActionBarContextView) stub.inflate();
          this.mPrimaryActionModePopup = null;
        }
      }
    }
    ActionBarContextView actionBarContextView2 = this.mPrimaryActionModeView;
    if (actionBarContextView2 == null) {
      return null;
    }
    actionBarContextView2.killMode();
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
    ObjectAnimator objectAnimator = this.mFadeAnim;
    if (objectAnimator != null) {
      objectAnimator.end();
    }
  }

  private void setHandledPrimaryActionMode(ActionMode mode) {
    endOnGoingFadeAnimation();
    this.mPrimaryActionMode = mode;
    mode.invalidate();
    this.mPrimaryActionModeView.initForMode(this.mPrimaryActionMode);
    if (this.mPrimaryActionModePopup != null) {
      post(this.mShowPrimaryActionModePopup);
    } else if (shouldAnimatePrimaryActionModeView()) {
      ObjectAnimator ofFloat =
          ObjectAnimator.ofFloat(
              this.mPrimaryActionModeView,
              (Property<ActionBarContextView, Float>) View.ALPHA,
              0.0f,
              1.0f);
      this.mFadeAnim = ofFloat;
      ofFloat.addListener(
          new AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.9
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

  private ActionMode createFloatingActionMode(View originatingView, ActionMode.Callback2 callback) {
    ActionMode actionMode = this.mFloatingActionMode;
    if (actionMode != null) {
      actionMode.finish();
    }
    cleanupFloatingActionModeViews();
    this.mFloatingToolbar = new FloatingToolbar(this.mWindow);
    final FloatingActionMode mode =
        new FloatingActionMode(this.mContext, callback, originatingView, this.mFloatingToolbar);
    this.mFloatingActionModeOriginatingView = originatingView;
    this.mFloatingToolbarPreDrawListener =
        new ViewTreeObserver
            .OnPreDrawListener() { // from class: com.android.internal.policy.DecorView.10
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
    View view = this.mFloatingActionModeOriginatingView;
    if (view instanceof TextView) {
      isDeviceDefaultThemeTextView = ((TextView) view).isThemeDeviceDefault();
    }
    boolean isSemTypeFloating = mode.getType() == 99 || isDeviceDefaultThemeTextView;
    FloatingToolbar floatingToolbar = new FloatingToolbar(this.mWindow, isSemTypeFloating);
    this.mFloatingToolbar = floatingToolbar;
    ((FloatingActionMode) this.mFloatingActionMode).setFloatingToolbar(floatingToolbar);
    this.mFloatingActionMode.invalidate();
    this.mFloatingActionModeOriginatingView
        .getViewTreeObserver()
        .addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
  }

  void enableCaption(boolean attachedAndVisible) {
    if (this.mHasCaption != attachedAndVisible) {
      this.mHasCaption = attachedAndVisible;
      if (getForeground() != null) {
        drawableChanged();
      }
      notifyCaptionHeightChanged();
    }
  }

  public void notifyCaptionHeightChanged() {
    if (!ViewRootImpl.CAPTION_ON_SHELL || CoreRune.MW_CAPTION_SHELL_BUG_FIX) {
      getWindowInsetsController().setCaptionInsetsHeight(getCaptionInsetsHeight());
    }
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
    int i;
    StateListDrawable statefulWindowBackground;
    int[] states;
    StateListDrawable statefulWindowBackground2;
    int[] states2;
    Drawable newBackground;
    int i2;
    super.onConfigurationChanged(newConfig);
    boolean updateWindowFormat = false;
    int oldDisplayDeviceType = this.mLastDisplayDeviceType;
    int i3 = newConfig.semDisplayDeviceType;
    this.mLastDisplayDeviceType = i3;
    if (i3 != oldDisplayDeviceType) {
      updateWindowFormat = true;
    }
    WindowConfiguration winConfig = newConfig.windowConfiguration;
    boolean isPopPover = winConfig.isPopOver();
    if (this.mIsPopOver != isPopPover) {
      this.mIsPopOver = isPopPover;
      updateWindowFormat = true;
      if (!isPopPover) {
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
        this.mPopOverBackgroundColor = res.getColor(C4337R.color.sem_resolver_bg_color);
      } else if (this.mPopOverBackgroundColor == -1) {
        if (SemViewUtils.isLightTheme(this.mContext)) {
          i2 = C4337R.color.sem_app_bar_bg_color;
        } else {
          i2 = C4337R.color.sem_app_bar_bg_color_dark;
        }
        this.mPopOverBackgroundColor = res.getColor(i2, null);
      }
      Log.m98i(TAG, "mPopOverBackgroundColor=" + Integer.toHexString(this.mPopOverBackgroundColor));
      if (getViewRootImpl() != null) {
        getViewRootImpl().requestInvalidateRootRenderNode();
      }
    }
    updateOutlineProvider();
    boolean nightMode = (getResources().getConfiguration().uiMode & 48) == 32;
    Resources resources = this.mContext.getResources();
    if (nightMode) {
      i = C4337R.color.sec_decor_caption_color_dark;
    } else {
      i = C4337R.color.sec_decor_caption_color_light;
    }
    this.mFreeformOutlineColor = resources.getColor(i);
    boolean nightMode2 = CoreRune.MW_CAPTION_SHELL_DEX;
    if (nightMode2) {
      this.mIsDexEnabled = newConfig.isDesktopModeEnabled();
    }
    updateDecorCaptionStatus(newConfig);
    initializeElevation();
    Resources.Theme theme = getContext().getTheme();
    theme.resolveAttribute(16843607, this.mWindow.mMinWidthMinor, true);
    theme.resolveAttribute(16843606, this.mWindow.mMinWidthMajor, true);
    this.mLastSmallestScreenWidthDp = newConfig.smallestScreenWidthDp;
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
      Log.m94d(this.mLogTag, "failed to remove knox badge");
    }
    this.mWindow.updateDeviceDefaultNavigationBarColor();
    this.mLegacyNavigationBarBackgroundPaint.setColor(
        this.mWindow.getDeviceDefaultNavigationBarColor());
    this.mWindow.updateDefaultNavigationBarColor();
    int i4 = this.mLastBackgroundResource;
    if (i4 == 17304565) {
      Drawable currWindowBackground = getBackground();
      if ((currWindowBackground instanceof StateListDrawable)
          && (states2 =
                  (statefulWindowBackground2 = (StateListDrawable) currWindowBackground).getState())
              != null
          && states2.length > 0
          && (statefulWindowBackground2.getStateDrawable(0) instanceof BitmapDrawable)
          && (newBackground = getContext().getDrawable(this.mLastBackgroundResource)) != null) {
        setWindowBackground(newBackground);
        return;
      }
      return;
    }
    if (i4 == 17304562 || i4 == 17304563) {
      Drawable currWindowBackground2 = getBackground();
      Drawable newBackground2 = null;
      if (currWindowBackground2 instanceof BitmapDrawable) {
        newBackground2 = getContext().getResources().getDrawable(this.mLastBackgroundResource);
      } else if ((currWindowBackground2 instanceof StateListDrawable)
          && (states =
                  (statefulWindowBackground = (StateListDrawable) currWindowBackground2).getState())
              != null
          && states.length > 0
          && (statefulWindowBackground.getStateDrawable(0) instanceof BitmapDrawable)) {
        newBackground2 = getContext().getResources().getDrawable(this.mLastBackgroundResource);
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

  private void updateDecorCaptionStatus(Configuration config) {
    boolean displayWindowDecor =
        config.windowConfiguration.hasWindowDecorCaption() && !isFillingScreen(config);
    DecorCaptionView decorCaptionView = this.mDecorCaptionView;
    if (decorCaptionView == null && displayWindowDecor) {
      LayoutInflater inflater = this.mWindow.getLayoutInflater();
      DecorCaptionView createDecorCaptionView = createDecorCaptionView(inflater);
      this.mDecorCaptionView = createDecorCaptionView;
      if (createDecorCaptionView != null) {
        if (createDecorCaptionView.getParent() == null) {
          addView(this.mDecorCaptionView, 0, new ViewGroup.LayoutParams(-1, -1));
        }
        removeView(this.mContentRoot);
        this.mDecorCaptionView.addView(this.mContentRoot, new ViewGroup.MarginLayoutParams(-1, -1));
        return;
      }
      return;
    }
    if (decorCaptionView != null) {
      decorCaptionView.onConfigurationChanged(displayWindowDecor);
      enableCaption(displayWindowDecor);
    }
  }

  void onResourcesLoaded(LayoutInflater inflater, int layoutResource) {
    this.mLastOutlineProvider = getOutlineProvider();
    updateOutlineProvider();
    if (this.mBackdropFrameRenderer != null) {
      loadBackgroundDrawablesIfNeeded();
      this.mBackdropFrameRenderer.onResourcesLoaded(
          this,
          this.mResizingBackgroundDrawable,
          this.mCaptionBackgroundDrawable,
          this.mUserCaptionBackgroundDrawable,
          getCurrentColor(this.mStatusColorViewState),
          getCurrentColor(this.mNavigationColorViewState));
    }
    this.mDecorCaptionView = createDecorCaptionView(inflater);
    View root = inflater.inflate(layoutResource, (ViewGroup) null);
    DecorCaptionView decorCaptionView = this.mDecorCaptionView;
    if (decorCaptionView != null) {
      if (decorCaptionView.getParent() == null) {
        addView(this.mDecorCaptionView, new ViewGroup.LayoutParams(-1, -1));
      }
      this.mDecorCaptionView.addView(root, new ViewGroup.MarginLayoutParams(-1, -1));
    } else {
      addView(root, 0, new ViewGroup.LayoutParams(-1, -1));
    }
    this.mContentRoot = (ViewGroup) root;
    initializeElevation();
    this.mLastSmallestScreenWidthDp = getResources().getConfiguration().smallestScreenWidthDp;
  }

  private void loadBackgroundDrawablesIfNeeded() {
    if (this.mResizingBackgroundDrawable == null) {
      Drawable resizingBackgroundDrawable =
          getResizingBackgroundDrawable(
              this.mWindow.mBackgroundDrawable,
              this.mWindow.mBackgroundFallbackDrawable,
              this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
      this.mResizingBackgroundDrawable = resizingBackgroundDrawable;
      if (resizingBackgroundDrawable == null) {
        Log.m102w(
            this.mLogTag, "Failed to find background drawable for PhoneWindow=" + this.mWindow);
      }
    }
    if (this.mCaptionBackgroundDrawable == null) {
      this.mCaptionBackgroundDrawable =
          getContext().getDrawable(C4337R.drawable.decor_caption_title_focused);
    }
    Drawable drawable = this.mResizingBackgroundDrawable;
    if (drawable != null) {
      this.mLastBackgroundDrawableCb = drawable.getCallback();
      this.mResizingBackgroundDrawable.setCallback(null);
    }
  }

  private DecorCaptionView createDecorCaptionView(LayoutInflater inflater) {
    DecorCaptionView decorCaptionView = null;
    for (int i = getChildCount() - 1; i >= 0 && decorCaptionView == null; i--) {
      View view = getChildAt(i);
      if (view instanceof DecorCaptionView) {
        decorCaptionView = (DecorCaptionView) view;
        removeViewAt(i);
      }
    }
    WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
    boolean isApplication = attrs.type == 1 || attrs.type == 2 || attrs.type == 4;
    WindowConfiguration winConfig = getResources().getConfiguration().windowConfiguration;
    if (!this.mWindow.isFloating()
        && isApplication
        && winConfig.hasWindowDecorCaption()
        && !ViewRootImpl.CAPTION_ON_SHELL) {
      if (decorCaptionView == null) {
        decorCaptionView = inflateDecorCaptionView(inflater);
      }
      decorCaptionView.setPhoneWindow(this.mWindow, true);
    } else {
      decorCaptionView = null;
    }
    enableCaption(decorCaptionView != null);
    return decorCaptionView;
  }

  private DecorCaptionView inflateDecorCaptionView(LayoutInflater inflater) {
    Context context = getContext();
    LayoutInflater inflater2 = LayoutInflater.from(context);
    DecorCaptionView view =
        (DecorCaptionView) inflater2.inflate(C4337R.layout.decor_caption, (ViewGroup) null);
    setDecorCaptionShade(view);
    return view;
  }

  private void setDecorCaptionShade(DecorCaptionView view) {
    int shade = this.mWindow.getDecorCaptionShade();
    switch (shade) {
      case 1:
        setLightDecorCaptionShade(view);
        break;
      case 2:
        setDarkDecorCaptionShade(view);
        break;
      default:
        if ((getWindowSystemUiVisibility() & 8192) != 0) {
          setDarkDecorCaptionShade(view);
          break;
        } else {
          setLightDecorCaptionShade(view);
          break;
        }
    }
  }

  void updateDecorCaptionShade() {
    DecorCaptionView decorCaptionView = this.mDecorCaptionView;
    if (decorCaptionView != null) {
      setDecorCaptionShade(decorCaptionView);
    }
  }

  private void setLightDecorCaptionShade(DecorCaptionView view) {
    view.findViewById(C4337R.id.maximize_window)
        .setBackgroundResource(C4337R.drawable.decor_maximize_button_light);
    view.findViewById(C4337R.id.close_window)
        .setBackgroundResource(C4337R.drawable.decor_close_button_light);
  }

  private void setDarkDecorCaptionShade(DecorCaptionView view) {
    view.findViewById(C4337R.id.maximize_window)
        .setBackgroundResource(C4337R.drawable.decor_maximize_button_dark);
    view.findViewById(C4337R.id.close_window)
        .setBackgroundResource(C4337R.drawable.decor_close_button_dark);
  }

  public static Drawable getResizingBackgroundDrawable(
      Drawable backgroundDrawable, Drawable fallbackDrawable, boolean windowTranslucent) {
    if (backgroundDrawable != null) {
      return enforceNonTranslucentBackground(backgroundDrawable, windowTranslucent);
    }
    if (fallbackDrawable != null) {
      return enforceNonTranslucentBackground(fallbackDrawable, windowTranslucent);
    }
    return new ColorDrawable(-16777216);
  }

  private static Drawable enforceNonTranslucentBackground(
      Drawable drawable, boolean windowTranslucent) {
    if (!windowTranslucent && (drawable instanceof ColorDrawable)) {
      ColorDrawable colorDrawable = (ColorDrawable) drawable;
      int color = colorDrawable.getColor();
      if (Color.alpha(color) != 255) {
        ColorDrawable copy =
            (ColorDrawable) colorDrawable.getConstantState().newDrawable().mutate();
        copy.setColor(Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)));
        return copy;
      }
    }
    return drawable;
  }

  void clearContentView() {
    DecorCaptionView decorCaptionView = this.mDecorCaptionView;
    if (decorCaptionView != null) {
      decorCaptionView.removeContentView();
      return;
    }
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
      Rect newBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
    BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
    if (backdropFrameRenderer != null) {
      backdropFrameRenderer.setTargetRect(newBounds, fullscreen, systemInsets);
    }
  }

  @Override // android.view.WindowCallbacks
  public void onWindowDragResizeStart(
      Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {
    if (this.mWindow.isDestroyed()) {
      releaseThreadedRenderer();
      return;
    }
    if (this.mBackdropFrameRenderer != null) {
      return;
    }
    ThreadedRenderer renderer = getThreadedRenderer();
    if (renderer != null && !ViewRootImpl.CAPTION_ON_SHELL) {
      loadBackgroundDrawablesIfNeeded();
      WindowInsets rootInsets = getRootWindowInsets();
      this.mBackdropFrameRenderer =
          new BackdropFrameRenderer(
              this,
              renderer,
              initialBounds,
              this.mResizingBackgroundDrawable,
              this.mCaptionBackgroundDrawable,
              this.mUserCaptionBackgroundDrawable,
              getCurrentColor(this.mStatusColorViewState),
              getCurrentColor(this.mNavigationColorViewState),
              fullscreen,
              rootInsets.getInsets(WindowInsets.Type.systemBars()));
      updateElevation();
      updateColorViews(null, false);
    }
    getViewRootImpl().requestInvalidateRootRenderNode();
  }

  @Override // android.view.WindowCallbacks
  public void onWindowDragResizeEnd() {
    releaseThreadedRenderer();
    updateColorViews(null, false);
    getViewRootImpl().requestInvalidateRootRenderNode();
  }

  @Override // android.view.WindowCallbacks
  public boolean onContentDrawn(int offsetX, int offsetY, int sizeX, int sizeY) {
    BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
    if (backdropFrameRenderer == null) {
      return false;
    }
    return backdropFrameRenderer.onContentDrawn(offsetX, offsetY, sizeX, sizeY);
  }

  @Override // android.view.WindowCallbacks
  public void onRequestDraw(boolean reportNextDraw) {
    BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
    if (backdropFrameRenderer != null) {
      backdropFrameRenderer.onRequestDraw(reportNextDraw);
    } else if (reportNextDraw && isAttachedToWindow()) {
      getViewRootImpl().reportDrawFinish();
    }
  }

  @Override // android.view.WindowCallbacks
  public void onPostDraw(RecordingCanvas canvas) {
    drawLegacyNavigationBarBackground(canvas);
    if ((isActivity() || isFullSize()) && this.mIsPopOver) {
      PhoneWindow phoneWindow = this.mWindow;
      if (phoneWindow != null && phoneWindow.isFloating()) {
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
          SemViewUtils.getPopOverSmoothRoundedRect(this.mContext, width, height),
          this.mPopOverFramePaint);
      return;
    }
    drawFrameIfNeeded(canvas);
  }

  private void drawLegacyNavigationBarBackground(RecordingCanvas canvas) {
    View v;
    boolean z = this.mLastDrawLegacyNavigationBarBackground;
    boolean z2 = this.mDrawLegacyNavigationBarBackground;
    if (z != z2) {
      this.mLastDrawLegacyNavigationBarBackground = z2;
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

  private void releaseThreadedRenderer() {
    Drawable.Callback callback;
    Drawable drawable = this.mResizingBackgroundDrawable;
    if (drawable != null && (callback = this.mLastBackgroundDrawableCb) != null) {
      drawable.setCallback(callback);
      this.mLastBackgroundDrawableCb = null;
    }
    BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
    if (backdropFrameRenderer != null) {
      backdropFrameRenderer.releaseRenderer();
      this.mBackdropFrameRenderer = null;
      updateElevation();
    }
  }

  private boolean isResizing() {
    return this.mBackdropFrameRenderer != null;
  }

  private void initializeElevation() {
    this.mAllowUpdateElevation = false;
    updateElevation();
  }

  public void updateElevationIfNeeded() {
    if (this.mIsPopOver) {
      updateElevation();
    }
  }

  public void removePopOverElevation() {
    if (!isResizing()) {
      this.mWindow.setElevation(0.0f);
    } else {
      setElevation(0.0f);
    }
  }

  private void updateElevation() {
    int windowingMode = getResources().getConfiguration().windowConfiguration.getWindowingMode();
    boolean renderShadowsInCompositor =
        this.mWindow.mRenderShadowsInCompositor && !isPopOverState();
    if (renderShadowsInCompositor) {
      return;
    }
    float elevation = 0.0f;
    boolean wasAdjustedForStack = this.mElevationAdjustedForStack;
    if (this.mIsPopOver && (this.mIsPopOverWithoutOutlineEffect || this.mPreventPopOverElevation)) {
      elevation = 0.0f;
      this.mElevationAdjustedForStack = true;
    } else if (isPopOverState()) {
      elevation = dipToPx(32.0f);
      this.mElevationAdjustedForStack = true;
    } else if (windowingMode == 5 && !isResizing()) {
      float elevation2 = hasWindowFocus() ? 20.0f : 5.0f;
      if (!this.mAllowUpdateElevation) {
        elevation2 = 20.0f;
      }
      elevation = dipToPx(elevation2);
      this.mElevationAdjustedForStack = true;
    } else {
      this.mElevationAdjustedForStack = false;
    }
    if ((wasAdjustedForStack || this.mElevationAdjustedForStack) && getElevation() != elevation) {
      if (!isResizing()) {
        this.mWindow.setElevation(elevation);
      } else {
        setElevation(elevation);
      }
    }
  }

  boolean isShowingCaption() {
    DecorCaptionView decorCaptionView = this.mDecorCaptionView;
    return decorCaptionView != null && decorCaptionView.isCaptionShowing();
  }

  int getCaptionHeight() {
    if (isShowingCaption()) {
      return this.mDecorCaptionView.getCaptionHeight();
    }
    return 0;
  }

  public int getCaptionInsetsHeight() {
    if (this.mWindow.isOverlayWithDecorCaptionEnabled()) {
      return getCaptionHeight();
    }
    return 0;
  }

  private float dipToPx(float dip) {
    return TypedValue.applyDimension(1, dip, getResources().getDisplayMetrics());
  }

  void setUserCaptionBackgroundDrawable(Drawable drawable) {
    this.mUserCaptionBackgroundDrawable = drawable;
    BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
    if (backdropFrameRenderer != null) {
      backdropFrameRenderer.setUserCaptionBackgroundDrawable(drawable);
    }
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
    this.mLogTag = "DecorView[" + getTitleSuffix(params) + NavigationBarInflaterView.SIZE_MOD_END;
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
    return "DecorView@"
        + Integer.toHexString(hashCode())
        + NavigationBarInflaterView.SIZE_MOD_START
        + getTitleSuffix(this.mWindow.getAttributes())
        + NavigationBarInflaterView.SIZE_MOD_END;
  }

  private boolean isActivity() {
    PhoneWindow phoneWindow = this.mWindow;
    return (phoneWindow == null || phoneWindow.getWindowControllerCallback() == null)
        ? false
        : true;
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
    if (i != 0) {
      return i;
    }
    return getConfiguration().windowConfiguration.getWindowingMode();
  }

  private int getStagePosition() {
    return getConfiguration().windowConfiguration.getStagePosition();
  }

  public boolean isFullscreenMode() {
    return getWindowingMode() == 1;
  }

  private boolean isImmersiveMode() {
    int systemUiVis = getSystemUiVisibility() | getWindowSystemUiVisibility();
    if ((systemUiVis & GLES30.GL_COLOR) != 0 && (systemUiVis & 2) != 0) {
      return true;
    }
    return false;
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

  private void updateRoundedCornerStateIfNeeded() {
    boolean needToDrawAboveNavBar;
    if (this.mWindow.mActivityCurrentConfig == null) {
      return;
    }
    Context context = this.mContext.getApplicationContext();
    if (context == null) {
      context = this.mContext;
    }
    Configuration config = getConfiguration();
    WindowConfiguration windowConfig = config.windowConfiguration;
    int windowingMode = getWindowingMode();
    if (windowConfig.isPopOver()) {
      super.semSetRoundedCorners(0);
      this.mOverrideRoundedCornerBounds.setEmpty();
      return;
    }
    boolean z = true;
    boolean isFlipLargeCoverScreen = this.mContext.getDisplayId() == 1;
    this.mRotation = config.windowConfiguration.getRotation();
    this.mDisplayRotation = config.windowConfiguration.getDisplayRotation();
    boolean show = false;
    if (windowingMode == 1) {
      WindowManager.LayoutParams attrs = this.mWindow.getAttributes();
      boolean isInputMethod = attrs.type == 2011;
      boolean isFullscreen = attrs.isFullscreen();
      if (config.orientation == 2 && this.mHasDisplayCutout) {
        if (isInputMethod
            || (isFullscreen && (attrs.layoutInDisplayCutoutMode != 1 || isFlipLargeCoverScreen))) {
          int corners = 0;
          ViewRootImpl viewRootImpl = getViewRootImpl();
          if (viewRootImpl != null) {
            int i = 4;
            boolean hasFullScreen =
                ((attrs.flags & 1024) == 0 && (attrs.systemUiVisibility & 4) == 0) ? false : true;
            int letterboxDirection = viewRootImpl.mRequestedLetterboxDirection;
            if ((letterboxDirection & 1) != 0) {
              if (!isInputMethod || (isFullscreen && hasFullScreen)) {
                i = 5;
              }
              corners = 0 | i;
            }
            if ((letterboxDirection & 2) != 0) {
              corners |= (!isInputMethod || (isFullscreen && hasFullScreen)) ? 10 : 8;
            }
            if (isFlipLargeCoverScreen) {
              boolean isWallpaper = (attrs.flags & 1048576) != 0;
              if (isWallpaper) {
                if (letterboxDirection == 0
                    && attrs.layoutInDisplayCutoutMode == 3
                    && this.mRotation == 2) {
                  corners |= 3;
                }
              } else {
                if ((letterboxDirection & 4) != 0) {
                  corners |= 3;
                }
                if ((letterboxDirection & 8) != 0) {
                  corners |= 12;
                }
              }
            }
            viewRootImpl.updateAppliedLetterboxDirection(letterboxDirection);
          }
          if (corners != 0) {
            super.semSetRoundedCorners(corners, this.mRoundedCornerRadiusForLetterBox);
            super.semSetRoundedCornerColor(corners, -16777216);
            show = true;
          }
        }
      }
      if (config.orientation == 1
          && this.mIsShowNavigationBar
          && attrs.type != 2037
          && !isInputMethod) {
        if (this.mForceHideRoundedCorner) {
          needToDrawAboveNavBar = false;
        } else if (this.mForceRoundedCorner) {
          boolean hasTaskBar =
              Settings.Global.getInt(context.getContentResolver(), Settings.Global.SEM_TASK_BAR, 0)
                  == 1;
          needToDrawAboveNavBar =
              this.mNavigationColorViewState.visible
                  && !hasTaskBar
                  && (this.mRoundedCornerMode & 12) == 12;
        } else {
          needToDrawAboveNavBar =
              View.sIsSamsungBasicInteraction && this.mNavigationColorViewState.visible;
          if (needToDrawAboveNavBar) {
            int defaultViewCount = this.mContentRoot != null ? 1 : 0;
            if (defaultViewCount
                        + (this.mStatusColorViewState.view != null ? 1 : 0)
                        + (this.mNavigationColorViewState.view == null ? 0 : 1)
                    < getChildCount()
                && needToDrawAboveNavBar
                && this.mNavigationColorViewState.view != null
                && isChildIntersectsWith(this.mNavigationColorViewState.view)) {
              needToDrawAboveNavBar = false;
            }
          }
        }
        if (needToDrawAboveNavBar && this.mGestureNavBarEnabled && !this.mGestureHintEnabled) {
          needToDrawAboveNavBar = false;
        }
        if (needToDrawAboveNavBar
            && isFullscreen
            && (this.mWindow.getContext() instanceof Activity)) {
          needToDrawAboveNavBar = false;
        }
        if (needToDrawAboveNavBar) {
          super.semSetRoundedCorners(12, this.mRoundedCornerRadius);
          super.semSetRoundedCornerColor(12, getCurrentColor(this.mNavigationColorViewState));
        }
        show = needToDrawAboveNavBar;
      }
    } else if (shouldDrawRoundedCornerForSplit()) {
      Rect windowBounds = getCurrentBounds(context);
      if ((this.mWindow.mIsFloating || !isFullSize())
          && (windowBounds.width() > getWidth() || windowBounds.height() > getHeight())) {
        z = false;
      }
      boolean shouldUpdateRoundedCorner = z;
      if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER && !this.mWindow.isFloating()) {
        updateRoundedCornerForMultiSplit(context);
      } else if (shouldUpdateRoundedCorner) {
        updateRoundedCornerForSplit(context);
      }
      show = true;
      this.mForceRoundedCorner = false;
    } else if (isFreeformMode()) {
      super.semSetRoundedCorners(15, this.mMultiWindowRoundedCornerRadius);
      super.semSetRoundedCornerColor(15, this.mFreeformOutlineColor);
      show = true;
    }
    if (!show) {
      super.semSetRoundedCorners(0);
      this.mOverrideRoundedCornerBounds.setEmpty();
      return;
    }
    int i2 = this.mRotation;
    if ((i2 == 0 || i2 == 2) && !isSplitMode()) {
      this.mOverrideRoundedCornerBounds.set(
          this.mLastLeftInset,
          this.mLastTopInset,
          getWidth() - this.mLastRightInset,
          getHeight() - this.mLastBottomInset);
    } else {
      this.mOverrideRoundedCornerBounds.set(
          this.mLastLeftInset, 0, getWidth() - this.mLastRightInset, getHeight());
    }
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
        && this.mRotation == this.mDisplayRotation) {
      outRoundedCornerBounds.set(this.mOverrideRoundedCornerBounds);
      return true;
    }
    return false;
  }

  private boolean isChildIntersectsWith(View colorView) {
    this.mTmpColorViewBounds.set(
        colorView.getLeft(), colorView.getTop(), colorView.getRight(), colorView.getBottom());
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
                this.mContext.getContentResolver(), Settings.Global.NAVIGATIONBAR_GESTURE_HINT, 1)
            != 0;
  }

  private boolean shouldDrawRoundedCornerForSplit() {
    return isSplitMode() && getStagePosition() != 0;
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
        Log.m96e(TAG, "updateRoundedCornerForSplit: Invalid position 0x" + stagePosition);
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
    super.semSetRoundedCornerColor(roundedCorner, MultiWindowUtils.getRoundedCornerColor(context));
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

    /* renamed from: id */
    final int f999id;
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
      this.f999id = id;
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

    public boolean isVisible(int requestedVisibleTypes, int color, int windowFlags, boolean force) {
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
          Log.m96e(
              DecorView.this.mLogTag,
              "Destroying unexpected ActionMode instance of TYPE_PRIMARY; "
                  + mode
                  + " was not the current primary action mode! Expected "
                  + DecorView.this.mPrimaryActionMode);
        }
        if (!isFloating && mode.getType() == 1) {
          Log.m96e(
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
          DecorView decorView = DecorView.this;
          decorView.removeCallbacks(decorView.mShowPrimaryActionModePopup);
        }
        if (DecorView.this.mPrimaryActionModeView != null) {
          DecorView.this.endOnGoingFadeAnimation();
          final ActionBarContextView lastActionModeView = DecorView.this.mPrimaryActionModeView;
          DecorView decorView2 = DecorView.this;
          decorView2.mFadeAnim =
              ObjectAnimator.ofFloat(
                  decorView2.mPrimaryActionModeView,
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
                  if (lastActionModeView == DecorView.this.mPrimaryActionModeView) {
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
      if (DecorView.this.mWindow.getCallback() != null && !DecorView.this.mWindow.isDestroyed()) {
        try {
          DecorView.this.mWindow.getCallback().onActionModeFinished(mode);
        } catch (AbstractMethodError e) {
        }
      }
      DecorView.this.requestFitSystemWindows();
    }

    @Override // android.view.ActionMode.Callback2
    public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
      ActionMode.Callback callback = this.mWrapped;
      if (callback instanceof ActionMode.Callback2) {
        ((ActionMode.Callback2) callback).onGetContentRect(mode, view, outRect);
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
    View view;
    if (!this.mCalledDisplayCutoutBackgroundColor
        && (View.sIsSamsungBasicInteraction || View.sIsDisplayCutoutBackground)) {
      this.mDisplayCutoutBackgroundColor = getCurrentColor(this.mNavigationColorViewState);
    }
    if (insets == null && (view = this.mDisplayCutoutBackgroundView) != null) {
      view.setBackgroundColor(this.mDisplayCutoutBackgroundColor);
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
                this.mStatusColorViewState.visible ? this.mStatusColorViewState.view : null;
            if (statusBg != null && statusBg.getVisibility() == 0) {
              topMargin = statusBg.getLayoutParams().height;
            }
            View naviBg =
                this.mNavigationColorViewState.visible ? this.mNavigationColorViewState.view : null;
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
                this.mStatusColorViewState.visible ? this.mStatusColorViewState.view : null;
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
          View view2 = this.mDisplayCutoutBackgroundView;
          if (view2 != null) {
            if (view2.getParent() != this) {
              View view3 = new View(getContext());
              this.mDisplayCutoutBackgroundView = view3;
              addView(view3);
            }
          } else {
            View view4 = new View(getContext());
            this.mDisplayCutoutBackgroundView = view4;
            addView(view4);
          }
          if (this.mDisplayCutoutBackgroundView.getTag() == null) {
            this.mDisplayCutoutBackgroundView.setTag("DisplayCutoutBackgroundView");
            this.mDisplayCutoutBackgroundView.setElevation(-1.0f);
          }
          FrameLayout.LayoutParams param =
              (FrameLayout.LayoutParams) this.mDisplayCutoutBackgroundView.getLayoutParams();
          param.gravity = gravity;
          param.width = width;
          param.height = height;
          param.topMargin = topMargin;
          param.bottomMargin = bottomMargin;
          this.mDisplayCutoutBackgroundView.setBackgroundColor(this.mDisplayCutoutBackgroundColor);
          this.mDisplayCutoutBackgroundView.requestLayout();
          return;
        }
        View view5 = this.mDisplayCutoutBackgroundView;
        if (view5 != null && view5.getParent() == this) {
          removeView(this.mDisplayCutoutBackgroundView);
          this.mDisplayCutoutBackgroundView = null;
          return;
        }
        return;
      }
    }
    View view6 = this.mDisplayCutoutBackgroundView;
    if (view6 != null) {
      removeView(view6);
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
    this.mKnoxBadge =
        this.mPackageManagerForKnoxBadge.getUserBadgeForDensity(new UserHandle(this.mUserId), 0);
    Drawable customReverseBadgeForCustomContainer =
        SemPersonaManager.getCustomReverseBadgeForCustomContainer(
            new UserHandle(this.mUserId), 0, this.mContext);
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
  public boolean shouldHideProfileBadge(
      boolean isGestureHintOff, boolean taskbarEnabled, int displayType) {
    if (((getParent() instanceof ViewGroup) && this.mKnoxBadgeView != null) || isPopOverState()) {
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
                            DecorView.this.mContext.getContentResolver(),
                            Settings.Global.NAVIGATIONBAR_GESTURES_DETAIL_TYPE,
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
                DecorView.this.getContext().getResources().getConfiguration().semDisplayDeviceType;
            if (DecorView.this.shouldHideProfileBadge(
                isGestureHintOff, taskbarEnabled, displayType)) {
              DecorView.this.hideKnoxBadge();
              return;
            }
            int rotation = DecorView.this.mWm.getDefaultDisplay().getRotation();
            boolean isMultiWindow = DecorView.this.getWindowingMode() == 6;
            boolean isRotation_90 = rotation == 1;
            boolean isRotation_270 = rotation == 3;
            if (displayType != 0 && !SemViewUtils.isTablet()) {
              isLargeDisplay = false;
            }
            if (DecorView.this.mIsDexEnabled) {
              Context candiateContext = DecorView.this.getContext().getApplicationContext();
              int appDensityDpi = candiateContext.getResources().getConfiguration().densityDpi;
              int densityDpi =
                  DecorView.this.getContext().getResources().getConfiguration().densityDpi;
              DecorView.this.mDensityRatio = densityDpi / appDensityDpi;
            }
            int badgeW =
                (int)
                    (DecorView.this.mKnoxBadge.getIntrinsicWidth() * DecorView.this.mDensityRatio);
            int badgeH =
                (int)
                    (DecorView.this.mKnoxBadge.getIntrinsicHeight() * DecorView.this.mDensityRatio);
            int navigation_bar_height = 0;
            WindowInsets rootInsets = DecorView.this.getRootWindowInsets();
            if (rootInsets != null) {
              DecorView.this.mKnoxBadgeInsets =
                  rootInsets.getInsetsIgnoringVisibility(
                      WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
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
              DecorView decorView = DecorView.this;
              decorView.mKnoxBadgeStartX = decorView.mKnoxLayoutRight - badgeW;
              DecorView decorView2 = DecorView.this;
              decorView2.mKnoxBadgeStartY =
                  (decorView2.mKnoxLayoutBottom - badgeH) - navigation_bar_height;
              if (isRotation_270) {
                DecorView.this.mKnoxBadgeStartX -= DecorView.sKnoxBadgeRightCutout;
              }
              DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mKnoxBadge);
            } else {
              int position =
                  DecorView.this
                      .getResources()
                      .getConfiguration()
                      .windowConfiguration
                      .getStagePosition();
              if (isRotation_270) {
                if (!isGestureNavBarInCenter) {
                  DecorView decorView3 = DecorView.this;
                  decorView3.mKnoxBadgeStartX = decorView3.mKnoxLayoutLeft + navigation_bar_height;
                  DecorView decorView4 = DecorView.this;
                  decorView4.mKnoxBadgeStartY = decorView4.mKnoxLayoutBottom - badgeH;
                } else {
                  DecorView decorView5 = DecorView.this;
                  decorView5.mKnoxBadgeStartX = decorView5.mKnoxLayoutLeft;
                  DecorView decorView6 = DecorView.this;
                  decorView6.mKnoxBadgeStartY =
                      (decorView6.mKnoxLayoutBottom - badgeH) - navigation_bar_height;
                }
                if (isMultiWindow && position == 32) {
                  DecorView.this.mKnoxBadgeStartX = 0;
                }
                DecorView.this.mKnoxBadgeView.setBackgroundDrawable(
                    DecorView.this.mReverseKnoxBadge);
              } else if (isRotation_90) {
                if (isGestureNavBarInCenter) {
                  DecorView decorView7 = DecorView.this;
                  decorView7.mKnoxBadgeStartX = decorView7.mKnoxLayoutRight - badgeW;
                  DecorView decorView8 = DecorView.this;
                  decorView8.mKnoxBadgeStartY =
                      (decorView8.mKnoxLayoutBottom - badgeH) - navigation_bar_height;
                } else {
                  DecorView decorView9 = DecorView.this;
                  decorView9.mKnoxBadgeStartX =
                      (decorView9.mKnoxLayoutRight - badgeW) - navigation_bar_height;
                  DecorView decorView10 = DecorView.this;
                  decorView10.mKnoxBadgeStartY = decorView10.mKnoxLayoutBottom - badgeH;
                }
                if (isMultiWindow && position == 8) {
                  DecorView decorView11 = DecorView.this;
                  decorView11.mKnoxBadgeStartX = decorView11.mKnoxLayoutRight - badgeW;
                }
                DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mKnoxBadge);
              } else {
                DecorView decorView12 = DecorView.this;
                decorView12.mKnoxBadgeStartX = decorView12.mKnoxLayoutRight - badgeW;
                DecorView decorView13 = DecorView.this;
                decorView13.mKnoxBadgeStartY =
                    (decorView13.mKnoxLayoutBottom - badgeH) - navigation_bar_height;
                DecorView.this.mKnoxBadgeView.setBackgroundDrawable(DecorView.this.mKnoxBadge);
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

  public void hidden_semSetForceHideRoundedCorner(boolean hide) {
    this.mForceHideRoundedCorner = hide;
    Log.m98i(TAG, "hidden_semSetForceHideRoundedCorner() : " + hide);
    super.semSetRoundedCorners(0);
  }

  public boolean isNonFullscreenWindowInFreeform() {
    return isFreeformMode()
        && isActivity()
        && !isFullSize()
        && this.mWindow.getAttributes().type >= 1
        && this.mWindow.getAttributes().type <= 99;
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
      Log.m94d(TAG, "changed bg alpha=" + alpha);
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
      Log.m94d(TAG, "changed content alpha=" + alpha);
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
    ObjectAnimator animContent = ObjectAnimator.ofFloat(this, this.POP_OVER_CONTENT_ALPHA, 1.0f);
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
    ObjectAnimator animContent = ObjectAnimator.ofFloat(this, this.POP_OVER_CONTENT_ALPHA, 0.0f);
    animContent.setDuration(100L);
    AnimatorSet animSet = new AnimatorSet();
    animSet.playTogether(animContent, animBackground);
    animSet.start();
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
      }
      ViewOutlineProvider outlineProvider = getOutlineProvider();
      ViewOutlineProvider viewOutlineProvider = this.mLastOutlineProvider;
      if (outlineProvider != viewOutlineProvider) {
        setOutlineProvider(viewOutlineProvider);
      }
    }
  }

  public void releaseActivityFocusIfNeeded() {
    Window.Callback cb = this.mWindow.getCallback();
    if (this.mStayFocus && cb != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
      cb.onWindowFocusChanged(false);
    }
    this.mStayFocus = false;
  }

  private void requestInvalidateRenderNode(String msg) {
    ViewRootImpl viewRootImpl;
    if (!isResizing() && (viewRootImpl = getViewRootImpl()) != null) {
      viewRootImpl.requestInvalidateRootRenderNode();
      if (DEBUG_DRAW) {
        Log.m98i(TAG, "requestInvalidateRootRenderNode: msg=" + msg);
      }
    }
  }

  public void drawFrameIfNeeded(Canvas canvas) {
    boolean isActivity = isActivity();
    boolean isFullSizeWindow = isFullSize();
    if ((!isActivity && !isFullSizeWindow) || (getParent() instanceof ViewGroup)) {
      return;
    }
    boolean isFreeform = isFreeformMode();
    boolean isResizing = isResizing();
    if ((this.mWindow.mIsStartingWindow || isFreeform)
        && !this.mWindow.mIsFloating
        && !isResizing) {
      if (this.mIsDexEnabled && isFullscreenMode()) {
        return;
      }
      if (this.mFrameDrawHelper == null) {
        this.mFrameDrawHelper = new FrameDrawHelper(this);
      }
      this.mFrameDrawHelper.updateResources(isActivity() ? getConfiguration() : null);
      this.mFrameDrawHelper.drawFrame(canvas);
    }
  }

  public static float getDensity(View view) {
    Configuration config = view.getResources().getConfiguration();
    Context context = view.getContext();
    float densityDpi = config.densityDpi;
    if (config.isDesktopModeEnabled()) {
      densityDpi = 160.0f;
      SemDesktopModeManager desktopModeManager =
          (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
      if (desktopModeManager != null
          && desktopModeManager.getDesktopModeState().getDisplayType() == 101) {
        densityDpi = 280.0f;
      }
      Log.m98i(TAG, "updateDisplayMetrics: isDexEnabled=true, densityDpi=" + densityDpi);
    }
    float density = densityDpi / 160.0f;
    try {
      ActivityThread activityThread = ActivityThread.currentActivityThread();
      float dssFactor = 1.0f;
      if (activityThread != null) {
        dssFactor = activityThread.getDssScale();
      }
      String packageName = context.getPackageName();
      Log.m98i(TAG, "updateDisplayMetrics: packageName=" + packageName + ", dsf=" + dssFactor);
      if (dssFactor != 1.0f) {
        return Math.round((density * dssFactor) * 10000.0f) / 10000.0f;
      }
      return density;
    } catch (Exception e) {
      Log.m96e(TAG, "updateDisplayMetrics: error while getting dsf. e=" + e);
      return density;
    }
  }

  public void onWindowingModeChanged(int windowingMode, boolean split) {
    this.mWindowingMode = windowingMode;
    boolean updateWindowFormat = false;
    int oldDisplayDeviceType = this.mLastDisplayDeviceType;
    int i = getResources().getConfiguration().semDisplayDeviceType;
    this.mLastDisplayDeviceType = i;
    if (i != oldDisplayDeviceType) {
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

  public void setLastBackgroundResource(int redId) {
    this.mLastBackgroundResource = redId;
  }

  @Override // android.view.View
  public int getLastBackgroundResource() {
    return this.mLastBackgroundResource;
  }

  private boolean shouldConsumeCaptionBar() {
    int i = this.mLastTopInset;
    return i > 0
        && i == this.mLastCaptionHeight
        && CoreRune.MW_CAPTION_SHELL_DEX
        && this.mIsDexEnabled
        && (isFullscreenMode() || isSplitMode())
        && !isImmersiveMode()
        && getResources().getConfiguration().windowConfiguration.getActivityType() == 1;
  }

  public void updateCaptionHeightIfNeeded(WindowInsets insets) {
    int captionHeight = insets.getInsetsIgnoringVisibility(WindowInsets.Type.captionBar()).top;
    if (captionHeight != 0) {
      this.mLastCaptionHeight = captionHeight;
      requestInvalidateRenderNode("updateCaptionHeightIfNeeded");
    }
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

  @Override // android.view.View
  public void resetContentCaptureSession() {
    super.resetContentCaptureSession();
    Context context = getContext();
    if (context instanceof DecorContext) {
      ((DecorContext) context).resetContextCaptureManager();
    }
  }
}
