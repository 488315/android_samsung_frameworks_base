package com.android.server.wm;

import android.R;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.IAssistDataReceiver;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.TestUtilityService;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.configstore.V1_0.OptionalBool;
import android.hardware.configstore.V1_1.ISurfaceFlingerConfigs;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.InputConstants;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.SystemService;
import android.os.Trace;
import android.os.UserHandle;
import android.os.IInstalld;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.sysprop.SurfaceFlingerProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.ContentRecordingSession;
import android.view.DisplayInfo;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.ICrossWindowBlurEnabledListener;
import android.view.IDisplayChangeWindowController;
import android.view.IDisplayFoldListener;
import android.view.IDisplayWindowInsetsController;
import android.view.IDisplayWindowListener;
import android.view.IInputFilter;
import android.view.IOnKeyguardExitResult;
import android.view.IPinnedTaskListener;
import android.view.IRecentsAnimationRunner;
import android.view.IRotationWatcher;
import android.view.IScrollCaptureResponseListener;
import android.view.ISystemGestureExclusionListener;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindow;
import android.view.IWindowId;
import android.view.IWindowManager;
import android.view.IWindowSession;
import android.view.IWindowSessionCallback;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputWindowHandle;
import android.view.InsetsFrameProvider;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.MagnificationSpec;
import android.view.MotionEvent;
import android.view.RemoteAnimationAdapter;
import android.view.ScrollCaptureResponse;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.TaskTransitionSpec;
import android.view.WindowContentFrameStats;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.WindowManagerPolicyConstants;
import android.view.displayhash.DisplayHash;
import android.view.displayhash.VerifiedDisplayHash;
import android.view.inputmethod.ImeTracker;
import android.window.AddToSurfaceSyncGroupResult;
import android.window.ClientWindowFrames;
import android.window.ISurfaceSyncGroupCompletedListener;
import android.window.ITaskFpsCallback;
import android.window.ScreenCapture;
import android.window.TaskSnapshot;
import android.window.WindowContainerToken;
import android.window.WindowProviderService;
import com.android.internal.os.IResultReceiver;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardLockedStateListener;
import com.android.internal.policy.IShortcutService;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.view.WindowManagerPolicyThread;
import com.android.server.AnimationThread;
import com.android.server.DisplayThread;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.UiThread;
import com.android.server.Watchdog;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda1;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.AccessibilityController;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.DisplayAreaPolicy;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.DisplayWindowSettings;
import com.android.server.wm.EmbeddedWindowController;
import com.android.server.wm.RecentsAnimationController;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowState;
import com.android.server.wm.WindowToken;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.ShutdownThread;
import com.android.server.utils.PriorityDump;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.onehandop.IOneHandOpWatcher;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.FullScreenAppsSupportUtils;
import com.samsung.android.view.MultiResolutionChangeRequestInfo;
import com.samsung.android.view.ScreenshotResult;
import com.samsung.android.view.SemWindowManager;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class WindowManagerService extends IWindowManager.Stub implements Watchdog.Monitor, WindowManagerPolicy.WindowManagerFuncs {
    public final String ENG_HEALTH_GED_RELEASE_KEY;
    public final String ENG_HEALTH_RELEASE_KEY;
    public final Map PENDING_INTENT_AFTER_UNLOCK_ALLOW_MAP;
    public final String PLATFORM;
    public final String RAKUTEN_NAME;
    public final String RAKUTEN_PAY_KEY;
    public final String SHEALTH_NAME;
    public final String USER_HEALTH_RELEASE_KEY;
    public final AccessibilityController mAccessibilityController;
    public final IActivityManager mActivityManager;
    public final WindowManagerInternal.AppTransitionListener mActivityManagerAppTransitionNotifier;
    public final boolean mAllowAnimationsInLowPowerMode;
    public final boolean mAllowBootMessages;
    public boolean mAllowTheaterModeWakeFromLayout;
    public final ActivityManagerInternal mAmInternal;
    public final Handler mAnimationHandler;
    public final ArrayMap mAnimationTransferMap;
    public boolean mAnimationsDisabled;
    public final WindowAnimator mAnimator;
    public float mAnimatorDurationScaleSetting;
    public final AnrController mAnrController;
    public final ArrayList mAppFreezeListeners;
    public final AppOpsManager mAppOps;
    public int mAppsFreezingScreen;
    public final boolean mAssistantOnTopOfDream;
    public final ActivityTaskManagerService mAtmService;
    public final BlurController mBlurController;
    public boolean mBootAnimationStopped;
    public long mBootWaitForWindowsStartTime;
    public final BroadcastReceiver mBroadcastReceiver;
    public boolean mClientFreezingScreen;
    public final WindowManagerConstants mConstants;
    final ContentRecordingController mContentRecordingController;
    public final Context mContext;
    public int mCurrentUserId;
    public final ArrayList mDestroySurface;
    public boolean mDisableTransitionAnimation;
    public final DisplayAreaPolicy.Provider mDisplayAreaPolicyProvider;
    public IDisplayChangeWindowController mDisplayChangeController;
    public final IBinder.DeathRecipient mDisplayChangeControllerDeath;
    public boolean mDisplayEnabled;
    public long mDisplayFreezeTime;
    public boolean mDisplayFrozen;
    public final DisplayHashController mDisplayHashController;
    public volatile Map mDisplayImePolicyCache;
    public final DisplayManager mDisplayManager;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public final DisplayWindowListenerController mDisplayNotificationController;
    public boolean mDisplayReady;
    public final DisplayWindowSettings mDisplayWindowSettings;
    public final DisplayWindowSettingsProvider mDisplayWindowSettingsProvider;
    public final DragDropController mDragDropController;
    public final long mDrawLockTimeoutMillis;
    public final EmbeddedWindowController mEmbeddedWindowController;
    public EmulatorDisplayOverlay mEmulatorDisplayOverlay;
    public int mEnterAnimId;
    public boolean mEventDispatchingEnabled;
    public int mExitAnimId;
    public WindowManagerServiceExt mExt;
    public boolean mFocusMayChange;
    public InputTarget mFocusedInputTarget;
    public boolean mForceDesktopModeOnExternalDisplays;
    public boolean mForceDisplayEnabled;
    public final ArrayList mForceRemoves;
    public int mFrozenDisplayId;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public final HandlerC2998H f1749mH;
    public boolean mHardKeyboardAvailable;
    public WindowManagerInternal.OnHardKeyboardStatusChangeListener mHardKeyboardStatusChangeListener;
    public boolean mHasHdrSupport;
    public final boolean mHasPermanentDpad;
    public boolean mHasWideColorGamutSupport;
    public ArrayList mHidingNonSystemOverlayWindows;
    public final HighRefreshRateDenylist mHighRefreshRateDenylist;
    public ImeTargetChangeListener mImeTargetChangeListener;
    public final InputManagerService mInputManager;
    public final InputManagerCallback mInputManagerCallback;
    public final HashMap mInputToWindowMap;
    public boolean mIsFakeTouchDevice;
    public boolean mIsIgnoreOrientationRequestDisabled;
    public boolean mIsPc;
    public boolean mIsTouchDevice;
    public final KeyguardDisableHandler mKeyguardDisableHandler;
    public String mLastANRState;
    public int mLastDisplayFreezeDuration;
    public Object mLastFinishedFreezeSource;
    public final LatencyTracker mLatencyTracker;
    public final LetterboxConfiguration mLetterboxConfiguration;
    public final boolean mLimitedAlphaCompositing;
    public final int mMaxUiWidth;
    public volatile float mMaximumObscuringOpacityForTouch;
    public MousePositionTracker mMousePositionTracker;
    public final SparseIntArray mOrientationMapping;
    boolean mPerDisplayFocusEnabled;
    public final PackageManagerInternal mPmInternal;
    public boolean mPointerLocationEnabled;
    WindowManagerPolicy mPolicy;
    public final PossibleDisplayInfoMapper mPossibleDisplayInfoMapper;
    public PowerManager mPowerManager;
    public PowerManagerInternal mPowerManagerInternal;
    public final PriorityDump.PriorityDumper mPriorityDumper;
    public RecentsAnimationController mRecentsAnimationController;
    public final ArrayList mResizingWindows;
    public final RootWindowContainer mRoot;
    public final RotationWatcherController mRotationWatcherController;
    public boolean mSafeMode;
    public final PowerManager.WakeLock mScreenFrozenLock;
    public final ArraySet mSessions;
    public SettingsObserver mSettingsObserver;
    public boolean mShowAlertWindowNotifications;
    public boolean mShowingBootMessages;
    boolean mSkipActivityRelaunchWhenDocking;
    public final SnapshotController mSnapshotController;
    public final StartingSurfaceController mStartingSurfaceController;
    public StrictModeFlash mStrictModeFlash;
    public SurfaceAnimationRunner mSurfaceAnimationRunner;
    public Function mSurfaceControlFactory;
    public final SurfaceSyncGroupController mSurfaceSyncGroupController;
    public boolean mSwitchingUser;
    public final BLASTSyncEngine mSyncEngine;
    public boolean mSystemBooted;
    public boolean mSystemReady;
    public final TaskFpsCallbackController mTaskFpsCallbackController;
    public final TaskPositioningController mTaskPositioningController;
    public final TaskSnapshotController mTaskSnapshotController;
    public final TaskSystemBarsListenerController mTaskSystemBarsListenerController;
    public TaskTransitionSpec mTaskTransitionSpec;
    public WindowContentFrameStats mTempWindowRenderStats;
    public final TestUtilityService mTestUtilityService;
    public final Rect mTmpRect;
    public final SurfaceControl.Transaction mTransaction;
    public Supplier mTransactionFactory;
    public int mTransactionSequence;
    public float mTransitionAnimationScaleSetting;
    public final TransitionTracer mTransitionTracer;
    public final UserManagerInternal mUmInternal;
    public final boolean mUseBLAST;
    public ViewServer mViewServer;
    public final ArrayMap mWaitingForDrawnCallbacks;
    public final WallpaperVisibilityListeners mWallpaperVisibilityListeners;
    public Watermark mWatermark;
    public float mWindowAnimationScaleSetting;
    public final ArrayList mWindowChangeListeners;
    final WindowContextListenerController mWindowContextListenerController;
    public final HashMap mWindowMap;
    public final WindowSurfacePlacer mWindowPlacerLocked;
    public final WindowTracing mWindowTracing;
    public boolean mWindowsChanged;
    public int mWindowsFreezingScreen;
    public int mWindowsInsetsChanged;
    public static final int MY_PID = Process.myPid();
    public static final int MY_UID = Process.myUid();
    public static final boolean sEnableShellTransitions = SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);
    public static final boolean ENABLE_FIXED_ROTATION_TRANSFORM = SystemProperties.getBoolean("persist.wm.fixed_rotation_transform", true);
    public static WindowManagerThreadPriorityBooster sThreadPriorityBooster = new WindowManagerThreadPriorityBooster();
    public final RemoteCallbackList mKeyguardLockedStateListeners = new RemoteCallbackList();
    public boolean mDispatchedKeyguardLockedState = false;
    public int mVr2dDisplayId = -1;
    public boolean mVrModeEnabled = false;
    public final Map mKeyInterceptionInfoForToken = Collections.synchronizedMap(new ArrayMap());
    public final IVrStateCallbacks mVrStateCallbacks = new C29861();

    public interface AppFreezeListener {
        void onAppFreezeTimeout();
    }

    public interface WindowChangeListener {
        void focusChanged();

        void windowsChanged();
    }

    public static boolean excludeWindowTypeFromTapOutTask(int i) {
        return i == 2000 || i == 2012 || i == 2040 || i == 2019 || i == 2020;
    }

    public void changeDisplayScale(MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) {
    }

    public void endProlongedAnimations() {
    }

    public int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) {
        return 0;
    }

    public int getDockedStackSide() {
        return 0;
    }

    public int getSupportsFlexPanel(int i, String str) {
        return 2;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public boolean isFolded() {
        return false;
    }

    public boolean isTableMode() {
        return false;
    }

    public void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) {
    }

    public void setAppContinuityMode(int i, String str, boolean z) {
    }

    public void setSupportsFlexPanel(int i, String str, boolean z) {
    }

    public void setTableModeEnabled(boolean z) {
    }

    public void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) {
    }

    /* renamed from: com.android.server.wm.WindowManagerService$1 */
    public class C29861 extends IVrStateCallbacks.Stub {
        public C29861() {
        }

        public void onVrStateChanged(final boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService windowManagerService = WindowManagerService.this;
                    windowManagerService.mVrModeEnabled = z;
                    windowManagerService.mRoot.forAllDisplayPolicies(new Consumer() { // from class: com.android.server.wm.WindowManagerService$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((DisplayPolicy) obj).onVrStateChangedLw(z);
                        }
                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mDisplayChangeController = null;
    }

    final class SettingsObserver extends ContentObserver {
        public final Uri mAnimationDurationScaleUri;
        public final Uri mDevEnableNonResizableMultiWindowUri;
        public final Uri mDisplayInversionEnabledUri;
        public final Uri mDisplaySettingsPathUri;
        public final Uri mForceDesktopModeOnExternalDisplaysUri;
        public final Uri mForceResizableUri;
        public final Uri mFreeformWindowUri;
        public final Uri mGestureImmersiveModeConfirmationsUri;
        public final Uri mImmersiveModeConfirmationsUri;
        public final Uri mMaximumObscuringOpacityForTouchUri;
        public final Uri mPointerLocationUri;
        public final Uri mPolicyControlUri;
        public final Uri mScreenModeUri;
        public final Uri mTransitionAnimationScaleUri;
        public final Uri mWindowAnimationScaleUri;

        public SettingsObserver() {
            super(new Handler());
            Uri uriFor = Settings.Secure.getUriFor("accessibility_display_inversion_enabled");
            this.mDisplayInversionEnabledUri = uriFor;
            Uri uriFor2 = Settings.Global.getUriFor("window_animation_scale");
            this.mWindowAnimationScaleUri = uriFor2;
            Uri uriFor3 = Settings.Global.getUriFor("transition_animation_scale");
            this.mTransitionAnimationScaleUri = uriFor3;
            Uri uriFor4 = Settings.Global.getUriFor("animator_duration_scale");
            this.mAnimationDurationScaleUri = uriFor4;
            Uri uriFor5 = Settings.Secure.getUriFor("immersive_mode_confirmations");
            this.mImmersiveModeConfirmationsUri = uriFor5;
            Uri uriFor6 = Settings.Secure.getUriFor("gesture_immersive_mode_confirmations");
            this.mGestureImmersiveModeConfirmationsUri = uriFor6;
            Uri uriFor7 = Settings.Global.getUriFor("policy_control");
            this.mPolicyControlUri = uriFor7;
            Uri uriFor8 = Settings.System.getUriFor("pointer_location");
            this.mPointerLocationUri = uriFor8;
            Uri uriFor9 = Settings.Global.getUriFor("force_desktop_mode_on_external_displays");
            this.mForceDesktopModeOnExternalDisplaysUri = uriFor9;
            Uri uriFor10 = Settings.Global.getUriFor("enable_freeform_support");
            this.mFreeformWindowUri = uriFor10;
            Uri uriFor11 = Settings.Global.getUriFor("force_resizable_activities");
            this.mForceResizableUri = uriFor11;
            Uri uriFor12 = Settings.Global.getUriFor("enable_non_resizable_multi_window");
            this.mDevEnableNonResizableMultiWindowUri = uriFor12;
            Uri uriFor13 = Settings.Global.getUriFor("wm_display_settings_path");
            this.mDisplaySettingsPathUri = uriFor13;
            Uri uriFor14 = Settings.Global.getUriFor("maximum_obscuring_opacity_for_touch");
            this.mMaximumObscuringOpacityForTouchUri = uriFor14;
            Uri uriFor15 = Settings.System.getUriFor("screen_mode_setting");
            this.mScreenModeUri = uriFor15;
            ContentResolver contentResolver = WindowManagerService.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            contentResolver.registerContentObserver(uriFor2, false, this, -1);
            contentResolver.registerContentObserver(uriFor3, false, this, -1);
            contentResolver.registerContentObserver(uriFor4, false, this, -1);
            contentResolver.registerContentObserver(uriFor5, false, this, -1);
            contentResolver.registerContentObserver(uriFor6, false, this, -1);
            contentResolver.registerContentObserver(uriFor7, false, this, -1);
            contentResolver.registerContentObserver(uriFor8, false, this, -1);
            contentResolver.registerContentObserver(uriFor9, false, this, -1);
            contentResolver.registerContentObserver(uriFor10, false, this, -1);
            contentResolver.registerContentObserver(uriFor11, false, this, -1);
            contentResolver.registerContentObserver(uriFor12, false, this, -1);
            contentResolver.registerContentObserver(uriFor13, false, this, -1);
            contentResolver.registerContentObserver(uriFor14, false, this, -1);
            if (CoreRune.FW_SCREEN_MODE_SETTING) {
                contentResolver.registerContentObserver(uriFor15, false, this, -1);
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            int i = 1;
            if (this.mImmersiveModeConfirmationsUri.equals(uri) || this.mGestureImmersiveModeConfirmationsUri.equals(uri) || this.mPolicyControlUri.equals(uri)) {
                updateSystemUiSettings(true);
                return;
            }
            if (this.mPointerLocationUri.equals(uri)) {
                updatePointerLocation();
                return;
            }
            if (this.mForceDesktopModeOnExternalDisplaysUri.equals(uri)) {
                updateForceDesktopModeOnExternalDisplays();
                return;
            }
            if (this.mFreeformWindowUri.equals(uri)) {
                updateFreeformWindowManagement();
                return;
            }
            if (this.mForceResizableUri.equals(uri)) {
                updateForceResizableTasks();
                return;
            }
            if (this.mDevEnableNonResizableMultiWindowUri.equals(uri)) {
                updateDevEnableNonResizableMultiWindow();
                return;
            }
            if (this.mDisplaySettingsPathUri.equals(uri)) {
                updateDisplaySettingsLocation();
                return;
            }
            if (this.mMaximumObscuringOpacityForTouchUri.equals(uri)) {
                updateMaximumObscuringOpacityForTouch();
                return;
            }
            if (CoreRune.FW_SCREEN_MODE_SETTING && this.mScreenModeUri.equals(uri)) {
                WindowManagerService.this.mAtmService.updateConfiguration(null);
                return;
            }
            if (this.mWindowAnimationScaleUri.equals(uri)) {
                i = 0;
            } else if (!this.mTransitionAnimationScaleUri.equals(uri)) {
                if (!this.mAnimationDurationScaleUri.equals(uri)) {
                    return;
                } else {
                    i = 2;
                }
            }
            WindowManagerService.this.f1749mH.sendMessage(WindowManagerService.this.f1749mH.obtainMessage(51, i, 0));
        }

        public void loadSettings() {
            updateSystemUiSettings(false);
            updatePointerLocation();
            updateMaximumObscuringOpacityForTouch();
        }

        public void updateMaximumObscuringOpacityForTouch() {
            ContentResolver contentResolver = WindowManagerService.this.mContext.getContentResolver();
            WindowManagerService.this.mMaximumObscuringOpacityForTouch = Settings.Global.getFloat(contentResolver, "maximum_obscuring_opacity_for_touch", 0.8f);
            if (WindowManagerService.this.mMaximumObscuringOpacityForTouch < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || WindowManagerService.this.mMaximumObscuringOpacityForTouch > 1.0f) {
                WindowManagerService.this.mMaximumObscuringOpacityForTouch = 0.8f;
            }
        }

        public void updateSystemUiSettings(boolean z) {
            boolean z2;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (z) {
                        z2 = WindowManagerService.this.getDefaultDisplayContentLocked().getDisplayPolicy().onSystemUiSettingsChanged();
                    } else {
                        WindowManagerService windowManagerService = WindowManagerService.this;
                        ImmersiveModeConfirmation.loadSetting(windowManagerService.mCurrentUserId, windowManagerService.mContext);
                        WindowManagerService windowManagerService2 = WindowManagerService.this;
                        ImmersiveModeConfirmation.loadGestureSetting(windowManagerService2.mCurrentUserId, windowManagerService2.mContext);
                        z2 = false;
                    }
                    if (z2 | PolicyControl.reloadFromSetting(WindowManagerService.this.mContext)) {
                        WindowManagerService.this.mWindowPlacerLocked.requestTraversal();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public void updatePointerLocation() {
            boolean z = Settings.System.getIntForUser(WindowManagerService.this.mContext.getContentResolver(), "pointer_location", 0, -2) != 0;
            WindowManagerService windowManagerService = WindowManagerService.this;
            if (windowManagerService.mPointerLocationEnabled == z) {
                return;
            }
            windowManagerService.mPointerLocationEnabled = z;
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mRoot.forAllDisplayPolicies(new Consumer() { // from class: com.android.server.wm.WindowManagerService$SettingsObserver$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.SettingsObserver.this.lambda$updatePointerLocation$0((DisplayPolicy) obj);
                        }
                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updatePointerLocation$0(DisplayPolicy displayPolicy) {
            displayPolicy.setPointerLocationEnabled(WindowManagerService.this.mPointerLocationEnabled);
        }

        public void updateForceDesktopModeOnExternalDisplays() {
            boolean z = Settings.Global.getInt(WindowManagerService.this.mContext.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0;
            WindowManagerService windowManagerService = WindowManagerService.this;
            if (windowManagerService.mForceDesktopModeOnExternalDisplays == z) {
                return;
            }
            windowManagerService.setForceDesktopModeOnExternalDisplays(z);
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x001f, code lost:
        
            if (android.provider.Settings.Global.getInt(r0, "enable_freeform_support", 0) != 0) goto L6;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void updateFreeformWindowManagement() {
            ContentResolver contentResolver = WindowManagerService.this.mContext.getContentResolver();
            boolean z = WindowManagerService.this.mContext.getPackageManager().hasSystemFeature("android.software.freeform_window_management");
            WindowManagerService windowManagerService = WindowManagerService.this;
            ActivityTaskManagerService activityTaskManagerService = windowManagerService.mAtmService;
            if (activityTaskManagerService.mSupportsFreeformWindowManagement != z) {
                activityTaskManagerService.mSupportsFreeformWindowManagement = z;
                WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowManagerService.this.mRoot.onSettingsRetrieved();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public void updateForceResizableTasks() {
            WindowManagerService.this.mAtmService.mForceResizableActivities = Settings.Global.getInt(WindowManagerService.this.mContext.getContentResolver(), "force_resizable_activities", 0) != 0;
        }

        public void updateDevEnableNonResizableMultiWindow() {
            WindowManagerService.this.mAtmService.mDevEnableNonResizableMultiWindow = Settings.Global.getInt(WindowManagerService.this.mContext.getContentResolver(), "enable_non_resizable_multi_window", 0) != 0;
        }

        public void updateDisplaySettingsLocation() {
            String string = Settings.Global.getString(WindowManagerService.this.mContext.getContentResolver(), "wm_display_settings_path");
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mDisplayWindowSettingsProvider.setBaseSettingsFilePath(string);
                    WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$SettingsObserver$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.SettingsObserver.this.lambda$updateDisplaySettingsLocation$1((DisplayContent) obj);
                        }
                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateDisplaySettingsLocation$1(DisplayContent displayContent) {
            WindowManagerService.this.mDisplayWindowSettings.applySettingsToDisplayLocked(displayContent);
            displayContent.reconfigureDisplayLocked();
        }
    }

    public static void boostPriorityForLockedSection() {
        sThreadPriorityBooster.boost();
    }

    public static void resetPriorityAfterLockedSection() {
        sThreadPriorityBooster.reset();
    }

    public void openSurfaceTransaction() {
        try {
            Trace.traceBegin(32L, "openSurfaceTransaction");
            SurfaceControl.openTransaction();
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public void closeSurfaceTransaction(String str) {
        try {
            Trace.traceBegin(32L, "closeSurfaceTransaction");
            SurfaceControl.closeTransaction();
            this.mWindowTracing.logState(str);
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public static WindowManagerService main(Context context, InputManagerService inputManagerService, boolean z, WindowManagerPolicy windowManagerPolicy, ActivityTaskManagerService activityTaskManagerService) {
        WindowManagerService main = main(context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, new DisplayWindowSettingsProvider(), new Supplier() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda25
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        }, new Function() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new SurfaceControl.Builder((SurfaceSession) obj);
            }
        });
        WindowManagerGlobal.setWindowManagerServiceForSystemProcess(main);
        return main;
    }

    public static WindowManagerService main(final Context context, final InputManagerService inputManagerService, final boolean z, final WindowManagerPolicy windowManagerPolicy, final ActivityTaskManagerService activityTaskManagerService, final DisplayWindowSettingsProvider displayWindowSettingsProvider, final Supplier supplier, final Function function) {
        final WindowManagerService[] windowManagerServiceArr = new WindowManagerService[1];
        DisplayThread.getHandler().runWithScissors(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                WindowManagerService.lambda$main$1(windowManagerServiceArr, context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, displayWindowSettingsProvider, supplier, function);
            }
        }, 0L);
        return windowManagerServiceArr[0];
    }

    public static /* synthetic */ void lambda$main$1(WindowManagerService[] windowManagerServiceArr, Context context, InputManagerService inputManagerService, boolean z, WindowManagerPolicy windowManagerPolicy, ActivityTaskManagerService activityTaskManagerService, DisplayWindowSettingsProvider displayWindowSettingsProvider, Supplier supplier, Function function) {
        windowManagerServiceArr[0] = new WindowManagerService(context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, displayWindowSettingsProvider, supplier, function);
    }

    public final void initPolicy() {
        UiThread.getHandler().runWithScissors(new Runnable() { // from class: com.android.server.wm.WindowManagerService.5
            @Override // java.lang.Runnable
            public void run() {
                WindowManagerPolicyThread.set(Thread.currentThread(), Looper.myLooper());
                WindowManagerService windowManagerService = WindowManagerService.this;
                windowManagerService.mPolicy.init(windowManagerService.mContext, windowManagerService);
                WindowManagerService.this.mExt.mPolicyExt.init();
            }
        }, 0L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new WindowManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WindowManagerService(Context context, InputManagerService inputManagerService, boolean z, WindowManagerPolicy windowManagerPolicy, ActivityTaskManagerService activityTaskManagerService, DisplayWindowSettingsProvider displayWindowSettingsProvider, Supplier supplier, Function function) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.WindowManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED")) {
                    WindowManagerService.this.mKeyguardDisableHandler.updateKeyguardEnabled(getSendingUserId());
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        this.mPriorityDumper = new PriorityDump.PriorityDumper() { // from class: com.android.server.wm.WindowManagerService.3
            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dumpCritical(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z2) {
                WindowManagerService.this.doDump(fileDescriptor, printWriter, new String[]{"-a"}, z2);
            }

            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z2) {
                WindowManagerService.this.doDump(fileDescriptor, printWriter, strArr, z2);
            }
        };
        this.mShowAlertWindowNotifications = true;
        this.mSessions = new ArraySet();
        this.mWindowMap = new HashMap();
        this.mInputToWindowMap = new HashMap();
        this.mResizingWindows = new ArrayList();
        this.mDisplayImePolicyCache = Collections.unmodifiableMap(new ArrayMap());
        this.mDestroySurface = new ArrayList();
        this.mForceRemoves = new ArrayList();
        this.mWaitingForDrawnCallbacks = new ArrayMap();
        this.mHidingNonSystemOverlayWindows = new ArrayList();
        this.mOrientationMapping = new SparseIntArray();
        this.mTmpRect = new Rect();
        this.mDisplayEnabled = false;
        this.mSystemBooted = false;
        this.mForceDisplayEnabled = false;
        this.mShowingBootMessages = false;
        this.mSystemReady = false;
        this.mBootAnimationStopped = false;
        this.mBootWaitForWindowsStartTime = -1L;
        this.mWallpaperVisibilityListeners = new WallpaperVisibilityListeners();
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        this.mDisplayChangeController = null;
        this.mDisplayChangeControllerDeath = new IBinder.DeathRecipient() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda17
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                WindowManagerService.this.lambda$new$0();
            }
        };
        this.mDisplayFrozen = false;
        this.mDisplayFreezeTime = 0L;
        this.mLastDisplayFreezeDuration = 0;
        this.mLastFinishedFreezeSource = null;
        this.mSwitchingUser = false;
        this.mWindowsFreezingScreen = 0;
        this.mClientFreezingScreen = false;
        this.mAppsFreezingScreen = 0;
        this.mWindowsInsetsChanged = 0;
        HandlerC2998H handlerC2998H = new HandlerC2998H();
        this.f1749mH = handlerC2998H;
        this.mAnimationHandler = new Handler(AnimationThread.getHandler().getLooper());
        this.mMaximumObscuringOpacityForTouch = 0.8f;
        this.mWindowContextListenerController = new WindowContextListenerController();
        this.mContentRecordingController = new ContentRecordingController();
        this.mSurfaceSyncGroupController = new SurfaceSyncGroupController();
        this.mWindowAnimationScaleSetting = 1.0f;
        this.mTransitionAnimationScaleSetting = 1.0f;
        this.mAnimatorDurationScaleSetting = 1.0f;
        this.mAnimationsDisabled = false;
        this.mPointerLocationEnabled = false;
        this.mFrozenDisplayId = -1;
        this.mAnimationTransferMap = new ArrayMap();
        this.mWindowChangeListeners = new ArrayList();
        this.mWindowsChanged = false;
        this.mActivityManagerAppTransitionNotifier = new WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.wm.WindowManagerService.4
            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionCancelledLocked(boolean z2) {
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionFinishedLocked(IBinder iBinder) {
                ActivityRecord activityRecord = WindowManagerService.this.mRoot.getActivityRecord(iBinder);
                if (activityRecord == null) {
                    return;
                }
                WindowManagerService.this.mAtmService.mDexController.notifyAppTransitionFinishedIfNeeded(activityRecord);
                TaskDisplayArea displayArea = activityRecord.getDisplayArea();
                if (displayArea != null) {
                    WindowManagerService.this.mAtmService.mFreeformController.minimizeExcessiveVisibleFreeformLocked(displayArea);
                }
                if (activityRecord.mLaunchTaskBehind && !WindowManagerService.this.isRecentsAnimationTarget(activityRecord)) {
                    WindowManagerService.this.mAtmService.mTaskSupervisor.scheduleLaunchTaskBehindComplete(activityRecord.token);
                    activityRecord.mLaunchTaskBehind = false;
                    return;
                }
                activityRecord.updateReportedVisibilityLocked();
                if (!activityRecord.mEnteringAnimation || WindowManagerService.this.isRecentsAnimationTarget(activityRecord)) {
                    return;
                }
                activityRecord.mEnteringAnimation = false;
                if (activityRecord.attachedToProcess()) {
                    try {
                        activityRecord.app.getThread().scheduleEnterAnimationComplete(activityRecord.token);
                    } catch (RemoteException unused) {
                    }
                }
            }
        };
        this.mAppFreezeListeners = new ArrayList();
        this.mInputManagerCallback = new InputManagerCallback(this);
        this.mMousePositionTracker = new MousePositionTracker();
        this.PLATFORM = "android";
        this.SHEALTH_NAME = "com.sec.android.app.shealth";
        this.RAKUTEN_NAME = "jp.co.rakuten.pay";
        this.USER_HEALTH_RELEASE_KEY = "14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a";
        this.ENG_HEALTH_RELEASE_KEY = "699b10e8636d1d5f03b5ed04b10d98898e4e292ba42d4a371bb546f8eeb42650";
        this.ENG_HEALTH_GED_RELEASE_KEY = "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6";
        this.RAKUTEN_PAY_KEY = "b0c08d3318f7c6f5be0c62f47cab1b59f5f5f13d7da0d547d041fb51cc20b0ec";
        this.PENDING_INTENT_AFTER_UNLOCK_ALLOW_MAP = "user".equals(Build.TYPE) ? Collections.unmodifiableMap(new HashMap() { // from class: com.android.server.wm.WindowManagerService.11
            {
                put("com.sec.android.app.shealth", new ArrayList(Arrays.asList("14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a")));
                put("jp.co.rakuten.pay", new ArrayList(Collections.singletonList("b0c08d3318f7c6f5be0c62f47cab1b59f5f5f13d7da0d547d041fb51cc20b0ec")));
            }
        }) : Collections.unmodifiableMap(new HashMap() { // from class: com.android.server.wm.WindowManagerService.12
            {
                put("com.sec.android.app.shealth", new ArrayList(Arrays.asList("699b10e8636d1d5f03b5ed04b10d98898e4e292ba42d4a371bb546f8eeb42650", "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6")));
                put("jp.co.rakuten.pay", new ArrayList(Collections.singletonList("b0c08d3318f7c6f5be0c62f47cab1b59f5f5f13d7da0d547d041fb51cc20b0ec")));
            }
        });
        LockGuard.installLock(this, 5);
        this.mGlobalLock = activityTaskManagerService.getGlobalLock();
        this.mAtmService = activityTaskManagerService;
        this.mContext = context;
        this.mIsPc = context.getPackageManager().hasSystemFeature("android.hardware.type.pc");
        this.mAllowBootMessages = z;
        this.mLimitedAlphaCompositing = context.getResources().getBoolean(R.bool.config_permissionsIndividuallyControlled);
        this.mHasPermanentDpad = context.getResources().getBoolean(R.bool.config_enable_emergency_call_while_sim_locked);
        this.mDrawLockTimeoutMillis = context.getResources().getInteger(R.integer.config_fingerprintMaxTemplatesPerUser);
        this.mAllowAnimationsInLowPowerMode = context.getResources().getBoolean(R.bool.config_allowAutoBrightnessWhileDozing);
        this.mMaxUiWidth = context.getResources().getInteger(R.integer.config_networkAvoidBadWifi);
        this.mDisableTransitionAnimation = context.getResources().getBoolean(R.bool.config_customBugreport);
        this.mPerDisplayFocusEnabled = context.getResources().getBoolean(R.bool.config_perDisplayFocusEnabled);
        this.mAssistantOnTopOfDream = context.getResources().getBoolean(R.bool.config_assistantOnTopOfDream);
        this.mSkipActivityRelaunchWhenDocking = context.getResources().getBoolean(R.bool.config_quickSettingsSupported);
        this.mLetterboxConfiguration = new LetterboxConfiguration(ActivityThread.currentActivityThread().getSystemUiContext());
        this.mInputManager = inputManagerService;
        DisplayManagerInternal displayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mPossibleDisplayInfoMapper = new PossibleDisplayInfoMapper(displayManagerInternal);
        this.mSurfaceControlFactory = function;
        this.mTransactionFactory = supplier;
        this.mTransaction = (SurfaceControl.Transaction) supplier.get();
        this.mPolicy = windowManagerPolicy;
        this.mAnimator = new WindowAnimator(this);
        this.mRoot = new RootWindowContainer(this);
        this.mExt = new WindowManagerServiceExt(context, this);
        ContentResolver contentResolver = context.getContentResolver();
        this.mUseBLAST = Settings.Global.getInt(contentResolver, "use_blast_adapter_vr", 1) == 1;
        this.mSyncEngine = new BLASTSyncEngine(this);
        this.mWindowPlacerLocked = new WindowSurfacePlacer(this);
        SnapshotController snapshotController = new SnapshotController(this);
        this.mSnapshotController = snapshotController;
        this.mTaskSnapshotController = snapshotController.mTaskSnapshotController;
        this.mWindowTracing = WindowTracing.createDefaultAndStartLooper(this, Choreographer.getInstance());
        this.mTransitionTracer = new TransitionTracer();
        LocalServices.addService(WindowManagerPolicy.class, this.mPolicy);
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mKeyguardDisableHandler = KeyguardDisableHandler.create(context, this.mPolicy, handlerC2998H);
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        PowerManagerInternal powerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mPowerManagerInternal = powerManagerInternal;
        if (powerManagerInternal != null) {
            powerManagerInternal.registerLowPowerModeObserver(new PowerManagerInternal.LowPowerModeListener() { // from class: com.android.server.wm.WindowManagerService.6
                public int getServiceType() {
                    return 3;
                }

                public void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                    WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            boolean z2 = powerSaveState.batterySaverEnabled;
                            if (WindowManagerService.this.mAnimationsDisabled != z2) {
                                WindowManagerService windowManagerService = WindowManagerService.this;
                                if (!windowManagerService.mAllowAnimationsInLowPowerMode) {
                                    windowManagerService.mAnimationsDisabled = z2;
                                    WindowManagerService.this.dispatchNewAnimatorScaleLocked(null);
                                }
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            });
            this.mAnimationsDisabled = this.mPowerManagerInternal.getLowPowerState(3).batterySaverEnabled;
        }
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "SCREEN_FROZEN");
        this.mScreenFrozenLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        this.mRotationWatcherController = new RotationWatcherController(this);
        this.mDisplayNotificationController = new DisplayWindowListenerController(this);
        this.mTaskSystemBarsListenerController = new TaskSystemBarsListenerController();
        this.mActivityManager = ActivityManager.getService();
        this.mAmInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mUmInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mAppOps = appOpsManager;
        AppOpsManager.OnOpChangedInternalListener onOpChangedInternalListener = new AppOpsManager.OnOpChangedInternalListener() { // from class: com.android.server.wm.WindowManagerService.7
            public void onOpChanged(int i, String str) {
                WindowManagerService.this.updateAppOpsState();
            }
        };
        appOpsManager.startWatchingMode(24, (String) null, (AppOpsManager.OnOpChangedListener) onOpChangedInternalListener);
        appOpsManager.startWatchingMode(45, (String) null, (AppOpsManager.OnOpChangedListener) onOpChangedInternalListener);
        this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mTestUtilityService = (TestUtilityService) LocalServices.getService(TestUtilityService.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        context.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.wm.WindowManagerService.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                WindowManagerService.this.updateHiddenWhileSuspendedState(new ArraySet(Arrays.asList(stringArrayExtra)), "android.intent.action.PACKAGES_SUSPENDED".equals(intent.getAction()));
            }
        }, UserHandle.ALL, intentFilter, null, null);
        this.mWindowAnimationScaleSetting = getWindowAnimationScaleSetting();
        this.mTransitionAnimationScaleSetting = getTransitionAnimationScaleSetting();
        setAnimatorDurationScale(getAnimatorDurationScaleSetting());
        this.mForceDesktopModeOnExternalDisplays = Settings.Global.getInt(contentResolver, "force_desktop_mode_on_external_displays", 0) != 0;
        String string = Settings.Global.getString(contentResolver, "wm_display_settings_path");
        this.mDisplayWindowSettingsProvider = displayWindowSettingsProvider;
        if (string != null) {
            displayWindowSettingsProvider.setBaseSettingsFilePath(string);
        }
        this.mDisplayWindowSettings = new DisplayWindowSettings(this, displayWindowSettingsProvider);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter2, null, null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.DEVICE_LOCKED_CHANGED");
        context.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.wm.WindowManagerService.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (SemPersonaManager.isSecureFolderId(intExtra)) {
                    WindowManagerService windowManagerService = WindowManagerService.this;
                    windowManagerService.updateHiddenWhileProfileLockedState(intExtra, windowManagerService.mAmInternal.shouldConfirmCredentials(intExtra));
                }
            }
        }, UserHandle.ALL, intentFilter3, null, null);
        this.mLatencyTracker = LatencyTracker.getInstance(context);
        this.mSettingsObserver = new SettingsObserver();
        this.mSurfaceAnimationRunner = new SurfaceAnimationRunner(this.mTransactionFactory, this.mPowerManagerInternal);
        this.mAllowTheaterModeWakeFromLayout = context.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromWindowLayout);
        this.mTaskPositioningController = new TaskPositioningController(this);
        this.mDragDropController = new DragDropController(this, handlerC2998H.getLooper());
        this.mHighRefreshRateDenylist = HighRefreshRateDenylist.create(context.getResources());
        WindowManagerConstants windowManagerConstants = new WindowManagerConstants(this, DeviceConfigInterface.REAL);
        this.mConstants = windowManagerConstants;
        windowManagerConstants.start(new HandlerExecutor(handlerC2998H));
        LocalServices.addService(WindowManagerInternal.class, new LocalService());
        LocalServices.addService(ImeTargetVisibilityPolicy.class, new ImeTargetVisibilityPolicyImpl());
        this.mEmbeddedWindowController = new EmbeddedWindowController(activityTaskManagerService);
        this.mDisplayAreaPolicyProvider = DisplayAreaPolicy.Provider.fromResources(context.getResources());
        this.mDisplayHashController = new DisplayHashController(context);
        setGlobalShadowSettings();
        this.mAnrController = new AnrController(this);
        this.mStartingSurfaceController = new StartingSurfaceController(this);
        this.mBlurController = new BlurController(context, this.mPowerManager);
        this.mTaskFpsCallbackController = new TaskFpsCallbackController(context);
        this.mAccessibilityController = new AccessibilityController(this);
    }

    public DisplayAreaPolicy.Provider getDisplayAreaPolicyProvider() {
        return this.mDisplayAreaPolicyProvider;
    }

    public final void setGlobalShadowSettings() {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.Lighting, 0, 0);
        float dimension = obtainStyledAttributes.getDimension(3, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        float dimension2 = obtainStyledAttributes.getDimension(4, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        float dimension3 = obtainStyledAttributes.getDimension(2, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        float f = obtainStyledAttributes.getFloat(0, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        float f2 = obtainStyledAttributes.getFloat(1, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        obtainStyledAttributes.recycle();
        SurfaceControl.setGlobalShadowSettings(new float[]{DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f}, new float[]{DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f2}, dimension, dimension2, dimension3);
    }

    public final float getTransitionAnimationScaleSetting() {
        return WindowManager.fixScale(Settings.Global.getFloat(this.mContext.getContentResolver(), "transition_animation_scale", this.mContext.getResources().getFloat(R.dimen.chooser_preview_width)));
    }

    public final float getAnimatorDurationScaleSetting() {
        return WindowManager.fixScale(Settings.Global.getFloat(this.mContext.getContentResolver(), "animator_duration_scale", this.mAnimatorDurationScaleSetting));
    }

    public final float getWindowAnimationScaleSetting() {
        return WindowManager.fixScale(Settings.Global.getFloat(this.mContext.getContentResolver(), "window_animation_scale", this.mWindowAnimationScaleSetting));
    }

    public void onInitReady() {
        initPolicy();
        Watchdog.getInstance().addMonitor(this);
        createWatermark();
        showEmulatorDisplayOverlayIfNeeded();
    }

    public InputManagerCallback getInputManagerCallback() {
        return this.mInputManagerCallback;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException) && ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.wtf(ProtoLogGroup.WM_ERROR, 371641947, 0, "Window Manager Crash %s", new Object[]{String.valueOf(e)});
            }
            throw e;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:148:0x0552, code lost:
    
        if (r8.mOwnerUid != r14) goto L289;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x049f A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x04be A[Catch: all -> 0x0831, TRY_ENTER, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x051f A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x052a A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x056e A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x05df  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x05e7 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x05f2 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0629 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0642  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0657  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x06c8 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x06ef A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x06f7 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x06fd A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0712 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x071d A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x073e A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0752 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0776 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x078f A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x07c0 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x07cf A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x07d6 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x07f2 A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:256:0x080b A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x067d A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x068f A[Catch: all -> 0x0831, TryCatch #0 {all -> 0x0831, blocks: (B:103:0x0498, B:105:0x049f, B:107:0x04a3, B:108:0x04b9, B:111:0x04be, B:113:0x04c4, B:115:0x04c8, B:116:0x04d3, B:120:0x04d9, B:122:0x050a, B:127:0x0513, B:131:0x051f, B:134:0x052a, B:136:0x0532, B:138:0x0536, B:139:0x0540, B:143:0x0547, B:145:0x054c, B:147:0x0550, B:149:0x0566, B:151:0x056e, B:153:0x0578, B:155:0x0586, B:157:0x058a, B:158:0x05a9, B:160:0x05af, B:165:0x05db, B:168:0x05e3, B:170:0x05e7, B:171:0x05ec, B:173:0x05f2, B:174:0x05f7, B:176:0x0629, B:177:0x063a, B:180:0x0645, B:184:0x065b, B:186:0x0662, B:188:0x06be, B:190:0x06c8, B:192:0x06d0, B:193:0x06d9, B:195:0x06ef, B:196:0x06f1, B:198:0x06f7, B:199:0x06f9, B:201:0x06fd, B:203:0x0705, B:205:0x0712, B:209:0x071d, B:211:0x0726, B:212:0x0735, B:214:0x073e, B:215:0x0747, B:217:0x0752, B:219:0x0776, B:221:0x077c, B:223:0x0780, B:225:0x0788, B:226:0x078b, B:228:0x078f, B:230:0x0797, B:232:0x07a7, B:234:0x07ad, B:236:0x07b7, B:237:0x07ba, B:239:0x07c0, B:242:0x07c9, B:244:0x07cf, B:246:0x07d6, B:247:0x07d9, B:249:0x07f2, B:251:0x0807, B:252:0x0811, B:253:0x0817, B:256:0x080b, B:259:0x0703, B:261:0x067d, B:263:0x0687, B:267:0x068f, B:269:0x0696, B:270:0x06a2, B:272:0x06a8, B:273:0x06af, B:275:0x06b7, B:281:0x05bb, B:283:0x0554, B:431:0x082c, B:428:0x0821, B:429:0x0828), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0694  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0679  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0644  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x05e2  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0564  */
    /* JADX WARN: Type inference failed for: r5v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int addWindow(Session session, IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) {
        boolean z;
        WindowState windowState;
        int i5;
        int i6;
        int i7;
        boolean z2;
        boolean z3;
        int i8;
        int i9;
        DisplayContent displayContent;
        int[] iArr;
        WindowState windowState2;
        int i10;
        int i11;
        int i12;
        IBinder iBinder;
        ActivityRecord activityRecord;
        WindowToken windowToken;
        boolean z4;
        ActivityRecord activityRecord2;
        WindowState windowState3;
        boolean z5;
        boolean z6;
        ?? r5;
        DisplayContent displayContent2;
        int i13;
        int i14;
        int i15;
        int i16;
        WindowState windowState4;
        ActivityRecord activityRecord3;
        boolean z7;
        int adjustCutoutMode;
        ActivityRecord activityRecord4;
        boolean z8;
        boolean z9;
        ActivityRecord activityRecord5;
        boolean z10;
        int i17;
        WindowToken build;
        array.set((InsetsSourceControl[]) null);
        int[] iArr2 = new int[1];
        boolean z11 = (layoutParams.privateFlags & 1048576) != 0;
        int checkAddPermission = this.mPolicy.checkAddPermission(layoutParams.type, z11, layoutParams.packageName, iArr2);
        if (checkAddPermission != 0) {
            return checkAddPermission;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i18 = layoutParams.type;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                try {
                    if (!this.mDisplayReady) {
                        throw new IllegalStateException("Display has not been initialialized");
                    }
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2, layoutParams.token);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -861859917, 1, "Attempted to add window to a display that does not exist: %d. Aborting.", new Object[]{Long.valueOf(i2)});
                        }
                        resetPriorityAfterLockedSection();
                        return -9;
                    }
                    if (!displayContentOrCreate.hasAccess(session.mUid)) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 435494046, 1, "Attempted to add window to a display for which the application does not have access: %d.  Aborting.", new Object[]{Long.valueOf(displayContentOrCreate.getDisplayId())});
                        }
                        resetPriorityAfterLockedSection();
                        return -9;
                    }
                    if (this.mWindowMap.containsKey(iWindow.asBinder())) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -507657818, 0, "Window %s is already added", new Object[]{String.valueOf(iWindow)});
                        }
                        resetPriorityAfterLockedSection();
                        return -5;
                    }
                    if (i18 < 1000 || i18 > 1999) {
                        z = z11;
                        windowState = null;
                    } else {
                        z = z11;
                        windowState = windowForClientLocked((Session) null, layoutParams.token, false);
                        if (windowState == null) {
                            if (ProtoLogCache.WM_ERROR_enabled) {
                                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 631792420, 0, "Attempted to add window with token that is not a window: %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                            }
                            resetPriorityAfterLockedSection();
                            return -2;
                        }
                        int i19 = windowState.mAttrs.type;
                        if (i19 >= 1000 && i19 <= 1999) {
                            if (ProtoLogCache.WM_ERROR_enabled) {
                                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -2072089308, 0, "Attempted to add window with token that is a sub-window: %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                            }
                            resetPriorityAfterLockedSection();
                            return -2;
                        }
                    }
                    if (i18 == 2030 && !displayContentOrCreate.isPrivate()) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -784959154, 0, "Attempted to add private presentation window to a non-private display.  Aborting.", (Object[]) null);
                        }
                        resetPriorityAfterLockedSection();
                        return -8;
                    }
                    if (i18 == 2037 && !displayContentOrCreate.getDisplay().isPublicPresentation()) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1670695197, 0, "Attempted to add presentation window to a non-suitable display.  Aborting.", (Object[]) null);
                        }
                        resetPriorityAfterLockedSection();
                        return -9;
                    }
                    int userId = UserHandle.getUserId(session.mUid);
                    if (i3 != userId) {
                        try {
                            i5 = i18;
                            i6 = callingPid;
                            this.mAmInternal.handleIncomingUser(callingPid, callingUid, i3, false, 0, (String) null, (String) null);
                            i7 = i3;
                            z2 = true;
                            z3 = false;
                        } catch (Exception unused) {
                            if (ProtoLogCache.WM_ERROR_enabled) {
                                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 315395835, 1, "Trying to add window with invalid user=%d", new Object[]{Long.valueOf(i3)});
                            }
                            resetPriorityAfterLockedSection();
                            return -11;
                        }
                    } else {
                        i5 = i18;
                        i6 = callingPid;
                        z2 = true;
                        z3 = false;
                        i7 = userId;
                    }
                    boolean z12 = windowState != null ? z2 : z3;
                    WindowToken windowToken2 = displayContentOrCreate.getWindowToken(z12 ? windowState.mAttrs.token : layoutParams.token);
                    int i20 = z12 ? windowState.mAttrs.type : i5;
                    IBinder iBinder2 = layoutParams.mWindowContextToken;
                    if (windowToken2 == null) {
                        i9 = i6;
                        boolean z13 = z;
                        displayContent = displayContentOrCreate;
                        iArr = iArr2;
                        windowState2 = windowState;
                        if (!unprivilegedAppCanCreateTokenWith(windowState, callingUid, i5, i20, layoutParams.token, layoutParams.packageName)) {
                            resetPriorityAfterLockedSection();
                            return -1;
                        }
                        if (z12) {
                            build = windowState2.mToken;
                            i8 = i5;
                        } else if (this.mWindowContextListenerController.hasListener(iBinder2)) {
                            IBinder iBinder3 = layoutParams.token;
                            if (iBinder3 == null) {
                                iBinder3 = iBinder2;
                            }
                            i8 = i5;
                            build = new WindowToken.Builder(this, iBinder3, i8).setDisplayContent(displayContent).setOwnerCanManageAppTokens(session.mCanAddInternalSystemWindow).setRoundedCornerOverlay(z13).setFromClientToken(true).setOptions(this.mWindowContextListenerController.getOptions(iBinder2)).build();
                        } else {
                            i8 = i5;
                            IBinder iBinder4 = layoutParams.token;
                            if (iBinder4 == null) {
                                iBinder4 = iWindow.asBinder();
                            }
                            build = new WindowToken.Builder(this, iBinder4, i8).setDisplayContent(displayContent).setOwnerCanManageAppTokens(session.mCanAddInternalSystemWindow).setRoundedCornerOverlay(z13).build();
                        }
                        windowToken = build;
                        iBinder = iBinder2;
                        i12 = callingUid;
                        activityRecord2 = null;
                        activityRecord = null;
                        i11 = 2011;
                        i10 = 3;
                    } else {
                        i8 = i5;
                        i9 = i6;
                        displayContent = displayContentOrCreate;
                        iArr = iArr2;
                        windowState2 = windowState;
                        if (i20 >= 1 && i20 <= 99) {
                            activityRecord2 = windowToken2.asActivityRecord();
                            if (activityRecord2 == null) {
                                if (ProtoLogCache.WM_ERROR_enabled) {
                                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 246676969, 0, "Attempted to add window with non-application token .%s Aborting.", new Object[]{String.valueOf(windowToken2)});
                                }
                                resetPriorityAfterLockedSection();
                                return -3;
                            }
                            if (activityRecord2.getParent() == null) {
                                if (ProtoLogCache.WM_ERROR_enabled) {
                                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -853226675, 0, "Attempted to add window with exiting application token .%s Aborting.", new Object[]{String.valueOf(windowToken2)});
                                }
                                resetPriorityAfterLockedSection();
                                return -4;
                            }
                            i10 = 3;
                            if (i8 == 3) {
                                if (activityRecord2.mStartingWindow != null) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -167822951, 0, "Attempted to add starting window to token with already existing starting window", (Object[]) null);
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -5;
                                }
                                if (activityRecord2.mStartingData == null) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1804245629, 0, "Attempted to add starting window to token but already cleaned", (Object[]) null);
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -5;
                                }
                            }
                            windowToken = windowToken2;
                            iBinder = iBinder2;
                            i12 = callingUid;
                            activityRecord = null;
                            i11 = 2011;
                        } else {
                            i10 = 3;
                            i11 = 2011;
                            if (i20 == 2011) {
                                if (windowToken2.windowType != 2011) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1949279037, 0, "Attempted to add input method window with bad token %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -1;
                                }
                            } else if (i20 == 2031) {
                                if (windowToken2.windowType != 2031) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1389772804, 0, "Attempted to add voice interaction window with bad token %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -1;
                                }
                            } else if (i20 == 2013) {
                                if (windowToken2.windowType != 2013) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1915280162, 0, "Attempted to add wallpaper window with bad token %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -1;
                                }
                            } else if (i20 == 2032) {
                                if (windowToken2.windowType != 2032) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1976930686, 0, "Attempted to add Accessibility overlay window with bad token %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -1;
                                }
                            } else if (i8 == 2005) {
                                i12 = callingUid;
                                boolean doesAddToastWindowRequireToken = doesAddToastWindowRequireToken(layoutParams.packageName, i12, windowState2);
                                if (doesAddToastWindowRequireToken && windowToken2.windowType != 2005) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 662572728, 0, "Attempted to add a toast window with bad token %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -1;
                                }
                                windowToken = windowToken2;
                                z4 = doesAddToastWindowRequireToken;
                                iBinder = iBinder2;
                                activityRecord2 = null;
                                activityRecord = null;
                                int i21 = iArr[0];
                                int i22 = session.mUid;
                                boolean z14 = session.mCanAddInternalSystemWindow;
                                ActivityRecord activityRecord6 = activityRecord2;
                                int i23 = i8;
                                int i24 = i12;
                                int i25 = i11;
                                int i26 = i10;
                                DisplayContent displayContent3 = displayContent;
                                windowState3 = new WindowState(this, session, iWindow, windowToken, windowState2, i21, layoutParams, i, i22, i7, z14);
                                if (windowState3.mDeathRecipient == null) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1770075711, 0, "Adding window client %s that is dead, aborting.", new Object[]{String.valueOf(iWindow.asBinder())});
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -4;
                                }
                                if (windowState3.getDisplayContent() == null) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1720696061, 0, "Adding window to Display that has been removed.", (Object[]) null);
                                    }
                                    resetPriorityAfterLockedSection();
                                    return -9;
                                }
                                DisplayPolicy displayPolicy = displayContent3.getDisplayPolicy();
                                displayPolicy.adjustWindowParamsLw(windowState3, windowState3.mAttrs);
                                int i27 = i9;
                                layoutParams.flags = sanitizeFlagSlippery(layoutParams.flags, windowState3.getName(), i24, i27);
                                layoutParams.inputFeatures = sanitizeSpyWindow(layoutParams.inputFeatures, windowState3.getName(), i24, i27);
                                windowState3.setRequestedVisibleTypes(i4);
                                int validateAddingWindowLw = displayPolicy.validateAddingWindowLw(layoutParams, i27, i24);
                                if (validateAddingWindowLw != 0) {
                                    resetPriorityAfterLockedSection();
                                    return validateAddingWindowLw;
                                }
                                if (inputChannel != null) {
                                    z5 = true;
                                    r5 = 1;
                                    if ((layoutParams.inputFeatures & 1) == 0) {
                                        z6 = true;
                                        if (z6) {
                                            windowState3.openInputChannel(inputChannel);
                                        }
                                        if (i23 != 2005) {
                                            DisplayContent displayContent4 = displayContent3;
                                            if (!displayContent4.canAddToastWindowForUid(i24)) {
                                                if (ProtoLogCache.WM_ERROR_enabled) {
                                                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -883738232, 0, "Adding more than one toast window for UID at a time.", (Object[]) null);
                                                }
                                                resetPriorityAfterLockedSection();
                                                return -5;
                                            }
                                            if (!z4 && (layoutParams.flags & 8) != 0 && (r8 = displayContent4.mCurrentFocus) != null) {
                                                displayContent2 = displayContent4;
                                            }
                                            HandlerC2998H handlerC2998H = this.f1749mH;
                                            handlerC2998H.sendMessageDelayed(handlerC2998H.obtainMessage(52, windowState3), windowState3.mAttrs.hideTimeoutMilliseconds);
                                            displayContent2 = displayContent4;
                                        } else {
                                            displayContent2 = displayContent3;
                                        }
                                        if (!windowState3.isChildWindow()) {
                                            IBinder iBinder5 = iBinder;
                                            if (this.mWindowContextListenerController.hasListener(iBinder5)) {
                                                int windowType = this.mWindowContextListenerController.getWindowType(iBinder5);
                                                Bundle options = this.mWindowContextListenerController.getOptions(iBinder5);
                                                if (i23 != windowType) {
                                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                                        long j = i23;
                                                        i17 = i23;
                                                        long j2 = windowType;
                                                        ProtoLogGroup protoLogGroup = ProtoLogGroup.WM_ERROR;
                                                        Object[] objArr = new Object[2];
                                                        objArr[0] = Long.valueOf(j);
                                                        objArr[r5] = Long.valueOf(j2);
                                                        ProtoLogImpl.w(protoLogGroup, 1252594551, 5, "Window types in WindowContext and LayoutParams.type should match! Type from LayoutParams is %d, but type from WindowContext is %d", objArr);
                                                    } else {
                                                        i17 = i23;
                                                    }
                                                    if (!WindowProviderService.isWindowProviderService(options)) {
                                                        resetPriorityAfterLockedSection();
                                                        return -10;
                                                    }
                                                    i13 = i17;
                                                    windowState4 = windowState3;
                                                    i14 = 2013;
                                                    i16 = i26;
                                                    i15 = i25;
                                                    int i28 = !this.mUseBLAST ? 8 : 0;
                                                    if (displayContent2.mCurrentFocus == null) {
                                                        displayContent2.mWinAddedSinceNullFocus.add(windowState4);
                                                    }
                                                    if (excludeWindowTypeFromTapOutTask(i13)) {
                                                        displayContent2.mTapExcludedWindows.add(windowState4);
                                                    }
                                                    windowState4.attach();
                                                    this.mWindowMap.put(iWindow.asBinder(), windowState4);
                                                    windowState4.initAppOpsState();
                                                    windowState4.setHiddenWhileSuspended(this.mPmInternal.isPackageSuspended(windowState4.getOwningPackage(), UserHandle.getUserId(windowState4.getOwningUid())));
                                                    if (SemPersonaManager.isSecureFolderId(UserHandle.getUserId(windowState4.getOwningUid()))) {
                                                        windowState4.setHiddenWhileProfileLockedStateLocked(this.mAmInternal.shouldConfirmCredentials(UserHandle.getUserId(windowState4.getOwningUid())));
                                                    }
                                                    windowState4.setForceHideNonSystemOverlayWindowIfNeeded(this.mHidingNonSystemOverlayWindows.isEmpty() ? r5 : false);
                                                    windowState4.mToken.addWindow(windowState4);
                                                    displayPolicy.addWindowLw(windowState4, layoutParams);
                                                    displayPolicy.setDropInputModePolicy(windowState4, windowState4.mAttrs);
                                                    if (i13 != i16) {
                                                        activityRecord3 = activityRecord6;
                                                        if (activityRecord3 != null) {
                                                            activityRecord3.attachStartingWindow(windowState4);
                                                            if (ProtoLogCache.WM_DEBUG_STARTING_WINDOW_enabled) {
                                                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 150351993, 0, "addWindow: %s startingWindow=%s", new Object[]{String.valueOf(activityRecord3), String.valueOf(windowState4)});
                                                            }
                                                            z7 = r5;
                                                            if (this.mAtmService.mDexController.getDexModeLocked() == 2 && windowState4.mAttrs.type == 2619) {
                                                                this.mExt.moveDisplayToTop(windowState4.getDisplayId());
                                                            }
                                                            adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowState4, i28);
                                                            WindowStateAnimator windowStateAnimator = windowState4.mWinAnimator;
                                                            windowStateAnimator.mEnterAnimationPending = r5;
                                                            windowStateAnimator.mEnteringAnimation = r5;
                                                            if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                                                                adjustCutoutMode |= 4;
                                                            }
                                                            if (displayContent2.isInTouchMode()) {
                                                                adjustCutoutMode |= 1;
                                                            }
                                                            activityRecord4 = windowState4.mActivityRecord;
                                                            if (activityRecord4 != null || activityRecord4.isClientVisible()) {
                                                                adjustCutoutMode |= 2;
                                                            }
                                                            displayContent2.getInputMonitor().setUpdateInputWindowsNeededLw();
                                                            if (windowState4.canReceiveKeys()) {
                                                                z8 = updateFocusedWindowLocked(r5, false);
                                                                z9 = z7;
                                                                if (z8) {
                                                                    z9 = false;
                                                                }
                                                            } else {
                                                                z8 = false;
                                                                z9 = z7;
                                                            }
                                                            if (z9) {
                                                                displayContent2.computeImeTarget(r5);
                                                                if (windowState4.isImeOverlayLayeringTarget()) {
                                                                    dispatchImeTargetOverlayVisibilityChanged(iWindow.asBinder(), windowState4.mAttrs.type, windowState4.isVisibleRequestedOrAdding(), false);
                                                                }
                                                            }
                                                            windowState4.getParent().assignChildLayers();
                                                            if (z8) {
                                                                displayContent2.getInputMonitor().setInputFocusLw(displayContent2.mCurrentFocus, false);
                                                            }
                                                            displayContent2.getInputMonitor().updateInputWindowsLw(false);
                                                            if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                                                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -1427184084, 0, (String) null, new Object[]{String.valueOf(iWindow.asBinder()), String.valueOf(windowState4), String.valueOf(Debug.getCallers(5))});
                                                            }
                                                            if (activityRecord3 != null && activityRecord3.isDexMode() && activityRecord3.mIsDexCompatEnabled && (windowState4.mAttrs.samsungFlags & 256) == 0) {
                                                                adjustCutoutMode |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
                                                            }
                                                            activityRecord5 = windowState4.mActivityRecord;
                                                            if (activityRecord5 != null && activityRecord5.mCompatRecord.isCompatModeEnabled() && !windowState4.mActivityRecord.mCompatRecord.getController().shouldUseSandboxViewBoundsAndMotionEvent(windowState4.mActivityRecord)) {
                                                                adjustCutoutMode |= 1048576;
                                                                if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS && windowState4.mActivityRecord.mCompatRecord.isFixedAspectRatioModeEnabled()) {
                                                                    adjustCutoutMode |= 8388608;
                                                                }
                                                            }
                                                            z10 = (windowState4.isVisibleRequestedOrAdding() || !displayContent2.updateOrientation()) ? false : r5;
                                                            if (windowState4.providesDisplayDecorInsets()) {
                                                                z10 |= displayPolicy.updateDecorInsetsInfo();
                                                            }
                                                            if (z10) {
                                                                displayContent2.sendNewConfiguration();
                                                            }
                                                            displayContent2.getInsetsStateController().updateAboveInsetsState(false);
                                                            insetsState.set(windowState4.getCompatInsetsState(), (boolean) r5);
                                                            getInsetsSourceControls(windowState4, array);
                                                            if (windowState4.mLayoutAttached) {
                                                                rect.set(windowState4.getParentWindow().getFrame());
                                                                float f = windowState4.mInvGlobalScale;
                                                                if (f != 1.0f) {
                                                                    rect.scale(f);
                                                                }
                                                            } else {
                                                                rect.set(0, 0, -1, -1);
                                                            }
                                                            fArr[0] = windowState4.getCompatScaleForClient();
                                                            resetPriorityAfterLockedSection();
                                                            Binder.restoreCallingIdentity(clearCallingIdentity);
                                                            return adjustCutoutMode;
                                                        }
                                                    } else {
                                                        activityRecord3 = activityRecord6;
                                                    }
                                                    if (i13 != i15 && (windowState4.getAttrs().flags & 16) == 0) {
                                                        displayContent2.setInputMethodWindowLocked(windowState4);
                                                    } else if (i13 != 2012) {
                                                        displayContent2.computeImeTarget(r5);
                                                    } else {
                                                        if (i13 == i14) {
                                                            displayContent2.mWallpaperController.clearLastWallpaperTimeoutTime();
                                                            displayContent2.pendingLayoutChanges |= 4;
                                                        } else if (windowState4.hasWallpaper()) {
                                                            displayContent2.pendingLayoutChanges |= 4;
                                                        } else if (displayContent2.mWallpaperController.isBelowWallpaperTarget(windowState4)) {
                                                            displayContent2.pendingLayoutChanges |= 4;
                                                        }
                                                        z7 = r5;
                                                        if (this.mAtmService.mDexController.getDexModeLocked() == 2) {
                                                            this.mExt.moveDisplayToTop(windowState4.getDisplayId());
                                                        }
                                                        adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowState4, i28);
                                                        WindowStateAnimator windowStateAnimator2 = windowState4.mWinAnimator;
                                                        windowStateAnimator2.mEnterAnimationPending = r5;
                                                        windowStateAnimator2.mEnteringAnimation = r5;
                                                        if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                                                        }
                                                        if (displayContent2.isInTouchMode()) {
                                                        }
                                                        activityRecord4 = windowState4.mActivityRecord;
                                                        if (activityRecord4 != null) {
                                                        }
                                                        adjustCutoutMode |= 2;
                                                        displayContent2.getInputMonitor().setUpdateInputWindowsNeededLw();
                                                        if (windowState4.canReceiveKeys()) {
                                                        }
                                                        if (z9) {
                                                        }
                                                        windowState4.getParent().assignChildLayers();
                                                        if (z8) {
                                                        }
                                                        displayContent2.getInputMonitor().updateInputWindowsLw(false);
                                                        if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                                                        }
                                                        if (activityRecord3 != null) {
                                                            adjustCutoutMode |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
                                                        }
                                                        activityRecord5 = windowState4.mActivityRecord;
                                                        if (activityRecord5 != null) {
                                                            adjustCutoutMode |= 1048576;
                                                            if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS) {
                                                                adjustCutoutMode |= 8388608;
                                                            }
                                                        }
                                                        if (windowState4.isVisibleRequestedOrAdding()) {
                                                        }
                                                        if (windowState4.providesDisplayDecorInsets()) {
                                                        }
                                                        if (z10) {
                                                        }
                                                        displayContent2.getInsetsStateController().updateAboveInsetsState(false);
                                                        insetsState.set(windowState4.getCompatInsetsState(), (boolean) r5);
                                                        getInsetsSourceControls(windowState4, array);
                                                        if (windowState4.mLayoutAttached) {
                                                        }
                                                        fArr[0] = windowState4.getCompatScaleForClient();
                                                        resetPriorityAfterLockedSection();
                                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                                        return adjustCutoutMode;
                                                    }
                                                    z7 = false;
                                                    if (this.mAtmService.mDexController.getDexModeLocked() == 2) {
                                                    }
                                                    adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowState4, i28);
                                                    WindowStateAnimator windowStateAnimator22 = windowState4.mWinAnimator;
                                                    windowStateAnimator22.mEnterAnimationPending = r5;
                                                    windowStateAnimator22.mEnteringAnimation = r5;
                                                    if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                                                    }
                                                    if (displayContent2.isInTouchMode()) {
                                                    }
                                                    activityRecord4 = windowState4.mActivityRecord;
                                                    if (activityRecord4 != null) {
                                                    }
                                                    adjustCutoutMode |= 2;
                                                    displayContent2.getInputMonitor().setUpdateInputWindowsNeededLw();
                                                    if (windowState4.canReceiveKeys()) {
                                                    }
                                                    if (z9) {
                                                    }
                                                    windowState4.getParent().assignChildLayers();
                                                    if (z8) {
                                                    }
                                                    displayContent2.getInputMonitor().updateInputWindowsLw(false);
                                                    if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
                                                    }
                                                    if (activityRecord3 != null) {
                                                    }
                                                    activityRecord5 = windowState4.mActivityRecord;
                                                    if (activityRecord5 != null) {
                                                    }
                                                    if (windowState4.isVisibleRequestedOrAdding()) {
                                                    }
                                                    if (windowState4.providesDisplayDecorInsets()) {
                                                    }
                                                    if (z10) {
                                                    }
                                                    displayContent2.getInsetsStateController().updateAboveInsetsState(false);
                                                    insetsState.set(windowState4.getCompatInsetsState(), (boolean) r5);
                                                    getInsetsSourceControls(windowState4, array);
                                                    if (windowState4.mLayoutAttached) {
                                                    }
                                                    fArr[0] = windowState4.getCompatScaleForClient();
                                                    resetPriorityAfterLockedSection();
                                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                                    return adjustCutoutMode;
                                                }
                                                i13 = i23;
                                                i14 = 2013;
                                                i16 = i26;
                                                i15 = i25;
                                                windowState4 = windowState3;
                                                this.mWindowContextListenerController.registerWindowContainerListener(iBinder5, windowToken, i24, i13, options);
                                                if (!this.mUseBLAST) {
                                                }
                                                if (displayContent2.mCurrentFocus == null) {
                                                }
                                                if (excludeWindowTypeFromTapOutTask(i13)) {
                                                }
                                                windowState4.attach();
                                                this.mWindowMap.put(iWindow.asBinder(), windowState4);
                                                windowState4.initAppOpsState();
                                                windowState4.setHiddenWhileSuspended(this.mPmInternal.isPackageSuspended(windowState4.getOwningPackage(), UserHandle.getUserId(windowState4.getOwningUid())));
                                                if (SemPersonaManager.isSecureFolderId(UserHandle.getUserId(windowState4.getOwningUid()))) {
                                                }
                                                windowState4.setForceHideNonSystemOverlayWindowIfNeeded(this.mHidingNonSystemOverlayWindows.isEmpty() ? r5 : false);
                                                windowState4.mToken.addWindow(windowState4);
                                                displayPolicy.addWindowLw(windowState4, layoutParams);
                                                displayPolicy.setDropInputModePolicy(windowState4, windowState4.mAttrs);
                                                if (i13 != i16) {
                                                }
                                                if (i13 != i15) {
                                                }
                                                if (i13 != 2012) {
                                                }
                                            }
                                        }
                                        i13 = i23;
                                        windowState4 = windowState3;
                                        i14 = 2013;
                                        i16 = i26;
                                        i15 = i25;
                                        if (!this.mUseBLAST) {
                                        }
                                        if (displayContent2.mCurrentFocus == null) {
                                        }
                                        if (excludeWindowTypeFromTapOutTask(i13)) {
                                        }
                                        windowState4.attach();
                                        this.mWindowMap.put(iWindow.asBinder(), windowState4);
                                        windowState4.initAppOpsState();
                                        windowState4.setHiddenWhileSuspended(this.mPmInternal.isPackageSuspended(windowState4.getOwningPackage(), UserHandle.getUserId(windowState4.getOwningUid())));
                                        if (SemPersonaManager.isSecureFolderId(UserHandle.getUserId(windowState4.getOwningUid()))) {
                                        }
                                        windowState4.setForceHideNonSystemOverlayWindowIfNeeded(this.mHidingNonSystemOverlayWindows.isEmpty() ? r5 : false);
                                        windowState4.mToken.addWindow(windowState4);
                                        displayPolicy.addWindowLw(windowState4, layoutParams);
                                        displayPolicy.setDropInputModePolicy(windowState4, windowState4.mAttrs);
                                        if (i13 != i16) {
                                        }
                                        if (i13 != i15) {
                                        }
                                        if (i13 != 2012) {
                                        }
                                    }
                                } else {
                                    z5 = true;
                                }
                                z6 = false;
                                r5 = z5;
                                if (z6) {
                                }
                                if (i23 != 2005) {
                                }
                                if (!windowState3.isChildWindow()) {
                                }
                                i13 = i23;
                                windowState4 = windowState3;
                                i14 = 2013;
                                i16 = i26;
                                i15 = i25;
                                if (!this.mUseBLAST) {
                                }
                                if (displayContent2.mCurrentFocus == null) {
                                }
                                if (excludeWindowTypeFromTapOutTask(i13)) {
                                }
                                windowState4.attach();
                                this.mWindowMap.put(iWindow.asBinder(), windowState4);
                                windowState4.initAppOpsState();
                                windowState4.setHiddenWhileSuspended(this.mPmInternal.isPackageSuspended(windowState4.getOwningPackage(), UserHandle.getUserId(windowState4.getOwningUid())));
                                if (SemPersonaManager.isSecureFolderId(UserHandle.getUserId(windowState4.getOwningUid()))) {
                                }
                                windowState4.setForceHideNonSystemOverlayWindowIfNeeded(this.mHidingNonSystemOverlayWindows.isEmpty() ? r5 : false);
                                windowState4.mToken.addWindow(windowState4);
                                displayPolicy.addWindowLw(windowState4, layoutParams);
                                displayPolicy.setDropInputModePolicy(windowState4, windowState4.mAttrs);
                                if (i13 != i16) {
                                }
                                if (i13 != i15) {
                                }
                                if (i13 != 2012) {
                                }
                            } else {
                                i12 = callingUid;
                                if (i8 == 2035) {
                                    if (windowToken2.windowType != 2035) {
                                        if (ProtoLogCache.WM_ERROR_enabled) {
                                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1060365734, 0, "Attempted to add QS dialog window with bad token %s.  Aborting.", new Object[]{String.valueOf(layoutParams.token)});
                                        }
                                        resetPriorityAfterLockedSection();
                                        return -1;
                                    }
                                } else if (windowToken2.asActivityRecord() != null) {
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        iBinder = iBinder2;
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 372792199, 1, "Non-null activity for system window of rootType=%d", new Object[]{Long.valueOf(i20)});
                                    } else {
                                        iBinder = iBinder2;
                                    }
                                    activityRecord = null;
                                    layoutParams.token = null;
                                    windowToken2 = new WindowToken.Builder(this, iWindow.asBinder(), i8).setDisplayContent(displayContent).setOwnerCanManageAppTokens(session.mCanAddInternalSystemWindow).build();
                                    windowToken = windowToken2;
                                    activityRecord2 = activityRecord;
                                    z4 = false;
                                    int i212 = iArr[0];
                                    int i222 = session.mUid;
                                    boolean z142 = session.mCanAddInternalSystemWindow;
                                    ActivityRecord activityRecord62 = activityRecord2;
                                    int i232 = i8;
                                    int i242 = i12;
                                    int i252 = i11;
                                    int i262 = i10;
                                    DisplayContent displayContent32 = displayContent;
                                    windowState3 = new WindowState(this, session, iWindow, windowToken, windowState2, i212, layoutParams, i, i222, i7, z142);
                                    if (windowState3.mDeathRecipient == null) {
                                    }
                                }
                                iBinder = iBinder2;
                                activityRecord = null;
                                windowToken = windowToken2;
                                activityRecord2 = activityRecord;
                                z4 = false;
                                int i2122 = iArr[0];
                                int i2222 = session.mUid;
                                boolean z1422 = session.mCanAddInternalSystemWindow;
                                ActivityRecord activityRecord622 = activityRecord2;
                                int i2322 = i8;
                                int i2422 = i12;
                                int i2522 = i11;
                                int i2622 = i10;
                                DisplayContent displayContent322 = displayContent;
                                windowState3 = new WindowState(this, session, iWindow, windowToken, windowState2, i2122, layoutParams, i, i2222, i7, z1422);
                                if (windowState3.mDeathRecipient == null) {
                                }
                            }
                            iBinder = iBinder2;
                            i12 = callingUid;
                            activityRecord = null;
                            windowToken = windowToken2;
                            activityRecord2 = activityRecord;
                            z4 = false;
                            int i21222 = iArr[0];
                            int i22222 = session.mUid;
                            boolean z14222 = session.mCanAddInternalSystemWindow;
                            ActivityRecord activityRecord6222 = activityRecord2;
                            int i23222 = i8;
                            int i24222 = i12;
                            int i25222 = i11;
                            int i26222 = i10;
                            DisplayContent displayContent3222 = displayContent;
                            windowState3 = new WindowState(this, session, iWindow, windowToken, windowState2, i21222, layoutParams, i, i22222, i7, z14222);
                            if (windowState3.mDeathRecipient == null) {
                            }
                        }
                    }
                    z4 = false;
                    int i212222 = iArr[0];
                    int i222222 = session.mUid;
                    boolean z142222 = session.mCanAddInternalSystemWindow;
                    ActivityRecord activityRecord62222 = activityRecord2;
                    int i232222 = i8;
                    int i242222 = i12;
                    int i252222 = i11;
                    int i262222 = i10;
                    DisplayContent displayContent32222 = displayContent;
                    windowState3 = new WindowState(this, session, iWindow, windowToken, windowState2, i212222, layoutParams, i, i222222, i7, z142222);
                    if (windowState3.mDeathRecipient == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean unprivilegedAppCanCreateTokenWith(WindowState windowState, int i, int i2, int i3, IBinder iBinder, String str) {
        if (i3 >= 1 && i3 <= 99) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1113134997, 0, "Attempted to add application window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
            }
            return false;
        }
        if (i3 == 2011) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -2039580386, 0, "Attempted to add input method window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
            }
            return false;
        }
        if (i3 == 2031) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -914253865, 0, "Attempted to add voice interaction window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
            }
            return false;
        }
        if (i3 == 2013) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 424524729, 0, "Attempted to add wallpaper window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
            }
            return false;
        }
        if (i3 == 2035) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 898863925, 0, "Attempted to add QS dialog window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
            }
            return false;
        }
        if (i3 == 2032) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1042574499, 0, "Attempted to add Accessibility overlay window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
            }
            return false;
        }
        if (i2 != 2005 || !doesAddToastWindowRequireToken(str, i, windowState)) {
            return true;
        }
        if (ProtoLogCache.WM_ERROR_enabled) {
            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1331177619, 0, "Attempted to add a toast window with unknown token %s.  Aborting.", new Object[]{String.valueOf(iBinder)});
        }
        return false;
    }

    public final DisplayContent getDisplayContentOrCreate(int i, IBinder iBinder) {
        WindowToken windowToken;
        if (iBinder != null && (windowToken = this.mRoot.getWindowToken(iBinder)) != null) {
            return windowToken.getDisplayContent();
        }
        return this.mRoot.getDisplayContentOrCreate(i);
    }

    public final boolean doesAddToastWindowRequireToken(String str, int i, WindowState windowState) {
        if (windowState != null) {
            ActivityRecord activityRecord = windowState.mActivityRecord;
            return activityRecord != null && activityRecord.mTargetSdk >= 26;
        }
        ApplicationInfo applicationInfo = this.mPmInternal.getApplicationInfo(str, 0L, 1000, UserHandle.getUserId(i));
        if (applicationInfo != null && applicationInfo.uid == i) {
            return applicationInfo.targetSdkVersion >= 26;
        }
        throw new SecurityException("Package " + str + " not in UID " + i);
    }

    public void refreshScreenCaptureDisabled() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Only system can call refreshScreenCaptureDisabled.");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.refreshSecureSurfaceState();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void removeWindow(Session session, IWindow iWindow) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked != null) {
                    windowForClientLocked.removeIfPossible();
                    resetPriorityAfterLockedSection();
                } else {
                    this.mEmbeddedWindowController.remove(iWindow);
                    resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void postWindowRemoveCleanupLocked(WindowState windowState) {
        if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -622997754, 0, (String) null, new Object[]{String.valueOf(windowState)});
        }
        this.mWindowMap.remove(windowState.mClient.asBinder());
        DisplayContent displayContent = windowState.getDisplayContent();
        displayContent.getDisplayRotation().markForSeamlessRotation(windowState, false);
        windowState.resetAppOpsState();
        if (displayContent.mCurrentFocus == null) {
            displayContent.mWinRemovedSinceNullFocus.add(windowState);
        }
        this.mEmbeddedWindowController.onWindowRemoved(windowState);
        this.mResizingWindows.remove(windowState);
        updateNonSystemOverlayWindowsVisibilityIfNeeded(windowState, false);
        this.mWindowsChanged = true;
        if (ProtoLogCache.WM_DEBUG_WINDOW_MOVEMENT_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_MOVEMENT, -193782861, 0, (String) null, new Object[]{String.valueOf(windowState)});
        }
        DisplayContent displayContent2 = windowState.getDisplayContent();
        if (displayContent2.mInputMethodWindow == windowState) {
            displayContent2.setInputMethodWindowLocked(null);
        }
        WindowToken windowToken = windowState.mToken;
        if (ProtoLogCache.WM_DEBUG_ADD_REMOVE_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -1963461591, 0, (String) null, new Object[]{String.valueOf(windowState), String.valueOf(windowToken)});
        }
        if (windowToken.isEmpty() && !windowToken.mPersistOnEmpty) {
            windowToken.removeImmediately();
        }
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord != null) {
            activityRecord.postWindowRemoveStartingWindowCleanup();
        }
        if (windowState.mAttrs.type == 2013) {
            displayContent.mWallpaperController.clearLastWallpaperTimeoutTime();
            displayContent.pendingLayoutChanges |= 4;
        } else if (displayContent.mWallpaperController.isWallpaperTarget(windowState)) {
            displayContent.pendingLayoutChanges |= 4;
        }
        if (!this.mWindowPlacerLocked.isInLayout()) {
            displayContent.assignWindowLayers(true);
            this.mWindowPlacerLocked.performSurfacePlacement();
            ActivityRecord activityRecord2 = windowState.mActivityRecord;
            if (activityRecord2 != null) {
                activityRecord2.updateReportedVisibilityLocked();
            }
        }
        displayContent.getInputMonitor().updateInputWindowsLw(true);
        if (!displayContent.isDefaultDisplay && !displayContent.getLastHasContent() && displayContent.mCurrentFocus == null && displayContent.equals(this.mRoot.getTopChild()) && windowState.getWindowType() == 2037) {
            moveDisplayToTopInternal(0);
        }
    }

    public final void updateHiddenWhileSuspendedState(ArraySet arraySet, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.updateHiddenWhileSuspendedState(arraySet, z);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void updateHiddenWhileProfileLockedState(int i, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.updateHiddenWhileProfileLockedStateLocked(i, z);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void updateAppOpsState() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.updateAppOpsState();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void clearTouchableRegion(Session session, IWindow iWindow) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    windowForClientLocked(session, iWindow, false).clearClientTouchableRegion();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setInsetsWindow(Session session, IWindow iWindow, int i, Rect rect, Rect rect2, Region region) {
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked != null) {
                        windowForClientLocked.mGivenInsetsPending = false;
                        windowForClientLocked.mGivenContentInsets.set(rect);
                        windowForClientLocked.mGivenVisibleInsets.set(rect2);
                        windowForClientLocked.mGivenTouchableRegion.set(region);
                        windowForClientLocked.mTouchableInsets = i;
                        float f = windowForClientLocked.mGlobalScale;
                        if (f != 1.0f) {
                            windowForClientLocked.mGivenContentInsets.scale(f);
                            windowForClientLocked.mGivenVisibleInsets.scale(windowForClientLocked.mGlobalScale);
                            windowForClientLocked.mGivenTouchableRegion.scale(windowForClientLocked.mGlobalScale);
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        windowForClientLocked.updateSourceFrame(windowForClientLocked.getFrame());
                        this.mWindowPlacerLocked.performSurfacePlacement();
                        windowForClientLocked.getDisplayContent().getInputMonitor().updateInputWindowsLw(true);
                        if (this.mAccessibilityController.hasCallbacks()) {
                            this.mAccessibilityController.onSomeWindowResizedOrMovedWithCallingUid(callingUid, windowForClientLocked.getDisplayContent().getDisplayId());
                        }
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) {
        WindowState windowState;
        AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternal = AccessibilityController.getAccessibilityControllerInternal(this);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (accessibilityControllerInternal.hasWindowManagerEventDispatcher() && (windowState = (WindowState) this.mWindowMap.get(iBinder)) != null) {
                    accessibilityControllerInternal.onRectangleOnScreenRequested(windowState.getDisplayId(), rect);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public IWindowId getWindowId(IBinder iBinder) {
        WindowState.WindowId windowId;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                windowId = windowState != null ? windowState.mWindowId : null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return windowId;
    }

    public void pokeDrawLock(Session session, IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iBinder, false);
                if (windowForClientLocked != null) {
                    windowForClientLocked.pokeDrawLockLw(this.mDrawLockTimeoutMillis);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final boolean hasStatusBarPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.STATUS_BAR", i, i2) == 0;
    }

    public boolean cancelDraw(Session session, IWindow iWindow) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean cancelAndRedraw = windowForClientLocked.cancelAndRedraw();
                resetPriorityAfterLockedSection();
                return cancelAndRedraw;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:367:0x0112, code lost:
    
        if (r15 == null) goto L402;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x0114, code lost:
    
        r19 = r14;
        r20 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x011a, code lost:
    
        if (r7.length != r15.length) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x011c, code lost:
    
        r5 = r7.length;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x011e, code lost:
    
        if (r14 >= r5) goto L405;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x0120, code lost:
    
        r22 = r5;
        r23 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x0130, code lost:
    
        if (r7[r14].getWindowType() != r15[r14].getWindowType()) goto L400;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x0132, code lost:
    
        r14 = r14 + 1;
        r5 = r22;
        r7 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x0140, code lost:
    
        throw new java.lang.IllegalArgumentException("Insets override types can not be changed after the window is added.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x0141, code lost:
    
        continue;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0227 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0232 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0250 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x022e  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x034a A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0364 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0381 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x03db A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03fd A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x040b A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0417 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0458 A[Catch: all -> 0x06b5, TRY_ENTER, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0477 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x04f8 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0505  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x050a A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0516 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0520 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:231:0x053a A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x054c A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0555 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x055d A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0574 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:253:0x057f A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:256:0x058b A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:259:0x05ad A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x05c8 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x05fe  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0607 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0627 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x063e A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:303:0x065f A[Catch: all -> 0x06b5, TRY_LEAVE, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0676 A[Catch: all -> 0x06bc, TRY_ENTER, TryCatch #3 {all -> 0x06bc, blocks: (B:306:0x0676, B:307:0x0679, B:400:0x06b7), top: B:7:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0624  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x05a8  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0561  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0507  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x04a4 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:337:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01b7 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01c6 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01db A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01ec A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0202 A[Catch: all -> 0x06b5, TryCatch #1 {all -> 0x06b5, blocks: (B:9:0x003c, B:11:0x0042, B:15:0x0047, B:17:0x004b, B:18:0x0056, B:20:0x005c, B:23:0x0064, B:25:0x0072, B:26:0x0075, B:28:0x007d, B:30:0x0081, B:32:0x008b, B:34:0x008f, B:36:0x00b4, B:39:0x00bb, B:41:0x00c5, B:43:0x00c9, B:47:0x015a, B:51:0x017c, B:54:0x0185, B:56:0x018b, B:58:0x0191, B:59:0x01a0, B:61:0x01a6, B:63:0x01aa, B:65:0x01b0, B:66:0x01b3, B:68:0x01b7, B:69:0x01c0, B:71:0x01c6, B:74:0x01d2, B:76:0x01d5, B:78:0x01db, B:81:0x01e5, B:83:0x01e8, B:85:0x01ec, B:88:0x01fb, B:90:0x01fe, B:92:0x0202, B:95:0x020c, B:97:0x0211, B:99:0x0219, B:101:0x021d, B:106:0x0227, B:108:0x0232, B:109:0x0236, B:111:0x0250, B:112:0x0285, B:116:0x02a3, B:118:0x02fb, B:119:0x0301, B:121:0x0310, B:123:0x0314, B:125:0x0318, B:127:0x0320, B:132:0x032e, B:138:0x033c, B:140:0x0340, B:145:0x034a, B:148:0x0353, B:151:0x035e, B:153:0x0364, B:155:0x0368, B:156:0x036f, B:158:0x0381, B:159:0x03cc, B:162:0x03d7, B:164:0x03db, B:166:0x03df, B:168:0x03e6, B:171:0x03ef, B:173:0x03fd, B:175:0x0403, B:177:0x0407, B:179:0x040b, B:180:0x0410, B:185:0x0419, B:189:0x0420, B:191:0x042c, B:192:0x044d, B:193:0x0450, B:197:0x0458, B:199:0x045c, B:201:0x0466, B:202:0x046d, B:204:0x0477, B:207:0x0489, B:209:0x0491, B:211:0x0495, B:212:0x049c, B:214:0x04f8, B:217:0x0501, B:221:0x050a, B:223:0x0510, B:225:0x0516, B:226:0x051c, B:228:0x0520, B:229:0x0529, B:231:0x053a, B:233:0x0548, B:235:0x054c, B:236:0x054f, B:238:0x0555, B:239:0x0557, B:241:0x055d, B:246:0x056a, B:247:0x0570, B:249:0x0574, B:253:0x057f, B:254:0x0587, B:256:0x058b, B:257:0x05a9, B:259:0x05ad, B:261:0x05b3, B:263:0x05b9, B:265:0x05c1, B:266:0x05c4, B:268:0x05c8, B:270:0x05d0, B:272:0x05e0, B:274:0x05e6, B:276:0x05f0, B:277:0x05f3, B:280:0x0601, B:283:0x0609, B:284:0x0619, B:289:0x0629, B:292:0x063e, B:295:0x0646, B:297:0x064c, B:299:0x0652, B:300:0x0654, B:301:0x0657, B:303:0x065f, B:314:0x0578, B:316:0x053e, B:318:0x0542, B:322:0x04a4, B:325:0x04b3, B:327:0x04b9, B:330:0x04e6, B:333:0x04eb, B:334:0x04ee, B:335:0x04ef, B:345:0x031c, B:348:0x0180, B:351:0x00d6, B:353:0x00da, B:355:0x00de, B:357:0x00e2, B:359:0x00f4, B:365:0x0141, B:368:0x0114, B:370:0x011c, B:372:0x0120, B:374:0x0132, B:376:0x0139, B:377:0x0140, B:380:0x014a, B:381:0x0151, B:385:0x0152, B:386:0x0159, B:388:0x028b, B:389:0x0292, B:390:0x0293, B:391:0x029a, B:395:0x0050, B:329:0x04c8), top: B:8:0x003c, inners: #0, #2 }] */
    /* JADX WARN: Type inference failed for: r10v2, types: [com.android.server.wm.WindowState, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v3, types: [com.android.server.wm.DisplayContent] */
    /* JADX WARN: Type inference failed for: r27v0, types: [com.android.server.wm.WindowManagerService] */
    /* JADX WARN: Type inference failed for: r2v17, types: [com.android.server.wm.DisplayCutoutController] */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.server.wm.WallpaperController] */
    /* JADX WARN: Type inference failed for: r4v0, types: [int] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.server.wm.DisplayPolicyExt] */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v50, types: [com.android.server.wm.PopOverController] */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.android.server.wm.DisplayPolicy] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int relayoutWindow(Session session, IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, ClientWindowFrames clientWindowFrames, MergedConfiguration mergedConfiguration, SurfaceControl surfaceControl, InsetsState insetsState, InsetsSourceControl.Array array, Bundle bundle) {
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        String str;
        boolean z5;
        DisplayPolicy displayPolicy;
        WindowManager.LayoutParams layoutParams2;
        boolean z6;
        boolean z7;
        int adjustCutoutMode;
        boolean z8;
        boolean z9;
        boolean z10;
        ActivityRecord activityRecord;
        boolean updateOrientation;
        ActivityRecord activityRecord2;
        boolean z11;
        boolean z12;
        ActivityRecord activityRecord3;
        ActivityRecord activityRecord4;
        boolean z13;
        ActivityRecord activityRecord5;
        WindowSurfaceController windowSurfaceController;
        InsetsFrameProvider[] insetsFrameProviderArr;
        int i11;
        int i12;
        boolean z14;
        ActivityRecord activityRecord6;
        ActivityRecord activityRecord7;
        boolean z15;
        int i13;
        int i14;
        DisplayWindowPolicyControllerHelper displayWindowPolicyControllerHelper;
        ActivityInfo activityInfo;
        WindowManager.LayoutParams layoutParams3;
        ?? r4 = i3;
        if (array != null) {
            array.set((InsetsSourceControl[]) null);
        }
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        WindowManager.LayoutParams layoutParams4 = new WindowManager.LayoutParams();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                try {
                    ?? windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked == 0) {
                        resetPriorityAfterLockedSection();
                        return 0;
                    }
                    int i15 = windowForClientLocked.mRelayoutSeq;
                    if (i15 < i5) {
                        windowForClientLocked.mRelayoutSeq = i5;
                    } else if (i15 > i5) {
                        resetPriorityAfterLockedSection();
                        return 0;
                    }
                    int i16 = (!windowForClientLocked.cancelAndRedraw() || windowForClientLocked.mPrepareSyncSeqId > i6) ? 0 : 16;
                    ?? displayContent = windowForClientLocked.getDisplayContent();
                    ?? displayPolicy2 = displayContent.getDisplayPolicy();
                    WindowStateAnimator windowStateAnimator = windowForClientLocked.mWinAnimator;
                    if (r4 != 8) {
                        windowForClientLocked.setRequestedSize(i, i2);
                    }
                    if (!displayPolicy2.mExt.canBeForceHiddenByAodLw(windowForClientLocked) && (!CoreRune.FW_SELF_POKE_DRAW_LOCK || !this.mPolicy.isKeyguardHostWindow(windowForClientLocked.mAttrs))) {
                        i16 |= 1024;
                    }
                    if (layoutParams != null) {
                        displayPolicy2.adjustWindowParamsLw(windowForClientLocked, layoutParams);
                        layoutParams.flags = sanitizeFlagSlippery(layoutParams.flags, windowForClientLocked.getName(), callingUid, callingPid);
                        layoutParams.inputFeatures = sanitizeSpyWindow(layoutParams.inputFeatures, windowForClientLocked.getName(), callingUid, callingPid);
                        int i17 = (layoutParams.systemUiVisibility | layoutParams.subtreeSystemUiVisibility) & 134152192;
                        if (i17 != 0 && !hasStatusBarPermission(callingPid, callingUid)) {
                            i17 = 0;
                        }
                        windowForClientLocked.mDisableFlags = i17;
                        WindowManager.LayoutParams layoutParams5 = windowForClientLocked.mAttrs;
                        if (layoutParams5.type != layoutParams.type) {
                            throw new IllegalArgumentException("Window type can not be changed after the window is added.");
                        }
                        InsetsFrameProvider[] insetsFrameProviderArr2 = layoutParams5.providedInsets;
                        if (insetsFrameProviderArr2 == null) {
                            if (layoutParams.providedInsets != null) {
                            }
                            i7 = i16;
                            i8 = callingPid;
                            WindowManager.LayoutParams layoutParams6 = windowForClientLocked.mAttrs;
                            i11 = layoutParams6.samsungFlags ^ layoutParams.samsungFlags;
                            windowForClientLocked.mOriginalLayoutInDisplayCutoutMode = layoutParams.layoutInDisplayCutoutMode;
                            i10 = layoutParams6.flags ^ layoutParams.flags;
                            i12 = layoutParams6.privateFlags ^ layoutParams.privateFlags;
                            i9 = layoutParams6.copyFrom(layoutParams);
                            z14 = (i9 & 1) == 0;
                            if (!z14 || (i9 & 16384) != 0) {
                                windowForClientLocked.mLayoutNeeded = true;
                            }
                            if (z14 || !windowForClientLocked.providesDisplayDecorInsets()) {
                                z = false;
                            } else {
                                boolean updateDecorInsetsInfo = displayPolicy2.updateDecorInsetsInfo();
                                if (updateDecorInsetsInfo) {
                                    z = updateDecorInsetsInfo;
                                    this.mAtmService.mExt.mAvoidCompatDisplayInsets = true;
                                } else {
                                    z = updateDecorInsetsInfo;
                                }
                            }
                            activityRecord6 = windowForClientLocked.mActivityRecord;
                            if (activityRecord6 != null && ((i10 & 524288) != 0 || (i10 & 4194304) != 0)) {
                                activityRecord6.checkKeyguardFlagsChanged();
                            }
                            if ((i12 & 524288) != 0) {
                                updateNonSystemOverlayWindowsVisibilityIfNeeded(windowForClientLocked, windowForClientLocked.mWinAnimator.getShown());
                            }
                            if ((i9 & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0) {
                                windowStateAnimator.setColorSpaceAgnosticLocked((windowForClientLocked.mAttrs.privateFlags & 16777216) != 0);
                            }
                            if ((i11 & Integer.MIN_VALUE) != 0) {
                                windowStateAnimator.setInternalPresentationOnly((Integer.MIN_VALUE & windowForClientLocked.mAttrs.samsungFlags) != 0);
                            }
                            if ((i11 & 8) != 0) {
                                windowForClientLocked.mToken.setPortraitWindowToken((windowForClientLocked.mAttrs.samsungFlags & 8) != 0);
                            }
                            if ((i11 & 2) != 0) {
                                displayContent.mPopOverController.updateTransparency(windowForClientLocked, (windowForClientLocked.mAttrs.samsungFlags & 2) != 0);
                            }
                            if (displayContent.mDwpcHelper.hasController() && (activityRecord7 = windowForClientLocked.mActivityRecord) != null && (!(z15 = windowForClientLocked.mRelayoutCalled) || i10 != 0 || i12 != 0)) {
                                i13 = z15 ? windowForClientLocked.mAttrs.flags : i10;
                                if (!z15) {
                                    i12 = windowForClientLocked.mAttrs.privateFlags;
                                }
                                i14 = i12;
                                displayWindowPolicyControllerHelper = displayContent.mDwpcHelper;
                                activityInfo = activityRecord7.info;
                                layoutParams3 = windowForClientLocked.mAttrs;
                                if (!displayWindowPolicyControllerHelper.keepActivityOnWindowFlagsChanged(activityInfo, i13, i14, layoutParams3.flags, layoutParams3.privateFlags)) {
                                    HandlerC2998H handlerC2998H = this.f1749mH;
                                    handlerC2998H.sendMessage(handlerC2998H.obtainMessage(65, windowForClientLocked.mActivityRecord.getTask()));
                                    Slog.w(StartingSurfaceController.TAG, "Activity " + windowForClientLocked.mActivityRecord + " window flag changed, can't remain on display " + displayContent.getDisplayId());
                                    resetPriorityAfterLockedSection();
                                    return 0;
                                }
                            }
                        }
                        if (insetsFrameProviderArr2 == null || (insetsFrameProviderArr = layoutParams.providedInsets) == null || insetsFrameProviderArr2.length != insetsFrameProviderArr.length) {
                            throw new IllegalArgumentException("Insets amount can not be changed after the window is added.");
                        }
                        int length = insetsFrameProviderArr.length;
                        int i18 = 0;
                        while (i18 < length) {
                            int i19 = length;
                            if (!windowForClientLocked.mAttrs.providedInsets[i18].idEquals(layoutParams.providedInsets[i18])) {
                                throw new IllegalArgumentException("Insets ID can not be changed after the window is added.");
                            }
                            InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrides = windowForClientLocked.mAttrs.providedInsets[i18].getInsetsSizeOverrides();
                            InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrides2 = layoutParams.providedInsets[i18].getInsetsSizeOverrides();
                            if (insetsSizeOverrides == null && insetsSizeOverrides2 == null) {
                                int i20 = i16;
                                int i21 = callingPid;
                                i18++;
                                length = i19;
                                callingPid = i21;
                                i16 = i20;
                            }
                            throw new IllegalArgumentException("Insets override types can not be changed after the window is added.");
                        }
                        i7 = i16;
                        i8 = callingPid;
                        WindowManager.LayoutParams layoutParams62 = windowForClientLocked.mAttrs;
                        i11 = layoutParams62.samsungFlags ^ layoutParams.samsungFlags;
                        windowForClientLocked.mOriginalLayoutInDisplayCutoutMode = layoutParams.layoutInDisplayCutoutMode;
                        i10 = layoutParams62.flags ^ layoutParams.flags;
                        i12 = layoutParams62.privateFlags ^ layoutParams.privateFlags;
                        i9 = layoutParams62.copyFrom(layoutParams);
                        if ((i9 & 1) == 0) {
                        }
                        if (!z14) {
                        }
                        windowForClientLocked.mLayoutNeeded = true;
                        if (z14) {
                        }
                        z = false;
                        activityRecord6 = windowForClientLocked.mActivityRecord;
                        if (activityRecord6 != null) {
                            activityRecord6.checkKeyguardFlagsChanged();
                        }
                        if ((i12 & 524288) != 0) {
                        }
                        if ((i9 & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0) {
                        }
                        if ((i11 & Integer.MIN_VALUE) != 0) {
                        }
                        if ((i11 & 8) != 0) {
                        }
                        if ((i11 & 2) != 0) {
                        }
                        if (displayContent.mDwpcHelper.hasController()) {
                            if (z15) {
                            }
                            if (!z15) {
                            }
                            i14 = i12;
                            displayWindowPolicyControllerHelper = displayContent.mDwpcHelper;
                            activityInfo = activityRecord7.info;
                            layoutParams3 = windowForClientLocked.mAttrs;
                            if (!displayWindowPolicyControllerHelper.keepActivityOnWindowFlagsChanged(activityInfo, i13, i14, layoutParams3.flags, layoutParams3.privateFlags)) {
                            }
                        }
                    } else {
                        i7 = i16;
                        i8 = callingPid;
                        i9 = 0;
                        i10 = 0;
                        z = false;
                    }
                    Log.v(StartingSurfaceController.TAG, "Relayout " + windowForClientLocked + ": viewVisibility=" + r4 + " req=" + i + "x" + i2 + " ty=" + windowForClientLocked.mAttrs.type + " d" + windowForClientLocked.getDisplayId());
                    layoutParams4.copyFrom(windowForClientLocked.mAttrs);
                    String hexString = Integer.toHexString(System.identityHashCode(windowForClientLocked));
                    if ((i9 & 128) != 0) {
                        windowStateAnimator.mAlpha = layoutParams.alpha;
                    }
                    windowForClientLocked.setWindowScale(windowForClientLocked.mRequestedWidth, windowForClientLocked.mRequestedHeight);
                    Rect rect = windowForClientLocked.mAttrs.surfaceInsets;
                    if (rect.left != 0 || rect.top != 0 || rect.right != 0 || rect.bottom != 0) {
                        windowStateAnimator.setOpaqueLocked(false);
                    }
                    int i22 = windowForClientLocked.mViewVisibility;
                    boolean z16 = (i22 == 4 || i22 == 8) && r4 == 0;
                    if ((131080 & i10) == 0 && !z16) {
                        z2 = false;
                        if (i22 == r4 && (i10 & 8) == 0 && windowForClientLocked.mRelayoutCalled) {
                            z3 = false;
                            z4 = (i22 == r4 && windowForClientLocked.hasWallpaper()) | ((i10 & 1048576) == 0);
                            if ((i10 & IInstalld.FLAG_FORCE) != 0 && (windowSurfaceController = windowStateAnimator.mSurfaceController) != null) {
                                windowSurfaceController.setSecure(windowForClientLocked.isSecureLocked());
                            }
                            boolean isVisible = windowForClientLocked.isVisible();
                            windowForClientLocked.mRelayoutCalled = true;
                            windowForClientLocked.mInRelayout = true;
                            windowForClientLocked.setViewVisibility(r4);
                            boolean z17 = z2;
                            if (ProtoLogCache.WM_DEBUG_SCREEN_ON_enabled) {
                                str = hexString;
                                z5 = isVisible;
                                displayPolicy = displayPolicy2;
                                layoutParams2 = layoutParams4;
                                z6 = z3;
                            } else {
                                displayPolicy = displayPolicy2;
                                z6 = z3;
                                layoutParams2 = layoutParams4;
                                str = hexString;
                                z5 = isVisible;
                                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_SCREEN_ON, -754503024, 20, (String) null, new Object[]{String.valueOf((Object) windowForClientLocked), Long.valueOf(i22), Long.valueOf((long) r4), String.valueOf(new RuntimeException().fillInStackTrace())});
                            }
                            windowForClientLocked.setDisplayLayoutNeeded();
                            windowForClientLocked.mGivenInsetsPending = (i4 & 1) == 0;
                            z7 = r4 != 0 && ((activityRecord5 = windowForClientLocked.mActivityRecord) == null || windowForClientLocked.mAttrs.type == 3 || activityRecord5.isClientVisible());
                            adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowForClientLocked, i7);
                            if (!z7 && windowStateAnimator.hasSurface() && !windowForClientLocked.mAnimatingExit) {
                                adjustCutoutMode |= 2;
                                if (z4) {
                                    displayContent.mWallpaperController.adjustWallpaperWindows();
                                }
                                tryStartExitingAnimation(windowForClientLocked, windowStateAnimator);
                            }
                            if (z7 && surfaceControl != null) {
                                try {
                                    adjustCutoutMode = createSurfaceControl(surfaceControl, adjustCutoutMode, windowForClientLocked, windowStateAnimator);
                                } catch (Exception e) {
                                    displayContent.getInputMonitor().updateInputWindowsLw(true);
                                    if (ProtoLogCache.WM_ERROR_enabled) {
                                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1750206390, 0, "Exception thrown when creating surface for client %s (%s). %s", new Object[]{String.valueOf(iWindow), String.valueOf(windowForClientLocked.mAttrs.getTitle()), String.valueOf(e)});
                                    }
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    resetPriorityAfterLockedSection();
                                    return 0;
                                }
                            }
                            if (z16 && windowForClientLocked.mLayoutAttached && windowForClientLocked.getParentWindow().isGoneForLayout()) {
                                windowForClientLocked.getParentWindow().mLayoutNeeded = true;
                            }
                            this.mWindowPlacerLocked.performSurfacePlacement(true);
                            if (!z7) {
                                Trace.traceBegin(32L, "relayoutWindow: viewVisibility_1");
                                adjustCutoutMode = windowForClientLocked.relayoutVisibleWindow(adjustCutoutMode);
                                boolean z18 = (adjustCutoutMode & 1) != 0 ? true : z6;
                                if (windowForClientLocked.mAttrs.type == 2011 && displayContent.mInputMethodWindow == null) {
                                    displayContent.setInputMethodWindowLocked(windowForClientLocked);
                                    z8 = true;
                                } else {
                                    z8 = z17;
                                }
                                windowForClientLocked.adjustStartingWindowFlags();
                                Trace.traceEnd(32L);
                                z9 = z18;
                            } else {
                                Trace.traceBegin(32L, "relayoutWindow: viewVisibility_2");
                                windowStateAnimator.mEnterAnimationPending = false;
                                windowStateAnimator.mEnteringAnimation = false;
                                if (surfaceControl != null) {
                                    if (r4 == 0 && windowStateAnimator.hasSurface()) {
                                        Trace.traceBegin(32L, "relayoutWindow: getSurface");
                                        windowStateAnimator.mSurfaceController.getSurfaceControl(surfaceControl);
                                        Trace.traceEnd(32L);
                                    } else {
                                        try {
                                            Trace.traceBegin(32L, "wmReleaseOutSurface_" + ((Object) windowForClientLocked.mAttrs.getTitle()));
                                            surfaceControl.release();
                                            Trace.traceEnd(32L);
                                        } finally {
                                            Trace.traceEnd(32L);
                                        }
                                    }
                                }
                                Trace.traceEnd(32L);
                                z8 = z17;
                                z9 = z6;
                            }
                            if (z9 && updateFocusedWindowLocked(0, true)) {
                                z8 = false;
                            }
                            z10 = (adjustCutoutMode & 1) == 0;
                            if (z8) {
                                displayContent.computeImeTarget(true);
                                if (z10) {
                                    displayContent.assignWindowLayers(false);
                                }
                            }
                            if (z4) {
                                displayContent.pendingLayoutChanges |= 4;
                            }
                            activityRecord = windowForClientLocked.mActivityRecord;
                            if (activityRecord != null) {
                                displayContent.mUnknownAppVisibilityController.notifyRelayouted(activityRecord, windowForClientLocked.mAttrs.type);
                            }
                            Trace.traceBegin(32L, "relayoutWindow: updateOrientation");
                            updateOrientation = z | displayContent.updateOrientation();
                            Trace.traceEnd(32L);
                            if ((!z10 || windowForClientLocked.mViewVisibility == 0) && windowForClientLocked.mIsWallpaper) {
                                displayContent.mWallpaperController.updateWallpaperOffset(windowForClientLocked, false);
                            }
                            activityRecord2 = windowForClientLocked.mActivityRecord;
                            if (activityRecord2 != null) {
                                activityRecord2.updateReportedVisibilityLocked();
                            }
                            if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                                adjustCutoutMode |= 8;
                            }
                            if (windowForClientLocked.isGoneForLayout()) {
                                z11 = false;
                                windowForClientLocked.mResizedWhileGone = false;
                            } else {
                                z11 = false;
                            }
                            if (clientWindowFrames != null && mergedConfiguration != null) {
                                windowForClientLocked.fillClientWindowFramesAndConfiguration(clientWindowFrames, mergedConfiguration, z11, z7);
                                windowForClientLocked.onResizeHandled();
                            }
                            if (windowForClientLocked.mActivityRecord == null || windowForClientLocked.mIsImWindow) {
                                windowForClientLocked.updateLetterboxDirectionIfNeeded();
                            }
                            if (insetsState != null) {
                                insetsState.set(windowForClientLocked.getCompatInsetsState(), true);
                            }
                            if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                                z12 = true;
                            } else {
                                z12 = true;
                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS, 95902367, 12, (String) null, new Object[]{String.valueOf((Object) windowForClientLocked), Boolean.valueOf(z9)});
                            }
                            activityRecord3 = windowForClientLocked.mActivityRecord;
                            if (activityRecord3 != null && activityRecord3.isDexMode() && windowForClientLocked.mActivityRecord.mIsDexCompatEnabled && (windowForClientLocked.mAttrs.samsungFlags & 256) == 0) {
                                adjustCutoutMode |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
                            }
                            activityRecord4 = windowForClientLocked.mActivityRecord;
                            if (activityRecord4 != null && activityRecord4.mCompatRecord.isCompatModeEnabled() && !windowForClientLocked.mActivityRecord.mCompatRecord.getController().shouldUseSandboxViewBoundsAndMotionEvent(windowForClientLocked.mActivityRecord)) {
                                adjustCutoutMode |= 1048576;
                                if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS && windowForClientLocked.mActivityRecord.mCompatRecord.isFixedAspectRatioModeEnabled()) {
                                    adjustCutoutMode |= 8388608;
                                }
                            }
                            windowForClientLocked.mInRelayout = false;
                            boolean z19 = windowForClientLocked.isVisible() == z5 ? z12 : false;
                            if (windowForClientLocked.isImeOverlayLayeringTarget() && z19) {
                                dispatchImeTargetOverlayVisibilityChanged(iWindow.asBinder(), windowForClientLocked.mAttrs.type, windowForClientLocked.isVisible(), false);
                            }
                            if (windowForClientLocked.getDisplayContent().getImeInputTarget() == windowForClientLocked) {
                                z12 = false;
                            }
                            if (z12 || !z19) {
                                z13 = false;
                            } else {
                                z13 = false;
                                dispatchImeInputTargetVisibilityChanged(windowForClientLocked.mClient.asBinder(), windowForClientLocked.isVisible(), false);
                            }
                            if (bundle != null) {
                                if (windowForClientLocked.useBLASTSync() && r4 == 0 && windowForClientLocked.mSyncSeqId > i6) {
                                    r1 = windowForClientLocked.shouldSyncWithBuffers() ? windowForClientLocked.mSyncSeqId : -1;
                                    windowForClientLocked.markRedrawForSyncReported();
                                }
                                bundle.putInt("seqid", r1);
                            }
                            if (updateOrientation) {
                                Trace.traceBegin(32L, "relayoutWindow: postNewConfigurationToHandler");
                                displayContent.sendNewConfiguration();
                                this.mAtmService.mExt.mAvoidCompatDisplayInsets = z13;
                            }
                            if (array != null) {
                                getInsetsSourceControls(windowForClientLocked, array);
                            }
                            resetPriorityAfterLockedSection();
                            Log.v(StartingSurfaceController.TAG, "Relayout hash=" + str + ", pid=" + i8 + ", syncId=" + r1 + ": mAttrs=" + layoutParams2);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return adjustCutoutMode;
                        }
                        z3 = true;
                        z4 = (i22 == r4 && windowForClientLocked.hasWallpaper()) | ((i10 & 1048576) == 0);
                        if ((i10 & IInstalld.FLAG_FORCE) != 0) {
                            windowSurfaceController.setSecure(windowForClientLocked.isSecureLocked());
                        }
                        boolean isVisible2 = windowForClientLocked.isVisible();
                        windowForClientLocked.mRelayoutCalled = true;
                        windowForClientLocked.mInRelayout = true;
                        windowForClientLocked.setViewVisibility(r4);
                        boolean z172 = z2;
                        if (ProtoLogCache.WM_DEBUG_SCREEN_ON_enabled) {
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        windowForClientLocked.mGivenInsetsPending = (i4 & 1) == 0;
                        if (r4 != 0) {
                        }
                        adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowForClientLocked, i7);
                        if (!z7) {
                            adjustCutoutMode |= 2;
                            if (z4) {
                            }
                            tryStartExitingAnimation(windowForClientLocked, windowStateAnimator);
                        }
                        if (z7) {
                            adjustCutoutMode = createSurfaceControl(surfaceControl, adjustCutoutMode, windowForClientLocked, windowStateAnimator);
                        }
                        if (z16) {
                            windowForClientLocked.getParentWindow().mLayoutNeeded = true;
                        }
                        this.mWindowPlacerLocked.performSurfacePlacement(true);
                        if (!z7) {
                        }
                        if (z9) {
                            z8 = false;
                        }
                        if ((adjustCutoutMode & 1) == 0) {
                        }
                        if (z8) {
                        }
                        if (z4) {
                        }
                        activityRecord = windowForClientLocked.mActivityRecord;
                        if (activityRecord != null) {
                        }
                        Trace.traceBegin(32L, "relayoutWindow: updateOrientation");
                        updateOrientation = z | displayContent.updateOrientation();
                        Trace.traceEnd(32L);
                        if (!z10) {
                        }
                        displayContent.mWallpaperController.updateWallpaperOffset(windowForClientLocked, false);
                        activityRecord2 = windowForClientLocked.mActivityRecord;
                        if (activityRecord2 != null) {
                        }
                        if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                        }
                        if (windowForClientLocked.isGoneForLayout()) {
                        }
                        if (clientWindowFrames != null) {
                            windowForClientLocked.fillClientWindowFramesAndConfiguration(clientWindowFrames, mergedConfiguration, z11, z7);
                            windowForClientLocked.onResizeHandled();
                        }
                        if (windowForClientLocked.mActivityRecord == null) {
                        }
                        windowForClientLocked.updateLetterboxDirectionIfNeeded();
                        if (insetsState != null) {
                        }
                        if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                        }
                        activityRecord3 = windowForClientLocked.mActivityRecord;
                        if (activityRecord3 != null) {
                            adjustCutoutMode |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
                        }
                        activityRecord4 = windowForClientLocked.mActivityRecord;
                        if (activityRecord4 != null) {
                            adjustCutoutMode |= 1048576;
                            if (CoreRune.FW_BOUNDS_COMPAT_TRANSLATOR_AS_BOUNDS) {
                                adjustCutoutMode |= 8388608;
                            }
                        }
                        windowForClientLocked.mInRelayout = false;
                        if (windowForClientLocked.isVisible() == z5) {
                        }
                        if (windowForClientLocked.isImeOverlayLayeringTarget()) {
                            dispatchImeTargetOverlayVisibilityChanged(iWindow.asBinder(), windowForClientLocked.mAttrs.type, windowForClientLocked.isVisible(), false);
                        }
                        if (windowForClientLocked.getDisplayContent().getImeInputTarget() == windowForClientLocked) {
                        }
                        if (z12) {
                        }
                        z13 = false;
                        if (bundle != null) {
                        }
                        if (updateOrientation) {
                        }
                        if (array != null) {
                        }
                        resetPriorityAfterLockedSection();
                        Log.v(StartingSurfaceController.TAG, "Relayout hash=" + str + ", pid=" + i8 + ", syncId=" + r1 + ": mAttrs=" + layoutParams2);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return adjustCutoutMode;
                    }
                    z2 = true;
                    if (i22 == r4) {
                        z3 = false;
                        z4 = (i22 == r4 && windowForClientLocked.hasWallpaper()) | ((i10 & 1048576) == 0);
                        if ((i10 & IInstalld.FLAG_FORCE) != 0) {
                        }
                        boolean isVisible22 = windowForClientLocked.isVisible();
                        windowForClientLocked.mRelayoutCalled = true;
                        windowForClientLocked.mInRelayout = true;
                        windowForClientLocked.setViewVisibility(r4);
                        boolean z1722 = z2;
                        if (ProtoLogCache.WM_DEBUG_SCREEN_ON_enabled) {
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        windowForClientLocked.mGivenInsetsPending = (i4 & 1) == 0;
                        if (r4 != 0) {
                        }
                        adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowForClientLocked, i7);
                        if (!z7) {
                        }
                        if (z7) {
                        }
                        if (z16) {
                        }
                        this.mWindowPlacerLocked.performSurfacePlacement(true);
                        if (!z7) {
                        }
                        if (z9) {
                        }
                        if ((adjustCutoutMode & 1) == 0) {
                        }
                        if (z8) {
                        }
                        if (z4) {
                        }
                        activityRecord = windowForClientLocked.mActivityRecord;
                        if (activityRecord != null) {
                        }
                        Trace.traceBegin(32L, "relayoutWindow: updateOrientation");
                        updateOrientation = z | displayContent.updateOrientation();
                        Trace.traceEnd(32L);
                        if (!z10) {
                        }
                        displayContent.mWallpaperController.updateWallpaperOffset(windowForClientLocked, false);
                        activityRecord2 = windowForClientLocked.mActivityRecord;
                        if (activityRecord2 != null) {
                        }
                        if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                        }
                        if (windowForClientLocked.isGoneForLayout()) {
                        }
                        if (clientWindowFrames != null) {
                        }
                        if (windowForClientLocked.mActivityRecord == null) {
                        }
                        windowForClientLocked.updateLetterboxDirectionIfNeeded();
                        if (insetsState != null) {
                        }
                        if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                        }
                        activityRecord3 = windowForClientLocked.mActivityRecord;
                        if (activityRecord3 != null) {
                        }
                        activityRecord4 = windowForClientLocked.mActivityRecord;
                        if (activityRecord4 != null) {
                        }
                        windowForClientLocked.mInRelayout = false;
                        if (windowForClientLocked.isVisible() == z5) {
                        }
                        if (windowForClientLocked.isImeOverlayLayeringTarget()) {
                        }
                        if (windowForClientLocked.getDisplayContent().getImeInputTarget() == windowForClientLocked) {
                        }
                        if (z12) {
                        }
                        z13 = false;
                        if (bundle != null) {
                        }
                        if (updateOrientation) {
                        }
                        if (array != null) {
                        }
                        resetPriorityAfterLockedSection();
                        Log.v(StartingSurfaceController.TAG, "Relayout hash=" + str + ", pid=" + i8 + ", syncId=" + r1 + ": mAttrs=" + layoutParams2);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return adjustCutoutMode;
                    }
                    z3 = true;
                    z4 = (i22 == r4 && windowForClientLocked.hasWallpaper()) | ((i10 & 1048576) == 0);
                    if ((i10 & IInstalld.FLAG_FORCE) != 0) {
                    }
                    boolean isVisible222 = windowForClientLocked.isVisible();
                    windowForClientLocked.mRelayoutCalled = true;
                    windowForClientLocked.mInRelayout = true;
                    windowForClientLocked.setViewVisibility(r4);
                    boolean z17222 = z2;
                    if (ProtoLogCache.WM_DEBUG_SCREEN_ON_enabled) {
                    }
                    windowForClientLocked.setDisplayLayoutNeeded();
                    windowForClientLocked.mGivenInsetsPending = (i4 & 1) == 0;
                    if (r4 != 0) {
                    }
                    adjustCutoutMode = this.mAtmService.mExt.mDisplayCutoutController.adjustCutoutMode(windowForClientLocked, i7);
                    if (!z7) {
                    }
                    if (z7) {
                    }
                    if (z16) {
                    }
                    this.mWindowPlacerLocked.performSurfacePlacement(true);
                    if (!z7) {
                    }
                    if (z9) {
                    }
                    if ((adjustCutoutMode & 1) == 0) {
                    }
                    if (z8) {
                    }
                    if (z4) {
                    }
                    activityRecord = windowForClientLocked.mActivityRecord;
                    if (activityRecord != null) {
                    }
                    Trace.traceBegin(32L, "relayoutWindow: updateOrientation");
                    updateOrientation = z | displayContent.updateOrientation();
                    Trace.traceEnd(32L);
                    if (!z10) {
                    }
                    displayContent.mWallpaperController.updateWallpaperOffset(windowForClientLocked, false);
                    activityRecord2 = windowForClientLocked.mActivityRecord;
                    if (activityRecord2 != null) {
                    }
                    if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                    }
                    if (windowForClientLocked.isGoneForLayout()) {
                    }
                    if (clientWindowFrames != null) {
                    }
                    if (windowForClientLocked.mActivityRecord == null) {
                    }
                    windowForClientLocked.updateLetterboxDirectionIfNeeded();
                    if (insetsState != null) {
                    }
                    if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                    }
                    activityRecord3 = windowForClientLocked.mActivityRecord;
                    if (activityRecord3 != null) {
                    }
                    activityRecord4 = windowForClientLocked.mActivityRecord;
                    if (activityRecord4 != null) {
                    }
                    windowForClientLocked.mInRelayout = false;
                    if (windowForClientLocked.isVisible() == z5) {
                    }
                    if (windowForClientLocked.isImeOverlayLayeringTarget()) {
                    }
                    if (windowForClientLocked.getDisplayContent().getImeInputTarget() == windowForClientLocked) {
                    }
                    if (z12) {
                    }
                    z13 = false;
                    if (bundle != null) {
                    }
                    if (updateOrientation) {
                    }
                    if (array != null) {
                    }
                    resetPriorityAfterLockedSection();
                    Log.v(StartingSurfaceController.TAG, "Relayout hash=" + str + ", pid=" + i8 + ", syncId=" + r1 + ": mAttrs=" + layoutParams2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return adjustCutoutMode;
                } catch (Throwable th) {
                    th = th;
                    r4 = windowManagerGlobalLock;
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public final void getInsetsSourceControls(WindowState windowState, InsetsSourceControl.Array array) {
        InsetsSourceControl[] controlsForDispatch = windowState.getDisplayContent().getInsetsStateController().getControlsForDispatch(windowState);
        if (controlsForDispatch != null) {
            int length = controlsForDispatch.length;
            InsetsSourceControl[] insetsSourceControlArr = new InsetsSourceControl[length];
            for (int i = 0; i < length; i++) {
                if (controlsForDispatch[i] != null) {
                    InsetsSourceControl insetsSourceControl = new InsetsSourceControl(controlsForDispatch[i]);
                    insetsSourceControlArr[i] = insetsSourceControl;
                    insetsSourceControl.setParcelableFlags(1);
                }
            }
            array.set(insetsSourceControlArr);
        }
    }

    public final void tryStartExitingAnimation(WindowState windowState, WindowStateAnimator windowStateAnimator) {
        String str;
        int i = windowState.mAttrs.type == 3 ? 5 : 2;
        if (windowState.isVisible() && windowState.isDisplayed() && windowState.mDisplayContent.okToAnimate()) {
            if (windowStateAnimator.applyAnimationLocked(i, false)) {
                str = "applyAnimation";
            } else if (windowState.isSelfAnimating(0, 16)) {
                str = "selfAnimating";
            } else if (windowState.mTransitionController.isShellTransitionsEnabled()) {
                ActivityRecord activityRecord = windowState.mActivityRecord;
                if (activityRecord != null && activityRecord.inTransition()) {
                    windowState.mTransitionController.mAnimatingExitWindows.add(windowState);
                    str = "inTransition";
                }
                str = null;
            } else {
                if (windowState.isAnimating(3, 9)) {
                    str = "inLegacyTransition";
                }
                str = null;
            }
            if (str != null) {
                windowState.mAnimatingExit = true;
                if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, 2075693141, 0, (String) null, new Object[]{str, String.valueOf(windowState)});
                }
            }
        }
        if (!windowState.mAnimatingExit) {
            ActivityRecord activityRecord2 = windowState.mActivityRecord;
            boolean z = activityRecord2 == null || activityRecord2.mAppStopped;
            windowState.mDestroying = true;
            windowState.destroySurface(false, z);
        }
        if (this.mAccessibilityController.hasCallbacks()) {
            this.mAccessibilityController.onWindowTransition(windowState, i);
        }
    }

    public final int createSurfaceControl(SurfaceControl surfaceControl, int i, WindowState windowState, WindowStateAnimator windowStateAnimator) {
        if (!windowState.mHasSurface) {
            i |= 2;
        }
        try {
            Trace.traceBegin(32L, "createSurfaceControl");
            WindowSurfaceController createSurfaceLocked = windowStateAnimator.createSurfaceLocked();
            Trace.traceEnd(32L);
            if (createSurfaceLocked != null) {
                createSurfaceLocked.getSurfaceControl(surfaceControl);
                if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_SHOW_TRANSACTIONS, -1257821162, 0, (String) null, new Object[]{String.valueOf(surfaceControl)});
                }
            } else {
                if (ProtoLogCache.WM_ERROR_enabled) {
                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 704998117, 0, "Failed to create surface control for %s", new Object[]{String.valueOf(windowState)});
                }
                surfaceControl.release();
            }
            return i;
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public boolean outOfMemoryWindow(Session session, IWindow iWindow) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked != null) {
                        boolean reclaimSomeSurfaceMemory = this.mRoot.reclaimSomeSurfaceMemory(windowForClientLocked.mWinAnimator, "from-client", false);
                        resetPriorityAfterLockedSection();
                        return reclaimSomeSurfaceMemory;
                    }
                    resetPriorityAfterLockedSection();
                    return false;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void finishDrawingWindow(Session session, IWindow iWindow, SurfaceControl.Transaction transaction, int i) {
        if (transaction != null) {
            transaction.sanitize(Binder.getCallingPid(), Binder.getCallingUid());
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (ProtoLogCache.WM_FORCE_DEBUG_ADD_REMOVE_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_FORCE_DEBUG_ADD_REMOVE, -2000257507, 16, "finishDrawingWindow: %s mDrawState=%s seqId=%d", new Object[]{String.valueOf(windowForClientLocked), String.valueOf(windowForClientLocked != null ? windowForClientLocked.mWinAnimator.drawStateToString() : "null"), Long.valueOf(i)});
                    }
                    if (transaction != null && !TextUtils.isEmpty(transaction.mDebugName)) {
                        Slog.d(StartingSurfaceController.TAG, "finishDrawingWindow: syncBuffer=" + transaction.mDebugName);
                    }
                    if (windowForClientLocked != null && windowForClientLocked.finishDrawing(transaction, i)) {
                        if (windowForClientLocked.hasWallpaper()) {
                            windowForClientLocked.getDisplayContent().pendingLayoutChanges |= 4;
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        this.mWindowPlacerLocked.requestTraversal();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean checkCallingPermission(String str, String str2) {
        return checkCallingPermission(str, str2, true);
    }

    public boolean checkCallingPermission(String str, String str2, boolean z) {
        if (Binder.getCallingPid() == MY_PID || this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        if (!z || !ProtoLogCache.WM_ERROR_enabled) {
            return false;
        }
        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1563755163, 20, "Permission Denial: %s from pid=%d, uid=%d requires %s", new Object[]{String.valueOf(str2), Long.valueOf(Binder.getCallingPid()), Long.valueOf(Binder.getCallingUid()), String.valueOf(str)});
        return false;
    }

    public void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "addWindowToken()")) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2, null);
                if (displayContentOrCreate == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1208313423, 4, "addWindowToken: Attempted to add token: %s for non-exiting displayId=%d", new Object[]{String.valueOf(iBinder), Long.valueOf(i2)});
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowToken windowToken = displayContentOrCreate.getWindowToken(iBinder);
                if (windowToken == null) {
                    if (i == 2013) {
                        new WallpaperWindowToken(this, iBinder, true, displayContentOrCreate, true, bundle);
                    } else {
                        new WindowToken.Builder(this, iBinder, i).setDisplayContent(displayContentOrCreate).setPersistOnEmpty(true).setOwnerCanManageAppTokens(true).setOptions(bundle).build();
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (ProtoLogCache.WM_ERROR_enabled) {
                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 254883724, 16, "addWindowToken: Attempted to add binder token: %s for already created window token: %s displayId=%d", new Object[]{String.valueOf(iBinder), String.valueOf(windowToken), Long.valueOf(i2)});
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public Configuration attachWindowContextToDisplayArea(IBinder iBinder, int i, int i2, Bundle bundle) {
        if (iBinder == null) {
            throw new IllegalArgumentException("clientToken must not be null!");
        }
        boolean checkCallingPermission = checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "attachWindowContextToDisplayArea", false);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i2);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 666937535, 1, "attachWindowContextToDisplayArea: trying to attach to a non-existing display:%d", new Object[]{Long.valueOf(i2)});
                        }
                        resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    DisplayArea findAreaForWindowType = displayContentOrCreate.findAreaForWindowType(i, bundle, checkCallingPermission, false);
                    this.mWindowContextListenerController.registerWindowContainerListener(iBinder, findAreaForWindowType, callingUid, i, bundle, false);
                    Configuration configuration = findAreaForWindowType.getConfiguration();
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return configuration;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    /* JADX WARN: Finally extract failed */
    public void attachWindowContextToWindowToken(IBinder iBinder, IBinder iBinder2) {
        boolean checkCallingPermission = checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "attachWindowContextToWindowToken", false);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowToken windowToken = this.mRoot.getWindowToken(iBinder2);
                    if (windowToken == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1789321832, 0, "Then token:%s is invalid. It might be removed", new Object[]{String.valueOf(iBinder2)});
                        }
                    } else {
                        int windowType = this.mWindowContextListenerController.getWindowType(iBinder);
                        if (windowType == -1) {
                            throw new IllegalArgumentException("The clientToken:" + iBinder + " should have been attached.");
                        }
                        if (windowType != windowToken.windowType) {
                            throw new IllegalArgumentException("The WindowToken's type should match the created WindowContext's type. WindowToken's type is " + windowToken.windowType + ", while WindowContext's is " + windowType);
                        }
                        if (this.mWindowContextListenerController.assertCallerCanModifyListener(iBinder, checkCallingPermission, callingUid)) {
                            this.mWindowContextListenerController.registerWindowContainerListener(iBinder, windowToken, callingUid, windowToken.windowType, windowToken.mOptions);
                            resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void detachWindowContextFromWindowContainer(IBinder iBinder) {
        boolean checkCallingPermission = checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "detachWindowContextFromWindowContainer", false);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mWindowContextListenerController.assertCallerCanModifyListener(iBinder, checkCallingPermission, callingUid)) {
                        WindowContainer container = this.mWindowContextListenerController.getContainer(iBinder);
                        this.mWindowContextListenerController.unregisterWindowContainerListener(iBinder);
                        WindowToken asWindowToken = container.asWindowToken();
                        if (asWindowToken != null && asWindowToken.isFromClient()) {
                            removeWindowToken(asWindowToken.token, asWindowToken.getDisplayContent().getDisplayId());
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Finally extract failed */
    public Configuration attachToDisplayContent(IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new IllegalArgumentException("clientToken must not be null!");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        if (Binder.getCallingPid() != MY_PID) {
                            throw new WindowManager.InvalidDisplayException("attachToDisplayContent: trying to attach to a non-existing display:" + i);
                        }
                        resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    this.mWindowContextListenerController.registerWindowContainerListener(iBinder, displayContent, callingUid, -1, null, false);
                    Configuration configuration = displayContent.getConfiguration();
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return configuration;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public boolean isWindowToken(IBinder iBinder) {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = this.mRoot.getWindowToken(iBinder) != null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return z;
    }

    public void removeWindowToken(IBinder iBinder, boolean z, boolean z2, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                final DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1739298851, 4, "removeWindowToken: Attempted to remove token: %s for non-exiting displayId=%d", new Object[]{String.valueOf(iBinder), Long.valueOf(i)});
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowToken removeWindowToken = displayContent.removeWindowToken(iBinder, z2);
                if (removeWindowToken == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1518495446, 0, "removeWindowToken: Attempted to remove non-existing token: %s", new Object[]{String.valueOf(iBinder)});
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (displayContent.isDefaultDisplay && displayContent.mInputMethodWindow != null && removeWindowToken.getWindow(new Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$removeWindowToken$2;
                        lambda$removeWindowToken$2 = WindowManagerService.lambda$removeWindowToken$2(DisplayContent.this, (WindowState) obj);
                        return lambda$removeWindowToken$2;
                    }
                }) != null && this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
                    this.mAtmService.mDexController.hideDexImeOnDefaultDisplayLocked();
                }
                if (z) {
                    removeWindowToken.removeAllWindowsIfPossible();
                }
                displayContent.getInputMonitor().updateInputWindowsLw(true);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public static /* synthetic */ boolean lambda$removeWindowToken$2(DisplayContent displayContent, WindowState windowState) {
        return windowState == displayContent.mInputMethodWindow;
    }

    public void removeWindowToken(IBinder iBinder, int i) {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "removeWindowToken()")) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removeWindowToken(iBinder, false, true, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void moveWindowTokenToDisplay(IBinder iBinder, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i);
                if (displayContentOrCreate == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 2060978050, 4, "moveWindowTokenToDisplay: Attempted to move token: %s to non-exiting displayId=%d", new Object[]{String.valueOf(iBinder), Long.valueOf(i)});
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowToken windowToken = this.mRoot.getWindowToken(iBinder);
                if (windowToken == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1033274509, 0, "moveWindowTokenToDisplay: Attempted to move non-existing token: %s", new Object[]{String.valueOf(iBinder)});
                    }
                    resetPriorityAfterLockedSection();
                } else if (windowToken.getDisplayContent() == displayContentOrCreate) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 781471998, 0, "moveWindowTokenToDisplay: Cannot move to the original display for token: %s", new Object[]{String.valueOf(iBinder)});
                    }
                    resetPriorityAfterLockedSection();
                } else {
                    displayContentOrCreate.reParentWindowToken(windowToken);
                    resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void prepareAppTransitionNone() {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "prepareAppTransition()")) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        getDefaultDisplayContentLocked().prepareAppTransition(0);
    }

    public void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(StartingSurfaceController.TAG, "Attempted to call overridePendingAppTransitionMultiThumbFuture for the display " + i + " that does not exist.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                displayContent.mAppTransition.overridePendingAppTransitionMultiThumbFuture(iAppTransitionAnimationSpecsFuture, iRemoteCallback, z);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) {
        if (!checkCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "overridePendingAppTransitionRemote()")) {
            throw new SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(StartingSurfaceController.TAG, "Attempted to call overridePendingAppTransitionRemote for the display " + i + " that does not exist.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                remoteAnimationAdapter.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
                displayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void executeAppTransition() {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "executeAppTransition()")) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        getDefaultDisplayContentLocked().executeAppTransition();
    }

    public void initializeRecentsAnimation(int i, IRecentsAnimationRunner iRecentsAnimationRunner, RecentsAnimationController.RecentsAnimationCallbacks recentsAnimationCallbacks, int i2, SparseBooleanArray sparseBooleanArray, ActivityRecord activityRecord) {
        this.mRecentsAnimationController = new RecentsAnimationController(this, iRecentsAnimationRunner, recentsAnimationCallbacks, i2);
        this.mRoot.getDisplayContent(i2).mAppTransition.updateBooster();
        this.mRecentsAnimationController.initialize(i, sparseBooleanArray, activityRecord);
    }

    public void setRecentsAnimationController(RecentsAnimationController recentsAnimationController) {
        this.mRecentsAnimationController = recentsAnimationController;
    }

    public RecentsAnimationController getRecentsAnimationController() {
        return this.mRecentsAnimationController;
    }

    public void cancelRecentsAnimation(int i, String str) {
        RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
        if (recentsAnimationController != null) {
            recentsAnimationController.cancelAnimation(i, str);
        }
    }

    public void cleanupRecentsAnimation(int i) {
        RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
        if (recentsAnimationController != null) {
            this.mRecentsAnimationController = null;
            recentsAnimationController.cleanupAnimation(i);
            DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
            if (defaultDisplayContentLocked.mAppTransition.isTransitionSet()) {
                defaultDisplayContentLocked.mSkipAppTransitionAnimation = true;
            }
            defaultDisplayContentLocked.forAllWindowContainers(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WindowManagerService.lambda$cleanupRecentsAnimation$3((WindowContainer) obj);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$cleanupRecentsAnimation$3(WindowContainer windowContainer) {
        if (windowContainer.isAnimating(1, 1)) {
            windowContainer.cancelAnimation();
        }
    }

    public boolean isRecentsAnimationTarget(ActivityRecord activityRecord) {
        RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
        return recentsAnimationController != null && recentsAnimationController.isTargetApp(activityRecord);
    }

    public void setWindowOpaqueLocked(IBinder iBinder, boolean z) {
        ActivityRecord activityRecord = this.mRoot.getActivityRecord(iBinder);
        if (activityRecord != null) {
            activityRecord.setMainWindowOpaque(z);
        }
    }

    public boolean isValidPictureInPictureAspectRatio(DisplayContent displayContent, float f) {
        return displayContent.getPinnedTaskController().isValidPictureInPictureAspectRatio(f);
    }

    public boolean isValidExpandedPictureInPictureAspectRatio(DisplayContent displayContent, float f) {
        return displayContent.getPinnedTaskController().isValidExpandedPictureInPictureAspectRatio(f);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void notifyKeyguardTrustedChanged() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mAtmService.mKeyguardController.isKeyguardShowing(0)) {
                    this.mRoot.ensureActivitiesVisible(null, 0, false);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void screenTurningOff(int i, WindowManagerPolicy.ScreenOffListener screenOffListener) {
        this.mTaskSnapshotController.screenTurningOff(i, screenOffListener);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void triggerAnimationFailsafe() {
        this.f1749mH.sendEmptyMessage(60);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void onKeyguardShowingAndNotOccludedChanged() {
        this.f1749mH.sendEmptyMessage(61);
        dispatchKeyguardLockedState();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void onPowerKeyDown(final boolean z) {
        this.mRoot.forAllDisplayPolicies(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((DisplayPolicy) obj).onPowerKeyDown(z);
            }
        });
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void onUserSwitched() {
        this.mSettingsObserver.updateSystemUiSettings(true);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllDisplayPolicies(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DisplayPolicy) obj).resetSystemBarAttributes();
                    }
                });
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void moveDisplayToTopIfAllowed(int i) {
        if (this.mExt.mExtraDisplayPolicy.shouldNotTopDisplay(i)) {
            Slog.i(StartingSurfaceController.TAG, "Not moving display " + i + " to top. Reason: ExtraDisplayPolicy");
            return;
        }
        moveDisplayToTopInternal(i);
        syncInputTransactions(true);
    }

    public void moveDisplayToTopInternal(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && this.mRoot.getTopChild() != displayContent) {
                    if (!displayContent.canStealTopFocus()) {
                        if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 34682671, 5, (String) null, new Object[]{Long.valueOf(i), Long.valueOf(this.mRoot.getTopFocusedDisplayContent().getDisplayId())});
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.getParent().positionChildAt(Integer.MAX_VALUE, displayContent, true);
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public boolean isAppTransitionStateIdle() {
        return getDefaultDisplayContentLocked().mAppTransition.isIdle();
    }

    public void startFreezingScreen(int i, int i2) {
        if (!checkCallingPermission("android.permission.FREEZE_SCREEN", "startFreezingScreen()")) {
            throw new SecurityException("Requires FREEZE_SCREEN permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mClientFreezingScreen) {
                    this.mClientFreezingScreen = true;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        startFreezingDisplay(i, i2);
                        this.f1749mH.removeMessages(30);
                        this.f1749mH.sendEmptyMessageDelayed(30, 5000L);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void stopFreezingScreen() {
        if (!checkCallingPermission("android.permission.FREEZE_SCREEN", "stopFreezingScreen()")) {
            throw new SecurityException("Requires FREEZE_SCREEN permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mClientFreezingScreen) {
                    this.mClientFreezingScreen = false;
                    this.mLastFinishedFreezeSource = "client";
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        stopFreezingDisplayLocked();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void disableKeyguard(IBinder iBinder, String str, int i) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 2, "disableKeyguard", (String) null);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mKeyguardDisableHandler.disableKeyguard(iBinder, str, callingUid, handleIncomingUser);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reenableKeyguard(IBinder iBinder, int i) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 2, "reenableKeyguard", (String) null);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        Objects.requireNonNull(iBinder, "token is null");
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mKeyguardDisableHandler.reenableKeyguard(iBinder, callingUid, handleIncomingUser);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void exitKeyguardSecurely(final IOnKeyguardExitResult iOnKeyguardExitResult) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        if (iOnKeyguardExitResult == null) {
            throw new IllegalArgumentException("callback == null");
        }
        this.mPolicy.exitKeyguardSecurely(new WindowManagerPolicy.OnKeyguardExitResult() { // from class: com.android.server.wm.WindowManagerService.10
            @Override // com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult
            public void onKeyguardExitResult(boolean z) {
                try {
                    iOnKeyguardExitResult.onKeyguardExitResult(z);
                } catch (RemoteException unused) {
                }
            }
        });
    }

    public boolean isKeyguardLocked() {
        return this.mPolicy.isKeyguardLocked();
    }

    public boolean isKeyguardShowingAndNotOccluded() {
        return this.mPolicy.isKeyguardShowingAndNotOccluded();
    }

    public boolean isKeyguardSecure(int i) {
        if (i != UserHandle.getCallingUserId() && !checkCallingPermission("android.permission.INTERACT_ACROSS_USERS", "isKeyguardSecure")) {
            throw new SecurityException("Requires INTERACT_ACROSS_USERS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPolicy.isKeyguardSecure(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        if (!checkCallingPermission("android.permission.CONTROL_KEYGUARD", "dismissKeyguard")) {
            throw new SecurityException("Requires CONTROL_KEYGUARD permission");
        }
        if (this.mAtmService.mKeyguardController.isShowingDream()) {
            this.mAtmService.mTaskSupervisor.wakeUp("leaveDream");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPolicy.dismissKeyguardLw(iKeyguardDismissCallback, charSequence);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) {
        enforceSubscribeToKeyguardLockedStatePermission();
        if (this.mKeyguardLockedStateListeners.register(iKeyguardLockedStateListener)) {
            return;
        }
        Slog.w(StartingSurfaceController.TAG, "Failed to register listener: " + iKeyguardLockedStateListener);
    }

    public void removeKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) {
        enforceSubscribeToKeyguardLockedStatePermission();
        this.mKeyguardLockedStateListeners.unregister(iKeyguardLockedStateListener);
    }

    public final void enforceSubscribeToKeyguardLockedStatePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE", "android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE permission required to subscribe to keyguard locked state changes");
    }

    public final void dispatchKeyguardLockedState() {
        this.f1749mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                WindowManagerService.this.lambda$dispatchKeyguardLockedState$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchKeyguardLockedState$5() {
        boolean isKeyguardShowing = this.mPolicy.isKeyguardShowing();
        if (this.mDispatchedKeyguardLockedState == isKeyguardShowing) {
            return;
        }
        int beginBroadcast = this.mKeyguardLockedStateListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mKeyguardLockedStateListeners.getBroadcastItem(i).onKeyguardLockedStateChanged(isKeyguardShowing);
            } catch (RemoteException unused) {
            }
        }
        this.mKeyguardLockedStateListeners.finishBroadcast();
        this.mDispatchedKeyguardLockedState = isKeyguardShowing;
    }

    public void dispatchImeTargetOverlayVisibilityChanged(final IBinder iBinder, final int i, final boolean z, final boolean z2) {
        if (this.mImeTargetChangeListener != null) {
            this.f1749mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerService.this.lambda$dispatchImeTargetOverlayVisibilityChanged$6(iBinder, i, z, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchImeTargetOverlayVisibilityChanged$6(IBinder iBinder, int i, boolean z, boolean z2) {
        this.mImeTargetChangeListener.onImeTargetOverlayVisibilityChanged(iBinder, i, z, z2);
    }

    public void dispatchImeInputTargetVisibilityChanged(final IBinder iBinder, final boolean z, final boolean z2) {
        if (this.mImeTargetChangeListener != null) {
            this.f1749mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerService.this.lambda$dispatchImeInputTargetVisibilityChanged$7(iBinder, z, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchImeInputTargetVisibilityChanged$7(IBinder iBinder, boolean z, boolean z2) {
        this.mImeTargetChangeListener.onImeInputTargetVisibilityChanged(iBinder, z, z2);
    }

    public void setSwitchingUser(boolean z) {
        if (!checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "setSwitchingUser()")) {
            throw new SecurityException("Requires INTERACT_ACROSS_USERS_FULL permission");
        }
        this.mPolicy.setSwitchingUser(z);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mSwitchingUser = z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void showGlobalActions() {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "showGlobalActions()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        this.mPolicy.showGlobalActions();
    }

    public void closeSystemDialogs(String str) {
        if (this.mAtmService.checkCanCloseSystemDialogs(Binder.getCallingPid(), Binder.getCallingUid(), null)) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mRoot.closeSystemDialogs(str);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public void closeSystemDialogsInDisplay(String str, int i) {
        if (this.mAtmService.checkCanCloseSystemDialogs(Binder.getCallingPid(), Binder.getCallingUid(), null)) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mRoot.closeSystemDialogs(str, i);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public void setAnimationScale(int i, float f) {
        if (!checkCallingPermission("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()")) {
            throw new SecurityException("Requires SET_ANIMATION_SCALE permission");
        }
        float fixScale = WindowManager.fixScale(f);
        if (i == 0) {
            this.mWindowAnimationScaleSetting = fixScale;
        } else if (i == 1) {
            this.mTransitionAnimationScaleSetting = fixScale;
        } else if (i == 2) {
            this.mAnimatorDurationScaleSetting = fixScale;
        }
        this.f1749mH.sendEmptyMessage(14);
    }

    public void setAnimationScales(float[] fArr) {
        if (!checkCallingPermission("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()")) {
            throw new SecurityException("Requires SET_ANIMATION_SCALE permission");
        }
        if (fArr != null) {
            if (fArr.length >= 1) {
                this.mWindowAnimationScaleSetting = WindowManager.fixScale(fArr[0]);
            }
            if (fArr.length >= 2) {
                this.mTransitionAnimationScaleSetting = WindowManager.fixScale(fArr[1]);
            }
            if (fArr.length >= 3) {
                this.mAnimatorDurationScaleSetting = WindowManager.fixScale(fArr[2]);
                dispatchNewAnimatorScaleLocked(null);
            }
        }
        this.f1749mH.sendEmptyMessage(14);
    }

    public final void setAnimatorDurationScale(float f) {
        this.mAnimatorDurationScaleSetting = f;
        ValueAnimator.setDurationScale(f);
    }

    public float getWindowAnimationScaleLocked() {
        return this.mAnimationsDisabled ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : this.mWindowAnimationScaleSetting;
    }

    public float getTransitionAnimationScaleLocked() {
        return this.mAnimationsDisabled ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : this.mTransitionAnimationScaleSetting;
    }

    public float getAnimationScale(int i) {
        if (i == 0) {
            return this.mWindowAnimationScaleSetting;
        }
        if (i != 1) {
            return i != 2 ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : this.mAnimatorDurationScaleSetting;
        }
        return this.mTransitionAnimationScaleSetting;
    }

    public float[] getAnimationScales() {
        return new float[]{this.mWindowAnimationScaleSetting, this.mTransitionAnimationScaleSetting, this.mAnimatorDurationScaleSetting};
    }

    public float getCurrentAnimatorScale() {
        float f;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                f = this.mAnimationsDisabled ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : this.mAnimatorDurationScaleSetting;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return f;
    }

    public void dispatchNewAnimatorScaleLocked(Session session) {
        this.f1749mH.obtainMessage(34, session).sendToTarget();
    }

    public void registerPointerEventListener(WindowManagerPolicyConstants.PointerEventListener pointerEventListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.registerPointerEventListener(pointerEventListener);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void unregisterPointerEventListener(WindowManagerPolicyConstants.PointerEventListener pointerEventListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.unregisterPointerEventListener(pointerEventListener);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public int getLidState() {
        int switchState = this.mInputManager.getSwitchState(-1, -256, 0);
        if (switchState > 0) {
            return 0;
        }
        return switchState == 0 ? 1 : -1;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void lockDeviceNow() {
        lockNow(null);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public int getCameraLensCoverState() {
        int switchState = this.mInputManager.getSwitchState(-1, -256, 9);
        if (switchState > 0) {
            return 1;
        }
        return switchState == 0 ? 0 : -1;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void switchKeyboardLayout(int i, int i2) {
        this.mInputManager.switchKeyboardLayout(i, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void shutdown(boolean z) {
        ShutdownThread.shutdown(ActivityThread.currentActivityThread().getSystemUiContext(), "userrequested", z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void reboot(boolean z) {
        ShutdownThread.reboot(ActivityThread.currentActivityThread().getSystemUiContext(), "userrequested", z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void rebootSafeMode(boolean z) {
        ShutdownThread.rebootSafeMode(ActivityThread.currentActivityThread().getSystemUiContext(), z);
    }

    public void setCurrentUser(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtmService.getTransitionController().requestTransitionIfNeeded(1, null);
                this.mCurrentUserId = i;
                this.mPolicy.setCurrentUserLw(i);
                this.mKeyguardDisableHandler.setCurrentUser(i);
                this.mRoot.switchUser(i);
                this.mWindowPlacerLocked.performSurfacePlacement();
                DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                if (this.mDisplayReady) {
                    int forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(i);
                    if (forcedDisplayDensityForUserLocked == 0) {
                        forcedDisplayDensityForUserLocked = defaultDisplayContentLocked.getInitialDisplayDensity();
                    }
                    defaultDisplayContentLocked.setForcedDensity(forcedDisplayDensityForUserLocked, -2);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public boolean isUserVisible(int i) {
        return this.mUmInternal.isUserVisible(i);
    }

    public int getUserAssignedToDisplay(int i) {
        return this.mUmInternal.getUserAssignedToDisplay(i);
    }

    public boolean shouldPlacePrimaryHomeOnDisplay(int i) {
        return shouldPlacePrimaryHomeOnDisplay(i, this.mUmInternal.getUserAssignedToDisplay(i));
    }

    public boolean shouldPlacePrimaryHomeOnDisplay(int i, int i2) {
        return this.mUmInternal.getMainDisplayAssignedToUser(i2) == i;
    }

    public void enableScreenAfterBoot() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                    boolean z = this.mDisplayEnabled;
                    boolean z2 = this.mForceDisplayEnabled;
                    boolean z3 = this.mShowingBootMessages;
                    boolean z4 = this.mSystemBooted;
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, -1884933373, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, (String) null, new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), String.valueOf(new RuntimeException("here").fillInStackTrace())});
                }
                if (this.mSystemBooted) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mSystemBooted = true;
                hideBootMessagesLocked();
                this.f1749mH.sendEmptyMessageDelayed(23, 30000L);
                resetPriorityAfterLockedSection();
                this.mPolicy.systemBooted();
                performEnableScreen();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void enableScreenIfNeeded() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                enableScreenIfNeededLocked();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void enableScreenIfNeededLocked() {
        if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
            boolean z = this.mDisplayEnabled;
            boolean z2 = this.mForceDisplayEnabled;
            boolean z3 = this.mShowingBootMessages;
            boolean z4 = this.mSystemBooted;
            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, -549028919, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, (String) null, new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), String.valueOf(new RuntimeException("here").fillInStackTrace())});
        }
        if (this.mDisplayEnabled) {
            return;
        }
        if (this.mSystemBooted || this.mShowingBootMessages) {
            this.f1749mH.sendEmptyMessage(16);
        }
    }

    public void performBootTimeout() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mDisplayEnabled) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (ProtoLogCache.WM_ERROR_enabled) {
                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1001904964, 0, "***** BOOT TIMEOUT: forcing display enabled", (Object[]) null);
                }
                this.mForceDisplayEnabled = true;
                resetPriorityAfterLockedSection();
                performEnableScreen();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void onSystemUiStarted() {
        this.mPolicy.onSystemUiStarted();
    }

    public final void performEnableScreen() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, -1256520588, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, (String) null, new Object[]{Boolean.valueOf(this.mDisplayEnabled), Boolean.valueOf(this.mForceDisplayEnabled), Boolean.valueOf(this.mShowingBootMessages), Boolean.valueOf(this.mSystemBooted), String.valueOf(new RuntimeException("here").fillInStackTrace())});
                }
                if (this.mDisplayEnabled) {
                    return;
                }
                if (!this.mSystemBooted && !this.mShowingBootMessages) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mShowingBootMessages && !this.mPolicy.canDismissBootAnimation()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mForceDisplayEnabled) {
                    if (this.mBootWaitForWindowsStartTime < 0) {
                        this.mBootWaitForWindowsStartTime = SystemClock.elapsedRealtime();
                    }
                    for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
                        if (((DisplayContent) this.mRoot.getChildAt(childCount)).shouldWaitForSystemDecorWindowsOnBoot()) {
                            resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.mBootWaitForWindowsStartTime;
                    this.mBootWaitForWindowsStartTime = -1L;
                    if (elapsedRealtime > 10 && ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                        ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, 544101314, 1, (String) null, new Object[]{Long.valueOf(elapsedRealtime)});
                    }
                }
                if (!this.mBootAnimationStopped) {
                    Trace.asyncTraceBegin(32L, "Stop bootanim", 0);
                    SystemProperties.set("service.bootanim.exit", "1");
                    this.mBootAnimationStopped = true;
                }
                if (!this.mForceDisplayEnabled && !checkBootAnimationCompleteLocked()) {
                    if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                        ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, 374972436, 0, (String) null, (Object[]) null);
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!SurfaceControl.bootFinished()) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1323783276, 0, "performEnableScreen: bootFinished() failed.", (Object[]) null);
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                Slog.i(StartingSurfaceController.TAG, "!@Boot_EBS_F: wm_boot_animation_done");
                this.mPolicy.finishedBootAnimation();
                EventLogTags.writeWmBootAnimationDone(SystemClock.uptimeMillis());
                Trace.asyncTraceEnd(32L, "Stop bootanim", 0);
                this.mDisplayEnabled = true;
                if (ProtoLogCache.WM_DEBUG_SCREEN_ON_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_SCREEN_ON, -116086365, 0, (String) null, (Object[]) null);
                }
                Slog.i(StartingSurfaceController.TAG, "!@Boot: Enabling Screen!");
                this.mInputManagerCallback.setEventDispatchingLw(this.mEventDispatchingEnabled);
                resetPriorityAfterLockedSection();
                try {
                    this.mActivityManager.bootAnimationComplete();
                } catch (RemoteException unused) {
                }
                this.mPolicy.enableScreenAfterBoot();
                updateRotationUnchecked(false, false);
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        this.mAtmService.getTransitionController().mIsWaitingForDisplayEnabled = false;
                        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -2088209279, 0, (String) null, (Object[]) null);
                        }
                    } finally {
                    }
                }
                resetPriorityAfterLockedSection();
            } finally {
                resetPriorityAfterLockedSection();
            }
        }
    }

    public final boolean checkBootAnimationCompleteLocked() {
        if (SystemService.isRunning("bootanim")) {
            this.f1749mH.removeMessages(37);
            this.f1749mH.sendEmptyMessageDelayed(37, 50L);
            if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, 600140673, 0, (String) null, (Object[]) null);
            }
            return false;
        }
        if (!ProtoLogCache.WM_DEBUG_BOOT_enabled) {
            return true;
        }
        ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, 1224307091, 0, (String) null, (Object[]) null);
        return true;
    }

    public void showBootMessage(CharSequence charSequence, boolean z) {
        showBootMessage(charSequence, z, 0);
    }

    public void showBootMessage(CharSequence charSequence, boolean z, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean z2 = false;
                if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, -874446906, 1020, (String) null, new Object[]{String.valueOf(charSequence), Boolean.valueOf(z), Boolean.valueOf(this.mAllowBootMessages), Boolean.valueOf(this.mShowingBootMessages), Boolean.valueOf(this.mSystemBooted), String.valueOf(new RuntimeException("here").fillInStackTrace())});
                }
                if (!this.mAllowBootMessages) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mShowingBootMessages) {
                    if (!z) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    z2 = true;
                }
                if (this.mSystemBooted) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mShowingBootMessages = true;
                this.mExt.mPolicyExt.showBootDialog(i);
                resetPriorityAfterLockedSection();
                if (z2) {
                    performEnableScreen();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void hideBootMessagesLocked() {
        if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
            boolean z = this.mDisplayEnabled;
            boolean z2 = this.mForceDisplayEnabled;
            boolean z3 = this.mShowingBootMessages;
            boolean z4 = this.mSystemBooted;
            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, -1350198040, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, (String) null, new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), String.valueOf(new RuntimeException("here").fillInStackTrace())});
        }
        if (this.mShowingBootMessages) {
            this.mShowingBootMessages = false;
            this.mPolicy.hideBootMessages();
        }
    }

    public void setInTouchMode(boolean z, int i) {
        int i2;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (this.mPerDisplayFocusEnabled && (displayContent == null || displayContent.isInTouchMode() == z)) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                boolean z2 = displayContent != null && displayContent.hasOwnFocus();
                if (z2 && displayContent.isInTouchMode() == z) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                int callingPid = Binder.getCallingPid();
                int callingUid = Binder.getCallingUid();
                boolean hasTouchModePermission = hasTouchModePermission(callingPid);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!this.mPerDisplayFocusEnabled && !z2) {
                        int size = this.mRoot.mChildren.size();
                        int i3 = 0;
                        while (i3 < size) {
                            DisplayContent displayContent2 = (DisplayContent) this.mRoot.mChildren.get(i3);
                            if (displayContent2.isInTouchMode() != z && !displayContent2.hasOwnFocus()) {
                                i2 = size;
                                if (this.mInputManager.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, displayContent2.mDisplayId)) {
                                    displayContent2.setInTouchMode(z);
                                }
                                i3++;
                                size = i2;
                            }
                            i2 = size;
                            i3++;
                            size = i2;
                        }
                        resetPriorityAfterLockedSection();
                    }
                    if (this.mInputManager.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, i)) {
                        displayContent.setInTouchMode(z);
                    }
                    resetPriorityAfterLockedSection();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setInTouchModeOnAllDisplays(boolean z) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        boolean hasTouchModePermission = hasTouchModePermission(callingPid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                for (int i = 0; i < this.mRoot.mChildren.size(); i++) {
                    try {
                        DisplayContent displayContent = (DisplayContent) this.mRoot.mChildren.get(i);
                        if (displayContent.isInTouchMode() != z && this.mInputManager.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, displayContent.mDisplayId)) {
                            displayContent.setInTouchMode(z);
                        }
                    } catch (Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean hasTouchModePermission(int i) {
        return this.mAtmService.instrumentationSourceHasPermission(i, "android.permission.MODIFY_TOUCH_MODE_STATE") || checkCallingPermission("android.permission.MODIFY_TOUCH_MODE_STATE", "setInTouchMode()", false);
    }

    public boolean isInTouchMode(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    boolean z = this.mContext.getResources().getBoolean(R.bool.config_cecTvWakeOnOneTouchPlayDisabled_default);
                    resetPriorityAfterLockedSection();
                    return z;
                }
                boolean isInTouchMode = displayContent.isInTouchMode();
                resetPriorityAfterLockedSection();
                return isInTouchMode;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void showEmulatorDisplayOverlayIfNeeded() {
        if (this.mContext.getResources().getBoolean(R.bool.config_tintNotificationActionButtons) && SystemProperties.getBoolean("ro.emulator.circular", false) && Build.IS_EMULATOR) {
            HandlerC2998H handlerC2998H = this.f1749mH;
            handlerC2998H.sendMessage(handlerC2998H.obtainMessage(36));
        }
    }

    public void showEmulatorDisplayOverlay() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mEmulatorDisplayOverlay == null) {
                    this.mEmulatorDisplayOverlay = new EmulatorDisplayOverlay(this.mContext, getDefaultDisplayContentLocked(), (this.mPolicy.getWindowLayerFromTypeLw(2018) * 10000) + 10, this.mTransaction);
                }
                this.mEmulatorDisplayOverlay.setVisibility(true, this.mTransaction);
                this.mTransaction.apply();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void showStrictModeViolation(boolean z) {
        int callingPid = Binder.getCallingPid();
        if (z) {
            HandlerC2998H handlerC2998H = this.f1749mH;
            handlerC2998H.sendMessage(handlerC2998H.obtainMessage(25, 1, callingPid));
            HandlerC2998H handlerC2998H2 = this.f1749mH;
            handlerC2998H2.sendMessageDelayed(handlerC2998H2.obtainMessage(25, 0, callingPid), 1000L);
            return;
        }
        HandlerC2998H handlerC2998H3 = this.f1749mH;
        handlerC2998H3.sendMessage(handlerC2998H3.obtainMessage(25, 0, callingPid));
    }

    public final void showStrictModeViolation(int i, int i2) {
        boolean z = i != 0;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (z) {
                try {
                    if (!this.mRoot.canShowStrictModeViolation(i2)) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (this.mStrictModeFlash == null) {
                this.mStrictModeFlash = new StrictModeFlash(getDefaultDisplayContentLocked(), this.mTransaction);
            }
            this.mStrictModeFlash.setVisibility(z, this.mTransaction);
            this.mTransaction.apply();
            resetPriorityAfterLockedSection();
        }
    }

    public void setStrictModeVisualIndicatorPreference(String str) {
        SystemProperties.set("persist.sys.strictmode.visual", str);
    }

    public Bitmap screenshotWallpaper() {
        Bitmap screenshotWallpaperLocked;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "screenshotWallpaper()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        try {
            Trace.traceBegin(32L, "screenshotWallpaper");
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    screenshotWallpaperLocked = this.mRoot.getDisplayContent(0).mWallpaperController.screenshotWallpaperLocked();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return screenshotWallpaperLocked;
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public SurfaceControl mirrorWallpaperSurface(int i) {
        SurfaceControl mirrorWallpaperSurface;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                mirrorWallpaperSurface = this.mRoot.getDisplayContent(i).mWallpaperController.mirrorWallpaperSurface();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return mirrorWallpaperSurface;
    }

    public final ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot() {
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer;
        ScreenCapture.LayerCaptureArgs layerCaptureArgs;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "requestAssistScreenshot()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(0);
                screenshotHardwareBuffer = null;
                if (displayContent == null) {
                    Slog.i(StartingSurfaceController.TAG, "Screenshot returning null. No Display for displayId=0");
                    layerCaptureArgs = null;
                } else {
                    layerCaptureArgs = displayContent.getLayerCaptureArgs(true);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        if (layerCaptureArgs != null) {
            ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
            ScreenCapture.captureLayers(layerCaptureArgs, createSyncCaptureListener);
            screenshotHardwareBuffer = createSyncCaptureListener.getBuffer();
        }
        if (screenshotHardwareBuffer == null) {
            Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot");
        }
        return screenshotHardwareBuffer;
    }

    public boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) {
        return omniRequestAssistScreenshot(iAssistDataReceiver, false);
    }

    public boolean omniRequestAssistScreenshot(final IAssistDataReceiver iAssistDataReceiver, boolean z) {
        final Bitmap screenshotDisplayLocked;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "requestAssistScreenshot()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(0);
                if (displayContent == null) {
                    Slog.i(StartingSurfaceController.TAG, "Screenshot returning null. No Display for displayId=0");
                    screenshotDisplayLocked = null;
                } else {
                    screenshotDisplayLocked = displayContent.screenshotDisplayLocked(z);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        FgThread.getHandler().post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                WindowManagerService.lambda$omniRequestAssistScreenshot$8(iAssistDataReceiver, screenshotDisplayLocked);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$omniRequestAssistScreenshot$8(IAssistDataReceiver iAssistDataReceiver, Bitmap bitmap) {
        try {
            iAssistDataReceiver.onHandleAssistScreenshot(bitmap);
        } catch (RemoteException unused) {
        }
    }

    public TaskSnapshot getTaskSnapshot(int i, int i2, boolean z, boolean z2) {
        return this.mTaskSnapshotController.getSnapshot(i, i2, z2, z);
    }

    public Bitmap captureTaskBitmap(int i, ScreenCapture.LayerCaptureArgs.Builder builder) {
        if (this.mTaskSnapshotController.shouldDisableSnapshots()) {
            return null;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mRoot.anyTaskForId(i);
                if (anyTaskForId == null) {
                    resetPriorityAfterLockedSection();
                    return null;
                }
                anyTaskForId.getBounds(this.mTmpRect);
                this.mTmpRect.offsetTo(0, 0);
                ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(builder.setLayer(anyTaskForId.getSurfaceControl()).setSourceCrop(this.mTmpRect).build());
                if (captureLayers == null) {
                    Slog.w(StartingSurfaceController.TAG, "Could not get screenshot buffer for taskId: " + i);
                    resetPriorityAfterLockedSection();
                    return null;
                }
                Bitmap asBitmap = captureLayers.asBitmap();
                resetPriorityAfterLockedSection();
                return asBitmap;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void removeObsoleteTaskFiles(ArraySet arraySet, int[] iArr) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mTaskSnapshotController.removeObsoleteTaskFiles(arraySet, iArr);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void setFixedToUserRotation(int i, int i2) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "setFixedToUserRotation()")) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Trying to set fixed to user rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.getDisplayRotation().setFixedToUserRotation(i2);
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getFixedToUserRotation(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(StartingSurfaceController.TAG, "Trying to get fixed to user rotation for a missing display.");
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int fixedToUserRotationMode = displayContent.getDisplayRotation().getFixedToUserRotationMode();
                resetPriorityAfterLockedSection();
                return fixedToUserRotationMode;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setIgnoreOrientationRequest(int i, boolean z) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "setIgnoreOrientationRequest()")) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Trying to setIgnoreOrientationRequest() for a missing display.");
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    if (displayContent.isDefaultDisplay) {
                        BoundsCompatUtils.setSupportsBoundsCompat(!z);
                        if (CoreRune.FW_ORIENTATION_CONTROL_DEFAULT_ENABLED) {
                            this.mAtmService.mExt.mOrientationController.mDefaultEnabled = false;
                        }
                    }
                    displayContent.setIgnoreOrientationRequest(z);
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getIgnoreOrientationRequest(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(StartingSurfaceController.TAG, "Trying to getIgnoreOrientationRequest() for a missing display.");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean ignoreOrientationRequest = displayContent.getIgnoreOrientationRequest();
                resetPriorityAfterLockedSection();
                return ignoreOrientationRequest;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2) {
        this.mOrientationMapping.clear();
        if (iArr != null && iArr2 != null && iArr.length == iArr2.length) {
            for (int i = 0; i < iArr.length; i++) {
                this.mOrientationMapping.put(iArr[i], iArr2[i]);
            }
        }
        if (z == this.mIsIgnoreOrientationRequestDisabled) {
            return;
        }
        this.mIsIgnoreOrientationRequestDisabled = z;
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            ((DisplayContent) this.mRoot.getChildAt(childCount)).onIsIgnoreOrientationRequestDisabledChanged();
        }
    }

    public int mapOrientationRequest(int i) {
        return !this.mIsIgnoreOrientationRequestDisabled ? i : this.mOrientationMapping.get(i, i);
    }

    public boolean isIgnoreOrientationRequestDisabled() {
        return this.mIsIgnoreOrientationRequestDisabled || !this.mLetterboxConfiguration.isIgnoreOrientationRequestAllowed();
    }

    public void freezeRotation(int i) {
        freezeDisplayRotation(0, i);
    }

    public void freezeDisplayRotation(int i, int i2) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "freezeRotation()")) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        }
        if (i2 < -1 || i2 > 3) {
            throw new IllegalArgumentException("Rotation argument must be -1 or a valid rotation constant.");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Trying to freeze rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.getDisplayRotation().freezeRotation(i2);
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    EventLogTags.writeWmUserRotationChanged(i2, i, Binder.getCallingPid());
                    updateRotationUnchecked(false, false);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void thawRotation() {
        thawDisplayRotation(0);
    }

    public void thawDisplayRotation(int i) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "thawRotation()")) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        }
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1076978367, 1, (String) null, new Object[]{Long.valueOf(getDefaultDisplayRotation())});
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Trying to thaw rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.getDisplayRotation().thawRotation();
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    EventLogTags.writeWmUserRotationChanged(777, i, Binder.getCallingPid());
                    updateRotationUnchecked(false, false);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isRotationFrozen() {
        return isDisplayRotationFrozen(0);
    }

    public boolean isDisplayRotationFrozen(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(StartingSurfaceController.TAG, "Trying to check if rotation is frozen on a missing display.");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean isRotationFrozen = displayContent.getDisplayRotation().isRotationFrozen();
                resetPriorityAfterLockedSection();
                return isRotationFrozen;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getDisplayUserRotation(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(StartingSurfaceController.TAG, "Trying to get user rotation of a missing display.");
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int userRotation = displayContent.getDisplayRotation().getUserRotation();
                resetPriorityAfterLockedSection();
                return userRotation;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void updateRotation(boolean z, boolean z2) {
        updateRotationUnchecked(z, z2);
    }

    public final void updateRotationUnchecked(boolean z, boolean z2) {
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -198463978, 15, (String) null, new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2)});
        }
        Trace.traceBegin(32L, "updateRotation");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    int size = this.mRoot.mChildren.size();
                    boolean z3 = false;
                    for (int i = 0; i < size; i++) {
                        DisplayContent displayContent = (DisplayContent) this.mRoot.mChildren.get(i);
                        Trace.traceBegin(32L, "updateRotation: display");
                        boolean updateRotationUnchecked = displayContent.updateRotationUnchecked();
                        Trace.traceEnd(32L);
                        if (updateRotationUnchecked) {
                            this.mAtmService.getTaskChangeNotificationController().notifyOnActivityRotation(displayContent.mDisplayId);
                        }
                        if (!(updateRotationUnchecked && (displayContent.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange() || displayContent.mTransitionController.isCollecting()))) {
                            if (z2) {
                                displayContent.setLayoutNeeded();
                                z3 = true;
                            }
                            if (updateRotationUnchecked || z) {
                                displayContent.sendNewConfiguration();
                            }
                        }
                    }
                    if (z3) {
                        Trace.traceBegin(32L, "updateRotation: performSurfacePlacement");
                        this.mWindowPlacerLocked.performSurfacePlacement();
                        Trace.traceEnd(32L);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Trace.traceEnd(32L);
        }
    }

    public int getDefaultDisplayRotation() {
        int rotation;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                rotation = getDefaultDisplayContentLocked().getRotation();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return rotation;
    }

    public void setDisplayChangeWindowController(IDisplayChangeWindowController iDisplayChangeWindowController) {
        ActivityTaskManagerService.enforceTaskPermission("setDisplayWindowRotationController");
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    IDisplayChangeWindowController iDisplayChangeWindowController2 = this.mDisplayChangeController;
                    if (iDisplayChangeWindowController2 != null) {
                        iDisplayChangeWindowController2.asBinder().unlinkToDeath(this.mDisplayChangeControllerDeath, 0);
                        this.mDisplayChangeController = null;
                    }
                    iDisplayChangeWindowController.asBinder().linkToDeath(this.mDisplayChangeControllerDeath, 0);
                    this.mDisplayChangeController = iDisplayChangeWindowController;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } catch (RemoteException e) {
            throw new RuntimeException("Unable to set rotation controller", e);
        }
    }

    /* JADX WARN: Finally extract failed */
    public SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_APP_TOKENS") != 0) {
            throw new SecurityException("Must hold permission android.permission.MANAGE_APP_TOKENS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        SurfaceControl addShellRoot = displayContent.addShellRoot(iWindow, i2);
                        resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return addShellRoot;
                    }
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    /* JADX WARN: Finally extract failed */
    public void setShellRootAccessibilityWindow(int i, int i2, IWindow iWindow) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_APP_TOKENS") != 0) {
            throw new SecurityException("Must hold permission android.permission.MANAGE_APP_TOKENS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        ShellRoot shellRoot = (ShellRoot) displayContent.mShellRoots.get(i2);
                        if (shellRoot != null) {
                            shellRoot.setAccessibilityWindow(iWindow);
                            resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setDisplayWindowInsetsController(int i, IDisplayWindowInsetsController iDisplayWindowInsetsController) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_APP_TOKENS") != 0) {
            throw new SecurityException("Must hold permission android.permission.MANAGE_APP_TOKENS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setRemoteInsetsController(iDisplayWindowInsetsController);
                        resetPriorityAfterLockedSection();
                    } else {
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateDisplayWindowRequestedVisibleTypes(int i, int i2) {
        DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_APP_TOKENS") != 0) {
            throw new SecurityException("Must hold permission android.permission.MANAGE_APP_TOKENS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null && (remoteInsetsControlTarget = displayContent.mRemoteInsetsControlTarget) != null) {
                        remoteInsetsControlTarget.setRequestedVisibleTypes(i2);
                        displayContent.getInsetsStateController().onInsetsModified(displayContent.mRemoteInsetsControlTarget);
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int watchRotation(IRotationWatcher iRotationWatcher, int i) {
        int rotation;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register rotation event for invalid display: " + i);
                }
                this.mRotationWatcherController.registerDisplayRotationWatcher(iRotationWatcher, i);
                rotation = displayContent.getRotation();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return rotation;
    }

    public void removeRotationWatcher(IRotationWatcher iRotationWatcher) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRotationWatcherController.removeRotationWatcher(iRotationWatcher);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher) {
        int proposedRotation;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowContainer associatedWindowContainer = this.mRotationWatcherController.getAssociatedWindowContainer(iBinder);
                if (associatedWindowContainer == null) {
                    Slog.w(StartingSurfaceController.TAG, "Register rotation listener from non-existing token, uid=" + Binder.getCallingUid());
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                this.mRotationWatcherController.registerProposedRotationListener(iRotationWatcher, iBinder);
                WindowOrientationListener orientationListener = associatedWindowContainer.mDisplayContent.getDisplayRotation().getOrientationListener();
                if (orientationListener != null && (proposedRotation = orientationListener.getProposedRotation()) >= 0) {
                    resetPriorityAfterLockedSection();
                    return proposedRotation;
                }
                int rotation = associatedWindowContainer.getWindowConfiguration().getRotation();
                resetPriorityAfterLockedSection();
                return rotation;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        boolean isWallpaperVisible;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register visibility event for invalid display: " + i);
                }
                this.mWallpaperVisibilityListeners.registerWallpaperVisibilityListener(iWallpaperVisibilityListener, i);
                isWallpaperVisible = displayContent.mWallpaperController.isWallpaperVisible();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return isWallpaperVisible;
    }

    public void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWallpaperVisibilityListeners.unregisterWallpaperVisibilityListener(iWallpaperVisibilityListener, i);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register visibility event for invalid display: " + i);
                }
                displayContent.registerSystemGestureExclusionListener(iSystemGestureExclusionListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register visibility event for invalid display: " + i);
                }
                displayContent.unregisterSystemGestureExclusionListener(iSystemGestureExclusionListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void reportSystemGestureExclusionChanged(Session session, IWindow iWindow, List list) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, true);
                if (windowForClientLocked.setSystemGestureExclusion(list)) {
                    windowForClientLocked.getDisplayContent().updateSystemGestureExclusion();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void reportKeepClearAreasChanged(Session session, IWindow iWindow, List list, List list2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, true);
                if (windowForClientLocked.setKeepClearAreas(list, list2)) {
                    windowForClientLocked.getDisplayContent().updateKeepClearAreas();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void registerDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        this.mPolicy.registerDisplayFoldListener(iDisplayFoldListener);
    }

    public void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        this.mPolicy.unregisterDisplayFoldListener(iDisplayFoldListener);
    }

    public void setOverrideFoldedArea(Rect rect) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mPolicy.setOverrideFoldedArea(rect);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public Rect getFoldedArea() {
        Rect foldedArea;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    foldedArea = this.mPolicy.getFoldedArea();
                } finally {
                }
            }
            resetPriorityAfterLockedSection();
            return foldedArea;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) {
        ActivityTaskManagerService.enforceTaskPermission("registerDisplayWindowListener");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDisplayNotificationController.registerListener(iDisplayWindowListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) {
        ActivityTaskManagerService.enforceTaskPermission("unregisterDisplayWindowListener");
        this.mDisplayNotificationController.unregisterListener(iDisplayWindowListener);
    }

    public int getPreferredOptionsPanelGravity(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return 81;
                }
                int preferredOptionsPanelGravity = displayContent.getPreferredOptionsPanelGravity();
                resetPriorityAfterLockedSection();
                return preferredOptionsPanelGravity;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean startViewServer(int i) {
        if (isSystemSecure() || !checkCallingPermission("android.permission.DUMP", "startViewServer") || i < 1024) {
            return false;
        }
        ViewServer viewServer = this.mViewServer;
        if (viewServer != null) {
            if (!viewServer.isRunning()) {
                try {
                    return this.mViewServer.start();
                } catch (IOException unused) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1545962566, 0, "View server did not start", (Object[]) null);
                    }
                }
            }
            return false;
        }
        try {
            ViewServer viewServer2 = new ViewServer(this, i);
            this.mViewServer = viewServer2;
            return viewServer2.start();
        } catch (IOException unused2) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1545962566, 0, "View server did not start", (Object[]) null);
            }
            return false;
        }
    }

    public final boolean isSystemSecure() {
        return "1".equals(SystemProperties.get("ro.secure", "1")) && "0".equals(SystemProperties.get("ro.debuggable", "0"));
    }

    public boolean stopViewServer() {
        ViewServer viewServer;
        if (isSystemSecure() || !checkCallingPermission("android.permission.DUMP", "stopViewServer") || (viewServer = this.mViewServer) == null) {
            return false;
        }
        return viewServer.stop();
    }

    public boolean isViewServerRunning() {
        ViewServer viewServer;
        return !isSystemSecure() && checkCallingPermission("android.permission.DUMP", "isViewServerRunning") && (viewServer = this.mViewServer) != null && viewServer.isRunning();
    }

    public boolean viewServerListWindows(Socket socket) {
        BufferedWriter bufferedWriter;
        Throwable th;
        boolean z = false;
        if (isSystemSecure()) {
            return false;
        }
        final ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        arrayList.add((WindowState) obj);
                    }
                }, false);
            } catch (Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        resetPriorityAfterLockedSection();
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), IInstalld.FLAG_FORCE);
            } catch (IOException unused) {
            }
            try {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    WindowState windowState = (WindowState) arrayList.get(i);
                    bufferedWriter.write(Integer.toHexString(System.identityHashCode(windowState)));
                    bufferedWriter.write(32);
                    bufferedWriter.append(windowState.mAttrs.getTitle());
                    bufferedWriter.write(10);
                }
                bufferedWriter.write("DONE.\n");
                bufferedWriter.flush();
                bufferedWriter.close();
                z = true;
            } catch (Exception unused2) {
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                return z;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (Exception unused4) {
        } catch (Throwable th4) {
            bufferedWriter = null;
            th = th4;
        }
        return z;
    }

    public boolean viewServerGetFocusedWindow(Socket socket) {
        boolean z = false;
        if (isSystemSecure()) {
            return false;
        }
        WindowState focusedWindow = getFocusedWindow();
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), IInstalld.FLAG_FORCE);
                if (focusedWindow != null) {
                    try {
                        bufferedWriter2.write(Integer.toHexString(System.identityHashCode(focusedWindow)));
                        bufferedWriter2.write(32);
                        bufferedWriter2.append(focusedWindow.mAttrs.getTitle());
                    } catch (Exception unused) {
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                }
                bufferedWriter2.write(10);
                bufferedWriter2.flush();
                bufferedWriter2.close();
                z = true;
            } catch (IOException unused3) {
            }
        } catch (Exception unused4) {
        } catch (Throwable th2) {
            th = th2;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00b3 A[Catch: all -> 0x00dd, TRY_LEAVE, TryCatch #7 {all -> 0x00dd, blocks: (B:32:0x00af, B:34:0x00b3), top: B:31:0x00af }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean viewServerWindowCommand(Socket socket, String str, String str2) {
        BufferedWriter bufferedWriter;
        Parcel parcel;
        if (isSystemSecure()) {
            return false;
        }
        Parcel parcel2 = null;
        BufferedWriter bufferedWriter2 = null;
        parcel2 = null;
        try {
            int indexOf = str2.indexOf(32);
            if (indexOf == -1) {
                indexOf = str2.length();
            }
            int parseLong = (int) Long.parseLong(str2.substring(0, indexOf), 16);
            str2 = indexOf < str2.length() ? str2.substring(indexOf + 1) : "";
            WindowState findWindow = findWindow(parseLong);
            if (findWindow == null) {
                return false;
            }
            Parcel obtain = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("android.view.IWindow");
                obtain.writeString(str);
                obtain.writeString(str2);
                obtain.writeInt(1);
                ParcelFileDescriptor.fromSocket(socket).writeToParcel(obtain, 0);
                parcel = Parcel.obtain();
                try {
                    findWindow.mClient.asBinder().transact(1, obtain, parcel, 0);
                    parcel.readException();
                    if (!socket.isOutputShutdown()) {
                        BufferedWriter bufferedWriter3 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        try {
                            bufferedWriter3.write("DONE\n");
                            bufferedWriter3.flush();
                            bufferedWriter2 = bufferedWriter3;
                        } catch (Exception e) {
                            parcel2 = obtain;
                            bufferedWriter = bufferedWriter3;
                            e = e;
                            try {
                                if (ProtoLogCache.WM_ERROR_enabled) {
                                }
                                if (parcel2 != null) {
                                }
                                if (parcel != null) {
                                }
                                if (bufferedWriter != null) {
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (parcel2 != null) {
                                    parcel2.recycle();
                                }
                                if (parcel != null) {
                                    parcel.recycle();
                                }
                                if (bufferedWriter != null) {
                                    try {
                                        bufferedWriter.close();
                                    } catch (IOException unused) {
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            parcel2 = obtain;
                            bufferedWriter = bufferedWriter3;
                            th = th2;
                            if (parcel2 != null) {
                            }
                            if (parcel != null) {
                            }
                            if (bufferedWriter != null) {
                            }
                            throw th;
                        }
                    }
                    obtain.recycle();
                    parcel.recycle();
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return true;
                } catch (Exception e2) {
                    e = e2;
                    bufferedWriter = null;
                    parcel2 = obtain;
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 2086878461, 0, "Could not send command %s with parameters %s. %s", new Object[]{String.valueOf(str), String.valueOf(str2), String.valueOf(e)});
                    }
                    if (parcel2 != null) {
                        parcel2.recycle();
                    }
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    if (bufferedWriter != null) {
                        return false;
                    }
                    try {
                        bufferedWriter.close();
                        return false;
                    } catch (IOException unused3) {
                        return false;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedWriter = null;
                    parcel2 = obtain;
                    if (parcel2 != null) {
                    }
                    if (parcel != null) {
                    }
                    if (bufferedWriter != null) {
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedWriter = null;
                parcel = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedWriter = null;
                parcel = null;
            }
        } catch (Exception e4) {
            e = e4;
            bufferedWriter = null;
            parcel = null;
        } catch (Throwable th5) {
            th = th5;
            bufferedWriter = null;
            parcel = null;
        }
    }

    public void addWindowChangeListener(WindowChangeListener windowChangeListener) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWindowChangeListeners.add(windowChangeListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void removeWindowChangeListener(WindowChangeListener windowChangeListener) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWindowChangeListeners.remove(windowChangeListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void notifyWindowsChanged() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mWindowChangeListeners.isEmpty()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowChangeListener[] windowChangeListenerArr = (WindowChangeListener[]) this.mWindowChangeListeners.toArray(new WindowChangeListener[this.mWindowChangeListeners.size()]);
                resetPriorityAfterLockedSection();
                for (WindowChangeListener windowChangeListener : windowChangeListenerArr) {
                    windowChangeListener.windowsChanged();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyFocusChanged() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mWindowChangeListeners.isEmpty()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowChangeListener[] windowChangeListenerArr = (WindowChangeListener[]) this.mWindowChangeListeners.toArray(new WindowChangeListener[this.mWindowChangeListeners.size()]);
                resetPriorityAfterLockedSection();
                for (WindowChangeListener windowChangeListener : windowChangeListenerArr) {
                    windowChangeListener.focusChanged();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final WindowState findWindow(final int i) {
        WindowState window;
        if (i == -1) {
            return getFocusedWindow();
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                window = this.mRoot.getWindow(new Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$findWindow$10;
                        lambda$findWindow$10 = WindowManagerService.lambda$findWindow$10(i, (WindowState) obj);
                        return lambda$findWindow$10;
                    }
                });
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return window;
    }

    public static /* synthetic */ boolean lambda$findWindow$10(int i, WindowState windowState) {
        return System.identityHashCode(windowState) == i;
    }

    public Configuration computeNewConfiguration(int i) {
        Configuration computeNewConfigurationLocked;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                computeNewConfigurationLocked = computeNewConfigurationLocked(i);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return computeNewConfigurationLocked;
    }

    public final Configuration computeNewConfigurationLocked(int i) {
        if (!this.mDisplayReady) {
            return null;
        }
        Configuration configuration = new Configuration();
        this.mRoot.getDisplayContent(i).computeScreenConfiguration(configuration);
        return configuration;
    }

    public void notifyHardKeyboardStatusChange() {
        WindowManagerInternal.OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener;
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                onHardKeyboardStatusChangeListener = this.mHardKeyboardStatusChangeListener;
                z = this.mHardKeyboardAvailable;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        if (onHardKeyboardStatusChangeListener != null) {
            onHardKeyboardStatusChangeListener.onHardKeyboardStatusChange(z);
        }
    }

    public void setEventDispatching(boolean z) {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setEventDispatching()")) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mEventDispatchingEnabled = z;
                if (this.mDisplayEnabled) {
                    this.mInputManagerCallback.setEventDispatchingLw(z);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public WindowState getFocusedWindow() {
        WindowState focusedWindowLocked;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                focusedWindowLocked = getFocusedWindowLocked();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return focusedWindowLocked;
    }

    public WindowState getFocusedWindowLocked() {
        return this.mRoot.getTopFocusedDisplayContent().mCurrentFocus;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:10|(1:62)(1:16)|17|(6:18|19|20|21|22|23)|(1:55)(2:26|(9:28|29|30|(6:(1:33)(1:51)|34|35|(5:37|(1:39)(1:46)|40|(1:42)|43)(2:47|(1:49))|44|45)|52|35|(0)(0)|44|45))|54|29|30|(0)|52|35|(0)(0)|44|45) */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x011b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean detectSafeMode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        String str;
        int i7;
        int i8 = 1;
        if (!this.mInputManagerCallback.waitForInputDevicesReady(1000L) && ProtoLogCache.WM_ERROR_enabled) {
            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1774661765, 1, "Devices still not ready after waiting %d milliseconds before attempting to detect safe mode.", new Object[]{1000L});
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "safe_boot_disallowed", 0) != 0) {
            return false;
        }
        int keyCodeState = this.mInputManager.getKeyCodeState(-1, -256, 82);
        int keyCodeState2 = this.mInputManager.getKeyCodeState(-1, -256, 47);
        int keyCodeState3 = this.mInputManager.getKeyCodeState(-1, FrameworkStatsLog.HEARING_AID_INFO_REPORTED, 23);
        int scanCodeState = this.mInputManager.getScanCodeState(-1, 65540, 272);
        int keyCodeState4 = this.mInputManager.getKeyCodeState(-1, -256, 25);
        this.mSafeMode = keyCodeState > 0 || keyCodeState2 > 0 || keyCodeState3 > 0 || scanCodeState > 0 || keyCodeState4 > 0;
        try {
            i = SystemProperties.getInt("persist.sys.safemode", 0);
            try {
                i2 = SystemProperties.getInt("ro.sys.safemode", 0);
                try {
                } catch (IllegalArgumentException unused) {
                    i3 = 0;
                }
            } catch (IllegalArgumentException unused2) {
                i2 = 0;
                i3 = i2;
                i8 = 0;
                if (!this.mSafeMode) {
                }
                this.mPolicy.setSafeMode(this.mSafeMode);
                return this.mSafeMode;
            }
        } catch (IllegalArgumentException unused3) {
            i = 0;
            i2 = 0;
        }
        if (i != 0 || i2 != 0) {
            this.mSafeMode = true;
            SystemProperties.set("persist.sys.safemode", "");
        } else {
            i3 = 3;
            if (SystemProperties.getInt("ro.boot_recovery", 0) == 3) {
                this.mSafeMode = true;
                if ("emergency".equals(SystemProperties.get("persist.sys.emergency_reset", ""))) {
                    this.mSafeMode = keyCodeState4 <= 0;
                    if (!this.mSafeMode) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            i6 = i2;
                            i4 = i8;
                            i5 = i;
                            ProtoLogImpl.i(ProtoLogGroup.WM_ERROR, -1443029505, 85, "SAFE MODE ENABLED (menu=%d s=%d dpad=%d trackball=%d)", new Object[]{Long.valueOf(keyCodeState), Long.valueOf(keyCodeState2), Long.valueOf(keyCodeState3), Long.valueOf(scanCodeState)});
                            str = "ro.sys.safemode";
                            i7 = 0;
                        } else {
                            i4 = i8;
                            i5 = i;
                            i6 = i2;
                            str = "ro.sys.safemode";
                            i7 = 0;
                        }
                        if (SystemProperties.getInt(str, i7) == 0) {
                            SystemProperties.set(str, "1");
                        }
                        this.mExt.updateSafeModeReason(new int[]{keyCodeState, keyCodeState2, keyCodeState3, scanCodeState, keyCodeState4}, new int[]{i5, i6, i3, i4});
                    } else if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.i(ProtoLogGroup.WM_ERROR, 1866772666, 0, "SAFE MODE not enabled", (Object[]) null);
                    }
                    this.mPolicy.setSafeMode(this.mSafeMode);
                    return this.mSafeMode;
                }
                i8 = 0;
                if (!this.mSafeMode) {
                }
                this.mPolicy.setSafeMode(this.mSafeMode);
                return this.mSafeMode;
            }
        }
        i3 = 0;
        if ("emergency".equals(SystemProperties.get("persist.sys.emergency_reset", ""))) {
        }
        i8 = 0;
        if (!this.mSafeMode) {
        }
        this.mPolicy.setSafeMode(this.mSafeMode);
        return this.mSafeMode;
    }

    public void displayReady() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mMaxUiWidth > 0) {
                    this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda21
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.this.lambda$displayReady$11((DisplayContent) obj);
                        }
                    });
                }
                applyForcedPropertiesForDefaultDisplay();
                this.mAnimator.ready();
                this.mDisplayReady = true;
                this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda22
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DisplayContent) obj).reconfigureDisplayLocked();
                    }
                });
                this.mIsTouchDevice = this.mContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen");
                this.mIsFakeTouchDevice = this.mContext.getPackageManager().hasSystemFeature("android.hardware.faketouch");
                this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda23
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        WindowManagerService.lambda$displayReady$12((DisplayContent) obj);
                    }
                });
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        this.mAtmService.updateConfiguration(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayReady$11(DisplayContent displayContent) {
        displayContent.setMaxUiWidth(this.mMaxUiWidth);
    }

    public static /* synthetic */ void lambda$displayReady$12(DisplayContent displayContent) {
        MultiWindowPointerEventListener multiWindowPointerEventListener = displayContent.mMultiWindowPointerEventListener;
        if (multiWindowPointerEventListener != null) {
            multiWindowPointerEventListener.onDisplayReady();
        }
    }

    public void systemReady() {
        this.mSystemReady = true;
        this.mPolicy.systemReady();
        this.mRoot.forAllDisplayPolicies(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((DisplayPolicy) obj).systemReady();
            }
        });
        this.mSnapshotController.systemReady();
        this.mHasWideColorGamutSupport = queryWideColorGamutSupport();
        this.mHasHdrSupport = queryHdrSupport();
        Handler handler = UiThread.getHandler();
        final SettingsObserver settingsObserver = this.mSettingsObserver;
        Objects.requireNonNull(settingsObserver);
        handler.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                WindowManagerService.SettingsObserver.this.loadSettings();
            }
        });
        IVrManager asInterface = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                boolean vrModeState = asInterface.getVrModeState();
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        asInterface.registerListener(this.mVrStateCallbacks);
                        if (vrModeState) {
                            this.mVrModeEnabled = vrModeState;
                            this.mVrStateCallbacks.onVrStateChanged(vrModeState);
                        }
                    } catch (Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                resetPriorityAfterLockedSection();
            } catch (RemoteException unused) {
            }
        }
        this.mExt.systemReady();
    }

    public static boolean queryWideColorGamutSupport() {
        Optional has_wide_color_display = SurfaceFlingerProperties.has_wide_color_display();
        if (has_wide_color_display.isPresent()) {
            return ((Boolean) has_wide_color_display.get()).booleanValue();
        }
        try {
            OptionalBool hasWideColorDisplay = ISurfaceFlingerConfigs.getService().hasWideColorDisplay();
            if (hasWideColorDisplay != null) {
                return hasWideColorDisplay.value;
            }
            return false;
        } catch (RemoteException | NoSuchElementException unused) {
            return false;
        }
    }

    public static boolean queryHdrSupport() {
        Optional has_HDR_display = SurfaceFlingerProperties.has_HDR_display();
        if (has_HDR_display.isPresent()) {
            return ((Boolean) has_HDR_display.get()).booleanValue();
        }
        try {
            OptionalBool hasHDRDisplay = ISurfaceFlingerConfigs.getService().hasHDRDisplay();
            if (hasHDRDisplay != null) {
                return hasHDRDisplay.value;
            }
            return false;
        } catch (RemoteException | NoSuchElementException unused) {
            return false;
        }
    }

    public InputTarget getInputTargetFromToken(IBinder iBinder) {
        WindowState windowState = (WindowState) this.mInputToWindowMap.get(iBinder);
        if (windowState != null) {
            return windowState;
        }
        EmbeddedWindowController.EmbeddedWindow embeddedWindow = this.mEmbeddedWindowController.get(iBinder);
        if (embeddedWindow != null) {
            return embeddedWindow;
        }
        return null;
    }

    public InputTarget getInputTargetFromWindowTokenLocked(IBinder iBinder) {
        InputTarget inputTarget = (InputTarget) this.mWindowMap.get(iBinder);
        return inputTarget != null ? inputTarget : this.mEmbeddedWindowController.getByWindowToken(iBinder);
    }

    public void reportFocusChanged(IBinder iBinder, IBinder iBinder2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                InputTarget inputTargetFromToken = getInputTargetFromToken(iBinder);
                InputTarget inputTargetFromToken2 = getInputTargetFromToken(iBinder2);
                if (inputTargetFromToken2 == null && inputTargetFromToken == null) {
                    Slog.v(StartingSurfaceController.TAG, "Unknown focus tokens, dropping reportFocusChanged");
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mFocusedInputTarget = inputTargetFromToken2;
                this.mAccessibilityController.onFocusChanged(inputTargetFromToken, inputTargetFromToken2);
                if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 115358443, 0, (String) null, new Object[]{String.valueOf(inputTargetFromToken), String.valueOf(inputTargetFromToken2)});
                }
                resetPriorityAfterLockedSection();
                WindowState windowState = inputTargetFromToken2 != null ? inputTargetFromToken2.getWindowState() : null;
                if (windowState != null && windowState.mInputChannelToken == iBinder2) {
                    this.mAnrController.onFocusChanged(windowState);
                    windowState.reportFocusChangedSerialized(true);
                    notifyFocusChanged();
                }
                WindowState windowState2 = inputTargetFromToken != null ? inputTargetFromToken.getWindowState() : null;
                if (windowState2 == null || windowState2.mInputChannelToken != iBinder) {
                    return;
                }
                windowState2.reportFocusChangedSerialized(false);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.wm.WindowManagerService$H */
    public final class HandlerC2998H extends Handler {
        public HandlerC2998H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Message message2;
            boolean checkBootAnimationCompleteLocked;
            int i = message.what;
            if (i == 11) {
                DisplayContent displayContent = (DisplayContent) message.obj;
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        displayContent.onWindowFreezeTimeout();
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 14) {
                Settings.Global.putFloat(WindowManagerService.this.mContext.getContentResolver(), "window_animation_scale", WindowManagerService.this.mWindowAnimationScaleSetting);
                Settings.Global.putFloat(WindowManagerService.this.mContext.getContentResolver(), "transition_animation_scale", WindowManagerService.this.mTransitionAnimationScaleSetting);
                Settings.Global.putFloat(WindowManagerService.this.mContext.getContentResolver(), "animator_duration_scale", WindowManagerService.this.mAnimatorDurationScaleSetting);
                return;
            }
            if (i == 19) {
                WindowManagerService windowManagerService = WindowManagerService.this;
                if (windowManagerService.mWindowsChanged) {
                    WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            WindowManagerService.this.mWindowsChanged = false;
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    WindowManagerService.this.notifyWindowsChanged();
                    return;
                }
                return;
            }
            if (i == 30) {
                WindowManagerGlobalLock windowManagerGlobalLock3 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock3) {
                    try {
                        WindowManagerService windowManagerService2 = WindowManagerService.this;
                        if (windowManagerService2.mClientFreezingScreen) {
                            windowManagerService2.mClientFreezingScreen = false;
                            windowManagerService2.mLastFinishedFreezeSource = "client-timeout";
                            windowManagerService2.stopFreezingDisplayLocked();
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 32) {
                ActivityRecord activityRecord = (ActivityRecord) message.obj;
                WindowManagerGlobalLock windowManagerGlobalLock4 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock4) {
                    try {
                        if (activityRecord.isAttached()) {
                            activityRecord.getRootTask().notifyActivityDrawnLocked(activityRecord);
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 34) {
                float currentAnimatorScale = WindowManagerService.this.getCurrentAnimatorScale();
                ValueAnimator.setDurationScale(currentAnimatorScale);
                Session session = (Session) message.obj;
                if (session != null) {
                    try {
                        session.mCallback.onAnimatorScaleChanged(currentAnimatorScale);
                        return;
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                ArrayList arrayList = new ArrayList();
                WindowManagerGlobalLock windowManagerGlobalLock5 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock5) {
                    for (int i2 = 0; i2 < WindowManagerService.this.mSessions.size(); i2++) {
                        try {
                            arrayList.add(((Session) WindowManagerService.this.mSessions.valueAt(i2)).mCallback);
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    try {
                        ((IWindowSessionCallback) arrayList.get(i3)).onAnimatorScaleChanged(currentAnimatorScale);
                    } catch (RemoteException unused2) {
                    }
                }
                return;
            }
            if (i == 41) {
                WindowManagerGlobalLock windowManagerGlobalLock6 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock6) {
                    try {
                        DisplayContent displayContent2 = (DisplayContent) message.obj;
                        if (displayContent2 != null) {
                            displayContent2.adjustForImeIfNeeded();
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 55) {
                WindowManagerGlobalLock windowManagerGlobalLock7 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock7) {
                    try {
                        WindowManagerService.this.restorePointerIconLocked((DisplayContent) message.obj, message.arg1, message.arg2);
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 58) {
                WindowManagerService.this.mAmInternal.setHasOverlayUi(message.arg1, message.arg2 == 1);
                return;
            }
            if (i == 200) {
                WindowManagerService.this.mExt.mPolicyExt.handleNotifyPogoKeyboardStatus(message.arg1 != 0);
                return;
            }
            if (i == 16) {
                WindowManagerService.this.performEnableScreen();
                return;
            }
            if (i == 17) {
                WindowManagerGlobalLock windowManagerGlobalLock8 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock8) {
                    try {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -322035974, 0, "App freeze timeout expired.", (Object[]) null);
                        }
                        WindowManagerService windowManagerService3 = WindowManagerService.this;
                        windowManagerService3.mWindowsFreezingScreen = 2;
                        for (int size = windowManagerService3.mAppFreezeListeners.size() - 1; size >= 0; size--) {
                            ((AppFreezeListener) WindowManagerService.this.mAppFreezeListeners.get(size)).onAppFreezeTimeout();
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 51) {
                int i4 = message.arg1;
                if (i4 == 0) {
                    WindowManagerService windowManagerService4 = WindowManagerService.this;
                    windowManagerService4.mWindowAnimationScaleSetting = windowManagerService4.getWindowAnimationScaleSetting();
                    return;
                } else if (i4 == 1) {
                    WindowManagerService windowManagerService5 = WindowManagerService.this;
                    windowManagerService5.mTransitionAnimationScaleSetting = windowManagerService5.getTransitionAnimationScaleSetting();
                    return;
                } else {
                    if (i4 != 2) {
                        return;
                    }
                    WindowManagerService windowManagerService6 = WindowManagerService.this;
                    windowManagerService6.mAnimatorDurationScaleSetting = windowManagerService6.getAnimatorDurationScaleSetting();
                    WindowManagerService.this.dispatchNewAnimatorScaleLocked(null);
                    return;
                }
            }
            if (i != 52) {
                switch (i) {
                    case 22:
                        WindowManagerService.this.notifyHardKeyboardStatusChange();
                        return;
                    case 23:
                        WindowManagerService.this.performBootTimeout();
                        return;
                    case 24:
                        WindowContainer windowContainer = (WindowContainer) message.obj;
                        WindowManagerGlobalLock windowManagerGlobalLock9 = WindowManagerService.this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock9) {
                            try {
                                if (ProtoLogCache.WM_ERROR_enabled) {
                                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1526645239, 0, "Timeout waiting for drawn: undrawn=%s", new Object[]{String.valueOf(windowContainer.mWaitingForDrawn)});
                                }
                                if (Trace.isTagEnabled(32L)) {
                                    for (int i5 = 0; i5 < windowContainer.mWaitingForDrawn.size(); i5++) {
                                        WindowManagerService.this.traceEndWaitingForWindowDrawn((WindowState) windowContainer.mWaitingForDrawn.get(i5));
                                    }
                                }
                                windowContainer.mWaitingForDrawn.clear();
                                message2 = (Message) WindowManagerService.this.mWaitingForDrawnCallbacks.remove(windowContainer);
                            } finally {
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        if (message2 != null) {
                            message2.sendToTarget();
                            return;
                        }
                        return;
                    case 25:
                        WindowManagerService.this.showStrictModeViolation(message.arg1, message.arg2);
                        return;
                    default:
                        switch (i) {
                            case 36:
                                WindowManagerService.this.showEmulatorDisplayOverlay();
                                return;
                            case 37:
                                WindowManagerGlobalLock windowManagerGlobalLock10 = WindowManagerService.this.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock10) {
                                    try {
                                        if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
                                            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_BOOT, 2034780299, 0, (String) null, (Object[]) null);
                                        }
                                        checkBootAnimationCompleteLocked = WindowManagerService.this.checkBootAnimationCompleteLocked();
                                    } finally {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                                if (checkBootAnimationCompleteLocked) {
                                    WindowManagerService.this.performEnableScreen();
                                    return;
                                }
                                return;
                            case 38:
                                WindowManagerGlobalLock windowManagerGlobalLock11 = WindowManagerService.this.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock11) {
                                    try {
                                        WindowManagerService windowManagerService7 = WindowManagerService.this;
                                        windowManagerService7.mLastANRState = null;
                                        windowManagerService7.mAtmService.mLastANRState = null;
                                    } finally {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            case 39:
                                WindowManagerGlobalLock windowManagerGlobalLock12 = WindowManagerService.this.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock12) {
                                    try {
                                        WallpaperController wallpaperController = (WallpaperController) message.obj;
                                        if (wallpaperController != null && wallpaperController.processWallpaperDrawPendingTimeout()) {
                                            WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement();
                                        }
                                    } finally {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            default:
                                switch (i) {
                                    case 60:
                                        WindowManagerGlobalLock windowManagerGlobalLock13 = WindowManagerService.this.mGlobalLock;
                                        WindowManagerService.boostPriorityForLockedSection();
                                        synchronized (windowManagerGlobalLock13) {
                                            try {
                                                if (WindowManagerService.this.mRecentsAnimationController != null) {
                                                    WindowManagerService.this.mRecentsAnimationController.scheduleFailsafe();
                                                }
                                            } finally {
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                            }
                                        }
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        return;
                                    case 61:
                                        WindowManagerGlobalLock windowManagerGlobalLock14 = WindowManagerService.this.mGlobalLock;
                                        WindowManagerService.boostPriorityForLockedSection();
                                        synchronized (windowManagerGlobalLock14) {
                                            try {
                                                WindowManagerService.this.updateFocusedWindowLocked(0, true);
                                            } finally {
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                            }
                                        }
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        return;
                                    case 62:
                                        WindowManagerGlobalLock windowManagerGlobalLock15 = WindowManagerService.this.mGlobalLock;
                                        WindowManagerService.boostPriorityForLockedSection();
                                        synchronized (windowManagerGlobalLock15) {
                                            try {
                                                Object obj = message.obj;
                                                if (obj instanceof SomeArgs) {
                                                    SomeArgs someArgs = (SomeArgs) obj;
                                                    WindowManagerService.this.onPointerDownOutsideFocusLocked(WindowManagerService.this.getInputTargetFromToken((IBinder) someArgs.arg1), someArgs.argi1, someArgs.argi2, someArgs.argi3);
                                                } else {
                                                    WindowManagerService windowManagerService8 = WindowManagerService.this;
                                                    windowManagerService8.onPointerDownOutsideFocusLocked(windowManagerService8.getInputTargetFromToken((IBinder) obj));
                                                }
                                            } finally {
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                            }
                                        }
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        return;
                                    default:
                                        switch (i) {
                                            case 64:
                                                WindowManagerGlobalLock windowManagerGlobalLock16 = WindowManagerService.this.mGlobalLock;
                                                WindowManagerService.boostPriorityForLockedSection();
                                                synchronized (windowManagerGlobalLock16) {
                                                    try {
                                                        WindowState windowState = (WindowState) message.obj;
                                                        Slog.i(StartingSurfaceController.TAG, "Blast sync timeout: " + windowState);
                                                        windowState.immediatelyNotifyBlastSync();
                                                    } finally {
                                                        WindowManagerService.resetPriorityAfterLockedSection();
                                                    }
                                                }
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                                return;
                                            case 65:
                                                WindowManagerGlobalLock windowManagerGlobalLock17 = WindowManagerService.this.mGlobalLock;
                                                WindowManagerService.boostPriorityForLockedSection();
                                                synchronized (windowManagerGlobalLock17) {
                                                    try {
                                                        Task task = (Task) message.obj;
                                                        task.reparent(WindowManagerService.this.mRoot.getDefaultTaskDisplayArea(), true);
                                                        task.resumeNextFocusAfterReparent();
                                                    } finally {
                                                    }
                                                }
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                                return;
                                            case 66:
                                                WindowManagerGlobalLock windowManagerGlobalLock18 = WindowManagerService.this.mGlobalLock;
                                                WindowManagerService.boostPriorityForLockedSection();
                                                synchronized (windowManagerGlobalLock18) {
                                                    try {
                                                        WindowManagerService windowManagerService9 = WindowManagerService.this;
                                                        if (windowManagerService9.mWindowsInsetsChanged > 0) {
                                                            windowManagerService9.mWindowsInsetsChanged = 0;
                                                            windowManagerService9.mWindowPlacerLocked.performSurfacePlacement();
                                                        }
                                                    } finally {
                                                    }
                                                }
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                                return;
                                            default:
                                                return;
                                        }
                                }
                        }
                }
            }
            WindowState windowState2 = (WindowState) message.obj;
            WindowManagerGlobalLock windowManagerGlobalLock19 = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock19) {
                try {
                    windowState2.mAttrs.flags &= -129;
                    windowState2.hidePermanentlyLw();
                    windowState2.setDisplayLayoutNeeded();
                    WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement();
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public void sendNewMessageDelayed(int i, Object obj, long j) {
            removeMessages(i, obj);
            sendMessageDelayed(obtainMessage(i, obj), j);
        }
    }

    public IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) {
        return new Session(this, iWindowSessionCallback);
    }

    public boolean useBLAST() {
        return this.mUseBLAST;
    }

    public void getInitialDisplaySize(int i, Point point) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(Binder.getCallingUid())) {
                    point.x = displayContent.mInitialDisplayWidth;
                    point.y = displayContent.mInitialDisplayHeight;
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void getBaseDisplaySize(int i, Point point) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(Binder.getCallingUid())) {
                    point.x = displayContent.mBaseDisplayWidth;
                    point.y = displayContent.mBaseDisplayHeight;
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void setForcedDisplaySize(int i, int i2, int i3) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedSize(i2, i3);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, -1, i2, i3, -1, false, null);
        }
    }

    public void setForcedDisplayScalingMode(int i, int i2) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedScalingMode(i2);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSandboxDisplayApis(int i, boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setSandboxDisplayApis(z);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean applyForcedPropertiesForDefaultDisplay() {
        boolean z;
        int forcedDisplayDensityForUserLocked;
        int indexOf;
        int i;
        int i2;
        DisplayWindowSettings.SettingsProvider.SettingsEntry settingsEntry;
        if (this.mSafeMode) {
            return false;
        }
        DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "display_size_forced");
        if (string == null || string.length() == 0) {
            string = SystemProperties.get("ro.config.size_override", (String) null);
        }
        String str = string;
        if (str != null && str.length() > 0 && (indexOf = str.indexOf(44)) > 0 && str.lastIndexOf(44) == indexOf) {
            try {
                Point validForcedSize = defaultDisplayContentLocked.getValidForcedSize(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
                i = validForcedSize.x;
                i2 = validForcedSize.y;
            } catch (NumberFormatException unused) {
            }
            if (defaultDisplayContentLocked.mBaseDisplayWidth == i && defaultDisplayContentLocked.mBaseDisplayHeight == i2) {
                z = false;
                if (i != 0 && i2 != 0) {
                    try {
                        settingsEntry = this.mDisplayWindowSettings.getSettingsEntry(defaultDisplayContentLocked);
                        if (settingsEntry.mForcedWidth == i || settingsEntry.mForcedHeight != i2) {
                            this.mExt.mMultiResolutionController.updateDefaultDisplaySizeDensityChangedReason("applyForcedPropertiesForDefaultDisplay: size=" + i + "x" + i2);
                        }
                        if (defaultDisplayContentLocked.mInitialDisplayWidth == i || defaultDisplayContentLocked.mInitialDisplayHeight != i2) {
                            defaultDisplayContentLocked.mIsSizeForced = true;
                        }
                        this.mDisplayWindowSettings.setForcedSize(defaultDisplayContentLocked, i, i2);
                    } catch (NumberFormatException unused2) {
                    }
                }
                forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(this.mCurrentUserId);
                if (forcedDisplayDensityForUserLocked != 0 && forcedDisplayDensityForUserLocked != defaultDisplayContentLocked.mBaseDisplayDensity) {
                    defaultDisplayContentLocked.mBaseDisplayDensity = forcedDisplayDensityForUserLocked;
                    z = true;
                }
                if (forcedDisplayDensityForUserLocked != 0) {
                    if (this.mDisplayWindowSettings.getSettingsEntry(defaultDisplayContentLocked).mForcedDensity != forcedDisplayDensityForUserLocked) {
                        this.mExt.mMultiResolutionController.updateDefaultDisplaySizeDensityChangedReason("applyForcedPropertiesForDefaultDisplay: density=" + forcedDisplayDensityForUserLocked);
                    }
                    if (defaultDisplayContentLocked.mInitialDisplayDensity != forcedDisplayDensityForUserLocked) {
                        defaultDisplayContentLocked.mIsDensityForced = true;
                    }
                    this.mDisplayWindowSettings.setForcedDensity(defaultDisplayContentLocked.getDisplayInfo(), forcedDisplayDensityForUserLocked, 0);
                }
                if (defaultDisplayContentLocked.mDisplayScalingDisabled != (Settings.Global.getInt(this.mContext.getContentResolver(), "display_scaling_force", 0) != 0)) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.i(ProtoLogGroup.WM_ERROR, 954470154, 0, "FORCED DISPLAY SCALING DISABLED", (Object[]) null);
                    }
                    defaultDisplayContentLocked.mDisplayScalingDisabled = true;
                    z = true;
                }
                if (CoreRune.FW_DYNAMIC_RESOLUTION_CONTROL || !this.mExt.applyScreenRatioToSizeDensity(defaultDisplayContentLocked, str, forcedDisplayDensityForUserLocked)) {
                    return z;
                }
                return true;
            }
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_ERROR, 1115417974, 5, "FORCED DISPLAY SIZE: %dx%d", new Object[]{Long.valueOf(i), Long.valueOf(i2)});
            }
            defaultDisplayContentLocked.updateBaseDisplayMetrics(i, i2, defaultDisplayContentLocked.mBaseDisplayDensity, defaultDisplayContentLocked.mBaseDisplayPhysicalXDpi, defaultDisplayContentLocked.mBaseDisplayPhysicalYDpi);
            z = true;
            if (i != 0) {
                settingsEntry = this.mDisplayWindowSettings.getSettingsEntry(defaultDisplayContentLocked);
                if (settingsEntry.mForcedWidth == i) {
                }
                this.mExt.mMultiResolutionController.updateDefaultDisplaySizeDensityChangedReason("applyForcedPropertiesForDefaultDisplay: size=" + i + "x" + i2);
                if (defaultDisplayContentLocked.mInitialDisplayWidth == i) {
                }
                defaultDisplayContentLocked.mIsSizeForced = true;
                this.mDisplayWindowSettings.setForcedSize(defaultDisplayContentLocked, i, i2);
            }
            forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(this.mCurrentUserId);
            if (forcedDisplayDensityForUserLocked != 0) {
                defaultDisplayContentLocked.mBaseDisplayDensity = forcedDisplayDensityForUserLocked;
                z = true;
            }
            if (forcedDisplayDensityForUserLocked != 0) {
            }
            if (defaultDisplayContentLocked.mDisplayScalingDisabled != (Settings.Global.getInt(this.mContext.getContentResolver(), "display_scaling_force", 0) != 0)) {
            }
            if (CoreRune.FW_DYNAMIC_RESOLUTION_CONTROL) {
            }
            return z;
        }
        z = false;
        forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(this.mCurrentUserId);
        if (forcedDisplayDensityForUserLocked != 0) {
        }
        if (forcedDisplayDensityForUserLocked != 0) {
        }
        if (defaultDisplayContentLocked.mDisplayScalingDisabled != (Settings.Global.getInt(this.mContext.getContentResolver(), "display_scaling_force", 0) != 0)) {
        }
        if (CoreRune.FW_DYNAMIC_RESOLUTION_CONTROL) {
        }
        return z;
    }

    public void clearForcedDisplaySize(int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        int i2 = displayContent.mInitialDisplayWidth;
                        int i3 = displayContent.mInitialDisplayHeight;
                        float f = displayContent.mInitialPhysicalXDpi;
                        displayContent.setForcedSize(i2, i3, f, f);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, -1, -1, -1, -1, false, null);
        }
    }

    public int getInitialDisplayDensity(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(Binder.getCallingUid())) {
                    int initialDisplayDensity = displayContent.getInitialDisplayDensity();
                    resetPriorityAfterLockedSection();
                    return initialDisplayDensity;
                }
                DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                if (displayInfo == null || !displayInfo.hasAccess(Binder.getCallingUid())) {
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int i2 = displayInfo.logicalDensityDpi;
                resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getBaseDisplayDensity(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null || !displayContent.hasAccess(Binder.getCallingUid())) {
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int i2 = displayContent.mBaseDisplayDensity;
                resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getDisplayIdByUniqueId(String str) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(str);
                if (displayContent == null || !displayContent.hasAccess(Binder.getCallingUid())) {
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int i = displayContent.mDisplayId;
                resetPriorityAfterLockedSection();
                return i;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setForcedDisplayDensityForUser(int i, int i2, int i3) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i3, false, true, "setForcedDisplayDensityForUser", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedDensity(i2, handleIncomingUser);
                    } else {
                        DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                        if (displayInfo != null) {
                            this.mDisplayWindowSettings.setForcedDensity(displayInfo, i2, i3);
                        }
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, i3, -1, -1, i2, false, null);
        }
    }

    public void clearForcedDisplayDensityForUser(int i, int i2) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i2, false, true, "clearForcedDisplayDensityForUser", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedDensity(displayContent.getInitialDisplayDensity(), handleIncomingUser);
                    } else {
                        DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                        if (displayInfo != null) {
                            this.mDisplayWindowSettings.setForcedDensity(displayInfo, displayInfo.logicalDensityDpi, i2);
                        }
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, i2, -1, -1, -1, false, null);
        }
    }

    public final int getForcedDisplayDensityForUserLocked(int i) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "display_density_forced", 0);
        if (stringForUser == null || stringForUser.length() == 0) {
            stringForUser = SystemProperties.get("ro.config.density_override", (String) null);
        }
        if (stringForUser != null && stringForUser.length() > 0) {
            try {
                return Integer.parseInt(stringForUser);
            } catch (NumberFormatException unused) {
            }
        }
        return 0;
    }

    public void startWindowTrace() {
        this.mWindowTracing.startTrace(null);
    }

    public void stopWindowTrace() {
        this.mWindowTracing.stopTrace(null);
    }

    public void saveWindowTraceToFile() {
        this.mWindowTracing.saveForBugreport(null);
    }

    public boolean isWindowTraceEnabled() {
        return this.mWindowTracing.isEnabled();
    }

    public void startTransitionTrace() {
        this.mTransitionTracer.startTrace(null);
    }

    public void stopTransitionTrace() {
        this.mTransitionTracer.stopTrace(null);
    }

    public boolean isTransitionTraceEnabled() {
        return this.mTransitionTracer.isActiveTracingEnabled();
    }

    public boolean registerCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) {
        return this.mBlurController.registerCrossWindowBlurEnabledListener(iCrossWindowBlurEnabledListener);
    }

    public void unregisterCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) {
        this.mBlurController.unregisterCrossWindowBlurEnabledListener(iCrossWindowBlurEnabledListener);
    }

    public final WindowState windowForClientLocked(Session session, IWindow iWindow, boolean z) {
        return windowForClientLocked(session, iWindow.asBinder(), z);
    }

    public final WindowState windowForClientLocked(Session session, IBinder iBinder, boolean z) {
        WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
        if (windowState == null) {
            if (z) {
                throw new IllegalArgumentException("Requested window " + iBinder + " does not exist");
            }
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -2101985723, 0, "Failed looking up window session=%s callers=%s", new Object[]{String.valueOf(session), String.valueOf(Debug.getCallers(3))});
            }
            return null;
        }
        if (session == null || windowState.mSession == session) {
            return windowState;
        }
        if (z) {
            throw new IllegalArgumentException("Requested window " + iBinder + " is in session " + windowState.mSession + ", not " + session);
        }
        if (ProtoLogCache.WM_ERROR_enabled) {
            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -2101985723, 0, "Failed looking up window session=%s callers=%s", new Object[]{String.valueOf(session), String.valueOf(Debug.getCallers(3))});
        }
        return null;
    }

    public void makeWindowFreezingScreenIfNeededLocked(WindowState windowState) {
        int i = this.mFrozenDisplayId;
        if (i == -1 || i != windowState.getDisplayId() || this.mWindowsFreezingScreen == 2) {
            return;
        }
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1632122349, 0, (String) null, new Object[]{String.valueOf(windowState)});
        }
        if (windowState.isVisibleRequested()) {
            windowState.setOrientationChanging(true);
        }
        if (this.mWindowsFreezingScreen == 0) {
            this.mWindowsFreezingScreen = 1;
            this.f1749mH.sendNewMessageDelayed(11, windowState.getDisplayContent(), 2000L);
        }
    }

    public void checkDrawnWindowsLocked() {
        if (this.mWaitingForDrawnCallbacks.isEmpty()) {
            return;
        }
        for (int size = this.mWaitingForDrawnCallbacks.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mWaitingForDrawnCallbacks.keyAt(size);
            int size2 = windowContainer.mWaitingForDrawn.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    break;
                }
                WindowState windowState = (WindowState) windowContainer.mWaitingForDrawn.get(size2);
                if (ProtoLogCache.WM_FORCE_DEBUG_SCREEN_ON_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, 1362380201, 508, "Waiting for drawn %s: removed=%b visible=%b mHasSurface=%b drawState=%d", new Object[]{String.valueOf(windowState), Boolean.valueOf(windowState.mRemoved), Boolean.valueOf(windowState.isVisible()), Boolean.valueOf(windowState.mHasSurface), Long.valueOf(windowState.mWinAnimator.mDrawState)});
                }
                if (windowState.mRemoved || !windowState.mHasSurface || !windowState.isVisibleByPolicy()) {
                    if (ProtoLogCache.WM_FORCE_DEBUG_SCREEN_ON_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, 455649397, 0, "Aborted waiting for drawn: %s", new Object[]{String.valueOf(windowState)});
                    }
                    windowContainer.mWaitingForDrawn.remove(windowState);
                    if (Trace.isTagEnabled(32L)) {
                        traceEndWaitingForWindowDrawn(windowState);
                    }
                } else if (windowState.hasDrawn()) {
                    if (ProtoLogCache.WM_FORCE_DEBUG_SCREEN_ON_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, 258453220, 0, "Window drawn win=%s", new Object[]{String.valueOf(windowState)});
                    }
                    windowContainer.mWaitingForDrawn.remove(windowState);
                    if (Trace.isTagEnabled(32L)) {
                        traceEndWaitingForWindowDrawn(windowState);
                    }
                }
            }
            if (windowContainer.mWaitingForDrawn.isEmpty()) {
                if (windowContainer.mTransitionController.isCollecting() && (windowContainer.mTransitionController.getCollectingTransitionType() == 8 || windowContainer.mTransitionController.getCollectingTransitionType() == 9 || (windowContainer.mTransitionController.getCollectingTransition().getFlags() & IInstalld.FLAG_USE_QUOTA) != 0)) {
                    Slog.d(StartingSurfaceController.TAG, "Waiting All windows drawn before keyguard occluding!!");
                    return;
                }
                if (ProtoLogCache.WM_FORCE_DEBUG_SCREEN_ON_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, -1363023508, 0, "All windows drawn!", (Object[]) null);
                }
                this.f1749mH.removeMessages(24, windowContainer);
                ((Message) this.mWaitingForDrawnCallbacks.removeAt(size)).sendToTarget();
            }
        }
    }

    public final void traceStartWaitingForWindowDrawn(WindowState windowState) {
        String str = "waitForAllWindowsDrawn#" + ((Object) windowState.getWindowTag());
        Trace.asyncTraceBegin(32L, str.substring(0, Math.min(127, str.length())), 0);
    }

    public final void traceEndWaitingForWindowDrawn(WindowState windowState) {
        String str = "waitForAllWindowsDrawn#" + ((Object) windowState.getWindowTag());
        Trace.asyncTraceEnd(32L, str.substring(0, Math.min(127, str.length())), 0);
    }

    public void requestTraversal() {
        this.mWindowPlacerLocked.requestTraversal();
    }

    public void scheduleAnimationLocked() {
        this.mAnimator.scheduleAnimation();
    }

    public boolean updateFocusedWindowLocked(int i, boolean z) {
        Trace.traceBegin(32L, "wmUpdateFocus");
        boolean updateFocusedWindowLocked = this.mRoot.updateFocusedWindowLocked(i, z);
        Trace.traceEnd(32L);
        return updateFocusedWindowLocked;
    }

    public void startFreezingDisplay(int i, int i2) {
        startFreezingDisplay(i, i2, getDefaultDisplayContentLocked());
    }

    public void startFreezingDisplay(int i, int i2, DisplayContent displayContent) {
        startFreezingDisplay(i, i2, displayContent, -1);
    }

    public void startFreezingDisplay(int i, int i2, DisplayContent displayContent, int i3) {
        if (this.mDisplayFrozen || displayContent.getDisplayRotation().isRotatingSeamlessly()) {
            return;
        }
        int displayId = displayContent.getDisplayId();
        boolean z = displayId == 2 && this.mPolicy.isScreenOn(displayId);
        if (displayContent.isReady() && displayContent.getDisplayPolicy().isScreenOnFully()) {
            if ((displayContent.getDisplayInfo().state != 1 || z) && displayContent.okToAnimate()) {
                Trace.traceBegin(32L, "WMS.doStartFreezingDisplay");
                doStartFreezingDisplay(i, i2, displayContent, i3);
                Trace.traceEnd(32L);
            }
        }
    }

    public final void doStartFreezingDisplay(int i, int i2, DisplayContent displayContent, int i3) {
        if (ProtoLogCache.WM_FORCE_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_FORCE_DEBUG_ORIENTATION, -1529990523, 5, "startFreezingDisplayLocked: exitAnim=%d enterAnim=%d called by %s", new Object[]{Long.valueOf(i), Long.valueOf(i2), String.valueOf(Debug.getCallers(8))});
        }
        this.mScreenFrozenLock.acquire();
        this.mAtmService.startLaunchPowerMode(2);
        this.mDisplayFrozen = true;
        this.mDisplayFreezeTime = SystemClock.elapsedRealtime();
        this.mLastFinishedFreezeSource = null;
        this.mFrozenDisplayId = displayContent.getDisplayId();
        this.mInputManagerCallback.freezeInputDispatchingLw();
        if (displayContent.mAppTransition.isTransitionSet()) {
            displayContent.mAppTransition.freeze();
        }
        this.mLatencyTracker.onActionStart(6);
        this.mExitAnimId = i;
        this.mEnterAnimId = i2;
        displayContent.updateDisplayInfo();
        if (i3 == -1) {
            i3 = displayContent.getDisplayInfo().rotation;
        }
        displayContent.setRotationAnimation(new ScreenRotationAnimation(displayContent, i3));
    }

    public void stopFreezingDisplayLocked() {
        int i;
        boolean z;
        boolean z2;
        if (this.mDisplayFrozen) {
            DisplayContent displayContent = this.mRoot.getDisplayContent(this.mFrozenDisplayId);
            if (displayContent != null) {
                i = displayContent.mOpeningApps.size();
                z = displayContent.mWaitingForConfig;
                z2 = displayContent.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange();
            } else {
                i = 0;
                z = false;
                z2 = false;
            }
            if (z || z2 || this.mAppsFreezingScreen > 0 || this.mWindowsFreezingScreen == 1 || this.mClientFreezingScreen || i > 0) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1360176455, 1887, (String) null, new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Long.valueOf(this.mAppsFreezingScreen), Long.valueOf(this.mWindowsFreezingScreen), Boolean.valueOf(this.mClientFreezingScreen), Long.valueOf(i)});
                    return;
                }
                return;
            }
            Trace.traceBegin(32L, "WMS.doStopFreezingDisplayLocked-" + this.mLastFinishedFreezeSource);
            doStopFreezingDisplayLocked(displayContent);
            Trace.traceEnd(32L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doStopFreezingDisplayLocked(DisplayContent displayContent) {
        boolean z;
        boolean z2;
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, 355720268, 0, (String) null, (Object[]) null);
        }
        this.mFrozenDisplayId = -1;
        this.mDisplayFrozen = false;
        this.mInputManagerCallback.thawInputDispatchingLw();
        this.mLastDisplayFreezeDuration = (int) (SystemClock.elapsedRealtime() - this.mDisplayFreezeTime);
        StringBuilder sb = new StringBuilder(128);
        sb.append("Screen frozen for ");
        TimeUtils.formatDuration(this.mLastDisplayFreezeDuration, sb);
        if (this.mLastFinishedFreezeSource != null) {
            sb.append(" due to ");
            sb.append(this.mLastFinishedFreezeSource);
        }
        if (ProtoLogCache.WM_ERROR_enabled) {
            ProtoLogImpl.i(ProtoLogGroup.WM_ERROR, -583031528, 0, "%s", new Object[]{String.valueOf(sb.toString())});
        }
        this.f1749mH.removeMessages(17);
        this.f1749mH.removeMessages(30);
        ScreenRotationAnimation rotationAnimation = displayContent == null ? null : displayContent.getRotationAnimation();
        if (rotationAnimation != null && rotationAnimation.hasScreenshot()) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1634557978, 0, (String) null, (Object[]) null);
            }
            DisplayInfo displayInfo = displayContent.getDisplayInfo();
            if (!displayContent.getDisplayRotation().validateRotationAnimation(this.mExitAnimId, this.mEnterAnimId, false)) {
                this.mEnterAnimId = 0;
                this.mExitAnimId = 0;
            }
            if (rotationAnimation.dismiss(this.mTransaction, 10000L, getTransitionAnimationScaleLocked(), displayInfo.logicalWidth, displayInfo.logicalHeight, this.mExitAnimId, this.mEnterAnimId)) {
                this.mTransaction.apply();
                z = false;
                z2 = displayContent == null && displayContent.updateOrientation();
                this.mScreenFrozenLock.release();
                if (z && displayContent != null) {
                    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -783405930, 0, (String) null, (Object[]) null);
                    }
                    z2 |= displayContent.updateRotationUnchecked();
                }
                if (z2) {
                    displayContent.sendNewConfiguration();
                }
                this.mAtmService.endLaunchPowerMode(2);
                this.mLatencyTracker.onActionEnd(6);
            }
            rotationAnimation.kill();
            displayContent.setRotationAnimation(null);
        } else if (rotationAnimation != null) {
            rotationAnimation.kill();
            displayContent.setRotationAnimation(null);
        }
        z = true;
        if (displayContent == null) {
        }
        this.mScreenFrozenLock.release();
        if (z) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            }
            z2 |= displayContent.updateRotationUnchecked();
        }
        if (z2) {
        }
        this.mAtmService.endLaunchPowerMode(2);
        this.mLatencyTracker.onActionEnd(6);
    }

    public static int getPropertyInt(String[] strArr, int i, int i2, int i3, DisplayMetrics displayMetrics) {
        String str;
        if (i < strArr.length && (str = strArr[i]) != null && str.length() > 0) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return i2 == 0 ? i3 : (int) TypedValue.applyDimension(i2, i3, displayMetrics);
    }

    public void createWatermark() {
        FileInputStream fileInputStream;
        String[] split;
        if (this.mWatermark != null) {
            return;
        }
        DataInputStream dataInputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(new File("/system/etc/setup.conf"));
                try {
                    DataInputStream dataInputStream2 = new DataInputStream(fileInputStream);
                    try {
                        String readLine = dataInputStream2.readLine();
                        if (readLine != null && (split = readLine.split("%")) != null && split.length > 0) {
                            DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                            this.mWatermark = new Watermark(defaultDisplayContentLocked, defaultDisplayContentLocked.mRealDisplayMetrics, split, this.mTransaction);
                            this.mTransaction.apply();
                        }
                        dataInputStream2.close();
                    } catch (FileNotFoundException unused) {
                        dataInputStream = dataInputStream2;
                        if (dataInputStream == null) {
                            if (fileInputStream == null) {
                                return;
                            }
                            fileInputStream.close();
                        }
                        dataInputStream.close();
                    } catch (IOException unused2) {
                        dataInputStream = dataInputStream2;
                        if (dataInputStream == null) {
                            if (fileInputStream == null) {
                                return;
                            }
                            fileInputStream.close();
                        }
                        dataInputStream.close();
                    } catch (Throwable th) {
                        th = th;
                        dataInputStream = dataInputStream2;
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        } else {
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw th;
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                } catch (IOException unused4) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException unused5) {
            }
        } catch (FileNotFoundException unused6) {
            fileInputStream = null;
        } catch (IOException unused7) {
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    public void setRecentsVisibility(boolean z) {
        if (!checkCallingPermission("android.permission.STATUS_BAR", "setRecentsVisibility()")) {
            throw new SecurityException("Requires STATUS_BAR permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPolicy.setRecentsVisibilityLw(z);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void hideTransientBars(int i) {
        if (!checkCallingPermission("android.permission.STATUS_BAR", "hideTransientBars()")) {
            throw new SecurityException("Requires STATUS_BAR permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    if (displayContent.getDisplayPolicy().mExt.isGameToolsVisibleLw()) {
                        Slog.w(StartingSurfaceController.TAG, "hideTransientBars skipped by game tools win");
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.getInsetsPolicy().hideTransient();
                } else {
                    Slog.w(StartingSurfaceController.TAG, "hideTransientBars with invalid displayId=" + i);
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.updatePrivacyIndicatorBounds(rectArr);
                } else {
                    Slog.w(StartingSurfaceController.TAG, "updateStaticPrivacyIndicatorBounds with invalid displayId=" + i);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR") != 0) {
            throw new SecurityException("Caller does not hold permission android.permission.STATUS_BAR");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPolicy.setNavBarVirtualKeyHapticFeedbackEnabledLw(z);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) {
        if (!this.mAtmService.isCallerRecents(Binder.getCallingUid()) && this.mContext.checkCallingOrSelfPermission("android.permission.INPUT_CONSUMER") != 0) {
            throw new SecurityException("createInputConsumer requires INPUT_CONSUMER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.getInputMonitor().createInputConsumer(iBinder, str, inputChannel, Binder.getCallingPid(), Binder.getCallingUserHandle());
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public boolean destroyInputConsumer(String str, int i) {
        if (!this.mAtmService.isCallerRecents(Binder.getCallingUid()) && this.mContext.checkCallingOrSelfPermission("android.permission.INPUT_CONSUMER") != 0) {
            throw new SecurityException("destroyInputConsumer requires INPUT_CONSUMER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean destroyInputConsumer = displayContent.getInputMonitor().destroyInputConsumer(str);
                resetPriorityAfterLockedSection();
                return destroyInputConsumer;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public Region getCurrentImeTouchRegion() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.RESTRICTED_VR_ACCESS") != 0) {
            throw new SecurityException("getCurrentImeTouchRegion is restricted to VR services");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Region region = new Region();
                for (int size = this.mRoot.mChildren.size() - 1; size >= 0; size--) {
                    WindowState windowState = ((DisplayContent) this.mRoot.mChildren.get(size)).mInputMethodWindow;
                    if (windowState != null) {
                        windowState.getTouchableRegion(region);
                        resetPriorityAfterLockedSection();
                        return region;
                    }
                }
                resetPriorityAfterLockedSection();
                return region;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean hasNavigationBar(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean hasNavigationBar = displayContent.getDisplayPolicy().hasNavigationBar();
                resetPriorityAfterLockedSection();
                return hasNavigationBar;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void lockNow(Bundle bundle) {
        this.mPolicy.lockNow(bundle);
    }

    public boolean isSafeModeEnabled() {
        return this.mSafeMode;
    }

    public boolean clearWindowContentFrameStats(IBinder iBinder) {
        if (!checkCallingPermission("android.permission.FRAME_STATS", "clearWindowContentFrameStats()")) {
            throw new SecurityException("Requires FRAME_STATS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean clearWindowContentFrameStats = windowSurfaceController.clearWindowContentFrameStats();
                resetPriorityAfterLockedSection();
                return clearWindowContentFrameStats;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) {
        if (!checkCallingPermission("android.permission.FRAME_STATS", "getWindowContentFrameStats()")) {
            throw new SecurityException("Requires FRAME_STATS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return null;
                }
                WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    resetPriorityAfterLockedSection();
                    return null;
                }
                if (this.mTempWindowRenderStats == null) {
                    this.mTempWindowRenderStats = new WindowContentFrameStats();
                }
                WindowContentFrameStats windowContentFrameStats = this.mTempWindowRenderStats;
                if (windowSurfaceController.getWindowContentFrameStats(windowContentFrameStats)) {
                    resetPriorityAfterLockedSection();
                    return windowContentFrameStats;
                }
                resetPriorityAfterLockedSection();
                return null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void dumpPolicyLocked(PrintWriter printWriter, String[] strArr) {
        printWriter.println("WINDOW MANAGER POLICY STATE (dumpsys window policy)");
        this.mPolicy.dump("    ", printWriter, strArr);
    }

    public final void dumpAnimatorLocked(PrintWriter printWriter, boolean z) {
        printWriter.println("WINDOW MANAGER ANIMATOR STATE (dumpsys window animator)");
        this.mAnimator.dumpLocked(printWriter, "    ", z);
    }

    public final void dumpTokensLocked(PrintWriter printWriter, boolean z) {
        printWriter.println("WINDOW MANAGER TOKENS (dumpsys window tokens)");
        this.mRoot.dumpTokens(printWriter, z);
    }

    public final void dumpHighRefreshRateBlacklist(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER HIGH REFRESH RATE BLACKLIST (dumpsys window refresh)");
        this.mHighRefreshRateDenylist.dump(printWriter);
    }

    public final void dumpTraceStatus(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER TRACE (dumpsys window trace)");
        printWriter.print(this.mWindowTracing.getStatus() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }

    public final void dumpLogStatus(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER LOGGING (dumpsys window logging)");
        printWriter.println(ProtoLogImpl.getSingleInstance().getStatus());
    }

    public final void dumpSessionsLocked(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER SESSIONS (dumpsys window sessions)");
        for (int i = 0; i < this.mSessions.size(); i++) {
            Session session = (Session) this.mSessions.valueAt(i);
            printWriter.print("  Session ");
            printWriter.print(session);
            printWriter.println(':');
            session.dump(printWriter, "    ");
        }
    }

    public void dumpDebugLocked(ProtoOutputStream protoOutputStream, int i) {
        this.mPolicy.dumpDebug(protoOutputStream, 1146756268033L);
        this.mRoot.dumpDebug(protoOutputStream, 1146756268034L, i);
        DisplayContent topFocusedDisplayContent = this.mRoot.getTopFocusedDisplayContent();
        WindowState windowState = topFocusedDisplayContent.mCurrentFocus;
        if (windowState != null) {
            windowState.writeIdentifierToProto(protoOutputStream, 1146756268035L);
        }
        ActivityRecord activityRecord = topFocusedDisplayContent.mFocusedApp;
        if (activityRecord != null) {
            activityRecord.writeNameToProto(protoOutputStream, 1138166333444L);
        }
        WindowState currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
        if (currentInputMethodWindow != null) {
            currentInputMethodWindow.writeIdentifierToProto(protoOutputStream, 1146756268037L);
        }
        protoOutputStream.write(1133871366150L, this.mDisplayFrozen);
        protoOutputStream.write(1120986464265L, topFocusedDisplayContent.getDisplayId());
        protoOutputStream.write(1133871366154L, this.mHardKeyboardAvailable);
        protoOutputStream.write(1133871366155L, true);
        this.mAtmService.mBackNavigationController.dumpDebug(protoOutputStream, 1146756268044L);
    }

    public final void dumpWindowsLocked(PrintWriter printWriter, boolean z, ArrayList arrayList) {
        printWriter.println("WINDOW MANAGER WINDOWS (dumpsys window windows)");
        dumpWindowsNoHeaderLocked(printWriter, z, arrayList);
    }

    public final void dumpWindowsNoHeaderLocked(final PrintWriter printWriter, boolean z, ArrayList arrayList) {
        this.mRoot.dumpWindowsNoHeader(printWriter, z, arrayList);
        if (!this.mHidingNonSystemOverlayWindows.isEmpty()) {
            printWriter.println();
            printWriter.println("  Hiding System Alert Windows:");
            for (int size = this.mHidingNonSystemOverlayWindows.size() - 1; size >= 0; size--) {
                WindowState windowState = (WindowState) this.mHidingNonSystemOverlayWindows.get(size);
                printWriter.print("  #");
                printWriter.print(size);
                printWriter.print(' ');
                printWriter.print(windowState);
                if (z) {
                    printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                    windowState.dump(printWriter, "    ", true);
                } else {
                    printWriter.println();
                }
            }
        }
        ArrayList arrayList2 = this.mForceRemoves;
        if (arrayList2 != null && !arrayList2.isEmpty()) {
            printWriter.println();
            printWriter.println("  Windows force removing:");
            for (int size2 = this.mForceRemoves.size() - 1; size2 >= 0; size2--) {
                WindowState windowState2 = (WindowState) this.mForceRemoves.get(size2);
                printWriter.print("  Removing #");
                printWriter.print(size2);
                printWriter.print(' ');
                printWriter.print(windowState2);
                if (z) {
                    printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                    windowState2.dump(printWriter, "    ", true);
                } else {
                    printWriter.println();
                }
            }
        }
        if (!this.mDestroySurface.isEmpty()) {
            printWriter.println();
            printWriter.println("  Windows waiting to destroy their surface:");
            for (int size3 = this.mDestroySurface.size() - 1; size3 >= 0; size3--) {
                WindowState windowState3 = (WindowState) this.mDestroySurface.get(size3);
                if (arrayList == null || arrayList.contains(windowState3)) {
                    printWriter.print("  Destroy #");
                    printWriter.print(size3);
                    printWriter.print(' ');
                    printWriter.print(windowState3);
                    if (z) {
                        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                        windowState3.dump(printWriter, "    ", true);
                    } else {
                        printWriter.println();
                    }
                }
            }
        }
        if (!this.mResizingWindows.isEmpty()) {
            printWriter.println();
            printWriter.println("  Windows waiting to resize:");
            for (int size4 = this.mResizingWindows.size() - 1; size4 >= 0; size4--) {
                WindowState windowState4 = (WindowState) this.mResizingWindows.get(size4);
                if (arrayList == null || arrayList.contains(windowState4)) {
                    printWriter.print("  Resizing #");
                    printWriter.print(size4);
                    printWriter.print(' ');
                    printWriter.print(windowState4);
                    if (z) {
                        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                        windowState4.dump(printWriter, "    ", true);
                    } else {
                        printWriter.println();
                    }
                }
            }
        }
        if (!this.mWaitingForDrawnCallbacks.isEmpty()) {
            printWriter.println();
            printWriter.println("  Clients waiting for these windows to be drawn:");
            this.mWaitingForDrawnCallbacks.forEach(new BiConsumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda30
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    WindowManagerService.lambda$dumpWindowsNoHeaderLocked$13(printWriter, (WindowContainer) obj, (Message) obj2);
                }
            });
        }
        printWriter.println();
        printWriter.print("  mGlobalConfiguration=");
        printWriter.println(this.mRoot.getConfiguration());
        printWriter.print("  mHasPermanentDpad=");
        printWriter.println(this.mHasPermanentDpad);
        this.mRoot.dumpTopFocusedDisplayId(printWriter);
        this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowManagerService.lambda$dumpWindowsNoHeaderLocked$14(printWriter, (DisplayContent) obj);
            }
        });
        printWriter.print("  mBlurEnabled=");
        printWriter.println(this.mBlurController.getBlurEnabled());
        printWriter.print("  mLastDisplayFreezeDuration=");
        TimeUtils.formatDuration(this.mLastDisplayFreezeDuration, printWriter);
        if (this.mLastFinishedFreezeSource != null) {
            printWriter.print(" due to ");
            printWriter.print(this.mLastFinishedFreezeSource);
        }
        printWriter.println();
        this.mInputManagerCallback.dump(printWriter, "  ");
        this.mSnapshotController.dump(printWriter, " ");
        dumpAccessibilityController(printWriter, false);
        if (z) {
            Object currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
            if (currentInputMethodWindow != null) {
                printWriter.print("  mInputMethodWindow=");
                printWriter.println(currentInputMethodWindow);
            }
            this.mWindowPlacerLocked.dump(printWriter, "  ");
            printWriter.print("  mSystemBooted=");
            printWriter.print(this.mSystemBooted);
            printWriter.print(" mDisplayEnabled=");
            printWriter.println(this.mDisplayEnabled);
            this.mRoot.dumpLayoutNeededDisplayIds(printWriter);
            printWriter.print("  mTransactionSequence=");
            printWriter.println(this.mTransactionSequence);
            printWriter.print("  mDisplayFrozen=");
            printWriter.print(this.mDisplayFrozen);
            printWriter.print(" windows=");
            printWriter.print(this.mWindowsFreezingScreen);
            printWriter.print(" client=");
            printWriter.print(this.mClientFreezingScreen);
            printWriter.print(" apps=");
            printWriter.println(this.mAppsFreezingScreen);
            DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
            printWriter.print("  mRotation=");
            printWriter.println(defaultDisplayContentLocked.getRotation());
            printWriter.print("  mLastOrientation=");
            printWriter.println(defaultDisplayContentLocked.getLastOrientation());
            printWriter.print("  mWaitingForConfig=");
            printWriter.println(defaultDisplayContentLocked.mWaitingForConfig);
            this.mRotationWatcherController.dump(printWriter);
            printWriter.print("  Animation settings: disabled=");
            printWriter.print(this.mAnimationsDisabled);
            printWriter.print(" window=");
            printWriter.print(this.mWindowAnimationScaleSetting);
            printWriter.print(" transition=");
            printWriter.print(this.mTransitionAnimationScaleSetting);
            printWriter.print(" animator=");
            printWriter.println(this.mAnimatorDurationScaleSetting);
            if (this.mRecentsAnimationController != null) {
                printWriter.print("  mRecentsAnimationController=");
                printWriter.println(this.mRecentsAnimationController);
                this.mRecentsAnimationController.dump(printWriter, "    ");
            }
            this.mDragDropController.dump(printWriter, "  ");
            printWriter.println();
            this.mWindowContextListenerController.dump(printWriter, "  ");
            this.mKeyguardDisableHandler.dump(printWriter);
        }
        printWriter.println();
        this.mExt.dumpLocked(printWriter);
    }

    public static /* synthetic */ void lambda$dumpWindowsNoHeaderLocked$13(PrintWriter printWriter, WindowContainer windowContainer, Message message) {
        printWriter.print("  WindowContainer ");
        printWriter.println(windowContainer.getName());
        for (int size = windowContainer.mWaitingForDrawn.size() - 1; size >= 0; size--) {
            WindowState windowState = (WindowState) windowContainer.mWaitingForDrawn.get(size);
            printWriter.print("  Waiting #");
            printWriter.print(size);
            printWriter.print(' ');
            printWriter.print(windowState);
        }
    }

    public static /* synthetic */ void lambda$dumpWindowsNoHeaderLocked$14(PrintWriter printWriter, DisplayContent displayContent) {
        int displayId = displayContent.getDisplayId();
        InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
        InputTarget imeInputTarget = displayContent.getImeInputTarget();
        InsetsControlTarget imeTarget2 = displayContent.getImeTarget(2);
        if (imeTarget != null) {
            printWriter.print("  imeLayeringTarget in display# ");
            printWriter.print(displayId);
            printWriter.print(' ');
            printWriter.println(imeTarget);
        }
        if (imeInputTarget != null) {
            printWriter.print("  imeInputTarget in display# ");
            printWriter.print(displayId);
            printWriter.print(' ');
            printWriter.println(imeInputTarget);
        }
        if (imeTarget2 != null) {
            printWriter.print("  imeControlTarget in display# ");
            printWriter.print(displayId);
            printWriter.print(' ');
            printWriter.println(imeTarget2);
        }
        printWriter.print("  Minimum task size of display#");
        printWriter.print(displayId);
        printWriter.print(' ');
        printWriter.print(displayContent.mMinSizeOfResizeableTaskDp);
    }

    public final void dumpAccessibilityController(PrintWriter printWriter, boolean z) {
        boolean hasCallbacks = this.mAccessibilityController.hasCallbacks();
        if (hasCallbacks || z) {
            if (!hasCallbacks) {
                printWriter.println("AccessibilityController doesn't have callbacks, but printing it anways:");
            } else {
                printWriter.println("AccessibilityController:");
            }
            this.mAccessibilityController.dump(printWriter, "  ");
        }
    }

    public final void dumpAccessibilityLocked(PrintWriter printWriter) {
        dumpAccessibilityController(printWriter, true);
    }

    public final boolean dumpWindows(PrintWriter printWriter, String str, boolean z) {
        final ArrayList arrayList = new ArrayList();
        if ("apps".equals(str) || "visible".equals(str) || "visible-apps".equals(str)) {
            final boolean contains = str.contains("apps");
            final boolean contains2 = str.contains("visible");
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (contains) {
                    try {
                        this.mRoot.dumpDisplayContents(printWriter);
                    } finally {
                        resetPriorityAfterLockedSection();
                    }
                }
                this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda33
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        WindowManagerService.lambda$dumpWindows$15(contains2, contains, arrayList, (WindowState) obj);
                    }
                }, true);
            }
            resetPriorityAfterLockedSection();
        } else {
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    this.mRoot.getWindowsByName(arrayList, str);
                } finally {
                }
            }
            resetPriorityAfterLockedSection();
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        WindowManagerGlobalLock windowManagerGlobalLock3 = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock3) {
            try {
                dumpWindowsLocked(printWriter, z, arrayList);
            } finally {
            }
        }
        resetPriorityAfterLockedSection();
        return true;
    }

    public static /* synthetic */ void lambda$dumpWindows$15(boolean z, boolean z2, ArrayList arrayList, WindowState windowState) {
        if (!z || windowState.isVisible()) {
            if (z2 && windowState.mActivityRecord == null) {
                return;
            }
            arrayList.add(windowState);
        }
    }

    public final void dumpLastANRLocked(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER LAST ANR (dumpsys window lastanr)");
        String str = this.mLastANRState;
        if (str == null) {
            printWriter.println("  <no ANR has occurred since boot>");
        } else {
            printWriter.println(str);
        }
    }

    public void saveANRStateLocked(ActivityRecord activityRecord, WindowState windowState, String str) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 1024);
        fastPrintWriter.println("  ANR time: " + DateFormat.getDateTimeInstance().format(new Date()));
        if (activityRecord != null) {
            fastPrintWriter.println("  Application at fault: " + activityRecord.stringName);
        }
        if (windowState != null) {
            fastPrintWriter.println("  Window at fault: " + ((Object) windowState.mAttrs.getTitle()));
        }
        if (str != null) {
            fastPrintWriter.println("  Reason: " + str);
        }
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) this.mRoot.getChildAt(childCount);
            int displayId = displayContent.getDisplayId();
            if (!displayContent.mWinAddedSinceNullFocus.isEmpty()) {
                fastPrintWriter.println("  Windows added in display #" + displayId + " since null focus: " + displayContent.mWinAddedSinceNullFocus);
            }
            if (!displayContent.mWinRemovedSinceNullFocus.isEmpty()) {
                fastPrintWriter.println("  Windows removed in display #" + displayId + " since null focus: " + displayContent.mWinRemovedSinceNullFocus);
            }
        }
        fastPrintWriter.println();
        dumpWindowsNoHeaderLocked(fastPrintWriter, true, null);
        fastPrintWriter.println();
        fastPrintWriter.println("Last ANR continued");
        this.mRoot.dumpDisplayContents(fastPrintWriter);
        fastPrintWriter.close();
        this.mLastANRState = stringWriter.toString();
        this.f1749mH.removeMessages(38);
        this.f1749mH.sendEmptyMessageDelayed(38, 7200000L);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00cf, code lost:
    
        r9 = new android.util.proto.ProtoOutputStream(r8);
        r8 = r7.mGlobalLock;
        boostPriorityForLockedSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d9, code lost:
    
        monitor-enter(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00da, code lost:
    
        dumpDebugLocked(r9, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00dd, code lost:
    
        monitor-exit(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00de, code lost:
    
        resetPriorityAfterLockedSection();
        r9.flush();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00e4, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e5, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ea, code lost:
    
        throw r7;
     */
    @NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doDump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr, boolean z) {
        String str;
        if (DumpUtils.checkDumpPermission(this.mContext, StartingSurfaceController.TAG, printWriter)) {
            int i = 0;
            boolean z2 = false;
            while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                i++;
                if ("-a".equals(str)) {
                    z2 = true;
                } else {
                    if ("-h".equals(str)) {
                        printWriter.println("Window manager dump options:");
                        printWriter.println("  [-a] [-h] [cmd] ...");
                        printWriter.println("  cmd may be one of:");
                        printWriter.println("    l[astanr]: last ANR information");
                        printWriter.println("    p[policy]: policy state");
                        printWriter.println("    a[animator]: animator state");
                        printWriter.println("    s[essions]: active sessions");
                        printWriter.println("    surfaces: active surfaces (debugging enabled only)");
                        printWriter.println("    d[isplays]: active display contents");
                        printWriter.println("    t[okens]: token list");
                        printWriter.println("    w[indows]: window list");
                        printWriter.println("    a11y[accessibility]: accessibility-related state");
                        printWriter.println("    package-config: installed packages having app-specific config");
                        printWriter.println("    trace: print trace status and write Winscope trace to file");
                        printWriter.println("  cmd may also be a NAME to dump windows.  NAME may");
                        printWriter.println("    be a partial substring in a window name, a");
                        printWriter.println("    Window hex object identifier, or");
                        printWriter.println("    \"all\" for all windows, or");
                        printWriter.println("    \"visible\" for the visible windows.");
                        printWriter.println("    \"visible-apps\" for the visible app windows.");
                        printWriter.println("  -a: include all available server state.");
                        printWriter.println("  --proto: output dump in protocol buffer format.");
                        return;
                    }
                    if (this.mExt.executeShellCommand(printWriter, strArr, i, str)) {
                        return;
                    }
                    printWriter.println("Unknown argument: " + str + "; use -h for help");
                }
            }
            if (i < strArr.length) {
                String str2 = strArr[i];
                if ("lastanr".equals(str2) || "l".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            dumpLastANRLocked(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("policy".equals(str2) || KnoxAnalyticsDataConverter.PAYLOAD.equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            dumpPolicyLocked(printWriter, strArr);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("animator".equals(str2) || "a".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock3 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock3) {
                        try {
                            dumpAnimatorLocked(printWriter, true);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("sessions".equals(str2) || "s".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock4 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock4) {
                        try {
                            dumpSessionsLocked(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("displays".equals(str2) || "d".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock5 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock5) {
                        try {
                            this.mRoot.dumpDisplayContents(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("tokens".equals(str2) || KnoxAnalyticsDataConverter.TIMESTAMP.equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock6 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock6) {
                        try {
                            dumpTokensLocked(printWriter, true);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("windows".equals(str2) || "w".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock7 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock7) {
                        try {
                            dumpWindowsLocked(printWriter, true, null);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("accessibility".equals(str2) || "a11y".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock8 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock8) {
                        try {
                            dumpAccessibilityLocked(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("all".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock9 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock9) {
                        try {
                            dumpWindowsLocked(printWriter, true, null);
                        } finally {
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("containers".equals(str2)) {
                    WindowManagerGlobalLock windowManagerGlobalLock10 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock10) {
                        try {
                            this.mRoot.dumpChildrenNames(printWriter, " ");
                            printWriter.println(" ");
                            this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda28
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    printWriter.println((WindowState) obj);
                                }
                            }, true);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("trace".equals(str2)) {
                    dumpTraceStatus(printWriter);
                    return;
                }
                if ("logging".equals(str2)) {
                    dumpLogStatus(printWriter);
                    return;
                }
                if ("refresh".equals(str2)) {
                    dumpHighRefreshRateBlacklist(printWriter);
                    return;
                }
                if ("constants".equals(str2)) {
                    this.mConstants.dump(printWriter);
                    return;
                }
                if ("package-config".equals(str2)) {
                    this.mAtmService.dumpInstalledPackagesConfig(printWriter);
                    return;
                }
                if (dumpWindows(printWriter, str2, z2)) {
                    return;
                }
                printWriter.println("Bad window command, or no windows match: " + str2);
                printWriter.println("Use -h for help.");
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock11 = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock11) {
                try {
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpLastANRLocked(printWriter);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpPolicyLocked(printWriter, strArr);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpAnimatorLocked(printWriter, z2);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpSessionsLocked(printWriter);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mRoot.dumpDisplayContents(printWriter);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpTokensLocked(printWriter, z2);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpWindowsLocked(printWriter, z2, null);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpTraceStatus(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpLogStatus(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpHighRefreshRateBlacklist(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mAtmService.dumpInstalledPackagesConfig(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mConstants.dump(printWriter);
                } finally {
                    resetPriorityAfterLockedSection();
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public DisplayContent getDefaultDisplayContentLocked() {
        return this.mRoot.getDisplayContent(0);
    }

    public void onOverlayChanged() {
        this.f1749mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                WindowManagerService.this.lambda$onOverlayChanged$18();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOverlayChanged$18() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtmService.deferWindowLayout();
                try {
                    this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda24
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.lambda$onOverlayChanged$17((DisplayContent) obj);
                        }
                    });
                } finally {
                    this.mAtmService.continueWindowLayout();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        FullScreenAppsSupportUtils.get().invalidateCache();
    }

    public static /* synthetic */ void lambda$onOverlayChanged$17(DisplayContent displayContent) {
        boolean hasNavigationBar = displayContent.getDisplayPolicy().hasNavigationBar();
        displayContent.getDisplayPolicy().onOverlayChanged();
        if (hasNavigationBar != displayContent.getDisplayPolicy().hasNavigationBar()) {
            displayContent.reconfigureDisplayLocked();
        }
    }

    public Object getWindowManagerLock() {
        return this.mGlobalLock;
    }

    public void setForceDesktopModeOnExternalDisplays(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mForceDesktopModeOnExternalDisplays = z;
                this.mRoot.updateDisplayImePolicyCache();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void setIsPc(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mIsPc = z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public static int dipToPixel(int i, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(1, i, displayMetrics);
    }

    public void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener) {
        if (checkCallingPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "registerPinnedTaskListener()") && this.mAtmService.mSupportsPictureInPicture) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mRoot.getDisplayContent(i).getPinnedTaskController().registerPinnedTaskListener(iPinnedTaskListener);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "requestAppKeyboardShortcuts");
        try {
            WindowState focusedWindow = getFocusedWindow();
            if (focusedWindow == null || focusedWindow.mClient == null) {
                return;
            }
            getFocusedWindow().mClient.requestAppKeyboardShortcuts(iResultReceiver, i);
        } catch (RemoteException unused) {
        }
    }

    public void getStableInsets(int i, Rect rect) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                getStableInsetsLocked(i, rect);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void getStableInsetsLocked(int i, Rect rect) {
        rect.setEmpty();
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            DisplayInfo displayInfo = displayContent.getDisplayInfo();
            rect.set(displayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight).mConfigInsets);
        }
    }

    public class MousePositionTracker implements WindowManagerPolicyConstants.PointerEventListener {
        public boolean mLatestEventWasMouse;
        public float mLatestMouseX;
        public float mLatestMouseY;
        public int mPointerDisplayId;

        public MousePositionTracker() {
            this.mPointerDisplayId = -1;
        }

        public boolean updatePosition(int i, float f, float f2) {
            synchronized (this) {
                this.mLatestEventWasMouse = true;
                if (i != this.mPointerDisplayId) {
                    return false;
                }
                this.mLatestMouseX = f;
                this.mLatestMouseY = f2;
                return true;
            }
        }

        public void setPointerDisplayId(int i) {
            synchronized (this) {
                this.mPointerDisplayId = i;
            }
        }

        public void onPointerEvent(MotionEvent motionEvent) {
            if (motionEvent.isFromSource(8194)) {
                updatePosition(motionEvent.getDisplayId(), motionEvent.getRawX(), motionEvent.getRawY());
            } else {
                synchronized (this) {
                    this.mLatestEventWasMouse = false;
                }
            }
        }
    }

    public void updatePointerIcon(IWindow iWindow) {
        synchronized (this.mMousePositionTracker) {
            if (this.mMousePositionTracker.mLatestEventWasMouse) {
                float f = this.mMousePositionTracker.mLatestMouseX;
                float f2 = this.mMousePositionTracker.mLatestMouseY;
                int i = this.mMousePositionTracker.mPointerDisplayId;
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (this.mDragDropController.dragDropActiveLocked()) {
                            resetPriorityAfterLockedSection();
                            return;
                        }
                        WindowState windowForClientLocked = windowForClientLocked((Session) null, iWindow, false);
                        if (windowForClientLocked == null) {
                            if (ProtoLogCache.WM_ERROR_enabled) {
                                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1325649102, 0, "Bad requesting window %s", new Object[]{String.valueOf(iWindow)});
                            }
                            resetPriorityAfterLockedSection();
                            return;
                        }
                        DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                        if (displayContent == null) {
                            resetPriorityAfterLockedSection();
                            return;
                        }
                        if (i != displayContent.getDisplayId()) {
                            resetPriorityAfterLockedSection();
                            return;
                        }
                        WindowState touchableWinAtPointLocked = displayContent.getTouchableWinAtPointLocked(f, f2);
                        if (touchableWinAtPointLocked != windowForClientLocked) {
                            resetPriorityAfterLockedSection();
                            return;
                        }
                        try {
                            touchableWinAtPointLocked.mClient.updatePointerIcon(touchableWinAtPointLocked.translateToWindowX(f), touchableWinAtPointLocked.translateToWindowY(f2));
                        } catch (RemoteException unused) {
                            if (ProtoLogCache.WM_ERROR_enabled) {
                                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -393505149, 0, "unable to update pointer icon", (Object[]) null);
                            }
                        }
                        resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        }
    }

    public void restorePointerIconLocked(DisplayContent displayContent, float f, float f2) {
        restorePointerIconLocked(displayContent, f, f2, true);
    }

    public void restorePointerIconLocked(DisplayContent displayContent, float f, float f2, boolean z) {
        if (this.mMousePositionTracker.updatePosition(displayContent.getDisplayId(), f, f2)) {
            WindowState touchableWinAtPointLocked = displayContent.getTouchableWinAtPointLocked(f, f2);
            if (touchableWinAtPointLocked != null) {
                try {
                    touchableWinAtPointLocked.mClient.updatePointerIcon(touchableWinAtPointLocked.translateToWindowX(f), touchableWinAtPointLocked.translateToWindowY(f2));
                    return;
                } catch (RemoteException unused) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1423418408, 0, "unable to restore pointer icon", (Object[]) null);
                        return;
                    }
                    return;
                }
            }
            ((InputManager) this.mContext.getSystemService(InputManager.class)).setPointerIconType(1000);
        }
    }

    public void setMousePointerDisplayId(int i) {
        this.mMousePositionTracker.setPointerDisplayId(i);
    }

    public void updateTapExcludeRegion(IWindow iWindow, Region region) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked((Session) null, iWindow, false);
                if (windowForClientLocked == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1325649102, 0, "Bad requesting window %s", new Object[]{String.valueOf(iWindow)});
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                windowForClientLocked.updateTapExcludeRegion(region);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void requestScrollCapture(int i, IBinder iBinder, int i2, IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        ScrollCaptureResponse.Builder builder;
        WindowManagerGlobalLock windowManagerGlobalLock;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "requestScrollCapture()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                builder = new ScrollCaptureResponse.Builder();
                windowManagerGlobalLock = this.mGlobalLock;
                boostPriorityForLockedSection();
            } catch (RemoteException e) {
                if (ProtoLogCache.WM_ERROR_enabled) {
                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1046922686, 0, "requestScrollCapture: caught exception dispatching callback: %s", new Object[]{String.valueOf(e)});
                }
            }
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.e(ProtoLogGroup.WM_ERROR, 646981048, 1, "Invalid displayId for requestScrollCapture: %d", new Object[]{Long.valueOf(i)});
                        }
                        builder.setDescription(String.format("bad displayId: %d", Integer.valueOf(i)));
                        iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                    } else {
                        WindowState findScrollCaptureTargetWindow = displayContent.findScrollCaptureTargetWindow(iBinder != null ? windowForClientLocked((Session) null, iBinder, false) : null, i2);
                        if (findScrollCaptureTargetWindow == null) {
                            builder.setDescription("findScrollCaptureTargetWindow returned null");
                            iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                        } else {
                            try {
                                findScrollCaptureTargetWindow.mClient.requestScrollCapture(iScrollCaptureResponseListener);
                            } catch (RemoteException e2) {
                                if (ProtoLogCache.WM_ERROR_enabled) {
                                    ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1517908912, 0, "requestScrollCapture: caught exception dispatching to window.token=%s", new Object[]{String.valueOf(findScrollCaptureTargetWindow.mClient.asBinder())});
                                }
                                builder.setWindowTitle(findScrollCaptureTargetWindow.getName());
                                builder.setPackageName(findScrollCaptureTargetWindow.getOwningPackage());
                                builder.setDescription(String.format("caught exception: %s", e2));
                                iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                            }
                            resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getWindowingMode(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "getWindowingMode()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 51628177, 1, "Attempted to get windowing mode of a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                    }
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                int windowingModeLocked = this.mDisplayWindowSettings.getWindowingModeLocked(displayContent);
                resetPriorityAfterLockedSection();
                return windowingModeLocked;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setWindowingMode(int i, int i2) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setWindowingMode()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -1838803135, 1, "Attempted to set windowing mode to a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    int windowingMode = displayContentOrCreate.getWindowingMode();
                    this.mDisplayWindowSettings.setWindowingModeLocked(displayContentOrCreate, i2);
                    displayContentOrCreate.reconfigureDisplayLocked();
                    if (windowingMode != displayContentOrCreate.getWindowingMode()) {
                        displayContentOrCreate.sendNewConfiguration();
                        displayContentOrCreate.executeAppTransition();
                    }
                    resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getRemoveContentMode(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "getRemoveContentMode()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -496681057, 1, "Attempted to get remove mode of a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                    }
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                int removeContentModeLocked = this.mDisplayWindowSettings.getRemoveContentModeLocked(displayContent);
                resetPriorityAfterLockedSection();
                return removeContentModeLocked;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setRemoveContentMode(int i, int i2) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setRemoveContentMode()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 288485303, 1, "Attempted to set remove mode to a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                        }
                        resetPriorityAfterLockedSection();
                    } else {
                        this.mDisplayWindowSettings.setRemoveContentModeLocked(displayContentOrCreate, i2);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldShowWithInsecureKeyguard(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "shouldShowWithInsecureKeyguard()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1434383382, 1, "Attempted to get flag of a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                    }
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean shouldShowWithInsecureKeyguardLocked = this.mDisplayWindowSettings.shouldShowWithInsecureKeyguardLocked(displayContent);
                resetPriorityAfterLockedSection();
                return shouldShowWithInsecureKeyguardLocked;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setShouldShowWithInsecureKeyguard(int i, boolean z) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setShouldShowWithInsecureKeyguard()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1521476038, 1, "Attempted to set flag to a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                        }
                        resetPriorityAfterLockedSection();
                    } else {
                        this.mDisplayWindowSettings.setShouldShowWithInsecureKeyguardLocked(displayContentOrCreate, z);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldShowSystemDecors(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "shouldShowSystemDecors()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 11060725, 1, "Attempted to get system decors flag of a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                    }
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean supportsSystemDecorations = displayContent.supportsSystemDecorations();
                resetPriorityAfterLockedSection();
                return supportsSystemDecorations;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setShouldShowSystemDecors(int i, boolean z) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setShouldShowSystemDecors()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -386552155, 1, "Attempted to set system decors flag to a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                        }
                        resetPriorityAfterLockedSection();
                    } else {
                        if (!displayContentOrCreate.isTrusted()) {
                            throw new SecurityException("Attempted to set system decors flag to an untrusted virtual display: " + i);
                        }
                        this.mDisplayWindowSettings.setShouldShowSystemDecorsLocked(displayContentOrCreate, z);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getDisplayImePolicy(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "getDisplayImePolicy()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        Map map = this.mDisplayImePolicyCache;
        if (!map.containsKey(Integer.valueOf(i))) {
            if (ProtoLogCache.WM_ERROR_enabled) {
                ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 1100065297, 1, "Attempted to get IME policy of a display that does not exist: %d", new Object[]{Long.valueOf(i)});
            }
            return 1;
        }
        return ((Integer) map.get(Integer.valueOf(i))).intValue();
    }

    public void setDisplayImePolicy(int i, int i2) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setDisplayImePolicy()")) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        if (ProtoLogCache.WM_ERROR_enabled) {
                            ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, -292790591, 1, "Attempted to set IME policy to a display that does not exist: %d", new Object[]{Long.valueOf(i)});
                        }
                        resetPriorityAfterLockedSection();
                    } else {
                        if (!displayContentOrCreate.isTrusted()) {
                            throw new SecurityException("Attempted to set IME policy to an untrusted virtual display: " + i);
                        }
                        this.mDisplayWindowSettings.setDisplayImePolicy(displayContentOrCreate, i2);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerShortcutKey(long j, IShortcutService iShortcutService) {
        if (!checkCallingPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "registerShortcutKey")) {
            throw new SecurityException("Requires REGISTER_WINDOW_MANAGER_LISTENERS permission");
        }
        this.mPolicy.registerShortcutKey(j, iShortcutService);
    }

    public final class LocalService extends WindowManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public WindowManagerInternal.AccessibilityControllerInternal getAccessibilityController() {
            return AccessibilityController.getAccessibilityControllerInternal(WindowManagerService.this);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void clearSnapshotCache() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mTaskSnapshotController.clearSnapshotCache();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void requestTraversalFromDisplayManager() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.requestTraversal();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void onDisplayManagerReceivedDeviceState(final int i) {
            WindowManagerService.this.f1749mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerService.LocalService.this.lambda$onDisplayManagerReceivedDeviceState$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayManagerReceivedDeviceState$0(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mRoot.onDisplayManagerReceivedDeviceState(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setMagnificationSpec(int i, MagnificationSpec magnificationSpec) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        WindowManagerService.this.mAccessibilityController.setMagnificationSpec(i, magnificationSpec);
                    } else {
                        throw new IllegalStateException("Magnification callbacks not set!");
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setForceShowMagnifiableBounds(int i, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        WindowManagerService.this.mAccessibilityController.setForceShowMagnifiableBounds(i, z);
                    } else {
                        throw new IllegalStateException("Magnification callbacks not set!");
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void getMagnificationRegion(int i, Region region) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        WindowManagerService.this.mAccessibilityController.getMagnificationRegion(i, region);
                    } else {
                        throw new IllegalStateException("Magnification callbacks not set!");
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean setMagnificationCallbacks(int i, WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
            boolean magnificationCallbacks2;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    magnificationCallbacks2 = WindowManagerService.this.mAccessibilityController.setMagnificationCallbacks(i, magnificationCallbacks);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return magnificationCallbacks2;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWindowsForAccessibilityCallback(int i, WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mAccessibilityController.setWindowsForAccessibilityCallback(i, windowsForAccessibilityCallback);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setInputFilter(IInputFilter iInputFilter) {
            WindowManagerService.this.mInputManager.setInputFilter(iInputFilter);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public IBinder getFocusedWindowToken() {
            IBinder focusedWindowToken;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    focusedWindowToken = WindowManagerService.this.mAccessibilityController.getFocusedWindowToken();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return focusedWindowToken;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public IBinder getFocusedWindowTokenFromWindowStates() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState focusedWindowLocked = WindowManagerService.this.getFocusedWindowLocked();
                    if (focusedWindowLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    IBinder asBinder = focusedWindowLocked.mClient.asBinder();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return asBinder;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void moveDisplayToTopIfAllowed(int i) {
            WindowManagerService.this.moveDisplayToTopIfAllowed(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void requestWindowFocus(IBinder iBinder) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.onPointerDownOutsideFocusLocked(WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isKeyguardLocked() {
            return WindowManagerService.this.isKeyguardLocked();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isKeyguardShowingAndNotOccluded() {
            return WindowManagerService.this.isKeyguardShowingAndNotOccluded();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isKeyguardSecure(int i) {
            return WindowManagerService.this.mPolicy.isKeyguardSecure(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void showGlobalActions() {
            WindowManagerService.this.showGlobalActions();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void getWindowFrame(IBinder iBinder, Rect rect) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState != null) {
                        rect.set(windowState.getFrame());
                    } else {
                        rect.setEmpty();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isEmbeddedWindowType(IBinder iBinder) {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = WindowManagerService.this.mEmbeddedWindowController.getByWindowToken(iBinder) != null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public Pair getWindowTransformationMatrixAndMagnificationSpec(IBinder iBinder) {
            return WindowManagerService.this.mAccessibilityController.getWindowTransformationMatrixAndMagnificationSpec(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void waitForAllWindowsDrawn(Message message, long j, int i) {
            boolean z;
            Objects.requireNonNull(message.getTarget());
            WindowContainer displayContent = i == -1 ? WindowManagerService.this.mRoot : WindowManagerService.this.mRoot.getDisplayContent(i);
            if (displayContent == null) {
                message.sendToTarget();
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayContent.waitForAllWindowsDrawn();
                    WindowManagerService.this.mWindowPlacerLocked.requestTraversal();
                    WindowManagerService.this.f1749mH.removeMessages(24, displayContent);
                    if (displayContent.mWaitingForDrawn.isEmpty()) {
                        z = true;
                    } else {
                        if (Trace.isTagEnabled(32L)) {
                            for (int i2 = 0; i2 < displayContent.mWaitingForDrawn.size(); i2++) {
                                WindowManagerService.this.traceStartWaitingForWindowDrawn((WindowState) displayContent.mWaitingForDrawn.get(i2));
                            }
                        }
                        WindowManagerService.this.mWaitingForDrawnCallbacks.put(displayContent, message);
                        WindowManagerService.this.f1749mH.sendNewMessageDelayed(24, displayContent, j);
                        WindowManagerService.this.checkDrawnWindowsLocked();
                        z = false;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (z) {
                message.sendToTarget();
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setForcedDisplaySize(int i, int i2, int i3) {
            WindowManagerService.this.setForcedDisplaySize(i, i2, i3);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void clearForcedDisplaySize(int i) {
            WindowManagerService.this.clearForcedDisplaySize(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) {
            WindowManagerService.this.addWindowToken(iBinder, i, i2, bundle);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeWindowToken(IBinder iBinder, boolean z, boolean z2, int i) {
            WindowManagerService.this.removeWindowToken(iBinder, z, z2, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void moveWindowTokenToDisplay(IBinder iBinder, int i) {
            WindowManagerService.this.moveWindowTokenToDisplay(iBinder, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerAppTransitionListener(WindowManagerInternal.AppTransitionListener appTransitionListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.getDefaultDisplayContentLocked().mAppTransition.registerListenerLocked(appTransitionListener);
                    WindowManagerService.this.mAtmService.getTransitionController().registerLegacyListener(appTransitionListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerTaskSystemBarsListener(WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mTaskSystemBarsListenerController.registerListener(taskSystemBarsListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void unregisterTaskSystemBarsListener(WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mTaskSystemBarsListenerController.unregisterListener(taskSystemBarsListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerKeyguardExitAnimationStartListener(WindowManagerInternal.KeyguardExitAnimationStartListener keyguardExitAnimationStartListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.getDefaultDisplayContentLocked().mAppTransition.registerKeygaurdExitAnimationStartListener(keyguardExitAnimationStartListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void reportPasswordChanged(int i) {
            WindowManagerService.this.mKeyguardDisableHandler.updateKeyguardEnabled(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getInputMethodWindowVisibleHeight(int i) {
            int inputMethodWindowVisibleHeight;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    inputMethodWindowVisibleHeight = WindowManagerService.this.mRoot.getDisplayContent(i).getInputMethodWindowVisibleHeight();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return inputMethodWindowVisibleHeight;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setDismissImeOnBackKeyPressed(boolean z, boolean z2) {
            WindowManagerService.this.mPolicy.setDismissImeOnBackKeyPressed(z);
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
                        WindowManagerService.this.mExt.updateImeWindowVisibility(z2);
                        WindowManagerService.this.mExt.updateTspStateControllerWindowPolicyLocked(null);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void updateInputMethodTargetWindow(IBinder iBinder, IBinder iBinder2) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    InputTarget inputTargetFromWindowTokenLocked = WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder2);
                    if (inputTargetFromWindowTokenLocked != null) {
                        inputTargetFromWindowTokenLocked.getDisplayContent().updateImeInputAndControlTarget(inputTargetFromWindowTokenLocked);
                        WindowManagerService.this.mAtmService.mDexController.setInputMethodInputTargetLocked(inputTargetFromWindowTokenLocked.getWindowState());
                    }
                    if (CoreRune.FW_TSP_STATE_CONTROLLER) {
                        WindowManagerService.this.mExt.updateImeTargetWindow(inputTargetFromWindowTokenLocked != null ? inputTargetFromWindowTokenLocked.getWindowState() : null);
                        WindowManagerService.this.mExt.updateTspStateControllerWindowPolicyLocked(null);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isHardKeyboardAvailable() {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = WindowManagerService.this.mHardKeyboardAvailable;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setOnHardKeyboardStatusChangeListener(WindowManagerInternal.OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mHardKeyboardStatusChangeListener = onHardKeyboardStatusChangeListener;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void computeWindowsForAccessibility(int i) {
            WindowManagerService.this.mAccessibilityController.performComputeChangedWindowsNot(i, true);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setVr2dDisplayId(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mVr2dDisplayId = i;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerDragDropControllerCallback(WindowManagerInternal.IDragDropCallback iDragDropCallback) {
            WindowManagerService.this.mDragDropController.registerCallback(iDragDropCallback);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void lockNow() {
            WindowManagerService.this.lockNow(null);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getWindowOwnerUserId(IBinder iBinder) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState != null) {
                        int i = windowState.mShowUserId;
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return i;
                    }
                    EmbeddedWindowController.EmbeddedWindow byWindowToken = WindowManagerService.this.mEmbeddedWindowController.getByWindowToken(iBinder);
                    if (byWindowToken == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -10000;
                    }
                    int i2 = byWindowToken.mShowUserId;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return i2;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWallpaperShowWhenLocked(IBinder iBinder, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowToken windowToken = WindowManagerService.this.mRoot.getWindowToken(iBinder);
                    if (windowToken != null && windowToken.asWallpaperToken() != null) {
                        windowToken.asWallpaperToken().setShowWhenLocked(z);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (ProtoLogCache.WM_ERROR_enabled) {
                        ProtoLogImpl.w(ProtoLogGroup.WM_ERROR, 2043434284, 0, "setWallpaperShowWhenLocked: non-existent wallpaper token: %s", new Object[]{String.valueOf(iBinder)});
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isUidFocused(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    for (int childCount = WindowManagerService.this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
                        WindowState windowState = ((DisplayContent) WindowManagerService.this.mRoot.getChildAt(childCount)).mCurrentFocus;
                        if (windowState != null && i == windowState.getOwningUid()) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int hasInputMethodClientFocus(IBinder iBinder, int i, int i2, int i3) {
            if (i3 == -1) {
                return -3;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent topFocusedDisplayContent = WindowManagerService.this.mRoot.getTopFocusedDisplayContent();
                    InputTarget inputTargetFromWindowTokenLocked = WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder);
                    if (inputTargetFromWindowTokenLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    int displayId = inputTargetFromWindowTokenLocked.getDisplayContent().getDisplayId();
                    if (displayId != i3) {
                        Slog.e(StartingSurfaceController.TAG, "isInputMethodClientFocus: display ID mismatch. from client: " + i3 + " from window: " + displayId);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -2;
                    }
                    if (topFocusedDisplayContent != null && topFocusedDisplayContent.getDisplayId() == i3 && topFocusedDisplayContent.hasAccess(i)) {
                        if (inputTargetFromWindowTokenLocked.isInputMethodClientFocus(i, i2)) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return 0;
                        }
                        WindowState windowState = topFocusedDisplayContent.mCurrentFocus;
                        if (windowState != null) {
                            Session session = windowState.mSession;
                            if (session.mUid == i && session.mPid == i2) {
                                int i4 = windowState.canBeImeTarget() ? 0 : -1;
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return i4;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -3;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void showImePostLayout(IBinder iBinder, ImeTracker.Token token) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    InputTarget inputTargetFromWindowTokenLocked = WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder);
                    if (inputTargetFromWindowTokenLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    Trace.asyncTraceBegin(32L, "WMS.showImePostLayout", 0);
                    if (inputTargetFromWindowTokenLocked.getDisplayId() == 2 && WindowManagerService.this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked() && WindowManagerService.this.mAtmService.mDexController.showDexImeOnDefaultDisplayLocked()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    InsetsControlTarget imeControlTarget = inputTargetFromWindowTokenLocked.getImeControlTarget();
                    WindowState window = imeControlTarget.getWindow();
                    (window != null ? window.getDisplayContent() : WindowManagerService.this.getDefaultDisplayContentLocked()).getInsetsStateController().getImeSourceProvider().scheduleShowImePostLayout(imeControlTarget, token);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void hideIme(IBinder iBinder, int i, ImeTracker.Token token) {
            Trace.traceBegin(32L, "WMS.hideIme");
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (ProtoLogCache.WM_DEBUG_IME_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_IME, 95216706, 0, (String) null, new Object[]{String.valueOf(windowState)});
                    }
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (windowState != null) {
                        if (windowState.getDisplayId() == 2 && WindowManagerService.this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked() && WindowManagerService.this.mAtmService.mDexController.hideDexImeOnDefaultDisplayLocked()) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        windowState = windowState.getImeControlTarget().getWindow();
                        if (windowState != null) {
                            displayContent = windowState.getDisplayContent();
                        }
                        displayContent.getInsetsStateController().getImeSourceProvider().abortShowImePostLayout();
                    }
                    if (displayContent != null && displayContent.getImeTarget(2) != null) {
                        ImeTracker.forLogging().onProgress(token, 20);
                        if (ProtoLogCache.WM_DEBUG_IME_enabled) {
                            ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_IME, -547111355, 0, (String) null, new Object[]{String.valueOf(displayContent.getImeTarget(2))});
                        }
                        displayContent.getImeTarget(2).hideInsets(WindowInsets.Type.ime(), true, token);
                    } else {
                        ImeTracker.forLogging().onFailed(token, 20);
                    }
                    if (displayContent != null) {
                        displayContent.getInsetsStateController().getImeSourceProvider().setImeShowing(false);
                        if (windowState != null) {
                            updateRemoteInsetsVisibleTypesIfNeededLocked(displayContent, windowState);
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Trace.traceEnd(32L);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        public final void updateRemoteInsetsVisibleTypesIfNeededLocked(DisplayContent displayContent, WindowState windowState) {
            InsetsControlTarget imeTarget = displayContent.getImeTarget(2);
            if (imeTarget == null || imeTarget != displayContent.mRemoteInsetsControlTarget) {
                return;
            }
            displayContent.getStableRect(WindowManagerService.this.mTmpRect);
            WindowState windowState2 = displayContent.mInputMethodWindow;
            if (windowState2 != null && windowState2.isVisible() && displayContent.mInputMethodWindow.mGivenContentInsets.top == WindowManagerService.this.mTmpRect.height()) {
                if (windowState.inSplitScreenWindowingMode() || windowState.inFreeformWindowingMode()) {
                    Log.d(StartingSurfaceController.TAG, "updateRemoteInsetsVisibleTypesIfNeededLocked: imeTarget=" + windowState);
                    displayContent.mRemoteInsetsControlTarget.clearImeRequestedVisibleTypes("fullscreen_layout");
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isUidAllowedOnDisplay(int i, int i2) {
            boolean z = true;
            if (i == 0) {
                return true;
            }
            if (i == -1) {
                return false;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null || !displayContent.hasAccess(i2)) {
                        z = false;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getDisplayIdForWindow(IBinder iBinder) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    int displayId = windowState.getDisplayContent().getDisplayId();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return displayId;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getTopFocusedDisplayId() {
            int displayId;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayId = WindowManagerService.this.mRoot.getTopFocusedDisplayContent().getDisplayId();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return displayId;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public Context getTopFocusedDisplayUiContext() {
            Context displayUiContext;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayUiContext = WindowManagerService.this.mRoot.getTopFocusedDisplayContent().getDisplayUiContext();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return displayUiContext;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean shouldShowSystemDecorOnDisplay(int i) {
            boolean shouldShowSystemDecors;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    shouldShowSystemDecors = WindowManagerService.this.shouldShowSystemDecors(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return shouldShowSystemDecors;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getDisplayImePolicy(int i) {
            return WindowManagerService.this.getDisplayImePolicy(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addRefreshRateRangeForPackage(final String str, final float f, final float f2) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.LocalService.lambda$addRefreshRateRangeForPackage$1(str, f, f2, (DisplayContent) obj);
                        }
                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public static /* synthetic */ void lambda$addRefreshRateRangeForPackage$1(String str, float f, float f2, DisplayContent displayContent) {
            displayContent.getDisplayPolicy().getRefreshRatePolicy().addRefreshRateRangeForPackage(str, f, f2);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeRefreshRateRangeForPackage(final String str) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.LocalService.lambda$removeRefreshRateRangeForPackage$2(str, (DisplayContent) obj);
                        }
                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public static /* synthetic */ void lambda$removeRefreshRateRangeForPackage$2(String str, DisplayContent displayContent) {
            displayContent.getDisplayPolicy().getRefreshRatePolicy().removeRefreshRateRangeForPackage(str);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addFixedRefreshRatePackageInternal(final String str, final int i) {
            if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE) {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                WindowManagerService.LocalService.lambda$addFixedRefreshRatePackageInternal$3(str, i, (DisplayContent) obj);
                            }
                        });
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public static /* synthetic */ void lambda$addFixedRefreshRatePackageInternal$3(String str, int i, DisplayContent displayContent) {
            displayContent.getDisplayPolicy().getRefreshRatePolicy().addFixedRefreshRatePackage(str, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeFixedRefreshRatePackageInternal(final String str) {
            if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE) {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                WindowManagerService.LocalService.lambda$removeFixedRefreshRatePackageInternal$4(str, (DisplayContent) obj);
                            }
                        });
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        public static /* synthetic */ void lambda$removeFixedRefreshRatePackageInternal$4(String str, DisplayContent displayContent) {
            displayContent.getDisplayPolicy().getRefreshRatePolicy().removeFixedRefreshRatePackage(str);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isTouchOrFaketouchDevice() {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService windowManagerService = WindowManagerService.this;
                    if (windowManagerService.mIsTouchDevice && !windowManagerService.mIsFakeTouchDevice) {
                        throw new IllegalStateException("touchscreen supported device must report faketouch.");
                    }
                    z = windowManagerService.mIsFakeTouchDevice;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public KeyInterceptionInfo getKeyInterceptionInfoFromToken(IBinder iBinder) {
            return (KeyInterceptionInfo) WindowManagerService.this.mKeyInterceptionInfoForToken.get(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setAccessibilityIdToSurfaceMetadata(IBinder iBinder, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        Slog.w(StartingSurfaceController.TAG, "Cannot find window which accessibility connection is added to");
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        WindowManagerService.this.mTransaction.setMetadata(windowState.mSurfaceControl, 5, i).apply();
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public String getWindowName(IBinder iBinder) {
            String name;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    name = windowState != null ? windowState.getName() : null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return name;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public WindowManagerInternal.ImeTargetInfo onToggleImeRequested(boolean z, IBinder iBinder, IBinder iBinder2, int i) {
            String name;
            String name2;
            String str;
            String str2;
            String str3;
            String str4;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    name = windowState != null ? windowState.getName() : "null";
                    WindowState windowState2 = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder2);
                    name2 = windowState2 != null ? windowState2.getName() : "null";
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        InsetsControlTarget imeTarget = displayContent.getImeTarget(2);
                        if (imeTarget != null) {
                            WindowState asWindowOrNull = InsetsControlTarget.asWindowOrNull(imeTarget);
                            str4 = asWindowOrNull != null ? asWindowOrNull.getName() : imeTarget.toString();
                        } else {
                            str4 = "null";
                        }
                        InsetsControlTarget imeTarget2 = displayContent.getImeTarget(0);
                        String name3 = imeTarget2 != null ? imeTarget2.getWindow().getName() : "null";
                        SurfaceControl surfaceControl = displayContent.mInputMethodSurfaceParent;
                        String surfaceControl2 = surfaceControl != null ? surfaceControl.toString() : "null";
                        if (z) {
                            displayContent.onShowImeRequested();
                        }
                        str = str4;
                        str2 = name3;
                        str3 = surfaceControl2;
                    } else {
                        str = "no-display";
                        str2 = str;
                        str3 = str2;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return new WindowManagerInternal.ImeTargetInfo(name, name2, str, str2, str3);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean shouldRestoreImeVisibility(IBinder iBinder) {
            return WindowManagerService.this.shouldRestoreImeVisibility(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task rootTask = WindowManagerService.this.mRoot.getRootTask(i);
                    if (rootTask == null) {
                        throw new IllegalArgumentException("no task with taskId" + i);
                    }
                    rootTask.addTrustedOverlay(surfacePackage, rootTask.getTopVisibleAppMainWindow());
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task rootTask = WindowManagerService.this.mRoot.getRootTask(i);
                    if (rootTask == null) {
                        throw new IllegalArgumentException("no task with taskId" + i);
                    }
                    rootTask.removeTrustedOverlay(surfacePackage);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public SurfaceControl getHandwritingSurfaceForDisplay(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.e(StartingSurfaceController.TAG, "Failed to create a handwriting surface on display: " + i + " - DisplayContent not found.");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    SurfaceControl inputOverlayLayer = displayContent.getInputOverlayLayer();
                    if (inputOverlayLayer == null) {
                        Slog.e(StartingSurfaceController.TAG, "Failed to create a gesture monitor on display: " + i + " - Input overlay layer is not initialized.");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    SurfaceControl build = WindowManagerService.this.makeSurfaceBuilder(displayContent.getSession()).setContainerLayer().setName("IME Handwriting Surface").setCallsite("getHandwritingSurfaceForDisplay").setParent(inputOverlayLayer).build();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return build;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isPointInsideWindow(IBinder iBinder, int i, float f, float f2) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState != null && windowState.getDisplayId() == i) {
                        boolean contains = windowState.getBounds().contains((int) f, (int) f2);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return contains;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (contentRecordingSession != null) {
                    try {
                        if (contentRecordingSession.getContentToRecord() == 1) {
                            WindowContainerToken taskWindowContainerTokenForLaunchCookie = WindowManagerService.this.getTaskWindowContainerTokenForLaunchCookie(contentRecordingSession.getTokenToRecord());
                            if (taskWindowContainerTokenForLaunchCookie == null) {
                                Slog.w(StartingSurfaceController.TAG, "Handling a new recording session; unable to find the WindowContainerToken");
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            contentRecordingSession.setTokenToRecord(taskWindowContainerTokenForLaunchCookie.asBinder());
                            WindowManagerService windowManagerService = WindowManagerService.this;
                            windowManagerService.mContentRecordingController.setContentRecordingSessionLocked(contentRecordingSession, windowManagerService);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService windowManagerService2 = WindowManagerService.this;
                windowManagerService2.mContentRecordingController.setContentRecordingSessionLocked(contentRecordingSession, windowManagerService2);
                WindowManagerService.resetPriorityAfterLockedSection();
                return true;
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public SurfaceControl getA11yOverlayLayer(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    SurfaceControl a11yOverlayLayer = displayContent.getA11yOverlayLayer();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return a11yOverlayLayer;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setInputMethodTargetChangeListener(ImeTargetChangeListener imeTargetChangeListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mImeTargetChangeListener = imeTargetChangeListener;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot() {
            return WindowManagerService.this.takeAssistScreenshot();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public Bitmap takeScreenshotToTargetWindowInternal(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
            if (!WindowManagerService.this.checkCallingPermission("android.permission.READ_FRAME_BUFFER", "takeScreenshotToTargetWindow()")) {
                throw new SecurityException("Requires READ_FRAME_BUFFER permission");
            }
            ScreenshotResult takeScreenshotToTargetWindow = WindowManagerService.this.mExt.mScreenshotController.takeScreenshotToTargetWindow(i, i2, z, rect, i3, i4, z2, Binder.getCallingUid() == 1000);
            if (takeScreenshotToTargetWindow != null) {
                return takeScreenshotToTargetWindow.getCapturedBitmap();
            }
            return null;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setDisplaySizeAndDensityInDex(int i, int i2, int i3, int i4) {
            if (WindowManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                        if (displayContent == null) {
                            Slog.w(StartingSurfaceController.TAG, "setDisplaySizeAndDensityInDex: cannot find dc, #" + i);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        WindowManagerService.this.mAtmService.mDexController.setDisplaySizeAndDensityLocked(displayContent, i2, i3, i4);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int[] getInitialDisplayProperties(int i) {
            return WindowManagerService.this.mExt.getInitialDisplayProperties(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void launchHomeForDesktopMode(int i) {
            WindowManagerService.this.mExt.launchHomeForDesktopMode(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void freezeDisplayRotation(int i, int i2) {
            Slog.d(StartingSurfaceController.TAG, "freezeDisplayRotation: #" + i + ", rot=" + i2);
            WindowManagerService.this.freezeDisplayRotation(i, i2);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void updateMirroredSurface(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "updateMirroredSurface: cannot find dc, #" + i);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.updateMirroredSurfaceFromDisplayManager();
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerAppTransitionListener(WindowManagerInternal.AppTransitionListener appTransitionListener, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.mAppTransition.registerListenerLocked(appTransitionListener);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void unregisterAppTransitionListener(WindowManagerInternal.AppTransitionListener appTransitionListener, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.mAppTransition.unregisterListener(appTransitionListener);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setDeviceStateListener(DeviceStateManager.DeviceStateCallback deviceStateCallback) {
            WindowManagerService.this.mExt.getClass();
            throw null;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void showBootDialog(int i) {
            WindowManagerService.this.showBootMessage(null, true, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWallpaperFoldedType(IBinder iBinder, boolean z) {
            if (CoreRune.FW_FOLD_WALLPAPER_POLICY) {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowToken windowToken = WindowManagerService.this.mRoot.getWindowToken(iBinder);
                        if (windowToken != null && windowToken.asWallpaperToken() != null) {
                            windowToken.asWallpaperToken().setIsFoldedType(z);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        Slog.w(StartingSurfaceController.TAG, "setWallpaperFoldableType: non-existent wallpaper token=" + iBinder);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        }
    }

    public final class ImeTargetVisibilityPolicyImpl extends ImeTargetVisibilityPolicy {
        public ImeTargetVisibilityPolicyImpl() {
        }

        @Override // com.android.server.wm.ImeTargetVisibilityPolicy
        public boolean showImeScreenshot(IBinder iBinder, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Invalid displayId:" + i + ", fail to show ime screenshot");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    displayContent.showImeScreenshot(windowState);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ImeTargetVisibilityPolicy
        public boolean removeImeScreenshot(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Invalid displayId:" + i + ", fail to remove ime screenshot");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    displayContent.removeImeSurfaceImmediately();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public void registerAppFreezeListener(AppFreezeListener appFreezeListener) {
        if (this.mAppFreezeListeners.contains(appFreezeListener)) {
            return;
        }
        this.mAppFreezeListeners.add(appFreezeListener);
    }

    public void unregisterAppFreezeListener(AppFreezeListener appFreezeListener) {
        this.mAppFreezeListeners.remove(appFreezeListener);
    }

    public void inSurfaceTransaction(Runnable runnable) {
        SurfaceControl.openTransaction();
        try {
            runnable.run();
        } finally {
            SurfaceControl.closeTransaction();
        }
    }

    public void disableNonVrUi(boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            boolean z2 = !z;
            try {
                if (z2 == this.mShowAlertWindowNotifications) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mShowAlertWindowNotifications = z2;
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    ((Session) this.mSessions.valueAt(size)).setShowingAlertWindowNotificationAllowed(this.mShowAlertWindowNotifications);
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean hasWideColorGamutSupport() {
        return this.mHasWideColorGamutSupport && SystemProperties.getInt("persist.sys.sf.native_mode", 0) != 1;
    }

    public boolean hasHdrSupport() {
        return this.mHasHdrSupport && hasWideColorGamutSupport();
    }

    public void updateNonSystemOverlayWindowsVisibilityIfNeeded(WindowState windowState, boolean z) {
        if (windowState.hideNonSystemOverlayWindowsWhenVisible() || this.mHidingNonSystemOverlayWindows.contains(windowState)) {
            boolean z2 = !this.mHidingNonSystemOverlayWindows.isEmpty();
            if (z && windowState.hideNonSystemOverlayWindowsWhenVisible()) {
                if (!this.mHidingNonSystemOverlayWindows.contains(windowState)) {
                    this.mHidingNonSystemOverlayWindows.add(windowState);
                }
            } else {
                this.mHidingNonSystemOverlayWindows.remove(windowState);
            }
            final boolean z3 = !this.mHidingNonSystemOverlayWindows.isEmpty();
            if (z2 == z3) {
                return;
            }
            this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((WindowState) obj).setForceHideNonSystemOverlayWindowIfNeeded(z3);
                }
            }, false);
        }
    }

    public void applyMagnificationSpecLocked(int i, MagnificationSpec magnificationSpec) {
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            displayContent.applyMagnificationSpec(magnificationSpec);
        }
    }

    public SurfaceControl.Builder makeSurfaceBuilder(SurfaceSession surfaceSession) {
        return (SurfaceControl.Builder) this.mSurfaceControlFactory.apply(surfaceSession);
    }

    public void onLockTaskStateChanged(final int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mExt.mPolicyExt.onLockTaskStateChanged(i);
                this.mRoot.forAllDisplayPolicies(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DisplayPolicy) obj).onLockTaskStateChangedLw(i);
                    }
                });
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void syncInputTransactions(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                try {
                    waitForAnimationsToComplete();
                } catch (InterruptedException e) {
                    Slog.e(StartingSurfaceController.TAG, "Exception thrown while waiting for window infos to be reported", e);
                }
            }
            final SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionFactory.get();
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mWindowPlacerLocked.performSurfacePlacementIfScheduled();
                    this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            WindowManagerService.lambda$syncInputTransactions$21(transaction, (DisplayContent) obj);
                        }
                    });
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            transaction.addWindowInfosReportedListener(new SettingsStore$$ExternalSyntheticLambda1(countDownLatch)).apply();
            countDownLatch.await(5000L, TimeUnit.MILLISECONDS);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ void lambda$syncInputTransactions$21(SurfaceControl.Transaction transaction, DisplayContent displayContent) {
        displayContent.getInputMonitor().updateInputWindowsImmediately(transaction);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:7|(1:45)(1:11)|12|(2:18|(5:37|38|39|41|42)(2:22|23))|44|(1:20)|37|38|39|41|42|5) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void waitForAnimationsToComplete() {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAnimator.mNotifyWhenNoAnimation = true;
                long j = 5000;
                boolean z2 = false;
                while (j > 0) {
                    z2 = !this.mAtmService.getTransitionController().isShellTransitionsEnabled() && this.mRoot.forAllActivities(new Transition$ChangeInfo$$ExternalSyntheticLambda0());
                    if (!this.mAnimator.isAnimationScheduled() && !this.mRoot.isAnimating(5, -1) && !z2) {
                        z = false;
                        if (z && !this.mAtmService.getTransitionController().inTransition()) {
                            break;
                        }
                        long currentTimeMillis = System.currentTimeMillis();
                        this.mGlobalLock.wait(j);
                        j -= System.currentTimeMillis() - currentTimeMillis;
                    }
                    z = true;
                    if (z) {
                    }
                    long currentTimeMillis2 = System.currentTimeMillis();
                    this.mGlobalLock.wait(j);
                    j -= System.currentTimeMillis() - currentTimeMillis2;
                }
                this.mAnimator.mNotifyWhenNoAnimation = false;
                WindowContainer animatingContainer = this.mRoot.getAnimatingContainer(5, -1);
                if (this.mAnimator.isAnimationScheduled() || animatingContainer != null || z2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Timed out waiting for animations to complete, animatingContainer=");
                    sb.append(animatingContainer);
                    sb.append(" animationType=");
                    sb.append(SurfaceAnimator.animationTypeToString(animatingContainer != null ? animatingContainer.mSurfaceAnimator.getAnimationType() : 0));
                    sb.append(" animateStarting=");
                    sb.append(z2);
                    Slog.w(StartingSurfaceController.TAG, sb.toString());
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void onAnimationFinished() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mGlobalLock.notifyAll();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void onPointerDownOutsideFocusLocked(InputTarget inputTarget) {
        onPointerDownOutsideFocusLocked(inputTarget, 0, -1, -1);
    }

    public final void onPointerDownOutsideFocusLocked(final InputTarget inputTarget, int i, int i2, int i3) {
        WindowState windowState = inputTarget != null ? inputTarget.getWindowState() : null;
        if (inputTarget == null || !inputTarget.receiveFocusFromTapOutside()) {
            return;
        }
        MultiTaskingController multiTaskingController = this.mAtmService.mMultiTaskingController;
        boolean z = i == 1 && multiTaskingController.isDeferredTaskFocusChange();
        if (multiTaskingController.shouldIgnoreTaskFocusChange(inputTarget, i, i2, i3)) {
            return;
        }
        if (z || i == 0) {
            if (z && multiTaskingController.isInImmersiveSplitScreenMode() && windowState.inSplitScreenWindowingMode() && this.mFocusedInputTarget != inputTarget) {
                RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
                if (recentsAnimationController == null || recentsAnimationController.getTargetAppMainWindow() != inputTarget) {
                    final InputTarget inputTarget2 = this.mFocusedInputTarget;
                    this.f1749mH.postDelayed(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda32
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowManagerService.this.lambda$onPointerDownOutsideFocusLocked$22(inputTarget2, inputTarget);
                        }
                    }, 100L);
                    return;
                }
                return;
            }
            RecentsAnimationController recentsAnimationController2 = this.mRecentsAnimationController;
            if (recentsAnimationController2 == null || recentsAnimationController2.getTargetAppMainWindow() != inputTarget) {
                WindowState windowState2 = inputTarget.getWindowState();
                if (windowState2 != null) {
                    Task task = windowState2.getTask();
                    if (task != null && windowState2.mTransitionController.isTransientHide(task)) {
                        return;
                    }
                    if (WmCoverState.isEnabled() && inputTarget.getWindowState().mAttrs.type == 2630 && inputTarget.getWindowState().getDisplayContent().getDisplayPolicy().mExt.mCoverPolicy != null && WmCoverState.getInstance().isViewCoverClosed()) {
                        return;
                    }
                }
                if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
                    ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -561092364, 0, (String) null, new Object[]{String.valueOf(inputTarget)});
                }
                InputTarget inputTarget3 = this.mFocusedInputTarget;
                if (inputTarget3 != inputTarget && inputTarget3 != null) {
                    inputTarget3.handleTapOutsideFocusOutsideSelf();
                }
                this.mAtmService.mTaskSupervisor.mUserLeaving = true;
                inputTarget.handleTapOutsideFocusInsideSelf();
                this.mAtmService.mTaskSupervisor.mUserLeaving = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPointerDownOutsideFocusLocked$22(InputTarget inputTarget, InputTarget inputTarget2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (inputTarget != this.mFocusedInputTarget) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (inputTarget != null && inputTarget != inputTarget2) {
                    inputTarget.handleTapOutsideFocusOutsideSelf();
                }
                inputTarget2.handleTapOutsideFocusInsideSelf();
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void handleTaskFocusChange(Task task, ActivityRecord activityRecord) {
        if (task == null) {
            return;
        }
        if (!this.mAtmService.mFreeformController.hasVisibleFreeform(task.mDisplayContent) && task.isActivityTypeHome()) {
            TaskDisplayArea displayArea = task.getDisplayArea();
            WindowState focusedWindow = getFocusedWindow();
            if (focusedWindow != null && displayArea != null && focusedWindow.isDescendantOf(displayArea)) {
                return;
            }
        }
        this.mAtmService.setFocusedTask(task.mTaskId, activityRecord);
    }

    public WindowContainerToken getTaskWindowContainerTokenForLaunchCookie(final IBinder iBinder) {
        ActivityRecord activity = this.mRoot.getActivity(new Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTaskWindowContainerTokenForLaunchCookie$23;
                lambda$getTaskWindowContainerTokenForLaunchCookie$23 = WindowManagerService.lambda$getTaskWindowContainerTokenForLaunchCookie$23(iBinder, (ActivityRecord) obj);
                return lambda$getTaskWindowContainerTokenForLaunchCookie$23;
            }
        });
        if (activity == null) {
            Slog.w(StartingSurfaceController.TAG, "Unable to find the activity for this launch cookie");
            return null;
        }
        if (activity.getTask() == null) {
            Slog.w(StartingSurfaceController.TAG, "Unable to find the task for this launch cookie");
            return null;
        }
        WindowContainerToken windowContainerToken = activity.getTask().mRemoteToken.toWindowContainerToken();
        if (windowContainerToken != null) {
            return windowContainerToken;
        }
        Slog.w(StartingSurfaceController.TAG, "Unable to find the WindowContainerToken for " + activity.getName());
        return null;
    }

    public static /* synthetic */ boolean lambda$getTaskWindowContainerTokenForLaunchCookie$23(IBinder iBinder, ActivityRecord activityRecord) {
        return activityRecord.mLaunchCookie == iBinder;
    }

    public final int sanitizeFlagSlippery(int i, String str, int i2, int i3) {
        if ((536870912 & i) == 0 || this.mContext.checkPermission("android.permission.ALLOW_SLIPPERY_TOUCHES", i3, i2) == 0) {
            return i;
        }
        Slog.w(StartingSurfaceController.TAG, "Removing FLAG_SLIPPERY from '" + str + "' because it doesn't have ALLOW_SLIPPERY_TOUCHES permission");
        return (-536870913) & i;
    }

    public final int sanitizeSpyWindow(int i, String str, int i2, int i3) {
        if ((i & 4) == 0 || this.mContext.checkPermission("android.permission.MONITOR_INPUT", i3, i2) == 0) {
            return i;
        }
        throw new IllegalArgumentException("Cannot use INPUT_FEATURE_SPY from '" + str + "' because it doesn't the have MONITOR_INPUT permission");
    }

    public void grantInputChannel(Session session, int i, int i2, int i3, SurfaceControl surfaceControl, IWindow iWindow, IBinder iBinder, int i4, int i5, int i6, int i7, IBinder iBinder2, IBinder iBinder3, String str, InputChannel inputChannel) {
        InputChannel openInputChannel;
        InputApplicationHandle applicationHandle;
        String embeddedWindow;
        int sanitizeWindowType = sanitizeWindowType(session, i3, iBinder2, i7);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                EmbeddedWindowController.EmbeddedWindow embeddedWindow2 = new EmbeddedWindowController.EmbeddedWindow(session, this, iWindow, (WindowState) this.mInputToWindowMap.get(iBinder), i, i2, sanitizeWindowType, i3, iBinder3, str, (i4 & 8) == 0);
                openInputChannel = embeddedWindow2.openInputChannel();
                this.mEmbeddedWindowController.add(openInputChannel.getToken(), embeddedWindow2);
                applicationHandle = embeddedWindow2.getApplicationHandle();
                embeddedWindow = embeddedWindow2.toString();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        updateInputChannel(openInputChannel.getToken(), i, i2, i3, surfaceControl, embeddedWindow, applicationHandle, i4, i5, i6, sanitizeWindowType, null, iWindow);
        openInputChannel.copyTo(inputChannel);
    }

    public void grantInputChannelWithTaskToken(Session session, int i, int i2, int i3, SurfaceControl surfaceControl, IWindow iWindow, IBinder iBinder, int i4, int i5, int i6, int i7, IBinder iBinder2, IBinder iBinder3, String str, InputChannel inputChannel, int i8, WindowContainerToken windowContainerToken) {
        int sanitizeWindowType = sanitizeWindowType(session, i3, iBinder2, i7);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                try {
                    EmbeddedWindowController.EmbeddedWindow embeddedWindow = new EmbeddedWindowController.EmbeddedWindow(session, this, iWindow, (WindowState) this.mInputToWindowMap.get(iBinder), i, i2, sanitizeWindowType, i3, iBinder3, str, (i4 & 8) == 0, surfaceControl, windowContainerToken);
                    InputChannel openInputChannel = embeddedWindow.openInputChannel();
                    this.mEmbeddedWindowController.add(openInputChannel.getToken(), embeddedWindow);
                    InputApplicationHandle applicationHandle = embeddedWindow.getApplicationHandle();
                    String embeddedWindow2 = embeddedWindow.toString();
                    resetPriorityAfterLockedSection();
                    updateInputChannel(openInputChannel.getToken(), i, i2, i3, surfaceControl, embeddedWindow2, applicationHandle, i4, i5, i6, sanitizeWindowType, null, iWindow, i8, windowContainerToken);
                    openInputChannel.copyTo(inputChannel);
                } catch (Throwable th) {
                    th = th;
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void removeWithTaskToken(Session session, IWindow iWindow, WindowContainerToken windowContainerToken) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mEmbeddedWindowController.remove(iWindow);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public ArrayList getExcludeLayers(WindowContainer windowContainer) {
        Task asTask = windowContainer.asTask();
        if (asTask == null) {
            return new ArrayList();
        }
        return this.mEmbeddedWindowController.getExcludeLayersByTaskToken(asTask.mRemoteToken.toWindowContainerToken());
    }

    public boolean transferEmbeddedTouchFocusToHost(IWindow iWindow) {
        IBinder asBinder = iWindow.asBinder();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                EmbeddedWindowController.EmbeddedWindow byWindowToken = this.mEmbeddedWindowController.getByWindowToken(asBinder);
                if (byWindowToken == null) {
                    Slog.w(StartingSurfaceController.TAG, "Attempt to transfer touch focus from non-existent embedded window");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                WindowState windowState = byWindowToken.getWindowState();
                if (windowState == null) {
                    Slog.w(StartingSurfaceController.TAG, "Attempt to transfer touch focus from embedded window with no associated host");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                IBinder inputChannelToken = byWindowToken.getInputChannelToken();
                if (inputChannelToken == null) {
                    Slog.w(StartingSurfaceController.TAG, "Attempt to transfer touch focus from embedded window with no input channel");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                IBinder iBinder = windowState.mInputChannelToken;
                if (iBinder == null) {
                    Slog.w(StartingSurfaceController.TAG, "Attempt to transfer touch focus to a host window with no input channel");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean transferTouchFocus = this.mInputManager.transferTouchFocus(inputChannelToken, iBinder);
                resetPriorityAfterLockedSection();
                return transferTouchFocus;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void updateInputChannel(IBinder iBinder, int i, int i2, int i3, SurfaceControl surfaceControl, String str, InputApplicationHandle inputApplicationHandle, int i4, int i5, int i6, int i7, Region region, IWindow iWindow) {
        updateInputChannel(iBinder, i, i2, i3, surfaceControl, str, inputApplicationHandle, i4, i5, i6, i7, region, iWindow, 0, null);
    }

    public final void updateInputChannel(IBinder iBinder, int i, int i2, int i3, SurfaceControl surfaceControl, String str, InputApplicationHandle inputApplicationHandle, int i4, int i5, int i6, int i7, Region region, IWindow iWindow, int i8, WindowContainerToken windowContainerToken) {
        updateInputChannel(iBinder, i, i2, i3, surfaceControl, str, inputApplicationHandle, i4, i5, i6, i7, region, iWindow, i8, windowContainerToken, null);
    }

    public final void updateInputChannel(IBinder iBinder, int i, int i2, int i3, SurfaceControl surfaceControl, String str, InputApplicationHandle inputApplicationHandle, int i4, int i5, int i6, int i7, Region region, IWindow iWindow, int i8, WindowContainerToken windowContainerToken, Region region2) {
        InputWindowHandle inputWindowHandle = new InputWindowHandle(inputApplicationHandle, i3);
        inputWindowHandle.token = iBinder;
        inputWindowHandle.setWindowToken(iWindow);
        inputWindowHandle.name = str;
        int sanitizeFlagSlippery = sanitizeFlagSlippery(i4, str, i, i2);
        int sanitizeSpyWindow = sanitizeSpyWindow(i6, str, i, i2);
        int i9 = (536870936 & sanitizeFlagSlippery) | 32;
        inputWindowHandle.layoutParamsType = i7;
        inputWindowHandle.layoutParamsFlags = i9;
        int inputConfigFromWindowParams = InputConfigAdapter.getInputConfigFromWindowParams(i7, i9, sanitizeSpyWindow);
        inputWindowHandle.inputConfig = inputConfigFromWindowParams;
        if ((sanitizeFlagSlippery & 8) != 0) {
            inputWindowHandle.inputConfig = inputConfigFromWindowParams | 4;
        }
        if ((i5 & 536870912) != 0) {
            inputWindowHandle.inputConfig |= 256;
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            if ((262144 & sanitizeFlagSlippery) != 0) {
                inputWindowHandle.inputConfig |= 512;
            }
            inputWindowHandle.surfaceInset = i8;
        }
        if (region2 == null) {
            inputWindowHandle.pointerTouchableRegion.setEmpty();
        } else {
            inputWindowHandle.pointerTouchableRegion.set(region2);
        }
        inputWindowHandle.dispatchingTimeoutMillis = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        inputWindowHandle.ownerUid = i;
        inputWindowHandle.ownerPid = i2;
        if (region == null) {
            inputWindowHandle.replaceTouchableRegionWithCrop((SurfaceControl) null);
        } else {
            inputWindowHandle.touchableRegion.set(region);
            inputWindowHandle.replaceTouchableRegionWithCrop = false;
            if (this.mContext.checkPermission("android.permission.MANAGE_ACTIVITY_TASKS", i2, i) != 0) {
                inputWindowHandle.setTouchableRegionCrop(surfaceControl);
            }
        }
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionFactory.get();
        transaction.setInputWindowInfo(surfaceControl, inputWindowHandle);
        transaction.apply();
        transaction.close();
        if (!CoreRune.MW_CAPTION_SHELL || windowContainerToken == null) {
            surfaceControl.release();
        }
    }

    public void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region) {
        updateInputChannel(iBinder, i, surfaceControl, i2, i3, i4, region, null);
    }

    public void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                EmbeddedWindowController.EmbeddedWindow embeddedWindow = this.mEmbeddedWindowController.get(iBinder);
                if (embeddedWindow == null) {
                    Slog.e(StartingSurfaceController.TAG, "Couldn't find window for provided channelToken.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                String embeddedWindow2 = embeddedWindow.toString();
                InputApplicationHandle applicationHandle = embeddedWindow.getApplicationHandle();
                embeddedWindow.setIsFocusable((i2 & 8) == 0);
                resetPriorityAfterLockedSection();
                updateInputChannel(iBinder, embeddedWindow.mOwnerUid, embeddedWindow.mOwnerPid, i, surfaceControl, embeddedWindow2, applicationHandle, i2, i3, i4, embeddedWindow.mWindowType, region, embeddedWindow.mClient, 0, null, region2);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0054 A[Catch: all -> 0x0058, TRY_ENTER, TryCatch #2 {all -> 0x0058, blocks: (B:11:0x002e, B:19:0x0045, B:21:0x004a, B:27:0x0054, B:29:0x005c, B:30:0x005f), top: B:4:0x0010 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005c A[Catch: all -> 0x0058, TryCatch #2 {all -> 0x0058, blocks: (B:11:0x002e, B:19:0x0045, B:21:0x004a, B:27:0x0054, B:29:0x005c, B:30:0x005f), top: B:4:0x0010 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isLayerTracing() {
        Parcel parcel;
        if (!checkCallingPermission("android.permission.DUMP", "isLayerTracing()")) {
            throw new SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Parcel parcel2 = null;
        try {
            try {
                try {
                    IBinder service = ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        parcel = Parcel.obtain();
                        try {
                            parcel2 = Parcel.obtain();
                            parcel2.writeInterfaceToken("android.ui.ISurfaceComposer");
                            service.transact(1026, parcel2, parcel, 0);
                            boolean readBoolean = parcel.readBoolean();
                            parcel2.recycle();
                            parcel.recycle();
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return readBoolean;
                        } catch (RemoteException unused) {
                            Slog.e(StartingSurfaceController.TAG, "Failed to get layer tracing");
                            if (parcel2 != null) {
                                parcel2.recycle();
                            }
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return false;
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (0 != 0) {
                        parcel2.recycle();
                    }
                    if (0 != 0) {
                        parcel2.recycle();
                    }
                    throw th;
                }
            } catch (RemoteException unused2) {
                parcel = null;
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                }
                if (0 != 0) {
                }
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th3) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0046, code lost:
    
        if (r5 == null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004c, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0037, code lost:
    
        r5.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0035, code lost:
    
        if (r5 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setLayerTracing(boolean z) {
        Parcel parcel;
        Throwable th;
        if (!checkCallingPermission("android.permission.DUMP", "setLayerTracing()")) {
            throw new SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Parcel parcel2 = null;
        try {
            try {
                try {
                    IBinder service = ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        parcel = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.ui.ISurfaceComposer");
                            parcel.writeInt(z ? 1 : 0);
                            service.transact(1025, parcel, null, 0);
                            parcel2 = parcel;
                        } catch (RemoteException unused) {
                            parcel2 = parcel;
                            Slog.e(StartingSurfaceController.TAG, "Failed to set layer tracing");
                        } catch (Throwable th2) {
                            th = th2;
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException unused2) {
            }
        } catch (Throwable th3) {
            parcel = parcel2;
            th = th3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        if (r5 == null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0047, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0032, code lost:
    
        r5.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0030, code lost:
    
        if (r5 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setLayerTracingFlags(int i) {
        Parcel parcel;
        Throwable th;
        if (!checkCallingPermission("android.permission.DUMP", "setLayerTracingFlags")) {
            throw new SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Parcel parcel2 = null;
        try {
            try {
                try {
                    IBinder service = ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        parcel = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.ui.ISurfaceComposer");
                            parcel.writeInt(i);
                            service.transact(1033, parcel, null, 0);
                            parcel2 = parcel;
                        } catch (RemoteException unused) {
                            parcel2 = parcel;
                            Slog.e(StartingSurfaceController.TAG, "Failed to set layer tracing flags");
                        } catch (Throwable th2) {
                            th = th2;
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th3) {
                    parcel = null;
                    th = th3;
                }
            } catch (RemoteException unused2) {
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0046, code lost:
    
        if (r5 == null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004c, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0037, code lost:
    
        r5.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0035, code lost:
    
        if (r5 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setActiveTransactionTracing(boolean z) {
        Parcel parcel;
        Throwable th;
        if (!checkCallingPermission("android.permission.DUMP", "setActiveTransactionTracing()")) {
            throw new SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Parcel parcel2 = null;
        try {
            try {
                try {
                    IBinder service = ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        parcel = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.ui.ISurfaceComposer");
                            parcel.writeInt(z ? 1 : 0);
                            service.transact(1041, parcel, null, 0);
                            parcel2 = parcel;
                        } catch (RemoteException unused) {
                            parcel2 = parcel;
                            Slog.e(StartingSurfaceController.TAG, "Failed to set transaction tracing");
                        } catch (Throwable th2) {
                            th = th2;
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException unused2) {
            }
        } catch (Throwable th3) {
            parcel = parcel2;
            th = th3;
        }
    }

    public boolean mirrorDisplay(int i, SurfaceControl surfaceControl) {
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "mirrorDisplay()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e(StartingSurfaceController.TAG, "Invalid displayId " + i + " for mirrorDisplay");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                SurfaceControl windowingLayer = displayContent.getWindowingLayer();
                resetPriorityAfterLockedSection();
                SurfaceControl mirrorSurface = SurfaceControl.mirrorSurface(windowingLayer);
                surfaceControl.copyFrom(mirrorSurface, "WMS.mirrorDisplay");
                mirrorSurface.release();
                return true;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean getWindowInsets(int i, IBinder iBinder, InsetsState insetsState) {
        boolean areSystemBarsForcedConsumedLw;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, iBinder);
                    if (displayContentOrCreate == null) {
                        throw new WindowManager.InvalidDisplayException("Display#" + i + "could not be found!");
                    }
                    displayContentOrCreate.getInsetsPolicy().getInsetsForWindowMetrics(displayContentOrCreate.getWindowToken(iBinder), insetsState);
                    areSystemBarsForcedConsumedLw = displayContentOrCreate.getDisplayPolicy().areSystemBarsForcedConsumedLw();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return areSystemBarsForcedConsumedLw;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Finally extract failed */
    public List getPossibleDisplayInfo(int i) {
        List possibleDisplayInfos;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!this.mAtmService.isCallerRecents(callingUid)) {
                        Slog.e(StartingSurfaceController.TAG, "Unable to verify uid for getPossibleDisplayInfo on uid " + callingUid);
                        possibleDisplayInfos = new ArrayList();
                    } else {
                        possibleDisplayInfos = this.mPossibleDisplayInfoMapper.getPossibleDisplayInfos(i);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return possibleDisplayInfos;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getPossibleDisplayInfoLocked(int i) {
        return this.mPossibleDisplayInfoMapper.getPossibleDisplayInfos(i);
    }

    public void grantEmbeddedWindowFocus(Session session, IBinder iBinder, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                EmbeddedWindowController.EmbeddedWindow byFocusToken = this.mEmbeddedWindowController.getByFocusToken(iBinder);
                if (byFocusToken == null) {
                    Slog.e(StartingSurfaceController.TAG, "Embedded window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (byFocusToken.mSession != session) {
                    Slog.e(StartingSurfaceController.TAG, "Window not in session:" + session);
                    resetPriorityAfterLockedSection();
                    return;
                }
                IBinder inputChannelToken = byFocusToken.getInputChannelToken();
                if (inputChannelToken == null) {
                    Slog.e(StartingSurfaceController.TAG, "Focus token found but input channel token not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionFactory.get();
                int i = byFocusToken.mDisplayId;
                byFocusToken.onGrantFocusChanged(z);
                if (z) {
                    transaction.setFocusedWindow(inputChannelToken, byFocusToken.toString(), i).apply();
                    if (byFocusToken.mWindowType == 3) {
                        Slog.d(StartingSurfaceController.TAG, "grantEmbeddedWindowFocus: w=" + byFocusToken + ", mIsFocusable=" + byFocusToken.receiveFocusFromTapOutside() + ", caller=" + Debug.getCallers(6));
                    }
                    EventLog.writeEvent(62001, "Focus request " + byFocusToken, "reason=grantEmbeddedWindowFocus(true)");
                } else {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    WindowState findFocusedWindow = displayContent == null ? null : displayContent.findFocusedWindow();
                    if (findFocusedWindow == null) {
                        transaction.setFocusedWindow(null, null, i).apply();
                        if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS, 958338552, 0, (String) null, new Object[]{String.valueOf(byFocusToken)});
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    transaction.setFocusedWindow(findFocusedWindow.mInputChannelToken, findFocusedWindow.getName(), i).apply();
                    EventLog.writeEvent(62001, "Focus request " + findFocusedWindow, "reason=grantEmbeddedWindowFocus(false)");
                }
                if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS, -2107721178, 0, (String) null, new Object[]{String.valueOf(byFocusToken), String.valueOf(z)});
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void grantEmbeddedWindowFocus(Session session, IWindow iWindow, IBinder iBinder, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    Slog.e(StartingSurfaceController.TAG, "Host window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowForClientLocked.mInputChannel == null) {
                    Slog.e(StartingSurfaceController.TAG, "Host window does not have an input channel");
                    resetPriorityAfterLockedSection();
                    return;
                }
                EmbeddedWindowController.EmbeddedWindow byFocusToken = this.mEmbeddedWindowController.getByFocusToken(iBinder);
                if (byFocusToken == null) {
                    Slog.e(StartingSurfaceController.TAG, "Embedded window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (byFocusToken.mHostWindowState != windowForClientLocked) {
                    Slog.e(StartingSurfaceController.TAG, "Embedded window does not belong to the host");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (z) {
                    windowForClientLocked.mInputWindowHandle.setFocusTransferTarget(byFocusToken.getInputChannelToken());
                    EventLog.writeEvent(62001, "Transfer focus request " + byFocusToken, "reason=grantEmbeddedWindowFocus(true)");
                } else {
                    windowForClientLocked.mInputWindowHandle.setFocusTransferTarget(null);
                    EventLog.writeEvent(62001, "Transfer focus request " + windowForClientLocked, "reason=grantEmbeddedWindowFocus(false)");
                }
                DisplayContent displayContent = this.mRoot.getDisplayContent(windowForClientLocked.getDisplayId());
                if (displayContent != null) {
                    displayContent.getInputMonitor().updateInputWindowsLw(true);
                }
                if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS, -2107721178, 0, (String) null, new Object[]{String.valueOf(byFocusToken), String.valueOf(z)});
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void holdLock(IBinder iBinder, int i) {
        this.mTestUtilityService.verifyHoldLockToken(iBinder);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                SystemClock.sleep(i);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public String[] getSupportedDisplayHashAlgorithms() {
        return this.mDisplayHashController.getSupportedHashAlgorithms();
    }

    public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) {
        return this.mDisplayHashController.verifyDisplayHash(displayHash);
    }

    public void setDisplayHashThrottlingEnabled(boolean z) {
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "setDisplayHashThrottle()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        this.mDisplayHashController.setDisplayHashThrottlingEnabled(z);
    }

    public boolean isTaskSnapshotSupported() {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = !this.mTaskSnapshotController.shouldDisableSnapshots();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return z;
    }

    public void generateDisplayHash(Session session, IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) {
        Rect rect2 = new Rect(rect);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    Slog.w(StartingSurfaceController.TAG, "Failed to generate DisplayHash. Invalid window");
                    this.mDisplayHashController.sendDisplayHashError(remoteCallback, -3);
                    resetPriorityAfterLockedSection();
                    return;
                }
                ActivityRecord activityRecord = windowForClientLocked.mActivityRecord;
                if (activityRecord != null && activityRecord.isState(ActivityRecord.State.RESUMED)) {
                    DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                    if (displayContent == null) {
                        Slog.w(StartingSurfaceController.TAG, "Failed to generate DisplayHash. Window is not on a display");
                        this.mDisplayHashController.sendDisplayHashError(remoteCallback, -4);
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    SurfaceControl surfaceControl = displayContent.getSurfaceControl();
                    this.mDisplayHashController.calculateDisplayHashBoundsLocked(windowForClientLocked, rect, rect2);
                    if (rect2.isEmpty()) {
                        Slog.w(StartingSurfaceController.TAG, "Failed to generate DisplayHash. Bounds are not on screen");
                        this.mDisplayHashController.sendDisplayHashError(remoteCallback, -4);
                        resetPriorityAfterLockedSection();
                        return;
                    } else {
                        resetPriorityAfterLockedSection();
                        int i = session.mUid;
                        this.mDisplayHashController.generateDisplayHash(new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setUid(i).setSourceCrop(rect2), rect, str, i, remoteCallback);
                        return;
                    }
                }
                this.mDisplayHashController.sendDisplayHashError(remoteCallback, -3);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean shouldRestoreImeVisibility(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                Task task = windowState.getTask();
                if (task == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                ActivityRecord activityRecord = windowState.mActivityRecord;
                if (activityRecord != null && activityRecord.mLastImeShown) {
                    resetPriorityAfterLockedSection();
                    return true;
                }
                resetPriorityAfterLockedSection();
                TaskSnapshot taskSnapshot = getTaskSnapshot(task.mTaskId, task.mUserId, false, false);
                return taskSnapshot != null && taskSnapshot.hasImeSurface();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getImeDisplayId() {
        int displayId;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent topFocusedDisplayContent = this.mRoot.getTopFocusedDisplayContent();
                displayId = topFocusedDisplayContent.getImePolicy() == 0 ? topFocusedDisplayContent.getDisplayId() : 0;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return displayId;
    }

    public void setTaskSnapshotEnabled(boolean z) {
        this.mTaskSnapshotController.setSnapshotEnabled(z);
    }

    public void setTaskTransitionSpec(TaskTransitionSpec taskTransitionSpec) {
        if (!checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "setTaskTransitionSpec()")) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        this.mTaskTransitionSpec = taskTransitionSpec;
    }

    public void clearTaskTransitionSpec() {
        if (!checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "clearTaskTransitionSpec()")) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        this.mTaskTransitionSpec = null;
    }

    public void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FPS_COUNTER") != 0) {
            throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission android.permission.ACCESS_FPS_COUNTER");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mRoot.anyTaskForId(i) == null) {
                    throw new IllegalArgumentException("no task with taskId: " + i);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        this.mTaskFpsCallbackController.registerListener(i, iTaskFpsCallback);
    }

    public void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FPS_COUNTER") != 0) {
            throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission android.permission.ACCESS_FPS_COUNTER");
        }
        this.mTaskFpsCallbackController.lambda$registerListener$0(iTaskFpsCallback);
    }

    public Bitmap snapshotTaskForRecents(int i) {
        TaskSnapshot captureSnapshot;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "snapshotTaskForRecents()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRoot.anyTaskForId(i, 1);
                    if (anyTaskForId == null) {
                        throw new IllegalArgumentException("Failed to find matching task for taskId=" + i);
                    }
                    captureSnapshot = this.mTaskSnapshotController.captureSnapshot(anyTaskForId, false);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            if (captureSnapshot == null || captureSnapshot.getHardwareBuffer() == null) {
                return null;
            }
            return Bitmap.wrapHardwareBuffer(captureSnapshot.getHardwareBuffer(), captureSnapshot.getColorSpace());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRecentsAppBehindSystemBars(boolean z) {
        if (!checkCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "setRecentsAppBehindSystemBars()")) {
            throw new SecurityException("Requires START_TASKS_FROM_RECENTS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task task = this.mRoot.getTask(new Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$setRecentsAppBehindSystemBars$24;
                            lambda$setRecentsAppBehindSystemBars$24 = WindowManagerService.lambda$setRecentsAppBehindSystemBars$24((Task) obj);
                            return lambda$setRecentsAppBehindSystemBars$24;
                        }
                    });
                    if (task != null) {
                        task.getTask().setCanAffectSystemUiFlags(z);
                        this.mWindowPlacerLocked.requestTraversal();
                    }
                    InputMethodManagerInternal.get().maybeFinishStylusHandwriting();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ boolean lambda$setRecentsAppBehindSystemBars$24(Task task) {
        return task.isActivityTypeHomeOrRecents() && task.getTopVisibleActivity() != null;
    }

    public List getVisibleWindowInfoList() {
        if (!checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "getVisibleWindowInfoList()", false) && !checkCallingPermission("android.permission.RETRIEVE_WINDOW_CONTENT", "getVisibleWindowInfoList()", false)) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS or RETRIEVE_WINDOW_CONTENT permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mExt.getVisibleWindowInfoList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getUserDisplaySize(Point point) {
        if (point == null) {
            return;
        }
        this.mExt.mMultiResolutionController.getForcedDisplaySize(point);
    }

    public int getUserDisplayDensity() {
        return this.mExt.mMultiResolutionController.getForcedDisplayDensity();
    }

    public void clearForcedDisplaySizeDensity(int i) {
        this.mExt.mMultiResolutionController.clearForcedDisplaySizeDensity(i);
    }

    public void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5) {
        setForcedDisplaySizeDensityWithInfo(new MultiResolutionChangeRequestInfo.Builder(0).setWidth(i2).setHeight(i3).setDensity(i4).setSaveToSettings(z).setForcedHideCutout(i5).build());
    }

    public void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) {
        this.mExt.mMultiResolutionController.setForcedDisplaySizeDensity(multiResolutionChangeRequestInfo);
    }

    public ScreenshotResult takeScreenshotToTargetWindow(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) {
        return takeScreenshotToTargetWindowFromCapture(i, i2, z, rect, i3, i4, z2, z3, false);
    }

    public ScreenshotResult takeScreenshotToTargetWindowFromCapture(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) {
        if (z3) {
            if (!checkCallingPermission("com.samsung.android.permission.READ_FRAME_BUFFER_IGNORE_POLICY", "takeScreenshotToTargetWindow()")) {
                throw new SecurityException("Only certain apps can request to ignorePolicy");
            }
        } else if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "takeScreenshotToTargetWindow()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        return this.mExt.mScreenshotController.takeScreenshotToTargetWindow(i, i2, z, rect, i3, i4, z2, z3, z4);
    }

    public int getLetterboxBackgroundColorInArgb() {
        return this.mLetterboxConfiguration.getLetterboxBackgroundColor().toArgb();
    }

    public boolean isLetterboxBackgroundMultiColored() {
        int letterboxBackgroundType = this.mLetterboxConfiguration.getLetterboxBackgroundType();
        if (letterboxBackgroundType == 0) {
            return false;
        }
        if (letterboxBackgroundType == 1 || letterboxBackgroundType == 2 || letterboxBackgroundType == 3) {
            return true;
        }
        throw new AssertionError("Unexpected letterbox background type: " + letterboxBackgroundType);
    }

    public void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        Slog.d(StartingSurfaceController.TAG, "captureDisplay");
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "captureDisplay()")) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        ScreenCapture.LayerCaptureArgs captureArgs2 = getCaptureArgs(i, captureArgs);
        ScreenCapture.captureLayers(captureArgs2, screenCaptureListener);
        if (Binder.getCallingUid() != 1000) {
            captureArgs2.release();
        }
    }

    public ScreenCapture.LayerCaptureArgs getCaptureArgs(int i, ScreenCapture.CaptureArgs captureArgs) {
        SurfaceControl surfaceControl;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to screenshot and invalid display: " + i);
                }
                surfaceControl = displayContent.getSurfaceControl();
                if (captureArgs == null) {
                    captureArgs = new ScreenCapture.CaptureArgs.Builder().build();
                }
                if (captureArgs.mSourceCrop.isEmpty()) {
                    displayContent.getBounds(this.mTmpRect);
                    this.mTmpRect.offsetTo(0, 0);
                } else {
                    this.mTmpRect.set(captureArgs.mSourceCrop);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl, captureArgs).setSourceCrop(this.mTmpRect).build();
    }

    public boolean isGlobalKey(int i) {
        return this.mPolicy.isGlobalKey(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0019, code lost:
    
        if (r7 != r3.getWindowType()) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int sanitizeWindowType(Session session, int i, IBinder iBinder, int i2) {
        boolean z = true;
        if (i2 == 2032 && iBinder != null) {
            WindowToken windowToken = this.mRoot.getDisplayContent(i).getWindowToken(iBinder);
            if (windowToken != null) {
            }
            z = false;
        } else if (!session.mCanAddInternalSystemWindow && i2 != 0) {
            Slog.w(StartingSurfaceController.TAG, "Requires INTERNAL_SYSTEM_WINDOW permission if assign type to input. New type will be 0.");
            z = false;
        }
        if (z) {
            return i2;
        }
        return 0;
    }

    public boolean addToSurfaceSyncGroup(IBinder iBinder, boolean z, ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) {
        return this.mSurfaceSyncGroupController.addToSyncGroup(iBinder, z, iSurfaceSyncGroupCompletedListener, addToSurfaceSyncGroupResult);
    }

    public void markSurfaceSyncGroupReady(IBinder iBinder) {
        this.mSurfaceSyncGroupController.markSyncGroupReady(iBinder);
    }

    public List notifyScreenshotListeners(int i) {
        if (!checkCallingPermission("android.permission.STATUS_BAR_SERVICE", "notifyScreenshotListeners()")) {
            throw new SecurityException("Requires STATUS_BAR_SERVICE permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    ArrayList arrayList = new ArrayList();
                    resetPriorityAfterLockedSection();
                    return arrayList;
                }
                final ArraySet arraySet = new ArraySet();
                displayContent.forAllActivities(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        WindowManagerService.lambda$notifyScreenshotListeners$25(arraySet, (ActivityRecord) obj);
                    }
                }, true);
                List copyOf = List.copyOf(arraySet);
                resetPriorityAfterLockedSection();
                return copyOf;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public static /* synthetic */ void lambda$notifyScreenshotListeners$25(ArraySet arraySet, ActivityRecord activityRecord) {
        if (!arraySet.contains(activityRecord.mActivityComponent) && activityRecord.isVisible() && activityRecord.isRegisteredForScreenCaptureCallback()) {
            activityRecord.reportScreenCaptured();
            arraySet.add(activityRecord.mActivityComponent);
        }
    }

    public int getTopFocusedDisplayId() {
        int topFocusedDisplayId;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                topFocusedDisplayId = this.mRoot.getTopFocusedDisplayId();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return topFocusedDisplayId;
    }

    public void moveDisplayToTop(int i, String str) {
        if (!checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "moveDisplayToTop")) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        Slog.d(StartingSurfaceController.TAG, "moveDisplayToTop caller pid = " + Binder.getCallingPid() + " displayId = " + i + " reason = " + str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.moveDisplayToTop(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startSurfaceAnimation(IBinder iBinder, String str) {
        Slog.e(StartingSurfaceController.TAG, "[SEC_SF_EFFECTS] WindowManagerService.startSurfaceAnimation()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    Slog.e(StartingSurfaceController.TAG, "startSurfaceAnimation, WindowState not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    Slog.e(StartingSurfaceController.TAG, "startSurfaceAnimation, surface isn't created");
                    resetPriorityAfterLockedSection();
                } else {
                    windowSurfaceController.startSurfaceAnimation(str);
                    resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean requestSystemKeyEvent(int i, ComponentName componentName, boolean z) {
        if (componentName == null) {
            return false;
        }
        return this.mExt.mPolicyExt.requestSystemKeyEvent(i, componentName, z);
    }

    public boolean isSystemKeyEventRequested(int i, ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return this.mExt.mPolicyExt.isSystemKeyEventRequested(i, componentName);
    }

    public void registerSystemKeyEvent(int i, ComponentName componentName, int i2) {
        if (!checkCallingPermission("com.samsung.android.permission.ACCESS_SYSTEM_KEY_DISPATCHING", "registerSystemKeyEvent")) {
            throw new SecurityException("Requires ACCESS_SYSTEM_KEY_DISPATCHING permission");
        }
        this.mExt.mPolicyExt.registerSystemKeyEvent(i, componentName, i2);
    }

    public void unregisterSystemKeyEvent(int i, ComponentName componentName) {
        if (!checkCallingPermission("com.samsung.android.permission.ACCESS_SYSTEM_KEY_DISPATCHING", "unregisterSystemKeyEvent")) {
            throw new SecurityException("Requires ACCESS_SYSTEM_KEY_DISPATCHING permission");
        }
        this.mExt.mPolicyExt.unregisterSystemKeyEvent(i, componentName);
    }

    public void requestMetaKeyEvent(ComponentName componentName, boolean z) {
        if (componentName == null) {
            return;
        }
        this.mExt.mPolicyExt.requestMetaKeyEvent(componentName, z);
    }

    public boolean isMetaKeyEventRequested(ComponentName componentName) {
        return this.mExt.mPolicyExt.isMetaKeyEventRequested(componentName);
    }

    public final boolean verifyPrivilegedApp(String str) {
        if (str == null) {
            Log.e(StartingSurfaceController.TAG, "verifyPrivilegedApp: packageName is null");
            return false;
        }
        try {
            return this.mContext.getPackageManager().getApplicationInfo(str, 0).isPrivilegedApp();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(StartingSurfaceController.TAG, "Package name: " + str + " cannot be found." + e);
            return false;
        }
    }

    public final boolean verifyPlatformApp(String str) {
        if (str == null) {
            Log.e(StartingSurfaceController.TAG, "verifyPlatformApp: packageName is null");
            return false;
        }
        if (this.mContext.getPackageManager().checkSignatures("android", str) == 0) {
            return true;
        }
        return verifyPrivilegedApp(str);
    }

    public boolean isAllowNonPlatformKeyApp(String str) {
        MessageDigest messageDigest;
        SigningInfo signingInfo;
        Signature[] signingCertificateHistory;
        if (str != null && this.PENDING_INTENT_AFTER_UNLOCK_ALLOW_MAP.containsKey(str)) {
            ArrayList arrayList = new ArrayList();
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
                signingInfo = packageManager.getPackageInfo(str, 134217728).signingInfo;
            } catch (Exception unused) {
                Log.e(StartingSurfaceController.TAG, "isAllowNonPlatformKeyApp : ${e.message}");
            }
            if (signingInfo == null) {
                Log.e(StartingSurfaceController.TAG, "isAllowNonPlatformKeyApp: signingInfo is null");
                return false;
            }
            if (signingInfo.hasMultipleSigners()) {
                signingCertificateHistory = signingInfo.getApkContentsSigners();
            } else {
                signingCertificateHistory = signingInfo.getSigningCertificateHistory();
            }
            for (Signature signature : signingCertificateHistory) {
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest.digest(signature.toCharsString().getBytes())) {
                    int i = (b >> 4) & 15;
                    sb.append((char) (i >= 10 ? (i + 97) - 10 : i + 48));
                    int i2 = b & 15;
                    sb.append((char) (i2 >= 10 ? (i2 + 97) - 10 : i2 + 48));
                }
                arrayList.add(sb.toString());
            }
            ArrayList arrayList2 = (ArrayList) this.PENDING_INTENT_AFTER_UNLOCK_ALLOW_MAP.get(str);
            if (arrayList2 != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    if (arrayList.contains((String) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setPendingIntentAfterUnlock(final PendingIntent pendingIntent, final Intent intent) {
        if (!checkCallingPermission("android.permission.CONTROL_KEYGUARD", "setPendingIntentAfterUnlock")) {
            int callingPid = Binder.getCallingPid();
            String packageNameByPid = this.mAmInternal.getPackageNameByPid(callingPid);
            if (verifyPlatformApp(packageNameByPid) || isAllowNonPlatformKeyApp(packageNameByPid)) {
                Log.d(StartingSurfaceController.TAG, "setPendingIntentAfterUnlock: allowed pid : " + callingPid);
            } else {
                throw new SecurityException("Requires CONTROL_KEYGUARD permission");
            }
        }
        if (this.mRoot.mTransitionController.inTransition()) {
            this.mRoot.mTransitionController.setCleanupRunnable(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerService.this.lambda$setPendingIntentAfterUnlock$26(pendingIntent, intent);
                }
            });
        } else {
            this.mExt.setPendingIntentAfterUnlock(pendingIntent, intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPendingIntentAfterUnlock$26(PendingIntent pendingIntent, Intent intent) {
        this.mExt.setPendingIntentAfterUnlock(pendingIntent, intent);
    }

    public void setTspDeadzone(Session session, IWindow iWindow, Bundle bundle) {
        this.mExt.setTspDeadzone(session, iWindow, bundle);
    }

    public void clearTspDeadzone(Session session, IWindow iWindow) {
        this.mExt.clearTspDeadzone(session, iWindow);
    }

    public void setTspNoteMode(Session session, IWindow iWindow, boolean z) {
        this.mExt.setTspNoteMode(session, iWindow, z);
    }

    public void setDeadzoneHole(Bundle bundle) {
        this.mExt.setDeadzoneHole(bundle);
    }

    public int getMaxAspectRatioPolicyByComponent(ComponentName componentName, int i) {
        int maxAspectRatioPolicy;
        ActivityTaskManagerService.enforceTaskPermission("getMaxAspectRatioPolicyByComponent()");
        ActivityInfo activityInfo = this.mPmInternal.getActivityInfo(componentName, 128L, Binder.getCallingUid(), UserHandle.getUserId(i));
        if (activityInfo == null) {
            return getMaxAspectRatioPolicy(componentName.getPackageName(), i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    maxAspectRatioPolicy = this.mAtmService.mExt.mCustomAspectRatioController.getMaxAspectRatioPolicy(activityInfo.applicationInfo, activityInfo);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return maxAspectRatioPolicy;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getMaxAspectRatioPolicy(String str, int i) {
        int maxAspectRatioPolicy;
        ActivityTaskManagerService.enforceTaskPermission("getMaxAspectRatioPolicy()");
        ApplicationInfo applicationInfo = this.mPmInternal.getApplicationInfo(str, 128L, Binder.getCallingUid(), UserHandle.getUserId(i));
        if (applicationInfo == null) {
            return 3;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    maxAspectRatioPolicy = this.mAtmService.mExt.mCustomAspectRatioController.getMaxAspectRatioPolicy(applicationInfo, null);
                } finally {
                }
            }
            resetPriorityAfterLockedSection();
            return maxAspectRatioPolicy;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) {
        ActivityTaskManagerService.enforceTaskPermission("setMaxAspectRatioPolicy()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAtmService.mExt.mCustomAspectRatioController.setMaxAspectRatioPolicy(str, i, z, i2, true);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getFullScreenAppsSupportMode() {
        return FullScreenAppsSupportUtils.get().getFullScreenAppsSupportMode();
    }

    public void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "putKeyCustomizationInfo")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        if (keyCustomizationInfo == null) {
            throw new IllegalArgumentException("KeyCustomizationInfo is empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.putKeyCustomizationInfo(keyCustomizationInfo);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) {
        return this.mExt.mPolicyExt.getKeyCustomizationInfo(i, i2, i3);
    }

    public SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) {
        return this.mExt.mPolicyExt.getKeyCustomizationInfoByPackage(str, i, i2);
    }

    public SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) {
        return this.mExt.mPolicyExt.getLastKeyCustomizationInfo(i, i2);
    }

    public void removeKeyCustomizationInfo(int i, int i2, int i3) {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "removeKeyCustomizationInfo")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.removeKeyCustomizationInfo(i, i2, i3);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeKeyCustomizationInfoByPackage(String str, int i, int i2) {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "removeKeyCustomizationInfo")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.removeKeyCustomizationInfoByPackage(str, i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void clearKeyCustomizationInfoByKeyCode(int i, int i2) {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "clearKeyCustomizationInfoByKeyCode")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.clearKeyCustomizationInfoByKeyCode(i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "clearKeyCustomizationInfoByAction")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.clearKeyCustomizationInfoByAction(i, i2, i3);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getBackupKeyCustomizationInfoList() {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "getBackupKeyCustomizationInfoList")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        return this.mExt.mPolicyExt.getBackupKeyCustomizationInfoList();
    }

    public void restoreKeyCustomizationInfo(List list) {
        if (!checkCallingPermission("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "restoreKeyCustomizationInfo")) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        if (list == null || list.size() == 0) {
            Slog.d(StartingSurfaceController.TAG, "keyInfoArray is null or size is zero.");
        } else {
            this.mExt.mPolicyExt.restoreKeyCustomizationInfo(list);
        }
    }

    public int getRotationLockOrientation(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                int rotationLockOrientation = displayContent.getDisplayRotation().getRotationLockOrientation();
                resetPriorityAfterLockedSection();
                return rotationLockOrientation;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setDisplayColorToSystemProperties(int i) {
        ActivityTaskManagerService.enforceTaskPermission("setDisplayColorToSystemProperties()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemProperties.set("persist.sys.sf.native_mode", Integer.toString(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setDragSurfaceToOverlay(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mDragDropController.setDragSurfaceToOverlay(z);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, IBinder iBinder) {
        if (CoreRune.FW_SPEN) {
            this.mExt.dispatchSPenGestureEvent(i, i2, inputEventArr, iBinder);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SurfaceControl startRemoteWallpaperAnimation(IBinder iBinder, int i) {
        DisplayContent displayContent;
        DisplayContent displayContent2 = null;
        if (!CoreRune.FW_REMOTE_WALLPAPER_ANIM || i != 0) {
            return null;
        }
        if (!checkCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "startRemoteWallpaperAnimation()")) {
            throw new SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent3 = this.mRoot.getDisplayContent(i);
                    try {
                        if (displayContent3 != null) {
                            SurfaceControl startRemoteWallpaperAnimation = displayContent3.mWallpaperController.startRemoteWallpaperAnimation(iBinder, callingPid);
                            displayContent = startRemoteWallpaperAnimation;
                            if (startRemoteWallpaperAnimation != 0) {
                                SurfaceControl surfaceControl = new SurfaceControl(startRemoteWallpaperAnimation, "startRemoteWallpaperAnimation");
                                resetPriorityAfterLockedSection();
                                Slog.d(StartingSurfaceController.TAG, "startRemoteWallpaperAnimation, d=" + i + ", leash=" + startRemoteWallpaperAnimation);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return surfaceControl;
                            }
                        } else {
                            displayContent = null;
                        }
                        try {
                            resetPriorityAfterLockedSection();
                            Slog.d(StartingSurfaceController.TAG, "startRemoteWallpaperAnimation, d=" + i + ", leash=" + displayContent);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            displayContent2 = displayContent;
                            Slog.d(StartingSurfaceController.TAG, "startRemoteWallpaperAnimation, d=" + i + ", leash=" + displayContent2);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        displayContent2 = displayContent3;
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public boolean finishRemoteWallpaperAnimation(IBinder iBinder) {
        if (!CoreRune.FW_REMOTE_WALLPAPER_ANIM) {
            return false;
        }
        if (!checkCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "finishRemoteWallpaperAnimation()")) {
            throw new SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                    r1 = defaultDisplayContentLocked != null ? defaultDisplayContentLocked.mWallpaperController.finishRemoteWallpaperAnimation(iBinder) : false;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return r1;
        } finally {
            Slog.d(StartingSurfaceController.TAG, "finishRemoteWallpaperAnimation, success=" + r1);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean hasTopOccludesParentTarget() {
        boolean z;
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "hasTopOccludesParentTarget()")) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        if (!CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK) {
            return false;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = this.mExt.mLastOccludesParentReportedTarget != null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return z;
    }

    public void dispatchSmartClipRemoteRequest(int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, IBinder iBinder) {
        Slog.d(StartingSurfaceController.TAG, "dispatchSmartClipRemoteRequest, flag : true");
        this.mExt.dispatchSmartClipRemoteRequest(i, i2, smartClipRemoteRequestInfo, iBinder);
    }

    public void startLockscreenFingerprintAuth() {
        this.mExt.startLockscreenFingerprintAuth();
    }

    public boolean isOnScreenWindow(int i) {
        final ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda34
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        arrayList.add((WindowState) obj);
                    }
                }, true);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    WindowState windowState = (WindowState) arrayList.get(i2);
                    if (windowState.getOwningUid() == i && windowState.isOnScreen()) {
                        resetPriorityAfterLockedSection();
                        return true;
                    }
                }
                resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean hasFloatingOrOnScreenWindow(int i) {
        final ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        arrayList.add((WindowState) obj);
                    }
                }, true);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    WindowState windowState = (WindowState) arrayList.get(i2);
                    if (windowState.getOwningUid() == i && (isFloatingWindowOpsLocked(windowState) || windowState.isOnScreen())) {
                        resetPriorityAfterLockedSection();
                        return true;
                    }
                }
                resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean isFloatingWindowOpsLocked(WindowState windowState) {
        int i = windowState.mAppOp;
        return i == 24 || i == 45;
    }

    public ArrayList getVisibleWinSurfacePkgList() {
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    Session session = (Session) this.mSessions.valueAt(size);
                    if (session.getWinSurfaceVisibleCount() > 0 && !arrayList.contains(session.getPackageName())) {
                        arrayList.add(session.getPackageName());
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return arrayList;
    }

    public String getRequestFocusPkg() {
        String requestFocusPkg;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                requestFocusPkg = getDefaultDisplayContentLocked().getInputMonitor().getRequestFocusPkg();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return requestFocusPkg;
    }
}
