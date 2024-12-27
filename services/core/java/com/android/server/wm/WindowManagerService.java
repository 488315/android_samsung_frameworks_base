package com.android.server.wm;

import android.R;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.ContextImpl;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.SemStatusBarManager;
import android.app.WindowConfiguration;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.TestUtilityService;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.configstore.V1_1.ISurfaceFlingerConfigs$Proxy;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.input.InputManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.InputConstants;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
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
import android.os.TokenWatcher;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.service.displayhash.DisplayHashParams;
import android.service.displayhash.IDisplayHashingService;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.sysprop.SurfaceFlingerProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Log;
import android.util.MergedConfiguration;
import android.util.Pair;
import android.util.RotationUtils;
import android.util.Size;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.Choreographer;
import android.view.ContentRecordingSession;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.ICrossWindowBlurEnabledListener;
import android.view.IDecorViewGestureListener;
import android.view.IDisplayChangeWindowController;
import android.view.IDisplayFoldListener;
import android.view.IDisplayWindowInsetsController;
import android.view.IDisplayWindowListener;
import android.view.IInputFilter;
import android.view.IOnKeyguardExitResult;
import android.view.IPinnedTaskListener;
import android.view.IRecentsAnimationRunner;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
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
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.MagnificationSpec;
import android.view.PrivacyIndicatorBounds;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.ScrollCaptureResponse;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.WindowContentFrameStats;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.WindowManagerPolicyConstants;
import android.view.WindowRelayoutResult;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.displayhash.DisplayHash;
import android.view.displayhash.VerifiedDisplayHash;
import android.view.inputmethod.ImeTracker;
import android.window.AddToSurfaceSyncGroupResult;
import android.window.ClientWindowFrames;
import android.window.IGlobalDragListener;
import android.window.IScreenRecordingCallback;
import android.window.ISurfaceSyncGroupCompletedListener;
import android.window.ITaskFpsCallback;
import android.window.ITransactionReadyCallback;
import android.window.ITrustedPresentationListener;
import android.window.InputTransferToken;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.ScreenCapture;
import android.window.SurfaceSyncGroup;
import android.window.SystemPerformanceHinter;
import android.window.TaskSnapshot;
import android.window.TrustedPresentationThresholds;
import android.window.WindowContainerToken;
import android.window.WindowContextInfo;
import android.window.WindowInfosListener;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.os.IResultReceiver;
import com.android.internal.os.SomeArgs;
import com.android.internal.os.TransferPipe;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardLockedStateListener;
import com.android.internal.policy.IShortcutService;
import com.android.internal.policy.KeyInterceptionInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.protolog.common.LogLevel;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnimationThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.FgThread;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.UiThread;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayPowerController;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.ImeVisibilityStateComputer;
import com.android.server.inputmethod.ImfLock;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda5;
import com.android.server.knox.zt.usertrust.AuthFactorTouchManager;
import com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda0;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.policy.DisplayFoldController;
import com.android.server.policy.KeyCustomizationConstants;
import com.android.server.policy.KeyCustomizationInfoManager;
import com.android.server.policy.KeyCustomizationManager;
import com.android.server.policy.ModifierShortcutManager;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda0;
import com.android.server.policy.SystemKeyManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.ShutdownThread;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.utils.PriorityDump;
import com.android.server.utils.UserTokenWatcher;
import com.android.server.wallpaper.WallpaperCropper;
import com.android.server.wm.AccessibilityController;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.BackNavigationController;
import com.android.server.wm.Dimmer;
import com.android.server.wm.DisplayAreaPolicy;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.DisplayHashController;
import com.android.server.wm.DisplayRotation;
import com.android.server.wm.DisplayWindowSettingsProvider;
import com.android.server.wm.DragState;
import com.android.server.wm.EmbeddedWindowController;
import com.android.server.wm.KeyguardDisableHandler;
import com.android.server.wm.LockTaskController;
import com.android.server.wm.OneHandOpPolicy;
import com.android.server.wm.RecentsAnimationController;
import com.android.server.wm.RefreshRatePolicy;
import com.android.server.wm.RotationWatcherController;
import com.android.server.wm.ScreenRecordingCallbackController;
import com.android.server.wm.ScreenRecordingCallbackController.Callback;
import com.android.server.wm.ScreenRotationAnimation;
import com.android.server.wm.SensitiveContentPackages;
import com.android.server.wm.SimpleSurfaceAnimatable;
import com.android.server.wm.SnapshotPersistQueue;
import com.android.server.wm.SurfaceAnimator;
import com.android.server.wm.SurfaceSyncGroupController;
import com.android.server.wm.TrustedPresentationListenerController;
import com.android.server.wm.TrustedPresentationListenerController.AnonymousClass1;
import com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient;
import com.android.server.wm.TspStateController;
import com.android.server.wm.ViewServer;
import com.android.server.wm.WallpaperController;
import com.android.server.wm.WindowContextListenerController;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowState;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestResult;
import com.samsung.android.content.smartclip.SpenGestureManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.onehandop.IOneHandOpWatcher;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.InputRune;
import com.samsung.android.server.util.FullScreenAppsSupportUtils;
import com.samsung.android.view.MultiResolutionChangeRequestInfo;
import com.samsung.android.view.ScreenshotResult;
import com.samsung.android.view.SemWindowManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
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
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class WindowManagerService extends IWindowManager.Stub implements Watchdog.Monitor, WindowManagerPolicy.WindowManagerFuncs {
    public static final boolean ENABLE_FIXED_ROTATION_TRANSFORM;
    public static final int MY_PID = Process.myPid();
    public static final int MY_UID = Process.myUid();
    public static final boolean sEnableShellTransitions;
    public static final WindowManagerThreadPriorityBooster sThreadPriorityBooster;
    public final Map PENDING_INTENT_AFTER_UNLOCK_ALLOW_MAP;
    public final AccessibilityController mAccessibilityController;
    public final IActivityManager mActivityManager;
    public final AnonymousClass4 mActivityManagerAppTransitionNotifier;
    public final boolean mAllowAnimationsInLowPowerMode;
    public final boolean mAllowBootMessages;
    public final boolean mAllowTheaterModeWakeFromLayout;
    public final ActivityManagerInternal mAmInternal;
    public final Handler mAnimationHandler;
    public final ArrayMap mAnimationTransferMap;
    public boolean mAnimationsDisabled;
    public final WindowAnimator mAnimator;
    public float mAnimatorDurationScaleSetting;
    public final AnrController mAnrController;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final ArrayList mAppFreezeListeners;
    public final AppOpsManager mAppOps;
    public int mAppsFreezingScreen;
    public final boolean mAssistantOnTopOfDream;
    public final ActivityTaskManagerService mAtmService;
    public final BlurController mBlurController;
    public boolean mBootAnimationStopped;
    public long mBootWaitForWindowsStartTime;
    public final AnonymousClass2 mBroadcastReceiver;
    public final IntArray mCaptureBlockedToastShownUids;
    public boolean mClientFreezingScreen;
    public final int mConfigTypes;
    public final WindowManagerConstants mConstants;
    final ContentRecordingController mContentRecordingController;
    public final Context mContext;
    public int mCurrentUserId;
    public final int mDecorTypes;
    public final ArrayList mDestroySurface;
    public volatile boolean mDisableSecureWindows;
    public final boolean mDisableTransitionAnimation;
    public final DisplayAreaPolicy.DefaultProvider mDisplayAreaPolicyProvider;
    public IDisplayChangeWindowController mDisplayChangeController;
    public final WindowManagerService$$ExternalSyntheticLambda32 mDisplayChangeControllerDeath;
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
    public final WindowManagerServiceExt mExt;
    public final WindowManagerFlags mFlags;
    public boolean mFocusMayChange;
    public InputTarget mFocusedInputTarget;
    public boolean mForceDesktopModeOnExternalDisplays;
    public boolean mForceDisplayEnabled;
    public final ArrayList mForceRemoves;
    public final ArrayList mFrameChangingWindows;
    public int mFrozenDisplayId;
    public final WindowManagerGlobalLock mGlobalLock;
    public final H mH;
    public boolean mHardKeyboardAvailable;
    public WindowManagerInternal.OnHardKeyboardStatusChangeListener mHardKeyboardStatusChangeListener;
    public boolean mHasHdrSupport;
    public final boolean mHasPermanentDpad;
    public boolean mHasWideColorGamutSupport;
    public final ArrayList mHidingNonSystemOverlayWindows;
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
    public final KnoxRemoteScreenCallbackController mKnoxRemoteScreenCallbackController;
    public String mLastANRState;
    public int mLastDisplayFreezeDuration;
    public Object mLastFinishedFreezeSource;
    public final LatencyTracker mLatencyTracker;
    public final int mMaxUiWidth;
    public volatile float mMaximumObscuringOpacityForTouch;
    public WindowManagerInternal.OnImeRequestedChangedListener mOnImeRequestedChangedListener;
    public final SparseIntArray mOrientationMapping;
    public final int mOverrideConfigTypes;
    public final int mOverrideDecorTypes;
    boolean mPerDisplayFocusEnabled;
    public final PackageManagerInternal mPmInternal;
    public boolean mPointerLocationEnabled;
    WindowManagerPolicy mPolicy;
    public final PossibleDisplayInfoMapper mPossibleDisplayInfoMapper;
    public final PowerManager mPowerManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public final AnonymousClass3 mPriorityDumper;
    public RecentsAnimationController mRecentsAnimationController;
    public final ArrayList mResizingWindows;
    public final RootWindowContainer mRoot;
    public final RotationWatcherController mRotationWatcherController;
    public boolean mSafeMode;
    public final PowerManager.WakeLock mScreenFrozenLock;
    public final ScreenRecordingCallbackController mScreenRecordingCallbackController;
    public final SensitiveContentPackages mSensitiveContentPackages;
    public long mSensitiveContentProtectionSessionId;
    public final ArraySet mSessions;
    public final SettingsObserver mSettingsObserver;
    IRemoteAnimationRunner mShellRemoteAnimRunner;
    public boolean mShowAlertWindowNotifications;
    public boolean mShowingBootMessages;
    boolean mSkipActivityRelaunchWhenDocking;
    public final SnapshotController mSnapshotController;
    public final StartingSurfaceController mStartingSurfaceController;
    public StrictModeFlash mStrictModeFlash;
    public final boolean mSupportsHighPerfTransitions;
    public final SurfaceAnimationRunner mSurfaceAnimationRunner;
    public final Function mSurfaceControlFactory;
    public final SurfaceSyncGroupController mSurfaceSyncGroupController;
    public boolean mSwitchingUser;
    public final BLASTSyncEngine mSyncEngine;
    public boolean mSystemBooted;
    public final SystemPerformanceHinter mSystemPerformanceHinter;
    public boolean mSystemReady;
    public final TaskFpsCallbackController mTaskFpsCallbackController;
    public final TaskPositioningController mTaskPositioningController;
    public final TaskSnapshotController mTaskSnapshotController;
    public final TaskSystemBarsListenerController mTaskSystemBarsListenerController;
    public WindowContentFrameStats mTempWindowRenderStats;
    public final TestUtilityService mTestUtilityService;
    public final Rect mTmpRect;
    public final SurfaceControl.Transaction mTransaction;
    public final Supplier mTransactionFactory;
    public int mTransactionSequence;
    public float mTransitionAnimationScaleSetting;
    public final TransitionTracer mTransitionTracer;
    public final TrustedPresentationListenerController mTrustedPresentationListenerController;
    public final UserManagerInternal mUmInternal;
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
    public final RemoteCallbackList mKeyguardLockedStateListeners = new RemoteCallbackList();
    public final List mOnWindowRemovedListeners = new ArrayList();
    public boolean mDispatchedKeyguardLockedState = false;
    public int mVr2dDisplayId = -1;
    public boolean mVrModeEnabled = false;
    public final Map mKeyInterceptionInfoForToken = Collections.synchronizedMap(new ArrayMap());
    public final AnonymousClass1 mVrStateCallbacks = new AnonymousClass1();

    /* renamed from: com.android.server.wm.WindowManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IVrStateCallbacks.Stub {
        public AnonymousClass1() {
        }

        public final void onVrStateChanged(boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService windowManagerService = WindowManagerService.this;
                    windowManagerService.mVrModeEnabled = z;
                    windowManagerService.mRoot.forAllDisplayPolicies(new WindowManagerService$$ExternalSyntheticLambda4(2, z));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* renamed from: com.android.server.wm.WindowManagerService$11, reason: invalid class name */
    public final class AnonymousClass11 extends IRemoteAnimationRunner.Stub {
        public final void onAnimationCancelled() {
            ActivityManagerService$$ExternalSyntheticOutline0.m(3, new StringBuilder("mShellRemoteAnimRunner#onAnimationCancelled, caller="), "WindowManager");
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            ActivityManagerService$$ExternalSyntheticOutline0.m(3, new StringBuilder("mShellRemoteAnimRunner#onAnimationStart, caller="), "WindowManager");
        }
    }

    /* renamed from: com.android.server.wm.WindowManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 implements WindowManagerPolicy.OnKeyguardExitResult, PriorityDump.PriorityDumper {
        public final /* synthetic */ Object this$0;

        public AnonymousClass3(IOnKeyguardExitResult iOnKeyguardExitResult) {
            this.this$0 = iOnKeyguardExitResult;
        }

        public AnonymousClass3(WindowManagerService windowManagerService) {
            this.this$0 = windowManagerService;
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            WindowManagerService.m1074$$Nest$mdoDump((WindowManagerService) this.this$0, fileDescriptor, printWriter, strArr, z);
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public void dumpCritical(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            WindowManagerService.m1074$$Nest$mdoDump((WindowManagerService) this.this$0, fileDescriptor, printWriter, new String[]{"-a"}, z);
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public void dumpHigh(final FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr, boolean z) {
            if (z) {
                return;
            }
            ((WindowManagerService) this.this$0).mAtmService.dumpActivity(fileDescriptor, printWriter, "all", new String[0], 0, true, true, false, -1, -1, 1000L);
            WindowManagerService windowManagerService = (WindowManagerService) this.this$0;
            windowManagerService.getClass();
            ArrayList arrayList = new ArrayList();
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    windowManagerService.mRoot.forAllWindows((Consumer) new WindowManagerService$$ExternalSyntheticLambda5(arrayList, 3), false);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            arrayList.forEach(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda37
                public final /* synthetic */ long f$2 = 1000;

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PrintWriter printWriter2 = printWriter;
                    FileDescriptor fileDescriptor2 = fileDescriptor;
                    long j = this.f$2;
                    WindowState windowState = (WindowState) obj;
                    int i = WindowManagerService.MY_PID;
                    printWriter2.println("---------------------------------");
                    printWriter2.println(windowState.toString());
                    printWriter2.flush();
                    try {
                        TransferPipe transferPipe = new TransferPipe();
                        try {
                            windowState.mClient.dumpWindow(transferPipe.getWriteFd());
                            transferPipe.go(fileDescriptor2, j);
                            transferPipe.close();
                        } catch (Throwable th2) {
                            try {
                                transferPipe.close();
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                            }
                            throw th2;
                        }
                    } catch (RemoteException unused) {
                        printWriter2.println("Got a RemoteException while dumping the window");
                    } catch (IOException e) {
                        printWriter2.println("Failure while dumping the window: " + e);
                    }
                }
            });
        }

        @Override // com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult
        public void onKeyguardExitResult(boolean z) {
            try {
                ((IOnKeyguardExitResult) this.this$0).onKeyguardExitResult(z);
            } catch (RemoteException unused) {
            }
        }
    }

    /* renamed from: com.android.server.wm.WindowManagerService$4, reason: invalid class name */
    public final class AnonymousClass4 extends WindowManagerInternal.AppTransitionListener {
        public AnonymousClass4() {
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionCancelledLocked(boolean z) {
            WindowManagerService.this.mAtmService.mTaskSupervisor.mActivityMetricsLogger.mInTransitionInfoList.clear();
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionFinishedLocked(IBinder iBinder) {
            RecentsAnimationController recentsAnimationController;
            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
            if (forTokenLocked == null) {
                return;
            }
            WindowManagerService windowManagerService = WindowManagerService.this;
            DexController dexController = windowManagerService.mAtmService.mDexController;
            if (dexController.mWaitingTransitionFinishedTokens.contains(forTokenLocked)) {
                dexController.mWaitingTransitionFinishedTokens.clear();
                dexController.mH.sendEmptyMessage(6);
            }
            TaskDisplayArea displayArea = forTokenLocked.getDisplayArea();
            if (displayArea != null) {
                windowManagerService.mAtmService.mFreeformController.minimizeExcessiveVisibleFreeformLocked(displayArea);
            }
            windowManagerService.mAtmService.mTaskSupervisor.mActivityMetricsLogger.mInTransitionInfoList.clear();
            if (forTokenLocked.mLaunchTaskBehind && ((recentsAnimationController = windowManagerService.mRecentsAnimationController) == null || !recentsAnimationController.isTargetApp(forTokenLocked))) {
                windowManagerService.mAtmService.mTaskSupervisor.mHandler.obtainMessage(212, forTokenLocked.token).sendToTarget();
                forTokenLocked.mLaunchTaskBehind = false;
                return;
            }
            forTokenLocked.updateReportedVisibilityLocked();
            if (forTokenLocked.mEnteringAnimation) {
                RecentsAnimationController recentsAnimationController2 = windowManagerService.mRecentsAnimationController;
                if (recentsAnimationController2 == null || !recentsAnimationController2.isTargetApp(forTokenLocked)) {
                    forTokenLocked.mEnteringAnimation = false;
                    if (forTokenLocked.attachedToProcess()) {
                        try {
                            forTokenLocked.app.mThread.scheduleEnterAnimationComplete(forTokenLocked.token);
                        } catch (RemoteException unused) {
                        }
                    }
                }
            }
        }
    }

    public final class H extends Handler {
        public H() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Message message2;
            boolean checkBootAnimationCompleteLocked;
            int i = message.what;
            int i2 = 0;
            if (i == 11) {
                DisplayContent displayContent = (DisplayContent) message.obj;
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        displayContent.getClass();
                        Slog.w("WindowManager", "Window freeze timeout expired.");
                        displayContent.mWmService.mWindowsFreezingScreen = 2;
                        displayContent.forAllWindows((Consumer) new DisplayContent$$ExternalSyntheticLambda1(6, displayContent), true);
                        displayContent.mWmService.mWindowPlacerLocked.performSurfacePlacement(false);
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
                    WindowManagerService.m1075$$Nest$mnotifyWindowsChanged(WindowManagerService.this);
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
                    for (int i3 = 0; i3 < WindowManagerService.this.mSessions.size(); i3++) {
                        try {
                            arrayList.add(((Session) WindowManagerService.this.mSessions.valueAt(i3)).mCallback);
                        } finally {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                while (i2 < arrayList.size()) {
                    try {
                        ((IWindowSessionCallback) arrayList.get(i2)).onAnimatorScaleChanged(currentAnimatorScale);
                    } catch (RemoteException unused2) {
                    }
                    i2++;
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
            if (i == 58) {
                WindowManagerService.this.mAmInternal.setHasOverlayUi(message.arg1, message.arg2 == 1);
                return;
            }
            if (i == 200) {
                PhoneWindowManagerExt phoneWindowManagerExt = WindowManagerService.this.mExt.mPolicyExt;
                boolean z = message.arg1 != 0;
                phoneWindowManagerExt.getClass();
                Slog.d("PhoneWindowManagerExt", "handleNotifyPogoKeyboardStatus status=" + z);
                if (phoneWindowManagerExt.mIsPogoKeyboardConnected == z) {
                    return;
                }
                phoneWindowManagerExt.mIsPogoKeyboardConnected = z;
                InputManager inputManager = InputManager.getInstance();
                if (inputManager == null) {
                    return;
                }
                if (!phoneWindowManagerExt.mIsPogoKeyboardConnected || phoneWindowManagerExt.isInDexMode()) {
                    inputManager.semUnregisterOnMultiFingerGestureListener(phoneWindowManagerExt.mMultiFingerGestureListener);
                    return;
                } else {
                    inputManager.semRegisterOnMultiFingerGestureListener(phoneWindowManagerExt.mMultiFingerGestureListener, null);
                    return;
                }
            }
            if (i == 16) {
                WindowManagerService windowManagerService3 = WindowManagerService.this;
                int i4 = WindowManagerService.MY_PID;
                windowManagerService3.performEnableScreen();
                return;
            }
            if (i == 17) {
                WindowManagerGlobalLock windowManagerGlobalLock7 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock7) {
                    try {
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 1624328195833150047L, 0, "App freeze timeout expired.", null);
                        }
                        WindowManagerService windowManagerService4 = WindowManagerService.this;
                        windowManagerService4.mWindowsFreezingScreen = 2;
                        for (int size = windowManagerService4.mAppFreezeListeners.size() - 1; size >= 0; size += -1) {
                            ActivityRecord activityRecord2 = (ActivityRecord) WindowManagerService.this.mAppFreezeListeners.get(size);
                            activityRecord2.getClass();
                            Slog.w("WindowManager", "Force clearing freeze: " + activityRecord2);
                            activityRecord2.stopFreezingScreen(true);
                        }
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 51) {
                int i5 = message.arg1;
                if (i5 == 0) {
                    WindowManagerService windowManagerService5 = WindowManagerService.this;
                    int i6 = WindowManagerService.MY_PID;
                    windowManagerService5.mWindowAnimationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(windowManagerService5.mContext.getContentResolver(), "window_animation_scale", windowManagerService5.mWindowAnimationScaleSetting));
                    return;
                } else if (i5 == 1) {
                    WindowManagerService windowManagerService6 = WindowManagerService.this;
                    int i7 = WindowManagerService.MY_PID;
                    windowManagerService6.mTransitionAnimationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(windowManagerService6.mContext.getContentResolver(), "transition_animation_scale", windowManagerService6.mContext.getResources().getFloat(R.dimen.config_letterboxDefaultMinAspectRatioForUnresizableApps)));
                    return;
                } else {
                    if (i5 != 2) {
                        return;
                    }
                    WindowManagerService windowManagerService7 = WindowManagerService.this;
                    int i8 = WindowManagerService.MY_PID;
                    windowManagerService7.mAnimatorDurationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(windowManagerService7.mContext.getContentResolver(), "animator_duration_scale", windowManagerService7.mAnimatorDurationScaleSetting));
                    WindowManagerService.this.mH.obtainMessage(34, null).sendToTarget();
                    return;
                }
            }
            if (i == 52) {
                WindowState windowState = (WindowState) message.obj;
                WindowManagerGlobalLock windowManagerGlobalLock8 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock8) {
                    try {
                        windowState.mAttrs.flags &= -129;
                        if (!windowState.mPermanentlyHidden) {
                            windowState.mPermanentlyHidden = true;
                            windowState.hide(true, true);
                        }
                        windowState.setDisplayLayoutNeeded();
                        WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement(false);
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
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
                            if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 5830724144971462783L, 0, "Timeout waiting for drawn: undrawn=%s", String.valueOf(windowContainer.mWaitingForDrawn));
                            }
                            if (Trace.isTagEnabled(32L)) {
                                while (i2 < windowContainer.mWaitingForDrawn.size()) {
                                    WindowManagerService windowManagerService8 = WindowManagerService.this;
                                    WindowState windowState2 = (WindowState) windowContainer.mWaitingForDrawn.get(i2);
                                    windowManagerService8.getClass();
                                    WindowManagerService.traceEndWaitingForWindowDrawn(windowState2);
                                    i2++;
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
                    WindowManagerService.m1076$$Nest$mshowStrictModeViolation(WindowManagerService.this, message.arg1, message.arg2);
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
                                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
                                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -2240705227895260140L, 0, null, null);
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
                                    WindowManagerService windowManagerService9 = WindowManagerService.this;
                                    windowManagerService9.mLastANRState = null;
                                    windowManagerService9.mAtmService.mLastANRState = null;
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
                                    if (wallpaperController != null && wallpaperController.mWallpaperDrawState == 1) {
                                        wallpaperController.mWallpaperDrawState = 2;
                                        WindowManagerService windowManagerService10 = wallpaperController.mService;
                                        RecentsAnimationController recentsAnimationController = windowManagerService10.mRecentsAnimationController;
                                        if (recentsAnimationController != null) {
                                            recentsAnimationController.startAnimation();
                                        }
                                        BackNavigationController backNavigationController = windowManagerService10.mAtmService.mBackNavigationController;
                                        if (backNavigationController.mBackAnimationInProgress) {
                                            BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0 backNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0 = backNavigationController.mPendingAnimation;
                                            if (backNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0 != null) {
                                                backNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0.run();
                                                backNavigationController.mPendingAnimation = null;
                                            }
                                        } else if (backNavigationController.mPendingAnimation != null) {
                                            backNavigationController.clearBackAnimations(true);
                                            backNavigationController.mPendingAnimation = null;
                                        }
                                        WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement(false);
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
                                            RecentsAnimationController recentsAnimationController2 = WindowManagerService.this.mRecentsAnimationController;
                                            if (recentsAnimationController2 != null) {
                                                recentsAnimationController2.mService.mH.postDelayed(recentsAnimationController2.mFailsafeRunnable, 1000L);
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
                                                WindowManagerService windowManagerService11 = WindowManagerService.this;
                                                windowManagerService11.onPointerDownOutsideFocusLocked(windowManagerService11.getInputTargetFromToken((IBinder) obj), 0, -1, -1);
                                            }
                                        } finally {
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
                                                    WindowState windowState3 = (WindowState) message.obj;
                                                    Slog.i("WindowManager", "Blast sync timeout: " + windowState3);
                                                    windowState3.finishDrawing(null, Integer.MAX_VALUE);
                                                    windowState3.mWmService.mH.removeMessages(64, windowState3);
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
                                                    task.reparent(WindowManagerService.this.mRoot.mDefaultDisplay.getDefaultTaskDisplayArea(), true);
                                                    task.resumeNextFocusAfterReparent();
                                                } finally {
                                                    WindowManagerService.resetPriorityAfterLockedSection();
                                                }
                                            }
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                            return;
                                        case 66:
                                            WindowManagerGlobalLock windowManagerGlobalLock18 = WindowManagerService.this.mGlobalLock;
                                            WindowManagerService.boostPriorityForLockedSection();
                                            synchronized (windowManagerGlobalLock18) {
                                                try {
                                                    WindowManagerService windowManagerService12 = WindowManagerService.this;
                                                    if (windowManagerService12.mWindowsInsetsChanged > 0) {
                                                        windowManagerService12.mWindowPlacerLocked.performSurfacePlacement(false);
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
    }

    public final class ImeTargetVisibilityPolicyImpl {
        public ImeTargetVisibilityPolicyImpl() {
        }
    }

    public final class LocalService extends WindowManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void addBlockScreenCaptureForApps(ArraySet arraySet) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    SensitiveContentPackages sensitiveContentPackages = WindowManagerService.this.mSensitiveContentPackages;
                    if (!sensitiveContentPackages.mProtectedPackages.equals(arraySet)) {
                        sensitiveContentPackages.mProtectedPackages.addAll(arraySet);
                        WindowManagerService.this.refreshScreenCaptureDisabled();
                        if (Flags.sensitiveContentImprovements()) {
                            WindowManagerService.this.mRoot.forAllWindows((Consumer) new WindowManagerService$$ExternalSyntheticLambda6(4, this), true);
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void addFixedRefreshRatePackageInternal(final String str, final int i) {
            if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE) {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                String str2 = str;
                                int i2 = i;
                                RefreshRatePolicy refreshRatePolicy = ((DisplayContent) obj).mDisplayPolicy.mRefreshRatePolicy;
                                refreshRatePolicy.mFixedRefreshRatePackages.put(str2, Integer.valueOf(i2));
                                refreshRatePolicy.mWmService.requestTraversal();
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

        @Override // com.android.server.wm.WindowManagerInternal
        public final void addRefreshRateRangeForPackage(final String str, final float f, final float f2) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            String str2 = str;
                            float f3 = f;
                            float f4 = f2;
                            RefreshRatePolicy refreshRatePolicy = ((DisplayContent) obj).mDisplayPolicy.mRefreshRatePolicy;
                            RefreshRatePolicy.PackageRefreshRate packageRefreshRate = refreshRatePolicy.mNonHighRefreshRatePackages;
                            RefreshRatePolicy refreshRatePolicy2 = RefreshRatePolicy.this;
                            packageRefreshRate.mPackages.put(str2, new SurfaceControl.RefreshRateRange(Math.max(refreshRatePolicy2.mMinSupportedRefreshRate, f3), Math.min(refreshRatePolicy2.mMaxSupportedRefreshRate, f4)));
                            refreshRatePolicy.mWmService.requestTraversal();
                        }
                    });
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void addTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (surfacePackage == null) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid overlay passed in for task="));
            }
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (surfacePackage.getSurfaceControl() == null || !surfacePackage.getSurfaceControl().isValid()) {
                        throw new IllegalArgumentException("Invalid overlay surfacecontrol passed in for task=" + i);
                    }
                    Task rootTask = WindowManagerService.this.mRoot.getRootTask(i);
                    if (rootTask == null) {
                        throw new IllegalArgumentException("no task with taskId" + i);
                    }
                    rootTask.addTrustedOverlay(surfacePackage, rootTask.getTopVisibleAppMainWindow(false));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) {
            WindowManagerService.this.addWindowToken(iBinder, i, i2, bundle);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) {
            WindowManagerService.this.captureDisplay(i, captureArgs, screenCaptureListener);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void clearBlockedApps() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    SensitiveContentPackages sensitiveContentPackages = WindowManagerService.this.mSensitiveContentPackages;
                    if (!sensitiveContentPackages.mProtectedPackages.isEmpty()) {
                        sensitiveContentPackages.mProtectedPackages.clear();
                        WindowManagerService.this.refreshScreenCaptureDisabled();
                    }
                    if (Flags.sensitiveContentImprovements()) {
                        WindowManagerService.this.mCaptureBlockedToastShownUids.clear();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void clearDisplaySettings(String str, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        DisplayWindowSettings displayWindowSettings = WindowManagerService.this.mDisplayWindowSettings;
                        displayWindowSettings.getClass();
                        DisplayInfo displayInfo = new DisplayInfo();
                        displayInfo.uniqueId = str;
                        displayInfo.type = i;
                        DisplayWindowSettingsProvider.WritableSettings writableSettings = displayWindowSettings.mSettingsProvider.mOverrideSettings;
                        String identifier = writableSettings.getIdentifier(displayInfo);
                        ((ArrayMap) writableSettings.mSettings).remove(identifier);
                        writableSettings.mVirtualDisplayIdentifiers.remove(identifier);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void clearForcedDisplaySize(int i) {
            WindowManagerService.this.clearForcedDisplaySize(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void clearSnapshotCache() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    TaskSnapshotCache taskSnapshotCache = (TaskSnapshotCache) WindowManagerService.this.mTaskSnapshotController.mCache;
                    synchronized (taskSnapshotCache.mLock) {
                        taskSnapshotCache.mRunningCache.clear();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void computeWindowsForAccessibility(int i) {
            WindowManagerService.this.mAccessibilityController.performComputeChangedWindowsNot(i, true);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void freezeDisplayRotation(int i, int i2, String str) {
            ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "freezeDisplayRotation: #", ", rot=", "WindowManager");
            WindowManagerService.this.freezeDisplayRotation(i, i2, str);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final SurfaceControl getA11yOverlayLayer(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    SurfaceControl surfaceControl = displayContent.mA11yOverlayLayer;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return surfaceControl;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final WindowManagerInternal.AccessibilityControllerInternal getAccessibilityController() {
            return AccessibilityController.getAccessibilityControllerInternal(WindowManagerService.this);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final int getDisplayIdForWindow(IBinder iBinder) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    int i = windowState.getDisplayContent().mDisplayId;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final int getDisplayImePolicy(int i) {
            return WindowManagerService.this.getDisplayImePolicy(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final IBinder getFocusedWindowToken() {
            IBinder iBinder;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    AccessibilityController accessibilityController = WindowManagerService.this.mAccessibilityController;
                    iBinder = (IBinder) accessibilityController.mFocusedWindow.get(accessibilityController.mFocusedDisplay);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return iBinder;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final IBinder getFocusedWindowTokenFromWindowStates() {
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
        public final SurfaceControl getHandwritingSurfaceForDisplay(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.e("WindowManager", "Failed to create a handwriting surface on display: " + i + " - DisplayContent not found.");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    SurfaceControl surfaceControl = displayContent.mInputOverlayLayer;
                    if (surfaceControl != null) {
                        SurfaceControl build = WindowManagerService.this.makeSurfaceBuilder(displayContent.mSession).setContainerLayer().setName("IME Handwriting Surface").setCallsite("getHandwritingSurfaceForDisplay").setParent(surfaceControl).build();
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return build;
                    }
                    Slog.e("WindowManager", "Failed to create a gesture monitor on display: " + i + " - Input overlay layer is not initialized.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final int[] getInitialDisplayProperties(int i) {
            WindowManagerServiceExt windowManagerServiceExt = WindowManagerService.this.mExt;
            int[] iArr = new int[3];
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = windowManagerServiceExt.mService.mRoot.getDisplayContent(i);
                    if (displayContent != null && displayContent.hasAccess(Binder.getCallingUid())) {
                        iArr[0] = displayContent.mInitialDisplayWidth;
                        iArr[1] = displayContent.mInitialDisplayHeight;
                        iArr[2] = displayContent.mInitialDisplayDensity;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return iArr;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final int getInputMethodWindowVisibleHeight(int i) {
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
        public final KeyInterceptionInfo getKeyInterceptionInfoFromToken(IBinder iBinder) {
            return (KeyInterceptionInfo) WindowManagerService.this.mKeyInterceptionInfoForToken.get(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void getMagnificationRegion(int i, Region region) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        throw new IllegalStateException("Magnification callbacks not set!");
                    }
                    WindowManagerService.this.mAccessibilityController.getMagnificationRegion(i, region);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final IBinder getTargetWindowTokenFromInputToken(IBinder iBinder) {
            InputTarget inputTargetFromToken = WindowManagerService.this.getInputTargetFromToken(iBinder);
            if (inputTargetFromToken == null) {
                return null;
            }
            return inputTargetFromToken.getWindowToken();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final int getTopFocusedDisplayId() {
            int i;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    i = WindowManagerService.this.mRoot.getTopFocusedDisplayContent().mDisplayId;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return i;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final Context getTopFocusedDisplayUiContext() {
            Context context;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    context = WindowManagerService.this.mRoot.getTopFocusedDisplayContent().mDisplayPolicy.mUiContext;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return context;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void getWindowFrame(IBinder iBinder, Rect rect) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState != null) {
                        rect.set(windowState.mWindowFrames.mFrame);
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
        public final String getWindowName(IBinder iBinder) {
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
        public final int getWindowOwnerUserId(IBinder iBinder) {
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
                    EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) WindowManagerService.this.mEmbeddedWindowController.mWindowsByWindowToken.get(iBinder);
                    if (embeddedWindow == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -10000;
                    }
                    int i2 = embeddedWindow.mShowUserId;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return i2;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final Pair getWindowTransformationMatrixAndMagnificationSpec(IBinder iBinder) {
            Pair pair;
            MagnificationSpec magnificationSpecForWindow;
            AccessibilityController accessibilityController = WindowManagerService.this.mAccessibilityController;
            WindowManagerGlobalLock windowManagerGlobalLock = accessibilityController.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Matrix matrix = new Matrix();
                    MagnificationSpec magnificationSpec = new MagnificationSpec();
                    WindowState windowState = (WindowState) accessibilityController.mService.mWindowMap.get(iBinder);
                    if (windowState != null) {
                        windowState.getTransformationMatrix(new float[9], matrix);
                        if (accessibilityController.hasCallbacks() && (magnificationSpecForWindow = accessibilityController.getMagnificationSpecForWindow(windowState)) != null && !magnificationSpecForWindow.isNop()) {
                            magnificationSpec.setTo(magnificationSpecForWindow);
                        }
                    }
                    pair = new Pair(matrix, magnificationSpec);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return pair;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final int hasInputMethodClientFocus(IBinder iBinder, int i, int i2, int i3) {
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
                    int i4 = inputTargetFromWindowTokenLocked.getDisplayContent().mDisplayId;
                    if (i4 != i3) {
                        Slog.e("WindowManager", "isInputMethodClientFocus: display ID mismatch. from client: " + i3 + " from window: " + i4);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -2;
                    }
                    if (topFocusedDisplayContent != null && topFocusedDisplayContent.mDisplayId == i3 && topFocusedDisplayContent.hasAccess(i)) {
                        if (inputTargetFromWindowTokenLocked.isInputMethodClientFocus(i, i2)) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return 0;
                        }
                        WindowState windowState = topFocusedDisplayContent.mCurrentFocus;
                        if (windowState != null) {
                            Session session = windowState.mSession;
                            if (session.mUid == i && session.mPid == i2) {
                                int i5 = windowState.canBeImeTarget() ? 0 : -1;
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return i5;
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
        public final boolean hasNavigationBar(int i) {
            return WindowManagerService.this.hasNavigationBar(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void hideIme(IBinder iBinder, int i, ImeTracker.Token token) {
            Trace.traceBegin(32L, "WMS.hideIme");
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled;
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_IME, -7428028317216329062L, 0, null, String.valueOf(windowState));
                    }
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (windowState != null) {
                        if (windowState.getDisplayId() == 2 && WindowManagerService.this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked() && WindowManagerService.this.mAtmService.mDexController.hideDexImeOnDefaultDisplayLocked()) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        WindowState window = windowState.getImeControlTarget().getWindow();
                        if (window != null) {
                            displayContent = window.getDisplayContent();
                        }
                        displayContent.mInsetsStateController.getImeSourceProvider().abortShowImePostLayout();
                    }
                    if (displayContent == null || displayContent.getImeTarget(2) == null) {
                        ImeTracker.forLogging().onFailed(token, 20);
                    } else {
                        ImeTracker.forLogging().onProgress(token, 20);
                        if (zArr[0]) {
                            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_IME, 1006302987953651112L, 0, null, String.valueOf(displayContent.getImeTarget(2)));
                        }
                        displayContent.getImeTarget(2).hideInsets(WindowInsets.Type.ime(), true, token);
                    }
                    if (displayContent != null) {
                        displayContent.mInsetsStateController.getImeSourceProvider().mImeShowing = false;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Trace.traceEnd(32L);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean isEmbeddedWindowType(IBinder iBinder) {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = ((EmbeddedWindowController.EmbeddedWindow) WindowManagerService.this.mEmbeddedWindowController.mWindowsByWindowToken.get(iBinder)) != null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean isHardKeyboardAvailable() {
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
        public final boolean isHomeSupportedOnDisplay(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        boolean isHomeSupported = displayContent.isHomeSupported();
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return isHomeSupported;
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 5213970642134448962L, 1, "Attempted to get home support flag of a display that does not exist: %d", Long.valueOf(i));
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
        public final boolean isKeyguardLocked() {
            return WindowManagerService.this.isKeyguardLocked();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean isKeyguardSecure(int i) {
            return ((PhoneWindowManager) WindowManagerService.this.mPolicy).isKeyguardSecure(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean isKeyguardShowingAndNotOccluded() {
            return WindowManagerService.this.isKeyguardShowingAndNotOccluded();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean isPointInsideWindow(IBinder iBinder, int i, float f, float f2) {
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
        public final boolean isTouchOrFaketouchDevice() {
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
        public final boolean isUidAllowedOnDisplay(int i, int i2) {
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
        public final boolean isUidFocused(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    for (int childCount = WindowManagerService.this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
                        WindowState windowState = ((DisplayContent) WindowManagerService.this.mRoot.getChildAt(childCount)).mCurrentFocus;
                        if (windowState != null && i == windowState.mOwnerUid) {
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
        public final void launchHomeForDesktopMode(int i) {
            WindowManagerService.this.mExt.mPolicyExt.mPolicy.launchHomeFromHotKey(i, true, false);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void lockNow() {
            WindowManagerService.this.lockNow(null);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void moveDisplayToTopIfAllowed(int i) {
            WindowManagerService.this.moveDisplayToTopIfAllowed(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean moveFocusToAdjacentEmbeddedActivityIfNeeded() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState focusedWindow = WindowManagerService.this.getFocusedWindow();
                    if (focusedWindow == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (!WindowManagerService.this.moveFocusToAdjacentEmbeddedWindow(focusedWindow)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    WindowManagerService.this.syncInputTransactions(false);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void moveWindowTokenToDisplay(IBinder iBinder, int i) {
            WindowManagerService.this.moveWindowTokenToDisplay(iBinder, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void onDisplayManagerReceivedDeviceState(final int i) {
            WindowManagerService.this.mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerService.LocalService localService = WindowManagerService.LocalService.this;
                    int i2 = i;
                    WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            WindowManagerService.this.mRoot.onDisplayManagerReceivedDeviceState(i2);
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            });
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final WindowManagerInternal.ImeTargetInfo onToggleImeRequested(boolean z, IBinder iBinder, IBinder iBinder2, int i) {
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
                            WindowState window = imeTarget.getWindow();
                            str4 = window != null ? window.getName() : imeTarget.toString();
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
        public final void registerAppTransitionListener(WindowManagerInternal.AppTransitionListener appTransitionListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.getDefaultDisplayContentLocked().mAppTransition.registerListenerLocked(appTransitionListener);
                    WindowManagerService.this.mAtmService.mWindowOrganizerController.mTransitionController.registerLegacyListener(appTransitionListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void registerAppTransitionListener(WindowManagerInternal.AppTransitionListener appTransitionListener, int i) {
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
        public final void registerDragDropControllerCallback(WindowManagerInternal.IDragDropCallback iDragDropCallback) {
            DragDropController dragDropController = WindowManagerService.this.mDragDropController;
            dragDropController.getClass();
            Objects.requireNonNull(iDragDropCallback);
            dragDropController.mCallback.set(iDragDropCallback);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void registerOnWindowRemovedListener(WindowManagerInternal.OnWindowRemovedListener onWindowRemovedListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ((ArrayList) WindowManagerService.this.mOnWindowRemovedListeners).add(onWindowRemovedListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void registerTaskSystemBarsListener(WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mTaskSystemBarsListenerController.mListeners.add(taskSystemBarsListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void removeBlockScreenCaptureForApps(ArraySet arraySet) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (WindowManagerService.this.mSensitiveContentPackages.mProtectedPackages.removeAll(arraySet)) {
                        WindowManagerService.this.refreshScreenCaptureDisabled();
                    }
                    if (Flags.sensitiveContentImprovements()) {
                        for (int i = 0; i < arraySet.size(); i++) {
                            int i2 = ((SensitiveContentPackages.PackageInfo) arraySet.valueAt(i)).mUid;
                            if (WindowManagerService.this.mCaptureBlockedToastShownUids.contains(i2)) {
                                IntArray intArray = WindowManagerService.this.mCaptureBlockedToastShownUids;
                                intArray.remove(intArray.indexOf(i2));
                            }
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void removeFixedRefreshRatePackageInternal(String str) {
            if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE) {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowManagerService.this.mRoot.forAllDisplays(new WindowManagerService$LocalService$$ExternalSyntheticLambda1(str, 0));
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void removeRefreshRateRangeForPackage(String str) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mRoot.forAllDisplays(new WindowManagerService$LocalService$$ExternalSyntheticLambda1(str, 1));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void removeTrustedTaskOverlay(int i, SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (surfacePackage == null) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid overlay passed in for task="));
            }
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (surfacePackage.getSurfaceControl() == null || !surfacePackage.getSurfaceControl().isValid()) {
                        throw new IllegalArgumentException("Invalid overlay surfacecontrol passed in for task=" + i);
                    }
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
        public final void removeWindowToken(IBinder iBinder, boolean z, boolean z2, int i) {
            WindowManagerService.this.removeWindowToken(iBinder, z, z2, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void reportPasswordChanged(int i) {
            WindowManagerService.this.mKeyguardDisableHandler.updateKeyguardEnabled(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void requestTraversalFromDisplayManager() {
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
        public final void requestWindowFocus(IBinder iBinder) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.onPointerDownOutsideFocusLocked(WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder), 0, -1, -1);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setAccessibilityIdToSurfaceMetadata(IBinder iBinder, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        Slog.w("WindowManager", "Cannot find window which accessibility connection is added to");
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
        public final void setBlockScreenCaptureForAppsSessionId(long j) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (Flags.sensitiveContentMetricsBugfix()) {
                        WindowManagerService windowManagerService = WindowManagerService.this;
                        if (windowManagerService.mSensitiveContentProtectionSessionId != j) {
                            windowManagerService.mSensitiveContentProtectionSessionId = j;
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (contentRecordingSession != null) {
                    try {
                        if (contentRecordingSession.getContentToRecord() == 1) {
                            WindowContainerInfo taskWindowContainerInfoForRecordingSession = WindowManagerService.this.getTaskWindowContainerInfoForRecordingSession(contentRecordingSession);
                            if (taskWindowContainerInfoForRecordingSession == null) {
                                Slog.w("WindowManager", "Handling a new recording session; unable to find the WindowContainerToken");
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            contentRecordingSession.setTokenToRecord(taskWindowContainerInfoForRecordingSession.mToken.asBinder());
                            contentRecordingSession.setTargetUid(taskWindowContainerInfoForRecordingSession.mUid);
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
        public final void setDeviceStateListener(DeviceStateManager.DeviceStateCallback deviceStateCallback) {
            WindowManagerService.this.mExt.getClass();
            throw null;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setDismissImeOnBackKeyPressed(boolean z, boolean z2) {
            ((PhoneWindowManager) WindowManagerService.this.mPolicy).mDismissImeOnBackKeyPressed = z;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    boolean z3 = CoreRune.FW_TSP_STATE_CONTROLLER;
                    if (z3) {
                        WindowManagerServiceExt windowManagerServiceExt = WindowManagerService.this.mExt;
                        windowManagerServiceExt.getClass();
                        if (z3) {
                            TspStateController tspStateController = windowManagerServiceExt.mTspStateController;
                            tspStateController.mImeWindowVisible = z2;
                            if (!z2) {
                                tspStateController.mImeWindow = null;
                            }
                        }
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
        public final void setDisplaySizeAndDensityInDex(int i, int i2, int i3, int i4) {
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
                        if (displayContent != null) {
                            WindowManagerService.this.mAtmService.mDexController.setDisplaySizeAndDensityLocked(i2, i3, i4, displayContent);
                            WindowManagerService.resetPriorityAfterLockedSection();
                        } else {
                            Slog.w("WindowManager", "setDisplaySizeAndDensityInDex: cannot find dc, #" + i);
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
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
        public final void setForcedDisplaySize(int i, int i2, int i3) {
            WindowManagerService.this.setForcedDisplaySize(i, i2, i3);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setFullscreenMagnificationActivated(int i, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        throw new IllegalStateException("Magnification callbacks not set!");
                    }
                    WindowManagerService.this.mAccessibilityController.setFullscreenMagnificationActivated(i, z);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setHomeSupportedOnDisplay(String str, int i, boolean z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        DisplayWindowSettings displayWindowSettings = WindowManagerService.this.mDisplayWindowSettings;
                        displayWindowSettings.getClass();
                        DisplayInfo displayInfo = new DisplayInfo();
                        displayInfo.uniqueId = str;
                        displayInfo.type = i;
                        DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
                        overrideSettings.mIsHomeSupported = Boolean.valueOf(z);
                        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setInputFilter(IInputFilter iInputFilter) {
            WindowManagerService.this.mInputManager.setInputFilter(iInputFilter);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setInputMethodTargetChangeListener(ImeTargetChangeListener imeTargetChangeListener) {
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
        public final boolean setMagnificationCallbacks(int i, WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
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
        public final void setMagnificationSpec(int i, MagnificationSpec magnificationSpec) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        throw new IllegalStateException("Magnification callbacks not set!");
                    }
                    WindowManagerService.this.mAccessibilityController.setMagnificationSpec(i, magnificationSpec);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setOnHardKeyboardStatusChangeListener(WindowManagerInternal.OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener) {
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
        public final void setOnImeRequestedChangedListener(WindowManagerInternal.OnImeRequestedChangedListener onImeRequestedChangedListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mOnImeRequestedChangedListener = onImeRequestedChangedListener;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.setOrientationRequestPolicy(z, iArr, iArr2);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setVr2dDisplayId(int i) {
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
        public final void setWallpaperCropHints(IBinder iBinder, SparseArray sparseArray) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowToken windowToken = WindowManagerService.this.mRoot.getWindowToken(iBinder);
                    if (windowToken != null && windowToken.asWallpaperToken() != null) {
                        WallpaperWindowToken asWallpaperToken = windowToken.asWallpaperToken();
                        asWallpaperToken.getClass();
                        asWallpaperToken.mCropHints = sparseArray.clone();
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 698926505694016512L, 0, "setWallpaperCropHints: non-existent wallpaper token: %s", String.valueOf(iBinder));
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setWallpaperCropUtils(WallpaperCropper.WallpaperCropUtils wallpaperCropUtils) {
            WindowManagerService.this.mRoot.getDisplayContent(0).mWallpaperController.mWallpaperCropUtils = wallpaperCropUtils;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setWallpaperDisplayAddress(IBinder iBinder, DisplayAddress displayAddress) {
            if (CoreRune.FW_FOLD_WALLPAPER_POLICY) {
                WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowToken windowToken = WindowManagerService.this.mRoot.getWindowToken(iBinder);
                        if (windowToken != null && windowToken.asWallpaperToken() != null) {
                            WallpaperWindowToken asWallpaperToken = windowToken.asWallpaperToken();
                            asWallpaperToken.mDisplayAddress = displayAddress;
                            asWallpaperToken.mWmService.mExt.getClass();
                            throw null;
                        }
                        Slog.w("WindowManager", "setWallpaperDisplayAddress: non-existent wallpaper token=" + iBinder);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setWallpaperShowWhenLocked(IBinder iBinder, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowToken windowToken = WindowManagerService.this.mRoot.getWindowToken(iBinder);
                    if (windowToken != null && windowToken.asWallpaperToken() != null) {
                        WallpaperWindowToken asWallpaperToken = windowToken.asWallpaperToken();
                        if (z != asWallpaperToken.mShowWhenLocked) {
                            asWallpaperToken.mShowWhenLocked = z;
                            asWallpaperToken.toStringInfo(true);
                            asWallpaperToken.getParent().positionChildAt(z ? Integer.MIN_VALUE : Integer.MAX_VALUE, asWallpaperToken, false);
                            WallpaperController wallpaperController = asWallpaperToken.mDisplayContent.mWallpaperController;
                            if (wallpaperController.mWallpaperTokens.size() > 1) {
                                wallpaperController.mWallpaperTokens.sort(null);
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 5770211341769258866L, 0, "setWallpaperShowWhenLocked: non-existent wallpaper token: %s", String.valueOf(iBinder));
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void setWindowsForAccessibilityCallback(int i, WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback) {
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
        public final boolean shouldRestoreImeVisibility(IBinder iBinder) {
            WindowManagerService windowManagerService = WindowManagerService.this;
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) windowManagerService.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    Task task = windowState.getTask();
                    if (task == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    ActivityRecord activityRecord = windowState.mActivityRecord;
                    if (activityRecord == null || !activityRecord.mLastImeShown) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        TaskSnapshot taskSnapshot = windowManagerService.getTaskSnapshot(task.mTaskId, task.mUserId, false, false);
                        if (taskSnapshot == null || !taskSnapshot.hasImeSurface()) {
                            return false;
                        }
                    } else {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                    return true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final boolean shouldStayAwakeOnFold() {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mExt.getClass();
                    throw null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void showBootDialog(int i) {
            WindowManagerService.this.showBootMessage(null, true, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void showGlobalActions() {
            WindowManagerService.this.showGlobalActions();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void showImePostLayout(IBinder iBinder, ImeTracker.Token token) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    InputTarget inputTargetFromWindowTokenLocked = WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder);
                    if (inputTargetFromWindowTokenLocked == null) {
                        ImeTracker.forLogging().onFailed(token, 20);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    ImeTracker.forLogging().onProgress(token, 20);
                    if (inputTargetFromWindowTokenLocked.getDisplayId() == 2 && WindowManagerService.this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked() && WindowManagerService.this.mAtmService.mDexController.showDexImeOnDefaultDisplayLocked()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    InsetsControlTarget imeControlTarget = inputTargetFromWindowTokenLocked.getImeControlTarget();
                    WindowState window = imeControlTarget.getWindow();
                    (window != null ? window.getDisplayContent() : WindowManagerService.this.getDefaultDisplayContentLocked()).mInsetsStateController.getImeSourceProvider().scheduleShowImePostLayout(imeControlTarget, token);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot(Set set) {
            int i = WindowManagerService.MY_PID;
            return WindowManagerService.this.takeAssistScreenshot(set);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final Bitmap takeScreenshotToTargetWindowInternal(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
            WindowManagerService windowManagerService = WindowManagerService.this;
            if (!windowManagerService.checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "takeScreenshotToTargetWindow()", true)) {
                throw new SecurityException("Requires READ_FRAME_BUFFER permission");
            }
            ScreenshotResult takeScreenshotToTargetWindow = windowManagerService.mExt.mScreenshotController.takeScreenshotToTargetWindow(i, i2, z, rect, i3, i4, z2, Binder.getCallingUid() == 1000, false);
            if (takeScreenshotToTargetWindow != null) {
                return takeScreenshotToTargetWindow.getCapturedBitmap();
            }
            return null;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void unregisterAppTransitionListener(WindowManagerInternal.AppTransitionListener appTransitionListener, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.mAppTransition.mListeners.remove(appTransitionListener);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void unregisterOnWindowRemovedListener(WindowManagerInternal.OnWindowRemovedListener onWindowRemovedListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ((ArrayList) WindowManagerService.this.mOnWindowRemovedListeners).remove(onWindowRemovedListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void unregisterTaskSystemBarsListener(WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService.this.mTaskSystemBarsListenerController.mListeners.remove(taskSystemBarsListener);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void updateInputMethodTargetWindow(IBinder iBinder, IBinder iBinder2) {
            WindowState windowState;
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    InputTarget inputTargetFromWindowTokenLocked = WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder2);
                    if (inputTargetFromWindowTokenLocked != null) {
                        inputTargetFromWindowTokenLocked.getDisplayContent().updateImeInputAndControlTarget(inputTargetFromWindowTokenLocked);
                        if (android.view.inputmethod.Flags.refactorInsetsController() && (windowState = inputTargetFromWindowTokenLocked.getWindowState()) != null && windowState != inputTargetFromWindowTokenLocked.getDisplayContent().getImeHostOrFallback(windowState)) {
                            final int i = inputTargetFromWindowTokenLocked.getDisplayContent().mDisplayId;
                            WindowManagerService.this.mRoot.forAllDisplays(new Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    DisplayContent displayContent = (DisplayContent) obj;
                                    if (displayContent.mDisplayId != i) {
                                        displayContent.setImeInputTarget(null);
                                    }
                                }
                            });
                        }
                        DexController dexController = WindowManagerService.this.mAtmService.mDexController;
                        WindowState windowState2 = inputTargetFromWindowTokenLocked.getWindowState();
                        if (dexController.mLastInputMethodInputTarget != windowState2) {
                            dexController.mLastInputMethodInputTarget = windowState2;
                        }
                    }
                    boolean z = CoreRune.FW_TSP_STATE_CONTROLLER;
                    if (z) {
                        WindowManagerServiceExt windowManagerServiceExt = WindowManagerService.this.mExt;
                        WindowState windowState3 = inputTargetFromWindowTokenLocked != null ? inputTargetFromWindowTokenLocked.getWindowState() : null;
                        windowManagerServiceExt.getClass();
                        if (z) {
                            windowManagerServiceExt.mTspStateController.mImeTargetWindow = windowState3;
                        }
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
        public final void updateMirroredSurface(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.updateMirroredSurfaceFromDisplayManager();
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        Slog.w("WindowManager", "updateMirroredSurface: cannot find dc, #" + i);
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public final void waitForAllWindowsDrawn(Message message, long j, int i) {
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
                    if (WindowManagerService.this.mRoot.mDefaultDisplay.mDisplayUpdater.waitForTransition(message)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.waitForAllWindowsDrawn();
                    WindowManagerService.this.mWindowPlacerLocked.requestTraversal();
                    WindowManagerService.this.mH.removeMessages(24, displayContent);
                    if (displayContent.mWaitingForDrawn.isEmpty()) {
                        z = true;
                    } else {
                        if (Trace.isTagEnabled(32L)) {
                            for (int i2 = 0; i2 < displayContent.mWaitingForDrawn.size(); i2++) {
                                WindowManagerService windowManagerService = WindowManagerService.this;
                                WindowState windowState = (WindowState) displayContent.mWaitingForDrawn.get(i2);
                                windowManagerService.getClass();
                                String str = "waitForAllWindowsDrawn#" + ((Object) windowState.getWindowTag());
                                Trace.asyncTraceBegin(32L, str.substring(0, Math.min(127, str.length())), 0);
                            }
                        }
                        WindowManagerService.this.mWaitingForDrawnCallbacks.put(displayContent, message);
                        H h = WindowManagerService.this.mH;
                        h.removeMessages(24, displayContent);
                        h.sendMessageDelayed(h.obtainMessage(24, displayContent), j);
                        WindowManagerService.this.checkDrawnWindowsLocked();
                        z = false;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    if (z) {
                        message.sendToTarget();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    final class SettingsObserver extends ContentObserver {
        public final Uri mAnimationDurationScaleUri;
        public final Uri mDevEnableNonResizableMultiWindowUri;
        public final Uri mDisableSecureWindowsUri;
        public final Uri mDisplaySettingsPathUri;
        public final Uri mForceDesktopModeOnExternalDisplaysUri;
        public final Uri mForceResizableUri;
        public final Uri mFreeformWindowUri;
        public final Uri mImmersiveModeConfirmationsUri;
        public final Uri mMaximumObscuringOpacityForTouchUri;
        public final Uri mPolicyControlUri;
        public final Uri mScreenModeUri;
        public final Uri mTransitionAnimationScaleUri;
        public final Uri mWindowAnimationScaleUri;

        public SettingsObserver() {
            super(new Handler());
            Uri uriFor = Settings.Secure.getUriFor("accessibility_display_inversion_enabled");
            Uri uriFor2 = Settings.Global.getUriFor("window_animation_scale");
            this.mWindowAnimationScaleUri = uriFor2;
            Uri uriFor3 = Settings.Global.getUriFor("transition_animation_scale");
            this.mTransitionAnimationScaleUri = uriFor3;
            Uri uriFor4 = Settings.Global.getUriFor("animator_duration_scale");
            this.mAnimationDurationScaleUri = uriFor4;
            Uri uriFor5 = Settings.Secure.getUriFor("immersive_mode_confirmations");
            this.mImmersiveModeConfirmationsUri = uriFor5;
            Uri uriFor6 = Settings.Secure.getUriFor("disable_secure_windows");
            this.mDisableSecureWindowsUri = uriFor6;
            Uri uriFor7 = Settings.Global.getUriFor("policy_control");
            this.mPolicyControlUri = uriFor7;
            Uri uriFor8 = Settings.Global.getUriFor("force_desktop_mode_on_external_displays");
            this.mForceDesktopModeOnExternalDisplaysUri = uriFor8;
            Uri uriFor9 = Settings.Global.getUriFor("enable_freeform_support");
            this.mFreeformWindowUri = uriFor9;
            Uri uriFor10 = Settings.Global.getUriFor("force_resizable_activities");
            this.mForceResizableUri = uriFor10;
            Uri uriFor11 = Settings.Global.getUriFor("enable_non_resizable_multi_window");
            this.mDevEnableNonResizableMultiWindowUri = uriFor11;
            Uri uriFor12 = Settings.Global.getUriFor("wm_display_settings_path");
            this.mDisplaySettingsPathUri = uriFor12;
            Uri uriFor13 = Settings.Global.getUriFor("maximum_obscuring_opacity_for_touch");
            this.mMaximumObscuringOpacityForTouchUri = uriFor13;
            this.mScreenModeUri = Settings.System.getUriFor("screen_mode_setting");
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
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            int i = 1;
            r0 = true;
            boolean z2 = true;
            if (this.mImmersiveModeConfirmationsUri.equals(uri) || this.mPolicyControlUri.equals(uri)) {
                updateSystemUiSettings(true);
                return;
            }
            if (this.mForceDesktopModeOnExternalDisplaysUri.equals(uri)) {
                boolean z3 = Settings.Global.getInt(WindowManagerService.this.mContext.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0;
                WindowManagerService windowManagerService = WindowManagerService.this;
                if (windowManagerService.mForceDesktopModeOnExternalDisplays == z3) {
                    return;
                }
                WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        windowManagerService.mForceDesktopModeOnExternalDisplays = z3;
                        windowManagerService.mRoot.updateDisplayImePolicyCache();
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (this.mFreeformWindowUri.equals(uri)) {
                ContentResolver contentResolver = WindowManagerService.this.mContext.getContentResolver();
                if (!WindowManagerService.this.mContext.getPackageManager().hasSystemFeature("android.software.freeform_window_management") && Settings.Global.getInt(contentResolver, "enable_freeform_support", 0) == 0) {
                    z2 = false;
                }
                WindowManagerService windowManagerService2 = WindowManagerService.this;
                ActivityTaskManagerService activityTaskManagerService = windowManagerService2.mAtmService;
                if (activityTaskManagerService.mSupportsFreeformWindowManagement != z2) {
                    activityTaskManagerService.mSupportsFreeformWindowManagement = z2;
                    WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService2.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            WindowManagerService.this.mRoot.onSettingsRetrieved();
                        } finally {
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                return;
            }
            if (this.mForceResizableUri.equals(uri)) {
                WindowManagerService.this.mAtmService.mForceResizableActivities = Settings.Global.getInt(WindowManagerService.this.mContext.getContentResolver(), "force_resizable_activities", 0) != 0;
                return;
            }
            if (this.mDevEnableNonResizableMultiWindowUri.equals(uri)) {
                WindowManagerService.this.mAtmService.mDevEnableNonResizableMultiWindow = Settings.Global.getInt(WindowManagerService.this.mContext.getContentResolver(), "enable_non_resizable_multi_window", 0) != 0;
                return;
            }
            if (this.mDisplaySettingsPathUri.equals(uri)) {
                String string = Settings.Global.getString(WindowManagerService.this.mContext.getContentResolver(), "wm_display_settings_path");
                WindowManagerGlobalLock windowManagerGlobalLock3 = WindowManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock3) {
                    try {
                        WindowManagerService.this.mDisplayWindowSettingsProvider.setBaseSettingsFilePath(string);
                        WindowManagerService.this.mRoot.forAllDisplays(new WindowManagerService$$ExternalSyntheticLambda6(1, this));
                    } finally {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (this.mMaximumObscuringOpacityForTouchUri.equals(uri)) {
                updateMaximumObscuringOpacityForTouch();
                return;
            }
            if (this.mDisableSecureWindowsUri.equals(uri)) {
                updateDisableSecureWindows();
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
            WindowManagerService.this.mH.sendMessage(WindowManagerService.this.mH.obtainMessage(51, i, 0));
        }

        public final void updateDisableSecureWindows() {
            if (SystemProperties.getBoolean("ro.debuggable", false)) {
                try {
                    boolean z = Settings.Secure.getIntForUser(WindowManagerService.this.mContext.getContentResolver(), "disable_secure_windows", 0) != 0;
                    if (WindowManagerService.this.mDisableSecureWindows == z) {
                        return;
                    }
                    WindowManagerGlobalLock windowManagerGlobalLock = WindowManagerService.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            WindowManagerService.this.mDisableSecureWindows = z;
                            WindowManagerService.this.mRoot.refreshSecureSurfaceState();
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Settings.SettingNotFoundException unused) {
                }
            }
        }

        public final void updateMaximumObscuringOpacityForTouch() {
            ContentResolver contentResolver = WindowManagerService.this.mContext.getContentResolver();
            WindowManagerService.this.mMaximumObscuringOpacityForTouch = Settings.Global.getFloat(contentResolver, "maximum_obscuring_opacity_for_touch", 0.8f);
            if (WindowManagerService.this.mMaximumObscuringOpacityForTouch < FullScreenMagnificationGestureHandler.MAX_SCALE || WindowManagerService.this.mMaximumObscuringOpacityForTouch > 1.0f) {
                WindowManagerService.this.mMaximumObscuringOpacityForTouch = 0.8f;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x0060, code lost:
        
            r7 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0069, code lost:
        
            android.util.Slog.w("PolicyControl", "Error loading policy control, value=" + r3, r7);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateSystemUiSettings(boolean r7) {
            /*
                r6 = this;
                com.android.server.wm.WindowManagerService r0 = com.android.server.wm.WindowManagerService.this
                com.android.server.wm.WindowManagerGlobalLock r0 = r0.mGlobalLock
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
                monitor-enter(r0)
                r1 = 0
                if (r7 == 0) goto L3a
                com.android.server.wm.WindowManagerService r7 = com.android.server.wm.WindowManagerService.this     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.DisplayContent r7 = r7.getDefaultDisplayContentLocked()     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.DisplayPolicy r7 = r7.mDisplayPolicy     // Catch: java.lang.Throwable -> L38
                r7.getClass()     // Catch: java.lang.Throwable -> L38
                boolean r2 = android.view.ViewRootImpl.CLIENT_TRANSIENT     // Catch: java.lang.Throwable -> L38
                if (r2 != 0) goto L43
                boolean r2 = android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION     // Catch: java.lang.Throwable -> L38
                if (r2 == 0) goto L1f
                goto L43
            L1f:
                com.android.server.wm.WindowManagerService r2 = r7.mService     // Catch: java.lang.Throwable -> L38
                int r2 = r2.mCurrentUserId     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.ImmersiveModeConfirmation r7 = r7.mImmersiveModeConfirmation     // Catch: java.lang.Throwable -> L38
                android.content.Context r2 = r7.mContext     // Catch: java.lang.Throwable -> L38
                boolean r2 = com.android.server.wm.ImmersiveModeConfirmation.loadSetting(r2)     // Catch: java.lang.Throwable -> L38
                if (r2 == 0) goto L44
                boolean r3 = com.android.server.wm.ImmersiveModeConfirmation.sConfirmed     // Catch: java.lang.Throwable -> L38
                if (r3 == 0) goto L44
                com.android.server.wm.ImmersiveModeConfirmation$H r7 = r7.mHandler     // Catch: java.lang.Throwable -> L38
                r3 = 2
                r7.sendEmptyMessage(r3)     // Catch: java.lang.Throwable -> L38
                goto L44
            L38:
                r6 = move-exception
                goto L8c
            L3a:
                com.android.server.wm.WindowManagerService r7 = com.android.server.wm.WindowManagerService.this     // Catch: java.lang.Throwable -> L38
                int r2 = r7.mCurrentUserId     // Catch: java.lang.Throwable -> L38
                android.content.Context r7 = r7.mContext     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.ImmersiveModeConfirmation.loadSetting(r7)     // Catch: java.lang.Throwable -> L38
            L43:
                r2 = r1
            L44:
                com.android.server.wm.WindowManagerService r7 = com.android.server.wm.WindowManagerService.this     // Catch: java.lang.Throwable -> L38
                android.content.Context r7 = r7.mContext     // Catch: java.lang.Throwable -> L38
                r3 = 0
                android.content.ContentResolver r7 = r7.getContentResolver()     // Catch: java.lang.Throwable -> L60
                java.lang.String r4 = "policy_control"
                r5 = -2
                java.lang.String r3 = android.provider.Settings.Global.getStringForUser(r7, r4, r5)     // Catch: java.lang.Throwable -> L60
                java.lang.String r7 = com.android.server.wm.PolicyControl.sSettingValue     // Catch: java.lang.Throwable -> L60
                if (r7 == 0) goto L62
                boolean r7 = r7.equals(r3)     // Catch: java.lang.Throwable -> L60
                if (r7 == 0) goto L62
                goto L7c
            L60:
                r7 = move-exception
                goto L69
            L62:
                com.android.server.wm.PolicyControl.setFilters(r3)     // Catch: java.lang.Throwable -> L60
                com.android.server.wm.PolicyControl.sSettingValue = r3     // Catch: java.lang.Throwable -> L60
                r1 = 1
                goto L7c
            L69:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38
                java.lang.String r5 = "Error loading policy control, value="
                r4.<init>(r5)     // Catch: java.lang.Throwable -> L38
                r4.append(r3)     // Catch: java.lang.Throwable -> L38
                java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L38
                java.lang.String r4 = "PolicyControl"
                android.util.Slog.w(r4, r3, r7)     // Catch: java.lang.Throwable -> L38
            L7c:
                r7 = r2 | r1
                if (r7 == 0) goto L87
                com.android.server.wm.WindowManagerService r6 = com.android.server.wm.WindowManagerService.this     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.WindowSurfacePlacer r6 = r6.mWindowPlacerLocked     // Catch: java.lang.Throwable -> L38
                r6.requestTraversal()     // Catch: java.lang.Throwable -> L38
            L87:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                return
            L8c:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.SettingsObserver.updateSystemUiSettings(boolean):void");
        }
    }

    public interface WindowChangeListener {
    }

    class WindowContainerInfo {
        public final WindowContainerToken mToken;
        public final int mUid;

        public WindowContainerInfo(int i, WindowContainerToken windowContainerToken) {
            this.mUid = i;
            this.mToken = windowContainerToken;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0091, code lost:
    
        r9 = new android.util.proto.ProtoOutputStream(r8);
        r8 = r7.mGlobalLock;
        boostPriorityForLockedSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x009b, code lost:
    
        monitor-enter(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009c, code lost:
    
        r7.dumpDebugLocked(0, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
    
        monitor-exit(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a0, code lost:
    
        resetPriorityAfterLockedSection();
        r9.flush();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a8, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ad, code lost:
    
        throw r7;
     */
    /* renamed from: -$$Nest$mdoDump, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m1074$$Nest$mdoDump(com.android.server.wm.WindowManagerService r7, java.io.FileDescriptor r8, java.io.PrintWriter r9, java.lang.String[] r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 1023
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.m1074$$Nest$mdoDump(com.android.server.wm.WindowManagerService, java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[], boolean):void");
    }

    /* renamed from: -$$Nest$mnotifyWindowsChanged, reason: not valid java name */
    public static void m1075$$Nest$mnotifyWindowsChanged(WindowManagerService windowManagerService) {
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (windowManagerService.mWindowChangeListeners.isEmpty()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowChangeListener[] windowChangeListenerArr = (WindowChangeListener[]) windowManagerService.mWindowChangeListeners.toArray(new WindowChangeListener[windowManagerService.mWindowChangeListeners.size()]);
                resetPriorityAfterLockedSection();
                for (WindowChangeListener windowChangeListener : windowChangeListenerArr) {
                    ViewServer.ViewServerWorker viewServerWorker = (ViewServer.ViewServerWorker) windowChangeListener;
                    synchronized (viewServerWorker) {
                        viewServerWorker.mNeedWindowListUpdate = true;
                        viewServerWorker.notifyAll();
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mshowStrictModeViolation, reason: not valid java name */
    public static void m1076$$Nest$mshowStrictModeViolation(WindowManagerService windowManagerService, int i, int i2) {
        windowManagerService.getClass();
        boolean z = i != 0;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (z) {
                try {
                    RootWindowContainer rootWindowContainer = windowManagerService.mRoot;
                    rootWindowContainer.getClass();
                    if (rootWindowContainer.getWindow(new RootWindowContainer$$ExternalSyntheticLambda13(i2, 2)) == null) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (windowManagerService.mStrictModeFlash == null) {
                windowManagerService.mStrictModeFlash = new StrictModeFlash(windowManagerService.getDefaultDisplayContentLocked(), windowManagerService.mTransaction);
            }
            windowManagerService.mStrictModeFlash.setVisibility(windowManagerService.mTransaction, z);
            windowManagerService.mTransaction.apply();
            resetPriorityAfterLockedSection();
        }
    }

    static {
        FeatureInfo featureInfo = (FeatureInfo) SystemConfig.getInstance().mAvailableFeatures.get("android.hardware.type.automotive");
        sEnableShellTransitions = (featureInfo == null || featureInfo.version < 0) ? true : SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);
        ENABLE_FIXED_ROTATION_TRANSFORM = SystemProperties.getBoolean("persist.wm.fixed_rotation_transform", true);
        sThreadPriorityBooster = new WindowManagerThreadPriorityBooster();
    }

    public WindowManagerService(Context context, InputManagerService inputManagerService, boolean z, WindowManagerPolicy windowManagerPolicy, ActivityTaskManagerService activityTaskManagerService, DisplayWindowSettingsProvider displayWindowSettingsProvider, Supplier supplier, Function function) {
        DisplayAreaPolicy.DefaultProvider defaultProvider;
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.wm.WindowManagerService.2
            public final /* synthetic */ WindowManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i) {
                    case 0:
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED")) {
                            this.this$0.mKeyguardDisableHandler.updateKeyguardEnabled(getSendingUserId());
                            return;
                        }
                        return;
                    case 1:
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        final boolean equals = "android.intent.action.PACKAGES_SUSPENDED".equals(intent.getAction());
                        WindowManagerService windowManagerService = this.this$0;
                        final ArraySet arraySet = new ArraySet(Arrays.asList(stringArrayExtra));
                        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                RootWindowContainer rootWindowContainer = windowManagerService.mRoot;
                                rootWindowContainer.getClass();
                                rootWindowContainer.forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda39
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ArraySet arraySet2 = arraySet;
                                        boolean z2 = equals;
                                        WindowState windowState = (WindowState) obj;
                                        if (arraySet2.contains(windowState.mAttrs.packageName)) {
                                            windowState.setHiddenWhileSuspended(z2);
                                        }
                                    }
                                }, false);
                            } finally {
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    default:
                        final int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (SemPersonaManager.isSecureFolderId(intExtra)) {
                            WindowManagerService windowManagerService2 = this.this$0;
                            final boolean shouldConfirmCredentials = windowManagerService2.mAmInternal.shouldConfirmCredentials(intExtra);
                            WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService2.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock2) {
                                try {
                                    RootWindowContainer rootWindowContainer2 = windowManagerService2.mRoot;
                                    rootWindowContainer2.getClass();
                                    rootWindowContainer2.forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda34
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i2 = intExtra;
                                            boolean z2 = shouldConfirmCredentials;
                                            WindowState windowState = (WindowState) obj;
                                            if (i2 == UserHandle.getUserId(windowState.mOwnerUid)) {
                                                windowState.setHiddenWhileProfileLockedStateLocked(z2);
                                            }
                                        }
                                    }, false);
                                } finally {
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                }
            }
        };
        this.mPriorityDumper = new AnonymousClass3(this);
        final int i2 = 1;
        this.mShowAlertWindowNotifications = true;
        this.mSessions = new ArraySet();
        this.mWindowMap = new HashMap();
        this.mInputToWindowMap = new HashMap();
        this.mResizingWindows = new ArrayList();
        this.mFrameChangingWindows = new ArrayList();
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
        this.mDisplayChangeController = null;
        this.mDisplayChangeControllerDeath = new IBinder.DeathRecipient() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda32
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                WindowManagerService.this.mDisplayChangeController = null;
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
        H h = new H();
        this.mH = h;
        this.mAnimationHandler = new Handler(AnimationThread.getHandler().getLooper());
        this.mMaximumObscuringOpacityForTouch = 0.8f;
        this.mWindowContextListenerController = new WindowContextListenerController();
        ContentRecordingController contentRecordingController = new ContentRecordingController();
        contentRecordingController.mSession = null;
        contentRecordingController.mDisplayContent = null;
        this.mContentRecordingController = contentRecordingController;
        this.mSurfaceSyncGroupController = new SurfaceSyncGroupController();
        this.mTrustedPresentationListenerController = new TrustedPresentationListenerController();
        this.mWindowAnimationScaleSetting = 1.0f;
        this.mTransitionAnimationScaleSetting = 1.0f;
        this.mAnimatorDurationScaleSetting = 1.0f;
        this.mAnimationsDisabled = false;
        this.mPointerLocationEnabled = false;
        this.mFrozenDisplayId = -1;
        this.mAnimationTransferMap = new ArrayMap();
        this.mWindowChangeListeners = new ArrayList();
        this.mWindowsChanged = false;
        this.mSensitiveContentProtectionSessionId = 0L;
        this.mSensitiveContentPackages = new SensitiveContentPackages();
        this.mCaptureBlockedToastShownUids = new IntArray();
        this.mActivityManagerAppTransitionNotifier = new AnonymousClass4();
        this.mAppFreezeListeners = new ArrayList();
        this.mDisableSecureWindows = false;
        this.mInputManagerCallback = new InputManagerCallback(this);
        this.mShellRemoteAnimRunner = new AnonymousClass11();
        this.PENDING_INTENT_AFTER_UNLOCK_ALLOW_MAP = "user".equals(Build.TYPE) ? Collections.unmodifiableMap(new HashMap() { // from class: com.android.server.wm.WindowManagerService.12
            {
                put("com.sec.android.app.shealth", new ArrayList(Arrays.asList("14aafbdad4dd99765346a1de191320328465b8420713bc22cc4f8b211b87cd3a")));
                put("jp.co.rakuten.pay", new ArrayList(Collections.singletonList("b0c08d3318f7c6f5be0c62f47cab1b59f5f5f13d7da0d547d041fb51cc20b0ec")));
            }
        }) : Collections.unmodifiableMap(new HashMap() { // from class: com.android.server.wm.WindowManagerService.13
            {
                put("com.sec.android.app.shealth", new ArrayList(Arrays.asList("699b10e8636d1d5f03b5ed04b10d98898e4e292ba42d4a371bb546f8eeb42650", "c88c9048f6d0fe9d8561926240f2ccc1982e24721150929350384659aa54aef6")));
                put("jp.co.rakuten.pay", new ArrayList(Collections.singletonList("b0c08d3318f7c6f5be0c62f47cab1b59f5f5f13d7da0d547d041fb51cc20b0ec")));
            }
        });
        LockGuard.installLock(this, 5, false);
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mGlobalLock = windowManagerGlobalLock;
        this.mAtmService = activityTaskManagerService;
        this.mContext = context;
        WindowManagerFlags windowManagerFlags = new WindowManagerFlags();
        this.mFlags = windowManagerFlags;
        this.mIsPc = context.getPackageManager().hasSystemFeature("android.hardware.type.pc");
        this.mAllowBootMessages = z;
        context.getResources().getBoolean(R.bool.config_skipActivityRelaunchWhenDocking);
        this.mHasPermanentDpad = context.getResources().getBoolean(R.bool.config_isDesktopModeSupported);
        this.mDrawLockTimeoutMillis = context.getResources().getInteger(R.integer.config_letterboxDefaultPositionForBookModeReachability);
        this.mAllowAnimationsInLowPowerMode = context.getResources().getBoolean(R.bool.config_allowEscrowTokenForTrustAgent);
        this.mMaxUiWidth = context.getResources().getInteger(R.integer.config_notificationsBatteryLowBehavior);
        this.mSupportsHighPerfTransitions = context.getResources().getBoolean(R.bool.config_disableLockscreenByDefault);
        this.mDisableTransitionAnimation = context.getResources().getBoolean(R.bool.config_displayBrightnessBucketsInDoze);
        this.mPerDisplayFocusEnabled = context.getResources().getBoolean(R.bool.config_perDisplayFocusEnabled);
        this.mAssistantOnTopOfDream = context.getResources().getBoolean(R.bool.config_assistantOnTopOfDream);
        this.mSkipActivityRelaunchWhenDocking = context.getResources().getBoolean(R.bool.config_strongAuthRequiredOnBoot);
        boolean z2 = context.getResources().getBoolean(R.bool.config_defaultEmergencyGestureEnabled) && windowManagerFlags.mAllowsScreenSizeDecoupledFromStatusBarAndCutout;
        if (windowManagerFlags.mInsetsDecoupledConfiguration) {
            this.mDecorTypes = 0;
            this.mConfigTypes = 0;
        } else {
            this.mDecorTypes = WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars();
            this.mConfigTypes = WindowInsets.Type.displayCutout() | WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
        }
        if (!z2 || windowManagerFlags.mInsetsDecoupledConfiguration) {
            this.mOverrideConfigTypes = WindowInsets.Type.displayCutout() | WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
            this.mOverrideDecorTypes = WindowInsets.Type.displayCutout() | WindowInsets.Type.navigationBars();
        } else {
            this.mOverrideConfigTypes = 0;
            this.mOverrideDecorTypes = 0;
        }
        final ContextImpl systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
        Supplier supplier2 = new Supplier() { // from class: com.android.server.wm.AppCompatConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                int i3 = i;
                Context context2 = systemUiContext;
                switch (i3) {
                    case 0:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, false));
                    case 1:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, false));
                    case 2:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, true));
                    default:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, true));
                }
            }
        };
        Supplier supplier3 = new Supplier() { // from class: com.android.server.wm.AppCompatConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                int i3 = i2;
                Context context2 = systemUiContext;
                switch (i3) {
                    case 0:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, false));
                    case 1:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, false));
                    case 2:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, true));
                    default:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, true));
                }
            }
        };
        final int i3 = 2;
        final int i4 = 3;
        this.mAppCompatConfiguration = new AppCompatConfiguration(systemUiContext, new AppCompatConfigurationPersister(supplier2, supplier3, new Supplier() { // from class: com.android.server.wm.AppCompatConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i3;
                Context context2 = systemUiContext;
                switch (i32) {
                    case 0:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, false));
                    case 1:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, false));
                    case 2:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, true));
                    default:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, true));
                }
            }
        }, new Supplier() { // from class: com.android.server.wm.AppCompatConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                int i32 = i4;
                Context context2 = systemUiContext;
                switch (i32) {
                    case 0:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, false));
                    case 1:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, false));
                    case 2:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxHorizontalReachabilityPositionFromConfig(context2, true));
                    default:
                        return Integer.valueOf(AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(context2, true));
                }
            }
        }, Environment.getDataSystemDirectory(), new PersisterQueue(), null, "letterbox_config"));
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
        this.mSyncEngine = new BLASTSyncEngine(this, h);
        this.mWindowPlacerLocked = new WindowSurfacePlacer(this);
        SnapshotController snapshotController = new SnapshotController(this);
        this.mSnapshotController = snapshotController;
        this.mTaskSnapshotController = snapshotController.mTaskSnapshotController;
        this.mWindowTracing = new WindowTracing(new File("/data/misc/wmtrace/wm_trace.winscope"), this, Choreographer.getInstance());
        if (android.tracing.Flags.perfettoTransitionTracing()) {
            this.mTransitionTracer = new PerfettoTransitionTracer();
        } else {
            this.mTransitionTracer = new LegacyTransitionTracer();
        }
        LocalServices.addService(WindowManagerPolicy.class, this.mPolicy);
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mKeyguardDisableHandler = new KeyguardDisableHandler(new KeyguardDisableHandler.AnonymousClass2(this.mPolicy, (UserManagerInternal) LocalServices.getService(UserManagerInternal.class)), h);
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        PowerManagerInternal powerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mPowerManagerInternal = powerManagerInternal;
        if (powerManagerInternal != null) {
            powerManagerInternal.registerLowPowerModeObserver(new PowerManagerInternal.LowPowerModeListener() { // from class: com.android.server.wm.WindowManagerService.6
                public final int getServiceType() {
                    return 3;
                }

                public final void onLowPowerModeChanged(PowerSaveState powerSaveState) {
                    WindowManagerGlobalLock windowManagerGlobalLock2 = WindowManagerService.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            boolean z3 = powerSaveState.batterySaverEnabled;
                            WindowManagerService windowManagerService = WindowManagerService.this;
                            if (windowManagerService.mAnimationsDisabled != z3 && !windowManagerService.mAllowAnimationsInLowPowerMode) {
                                windowManagerService.mAnimationsDisabled = z3;
                                windowManagerService.mH.obtainMessage(34, null).sendToTarget();
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            });
            this.mAnimationsDisabled = powerManagerInternal.getLowPowerState(3).batterySaverEnabled;
        }
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "SCREEN_FROZEN");
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
            public final void onOpChanged(int i5, String str) {
                WindowManagerService windowManagerService = WindowManagerService.this;
                WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        RootWindowContainer rootWindowContainer = windowManagerService.mRoot;
                        rootWindowContainer.getClass();
                        rootWindowContainer.forAllWindows((Consumer) new RootWindowContainer$$ExternalSyntheticLambda35(0), false);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        };
        appOpsManager.startWatchingMode(24, (String) null, (AppOpsManager.OnOpChangedListener) onOpChangedInternalListener);
        appOpsManager.startWatchingMode(45, (String) null, (AppOpsManager.OnOpChangedListener) onOpChangedInternalListener);
        this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mTestUtilityService = (TestUtilityService) LocalServices.getService(TestUtilityService.class);
        IntentFilter m = DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGES_SUSPENDED", "android.intent.action.PACKAGES_UNSUSPENDED");
        final int i5 = 1;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.wm.WindowManagerService.2
            public final /* synthetic */ WindowManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i5) {
                    case 0:
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED")) {
                            this.this$0.mKeyguardDisableHandler.updateKeyguardEnabled(getSendingUserId());
                            return;
                        }
                        return;
                    case 1:
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        final boolean equals = "android.intent.action.PACKAGES_SUSPENDED".equals(intent.getAction());
                        WindowManagerService windowManagerService = this.this$0;
                        final ArraySet arraySet = new ArraySet(Arrays.asList(stringArrayExtra));
                        WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                RootWindowContainer rootWindowContainer = windowManagerService.mRoot;
                                rootWindowContainer.getClass();
                                rootWindowContainer.forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda39
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ArraySet arraySet2 = arraySet;
                                        boolean z22 = equals;
                                        WindowState windowState = (WindowState) obj;
                                        if (arraySet2.contains(windowState.mAttrs.packageName)) {
                                            windowState.setHiddenWhileSuspended(z22);
                                        }
                                    }
                                }, false);
                            } finally {
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    default:
                        final int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (SemPersonaManager.isSecureFolderId(intExtra)) {
                            WindowManagerService windowManagerService2 = this.this$0;
                            final boolean shouldConfirmCredentials = windowManagerService2.mAmInternal.shouldConfirmCredentials(intExtra);
                            WindowManagerGlobalLock windowManagerGlobalLock22 = windowManagerService2.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock22) {
                                try {
                                    RootWindowContainer rootWindowContainer2 = windowManagerService2.mRoot;
                                    rootWindowContainer2.getClass();
                                    rootWindowContainer2.forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda34
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i22 = intExtra;
                                            boolean z22 = shouldConfirmCredentials;
                                            WindowState windowState = (WindowState) obj;
                                            if (i22 == UserHandle.getUserId(windowState.mOwnerUid)) {
                                                windowState.setHiddenWhileProfileLockedStateLocked(z22);
                                            }
                                        }
                                    }, false);
                                } finally {
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                }
            }
        };
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver2, userHandle, m, null, null);
        this.mWindowAnimationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(context.getContentResolver(), "window_animation_scale", this.mWindowAnimationScaleSetting));
        this.mTransitionAnimationScaleSetting = WindowManager.fixScale(Settings.Global.getFloat(context.getContentResolver(), "transition_animation_scale", context.getResources().getFloat(R.dimen.config_letterboxDefaultMinAspectRatioForUnresizableApps)));
        float fixScale = WindowManager.fixScale(Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", this.mAnimatorDurationScaleSetting));
        this.mAnimatorDurationScaleSetting = fixScale;
        ValueAnimator.setDurationScale(fixScale);
        this.mForceDesktopModeOnExternalDisplays = Settings.Global.getInt(contentResolver, "force_desktop_mode_on_external_displays", 0) != 0;
        String string = Settings.Global.getString(contentResolver, "wm_display_settings_path");
        this.mDisplayWindowSettingsProvider = displayWindowSettingsProvider;
        if (string != null) {
            displayWindowSettingsProvider.setBaseSettingsFilePath(string);
        }
        this.mDisplayWindowSettings = new DisplayWindowSettings(this, displayWindowSettingsProvider);
        context.registerReceiverAsUser(broadcastReceiver, userHandle, BatteryService$$ExternalSyntheticOutline0.m("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), null, null);
        final int i6 = 2;
        context.registerReceiverAsUser(new BroadcastReceiver(this) { // from class: com.android.server.wm.WindowManagerService.2
            public final /* synthetic */ WindowManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i6) {
                    case 0:
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED")) {
                            this.this$0.mKeyguardDisableHandler.updateKeyguardEnabled(getSendingUserId());
                            return;
                        }
                        return;
                    case 1:
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        final boolean equals = "android.intent.action.PACKAGES_SUSPENDED".equals(intent.getAction());
                        WindowManagerService windowManagerService = this.this$0;
                        final ArraySet arraySet = new ArraySet(Arrays.asList(stringArrayExtra));
                        WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                RootWindowContainer rootWindowContainer = windowManagerService.mRoot;
                                rootWindowContainer.getClass();
                                rootWindowContainer.forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda39
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ArraySet arraySet2 = arraySet;
                                        boolean z22 = equals;
                                        WindowState windowState = (WindowState) obj;
                                        if (arraySet2.contains(windowState.mAttrs.packageName)) {
                                            windowState.setHiddenWhileSuspended(z22);
                                        }
                                    }
                                }, false);
                            } finally {
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    default:
                        final int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (SemPersonaManager.isSecureFolderId(intExtra)) {
                            WindowManagerService windowManagerService2 = this.this$0;
                            final boolean shouldConfirmCredentials = windowManagerService2.mAmInternal.shouldConfirmCredentials(intExtra);
                            WindowManagerGlobalLock windowManagerGlobalLock22 = windowManagerService2.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock22) {
                                try {
                                    RootWindowContainer rootWindowContainer2 = windowManagerService2.mRoot;
                                    rootWindowContainer2.getClass();
                                    rootWindowContainer2.forAllWindows(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda34
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i22 = intExtra;
                                            boolean z22 = shouldConfirmCredentials;
                                            WindowState windowState = (WindowState) obj;
                                            if (i22 == UserHandle.getUserId(windowState.mOwnerUid)) {
                                                windowState.setHiddenWhileProfileLockedStateLocked(z22);
                                            }
                                        }
                                    }, false);
                                } finally {
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        return;
                }
            }
        }, userHandle, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.DEVICE_LOCKED_CHANGED"), null, null);
        this.mLatencyTracker = LatencyTracker.getInstance(context);
        this.mSettingsObserver = new SettingsObserver();
        this.mSurfaceAnimationRunner = new SurfaceAnimationRunner(null, null, (SurfaceControl.Transaction) supplier.get(), powerManagerInternal);
        this.mAllowTheaterModeWakeFromLayout = context.getResources().getBoolean(R.bool.config_alwaysScaleWallpaper);
        this.mTaskPositioningController = new TaskPositioningController(this);
        this.mDragDropController = new DragDropController(this, h.getLooper());
        Resources resources = context.getResources();
        DeviceConfigInterface deviceConfigInterface = DeviceConfigInterface.REAL;
        this.mHighRefreshRateDenylist = new HighRefreshRateDenylist(resources, deviceConfigInterface);
        WindowManagerConstants windowManagerConstants = new WindowManagerConstants(windowManagerGlobalLock, new Runnable() { // from class: com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                WindowManagerService.this.mRoot.forAllDisplays(new WindowManagerConstants$$ExternalSyntheticLambda3());
            }
        }, deviceConfigInterface);
        this.mConstants = windowManagerConstants;
        HandlerExecutor handlerExecutor = new HandlerExecutor(h);
        windowManagerConstants.mDeviceConfig.addOnPropertiesChangedListener("android", handlerExecutor, windowManagerConstants.mListenerAndroid);
        windowManagerConstants.mDeviceConfig.addOnPropertiesChangedListener("window_manager", handlerExecutor, windowManagerConstants.mListenerWindowManager);
        windowManagerConstants.mSystemGestureExclusionLogDebounceTimeoutMillis = windowManagerConstants.mDeviceConfig.getLong("window_manager", "system_gesture_exclusion_log_debounce_millis", 0L);
        windowManagerConstants.mSystemGestureExclusionLimitDp = Math.max(200, windowManagerConstants.mDeviceConfig.getInt("android", "system_gesture_exclusion_limit_dp", 0));
        windowManagerConstants.mSystemGestureExcludedByPreQStickyImmersive = windowManagerConstants.mDeviceConfig.getBoolean("android", "system_gestures_excluded_by_pre_q_sticky_immersive", false);
        LocalServices.addService(WindowManagerInternal.class, new LocalService());
        LocalServices.addService(ImeTargetVisibilityPolicyImpl.class, new ImeTargetVisibilityPolicyImpl());
        this.mEmbeddedWindowController = new EmbeddedWindowController(activityTaskManagerService, inputManagerService);
        String string2 = context.getResources().getString(R.string.display_manager_overlay_display_title);
        if (TextUtils.isEmpty(string2)) {
            defaultProvider = new DisplayAreaPolicy.DefaultProvider();
        } else {
            try {
                defaultProvider = (DisplayAreaPolicy.DefaultProvider) Class.forName(string2).newInstance();
            } catch (ClassCastException | ReflectiveOperationException e) {
                throw new IllegalStateException(XmlUtils$$ExternalSyntheticOutline0.m("Couldn't instantiate class ", string2, " for config_deviceSpecificDisplayAreaPolicyProvider: make sure it has a public zero-argument constructor and implements DisplayAreaPolicy.Provider"), e);
            }
        }
        this.mDisplayAreaPolicyProvider = defaultProvider;
        this.mDisplayHashController = new DisplayHashController(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.Lighting, 0, 0);
        float dimension = obtainStyledAttributes.getDimension(3, FullScreenMagnificationGestureHandler.MAX_SCALE);
        float dimension2 = obtainStyledAttributes.getDimension(4, FullScreenMagnificationGestureHandler.MAX_SCALE);
        float dimension3 = obtainStyledAttributes.getDimension(2, FullScreenMagnificationGestureHandler.MAX_SCALE);
        float f = obtainStyledAttributes.getFloat(0, FullScreenMagnificationGestureHandler.MAX_SCALE);
        float f2 = obtainStyledAttributes.getFloat(1, FullScreenMagnificationGestureHandler.MAX_SCALE);
        obtainStyledAttributes.recycle();
        SurfaceControl.setGlobalShadowSettings(new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f2}, dimension, dimension2, dimension3);
        this.mAnrController = new AnrController(this);
        this.mStartingSurfaceController = new StartingSurfaceController(this);
        this.mBlurController = new BlurController(context, powerManager);
        this.mTaskFpsCallbackController = new TaskFpsCallbackController();
        this.mAccessibilityController = new AccessibilityController(this);
        this.mScreenRecordingCallbackController = new ScreenRecordingCallbackController(this);
        this.mKnoxRemoteScreenCallbackController = new KnoxRemoteScreenCallbackController(this);
        SystemPerformanceHinter systemPerformanceHinter = new SystemPerformanceHinter(context, new SystemPerformanceHinter.DisplayRootProvider() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda33
            public final SurfaceControl getRootForDisplay(int i7) {
                SurfaceControl surfaceControl;
                WindowManagerService windowManagerService = WindowManagerService.this;
                WindowManagerGlobalLock windowManagerGlobalLock2 = windowManagerService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        DisplayContent displayContent = windowManagerService.mRoot.getDisplayContent(i7);
                        surfaceControl = displayContent == null ? null : displayContent.getSurfaceControl();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return surfaceControl;
            }
        }, supplier);
        this.mSystemPerformanceHinter = systemPerformanceHinter;
        systemPerformanceHinter.mTraceTag = 32L;
    }

    public static void boostPriorityForLockedSection() {
        sThreadPriorityBooster.boost();
    }

    public static int createSurfaceControl(SurfaceControl surfaceControl, int i, WindowState windowState, WindowStateAnimator windowStateAnimator) {
        if (!windowState.mHasSurface) {
            i |= 2;
        }
        try {
            Trace.traceBegin(32L, "createSurfaceControl");
            WindowSurfaceController createSurfaceLocked = windowStateAnimator.createSurfaceLocked();
            if (createSurfaceLocked != null) {
                surfaceControl.copyFrom(createSurfaceLocked.mSurfaceControl, "WindowSurfaceController.getSurfaceControl");
                if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_SHOW_TRANSACTIONS, 6555160513135851764L, 0, null, String.valueOf(surfaceControl));
                }
            } else {
                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -196459205494031145L, 0, "Failed to create surface control for %s", String.valueOf(windowState));
                }
                surfaceControl.release();
            }
            return i;
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public static int dipToPixel(int i, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(1, i, displayMetrics);
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

    public static boolean isSystemSecure() {
        return "1".equals(SystemProperties.get("ro.secure", "1")) && "0".equals(SystemProperties.get("ro.debuggable", "0"));
    }

    public static WindowManagerService main(Context context, InputManagerService inputManagerService, boolean z, WindowManagerPolicy windowManagerPolicy, ActivityTaskManagerService activityTaskManagerService) {
        WindowManagerService main = main(context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, new DisplayWindowSettingsProvider(new DisplayWindowSettingsProvider.AtomicFileStorage(DisplayWindowSettingsProvider.getVendorSettingsFile()), new DisplayWindowSettingsProvider.AtomicFileStorage(new AtomicFile(new File(Environment.getDataDirectory(), "system/display_settings.xml"), "wm-displays"))), new WindowManagerService$$ExternalSyntheticLambda13(), new WindowManagerService$$ExternalSyntheticLambda14());
        WindowManagerGlobal.setWindowManagerServiceForSystemProcess(main);
        return main;
    }

    public static WindowManagerService main(final Context context, final InputManagerService inputManagerService, final boolean z, final WindowManagerPolicy windowManagerPolicy, final ActivityTaskManagerService activityTaskManagerService, final DisplayWindowSettingsProvider displayWindowSettingsProvider, final Supplier supplier, final Function function) {
        final WindowManagerService[] windowManagerServiceArr = new WindowManagerService[1];
        DisplayThread.getHandler().runWithScissors(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                windowManagerServiceArr[0] = new WindowManagerService(context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, displayWindowSettingsProvider, supplier, function);
            }
        }, 0L);
        return windowManagerServiceArr[0];
    }

    public static void notifyReceiverWithEmptyBundle(IResultReceiver iResultReceiver) {
        try {
            iResultReceiver.send(0, Bundle.EMPTY);
        } catch (RemoteException unused) {
            if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[4]) {
                ProtoLogImpl_54989576.e(ProtoLogGroup.WM_ERROR, 1010635158502326025L, 0, "unable to call receiver for empty keyboard shortcuts", null);
            }
        }
    }

    public static void resetPriorityAfterLockedSection() {
        sThreadPriorityBooster.reset();
    }

    public static void traceEndWaitingForWindowDrawn(WindowState windowState) {
        String str = "waitForAllWindowsDrawn#" + ((Object) windowState.getWindowTag());
        Trace.asyncTraceEnd(32L, str.substring(0, Math.min(127, str.length())), 0);
    }

    public final void addKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE", "android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE permission required to subscribe to keyguard locked state changes");
        if (this.mKeyguardLockedStateListeners.register(iKeyguardLockedStateListener)) {
            return;
        }
        Slog.w("WindowManager", "Failed to register listener: " + iKeyguardLockedStateListener);
    }

    public final SurfaceControl addShellRoot(int i, IWindow iWindow, int i2) {
        addShellRoot_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return null;
                    }
                    SurfaceControl addShellRoot = displayContent.addShellRoot(iWindow, i2);
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return addShellRoot;
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

    public final boolean addToSurfaceSyncGroup(IBinder iBinder, boolean z, final ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) {
        SurfaceSyncGroup surfaceSyncGroup;
        SurfaceSyncGroupController surfaceSyncGroupController = this.mSurfaceSyncGroupController;
        synchronized (surfaceSyncGroupController.mLock) {
            try {
                SurfaceSyncGroupController.SurfaceSyncGroupData surfaceSyncGroupData = (SurfaceSyncGroupController.SurfaceSyncGroupData) surfaceSyncGroupController.mSurfaceSyncGroups.get(iBinder);
                if (surfaceSyncGroupData == null) {
                    surfaceSyncGroup = new SurfaceSyncGroup("SurfaceSyncGroupController-" + iBinder.hashCode());
                    if (iSurfaceSyncGroupCompletedListener != null) {
                        surfaceSyncGroup.addSyncCompleteCallback(new SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new Runnable() { // from class: com.android.server.wm.SurfaceSyncGroupController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    iSurfaceSyncGroupCompletedListener.onSurfaceSyncGroupComplete();
                                } catch (RemoteException unused) {
                                }
                            }
                        });
                    }
                    surfaceSyncGroupController.mSurfaceSyncGroups.put(iBinder, new SurfaceSyncGroupController.SurfaceSyncGroupData(Binder.getCallingUid(), surfaceSyncGroup));
                } else {
                    surfaceSyncGroup = surfaceSyncGroupData.mSurfaceSyncGroup;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        ITransactionReadyCallback createTransactionReadyCallback = surfaceSyncGroup.createTransactionReadyCallback(z);
        if (createTransactionReadyCallback == null) {
            return false;
        }
        addToSurfaceSyncGroupResult.mParentSyncGroup = surfaceSyncGroup.mISurfaceSyncGroup;
        addToSurfaceSyncGroupResult.mTransactionReadyCallback = createTransactionReadyCallback;
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int addWindow(com.android.server.wm.Session r42, android.view.IWindow r43, android.view.WindowManager.LayoutParams r44, int r45, int r46, int r47, int r48, android.view.InputChannel r49, android.view.InsetsState r50, android.view.InsetsSourceControl.Array r51, android.graphics.Rect r52, float[] r53) {
        /*
            Method dump skipped, instructions count: 2608
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.addWindow(com.android.server.wm.Session, android.view.IWindow, android.view.WindowManager$LayoutParams, int, int, int, int, android.view.InputChannel, android.view.InsetsState, android.view.InsetsSourceControl$Array, android.graphics.Rect, float[]):int");
    }

    public final void addWindowChangeListener(WindowChangeListener windowChangeListener) {
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

    public final void addWindowToken(IBinder iBinder, int i, int i2, Bundle bundle) {
        if (!checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "addWindowToken()", true)) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2, null);
                if (displayContentOrCreate == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 4547566763172245740L, 4, "addWindowToken: Attempted to add token: %s for non-exiting displayId=%d", String.valueOf(iBinder), Long.valueOf(i2));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowToken windowToken = displayContentOrCreate.getWindowToken(iBinder);
                if (windowToken != null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -972832559831959983L, 16, "addWindowToken: Attempted to add binder token: %s for already created window token: %s displayId=%d", String.valueOf(iBinder), String.valueOf(windowToken), Long.valueOf(i2));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (i == 2013) {
                    new WallpaperWindowToken(this, iBinder, displayContentOrCreate, bundle);
                } else if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && i == 2632) {
                    displayContentOrCreate.mTransientLaunchOverlayTokens.add(new TransientLaunchOverlayToken(this, iBinder, 2632, true, displayContentOrCreate, true, false, false, bundle));
                } else {
                    new WindowToken(this, iBinder, i, true, displayContentOrCreate, true, false, false, bundle);
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyForcedPropertiesForDefaultDisplay() {
        /*
            Method dump skipped, instructions count: 552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.applyForcedPropertiesForDefaultDisplay():void");
    }

    public final void applyMagnificationSpecLocked(int i, MagnificationSpec magnificationSpec) {
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            if (magnificationSpec.scale != 1.0d) {
                displayContent.mMagnificationSpec = magnificationSpec;
            } else {
                displayContent.mMagnificationSpec = null;
            }
            displayContent.updateImeParent();
            InputMonitor inputMonitor = displayContent.mInputMonitor;
            DisplayInfo displayInfo = displayContent.mDisplayInfo;
            inputMonitor.layoutInputConsumers(displayInfo.logicalWidth, displayInfo.logicalHeight, true);
            if (magnificationSpec.scale != 1.0d) {
                displayContent.applyMagnificationSpec(displayContent.getPendingTransaction(), magnificationSpec);
            } else {
                displayContent.clearMagnificationSpec(displayContent.getPendingTransaction());
            }
            displayContent.getPendingTransaction().apply();
        }
    }

    public final WindowContextInfo attachWindowContextToDisplayArea(IApplicationThread iApplicationThread, IBinder iBinder, int i, int i2, Bundle bundle) {
        Objects.requireNonNull(iApplicationThread);
        Objects.requireNonNull(iBinder);
        boolean checkCallingPermission$1 = checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "attachWindowContextToDisplayArea", false);
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowProcessController processController = this.mAtmService.getProcessController(iApplicationThread);
                    if (processController != null) {
                        DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i2);
                        if (displayContentOrCreate != null) {
                            DisplayArea findAreaForWindowType = displayContentOrCreate.findAreaForWindowType(i, bundle, checkCallingPermission$1, false);
                            this.mWindowContextListenerController.registerWindowContainerListener(processController, iBinder, findAreaForWindowType, i, bundle);
                            WindowContextInfo windowContextInfo = new WindowContextInfo(findAreaForWindowType.getConfiguration(), i2);
                            resetPriorityAfterLockedSection();
                            return windowContextInfo;
                        }
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 1904306629015452865L, 1, "attachWindowContextToDisplayArea: trying to attach to a non-existing display:%d", Long.valueOf(i2));
                        }
                    } else if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 8372202339190060748L, 5, "attachWindowContextToDisplayArea: calling from non-existing process pid=%d uid=%d", Long.valueOf(callingPid), Long.valueOf(callingUid));
                    }
                    resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final WindowContextInfo attachWindowContextToDisplayContent(IApplicationThread iApplicationThread, IBinder iBinder, int i) {
        Objects.requireNonNull(iApplicationThread);
        Objects.requireNonNull(iBinder);
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowProcessController processController = this.mAtmService.getProcessController(iApplicationThread);
                    if (processController != null) {
                        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                        if (displayContent != null) {
                            this.mWindowContextListenerController.registerWindowContainerListener(processController, iBinder, displayContent, -1, null);
                            WindowContextInfo windowContextInfo = new WindowContextInfo(displayContent.getConfiguration(), i);
                            resetPriorityAfterLockedSection();
                            return windowContextInfo;
                        }
                        if (callingPid != MY_PID) {
                            throw new WindowManager.InvalidDisplayException("attachWindowContextToDisplayContent: trying to attach to a non-existing display:" + i);
                        }
                    } else if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -6845859096032432107L, 5, "attachWindowContextToDisplayContent: calling from non-existing process pid=%d uid=%d", Long.valueOf(callingPid), Long.valueOf(callingUid));
                    }
                    resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final WindowContextInfo attachWindowContextToWindowToken(IApplicationThread iApplicationThread, IBinder iBinder, IBinder iBinder2) {
        Objects.requireNonNull(iApplicationThread);
        Objects.requireNonNull(iBinder);
        Objects.requireNonNull(iBinder2);
        boolean checkCallingPermission$1 = checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "attachWindowContextToWindowToken", false);
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowProcessController processController = this.mAtmService.getProcessController(iApplicationThread);
                    if (processController != null) {
                        WindowToken windowToken = this.mRoot.getWindowToken(iBinder2);
                        if (windowToken != null) {
                            WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerController.WindowContextListenerImpl) this.mWindowContextListenerController.mListeners.get(iBinder);
                            int i = windowContextListenerImpl != null ? windowContextListenerImpl.mType : -1;
                            if (i == -1) {
                                throw new IllegalArgumentException("The clientToken:" + iBinder + " should have been attached.");
                            }
                            if (i != windowToken.windowType) {
                                throw new IllegalArgumentException("The WindowToken's type should match the created WindowContext's type. WindowToken's type is " + windowToken.windowType + ", while WindowContext's is " + i);
                            }
                            if (this.mWindowContextListenerController.assertCallerCanModifyListener(callingUid, checkCallingPermission$1, iBinder)) {
                                this.mWindowContextListenerController.registerWindowContainerListener(processController, iBinder, windowToken, windowToken.windowType, windowToken.mOptions);
                                WindowContextInfo windowContextInfo = new WindowContextInfo(windowToken.getConfiguration(), windowToken.getDisplayContent().mDisplayId);
                                resetPriorityAfterLockedSection();
                                return windowContextInfo;
                            }
                        } else if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -2056866750160555704L, 0, "Then token:%s is invalid. It might be removed", String.valueOf(iBinder2));
                        }
                    } else if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 1473791807245791604L, 5, "attachWindowContextToWindowToken: calling from non-existing process pid=%d uid=%d", Long.valueOf(callingPid), Long.valueOf(callingUid));
                    }
                    resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean cancelDraw(Session session, IWindow iWindow) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean z = windowForClientLocked.mPrepareSyncSeqId > 0;
                resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void captureDisplay(int i, ScreenCapture.CaptureArgs captureArgs, ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        Slog.d("WindowManager", "captureDisplay");
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "captureDisplay()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        ScreenCapture.LayerCaptureArgs captureArgs2 = getCaptureArgs(i, captureArgs);
        ScreenCapture.captureLayers(captureArgs2, screenCaptureListener);
        if (Binder.getCallingUid() != 1000) {
            captureArgs2.release();
        }
    }

    public final Bitmap captureTaskBitmap(int i, ScreenCapture.LayerCaptureArgs.Builder builder) {
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
                ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(builder.setLayer(anyTaskForId.mSurfaceControl).setSourceCrop(this.mTmpRect).build());
                if (captureLayers != null) {
                    Bitmap asBitmap = captureLayers.asBitmap();
                    resetPriorityAfterLockedSection();
                    return asBitmap;
                }
                Slog.w("WindowManager", "Could not get screenshot buffer for taskId: " + i);
                resetPriorityAfterLockedSection();
                return null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void changeDisplayScale(MagnificationSpec magnificationSpec, boolean z, IInputFilter iInputFilter) {
        if (!checkCallingPermission$1("android.permission.WRITE_SECURE_SETTINGS", "changeDisplayScale()", true)) {
            throw new SecurityException("Requires WRITE SECURE SYSTEM SETTING permission.");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                if (defaultDisplayContentLocked != null && defaultDisplayContentLocked.getOneHandOpPolicy() != null) {
                    defaultDisplayContentLocked.getOneHandOpPolicy().changeDisplayScaleLocked(magnificationSpec, z, iInputFilter);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final boolean checkBootAnimationCompleteLocked() {
        boolean isRunning = SystemService.isRunning("bootanim");
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled;
        if (!isRunning) {
            if (!zArr[2]) {
                return true;
            }
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -8177456840019985809L, 0, null, null);
            return true;
        }
        this.mH.removeMessages(37);
        this.mH.sendEmptyMessageDelayed(37, 50L);
        if (!zArr[2]) {
            return false;
        }
        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -2061779801633179448L, 0, null, null);
        return false;
    }

    public final boolean checkCallingPermission$1(String str, String str2, boolean z) {
        if (Binder.getCallingPid() == MY_PID || this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        if (!z || !ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
            return false;
        }
        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -2577785761087081584L, 20, "Permission Denial: %s from pid=%d, uid=%d requires %s", str2, Long.valueOf(Binder.getCallingPid()), Long.valueOf(Binder.getCallingUid()), str);
        return false;
    }

    public final void checkDrawnWindowsLocked() {
        boolean[] zArr;
        if (this.mWaitingForDrawnCallbacks.isEmpty()) {
            return;
        }
        for (int size = this.mWaitingForDrawnCallbacks.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mWaitingForDrawnCallbacks.keyAt(size);
            int size2 = windowContainer.mWaitingForDrawn.size();
            while (true) {
                size2--;
                zArr = ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_SCREEN_ON_enabled;
                if (size2 < 0) {
                    break;
                }
                WindowState windowState = (WindowState) windowContainer.mWaitingForDrawn.get(size2);
                if (zArr[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, 4749314182037322882L, 508, "Waiting for drawn %s: removed=%b visible=%b mHasSurface=%b drawState=%d", String.valueOf(windowState), Boolean.valueOf(windowState.mRemoved), Boolean.valueOf(windowState.isVisible()), Boolean.valueOf(windowState.mHasSurface), Long.valueOf(windowState.mWinAnimator.mDrawState));
                }
                if (windowState.mRemoved || !windowState.mHasSurface || !windowState.isVisibleByPolicy()) {
                    if (zArr[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, -257537448418987051L, 0, "Aborted waiting for drawn: %s", String.valueOf(windowState));
                    }
                    windowContainer.mWaitingForDrawn.remove(windowState);
                    if (Trace.isTagEnabled(32L)) {
                        traceEndWaitingForWindowDrawn(windowState);
                    }
                } else if (windowState.mWinAnimator.mDrawState == 4) {
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, 3528458211932714003L, 0, "Window drawn win=%s", String.valueOf(windowState));
                    }
                    windowContainer.mWaitingForDrawn.remove(windowState);
                    if (Trace.isTagEnabled(32L)) {
                        traceEndWaitingForWindowDrawn(windowState);
                    }
                }
            }
            if (windowContainer.mWaitingForDrawn.isEmpty()) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_FORCE_DEBUG_SCREEN_ON, -556131401893662046L, 0, "All windows drawn!", null);
                }
                this.mH.removeMessages(24, windowContainer);
                ((Message) this.mWaitingForDrawnCallbacks.removeAt(size)).sendToTarget();
            }
        }
    }

    public final void cleanupRecentsAnimation(int i) {
        IStatusBar iStatusBar;
        Dimmer.DimState dimState;
        RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
        if (recentsAnimationController != null) {
            this.mRecentsAnimationController = null;
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 622027757443954945L, 5, null, Long.valueOf(recentsAnimationController.mPendingAnimations.size()), Long.valueOf(i));
            }
            if (i != 2 && recentsAnimationController.mTargetActivityRecord != recentsAnimationController.mDisplayContent.topRunningActivity(false)) {
                recentsAnimationController.mDisplayContent.mFixedRotationTransitionListener.mRecentsWillBeTop = true;
            }
            for (int size = recentsAnimationController.mPendingAnimations.size() - 1; size >= 0; size--) {
                RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = (RecentsAnimationController.TaskAnimationAdapter) recentsAnimationController.mPendingAnimations.get(size);
                if ((i == 1 || i == 0) && (dimState = taskAnimationAdapter.mTask.mDimmer.mDimState) != null) {
                    dimState.mAnimateExit = false;
                }
                recentsAnimationController.removeAnimation(taskAnimationAdapter);
                Task task = taskAnimationAdapter.mTask;
                SurfaceControl.Transaction pendingTransaction = task.getPendingTransaction();
                if (taskAnimationAdapter.mFinishTransaction != null) {
                    SurfaceControl surfaceControl = taskAnimationAdapter.mFinishOverlay;
                    if (surfaceControl != null) {
                        pendingTransaction.reparent(surfaceControl, task.mSurfaceControl);
                    }
                    PictureInPictureSurfaceTransaction.apply(taskAnimationAdapter.mFinishTransaction, task.mSurfaceControl, pendingTransaction);
                    PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = taskAnimationAdapter.mFinishTransaction;
                    SurfaceControl surfaceControl2 = taskAnimationAdapter.mFinishOverlay;
                    task.mLastRecentsAnimationTransaction = new PictureInPictureSurfaceTransaction(pictureInPictureSurfaceTransaction);
                    task.mLastRecentsAnimationOverlay = surfaceControl2;
                    RecentsAnimationController recentsAnimationController2 = RecentsAnimationController.this;
                    if (recentsAnimationController2.mDisplayContent.isFixedRotationLaunchingApp(recentsAnimationController2.mTargetActivityRecord)) {
                        PinnedTaskController pinnedTaskController = recentsAnimationController2.mDisplayContent.mPinnedTaskController;
                        PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction2 = taskAnimationAdapter.mFinishTransaction;
                        pinnedTaskController.mFreezingTaskConfig = true;
                        pinnedTaskController.mPipTransaction = pictureInPictureSurfaceTransaction2;
                    }
                    if (task.getActivityType() != recentsAnimationController2.mTargetActivityType && taskAnimationAdapter.mFinishTransaction.getShouldDisableCanAffectSystemUiFlags()) {
                        task.mCanAffectSystemUiFlags = false;
                    }
                    taskAnimationAdapter.mFinishTransaction = null;
                    taskAnimationAdapter.mFinishOverlay = null;
                    pendingTransaction.apply();
                } else if (!task.isAttached()) {
                    pendingTransaction.apply();
                }
            }
            recentsAnimationController.mPendingNewTaskTargets.clear();
            recentsAnimationController.mPendingTaskAppears.clear();
            for (int size2 = recentsAnimationController.mPendingWallpaperAnimations.size() - 1; size2 >= 0; size2--) {
                recentsAnimationController.removeWallpaperAnimation((WallpaperAnimationAdapter) recentsAnimationController.mPendingWallpaperAnimations.get(size2));
            }
            recentsAnimationController.restoreNavigationBarFromApp(i == 1 || recentsAnimationController.mIsAddingTaskToTargets);
            recentsAnimationController.mService.mH.removeCallbacks(recentsAnimationController.mFailsafeRunnable);
            recentsAnimationController.mDisplayContent.mAppTransition.mListeners.remove(recentsAnimationController.mAppTransitionListener);
            if (recentsAnimationController.mLinkedToDeathOfRunner) {
                recentsAnimationController.mRunner.asBinder().unlinkToDeath(recentsAnimationController, 0);
                recentsAnimationController.mLinkedToDeathOfRunner = false;
            }
            recentsAnimationController.mRunner = null;
            recentsAnimationController.mCanceled = true;
            if (i == 2 && !recentsAnimationController.mIsAddingTaskToTargets) {
                InputMethodManagerInternal.get().updateImeWindowStatus(false);
            }
            recentsAnimationController.mDisplayContent.mInputMonitor.updateInputWindowsLw(true);
            ActivityRecord activityRecord = recentsAnimationController.mTargetActivityRecord;
            if (activityRecord != null && (i == 1 || i == 0)) {
                recentsAnimationController.mDisplayContent.mAppTransition.notifyAppTransitionFinishedLocked(activityRecord.token);
            }
            DisplayContent.FixedRotationTransitionListener fixedRotationTransitionListener = recentsAnimationController.mDisplayContent.mFixedRotationTransitionListener;
            ActivityRecord activityRecord2 = fixedRotationTransitionListener.mAnimatingRecents;
            boolean z = fixedRotationTransitionListener.mRecentsWillBeTop;
            fixedRotationTransitionListener.mAnimatingRecents = null;
            fixedRotationTransitionListener.mRecentsWillBeTop = false;
            if (!z) {
                DisplayContent displayContent = DisplayContent.this;
                if (activityRecord2 == null || activityRecord2 != displayContent.mFixedRotationLaunchingApp || !activityRecord2.mVisible || activityRecord2 == displayContent.topRunningActivity(false)) {
                    displayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
                } else {
                    displayContent.setFixedRotationLaunchingAppUnchecked(-1, null);
                }
            }
            int i2 = i != 2 ? 1 : 0;
            MultiTaskingController multiTaskingController = recentsAnimationController.mService.mAtmService.mMultiTaskingController;
            multiTaskingController.mH.removeMessages(4);
            multiTaskingController.mH.obtainMessage(4, i2, 0).sendToTarget();
            StatusBarManagerInternal statusBarManagerInternal = recentsAnimationController.mStatusBar;
            if (statusBarManagerInternal != null && (iStatusBar = StatusBarManagerService.this.mBar) != null) {
                try {
                    iStatusBar.onRecentsAnimationStateChanged(false);
                } catch (RemoteException unused) {
                }
            }
            DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
            if (defaultDisplayContentLocked.mAppTransition.isTransitionSet()) {
                defaultDisplayContentLocked.mSkipAppTransitionAnimation = true;
            }
            defaultDisplayContentLocked.forAllWindowContainers(new WindowManagerService$$ExternalSyntheticLambda7(3));
        }
    }

    public final void clearForcedDisplayDensityForUser(int i, int i2) {
        clearForcedDisplayDensityForUser_enforcePermission();
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
            int i3 = -1;
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, i2, i3, null, -1, -1, false);
        }
    }

    public final void clearForcedDisplaySize(int i) {
        clearForcedDisplaySize_enforcePermission();
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
                        displayContent.setForcedSize(i2, i3, f, f, true, false, false);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, -1, -1, null, -1, -1, false);
        }
    }

    public final void clearForcedDisplaySizeDensity(int i) {
        MultiResolutionController multiResolutionController = this.mExt.mMultiResolutionController;
        if (multiResolutionController.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        if (i != 0) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "input illegalArgument(displayId=", ")"));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = multiResolutionController.mService.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = multiResolutionController.mService.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        multiResolutionController.getForcedDisplaySize(multiResolutionController.mTmpDisplaySize);
                        Point point = multiResolutionController.mTmpDisplaySize;
                        displayContent.setForcedSizeDensity(point.x, point.y, multiResolutionController.getForcedDisplayDensity(), false, 0, false);
                        if (CoreRune.FW_FLEXIBLE_TABLE_MODE) {
                            multiResolutionController.mService.setTableModeEnabled(true);
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
            multiResolutionController.updateDisplaySizeDensityChangedReason(i, -1, -1, null, -1, -1, false);
        }
    }

    public final void clearKeyCustomizationInfoByAction(int i, int i2, int i3) {
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "clearKeyCustomizationInfoByAction", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.clearKeyCustomizationInfoByAction(i, i2, i3);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearKeyCustomizationInfoByKeyCode(int i, int i2) {
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "clearKeyCustomizationInfoByKeyCode", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.clearKeyCustomizationInfoByKeyCode(i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearTspDeadzone(Session session, IWindow iWindow) {
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        windowManagerServiceExt.getClass();
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked = windowManagerServiceExt.mService.windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked == null) {
                        Slog.e("WindowManagerServiceExt", "clearTspDeadzone failed. The win is null.");
                        resetPriorityAfterLockedSection();
                    } else {
                        windowForClientLocked.mTspDeadzone = null;
                        windowManagerServiceExt.updateTspStateControllerWindowPolicyLocked(windowForClientLocked);
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public final boolean clearWindowContentFrameStats(IBinder iBinder) {
        if (!checkCallingPermission$1("android.permission.FRAME_STATS", "clearWindowContentFrameStats()", true)) {
            throw new SecurityException("Requires FRAME_STATS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                boolean z = false;
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                SurfaceControl surfaceControl = windowSurfaceController.mSurfaceControl;
                if (surfaceControl != null) {
                    z = surfaceControl.clearContentFrameStats();
                }
                resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void closeSystemDialogs(String str) {
        if (this.mAtmService.checkCanCloseSystemDialogs(Binder.getCallingPid(), Binder.getCallingUid(), null)) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = this.mRoot;
                    rootWindowContainer.mCloseSystemDialogsReason = str;
                    rootWindowContainer.forAllWindows((Consumer) rootWindowContainer.mCloseSystemDialogsConsumer, false);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public final void closeSystemDialogsInDisplay(String str, int i) {
        if (this.mAtmService.checkCanCloseSystemDialogs(Binder.getCallingPid(), Binder.getCallingUid(), null)) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = this.mRoot;
                    DisplayContent displayContent = rootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.e("WindowManager", "closeSystemDialogs: cannot find display #" + i);
                    } else {
                        rootWindowContainer.mCloseSystemDialogsReason = str;
                        displayContent.forAllWindows((Consumer) rootWindowContainer.mCloseSystemDialogsConsumer, false);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public final Configuration computeNewConfiguration(int i) {
        Configuration configuration;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mDisplayReady) {
                    Configuration configuration2 = new Configuration();
                    this.mRoot.getDisplayContent(i).computeScreenConfiguration(configuration2);
                    configuration = configuration2;
                } else {
                    configuration = null;
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return configuration;
    }

    public final void createInputConsumer(IBinder iBinder, String str, int i, InputChannel inputChannel) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        if (!activityTaskManagerService.mRecentTasks.isCallerRecents(Binder.getCallingUid()) && this.mContext.checkCallingOrSelfPermission("android.permission.INPUT_CONSUMER") != 0) {
            throw new SecurityException("createInputConsumer requires INPUT_CONSUMER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.mInputMonitor.createInputConsumer(iBinder, str, inputChannel, Binder.getCallingPid(), Binder.getCallingUserHandle());
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final boolean destroyInputConsumer(IBinder iBinder, int i) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        if (!activityTaskManagerService.mRecentTasks.isCallerRecents(Binder.getCallingUid()) && this.mContext.checkCallingOrSelfPermission("android.permission.INPUT_CONSUMER") != 0) {
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
                boolean destroyInputConsumer = displayContent.mInputMonitor.destroyInputConsumer(iBinder);
                resetPriorityAfterLockedSection();
                return destroyInputConsumer;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void detachWindowContext(IBinder iBinder) {
        Objects.requireNonNull(iBinder);
        boolean checkCallingPermission$1 = checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "detachWindowContext", false);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!this.mWindowContextListenerController.assertCallerCanModifyListener(callingUid, checkCallingPermission$1, iBinder)) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerController.WindowContextListenerImpl) this.mWindowContextListenerController.mListeners.get(iBinder);
                    WindowContainer windowContainer = windowContextListenerImpl != null ? windowContextListenerImpl.mContainer : null;
                    WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl2 = (WindowContextListenerController.WindowContextListenerImpl) this.mWindowContextListenerController.mListeners.get(iBinder);
                    if (windowContextListenerImpl2 != null) {
                        windowContextListenerImpl2.unregister();
                        WindowContextListenerController.WindowContextListenerImpl.DeathRecipient deathRecipient = windowContextListenerImpl2.mDeathRecipient;
                        if (deathRecipient != null) {
                            WindowContextListenerController.WindowContextListenerImpl.this.mClientToken.unlinkToDeath(deathRecipient, 0);
                        }
                    }
                    WindowToken asWindowToken = windowContainer.asWindowToken();
                    if (asWindowToken != null && asWindowToken.mFromClientToken) {
                        removeWindowToken(asWindowToken.token, asWindowToken.getDisplayContent().mDisplayId);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean detectSafeMode() {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.detectSafeMode():boolean");
    }

    public final void disableKeyguard(IBinder iBinder, String str, int i) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 2, "disableKeyguard", (String) null);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mKeyguardDisableHandler.disableKeyguard(callingUid, iBinder, str, handleIncomingUser);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void disableNonVrUi(boolean z) {
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
                    Session session = (Session) this.mSessions.valueAt(size);
                    boolean z3 = this.mShowAlertWindowNotifications;
                    session.mShowingAlertWindowNotificationAllowed = z3;
                    AlertWindowNotification alertWindowNotification = session.mAlertWindowNotification;
                    if (alertWindowNotification != null) {
                        if (z3) {
                            alertWindowNotification.mService.mH.post(new AlertWindowNotification$$ExternalSyntheticLambda0(alertWindowNotification));
                        } else {
                            alertWindowNotification.mService.mH.post(new AlertWindowNotification$$ExternalSyntheticLambda1(alertWindowNotification, false));
                        }
                    }
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void dismissKeyguard(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        if (!checkCallingPermission$1("android.permission.CONTROL_KEYGUARD", "dismissKeyguard", true)) {
            throw new SecurityException("Requires CONTROL_KEYGUARD permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!android.service.dreams.Flags.dreamHandlesConfirmKeys() && getDefaultDisplayContentLocked().mDisplayPolicy.mShowingDream) {
                    this.mAtmService.mTaskSupervisor.wakeUp("leaveDream");
                }
                ((PhoneWindowManager) this.mPolicy).dismissKeyguardLw(iKeyguardDismissCallback, charSequence);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void dispatchImeInputTargetVisibilityChanged(final IBinder iBinder, final boolean z, final boolean z2) {
        if (this.mImeTargetChangeListener != null) {
            this.mH.post(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerService windowManagerService = WindowManagerService.this;
                    IBinder iBinder2 = iBinder;
                    boolean z3 = z;
                    boolean z4 = z2;
                    ImeVisibilityStateComputer.AnonymousClass1 anonymousClass1 = (ImeVisibilityStateComputer.AnonymousClass1) windowManagerService.mImeTargetChangeListener;
                    ImeVisibilityStateComputer imeVisibilityStateComputer = ImeVisibilityStateComputer.this;
                    if (imeVisibilityStateComputer.mCurVisibleImeInputTarget == iBinder2 && ((!z3 || z4) && imeVisibilityStateComputer.mCurVisibleImeLayeringOverlay != null)) {
                        ImeTracker.Token onStart = ImeTracker.forLogging().onStart(2, 6, 37, false);
                        InputMethodManagerService inputMethodManagerService = ImeVisibilityStateComputer.this.mService;
                        inputMethodManagerService.getClass();
                        synchronized (ImfLock.class) {
                            inputMethodManagerService.mVisibilityApplier.applyImeVisibility(iBinder2, onStart, 5, 37, inputMethodManagerService.mCurrentUserId);
                        }
                    }
                    ImeVisibilityStateComputer imeVisibilityStateComputer2 = ImeVisibilityStateComputer.this;
                    if (!z3 || z4) {
                        iBinder2 = null;
                    }
                    imeVisibilityStateComputer2.mCurVisibleImeInputTarget = iBinder2;
                }
            });
        }
    }

    public final void dispatchSPenGestureEvent(int i, int i2, InputEvent[] inputEventArr, final IBinder iBinder) {
        WindowState window;
        if (InputRune.PWM_SPEN) {
            WindowManagerServiceExt windowManagerServiceExt = this.mExt;
            windowManagerServiceExt.getClass();
            int callingUid = Binder.getCallingUid();
            if (windowManagerServiceExt.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingUid, "only system signature can use dispatchSPenGestureEvent, but UID(", ") has not system signature"));
            }
            float f = i;
            float f2 = i2;
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                final int i3 = (int) f;
                final int i4 = (int) f2;
                try {
                    DisplayContent defaultDisplayContentLocked = windowManagerServiceExt.mService.getDefaultDisplayContentLocked();
                    if (defaultDisplayContentLocked == null) {
                        Slog.e("WindowManagerServiceExt", "findTargetSPenGestureWindow : failed to get display content");
                        resetPriorityAfterLockedSection();
                        window = null;
                    } else {
                        window = defaultDisplayContentLocked.getWindow(new Predicate() { // from class: com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i5 = i3;
                                int i6 = i4;
                                IBinder iBinder2 = iBinder;
                                WindowState windowState = (WindowState) obj;
                                Rect rect = new Rect();
                                int i7 = windowState.mAttrs.flags;
                                if (!windowState.isVisible() || (i7 & 16) != 0) {
                                    return false;
                                }
                                Region region = new Region();
                                windowState.getTouchableRegion(region);
                                if (!region.contains(i5, i6)) {
                                    return false;
                                }
                                rect.set(windowState.mWindowFrames.mFrame);
                                int i8 = windowState.mTouchableInsets;
                                if (i8 == 1) {
                                    int i9 = rect.left;
                                    Rect rect2 = windowState.mGivenContentInsets;
                                    rect.left = i9 + rect2.left;
                                    rect.top += rect2.top;
                                    rect.right -= rect2.right;
                                    rect.bottom -= rect2.bottom;
                                } else if (i8 == 2 || i8 == 3) {
                                    int i10 = rect.left;
                                    Rect rect3 = windowState.mGivenVisibleInsets;
                                    rect.left = i10 + rect3.left;
                                    rect.top += rect3.top;
                                    rect.right -= rect3.right;
                                    rect.bottom -= rect3.bottom;
                                }
                                return (rect.contains(i5, i6) || ((i7 & 40) == 0)) && windowState.mClient.asBinder() != iBinder2;
                            }
                        });
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (window == null) {
                Slog.e("WindowManagerServiceExt", "dispatchSPenGestureEvent : Could not find the target window!");
            } else {
                if (window.mAttrs.type == 2019) {
                    return;
                }
                try {
                    window.mClient.dispatchSPenGestureEvent(inputEventArr);
                } catch (Exception e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(e, "Failed to call IWindow.dispatchSmartClipRemoteRequest()!, ", "WindowManagerServiceExt");
                }
            }
        }
    }

    public final void dispatchSmartClipRemoteRequest(int i, int i2, SmartClipRemoteRequestInfo smartClipRemoteRequestInfo, final IBinder iBinder) {
        WindowState window;
        Slog.d("WindowManager", "dispatchSmartClipRemoteRequest, flag : true");
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        windowManagerServiceExt.getClass();
        if (smartClipRemoteRequestInfo == null) {
            Slog.e("WindowManagerServiceExt", "dispatchSmartClipRemoteRequest : request is null!");
            return;
        }
        smartClipRemoteRequestInfo.mCallerPid = Binder.getCallingPid();
        smartClipRemoteRequestInfo.mCallerUid = Binder.getCallingUid();
        final int i3 = smartClipRemoteRequestInfo.mWindowTargetingType;
        final int i4 = i;
        final int i5 = i2;
        DisplayContent defaultDisplayContentLocked = windowManagerServiceExt.mService.getDefaultDisplayContentLocked();
        if (defaultDisplayContentLocked == null) {
            Slog.e("WindowManagerServiceExt", "findTargetSmartClipWindow : failed to get display content");
            window = null;
        } else {
            window = defaultDisplayContentLocked.getWindow(new Predicate() { // from class: com.android.server.wm.WindowManagerServiceExt$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    int i6 = i4;
                    int i7 = i5;
                    int i8 = i3;
                    IBinder iBinder2 = iBinder;
                    WindowState windowState = (WindowState) obj;
                    Rect rect = new Rect();
                    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
                    int i9 = layoutParams.flags;
                    int i10 = layoutParams.type;
                    if ("ScreenshotAnimation".equalsIgnoreCase(windowState.getWindowTag().toString()) || !windowState.isVisible() || (i9 & 16) != 0) {
                        return false;
                    }
                    windowState.getVisibleBounds(rect);
                    if (!rect.contains(i6, i7)) {
                        return false;
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && i10 == 2604) {
                        return false;
                    }
                    if (i8 == 1) {
                        Region region = new Region();
                        windowState.getTouchableRegion(region);
                        if (!region.contains(i6, i7)) {
                            return false;
                        }
                    }
                    rect.set(windowState.mWindowFrames.mFrame);
                    int i11 = windowState.mTouchableInsets;
                    if (i11 == 1) {
                        int i12 = rect.left;
                        Rect rect2 = windowState.mGivenContentInsets;
                        rect.left = i12 + rect2.left;
                        rect.top += rect2.top;
                        rect.right -= rect2.right;
                        rect.bottom -= rect2.bottom;
                    } else if (i11 == 2 || i11 == 3) {
                        int i13 = rect.left;
                        Rect rect3 = windowState.mGivenVisibleInsets;
                        rect.left = i13 + rect3.left;
                        rect.top += rect3.top;
                        rect.right -= rect3.right;
                        rect.bottom -= rect3.bottom;
                    }
                    return (rect.contains(i6, i7) || ((i9 & 40) == 0)) && windowState.mClient.asBinder() != iBinder2;
                }
            });
        }
        if (window != null) {
            smartClipRemoteRequestInfo.mTargetWindowLayer = 0;
            try {
                window.mClient.dispatchSmartClipRemoteRequest(smartClipRemoteRequestInfo);
                return;
            } catch (Exception e) {
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "dispatchSmartClipRemoteRequest : Failed to call IWindow.dispatchSmartClipRemoteRequest()! e=", "WindowManagerServiceExt");
                return;
            }
        }
        Slog.e("WindowManagerServiceExt_SmartClip", "dispatchSmartClipRemoteRequest : Could not find the target window! x=" + i + " y=" + i2);
        Slog.e("WindowManagerServiceExt_SmartClip", "dispatchSmartClipRemoteRequest : Send empty response...");
        try {
            ((SpenGestureManager) windowManagerServiceExt.mContext.getSystemService("spengestureservice")).sendSmartClipRemoteRequestResult(new SmartClipRemoteRequestResult(smartClipRemoteRequestInfo.mRequestId, smartClipRemoteRequestInfo.mRequestType, (Parcelable) null));
        } catch (RuntimeException e2) {
            Slog.e("WindowManagerServiceExt_SmartClip", "dispatchSmartClipRemoteRequest : Failed to send the empty result! e=" + e2);
        }
    }

    public final void displayReady() {
        boolean z;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mMaxUiWidth > 0) {
                    this.mRoot.forAllDisplays(new WindowManagerService$$ExternalSyntheticLambda6(0, this));
                }
                applyForcedPropertiesForDefaultDisplay();
                this.mAnimator.mInitialized = true;
                this.mDisplayReady = true;
                Optional ofNullable = Optional.ofNullable(SurfaceFlingerProperties.tryParseBoolean(SystemProperties.get("ro.surface_flinger.has_wide_color_display")));
                boolean z2 = false;
                if (ofNullable.isPresent()) {
                    z = ((Boolean) ofNullable.get()).booleanValue();
                } else {
                    try {
                        z = ISurfaceFlingerConfigs$Proxy.getService().hasWideColorDisplay().value;
                    } catch (RemoteException | NoSuchElementException unused) {
                        z = false;
                    }
                }
                this.mHasWideColorGamutSupport = z;
                Optional ofNullable2 = Optional.ofNullable(SurfaceFlingerProperties.tryParseBoolean(SystemProperties.get("ro.surface_flinger.has_HDR_display")));
                if (ofNullable2.isPresent()) {
                    z2 = ((Boolean) ofNullable2.get()).booleanValue();
                } else {
                    try {
                        z2 = ISurfaceFlingerConfigs$Proxy.getService().hasHDRDisplay().value;
                    } catch (RemoteException | NoSuchElementException unused2) {
                    }
                }
                this.mHasHdrSupport = z2;
                this.mIsTouchDevice = this.mContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen");
                this.mIsFakeTouchDevice = this.mContext.getPackageManager().hasSystemFeature("android.hardware.faketouch");
                this.mRoot.forAllDisplays(new WindowManagerService$$ExternalSyntheticLambda7(0));
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
        if (defaultDisplayContentLocked != null) {
            MultiWindowPointerEventListener multiWindowPointerEventListener = defaultDisplayContentLocked.mMultiWindowPointerEventListener;
            multiWindowPointerEventListener.getClass();
            multiWindowPointerEventListener.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(multiWindowPointerEventListener.mService.mContext, "Service");
            multiWindowPointerEventListener.mTaskFromPointSearchResult.mTask = null;
            multiWindowPointerEventListener.mTaskId = -1;
            multiWindowPointerEventListener.mTask = null;
            if (CoreRune.MT_SIZE_COMPAT_POLICY_DRAG) {
                multiWindowPointerEventListener.mSizeCompatDragPolicy = null;
            }
            multiWindowPointerEventListener.mMinHeight = -1;
            multiWindowPointerEventListener.mMinWidth = -1;
            multiWindowPointerEventListener.loadDimens();
            multiWindowPointerEventListener.mPersona = (SemPersonaManager) multiWindowPointerEventListener.mService.mContext.getSystemService("persona");
            multiWindowPointerEventListener.mStatusBarManager = (SemStatusBarManager) multiWindowPointerEventListener.mService.mContext.getSystemService(SemStatusBarManager.class);
        }
    }

    public final boolean doesAddToastWindowRequireToken(String str, int i, WindowState windowState) {
        if (windowState != null) {
            ActivityRecord activityRecord = windowState.mActivityRecord;
            return activityRecord != null && activityRecord.mTargetSdk >= 26;
        }
        ApplicationInfo applicationInfo = this.mPmInternal.getApplicationInfo(1000, UserHandle.getUserId(i), 0L, str);
        if (applicationInfo == null || applicationInfo.uid != i) {
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Package ", str, " not in UID "));
        }
        return applicationInfo.targetSdkVersion >= 26;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
    }

    public final void dumpAccessibilityController(final PrintWriter printWriter, boolean z) {
        boolean hasCallbacks = this.mAccessibilityController.hasCallbacks();
        if (hasCallbacks || z) {
            if (hasCallbacks) {
                printWriter.println("AccessibilityController:");
            } else {
                printWriter.println("AccessibilityController doesn't have callbacks, but printing it anways:");
            }
            AccessibilityController accessibilityController = this.mAccessibilityController;
            DumpUtils.dumpSparseArray(printWriter, "  ", accessibilityController.mDisplayMagnifiers, "magnification display", new DumpUtils.KeyDumper() { // from class: com.android.server.wm.AccessibilityController$$ExternalSyntheticLambda0
                public final /* synthetic */ String f$1 = "  ";

                public final void dump(int i, int i2) {
                    printWriter.printf("%sDisplay #%d:", ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(this.f$1, "  "), Integer.valueOf(i2));
                }
            }, new DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityController$$ExternalSyntheticLambda1
                public final void dump(Object obj) {
                    PrintWriter printWriter2 = printWriter;
                    AccessibilityController.DisplayMagnifier displayMagnifier = (AccessibilityController.DisplayMagnifier) obj;
                    displayMagnifier.getClass();
                    if (com.android.window.flags.Flags.alwaysDrawMagnificationFullscreenBorder()) {
                        return;
                    }
                    AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow viewportWindow = displayMagnifier.mMagnifiedViewport.mWindow;
                    viewportWindow.getClass();
                    StringBuilder sb = new StringBuilder(" mBounds= ");
                    sb.append(viewportWindow.mBounds);
                    sb.append(" mDirtyRect= ");
                    sb.append(viewportWindow.mDirtyRect);
                    sb.append(" mWidth= ");
                    sb.append(AccessibilityController.DisplayMagnifier.this.mScreenSize.x);
                    sb.append(" mHeight= ");
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(sb, AccessibilityController.DisplayMagnifier.this.mScreenSize.y, printWriter2);
                }
            });
            DumpUtils.dumpSparseArrayValues(printWriter, "  ", accessibilityController.mWindowsForAccessibilityObserver, "windows for accessibility observer");
            AccessibilityWindowsPopulator accessibilityWindowsPopulator = accessibilityController.mAccessibilityWindowsPopulator;
            synchronized (accessibilityWindowsPopulator.mLock) {
                try {
                    printWriter.print("  ");
                    printWriter.println("AccessibilityWindowsPopulator");
                    printWriter.print("    ");
                    printWriter.print("mWindowsNotificationEnabled: ");
                    printWriter.println(accessibilityWindowsPopulator.mWindowsNotificationEnabled);
                    if (((ArrayList) accessibilityWindowsPopulator.mVisibleWindows).isEmpty()) {
                        printWriter.print("    ");
                        printWriter.println("No visible windows");
                    } else {
                        printWriter.print("    ");
                        printWriter.print(((ArrayList) accessibilityWindowsPopulator.mVisibleWindows).size());
                        printWriter.print(" visible windows: ");
                        printWriter.println(accessibilityWindowsPopulator.mVisibleWindows);
                    }
                    AccessibilityWindowsPopulator$$ExternalSyntheticLambda1 accessibilityWindowsPopulator$$ExternalSyntheticLambda1 = new AccessibilityWindowsPopulator$$ExternalSyntheticLambda1();
                    DumpUtils.KeyDumper keyDumper = new DumpUtils.KeyDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda2
                        public final /* synthetic */ String f$1 = "  ";

                        public final void dump(int i, int i2) {
                            printWriter.printf("%sDisplay #%d: ", this.f$1, Integer.valueOf(i2));
                        }
                    };
                    final int i = 0;
                    DumpUtils.ValueDumper valueDumper = new DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda3
                        public final void dump(Object obj) {
                            int i2 = i;
                            PrintWriter printWriter2 = printWriter;
                            switch (i2) {
                                case 0:
                                    printWriter2.print((MagnificationSpec) obj);
                                    break;
                                case 1:
                                    printWriter2.print((WindowInfosListener.DisplayInfo) obj);
                                    break;
                                case 2:
                                    printWriter2.print((List) obj);
                                    break;
                                default:
                                    ((Matrix) obj).dump(printWriter2);
                                    break;
                            }
                        }
                    };
                    final int i2 = 1;
                    DumpUtils.dumpSparseArray(printWriter, "    ", accessibilityWindowsPopulator.mDisplayInfos, "display info", accessibilityWindowsPopulator$$ExternalSyntheticLambda1, new DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda3
                        public final void dump(Object obj) {
                            int i22 = i2;
                            PrintWriter printWriter2 = printWriter;
                            switch (i22) {
                                case 0:
                                    printWriter2.print((MagnificationSpec) obj);
                                    break;
                                case 1:
                                    printWriter2.print((WindowInfosListener.DisplayInfo) obj);
                                    break;
                                case 2:
                                    printWriter2.print((List) obj);
                                    break;
                                default:
                                    ((Matrix) obj).dump(printWriter2);
                                    break;
                            }
                        }
                    });
                    final int i3 = 2;
                    DumpUtils.dumpSparseArray(printWriter, "    ", accessibilityWindowsPopulator.mInputWindowHandlesOnDisplays, "window handles on display", keyDumper, new DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda3
                        public final void dump(Object obj) {
                            int i22 = i3;
                            PrintWriter printWriter2 = printWriter;
                            switch (i22) {
                                case 0:
                                    printWriter2.print((MagnificationSpec) obj);
                                    break;
                                case 1:
                                    printWriter2.print((WindowInfosListener.DisplayInfo) obj);
                                    break;
                                case 2:
                                    printWriter2.print((List) obj);
                                    break;
                                default:
                                    ((Matrix) obj).dump(printWriter2);
                                    break;
                            }
                        }
                    });
                    final int i4 = 3;
                    DumpUtils.dumpSparseArray(printWriter, "    ", accessibilityWindowsPopulator.mMagnificationSpecInverseMatrix, "magnification spec matrix", accessibilityWindowsPopulator$$ExternalSyntheticLambda1, new DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda3
                        public final void dump(Object obj) {
                            int i22 = i4;
                            PrintWriter printWriter2 = printWriter;
                            switch (i22) {
                                case 0:
                                    printWriter2.print((MagnificationSpec) obj);
                                    break;
                                case 1:
                                    printWriter2.print((WindowInfosListener.DisplayInfo) obj);
                                    break;
                                case 2:
                                    printWriter2.print((List) obj);
                                    break;
                                default:
                                    ((Matrix) obj).dump(printWriter2);
                                    break;
                            }
                        }
                    });
                    DumpUtils.dumpSparseArray(printWriter, "    ", accessibilityWindowsPopulator.mCurrentMagnificationSpec, "current magnification spec", accessibilityWindowsPopulator$$ExternalSyntheticLambda1, valueDumper);
                    DumpUtils.dumpSparseArray(printWriter, "    ", accessibilityWindowsPopulator.mPreviousMagnificationSpec, "previous magnification spec", accessibilityWindowsPopulator$$ExternalSyntheticLambda1, valueDumper);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void dumpAnimatorLocked(PrintWriter printWriter, boolean z) {
        printWriter.println("WINDOW MANAGER ANIMATOR STATE (dumpsys window animator)");
        WindowAnimator windowAnimator = this.mAnimator;
        windowAnimator.getClass();
        int i = 0;
        while (true) {
            WindowManagerService windowManagerService = windowAnimator.mService;
            if (i >= windowManagerService.mRoot.getChildCount()) {
                break;
            }
            DisplayContent displayContent = (DisplayContent) windowManagerService.mRoot.getChildAt(i);
            printWriter.print("    ");
            printWriter.print(displayContent);
            printWriter.println(":");
            displayContent.getClass();
            displayContent.forAllWindows((Consumer) new DisplayContent$$ExternalSyntheticLambda54(printWriter, "      ", new int[1]), false);
            printWriter.println();
            i++;
        }
        printWriter.println();
        if (z) {
            printWriter.print("    ");
            printWriter.print("mCurrentTime=");
            printWriter.println(TimeUtils.formatUptime(windowAnimator.mCurrentTime));
        }
        if (windowAnimator.mBulkUpdateParams != 0) {
            printWriter.print("    ");
            printWriter.print("mBulkUpdateParams=0x");
            printWriter.print(Integer.toHexString(windowAnimator.mBulkUpdateParams));
            int i2 = windowAnimator.mBulkUpdateParams;
            StringBuilder sb = new StringBuilder(128);
            if ((i2 & 1) != 0) {
                sb.append(" UPDATE_ROTATION");
            }
            if ((i2 & 2) != 0) {
                sb.append(" SET_WALLPAPER_ACTION_PENDING");
            }
            printWriter.println(sb.toString());
        }
    }

    public final void dumpDebugLocked(int i, ProtoOutputStream protoOutputStream) {
        ((PhoneWindowManager) this.mPolicy).dumpDebug(protoOutputStream);
        this.mRoot.dumpDebug(protoOutputStream, 1146756268034L, i);
        DisplayContent topFocusedDisplayContent = this.mRoot.getTopFocusedDisplayContent();
        WindowState windowState = topFocusedDisplayContent.mCurrentFocus;
        if (windowState != null) {
            windowState.writeIdentifierToProto(protoOutputStream, 1146756268035L);
        }
        ActivityRecord activityRecord = topFocusedDisplayContent.mFocusedApp;
        if (activityRecord != null) {
            protoOutputStream.write(1138166333444L, activityRecord.shortComponentName);
        }
        WindowState currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
        if (currentInputMethodWindow != null) {
            currentInputMethodWindow.writeIdentifierToProto(protoOutputStream, 1146756268037L);
        }
        protoOutputStream.write(1133871366150L, this.mDisplayFrozen);
        protoOutputStream.write(1120986464265L, topFocusedDisplayContent.mDisplayId);
        protoOutputStream.write(1133871366154L, this.mHardKeyboardAvailable);
        boolean z = true;
        protoOutputStream.write(1133871366155L, true);
        BackNavigationController backNavigationController = this.mAtmService.mBackNavigationController;
        backNavigationController.getClass();
        long start = protoOutputStream.start(1146756268044L);
        protoOutputStream.write(1133871366145L, backNavigationController.mBackAnimationInProgress);
        protoOutputStream.write(1120986464258L, backNavigationController.mLastBackType);
        protoOutputStream.write(1133871366147L, backNavigationController.mShowWallpaper);
        BackNavigationController.AnimationHandler animationHandler = backNavigationController.mAnimationHandler;
        BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper backWindowAnimationAdaptorWrapper = animationHandler.mOpenAnimAdaptor;
        if (backWindowAnimationAdaptorWrapper == null || backWindowAnimationAdaptorWrapper.mAdaptors.length <= 0) {
            protoOutputStream.write(1138166333444L, "");
        } else {
            protoOutputStream.write(1138166333444L, animationHandler.mOpenActivities[0].shortComponentName);
        }
        BackNavigationController.AnimationHandler animationHandler2 = backNavigationController.mAnimationHandler;
        if (!animationHandler2.mComposed && !animationHandler2.mWaitTransition) {
            z = false;
        }
        protoOutputStream.write(1133871366149L, z);
        protoOutputStream.end(start);
    }

    public final void dumpHighRefreshRateBlacklist(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER HIGH REFRESH RATE BLACKLIST (dumpsys window refresh)");
        HighRefreshRateDenylist highRefreshRateDenylist = this.mHighRefreshRateDenylist;
        highRefreshRateDenylist.getClass();
        printWriter.println("High Refresh Rate Denylist");
        printWriter.println("  Packages:");
        synchronized (highRefreshRateDenylist.mLock) {
            try {
                Iterator it = highRefreshRateDenylist.mDenylistedPackages.iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + ((String) it.next()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpSessionsLocked(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER SESSIONS (dumpsys window sessions)");
        for (int i = 0; i < this.mSessions.size(); i++) {
            Session session = (Session) this.mSessions.valueAt(i);
            printWriter.print("  Session ");
            printWriter.print(session);
            printWriter.println(':');
            session.getClass();
            printWriter.print("    ");
            printWriter.print("numWindow=");
            printWriter.print(session.mAddedWindows.size());
            printWriter.print(" mCanAddInternalSystemWindow=");
            printWriter.print(session.mCanAddInternalSystemWindow);
            printWriter.print(" mAlertWindowSurfaces=");
            printWriter.print(session.mAlertWindowSurfaces);
            printWriter.print(" mClientDead=");
            printWriter.print(session.mClientDead);
            printWriter.print(" mSurfaceSession=");
            printWriter.println(session.mSurfaceSession);
            printWriter.print("    ");
            printWriter.print("mPackageName=");
            printWriter.println(session.mPackageName);
            if (session.isSatellitePointingUiPackage()) {
                printWriter.print("    ");
                printWriter.println("mIsSatellitePointingUiPackage=true");
            }
            printWriter.print("    ");
            printWriter.print("mWinSurfaceVisibleCount=");
            printWriter.println(session.mWinSurfaceVisibleCount);
        }
    }

    public final void dumpTokensLocked(PrintWriter printWriter, boolean z) {
        printWriter.println("WINDOW MANAGER TOKENS (dumpsys window tokens)");
        RootWindowContainer rootWindowContainer = this.mRoot;
        rootWindowContainer.getClass();
        printWriter.println("  All tokens:");
        for (int size = rootWindowContainer.mChildren.size() - 1; size >= 0; size--) {
            DisplayContent displayContent = (DisplayContent) rootWindowContainer.mChildren.get(size);
            if (!displayContent.mTokenMap.isEmpty()) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  Display #"), displayContent.mDisplayId, printWriter, "    mInTouchMode="), displayContent.mInTouchMode, printWriter);
                for (WindowToken windowToken : displayContent.mTokenMap.values()) {
                    printWriter.print("  ");
                    printWriter.print(windowToken);
                    if (z) {
                        printWriter.println(':');
                        windowToken.dump(printWriter, "    ", z);
                    } else {
                        printWriter.println();
                    }
                }
                if (!displayContent.mOpeningApps.isEmpty() || !displayContent.mClosingApps.isEmpty() || !displayContent.mChangingContainers.isEmpty()) {
                    printWriter.println();
                    if (displayContent.mOpeningApps.size() > 0) {
                        printWriter.print("  mOpeningApps=");
                        printWriter.println(displayContent.mOpeningApps);
                    }
                    if (displayContent.mClosingApps.size() > 0) {
                        printWriter.print("  mClosingApps=");
                        printWriter.println(displayContent.mClosingApps);
                    }
                    if (displayContent.mChangingContainers.size() > 0) {
                        printWriter.print("  mChangingApps=");
                        printWriter.println(displayContent.mChangingContainers);
                    }
                }
                UnknownAppVisibilityController unknownAppVisibilityController = displayContent.mUnknownAppVisibilityController;
                if (!unknownAppVisibilityController.mUnknownApps.isEmpty()) {
                    printWriter.println("  Unknown visibilities:");
                    for (int size2 = unknownAppVisibilityController.mUnknownApps.size() - 1; size2 >= 0; size2 += -1) {
                        printWriter.println("    app=" + unknownAppVisibilityController.mUnknownApps.keyAt(size2) + " state=" + unknownAppVisibilityController.mUnknownApps.valueAt(size2));
                    }
                }
            }
        }
    }

    public final void dumpTraceStatus(PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER TRACE (dumpsys window trace)");
        printWriter.print(this.mWindowTracing.getStatus() + "\n");
    }

    public final boolean dumpWindows(PrintWriter printWriter, String str, boolean z) {
        int i;
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
                    } catch (Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda45
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean z2 = contains2;
                        boolean z3 = contains;
                        ArrayList arrayList2 = arrayList;
                        WindowState windowState = (WindowState) obj;
                        int i2 = WindowManagerService.MY_PID;
                        if (!z2 || windowState.isVisible()) {
                            if (z3 && windowState.mActivityRecord == null) {
                                return;
                            }
                            arrayList2.add(windowState);
                        }
                    }
                }, true);
            }
            resetPriorityAfterLockedSection();
        } else {
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    RootWindowContainer rootWindowContainer = this.mRoot;
                    rootWindowContainer.getClass();
                    try {
                        i = Integer.parseInt(str, 16);
                        str = null;
                    } catch (RuntimeException unused) {
                        i = 0;
                    }
                    rootWindowContainer.forAllWindows((Consumer) new RootWindowContainer$$ExternalSyntheticLambda8(i, str, arrayList), true);
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

    public final void dumpWindowsLocked(final PrintWriter printWriter, boolean z, ArrayList arrayList) {
        int i;
        SnapshotPersistQueue.WriteQueueItem[] writeQueueItemArr;
        BufferedReader bufferedReader;
        printWriter.println("WINDOW MANAGER WINDOWS (dumpsys window windows)");
        RootWindowContainer rootWindowContainer = this.mRoot;
        rootWindowContainer.getClass();
        rootWindowContainer.forAllWindows((Consumer) new RootWindowContainer$$ExternalSyntheticLambda51(arrayList, printWriter, new int[1], z), true);
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
                    printWriter.println(":");
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
                    printWriter.println(":");
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
                        printWriter.println(":");
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
                        printWriter.println(":");
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
            this.mWaitingForDrawnCallbacks.forEach(new BiConsumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda41
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PrintWriter printWriter2 = printWriter;
                    WindowContainer windowContainer = (WindowContainer) obj;
                    int i2 = WindowManagerService.MY_PID;
                    printWriter2.print("  WindowContainer ");
                    printWriter2.println(windowContainer.getName());
                    for (int size5 = windowContainer.mWaitingForDrawn.size() - 1; size5 >= 0; size5--) {
                        WindowState windowState5 = (WindowState) windowContainer.mWaitingForDrawn.get(size5);
                        printWriter2.print("  Waiting #");
                        printWriter2.print(size5);
                        printWriter2.print(' ');
                        printWriter2.print(windowState5);
                    }
                }
            });
        }
        printWriter.println();
        printWriter.print("  mGlobalConfiguration=");
        printWriter.println(this.mRoot.getConfiguration());
        printWriter.print("  mHasPermanentDpad=");
        printWriter.println(this.mHasPermanentDpad);
        RootWindowContainer rootWindowContainer2 = this.mRoot;
        rootWindowContainer2.getClass();
        printWriter.print("  mTopFocusedDisplayId=");
        printWriter.println(rootWindowContainer2.mTopFocusedDisplayId);
        this.mRoot.forAllDisplays(new WindowManagerService$$ExternalSyntheticLambda38(2, printWriter));
        printWriter.print("  mBlurEnabled=");
        printWriter.println(this.mBlurController.mBlurEnabled);
        printWriter.print("  mLastDisplayFreezeDuration=");
        TimeUtils.formatDuration(this.mLastDisplayFreezeDuration, printWriter);
        if (this.mLastFinishedFreezeSource != null) {
            printWriter.print(" due to ");
            printWriter.print(this.mLastFinishedFreezeSource);
        }
        printWriter.println();
        printWriter.print("  mDisableSecureWindows=");
        printWriter.println(this.mDisableSecureWindows);
        this.mInputManagerCallback.getClass();
        SnapshotController snapshotController = this.mSnapshotController;
        snapshotController.mTaskSnapshotController.dump(printWriter);
        snapshotController.mActivitySnapshotController.dump(printWriter);
        SnapshotPersistQueue snapshotPersistQueue = snapshotController.mSnapshotPersistQueue;
        synchronized (snapshotPersistQueue.mLock) {
            writeQueueItemArr = (SnapshotPersistQueue.WriteQueueItem[]) snapshotPersistQueue.mWriteQueue.toArray(new SnapshotPersistQueue.WriteQueueItem[0]);
        }
        if (writeQueueItemArr.length != 0) {
            printWriter.println(" PersistQueue contains:");
            for (int length = writeQueueItemArr.length - 1; length >= 0; length += -1) {
                printWriter.println("   " + writeQueueItemArr[length] + "");
            }
        }
        dumpAccessibilityController(printWriter, false);
        if (z) {
            Object currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
            if (currentInputMethodWindow != null) {
                printWriter.print("  mInputMethodWindow=");
                printWriter.println(currentInputMethodWindow);
            }
            WindowSurfacePlacer windowSurfacePlacer = this.mWindowPlacerLocked;
            windowSurfacePlacer.getClass();
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mTraversalScheduled="), windowSurfacePlacer.mTraversalScheduled, printWriter, "  mDeferDepth=");
            m.append(windowSurfacePlacer.mDeferDepth);
            m.append(" mDeferredRequests=");
            m.append(windowSurfacePlacer.mDeferredRequests);
            printWriter.println(m.toString());
            printWriter.print("  mSystemBooted=");
            printWriter.print(this.mSystemBooted);
            printWriter.print(" mDisplayEnabled=");
            printWriter.println(this.mDisplayEnabled);
            RootWindowContainer rootWindowContainer3 = this.mRoot;
            if (rootWindowContainer3.isLayoutNeeded()) {
                printWriter.print("  mLayoutNeeded on displays=");
                int size5 = rootWindowContainer3.mChildren.size();
                for (int i2 = 0; i2 < size5; i2++) {
                    DisplayContent displayContent = (DisplayContent) rootWindowContainer3.mChildren.get(i2);
                    if (displayContent.mLayoutNeeded) {
                        printWriter.print(displayContent.mDisplayId);
                    }
                }
                printWriter.println();
            }
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
            printWriter.println(defaultDisplayContentLocked.mDisplayRotation.mRotation);
            printWriter.print("  mLastOrientation=");
            printWriter.println(defaultDisplayContentLocked.mDisplayRotation.mLastOrientation);
            printWriter.print("  mWaitingForConfig=");
            printWriter.println(defaultDisplayContentLocked.mWaitingForConfig);
            printWriter.print("  mWindowsInsetsChanged=");
            printWriter.println(this.mWindowsInsetsChanged);
            RotationWatcherController rotationWatcherController = this.mRotationWatcherController;
            if (!rotationWatcherController.mDisplayRotationWatchers.isEmpty()) {
                printWriter.print("  mDisplayRotationWatchers: [");
                for (int size6 = rotationWatcherController.mDisplayRotationWatchers.size() - 1; size6 >= 0; size6--) {
                    printWriter.print(' ');
                    RotationWatcherController.DisplayRotationWatcher displayRotationWatcher = (RotationWatcherController.DisplayRotationWatcher) rotationWatcherController.mDisplayRotationWatchers.get(size6);
                    printWriter.print(displayRotationWatcher.mOwnerUid);
                    printWriter.print("->");
                    printWriter.print(displayRotationWatcher.mDisplayId);
                }
                printWriter.println(']');
            }
            if (!rotationWatcherController.mProposedRotationListeners.isEmpty()) {
                printWriter.print("  mProposedRotationListeners: [");
                for (int size7 = rotationWatcherController.mProposedRotationListeners.size() - 1; size7 >= 0; size7--) {
                    printWriter.print(' ');
                    RotationWatcherController.ProposedRotationListener proposedRotationListener = (RotationWatcherController.ProposedRotationListener) rotationWatcherController.mProposedRotationListeners.get(size7);
                    printWriter.print(proposedRotationListener.mOwnerUid);
                    printWriter.print("->");
                    IBinder iBinder = proposedRotationListener.mToken;
                    Object forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked == null) {
                        WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerController.WindowContextListenerImpl) rotationWatcherController.mService.mWindowContextListenerController.mListeners.get(iBinder);
                        forTokenLocked = windowContextListenerImpl != null ? windowContextListenerImpl.mContainer : null;
                    }
                    printWriter.print(forTokenLocked);
                }
                printWriter.println(']');
            }
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
                RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
                recentsAnimationController.getClass();
                printWriter.print("    ");
                printWriter.println("RecentsAnimationController:");
                printWriter.print("      ");
                printWriter.println("mPendingStart=" + recentsAnimationController.mPendingStart);
                printWriter.print("      ");
                printWriter.println("mPendingAnimations=" + recentsAnimationController.mPendingAnimations.size());
                printWriter.print("      ");
                printWriter.println("mCanceled=" + recentsAnimationController.mCanceled);
                printWriter.print("      ");
                printWriter.println("mInputConsumerEnabled=" + recentsAnimationController.mInputConsumerEnabled);
                printWriter.print("      ");
                printWriter.println("mTargetActivityRecord=" + recentsAnimationController.mTargetActivityRecord);
                printWriter.print("      ");
                StringBuilder sb = new StringBuilder("isTargetOverWallpaper=");
                ActivityRecord activityRecord = recentsAnimationController.mTargetActivityRecord;
                sb.append(activityRecord == null ? false : activityRecord.windowsCanBeWallpaperTarget());
                printWriter.println(sb.toString());
                printWriter.print("      ");
                printWriter.println("mRequestDeferCancelUntilNextTransition=" + recentsAnimationController.mRequestDeferCancelUntilNextTransition);
                printWriter.print("      ");
                printWriter.println("mCancelOnNextTransitionStart=" + recentsAnimationController.mCancelOnNextTransitionStart);
                printWriter.print("      ");
                printWriter.println("mCancelDeferredWithScreenshot=" + recentsAnimationController.mCancelDeferredWithScreenshot);
                printWriter.print("      ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("mPendingCancelWithScreenshotReorderMode="), recentsAnimationController.mPendingCancelWithScreenshotReorderMode, printWriter);
            }
            printWriter.println();
            WindowContextListenerController windowContextListenerController = this.mWindowContextListenerController;
            windowContextListenerController.getClass();
            printWriter.print("  ");
            printWriter.println("WindowContextListenerController");
            int size8 = windowContextListenerController.mListeners.values().size();
            for (i = 0; i < size8; i++) {
                WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl2 = (WindowContextListenerController.WindowContextListenerImpl) windowContextListenerController.mListeners.valueAt(i);
                printWriter.print("    ");
                printWriter.print("mListeners #");
                printWriter.print(i);
                printWriter.print(" {");
                printWriter.print("type:");
                printWriter.print(windowContextListenerImpl2.mType);
                printWriter.print(", display:");
                printWriter.print(windowContextListenerImpl2.mLastReportedDisplay);
                printWriter.print(", package=");
                printWriter.print(windowContextListenerImpl2.mWpc.mInfo.packageName);
                printWriter.print(", container=");
                printWriter.print(windowContextListenerImpl2.mContainer);
                printWriter.println("}");
                if (windowContextListenerImpl2.mHasPendingConfiguration) {
                    printWriter.print("    ");
                    printWriter.println("  mHasPendingConfiguration=true");
                }
                printWriter.print("    ");
                printWriter.print("  mLastReportedConfig=");
                printWriter.println(windowContextListenerImpl2.mLastReportedConfig);
            }
            printWriter.println();
            DragDropController dragDropController = this.mDragDropController;
            if (dragDropController.mDragState != null) {
                StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "  DragState: ", "CallingWindowToken=");
                m2.append(dragDropController.mDragState.mLocalWin);
                printWriter.print(m2.toString());
                DragState.InputInterceptor inputInterceptor = dragDropController.mDragState.mInputInterceptor;
                InputWindowHandle inputWindowHandle = inputInterceptor != null ? inputInterceptor.mDragWindowHandle : null;
                if (inputWindowHandle != null) {
                    printWriter.print(" InputWindowHandle=" + inputWindowHandle);
                    printWriter.print(" Token=" + inputWindowHandle.token);
                }
                printWriter.println(" InputSurface=" + dragDropController.mDragState.mInputSurface);
            }
            KeyguardDisableHandler keyguardDisableHandler = this.mKeyguardDisableHandler;
            keyguardDisableHandler.getClass();
            printWriter.println("  KeyguardDisable Token Info:");
            UserTokenWatcher userTokenWatcher = keyguardDisableHandler.mAppTokenWatcher;
            if (userTokenWatcher != null) {
                userTokenWatcher.dump(printWriter);
            }
            UserTokenWatcher userTokenWatcher2 = keyguardDisableHandler.mSystemTokenWatcher;
            if (userTokenWatcher2 != null) {
                userTokenWatcher2.dump(printWriter);
            }
        }
        printWriter.println();
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        windowManagerServiceExt.getClass();
        printWriter.println("WINDOW MANAGER EXTENSION (dumpsys window extension)");
        if (!TextUtils.isEmpty(windowManagerServiceExt.mSafeModeReason)) {
            printWriter.print("  ");
            printWriter.println(windowManagerServiceExt.mSafeModeReason);
            printWriter.println();
        }
        MultiResolutionController multiResolutionController = windowManagerServiceExt.mMultiResolutionController;
        if (multiResolutionController.mDisplaySizeDensityChangedReason != null) {
            printWriter.print("  ");
            printWriter.print("mDisplaySizeDensityChangedReason: ");
            printWriter.println(multiResolutionController.mDisplaySizeDensityChangedReason);
            printWriter.println();
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(Environment.getDataSystemDirectory(), "wmlogs.txt")));
        } catch (FileNotFoundException e) {
            Slog.e("WindowManagerServiceExt", "dumpCriticalInfo failed FileNotFoundException, " + e);
        } catch (IOException e2) {
            BootReceiver$$ExternalSyntheticOutline0.m("dumpCriticalInfo failed IOException, ", e2, "WindowManagerServiceExt");
        }
        try {
            printWriter.print("  ");
            printWriter.println("dumpCriticalInfo");
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                printWriter.print("    ");
                printWriter.println(readLine);
            }
            printWriter.println();
            bufferedReader.close();
            PolicyControl.dump("sImmersiveStatusFilter", PolicyControl.sImmersiveStatusFilter, printWriter);
            PolicyControl.dump("sImmersiveNavigationFilter", PolicyControl.sImmersiveNavigationFilter, printWriter);
            printWriter.print("  ");
            printWriter.println("ExtraDisplayPolicy=" + windowManagerServiceExt.mExtraDisplayPolicy);
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void enableScreenAfterBoot() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
                    boolean z = this.mDisplayEnabled;
                    boolean z2 = this.mForceDisplayEnabled;
                    boolean z3 = this.mShowingBootMessages;
                    boolean z4 = this.mSystemBooted;
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -1557387535886241553L, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, null, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), String.valueOf(new RuntimeException("here").fillInStackTrace()));
                }
                if (this.mSystemBooted) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mSystemBooted = true;
                hideBootMessagesLocked();
                this.mH.sendEmptyMessageDelayed(23, 30000L);
                resetPriorityAfterLockedSection();
                ((PhoneWindowManager) this.mPolicy).systemBooted();
                performEnableScreen();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void enableScreenIfNeeded() {
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

    public final void enableScreenIfNeededLocked() {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
            boolean z = this.mDisplayEnabled;
            boolean z2 = this.mForceDisplayEnabled;
            boolean z3 = this.mShowingBootMessages;
            boolean z4 = this.mSystemBooted;
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -6467850045030187736L, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, null, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), String.valueOf(new RuntimeException("here").fillInStackTrace()));
        }
        if (this.mDisplayEnabled) {
            return;
        }
        if (this.mSystemBooted || this.mShowingBootMessages) {
            this.mH.sendEmptyMessage(16);
        }
    }

    public final void endProlongedAnimations() {
    }

    public final void executeAppTransition() {
        if (!checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "executeAppTransition()", true)) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        getDefaultDisplayContentLocked().executeAppTransition();
    }

    public final void exitKeyguardSecurely(IOnKeyguardExitResult iOnKeyguardExitResult) {
        exitKeyguardSecurely_enforcePermission();
        if (iOnKeyguardExitResult == null) {
            throw new IllegalArgumentException("callback == null");
        }
        ((PhoneWindowManager) this.mPolicy).exitKeyguardSecurely(new AnonymousClass3(iOnKeyguardExitResult));
    }

    public final WindowState findWindow(int i) {
        WindowState window;
        if (i == -1) {
            return getFocusedWindow();
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                window = this.mRoot.getWindow(new WindowManagerService$$ExternalSyntheticLambda1(i, 1));
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return window;
    }

    public final boolean finishRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) {
        DisplayArea displayArea;
        boolean z = false;
        if (!CoreRune.FW_REMOTE_WALLPAPER_ANIM || iRemoteAnimationRunner == null || iRemoteAnimationRunner.asBinder() == null) {
            return false;
        }
        if (!checkCallingPermission$1("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "finishRemoteWallpaperAnimation()", true)) {
            throw new SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                    if (defaultDisplayContentLocked != null) {
                        WallpaperController wallpaperController = defaultDisplayContentLocked.mWallpaperController;
                        IBinder asBinder = iRemoteAnimationRunner.asBinder();
                        if (wallpaperController.mRemoteWallpaperAnimAreaMap.containsKey(asBinder) && (displayArea = (DisplayArea) wallpaperController.mRemoteWallpaperAnimAreaMap.remove(asBinder)) != null) {
                            try {
                                WallpaperController.RemoteWallpaperAnimationAdapter remoteWallpaperAnimationAdapter = (WallpaperController.RemoteWallpaperAnimationAdapter) displayArea.getAnimation();
                                if (remoteWallpaperAnimationAdapter != null) {
                                    asBinder.unlinkToDeath(remoteWallpaperAnimationAdapter, 0);
                                }
                            } catch (NoSuchElementException unused) {
                            }
                            displayArea.cancelAnimation();
                            z = true;
                        }
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return z;
        } finally {
            Slog.d("WindowManager", "finishRemoteWallpaperAnimation, success=" + z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void freezeDisplayRotation(int i, int i2, String str) {
        if (!checkCallingPermission$1("android.permission.SET_ORIENTATION", "freezeRotation()", true)) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        }
        if (i2 < -1 || i2 > 3) {
            throw new IllegalArgumentException("Rotation argument must be -1 or a valid rotation constant.");
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -6625203651195752178L, 5, null, Long.valueOf(getDefaultDisplayRotation()), Long.valueOf(i2), String.valueOf(str));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w("WindowManager", "Trying to freeze rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayRotation displayRotation = displayContent.mDisplayRotation;
                    DeviceStateController deviceStateController = displayRotation.mDeviceStateController;
                    DisplayContent displayContent2 = displayRotation.mDisplayContent;
                    deviceStateController.getClass();
                    int reverseRotationDirectionAroundZAxis = !displayContent2.isDefaultDisplay ? false : ArrayUtils.contains(deviceStateController.mReverseRotationAroundZAxisStates, deviceStateController.mCurrentState) ? RotationUtils.reverseRotationDirectionAroundZAxis(i2) : i2;
                    if (reverseRotationDirectionAroundZAxis == -1) {
                        reverseRotationDirectionAroundZAxis = displayRotation.mRotation;
                    }
                    displayRotation.setUserRotation(1, reverseRotationDirectionAroundZAxis, str);
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    EventLog.writeEvent(31901, Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(Binder.getCallingPid()));
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

    public final void freezeRotation(int i, String str) {
        freezeDisplayRotation(0, i, str);
    }

    public final void generateDisplayHash(Session session, IWindow iWindow, final Rect rect, final String str, final RemoteCallback remoteCallback) {
        int i;
        Rect rect2 = new Rect(rect);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    Slog.w("WindowManager", "Failed to generate DisplayHash. Invalid window");
                    this.mDisplayHashController.getClass();
                    DisplayHashController.sendDisplayHashError(-3, remoteCallback);
                    resetPriorityAfterLockedSection();
                    return;
                }
                ActivityRecord activityRecord = windowForClientLocked.mActivityRecord;
                if (activityRecord != null && activityRecord.isState(ActivityRecord.State.RESUMED)) {
                    DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                    if (displayContent == null) {
                        Slog.w("WindowManager", "Failed to generate DisplayHash. Window is not on a display");
                        this.mDisplayHashController.getClass();
                        DisplayHashController.sendDisplayHashError(-4, remoteCallback);
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    SurfaceControl surfaceControl = displayContent.getSurfaceControl();
                    this.mDisplayHashController.calculateDisplayHashBoundsLocked(windowForClientLocked, rect, rect2);
                    if (rect2.isEmpty()) {
                        Slog.w("WindowManager", "Failed to generate DisplayHash. Bounds are not on screen");
                        this.mDisplayHashController.getClass();
                        DisplayHashController.sendDisplayHashError(-4, remoteCallback);
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    resetPriorityAfterLockedSection();
                    int i2 = session.mUid;
                    ScreenCapture.LayerCaptureArgs.Builder sourceCrop = new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setUid(i2).setSourceCrop(rect2);
                    final DisplayHashController displayHashController = this.mDisplayHashController;
                    if (displayHashController.mDisplayHashThrottlingEnabled) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (displayHashController.mLastRequestUid != i2) {
                            displayHashController.mLastRequestUid = i2;
                            displayHashController.mLastRequestTimeMs = currentTimeMillis;
                        } else {
                            synchronized (displayHashController.mIntervalBetweenRequestsLock) {
                                try {
                                    i = displayHashController.mIntervalBetweenRequestMillis;
                                    if (i == -1) {
                                        i = new DisplayHashController.SyncCommand(displayHashController).run(new DisplayHashController$$ExternalSyntheticLambda1(0)).getInt("android.service.displayhash.extra.INTERVAL_BETWEEN_REQUESTS", 0);
                                        displayHashController.mIntervalBetweenRequestMillis = i;
                                    }
                                } finally {
                                }
                            }
                            if (currentTimeMillis - displayHashController.mLastRequestTimeMs < i) {
                                DisplayHashController.sendDisplayHashError(-6, remoteCallback);
                                return;
                            }
                            displayHashController.mLastRequestTimeMs = currentTimeMillis;
                        }
                    }
                    DisplayHashParams displayHashParams = (DisplayHashParams) ((HashMap) displayHashController.getDisplayHashAlgorithms()).get(str);
                    if (displayHashParams == null) {
                        Slog.w("WindowManager", "Failed to generateDisplayHash. Invalid hashAlgorithm");
                        DisplayHashController.sendDisplayHashError(-5, remoteCallback);
                        return;
                    }
                    Size bufferSize = displayHashParams.getBufferSize();
                    if (bufferSize != null && (bufferSize.getWidth() > 0 || bufferSize.getHeight() > 0)) {
                        sourceCrop.setFrameScale(bufferSize.getWidth() / rect.width(), bufferSize.getHeight() / rect.height());
                    }
                    sourceCrop.setGrayscale(displayHashParams.isGrayscaleBuffer());
                    ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(sourceCrop.build());
                    if (captureLayers == null || captureLayers.getHardwareBuffer() == null) {
                        Slog.w("WindowManager", "Failed to generate DisplayHash. Couldn't capture content");
                        DisplayHashController.sendDisplayHashError(-1, remoteCallback);
                        return;
                    } else {
                        final HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
                        displayHashController.connectAndRun(new DisplayHashController.Command() { // from class: com.android.server.wm.DisplayHashController$$ExternalSyntheticLambda0
                            @Override // com.android.server.wm.DisplayHashController.Command
                            public final void run(IDisplayHashingService iDisplayHashingService) {
                                iDisplayHashingService.generateDisplayHash(DisplayHashController.this.mSalt, hardwareBuffer, rect, str, remoteCallback);
                            }
                        });
                        return;
                    }
                }
                this.mDisplayHashController.getClass();
                DisplayHashController.sendDisplayHashError(-3, remoteCallback);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final float getAnimationScale(int i) {
        return i != 0 ? i != 1 ? i != 2 ? FullScreenMagnificationGestureHandler.MAX_SCALE : this.mAnimatorDurationScaleSetting : this.mTransitionAnimationScaleSetting : this.mWindowAnimationScaleSetting;
    }

    public final float[] getAnimationScales() {
        return new float[]{this.mWindowAnimationScaleSetting, this.mTransitionAnimationScaleSetting, this.mAnimatorDurationScaleSetting};
    }

    public final int getAppContinuityMode(int i, String str, ActivityInfo activityInfo) {
        return 0;
    }

    public final List getBackupKeyCustomizationInfoList() {
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "getBackupKeyCustomizationInfoList", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        KeyCustomizationInfoManager keyCustomizationInfoManager = this.mExt.mPolicyExt.mKeyCustomizationPolicy.mKeyCustomizationInfoManager;
        synchronized (keyCustomizationInfoManager.mLock) {
            try {
                ArrayList arrayList = new ArrayList();
                SparseArray sparseArray = keyCustomizationInfoManager.mHotKeyMap;
                for (int i = 0; i < sparseArray.size(); i++) {
                    int keyAt = sparseArray.keyAt(i);
                    ComponentName componentName = (ComponentName) sparseArray.get(keyAt);
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    arrayList.add(new SemWindowManager.KeyCustomizationInfo(3, 1000, keyAt, 1, intent));
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
                return null;
            } finally {
            }
        }
    }

    public final int getBaseDisplayDensity(int i) {
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

    public final void getBaseDisplaySize(int i, Point point) {
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

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final int getCameraLensCoverState() {
        int switchState = this.mInputManager.mNative.getSwitchState(-1, -256, 9);
        if (switchState > 0) {
            return 1;
        }
        return switchState == 0 ? 0 : -1;
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

    public final float getCurrentAnimatorScale() {
        float f;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                f = this.mAnimationsDisabled ? FullScreenMagnificationGestureHandler.MAX_SCALE : this.mAnimatorDurationScaleSetting;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return f;
    }

    public final Region getCurrentImeTouchRegion() {
        getCurrentImeTouchRegion_enforcePermission();
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

    public final DisplayContent getDefaultDisplayContentLocked() {
        return this.mRoot.getDisplayContent(0);
    }

    public final int getDefaultDisplayRotation() {
        int i;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                i = getDefaultDisplayContentLocked().mDisplayRotation.mRotation;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return i;
    }

    public final DisplayContent getDisplayContentOrCreate(int i, IBinder iBinder) {
        WindowToken windowToken;
        return (iBinder == null || (windowToken = this.mRoot.getWindowToken(iBinder)) == null) ? this.mRoot.getDisplayContentOrCreate(i) : windowToken.getDisplayContent();
    }

    public final int getDisplayIdByUniqueId(String str) {
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

    public final int getDisplayImePolicy(int i) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "getDisplayImePolicy()", true)) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        Map map = this.mDisplayImePolicyCache;
        if (map.containsKey(Integer.valueOf(i))) {
            return ((Integer) map.get(Integer.valueOf(i))).intValue();
        }
        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 5177195624625618567L, 1, "Attempted to get IME policy of a display that does not exist: %d", Long.valueOf(i));
        }
        return 1;
    }

    public final int getDisplayUserRotation(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w("WindowManager", "Trying to get user rotation of a missing display.");
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int i2 = displayContent.mDisplayRotation.mUserRotation;
                resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final int getDockedStackSide() {
        return 0;
    }

    public final ArrayList getExcludeLayers(WindowContainer windowContainer) {
        Task asTask = windowContainer.asTask();
        if (asTask == null) {
            return new ArrayList();
        }
        EmbeddedWindowController embeddedWindowController = this.mEmbeddedWindowController;
        WindowContainerToken windowContainerToken = asTask.mRemoteToken.toWindowContainerToken();
        embeddedWindowController.getClass();
        ArrayList arrayList = new ArrayList();
        for (int size = embeddedWindowController.mWindows.size() - 1; size >= 0; size--) {
            EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) embeddedWindowController.mWindows.valueAt(size);
            if (windowContainerToken.equals(embeddedWindow.mTaskToken)) {
                arrayList.add(embeddedWindow.mSurface);
            }
        }
        return arrayList;
    }

    public final WindowState getFocusedWindow() {
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

    public final WindowState getFocusedWindowLocked() {
        return this.mRoot.getTopFocusedDisplayContent().mCurrentFocus;
    }

    public final int getForcedDisplayDensityForUserLocked() {
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

    public final int getFullScreenAppsSupportMode() {
        return FullScreenAppsSupportUtils.LazyHolder.sUtils.getFullScreenAppsSupportMode();
    }

    public final int getImeDisplayId() {
        int i;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent topFocusedDisplayContent = this.mRoot.getTopFocusedDisplayContent();
                i = topFocusedDisplayContent.getImePolicy() == 0 ? topFocusedDisplayContent.mDisplayId : 0;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return i;
    }

    public final int getInitialDisplayDensity(int i) {
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

    public final void getInitialDisplaySize(int i, Point point) {
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

    public final InputManagerCallback getInputManagerCallback() {
        return this.mInputManagerCallback;
    }

    public final InputTarget getInputTargetFromToken(IBinder iBinder) {
        WindowState windowState = (WindowState) this.mInputToWindowMap.get(iBinder);
        if (windowState != null) {
            return windowState;
        }
        EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) this.mEmbeddedWindowController.mWindows.get(iBinder);
        if (embeddedWindow != null) {
            return embeddedWindow;
        }
        return null;
    }

    public final InputTarget getInputTargetFromWindowTokenLocked(IBinder iBinder) {
        InputTarget inputTarget = (InputTarget) this.mWindowMap.get(iBinder);
        return inputTarget != null ? inputTarget : (EmbeddedWindowController.EmbeddedWindow) this.mEmbeddedWindowController.mWindowsByWindowToken.get(iBinder);
    }

    public final SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfo(int i, int i2, int i3) {
        return this.mExt.mPolicyExt.mKeyCustomizationPolicy.mKeyCustomizationInfoManager.get(i, i2, i3, null);
    }

    public final SemWindowManager.KeyCustomizationInfo getKeyCustomizationInfoByPackage(String str, int i, int i2) {
        return this.mExt.mPolicyExt.mKeyCustomizationPolicy.mKeyCustomizationInfoManager.get(2003, i, i2, str);
    }

    public final SemWindowManager.KeyCustomizationInfo getLastKeyCustomizationInfo(int i, int i2) {
        return this.mExt.mPolicyExt.mKeyCustomizationPolicy.mKeyCustomizationInfoManager.getLast(i, i2);
    }

    public final int getLetterboxBackgroundColorInArgb() {
        return this.mAppCompatConfiguration.getLetterboxBackgroundColor().toArgb();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final int getLidState() {
        int switchState = this.mInputManager.mNative.getSwitchState(-1, -256, 0);
        if (switchState > 0) {
            return 0;
        }
        return switchState == 0 ? 1 : -1;
    }

    public final int getMaxAspectRatioPolicy(String str, int i) {
        return 3;
    }

    public final void getOverrideStableInsets(int i, Rect rect) throws RemoteException {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                getOverrideStableInsetsLocked(i, rect);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void getOverrideStableInsetsLocked(int i, Rect rect) {
        rect.setEmpty();
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            DisplayInfo displayInfo = displayContent.mDisplayInfo;
            rect.set(displayContent.mDisplayPolicy.getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight).mOverrideConfigInsets);
        }
    }

    public final List getPossibleDisplayInfo(int i) {
        List possibleDisplayInfos;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mAtmService.mRecentTasks.isCallerRecents(callingUid) || (com.android.window.flags.Flags.multiCrop() && callingUid == 1000)) {
                        possibleDisplayInfos = this.mPossibleDisplayInfoMapper.getPossibleDisplayInfos(i);
                    } else {
                        Slog.e("WindowManager", "Unable to verify uid for getPossibleDisplayInfo on uid " + callingUid);
                        possibleDisplayInfos = new ArrayList();
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

    public final int getPreferredOptionsPanelGravity(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                int i2 = 81;
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return 81;
                }
                int i3 = displayContent.mDisplayRotation.mRotation;
                if (displayContent.mInitialDisplayWidth < displayContent.mInitialDisplayHeight) {
                    if (i3 != 1) {
                        if (i3 != 3) {
                        }
                        i2 = 8388691;
                    }
                    i2 = 85;
                } else if (i3 != 1) {
                    if (i3 != 2) {
                        if (i3 != 3) {
                            i2 = 85;
                        }
                    }
                    i2 = 8388691;
                }
                resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final int getRemoveContentMode(int i) {
        int i2 = 1;
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "getRemoveContentMode()", true)) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 4200292050699107329L, 1, "Attempted to get remove mode of a display that does not exist: %d", Long.valueOf(i));
                    }
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                displayWindowSettings.getClass();
                int i3 = displayWindowSettings.mSettingsProvider.getSettings(displayContent.mDisplayInfo).mRemoveContentMode;
                if (i3 != 0) {
                    i2 = i3;
                } else if (displayContent.isPrivate()) {
                    i2 = 2;
                }
                resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final String getRequestFocusPkg() {
        String str;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                str = getDefaultDisplayContentLocked().mInputMonitor.mFreezeExceptionPkg;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return str;
    }

    public final int getRotationLockOrientation(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                DisplayRotation displayRotation = displayContent.mDisplayRotation;
                int i2 = displayRotation.isAnyPortrait(displayRotation.mUserRotation) ? 1 : 2;
                resetPriorityAfterLockedSection();
                return i2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void getStableInsets(int i, Rect rect) throws RemoteException {
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

    public final void getStableInsetsLocked(int i, Rect rect) {
        rect.setEmpty();
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            DisplayInfo displayInfo = displayContent.mDisplayInfo;
            rect.set(displayContent.mDisplayPolicy.getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight).mConfigInsets);
        }
    }

    public final String[] getSupportedDisplayHashAlgorithms() {
        return (String[]) this.mDisplayHashController.getDisplayHashAlgorithms().keySet().toArray(new String[0]);
    }

    public final int getSupportsFlexPanel(int i, String str) {
        if (!CoreRune.FW_FLEX_PANEL_CONTROL) {
            return 2;
        }
        ActivityTaskManagerService.enforceTaskPermission("getSupportsFlexPanel()");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("getSupportsFlexPanel, packageName=", str));
        }
        return this.mAtmService.mExt.mFlexPanelController.getSupportsFlexPanel(i, str);
    }

    public final TaskSnapshot getTaskSnapshot(int i, int i2, boolean z, boolean z2) {
        return this.mTaskSnapshotController.getSnapshot(i, i2, z2, z, false);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.server.wm.WindowManagerService.WindowContainerInfo getTaskWindowContainerInfoForRecordingSession(android.view.ContentRecordingSession r7) {
        /*
            r6 = this;
            android.os.IBinder r0 = r7.getTokenToRecord()
            java.lang.String r1 = "WindowManager"
            r2 = 0
            if (r0 == 0) goto L32
            android.os.IBinder r0 = r7.getTokenToRecord()
            com.android.server.wm.RootWindowContainer r3 = r6.mRoot
            com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda0 r4 = new com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda0
            r5 = 0
            r4.<init>(r5, r0)
            com.android.server.wm.ActivityRecord r0 = r3.getActivity(r4)
            if (r0 != 0) goto L21
            java.lang.String r0 = "Unable to find the activity for this launch cookie"
            android.util.Slog.w(r1, r0)
            goto L32
        L21:
            com.android.server.wm.Task r0 = r0.task
            if (r0 != 0) goto L2b
            java.lang.String r0 = "Unable to find the task for this launch cookie"
            android.util.Slog.w(r1, r0)
            goto L32
        L2b:
            com.android.server.wm.WindowContainer$RemoteToken r3 = r0.mRemoteToken
            android.window.WindowContainerToken r3 = r3.toWindowContainerToken()
            goto L34
        L32:
            r0 = r2
            r3 = r0
        L34:
            if (r3 != 0) goto L5b
            int r4 = r7.getTaskId()
            r5 = -1
            if (r4 == r5) goto L5b
            int r7 = r7.getTaskId()
            com.android.server.wm.RootWindowContainer r6 = r6.mRoot
            com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda1 r0 = new com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda1
            r4 = 0
            r0.<init>(r7, r4)
            com.android.server.wm.Task r0 = r6.getTask(r0)
            if (r0 != 0) goto L55
            java.lang.String r6 = "Unable to find the task for this projection"
            android.util.Slog.w(r1, r6)
            goto L5b
        L55:
            com.android.server.wm.WindowContainer$RemoteToken r6 = r0.mRemoteToken
            android.window.WindowContainerToken r3 = r6.toWindowContainerToken()
        L5b:
            if (r3 != 0) goto L63
            java.lang.String r6 = "Unable to find the WindowContainerToken for ContentRecordingSession"
            android.util.Slog.w(r1, r6)
            return r2
        L63:
            com.android.server.wm.WindowManagerService$WindowContainerInfo r6 = new com.android.server.wm.WindowManagerService$WindowContainerInfo
            int r7 = r0.effectiveUid
            r6.<init>(r7, r3)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.getTaskWindowContainerInfoForRecordingSession(android.view.ContentRecordingSession):com.android.server.wm.WindowManagerService$WindowContainerInfo");
    }

    public final int getTopFocusedDisplayId() {
        int i;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                i = this.mRoot.mTopFocusedDisplayId;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return i;
    }

    public final float getTransitionAnimationScaleLocked() {
        return this.mAnimationsDisabled ? FullScreenMagnificationGestureHandler.MAX_SCALE : this.mTransitionAnimationScaleSetting;
    }

    public final int getUserDisplayDensity() {
        return this.mExt.mMultiResolutionController.getForcedDisplayDensity();
    }

    public final void getUserDisplaySize(Point point) {
        if (point == null) {
            return;
        }
        this.mExt.mMultiResolutionController.getForcedDisplaySize(point);
    }

    public final ArrayList getVisibleWinSurfacePkgList() {
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    Session session = (Session) this.mSessions.valueAt(size);
                    if (session.mWinSurfaceVisibleCount > 0 && !arrayList.contains(session.mPackageName)) {
                        arrayList.add(session.mPackageName);
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

    public final List getVisibleWindowInfoList() {
        if (!checkCallingPermission$1("android.permission.MANAGE_ACTIVITY_TASKS", "getVisibleWindowInfoList()", false) && !checkCallingPermission$1("android.permission.RETRIEVE_WINDOW_CONTENT", "getVisibleWindowInfoList()", false)) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS or RETRIEVE_WINDOW_CONTENT permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mExt.getVisibleWindowInfoList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final float getWindowAnimationScaleLocked() {
        return this.mAnimationsDisabled ? FullScreenMagnificationGestureHandler.MAX_SCALE : this.mWindowAnimationScaleSetting;
    }

    public final WindowContentFrameStats getWindowContentFrameStats(IBinder iBinder) {
        if (!checkCallingPermission$1("android.permission.FRAME_STATS", "getWindowContentFrameStats()", true)) {
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
                SurfaceControl surfaceControl = windowSurfaceController.mSurfaceControl;
                if (surfaceControl == null ? false : surfaceControl.getContentFrameStats(windowContentFrameStats)) {
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

    public final IWindowId getWindowId(IBinder iBinder) {
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

    public final boolean getWindowInsets(int i, IBinder iBinder, InsetsState insetsState) {
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
                    displayContentOrCreate.mInsetsPolicy.getInsetsForWindowMetrics(displayContentOrCreate.getWindowToken(iBinder), insetsState);
                    displayContentOrCreate.mDisplayPolicy.getClass();
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final Object getWindowManagerLock() {
        return this.mGlobalLock;
    }

    public final int getWindowingMode(int i) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "getWindowingMode()", true)) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -1875125162673622728L, 1, "Attempted to get windowing mode of a display that does not exist: %d", Long.valueOf(i));
                    }
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                displayWindowSettings.getClass();
                int windowingModeLocked = displayWindowSettings.getWindowingModeLocked(displayWindowSettings.mSettingsProvider.getSettings(displayContent.mDisplayInfo), displayContent);
                resetPriorityAfterLockedSection();
                return windowingModeLocked;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void grantEmbeddedWindowFocus(Session session, IWindow iWindow, InputTransferToken inputTransferToken, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    Slog.e("WindowManager", "Host window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowForClientLocked.mInputChannel == null) {
                    Slog.e("WindowManager", "Host window does not have an input channel");
                    resetPriorityAfterLockedSection();
                    return;
                }
                EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) this.mEmbeddedWindowController.mWindowsByInputTransferToken.get(inputTransferToken);
                if (embeddedWindow == null) {
                    Slog.e("WindowManager", "Embedded window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (embeddedWindow.mHostWindowState != windowForClientLocked) {
                    Slog.e("WindowManager", "Embedded window does not belong to the host");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (z) {
                    InputWindowHandleWrapper inputWindowHandleWrapper = windowForClientLocked.mInputWindowHandle;
                    InputChannel inputChannel = embeddedWindow.mInputChannel;
                    IBinder token = inputChannel != null ? inputChannel.getToken() : null;
                    InputWindowHandle inputWindowHandle = inputWindowHandleWrapper.mHandle;
                    if (inputWindowHandle.focusTransferTarget != token) {
                        inputWindowHandle.focusTransferTarget = token;
                        inputWindowHandleWrapper.mChanged = true;
                    }
                    EventLog.writeEvent(62001, "Transfer focus request " + embeddedWindow, "reason=grantEmbeddedWindowFocus(true)");
                } else {
                    InputWindowHandleWrapper inputWindowHandleWrapper2 = windowForClientLocked.mInputWindowHandle;
                    InputWindowHandle inputWindowHandle2 = inputWindowHandleWrapper2.mHandle;
                    if (inputWindowHandle2.focusTransferTarget != null) {
                        inputWindowHandle2.focusTransferTarget = null;
                        inputWindowHandleWrapper2.mChanged = true;
                    }
                    EventLog.writeEvent(62001, "Transfer focus request " + windowForClientLocked, "reason=grantEmbeddedWindowFocus(false)");
                }
                DisplayContent displayContent = this.mRoot.getDisplayContent(windowForClientLocked.getDisplayId());
                if (displayContent != null) {
                    displayContent.mInputMonitor.updateInputWindowsLw(true);
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_FOCUS, -6056928081282320632L, 0, null, String.valueOf(embeddedWindow), String.valueOf(z));
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void grantEmbeddedWindowFocus(Session session, InputTransferToken inputTransferToken, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) this.mEmbeddedWindowController.mWindowsByInputTransferToken.get(inputTransferToken);
                if (embeddedWindow == null) {
                    Slog.e("WindowManager", "Embedded window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (embeddedWindow.mSession != session) {
                    Slog.e("WindowManager", "Window not in session:" + session);
                    resetPriorityAfterLockedSection();
                    return;
                }
                InputChannel inputChannel = embeddedWindow.mInputChannel;
                IBinder token = inputChannel != null ? inputChannel.getToken() : null;
                if (token == null) {
                    Slog.e("WindowManager", "Focus token found but input channel token not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionFactory.get();
                int i = embeddedWindow.mDisplayId;
                if (embeddedWindow.mFocusGranted != z) {
                    embeddedWindow.mFocusGranted = z;
                    StringBuilder sb = new StringBuilder("onGrantFocusChanged: ");
                    sb.append(z);
                    sb.append(", this=");
                    sb.append(embeddedWindow);
                    sb.append(", Callers=");
                    ActivityManagerService$$ExternalSyntheticOutline0.m(2, sb, "EmbeddedWindow");
                }
                if (z) {
                    transaction.setFocusedWindow(token, embeddedWindow.mName, i).apply();
                    EventLog.writeEvent(62001, "Focus request " + embeddedWindow, "reason=grantEmbeddedWindowFocus(true)");
                } else {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    WindowState findFocusedWindow = displayContent == null ? null : displayContent.findFocusedWindow();
                    if (findFocusedWindow == null) {
                        transaction.setFocusedWindow(null, null, i).apply();
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[1]) {
                            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_FOCUS, -7394143854567081754L, 0, null, String.valueOf(embeddedWindow));
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    transaction.setFocusedWindow(findFocusedWindow.mInputChannelToken, findFocusedWindow.getName(), i).apply();
                    EventLog.writeEvent(62001, "Focus request " + findFocusedWindow, "reason=grantEmbeddedWindowFocus(false)");
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_FOCUS, -6056928081282320632L, 0, null, String.valueOf(embeddedWindow), String.valueOf(z));
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void grantInputChannel(Session session, int i, int i2, int i3, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i4, int i5, int i6, int i7, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel) {
        WindowState windowState;
        int sanitizeWindowType = sanitizeWindowType(session, i3, iBinder2, i7);
        Objects.requireNonNull(inputChannel);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (inputTransferToken != null) {
                    try {
                        windowState = (WindowState) this.mInputToWindowMap.get(inputTransferToken.getToken());
                    } catch (Throwable th) {
                        th = th;
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                } else {
                    windowState = null;
                }
                EmbeddedWindowController.EmbeddedWindow embeddedWindow = new EmbeddedWindowController.EmbeddedWindow(session, this, iBinder, windowState, i, i2, sanitizeWindowType, i3, inputTransferToken2, str, (i4 & 8) == 0, null, null);
                InputChannel createInputChannel = embeddedWindow.mWmService.mInputManager.createInputChannel(embeddedWindow.mName);
                embeddedWindow.mInputChannel = createInputChannel;
                createInputChannel.copyTo(inputChannel);
                this.mEmbeddedWindowController.add(inputChannel.getToken(), embeddedWindow);
                InputApplicationHandle applicationHandle = embeddedWindow.getApplicationHandle();
                String str2 = embeddedWindow.mName;
                resetPriorityAfterLockedSection();
                updateInputChannel(inputChannel.getToken(), i, i2, i3, surfaceControl, str2, applicationHandle, i4, i5, i6, sanitizeWindowType, null, iBinder, 0, null, null);
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public final void grantInputChannelWithTaskToken(Session session, int i, int i2, int i3, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i4, int i5, int i6, int i7, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel, int i8, WindowContainerToken windowContainerToken) {
        WindowState windowState;
        int sanitizeWindowType = sanitizeWindowType(session, i3, iBinder2, i7);
        Objects.requireNonNull(inputChannel);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (inputTransferToken != null) {
                    try {
                        windowState = (WindowState) this.mInputToWindowMap.get(inputTransferToken.getToken());
                    } catch (Throwable th) {
                        th = th;
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                } else {
                    windowState = null;
                }
                EmbeddedWindowController.EmbeddedWindow embeddedWindow = new EmbeddedWindowController.EmbeddedWindow(session, this, iBinder, windowState, i, i2, sanitizeWindowType, i3, inputTransferToken2, str, (i4 & 8) == 0, surfaceControl, windowContainerToken);
                InputChannel createInputChannel = embeddedWindow.mWmService.mInputManager.createInputChannel(embeddedWindow.mName);
                embeddedWindow.mInputChannel = createInputChannel;
                createInputChannel.copyTo(inputChannel);
                this.mEmbeddedWindowController.add(inputChannel.getToken(), embeddedWindow);
                InputApplicationHandle applicationHandle = embeddedWindow.getApplicationHandle();
                String str2 = embeddedWindow.mName;
                resetPriorityAfterLockedSection();
                updateInputChannel(inputChannel.getToken(), i, i2, i3, surfaceControl, str2, applicationHandle, i4, i5, i6, sanitizeWindowType, null, iBinder, i8, windowContainerToken, null);
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public void handleTaskFocusChange(Task task, ActivityRecord activityRecord) {
        if (task == null) {
            return;
        }
        FreeformController freeformController = this.mAtmService.mFreeformController;
        DisplayContent displayContent = task.mDisplayContent;
        freeformController.getClass();
        Task rootTask = displayContent.getDefaultTaskDisplayArea().getRootTask(5, 0);
        if ((rootTask == null || !rootTask.shouldBeVisible(null)) && task.isActivityTypeHome()) {
            TaskDisplayArea displayArea = task.getDisplayArea();
            WindowState focusedWindow = getFocusedWindow();
            if (focusedWindow != null && displayArea != null && focusedWindow.isDescendantOf(displayArea)) {
                return;
            }
        }
        this.mAtmService.setFocusedTask(task.mTaskId, activityRecord);
    }

    public final boolean hasFloatingOrOnScreenWindow(int i) {
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows((Consumer) new WindowManagerService$$ExternalSyntheticLambda5(arrayList, 1), true);
                for (0; i2 < arrayList.size(); i2 + 1) {
                    WindowState windowState = (WindowState) arrayList.get(i2);
                    i2 = (windowState.mOwnerUid == i && ((i3 = windowState.mAppOp) == 24 || i3 == 45 || windowState.isOnScreen())) ? 0 : i2 + 1;
                    resetPriorityAfterLockedSection();
                    return true;
                }
                resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean hasNavigationBar(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean z = displayContent.mDisplayPolicy.mHasNavigationBar;
                resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean hasStatusBarPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.STATUS_BAR", i, i2) == 0;
    }

    public final boolean hasTaskbarTarget() {
        boolean z;
        if (!CoreRune.FW_NOTIFY_TASKBAR_VISIBLE) {
            return false;
        }
        if (!checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "hasTaskbarTarget()", true)) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = this.mExt.mReportedTaskbarTarget != null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return z;
    }

    public final boolean hasTouchModePermission(int i) {
        WindowProcessController process;
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                process = activityTaskManagerService.mProcessMap.getProcess(i);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return (process != null && process.mInstrumenting && ActivityManagerService.checkComponentPermission(-1, process.mInstrumentationSourceUid, "android.permission.MODIFY_TOUCH_MODE_STATE", 0, -1, true) == 0) || checkCallingPermission$1("android.permission.MODIFY_TOUCH_MODE_STATE", "setInTouchMode()", false);
    }

    public final void hideBootMessagesLocked() {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
            boolean z = this.mDisplayEnabled;
            boolean z2 = this.mForceDisplayEnabled;
            boolean z3 = this.mShowingBootMessages;
            boolean z4 = this.mSystemBooted;
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, 2994810644159608200L, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, null, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), String.valueOf(new RuntimeException("here").fillInStackTrace()));
        }
        if (this.mShowingBootMessages) {
            this.mShowingBootMessages = false;
            ((PhoneWindowManager) this.mPolicy).mHandler.sendEmptyMessage(11);
        }
    }

    public final void hideTransientBars(int i) {
        if (!checkCallingPermission$1("android.permission.STATUS_BAR", "hideTransientBars()", true)) {
            throw new SecurityException("Requires STATUS_BAR permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    WindowState windowState = displayContent.mDisplayPolicy.mExt.mGameToolsWindow;
                    if (windowState != null && windowState.isVisible()) {
                        Slog.w("WindowManager", "hideTransientBars skipped by game tools win");
                        resetPriorityAfterLockedSection();
                        return;
                    } else {
                        InsetsPolicy insetsPolicy = displayContent.mInsetsPolicy;
                        if (insetsPolicy.mShowingTransientTypes != 0) {
                            insetsPolicy.dispatchTransientSystemBarsVisibilityChanged(insetsPolicy.mFocusedWin, false, false);
                            insetsPolicy.mShowingTransientTypes = 0;
                            insetsPolicy.updateBarControlTarget(insetsPolicy.mFocusedWin);
                        }
                    }
                } else {
                    Slog.w("WindowManager", "hideTransientBars with invalid displayId=" + i);
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void holdLock(IBinder iBinder, int i) {
        ((PackageManagerService) this.mTestUtilityService).verifyHoldLockToken(iBinder);
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

    public final void initializeRecentsAnimation(int i, IRecentsAnimationRunner iRecentsAnimationRunner, RecentsAnimation recentsAnimation, int i2, SparseBooleanArray sparseBooleanArray, ActivityRecord activityRecord) {
        IStatusBar iStatusBar;
        ActivityRecord activityRecord2;
        WindowToken windowToken;
        this.mRecentsAnimationController = new RecentsAnimationController(this, iRecentsAnimationRunner, recentsAnimation, i2);
        this.mRoot.getDisplayContent(i2).mAppTransition.updateBooster();
        RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
        recentsAnimationController.mTargetActivityType = i;
        recentsAnimationController.mDisplayContent.mAppTransition.registerListenerLocked(recentsAnimationController.mAppTransitionListener);
        TaskDisplayArea defaultTaskDisplayArea = recentsAnimationController.mDisplayContent.getDefaultTaskDisplayArea();
        defaultTaskDisplayArea.getClass();
        ArrayList arrayList = new ArrayList();
        defaultTaskDisplayArea.forAllTasks(new TaskDisplayArea$$ExternalSyntheticLambda0(0, arrayList));
        Task rootTask = recentsAnimationController.mDisplayContent.getDefaultTaskDisplayArea().getRootTask(0, i);
        if (rootTask != null) {
            rootTask.forAllLeafTasks(new RecentsAnimationController$$ExternalSyntheticLambda1(0, arrayList), true);
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            final Task task = (Task) arrayList.get(size);
            WindowConfiguration windowConfiguration = task.getWindowConfiguration();
            if (!task.isAlwaysOnTop() && !windowConfiguration.tasksAreFloating()) {
                if (task.mIsDragSourceTask) {
                    Slog.d("RecentsAnimationController", "[TWODND] initialize-DragSourceTask");
                    task.mHiddenWhileActivatingDrag = true;
                    task.mIsAnimatingByRecentsAndDragSourceTask = true;
                }
                recentsAnimationController.addAnimation(task, !sparseBooleanArray.get(task.mTaskId), false, new SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda2
                    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                    public final void onAnimationFinished(final int i3, final AnimationAdapter animationAdapter) {
                        Task.this.forAllWindows(new Consumer() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((WindowState) obj).onAnimationFinished(i3, animationAdapter);
                            }
                        }, true);
                    }
                });
            }
        }
        if (recentsAnimationController.mPendingAnimations.isEmpty()) {
            recentsAnimationController.cancelAnimation(2, "initialize-noVisibleTasks", false);
            return;
        }
        try {
            if (!recentsAnimationController.mLinkedToDeathOfRunner) {
                recentsAnimationController.mRunner.asBinder().linkToDeath(recentsAnimationController, 0);
                recentsAnimationController.mLinkedToDeathOfRunner = true;
            }
            if (recentsAnimationController.mDisplayContent.mDisplayPolicy.shouldAttachNavBarToAppDuringTransition() && recentsAnimationController.mDisplayContent.getAsyncRotationController() == null) {
                int size2 = recentsAnimationController.mPendingAnimations.size() - 1;
                while (true) {
                    if (size2 < 0) {
                        break;
                    }
                    Task task2 = ((RecentsAnimationController.TaskAnimationAdapter) recentsAnimationController.mPendingAnimations.get(size2)).mTask;
                    if (!task2.isActivityTypeHomeOrRecents()) {
                        recentsAnimationController.mNavBarAttachedApp = task2.getTopVisibleActivity(false, false);
                        break;
                    }
                    size2--;
                }
                WindowState navigationBarWindow = recentsAnimationController.getNavigationBarWindow();
                if (recentsAnimationController.mNavBarAttachedApp != null && navigationBarWindow != null && (windowToken = navigationBarWindow.mToken) != null) {
                    recentsAnimationController.mNavigationBarAttachedToApp = true;
                    windowToken.cancelAnimation();
                    SurfaceControl.Transaction pendingTransaction = navigationBarWindow.mToken.getPendingTransaction();
                    SurfaceControl surfaceControl = navigationBarWindow.mToken.mSurfaceControl;
                    navigationBarWindow.mSurfaceTranslationY = -recentsAnimationController.mNavBarAttachedApp.getBounds().top;
                    pendingTransaction.reparent(surfaceControl, recentsAnimationController.mNavBarAttachedApp.mSurfaceControl);
                    pendingTransaction.show(surfaceControl);
                    DisplayContent.ImeContainer imeContainer = recentsAnimationController.mDisplayContent.mImeWindowsContainer;
                    if (imeContainer.isVisible()) {
                        pendingTransaction.setRelativeLayer(surfaceControl, imeContainer.getSurfaceControl(), 1);
                    } else {
                        pendingTransaction.setLayer(surfaceControl, Integer.MAX_VALUE);
                    }
                    StatusBarManagerInternal statusBarManagerInternal = recentsAnimationController.mStatusBar;
                    if (statusBarManagerInternal != null) {
                        int i3 = recentsAnimationController.mDisplayId;
                        IStatusBar iStatusBar2 = StatusBarManagerService.this.mBar;
                        if (iStatusBar2 != null) {
                            try {
                                iStatusBar2.setNavigationBarLumaSamplingEnabled(i3, false);
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                }
            }
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -5305978958548091997L, 0, null, String.valueOf(activityRecord.toString()));
            }
            recentsAnimationController.mTargetActivityRecord = activityRecord;
            if (activityRecord.windowsCanBeWallpaperTarget()) {
                DisplayContent displayContent = recentsAnimationController.mDisplayContent;
                displayContent.pendingLayoutChanges |= 4;
                displayContent.setLayoutNeeded();
            }
            recentsAnimationController.mService.mWindowPlacerLocked.performSurfacePlacement(false);
            DisplayContent.FixedRotationTransitionListener fixedRotationTransitionListener = recentsAnimationController.mDisplayContent.mFixedRotationTransitionListener;
            fixedRotationTransitionListener.mAnimatingRecents = activityRecord;
            boolean z = activityRecord.mVisible;
            DisplayContent displayContent2 = DisplayContent.this;
            if (!z || (activityRecord2 = displayContent2.mFocusedApp) == null || activityRecord2.occludesParent(false)) {
                int rotationForActivityInDifferentOrientation = displayContent2.rotationForActivityInDifferentOrientation(activityRecord);
                if (rotationForActivityInDifferentOrientation != -1) {
                    displayContent2.startFixedRotationTransform(activityRecord, rotationForActivityInDifferentOrientation);
                }
                if (activityRecord.hasFixedRotationTransform()) {
                    displayContent2.setFixedRotationLaunchingApp(activityRecord.getWindowConfiguration().getRotation(), activityRecord);
                }
            }
            MultiTaskingController multiTaskingController = recentsAnimationController.mService.mAtmService.mMultiTaskingController;
            multiTaskingController.mH.removeMessages(3);
            multiTaskingController.mH.obtainMessage(3, 1, 0).sendToTarget();
            StatusBarManagerInternal statusBarManagerInternal2 = recentsAnimationController.mStatusBar;
            if (statusBarManagerInternal2 == null || (iStatusBar = StatusBarManagerService.this.mBar) == null) {
                return;
            }
            try {
                iStatusBar.onRecentsAnimationStateChanged(true);
            } catch (RemoteException unused2) {
            }
        } catch (RemoteException unused3) {
            recentsAnimationController.cancelAnimation(2, "initialize-failedToLinkToDeath", false);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final boolean isAppTransitionStateIdle() {
        return getDefaultDisplayContentLocked().mAppTransition.mAppTransitionState == 0;
    }

    public final boolean isDisplayRotationFrozen(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w("WindowManager", "Trying to check if rotation is frozen on a missing display.");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean isRotationFrozen = displayContent.mDisplayRotation.isRotationFrozen();
                resetPriorityAfterLockedSection();
                return isRotationFrozen;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean isFolded() {
        return false;
    }

    public final boolean isGlobalKey(int i) {
        return ((PhoneWindowManager) this.mPolicy).mGlobalKeyManager.mKeyMapping.get(i) != null;
    }

    public final boolean isInTouchMode(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    boolean z = this.mContext.getResources().getBoolean(R.bool.config_deskDockEnablesAccelerometer);
                    resetPriorityAfterLockedSection();
                    return z;
                }
                boolean z2 = displayContent.mInTouchMode;
                resetPriorityAfterLockedSection();
                return z2;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean isKeyguardLocked() {
        return ((PhoneWindowManager) this.mPolicy).keyguardOn();
    }

    public final boolean isKeyguardSecure(int i) {
        if (i != UserHandle.getCallingUserId() && !checkCallingPermission$1("android.permission.INTERACT_ACROSS_USERS", "isKeyguardSecure", true)) {
            throw new SecurityException("Requires INTERACT_ACROSS_USERS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((PhoneWindowManager) this.mPolicy).isKeyguardSecure(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isKeyguardShowingAndNotOccluded() {
        return ((PhoneWindowManager) this.mPolicy).isKeyguardShowingAndNotOccluded();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isLayerTracing() {
        /*
            r6 = this;
            r0 = 1
            java.lang.String r1 = "android.permission.DUMP"
            java.lang.String r2 = "isLayerTracing()"
            boolean r6 = r6.checkCallingPermission$1(r1, r2, r0)
            if (r6 == 0) goto L65
            long r0 = android.os.Binder.clearCallingIdentity()
            r6 = 0
            r2 = 0
            java.lang.String r3 = "SurfaceFlinger"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r3)     // Catch: java.lang.Throwable -> L3d android.os.RemoteException -> L40
            if (r3 == 0) goto L52
            android.os.Parcel r4 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> L3d android.os.RemoteException -> L40
            android.os.Parcel r2 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> L3b android.os.RemoteException -> L41
            java.lang.String r5 = "android.ui.ISurfaceComposer"
            r2.writeInterfaceToken(r5)     // Catch: java.lang.Throwable -> L3b android.os.RemoteException -> L41
            r5 = 1026(0x402, float:1.438E-42)
            r3.transact(r5, r2, r4, r6)     // Catch: java.lang.Throwable -> L3b android.os.RemoteException -> L41
            boolean r6 = r4.readBoolean()     // Catch: java.lang.Throwable -> L3b android.os.RemoteException -> L41
            r2.recycle()     // Catch: java.lang.Throwable -> L39
            r4.recycle()     // Catch: java.lang.Throwable -> L39
            android.os.Binder.restoreCallingIdentity(r0)
            return r6
        L39:
            r6 = move-exception
            goto L61
        L3b:
            r6 = move-exception
            goto L56
        L3d:
            r6 = move-exception
            r4 = r2
            goto L56
        L40:
            r4 = r2
        L41:
            java.lang.String r3 = "WindowManager"
            java.lang.String r5 = "Failed to get layer tracing"
            android.util.Slog.e(r3, r5)     // Catch: java.lang.Throwable -> L3b
            if (r2 == 0) goto L4d
            r2.recycle()     // Catch: java.lang.Throwable -> L39
        L4d:
            if (r4 == 0) goto L52
            r4.recycle()     // Catch: java.lang.Throwable -> L39
        L52:
            android.os.Binder.restoreCallingIdentity(r0)
            return r6
        L56:
            if (r2 == 0) goto L5b
            r2.recycle()     // Catch: java.lang.Throwable -> L39
        L5b:
            if (r4 == 0) goto L60
            r4.recycle()     // Catch: java.lang.Throwable -> L39
        L60:
            throw r6     // Catch: java.lang.Throwable -> L39
        L61:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r6
        L65:
            java.lang.SecurityException r6 = new java.lang.SecurityException
            java.lang.String r0 = "Requires DUMP permission"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.isLayerTracing():boolean");
    }

    public final boolean isLetterboxBackgroundMultiColored() {
        int letterboxBackgroundType = this.mAppCompatConfiguration.getLetterboxBackgroundType();
        if (letterboxBackgroundType == 0) {
            return false;
        }
        if (letterboxBackgroundType == 1 || letterboxBackgroundType == 2 || letterboxBackgroundType == 3) {
            return true;
        }
        throw new AssertionError(VibrationParam$1$$ExternalSyntheticOutline0.m(letterboxBackgroundType, "Unexpected letterbox background type: "));
    }

    public final boolean isMetaKeyEventRequested(ComponentName componentName) {
        boolean contains;
        SystemKeyManager systemKeyManager = this.mExt.mPolicyExt.mSystemKeyPolicy;
        if (componentName == null) {
            systemKeyManager.getClass();
            return false;
        }
        synchronized (systemKeyManager) {
            contains = systemKeyManager.mMetaKeyRequestedComponents.contains(componentName.flattenToString());
        }
        return contains;
    }

    public final boolean isOnScreenWindow(int i) {
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows((Consumer) new WindowManagerService$$ExternalSyntheticLambda5(arrayList, 0), true);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    WindowState windowState = (WindowState) arrayList.get(i2);
                    if (windowState.mOwnerUid == i && windowState.isOnScreen()) {
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

    public final boolean isRotationFrozen() {
        return isDisplayRotationFrozen(0);
    }

    public final boolean isSafeModeEnabled() {
        return this.mSafeMode;
    }

    public final boolean isSystemKeyEventRequested(int i, ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return this.mExt.mPolicyExt.mSystemKeyPolicy.isSystemKeyEventRequested(i, componentName);
    }

    public final boolean isTableMode() {
        if (!CoreRune.FW_FLEXIBLE_TABLE_MODE) {
            return false;
        }
        this.mExt.getClass();
        throw null;
    }

    public final boolean isTaskSnapshotSupported() {
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

    public final boolean isTransitionTraceEnabled() {
        return this.mTransitionTracer.isTracing();
    }

    public final boolean isViewServerRunning() {
        ViewServer viewServer;
        Thread thread;
        return (isSystemSecure() || !checkCallingPermission$1("android.permission.DUMP", "isViewServerRunning", true) || (viewServer = this.mViewServer) == null || (thread = viewServer.mThread) == null || !thread.isAlive()) ? false : true;
    }

    public final boolean isWindowToken(IBinder iBinder) {
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

    public final boolean isWindowTraceEnabled() {
        return this.mWindowTracing.mEnabledLockFree;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void lockDeviceNow() {
        lockNow(null);
    }

    public final void lockNow(Bundle bundle) {
        ((PhoneWindowManager) this.mPolicy).lockNow(bundle);
    }

    public final SurfaceControl.Builder makeSurfaceBuilder(SurfaceSession surfaceSession) {
        return (SurfaceControl.Builder) this.mSurfaceControlFactory.apply(surfaceSession);
    }

    public final void makeWindowFreezingScreenIfNeededLocked(WindowState windowState) {
        int i = this.mFrozenDisplayId;
        if (i == -1 || i != windowState.getDisplayId() || this.mWindowsFreezingScreen == 2) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1233670725456443473L, 0, null, String.valueOf(windowState));
        }
        if (windowState.isVisibleRequested()) {
            windowState.setOrientationChanging(true);
        }
        if (this.mWindowsFreezingScreen == 0) {
            this.mWindowsFreezingScreen = 1;
            H h = this.mH;
            DisplayContent displayContent = windowState.getDisplayContent();
            h.removeMessages(11, displayContent);
            h.sendMessageDelayed(h.obtainMessage(11, displayContent), 2000L);
        }
    }

    public final void markSurfaceSyncGroupReady(IBinder iBinder) {
        SurfaceSyncGroup surfaceSyncGroup;
        SurfaceSyncGroupController surfaceSyncGroupController = this.mSurfaceSyncGroupController;
        synchronized (surfaceSyncGroupController.mLock) {
            SurfaceSyncGroupController.SurfaceSyncGroupData surfaceSyncGroupData = (SurfaceSyncGroupController.SurfaceSyncGroupData) surfaceSyncGroupController.mSurfaceSyncGroups.get(iBinder);
            if (surfaceSyncGroupData == null) {
                throw new IllegalArgumentException("SurfaceSyncGroup Token has not been set up or has already been marked as ready");
            }
            if (surfaceSyncGroupData.mOwningUid != Binder.getCallingUid()) {
                throw new IllegalArgumentException("Only process that created the SurfaceSyncGroup can call markSyncGroupReady");
            }
            surfaceSyncGroup = surfaceSyncGroupData.mSurfaceSyncGroup;
            surfaceSyncGroupController.mSurfaceSyncGroups.remove(iBinder);
        }
        surfaceSyncGroup.markSyncReady();
    }

    public final boolean mirrorDisplay(int i, SurfaceControl surfaceControl) {
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "mirrorDisplay()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e("WindowManager", "Invalid displayId " + i + " for mirrorDisplay");
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

    public final SurfaceControl mirrorWallpaperSurface(int i) {
        SurfaceControl mirrorSurface;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState topVisibleWallpaper = this.mRoot.getDisplayContent(i).mWallpaperController.getTopVisibleWallpaper();
                mirrorSurface = topVisibleWallpaper != null ? SurfaceControl.mirrorSurface(topVisibleWallpaper.mSurfaceControl) : null;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return mirrorSurface;
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
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

    public final void moveDisplayToTop(int i, String str) {
        if (!checkCallingPermission$1("android.permission.MANAGE_ACTIVITY_TASKS", "moveDisplayToTop", true)) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        Slog.d("WindowManager", "moveDisplayToTop caller pid = " + Binder.getCallingPid() + " displayId = " + i + " reason = " + str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.moveDisplayToTop(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void moveDisplayToTopIfAllowed(int i) {
        if (this.mExt.mExtraDisplayPolicy.shouldNotTopDisplay(i)) {
            BootReceiver$$ExternalSyntheticOutline0.m(i, "Not moving display ", " to top. Reason: ExtraDisplayPolicy", "WindowManager");
            return;
        }
        MultiTaskingController multiTaskingController = this.mAtmService.mMultiTaskingController;
        WindowManagerGlobalLock windowManagerGlobalLock = multiTaskingController.mAtm.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = multiTaskingController.mAtm.mRootWindowContainer.getDisplayContent(i);
                if (displayContent != null && displayContent.isAppCastingDisplay() && displayContent.topRunningActivity(false) == null) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                resetPriorityAfterLockedSection();
                moveDisplayToTopInternal(i);
                syncInputTransactions(true);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void moveDisplayToTopInternal(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && this.mRoot.getTopChild() != displayContent) {
                    if ((displayContent.mDisplayInfo.flags & 4096) != 0) {
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[2]) {
                            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -886583195545553099L, 5, null, Long.valueOf(i), Long.valueOf(this.mRoot.getTopFocusedDisplayContent().mDisplayId));
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

    public void moveFocusToActivity(ActivityRecord activityRecord) {
        moveDisplayToTopInternal(activityRecord.getDisplayId());
        handleTaskFocusChange(activityRecord.task, activityRecord);
    }

    public final boolean moveFocusToAdjacentEmbeddedWindow(WindowState windowState) {
        ActivityRecord activityRecord = windowState.mActivityRecord;
        TaskFragment taskFragment = activityRecord != null ? activityRecord.getTaskFragment() : null;
        if (taskFragment == null || !com.android.window.flags.Flags.embeddedActivityBackNavFlag() || !windowState.mActivityRecord.isEmbedded()) {
            return false;
        }
        TaskFragment taskFragment2 = taskFragment.mAdjacentTaskFragment;
        ActivityRecord activityRecord2 = taskFragment2 != null ? taskFragment2.topRunningActivity(false) : null;
        if (activityRecord2 == null) {
            return false;
        }
        WindowState window = activityRecord2.getWindow(WindowContainer.alwaysTruePredicate());
        long j = (window == null || window.mAttrs.type == 1) ? activityRecord2.createTime : window.mCreateTime;
        ActivityRecord activityRecord3 = windowState.mActivityRecord;
        activityRecord3.getClass();
        WindowState window2 = activityRecord3.getWindow(WindowContainer.alwaysTruePredicate());
        if (j < ((window2 == null || window2.mAttrs.type == 1) ? activityRecord3.createTime : window2.mCreateTime)) {
            return false;
        }
        moveFocusToActivity(activityRecord2);
        return !windowState.isFocused();
    }

    public final boolean moveFocusToAdjacentWindow(WindowState windowState, int i) {
        TaskFragment taskFragment;
        if (!windowState.isFocused()) {
            return false;
        }
        ActivityRecord activityRecord = windowState.mActivityRecord;
        TaskFragment taskFragment2 = activityRecord != null ? activityRecord.getTaskFragment() : null;
        if (taskFragment2 == null || (taskFragment = taskFragment2.mAdjacentTaskFragment) == null || taskFragment.asTask() != null) {
            return false;
        }
        if (taskFragment.mIsEmbedded && taskFragment.mIsolatedNav) {
            return false;
        }
        Rect bounds = taskFragment2.getBounds();
        Rect bounds2 = taskFragment.getBounds();
        if (i != 1 && i != 2) {
            if (i != 17) {
                if (i != 33) {
                    if (i != 66) {
                        if (i != 130 || bounds2.bottom <= bounds.bottom) {
                            return false;
                        }
                    } else if (bounds2.right <= bounds.right) {
                        return false;
                    }
                } else if (bounds2.top >= bounds.top) {
                    return false;
                }
            } else if (bounds2.left >= bounds.left) {
                return false;
            }
        }
        ActivityRecord activityRecord2 = taskFragment.topRunningActivity(true);
        if (activityRecord2 == null) {
            return false;
        }
        moveFocusToActivity(activityRecord2);
        return !windowState.isFocused();
    }

    public final void moveWindowTokenToDisplay(IBinder iBinder, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i);
                if (displayContentOrCreate == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 5128669121055635771L, 4, "moveWindowTokenToDisplay: Attempted to move token: %s to non-exiting displayId=%d", String.valueOf(iBinder), Long.valueOf(i));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowToken windowToken = this.mRoot.getWindowToken(iBinder);
                if (windowToken == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 6497954191906583839L, 0, "moveWindowTokenToDisplay: Attempted to move non-existing token: %s", String.valueOf(iBinder));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowToken.getDisplayContent() != displayContentOrCreate) {
                    displayContentOrCreate.reParentWindowToken(windowToken);
                    resetPriorityAfterLockedSection();
                } else {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 2865882097969084039L, 0, "moveWindowTokenToDisplay: Cannot move to the original display for token: %s", String.valueOf(iBinder));
                    }
                    resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyHardKeyboardStatusChange() {
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
            ((InputMethodManagerService$$ExternalSyntheticLambda5) onHardKeyboardStatusChangeListener).f$0.mHandler.obtainMessage(4000, z ? 1 : 0, 0).sendToTarget();
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void notifyKeyguardTrustedChanged() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mAtmService.mKeyguardController.isKeyguardShowing(0)) {
                        this.mRoot.ensureActivitiesVisible();
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

    public final List notifyScreenshotListeners(int i) {
        if (!checkCallingPermission$1("android.permission.STATUS_BAR_SERVICE", "notifyScreenshotListeners()", true)) {
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
                ArraySet arraySet = new ArraySet();
                displayContent.forAllActivities((Consumer) new WindowManagerService$$ExternalSyntheticLambda6(3, arraySet), true);
                List copyOf = List.copyOf(arraySet);
                resetPriorityAfterLockedSection();
                return copyOf;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean omniRequestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver, boolean z) {
        final Set of = Set.of();
        boolean z2 = InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE;
        if (z2 && z) {
            of = new ArraySet();
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mRoot.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda28
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i;
                            WindowManagerService windowManagerService = WindowManagerService.this;
                            Set set = of;
                            WindowState windowState = (WindowState) obj;
                            int i2 = WindowManagerService.MY_PID;
                            windowManagerService.getClass();
                            if ((windowState.mAttrs.privateFlags & 1048576) != 0 || windowState.hasRelativeLayer() || !windowState.isVisible() || windowState.mIsWallpaper || (i = windowState.mAttrs.type) < 2000 || i > 2999) {
                                return;
                            }
                            set.add(Integer.valueOf(i));
                        }
                    }, true);
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
        if (!z2) {
            of = Set.of();
        }
        ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot = takeAssistScreenshot(of);
        FgThread.getHandler().post(new WindowManagerService$$ExternalSyntheticLambda3(0, iAssistDataReceiver, takeAssistScreenshot != null ? takeAssistScreenshot.asBitmap() : null));
        return true;
    }

    public final void onAnimationFinished() {
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
    
        if (r2 != null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007b, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0079, code lost:
    
        if (r2 != null) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onInitReady() {
        /*
            r7 = this;
            android.os.Handler r0 = com.android.server.UiThread.getHandler()
            com.android.server.wm.WindowManagerService$5 r1 = new com.android.server.wm.WindowManagerService$5
            r1.<init>()
            r2 = 0
            r0.runWithScissors(r1, r2)
            com.android.server.Watchdog r0 = com.android.server.Watchdog.getInstance()
            r0.addMonitor(r7)
            com.android.server.wm.Watermark r0 = r7.mWatermark
            if (r0 == 0) goto L1b
            goto L85
        L1b:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/system/etc/setup.conf"
            r0.<init>(r1)
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L63 java.io.FileNotFoundException -> L65
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L63 java.io.FileNotFoundException -> L65
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L73 java.io.FileNotFoundException -> L7f
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L73 java.io.FileNotFoundException -> L7f
            java.lang.String r1 = r0.readLine()     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            if (r1 == 0) goto L5a
            java.lang.String r3 = "%"
            java.lang.String[] r1 = r1.split(r3)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            if (r1 == 0) goto L5a
            int r3 = r1.length     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            if (r3 <= 0) goto L5a
            com.android.server.wm.DisplayContent r3 = r7.getDefaultDisplayContentLocked()     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            com.android.server.wm.Watermark r4 = new com.android.server.wm.Watermark     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            android.util.DisplayMetrics r5 = r3.mRealDisplayMetrics     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            android.view.SurfaceControl$Transaction r6 = r7.mTransaction     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            r4.<init>(r3, r5, r1, r6)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            r7.mWatermark = r4     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            android.view.SurfaceControl$Transaction r1 = r7.mTransaction     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            r1.apply()     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L56 java.io.FileNotFoundException -> L58
            goto L5a
        L53:
            r7 = move-exception
            r1 = r0
            goto L67
        L56:
            r1 = r0
            goto L73
        L58:
            r1 = r0
            goto L7f
        L5a:
            r0.close()     // Catch: java.io.IOException -> L85
            goto L85
        L5e:
            r7 = move-exception
            goto L67
        L60:
            r7 = move-exception
            r2 = r1
            goto L67
        L63:
            r2 = r1
            goto L73
        L65:
            r2 = r1
            goto L7f
        L67:
            if (r1 != 0) goto L6f
            if (r2 == 0) goto L72
            r2.close()     // Catch: java.io.IOException -> L72
            goto L72
        L6f:
            r1.close()     // Catch: java.io.IOException -> L72
        L72:
            throw r7
        L73:
            if (r1 == 0) goto L79
        L75:
            r1.close()     // Catch: java.io.IOException -> L85
            goto L85
        L79:
            if (r2 == 0) goto L85
        L7b:
            r2.close()     // Catch: java.io.IOException -> L85
            goto L85
        L7f:
            if (r1 == 0) goto L82
            goto L75
        L82:
            if (r2 == 0) goto L85
            goto L7b
        L85:
            r7.showEmulatorDisplayOverlayIfNeeded()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.onInitReady():void");
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void onKeyguardShowingAndNotOccludedChanged() {
        this.mH.sendEmptyMessage(61);
        this.mH.post(new WindowManagerService$$ExternalSyntheticLambda19(0, this));
    }

    public final void onLockTaskStateChanged(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mExt.mPolicyExt.mLockTaskModeState = i;
                this.mRoot.forAllDisplayPolicies(new WindowManagerService$$ExternalSyntheticLambda7(4));
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void onOverlayChanged() {
        this.mH.post(new WindowManagerService$$ExternalSyntheticLambda19(1, this));
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x00b5, code lost:
    
        if (r14.isTransient(android.view.WindowInsets.Type.systemGestures()) == false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0160, code lost:
    
        if (((r6 == null || r6.mRemoved) ? false : r6.mWindowFrames.mFrame.contains(r14, r15)) != false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0140, code lost:
    
        if (r14 <= (r2.mTmpRect.right - r6)) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x007a, code lost:
    
        if (r13 != 0) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPointerDownOutsideFocusLocked(com.android.server.wm.InputTarget r12, int r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 548
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.onPointerDownOutsideFocusLocked(com.android.server.wm.InputTarget, int, int, int):void");
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void onPowerKeyDown(boolean z) {
        this.mRoot.forAllDisplayPolicies(new WindowManagerService$$ExternalSyntheticLambda4(0, z));
    }

    public final void onProcessActivityVisibilityChanged(int i, boolean z) {
        boolean z2;
        boolean z3 = false;
        ScreenRecordingCallbackController screenRecordingCallbackController = this.mScreenRecordingCallbackController;
        if (screenRecordingCallbackController.mRecordedWC != null && screenRecordingCallbackController.mLastInvokedStateByUid.containsKey(Integer.valueOf(i)) && z != ((Boolean) screenRecordingCallbackController.mLastInvokedStateByUid.get(Integer.valueOf(i))).booleanValue()) {
            WindowContainer windowContainer = screenRecordingCallbackController.mRecordedWC;
            if (windowContainer == null) {
                z2 = false;
            } else {
                boolean[] zArr = {false};
                windowContainer.forAllActivities((Predicate) new ScreenRecordingCallbackController$$ExternalSyntheticLambda0(i, zArr), true);
                z2 = zArr[0];
            }
            if ((!z || z2) && (z || !z2)) {
                ArraySet arraySet = new ArraySet();
                arraySet.add(Integer.valueOf(i));
                screenRecordingCallbackController.dispatchCallbacks(arraySet, z);
            }
        }
        KnoxRemoteScreenCallbackController knoxRemoteScreenCallbackController = this.mKnoxRemoteScreenCallbackController;
        if (knoxRemoteScreenCallbackController.mRecordedWC == null || !knoxRemoteScreenCallbackController.mLastInvokedStateByUid.containsKey(Integer.valueOf(i)) || z == ((Boolean) knoxRemoteScreenCallbackController.mLastInvokedStateByUid.get(Integer.valueOf(i))).booleanValue()) {
            return;
        }
        DisplayContent displayContent = knoxRemoteScreenCallbackController.mRecordedWC;
        if (displayContent != null) {
            boolean[] zArr2 = {false};
            displayContent.forAllActivities((Predicate) new KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda0(i, zArr2), true);
            z3 = zArr2[0];
        }
        if (!z || z3) {
            if (z || !z3) {
                ArraySet arraySet2 = new ArraySet();
                arraySet2.add(Integer.valueOf(i));
                knoxRemoteScreenCallbackController.dispatchCallbacks(arraySet2, z);
            }
        }
    }

    public final void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) {
        WindowState windowState;
        AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternal = AccessibilityController.getAccessibilityControllerInternal(this);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (accessibilityControllerInternal.isTracingEnabled(3072L)) {
                    accessibilityControllerInternal.logTrace("AccessibilityController.hasCallbacks", 3072L);
                }
                if (accessibilityControllerInternal.mCallbacksDispatcher != null && (windowState = (WindowState) this.mWindowMap.get(iBinder)) != null) {
                    accessibilityControllerInternal.onRectangleOnScreenRequested(windowState.getDisplayId(), rect);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new WindowManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void onSystemUiStarted() {
        ((PhoneWindowManager) this.mPolicy).bindKeyguard();
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException) && ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[5]) {
                String valueOf = String.valueOf(e);
                ProtoLogImpl_54989576.getSingleInstance().log(LogLevel.WTF, ProtoLogGroup.WM_ERROR, 3655576047584951173L, 0, "Window Manager Crash %s", new Object[]{valueOf});
            }
            throw e;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void onUserSwitched() {
        this.mSettingsObserver.updateSystemUiSettings(true);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllDisplayPolicies(new WindowManagerService$$ExternalSyntheticLambda7(5));
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void onWindowVisible(WindowState windowState) {
        showToastIfBlockingScreenCapture(windowState);
    }

    public final IWindowSession openSession(IWindowSessionCallback iWindowSessionCallback) {
        return new Session(this, iWindowSessionCallback, Binder.getCallingPid(), Binder.getCallingUid());
    }

    public final boolean outOfMemoryWindow(Session session, IWindow iWindow) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked == null) {
                        resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean reclaimSomeSurfaceMemory = this.mRoot.reclaimSomeSurfaceMemory(windowForClientLocked.mWinAnimator, "from-client", false);
                    resetPriorityAfterLockedSection();
                    return reclaimSomeSurfaceMemory;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void overridePendingAppTransitionMultiThumbFuture(IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, IRemoteCallback iRemoteCallback, boolean z, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.mAppTransition.overridePendingAppTransitionMultiThumbFuture(iAppTransitionAnimationSpecsFuture, iRemoteCallback, z);
                    resetPriorityAfterLockedSection();
                    return;
                }
                Slog.w("WindowManager", "Attempted to call overridePendingAppTransitionMultiThumbFuture for the display " + i + " that does not exist.");
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void overridePendingAppTransitionRemote(RemoteAnimationAdapter remoteAnimationAdapter, int i) {
        if (!checkCallingPermission$1("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "overridePendingAppTransitionRemote()", true)) {
            throw new SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    remoteAnimationAdapter.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
                    displayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter, false, false);
                    resetPriorityAfterLockedSection();
                } else {
                    Slog.w("WindowManager", "Attempted to call overridePendingAppTransitionRemote for the display " + i + " that does not exist.");
                    resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void performBootTimeout() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mDisplayEnabled) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 179762478329442868L, 0, "***** BOOT TIMEOUT: forcing display enabled", null);
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

    public final void performEnableScreen() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -3417569256875279779L, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, null, Boolean.valueOf(this.mDisplayEnabled), Boolean.valueOf(this.mForceDisplayEnabled), Boolean.valueOf(this.mShowingBootMessages), Boolean.valueOf(this.mSystemBooted), String.valueOf(new RuntimeException("here").fillInStackTrace()));
                }
                if (this.mDisplayEnabled) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mSystemBooted && !this.mShowingBootMessages) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mShowingBootMessages) {
                    PhoneWindowManager phoneWindowManager = (PhoneWindowManager) this.mPolicy;
                    if (!phoneWindowManager.mDefaultDisplayPolicy.mKeyguardDrawComplete && !phoneWindowManager.mBootAnimationDismissable) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
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
                    if (elapsedRealtime > 10 && ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -7516915153725082358L, 1, null, Long.valueOf(elapsedRealtime));
                    }
                }
                if (!this.mBootAnimationStopped) {
                    Trace.asyncTraceBegin(32L, "Stop bootanim", 0);
                    SystemProperties.set("service.bootanim.exit", "1");
                    this.mBootAnimationStopped = true;
                }
                if (!this.mForceDisplayEnabled && !checkBootAnimationCompleteLocked()) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -1541244520024033685L, 0, null, null);
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!SurfaceControl.bootFinished()) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 2670150656385758826L, 0, "performEnableScreen: bootFinished() failed.", null);
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                Slog.i("WindowManager", "!@Boot_EBS_F: wm_boot_animation_done");
                ((PhoneWindowManager) this.mPolicy).finishedBootAnimation();
                EventLog.writeEvent(31007, SystemClock.uptimeMillis());
                Trace.asyncTraceEnd(32L, "Stop bootanim", 0);
                this.mDisplayEnabled = true;
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SCREEN_ON_enabled[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_SCREEN_ON, 530628508916855904L, 0, null, null);
                }
                Slog.i("WindowManager", "!@Boot: Enabling Screen!");
                InputManagerCallback inputManagerCallback = this.mInputManagerCallback;
                boolean z = this.mEventDispatchingEnabled;
                if (inputManagerCallback.mInputDispatchEnabled != z) {
                    inputManagerCallback.mInputDispatchEnabled = z;
                    inputManagerCallback.mService.mInputManager.setInputDispatchMode(z, inputManagerCallback.mInputDispatchFrozen);
                }
                resetPriorityAfterLockedSection();
                try {
                    this.mActivityManager.bootAnimationComplete();
                } catch (RemoteException unused) {
                }
                PhoneWindowManager phoneWindowManager2 = (PhoneWindowManager) this.mPolicy;
                phoneWindowManager2.mDefaultDisplayPolicy.mLidState = phoneWindowManager2.mWindowManagerFuncs.getLidState();
                phoneWindowManager2.applyLidSwitchState();
                phoneWindowManager2.mWindowManagerFuncs.updateRotation(true, false);
                updateRotationUnchecked(false, false);
                WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        this.mAtmService.mWindowOrganizerController.mTransitionController.mIsWaitingForDisplayEnabled = false;
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 5477889324043875194L, 0, null, null);
                        }
                    } finally {
                    }
                }
                resetPriorityAfterLockedSection();
            } finally {
            }
        }
    }

    public final void pokeDrawLock(Session session, IBinder iBinder) {
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

    public final void prepareAppTransitionNone() {
        if (!checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "prepareAppTransition()", true)) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        getDefaultDisplayContentLocked().prepareAppTransition(0, 0);
    }

    public final void putKeyCustomizationInfo(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo) throws IllegalArgumentException {
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "putKeyCustomizationInfo", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        if (keyCustomizationInfo == null) {
            throw new IllegalArgumentException("KeyCustomizationInfo is empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.mKeyCustomizationPolicy.putKeyCustomizationInfo(keyCustomizationInfo);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void reboot(boolean z) {
        ShutdownThread.reboot(ActivityThread.currentActivityThread().getSystemUiContext(), "userrequested", z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void rebootSafeMode(boolean z) {
        ShutdownThread.rebootSafeMode(ActivityThread.currentActivityThread().getSystemUiContext());
    }

    public final void reenableKeyguard(IBinder iBinder, int i) {
        UserTokenWatcher userTokenWatcher;
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 2, "reenableKeyguard", (String) null);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        Objects.requireNonNull(iBinder, "token is null");
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            KeyguardDisableHandler keyguardDisableHandler = this.mKeyguardDisableHandler;
            keyguardDisableHandler.getClass();
            if (Process.isApplicationUid(callingUid)) {
                userTokenWatcher = keyguardDisableHandler.mAppTokenWatcher;
            } else {
                if (callingUid != 1000 || !(iBinder instanceof LockTaskController.LockTaskToken)) {
                    throw new UnsupportedOperationException("Only apps can use the KeyguardLock API");
                }
                userTokenWatcher = keyguardDisableHandler.mSystemTokenWatcher;
            }
            int profileParentId = ((KeyguardDisableHandler.AnonymousClass2) keyguardDisableHandler.mInjector).val$userManager.getProfileParentId(handleIncomingUser);
            synchronized (userTokenWatcher.mWatchers) {
                try {
                    TokenWatcher tokenWatcher = (TokenWatcher) userTokenWatcher.mWatchers.get(profileParentId);
                    if (tokenWatcher != null) {
                        tokenWatcher.release(iBinder);
                    }
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void refreshScreenCaptureDisabled() {
        if (Binder.getCallingUid() != MY_UID) {
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

    public final void registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) {
        Slog.d("WindowManager", "WindowManagerService.registerAuthTouchEventListener()");
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            NandswapManager$$ExternalSyntheticOutline0.m(callingUid, "Unable to verify uid for registerAuthTouchEventListener on uid ", "WindowManager");
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                AuthFactorTouchManager authFactorTouchManager = getDefaultDisplayContentLocked().mDisplayPolicy.mExt.mAuthFactorTouchManager;
                if (authFactorTouchManager != null) {
                    authFactorTouchManager.registerAuthTouchEventListener(iAuthTouchEventListener);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final boolean registerCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) {
        BlurController blurController = this.mBlurController;
        if (iCrossWindowBlurEnabledListener == null) {
            blurController.getClass();
            return false;
        }
        blurController.mBlurEnabledListeners.register(iCrossWindowBlurEnabledListener);
        return blurController.mBlurEnabled;
    }

    public final void registerDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) {
        if (!checkCallingPermission$1("android.permission.MONITOR_INPUT", "registerDecorViewGestureListener()", true)) {
            throw new SecurityException("Requires MONITOR_INPUT permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register DecorView gesture event listenerfor invalid display: " + i);
                }
                displayContent.mDecorViewGestureListener.register(iDecorViewGestureListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void registerDisplayFoldListener(final IDisplayFoldListener iDisplayFoldListener) {
        final DisplayFoldController displayFoldController = ((PhoneWindowManager) this.mPolicy).mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.mListeners.register(iDisplayFoldListener);
            if (displayFoldController.mFolded == null) {
                return;
            }
            displayFoldController.mHandler.post(new Runnable() { // from class: com.android.server.policy.DisplayFoldController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayFoldController displayFoldController2 = DisplayFoldController.this;
                    IDisplayFoldListener iDisplayFoldListener2 = iDisplayFoldListener;
                    displayFoldController2.getClass();
                    try {
                        iDisplayFoldListener2.onDisplayFoldChanged(displayFoldController2.mDisplayId, displayFoldController2.mFolded.booleanValue());
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }

    public final int[] registerDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) {
        ActivityTaskManagerService.enforceTaskPermission("registerDisplayWindowListener");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDisplayNotificationController.registerListener(iDisplayWindowListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean registerKnoxRemoteScreenCallback(android.window.IScreenRecordingCallback r9) {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            r8.registerScreenRecordingCallback_enforcePermission()
            com.android.server.wm.KnoxRemoteScreenCallbackController r8 = r8.mKnoxRemoteScreenCallbackController
            com.android.server.wm.WindowManagerService r2 = r8.mWms
            com.android.server.wm.WindowManagerGlobalLock r2 = r2.mGlobalLock
            boostPriorityForLockedSection()
            monitor-enter(r2)
            boolean r3 = r8.mRemoteScreenWatcherCallbackRegistered     // Catch: java.lang.Throwable -> L6e
            if (r3 == 0) goto L14
            goto L49
        L14:
            com.samsung.android.knox.remotecontrol.IRemoteInjection r3 = r8.mRemoteService     // Catch: android.os.RemoteException -> L3a java.lang.Throwable -> L6e
            if (r3 != 0) goto L25
            java.lang.String r3 = "remoteinjection"
            android.os.IBinder r3 = android.os.ServiceManager.getService(r3)     // Catch: android.os.RemoteException -> L3a java.lang.Throwable -> L6e
            com.samsung.android.knox.remotecontrol.IRemoteInjection r3 = com.samsung.android.knox.remotecontrol.IRemoteInjection.Stub.asInterface(r3)     // Catch: android.os.RemoteException -> L3a java.lang.Throwable -> L6e
            r8.mRemoteService = r3     // Catch: android.os.RemoteException -> L3a java.lang.Throwable -> L6e
        L25:
            com.samsung.android.knox.remotecontrol.IRemoteInjection r3 = r8.mRemoteService     // Catch: android.os.RemoteException -> L3a java.lang.Throwable -> L6e
            com.android.server.wm.KnoxRemoteScreenCallbackController$RemoteScreenWatcherCallback r4 = new com.android.server.wm.KnoxRemoteScreenCallbackController$RemoteScreenWatcherCallback     // Catch: android.os.RemoteException -> L35 java.lang.Throwable -> L6e
            r4.<init>()     // Catch: android.os.RemoteException -> L35 java.lang.Throwable -> L6e
            boolean r3 = r3.addRemoteScreenWatcherCallback(r4)     // Catch: android.os.RemoteException -> L35 java.lang.Throwable -> L6e
            r8.mRemoteScreenWatcherCallbackRegistered = r1     // Catch: android.os.RemoteException -> L33 java.lang.Throwable -> L6e
            goto L3f
        L33:
            r4 = move-exception
            goto L3c
        L35:
            r4 = move-exception
        L36:
            r3 = r0
            goto L3c
        L38:
            r4 = r3
            goto L36
        L3a:
            r3 = move-exception
            goto L38
        L3c:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L6e
        L3f:
            if (r3 == 0) goto L49
            com.android.server.wm.WindowManagerService r3 = r8.mWms     // Catch: java.lang.Throwable -> L6e
            com.android.server.wm.RootWindowContainer r3 = r3.mRoot     // Catch: java.lang.Throwable -> L6e
            com.android.server.wm.DisplayContent r3 = r3.mDefaultDisplay     // Catch: java.lang.Throwable -> L6e
            r8.mRecordedWC = r3     // Catch: java.lang.Throwable -> L6e
        L49:
            android.os.IBinder r3 = r9.asBinder()     // Catch: java.lang.Throwable -> L6e
            int r4 = android.os.Binder.getCallingUid()     // Catch: java.lang.Throwable -> L6e
            android.util.ArrayMap r5 = r8.mCallbacks     // Catch: java.lang.Throwable -> L6e
            boolean r5 = r5.containsKey(r3)     // Catch: java.lang.Throwable -> L6e
            if (r5 == 0) goto L70
            android.util.ArrayMap r8 = r8.mLastInvokedStateByUid     // Catch: java.lang.Throwable -> L6e
            java.lang.Integer r9 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L6e
            java.lang.Object r8 = r8.get(r9)     // Catch: java.lang.Throwable -> L6e
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: java.lang.Throwable -> L6e
            boolean r0 = r8.booleanValue()     // Catch: java.lang.Throwable -> L6e
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L6e
            resetPriorityAfterLockedSection()
            goto La6
        L6e:
            r8 = move-exception
            goto La7
        L70:
            com.android.server.wm.KnoxRemoteScreenCallbackController$Callback r5 = new com.android.server.wm.KnoxRemoteScreenCallbackController$Callback     // Catch: java.lang.Throwable -> L6e
            r5.<init>(r9, r4)     // Catch: java.lang.Throwable -> L6e
            r3.linkToDeath(r5, r0)     // Catch: java.lang.Throwable -> L6e android.os.RemoteException -> La2
            com.android.server.wm.DisplayContent r9 = r8.mRecordedWC     // Catch: java.lang.Throwable -> L6e
            if (r9 != 0) goto L7d
            goto L8b
        L7d:
            boolean[] r6 = new boolean[r1]     // Catch: java.lang.Throwable -> L6e
            r6[r0] = r0     // Catch: java.lang.Throwable -> L6e
            com.android.server.wm.KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda0 r7 = new com.android.server.wm.KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L6e
            r7.<init>(r4, r6)     // Catch: java.lang.Throwable -> L6e
            r9.forAllActivities(r7, r1)     // Catch: java.lang.Throwable -> L6e
            boolean r0 = r6[r0]     // Catch: java.lang.Throwable -> L6e
        L8b:
            android.util.ArrayMap r9 = r8.mLastInvokedStateByUid     // Catch: java.lang.Throwable -> L6e
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L6e
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)     // Catch: java.lang.Throwable -> L6e
            r9.put(r1, r4)     // Catch: java.lang.Throwable -> L6e
            android.util.ArrayMap r8 = r8.mCallbacks     // Catch: java.lang.Throwable -> L6e
            r8.put(r3, r5)     // Catch: java.lang.Throwable -> L6e
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L6e
            resetPriorityAfterLockedSection()
            goto La6
        La2:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L6e
            resetPriorityAfterLockedSection()
        La6:
            return r0
        La7:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L6e
            resetPriorityAfterLockedSection()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.registerKnoxRemoteScreenCallback(android.window.IScreenRecordingCallback):boolean");
    }

    public final void registerOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                if (defaultDisplayContentLocked.getOneHandOpPolicy() != null) {
                    OneHandOpPolicy oneHandOpPolicy = defaultDisplayContentLocked.getOneHandOpPolicy();
                    if (iOneHandOpWatcher != null) {
                        OneHandOpPolicy.OneHandOpMonitor oneHandOpMonitor = oneHandOpPolicy.mOneHandOpMonitor;
                        oneHandOpMonitor.getClass();
                        try {
                            if (oneHandOpMonitor.mWatcher == null) {
                                iOneHandOpWatcher.asBinder().linkToDeath(oneHandOpMonitor, 0);
                                oneHandOpMonitor.mWatcher = iOneHandOpWatcher;
                            }
                        } catch (RemoteException unused) {
                        }
                    } else {
                        oneHandOpPolicy.getClass();
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void registerPinnedTaskListener(int i, IPinnedTaskListener iPinnedTaskListener) {
        if (checkCallingPermission$1("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "registerPinnedTaskListener()", true) && this.mAtmService.mSupportsPictureInPicture) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    PinnedTaskController pinnedTaskController = this.mRoot.getDisplayContent(i).mPinnedTaskController;
                    pinnedTaskController.getClass();
                    try {
                        iPinnedTaskListener.asBinder().linkToDeath(pinnedTaskController.mPinnedTaskListenerDeathHandler, 0);
                        pinnedTaskController.mPinnedTaskListener = iPinnedTaskListener;
                        try {
                            iPinnedTaskListener.onImeVisibilityChanged(pinnedTaskController.mIsImeShowing, pinnedTaskController.mImeHeight);
                        } catch (RemoteException e) {
                            Slog.e("WindowManager", "Error delivering bounds changed event.", e);
                        }
                        pinnedTaskController.notifyMovementBoundsChanged(false);
                    } catch (RemoteException e2) {
                        Log.e("WindowManager", "Failed to register pinned task listener", e2);
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public final void registerPointerEventListener(WindowManagerPolicyConstants.PointerEventListener pointerEventListener, int i) {
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

    public final int registerProposedRotationListener(IBinder iBinder, IRotationWatcher iRotationWatcher) {
        int proposedRotation;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RotationWatcherController rotationWatcherController = this.mRotationWatcherController;
                rotationWatcherController.getClass();
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    WindowContextListenerController.WindowContextListenerImpl windowContextListenerImpl = (WindowContextListenerController.WindowContextListenerImpl) rotationWatcherController.mService.mWindowContextListenerController.mListeners.get(iBinder);
                    forTokenLocked = windowContextListenerImpl != null ? windowContextListenerImpl.mContainer : null;
                }
                if (forTokenLocked == null) {
                    Slog.w("WindowManager", "Register rotation listener from non-existing token, uid=" + Binder.getCallingUid());
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                this.mRotationWatcherController.registerProposedRotationListener(iBinder, iRotationWatcher);
                DisplayRotation.OrientationListener orientationListener = forTokenLocked.mDisplayContent.mDisplayRotation.mOrientationListener;
                if (orientationListener != null && (proposedRotation = orientationListener.getProposedRotation()) >= 0) {
                    resetPriorityAfterLockedSection();
                    return proposedRotation;
                }
                int rotation = forTokenLocked.getWindowConfiguration().getRotation();
                resetPriorityAfterLockedSection();
                return rotation;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean registerScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) {
        boolean z = false;
        registerScreenRecordingCallback_enforcePermission();
        ScreenRecordingCallbackController screenRecordingCallbackController = this.mScreenRecordingCallbackController;
        WindowManagerGlobalLock windowManagerGlobalLock = screenRecordingCallbackController.mWms.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                screenRecordingCallbackController.ensureMediaProjectionWatcherCallbackRegistered();
                IBinder asBinder = iScreenRecordingCallback.asBinder();
                int callingUid = Binder.getCallingUid();
                if (screenRecordingCallbackController.mCallbacks.containsKey(asBinder)) {
                    z = ((Boolean) screenRecordingCallbackController.mLastInvokedStateByUid.get(Integer.valueOf(callingUid))).booleanValue();
                    resetPriorityAfterLockedSection();
                } else {
                    ScreenRecordingCallbackController.Callback callback = screenRecordingCallbackController.new Callback(iScreenRecordingCallback, callingUid);
                    try {
                        asBinder.linkToDeath(callback, 0);
                        WindowContainer windowContainer = screenRecordingCallbackController.mRecordedWC;
                        if (windowContainer != null) {
                            boolean[] zArr = {false};
                            windowContainer.forAllActivities((Predicate) new ScreenRecordingCallbackController$$ExternalSyntheticLambda0(callingUid, zArr), true);
                            z = zArr[0];
                        }
                        screenRecordingCallbackController.mLastInvokedStateByUid.put(Integer.valueOf(callingUid), Boolean.valueOf(z));
                        screenRecordingCallbackController.mCallbacks.put(asBinder, callback);
                        resetPriorityAfterLockedSection();
                    } catch (RemoteException unused) {
                        resetPriorityAfterLockedSection();
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        return z;
    }

    public final void registerShortcutKey(long j, IShortcutService iShortcutService) throws RemoteException {
        if (!checkCallingPermission$1("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "registerShortcutKey", true)) {
            throw new SecurityException("Requires REGISTER_WINDOW_MANAGER_LISTENERS permission");
        }
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) this.mPolicy;
        synchronized (phoneWindowManager.mLock) {
            ModifierShortcutManager modifierShortcutManager = phoneWindowManager.mModifierShortcutManager;
            IShortcutService iShortcutService2 = (IShortcutService) modifierShortcutManager.mShortcutKeyServices.get(j);
            if (iShortcutService2 != null && iShortcutService2.asBinder().pingBinder()) {
                throw new RemoteException("Key already exists.");
            }
            modifierShortcutManager.mShortcutKeyServices.put(j, iShortcutService);
        }
    }

    public final void registerSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register system gesture exclusion event for invalid display: " + i);
                }
                displayContent.mSystemGestureExclusionListeners.register(iSystemGestureExclusionListener);
                if (!(displayContent.mSystemGestureExclusionListeners.getRegisteredCallbackCount() == 1 ? displayContent.updateSystemGestureExclusion() : false)) {
                    try {
                        iSystemGestureExclusionListener.onSystemGestureExclusionChanged(displayContent.mDisplayId, displayContent.mSystemGestureExclusion, displayContent.mSystemGestureExclusionWasRestricted ? displayContent.mSystemGestureExclusionUnrestricted : null);
                    } catch (RemoteException e) {
                        Slog.e("WindowManager", "Failed to notify SystemGestureExclusionListener during register", e);
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void registerSystemKeyEvent(int i, ComponentName componentName, int i2) throws IllegalArgumentException {
        if (!checkCallingPermission$1("com.samsung.android.permission.ACCESS_SYSTEM_KEY_DISPATCHING", "registerSystemKeyEvent", true)) {
            throw new SecurityException("Requires ACCESS_SYSTEM_KEY_DISPATCHING permission");
        }
        SystemKeyManager systemKeyManager = this.mExt.mPolicyExt.mSystemKeyPolicy;
        systemKeyManager.getClass();
        Log.v("SystemKeyManager", "registerSystemKeyEvent() is called keyCode=" + i + " componentName=" + componentName + " press=" + SystemKeyManager.keyPressToString(i2));
        SystemKeyManager.checkValidRequestedDefaultInfo(i, i2, componentName);
        if ((i2 & 15) == 0 || i2 == -1) {
            throw new IllegalArgumentException("requested press was wrong. The press(" + SystemKeyManager.keyPressToString(i2) + ") type does not supported.");
        }
        if ((i == 6 || i == 224 || i == 1064) && (i2 & 3) == 0) {
            throw new IllegalArgumentException("requested press was wrong. The press(" + SystemKeyManager.keyPressToString(i2) + ") type does not supported.");
        }
        String flattenToString = componentName.flattenToString();
        synchronized (systemKeyManager) {
            try {
                HashMap hashMap = (HashMap) systemKeyManager.mSystemKeyInfoMap.get(i);
                if (hashMap == null) {
                    systemKeyManager.mSystemKeyInfoMap.put(i, new HashMap());
                    hashMap = (HashMap) systemKeyManager.mSystemKeyInfoMap.get(i);
                }
                hashMap.put(flattenToString, new SystemKeyManager.SystemKeyInfo(i, i2, componentName));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerTaskFpsCallback(int i, ITaskFpsCallback iTaskFpsCallback) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FPS_COUNTER") != 0) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(Binder.getCallingPid(), "Access denied to process: ", ", must have permission android.permission.ACCESS_FPS_COUNTER"));
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

    public final void registerTrustedPresentationListener(final IBinder iBinder, final ITrustedPresentationListener iTrustedPresentationListener, final TrustedPresentationThresholds trustedPresentationThresholds, final int i) {
        final TrustedPresentationListenerController trustedPresentationListenerController = this.mTrustedPresentationListenerController;
        trustedPresentationListenerController.startHandlerThreadIfNeeded();
        trustedPresentationListenerController.mHandler.post(new Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TrustedPresentationListenerController trustedPresentationListenerController2 = TrustedPresentationListenerController.this;
                ITrustedPresentationListener iTrustedPresentationListener2 = iTrustedPresentationListener;
                int i2 = i;
                IBinder iBinder2 = iBinder;
                TrustedPresentationThresholds trustedPresentationThresholds2 = trustedPresentationThresholds;
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TPL_enabled[0]) {
                    trustedPresentationListenerController2.getClass();
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TPL, -6140852484700685564L, 4, null, String.valueOf(iTrustedPresentationListener2), Long.valueOf(i2), String.valueOf(iBinder2), String.valueOf(trustedPresentationThresholds2));
                }
                final TrustedPresentationListenerController.Listeners listeners = trustedPresentationListenerController2.mRegisteredListeners;
                ((ArrayList) listeners.mWindowToListeners.computeIfAbsent(iBinder2, new TrustedPresentationListenerController$Listeners$$ExternalSyntheticLambda0())).add(new TrustedPresentationListenerController.TrustedPresentationInfo(trustedPresentationThresholds2, i2, iTrustedPresentationListener2));
                ((TrustedPresentationListenerController.Listeners.ListenerDeathRecipient) listeners.mUniqueListeners.computeIfAbsent(iTrustedPresentationListener2.asBinder(), new Function() { // from class: com.android.server.wm.TrustedPresentationListenerController$Listeners$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        TrustedPresentationListenerController.Listeners listeners2 = TrustedPresentationListenerController.Listeners.this;
                        listeners2.getClass();
                        return listeners2.new ListenerDeathRecipient((IBinder) obj);
                    }
                })).mInstances++;
                if (trustedPresentationListenerController2.mWindowInfosListener == null) {
                    TrustedPresentationListenerController.AnonymousClass1 anonymousClass1 = trustedPresentationListenerController2.new AnonymousClass1();
                    trustedPresentationListenerController2.mWindowInfosListener = anonymousClass1;
                    trustedPresentationListenerController2.mLastWindowHandles = (InputWindowHandle[]) anonymousClass1.register().first;
                }
                trustedPresentationListenerController2.computeTpl(trustedPresentationListenerController2.mLastWindowHandles);
            }
        });
    }

    public final boolean registerWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        boolean isWallpaperVisible;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register visibility event for invalid display: " + i);
                }
                WallpaperVisibilityListeners wallpaperVisibilityListeners = this.mWallpaperVisibilityListeners;
                RemoteCallbackList remoteCallbackList = (RemoteCallbackList) wallpaperVisibilityListeners.mDisplayListeners.get(i);
                if (remoteCallbackList == null) {
                    remoteCallbackList = new RemoteCallbackList();
                    wallpaperVisibilityListeners.mDisplayListeners.append(i, remoteCallbackList);
                }
                remoteCallbackList.register(iWallpaperVisibilityListener);
                isWallpaperVisible = displayContent.mWallpaperController.isWallpaperVisible();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return isWallpaperVisible;
    }

    public final int relayoutWindow(Session session, IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, WindowRelayoutResult windowRelayoutResult) {
        ClientWindowFrames clientWindowFrames;
        MergedConfiguration mergedConfiguration;
        SurfaceControl surfaceControl;
        InsetsState insetsState;
        InsetsSourceControl.Array array;
        if (windowRelayoutResult != null) {
            ClientWindowFrames clientWindowFrames2 = windowRelayoutResult.frames;
            MergedConfiguration mergedConfiguration2 = windowRelayoutResult.mergedConfiguration;
            clientWindowFrames = clientWindowFrames2;
            mergedConfiguration = mergedConfiguration2;
            surfaceControl = windowRelayoutResult.surfaceControl;
            insetsState = windowRelayoutResult.insetsState;
            array = windowRelayoutResult.activeControls;
        } else {
            clientWindowFrames = null;
            mergedConfiguration = null;
            surfaceControl = null;
            insetsState = null;
            array = null;
        }
        return relayoutWindowInner(session, iWindow, layoutParams, i, i2, i3, i4, i5, i6, clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array, null, windowRelayoutResult);
    }

    @Deprecated
    public final int relayoutWindow(Session session, IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, ClientWindowFrames clientWindowFrames, MergedConfiguration mergedConfiguration, SurfaceControl surfaceControl, InsetsState insetsState, InsetsSourceControl.Array array, Bundle bundle) {
        return relayoutWindowInner(session, iWindow, layoutParams, i, i2, i3, i4, i5, i6, clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array, bundle, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:246:0x0585, code lost:
    
        if (r8.mLastReportedActivityWindowInfo != null) goto L320;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0182, code lost:
    
        if ((r3 & com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0) goto L82;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int relayoutWindowInner(com.android.server.wm.Session r39, android.view.IWindow r40, android.view.WindowManager.LayoutParams r41, int r42, int r43, int r44, int r45, int r46, int r47, android.window.ClientWindowFrames r48, android.util.MergedConfiguration r49, android.view.SurfaceControl r50, android.view.InsetsState r51, android.view.InsetsSourceControl.Array r52, android.os.Bundle r53, android.view.WindowRelayoutResult r54) {
        /*
            Method dump skipped, instructions count: 1838
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.relayoutWindowInner(com.android.server.wm.Session, android.view.IWindow, android.view.WindowManager$LayoutParams, int, int, int, int, int, int, android.window.ClientWindowFrames, android.util.MergedConfiguration, android.view.SurfaceControl, android.view.InsetsState, android.view.InsetsSourceControl$Array, android.os.Bundle, android.view.WindowRelayoutResult):int");
    }

    public final void removeKeyCustomizationInfo(int i, int i2, int i3) {
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "removeKeyCustomizationInfo", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExt.mPolicyExt.mKeyCustomizationPolicy.removeKeyCustomizationInfo(i, i2, i3, null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeKeyCustomizationInfoByPackage(String str, int i, int i2) {
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "removeKeyCustomizationInfo", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            KeyCustomizationManager keyCustomizationManager = this.mExt.mPolicyExt.mKeyCustomizationPolicy;
            keyCustomizationManager.getClass();
            String str2 = KeyCustomizationConstants.VOLD_DECRYPT;
            keyCustomizationManager.removeKeyCustomizationInfo(2003, i, i2, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeKeyguardLockedStateListener(IKeyguardLockedStateListener iKeyguardLockedStateListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE", "android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE permission required to subscribe to keyguard locked state changes");
        this.mKeyguardLockedStateListeners.unregister(iKeyguardLockedStateListener);
    }

    public final void removeObsoleteTaskFiles(ArraySet arraySet, int[] iArr) {
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

    public final void removeRotationWatcher(IRotationWatcher iRotationWatcher) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RotationWatcherController rotationWatcherController = this.mRotationWatcherController;
                if (RotationWatcherController.unregister(iRotationWatcher, rotationWatcherController.mProposedRotationListeners)) {
                    rotationWatcherController.mHasProposedRotationListeners = !rotationWatcherController.mProposedRotationListeners.isEmpty();
                } else {
                    RotationWatcherController.unregister(iRotationWatcher, rotationWatcherController.mDisplayRotationWatchers);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void removeWindowChangeListener(WindowChangeListener windowChangeListener) {
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

    public final void removeWindowToken(IBinder iBinder, int i) {
        if (!checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "removeWindowToken()", true)) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removeWindowToken(iBinder, false, true, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeWindowToken(IBinder iBinder, boolean z, boolean z2, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -1045756671264607145L, 4, "removeWindowToken: Attempted to remove token: %s for non-exiting displayId=%d", String.valueOf(iBinder), Long.valueOf(i));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowToken removeWindowToken = displayContent.removeWindowToken(iBinder, z2);
                if (removeWindowToken == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 874825105313641295L, 0, "removeWindowToken: Attempted to remove non-existing token: %s", String.valueOf(iBinder));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (displayContent.isDefaultDisplay && displayContent.mInputMethodWindow != null && removeWindowToken.getWindow(new WindowManagerService$$ExternalSyntheticLambda0(1, displayContent)) != null && this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
                    this.mAtmService.mDexController.hideDexImeOnDefaultDisplayLocked();
                }
                if (z) {
                    removeWindowToken.removeAllWindowsIfPossible();
                }
                displayContent.mInputMonitor.updateInputWindowsLw(true);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean replaceContentOnDisplay(int i, SurfaceControl surfaceControl) {
        if (!checkCallingPermission$1("android.permission.ACCESS_SURFACE_FLINGER", "replaceDisplayContent()", true)) {
            throw new SecurityException("Requires ACCESS_SURFACE_FLINGER permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i);
                    if (displayContentOrCreate == null) {
                        resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    new SurfaceControl.Transaction().reparent(surfaceControl, displayContentOrCreate.getSurfaceControl()).reparent(displayContentOrCreate.mOverlayLayer, null).reparent(displayContentOrCreate.mInputOverlayLayer, null).reparent(displayContentOrCreate.mA11yOverlayLayer, null).apply();
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
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

    public final void reportDecorViewGestureChanged(Session session, IWindow iWindow, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                IBinder iBinder = windowForClientLocked.mToken.token;
                for (int beginBroadcast = displayContent.mDecorViewGestureListener.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    try {
                        displayContent.mDecorViewGestureListener.getBroadcastItem(beginBroadcast).onInterceptionChanged(iBinder, z);
                    } catch (RemoteException e) {
                        Slog.e("WindowManager", "Failed to notify DecorViewGestureListener", e);
                    }
                }
                displayContent.mDecorViewGestureListener.finishBroadcast();
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void reportKeepClearAreasChanged(Session session, IWindow iWindow, List list, List list2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked != null) {
                    if (windowForClientLocked.setKeepClearAreas(list, list2)) {
                        windowForClientLocked.getDisplayContent().updateKeepClearAreas();
                    }
                    resetPriorityAfterLockedSection();
                } else {
                    Slog.i("WindowManager", "reportKeepClearAreasChanged(): No window state for package:" + session.mPackageName);
                    resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void reportSystemGestureExclusionChanged(Session session, IWindow iWindow, List list) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    Slog.i("WindowManager", "reportSystemGestureExclusionChanged(): No window state for package:" + session.mPackageName);
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!((ArrayList) windowForClientLocked.mExclusionRects).equals(list)) {
                    ((ArrayList) windowForClientLocked.mExclusionRects).clear();
                    ((ArrayList) windowForClientLocked.mExclusionRects).addAll(list);
                    windowForClientLocked.getDisplayContent().updateSystemGestureExclusion();
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        IWindow iWindow;
        this.mContext.enforceCallingOrSelfPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "requestAppKeyboardShortcuts");
        WindowState focusedWindow = getFocusedWindow();
        if (focusedWindow == null || (iWindow = focusedWindow.mClient) == null) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
            return;
        }
        try {
            iWindow.requestAppKeyboardShortcuts(iResultReceiver, i);
        } catch (RemoteException unused) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
        }
    }

    public final boolean requestAssistScreenshot(IAssistDataReceiver iAssistDataReceiver) {
        return omniRequestAssistScreenshot(iAssistDataReceiver, false);
    }

    public final void requestImeKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        IWindow iWindow;
        this.mContext.enforceCallingOrSelfPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "requestImeKeyboardShortcuts");
        WindowState currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
        if (currentInputMethodWindow == null || (iWindow = currentInputMethodWindow.mClient) == null) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
            return;
        }
        try {
            iWindow.requestAppKeyboardShortcuts(iResultReceiver, i);
        } catch (RemoteException unused) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
        }
    }

    public final void requestMetaKeyEvent(ComponentName componentName, boolean z) {
        if (componentName == null) {
            return;
        }
        SystemKeyManager systemKeyManager = this.mExt.mPolicyExt.mSystemKeyPolicy;
        systemKeyManager.getClass();
        Log.v("SystemKeyManager", "requestMetaKeyEvent() : componentName=" + componentName + " request=" + z);
        String flattenToString = componentName.flattenToString();
        synchronized (systemKeyManager) {
            try {
                if (z) {
                    systemKeyManager.mMetaKeyRequestedComponents.add(flattenToString);
                } else {
                    systemKeyManager.mMetaKeyRequestedComponents.remove(flattenToString);
                }
                String str = systemKeyManager.mFocusedWindow;
                if (str != null && str.contains(flattenToString)) {
                    systemKeyManager.updateFocusedWindow(flattenToString);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestScrollCapture(int i, IBinder iBinder, int i2, IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        ScrollCaptureResponse.Builder builder;
        WindowManagerGlobalLock windowManagerGlobalLock;
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "requestScrollCapture()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                builder = new ScrollCaptureResponse.Builder();
                windowManagerGlobalLock = this.mGlobalLock;
                boostPriorityForLockedSection();
            } catch (RemoteException e) {
                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -8972916676375201577L, 0, "requestScrollCapture: caught exception dispatching callback: %s", String.valueOf(e));
                }
            }
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[4]) {
                            ProtoLogImpl_54989576.e(ProtoLogGroup.WM_ERROR, -6186782212018913664L, 1, "Invalid displayId for requestScrollCapture: %d", Long.valueOf(i));
                        }
                        builder.setDescription(String.format("bad displayId: %d", Integer.valueOf(i)));
                        iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                    } else {
                        WindowState window = displayContent.getWindow(new Predicate(i2) { // from class: com.android.server.wm.DisplayContent.3
                            public boolean behindTopWindow;
                            public final /* synthetic */ int val$taskId;

                            public AnonymousClass3(int i22) {
                                this.val$taskId = i22;
                                this.behindTopWindow = WindowState.this == null;
                            }

                            /* JADX WARN: Code restructure failed: missing block: B:12:0x0019, code lost:
                            
                                if (r5.canReceiveKeys(false) == false) goto L29;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:14:0x002c, code lost:
                            
                                if (r5.isSecureLocked() == false) goto L43;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:15:?, code lost:
                            
                                return true;
                             */
                            /* JADX WARN: Code restructure failed: missing block: B:19:0x0026, code lost:
                            
                                if (r0.mTaskId == r4.val$taskId) goto L39;
                             */
                            @Override // java.util.function.Predicate
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final boolean test(java.lang.Object r5) {
                                /*
                                    r4 = this;
                                    com.android.server.wm.WindowState r5 = (com.android.server.wm.WindowState) r5
                                    boolean r0 = r4.behindTopWindow
                                    r1 = 1
                                    r2 = 0
                                    if (r0 != 0) goto L10
                                    com.android.server.wm.WindowState r0 = com.android.server.wm.WindowState.this
                                    if (r5 != r0) goto Le
                                    r4.behindTopWindow = r1
                                Le:
                                    r1 = r2
                                    goto L2f
                                L10:
                                    int r0 = r4.val$taskId
                                    r3 = -1
                                    if (r0 != r3) goto L1c
                                    boolean r4 = r5.canReceiveKeys(r2)
                                    if (r4 != 0) goto L28
                                    goto Le
                                L1c:
                                    com.android.server.wm.Task r0 = r5.getTask()
                                    if (r0 == 0) goto Le
                                    int r4 = r4.val$taskId
                                    int r0 = r0.mTaskId
                                    if (r0 != r4) goto Le
                                L28:
                                    boolean r4 = r5.isSecureLocked()
                                    if (r4 == 0) goto L2f
                                    goto Le
                                L2f:
                                    return r1
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayContent.AnonymousClass3.test(java.lang.Object):boolean");
                            }
                        });
                        if (window != null) {
                            try {
                                window.mClient.requestScrollCapture(iScrollCaptureResponseListener);
                            } catch (RemoteException e2) {
                                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 51378282333944649L, 0, "requestScrollCapture: caught exception dispatching to window.token=%s", String.valueOf(window.mClient.asBinder()));
                                }
                                builder.setWindowTitle(window.getName());
                                builder.setPackageName(window.mAttrs.packageName);
                                builder.setDescription(String.format("caught exception: %s", e2));
                                iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                            }
                            resetPriorityAfterLockedSection();
                            return;
                        }
                        builder.setDescription("findScrollCaptureTargetWindow returned null");
                        iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean requestSystemKeyEvent(int r6, android.content.ComponentName r7, boolean r8) {
        /*
            r5 = this;
            r0 = 0
            if (r7 != 0) goto L4
            return r0
        L4:
            com.android.server.wm.WindowManagerServiceExt r5 = r5.mExt
            com.android.server.policy.PhoneWindowManagerExt r5 = r5.mPolicyExt
            com.android.server.policy.SystemKeyManager r5 = r5.mSystemKeyPolicy
            r5.getClass()
            java.lang.String r1 = "SystemKeyManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "requestSystemKeyEvent() is called keyCode="
            r2.<init>(r3)
            r2.append(r6)
            java.lang.String r3 = " componentName="
            r2.append(r3)
            r2.append(r7)
            java.lang.String r3 = " request="
            r2.append(r3)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            android.util.Log.v(r1, r2)
            r1 = -1
            boolean r1 = com.android.server.policy.SystemKeyManager.checkValidRequestedDefaultInfo(r6, r1, r7)
            if (r1 != 0) goto L39
            goto L88
        L39:
            java.lang.String r1 = r7.flattenToString()
            monitor-enter(r5)
            r2 = 3
            r3 = 1
            if (r6 == r2) goto L48
            r2 = 187(0xbb, float:2.62E-43)
            if (r6 == r2) goto L48
        L46:
            r2 = r0
            goto L4f
        L48:
            boolean r2 = r5.isSystemKeyEventRequested(r6, r7)     // Catch: java.lang.Throwable -> L6f
            if (r8 == r2) goto L46
            r2 = r3
        L4f:
            android.util.SparseArray r4 = r5.mSystemKeyInfoMap     // Catch: java.lang.Throwable -> L6f
            java.lang.Object r4 = r4.get(r6)     // Catch: java.lang.Throwable -> L6f
            java.util.HashMap r4 = (java.util.HashMap) r4     // Catch: java.lang.Throwable -> L6f
            if (r4 != 0) goto L73
            if (r8 == 0) goto L71
            android.util.SparseArray r0 = r5.mSystemKeyInfoMap     // Catch: java.lang.Throwable -> L6f
            java.util.HashMap r4 = new java.util.HashMap     // Catch: java.lang.Throwable -> L6f
            r4.<init>()     // Catch: java.lang.Throwable -> L6f
            r0.put(r6, r4)     // Catch: java.lang.Throwable -> L6f
            android.util.SparseArray r0 = r5.mSystemKeyInfoMap     // Catch: java.lang.Throwable -> L6f
            java.lang.Object r0 = r0.get(r6)     // Catch: java.lang.Throwable -> L6f
            r4 = r0
            java.util.HashMap r4 = (java.util.HashMap) r4     // Catch: java.lang.Throwable -> L6f
            goto L73
        L6f:
            r6 = move-exception
            goto L89
        L71:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6f
            goto L88
        L73:
            if (r8 == 0) goto L7e
            com.android.server.policy.SystemKeyManager$SystemKeyInfo r8 = new com.android.server.policy.SystemKeyManager$SystemKeyInfo     // Catch: java.lang.Throwable -> L6f
            r8.<init>(r6, r7)     // Catch: java.lang.Throwable -> L6f
            r4.put(r1, r8)     // Catch: java.lang.Throwable -> L6f
            goto L81
        L7e:
            r4.remove(r1)     // Catch: java.lang.Throwable -> L6f
        L81:
            if (r2 == 0) goto L86
            r5.notifyRequestedSystemKey()     // Catch: java.lang.Throwable -> L6f
        L86:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6f
            r0 = r3
        L88:
            return r0
        L89:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6f
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.requestSystemKeyEvent(int, android.content.ComponentName, boolean):boolean");
    }

    public final void requestTraversal() {
        this.mWindowPlacerLocked.requestTraversal();
    }

    public final void restoreKeyCustomizationInfo(List list) {
        boolean z = true;
        if (!checkCallingPermission$1("com.samsung.android.permisson.ACCESS_KEY_CUSTOMIZE_INFO", "restoreKeyCustomizationInfo", true)) {
            throw new SecurityException("Requires ACCESS_KEY_CUSTOMIZATION_INFO permission");
        }
        if (list == null || list.size() == 0) {
            Slog.d("WindowManager", "keyInfoArray is null or size is zero.");
            return;
        }
        KeyCustomizationManager keyCustomizationManager = this.mExt.mPolicyExt.mKeyCustomizationPolicy;
        keyCustomizationManager.getClass();
        Slog.d("KeyCustomizationManager", "restoreKeyCustomizationInfo, size=" + list.size());
        KeyCustomizationInfoManager keyCustomizationInfoManager = keyCustomizationManager.mKeyCustomizationInfoManager;
        synchronized (keyCustomizationInfoManager.mLock) {
            try {
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = (SemWindowManager.KeyCustomizationInfo) it.next();
                    if (keyCustomizationInfo != null) {
                        int i = keyCustomizationInfo.keyCode;
                        if (i != 1090 && i != 1091 && i != 1092) {
                            Slog.d("KeyCustomizationInfoManager", "restoreKeyCustomizationInfo keyCode=" + i + KeyCustomizationManager.pressToString(keyCustomizationInfo.press) + " " + KeyCustomizationManager.actionToString(keyCustomizationInfo.action) + keyCustomizationInfo.intent);
                            keyCustomizationInfoManager.put(keyCustomizationInfo, true);
                            z2 = true;
                        }
                        Slog.d("KeyCustomizationInfoManager", "restoreKeyCustomizationInfo keyCode=" + i);
                        arrayList.add(keyCustomizationInfo);
                        z2 = true;
                    }
                }
                if (arrayList.size() > 0) {
                    keyCustomizationInfoManager.mHotKeyMap.clear();
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 = (SemWindowManager.KeyCustomizationInfo) it2.next();
                        if (keyCustomizationInfo2 != null) {
                            int i2 = keyCustomizationInfo2.keyCode;
                            Intent intent = keyCustomizationInfo2.intent;
                            if (intent == null) {
                                Log.d("KeyCustomizationInfoManager", "restoreKeyCustomizationInfo, keyCode=" + i2 + " intent is null.");
                            } else {
                                ComponentName component = intent.getComponent();
                                if (component == null) {
                                    Log.d("KeyCustomizationInfoManager", "restoreKeyCustomizationInfo, keyCode=" + i2 + " componentName is null.");
                                } else {
                                    keyCustomizationInfoManager.mHotKeyMap.put(i2, component);
                                }
                            }
                        }
                    }
                } else {
                    z = z2;
                }
                if (z) {
                    keyCustomizationInfoManager.saveSettingsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int sanitizeFlagSlippery(int i, int i2, int i3, String str) {
        if ((536870912 & i) == 0 || this.mContext.checkPermission("android.permission.ALLOW_SLIPPERY_TOUCHES", i3, i2) == 0) {
            return i;
        }
        PinnerService$$ExternalSyntheticOutline0.m("Removing FLAG_SLIPPERY from '", str, "' because it doesn't have ALLOW_SLIPPERY_TOUCHES permission", "WindowManager");
        return (-536870913) & i;
    }

    public final int sanitizeInputFeatures(int i, int i2, String str, int i3, boolean z) {
        if ((i & 4) != 0 && this.mContext.checkPermission("android.permission.MONITOR_INPUT", i3, i2) != 0) {
            throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Cannot use INPUT_FEATURE_SPY from '", str, "' because it doesn't the have MONITOR_INPUT permission"));
        }
        if ((i & 8) == 0 || z) {
            return i;
        }
        PinnerService$$ExternalSyntheticOutline0.m("Removing INPUT_FEATURE_SENSITIVE_FOR_PRIVACY from '", str, "' because it isn't a trusted overlay", "WindowManager");
        return i & (-9);
    }

    public final int sanitizeWindowType(Session session, int i, IBinder iBinder, int i2) {
        if (i2 == 2032 && iBinder != null) {
            WindowToken windowToken = this.mRoot.getDisplayContent(i).getWindowToken(iBinder);
            if (windowToken == null || i2 != windowToken.windowType) {
                return 0;
            }
        } else if (!session.mCanAddInternalSystemWindow && i2 != 0) {
            Slog.w("WindowManager", "Requires INTERNAL_SYSTEM_WINDOW permission if assign type to input. New type will be 0.");
            return 0;
        }
        return i2;
    }

    public final void saveANRStateLocked(ActivityRecord activityRecord, WindowState windowState, String str) {
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
            fastPrintWriter.println("  Reason: ".concat(str));
        }
        fastPrintWriter.println();
        final ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) this.mRoot.getChildAt(childCount);
            int i = displayContent.mDisplayId;
            final WindowState windowState2 = displayContent.mCurrentFocus;
            final ActivityRecord activityRecord2 = displayContent.mFocusedApp;
            fastPrintWriter.println("  Display #" + i + " currentFocus=" + windowState2 + " focusedApp=" + activityRecord2);
            if (!displayContent.mWinAddedSinceNullFocus.isEmpty()) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "  Windows added in display #", " since null focus: ");
                m.append(displayContent.mWinAddedSinceNullFocus);
                fastPrintWriter.println(m.toString());
            }
            if (!displayContent.mWinRemovedSinceNullFocus.isEmpty()) {
                StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m(i, "  Windows removed in display #", " since null focus: ");
                m2.append(displayContent.mWinRemovedSinceNullFocus);
                fastPrintWriter.println(m2.toString());
            }
            fastPrintWriter.println("  Tasks in top down Z order:");
            displayContent.forAllTaskDisplayAreas(new WindowManagerService$$ExternalSyntheticLambda38(0, fastPrintWriter));
            displayContent.mInputMonitor.dump(fastPrintWriter);
            fastPrintWriter.println();
            displayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda39
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    WindowState windowState3 = WindowState.this;
                    ActivityRecord activityRecord3 = activityRecord2;
                    ArrayList arrayList2 = arrayList;
                    WindowState windowState4 = (WindowState) obj;
                    int i2 = WindowManagerService.MY_PID;
                    if ((windowState3 == null || !Objects.equals(windowState4.mAttrs.packageName, windowState3.mAttrs.packageName)) && (activityRecord3 == null || !Objects.equals(windowState4.mAttrs.packageName, activityRecord3.packageName))) {
                        return;
                    }
                    arrayList2.add(windowState4);
                }
            }, true);
        }
        if (windowState != null && !arrayList.contains(windowState)) {
            arrayList.add(windowState);
        }
        RootWindowContainer rootWindowContainer = this.mRoot;
        rootWindowContainer.getClass();
        rootWindowContainer.forAllWindows((Consumer) new RootWindowContainer$$ExternalSyntheticLambda51(arrayList, fastPrintWriter, new int[1], z), true);
        fastPrintWriter.println();
        fastPrintWriter.close();
        this.mLastANRState = stringWriter.toString();
        this.mH.removeMessages(38);
        this.mH.sendEmptyMessageDelayed(38, 7200000L);
    }

    public final void saveWindowTraceToFile() {
        this.mWindowTracing.saveForBugreport(null);
    }

    public final void scheduleAnimationLocked() {
        WindowAnimator windowAnimator = this.mAnimator;
        if (windowAnimator.mAnimationFrameCallbackScheduled) {
            return;
        }
        windowAnimator.mAnimationFrameCallbackScheduled = true;
        windowAnimator.mChoreographer.postFrameCallback(windowAnimator.mAnimationFrameCallback);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void screenTurningOff(final int i, final WindowManagerPolicy.ScreenOffListener screenOffListener) {
        final TaskSnapshotController taskSnapshotController = this.mTaskSnapshotController;
        if (taskSnapshotController.shouldDisableSnapshots()) {
            ((DisplayPowerController.AnonymousClass4) screenOffListener).onScreenOff();
        } else {
            taskSnapshotController.mHandler.post(new Runnable() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TaskSnapshotController taskSnapshotController2 = TaskSnapshotController.this;
                    int i2 = i;
                    WindowManagerPolicy.ScreenOffListener screenOffListener2 = screenOffListener;
                    taskSnapshotController2.getClass();
                    try {
                        WindowManagerGlobalLock windowManagerGlobalLock = taskSnapshotController2.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                taskSnapshotController2.snapshotForSleeping(i2);
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } finally {
                        ((DisplayPowerController.AnonymousClass4) screenOffListener2).onScreenOff();
                    }
                }
            });
        }
    }

    public final Bitmap screenshotWallpaper() {
        Bitmap screenshotWallpaperLocked;
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "screenshotWallpaper()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        try {
            Trace.traceBegin(32L, "screenshotWallpaper");
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    screenshotWallpaperLocked = this.mRoot.getDisplayContent(0).mWallpaperController.screenshotWallpaperLocked(null);
                } finally {
                }
            }
            resetPriorityAfterLockedSection();
            return screenshotWallpaperLocked;
        } finally {
            Trace.traceEnd(32L);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
    
        if (r5 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004a, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0037, code lost:
    
        r5.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0035, code lost:
    
        if (r5 != null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setActiveTransactionTracing(boolean r6) {
        /*
            r5 = this;
            java.lang.String r0 = "android.permission.DUMP"
            java.lang.String r1 = "setActiveTransactionTracing()"
            r2 = 1
            boolean r5 = r5.checkCallingPermission$1(r0, r1, r2)
            if (r5 == 0) goto L55
            long r0 = android.os.Binder.clearCallingIdentity()
            r5 = 0
            java.lang.String r2 = "SurfaceFlinger"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Throwable -> L31 android.os.RemoteException -> L3d
            if (r2 == 0) goto L35
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> L31 android.os.RemoteException -> L3d
            java.lang.String r4 = "android.ui.ISurfaceComposer"
            r3.writeInterfaceToken(r4)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r4 = 0
            r3.writeInt(r6)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r6 = 1041(0x411, float:1.459E-42)
            r2.transact(r6, r3, r5, r4)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r5 = r3
            goto L35
        L2d:
            r5 = move-exception
            goto L4b
        L2f:
            r5 = r3
            goto L3d
        L31:
            r6 = move-exception
            r3 = r5
            r5 = r6
            goto L4b
        L35:
            if (r5 == 0) goto L47
        L37:
            r5.recycle()     // Catch: java.lang.Throwable -> L3b
            goto L47
        L3b:
            r5 = move-exception
            goto L51
        L3d:
            java.lang.String r6 = "WindowManager"
            java.lang.String r2 = "Failed to set transaction tracing"
            android.util.Slog.e(r6, r2)     // Catch: java.lang.Throwable -> L31
            if (r5 == 0) goto L47
            goto L37
        L47:
            android.os.Binder.restoreCallingIdentity(r0)
            return
        L4b:
            if (r3 == 0) goto L50
            r3.recycle()     // Catch: java.lang.Throwable -> L3b
        L50:
            throw r5     // Catch: java.lang.Throwable -> L3b
        L51:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r5
        L55:
            java.lang.SecurityException r5 = new java.lang.SecurityException
            java.lang.String r6 = "Requires DUMP permission"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.setActiveTransactionTracing(boolean):void");
    }

    public final void setAnimationScale(int i, float f) {
        if (!checkCallingPermission$1("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()", true)) {
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
        this.mH.sendEmptyMessage(14);
    }

    public final void setAnimationScales(float[] fArr) {
        if (!checkCallingPermission$1("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()", true)) {
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
                this.mH.obtainMessage(34, null).sendToTarget();
            }
        }
        this.mH.sendEmptyMessage(14);
    }

    public final void setAppContinuityMode(int i, String str, boolean z) {
    }

    public final void setCurrentUser(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                TransitionController transitionController = this.mAtmService.mWindowOrganizerController.mTransitionController;
                if (!transitionController.isCollecting() && transitionController.isShellTransitionsEnabled()) {
                    transitionController.requestStartTransition(transitionController.createTransition(1, 0), null, null, null);
                }
                this.mCurrentUserId = i;
                ((PhoneWindowManager) this.mPolicy).setCurrentUserLw(i);
                KeyguardDisableHandler keyguardDisableHandler = this.mKeyguardDisableHandler;
                synchronized (keyguardDisableHandler) {
                    keyguardDisableHandler.mCurrentUser = i;
                    keyguardDisableHandler.updateKeyguardEnabledLocked(-1);
                }
                this.mRoot.switchUser(i);
                this.mWindowPlacerLocked.performSurfacePlacement(false);
                DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                if (this.mDisplayReady) {
                    int forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked();
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

    public final void setDeadzoneHole(Bundle bundle) {
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        windowManagerServiceExt.getClass();
        if (CoreRune.FW_TSP_DEADZONE) {
            if (windowManagerServiceExt.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new SecurityException("setDeadzoneHole requires permission android.permission.WRITE_SECURE_SETTINGS");
            }
            TspStateController tspStateController = windowManagerServiceExt.mTspStateController;
            tspStateController.getClass();
            if (bundle == null) {
                Slog.w("TspStateManager", "setDeadzoneHole hole is null");
                return;
            }
            String string = bundle.getString("dead_zone_process_name", null);
            if (string == null) {
                Slog.w("TspStateManager", "setDeadzoneHole invalid name key");
                return;
            }
            int i = bundle.getInt("dead_zone_direction", 0);
            int i2 = bundle.getInt("dead_zone_port_y1", 0);
            int i3 = bundle.getInt("dead_zone_port_y2", 0);
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "holeName: ", string, ", direction: ", ", startY: ");
            m.append(i2);
            m.append(", endY: ");
            m.append(i3);
            Slog.d("TspStateManager", m.toString());
            if (!CoreRune.FW_TSP_DEADZONEHOLE_LAND && i > 2) {
                Slog.d("TspStateManager", "does not support top/bottom deadzone hole!");
                return;
            }
            ArrayMap arrayMap = new ArrayMap();
            synchronized (tspStateController.mDeadzoneHoleMap) {
                try {
                    if (i != 0 && i2 >= 0 && i3 >= 0 && i2 != i3) {
                        int min = Math.min(i2, i3);
                        int max = Math.max(i2, i3);
                        TspStateController.HoleInfo holeInfo = new TspStateController.HoleInfo();
                        holeInfo.direction = i;
                        holeInfo.startY = min;
                        holeInfo.endY = max;
                        tspStateController.mDeadzoneHoleMap.put(string, holeInfo);
                    } else if (tspStateController.mDeadzoneHoleMap.containsKey(string)) {
                        tspStateController.mDeadzoneHoleMap.remove(string);
                    }
                    int size = tspStateController.mDeadzoneHoleMap.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        ArrayMap arrayMap2 = tspStateController.mDeadzoneHoleMap;
                        TspStateController.HoleInfo holeInfo2 = (TspStateController.HoleInfo) arrayMap2.get(arrayMap2.keyAt(i4));
                        if (holeInfo2 != null) {
                            Rect rect = (Rect) arrayMap.get(Integer.valueOf(holeInfo2.direction));
                            if (rect == null) {
                                rect = new Rect(0, holeInfo2.startY, 0, holeInfo2.endY);
                            } else {
                                rect.top = Math.min(rect.top, holeInfo2.startY);
                                rect.bottom = Math.max(rect.bottom, holeInfo2.endY);
                            }
                            arrayMap.put(Integer.valueOf(holeInfo2.direction), rect);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            int size2 = arrayMap.size();
            tspStateController.mH.removeCallbacks(tspStateController.clearDeadzoneHole);
            if (size2 == 0) {
                tspStateController.writeDeadzoneHoleCmd(0, 0, 0);
                return;
            }
            for (int i5 = 0; i5 < size2; i5++) {
                Integer num = (Integer) arrayMap.keyAt(i5);
                int intValue = num.intValue();
                Rect rect2 = (Rect) arrayMap.get(num);
                TspStateController.DeviceSize deviceSize = tspStateController.mDeviceSize;
                int i6 = deviceSize.height;
                int i7 = deviceSize.initHeight;
                int i8 = rect2.top;
                float f = TspGripCommand.MIN_REJECT_ZONE_RATIO;
                tspStateController.writeDeadzoneHoleCmd(intValue, (i8 * i7) / i6, (i7 * rect2.bottom) / i6);
            }
        }
    }

    public final void setDisplayChangeWindowController(IDisplayChangeWindowController iDisplayChangeWindowController) {
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

    public final void setDisplayColorToSystemProperties(int i) {
        ActivityTaskManagerService.enforceTaskPermission("setDisplayColorToSystemProperties()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SystemProperties.set("persist.sys.sf.native_mode", Integer.toString(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDisplayHashThrottlingEnabled(boolean z) {
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "setDisplayHashThrottle()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        this.mDisplayHashController.mDisplayHashThrottlingEnabled = z;
    }

    public final void setDisplayImePolicy(int i, int i2) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "setDisplayImePolicy()", true)) {
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
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 3932627933834459400L, 1, "Attempted to set IME policy to a display that does not exist: %d", Long.valueOf(i));
                        }
                        resetPriorityAfterLockedSection();
                    } else {
                        if (!displayContentOrCreate.mDisplay.isTrusted()) {
                            throw new SecurityException("Attempted to set IME policy to an untrusted virtual display: " + i);
                        }
                        DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                        displayWindowSettings.getClass();
                        DisplayInfo displayInfo = displayContentOrCreate.mDisplayInfo;
                        DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
                        overrideSettings.mImePolicy = Integer.valueOf(i2);
                        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
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

    public final void setDisplayWindowInsetsController(int i, IDisplayWindowInsetsController iDisplayWindowInsetsController) {
        setDisplayWindowInsetsController_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.setRemoteInsetsController(iDisplayWindowInsetsController);
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

    public final void setDragSurfaceToOverlay(boolean z) {
        DisplayContent displayContent;
        SurfaceControl surfaceControl;
        if (!checkCallingPermission$1("android.permission.MANAGE_ACTIVITY_TASKS", "setDragSurfaceToOverlay", true)) {
            throw new SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragDropController dragDropController = this.mDragDropController;
                    DragState dragState = dragDropController.mDragState;
                    if (dragState != null && (displayContent = dragState.mDisplayContent) != null) {
                        if (z) {
                            dragState.mTransaction.setRelativeLayer(dragState.mSurfaceControl, displayContent.mOverlayLayer, -1);
                        } else {
                            WindowState window = displayContent.getWindow(new DisplayContent$$ExternalSyntheticLambda7(0));
                            if (window != null && (surfaceControl = window.mSurfaceControl) != null) {
                                DragState dragState2 = dragDropController.mDragState;
                                dragState2.mTransaction.setRelativeLayer(dragState2.mSurfaceControl, surfaceControl, -1);
                            }
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

    public final void setEventDispatching(boolean z) {
        if (!checkCallingPermission$1("android.permission.MANAGE_APP_TOKENS", "setEventDispatching()", true)) {
            throw new SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mEventDispatchingEnabled = z;
                if (this.mDisplayEnabled) {
                    InputManagerCallback inputManagerCallback = this.mInputManagerCallback;
                    if (inputManagerCallback.mInputDispatchEnabled != z) {
                        inputManagerCallback.mInputDispatchEnabled = z;
                        inputManagerCallback.mService.mInputManager.setInputDispatchMode(z, inputManagerCallback.mInputDispatchFrozen);
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void setFixedToUserRotation(int i, int i2) {
        if (!checkCallingPermission$1("android.permission.SET_ORIENTATION", "setFixedToUserRotation()", true)) {
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
                        Slog.w("WindowManager", "Trying to set fixed to user rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.mDisplayRotation.setFixedToUserRotation(i2);
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

    public final void setForcedDisplayDensityForUser(int i, int i2, int i3) {
        setForcedDisplayDensityForUser_enforcePermission();
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
            int i4 = -1;
            this.mExt.mMultiResolutionController.updateDisplaySizeDensityChangedReason(i, i3, i4, null, -1, i2, false);
        }
    }

    public final void setForcedDisplayScalingMode(int i, int i2) {
        setForcedDisplayScalingMode_enforcePermission();
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

    public final void setForcedDisplaySize(int i, int i2, int i3) {
        setForcedDisplaySize_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedSize(i2, i3, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, true, false, false);
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

    public final void setForcedDisplaySizeDensity(int i, int i2, int i3, int i4, boolean z, int i5) {
        setForcedDisplaySizeDensityWithInfo(new MultiResolutionChangeRequestInfo.Builder(0).setWidth(i2).setHeight(i3).setDensity(i4).setSaveToSettings(z).setForcedHideCutout(i5).build());
    }

    public final void setForcedDisplaySizeDensityWithInfo(MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) {
        MultiResolutionController multiResolutionController = this.mExt.mMultiResolutionController;
        if (multiResolutionController.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        if (multiResolutionChangeRequestInfo.getDisplayId() != 0) {
            throw new IllegalArgumentException("input illegalArgument(displayId=" + multiResolutionChangeRequestInfo.getDisplayId() + ")");
        }
        int width = multiResolutionChangeRequestInfo.getWidth();
        int height = multiResolutionChangeRequestInfo.getHeight();
        int density = multiResolutionChangeRequestInfo.getDensity();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            multiResolutionController.setForcedDisplaySizeDensityInner(multiResolutionChangeRequestInfo);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            multiResolutionController.updateDisplaySizeDensityChangedReason(multiResolutionChangeRequestInfo.getDisplayId(), -1, width, multiResolutionChangeRequestInfo.getCallerInfo(), height, density, multiResolutionChangeRequestInfo.getSaveToSettings());
        }
    }

    public final void setGlobalDragListener(IGlobalDragListener iGlobalDragListener) throws RemoteException {
        ActivityTaskManagerService.enforceTaskPermission("setUnhandledDragListener");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDragDropController.setGlobalDragListener(iGlobalDragListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void setIgnoreOrientationRequest(int i, boolean z) {
        setNullableIgnoreOrientationRequest(i, Boolean.valueOf(z));
    }

    public final void setInTouchMode(boolean z, int i) {
        int i2;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (this.mPerDisplayFocusEnabled && (displayContent == null || displayContent.mInTouchMode == z)) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                boolean z2 = displayContent != null && displayContent.hasOwnFocus();
                if (z2 && displayContent.mInTouchMode == z) {
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
                            if (displayContent2.mInTouchMode == z || displayContent2.hasOwnFocus()) {
                                i2 = size;
                            } else {
                                i2 = size;
                                if (this.mInputManager.mNative.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, displayContent2.mDisplayId) && displayContent2.mInTouchMode != z) {
                                    displayContent2.mInTouchMode = z;
                                }
                            }
                            i3++;
                            size = i2;
                        }
                        resetPriorityAfterLockedSection();
                    }
                    if (this.mInputManager.mNative.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, i) && displayContent.mInTouchMode != z) {
                        displayContent.mInTouchMode = z;
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

    public final void setInTouchModeOnAllDisplays(boolean z) {
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
                        if (displayContent.mInTouchMode != z) {
                            if (this.mInputManager.mNative.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, displayContent.mDisplayId) && displayContent.mInTouchMode != z) {
                                displayContent.mInTouchMode = z;
                            }
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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
    
        if (r5 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004a, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0037, code lost:
    
        r5.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0035, code lost:
    
        if (r5 != null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLayerTracing(boolean r6) {
        /*
            r5 = this;
            java.lang.String r0 = "android.permission.DUMP"
            java.lang.String r1 = "setLayerTracing()"
            r2 = 1
            boolean r5 = r5.checkCallingPermission$1(r0, r1, r2)
            if (r5 == 0) goto L55
            long r0 = android.os.Binder.clearCallingIdentity()
            r5 = 0
            java.lang.String r2 = "SurfaceFlinger"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Throwable -> L31 android.os.RemoteException -> L3d
            if (r2 == 0) goto L35
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> L31 android.os.RemoteException -> L3d
            java.lang.String r4 = "android.ui.ISurfaceComposer"
            r3.writeInterfaceToken(r4)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r4 = 0
            r3.writeInt(r6)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r6 = 1025(0x401, float:1.436E-42)
            r2.transact(r6, r3, r5, r4)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r5 = r3
            goto L35
        L2d:
            r5 = move-exception
            goto L4b
        L2f:
            r5 = r3
            goto L3d
        L31:
            r6 = move-exception
            r3 = r5
            r5 = r6
            goto L4b
        L35:
            if (r5 == 0) goto L47
        L37:
            r5.recycle()     // Catch: java.lang.Throwable -> L3b
            goto L47
        L3b:
            r5 = move-exception
            goto L51
        L3d:
            java.lang.String r6 = "WindowManager"
            java.lang.String r2 = "Failed to set layer tracing"
            android.util.Slog.e(r6, r2)     // Catch: java.lang.Throwable -> L31
            if (r5 == 0) goto L47
            goto L37
        L47:
            android.os.Binder.restoreCallingIdentity(r0)
            return
        L4b:
            if (r3 == 0) goto L50
            r3.recycle()     // Catch: java.lang.Throwable -> L3b
        L50:
            throw r5     // Catch: java.lang.Throwable -> L3b
        L51:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r5
        L55:
            java.lang.SecurityException r5 = new java.lang.SecurityException
            java.lang.String r6 = "Requires DUMP permission"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.setLayerTracing(boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
    
        if (r5 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004a, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0037, code lost:
    
        r5.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0035, code lost:
    
        if (r5 != null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLayerTracingFlags(int r6) {
        /*
            r5 = this;
            r0 = 1
            java.lang.String r1 = "android.permission.DUMP"
            java.lang.String r2 = "setLayerTracingFlags"
            boolean r5 = r5.checkCallingPermission$1(r1, r2, r0)
            if (r5 == 0) goto L55
            long r0 = android.os.Binder.clearCallingIdentity()
            r5 = 0
            java.lang.String r2 = "SurfaceFlinger"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)     // Catch: java.lang.Throwable -> L31 android.os.RemoteException -> L3d
            if (r2 == 0) goto L35
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> L31 android.os.RemoteException -> L3d
            java.lang.String r4 = "android.ui.ISurfaceComposer"
            r3.writeInterfaceToken(r4)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r3.writeInt(r6)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r6 = 1033(0x409, float:1.448E-42)
            r4 = 0
            r2.transact(r6, r3, r5, r4)     // Catch: java.lang.Throwable -> L2d android.os.RemoteException -> L2f
            r5 = r3
            goto L35
        L2d:
            r5 = move-exception
            goto L4b
        L2f:
            r5 = r3
            goto L3d
        L31:
            r6 = move-exception
            r3 = r5
            r5 = r6
            goto L4b
        L35:
            if (r5 == 0) goto L47
        L37:
            r5.recycle()     // Catch: java.lang.Throwable -> L3b
            goto L47
        L3b:
            r5 = move-exception
            goto L51
        L3d:
            java.lang.String r6 = "WindowManager"
            java.lang.String r2 = "Failed to set layer tracing flags"
            android.util.Slog.e(r6, r2)     // Catch: java.lang.Throwable -> L31
            if (r5 == 0) goto L47
            goto L37
        L47:
            android.os.Binder.restoreCallingIdentity(r0)
            return
        L4b:
            if (r3 == 0) goto L50
            r3.recycle()     // Catch: java.lang.Throwable -> L3b
        L50:
            throw r5     // Catch: java.lang.Throwable -> L3b
        L51:
            android.os.Binder.restoreCallingIdentity(r0)
            throw r5
        L55:
            java.lang.SecurityException r5 = new java.lang.SecurityException
            java.lang.String r6 = "Requires DUMP permission"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.setLayerTracingFlags(int):void");
    }

    public final void setMaxAspectRatioPolicy(String str, int i, boolean z, int i2) {
    }

    public final void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) {
        setNavBarVirtualKeyHapticFeedbackEnabled_enforcePermission();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ((PhoneWindowManager) this.mPolicy).mNavBarVirtualKeyHapticFeedbackEnabled = z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void setNullableIgnoreOrientationRequest(int i, Boolean bool) {
        boolean z;
        boolean z2 = CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY;
        if (z2 && bool == null) {
            bool = Boolean.FALSE;
            z = true;
        } else {
            z = false;
        }
        if (!checkCallingPermission$1("android.permission.SET_ORIENTATION", "setIgnoreOrientationRequest()", true)) {
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
                        Slog.w("WindowManager", "Trying to setIgnoreOrientationRequest() for a missing display.");
                    } else if (!z2 || !z) {
                        displayContent.setIgnoreOrientationRequest(bool.booleanValue());
                        resetPriorityAfterLockedSection();
                        return;
                    } else {
                        this.mDisplayWindowSettings.setNullableIgnoreOrientationRequest(displayContent, null);
                        displayContent.setIgnoreOrientationRequestOverrideIfNeeded();
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

    public final void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2) {
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
            DisplayContent displayContent = (DisplayContent) this.mRoot.getChildAt(childCount);
            ActivityRecord activityRecord = displayContent.mFocusedApp;
            if (activityRecord != null) {
                displayContent.mOrientationRequestingTaskDisplayArea = activityRecord.getDisplayArea();
            }
            if (displayContent.mSetIgnoreOrientationRequest) {
                displayContent.updateOrientation(false);
            }
        }
    }

    public final void setOverrideFoldedArea(Rect rect) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayFoldController displayFoldController = ((PhoneWindowManager) this.mPolicy).mDisplayFoldController;
                    if (displayFoldController != null) {
                        displayFoldController.mOverrideFoldedArea.set(rect);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPendingIntentAfterUnlock(android.app.PendingIntent r19, android.content.Intent r20) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.setPendingIntentAfterUnlock(android.app.PendingIntent, android.content.Intent):void");
    }

    public void setRecentsAnimationController(RecentsAnimationController recentsAnimationController) {
        this.mRecentsAnimationController = recentsAnimationController;
    }

    public final void setRecentsAppBehindSystemBars(boolean z) {
        if (!checkCallingPermission$1("android.permission.START_TASKS_FROM_RECENTS", "setRecentsAppBehindSystemBars()", true)) {
            throw new SecurityException("Requires START_TASKS_FROM_RECENTS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task task = this.mRoot.getTask(new WindowManagerService$$ExternalSyntheticLambda25(0));
                    if (task != null) {
                        task.getTask().mCanAffectSystemUiFlags = z;
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

    public final void setRecentsVisibility(boolean z) {
        if (!checkCallingPermission$1("android.permission.STATUS_BAR", "setRecentsVisibility()", true)) {
            throw new SecurityException("Requires STATUS_BAR permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ((PhoneWindowManager) this.mPolicy).mRecentsVisible = z;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void setRemoveContentMode(int i, int i2) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "setRemoveContentMode()", true)) {
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
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -5574580669790275797L, 1, "Attempted to set remove mode to a display that does not exist: %d", Long.valueOf(i));
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                    displayWindowSettings.getClass();
                    DisplayInfo displayInfo = displayContentOrCreate.mDisplayInfo;
                    DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                    DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
                    overrideSettings.mRemoveContentMode = i2;
                    displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
                    displayContentOrCreate.reconfigureDisplayLocked();
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

    public final void setSandboxDisplayApis(int i, boolean z) {
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
                        displayContent.mSandboxDisplayApis = z;
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

    public final void setShellRootAccessibilityWindow(int i, int i2, IWindow iWindow) {
        setShellRootAccessibilityWindow_enforcePermission();
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

    public final void setShouldShowSystemDecors(int i, boolean z) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "setShouldShowSystemDecors()", true)) {
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
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 3056518663346732662L, 1, "Attempted to set system decors flag to a display that does not exist: %d", Long.valueOf(i));
                        }
                        resetPriorityAfterLockedSection();
                    } else {
                        if (!displayContentOrCreate.mDisplay.isTrusted()) {
                            throw new SecurityException("Attempted to set system decors flag to an untrusted virtual display: " + i);
                        }
                        DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                        displayWindowSettings.getClass();
                        DisplayInfo displayInfo = displayContentOrCreate.mDisplayInfo;
                        DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
                        overrideSettings.mShouldShowSystemDecors = Boolean.valueOf(z);
                        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
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

    public final void setShouldShowWithInsecureKeyguard(int i, boolean z) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "setShouldShowWithInsecureKeyguard()", true)) {
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
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 8186524992939307511L, 1, "Attempted to set flag to a display that does not exist: %d", Long.valueOf(i));
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                    displayWindowSettings.getClass();
                    if (!displayContentOrCreate.isPrivate() && z) {
                        throw new IllegalArgumentException("Public display can't be allowed to show content when locked");
                    }
                    DisplayInfo displayInfo = displayContentOrCreate.mDisplayInfo;
                    DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                    DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
                    overrideSettings.mShouldShowWithInsecureKeyguard = Boolean.valueOf(z);
                    displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
                    displayContentOrCreate.reconfigureDisplayLocked();
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

    public final void setStrictModeVisualIndicatorPreference(String str) {
        SystemProperties.set("persist.sys.strictmode.visual", str);
    }

    public final void setSupportsFlexPanel(int i, String str, boolean z) {
        if (CoreRune.FW_FLEX_PANEL_CONTROL) {
            ActivityTaskManagerService.enforceTaskPermission("setSupportsFlexPanel()");
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("setSupportsFlexPanel, packageName=", str));
            }
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    FlexPanelController flexPanelController = this.mAtmService.mExt.mFlexPanelController;
                    flexPanelController.mUserChange.putValue(str, i, Boolean.valueOf(z));
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    public final void setSwitchingUser(boolean z) {
        if (!checkCallingPermission$1("android.permission.INTERACT_ACROSS_USERS_FULL", "setSwitchingUser()", true)) {
            throw new SecurityException("Requires INTERACT_ACROSS_USERS_FULL permission");
        }
        ((PhoneWindowManager) this.mPolicy).setSwitchingUser(z);
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

    public final void setTableModeEnabled(boolean z) {
        if (CoreRune.FW_FLEXIBLE_TABLE_MODE) {
            this.mExt.getClass();
            throw null;
        }
    }

    public final void setTaskSnapshotEnabled(boolean z) {
        this.mTaskSnapshotController.mSnapshotEnabled = z;
    }

    public final void setTspDeadzone(Session session, IWindow iWindow, Bundle bundle) {
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        windowManagerServiceExt.getClass();
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked = windowManagerServiceExt.mService.windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked == null) {
                        Slog.e("WindowManagerServiceExt", "setTspDeadzone failed. The win is null.");
                        resetPriorityAfterLockedSection();
                    } else {
                        windowForClientLocked.mTspDeadzone = bundle;
                        windowManagerServiceExt.updateTspStateControllerWindowPolicyLocked(windowForClientLocked);
                        resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public final void setTspNoteMode(Session session, IWindow iWindow, boolean z) {
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        windowManagerServiceExt.getClass();
        if (CoreRune.FW_TSP_NOTE_MODE) {
            int callingUid = Binder.getCallingUid();
            Context context = windowManagerServiceExt.mContext;
            if (!Settings.checkAndNoteWriteSettingsOperation(context, callingUid, Settings.getPackageNameForUid(context, callingUid), false)) {
                throw new SecurityException("Requires WRITE_SETTINGS permission.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowState windowForClientLocked = windowManagerServiceExt.mService.windowForClientLocked(session, iWindow, false);
                        if (windowForClientLocked == null) {
                            Slog.e("WindowManagerServiceExt", "setTspNoteMode failed. The win is null.");
                            resetPriorityAfterLockedSection();
                        } else {
                            windowForClientLocked.mIsTspNoteMode = z;
                            windowManagerServiceExt.updateTspStateControllerWindowPolicyLocked(windowForClientLocked);
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
    }

    public final void setWindowingMode(int i, int i2) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "setWindowingMode()", true)) {
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
                        if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 3938331948687900219L, 1, "Attempted to set windowing mode to a display that does not exist: %d", Long.valueOf(i));
                        }
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    int windowingMode = displayContentOrCreate.getWindowingMode();
                    DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                    displayWindowSettings.getClass();
                    DisplayInfo displayInfo = displayContentOrCreate.mDisplayInfo;
                    DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
                    DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
                    overrideSettings.mWindowingMode = i2;
                    TaskDisplayArea defaultTaskDisplayArea = displayContentOrCreate.getDefaultTaskDisplayArea();
                    if (defaultTaskDisplayArea != null) {
                        defaultTaskDisplayArea.setWindowingMode(i2);
                    }
                    displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
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

    public final boolean shouldShowSystemDecors(int i) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "shouldShowSystemDecors()", true)) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    boolean supportsSystemDecorations = displayContent.supportsSystemDecorations();
                    resetPriorityAfterLockedSection();
                    return supportsSystemDecorations;
                }
                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -600035824255550632L, 1, "Attempted to get system decors flag of a display that does not exist: %d", Long.valueOf(i));
                }
                resetPriorityAfterLockedSection();
                return false;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean shouldShowWithInsecureKeyguard(int i) {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "shouldShowWithInsecureKeyguard()", true)) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 525945815055875796L, 1, "Attempted to get flag of a display that does not exist: %d", Long.valueOf(i));
                    }
                    resetPriorityAfterLockedSection();
                    return false;
                }
                DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
                displayWindowSettings.getClass();
                Boolean bool = displayWindowSettings.mSettingsProvider.getSettings(displayContent.mDisplayInfo).mShouldShowWithInsecureKeyguard;
                boolean booleanValue = bool != null ? bool.booleanValue() : false;
                resetPriorityAfterLockedSection();
                return booleanValue;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void showBootMessage(CharSequence charSequence, boolean z) {
        showBootMessage(charSequence, z, 0);
    }

    public final void showBootMessage(CharSequence charSequence, boolean z, int i) {
        boolean z2;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[2]) {
                    ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_BOOT, -333924817004774456L, 1020, null, String.valueOf(charSequence), Boolean.valueOf(z), Boolean.valueOf(this.mAllowBootMessages), Boolean.valueOf(this.mShowingBootMessages), Boolean.valueOf(this.mSystemBooted), String.valueOf(new RuntimeException("here").fillInStackTrace()));
                }
                if (!this.mAllowBootMessages) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (this.mShowingBootMessages) {
                    z2 = false;
                } else {
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
                PhoneWindowManagerExt phoneWindowManagerExt = this.mExt.mPolicyExt;
                phoneWindowManagerExt.mHandler.post(new PhoneWindowManagerExt$$ExternalSyntheticLambda0(phoneWindowManagerExt, i, 2));
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

    public final void showEmulatorDisplayOverlay() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mEmulatorDisplayOverlay == null) {
                    Context context = this.mContext;
                    DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                    this.mPolicy.getClass();
                    this.mEmulatorDisplayOverlay = new EmulatorDisplayOverlay(context, defaultDisplayContentLocked, (WindowManagerPolicy.getWindowLayerFromTypeLw(2018) * 10000) + 10, this.mTransaction);
                }
                EmulatorDisplayOverlay emulatorDisplayOverlay = this.mEmulatorDisplayOverlay;
                SurfaceControl.Transaction transaction = this.mTransaction;
                if (emulatorDisplayOverlay.mSurfaceControl != null) {
                    emulatorDisplayOverlay.mVisible = true;
                    emulatorDisplayOverlay.drawIfNeeded(transaction);
                    transaction.show(emulatorDisplayOverlay.mSurfaceControl);
                }
                this.mTransaction.apply();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void showEmulatorDisplayOverlayIfNeeded() {
        if (this.mContext.getResources().getBoolean(R.bool.kg_enable_camera_default_widget) && SystemProperties.getBoolean("ro.boot.emulator.circular", false) && Build.IS_EMULATOR) {
            H h = this.mH;
            h.sendMessage(h.obtainMessage(36));
        }
    }

    public final void showGlobalActions() {
        if (!checkCallingPermission$1("android.permission.INTERNAL_SYSTEM_WINDOW", "showGlobalActions()", true)) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        ((PhoneWindowManager) this.mPolicy).showGlobalActions();
    }

    public final void showRecentApps() {
        PhoneWindowManager phoneWindowManager = (PhoneWindowManager) this.mPolicy;
        phoneWindowManager.mHandler.removeMessages(9);
        phoneWindowManager.mHandler.obtainMessage(9).sendToTarget();
    }

    public final void showStrictModeViolation(boolean z) {
        int callingPid = Binder.getCallingPid();
        if (!z) {
            H h = this.mH;
            h.sendMessage(h.obtainMessage(25, 0, callingPid));
        } else {
            H h2 = this.mH;
            h2.sendMessage(h2.obtainMessage(25, 1, callingPid));
            H h3 = this.mH;
            h3.sendMessageDelayed(h3.obtainMessage(25, 0, callingPid), 1000L);
        }
    }

    public final void showToastIfBlockingScreenCapture(WindowState windowState) {
        int i = windowState.mOwnerUid;
        if (this.mCaptureBlockedToastShownUids.contains(i)) {
            return;
        }
        if (this.mSensitiveContentPackages.shouldBlockScreenCaptureForApp(i, windowState.mClient.asBinder(), windowState.mAttrs.packageName)) {
            this.mCaptureBlockedToastShownUids.add(i);
            this.mH.post(new WindowManagerService$$ExternalSyntheticLambda19(2, this));
            if (Flags.sensitiveContentMetricsBugfix() && this.mSensitiveContentPackages.shouldBlockScreenCaptureForApp(i, null, windowState.mAttrs.packageName)) {
                FrameworkStatsLog.write(FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_APPLIED, this.mSensitiveContentProtectionSessionId, i);
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void shutdown(boolean z) {
        ShutdownThread.shutdown(ActivityThread.currentActivityThread().getSystemUiContext(), "userrequested", z);
    }

    public final Bitmap snapshotTaskForRecents(int i) {
        TaskSnapshot captureSnapshot;
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "snapshotTaskForRecents()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRoot.anyTaskForId(i, 1, null, false);
                    if (anyTaskForId == null) {
                        throw new IllegalArgumentException("Failed to find matching task for taskId=" + i);
                    }
                    captureSnapshot = this.mTaskSnapshotController.captureSnapshot(anyTaskForId);
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

    public final void startFreezingDisplay(final int i, final int i2, final int i3, final DisplayContent displayContent) {
        if (this.mDisplayFrozen || displayContent.mDisplayRotation.mRotatingSeamlessly) {
            return;
        }
        int i4 = displayContent.mDisplayId;
        boolean z = i4 == 2 && ((PhoneWindowManager) this.mPolicy).isScreenOn(i4);
        if (displayContent.isReady() && displayContent.mDisplayPolicy.mScreenOnFully) {
            if ((displayContent.mDisplayInfo.state != 1 || z) && displayContent.okToAnimate()) {
                displayContent.requestDisplayUpdate(new Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowManagerService windowManagerService = WindowManagerService.this;
                        int i5 = i;
                        int i6 = i2;
                        DisplayContent displayContent2 = displayContent;
                        int i7 = i3;
                        int i8 = WindowManagerService.MY_PID;
                        windowManagerService.getClass();
                        Trace.traceBegin(32L, "WMS.doStartFreezingDisplay");
                        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[0]) {
                            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1615905649072328410L, 5, null, Long.valueOf(i5), Long.valueOf(i6), String.valueOf(Debug.getCallers(8)));
                        }
                        windowManagerService.mScreenFrozenLock.acquire();
                        windowManagerService.mAtmService.startPowerMode(2);
                        windowManagerService.mDisplayFrozen = true;
                        windowManagerService.mDisplayFreezeTime = SystemClock.elapsedRealtime();
                        windowManagerService.mLastFinishedFreezeSource = null;
                        windowManagerService.mFrozenDisplayId = displayContent2.mDisplayId;
                        InputManagerCallback inputManagerCallback = windowManagerService.mInputManagerCallback;
                        if (!inputManagerCallback.mInputDispatchFrozen) {
                            inputManagerCallback.mInputDispatchFrozen = true;
                            inputManagerCallback.mService.mInputManager.setInputDispatchMode(inputManagerCallback.mInputDispatchEnabled, true);
                        }
                        if (displayContent2.mAppTransition.isTransitionSet()) {
                            AppTransition appTransition = displayContent2.mAppTransition;
                            boolean contains = appTransition.mNextAppTransitionRequests.contains(7);
                            RemoteAnimationController remoteAnimationController = appTransition.mRemoteAnimationController;
                            if (remoteAnimationController != null) {
                                remoteAnimationController.cancelAnimation("freeze");
                            }
                            appTransition.mNextAppTransitionRequests.clear();
                            appTransition.clear(true);
                            appTransition.mAppTransitionState = 1;
                            appTransition.updateBooster();
                            IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = appTransition.mNextAppTransitionAnimationsSpecsFuture;
                            if (iAppTransitionAnimationSpecsFuture != null) {
                                appTransition.mNextAppTransitionAnimationsSpecsPending = true;
                                appTransition.mNextAppTransitionAnimationsSpecsFuture = null;
                                appTransition.mDefaultExecutor.execute(new AppTransition$$ExternalSyntheticLambda0(appTransition, iAppTransitionAnimationSpecsFuture));
                            }
                            for (int i9 = 0; i9 < appTransition.mListeners.size(); i9++) {
                                ((WindowManagerInternal.AppTransitionListener) appTransition.mListeners.get(i9)).onAppTransitionCancelledLocked(contains);
                            }
                        }
                        windowManagerService.mLatencyTracker.onActionStart(6);
                        windowManagerService.mExitAnimId = i5;
                        windowManagerService.mEnterAnimId = i6;
                        if (i7 == -1) {
                            i7 = displayContent2.mDisplayInfo.rotation;
                        }
                        displayContent2.setRotationAnimation(new ScreenRotationAnimation(i7, displayContent2));
                        Trace.traceEnd(32L);
                    }
                });
            }
        }
    }

    public final void startFreezingScreen(int i, int i2) {
        if (!checkCallingPermission$1("android.permission.FREEZE_SCREEN", "startFreezingScreen()", true)) {
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
                        startFreezingDisplay(i, i2, -1, getDefaultDisplayContentLocked());
                        this.mH.removeMessages(30);
                        this.mH.sendEmptyMessageDelayed(30, 5000L);
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

    public final void startLockscreenFingerprintAuth() {
        this.mExt.mPolicyExt.startLockscreenFingerprintAuth();
    }

    public final boolean startRemoteWallpaperAnimation(IRemoteAnimationRunner iRemoteAnimationRunner, int i) {
        if (!CoreRune.FW_REMOTE_WALLPAPER_ANIM || i != 0 || iRemoteAnimationRunner == null || iRemoteAnimationRunner.asBinder() == null) {
            return false;
        }
        if (!checkCallingPermission$1("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "startRemoteWallpaperAnimation()", true)) {
            throw new SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        resetPriorityAfterLockedSection();
                        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "startRemoteWallpaperAnimation, d=", ", leash=null", "WindowManager");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    SurfaceControl startRemoteWallpaperAnimation = displayContent.mWallpaperController.startRemoteWallpaperAnimation(iRemoteAnimationRunner.asBinder(), callingPid, iRemoteAnimationRunner);
                    boolean z = startRemoteWallpaperAnimation != null;
                    resetPriorityAfterLockedSection();
                    Slog.d("WindowManager", "startRemoteWallpaperAnimation, d=" + i + ", leash=" + startRemoteWallpaperAnimation);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z;
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Slog.d("WindowManager", "startRemoteWallpaperAnimation, d=" + i + ", leash=" + ((Object) null));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final void startSurfaceAnimation(IBinder iBinder, String str) {
        Slog.v("WindowManager", "[SEC_SF_EFFECTS] WindowManagerService.startSurfaceAnimation()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    Slog.e("WindowManager", "startSurfaceAnimation, WindowState not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    Slog.e("WindowManager", "startSurfaceAnimation, surface isn't created");
                    resetPriorityAfterLockedSection();
                    return;
                }
                WindowManagerService windowManagerService = windowSurfaceController.mService;
                if (windowSurfaceController.mSurfaceControl != null) {
                    try {
                        windowSurfaceController.mAnimator.mWin.getPendingTransaction().startSurfaceAnimation(windowSurfaceController.mSurfaceControl, str);
                    } finally {
                        windowManagerService.scheduleAnimationLocked();
                    }
                }
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void startTransitionTrace() {
        this.mTransitionTracer.startTrace(null);
    }

    public final boolean startViewServer(int i) {
        if (isSystemSecure() || !checkCallingPermission$1("android.permission.DUMP", "startViewServer", true) || i < 1024) {
            return false;
        }
        ViewServer viewServer = this.mViewServer;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_ERROR_enabled;
        if (viewServer == null) {
            try {
                ViewServer viewServer2 = new ViewServer(this, i);
                this.mViewServer = viewServer2;
                return viewServer2.start();
            } catch (IOException unused) {
                if (zArr[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 8664813170125714536L, 0, "View server did not start", null);
                }
                return false;
            }
        }
        Thread thread = viewServer.mThread;
        if (thread == null || !thread.isAlive()) {
            try {
                return this.mViewServer.start();
            } catch (IOException unused2) {
                if (zArr[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 8664813170125714536L, 0, "View server did not start", null);
                }
            }
        }
        return false;
    }

    public final void startWindowTrace() {
        this.mWindowTracing.startTrace(null);
    }

    public final void stopFreezingDisplayLocked() {
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        ScreenRotationAnimation screenRotationAnimation;
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
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
            if (!z && !z2 && this.mAppsFreezingScreen <= 0) {
                boolean z5 = true;
                if (this.mWindowsFreezingScreen != 1 && !this.mClientFreezingScreen && i <= 0) {
                    Trace.traceBegin(32L, "WMS.doStopFreezingDisplayLocked-" + this.mLastFinishedFreezeSource);
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -6877112251967196129L, 0, null, null);
                    }
                    this.mFrozenDisplayId = -1;
                    this.mDisplayFrozen = false;
                    InputManagerCallback inputManagerCallback = this.mInputManagerCallback;
                    if (inputManagerCallback.mInputDispatchFrozen) {
                        inputManagerCallback.mInputDispatchFrozen = false;
                        inputManagerCallback.mService.mInputManager.setInputDispatchMode(inputManagerCallback.mInputDispatchEnabled, false);
                    }
                    this.mLastDisplayFreezeDuration = (int) (SystemClock.elapsedRealtime() - this.mDisplayFreezeTime);
                    StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "Screen frozen for ");
                    TimeUtils.formatDuration(this.mLastDisplayFreezeDuration, m);
                    if (this.mLastFinishedFreezeSource != null) {
                        m.append(" due to ");
                        m.append(this.mLastFinishedFreezeSource);
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[2]) {
                        ProtoLogImpl_54989576.i(ProtoLogGroup.WM_ERROR, 721393258715103117L, 0, "%s", String.valueOf(m.toString()));
                    }
                    this.mH.removeMessages(17);
                    this.mH.removeMessages(30);
                    ScreenRotationAnimation screenRotationAnimation2 = displayContent == null ? null : displayContent.mScreenRotationAnimation;
                    if (screenRotationAnimation2 == null || screenRotationAnimation2.mScreenshotLayer == null) {
                        if (screenRotationAnimation2 != null) {
                            screenRotationAnimation2.kill();
                            displayContent.setRotationAnimation(null);
                        }
                        z3 = true;
                    } else {
                        if (zArr[2]) {
                            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, -5706083447992207254L, 0, null, null);
                        }
                        DisplayInfo displayInfo = displayContent.mDisplayInfo;
                        DisplayRotation displayRotation = displayContent.mDisplayRotation;
                        int i2 = this.mExitAnimId;
                        int i3 = this.mEnterAnimId;
                        displayRotation.getClass();
                        switch (i2) {
                            case R.anim.slide_out_micro:
                            case R.anim.slow_fade_in:
                                DisplayRotation.RotationAnimationPair selectRotationAnimation = displayRotation.selectRotationAnimation();
                                if (i2 != selectRotationAnimation.mExit || i3 != selectRotationAnimation.mEnter) {
                                    this.mEnterAnimId = 0;
                                    this.mExitAnimId = 0;
                                    break;
                                }
                                break;
                        }
                        SurfaceControl.Transaction transaction = this.mTransaction;
                        float transitionAnimationScaleLocked = getTransitionAnimationScaleLocked();
                        int i4 = displayInfo.logicalWidth;
                        int i5 = displayInfo.logicalHeight;
                        int i6 = this.mExitAnimId;
                        int i7 = this.mEnterAnimId;
                        if (screenRotationAnimation2.mScreenshotLayer != null) {
                            if (!screenRotationAnimation2.mStarted) {
                                screenRotationAnimation2.mEndLuma = TransitionAnimation.getBorderLuma(screenRotationAnimation2.mDisplayContent.getWindowingLayer(), i4, i5);
                                if (screenRotationAnimation2.mScreenshotLayer != null && !screenRotationAnimation2.mStarted) {
                                    screenRotationAnimation2.mStarted = true;
                                    int i8 = screenRotationAnimation2.mCurRotation;
                                    int i9 = screenRotationAnimation2.mOriginalRotation;
                                    int deltaRotation = RotationUtils.deltaRotation(i8, i9);
                                    if (i6 == 0 || i7 == 0) {
                                        if (deltaRotation == 0) {
                                            screenRotationAnimation2.mRotateExitAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, R.anim.wallpaper_close_exit);
                                            screenRotationAnimation2.mRotateEnterAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, R.anim.slide_out_left);
                                        } else if (deltaRotation == 1) {
                                            screenRotationAnimation2.mRotateExitAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, R.anim.ft_avd_tooverflow_rectangle_2_animation);
                                            screenRotationAnimation2.mRotateEnterAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, CoreRune.FW_CUSTOM_BASIC_ANIM ? R.anim.toast_enter : R.anim.ft_avd_tooverflow_rectangle_1_pivot_animation);
                                        } else if (deltaRotation == 2) {
                                            screenRotationAnimation2.mRotateExitAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, R.anim.wallpaper_exit);
                                            screenRotationAnimation2.mRotateEnterAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, CoreRune.FW_CUSTOM_BASIC_ANIM ? R.anim.task_open_enter_cross_profile_apps : R.anim.wallpaper_enter);
                                        } else if (deltaRotation == 3) {
                                            screenRotationAnimation2.mRotateExitAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, R.anim.window_move_from_decor);
                                            screenRotationAnimation2.mRotateEnterAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, CoreRune.FW_CUSTOM_BASIC_ANIM ? R.anim.task_open_exit : R.anim.wallpaper_open_exit);
                                        }
                                        z4 = false;
                                    } else {
                                        screenRotationAnimation2.mRotateExitAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, i6);
                                        screenRotationAnimation2.mRotateEnterAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, i7);
                                        screenRotationAnimation2.mRotateAlphaAnimation = AnimationUtils.loadAnimation(screenRotationAnimation2.mContext, R.anim.wallpaper_intra_close_exit);
                                        z4 = true;
                                    }
                                    if (zArr[0]) {
                                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -6586462455018013482L, 0, null, String.valueOf(z4), String.valueOf(Surface.rotationToString(screenRotationAnimation2.mCurRotation)), String.valueOf(Surface.rotationToString(i9)));
                                    }
                                    Animation animation = screenRotationAnimation2.mRotateExitAnimation;
                                    int i10 = screenRotationAnimation2.mOriginalWidth;
                                    int i11 = screenRotationAnimation2.mOriginalHeight;
                                    animation.initialize(i4, i5, i10, i11);
                                    screenRotationAnimation2.mRotateExitAnimation.restrictDuration(10000L);
                                    screenRotationAnimation2.mRotateExitAnimation.scaleCurrentDuration(transitionAnimationScaleLocked);
                                    screenRotationAnimation2.mRotateEnterAnimation.initialize(i4, i5, i10, i11);
                                    screenRotationAnimation2.mRotateEnterAnimation.restrictDuration(10000L);
                                    screenRotationAnimation2.mRotateEnterAnimation.scaleCurrentDuration(transitionAnimationScaleLocked);
                                    screenRotationAnimation2.mFinishAnimReady = false;
                                    screenRotationAnimation2.mFinishAnimStartTime = -1L;
                                    if (z4) {
                                        screenRotationAnimation2.mRotateAlphaAnimation.restrictDuration(10000L);
                                        screenRotationAnimation2.mRotateAlphaAnimation.scaleCurrentDuration(transitionAnimationScaleLocked);
                                    }
                                    if (z4 && screenRotationAnimation2.mEnteringBlackFrame == null) {
                                        try {
                                            screenRotationAnimation2.mEnteringBlackFrame = new BlackFrame(screenRotationAnimation2.mService.mTransactionFactory, transaction, new Rect(-i4, -i5, i4 * 2, i5 * 2), new Rect(0, 0, i4, i5), screenRotationAnimation2.mDisplayContent, screenRotationAnimation2.mEnterBlackFrameLayer);
                                        } catch (Surface.OutOfResourcesException e) {
                                            Slog.w("WindowManager", "Unable to allocate black surface", e);
                                        }
                                    }
                                    if (z4) {
                                        ScreenRotationAnimation.SurfaceRotationAnimationController surfaceRotationAnimationController = screenRotationAnimation2.mSurfaceRotationAnimationController;
                                        screenRotationAnimation = ScreenRotationAnimation.this;
                                        try {
                                            SurfaceAnimationRunner surfaceAnimationRunner = screenRotationAnimation.mService.mSurfaceAnimationRunner;
                                            synchronized (surfaceAnimationRunner.mLock) {
                                                surfaceAnimationRunner.mAnimationStartDeferred = true;
                                            }
                                            surfaceRotationAnimationController.mRotateScreenAnimator = surfaceRotationAnimationController.startScreenshotAlphaAnimation();
                                            surfaceRotationAnimationController.mDisplayAnimator = surfaceRotationAnimationController.startDisplayRotation();
                                            if (screenRotationAnimation.mEnteringBlackFrame != null) {
                                                SimpleSurfaceAnimatable.Builder initializeBuilder = surfaceRotationAnimationController.initializeBuilder();
                                                initializeBuilder.mSurfaceControl = screenRotationAnimation.mEnterBlackFrameLayer;
                                                initializeBuilder.mAnimationLeashParent = screenRotationAnimation.mDisplayContent.mOverlayLayer;
                                                surfaceRotationAnimationController.mEnterBlackFrameAnimator = surfaceRotationAnimationController.startAnimation(initializeBuilder.build(), ScreenRotationAnimation.SurfaceRotationAnimationController.createWindowAnimationSpec(screenRotationAnimation.mRotateEnterAnimation), new ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(surfaceRotationAnimationController));
                                            }
                                        } finally {
                                        }
                                    } else {
                                        ScreenRotationAnimation.SurfaceRotationAnimationController surfaceRotationAnimationController2 = screenRotationAnimation2.mSurfaceRotationAnimationController;
                                        screenRotationAnimation = ScreenRotationAnimation.this;
                                        try {
                                            SurfaceAnimationRunner surfaceAnimationRunner2 = screenRotationAnimation.mService.mSurfaceAnimationRunner;
                                            synchronized (surfaceAnimationRunner2.mLock) {
                                                surfaceAnimationRunner2.mAnimationStartDeferred = true;
                                            }
                                            surfaceRotationAnimationController2.mDisplayAnimator = surfaceRotationAnimationController2.startDisplayRotation();
                                            SimpleSurfaceAnimatable.Builder initializeBuilder2 = surfaceRotationAnimationController2.initializeBuilder();
                                            initializeBuilder2.mSurfaceControl = screenRotationAnimation.mScreenshotLayer;
                                            initializeBuilder2.mAnimationLeashParent = screenRotationAnimation.mDisplayContent.mOverlayLayer;
                                            surfaceRotationAnimationController2.mScreenshotRotationAnimator = surfaceRotationAnimationController2.startAnimation(initializeBuilder2.build(), ScreenRotationAnimation.SurfaceRotationAnimationController.createWindowAnimationSpec(screenRotationAnimation.mRotateExitAnimation), new ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(surfaceRotationAnimationController2));
                                            surfaceRotationAnimationController2.startColorAnimation();
                                            screenRotationAnimation.mService.mSurfaceAnimationRunner.continueStartingAnimations();
                                        } finally {
                                        }
                                    }
                                }
                            }
                            if (screenRotationAnimation2.mStarted) {
                                z5 = true;
                                screenRotationAnimation2.mFinishAnimReady = true;
                                this.mTransaction.apply();
                                z3 = false;
                            }
                        }
                        screenRotationAnimation2.kill();
                        displayContent.setRotationAnimation(null);
                        z5 = true;
                        z3 = true;
                    }
                    if (displayContent == null || !displayContent.updateOrientation(false)) {
                        z5 = false;
                    }
                    this.mScreenFrozenLock.release();
                    if (z3 && displayContent != null) {
                        if (zArr[0]) {
                            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, 2233371241933584073L, 0, null, null);
                        }
                        z5 |= displayContent.mDisplayRotation.updateRotationUnchecked(false);
                    }
                    if (z5) {
                        displayContent.sendNewConfiguration();
                    }
                    this.mAtmService.endPowerMode(2);
                    this.mLatencyTracker.onActionEnd(6);
                    Trace.traceEnd(32L);
                    return;
                }
            }
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, 4565793239453546297L, 1887, null, Boolean.valueOf(z), Boolean.valueOf(z2), Long.valueOf(this.mAppsFreezingScreen), Long.valueOf(this.mWindowsFreezingScreen), Boolean.valueOf(this.mClientFreezingScreen), Long.valueOf(i));
            }
        }
    }

    public final void stopFreezingScreen() {
        if (!checkCallingPermission$1("android.permission.FREEZE_SCREEN", "stopFreezingScreen()", true)) {
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

    public final void stopTransitionTrace() {
        this.mTransitionTracer.stopTrace(null);
    }

    public final boolean stopViewServer() {
        ViewServer viewServer;
        Thread thread;
        if (isSystemSecure() || !checkCallingPermission$1("android.permission.DUMP", "stopViewServer", true) || (viewServer = this.mViewServer) == null || (thread = viewServer.mThread) == null) {
            return false;
        }
        thread.interrupt();
        ExecutorService executorService = viewServer.mThreadPool;
        if (executorService != null) {
            try {
                executorService.shutdownNow();
            } catch (SecurityException unused) {
                Slog.w("WindowManager", "Could not stop all view server threads");
            }
        }
        viewServer.mThreadPool = null;
        viewServer.mThread = null;
        try {
            viewServer.mServer.close();
            viewServer.mServer = null;
            return true;
        } catch (IOException unused2) {
            Slog.w("WindowManager", "Could not close the view server");
            return false;
        }
    }

    public final void stopWindowTrace() {
        this.mWindowTracing.stopTrace(null);
    }

    public final void syncInputTransactions(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                try {
                    waitForAnimationsToComplete();
                } catch (InterruptedException e) {
                    Slog.e("WindowManager", "Exception thrown while waiting for window infos to be reported", e);
                }
            }
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionFactory.get();
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowSurfacePlacer windowSurfacePlacer = this.mWindowPlacerLocked;
                    if (windowSurfacePlacer.mTraversalScheduled) {
                        windowSurfacePlacer.performSurfacePlacement(false);
                    }
                    this.mRoot.forAllDisplays(new WindowManagerService$$ExternalSyntheticLambda6(2, transaction));
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            transaction.addWindowInfosReportedListener(new SettingsStore$$ExternalSyntheticLambda0(countDownLatch)).apply();
            countDownLatch.await(5000L, TimeUnit.MILLISECONDS);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final void systemReady() {
        this.mSystemReady = true;
        ((PhoneWindowManager) this.mPolicy).systemReady();
        this.mRoot.forAllDisplayPolicies(new WindowManagerService$$ExternalSyntheticLambda7(1));
        SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotController.mSnapshotPersistQueue;
        if (!snapshotPersistQueue.mStarted) {
            snapshotPersistQueue.mStarted = true;
            snapshotPersistQueue.mPersister.start();
        }
        Handler handler = UiThread.getHandler();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        Objects.requireNonNull(settingsObserver);
        handler.post(new WindowManagerService$$ExternalSyntheticLambda19(3, settingsObserver));
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
        WindowManagerServiceExt windowManagerServiceExt = this.mExt;
        DisplayContent defaultDisplayContentLocked = windowManagerServiceExt.mService.getDefaultDisplayContentLocked();
        if (defaultDisplayContentLocked != null && defaultDisplayContentLocked.mUdcCutoutPolicy != null) {
            defaultDisplayContentLocked.updateBaseDisplayCutout(defaultDisplayContentLocked.mBaseDisplayWidth, defaultDisplayContentLocked.mBaseDisplayHeight);
        }
        if (CoreRune.FW_TSP_STATE_CONTROLLER) {
            TspStateController tspStateController = windowManagerServiceExt.mTspStateController;
            SemInputDeviceManager semInputDeviceManager = (SemInputDeviceManager) tspStateController.mContext.getSystemService("SemInputDeviceManagerService");
            tspStateController.mSemInputDeviceManager = semInputDeviceManager;
            if (semInputDeviceManager == null) {
                Slog.d("TspStateManager", "systemReady: failed to get the service");
            }
        }
    }

    public final ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot(Set set) {
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer;
        ScreenCapture.LayerCaptureArgs layerCaptureArgs;
        if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "requestAssistScreenshot()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(0);
                screenshotHardwareBuffer = null;
                if (displayContent == null) {
                    Slog.i("WindowManager", "Screenshot returning null. No Display for displayId=0");
                    layerCaptureArgs = null;
                } else {
                    layerCaptureArgs = displayContent.getLayerCaptureArgs(set);
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
            Slog.w("WindowManager", "Failed to take screenshot");
        }
        return screenshotHardwareBuffer;
    }

    public final ScreenshotResult takeScreenshotToTargetWindow(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3) {
        return takeScreenshotToTargetWindowFromCapture(i, i2, z, rect, i3, i4, z2, z3, false);
    }

    public final ScreenshotResult takeScreenshotToTargetWindowFromCapture(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2, boolean z3, boolean z4) {
        if (z3) {
            if (!checkCallingPermission$1("com.samsung.android.permission.READ_FRAME_BUFFER_IGNORE_POLICY", "takeScreenshotToTargetWindow()", true)) {
                throw new SecurityException("Only certain apps can request to ignorePolicy");
            }
        } else if (!checkCallingPermission$1("android.permission.READ_FRAME_BUFFER", "takeScreenshotToTargetWindow()", true)) {
            throw new SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        return this.mExt.mScreenshotController.takeScreenshotToTargetWindow(i, i2, z, rect, i3, i4, z2, z3, z4);
    }

    public final void thawDisplayRotation(int i, String str) {
        if (!checkCallingPermission$1("android.permission.SET_ORIENTATION", "thawRotation()", true)) {
            throw new SecurityException("Requires SET_ORIENTATION permission");
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 8988910478484254861L, 1, null, Long.valueOf(getDefaultDisplayRotation()), String.valueOf(str));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        Slog.w("WindowManager", "Trying to thaw rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayRotation displayRotation = displayContent.mDisplayRotation;
                    displayRotation.setUserRotation(0, displayRotation.mUserRotation, str);
                    resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    EventLog.writeEvent(31901, 777, Integer.valueOf(i), Integer.valueOf(Binder.getCallingPid()));
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

    public final void thawRotation(String str) {
        thawDisplayRotation(0, str);
    }

    public final boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) {
        boolean z;
        Objects.requireNonNull(inputTransferToken);
        Objects.requireNonNull(inputTransferToken2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowState = (WindowState) this.mInputToWindowMap.get(inputTransferToken2.getToken());
                    z = false;
                    if (windowState != null) {
                        EmbeddedWindowController embeddedWindowController = this.mEmbeddedWindowController;
                        EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) embeddedWindowController.mWindowsByInputTransferToken.get(inputTransferToken);
                        if (EmbeddedWindowController.isValidTouchGestureParams(windowState, embeddedWindow)) {
                            InputChannel inputChannel = embeddedWindow.mInputChannel;
                            z = embeddedWindowController.mInputManagerService.transferTouchGesture(inputChannel != null ? inputChannel.getToken() : null, windowState.mInputChannelToken);
                        }
                    } else {
                        WindowState windowState2 = (WindowState) this.mInputToWindowMap.get(inputTransferToken.getToken());
                        EmbeddedWindowController embeddedWindowController2 = this.mEmbeddedWindowController;
                        EmbeddedWindowController.EmbeddedWindow embeddedWindow2 = (EmbeddedWindowController.EmbeddedWindow) embeddedWindowController2.mWindowsByInputTransferToken.get(inputTransferToken2);
                        if (EmbeddedWindowController.isValidTouchGestureParams(windowState2, embeddedWindow2)) {
                            IBinder iBinder = windowState2.mInputChannelToken;
                            InputChannel inputChannel2 = embeddedWindow2.mInputChannel;
                            z = embeddedWindowController2.mInputManagerService.transferTouchGesture(iBinder, inputChannel2 != null ? inputChannel2.getToken() : null);
                        }
                    }
                } catch (Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void triggerAnimationFailsafe() {
        this.mH.sendEmptyMessage(60);
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
                if (activityRecord != null && activityRecord.inTransition() && !windowState.mSkipExitAnimation) {
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
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_ANIM, -255991894956556845L, 0, null, str, String.valueOf(windowState));
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

    public final boolean unprivilegedAppCanCreateTokenWith(WindowState windowState, int i, int i2, int i3, IBinder iBinder, String str) {
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_ERROR_enabled;
        if (i3 >= 1 && i3 <= 99) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -7315179333005789167L, 0, "Attempted to add application window with unknown token %s.  Aborting.", String.valueOf(iBinder));
            }
            return false;
        }
        if (i3 == 2011) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -7547709658889961930L, 0, "Attempted to add input method window with unknown token %s.  Aborting.", String.valueOf(iBinder));
            }
            return false;
        }
        if (i3 == 2031) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 3009864422591182484L, 0, "Attempted to add voice interaction window with unknown token %s.  Aborting.", String.valueOf(iBinder));
            }
            return false;
        }
        if (i3 == 2013) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -2639914438438144071L, 0, "Attempted to add wallpaper window with unknown token %s.  Aborting.", String.valueOf(iBinder));
            }
            return false;
        }
        if (i3 == 2035) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, -7529563697886120786L, 0, "Attempted to add QS dialog window with unknown token %s.  Aborting.", String.valueOf(iBinder));
            }
            return false;
        }
        if (i3 == 2032) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 4253401518117961686L, 0, "Attempted to add Accessibility overlay window with unknown token %s.  Aborting.", String.valueOf(iBinder));
            }
            return false;
        }
        if (i2 != 2005 || !doesAddToastWindowRequireToken(str, i, windowState)) {
            return true;
        }
        if (zArr[3]) {
            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 5834230650841873680L, 0, "Attempted to add a toast window with unknown token %s.  Aborting.", String.valueOf(iBinder));
        }
        return false;
    }

    public final void unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) {
        Slog.d("WindowManager", "WindowManagerService.unregisterAuthTouchEventListener()");
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            NandswapManager$$ExternalSyntheticOutline0.m(callingUid, "Unable to verify uid for unregisterAuthTouchEventListener on uid ", "WindowManager");
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                AuthFactorTouchManager authFactorTouchManager = getDefaultDisplayContentLocked().mDisplayPolicy.mExt.mAuthFactorTouchManager;
                if (authFactorTouchManager != null) {
                    authFactorTouchManager.unregisterAuthTouchEventListener(iAuthTouchEventListener);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void unregisterCrossWindowBlurEnabledListener(ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) {
        BlurController blurController = this.mBlurController;
        if (iCrossWindowBlurEnabledListener == null) {
            blurController.getClass();
        } else {
            blurController.mBlurEnabledListeners.unregister(iCrossWindowBlurEnabledListener);
        }
    }

    public final void unregisterDecorViewGestureListener(IDecorViewGestureListener iDecorViewGestureListener, int i) {
        if (!checkCallingPermission$1("android.permission.MONITOR_INPUT", "unregisterSystemGestureExclusionListener()", true)) {
            throw new SecurityException("Requires MONITOR_INPUT permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to unregister DecorView gesture event listenerfor invalid display: " + i);
                }
                displayContent.mDecorViewGestureListener.unregister(iDecorViewGestureListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void unregisterDisplayFoldListener(IDisplayFoldListener iDisplayFoldListener) {
        DisplayFoldController displayFoldController = ((PhoneWindowManager) this.mPolicy).mDisplayFoldController;
        if (displayFoldController != null) {
            displayFoldController.mListeners.unregister(iDisplayFoldListener);
        }
    }

    public final void unregisterDisplayWindowListener(IDisplayWindowListener iDisplayWindowListener) {
        ActivityTaskManagerService.enforceTaskPermission("unregisterDisplayWindowListener");
        this.mDisplayNotificationController.mDisplayListeners.unregister(iDisplayWindowListener);
    }

    public final void unregisterKnoxRemoteScreenCallback(IScreenRecordingCallback iScreenRecordingCallback) {
        unregisterScreenRecordingCallback_enforcePermission();
        this.mKnoxRemoteScreenCallbackController.unregister(iScreenRecordingCallback);
    }

    public final void unregisterOneHandOpWatcher(IOneHandOpWatcher iOneHandOpWatcher) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                if (defaultDisplayContentLocked.getOneHandOpPolicy() != null) {
                    OneHandOpPolicy oneHandOpPolicy = defaultDisplayContentLocked.getOneHandOpPolicy();
                    if (iOneHandOpWatcher != null) {
                        OneHandOpPolicy.OneHandOpMonitor oneHandOpMonitor = oneHandOpPolicy.mOneHandOpMonitor;
                        IOneHandOpWatcher iOneHandOpWatcher2 = oneHandOpMonitor.mWatcher;
                        if (iOneHandOpWatcher2 != null && iOneHandOpWatcher2.asBinder() == iOneHandOpWatcher.asBinder()) {
                            try {
                                oneHandOpMonitor.mWatcher = null;
                                iOneHandOpWatcher.asBinder().unlinkToDeath(oneHandOpMonitor, 0);
                            } catch (IllegalArgumentException unused) {
                            }
                        }
                    } else {
                        oneHandOpPolicy.getClass();
                    }
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void unregisterPointerEventListener(WindowManagerPolicyConstants.PointerEventListener pointerEventListener, int i) {
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

    public final void unregisterScreenRecordingCallback(IScreenRecordingCallback iScreenRecordingCallback) {
        unregisterScreenRecordingCallback_enforcePermission();
        this.mScreenRecordingCallbackController.unregister(iScreenRecordingCallback);
    }

    public final void unregisterSystemGestureExclusionListener(ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to unregister system gesture exclusion event for invalid display: " + i);
                }
                displayContent.mSystemGestureExclusionListeners.unregister(iSystemGestureExclusionListener);
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void unregisterSystemKeyEvent(int i, ComponentName componentName) throws IllegalArgumentException {
        if (!checkCallingPermission$1("com.samsung.android.permission.ACCESS_SYSTEM_KEY_DISPATCHING", "unregisterSystemKeyEvent", true)) {
            throw new SecurityException("Requires ACCESS_SYSTEM_KEY_DISPATCHING permission");
        }
        SystemKeyManager systemKeyManager = this.mExt.mPolicyExt.mSystemKeyPolicy;
        systemKeyManager.getClass();
        Log.v("SystemKeyManager", "unregisterSystemKeyEvent() is called keyCode=" + i + " componentName=" + componentName);
        SystemKeyManager.checkValidRequestedDefaultInfo(i, 0, componentName);
        String flattenToString = componentName.flattenToString();
        synchronized (systemKeyManager) {
            try {
                HashMap hashMap = (HashMap) systemKeyManager.mSystemKeyInfoMap.get(i);
                if (hashMap == null) {
                    return;
                }
                hashMap.remove(flattenToString);
            } finally {
            }
        }
    }

    public final void unregisterTaskFpsCallback(ITaskFpsCallback iTaskFpsCallback) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FPS_COUNTER") != 0) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(Binder.getCallingPid(), "Access denied to process: ", ", must have permission android.permission.ACCESS_FPS_COUNTER"));
        }
        this.mTaskFpsCallbackController.unregisterListener(iTaskFpsCallback);
    }

    public final void unregisterTrustedPresentationListener(final ITrustedPresentationListener iTrustedPresentationListener, final int i) {
        final TrustedPresentationListenerController trustedPresentationListenerController = this.mTrustedPresentationListenerController;
        trustedPresentationListenerController.startHandlerThreadIfNeeded();
        trustedPresentationListenerController.mHandler.post(new Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TrustedPresentationListenerController.AnonymousClass1 anonymousClass1;
                TrustedPresentationListenerController trustedPresentationListenerController2 = TrustedPresentationListenerController.this;
                ITrustedPresentationListener iTrustedPresentationListener2 = iTrustedPresentationListener;
                int i2 = i;
                boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_TPL_enabled;
                if (zArr[0]) {
                    trustedPresentationListenerController2.getClass();
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TPL, 3691097873058247482L, 4, null, String.valueOf(iTrustedPresentationListener2), Long.valueOf(i2));
                }
                TrustedPresentationListenerController.Listeners listeners = trustedPresentationListenerController2.mRegisteredListeners;
                listeners.getClass();
                IBinder asBinder = iTrustedPresentationListener2.asBinder();
                TrustedPresentationListenerController.Listeners.ListenerDeathRecipient listenerDeathRecipient = (TrustedPresentationListenerController.Listeners.ListenerDeathRecipient) listeners.mUniqueListeners.get(asBinder);
                if (listenerDeathRecipient != null) {
                    int i3 = listenerDeathRecipient.mInstances - 1;
                    listenerDeathRecipient.mInstances = i3;
                    if (i3 <= 0) {
                        listenerDeathRecipient.mListenerBinder.unlinkToDeath(listenerDeathRecipient, 0);
                        listeners.mUniqueListeners.remove(asBinder);
                    }
                    listeners.removeListeners(asBinder, Optional.of(Integer.valueOf(i2)));
                } else if (zArr[4]) {
                    ProtoLogImpl_54989576.e(ProtoLogGroup.WM_DEBUG_TPL, 3445530300764535903L, 4, null, String.valueOf(iTrustedPresentationListener2), Long.valueOf(i2));
                }
                if (!listeners.mWindowToListeners.isEmpty() || (anonymousClass1 = trustedPresentationListenerController2.mWindowInfosListener) == null) {
                    return;
                }
                anonymousClass1.unregister();
                trustedPresentationListenerController2.mWindowInfosListener = null;
                trustedPresentationListenerController2.mLastWindowHandles = null;
            }
        });
    }

    public final void unregisterWallpaperVisibilityListener(IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mWallpaperVisibilityListeners.mDisplayListeners.get(i);
                if (remoteCallbackList != null) {
                    remoteCallbackList.unregister(iWallpaperVisibilityListener);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void updateDefaultDisplaySize(DisplayContent displayContent, int i, int i2) {
        if (i == 0 || i2 == 0) {
            return;
        }
        DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
        displayWindowSettings.getClass();
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettings.mSettingsProvider.getOverrideSettings(displayContent.mDisplayInfo);
        if (overrideSettings.mForcedWidth != i || overrideSettings.mForcedHeight != i2) {
            this.mExt.mMultiResolutionController.updateDefaultDisplaySizeDensityChangedReason("applyForcedPropertiesForDefaultDisplay: size=" + i + "x" + i2);
        }
        if (displayContent.mInitialDisplayWidth != i || displayContent.mInitialDisplayHeight != i2) {
            displayContent.mIsSizeForced = true;
        }
        this.mDisplayWindowSettings.setForcedSize(displayContent, i, i2);
    }

    public final void updateDisplayWindowRequestedVisibleTypes(int i, int i2) {
        DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget;
        updateDisplayWindowRequestedVisibleTypes_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null && (remoteInsetsControlTarget = displayContent.mRemoteInsetsControlTarget) != null) {
                        if (remoteInsetsControlTarget.mRequestedVisibleTypes != i2) {
                            remoteInsetsControlTarget.mRequestedVisibleTypes = i2;
                        }
                        displayContent.mInsetsStateController.onRequestedVisibleTypesChanged(remoteInsetsControlTarget);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateFocusedWindowLocked(int r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 785
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.updateFocusedWindowLocked(int, boolean):boolean");
    }

    public final void updateInputChannel(IBinder iBinder, int i, int i2, int i3, SurfaceControl surfaceControl, String str, InputApplicationHandle inputApplicationHandle, int i4, int i5, int i6, int i7, Region region, IBinder iBinder2, int i8, WindowContainerToken windowContainerToken, Region region2) {
        int i9;
        boolean z;
        InputWindowHandle inputWindowHandle = new InputWindowHandle(inputApplicationHandle, i3);
        inputWindowHandle.token = iBinder;
        inputWindowHandle.setWindowToken(iBinder2);
        inputWindowHandle.name = str;
        if ((i5 & 536870912) != 0) {
            z = true;
            i9 = i4;
        } else {
            i9 = i4;
            z = false;
        }
        int sanitizeFlagSlippery = sanitizeFlagSlippery(i9, i, i2, str);
        int sanitizeInputFeatures = sanitizeInputFeatures(i6, i, str, i2, z);
        int i10 = (sanitizeFlagSlippery & 536870936) | 32;
        inputWindowHandle.layoutParamsType = i7;
        inputWindowHandle.layoutParamsFlags = i10;
        int inputConfigFromWindowParams = InputConfigAdapter.getInputConfigFromWindowParams(i7, i10, sanitizeInputFeatures);
        inputWindowHandle.inputConfig = inputConfigFromWindowParams;
        if ((sanitizeFlagSlippery & 8) != 0) {
            inputWindowHandle.inputConfig = inputConfigFromWindowParams | 4;
        }
        boolean z2 = CoreRune.MW_CAPTION_SHELL;
        if (z2) {
            if ((sanitizeFlagSlippery & 262144) != 0) {
                inputWindowHandle.inputConfig |= 512;
            }
            inputWindowHandle.surfaceInset = i8;
        }
        if (CoreRune.MW_FREEFORM_RESIZE_TOUCHABLE_REGION) {
            if (region2 == null) {
                inputWindowHandle.pointerTouchableRegion.setEmpty();
            } else {
                inputWindowHandle.pointerTouchableRegion.set(region2);
            }
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
        inputWindowHandle.setTrustedOverlay(transaction, surfaceControl, z);
        transaction.setInputWindowInfo(surfaceControl, inputWindowHandle);
        transaction.apply();
        transaction.close();
        if (!z2 || windowContainerToken == null) {
            surfaceControl.release();
        }
    }

    public final void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                EmbeddedWindowController.EmbeddedWindow embeddedWindow = (EmbeddedWindowController.EmbeddedWindow) this.mEmbeddedWindowController.mWindows.get(iBinder);
                if (embeddedWindow == null) {
                    Slog.e("WindowManager", "Couldn't find window for provided channelToken.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                String str = embeddedWindow.mName;
                InputApplicationHandle applicationHandle = embeddedWindow.getApplicationHandle();
                embeddedWindow.mIsFocusable = (i2 & 8) == 0;
                resetPriorityAfterLockedSection();
                if (CoreRune.MW_FREEFORM_RESIZE_TOUCHABLE_REGION) {
                    updateInputChannel(iBinder, embeddedWindow.mOwnerUid, embeddedWindow.mOwnerPid, i, surfaceControl, str, applicationHandle, i2, i3, i4, embeddedWindow.mWindowType, region, embeddedWindow.mClient, 0, null, region2);
                } else {
                    updateInputChannel(iBinder, embeddedWindow.mOwnerUid, embeddedWindow.mOwnerPid, i, surfaceControl, str, applicationHandle, i2, i3, i4, embeddedWindow.mWindowType, region, embeddedWindow.mClient, 0, null, null);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void updateNonSystemOverlayWindowsVisibilityIfNeeded(WindowState windowState, boolean z) {
        if (((windowState.mAttrs.privateFlags & 524288) == 0 || !windowState.mSession.mCanHideNonSystemOverlayWindows) && !this.mHidingNonSystemOverlayWindows.contains(windowState)) {
            return;
        }
        boolean z2 = !this.mHidingNonSystemOverlayWindows.isEmpty();
        if (!z || (windowState.mAttrs.privateFlags & 524288) == 0 || !windowState.mSession.mCanHideNonSystemOverlayWindows) {
            this.mHidingNonSystemOverlayWindows.remove(windowState);
        } else if (!this.mHidingNonSystemOverlayWindows.contains(windowState)) {
            this.mHidingNonSystemOverlayWindows.add(windowState);
        }
        boolean z3 = !this.mHidingNonSystemOverlayWindows.isEmpty();
        if (z2 == z3) {
            return;
        }
        this.mRoot.forAllWindows((Consumer) new WindowManagerService$$ExternalSyntheticLambda4(1, z3), false);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public final void updateRotation(boolean z, boolean z2) {
        updateRotationUnchecked(z, z2);
    }

    public final void updateRotationUnchecked(boolean z, boolean z2) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 7261084872394224738L, 15, null, Boolean.valueOf(z), Boolean.valueOf(z2));
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
                        boolean updateRotationUnchecked = displayContent.mDisplayRotation.updateRotationUnchecked(false);
                        Trace.traceEnd(32L);
                        if (updateRotationUnchecked) {
                            TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
                            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(26, displayContent.mDisplayId, 0);
                            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyOnActivityRotation, obtainMessage);
                            obtainMessage.sendToTarget();
                        }
                        if (!updateRotationUnchecked || (!displayContent.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange() && !displayContent.mTransitionController.isCollecting())) {
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
                        this.mWindowPlacerLocked.performSurfacePlacement(false);
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

    public final void updateStaticPrivacyIndicatorBounds(int i, Rect[] rectArr) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    PrivacyIndicatorBounds privacyIndicatorBounds = displayContent.mCurrentPrivacyIndicatorBounds;
                    PrivacyIndicatorBounds updateStaticBounds = privacyIndicatorBounds.updateStaticBounds(rectArr);
                    displayContent.mCurrentPrivacyIndicatorBounds = updateStaticBounds;
                    if (!privacyIndicatorBounds.equals(updateStaticBounds)) {
                        displayContent.updateDisplayFrames(true);
                    }
                } else {
                    Slog.w("WindowManager", "updateStaticPrivacyIndicatorBounds with invalid displayId=" + i);
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final void updateTapExcludeRegion(IWindow iWindow, Region region) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked = windowForClientLocked((Session) null, iWindow, false);
                if (windowForClientLocked == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 1278715281433572858L, 0, "Bad requesting window %s", String.valueOf(iWindow));
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                if (displayContent == null) {
                    throw new IllegalStateException("Trying to update window not attached to any display.");
                }
                if (region != null && !region.isEmpty()) {
                    windowForClientLocked.mTapExcludeRegion.set(region);
                    displayContent.mInputMonitor.updateInputWindowsLw(true);
                    resetPriorityAfterLockedSection();
                }
                windowForClientLocked.mTapExcludeRegion.setEmpty();
                displayContent.mInputMonitor.updateInputWindowsLw(true);
                resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final VerifiedDisplayHash verifyDisplayHash(final DisplayHash displayHash) {
        final DisplayHashController displayHashController = this.mDisplayHashController;
        displayHashController.getClass();
        return (VerifiedDisplayHash) new DisplayHashController.SyncCommand(displayHashController).run(new BiConsumer() { // from class: com.android.server.wm.DisplayHashController$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                DisplayHashController displayHashController2 = DisplayHashController.this;
                DisplayHash displayHash2 = displayHash;
                IDisplayHashingService iDisplayHashingService = (IDisplayHashingService) obj;
                RemoteCallback remoteCallback = (RemoteCallback) obj2;
                displayHashController2.getClass();
                try {
                    iDisplayHashingService.verifyDisplayHash(displayHashController2.mSalt, displayHash2, remoteCallback);
                } catch (RemoteException unused) {
                    Slog.e("WindowManager", "Failed to invoke verifyDisplayHash command");
                }
            }
        }).getParcelable("android.service.displayhash.extra.VERIFIED_DISPLAY_HASH");
    }

    public final boolean viewServerGetFocusedWindow(Socket socket) {
        boolean z = false;
        if (isSystemSecure()) {
            return false;
        }
        WindowState focusedWindow = getFocusedWindow();
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
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
            } catch (Exception unused3) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
        }
        return z;
    }

    public final boolean viewServerListWindows(Socket socket) {
        BufferedWriter bufferedWriter;
        Throwable th;
        boolean z = false;
        if (isSystemSecure()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows((Consumer) new WindowManagerService$$ExternalSyntheticLambda5(arrayList, 2), false);
            } catch (Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        resetPriorityAfterLockedSection();
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), 8192);
            } catch (Exception unused) {
            } catch (Throwable th3) {
                bufferedWriter = null;
                th = th3;
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
            } catch (Throwable th4) {
                th = th4;
                if (bufferedWriter == null) {
                    throw th;
                }
                try {
                    bufferedWriter.close();
                    throw th;
                } catch (IOException unused3) {
                    throw th;
                }
            }
        } catch (IOException unused4) {
        }
        return z;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean viewServerWindowCommand(java.net.Socket r12, java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.viewServerWindowCommand(java.net.Socket, java.lang.String, java.lang.String):boolean");
    }

    public final void waitForAnimationsToComplete() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAnimator.mNotifyWhenNoAnimation = true;
                long j = 5000;
                boolean z = false;
                while (j > 0) {
                    z = !this.mAtmService.mWindowOrganizerController.mTransitionController.isShellTransitionsEnabled() && this.mRoot.forAllActivities(new WindowManagerService$$ExternalSyntheticLambda25(1));
                    if (!this.mAnimator.mAnimationFrameCallbackScheduled && !this.mRoot.isAnimating(5, -1) && !z && !this.mAtmService.mWindowOrganizerController.mTransitionController.inTransition()) {
                        break;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    try {
                        this.mGlobalLock.wait(j);
                    } catch (InterruptedException unused) {
                    }
                    j -= System.currentTimeMillis() - currentTimeMillis;
                }
                this.mAnimator.mNotifyWhenNoAnimation = false;
                WindowContainer animatingContainer = this.mRoot.getAnimatingContainer(5, -1);
                if (this.mAnimator.mAnimationFrameCallbackScheduled || animatingContainer != null || z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Timed out waiting for animations to complete, animatingContainer=");
                    sb.append(animatingContainer);
                    sb.append(" animationType=");
                    sb.append(SurfaceAnimator.animationTypeToString(animatingContainer != null ? animatingContainer.mSurfaceAnimator.mAnimationType : 0));
                    sb.append(" animateStarting=");
                    sb.append(z);
                    Slog.w("WindowManager", sb.toString());
                }
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public final int watchRotation(IRotationWatcher iRotationWatcher, int i) {
        int i2;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new IllegalArgumentException("Trying to register rotation event for invalid display: " + i);
                }
                this.mRotationWatcherController.registerDisplayRotationWatcher(iRotationWatcher, i);
                i2 = displayContent.mDisplayRotation.mRotation;
            } catch (Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return i2;
    }

    public final WindowState windowForClientLocked(Session session, IBinder iBinder, boolean z) {
        WindowState windowState = (WindowState) this.mWindowMap.get(iBinder);
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_ERROR_enabled;
        if (windowState == null) {
            if (z) {
                throw new IllegalArgumentException("Requested window " + iBinder + " does not exist");
            }
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 4117606810523219596L, 0, "Failed looking up window session=%s callers=%s", String.valueOf(session), String.valueOf(Debug.getCallers(3)));
            }
            return null;
        }
        if (session == null || windowState.mSession == session) {
            return windowState;
        }
        if (!z) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_ERROR, 4117606810523219596L, 0, "Failed looking up window session=%s callers=%s", String.valueOf(session), String.valueOf(Debug.getCallers(3)));
            }
            return null;
        }
        throw new IllegalArgumentException("Requested window " + iBinder + " is in session " + windowState.mSession + ", not " + session);
    }

    public final WindowState windowForClientLocked(Session session, IWindow iWindow, boolean z) {
        return windowForClientLocked(session, iWindow.asBinder(), z);
    }
}
