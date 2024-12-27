package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.ResourcesManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.DisplayShape;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.PrivacyIndicatorBounds;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.TwoFingerSwipeGestureDetector;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowLayout;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.accessibility.AccessibilityManager;
import android.window.ClientWindowFrames;

import com.android.internal.os.BackgroundThread;
import com.android.internal.policy.ForceShowNavBarSettingsObserver;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.util.function.TriFunction;
import com.android.internal.view.AppearanceRegion;
import com.android.internal.widget.PointerLocationView;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.display.DisplayPowerController;
import com.android.server.knox.zt.usertrust.TouchEventView;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.SystemGesturesPointerEventListener.FlingGestureDetector;
import com.android.wm.shell.Flags;

import com.samsung.android.cocktailbar.CocktailBarManagerInternal;
import com.samsung.android.cover.CoverState;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DisplayPolicy {
    public final AccessibilityManager mAccessibilityManager;
    public boolean mAllowLockscreenWhenOn;
    public final AnonymousClass2 mAppTransitionListener;
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
    public final DisplayPolicyExt mExt;
    public String mFocusedApp;
    public WindowState mFocusedWindow;
    public final ForceShowNavBarSettingsObserver mForceShowNavBarSettingsObserver;
    public boolean mForceShowNavigationBarEnabled;
    public int mForciblyShownTypes;
    public boolean mFreeformTaskSurfaceOverlappingWithNavBar;
    public final GestureNavigationSettingsObserver mGestureNavigationSettingsObserver;
    public final PolicyHandler mHandler;
    public volatile boolean mHasNavigationBar;
    public volatile boolean mHdmiPlugged;
    public boolean mImeInsetsConsumed;
    public boolean mImmersiveConfirmationWindowExists;
    public final ImmersiveModeConfirmation mImmersiveModeConfirmation;
    public boolean mIsFreeformWindowOverlappingWithNavBar;
    public boolean mIsImmersiveMode;
    public boolean mIsPipWindowOverlappingWithNavBar;
    public boolean mIsResizingByDivider;
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
    public final WindowManagerGlobalLock mLock;
    public WindowState mNavBarBackgroundWindowCandidate;
    public WindowState mNavBarColorWindowCandidate;
    public volatile boolean mNavigationBarAlwaysShowOnSideGesture;
    public volatile boolean mNavigationBarCanMove;
    public volatile WindowState mNotificationShade;
    public final long mPanicThresholdMs;
    public long mPanicTime;
    public long mPendingPanicGestureUptime;
    public volatile boolean mPersistentVrModeEnabled;
    public PointerLocationView mPointerLocationView;
    public final RefreshRatePolicy mRefreshRatePolicy;
    public boolean mRemoteInsetsControllerControlsSystemBars;
    public WindowState mRightGestureHost;
    public int mRightGestureInset;
    public volatile boolean mScreenOnEarly;
    public volatile boolean mScreenOnFully;
    public volatile DisplayPowerController.ScreenOnUnblocker mScreenOnListener;
    public final ScreenshotHelper mScreenshotHelper;
    public final WindowManagerService mService;
    public boolean mShouldAttachNavBarToAppDuringTransition;
    public boolean mShowingDream;
    public boolean mSkipTransferTouchToStatusBar;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public final SystemGesturesPointerEventListener mSystemGestures;
    public WindowState mSystemUiControllingWindow;
    public WindowState mTopFullscreenOpaqueWindowState;
    public WindowState mTopGestureHost;
    public boolean mTopIsFullscreen;
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
    public boolean mIsKnoxZtStarted = false;
    public TouchEventView mTouchEventView = null;
    public int mTouchEventViewHash = -1;
    public int mDexTaskbarHeight = 0;
    public WindowState mDefaultStatusBar = null;
    public WindowState mDefaultNotificationShade = null;
    public WindowState mDefaultNavigationBar = null;
    public final Rect mTmpFrame = new Rect();
    public final Rect mWindowBounds = new Rect();
    public final AnonymousClass4 mHiddenNavPanic =
            new Runnable() { // from class: com.android.server.wm.DisplayPolicy.4
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (DisplayPolicy.this.mLock) {
                        try {
                            if (((PhoneWindowManager) DisplayPolicy.this.mService.mPolicy)
                                    .isUserSetupComplete()) {
                                DisplayPolicy.this.mPendingPanicGestureUptime =
                                        SystemClock.uptimeMillis();
                                DisplayPolicy.this.updateSystemBarAttributes();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };

    /* renamed from: com.android.server.wm.DisplayPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final DisplayPolicy$1$$ExternalSyntheticLambda0 mOnSwipeFromBottom;
        public final DisplayPolicy$1$$ExternalSyntheticLambda0 mOnSwipeFromLeft;
        public final DisplayPolicy$1$$ExternalSyntheticLambda0 mOnSwipeFromRight;
        public final DisplayPolicy$1$$ExternalSyntheticLambda0 mOnSwipeFromTop;

        public AnonymousClass1() {
            final int i = 0;
            this.mOnSwipeFromLeft =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ DisplayPolicy.AnonymousClass1 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            DisplayPolicy.AnonymousClass1 anonymousClass1 = this.f$0;
                            switch (i2) {
                                case 0:
                                    anonymousClass1.onSwipeFromLeft();
                                    break;
                                case 1:
                                    anonymousClass1.onSwipeFromTop();
                                    break;
                                case 2:
                                    anonymousClass1.onSwipeFromRight();
                                    break;
                                default:
                                    anonymousClass1.onSwipeFromBottom();
                                    break;
                            }
                        }
                    };
            final int i2 = 1;
            this.mOnSwipeFromTop =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ DisplayPolicy.AnonymousClass1 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i2;
                            DisplayPolicy.AnonymousClass1 anonymousClass1 = this.f$0;
                            switch (i22) {
                                case 0:
                                    anonymousClass1.onSwipeFromLeft();
                                    break;
                                case 1:
                                    anonymousClass1.onSwipeFromTop();
                                    break;
                                case 2:
                                    anonymousClass1.onSwipeFromRight();
                                    break;
                                default:
                                    anonymousClass1.onSwipeFromBottom();
                                    break;
                            }
                        }
                    };
            final int i3 = 2;
            this.mOnSwipeFromRight =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ DisplayPolicy.AnonymousClass1 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i3;
                            DisplayPolicy.AnonymousClass1 anonymousClass1 = this.f$0;
                            switch (i22) {
                                case 0:
                                    anonymousClass1.onSwipeFromLeft();
                                    break;
                                case 1:
                                    anonymousClass1.onSwipeFromTop();
                                    break;
                                case 2:
                                    anonymousClass1.onSwipeFromRight();
                                    break;
                                default:
                                    anonymousClass1.onSwipeFromBottom();
                                    break;
                            }
                        }
                    };
            final int i4 = 3;
            this.mOnSwipeFromBottom =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda0
                        public final /* synthetic */ DisplayPolicy.AnonymousClass1 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i4;
                            DisplayPolicy.AnonymousClass1 anonymousClass1 = this.f$0;
                            switch (i22) {
                                case 0:
                                    anonymousClass1.onSwipeFromLeft();
                                    break;
                                case 1:
                                    anonymousClass1.onSwipeFromTop();
                                    break;
                                case 2:
                                    anonymousClass1.onSwipeFromRight();
                                    break;
                                default:
                                    anonymousClass1.onSwipeFromBottom();
                                    break;
                            }
                        }
                    };
        }

        public static Insets getControllableInsets(WindowState windowState) {
            InsetsSourceProvider controllableInsetProvider;
            if (windowState != null
                    && (controllableInsetProvider = windowState.getControllableInsetProvider())
                            != null) {
                return controllableInsetProvider.mSource.calculateInsets(
                        windowState.getBounds(), true);
            }
            return Insets.NONE;
        }

        public final void onMouseHoverAtBottom() {
            long j;
            int i;
            ActivityRecord activityRecord;
            DisplayPolicy displayPolicy = DisplayPolicy.this;
            displayPolicy.mHandler.removeCallbacks(this.mOnSwipeFromBottom);
            WindowState windowState = displayPolicy.mTopFullscreenOpaqueWindowState;
            if (windowState == null
                    || !windowState.isDexMode()
                    || (activityRecord = windowState.mActivityRecord) == null
                    || (i = activityRecord.mTransientBarShowingDelayMillis) < 0) {
                DisplayContent displayContent = displayPolicy.mDisplayContent;
                if (!displayContent.isDexMode()
                        || (i = displayContent.mAtmService.mDexController.mDexStarShowingDelayTime)
                                < 0) {
                    j = 500;
                    displayPolicy.mHandler.postDelayed(this.mOnSwipeFromBottom, j);
                }
            }
            j = i;
            displayPolicy.mHandler.postDelayed(this.mOnSwipeFromBottom, j);
        }

        public final void onSwipeFromBottom() {
            synchronized (DisplayPolicy.this.mLock) {
                DisplayPolicy displayPolicy = DisplayPolicy.this;
                WindowState windowState = displayPolicy.mBottomGestureHost;
                displayPolicy.requestTransientBars(
                        windowState, getControllableInsets(windowState).bottom > 0);
            }
        }

        public final void onSwipeFromLeft() {
            Region obtain = Region.obtain();
            synchronized (DisplayPolicy.this.mLock) {
                try {
                    DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(
                            obtain, null);
                    boolean z = getControllableInsets(DisplayPolicy.this.mLeftGestureHost).left > 0;
                    if (!z) {
                        if (DisplayPolicy.this.mNavigationBarAlwaysShowOnSideGesture) {
                            SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                                    DisplayPolicy.this.mSystemGestures;
                            if (!obtain.contains(
                                    (int) systemGesturesPointerEventListener.mDownX[0],
                                    (int) systemGesturesPointerEventListener.mDownY[0])) {}
                        }
                        requestTransientBarsForSideSwipe(DisplayPolicy.this.mLeftGestureHost, 1);
                    }
                    DisplayPolicy displayPolicy = DisplayPolicy.this;
                    displayPolicy.requestTransientBars(displayPolicy.mLeftGestureHost, z);
                } catch (Throwable th) {
                    throw th;
                }
            }
            obtain.recycle();
        }

        public final void onSwipeFromRight() {
            Region obtain = Region.obtain();
            synchronized (DisplayPolicy.this.mLock) {
                try {
                    DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(
                            obtain, null);
                    boolean z =
                            getControllableInsets(DisplayPolicy.this.mRightGestureHost).right > 0;
                    if (!z) {
                        if (DisplayPolicy.this.mNavigationBarAlwaysShowOnSideGesture) {
                            SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                                    DisplayPolicy.this.mSystemGestures;
                            if (!obtain.contains(
                                    (int) systemGesturesPointerEventListener.mDownX[0],
                                    (int) systemGesturesPointerEventListener.mDownY[0])) {}
                        }
                        requestTransientBarsForSideSwipe(DisplayPolicy.this.mRightGestureHost, 2);
                    }
                    DisplayPolicy displayPolicy = DisplayPolicy.this;
                    displayPolicy.requestTransientBars(displayPolicy.mRightGestureHost, z);
                } catch (Throwable th) {
                    throw th;
                }
            }
            obtain.recycle();
        }

        public final void onSwipeFromTop() {
            synchronized (DisplayPolicy.this.mLock) {
                try {
                    MultiWindowPointerEventListener multiWindowPointerEventListener =
                            DisplayPolicy.this.mDisplayContent.mMultiWindowPointerEventListener;
                    MultiWindowEdgeDetector multiWindowEdgeDetector =
                            multiWindowPointerEventListener != null
                                    ? multiWindowPointerEventListener.mMultiWindowEdgeDetector
                                    : null;
                    if (multiWindowEdgeDetector != null
                            && multiWindowPointerEventListener.isAllowCornerGestureState()
                            && multiWindowEdgeDetector.isEdge()) {
                        return;
                    }
                    DisplayPolicy displayPolicy = DisplayPolicy.this;
                    WindowState windowState = displayPolicy.mTopGestureHost;
                    displayPolicy.requestTransientBars(
                            windowState, getControllableInsets(windowState).top > 0);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0029, code lost:

           if (r0.logicalWidth < r0.logicalHeight) goto L16;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void requestTransientBarsForSideSwipe(
                com.android.server.wm.WindowState r5, int r6) {
            /*
                r4 = this;
                com.android.server.wm.DisplayPolicy r0 = com.android.server.wm.DisplayPolicy.this
                boolean r1 = r0.mEdgeEnabled
                r2 = 1
                if (r1 == 0) goto L42
                com.android.server.wm.DisplayContent r1 = r0.mDisplayContent
                boolean r3 = r1.isDefaultDisplay
                if (r3 == 0) goto L42
                boolean r3 = com.samsung.android.multiwindow.MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
                if (r3 == 0) goto L42
                com.android.server.wm.TaskDisplayArea r1 = r1.getDefaultTaskDisplayArea()
                boolean r1 = r1.isSplitScreenModeActivated()
                if (r1 != 0) goto L1c
                goto L42
            L1c:
                boolean r1 = r0.mNavigationBarCanMove
                if (r1 != 0) goto L21
                goto L2b
            L21:
                com.android.server.wm.DisplayContent r0 = r0.mDisplayContent
                android.view.DisplayInfo r0 = r0.mDisplayInfo
                int r1 = r0.logicalWidth
                int r0 = r0.logicalHeight
                if (r1 >= r0) goto L42
            L2b:
                com.android.server.wm.DisplayPolicy r5 = com.android.server.wm.DisplayPolicy.this
                com.android.server.wm.WindowState r6 = r5.mNavigationBar
                if (r6 != 0) goto L35
                r5.mSkipTransferTouchToStatusBar = r2
                com.android.server.wm.WindowState r6 = r5.mStatusBar
            L35:
                r5.requestTransientBars(r6, r2)
                com.android.server.wm.DisplayPolicy r4 = com.android.server.wm.DisplayPolicy.this
                boolean r5 = r4.mSkipTransferTouchToStatusBar
                if (r5 == 0) goto L51
                r5 = 0
                r4.mSkipTransferTouchToStatusBar = r5
                goto L51
            L42:
                com.android.server.wm.DisplayPolicy r4 = com.android.server.wm.DisplayPolicy.this
                com.android.server.wm.WindowState r0 = r4.mNavigationBar
                if (r0 == 0) goto L51
                if (r0 != r5) goto L51
                int r0 = r4.mNavigationBarPosition
                if (r0 != r6) goto L51
                r4.requestTransientBars(r5, r2)
            L51:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.DisplayPolicy.AnonymousClass1.requestTransientBarsForSideSwipe(com.android.server.wm.WindowState,"
                        + " int):void");
        }
    }

    /* renamed from: com.android.server.wm.DisplayPolicy$2, reason: invalid class name */
    public final class AnonymousClass2 extends WindowManagerInternal.AppTransitionListener {
        public final DisplayPolicy$2$$ExternalSyntheticLambda1 mAppTransitionCancelled;
        public final DisplayPolicy$2$$ExternalSyntheticLambda1 mAppTransitionFinished;
        public final DisplayPolicy$2$$ExternalSyntheticLambda1 mAppTransitionPending;

        public AnonymousClass2(final int i) {
            final int i2 = 0;
            this.mAppTransitionPending =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda1
                        public final /* synthetic */ DisplayPolicy.AnonymousClass2 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            IStatusBar iStatusBar;
                            IStatusBar iStatusBar2;
                            switch (i2) {
                                case 0:
                                    DisplayPolicy.AnonymousClass2 anonymousClass2 = this.f$0;
                                    int i3 = i;
                                    StatusBarManagerInternal statusBarManagerInternal =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal != null
                                            && (iStatusBar = StatusBarManagerService.this.mBar)
                                                    != null) {
                                        try {
                                            iStatusBar.appTransitionPending(i3);
                                            break;
                                        } catch (RemoteException unused) {
                                            return;
                                        }
                                    }
                                    break;
                                case 1:
                                    DisplayPolicy.AnonymousClass2 anonymousClass22 = this.f$0;
                                    int i4 = i;
                                    StatusBarManagerInternal statusBarManagerInternal2 =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal2 != null
                                            && (iStatusBar2 = StatusBarManagerService.this.mBar)
                                                    != null) {
                                        try {
                                            iStatusBar2.appTransitionCancelled(i4);
                                            break;
                                        } catch (RemoteException unused2) {
                                            return;
                                        }
                                    }
                                    break;
                                default:
                                    DisplayPolicy.AnonymousClass2 anonymousClass23 = this.f$0;
                                    int i5 = i;
                                    StatusBarManagerInternal statusBarManagerInternal3 =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal3 != null) {
                                        StatusBarManagerService.AnonymousClass2 anonymousClass24 =
                                                (StatusBarManagerService.AnonymousClass2)
                                                        statusBarManagerInternal3;
                                        StatusBarManagerService.this.enforceStatusBarService();
                                        IStatusBar iStatusBar3 = StatusBarManagerService.this.mBar;
                                        if (iStatusBar3 != null) {
                                            try {
                                                iStatusBar3.appTransitionFinished(i5);
                                                break;
                                            } catch (RemoteException unused3) {
                                                return;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                    };
            final int i3 = 1;
            this.mAppTransitionCancelled =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda1
                        public final /* synthetic */ DisplayPolicy.AnonymousClass2 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            IStatusBar iStatusBar;
                            IStatusBar iStatusBar2;
                            switch (i3) {
                                case 0:
                                    DisplayPolicy.AnonymousClass2 anonymousClass2 = this.f$0;
                                    int i32 = i;
                                    StatusBarManagerInternal statusBarManagerInternal =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal != null
                                            && (iStatusBar = StatusBarManagerService.this.mBar)
                                                    != null) {
                                        try {
                                            iStatusBar.appTransitionPending(i32);
                                            break;
                                        } catch (RemoteException unused) {
                                            return;
                                        }
                                    }
                                    break;
                                case 1:
                                    DisplayPolicy.AnonymousClass2 anonymousClass22 = this.f$0;
                                    int i4 = i;
                                    StatusBarManagerInternal statusBarManagerInternal2 =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal2 != null
                                            && (iStatusBar2 = StatusBarManagerService.this.mBar)
                                                    != null) {
                                        try {
                                            iStatusBar2.appTransitionCancelled(i4);
                                            break;
                                        } catch (RemoteException unused2) {
                                            return;
                                        }
                                    }
                                    break;
                                default:
                                    DisplayPolicy.AnonymousClass2 anonymousClass23 = this.f$0;
                                    int i5 = i;
                                    StatusBarManagerInternal statusBarManagerInternal3 =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal3 != null) {
                                        StatusBarManagerService.AnonymousClass2 anonymousClass24 =
                                                (StatusBarManagerService.AnonymousClass2)
                                                        statusBarManagerInternal3;
                                        StatusBarManagerService.this.enforceStatusBarService();
                                        IStatusBar iStatusBar3 = StatusBarManagerService.this.mBar;
                                        if (iStatusBar3 != null) {
                                            try {
                                                iStatusBar3.appTransitionFinished(i5);
                                                break;
                                            } catch (RemoteException unused3) {
                                                return;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                    };
            final int i4 = 2;
            this.mAppTransitionFinished =
                    new Runnable(
                            this) { // from class:
                                    // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda1
                        public final /* synthetic */ DisplayPolicy.AnonymousClass2 f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            IStatusBar iStatusBar;
                            IStatusBar iStatusBar2;
                            switch (i4) {
                                case 0:
                                    DisplayPolicy.AnonymousClass2 anonymousClass2 = this.f$0;
                                    int i32 = i;
                                    StatusBarManagerInternal statusBarManagerInternal =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal != null
                                            && (iStatusBar = StatusBarManagerService.this.mBar)
                                                    != null) {
                                        try {
                                            iStatusBar.appTransitionPending(i32);
                                            break;
                                        } catch (RemoteException unused) {
                                            return;
                                        }
                                    }
                                    break;
                                case 1:
                                    DisplayPolicy.AnonymousClass2 anonymousClass22 = this.f$0;
                                    int i42 = i;
                                    StatusBarManagerInternal statusBarManagerInternal2 =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal2 != null
                                            && (iStatusBar2 = StatusBarManagerService.this.mBar)
                                                    != null) {
                                        try {
                                            iStatusBar2.appTransitionCancelled(i42);
                                            break;
                                        } catch (RemoteException unused2) {
                                            return;
                                        }
                                    }
                                    break;
                                default:
                                    DisplayPolicy.AnonymousClass2 anonymousClass23 = this.f$0;
                                    int i5 = i;
                                    StatusBarManagerInternal statusBarManagerInternal3 =
                                            DisplayPolicy.this.getStatusBarManagerInternal();
                                    if (statusBarManagerInternal3 != null) {
                                        StatusBarManagerService.AnonymousClass2 anonymousClass24 =
                                                (StatusBarManagerService.AnonymousClass2)
                                                        statusBarManagerInternal3;
                                        StatusBarManagerService.this.enforceStatusBarService();
                                        IStatusBar iStatusBar3 = StatusBarManagerService.this.mBar;
                                        if (iStatusBar3 != null) {
                                            try {
                                                iStatusBar3.appTransitionFinished(i5);
                                                break;
                                            } catch (RemoteException unused3) {
                                                return;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                    };
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionCancelledLocked(boolean z) {
            DisplayPolicy.this.mHandler.post(this.mAppTransitionCancelled);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionFinishedLocked(IBinder iBinder) {
            DisplayPolicy.this.mHandler.post(this.mAppTransitionFinished);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionPendingLocked() {
            DisplayPolicy.this.mHandler.post(this.mAppTransitionPending);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionStartingLocked(final long j) {
            DisplayPolicy.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda0
                        public final /* synthetic */ long f$2 = 120;

                        @Override // java.lang.Runnable
                        public final void run() {
                            DisplayPolicy.AnonymousClass2 anonymousClass2 =
                                    DisplayPolicy.AnonymousClass2.this;
                            long j2 = j;
                            long j3 = this.f$2;
                            StatusBarManagerInternal statusBarManagerInternal =
                                    DisplayPolicy.this.getStatusBarManagerInternal();
                            if (statusBarManagerInternal != null) {
                                int displayId = DisplayPolicy.this.mContext.getDisplayId();
                                IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                                if (iStatusBar != null) {
                                    try {
                                        iStatusBar.appTransitionStarting(displayId, j2, j3);
                                    } catch (RemoteException unused) {
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public final class DecorInsets {
        public final DisplayContent mDisplayContent;
        public final Info[] mInfoForRotation;
        public final Info mTmpInfo = new Info();

        public final class Cache {
            public boolean mActive;
            public final DecorInsets mDecorInsets;
            public int mPreserveId;

            public Cache(DisplayContent displayContent) {
                this.mDecorInsets = new DecorInsets(displayContent);
            }
        }

        public final class Info {
            public final Rect mNonDecorInsets = new Rect();
            public final Rect mConfigInsets = new Rect();
            public final Rect mOverrideConfigInsets = new Rect();
            public final Rect mOverrideNonDecorInsets = new Rect();
            public final Rect mNonDecorFrame = new Rect();
            public final Rect mConfigFrame = new Rect();
            public final Rect mOverrideConfigFrame = new Rect();
            public final Rect mOverrideNonDecorFrame = new Rect();
            public boolean mNeedUpdate = true;
            public final Rect mCutoutInsets = new Rect();
            public final Rect mExceptNavConfigInsets = new Rect();

            public final void set(Info info) {
                this.mNonDecorInsets.set(info.mNonDecorInsets);
                this.mConfigInsets.set(info.mConfigInsets);
                this.mOverrideConfigInsets.set(info.mOverrideConfigInsets);
                this.mOverrideNonDecorInsets.set(info.mOverrideNonDecorInsets);
                this.mNonDecorFrame.set(info.mNonDecorFrame);
                this.mConfigFrame.set(info.mConfigFrame);
                this.mOverrideConfigFrame.set(info.mOverrideConfigFrame);
                this.mOverrideNonDecorFrame.set(info.mOverrideNonDecorFrame);
                this.mNeedUpdate = false;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder(32);
                return "{nonDecorInsets="
                        + this.mNonDecorInsets.toShortString(sb)
                        + ", overrideNonDecorInsets="
                        + this.mOverrideNonDecorInsets.toShortString(sb)
                        + ", configInsets="
                        + this.mConfigInsets.toShortString(sb)
                        + ", overrideConfigInsets="
                        + this.mOverrideConfigInsets.toShortString(sb)
                        + ", nonDecorFrame="
                        + this.mNonDecorFrame.toShortString(sb)
                        + ", overrideNonDecorFrame="
                        + this.mOverrideNonDecorFrame.toShortString(sb)
                        + ", configFrame="
                        + this.mConfigFrame.toShortString(sb)
                        + ", overrideConfigFrame="
                        + this.mOverrideConfigFrame.toShortString(sb)
                        + '}';
            }

            public final InsetsState update(int i, int i2, int i3, DisplayContent displayContent) {
                Insets calculateInsets;
                Insets calculateInsets2;
                DisplayFrames displayFrames = new DisplayFrames();
                displayFrames.update(
                        i,
                        i2,
                        i3,
                        displayContent.calculateDisplayCutoutForRotation(i, false),
                        displayContent.calculateRoundedCornersForRotation(i),
                        (PrivacyIndicatorBounds)
                                displayContent.mPrivacyIndicatorBoundsCache.getOrCompute(
                                        i, displayContent.mCurrentPrivacyIndicatorBounds),
                        (DisplayShape)
                                displayContent.mDisplayShapeCache.getOrCompute(
                                        i, displayContent.mInitialDisplayShape));
                displayContent.mDisplayPolicy.simulateLayoutDisplay(displayFrames);
                InsetsState insetsState = displayFrames.mInsetsState;
                Rect displayFrame = insetsState.getDisplayFrame();
                if (displayContent.isDexMode()) {
                    int displayCutout =
                            CoreRune.MD_DEX_NOT_SUPPORT_CUTOUT
                                    ? WindowInsets.Type.displayCutout()
                                    : 0;
                    if (displayContent.mAtmService.mDexController.mIsDexForceImmersiveModeEnabled) {
                        displayCutout |= WindowInsets.Type.navigationBars();
                    }
                    int i4 = displayContent.mWmService.mDecorTypes;
                    int i5 = ~displayCutout;
                    calculateInsets = insetsState.calculateInsets(displayFrame, i4 & i5, true);
                    WindowManagerService windowManagerService = displayContent.mWmService;
                    int i6 = windowManagerService.mConfigTypes;
                    if (i6 != windowManagerService.mDecorTypes) {
                        calculateInsets2 = insetsState.calculateInsets(displayFrame, i5 & i6, true);
                    }
                    calculateInsets2 = calculateInsets;
                } else {
                    calculateInsets =
                            insetsState.calculateInsets(
                                    displayFrame, displayContent.mWmService.mDecorTypes, true);
                    WindowManagerService windowManagerService2 = displayContent.mWmService;
                    int i7 = windowManagerService2.mConfigTypes;
                    if (i7 != windowManagerService2.mDecorTypes) {
                        calculateInsets2 = insetsState.calculateInsets(displayFrame, i7, true);
                    }
                    calculateInsets2 = calculateInsets;
                }
                WindowManagerService windowManagerService3 = displayContent.mWmService;
                int i8 = windowManagerService3.mConfigTypes;
                int i9 = windowManagerService3.mOverrideConfigTypes;
                Insets calculateInsets3 =
                        i8 == i9
                                ? calculateInsets2
                                : insetsState.calculateInsets(displayFrame, i9, true);
                WindowManagerService windowManagerService4 = displayContent.mWmService;
                int i10 = windowManagerService4.mDecorTypes;
                int i11 = windowManagerService4.mOverrideDecorTypes;
                Insets calculateInsets4 =
                        i10 == i11
                                ? calculateInsets
                                : insetsState.calculateInsets(displayFrame, i11, true);
                this.mNonDecorInsets.set(
                        calculateInsets.left,
                        calculateInsets.top,
                        calculateInsets.right,
                        calculateInsets.bottom);
                this.mConfigInsets.set(
                        calculateInsets2.left,
                        calculateInsets2.top,
                        calculateInsets2.right,
                        calculateInsets2.bottom);
                this.mOverrideConfigInsets.set(
                        calculateInsets3.left,
                        calculateInsets3.top,
                        calculateInsets3.right,
                        calculateInsets3.bottom);
                this.mOverrideNonDecorInsets.set(
                        calculateInsets4.left,
                        calculateInsets4.top,
                        calculateInsets4.right,
                        calculateInsets4.bottom);
                this.mNonDecorFrame.set(displayFrame);
                this.mNonDecorFrame.inset(this.mNonDecorInsets);
                this.mConfigFrame.set(displayFrame);
                this.mConfigFrame.inset(this.mConfigInsets);
                this.mOverrideConfigFrame.set(displayFrame);
                this.mOverrideConfigFrame.inset(this.mOverrideConfigInsets);
                this.mOverrideNonDecorFrame.set(displayFrame);
                this.mOverrideNonDecorFrame.inset(this.mOverrideNonDecorInsets);
                this.mNeedUpdate = false;
                Insets calculateInsets5 =
                        insetsState.calculateInsets(
                                displayFrame, WindowInsets.Type.statusBars(), true);
                Insets calculateInsets6 =
                        insetsState.calculateInsets(
                                displayFrame, WindowInsets.Type.displayCutout(), true);
                this.mCutoutInsets.set(
                        calculateInsets6.left,
                        calculateInsets6.top,
                        calculateInsets6.right,
                        calculateInsets6.bottom);
                this.mExceptNavConfigInsets.set(
                        Math.max(calculateInsets5.left, calculateInsets6.left),
                        Math.max(calculateInsets5.top, calculateInsets6.top),
                        Math.max(calculateInsets5.right, calculateInsets6.right),
                        Math.max(calculateInsets5.bottom, calculateInsets6.bottom));
                return insetsState;
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

        public final void dump(String str, PrintWriter printWriter) {
            int i = 0;
            while (true) {
                Info[] infoArr = this.mInfoForRotation;
                if (i >= infoArr.length) {
                    return;
                }
                Info info = infoArr[i];
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                m.append(Surface.rotationToString(i));
                m.append("=");
                m.append(info);
                printWriter.println(m.toString());
                i++;
            }
        }

        public final void invalidate() {
            for (Info info : this.mInfoForRotation) {
                info.mNeedUpdate = true;
            }
        }

        public final void setTo(DecorInsets decorInsets) {
            Info[] infoArr = this.mInfoForRotation;
            for (int length = infoArr.length - 1; length >= 0; length--) {
                infoArr[length].set(decorInsets.mInfoForRotation[length]);
            }
        }
    }

    public final class PolicyHandler extends Handler {
        public PolicyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            DisplayPolicy displayPolicy = DisplayPolicy.this;
            if (i == 4) {
                if (displayPolicy.mPointerLocationView != null) {
                    return;
                }
                PointerLocationView pointerLocationView =
                        new PointerLocationView(displayPolicy.mContext);
                displayPolicy.mPointerLocationView = pointerLocationView;
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
                StringBuilder sb = new StringBuilder("PointerLocation - display ");
                DisplayContent displayContent = displayPolicy.mDisplayContent;
                sb.append(displayContent.mDisplayId);
                layoutParams.setTitle(sb.toString());
                layoutParams.inputFeatures |= 1;
                ((WindowManager) displayPolicy.mContext.getSystemService(WindowManager.class))
                        .addView(displayPolicy.mPointerLocationView, layoutParams);
                displayContent.registerPointerEventListener(displayPolicy.mPointerLocationView);
                return;
            }
            if (i == 5) {
                WindowManagerPolicyConstants.PointerEventListener pointerEventListener =
                        displayPolicy.mPointerLocationView;
                if (pointerEventListener == null) {
                    return;
                }
                DisplayContent displayContent2 = displayPolicy.mDisplayContent;
                if (!displayContent2.mRemoved) {
                    displayContent2.unregisterPointerEventListener(pointerEventListener);
                }
                ((WindowManager) displayPolicy.mContext.getSystemService(WindowManager.class))
                        .removeView(displayPolicy.mPointerLocationView);
                displayPolicy.mPointerLocationView = null;
                return;
            }
            if (i == 107) {
                int i2 = message.arg1;
                if (i2 == 101 || i2 == 102) {
                    if (displayPolicy.mIsKnoxZtStarted) {
                        Slog.i("WindowManager", "TouchEventView is already added");
                        return;
                    }
                    if (displayPolicy.mTouchEventView == null) {
                        displayPolicy.mTouchEventView =
                                new TouchEventView(
                                        displayPolicy.mContext,
                                        displayPolicy.mExt.mAuthFactorTouchManager);
                    }
                    Slog.i("WindowManager", "enableTouchListener debugmode:false");
                    displayPolicy.mTouchEventView.setDebugmode(false);
                    try {
                        displayPolicy.mDisplayContent.registerPointerEventListener(
                                displayPolicy.mTouchEventView);
                    } catch (IllegalStateException e) {
                        Slog.e("WindowManager", "Exception in registering mTouchEventView :- " + e);
                    }
                    displayPolicy.mTouchEventViewHash = displayPolicy.mTouchEventView.hashCode();
                    displayPolicy.mIsKnoxZtStarted = true;
                    return;
                }
                return;
            }
            if (i != 108) {
                return;
            }
            TouchEventView touchEventView = displayPolicy.mTouchEventView;
            if (touchEventView == null) {
                Slog.i("WindowManager", "TouchEventView is not added");
            } else if (displayPolicy.mIsKnoxZtStarted) {
                try {
                    if (displayPolicy.mTouchEventViewHash == touchEventView.hashCode()) {
                        displayPolicy.mDisplayContent.unregisterPointerEventListener(
                                displayPolicy.mTouchEventView);
                    }
                } catch (IllegalStateException e2) {
                    Slog.e("WindowManager", "Exception in unregistering mTouchEventView :- " + e2);
                }
                displayPolicy.mIsKnoxZtStarted = false;
            } else {
                Slog.i("WindowManager", "mIsKnoxZtStarted is false");
            }
            int i3 = message.arg1;
            if (i3 == 101) {
                displayPolicy.startEnableTouchEvent(true);
            } else if (i3 == 102) {
                displayPolicy.startEnableTouchEvent(false);
            }
        }
    }

    public DisplayPolicy(WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        Context createDisplayContext =
                displayContent.isDefaultDisplay
                        ? windowManagerService.mContext
                        : windowManagerService.mContext.createDisplayContext(
                                displayContent.mDisplay);
        this.mContext = createDisplayContext;
        this.mUiContext =
                displayContent.isDefaultDisplay
                        ? windowManagerService.mAtmService.mUiContext
                        : windowManagerService.mAtmService.mSystemThread.getSystemUiContext(
                                displayContent.mDisplayId);
        this.mDisplayContent = displayContent;
        this.mDecorInsets = new DecorInsets(displayContent);
        this.mLock = windowManagerService.mGlobalLock;
        this.mExt = new DisplayPolicyExt(createDisplayContext, windowManagerService, this);
        int i = displayContent.mDisplayId;
        Resources resources = createDisplayContext.getResources();
        this.mCarDockEnablesAccelerometer =
                resources.getBoolean(R.bool.config_carrier_wfc_ims_available);
        this.mDeskDockEnablesAccelerometer =
                resources.getBoolean(R.bool.config_device_wfc_ims_available);
        this.mCanSystemBarsBeShownByUser =
                !resources.getBoolean(R.bool.config_remoteInsetsControllerControlsSystemBars)
                        || resources.getBoolean(R.bool.config_setColorTransformAccelerated);
        this.mPanicThresholdMs = resources.getInteger(R.integer.config_maxShortcutTargetsPerApp);
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
        boolean z = ViewRootImpl.CLIENT_TRANSIENT;
        if (!z) {
            SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                    new SystemGesturesPointerEventListener(
                            this.mUiContext, policyHandler, new AnonymousClass1());
            this.mSystemGestures = systemGesturesPointerEventListener;
            displayContent.registerPointerEventListener(
                    new TwoFingerSwipeGestureDetector(
                            this.mUiContext,
                            new Function() { // from class:
                                // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda1
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    final DisplayPolicy displayPolicy = DisplayPolicy.this;
                                    final TwoFingerSwipeGestureDetector
                                            twoFingerSwipeGestureDetector =
                                                    (TwoFingerSwipeGestureDetector) obj;
                                    displayPolicy.getClass();
                                    return new TwoFingerSwipeGestureDetector
                                            .GestureListener() { // from class:
                                        // com.android.server.wm.DisplayPolicy.3
                                        /* JADX WARN: Code restructure failed: missing block: B:29:0x005c, code lost:

                                           if (r10 == 3) goto L25;
                                        */
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        public final void onCommitted(int r10) {
                                            /*
                                                r9 = this;
                                                com.android.server.wm.DisplayPolicy r0 = com.android.server.wm.DisplayPolicy.this
                                                com.android.server.wm.WindowManagerGlobalLock r0 = r0.mLock
                                                monitor-enter(r0)
                                                android.graphics.Region r1 = android.graphics.Region.obtain()     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.DisplayPolicy r2 = com.android.server.wm.DisplayPolicy.this     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.DisplayContent r2 = r2.mDisplayContent     // Catch: java.lang.Throwable -> L6e
                                                r3 = 0
                                                r2.calculateSystemGestureExclusion(r1, r3)     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.DisplayPolicy r2 = com.android.server.wm.DisplayPolicy.this     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.SystemGesturesPointerEventListener r2 = r2.mSystemGestures     // Catch: java.lang.Throwable -> L6e
                                                float[] r3 = r2.mDownX     // Catch: java.lang.Throwable -> L6e
                                                r4 = 0
                                                r3 = r3[r4]     // Catch: java.lang.Throwable -> L6e
                                                int r3 = (int) r3     // Catch: java.lang.Throwable -> L6e
                                                float[] r2 = r2.mDownY     // Catch: java.lang.Throwable -> L6e
                                                r2 = r2[r4]     // Catch: java.lang.Throwable -> L6e
                                                int r2 = (int) r2     // Catch: java.lang.Throwable -> L6e
                                                boolean r1 = r1.contains(r3, r2)     // Catch: java.lang.Throwable -> L6e
                                                r2 = 1
                                                r1 = r1 ^ r2
                                                com.android.server.wm.DisplayPolicy r3 = com.android.server.wm.DisplayPolicy.this     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.DisplayContent r5 = r3.mDisplayContent     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.TaskDisplayArea r5 = r5.getDefaultTaskDisplayArea()     // Catch: java.lang.Throwable -> L6e
                                                if (r5 == 0) goto L70
                                                boolean r6 = r5.isSplitScreenModeActivated()     // Catch: java.lang.Throwable -> L6e
                                                if (r6 == 0) goto L37
                                                goto L70
                                            L37:
                                                boolean r6 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_FULL_TO_SPLIT_BY_GESTURE     // Catch: java.lang.Throwable -> L6e
                                                r7 = 3
                                                r8 = 4
                                                if (r6 == 0) goto L4c
                                                android.content.Context r3 = r3.mContext     // Catch: java.lang.Throwable -> L6e
                                                boolean r3 = com.samsung.android.multiwindow.MultiWindowUtils.isInSubDisplay(r3)     // Catch: java.lang.Throwable -> L6e
                                                if (r3 != 0) goto L4c
                                                if (r10 == r2) goto L5e
                                                if (r10 == r7) goto L5e
                                                if (r10 != r8) goto L70
                                                goto L5e
                                            L4c:
                                                android.content.res.Configuration r3 = r5.getConfiguration()     // Catch: java.lang.Throwable -> L6e
                                                int r3 = r3.orientation     // Catch: java.lang.Throwable -> L6e
                                                if (r3 != r2) goto L57
                                                if (r10 != r8) goto L70
                                                goto L5e
                                            L57:
                                                r5 = 2
                                                if (r3 != r5) goto L70
                                                if (r10 == r2) goto L5e
                                                if (r10 != r7) goto L70
                                            L5e:
                                                if (r1 == 0) goto L70
                                                if (r10 == r2) goto L66
                                                if (r10 == r7) goto L66
                                                if (r10 != r8) goto L70
                                            L66:
                                                com.android.server.wm.DisplayPolicy r9 = com.android.server.wm.DisplayPolicy.this     // Catch: java.lang.Throwable -> L6e
                                                com.android.server.wm.WindowState r10 = r9.mNavigationBar     // Catch: java.lang.Throwable -> L6e
                                                r9.requestTransientBars(r10, r4)     // Catch: java.lang.Throwable -> L6e
                                                goto L70
                                            L6e:
                                                r9 = move-exception
                                                goto L72
                                            L70:
                                                monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
                                                return
                                            L72:
                                                monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
                                                throw r9
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.android.server.wm.DisplayPolicy.AnonymousClass3.onCommitted(int):void");
                                        }

                                        public final void onDetecting() {
                                            synchronized (DisplayPolicy.this.mLock) {
                                                try {
                                                    DisplayContent displayContent2 =
                                                            DisplayPolicy.this.mDisplayContent;
                                                    if (displayContent2
                                                            .mAtmService
                                                            .mMultiTaskingController
                                                            .mIsFullToSplitEnabled) {
                                                        twoFingerSwipeGestureDetector.init(
                                                                displayContent2.getBounds(),
                                                                DisplayPolicy.this
                                                                                .mDisplayContent
                                                                                .mBaseDisplayDensity
                                                                        / 160,
                                                                13);
                                                    } else {
                                                        twoFingerSwipeGestureDetector.cancel();
                                                    }
                                                } catch (Throwable th) {
                                                    throw th;
                                                }
                                            }
                                        }
                                    };
                                }
                            },
                            "DP"));
            systemGesturesPointerEventListener.mDisplayContent = displayContent;
            displayContent.registerPointerEventListener(systemGesturesPointerEventListener);
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(i);
        this.mAppTransitionListener = anonymousClass2;
        displayContent.mAppTransition.registerListenerLocked(anonymousClass2);
        displayContent.mTransitionController.registerLegacyListener(anonymousClass2);
        if (z || ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            this.mImmersiveModeConfirmation = null;
        } else {
            this.mImmersiveModeConfirmation =
                    new ImmersiveModeConfirmation(
                            createDisplayContext, looper, windowManagerService.mVrModeEnabled);
        }
        this.mScreenshotHelper =
                displayContent.isDefaultDisplay ? new ScreenshotHelper(createDisplayContext) : null;
        if (displayContent.isDefaultDisplay) {
            this.mHasNavigationBar =
                    createDisplayContext
                            .getResources()
                            .getBoolean(R.bool.config_sms_decode_gsm_8bit_data);
        } else {
            this.mHasNavigationBar = displayContent.supportsSystemDecorations();
        }
        this.mRefreshRatePolicy =
                new RefreshRatePolicy(
                        windowManagerService,
                        displayContent.mDisplayInfo,
                        windowManagerService.mHighRefreshRateDenylist);
        GestureNavigationSettingsObserver gestureNavigationSettingsObserver =
                new GestureNavigationSettingsObserver(
                        policyHandler,
                        BackgroundThread.getHandler(),
                        createDisplayContext,
                        new DisplayPolicy$$ExternalSyntheticLambda2(this, 0));
        this.mGestureNavigationSettingsObserver = gestureNavigationSettingsObserver;
        policyHandler.post(
                new DisplayPolicy$$ExternalSyntheticLambda3(gestureNavigationSettingsObserver, 0));
        ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver =
                new ForceShowNavBarSettingsObserver(policyHandler, createDisplayContext);
        this.mForceShowNavBarSettingsObserver = forceShowNavBarSettingsObserver;
        forceShowNavBarSettingsObserver.setOnChangeRunnable(
                new DisplayPolicy$$ExternalSyntheticLambda2(this, 3));
        this.mForceShowNavigationBarEnabled = forceShowNavBarSettingsObserver.isEnabled();
        policyHandler.post(
                new DisplayPolicy$$ExternalSyntheticLambda5(forceShowNavBarSettingsObserver, 0));
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

    public static WindowState chooseNavigationBackgroundWindow(
            WindowState windowState, WindowState windowState2, int i) {
        if (windowState2 != null
                && windowState2.isVisible()
                && i == 4
                && drawsBarBackground(windowState2)) {
            return windowState2;
        }
        if (drawsBarBackground(windowState)) {
            return windowState;
        }
        return null;
    }

    public static WindowState chooseNavigationColorWindowLw(
            WindowState windowState, WindowState windowState2, int i) {
        return (windowState2 == null
                        || !windowState2.isVisible()
                        || i != 4
                        || (windowState2.mAttrs.flags & Integer.MIN_VALUE) == 0)
                ? windowState
                : (windowState == null
                                || !windowState.mIsDimming
                                || WindowManager.LayoutParams.mayUseInputMethod(
                                        windowState.mAttrs.flags))
                        ? windowState2
                        : windowState;
    }

    public static boolean drawsBarBackground(WindowState windowState) {
        if (windowState == null) {
            return true;
        }
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        return (layoutParams.privateFlags & 32768) != 0
                || ((layoutParams.flags & Integer.MIN_VALUE) != 0);
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

    public static boolean isOverlappingWithNavBar(WindowState windowState) {
        if (!windowState.isVisible()) {
            return false;
        }
        if (windowState.getTask() != null && windowState.getTask().isFreeformStashed()) {
            InsetsState insetsState = windowState.getDisplayContent().mInsetsStateController.mState;
            Rect stashedBounds = windowState.getTask().getStashedBounds();
            return stashedBounds != null
                    && intersectsAnyInsets(
                            stashedBounds, insetsState, WindowInsets.Type.navigationBars());
        }
        if (!windowState.getWindowConfiguration().tasksAreFloating()
                || windowState.getDisplayContent() == null) {
            return intersectsAnyInsets(
                    windowState.mIsDimming
                            ? windowState.getBounds()
                            : windowState.mWindowFrames.mFrame,
                    windowState.getInsetsState(false),
                    WindowInsets.Type.navigationBars());
        }
        return intersectsAnyInsets(
                windowState.mIsDimming ? windowState.getBounds() : windowState.mWindowFrames.mFrame,
                windowState.getDisplayContent().mInsetsStateController.mState,
                WindowInsets.Type.navigationBars());
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addWindowLw(
            final com.android.server.wm.WindowState r10,
            android.view.WindowManager.LayoutParams r11) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayPolicy.addWindowLw(com.android.server.wm.WindowState,"
                    + " android.view.WindowManager$LayoutParams):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0016, code lost:

       if (r0 != 2006) goto L37;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0135, code lost:

       if ((134217728 & r8) != 0) goto L63;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x013e, code lost:

       if (android.view.InsetsState.clearsCompatInsets(r10.type, r8, 0, 0) != false) goto L63;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void adjustWindowParamsLw(
            com.android.server.wm.WindowState r9, android.view.WindowManager.LayoutParams r10) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayPolicy.adjustWindowParamsLw(com.android.server.wm.WindowState,"
                    + " android.view.WindowManager$LayoutParams):void");
    }

    public final void beginPostLayoutPolicyLw() {
        this.mLeftGestureHost = null;
        this.mTopGestureHost = null;
        this.mRightGestureHost = null;
        this.mBottomGestureHost = null;
        this.mTopFullscreenOpaqueWindowState = null;
        this.mNavBarColorWindowCandidate = null;
        this.mNavBarBackgroundWindowCandidate = null;
        this.mStatusBarAppearanceRegionList.clear();
        this.mLetterboxDetails.clear();
        this.mStatusBarBackgroundWindows.clear();
        this.mStatusBarColorCheckedBounds.setEmpty();
        this.mStatusBarBackgroundCheckedBounds.setEmpty();
        this.mSystemBarColorApps.clear();
        this.mAllowLockscreenWhenOn = false;
        this.mShowingDream = false;
        this.mIsFreeformWindowOverlappingWithNavBar = false;
        this.mForciblyShownTypes = 0;
        DisplayPolicyExt displayPolicyExt = this.mExt;
        if (displayPolicyExt.mDisplayPolicy.mDisplayContent.mDisplayId == 0) {
            FreeformController freeformController =
                    displayPolicyExt.mService.mAtmService.mFreeformController;
            DisplayContent defaultDisplayContentLocked =
                    freeformController.mAtm.mWindowManager.getDefaultDisplayContentLocked();
            if (defaultDisplayContentLocked == null) {
                Slog.d("FreeformController", "Default display content is null");
            } else if (!defaultDisplayContentLocked.isDexMode()) {
                freeformController.mTmpForceHideFreeformRequester = null;
                freeformController.mTmpForceHideMinimizeRequester = null;
                defaultDisplayContentLocked.forAllWindows(
                        (Consumer)
                                new FreeformController$$ExternalSyntheticLambda0(
                                        freeformController, 1),
                        true);
                if (freeformController.mForceHideFreeformRequester
                        != freeformController.mTmpForceHideFreeformRequester) {
                    Slog.d(
                            "FreeformController",
                            "beginPostLayoutPolicyLw: forceHideRequester changed, old="
                                    + freeformController.mForceHideFreeformRequester
                                    + ", new="
                                    + freeformController.mTmpForceHideFreeformRequester);
                    freeformController.mForceHideFreeformRequester =
                            freeformController.mTmpForceHideFreeformRequester;
                    freeformController.setBlockToAddForceHideFreeformTasks(false);
                }
                if (freeformController.mForceHideMinimizeRequester
                        != freeformController.mTmpForceHideMinimizeRequester) {
                    Slog.d(
                            "FreeformController",
                            "beginPostLayoutPolicyLw: forceHide minimize Requester changed, old="
                                    + freeformController.mForceHideMinimizeRequester
                                    + ", new="
                                    + freeformController.mTmpForceHideMinimizeRequester);
                    freeformController.mForceHideMinimizeRequester =
                            freeformController.mTmpForceHideMinimizeRequester;
                }
            }
        }
        displayPolicyExt.mFakeFocusedWindow = null;
        CoverPolicy coverPolicy = displayPolicyExt.mCoverPolicy;
        if (coverPolicy != null) {
            coverPolicy.mAppsToBeHiddenBySViewCover.clear();
            coverPolicy.mHideSViewCoverWindow = null;
            if (!((CoverState) WmCoverState.getInstance()).attached) {
                coverPolicy.mLastCoverAppCovered = false;
            }
            coverPolicy.mWallpaperTargetMayChange = false;
        }
        this.mIsPipWindowOverlappingWithNavBar = false;
        this.mIsResizingByDivider = false;
        this.mImeInsetsConsumed = false;
    }

    public final void callStatusBarSafely(Consumer consumer) {
        this.mHandler.post(new DisplayPolicy$$ExternalSyntheticLambda16(this, consumer));
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
                if (this.mNotificationShade == null
                        || this.mNotificationShade.mAttrs.type != 2040) {
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void finishPostLayoutPolicyLw() {
        /*
            Method dump skipped, instructions count: 382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayPolicy.finishPostLayoutPolicyLw():void");
    }

    public final Rect getBarContentFrameForWindow(WindowState windowState, int i) {
        DisplayFrames displayFrames = this.mDisplayContent.mDisplayFrames;
        DisplayFrames fixedRotationTransformDisplayFrames =
                windowState.mToken.getFixedRotationTransformDisplayFrames();
        if (fixedRotationTransformDisplayFrames != null) {
            displayFrames = fixedRotationTransformDisplayFrames;
        } else if (windowState.isLayoutNeededInUdcCutout()) {
            displayFrames = windowState.mDisplayContent.mUdcCutoutPolicy.mUdcDisplayFrames;
        }
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

    public Context getContext() {
        return this.mContext;
    }

    public Resources getCurrentUserResources() {
        if (this.mCurrentUserResources == null) {
            updateCurrentUserResources();
        }
        return this.mCurrentUserResources;
    }

    public final DecorInsets.Info getDecorInsetsInfo(int i, int i2, int i3) {
        DecorInsets decorInsets = this.mDecorInsets;
        DecorInsets.Info info = decorInsets.mInfoForRotation[i];
        if (info.mNeedUpdate) {
            info.update(i, i2, i3, decorInsets.mDisplayContent);
        }
        return info;
    }

    public final StatusBarManagerInternal getStatusBarManagerInternal() {
        StatusBarManagerInternal statusBarManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mStatusBarManagerInternal == null) {
                    this.mStatusBarManagerInternal =
                            (StatusBarManagerInternal)
                                    LocalServices.getService(StatusBarManagerInternal.class);
                }
                statusBarManagerInternal = this.mStatusBarManagerInternal;
            } catch (Throwable th) {
                throw th;
            }
        }
        return statusBarManagerInternal;
    }

    public final boolean hasDexStandAloneNavigationBar() {
        WindowState windowState = this.mNavigationBar;
        return windowState != null && windowState.mAttrs.type == 2623;
    }

    public final boolean hasDexStandAloneNotificationShade() {
        return this.mNotificationShade != null && this.mNotificationShade.mAttrs.type == 2622;
    }

    public final boolean hasDexStandAloneStatusBar() {
        WindowState windowState = this.mStatusBar;
        return windowState != null && windowState.mAttrs.type == 2621;
    }

    public final boolean isDexStandAloneMode() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent.isDexMode() && displayContent.isDefaultDisplay;
    }

    public boolean isFullyTransparentAllowed(WindowState windowState, int i) {
        if (windowState == null) {
            return true;
        }
        Rect barContentFrameForWindow = getBarContentFrameForWindow(windowState, i);
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord == null) {
            return true;
        }
        if (activityRecord.isLayoutNeededInUdcCutout()) {
            UdcCutoutPolicy udcCutoutPolicy = activityRecord.mDisplayContent.mUdcCutoutPolicy;
            if (udcCutoutPolicy.mTmpBarContentFrame == null) {
                udcCutoutPolicy.mTmpBarContentFrame = new Rect();
            }
            udcCutoutPolicy.mTmpBarContentFrame.set(barContentFrameForWindow);
            udcCutoutPolicy.mTmpBarContentFrame.intersectUnchecked(
                    udcCutoutPolicy.mUdcDisplayFrames.mDisplayCutoutSafe);
            barContentFrameForWindow = udcCutoutPolicy.mTmpBarContentFrame;
        }
        AppCompatLetterboxPolicy.LetterboxPolicyState letterboxPolicyState =
                activityRecord.mAppCompatController.mAppCompatLetterboxPolicy.mLetterboxPolicyState;
        if (!letterboxPolicyState.isRunning()) {
            return true;
        }
        Letterbox.LetterboxSurface[] letterboxSurfaceArr =
                letterboxPolicyState.mLetterbox.mSurfaces;
        int i2 = 0;
        int i3 = 0;
        for (Letterbox.LetterboxSurface letterboxSurface : letterboxSurfaceArr) {
            Rect rect = letterboxSurface.mLayoutFrameGlobal;
            if (rect.isEmpty()) {
                i2++;
            } else if (!Rect.intersects(rect, barContentFrameForWindow)) {
                i3++;
            } else if (rect.contains(barContentFrameForWindow)) {
                return true;
            }
        }
        return i2 + i3 == letterboxSurfaceArr.length;
    }

    public final boolean isImmersiveMode(WindowState windowState) {
        InsetsControlTarget navControlTarget;
        boolean z = false;
        if (windowState == null) {
            return false;
        }
        windowState.mPolicy.getClass();
        int windowLayerLw = WindowManagerPolicy.getWindowLayerLw(windowState);
        windowState.mPolicy.getClass();
        if (windowLayerLw > WindowManagerPolicy.getWindowLayerFromTypeLw(2000)
                || windowState.isActivityTypeDream()) {
            return false;
        }
        boolean isDexMode = windowState.isDexMode();
        DisplayContent displayContent = this.mDisplayContent;
        if (!isDexMode) {
            return displayContent.mInsetsPolicy.hasHiddenSources(
                    WindowInsets.Type.navigationBars());
        }
        if (windowState.getWindowingMode() == 1) {
            WindowState windowState2 = null;
            if (windowState.mIsChildWindow
                    && (navControlTarget =
                                    displayContent.mInsetsPolicy.getNavControlTarget(
                                            windowState, false))
                            != null) {
                windowState2 = navControlTarget.getWindow();
            }
            if (windowState2 != null) {
                windowState = windowState2;
            }
            if (windowState.isRequestedVisible(WindowInsets.Type.navigationBars(), true)) {
                return false;
            }
            return displayContent.mInsetsPolicy.hasHiddenSources(WindowInsets.Type.navigationBars())
                    || displayContent.mInsetsPolicy.isTransient(WindowInsets.Type.navigationBars());
        }
        InsetsPolicy insetsPolicy = displayContent.mInsetsPolicy;
        InsetsPolicy.ImmersiveControlTarget immersiveControlTarget =
                insetsPolicy.mDexForceImmersiveModeControlTarget;
        int navigationBars = WindowInsets.Type.navigationBars();
        InsetsStateController insetsStateController = insetsPolicy.mStateController;
        InsetsState insetsState = insetsStateController.mState;
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if ((sourceAt.getType() & navigationBars) != 0
                    && !sourceAt.getFrame().isEmpty()
                    && !sourceAt.isVisible()) {
                int type = sourceAt.getType();
                ArrayList arrayList =
                        (ArrayList)
                                insetsStateController.mControlTargetProvidersMap.get(
                                        immersiveControlTarget);
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        InsetsSource insetsSource = ((InsetsSourceProvider) it.next()).mSource;
                        if (insetsSource != null && insetsSource.getType() == type) {
                            z = true;
                            break;
                        }
                    }
                }
                return !z;
            }
        }
        return false;
    }

    public final void layoutWindowLw(
            WindowState windowState, WindowState windowState2, DisplayFrames displayFrames) {
        int i;
        int i2;
        int i3;
        Task task;
        windowState.getClass();
        if (CoreRune.FW_FOLD_WALLPAPER_POLICY
                && windowState.mDisplayContent.isDefaultDisplay
                && !windowState.mToken.canShowInCurrentDevice()) {
            return;
        }
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord == null || !activityRecord.mWaitForEnteringPinnedMode) {
            DisplayFrames fixedRotationTransformDisplayFrames =
                    windowState.mToken.getFixedRotationTransformDisplayFrames();
            if (fixedRotationTransformDisplayFrames == null) {
                fixedRotationTransformDisplayFrames =
                        windowState.isLayoutNeededInUdcCutout()
                                ? windowState.mDisplayContent.mUdcCutoutPolicy.mUdcDisplayFrames
                                : displayFrames;
            }
            WindowManager.LayoutParams forRotation =
                    windowState.mAttrs.forRotation(fixedRotationTransformDisplayFrames.mRotation);
            ClientWindowFrames clientWindowFrames = sTmpClientFrames;
            clientWindowFrames.attachedFrame =
                    windowState2 != null ? windowState2.mWindowFrames.mFrame : null;
            boolean z = forRotation == windowState.mAttrs;
            int i4 = z ? windowState.mRequestedWidth : -1;
            if (this.mDisplayContent.isDexMode() && windowState == this.mNavigationBar) {
                int i5 = this.mDexTaskbarHeight;
                forRotation.height = i5;
                i = i5;
            } else {
                i = z ? windowState.mRequestedHeight : -1;
            }
            this.mWindowBounds.set(windowState.getBounds());
            if (windowState.isPopOver() && (task = windowState.getTask()) != null) {
                DisplayContent displayContent = windowState.getDisplayContent();
                InputTarget inputTarget = displayContent.mImeInputTarget;
                WindowState windowState3 =
                        inputTarget != null ? inputTarget.getWindowState() : null;
                boolean z2 =
                        windowState3 != null
                                && (!windowState.inFreeformWindowingMode()
                                        ? windowState != windowState3
                                        : task != windowState3.getTask());
                WindowState windowState4 = displayContent.mInputMethodWindow;
                if (z2 && windowState4 != null && windowState4.isVisibleNow()) {
                    InsetsState insetsState = displayContent.mInsetsStateController.mState;
                    this.mTmpFrame.set(insetsState.getDisplayFrame());
                    Rect rect = this.mTmpFrame;
                    rect.inset(
                            insetsState.calculateInsets(
                                    rect,
                                    WindowInsets.Type.systemBars()
                                            | WindowInsets.Type.ime()
                                            | WindowInsets.Type.displayCutout(),
                                    false));
                    int i6 = windowState.getBounds().bottom - this.mTmpFrame.bottom;
                    if (i6 > 0) {
                        PopOverController popOverController = displayContent.mPopOverController;
                        int i7 = windowState.getBounds().top;
                        int i8 = this.mTmpFrame.top;
                        popOverController.getClass();
                        if (windowState.isDesktopModeEnabled()
                                || windowState.inFreeformWindowingMode()) {
                            i7 -= windowState.getCaptionHeight();
                            if (windowState.inFreeformWindowingMode()) {
                                i7 -= windowState.getFreeformThickness();
                            }
                            if (windowState.inFullscreenWindowingMode()) {
                                i7 -=
                                        (int)
                                                TypedValue.applyDimension(
                                                        1,
                                                        32.0f,
                                                        popOverController
                                                                .mDisplayContent
                                                                .mWmService
                                                                .mContext
                                                                .getResources()
                                                                .getDisplayMetrics());
                            }
                        }
                        this.mWindowBounds.offset(
                                0, -Math.min(i6, Math.max(Math.max(i7, 0) - i8, 0)));
                        if (!windowState.mLayoutWithIme) {
                            windowState.mLayoutWithIme = true;
                            windowState.forceReportingResized();
                        }
                        windowState.mWindowFrames.mContentChanged = true;
                    }
                } else if (windowState.mLayoutWithIme) {
                    windowState.mLayoutWithIme = false;
                    windowState.forceReportingResized();
                }
            }
            this.mWindowLayout.computeFrames(
                    forRotation,
                    windowState.getInsetsState(false),
                    fixedRotationTransformDisplayFrames.mDisplayCutoutSafe,
                    this.mWindowBounds,
                    windowState.getWindowingMode(),
                    i4,
                    i,
                    windowState.mRequestedVisibleTypes,
                    windowState.mGlobalScale,
                    clientWindowFrames,
                    windowState.getStageType());
            int i9 = windowState.mRequestedWidth;
            int i10 = windowState.mRequestedHeight;
            WindowFrames windowFrames = windowState.mWindowFrames;
            windowState.mTmpRect.set(windowFrames.mParentFrame);
            windowFrames.mDisplayFrame.set(clientWindowFrames.displayFrame);
            windowFrames.mParentFrame.set(clientWindowFrames.parentFrame);
            windowFrames.mFrame.set(clientWindowFrames.frame);
            windowFrames.mCompatFrame.set(windowFrames.mFrame);
            float f = windowState.mInvGlobalScale;
            Rect rect2 = windowFrames.mCompatFrame;
            if (f == 1.0f
                    || windowState.getConfiguration().windowConfiguration.getCompatSandboxInvScale()
                            == 1.0f) {
                float f2 = windowState.mInvGlobalScale;
                if (f2 != 1.0f) {
                    windowFrames.mCompatFrame.scale(f2);
                }
            } else {
                int width = rect2.width();
                int height = rect2.height();
                int i11 = (int) ((rect2.left * f) + 0.5f);
                rect2.left = i11;
                int i12 = (int) ((rect2.top * f) + 0.5f);
                rect2.top = i12;
                rect2.right = i11 + ((int) ((width * f) + 0.5f));
                rect2.bottom = i12 + ((int) ((height * f) + 0.5f));
            }
            windowFrames.mParentFrameWasClippedByDisplayCutout =
                    clientWindowFrames.isParentFrameClippedByDisplayCutout;
            windowFrames.mRelFrame.set(windowFrames.mFrame);
            WindowContainer parent = windowState.getParent();
            if (windowState.mIsChildWindow) {
                Rect rect3 = ((WindowState) parent).mWindowFrames.mFrame;
                i3 = rect3.left;
                i2 = rect3.top;
            } else if (parent != null) {
                Rect bounds = parent.getBounds();
                i3 = bounds.left;
                i2 = bounds.top;
            } else {
                i2 = 0;
                i3 = 0;
            }
            Rect rect4 = windowFrames.mRelFrame;
            Rect rect5 = windowFrames.mFrame;
            rect4.offsetTo(rect5.left - i3, rect5.top - i2);
            if (i9 != windowState.mLastRequestedWidth
                    || i10 != windowState.mLastRequestedHeight
                    || !windowState.mTmpRect.equals(windowFrames.mParentFrame)) {
                windowState.mLastRequestedWidth = i9;
                windowState.mLastRequestedHeight = i10;
                windowFrames.mContentChanged = true;
            }
            if (!windowFrames.mFrame.equals(windowFrames.mLastFrame)
                    || !windowFrames.mRelFrame.equals(windowFrames.mLastRelFrame)) {
                windowState.mWmService.mFrameChangingWindows.add(windowState);
            }
            if (windowState.mAttrs.type == 2034
                    && !windowFrames.mFrame.equals(windowFrames.mLastFrame)) {
                windowState.mMovedByResize = true;
            }
            if (windowState.mIsWallpaper) {
                Rect rect6 = windowFrames.mLastFrame;
                Rect rect7 = windowFrames.mFrame;
                if (rect6.width() != rect7.width() || rect6.height() != rect7.height()) {
                    windowState.mDisplayContent.mWallpaperController.updateWallpaperOffset(
                            windowState, false);
                }
            }
            windowState.updateSourceFrame(windowFrames.mFrame);
            ActivityRecord activityRecord2 = windowState.mActivityRecord;
            if (activityRecord2 != null && !windowState.mIsChildWindow) {
                activityRecord2.mAppCompatController.mAppCompatLetterboxPolicy.start(windowState);
            }
            windowState.mSurfacePlacementNeeded = true;
            windowState.mHaveFrame = true;
        }
    }

    public final int navigationBarPosition(int i) {
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

    public final void notifyDisplayReady() {
        this.mHandler.post(new DisplayPolicy$$ExternalSyntheticLambda2(this, 1));
        startEnableTouchEvent(false);
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                new StringBuilder(
                        "notifyDisplayReady() >> KnoxZT startEnableTouchEvent called for Display Id"
                            + " : "),
                this.mDisplayContent.mDisplayId,
                "WindowManager");
    }

    public final void onConfigurationChanged() {
        boolean z;
        Resources currentUserResources = getCurrentUserResources();
        if (this.mDisplayContent.isDefaultDisplay) {
            this.mHasNavigationBar =
                    currentUserResources.getBoolean(R.bool.config_sms_decode_gsm_8bit_data);
        }
        if (this.mDisplayContent.isDexMode()) {
            this.mDexTaskbarHeight = currentUserResources.getDimensionPixelSize(17106303);
        }
        this.mSystemGestures.onConfigurationChanged();
        this.mNavBarOpacityMode =
                currentUserResources.getInteger(R.integer.config_powerStatsAggregationPeriod);
        this.mLeftGestureInset =
                this.mGestureNavigationSettingsObserver.getLeftSensitivity(currentUserResources);
        this.mRightGestureInset =
                this.mGestureNavigationSettingsObserver.getRightSensitivity(currentUserResources);
        this.mNavigationBarAlwaysShowOnSideGesture =
                currentUserResources.getBoolean(R.bool.config_omnipresentCommunalUser);
        this.mRemoteInsetsControllerControlsSystemBars =
                currentUserResources.getBoolean(
                        R.bool.config_remoteInsetsControllerControlsSystemBars);
        updateConfigurationAndScreenSizeDependentBehaviors();
        if (currentUserResources.getBoolean(R.bool.config_autoPowerModePreferWristTilt)) {
            Flags.enableTinyTaskbar();
            z = true;
        } else {
            z = false;
        }
        if (this.mShouldAttachNavBarToAppDuringTransition != z) {
            this.mShouldAttachNavBarToAppDuringTransition = z;
        }
        DisplayPolicyExt displayPolicyExt = this.mExt;
        DisplayPolicy displayPolicy = displayPolicyExt.mDisplayPolicy;
        Resources currentUserResources2 = displayPolicy.getCurrentUserResources();
        int i = displayPolicyExt.mNavigationMode;
        int integer = currentUserResources2.getInteger(R.integer.config_pinnerWebviewPinBytes);
        displayPolicyExt.mNavigationMode = integer;
        if (i != integer && displayPolicy.mDisplayContent.isDefaultDisplay) {
            StringBuilder sb =
                    new StringBuilder(
                            "DisplayPolicyExt#onConfigurationChanged, NavigationMode(new)=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    displayPolicyExt.mNavigationMode,
                    i,
                    ", NavigationMode(old)=",
                    ", callers=",
                    sb);
            sb.append(Debug.getCallers(3));
            Log.i("DisplayPolicyExt", sb.toString());
        }
        displayPolicyExt.mNavBarImeBtnAllRotationsAllowed =
                CoreRune.IS_TABLET_DEVICE
                        || currentUserResources2.getBoolean(R.bool.config_pdp_reject_enable_retry);
    }

    public final void onDisplaySwitchFinished() {
        WallpaperController wallpaperController = this.mDisplayContent.mWallpaperController;
        if (wallpaperController.mIsWallpaperNotifiedOnDisplaySwitch) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    wallpaperController.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    wallpaperController.mIsWallpaperNotifiedOnDisplaySwitch = false;
                    wallpaperController.notifyDisplaySwitch(false);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
        this.mDisplayContent.mDisplayUpdater.onDisplaySwitching(false);
    }

    public final void removeWindowLw(WindowState windowState) {
        if (this.mStatusBar == windowState) {
            if (hasDexStandAloneStatusBar()) {
                WindowState windowState2 = this.mStatusBar;
                WindowState windowState3 = this.mDefaultStatusBar;
                if (windowState2 != windowState3) {
                    this.mStatusBar = null;
                    if (windowState3 != null) {
                        addWindowLw(windowState3, windowState3.mAttrs);
                    }
                    Slog.d(
                            "WindowManager",
                            "restoreDefaultStatusBar: prev="
                                    + windowState2
                                    + ", now="
                                    + this.mStatusBar);
                }
            } else {
                this.mStatusBar = null;
            }
        } else if (this.mNavigationBar == windowState) {
            if (hasDexStandAloneNavigationBar()) {
                WindowState windowState4 = this.mNavigationBar;
                WindowState windowState5 = this.mDefaultNavigationBar;
                if (windowState4 != windowState5) {
                    this.mNavigationBar = null;
                    if (windowState5 != null) {
                        addWindowLw(windowState5, windowState5.mAttrs);
                    }
                    Slog.d(
                            "WindowManager",
                            "restoreDefaultNavigationBar: prev="
                                    + windowState4
                                    + ", now="
                                    + this.mNavigationBar);
                }
            } else {
                this.mNavigationBar = null;
            }
        } else if (this.mNotificationShade == windowState) {
            if (!hasDexStandAloneNotificationShade()) {
                this.mNotificationShade = null;
            } else if (this.mNotificationShade != this.mDefaultNotificationShade) {
                WindowState windowState6 = this.mNotificationShade;
                this.mNotificationShade = null;
                WindowState windowState7 = this.mDefaultNotificationShade;
                if (windowState7 != null) {
                    addWindowLw(windowState7, windowState7.mAttrs);
                }
                Slog.d(
                        "WindowManager",
                        "restoreDefaultNotificationShade: prev="
                                + windowState6
                                + ", now="
                                + this.mNotificationShade);
            }
        }
        if (this.mLastFocusedWindow == windowState) {
            this.mLastFocusedWindow = null;
        }
        if (windowState.hasInsetsSourceProvider()) {
            SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
            InsetsStateController insetsStateController =
                    this.mDisplayContent.mInsetsStateController;
            for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
                InsetsSourceProvider insetsSourceProvider =
                        (InsetsSourceProvider) insetsSourceProviders.valueAt(size);
                insetsSourceProvider.setWindowContainer(null, null, null);
                int id = insetsSourceProvider.mSource.getId();
                insetsStateController.getClass();
                if (id != InsetsSource.ID_IME) {
                    insetsStateController.mState.removeSource(id);
                    insetsStateController.mProviders.remove(id);
                }
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
        DisplayPolicyExt displayPolicyExt = this.mExt;
        CoverPolicy coverPolicy = displayPolicyExt.mCoverPolicy;
        if (coverPolicy != null && coverPolicy.mCoverWindow == windowState) {
            coverPolicy.mCoverWindow = null;
        }
        if (displayPolicyExt.mPayHandlerWin == windowState) {
            displayPolicyExt.mPayHandlerWin = null;
        }
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord != null) {
            PopOverState popOverState = activityRecord.mPopOverState;
            if (popOverState.mOptions != null || popOverState.mOptionsInherited != null) {
                PopOverController popOverController =
                        displayPolicyExt.mDisplayPolicy.mDisplayContent.mPopOverController;
                popOverController.getClass();
                WindowState findMainWindow = windowState.mActivityRecord.findMainWindow(false);
                if (findMainWindow != null
                        && findMainWindow.mChildDimmingDialogs.contains(windowState)) {
                    findMainWindow.mChildDimmingDialogs.remove(windowState);
                    popOverController.updatePopOverDimAttributesLw(
                            findMainWindow, findMainWindow.mAttrs);
                }
            }
        }
        TaskbarController taskbarController = displayPolicyExt.mTaskbarController;
        if (taskbarController.mTaskbarWin == windowState) {
            taskbarController.mTaskbarWin = null;
            WindowState windowState8 = taskbarController.mDisplayPolicy.mNavigationBar;
            if ((windowState8 == null ? null : windowState8.getControllableInsetProvider())
                    != null) {
                WindowState windowState9 = taskbarController.mDisplayPolicy.mNavigationBar;
                if ((windowState9 == null ? null : windowState9.getControllableInsetProvider())
                                .mControlTarget
                        != null) {
                    DisplayPolicy displayPolicy = taskbarController.mDisplayPolicy;
                    InsetsStateController insetsStateController2 =
                            displayPolicy.mDisplayContent.mInsetsStateController;
                    WindowState windowState10 = displayPolicy.mNavigationBar;
                    insetsStateController2.notifyControlChanged(
                            (windowState10 == null
                                            ? null
                                            : windowState10.getControllableInsetProvider())
                                    .mControlTarget);
                }
            }
        }
        if (displayPolicyExt.mGameToolsWindow == windowState) {
            displayPolicyExt.mGameToolsWindow = null;
            displayPolicyExt.notifyRequestedGameToolsWin(false);
        } else if (displayPolicyExt.mGameToolsOverlayWindow == windowState) {
            displayPolicyExt.mGameToolsOverlayWindow = null;
        }
        if ((windowState.mAttrs.privateFlags & 131072) != 0) {
            this.mImmersiveConfirmationWindowExists = false;
        }
    }

    public void requestTransientBars(WindowState windowState, boolean z) {
        if (ViewRootImpl.CLIENT_TRANSIENT) {
            return;
        }
        if (CoreRune.IS_FACTORY_BINARY
                || FactoryTest.isAutomaticTestMode(this.mContext)
                || FactoryTest.isRunningFactoryApp()) {
            Slog.d("WindowManager", "Not showing transient bar, because factory test mode");
            return;
        }
        if (windowState == this.mStatusBar
                && this.mService.mExt.mPolicyExt.mLockTaskModeState == 2) {
            Slog.d("WindowManager", "Not showing transient bar, because lock task mode pinned");
            return;
        }
        if (windowState == null
                || !((PhoneWindowManager) this.mService.mPolicy).isUserSetupComplete()) {
            return;
        }
        if (!this.mCanSystemBarsBeShownByUser) {
            Slog.d(
                    "WindowManager",
                    "Remote insets controller disallows showing system bars - ignoring request");
            return;
        }
        if (windowState == this.mStatusBar && this.mDisplayContent.isDexMode()) {
            if (this.mNavigationBar == windowState) {
                return;
            }
            if (windowState.hasInsetsSourceProvider()) {
                SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
                for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
                    if (((InsetsSourceProvider) insetsSourceProviders.valueAt(size))
                                    .mSource.getType()
                            == WindowInsets.Type.navigationBars()) {
                        return;
                    }
                }
            }
        }
        InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
        InsetsControlTarget insetsControlTarget =
                controllableInsetProvider != null ? controllableInsetProvider.mControlTarget : null;
        if (insetsControlTarget == null || insetsControlTarget == this.mNotificationShade) {
            return;
        }
        WindowState window = insetsControlTarget.getWindow();
        if (window == null || !window.isActivityTypeDream()) {
            int statusBars =
                    (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars())
                            & insetsControlTarget.getRequestedVisibleTypes();
            Slog.d(
                    "WindowManager",
                    "requestTransientBars: swipeTarget="
                            + windowState
                            + ", controlTarget="
                            + insetsControlTarget
                            + ", canShowTransient="
                            + insetsControlTarget.canShowTransient()
                            + ", restorePositionTypes=0x"
                            + Integer.toHexString(statusBars)
                            + ", from="
                            + Debug.getCallers(2));
            InsetsSourceProvider controllableInsetProvider2 =
                    windowState.getControllableInsetProvider();
            if (controllableInsetProvider2 != null
                    && controllableInsetProvider2.mSource.getType()
                            == WindowInsets.Type.navigationBars()
                    && (WindowInsets.Type.navigationBars() & statusBars) != 0) {
                insetsControlTarget.showInsets(WindowInsets.Type.navigationBars(), false, null);
                return;
            }
            if (insetsControlTarget.canShowTransient()) {
                InsetsPolicy insetsPolicy = this.mDisplayContent.mInsetsPolicy;
                insetsPolicy.mLastTransientRequestedByPolicyControl =
                        insetsControlTarget instanceof InsetsPolicy.PolicyControlTarget;
                insetsPolicy.showTransient(SHOW_TYPES_FOR_SWIPE, z);
                if (this.mDisplayContent.isDexMode()) {
                    insetsControlTarget.showInsets(
                            (~WindowInsets.Type.statusBars()) & statusBars, false, null);
                } else {
                    insetsControlTarget.showInsets(statusBars, false, null);
                }
            } else {
                if (this.mDisplayContent.isDexMode()) {
                    insetsControlTarget.showInsets(WindowInsets.Type.navigationBars(), false, null);
                } else {
                    insetsControlTarget.showInsets(
                            WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars(),
                            false,
                            null);
                }
                if (windowState == this.mStatusBar) {
                    if (this.mDisplayContent.isDexMode() || this.mSkipTransferTouchToStatusBar) {
                        return;
                    }
                    WindowState windowState2 = this.mStatusBar;
                    if (!windowState2.mWmService.mInputManager.transferTouch(
                            windowState2.mInputChannelToken, windowState2.getDisplayId())) {
                        Slog.i("WindowManager", "Could not transfer touch to the status bar");
                    }
                }
            }
            if (!ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION && !ViewRootImpl.CLIENT_TRANSIENT) {
                ImmersiveModeConfirmation immersiveModeConfirmation =
                        this.mImmersiveModeConfirmation;
                if (immersiveModeConfirmation.mClingWindow != null) {
                    immersiveModeConfirmation.mHandler.post(immersiveModeConfirmation.mConfirm);
                    return;
                }
                return;
            }
            StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                    (StatusBarManagerService.AnonymousClass2) this.mStatusBarManagerInternal;
            if (StatusBarManagerService.this.mBar == null) {
                return;
            }
            try {
                StatusBarManagerService.this.mBar.confirmImmersivePrompt();
            } catch (RemoteException unused) {
            }
        }
    }

    public final void screenTurnedOff() {
        synchronized (this.mLock) {
            this.mScreenOnEarly = false;
            this.mScreenOnFully = false;
            this.mKeyguardDrawComplete = false;
            this.mWindowManagerDrawComplete = false;
            this.mScreenOnListener = null;
            this.mService.mAtmService.mVisibleDozeUiProcess = null;
        }
    }

    public final void screenTurningOn(DisplayPowerController.ScreenOnUnblocker screenOnUnblocker) {
        WindowProcessController windowProcessController;
        synchronized (this.mLock) {
            try {
                this.mScreenOnEarly = true;
                this.mScreenOnFully = false;
                this.mKeyguardDrawComplete = false;
                this.mWindowManagerDrawComplete = false;
                this.mScreenOnListener = screenOnUnblocker;
                if (this.mAwake || this.mNotificationShade == null) {
                    windowProcessController = null;
                } else {
                    windowProcessController = this.mNotificationShade.mSession.mProcess;
                    this.mService.mAtmService.mVisibleDozeUiProcess = windowProcessController;
                }
                if (this.mDisplayContent.mDisplayId == 1) {
                    this.mScreenOnFully = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (windowProcessController != null) {
            Trace.instant(32L, "screenTurnedOnWhileDozing");
            this.mService.mAtmService.setProcessAnimatingWhileDozing(windowProcessController);
        }
    }

    public final void setAwake(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z == this.mAwake) {
                    return;
                }
                this.mAwake = z;
                if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && z) {
                    this.mDisplayContent.mWmService.getClass();
                }
                if (this.mDisplayContent.isDefaultDisplay) {
                    if (z) {
                        this.mService.mAtmService.mVisibleDozeUiProcess = null;
                    } else if (this.mScreenOnFully && this.mNotificationShade != null) {
                        this.mService.mAtmService.mVisibleDozeUiProcess =
                                this.mNotificationShade.mSession.mProcess;
                    }
                    this.mService.mAtmService.mKeyguardController.updateDeferTransitionForAod(
                            this.mAwake);
                    if (!z) {
                        onDisplaySwitchFinished();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setCanSystemBarsBeShownByUser(boolean z) {
        this.mCanSystemBarsBeShownByUser = z;
    }

    public final void setDropInputModePolicy(
            WindowState windowState, WindowManager.LayoutParams layoutParams) {
        if (layoutParams.type == 2005 && (layoutParams.privateFlags & 536870912) == 0) {
            ((SurfaceControl.Transaction) this.mService.mTransactionFactory.get())
                    .setDropInputMode(windowState.mSurfaceControl, 1)
                    .apply();
        }
    }

    public final void setHdmiPlugged(boolean z, boolean z2) {
        if (z2 || this.mHdmiPlugged != z) {
            this.mHdmiPlugged = z;
            this.mService.updateRotationUnchecked(true, true);
            Intent intent = new Intent("android.intent.action.HDMI_PLUGGED");
            intent.addFlags(67108864);
            intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, z);
            this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
        }
    }

    public final void setPointerLocationEnabled(boolean z) {
        boolean z2 = CoreRune.SYSFW_APP_SPEG;
        DisplayContent displayContent = this.mDisplayContent;
        if (z2 && (displayContent.mDisplayInfo.flags & 32768) != 0) {
            Slog.d("SPEG", "Pointer location is not supported");
        } else if (displayContent.isDefaultDisplay || !displayContent.isPrivate()) {
            this.mHandler.sendEmptyMessage(z ? 4 : 5);
        }
    }

    public void setRemoteInsetsControllerControlsSystemBars(boolean z) {
        this.mRemoteInsetsControllerControlsSystemBars = z;
    }

    public final boolean shouldAttachNavBarToAppDuringTransition() {
        return this.mShouldAttachNavBarToAppDuringTransition && this.mNavigationBar != null;
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

    public final void simulateLayoutDisplay(DisplayFrames displayFrames) {
        sTmpClientFrames.attachedFrame = null;
        for (int size = this.mInsetsSourceWindowsExceptIme.size() - 1; size >= 0; size--) {
            WindowState windowState =
                    (WindowState) this.mInsetsSourceWindowsExceptIme.valueAt(size);
            if (!isDexStandAloneMode()
                    || !hasDexStandAloneNavigationBar()
                    || windowState != this.mDefaultNavigationBar) {
                this.mWindowLayout.computeFrames(
                        windowState.mAttrs.forRotation(displayFrames.mRotation),
                        displayFrames.mInsetsState,
                        displayFrames.mDisplayCutoutSafe,
                        displayFrames.mUnrestricted,
                        windowState.getWindowingMode(),
                        -1,
                        -1,
                        windowState.mRequestedVisibleTypes,
                        windowState.mGlobalScale,
                        sTmpClientFrames,
                        windowState.getStageType());
                SparseArray insetsSourceProviders = windowState.getInsetsSourceProviders();
                InsetsState insetsState = displayFrames.mInsetsState;
                for (int size2 = insetsSourceProviders.size() - 1; size2 >= 0; size2--) {
                    InsetsSourceProvider insetsSourceProvider =
                            (InsetsSourceProvider) insetsSourceProviders.valueAt(size2);
                    Rect rect = sTmpClientFrames.frame;
                    insetsSourceProvider.getClass();
                    InsetsSource insetsSource = new InsetsSource(insetsSourceProvider.mSource);
                    insetsSourceProvider.mTmpRect.set(rect);
                    TriFunction triFunction = insetsSourceProvider.mFrameProvider;
                    if (triFunction != null) {
                        triFunction.apply(
                                displayFrames,
                                insetsSourceProvider.mWindowContainer,
                                insetsSourceProvider.mTmpRect);
                    }
                    insetsSource.setFrame(insetsSourceProvider.mTmpRect);
                    insetsSource.updateSideHint(displayFrames.mUnrestricted);
                    insetsSource.setVisibleFrame((Rect) null);
                    insetsState.addSource(insetsSource);
                }
            }
        }
    }

    public final void startEnableTouchEvent(boolean z) {
        Message message = new Message();
        message.what = 107;
        if (z) {
            message.arg1 = 101;
        } else {
            message.arg1 = 102;
        }
        this.mHandler.sendMessage(message);
    }

    public final void systemReady() {
        if (!ViewRootImpl.CLIENT_TRANSIENT) {
            final SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                    this.mSystemGestures;
            systemGesturesPointerEventListener.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.SystemGesturesPointerEventListener$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemGesturesPointerEventListener systemGesturesPointerEventListener2 =
                                    SystemGesturesPointerEventListener.this;
                            int displayId =
                                    systemGesturesPointerEventListener2.mContext.getDisplayId();
                            if (DisplayManagerGlobal.getInstance().getDisplayInfo(displayId)
                                    == null) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        displayId,
                                        "Cannot create GestureDetector, display removed:",
                                        "SystemGestures");
                            } else {
                                systemGesturesPointerEventListener2.mGestureDetector =
                                        new SystemGesturesPointerEventListener.AnonymousClass1(
                                                systemGesturesPointerEventListener2.mContext,
                                                systemGesturesPointerEventListener2
                                                .new FlingGestureDetector(),
                                                systemGesturesPointerEventListener2.mHandler);
                            }
                        }
                    });
            if (systemGesturesPointerEventListener.mContext.getDisplayId() == 0) {
                systemGesturesPointerEventListener.mMultiWindowEdgeDetector =
                        new MultiWindowEdgeDetector(
                                systemGesturesPointerEventListener.mContext, "SystemGesture");
            }
        }
        if (this.mService.mPointerLocationEnabled) {
            setPointerLocationEnabled(true);
        }
        startEnableTouchEvent(false);
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                new StringBuilder(
                        "systemReady() >> KnoxZT startEnableTouchEvent called for Display Id : "),
                this.mDisplayContent.mDisplayId,
                "WindowManager");
    }

    public final boolean topAppHidesSystemBar(int i) {
        if (this.mTopFullscreenOpaqueWindowState == null
                || (this.mDisplayContent.mInsetsPolicy.mForcedShowingTypes & i) == i) {
            return false;
        }
        return !r0.isRequestedVisible(i, false);
    }

    public void updateCachedDecorInsets() {
        DecorInsets decorInsets;
        DecorInsets.Cache cache = this.mCachedDecorInsets;
        DisplayContent displayContent = this.mDisplayContent;
        if (cache == null) {
            this.mCachedDecorInsets = new DecorInsets.Cache(displayContent);
            decorInsets = null;
        } else {
            decorInsets = new DecorInsets(displayContent);
            decorInsets.setTo(this.mCachedDecorInsets.mDecorInsets);
        }
        DecorInsets.Cache cache2 = this.mCachedDecorInsets;
        cache2.mPreserveId = -1;
        DecorInsets decorInsets2 = cache2.mDecorInsets;
        DecorInsets decorInsets3 = this.mDecorInsets;
        decorInsets2.setTo(decorInsets3);
        if (decorInsets != null) {
            decorInsets3.setTo(decorInsets);
            this.mCachedDecorInsets.mActive = true;
        }
    }

    public final void updateConfigurationAndScreenSizeDependentBehaviors() {
        Resources currentUserResources = getCurrentUserResources();
        boolean z = false;
        if (!CoreRune.FW_NAVBAR_MOVABLE_POLICY) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mBaseDisplayWidth != displayContent.mBaseDisplayHeight
                    && currentUserResources.getBoolean(R.bool.config_orderUnlockAndWake)) {
                z = true;
            }
        }
        this.mNavigationBarCanMove = z;
        DisplayRotation displayRotation = this.mDisplayContent.mDisplayRotation;
        displayRotation.getClass();
        displayRotation.mAllowSeamlessRotationDespiteNavBarMoving =
                currentUserResources.getBoolean(R.bool.config_allowTheaterModeWakeFromDock);
        boolean z2 = CoreRune.FW_TSP_STATE_CONTROLLER;
        if (z2) {
            DisplayPolicyExt displayPolicyExt = this.mExt;
            DisplayContent displayContent2 = this.mDisplayContent;
            int i = displayContent2.mBaseDisplayWidth;
            int i2 = displayContent2.mBaseDisplayHeight;
            int i3 = displayContent2.mInitialDisplayWidth;
            int i4 = displayContent2.mInitialDisplayHeight;
            displayPolicyExt.getClass();
            if (z2 && displayPolicyExt.mDisplayPolicy.mDisplayContent.isDefaultDisplay) {
                TspStateController tspStateController =
                        displayPolicyExt.mService.mExt.mTspStateController;
                TspStateController.DeviceSize deviceSize = tspStateController.mDeviceSize;
                deviceSize.width = i;
                deviceSize.height = i2;
                deviceSize.initWidth = i3;
                deviceSize.initHeight = i4;
                tspStateController.initDefaultValues();
                tspStateController.updateCustomValue();
                int i5 = deviceSize.initWidth;
                int i6 = deviceSize.initHeight;
                TspStateController.TspDebug tspDebug = tspStateController.mTspDebug;
                tspDebug.mInitDisplayWidth = i5;
                tspDebug.mInitDisplayHeight = i6;
            }
        }
    }

    public final void updateCurrentUserResources() {
        int currentUserId = this.mService.mAmInternal.getCurrentUserId();
        Context context = this.mUiContext;
        if (currentUserId == 0) {
            this.mCurrentUserResources = context.getResources();
        } else {
            LoadedApk packageInfo =
                    ActivityThread.currentActivityThread()
                            .getPackageInfo(
                                    context.getPackageName(),
                                    (CompatibilityInfo) null,
                                    0,
                                    currentUserId);
            this.mCurrentUserResources =
                    ResourcesManager.getInstance()
                            .getResources(
                                    context.getWindowContextToken(),
                                    packageInfo.getResDir(),
                                    (String[]) null,
                                    packageInfo.getOverlayDirs(),
                                    packageInfo.getOverlayPaths(),
                                    packageInfo.getApplicationInfo().sharedLibraryFiles,
                                    Integer.valueOf(this.mDisplayContent.mDisplayId),
                                    (Configuration) null,
                                    context.getResources().getCompatibilityInfo(),
                                    (ClassLoader) null,
                                    (List) null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a1, code lost:

       r0 = r13.mDecorInsets.mInfoForRotation.length - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a7, code lost:

       if (r0 < 0) goto L70;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a9, code lost:

       if (r0 == r5) goto L72;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00af, code lost:

       if (((r0 + r5) % 2) != 1) goto L42;
    */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b1, code lost:

       r3 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b4, code lost:

       if (r3 == false) goto L45;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b6, code lost:

       r8 = r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b9, code lost:

       if (r3 == false) goto L48;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00bb, code lost:

       r3 = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00be, code lost:

       r13.mDecorInsets.mInfoForRotation[r0].update(r0, r8, r3, r13.mDisplayContent);
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c9, code lost:

       r0 = r0 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bd, code lost:

       r3 = r4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b8, code lost:

       r8 = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b3, code lost:

       r3 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00cc, code lost:

       r13.mDecorInsets.mInfoForRotation[r5].set(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x009f, code lost:

       if (r10 != r9) goto L36;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateDecorInsetsInfo() {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayPolicy.updateDecorInsetsInfo():boolean");
    }

    public final void updateForceShowNavBarSettings() {
        synchronized (this.mLock) {
            this.mForceShowNavigationBarEnabled = this.mForceShowNavBarSettingsObserver.isEnabled();
            updateSystemBarAttributes();
        }
    }

    public int updateLightNavigationBarLw(int i, WindowState windowState) {
        if (windowState != null) {
            boolean z = false;
            if (intersectsAnyInsets(
                    windowState.mWindowFrames.mFrame,
                    windowState.getInsetsState(false),
                    WindowInsets.Type.navigationBars())) {
                boolean z2 = this.mIsResizingByDivider;
                if (this.mDisplayContent.isDefaultDisplay
                        && this.mService
                                .mAtmService
                                .mNaturalSwitchingController
                                .mNaturalSwitchingRunning) {
                    z = true;
                }
                return (z2 || z)
                        ? i & (-17)
                        : (windowState.isDexMode()
                                        && windowState.mActivityRecord != null
                                        && windowState.getWindowingMode() == 1
                                        && (windowState.isActivityTypeStandard()
                                                || windowState.isActivityTypeAssistant()))
                                ? (i & (-17)) | 2
                                : (i & (-17)) | (windowState.mAttrs.insetsFlags.appearance & 16);
            }
        }
        int i2 = i & (-17);
        if (windowState == null) {
            return i2;
        }
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        this.mExt.getClass();
        return DisplayPolicyExt.isUsingBlurEffect(layoutParams)
                ? i2 | (windowState.mAttrs.insetsFlags.appearance & 16)
                : i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:314:0x0200, code lost:

       if (r0.isRequestedVisible(android.view.WindowInsets.Type.navigationBars(), false) == false) goto L227;
    */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x025d, code lost:

       if (com.samsung.android.multiwindow.MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED != false) goto L210;
    */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x02a5, code lost:

       if (r6 == false) goto L226;
    */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x02ae, code lost:

       if (com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT != false) goto L153;
    */
    /* JADX WARN: Code restructure failed: missing block: B:382:0x0199, code lost:

       if (r12 != false) goto L114;
    */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x019b, code lost:

       r8 = r8 & (-3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x01a5, code lost:

       if (r12 != false) goto L114;
    */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x01ae, code lost:

       if (r25.mFreeformTaskSurfaceOverlappingWithNavBar == false) goto L129;
    */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x01b3, code lost:

       if (r0 != false) goto L114;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSystemBarAttributes() {
        /*
            Method dump skipped, instructions count: 1595
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayPolicy.updateSystemBarAttributes():void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int validateAddingWindowLw(
            android.view.WindowManager.LayoutParams r8, int r9, int r10) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayPolicy.validateAddingWindowLw(android.view.WindowManager$LayoutParams,"
                    + " int, int):int");
    }
}
