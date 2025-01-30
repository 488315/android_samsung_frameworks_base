package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AnrController;
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
import android.app.INotificationManager;
import android.app.IScreenCaptureObserver;
import android.app.ITaskStackListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureUiState;
import android.app.ProfilerInfo;
import android.app.WaitResult;
import android.app.admin.DevicePolicyCache;
import android.app.admin.DeviceStateCache;
import android.app.assist.ActivityId;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.app.compat.CompatChanges;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.LocusId;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IUserManager;
import android.os.InputConstants;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UpdateLock;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.WorkSource;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.service.dreams.DreamActivity;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice.VoiceInteractionManagerInternal;
import android.sysprop.DisplayProperties;
import android.telecom.TelecomManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.widget.Toast;
import android.window.BackAnimationAdapter;
import android.window.BackNavigationInfo;
import android.window.IWindowOrganizerController;
import android.window.RemoteTransition;
import android.window.SplashScreenView;
import android.window.TaskSnapshot;
import android.window.TransitionRequestInfo;
import com.android.internal.app.AppLockPolicy;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.ProcessMap;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.TransferPipe;
import com.android.internal.policy.AttributeCache;
import com.android.internal.policy.KeyguardDismissCallback;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.DualAppManagerService;
import com.android.server.FgThread;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.UiThread;
import com.android.server.Watchdog;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.AppStateBroadcaster;
import com.android.server.am.AppTimeTracker;
import com.android.server.am.AssistDataRequester;
import com.android.server.am.BaseErrorDialog;
import com.android.server.am.EventLogTags;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.Pageboost;
import com.android.server.am.PendingIntentController;
import com.android.server.am.PendingIntentRecord;
import com.android.server.am.UserState;
import com.android.server.appprelauncher.AppPrelaunchManagerService;
import com.android.server.baiducarlife.BaiduCarlifeADBConnectUtils;
import com.android.server.firewall.IntentFirewall;
import com.android.server.locales.LocaleManagerService;
import com.android.server.om.wallpapertheme.SemWallpaperThemeUtils;
import com.android.server.pm.ContentDispatcher;
import com.android.server.pm.UserManagerService;
import com.android.server.policy.PermissionPolicyInternal;
import com.android.server.sdksandbox.SdkSandboxManagerLocal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.wallpaper.WallpaperManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.core.IFoldStarManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.multiwindow.IMultiTaskingBinder;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CompatChangeableAppsService;
import com.samsung.android.server.util.SafetySystemService;
import com.samsung.android.wifi.SemWifiManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class ActivityTaskManagerService extends IActivityTaskManager.Stub {
  public static Bundle mKnoxInfo;
  public final int GL_ES_VERSION;
  public Bundle knoxVersionInfo;
  public ActivityManagerPerformance mAMBooster;
  public int[] mAccessibilityServiceUids;
  public volatile ComponentName mActiveDreamComponent;
  public final MirrorActiveUids mActiveUids;
  public ComponentName mActiveVoiceInteractionServiceComponent;
  public ActivityClientController mActivityClientController;
  public SparseArray mActivityInterceptorCallbacks;
  public ActivityStartController mActivityStartController;
  public final SparseArray mAllowAppSwitchUids;
  public ActivityManagerInternal mAmInternal;
  public final List mAnrController;
  public AppLockPolicy mAppLockPolicy;
  public AppOpsManager mAppOpsManager;
  public AppPairController mAppPairController;
  public volatile int mAppSwitchesState;
  public AppWarnings mAppWarnings;
  public AppPrelaunchManagerService mAps;
  public final BackNavigationController mBackNavigationController;
  public BackgroundActivityStartCallback mBackgroundActivityStartCallback;
  public Optional mCb4Task;
  public ChangeTransitionController mChangeTransitController;
  public final Map mCompanionAppUidsMap;
  public CompatModePackages mCompatModePackages;
  public int mConfigurationSeq;
  public ContentDispatcher mContentDispatcher;
  public Context mContext;
  public IActivityController mController;
  public String mControllerDescription;
  public boolean mControllerIsAMonkey;
  public BroadcastReceiver mCooldownLevelReceiver;
  public AppTimeTracker mCurAppTimeTracker;
  public volatile int mDemoteTopAppReasons;
  public boolean mDevEnableNonResizableMultiWindow;
  public int mDeviceOwnerUid;
  public DexCompatController mDexCompatController;
  public DexController mDexController;
  public DexDockingController mDexDockingController;
  public ActivityTaskManagerServiceExt mExt;
  public final int mFactoryTest;
  public FoldStarManagerService mFoldStarManagerService;
  public boolean mForceResizableActivities;
  public FreeformController mFreeformController;
  public int mGesutreHint;
  public int mGlobalAssetsSeq;
  public final WindowManagerGlobalLock mGlobalLock;
  public final Object mGlobalLockWithoutBoost;

  /* renamed from: mH */
  public HandlerC2878H f1734mH;
  public boolean mHasCompanionDeviceSetupFeature;
  public boolean mHasHeavyWeightFeature;
  public boolean mHasLeanbackFeature;
  public volatile WindowProcessController mHeavyWeightProcess;
  public volatile WindowProcessController mHomeProcess;
  public final Object mIdsLock;
  public BroadcastReceiver mIdsReceiver;
  public IntentFirewall mIntentFirewall;
  final ActivityTaskManagerInternal mInternal;
  public IKeyEventListener mKeyEventListener;
  public KeyguardController mKeyguardController;
  public boolean mKeyguardShown;
  public String mLastANRState;
  public ActivityRecord mLastResumedActivity;
  public volatile long mLastStopAppSwitchesTime;
  public int mLaunchPowerModeReasons;
  public int mLayoutReasons;
  public final ClientLifecycleManager mLifecycleManager;
  public LockTaskController mLockTaskController;
  public final ArrayList mMWControllers;
  public float mMinPercentageMultiWindowSupportHeight;
  public float mMinPercentageMultiWindowSupportWidth;
  public MultiInstanceController mMultiInstanceController;
  public MultiStarController mMultiStarController;
  public final IMultiTaskingBinder mMultiTaskingBinder;
  public final MultiTaskingController mMultiTaskingController;
  public MultiWindowEnableController mMultiWindowEnableController;
  public MultiWindowFoldController mMultiWindowFoldController;
  public MultiWindowSupportPolicyController mMwSupportPolicyController;
  public NaturalSwitchingController mNaturalSwitchingController;
  public NewDexController mNewDexController;
  public final ArrayMap mOccludingMap;
  public PackageConfigPersister mPackageConfigPersister;
  public final ArrayList mPendingAssistExtras;
  public PendingIntentController mPendingIntentController;
  public PermissionPolicyInternal mPermissionPolicyInternal;
  public PersonaActivityHelper mPersonaActivityHelper;
  public PackageManagerInternal mPmInternal;
  public PowerManagerInternal mPowerManagerInternal;
  public final ArrayMap mPrepareOccludingMap;
  public volatile WindowProcessController mPreviousProcess;
  public long mPreviousProcessVisibleTime;
  public final WindowProcessControllerMap mProcessMap;
  public final ProcessMap mProcessNames;
  public String mProfileApp;
  public Set mProfileOwnerUids;
  public WindowProcessController mProfileProc;
  public ProfilerInfo mProfilerInfo;
  public RecentTasks mRecentTasks;
  public RemoteAppController mRemoteAppController;
  public int mRespectsActivityMinWidthHeightMultiWindow;
  public volatile boolean mRetainPowerModeAndTopProcessState;
  public RootWindowContainer mRootWindowContainer;
  public IVoiceInteractionSession mRunningVoice;
  public final List mScreenObservers;
  public Set mSetClearIds;
  public SettingObserver mSettingsObserver;
  public boolean mShowDialogs;
  public boolean mShuttingDown;
  public volatile boolean mSleeping;
  public StatusBarManagerInternal mStatusBarManagerInternal;
  public String[] mSupportedSystemLocales;
  public boolean mSupportsExpandedPictureInPicture;
  public boolean mSupportsFreeformWindowManagement;
  public boolean mSupportsMultiDisplay;
  public boolean mSupportsMultiWindow;
  public int mSupportsNonResizableMultiWindow;
  public boolean mSupportsPictureInPicture;
  public boolean mSupportsSplitScreenMultiWindow;
  public boolean mSuppressResizeConfigChanges;
  public ComponentName mSysUiServiceComponent;
  public final ActivityThread mSystemThread;
  public TaskChangeNotificationController mTaskChangeNotificationController;
  public TaskFragmentOrganizerController mTaskFragmentOrganizerController;
  public TaskOrganizerController mTaskOrganizerController;
  public ActivityTaskSupervisor mTaskSupervisor;
  public Configuration mTempConfig;
  public int mThumbnailHeight;
  public int mThumbnailWidth;
  public final UpdateConfigurationResult mTmpUpdateConfigurationResult;
  public String mTopAction;
  public volatile WindowProcessController mTopApp;
  public ComponentName mTopComponent;
  public String mTopData;
  public volatile int mTopProcessState;
  public ActivityRecord mTracedResumedActivity;
  public UriGrantsManagerInternal mUgmInternal;
  public final Context mUiContext;
  public UiHandler mUiHandler;
  public final UpdateLock mUpdateLock;
  public final Runnable mUpdateOomAdjRunnable;
  public UsageStatsManagerInternal mUsageStatsInternal;
  public UserManagerService mUserManager;
  public int mViSessionId;
  public final VisibleActivityProcessTracker mVisibleActivityProcessTracker;
  public PowerManager.WakeLock mVoiceWakeLock;
  public int mVr2dDisplayId;
  public VrController mVrController;
  public WallpaperManagerInternal mWallpaperManagerInternal;
  public WindowManagerService mWindowManager;
  public WindowOrganizerController mWindowOrganizerController;
  public boolean shouldDebug;
  public int mSIOPLevel = -1;
  public int mBatteryOverheatLevel = -1;
  public int mOverheatTextId = -1;
  public HashMap mCheckSIOPLevelList = new HashMap();

  public final class UpdateConfigurationResult {
    public boolean activityRelaunched;
    public int changes;
  }

  public static String relaunchReasonToString(int i) {
    if (i == 1) {
      return "window_resize";
    }
    if (i != 2) {
      return null;
    }
    return "free_resize";
  }

  public Map getCoverLauncherEnabledAppListByType(int i, int i2) {
    return null;
  }

  public boolean isPackageEnabledForCoverLauncher(String str, int i) {
    return false;
  }

  public boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2) {
    return false;
  }

  public void logAppTooSlow(WindowProcessController windowProcessController, long j, String str) {}

  public int setCoverLauncherPackageDisabled(String str, int i) {
    return -100;
  }

  public int setCoverLauncherPackageEnabled(String str, int i) {
    return -100;
  }

  public final class SettingObserver extends ContentObserver {
    public final Uri mFontScaleUri;
    public final Uri mFontWeightAdjustmentUri;
    public final Uri mGestureHintUri;
    public final Uri mHideErrorDialogsUri;

    public SettingObserver() {
      super(ActivityTaskManagerService.this.f1734mH);
      Uri uriFor = Settings.System.getUriFor("font_scale");
      this.mFontScaleUri = uriFor;
      Uri uriFor2 = Settings.Global.getUriFor("hide_error_dialogs");
      this.mHideErrorDialogsUri = uriFor2;
      Uri uriFor3 = Settings.Secure.getUriFor("font_weight_adjustment");
      this.mFontWeightAdjustmentUri = uriFor3;
      Uri uriFor4 = Settings.Global.getUriFor("navigation_bar_gesture_hint");
      this.mGestureHintUri = uriFor4;
      ContentResolver contentResolver =
          ActivityTaskManagerService.this.mContext.getContentResolver();
      contentResolver.registerContentObserver(uriFor, false, this, -1);
      contentResolver.registerContentObserver(uriFor2, false, this, -1);
      contentResolver.registerContentObserver(uriFor3, false, this, -1);
      contentResolver.registerContentObserver(uriFor4, false, this, -1);
      ActivityTaskManagerService.this.updateGestureHint();
    }

    public void onChange(boolean z, Collection collection, int i, int i2) {
      Iterator it = collection.iterator();
      while (it.hasNext()) {
        Uri uri = (Uri) it.next();
        if (this.mFontScaleUri.equals(uri)) {
          ActivityTaskManagerService.this.updateFontScaleIfNeeded(i2);
        } else if (this.mHideErrorDialogsUri.equals(uri)) {
          WindowManagerGlobalLock windowManagerGlobalLock =
              ActivityTaskManagerService.this.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock) {
            try {
              ActivityTaskManagerService activityTaskManagerService =
                  ActivityTaskManagerService.this;
              activityTaskManagerService.updateShouldShowDialogsLocked(
                  activityTaskManagerService.getGlobalConfiguration());
            } catch (Throwable th) {
              WindowManagerService.resetPriorityAfterLockedSection();
              throw th;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
        } else if (this.mFontWeightAdjustmentUri.equals(uri)) {
          ActivityTaskManagerService.this.updateFontWeightAdjustmentIfNeeded(i2);
        } else if (this.mGestureHintUri.equals(uri)) {
          ActivityTaskManagerService.this.updateGestureHint();
        }
      }
    }
  }

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
    this.knoxVersionInfo = SemPersonaManager.getKnoxInfo();
    this.mTopProcessState = 2;
    this.mShowDialogs = true;
    this.mShuttingDown = false;
    this.mControllerDescription = null;
    this.mAccessibilityServiceUids = new int[0];
    this.mDeviceOwnerUid = -1;
    this.mProfileOwnerUids = new ArraySet();
    this.mUpdateOomAdjRunnable =
        new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService.1
          @Override // java.lang.Runnable
          public void run() {
            ActivityTaskManagerService.this.mAmInternal.updateOomAdj(1);
          }
        };
    this.mMultiTaskingController = new MultiTaskingController(this);
    this.mMultiTaskingBinder = new MultiTaskingBinder(this);
    this.mMultiStarController = new MultiStarController(this);
    ArrayList arrayList =
        new ArrayList() { // from class: com.android.server.wm.ActivityTaskManagerService.2
          {
            add(ActivityTaskManagerService.this.mMultiTaskingController);
            add(ActivityTaskManagerService.this.mMultiStarController);
          }
        };
    this.mMWControllers = arrayList;
    this.mIdsReceiver =
        new BroadcastReceiver() { // from class: com.android.server.wm.ActivityTaskManagerService.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if (intent != null) {
              synchronized (ActivityTaskManagerService.this.mIdsLock) {
                ActivityTaskManagerService.this
                    .getIdsClearSet()
                    .add(Integer.valueOf(intent.getIntExtra("android.intent.extra.UID", -1)));
                try {
                  ObjectOutputStream objectOutputStream =
                      new ObjectOutputStream(new FileOutputStream("/data/system/idsFile.txt"));
                  try {
                    objectOutputStream.writeObject(
                        ActivityTaskManagerService.this.getIdsClearSet());
                    objectOutputStream.close();
                  } catch (Throwable th) {
                    try {
                      objectOutputStream.close();
                    } catch (Throwable th2) {
                      th.addSuppressed(th2);
                    }
                    throw th;
                  }
                } catch (IOException unused) {
                  Slog.w(
                      "ActivityTaskManager",
                      "Error writing IDS file during package update broadcast.");
                }
              }
            }
          }
        };
    this.mCooldownLevelReceiver =
        new BroadcastReceiver() { // from class: com.android.server.wm.ActivityTaskManagerService.4
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            Bundle extras;
            if (!"com.samsung.CHECK_COOLDOWN_LEVEL".equals(intent.getAction())
                || (extras = intent.getExtras()) == null) {
              return;
            }
            int i = extras.getInt("check_cooldown_level", -1);
            if (i != -1) {
              ActivityTaskManagerService.this.mSIOPLevel = i;
            }
            int i2 = extras.getInt("batt_temp_level", -1);
            if (i2 != -1) {
              ActivityTaskManagerService.this.mBatteryOverheatLevel = i2;
            }
            ActivityTaskManagerService.this.mOverheatTextId =
                extras.getInt("overheat_id", R.string.face_error_hw_not_available);
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            if ((activityTaskManagerService.mSIOPLevel != -1
                    || activityTaskManagerService.mBatteryOverheatLevel != -1)
                && extras.getSerializable("check_cooldown_list") != null) {
              ActivityTaskManagerService.this.mCheckSIOPLevelList.clear();
              ActivityTaskManagerService.this.mCheckSIOPLevelList =
                  (HashMap) extras.getSerializable("check_cooldown_list");
            }
            Slog.d(
                "checkingSIOP",
                "VZWLevel = "
                    + ActivityTaskManagerService.this.mSIOPLevel
                    + ", OverheatLevel = "
                    + ActivityTaskManagerService.this.mBatteryOverheatLevel);
          }
        };
    this.mCb4Task = Optional.empty();
    this.mPrepareOccludingMap = new ArrayMap();
    this.mOccludingMap = new ArrayMap();
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
    this.mTaskFragmentOrganizerController =
        windowOrganizerController.mTaskFragmentOrganizerController;
    this.mBackNavigationController = new BackNavigationController();
    this.mPersonaActivityHelper = null;
    this.mExt = new ActivityTaskManagerServiceExt(this.mContext, this);
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
    RemoteAppController remoteAppController = new RemoteAppController(this);
    this.mRemoteAppController = remoteAppController;
    arrayList.add(remoteAppController);
    MultiWindowSupportPolicyController multiWindowSupportPolicyController =
        new MultiWindowSupportPolicyController(this);
    this.mMwSupportPolicyController = multiWindowSupportPolicyController;
    arrayList.add(multiWindowSupportPolicyController);
    NaturalSwitchingController naturalSwitchingController = new NaturalSwitchingController(this);
    this.mNaturalSwitchingController = naturalSwitchingController;
    arrayList.add(naturalSwitchingController);
    FreeformController freeformController = new FreeformController(this);
    this.mFreeformController = freeformController;
    arrayList.add(freeformController);
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
    DexDockingController dexDockingController = new DexDockingController(this);
    this.mDexDockingController = dexDockingController;
    arrayList.add(dexDockingController);
    this.mContentDispatcher = new ContentDispatcher(context);
  }

  public void onSystemReady() {
    this.mContext.registerReceiver(
        this.mCooldownLevelReceiver, new IntentFilter("com.samsung.CHECK_COOLDOWN_LEVEL"));
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        PackageManager packageManager = this.mContext.getPackageManager();
        this.mHasHeavyWeightFeature =
            packageManager.hasSystemFeature("android.software.cant_save_state");
        this.mHasLeanbackFeature = packageManager.hasSystemFeature("android.software.leanback");
        this.mHasCompanionDeviceSetupFeature =
            packageManager.hasSystemFeature("android.software.companion_device_setup");
        this.mVrController.onSystemReady();
        this.mRecentTasks.onSystemReadyLocked();
        this.mTaskSupervisor.onSystemReady();
        this.mActivityClientController.onSystemReady();
        this.mExt.onSystemReady();
        SafetySystemService.onSystemReady(this, this.mContext);
        if (CoreRune.SYSFW_APP_PREL) {
          this.mAps =
              (AppPrelaunchManagerService)
                  LocalServices.getService(AppPrelaunchManagerService.class);
        }
        ActivitySecurityModelFeatureFlags.initialize(
            this.mContext.getMainExecutor(), packageManager);
        this.mPersonaActivityHelper.onSystemReady();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    HandlerThread handlerThread = new HandlerThread("activeLaunch");
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.DO_ACTIVE_LAUNCH");
    intentFilter.addAction("com.samsung.DO_ACTIVE_LAUNCH_FOR_KNOX");
    intentFilter.addAction("com.samsung.DO_ACTIVE_LAUNCH_FOR_KNOX_LAUNCHER");
    handlerThread.start();
    if (CoreRune.SYSPERF_BOOST_OPT) {
      Process.setThreadGroup(handlerThread.getThreadId(), 10);
    }
    this.mContext.registerReceiver(
        new ActiveLaunchReceiver(), intentFilter, null, new Handler(handlerThread.getLooper()));
    IntentFilter intentFilter2 = new IntentFilter("android.intent.action.PACKAGE_REPLACED");
    intentFilter2.addDataScheme("package");
    this.mContext.registerReceiver(this.mIdsReceiver, intentFilter2);
    synchronized (this.mIdsLock) {
      try {
        ObjectInputStream objectInputStream =
            new ObjectInputStream(new FileInputStream("/data/system/idsFile.txt"));
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

  public final Set getIdsClearSet() {
    if (this.mSetClearIds == null) {
      this.mSetClearIds = ConcurrentHashMap.newKeySet();
    }
    return this.mSetClearIds;
  }

  public void onInitPowerManagement() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mTaskSupervisor.initPowerManagement();
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mPowerManagerInternal =
            (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "*voice*");
        this.mVoiceWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public class ActiveLaunchReceiver extends BroadcastReceiver {
    public ActiveLaunchReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      Bundle extras;
      boolean z;
      boolean z2;
      IllegalArgumentException e;
      ActivityManagerPerformance activityManagerPerformance;
      if (intent == null) {
        return;
      }
      String action = intent.getAction();
      boolean equals = "com.samsung.DO_ACTIVE_LAUNCH".equals(action);
      boolean z3 =
          "com.samsung.DO_ACTIVE_LAUNCH_FOR_KNOX_LAUNCHER".equals(action)
              || "com.samsung.DO_ACTIVE_LAUNCH_FOR_KNOX".equals(action);
      if ((equals || z3) && (extras = intent.getExtras()) != null) {
        PackageManager packageManager = context.getPackageManager();
        String string = extras.getString("package_name");
        int myUserId = equals ? UserHandle.myUserId() : extras.getInt("userid", 0);
        boolean z4 = !equals;
        if (equals) {
          Pageboost.startActiveLaunch(string);
        }
        try {
          PackageManagerInternal packageManagerInternalLocked =
              ActivityTaskManagerService.this.getPackageManagerInternalLocked();
          if (packageManagerInternalLocked != null) {
            z2 = packageManagerInternalLocked.wasPackageEverLaunched(string, myUserId);
            try {
              z = packageManagerInternalLocked.wasPackageStopped(string, myUserId);
            } catch (IllegalArgumentException e2) {
              e = e2;
              z = true;
            }
            try {
              boolean isPackageSuspended =
                  packageManagerInternalLocked.isPackageSuspended(string, UserHandle.myUserId());
              if (!z2 || z || isPackageSuspended) {
                Slog.e(
                    "ActivityTaskManager",
                    "Checking for the Active launch isPkgEverLaunched :"
                        + z2
                        + ", isPkgStopped : "
                        + z
                        + ", isPkgSuspended : "
                        + isPackageSuspended);
                return;
              }
            } catch (IllegalArgumentException e3) {
              e = e3;
              Slog.w("ActivityTaskManager", "Failed active Launch package : " + string + ": " + e);
              Slog.d(
                  "ActivityTaskManager",
                  "Checking for the Active launch isPkgEverLaunched :"
                      + z2
                      + ", isPkgStopped : "
                      + z
                      + ", isDataCleared :"
                      + z4);
              if (packageManager != null) {
                return;
              } else {
                return;
              }
            }
          } else {
            z = true;
            z2 = false;
          }
          z4 = ActivityTaskManagerService.this.mAmInternal.getIsDataClearedInAms(string, myUserId);
        } catch (IllegalArgumentException e4) {
          z = true;
          z2 = false;
          e = e4;
        }
        if (z4) {
          Slog.e("ActivityTaskManager", "Checking for the Active launch isDataCleared :" + z4);
          return;
        }
        if ((packageManager != null ? packageManager.getApplicationEnabledSetting(string) : 0)
            == 4) {
          Slog.e(
              "ActivityTaskManager", "Checking for the Active launch getApplicationEnabledSetting");
          return;
        }
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.getInstance();
        if (mARsPolicyManager.isChinaPolicyEnabled() && !mARsPolicyManager.isAutoRunOn(string, 0)) {
          Slog.e("ActivityTaskManager", "Active launch : App auto run is off : " + string);
          return;
        }
        Slog.d(
            "ActivityTaskManager",
            "Checking for the Active launch isPkgEverLaunched :"
                + z2
                + ", isPkgStopped : "
                + z
                + ", isDataCleared :"
                + z4);
        if (packageManager != null || string == null || !z2 || z || z4) {
          return;
        }
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(string);
        if (launchIntentForPackage != null) {
          ActivityOptions makeBasic = ActivityOptions.makeBasic();
          if (makeBasic != null) {
            makeBasic.setActiveApplaunch(true);
            try {
              if (CoreRune.SYSPERF_VI_BOOST
                  && (activityManagerPerformance = ActivityTaskManagerService.this.mAMBooster)
                      != null) {
                activityManagerPerformance.notifyAnimationBoost(100);
              }
              if (equals) {
                context.startActivity(launchIntentForPackage, makeBasic.toBundle());
              } else {
                context.startActivityAsUser(
                    launchIntentForPackage, makeBasic.toBundle(), UserHandle.of(myUserId));
              }
            } catch (Exception e5) {
              Slog.i("ActivityTaskManager", "No activity to handle assist action.", e5);
            }
            Slog.d("ActivityTaskManager", "starting Active launch");
            return;
          }
          return;
        }
        Slog.e("ActivityTaskManager", "Received intent is null");
      }
    }
  }

  public void installSystemProviders() {
    this.mSettingsObserver = new SettingObserver();
  }

  /* JADX WARN: Removed duplicated region for block: B:44:0x0121 A[Catch: all -> 0x0159, TryCatch #0 {all -> 0x0159, blocks: (B:30:0x00ce, B:39:0x00fa, B:41:0x0107, B:42:0x010d, B:44:0x0121, B:45:0x0132, B:46:0x014f, B:51:0x010b, B:53:0x00ec), top: B:29:0x00ce }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void retrieveSettings(ContentResolver contentResolver) {
    boolean z;
    boolean z2 =
        this.mContext
                .getPackageManager()
                .hasSystemFeature("android.software.freeform_window_management")
            || Settings.Global.getInt(contentResolver, "enable_freeform_support", 0) != 0;
    boolean supportsMultiWindow = ActivityTaskManager.supportsMultiWindow(this.mContext);
    boolean z3 =
        supportsMultiWindow
            && this.mContext
                .getPackageManager()
                .hasSystemFeature("android.software.picture_in_picture");
    boolean z4 =
        z3
            && this.mContext
                .getPackageManager()
                .hasSystemFeature("android.software.expanded_picture_in_picture");
    boolean supportsSplitScreenMultiWindow =
        ActivityTaskManager.supportsSplitScreenMultiWindow(this.mContext);
    boolean hasSystemFeature =
        this.mContext
            .getPackageManager()
            .hasSystemFeature("android.software.activities_on_secondary_displays");
    boolean z5 = Settings.Global.getInt(contentResolver, "debug.force_rtl", 0) != 0;
    boolean z6 = Settings.Global.getInt(contentResolver, "force_resizable_activities", 0) != 0;
    boolean z7 =
        Settings.Global.getInt(contentResolver, "enable_non_resizable_multi_window", 0) != 0;
    int integer = this.mContext.getResources().getInteger(R.integer.config_undockedHdmiRotation);
    int integer2 =
        this.mContext
            .getResources()
            .getInteger(R.integer.config_screenBrightnessCapForWearBedtimeMode);
    float f = this.mContext.getResources().getFloat(R.dimen.config_highResTaskSnapshotScale);
    float f2 = this.mContext.getResources().getFloat(R.dimen.config_horizontalScrollFactor);
    DisplayProperties.debug_force_rtl(Boolean.valueOf(z5));
    Configuration configuration = new Configuration();
    Settings.System.getConfiguration(contentResolver, configuration);
    if (z5) {
      configuration.setLayoutDirection(configuration.locale);
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mForceResizableActivities = z6;
        this.mDevEnableNonResizableMultiWindow = z7;
        this.mSupportsNonResizableMultiWindow = integer;
        this.mRespectsActivityMinWidthHeightMultiWindow = integer2;
        this.mMinPercentageMultiWindowSupportHeight = f;
        this.mMinPercentageMultiWindowSupportWidth = f2;
        if (!z2 && !supportsSplitScreenMultiWindow && !z3 && !hasSystemFeature) {
          z = false;
          if ((!supportsMultiWindow || z6) && z) {
            this.mSupportsMultiWindow = true;
            this.mSupportsFreeformWindowManagement = z2;
            this.mSupportsSplitScreenMultiWindow = supportsSplitScreenMultiWindow;
            this.mSupportsPictureInPicture = z3;
            this.mSupportsExpandedPictureInPicture = z4;
            this.mSupportsMultiDisplay = hasSystemFeature;
          } else {
            this.mSupportsMultiWindow = false;
            this.mSupportsFreeformWindowManagement = false;
            this.mSupportsSplitScreenMultiWindow = false;
            this.mSupportsPictureInPicture = false;
            this.mSupportsExpandedPictureInPicture = false;
            if (hasSystemFeature) {
              this.mSupportsMultiDisplay = true;
            } else {
              this.mSupportsMultiDisplay = false;
            }
          }
          this.mWindowManager.mRoot.onSettingsRetrieved();
          updateConfigurationLocked(configuration, null, true);
          Configuration globalConfiguration = getGlobalConfiguration();
          if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_CONFIGURATION,
                -1305755880,
                0,
                (String) null,
                new Object[] {String.valueOf(globalConfiguration)});
          }
          Resources resources = this.mContext.getResources();
          this.mThumbnailWidth = resources.getDimensionPixelSize(R.dimen.thumbnail_width);
          this.mThumbnailHeight = resources.getDimensionPixelSize(R.dimen.thumbnail_height);
          this.mExt.installSystemProvidersLocked();
        }
        z = true;
        if (!supportsMultiWindow) {}
        this.mSupportsMultiWindow = true;
        this.mSupportsFreeformWindowManagement = z2;
        this.mSupportsSplitScreenMultiWindow = supportsSplitScreenMultiWindow;
        this.mSupportsPictureInPicture = z3;
        this.mSupportsExpandedPictureInPicture = z4;
        this.mSupportsMultiDisplay = hasSystemFeature;
        this.mWindowManager.mRoot.onSettingsRetrieved();
        updateConfigurationLocked(configuration, null, true);
        Configuration globalConfiguration2 = getGlobalConfiguration();
        if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {}
        Resources resources2 = this.mContext.getResources();
        this.mThumbnailWidth = resources2.getDimensionPixelSize(R.dimen.thumbnail_width);
        this.mThumbnailHeight = resources2.getDimensionPixelSize(R.dimen.thumbnail_height);
        this.mExt.installSystemProvidersLocked();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    this.mMultiTaskingController.retrieveSettings();
  }

  public WindowManagerGlobalLock getGlobalLock() {
    return this.mGlobalLock;
  }

  public ActivityTaskManagerInternal getAtmInternal() {
    return this.mInternal;
  }

  public void initialize(
      IntentFirewall intentFirewall,
      PendingIntentController pendingIntentController,
      Looper looper) {
    this.f1734mH = new HandlerC2878H(looper);
    this.mUiHandler = new UiHandler();
    this.mIntentFirewall = intentFirewall;
    File ensureSystemDir = SystemServiceManager.ensureSystemDir();
    this.mAppWarnings =
        createAppWarnings(this.mUiContext, this.f1734mH, this.mUiHandler, ensureSystemDir);
    this.mCompatModePackages = new CompatModePackages(this, ensureSystemDir, this.f1734mH);
    this.mPendingIntentController = pendingIntentController;
    this.mTaskSupervisor = createTaskSupervisor();
    this.mActivityClientController = new ActivityClientController(this);
    this.mTaskChangeNotificationController =
        new TaskChangeNotificationController(this.mTaskSupervisor, this.f1734mH);
    this.mLockTaskController =
        new LockTaskController(
            this.mContext,
            this.mTaskSupervisor,
            this.f1734mH,
            this.mTaskChangeNotificationController);
    this.mActivityStartController = new ActivityStartController(this);
    setRecentTasks(new RecentTasks(this, this.mTaskSupervisor));
    this.mVrController = new VrController(this.mGlobalLock);
    this.mKeyguardController = this.mTaskSupervisor.getKeyguardController();
    this.mPackageConfigPersister =
        new PackageConfigPersister(this.mTaskSupervisor.mPersisterQueue, this);
    this.mExt.initialize();
    Iterator it = this.mMWControllers.iterator();
    while (it.hasNext()) {
      ((IController) it.next()).initialize();
    }
    this.mPersonaActivityHelper = createPersonaActivityHelper();
  }

  public void onActivityManagerInternalAdded() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAmInternal =
            (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mUgmInternal =
            (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public int increaseConfigurationSeqLocked() {
    int i = this.mConfigurationSeq + 1;
    this.mConfigurationSeq = i;
    int max = Math.max(i, 1);
    this.mConfigurationSeq = max;
    return max;
  }

  public ActivityTaskSupervisor createTaskSupervisor() {
    ActivityTaskSupervisor activityTaskSupervisor =
        new ActivityTaskSupervisor(this, this.f1734mH.getLooper());
    activityTaskSupervisor.initialize();
    return activityTaskSupervisor;
  }

  public AppWarnings createAppWarnings(
      Context context, Handler handler, Handler handler2, File file) {
    return new AppWarnings(this, context, handler, handler2, file);
  }

  public void setWindowManager(WindowManagerService windowManagerService) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mWindowManager = windowManagerService;
        this.mRootWindowContainer = windowManagerService.mRoot;
        this.mWindowOrganizerController.mTransitionController.setWindowManager(
            windowManagerService);
        this.mTempConfig.setToDefaults();
        this.mTempConfig.setLocales(LocaleList.getDefault());
        Configuration configuration = this.mTempConfig;
        configuration.seq = 1;
        this.mConfigurationSeq = 1;
        this.mRootWindowContainer.onConfigurationChanged(configuration);
        this.mLockTaskController.setWindowManager(windowManagerService);
        this.mTaskSupervisor.setWindowManager(windowManagerService);
        this.mRootWindowContainer.setWindowManager(windowManagerService);
        this.mBackNavigationController.setWindowManager(windowManagerService);
        Iterator it = this.mMWControllers.iterator();
        while (it.hasNext()) {
          ((IController) it.next()).setWindowManager(windowManagerService);
        }
        this.mPersonaActivityHelper.setWindowManager(windowManagerService);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void setUsageStatsManager(UsageStatsManagerInternal usageStatsManagerInternal) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mUsageStatsInternal = usageStatsManagerInternal;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public Context getUiContext() {
    return this.mUiContext;
  }

  public UserManagerService getUserManager() {
    if (this.mUserManager == null) {
      this.mUserManager = IUserManager.Stub.asInterface(ServiceManager.getService("user"));
    }
    return this.mUserManager;
  }

  public AppOpsManager getAppOpsManager() {
    if (this.mAppOpsManager == null) {
      this.mAppOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
    }
    return this.mAppOpsManager;
  }

  public boolean hasUserRestriction(String str, int i) {
    return getUserManager().hasUserRestriction(str, i);
  }

  public boolean hasSystemAlertWindowPermission(int i, int i2, String str) {
    int noteOpNoThrow = getAppOpsManager().noteOpNoThrow(24, i, str, (String) null, "");
    return noteOpNoThrow == 3
        ? checkPermission("android.permission.SYSTEM_ALERT_WINDOW", i2, i) == 0
        : noteOpNoThrow == 0;
  }

  public void setRecentTasks(RecentTasks recentTasks) {
    this.mRecentTasks = recentTasks;
    this.mTaskSupervisor.setRecentTasks(recentTasks);
  }

  public RecentTasks getRecentTasks() {
    return this.mRecentTasks;
  }

  public ClientLifecycleManager getLifecycleManager() {
    return this.mLifecycleManager;
  }

  public ActivityStartController getActivityStartController() {
    return this.mActivityStartController;
  }

  public TaskChangeNotificationController getTaskChangeNotificationController() {
    return this.mTaskChangeNotificationController;
  }

  public LockTaskController getLockTaskController() {
    return this.mLockTaskController;
  }

  public TransitionController getTransitionController() {
    return this.mWindowOrganizerController.getTransitionController();
  }

  public Configuration getGlobalConfigurationForCallingPid() {
    return getGlobalConfigurationForPid(Binder.getCallingPid());
  }

  public Configuration getGlobalConfigurationForPid(int i) {
    Configuration configuration;
    if (i == WindowManagerService.MY_PID || i < 0) {
      return getGlobalConfiguration();
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        WindowProcessController process = this.mProcessMap.getProcess(i);
        configuration = process != null ? process.getConfiguration() : getGlobalConfiguration();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return configuration;
  }

  public ConfigurationInfo getDeviceConfigurationInfo() {
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

  public BackgroundActivityStartCallback getBackgroundActivityStartCallback() {
    return this.mBackgroundActivityStartCallback;
  }

  public SparseArray getActivityInterceptorCallbacks() {
    return this.mActivityInterceptorCallbacks;
  }

  public final void start() {
    LocalServices.addService(ActivityTaskManagerInternal.class, this.mInternal);
  }

  public final class Lifecycle extends SystemService {
    public final ActivityTaskManagerService mService;

    public Lifecycle(Context context) {
      super(context);
      this.mService = new ActivityTaskManagerService(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
      publishBinderService("activity_task", this.mService);
      this.mService.start();
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(SystemService.TargetUser targetUser) {
      WindowManagerGlobalLock globalLock = this.mService.getGlobalLock();
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (globalLock) {
        try {
          this.mService.mTaskSupervisor.onUserUnlocked(targetUser.getUserIdentifier());
          if (CoreRune.SM_SUPPORT_RISK_CONTROL) {
            this.mService.mExt.onUserUnlocked();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(SystemService.TargetUser targetUser) {
      WindowManagerGlobalLock globalLock = this.mService.getGlobalLock();
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (globalLock) {
        try {
          this.mService.mTaskSupervisor.mLaunchParamsPersister.onCleanupUser(
              targetUser.getUserIdentifier());
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    public ActivityTaskManagerService getService() {
      return this.mService;
    }
  }

  public final int startActivity(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle) {
    ActivityOptions fromBundle;
    if ("VZW".equals(SystemProperties.get("ro.csc.sales_code"))
        && intent != null
        && intent.getComponent() != null
        && "android.intent.action.MAIN".equals(intent.getAction())
        && bundle != null
        && (fromBundle = ActivityOptions.fromBundle(bundle)) != null
        && !fromBundle.isActiveApplaunch()
        && fromBundle.isMlLaunch() == -1) {
      SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
      if (semWifiManager != null) {
        semWifiManager.checkAppForWiFiOffloading(intent.getComponent().getPackageName());
      }
    }
    return startActivityAsUser(
        iApplicationThread,
        str,
        str2,
        intent,
        str3,
        iBinder,
        str4,
        i,
        i2,
        profilerInfo,
        bundle,
        UserHandle.getCallingUserId());
  }

  public final int startActivities(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent[] intentArr,
      String[] strArr,
      IBinder iBinder,
      Bundle bundle,
      int i) {
    assertPackageMatchesCallingUid(str);
    enforceNotIsolatedCaller("startActivities");
    return getActivityStartController()
        .startActivities(
            iApplicationThread,
            -1,
            0,
            -1,
            str,
            str2,
            intentArr,
            strArr,
            iBinder,
            SafeActivityOptions.fromBundle(bundle),
            handleIncomingUser(
                Binder.getCallingPid(), Binder.getCallingUid(), i, "startActivities"),
            "startActivities",
            null,
            BackgroundStartPrivileges.NONE);
  }

  public int startActivityAsUser(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i3) {
    return startActivityAsUser(
        iApplicationThread,
        str,
        str2,
        intent,
        str3,
        iBinder,
        str4,
        i,
        i2,
        profilerInfo,
        bundle,
        i3,
        true);
  }

  public final int startActivityAsUser(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i3,
      boolean z) {
    String str5;
    int hiddenDisplayId;
    Intent intent2 = intent;
    if ((this.mSIOPLevel >= 6 || this.mBatteryOverheatLevel > 0) && !isPossibleToStart(intent2)) {
      Message obtainMessage = this.f1734mH.obtainMessage(5);
      obtainMessage.obj = "";
      ComponentName component = intent.getComponent();
      r6 = component != null ? component.getPackageName() : null;
      PackageManager packageManager = this.mContext.getPackageManager();
      if (r6 != null) {
        try {
          r6 =
              packageManager
                  .getApplicationLabel(packageManager.getApplicationInfo(r6, IInstalld.FLAG_FORCE))
                  .toString();
        } catch (PackageManager.NameNotFoundException e) {
          Slog.w("ActivityTaskManager", "No such package", e);
        }
        if (r6 != null) {
          obtainMessage.obj = r6;
        }
      }
      this.f1734mH.sendMessage(obtainMessage);
      return -102;
    }
    SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(bundle);
    assertPackageMatchesCallingUid(str);
    enforceNotIsolatedCaller("startActivityAsUser");
    if (intent2 != null && intent2.isSandboxActivity(this.mContext)) {
      ((SdkSandboxManagerLocal) LocalManagerRegistry.getManager(SdkSandboxManagerLocal.class))
          .enforceAllowedToHostSandboxedActivity(intent2, Binder.getCallingUid(), str);
    }
    if (Process.isSdkSandboxUid(Binder.getCallingUid())) {
      SdkSandboxManagerLocal sdkSandboxManagerLocal =
          (SdkSandboxManagerLocal) LocalManagerRegistry.getManager(SdkSandboxManagerLocal.class);
      if (sdkSandboxManagerLocal == null) {
        throw new IllegalStateException(
            "SdkSandboxManagerLocal not found when starting an activity from an SDK sandbox uid.");
      }
      sdkSandboxManagerLocal.enforceAllowedToStartActivity(intent2);
    }
    Slog.d(
        "ActivityTaskManager",
        "startActivityAsUser: callingPid="
            + Binder.getCallingPid()
            + ", callingUid="
            + Binder.getCallingUid()
            + ", caller="
            + Debug.getCallers(7));
    int checkTargetUser =
        getActivityStartController()
            .checkTargetUser(
                i3, z, Binder.getCallingPid(), Binder.getCallingUid(), "startActivityAsUser");
    if (CoreRune.SYSFW_APP_SPEG
        && str != null
        && (hiddenDisplayId =
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                    .getHiddenDisplayId(str))
            != -1) {
      if (ActivityStartController.isExternalOrNoComponentIntent(str, intent2)) {
        Slog.w(
            "SPEG",
            "Application "
                + str
                + " is trying to startActivityAsUser an extrnal intent "
                + intent2);
        return 102;
      }
      ActivityStartController.adjustOptions(bundle, hiddenDisplayId).toBundle();
      Slog.d("SPEG", "Reuse hidden display #" + hiddenDisplayId + " for " + intent2);
    }
    Intent notifyStartActivityAsUser =
        this.mPersonaActivityHelper.notifyStartActivityAsUser(
            intent2, str3, checkTargetUser, mKnoxInfo);
    if (notifyStartActivityAsUser != null) {
      intent2 = notifyStartActivityAsUser;
    }
    if (SemDualAppManager.isDualAppId(SemDualAppManager.getDualAppProfileId())) {
      int userId = UserHandle.getUserId(Binder.getCallingUid());
      Intent startDAChooserActivity =
          DualAppManagerService.startDAChooserActivity(intent2, userId, checkTargetUser, str);
      if (startDAChooserActivity != null) {
        intent2 = startDAChooserActivity;
      } else {
        r6 = str3;
      }
      if (SemDualAppManager.isDualAppId(userId)) {
        if (intent2.getComponent() != null) {
          str5 = intent2.getComponent().getPackageName();
        } else {
          str5 = intent2.getPackage();
        }
        if (str != null
            && str5 != null
            && !str.equals(str5)
            && DualAppManagerService.shouldForwardToOwner(str5)) {
          checkTargetUser = 0;
        }
      }
    } else {
      r6 = str3;
    }
    return getActivityStartController()
        .obtainStarter(intent2, "startActivityAsUser")
        .setCaller(iApplicationThread)
        .setCallingPackage(str)
        .setCallingFeatureId(str2)
        .setResolvedType(r6)
        .setResultTo(iBinder)
        .setResultWho(str4)
        .setRequestCode(i)
        .setStartFlags(i2)
        .setProfilerInfo(profilerInfo)
        .setActivityOptions(fromBundle)
        .setUserId(checkTargetUser)
        .execute();
  }

  public int startActivityIntentSender(
      IApplicationThread iApplicationThread,
      IIntentSender iIntentSender,
      IBinder iBinder,
      Intent intent,
      String str,
      IBinder iBinder2,
      String str2,
      int i,
      int i2,
      int i3,
      Bundle bundle) {
    enforceNotIsolatedCaller("startActivityIntentSender");
    if (intent != null && intent.hasFileDescriptors()) {
      throw new IllegalArgumentException("File descriptors passed in Intent");
    }
    if (!(iIntentSender instanceof PendingIntentRecord)) {
      throw new IllegalArgumentException("Bad PendingIntent object");
    }
    PendingIntentRecord pendingIntentRecord = (PendingIntentRecord) iIntentSender;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask != null
            && topDisplayFocusedRootTask.getTopResumedActivity() != null
            && topDisplayFocusedRootTask.getTopResumedActivity().info.applicationInfo.uid
                == Binder.getCallingUid()) {
          this.mAppSwitchesState = 2;
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return pendingIntentRecord.sendInner(
        iApplicationThread, 0, intent, str, iBinder, null, null, iBinder2, str2, i, i2, i3, bundle);
  }

  /* JADX WARN: Removed duplicated region for block: B:42:0x010c A[Catch: all -> 0x01c7, TRY_ENTER, TryCatch #1 {all -> 0x01c7, blocks: (B:10:0x001b, B:12:0x0022, B:13:0x0025, B:16:0x002a, B:18:0x0030, B:19:0x0033, B:22:0x0038, B:27:0x005c, B:29:0x0074, B:32:0x007d, B:34:0x008f, B:37:0x009d, B:39:0x00a0, B:70:0x00ac, B:73:0x00fa, B:75:0x00e2, B:42:0x010c, B:44:0x0111, B:45:0x0118, B:48:0x011d, B:50:0x0144, B:51:0x0147, B:59:0x01ba, B:60:0x01bd, B:65:0x01c3, B:66:0x01c6, B:68:0x014d, B:53:0x0156, B:55:0x017e, B:56:0x0180), top: B:9:0x001b, inners: #0 }] */
  /* JADX WARN: Removed duplicated region for block: B:48:0x011d A[Catch: all -> 0x01c7, TRY_ENTER, TryCatch #1 {all -> 0x01c7, blocks: (B:10:0x001b, B:12:0x0022, B:13:0x0025, B:16:0x002a, B:18:0x0030, B:19:0x0033, B:22:0x0038, B:27:0x005c, B:29:0x0074, B:32:0x007d, B:34:0x008f, B:37:0x009d, B:39:0x00a0, B:70:0x00ac, B:73:0x00fa, B:75:0x00e2, B:42:0x010c, B:44:0x0111, B:45:0x0118, B:48:0x011d, B:50:0x0144, B:51:0x0147, B:59:0x01ba, B:60:0x01bd, B:65:0x01c3, B:66:0x01c6, B:68:0x014d, B:53:0x0156, B:55:0x017e, B:56:0x0180), top: B:9:0x001b, inners: #0 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) {
    ActivityInfo activityInfo;
    List list;
    int size;
    int i;
    if (intent != null && intent.hasFileDescriptors()) {
      throw new IllegalArgumentException("File descriptors passed in Intent");
    }
    SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(bundle);
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
        if (isInRootTaskLocked == null) {
          SafeActivityOptions.abort(fromBundle);
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        if (!isInRootTaskLocked.attachedToProcess()) {
          SafeActivityOptions.abort(fromBundle);
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        Intent intent2 = new Intent(intent);
        intent2.setDataAndType(
            isInRootTaskLocked.intent.getData(), isInRootTaskLocked.intent.getType());
        intent2.setComponent(null);
        boolean z = (intent2.getFlags() & 8) != 0;
        try {
          list =
              AppGlobals.getPackageManager()
                  .queryIntentActivities(
                      intent2,
                      isInRootTaskLocked.resolvedType,
                      66560L,
                      UserHandle.getCallingUserId())
                  .getList();
          size = list != null ? list.size() : 0;
        } catch (RemoteException unused) {
        }
        for (i = 0; i < size; i++) {
          ResolveInfo resolveInfo = (ResolveInfo) list.get(i);
          if (resolveInfo.activityInfo.packageName.equals(isInRootTaskLocked.packageName)
              && resolveInfo.activityInfo.name.equals(isInRootTaskLocked.info.name)) {
            int i2 = i + 1;
            activityInfo = i2 < size ? ((ResolveInfo) list.get(i2)).activityInfo : null;
            if (z) {
              try {
                Slog.v(
                    "ActivityTaskManager",
                    "Next matching activity: found current "
                        + isInRootTaskLocked.packageName
                        + "/"
                        + isInRootTaskLocked.info.name);
                StringBuilder sb = new StringBuilder();
                sb.append("Next matching activity: next is ");
                sb.append(
                    activityInfo == null
                        ? "null"
                        : activityInfo.packageName + "/" + activityInfo.name);
                Slog.v("ActivityTaskManager", sb.toString());
              } catch (RemoteException unused2) {
              }
            }
            if (activityInfo != null) {
              SafeActivityOptions.abort(fromBundle);
              if (z) {
                Slog.d("ActivityTaskManager", "Next matching activity: nothing found");
              }
              WindowManagerService.resetPriorityAfterLockedSection();
              return false;
            }
            intent2.setComponent(
                new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            intent2.setFlags(intent2.getFlags() & (-503316481));
            boolean z2 = isInRootTaskLocked.finishing;
            isInRootTaskLocked.finishing = true;
            ActivityRecord activityRecord = isInRootTaskLocked.resultTo;
            String str = isInRootTaskLocked.resultWho;
            int i3 = isInRootTaskLocked.requestCode;
            isInRootTaskLocked.resultTo = null;
            if (activityRecord != null) {
              activityRecord.removeResultsLocked(isInRootTaskLocked, str, i3);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (fromBundle == null) {
              try {
                fromBundle = new SafeActivityOptions(ActivityOptions.makeBasic());
              } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
              }
            }
            fromBundle.getOptions(isInRootTaskLocked).setAvoidMoveToFront();
            int execute =
                getActivityStartController()
                    .obtainStarter(intent2, "startNextMatchingActivity")
                    .setCaller(isInRootTaskLocked.app.getThread())
                    .setResolvedType(isInRootTaskLocked.resolvedType)
                    .setActivityInfo(activityInfo)
                    .setResultTo(activityRecord != null ? activityRecord.token : null)
                    .setResultWho(str)
                    .setRequestCode(i3)
                    .setCallingPid(-1)
                    .setCallingUid(isInRootTaskLocked.launchedFromUid)
                    .setCallingPackage(isInRootTaskLocked.launchedFromPackage)
                    .setCallingFeatureId(isInRootTaskLocked.launchedFromFeatureId)
                    .setRealCallingPid(-1)
                    .setRealCallingUid(isInRootTaskLocked.launchedFromUid)
                    .setActivityOptions(fromBundle)
                    .execute();
            isInRootTaskLocked.finishing = z2;
            boolean z3 = execute == 0;
            WindowManagerService.resetPriorityAfterLockedSection();
            return z3;
          }
        }
        activityInfo = null;
        if (activityInfo != null) {}
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public boolean isDreaming() {
    return this.mActiveDreamComponent != null;
  }

  public boolean canLaunchDreamActivity(String str) {
    if (this.mActiveDreamComponent == null || str == null) {
      if (ProtoLogCache.WM_DEBUG_DREAM_enabled) {
        ProtoLogImpl.e(
            ProtoLogGroup.WM_DEBUG_DREAM,
            -787664727,
            0,
            "Cannot launch dream activity due to invalid state. dream component: %s packageName:"
                + " %s",
            new Object[] {String.valueOf(this.mActiveDreamComponent), String.valueOf(str)});
      }
      return false;
    }
    if (str.equals(this.mActiveDreamComponent.getPackageName())) {
      return true;
    }
    if (ProtoLogCache.WM_DEBUG_DREAM_enabled) {
      ProtoLogImpl.e(
          ProtoLogGroup.WM_DEBUG_DREAM,
          601283564,
          0,
          "Dream packageName does not match active dream. Package %s does not match %s",
          new Object[] {str, String.valueOf(this.mActiveDreamComponent)});
    }
    return false;
  }

  public final void enforceCallerIsDream(String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (canLaunchDreamActivity(str)) {
      } else {
        throw new SecurityException(
            "The dream activity can be started only when the device is dreaming and only by the"
                + " active dream package.");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean startDreamActivity(Intent intent) {
    assertPackageMatchesCallingUid(intent.getPackage());
    enforceCallerIsDream(intent.getPackage());
    ActivityInfo activityInfo = new ActivityInfo();
    activityInfo.theme = R.style.Theme.Material.Light.Dialog.NoActionBar.FixedSize;
    activityInfo.exported = true;
    activityInfo.name = DreamActivity.class.getName();
    activityInfo.enabled = true;
    activityInfo.launchMode = 3;
    activityInfo.persistableMode = 1;
    activityInfo.screenOrientation = -1;
    activityInfo.colorMode = 0;
    activityInfo.flags |= 32;
    activityInfo.resizeMode = 0;
    activityInfo.configChanges = -1;
    ActivityOptions makeBasic = ActivityOptions.makeBasic();
    makeBasic.setLaunchActivityType(5);
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        WindowProcessController process = this.mProcessMap.getProcess(Binder.getCallingPid());
        ApplicationInfo applicationInfo = process.mInfo;
        activityInfo.packageName = applicationInfo.packageName;
        activityInfo.applicationInfo = applicationInfo;
        activityInfo.processName = process.mName;
        activityInfo.uiOptions = applicationInfo.uiOptions;
        activityInfo.taskAffinity = "android:" + activityInfo.packageName + "/dream";
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getActivityStartController()
              .obtainStarter(intent, "dream")
              .setCallingUid(callingUid)
              .setCallingPid(callingPid)
              .setCallingPackage(intent.getPackage())
              .setActivityInfo(activityInfo)
              .setActivityOptions(createSafeActivityOptionsWithBalAllowed(makeBasic))
              .setRealCallingUid(Binder.getCallingUid())
              .setBackgroundStartPrivileges(BackgroundStartPrivileges.ALLOW_BAL)
              .execute();
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return true;
  }

  public final WaitResult startActivityAndWait(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i3) {
    assertPackageMatchesCallingUid(str);
    WaitResult waitResult = new WaitResult();
    enforceNotIsolatedCaller("startActivityAndWait");
    getActivityStartController()
        .obtainStarter(intent, "startActivityAndWait")
        .setCaller(iApplicationThread)
        .setCallingPackage(str)
        .setCallingFeatureId(str2)
        .setResolvedType(str3)
        .setResultTo(iBinder)
        .setResultWho(str4)
        .setRequestCode(i)
        .setStartFlags(i2)
        .setActivityOptions(bundle)
        .setUserId(
            handleIncomingUser(
                Binder.getCallingPid(), Binder.getCallingUid(), i3, "startActivityAndWait"))
        .setProfilerInfo(profilerInfo)
        .setWaitResult(waitResult)
        .execute();
    return waitResult;
  }

  public final int startActivityWithConfig(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      Configuration configuration,
      Bundle bundle,
      int i3) {
    assertPackageMatchesCallingUid(str);
    enforceNotIsolatedCaller("startActivityWithConfig");
    return getActivityStartController()
        .obtainStarter(intent, "startActivityWithConfig")
        .setCaller(iApplicationThread)
        .setCallingPackage(str)
        .setCallingFeatureId(str2)
        .setResolvedType(str3)
        .setResultTo(iBinder)
        .setResultWho(str4)
        .setRequestCode(i)
        .setStartFlags(i2)
        .setGlobalConfiguration(configuration)
        .setActivityOptions(bundle)
        .setUserId(
            handleIncomingUser(
                Binder.getCallingPid(), Binder.getCallingUid(), i3, "startActivityWithConfig"))
        .execute();
  }

  /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:

     if (r6.getComponent() == null) goto L30;
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x0074, code lost:

     if (r6.getSelector() != null) goto L28;
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x007e, code lost:

     throw new java.lang.SecurityException("Selector not allowed with ignoreTargetSecurity");
  */
  /* JADX WARN: Code restructure failed: missing block: B:32:0x0086, code lost:

     throw new java.lang.SecurityException("Component must be specified with ignoreTargetSecurity");
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int startActivityAsCaller(
      IApplicationThread iApplicationThread,
      String str,
      Intent intent,
      String str2,
      IBinder iBinder,
      String str3,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      boolean z,
      int i3) {
    ActivityRecord isInAnyTask;
    int i4;
    String str4;
    String str5;
    boolean isResolverOrChildActivity;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (iBinder == null) {
          throw new SecurityException("Must be called from an activity");
        }
        isInAnyTask = ActivityRecord.isInAnyTask(iBinder);
        if (isInAnyTask == null) {
          throw new SecurityException("Called with bad activity token: " + iBinder);
        }
        if (isInAnyTask.app == null) {
          throw new SecurityException("Called without a process attached to activity");
        }
        if (checkCallingPermission("android.permission.START_ACTIVITY_AS_CALLER") != 0) {
          if (!isInAnyTask.info.packageName.equals("android")) {
            throw new SecurityException(
                "Must be called from an activity that is declared in the android package");
          }
          if (UserHandle.getAppId(isInAnyTask.app.mUid) != 1000
              && isInAnyTask.app.mUid != isInAnyTask.launchedFromUid) {
            throw new SecurityException(
                "Calling activity in uid "
                    + isInAnyTask.app.mUid
                    + " must be system uid or original calling uid "
                    + isInAnyTask.launchedFromUid);
          }
        }
        i4 = isInAnyTask.launchedFromUid;
        str4 = isInAnyTask.launchedFromPackage;
        str5 = isInAnyTask.launchedFromFeatureId;
        isResolverOrChildActivity = isInAnyTask.isResolverOrChildActivity();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    if (i3 == -10000) {
      i3 = UserHandle.getUserId(isInAnyTask.app.mUid);
    }
    ActivityStarter ignoreTargetSecurity =
        getActivityStartController()
            .obtainStarter(intent, "startActivityAsCaller")
            .setCallingUid(i4)
            .setCallingPackage(str4)
            .setCallingFeatureId(str5)
            .setResolvedType(str2)
            .setResultTo(iBinder)
            .setResultWho(str3)
            .setRequestCode(i)
            .setStartFlags(i2)
            .setActivityOptions(createSafeActivityOptionsWithBalAllowed(bundle))
            .setUserId(i3)
            .setIgnoreTargetSecurity(z);
    if (isResolverOrChildActivity) {
      i4 = 0;
    }
    return ignoreTargetSecurity
        .setFilterCallingUid(i4)
        .setBackgroundStartPrivileges(BackgroundStartPrivileges.ALLOW_BAL)
        .execute();
  }

  public int handleIncomingUser(int i, int i2, int i3, String str) {
    return this.mAmInternal.handleIncomingUser(i, i2, i3, false, 0, str, (String) null);
  }

  public int startVoiceActivity(
      String str,
      String str2,
      int i,
      int i2,
      Intent intent,
      String str3,
      IVoiceInteractionSession iVoiceInteractionSession,
      IVoiceInteractor iVoiceInteractor,
      int i3,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i4) {
    assertPackageMatchesCallingUid(str);
    this.mAmInternal.enforceCallingPermission(
        "android.permission.BIND_VOICE_INTERACTION", "startVoiceActivity()");
    if (iVoiceInteractionSession == null || iVoiceInteractor == null) {
      throw new NullPointerException("null session or interactor");
    }
    return getActivityStartController()
        .obtainStarter(intent, "startVoiceActivity")
        .setCallingUid(i2)
        .setCallingPackage(str)
        .setCallingFeatureId(str2)
        .setResolvedType(str3)
        .setVoiceSession(iVoiceInteractionSession)
        .setVoiceInteractor(iVoiceInteractor)
        .setStartFlags(i3)
        .setProfilerInfo(profilerInfo)
        .setActivityOptions(createSafeActivityOptionsWithBalAllowed(bundle))
        .setUserId(handleIncomingUser(i, i2, i4, "startVoiceActivity"))
        .setBackgroundStartPrivileges(BackgroundStartPrivileges.ALLOW_BAL)
        .execute();
  }

  public String getVoiceInteractorPackageName(IBinder iBinder) {
    return ((VoiceInteractionManagerInternal)
            LocalServices.getService(VoiceInteractionManagerInternal.class))
        .getVoiceInteractorPackageName(iBinder);
  }

  public int startAssistantActivity(
      String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3) {
    assertPackageMatchesCallingUid(str);
    this.mAmInternal.enforceCallingPermission(
        "android.permission.BIND_VOICE_INTERACTION", "startAssistantActivity()");
    int handleIncomingUser = handleIncomingUser(i, i2, i3, "startAssistantActivity");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return getActivityStartController()
          .obtainStarter(intent, "startAssistantActivity")
          .setCallingUid(i2)
          .setCallingPackage(str)
          .setCallingFeatureId(str2)
          .setResolvedType(str3)
          .setActivityOptions(createSafeActivityOptionsWithBalAllowed(bundle))
          .setUserId(handleIncomingUser)
          .setBackgroundStartPrivileges(BackgroundStartPrivileges.ALLOW_BAL)
          .execute();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void startRecentsActivity(
      Intent intent, long j, IRecentsAnimationRunner iRecentsAnimationRunner) {
    enforceTaskPermission("startRecentsActivity()");
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          RecentsAnimation recentsAnimation =
              new RecentsAnimation(
                  this,
                  this.mTaskSupervisor,
                  getActivityStartController(),
                  this.mWindowManager,
                  intent,
                  this.mRecentTasks.getRecentsComponent(),
                  this.mRecentTasks.getRecentsComponentFeatureId(),
                  this.mRecentTasks.getRecentsComponentUid(),
                  getProcessController(callingPid, callingUid));
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

  public final int startActivityFromRecents(int i, Bundle bundle) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.START_TASKS_FROM_RECENTS", "startActivityFromRecents()");
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(bundle);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        return this.mTaskSupervisor.startActivityFromRecents(callingPid, callingUid, i, fromBundle);
      } catch (SecurityException e) {
        Slog.w("ActivityTaskManager", "startActivity: reason=startActivityFromRecents", e);
        throw e;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int startActivityFromGameSession(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      int i,
      int i2,
      Intent intent,
      int i3,
      int i4) {
    if (checkCallingPermission("android.permission.MANAGE_GAME_ACTIVITY") != 0) {
      String str3 =
          "Permission Denial: startActivityFromGameSession() from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " requires android.permission.MANAGE_GAME_ACTIVITY";
      Slog.w("ActivityTaskManager", str3);
      throw new SecurityException(str3);
    }
    assertPackageMatchesCallingUid(str);
    ActivityOptions makeBasic = ActivityOptions.makeBasic();
    makeBasic.setLaunchTaskId(i3);
    int handleIncomingUser = handleIncomingUser(i, i2, i4, "startActivityFromGameSession");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return getActivityStartController()
          .obtainStarter(intent, "startActivityFromGameSession")
          .setCaller(iApplicationThread)
          .setCallingUid(i2)
          .setCallingPid(i)
          .setCallingPackage(intent.getPackage())
          .setCallingFeatureId(str2)
          .setUserId(handleIncomingUser)
          .setActivityOptions(makeBasic.toBundle())
          .setRealCallingUid(Binder.getCallingUid())
          .execute();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public BackNavigationInfo startBackNavigation(
      RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.START_TASKS_FROM_RECENTS", "startBackNavigation()");
    return this.mBackNavigationController.startBackNavigation(remoteCallback, backAnimationAdapter);
  }

  public final boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) {
    boolean canPlaceEntityOnDisplay;
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ActivityInfo resolveActivityInfoForIntent =
          resolveActivityInfoForIntent(intent, str, i2, callingUid, callingPid);
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          canPlaceEntityOnDisplay =
              this.mTaskSupervisor.canPlaceEntityOnDisplay(
                  i, callingPid, callingUid, resolveActivityInfoForIntent);
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

  public ActivityInfo resolveActivityInfoForIntent(
      Intent intent, String str, int i, int i2, int i3) {
    return this.mAmInternal.getActivityInfoForUser(
        this.mTaskSupervisor.resolveActivity(
            intent, str, 0, null, i, ActivityStarter.computeResolveFilterUid(i2, i2, -10000), i3),
        i);
  }

  public IActivityClientController getActivityClientController() {
    return this.mActivityClientController;
  }

  public void applyUpdateLockStateLocked(final ActivityRecord activityRecord) {
    final boolean z = activityRecord != null && activityRecord.immersive;
    this.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$applyUpdateLockStateLocked$0(z, activityRecord);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyUpdateLockStateLocked$0(
      boolean z, ActivityRecord activityRecord) {
    if (this.mUpdateLock.isHeld() != z) {
      if (ProtoLogCache.WM_DEBUG_IMMERSIVE_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_IMMERSIVE,
            556758086,
            0,
            (String) null,
            new Object[] {String.valueOf(z), String.valueOf(activityRecord)});
      }
      if (z) {
        this.mUpdateLock.acquire();
      } else {
        this.mUpdateLock.release();
      }
    }
  }

  public boolean isTopActivityImmersive() {
    enforceNotIsolatedCaller("isTopActivityImmersive");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        boolean z = false;
        if (topDisplayFocusedRootTask == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        ActivityRecord activityRecord = topDisplayFocusedRootTask.topRunningActivity();
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

  public int getFrontActivityScreenCompatMode() {
    enforceNotIsolatedCaller("getFrontActivityScreenCompatMode");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        ActivityRecord activityRecord =
            topDisplayFocusedRootTask != null
                ? topDisplayFocusedRootTask.topRunningActivity()
                : null;
        if (activityRecord == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return -3;
        }
        int computeCompatModeLocked =
            this.mCompatModePackages.computeCompatModeLocked(activityRecord.info.applicationInfo);
        WindowManagerService.resetPriorityAfterLockedSection();
        return computeCompatModeLocked;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void setFrontActivityScreenCompatMode(int i) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.SET_SCREEN_COMPATIBILITY", "setFrontActivityScreenCompatMode");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        ActivityRecord activityRecord =
            topDisplayFocusedRootTask != null
                ? topDisplayFocusedRootTask.topRunningActivity()
                : null;
        if (activityRecord == null) {
          Slog.w("ActivityTaskManager", "setFrontActivityScreenCompatMode failed: no top activity");
          WindowManagerService.resetPriorityAfterLockedSection();
        } else {
          this.mCompatModePackages.setPackageScreenCompatModeLocked(
              activityRecord.info.applicationInfo, i);
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() {
    enforceTaskPermission("getFocusedRootTaskInfo()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
          if (topDisplayFocusedRootTask != null) {
            ActivityTaskManager.RootTaskInfo rootTaskInfo =
                this.mRootWindowContainer.getRootTaskInfo(topDisplayFocusedRootTask.mTaskId);
            WindowManagerService.resetPriorityAfterLockedSection();
            return rootTaskInfo;
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          Binder.restoreCallingIdentity(clearCallingIdentity);
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

  public void setFocusedRootTask(int i) {
    enforceTaskPermission("setFocusedRootTask()");
    if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_FOCUS,
          255339989,
          1,
          (String) null,
          new Object[] {Long.valueOf(i)});
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
          ActivityRecord activityRecord = rootTask.topRunningActivity();
          if (activityRecord != null
              && activityRecord.moveFocusableActivityToTop("setFocusedRootTask")) {
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

  public void setFocusedTask(int i) {
    enforceTaskPermission("setFocusedTask()");
    Slog.d(
        "ActivityTaskManager", "setFocusedTask: taskId=" + i + ", pid=" + Binder.getCallingPid());
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

  /* JADX WARN: Finally extract failed */
  public void focusTopTask(int i) {
    enforceTaskPermission("focusTopTask()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(i);
          if (displayContent != null) {
            Task task =
                displayContent.getTask(
                    new Predicate() { // from class:
                                      // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda11
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$focusTopTask$1;
                        lambda$focusTopTask$1 =
                            ActivityTaskManagerService.lambda$focusTopTask$1((Task) obj);
                        return lambda$focusTopTask$1;
                      }
                    },
                    true);
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

  public static /* synthetic */ boolean lambda$focusTopTask$1(Task task) {
    return task.isLeafTask()
        && task.isTopActivityFocusable()
        && !task.isFreeformForceHidden()
        && !(task.isFreeformPinned() && task.isMinimized());
  }

  public void setFocusedTask(int i, ActivityRecord activityRecord) {
    ActivityRecord activityRecord2;
    TaskFragment taskFragment;
    if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_FOCUS,
          -55185509,
          1,
          (String) null,
          new Object[] {Long.valueOf(i), String.valueOf(activityRecord)});
    }
    Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
    if (anyTaskForId == null
        || (activityRecord2 = anyTaskForId.topRunningActivityLocked()) == null) {
      return;
    }
    if ((activityRecord == null || activityRecord2 == activityRecord)
        && activityRecord2.isState(ActivityRecord.State.RESUMED)
        && activityRecord2 == this.mRootWindowContainer.getTopResumedActivity()) {
      setLastResumedActivityUncheckLocked(activityRecord2, "setFocusedTask-alreadyTop");
      return;
    }
    Transition createTransition =
        (getTransitionController().isCollecting()
                || !getTransitionController().isShellTransitionsEnabled())
            ? null
            : getTransitionController().createTransition(3);
    if (createTransition != null) {
      createTransition.setReady(anyTaskForId, true);
    }
    boolean moveFocusableActivityToTop =
        activityRecord2.moveFocusableActivityToTop("setFocusedTask");
    if (moveFocusableActivityToTop) {
      if (createTransition != null) {
        getTransitionController().requestStartTransition(createTransition, null, null, null);
      }
      this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    } else if (activityRecord != null
        && activityRecord.isFocusable()
        && (taskFragment = activityRecord.getTaskFragment()) != null
        && taskFragment.isEmbedded()) {
      activityRecord.getDisplayContent().setFocusedApp(activityRecord);
      this.mWindowManager.updateFocusedWindowLocked(0, true);
    }
    if (createTransition == null || moveFocusableActivityToTop) {
      return;
    }
    createTransition.abort();
  }

  public boolean removeTask(int i) {
    this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeTask()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
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

  public boolean removeTaskWithFlags(int i, int i2) {
    boolean removeTaskWithFlagsLocked;
    this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeTask()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Slog.i(
            "ActivityTaskManager",
            "removeTaskWithFlags: callingUid="
                + Binder.getCallingUid()
                + ", callerPid="
                + Binder.getCallingPid()
                + ", taskId="
                + i
                + ", flags="
                + i2
                + Debug.getCaller());
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

  public void removeAllVisibleRecentTasks() {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.REMOVE_TASKS", "removeAllVisibleRecentTasks()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getRecentTasks().removeAllVisibleTasks(this.mAmInternal.getCurrentUserId());
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

  public Rect getTaskBounds(int i) {
    enforceTaskPermission("getTaskBounds()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    Rect rect = new Rect();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
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

  public ActivityManager.TaskDescription getTaskDescription(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        enforceTaskPermission("getTaskDescription()");
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
        if (anyTaskForId == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        ActivityManager.TaskDescription taskDescription = anyTaskForId.getTaskDescription();
        WindowManagerService.resetPriorityAfterLockedSection();
        return taskDescription;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void setLocusId(LocusId locusId, IBinder iBinder) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
        if (isInRootTaskLocked != null) {
          isInRootTaskLocked.setLocusId(locusId);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public NeededUriGrants collectGrants(Intent intent, ActivityRecord activityRecord) {
    if (activityRecord != null) {
      return this.mUgmInternal.checkGrantUriPermissionFromIntent(
          intent, Binder.getCallingUid(), activityRecord.packageName, activityRecord.mUserId);
    }
    return null;
  }

  public void unhandledBack() {
    this.mAmInternal.enforceCallingPermission("android.permission.FORCE_BACK", "unhandledBack()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
          if (topDisplayFocusedRootTask != null) {
            topDisplayFocusedRootTask.unhandledBackLocked();
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

  public boolean isPossibleToStart(Intent intent) {
    String packageName;
    if (intent == null) {
      return false;
    }
    String action = intent.getAction();
    if ("android.intent.action.MAIN".equals(action)
        && intent.hasCategory("android.intent.category.HOME")) {
      return true;
    }
    if (action != null
        && ("android.intent.action.CALL_PRIVILEGED".equals(action)
            || "com.android.internal.intent.action.REQUEST_SHUTDOWN".equals(action)
            || action.toLowerCase().contains("emergency")
            || "intent.action.INTERACTION_ICE".equals(action))) {
      return true;
    }
    if (this.mCheckSIOPLevelList != null
        && intent.getComponent() != null
        && (packageName = intent.getComponent().getPackageName()) != null) {
      Slog.d("checkingSIOP", "isPossibleToStart  : " + packageName);
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

  public void moveTaskToFront(
      IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.REORDER_TASKS", "moveTaskToFront()");
    if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_TASKS,
          2117696413,
          1,
          (String) null,
          new Object[] {Long.valueOf(i)});
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        moveTaskToFrontLocked(
            iApplicationThread, str, i, i2, SafeActivityOptions.fromBundle(bundle));
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void moveTaskToFrontLocked(
      IApplicationThread iApplicationThread,
      String str,
      int i,
      int i2,
      SafeActivityOptions safeActivityOptions) {
    moveTaskToFrontLocked(iApplicationThread, str, i, i2, safeActivityOptions, false);
  }

  public void moveTaskToFrontLocked(
      IApplicationThread iApplicationThread,
      String str,
      int i,
      int i2,
      SafeActivityOptions safeActivityOptions,
      boolean z) {
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    assertPackageMatchesCallingUid(str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (!getActivityStartController()
            .getBackgroundActivityLaunchController()
            .shouldAbortBackgroundActivityStart(
                callingUid,
                callingPid,
                str,
                -1,
                -1,
                iApplicationThread != null ? getProcessController(iApplicationThread) : null,
                null,
                BackgroundStartPrivileges.NONE,
                null,
                null)
        || isBackgroundActivityStartsEnabled()) {
      try {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
        if (anyTaskForId == null) {
          if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_TASKS,
                -1474292612,
                1,
                (String) null,
                new Object[] {Long.valueOf(i)});
          }
          SafeActivityOptions.abort(safeActivityOptions);
          return;
        }
        if (getLockTaskController().isLockTaskModeViolation(anyTaskForId)) {
          Slog.e("ActivityTaskManager", "moveTaskToFront: Attempt to violate Lock Task Mode");
          SafeActivityOptions.abort(safeActivityOptions);
          return;
        }
        if (anyTaskForId.inFreeformWindowingMode() && anyTaskForId.isMinimized()) {
          resumeAppSwitches();
        }
        ActivityOptions options =
            safeActivityOptions != null
                ? safeActivityOptions.getOptions(this.mTaskSupervisor)
                : null;
        if (anyTaskForId.getDisplayId() == 2
            && ((options == null || options.getLaunchDisplayId() != -1)
                && this.mDexController.interceptStartActivityFromRecentsLocked(
                    anyTaskForId, ActivityOptions.makeBasic().setLaunchDisplayId(2)))) {
          SafeActivityOptions.abort(safeActivityOptions);
          return;
        }
        if (CoreRune.MT_NEW_DEX_RESUMED_AFFORDANCE_ANIMATION
            && anyTaskForId.isNewDexMode()
            && this.mMultiTaskingController.needAffordanceAnimation(anyTaskForId, options)) {
          this.mMultiTaskingController.setAffordanceTargetTask(anyTaskForId);
        }
        this.mTaskSupervisor.findTaskToMoveToFront(
            anyTaskForId, i2, options, "moveTaskToFront", false, z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public final boolean isSameApp(int i, String str) {
    if (i == 0 || i == 1000) {
      return true;
    }
    return this.mPmInternal.isSameApp(str, i, UserHandle.getUserId(i));
  }

  public void assertPackageMatchesCallingUid(String str) {
    int callingUid = Binder.getCallingUid();
    if (isSameApp(callingUid, str)) {
      return;
    }
    String str2 = "Permission Denial: package=" + str + " does not belong to uid=" + callingUid;
    Slog.w("ActivityTaskManager", str2);
    throw new SecurityException(str2);
  }

  public int getBalAppSwitchesState() {
    return this.mAppSwitchesState;
  }

  public void registerAnrController(AnrController anrController) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAnrController.add(anrController);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void unregisterAnrController(AnrController anrController) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAnrController.remove(anrController);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public AnrController getAnrController(ApplicationInfo applicationInfo) {
    ArrayList arrayList;
    AnrController anrController = null;
    if (applicationInfo == null || applicationInfo.packageName == null) {
      return null;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        arrayList = new ArrayList(this.mAnrController);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    String str = applicationInfo.packageName;
    int i = applicationInfo.uid;
    Iterator it = arrayList.iterator();
    long j = 0;
    while (it.hasNext()) {
      AnrController anrController2 = (AnrController) it.next();
      long anrDelayMillis = anrController2.getAnrDelayMillis(str, i);
      if (anrDelayMillis > 0 && anrDelayMillis > j) {
        anrController = anrController2;
        j = anrDelayMillis;
      }
    }
    return anrController;
  }

  public void setActivityController(IActivityController iActivityController, boolean z) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.SET_ACTIVITY_WATCHER", "setActivityController()");
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mController = iActivityController;
        this.mControllerIsAMonkey = z;
        Watchdog.getInstance().setActivityController(iActivityController);
        this.mControllerDescription = "pid = " + callingPid + " uid = " + callingUid;
        Slog.w(
            "ActivityTaskManager",
            "setActivityController controller: "
                + iActivityController
                + "  mControllerDescription: "
                + this.mControllerDescription);
        Watchdog.getInstance().setActivityControllerDescription(this.mControllerDescription);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isControllerAMonkey() {
    boolean z;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        z = this.mController != null && this.mControllerIsAMonkey;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return z;
  }

  public List getTasks(int i) {
    return getTasks(i, false, false, -1);
  }

  public List getTasks(int i, boolean z, boolean z2, int i2) {
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    int i3 = (z ? 1 : 0) | (z2 ? 8 : 0) | (isCrossUserAllowed(callingPid, callingUid) ? 4 : 0);
    int[] profileIds = getUserManager().getProfileIds(UserHandle.getUserId(callingUid), true);
    ArraySet arraySet = new ArraySet();
    for (int i4 : profileIds) {
      arraySet.add(Integer.valueOf(i4));
    }
    ArrayList arrayList = new ArrayList();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mRootWindowContainer.getRunningTasks(
            i,
            arrayList,
            i3 | (isGetTasksAllowed("getTasks", callingPid, callingUid) ? 2 : 0),
            callingUid,
            arraySet,
            i2);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return arrayList;
  }

  public boolean isForceStopDisabled(Task task, boolean z) {
    String packageName;
    if (z || task == null) {
      return false;
    }
    ActivityRecord rootActivity = task.getRootActivity();
    Intent baseIntent = task.getBaseIntent();
    if (rootActivity != null) {
      packageName = rootActivity.packageName;
    } else {
      packageName =
          (baseIntent == null || baseIntent.getComponent() == null)
              ? null
              : baseIntent.getComponent().getPackageName();
    }
    String str = packageName;
    if (str == null || str.isEmpty()) {
      return false;
    }
    return isForceStopDisabled(str, task.mUserId, null, null, null);
  }

  public boolean isForceStopDisabledWithoutToast(Task task, boolean z) {
    String packageName;
    if (z || task == null) {
      return false;
    }
    ActivityRecord rootActivity = task.getRootActivity();
    Intent baseIntent = task.getBaseIntent();
    if (rootActivity != null) {
      packageName = rootActivity.packageName;
    } else {
      packageName =
          (baseIntent == null || baseIntent.getComponent() == null)
              ? null
              : baseIntent.getComponent().getPackageName();
    }
    String str = packageName;
    if (str == null || str.isEmpty()) {
      return false;
    }
    return isForceStopDisabled(str, task.mUserId, null, null, null, false, false);
  }

  public boolean isForceStopDisabled(String str, int i, String str2, String str3, String str4) {
    return isForceStopDisabled(str, i, str2, str3, str4, false);
  }

  public boolean isForceStopDisabled(
      String str, int i, String str2, String str3, String str4, boolean z) {
    return isForceStopDisabled(str, i, str2, str3, str4, z, true);
  }

  public boolean isForceStopDisabled(
      String str, int i, String str2, String str3, String str4, boolean z, boolean z2) {
    try {
      IApplicationPolicy asInterface =
          IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
      if (z || str == null || asInterface == null) {
        return false;
      }
      return asInterface.isApplicationForceStopDisabled(str, i, str2, str3, str4, z2);
    } catch (RemoteException unused) {
      return false;
    }
  }

  public boolean clearRecentTasks(int i) {
    int callingPid = Binder.getCallingPid();
    if (callingPid != Process.myPid()
        && !SemPersonaManager.isContainerService(this.mContext, callingPid)) {
      throw new SecurityException("Pid " + callingPid + " cannot clear recent tasks!");
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

  public void moveTaskToRootTask(int i, int i2, boolean z) {
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
          if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_TASKS,
                -677449371,
                53,
                (String) null,
                new Object[] {Long.valueOf(i), Long.valueOf(i2), Boolean.valueOf(z)});
          }
          Task rootTask = this.mRootWindowContainer.getRootTask(i2);
          if (rootTask == null) {
            throw new IllegalStateException("moveTaskToRootTask: No rootTask for rootTaskId=" + i2);
          }
          if (!rootTask.isActivityTypeStandardOrUndefined()) {
            throw new IllegalArgumentException(
                "moveTaskToRootTask: Attempt to move task " + i + " to rootTask " + i2);
          }
          anyTaskForId.reparent(rootTask, z, 1, true, false, "moveTaskToRootTask");
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

  public void removeRootTasksInWindowingModes(int[] iArr) {
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

  public void removeRootTasksWithActivityTypes(int[] iArr) {
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

  public ParceledListSlice getRecentTasks(int i, int i2, int i3) {
    ParceledListSlice recentTasks;
    int callingUid = Binder.getCallingUid();
    int handleIncomingUser =
        handleIncomingUser(Binder.getCallingPid(), callingUid, i3, "getRecentTasks");
    boolean isGetTasksAllowed =
        isGetTasksAllowed("getRecentTasks", Binder.getCallingPid(), callingUid);
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        recentTasks =
            this.mRecentTasks.getRecentTasks(
                i, i2, isGetTasksAllowed, handleIncomingUser, callingUid);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return recentTasks;
  }

  public List getAllRootTaskInfos() {
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

  public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) {
    ActivityTaskManager.RootTaskInfo rootTaskInfo;
    enforceTaskPermission("getRootTaskInfo()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          rootTaskInfo = this.mRootWindowContainer.getRootTaskInfo(i, i2);
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

  public List getAllRootTaskInfosOnDisplay(int i) {
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

  public ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) {
    ActivityTaskManager.RootTaskInfo rootTaskInfo;
    enforceTaskPermission("getRootTaskInfoOnDisplay()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          rootTaskInfo = this.mRootWindowContainer.getRootTaskInfo(i, i2, i3);
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

  public void cancelRecentsAnimation(boolean z) {
    enforceTaskPermission("cancelRecentsAnimation()");
    long callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mWindowManager.cancelRecentsAnimation(
              z ? 2 : 0, "cancelRecentsAnimation/uid=" + callingUid);
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

  public void startSystemLockTaskMode(int i) {
    enforceTaskPermission("startSystemLockTaskMode");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId != null) {
            anyTaskForId.getRootTask().moveToFront("startSystemLockTaskMode");
            startLockTaskMode(anyTaskForId, true);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
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

  public void stopSystemLockTaskMode() {
    enforceTaskPermission("stopSystemLockTaskMode");
    stopLockTaskModeInternal(null, true);
  }

  public void startLockTaskMode(Task task, boolean z) {
    if (ProtoLogCache.WM_DEBUG_LOCKTASK_enabled) {
      ProtoLogImpl.w(
          ProtoLogGroup.WM_DEBUG_LOCKTASK,
          295861935,
          0,
          (String) null,
          new Object[] {String.valueOf(task)});
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
      this.mRootWindowContainer.removeRootTasksInWindowingModes(2);
      getLockTaskController().startLockTaskMode(task, z, callingUid);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void stopLockTaskModeInternal(IBinder iBinder, boolean z) {
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
            if (forTokenLocked != null) {
              task = forTokenLocked.getTask();
            } else {
              WindowManagerService.resetPriorityAfterLockedSection();
              return;
            }
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        } else {
          task = null;
        }
        getLockTaskController().stopLockTaskMode(task, z, callingUid);
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

  public void updateLockTaskPackages(int i, String[] strArr) {
    int callingUid = Binder.getCallingUid();
    if (callingUid != 0 && callingUid != 1000) {
      this.mAmInternal.enforceCallingPermission(
          "android.permission.UPDATE_LOCK_TASK_PACKAGES", "updateLockTaskPackages()");
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (ProtoLogCache.WM_DEBUG_LOCKTASK_enabled) {
          ProtoLogImpl.w(
              ProtoLogGroup.WM_DEBUG_LOCKTASK,
              715749922,
              1,
              (String) null,
              new Object[] {Long.valueOf(i), String.valueOf(Arrays.toString(strArr))});
        }
        getLockTaskController().updateLockTaskPackages(i, strArr);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isInLockTaskMode() {
    return getLockTaskModeState() != 0;
  }

  public int getLockTaskModeState() {
    return getLockTaskController().getLockTaskModeState();
  }

  public List getAppTasks(String str) {
    assertPackageMatchesCallingUid(str);
    return getAppTasks(str, Binder.getCallingUid());
  }

  public final List getAppTasks(String str, int i) {
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

  public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mRootWindowContainer.finishVoiceTask(iVoiceInteractionSession);
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

  public void reportAssistContextExtras(
      IBinder iBinder,
      Bundle bundle,
      AssistStructure assistStructure,
      AssistContent assistContent,
      Uri uri) {
    Bundle bundle2;
    PendingAssistExtras pendingAssistExtras = (PendingAssistExtras) iBinder;
    synchronized (pendingAssistExtras) {
      pendingAssistExtras.result = bundle;
      pendingAssistExtras.structure = assistStructure;
      pendingAssistExtras.content = assistContent;
      if (uri != null) {
        pendingAssistExtras.extras.putParcelable("android.intent.extra.REFERRER", uri);
      }
      if (pendingAssistExtras.activity.isAttached()) {
        if (assistStructure != null) {
          assistStructure.setTaskId(pendingAssistExtras.activity.getTask().mTaskId);
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
          try {
            buildAssistBundleLocked(pendingAssistExtras, bundle);
            boolean remove = this.mPendingAssistExtras.remove(pendingAssistExtras);
            this.mUiHandler.removeCallbacks(pendingAssistExtras);
            if (!remove) {
              WindowManagerService.resetPriorityAfterLockedSection();
              return;
            }
            IAssistDataReceiver iAssistDataReceiver = pendingAssistExtras.receiver;
            if (iAssistDataReceiver != null) {
              bundle2 = new Bundle();
              bundle2.putInt("taskId", pendingAssistExtras.activity.getTask().mTaskId);
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
                this.mContext.startActivityAsUser(
                    pendingAssistExtras.intent, new UserHandle(pendingAssistExtras.userHandle));
              } catch (ActivityNotFoundException e) {
                Slog.w("ActivityTaskManager", "No activity to handle assist action.", e);
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
    }
  }

  /* JADX WARN: Finally extract failed */
  public int addAppTask(
      IBinder iBinder,
      Intent intent,
      ActivityManager.TaskDescription taskDescription,
      Bitmap bitmap) {
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
            throw new IllegalArgumentException(
                "Intent " + intent + " must specify explicit component");
          }
          if (bitmap.getWidth() != this.mThumbnailWidth
              || bitmap.getHeight() != this.mThumbnailHeight) {
            throw new IllegalArgumentException(
                "Bad thumbnail size: got "
                    + bitmap.getWidth()
                    + "x"
                    + bitmap.getHeight()
                    + ", require "
                    + this.mThumbnailWidth
                    + "x"
                    + this.mThumbnailHeight);
          }
          if (intent.getSelector() != null) {
            intent.setSelector(null);
          }
          if (intent.getSourceBounds() != null) {
            intent.setSourceBounds(null);
          }
          if ((intent.getFlags() & 524288) != 0
              && (intent.getFlags() & IInstalld.FLAG_FORCE) == 0) {
            intent.addFlags(IInstalld.FLAG_FORCE);
          }
          ActivityInfo activityInfo =
              AppGlobals.getPackageManager()
                  .getActivityInfo(component, 1024L, UserHandle.getUserId(callingUid));
          if (activityInfo != null && activityInfo.applicationInfo.uid == callingUid) {
            Task rootTask = isInRootTaskLocked.getRootTask();
            Task build =
                new Task.Builder(this)
                    .setWindowingMode(rootTask.getWindowingMode())
                    .setActivityType(rootTask.getActivityType())
                    .setActivityInfo(activityInfo)
                    .setIntent(intent)
                    .setTaskId(rootTask.getDisplayArea().getNextRootTaskId())
                    .build();
            if (!this.mRecentTasks.addToBottom(build)) {
              rootTask.removeChild(build, "addAppTask");
              WindowManagerService.resetPriorityAfterLockedSection();
              return -1;
            }
            build.getTaskDescription().copyFrom(taskDescription);
            int i = build.mTaskId;
            WindowManagerService.resetPriorityAfterLockedSection();
            return i;
          }
          StringBuilder sb = new StringBuilder();
          sb.append("Can't add task for another application: target uid=");
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

  public Point getAppTaskThumbnailSize() {
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

  public void setTaskResizeable(int i, int i2) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
        if (anyTaskForId == null) {
          Slog.w("ActivityTaskManager", "setTaskResizeable: taskId=" + i + " not found");
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        anyTaskForId.setResizeMode(i2);
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX WARN: Finally extract failed */
  public void resizeTask(int i, final Rect rect, final int i2) {
    enforceTaskPermission("resizeTask()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          final Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w("ActivityTaskManager", "resizeTask: taskId=" + i + " not found");
          } else if (!anyTaskForId.getWindowConfiguration().canResizeTask()) {
            Slog.w("ActivityTaskManager", "resizeTask not allowed on task=" + anyTaskForId);
          } else {
            final boolean z = (i2 & 1) != 0;
            if (!getTransitionController().isShellTransitionsEnabled()) {
              anyTaskForId.resize(rect, i2, z);
            } else {
              final Transition transition =
                  new Transition(6, 0, getTransitionController(), this.mWindowManager.mSyncEngine);
              getTransitionController()
                  .startCollectOrQueue(
                      transition,
                      new TransitionController
                          .OnStartCollect() { // from class:
                                              // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda6
                        @Override // com.android.server.wm.TransitionController.OnStartCollect
                        public final void onCollectStarted(boolean z2) {
                          ActivityTaskManagerService.this.lambda$resizeTask$2(
                              anyTaskForId, transition, rect, i2, z, z2);
                        }
                      });
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

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$resizeTask$2(
      Task task, Transition transition, Rect rect, int i, boolean z, boolean z2) {
    if (z2 && !task.getWindowConfiguration().canResizeTask()) {
      Slog.w("ActivityTaskManager", "resizeTask not allowed on task=" + task);
      transition.abort();
      return;
    }
    getTransitionController().requestStartTransition(transition, task, null, null);
    getTransitionController().collect(task);
    task.resize(rect, i, z);
    transition.setReady(task, true);
  }

  public void releaseSomeActivities(IApplicationThread iApplicationThread) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getProcessController(iApplicationThread).releaseSomeActivities("low-mem");
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

  public void setLockScreenShown(final boolean z, final boolean z2) {
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
          this.f1734mH.sendMessage(
              PooledLambda.obtainMessage(
                  new BiConsumer() { // from class:
                                     // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda12
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                      ((ActivityManagerInternal) obj)
                          .reportCurKeyguardUsageEvent(((Boolean) obj2).booleanValue());
                    }
                  },
                  this.mAmInternal,
                  Boolean.valueOf(z)));
        }
        if ((this.mDemoteTopAppReasons & 1) != 0) {
          this.mDemoteTopAppReasons &= -2;
          if (this.mTopApp != null) {
            this.mTopApp.scheduleUpdateOomAdj();
          }
        }
        try {
          Trace.traceBegin(32L, "setLockScreenShown");
          this.mRootWindowContainer.forAllDisplays(
              new Consumer() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ActivityTaskManagerService.this.lambda$setLockScreenShown$3(
                      z, z2, (DisplayContent) obj);
                }
              });
          maybeHideLockedProfileActivityLocked();
          Trace.traceEnd(32L);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && z) {
            this.mMultiWindowFoldController.removeWakeUpInFoldingState();
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
    this.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda14
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$setLockScreenShown$4(z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setLockScreenShown$3(
      boolean z, boolean z2, DisplayContent displayContent) {
    this.mKeyguardController.setKeyguardShown(displayContent.getDisplayId(), z, z2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setLockScreenShown$4(boolean z) {
    synchronized (this.mScreenObservers) {
      for (int size = this.mScreenObservers.size() - 1; size >= 0; size--) {
        ((ActivityTaskManagerInternal.ScreenObserver) this.mScreenObservers.get(size))
            .onKeyguardStateChanged(z);
      }
    }
  }

  public final void maybeHideLockedProfileActivityLocked() {
    ActivityRecord activityRecord;
    UserInfo userInfo;
    if (this.mKeyguardController.isKeyguardLocked(0)
        && (activityRecord = this.mLastResumedActivity) != null
        && (userInfo = this.mUserManager.getUserInfo(activityRecord.mUserId)) != null
        && userInfo.isManagedProfile()
        && this.mAmInternal.shouldConfirmCredentials(this.mLastResumedActivity.mUserId)) {
      this.mInternal.startHomeActivity(
          this.mAmInternal.getCurrentUserId(), "maybeHideLockedProfileActivityLocked");
    }
  }

  public void onScreenAwakeChanged(final boolean z) {
    WindowProcessController process;
    this.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$onScreenAwakeChanged$5(z);
          }
        });
    if (z) {
      return;
    }
    synchronized (this.mGlobalLockWithoutBoost) {
      this.mDemoteTopAppReasons &= -2;
      WindowState notificationShade =
          this.mRootWindowContainer.getDefaultDisplay().getDisplayPolicy().getNotificationShade();
      process = notificationShade != null ? notificationShade.getProcess() : null;
    }
    setProcessAnimatingWhileDozing(process);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onScreenAwakeChanged$5(boolean z) {
    synchronized (this.mScreenObservers) {
      for (int size = this.mScreenObservers.size() - 1; size >= 0; size--) {
        ((ActivityTaskManagerInternal.ScreenObserver) this.mScreenObservers.get(size))
            .onAwakeStateChanged(z);
      }
    }
  }

  public void setProcessAnimatingWhileDozing(WindowProcessController windowProcessController) {
    if (windowProcessController == null) {
      return;
    }
    windowProcessController.setRunningAnimationUnsafe();
    this.f1734mH.removeMessages(2, windowProcessController);
    HandlerC2878H handlerC2878H = this.f1734mH;
    handlerC2878H.sendMessageDelayed(
        handlerC2878H.obtainMessage(2, windowProcessController), 2000L);
  }

  public Bitmap getTaskDescriptionIcon(String str, int i) {
    int handleIncomingUser =
        handleIncomingUser(
            Binder.getCallingPid(), Binder.getCallingUid(), i, "getTaskDescriptionIcon");
    if (!new File(TaskPersister.getUserImagesDir(handleIncomingUser), new File(str).getName())
            .getPath()
            .equals(str)
        || !str.contains("_activity_icon_")) {
      throw new IllegalArgumentException(
          "Bad file path: " + str + " passed for userId " + handleIncomingUser);
    }
    return this.mRecentTasks.getTaskDescriptionIcon(str);
  }

  public void moveRootTaskToDisplay(int i, int i2) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.INTERNAL_SYSTEM_WINDOW", "moveRootTaskToDisplay()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
            ProtoLogImpl.d(
                ProtoLogGroup.WM_DEBUG_TASKS,
                -1419762046,
                5,
                (String) null,
                new Object[] {Long.valueOf(i), Long.valueOf(i2)});
          }
          this.mRootWindowContainer.moveRootTaskToDisplay(i, i2, true);
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

  public void registKeyEventListener(IKeyEventListener iKeyEventListener) {
    enforceTaskPermission("registKeyEventListener()");
    this.mKeyEventListener = iKeyEventListener;
  }

  public void registerTaskStackListener(ITaskStackListener iTaskStackListener) {
    enforceTaskPermission("registerTaskStackListener()");
    this.mTaskChangeNotificationController.registerTaskStackListener(iTaskStackListener);
  }

  public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) {
    enforceTaskPermission("unregisterTaskStackListener()");
    this.mTaskChangeNotificationController.unregisterTaskStackListener(iTaskStackListener);
  }

  public boolean requestAssistContextExtras(
      int i,
      IAssistDataReceiver iAssistDataReceiver,
      Bundle bundle,
      IBinder iBinder,
      boolean z,
      boolean z2) {
    return enqueueAssistContext(
            i,
            null,
            null,
            iAssistDataReceiver,
            bundle,
            iBinder,
            z,
            z2,
            UserHandle.getCallingUserId(),
            null,
            2000L,
            0)
        != null;
  }

  public boolean requestAssistDataForTask(
      IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.GET_TOP_ACTIVITY_INFO", "requestAssistDataForTask()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask =
          this.mInternal.getAttachedNonFinishingActivityForTask(i, null);
      if (attachedNonFinishingActivityForTask == null) {
        Log.e("ActivityTaskManager", "Could not find activity for task " + i);
        return false;
      }
      AssistDataRequester assistDataRequester =
          new AssistDataRequester(
              this.mContext,
              this.mWindowManager,
              getAppOpsManager(),
              new AssistDataReceiverProxy(iAssistDataReceiver, str),
              new Object(),
              49,
              -1);
      ArrayList arrayList = new ArrayList();
      arrayList.add(attachedNonFinishingActivityForTask.getActivityToken());
      assistDataRequester.requestAssistData(
          arrayList, true, false, false, true, false, true, Binder.getCallingUid(), str, str2);
      return true;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean requestAutofillData(
      IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) {
    return enqueueAssistContext(
            2,
            null,
            null,
            iAssistDataReceiver,
            bundle,
            iBinder,
            true,
            true,
            UserHandle.getCallingUserId(),
            null,
            2000L,
            i)
        != null;
  }

  public Bundle getAssistContextExtras(int i) {
    PendingAssistExtras enqueueAssistContext =
        enqueueAssistContext(
            i,
            null,
            null,
            null,
            null,
            null,
            true,
            true,
            UserHandle.getCallingUserId(),
            null,
            500L,
            0);
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
        buildAssistBundleLocked(enqueueAssistContext, enqueueAssistContext.result);
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

  public static int checkCallingPermission(String str) {
    return checkPermission(str, Binder.getCallingPid(), Binder.getCallingUid());
  }

  public boolean checkCanCloseSystemDialogs(int i, int i2, String str) {
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
    String str2 = "(pid=" + i + ", uid=" + i2 + ")";
    if (str != null) {
      str2 = str + " " + str2;
    }
    if (canCloseSystemDialogs(i, i2)) {
      return true;
    }
    if (CompatChanges.isChangeEnabled(174664365L, i2)) {
      throw new SecurityException(
          "Permission Denial: android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from "
              + str2
              + " requires android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS.");
    }
    if (CompatChanges.isChangeEnabled(174664120L, i2)) {
      Slog.e(
          "ActivityTaskManager",
          "Permission Denial: android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from "
              + str2
              + " requires android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS, dropping broadcast.");
      return false;
    }
    Slog.w(
        "ActivityTaskManager",
        "android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from "
            + str2
            + " will require android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS in future builds.");
    return true;
  }

  public final boolean canCloseSystemDialogs(int i, int i2) {
    if (checkPermission("android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS", i, i2) == 0) {
      return true;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ArraySet processes = this.mProcessMap.getProcesses(i2);
        if (processes != null) {
          int size = processes.size();
          for (int i3 = 0; i3 < size; i3++) {
            WindowProcessController windowProcessController =
                (WindowProcessController) processes.valueAt(i3);
            int instrumentationSourceUid = windowProcessController.getInstrumentationSourceUid();
            if (windowProcessController.isInstrumenting()
                && instrumentationSourceUid != -1
                && checkPermission(
                        "android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS",
                        -1,
                        instrumentationSourceUid)
                    == 0) {
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
          if (this.mRootWindowContainer.hasVisibleWindowAboveButDoesNotOwnNotificationShade(i2)) {
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

  public static void enforceTaskPermission(String str) {
    if (checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS") == 0) {
      return;
    }
    if (checkCallingPermission("android.permission.MANAGE_ACTIVITY_STACKS") == 0) {
      Slog.w(
          "ActivityTaskManager",
          "MANAGE_ACTIVITY_STACKS is deprecated, please use alternative permission:"
              + " MANAGE_ACTIVITY_TASKS");
      return;
    }
    String str2 =
        "Permission Denial: "
            + str
            + " from pid="
            + Binder.getCallingPid()
            + ", uid="
            + Binder.getCallingUid()
            + " requires android.permission.MANAGE_ACTIVITY_TASKS";
    Slog.w("ActivityTaskManager", str2);
    throw new SecurityException(str2);
  }

  public static int checkPermission(String str, int i, int i2) {
    if (str == null) {
      return -1;
    }
    return checkComponentPermission(str, i, i2, -1, true);
  }

  public static int checkComponentPermission(String str, int i, int i2, int i3, boolean z) {
    return ActivityManagerService.checkComponentPermission(str, i, i2, i3, z);
  }

  public boolean isCallerRecents(int i) {
    return this.mRecentTasks.isCallerRecents(i);
  }

  public boolean isGetTasksAllowed(String str, int i, int i2) {
    boolean z = true;
    if (isCallerRecents(i2)) {
      return true;
    }
    boolean z2 = checkPermission("android.permission.REAL_GET_TASKS", i, i2) == 0;
    if (!z2) {
      if (checkPermission("android.permission.GET_TASKS", i, i2) == 0) {
        if (AppGlobals.getPackageManager().isUidPrivileged(i2)) {
          try {
            if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
              ProtoLogImpl.w(
                  ProtoLogGroup.WM_DEBUG_TASKS,
                  -917215012,
                  4,
                  (String) null,
                  new Object[] {String.valueOf(str), Long.valueOf(i2)});
            }
          } catch (RemoteException unused) {
          }
          z2 = z;
        }
        z = z2;
        z2 = z;
      }
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_TASKS,
            -401029526,
            4,
            (String) null,
            new Object[] {String.valueOf(str), Long.valueOf(i2)});
      }
    }
    return z2;
  }

  public boolean isCrossUserAllowed(int i, int i2) {
    return checkPermission("android.permission.INTERACT_ACROSS_USERS", i, i2) == 0
        || checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2) == 0;
  }

  public final PendingAssistExtras enqueueAssistContext(
      int i,
      Intent intent,
      String str,
      IAssistDataReceiver iAssistDataReceiver,
      Bundle bundle,
      IBinder iBinder,
      boolean z,
      boolean z2,
      int i2,
      Bundle bundle2,
      long j,
      int i3) {
    ActivityRecord forTokenLocked;
    this.mAmInternal.enforceCallingPermission(
        "android.permission.GET_TOP_ACTIVITY_INFO", "enqueueAssistContext()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        ActivityRecord topNonFinishingActivity =
            topDisplayFocusedRootTask != null
                ? topDisplayFocusedRootTask.getTopNonFinishingActivity()
                : null;
        if (topNonFinishingActivity == null) {
          Slog.w("ActivityTaskManager", "getAssistContextExtras failed: no top activity");
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        if (!topNonFinishingActivity.attachedToProcess()) {
          Slog.w(
              "ActivityTaskManager",
              "getAssistContextExtras failed: no process for " + topNonFinishingActivity);
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        if (!z) {
          topNonFinishingActivity = ActivityRecord.forTokenLocked(iBinder);
          if (topNonFinishingActivity == null) {
            Slog.w(
                "ActivityTaskManager",
                "enqueueAssistContext failed: activity for token="
                    + iBinder
                    + " couldn't be found");
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
          if (!topNonFinishingActivity.attachedToProcess()) {
            Slog.w(
                "ActivityTaskManager",
                "enqueueAssistContext failed: no process for " + topNonFinishingActivity);
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
        } else if (iBinder != null
            && topNonFinishingActivity
                != (forTokenLocked = ActivityRecord.forTokenLocked(iBinder))) {
          Slog.w(
              "ActivityTaskManager",
              "enqueueAssistContext failed: caller "
                  + forTokenLocked
                  + " is not current top "
                  + topNonFinishingActivity);
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        ActivityRecord activityRecord = topNonFinishingActivity;
        Bundle bundle3 = new Bundle();
        if (bundle2 != null) {
          bundle3.putAll(bundle2);
        }
        bundle3.putString("android.intent.extra.ASSIST_PACKAGE", activityRecord.packageName);
        bundle3.putInt("android.intent.extra.ASSIST_UID", activityRecord.app.mUid);
        PendingAssistExtras pendingAssistExtras =
            new PendingAssistExtras(
                activityRecord, bundle3, intent, str, iAssistDataReceiver, bundle, i2);
        pendingAssistExtras.isHome = activityRecord.isActivityTypeHome();
        if (z2) {
          this.mViSessionId++;
        }
        try {
          activityRecord
              .app
              .getThread()
              .requestAssistContextExtras(
                  activityRecord.token, pendingAssistExtras, i, this.mViSessionId, i3);
          this.mPendingAssistExtras.add(pendingAssistExtras);
          this.mUiHandler.postDelayed(pendingAssistExtras, j);
          WindowManagerService.resetPriorityAfterLockedSection();
          return pendingAssistExtras;
        } catch (RemoteException unused) {
          Slog.w(
              "ActivityTaskManager",
              "getAssistContextExtras failed: crash calling " + activityRecord);
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final void buildAssistBundleLocked(
      PendingAssistExtras pendingAssistExtras, Bundle bundle) {
    if (bundle != null) {
      pendingAssistExtras.extras.putBundle("android.intent.extra.ASSIST_CONTEXT", bundle);
    }
    String str = pendingAssistExtras.hint;
    if (str != null) {
      pendingAssistExtras.extras.putBoolean(str, true);
    }
  }

  public final void pendingAssistExtrasTimedOut(PendingAssistExtras pendingAssistExtras) {
    IAssistDataReceiver iAssistDataReceiver;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mPendingAssistExtras.remove(pendingAssistExtras);
        iAssistDataReceiver = pendingAssistExtras.receiver;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    if (iAssistDataReceiver != null) {
      Bundle bundle = new Bundle();
      bundle.putBundle("receiverExtras", pendingAssistExtras.receiverExtras);
      try {
        pendingAssistExtras.receiver.onHandleAssistData(bundle);
      } catch (RemoteException unused) {
      }
    }
  }

  public class PendingAssistExtras extends Binder implements Runnable {
    public final ActivityRecord activity;
    public final Bundle extras;
    public final String hint;
    public final Intent intent;
    public boolean isHome;
    public final IAssistDataReceiver receiver;
    public Bundle receiverExtras;
    public final int userHandle;
    public boolean haveResult = false;
    public Bundle result = null;
    public AssistStructure structure = null;
    public AssistContent content = null;

    public PendingAssistExtras(
        ActivityRecord activityRecord,
        Bundle bundle,
        Intent intent,
        String str,
        IAssistDataReceiver iAssistDataReceiver,
        Bundle bundle2,
        int i) {
      this.activity = activityRecord;
      this.extras = bundle;
      this.intent = intent;
      this.hint = str;
      this.receiver = iAssistDataReceiver;
      this.receiverExtras = bundle2;
      this.userHandle = i;
    }

    @Override // java.lang.Runnable
    public void run() {
      Slog.w(
          "ActivityTaskManager",
          "getAssistContextExtras failed: timeout retrieving from " + this.activity);
      synchronized (this) {
        this.haveResult = true;
        notifyAll();
      }
      ActivityTaskManagerService.this.pendingAssistExtrasTimedOut(this);
    }
  }

  public boolean isAssistDataAllowedOnCurrentActivity() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask != null
            && !topDisplayFocusedRootTask.isActivityTypeAssistant()) {
          ActivityRecord topNonFinishingActivity =
              topDisplayFocusedRootTask.getTopNonFinishingActivity();
          if (topNonFinishingActivity == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return false;
          }
          int i = topNonFinishingActivity.mUserId;
          WindowManagerService.resetPriorityAfterLockedSection();
          return DevicePolicyCache.getInstance().isScreenCaptureAllowed(i);
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return false;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final void onLocalVoiceInteractionStartedLocked(
      IBinder iBinder,
      IVoiceInteractionSession iVoiceInteractionSession,
      IVoiceInteractor iVoiceInteractor) {
    ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
    if (forTokenLocked == null) {
      return;
    }
    forTokenLocked.setVoiceSessionLocked(iVoiceInteractionSession);
    try {
      forTokenLocked
          .app
          .getThread()
          .scheduleLocalVoiceInteractionStarted(iBinder, iVoiceInteractor);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        startRunningVoiceLocked(iVoiceInteractionSession, forTokenLocked.info.applicationInfo.uid);
        Binder.restoreCallingIdentity(clearCallingIdentity);
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    } catch (RemoteException unused) {
      forTokenLocked.clearVoiceSessionLocked();
    }
  }

  public final void startRunningVoiceLocked(
      IVoiceInteractionSession iVoiceInteractionSession, int i) {
    Slog.d("ActivityTaskManager", "<<<  startRunningVoiceLocked()");
    this.mVoiceWakeLock.setWorkSource(new WorkSource(i));
    IVoiceInteractionSession iVoiceInteractionSession2 = this.mRunningVoice;
    if (iVoiceInteractionSession2 == null
        || iVoiceInteractionSession2.asBinder() != iVoiceInteractionSession.asBinder()) {
      boolean z = this.mRunningVoice != null;
      this.mRunningVoice = iVoiceInteractionSession;
      if (z) {
        return;
      }
      this.mVoiceWakeLock.acquire();
      updateSleepIfNeededLocked();
    }
  }

  public void finishRunningVoiceLocked() {
    if (this.mRunningVoice != null) {
      this.mRunningVoice = null;
      this.mVoiceWakeLock.release();
      updateSleepIfNeededLocked();
    }
  }

  public void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        IVoiceInteractionSession iVoiceInteractionSession2 = this.mRunningVoice;
        if (iVoiceInteractionSession2 != null
            && iVoiceInteractionSession2.asBinder() == iVoiceInteractionSession.asBinder()) {
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

  public void keyguardGoingAway(final int i) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.CONTROL_KEYGUARD", "unlock keyguard");
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
          this.mRootWindowContainer.forAllDisplays(
              new Consumer() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ActivityTaskManagerService.this.lambda$keyguardGoingAway$7(
                      i, (DisplayContent) obj);
                }
              });
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      WallpaperManagerInternal wallpaperManagerInternal = getWallpaperManagerInternal();
      if (wallpaperManagerInternal != null) {
        wallpaperManagerInternal.onKeyguardGoingAway();
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$keyguardGoingAway$7(int i, DisplayContent displayContent) {
    this.mKeyguardController.keyguardGoingAway(displayContent.getDisplayId(), i);
  }

  public void suppressResizeConfigChanges(boolean z) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.MANAGE_ACTIVITY_TASKS", "suppressResizeConfigChanges()");
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

  public void onSplashScreenViewCopyFinished(
      int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
    ActivityRecord topWaitSplashScreenActivity;
    this.mAmInternal.enforceCallingPermission(
        "android.permission.MANAGE_ACTIVITY_TASKS", "copySplashScreenViewFinish()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
        if (anyTaskForId != null
            && (topWaitSplashScreenActivity = anyTaskForId.getTopWaitSplashScreenActivity())
                != null) {
          topWaitSplashScreenActivity.onCopySplashScreenFinish(splashScreenViewParcelable);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean enterPictureInPictureMode(
      ActivityRecord activityRecord, PictureInPictureParams pictureInPictureParams, boolean z) {
    return enterPictureInPictureMode(activityRecord, pictureInPictureParams, z, false);
  }

  public boolean enterPictureInPictureMode(
      final ActivityRecord activityRecord,
      final PictureInPictureParams pictureInPictureParams,
      boolean z,
      final boolean z2) {
    if (activityRecord.inPinnedWindowingMode()) {
      return true;
    }
    if (!activityRecord.checkEnterPictureInPictureState("enterPictureInPictureMode", false)) {
      return false;
    }
    Transition transition =
        (getTransitionController().isShellTransitionsEnabled()
                && (z
                    && (!activityRecord.isState(ActivityRecord.State.PAUSING)
                        || pictureInPictureParams.isAutoEnterEnabled())))
            ? new Transition(10, 0, getTransitionController(), this.mWindowManager.mSyncEngine)
            : null;
    final Transition transition2 = transition;
    final Runnable runnable =
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda15
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$enterPictureInPictureMode$8(
                activityRecord, transition2, z2, pictureInPictureParams);
          }
        };
    if (activityRecord.isKeyguardLocked()) {
      this.mActivityClientController.dismissKeyguard(
          activityRecord.token, new C28775(transition, runnable), null);
    } else if (transition != null) {
      getTransitionController()
          .startCollectOrQueue(
              transition,
              new TransitionController
                  .OnStartCollect() { // from class:
                                      // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda16
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z3) {
                  runnable.run();
                }
              });
    } else {
      runnable.run();
    }
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$enterPictureInPictureMode$8(
      ActivityRecord activityRecord,
      Transition transition,
      boolean z,
      PictureInPictureParams pictureInPictureParams) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (activityRecord.getParent() != null
            && (!CoreRune.MW_PIP_SHELL_TRANSITION || !activityRecord.finishing)) {
          EventLogTags.writeWmEnterPip(
              activityRecord.mUserId,
              System.identityHashCode(activityRecord),
              activityRecord.shortComponentName,
              Boolean.toString(z));
          activityRecord.setPictureInPictureParams(pictureInPictureParams);
          activityRecord.mAutoEnteringPip = z;
          this.mRootWindowContainer.moveActivityToPinnedRootTask(
              activityRecord, null, "enterPictureInPictureMode", transition);
          if (activityRecord.isState(ActivityRecord.State.PAUSING)
              && activityRecord.mPauseSchedulePendingForPip) {
            activityRecord
                .getTask()
                .schedulePauseActivity(activityRecord, false, false, true, "auto-pip");
          }
          activityRecord.mAutoEnteringPip = false;
          if (CoreRune.MW_PIP_SA_LOGGING) {
            CoreSaLogger.logForAdvanced("2301", activityRecord.packageName);
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        Slog.e(
            "ActivityTaskManager", "Skip enterPictureInPictureMode, destroyed " + activityRecord);
        if (CoreRune.MW_PIP_SHELL_TRANSITION && transition != null && transition.isCollecting()) {
          Slog.e(
              "ActivityTaskManager[PipTaskOrganizer]",
              "transition abort, destroyed=" + activityRecord);
          transition.abort();
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* renamed from: com.android.server.wm.ActivityTaskManagerService$5 */
  public class C28775 extends KeyguardDismissCallback {
    public final /* synthetic */ Runnable val$enterPipRunnable;
    public final /* synthetic */ Transition val$transition;

    public C28775(Transition transition, Runnable runnable) {
      this.val$transition = transition;
      this.val$enterPipRunnable = runnable;
    }

    public void onDismissSucceeded() {
      if (this.val$transition == null) {
        ActivityTaskManagerService.this.f1734mH.post(this.val$enterPipRunnable);
        return;
      }
      TransitionController transitionController =
          ActivityTaskManagerService.this.getTransitionController();
      Transition transition = this.val$transition;
      final Runnable runnable = this.val$enterPipRunnable;
      transitionController.startCollectOrQueue(
          transition,
          new TransitionController
              .OnStartCollect() { // from class:
                                  // com.android.server.wm.ActivityTaskManagerService$5$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z) {
              ActivityTaskManagerService.C28775.this.lambda$onDismissSucceeded$0(runnable, z);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDismissSucceeded$0(Runnable runnable, boolean z) {
      if (z) {
        runnable.run();
      } else {
        ActivityTaskManagerService.this.f1734mH.post(runnable);
      }
    }
  }

  public IWindowOrganizerController getWindowOrganizerController() {
    return this.mWindowOrganizerController;
  }

  public void enforceSystemHasVrFeature() {
    if (!this.mContext
        .getPackageManager()
        .hasSystemFeature("android.hardware.vr.high_performance")) {
      throw new UnsupportedOperationException("VR mode not supported on this device!");
    }
  }

  public boolean supportsLocalVoiceInteraction() {
    return ((VoiceInteractionManagerInternal)
            LocalServices.getService(VoiceInteractionManagerInternal.class))
        .supportsLocalVoiceInteraction();
  }

  public boolean updateConfiguration(Configuration configuration) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.CHANGE_CONFIGURATION", "updateConfiguration()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        WindowManagerService windowManagerService = this.mWindowManager;
        if (windowManagerService == null) {
          Slog.w(
              "ActivityTaskManager", "Skip updateConfiguration because mWindowManager isn't set");
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        if (configuration == null) {
          configuration = windowManagerService.computeNewConfiguration(0);
        }
        Configuration configuration2 = configuration;
        this.f1734mH.sendMessage(
            PooledLambda.obtainMessage(
                new ActivityTaskManagerService$$ExternalSyntheticLambda9(), this.mAmInternal, 0));
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (configuration2 != null) {
          try {
            Settings.System.clearConfiguration(configuration2);
          } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
          }
        }
        this.mAmInternal.setUpdateConfigurationCallerLocked(callingPid);
        updateConfigurationLocked(
            configuration2, null, false, false, -10000, false, this.mTmpUpdateConfigurationResult);
        this.mAmInternal.resetUpdateConfigurationCallerLocked();
        boolean z = this.mTmpUpdateConfigurationResult.changes != 0;
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void cancelTaskWindowTransition(int i) {
    enforceTaskPermission("cancelTaskWindowTransition()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w("ActivityTaskManager", "cancelTaskWindowTransition: taskId=" + i + " not found");
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          anyTaskForId.cancelTaskWindowTransition();
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

  /* JADX WARN: Finally extract failed */
  public TaskSnapshot getTaskSnapshot(int i, boolean z, boolean z2) {
    ActivityRecord topNonFinishingActivity;
    this.mAmInternal.enforceCallingPermission(
        "android.permission.READ_FRAME_BUFFER", "getTaskSnapshot()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
          if (anyTaskForId == null) {
            Slog.w("ActivityTaskManager", "getTaskSnapshot: taskId=" + i + " not found");
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
          }
          if (anyTaskForId.isDexMode()
              && (topNonFinishingActivity = anyTaskForId.getTopNonFinishingActivity()) != null
              && topNonFinishingActivity.isState(ActivityRecord.State.RESUMED)) {
            this.mWindowManager.mTaskSnapshotController.takeSnapshotByForce(anyTaskForId);
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          TaskSnapshot snapshot =
              this.mWindowManager.mTaskSnapshotController.getSnapshot(
                  i, anyTaskForId.mUserId, true, z);
          if (snapshot == null && z2) {
            snapshot = takeTaskSnapshot(i, false);
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return snapshot;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    } catch (Throwable th2) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th2;
    }
  }

  public TaskSnapshot takeTaskSnapshot(int i, boolean z) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.READ_FRAME_BUFFER", "takeTaskSnapshot()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
          if (anyTaskForId != null && anyTaskForId.isVisible()) {
            if (z) {
              TaskSnapshot recordSnapshot =
                  this.mWindowManager.mTaskSnapshotController.recordSnapshot(anyTaskForId, true);
              WindowManagerService.resetPriorityAfterLockedSection();
              return recordSnapshot;
            }
            TaskSnapshot captureSnapshot =
                this.mWindowManager.mTaskSnapshotController.captureSnapshot(anyTaskForId, true);
            WindowManagerService.resetPriorityAfterLockedSection();
            return captureSnapshot;
          }
          Slog.w(
              "ActivityTaskManager", "takeTaskSnapshot: taskId=" + i + " not found or not visible");
          WindowManagerService.resetPriorityAfterLockedSection();
          Binder.restoreCallingIdentity(clearCallingIdentity);
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

  public int getLastResumedActivityUserId() {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.INTERACT_ACROSS_USERS_FULL", "getLastResumedActivityUserId()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ActivityRecord activityRecord = this.mLastResumedActivity;
        if (activityRecord == null) {
          int currentUserId = getCurrentUserId();
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

  public void updateLockTaskFeatures(int i, int i2) {
    int callingUid = Binder.getCallingUid();
    if (callingUid != 0 && callingUid != 1000) {
      this.mAmInternal.enforceCallingPermission(
          "android.permission.UPDATE_LOCK_TASK_PACKAGES", "updateLockTaskFeatures()");
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (ProtoLogCache.WM_DEBUG_LOCKTASK_enabled) {
          ProtoLogImpl.w(
              ProtoLogGroup.WM_DEBUG_LOCKTASK,
              -168799453,
              1,
              (String) null,
              new Object[] {Long.valueOf(i), String.valueOf(Integer.toHexString(i2))});
        }
        getLockTaskController().updateLockTaskFeatures(i, i2);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void registerRemoteAnimationForNextActivityStart(
      String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
        "registerRemoteAnimationForNextActivityStart");
    remoteAnimationAdapter.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getActivityStartController()
              .registerRemoteAnimationForNextActivityStart(str, remoteAnimationAdapter, iBinder);
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

  public void registerRemoteTransitionForNextActivityStart(
      String str,
      RemoteAnimationAdapter remoteAnimationAdapter,
      IBinder iBinder,
      RemoteTransition remoteTransition) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
        "registerRemoteTransitionForNextActivityStart");
    remoteAnimationAdapter.setCallingPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          getActivityStartController()
              .registerRemoteTransitionForNextActivityStart(
                  str, remoteAnimationAdapter, iBinder, remoteTransition);
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

  public void registerRemoteAnimationsForDisplay(
      int i, RemoteAnimationDefinition remoteAnimationDefinition) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteAnimations");
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
          displayContent.registerRemoteAnimations(remoteAnimationDefinition);
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

  public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mAppWarnings.alwaysShowUnsupportedCompileSdkWarning(componentName);
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

  public void setVrThread(int i) {
    enforceSystemHasVrFeature();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        int callingPid = Binder.getCallingPid();
        this.mVrController.setVrThreadLocked(
            i, callingPid, this.mProcessMap.getProcess(callingPid));
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void setPersistentVrThread(int i) {
    if (checkCallingPermission("android.permission.RESTRICTED_VR_ACCESS") != 0) {
      String str =
          "Permission Denial: setPersistentVrThread() from pid="
              + Binder.getCallingPid()
              + ", uid="
              + Binder.getCallingUid()
              + " requires android.permission.RESTRICTED_VR_ACCESS";
      Slog.w("ActivityTaskManager", str);
      throw new SecurityException(str);
    }
    enforceSystemHasVrFeature();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        int callingPid = Binder.getCallingPid();
        this.mVrController.setPersistentVrThreadLocked(
            i, callingPid, this.mProcessMap.getProcess(callingPid));
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void stopAppSwitches() {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.STOP_APP_SWITCHES", "stopAppSwitches");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAppSwitchesState = 0;
        this.mLastStopAppSwitchesTime = SystemClock.uptimeMillis();
        this.f1734mH.removeMessages(4);
        this.f1734mH.sendEmptyMessageDelayed(4, 500L);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void resumeAppSwitches() {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.STOP_APP_SWITCHES", "resumeAppSwitches");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAppSwitchesState = 2;
        this.f1734mH.removeMessages(4);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public long getLastStopAppSwitchesTime() {
    return this.mLastStopAppSwitchesTime;
  }

  public boolean shouldDisableNonVrUiLocked() {
    return this.mVrController.shouldDisableNonVrUiLocked();
  }

  public void applyUpdateVrModeLocked(final ActivityRecord activityRecord) {
    if (activityRecord.requestedVrComponent != null && activityRecord.getDisplayId() != 0) {
      Slog.i(
          "ActivityTaskManager",
          "Moving "
              + activityRecord.shortComponentName
              + " from display "
              + activityRecord.getDisplayId()
              + " to main display for VR");
      this.mRootWindowContainer.moveRootTaskToDisplay(activityRecord.getRootTaskId(), 0, true);
    }
    this.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$applyUpdateVrModeLocked$10(activityRecord);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyUpdateVrModeLocked$10(ActivityRecord activityRecord) {
    if (this.mVrController.onVrModeChanged(activityRecord)) {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          boolean shouldDisableNonVrUiLocked = this.mVrController.shouldDisableNonVrUiLocked();
          this.mWindowManager.disableNonVrUi(shouldDisableNonVrUiLocked);
          if (shouldDisableNonVrUiLocked) {
            this.mRootWindowContainer.removeRootTasksInWindowingModes(2);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }

  public int getPackageScreenCompatMode(String str) {
    int packageScreenCompatModeLocked;
    enforceNotIsolatedCaller("getPackageScreenCompatMode");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        packageScreenCompatModeLocked =
            this.mCompatModePackages.getPackageScreenCompatModeLocked(str);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return packageScreenCompatModeLocked;
  }

  public void setPackageScreenCompatMode(String str, int i) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.SET_SCREEN_COMPATIBILITY", "setPackageScreenCompatMode");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mCompatModePackages.setPackageScreenCompatModeLocked(str, i);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean getPackageAskScreenCompat(String str) {
    boolean packageAskCompatModeLocked;
    enforceNotIsolatedCaller("getPackageAskScreenCompat");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        packageAskCompatModeLocked = this.mCompatModePackages.getPackageAskCompatModeLocked(str);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return packageAskCompatModeLocked;
  }

  public void setPackageAskScreenCompat(String str, boolean z) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.SET_SCREEN_COMPATIBILITY", "setPackageAskScreenCompat");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mCompatModePackages.setPackageAskCompatModeLocked(str, z);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void sendAppStateBroadcaster() {
    Handler handler = AppStateBroadcaster.mObjHandler;
    if (handler != null) {
      handler.post(
          new Runnable() { // from class:
                           // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              ActivityTaskManagerService.this.lambda$sendAppStateBroadcaster$11();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendAppStateBroadcaster$11() {
    ActivityRecord activityRecord;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask != null) {
          activityRecord = topDisplayFocusedRootTask.topRunningActivityLocked();
          if (activityRecord == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
        } else {
          activityRecord = null;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (activityRecord == null) {
          return;
        }
        AppStateBroadcaster.sendApplicationFocusLoss(activityRecord.packageName);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public Task getTopDisplayFocusedRootTask() {
    return this.mRootWindowContainer.getTopDisplayFocusedRootTask();
  }

  public void notifyTaskPersisterLocked(Task task, boolean z) {
    this.mRecentTasks.notifyTaskPersisterLocked(task, z);
  }

  public boolean isKeyguardLocked(int i) {
    return this.mKeyguardController.isKeyguardLocked(i);
  }

  public void clearLaunchParamsForPackages(List list) {
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

  public void onPictureInPictureStateChanged(PictureInPictureUiState pictureInPictureUiState) {
    enforceTaskPermission("onPictureInPictureStateChanged");
    Task rootPinnedTask = this.mRootWindowContainer.getDefaultTaskDisplayArea().getRootPinnedTask();
    if (rootPinnedTask == null || rootPinnedTask.getTopMostActivity() == null) {
      return;
    }
    this.mWindowManager.mAtmService.mActivityClientController.onPictureInPictureStateChanged(
        rootPinnedTask.getTopMostActivity(), pictureInPictureUiState);
  }

  public void detachNavigationBarFromApp(IBinder iBinder) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
        "detachNavigationBarFromApp");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          getTransitionController().legacyDetachNavigationBarFromApp(iBinder);
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

  public IMultiTaskingBinder getMultiTaskingBinder() {
    return this.mMultiTaskingBinder;
  }

  public static boolean isMultiTaskingDumpsysCommand(String str) {
    return "multitasking".equals(str) || "mt".equals(str);
  }

  public Bitmap getResumedTaskThumbnail(int i) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.READ_FRAME_BUFFER", "getResumedTaskThumbnail()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1, null, false);
        if (anyTaskForId == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return null;
        }
        Bitmap snapshotAsBitmapForRecentsLocked =
            anyTaskForId.getSnapshotAsBitmapForRecentsLocked();
        WindowManagerService.resetPriorityAfterLockedSection();
        return snapshotAsBitmapForRecentsLocked;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public ParceledListSlice getCompatChangeablePackageInfoList(String str, int i) {
    enforceTaskPermission("getCompatChangeablePackageInfoList()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return CompatChangeableAppsService.getCompatChangeablePackageInfoList(str, i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void resetUserPackageSettings(int i, int i2) {
    enforceTaskPermission("resetUserPackageSettings()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      PackagesChange.resetAllIfNeeded(i, i2);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void dumpLastANRLocked(PrintWriter printWriter) {
    printWriter.println("ACTIVITY MANAGER LAST ANR (dumpsys activity lastanr)");
    String str = this.mLastANRState;
    if (str == null) {
      printWriter.println("  <no ANR has occurred since boot>");
    } else {
      printWriter.println(str);
    }
  }

  public void dumpLastANRTracesLocked(PrintWriter printWriter) {
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
          if (readLine != null) {
            printWriter.println(readLine);
          } else {
            bufferedReader.close();
            return;
          }
        } finally {
        }
      }
    } catch (IOException e) {
      printWriter.print("Unable to read: ");
      printWriter.print(e);
      printWriter.println();
    }
  }

  public void dumpTopResumedActivityLocked(PrintWriter printWriter) {
    printWriter.println("ACTIVITY MANAGER TOP-RESUMED (dumpsys activity top-resumed)");
    ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
    if (topResumedActivity != null) {
      topResumedActivity.dump(printWriter, "", true);
    }
  }

  public void dumpVisibleActivitiesLocked(PrintWriter printWriter, int i) {
    printWriter.println("ACTIVITY MANAGER VISIBLE ACTIVITIES (dumpsys activity visible)");
    boolean z = false;
    ArrayList dumpActivities = this.mRootWindowContainer.getDumpActivities("all", true, false, -1);
    boolean z2 = false;
    for (int size = dumpActivities.size() - 1; size >= 0; size--) {
      ActivityRecord activityRecord = (ActivityRecord) dumpActivities.get(size);
      if (activityRecord.isVisible() && (i == -1 || activityRecord.getDisplayId() == i)) {
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

  public void dumpActivitiesLocked(
      FileDescriptor fileDescriptor,
      PrintWriter printWriter,
      String[] strArr,
      int i,
      boolean z,
      boolean z2,
      String str,
      int i2) {
    dumpActivitiesLocked(
        fileDescriptor,
        printWriter,
        strArr,
        i,
        z,
        z2,
        str,
        i2,
        "ACTIVITY MANAGER ACTIVITIES (dumpsys activity activities)");
  }

  public void dumpActivitiesLocked(
      FileDescriptor fileDescriptor,
      PrintWriter printWriter,
      String[] strArr,
      int i,
      boolean z,
      boolean z2,
      String str,
      int i2,
      String str2) {
    boolean z3;
    printWriter.println(str2);
    boolean dumpActivities =
        this.mRootWindowContainer.dumpActivities(fileDescriptor, printWriter, z, z2, str, i2);
    boolean z4 = true;
    if (ActivityTaskSupervisor.printThisActivity(
        printWriter,
        this.mRootWindowContainer.getTopResumedActivity(),
        str,
        i2,
        dumpActivities,
        "  ResumedActivity: ",
        null)) {
      dumpActivities = false;
      z3 = true;
    } else {
      z3 = dumpActivities;
    }
    if (str == null) {
      if (dumpActivities) {
        printWriter.println();
      }
      this.mTaskSupervisor.dump(printWriter, "  ");
      this.mTaskOrganizerController.dump(printWriter, "  ");
      this.mVisibleActivityProcessTracker.dump(printWriter, "  ");
      this.mActiveUids.dump(printWriter, "  ");
      if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
        SizeCompatPolicyManager.get().dump(printWriter, "  ");
      }
      PackagesChange.dumpAll(printWriter, "  ");
      if (this.mDemoteTopAppReasons != 0) {
        printWriter.println("  mDemoteTopAppReasons=" + this.mDemoteTopAppReasons);
      }
    } else {
      z4 = z3;
    }
    if (z4) {
      return;
    }
    printWriter.println("  (nothing)");
  }

  public void dumpActivityContainersLocked(PrintWriter printWriter) {
    printWriter.println("ACTIVITY MANAGER CONTAINERS (dumpsys activity containers)");
    this.mRootWindowContainer.dumpChildrenNames(printWriter, " ");
    printWriter.println(" ");
  }

  public void dumpActivityStarterLocked(PrintWriter printWriter, String str) {
    printWriter.println("ACTIVITY MANAGER STARTER (dumpsys activity starter)");
    getActivityStartController().dump(printWriter, "", str);
  }

  public void dumpInstalledPackagesConfig(PrintWriter printWriter) {
    this.mPackageConfigPersister.dump(printWriter, getCurrentUserId());
  }

  public boolean dumpActivity(
      FileDescriptor fileDescriptor,
      PrintWriter printWriter,
      String str,
      String[] strArr,
      int i,
      boolean z,
      boolean z2,
      boolean z3,
      int i2,
      int i3) {
    ArrayList dumpActivities;
    Task task;
    boolean z4;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        dumpActivities = this.mRootWindowContainer.getDumpActivities(str, z2, z3, i3);
      } finally {
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    if (CoreRune.BAIDU_CARLIFE && BaiduCarlifeADBConnectUtils.isCarlifeForceConnect()) {
      BaiduCarlifeADBConnectUtils.printCarlifeDumpActivity(printWriter);
      return true;
    }
    boolean z5 = false;
    if (dumpActivities.size() <= 0) {
      return false;
    }
    String[] strArr2 = new String[strArr.length - i];
    System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
    Task task2 = null;
    int size = dumpActivities.size() - 1;
    boolean z6 = false;
    while (size >= 0) {
      ActivityRecord activityRecord = (ActivityRecord) dumpActivities.get(size);
      if (z6) {
        printWriter.println();
      }
      WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock2) {
        try {
          Task task3 = activityRecord.getTask();
          int displayId = task3.getDisplayId();
          if (i2 == -1 || displayId == i2) {
            if (task2 != task3) {
              printWriter.print("TASK ");
              printWriter.print(task3.affinity);
              printWriter.print(" id=");
              printWriter.print(task3.mTaskId);
              printWriter.print(" userId=");
              printWriter.print(task3.mUserId);
              printDisplayInfoAndNewLine(printWriter, activityRecord);
              if (z) {
                task3.dump(printWriter, "  ");
              }
              task = task3;
              z4 = true;
            } else {
              task = task2;
              z4 = z5;
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            dumpActivity(
                "  ",
                fileDescriptor,
                printWriter,
                (ActivityRecord) dumpActivities.get(size),
                strArr2,
                z);
            task2 = task;
            z5 = z4;
          } else {
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        } finally {
        }
      }
      size--;
      z6 = true;
    }
    if (!z5) {
      printWriter.println("(nothing)");
    }
    return true;
  }

  public final void dumpActivity(
      String str,
      FileDescriptor fileDescriptor,
      PrintWriter printWriter,
      ActivityRecord activityRecord,
      String[] strArr,
      boolean z) {
    IApplicationThread iApplicationThread;
    String str2 = str + "  ";
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        printWriter.print(str);
        printWriter.print("ACTIVITY ");
        printWriter.print(activityRecord.shortComponentName);
        printWriter.print(" ");
        printWriter.print(Integer.toHexString(System.identityHashCode(activityRecord)));
        printWriter.print(" pid=");
        if (activityRecord.hasProcess()) {
          printWriter.print(activityRecord.app.getPid());
          iApplicationThread = activityRecord.app.getThread();
        } else {
          printWriter.print("(not running)");
          iApplicationThread = null;
        }
        printWriter.print(" userId=");
        printWriter.print(activityRecord.mUserId);
        printWriter.print(" uid=");
        printWriter.print(activityRecord.getUid());
        printDisplayInfoAndNewLine(printWriter, activityRecord);
        if (z) {
          activityRecord.dump(printWriter, str2, true);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    if (iApplicationThread != null) {
      printWriter.flush();
      try {
        TransferPipe transferPipe = new TransferPipe();
        try {
          iApplicationThread.dumpActivity(
              transferPipe.getWriteFd(), activityRecord.token, str2, strArr);
          transferPipe.go(fileDescriptor);
          transferPipe.close();
        } finally {
        }
      } catch (RemoteException unused) {
        printWriter.println(str2 + "Got a RemoteException while dumping the activity");
      } catch (IOException e) {
        printWriter.println(str2 + "Failure while dumping the activity: " + e);
      }
    }
  }

  public final void printDisplayInfoAndNewLine(
      PrintWriter printWriter, ActivityRecord activityRecord) {
    printWriter.print(" displayId=");
    DisplayContent displayContent = activityRecord.getDisplayContent();
    if (displayContent == null) {
      printWriter.println("N/A");
      return;
    }
    Display display = displayContent.getDisplay();
    printWriter.print(display.getDisplayId());
    printWriter.print("(type=");
    printWriter.print(Display.typeToString(display.getType()));
    printWriter.println(")");
  }

  public final void writeSleepStateToProto(ProtoOutputStream protoOutputStream, int i, boolean z) {
    long start = protoOutputStream.start(1146756268059L);
    protoOutputStream.write(1159641169921L, PowerManagerInternal.wakefulnessToProtoEnum(i));
    int size = this.mRootWindowContainer.mSleepTokens.size();
    for (int i2 = 0; i2 < size; i2++) {
      protoOutputStream.write(
          2237677961218L,
          ((RootWindowContainer.SleepToken) this.mRootWindowContainer.mSleepTokens.valueAt(i2))
              .toString());
    }
    protoOutputStream.write(1133871366147L, this.mSleeping);
    protoOutputStream.write(1133871366148L, this.mShuttingDown);
    protoOutputStream.write(1133871366149L, z);
    protoOutputStream.end(start);
  }

  public int getCurrentUserId() {
    return this.mAmInternal.getCurrentUserId();
  }

  public static void enforceNotIsolatedCaller(String str) {
    if (UserHandle.isIsolated(Binder.getCallingUid())) {
      throw new SecurityException("Isolated process not allowed to call " + str);
    }
  }

  public Configuration getConfiguration() {
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

  public Configuration getGlobalConfiguration() {
    RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
    return rootWindowContainer != null
        ? rootWindowContainer.getConfiguration()
        : new Configuration();
  }

  public boolean updateConfigurationLocked(
      Configuration configuration, ActivityRecord activityRecord, boolean z) {
    return updateConfigurationLocked(configuration, activityRecord, z, false);
  }

  public boolean updateConfigurationLocked(
      Configuration configuration, ActivityRecord activityRecord, boolean z, boolean z2) {
    return updateConfigurationLocked(configuration, activityRecord, z, false, -10000, z2);
  }

  public void updatePersistentConfiguration(Configuration configuration, int i) {
    updatePersistentConfiguration(configuration, i, Binder.getCallingPid());
  }

  public void updatePersistentConfiguration(Configuration configuration, int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          configuration.windowConfiguration.setToDefaults();
          this.mAmInternal.setUpdateConfigurationCallerLocked(i2);
          updateConfigurationLocked(configuration, null, false, true, i, false);
          this.mAmInternal.resetUpdateConfigurationCallerLocked();
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

  public final boolean updateConfigurationLocked(
      Configuration configuration,
      ActivityRecord activityRecord,
      boolean z,
      boolean z2,
      int i,
      boolean z3) {
    return updateConfigurationLocked(configuration, activityRecord, z, z2, i, z3, null);
  }

  public boolean updateConfigurationLocked(
      final Configuration configuration,
      ActivityRecord activityRecord,
      boolean z,
      boolean z2,
      int i,
      boolean z3,
      UpdateConfigurationResult updateConfigurationResult) {
    int i2;
    deferWindowLayout();
    try {
      final Configuration configuration2 =
          (!CoreRune.FW_UI_MODE_ANIMATION || WindowManagerService.sEnableShellTransitions)
              ? null
              : new Configuration(getGlobalConfiguration());
      Configuration configuration3 = new Configuration(getGlobalConfiguration());
      if (configuration != null) {
        i2 = updateGlobalConfigurationLocked(configuration, z, z2, i);
        if (CoreRune.FW_UI_MODE_ANIMATION
            && configuration2 != null
            && configuration2.isNightModeActive() != configuration.isNightModeActive()) {
          this.mRootWindowContainer.forAllDisplays(
              new Consumer() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ActivityTaskManagerService.this.lambda$updateConfigurationLocked$12(
                      configuration2, configuration, (DisplayContent) obj);
                }
              });
        }
        if (configuration3.isNightModeActive() != configuration.isNightModeActive()) {
          this.mWindowManager.mTaskSnapshotController.snapshotForNightMode();
        }
      } else {
        i2 = 0;
      }
      boolean ensureConfigAndVisibilityAfterUpdate =
          !z3 ? ensureConfigAndVisibilityAfterUpdate(activityRecord, i2) : true;
      if (updateConfigurationResult != null) {
        updateConfigurationResult.changes = i2;
        updateConfigurationResult.activityRelaunched = !ensureConfigAndVisibilityAfterUpdate;
      }
      return ensureConfigAndVisibilityAfterUpdate;
    } finally {
      continueWindowLayout();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateConfigurationLocked$12(
      Configuration configuration, Configuration configuration2, DisplayContent displayContent) {
    TransitionRequestInfo.DisplayChange createDisplayChangeIfNeeded;
    if (!CoreRune.FW_CUSTOM_DISPLAY_CHANGE_ANIM
        || (createDisplayChangeIfNeeded =
                displayContent.createDisplayChangeIfNeeded(configuration, configuration2))
            == null) {
      return;
    }
    DisplayContent.DisplayAnimationPair selectDisplayChangeAnimation =
        displayContent.selectDisplayChangeAnimation(createDisplayChangeIfNeeded);
    int i = selectDisplayChangeAnimation.mEnter;
    if (i == 0 && selectDisplayChangeAnimation.mExit == 0) {
      return;
    }
    this.mWindowManager.startFreezingDisplay(selectDisplayChangeAnimation.mExit, i, displayContent);
  }

  public int updateGlobalConfigurationLocked(
      Configuration configuration, boolean z, boolean z2, int i) {
    DisplayContent displayContent;
    int i2;
    this.mTempConfig.setTo(getGlobalConfiguration());
    int updateFrom = this.mTempConfig.updateFrom(configuration);
    if (updateFrom == 0) {
      return 0;
    }
    Trace.traceBegin(32L, "updateGlobalConfiguration");
    if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_CONFIGURATION,
          -927199900,
          0,
          (String) null,
          new Object[] {String.valueOf(configuration)});
    }
    EventLogTags.writeConfigurationChanged(updateFrom);
    FrameworkStatsLog.write(
        66,
        configuration.colorMode,
        configuration.densityDpi,
        configuration.fontScale,
        configuration.hardKeyboardHidden,
        configuration.keyboard,
        configuration.keyboardHidden,
        configuration.mcc,
        configuration.mnc,
        configuration.navigation,
        configuration.navigationHidden,
        configuration.orientation,
        configuration.screenHeightDp,
        configuration.screenLayout,
        configuration.screenWidthDp,
        configuration.smallestScreenWidthDp,
        configuration.touchscreen,
        configuration.uiMode);
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
      } else {
        i2 = 0;
      }
      this.mAmInternal.addToLocaleChangedHistoryLocked(
          this.mRootWindowContainer.getConfiguration().getLocales(),
          locales,
          configuration.userSetLocale);
      SystemProperties.set("persist.sys.locale", locales.get(i2).toLanguageTag());
      LocaleList.setDefault(locales, i2);
    }
    this.mTempConfig.seq = increaseConfigurationSeqLocked();
    Slog.i(
        "ActivityTaskManager",
        "Config changes=" + Integer.toHexString(updateFrom) + " " + this.mTempConfig);
    this.mUsageStatsInternal.reportConfigurationChange(
        this.mTempConfig, this.mAmInternal.getCurrentUserId());
    updateShouldShowDialogsLocked(this.mTempConfig);
    AttributeCache instance = AttributeCache.instance();
    if (instance != null) {
      instance.updateConfiguration(this.mTempConfig);
    }
    this.mSystemThread.applyConfigurationToResources(this.mTempConfig);
    if (z2 && Settings.System.hasInterestingConfigurationChanges(updateFrom)) {
      if ((1073741824 & updateFrom) != 0) {
        this.mDexController.setGlobalFontScale(this.mTempConfig.fontScale);
      }
      this.f1734mH.sendMessage(
          PooledLambda.obtainMessage(
              new TriConsumer() { // from class:
                                  // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda17
                public final void accept(Object obj, Object obj2, Object obj3) {
                  ((ActivityTaskManagerService) obj)
                      .sendPutConfigurationForUserMsg(
                          ((Integer) obj2).intValue(), (Configuration) obj3);
                }
              },
              this,
              Integer.valueOf(i),
              new Configuration(this.mTempConfig)));
    }
    int sysUiPid = this.mExt.getSysUiPid();
    boolean z3 = sysUiPid > -1;
    SparseArray pidMap = this.mProcessMap.getPidMap();
    int size = pidMap.size() - 1;
    while (size >= 0) {
      int keyAt = pidMap.keyAt(size);
      if (z3) {
        if (pidMap.get(sysUiPid) != null) {
          size++;
          keyAt = sysUiPid;
        }
        z3 = false;
      } else if (keyAt == sysUiPid) {
        size--;
      }
      WindowProcessController windowProcessController = (WindowProcessController) pidMap.get(keyAt);
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -503656156,
            0,
            (String) null,
            new Object[] {
              String.valueOf(windowProcessController.mName), String.valueOf(this.mTempConfig)
            });
      }
      windowProcessController.onConfigurationChanged(this.mTempConfig);
      size--;
    }
    this.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new TriConsumer() { // from class:
                                // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda18
              public final void accept(Object obj, Object obj2, Object obj3) {
                ((ActivityManagerInternal) obj)
                    .broadcastGlobalConfigurationChanged(
                        ((Integer) obj2).intValue(), ((Boolean) obj3).booleanValue());
              }
            },
            this.mAmInternal,
            Integer.valueOf(updateFrom),
            Boolean.valueOf(z)));
    Trace.traceBegin(32L, "RootConfigChange");
    this.mRootWindowContainer.onConfigurationChanged(this.mTempConfig);
    Trace.traceEnd(32L);
    if ((updateFrom & 512) != 0
        && this.mDexController.getDexModeLocked() == 2
        && (displayContent = this.mRootWindowContainer.getDisplayContent(2)) != null) {
      displayContent.reconfigureDisplayLocked();
    }
    if (CoreRune.FW_CUSTOM_LETTERBOX) {
      CustomLetterboxConfiguration.onConfigurationChanged(updateFrom);
    }
    return updateFrom;
  }

  public final int increaseAssetConfigurationSeq() {
    int i = this.mGlobalAssetsSeq + 1;
    this.mGlobalAssetsSeq = i;
    int max = Math.max(i, 1);
    this.mGlobalAssetsSeq = max;
    return max;
  }

  public void updateAssetConfiguration(List list, boolean z) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        int increaseAssetConfigurationSeq = increaseAssetConfigurationSeq();
        if (z) {
          this.mExt.mAvoidCompatDisplayInsets = true;
          Configuration configuration = new Configuration();
          configuration.assetsSeq = increaseAssetConfigurationSeq;
          updateConfiguration(configuration);
          this.mExt.mAvoidCompatDisplayInsets = false;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
          WindowProcessController windowProcessController =
              (WindowProcessController) list.get(size);
          windowProcessController.updateAssetConfiguration(increaseAssetConfigurationSeq);
          List taskIds = windowProcessController.getTaskIds();
          Slog.e(
              "ActivityTaskManager",
              "Removing snapshot from cache for "
                  + windowProcessController.mInfo.packageName
                  + " "
                  + taskIds);
          for (int size2 = taskIds.size() - 1; size2 >= 0; size2--) {
            this.mWindowManager.mTaskSnapshotController.removeSnapshotCache(
                ((Integer) taskIds.get(size2)).intValue());
          }
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void startLaunchPowerMode(int i) {
    PowerManagerInternal powerManagerInternal = this.mPowerManagerInternal;
    if (powerManagerInternal != null) {
      powerManagerInternal.setPowerMode(5, true);
    }
    this.mLaunchPowerModeReasons |= i;
    if ((i & 4) != 0) {
      if (this.mRetainPowerModeAndTopProcessState) {
        this.f1734mH.removeMessages(3);
      }
      this.mRetainPowerModeAndTopProcessState = true;
      this.f1734mH.sendEmptyMessageDelayed(3, 1000L);
      Slog.d("ActivityTaskManager", "Temporarily retain top process state for launching app");
    }
  }

  public void endLaunchPowerMode(int i) {
    PowerManagerInternal powerManagerInternal;
    int i2 = this.mLaunchPowerModeReasons;
    if (i2 == 0) {
      return;
    }
    int i3 = (~i) & i2;
    this.mLaunchPowerModeReasons = i3;
    if ((i3 & 4) != 0) {
      boolean z = true;
      for (int childCount = this.mRootWindowContainer.getChildCount() - 1;
          childCount >= 0;
          childCount--) {
        z &=
            ((DisplayContent) this.mRootWindowContainer.getChildAt(childCount))
                .mUnknownAppVisibilityController.allResolved();
      }
      if (z) {
        this.mLaunchPowerModeReasons &= -5;
        this.mRetainPowerModeAndTopProcessState = false;
        this.f1734mH.removeMessages(3);
      }
    }
    if (this.mLaunchPowerModeReasons != 0
        || (powerManagerInternal = this.mPowerManagerInternal) == null) {
      return;
    }
    powerManagerInternal.setPowerMode(5, false);
    ActivityManagerPerformance activityManagerPerformance = this.mAMBooster;
    if (activityManagerPerformance != null) {
      activityManagerPerformance.onActivityVisibleLocked(this.mLastResumedActivity);
    }
  }

  public void deferWindowLayout() {
    if (!this.mWindowManager.mWindowPlacerLocked.isLayoutDeferred()) {
      this.mLayoutReasons = 0;
    }
    this.mWindowManager.mWindowPlacerLocked.deferLayout();
  }

  public void continueWindowLayout() {
    this.mWindowManager.mWindowPlacerLocked.continueLayout(this.mLayoutReasons != 0);
  }

  public void addWindowLayoutReasons(int i) {
    this.mLayoutReasons = i | this.mLayoutReasons;
  }

  public final void updateEventDispatchingLocked(boolean z) {
    this.mWindowManager.setEventDispatching(z && !this.mShuttingDown);
  }

  public final void sendPutConfigurationForUserMsg(int i, Configuration configuration) {
    Settings.System.putConfigurationForUser(this.mContext.getContentResolver(), configuration, i);
  }

  public boolean isActivityStartsLoggingEnabled() {
    return this.mAmInternal.isActivityStartsLoggingEnabled();
  }

  public boolean isBackgroundActivityStartsEnabled() {
    return this.mAmInternal.isBackgroundActivityStartsEnabled();
  }

  public static long getInputDispatchingTimeoutMillisLocked(ActivityRecord activityRecord) {
    if (activityRecord == null || !activityRecord.hasProcess()) {
      return InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    }
    return getInputDispatchingTimeoutMillisLocked(activityRecord.app);
  }

  public static long getInputDispatchingTimeoutMillisLocked(
      WindowProcessController windowProcessController) {
    if (windowProcessController == null) {
      return InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    }
    return windowProcessController.getInputDispatchingTimeoutMillis();
  }

  public final void updateShouldShowDialogsLocked(Configuration configuration) {
    boolean z = false;
    boolean z2 =
        (configuration.keyboard == 1
                && configuration.touchscreen == 1
                && configuration.navigation == 1)
            ? false
            : true;
    boolean z3 =
        Settings.Global.getInt(this.mContext.getContentResolver(), "hide_error_dialogs", 0) != 0;
    if (z2 && ActivityTaskManager.currentUiModeSupportsErrorDialogs(configuration) && !z3) {
      z = true;
    }
    this.mShowDialogs = z;
  }

  public final void updateFontScaleIfNeeded(int i) {
    if (i != getCurrentUserId()) {
      return;
    }
    float floatForUser =
        Settings.System.getFloatForUser(this.mContext.getContentResolver(), "font_scale", 1.0f, i);
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (getGlobalConfiguration().fontScale == floatForUser) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        Configuration computeNewConfiguration = this.mWindowManager.computeNewConfiguration(0);
        computeNewConfiguration.fontScale = floatForUser;
        updatePersistentConfiguration(computeNewConfiguration, i);
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final void updateFontWeightAdjustmentIfNeeded(int i) {
    if (i != getCurrentUserId()) {
      return;
    }
    int intForUser =
        Settings.Secure.getIntForUser(
            this.mContext.getContentResolver(), "font_weight_adjustment", Integer.MAX_VALUE, i);
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (getGlobalConfiguration().fontWeightAdjustment == intForUser) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        Configuration computeNewConfiguration = this.mWindowManager.computeNewConfiguration(0);
        computeNewConfiguration.fontWeightAdjustment = intForUser;
        updatePersistentConfiguration(computeNewConfiguration, i);
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final void updateGestureHint() {
    this.mGesutreHint =
        Settings.Global.getInt(
            this.mContext.getContentResolver(), "navigation_bar_gesture_hint", 1);
  }

  public boolean isSleepingOrShuttingDownLocked() {
    return isSleepingLocked() || this.mShuttingDown;
  }

  public boolean isSleepingLocked() {
    return this.mSleeping && !isOccludingForAllDisplay();
  }

  public void setLastResumedActivityUncheckLocked(ActivityRecord activityRecord, String str) {
    IVoiceInteractionSession iVoiceInteractionSession;
    int i;
    Task task = activityRecord.getTask();
    if (task.isActivityTypeStandard()) {
      AppTimeTracker appTimeTracker = this.mCurAppTimeTracker;
      if (appTimeTracker != activityRecord.appTimeTracker) {
        if (appTimeTracker != null) {
          appTimeTracker.stop();
          this.f1734mH.obtainMessage(1, this.mCurAppTimeTracker).sendToTarget();
          this.mRootWindowContainer.clearOtherAppTimeTrackers(activityRecord.appTimeTracker);
          this.mCurAppTimeTracker = null;
        }
        AppTimeTracker appTimeTracker2 = activityRecord.appTimeTracker;
        if (appTimeTracker2 != null) {
          this.mCurAppTimeTracker = appTimeTracker2;
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
      finishRunningVoiceLocked();
      ActivityRecord activityRecord2 = this.mLastResumedActivity;
      if (activityRecord2 != null) {
        Task task2 = activityRecord2.getTask();
        if (task2 == null || (iVoiceInteractionSession = task2.voiceSession) == null) {
          iVoiceInteractionSession = this.mLastResumedActivity.voiceSession;
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
      ActivityRecord.State state = activityRecord4.getState();
      ActivityRecord.State state2 = ActivityRecord.State.RESUMED;
      if (state == state2
          && activityRecord.getState() == state2
          && activityRecord != this.mLastResumedActivity) {
        ActivityManagerPerformance.notifyMultiWindowChanged(activityRecord);
      }
    }
    ActivityRecord activityRecord5 = this.mLastResumedActivity;
    Task task3 = activityRecord5 != null ? activityRecord5.getTask() : null;
    updateResumedAppTrace(activityRecord);
    this.mLastResumedActivity = activityRecord;
    boolean z = false;
    if (!getTransitionController().isTransientCollect(activityRecord)) {
      ActivityRecord activityRecord6 = activityRecord.mDisplayContent.mFocusedApp;
      Task task4 = activityRecord6 != null ? activityRecord6.getTask() : null;
      boolean focusedApp = activityRecord.mDisplayContent.setFocusedApp(activityRecord);
      if (focusedApp) {
        this.mWindowManager.updateFocusedWindowLocked(0, true);
      }
      if (task != task4) {
        if (task4 != null) {
          this.mTaskChangeNotificationController.notifyTaskFocusChanged(task4.mTaskId, false);
        }
        this.mTaskChangeNotificationController.notifyTaskFocusChanged(task.mTaskId, true);
      } else if (!task.equals(task3)) {
        if (task3 != null) {
          this.mTaskChangeNotificationController.notifyTaskFocusChanged(task3.mTaskId, false);
        }
        this.mTaskChangeNotificationController.notifyTaskFocusChanged(task.mTaskId, true);
      }
      z = focusedApp;
    }
    if ((!CoreRune.SYSFW_APP_SPEG
            || (activityRecord.mDisplayContent.getDisplayInfo().flags & 32768) == 0)
        && task != task3) {
      this.mTaskSupervisor.mRecentTasks.add(task);
    }
    task.touchGainFocusTime();
    if (z) {
      applyUpdateLockStateLocked(activityRecord);
    }
    if (this.mVrController.mVrService != null) {
      applyUpdateVrModeLocked(activityRecord);
    }
    EventLogTags.writeWmSetResumedActivity(
        activityRecord.mUserId, activityRecord.shortComponentName, str);
    this.mPersonaActivityHelper.notifySetResumedActivityUncheckLocked(activityRecord);
    if (CoreRune.FW_APPLOCK) {
      this.mExt.startAppLockActivity(activityRecord);
    }
  }

  public void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) {
    this.mExt.startAppLockService(iBinder, intent, z, str);
  }

  public List getAppLockedPackageList() {
    return this.mExt.getAppLockedPackageList();
  }

  public void setAppLockedUnLockPackage(String str) {
    this.mExt.setAppLockedUnLockPackage(str);
  }

  public boolean isAppLockedPackage(String str) {
    return this.mExt.isAppLockedPackage(str);
  }

  public void clearAppLockedUnLockedApp() {
    this.mExt.clearAppLockedUnLockedApp();
  }

  public String getAppLockedLockType() {
    return this.mExt.getAppLockedLockType();
  }

  public String getAppLockedCheckAction() {
    return this.mExt.getAppLockedCheckAction();
  }

  public void setAppLockedVerifying(String str, boolean z) {
    this.mExt.setAppLockedVerifying(str, z);
  }

  public boolean isAppLockedVerifying(String str) {
    return this.mExt.isAppLockedVerifying(str);
  }

  public void setApplockLockedAppsPackage(String str) {
    this.mExt.setApplockLockedAppsPackage(str);
  }

  public void setApplockLockedAppsClass(String str) {
    this.mExt.setApplockLockedAppsClass(str);
  }

  public void setApplockType(int i) {
    this.mExt.setApplockType(i);
  }

  public void setApplockEnabled(boolean z) {
    this.mExt.setApplockEnabled(z);
  }

  public void setSsecureHiddenAppsPackages(String str) {
    this.mExt.setSsecureHiddenAppsPackages(str);
  }

  public String getApplockLockedAppsPackage() {
    return this.mExt.getApplockLockedAppsPackage();
  }

  public String getApplockLockedAppsClass() {
    return this.mExt.getApplockLockedAppsClass();
  }

  public int getApplockType() {
    return this.mExt.getApplockType();
  }

  public String getSsecureHiddenAppsPackages() {
    return this.mExt.getSsecureHiddenAppsPackages();
  }

  public boolean isApplockEnabled() {
    return this.mExt.isApplockEnabled();
  }

  public final class SleepTokenAcquirerImpl
      implements ActivityTaskManagerInternal.SleepTokenAcquirer {
    public final SparseArray mSleepTokens = new SparseArray();
    public final String mTag;

    public SleepTokenAcquirerImpl(String str) {
      this.mTag = str;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer
    public void acquire(int i) {
      acquire(i, false);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer
    public void acquire(int i, boolean z) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (!this.mSleepTokens.contains(i)) {
            this.mSleepTokens.append(
                i,
                ActivityTaskManagerService.this.mRootWindowContainer.createSleepToken(
                    this.mTag, i, z));
            ActivityTaskManagerService.this.updateSleepIfNeededLocked();
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer
    public void release(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          RootWindowContainer.SleepToken sleepToken =
              (RootWindowContainer.SleepToken) this.mSleepTokens.get(i);
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

  /* JADX WARN: Code restructure failed: missing block: B:6:0x002d, code lost:

     if (r2 != false) goto L18;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void updateSleepIfNeededLocked() {
    boolean z = true;
    boolean z2 = !this.mRootWindowContainer.hasAwakeDisplay();
    boolean z3 = this.mSleeping;
    if (!z2) {
      if (z3) {
        this.mSleeping = false;
        FrameworkStatsLog.write(14, 2);
        startTimeTrackingFocusedActivityLocked();
        this.mTopProcessState = 2;
        Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP");
        this.mTaskSupervisor.comeOutOfSleepIfNeededLocked();
      }
      this.mRootWindowContainer.applySleepTokens(true);
    } else if (!this.mSleeping && z2) {
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
    } else {
      Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP_SLEEPING#2");
      this.mRootWindowContainer.applySleepTokens(false);
      z = false;
    }
    if (z) {
      updateOomAdj();
    }
  }

  public void updateOomAdj() {
    this.f1734mH.removeCallbacks(this.mUpdateOomAdjRunnable);
    this.f1734mH.post(this.mUpdateOomAdjRunnable);
  }

  public void updateCpuStats() {
    HandlerC2878H handlerC2878H = this.f1734mH;
    final ActivityManagerInternal activityManagerInternal = this.mAmInternal;
    Objects.requireNonNull(activityManagerInternal);
    handlerC2878H.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda19
          @Override // java.lang.Runnable
          public final void run() {
            activityManagerInternal.updateCpuStats();
          }
        });
  }

  public void updateBatteryStats(ActivityRecord activityRecord, boolean z) {
    this.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new QuintConsumer() { // from class:
                                  // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda24
              public final void accept(
                  Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                ((ActivityManagerInternal) obj)
                    .updateBatteryStats(
                        (ComponentName) obj2,
                        ((Integer) obj3).intValue(),
                        ((Integer) obj4).intValue(),
                        ((Boolean) obj5).booleanValue());
              }
            },
            this.mAmInternal,
            activityRecord.mActivityComponent,
            Integer.valueOf(activityRecord.app.mUid),
            Integer.valueOf(activityRecord.mUserId),
            Boolean.valueOf(z)));
  }

  public void updateTopApp(ActivityRecord activityRecord) {
    if (activityRecord == null) {
      activityRecord = this.mRootWindowContainer.getTopResumedActivity();
    }
    this.mTopApp = activityRecord != null ? activityRecord.app : null;
    if (this.mTopApp == this.mPreviousProcess) {
      this.mPreviousProcess = null;
    }
  }

  public void updatePreviousProcess(ActivityRecord activityRecord) {
    if (activityRecord.app == null
        || this.mTopApp == null
        || activityRecord.app == this.mTopApp
        || activityRecord.lastVisibleTime <= this.mPreviousProcessVisibleTime
        || activityRecord.app == this.mHomeProcess) {
      return;
    }
    this.mPreviousProcess = activityRecord.app;
    this.mPreviousProcessVisibleTime = activityRecord.lastVisibleTime;
  }

  public void updateActivityUsageStats(ActivityRecord activityRecord, int i) {
    int i2;
    Task task = activityRecord.getTask();
    if (task != null) {
      ActivityRecord rootActivity = task.getRootActivity();
      r1 = rootActivity != null ? rootActivity.mActivityComponent : null;
      i2 = task.mTaskId;
    } else {
      i2 = -1;
    }
    this.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new OctConsumer() { // from class:
                                // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda26
              public final void accept(
                  Object obj,
                  Object obj2,
                  Object obj3,
                  Object obj4,
                  Object obj5,
                  Object obj6,
                  Object obj7,
                  Object obj8) {
                ((ActivityManagerInternal) obj)
                    .updateActivityUsageStatsWithIntent(
                        (ComponentName) obj2,
                        ((Integer) obj3).intValue(),
                        ((Integer) obj4).intValue(),
                        (IBinder) obj5,
                        (ComponentName) obj6,
                        (ActivityId) obj7,
                        (Intent) obj8);
              }
            },
            this.mAmInternal,
            activityRecord.mActivityComponent,
            Integer.valueOf(activityRecord.mUserId),
            Integer.valueOf(i),
            activityRecord.token,
            r1,
            new ActivityId(i2, activityRecord.shareableActivityToken),
            activityRecord.intent));
  }

  public void startProcessAsync(ActivityRecord activityRecord, boolean z, boolean z2, String str) {
    startProcessAsync(activityRecord, z, z2, str, -1, -1);
  }

  public void startProcessAsync(
      ActivityRecord activityRecord, boolean z, boolean z2, String str, int i, int i2) {
    try {
      if (Trace.isTagEnabled(32L)) {
        Trace.traceBegin(32L, "dispatchingStartProcess:" + activityRecord.processName);
      }
      updateActivityRecordForColorThemeIfNeeded(activityRecord);
      this.f1734mH.sendMessage(
          PooledLambda.obtainMessage(
              new NonaConsumer() { // from class:
                                   // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda23
                public final void accept(
                    Object obj,
                    Object obj2,
                    Object obj3,
                    Object obj4,
                    Object obj5,
                    Object obj6,
                    Object obj7,
                    Object obj8,
                    Object obj9) {
                  ((ActivityManagerInternal) obj)
                      .startProcess(
                          (String) obj2,
                          (ApplicationInfo) obj3,
                          ((Boolean) obj4).booleanValue(),
                          ((Boolean) obj5).booleanValue(),
                          (String) obj6,
                          (ComponentName) obj7,
                          ((Integer) obj8).intValue(),
                          ((Integer) obj9).intValue());
                }
              },
              this.mAmInternal,
              activityRecord.processName,
              activityRecord.info.applicationInfo,
              Boolean.valueOf(z),
              Boolean.valueOf(z2),
              str,
              activityRecord.intent.getComponent(),
              Integer.valueOf(i),
              Integer.valueOf(i2)));
    } finally {
      Trace.traceEnd(32L);
    }
  }

  public void startProcessAsyncForActiveLaunch(
      String str,
      ApplicationInfo applicationInfo,
      boolean z,
      boolean z2,
      String str2,
      ComponentName componentName,
      boolean z3,
      int i) {
    this.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new NonaConsumer() { // from class:
                                 // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda20
              public final void accept(
                  Object obj,
                  Object obj2,
                  Object obj3,
                  Object obj4,
                  Object obj5,
                  Object obj6,
                  Object obj7,
                  Object obj8,
                  Object obj9) {
                ((ActivityManagerInternal) obj)
                    .startProcess(
                        (String) obj2,
                        (ApplicationInfo) obj3,
                        ((Boolean) obj4).booleanValue(),
                        ((Boolean) obj5).booleanValue(),
                        (String) obj6,
                        (ComponentName) obj7,
                        ((Boolean) obj8).booleanValue(),
                        ((Integer) obj9).intValue());
              }
            },
            this.mAmInternal,
            str,
            applicationInfo,
            Boolean.valueOf(z),
            Boolean.valueOf(z2),
            str2,
            componentName,
            Boolean.valueOf(z3),
            Integer.valueOf(i)));
  }

  public void setBooting(boolean z) {
    this.mAmInternal.setBooting(z);
  }

  public boolean isBooting() {
    return this.mAmInternal.isBooting();
  }

  public void setBooted(boolean z) {
    this.mAmInternal.setBooted(z);
  }

  public boolean isBooted() {
    return this.mAmInternal.isBooted();
  }

  public void postFinishBooting(final boolean z, final boolean z2) {
    this.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda25
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$postFinishBooting$13(z, z2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$postFinishBooting$13(boolean z, boolean z2) {
    if (z) {
      this.mAmInternal.finishBooting();
      this.mAMBooster = ActivityManagerPerformance.getBooster(this, this.mContext);
      this.mMultiTaskingController.finishBooting();
    }
    if (z2) {
      this.mInternal.enableScreenAfterBoot(isBooted());
    }
  }

  public void setHeavyWeightProcess(ActivityRecord activityRecord) {
    this.mHeavyWeightProcess = activityRecord.app;
    this.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new QuadConsumer() { // from class:
                                 // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda22
              public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                ((ActivityTaskManagerService) obj)
                    .postHeavyWeightProcessNotification(
                        (WindowProcessController) obj2, (Intent) obj3, ((Integer) obj4).intValue());
              }
            },
            this,
            activityRecord.app,
            activityRecord.intent,
            Integer.valueOf(activityRecord.mUserId)));
  }

  public void clearHeavyWeightProcessIfEquals(WindowProcessController windowProcessController) {
    if (this.mHeavyWeightProcess == null || this.mHeavyWeightProcess != windowProcessController) {
      return;
    }
    this.mHeavyWeightProcess = null;
    this.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new BiConsumer() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda4
              @Override // java.util.function.BiConsumer
              public final void accept(Object obj, Object obj2) {
                ((ActivityTaskManagerService) obj)
                    .cancelHeavyWeightProcessNotification(((Integer) obj2).intValue());
              }
            },
            this,
            Integer.valueOf(windowProcessController.mUserId)));
  }

  public final void cancelHeavyWeightProcessNotification(int i) {
    INotificationManager service = NotificationManager.getService();
    if (service == null) {
      return;
    }
    try {
      service.cancelNotificationWithTag("android", "android", (String) null, 11, i);
    } catch (RemoteException unused) {
    } catch (RuntimeException e) {
      Slog.w("ActivityTaskManager", "Error canceling notification for service", e);
    }
  }

  public final void postHeavyWeightProcessNotification(
      WindowProcessController windowProcessController, Intent intent, int i) {
    INotificationManager service;
    if (windowProcessController == null || (service = NotificationManager.getService()) == null) {
      return;
    }
    try {
      Context createPackageContext =
          this.mContext.createPackageContext(windowProcessController.mInfo.packageName, 0);
      String string =
          this.mContext.getString(
              R.string.mediasize_na_arch_e1,
              createPackageContext
                  .getApplicationInfo()
                  .loadLabel(createPackageContext.getPackageManager()));
      try {
        service.enqueueNotificationWithTag(
            "android",
            "android",
            (String) null,
            11,
            new Notification.Builder(
                    createPackageContext, SystemNotificationChannels.HEAVY_WEIGHT_APP)
                .setSmallIcon(17304219)
                .setWhen(0L)
                .setOngoing(true)
                .setTicker(string)
                .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
                .setContentTitle(string)
                .setContentText(this.mContext.getText(R.string.mediasize_na_foolscap))
                .setContentIntent(
                    PendingIntent.getActivityAsUser(
                        this.mContext, 0, intent, 335544320, null, new UserHandle(i)))
                .build(),
            i);
      } catch (RemoteException unused) {
      } catch (RuntimeException e) {
        Slog.w("ActivityTaskManager", "Error showing notification for heavy-weight app", e);
      }
    } catch (PackageManager.NameNotFoundException e2) {
      Slog.w("ActivityTaskManager", "Unable to create context for heavy notification", e2);
    }
  }

  public IIntentSender getIntentSenderLocked(
      int i,
      String str,
      String str2,
      int i2,
      int i3,
      IBinder iBinder,
      String str3,
      int i4,
      Intent[] intentArr,
      String[] strArr,
      int i5,
      Bundle bundle) {
    ActivityRecord activityRecord;
    ActivityTaskManagerService activityTaskManagerService;
    if (i == 3) {
      ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
      if (isInRootTaskLocked == null) {
        Slog.w(
            "ActivityTaskManager",
            "Failed createPendingResult: activity " + iBinder + " not in any root task");
        return null;
      }
      if (isInRootTaskLocked.finishing) {
        Slog.w(
            "ActivityTaskManager",
            "Failed createPendingResult: activity " + isInRootTaskLocked + " is finishing");
        return null;
      }
      activityTaskManagerService = this;
      activityRecord = isInRootTaskLocked;
    } else {
      activityRecord = null;
      activityTaskManagerService = this;
    }
    PendingIntentRecord intentSender =
        activityTaskManagerService.mPendingIntentController.getIntentSender(
            i, str, str2, i2, i3, iBinder, str3, i4, intentArr, strArr, i5, bundle);
    if (!((i5 & 536870912) != 0) && i == 3) {
      if (activityRecord.pendingResults == null) {
        activityRecord.pendingResults = new HashSet();
      }
      activityRecord.pendingResults.add(intentSender.ref);
    }
    return intentSender;
  }

  public final void startTimeTrackingFocusedActivityLocked() {
    AppTimeTracker appTimeTracker;
    ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
    if (this.mSleeping
        || (appTimeTracker = this.mCurAppTimeTracker) == null
        || topResumedActivity == null) {
      return;
    }
    appTimeTracker.start(topResumedActivity.packageName);
  }

  public final void updateResumedAppTrace(ActivityRecord activityRecord) {
    if (Trace.isTagEnabled(32L)) {
      ActivityRecord activityRecord2 = this.mTracedResumedActivity;
      if (activityRecord2 != null) {
        Trace.asyncTraceForTrackEnd(32L, "Focused app", System.identityHashCode(activityRecord2));
      }
      if (activityRecord != null) {
        Trace.asyncTraceForTrackBegin(
            32L,
            "Focused app",
            activityRecord.mActivityComponent.flattenToShortString(),
            System.identityHashCode(activityRecord));
      }
    }
    this.mTracedResumedActivity = activityRecord;
  }

  public boolean ensureConfigAndVisibilityAfterUpdate(ActivityRecord activityRecord, int i) {
    Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
    if (topDisplayFocusedRootTask != null) {
      if (i != 0 && activityRecord == null) {
        activityRecord = topDisplayFocusedRootTask.topRunningActivity();
      }
      if (activityRecord != null) {
        boolean ensureActivityConfiguration = activityRecord.ensureActivityConfiguration(i, false);
        this.mRootWindowContainer.ensureActivitiesVisible(activityRecord, i, false);
        return ensureActivityConfiguration;
      }
    }
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleAppGcsLocked$14() {
    this.mAmInternal.scheduleAppGcs();
  }

  public void scheduleAppGcsLocked() {
    this.f1734mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda21
          @Override // java.lang.Runnable
          public final void run() {
            ActivityTaskManagerService.this.lambda$scheduleAppGcsLocked$14();
          }
        });
  }

  public CompatibilityInfo compatibilityInfoForPackageLocked(ApplicationInfo applicationInfo) {
    return this.mCompatModePackages.compatibilityInfoForPackageLocked(applicationInfo);
  }

  public IPackageManager getPackageManager() {
    return AppGlobals.getPackageManager();
  }

  public PackageManagerInternal getPackageManagerInternalLocked() {
    if (this.mPmInternal == null) {
      this.mPmInternal =
          (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    }
    return this.mPmInternal;
  }

  public ComponentName getSysUiServiceComponentLocked() {
    if (this.mSysUiServiceComponent == null) {
      this.mSysUiServiceComponent = getPackageManagerInternalLocked().getSystemUiServiceComponent();
    }
    return this.mSysUiServiceComponent;
  }

  public PermissionPolicyInternal getPermissionPolicyInternal() {
    if (this.mPermissionPolicyInternal == null) {
      this.mPermissionPolicyInternal =
          (PermissionPolicyInternal) LocalServices.getService(PermissionPolicyInternal.class);
    }
    return this.mPermissionPolicyInternal;
  }

  public StatusBarManagerInternal getStatusBarManagerInternal() {
    if (this.mStatusBarManagerInternal == null) {
      this.mStatusBarManagerInternal =
          (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
    }
    return this.mStatusBarManagerInternal;
  }

  public WallpaperManagerInternal getWallpaperManagerInternal() {
    if (this.mWallpaperManagerInternal == null) {
      this.mWallpaperManagerInternal =
          (WallpaperManagerInternal) LocalServices.getService(WallpaperManagerInternal.class);
    }
    return this.mWallpaperManagerInternal;
  }

  public AppWarnings getAppWarningsLocked() {
    return this.mAppWarnings;
  }

  public Intent getHomeIntent() {
    String str = this.mTopAction;
    String str2 = this.mTopData;
    Intent intent = new Intent(str, str2 != null ? Uri.parse(str2) : null);
    intent.setComponent(this.mTopComponent);
    intent.addFlags(256);
    if (this.mFactoryTest != 1) {
      intent.addCategory("android.intent.category.HOME");
    }
    return intent;
  }

  public Intent getSecondaryHomeIntent(String str) {
    String str2 = this.mTopAction;
    String str3 = this.mTopData;
    Intent intent = new Intent(str2, str3 != null ? Uri.parse(str3) : null);
    boolean z = this.mContext.getResources().getBoolean(R.bool.config_supportsCamToggle);
    if (str == null || z) {
      intent.setPackage(this.mContext.getResources().getString(R.string.ext_media_missing_title));
    } else {
      intent.setPackage(str);
    }
    intent.addFlags(256);
    if (this.mFactoryTest != 1) {
      intent.addCategory("android.intent.category.SECONDARY_HOME");
    }
    return intent;
  }

  public ApplicationInfo getAppInfoForUser(ApplicationInfo applicationInfo, int i) {
    if (applicationInfo == null) {
      return null;
    }
    ApplicationInfo applicationInfo2 = new ApplicationInfo(applicationInfo);
    applicationInfo2.initForUser(i);
    return applicationInfo2;
  }

  public WindowProcessController getProcessController(String str, int i) {
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

  public WindowProcessController getProcessController(IApplicationThread iApplicationThread) {
    if (iApplicationThread == null) {
      return null;
    }
    IBinder asBinder = iApplicationThread.asBinder();
    ArrayMap map = this.mProcessNames.getMap();
    for (int size = map.size() - 1; size >= 0; size--) {
      SparseArray sparseArray = (SparseArray) map.valueAt(size);
      for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
        WindowProcessController windowProcessController =
            (WindowProcessController) sparseArray.valueAt(size2);
        if (windowProcessController.hasThread()
            && windowProcessController.getThread().asBinder() == asBinder) {
          return windowProcessController;
        }
      }
    }
    return null;
  }

  public WindowProcessController getProcessController(int i, int i2) {
    WindowProcessController process = this.mProcessMap.getProcess(i);
    if (process != null && UserHandle.isApp(i2) && process.mUid == i2) {
      return process;
    }
    return null;
  }

  public String getPackageNameIfUnique(int i, int i2) {
    WindowProcessController process = this.mProcessMap.getProcess(i2);
    if (process == null || process.mUid != i) {
      Slog.w(
          "ActivityTaskManager", "callingPackage for (uid=" + i + ", pid=" + i2 + ") has no WPC");
      return null;
    }
    List packageList = process.getPackageList();
    if (packageList.size() != 1) {
      Slog.w(
          "ActivityTaskManager",
          "callingPackage for (uid=" + i + ", pid=" + i2 + ") is ambiguous: " + packageList);
      return null;
    }
    return (String) packageList.get(0);
  }

  public boolean hasActiveVisibleWindow(int i) {
    if (this.mVisibleActivityProcessTracker.hasVisibleActivity(i)) {
      return true;
    }
    return this.mActiveUids.hasNonAppVisibleWindow(i);
  }

  public boolean isDeviceOwner(int i) {
    return i >= 0 && this.mDeviceOwnerUid == i;
  }

  public void setDeviceOwnerUid(int i) {
    this.mDeviceOwnerUid = i;
  }

  public boolean isAffiliatedProfileOwner(int i) {
    return i >= 0
        && this.mProfileOwnerUids.contains(Integer.valueOf(i))
        && DeviceStateCache.getInstance().hasAffiliationWithDevice(UserHandle.getUserId(i));
  }

  public void setProfileOwnerUids(Set set) {
    this.mProfileOwnerUids = set;
  }

  public void saveANRState(String str) {
    StringWriter stringWriter = new StringWriter();
    FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 1024);
    fastPrintWriter.println("  ANR time: " + DateFormat.getDateTimeInstance().format(new Date()));
    if (str != null) {
      fastPrintWriter.println("  Reason: " + str);
    }
    fastPrintWriter.println();
    getActivityStartController().dump(fastPrintWriter, "  ", null);
    fastPrintWriter.println();
    fastPrintWriter.println(
        "-------------------------------------------------------------------------------");
    dumpActivitiesLocked(null, fastPrintWriter, null, 0, true, false, null, -1, "");
    fastPrintWriter.println();
    fastPrintWriter.close();
    this.mLastANRState = stringWriter.toString();
  }

  public boolean isAssociatedCompanionApp(int i, int i2) {
    Set set = (Set) this.mCompanionAppUidsMap.get(Integer.valueOf(i));
    if (set == null) {
      return false;
    }
    return set.contains(Integer.valueOf(i2));
  }

  public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
    try {
      return super.onTransact(i, parcel, parcel2, i2);
    } catch (RuntimeException e) {
      throw logAndRethrowRuntimeExceptionOnTransact("ActivityTaskManager", e);
    }
  }

  public static RuntimeException logAndRethrowRuntimeExceptionOnTransact(
      String str, RuntimeException runtimeException) {
    if (!(runtimeException instanceof SecurityException)) {
      Slog.w(
          "ActivityTaskManager",
          str
              + " onTransact aborts UID:"
              + Binder.getCallingUid()
              + " PID:"
              + Binder.getCallingPid(),
          runtimeException);
      throw runtimeException;
    }
    throw runtimeException;
  }

  public void onImeWindowSetOnDisplayArea(int i, DisplayArea displayArea) {
    if (i == WindowManagerService.MY_PID || i < 0) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION, -1810446914, 0, (String) null, (Object[]) null);
        return;
      }
      return;
    }
    WindowProcessController process = this.mProcessMap.getProcess(i);
    if (process == null) {
      if (ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_CONFIGURATION,
            -449118559,
            1,
            (String) null,
            new Object[] {Long.valueOf(i)});
        return;
      }
      return;
    }
    process.registerDisplayAreaConfigurationListener(displayArea);
  }

  public void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) {
    TransitionController transitionController = getTransitionController();
    if (iApplicationThread == null
        || !transitionController.mRemotePlayer.reportRunning(iApplicationThread)) {
      this.mAmInternal.enforceCallingPermission(
          "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
          "setRunningRemoteTransition");
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          WindowProcessController processController = getProcessController(callingPid, callingUid);
          if (processController == null || !processController.isRunningRemoteTransition()) {
            String str =
                "Can't call setRunningRemoteTransition from a process (pid="
                    + callingPid
                    + " uid="
                    + callingUid
                    + ") which isn't itself running a remote transition.";
            Slog.e("ActivityTaskManager", str);
            throw new SecurityException(str);
          }
          WindowProcessController processController2 = getProcessController(iApplicationThread);
          if (processController2 == null) {
            Slog.w(
                "ActivityTaskManager",
                "setRunningRemoteTransition: no process for " + iApplicationThread);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          transitionController.mRemotePlayer.update(processController2, true, false);
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }
  }

  public void registerScreenCaptureObserver(
      IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.DETECT_SCREEN_CAPTURE", "registerScreenCaptureObserver");
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

  public void unregisterScreenCaptureObserver(
      IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) {
    this.mAmInternal.enforceCallingPermission(
        "android.permission.DETECT_SCREEN_CAPTURE", "unregisterScreenCaptureObserver");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked != null) {
          forTokenLocked.unregisterCaptureObserver(iScreenCaptureObserver);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean instrumentationSourceHasPermission(int i, String str) {
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
    return process != null
        && process.isInstrumenting()
        && checkPermission(str, -1, process.getInstrumentationSourceUid()) == 0;
  }

  public final SafeActivityOptions createSafeActivityOptionsWithBalAllowed(
      ActivityOptions activityOptions) {
    if (activityOptions == null) {
      activityOptions = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
    } else if (activityOptions.getPendingIntentBackgroundActivityStartMode() == 0) {
      activityOptions.setPendingIntentBackgroundActivityStartMode(1);
    }
    return new SafeActivityOptions(activityOptions);
  }

  public final SafeActivityOptions createSafeActivityOptionsWithBalAllowed(Bundle bundle) {
    return createSafeActivityOptionsWithBalAllowed(ActivityOptions.fromBundle(bundle));
  }

  public int getInitProcessDisplayId(WindowProcessController windowProcessController) {
    int initProcessDisplayId;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        initProcessDisplayId = this.mDexController.getInitProcessDisplayId(windowProcessController);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return initProcessDisplayId;
  }

  /* renamed from: com.android.server.wm.ActivityTaskManagerService$H */
  public final class HandlerC2878H extends Handler {
    public HandlerC2878H(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        ((AppTimeTracker) message.obj).deliverResult(ActivityTaskManagerService.this.mContext);
        return;
      }
      if (i == 2) {
        WindowProcessController windowProcessController = (WindowProcessController) message.obj;
        WindowManagerGlobalLock windowManagerGlobalLock =
            ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            windowProcessController.updateRunningRemoteOrRecentsAnimation();
          } finally {
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return;
      }
      if (i == 3) {
        WindowManagerGlobalLock windowManagerGlobalLock2 =
            ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock2) {
          try {
            ActivityTaskManagerService.this.mRetainPowerModeAndTopProcessState = false;
            ActivityTaskManagerService.this.endLaunchPowerMode(4);
            if (ActivityTaskManagerService.this.mTopApp != null
                && ActivityTaskManagerService.this.mTopProcessState == 12) {
              ActivityTaskManagerService.this.mTopApp.updateProcessInfo(false, false, true, false);
            }
          } finally {
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return;
      }
      if (i != 4) {
        if (i != 5) {
          return;
        }
        ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
        Context context = activityTaskManagerService.mContext;
        Toast.makeText(
                context,
                context.getString(activityTaskManagerService.mOverheatTextId, (String) message.obj),
                0)
            .show();
        return;
      }
      WindowManagerGlobalLock windowManagerGlobalLock3 =
          ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock3) {
        try {
          if (ActivityTaskManagerService.this.mAppSwitchesState == 0) {
            ActivityTaskManagerService.this.mAppSwitchesState = 1;
          }
        } finally {
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }

  public PersonaActivityHelper createPersonaActivityHelper() {
    return new PersonaActivityHelper(this, this.f1734mH.getLooper(), this.mRootWindowContainer);
  }

  public PersonaActivityHelper getPersonaActivityHelper() {
    return this.mPersonaActivityHelper;
  }

  public final class UiHandler extends Handler {
    public UiHandler() {
      super(UiThread.get().getLooper(), null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      if (message.what != 1) {
        return;
      }
      ((Dialog) message.obj).dismiss();
    }
  }

  public final class LocalService extends ActivityTaskManagerInternal {
    public LocalService() {}

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ActivityTaskManagerInternal.SleepTokenAcquirer createSleepTokenAcquirer(String str) {
      Objects.requireNonNull(str);
      return ActivityTaskManagerService.this.new SleepTokenAcquirerImpl(str);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ComponentName getHomeActivityForUser(int i) {
      ComponentName componentName;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord defaultDisplayHomeActivityForUser =
              ActivityTaskManagerService.this.mRootWindowContainer
                  .getDefaultDisplayHomeActivityForUser(i);
          componentName =
              defaultDisplayHomeActivityForUser == null
                  ? null
                  : defaultDisplayHomeActivityForUser.mActivityComponent;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return componentName;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onLocalVoiceInteractionStarted(
        IBinder iBinder,
        IVoiceInteractionSession iVoiceInteractionSession,
        IVoiceInteractor iVoiceInteractor) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.onLocalVoiceInteractionStartedLocked(
              iBinder, iVoiceInteractionSession, iVoiceInteractor);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public List getTopVisibleActivities() {
      List topVisibleActivities;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          topVisibleActivities =
              ActivityTaskManagerService.this.mRootWindowContainer.getTopVisibleActivities();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return topVisibleActivities;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean hasResumedActivity(int i) {
      return ActivityTaskManagerService.this.mVisibleActivityProcessTracker.hasResumedActivity(i);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setBackgroundActivityStartCallback(
        BackgroundActivityStartCallback backgroundActivityStartCallback) {
      ActivityTaskManagerService.this.mBackgroundActivityStartCallback =
          backgroundActivityStartCallback;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setAccessibilityServiceUids(IntArray intArray) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mAccessibilityServiceUids = intArray.toArray();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int startActivitiesAsPackage(
        String str, String str2, int i, Intent[] intentArr, Bundle bundle) {
      Objects.requireNonNull(intentArr, "intents");
      String[] strArr = new String[intentArr.length];
      long clearCallingIdentity = Binder.clearCallingIdentity();
      int i2 = 0;
      for (int i3 = 0; i3 < intentArr.length; i3++) {
        try {
          try {
            strArr[i3] =
                intentArr[i3].resolveTypeIfNeeded(
                    ActivityTaskManagerService.this.mContext.getContentResolver());
          } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
          }
        } catch (RemoteException unused) {
        }
      }
      try {
        i2 = AppGlobals.getPackageManager().getPackageUid(str, 268435456L, i);
      } catch (RemoteException unused2) {
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return ActivityTaskManagerService.this
          .getActivityStartController()
          .startActivitiesInPackage(
              i2,
              str,
              str2,
              intentArr,
              strArr,
              null,
              SafeActivityOptions.fromBundle(bundle),
              i,
              false,
              null,
              BackgroundStartPrivileges.NONE);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int startActivitiesInPackage(
        int i,
        int i2,
        int i3,
        String str,
        String str2,
        Intent[] intentArr,
        String[] strArr,
        IBinder iBinder,
        SafeActivityOptions safeActivityOptions,
        int i4,
        boolean z,
        PendingIntentRecord pendingIntentRecord,
        BackgroundStartPrivileges backgroundStartPrivileges) {
      ActivityTaskManagerService.this.assertPackageMatchesCallingUid(str);
      return ActivityTaskManagerService.this
          .getActivityStartController()
          .startActivitiesInPackage(
              i,
              i2,
              i3,
              str,
              str2,
              intentArr,
              strArr,
              iBinder,
              safeActivityOptions,
              i4,
              z,
              pendingIntentRecord,
              backgroundStartPrivileges);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int startActivityInPackage(
        int i,
        int i2,
        int i3,
        String str,
        String str2,
        Intent intent,
        String str3,
        IBinder iBinder,
        String str4,
        int i4,
        int i5,
        SafeActivityOptions safeActivityOptions,
        int i6,
        Task task,
        String str5,
        boolean z,
        PendingIntentRecord pendingIntentRecord,
        BackgroundStartPrivileges backgroundStartPrivileges) {
      ActivityTaskManagerService.this.assertPackageMatchesCallingUid(str);
      return ActivityTaskManagerService.this
          .getActivityStartController()
          .startActivityInPackage(
              i,
              i2,
              i3,
              str,
              str2,
              intent,
              str3,
              iBinder,
              str4,
              i4,
              i5,
              safeActivityOptions,
              i6,
              task,
              str5,
              z,
              pendingIntentRecord,
              backgroundStartPrivileges);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int startActivityAsUser(
        IApplicationThread iApplicationThread,
        String str,
        String str2,
        Intent intent,
        IBinder iBinder,
        int i,
        Bundle bundle,
        int i2) {
      ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
      return activityTaskManagerService.startActivityAsUser(
          iApplicationThread,
          str,
          str2,
          intent,
          intent.resolveTypeIfNeeded(activityTaskManagerService.mContext.getContentResolver()),
          iBinder,
          null,
          0,
          i,
          null,
          bundle,
          i2,
          false);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int startActivityWithScreenshot(
        Intent intent, String str, int i, int i2, IBinder iBinder, Bundle bundle, int i3) {
      return ActivityTaskManagerService.this
          .getActivityStartController()
          .obtainStarter(intent, "startActivityWithScreenshot")
          .setCallingUid(i)
          .setCallingPid(i2)
          .setCallingPackage(str)
          .setResultTo(iBinder)
          .setActivityOptions(
              ActivityTaskManagerService.this.createSafeActivityOptionsWithBalAllowed(bundle))
          .setRealCallingUid(Binder.getCallingUid())
          .setUserId(i3)
          .setBackgroundStartPrivileges(BackgroundStartPrivileges.ALLOW_BAL)
          .setFreezeScreen(true)
          .execute();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setVr2dDisplayId(int i) {
      if (ProtoLogCache.WM_DEBUG_TASKS_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_TASKS,
            -1679411993,
            1,
            (String) null,
            new Object[] {Long.valueOf(i)});
      }
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mVr2dDisplayId = i;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int getDisplayId(IBinder iBinder) {
      int displayId;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
          if (forTokenLocked == null) {
            throw new IllegalArgumentException(
                "getDisplayId: No activity record matching token=" + iBinder);
          }
          displayId = forTokenLocked.getDisplayId();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return displayId;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void registerScreenObserver(ActivityTaskManagerInternal.ScreenObserver screenObserver) {
      ActivityTaskManagerService.this.mScreenObservers.add(screenObserver);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void unregisterScreenObserver(
        ActivityTaskManagerInternal.ScreenObserver screenObserver) {
      ActivityTaskManagerService.this.mScreenObservers.remove(screenObserver);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isCallerRecents(int i) {
      return ActivityTaskManagerService.this.isCallerRecents(i);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean checkCanCloseSystemDialogs(int i, int i2, String str) {
      return ActivityTaskManagerService.this.checkCanCloseSystemDialogs(i, i2, str);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean canCloseSystemDialogs(int i, int i2) {
      return ActivityTaskManagerService.this.canCloseSystemDialogs(i, i2);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void notifyActiveVoiceInteractionServiceChanged(ComponentName componentName) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mActiveVoiceInteractionServiceComponent = componentName;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void notifyActiveDreamChanged(ComponentName componentName) {
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
    public void setAllowAppSwitches(String str, int i, int i2) {
      if (ActivityTaskManagerService.this.mAmInternal.isUserRunning(i2, 1)) {
        WindowManagerGlobalLock windowManagerGlobalLock =
            ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            ArrayMap arrayMap =
                (ArrayMap) ActivityTaskManagerService.this.mAllowAppSwitchUids.get(i2);
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
    public void onUserStopped(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.getRecentTasks().unloadUserDataFromMemoryLocked(i);
          ActivityTaskManagerService.this.mAllowAppSwitchUids.remove(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isGetTasksAllowed(String str, int i, int i2) {
      return ActivityTaskManagerService.this.isGetTasksAllowed(str, i, i2);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onProcessAdded(WindowProcessController windowProcessController) {
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        ActivityTaskManagerService.this.mProcessNames.put(
            windowProcessController.mName, windowProcessController.mUid, windowProcessController);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onProcessRemoved(String str, int i) {
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        ActivityTaskManagerService.this.mProcessNames.remove(str, i);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onCleanUpApplicationRecord(WindowProcessController windowProcessController) {
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        if (windowProcessController == ActivityTaskManagerService.this.mHomeProcess) {
          ActivityTaskManagerService.this.mHomeProcess = null;
        }
        if (windowProcessController == ActivityTaskManagerService.this.mPreviousProcess) {
          ActivityTaskManagerService.this.mPreviousProcess = null;
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int getTopProcessState() {
      if (ActivityTaskManagerService.this.mRetainPowerModeAndTopProcessState) {
        return 2;
      }
      return ActivityTaskManagerService.this.mTopProcessState;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean useTopSchedGroupForTopProcess() {
      return ActivityTaskManagerService.this.mDemoteTopAppReasons == 0;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void clearHeavyWeightProcessIfEquals(WindowProcessController windowProcessController) {
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        ActivityTaskManagerService.this.clearHeavyWeightProcessIfEquals(windowProcessController);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void finishHeavyWeightApp() {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (ActivityTaskManagerService.this.mHeavyWeightProcess != null) {
            ActivityTaskManagerService.this.mHeavyWeightProcess.finishActivities();
          }
          ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
          activityTaskManagerService.clearHeavyWeightProcessIfEquals(
              activityTaskManagerService.mHeavyWeightProcess);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isSleeping() {
      return ActivityTaskManagerService.this.mSleeping;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isShuttingDown() {
      boolean z;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          z = ActivityTaskManagerService.this.mShuttingDown;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return z;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean shuttingDown(boolean z, int i) {
      boolean shutdownLocked;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
          activityTaskManagerService.mShuttingDown = true;
          activityTaskManagerService.mRootWindowContainer.prepareForShutdown();
          ActivityTaskManagerService.this.updateEventDispatchingLocked(z);
          ActivityTaskManagerService.this.notifyTaskPersisterLocked(null, true);
          shutdownLocked = ActivityTaskManagerService.this.mTaskSupervisor.shutdownLocked(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return shutdownLocked;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void enableScreenAfterBoot(boolean z) {
      EventLogTags.writeBootProgressEnableScreen(SystemClock.uptimeMillis());
      ActivityTaskManagerService.this.mWindowManager.enableScreenAfterBoot();
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.updateEventDispatchingLocked(z);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      FgThread.getHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  ActivityTaskManagerService.LocalService.this.lambda$enableScreenAfterBoot$0();
                }
              });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enableScreenAfterBoot$0() {
      ActivityTaskManagerService.this.mMultiTaskingController.enableScreenAfterBoot();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean showStrictModeViolationDialog() {
      boolean z;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          z =
              (!ActivityTaskManagerService.this.mShowDialogs
                      || ActivityTaskManagerService.this.mSleeping
                      || ActivityTaskManagerService.this.mShuttingDown)
                  ? false
                  : true;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return z;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void showSystemReadyErrorDialogsIfNeeded() {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          try {
            if (AppGlobals.getPackageManager().hasSystemUidErrors()) {
              Slog.e(
                  "ActivityTaskManager",
                  "UIDs on the system are inconsistent, you need to wipe your data partition or"
                      + " your device will be unstable.");
              ActivityTaskManagerService.this.mUiHandler.post(
                  new Runnable() { // from class:
                                   // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                      ActivityTaskManagerService.LocalService.this
                          .lambda$showSystemReadyErrorDialogsIfNeeded$1();
                    }
                  });
            }
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        } catch (RemoteException unused) {
        }
        if (!Build.isBuildConsistent()) {
          Slog.e("ActivityTaskManager", "Build fingerprint is not consistent, warning user");
          ActivityTaskManagerService.this.mUiHandler.post(
              new Runnable() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                  ActivityTaskManagerService.LocalService.this
                      .lambda$showSystemReadyErrorDialogsIfNeeded$2();
                }
              });
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSystemReadyErrorDialogsIfNeeded$1() {
      if (ActivityTaskManagerService.this.mShowDialogs) {
        BaseErrorDialog baseErrorDialog =
            new BaseErrorDialog(ActivityTaskManagerService.this.mUiContext);
        baseErrorDialog.getWindow().setType(2010);
        baseErrorDialog.setCancelable(false);
        baseErrorDialog.setTitle(
            ActivityTaskManagerService.this.mUiContext.getText(
                R.string.call_notification_hang_up_action));
        baseErrorDialog.setMessage(ActivityTaskManagerService.this.mUiContext.getText(17042985));
        baseErrorDialog.setButton(
            -1,
            ActivityTaskManagerService.this.mUiContext.getText(R.string.ok),
            ActivityTaskManagerService.this.mUiHandler.obtainMessage(1, baseErrorDialog));
        baseErrorDialog.show();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSystemReadyErrorDialogsIfNeeded$2() {
      if (ActivityTaskManagerService.this.mShowDialogs) {
        BaseErrorDialog baseErrorDialog =
            new BaseErrorDialog(ActivityTaskManagerService.this.mUiContext);
        baseErrorDialog.getWindow().setType(2010);
        baseErrorDialog.setCancelable(false);
        baseErrorDialog.setTitle(
            ActivityTaskManagerService.this.mUiContext.getText(
                R.string.call_notification_hang_up_action));
        baseErrorDialog.setMessage(ActivityTaskManagerService.this.mUiContext.getText(17042984));
        baseErrorDialog.setButton(
            -1,
            ActivityTaskManagerService.this.mUiContext.getText(R.string.ok),
            ActivityTaskManagerService.this.mUiHandler.obtainMessage(1, baseErrorDialog));
        baseErrorDialog.show();
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onProcessMapped(int i, WindowProcessController windowProcessController) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mProcessMap.put(i, windowProcessController);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onProcessUnMapped(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mProcessMap.remove(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onPackageDataCleared(String str, int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mCompatModePackages.handlePackageDataClearedLocked(str);
          ActivityTaskManagerService.this.mAppWarnings.onPackageDataCleared(str);
          ActivityTaskManagerService.this.mPackageConfigPersister.onPackageDataCleared(str, i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onPackageUninstalled(String str, int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mAppWarnings.onPackageUninstalled(str);
          ActivityTaskManagerService.this.mCompatModePackages.handlePackageUninstalledLocked(str);
          ActivityTaskManagerService.this.mPackageConfigPersister.onPackageUninstall(str, i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onPackageAdded(String str, boolean z) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mCompatModePackages.handlePackageAddedLocked(str, z);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onPackageReplaced(ApplicationInfo applicationInfo) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRootWindowContainer.updateActivityApplicationInfo(
              applicationInfo);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public CompatibilityInfo compatibilityInfoForPackage(ApplicationInfo applicationInfo) {
      CompatibilityInfo compatibilityInfoForPackageLocked;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          compatibilityInfoForPackageLocked =
              ActivityTaskManagerService.this.compatibilityInfoForPackageLocked(applicationInfo);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return compatibilityInfoForPackageLocked;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void sendActivityResult(
        int i, IBinder iBinder, String str, int i2, int i3, Intent intent) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
          if (isInRootTaskLocked != null && isInRootTaskLocked.getRootTask() != null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            NeededUriGrants collectGrants =
                ActivityTaskManagerService.this.collectGrants(intent, isInRootTaskLocked);
            WindowManagerGlobalLock windowManagerGlobalLock2 =
                ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
              try {
                isInRootTaskLocked.sendResult(i, str, i2, i3, intent, collectGrants);
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
    public void clearPendingResultForActivity(IBinder iBinder, WeakReference weakReference) {
      HashSet hashSet;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
          if (isInRootTaskLocked != null && (hashSet = isInRootTaskLocked.pendingResults) != null) {
            hashSet.remove(weakReference);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ComponentName getActivityName(IBinder iBinder) {
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
    public ActivityTaskManagerInternal.ActivityTokens getAttachedNonFinishingActivityForTask(
        int i, IBinder iBinder) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId =
              ActivityTaskManagerService.this.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w(
                "ActivityTaskManager",
                "getApplicationThreadForTopActivity failed: Requested task not found");
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
          final ArrayList arrayList = new ArrayList();
          anyTaskForId.forAllActivities(
              new Consumer() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ActivityTaskManagerService.LocalService
                      .lambda$getAttachedNonFinishingActivityForTask$3(
                          arrayList, (ActivityRecord) obj);
                }
              });
          if (arrayList.size() <= 0) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return null;
          }
          if (iBinder == null && ((ActivityRecord) arrayList.get(0)).attachedToProcess()) {
            ActivityRecord activityRecord = (ActivityRecord) arrayList.get(0);
            ActivityTaskManagerInternal.ActivityTokens activityTokens =
                new ActivityTaskManagerInternal.ActivityTokens(
                    activityRecord.token,
                    activityRecord.assistToken,
                    activityRecord.app.getThread(),
                    activityRecord.shareableActivityToken,
                    activityRecord.getUid());
            WindowManagerService.resetPriorityAfterLockedSection();
            return activityTokens;
          }
          for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ActivityRecord activityRecord2 = (ActivityRecord) arrayList.get(i2);
            if (activityRecord2.shareableActivityToken == iBinder
                && activityRecord2.attachedToProcess()) {
              ActivityTaskManagerInternal.ActivityTokens activityTokens2 =
                  new ActivityTaskManagerInternal.ActivityTokens(
                      activityRecord2.token,
                      activityRecord2.assistToken,
                      activityRecord2.app.getThread(),
                      activityRecord2.shareableActivityToken,
                      activityRecord2.getUid());
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

    public static /* synthetic */ void lambda$getAttachedNonFinishingActivityForTask$3(
        List list, ActivityRecord activityRecord) {
      if (activityRecord.finishing) {
        return;
      }
      list.add(activityRecord);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public IIntentSender getIntentSender(
        int i,
        String str,
        String str2,
        int i2,
        int i3,
        IBinder iBinder,
        String str3,
        int i4,
        Intent[] intentArr,
        String[] strArr,
        int i5,
        Bundle bundle) {
      IIntentSender intentSenderLocked;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          intentSenderLocked =
              ActivityTaskManagerService.this.getIntentSenderLocked(
                  i, str, str2, i2, i3, iBinder, str3, i4, intentArr, strArr, i5, bundle);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return intentSenderLocked;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ActivityServiceConnectionsHolder getServiceConnectionsHolder(IBinder iBinder) {
      ActivityRecord forToken = ActivityRecord.forToken(iBinder);
      if (forToken == null || !forToken.inHistory) {
        return null;
      }
      return forToken.getOrCreateServiceConnectionsHolder();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public Intent getHomeIntent() {
      Intent homeIntent;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          homeIntent = ActivityTaskManagerService.this.getHomeIntent();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return homeIntent;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean startHomeActivity(int i, String str) {
      boolean startHomeOnDisplay;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          startHomeOnDisplay =
              ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnDisplay(i, str, 0);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return startHomeOnDisplay;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean startHomeOnDisplay(int i, String str, int i2, boolean z, boolean z2) {
      boolean startHomeOnDisplay;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          startHomeOnDisplay =
              ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnDisplay(
                  i, str, i2, z, z2);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return startHomeOnDisplay;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean startHomeOnAllDisplays(int i, String str) {
      boolean startHomeOnAllDisplays;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          startHomeOnAllDisplays =
              ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnAllDisplays(i, str);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return startHomeOnAllDisplays;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void updateTopComponentForFactoryTest() {
      final CharSequence text;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
          if (activityTaskManagerService.mFactoryTest != 1) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          ResolveInfo resolveActivity =
              activityTaskManagerService
                  .mContext
                  .getPackageManager()
                  .resolveActivity(new Intent("android.intent.action.FACTORY_TEST"), 1024);
          if (resolveActivity != null) {
            ActivityInfo activityInfo = resolveActivity.activityInfo;
            ApplicationInfo applicationInfo = activityInfo.applicationInfo;
            if ((1 & applicationInfo.flags) != 0) {
              ActivityTaskManagerService activityTaskManagerService2 =
                  ActivityTaskManagerService.this;
              activityTaskManagerService2.mTopAction = "android.intent.action.FACTORY_TEST";
              activityTaskManagerService2.mTopData = null;
              activityTaskManagerService2.mTopComponent =
                  new ComponentName(applicationInfo.packageName, activityInfo.name);
              text = null;
            } else {
              text =
                  ActivityTaskManagerService.this
                      .mContext
                      .getResources()
                      .getText(R.string.kg_login_username_hint);
            }
          } else {
            text =
                ActivityTaskManagerService.this
                    .mContext
                    .getResources()
                    .getText(R.string.kg_login_too_many_attempts);
          }
          if (text == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          ActivityTaskManagerService activityTaskManagerService3 = ActivityTaskManagerService.this;
          activityTaskManagerService3.mTopAction = null;
          activityTaskManagerService3.mTopData = null;
          activityTaskManagerService3.mTopComponent = null;
          activityTaskManagerService3.mUiHandler.post(
              new Runnable() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                  ActivityTaskManagerService.LocalService.this
                      .lambda$updateTopComponentForFactoryTest$4(text);
                }
              });
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTopComponentForFactoryTest$4(
        CharSequence charSequence) {
      new FactoryErrorDialog(ActivityTaskManagerService.this.mUiContext, charSequence).show();
      ActivityTaskManagerService.this.mAmInternal.ensureBootCompleted();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void handleAppDied(
        WindowProcessController windowProcessController, boolean z, Runnable runnable) {
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        ActivityTaskManagerService.this.mTaskSupervisor.beginDeferResume();
        try {
          boolean handleAppDied = windowProcessController.handleAppDied();
          if (!z && handleAppDied) {
            ActivityTaskManagerService.this.deferWindowLayout();
            try {
              if (!ActivityTaskManagerService.this.mRootWindowContainer
                  .resumeFocusedTasksTopActivities()) {
                ActivityTaskManagerService.this.mRootWindowContainer.ensureActivitiesVisible(
                    null, 0, false);
              }
              ActivityTaskManagerService.this.continueWindowLayout();
            } catch (Throwable th) {
              ActivityTaskManagerService.this.continueWindowLayout();
              throw th;
            }
          }
        } finally {
          ActivityTaskManagerService.this.mTaskSupervisor.endDeferResume();
        }
      }
      if (windowProcessController.isInstrumenting()) {
        runnable.run();
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void closeSystemDialogs(String str) {
      ActivityTaskManagerService.enforceNotIsolatedCaller("closeSystemDialogs");
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      if (checkCanCloseSystemDialogs(callingPid, callingUid, null)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          WindowManagerGlobalLock windowManagerGlobalLock =
              ActivityTaskManagerService.this.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock) {
            if (callingUid >= 10000) {
              try {
                WindowProcessController process =
                    ActivityTaskManagerService.this.mProcessMap.getProcess(callingPid);
                if (!process.isPerceptible()) {
                  Slog.w(
                      "ActivityTaskManager",
                      "Ignoring closeSystemDialogs " + str + " from background process " + process);
                  WindowManagerService.resetPriorityAfterLockedSection();
                  return;
                }
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            ActivityTaskManagerService.this.mWindowManager.closeSystemDialogs(str);
            ActivityTaskManagerService.this.mRootWindowContainer.closeSystemDialogActivities(str);
            WindowManagerService.resetPriorityAfterLockedSection();
            ActivityTaskManagerService.this.mAmInternal.broadcastCloseSystemDialogs(str);
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void closeSystemDialogs(String str, int i) {
      ActivityTaskManagerService.enforceNotIsolatedCaller("closeSystemDialogs");
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      if (checkCanCloseSystemDialogs(callingPid, callingUid, null)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          WindowManagerGlobalLock windowManagerGlobalLock =
              ActivityTaskManagerService.this.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock) {
            if (callingUid >= 10000) {
              try {
                WindowProcessController process =
                    ActivityTaskManagerService.this.mProcessMap.getProcess(callingPid);
                if (!process.isPerceptible() && !process.hasVisibleActivities()) {
                  Slog.w(
                      "ActivityTaskManager",
                      "Ignoring closeSystemDialogs " + str + " from background process " + process);
                  WindowManagerService.resetPriorityAfterLockedSection();
                  return;
                }
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            ActivityTaskManagerService.this.mWindowManager.closeSystemDialogsInDisplay(str, i);
            ActivityTaskManagerService.this.mRootWindowContainer.closeSystemDialogActivities(
                str, i);
            WindowManagerService.resetPriorityAfterLockedSection();
            ActivityTaskManagerService.this.mAmInternal.broadcastCloseSystemDialogs(str, i);
          }
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void cleanupDisabledPackageComponents(String str, Set set, int i, boolean z) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          RootWindowContainer rootWindowContainer =
              ActivityTaskManagerService.this.mRootWindowContainer;
          if (rootWindowContainer == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          if (rootWindowContainer.finishDisabledPackageActivities(str, set, true, false, i, false)
              && z) {
            ActivityTaskManagerService.this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            ActivityTaskManagerService.this.mTaskSupervisor.scheduleIdle();
          }
          ActivityTaskManagerService.this
              .getRecentTasks()
              .cleanupDisabledPackageTasksLocked(str, set, i);
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean onForceStopPackage(String str, boolean z, boolean z2, int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          RootWindowContainer rootWindowContainer =
              ActivityTaskManagerService.this.mRootWindowContainer;
          if (rootWindowContainer == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return false;
          }
          boolean finishDisabledPackageActivities =
              rootWindowContainer.finishDisabledPackageActivities(str, null, z, z2, i, true);
          WindowManagerService.resetPriorityAfterLockedSection();
          return finishDisabledPackageActivities;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void resumeTopActivities(boolean z) {
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
    public void preBindApplication(WindowProcessController windowProcessController) {
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        ActivityTaskManagerService.this
            .mTaskSupervisor
            .getActivityMetricsLogger()
            .notifyBindApplication(windowProcessController.mInfo);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean attachApplication(final WindowProcessController windowProcessController) {
      boolean attachApplication;
      synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
        if (Trace.isTagEnabled(32L)) {
          Trace.traceBegin(32L, "attachApplication:" + windowProcessController.mName);
        }
        try {
          attachApplication =
              ActivityTaskManagerService.this.mRootWindowContainer.attachApplication(
                  windowProcessController);
        } finally {
          ActivityTaskManagerService.this.f1734mH.post(
              new Runnable() { // from class:
                               // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  ActivityTaskManagerService.LocalService.this.lambda$attachApplication$5(
                      windowProcessController);
                }
              });
          Trace.traceEnd(32L);
        }
      }
      return attachApplication;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachApplication$5(
        WindowProcessController windowProcessController) {
      boolean z;
      synchronized (ActivityTaskManagerService.this.mIdsLock) {
        Set idsClearSet = ActivityTaskManagerService.this.getIdsClearSet();
        synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
          Integer valueOf = Integer.valueOf(windowProcessController.mUid);
          if (idsClearSet != null
              && idsClearSet.contains(valueOf)
              && windowProcessController.hasThread()) {
            try {
              z = true;
              windowProcessController.getThread().clearIdsTrainingData(true);
              idsClearSet.remove(valueOf);
            } catch (RemoteException unused) {
              Log.e("ActivityTaskManager", "Failed to clear Ids Training Data");
            }
          }
          z = false;
        }
        if (z) {
          try {
            ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream("/data/system/idsFile.txt"));
            try {
              objectOutputStream.writeObject(idsClearSet);
              objectOutputStream.close();
            } catch (Throwable th) {
              try {
                objectOutputStream.close();
              } catch (Throwable th2) {
                th.addSuppressed(th2);
              }
              throw th;
            }
          } catch (IOException unused2) {
            Slog.w("ActivityTaskManager", "Error writing IDS file during bindApplication.");
          }
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void notifyLockedProfile(int i) {
      try {
        if (!AppGlobals.getPackageManager().isUidPrivileged(Binder.getCallingUid())) {
          throw new SecurityException("Only privileged app can call notifyLockedProfile");
        }
        WindowManagerGlobalLock windowManagerGlobalLock =
            ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
              if (ActivityTaskManagerService.this.mAmInternal.shouldConfirmCredentials(i)) {
                ActivityTaskManagerService.this.maybeHideLockedProfileActivityLocked();
                ActivityTaskManagerService.this.mRootWindowContainer.lockAllProfileTasks(i);
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
      } catch (RemoteException e) {
        throw new SecurityException("Fail to check is caller a privileged app", e);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) {
      ActivityTaskManagerService.enforceTaskPermission("startConfirmDeviceCredentialIntent");
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            intent.addFlags(276824064);
            ActivityTaskManagerService.this.mContext.startActivityAsUser(
                intent,
                (bundle != null ? new ActivityOptions(bundle) : ActivityOptions.makeBasic())
                    .toBundle(),
                UserHandle.CURRENT);
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

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void writeActivitiesToProto(ProtoOutputStream protoOutputStream) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRootWindowContainer.dumpDebug(
              protoOutputStream, 1146756268034L, 0);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void dump(
        String str,
        FileDescriptor fileDescriptor,
        PrintWriter printWriter,
        String[] strArr,
        int i,
        boolean z,
        boolean z2,
        String str2,
        int i2) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (!"activities".equals(str) && !"a".equals(str)) {
            if ("lastanr".equals(str)) {
              ActivityTaskManagerService.this.dumpLastANRLocked(printWriter);
            } else if ("lastanr-traces".equals(str)) {
              ActivityTaskManagerService.this.dumpLastANRTracesLocked(printWriter);
            } else if ("starter".equals(str)) {
              ActivityTaskManagerService.this.dumpActivityStarterLocked(printWriter, str2);
            } else if ("containers".equals(str)) {
              ActivityTaskManagerService.this.dumpActivityContainersLocked(printWriter);
            } else {
              if (!"recents".equals(str) && !"r".equals(str)) {
                if ("top-resumed".equals(str)) {
                  ActivityTaskManagerService.this.dumpTopResumedActivityLocked(printWriter);
                } else if ("visible".equals(str)) {
                  ActivityTaskManagerService.this.dumpVisibleActivitiesLocked(printWriter, i2);
                } else if (ActivityTaskManagerService.isMultiTaskingDumpsysCommand(str)) {
                  printWriter.println("MULTI TASKING DUMPSYS (dumpsys activity multitasking)");
                  Iterator it = ActivityTaskManagerService.this.mMWControllers.iterator();
                  while (it.hasNext()) {
                    ((IController) it.next()).dumpLocked(printWriter, "  ");
                  }
                } else if (CoreRune.FW_APPLOCK) {
                  printWriter.println();
                  if (z) {
                    printWriter.println(
                        "-------------------------------------------------------------------------------");
                  }
                  AppLockPolicy appLockPolicy = ActivityTaskManagerService.this.mAppLockPolicy;
                  if (appLockPolicy != null) {
                    appLockPolicy.dumpAppLockPolicyLocked(fileDescriptor, printWriter);
                  }
                }
              }
              if (ActivityTaskManagerService.this.getRecentTasks() != null) {
                ActivityTaskManagerService.this.getRecentTasks().dump(printWriter, z, str2);
              }
            }
          }
          ActivityTaskManagerService.this.dumpActivitiesLocked(
              fileDescriptor, printWriter, strArr, i, z, z2, str2, i2);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean dumpForProcesses(
        FileDescriptor fileDescriptor,
        PrintWriter printWriter,
        boolean z,
        String str,
        int i,
        boolean z2,
        boolean z3,
        int i2) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (ActivityTaskManagerService.this.mHomeProcess != null
              && (str == null
                  || ActivityTaskManagerService.this.mHomeProcess.containsPackage(str))) {
            if (z2) {
              printWriter.println();
              z2 = false;
            }
            printWriter.println("  mHomeProcess: " + ActivityTaskManagerService.this.mHomeProcess);
          }
          if (ActivityTaskManagerService.this.mPreviousProcess != null
              && (str == null
                  || ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
            if (z2) {
              printWriter.println();
              z2 = false;
            }
            printWriter.println(
                "  mPreviousProcess: " + ActivityTaskManagerService.this.mPreviousProcess);
          }
          if (z
              && (ActivityTaskManagerService.this.mPreviousProcess == null
                  || str == null
                  || ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("  mPreviousProcessVisibleTime: ");
            TimeUtils.formatDuration(
                ActivityTaskManagerService.this.mPreviousProcessVisibleTime, sb);
            printWriter.println(sb);
          }
          if (ActivityTaskManagerService.this.mHeavyWeightProcess != null
              && (str == null
                  || ActivityTaskManagerService.this.mHeavyWeightProcess.containsPackage(str))) {
            if (z2) {
              printWriter.println();
              z2 = false;
            }
            printWriter.println(
                "  mHeavyWeightProcess: " + ActivityTaskManagerService.this.mHeavyWeightProcess);
          }
          if (str == null) {
            printWriter.println(
                "  mGlobalConfiguration: "
                    + ActivityTaskManagerService.this.getGlobalConfiguration());
            ActivityTaskManagerService.this.mRootWindowContainer.dumpDisplayConfigs(
                printWriter, "  ");
          }
          if (z) {
            Task topDisplayFocusedRootTask =
                ActivityTaskManagerService.this.getTopDisplayFocusedRootTask();
            if (str == null && topDisplayFocusedRootTask != null) {
              printWriter.println(
                  "  mConfigWillChange: " + topDisplayFocusedRootTask.mConfigWillChange);
            }
            if (ActivityTaskManagerService.this.mCompatModePackages.getPackages().size() > 0) {
              boolean z4 = false;
              for (Map.Entry entry :
                  ActivityTaskManagerService.this.mCompatModePackages.getPackages().entrySet()) {
                String str2 = (String) entry.getKey();
                int intValue = ((Integer) entry.getValue()).intValue();
                if (str == null || str.equals(str2)) {
                  if (!z4) {
                    printWriter.println("  mScreenCompatPackages:");
                    z4 = true;
                  }
                  printWriter.println("    " + str2 + ": " + intValue);
                }
              }
            }
          }
          if (str == null) {
            printWriter.println("  mWakefulness=" + PowerManagerInternal.wakefulnessToString(i2));
            printWriter.println(
                "  mSleepTokens="
                    + ActivityTaskManagerService.this.mRootWindowContainer.mSleepTokens);
            if (ActivityTaskManagerService.this.mRunningVoice != null) {
              printWriter.println(
                  "  mRunningVoice=" + ActivityTaskManagerService.this.mRunningVoice);
              printWriter.println(
                  "  mVoiceWakeLock" + ActivityTaskManagerService.this.mVoiceWakeLock);
            }
            printWriter.println("  mSleeping=" + ActivityTaskManagerService.this.mSleeping);
            printWriter.println(
                "  mShuttingDown="
                    + ActivityTaskManagerService.this.mShuttingDown
                    + " mTestPssMode="
                    + z3);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("  mVrController=");
            sb2.append(ActivityTaskManagerService.this.mVrController);
            printWriter.println(sb2.toString());
          }
          AppTimeTracker appTimeTracker = ActivityTaskManagerService.this.mCurAppTimeTracker;
          if (appTimeTracker != null) {
            appTimeTracker.dumpWithHeader(printWriter, "  ", true);
          }
          if (ActivityTaskManagerService.this.mAllowAppSwitchUids.size() > 0) {
            boolean z5 = false;
            for (int i3 = 0;
                i3 < ActivityTaskManagerService.this.mAllowAppSwitchUids.size();
                i3++) {
              ArrayMap arrayMap =
                  (ArrayMap) ActivityTaskManagerService.this.mAllowAppSwitchUids.valueAt(i3);
              for (int i4 = 0; i4 < arrayMap.size(); i4++) {
                if (str == null
                    || UserHandle.getAppId(((Integer) arrayMap.valueAt(i4)).intValue()) == i) {
                  if (z2) {
                    printWriter.println();
                    z2 = false;
                  }
                  if (!z5) {
                    printWriter.println("  mAllowAppSwitchUids:");
                    z5 = true;
                  }
                  printWriter.print("    User ");
                  printWriter.print(ActivityTaskManagerService.this.mAllowAppSwitchUids.keyAt(i3));
                  printWriter.print(": Type ");
                  printWriter.print((String) arrayMap.keyAt(i4));
                  printWriter.print(" = ");
                  UserHandle.formatUid(printWriter, ((Integer) arrayMap.valueAt(i4)).intValue());
                  printWriter.println();
                }
              }
            }
          }
          if (str == null) {
            if (ActivityTaskManagerService.this.mController != null) {
              printWriter.println(
                  "  mController="
                      + ActivityTaskManagerService.this.mController
                      + " mControllerIsAMonkey="
                      + ActivityTaskManagerService.this.mControllerIsAMonkey);
            }
            if (ActivityTaskManagerService.this.mControllerDescription != null) {
              printWriter.println(
                  "  mControllerDescription: "
                      + ActivityTaskManagerService.this.mControllerDescription);
            }
            printWriter.println(
                "  mGoingToSleepWakeLock="
                    + ActivityTaskManagerService.this.mTaskSupervisor.mGoingToSleepWakeLock);
            printWriter.println(
                "  mLaunchingActivityWakeLock="
                    + ActivityTaskManagerService.this.mTaskSupervisor.mLaunchingActivityWakeLock);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return z2;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void writeProcessesToProto(
        ProtoOutputStream protoOutputStream, String str, int i, boolean z) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        if (str == null) {
          try {
            ActivityTaskManagerService.this
                .getGlobalConfiguration()
                .dumpDebug(protoOutputStream, 1146756268051L);
            Task topDisplayFocusedRootTask =
                ActivityTaskManagerService.this.getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null) {
              protoOutputStream.write(1133871366165L, topDisplayFocusedRootTask.mConfigWillChange);
            }
            ActivityTaskManagerService.this.writeSleepStateToProto(protoOutputStream, i, z);
            if (ActivityTaskManagerService.this.mRunningVoice != null) {
              long start = protoOutputStream.start(1146756268060L);
              protoOutputStream.write(
                  1138166333441L, ActivityTaskManagerService.this.mRunningVoice.toString());
              ActivityTaskManagerService.this.mVoiceWakeLock.dumpDebug(
                  protoOutputStream, 1146756268034L);
              protoOutputStream.end(start);
            }
            ActivityTaskManagerService.this.mVrController.dumpDebug(
                protoOutputStream, 1146756268061L);
            if (ActivityTaskManagerService.this.mController != null) {
              long start2 = protoOutputStream.start(1146756268069L);
              protoOutputStream.write(
                  1138166333441L, ActivityTaskManagerService.this.mController.toString());
              protoOutputStream.write(
                  1133871366146L, ActivityTaskManagerService.this.mControllerIsAMonkey);
              protoOutputStream.end(start2);
            }
            ActivityTaskManagerService.this.mTaskSupervisor.mGoingToSleepWakeLock.dumpDebug(
                protoOutputStream, 1146756268079L);
            ActivityTaskManagerService.this.mTaskSupervisor.mLaunchingActivityWakeLock.dumpDebug(
                protoOutputStream, 1146756268080L);
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
        if (ActivityTaskManagerService.this.mHomeProcess != null
            && (str == null || ActivityTaskManagerService.this.mHomeProcess.containsPackage(str))) {
          ActivityTaskManagerService.this.mHomeProcess.dumpDebug(protoOutputStream, 1146756268047L);
        }
        if (ActivityTaskManagerService.this.mPreviousProcess != null
            && (str == null
                || ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
          ActivityTaskManagerService.this.mPreviousProcess.dumpDebug(
              protoOutputStream, 1146756268048L);
          protoOutputStream.write(
              1112396529681L, ActivityTaskManagerService.this.mPreviousProcessVisibleTime);
        }
        if (ActivityTaskManagerService.this.mHeavyWeightProcess != null
            && (str == null
                || ActivityTaskManagerService.this.mHeavyWeightProcess.containsPackage(str))) {
          ActivityTaskManagerService.this.mHeavyWeightProcess.dumpDebug(
              protoOutputStream, 1146756268050L);
        }
        for (Map.Entry entry :
            ActivityTaskManagerService.this.mCompatModePackages.getPackages().entrySet()) {
          String str2 = (String) entry.getKey();
          int intValue = ((Integer) entry.getValue()).intValue();
          if (str == null || str.equals(str2)) {
            long start3 = protoOutputStream.start(2246267895830L);
            protoOutputStream.write(1138166333441L, str2);
            protoOutputStream.write(1120986464258L, intValue);
            protoOutputStream.end(start3);
          }
        }
        AppTimeTracker appTimeTracker = ActivityTaskManagerService.this.mCurAppTimeTracker;
        if (appTimeTracker != null) {
          appTimeTracker.dumpDebug(protoOutputStream, 1146756268063L, true);
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean dumpActivity(
        FileDescriptor fileDescriptor,
        PrintWriter printWriter,
        String str,
        String[] strArr,
        int i,
        boolean z,
        boolean z2,
        boolean z3,
        int i2,
        int i3) {
      return ActivityTaskManagerService.this.dumpActivity(
          fileDescriptor, printWriter, str, strArr, i, z, z2, z3, i2, i3);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void dumpForOom(PrintWriter printWriter) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          printWriter.println("  mHomeProcess: " + ActivityTaskManagerService.this.mHomeProcess);
          printWriter.println(
              "  mPreviousProcess: " + ActivityTaskManagerService.this.mPreviousProcess);
          if (ActivityTaskManagerService.this.mHeavyWeightProcess != null) {
            printWriter.println(
                "  mHeavyWeightProcess: " + ActivityTaskManagerService.this.mHeavyWeightProcess);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean canGcNow() {
      boolean z;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          z =
              isSleeping()
                  || ActivityTaskManagerService.this.mRootWindowContainer
                      .allResumedActivitiesIdle();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return z;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public WindowProcessController getTopApp() {
      return ActivityTaskManagerService.this.mTopApp;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void scheduleDestroyAllActivities(String str) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRootWindowContainer.scheduleDestroyAllActivities(str);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void scheduleDestroyAllActivities(int i, int i2, String str) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRootWindowContainer.scheduleDestroyAllActivities(
              ActivityTaskManagerService.this.getProcessController(i, i2), str);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void removeUser(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRootWindowContainer.removeUser(i);
          ActivityTaskManagerService.this.mPackageConfigPersister.removeUser(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean switchUser(int i, UserState userState) {
      boolean switchUser;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          switchUser =
              ActivityTaskManagerService.this.mRootWindowContainer.switchUser(i, userState);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return switchUser;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onHandleAppCrash(WindowProcessController windowProcessController) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          windowProcessController.handleAppCrash();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int finishTopCrashedActivities(
        WindowProcessController windowProcessController, String str) {
      int finishTopCrashedActivities;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          finishTopCrashedActivities =
              ActivityTaskManagerService.this.mRootWindowContainer.finishTopCrashedActivities(
                  windowProcessController, str);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return finishTopCrashedActivities;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onUidActive(int i, int i2) {
      ActivityTaskManagerService.this.mActiveUids.onUidActive(i, i2);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onUidInactive(int i) {
      ActivityTaskManagerService.this.mActiveUids.onUidInactive(i);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onUidProcStateChanged(int i, int i2) {
      ActivityTaskManagerService.this.mActiveUids.onUidProcStateChanged(i, i2);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean handleAppCrashInActivityController(
        String str, int i, String str2, String str3, long j, String str4, Runnable runnable) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          IActivityController iActivityController = ActivityTaskManagerService.this.mController;
          if (iActivityController == null) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return false;
          }
          Runnable runnable2 = null;
          try {
            if (!iActivityController.appCrashed(str, i, str2, str3, j, str4)) {
              runnable2 = runnable;
            }
          } catch (RemoteException unused) {
            ActivityTaskManagerService.this.mController = null;
            Watchdog.getInstance().setActivityController(null);
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          if (runnable2 == null) {
            return false;
          }
          runnable2.run();
          return true;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void removeRecentTasksByPackageName(String str, int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRecentTasks.removeTasksByPackageName(str, i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void cleanupRecentTasksForUser(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRecentTasks.cleanupLocked(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void loadRecentTasksForUser(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRecentTasks.loadUserRecentsLocked(i);
          ActivityTaskManagerService.this.mPackageConfigPersister.loadUserPackages(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      LocaleManagerService.startResourceOverlayServiceForCleanUp(
          ActivityTaskManagerService.this.mContext);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void onPackagesSuspendedChanged(String[] strArr, boolean z, int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mRecentTasks.onPackagesSuspendedChanged(strArr, z, i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void flushRecentTasks() {
      ActivityTaskManagerService.this.mRecentTasks.flush();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void clearLockedTasks(String str) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.getLockTaskController().clearLockedTasks(str);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void updateUserConfiguration() {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Configuration configuration =
              new Configuration(ActivityTaskManagerService.this.getGlobalConfiguration());
          int currentUserId = ActivityTaskManagerService.this.mAmInternal.getCurrentUserId();
          Settings.System.adjustConfigurationForUser(
              ActivityTaskManagerService.this.mContext.getContentResolver(),
              configuration,
              currentUserId,
              Settings.System.canWrite(ActivityTaskManagerService.this.mContext));
          ActivityTaskManagerService.this.updateConfigurationLocked(
              configuration, null, false, false, currentUserId, false);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean canShowErrorDialogs() {
      boolean z;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          z = false;
          if (ActivityTaskManagerService.this.mShowDialogs
              && !ActivityTaskManagerService.this.mSleeping) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            if (!activityTaskManagerService.mShuttingDown
                && !activityTaskManagerService.mKeyguardController.isKeyguardOrAodShowing(0)) {
              ActivityTaskManagerService activityTaskManagerService2 =
                  ActivityTaskManagerService.this;
              if (!activityTaskManagerService2.hasUserRestriction(
                      "no_system_error_dialogs",
                      activityTaskManagerService2.mAmInternal.getCurrentUserId())
                  && (!UserManager.isDeviceInDemoMode(ActivityTaskManagerService.this.mContext)
                      || !ActivityTaskManagerService.this.mAmInternal.getCurrentUser().isDemo())) {
                z = true;
              }
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
    public void setProfileApp(String str) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mProfileApp = str;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setProfileProc(WindowProcessController windowProcessController) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mProfileProc = windowProcessController;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setProfilerInfo(ProfilerInfo profilerInfo) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mProfilerInfo = profilerInfo;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ActivityMetricsLaunchObserverRegistry getLaunchObserverRegistry() {
      ActivityMetricsLaunchObserverRegistry launchObserverRegistry;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          launchObserverRegistry =
              ActivityTaskManagerService.this
                  .mTaskSupervisor
                  .getActivityMetricsLogger()
                  .getLaunchObserverRegistry();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return launchObserverRegistry;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public IBinder getUriPermissionOwnerForActivity(IBinder iBinder) {
      Binder externalToken;
      ActivityTaskManagerService.enforceNotIsolatedCaller("getUriPermissionOwnerForActivity");
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
          externalToken =
              isInRootTaskLocked == null
                  ? null
                  : isInRootTaskLocked.getUriPermissionsLocked().getExternalToken();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return externalToken;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public TaskSnapshot getTaskSnapshotBlocking(int i, boolean z) {
      return ActivityTaskManagerService.this.getTaskSnapshot(i, z, false);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isUidForeground(int i) {
      return ActivityTaskManagerService.this.hasActiveVisibleWindow(i);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setDeviceOwnerUid(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.setDeviceOwnerUid(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setProfileOwnerUids(Set set) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.setProfileOwnerUids(set);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void setCompanionAppUids(int i, Set set) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mCompanionAppUidsMap.put(Integer.valueOf(i), set);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isBaseOfLockedTask(String str) {
      boolean isBaseOfLockedTask;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          isBaseOfLockedTask =
              ActivityTaskManagerService.this.getLockTaskController().isBaseOfLockedTask(str);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return isBaseOfLockedTask;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int getForegroundTaskId(int i) {
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
    public void bringTaskToForeground(int i, int i2, int i3) {
      ActivityTaskManagerService.this.mDexController.bringTaskToForeground(i, i2, i3);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ComponentName getRealActivityForTaskId(int i) {
      ComponentName realActivityForTaskId;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          realActivityForTaskId = ActivityTaskManagerService.this.mExt.getRealActivityForTaskId(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return realActivityForTaskId;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void clearHomeStack(int i) {
      ActivityTaskManagerService.this.mExt.clearHomeStack(i);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean hasPackageTask(int i, String str, int i2) {
      return ActivityTaskManagerService.this.mExt.hasPackageTask(i, str, i2);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void minimizeAllTasks(int i, boolean z) {
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
    public ActivityTaskManagerInternal.PackageConfigurationUpdater
        createPackageConfigurationUpdater() {
      return new PackageConfigurationUpdaterImpl(
          Binder.getCallingPid(), ActivityTaskManagerService.this);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ActivityTaskManagerInternal.PackageConfigurationUpdater
        createPackageConfigurationUpdater(String str, int i) {
      return new PackageConfigurationUpdaterImpl(str, i, ActivityTaskManagerService.this);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ActivityTaskManagerInternal.PackageConfig getApplicationConfig(String str, int i) {
      return ActivityTaskManagerService.this.mPackageConfigPersister.findPackageConfiguration(
          str, i);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean hasSystemAlertWindowPermission(int i, int i2, String str) {
      return ActivityTaskManagerService.this.hasSystemAlertWindowPermission(i, i2, str);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void registerActivityStartInterceptor(
        int i, ActivityInterceptorCallback activityInterceptorCallback) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (ActivityTaskManagerService.this.mActivityInterceptorCallbacks.contains(i)) {
            throw new IllegalArgumentException("Duplicate id provided: " + i);
          }
          if (activityInterceptorCallback == null) {
            throw new IllegalArgumentException(
                "The passed ActivityInterceptorCallback can not be null");
          }
          if (!ActivityInterceptorCallback.isValidOrderId(i)) {
            throw new IllegalArgumentException(
                "Provided id "
                    + i
                    + " is not in range of valid ids for system services [0,6] nor in range of"
                    + " valid ids for mainline module services [1000,1001]");
          }
          ActivityTaskManagerService.this.mActivityInterceptorCallbacks.put(
              i, activityInterceptorCallback);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void unregisterActivityStartInterceptor(int i) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (!ActivityTaskManagerService.this.mActivityInterceptorCallbacks.contains(i)) {
            throw new IllegalArgumentException(
                "ActivityInterceptorCallback with id (" + i + ") is not registered");
          }
          ActivityTaskManagerService.this.mActivityInterceptorCallbacks.remove(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public ActivityManager.RecentTaskInfo getMostRecentTaskFromBackground() {
      List tasks = ActivityTaskManagerService.this.getTasks(1);
      if (tasks.size() > 0) {
        ActivityManager.RunningTaskInfo runningTaskInfo =
            (ActivityManager.RunningTaskInfo) tasks.get(0);
        ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
        for (ActivityManager.RecentTaskInfo recentTaskInfo :
            activityTaskManagerService
                .getRecentTasks(2, 2, activityTaskManagerService.mContext.getUserId())
                .getList()) {
          if (recentTaskInfo.id != runningTaskInfo.id) {
            return recentTaskInfo;
          }
        }
        return null;
      }
      Slog.i("ActivityTaskManager", "No running task found!");
      return null;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public List getAppTasks(String str, int i) {
      ArrayList arrayList = new ArrayList();
      List appTasks = ActivityTaskManagerService.this.getAppTasks(str, i);
      int size = appTasks.size();
      for (int i2 = 0; i2 < size; i2++) {
        arrayList.add(
            new ActivityManager.AppTask(IAppTask.Stub.asInterface((IBinder) appTasks.get(i2))));
      }
      return arrayList;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int getTaskToShowPermissionDialogOn(String str, int i) {
      int taskToShowPermissionDialogOn;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          taskToShowPermissionDialogOn =
              ActivityTaskManagerService.this.mRootWindowContainer.getTaskToShowPermissionDialogOn(
                  str, i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return taskToShowPermissionDialogOn;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void restartTaskActivityProcessIfVisible(int i, final String str) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId =
              ActivityTaskManagerService.this.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w("ActivityTaskManager", "Failed to restart Activity. No task found for id: " + i);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          ActivityRecord activity =
              anyTaskForId.getActivity(
                  new Predicate() { // from class:
                                    // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean lambda$restartTaskActivityProcessIfVisible$6;
                      lambda$restartTaskActivityProcessIfVisible$6 =
                          ActivityTaskManagerService.LocalService
                              .lambda$restartTaskActivityProcessIfVisible$6(
                                  str, (ActivityRecord) obj);
                      return lambda$restartTaskActivityProcessIfVisible$6;
                    }
                  });
          if (activity == null) {
            Slog.w(
                "ActivityTaskManager",
                "Failed to restart Activity. No Activity found for package name: "
                    + str
                    + " in task: "
                    + i);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          activity.restartProcessIfVisible();
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }

    public static /* synthetic */ boolean lambda$restartTaskActivityProcessIfVisible$6(
        String str, ActivityRecord activityRecord) {
      return str.equals(activityRecord.packageName) && !activityRecord.finishing;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void registerTaskStackListener(ITaskStackListener iTaskStackListener) {
      ActivityTaskManagerService.this.registerTaskStackListener(iTaskStackListener);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean isAssistDataAllowed() {
      return ActivityTaskManagerService.this.isAssistDataAllowedOnCurrentActivity();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean moveTaskToBack(int i, boolean z, Bundle bundle) {
      return ActivityTaskManagerService.this.mExt.moveTaskToBack(i, z, bundle);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int enableDexDisplay(int i, int i2, int i3) {
      ActivityTaskManagerService.this.mAmInternal.enforceCallingPermission(
          "android.permission.MANAGE_ACTIVITY_TASKS", "enableDexDisplay");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return ActivityTaskManagerService.this.mDexController.enableDexDisplay(i, i2, i3);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int disableDexDisplay() {
      ActivityTaskManagerService.this.mAmInternal.enforceCallingPermission(
          "android.permission.MANAGE_ACTIVITY_TASKS", "disableDexDisplay");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return ActivityTaskManagerService.this.mDexController.disableDexDisplay();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void registerRemoteAppControllerCallbacks(
        RemoteAppControllerCallbacks remoteAppControllerCallbacks) {
      ActivityTaskManagerService.this.mRemoteAppController.registerCallbacks(
          remoteAppControllerCallbacks);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void unregisterRemoteAppControllerCallbacks(
        RemoteAppControllerCallbacks remoteAppControllerCallbacks) {
      ActivityTaskManagerService.this.mRemoteAppController.unregisterCallbacks(
          remoteAppControllerCallbacks);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int startRemoteActivityAsCaller(
        Intent intent, RemoteAppController.CallerInfo callerInfo, int i, Bundle bundle) {
      int callingPid = Binder.getCallingPid();
      if (callingPid != Process.myPid()) {
        Slog.e("ActivityTaskManager", "Pid " + callingPid + " cannot startRemoteActivityAsCaller");
        return 0;
      }
      ActivityTaskManagerService.this.mRemoteAppController.startActivityAsCaller(
          intent, callerInfo, i, bundle);
      return 0;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean goodToChangeMode(int i, int i2) {
      ActivityTaskManagerService.this.mAmInternal.enforceCallingPermission(
          "android.permission.MANAGE_ACTIVITY_STACKS", "goodToChangeMode");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return ActivityTaskManagerService.this.mDexController.goodToChangeMode(i, i2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void dismissSplitScreenMode(boolean z) {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          TaskDisplayArea defaultTaskDisplayArea =
              ActivityTaskManagerService.this.mRootWindowContainer.getDefaultTaskDisplayArea();
          if (!defaultTaskDisplayArea.isSplitScreenModeActivated()) {
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          Task rootMainStageTask =
              z
                  ? defaultTaskDisplayArea.getRootMainStageTask()
                  : defaultTaskDisplayArea.getRootSideStageTask();
          defaultTaskDisplayArea.onStageSplitScreenDismissed(
              rootMainStageTask != null ? rootMainStageTask.getTopMostTask() : null);
          WindowManagerService.resetPriorityAfterLockedSection();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void showRescuePartyDialog() {
      ActivityTaskManagerService.this.mUiHandler.post(
          new Runnable() { // from class:
                           // com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              ActivityTaskManagerService.LocalService.this.lambda$showRescuePartyDialog$7();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRescuePartyDialog$7() {
      if (ActivityTaskManagerService.this.mShowDialogs) {
        BaseErrorDialog baseErrorDialog =
            new BaseErrorDialog(ActivityTaskManagerService.this.mUiContext);
        baseErrorDialog.getWindow().setType(2010);
        baseErrorDialog.setCancelable(false);
        baseErrorDialog.setTitle(
            ActivityTaskManagerService.this.mUiContext.getText(
                CoreRune.IS_TABLET_DEVICE ? 17042404 : 17042403));
        baseErrorDialog.setMessage(
            ActivityTaskManagerService.this.mUiContext.getText(
                CoreRune.IS_TABLET_DEVICE ? 17042402 : 17042401));
        baseErrorDialog.setButton(
            -1,
            ActivityTaskManagerService.this.mUiContext.getText(R.string.ok),
            ActivityTaskManagerService.this.mUiHandler.obtainMessage(1, baseErrorDialog));
        baseErrorDialog.show();
      }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public boolean handleAltTabKeyIfNeeded() {
      boolean handleAltTabKeyIfNeededLocked;
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          handleAltTabKeyIfNeededLocked =
              ActivityTaskManagerService.this.mMultiTaskingController
                  .handleAltTabKeyIfNeededLocked();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return handleAltTabKeyIfNeededLocked;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void releaseAltTabKeyConsumer() {
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          ActivityTaskManagerService.this.mMultiTaskingController.releaseAltTabKeyConsumerLocked();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public int getMultiWindowModeForAssistantHotKey() {
      int multiWindowModeForAssistantHotKeyLocked;
      if (!CoreRune.MW_SUPPORT_ASSISTANT_HOT_KEY) {
        return 1;
      }
      WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          multiWindowModeForAssistantHotKeyLocked =
              ActivityTaskManagerService.this.mMultiTaskingController
                  .getMultiWindowModeForAssistantHotKeyLocked();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
      return multiWindowModeForAssistantHotKeyLocked;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal
    public void startAssistantActivityToSplit(Intent intent, float f) {
      if (CoreRune.MW_SUPPORT_ASSISTANT_HOT_KEY) {
        WindowManagerGlobalLock windowManagerGlobalLock =
            ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            ActivityTaskManagerService.this.mMultiTaskingController
                .startAssistantActivityToSplitLocked(intent, f);
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      }
    }
  }

  public void initActivityManagerPerformance() {
    this.mAMBooster = ActivityManagerPerformance.getBooster(this, this.mContext);
  }

  public void adjustConfigurationForDexIfNeeded(
      Configuration configuration, WindowProcessController windowProcessController) {
    this.mDexController.adjustConfigurationForDexIfNeeded(configuration, windowProcessController);
  }

  public ParceledListSlice getVisibleTasks() {
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    boolean isCrossUserAllowed = isCrossUserAllowed(callingPid, callingUid);
    int[] profileIds = getUserManager().getProfileIds(UserHandle.getUserId(callingUid), true);
    ArraySet arraySet = new ArraySet();
    for (int i : profileIds) {
      arraySet.add(Integer.valueOf(i));
    }
    ArrayList arrayList = new ArrayList();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mTaskSupervisor
            .getRunningTasks()
            .getTasks(
                100,
                arrayList,
                (isGetTasksAllowed("getVisibleTasks", callingPid, callingUid) ? 2 : 0)
                    | (isCrossUserAllowed ? 4 : 0),
                getRecentTasks(),
                this.mRootWindowContainer,
                callingUid,
                arraySet,
                true);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return new ParceledListSlice(arrayList);
  }

  public Pair setLongLiveTask(int i, boolean z) {
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
        Pair pair2 =
            new Pair(anyTaskForId.getRootProcessName(), Integer.valueOf(anyTaskForId.mUserId));
        WindowManagerService.resetPriorityAfterLockedSection();
        return pair2;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public ArrayList getDedicatedProcesses(int i) {
    ArrayList dedicatedProcesses;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        dedicatedProcesses = this.mRecentTasks.getDedicatedProcesses(i);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return dedicatedProcesses;
  }

  public ArrayList getDedicatedTaskIds(int i) {
    ArrayList dedicatedTaskIdsLocked;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        dedicatedTaskIdsLocked = this.mRecentTasks.getDedicatedTaskIdsLocked(i);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return dedicatedTaskIdsLocked;
  }

  public boolean isDedicatedProcess(int i, String str) {
    boolean isDedicatedProcess;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        isDedicatedProcess = this.mRecentTasks.isDedicatedProcess(i, str);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return isDedicatedProcess;
  }

  public void removeDedicatedProcessFromPackage(String str, int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mRecentTasks.removeDedicatedProcessFromPackage(str, i);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean getAutoRemoveRecents(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
        if (anyTaskForId == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        boolean z = anyTaskForId.autoRemoveRecents;
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void registerCallback4Task(RemoteCallback remoteCallback) {
    synchronized (this.mGlobalLockWithoutBoost) {
      this.mCb4Task = Optional.ofNullable(remoteCallback);
    }
  }

  public void notifyDedicated(final int i) {
    synchronized (this.mGlobalLockWithoutBoost) {
      Slog.v("ActivityTaskManager", "notifyDedicatedState:" + i + "," + this.mCb4Task.isPresent());
      this.mCb4Task.ifPresent(
          new Consumer() { // from class:
                           // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ActivityTaskManagerService.this.lambda$notifyDedicated$15(i, (RemoteCallback) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notifyDedicated$15(int i, RemoteCallback remoteCallback) {
    try {
      Bundle bundle = new Bundle();
      bundle.putInt("TASK_ID", i);
      remoteCallback.sendResult(bundle);
    } catch (Exception e) {
      Slog.e("ActivityTaskManager", "notifyDedicatedState : ", e);
      this.mCb4Task = Optional.empty();
    }
  }

  public void sendSaLoggingBroadcast(String str, String str2, String str3, long j, Map map) {
    if (CoreRune.FW_SA_LOGGING) {
      String str4 = "None";
      if (CoreRune.MT_NEW_DEX) {
        DisplayContent defaultDisplay = this.mRootWindowContainer.getDefaultDisplay();
        if (this.mRootWindowContainer.getTopFocusedDisplayId() == 0
            && !defaultDisplay.isDexMode()) {
          str4 = defaultDisplay.isNewDexMode() ? "NewDex" : "Normal";
        }
      }
      CoreSaLogger.sendSaLoggingBroadcast(
          this.mContext, str, str2, str3, j, map instanceof HashMap ? (HashMap) map : null, str4);
    }
  }

  public void sendSaLoggingBroadcastForSetting(String str, String str2, String str3) {
    if (CoreRune.FW_SA_LOGGING) {
      CoreSaLogger.sendSaLoggingBroadcastForSetting(this.mContext, str, str2, str3);
    }
  }

  public void setCustomSplashScreenTheme(String str, String str2, int i) {
    if (str == null) {
      throw new IllegalArgumentException("Can not verify calling package");
    }
    int callingPid = Binder.getCallingPid();
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        WindowProcessController process = this.mProcessMap.getProcess(callingPid);
        if (process == null) {
          throw new SecurityException("Can not verify calling process");
        }
        if (!str.equals(process.mInfo.packageName)) {
          throw new SecurityException("Can not replace splash screen theme on other package");
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    try {
      AppGlobals.getPackageManager()
          .setSplashScreenTheme(
              str,
              str2,
              handleIncomingUser(
                  callingPid, Binder.getCallingUid(), i, "setCustomSplashScreenTheme"));
    } catch (RemoteException e) {
      Log.w("ActivityTaskManager", "Couldn't persist the starting theme", e);
    }
  }

  public void setCutoutPolicy(int i, String str, int i2) {
    enforceTaskPermission("setCutoutPolicy()");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mExt.mDisplayCutoutController.setPolicy(i, str, i2, true);
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

  public int getCutoutPolicy(int i, String str) {
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

  public boolean isPrepareOccluding(int i) {
    if (this.mPrepareOccludingMap.get(Integer.valueOf(i)) != null) {
      return ((Boolean) this.mPrepareOccludingMap.get(Integer.valueOf(i))).booleanValue();
    }
    return false;
  }

  public boolean isOccludingForAllDisplay() {
    for (int i = 0; i < this.mOccludingMap.size(); i++) {
      if (this.mOccludingMap.get(Integer.valueOf(i)) != null
          && ((Boolean) this.mOccludingMap.get(Integer.valueOf(i))).booleanValue()) {
        return true;
      }
    }
    return false;
  }

  public boolean isOccluding(int i) {
    if (this.mOccludingMap.get(Integer.valueOf(i)) != null) {
      return ((Boolean) this.mOccludingMap.get(Integer.valueOf(i))).booleanValue();
    }
    return false;
  }

  public void setPrepareOccluding(boolean z, int i) {
    this.mPrepareOccludingMap.put(Integer.valueOf(i), Boolean.valueOf(z));
  }

  public void setOccluding(boolean z, int i) {
    this.mOccludingMap.put(Integer.valueOf(i), Boolean.valueOf(z));
  }

  public void requestWaitingForOccluding(int i) {
    enforceNotIsolatedCaller("requestWaitingForOccluding");
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          if (this.mWindowManager.mExt.mExtraDisplayPolicy.shouldNotTopDisplay(i)) {
            Slog.v(
                "ActivityTaskManager",
                "Dropping request of waiting for occluding as display "
                    + i
                    + ", is no Top display. CallingUid="
                    + callingUid
                    + ", CallingPid= "
                    + callingPid);
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          }
          if (!isPrepareOccluding(i) && this.mKeyguardController.isKeyguardLocked(i)) {
            Slog.v(
                "ActivityTaskManager",
                "Setting PrepareOccluding for display  "
                    + i
                    + ". CallingUid="
                    + callingUid
                    + ", CallingPid= "
                    + callingPid);
            setPrepareOccluding(true, i);
          }
          if (CoreRune.SAFE_DEBUG) {
            Slog.d("ActivityTaskManager", "requestWaitingForOccluding is called");
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

  public String getScpmVersion() {
    return this.mExt.mPackageFeatureManagerService.getScpmVersion(
        PackageFeatureGroup.FOLDABLE_PACKAGE_FEATURE.mName);
  }

  public void setOrientationControlPolicy(int i, String str, int i2) {
    if (CoreRune.FW_ORIENTATION_CONTROL) {
      enforceTaskPermission("setOrientationControlPolicy()");
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mExt.mOrientationController.setPolicy(i, str, i2);
          long clearCallingIdentity = Binder.clearCallingIdentity();
          try {
            this.mExt.mOrientationController.updateValueToTask(true);
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
  }

  public int getOrientationControlPolicy(int i, String str) {
    int policy;
    if (!CoreRune.FW_ORIENTATION_CONTROL) {
      return 0;
    }
    enforceTaskPermission("getOrientationControlPolicy()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        policy = this.mExt.mOrientationController.getPolicy(i, str);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return policy;
  }

  public void setOrientationControlDefault(boolean z) {
    if (CoreRune.FW_ORIENTATION_CONTROL) {
      enforceTaskPermission("setOrientationControlDefault()");
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mExt.mOrientationController.mDefaultEnabled = z;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }

  public void setDisallowWhenLandscape(boolean z) {
    if (CoreRune.FW_ORIENTATION_CONTROL) {
      enforceTaskPermission("setDisallowWhenLandscape()");
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          this.mExt.mOrientationController.mDisallowWhenLandscapeFixedApp = z;
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }

  public void setBoundsCompatAlignment(int i) {
    if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL) {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          BoundsCompatAlignmentController.setAlignmentLocked(i);
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }
  }

  public int getBoundsCompatAlignment() {
    int alignmentLocked;
    if (!CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL) {
      return 0;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        alignmentLocked = BoundsCompatAlignmentController.getAlignmentLocked();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return alignmentLocked;
  }

  public IFoldStarManager getFoldStarManagerService() {
    FoldStarManagerService foldStarManagerService;
    enforceTaskPermission("getFoldStarManagerService()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (this.mFoldStarManagerService == null) {
          this.mFoldStarManagerService = new FoldStarManagerService(this);
        }
        foldStarManagerService = this.mFoldStarManagerService;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return foldStarManagerService;
  }

  public void setUseLetterbox(boolean z) {
    if (CoreRune.FW_CUSTOM_LETTERBOX) {
      enforceTaskPermission("setUseLetterbox()");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            CustomLetterboxConfiguration.setUseLetterbox(z);
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
  }

  public boolean hasWallpaperBackgroundForLetterbox(int i) {
    boolean hasWallpaperBackgroundForLetterboxLocked;
    enforceTaskPermission("hasWallpaperBackgroundForLetterbox()");
    WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        hasWallpaperBackgroundForLetterboxLocked =
            hasWallpaperBackgroundForLetterboxLocked(
                this.mRootWindowContainer.getDisplayContent(i));
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return hasWallpaperBackgroundForLetterboxLocked;
  }

  public boolean hasWallpaperBackgroundForLetterboxLocked(DisplayContent displayContent) {
    return (displayContent == null
            || displayContent.getActivity(
                    new Predicate() { // from class:
                                      // com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda5
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$hasWallpaperBackgroundForLetterboxLocked$16;
                        lambda$hasWallpaperBackgroundForLetterboxLocked$16 =
                            ActivityTaskManagerService
                                .lambda$hasWallpaperBackgroundForLetterboxLocked$16(
                                    (ActivityRecord) obj);
                        return lambda$hasWallpaperBackgroundForLetterboxLocked$16;
                      }
                    })
                == null)
        ? false
        : true;
  }

  public static /* synthetic */ boolean lambda$hasWallpaperBackgroundForLetterboxLocked$16(
      ActivityRecord activityRecord) {
    return activityRecord.isVisible() && activityRecord.hasWallpaperBackgroundForLetterbox();
  }

  public List getCoverLauncherAvailableAppList(int i) {
    return List.of();
  }

  public Map getCoverLauncherEnabledAppList(int i) {
    return getCoverLauncherEnabledAppListByType(i, 0);
  }

  public void startActivityForCoverLauncher(Intent intent, String str) {
    startActivityForCoverLauncherAsUser(intent, str, this.mContext.getUserId());
  }

  public void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) {
    if (i != UserHandle.getCallingUserId()) {
      this.mAmInternal.enforceCallingPermission(
          "android.permission.INTERACT_ACROSS_USERS", "startActivityForCoverLauncher");
    }
    if (intent == null) {
      Log.e(
          "ActivityTaskManager",
          "Intent for startActivityForCoverLauncher is null, requestReason=" + str);
      return;
    }
    Log.i(
        "ActivityTaskManager",
        "Launch application for CoverLauncher : "
            + intent
            + " for user="
            + i
            + ", requestReason="
            + str);
    if (this.mWindowManager.isKeyguardLocked()
        && this.mWindowManager.isKeyguardSecure(getCurrentUserId())) {
      throw null;
    }
    this.mWindowManager.dismissKeyguard(null, null);
    throw null;
  }

  public void startActivityForDexRestart(int i) {
    enforceTaskPermission("startActivityForDexRestart");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
          if (anyTaskForId == null) {
            Slog.w("ActivityTaskManager", "startActivityForDexRestart : invalid task");
            WindowManagerService.resetPriorityAfterLockedSection();
          } else {
            this.mDexCompatController.startActivityForDexRestart(anyTaskForId);
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

  public void setShouldDebugForUPSM(boolean z) {
    this.shouldDebug = z;
  }

  public void writeDebugLogForUPSM(String str) {
    if (this.shouldDebug) {
      Slog.d("WM_DEBUG_UPSM", str);
    }
  }

  public final void updateActivityRecordForColorThemeIfNeeded(ActivityRecord activityRecord) {
    if (!SemWallpaperThemeUtils.hasColorThemeOverlay(
            activityRecord.info.applicationInfo.overlayPaths)
        || activityRecord.info.applicationInfo.packageName == null) {
      return;
    }
    try {
      ApplicationInfo applicationInfo =
          AppGlobals.getPackageManager()
              .getApplicationInfo(
                  activityRecord.info.applicationInfo.packageName,
                  1024L,
                  UserHandle.getCallingUserId());
      if (applicationInfo != null) {
        ApplicationInfo applicationInfo2 = activityRecord.info.applicationInfo;
        applicationInfo2.resourceDirs = applicationInfo.resourceDirs;
        applicationInfo2.overlayPaths = applicationInfo.overlayPaths;
      }
    } catch (RemoteException unused) {
      Slog.w("ActivityTaskManager", "Failed to update resourceDirs and overlayPaths");
    }
  }
}
