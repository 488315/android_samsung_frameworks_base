package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BackgroundStartPrivileges;
import android.app.Dialog;
import android.app.IActivityClientController;
import android.app.IActivityController;
import android.app.IActivityTaskManager;
import android.app.IAppTask;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.IScreenCaptureObserver;
import android.app.ITaskStackListener;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureUiState;
import android.app.ProfilerInfo;
import android.app.TaskStackListener;
import android.app.WaitResult;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyCache;
import android.app.assist.ActivityId;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.compat.CompatChanges;
import android.app.role.RoleManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IUserManager;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UpdateLock;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.dreams.DreamActivity;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice.VoiceInteractionManagerInternal;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.MutableLong;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import android.view.Display;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.widget.Toast;
import android.window.BackAnimationAdapter;
import android.window.BackNavigationInfo;
import android.window.ITaskOrganizer;
import android.window.IWindowOrganizerController;
import android.window.RemoteTransition;
import android.window.SplashScreenView;
import android.window.TaskSnapshot;
import com.android.internal.app.AppLockPolicy;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.ProcessMap;
import com.android.internal.app.SmRccPolicy;
import com.android.internal.hidden_from_bootclasspath.android.service.controls.flags.Flags;
import com.android.internal.os.TransferPipe;
import com.android.internal.policy.AttributeCache;
import com.android.internal.policy.KeyguardDismissCallback;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.AppTimeTracker;
import com.android.server.am.AssistDataRequester;
import com.android.server.am.PendingIntentController;
import com.android.server.am.PendingIntentRecord;
import com.android.server.baiducarlife.BaiduCarlifeADBConnectUtils;
import com.android.server.firewall.IntentFirewall;
import com.android.server.grammaticalinflection.GrammaticalInflectionService;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.notification.NotificationManagerService;
import com.android.server.pm.ContentDispatcher;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.SpegService;
import com.android.server.pm.UserManagerService;
import com.android.server.policy.PermissionPolicyService;
import com.android.server.remoteappmode.RemoteAppModeService;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.usage.UsageStatsService;
import com.android.server.vr.VrManagerService;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;
import com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda28;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityStarter;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskSupervisor;
import com.android.server.wm.BackNavigationController;
import com.android.server.wm.BackgroundActivityStartController;
import com.android.server.wm.KeyguardController;
import com.android.server.wm.LaunchParamsPersister.PackageListObserver;
import com.android.server.wm.MultiTaskingAppCompatConfiguration;
import com.android.server.wm.MultiTaskingAppCompatController;
import com.android.server.wm.PackageConfigPersister;
import com.android.server.wm.PendingRemoteAnimationRegistry.Entry;
import com.android.server.wm.PersonaActivityHelper;
import com.android.server.wm.RemoteAppController;
import com.android.server.wm.RootWindowContainer;
import com.android.server.wm.SizeCompatPolicyManager;
import com.android.server.wm.Task;
import com.android.server.wm.TaskOrganizerController;
import com.android.server.wm.TaskPersister;
import com.android.server.wm.TransitionController;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.AppJumpBlockTool;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.core.IFoldStarManager;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.multiwindow.IMultiTaskingBinder;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.core.PackageFeatureController;
import com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;
import com.samsung.android.server.util.CompatChangeableAppsService;
import com.samsung.android.wifi.SemWifiManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ActivityTaskManagerService extends IActivityTaskManager.Stub {
    public static Boolean sIsPip2ExperimentEnabled;
    public final int GL_ES_VERSION;
    public ActivityManagerPerformance mAMBooster;
    public int[] mAccessibilityServiceUids;
    public volatile ComponentName mActiveDreamComponent;
    public final MirrorActiveUids mActiveUids;
    public ComponentName mActiveVoiceInteractionServiceComponent;
    public ActivityClientController mActivityClientController;
    public final SparseArray mActivityInterceptorCallbacks;
    public ActivityStartController mActivityStartController;
    public final SparseArray mAllowAppSwitchUids;
    public ActivityManagerInternal mAmInternal;
    public final List mAnrController;
    public final AnonymousClass3 mAppJumpBlockReceiver;
    public AppLockPolicy mAppLockPolicy;
    public AppOpsManager mAppOpsManager;
    public final AppPairController mAppPairController;
    public volatile int mAppSwitchesState;
    public AppWarnings mAppWarnings;
    public final BackNavigationController mBackNavigationController;
    public NotificationManagerService.AnonymousClass2 mBackgroundActivityStartCallback;
    public Optional mCb4Task;
    public final ChangeTransitionController mChangeTransitController;
    public final Map mCompanionAppUidsMap;
    public CompatModePackages mCompatModePackages;
    public int mConfigurationSeq;
    public final ContentDispatcher mContentDispatcher;
    public final Context mContext;
    public IActivityController mController;
    public boolean mControllerIsAMonkey;
    public final AnonymousClass3 mCooldownLevelReceiver;
    public AppTimeTracker mCurAppTimeTracker;
    public volatile int mDemoteTopAppReasons;
    public boolean mDevEnableNonResizableMultiWindow;
    public int mDeviceOwnerUid;
    public final DexCompatController mDexCompatController;
    public final DexController mDexController;
    public final DexDockingController mDexDockingController;
    public final ActivityTaskManagerServiceExt mExt;
    public final int mFactoryTest;
    public boolean mForceResizableActivities;
    public final FreeformController mFreeformController;
    public boolean mGesutreHintOn;
    public int mGlobalAssetsSeq;
    public final WindowManagerGlobalLock mGlobalLock;
    public final WindowManagerGlobalLock mGlobalLockWithoutBoost;
    public GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl mGrammaticalManagerInternal;
    public H mH;
    public boolean mHasCompanionDeviceSetupFeature;
    public boolean mHasHeavyWeightFeature;
    public boolean mHasLeanbackFeature;
    public volatile WindowProcessController mHeavyWeightProcess;
    public volatile WindowProcessController mHomeProcess;
    public final Object mIdsLock;
    public final AnonymousClass3 mIdsReceiver;
    public IntentFirewall mIntentFirewall;
    final ActivityTaskManagerInternal mInternal;
    public IKeyEventListener mKeyEventListener;
    public KeyguardController mKeyguardController;
    public boolean mKeyguardShown;
    public String mLastANRState;
    public ActivityRecord mLastResumedActivity;
    public volatile long mLastStopAppSwitchesTime;
    public int mLayoutReasons;
    public final ClientLifecycleManager mLifecycleManager;
    public LockTaskController mLockTaskController;
    public final ArrayList mMWControllers;
    public final MultiInstanceController mMultiInstanceController;
    public final MultiStarController mMultiStarController;
    public final MultiTaskingAppCompatController mMultiTaskingAppCompatController;
    public final MultiTaskingBinder mMultiTaskingBinder;
    public final MultiTaskingController mMultiTaskingController;
    public final MultiWindowEnableController mMultiWindowEnableController;
    public final MultiWindowFoldController mMultiWindowFoldController;
    public final MultiWindowSupportPolicyController mMwSupportPolicyController;
    public final NaturalSwitchingController mNaturalSwitchingController;
    public final NewDexController mNewDexController;
    public PackageConfigPersister mPackageConfigPersister;
    public final ArrayList mPendingAssistExtras;
    public PendingIntentController mPendingIntentController;
    public PermissionPolicyService.Internal mPermissionPolicyInternal;
    public PersonaActivityHelper mPersonaActivityHelper;
    public PackageManagerInternal mPmInternal;
    public PowerManagerInternal mPowerManagerInternal;
    public int mPowerModeReasons;
    public volatile WindowProcessController mPreviousProcess;
    public long mPreviousProcessVisibleTime;
    public final WindowProcessControllerMap mProcessMap;
    public final ProcessMap mProcessNames;
    public String mProfileApp;
    public Set mProfileOwnerUids;
    public WindowProcessController mProfileProc;
    public ProfilerInfo mProfilerInfo;
    public RecentTasks mRecentTasks;
    public final RemoteAppController mRemoteAppController;
    public int mRespectsActivityMinWidthHeightMultiWindow;
    public volatile boolean mRetainPowerModeAndTopProcessState;
    public RootWindowContainer mRootWindowContainer;
    public IVoiceInteractionSession mRunningVoice;
    public final List mScreenObservers;
    public Set mSetClearIds;
    public boolean mShowDialogs;
    public volatile boolean mShuttingDown;
    public volatile boolean mSleeping;
    public SpegService mSpeg;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public String[] mSupportedSystemLocales;
    public boolean mSupportsExpandedPictureInPicture;
    public boolean mSupportsFreeformWindowManagement;
    public boolean mSupportsMultiDisplay;
    public boolean mSupportsMultiWindow;
    public int mSupportsNonResizableMultiWindow;
    public boolean mSupportsPictureInPicture;
    public boolean mSuppressResizeConfigChanges;
    public ComponentName mSysUiServiceComponent;
    public final ActivityThread mSystemThread;
    public TaskChangeNotificationController mTaskChangeNotificationController;
    public final TaskFragmentOrganizerController mTaskFragmentOrganizerController;
    public final TaskOrganizerController mTaskOrganizerController;
    public ActivityTaskSupervisor mTaskSupervisor;
    public final Configuration mTempConfig;
    public int mThumbnailHeight;
    public int mThumbnailWidth;
    public final UpdateConfigurationResult mTmpUpdateConfigurationResult;
    public String mTopAction;
    public volatile WindowProcessController mTopApp;
    public ComponentName mTopComponent;
    public volatile int mTopProcessState;
    public ActivityRecord mTracedResumedActivity;
    public UriGrantsManagerInternal mUgmInternal;
    public final Context mUiContext;
    public UiHandler mUiHandler;
    public final UpdateLock mUpdateLock;
    public final AnonymousClass2 mUpdateOomAdjRunnable;
    public UsageStatsManagerInternal mUsageStatsInternal;
    public UserManagerService mUserManager;
    public int mViSessionId;
    public final VisibleActivityProcessTracker mVisibleActivityProcessTracker;
    public volatile WindowProcessController mVisibleDozeUiProcess;
    public PowerManager.WakeLock mVoiceWakeLock;
    public int mVr2dDisplayId;
    public VrController mVrController;
    public WallpaperManagerService.LocalService mWallpaperManagerInternal;
    public WindowManagerService mWindowManager;
    public final WindowOrganizerController mWindowOrganizerController;
    public int mSIOPLevel = -1;
    public int mBatteryOverheatLevel = -1;
    public int mOverheatTextId = -1;
    public HashMap mCheckSIOPLevelList = new HashMap();
    public final ArrayList mStartingProcessActivities = new ArrayList();

    /* renamed from: com.android.server.wm.ActivityTaskManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ActivityTaskManagerService this$0;

        public /* synthetic */ AnonymousClass3(int i, ActivityTaskManagerService activityTaskManagerService) {
            this.$r8$classId = i;
            this.this$0 = activityTaskManagerService;
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x012e A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:76:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r17, android.content.Intent r18) {
            /*
                Method dump skipped, instructions count: 642
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskManagerService.AnonymousClass3.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    /* renamed from: com.android.server.wm.ActivityTaskManagerService$6, reason: invalid class name */
    public final class AnonymousClass6 extends KeyguardDismissCallback {
        public final /* synthetic */ Runnable val$enterPipRunnable;
        public final /* synthetic */ Transition val$transition;

        public AnonymousClass6(Transition transition, ActivityTaskManagerService$$ExternalSyntheticLambda22 activityTaskManagerService$$ExternalSyntheticLambda22) {
            this.val$transition = transition;
            this.val$enterPipRunnable = activityTaskManagerService$$ExternalSyntheticLambda22;
        }

        public final void onDismissSucceeded() {
            Transition transition = this.val$transition;
            if (transition == null) {
                ActivityTaskManagerService.this.mH.post(this.val$enterPipRunnable);
                return;
            }
            TransitionController transitionController = ActivityTaskManagerService.this.mWindowOrganizerController.mTransitionController;
            final ActivityTaskManagerService$$ExternalSyntheticLambda22 activityTaskManagerService$$ExternalSyntheticLambda22 = (ActivityTaskManagerService$$ExternalSyntheticLambda22) this.val$enterPipRunnable;
            transitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$6$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z) {
                    Runnable runnable = activityTaskManagerService$$ExternalSyntheticLambda22;
                    ActivityTaskManagerService.AnonymousClass6 anonymousClass6 = ActivityTaskManagerService.AnonymousClass6.this;
                    if (!z) {
                        ActivityTaskManagerService.this.mH.post(runnable);
                    } else {
                        anonymousClass6.getClass();
                        runnable.run();
                    }
                }
            });
        }
    }

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            boolean z = true;
            if (i == 1) {
                AppTimeTracker appTimeTracker = (AppTimeTracker) message.obj;
                Context context = ActivityTaskManagerService.this.mContext;
                appTimeTracker.stop();
                Bundle bundle = new Bundle();
                bundle.putLong("android.activity.usage_time", appTimeTracker.mTotalTime);
                Bundle bundle2 = new Bundle();
                for (int size = appTimeTracker.mPackageTimes.size() - 1; size >= 0; size--) {
                    bundle2.putLong((String) appTimeTracker.mPackageTimes.keyAt(size), ((MutableLong) appTimeTracker.mPackageTimes.valueAt(size)).value);
                }
                bundle.putBundle("android.usage_time_packages", bundle2);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                try {
                    appTimeTracker.mReceiver.send(context, 0, intent);
                    return;
                } catch (PendingIntent.CanceledException unused) {
                    return;
                }
            }
            if (i == 3) {
                WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ActivityTaskManagerService.this.mRetainPowerModeAndTopProcessState = false;
                        ActivityTaskManagerService.this.endPowerMode(4);
                        if (ActivityTaskManagerService.this.mTopApp != null && ActivityTaskManagerService.this.mTopProcessState == 12) {
                            ActivityTaskManagerService.this.mTopApp.updateProcessInfo(false, false, true, false);
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 4) {
                WindowManagerGlobalLock windowManagerGlobalLock2 = ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        if (ActivityTaskManagerService.this.mAppSwitchesState == 0) {
                            ActivityTaskManagerService.this.mAppSwitchesState = 1;
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i == 5) {
                WindowProcessController windowProcessController = (WindowProcessController) message.obj;
                WindowManagerGlobalLock windowManagerGlobalLock3 = ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock3) {
                    try {
                        int i2 = windowProcessController.mAnimatingReasons;
                        windowProcessController.mAnimatingReasons = i2 | 2;
                        if (i2 == 0) {
                            windowProcessController.mAtm.mH.post(new WindowProcessController$$ExternalSyntheticLambda1(windowProcessController, z));
                        }
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            if (i != 6) {
                if (i != 7) {
                    return;
                }
                ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                Context context2 = activityTaskManagerService.mContext;
                Toast.makeText(context2, context2.getString(activityTaskManagerService.mOverheatTextId, (String) message.obj), 0).show();
                return;
            }
            WindowProcessController windowProcessController2 = (WindowProcessController) message.obj;
            WindowManagerGlobalLock windowManagerGlobalLock4 = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock4) {
                try {
                    windowProcessController2.removeAnimatingReason(2);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            Trace.instant(32L, "finishWakefulnessAnimating");
        }
    }

    public final class Lifecycle extends SystemService {
        public final ActivityTaskManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new ActivityTaskManagerService(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [android.os.IBinder, com.android.server.wm.ActivityTaskManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? r1 = this.mService;
            publishBinderService("activity_task", r1);
            LocalServices.addService(ActivityTaskManagerInternal.class, r1.mInternal);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    LaunchParamsPersister launchParamsPersister = this.mService.mTaskSupervisor.mLaunchParamsPersister;
                    launchParamsPersister.mLaunchParamsMap.remove(targetUser.getUserIdentifier());
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocked(SystemService.TargetUser targetUser) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.mTaskSupervisor.onUserUnlocked(targetUser.getUserIdentifier());
                    if (CoreRune.SUPPORT_SMARTMANAGER_CN) {
                        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mService.mExt;
                        activityTaskManagerServiceExt.getClass();
                        Slog.i("ActivityTaskManagerServiceExt", "SmRcc onUserUnlocked loadData");
                        SmRccPolicy smRccPolicy = SmRccPolicy.getInstance(activityTaskManagerServiceExt.mContext);
                        activityTaskManagerServiceExt.mSmRccPolicy = smRccPolicy;
                        smRccPolicy.loadData();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final class LocalService extends ActivityTaskManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean attachApplication(WindowProcessController windowProcessController) {
            boolean attachApplication;
            synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                if (Trace.isTagEnabled(32L)) {
                    Trace.traceBegin(32L, "attachApplication:" + windowProcessController.mName);
                }
                try {
                    attachApplication = ActivityTaskManagerService.this.mRootWindowContainer.attachApplication(windowProcessController);
                } finally {
                    ActivityTaskManagerService.this.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda5(1, this, windowProcessController));
                    Trace.traceEnd(32L);
                }
            }
            return attachApplication;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean canShowErrorDialogs() {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                    z = false;
                    if (activityTaskManagerService.mShowDialogs && !activityTaskManagerService.mSleeping && !ActivityTaskManagerService.this.mShuttingDown && !ActivityTaskManagerService.this.mKeyguardController.isKeyguardOrAodShowing(0)) {
                        ActivityTaskManagerService activityTaskManagerService2 = ActivityTaskManagerService.this;
                        if (!activityTaskManagerService2.getUserManager().hasUserRestriction("no_system_error_dialogs", activityTaskManagerService2.mAmInternal.getCurrentUserId())) {
                            if (UserManager.isDeviceInDemoMode(ActivityTaskManagerService.this.mContext)) {
                                if (!ActivityTaskManagerService.this.mAmInternal.getCurrentUser().isDemo()) {
                                }
                            }
                            z = true;
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void cleanupRecentTasksForUser() {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mRecentTasks.cleanupLocked(-1);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void clearHeavyWeightProcessIfEquals(WindowProcessController windowProcessController) {
            synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                ActivityTaskManagerService.this.clearHeavyWeightProcessIfEquals(windowProcessController);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void clearHomeStack(int i) {
            ActivityTaskManagerServiceExt activityTaskManagerServiceExt = ActivityTaskManagerService.this.mExt;
            String str = null;
            if (i == 0) {
                List roleHoldersAsUser = ((RoleManager) activityTaskManagerServiceExt.mContext.getSystemService("role")).getRoleHoldersAsUser("android.app.role.HOME", Process.myUserHandle());
                if (!roleHoldersAsUser.isEmpty()) {
                    str = (String) roleHoldersAsUser.get(0);
                }
            } else {
                activityTaskManagerServiceExt.getClass();
            }
            if (DesktopModeFeature.DEBUG) {
                Log.d("ActivityTaskManagerServiceExt", "clearHomeStack(), displayId=" + i + ", defaultHomePkgName=" + str);
            }
            WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerServiceExt.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = activityTaskManagerServiceExt.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.forAllTasks(new ActivityTaskManagerServiceExt$$ExternalSyntheticLambda1(activityTaskManagerServiceExt, str), true);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void closeSystemDialogs(String str) {
            ActivityTaskManagerService.enforceNotIsolatedCaller("closeSystemDialogs");
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            if (ActivityTaskManagerService.this.checkCanCloseSystemDialogs(callingPid, callingUid, null)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        if (callingUid >= 10000) {
                            try {
                                WindowProcessController process = ActivityTaskManagerService.this.mProcessMap.getProcess(callingPid);
                                if (!process.mPerceptible) {
                                    Slog.w("ActivityTaskManager", "Ignoring closeSystemDialogs " + str + " from background process " + process);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        ActivityTaskManagerService.this.mWindowManager.closeSystemDialogs(str);
                        RootWindowContainer rootWindowContainer = ActivityTaskManagerService.this.mRootWindowContainer;
                        rootWindowContainer.getClass();
                        rootWindowContainer.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda16(rootWindowContainer, str, 1));
                        WindowManagerService.resetPriorityAfterLockedSection();
                        ActivityTaskManagerService.this.mAmInternal.broadcastCloseSystemDialogs(str);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void dismissSplitScreenMode() {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    TaskDisplayArea defaultTaskDisplayArea = ActivityTaskManagerService.this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
                    if (!defaultTaskDisplayArea.isSplitScreenModeActivated()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    Task task = defaultTaskDisplayArea.mRootMainStageTask;
                    defaultTaskDisplayArea.onStageSplitScreenDismissed(task != null ? task.getTopMostTask() : null, true);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, int i, boolean z, boolean z2, String str2, int i2) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if ("activities".equals(str) || "a".equals(str)) {
                        ActivityTaskManagerService.this.dumpActivitiesLocked(fileDescriptor, printWriter, z, z2, str2, i2);
                    } else if ("lastanr".equals(str)) {
                        ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                        activityTaskManagerService.getClass();
                        printWriter.println("ACTIVITY MANAGER LAST ANR (dumpsys activity lastanr)");
                        String str3 = activityTaskManagerService.mLastANRState;
                        if (str3 == null) {
                            printWriter.println("  <no ANR has occurred since boot>");
                        } else {
                            printWriter.println(str3);
                        }
                    } else if ("lastanr-traces".equals(str)) {
                        ActivityTaskManagerService.this.getClass();
                        ActivityTaskManagerService.dumpLastANRTracesLocked(printWriter);
                    } else if ("starter".equals(str)) {
                        ActivityTaskManagerService activityTaskManagerService2 = ActivityTaskManagerService.this;
                        activityTaskManagerService2.getClass();
                        printWriter.println("ACTIVITY MANAGER STARTER (dumpsys activity starter)");
                        activityTaskManagerService2.mActivityStartController.dump(printWriter, "", str2);
                    } else if ("containers".equals(str)) {
                        ActivityTaskManagerService activityTaskManagerService3 = ActivityTaskManagerService.this;
                        activityTaskManagerService3.getClass();
                        printWriter.println("ACTIVITY MANAGER CONTAINERS (dumpsys activity containers)");
                        activityTaskManagerService3.mRootWindowContainer.dumpChildrenNames(printWriter, "");
                        printWriter.println(" ");
                    } else {
                        if (!"recents".equals(str) && !"r".equals(str)) {
                            if ("top-resumed".equals(str)) {
                                ActivityTaskManagerService activityTaskManagerService4 = ActivityTaskManagerService.this;
                                activityTaskManagerService4.getClass();
                                printWriter.println("ACTIVITY MANAGER TOP-RESUMED (dumpsys activity top-resumed)");
                                ActivityRecord topResumedActivity = activityTaskManagerService4.mRootWindowContainer.getTopResumedActivity();
                                if (topResumedActivity != null) {
                                    topResumedActivity.dump(printWriter, "", true);
                                }
                            } else if ("visible".equals(str)) {
                                ActivityTaskManagerService.this.dumpVisibleActivitiesLocked(i2, printWriter);
                            } else {
                                if (!"multitasking".equals(str) && !"mt".equals(str)) {
                                    if (CoreRune.FW_APPLOCK && ActivityTaskManagerService.this.mAppLockPolicy != null) {
                                        printWriter.println();
                                        if (z) {
                                            printWriter.println("-----------------------------------------------------");
                                        }
                                        ActivityTaskManagerService.this.mAppLockPolicy.dumpAppLockPolicyLocked(fileDescriptor, printWriter);
                                    }
                                }
                                printWriter.println("MULTI TASKING DUMPSYS (dumpsys activity multitasking)");
                                Iterator it = ActivityTaskManagerService.this.mMWControllers.iterator();
                                while (it.hasNext()) {
                                    ((IController) it.next()).dumpLocked(printWriter);
                                }
                            }
                        }
                        RecentTasks recentTasks = ActivityTaskManagerService.this.mRecentTasks;
                        if (recentTasks != null) {
                            recentTasks.dump(printWriter, str2, z);
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x002f, code lost:
        
            r14.println();
            r9 = false;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00b5 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00ce A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00ed A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0171 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0231 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0240  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x02bf A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0016, B:7:0x001f, B:11:0x002f, B:12:0x0036, B:13:0x004c, B:16:0x0054, B:19:0x0060, B:20:0x0064, B:22:0x0079, B:25:0x0081, B:27:0x008b, B:28:0x00a1, B:31:0x00a9, B:34:0x00b5, B:35:0x00b9, B:37:0x00ce, B:40:0x00ed, B:43:0x00f9, B:44:0x010a, B:46:0x0116, B:47:0x0125, B:49:0x012b, B:51:0x0143, B:55:0x014c, B:56:0x0152, B:65:0x0171, B:67:0x01a9, B:68:0x01d9, B:69:0x022b, B:71:0x0231, B:72:0x0236, B:75:0x0242, B:77:0x024c, B:78:0x0257, B:81:0x025f, B:85:0x0276, B:87:0x027c, B:88:0x0282, B:90:0x02b5, B:94:0x02b8, B:97:0x02bf, B:99:0x02c5, B:100:0x02e9, B:101:0x031d), top: B:3:0x0016 }] */
        @Override // com.android.server.wm.ActivityTaskManagerInternal
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean dumpForProcesses(java.io.PrintWriter r14, boolean r15, java.lang.String r16, int r17, boolean r18, boolean r19, int r20) {
            /*
                Method dump skipped, instructions count: 807
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskManagerService.LocalService.dumpForProcesses(java.io.PrintWriter, boolean, java.lang.String, int, boolean, boolean, int):boolean");
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void enableScreenAfterBoot(boolean z) {
            EventLog.writeEvent(3050, SystemClock.uptimeMillis());
            ActivityTaskManagerService.this.mWindowManager.enableScreenAfterBoot();
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                    activityTaskManagerService.mWindowManager.setEventDispatching(z && !activityTaskManagerService.mShuttingDown);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            FgThread.getHandler().post(new ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final ComponentName getActivityName(IBinder iBinder) {
            ComponentName component;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    component = isInRootTaskLocked != null ? isInRootTaskLocked.intent.getComponent() : null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return component;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final List getAppTasks(int i, String str) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) ActivityTaskManagerService.this.getAppTasks(i, str);
            int size = arrayList2.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(new ActivityManager.AppTask(IAppTask.Stub.asInterface((IBinder) arrayList2.get(i2))));
            }
            return arrayList;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final ActivityTaskManagerInternal.ActivityTokens getAttachedNonFinishingActivityForTask(int i, IBinder iBinder) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = ActivityTaskManagerService.this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "getApplicationThreadForTopActivity failed: Requested task not found");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    ArrayList arrayList = new ArrayList();
                    anyTaskForId.forAllActivities(new ActivityTaskManagerService$$ExternalSyntheticLambda27(1, arrayList));
                    if (arrayList.size() <= 0) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (iBinder == null && ((ActivityRecord) arrayList.get(0)).attachedToProcess()) {
                        ActivityRecord activityRecord = (ActivityRecord) arrayList.get(0);
                        ActivityTaskManagerInternal.ActivityTokens activityTokens = new ActivityTaskManagerInternal.ActivityTokens(activityRecord.token, activityRecord.assistToken, activityRecord.app.mThread, activityRecord.shareableActivityToken, activityRecord.getUid());
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return activityTokens;
                    }
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        ActivityRecord activityRecord2 = (ActivityRecord) arrayList.get(i2);
                        if (activityRecord2.shareableActivityToken == iBinder && activityRecord2.attachedToProcess()) {
                            ActivityTaskManagerInternal.ActivityTokens activityTokens2 = new ActivityTaskManagerInternal.ActivityTokens(activityRecord2.token, activityRecord2.assistToken, activityRecord2.app.mThread, activityRecord2.shareableActivityToken, activityRecord2.getUid());
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return activityTokens2;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int getForegroundTaskId(int i) {
            int foregroundTaskId;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    foregroundTaskId = ActivityTaskManagerService.this.mExt.getForegroundTaskId(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return foregroundTaskId;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final LaunchObserverRegistryImpl getLaunchObserverRegistry() {
            LaunchObserverRegistryImpl launchObserverRegistryImpl;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    launchObserverRegistryImpl = ActivityTaskManagerService.this.mTaskSupervisor.mActivityMetricsLogger.mLaunchObserver;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return launchObserverRegistryImpl;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int getTaskToShowPermissionDialogOn(final int i, final String str) {
            int i2;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = ActivityTaskManagerService.this.mRootWindowContainer;
                    ActivityTaskManagerService activityTaskManagerService = rootWindowContainer.mService;
                    if (activityTaskManagerService.mPermissionPolicyInternal == null) {
                        activityTaskManagerService.mPermissionPolicyInternal = (PermissionPolicyService.Internal) LocalServices.getService(PermissionPolicyService.Internal.class);
                    }
                    final PermissionPolicyService.Internal internal = activityTaskManagerService.mPermissionPolicyInternal;
                    i2 = -1;
                    if (internal != null) {
                        final int[] iArr = {-1};
                        rootWindowContainer.forAllLeafTaskFragments(new Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda53
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                PermissionPolicyService.Internal internal2 = PermissionPolicyService.Internal.this;
                                int i3 = i;
                                String str2 = str;
                                int[] iArr2 = iArr;
                                ActivityRecord activity = ((TaskFragment) obj).getActivity(new RootWindowContainer$$ExternalSyntheticLambda12(2, internal2));
                                if (activity == null || !activity.isUid(i3) || !Objects.equals(str2, activity.packageName) || !internal2.shouldShowNotificationDialogOrClearFlags(activity.task.getTaskInfo(), str2, activity.launchedFromPackage, activity.intent, null, activity.toString(), false)) {
                                    return false;
                                }
                                iArr2[0] = activity.task.mTaskId;
                                return true;
                            }
                        });
                        i2 = iArr[0];
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return i2;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int getTopProcessState() {
            if (ActivityTaskManagerService.this.mRetainPowerModeAndTopProcessState) {
                return 2;
            }
            return ActivityTaskManagerService.this.mTopProcessState;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final List getTopVisibleActivities() {
            ArrayList arrayList;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = ActivityTaskManagerService.this.mRootWindowContainer;
                    rootWindowContainer.getClass();
                    arrayList = new ArrayList();
                    rootWindowContainer.forAllRootTasks(new RootWindowContainer$$ExternalSyntheticLambda0(new ArrayList(), rootWindowContainer.getTopDisplayFocusedRootTask(), arrayList));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return arrayList;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean isBaseOfLockedTask(String str) {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    LockTaskController lockTaskController = ActivityTaskManagerService.this.mLockTaskController;
                    z = false;
                    int i = 0;
                    while (true) {
                        if (i >= lockTaskController.mLockTaskModeTasks.size()) {
                            break;
                        }
                        if (str.equals(((Task) lockTaskController.mLockTaskModeTasks.get(i)).getBasePackageName())) {
                            z = true;
                            break;
                        }
                        i++;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean isCallerRecents(int i) {
            return ActivityTaskManagerService.this.mRecentTasks.isCallerRecents(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void minimizeAllTasks(int i, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mMultiTaskingController.minimizeAllTasksLocked(i, z);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean moveTaskToBack(int i, boolean z, Bundle bundle) {
            boolean z2;
            ActivityTaskManagerServiceExt activityTaskManagerServiceExt = ActivityTaskManagerService.this.mExt;
            WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerServiceExt.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = activityTaskManagerServiceExt.mService.mRootWindowContainer.anyTaskForId(i);
                    z2 = false;
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else if (activityTaskManagerServiceExt.mService.mLockTaskController.isLockTaskModeViolation(anyTaskForId, false)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        activityTaskManagerServiceExt.keepAliveActivityLocked(anyTaskForId, z);
                        Task rootTask = anyTaskForId.getRootTask();
                        if (rootTask != null && rootTask.moveTaskToBack(anyTaskForId, bundle)) {
                            z2 = true;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            return z2;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void notifyActiveDreamChanged(ComponentName componentName) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mActiveDreamComponent = componentName;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onCleanUpApplicationRecord(WindowProcessController windowProcessController) {
            synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                try {
                    if (windowProcessController == ActivityTaskManagerService.this.mHomeProcess) {
                        ActivityTaskManagerService.this.mHomeProcess = null;
                    }
                    if (windowProcessController == ActivityTaskManagerService.this.mPreviousProcess) {
                        ActivityTaskManagerService.this.mPreviousProcess = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean onForceStopPackage(String str, boolean z, boolean z2, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = ActivityTaskManagerService.this.mRootWindowContainer;
                    if (rootWindowContainer == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean finishDisabledPackageActivities = rootWindowContainer.finishDisabledPackageActivities(i, str, null, z, z2, true);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return finishDisabledPackageActivities;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onPackageAdded(String str, boolean z) {
            ApplicationInfo applicationInfo;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    CompatModePackages compatModePackages = ActivityTaskManagerService.this.mCompatModePackages;
                    compatModePackages.getClass();
                    boolean z2 = false;
                    try {
                        applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
                    } catch (RemoteException unused) {
                        applicationInfo = null;
                    }
                    if (applicationInfo != null) {
                        CompatibilityInfo compatibilityInfoForPackageLocked = compatModePackages.compatibilityInfoForPackageLocked(applicationInfo);
                        if (!compatibilityInfoForPackageLocked.alwaysSupportsScreen() && !compatibilityInfoForPackageLocked.neverSupportsScreen()) {
                            z2 = true;
                        }
                        if (z && !z2 && compatModePackages.mPackages.containsKey(str)) {
                            compatModePackages.mPackages.remove(str);
                            compatModePackages.scheduleWrite();
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onPackageDataCleared(String str, int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    CompatModePackages compatModePackages = ActivityTaskManagerService.this.mCompatModePackages;
                    if (compatModePackages.mPackages.containsKey(str)) {
                        compatModePackages.mPackages.remove(str);
                        compatModePackages.scheduleWrite();
                    }
                    compatModePackages.mLegacyScreenCompatPackages.delete(str.hashCode());
                    ActivityTaskManagerService.this.mAppWarnings.removePackageAndHideDialogs(i, str);
                    PackageConfigPersister packageConfigPersister = ActivityTaskManagerService.this.mPackageConfigPersister;
                    synchronized (packageConfigPersister.mLock) {
                        packageConfigPersister.removePackage(i, str);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onPackageReplaced(ApplicationInfo applicationInfo) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = ActivityTaskManagerService.this.mRootWindowContainer;
                    if (rootWindowContainer == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    rootWindowContainer.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda8(UserHandle.getUserId(applicationInfo.uid), applicationInfo.packageName, applicationInfo));
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onPackageUninstalled(int i, String str) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mAppWarnings.removePackageAndHideDialogs(i, str);
                    CompatModePackages compatModePackages = ActivityTaskManagerService.this.mCompatModePackages;
                    if (compatModePackages.mPackages.containsKey(str)) {
                        compatModePackages.mPackages.remove(str);
                        compatModePackages.scheduleWrite();
                    }
                    compatModePackages.mLegacyScreenCompatPackages.delete(str.hashCode());
                    PackageConfigPersister packageConfigPersister = ActivityTaskManagerService.this.mPackageConfigPersister;
                    synchronized (packageConfigPersister.mLock) {
                        packageConfigPersister.removePackage(i, str);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onPackagesSuspendedChanged(int i, boolean z, String[] strArr) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mRecentTasks.onPackagesSuspendedChanged(i, z, strArr);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void onUserStopped(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mRecentTasks.unloadUserDataFromMemoryLocked(i);
                    ActivityTaskManagerService.this.mAllowAppSwitchUids.remove(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void preBindApplication(WindowProcessController windowProcessController) {
            synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                ActivityTaskManagerService.this.mTaskSupervisor.mActivityMetricsLogger.notifyBindApplication(windowProcessController.mInfo);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void registerActivityStartInterceptor(int i, ActivityInterceptorCallback activityInterceptorCallback) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ActivityTaskManagerService.this.mActivityInterceptorCallbacks.contains(i)) {
                        throw new IllegalArgumentException("Duplicate id provided: " + i);
                    }
                    if (activityInterceptorCallback == null) {
                        throw new IllegalArgumentException("The passed ActivityInterceptorCallback can not be null");
                    }
                    if (!ActivityInterceptorCallback.isValidOrderId(i)) {
                        throw new IllegalArgumentException("Provided id " + i + " is not in range of valid ids for system services [0,6] nor in range of valid ids for mainline module services [1000,1001]");
                    }
                    ActivityTaskManagerService.this.mActivityInterceptorCallbacks.put(i, activityInterceptorCallback);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void registerRemoteAppControllerCallbacks(RemoteAppModeService.AnonymousClass1 anonymousClass1) {
            RemoteAppController remoteAppController = ActivityTaskManagerService.this.mRemoteAppController;
            synchronized (remoteAppController.mLock) {
                remoteAppController.mListeners.add(anonymousClass1);
                android.util.secutil.Slog.d("RemoteAppController", "registerCallbacks: " + anonymousClass1);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void registerScreenObserver(ActivityTaskManagerInternal.ScreenObserver screenObserver) {
            ActivityTaskManagerService.this.mScreenObservers.add(screenObserver);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void releaseAltTabKeyConsumer() {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    MultiTaskingController multiTaskingController = ActivityTaskManagerService.this.mMultiTaskingController;
                    if (!multiTaskingController.mFocusableTaskIds.isEmpty()) {
                        Slog.d("MultiTaskingController", "releaseAltTabKeyStateLocked:" + multiTaskingController.mFocusableTaskIds);
                        ((LinkedList) multiTaskingController.mFocusableTaskIds).clear();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void removeRecentTasksByPackageName(int i, String str) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RecentTasks recentTasks = ActivityTaskManagerService.this.mRecentTasks;
                    for (int size = recentTasks.mTasks.size() - 1; size >= 0; size--) {
                        Task task = (Task) recentTasks.mTasks.get(size);
                        if (task.mUserId == i && task.getBasePackageName().equals(str)) {
                            recentTasks.mSupervisor.removeTask(task, true, true, "remove-package-task");
                        }
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void resumeTopActivities(boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    if (z) {
                        ActivityTaskManagerService.this.mTaskSupervisor.scheduleIdle();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void scheduleDestroyAllActivities() {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = ActivityTaskManagerService.this.mRootWindowContainer;
                    rootWindowContainer.mDestroyAllActivitiesReason = "always-finish";
                    rootWindowContainer.mService.mH.post(rootWindowContainer.mDestroyAllActivitiesRunnable);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void sendActivityResult(IBinder iBinder, String str, int i, int i2, Intent intent) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null && isInRootTaskLocked.getRootTask() != null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        NeededUriGrants collectGrants = ActivityTaskManagerService.this.collectGrants(intent, isInRootTaskLocked);
                        WindowManagerGlobalLock windowManagerGlobalLock2 = ActivityTaskManagerService.this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                isInRootTaskLocked.sendResult(-1, str, i, i2, intent, new Binder(), collectGrants, false);
                            } finally {
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void setAllowAppSwitches(int i, int i2, String str) {
            if (ActivityTaskManagerService.this.mAmInternal.isUserRunning(i2, 1)) {
                WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ArrayMap arrayMap = (ArrayMap) ActivityTaskManagerService.this.mAllowAppSwitchUids.get(i2);
                        if (arrayMap == null) {
                            if (i < 0) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            } else {
                                arrayMap = new ArrayMap();
                                ActivityTaskManagerService.this.mAllowAppSwitchUids.put(i2, arrayMap);
                            }
                        }
                        if (i < 0) {
                            arrayMap.remove(str);
                        } else {
                            arrayMap.put(str, Integer.valueOf(i));
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean showStrictModeViolationDialog() {
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                    z = (!activityTaskManagerService.mShowDialogs || activityTaskManagerService.mSleeping || ActivityTaskManagerService.this.mShuttingDown) ? false : true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        /* JADX WARN: Finally extract failed */
        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int startActivitiesAsPackage(String str, String str2, int i, Intent[] intentArr, Bundle bundle) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            String[] strArr = new String[intentArr.length];
            long clearCallingIdentity = Binder.clearCallingIdentity();
            int i2 = 0;
            for (int i3 = 0; i3 < intentArr.length; i3++) {
                try {
                    try {
                        strArr[i3] = intentArr[i3].resolveTypeIfNeeded(activityTaskManagerService.mContext.getContentResolver());
                    } catch (RemoteException unused) {
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            try {
                i2 = AppGlobals.getPackageManager().getPackageUid(str, 268435456L, i);
            } catch (RemoteException unused2) {
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int i4 = i2;
            ActivityStartController activityStartController = activityTaskManagerService.mActivityStartController;
            SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(bundle);
            BackgroundStartPrivileges backgroundStartPrivileges = BackgroundStartPrivileges.NONE;
            activityStartController.getClass();
            return activityStartController.startActivities(null, i4, 0, -1, str, str2, intentArr, strArr, null, fromBundle, activityStartController.checkTargetUser(i, Binder.getCallingPid(), "startActivityInPackage", Binder.getCallingUid(), false), "startActivityInPackage", null, backgroundStartPrivileges);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int startActivitiesInPackage(int i, int i2, int i3, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, SafeActivityOptions safeActivityOptions, int i4, PendingIntentRecord pendingIntentRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            activityTaskManagerService.assertPackageMatchesCallingUid(str);
            ActivityStartController activityStartController = activityTaskManagerService.mActivityStartController;
            activityStartController.getClass();
            return activityStartController.startActivities(null, i, i2, i3, str, str2, intentArr, strArr, iBinder, safeActivityOptions, activityStartController.checkTargetUser(i4, Binder.getCallingPid(), "startActivityInPackage", Binder.getCallingUid(), false), "startActivityInPackage", pendingIntentRecord, backgroundStartPrivileges);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, Intent intent, IBinder iBinder, int i, Bundle bundle, int i2) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            return activityTaskManagerService.startActivityAsUser(iApplicationThread, str, str2, intent, intent.resolveTypeIfNeeded(activityTaskManagerService.mContext.getContentResolver()), iBinder, null, 0, i, null, bundle, i2, false);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int startActivityInPackage(int i, int i2, int i3, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i4, SafeActivityOptions safeActivityOptions, int i5, PendingIntentRecord pendingIntentRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            activityTaskManagerService.assertPackageMatchesCallingUid(str);
            return activityTaskManagerService.mActivityStartController.startActivityInPackage(i, i2, i3, str, str2, intent, str3, iBinder, str4, i4, safeActivityOptions, i5, null, "PendingIntentRecord", pendingIntentRecord, backgroundStartPrivileges);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final int startActivityWithScreenshot(int i, Intent intent, int i2, Bundle bundle, String str, int i3) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            int checkTargetUser = activityTaskManagerService.mActivityStartController.checkTargetUser(i3, Binder.getCallingPid(), "startActivityWithScreenshot", Binder.getCallingUid(), false);
            ActivityStarter obtainStarter = activityTaskManagerService.mActivityStartController.obtainStarter(intent, "startActivityWithScreenshot");
            ActivityStarter.Request request = obtainStarter.mRequest;
            request.callingUid = i;
            request.callingPid = i2;
            request.callingPackage = str;
            request.resultTo = null;
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
            if (fromBundle == null) {
                fromBundle = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
            } else if (fromBundle.getPendingIntentBackgroundActivityStartMode() == 0) {
                fromBundle.setPendingIntentBackgroundActivityStartMode(1);
            }
            obtainStarter.mRequest.activityOptions = new SafeActivityOptions(fromBundle);
            int callingUid = Binder.getCallingUid();
            ActivityStarter.Request request2 = obtainStarter.mRequest;
            request2.realCallingUid = callingUid;
            request2.userId = checkTargetUser;
            request2.forcedBalByPiSender = BackgroundStartPrivileges.ALLOW_BAL;
            request2.freezeScreen = true;
            return obtainStarter.execute();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final AppTaskImpl startDreamActivity(int i, int i2, Intent intent) {
            AppTaskImpl appTaskImpl;
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            activityTaskManagerService.getClass();
            ActivityInfo activityInfo = new ActivityInfo();
            activityInfo.theme = R.style.Widget.ActivityChooserView;
            activityInfo.exported = true;
            activityInfo.name = DreamActivity.class.getName();
            activityInfo.enabled = true;
            activityInfo.persistableMode = 1;
            activityInfo.screenOrientation = -1;
            activityInfo.colorMode = 0;
            activityInfo.flags |= 8388640;
            activityInfo.configChanges = -1;
            if (Flags.homePanelDream()) {
                activityInfo.launchMode = 0;
                activityInfo.documentLaunchMode = 2;
            } else {
                activityInfo.resizeMode = 0;
                activityInfo.launchMode = 3;
            }
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchActivityType(5);
            WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowProcessController process = activityTaskManagerService.mProcessMap.getProcess(i2);
                    ApplicationInfo applicationInfo = process.mInfo;
                    activityInfo.packageName = applicationInfo.packageName;
                    activityInfo.applicationInfo = applicationInfo;
                    activityInfo.processName = process.mName;
                    activityInfo.uiOptions = applicationInfo.uiOptions;
                    activityInfo.taskAffinity = "android:" + activityInfo.packageName + "/dream";
                    ActivityRecord[] activityRecordArr = new ActivityRecord[1];
                    ActivityStarter obtainStarter = activityTaskManagerService.mActivityStartController.obtainStarter(intent, "dream");
                    ActivityStarter.Request request = obtainStarter.mRequest;
                    request.callingUid = i;
                    request.callingPid = i2;
                    String str = intent.getPackage();
                    ActivityStarter.Request request2 = obtainStarter.mRequest;
                    request2.callingPackage = str;
                    request2.activityInfo = activityInfo;
                    if (makeBasic.getPendingIntentBackgroundActivityStartMode() == 0) {
                        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                    }
                    SafeActivityOptions safeActivityOptions = new SafeActivityOptions(makeBasic);
                    ActivityStarter.Request request3 = obtainStarter.mRequest;
                    request3.activityOptions = safeActivityOptions;
                    request3.outActivity = activityRecordArr;
                    int callingUid = Binder.getCallingUid();
                    ActivityStarter.Request request4 = obtainStarter.mRequest;
                    request4.realCallingUid = callingUid;
                    request4.forcedBalByPiSender = BackgroundStartPrivileges.ALLOW_BAL;
                    obtainStarter.execute();
                    ActivityRecord activityRecord = activityRecordArr[0];
                    appTaskImpl = activityRecord == null ? null : new AppTaskImpl(activityTaskManagerService, activityRecord.task.mTaskId, i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return appTaskImpl;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final boolean startHomeActivity(int i, String str) {
            boolean startHomeOnDisplay;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    startHomeOnDisplay = ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnDisplay(str, i, 0, false, false);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return startHomeOnDisplay;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void startRemoteActivityAsCaller(Intent intent, RemoteAppController.CallerInfo callerInfo, int i, Bundle bundle) {
            int callingPid = Binder.getCallingPid();
            if (callingPid != Process.myPid()) {
                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(callingPid, "Pid ", " cannot startRemoteActivityAsCaller", "ActivityTaskManager");
                return;
            }
            ActivityStarter obtainStarter = ActivityTaskManagerService.this.mRemoteAppController.mAtmService.mActivityStartController.obtainStarter(intent, "startActivityAsCaller");
            int i2 = callerInfo.launchedFromUid;
            ActivityStarter.Request request = obtainStarter.mRequest;
            request.callingUid = i2;
            request.callingPackage = callerInfo.launchedFromPackage;
            request.callingFeatureId = callerInfo.launchedFromFeatureId;
            request.resolvedType = callerInfo.resolvedType;
            request.requestCode = -1;
            obtainStarter.setActivityOptions(bundle);
            ActivityStarter.Request request2 = obtainStarter.mRequest;
            request2.userId = i;
            request2.ignoreTargetSecurity = false;
            request2.filterCallingUid = callerInfo.isResolver ? 0 : callerInfo.launchedFromUid;
            request2.forcedBalByPiSender = BackgroundStartPrivileges.ALLOW_BAL;
            obtainStarter.execute();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void unregisterRemoteAppControllerCallbacks(RemoteAppModeService.AnonymousClass1 anonymousClass1) {
            RemoteAppController remoteAppController = ActivityTaskManagerService.this.mRemoteAppController;
            synchronized (remoteAppController.mLock) {
                remoteAppController.mListeners.remove(anonymousClass1);
                android.util.secutil.Slog.d("RemoteAppController", "unregisterCallbacks: " + anonymousClass1);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void writeActivitiesToProto(ProtoOutputStream protoOutputStream) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mRootWindowContainer.dumpDebug(protoOutputStream, 1146756268034L, 0);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public final void writeProcessesToProto(ProtoOutputStream protoOutputStream, String str, int i, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (str == null) {
                    try {
                        ActivityTaskManagerService.this.getGlobalConfiguration().dumpDebug(protoOutputStream, 1146756268051L);
                        Task topDisplayFocusedRootTask = ActivityTaskManagerService.this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                        if (topDisplayFocusedRootTask != null) {
                            protoOutputStream.write(1133871366165L, topDisplayFocusedRootTask.mConfigWillChange);
                        }
                        ActivityTaskManagerService.m1054$$Nest$mwriteSleepStateToProto(ActivityTaskManagerService.this, protoOutputStream, i, z);
                        if (ActivityTaskManagerService.this.mRunningVoice != null) {
                            long start = protoOutputStream.start(1146756268060L);
                            protoOutputStream.write(1138166333441L, ActivityTaskManagerService.this.mRunningVoice.toString());
                            ActivityTaskManagerService.this.mVoiceWakeLock.dumpDebug(protoOutputStream, 1146756268034L);
                            protoOutputStream.end(start);
                        }
                        VrController vrController = ActivityTaskManagerService.this.mVrController;
                        vrController.getClass();
                        long start2 = protoOutputStream.start(1146756268061L);
                        ProtoUtils.writeBitWiseFlagsToProtoEnum(protoOutputStream, 2259152797697L, vrController.mVrState, VrController.ORIG_ENUMS, VrController.PROTO_ENUMS);
                        protoOutputStream.write(1120986464258L, vrController.mVrRenderThreadTid);
                        protoOutputStream.end(start2);
                        if (ActivityTaskManagerService.this.mController != null) {
                            long start3 = protoOutputStream.start(1146756268069L);
                            protoOutputStream.write(1138166333441L, ActivityTaskManagerService.this.mController.toString());
                            protoOutputStream.write(1133871366146L, ActivityTaskManagerService.this.mControllerIsAMonkey);
                            protoOutputStream.end(start3);
                        }
                        ActivityTaskManagerService.this.mTaskSupervisor.mGoingToSleepWakeLock.dumpDebug(protoOutputStream, 1146756268079L);
                        ActivityTaskManagerService.this.mTaskSupervisor.mLaunchingActivityWakeLock.dumpDebug(protoOutputStream, 1146756268080L);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                if (ActivityTaskManagerService.this.mHomeProcess != null && (str == null || ActivityTaskManagerService.this.mHomeProcess.containsPackage(str))) {
                    ActivityTaskManagerService.this.mHomeProcess.mListener.dumpDebug(protoOutputStream, 1146756268047L, -1);
                }
                if (ActivityTaskManagerService.this.mPreviousProcess != null && (str == null || ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
                    ActivityTaskManagerService.this.mPreviousProcess.mListener.dumpDebug(protoOutputStream, 1146756268048L, -1);
                    protoOutputStream.write(1112396529681L, ActivityTaskManagerService.this.mPreviousProcessVisibleTime);
                }
                if (ActivityTaskManagerService.this.mHeavyWeightProcess != null && (str == null || ActivityTaskManagerService.this.mHeavyWeightProcess.containsPackage(str))) {
                    ActivityTaskManagerService.this.mHeavyWeightProcess.mListener.dumpDebug(protoOutputStream, 1146756268050L, -1);
                }
                for (Map.Entry entry : ActivityTaskManagerService.this.mCompatModePackages.mPackages.entrySet()) {
                    String str2 = (String) entry.getKey();
                    int intValue = ((Integer) entry.getValue()).intValue();
                    if (str != null && !str.equals(str2)) {
                    }
                    long start4 = protoOutputStream.start(2246267895830L);
                    protoOutputStream.write(1138166333441L, str2);
                    protoOutputStream.write(1120986464258L, intValue);
                    protoOutputStream.end(start4);
                }
                AppTimeTracker appTimeTracker = ActivityTaskManagerService.this.mCurAppTimeTracker;
                if (appTimeTracker != null) {
                    appTimeTracker.dumpDebug(protoOutputStream);
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final class PendingAssistExtras extends Binder implements Runnable {
        public final ActivityRecord activity;
        public final Bundle extras;
        public boolean isHome;
        public final IAssistDataReceiver receiver;
        public final Bundle receiverExtras;
        public final int userHandle;
        public boolean haveResult = false;
        public Bundle result = null;
        public AssistStructure structure = null;
        public AssistContent content = null;
        public final Intent intent = null;
        public final String hint = null;

        public PendingAssistExtras(ActivityRecord activityRecord, Bundle bundle, IAssistDataReceiver iAssistDataReceiver, Bundle bundle2, int i) {
            this.activity = activityRecord;
            this.extras = bundle;
            this.receiver = iAssistDataReceiver;
            this.receiverExtras = bundle2;
            this.userHandle = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            IAssistDataReceiver iAssistDataReceiver;
            Slog.w("ActivityTaskManager", "getAssistContextExtras failed: timeout retrieving from " + this.activity);
            synchronized (this) {
                this.haveResult = true;
                notifyAll();
            }
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    activityTaskManagerService.mPendingAssistExtras.remove(this);
                    iAssistDataReceiver = this.receiver;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (iAssistDataReceiver != null) {
                Bundle bundle = new Bundle();
                bundle.putBundle("receiverExtras", this.receiverExtras);
                try {
                    this.receiver.onHandleAssistData(bundle);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final class SettingObserver extends ContentObserver {
        public final Uri mFontScaleUri;
        public final Uri mFontWeightAdjustmentUri;
        public final Uri mGestureHintUri;
        public final Uri mHideErrorDialogsUri;

        public SettingObserver() {
            super(ActivityTaskManagerService.this.mH);
            Uri uriFor = Settings.System.getUriFor("font_scale");
            this.mFontScaleUri = uriFor;
            Uri uriFor2 = Settings.Global.getUriFor("hide_error_dialogs");
            this.mHideErrorDialogsUri = uriFor2;
            Uri uriFor3 = Settings.Secure.getUriFor("font_weight_adjustment");
            this.mFontWeightAdjustmentUri = uriFor3;
            Uri uriFor4 = Settings.Global.getUriFor("navigation_bar_gesture_hint");
            this.mGestureHintUri = uriFor4;
            ContentResolver contentResolver = ActivityTaskManagerService.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            contentResolver.registerContentObserver(uriFor2, false, this, -1);
            contentResolver.registerContentObserver(uriFor3, false, this, -1);
            contentResolver.registerContentObserver(uriFor4, false, this, -1);
            ActivityTaskManagerService.this.mGesutreHintOn = Settings.Global.getInt(ActivityTaskManagerService.this.mContext.getContentResolver(), "navigation_bar_gesture_hint", 1) != 0;
        }

        public final void onChange(boolean z, Collection collection, int i, int i2) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Uri uri = (Uri) it.next();
                if (this.mFontScaleUri.equals(uri)) {
                    ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                    if (i2 != activityTaskManagerService.mAmInternal.getCurrentUserId()) {
                        continue;
                    } else {
                        float floatForUser = Settings.System.getFloatForUser(activityTaskManagerService.mContext.getContentResolver(), "font_scale", 1.0f, i2);
                        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                if (activityTaskManagerService.getGlobalConfiguration().fontScale == floatForUser) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                } else {
                                    Configuration computeNewConfiguration = activityTaskManagerService.mWindowManager.computeNewConfiguration(0);
                                    computeNewConfiguration.fontScale = floatForUser;
                                    activityTaskManagerService.updatePersistentConfiguration(computeNewConfiguration, i2);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            } finally {
                            }
                        }
                    }
                } else if (this.mHideErrorDialogsUri.equals(uri)) {
                    WindowManagerGlobalLock windowManagerGlobalLock2 = ActivityTaskManagerService.this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            ActivityTaskManagerService activityTaskManagerService2 = ActivityTaskManagerService.this;
                            activityTaskManagerService2.updateShouldShowDialogsLocked(activityTaskManagerService2.getGlobalConfiguration());
                        } finally {
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else if (this.mFontWeightAdjustmentUri.equals(uri)) {
                    ActivityTaskManagerService activityTaskManagerService3 = ActivityTaskManagerService.this;
                    if (i2 != activityTaskManagerService3.mAmInternal.getCurrentUserId()) {
                        continue;
                    } else {
                        int intForUser = Settings.Secure.getIntForUser(activityTaskManagerService3.mContext.getContentResolver(), "font_weight_adjustment", Integer.MAX_VALUE, i2);
                        WindowManagerGlobalLock windowManagerGlobalLock3 = activityTaskManagerService3.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock3) {
                            try {
                                if (activityTaskManagerService3.getGlobalConfiguration().fontWeightAdjustment == intForUser) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                } else {
                                    Configuration computeNewConfiguration2 = activityTaskManagerService3.mWindowManager.computeNewConfiguration(0);
                                    computeNewConfiguration2.fontWeightAdjustment = intForUser;
                                    activityTaskManagerService3.updatePersistentConfiguration(computeNewConfiguration2, i2);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            } finally {
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                    }
                } else if (this.mGestureHintUri.equals(uri)) {
                    ActivityTaskManagerService activityTaskManagerService4 = ActivityTaskManagerService.this;
                    activityTaskManagerService4.mGesutreHintOn = Settings.Global.getInt(activityTaskManagerService4.mContext.getContentResolver(), "navigation_bar_gesture_hint", 1) != 0;
                }
            }
        }
    }

    public final class SleepTokenAcquirerImpl {
        public final SparseArray mSleepTokens = new SparseArray();
        public final String mTag;

        public SleepTokenAcquirerImpl(String str) {
            this.mTag = str;
        }

        public final void acquire(int i, boolean z) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!this.mSleepTokens.contains(i)) {
                        this.mSleepTokens.append(i, ActivityTaskManagerService.this.mRootWindowContainer.createSleepToken(i, this.mTag, z));
                        ActivityTaskManagerService.this.updateSleepIfNeededLocked();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void release(int i) {
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer.SleepToken sleepToken = (RootWindowContainer.SleepToken) this.mSleepTokens.get(i);
                    if (sleepToken != null) {
                        ActivityTaskManagerService.this.mRootWindowContainer.removeSleepToken(sleepToken);
                        this.mSleepTokens.remove(i);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final class UiHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            ((Dialog) message.obj).dismiss();
        }
    }

    public final class UpdateConfigurationResult {
        public int changes;
        public boolean mIsUpdating;
    }

    /* renamed from: -$$Nest$monLocalVoiceInteractionStartedLocked, reason: not valid java name */
    public static void m1053$$Nest$monLocalVoiceInteractionStartedLocked(ActivityTaskManagerService activityTaskManagerService, IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) {
        activityTaskManagerService.getClass();
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            return;
        }
        forTokenLocked.voiceSession = iVoiceInteractionSession;
        forTokenLocked.pendingVoiceInteractionStart = false;
        try {
            forTokenLocked.app.mThread.scheduleLocalVoiceInteractionStarted(iBinder, iVoiceInteractor);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                activityTaskManagerService.startRunningVoiceLocked(iVoiceInteractionSession, forTokenLocked.info.applicationInfo.uid);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (RemoteException unused) {
            forTokenLocked.voiceSession = null;
            forTokenLocked.pendingVoiceInteractionStart = false;
        }
    }

    /* renamed from: -$$Nest$mwriteSleepStateToProto, reason: not valid java name */
    public static void m1054$$Nest$mwriteSleepStateToProto(ActivityTaskManagerService activityTaskManagerService, ProtoOutputStream protoOutputStream, int i, boolean z) {
        activityTaskManagerService.getClass();
        long start = protoOutputStream.start(1146756268059L);
        protoOutputStream.write(1159641169921L, PowerManagerInternal.wakefulnessToProtoEnum(i));
        int size = activityTaskManagerService.mRootWindowContainer.mSleepTokens.size();
        for (int i2 = 0; i2 < size; i2++) {
            protoOutputStream.write(2237677961218L, ((RootWindowContainer.SleepToken) activityTaskManagerService.mRootWindowContainer.mSleepTokens.valueAt(i2)).toString());
        }
        protoOutputStream.write(1133871366147L, activityTaskManagerService.mSleeping);
        protoOutputStream.write(1133871366148L, activityTaskManagerService.mShuttingDown);
        protoOutputStream.write(1133871366149L, z);
        protoOutputStream.end(start);
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.server.wm.ActivityTaskManagerService$2] */
    public ActivityTaskManagerService(Context context) {
        WindowManagerGlobalLock windowManagerGlobalLock = new WindowManagerGlobalLock();
        this.mGlobalLock = windowManagerGlobalLock;
        this.mGlobalLockWithoutBoost = windowManagerGlobalLock;
        this.mActiveUids = new MirrorActiveUids();
        this.mProcessNames = new ProcessMap();
        this.mProcessMap = new WindowProcessControllerMap();
        this.mKeyguardShown = false;
        this.mViSessionId = 1000;
        this.mPendingAssistExtras = new ArrayList();
        this.mCompanionAppUidsMap = new ArrayMap();
        this.mActivityInterceptorCallbacks = new SparseArray();
        this.mTmpUpdateConfigurationResult = new UpdateConfigurationResult();
        this.mIdsLock = new Object();
        this.mSupportedSystemLocales = null;
        this.mTempConfig = new Configuration();
        this.mAppSwitchesState = 2;
        this.mAnrController = new ArrayList();
        this.mController = null;
        this.mControllerIsAMonkey = false;
        this.mTopAction = "android.intent.action.MAIN";
        this.mProfileApp = null;
        this.mProfileProc = null;
        this.mProfilerInfo = null;
        this.mUpdateLock = new UpdateLock("immersive");
        this.mAllowAppSwitchUids = new SparseArray();
        this.mScreenObservers = Collections.synchronizedList(new ArrayList());
        this.mVr2dDisplayId = -1;
        this.mAppLockPolicy = null;
        SemPersonaManager.getKnoxInfo();
        this.mTopProcessState = 2;
        this.mShowDialogs = true;
        this.mAccessibilityServiceUids = new int[0];
        this.mDeviceOwnerUid = -1;
        this.mProfileOwnerUids = new ArraySet();
        this.mMultiTaskingController = new MultiTaskingController(this);
        this.mMultiTaskingBinder = new MultiTaskingBinder(this);
        MultiStarController multiStarController = new MultiStarController();
        multiStarController.mAtm = this;
        this.mMultiStarController = multiStarController;
        ArrayList arrayList = new ArrayList() { // from class: com.android.server.wm.ActivityTaskManagerService.1
            {
                add(ActivityTaskManagerService.this.mMultiTaskingController);
            }
        };
        this.mMWControllers = arrayList;
        this.mUpdateOomAdjRunnable = new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService.2
            @Override // java.lang.Runnable
            public final void run() {
                ActivityTaskManagerService.this.mAmInternal.updateOomAdj(1);
            }
        };
        this.mIdsReceiver = new AnonymousClass3(0, this);
        this.mAppJumpBlockReceiver = new AnonymousClass3(1, this);
        this.mCooldownLevelReceiver = new AnonymousClass3(2, this);
        this.mCb4Task = Optional.empty();
        this.mContext = context;
        this.mFactoryTest = FactoryTest.getMode();
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        this.mSystemThread = currentActivityThread;
        this.mUiContext = currentActivityThread.getSystemUiContext();
        this.mLifecycleManager = new ClientLifecycleManager();
        this.mVisibleActivityProcessTracker = new VisibleActivityProcessTracker(this);
        this.mInternal = new LocalService();
        this.GL_ES_VERSION = SystemProperties.getInt("ro.opengles.version", 0);
        WindowOrganizerController windowOrganizerController = new WindowOrganizerController(this);
        this.mWindowOrganizerController = windowOrganizerController;
        this.mTaskOrganizerController = windowOrganizerController.mTaskOrganizerController;
        this.mTaskFragmentOrganizerController = windowOrganizerController.mTaskFragmentOrganizerController;
        this.mBackNavigationController = new BackNavigationController();
        this.mPersonaActivityHelper = null;
        this.mExt = new ActivityTaskManagerServiceExt(context, this);
        MultiWindowSupportPolicyController multiWindowSupportPolicyController = new MultiWindowSupportPolicyController(this);
        this.mMwSupportPolicyController = multiWindowSupportPolicyController;
        arrayList.add(multiWindowSupportPolicyController);
        MultiWindowEnableController multiWindowEnableController = new MultiWindowEnableController(this);
        this.mMultiWindowEnableController = multiWindowEnableController;
        arrayList.add(multiWindowEnableController);
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ChangeTransitionController changeTransitionController = new ChangeTransitionController(this);
            this.mChangeTransitController = changeTransitionController;
            arrayList.add(changeTransitionController);
        }
        DexController dexController = new DexController(this);
        this.mDexController = dexController;
        arrayList.add(dexController);
        DexCompatController dexCompatController = new DexCompatController(this);
        this.mDexCompatController = dexCompatController;
        arrayList.add(dexCompatController);
        if (CoreRune.MT_NEW_DEX) {
            NewDexController newDexController = new NewDexController(this);
            this.mNewDexController = newDexController;
            arrayList.add(newDexController);
        }
        FreeformController freeformController = new FreeformController(this);
        this.mFreeformController = freeformController;
        arrayList.add(freeformController);
        MultiTaskingAppCompatController multiTaskingAppCompatController = new MultiTaskingAppCompatController(this);
        this.mMultiTaskingAppCompatController = multiTaskingAppCompatController;
        arrayList.add(multiTaskingAppCompatController);
        RemoteAppController remoteAppController = new RemoteAppController(this);
        this.mRemoteAppController = remoteAppController;
        arrayList.add(remoteAppController);
        NaturalSwitchingController naturalSwitchingController = new NaturalSwitchingController(this);
        this.mNaturalSwitchingController = naturalSwitchingController;
        arrayList.add(naturalSwitchingController);
        DexDockingController dexDockingController = new DexDockingController(this);
        this.mDexDockingController = dexDockingController;
        arrayList.add(dexDockingController);
        MultiInstanceController multiInstanceController = new MultiInstanceController(this);
        this.mMultiInstanceController = multiInstanceController;
        arrayList.add(multiInstanceController);
        AppPairController appPairController = new AppPairController(this);
        this.mAppPairController = appPairController;
        arrayList.add(appPairController);
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            MultiWindowFoldController multiWindowFoldController = new MultiWindowFoldController(this);
            this.mMultiWindowFoldController = multiWindowFoldController;
            arrayList.add(multiWindowFoldController);
        }
        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                activityTaskManagerService.getClass();
                Intent intent = new Intent();
                intent.setData((Uri) obj2);
                intent.setFlags(1);
                ((UriGrantsManagerService.LocalService) activityTaskManagerService.mUgmInternal).grantUriPermissionUncheckedFromIntent(((UriGrantsManagerService.LocalService) activityTaskManagerService.mUgmInternal).internalCheckGrantUriPermissionFromIntent(intent, ((Integer) obj).intValue(), "com.google.android.googlequicksearchbox", activityTaskManagerService.mAmInternal.getCurrentUserId(), null), null);
            }
        };
        ContentDispatcher contentDispatcher = new ContentDispatcher();
        ArrayMap arrayMap = new ArrayMap();
        contentDispatcher.mPdfInfos = arrayMap;
        contentDispatcher.mContext = context;
        contentDispatcher.mHandler = new Handler(DisplayThread.get().getLooper());
        contentDispatcher.mGrantUriPermission = biConsumer;
        arrayMap.clear();
        this.mContentDispatcher = contentDispatcher;
    }

    public static int checkCallingPermission(String str) {
        return ActivityManagerService.checkComponentPermission(Binder.getCallingPid(), Binder.getCallingUid(), str, 0, -1, true);
    }

    public static void dumpLastANRTracesLocked(PrintWriter printWriter) {
        printWriter.println("ACTIVITY MANAGER LAST ANR TRACES (dumpsys activity lastanr-traces)");
        File[] listFiles = new File("/data/anr").listFiles();
        if (ArrayUtils.isEmpty(listFiles)) {
            printWriter.println("  <no ANR has occurred since boot>");
            return;
        }
        File file = null;
        for (File file2 : listFiles) {
            if (file == null || file.lastModified() < file2.lastModified()) {
                file = file2;
            }
        }
        printWriter.print("File: ");
        printWriter.print(file.getName());
        printWriter.println();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return;
                    }
                    printWriter.println(readLine);
                } finally {
                }
            }
        } catch (IOException e) {
            printWriter.print("Unable to read: ");
            printWriter.print(e);
            printWriter.println();
        }
    }

    public static void enforceNotIsolatedCaller(String str) {
        if (UserHandle.isIsolated(Binder.getCallingUid())) {
            throw new SecurityException("Isolated process not allowed to call ".concat(str));
        }
    }

    public static void enforceTaskPermission(String str) {
        if (checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS") == 0) {
            return;
        }
        if (checkCallingPermission("android.permission.MANAGE_ACTIVITY_STACKS") != 0) {
            throw new SecurityException(ActivityManagerService$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("Permission Denial: ", str, " from pid="), ", uid=", " requires android.permission.MANAGE_ACTIVITY_TASKS", "ActivityTaskManager"));
        }
        Slog.w("ActivityTaskManager", "MANAGE_ACTIVITY_STACKS is deprecated, please use alternative permission: MANAGE_ACTIVITY_TASKS");
    }

    public static boolean isForceStopDisabled(String str, int i, String str2, String str3, String str4, boolean z) {
        try {
            IApplicationPolicy asInterface = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
            if (str == null || asInterface == null) {
                return false;
            }
            return asInterface.isApplicationForceStopDisabled(str, i, str2, str3, str4, z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public static boolean isPip2ExperimentEnabled() {
        if (sIsPip2ExperimentEnabled == null) {
            FeatureInfo featureInfo = (FeatureInfo) SystemConfig.getInstance().mAvailableFeatures.get("org.chromium.arc");
            FeatureInfo featureInfo2 = (FeatureInfo) SystemConfig.getInstance().mAvailableFeatures.get("android.software.leanback");
            if (featureInfo != null) {
                int i = featureInfo.version;
            }
            if (featureInfo2 != null) {
                int i2 = featureInfo2.version;
            }
            boolean z = false;
            if (SystemProperties.getBoolean("persist.wm_shell.pip2", false)) {
                z = true;
            } else {
                com.android.wm.shell.Flags.enablePip2Implementation();
            }
            sIsPip2ExperimentEnabled = Boolean.valueOf(z);
        }
        return sIsPip2ExperimentEnabled.booleanValue();
    }

    public static void logAndRethrowRuntimeExceptionOnTransact(RuntimeException runtimeException, String str) {
        if (runtimeException instanceof SecurityException) {
            throw runtimeException;
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " onTransact aborts UID:");
        m.append(Binder.getCallingUid());
        m.append(" PID:");
        m.append(Binder.getCallingPid());
        Slog.w("ActivityTaskManager", m.toString(), runtimeException);
        throw runtimeException;
    }

    public static void printDisplayInfoAndNewLine(PrintWriter printWriter, ActivityRecord activityRecord) {
        printWriter.print(" displayId=");
        DisplayContent displayContent = activityRecord.getDisplayContent();
        if (displayContent == null) {
            printWriter.println("N/A");
            return;
        }
        Display display = displayContent.mDisplay;
        printWriter.print(display.getDisplayId());
        printWriter.print("(type=");
        printWriter.print(Display.typeToString(display.getType()));
        printWriter.println(")");
    }

    public final int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) {
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        throw new IllegalArgumentException("Activity does not exist; token=" + iBinder);
                    }
                    ComponentName component = intent.getComponent();
                    if (component == null) {
                        throw new IllegalArgumentException("Intent " + intent + " must specify explicit component");
                    }
                    if (bitmap.getWidth() != this.mThumbnailWidth || bitmap.getHeight() != this.mThumbnailHeight) {
                        throw new IllegalArgumentException("Bad thumbnail size: got " + bitmap.getWidth() + "x" + bitmap.getHeight() + ", require " + this.mThumbnailWidth + "x" + this.mThumbnailHeight);
                    }
                    if (intent.getSelector() != null) {
                        intent.setSelector(null);
                    }
                    if (intent.getSourceBounds() != null) {
                        intent.setSourceBounds(null);
                    }
                    if ((intent.getFlags() & 524288) != 0 && (intent.getFlags() & 8192) == 0) {
                        intent.addFlags(8192);
                    }
                    ActivityInfo activityInfo = AppGlobals.getPackageManager().getActivityInfo(component, 1024L, UserHandle.getUserId(callingUid));
                    if (activityInfo != null && activityInfo.applicationInfo.uid == callingUid) {
                        Task rootTask = isInRootTaskLocked.getRootTask();
                        Task.Builder builder = new Task.Builder(this);
                        builder.mWindowingMode = rootTask.getWindowingMode();
                        builder.mActivityType = rootTask.getActivityType();
                        builder.mActivityInfo = activityInfo;
                        builder.mIntent = intent;
                        builder.mTaskId = rootTask.getDisplayArea().getNextRootTaskId();
                        Task build = builder.build();
                        RecentTasks recentTasks = this.mRecentTasks;
                        if (recentTasks.findRemoveIndexForAddTask(build) != -1) {
                            rootTask.removeChild(build, "addAppTask");
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return -1;
                        }
                        recentTasks.add(build);
                        build.mTaskDescription.copyFrom(taskDescription);
                        int i = build.mTaskId;
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return i;
                    }
                    StringBuilder sb = new StringBuilder("Can't add task for another application: target uid=");
                    sb.append(activityInfo == null ? -1 : activityInfo.applicationInfo.uid);
                    sb.append(", calling uid=");
                    sb.append(callingUid);
                    Slog.e("ActivityTaskManager", sb.toString());
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void adjustConfigurationForDexIfNeeded(Configuration configuration, WindowProcessController windowProcessController) {
        int i;
        DexController dexController = this.mDexController;
        dexController.getClass();
        if (windowProcessController.mAdjustBindAppToDexConfig) {
            windowProcessController.mAdjustBindAppToDexConfig = false;
            i = 2;
        } else {
            i = -1;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = dexController.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (i == 2) {
                try {
                    DisplayContent displayContent = dexController.mAtm.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent != null) {
                        configuration.setTo(displayContent.getConfiguration());
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mAppWarnings.mAlwaysShowUnsupportedCompileSdkWarningActivities.add(componentName);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void applyUpdateVrModeLocked(ActivityRecord activityRecord) {
        if (activityRecord.requestedVrComponent != null && activityRecord.getDisplayId() != 0) {
            Slog.i("ActivityTaskManager", "Moving " + activityRecord.shortComponentName + " from display " + activityRecord.getDisplayId() + " to main display for VR");
            RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
            Task task = activityRecord.task;
            rootWindowContainer.moveRootTaskToDisplay(task != null ? task.getRootTask().mTaskId : -1, 0);
        }
        this.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda5(0, this, activityRecord));
    }

    public final void assertPackageMatchesCallingUid(String str) {
        boolean z;
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000) {
            z = true;
        } else {
            z = ((PackageManagerService.PackageManagerInternalImpl) this.mPmInternal).isSameApp(callingUid, UserHandle.getUserId(callingUid), 0L, str);
        }
        if (z) {
            return;
        }
        String str2 = "Permission Denial: package=" + str + " does not belong to uid=" + callingUid;
        Slog.w("ActivityTaskManager", str2);
        throw new SecurityException(str2);
    }

    public final boolean canCloseSystemDialogs(int i, int i2) {
        if (ActivityManagerService.checkComponentPermission(i, i2, "android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS", 0, -1, true) == 0) {
            return true;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ArraySet arraySet = (ArraySet) ((HashMap) this.mProcessMap.mUidMap).get(Integer.valueOf(i2));
                if (arraySet != null) {
                    int size = arraySet.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        WindowProcessController windowProcessController = (WindowProcessController) arraySet.valueAt(i3);
                        int i4 = windowProcessController.mInstrumentationSourceUid;
                        if (windowProcessController.mInstrumenting && i4 != -1 && ActivityManagerService.checkComponentPermission(-1, i4, "android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS", 0, -1, true) == 0) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                        if (windowProcessController.canCloseSystemDialogsByToken()) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    }
                }
                if (!CompatChanges.isChangeEnabled(174664365L, i2)) {
                    RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
                    rootWindowContainer.getClass();
                    if (rootWindowContainer.forAllWindows((ToBooleanFunction) new RootWindowContainer$$ExternalSyntheticLambda23(i2, new boolean[]{false}), true)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    if (ArrayUtils.contains(this.mAccessibilityServiceUids, i2)) {
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

    public final void cancelRecentsAnimation(boolean z) {
        enforceTaskPermission("cancelRecentsAnimation()");
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService windowManagerService = this.mWindowManager;
                    int i = z ? 2 : 0;
                    String str = "cancelRecentsAnimation/uid=" + callingUid;
                    RecentsAnimationController recentsAnimationController = windowManagerService.mRecentsAnimationController;
                    if (recentsAnimationController != null) {
                        recentsAnimationController.cancelAnimation(i, str, false);
                    }
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

    public final void cancelTaskWindowTransition(int i) {
        enforceTaskPermission("cancelTaskWindowTransition()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "cancelTaskWindowTransition: taskId=" + i + " not found");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    for (int size = anyTaskForId.mChildren.size() - 1; size >= 0; size--) {
                        ((WindowContainer) anyTaskForId.mChildren.get(size)).cancelAnimation();
                    }
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

    public final boolean checkCanCloseSystemDialogs(int i, int i2, String str) {
        WindowProcessController process;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                process = this.mProcessMap.getProcess(i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (str == null && process != null) {
            str = process.mInfo.packageName;
        }
        String m = DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "(pid=", ", uid=", ")");
        if (str != null) {
            m = AnyMotionDetector$$ExternalSyntheticOutline0.m(str, " ", m);
        }
        if (canCloseSystemDialogs(i, i2)) {
            return true;
        }
        if (CompatChanges.isChangeEnabled(174664365L, i2)) {
            throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Permission Denial: android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from ", m, " requires android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS."));
        }
        if (!CompatChanges.isChangeEnabled(174664120L, i2)) {
            PinnerService$$ExternalSyntheticOutline0.m("android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from ", m, " will require android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS in future builds.", "ActivityTaskManager");
            return true;
        }
        Slog.e("ActivityTaskManager", "Permission Denial: android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from " + m + " requires android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS, dropping broadcast.");
        return false;
    }

    public final void clearAppLockedUnLockedApp() {
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mExt;
        activityTaskManagerServiceExt.getClass();
        int callingUid = Binder.getCallingUid();
        if (!ActivityTaskManagerServiceExt.isSystemUid(callingUid)) {
            throw new SecurityException(NandswapManager$$ExternalSyntheticOutline0.m(callingUid, " cannot call clearAppLockedUnLockedApp()"));
        }
        AppLockPolicy appLockPolicy = activityTaskManagerServiceExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            appLockPolicy.clearAppLockedUnLockedApp();
        }
    }

    public final void clearHeavyWeightProcessIfEquals(WindowProcessController windowProcessController) {
        if (this.mHeavyWeightProcess == null || this.mHeavyWeightProcess != windowProcessController) {
            return;
        }
        this.mHeavyWeightProcess = null;
        this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda0(2), this, Integer.valueOf(windowProcessController.mUserId)));
    }

    public final void clearLaunchParamsForPackages(List list) {
        enforceTaskPermission("clearLaunchParamsForPackages");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    this.mTaskSupervisor.mLaunchParamsPersister.removeRecordForPackage((String) list.get(i));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean clearRecentTasks(int i) {
        int callingPid = Binder.getCallingPid();
        if (callingPid != Process.myPid() && !SemPersonaManager.isContainerService(this.mContext, callingPid)) {
            throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(callingPid, "Pid ", " cannot clear recent tasks!"));
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRecentTasks.clearRecentTasksLocked(i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return true;
    }

    public final NeededUriGrants collectGrants(Intent intent, ActivityRecord activityRecord) {
        if (activityRecord == null) {
            return null;
        }
        return ((UriGrantsManagerService.LocalService) this.mUgmInternal).internalCheckGrantUriPermissionFromIntent(intent, Binder.getCallingUid(), activityRecord.packageName, activityRecord.mUserId, null);
    }

    public final void continueWindowLayout() {
        WindowSurfacePlacer windowSurfacePlacer = this.mWindowManager.mWindowPlacerLocked;
        boolean z = this.mLayoutReasons != 0;
        int i = windowSurfacePlacer.mDeferDepth - 1;
        windowSurfacePlacer.mDeferDepth = i;
        if (i <= 0 && (z || windowSurfacePlacer.mDeferredRequests > 0)) {
            windowSurfacePlacer.performSurfacePlacement(false);
            windowSurfacePlacer.mDeferredRequests = 0;
        }
        ClientLifecycleManager clientLifecycleManager = this.mLifecycleManager;
        if (clientLifecycleManager.shouldDispatchPendingTransactionsImmediately()) {
            clientLifecycleManager.dispatchPendingTransactions();
        }
    }

    public PersonaActivityHelper createPersonaActivityHelper() {
        return new PersonaActivityHelper(this, this.mH.getLooper());
    }

    public final void deferWindowLayout() {
        WindowSurfacePlacer windowSurfacePlacer = this.mWindowManager.mWindowPlacerLocked;
        int i = windowSurfacePlacer.mDeferDepth;
        if (i <= 0) {
            this.mLayoutReasons = 0;
        }
        windowSurfacePlacer.mDeferDepth = i + 1;
    }

    public final void detachNavigationBarFromApp(IBinder iBinder) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "detachNavigationBarFromApp");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    TransitionController transitionController = this.mWindowOrganizerController.mTransitionController;
                    transitionController.getClass();
                    Transition fromBinder = Transition.fromBinder(iBinder);
                    if (fromBinder != null && transitionController.mPlayingTransitions.contains(fromBinder)) {
                        fromBinder.legacyRestoreNavigationBarFromApp();
                    }
                    Slog.e("TransitionController", "Transition isn't playing: " + iBinder);
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

    public final void dumpActivitiesLocked(final FileDescriptor fileDescriptor, final PrintWriter printWriter, final boolean z, final boolean z2, final String str, int i) {
        boolean z3;
        boolean z4;
        int i2;
        boolean[] zArr;
        int i3;
        int i4 = 0;
        int i5 = -1;
        printWriter.println("ACTIVITY MANAGER ACTIVITIES (dumpsys activity activities)");
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        rootWindowContainer.getClass();
        final boolean[] zArr2 = new boolean[1];
        zArr2[0] = false;
        boolean[] zArr3 = {false};
        int childCount = rootWindowContainer.getChildCount() - 1;
        while (childCount >= 0) {
            DisplayContent displayContent = (DisplayContent) rootWindowContainer.getChildAt(childCount);
            if (zArr2[i4]) {
                printWriter.println();
            }
            if (i == i5 || displayContent.mDisplayId == i) {
                printWriter.print("Display #");
                printWriter.print(displayContent.mDisplayId);
                printWriter.println(" (activities from top to bottom):");
                final boolean[] zArr4 = zArr3;
                i2 = childCount;
                zArr = zArr3;
                displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda30
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean[] zArr5 = zArr4;
                        PrintWriter printWriter2 = printWriter;
                        FileDescriptor fileDescriptor2 = fileDescriptor;
                        boolean z5 = z;
                        boolean z6 = z2;
                        String str2 = str;
                        boolean[] zArr6 = zArr2;
                        Task task = (Task) obj;
                        if (zArr5[0]) {
                            printWriter2.println();
                        }
                        boolean dump = task.dump("  ", fileDescriptor2, printWriter2, z5, z6, str2, null);
                        zArr5[0] = dump;
                        zArr6[0] = zArr6[0] | dump;
                    }
                });
                displayContent.forAllTaskDisplayAreas(new RootWindowContainer$$ExternalSyntheticLambda29(zArr2, printWriter, str, zArr));
                i3 = -1;
            } else {
                i2 = childCount;
                i3 = i5;
                zArr = zArr3;
            }
            childCount = i2 - 1;
            zArr3 = zArr;
            i4 = 0;
            i5 = i3;
        }
        boolean z5 = zArr2[i4];
        boolean z6 = !z;
        ActivityTaskSupervisor.dumpHistoryList(fileDescriptor, printWriter, rootWindowContainer.mTaskSupervisor.mFinishingActivities, "Fin", z6, str, new RootWindowContainer$$ExternalSyntheticLambda32(i4, printWriter));
        zArr2[i4] = z5;
        ActivityTaskSupervisor.dumpHistoryList(fileDescriptor, printWriter, rootWindowContainer.mTaskSupervisor.mStoppingActivities, "Stop", z6, str, new RootWindowContainer$$ExternalSyntheticLambda32(1, printWriter));
        zArr2[0] = z5;
        if (ActivityTaskSupervisor.printThisActivity(printWriter, this.mRootWindowContainer.getTopResumedActivity(), str, i, z5, "  ResumedActivity: ", null)) {
            z5 = false;
            z3 = true;
        } else {
            z3 = z5;
        }
        if (str == null) {
            if (z5) {
                printWriter.println();
            }
            ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
            activityTaskSupervisor.getClass();
            printWriter.println();
            printWriter.println("ActivityTaskSupervisor state:");
            activityTaskSupervisor.mRootWindowContainer.dump(printWriter, "  ", true);
            activityTaskSupervisor.mKeyguardController.dump(printWriter);
            activityTaskSupervisor.mService.mLockTaskController.dump(printWriter);
            printWriter.print("  ");
            printWriter.println("mCurTaskIdForUser=" + activityTaskSupervisor.mCurTaskIdForUser);
            printWriter.println("  mUserRootTaskInFront=" + activityTaskSupervisor.mRootWindowContainer.mUserRootTaskInFront);
            printWriter.println("  mVisibilityTransactionDepth=" + activityTaskSupervisor.mVisibilityTransactionDepth);
            printWriter.print("  ");
            printWriter.print("isHomeRecentsComponent=");
            printWriter.println(activityTaskSupervisor.mRecentTasks.isRecentsComponentHomeActivity(activityTaskSupervisor.mRootWindowContainer.mCurrentUser));
            if (!activityTaskSupervisor.mWaitingActivityLaunched.isEmpty()) {
                printWriter.println("  mWaitingActivityLaunched=");
                for (int size = activityTaskSupervisor.mWaitingActivityLaunched.size() - 1; size >= 0; size--) {
                    ActivityTaskSupervisor.WaitInfo waitInfo = (ActivityTaskSupervisor.WaitInfo) activityTaskSupervisor.mWaitingActivityLaunched.get(size);
                    waitInfo.getClass();
                    printWriter.println("    WaitInfo:");
                    printWriter.println("      mTargetComponent=" + waitInfo.mTargetComponent);
                    printWriter.println("      mResult=");
                    waitInfo.mResult.dump(printWriter, "        ");
                }
            }
            printWriter.println("  mNoHistoryActivities=" + activityTaskSupervisor.mNoHistoryActivities);
            printWriter.println();
            TaskOrganizerController taskOrganizerController = this.mTaskOrganizerController;
            taskOrganizerController.getClass();
            printWriter.print("  ");
            printWriter.println("TaskOrganizerController:");
            ITaskOrganizer iTaskOrganizer = (ITaskOrganizer) taskOrganizerController.mTaskOrganizers.peekLast();
            Iterator it = taskOrganizerController.mTaskOrganizers.iterator();
            while (it.hasNext()) {
                ITaskOrganizer iTaskOrganizer2 = (ITaskOrganizer) it.next();
                TaskOrganizerController.TaskOrganizerState taskOrganizerState = (TaskOrganizerController.TaskOrganizerState) taskOrganizerController.mTaskOrganizerStates.get(iTaskOrganizer2.asBinder());
                ArrayList arrayList = taskOrganizerState.mOrganizedTasks;
                printWriter.print("      ");
                printWriter.print(taskOrganizerState.mOrganizer.mTaskOrganizer + " uid=" + taskOrganizerState.mUid);
                if (iTaskOrganizer == iTaskOrganizer2) {
                    printWriter.print(" (active)");
                }
                printWriter.println(':');
                for (int i6 = 0; i6 < arrayList.size(); i6++) {
                    Task task = (Task) arrayList.get(i6);
                    printWriter.println("        (" + WindowConfiguration.windowingModeToString(task.getWindowingMode()) + ") " + task);
                }
            }
            printWriter.println();
            VisibleActivityProcessTracker visibleActivityProcessTracker = this.mVisibleActivityProcessTracker;
            visibleActivityProcessTracker.getClass();
            printWriter.print("  VisibleActivityProcess:[");
            synchronized (visibleActivityProcessTracker.mProcMap) {
                try {
                    for (int size2 = visibleActivityProcessTracker.mProcMap.size() - 1; size2 >= 0; size2 += -1) {
                        printWriter.print(" " + visibleActivityProcessTracker.mProcMap.keyAt(size2));
                    }
                } finally {
                }
            }
            printWriter.println("]");
            MirrorActiveUids mirrorActiveUids = this.mActiveUids;
            synchronized (mirrorActiveUids) {
                try {
                    printWriter.print("  NumNonAppVisibleWindowUidMap:[");
                    for (int size3 = mirrorActiveUids.mNumNonAppVisibleWindowMap.size() - 1; size3 >= 0; size3 += -1) {
                        printWriter.print(" " + mirrorActiveUids.mNumNonAppVisibleWindowMap.keyAt(size3) + ":" + mirrorActiveUids.mNumNonAppVisibleWindowMap.valueAt(size3));
                    }
                    printWriter.println("]");
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.mDemoteTopAppReasons != 0) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("  mDemoteTopAppReasons="), this.mDemoteTopAppReasons, printWriter);
            }
            printWriter.println();
            if (CoreRune.MT_SIZE_COMPAT_POLICY) {
                SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.LazyHolder.sManager;
                if (sizeCompatPolicyManager.mCompatPolicyCount > 0) {
                    StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  SIZE COMPAT POLICY MANAGER", "    mLaunchPolicy=");
                    m$1.append(Integer.toString(sizeCompatPolicyManager.mLaunchPolicy));
                    printWriter.println(m$1.toString());
                    int size4 = sizeCompatPolicyManager.mDisplayIdsForActiveMode.size();
                    if (size4 > 0) {
                        printWriter.print("    mActiveMode={ ");
                        for (int i7 = 0; i7 < size4; i7++) {
                            int keyAt = sizeCompatPolicyManager.mDisplayIdsForActiveMode.keyAt(i7);
                            int intValue = ((Integer) sizeCompatPolicyManager.mDisplayIdsForActiveMode.valueAt(i7)).intValue();
                            printWriter.print(keyAt);
                            printWriter.print("=");
                            printWriter.print(SizeCompatInfo.sizeCompatModeToString(intValue));
                            printWriter.print(" ");
                        }
                        printWriter.println("}");
                    }
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("    mCompatPolicyCount="), sizeCompatPolicyManager.mCompatPolicyCount, printWriter);
                }
            }
            List list = PackagesChange.sAllPackagesChange;
            printWriter.println("  PACKAGE SETTINGS MANAGER");
            Iterator it2 = ((ArrayList) PackagesChange.sAllPackagesChange).iterator();
            while (it2.hasNext()) {
                PackagesChange packagesChange = (PackagesChange) it2.next();
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("    "), packagesChange.mControllerName, printWriter);
                packagesChange.dump(printWriter, "      ");
            }
            printWriter.println("  PACKAGE SETTINGS MANAGER - USER CHANGES");
            Iterator it3 = ((ArrayList) PackagesChange.sAllPackagesChange).iterator();
            while (it3.hasNext()) {
                PackagesChange packagesChange2 = (PackagesChange) it3.next();
                PackageFeatureUserChange[] packageFeatureUserChangeArr = packagesChange2.mUserChanges;
                if (packageFeatureUserChangeArr != null && packageFeatureUserChangeArr.length != 0) {
                    for (PackageFeatureUserChange packageFeatureUserChange : packageFeatureUserChangeArr) {
                        packageFeatureUserChange.dump(printWriter, packagesChange2.mControllerName, "    ");
                    }
                }
            }
            printWriter.println();
            if (!this.mStartingProcessActivities.isEmpty()) {
                printWriter.println("  mStartingProcessActivities=" + this.mStartingProcessActivities);
            }
            z4 = true;
        } else {
            z4 = z3;
        }
        if (z4) {
            return;
        }
        printWriter.println("  (nothing)");
    }

    public final boolean dumpActivity(FileDescriptor fileDescriptor, PrintWriter printWriter, String str, String[] strArr, int i, boolean z, boolean z2, boolean z3, int i2, int i3, long j) {
        ArrayList dumpActivities;
        boolean z4;
        IApplicationThread iApplicationThread;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                dumpActivities = this.mRootWindowContainer.getDumpActivities(str, z2, z3, i3);
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (CoreRune.BAIDU_CARLIFE && BaiduCarlifeADBConnectUtils.isCarlifeForceConnect()) {
            printWriter.print("TASK com.baidu.carlife id=23203 userId=0\n");
            printWriter.print("ACTIVITY com.baidu.carlife/.CarlifeActivity 5b92975 pid=18428\n");
            printWriter.print("Local Activity bae17e9 State:\n");
            printWriter.print("mResumed=true mStopped=false mFinished=false\n");
            printWriter.print("mChangingConfigurations=false\n");
            return true;
        }
        if (dumpActivities.size() <= 0) {
            return false;
        }
        String[] strArr2 = new String[strArr.length - i];
        System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
        Task task = null;
        boolean z5 = false;
        int size = dumpActivities.size() - 1;
        boolean z6 = false;
        while (size >= 0) {
            ActivityRecord activityRecord = (ActivityRecord) dumpActivities.get(size);
            if (z5) {
                printWriter.println();
            }
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    Task task2 = activityRecord.task;
                    int displayId = task2.getDisplayId();
                    if (i2 == -1 || displayId == i2) {
                        if (task != task2) {
                            printWriter.print("TASK ");
                            printWriter.print(task2.affinity);
                            printWriter.print(" id=");
                            printWriter.print(task2.mTaskId);
                            printWriter.print(" userId=");
                            printWriter.print(task2.mUserId);
                            printDisplayInfoAndNewLine(printWriter, activityRecord);
                            if (z) {
                                task2.dump(printWriter, "  ");
                            }
                            z4 = true;
                        } else {
                            task2 = task;
                            z4 = z6;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        ActivityRecord activityRecord2 = (ActivityRecord) dumpActivities.get(size);
                        WindowManagerGlobalLock windowManagerGlobalLock3 = this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock3) {
                            try {
                                printWriter.print("  ");
                                printWriter.print("ACTIVITY ");
                                printWriter.print(activityRecord2.shortComponentName);
                                printWriter.print(" ");
                                printWriter.print(Integer.toHexString(System.identityHashCode(activityRecord2)));
                                printWriter.print(" pid=");
                                if (activityRecord2.hasProcess()) {
                                    printWriter.print(activityRecord2.app.mPid);
                                    iApplicationThread = activityRecord2.app.mThread;
                                } else {
                                    printWriter.print("(not running)");
                                    iApplicationThread = null;
                                }
                                printWriter.print(" userId=");
                                printWriter.print(activityRecord2.mUserId);
                                printWriter.print(" uid=");
                                printWriter.print(activityRecord2.getUid());
                                printDisplayInfoAndNewLine(printWriter, activityRecord2);
                                if (z) {
                                    activityRecord2.dump(printWriter, "    ", true);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        if (iApplicationThread != null) {
                            printWriter.flush();
                            try {
                                TransferPipe transferPipe = new TransferPipe();
                                try {
                                    iApplicationThread.dumpActivity(transferPipe.getWriteFd(), activityRecord2.token, "    ", strArr2);
                                    try {
                                        transferPipe.go(fileDescriptor, j);
                                        try {
                                            transferPipe.close();
                                        } catch (RemoteException unused) {
                                            printWriter.println("    Got a RemoteException while dumping the activity");
                                            z6 = z4;
                                            task = task2;
                                            size--;
                                            z5 = true;
                                        } catch (IOException e) {
                                            e = e;
                                            printWriter.println("    Failure while dumping the activity: " + e);
                                            z6 = z4;
                                            task = task2;
                                            size--;
                                            z5 = true;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        Throwable th3 = th;
                                        try {
                                            transferPipe.close();
                                        } catch (Throwable th4) {
                                            th3.addSuppressed(th4);
                                        }
                                        throw th3;
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                }
                            } catch (RemoteException unused2) {
                            } catch (IOException e2) {
                                e = e2;
                            }
                        }
                        z6 = z4;
                        task = task2;
                    } else {
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
            size--;
            z5 = true;
        }
        if (!z6) {
            printWriter.println("(nothing)");
        }
        return true;
    }

    public final void dumpInstalledPackagesConfig(PrintWriter printWriter) {
        PackageConfigPersister packageConfigPersister = this.mPackageConfigPersister;
        int currentUserId = this.mAmInternal.getCurrentUserId();
        packageConfigPersister.getClass();
        printWriter.println("INSTALLED PACKAGES HAVING APP-SPECIFIC CONFIGURATIONS");
        AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(printWriter, "Current user ID : ", currentUserId);
        synchronized (packageConfigPersister.mLock) {
            try {
                HashMap hashMap = (HashMap) packageConfigPersister.mModified.get(currentUserId);
                if (hashMap != null) {
                    for (PackageConfigPersister.PackageConfigRecord packageConfigRecord : hashMap.values()) {
                        printWriter.println();
                        printWriter.println("    PackageName : " + packageConfigRecord.mName);
                        printWriter.println("        NightMode : " + packageConfigRecord.mNightMode);
                        printWriter.println("        Locales : " + packageConfigRecord.mLocales);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpVisibleActivitiesLocked(int i, PrintWriter printWriter) {
        printWriter.println("ACTIVITY MANAGER VISIBLE ACTIVITIES (dumpsys activity visible)");
        boolean z = false;
        ArrayList dumpActivities = this.mRootWindowContainer.getDumpActivities("all", true, false, -1);
        boolean z2 = false;
        for (int size = dumpActivities.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord = (ActivityRecord) dumpActivities.get(size);
            if (activityRecord.mVisible && (i == -1 || activityRecord.getDisplayId() == i)) {
                if (z2) {
                    printWriter.println();
                }
                activityRecord.dump(printWriter, "", true);
                z = true;
                z2 = true;
            }
        }
        if (z) {
            return;
        }
        printWriter.println("(nothing)");
    }

    public final void endPowerMode(int i) {
        int i2 = this.mPowerModeReasons;
        if (i2 == 0) {
            return;
        }
        int i3 = (~i) & i2;
        this.mPowerModeReasons = i3;
        if ((i3 & 4) != 0) {
            boolean z = true;
            for (int childCount = this.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                z &= ((DisplayContent) this.mRootWindowContainer.getChildAt(childCount)).mUnknownAppVisibilityController.mUnknownApps.isEmpty();
            }
            if (z) {
                this.mPowerModeReasons &= -5;
                this.mRetainPowerModeAndTopProcessState = false;
                this.mH.removeMessages(3);
            }
        }
        if (this.mPowerManagerInternal == null) {
            return;
        }
        if ((i2 & 5) != 0 && (this.mPowerModeReasons & 5) == 0) {
            Trace.instant(32L, "EndModeLaunch");
            this.mPowerManagerInternal.setPowerMode(5, false);
            ActivityManagerPerformance activityManagerPerformance = this.mAMBooster;
            if (activityManagerPerformance != null) {
                activityManagerPerformance.onActivityVisibleLocked(this.mLastResumedActivity);
            }
        }
        if ((i2 & 2) == 0 || (this.mPowerModeReasons & 2) != 0) {
            return;
        }
        Trace.instant(32L, "EndModeDisplayChange");
        this.mPowerManagerInternal.setPowerMode(17, false);
    }

    public final void enforceSystemHasVrFeature() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) {
            throw new UnsupportedOperationException("VR mode not supported on this device!");
        }
    }

    public final PendingAssistExtras enqueueAssistContext(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2, int i2, long j, int i3) {
        ActivityRecord forTokenLocked;
        this.mAmInternal.enforceCallingPermission("android.permission.GET_TOP_ACTIVITY_INFO", "enqueueAssistContext()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                ActivityRecord topNonFinishingActivity = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopNonFinishingActivity(true, true) : null;
                if (topNonFinishingActivity == null) {
                    Slog.w("ActivityTaskManager", "getAssistContextExtras failed: no top activity");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (!topNonFinishingActivity.attachedToProcess()) {
                    Slog.w("ActivityTaskManager", "getAssistContextExtras failed: no process for " + topNonFinishingActivity);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (!z) {
                    topNonFinishingActivity = ActivityRecord.forTokenLocked(iBinder);
                    if (topNonFinishingActivity == null) {
                        Slog.w("ActivityTaskManager", "enqueueAssistContext failed: activity for token=" + iBinder + " couldn't be found");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (!topNonFinishingActivity.attachedToProcess()) {
                        Slog.w("ActivityTaskManager", "enqueueAssistContext failed: no process for " + topNonFinishingActivity);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                } else if (iBinder != null && topNonFinishingActivity != (forTokenLocked = ActivityRecord.forTokenLocked(iBinder))) {
                    Slog.w("ActivityTaskManager", "enqueueAssistContext failed: caller " + forTokenLocked + " is not current top " + topNonFinishingActivity);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                ActivityRecord activityRecord = topNonFinishingActivity;
                Bundle bundle2 = new Bundle();
                bundle2.putString("android.intent.extra.ASSIST_PACKAGE", activityRecord.packageName);
                bundle2.putInt("android.intent.extra.ASSIST_UID", activityRecord.app.mUid);
                PendingAssistExtras pendingAssistExtras = new PendingAssistExtras(activityRecord, bundle2, iAssistDataReceiver, bundle, i2);
                pendingAssistExtras.isHome = activityRecord.isActivityTypeHome();
                if (z2) {
                    this.mViSessionId++;
                }
                try {
                    activityRecord.app.mThread.requestAssistContextExtras(activityRecord.token, pendingAssistExtras, i, this.mViSessionId, i3);
                    this.mPendingAssistExtras.add(pendingAssistExtras);
                    this.mUiHandler.postDelayed(pendingAssistExtras, j);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return pendingAssistExtras;
                } catch (RemoteException unused) {
                    Slog.w("ActivityTaskManager", "getAssistContextExtras failed: crash calling " + activityRecord);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean ensureConfigAndVisibilityAfterUpdate(int i, ActivityRecord activityRecord) {
        Task topDisplayFocusedRootTask;
        if ((activityRecord == null && this.mTaskSupervisor.mDeferRootVisibilityUpdate) || (topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask()) == null) {
            return true;
        }
        if (i != 0 && activityRecord == null) {
            activityRecord = topDisplayFocusedRootTask.topRunningActivity(false);
        }
        if (activityRecord == null) {
            return true;
        }
        boolean ensureActivityConfiguration = activityRecord.ensureActivityConfiguration(false);
        this.mRootWindowContainer.ensureActivitiesVisible(true, activityRecord);
        return ensureActivityConfiguration;
    }

    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda22] */
    public final boolean enterPictureInPictureMode(final ActivityRecord activityRecord, final PictureInPictureParams pictureInPictureParams, boolean z, final boolean z2) {
        if (activityRecord.inPinnedWindowingMode()) {
            return true;
        }
        if (!activityRecord.checkEnterPictureInPictureState("enterPictureInPictureMode", false, false)) {
            return false;
        }
        ActivityRecord.State state = ActivityRecord.State.PAUSING;
        if (z && activityRecord.isState(state) && pictureInPictureParams.isAutoEnterEnabled()) {
            Slog.w("ActivityTaskManager", "Skip client enterPictureInPictureMode request while pausing, auto-enter-pip is enabled");
            return false;
        }
        if (isPip2ExperimentEnabled()) {
            final Transition transition = new Transition(10, 0, this.mWindowOrganizerController.mTransitionController, this.mWindowManager.mSyncEngine);
            transition.mPipActivity = activityRecord;
            activityRecord.mAutoEnteringPip = z2;
            this.mWindowOrganizerController.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda21
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z3) {
                    ActivityTaskManagerService.this.mWindowOrganizerController.mTransitionController.requestStartTransition(transition, activityRecord.task, null, null);
                }
            });
            return true;
        }
        Transition transition2 = (this.mWindowOrganizerController.mTransitionController.isShellTransitionsEnabled() && (z && (!activityRecord.isState(state) || pictureInPictureParams.isAutoEnterEnabled()))) ? new Transition(10, 0, this.mWindowOrganizerController.mTransitionController, this.mWindowManager.mSyncEngine) : null;
        if (activityRecord.getTaskFragment() != null && activityRecord.getTaskFragment().isEmbeddedWithBoundsOverride() && transition2 != null) {
            transition2.addFlag(512);
        }
        final Transition transition3 = transition2;
        final ?? r11 = new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                ActivityRecord activityRecord2 = activityRecord;
                Transition transition4 = transition3;
                boolean z3 = z2;
                PictureInPictureParams pictureInPictureParams2 = pictureInPictureParams;
                WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (activityRecord2.getParent() != null && (!CoreRune.MW_PIP_SHELL_TRANSITION || !activityRecord2.finishing)) {
                            EventLog.writeEvent(38000, Integer.valueOf(activityRecord2.mUserId), Integer.valueOf(System.identityHashCode(activityRecord2)), activityRecord2.shortComponentName, Boolean.toString(z3));
                            activityRecord2.pictureInPictureArgs.copyOnlySet(pictureInPictureParams2);
                            Rect bounds = activityRecord2.getBounds();
                            PictureInPictureParams pictureInPictureParams3 = activityRecord2.pictureInPictureArgs;
                            if (pictureInPictureParams3 != null && pictureInPictureParams3.hasSourceBoundsHint()) {
                                activityRecord2.pictureInPictureArgs.getSourceRectHint().offset(bounds.left, bounds.top);
                            }
                            Task rootTask = activityRecord2.task.getRootTask();
                            if (rootTask.inPinnedWindowingMode()) {
                                rootTask.dispatchTaskInfoChangedIfNeeded(true);
                            }
                            activityRecord2.mAutoEnteringPip = z3;
                            activityTaskManagerService.mRootWindowContainer.moveActivityToPinnedRootTask(activityRecord2, null, "enterPictureInPictureMode", transition4, null);
                            if (activityRecord2.isState(ActivityRecord.State.PAUSING) && activityRecord2.mPauseSchedulePendingForPip) {
                                activityRecord2.task.schedulePauseActivity(activityRecord2, false, false, true, "auto-pip");
                            }
                            activityRecord2.mAutoEnteringPip = false;
                            if (CoreRune.MW_PIP_SA_LOGGING) {
                                CoreSaLogger.logForAdvanced("2301", activityRecord2.packageName);
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        Slog.e("ActivityTaskManager", "Skip enterPictureInPictureMode, destroyed " + activityRecord2);
                        if (transition4 != null && (!CoreRune.MW_PIP_SHELL_TRANSITION || transition4.isCollecting())) {
                            Slog.e("ActivityTaskManager[PipTaskOrganizer]", "transition abort, destroyed=" + activityRecord2);
                            transition4.abort();
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        };
        if (activityRecord.isKeyguardLocked()) {
            this.mActivityClientController.dismissKeyguard(activityRecord.token, new AnonymousClass6(transition2, r11), null);
        } else if (transition2 != null) {
            this.mWindowOrganizerController.mTransitionController.startCollectOrQueue(transition2, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda23
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z3) {
                    r11.run();
                }
            });
        } else {
            r11.run();
        }
        return true;
    }

    public final void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
                    rootWindowContainer.getClass();
                    rootWindowContainer.forAllLeafTasks(new RootWindowContainer$$ExternalSyntheticLambda1(1, iVoiceInteractionSession.asBinder()), true);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX WARN: Finally extract failed */
    public final void focusTopTask(int i) {
        enforceTaskPermission("focusTopTask()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent != null) {
                        Task task = displayContent.getTask(new ActivityTaskManagerService$$ExternalSyntheticLambda6(0), true);
                        if (task != null) {
                            setFocusedTask(task.mTaskId, null);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                    }
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

    public final IActivityClientController getActivityClientController() {
        return this.mActivityClientController;
    }

    public final List getAllRootTaskInfos() {
        ArrayList allRootTaskInfos;
        enforceTaskPermission("getAllRootTaskInfos()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    allRootTaskInfos = this.mRootWindowContainer.getAllRootTaskInfos(-1);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return allRootTaskInfos;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getAllRootTaskInfosOnDisplay(int i) {
        ArrayList allRootTaskInfos;
        enforceTaskPermission("getAllRootTaskInfosOnDisplay()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    allRootTaskInfos = this.mRootWindowContainer.getAllRootTaskInfos(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return allRootTaskInfos;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final android.app.AnrController getAnrController(ApplicationInfo applicationInfo) {
        ArrayList arrayList;
        android.app.AnrController anrController = null;
        if (applicationInfo == null || applicationInfo.packageName == null) {
            return null;
        }
        synchronized (this.mAnrController) {
            arrayList = new ArrayList(this.mAnrController);
        }
        String str = applicationInfo.packageName;
        int i = applicationInfo.uid;
        Iterator it = arrayList.iterator();
        long j = 0;
        while (it.hasNext()) {
            android.app.AnrController anrController2 = (android.app.AnrController) it.next();
            long anrDelayMillis = anrController2.getAnrDelayMillis(str, i);
            if (anrDelayMillis > 0 && anrDelayMillis > j) {
                anrController = anrController2;
                j = anrDelayMillis;
            }
        }
        return anrController;
    }

    public final String getAppLockedCheckAction() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getAppLockedCheckAction();
        }
        return null;
    }

    public final String getAppLockedLockType() {
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mExt;
        activityTaskManagerServiceExt.getClass();
        int callingUid = Binder.getCallingUid();
        if (!ActivityTaskManagerServiceExt.isSystemUid(callingUid)) {
            throw new SecurityException(NandswapManager$$ExternalSyntheticOutline0.m(callingUid, " cannot call getAppLockedLockType()"));
        }
        AppLockPolicy appLockPolicy = activityTaskManagerServiceExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getAppLockedLockType();
        }
        return null;
    }

    public final List getAppLockedPackageList() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getAppLockedPackageList();
        }
        return null;
    }

    public final AppOpsManager getAppOpsManager() {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        }
        return this.mAppOpsManager;
    }

    public final Point getAppTaskThumbnailSize() {
        Point point;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                point = new Point(this.mThumbnailWidth, this.mThumbnailHeight);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return point;
    }

    public final List getAppTasks(int i, String str) {
        ArrayList appTasksList;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    appTasksList = this.mRecentTasks.getAppTasksList(i, str);
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return appTasksList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getAppTasks(String str) {
        assertPackageMatchesCallingUid(str);
        return getAppTasks(Binder.getCallingUid(), str);
    }

    public final String getApplockLockedAppsClass() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getApplockLockedAppsClass();
        }
        return null;
    }

    public final String getApplockLockedAppsPackage() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getApplockLockedAppsPackage();
        }
        return null;
    }

    public final int getApplockType() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getApplockType();
        }
        return 0;
    }

    public final Bundle getAssistContextExtras(int i) {
        PendingAssistExtras enqueueAssistContext = enqueueAssistContext(i, null, null, null, true, true, UserHandle.getCallingUserId(), 500L, 0);
        if (enqueueAssistContext == null) {
            return null;
        }
        synchronized (enqueueAssistContext) {
            while (!enqueueAssistContext.haveResult) {
                try {
                    enqueueAssistContext.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Bundle bundle = enqueueAssistContext.result;
                if (bundle != null) {
                    enqueueAssistContext.extras.putBundle("android.intent.extra.ASSIST_CONTEXT", bundle);
                }
                String str = enqueueAssistContext.hint;
                if (str != null) {
                    enqueueAssistContext.extras.putBoolean(str, true);
                }
                this.mPendingAssistExtras.remove(enqueueAssistContext);
                this.mUiHandler.removeCallbacks(enqueueAssistContext);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return enqueueAssistContext.extras;
    }

    public ActivityTaskManagerInternal getAtmInternal() {
        return this.mInternal;
    }

    public final ParceledListSlice getCompatChangeablePackageInfoList(String str, int i) {
        enforceTaskPermission("getCompatChangeablePackageInfoList()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return CompatChangeableAppsService.getCompatChangeablePackageInfoList(str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Configuration getConfiguration() {
        Configuration configuration;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                configuration = new Configuration(getGlobalConfigurationForCallingPid());
                configuration.userSetLocale = false;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return configuration;
    }

    public final List getCoverLauncherAvailableAppList(int i) {
        return List.of();
    }

    public final Map getCoverLauncherEnabledAppList(int i) {
        return null;
    }

    public final Map getCoverLauncherEnabledAppListByType(int i, int i2) {
        return null;
    }

    public final int getCutoutPolicy(int i, String str) {
        int policy;
        enforceTaskPermission("getCutoutPolicy()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    policy = this.mExt.mDisplayCutoutController.getPolicy(i, str);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return policy;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ConfigurationInfo getDeviceConfigurationInfo() {
        ConfigurationInfo configurationInfo = new ConfigurationInfo();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Configuration globalConfigurationForCallingPid = getGlobalConfigurationForCallingPid();
                configurationInfo.reqTouchScreen = globalConfigurationForCallingPid.touchscreen;
                int i = globalConfigurationForCallingPid.keyboard;
                configurationInfo.reqKeyboardType = i;
                int i2 = globalConfigurationForCallingPid.navigation;
                configurationInfo.reqNavigation = i2;
                if (i2 == 2 || i2 == 3) {
                    configurationInfo.reqInputFeatures |= 2;
                }
                if (i != 0 && i != 1) {
                    configurationInfo.reqInputFeatures = 1 | configurationInfo.reqInputFeatures;
                }
                configurationInfo.reqGlEsVersion = this.GL_ES_VERSION;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return configurationInfo;
    }

    /* JADX WARN: Finally extract failed */
    public final ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() {
        ActivityTaskManager.RootTaskInfo rootTaskInfo;
        enforceTaskPermission("getFocusedRootTaskInfo()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                    if (topDisplayFocusedRootTask != null) {
                        Task rootTask = this.mRootWindowContainer.getRootTask(topDisplayFocusedRootTask.mTaskId);
                        rootTaskInfo = rootTask != null ? RootWindowContainer.getRootTaskInfo(rootTask) : null;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return rootTaskInfo;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IFoldStarManager getFoldStarManagerService() {
        FoldStarManagerService foldStarManagerService;
        enforceTaskPermission("getFoldStarManagerService()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (FoldStarManagerService.sService == null) {
                    FoldStarManagerService.sService = new FoldStarManagerService(this);
                }
                foldStarManagerService = FoldStarManagerService.sService;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return foldStarManagerService;
    }

    public final int getFrontActivityScreenCompatMode() {
        int i;
        enforceNotIsolatedCaller("getFrontActivityScreenCompatMode");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                ActivityRecord activityRecord = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.topRunningActivity(false) : null;
                if (activityRecord == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return -3;
                }
                CompatModePackages compatModePackages = this.mCompatModePackages;
                ApplicationInfo applicationInfo = activityRecord.info.applicationInfo;
                CompatibilityInfo compatibilityInfoForPackageLocked = compatModePackages.compatibilityInfoForPackageLocked(applicationInfo);
                if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
                    i = -2;
                } else if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
                    i = -1;
                } else {
                    i = (compatModePackages.getPackageFlags(applicationInfo.packageName) & 2) != 0 ? 1 : 0;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final Configuration getGlobalConfiguration() {
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        return rootWindowContainer != null ? rootWindowContainer.getConfiguration() : new Configuration();
    }

    public final Configuration getGlobalConfigurationForCallingPid() {
        int callingPid = Binder.getCallingPid();
        if (callingPid == WindowManagerService.MY_PID || callingPid < 0) {
            return getGlobalConfiguration();
        }
        WindowProcessController process = this.mProcessMap.getProcess(callingPid);
        return process != null ? process.getConfiguration() : getGlobalConfiguration();
    }

    public final Intent getHomeIntent() {
        Intent intent = new Intent(this.mTopAction, (Uri) null);
        intent.setComponent(this.mTopComponent);
        intent.addFlags(256);
        if (this.mFactoryTest != 1) {
            intent.addCategory("android.intent.category.HOME");
        }
        return intent;
    }

    public final Set getIdsClearSet() {
        if (this.mSetClearIds == null) {
            this.mSetClearIds = ConcurrentHashMap.newKeySet();
        }
        return this.mSetClearIds;
    }

    public final PendingIntentRecord getIntentSenderLocked(int i, int i2, int i3, int i4, int i5, Bundle bundle, IBinder iBinder, String str, String str2, String str3, Intent[] intentArr, String[] strArr) {
        ActivityRecord activityRecord;
        ActivityTaskManagerService activityTaskManagerService;
        if (i == 3) {
            ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
            if (isInRootTaskLocked == null) {
                Slog.w("ActivityTaskManager", "Failed createPendingResult: activity " + iBinder + " not in any root task");
                return null;
            }
            if (isInRootTaskLocked.finishing) {
                Slog.w("ActivityTaskManager", "Failed createPendingResult: activity " + isInRootTaskLocked + " is finishing");
                return null;
            }
            activityTaskManagerService = this;
            activityRecord = isInRootTaskLocked;
        } else {
            activityRecord = null;
            activityTaskManagerService = this;
        }
        PendingIntentRecord intentSender = activityTaskManagerService.mPendingIntentController.getIntentSender(i, i2, i3, i4, i5, bundle, iBinder, str, str2, str3, intentArr, strArr);
        if ((i5 & 536870912) == 0 && i == 3) {
            if (activityRecord.pendingResults == null) {
                activityRecord.pendingResults = new HashSet();
            }
            activityRecord.pendingResults.add(intentSender.ref);
        }
        return intentSender;
    }

    public final int getLastResumedActivityUserId() {
        this.mAmInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "getLastResumedActivityUserId()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord activityRecord = this.mLastResumedActivity;
                if (activityRecord == null) {
                    int currentUserId = this.mAmInternal.getCurrentUserId();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return currentUserId;
                }
                int i = activityRecord.mUserId;
                WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final int getLockTaskModeState() {
        return this.mLockTaskController.mLockTaskModeState;
    }

    public final IMultiTaskingBinder getMultiTaskingBinder() {
        return this.mMultiTaskingBinder;
    }

    public final int getOrientationControlPolicy(int i, String str) {
        if (!CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY || str == null) {
            return 0;
        }
        enforceTaskPermission("getOrientationControlPolicy()");
        int respectOrientationRequest = this.mMultiTaskingAppCompatController.mOrientationOverrides.getRespectOrientationRequest(i, str);
        if (respectOrientationRequest != -1) {
            return respectOrientationRequest;
        }
        return 0;
    }

    public final boolean getPackageAskScreenCompat(String str) {
        boolean z;
        enforceNotIsolatedCaller("getPackageAskScreenCompat");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int packageFlags = this.mCompatModePackages.getPackageFlags(str);
                z = true;
                if ((packageFlags & 1) != 0) {
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

    public final PackageManagerInternal getPackageManagerInternalLocked() {
        if (this.mPmInternal == null) {
            this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        return this.mPmInternal;
    }

    public final int getPackageScreenCompatMode(String str) {
        ApplicationInfo applicationInfo;
        int i;
        enforceNotIsolatedCaller("getPackageScreenCompatMode");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                CompatModePackages compatModePackages = this.mCompatModePackages;
                compatModePackages.getClass();
                try {
                    applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
                } catch (RemoteException unused) {
                    applicationInfo = null;
                }
                if (applicationInfo == null) {
                    i = -3;
                } else {
                    CompatibilityInfo compatibilityInfoForPackageLocked = compatModePackages.compatibilityInfoForPackageLocked(applicationInfo);
                    if (compatibilityInfoForPackageLocked.alwaysSupportsScreen()) {
                        i = -2;
                    } else if (compatibilityInfoForPackageLocked.neverSupportsScreen()) {
                        i = -1;
                    } else {
                        i = (compatModePackages.getPackageFlags(applicationInfo.packageName) & 2) != 0 ? 1 : 0;
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return i;
    }

    public final WindowProcessController getProcessController(int i, int i2) {
        WindowProcessController process = this.mProcessMap.getProcess(i);
        if (process != null && UserHandle.isApp(i2) && process.mUid == i2) {
            return process;
        }
        return null;
    }

    public final WindowProcessController getProcessController(int i, String str) {
        if (i == 1000) {
            SparseArray sparseArray = (SparseArray) this.mProcessNames.getMap().get(str);
            if (sparseArray == null) {
                return null;
            }
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sparseArray.keyAt(i2);
                if (!UserHandle.isApp(keyAt) && UserHandle.isSameUser(keyAt, i)) {
                    return (WindowProcessController) sparseArray.valueAt(i2);
                }
            }
        }
        return (WindowProcessController) this.mProcessNames.get(str, i);
    }

    public final WindowProcessController getProcessController(IApplicationThread iApplicationThread) {
        if (iApplicationThread == null) {
            return null;
        }
        IBinder asBinder = iApplicationThread.asBinder();
        ArrayMap map = this.mProcessNames.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            SparseArray sparseArray = (SparseArray) map.valueAt(size);
            for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                WindowProcessController windowProcessController = (WindowProcessController) sparseArray.valueAt(size2);
                if (windowProcessController.hasThread() && windowProcessController.mThread.asBinder() == asBinder) {
                    return windowProcessController;
                }
            }
        }
        return null;
    }

    public final ParceledListSlice getRecentTasks(int i, int i2, int i3) {
        ParceledListSlice parceledListSlice;
        int callingUid = Binder.getCallingUid();
        int handleIncomingUser = handleIncomingUser(Binder.getCallingPid(), callingUid, i3, "getRecentTasks");
        boolean isGetTasksAllowed = isGetTasksAllowed(Binder.getCallingPid(), callingUid, "getRecentTasks");
        if (!this.mAmInternal.isUserRunning(handleIncomingUser, 4)) {
            Slog.i("ActivityTaskManager", "User " + handleIncomingUser + " is locked. Cannot load recents");
            return ParceledListSlice.emptyList();
        }
        this.mRecentTasks.loadRecentTasksIfNeeded(handleIncomingUser);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                RecentTasks recentTasks = this.mRecentTasks;
                recentTasks.getClass();
                parceledListSlice = new ParceledListSlice(recentTasks.getRecentTasksImpl(i, i2, handleIncomingUser, callingUid, isGetTasksAllowed));
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return parceledListSlice;
    }

    public final Bitmap getResumedTaskThumbnail(int i) {
        ActivityRecord activityRecord;
        this.mAmInternal.enforceCallingPermission("android.permission.READ_FRAME_BUFFER", "getResumedTaskThumbnail()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Bitmap bitmap = null;
                Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
                if (anyTaskForId == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                Task rootTask = anyTaskForId.getRootTask();
                if (rootTask != null && !rootTask.isActivityTypeHomeOrRecents() && ((!rootTask.inSplitScreenWindowingMode() || rootTask.isFocusable()) && (activityRecord = rootTask.mResumedActivity) != null && activityRecord.task == anyTaskForId && !activityRecord.noDisplay)) {
                    bitmap = anyTaskForId.getSnapshotAsBitmapLocked();
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return bitmap;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) {
        Task task;
        ActivityTaskManager.RootTaskInfo rootTaskInfo;
        enforceTaskPermission("getRootTaskInfo()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
                    int childCount = rootWindowContainer.getChildCount() - 1;
                    while (true) {
                        if (childCount < 0) {
                            task = null;
                            break;
                        }
                        task = ((DisplayContent) rootWindowContainer.getChildAt(childCount)).getRootTask(i, i2);
                        if (task != null) {
                            break;
                        }
                        childCount--;
                    }
                    rootTaskInfo = task != null ? RootWindowContainer.getRootTaskInfo(task) : null;
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return rootTaskInfo;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) {
        ActivityTaskManager.RootTaskInfo rootTaskInfo;
        enforceTaskPermission("getRootTaskInfoOnDisplay()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(i3);
                    Task rootTask = displayContent == null ? null : displayContent.getRootTask(i, i2);
                    rootTaskInfo = rootTask != null ? RootWindowContainer.getRootTaskInfo(rootTask) : null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return rootTaskInfo;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getScpmVersion() {
        PackageFeatureManagerService packageFeatureManagerService = PackageFeatureManagerService.LazyHolder.sInstance;
        String str = PackageFeatureGroup.FOLDABLE_PACKAGE_FEATURE.mName;
        PackageFeatureController packageFeatureController = packageFeatureManagerService.mPackageFeatureController;
        synchronized (packageFeatureController.mLock) {
            try {
                PackageFeatureGroupRecord packageFeatureGroupRecord = (PackageFeatureGroupRecord) ((ConcurrentHashMap) packageFeatureController.mPackageFeatures.mGroups).get(str);
                if (packageFeatureGroupRecord == null) {
                    return null;
                }
                return Integer.toString(packageFeatureGroupRecord.getCurrentVersion());
            } finally {
            }
        }
    }

    public final Intent getSecondaryHomeIntent(String str) {
        Intent intent = new Intent(this.mTopAction, (Uri) null);
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_volume_up_to_exit_silent);
        if (str == null || z) {
            intent.setPackage(this.mContext.getResources().getString(R.string.face_acquired_pan_too_extreme));
        } else {
            intent.setPackage(str);
        }
        intent.addFlags(256);
        if (this.mFactoryTest != 1) {
            intent.addCategory("android.intent.category.SECONDARY_HOME");
        }
        return intent;
    }

    public final String getSsecureHiddenAppsPackages() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getSsecureHiddenAppsPackages();
        }
        return null;
    }

    public final ComponentName getSysUiServiceComponentLocked() {
        if (this.mSysUiServiceComponent == null) {
            this.mSysUiServiceComponent = getPackageManagerInternalLocked().getSystemUiServiceComponent();
        }
        return this.mSysUiServiceComponent;
    }

    public final Rect getTaskBounds(int i) {
        enforceTaskPermission("getTaskBounds()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Rect rect = new Rect();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "getTaskBounds: taskId=" + i + " not found");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return rect;
                    }
                    if (anyTaskForId.getParent() != null) {
                        rect.set(anyTaskForId.getBounds());
                    } else {
                        Rect rect2 = anyTaskForId.mLastNonFullscreenBounds;
                        if (rect2 != null) {
                            rect.set(rect2);
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return rect;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Bitmap getTaskDescriptionIcon(String str, int i) {
        int callingUid = Binder.getCallingUid();
        int handleIncomingUser = handleIncomingUser(Binder.getCallingPid(), callingUid, i, "getTaskDescriptionIcon");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord activity = this.mRootWindowContainer.getActivity(new ActivityTaskManagerService$$ExternalSyntheticLambda11(str, 0));
                if (activity == null || activity.getUid() != callingUid) {
                    try {
                        enforceTaskPermission("getTaskDescriptionIcon");
                    } catch (SecurityException e) {
                        Slog.w("ActivityTaskManager", "getTaskDescriptionIcon(): request (callingUid=" + callingUid + ", filePath=" + str + ", user=" + handleIncomingUser + ") doesn't match any activity");
                        throw e;
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (!new File(new File(Environment.getDataSystemCeDirectory(handleIncomingUser), "recent_images"), new File(str).getName()).getPath().equals(str) || !str.contains("_activity_icon_")) {
            throw new IllegalArgumentException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(handleIncomingUser, "Bad file path: ", str, " passed for userId "));
        }
        TaskPersister taskPersister = this.mRecentTasks.mTaskPersister;
        taskPersister.getClass();
        TaskPersister.ImageWriteQueueItem imageWriteQueueItem = (TaskPersister.ImageWriteQueueItem) taskPersister.mPersisterQueue.findLastItem(new TaskPersister$$ExternalSyntheticLambda0(str), TaskPersister.ImageWriteQueueItem.class);
        Bitmap bitmap = imageWriteQueueItem != null ? imageWriteQueueItem.mImage : null;
        return bitmap != null ? bitmap : BitmapFactory.decodeFile(str);
    }

    public final TaskSnapshot getTaskSnapshot(int i, boolean z) {
        return getTaskSnapshot(i, z, false);
    }

    public final TaskSnapshot getTaskSnapshot(int i, boolean z, boolean z2) {
        ActivityRecord topNonFinishingActivity;
        this.mAmInternal.enforceCallingPermission("android.permission.READ_FRAME_BUFFER", "getTaskSnapshot()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "getTaskSnapshot: taskId=" + i + " not found");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (anyTaskForId.isDexMode() && (topNonFinishingActivity = anyTaskForId.getTopNonFinishingActivity(true, true)) != null && topNonFinishingActivity.isState(ActivityRecord.State.RESUMED)) {
                        this.mWindowManager.mTaskSnapshotController.takeSnapshotByForce(anyTaskForId);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return this.mWindowManager.mTaskSnapshotController.getSnapshot(i, anyTaskForId.mUserId, true, z, z2);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final TaskSnapshot getTaskSnapshotLowResolution(int i) {
        return getTaskSnapshot(i, true, true);
    }

    public final List getTasks(int i, boolean z, boolean z2, int i2) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int i3 = (z ? 1 : 0) | (z2 ? 8 : 0) | ((ActivityManagerService.checkComponentPermission(callingPid, callingUid, "android.permission.INTERACT_ACROSS_USERS", 0, -1, true) == 0 || ActivityManagerService.checkComponentPermission(callingPid, callingUid, "android.permission.INTERACT_ACROSS_USERS_FULL", 0, -1, true) == 0) ? 4 : 0);
        int[] profileIds = getUserManager().getProfileIds(null, true, false, UserHandle.getUserId(callingUid));
        ArraySet arraySet = new ArraySet();
        for (int i4 : profileIds) {
            arraySet.add(Integer.valueOf(i4));
        }
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRootWindowContainer.getRunningTasks(i, arrayList, i3 | (isGetTasksAllowed(callingPid, callingUid, "getTasks") ? 2 : 0), callingUid, arraySet, i2);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return arrayList;
    }

    public final UserManagerService getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
        }
        return this.mUserManager;
    }

    public final int getUserOrSystemMinAspectRatioOverrideCode(String str, int i) {
        if (!CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY || str == null) {
            return 0;
        }
        MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides = this.mMultiTaskingAppCompatController.mAspectRatioOverrides;
        multiTaskingAppCompatAspectRatioOverrides.getClass();
        int userMinAspectRatioOverrideCode = MultiTaskingAppCompatAspectRatioOverrides.getUserMinAspectRatioOverrideCode(i, str);
        if (userMinAspectRatioOverrideCode != 0) {
            return userMinAspectRatioOverrideCode;
        }
        float systemMinAspectRatio = multiTaskingAppCompatAspectRatioOverrides.getSystemMinAspectRatio(str);
        if (systemMinAspectRatio == 1.7777778f) {
            return 4;
        }
        if (systemMinAspectRatio == 1.3333334f) {
            return 3;
        }
        return userMinAspectRatioOverrideCode;
    }

    public final String getVoiceInteractorPackageName(IBinder iBinder) {
        return ((VoiceInteractionManagerInternal) LocalServices.getService(VoiceInteractionManagerInternal.class)).getVoiceInteractorPackageName(iBinder);
    }

    public final IWindowOrganizerController getWindowOrganizerController() {
        return this.mWindowOrganizerController;
    }

    public final int handleIncomingUser(int i, int i2, int i3, String str) {
        return this.mAmInternal.handleIncomingUser(i, i2, i3, false, 0, str, (String) null);
    }

    public final boolean hasActiveVisibleWindow(int i) {
        boolean z;
        VisibleActivityProcessTracker visibleActivityProcessTracker = this.mVisibleActivityProcessTracker;
        synchronized (visibleActivityProcessTracker.mProcMap) {
            try {
                z = true;
                int size = visibleActivityProcessTracker.mProcMap.size() - 1;
                while (true) {
                    if (size < 0) {
                        z = false;
                        break;
                    }
                    if (((WindowProcessController) visibleActivityProcessTracker.mProcMap.keyAt(size)).mUid != i) {
                        size--;
                    }
                }
            } finally {
            }
        }
        if (z) {
            return true;
        }
        return this.mActiveUids.hasNonAppVisibleWindow(i);
    }

    public final boolean hasSystemAlertWindowPermission(int i, int i2, String str) {
        int noteOpNoThrow = getAppOpsManager().noteOpNoThrow(24, i, str, (String) null, "");
        return noteOpNoThrow == 3 ? ActivityManagerService.checkComponentPermission(i2, i, "android.permission.SYSTEM_ALERT_WINDOW", 0, -1, true) == 0 : noteOpNoThrow == 0;
    }

    public final boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) {
        boolean canPlaceEntityOnDisplay;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ActivityInfo resolveActivityInfoForIntent = resolveActivityInfoForIntent(i2, callingUid, callingPid, intent, str);
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    canPlaceEntityOnDisplay = this.mTaskSupervisor.canPlaceEntityOnDisplay(i, callingPid, callingUid, null, resolveActivityInfoForIntent);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return canPlaceEntityOnDisplay;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAppLockedPackage(String str) {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.isAppLockedPackage(str);
        }
        return false;
    }

    public final boolean isAppLockedVerifying(String str) {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.isAppLockedVerifying(str);
        }
        return false;
    }

    public final boolean isApplockEnabled() {
        AppLockPolicy appLockPolicy = this.mExt.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.isApplockEnabled();
        }
        return false;
    }

    public final boolean isAssistDataAllowed() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                if (topDisplayFocusedRootTask != null && !topDisplayFocusedRootTask.isActivityTypeAssistant()) {
                    ActivityRecord topNonFinishingActivity = topDisplayFocusedRootTask.getTopNonFinishingActivity(true, true);
                    if (topNonFinishingActivity == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    int i = topNonFinishingActivity.mUserId;
                    DisplayContent displayContent = topNonFinishingActivity.getDisplayContent();
                    if (displayContent == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        boolean forAllWindows = displayContent.forAllWindows(new ToBooleanFunction() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda15
                            public final boolean apply(Object obj) {
                                ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                                WindowState windowState = (WindowState) obj;
                                activityTaskManagerService.getClass();
                                if (windowState.isOnScreen()) {
                                    if (!UserManager.isUserTypePrivateProfile(activityTaskManagerService.getUserManager().getProfileType(windowState.mShowUserId))) {
                                        if (activityTaskManagerService.getUserManager().hasUserRestriction("no_assist_content", windowState.mShowUserId)) {
                                        }
                                    }
                                    return true;
                                }
                                return false;
                            }
                        }, true);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return DevicePolicyCache.getInstance().isScreenCaptureAllowed(i) && !forAllWindows;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
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

    public final boolean isGetTasksAllowed(int i, int i2, String str) {
        boolean z = true;
        if (this.mRecentTasks.isCallerRecents(i2)) {
            return true;
        }
        boolean z2 = ActivityManagerService.checkComponentPermission(i, i2, "android.permission.REAL_GET_TASKS", 0, -1, true) == 0;
        if (!z2) {
            int checkComponentPermission = ActivityManagerService.checkComponentPermission(i, i2, "android.permission.GET_TASKS", 0, -1, true);
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled;
            if (checkComponentPermission == 0) {
                if (AppGlobals.getPackageManager().isUidPrivileged(i2)) {
                    try {
                        if (zArr[3]) {
                            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_TASKS, 8392804603924461448L, 4, null, str, Long.valueOf(i2));
                        }
                    } catch (RemoteException unused) {
                    }
                    z2 = z;
                }
                z = z2;
                z2 = z;
            }
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_TASKS, 4303745325174700522L, 4, null, str, Long.valueOf(i2));
            }
        }
        return z2;
    }

    public final boolean isInLockTaskMode() {
        return this.mLockTaskController.mLockTaskModeState != 0;
    }

    public final boolean isPackageEnabledForCoverLauncher(String str, int i) {
        return false;
    }

    public final boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2) {
        return false;
    }

    public final boolean isPossibleToStart(Intent intent) {
        String packageName;
        if (intent == null) {
            return false;
        }
        String action = intent.getAction();
        if ("android.intent.action.MAIN".equals(action) && intent.hasCategory("android.intent.category.HOME")) {
            return true;
        }
        if (action != null && ("android.intent.action.CALL_PRIVILEGED".equals(action) || "com.android.internal.intent.action.REQUEST_SHUTDOWN".equals(action) || action.toLowerCase().contains("emergency") || "intent.action.INTERACTION_ICE".equals(action))) {
            return true;
        }
        if (this.mCheckSIOPLevelList != null && intent.getComponent() != null && (packageName = intent.getComponent().getPackageName()) != null) {
            Slog.d("checkingSIOP", "isPossibleToStart  : ".concat(packageName));
            if (this.mCheckSIOPLevelList.containsKey(packageName)) {
                return ((Boolean) this.mCheckSIOPLevelList.get(packageName)).booleanValue();
            }
            for (Map.Entry entry : this.mCheckSIOPLevelList.entrySet()) {
                if (packageName.contains(entry.getKey().toString())) {
                    return Boolean.valueOf(entry.getValue().toString()).booleanValue();
                }
            }
        }
        return this.mSIOPLevel != 8 && this.mBatteryOverheatLevel <= 0;
    }

    public final boolean isTopActivityImmersive() {
        enforceNotIsolatedCaller("isTopActivityImmersive");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                boolean z = false;
                if (topDisplayFocusedRootTask == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                ActivityRecord activityRecord = topDisplayFocusedRootTask.topRunningActivity(false);
                if (activityRecord != null && activityRecord.immersive) {
                    z = true;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void keyguardGoingAway(int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_KEYGUARD", "unlock keyguard");
        enforceNotIsolatedCaller("keyguardGoingAway");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if ((i & 16) != 0) {
                        this.mActivityClientController.invalidateHomeTaskSnapshot(null);
                    } else if (this.mKeyguardShown) {
                        this.mDemoteTopAppReasons |= 1;
                    }
                    this.mRootWindowContainer.forAllDisplays(new ActivityTaskManagerService$$ExternalSyntheticLambda13(this, i, 0));
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (this.mWallpaperManagerInternal == null) {
                this.mWallpaperManagerInternal = (WallpaperManagerService.LocalService) LocalServices.getService(WallpaperManagerService.LocalService.class);
            }
            WallpaperManagerService.LocalService localService = this.mWallpaperManagerInternal;
            if (localService != null) {
                WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                synchronized (wallpaperManagerService.mLock) {
                    try {
                        for (WallpaperData wallpaperData : wallpaperManagerService.getActiveWallpapers()) {
                            wallpaperData.connection.forEachDisplayConnector(new WallpaperManagerService$$ExternalSyntheticLambda28(0));
                        }
                    } finally {
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void maybeHideLockedProfileActivityLocked() {
        UserInfo userInfo;
        if (this.mKeyguardController.isKeyguardLocked(0) && this.mLastResumedActivity != null && (userInfo = getUserManager().getUserInfo(this.mLastResumedActivity.mUserId)) != null && userInfo.isManagedProfile() && this.mAmInternal.shouldConfirmCredentials(this.mLastResumedActivity.mUserId)) {
            this.mInternal.startHomeActivity(this.mAmInternal.getCurrentUserId(), "maybeHideLockedProfileActivityLocked");
        }
    }

    public final void moveRootTaskToDisplay(int i, int i2) {
        this.mAmInternal.enforceCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "moveRootTaskToDisplay()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TASKS, 893763316922465955L, 5, null, Long.valueOf(i), Long.valueOf(i2));
                    }
                    this.mRootWindowContainer.moveRootTaskToDisplay(i, i2);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) {
        this.mAmInternal.enforceCallingPermission("android.permission.REORDER_TASKS", "moveTaskToFront()");
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TASKS, 7095858131234795548L, 1, null, Long.valueOf(i));
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                moveTaskToFrontLocked(iApplicationThread, str, i, i2, SafeActivityOptions.fromBundle(bundle));
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void moveTaskToFrontLocked(IApplicationThread iApplicationThread, String str, int i, int i2, SafeActivityOptions safeActivityOptions) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        assertPackageMatchesCallingUid(str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart = this.mTaskSupervisor.mBalController.checkBackgroundActivityStart(callingUid, callingPid, str, -1, -1, iApplicationThread != null ? getProcessController(iApplicationThread) : null, null, BackgroundStartPrivileges.NONE, null, null, null);
        if ((checkBackgroundActivityStart.mCode == 0) && !this.mAmInternal.isBackgroundActivityStartsEnabled()) {
            Slog.w("ActivityTaskManager", "moveTaskToFront blocked: " + checkBackgroundActivityStart);
            return;
        }
        try {
            Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
            if (anyTaskForId == null) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TASKS, -4458288191054594222L, 1, null, Long.valueOf(i));
                }
                SafeActivityOptions.abort(safeActivityOptions);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            if (this.mLockTaskController.isLockTaskModeViolation(anyTaskForId, false)) {
                Slog.e("ActivityTaskManager", "moveTaskToFront: Attempt to violate Lock Task Mode");
                SafeActivityOptions.abort(safeActivityOptions);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } else {
                if (anyTaskForId.inFreeformWindowingMode() && anyTaskForId.isMinimized()) {
                    resumeAppSwitches();
                }
                this.mTaskSupervisor.findTaskToMoveToFront(anyTaskForId, i2, safeActivityOptions != null ? safeActivityOptions.getOptions(null, null, null, this.mTaskSupervisor) : null, "moveTaskToFront", false);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void moveTaskToRootTask(int i, int i2, boolean z) {
        enforceTaskPermission("moveTaskToRootTask()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "moveTaskToRootTask: No task for id=" + i);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled[0]) {
                        ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_TASKS, -1136891560663761442L, 53, null, Long.valueOf(i), Long.valueOf(i2), Boolean.valueOf(z));
                    }
                    Task rootTask = this.mRootWindowContainer.getRootTask(i2);
                    if (rootTask == null) {
                        throw new IllegalStateException("moveTaskToRootTask: No rootTask for rootTaskId=" + i2);
                    }
                    if (rootTask.isActivityTypeStandardOrUndefined()) {
                        anyTaskForId.reparent(rootTask, z, 1, true, false, "moveTaskToRootTask");
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        throw new IllegalArgumentException("moveTaskToRootTask: Attempt to move task " + i + " to rootTask " + i2);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void notifyDedicated(int i) {
        synchronized (this.mGlobalLockWithoutBoost) {
            Slog.v("ActivityTaskManager", "notifyDedicatedState:" + i + "," + this.mCb4Task.isPresent());
            this.mCb4Task.ifPresent(new ActivityTaskManagerService$$ExternalSyntheticLambda13(this, i, 1));
        }
    }

    public final void notifyTaskPersisterLocked(Task task, boolean z) {
        this.mRecentTasks.notifyTaskPersisterLocked(task, z);
    }

    public final void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) {
        enforceTaskPermission("onPictureInPictureUiStateChanged");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task rootTask = this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().hasPinnedTask() ? this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().mRootPinnedTask : this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().getRootTask(new ActivityTaskManagerService$$ExternalSyntheticLambda6(1));
                if (rootTask != null && rootTask.getTopMostActivity() != null) {
                    ActivityRecord topMostActivity = rootTask.getTopMostActivity();
                    ActivityRecord.State state = ActivityRecord.State.FINISHING;
                    ActivityRecord.State state2 = ActivityRecord.State.DESTROYING;
                    ActivityRecord.State state3 = ActivityRecord.State.DESTROYED;
                    ActivityRecord.State state4 = topMostActivity.mState;
                    if (state != state4 && state2 != state4 && state3 != state4) {
                        this.mWindowManager.mAtmService.mActivityClientController.onPictureInPictureUiStateChanged(rootTask.getTopMostActivity(), pictureInPictureUiState);
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void onScreenAwakeChanged(boolean z) {
        WindowProcessController windowProcessController;
        this.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda2(this, z, 1));
        if (z) {
            return;
        }
        synchronized (this.mGlobalLockWithoutBoost) {
            this.mDemoteTopAppReasons &= -2;
            WindowState windowState = this.mRootWindowContainer.mDefaultDisplay.mDisplayPolicy.mNotificationShade;
            windowProcessController = windowState != null ? windowState.mSession.mProcess : null;
        }
        setProcessAnimatingWhileDozing(windowProcessController);
    }

    public final void onSplashScreenViewCopyFinished(int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
        ActivityRecord activity;
        this.mAmInternal.enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "copySplashScreenViewFinish()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                if (anyTaskForId != null && (activity = anyTaskForId.getActivity(new Task$$ExternalSyntheticLambda0(11))) != null) {
                    activity.onCopySplashScreenFinish(splashScreenViewParcelable);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void onSystemReady() {
        this.mContext.registerReceiver(this.mCooldownLevelReceiver, new IntentFilter("com.samsung.CHECK_COOLDOWN_LEVEL"), 2);
        if (CoreRune.SUPPORT_APP_JUMP_BLOCK) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiver(this.mAppJumpBlockReceiver, intentFilter);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                this.mHasHeavyWeightFeature = packageManager.hasSystemFeature("android.software.cant_save_state");
                this.mHasLeanbackFeature = packageManager.hasSystemFeature("android.software.leanback");
                this.mHasCompanionDeviceSetupFeature = packageManager.hasSystemFeature("android.software.companion_device_setup");
                VrController vrController = this.mVrController;
                vrController.getClass();
                VrManagerService.LocalService localService = (VrManagerService.LocalService) LocalServices.getService(VrManagerService.LocalService.class);
                if (localService != null) {
                    vrController.mVrService = localService;
                    VrManagerService.this.mPersistentVrStateRemoteCallbacks.register(vrController.mPersistentVrModeListener);
                }
                this.mRecentTasks.onSystemReadyLocked();
                LaunchParamsPersister launchParamsPersister = this.mTaskSupervisor.mLaunchParamsPersister;
                launchParamsPersister.getClass();
                launchParamsPersister.mPackageList = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageList(launchParamsPersister.new PackageListObserver());
                ActivityClientController activityClientController = this.mActivityClientController;
                activityClientController.getClass();
                activityClientController.mAssistUtils = new AssistUtils(activityClientController.mContext);
                this.mAppWarnings.onSystemReady();
                this.mExt.onSystemReady();
                Executor mainExecutor = this.mContext.getMainExecutor();
                ActivitySecurityModelFeatureFlags.sAsmToastsEnabled = DeviceConfig.getInt("window_manager", "ActivitySecurity__asm_toasts_enabled", 0);
                ActivitySecurityModelFeatureFlags.sAsmRestrictionsEnabled = DeviceConfig.getInt("window_manager", "ActivitySecurity__asm_restrictions_enabled", 0);
                DeviceConfig.addOnPropertiesChangedListener("window_manager", mainExecutor, new ActivitySecurityModelFeatureFlags$$ExternalSyntheticLambda0());
                this.mGrammaticalManagerInternal = (GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl) LocalServices.getService(GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl.class);
                PersonaActivityHelper personaActivityHelper = this.mPersonaActivityHelper;
                personaActivityHelper.getClass();
                new IntentFilter().addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
                personaActivityHelper.checkKnoxFeatureEnabled();
                HandlerThread handlerThread = new HandlerThread("activeLaunch");
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.samsung.DO_ACTIVE_LAUNCH");
                intentFilter2.addAction("com.samsung.DO_ACTIVE_LAUNCH_FOR_KNOX");
                intentFilter2.addAction("com.samsung.DO_ACTIVE_LAUNCH_FOR_KNOX_LAUNCHER");
                handlerThread.start();
                if (CoreRune.SYSPERF_BOOST_OPT) {
                    Process.setThreadGroup(handlerThread.getThreadId(), 10);
                }
                this.mContext.registerReceiver(new AnonymousClass3(3, this), intentFilter2, null, new Handler(handlerThread.getLooper()), 2);
                if (CoreRune.SYSFW_APP_SPEG) {
                    this.mSpeg = (SpegService) LocalServices.getService(SpegService.class);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        IntentFilter intentFilter3 = new IntentFilter("android.intent.action.PACKAGE_REPLACED");
        intentFilter3.addDataScheme("package");
        this.mContext.registerReceiver(this.mIdsReceiver, intentFilter3);
        synchronized (this.mIdsLock) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/data/system/idsFile.txt"));
                try {
                    Set set = (Set) objectInputStream.readObject();
                    if (set != null) {
                        getIdsClearSet().addAll(set);
                    }
                    objectInputStream.close();
                } catch (Throwable th2) {
                    try {
                        objectInputStream.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            } catch (Exception unused) {
                Slog.w("ActivityTaskManager", "Error reading IDS file during onSystemReady.");
            }
        }
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            logAndRethrowRuntimeExceptionOnTransact(e, "ActivityTaskManager");
            throw null;
        }
    }

    public final void registKeyEventListener(IKeyEventListener iKeyEventListener) {
        enforceTaskPermission("registKeyEventListener()");
        this.mKeyEventListener = iKeyEventListener;
    }

    public final void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteAnimationForNextActivityStart");
        remoteAnimationAdapter.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    PendingRemoteAnimationRegistry pendingRemoteAnimationRegistry = this.mActivityStartController.mPendingRemoteAnimationRegistry;
                    ArrayMap arrayMap = pendingRemoteAnimationRegistry.mEntries;
                    boolean z = CoreRune.FW_SHELL_TRANSITION_REMOTE;
                    arrayMap.put(str, pendingRemoteAnimationRegistry.new Entry(str, remoteAnimationAdapter, iBinder, null));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteAnimations");
        remoteAnimationDefinition.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.e("ActivityTaskManager", "Couldn't find display with id: " + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    displayContent.mAppTransitionController.mRemoteAnimationDefinition = remoteAnimationDefinition;
                    WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void registerRemoteTransitionForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteTransitionForNextActivityStart");
        remoteAnimationAdapter.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    PendingRemoteAnimationRegistry pendingRemoteAnimationRegistry = this.mActivityStartController.mPendingRemoteAnimationRegistry;
                    ArrayMap arrayMap = pendingRemoteAnimationRegistry.mEntries;
                    if (!CoreRune.FW_SHELL_TRANSITION_REMOTE) {
                        remoteTransition = null;
                    }
                    arrayMap.put(str, pendingRemoteAnimationRegistry.new Entry(str, remoteAnimationAdapter, iBinder, remoteTransition));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void registerScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) {
        this.mAmInternal.enforceCallingPermission("android.permission.DETECT_SCREEN_CAPTURE", "registerScreenCaptureObserver");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.registerCaptureObserver(iScreenCaptureObserver);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void registerTaskStackListener(ITaskStackListener iTaskStackListener) {
        enforceTaskPermission("registerTaskStackListener()");
        TaskChangeNotificationController taskChangeNotificationController = this.mTaskChangeNotificationController;
        taskChangeNotificationController.getClass();
        if (!(iTaskStackListener instanceof Binder)) {
            if (iTaskStackListener != null) {
                synchronized (taskChangeNotificationController.mRemoteTaskStackListeners) {
                    taskChangeNotificationController.mRemoteTaskStackListeners.register(iTaskStackListener);
                }
                return;
            }
            return;
        }
        synchronized (taskChangeNotificationController.mLocalTaskStackListeners) {
            try {
                if (!taskChangeNotificationController.mLocalTaskStackListeners.contains(iTaskStackListener)) {
                    if (iTaskStackListener instanceof TaskStackListener) {
                        ((TaskStackListener) iTaskStackListener).setIsLocal();
                    }
                    taskChangeNotificationController.mLocalTaskStackListeners.add(iTaskStackListener);
                }
            } finally {
            }
        }
    }

    public final void releaseSomeActivities(IApplicationThread iApplicationThread) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    getProcessController(iApplicationThread).releaseSomeActivities();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void removeAllVisibleRecentTasks() {
        this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeAllVisibleRecentTasks()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mRecentTasks.removeAllVisibleTasks(this.mAmInternal.getCurrentUserId());
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void removeRootTasksInWindowingModes(int[] iArr) {
        enforceTaskPermission("removeRootTasksInWindowingModes()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mRootWindowContainer.removeRootTasksInWindowingModes(iArr);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void removeRootTasksWithActivityTypes(int[] iArr) {
        enforceTaskPermission("removeRootTasksWithActivityTypes()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mRootWindowContainer.removeRootTasksWithActivityTypes(iArr);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean removeTask(int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeTask()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "removeTask: No task remove with id=" + i);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (anyTaskForId.isLeafTask()) {
                        this.mTaskSupervisor.removeTask(anyTaskForId, true, true, "remove-task");
                    } else {
                        this.mTaskSupervisor.removeRootTask(anyTaskForId);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean removeTaskWithFlags(int i, int i2) {
        boolean removeTaskWithFlagsLocked;
        this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeTask()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Slog.i("ActivityTaskManager", "removeTaskWithFlags: callingUid=" + Binder.getCallingUid() + ", callerPid=" + Binder.getCallingPid() + ", taskId=" + i + ", flags=" + i2 + Debug.getCaller());
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    removeTaskWithFlagsLocked = this.mExt.removeTaskWithFlagsLocked(i, i2);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return removeTaskWithFlagsLocked;
    }

    public final void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) {
        Bundle bundle2;
        PendingAssistExtras pendingAssistExtras = (PendingAssistExtras) iBinder;
        synchronized (pendingAssistExtras) {
            try {
                pendingAssistExtras.result = bundle;
                pendingAssistExtras.structure = assistStructure;
                pendingAssistExtras.content = assistContent;
                if (uri != null) {
                    pendingAssistExtras.extras.putParcelable("android.intent.extra.REFERRER", uri);
                }
                if (pendingAssistExtras.activity.isAttached()) {
                    if (assistStructure != null) {
                        assistStructure.setTaskId(pendingAssistExtras.activity.task.mTaskId);
                        assistStructure.setActivityComponent(pendingAssistExtras.activity.mActivityComponent);
                        assistStructure.setHomeActivity(pendingAssistExtras.isHome);
                    }
                    pendingAssistExtras.haveResult = true;
                    pendingAssistExtras.notifyAll();
                    if (pendingAssistExtras.intent == null && pendingAssistExtras.receiver == null) {
                        return;
                    }
                    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        if (bundle != null) {
                            try {
                                pendingAssistExtras.extras.putBundle("android.intent.extra.ASSIST_CONTEXT", bundle);
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        String str = pendingAssistExtras.hint;
                        if (str != null) {
                            pendingAssistExtras.extras.putBoolean(str, true);
                        }
                        boolean remove = this.mPendingAssistExtras.remove(pendingAssistExtras);
                        this.mUiHandler.removeCallbacks(pendingAssistExtras);
                        if (!remove) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        IAssistDataReceiver iAssistDataReceiver = pendingAssistExtras.receiver;
                        if (iAssistDataReceiver != null) {
                            bundle2 = new Bundle();
                            bundle2.putInt("taskId", pendingAssistExtras.activity.task.mTaskId);
                            bundle2.putBinder("activityId", pendingAssistExtras.activity.assistToken);
                            bundle2.putBundle("data", pendingAssistExtras.extras);
                            bundle2.putParcelable("structure", pendingAssistExtras.structure);
                            bundle2.putParcelable("content", pendingAssistExtras.content);
                            bundle2.putBundle("receiverExtras", pendingAssistExtras.receiverExtras);
                        } else {
                            bundle2 = null;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        if (iAssistDataReceiver != null) {
                            try {
                                iAssistDataReceiver.onHandleAssistData(bundle2);
                                return;
                            } catch (RemoteException unused) {
                                return;
                            }
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            pendingAssistExtras.intent.replaceExtras(pendingAssistExtras.extras);
                            pendingAssistExtras.intent.setFlags(872415232);
                            this.mInternal.closeSystemDialogs("assist");
                            try {
                                this.mContext.startActivityAsUser(pendingAssistExtras.intent, new UserHandle(pendingAssistExtras.userHandle));
                            } catch (ActivityNotFoundException e) {
                                Slog.w("ActivityTaskManager", "No activity to handle assist action.", e);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final boolean requestAssistContextExtras(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2) {
        return enqueueAssistContext(i, iAssistDataReceiver, bundle, iBinder, z, z2, UserHandle.getCallingUserId(), 2000L, 0) != null;
    }

    public final boolean requestAssistDataForTask(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2) {
        this.mAmInternal.enforceCallingPermission("android.permission.GET_TOP_ACTIVITY_INFO", "requestAssistDataForTask()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = this.mInternal.getAttachedNonFinishingActivityForTask(i, null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (attachedNonFinishingActivityForTask == null) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Could not find activity for task ", "ActivityTaskManager");
                return false;
            }
            AssistDataReceiverProxy assistDataReceiverProxy = new AssistDataReceiverProxy();
            assistDataReceiverProxy.mReceiver = iAssistDataReceiver;
            assistDataReceiverProxy.mCallerPackage = str;
            try {
                iAssistDataReceiver.asBinder().linkToDeath(assistDataReceiverProxy, 0);
            } catch (RemoteException e) {
                Log.w("ActivityTaskManager", "Could not link to client death", e);
            }
            AssistDataRequester assistDataRequester = new AssistDataRequester(this.mContext, this.mWindowManager, getAppOpsManager(), assistDataReceiverProxy, new Object(), -1);
            ArrayList arrayList = new ArrayList();
            arrayList.add(attachedNonFinishingActivityForTask.mActivityToken);
            assistDataRequester.requestData(arrayList, true, false, false, true, false, true, Binder.getCallingUid(), str, str2, false);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) {
        return enqueueAssistContext(2, iAssistDataReceiver, bundle, iBinder, true, true, UserHandle.getCallingUserId(), 2000L, i) != null;
    }

    public final void resetUserPackageSettings(final int i, final int i2) {
        enforceTaskPermission("resetUserPackageSettings()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Iterator it = ((ArrayList) PackagesChange.sAllPackagesChange).iterator();
            while (it.hasNext()) {
                PackageFeatureUserChange[] packageFeatureUserChangeArr = ((PackagesChange) it.next()).mUserChanges;
                if (packageFeatureUserChangeArr != null) {
                    for (PackageFeatureUserChange packageFeatureUserChange : packageFeatureUserChangeArr) {
                        int i3 = packageFeatureUserChange.mIdentityFlag;
                        if ((i2 & i3) == i3) {
                            packageFeatureUserChange.reset(i);
                        }
                    }
                }
            }
            MultiTaskingAppCompatController multiTaskingAppCompatController = this.mMultiTaskingAppCompatController;
            synchronized (multiTaskingAppCompatController) {
                try {
                    List list = multiTaskingAppCompatController.mOverridesObservers;
                    if (list != null) {
                        ((ArrayList) list).forEach(new Consumer() { // from class: com.android.server.wm.MultiTaskingAppCompatController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((MultiTaskingAppCompatController.OverridesObserver) obj).resetUserOverrides(i, i2);
                            }
                        });
                    }
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void resizeTask(int i, final Rect rect, final int i2) {
        enforceTaskPermission("resizeTask()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "resizeTask: taskId=" + i + " not found");
                    } else if (anyTaskForId.getWindowConfiguration().canResizeTask()) {
                        boolean z = (i2 & 1) != 0;
                        if (this.mWindowOrganizerController.mTransitionController.isShellTransitionsEnabled()) {
                            final Transition transition = new Transition(6, 0, this.mWindowOrganizerController.mTransitionController, this.mWindowManager.mSyncEngine);
                            final boolean z2 = z;
                            this.mWindowOrganizerController.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect(anyTaskForId, transition, rect, i2, z2) { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda7
                                public final /* synthetic */ Task f$1;
                                public final /* synthetic */ Transition f$2;
                                public final /* synthetic */ Rect f$3;
                                public final /* synthetic */ int f$4;
                                public final /* synthetic */ boolean f$5;

                                @Override // com.android.server.wm.TransitionController.OnStartCollect
                                public final void onCollectStarted(boolean z3) {
                                    Rect rect2 = this.f$3;
                                    ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                                    Task task = this.f$1;
                                    Transition transition2 = this.f$2;
                                    if (z3) {
                                        activityTaskManagerService.getClass();
                                        if (!task.getWindowConfiguration().canResizeTask()) {
                                            Slog.w("ActivityTaskManager", "resizeTask not allowed on task=" + task);
                                            transition2.abort();
                                            return;
                                        }
                                    }
                                    activityTaskManagerService.mWindowOrganizerController.mTransitionController.requestStartTransition(transition2, task, null, null);
                                    activityTaskManagerService.mWindowOrganizerController.mTransitionController.collect(task);
                                    task.resize(this.f$4, rect2);
                                    transition2.setReady(task, true);
                                }
                            });
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        anyTaskForId.resize(i2, rect);
                    } else {
                        Slog.w("ActivityTaskManager", "resizeTask not allowed on task=" + anyTaskForId);
                    }
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

    public final ActivityInfo resolveActivityInfoForIntent(int i, int i2, int i3, Intent intent, String str) {
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        return this.mAmInternal.getActivityInfoForUser(activityTaskSupervisor.resolveActivity(intent, activityTaskSupervisor.resolveIntent(intent, str, i, 0, ActivityStarter.computeResolveFilterUid(i2, i2, -10000), i3), 0, null), i);
    }

    public final void resumeAppSwitches() {
        this.mAmInternal.enforceCallingPermission("android.permission.STOP_APP_SWITCHES", "resumeAppSwitches");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAppSwitchesState = 2;
                this.mH.removeMessages(4);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void saveANRState(ActivityRecord activityRecord, String str) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 1024);
        fastPrintWriter.println("  ANR time: " + DateFormat.getDateTimeInstance().format(new Date()));
        if (str != null) {
            fastPrintWriter.println("  Reason: ".concat(str));
        }
        fastPrintWriter.println();
        if (activityRecord != null) {
            Task rootTask = activityRecord.getRootTask();
            if (rootTask != null) {
                rootTask.forAllTaskFragments(new ActivityTaskManagerService$$ExternalSyntheticLambda27(0, fastPrintWriter));
                fastPrintWriter.println();
            }
            this.mActivityStartController.dump(fastPrintWriter, "  ", activityRecord.packageName);
            ActivityStarter activityStarter = this.mActivityStartController.mLastStarter;
            if ((activityStarter != null ? activityStarter.mStartActivity : null) != activityRecord) {
                activityRecord.dump(fastPrintWriter, "  ", true);
            }
        }
        ActivityTaskSupervisor.printThisActivity(fastPrintWriter, this.mRootWindowContainer.getTopResumedActivity(), null, -1, true, "  ResumedActivity: ", null);
        this.mLockTaskController.dump(fastPrintWriter);
        this.mKeyguardController.dump(fastPrintWriter);
        fastPrintWriter.println("-------------------------------------------------------------------------------");
        fastPrintWriter.close();
        this.mLastANRState = stringWriter.toString();
    }

    public final void sendSaLoggingBroadcast(String str, String str2, String str3, long j, Map map) {
        if (CoreRune.FW_SA_LOGGING) {
            CoreSaLogger.sendSaLoggingBroadcast(this.mContext, str, str2, str3, j, map instanceof HashMap ? (HashMap) map : null, KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
        }
    }

    public final void sendSaLoggingBroadcastForSetting(String str, String str2, String str3) {
        if (CoreRune.FW_SA_LOGGING) {
            CoreSaLogger.sendSaLoggingBroadcastForSetting(this.mContext, str, str2, str3);
        }
    }

    public final void setActivityController(IActivityController iActivityController, boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "setActivityController()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mController = iActivityController;
                this.mControllerIsAMonkey = z;
                Watchdog.getInstance().setActivityController(iActivityController);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setAppLockedUnLockPackage(String str) {
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mExt;
        activityTaskManagerServiceExt.getClass();
        int callingUid = Binder.getCallingUid();
        if (ActivityTaskManagerServiceExt.isSystemUid(callingUid)) {
            AppLockPolicy appLockPolicy = activityTaskManagerServiceExt.mService.mAppLockPolicy;
            if (appLockPolicy != null) {
                appLockPolicy.setAppLockedUnLockPackage(str);
                return;
            }
            return;
        }
        throw new SecurityException(callingUid + " cannot call setAppLockedUnLockPackage(" + str + ")");
    }

    public final void setAppLockedVerifying(String str, boolean z) {
        this.mExt.setAppLockedVerifying(str, z);
    }

    public final void setApplockEnabled(boolean z) {
        this.mExt.mService.mAppLockPolicy.setApplockEnabled(z);
    }

    public final void setApplockLockedAppsClass(String str) {
        this.mExt.mService.mAppLockPolicy.setApplockLockedAppsClass(str);
    }

    public final void setApplockLockedAppsPackage(String str) {
        this.mExt.mService.mAppLockPolicy.setApplockLockedAppsPackage(str);
    }

    public final void setApplockType(int i) {
        this.mExt.mService.mAppLockPolicy.setApplockType(i);
    }

    public final int setCoverLauncherPackageDisabled(String str, int i) {
        return -100;
    }

    public final int setCoverLauncherPackageEnabled(String str, int i) {
        return -100;
    }

    public final void setCutoutPolicy(int i, String str, int i2) {
        enforceTaskPermission("setCutoutPolicy()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mExt.mDisplayCutoutController.setPolicy(i, i2, str, true);
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

    public final void setDisallowWhenLandscape(boolean z) {
        if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
            enforceTaskPermission("setDisallowWhenLandscape()");
            synchronized (this.mGlobalLockWithoutBoost) {
                this.mMultiTaskingAppCompatController.mOrientationPolicy.mShouldIgnoreLandscapeRequestDueToMultiStar = !z;
            }
        }
    }

    public final void setFocusedRootTask(int i) {
        enforceTaskPermission("setFocusedRootTask()");
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_FOCUS, -4356952232698761083L, 1, null, Long.valueOf(i));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task rootTask = this.mRootWindowContainer.getRootTask(i);
                    if (rootTask == null) {
                        Slog.w("ActivityTaskManager", "setFocusedRootTask: No task with id=" + i);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    ActivityRecord activityRecord = rootTask.topRunningActivity(false);
                    if (activityRecord != null && activityRecord.moveFocusableActivityToTop("setFocusedRootTask")) {
                        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    }
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

    public final void setFocusedTask(int i) {
        enforceTaskPermission("setFocusedTask()");
        Slog.d("ActivityTaskManager", "setFocusedTask: taskId=" + i + ", pid=" + Binder.getCallingPid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    setFocusedTask(i, null);
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

    public final void setFocusedTask(int i, ActivityRecord activityRecord) {
        ActivityRecord activityRecord2;
        TaskFragment taskFragment;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_FOCUS, 301842347780487555L, 1, null, Long.valueOf(i), String.valueOf(activityRecord));
        }
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
        if (anyTaskForId == null || (activityRecord2 = anyTaskForId.topRunningActivityLocked()) == null) {
            return;
        }
        if (CoreRune.FW_FLEX_PANEL && activityRecord2.mIsFlexPanel) {
            return;
        }
        if ((activityRecord == null || activityRecord2 == activityRecord) && activityRecord2.isState(ActivityRecord.State.RESUMED) && activityRecord2 == this.mRootWindowContainer.getTopResumedActivity()) {
            setLastResumedActivityUncheckLocked(activityRecord2, "setFocusedTask-alreadyTop");
            return;
        }
        Transition createTransition = (this.mWindowOrganizerController.mTransitionController.isCollecting() || !this.mWindowOrganizerController.mTransitionController.isShellTransitionsEnabled()) ? null : this.mWindowOrganizerController.mTransitionController.createTransition(3, 0);
        if (createTransition != null) {
            createTransition.setReady(anyTaskForId, true);
        }
        boolean moveFocusableActivityToTop = activityRecord2.moveFocusableActivityToTop("setFocusedTask");
        if (moveFocusableActivityToTop) {
            if (createTransition != null) {
                this.mWindowOrganizerController.mTransitionController.requestStartTransition(createTransition, null, null, null);
            }
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        } else if (activityRecord != null && activityRecord.isFocusable() && (taskFragment = activityRecord.getTaskFragment()) != null && taskFragment.mIsEmbedded) {
            activityRecord.getDisplayContent().setFocusedApp(activityRecord);
            this.mWindowManager.updateFocusedWindowLocked(0, true);
        }
        if (createTransition == null || moveFocusableActivityToTop) {
            return;
        }
        createTransition.abort();
    }

    public final void setFrontActivityScreenCompatMode(int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_SCREEN_COMPATIBILITY", "setFrontActivityScreenCompatMode");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                ActivityRecord activityRecord = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.topRunningActivity(false) : null;
                if (activityRecord == null) {
                    Slog.w("ActivityTaskManager", "setFrontActivityScreenCompatMode failed: no top activity");
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    this.mCompatModePackages.setPackageScreenCompatModeLocked(activityRecord.info.applicationInfo, i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setHeavyWeightProcess(ActivityRecord activityRecord) {
        this.mHeavyWeightProcess = activityRecord.app;
        this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda26(), this, activityRecord.app, activityRecord.intent, Integer.valueOf(activityRecord.mUserId)));
    }

    public final void setLastResumedActivityUncheckLocked(ActivityRecord activityRecord, String str) {
        IVoiceInteractionSession iVoiceInteractionSession;
        int i;
        Task task = activityRecord.task;
        if (task.isActivityTypeStandard()) {
            AppTimeTracker appTimeTracker = this.mCurAppTimeTracker;
            if (appTimeTracker != activityRecord.appTimeTracker) {
                if (appTimeTracker != null) {
                    appTimeTracker.stop();
                    this.mH.obtainMessage(1, this.mCurAppTimeTracker).sendToTarget();
                    RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
                    AppTimeTracker appTimeTracker2 = activityRecord.appTimeTracker;
                    rootWindowContainer.getClass();
                    rootWindowContainer.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda1(2, appTimeTracker2));
                    this.mCurAppTimeTracker = null;
                }
                AppTimeTracker appTimeTracker3 = activityRecord.appTimeTracker;
                if (appTimeTracker3 != null) {
                    this.mCurAppTimeTracker = appTimeTracker3;
                    startTimeTrackingFocusedActivityLocked();
                }
            } else {
                startTimeTrackingFocusedActivityLocked();
            }
        } else {
            activityRecord.appTimeTracker = null;
        }
        if (task.voiceInteractor != null) {
            startRunningVoiceLocked(task.voiceSession, activityRecord.info.applicationInfo.uid);
        } else {
            if (this.mRunningVoice != null) {
                this.mRunningVoice = null;
                this.mVoiceWakeLock.release();
                updateSleepIfNeededLocked();
            }
            ActivityRecord activityRecord2 = this.mLastResumedActivity;
            if (activityRecord2 != null) {
                Task task2 = activityRecord2.task;
                if (task2 == null || (iVoiceInteractionSession = task2.voiceSession) == null) {
                    iVoiceInteractionSession = activityRecord2.voiceSession;
                }
                if (iVoiceInteractionSession != null) {
                    finishVoiceTask(iVoiceInteractionSession);
                }
            }
        }
        ActivityRecord activityRecord3 = this.mLastResumedActivity;
        if (activityRecord3 != null && (i = activityRecord.mUserId) != activityRecord3.mUserId) {
            this.mAmInternal.sendForegroundProfileChanged(i);
        }
        ActivityRecord activityRecord4 = this.mLastResumedActivity;
        if (activityRecord4 != null && this.mAMBooster != null) {
            ActivityRecord.State state = activityRecord4.mState;
            ActivityRecord.State state2 = ActivityRecord.State.RESUMED;
            if (state == state2 && activityRecord.mState == state2 && activityRecord != activityRecord4) {
                boolean z = ActivityManagerPerformance.DEBUG;
                synchronized (ActivityManagerPerformance.class) {
                    try {
                        if (ActivityManagerPerformance.AMP_ENABLE && ActivityManagerPerformance.curTopAct != activityRecord) {
                            if (ActivityManagerPerformance.DEBUG) {
                                Slog.d("ActivityManagerPerformance", "notifyMultiWindowChanged() focus changed\n[Activity] prev: " + ActivityManagerPerformance.curTopAct + ", cur: " + activityRecord);
                                if (ActivityManagerPerformance.DEBUG_TRACE) {
                                    new Exception().printStackTrace();
                                }
                            }
                            ActivityManagerPerformance.curTopAct = activityRecord;
                        }
                    } finally {
                    }
                }
            }
        }
        ActivityRecord activityRecord5 = this.mLastResumedActivity;
        Task task3 = activityRecord5 != null ? activityRecord5.task : null;
        updateResumedAppTrace(activityRecord);
        this.mLastResumedActivity = activityRecord;
        Transition transition = this.mWindowOrganizerController.mTransitionController.mCollectingTransition;
        boolean z2 = false;
        if (!(transition != null && transition.isTransientLaunch(activityRecord))) {
            boolean focusedApp = activityRecord.mDisplayContent.setFocusedApp(activityRecord);
            if (focusedApp) {
                this.mWindowManager.updateFocusedWindowLocked(0, true);
            } else if (!task.equals(task3)) {
                if (task3 != null) {
                    TaskChangeNotificationController taskChangeNotificationController = this.mTaskChangeNotificationController;
                    Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(24, task3.mTaskId, 0);
                    taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskFocusChanged, obtainMessage);
                    obtainMessage.sendToTarget();
                }
                TaskChangeNotificationController taskChangeNotificationController2 = this.mTaskChangeNotificationController;
                Message obtainMessage2 = taskChangeNotificationController2.mHandler.obtainMessage(24, task.mTaskId, 1);
                taskChangeNotificationController2.forAllLocalListeners(taskChangeNotificationController2.mNotifyTaskFocusChanged, obtainMessage2);
                obtainMessage2.sendToTarget();
            }
            z2 = focusedApp;
        }
        if ((!CoreRune.SYSFW_APP_SPEG || (activityRecord.mDisplayContent.mDisplayInfo.flags & 32768) == 0) && task != task3) {
            this.mTaskSupervisor.mRecentTasks.add(task);
        }
        task.lastGainFocusTime = SystemClock.elapsedRealtime();
        if (z2) {
            this.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda16(this, activityRecord.immersive, activityRecord));
        }
        if (this.mVrController.mVrService != null) {
            applyUpdateVrModeLocked(activityRecord);
        }
        EventLog.writeEvent(30043, Integer.valueOf(activityRecord.mUserId), activityRecord.shortComponentName, str);
        PersonaActivityHelper personaActivityHelper = this.mPersonaActivityHelper;
        if (personaActivityHelper.checkKnoxFeatureEnabled()) {
            personaActivityHelper.mLastReceivedResumedActivity = activityRecord;
            if (SemPersonaManager.isSecureFolderId(activityRecord.mUserId) || personaActivityHelper.isLockSecureFolderExceptionalCase(activityRecord.shortComponentName)) {
                personaActivityHelper.mLastResumedSFActivity = activityRecord;
            }
            PersonaActivityHelper.PersonaActivityHandler personaActivityHandler = personaActivityHelper.mPersonaActivityHandler;
            personaActivityHandler.removeMessages(FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED);
            Message obtainMessage3 = personaActivityHandler.obtainMessage(FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED);
            obtainMessage3.obj = activityRecord;
            personaActivityHandler.sendMessage(obtainMessage3);
        }
        if (CoreRune.FW_APPLOCK) {
            this.mExt.startAppLockActivity(activityRecord);
        }
    }

    public final void setLockScreenShown(final boolean z, final boolean z2) {
        if (checkCallingPermission("android.permission.DEVICE_POWER") != 0) {
            throw new SecurityException("Requires permission android.permission.DEVICE_POWER");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (this.mKeyguardShown != z) {
                    this.mKeyguardShown = z;
                    this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda0(0), this.mAmInternal, Boolean.valueOf(z)));
                }
                if ((this.mDemoteTopAppReasons & 1) != 0) {
                    this.mDemoteTopAppReasons &= -2;
                    if (this.mTopApp != null) {
                        WindowProcessController windowProcessController = this.mTopApp;
                        H h = windowProcessController.mAtm.mH;
                        WindowProcessController$$ExternalSyntheticLambda2 windowProcessController$$ExternalSyntheticLambda2 = new WindowProcessController$$ExternalSyntheticLambda2();
                        Boolean bool = Boolean.FALSE;
                        h.sendMessage(PooledLambda.obtainMessage(windowProcessController$$ExternalSyntheticLambda2, windowProcessController.mListener, bool, bool, Boolean.TRUE));
                    }
                }
                try {
                    Trace.traceBegin(32L, "setLockScreenShown");
                    this.mRootWindowContainer.forAllDisplays(new Consumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda1
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r0v17 */
                        /* JADX WARN: Type inference failed for: r14v0 */
                        /* JADX WARN: Type inference failed for: r14v1 */
                        /* JADX WARN: Type inference failed for: r14v2 */
                        /* JADX WARN: Type inference failed for: r15v0 */
                        /* JADX WARN: Type inference failed for: r15v1 */
                        /* JADX WARN: Type inference failed for: r15v2 */
                        /* JADX WARN: Type inference failed for: r16v0 */
                        /* JADX WARN: Type inference failed for: r16v1 */
                        /* JADX WARN: Type inference failed for: r16v2 */
                        /* JADX WARN: Type inference failed for: r2v20 */
                        /* JADX WARN: Type inference failed for: r2v27 */
                        /* JADX WARN: Type inference failed for: r2v6 */
                        /* JADX WARN: Type inference failed for: r2v7 */
                        /* JADX WARN: Type inference failed for: r8v1 */
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ActivityRecord activityRecord;
                            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                            boolean z3 = z;
                            boolean z4 = z2;
                            KeyguardController keyguardController = activityTaskManagerService.mKeyguardController;
                            int i = ((DisplayContent) obj).mDisplayId;
                            DisplayContent displayContent = keyguardController.mRootWindowContainer.getDisplayContent(i);
                            if (displayContent == null) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "setKeyguardShown called on non-existent display ", "ActivityTaskManager");
                                return;
                            }
                            if ((displayContent.mDisplayInfo.flags & 512) != 0) {
                                HermesService$3$$ExternalSyntheticOutline0.m(i, "setKeyguardShown ignoring always unlocked display ", "ActivityTaskManager");
                                return;
                            }
                            KeyguardController.KeyguardDisplayState displayState = keyguardController.getDisplayState(i);
                            if (displayState.mWakeAndUnlock && z3 == z4) {
                                displayState.mWakeAndUnlock = false;
                            }
                            boolean z5 = displayState.mAodShowing;
                            ?? r14 = z4 != z5;
                            ?? r2 = z5 && !z4;
                            ?? r15 = displayState.mKeyguardGoingAway && z3;
                            ?? r16 = !(z3 == displayState.mKeyguardShowing || displayState.mWakeAndUnlock) || (r15 == true && !r2 == true);
                            if (r2 != false) {
                                keyguardController.updateDeferTransitionForAod(false);
                            }
                            if (r16 != true && r14 != true) {
                                keyguardController.setWakeTransitionReady();
                                return;
                            }
                            EventLogTags.writeWmSetKeyguardShown(i, z3 ? 1 : 0, z4 ? 1 : 0, displayState.mKeyguardGoingAway ? 1 : 0, displayState.mOccluded ? 1 : 0, "setKeyguardShown");
                            if (i == 0) {
                                if ((((z4 ? 1 : 0) ^ (z3 ? 1 : 0)) != 0 || (z4 && r14 != false && r16 != false)) && !displayState.mKeyguardGoingAway && Display.isOnState(displayContent.mDisplayInfo.state)) {
                                    keyguardController.mWindowManager.mTaskSnapshotController.snapshotForSleeping(0);
                                }
                            }
                            displayState.mKeyguardShowing = z3;
                            displayState.mAodShowing = z4;
                            if (z3 && keyguardController.mTaskSupervisor.mService.mDexController.getDexModeLocked() == 2) {
                                DisplayPolicy displayPolicy = keyguardController.mWindowManager.getDefaultDisplayContentLocked().mDisplayPolicy;
                                if (displayPolicy.mNotificationShade != null) {
                                    Slog.d("WindowManager", "set ControlTarget to NotificationShade=" + displayPolicy.mNotificationShade);
                                    displayPolicy.mDisplayContent.mInsetsPolicy.updateBarControlTarget(displayPolicy.mNotificationShade);
                                }
                            }
                            if (i == 0 && r16 != false && z3 && (activityRecord = displayState.mTopOccludesActivity) != null && activityRecord.inFreeformWindowingMode()) {
                                Task rootTask = displayContent.getRootTask(1, 0);
                                WindowState topVisibleAppMainWindow = rootTask != null ? rootTask.getTopVisibleAppMainWindow(false) : null;
                                if (topVisibleAppMainWindow != null && !topVisibleAppMainWindow.hasWallpaper()) {
                                    displayContent.mWallpaperController.adjustWallpaperWindows();
                                }
                            }
                            if (r16 == true) {
                                displayState.mKeyguardGoingAway = false;
                                if (z3) {
                                    displayState.mDismissalRequested = false;
                                }
                                if (r15 != false || (com.android.window.flags.Flags.keyguardAppearTransition() && z3 && !Display.isOffState(displayContent.mDisplayInfo.state))) {
                                    DisplayContent displayContent2 = com.android.window.flags.Flags.keyguardAppearTransition() ? displayContent : keyguardController.mRootWindowContainer.mDefaultDisplay;
                                    displayContent2.prepareAppTransition(3, 2048);
                                    displayContent2.mTransitionController.requestTransitionIfNeeded(3, 2048, null, displayContent2);
                                    if (com.android.window.flags.Flags.keyguardAppearTransition()) {
                                        displayContent.mWallpaperController.adjustWallpaperWindows();
                                    }
                                    displayContent2.executeAppTransition();
                                }
                            } else if (CoreRune.FW_SHELL_TRANSITION_AOD_APPEAR && r14 != false && z4) {
                                displayContent.prepareAppTransition(3, 264192);
                                displayContent.mTransitionController.requestTransitionIfNeeded(3, 264192, null, displayContent);
                                displayContent.mWallpaperController.adjustWallpaperWindows();
                                displayContent.executeAppTransition();
                            }
                            keyguardController.updateKeyguardSleepToken();
                            keyguardController.mRootWindowContainer.ensureActivitiesVisible();
                            InputMethodManagerInternal.get().updateImeWindowStatus(false);
                            keyguardController.setWakeTransitionReady();
                            if (r14 == true) {
                                keyguardController.mWindowManager.mWindowPlacerLocked.performSurfacePlacement(false);
                            }
                        }
                    });
                    maybeHideLockedProfileActivityLocked();
                    Trace.traceEnd(32L);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && z) {
                        MultiWindowFoldController multiWindowFoldController = this.mMultiWindowFoldController;
                        if (multiWindowFoldController.mH.hasMessages(1)) {
                            multiWindowFoldController.mH.removeMessages(1);
                        }
                    }
                } catch (Throwable th) {
                    Trace.traceEnd(32L);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        this.mH.post(new ActivityTaskManagerService$$ExternalSyntheticLambda2(this, z, 0));
    }

    public final Pair setLongLiveTask(int i, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
                if (anyTaskForId == null) {
                    Pair pair = new Pair(null, -1);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return pair;
                }
                this.mRecentTasks.dedicateTo(anyTaskForId, z);
                WindowProcessController windowProcessController = anyTaskForId.mRootProcess;
                if (windowProcessController != null) {
                    anyTaskForId.mHostProcessName = windowProcessController.mName;
                }
                Pair pair2 = new Pair(anyTaskForId.mHostProcessName, Integer.valueOf(anyTaskForId.mUserId));
                WindowManagerService.resetPriorityAfterLockedSection();
                return pair2;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setOrientationControlDefault(boolean z) {
        if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
            enforceTaskPermission("setOrientationControlDefault()");
            this.mMultiTaskingAppCompatController.mOrientationOverrides.setDefaultEnabled(z);
        }
    }

    public final void setOrientationControlPolicy(final int i, final String str, int i2) {
        if (!CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY || str == null) {
            return;
        }
        enforceTaskPermission("setOrientationControlPolicy()");
        MultiTaskingAppCompatOrientationOverrides multiTaskingAppCompatOrientationOverrides = this.mMultiTaskingAppCompatController.mOrientationOverrides;
        synchronized (multiTaskingAppCompatOrientationOverrides) {
            multiTaskingAppCompatOrientationOverrides.mUserOverride.putValue(str, i, Integer.valueOf(i2));
        }
        final MultiTaskingAppCompatController multiTaskingAppCompatController = this.mMultiTaskingAppCompatController;
        WindowManagerGlobalLock windowManagerGlobalLock = multiTaskingAppCompatController.mAtmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                multiTaskingAppCompatController.mAtmService.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.MultiTaskingAppCompatController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ComponentName componentName;
                        MultiTaskingAppCompatController multiTaskingAppCompatController2 = MultiTaskingAppCompatController.this;
                        int i3 = i;
                        String str2 = str;
                        Task task = (Task) obj;
                        multiTaskingAppCompatController2.getClass();
                        if (i3 == task.mUserId && (componentName = task.realActivity) != null && str2.equals(componentName.getPackageName())) {
                            multiTaskingAppCompatController2.onOverridesChanged(i3, task, str2, true);
                        }
                    }
                });
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setPackageAskScreenCompat(String str, boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_SCREEN_COMPATIBILITY", "setPackageAskScreenCompat");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                CompatModePackages compatModePackages = this.mCompatModePackages;
                int packageFlags = compatModePackages.getPackageFlags(str);
                int i = z ? packageFlags & (-2) : packageFlags | 1;
                if (packageFlags != i) {
                    if (i != 0) {
                        compatModePackages.mPackages.put(str, Integer.valueOf(i));
                    } else {
                        compatModePackages.mPackages.remove(str);
                    }
                    compatModePackages.scheduleWrite();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setPackageScreenCompatMode(String str, int i) {
        ApplicationInfo applicationInfo;
        this.mAmInternal.enforceCallingPermission("android.permission.SET_SCREEN_COMPATIBILITY", "setPackageScreenCompatMode");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                CompatModePackages compatModePackages = this.mCompatModePackages;
                compatModePackages.getClass();
                try {
                    applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 0L, 0);
                } catch (RemoteException unused) {
                    applicationInfo = null;
                }
                if (applicationInfo == null) {
                    Slog.w("ActivityTaskManager", "setPackageScreenCompatMode failed: unknown package " + str);
                } else {
                    compatModePackages.setPackageScreenCompatModeLocked(applicationInfo, i);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setPersistentVrThread(int i) {
        if (checkCallingPermission("android.permission.RESTRICTED_VR_ACCESS") != 0) {
            throw new SecurityException(ActivityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Permission Denial: setPersistentVrThread() from pid="), ", uid=", " requires android.permission.RESTRICTED_VR_ACCESS", "ActivityTaskManager"));
        }
        enforceSystemHasVrFeature();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int callingPid = Binder.getCallingPid();
                WindowProcessController process = this.mProcessMap.getProcess(callingPid);
                VrController vrController = this.mVrController;
                if (!((vrController.mVrState & 2) != 0)) {
                    Slog.w("VrController", "Persistent VR thread may only be set in persistent VR mode!");
                } else if (process == null) {
                    Slog.w("VrController", "Persistent VR thread not set, calling process doesn't exist!");
                } else {
                    if (i != 0 && !Process.isThreadInProcess(callingPid, i)) {
                        throw new IllegalArgumentException("VR thread does not belong to process");
                    }
                    if ((vrController.mVrState & 2) != 0) {
                        vrController.updateVrRenderThreadLocked(i, false);
                    } else {
                        Slog.w("VrController", "Failed to set persistent VR thread, system not in persistent VR mode.");
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setProcessAnimatingWhileDozing(WindowProcessController windowProcessController) {
        if (windowProcessController == null) {
            return;
        }
        windowProcessController.mListener.setRunningRemoteAnimation(true);
        H h = this.mH;
        h.sendMessage(h.obtainMessage(5, windowProcessController));
        this.mH.removeMessages(6, windowProcessController);
        H h2 = this.mH;
        h2.sendMessageDelayed(h2.obtainMessage(6, windowProcessController), 2000L);
        Trace.instant(32L, "requestWakefulnessAnimating");
    }

    public void setRecentTasks(RecentTasks recentTasks) {
        this.mRecentTasks = recentTasks;
        ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        RecentTasks recentTasks2 = activityTaskSupervisor.mRecentTasks;
        if (recentTasks2 != null) {
            recentTasks2.mCallbacks.remove(activityTaskSupervisor);
        }
        activityTaskSupervisor.mRecentTasks = recentTasks;
        recentTasks.mCallbacks.add(activityTaskSupervisor);
    }

    public final void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) {
        TransitionController.RemotePlayer.DelegateProcess delegateProcess;
        TransitionController transitionController = this.mWindowOrganizerController.mTransitionController;
        if (iApplicationThread != null) {
            TransitionController.RemotePlayer remotePlayer = transitionController.mRemotePlayer;
            synchronized (remotePlayer.mDelegateProcesses) {
                try {
                    delegateProcess = (TransitionController.RemotePlayer.DelegateProcess) remotePlayer.mDelegateProcesses.get(iApplicationThread.asBinder());
                    if (delegateProcess != null && delegateProcess.mNeedReport) {
                        delegateProcess.mNeedReport = false;
                        remotePlayer.mAtm.mH.removeCallbacks(delegateProcess);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (delegateProcess != null) {
                return;
            }
        }
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "setRunningRemoteTransition");
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowProcessController processController = getProcessController(callingPid, callingUid);
                if (processController == null || (processController.mAnimatingReasons & 1) == 0) {
                    String str = "Can't call setRunningRemoteTransition from a process (pid=" + callingPid + " uid=" + callingUid + ") which isn't itself running a remote transition.";
                    Slog.e("ActivityTaskManager", str);
                    throw new SecurityException(str);
                }
                WindowProcessController processController2 = getProcessController(iApplicationThread);
                if (processController2 != null) {
                    transitionController.mRemotePlayer.update(processController2, true, false);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    Slog.w("ActivityTaskManager", "setRunningRemoteTransition: no process for " + iApplicationThread);
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
    }

    public final void setSsecureHiddenAppsPackages(String str) {
        this.mExt.mService.mAppLockPolicy.setSsecureHiddenAppsPackages(str);
    }

    public final void setTaskResizeable(int i, int i2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
                if (anyTaskForId == null) {
                    Slog.w("ActivityTaskManager", "setTaskResizeable: taskId=" + i + " not found");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (anyTaskForId.mResizeMode != i2) {
                    anyTaskForId.mResizeMode = i2;
                    anyTaskForId.mRootWindowContainer.ensureActivitiesVisible();
                    anyTaskForId.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    anyTaskForId.updateTaskDescription$1();
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setUseLetterbox(boolean z) {
        int i;
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
            enforceTaskPermission("setUseLetterbox()");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (z) {
                i = 2;
            } else {
                try {
                    i = MultiTaskingAppCompatConfiguration.Preset.DEFAULT;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    updateAppCompatConfigurationPreset(i);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                IVoiceInteractionSession iVoiceInteractionSession2 = this.mRunningVoice;
                if (iVoiceInteractionSession2 != null && iVoiceInteractionSession2.asBinder() == iVoiceInteractionSession.asBinder()) {
                    if (z) {
                        this.mVoiceWakeLock.acquire();
                    } else {
                        this.mVoiceWakeLock.release();
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setVrThread(int i) {
        enforceSystemHasVrFeature();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int callingPid = Binder.getCallingPid();
                WindowProcessController process = this.mProcessMap.getProcess(callingPid);
                VrController vrController = this.mVrController;
                boolean z = true;
                if ((vrController.mVrState & 2) != 0) {
                    Slog.w("VrController", "VR thread cannot be set in persistent VR mode!");
                } else if (process == null) {
                    Slog.w("VrController", "Persistent VR thread not set, calling process doesn't exist!");
                } else {
                    if (i != 0 && !Process.isThreadInProcess(callingPid, i)) {
                        throw new IllegalArgumentException("VR thread does not belong to process");
                    }
                    if ((vrController.mVrState & 1) == 0) {
                        z = false;
                    }
                    if (z) {
                        vrController.setVrRenderThreadLocked(i, process.mCurSchedGroup, false);
                    } else {
                        Slog.w("VrController", "VR thread cannot be set when not in VR mode!");
                    }
                    if (i <= 0) {
                        i = 0;
                    }
                    process.mVrThreadTid = i;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setWindowManager(WindowManagerService windowManagerService) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWindowManager = windowManagerService;
                this.mRootWindowContainer = windowManagerService.mRoot;
                TransitionController transitionController = this.mWindowOrganizerController.mTransitionController;
                transitionController.getClass();
                transitionController.mSnapshotController = windowManagerService.mSnapshotController;
                transitionController.mTransitionTracer = windowManagerService.mTransitionTracer;
                transitionController.mIsWaitingForDisplayEnabled = !windowManagerService.mDisplayEnabled;
                transitionController.registerLegacyListener(windowManagerService.mActivityManagerAppTransitionNotifier);
                transitionController.setSyncEngine(windowManagerService.mSyncEngine);
                transitionController.mFullReadyTracking = com.android.window.flags.Flags.transitReadyTracking();
                this.mLifecycleManager.mWms = windowManagerService;
                this.mTempConfig.setToDefaults();
                this.mTempConfig.setLocales(LocaleList.getDefault());
                Configuration configuration = this.mTempConfig;
                configuration.seq = 1;
                this.mConfigurationSeq = 1;
                this.mRootWindowContainer.onConfigurationChanged(configuration);
                this.mLockTaskController.mWindowManager = windowManagerService;
                ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
                activityTaskSupervisor.mWindowManager = windowManagerService;
                KeyguardController keyguardController = activityTaskSupervisor.mKeyguardController;
                keyguardController.mWindowManager = windowManagerService;
                keyguardController.mRootWindowContainer = keyguardController.mService.mRootWindowContainer;
                this.mRootWindowContainer.setWindowManager(windowManagerService);
                BackNavigationController backNavigationController = this.mBackNavigationController;
                backNavigationController.mWindowManagerService = windowManagerService;
                backNavigationController.mAnimationHandler = new BackNavigationController.AnimationHandler(windowManagerService);
                Iterator it = this.mMWControllers.iterator();
                while (it.hasNext()) {
                    ((IController) it.next()).setWindowManager(windowManagerService);
                }
                PersonaActivityHelper personaActivityHelper = this.mPersonaActivityHelper;
                synchronized (personaActivityHelper.mService) {
                    personaActivityHelper.mRootWindowContainer = windowManagerService.mRoot;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final int startActivities(IApplicationThread iApplicationThread, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, Bundle bundle, int i) {
        Intent createAppBlockIntent;
        assertPackageMatchesCallingUid(str);
        enforceNotIsolatedCaller("startActivities");
        int handleIncomingUser = handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, "startActivities");
        if (!CoreRune.SUPPORT_APP_JUMP_BLOCK || (createAppBlockIntent = AppJumpBlockTool.createAppBlockIntent(this.mUiContext, str, Binder.getCallingPid(), Binder.getCallingUid(), Arrays.asList(intentArr), bundle)) == null) {
            return this.mActivityStartController.startActivities(iApplicationThread, -1, 0, -1, str, str2, intentArr, strArr, iBinder, SafeActivityOptions.fromBundle(bundle), handleIncomingUser, "startActivities", null, BackgroundStartPrivileges.NONE);
        }
        this.mActivityStartController.startActivities(iApplicationThread, -1, 0, -1, str, str2, new Intent[]{createAppBlockIntent}, new String[]{createAppBlockIntent.resolveTypeIfNeeded(this.mContext.getContentResolver())}, iBinder, SafeActivityOptions.fromBundle(bundle), handleIncomingUser, "startActivities", null, BackgroundStartPrivileges.NONE);
        return -103;
    }

    public final int startActivity(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) {
        ActivityOptions fromBundle;
        if ("VZW".equals(SystemProperties.get("ro.csc.sales_code")) && intent != null && intent.getComponent() != null && "android.intent.action.MAIN".equals(intent.getAction()) && bundle != null && (fromBundle = ActivityOptions.fromBundle(bundle)) != null && !fromBundle.isActiveApplaunch() && fromBundle.isMlLaunch() == -1) {
            SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
            if (semWifiManager != null) {
                Log.d("ActivityTaskManager", "start wifioffload check");
                semWifiManager.checkAppForWiFiOffloading(intent.getComponent().getPackageName());
            }
        }
        return startActivityAsUser(iApplicationThread, str, str2, intent, str3, iBinder, str4, i, i2, profilerInfo, bundle, UserHandle.getCallingUserId(), true);
    }

    public final WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) {
        assertPackageMatchesCallingUid(str);
        WaitResult waitResult = new WaitResult();
        enforceNotIsolatedCaller("startActivityAndWait");
        int handleIncomingUser = handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i3, "startActivityAndWait");
        ActivityStarter obtainStarter = this.mActivityStartController.obtainStarter(intent, "startActivityAndWait");
        ActivityStarter.Request request = obtainStarter.mRequest;
        request.caller = iApplicationThread;
        request.callingPackage = str;
        request.callingFeatureId = str2;
        request.resolvedType = str3;
        request.resultTo = iBinder;
        request.resultWho = str4;
        request.requestCode = i;
        request.startFlags = i2;
        obtainStarter.setActivityOptions(bundle);
        ActivityStarter.Request request2 = obtainStarter.mRequest;
        request2.userId = handleIncomingUser;
        request2.profilerInfo = profilerInfo;
        request2.waitResult = waitResult;
        obtainStarter.execute();
        return waitResult;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
    
        if (r6.getComponent() == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0076, code lost:
    
        if (r6.getSelector() != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0080, code lost:
    
        throw new java.lang.SecurityException("Selector not allowed with ignoreTargetSecurity");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0088, code lost:
    
        throw new java.lang.SecurityException("Component must be specified with ignoreTargetSecurity");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int startActivityAsCaller(android.app.IApplicationThread r4, java.lang.String r5, android.content.Intent r6, java.lang.String r7, android.os.IBinder r8, java.lang.String r9, int r10, int r11, android.app.ProfilerInfo r12, android.os.Bundle r13, boolean r14, int r15) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskManagerService.startActivityAsCaller(android.app.IApplicationThread, java.lang.String, android.content.Intent, java.lang.String, android.os.IBinder, java.lang.String, int, int, android.app.ProfilerInfo, android.os.Bundle, boolean, int):int");
    }

    public final int startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) {
        return startActivityAsUser(iApplicationThread, str, str2, intent, str3, iBinder, str4, i, i2, profilerInfo, bundle, i3, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int startActivityAsUser(android.app.IApplicationThread r24, java.lang.String r25, java.lang.String r26, android.content.Intent r27, java.lang.String r28, android.os.IBinder r29, java.lang.String r30, int r31, int r32, android.app.ProfilerInfo r33, android.os.Bundle r34, int r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 615
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskManagerService.startActivityAsUser(android.app.IApplicationThread, java.lang.String, java.lang.String, android.content.Intent, java.lang.String, android.os.IBinder, java.lang.String, int, int, android.app.ProfilerInfo, android.os.Bundle, int, boolean):int");
    }

    public final void startActivityForCoverLauncher(Intent intent, String str) {
        startActivityForCoverLauncherAsUser(intent, str, this.mContext.getUserId());
    }

    public final void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) {
        if (i != UserHandle.getCallingUserId()) {
            this.mAmInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "startActivityForCoverLauncher");
        }
        if (intent == null) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Intent for startActivityForCoverLauncher is null, requestReason=", str, "ActivityTaskManager");
            return;
        }
        StringBuilder sb = new StringBuilder("Launch application for CoverLauncher : ");
        sb.append(intent);
        sb.append(" for user=");
        sb.append(i);
        sb.append(", requestReason=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str, "ActivityTaskManager");
        if (this.mWindowManager.isKeyguardLocked() && this.mWindowManager.isKeyguardSecure(this.mAmInternal.getCurrentUserId())) {
            throw null;
        }
        this.mWindowManager.dismissKeyguard(null, null);
        throw null;
    }

    public final void startActivityForDexRestart(int i) {
        enforceTaskPermission("startActivityForDexRestart");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        Slog.w("ActivityTaskManager", "startActivityForDexRestart : invalid task");
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        DexCompatController dexCompatController = this.mDexCompatController;
                        dexCompatController.getClass();
                        dexCompatController.scheduleStartActivityAsToggleFreeform(anyTaskForId, new DexCompatController$$ExternalSyntheticLambda3(), new DexCompatController$$ExternalSyntheticLambda1(1, anyTaskForId), new DexCompatController$$ExternalSyntheticLambda2(1));
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

    public final int startActivityFromGameSession(IApplicationThread iApplicationThread, String str, String str2, int i, int i2, Intent intent, int i3, int i4) {
        if (checkCallingPermission("android.permission.MANAGE_GAME_ACTIVITY") != 0) {
            throw new SecurityException(ActivityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Permission Denial: startActivityFromGameSession() from pid="), ", uid=", " requires android.permission.MANAGE_GAME_ACTIVITY", "ActivityTaskManager"));
        }
        assertPackageMatchesCallingUid(str);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchTaskId(i3);
        int handleIncomingUser = handleIncomingUser(i, i2, i4, "startActivityFromGameSession");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ActivityStarter obtainStarter = this.mActivityStartController.obtainStarter(intent, "startActivityFromGameSession");
            ActivityStarter.Request request = obtainStarter.mRequest;
            request.caller = iApplicationThread;
            request.callingUid = i2;
            request.callingPid = i;
            String str3 = intent.getPackage();
            ActivityStarter.Request request2 = obtainStarter.mRequest;
            request2.callingPackage = str3;
            request2.callingFeatureId = str2;
            request2.userId = handleIncomingUser;
            obtainStarter.setActivityOptions(makeBasic.toBundle());
            obtainStarter.mRequest.realCallingUid = Binder.getCallingUid();
            return obtainStarter.execute();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int startActivityFromRecents(int i, Bundle bundle) {
        this.mAmInternal.enforceCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "startActivityFromRecents()");
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(bundle);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mTaskSupervisor.startActivityFromRecents(callingPid, callingUid, i, fromBundle, false);
            } catch (SecurityException e) {
                Slog.w("ActivityTaskManager", "startActivity: reason=startActivityFromRecents", e);
                throw e;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String str, IBinder iBinder2, String str2, int i, int i2, int i3, Bundle bundle) {
        enforceNotIsolatedCaller("startActivityIntentSender");
        if (intent != null) {
            if (intent.hasFileDescriptors()) {
                throw new IllegalArgumentException("File descriptors passed in Intent");
            }
            intent.removeExtendedFlags(1);
        }
        if (!(iIntentSender instanceof PendingIntentRecord)) {
            throw new IllegalArgumentException("Bad PendingIntent object");
        }
        PendingIntentRecord pendingIntentRecord = (PendingIntentRecord) iIntentSender;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                if (topDisplayFocusedRootTask != null && topDisplayFocusedRootTask.getTopResumedActivity() != null && topDisplayFocusedRootTask.getTopResumedActivity().info.applicationInfo.uid == Binder.getCallingUid()) {
                    this.mAppSwitchesState = 2;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return pendingIntentRecord.sendInner(iApplicationThread, 0, intent, str, iBinder, null, null, iBinder2, str2, i, i2, i3, bundle, -1, -1);
    }

    public final int startActivityWithConfig(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, Configuration configuration, Bundle bundle, int i3) {
        assertPackageMatchesCallingUid(str);
        enforceNotIsolatedCaller("startActivityWithConfig");
        int handleIncomingUser = handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i3, "startActivityWithConfig");
        ActivityStarter obtainStarter = this.mActivityStartController.obtainStarter(intent, "startActivityWithConfig");
        ActivityStarter.Request request = obtainStarter.mRequest;
        request.caller = iApplicationThread;
        request.callingPackage = str;
        request.callingFeatureId = str2;
        request.resolvedType = str3;
        request.resultTo = iBinder;
        request.resultWho = str4;
        request.requestCode = i;
        request.startFlags = i2;
        request.globalConfig = configuration;
        obtainStarter.setActivityOptions(bundle);
        obtainStarter.mRequest.userId = handleIncomingUser;
        return obtainStarter.execute();
    }

    public final void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) {
        boolean[] zArr;
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mExt;
        ActivityTaskManagerService activityTaskManagerService = activityTaskManagerServiceExt.mService;
        if (activityTaskManagerService.mAppLockPolicy == null) {
            return;
        }
        try {
            zArr = activityTaskManagerServiceExt.getAppLockLaunchingState(iBinder);
        } catch (Exception e) {
            Log.e("ActivityTaskManagerServiceExt", "exception while querying AppLock Launching State", e);
            zArr = null;
        }
        if (zArr != null) {
            activityTaskManagerServiceExt.mAppLockIsInMultiWindowMode = zArr[0];
            boolean z2 = zArr[1];
            boolean z3 = zArr[2];
            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
            boolean z4 = forTokenLocked != null && forTokenLocked.getDisplayId() == 1;
            if (forTokenLocked == null || !activityTaskManagerService.mAppLockPolicy.isAppLockBypassList(forTokenLocked.info.name)) {
                if (activityTaskManagerService.mKeyguardController.isKeyguardLocked(0) && z3) {
                    return;
                }
                if (z3 || z2 || activityTaskManagerServiceExt.mAppLockIsInMultiWindowMode) {
                    int callingUid = Binder.getCallingUid();
                    if (SemPersonaManager.isKnoxId(UserHandle.getUserId(callingUid)) || activityTaskManagerService.mAppLockPolicy.isManagedProfileUserId(UserHandle.getUserId(callingUid))) {
                        return;
                    }
                    if ((SemDualAppManager.isDualAppId(UserHandle.getUserId(callingUid)) && AppLockPolicy.isSupportSSecure()) || z4) {
                        return;
                    }
                    activityTaskManagerServiceExt.checkAppLockState(intent, z, str, z2);
                }
            }
        }
    }

    public final int startAssistantActivity(String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3) {
        assertPackageMatchesCallingUid(str);
        this.mAmInternal.enforceCallingPermission("android.permission.BIND_VOICE_INTERACTION", "startAssistantActivity()");
        int handleIncomingUser = handleIncomingUser(i, i2, i3, "startAssistantActivity");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ActivityStarter obtainStarter = this.mActivityStartController.obtainStarter(intent, "startAssistantActivity");
            ActivityStarter.Request request = obtainStarter.mRequest;
            request.callingUid = i2;
            request.callingPackage = str;
            request.callingFeatureId = str2;
            request.resolvedType = str3;
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
            if (fromBundle == null) {
                fromBundle = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
            } else if (fromBundle.getPendingIntentBackgroundActivityStartMode() == 0) {
                fromBundle.setPendingIntentBackgroundActivityStartMode(1);
            }
            SafeActivityOptions safeActivityOptions = new SafeActivityOptions(fromBundle);
            ActivityStarter.Request request2 = obtainStarter.mRequest;
            request2.activityOptions = safeActivityOptions;
            request2.userId = handleIncomingUser;
            request2.forcedBalByPiSender = BackgroundStartPrivileges.ALLOW_BAL;
            return obtainStarter.execute();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final BackNavigationInfo startBackNavigation(RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) {
        this.mAmInternal.enforceCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "startBackNavigation()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mBackNavigationController.startBackNavigation(remoteCallback, backAnimationAdapter);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startLockTaskMode(Task task, boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled[3]) {
            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, 6954122272402912822L, 0, null, String.valueOf(task));
        }
        if (task == null || task.mLockTaskAuth == 0) {
            return;
        }
        Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null || task != topDisplayFocusedRootTask.getTopMostTask()) {
            throw new IllegalArgumentException("Invalid task, not in foreground");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mLockTaskController.startLockTaskMode(callingUid, task, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0117 A[Catch: all -> 0x002e, TRY_ENTER, TryCatch #3 {all -> 0x002e, blocks: (B:11:0x001f, B:13:0x0026, B:14:0x0029, B:17:0x0031, B:19:0x0037, B:20:0x003a, B:23:0x003f, B:26:0x0066, B:28:0x006a, B:30:0x007f, B:33:0x0088, B:35:0x009a, B:38:0x00a8, B:40:0x00ab, B:72:0x00b7, B:75:0x0105, B:77:0x00ed, B:43:0x0117, B:45:0x011c, B:46:0x0123, B:49:0x0128, B:51:0x014f, B:52:0x0152, B:60:0x01ad, B:61:0x01b0, B:67:0x01b5, B:68:0x01b8, B:70:0x0160, B:54:0x016c, B:56:0x0185, B:57:0x0187), top: B:10:0x001f, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0128 A[Catch: all -> 0x002e, TRY_ENTER, TryCatch #3 {all -> 0x002e, blocks: (B:11:0x001f, B:13:0x0026, B:14:0x0029, B:17:0x0031, B:19:0x0037, B:20:0x003a, B:23:0x003f, B:26:0x0066, B:28:0x006a, B:30:0x007f, B:33:0x0088, B:35:0x009a, B:38:0x00a8, B:40:0x00ab, B:72:0x00b7, B:75:0x0105, B:77:0x00ed, B:43:0x0117, B:45:0x011c, B:46:0x0123, B:49:0x0128, B:51:0x014f, B:52:0x0152, B:60:0x01ad, B:61:0x01b0, B:67:0x01b5, B:68:0x01b8, B:70:0x0160, B:54:0x016c, B:56:0x0185, B:57:0x0187), top: B:10:0x001f, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startNextMatchingActivity(android.os.IBinder r19, android.content.Intent r20, android.os.Bundle r21) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskManagerService.startNextMatchingActivity(android.os.IBinder, android.content.Intent, android.os.Bundle):boolean");
    }

    public final void startPowerMode(int i) {
        int i2 = this.mPowerModeReasons;
        this.mPowerModeReasons = i2 | i;
        if ((i & 4) != 0) {
            if (this.mRetainPowerModeAndTopProcessState) {
                this.mH.removeMessages(3);
            }
            this.mRetainPowerModeAndTopProcessState = true;
            this.mH.sendEmptyMessageDelayed(3, 1000L);
            Slog.d("ActivityTaskManager", "Temporarily retain top process state for launching app");
        }
        if (this.mPowerManagerInternal == null) {
            return;
        }
        if ((i & 1) != 0 && (i2 & 1) == 0) {
            Trace.instant(32L, "StartModeLaunch");
            this.mPowerManagerInternal.setPowerMode(5, true);
        } else if (i == 2 && (i2 & 2) == 0) {
            Trace.instant(32L, "StartModeDisplayChange");
            this.mPowerManagerInternal.setPowerMode(17, true);
        }
    }

    public final void startProcessAsync(ActivityRecord activityRecord, String str, boolean z, boolean z2) {
        if (!this.mStartingProcessActivities.contains(activityRecord)) {
            this.mStartingProcessActivities.add(activityRecord);
        } else if (this.mProcessNames.get(activityRecord.processName, activityRecord.info.applicationInfo.uid) != null) {
            return;
        }
        try {
            if (Trace.isTagEnabled(32L)) {
                Trace.traceBegin(32L, "dispatchingStartProcess:" + activityRecord.processName);
            }
            this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda30(), this.mAmInternal, activityRecord.processName, activityRecord.info.applicationInfo, Boolean.valueOf(z), Boolean.valueOf(z2), str, activityRecord.intent.getComponent()));
            Trace.traceEnd(32L);
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public final void startRecentsActivity(Intent intent, long j, IRecentsAnimationRunner iRecentsAnimationRunner) {
        enforceTaskPermission("startRecentsActivity()");
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RecentTasks recentTasks = this.mRecentTasks;
                    RecentsAnimation recentsAnimation = new RecentsAnimation(this, this.mTaskSupervisor, this.mActivityStartController, this.mWindowManager, intent, recentTasks.mRecentsComponent, recentTasks.mRecentsUid, getProcessController(callingPid, callingUid));
                    if (iRecentsAnimationRunner == null) {
                        recentsAnimation.preloadRecentsActivity();
                    } else {
                        recentsAnimation.startRecentsActivity(iRecentsAnimationRunner, j);
                    }
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

    public final void startRunningVoiceLocked(IVoiceInteractionSession iVoiceInteractionSession, int i) {
        Slog.d("ActivityTaskManager", "<<<  startRunningVoiceLocked()");
        this.mVoiceWakeLock.setWorkSource(new WorkSource(i));
        IVoiceInteractionSession iVoiceInteractionSession2 = this.mRunningVoice;
        if (iVoiceInteractionSession2 == null || iVoiceInteractionSession2.asBinder() != iVoiceInteractionSession.asBinder()) {
            boolean z = this.mRunningVoice != null;
            this.mRunningVoice = iVoiceInteractionSession;
            if (z) {
                return;
            }
            this.mVoiceWakeLock.acquire();
            updateSleepIfNeededLocked();
        }
    }

    public final void startSystemLockTaskMode(int i) {
        enforceTaskPermission("startSystemLockTaskMode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0, null, false);
                    if (anyTaskForId == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    anyTaskForId.getRootTask().moveToFront("startSystemLockTaskMode", null);
                    startLockTaskMode(anyTaskForId, true);
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

    public final void startTimeTrackingFocusedActivityLocked() {
        AppTimeTracker appTimeTracker;
        ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
        if (this.mSleeping || (appTimeTracker = this.mCurAppTimeTracker) == null || topResumedActivity == null) {
            return;
        }
        String str = topResumedActivity.packageName;
        appTimeTracker.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (appTimeTracker.mStartedTime == 0) {
            appTimeTracker.mStartedTime = elapsedRealtime;
        }
        if (str.equals(appTimeTracker.mStartedPackage)) {
            return;
        }
        MutableLong mutableLong = appTimeTracker.mStartedPackageTime;
        if (mutableLong != null) {
            long j = elapsedRealtime - appTimeTracker.mStartedTime;
            mutableLong.value += j;
            appTimeTracker.mTotalTime += j;
        }
        appTimeTracker.mStartedPackage = str;
        MutableLong mutableLong2 = (MutableLong) appTimeTracker.mPackageTimes.get(str);
        appTimeTracker.mStartedPackageTime = mutableLong2;
        if (mutableLong2 == null) {
            MutableLong mutableLong3 = new MutableLong(0L);
            appTimeTracker.mStartedPackageTime = mutableLong3;
            appTimeTracker.mPackageTimes.put(str, mutableLong3);
        }
    }

    public final int startVoiceActivity(String str, String str2, int i, int i2, Intent intent, String str3, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i3, ProfilerInfo profilerInfo, Bundle bundle, int i4) {
        assertPackageMatchesCallingUid(str);
        this.mAmInternal.enforceCallingPermission("android.permission.BIND_VOICE_INTERACTION", "startVoiceActivity()");
        if (iVoiceInteractionSession == null || iVoiceInteractor == null) {
            throw new NullPointerException("null session or interactor");
        }
        int handleIncomingUser = handleIncomingUser(i, i2, i4, "startVoiceActivity");
        ActivityStarter obtainStarter = this.mActivityStartController.obtainStarter(intent, "startVoiceActivity");
        ActivityStarter.Request request = obtainStarter.mRequest;
        request.callingUid = i2;
        request.callingPackage = str;
        request.callingFeatureId = str2;
        request.resolvedType = str3;
        request.voiceSession = iVoiceInteractionSession;
        request.voiceInteractor = iVoiceInteractor;
        request.startFlags = i3;
        request.profilerInfo = profilerInfo;
        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
        if (fromBundle == null) {
            fromBundle = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
        } else if (fromBundle.getPendingIntentBackgroundActivityStartMode() == 0) {
            fromBundle.setPendingIntentBackgroundActivityStartMode(1);
        }
        SafeActivityOptions safeActivityOptions = new SafeActivityOptions(fromBundle);
        ActivityStarter.Request request2 = obtainStarter.mRequest;
        request2.activityOptions = safeActivityOptions;
        request2.userId = handleIncomingUser;
        request2.forcedBalByPiSender = BackgroundStartPrivileges.ALLOW_BAL;
        return obtainStarter.execute();
    }

    public final void stopAppSwitches() {
        this.mAmInternal.enforceCallingPermission("android.permission.STOP_APP_SWITCHES", "stopAppSwitches");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAppSwitchesState = 0;
                this.mLastStopAppSwitchesTime = SystemClock.uptimeMillis();
                this.mH.removeMessages(4);
                this.mH.sendEmptyMessageDelayed(4, 500L);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void stopLockTaskModeInternal(IBinder iBinder, boolean z) {
        Task task;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (iBinder != null) {
                    try {
                        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                        if (forTokenLocked == null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        task = forTokenLocked.task;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                } else {
                    task = null;
                }
                this.mLockTaskController.stopLockTaskMode(callingUid, task, z);
                WindowManagerService.resetPriorityAfterLockedSection();
                TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService("telecom");
                if (telecomManager != null) {
                    telecomManager.showInCallScreen(false);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void stopSystemLockTaskMode() {
        enforceTaskPermission("stopSystemLockTaskMode");
        stopLockTaskModeInternal(null, true);
    }

    public final boolean supportsLocalVoiceInteraction() {
        return ((VoiceInteractionManagerInternal) LocalServices.getService(VoiceInteractionManagerInternal.class)).supportsLocalVoiceInteraction();
    }

    public final void suppressResizeConfigChanges(boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "suppressResizeConfigChanges()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mSuppressResizeConfigChanges = z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX WARN: Finally extract failed */
    public final TaskSnapshot takeTaskSnapshot(int i, boolean z) {
        TaskSnapshot snapshot;
        this.mAmInternal.enforceCallingPermission("android.permission.READ_FRAME_BUFFER", "takeTaskSnapshot()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
                    if (anyTaskForId != null && anyTaskForId.isVisible()) {
                        if (z) {
                            snapshot = this.mWindowManager.mTaskSnapshotController.recordSnapshot(anyTaskForId);
                        } else {
                            TaskSnapshotController taskSnapshotController = this.mWindowManager.mTaskSnapshotController;
                            snapshot = taskSnapshotController.snapshot(anyTaskForId, taskSnapshotController.mHighResSnapshotScale);
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return snapshot;
                    }
                    Slog.w("ActivityTaskManager", "takeTaskSnapshot: taskId=" + i + " not found or not visible");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unhandledBack() {
        ActivityRecord topMostActivity;
        this.mAmInternal.enforceCallingPermission("android.permission.FORCE_BACK", "unhandledBack()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                    if (topDisplayFocusedRootTask != null && (topMostActivity = topDisplayFocusedRootTask.getTopMostActivity()) != null) {
                        topMostActivity.finishIfPossible("unhandled-back", true);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void unregisterScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) {
        this.mAmInternal.enforceCallingPermission("android.permission.DETECT_SCREEN_CAPTURE", "unregisterScreenCaptureObserver");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    WindowManagerGlobalLock windowManagerGlobalLock2 = forTokenLocked.mWmService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            RemoteCallbackList remoteCallbackList = forTokenLocked.mCaptureCallbacks;
                            if (remoteCallbackList != null) {
                                remoteCallbackList.unregister(iScreenCaptureObserver);
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) {
        enforceTaskPermission("unregisterTaskStackListener()");
        TaskChangeNotificationController taskChangeNotificationController = this.mTaskChangeNotificationController;
        taskChangeNotificationController.getClass();
        if (iTaskStackListener instanceof Binder) {
            synchronized (taskChangeNotificationController.mLocalTaskStackListeners) {
                taskChangeNotificationController.mLocalTaskStackListeners.remove(iTaskStackListener);
            }
        } else if (iTaskStackListener != null) {
            synchronized (taskChangeNotificationController.mRemoteTaskStackListeners) {
                taskChangeNotificationController.mRemoteTaskStackListeners.unregister(iTaskStackListener);
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void updateActiveRecents(int i) {
        DisplayContent displayContent;
        if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
            enforceTaskPermission("focusTopTask()");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        Task anyTaskForId = this.mWindowManager.mRoot.anyTaskForId(i);
                        if (anyTaskForId != null && (displayContent = anyTaskForId.mDisplayContent) != null) {
                            InputConsumerImpl inputConsumer = displayContent.mInputMonitor.getInputConsumer("recents_animation_input_consumer");
                            if (inputConsumer != null) {
                                if (displayContent.mTransitionController.hasTransientLaunch(displayContent)) {
                                    WeakReference weakReference = displayContent.mInputMonitor.mActiveRecentsActivity;
                                    ActivityRecord activityRecord = weakReference != null ? (ActivityRecord) weakReference.get() : null;
                                    if (activityRecord != null) {
                                        inputConsumer.mWindowHandle.touchableRegion.set(anyTaskForId.getBounds());
                                        displayContent.mInputMonitor.setActiveRecents(anyTaskForId, activityRecord);
                                        displayContent.mInputMonitor.updateInputWindowsLw(false);
                                    }
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
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
    }

    public final void updateActivityUsageStats(int i, ActivityRecord activityRecord) {
        int i2;
        Task task = activityRecord.task;
        if (task != null) {
            ActivityRecord rootActivity = task.getRootActivity(true, false);
            r1 = rootActivity != null ? rootActivity.mActivityComponent : null;
            i2 = task.mTaskId;
        } else {
            i2 = -1;
        }
        this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda29(), this.mAmInternal, activityRecord.mActivityComponent, Integer.valueOf(activityRecord.mUserId), Integer.valueOf(i), activityRecord.token, r1, new ActivityId(i2, activityRecord.shareableActivityToken), activityRecord.intent));
    }

    public final void updateAppCompatConfigurationPreset(int i) {
        MultiTaskingAppCompatConfiguration.PresetManager presetManager = this.mWindowManager.mAppCompatConfiguration.mPresetManager;
        int i2 = presetManager.mPreset;
        if (i2 == i || i < 0 || i > 2) {
            return;
        }
        presetManager.mPreset = i;
        Slog.d("MultiTaskingAppCompat", "Preset is updated " + MultiTaskingAppCompatConfiguration.PresetManager.presetToString(i2) + " to " + MultiTaskingAppCompatConfiguration.PresetManager.presetToString(presetManager.mPreset));
        this.mRootWindowContainer.forAllDisplays(new ActivityTaskManagerService$$ExternalSyntheticLambda3());
        this.mWindowManager.requestTraversal();
    }

    public final void updateBatteryStats(boolean z, ActivityRecord activityRecord) {
        this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda28(), this.mAmInternal, activityRecord.mActivityComponent, Integer.valueOf(activityRecord.app.mUid), Integer.valueOf(activityRecord.mUserId), Boolean.valueOf(z)));
    }

    public final boolean updateConfiguration(Configuration configuration) {
        this.mAmInternal.enforceCallingPermission("android.permission.CHANGE_CONFIGURATION", "updateConfiguration()");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowManagerService windowManagerService = this.mWindowManager;
                if (windowManagerService == null) {
                    Slog.w("ActivityTaskManager", "Skip updateConfiguration because mWindowManager isn't set");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (configuration == null) {
                    configuration = windowManagerService.computeNewConfiguration(0);
                }
                this.mH.sendMessage(PooledLambda.obtainMessage(new ActivityTaskManagerService$$ExternalSyntheticLambda0(1), this.mAmInternal, 0));
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (configuration != null) {
                    try {
                        Settings.System.clearConfiguration(configuration);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                updateConfigurationLocked(configuration, false, false, -10000);
                boolean z = this.mTmpUpdateConfigurationResult.changes != 0;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
    }

    public final boolean updateConfigurationLocked(final Configuration configuration, boolean z, boolean z2, int i) {
        int updateGlobalConfigurationLocked;
        deferWindowLayout();
        if (configuration != null) {
            try {
                boolean z3 = CoreRune.FW_CUSTOM_DISPLAY_CHANGE_ANIM;
                final Configuration configuration2 = z3 ? new Configuration(getGlobalConfiguration()) : null;
                updateGlobalConfigurationLocked = updateGlobalConfigurationLocked(configuration, z, z2, i);
                if (z3 && !WindowManagerService.sEnableShellTransitions && updateGlobalConfigurationLocked != 0) {
                    this.mRootWindowContainer.forAllDisplays(new Consumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i2;
                            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
                            Configuration configuration3 = configuration2;
                            Configuration configuration4 = configuration;
                            DisplayContent displayContent = (DisplayContent) obj;
                            activityTaskManagerService.getClass();
                            displayContent.getClass();
                            int i3 = 0;
                            if (!CoreRune.FW_UI_MODE_ANIMATION || configuration3.isNightModeActive() == configuration4.isNightModeActive()) {
                                i2 = 0;
                            } else {
                                i3 = R.anim.translucent_exit;
                                i2 = R.anim.voice_activity_close_enter;
                            }
                            if (i3 == 0 && i2 == 0) {
                                return;
                            }
                            activityTaskManagerService.mWindowManager.startFreezingDisplay(i2, i3, -1, displayContent);
                        }
                    });
                }
                UpdateConfigurationResult updateConfigurationResult = this.mTmpUpdateConfigurationResult;
                updateConfigurationResult.changes = updateGlobalConfigurationLocked;
                updateConfigurationResult.mIsUpdating = true;
            } catch (Throwable th) {
                this.mTmpUpdateConfigurationResult.mIsUpdating = false;
                continueWindowLayout();
                throw th;
            }
        } else {
            updateGlobalConfigurationLocked = 0;
        }
        boolean ensureConfigAndVisibilityAfterUpdate = ensureConfigAndVisibilityAfterUpdate(updateGlobalConfigurationLocked, null);
        this.mTmpUpdateConfigurationResult.mIsUpdating = false;
        continueWindowLayout();
        this.mTmpUpdateConfigurationResult.getClass();
        return ensureConfigAndVisibilityAfterUpdate;
    }

    public final int updateGlobalConfigurationLocked(Configuration configuration, boolean z, boolean z2, int i) {
        DisplayContent displayContent;
        this.mTempConfig.setTo(getGlobalConfiguration());
        int updateFrom = this.mTempConfig.updateFrom(configuration);
        int i2 = 0;
        if (updateFrom == 0) {
            return 0;
        }
        Trace.traceBegin(32L, "updateGlobalConfiguration");
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled;
        if (zArr[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_CONFIGURATION, 2008996027621913637L, 0, null, String.valueOf(configuration));
        }
        EventLog.writeEvent(2719, updateFrom);
        FrameworkStatsLog.write(66, configuration.colorMode, configuration.densityDpi, configuration.fontScale, configuration.hardKeyboardHidden, configuration.keyboard, configuration.keyboardHidden, configuration.mcc, configuration.mnc, configuration.navigation, configuration.navigationHidden, configuration.orientation, configuration.screenHeightDp, configuration.screenLayout, configuration.screenWidthDp, configuration.smallestScreenWidthDp, configuration.touchscreen, configuration.uiMode);
        if (Process.myUid() == 1000) {
            int i3 = configuration.mcc;
            if (i3 != 0) {
                SystemProperties.set("debug.tracing.mcc", Integer.toString(i3));
            }
            int i4 = configuration.mnc;
            if (i4 != 0) {
                SystemProperties.set("debug.tracing.mnc", Integer.toString(i4));
            }
        }
        if (!z && !configuration.getLocales().isEmpty() && configuration.userSetLocale) {
            LocaleList locales = configuration.getLocales();
            if (locales.size() > 1) {
                if (this.mSupportedSystemLocales == null) {
                    this.mSupportedSystemLocales = Resources.getSystem().getAssets().getLocales();
                }
                i2 = Math.max(0, locales.getFirstMatchIndex(this.mSupportedSystemLocales));
            }
            SystemProperties.set("persist.sys.locale", locales.get(i2).toLanguageTag());
            LocaleList.setDefault(locales, i2);
        }
        Configuration configuration2 = this.mTempConfig;
        int i5 = this.mConfigurationSeq + 1;
        this.mConfigurationSeq = i5;
        int max = Math.max(i5, 1);
        this.mConfigurationSeq = max;
        configuration2.seq = max;
        StringBuilder sb = new StringBuilder("Config changes=");
        BatteryService$$ExternalSyntheticOutline0.m(updateFrom, sb, " ");
        sb.append(this.mTempConfig);
        Slog.i("ActivityTaskManager", sb.toString());
        UsageStatsManagerInternal usageStatsManagerInternal = this.mUsageStatsInternal;
        Configuration configuration3 = this.mTempConfig;
        int currentUserId = this.mAmInternal.getCurrentUserId();
        UsageStatsService.LocalService localService = (UsageStatsService.LocalService) usageStatsManagerInternal;
        localService.getClass();
        if (configuration3 == null) {
            Slog.w("UsageStatsService", "Configuration event reported with a null config");
        } else {
            UsageEvents.Event event = new UsageEvents.Event(5, SystemClock.elapsedRealtime());
            event.mPackage = "android";
            event.mConfiguration = new Configuration(configuration3);
            UsageStatsService.this.reportEventOrAddToQueue(currentUserId, event);
        }
        updateShouldShowDialogsLocked(this.mTempConfig);
        AttributeCache instance = AttributeCache.instance();
        if (instance != null) {
            instance.updateConfiguration(this.mTempConfig);
        }
        this.mSystemThread.applyConfigurationToResources(this.mTempConfig);
        if (z2 && Settings.System.hasInterestingConfigurationChanges(updateFrom)) {
            if ((1073741824 & updateFrom) != 0) {
                this.mDexController.mGlobalFontScaleForRestore = this.mTempConfig.fontScale;
            }
            final int i6 = 0;
            this.mH.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda18
                public final void accept(Object obj, Object obj2, Object obj3) {
                    switch (i6) {
                        case 0:
                            int intValue = ((Integer) obj2).intValue();
                            Settings.System.putConfigurationForUser(((ActivityTaskManagerService) obj).mContext.getContentResolver(), (Configuration) obj3, intValue);
                            break;
                        default:
                            ((ActivityManagerInternal) obj).broadcastGlobalConfigurationChanged(((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
                            break;
                    }
                }
            }, this, Integer.valueOf(i), new Configuration(this.mTempConfig)));
        }
        SparseArray sparseArray = this.mProcessMap.mPidMap;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            WindowProcessController windowProcessController = (WindowProcessController) sparseArray.get(sparseArray.keyAt(size));
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, -6404059840638143757L, 0, null, String.valueOf(windowProcessController.mName), String.valueOf(this.mTempConfig));
            }
            windowProcessController.onConfigurationChanged(this.mTempConfig);
        }
        final int i7 = 1;
        this.mH.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda18
            public final void accept(Object obj, Object obj2, Object obj3) {
                switch (i7) {
                    case 0:
                        int intValue = ((Integer) obj2).intValue();
                        Settings.System.putConfigurationForUser(((ActivityTaskManagerService) obj).mContext.getContentResolver(), (Configuration) obj3, intValue);
                        break;
                    default:
                        ((ActivityManagerInternal) obj).broadcastGlobalConfigurationChanged(((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
                        break;
                }
            }
        }, this.mAmInternal, Integer.valueOf(updateFrom), Boolean.valueOf(z)));
        Trace.traceBegin(32L, "RootConfigChange");
        this.mRootWindowContainer.onConfigurationChanged(this.mTempConfig);
        Trace.traceEnd(32L);
        if ((updateFrom & 512) != 0 && this.mDexController.getDexModeLocked() == 2 && (displayContent = this.mRootWindowContainer.getDisplayContent(2)) != null) {
            displayContent.reconfigureDisplayLocked();
        }
        Trace.traceEnd(32L);
        return updateFrom;
    }

    public final void updateLockTaskFeatures(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000) {
            this.mAmInternal.enforceCallingPermission("android.permission.UPDATE_LOCK_TASK_PACKAGES", "updateLockTaskFeatures()");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, -559595900417262876L, 1, null, Long.valueOf(i), String.valueOf(Integer.toHexString(i2)));
                }
                this.mLockTaskController.updateLockTaskFeatures(i, i2);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateLockTaskPackages(int i, String[] strArr) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000) {
            this.mAmInternal.enforceCallingPermission("android.permission.UPDATE_LOCK_TASK_PACKAGES", "updateLockTaskPackages()");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled[3]) {
                        ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, -829638795650515884L, 1, null, Long.valueOf(i), String.valueOf(Arrays.toString(strArr)));
                    }
                    this.mLockTaskController.updateLockTaskPackages(i, strArr);
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

    public final void updateOomAdj() {
        this.mH.removeCallbacks(this.mUpdateOomAdjRunnable);
        this.mH.post(this.mUpdateOomAdjRunnable);
    }

    public final void updatePersistentConfiguration(Configuration configuration, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    configuration.windowConfiguration.setToDefaults();
                    updateConfigurationLocked(configuration, false, true, i);
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

    public final void updateResumedAppTrace(ActivityRecord activityRecord) {
        if (Trace.isTagEnabled(32L)) {
            ActivityRecord activityRecord2 = this.mTracedResumedActivity;
            if (activityRecord2 != null) {
                Trace.asyncTraceForTrackEnd(32L, "Focused app", System.identityHashCode(activityRecord2));
            }
            if (activityRecord != null) {
                Trace.asyncTraceForTrackBegin(32L, "Focused app", activityRecord.mActivityComponent.flattenToShortString(), System.identityHashCode(activityRecord));
            }
        }
        this.mTracedResumedActivity = activityRecord;
    }

    public final void updateShouldShowDialogsLocked(Configuration configuration) {
        boolean z = false;
        boolean z2 = (configuration.keyboard == 1 && configuration.touchscreen == 1 && configuration.navigation == 1) ? false : true;
        boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "hide_error_dialogs", 0) != 0;
        if (z2 && ActivityTaskManager.currentUiModeSupportsErrorDialogs(configuration) && !z3) {
            z = true;
        }
        this.mShowDialogs = z;
    }

    public final void updateSleepIfNeededLocked() {
        boolean z;
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        int childCount = rootWindowContainer.getChildCount() - 1;
        while (true) {
            if (childCount < 0) {
                z = false;
                break;
            } else {
                if (!((DisplayContent) rootWindowContainer.getChildAt(childCount)).shouldSleep()) {
                    z = true;
                    break;
                }
                childCount--;
            }
        }
        boolean z2 = !z;
        boolean z3 = this.mSleeping;
        if (!z2) {
            if (z3) {
                this.mSleeping = false;
                FrameworkStatsLog.write(14, 2);
                startTimeTrackingFocusedActivityLocked();
                if (this.mTopApp != null) {
                    this.mTopApp.addToPendingTop();
                }
                this.mTopProcessState = 2;
                Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP");
                ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
                activityTaskSupervisor.mHandler.removeMessages(203);
                if (activityTaskSupervisor.mGoingToSleepWakeLock.isHeld()) {
                    activityTaskSupervisor.mGoingToSleepWakeLock.release();
                }
            }
            this.mRootWindowContainer.applySleepTokens(true);
            if (!z3) {
                return;
            }
        } else {
            if (this.mSleeping || !z2) {
                Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP_SLEEPING#2");
                this.mRootWindowContainer.applySleepTokens(false);
                return;
            }
            this.mSleeping = true;
            FrameworkStatsLog.write(14, 1);
            AppTimeTracker appTimeTracker = this.mCurAppTimeTracker;
            if (appTimeTracker != null) {
                appTimeTracker.stop();
            }
            this.mTopProcessState = 12;
            Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP_SLEEPING");
            this.mTaskSupervisor.goingToSleepLocked();
            updateResumedAppTrace(null);
        }
        updateOomAdj();
    }
}
