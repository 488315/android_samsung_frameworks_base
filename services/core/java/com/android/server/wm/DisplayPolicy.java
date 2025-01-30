package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.ResourcesManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayInfo;
import android.view.InsetsFlags;
import android.view.InsetsFrameProvider;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.TwoFingerSwipeGestureDetector;
import android.view.ViewDebug;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowLayout;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.window.ClientWindowFrames;
import com.android.internal.policy.ForceShowNavBarSettingsObserver;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.util.ScreenshotRequest;
import com.android.internal.util.function.TriFunction;
import com.android.internal.view.AppearanceRegion;
import com.android.internal.widget.PointerLocationView;
import com.android.server.LocalServices;
import com.android.server.UiThread;
import com.android.server.display.DisplayPowerController2;
import com.android.server.input.InputManagerService;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.wallpaper.WallpaperManagerInternal;
import com.samsung.android.cocktailbar.CocktailBarManagerInternal;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.cmfa.AuthTouch.IAuthTouchEnableListener;
import com.samsung.cmfa.AuthTouch.TouchEventView;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class DisplayPolicy {
  public final AccessibilityManager mAccessibilityManager;
  public boolean mAllowLockscreenWhenOn;
  public final WindowManagerInternal.AppTransitionListener mAppTransitionListener;
  public volatile boolean mAwake;
  public WindowState mBottomGestureHost;
  public DecorInsets.Cache mCachedDecorInsets;
  public boolean mCanSystemBarsBeShownByUser;
  public final boolean mCarDockEnablesAccelerometer;
  public CocktailBarManagerInternal mCocktailBarInternal;
  public final Context mContext;
  public Resources mCurrentUserResources;
  public final DecorInsets mDecorInsets;
  public final boolean mDeskDockEnablesAccelerometer;
  public final DisplayContent mDisplayContent;
  public boolean mDreamingLockscreen;
  public boolean mEdgeEnabled;
  public DisplayPolicyExt mExt;
  public String mFocusedApp;
  public WindowState mFocusedWindow;
  public final ForceShowNavBarSettingsObserver mForceShowNavBarSettingsObserver;
  public boolean mForceShowNavigationBarEnabled;
  public boolean mFreeformTaskSurfaceOverlappingWithNavBar;
  public final GestureNavigationSettingsObserver mGestureNavigationSettingsObserver;
  public final Handler mHandler;
  public volatile boolean mHasNavigationBar;
  public volatile boolean mHasStatusBar;
  public volatile boolean mHdmiPlugged;
  public final ImmersiveModeConfirmation mImmersiveModeConfirmation;
  public boolean mIsFreeformWindowOverlappingWithNavBar;
  public boolean mIsImmersiveMode;
  public boolean mIsPipWindowOverlappingWithNavBar;
  public boolean mIsResizingByDivider;
  public boolean mIsVisibleBySwipe;
  public volatile boolean mKeyguardDrawComplete;
  public int mLastAppearance;
  public int mLastBehavior;
  public int mLastDisableFlags;
  public boolean mLastFocusIsFullscreenAll;
  public WindowState mLastFocusedWindow;
  public LetterboxDetails[] mLastLetterboxDetails;
  public boolean mLastShowingDream;
  public AppearanceRegion[] mLastStatusBarAppearanceRegions;
  public WindowState mLeftGestureHost;
  public int mLeftGestureInset;
  public final Object mLock;
  public WindowState mNavBarBackgroundWindow;
  public WindowState mNavBarColorWindowCandidate;
  public volatile boolean mNavigationBarAlwaysShowOnSideGesture;
  public volatile boolean mNavigationBarCanMove;
  public volatile WindowState mNotificationShade;
  public long mPendingPanicGestureUptime;
  public volatile boolean mPersistentVrModeEnabled;
  public PointerLocationView mPointerLocationView;
  public RefreshRatePolicy mRefreshRatePolicy;
  public boolean mRemoteInsetsControllerControlsSystemBars;
  public WindowState mRightGestureHost;
  public int mRightGestureInset;
  public volatile boolean mScreenOnEarly;
  public volatile boolean mScreenOnFully;
  public volatile WindowManagerPolicy.ScreenOnListener mScreenOnListener;
  public final ScreenshotHelper mScreenshotHelper;
  public final WindowManagerService mService;
  public boolean mShouldAttachNavBarToAppDuringTransition;
  public boolean mShowingDream;
  public boolean mSkipTransferTouchToStatusBar;
  public StatusBarManagerInternal mStatusBarManagerInternal;
  public SystemGesturesPointerEventListener mSystemGestures;
  public WindowState mSystemUiControllingWindow;
  public WindowManagerInternal.TaskSystemBarsListener mTaskSystemBarsVisibilityListener;
  public WindowState mTopFullscreenOpaqueWindowState;
  public WindowState mTopGestureHost;
  public boolean mTopIsFullscreen;
  public WindowState mTransientWindowState;
  public final TwoFingerSwipeGestureDetector mTwoFingerSwipeGestureDetector;
  public Context mUiContext;
  public volatile boolean mWindowManagerDrawComplete;
  public static final int SHOW_TYPES_FOR_SWIPE =
      WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
  public static final int SHOW_TYPES_FOR_PANIC = WindowInsets.Type.navigationBars();
  public static final boolean USE_CACHED_INSETS_FOR_DISPLAY_SWITCH =
      SystemProperties.getBoolean("persist.wm.debug.cached_insets_switch", true);
  public static final Rect sTmpRect = new Rect();
  public static final Rect sTmpRect2 = new Rect();
  public static final Rect sTmpDisplayCutoutSafe = new Rect();
  public static final ClientWindowFrames sTmpClientFrames = new ClientWindowFrames();
  public final Object mServiceAcquireLock = new Object();
  public volatile int mLidState = -1;
  public volatile int mDockMode = 0;
  public WindowState mStatusBar = null;
  public WindowState mNavigationBar = null;
  public int mNavigationBarPosition = 4;
  public final ArraySet mInsetsSourceWindowsExceptIme = new ArraySet();
  public final ArraySet mSystemBarColorApps = new ArraySet();
  public final ArraySet mRelaunchingSystemBarColorApps = new ArraySet();
  public final ArrayList mStatusBarAppearanceRegionList = new ArrayList();
  public final ArrayList mStatusBarBackgroundWindows = new ArrayList();
  public final ArrayList mLetterboxDetails = new ArrayList();
  public int mLastRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
  public final Rect mStatusBarColorCheckedBounds = new Rect();
  public final Rect mStatusBarBackgroundCheckedBounds = new Rect();
  public boolean mLastFocusIsFullscreen = false;
  public final WindowLayout mWindowLayout = new WindowLayout();
  public int mNavBarOpacityMode = 0;
  public boolean mIsCmfaStarted = false;
  public TouchEventView mTouchEventView = null;
  public int mTouchEventViewHash = -1;
  public int mDexTaskbarHeight = 0;
  public WindowState mDefaultStatusBar = null;
  public WindowState mDefaultNotificationShade = null;
  public WindowState mDefaultNavigationBar = null;
  public Rect mTmpFrame = new Rect();
  public Rect mWindowBounds = new Rect();
  public boolean mIsCaptionImmersiveMode = false;
  public boolean mWasStatusInvisible = false;
  public int mImmersiveTaskId = -1;
  public boolean mNeedToUpdateDecorInsets = false;
  public final Runnable mHiddenNavPanic =
      new Runnable() { // from class: com.android.server.wm.DisplayPolicy.4
        @Override // java.lang.Runnable
        public void run() {
          synchronized (DisplayPolicy.this.mLock) {
            if (DisplayPolicy.this.mService.mPolicy.isUserSetupComplete()) {
              DisplayPolicy.this.mPendingPanicGestureUptime = SystemClock.uptimeMillis();
              DisplayPolicy.this.updateSystemBarAttributes();
            }
          }
        }
      };
  public IAuthTouchEnableListener mAuthTouchEnableListener =
      new IAuthTouchEnableListener.Stub() { // from class: com.android.server.wm.DisplayPolicy.5
        public void notifyTouchEventEnabled(boolean z, boolean z2) {
          Slog.i(
              StartingSurfaceController.TAG,
              "notifyTouchEventEnabled:" + z + "," + z2 + "," + DisplayPolicy.this.mIsCmfaStarted);
          Message message = new Message();
          if (z) {
            if (DisplayPolicy.this.mIsCmfaStarted) {
              message.what = 8;
              if (z2) {
                message.arg1 = 1;
              } else {
                message.arg1 = 2;
              }
            } else {
              message.what = 7;
              if (z2) {
                message.arg1 = 1;
              } else {
                message.arg1 = 2;
              }
            }
          } else {
            message.what = 8;
          }
          DisplayPolicy.this.mHandler.sendMessage(message);
        }
      };

  public static boolean isNavBarEmpty(int i) {
    return (i & 23068672) == 23068672;
  }

  public boolean areSystemBarsForcedConsumedLw() {
    return false;
  }

  public final int clearNavBarOpaqueFlag(int i) {
    return i & (-3);
  }

  public StatusBarManagerInternal getStatusBarManagerInternal() {
    StatusBarManagerInternal statusBarManagerInternal;
    synchronized (this.mServiceAcquireLock) {
      if (this.mStatusBarManagerInternal == null) {
        this.mStatusBarManagerInternal =
            (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
      }
      statusBarManagerInternal = this.mStatusBarManagerInternal;
    }
    return statusBarManagerInternal;
  }

  public void requestTransientTaskBar() {
    WindowState windowState = this.mNavigationBar;
    if (windowState == null || !windowState.isDrawFinishedLw()) {
      return;
    }
    InsetsPolicy insetsPolicy = getInsetsPolicy();
    if (insetsPolicy.isTransient(WindowInsets.Type.navigationBars())) {
      insetsPolicy.abortTransient();
    }
    requestTransientBars(this.mNavigationBar, false);
  }

  public class PolicyHandler extends Handler {
    public PolicyHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 4) {
        DisplayPolicy.this.enablePointerLocation();
        return;
      }
      if (i == 5) {
        DisplayPolicy.this.disablePointerLocation();
        return;
      }
      if (i == 7) {
        int i2 = message.arg1;
        if (i2 == 1) {
          DisplayPolicy.this.enableTouchListener(false);
          return;
        } else {
          if (i2 == 2) {
            DisplayPolicy.this.enableTouchListener(false);
            return;
          }
          return;
        }
      }
      if (i != 8) {
        return;
      }
      DisplayPolicy.this.disableTouchListener();
      int i3 = message.arg1;
      if (i3 == 1) {
        DisplayPolicy.this.startEnableTouchEvent(true);
      } else if (i3 == 2) {
        DisplayPolicy.this.startEnableTouchEvent(false);
      }
    }
  }

  public DisplayPolicy(WindowManagerService windowManagerService, DisplayContent displayContent) {
    this.mService = windowManagerService;
    Context createDisplayContext =
        displayContent.isDefaultDisplay
            ? windowManagerService.mContext
            : windowManagerService.mContext.createDisplayContext(displayContent.getDisplay());
    this.mContext = createDisplayContext;
    this.mUiContext =
        displayContent.isDefaultDisplay
            ? windowManagerService.mAtmService.getUiContext()
            : windowManagerService.mAtmService.mSystemThread.getSystemUiContext(
                displayContent.getDisplayId());
    this.mDisplayContent = displayContent;
    this.mDecorInsets = new DecorInsets(displayContent);
    this.mLock = windowManagerService.getWindowManagerLock();
    this.mExt = new DisplayPolicyExt(createDisplayContext, windowManagerService, this);
    int displayId = displayContent.getDisplayId();
    Resources resources = createDisplayContext.getResources();
    this.mCarDockEnablesAccelerometer =
        resources.getBoolean(R.bool.config_battery_percentage_setting_available);
    this.mDeskDockEnablesAccelerometer =
        resources.getBoolean(R.bool.config_cecVolumeControlModeDisabled_allowed);
    this.mCanSystemBarsBeShownByUser =
        !resources.getBoolean(R.bool.config_remoteInsetsControllerControlsSystemBars)
            || resources.getBoolean(R.bool.config_navBarTapThrough);
    this.mAccessibilityManager =
        (AccessibilityManager) createDisplayContext.getSystemService("accessibility");
    if (!displayContent.isDefaultDisplay) {
      this.mAwake = true;
      this.mScreenOnEarly = true;
      this.mScreenOnFully = true;
    }
    Looper looper = UiThread.getHandler().getLooper();
    PolicyHandler policyHandler = new PolicyHandler(looper);
    this.mHandler = policyHandler;
    if (!ViewRootImpl.CLIENT_TRANSIENT) {
      this.mSystemGestures =
          new SystemGesturesPointerEventListener(this.mUiContext, policyHandler, new C29051());
      TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector =
          new TwoFingerSwipeGestureDetector(
              this.mUiContext,
              new Function() { // from class:
                               // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                  TwoFingerSwipeGestureDetector.GestureListener createGestureListener;
                  createGestureListener =
                      DisplayPolicy.this.createGestureListener((TwoFingerSwipeGestureDetector) obj);
                  return createGestureListener;
                }
              },
              "DP");
      this.mTwoFingerSwipeGestureDetector = twoFingerSwipeGestureDetector;
      displayContent.registerPointerEventListener(twoFingerSwipeGestureDetector);
      this.mSystemGestures.setDisplayContent(displayContent);
      displayContent.registerPointerEventListener(this.mSystemGestures);
    } else {
      this.mTwoFingerSwipeGestureDetector = null;
    }
    C29062 c29062 = new C29062(displayId);
    this.mAppTransitionListener = c29062;
    displayContent.mAppTransition.registerListenerLocked(c29062);
    displayContent.mTransitionController.registerLegacyListener(c29062);
    this.mImmersiveModeConfirmation =
        new ImmersiveModeConfirmation(
            createDisplayContext,
            looper,
            windowManagerService.mVrModeEnabled,
            this.mCanSystemBarsBeShownByUser,
            this);
    this.mScreenshotHelper =
        displayContent.isDefaultDisplay ? new ScreenshotHelper(createDisplayContext) : null;
    if (displayContent.isDefaultDisplay) {
      this.mHasStatusBar = true;
      this.mHasNavigationBar =
          createDisplayContext.getResources().getBoolean(R.bool.config_predictShowStartingSurface);
    } else {
      this.mHasStatusBar = false;
      this.mHasNavigationBar = displayContent.supportsSystemDecorations();
    }
    this.mRefreshRatePolicy =
        new RefreshRatePolicy(
            windowManagerService,
            displayContent.getDisplayInfo(),
            windowManagerService.mHighRefreshRateDenylist);
    final GestureNavigationSettingsObserver gestureNavigationSettingsObserver =
        new GestureNavigationSettingsObserver(
            policyHandler,
            createDisplayContext,
            new Runnable() { // from class:
                             // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda10
              @Override // java.lang.Runnable
              public final void run() {
                DisplayPolicy.this.lambda$new$0();
              }
            });
    this.mGestureNavigationSettingsObserver = gestureNavigationSettingsObserver;
    Objects.requireNonNull(gestureNavigationSettingsObserver);
    policyHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda11
          @Override // java.lang.Runnable
          public final void run() {
            gestureNavigationSettingsObserver.register();
          }
        });
    final ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver =
        new ForceShowNavBarSettingsObserver(policyHandler, createDisplayContext);
    this.mForceShowNavBarSettingsObserver = forceShowNavBarSettingsObserver;
    forceShowNavBarSettingsObserver.setOnChangeRunnable(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda12
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.this.updateForceShowNavBarSettings();
          }
        });
    this.mForceShowNavigationBarEnabled = forceShowNavBarSettingsObserver.isEnabled();
    Objects.requireNonNull(forceShowNavBarSettingsObserver);
    policyHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda13
          @Override // java.lang.Runnable
          public final void run() {
            forceShowNavBarSettingsObserver.register();
          }
        });
  }

  /* renamed from: com.android.server.wm.DisplayPolicy$1 */
  public class C29051 implements SystemGesturesPointerEventListener.Callbacks {
    public Runnable mOnSwipeFromLeft =
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.C29051.this.onSwipeFromLeft();
          }
        };
    public Runnable mOnSwipeFromTop =
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.C29051.this.onSwipeFromTop();
          }
        };
    public Runnable mOnSwipeFromRight =
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.C29051.this.onSwipeFromRight();
          }
        };
    public Runnable mOnSwipeFromBottom =
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.C29051.this.onSwipeFromBottom();
          }
        };

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onDebug() {}

    public C29051() {}

    public final Insets getControllableInsets(WindowState windowState) {
      if (windowState == null) {
        return Insets.NONE;
      }
      InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
      if (controllableInsetProvider == null) {
        return Insets.NONE;
      }
      return controllableInsetProvider.getSource().calculateInsets(windowState.getBounds(), true);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onSwipeFromTop() {
      synchronized (DisplayPolicy.this.mLock) {
        MultiWindowPointerEventListener multiWindowPointerEventListener =
            DisplayPolicy.this.mDisplayContent.mMultiWindowPointerEventListener;
        MultiWindowEdgeDetector multiWindowEdgeDetector =
            multiWindowPointerEventListener != null
                ? multiWindowPointerEventListener.getMultiWindowEdgeDetector()
                : null;
        if (multiWindowEdgeDetector != null
            && multiWindowPointerEventListener.isAllowCornerGestureState()
            && multiWindowEdgeDetector.isEdge()) {
          return;
        }
        DisplayPolicy displayPolicy = DisplayPolicy.this;
        displayPolicy.requestTransientBars(
            displayPolicy.mTopGestureHost,
            getControllableInsets(DisplayPolicy.this.mTopGestureHost).top > 0);
      }
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onSwipeFromBottom() {
      synchronized (DisplayPolicy.this.mLock) {
        DisplayPolicy displayPolicy = DisplayPolicy.this;
        displayPolicy.requestTransientBars(
            displayPolicy.mBottomGestureHost,
            getControllableInsets(DisplayPolicy.this.mBottomGestureHost).bottom > 0);
      }
    }

    public final boolean allowsSideSwipe(Region region) {
      return DisplayPolicy.this.mNavigationBarAlwaysShowOnSideGesture
          && !DisplayPolicy.this.mSystemGestures.currentGestureStartedInRegion(region);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onSwipeFromRight() {
      Region obtain = Region.obtain();
      synchronized (DisplayPolicy.this.mLock) {
        DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(obtain, null);
        boolean z = getControllableInsets(DisplayPolicy.this.mRightGestureHost).right > 0;
        if (!z && !allowsSideSwipe(obtain)) {
          requestTransientBarsForSideSwipe(DisplayPolicy.this.mRightGestureHost, 2);
        }
        DisplayPolicy displayPolicy = DisplayPolicy.this;
        displayPolicy.requestTransientBars(displayPolicy.mRightGestureHost, z);
      }
      obtain.recycle();
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onSwipeFromLeft() {
      Region obtain = Region.obtain();
      synchronized (DisplayPolicy.this.mLock) {
        DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(obtain, null);
        boolean z = getControllableInsets(DisplayPolicy.this.mLeftGestureHost).left > 0;
        if (!z && !allowsSideSwipe(obtain)) {
          requestTransientBarsForSideSwipe(DisplayPolicy.this.mLeftGestureHost, 1);
        }
        DisplayPolicy displayPolicy = DisplayPolicy.this;
        displayPolicy.requestTransientBars(displayPolicy.mLeftGestureHost, z);
      }
      obtain.recycle();
    }

    public final void requestTransientBarsForSideSwipe(WindowState windowState, int i) {
      if (DisplayPolicy.this.supportTransientEdgeInSplitMode()) {
        WindowState windowState2 = DisplayPolicy.this.mNavigationBar;
        if (DisplayPolicy.this.mNavigationBar == null) {
          DisplayPolicy.this.mSkipTransferTouchToStatusBar = true;
          windowState2 = DisplayPolicy.this.mStatusBar;
        }
        DisplayPolicy.this.requestTransientBars(windowState2, true);
        if (DisplayPolicy.this.mSkipTransferTouchToStatusBar) {
          DisplayPolicy.this.mSkipTransferTouchToStatusBar = false;
          return;
        }
        return;
      }
      if (DisplayPolicy.this.mNavigationBar != null
          && DisplayPolicy.this.mNavigationBar == windowState
          && DisplayPolicy.this.mNavigationBarPosition == i) {
        DisplayPolicy.this.requestTransientBars(windowState, true);
      }
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onFling(int i) {
      if (CoreRune.FW_VRR_PERFORMANCE
          || DisplayPolicy.this.mService.mPowerManagerInternal == null) {
        return;
      }
      DisplayPolicy.this.mService.mPowerManagerInternal.setPowerBoost(0, i);
    }

    public final WindowOrientationListener getOrientationListener() {
      DisplayRotation displayRotation = DisplayPolicy.this.mDisplayContent.getDisplayRotation();
      if (displayRotation != null) {
        return displayRotation.getOrientationListener();
      }
      return null;
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onDown() {
      WindowOrientationListener orientationListener = getOrientationListener();
      if (orientationListener != null) {
        orientationListener.onTouchStart();
      }
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onUpOrCancel() {
      WindowOrientationListener orientationListener = getOrientationListener();
      if (orientationListener != null) {
        orientationListener.onTouchEnd();
      }
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseHoverAtLeft() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromLeft);
      DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromLeft, 500L);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseHoverAtTop() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromTop);
      DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromTop, 500L);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseHoverAtRight() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromRight);
      DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromRight, 500L);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseHoverAtBottom() {
      long j;
      int dexStarShowingDelayTime;
      ActivityRecord activityRecord;
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromBottom);
      WindowState windowState = DisplayPolicy.this.mTopFullscreenOpaqueWindowState;
      if (windowState != null
          && windowState.isDexMode()
          && (activityRecord = windowState.mActivityRecord) != null
          && activityRecord.hasTransientBarShowingDelay()) {
        dexStarShowingDelayTime = windowState.mActivityRecord.getTransientBarShowingDelayMillis();
      } else if (DisplayPolicy.this.mDisplayContent.isDexMode()
          && DisplayPolicy.this.mDisplayContent.mAtmService.mDexController
                  .getDexStarShowingDelayTime()
              >= 0) {
        dexStarShowingDelayTime =
            DisplayPolicy.this.mDisplayContent.mAtmService.mDexController
                .getDexStarShowingDelayTime();
      } else {
        j = 500;
        DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromBottom, j);
      }
      j = dexStarShowingDelayTime;
      DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromBottom, j);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseLeaveFromLeft() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromLeft);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseLeaveFromTop() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromTop);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseLeaveFromRight() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromRight);
    }

    @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
    public void onMouseLeaveFromBottom() {
      DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromBottom);
    }
  }

  /* renamed from: com.android.server.wm.DisplayPolicy$2 */
  public class C29062 extends WindowManagerInternal.AppTransitionListener {
    public Runnable mAppTransitionCancelled;
    public Runnable mAppTransitionFinished;
    public Runnable mAppTransitionPending;
    public final /* synthetic */ int val$displayId;

    public C29062(final int i) {
      this.val$displayId = i;
      this.mAppTransitionPending =
          new Runnable() { // from class:
                           // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              DisplayPolicy.C29062.this.lambda$$0(i);
            }
          };
      this.mAppTransitionCancelled =
          new Runnable() { // from class:
                           // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              DisplayPolicy.C29062.this.lambda$$1(i);
            }
          };
      this.mAppTransitionFinished =
          new Runnable() { // from class:
                           // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              DisplayPolicy.C29062.this.lambda$$2(i);
            }
          };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$$0(int i) {
      StatusBarManagerInternal statusBarManagerInternal =
          DisplayPolicy.this.getStatusBarManagerInternal();
      if (statusBarManagerInternal != null) {
        statusBarManagerInternal.appTransitionPending(i);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$$1(int i) {
      StatusBarManagerInternal statusBarManagerInternal =
          DisplayPolicy.this.getStatusBarManagerInternal();
      if (statusBarManagerInternal != null) {
        statusBarManagerInternal.appTransitionCancelled(i);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$$2(int i) {
      StatusBarManagerInternal statusBarManagerInternal =
          DisplayPolicy.this.getStatusBarManagerInternal();
      if (statusBarManagerInternal != null) {
        statusBarManagerInternal.appTransitionFinished(i);
      }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public void onAppTransitionPendingLocked() {
      DisplayPolicy.this.mHandler.post(this.mAppTransitionPending);
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public int onAppTransitionStartingLocked(final long j, final long j2) {
      DisplayPolicy.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              DisplayPolicy.C29062.this.lambda$onAppTransitionStartingLocked$3(j, j2);
            }
          });
      return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAppTransitionStartingLocked$3(long j, long j2) {
      StatusBarManagerInternal statusBarManagerInternal =
          DisplayPolicy.this.getStatusBarManagerInternal();
      if (statusBarManagerInternal != null) {
        statusBarManagerInternal.appTransitionStarting(
            DisplayPolicy.this.mContext.getDisplayId(), j, j2);
      }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public void onAppTransitionCancelledLocked(boolean z) {
      DisplayPolicy.this.mHandler.post(this.mAppTransitionCancelled);
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public void onAppTransitionFinishedLocked(IBinder iBinder) {
      DisplayPolicy.this.mHandler.post(this.mAppTransitionFinished);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    synchronized (this.mLock) {
      onConfigurationChanged();
      if (!ViewRootImpl.CLIENT_TRANSIENT) {
        this.mSystemGestures.onConfigurationChanged();
      }
      this.mDisplayContent.updateSystemGestureExclusion();
    }
  }

  public final TwoFingerSwipeGestureDetector.GestureListener createGestureListener(
      final TwoFingerSwipeGestureDetector twoFingerSwipeGestureDetector) {
    return new TwoFingerSwipeGestureDetector
        .GestureListener() { // from class: com.android.server.wm.DisplayPolicy.3
      public void onDetecting() {
        synchronized (DisplayPolicy.this.mLock) {
          if (!DisplayPolicy.this.mDisplayContent.mAtmService.mMultiTaskingController
              .isFullToSplitEnabled()) {
            twoFingerSwipeGestureDetector.cancel();
          } else {
            twoFingerSwipeGestureDetector.init(
                DisplayPolicy.this.mDisplayContent.getBounds(),
                DisplayPolicy.this.mDisplayContent.mBaseDisplayDensity / 160,
                13);
          }
        }
      }

      public void onCommitted(int i) {
        synchronized (DisplayPolicy.this.mLock) {
          Region obtain = Region.obtain();
          DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(obtain, null);
          boolean z = !DisplayPolicy.this.mSystemGestures.currentGestureStartedInRegion(obtain);
          if (DisplayPolicy.this.isTwoFingerSwipeGestureAcceptable(i)
              && z
              && (i == 1 || i == 3 || i == 4)) {
            DisplayPolicy displayPolicy = DisplayPolicy.this;
            displayPolicy.requestTransientBars(displayPolicy.mNavigationBar, false);
          }
        }
      }
    };
  }

  public final boolean isTwoFingerSwipeGestureAcceptable(int i) {
    TaskDisplayArea defaultTaskDisplayArea = this.mDisplayContent.getDefaultTaskDisplayArea();
    if (defaultTaskDisplayArea == null || defaultTaskDisplayArea.isSplitScreenModeActivated()) {
      return false;
    }
    if (CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE
        && !MultiWindowUtils.isInSubDisplay(this.mContext)) {
      return i == 1 || i == 3 || i == 4;
    }
    int i2 = defaultTaskDisplayArea.getConfiguration().orientation;
    if (i2 == 1) {
      return i == 4;
    }
    if (i2 == 2) {
      return i == 1 || i == 3;
    }
    return false;
  }

  public final void updateForceShowNavBarSettings() {
    synchronized (this.mLock) {
      this.mForceShowNavigationBarEnabled = this.mForceShowNavBarSettingsObserver.isEnabled();
      updateSystemBarAttributes();
    }
  }

  public void systemReady() {
    if (!ViewRootImpl.CLIENT_TRANSIENT) {
      this.mSystemGestures.systemReady();
    }
    if (this.mService.mPointerLocationEnabled) {
      setPointerLocationEnabled(true);
    }
    startEnableTouchEvent(false);
    Slog.i(
        StartingSurfaceController.TAG,
        "systemReady() >> CMFA startEnableTouchEvent called for Display Id : " + getDisplayId());
    this.mImmersiveModeConfirmation.systemReady();
  }

  public int getDisplayId() {
    return this.mDisplayContent.getDisplayId();
  }

  public void setHdmiPlugged(boolean z) {
    setHdmiPlugged(z, false);
  }

  public void setHdmiPlugged(boolean z, boolean z2) {
    if (z2 || this.mHdmiPlugged != z) {
      this.mHdmiPlugged = z;
      this.mService.updateRotation(true, true);
      Intent intent = new Intent("android.intent.action.HDMI_PLUGGED");
      intent.addFlags(67108864);
      intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, z);
      this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }
  }

  public boolean isHdmiPlugged() {
    return this.mHdmiPlugged;
  }

  public boolean isCarDockEnablesAccelerometer() {
    return this.mCarDockEnablesAccelerometer;
  }

  public boolean isDeskDockEnablesAccelerometer() {
    return this.mDeskDockEnablesAccelerometer;
  }

  public void setPersistentVrModeEnabled(boolean z) {
    this.mPersistentVrModeEnabled = z;
  }

  public boolean isPersistentVrModeEnabled() {
    return this.mPersistentVrModeEnabled;
  }

  public void setDockMode(int i) {
    this.mDockMode = i;
  }

  public int getDockMode() {
    return this.mDockMode;
  }

  public boolean hasNavigationBar() {
    return this.mHasNavigationBar;
  }

  public boolean hasSideGestures() {
    return this.mHasNavigationBar && (this.mLeftGestureInset > 0 || this.mRightGestureInset > 0);
  }

  public boolean navigationBarCanMove() {
    return this.mNavigationBarCanMove;
  }

  public void setLidState(int i) {
    this.mLidState = i;
  }

  public int getLidState() {
    return this.mLidState;
  }

  public void setAwake(boolean z) {
    synchronized (this.mLock) {
      if (z == this.mAwake) {
        return;
      }
      this.mAwake = z;
      if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY
          && z
          && this.mDisplayContent.mWmService.isFolded()) {
        this.mService.mAtmService.mMultiWindowFoldController.scheduleWakeUpInFoldingState(
            this.mDisplayContent.isSleeping());
      }
      if (this.mDisplayContent.isDefaultDisplay) {
        this.mService.mAtmService.mKeyguardController.updateDeferTransitionForAod(this.mAwake);
      }
    }
  }

  public boolean isAwake() {
    return this.mAwake;
  }

  public boolean isScreenOnEarly() {
    return this.mScreenOnEarly;
  }

  public boolean isScreenOnFully() {
    return this.mScreenOnFully;
  }

  public boolean isKeyguardDrawComplete() {
    return this.mKeyguardDrawComplete;
  }

  public boolean isWindowManagerDrawComplete() {
    return this.mWindowManagerDrawComplete;
  }

  public boolean isForceShowNavigationBarEnabled() {
    return this.mForceShowNavigationBarEnabled;
  }

  public WindowManagerPolicy.ScreenOnListener getScreenOnListener() {
    return this.mScreenOnListener;
  }

  public boolean isRemoteInsetsControllerControllingSystemBars() {
    return this.mRemoteInsetsControllerControlsSystemBars;
  }

  public void setRemoteInsetsControllerControlsSystemBars(boolean z) {
    this.mRemoteInsetsControllerControlsSystemBars = z;
  }

  public void screenTurnedOn(WindowManagerPolicy.ScreenOnListener screenOnListener) {
    synchronized (this.mLock) {
      this.mScreenOnEarly = true;
      this.mScreenOnFully = false;
      this.mKeyguardDrawComplete = false;
      this.mWindowManagerDrawComplete = false;
      this.mScreenOnListener = screenOnListener;
    }
  }

  public void screenTurnedOff() {
    synchronized (this.mLock) {
      this.mScreenOnEarly = false;
      this.mScreenOnFully = false;
      this.mKeyguardDrawComplete = false;
      this.mWindowManagerDrawComplete = false;
      this.mScreenOnListener = null;
      this.mExt.screenTurnedOff();
    }
  }

  public boolean finishKeyguardDrawn() {
    synchronized (this.mLock) {
      if (this.mScreenOnEarly && !this.mKeyguardDrawComplete) {
        this.mKeyguardDrawComplete = true;
        this.mWindowManagerDrawComplete = false;
        return true;
      }
      return false;
    }
  }

  public boolean finishWindowsDrawn() {
    synchronized (this.mLock) {
      if (this.mScreenOnEarly && !this.mWindowManagerDrawComplete) {
        this.mWindowManagerDrawComplete = true;
        return true;
      }
      return false;
    }
  }

  public boolean finishScreenTurningOn() {
    synchronized (this.mLock) {
      if (ProtoLogCache.WM_FORCE_DEBUG_SCREEN_ON_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON,
            1312084488,
            1023,
            "finishScreenTurningOn: mAwake=%b, mScreenOnEarly=%b, mScreenOnFully=%b,"
                + " mKeyguardDrawComplete=%b, mWindowManagerDrawComplete=%b",
            new Object[] {
              Boolean.valueOf(this.mAwake),
              Boolean.valueOf(this.mScreenOnEarly),
              Boolean.valueOf(this.mScreenOnFully),
              Boolean.valueOf(this.mKeyguardDrawComplete),
              Boolean.valueOf(this.mWindowManagerDrawComplete)
            });
      }
      if (!this.mScreenOnFully
          && this.mScreenOnEarly
          && this.mWindowManagerDrawComplete
          && (!this.mAwake || this.mKeyguardDrawComplete)) {
        if (ProtoLogCache.WM_FORCE_DEBUG_SCREEN_ON_enabled) {
          ProtoLogImpl.i(
              ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON,
              823210702,
              0,
              "Finished screen turning on...",
              (Object[]) null);
        }
        this.mScreenOnListener = null;
        this.mScreenOnFully = true;
        if (CoreRune.FW_TSP_STATE_CONTROLLER && this.mDisplayContent.isDefaultDisplay) {
          this.mExt.finishScreenTurningOn();
        }
        return true;
      }
      return false;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x0018, code lost:

     if (r0 != 2006) goto L39;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void adjustWindowParamsLw(
      WindowState windowState, WindowManager.LayoutParams layoutParams) {
    ActivityRecord activityRecord;
    int i = layoutParams.type;
    if (i != 1) {
      if (i != 2013) {
        if (i != 2015 && i != 2632) {
          if (i == 2005) {
            long j = layoutParams.hideTimeoutMilliseconds;
            if (j < 0 || j > 4100) {
              layoutParams.hideTimeoutMilliseconds = 4100L;
            }
            layoutParams.hideTimeoutMilliseconds =
                this.mAccessibilityManager.getRecommendedTimeoutMillis(
                    (int) layoutParams.hideTimeoutMilliseconds, 2);
            layoutParams.flags |= 16;
          }
        }
        layoutParams.flags = (layoutParams.flags | 24) & (-262145);
      } else {
        layoutParams.layoutInDisplayCutoutMode = 3;
      }
    } else {
      if (layoutParams.isFullscreen()
          && (activityRecord = windowState.mActivityRecord) != null
          && activityRecord.fillsParent()
          && (windowState.mAttrs.privateFlags & 32768) != 0
          && layoutParams.getFitInsetsTypes() != 0) {
        throw new IllegalArgumentException(
            "Illegal attributes: Main activity window that isn't translucent trying to fit insets: "
                + layoutParams.getFitInsetsTypes()
                + " attrs="
                + layoutParams);
      }
      if (windowState.isPopOver()) {
        windowState.mOriginalDimBehind = layoutParams.flags & 2;
        windowState.mOriginalDimAmount = layoutParams.dimAmount;
        windowState.mOriginalDimDuration = layoutParams.dimDuration;
        this.mDisplayContent.mPopOverController.updatePopOverDimAttributesLw(
            windowState, layoutParams);
        layoutParams.flags &= -33;
      }
    }
    if (WindowManager.LayoutParams.isSystemAlertWindowType(layoutParams.type)) {
      float f = this.mService.mMaximumObscuringOpacityForTouch;
      if (layoutParams.alpha > f
          && (layoutParams.flags & 16) != 0
          && (layoutParams.privateFlags & 536870912) == 0) {
        Slog.w(
            StartingSurfaceController.TAG,
            String.format(
                "App %s has a system alert window (type = %d) with FLAG_NOT_TOUCHABLE and"
                    + " LayoutParams.alpha = %.2f > %.2f, setting alpha to %.2f to let touches pass"
                    + " through (if this is isn't desirable, remove flag FLAG_NOT_TOUCHABLE).",
                layoutParams.packageName,
                Integer.valueOf(layoutParams.type),
                Float.valueOf(layoutParams.alpha),
                Float.valueOf(f),
                Float.valueOf(f)));
        layoutParams.alpha = f;
        windowState.mWinAnimator.mAlpha = f;
      }
    }
    if (!windowState.mSession.mCanSetUnrestrictedGestureExclusion) {
      layoutParams.privateFlags &= -33;
    }
    InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
    if (controllableInsetProvider != null
        && controllableInsetProvider.getSource().insetsRoundedCornerFrame()
            != layoutParams.insetsRoundedCornerFrame) {
      controllableInsetProvider
          .getSource()
          .setInsetsRoundedCornerFrame(layoutParams.insetsRoundedCornerFrame);
    }
    InsetsFlags insetsFlags = layoutParams.insetsFlags;
    insetsFlags.appearance =
        this.mExt.getForceLightNavigationBar(layoutParams) | insetsFlags.appearance;
  }

  public void setDropInputModePolicy(
      WindowState windowState, WindowManager.LayoutParams layoutParams) {
    if (layoutParams.type == 2005 && (layoutParams.privateFlags & 536870912) == 0) {
      ((SurfaceControl.Transaction) this.mService.mTransactionFactory.get())
          .setDropInputMode(windowState.getSurfaceControl(), 1)
          .apply();
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:40:0x00d2  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int validateAddingWindowLw(WindowManager.LayoutParams layoutParams, int i, int i2) {
    if ((layoutParams.privateFlags & 536870912) != 0) {
      this.mContext.enforcePermission(
          "android.permission.INTERNAL_SYSTEM_WINDOW", i, i2, "DisplayPolicy");
    }
    if ((layoutParams.privateFlags & Integer.MIN_VALUE) != 0) {
      ActivityTaskManagerService.enforceTaskPermission("DisplayPolicy");
    }
    int i3 = layoutParams.type;
    if (i3 != 2000) {
      if (i3 == 2014) {
        return -10;
      }
      if (i3 != 2017) {
        if (i3 != 2019) {
          if (i3 != 2024) {
            if (i3 != 2033) {
              if (i3 != 2040) {
                if (i3 != 2041) {
                  switch (i3) {
                    case 2621:
                      if (!isDexStandAloneMode()) {
                        Slog.w(
                            StartingSurfaceController.TAG,
                            "validateAddingWindowLw: failed, TYPE_STATUS_BAR_STANDALONE");
                        break;
                      }
                      break;
                    case 2622:
                      if (!isDexStandAloneMode()) {
                        Slog.w(
                            StartingSurfaceController.TAG,
                            "validateAddingWindowLw: failed, TYPE_NOTIFICATION_SHADE_STANDALONE");
                        break;
                      }
                      break;
                    case 2623:
                      if (!isDexStandAloneMode()) {
                        Slog.w(
                            StartingSurfaceController.TAG,
                            "validateAddingWindowLw: failed, TYPE_NAVIGATION_BAR_STANDALONE");
                        break;
                      }
                      break;
                  }
                  return -10;
                }
              }
              this.mContext.enforcePermission(
                  "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
              if (this.mNotificationShade != null && this.mNotificationShade.isAlive()) {
                return canReplaceSystemWindowForDexStandAlone(layoutParams) ? 0 : -7;
              }
            }
          } else if (!this.mService.mAtmService.isCallerRecents(i2)) {
            this.mContext.enforcePermission(
                "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
          }
          if (layoutParams.providedInsets != null
              && !this.mService.mAtmService.isCallerRecents(i2)) {
            this.mContext.enforcePermission(
                "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
          }
          return 0;
        }
        this.mContext.enforcePermission(
            "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
        WindowState windowState = this.mNavigationBar;
        if (windowState != null && windowState.isAlive()) {
          return canReplaceSystemWindowForDexStandAlone(layoutParams) ? 0 : -7;
        }
        if (layoutParams.providedInsets != null) {
          this.mContext.enforcePermission(
              "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
        }
        return 0;
      }
      this.mContext.enforcePermission(
          "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
      if (layoutParams.providedInsets != null) {}
      return 0;
    }
    this.mContext.enforcePermission(
        "android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
    WindowState windowState2 = this.mStatusBar;
    if (windowState2 != null && windowState2.isAlive()) {
      return canReplaceSystemWindowForDexStandAlone(layoutParams) ? 0 : -7;
    }
    if (layoutParams.providedInsets != null) {}
    return 0;
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x0051  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void addWindowLw(WindowState windowState, WindowManager.LayoutParams layoutParams) {
    InsetsFrameProvider[] insetsFrameProviderArr;
    SparseArray sparseArray;
    int i = layoutParams.type;
    if (i != 2000) {
      if (i != 2019) {
        if (i != 2040) {
          switch (i) {
          }
          insetsFrameProviderArr = layoutParams.providedInsets;
          if (insetsFrameProviderArr != null) {
            for (int length = insetsFrameProviderArr.length - 1; length >= 0; length--) {
              InsetsFrameProvider insetsFrameProvider = layoutParams.providedInsets[length];
              TriFunction frameProvider = getFrameProvider(windowState, length, -1);
              InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrides =
                  insetsFrameProvider.getInsetsSizeOverrides();
              if (insetsSizeOverrides != null) {
                sparseArray = new SparseArray();
                for (int length2 = insetsSizeOverrides.length - 1; length2 >= 0; length2--) {
                  sparseArray.put(
                      insetsSizeOverrides[length2].getWindowType(),
                      getFrameProvider(windowState, length, length2));
                }
              } else {
                sparseArray = null;
              }
              this.mDisplayContent
                  .getInsetsStateController()
                  .getOrCreateSourceProvider(
                      insetsFrameProvider.getId(), insetsFrameProvider.getType())
                  .setWindowContainer(windowState, frameProvider, sparseArray);
              this.mInsetsSourceWindowsExceptIme.add(windowState);
            }
            if (windowState.mAttrs.type == 2024
                && windowState.getControllableInsetProvider() != null
                && windowState.getControllableInsetProvider().getSource().getType()
                    == WindowInsets.Type.navigationBars()) {
              this.mExt.getTaskbarController().onTaskbarAddedLw(windowState);
            }
          }
          this.mExt.addWindowLw(windowState, layoutParams);
        }
        if (this.mDisplayContent.isDefaultDisplay && i == 2040) {
          this.mDefaultNotificationShade = windowState;
          if (hasDexStandAloneNotificationShade()) {
            return;
          }
        }
        this.mNotificationShade = windowState;
        insetsFrameProviderArr = layoutParams.providedInsets;
        if (insetsFrameProviderArr != null) {}
        this.mExt.addWindowLw(windowState, layoutParams);
      }
      if (this.mDisplayContent.isDefaultDisplay && i == 2019) {
        this.mDefaultNavigationBar = windowState;
        if (hasDexStandAloneNavigationBar()) {
          return;
        }
      }
      this.mNavigationBar = windowState;
      insetsFrameProviderArr = layoutParams.providedInsets;
      if (insetsFrameProviderArr != null) {}
      this.mExt.addWindowLw(windowState, layoutParams);
    }
    if (this.mDisplayContent.isDefaultDisplay && i == 2000) {
      this.mDefaultStatusBar = windowState;
      if (hasDexStandAloneStatusBar()) {
        return;
      }
    }
    this.mStatusBar = windowState;
    insetsFrameProviderArr = layoutParams.providedInsets;
    if (insetsFrameProviderArr != null) {}
    this.mExt.addWindowLw(windowState, layoutParams);
  }

  public static TriFunction getFrameProvider(
      final WindowState windowState, final int i, final int i2) {
    return new TriFunction() { // from class:
                               // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda4
      public final Object apply(Object obj, Object obj2, Object obj3) {
        Integer lambda$getFrameProvider$1;
        lambda$getFrameProvider$1 =
            DisplayPolicy.lambda$getFrameProvider$1(
                WindowState.this, i, i2, (DisplayFrames) obj, (WindowContainer) obj2, (Rect) obj3);
        return lambda$getFrameProvider$1;
      }
    };
  }

  public static /* synthetic */ Integer lambda$getFrameProvider$1(
      WindowState windowState,
      int i,
      int i2,
      DisplayFrames displayFrames,
      WindowContainer windowContainer,
      Rect rect) {
    Insets insetsSize;
    WindowManager.LayoutParams forRotation =
        windowState.mAttrs.forRotation(displayFrames.mRotation);
    InsetsFrameProvider insetsFrameProvider = forRotation.providedInsets[i];
    Rect rect2 = displayFrames.mUnrestricted;
    Rect rect3 = displayFrames.mDisplayCutoutSafe;
    int source = insetsFrameProvider.getSource();
    boolean z = false;
    if (source == 0) {
      rect.set(rect2);
    } else if (source == 1) {
      rect.set(windowContainer.getBounds());
    } else if (source != 2) {
      if (source == 3) {
        rect.set(insetsFrameProvider.getArbitraryRectangle());
      }
    } else if ((forRotation.privateFlags & IInstalld.FLAG_USE_QUOTA) != 0) {
      z = true;
    }
    if (i2 == -1) {
      insetsSize = insetsFrameProvider.getInsetsSize();
    } else {
      insetsSize = insetsFrameProvider.getInsetsSizeOverrides()[i2].getInsetsSize();
    }
    if (insetsFrameProvider.getMinimalInsetsSizeInDisplayCutoutSafe() != null) {
      sTmpRect2.set(rect);
    }
    calculateInsetsFrame(rect, insetsSize);
    if (z && insetsSize != null) {
      WindowLayout.extendFrameByCutout(rect3, rect2, rect, sTmpRect);
    }
    if (insetsFrameProvider.getMinimalInsetsSizeInDisplayCutoutSafe() != null) {
      Rect rect4 = sTmpRect2;
      calculateInsetsFrame(rect4, insetsFrameProvider.getMinimalInsetsSizeInDisplayCutoutSafe());
      WindowLayout.extendFrameByCutout(rect3, rect2, rect4, sTmpRect);
      if (rect4.contains(rect)) {
        rect.set(rect4);
      }
    }
    return Integer.valueOf(insetsFrameProvider.getFlags());
  }

  public static void calculateInsetsFrame(Rect rect, Insets insets) {
    if (insets == null) {
      return;
    }
    int i = insets.left;
    if (i != 0) {
      rect.right = rect.left + i;
      return;
    }
    int i2 = insets.top;
    if (i2 != 0) {
      rect.bottom = rect.top + i2;
      return;
    }
    int i3 = insets.right;
    if (i3 != 0) {
      rect.left = rect.right - i3;
      return;
    }
    int i4 = insets.bottom;
    if (i4 != 0) {
      rect.top = rect.bottom - i4;
    } else {
      rect.setEmpty();
    }
  }

  public TriFunction getImeSourceFrameProvider() {
    return new TriFunction() { // from class:
                               // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda14
      public final Object apply(Object obj, Object obj2, Object obj3) {
        Integer lambda$getImeSourceFrameProvider$2;
        lambda$getImeSourceFrameProvider$2 =
            DisplayPolicy.this.lambda$getImeSourceFrameProvider$2(
                (DisplayFrames) obj, (WindowContainer) obj2, (Rect) obj3);
        return lambda$getImeSourceFrameProvider$2;
      }
    };
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Integer lambda$getImeSourceFrameProvider$2(
      DisplayFrames displayFrames, WindowContainer windowContainer, Rect rect) {
    WindowState asWindowState = windowContainer.asWindowState();
    if (asWindowState == null) {
      throw new IllegalArgumentException("IME insets must be provided by a window.");
    }
    if (this.mNavigationBar != null
        && navigationBarPosition(displayFrames.mRotation) == 4
        && !this.mExt.getTaskbarController().hasTaskbar()
        && this.mExt.isImeBtnOnGestureAllowed(displayFrames.mRotation)) {
      Rect rect2 = sTmpRect;
      rect2.set(rect);
      rect2.intersectUnchecked(this.mNavigationBar.getFrame());
      rect.inset(asWindowState.mGivenContentInsets);
      rect.union(rect2);
    } else {
      rect.inset(asWindowState.mGivenContentInsets);
    }
    return 0;
  }

  public void removeWindowLw(WindowState windowState) {
    if (this.mStatusBar == windowState) {
      if (hasDexStandAloneStatusBar()) {
        restoreDefaultStatusBar();
      } else {
        this.mStatusBar = null;
      }
    } else if (this.mNavigationBar == windowState) {
      if (hasDexStandAloneNavigationBar()) {
        restoreDefaultNavigationBar();
      } else {
        this.mNavigationBar = null;
      }
    } else if (this.mNotificationShade == windowState) {
      if (hasDexStandAloneNotificationShade()) {
        restoreDefaultNotificationShade();
      } else {
        this.mNotificationShade = null;
      }
    }
    if (this.mLastFocusedWindow == windowState) {
      this.mLastFocusedWindow = null;
    }
    if (windowState.hasInsetsSourceProvider()) {
      SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
      InsetsStateController insetsStateController = this.mDisplayContent.getInsetsStateController();
      for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
        InsetsSourceProvider insetsSourceProvider =
            (InsetsSourceProvider) insetsSourceProviders.valueAt(size);
        insetsSourceProvider.setWindowContainer(null, null, null);
        insetsStateController.removeSourceProvider(insetsSourceProvider.getSource().getId());
      }
      if (this.mExt.getTaskbarController().isTaskbar(windowState)) {
        this.mExt.getTaskbarController().onTaskbarRemovedLw();
      }
    }
    this.mInsetsSourceWindowsExceptIme.remove(windowState);
    if (this.mDefaultStatusBar == windowState) {
      this.mDefaultStatusBar = null;
    }
    if (this.mDefaultNavigationBar == windowState) {
      this.mDefaultNavigationBar = null;
    }
    if (this.mDefaultNotificationShade == windowState) {
      this.mDefaultNotificationShade = null;
    }
    this.mExt.removeWindowLw(windowState);
  }

  public WindowState getStatusBar() {
    return this.mStatusBar;
  }

  public WindowState getNotificationShade() {
    return this.mNotificationShade;
  }

  public WindowState getNavigationBar() {
    return this.mNavigationBar;
  }

  public boolean isImmersiveMode() {
    return this.mIsImmersiveMode;
  }

  public int selectAnimation(WindowState windowState, int i) {
    if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_ANIM,
          341360111,
          4,
          (String) null,
          new Object[] {String.valueOf(windowState), Long.valueOf(i)});
    }
    CoverPolicy coverPolicy = this.mExt.mCoverPolicy;
    if (coverPolicy != null && coverPolicy.shouldApplyNoAnimation(windowState)) {
      return -1;
    }
    if (CoreRune.SYSFW_APP_SPEG && (this.mDisplayContent.getDisplayInfo().flags & 32768) != 0) {
      Slog.d("SPEG", "skip animation-leash of window_animation");
      return -1;
    }
    if (i != 5 || !windowState.hasAppShownWindows()) {
      return 0;
    }
    if (windowState.isActivityTypeHome()
        || windowState.mAttrs.alpha == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return -1;
    }
    if (!ProtoLogCache.WM_DEBUG_ANIM_enabled) {
      return R.anim.app_starting_exit;
    }
    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ANIM, -1303628829, 0, (String) null, (Object[]) null);
    return R.anim.app_starting_exit;
  }

  public void simulateLayoutDisplay(DisplayFrames displayFrames) {
    sTmpClientFrames.attachedFrame = null;
    for (int size = this.mInsetsSourceWindowsExceptIme.size() - 1; size >= 0; size--) {
      WindowState windowState = (WindowState) this.mInsetsSourceWindowsExceptIme.valueAt(size);
      if (!this.mExt.getTaskbarController().isHiddenBar(windowState)
          && (!isDexStandAloneMode()
              || !hasDexStandAloneNavigationBar()
              || windowState != this.mDefaultNavigationBar)) {
        this.mWindowLayout.computeFrames(
            windowState.mAttrs.forRotation(displayFrames.mRotation),
            displayFrames.mInsetsState,
            displayFrames.mDisplayCutoutSafe,
            displayFrames.mUnrestricted,
            windowState.getWindowingMode(),
            -1,
            -1,
            windowState.getRequestedVisibleTypes(),
            windowState.mGlobalScale,
            sTmpClientFrames,
            windowState.getStageType());
        SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
        InsetsState insetsState = displayFrames.mInsetsState;
        for (int size2 = insetsSourceProviders.size() - 1; size2 >= 0; size2--) {
          insetsState.addSource(
              ((InsetsSourceProvider) insetsSourceProviders.valueAt(size2))
                  .createSimulatedSource(displayFrames, sTmpClientFrames.frame));
        }
      }
    }
  }

  public void onDisplayInfoChanged(DisplayInfo displayInfo) {
    if (ViewRootImpl.CLIENT_TRANSIENT) {
      return;
    }
    int i = displayInfo.displayId;
    if (i == 2) {
      this.mUiContext = this.mService.mAtmService.mSystemThread.getSystemUiContext(i);
      updateCurrentUserResources();
    }
    this.mSystemGestures.onDisplayInfoChanged(displayInfo);
    if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && displayInfo.displayId == 0) {
      this.mRefreshRatePolicy.onDisplayInfoChanged(displayInfo);
    }
  }

  public void layoutWindowLw(
      WindowState windowState, WindowState windowState2, DisplayFrames displayFrames) {
    int i;
    if (windowState.skipLayout()) {
      return;
    }
    DisplayFrames displayFrames2 = windowState.getDisplayFrames(displayFrames);
    WindowManager.LayoutParams forRotation =
        windowState.mAttrs.forRotation(displayFrames2.mRotation);
    ClientWindowFrames clientWindowFrames = sTmpClientFrames;
    clientWindowFrames.attachedFrame = windowState2 != null ? windowState2.getFrame() : null;
    boolean z = forRotation == windowState.mAttrs;
    int i2 = z ? windowState.mRequestedWidth : -1;
    if (this.mDisplayContent.isDexMode() && windowState == this.mNavigationBar) {
      int i3 = this.mDexTaskbarHeight;
      forRotation.height = i3;
      i = i3;
    } else {
      i = z ? windowState.mRequestedHeight : -1;
    }
    this.mWindowBounds.set(windowState.getBounds());
    if (windowState.isPopOver()) {
      updatePopOverLayoutWindow(windowState);
    }
    if (CoreRune.FW_BOUNDS_COMPAT_FOR_IME_EXPERIENCE) {
      BoundsCompatUtils.get().adjustBoundsWithImeIfNeeded(windowState, this.mWindowBounds);
    }
    this.mWindowLayout.computeFrames(
        forRotation,
        windowState.getInsetsState(),
        displayFrames2.mDisplayCutoutSafe,
        this.mWindowBounds,
        windowState.getWindowingMode(),
        i2,
        i,
        windowState.getRequestedVisibleTypes(),
        windowState.mGlobalScale,
        clientWindowFrames,
        windowState.getStageType());
    windowState.setFrames(
        clientWindowFrames, windowState.mRequestedWidth, windowState.mRequestedHeight);
  }

  public WindowState getTopFullscreenOpaqueWindow() {
    return this.mTopFullscreenOpaqueWindowState;
  }

  public boolean isTopLayoutFullscreen() {
    return this.mTopIsFullscreen;
  }

  public void beginPostLayoutPolicyLw() {
    this.mLeftGestureHost = null;
    this.mTopGestureHost = null;
    this.mRightGestureHost = null;
    this.mBottomGestureHost = null;
    this.mTopFullscreenOpaqueWindowState = null;
    this.mNavBarColorWindowCandidate = null;
    this.mNavBarBackgroundWindow = null;
    this.mStatusBarAppearanceRegionList.clear();
    this.mLetterboxDetails.clear();
    this.mStatusBarBackgroundWindows.clear();
    this.mStatusBarColorCheckedBounds.setEmpty();
    this.mStatusBarBackgroundCheckedBounds.setEmpty();
    this.mSystemBarColorApps.clear();
    this.mAllowLockscreenWhenOn = false;
    this.mShowingDream = false;
    this.mIsFreeformWindowOverlappingWithNavBar = false;
    this.mExt.beginPostLayoutPolicyLw();
    this.mIsPipWindowOverlappingWithNavBar = false;
    this.mIsResizingByDivider = false;
  }

  public final boolean shouldSetGesutreHost(WindowState windowState, int i) {
    return (windowState == this.mNavigationBar && this.mNavigationBarPosition == i)
        || (this.mExt.getTaskbarController().isTaskbar(windowState)
            && this.mExt.getTaskbarController().isTaskbarVisible());
  }

  public void applyPostLayoutPolicyLw(
      WindowState windowState,
      WindowManager.LayoutParams layoutParams,
      WindowState windowState2,
      WindowState windowState3) {
    LetterboxDetails letterboxDetails;
    if (layoutParams.type == 2019) {
      this.mNavigationBarPosition =
          navigationBarPosition(this.mDisplayContent.mDisplayFrames.mRotation);
    }
    boolean z = false;
    boolean z2 = windowState.canAffectSystemUiFlags() && !windowState.inFreeformWindowingMode();
    this.mExt.applyPostLayoutPolicyLw(windowState, layoutParams);
    if (!this.mExt.applyForceHidePolicyLw(windowState)) {
      applyKeyguardPolicy(windowState, windowState3);
    }
    if (!this.mIsFreeformWindowOverlappingWithNavBar
        && windowState.inFreeformWindowingMode()
        && windowState.mActivityRecord != null
        && ((windowState.getTask() == null || !windowState.getTask().isFreeformForceHidden())
            && isOverlappingWithNavBar(windowState))) {
      this.mIsFreeformWindowOverlappingWithNavBar = true;
    }
    if (!this.mIsPipWindowOverlappingWithNavBar
        && windowState.mActivityRecord != null
        && windowState.inPinnedWindowingMode()
        && windowState.getTask() != null
        && !windowState.getTask().matchParentBounds()
        && isOverlappingWithNavBar(windowState)) {
      this.mIsPipWindowOverlappingWithNavBar = true;
    }
    if (windowState.hasInsetsSourceProvider()) {
      SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
      Rect bounds = windowState.getBounds();
      for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
        InsetsSource source =
            ((InsetsSourceProvider) insetsSourceProviders.valueAt(size)).getSource();
        if ((source.getType()
                    & (WindowInsets.Type.systemGestures()
                        | WindowInsets.Type.mandatorySystemGestures()))
                != 0
            && (this.mLeftGestureHost == null
                || this.mTopGestureHost == null
                || this.mRightGestureHost == null
                || this.mBottomGestureHost == null)) {
          Insets calculateInsets = source.calculateInsets(bounds, false);
          if (this.mLeftGestureHost == null
              && (calculateInsets.left > 0 || shouldSetGesutreHost(windowState, 1))) {
            this.mLeftGestureHost = windowState;
          }
          if (this.mTopGestureHost == null && calculateInsets.top > 0) {
            this.mTopGestureHost = windowState;
          }
          if (this.mRightGestureHost == null
              && (calculateInsets.right > 0 || shouldSetGesutreHost(windowState, 2))) {
            this.mRightGestureHost = windowState;
          }
          if (this.mBottomGestureHost == null && calculateInsets.bottom > 0) {
            this.mBottomGestureHost = windowState;
          }
        }
      }
    }
    if ((windowState.mAttrs.multiwindowFlags & 64) != 0) {
      this.mIsResizingByDivider = true;
    }
    if (z2) {
      int i = layoutParams.type;
      boolean z3 = i >= 1 && i < 2000;
      if (this.mTopFullscreenOpaqueWindowState == null) {
        int i2 = layoutParams.flags;
        if (windowState.isDreamWindow()
            && (!this.mDreamingLockscreen || (windowState.isVisible() && windowState.hasDrawn()))) {
          this.mShowingDream = true;
          z3 = true;
        }
        if (z3 && windowState2 == null && layoutParams.isFullscreen() && (i2 & 1) != 0) {
          this.mAllowLockscreenWhenOn = true;
        }
      }
      if ((z3 && windowState2 == null && layoutParams.isFullscreen())
          || layoutParams.type == 2031) {
        if (this.mTopFullscreenOpaqueWindowState == null
            && !windowState.isPopOver()
            && !windowState.isInBubbleMode()) {
          this.mTopFullscreenOpaqueWindowState = windowState;
        }
        if (this.mStatusBar != null) {
          Rect rect = sTmpRect;
          if (rect.setIntersect(windowState.getFrame(), this.mStatusBar.getFrame())
              && !this.mStatusBarBackgroundCheckedBounds.contains(rect)) {
            if (!this.mDisplayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()
                || windowState.inSplitScreenWindowingMode()) {
              this.mStatusBarBackgroundWindows.add(windowState);
              this.mStatusBarBackgroundCheckedBounds.union(rect);
              z = true;
            }
            if (!this.mStatusBarColorCheckedBounds.contains(rect) && z) {
              int i3 = windowState.mAttrs.insetsFlags.appearance & 8;
              if (this.mIsResizingByDivider
                  && windowState.getFrame().width() == this.mStatusBar.getFrame().width()) {
                i3 &= -2;
              }
              this.mStatusBarAppearanceRegionList.add(
                  new AppearanceRegion(i3, new Rect(windowState.getFrame())));
              this.mStatusBarColorCheckedBounds.union(rect);
              addSystemBarColorApp(windowState);
            }
          }
        }
        if (isOverlappingWithNavBar(windowState)) {
          if (this.mNavBarColorWindowCandidate == null) {
            this.mNavBarColorWindowCandidate = windowState;
            addSystemBarColorApp(windowState);
          }
          if (this.mNavBarBackgroundWindow == null) {
            this.mNavBarBackgroundWindow = windowState;
          }
        }
        ActivityRecord activityRecord = windowState.getActivityRecord();
        if (activityRecord == null
            || (letterboxDetails = activityRecord.mLetterboxUiController.getLetterboxDetails())
                == null) {
          return;
        }
        this.mLetterboxDetails.add(letterboxDetails);
        return;
      }
      if (windowState.isDimming()
          && !this.mExt.isBlurringWinNotAffectingLightBarAppearance(windowState)) {
        WindowState windowState4 = this.mStatusBar;
        if (windowState4 != null
            && addStatusBarAppearanceRegionsForDimmingWindow(
                windowState.mAttrs.insetsFlags.appearance & 8,
                windowState4.getFrame(),
                windowState.getBounds(),
                windowState.getFrame())) {
          addSystemBarColorApp(windowState);
        }
        if (isOverlappingWithNavBar(windowState) && this.mNavBarColorWindowCandidate == null) {
          this.mNavBarColorWindowCandidate = windowState;
          return;
        }
        return;
      }
      if (z3
          && windowState2 == null
          && this.mNavBarColorWindowCandidate == null
          && windowState
              .getFrame()
              .contains(
                  getBarContentFrameForWindow(windowState, WindowInsets.Type.navigationBars()))) {
        this.mNavBarColorWindowCandidate = windowState;
        addSystemBarColorApp(windowState);
      }
    }
  }

  public final boolean addStatusBarAppearanceRegionsForDimmingWindow(
      int i, Rect rect, Rect rect2, Rect rect3) {
    Rect rect4 = sTmpRect;
    if (!rect4.setIntersect(rect2, rect) || this.mStatusBarColorCheckedBounds.contains(rect4)) {
      return false;
    }
    if (i != 0) {
      Rect rect5 = sTmpRect2;
      if (rect5.setIntersect(rect3, rect)) {
        this.mStatusBarAppearanceRegionList.add(new AppearanceRegion(i, new Rect(rect3)));
        if (!rect4.equals(rect5) && rect4.height() == rect5.height()) {
          if (rect4.left != rect5.left) {
            this.mStatusBarAppearanceRegionList.add(
                new AppearanceRegion(0, new Rect(rect2.left, rect2.top, rect5.left, rect2.bottom)));
          }
          if (rect4.right != rect5.right) {
            this.mStatusBarAppearanceRegionList.add(
                new AppearanceRegion(
                    0, new Rect(rect5.right, rect2.top, rect2.right, rect2.bottom)));
          }
        }
        this.mStatusBarColorCheckedBounds.union(rect4);
        return true;
      }
    }
    this.mStatusBarAppearanceRegionList.add(new AppearanceRegion(0, new Rect(rect2)));
    this.mStatusBarColorCheckedBounds.union(rect4);
    return true;
  }

  public final void addSystemBarColorApp(WindowState windowState) {
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord != null) {
      this.mSystemBarColorApps.add(activityRecord);
    }
  }

  public void finishPostLayoutPolicyLw() {
    if (!this.mShowingDream) {
      this.mDreamingLockscreen = this.mService.mPolicy.isKeyguardShowingAndNotOccluded();
    }
    updateSystemBarAttributes();
    boolean z = this.mShowingDream;
    if (z != this.mLastShowingDream) {
      this.mLastShowingDream = z;
      this.mDisplayContent.notifyKeyguardFlagsChanged();
    }
    this.mExt.finishPostLayoutPolicyLw();
    this.mService.mPolicy.setAllowLockscreenWhenOn(getDisplayId(), this.mAllowLockscreenWhenOn);
  }

  public final void applyKeyguardPolicy(WindowState windowState, WindowState windowState2) {
    if (windowState.canBeHiddenByKeyguard()) {
      boolean shouldBeHiddenByKeyguard = shouldBeHiddenByKeyguard(windowState, windowState2);
      if (windowState.mIsImWindow) {
        this.mDisplayContent
            .getInsetsStateController()
            .getImeSourceProvider()
            .setFrozen(shouldBeHiddenByKeyguard);
      }
      if (shouldBeHiddenByKeyguard) {
        windowState.hide(false, true);
        return;
      } else {
        windowState.show(false, true);
        return;
      }
    }
    if (this.mExt.canBeForceHiddenByAodLw(windowState)) {
      if (this.mService.mAtmService.mKeyguardController.isAodShowing(getDisplayId())) {
        windowState.hide(false, true);
      } else {
        windowState.show(false, true);
      }
    }
  }

  public final boolean shouldBeHiddenByKeyguard(WindowState windowState, WindowState windowState2) {
    DisplayContent displayContent = this.mDisplayContent;
    boolean z = false;
    if ((displayContent.isDefaultDisplay || !displayContent.isDexMode())
        && windowState.mIsImWindow
        && (this.mDisplayContent.isAodShowing()
            || (this.mDisplayContent.isDefaultDisplay && !this.mWindowManagerDrawComplete))) {
      return true;
    }
    if (!this.mDisplayContent.isDefaultDisplay || !isKeyguardShowing()) {
      return CoreRune.FW_INFINITY_WALLPAPER_IN_AOD
          && this.mExt.canBeForceHiddenByAodLw(windowState)
          && this.mDisplayContent.isAodShowing();
    }
    if (windowState2 != null
        && windowState2.isVisible()
        && windowState.mIsImWindow
        && (windowState2.canShowWhenLocked() || !windowState2.canBeHiddenByKeyguard())) {
      return false;
    }
    if (isKeyguardOccluded()
        && (windowState.canShowWhenLocked() || (windowState.mAttrs.privateFlags & 256) != 0)) {
      z = true;
    }
    return !z;
  }

  public boolean topAppHidesSystemBar(int i) {
    if (this.mTopFullscreenOpaqueWindowState == null
        || getInsetsPolicy().areTypesForciblyShowing(i)) {
      return false;
    }
    return !this.mTopFullscreenOpaqueWindowState.isRequestedVisible(i);
  }

  public void switchUser() {
    updateCurrentUserResources();
    updateForceShowNavBarSettings();
  }

  public void onOverlayChanged() {
    updateCurrentUserResources();
    this.mDisplayContent.updateDisplayInfo();
    onConfigurationChanged();
    if (ViewRootImpl.CLIENT_TRANSIENT) {
      return;
    }
    this.mSystemGestures.onConfigurationChanged();
  }

  public void onConfigurationChanged() {
    Resources currentUserResources = getCurrentUserResources();
    if (this.mDisplayContent.isDefaultDisplay) {
      this.mHasNavigationBar =
          currentUserResources.getBoolean(R.bool.config_predictShowStartingSurface);
    }
    if (this.mDisplayContent.isDexMode()) {
      this.mDexTaskbarHeight = currentUserResources.getDimensionPixelSize(17106192);
    }
    this.mSystemGestures.onConfigurationChanged();
    if (CoreRune.SAFE_DEBUG) {
      Slog.i(
          StartingSurfaceController.TAG,
          "DisplayPolicy#onConfigurationChanged, displayId="
              + this.mDisplayContent.getDisplayId()
              + ", ResourcesConfig="
              + this.mUiContext.getResources().getConfiguration()
              + ", ResourcesMetrics="
              + this.mUiContext.getResources().getDisplayMetrics());
    }
    this.mNavBarOpacityMode =
        currentUserResources.getInteger(R.integer.config_notificationsBatteryLedOff);
    this.mLeftGestureInset =
        this.mGestureNavigationSettingsObserver.getLeftSensitivity(currentUserResources);
    this.mRightGestureInset =
        this.mGestureNavigationSettingsObserver.getRightSensitivity(currentUserResources);
    this.mNavigationBarAlwaysShowOnSideGesture =
        currentUserResources.getBoolean(
            R.bool.config_letterboxIsDisplayRotationImmersiveAppCompatPolicyEnabled);
    this.mRemoteInsetsControllerControlsSystemBars =
        currentUserResources.getBoolean(R.bool.config_remoteInsetsControllerControlsSystemBars);
    updateConfigurationAndScreenSizeDependentBehaviors();
    boolean z = currentUserResources.getBoolean(R.bool.config_assistLongPressHomeEnabledDefault);
    if (this.mShouldAttachNavBarToAppDuringTransition != z) {
      this.mShouldAttachNavBarToAppDuringTransition = z;
    }
    this.mExt.onConfigurationChanged();
  }

  public void updateConfigurationAndScreenSizeDependentBehaviors() {
    Resources currentUserResources = getCurrentUserResources();
    DisplayContent displayContent = this.mDisplayContent;
    this.mNavigationBarCanMove =
        displayContent.mBaseDisplayWidth != displayContent.mBaseDisplayHeight
            && currentUserResources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
    if (CoreRune.FW_NAVBAR_MOVABLE_POLICY) {
      this.mNavigationBarCanMove = false;
    }
    this.mDisplayContent
        .getDisplayRotation()
        .updateUserDependentConfiguration(currentUserResources);
    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
      DisplayPolicyExt displayPolicyExt = this.mExt;
      DisplayContent displayContent2 = this.mDisplayContent;
      displayPolicyExt.updateConfigurationAndScreenSizeDependentBehaviors(
          displayContent2.mBaseDisplayWidth,
          displayContent2.mBaseDisplayHeight,
          displayContent2.mInitialDisplayWidth,
          displayContent2.mInitialDisplayHeight);
    }
  }

  public final void updateCurrentUserResources() {
    int currentUserId = this.mService.mAmInternal.getCurrentUserId();
    Context systemUiContext = getSystemUiContext();
    if (currentUserId == 0) {
      this.mCurrentUserResources = systemUiContext.getResources();
    } else {
      LoadedApk packageInfo =
          ActivityThread.currentActivityThread()
              .getPackageInfo(
                  systemUiContext.getPackageName(), (CompatibilityInfo) null, 0, currentUserId);
      this.mCurrentUserResources =
          ResourcesManager.getInstance()
              .getResources(
                  systemUiContext.getWindowContextToken(),
                  packageInfo.getResDir(),
                  (String[]) null,
                  packageInfo.getOverlayDirs(),
                  packageInfo.getOverlayPaths(),
                  packageInfo.getApplicationInfo().sharedLibraryFiles,
                  Integer.valueOf(this.mDisplayContent.getDisplayId()),
                  (Configuration) null,
                  systemUiContext.getResources().getCompatibilityInfo(),
                  (ClassLoader) null,
                  (List) null);
    }
  }

  public Resources getCurrentUserResources() {
    if (this.mCurrentUserResources == null) {
      updateCurrentUserResources();
    }
    return this.mCurrentUserResources;
  }

  public Context getContext() {
    return this.mContext;
  }

  public Context getSystemUiContext() {
    return this.mUiContext;
  }

  public void setCanSystemBarsBeShownByUser(boolean z) {
    this.mCanSystemBarsBeShownByUser = z;
  }

  public void notifyDisplayReady() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.this.lambda$notifyDisplayReady$3();
          }
        });
    startEnableTouchEvent(false);
    Slog.i(
        StartingSurfaceController.TAG,
        "notifyDisplayReady() >> CMFA startEnableTouchEvent called for Display Id : "
            + getDisplayId());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyDisplayReady$3() {
    int displayId = getDisplayId();
    StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
    if (statusBarManagerInternal != null) {
      statusBarManagerInternal.onDisplayReady(displayId);
    }
    WallpaperManagerInternal wallpaperManagerInternal =
        (WallpaperManagerInternal) LocalServices.getService(WallpaperManagerInternal.class);
    if (wallpaperManagerInternal != null) {
      wallpaperManagerInternal.onDisplayReady(displayId);
    }
  }

  public float getWindowCornerRadius() {
    return this.mDisplayContent.getDisplay().getType() == 1
        ? ScreenDecorationsUtils.getWindowCornerRadius(this.mContext)
        : DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  public boolean isShowingDreamLw() {
    return this.mShowingDream;
  }

  public class DecorInsets {
    public final DisplayContent mDisplayContent;
    public final Info[] mInfoForRotation;
    public final Info mTmpInfo = new Info();
    public static final int DECOR_TYPES =
        WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars();
    public static final int CONFIG_TYPES =
        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();

    public class Info {
      public int mLastInsetsSourceCount;
      public final Rect mNonDecorInsets = new Rect();
      public final Rect mConfigInsets = new Rect();
      public final Rect mNonDecorFrame = new Rect();
      public final Rect mConfigFrame = new Rect();
      public boolean mNeedUpdate = true;
      public final Rect mCutoutInsets = new Rect();
      public final Rect mExceptNavConfigInsets = new Rect();

      public void update(DisplayContent displayContent, int i, int i2, int i3) {
        int i4;
        DisplayFrames displayFrames = new DisplayFrames();
        displayContent.updateDisplayFrames(displayFrames, i, i2, i3);
        displayContent.getDisplayPolicy().simulateLayoutDisplay(displayFrames);
        InsetsState insetsState = displayFrames.mInsetsState;
        Rect displayFrame = insetsState.getDisplayFrame();
        if (displayContent.isDexMode()) {
          i4 = CoreRune.MD_DEX_NOT_SUPPORT_CUTOUT ? WindowInsets.Type.displayCutout() | 0 : 0;
          if (displayContent.mAtmService.mDexController.isDexForceImmersiveModeEnabled()) {
            i4 |= WindowInsets.Type.navigationBars();
          }
        } else {
          i4 = 0;
        }
        Insets calculateInsets =
            insetsState.calculateInsets(displayFrame, (~i4) & DecorInsets.DECOR_TYPES, true);
        Insets calculateInsets2 =
            insetsState.calculateInsets(displayFrame, WindowInsets.Type.statusBars(), true);
        this.mNonDecorInsets.set(
            calculateInsets.left,
            calculateInsets.top,
            calculateInsets.right,
            calculateInsets.bottom);
        this.mConfigInsets.set(
            Math.max(calculateInsets2.left, calculateInsets.left),
            Math.max(calculateInsets2.top, calculateInsets.top),
            Math.max(calculateInsets2.right, calculateInsets.right),
            Math.max(calculateInsets2.bottom, calculateInsets.bottom));
        this.mNonDecorFrame.set(displayFrame);
        this.mNonDecorFrame.inset(this.mNonDecorInsets);
        this.mConfigFrame.set(displayFrame);
        this.mConfigFrame.inset(this.mConfigInsets);
        this.mLastInsetsSourceCount =
            displayContent.getDisplayPolicy().mInsetsSourceWindowsExceptIme.size();
        this.mNeedUpdate = false;
        Insets calculateInsets3 =
            insetsState.calculateInsets(displayFrame, WindowInsets.Type.displayCutout(), true);
        this.mCutoutInsets.set(
            calculateInsets3.left,
            calculateInsets3.top,
            calculateInsets3.right,
            calculateInsets3.bottom);
        this.mExceptNavConfigInsets.set(
            Math.max(calculateInsets2.left, calculateInsets3.left),
            Math.max(calculateInsets2.top, calculateInsets3.top),
            Math.max(calculateInsets2.right, calculateInsets3.right),
            Math.max(calculateInsets2.bottom, calculateInsets3.bottom));
      }

      public void set(Info info) {
        this.mNonDecorInsets.set(info.mNonDecorInsets);
        this.mConfigInsets.set(info.mConfigInsets);
        this.mNonDecorFrame.set(info.mNonDecorFrame);
        this.mConfigFrame.set(info.mConfigFrame);
        this.mLastInsetsSourceCount = info.mLastInsetsSourceCount;
        this.mNeedUpdate = false;
      }

      public String toString() {
        StringBuilder sb = new StringBuilder(32);
        return "{nonDecorInsets="
            + this.mNonDecorInsets.toShortString(sb)
            + ", configInsets="
            + this.mConfigInsets.toShortString(sb)
            + ", nonDecorFrame="
            + this.mNonDecorFrame.toShortString(sb)
            + ", configFrame="
            + this.mConfigFrame.toShortString(sb)
            + '}';
      }
    }

    public DecorInsets(DisplayContent displayContent) {
      Info[] infoArr = new Info[4];
      this.mInfoForRotation = infoArr;
      this.mDisplayContent = displayContent;
      for (int length = infoArr.length - 1; length >= 0; length--) {
        this.mInfoForRotation[length] = new Info();
      }
    }

    public Info get(int i, int i2, int i3) {
      Info info = this.mInfoForRotation[i];
      if (info.mNeedUpdate) {
        info.update(this.mDisplayContent, i, i2, i3);
      }
      return info;
    }

    public void invalidate() {
      for (Info info : this.mInfoForRotation) {
        info.mNeedUpdate = true;
      }
    }

    public void setTo(DecorInsets decorInsets) {
      for (int length = this.mInfoForRotation.length - 1; length >= 0; length--) {
        this.mInfoForRotation[length].set(decorInsets.mInfoForRotation[length]);
      }
    }

    public void dump(String str, PrintWriter printWriter) {
      int i = 0;
      while (true) {
        Info[] infoArr = this.mInfoForRotation;
        if (i >= infoArr.length) {
          return;
        }
        printWriter.println(str + Surface.rotationToString(i) + "=" + infoArr[i]);
        i++;
      }
    }

    public class Cache {
      public boolean mActive;
      public final DecorInsets mDecorInsets;
      public int mPreserveId;

      public Cache(DisplayContent displayContent) {
        this.mDecorInsets = new DecorInsets(displayContent);
      }

      public boolean canPreserve() {
        return this.mPreserveId == -1
            || this.mDecorInsets.mDisplayContent.mTransitionController.inTransition(
                this.mPreserveId);
      }
    }
  }

  public boolean updateDecorInsetsInfo() {
    if (shouldKeepCurrentDecorInsets()) {
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    DisplayFrames displayFrames = displayContent.mDisplayFrames;
    int i = displayFrames.mRotation;
    int i2 = displayFrames.mWidth;
    int i3 = displayFrames.mHeight;
    DecorInsets.Info info = this.mDecorInsets.mTmpInfo;
    info.update(displayContent, i, i2, i3);
    DecorInsets.Info decorInsetsInfo = getDecorInsetsInfo(i, i2, i3);
    if (info.mConfigFrame.equals(decorInsetsInfo.mConfigFrame)) {
      if (info.mLastInsetsSourceCount != decorInsetsInfo.mLastInsetsSourceCount) {
        for (int length = this.mDecorInsets.mInfoForRotation.length - 1; length >= 0; length--) {
          if (length != i) {
            boolean z = (length + i) % 2 == 1;
            this.mDecorInsets.mInfoForRotation[length].update(
                this.mDisplayContent, length, z ? i3 : i2, z ? i2 : i3);
          }
        }
        this.mDecorInsets.mInfoForRotation[i].set(info);
      }
      return false;
    }
    DecorInsets.Cache cache = this.mCachedDecorInsets;
    if (cache != null && !cache.canPreserve() && !this.mDisplayContent.isSleeping()) {
      this.mCachedDecorInsets = null;
    }
    this.mDecorInsets.invalidate();
    this.mDecorInsets.mInfoForRotation[i].set(info);
    return true;
  }

  public DecorInsets.Info getDecorInsetsInfo(int i, int i2, int i3) {
    return this.mDecorInsets.get(i, i2, i3);
  }

  public boolean shouldKeepCurrentDecorInsets() {
    DecorInsets.Cache cache = this.mCachedDecorInsets;
    return cache != null && cache.mActive && cache.canPreserve();
  }

  public void physicalDisplayChanged() {
    if (USE_CACHED_INSETS_FOR_DISPLAY_SWITCH) {
      updateCachedDecorInsets();
    }
  }

  public void updateCachedDecorInsets() {
    DecorInsets decorInsets;
    if (this.mCachedDecorInsets == null) {
      this.mCachedDecorInsets = new DecorInsets.Cache(this.mDisplayContent);
      decorInsets = null;
    } else {
      decorInsets = new DecorInsets(this.mDisplayContent);
      decorInsets.setTo(this.mCachedDecorInsets.mDecorInsets);
    }
    DecorInsets.Cache cache = this.mCachedDecorInsets;
    cache.mPreserveId = -1;
    cache.mDecorInsets.setTo(this.mDecorInsets);
    if (decorInsets != null) {
      this.mDecorInsets.setTo(decorInsets);
      this.mCachedDecorInsets.mActive = true;
    }
  }

  public void physicalDisplayUpdated() {
    if (this.mCachedDecorInsets == null) {
      return;
    }
    if (!this.mDisplayContent.mTransitionController.isCollecting()) {
      this.mCachedDecorInsets = null;
      return;
    }
    this.mCachedDecorInsets.mPreserveId =
        this.mDisplayContent.mTransitionController.getCollectingTransitionId();
    this.mDisplayContent.mTransitionController.mStateValidators.add(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.this.lambda$physicalDisplayUpdated$4();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$physicalDisplayUpdated$4() {
    if (this.mDisplayContent.isSleeping() || !updateDecorInsetsInfo()) {
      return;
    }
    Slog.d(StartingSurfaceController.TAG, "Insets changed after display switch transition");
    this.mDisplayContent.sendNewConfiguration();
  }

  public int navigationBarPosition(int i) {
    if (this.mDisplayContent.isDexMode()) {
      return 4;
    }
    WindowState windowState = this.mNavigationBar;
    if (windowState == null) {
      return -1;
    }
    int i2 = windowState.mAttrs.forRotation(i).gravity;
    if (i2 != 3) {
      return i2 != 5 ? 4 : 2;
    }
    return 1;
  }

  public int getNavBarPosition() {
    return this.mNavigationBarPosition;
  }

  public void focusChangedLw(WindowState windowState, WindowState windowState2) {
    this.mFocusedWindow = windowState2;
    this.mLastFocusedWindow = windowState;
    if (this.mDisplayContent.isDefaultDisplay) {
      this.mService.mPolicy.onDefaultDisplayFocusChangedLw(windowState2);
    }
    updateSystemBarAttributes();
  }

  public void requestTransientBars(WindowState windowState, boolean z) {
    if (ViewRootImpl.CLIENT_TRANSIENT) {
      return;
    }
    if (isNavOrAltBar(windowState)) {
      this.mImmersiveModeConfirmation.showOkButton();
    }
    if (CoreRune.IS_FACTORY_BINARY
        || FactoryTest.isAutomaticTestMode(this.mContext)
        || FactoryTest.isRunningFactoryApp()) {
      Slog.d(StartingSurfaceController.TAG, "Not showing transient bar, because factory test mode");
      return;
    }
    if (windowState == this.mStatusBar && this.mService.mExt.mPolicyExt.isLockTaskModePinned()) {
      Slog.d(
          StartingSurfaceController.TAG,
          "Not showing transient bar, because lock task mode pinned");
      return;
    }
    boolean z2 =
        isNavBarEmpty(this.mLastDisableFlags)
            || (windowState == this.mStatusBar && this.mDisplayContent.isDexMode());
    if ((z2 && isNavOrAltBar(windowState))
        || windowState == null
        || !this.mService.mPolicy.isUserSetupComplete()) {
      return;
    }
    if (!this.mCanSystemBarsBeShownByUser) {
      Slog.d(
          StartingSurfaceController.TAG,
          "Remote insets controller disallows showing system bars - ignoring request");
      return;
    }
    InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
    InsetsControlTarget controlTarget =
        controllableInsetProvider != null ? controllableInsetProvider.getControlTarget() : null;
    if (controlTarget == null || controlTarget == getNotificationShade()) {
      return;
    }
    WindowState window = controlTarget.getWindow();
    if (window == null || !window.isActivityTypeDream()) {
      int statusBars =
          (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars())
              & controlTarget.getRequestedVisibleTypes();
      Slog.d(
          StartingSurfaceController.TAG,
          "requestTransientBars: swipeTarget="
              + windowState
              + ", controlTarget="
              + controlTarget
              + ", canShowTransient="
              + controlTarget.canShowTransient()
              + ", restorePositionTypes=0x"
              + Integer.toHexString(statusBars)
              + ", from="
              + Debug.getCallers(2));
      InsetsSourceProvider controllableInsetProvider2 = windowState.getControllableInsetProvider();
      if (controllableInsetProvider2 != null
          && controllableInsetProvider2.getSource().getType() == WindowInsets.Type.navigationBars()
          && (WindowInsets.Type.navigationBars() & statusBars) != 0) {
        controlTarget.showInsets(WindowInsets.Type.navigationBars(), false, null);
        return;
      }
      if (controlTarget.canShowTransient()) {
        getInsetsPolicy().mLastTransientRequestedByPolicyControl =
            controlTarget instanceof InsetsPolicy.PolicyControlTarget;
        if (z2) {
          this.mDisplayContent
              .getInsetsPolicy()
              .showTransient(SHOW_TYPES_FOR_SWIPE & (~WindowInsets.Type.navigationBars()), z);
        } else {
          this.mDisplayContent.getInsetsPolicy().showTransient(SHOW_TYPES_FOR_SWIPE, z);
        }
        if (this.mDisplayContent.isDexMode()) {
          controlTarget.showInsets((~WindowInsets.Type.statusBars()) & statusBars, false, null);
          return;
        } else {
          controlTarget.showInsets(statusBars, false, null);
          return;
        }
      }
      if (this.mDisplayContent.isDexMode()) {
        controlTarget.showInsets(WindowInsets.Type.navigationBars(), false, null);
      } else {
        controlTarget.showInsets(
            WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars(), false, null);
      }
      if (windowState == this.mStatusBar) {
        if (this.mDisplayContent.isDexMode()) {
          if (CoreRune.SAFE_DEBUG) {
            Slog.i(
                StartingSurfaceController.TAG,
                "Don't transfer touch to the status bar in DexMode.");
          }
        } else {
          if (this.mSkipTransferTouchToStatusBar || this.mStatusBar.transferTouch()) {
            return;
          }
          Slog.i(StartingSurfaceController.TAG, "Could not transfer touch to the status bar");
        }
      }
    }
  }

  public boolean isKeyguardShowing() {
    return this.mService.mPolicy.isKeyguardShowing();
  }

  public final boolean isKeyguardOccluded() {
    return this.mService.mExt.mPolicyExt.isKeyguardOccluded(getDisplayId());
  }

  public InsetsPolicy getInsetsPolicy() {
    return this.mDisplayContent.getInsetsPolicy();
  }

  public void addRelaunchingApp(ActivityRecord activityRecord) {
    if (!this.mSystemBarColorApps.contains(activityRecord) || activityRecord.hasStartingWindow()) {
      return;
    }
    this.mRelaunchingSystemBarColorApps.add(activityRecord);
  }

  public void removeRelaunchingApp(ActivityRecord activityRecord) {
    if (this.mRelaunchingSystemBarColorApps.remove(activityRecord)
        && this.mRelaunchingSystemBarColorApps.isEmpty()) {
      updateSystemBarAttributes();
    }
  }

  public void resetSystemBarAttributes() {
    this.mLastDisableFlags = 0;
    updateSystemBarAttributes();
  }

  /* JADX WARN: Removed duplicated region for block: B:116:0x0243  */
  /* JADX WARN: Removed duplicated region for block: B:119:0x0252  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void updateSystemBarAttributes() {
    boolean inSplitScreenWindowingMode;
    String str;
    boolean z;
    int i;
    boolean z2;
    final boolean z3;
    final int i2;
    final int i3;
    WindowState windowState;
    WindowState windowState2 = this.mFocusedWindow;
    if (windowState2 == null) {
      windowState2 = this.mTopFullscreenOpaqueWindowState;
    }
    if (windowState2 == null) {
      return;
    }
    if (shouldKeepSystemUiControllingWindow()) {
      if (CoreRune.SAFE_DEBUG) {
        Slog.d(
            StartingSurfaceController.TAG,
            "updateSystemBarAttributes: keep " + this.mSystemUiControllingWindow);
        return;
      }
      return;
    }
    if (windowState2.getAttrs().token == this.mImmersiveModeConfirmation.getWindowToken()) {
      if (this.mNotificationShade != null && this.mNotificationShade.canReceiveKeys()) {
        windowState2 = this.mNotificationShade;
      } else {
        WindowState windowState3 = this.mLastFocusedWindow;
        if (windowState3 != null && windowState3.canReceiveKeys()) {
          windowState2 = this.mLastFocusedWindow;
        } else {
          windowState2 = this.mTopFullscreenOpaqueWindowState;
        }
      }
      if (windowState2 == null) {
        return;
      }
    }
    this.mSystemUiControllingWindow = windowState2;
    final int displayId = getDisplayId();
    final int disableFlags = windowState2.getDisableFlags();
    int updateSystemBarsLw = updateSystemBarsLw(windowState2, disableFlags);
    if (this.mRelaunchingSystemBarColorApps.isEmpty()) {
      WindowState chooseNavigationColorWindowLw =
          chooseNavigationColorWindowLw(
              this.mNavBarColorWindowCandidate,
              this.mDisplayContent.mInputMethodWindow,
              this.mNavigationBarPosition);
      boolean z4 =
          chooseNavigationColorWindowLw != null
              && chooseNavigationColorWindowLw == this.mDisplayContent.mInputMethodWindow;
      boolean canBeNavColorWinLw =
          this.mExt.canBeNavColorWinLw(windowState2, chooseNavigationColorWindowLw);
      int i4 = windowState2.mAttrs.insetsFlags.appearance;
      if (windowState2.inMultiWindowMode() && (i4 & 4) != 0) {
        i4 &= -5;
      }
      if (CoreRune.MW_EMBED_ACTIVITY_SYSTEM_BAR_POLICY
          && z4
          && (windowState = this.mNavBarBackgroundWindow) != null
          && windowState.isSplitEmbedded()
          && (updateSystemBarsLw & 256) != 0) {
        updateSystemBarsLw &= -257;
      }
      final int updateLightNavigationBarLw =
          updateLightNavigationBarLw(
                  i4, canBeNavColorWinLw ? windowState2 : chooseNavigationColorWindowLw)
              | updateSystemBarsLw;
      topAppHidesSystemBar(WindowInsets.Type.navigationBars());
      boolean isInImmersiveSplitMode = isInImmersiveSplitMode(windowState2);
      int i5 = isInImmersiveSplitMode ? 2 : windowState2.mAttrs.insetsFlags.behavior;
      final String str2 = windowState2.mAttrs.packageName;
      boolean z5 =
          (windowState2.isRequestedVisible(WindowInsets.Type.statusBars())
                  && windowState2.isRequestedVisible(WindowInsets.Type.navigationBars(), true))
              ? false
              : true;
      if (isInImmersiveSplitMode) {
        inSplitScreenWindowingMode =
            windowState2.getWindowType() == 2226 || windowState2.getWindowType() == 2040;
      } else {
        inSplitScreenWindowingMode = windowState2.inSplitScreenWindowingMode();
      }
      boolean z6 =
          (inSplitScreenWindowingMode
                  || windowState2.isRequestedVisible(WindowInsets.Type.statusBars())
                  || windowState2.isRequestedVisible(WindowInsets.Type.navigationBars()))
              ? false
              : true;
      final AppearanceRegion[] appearanceRegionArr =
          new AppearanceRegion[this.mStatusBarAppearanceRegionList.size()];
      this.mStatusBarAppearanceRegionList.toArray(appearanceRegionArr);
      final int naturalBarTypeByDisplayId =
          StatusBarManager.getNaturalBarTypeByDisplayId(this.mContext, displayId);
      if (this.mLastDisableFlags != disableFlags) {
        this.mLastDisableFlags = disableFlags;
        final String windowState4 = windowState2.toString();
        str = StartingSurfaceController.TAG;
        callStatusBarSafely(
            new Consumer() { // from class:
                             // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((StatusBarManagerInternal) obj)
                    .setDisableFlagsToType(
                        displayId, disableFlags, windowState4, naturalBarTypeByDisplayId);
              }
            });
      } else {
        str = StartingSurfaceController.TAG;
      }
      final int requestedVisibleTypes = windowState2.getRequestedVisibleTypes();
      final LetterboxDetails[] letterboxDetailsArr =
          new LetterboxDetails[this.mLetterboxDetails.size()];
      this.mLetterboxDetails.toArray(letterboxDetailsArr);
      if (this.mLastAppearance == updateLightNavigationBarLw
          && this.mLastBehavior == i5
          && this.mLastRequestedVisibleTypes == requestedVisibleTypes
          && Objects.equals(this.mFocusedApp, str2)
          && this.mLastFocusIsFullscreen == z5
          && this.mLastFocusIsFullscreenAll == z6
          && Arrays.equals(this.mLastStatusBarAppearanceRegions, appearanceRegionArr)
          && Arrays.equals(this.mLastLetterboxDetails, letterboxDetailsArr)) {
        return;
      }
      if (this.mDisplayContent.isDefaultDisplay
          && (this.mLastFocusIsFullscreen != z5
              || ((this.mLastAppearance ^ updateLightNavigationBarLw) & 4) != 0)) {
        this.mService.mInputManager.setSystemUiLightsOut(
            z5 || (updateLightNavigationBarLw & 4) != 0);
      } else if (getDisplayId() == 2
          && (this.mLastFocusIsFullscreen != z5
              || ((this.mLastAppearance ^ updateLightNavigationBarLw) & 4) != 0)) {
        InputManagerService inputManagerService = this.mService.mInputManager;
        if (z5 || (updateLightNavigationBarLw & 4) != 0) {
          z = z6;
          i = 2;
          z2 = true;
        } else {
          z = z6;
          i = 2;
          z2 = false;
        }
        inputManagerService.setSystemUiLightsOut(z2, i);
        this.mLastAppearance = updateLightNavigationBarLw;
        this.mLastBehavior = i5;
        this.mLastRequestedVisibleTypes = requestedVisibleTypes;
        this.mFocusedApp = str2;
        this.mLastFocusIsFullscreen = z5;
        this.mLastStatusBarAppearanceRegions = appearanceRegionArr;
        this.mLastLetterboxDetails = letterboxDetailsArr;
        z3 = z;
        final boolean z7 = z4;
        final int i6 = i5;
        i2 = i5;
        callStatusBarSafely(
            new Consumer() { // from class:
                             // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((StatusBarManagerInternal) obj)
                    .onSystemBarAttributesChanged(
                        displayId,
                        updateLightNavigationBarLw,
                        appearanceRegionArr,
                        z7,
                        i6,
                        requestedVisibleTypes,
                        str2,
                        letterboxDetailsArr);
              }
            });
        if (CoreRune.CARLIFE_NAVBAR
            && this.mDisplayContent.isCarLifeDisplay()
            && this.mLastFocusIsFullscreenAll != z3) {
          final boolean z8 = z4;
          callStatusBarSafely(
              new Consumer() { // from class:
                               // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ((StatusBarManagerInternal) obj)
                      .onSystemBarAttributesChangedToType(
                          displayId,
                          updateLightNavigationBarLw,
                          appearanceRegionArr,
                          z8,
                          i2,
                          requestedVisibleTypes,
                          str2,
                          letterboxDetailsArr,
                          2);
                }
              });
        }
        if (this.mLastFocusIsFullscreenAll == z3) {
          this.mLastFocusIsFullscreenAll = z3;
          i3 = updateLightNavigationBarLw;
          callCocktailBarSafely(
              new Consumer() { // from class:
                               // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayPolicy.lambda$updateSystemBarAttributes$9(
                      displayId, i3, appearanceRegionArr, z3, (CocktailBarManagerInternal) obj);
                }
              });
        } else {
          i3 = updateLightNavigationBarLw;
        }
        Slog.d(
            str,
            "updateSystemBarAttributes: displayId="
                + displayId
                + ", win="
                + windowState2
                + ", navColorWin="
                + chooseNavigationColorWindowLw
                + ", focusedCanBeNavColorWin="
                + canBeNavColorWinLw
                + ", behavior="
                + i2
                + ", appearance="
                + i3
                + ", statusBarAppearanceRegions="
                + this.mStatusBarAppearanceRegionList
                + ", requestedVisibilities="
                + requestedVisibleTypes
                + ", from="
                + Debug.getCallers(3));
      }
      z = z6;
      this.mLastAppearance = updateLightNavigationBarLw;
      this.mLastBehavior = i5;
      this.mLastRequestedVisibleTypes = requestedVisibleTypes;
      this.mFocusedApp = str2;
      this.mLastFocusIsFullscreen = z5;
      this.mLastStatusBarAppearanceRegions = appearanceRegionArr;
      this.mLastLetterboxDetails = letterboxDetailsArr;
      z3 = z;
      final boolean z72 = z4;
      final int i62 = i5;
      i2 = i5;
      callStatusBarSafely(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((StatusBarManagerInternal) obj)
                  .onSystemBarAttributesChanged(
                      displayId,
                      updateLightNavigationBarLw,
                      appearanceRegionArr,
                      z72,
                      i62,
                      requestedVisibleTypes,
                      str2,
                      letterboxDetailsArr);
            }
          });
      if (CoreRune.CARLIFE_NAVBAR) {
        final boolean z82 = z4;
        callStatusBarSafely(
            new Consumer() { // from class:
                             // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda2
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((StatusBarManagerInternal) obj)
                    .onSystemBarAttributesChangedToType(
                        displayId,
                        updateLightNavigationBarLw,
                        appearanceRegionArr,
                        z82,
                        i2,
                        requestedVisibleTypes,
                        str2,
                        letterboxDetailsArr,
                        2);
              }
            });
      }
      if (this.mLastFocusIsFullscreenAll == z3) {}
      Slog.d(
          str,
          "updateSystemBarAttributes: displayId="
              + displayId
              + ", win="
              + windowState2
              + ", navColorWin="
              + chooseNavigationColorWindowLw
              + ", focusedCanBeNavColorWin="
              + canBeNavColorWinLw
              + ", behavior="
              + i2
              + ", appearance="
              + i3
              + ", statusBarAppearanceRegions="
              + this.mStatusBarAppearanceRegionList
              + ", requestedVisibilities="
              + requestedVisibleTypes
              + ", from="
              + Debug.getCallers(3));
    }
  }

  public static /* synthetic */ void lambda$updateSystemBarAttributes$9(
      int i,
      int i2,
      AppearanceRegion[] appearanceRegionArr,
      boolean z,
      CocktailBarManagerInternal cocktailBarManagerInternal) {
    cocktailBarManagerInternal.onSystemBarAppearanceChanged(i, i2, appearanceRegionArr);
    cocktailBarManagerInternal.topAppWindowChanged(i, z, true);
  }

  public final void callStatusBarSafely(final Consumer consumer) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda15
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.this.lambda$callStatusBarSafely$10(consumer);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$callStatusBarSafely$10(Consumer consumer) {
    StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
    if (statusBarManagerInternal != null) {
      consumer.accept(statusBarManagerInternal);
    }
  }

  public static WindowState chooseNavigationColorWindowLw(
      WindowState windowState, WindowState windowState2, int i) {
    return !(windowState2 != null
            && windowState2.isVisible()
            && i == 4
            && (windowState2.mAttrs.flags & Integer.MIN_VALUE) != 0)
        ? windowState
        : (windowState == null
                || !windowState.isDimming()
                || WindowManager.LayoutParams.mayUseInputMethod(windowState.mAttrs.flags))
            ? windowState2
            : windowState;
  }

  public int updateLightNavigationBarLw(int i, WindowState windowState) {
    if (windowState == null
        || !isLightBarAllowed(windowState, WindowInsets.Type.navigationBars())) {
      int i2 = i & (-17);
      return (windowState == null || !this.mExt.isUsingBlurEffect(windowState.mAttrs))
          ? i2
          : i2 | (windowState.mAttrs.insetsFlags.appearance & 16);
    }
    boolean z = this.mIsResizingByDivider;
    boolean z2 = false;
    boolean z3 =
        this.mDisplayContent.isDefaultDisplay
            && this.mService.mAtmService.mNaturalSwitchingController.isRunning();
    if (z || z3) {
      return i & (-17);
    }
    if (windowState.isDexMode()) {
      if (windowState.mActivityRecord != null
          && windowState.getWindowingMode() == 1
          && (windowState.isActivityTypeStandard() || windowState.isActivityTypeAssistant())) {
        z2 = true;
      }
      if (z2) {
        return (i & (-17)) | 2;
      }
    }
    return (i & (-17)) | (windowState.mAttrs.insetsFlags.appearance & 16);
  }

  public final int updateSystemBarsLw(WindowState windowState, int i) {
    WindowState windowState2;
    WindowState windowState3;
    ActivityRecord activityRecord;
    StatusBarManagerInternal statusBarManagerInternal;
    TaskDisplayArea defaultTaskDisplayArea = this.mDisplayContent.getDefaultTaskDisplayArea();
    Task topRootTaskInWindowingMode = defaultTaskDisplayArea.getTopRootTaskInWindowingMode(1);
    boolean z = false;
    boolean z2 =
        topRootTaskInWindowingMode != null
            && topRootTaskInWindowingMode.isVisible()
            && topRootTaskInWindowingMode.getTopLeafTask().inSplitScreenWindowingMode();
    boolean isRootTaskVisible = defaultTaskDisplayArea.isRootTaskVisible(5);
    getInsetsPolicy().updateSystemBars(windowState, z2, isRootTaskVisible);
    boolean z3 = topAppHidesSystemBar(WindowInsets.Type.statusBars());
    if (getStatusBar() != null
        && (statusBarManagerInternal = getStatusBarManagerInternal()) != null) {
      statusBarManagerInternal.setTopAppHidesStatusBar(z3);
    }
    this.mTopIsFullscreen =
        z3 && (this.mNotificationShade == null || !this.mNotificationShade.isVisible());
    int configureNavBarOpacity =
        configureNavBarOpacity(
            configureStatusBarOpacity(3),
            z2,
            isRootTaskVisible,
            CoreRune.FW_FIXED_ASPECT_RATIO_MODE
                && (activityRecord = windowState.mActivityRecord) != null
                && activityRecord.mCompatRecord.isFixedAspectRatioModeEnabled()
                && windowState.mActivityRecord.getConfiguration().orientation == 1);
    boolean z4 = this.mIsImmersiveMode;
    boolean isImmersiveMode = isImmersiveMode(windowState);
    if (z4 != isImmersiveMode) {
      this.mIsImmersiveMode = isImmersiveMode;
      DisplayContent displayContent = this.mDisplayContent;
      if (displayContent.isDefaultDisplay && !displayContent.isDexMode()) {
        RootDisplayArea rootDisplayArea = windowState.getRootDisplayArea();
        this.mImmersiveModeConfirmation.immersiveModeChangedLw(
            rootDisplayArea == null ? -1 : rootDisplayArea.mFeatureId,
            isImmersiveMode,
            this.mService.mPolicy.isUserSetupComplete(),
            isNavBarEmpty(i),
            windowState);
      }
      if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE
          && this.mDisplayContent.isDexMode()
          && windowState.getTask() != null
          && windowState.getTask().inFullscreenWindowingMode()) {
        this.mService.mAtmService.mTaskOrganizerController.onImmersiveModeChanged(
            windowState.getTask(), this.mIsImmersiveMode);
      }
    }
    if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE
        && (windowState3 = this.mTopFullscreenOpaqueWindowState) != null
        && windowState3.getTask() != null
        && this.mTopFullscreenOpaqueWindowState.getTask().inFullscreenWindowingMode()) {
      boolean z5 = this.mIsCaptionImmersiveMode;
      boolean isCaptionImmersiveMode = isCaptionImmersiveMode(this.mTopFullscreenOpaqueWindowState);
      if (this.mDisplayContent.isNewDexMode()) {
        boolean z6 =
            isCaptionImmersiveMode
                && z5
                && this.mTopFullscreenOpaqueWindowState.getTask().mTaskId != this.mImmersiveTaskId;
        if (z5 != isCaptionImmersiveMode || z6) {
          this.mIsCaptionImmersiveMode = isCaptionImmersiveMode;
          this.mImmersiveTaskId =
              isCaptionImmersiveMode ? this.mTopFullscreenOpaqueWindowState.getTask().mTaskId : -1;
          this.mService.mAtmService.mTaskOrganizerController.onImmersiveModeChanged(
              this.mTopFullscreenOpaqueWindowState.getTask(), isCaptionImmersiveMode);
          setTransientVisibilityChangeListener(
              this.mTopFullscreenOpaqueWindowState, isCaptionImmersiveMode);
        } else {
          boolean z7 = !isStatusBarVisibleLw();
          if (this.mWasStatusInvisible != z7) {
            this.mWasStatusInvisible = z7;
            if (this.mIsVisibleBySwipe) {
              this.mIsVisibleBySwipe = false;
            } else {
              this.mService.mAtmService.mTaskOrganizerController.onImmersiveModeChanged(
                  this.mTopFullscreenOpaqueWindowState.getTask(), z7);
              setTransientVisibilityChangeListener(this.mTopFullscreenOpaqueWindowState, z7);
            }
          }
        }
      } else if (!this.mDisplayContent.isDesktopModeEnabled()
          && z5 != isCaptionImmersiveMode
          && windowState.getTask() != null) {
        this.mIsCaptionImmersiveMode = isCaptionImmersiveMode;
        windowState.getTask().updateFullScreenCaptionState(isCaptionImmersiveMode);
      }
    }
    boolean z8 = !windowState.isRequestedVisible(WindowInsets.Type.navigationBars());
    long uptimeMillis = SystemClock.uptimeMillis();
    long j = this.mPendingPanicGestureUptime;
    if (j != 0 && uptimeMillis - j <= 30000) {
      z = true;
    }
    DisplayPolicy displayPolicy = this.mService.getDefaultDisplayContentLocked().getDisplayPolicy();
    if (z && z8 && isImmersiveMode && displayPolicy.isKeyguardDrawComplete()) {
      this.mPendingPanicGestureUptime = 0L;
      if ((windowState.getAttrs().type != 2226 || !isKeyguardShowing() || isKeyguardOccluded())
          && (((windowState2 = this.mNavigationBar) == null || !windowState2.mHasSeamlessOperation)
              && !isNavBarEmpty(i))) {
        this.mDisplayContent.getInsetsPolicy().showTransient(SHOW_TYPES_FOR_PANIC, true);
      }
    }
    return configureNavBarOpacity;
  }

  public static boolean isLightBarAllowed(WindowState windowState, int i) {
    if (windowState == null) {
      return false;
    }
    return intersectsAnyInsets(windowState.getFrame(), windowState.getInsetsState(), i);
  }

  public final Rect getBarContentFrameForWindow(WindowState windowState, int i) {
    DisplayFrames displayFrames = windowState.getDisplayFrames(this.mDisplayContent.mDisplayFrames);
    InsetsState insetsState = displayFrames.mInsetsState;
    Rect rect = displayFrames.mUnrestricted;
    Rect rect2 = sTmpDisplayCutoutSafe;
    Insets waterfallInsets = insetsState.getDisplayCutout().getWaterfallInsets();
    Rect rect3 = new Rect();
    Rect rect4 = sTmpRect;
    rect2.set(displayFrames.mDisplayCutoutSafe);
    for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
      if (sourceAt.getType() == i) {
        if (i == WindowInsets.Type.statusBars()) {
          rect2.set(displayFrames.mDisplayCutoutSafe);
          Insets calculateInsets = sourceAt.calculateInsets(rect, true);
          if (calculateInsets.left > 0) {
            int i2 = rect.left;
            rect2.left = Math.max(waterfallInsets.left + i2, i2);
          } else if (calculateInsets.top > 0) {
            int i3 = rect.top;
            rect2.top = Math.max(waterfallInsets.top + i3, i3);
          } else if (calculateInsets.right > 0) {
            int i4 = rect.right;
            rect2.right = Math.max(i4 - waterfallInsets.right, i4);
          } else if (calculateInsets.bottom > 0) {
            int i5 = rect.bottom;
            rect2.bottom = Math.max(i5 - waterfallInsets.bottom, i5);
          }
        }
        rect4.set(sourceAt.getFrame());
        rect4.intersect(rect2);
        rect3.union(rect4);
      }
    }
    return rect3;
  }

  public boolean isFullyTransparentAllowed(WindowState windowState, int i) {
    if (windowState == null) {
      return true;
    }
    return windowState.isFullyTransparentBarAllowed(getBarContentFrameForWindow(windowState, i));
  }

  public final boolean drawsBarBackground(WindowState windowState) {
    if (windowState == null) {
      return true;
    }
    return ((windowState.getAttrs().privateFlags & 32768) != 0)
        || ((windowState.getAttrs().flags & Integer.MIN_VALUE) != 0);
  }

  public final int configureStatusBarOpacity(int i) {
    boolean z = true;
    boolean z2 = true;
    for (int size = this.mStatusBarBackgroundWindows.size() - 1; size >= 0; size--) {
      WindowState windowState = (WindowState) this.mStatusBarBackgroundWindows.get(size);
      z &= drawsBarBackground(windowState);
      z2 &= isFullyTransparentAllowed(windowState, WindowInsets.Type.statusBars());
    }
    if (z) {
      i &= -2;
    }
    return !z2 ? i | 32 : i;
  }

  /* JADX WARN: Code restructure failed: missing block: B:55:0x00d7, code lost:

     if (com.samsung.android.multiwindow.MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED != false) goto L95;
  */
  /* JADX WARN: Code restructure failed: missing block: B:56:0x0128, code lost:

     r3 = false;
  */
  /* JADX WARN: Code restructure failed: missing block: B:79:0x0125, code lost:

     if (r8 == false) goto L94;
  */
  /* JADX WARN: Removed duplicated region for block: B:58:0x012b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int configureNavBarOpacity(int i, boolean z, boolean z2, boolean z3) {
    int clearNavBarOpaqueFlag;
    WindowState windowState;
    boolean drawsBarBackground = drawsBarBackground(this.mNavBarBackgroundWindow);
    int i2 = this.mNavBarOpacityMode;
    boolean z4 = true;
    if (i2 == 2) {
      if (z2
          && (this.mIsFreeformWindowOverlappingWithNavBar
              || this.mFreeformTaskSurfaceOverlappingWithNavBar)) {
        clearNavBarOpaqueFlag = clearNavBarOpaqueFlag(i);
        i = clearNavBarOpaqueFlag | 128;
      } else if (drawsBarBackground) {
        i = clearNavBarOpaqueFlag(i);
      }
    } else if (i2 == 0) {
      if (z || z2) {
        if (this.mIsFreeformWindowOverlappingWithNavBar
            || this.mFreeformTaskSurfaceOverlappingWithNavBar) {
          clearNavBarOpaqueFlag = clearNavBarOpaqueFlag(i);
          i = clearNavBarOpaqueFlag | 128;
        }
      } else if (drawsBarBackground) {
        i = clearNavBarOpaqueFlag(i);
      }
    } else if (i2 == 1 && z2) {
      i = clearNavBarOpaqueFlag(i);
    }
    if (!isFullyTransparentAllowed(this.mNavBarBackgroundWindow, WindowInsets.Type.navigationBars())
        || z3) {
      i |= 64;
    }
    if (this.mService.mAtmService.mNaturalSwitchingController.isRunning()) {
      return clearNavBarOpaqueFlag(i);
    }
    if (this.mExt.isGameToolsOverlayVisibleLw()) {
      return clearNavBarOpaqueFlag(i);
    }
    if (CoreRune.MW_EMBED_ACTIVITY_SYSTEM_BAR_POLICY
        && (windowState = this.mNavBarBackgroundWindow) != null
        && windowState.isSplitEmbedded()) {
      return clearNavBarOpaqueFlag(i) | 256;
    }
    if (z2 && drawsBarBackground) {
      WindowState windowState2 = this.mNavBarBackgroundWindow;
      if (windowState2 == null
          || windowState2.isRequestedVisible(WindowInsets.Type.navigationBars())) {
        return clearNavBarOpaqueFlag(i);
      }
      return 128;
    }
    if (this.mIsPipWindowOverlappingWithNavBar
        && this.mDisplayContent.getDefaultTaskDisplayArea().isRootTaskVisible(2)) {
      return clearNavBarOpaqueFlag(i) | 64;
    }
    if (z) {
      if (CoreRune.MW_SPLIT_TRANSPARENT_NAVIGATION_BAR) {
        WindowState windowState3 = this.mNavBarBackgroundWindow;
        if (windowState3 == null) {
          if ((this.mLastAppearance & 2) != 0) {
            if (!MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {}
          }
          if (z4) {
            return clearNavBarOpaqueFlag(i);
          }
        } else {
          if (windowState3.inSplitScreenWindowingMode()) {
            WindowManager.LayoutParams attrs = this.mNavBarBackgroundWindow.getAttrs();
            boolean z5 =
                this.mNavBarBackgroundWindow.isRequestedVisible(WindowInsets.Type.navigationBars())
                    && (attrs.flags & Integer.MIN_VALUE) != 0;
            Task task = this.mNavBarBackgroundWindow.getTask();
            if (z5 && task != null && task.mMinHeight != -1) {
              Rect rect = sTmpRect;
              task.getDimBounds(rect);
              if (rect.height() < task.mMinHeight) {
                z5 = false;
              }
            }
            boolean z6 = (attrs.multiwindowFlags & 4) != 0;
            if (!z5) {}
          }
          if (z4) {}
        }
      } else if (CoreRune.MW_MULTI_SPLIT) {
        return clearNavBarOpaqueFlag(i);
      }
    }
    return i;
  }

  public final boolean isImmersiveMode(WindowState windowState) {
    if (windowState == null
        || windowState == getNotificationShade()
        || windowState.isActivityTypeDream()) {
      return false;
    }
    if (windowState.isDexMode() && windowState.getWindowingMode() == 1) {
      WindowState navControlWindow =
          windowState.isChildWindow() ? getInsetsPolicy().getNavControlWindow(windowState) : null;
      if (navControlWindow != null) {
        windowState = navControlWindow;
      }
      if (windowState.isRequestedVisible(WindowInsets.Type.navigationBars(), true)) {
        return false;
      }
    } else if (windowState.mAttrs.insetsFlags.behavior != 2
        || windowState.getWindowingMode() != 1) {
      return false;
    }
    return getInsetsPolicy().hasHiddenSources(WindowInsets.Type.navigationBars())
        || getInsetsPolicy().isTransient(WindowInsets.Type.navigationBars());
  }

  public void onPowerKeyDown(boolean z) {
    if (this.mImmersiveModeConfirmation.onPowerKeyDown(
        z,
        SystemClock.elapsedRealtime(),
        isImmersiveMode(this.mSystemUiControllingWindow),
        isNavBarEmpty(this.mLastDisableFlags))) {
      this.mHandler.post(this.mHiddenNavPanic);
    }
  }

  public void onVrStateChangedLw(boolean z) {
    this.mImmersiveModeConfirmation.onVrStateChangedLw(z);
  }

  public void onLockTaskStateChangedLw(int i) {
    this.mImmersiveModeConfirmation.onLockTaskModeChangedLw(i);
  }

  public void onUserActivityEventTouch() {
    if (this.mAwake) {
      return;
    }
    WindowState windowState = this.mNotificationShade;
    this.mService.mAtmService.setProcessAnimatingWhileDozing(
        windowState != null ? windowState.getProcess() : null);
  }

  public boolean onSystemUiSettingsChanged() {
    return this.mImmersiveModeConfirmation.onSettingChanged(this.mService.mCurrentUserId);
  }

  public void takeScreenshot(int i, int i2) {
    if (this.mScreenshotHelper != null) {
      this.mScreenshotHelper.takeScreenshot(
          new ScreenshotRequest.Builder(i, i2).build(), this.mHandler, (Consumer) null);
    }
  }

  public RefreshRatePolicy getRefreshRatePolicy() {
    return this.mRefreshRatePolicy;
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.print(str);
    printWriter.println("DisplayPolicy");
    String str2 = str + "  ";
    String str3 = str2 + "  ";
    printWriter.print(str2);
    printWriter.print("mCarDockEnablesAccelerometer=");
    printWriter.print(this.mCarDockEnablesAccelerometer);
    printWriter.print(" mDeskDockEnablesAccelerometer=");
    printWriter.println(this.mDeskDockEnablesAccelerometer);
    printWriter.print(str2);
    printWriter.print("mDockMode=");
    printWriter.print(Intent.dockStateToString(this.mDockMode));
    printWriter.print(" mLidState=");
    printWriter.println(WindowManagerPolicy.WindowManagerFuncs.lidStateToString(this.mLidState));
    printWriter.print(str2);
    printWriter.print("mAwake=");
    printWriter.print(this.mAwake);
    printWriter.print(" mScreenOnEarly=");
    printWriter.print(this.mScreenOnEarly);
    printWriter.print(" mScreenOnFully=");
    printWriter.println(this.mScreenOnFully);
    printWriter.print(str2);
    printWriter.print("mKeyguardDrawComplete=");
    printWriter.print(this.mKeyguardDrawComplete);
    printWriter.print(" mWindowManagerDrawComplete=");
    printWriter.println(this.mWindowManagerDrawComplete);
    printWriter.print(str2);
    printWriter.print("mHdmiPlugged=");
    printWriter.println(this.mHdmiPlugged);
    if (this.mLastDisableFlags != 0) {
      printWriter.print(str2);
      printWriter.print("mLastDisableFlags=0x");
      printWriter.println(Integer.toHexString(this.mLastDisableFlags));
    }
    if (this.mLastAppearance != 0) {
      printWriter.print(str2);
      printWriter.print("mLastAppearance=");
      printWriter.println(
          ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.mLastAppearance));
    }
    if (this.mLastBehavior != 0) {
      printWriter.print(str2);
      printWriter.print("mLastBehavior=");
      printWriter.println(
          ViewDebug.flagsToString(InsetsFlags.class, "behavior", this.mLastBehavior));
    }
    printWriter.print(str2);
    printWriter.print("mShowingDream=");
    printWriter.print(this.mShowingDream);
    printWriter.print(" mDreamingLockscreen=");
    printWriter.println(this.mDreamingLockscreen);
    if (this.mStatusBar != null) {
      printWriter.print(str2);
      printWriter.print("mStatusBar=");
      printWriter.println(this.mStatusBar);
    }
    if (this.mNotificationShade != null) {
      printWriter.print(str2);
      printWriter.print("mExpandedPanel=");
      printWriter.println(this.mNotificationShade);
    }
    printWriter.print(str2);
    printWriter.print("isKeyguardShowing=");
    printWriter.println(isKeyguardShowing());
    if (this.mNavigationBar != null) {
      printWriter.print(str2);
      printWriter.print("mNavigationBar=");
      printWriter.println(this.mNavigationBar);
      printWriter.print(str2);
      printWriter.print("mNavBarOpacityMode=");
      printWriter.println(this.mNavBarOpacityMode);
      printWriter.print(str2);
      printWriter.print("mNavigationBarCanMove=");
      printWriter.println(this.mNavigationBarCanMove);
      printWriter.print(str2);
      printWriter.print("mNavigationBarPosition=");
      printWriter.println(this.mNavigationBarPosition);
      if (this.mCurrentUserResources != null) {
        printWriter.print(str2);
        printWriter.println("mCurrentUserResources");
        printWriter.print(str2);
        printWriter.print(" .mUserId=");
        printWriter.println(this.mCurrentUserResources.mUserId);
        printWriter.print(str2);
        printWriter.print(" .getAssets().getApkPaths()=");
        printWriter.println(Arrays.toString(this.mCurrentUserResources.getAssets().getApkPaths()));
        printWriter.print(str2);
        printWriter.print(" .getConfiguration()=");
        printWriter.println(this.mCurrentUserResources.getConfiguration());
        printWriter.print(str2);
        if (this.mDisplayContent.isDexMode()) {
          printWriter.print(" .getDimensionPixelSize(R.dimen.task_bar_height)=");
          printWriter.println(this.mCurrentUserResources.getDimensionPixelSize(17106192));
        } else {
          printWriter.print(" .getDimensionPixelSize(R.dimen.navigation_bar_height)=");
          printWriter.println(
              this.mCurrentUserResources.getDimensionPixelSize(
                  R.dimen.notification_custom_view_max_image_height_low_ram));
        }
      }
    }
    WindowState windowState = this.mStatusBar;
    WindowState windowState2 = this.mDefaultStatusBar;
    if (windowState != windowState2 && windowState2 != null) {
      printWriter.print(str2);
      printWriter.print("mDefaultStatusBar=");
      printWriter.println(this.mDefaultStatusBar);
    }
    WindowState windowState3 = this.mNotificationShade;
    WindowState windowState4 = this.mDefaultNotificationShade;
    if (windowState3 != windowState4 && windowState4 != null) {
      printWriter.print(str2);
      printWriter.print("mDefaultNotificationShade=");
      printWriter.println(this.mDefaultNotificationShade);
    }
    WindowState windowState5 = this.mNavigationBar;
    WindowState windowState6 = this.mDefaultNavigationBar;
    if (windowState5 != windowState6 && windowState6 != null) {
      printWriter.print(str2);
      printWriter.print("mDefaultNavigationBar=");
      printWriter.println(this.mDefaultNavigationBar);
    }
    if (shouldKeepSystemUiControllingWindow()) {
      printWriter.print(str2);
      printWriter.print("shouldKeepSystemUiControllingWindow=true");
    }
    if (this.mLeftGestureHost != null) {
      printWriter.print(str2);
      printWriter.print("mLeftGestureHost=");
      printWriter.println(this.mLeftGestureHost);
    }
    if (this.mTopGestureHost != null) {
      printWriter.print(str2);
      printWriter.print("mTopGestureHost=");
      printWriter.println(this.mTopGestureHost);
    }
    if (this.mRightGestureHost != null) {
      printWriter.print(str2);
      printWriter.print("mRightGestureHost=");
      printWriter.println(this.mRightGestureHost);
    }
    if (this.mBottomGestureHost != null) {
      printWriter.print(str2);
      printWriter.print("mBottomGestureHost=");
      printWriter.println(this.mBottomGestureHost);
    }
    if (this.mFocusedWindow != null) {
      printWriter.print(str2);
      printWriter.print("mFocusedWindow=");
      printWriter.println(this.mFocusedWindow);
    }
    if (this.mTopFullscreenOpaqueWindowState != null) {
      printWriter.print(str2);
      printWriter.print("mTopFullscreenOpaqueWindowState=");
      printWriter.println(this.mTopFullscreenOpaqueWindowState);
    }
    if (!this.mSystemBarColorApps.isEmpty()) {
      printWriter.print(str2);
      printWriter.print("mSystemBarColorApps=");
      printWriter.println(this.mSystemBarColorApps);
    }
    if (!this.mRelaunchingSystemBarColorApps.isEmpty()) {
      printWriter.print(str2);
      printWriter.print("mRelaunchingSystemBarColorApps=");
      printWriter.println(this.mRelaunchingSystemBarColorApps);
    }
    if (this.mNavBarColorWindowCandidate != null) {
      printWriter.print(str2);
      printWriter.print("mNavBarColorWindowCandidate=");
      printWriter.println(this.mNavBarColorWindowCandidate);
    }
    if (this.mNavBarBackgroundWindow != null) {
      printWriter.print(str2);
      printWriter.print("mNavBarBackgroundWindow=");
      printWriter.println(this.mNavBarBackgroundWindow);
    }
    if (this.mLastStatusBarAppearanceRegions != null) {
      printWriter.print(str2);
      printWriter.println("mLastStatusBarAppearanceRegions=");
      for (int length = this.mLastStatusBarAppearanceRegions.length - 1; length >= 0; length--) {
        printWriter.print(str3);
        printWriter.println(this.mLastStatusBarAppearanceRegions[length]);
      }
    }
    if (this.mLastLetterboxDetails != null) {
      printWriter.print(str2);
      printWriter.println("mLastLetterboxDetails=");
      for (int length2 = this.mLastLetterboxDetails.length - 1; length2 >= 0; length2--) {
        printWriter.print(str3);
        printWriter.println(this.mLastLetterboxDetails[length2]);
      }
    }
    if (!this.mStatusBarBackgroundWindows.isEmpty()) {
      printWriter.print(str2);
      printWriter.println("mStatusBarBackgroundWindows=");
      for (int size = this.mStatusBarBackgroundWindows.size() - 1; size >= 0; size--) {
        WindowState windowState7 = (WindowState) this.mStatusBarBackgroundWindows.get(size);
        printWriter.print(str3);
        printWriter.println(windowState7);
      }
    }
    printWriter.print(str2);
    printWriter.print("mTopIsFullscreen=");
    printWriter.println(this.mTopIsFullscreen);
    printWriter.print(str2);
    printWriter.print("mForceShowNavigationBarEnabled=");
    printWriter.print(this.mForceShowNavigationBarEnabled);
    printWriter.print(" mAllowLockscreenWhenOn=");
    printWriter.println(this.mAllowLockscreenWhenOn);
    printWriter.print(str2);
    printWriter.print("mRemoteInsetsControllerControlsSystemBars=");
    printWriter.println(this.mRemoteInsetsControllerControlsSystemBars);
    printWriter.print(str2);
    printWriter.println("mDecorInsetsInfo:");
    this.mDecorInsets.dump(str3, printWriter);
    if (this.mCachedDecorInsets != null) {
      printWriter.print(str2);
      printWriter.println("mCachedDecorInsets:");
      this.mCachedDecorInsets.mDecorInsets.dump(str3, printWriter);
    }
    if (!ViewRootImpl.CLIENT_TRANSIENT) {
      this.mSystemGestures.dump(printWriter, str2);
    }
    printWriter.print(str2);
    printWriter.println("Looper state:");
    this.mHandler.getLooper().dump(new PrintWriterPrinter(printWriter), str2 + "  ");
    Context context = this.mUiContext;
    if (context != null && context.getResources() != null) {
      printWriter.print(str2);
      printWriter.println("UiContextResourcesConfig=");
      printWriter.print(str2);
      printWriter.println(this.mUiContext.getResources().getConfiguration());
      printWriter.print(str2);
      printWriter.println("UiContextResourcesMetrics=");
      printWriter.print(str2);
      printWriter.println(this.mUiContext.getResources().getDisplayMetrics());
    }
    printWriter.println();
    this.mExt.dump(str2, printWriter);
    if (CoreRune.FW_VRR_POLICY) {
      printWriter.println();
      this.mRefreshRatePolicy.dump(str2, printWriter);
    }
  }

  public final boolean supportsPointerLocation() {
    if (CoreRune.SYSFW_APP_SPEG && (this.mDisplayContent.getDisplayInfo().flags & 32768) != 0) {
      Slog.d("SPEG", "Pointer location is not supported");
      return false;
    }
    DisplayContent displayContent = this.mDisplayContent;
    return displayContent.isDefaultDisplay || !displayContent.isPrivate();
  }

  public void setPointerLocationEnabled(boolean z) {
    if (supportsPointerLocation()) {
      this.mHandler.sendEmptyMessage(z ? 4 : 5);
    }
  }

  public final void enablePointerLocation() {
    if (this.mPointerLocationView != null) {
      return;
    }
    PointerLocationView pointerLocationView = new PointerLocationView(this.mContext);
    this.mPointerLocationView = pointerLocationView;
    pointerLocationView.setPrintCoords(false);
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    layoutParams.type = 2015;
    layoutParams.flags = 280;
    layoutParams.privateFlags |= 16;
    layoutParams.setFitInsetsTypes(0);
    layoutParams.layoutInDisplayCutoutMode = 3;
    if (ActivityManager.isHighEndGfx()) {
      layoutParams.flags |= 16777216;
      layoutParams.privateFlags |= 2;
    }
    layoutParams.format = -3;
    layoutParams.setTitle("PointerLocation - display " + getDisplayId());
    layoutParams.inputFeatures = layoutParams.inputFeatures | 1;
    ((WindowManager) this.mContext.getSystemService(WindowManager.class))
        .addView(this.mPointerLocationView, layoutParams);
    this.mDisplayContent.registerPointerEventListener(this.mPointerLocationView);
  }

  public final void disablePointerLocation() {
    if (this.mPointerLocationView == null) {
      return;
    }
    if (!this.mDisplayContent.isRemoved()) {
      this.mDisplayContent.unregisterPointerEventListener(this.mPointerLocationView);
    }
    ((WindowManager) this.mContext.getSystemService(WindowManager.class))
        .removeView(this.mPointerLocationView);
    this.mPointerLocationView = null;
  }

  public boolean isWindowExcludedFromContent(WindowState windowState) {
    PointerLocationView pointerLocationView;
    return (windowState == null
            || (pointerLocationView = this.mPointerLocationView) == null
            || windowState.mClient != pointerLocationView.getWindowToken())
        ? false
        : true;
  }

  public void release() {
    this.mDisplayContent.mTransitionController.unregisterLegacyListener(
        this.mAppTransitionListener);
    Handler handler = this.mHandler;
    final GestureNavigationSettingsObserver gestureNavigationSettingsObserver =
        this.mGestureNavigationSettingsObserver;
    Objects.requireNonNull(gestureNavigationSettingsObserver);
    handler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            gestureNavigationSettingsObserver.unregister();
          }
        });
    Handler handler2 = this.mHandler;
    final ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver =
        this.mForceShowNavBarSettingsObserver;
    Objects.requireNonNull(forceShowNavBarSettingsObserver);
    handler2.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            forceShowNavBarSettingsObserver.unregister();
          }
        });
    this.mImmersiveModeConfirmation.release();
    this.mIsCmfaStarted = false;
    Slog.i(
        StartingSurfaceController.TAG,
        "release() >> CMFA mIsCmfaStarted is false for Display Id : " + getDisplayId());
    if (this.mService.mPointerLocationEnabled) {
      setPointerLocationEnabled(false);
    }
  }

  public static boolean isOverlappingWithNavBar(WindowState windowState) {
    if ((!CoreRune.FW_CUSTOM_LETTERBOX
            || !windowState.mAnimatingExit
            || windowState.mAttrs.type != 3
            || !CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(
                windowState.mActivityRecord))
        && !windowState.isVisible()) {
      return false;
    }
    if (windowState.getTask() != null && windowState.getTask().isFreeformStashed()) {
      InsetsState rawInsetsState =
          windowState.getDisplayContent().getInsetsStateController().getRawInsetsState();
      Rect stashedBounds = windowState.getTask().getStashedBounds();
      return stashedBounds != null
          && intersectsAnyInsets(stashedBounds, rawInsetsState, WindowInsets.Type.navigationBars());
    }
    if (windowState.getWindowConfiguration().tasksAreFloating()
        && windowState.getDisplayContent() != null) {
      return intersectsAnyInsets(
          windowState.isDimming() ? windowState.getBounds() : windowState.getFrame(),
          windowState.getDisplayContent().getInsetsStateController().getRawInsetsState(),
          WindowInsets.Type.navigationBars());
    }
    return intersectsAnyInsets(
        windowState.isDimming() ? windowState.getBounds() : windowState.getFrame(),
        windowState.getInsetsState(),
        WindowInsets.Type.navigationBars());
  }

  public static boolean intersectsAnyInsets(Rect rect, InsetsState insetsState, int i) {
    for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
      if ((sourceAt.getType() & i) != 0
          && sourceAt.isVisible()
          && Rect.intersects(rect, sourceAt.getFrame())) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldAttachNavBarToAppDuringTransition() {
    return this.mShouldAttachNavBarToAppDuringTransition && this.mNavigationBar != null;
  }

  public final void startEnableTouchEvent(boolean z) {
    Message message = new Message();
    message.what = 7;
    if (z) {
      message.arg1 = 1;
    } else {
      message.arg1 = 2;
    }
    this.mHandler.sendMessage(message);
  }

  public final void enableTouchListener(boolean z) {
    if (this.mIsCmfaStarted) {
      Slog.i(StartingSurfaceController.TAG, "TouchEventView is already added");
      return;
    }
    if (this.mTouchEventView == null) {
      this.mTouchEventView = new TouchEventView(this.mContext, this.mAuthTouchEnableListener);
    }
    Slog.i(StartingSurfaceController.TAG, "enableTouchListener debugmode:" + z);
    this.mTouchEventView.setDebugmode(z);
    try {
      this.mDisplayContent.registerPointerEventListener(this.mTouchEventView);
    } catch (IllegalStateException e) {
      Slog.e(StartingSurfaceController.TAG, "Exception in registering mTouchEventView :- " + e);
    }
    this.mTouchEventViewHash = this.mTouchEventView.hashCode();
    this.mIsCmfaStarted = true;
  }

  public void disableTouchListener() {
    TouchEventView touchEventView = this.mTouchEventView;
    if (touchEventView == null) {
      Slog.i(StartingSurfaceController.TAG, "TouchEventView is not added");
      return;
    }
    if (!this.mIsCmfaStarted) {
      Slog.i(StartingSurfaceController.TAG, "mIsCmfaStarted is false");
      return;
    }
    try {
      if (this.mTouchEventViewHash == touchEventView.hashCode()) {
        this.mDisplayContent.unregisterPointerEventListener(this.mTouchEventView);
      }
    } catch (IllegalStateException e) {
      Slog.e(StartingSurfaceController.TAG, "Exception in unregistering mTouchEventView :- " + e);
    }
    this.mIsCmfaStarted = false;
  }

  public WindowState getFocusedWindow() {
    return this.mFocusedWindow;
  }

  public void updateDisplayOverrideConfiguration() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Configuration configuration = new Configuration();
    this.mDisplayContent.computeScreenConfiguration(configuration);
    this.mDisplayContent.mDisplay.getMetrics(displayMetrics);
    this.mUiContext.getResources().updateConfiguration(configuration, displayMetrics);
    onConfigurationChanged();
    this.mSystemGestures.onConfigurationChanged();
  }

  public boolean isStatusBarVisibleLw() {
    WindowState windowState = this.mStatusBar;
    return windowState != null && windowState.isVisible();
  }

  public boolean isNavigationBarVisibleLw() {
    WindowState windowState = this.mNavigationBar;
    return (windowState != null && windowState.isVisible())
        || this.mExt.getTaskbarController().isTaskbarVisible();
  }

  public final boolean isNavOrAltBar(WindowState windowState) {
    if (this.mNavigationBar == windowState) {
      return true;
    }
    if (windowState == null || !windowState.hasInsetsSourceProvider()) {
      return false;
    }
    SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
    for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
      if (((InsetsSourceProvider) insetsSourceProviders.valueAt(size)).getSource().getType()
          == WindowInsets.Type.navigationBars()) {
        return true;
      }
    }
    return false;
  }

  public final void callCocktailBarSafely(final Consumer consumer) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda16
          @Override // java.lang.Runnable
          public final void run() {
            DisplayPolicy.this.lambda$callCocktailBarSafely$12(consumer);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$callCocktailBarSafely$12(Consumer consumer) {
    CocktailBarManagerInternal cocktailBarManagerInternal = getCocktailBarManagerInternal();
    if (cocktailBarManagerInternal != null) {
      consumer.accept(cocktailBarManagerInternal);
    }
  }

  public final CocktailBarManagerInternal getCocktailBarManagerInternal() {
    CocktailBarManagerInternal cocktailBarManagerInternal;
    synchronized (this.mServiceAcquireLock) {
      if (this.mCocktailBarInternal == null) {
        this.mCocktailBarInternal =
            (CocktailBarManagerInternal) LocalServices.getService(CocktailBarManagerInternal.class);
      }
      cocktailBarManagerInternal = this.mCocktailBarInternal;
    }
    return cocktailBarManagerInternal;
  }

  public void setControlTargetToNotificationShade() {
    if (this.mNotificationShade != null) {
      Slog.d(
          StartingSurfaceController.TAG,
          "set ControlTarget to NotificationShade=" + this.mNotificationShade);
      this.mDisplayContent.getInsetsPolicy().updateBarControlTarget(this.mNotificationShade);
    }
  }

  public boolean isDexStandAloneMode() {
    return this.mDisplayContent.isDexMode() && this.mDisplayContent.isDefaultDisplay;
  }

  public final boolean canReplaceSystemWindowForDexStandAlone(
      WindowManager.LayoutParams layoutParams) {
    int i = layoutParams.type;
    if (i == 2000) {
      return hasDexStandAloneStatusBar();
    }
    if (i == 2019) {
      return hasDexStandAloneNavigationBar();
    }
    if (i == 2040) {
      return hasDexStandAloneNotificationShade();
    }
    switch (i) {
      case 2621:
        WindowState windowState = this.mStatusBar;
        if (windowState == null || windowState.mAttrs.type != 2000) {
          break;
        }
        break;
      case 2622:
        if (this.mNotificationShade == null || this.mNotificationShade.mAttrs.type != 2040) {
          break;
        }
        break;
      case 2623:
        WindowState windowState2 = this.mNavigationBar;
        if (windowState2 == null || windowState2.mAttrs.type != 2019) {
          break;
        }
        break;
    }
    return false;
  }

  public final boolean hasDexStandAloneStatusBar() {
    WindowState windowState = this.mStatusBar;
    return windowState != null && windowState.mAttrs.type == 2621;
  }

  public final void restoreDefaultStatusBar() {
    WindowState windowState = this.mStatusBar;
    WindowState windowState2 = this.mDefaultStatusBar;
    if (windowState == windowState2) {
      return;
    }
    this.mStatusBar = null;
    if (windowState2 != null) {
      addWindowLw(windowState2, windowState2.mAttrs);
    }
    Slog.d(
        StartingSurfaceController.TAG,
        "restoreDefaultStatusBar: prev=" + windowState + ", now=" + this.mStatusBar);
  }

  public final boolean hasDexStandAloneNavigationBar() {
    WindowState windowState = this.mNavigationBar;
    return windowState != null && windowState.mAttrs.type == 2623;
  }

  public final void restoreDefaultNavigationBar() {
    WindowState windowState = this.mNavigationBar;
    WindowState windowState2 = this.mDefaultNavigationBar;
    if (windowState == windowState2) {
      return;
    }
    this.mNavigationBar = null;
    if (windowState2 != null) {
      addWindowLw(windowState2, windowState2.mAttrs);
    }
    Slog.d(
        StartingSurfaceController.TAG,
        "restoreDefaultNavigationBar: prev=" + windowState + ", now=" + this.mNavigationBar);
  }

  public final boolean hasDexStandAloneNotificationShade() {
    return this.mNotificationShade != null && this.mNotificationShade.mAttrs.type == 2622;
  }

  public final void restoreDefaultNotificationShade() {
    if (this.mNotificationShade == this.mDefaultNotificationShade) {
      return;
    }
    WindowState windowState = this.mNotificationShade;
    this.mNotificationShade = null;
    WindowState windowState2 = this.mDefaultNotificationShade;
    if (windowState2 != null) {
      addWindowLw(windowState2, windowState2.mAttrs);
    }
    Slog.d(
        StartingSurfaceController.TAG,
        "restoreDefaultNotificationShade: prev="
            + windowState
            + ", now="
            + this.mNotificationShade);
  }

  public void setFreeforTaskSurfaceOverlappingWithNavBar(boolean z) {
    if (z != this.mFreeformTaskSurfaceOverlappingWithNavBar) {
      this.mFreeformTaskSurfaceOverlappingWithNavBar = z;
      if (z != ((this.mLastAppearance & 128) != 0)) {
        updateSystemBarAttributes();
      }
    }
  }

  public void hideImmersiveModeConfirmation() {
    ImmersiveModeConfirmation immersiveModeConfirmation = this.mImmersiveModeConfirmation;
    if (immersiveModeConfirmation != null) {
      immersiveModeConfirmation.hideImmersiveModeConfirmation();
    }
  }

  public boolean isInImmersiveSplitMode(WindowState windowState) {
    return isInImmersiveSplitMode(windowState, false);
  }

  public boolean isInImmersiveSplitMode(WindowState windowState, boolean z) {
    if (windowState == null && (windowState = this.mTopFullscreenOpaqueWindowState) == null) {
      return false;
    }
    if (z) {
      return PolicyControl.shouldApplySplitImmersiveStatusBar(windowState);
    }
    return PolicyControl.shouldApplySplitImmersiveNavigation(windowState);
  }

  public final boolean supportTransientEdgeInSplitMode() {
    if (this.mEdgeEnabled) {
      DisplayContent displayContent = this.mDisplayContent;
      if (displayContent.isDefaultDisplay
          && MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
          && displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
        if (!this.mNavigationBarCanMove) {
          return true;
        }
        DisplayInfo displayInfo = this.mDisplayContent.getDisplayInfo();
        if (displayInfo.logicalWidth < displayInfo.logicalHeight) {
          return true;
        }
      }
    }
    return false;
  }

  public void updateEdgeSettings(boolean z) {
    this.mEdgeEnabled = z;
  }

  public final boolean shouldKeepSystemUiControllingWindow() {
    WindowState windowState;
    WindowState windowState2;
    ActivityRecord activityRecord;
    return MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
        && this.mFocusedWindow == null
        && (windowState = this.mTopFullscreenOpaqueWindowState) != null
        && windowState.isActivityTypeHome()
        && (windowState2 = this.mSystemUiControllingWindow) != null
        && windowState2.inSplitScreenWindowingMode()
        && (activityRecord = this.mDisplayContent.mFocusedApp) != null
        && activityRecord.inSplitScreenWindowingMode();
  }

  public boolean isDexForceImmersiveModeEnabled() {
    return this.mDisplayContent.isDexMode()
        && this.mDisplayContent.mAtmService.mDexController.isDexForceImmersiveModeEnabled();
  }

  public boolean isInDexForceImmersiveMode() {
    return this.mDisplayContent.isDexMode()
        && this.mDisplayContent.mAtmService.mDexController.isInDexForceImmersiveMode();
  }

  public void updatePopOverLayoutWindow(WindowState windowState) {
    Task task = windowState.getTask();
    if (task == null) {
      return;
    }
    DisplayContent displayContent = windowState.getDisplayContent();
    WindowState windowState2 =
        displayContent.getImeInputTarget() != null
            ? displayContent.getImeInputTarget().getWindowState()
            : null;
    boolean z =
        windowState2 != null
            && (!windowState.inFreeformWindowingMode()
                ? windowState != windowState2
                : task != windowState2.getTask());
    WindowState windowState3 = displayContent.mInputMethodWindow;
    if (z && windowState3 != null && windowState3.isVisibleNow()) {
      InsetsState rawInsetsState = displayContent.getInsetsStateController().getRawInsetsState();
      this.mTmpFrame.set(rawInsetsState.getDisplayFrame());
      Rect rect = this.mTmpFrame;
      rect.inset(
          rawInsetsState.calculateInsets(
              rect,
              WindowInsets.Type.systemBars()
                  | WindowInsets.Type.ime()
                  | WindowInsets.Type.displayCutout(),
              false));
      int i = windowState.getBounds().bottom - this.mTmpFrame.bottom;
      if (i > 0) {
        this.mWindowBounds.offset(
            0,
            -Math.min(
                i,
                displayContent.mPopOverController.getDistanceToTopForPopOver(
                    windowState, windowState.getBounds().top, this.mTmpFrame.top)));
        if (!windowState.isLayoutWithIme()) {
          windowState.setLayoutWithIme(true);
          windowState.forceReportingResized();
        }
        windowState.getWindowFrames().setContentChanged(true);
        return;
      }
      return;
    }
    if (windowState.isLayoutWithIme()) {
      windowState.setLayoutWithIme(false);
      windowState.forceReportingResized();
    }
  }

  public final boolean isCaptionImmersiveMode(WindowState windowState) {
    if (!CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE
        || windowState == null
        || windowState == getNotificationShade()
        || windowState.isActivityTypeDream()) {
      return false;
    }
    if (CoreRune.MT_NEW_DEX && windowState.isActivityTypeHome()) {
      return false;
    }
    WindowState statusControlWindow =
        windowState.isChildWindow() ? getInsetsPolicy().getStatusControlWindow(windowState) : null;
    if (statusControlWindow != null) {
      windowState = statusControlWindow;
    }
    if (windowState.mAttrs.insetsFlags.behavior == 2 && windowState.getWindowingMode() == 1) {
      return getInsetsPolicy().hasHiddenSources(WindowInsets.Type.statusBars())
          || getInsetsPolicy().isTransient(WindowInsets.Type.statusBars());
    }
    return false;
  }

  public void setTransientVisibilityChangeListener(WindowState windowState, boolean z) {
    if (windowState.getTask() == null || !windowState.getTask().isOrganized()) {
      return;
    }
    WindowManagerInternal windowManagerInternal =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    if (z) {
      this.mTransientWindowState = windowState;
      if (this.mTaskSystemBarsVisibilityListener == null) {
        this.mTaskSystemBarsVisibilityListener =
            new WindowManagerInternal
                .TaskSystemBarsListener() { // from class: com.android.server.wm.DisplayPolicy.6
              @Override // com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener
              public void onTransientSystemBarsVisibilityChanged(int i, boolean z2, boolean z3) {
                Task task;
                if (DisplayPolicy.this.mTransientWindowState == null
                    || (task = DisplayPolicy.this.mTransientWindowState.getTask()) == null
                    || task.mTaskId != i) {
                  return;
                }
                DisplayPolicy.this.mIsVisibleBySwipe = true;
                DisplayPolicy.this.mService.mAtmService.mTaskOrganizerController
                    .onNewDexImmersiveModeChanged(task, !z2);
              }
            };
      }
      if (windowManagerInternal != null) {
        windowManagerInternal.registerTaskSystemBarsListener(
            this.mTaskSystemBarsVisibilityListener);
        return;
      }
      return;
    }
    this.mTransientWindowState = null;
    WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener =
        this.mTaskSystemBarsVisibilityListener;
    if (taskSystemBarsListener != null) {
      if (windowManagerInternal != null) {
        windowManagerInternal.unregisterTaskSystemBarsListener(taskSystemBarsListener);
      }
      this.mTaskSystemBarsVisibilityListener = null;
    }
  }
}
