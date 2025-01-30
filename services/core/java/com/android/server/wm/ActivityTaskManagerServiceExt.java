package com.android.server.wm;

import android.R;
import android.app.ActivityOptions;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.util.Log;
import android.util.Slog;
import com.android.internal.app.AppLockPolicy;
import com.android.internal.app.SmRccPolicy;
import com.android.server.wm.ActivityRecord;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;
import com.samsung.android.server.util.SafetySystemService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class ActivityTaskManagerServiceExt {
    public final String PKG_SYSTEMUI;
    public ActivityEmbeddedController mActivityEmbeddedController;
    public boolean mAvoidCompatDisplayInsets;
    public final Context mContext;
    public final CoreStateController mCoreStateController;
    public CustomAspectRatioController mCustomAspectRatioController;
    public DisplayCutoutController mDisplayCutoutController;
    public FixedAspectRatioController mFixedAspectRatioController;
    public final PackageSpecialManagementList mHighRefreshRateBlockList;
    public final PackageSpecialManagementList mLowRefreshRateList;
    public final PackageSpecialManagementList mNaviAppLowRefreshRateList;
    public OrientationController mOrientationController;
    public final ActivityTaskManagerService mService;
    public final ArrayList mStartedUserIds = new ArrayList();
    public final PackageFeatureManagerService mPackageFeatureManagerService = new PackageFeatureManagerService();
    public int mSysUiPid = -1;
    public SmRccPolicy mSmRccPolicy = null;
    public boolean mAppLockIsInMultiWindowMode = false;
    public WeakHashMap mKeepAliveActivities = new WeakHashMap();
    public AtomicBoolean mHasActivitiesKeptAlive = new AtomicBoolean(false);

    public void removeTaskByCmpName(String str, int i, String str2) {
    }

    public void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) {
        boolean[] zArr;
        if (this.mService.mAppLockPolicy == null) {
            return;
        }
        try {
            zArr = getAppLockLaunchingState(iBinder);
        } catch (Exception e) {
            Log.e("ActivityTaskManagerServiceExt", "exception while querying AppLock Launching State", e);
            zArr = null;
        }
        if (zArr != null) {
            this.mAppLockIsInMultiWindowMode = zArr[0];
            boolean z2 = zArr[1];
            boolean z3 = zArr[2];
            ActivityRecord activityRecord = this.mService.mRootWindowContainer.getActivityRecord(iBinder);
            boolean z4 = activityRecord != null && activityRecord.getDisplayId() == 1;
            boolean z5 = activityRecord != null && activityRecord.isNewDexMode();
            if (this.mService.mAppLockPolicy.isAppLockBypassList(ActivityRecord.forTokenLocked(iBinder).info.name)) {
                return;
            }
            if (this.mService.isKeyguardLocked(0) && z3) {
                return;
            }
            if (z3 || z2 || this.mAppLockIsInMultiWindowMode || z5) {
                int callingUid = Binder.getCallingUid();
                if (SemPersonaManager.isKnoxId(UserHandle.getUserId(callingUid)) || this.mService.mAppLockPolicy.isManagedProfileUserId(UserHandle.getUserId(callingUid))) {
                    return;
                }
                if ((SemDualAppManager.isDualAppId(UserHandle.getUserId(callingUid)) && AppLockPolicy.isSupportSSecure()) || z4) {
                    return;
                }
                checkAppLockState(intent, z, str, z2);
            }
        }
    }

    public final void checkAppLockState(final Intent intent, final boolean z, final String str, boolean z2) {
        try {
            boolean[] appLockLockedVerifyingState = getAppLockLockedVerifyingState(str);
            final int callingUid = Binder.getCallingUid();
            boolean z3 = appLockLockedVerifyingState[0];
            if (z3) {
                boolean z4 = appLockLockedVerifyingState[1];
                Slog.w("ActivityTaskManagerServiceExt", "AppLock checkAppLockState locked:" + z3 + " verifying:" + z4 + " pkgName = " + str + " isInMultiWindowMode:" + this.mAppLockIsInMultiWindowMode + " showWhenLocked:" + z);
                if (!z4 || z) {
                    setAppLockedVerifying(str, true);
                    Runnable runnable = new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityTaskManagerServiceExt.this.lambda$checkAppLockState$0(str, callingUid, z, intent);
                        }
                    };
                    if (z2) {
                        runnable.run();
                    } else {
                        this.mService.f1734mH.post(runnable);
                    }
                }
            }
        } catch (Exception e) {
            Slog.e("ActivityTaskManagerServiceExt", "Exception while checking App Lock locked / verifying state : ", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAppLockState$0(String str, int i, boolean z, Intent intent) {
        Intent intent2 = new Intent("com.samsung.android.intent.action.CHECK_APPLOCK_SERVICE");
        intent2.setPackage("com.samsung.android.applock");
        intent2.putExtra("LAUNCH_FROM_RESUME", true);
        intent2.putExtra("LOCKED_PACKAGE_NAME", str);
        intent2.putExtra("LOCKED_PACKAGE_USERID", UserHandle.getUserId(i));
        intent2.putExtra("LOCKED_APP_CAN_SHOW_WHEN_LOCKED", z);
        if (intent != null) {
            Intent intent3 = new Intent(intent.getAction());
            intent3.setComponent(intent.getComponent());
            intent2.putExtra("LOCKED_PACKAGE_INTENT", intent3);
        }
        try {
            Context context = this.mContext;
            context.startServiceAsUser(intent2, context.getUser());
        } catch (Exception e) {
            Slog.e("ActivityTaskManagerServiceExt", "AppLock service start failed for intent:" + intent2, e);
        }
    }

    public void startAppLockActivity(final ActivityRecord activityRecord) {
        boolean inMultiWindowMode;
        if (this.mService.mAppLockPolicy == null) {
            return;
        }
        Task task = activityRecord.getTask();
        int i = activityRecord.mUserId;
        ActivityInfo activityInfo = activityRecord.info;
        String str = task.mCallingPackage;
        String str2 = task.mCallingFeatureId;
        int i2 = task.mCallingUid;
        boolean isAppLockedPackage = isAppLockedPackage(activityRecord.packageName);
        boolean isAppLockedVerifying = isAppLockedVerifying(activityRecord.packageName);
        boolean z = activityInfo == null || !this.mService.mAppLockPolicy.isActivityInExceptionList(activityInfo.name);
        if (!isAppLockedPackage || isAppLockedVerifying) {
            return;
        }
        if ((!activityRecord.isLaunchRequestedFromNotification() || activityRecord.getDisplayId() == 1) && z && activityRecord.isState(ActivityRecord.State.RESUMED) && !activityRecord.isNewDexMode()) {
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
            if (task.inFreeformWindowingMode() && task.isDexMode()) {
                inMultiWindowMode = false;
            } else {
                inMultiWindowMode = activityRecord.getTask().inMultiWindowMode();
                if (!task.isDexMode()) {
                    if (this.mService.mRootWindowContainer.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
                        atomicBoolean.set(true);
                    }
                    this.mService.mRootWindowContainer.getDefaultDisplay().forAllActivities(new Consumer() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ActivityTaskManagerServiceExt.lambda$startAppLockActivity$1(ActivityRecord.this, atomicBoolean2, (ActivityRecord) obj);
                        }
                    });
                }
            }
            if (SemPersonaManager.isKnoxId(i) || this.mService.mAppLockPolicy.isManagedProfileUserId(i)) {
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
            Intent intent = new Intent(getAppLockedCheckAction());
            intent.addFlags(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES);
            intent.putExtra("android.intent.extra.PACKAGE_NAME", activityInfo.packageName);
            intent.putExtra("LAUNCH_FROM_RESUME", true);
            intent.putExtra("LOCKED_PACKAGE_NAME", activityInfo.packageName);
            intent.putExtra("LOCKED_PACKAGE_USERID", i);
            intent.putExtra("LOCKED_APP_CAN_SHOW_WHEN_LOCKED", activityRecord.canShowWhenLocked());
            intent.setPackage("com.samsung.android.applock");
            ActivityTaskSupervisor activityTaskSupervisor = this.mService.mTaskSupervisor;
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
                this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), str, str2, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), null, null, 0, intent.getFlags(), null, makeBasic.toBundle(), 0);
            } catch (Exception e) {
                Log.e("ActivityTaskManagerServiceExt", "Exception while launching AppLock Confirm Activity for" + activityRecord + ", Exception is : " + e);
            }
        }
    }

    public static /* synthetic */ void lambda$startAppLockActivity$1(ActivityRecord activityRecord, AtomicBoolean atomicBoolean, ActivityRecord activityRecord2) {
        if (activityRecord2.getTask().inMultiWindowMode() && activityRecord2.packageName.equals(activityRecord.packageName) && !activityRecord2.equals(activityRecord)) {
            atomicBoolean.set(true);
        }
    }

    public List getAppLockedPackageList() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getAppLockedPackageList();
        }
        return null;
    }

    public void setAppLockedUnLockPackage(String str) {
        int callingUid = Binder.getCallingUid();
        if (!isSystemUid(callingUid)) {
            throw new SecurityException(callingUid + " cannot call setAppLockedUnLockPackage(" + str + ")");
        }
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            appLockPolicy.setAppLockedUnLockPackage(str);
        }
    }

    public boolean isAppLockedPackage(String str) {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.isAppLockedPackage(str);
        }
        return false;
    }

    public void clearAppLockedUnLockedApp() {
        int callingUid = Binder.getCallingUid();
        if (!isSystemUid(callingUid)) {
            throw new SecurityException(callingUid + " cannot call clearAppLockedUnLockedApp()");
        }
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            appLockPolicy.clearAppLockedUnLockedApp();
        }
    }

    public String getAppLockedLockType() {
        int callingUid = Binder.getCallingUid();
        if (!isSystemUid(callingUid)) {
            throw new SecurityException(callingUid + " cannot call getAppLockedLockType()");
        }
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getAppLockedLockType();
        }
        return null;
    }

    public String getAppLockedCheckAction() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getAppLockedCheckAction();
        }
        return null;
    }

    public void setAppLockedVerifying(String str, boolean z) {
        int callingUid = Binder.getCallingUid();
        if (!isSystemUid(callingUid) && !isCallerSetSelf(callingUid, str)) {
            throw new SecurityException(callingUid + " is not system uid or the packageNmae is not itself's");
        }
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            appLockPolicy.setAppLockedVerifying(str, z);
        }
    }

    public boolean isAppLockedVerifying(String str) {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.isAppLockedVerifying(str);
        }
        return false;
    }

    public void setApplockLockedAppsPackage(String str) {
        this.mService.mAppLockPolicy.setApplockLockedAppsPackage(str);
    }

    public void setApplockLockedAppsClass(String str) {
        this.mService.mAppLockPolicy.setApplockLockedAppsClass(str);
    }

    public void setApplockType(int i) {
        this.mService.mAppLockPolicy.setApplockType(i);
    }

    public void setApplockEnabled(boolean z) {
        this.mService.mAppLockPolicy.setApplockEnabled(z);
    }

    public void setSsecureHiddenAppsPackages(String str) {
        this.mService.mAppLockPolicy.setSsecureHiddenAppsPackages(str);
    }

    public String getApplockLockedAppsPackage() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getApplockLockedAppsPackage();
        }
        return null;
    }

    public String getApplockLockedAppsClass() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getApplockLockedAppsClass();
        }
        return null;
    }

    public int getApplockType() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getApplockType();
        }
        return 0;
    }

    public boolean isApplockEnabled() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.isApplockEnabled();
        }
        return false;
    }

    public String getSsecureHiddenAppsPackages() {
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            return appLockPolicy.getSsecureHiddenAppsPackages();
        }
        return null;
    }

    public final boolean isCallerSetSelf(int i, String str) {
        try {
            try {
                ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 0);
                return applicationInfo != null && UserHandle.getAppId(applicationInfo.uid) == UserHandle.getAppId(i);
            } catch (Exception unused) {
                Slog.d("ActivityTaskManagerServiceExt", "Exception - isCallerSetSelf:" + i);
                return false;
            }
        } catch (Throwable unused2) {
            return false;
        }
    }

    public final boolean isSystemUid(int i) {
        return UserHandle.getAppId(i) == 1000 || i == 0;
    }

    public boolean[] getAppLockLaunchingState(IBinder iBinder) {
        boolean[] zArr = new boolean[3];
        synchronized (this) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    throw new IllegalArgumentException("AppLockLaunchingFromActivity: No activity record matching token=" + iBinder);
                }
                if (forTokenLocked.isLaunchRequestedFromNotification()) {
                    forTokenLocked.setLaunchingRequestFromNotification(false);
                    zArr[1] = true;
                } else {
                    zArr[1] = false;
                }
                if (this.mService.mAppLockPolicy.isActivityInExceptionList(forTokenLocked.info.name)) {
                    zArr[2] = true;
                } else {
                    zArr[2] = false;
                }
                Task task = forTokenLocked.getTask();
                if (task != null && task.inFreeformWindowingMode() && task.isDexMode()) {
                    zArr[0] = false;
                } else {
                    zArr[0] = forTokenLocked.getTask().inMultiWindowMode();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return zArr;
    }

    public boolean[] getAppLockLockedVerifyingState(String str) {
        boolean[] zArr = new boolean[2];
        AppLockPolicy appLockPolicy = this.mService.mAppLockPolicy;
        if (appLockPolicy != null) {
            zArr[0] = appLockPolicy.isAppLockedPackage(str);
            zArr[1] = this.mService.mAppLockPolicy.isAppLockedVerifying(str);
        }
        return zArr;
    }

    public ActivityTaskManagerServiceExt(Context context, ActivityTaskManagerService activityTaskManagerService) {
        this.mNaviAppLowRefreshRateList = CoreRune.FW_VRR_NAVIGATION_LOW_REFRESH_RATE ? new RefreshRateBlockList(PackageFeature.NAVIGATION_LOW_REFRESH_RATE) : null;
        this.mLowRefreshRateList = CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST ? new RefreshRateBlockList(PackageFeature.LOW_REFRESH_RATE) : null;
        this.mHighRefreshRateBlockList = CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST ? new RefreshRateBlockList(PackageFeature.HIGH_REFRESH_RATE) : null;
        this.mContext = context;
        this.mService = activityTaskManagerService;
        this.mCoreStateController = new CoreStateController(activityTaskManagerService);
        this.PKG_SYSTEMUI = context.getString(R.string.config_systemUi);
        SafetySystemService.registerForSystemReady(new SafetySystemService.Callback() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda1
            @Override // com.samsung.android.server.util.SafetySystemService.Callback
            public final void onSystemReady(ActivityTaskManagerService activityTaskManagerService2) {
                ActivityTaskManagerServiceExt.lambda$new$2(activityTaskManagerService2);
            }
        });
    }

    public static /* synthetic */ void lambda$new$2(ActivityTaskManagerService activityTaskManagerService) {
        if (CoreRune.FW_BOUNDS_COMPAT_STATUS_LOGGING) {
            BoundsCompatStatusLogger.get().onSystemReady(activityTaskManagerService);
        }
    }

    public void onSystemReady() {
        if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            this.mActivityEmbeddedController = new ActivityEmbeddedController(this.mService);
        }
        boolean z = CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED;
    }

    public void initialize() {
        if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE) {
            this.mFixedAspectRatioController = new FixedAspectRatioController(this.mService);
        }
        this.mDisplayCutoutController = new DisplayCutoutController(this.mService);
        this.mCustomAspectRatioController = new CustomAspectRatioController(this.mService);
        new CustomAspectRatioLegacyController(this.mService);
        if (CoreRune.FW_ORIENTATION_CONTROL) {
            this.mOrientationController = new OrientationController(this.mService);
        }
        if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL) {
            BoundsCompatAlignmentController.setAtmService(this.mService);
        }
    }

    public boolean removeTaskWithFlagsLocked(int i, int i2) {
        if ((i2 & 8) != 0 || (i2 & 32) != 0) {
            return removeAllRecentTasksLocked(i2);
        }
        try {
            return this.mService.mTaskSupervisor.removeTaskById(i, true, (i2 & 16) == 0, CoreRune.MW_FREEFORM_DISMISS_VIEW && (i2 & 128) != 0, "remove-task-with-flags", Binder.getCallingUid());
        } finally {
            if ((i2 & 4) != 0) {
                this.mService.mTaskSupervisor.scheduleIdle();
            }
        }
    }

    public final boolean removeAllRecentTasksLocked(int i) {
        ArrayList rawTasks = this.mService.getRecentTasks().getRawTasks();
        for (int size = rawTasks.size() - 1; size >= 0; size--) {
            if (size != 0 || (i & 32) == 0) {
                Task task = (Task) rawTasks.get(size);
                if (!task.isActivityTypeHome() && !task.isActivityTypeRecents()) {
                    removeTaskByIdIfNeededLocked(task, i);
                }
            }
        }
        return true;
    }

    public final void removeTaskByIdIfNeededLocked(Task task, int i) {
        if (task.mUserId != this.mService.mRootWindowContainer.mCurrentUser) {
            HashSet hashSet = new HashSet(Arrays.asList((Integer[]) Arrays.stream(this.mService.mAmInternal.getCurrentProfileIds()).boxed().toArray(new IntFunction() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda3
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    Integer[] lambda$removeTaskByIdIfNeededLocked$3;
                    lambda$removeTaskByIdIfNeededLocked$3 = ActivityTaskManagerServiceExt.lambda$removeTaskByIdIfNeededLocked$3(i2);
                    return lambda$removeTaskByIdIfNeededLocked$3;
                }
            })));
            hashSet.add(Integer.valueOf(this.mService.mRootWindowContainer.mCurrentUser));
            if (!hashSet.contains(Integer.valueOf(task.mUserId))) {
                return;
            }
        }
        this.mService.mTaskSupervisor.removeTaskById(task.mTaskId, true, (i & 16) == 0, "remove-task-by-id", Binder.getCallingUid());
    }

    public static /* synthetic */ Integer[] lambda$removeTaskByIdIfNeededLocked$3(int i) {
        return new Integer[i];
    }

    public final void keepAliveActivityLocked(Task task, boolean z) {
        ActivityRecord activityRecord = task.topRunningActivityLocked();
        if (activityRecord != null) {
            if (z) {
                if (this.mKeepAliveActivities.size() < 2) {
                    this.mKeepAliveActivities.put(activityRecord, Boolean.TRUE);
                    this.mHasActivitiesKeptAlive.compareAndSet(false, true);
                    return;
                } else {
                    Slog.w("ActivityTaskManagerServiceExt", "Max count exceeded! Cannot set keepAlive for taskId=" + task.mTaskId);
                    return;
                }
            }
            resetActivityKeepAliveLocked(activityRecord);
        }
    }

    public boolean moveTaskToBack(int i, boolean z, Bundle bundle) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i);
                boolean z2 = false;
                if (anyTaskForId == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (this.mService.getLockTaskController().isLockTaskModeViolation(anyTaskForId)) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                keepAliveActivityLocked(anyTaskForId, z);
                Task rootTask = anyTaskForId.getRootTask();
                if (rootTask != null && rootTask.moveTaskToBack(anyTaskForId, bundle)) {
                    z2 = true;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return z2;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean isKeepAliveActivity(ActivityRecord activityRecord) {
        return this.mKeepAliveActivities.get(activityRecord) != null;
    }

    public void resetActivityKeepAliveLocked(ActivityRecord activityRecord) {
        this.mKeepAliveActivities.remove(activityRecord);
        if (this.mKeepAliveActivities.size() == 0) {
            this.mHasActivitiesKeptAlive.compareAndSet(true, false);
        }
    }

    public boolean hasKeepAliveActivities(WindowProcessController windowProcessController) {
        if (!this.mHasActivitiesKeptAlive.get()) {
            return false;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Iterator it = this.mKeepAliveActivities.keySet().iterator();
                while (it.hasNext()) {
                    if (windowProcessController.equals(((ActivityRecord) it.next()).app)) {
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

    public boolean checkScreenDpSizeChanges(Configuration configuration, Configuration configuration2) {
        int i;
        int i2 = configuration2.screenWidthDp;
        return ((i2 == 0 || configuration.screenWidthDp == i2) && ((i = configuration2.screenHeightDp) == 0 || configuration.screenHeightDp == i)) ? false : true;
    }

    class RefreshRateBlockList extends PackageSpecialManagementList {
        public RefreshRateBlockList(PackageFeature packageFeature) {
            super(packageFeature);
        }

        @Override // com.samsung.android.server.packagefeature.util.PackageSpecialManagementList, com.samsung.android.server.packagefeature.PackageFeatureCallback
        public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
            super.onPackageFeatureDataChanged(packageFeatureData);
            ActivityTaskManagerServiceExt.this.mService.mWindowManager.requestTraversal();
        }
    }

    public CoreStateController getCoreStateController() {
        return this.mCoreStateController;
    }

    public void installSystemProvidersLocked() {
        this.mStartedUserIds.add(0);
        this.mCoreStateController.initializeLocked();
        this.mService.mMultiWindowEnableController.initializeLocked(0);
    }

    public void startUser(final int i, final boolean z, final boolean z2) {
        this.mService.f1734mH.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ActivityTaskManagerServiceExt.this.lambda$startUser$4(i, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUser$4(int i, boolean z, boolean z2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mStartedUserIds.contains(Integer.valueOf(i))) {
                    this.mStartedUserIds.add(Integer.valueOf(i));
                }
                this.mCoreStateController.startUserLocked(i, z, z2);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void stopUser(final int i, final boolean z) {
        this.mService.f1734mH.post(new Runnable() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ActivityTaskManagerServiceExt.this.lambda$stopUser$5(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopUser$5(int i, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mStartedUserIds.remove(Integer.valueOf(i));
                this.mService.mMultiWindowEnableController.stopUserLocked(i, z);
                this.mCoreStateController.stopUserLocked(i, z);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public ArrayList getStartedUserIdsLocked() {
        return new ArrayList(this.mStartedUserIds);
    }

    public void onLockTaskStateChanged(int i) {
        boolean z = i != 0;
        if (CoreRune.FW_SUPPORT_LOCK_TASK_MODE_BROADCAST) {
            Intent intent = new Intent("com.kddi.agent.action.SCREEN_PINNING_CONDITION");
            intent.putExtra("status", z);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        }
        Intent intent2 = new Intent("com.samsung.android.action.LOCK_TASK_MODE");
        intent2.putExtra("enable", z);
        this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, "com.samsung.android.permission.LOCK_TASK_MODE");
    }

    public int getForegroundTaskId(int i) {
        Task task;
        ActivityRecord activity;
        DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
        int i2 = -1;
        if (displayContent == null || (activity = displayContent.getActivity(new Predicate() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isValidActivityForForeground;
                isValidActivityForForeground = ActivityTaskManagerServiceExt.isValidActivityForForeground((ActivityRecord) obj);
                return isValidActivityForForeground;
            }
        })) == null) {
            task = null;
        } else {
            task = activity.getTask();
            if (task != null) {
                i2 = task.mTaskId;
            }
        }
        if (DesktopModeFeature.DEBUG) {
            Log.i("ActivityTaskManagerServiceExt", "getForegroundTaskId(), displayId=" + i + ", taskId=" + i2 + ", task=" + task);
        }
        return i2;
    }

    public static boolean isValidActivityForForeground(ActivityRecord activityRecord) {
        Bundle bundle;
        if (activityRecord.finishing || activityRecord.isTaskOverlay()) {
            return false;
        }
        if (!activityRecord.isActivityTypeStandard() && !activityRecord.isActivityTypeHome()) {
            return false;
        }
        ActivityInfo activityInfo = activityRecord.info;
        return activityInfo == null || (bundle = activityInfo.metaData) == null || !bundle.getBoolean("com.samsung.android.dex.autoopenlastapp.ignore", false);
    }

    public ComponentName getRealActivityForTaskId(int i) {
        Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(i);
        if (anyTaskForId == null) {
            return null;
        }
        return anyTaskForId.realActivity;
    }

    public void clearHomeStack(int i) {
        final String defaultHomePackageName = i == 0 ? getDefaultHomePackageName() : null;
        if (DesktopModeFeature.DEBUG) {
            Log.d("ActivityTaskManagerServiceExt", "clearHomeStack(), displayId=" + i + ", defaultHomePkgName=" + defaultHomePackageName);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ActivityTaskManagerServiceExt.this.lambda$clearHomeStack$6(defaultHomePackageName, (Task) obj);
                        }
                    }, true);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearHomeStack$6(String str, Task task) {
        if ((task.isActivityTypeHome() || task.isActivityTypeRecents()) && !task.isRootTask()) {
            ComponentName componentName = task.realActivity;
            if (componentName == null || !componentName.getPackageName().equals(str)) {
                if (DesktopModeFeature.DEBUG) {
                    Log.d("ActivityTaskManagerServiceExt", "clearHomeStack(), removing task=" + task);
                }
                this.mService.mTaskSupervisor.removeTask(task, task.isActivityTypeRecents(), true, "ActivityTaskManagerServiceExt#clearHomeStack");
            }
        }
    }

    public final String getDefaultHomePackageName() {
        List roleHoldersAsUser = ((RoleManager) this.mContext.getSystemService("role")).getRoleHoldersAsUser("android.app.role.HOME", Process.myUserHandle());
        if (roleHoldersAsUser.isEmpty()) {
            return null;
        }
        return (String) roleHoldersAsUser.get(0);
    }

    public boolean hasPackageTask(int i, final String str, final int i2) {
        DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
        return (displayContent == null || displayContent.getTask(new Predicate() { // from class: com.android.server.wm.ActivityTaskManagerServiceExt$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$hasPackageTask$7;
                lambda$hasPackageTask$7 = ActivityTaskManagerServiceExt.lambda$hasPackageTask$7(i2, str, (Task) obj);
                return lambda$hasPackageTask$7;
            }
        }, true) == null) ? false : true;
    }

    public static /* synthetic */ boolean lambda$hasPackageTask$7(int i, String str, Task task) {
        ComponentName componentName;
        return task.getActivityType() == i && (componentName = task.realActivity) != null && componentName.getPackageName().equals(str);
    }

    public void boostPriority(ActivityRecord activityRecord) {
        WindowProcessController windowProcessController = activityRecord.app;
        if (windowProcessController == null || windowProcessController.getThread() == null) {
            return;
        }
        try {
            Process.setThreadPriority(activityRecord.app.getPid(), -10);
            activityRecord.app.getThread().setProcessState(2);
            activityRecord.app.setCurrentSchedulingGroup(3);
        } catch (Exception unused) {
        }
    }

    public int getSysUiPid() {
        return this.mSysUiPid;
    }

    public void setSystemUiPidIfNeed(WindowProcessController windowProcessController) {
        String str;
        if (windowProcessController.mUserId == 0 && (str = this.PKG_SYSTEMUI) != null && str.equals(windowProcessController.mName)) {
            Slog.d("ActivityTaskManagerServiceExt", "setSystemUiPidIfNeed : " + windowProcessController.getPid());
            this.mSysUiPid = windowProcessController.getPid();
        }
    }

    public void onUserUnlocked() {
        Slog.i("ActivityTaskManagerServiceExt", "SmRcc onUserUnlocked loadData");
        SmRccPolicy smRccPolicy = SmRccPolicy.getInstance(this.mContext);
        this.mSmRccPolicy = smRccPolicy;
        smRccPolicy.loadData();
    }

    public boolean isSmRccPkg(String str) {
        SmRccPolicy smRccPolicy = this.mSmRccPolicy;
        if (smRccPolicy != null) {
            return smRccPolicy.isSmRccPkg(str);
        }
        return false;
    }

    public boolean isSmRccOpen(String str) {
        SmRccPolicy smRccPolicy = this.mSmRccPolicy;
        if (smRccPolicy != null) {
            return smRccPolicy.isSmRccOpen(str);
        }
        return false;
    }

    public void resetSmRccOpen(String str) {
        SmRccPolicy smRccPolicy = this.mSmRccPolicy;
        if (smRccPolicy != null) {
            smRccPolicy.resetSmRccOpen(str);
        }
    }

    public String getSmRccAction() {
        SmRccPolicy smRccPolicy = this.mSmRccPolicy;
        if (smRccPolicy != null) {
            return smRccPolicy.getSmRccAction();
        }
        return null;
    }
}
