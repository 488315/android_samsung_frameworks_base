package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.AppLockPolicy;
import com.android.internal.app.SmRccPolicy;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.CompatChangeableAppsCache;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.CompatChangeableApps;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.corescpm.ScpmController;
import com.samsung.android.server.corescpm.ScpmControllerImpl;
import com.samsung.android.server.corestate.CoreStateObserverController;
import com.samsung.android.server.corestate.CoreStateSystemFeatureObserver;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.core.PackageFeatureController;
import com.samsung.android.server.packagefeature.core.PackageFeatureController$$ExternalSyntheticLambda0;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService$$ExternalSyntheticLambda1;
import com.samsung.android.server.packagefeature.core.PackageFeatureSettings;
import com.samsung.android.server.packagefeature.core.PackageFeatures;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;
import com.samsung.android.server.util.CoreLogger;
import com.samsung.android.server.util.SafetySystemService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityTaskManagerServiceExt {
    public ActivityEmbeddedController mActivityEmbeddedController;
    public final Context mContext;
    public final CoreStateController mCoreStateController;
    public DisplayCutoutController mDisplayCutoutController;
    public FlexPanelController mFlexPanelController;
    public final PackageSpecialManagementList mHighRefreshRateBlockList;
    public final PackageSpecialManagementList mLowRefreshRateList;
    public final PackageSpecialManagementList mNaviAppLowRefreshRateList;
    public final ActivityTaskManagerService mService;
    public final ArrayList mStartedUserIds = new ArrayList();
    public SmRccPolicy mSmRccPolicy = null;
    public boolean mAppLockIsInMultiWindowMode = false;
    public final WeakHashMap mKeepAliveActivities = new WeakHashMap();
    public final AtomicBoolean mHasActivitiesKeptAlive = new AtomicBoolean(false);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class RefreshRateBlockList extends PackageSpecialManagementList {
        public RefreshRateBlockList(PackageFeature packageFeature) {
            super(packageFeature);
        }

        @Override // com.samsung.android.server.packagefeature.util.PackageSpecialManagementList, com.samsung.android.server.packagefeature.PackageFeatureCallback
        public final void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
            super.onPackageFeatureDataChanged(packageFeatureData);
            ActivityTaskManagerServiceExt.this.mService.mWindowManager.requestTraversal();
        }
    }

    public ActivityTaskManagerServiceExt(Context context, ActivityTaskManagerService activityTaskManagerService) {
        this.mLowRefreshRateList = CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST ? new RefreshRateBlockList(PackageFeature.LOW_REFRESH_RATE) : null;
        this.mNaviAppLowRefreshRateList = CoreRune.FW_VRR_NAVIGATION_LOW_REFRESH_RATE ? new RefreshRateBlockList(PackageFeature.NAVIGATION_LOW_REFRESH_RATE) : null;
        this.mHighRefreshRateBlockList = CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST ? new RefreshRateBlockList(PackageFeature.HIGH_REFRESH_RATE) : null;
        this.mContext = context;
        this.mService = activityTaskManagerService;
        this.mCoreStateController = new CoreStateController(activityTaskManagerService);
    }

    public static boolean isSystemUid(int i) {
        return UserHandle.getAppId(i) == 1000 || i == 0;
    }

    public final void checkAppLockState(final Intent intent, final boolean z, final String str, boolean z2) {
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        try {
            boolean[] zArr = new boolean[2];
            AppLockPolicy appLockPolicy = activityTaskManagerService.mAppLockPolicy;
            if (appLockPolicy != null) {
                zArr[0] = appLockPolicy.isAppLockedPackage(str);
                zArr[1] = activityTaskManagerService.mAppLockPolicy.isAppLockedVerifying(str);
            }
            final int callingUid = Binder.getCallingUid();
            boolean z3 = zArr[0];
            if (z3) {
                boolean z4 = zArr[1];
                StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("AppLock checkAppLockState locked:", z3, " verifying:", z4, " pkgName = ");
                m.append(str);
                m.append(" isInMultiWindowMode:");
                m.append(this.mAppLockIsInMultiWindowMode);
                m.append(" showWhenLocked:");
                m.append(z);
                Slog.w("ActivityTaskManagerServiceExt", m.toString());
                if (!z4 || z) {
                    setAppLockedVerifying(str, true);
                    Runnable runnable = new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityTaskManagerServiceExt activityTaskManagerServiceExt = ActivityTaskManagerServiceExt.this;
                            String str2 = str;
                            int i = callingUid;
                            boolean z5 = z;
                            Intent intent2 = intent;
                            activityTaskManagerServiceExt.getClass();
                            Intent intent3 = new Intent("com.samsung.android.intent.action.CHECK_APPLOCK_SERVICE");
                            intent3.setPackage("com.samsung.android.applock");
                            intent3.putExtra("LAUNCH_FROM_RESUME", true);
                            intent3.putExtra("LOCKED_PACKAGE_NAME", str2);
                            intent3.putExtra("LOCKED_PACKAGE_USERID", UserHandle.getUserId(i));
                            intent3.putExtra("LOCKED_APP_CAN_SHOW_WHEN_LOCKED", z5);
                            if (intent2 != null) {
                                Intent intent4 = new Intent(intent2.getAction());
                                intent4.setComponent(intent2.getComponent());
                                intent3.putExtra("LOCKED_PACKAGE_INTENT", intent4);
                            }
                            try {
                                Context context = activityTaskManagerServiceExt.mContext;
                                context.startServiceAsUser(intent3, context.getUser());
                            } catch (Exception e) {
                                Slog.e("ActivityTaskManagerServiceExt", "AppLock service start failed for intent:" + intent3, e);
                            }
                        }
                    };
                    if (z2) {
                        runnable.run();
                    } else {
                        activityTaskManagerService.mH.post(runnable);
                    }
                }
            }
        } catch (Exception e) {
            Slog.e("ActivityTaskManagerServiceExt", "Exception while checking App Lock locked / verifying state : ", e);
        }
    }

    public final boolean[] getAppLockLaunchingState(IBinder iBinder) {
        boolean[] zArr = new boolean[3];
        synchronized (this) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    throw new IllegalArgumentException("AppLockLaunchingFromActivity: No activity record matching token=" + iBinder);
                }
                if (forTokenLocked.mLaunchingRequestedFromNotification) {
                    forTokenLocked.mLaunchingRequestedFromNotification = false;
                    zArr[1] = true;
                } else {
                    zArr[1] = false;
                }
                if (this.mService.mAppLockPolicy.isActivityInExceptionList(forTokenLocked.info.name)) {
                    zArr[2] = true;
                } else {
                    zArr[2] = false;
                }
                Task task = forTokenLocked.task;
                if (task != null && task.inFreeformWindowingMode() && task.isDexMode()) {
                    zArr[0] = false;
                } else {
                    zArr[0] = forTokenLocked.inMultiWindowMode();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return zArr;
    }

    public final int getForegroundTaskId(int i) {
        Task task;
        ActivityRecord activity;
        DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
        int i2 = -1;
        if (displayContent == null || (activity = displayContent.getActivity(new ActivityTaskManagerServiceExt$$ExternalSyntheticLambda6())) == null) {
            task = null;
        } else {
            task = activity.task;
            if (task != null) {
                i2 = task.mTaskId;
            }
        }
        if (DesktopModeFeature.DEBUG) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "getForegroundTaskId(), displayId=", ", taskId=", ", task=");
            m.append(task);
            Log.i("ActivityTaskManagerServiceExt", m.toString());
        }
        return i2;
    }

    public final String getSmRccAction() {
        SmRccPolicy smRccPolicy = this.mSmRccPolicy;
        if (smRccPolicy != null) {
            return smRccPolicy.getSmRccAction();
        }
        return null;
    }

    public final ArrayList getStartedUserIdsLocked() {
        return new ArrayList(this.mStartedUserIds);
    }

    public final boolean hasPackageTask(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
                if (displayContent == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean z = displayContent.getTask(new Predicate() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda9
                    public final /* synthetic */ int f$0 = 2;
                    public final /* synthetic */ String f$1 = "com.sec.android.app.desktoplauncher";

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        ComponentName componentName;
                        Task task = (Task) obj;
                        return task.getActivityType() == this.f$0 && (componentName = task.realActivity) != null && componentName.getPackageName().equals(this.f$1);
                    }
                }, true) != null;
                WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void installSystemProvidersLocked() {
        this.mStartedUserIds.add(0);
        CoreStateController coreStateController = this.mCoreStateController;
        coreStateController.getClass();
        Slog.d("CoreStateController", "initializeLocked()");
        ActivityTaskManagerService activityTaskManagerService = coreStateController.mAtm;
        CoreStateObserverController coreStateObserverController = new CoreStateObserverController(activityTaskManagerService.mContext, coreStateController, new Handler(activityTaskManagerService.mH.getLooper()));
        coreStateController.mObserverController = coreStateObserverController;
        coreStateObserverController.mStartedUserIds.add(0);
        CoreStateSystemFeatureObserver coreStateSystemFeatureObserver = coreStateObserverController.mSystemFeatureObserver;
        Iterator it = coreStateSystemFeatureObserver.mSystemFeaturesList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            ((HashMap) coreStateSystemFeatureObserver.mSystemFeaturesRepository).put(str, Boolean.valueOf(coreStateSystemFeatureObserver.mContext.getPackageManager().hasSystemFeature(str)));
        }
        coreStateObserverController.mSettingObserver.beginObserveCoreStateSettings();
        coreStateObserverController.sendCoreState(true, 0, null);
        coreStateController.notifyCoreStatesChangedLocked(0, null);
        MultiWindowEnableController multiWindowEnableController = this.mService.mMultiWindowEnableController;
        multiWindowEnableController.mDeviceSupportsMultiWindow = ActivityTaskManager.deviceSupportsMultiWindow(multiWindowEnableController.mAtm.mContext);
        AnyMotionDetector$$ExternalSyntheticOutline0.m("MultiWindowEnableController", new StringBuilder("updateDeviceSupportsMultiWindow: support="), multiWindowEnableController.mDeviceSupportsMultiWindow);
        if (multiWindowEnableController.mDeviceSupportsMultiWindow) {
            multiWindowEnableController.setMultiWindowDynamicEnabled(0, "Initialize", multiWindowEnableController.isMultiWindowForceOnRequested(0) || !multiWindowEnableController.isMultiWindowOffRequested(0), false, false);
        }
    }

    public final void keepAliveActivityLocked(Task task, boolean z) {
        ActivityRecord activityRecord = task.topRunningActivityLocked();
        if (activityRecord != null) {
            if (!z) {
                this.mKeepAliveActivities.remove(activityRecord);
                if (this.mKeepAliveActivities.size() == 0) {
                    this.mHasActivitiesKeptAlive.compareAndSet(true, false);
                    return;
                }
                return;
            }
            if (this.mKeepAliveActivities.size() >= 2) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("Max count exceeded! Cannot set keepAlive for taskId="), task.mTaskId, "ActivityTaskManagerServiceExt");
            } else {
                this.mKeepAliveActivities.put(activityRecord, Boolean.TRUE);
                this.mHasActivitiesKeptAlive.compareAndSet(false, true);
            }
        }
    }

    public final void onSystemReady() {
        Context context = this.mContext;
        SafetySystemService.Manager manager = SafetySystemService.LazyHolder.sSingleton;
        synchronized (manager) {
            manager.mSystemContext = context;
        }
        final MultiTaskingAppCompatController multiTaskingAppCompatController = this.mService.mMultiTaskingAppCompatController;
        multiTaskingAppCompatController.mAtmService.mH.post(new Runnable() { // from class: com.android.server.wm.MultiTaskingAppCompatController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingAppCompatController multiTaskingAppCompatController2 = MultiTaskingAppCompatController.this;
                CompatChangeableAppsCache compatChangeableAppsCache = CompatChangeableAppsCache.LazyHolder.sCache;
                Context context2 = multiTaskingAppCompatController2.mAtmService.mContext;
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addDataScheme("package");
                context2.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.wm.CompatChangeableAppsCache.1
                    public final /* synthetic */ CompatChangeableAppsCache this$0 = LazyHolder.sCache;

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        Uri data = intent.getData();
                        if (data == null) {
                            Slog.w("CompatChangeableApps", "Failed to get package name in package receiver");
                            return;
                        }
                        String schemeSpecificPart = data.getSchemeSpecificPart();
                        String action = intent.getAction();
                        if (action == null) {
                            Slog.w("CompatChangeableApps", "Failed to get action in package receiver");
                            return;
                        }
                        switch (action) {
                            case "android.intent.action.PACKAGE_CHANGED":
                            case "android.intent.action.PACKAGE_REMOVED":
                            case "android.intent.action.PACKAGE_ADDED":
                                CompatChangeableAppsCache compatChangeableAppsCache2 = this.this$0;
                                synchronized (compatChangeableAppsCache2) {
                                    try {
                                        SparseArray sparseArray = compatChangeableAppsCache2.mAppsArray;
                                        if (sparseArray == null) {
                                            return;
                                        }
                                        int size = sparseArray.size();
                                        for (int i = 0; i < size; i++) {
                                            ((CompatChangeableApps) compatChangeableAppsCache2.mAppsArray.valueAt(i)).removeCache(schemeSpecificPart);
                                        }
                                        return;
                                    } finally {
                                    }
                                }
                            default:
                                Slog.w("CompatChangeableApps", "Unsupported action in package receiver: ".concat(action));
                                return;
                        }
                    }
                }, intentFilter, null, null);
                synchronized (compatChangeableAppsCache) {
                    compatChangeableAppsCache.mAppsArray = new SparseArray();
                }
                if (CoreRune.MT_APP_COMPAT_STATUS_LOGGING) {
                    MultiTaskingAppCompatStatusLogger multiTaskingAppCompatStatusLogger = multiTaskingAppCompatController2.mMultiTaskingAppCompatStatusLogger;
                    if (multiTaskingAppCompatStatusLogger == null) {
                        HashSet hashSet = CoreSaStatusLoggingService.sCoreSaStatusLoggers;
                        return;
                    }
                    HashSet hashSet2 = CoreSaStatusLoggingService.sCoreSaStatusLoggers;
                    synchronized (hashSet2) {
                        try {
                            boolean isEmpty = hashSet2.isEmpty();
                            hashSet2.add(multiTaskingAppCompatStatusLogger);
                            if (isEmpty) {
                                CoreSaStatusLoggingService.schedule();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    Slog.d("CoreSaStatusLoggingService", "registerCoreSaStatusLogger logger=MultiTaskingAppCompatStatusLogger");
                }
            }
        });
        this.mService.mH.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ActivityTaskManagerServiceExt activityTaskManagerServiceExt = ActivityTaskManagerServiceExt.this;
                activityTaskManagerServiceExt.getClass();
                final PackageFeatureManagerService packageFeatureManagerService = PackageFeatureManagerService.LazyHolder.sInstance;
                final Context context2 = activityTaskManagerServiceExt.mContext;
                final ActivityTaskManagerService.H h = activityTaskManagerServiceExt.mService.mH;
                PackageFeatureController packageFeatureController = packageFeatureManagerService.mPackageFeatureController;
                CoreLogger coreLogger = packageFeatureManagerService.mLogger;
                packageFeatureController.getClass();
                Uri uri = PackageFeatureSettings.URI_PACKAGE_POLICY_DISABLED;
                boolean z = Settings.Global.getInt(context2.getContentResolver(), "package_policy_disabled", 0) == 1;
                synchronized (packageFeatureController.mLock) {
                    try {
                        if (packageFeatureController.mStarted) {
                            packageFeatureController.mLogger.log(5, "The controller has already been started.", null);
                        } else {
                            packageFeatureController.mContext = context2;
                            packageFeatureController.mHandler = h;
                            packageFeatureController.mLogger = coreLogger;
                            packageFeatureController.mPackageFeatures = new PackageFeatures(context2, h, coreLogger);
                            packageFeatureController.mSettings = new PackageFeatureSettings(context2, h, packageFeatureController, z);
                            h.post(new PackageFeatureController$$ExternalSyntheticLambda0(packageFeatureController, 1));
                            packageFeatureController.mStarted = true;
                        }
                    } finally {
                    }
                }
                PackageFeatureController packageFeatureController2 = packageFeatureManagerService.mPackageFeatureController;
                ScpmController scpmController = packageFeatureManagerService.mScpmController;
                Objects.requireNonNull(scpmController);
                PackageFeatureManagerService$$ExternalSyntheticLambda1 packageFeatureManagerService$$ExternalSyntheticLambda1 = new PackageFeatureManagerService$$ExternalSyntheticLambda1(scpmController);
                synchronized (packageFeatureController2.mLock) {
                    packageFeatureController2.mGetFileDescriptor = packageFeatureManagerService$$ExternalSyntheticLambda1;
                }
                ScpmController scpmController2 = packageFeatureManagerService.mScpmController;
                final Set groupNames = packageFeatureManagerService.mPackageFeatureController.getGroupNames();
                PackageFeatureController packageFeatureController3 = packageFeatureManagerService.mPackageFeatureController;
                CoreLogger coreLogger2 = packageFeatureManagerService.mLogger;
                final ScpmControllerImpl scpmControllerImpl = (ScpmControllerImpl) scpmController2;
                synchronized (scpmControllerImpl.mLock) {
                    try {
                        if (!scpmControllerImpl.mStarted) {
                            scpmControllerImpl.mContext = context2;
                            scpmControllerImpl.mHandler = h;
                            scpmControllerImpl.mCallback = packageFeatureController3;
                            scpmControllerImpl.mLogger = coreLogger2;
                            scpmControllerImpl.mStarted = true;
                            h.post(new Runnable() { // from class: com.samsung.android.server.corescpm.ScpmControllerImpl$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ScpmControllerImpl scpmControllerImpl2 = ScpmControllerImpl.this;
                                    Set set = groupNames;
                                    Context context3 = context2;
                                    Handler handler = h;
                                    scpmControllerImpl2.getClass();
                                    try {
                                        IntentFilter intentFilter = new IntentFilter();
                                        intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
                                        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
                                        Iterator it = set.iterator();
                                        while (it.hasNext()) {
                                            intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE." + ((String) it.next()));
                                        }
                                        context3.registerReceiver(scpmControllerImpl2, intentFilter, 2);
                                        handler.postDelayed(scpmControllerImpl2.mOnLazyBootCompletedTimeout, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                                    } catch (Throwable th) {
                                        th.printStackTrace();
                                    }
                                }
                            });
                        }
                    } finally {
                    }
                }
                synchronized (packageFeatureManagerService) {
                    try {
                        Map map = packageFeatureManagerService.mTmpPackageFeatureCallbacks;
                        if (map == null) {
                            Slog.e("PackageFeature", "The package feature manager service has already been started.");
                        } else {
                            ((HashMap) map).forEach(new BiConsumer() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureManagerService$$ExternalSyntheticLambda2
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    PackageFeatureManagerService packageFeatureManagerService2 = PackageFeatureManagerService.this;
                                    PackageFeature packageFeature = (PackageFeature) obj;
                                    packageFeatureManagerService2.getClass();
                                    Iterator it = ((List) obj2).iterator();
                                    while (it.hasNext()) {
                                        packageFeatureManagerService2.mPackageFeatureController.registerCallback(packageFeature, (PackageFeatureCallback) it.next());
                                    }
                                }
                            });
                            ((HashMap) packageFeatureManagerService.mTmpPackageFeatureCallbacks).clear();
                            packageFeatureManagerService.mTmpPackageFeatureCallbacks = null;
                        }
                    } finally {
                    }
                }
                Iterator it = ((ArrayList) PackagesChange.sAllPackagesChange).iterator();
                while (it.hasNext()) {
                    PackageFeatureUserChange[] packageFeatureUserChangeArr = ((PackagesChange) it.next()).mUserChanges;
                    if (packageFeatureUserChangeArr != null) {
                        for (PackageFeatureUserChange packageFeatureUserChange : packageFeatureUserChangeArr) {
                            packageFeatureUserChange.onLoadFileCompletedAndSystemReady(true, false);
                        }
                    }
                }
            }
        });
        if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            this.mActivityEmbeddedController = new ActivityEmbeddedController(this.mService);
        }
    }

    public final void removeTaskByCmpName() {
        int callingPid = Binder.getCallingPid();
        if (callingPid != Process.myPid()) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(callingPid, "Pid ", " cannot clear task!", "ActivityTaskManagerServiceExt");
            return;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(callingPid, "removeTaskByCmpName:com.android.settings/.password.ChooseLockGeneric$InternalActivity user:0 callerPid:", " reason:Managed profile unavailable", "ActivityTaskManagerServiceExt");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                try {
                    for (int childCount = this.mService.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                        DisplayContent displayContent = (DisplayContent) this.mService.mRootWindowContainer.getChildAt(childCount);
                        if (displayContent == null) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        displayContent.forAllRootTasks(new Consumer() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda5
                            public final /* synthetic */ int f$1 = 0;
                            public final /* synthetic */ String f$2 = "com.android.settings/.password.ChooseLockGeneric$InternalActivity";

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Task task;
                                ActivityTaskManagerServiceExt activityTaskManagerServiceExt = ActivityTaskManagerServiceExt.this;
                                int i = this.f$1;
                                String str = this.f$2;
                                Task task2 = (Task) obj;
                                activityTaskManagerServiceExt.getClass();
                                if (task2.topRunningActivityLocked() != null) {
                                    ArrayList dumpActivitiesLocked = task2.getDumpActivitiesLocked(-1, "all");
                                    for (int size = dumpActivitiesLocked.size() - 1; size >= 0; size--) {
                                        ActivityRecord activityRecord = (ActivityRecord) dumpActivitiesLocked.get(size);
                                        if (activityRecord != null && (task = activityRecord.task) != null && task.mUserId == i && str.equals(activityRecord.shortComponentName)) {
                                            long clearCallingIdentity = Binder.clearCallingIdentity();
                                            try {
                                                activityTaskManagerServiceExt.mService.mTaskSupervisor.removeTask(task, true, true, "remove-task-by-knox");
                                            } finally {
                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean removeTaskWithFlagsLocked(int i, int i2) {
        int i3 = i2 & 8;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if (i3 == 0 && (i2 & 32) == 0) {
            try {
                return activityTaskManagerService.mTaskSupervisor.removeTaskById(i, Binder.getCallingUid(), Binder.getCallingPid(), "remove-task-with-flags", true, (i2 & 16) == 0, CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && (i2 & 128) != 0);
            } finally {
                if ((i2 & 4) != 0) {
                    activityTaskManagerService.mTaskSupervisor.scheduleIdle();
                }
            }
        }
        ArrayList rawTasks = activityTaskManagerService.mRecentTasks.getRawTasks();
        for (int size = rawTasks.size() - 1; size >= 0; size--) {
            if (size != 0 || (i2 & 32) == 0) {
                Task task = (Task) rawTasks.get(size);
                if (!task.isActivityTypeHome() && !task.isActivityTypeRecents()) {
                    if (task.mUserId != activityTaskManagerService.mRootWindowContainer.mCurrentUser) {
                        HashSet hashSet = new HashSet(Arrays.asList((Integer[]) Arrays.stream(activityTaskManagerService.mAmInternal.getCurrentProfileIds()).boxed().toArray(new ActivityTaskManagerServiceExt$$ExternalSyntheticLambda0())));
                        hashSet.add(Integer.valueOf(activityTaskManagerService.mRootWindowContainer.mCurrentUser));
                        if (!hashSet.contains(Integer.valueOf(task.mUserId))) {
                        }
                    }
                    activityTaskManagerService.mTaskSupervisor.removeTaskById(task.mTaskId, Binder.getCallingUid(), Binder.getCallingPid(), "remove-task-by-id", true, (i2 & 16) == 0, false);
                }
            }
        }
        return true;
    }

    public final void resetSmRccOpen(String str) {
        SmRccPolicy smRccPolicy = this.mSmRccPolicy;
        if (smRccPolicy != null) {
            smRccPolicy.resetSmRccOpen(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0023, code lost:
    
        if (android.os.UserHandle.getAppId(r1.uid) == android.os.UserHandle.getAppId(r0)) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAppLockedVerifying(java.lang.String r5, boolean r6) {
        /*
            r4 = this;
            int r0 = android.os.Binder.getCallingUid()
            boolean r1 = isSystemUid(r0)
            if (r1 != 0) goto L43
            java.lang.String r1 = "Exception - isCallerSetSelf:"
            android.content.Context r2 = r4.mContext     // Catch: java.lang.Exception -> L26 java.lang.Throwable -> L37
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch: java.lang.Exception -> L26 java.lang.Throwable -> L37
            r3 = 0
            android.content.pm.ApplicationInfo r1 = r2.getApplicationInfo(r5, r3)     // Catch: java.lang.Exception -> L26 java.lang.Throwable -> L37
            if (r1 == 0) goto L37
            int r1 = r1.uid
            int r1 = android.os.UserHandle.getAppId(r1)
            int r2 = android.os.UserHandle.getAppId(r0)
            if (r1 != r2) goto L37
            goto L43
        L26:
            java.lang.String r4 = "ActivityTaskManagerServiceExt"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L37
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L37
            r5.append(r0)     // Catch: java.lang.Throwable -> L37
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L37
            android.util.Slog.d(r4, r5)     // Catch: java.lang.Throwable -> L37
        L37:
            java.lang.SecurityException r4 = new java.lang.SecurityException
            java.lang.String r5 = " is not system uid or the packageNmae is not itself's"
            java.lang.String r5 = com.android.server.NandswapManager$$ExternalSyntheticOutline0.m(r0, r5)
            r4.<init>(r5)
            throw r4
        L43:
            com.android.server.wm.ActivityTaskManagerService r4 = r4.mService
            com.android.internal.app.AppLockPolicy r4 = r4.mAppLockPolicy
            if (r4 == 0) goto L4c
            r4.setAppLockedVerifying(r5, r6)
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityTaskManagerServiceExt.setAppLockedVerifying(java.lang.String, boolean):void");
    }

    public final void startAppLockActivity(ActivityRecord activityRecord) {
        boolean inMultiWindowMode;
        String str;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        AppLockPolicy appLockPolicy = activityTaskManagerService.mAppLockPolicy;
        if (appLockPolicy == null) {
            return;
        }
        Task task = activityRecord.task;
        int i = activityRecord.mUserId;
        ActivityInfo activityInfo = activityRecord.info;
        String str2 = task.mCallingPackage;
        String str3 = task.mCallingFeatureId;
        int i2 = task.mCallingUid;
        boolean isAppLockedPackage = appLockPolicy != null ? appLockPolicy.isAppLockedPackage(activityRecord.packageName) : false;
        String str4 = activityRecord.packageName;
        AppLockPolicy appLockPolicy2 = activityTaskManagerService.mAppLockPolicy;
        boolean isAppLockedVerifying = appLockPolicy2 != null ? appLockPolicy2.isAppLockedVerifying(str4) : false;
        boolean z = activityInfo == null || !activityTaskManagerService.mAppLockPolicy.isActivityInExceptionList(activityInfo.name);
        if (!isAppLockedPackage || isAppLockedVerifying) {
            return;
        }
        if ((activityRecord.mLaunchingRequestedFromNotification && activityRecord.getDisplayId() != 1) || !z || !activityRecord.isState(ActivityRecord.State.RESUMED)) {
            return;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
        if (task.inFreeformWindowingMode() && task.isDexMode()) {
            inMultiWindowMode = false;
        } else {
            inMultiWindowMode = activityRecord.inMultiWindowMode();
            if (!task.isDexMode()) {
                if (activityTaskManagerService.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
                    atomicBoolean.set(true);
                }
                activityTaskManagerService.mRootWindowContainer.mDefaultDisplay.forAllActivities(new ActivityTaskManagerServiceExt$$ExternalSyntheticLambda1(activityRecord, atomicBoolean2));
            }
        }
        if (SemPersonaManager.isKnoxId(i) || activityTaskManagerService.mAppLockPolicy.isManagedProfileUserId(i)) {
            return;
        }
        if ((SemDualAppManager.isDualAppId(i) && AppLockPolicy.isSupportSSecure()) || inMultiWindowMode) {
            return;
        }
        if (atomicBoolean2.get() || atomicBoolean.get()) {
            Slog.i("ActivityTaskManagerServiceExt", "Start AppLock Service");
            checkAppLockState(activityRecord.intent, activityRecord.canShowWhenLocked(), activityInfo.packageName, false);
            return;
        }
        setAppLockedVerifying(activityRecord.packageName, true);
        AppLockPolicy appLockPolicy3 = activityTaskManagerService.mAppLockPolicy;
        Intent intent = new Intent(appLockPolicy3 != null ? appLockPolicy3.getAppLockedCheckAction() : null);
        intent.addFlags(131072);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", activityInfo.packageName);
        intent.putExtra("LAUNCH_FROM_RESUME", true);
        intent.putExtra("LOCKED_PACKAGE_NAME", activityInfo.packageName);
        intent.putExtra("LOCKED_PACKAGE_USERID", i);
        intent.putExtra("LOCKED_APP_CAN_SHOW_WHEN_LOCKED", activityRecord.canShowWhenLocked());
        intent.setPackage("com.samsung.android.applock");
        ActivityTaskSupervisor activityTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.mContext.getContentResolver());
        if (SemDualAppManager.isDualAppId(i)) {
            i = 0;
        }
        ActivityInfo activityInfo2 = activityTaskSupervisor.resolveIntent(intent, resolveTypeIfNeeded, i, 0, i2, Binder.getCallingPid()).activityInfo;
        intent.setClassName(activityInfo2.packageName, activityInfo2.name);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchTaskId(task.mTaskId);
        makeBasic.setTaskOverlay(true, false);
        try {
            str = "ActivityTaskManagerServiceExt";
            try {
                this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), str2, str3, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), null, null, 0, intent.getFlags(), null, makeBasic.toBundle(), 0, true);
            } catch (Exception e) {
                e = e;
                Log.e(str, "Exception while launching AppLock Confirm Activity for" + activityRecord + ", Exception is : " + e);
            }
        } catch (Exception e2) {
            e = e2;
            str = "ActivityTaskManagerServiceExt";
        }
    }
}
