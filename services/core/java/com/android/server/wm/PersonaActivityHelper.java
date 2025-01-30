package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.TaskStackListener;
import android.app.trust.ITrustManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.QuintPredicate;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.UserManagerService;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class PersonaActivityHelper {
  public final Context mContext;
  public KeyguardManager mKeyguardManager;
  public int mLastPIPModeActivityUserId;
  public final Looper mLooper;
  public PackageManager mPackageManager;
  public PersonaActivityHandler mPersonaActivityHandler;
  public PersonaManagerService mPersonaManager;
  public RootWindowContainer mRootWindowContainer;
  public SemPersonaManager mSemPersonaManager;
  public final ActivityTaskManagerService mService;
  public UserManagerService mUserManager;
  public WindowManagerService mWindowManager;
  public SemDesktopModeManager mDesktopModeManager = null;
  public WallpaperManagerService mWms = null;
  public ActivityRecord mLastReceivedResumedActivity = null;
  public ActivityRecord mLastResumedSFActivity = null;
  public List mLockSecureFolderExceptionalList = null;

  public PersonaActivityHelper(
      ActivityTaskManagerService activityTaskManagerService,
      Looper looper,
      RootWindowContainer rootWindowContainer) {
    this.mService = activityTaskManagerService;
    Context context = activityTaskManagerService.mContext;
    this.mContext = context;
    this.mLooper = looper;
    this.mPackageManager = context.getPackageManager();
    this.mUserManager = UserManagerService.getInstance();
    this.mPersonaActivityHandler = new PersonaActivityHandler(looper);
    activityTaskManagerService.registerTaskStackListener(
        new TaskStackListener() { // from class: com.android.server.wm.PersonaActivityHelper.1
          public void onActivityPinned(String str, int i, int i2, int i3) {
            PersonaActivityHelper.this.mLastPIPModeActivityUserId = i;
          }

          public void onActivityUnpinned() {
            if (SemPersonaManager.isSecureFolderId(
                PersonaActivityHelper.this.mLastPIPModeActivityUserId)) {
              PersonaActivityHelper personaActivityHelper = PersonaActivityHelper.this;
              personaActivityHelper.checkAndLockSecureFolder(
                  personaActivityHelper.mLastReceivedResumedActivity);
            }
          }
        });
    activityTaskManagerService
        .getTransitionController()
        .registerLegacyListener(
            new WindowManagerInternal
                .AppTransitionListener() { // from class:
                                           // com.android.server.wm.PersonaActivityHelper.2
              @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
              public void onAppTransitionFinishedLocked(IBinder iBinder) {
                RootWindowContainer rootWindowContainer2 =
                    PersonaActivityHelper.this.mRootWindowContainer;
                if (rootWindowContainer2 != null) {
                  ActivityRecord activityRecord = rootWindowContainer2.getActivityRecord(iBinder);
                  if (PersonaActivityHelper.this.mLastResumedSFActivity == null
                      || PersonaActivityHelper.this.mLastResumedSFActivity.info == null) {
                    return;
                  }
                  Log.d("PersonaActivityHelper", "token.toString()  " + iBinder.toString());
                  Log.d(
                      "PersonaActivityHelper",
                      "mLastResumedSFActivity getPackageName  "
                          + PersonaActivityHelper.this
                              .mLastResumedSFActivity
                              .info
                              .getComponentName()
                              .getPackageName());
                  Log.d(
                      "PersonaActivityHelper",
                      "mLastResumedSFActivity getShortClassName  "
                          + PersonaActivityHelper.this
                              .mLastResumedSFActivity
                              .info
                              .getComponentName()
                              .getShortClassName());
                  if (activityRecord == null) {
                    if (iBinder
                            .toString()
                            .contains(
                                PersonaActivityHelper.this
                                    .mLastResumedSFActivity
                                    .info
                                    .getComponentName()
                                    .getPackageName())
                        && iBinder
                            .toString()
                            .contains(
                                PersonaActivityHelper.this
                                    .mLastResumedSFActivity
                                    .info
                                    .getComponentName()
                                    .getShortClassName())) {
                      PersonaActivityHelper personaActivityHelper = PersonaActivityHelper.this;
                      personaActivityHelper.checkAndLockSecureFolder(
                          personaActivityHelper.mLastReceivedResumedActivity);
                      return;
                    }
                    return;
                  }
                  if (activityRecord == null
                      || activityRecord.info == null
                      || !PersonaActivityHelper.this
                          .mLastResumedSFActivity
                          .info
                          .getComponentName()
                          .equals(activityRecord.info.getComponentName())
                      || activityRecord.isVisible()) {
                    return;
                  }
                  PersonaActivityHelper personaActivityHelper2 = PersonaActivityHelper.this;
                  personaActivityHelper2.checkAndLockSecureFolder(
                      personaActivityHelper2.mLastReceivedResumedActivity);
                }
              }
            });
  }

  public void setWindowManager(WindowManagerService windowManagerService) {
    synchronized (this.mService) {
      this.mWindowManager = windowManagerService;
      this.mRootWindowContainer = windowManagerService.mRoot;
    }
  }

  public void onSystemReady() {
    new IntentFilter().addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
    checkKnoxFeatureEnabled();
  }

  public Intent notifyStartActivityAsUser(Intent intent, String str, int i, Bundle bundle) {
    if (intent != null
        && intent.getAction() != null
        && ("android.app.action.PROVISION_MANAGED_PROFILE".equals(intent.getAction())
            || "android.app.action.PROVISION_MANAGED_USER".equals(intent.getAction())
            || "android.app.action.PROVISION_MANAGED_DEVICE".equals(intent.getAction())
            || "android.app.action.PROVISION_MANAGED_SHAREABLE_DEVICE".equals(intent.getAction())
            || "com.android.managedprovisioning.action.RESUME_PROVISIONING"
                .equals(intent.getAction()))) {
      int intExtra = intent.getIntExtra("com.samsung.knox.container.requestId", -1);
      int callingUid = Binder.getCallingUid();
      Log.d(
          "PersonaActivityHelper",
          intent.getAction()
              + " KnoxContainerManager.EXTRA_REQUEST_ID : "
              + intExtra
              + " caller : "
              + callingUid);
      if (intExtra != -1) {
        if (callingUid == 1000) {
          return intent;
        }
        intent.removeExtra("com.samsung.knox.container.requestId");
        return intent;
      }
    }
    checkKnoxFeatureEnabled();
    return intent;
  }

  public void notifySetResumedActivityUncheckLocked(ActivityRecord activityRecord) {
    if (checkKnoxFeatureEnabled() && activityRecord != null) {
      this.mLastReceivedResumedActivity = activityRecord;
      if (SemPersonaManager.isSecureFolderId(activityRecord.mUserId)
          || isLockSecureFolderExceptionalCase(activityRecord.shortComponentName)) {
        this.mLastResumedSFActivity = activityRecord;
      }
      this.mPersonaActivityHandler.removeMessages(
          FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED);
      Message obtainMessage =
          this.mPersonaActivityHandler.obtainMessage(
              FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED);
      obtainMessage.obj = activityRecord;
      this.mPersonaActivityHandler.sendMessage(obtainMessage);
    }
  }

  public final void setFocusIfNecessary(ActivityRecord activityRecord) {
    this.mPersonaManager.setFocusedUser(activityRecord.mUserId);
  }

  public final void checkAndLockSecureFolder(ActivityRecord activityRecord) {
    ActivityRecord activityRecord2 = activityRecord != null ? activityRecord.resultTo : null;
    if (activityRecord2 == null
        || !SemPersonaManager.isSecureFolderId(activityRecord2.mUserId)
        || activityRecord2.mUserId == activityRecord.mUserId) {
      LockSecureFolderTask(activityRecord);
    }
  }

  public void notifyActivityResumedLocked(ActivityRecord activityRecord, boolean z) {
    if ((checkKnoxFeatureEnabled() || SemPersonaManager.isDoEnabled(0)) && activityRecord != null) {
      ActivityRecord.State state = activityRecord.getState();
      int i = activityRecord.mUserId;
      ComponentName componentName = activityRecord.info.getComponentName();
      int activityType = activityRecord.getRootTask().getActivityType();
      int windowingMode = activityRecord.getRootTask().getWindowingMode();
      boolean occludesParent = activityRecord.occludesParent();
      boolean z2 = z && (activityRecord.nowVisible || activityRecord.isVisible());
      boolean canShowWhenLocked = canShowWhenLocked(activityRecord);
      boolean isDexMode = activityRecord.isDexMode();
      if (state == ActivityRecord.State.RESUMED) {
        this.mPersonaManager.postActiveUserChange(
            i,
            componentName,
            canShowWhenLocked && !SemDualAppManager.isDualAppId(i),
            activityType,
            windowingMode,
            occludesParent,
            z2,
            isDexMode);
      }
    }
  }

  public boolean notifyKillForegroundAppsForUser(int i) {
    if (!checkKnoxFeatureEnabled() || !SemPersonaManager.isKnoxId(i)) {
      return false;
    }
    Message obtainMessage = this.mPersonaActivityHandler.obtainMessage(602);
    obtainMessage.arg1 = i;
    this.mPersonaActivityHandler.sendMessage(obtainMessage);
    return true;
  }

  public static boolean isKnoxWindowVisible(Task task, boolean z, int i, boolean z2, int i2) {
    ActivityRecord activityRecord;
    if (task == null || (activityRecord = task.topRunningActivityLocked()) == null) {
      return false;
    }
    if ((!z || !SemPersonaManager.isKnoxId(activityRecord.mUserId))
        && activityRecord.mUserId != i) {
      return false;
    }
    if (!(z2 && activityRecord.nowVisible) && (z2 || !activityRecord.isVisible())) {
      return false;
    }
    return (i2 != -1 && i2 == activityRecord.getWindowingMode()) || i2 == -1;
  }

  public boolean isKnoxWindowVisibleLocked(boolean z, int i, boolean z2, int i2, int i3) {
    if (!checkKnoxFeatureEnabled() || this.mRootWindowContainer == null) {
      return false;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        for (int childCount = this.mRootWindowContainer.getChildCount() - 1;
            childCount >= 0;
            childCount--) {
          DisplayContent displayContent =
              (DisplayContent) this.mRootWindowContainer.getChildAt(childCount);
          if (displayContent != null) {
            PooledPredicate obtainPredicate =
                PooledLambda.obtainPredicate(
                    new QuintPredicate() { // from class:
                                           // com.android.server.wm.PersonaActivityHelper$$ExternalSyntheticLambda0
                      public final boolean test(
                          Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                        boolean isKnoxWindowVisible;
                        isKnoxWindowVisible =
                            PersonaActivityHelper.isKnoxWindowVisible(
                                (Task) obj,
                                ((Boolean) obj2).booleanValue(),
                                ((Integer) obj3).intValue(),
                                ((Boolean) obj4).booleanValue(),
                                ((Integer) obj5).intValue());
                        return isKnoxWindowVisible;
                      }
                    },
                    PooledLambda.__(Task.class),
                    Boolean.valueOf(z),
                    Integer.valueOf(i),
                    Boolean.valueOf(z2),
                    Integer.valueOf(i3));
            Task task = displayContent.getTask(obtainPredicate, true);
            obtainPredicate.recycle();
            if (task != null) {
              WindowManagerService.resetPriorityAfterLockedSection();
              return true;
            }
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

  public Set getRecentExcludedUsers() {
    int[] userIds;
    HashSet hashSet = new HashSet();
    if (checkKnoxFeatureEnabled()
        && !SemEmergencyManager.isEmergencyMode(this.mContext)
        && (userIds = getUserManager().getUserIds()) != null) {
      for (int i : userIds) {
        if (SemPersonaManager.isKnoxId(i)) {
          if (getUserManager().isQuietModeEnabled(i)) {
            hashSet.add(Integer.valueOf(i));
          } else if (SemPersonaManager.isSecureFolderId(i)) {
            if (!(Settings.Secure.getIntForUser(
                    this.mContext.getContentResolver(), "hide_secure_folder_flag", 0, 0)
                == 0)) {
              hashSet.add(Integer.valueOf(i));
            }
          }
        }
      }
    }
    return hashSet;
  }

  public final void LockSecureFolderTask(ActivityRecord activityRecord) {
    if (isActivityInResumedState(activityRecord)) {
      int secureFolderId = SemPersonaManager.getSecureFolderId(this.mContext);
      if (secureFolderId <= 0
          || SemPersonaManager.isSecureFolderId(activityRecord.mUserId)
          || (isImmediateLockSet(secureFolderId)
              && !isSecureFolderLocked(secureFolderId)
              && isSecureFolderAvailable(secureFolderId)
              && !isLockSecureFolderExceptionalCase(activityRecord.shortComponentName))) {
        sendLockSecureFolderMessage(activityRecord);
      }
    }
  }

  public final boolean isSecureFolderAvailable(int i) {
    return SystemProperties.getBoolean("persist.sys.knox.secure_folder_state_available", true);
  }

  public final boolean isActivityInResumedState(ActivityRecord activityRecord) {
    if (activityRecord == null) {
      return false;
    }
    if (activityRecord.getState() == ActivityRecord.State.RESUMED) {
      return true;
    }
    Log.d("PersonaActivityHelper", "Activity not in resumed state, do not run immediate lock");
    return false;
  }

  public final boolean isImmediateLockSet(int i) {
    try {
      return Settings.System.getIntForUser(
              this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i)
          == -2;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public final boolean isSecureFolderLocked(int i) {
    try {
      if (getKeyguardManager() != null) {
        if (getKeyguardManager().isDeviceLocked(i)) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public final boolean isLockSecureFolderExceptionalCase(String str) {
    if (this.mLockSecureFolderExceptionalList == null) {
      ArrayList arrayList = new ArrayList();
      this.mLockSecureFolderExceptionalList = arrayList;
      arrayList.add("com.android.systemui/.keyguard.WorkLockActivity");
      this.mLockSecureFolderExceptionalList.add(
          "android/com.android.internal.app.ForwardIntentToManagedProfile");
      this.mLockSecureFolderExceptionalList.add(
          "android/com.android.internal.app.ForwardIntentToManagedProfile4");
      this.mLockSecureFolderExceptionalList.add("com.android.intentresolver/.ChooserActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.allshare.service.fileshare/.client.DeviceSelectActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.sec.android.app.myfiles/.external.ui.PermissionActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.google.android.permissioncontroller/com.android.permissioncontroller.permission.ui.GrantPermissionsActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.google.android.permissioncontroller/com.android.permissioncontroller.permission.ui.ManagePermissionsActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.google.android.permissioncontroller/com.android.permissioncontroller.role.ui.RequestRoleActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.messaging/.ui.view.main.WithActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.messaging/.ui.view.recipientspicker.PickerActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.messaging/.ui.view.firstlaunch.GoogleFirstLaunchActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.google.android.gms/.constellation.ui.ApiConsentActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.messaging/com.android.mms.ui.ConversationComposer");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.messaging/.ui.view.permission.CheckDefaultSmsAppsActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.biometrics.app.setting/.fingerprint.enroll.FingerprintEnrollActivity");
      this.mLockSecureFolderExceptionalList.add(
          "com.samsung.android.settings.notification.SecRedactionInterstitial");
      this.mLockSecureFolderExceptionalList.add(
          "com.wssyncmldm/com.idm.fotaagent.enabler.ui.downloadconfirm.DownloadConfirmActivity");
      if (isRunningDexOnPc()) {
        this.mLockSecureFolderExceptionalList.add(
            "com.samsung.knox.securefolder/.presentation.switcher.view.SecureFolderShortcutActivity");
      }
    }
    return this.mLockSecureFolderExceptionalList.contains(str)
        || str.startsWith("com.android.settings/");
  }

  public final boolean isRunningDexOnPc() {
    if (!isDesktopMode()
        || Settings.System.getIntForUser(
                this.mContext.getContentResolver(), "dexonpc_connection_state", 0, 0)
            != 3) {
      return false;
    }
    Log.d("PersonaActivityHelper", "DeX on PC is running");
    return true;
  }

  public final boolean isDesktopMode() {
    if (getDesktopModeManager() == null) {
      return false;
    }
    int enabled = getDesktopModeManager().getDesktopModeState().getEnabled();
    return enabled == 4 || enabled == 3;
  }

  public final SemDesktopModeManager getDesktopModeManager() {
    if (this.mDesktopModeManager == null) {
      this.mDesktopModeManager =
          (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
    }
    return this.mDesktopModeManager;
  }

  public final void sendLockSecureFolderMessage(ActivityRecord activityRecord) {
    this.mPersonaActivityHandler.removeMessages(FrameworkStatsLog.AUTOFILL_UI_EVENT_REPORTED);
    Message obtainMessage =
        this.mPersonaActivityHandler.obtainMessage(FrameworkStatsLog.AUTOFILL_UI_EVENT_REPORTED);
    obtainMessage.obj = createLockSecureFolderMessageBundle(activityRecord);
    obtainMessage.arg1 = activityRecord.mUserId;
    this.mPersonaActivityHandler.sendMessage(obtainMessage);
  }

  public final Bundle createLockSecureFolderMessageBundle(ActivityRecord activityRecord) {
    Bundle bundle = new Bundle();
    bundle.putBoolean("isHomeActivity", activityRecord.isActivityTypeHome());
    bundle.putString("componentName", activityRecord.shortComponentName);
    return bundle;
  }

  public UserHandle getCurrentScreenUserId(UserHandle userHandle) {
    if (checkKnoxFeatureEnabled()
        && "2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
      int focusedKnoxId = getSemPersonaManager().getFocusedKnoxId();
      Log.d("PersonaActivityHelper", "screenshot: check focused user : " + focusedKnoxId);
      UserInfo userInfo = getUserManager().getUserInfo(focusedKnoxId);
      if (userInfo != null
          && (userInfo.isManagedProfile() || SemPersonaManager.isSecureFolderId(focusedKnoxId))) {
        UserHandle userHandle2 = new UserHandle(focusedKnoxId);
        Log.d(
            "PersonaActivityHelper", "screenshot: getCurrentScreenUserId:premium = " + userHandle2);
        return userHandle2;
      }
      if (userInfo != null && userInfo.isUserTypeAppSeparation()) {
        UserHandle userHandle3 = new UserHandle(focusedKnoxId);
        Log.d("PersonaActivityHelper", "screenshot: App Separation user type. ID = " + userHandle3);
        return userHandle3;
      }
      Log.d("PersonaActivityHelper", "screenshot: getCurrentScreenUserId = " + userHandle);
    }
    return userHandle;
  }

  public final boolean checkKnoxFeatureEnabled() {
    String str = SystemProperties.get("persist.sys.knox.userinfo");
    boolean z = (str == null || str.length() == 0) ? false : true;
    if (this.mPersonaManager == null) {
      this.mPersonaManager = getPersonaManager();
    }
    if (this.mPersonaManager != null && z) {
      return true;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        if (SemPersonaManager.isDoEnabled(0)) {
          return true;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final SemPersonaManager getSemPersonaManager() {
    if (this.mSemPersonaManager == null) {
      this.mSemPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
    }
    return this.mSemPersonaManager;
  }

  public final KeyguardManager getKeyguardManager() {
    if (this.mKeyguardManager == null) {
      this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
    }
    return this.mKeyguardManager;
  }

  public final boolean canShowWhenLocked(ActivityRecord activityRecord) {
    boolean canShowWhenLocked = activityRecord.canShowWhenLocked();
    return !canShowWhenLocked
        ? activityRecord.packageName.equals("com.samsung.android.incallui")
            || activityRecord.packageName.equals("com.android.server.telecom")
            || activityRecord.shortComponentName.equals(
                "com.android.calendar/.alerts.PopUpActivity")
            || activityRecord.shortComponentName.equals(
                "com.samsung.android.calendar/com.samsung.android.app.calendarnotification.AlertPopupActivity")
            || activityRecord.shortComponentName.equals(
                "com.microsoft.teams/com.microsoft.skype.teams.views.activities.PreCallActivity")
            || activityRecord.shortComponentName.equals(
                "com.microsoft.teams/com.microsoft.skype.teams.views.activities.InCallActivity")
        : canShowWhenLocked;
  }

  public PersonaManagerService getPersonaManager() {
    if (this.mPersonaManager == null) {
      this.mPersonaManager =
          ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
    }
    return this.mPersonaManager;
  }

  public final UserManagerService getUserManager() {
    if (this.mUserManager == null) {
      this.mUserManager = UserManagerService.getInstance();
    }
    return this.mUserManager;
  }

  public static boolean isActivityNeedtoFinish(ActivityRecord activityRecord, int i) {
    return activityRecord != null && activityRecord.mUserId == i;
  }

  public class PersonaActivityHandler extends Handler {
    public PersonaActivityHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 601:
          Context context = PersonaActivityHelper.this.mContext;
          Toast.makeText(
                  context, context.getText(R.string.number_picker_increment_button).toString(), 0)
              .show();
          return;
        case 602:
          PersonaActivityHelper personaActivityHelper = PersonaActivityHelper.this;
          if (personaActivityHelper.mRootWindowContainer == null) {
            return;
          }
          final int i = message.arg1;
          WindowManagerGlobalLock windowManagerGlobalLock =
              personaActivityHelper.mService.mGlobalLock;
          WindowManagerService.boostPriorityForLockedSection();
          synchronized (windowManagerGlobalLock) {
            try {
              for (int childCount =
                      PersonaActivityHelper.this.mRootWindowContainer.getChildCount() - 1;
                  childCount >= 0;
                  childCount--) {
                DisplayContent displayContent =
                    (DisplayContent)
                        PersonaActivityHelper.this.mRootWindowContainer.getChildAt(childCount);
                if (displayContent != null) {
                  displayContent.forAllLeafTasks(
                      new Consumer() { // from class:
                                       // com.android.server.wm.PersonaActivityHelper$PersonaActivityHandler$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                          PersonaActivityHelper.PersonaActivityHandler.this.lambda$handleMessage$0(
                              i, (Task) obj);
                        }
                      },
                      true);
                }
              }
            } catch (Throwable th) {
              WindowManagerService.resetPriorityAfterLockedSection();
              throw th;
            }
          }
          WindowManagerService.resetPriorityAfterLockedSection();
          PersonaActivityHelper.this.removeMinimizedFreeformTaskExplicitly(i);
          return;
        case FrameworkStatsLog.AUTOFILL_UI_EVENT_REPORTED /* 603 */:
          int i2 = message.arg1;
          Bundle bundle = (Bundle) message.obj;
          boolean z = bundle.getBoolean("isHomeActivity");
          String string = bundle.getString("componentName");
          Intent intent = new Intent("com.samsung.android.knox.container.LOCK_SECUREFOLDER");
          intent.putExtra("userid", i2);
          intent.putExtra("isHomeActivity", z);
          intent.putExtra("componentName", string);
          intent.setPackage("com.samsung.android.knox.containercore");
          PersonaActivityHelper.this.mContext.sendBroadcastAsUser(intent, UserHandle.OWNER);
          return;
        case FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED /* 604 */:
          int i3 = message.arg1;
          try {
            ITrustManager asInterface =
                ITrustManager.Stub.asInterface(ServiceManager.getService("trust"));
            if (asInterface != null) {
              asInterface.setDeviceLockedForUser(i3, true);
            } else {
              Log.d("PersonaActivityHandler", "device lock failed. trustmanager may be null.");
            }
            return;
          } catch (Exception e) {
            e.printStackTrace();
            return;
          }
        case FrameworkStatsLog.AUTOFILL_FILL_RESPONSE_REPORTED /* 605 */:
          PersonaActivityHelper.this.setFocusIfNecessary((ActivityRecord) message.obj);
          return;
        default:
          return;
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleMessage$0(int i, Task task) {
      if (task == null) {
        return;
      }
      PooledPredicate obtainPredicate =
          PooledLambda.obtainPredicate(
              new BiPredicate() { // from class:
                                  // com.android.server.wm.PersonaActivityHelper$PersonaActivityHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                  boolean isActivityNeedtoFinish;
                  isActivityNeedtoFinish =
                      PersonaActivityHelper.isActivityNeedtoFinish(
                          (ActivityRecord) obj, ((Integer) obj2).intValue());
                  return isActivityNeedtoFinish;
                }
              },
              PooledLambda.__(ActivityRecord.class),
              Integer.valueOf(i));
      boolean forAllActivities = task.forAllActivities((Predicate) obtainPredicate);
      obtainPredicate.recycle();
      if (forAllActivities) {
        try {
          if (task.inFreeformWindowingMode() && task.isMinimized()) {
            task.setUnminimizedWhenRestored();
          }
          PersonaActivityHelper.this.mService.removeTaskWithFlags(task.mTaskId, 0);
        } catch (Exception e) {
          Log.e("PersonaActivityHelper", "Failed to removeTask exception " + e);
        }
      }
    }
  }

  public final void removeMinimizedFreeformTaskExplicitly(int i) {
    try {
      List<ActivityManager.RunningTaskInfo> minimizedFreeformTasksForCurrentUser =
          MultiWindowManager.getInstance().getMinimizedFreeformTasksForCurrentUser();
      if (minimizedFreeformTasksForCurrentUser == null
          || minimizedFreeformTasksForCurrentUser.isEmpty()) {
        return;
      }
      for (ActivityManager.RunningTaskInfo runningTaskInfo : minimizedFreeformTasksForCurrentUser) {
        if (runningTaskInfo.userId == i) {
          this.mService.removeTaskWithFlags(runningTaskInfo.taskId, 16);
        }
      }
    } catch (Exception e) {
      Log.e("PersonaActivityHelper", "Failed to remove minimized Task exception " + e);
    }
  }

  public void exitAndLockSecureFolder(int i) {
    notifyKillForegroundAppsForUser(i);
    this.mPersonaActivityHandler.removeMessages(FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED);
    Message obtainMessage =
        this.mPersonaActivityHandler.obtainMessage(
            FrameworkStatsLog.AUTOFILL_FILL_REQUEST_REPORTED);
    obtainMessage.arg1 = i;
    this.mPersonaActivityHandler.sendMessage(obtainMessage);
  }

  public void notifyTaskStackChanged() {
    this.mService.getTaskChangeNotificationController().notifyTaskStackChanged();
  }

  public boolean isQuickSwitchExceptionalCase(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock;
    try {
      windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
    } catch (Exception e) {
      Log.e("PersonaActivityHelper", "Exception :: " + e.getMessage());
    }
    synchronized (windowManagerGlobalLock) {
      try {
        Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
        if (anyTaskForId == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        ActivityRecord topNonFinishingActivity = anyTaskForId.getTopNonFinishingActivity();
        if (topNonFinishingActivity == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        Intent intent = topNonFinishingActivity.intent;
        if (intent == null
            || !SemPersonaManager.isKnoxId(intent.getIntExtra("android.intent.extra.USER_ID", 0))) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return true;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }
}
