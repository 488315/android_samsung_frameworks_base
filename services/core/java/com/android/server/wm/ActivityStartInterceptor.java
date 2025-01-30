package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.BlockedAppActivity;
import com.android.internal.app.HarmfulAppWarningActivity;
import com.android.internal.app.SuspendedAppActivity;
import com.android.internal.app.UnlaunchableAppActivity;
import com.android.server.LocalServices;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ActivityStartInterceptor {
  public ActivityInfo mAInfo;
  public ActivityOptions mActivityOptions;
  public String mCallingFeatureId;
  public String mCallingPackage;
  public int mCallingPid;
  public int mCallingUid;
  public Task mInTask;
  public TaskFragment mInTaskFragment;
  public Intent mIntent;
  public boolean mIsInterceptedForAliasActivity;
  public boolean mIsInterceptedForCarLife;
  public PersonaManagerInternal mPersonaManagerInternal;
  public ResolveInfo mRInfo;
  public int mRealCallingPid;
  public int mRealCallingUid;
  public String mResolvedType;
  public final RootWindowContainer mRootWindowContainer;
  public final ActivityTaskManagerService mService;
  public final Context mServiceContext;
  public ActivityRecord mSourceRecord;
  public int mStartFlags;
  public final ActivityTaskSupervisor mSupervisor;
  public int mUserId;
  public UserManager mUserManager;

  public ActivityStartInterceptor(
      ActivityTaskManagerService activityTaskManagerService,
      ActivityTaskSupervisor activityTaskSupervisor) {
    this(
        activityTaskManagerService,
        activityTaskSupervisor,
        activityTaskManagerService.mRootWindowContainer,
        activityTaskManagerService.mContext);
  }

  public ActivityStartInterceptor(
      ActivityTaskManagerService activityTaskManagerService,
      ActivityTaskSupervisor activityTaskSupervisor,
      RootWindowContainer rootWindowContainer,
      Context context) {
    this.mService = activityTaskManagerService;
    this.mSupervisor = activityTaskSupervisor;
    this.mRootWindowContainer = rootWindowContainer;
    this.mServiceContext = context;
  }

  public void setStates(
      int i, int i2, int i3, int i4, String str, String str2, ActivityRecord activityRecord) {
    this.mRealCallingPid = i2;
    this.mRealCallingUid = i3;
    this.mUserId = i;
    this.mStartFlags = i4;
    this.mCallingPackage = str;
    this.mCallingFeatureId = str2;
    this.mSourceRecord = activityRecord;
    this.mIsInterceptedForAliasActivity = false;
  }

  public boolean isInterceptedForCarLife() {
    return this.mIsInterceptedForCarLife;
  }

  public void setInterceptedForCarLife(boolean z) {
    this.mIsInterceptedForCarLife = z;
  }

  public boolean interceptCarlifeHomeIfNeeded() {
    if (this.mIntent == null || !hasCarLifeDisplay()) {
      return false;
    }
    int launchDisplayId = getLaunchDisplayId();
    boolean isBackToCarlifeHomePage = isBackToCarlifeHomePage();
    boolean isCarLifeNeedRestartApp = isCarLifeNeedRestartApp(launchDisplayId);
    if (!isBackToCarlifeHomePage && !isCarLifeNeedRestartApp) {
      return false;
    }
    Intent createCarlifeHomeIntent =
        createCarlifeHomeIntent(this.mUserId, this.mAInfo.applicationInfo.packageName);
    ResolveInfo resolveIntent =
        this.mSupervisor.resolveIntent(
            createCarlifeHomeIntent,
            this.mResolvedType,
            this.mUserId,
            0,
            this.mRealCallingUid,
            this.mRealCallingPid);
    ActivityInfo resolveActivity =
        this.mSupervisor.resolveActivity(
            createCarlifeHomeIntent, resolveIntent, this.mStartFlags, null);
    if (resolveIntent == null || resolveActivity == null) {
      return false;
    }
    this.mCallingPid = this.mRealCallingPid;
    this.mCallingUid = this.mRealCallingUid;
    this.mResolvedType = null;
    createCarlifeHomeIntent.putExtra("orginal_intent", this.mIntent);
    createCarlifeHomeIntent.putExtra("launch_display_id", launchDisplayId);
    createCarlifeHomeIntent.putExtra("is_back_to_home", isBackToCarlifeHomePage);
    createCarlifeHomeIntent.putExtra("is_restart_app", isCarLifeNeedRestartApp);
    this.mIntent = createCarlifeHomeIntent;
    this.mRInfo = resolveIntent;
    this.mAInfo = resolveActivity;
    if (this.mActivityOptions == null) {
      this.mActivityOptions = ActivityOptions.makeBasic();
    }
    this.mActivityOptions.setLaunchDisplayId(0);
    this.mIsInterceptedForCarLife = true;
    Slog.w("ActivityTaskManager", "Start CarLifeStartInterceptActivity and handler intent");
    return true;
  }

  public boolean hasCarLifeDisplay() {
    for (int childCount = this.mService.mRootWindowContainer.getChildCount() - 1;
        childCount >= 0;
        childCount--) {
      if (((DisplayContent) this.mService.mRootWindowContainer.getChildAt(childCount))
          .isCarLifeDisplay()) {
        return true;
      }
    }
    return false;
  }

  public final boolean isBackToCarlifeHomePage() {
    DisplayContent displayContent;
    ActivityRecord activityRecord = this.mSourceRecord;
    return activityRecord != null
        && (displayContent = activityRecord.getDisplayContent()) != null
        && displayContent.isCarLifeDisplay()
        && this.mIntent.hasCategory("android.intent.category.HOME")
        && "android.intent.action.MAIN".equals(this.mIntent.getAction());
  }

  public final int getLaunchDisplayId() {
    ActivityOptions activityOptions = this.mActivityOptions;
    if (activityOptions != null && activityOptions.getLaunchDisplayId() != -1) {
      return this.mActivityOptions.getLaunchDisplayId();
    }
    ActivityRecord activityRecord = this.mSourceRecord;
    if (activityRecord != null) {
      return activityRecord.getDisplayId();
    }
    return 0;
  }

  public final boolean isCarLifeNeedRestartApp(int i) {
    ApplicationInfo applicationInfo;
    ActivityInfo activityInfo = this.mAInfo;
    if ((activityInfo != null
            && (applicationInfo = activityInfo.applicationInfo) != null
            && applicationInfo.uid == this.mRealCallingUid)
        || this.mIntent.getBooleanExtra("is_skip_intercept", false)) {
      return false;
    }
    if (isPackageInCarlifeAndLaunchInOther(i) || isPackageInDefaultAndLaunchInCarLife(i)) {
      return true;
    }
    return "com.baidu.carlife".equals(this.mAInfo.applicationInfo.packageName)
        && this.mIntent.hasCategory("android.intent.category.LAUNCHER")
        && "android.intent.action.MAIN".equals(this.mIntent.getAction());
  }

  public final boolean isPackageInCarlifeAndLaunchInOther(final int i) {
    final ArrayList arrayList = new ArrayList();
    if (getActualDisplayContent(i).isCarLifeDisplay()) {
      return false;
    }
    for (int childCount = this.mService.mRootWindowContainer.getChildCount() - 1;
        childCount >= 0;
        childCount--) {
      DisplayContent displayContent =
          (DisplayContent) this.mService.mRootWindowContainer.getChildAt(childCount);
      if (displayContent.isCarLifeDisplay()) {
        displayContent.forAllTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActivityStartInterceptor.this.lambda$isPackageInCarlifeAndLaunchInOther$0(
                    i, arrayList, (Task) obj);
              }
            });
      }
      if (arrayList.size() > 0) {
        return true;
      }
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$isPackageInCarlifeAndLaunchInOther$0(
      int i, ArrayList arrayList, Task task) {
    ActivityInfo activityInfo = this.mAInfo;
    if (activityInfo == null
        || activityInfo.applicationInfo == null
        || task == null
        || task.realActivity == null) {
      return;
    }
    Slog.i(
        "ActivityTaskManager",
        "isPackageInCarlifeAndLaunchInOther, start package: "
            + this.mAInfo.applicationInfo.packageName
            + ", task: "
            + task.realActivity.getPackageName()
            + ", launchDisplayId: "
            + i
            + ", isCarLifeDisplay: "
            + getActualDisplayContent(i).isCarLifeDisplay());
    if (this.mAInfo.applicationInfo.packageName.contains(task.realActivity.getPackageName())) {
      arrayList.add(task);
    }
  }

  public final boolean isPackageInDefaultAndLaunchInCarLife(final int i) {
    final ArrayList arrayList = new ArrayList();
    if (!getActualDisplayContent(i).isCarLifeDisplay()) {
      return false;
    }
    for (int childCount = this.mService.mRootWindowContainer.getChildCount() - 1;
        childCount >= 0;
        childCount--) {
      DisplayContent displayContent =
          (DisplayContent) this.mService.mRootWindowContainer.getChildAt(childCount);
      if (!displayContent.isCarLifeDisplay()) {
        displayContent.forAllTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ActivityStartInterceptor.this.lambda$isPackageInDefaultAndLaunchInCarLife$1(
                    i, arrayList, (Task) obj);
              }
            });
      }
      if (arrayList.size() > 0) {
        return true;
      }
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$isPackageInDefaultAndLaunchInCarLife$1(
      int i, ArrayList arrayList, Task task) {
    ActivityInfo activityInfo = this.mAInfo;
    if (activityInfo == null
        || activityInfo.applicationInfo == null
        || task == null
        || task.realActivity == null) {
      return;
    }
    Slog.i(
        "ActivityTaskManager",
        "isPackageInDefaultAndLaunchInCarLife, start package: "
            + this.mAInfo.applicationInfo.packageName
            + ", task: "
            + task.realActivity.getPackageName()
            + ", launchDisplayId: "
            + i
            + ", isCarLifeDisplay: "
            + getActualDisplayContent(i).isCarLifeDisplay());
    if (this.mAInfo.applicationInfo.packageName.contains(task.realActivity.getPackageName())) {
      arrayList.add(task);
    }
  }

  public final Intent createCarlifeHomeIntent(int i, String str) {
    Intent intent = new Intent("com.samsung.android.carlink.action.HOME");
    intent.setFlags(276824064);
    intent.setClassName(
        "com.samsung.android.carlink",
        "com.samsung.android.carlink.common.StartInterceptorActivity");
    intent.putExtra("android.intent.extra.USER_ID", i);
    intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
    intent.putExtra("orginal_intent", this.mIntent);
    return intent;
  }

  public final IntentSender createIntentSenderForOriginalIntent(int i, int i2) {
    ActivityOptions makeBasic;
    Bundle deferCrossProfileAppsAnimationIfNecessary = deferCrossProfileAppsAnimationIfNecessary();
    TaskFragment launchTaskFragment = getLaunchTaskFragment();
    if (launchTaskFragment != null) {
      if (deferCrossProfileAppsAnimationIfNecessary != null) {
        makeBasic = ActivityOptions.fromBundle(deferCrossProfileAppsAnimationIfNecessary);
      } else {
        makeBasic = ActivityOptions.makeBasic();
      }
      makeBasic.setLaunchTaskFragmentToken(launchTaskFragment.getFragmentToken());
      deferCrossProfileAppsAnimationIfNecessary = makeBasic.toBundle();
    }
    return new IntentSender(
        this.mService.getIntentSenderLocked(
            2,
            this.mCallingPackage,
            this.mCallingFeatureId,
            i,
            this.mUserId,
            null,
            null,
            0,
            new Intent[] {this.mIntent},
            new String[] {this.mResolvedType},
            i2,
            deferCrossProfileAppsAnimationIfNecessary));
  }

  public final TaskFragment getLaunchTaskFragment() {
    IBinder launchTaskFragmentToken;
    TaskFragment taskFragment = this.mInTaskFragment;
    if (taskFragment != null) {
      return taskFragment;
    }
    ActivityOptions activityOptions = this.mActivityOptions;
    if (activityOptions == null
        || (launchTaskFragmentToken = activityOptions.getLaunchTaskFragmentToken()) == null) {
      return null;
    }
    return TaskFragment.fromTaskFragmentToken(launchTaskFragmentToken, this.mService);
  }

  public boolean intercept(
      Intent intent,
      ResolveInfo resolveInfo,
      ActivityInfo activityInfo,
      String str,
      Task task,
      TaskFragment taskFragment,
      int i,
      int i2,
      ActivityOptions activityOptions) {
    ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch;
    this.mUserManager = UserManager.get(this.mServiceContext);
    this.mIntent = intent;
    this.mCallingPid = i;
    this.mCallingUid = i2;
    this.mRInfo = resolveInfo;
    this.mAInfo = activityInfo;
    this.mResolvedType = str;
    this.mInTask = task;
    this.mInTaskFragment = taskFragment;
    this.mActivityOptions = activityOptions;
    if (interceptQuietProfileIfNeeded()
        || interceptSuspendedPackageIfNeeded()
        || interceptLockTaskModeViolationPackageIfNeeded()
        || interceptHarmfulAppIfNeeded()) {
      return true;
    }
    if (CoreRune.BAIDU_CARLIFE && interceptCarlifeHomeIfNeeded()) {
      return true;
    }
    if ((getPersonaManagerInternal().isKnoxId(this.mUserId) && interceptSuperLockIfNeeded())
        || interceptLockedManagedProfileIfNeeded()) {
      return true;
    }
    SparseArray activityInterceptorCallbacks = this.mService.getActivityInterceptorCallbacks();
    ActivityInterceptorCallback.ActivityInterceptorInfo interceptorInfo = getInterceptorInfo(null);
    for (int i3 = 0; i3 < activityInterceptorCallbacks.size(); i3++) {
      if (shouldInterceptActivityLaunch(activityInterceptorCallbacks.keyAt(i3), interceptorInfo)
          && (onInterceptActivityLaunch =
                  ((ActivityInterceptorCallback) activityInterceptorCallbacks.valueAt(i3))
                      .onInterceptActivityLaunch(interceptorInfo))
              != null) {
        this.mIntent = onInterceptActivityLaunch.getIntent();
        this.mActivityOptions = onInterceptActivityLaunch.getActivityOptions();
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        if (onInterceptActivityLaunch.isActivityResolved()) {
          return true;
        }
        ResolveInfo resolveIntent =
            this.mSupervisor.resolveIntent(
                this.mIntent, null, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
        this.mRInfo = resolveIntent;
        this.mAInfo =
            this.mSupervisor.resolveActivity(this.mIntent, resolveIntent, this.mStartFlags, null);
        return true;
      }
    }
    return interceptMultiWindowAliasActivityIfNeeded();
  }

  public final boolean hasCrossProfileAnimation() {
    ActivityOptions activityOptions = this.mActivityOptions;
    return activityOptions != null && activityOptions.getAnimationType() == 12;
  }

  public final Bundle deferCrossProfileAppsAnimationIfNecessary() {
    if (!hasCrossProfileAnimation()) {
      return null;
    }
    this.mActivityOptions = null;
    return ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle();
  }

  public final boolean interceptQuietProfileIfNeeded() {
    if (!this.mUserManager.isQuietModeEnabled(UserHandle.of(this.mUserId))) {
      return false;
    }
    if (isKeepProfilesRunningEnabled()
        && !isPackageSuspended()
        && !SemPersonaManager.isSecureFolderId(this.mUserId)) {
      return false;
    }
    this.mIntent =
        UnlaunchableAppActivity.createInQuietModeDialogIntent(
            this.mUserId,
            createIntentSenderForOriginalIntent(this.mCallingUid, 1342177280),
            this.mRInfo);
    this.mCallingPid = this.mRealCallingPid;
    this.mCallingUid = this.mRealCallingUid;
    this.mResolvedType = null;
    ResolveInfo resolveIntent =
        this.mSupervisor.resolveIntent(
            this.mIntent,
            this.mResolvedType,
            this.mUserManager.getProfileParent(this.mUserId).id,
            0,
            this.mRealCallingUid,
            this.mRealCallingPid);
    this.mRInfo = resolveIntent;
    this.mAInfo =
        this.mSupervisor.resolveActivity(this.mIntent, resolveIntent, this.mStartFlags, null);
    return true;
  }

  public final boolean interceptSuspendedByAdminPackage() {
    DevicePolicyManagerInternal devicePolicyManagerInternal =
        (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
    if (devicePolicyManagerInternal == null) {
      return false;
    }
    Intent createShowAdminSupportIntent =
        devicePolicyManagerInternal.createShowAdminSupportIntent(this.mUserId, true);
    this.mIntent = createShowAdminSupportIntent;
    createShowAdminSupportIntent.putExtra(
        "android.app.extra.RESTRICTION", "policy_suspend_packages");
    this.mCallingPid = this.mRealCallingPid;
    this.mCallingUid = this.mRealCallingUid;
    this.mResolvedType = null;
    UserInfo profileParent = this.mUserManager.getProfileParent(this.mUserId);
    if (profileParent != null) {
      this.mRInfo =
          this.mSupervisor.resolveIntent(
              this.mIntent,
              this.mResolvedType,
              profileParent.id,
              0,
              this.mRealCallingUid,
              this.mRealCallingPid);
    } else {
      this.mRInfo =
          this.mSupervisor.resolveIntent(
              this.mIntent,
              this.mResolvedType,
              this.mUserId,
              0,
              this.mRealCallingUid,
              this.mRealCallingPid);
    }
    this.mAInfo =
        this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
    return true;
  }

  public final boolean interceptSuspendedPackageIfNeeded() {
    PackageManagerInternal packageManagerInternalLocked;
    if (!isPackageSuspended()
        || (packageManagerInternalLocked = this.mService.getPackageManagerInternalLocked())
            == null) {
      return false;
    }
    String str = this.mAInfo.applicationInfo.packageName;
    String suspendingPackage = packageManagerInternalLocked.getSuspendingPackage(str, this.mUserId);
    if ("android".equals(suspendingPackage)) {
      return interceptSuspendedByAdminPackage();
    }
    Intent createSuspendedAppInterceptIntent =
        SuspendedAppActivity.createSuspendedAppInterceptIntent(
            str,
            suspendingPackage,
            packageManagerInternalLocked.getSuspendedDialogInfo(
                str, suspendingPackage, this.mUserId),
            hasCrossProfileAnimation()
                ? ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle()
                : null,
            createIntentSenderForOriginalIntent(this.mCallingUid, 67108864),
            this.mUserId);
    this.mIntent = createSuspendedAppInterceptIntent;
    int i = this.mRealCallingPid;
    this.mCallingPid = i;
    int i2 = this.mRealCallingUid;
    this.mCallingUid = i2;
    this.mResolvedType = null;
    ResolveInfo resolveIntent =
        this.mSupervisor.resolveIntent(
            createSuspendedAppInterceptIntent, null, this.mUserId, 0, i2, i);
    this.mRInfo = resolveIntent;
    this.mAInfo =
        this.mSupervisor.resolveActivity(this.mIntent, resolveIntent, this.mStartFlags, null);
    return true;
  }

  public final boolean interceptLockTaskModeViolationPackageIfNeeded() {
    ActivityInfo activityInfo = this.mAInfo;
    if (activityInfo == null || activityInfo.applicationInfo == null) {
      return false;
    }
    LockTaskController lockTaskController = this.mService.getLockTaskController();
    ActivityInfo activityInfo2 = this.mAInfo;
    if (lockTaskController.isActivityAllowed(
        this.mUserId,
        activityInfo2.applicationInfo.packageName,
        ActivityRecord.getLockTaskLaunchMode(activityInfo2, this.mActivityOptions))) {
      return false;
    }
    Intent createIntent =
        BlockedAppActivity.createIntent(this.mUserId, this.mAInfo.applicationInfo.packageName);
    this.mIntent = createIntent;
    int i = this.mRealCallingPid;
    this.mCallingPid = i;
    int i2 = this.mRealCallingUid;
    this.mCallingUid = i2;
    this.mResolvedType = null;
    ResolveInfo resolveIntent =
        this.mSupervisor.resolveIntent(createIntent, null, this.mUserId, 0, i2, i);
    this.mRInfo = resolveIntent;
    this.mAInfo =
        this.mSupervisor.resolveActivity(this.mIntent, resolveIntent, this.mStartFlags, null);
    return true;
  }

  public final boolean interceptLockedManagedProfileIfNeeded() {
    Task task;
    Intent interceptWithConfirmCredentialsIfNeeded =
        interceptWithConfirmCredentialsIfNeeded(this.mAInfo, this.mUserId);
    if (interceptWithConfirmCredentialsIfNeeded == null) {
      return false;
    }
    this.mIntent = interceptWithConfirmCredentialsIfNeeded;
    this.mCallingPid = this.mRealCallingPid;
    this.mCallingUid = this.mRealCallingUid;
    this.mResolvedType = null;
    TaskFragment launchTaskFragment = getLaunchTaskFragment();
    if (this.mInTask != null) {
      if (isFreeFormMode()) {
        DisplayContent actualDisplayContent = getActualDisplayContent(getActualDisplayId());
        if (actualDisplayContent != null && actualDisplayContent.isDexMode()) {
          Slog.w("ActivityTaskManager", "Ignore setLaunchTaskId when dex mode.");
        } else {
          ActivityOptions activityOptions = this.mActivityOptions;
          if (activityOptions != null) {
            activityOptions.setLaunchTaskId(this.mInTask.mTaskId);
          }
        }
      }
      this.mIntent.putExtra("android.intent.extra.TASK_ID", this.mInTask.mTaskId);
      this.mInTask = null;
    } else if (launchTaskFragment != null && (task = launchTaskFragment.getTask()) != null) {
      this.mIntent.putExtra("android.intent.extra.TASK_ID", task.mTaskId);
    }
    if (this.mActivityOptions == null) {
      this.mActivityOptions = ActivityOptions.makeBasic();
    }
    ResolveInfo resolveIntent =
        this.mSupervisor.resolveIntent(
            this.mIntent,
            this.mResolvedType,
            this.mUserManager.getProfileParent(this.mUserId).id,
            0,
            this.mRealCallingUid,
            this.mRealCallingPid);
    this.mRInfo = resolveIntent;
    this.mAInfo =
        this.mSupervisor.resolveActivity(this.mIntent, resolveIntent, this.mStartFlags, null);
    return true;
  }

  public final Intent interceptWithConfirmCredentialsIfNeeded(ActivityInfo activityInfo, int i) {
    ActivityOptions activityOptions;
    Task fromWindowContainerToken;
    if (!this.mService.mAmInternal.shouldConfirmCredentials(i)) {
      return null;
    }
    if ((activityInfo.flags & 8388608) != 0
        && (this.mUserManager.isUserUnlocked(i) || activityInfo.directBootAware)) {
      return null;
    }
    IntentSender createIntentSenderForOriginalIntent =
        createIntentSenderForOriginalIntent(this.mCallingUid, 1409286144);
    Intent createConfirmDeviceCredentialIntent =
        ((KeyguardManager) this.mServiceContext.getSystemService("keyguard"))
            .createConfirmDeviceCredentialIntent(null, null, i, true);
    if (createConfirmDeviceCredentialIntent == null) {
      return null;
    }
    createConfirmDeviceCredentialIntent.setFlags(276840448);
    createConfirmDeviceCredentialIntent.putExtra(
        "android.intent.extra.PACKAGE_NAME", activityInfo.packageName);
    createConfirmDeviceCredentialIntent.putExtra(
        "android.intent.extra.INTENT", createIntentSenderForOriginalIntent);
    if (this.mIntent != null
        && (activityOptions = this.mActivityOptions) != null
        && (fromWindowContainerToken =
                Task.fromWindowContainerToken(activityOptions.getLaunchRootTask()))
            != null
        && fromWindowContainerToken.getWindowingMode() == 6) {
      createConfirmDeviceCredentialIntent.addFlags(134217728);
    }
    return createConfirmDeviceCredentialIntent;
  }

  public final boolean interceptHarmfulAppIfNeeded() {
    try {
      CharSequence harmfulAppWarning =
          this.mService
              .getPackageManager()
              .getHarmfulAppWarning(this.mAInfo.packageName, this.mUserId);
      if (harmfulAppWarning == null) {
        return false;
      }
      Intent createHarmfulAppWarningIntent =
          HarmfulAppWarningActivity.createHarmfulAppWarningIntent(
              this.mServiceContext,
              this.mAInfo.packageName,
              createIntentSenderForOriginalIntent(this.mCallingUid, 1409286144),
              harmfulAppWarning);
      this.mIntent = createHarmfulAppWarningIntent;
      int i = this.mRealCallingPid;
      this.mCallingPid = i;
      int i2 = this.mRealCallingUid;
      this.mCallingUid = i2;
      this.mResolvedType = null;
      ResolveInfo resolveIntent =
          this.mSupervisor.resolveIntent(
              createHarmfulAppWarningIntent, null, this.mUserId, 0, i2, i);
      this.mRInfo = resolveIntent;
      this.mAInfo =
          this.mSupervisor.resolveActivity(this.mIntent, resolveIntent, this.mStartFlags, null);
      return true;
    } catch (RemoteException | IllegalArgumentException unused) {
      return false;
    }
  }

  public final boolean isPackageSuspended() {
    ApplicationInfo applicationInfo;
    ActivityInfo activityInfo = this.mAInfo;
    return (activityInfo == null
            || (applicationInfo = activityInfo.applicationInfo) == null
            || (applicationInfo.flags & 1073741824) == 0)
        ? false
        : true;
  }

  public static boolean isKeepProfilesRunningEnabled() {
    DevicePolicyManagerInternal devicePolicyManagerInternal =
        (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
    return devicePolicyManagerInternal == null
        || devicePolicyManagerInternal.isKeepProfilesRunningEnabled();
  }

  public void onActivityLaunched(TaskInfo taskInfo, final ActivityRecord activityRecord) {
    SparseArray activityInterceptorCallbacks = this.mService.getActivityInterceptorCallbacks();
    Objects.requireNonNull(activityRecord);
    ActivityInterceptorCallback.ActivityInterceptorInfo interceptorInfo =
        getInterceptorInfo(
            new Runnable() { // from class:
                             // com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                ActivityRecord.this.clearOptionsAnimationForSiblings();
              }
            });
    for (int i = 0; i < activityInterceptorCallbacks.size(); i++) {
      if (shouldNotifyOnActivityLaunch(activityInterceptorCallbacks.keyAt(i), interceptorInfo)) {
        ((ActivityInterceptorCallback) activityInterceptorCallbacks.valueAt(i))
            .onActivityLaunched(taskInfo, activityRecord.info, interceptorInfo);
      }
    }
  }

  public final ActivityInterceptorCallback.ActivityInterceptorInfo getInterceptorInfo(
      Runnable runnable) {
    return new ActivityInterceptorCallback.ActivityInterceptorInfo.Builder(
            this.mCallingUid,
            this.mCallingPid,
            this.mRealCallingUid,
            this.mRealCallingPid,
            this.mUserId,
            this.mIntent,
            this.mRInfo,
            this.mAInfo)
        .setResolvedType(this.mResolvedType)
        .setCallingPackage(this.mCallingPackage)
        .setCallingFeatureId(this.mCallingFeatureId)
        .setCheckedOptions(this.mActivityOptions)
        .setClearOptionsAnimationRunnable(runnable)
        .build();
  }

  public final boolean shouldInterceptActivityLaunch(
      int i, ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
    if (i == 1001) {
      return activityInterceptorInfo.getIntent() != null
          && activityInterceptorInfo.getIntent().isSandboxActivity(this.mServiceContext);
    }
    return true;
  }

  public final boolean shouldNotifyOnActivityLaunch(
      int i, ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
    if (i == 1001) {
      return activityInterceptorInfo.getIntent() != null
          && activityInterceptorInfo.getIntent().isSandboxActivity(this.mServiceContext);
    }
    return true;
  }

  public final boolean interceptMultiWindowAliasActivityIfNeeded() {
    ActivityInfo activityInfo;
    int i;
    boolean z;
    int i2;
    boolean z2;
    Intent intent;
    ResolveInfo resolveIntent;
    ActivityInfo resolveActivity;
    ActivityOptions activityOptions = this.mActivityOptions;
    boolean z3 = activityOptions != null && activityOptions.isResumedAffordanceAnimationRequested();
    ActivityOptions activityOptions2 = this.mActivityOptions;
    boolean z4 = activityOptions2 != null && activityOptions2.isStartedFromWindowTypeLauncher();
    ActivityRecord activityRecord = this.mSourceRecord;
    if (activityRecord == null && !z4) {
      return false;
    }
    if (activityRecord != null && !activityRecord.isActivityTypeHome() && !z4) {
      return false;
    }
    ActivityRecord activityRecord2 = this.mSourceRecord;
    if ((activityRecord2 == null || !activityRecord2.mIsAliasActivity)
        && (activityInfo = this.mAInfo) != null
        && activityInfo.metaData != null
        && ActivityInfo.isResizeableMode(activityInfo.resizeMode)) {
      String string =
          this.mAInfo.metaData.getString(
              "com.samsung.android.multiwindow.activity.alias.targetactivity");
      if (TextUtils.isEmpty(string)) {
        return false;
      }
      ActivityRecord activityRecord3 = this.mSourceRecord;
      int displayId = activityRecord3 != null ? activityRecord3.getDisplayId() : 0;
      ActivityOptions activityOptions3 = this.mActivityOptions;
      if (activityOptions3 != null) {
        Task fromWindowContainerToken =
            Task.fromWindowContainerToken(activityOptions3.getLaunchRootTask());
        i =
            (fromWindowContainerToken == null
                    || !WindowConfiguration.isSplitScreenWindowingMode(
                        fromWindowContainerToken.getWindowConfiguration()))
                ? 0
                : fromWindowContainerToken.getStageType();
        z = this.mActivityOptions.getLaunchTaskFragmentToken() != null;
        i2 = this.mActivityOptions.getForceLaunchWindowingMode();
        if (i2 == 0) {
          i2 = this.mActivityOptions.getLaunchWindowingMode();
        }
        int launchDisplayId = this.mActivityOptions.getLaunchDisplayId();
        if (launchDisplayId != -1) {
          displayId = launchDisplayId;
        }
      } else {
        i = 0;
        z = false;
        i2 = 0;
      }
      DisplayContent displayContent =
          this.mService.mRootWindowContainer.getDisplayContent(displayId);
      if (displayContent == null) {
        displayContent = this.mService.mRootWindowContainer.getDisplayContent();
      }
      boolean z5 =
          displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()
              || displayContent.getRootTask(5, 0) != null
              || i != 0
              || i2 == 5
              || z
              || displayContent.isDexMode();
      ArrayList arrayList = new ArrayList();
      this.mService.mMultiInstanceController.findAliasManagedTaskInPackage(
          this.mAInfo.packageName, this.mUserId, arrayList);
      arrayList.sort(
          new Comparator() { // from class:
                             // com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
              int lambda$interceptMultiWindowAliasActivityIfNeeded$2;
              lambda$interceptMultiWindowAliasActivityIfNeeded$2 =
                  ActivityStartInterceptor.lambda$interceptMultiWindowAliasActivityIfNeeded$2(
                      (Task) obj, (Task) obj2);
              return lambda$interceptMultiWindowAliasActivityIfNeeded$2;
            }
          });
      TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
      if (z3) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
          Task task = (Task) it.next();
          if (task.getDisplayId() == 0 && task.isFocusableAndVisible()) {
            resolveLaunchTaskIdForAliasManagedTarget(task);
            if (i != 0) {
              this.mActivityOptions.setLaunchRootTask(null);
            }
            return true;
          }
        }
      }
      ActivityRecord activityRecord4 = this.mSourceRecord;
      if (activityRecord4 != null && (activityRecord4.isActivityTypeHome() || z4)) {
        int i3 = 0;
        while (i3 < arrayList.size()) {
          Task task2 = (Task) arrayList.get(i3);
          if (task2.getDisplayId() != displayId) {
            if (task2.getDisplayId() == 2 || displayId == 2) {
              resolveLaunchTaskIdForAliasManagedTarget(task2);
              return true;
            }
          } else if (!task2.inFreeformWindowingMode()) {
            continue;
          } else {
            if (task2.isMinimized()) {
              resolveLaunchTaskIdForAliasManagedTarget(task2);
              return true;
            }
            arrayList.remove(i3);
            i3--;
          }
          i3++;
        }
        if (!z5
            && (arrayList.size() > 1
                || (arrayList.size() == 1
                    && !((Task) arrayList.get(0))
                        .getBaseIntent()
                        .getComponent()
                        .equals(this.mIntent.getComponent())))) {
          z2 = true;
          if (!z5 || z2) {
            intent = new Intent(this.mIntent);
            intent.setClassName(this.mIntent.getComponent().getPackageName(), string);
            resolveIntent =
                this.mSupervisor.resolveIntent(
                    intent, this.mResolvedType, this.mUserId, 0, 1000, Process.myPid());
            resolveActivity =
                this.mSupervisor.resolveActivity(intent, resolveIntent, this.mStartFlags, null);
            if (resolveIntent != null && resolveActivity != null) {
              this.mIntent = intent;
              this.mRInfo = resolveIntent;
              this.mAInfo = resolveActivity;
              this.mIsInterceptedForAliasActivity = true;
              return true;
            }
          }
        }
      } else if (i != 0 && defaultTaskDisplayArea.isSplitScreenModeActivated()) {
        Task topRootTaskInStageType = defaultTaskDisplayArea.getTopRootTaskInStageType(i);
        Task topMostTask =
            topRootTaskInStageType != null ? topRootTaskInStageType.getTopMostTask() : null;
        int indexOf = arrayList.indexOf(topMostTask);
        if (indexOf >= 0 && ((Task) arrayList.get(indexOf)).isVisible()) {
          resolveLaunchTaskIdForAliasManagedTarget(topMostTask);
          return true;
        }
      }
      z2 = false;
      if (!z5) {}
      intent = new Intent(this.mIntent);
      intent.setClassName(this.mIntent.getComponent().getPackageName(), string);
      resolveIntent =
          this.mSupervisor.resolveIntent(
              intent, this.mResolvedType, this.mUserId, 0, 1000, Process.myPid());
      resolveActivity =
          this.mSupervisor.resolveActivity(intent, resolveIntent, this.mStartFlags, null);
      if (resolveIntent != null) {
        this.mIntent = intent;
        this.mRInfo = resolveIntent;
        this.mAInfo = resolveActivity;
        this.mIsInterceptedForAliasActivity = true;
        return true;
      }
    }
    return false;
  }

  public static /* synthetic */ int lambda$interceptMultiWindowAliasActivityIfNeeded$2(
      Task task, Task task2) {
    return Long.compare(task2.lastGainFocusTime, task.lastGainFocusTime);
  }

  public void resolveLaunchTaskIdForAliasManagedTarget(Task task) {
    Intent intent = new Intent(this.mIntent);
    intent.setComponent(task.getBaseIntent().getComponent());
    intent.setLaunchTaskIdForAliasManagedTarget(task.mTaskId);
    ResolveInfo resolveIntent =
        this.mSupervisor.resolveIntent(
            intent, this.mResolvedType, this.mUserId, 0, 1000, Process.myPid());
    ActivityInfo resolveActivity =
        this.mSupervisor.resolveActivity(intent, resolveIntent, this.mStartFlags, null);
    if (resolveIntent == null || resolveActivity == null) {
      return;
    }
    this.mIntent = intent;
    this.mRInfo = resolveIntent;
    this.mAInfo = resolveActivity;
  }

  public boolean hasAliasActivity(Intent intent) {
    return this.mIsInterceptedForAliasActivity && this.mIntent == intent;
  }

  public final PersonaManagerInternal getPersonaManagerInternal() {
    if (this.mPersonaManagerInternal == null) {
      this.mPersonaManagerInternal =
          (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);
    }
    return this.mPersonaManagerInternal;
  }

  public final boolean interceptSuperLockIfNeeded() {
    if (!this.mUserManager.getUserInfo(this.mUserId).isSuperLocked()
        || isIntentFromExceptionList()) {
      return false;
    }
    this.mIntent = SemPersonaManager.createLockdownIntent(this.mUserId);
    this.mCallingPid = this.mRealCallingPid;
    this.mCallingUid = this.mRealCallingUid;
    this.mResolvedType = null;
    UserInfo profileParent = this.mUserManager.getProfileParent(this.mUserId);
    if (profileParent == null) {
      this.mRInfo =
          this.mSupervisor.resolveIntent(
              this.mIntent,
              this.mResolvedType,
              this.mUserId,
              0,
              this.mRealCallingUid,
              this.mCallingPid);
    } else {
      this.mRInfo =
          this.mSupervisor.resolveIntent(
              this.mIntent,
              this.mResolvedType,
              profileParent.id,
              0,
              this.mRealCallingUid,
              this.mCallingPid);
    }
    this.mAInfo =
        this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
    return true;
  }

  public final boolean isIntentFromExceptionList() {
    Intent intent = this.mIntent;
    if (intent == null) {
      return false;
    }
    if (intent.getAction() != null
        && (this.mIntent.getAction().equals("android.intent.action.CALL_PRIVILEGED")
            || this.mIntent.getAction().equals("android.intent.action.CALL_EMERGENCY")
            || this.mIntent.getAction().equals("com.android.phone.EmergencyDialer.DIAL")
            || this.mIntent
                .getAction()
                .equals("com.samsung.android.app.telephonyui.action.OPEN_EMERGENCY_DIALER")
            || this.mIntent.getAction().equals("intent.action.INTERACTION_ICE")
            || this.mIntent.getAction().equals("com.samsung.android.knox.LOCKDOWN_SCREEN"))) {
      return true;
    }
    if (this.mIntent.getComponent() == null || this.mIntent.getComponent().getClassName() == null) {
      return false;
    }
    return this.mIntent
            .getComponent()
            .getClassName()
            .equalsIgnoreCase("com.android.incallui.InCallActivity")
        || this.mIntent
            .getComponent()
            .getClassName()
            .equalsIgnoreCase("com.android.incallui.call.InCallActivity");
  }

  public final boolean isFreeFormMode() {
    return this.mInTask.getRootActivity() != null
        && this.mInTask.getRootActivity().getWindowingMode() == 5;
  }

  public final int getActualDisplayId() {
    int launchDisplayId;
    ActivityRecord activityRecord = this.mSourceRecord;
    int displayId = activityRecord != null ? activityRecord.getDisplayId() : 0;
    ActivityOptions activityOptions = this.mActivityOptions;
    return (activityOptions == null
            || (launchDisplayId = activityOptions.getLaunchDisplayId()) == -1)
        ? displayId
        : launchDisplayId;
  }

  public final DisplayContent getActualDisplayContent(int i) {
    DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
    return displayContent == null
        ? this.mService.mRootWindowContainer.getDefaultDisplay()
        : displayContent;
  }
}
