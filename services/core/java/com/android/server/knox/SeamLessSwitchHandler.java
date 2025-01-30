package com.android.server.knox;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SemStatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.os.BackgroundThread;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PersonaManagerService;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.PersonaManagerInternal;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SeamLessSwitchHandler {
  public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
  public static String packageExtraForSALog = null;

  /* renamed from: am */
  public final ActivityManager f1705am;

  /* renamed from: c */
  public final Context f1706c;
  public final KeyguardManager mKeyguardManager;
  public final LockPatternUtils mLockPatternUtils;
  public final PackageManagerService mPm;
  public final UserManager mUserManager;
  public final PersonaManagerService personaManagerService;

  /* renamed from: pm */
  public final PackageManager f1707pm;
  public final SemPersonaManager semPersonaManager;
  public final SemStatusBarManager statusBarManager;
  public List SEAMLESS_NOTALLOWED_EXCEPTIONAL_LIST = new ArrayList(Arrays.asList("android"));
  public List LAUNCHSF_HOME_LIST = new ArrayList(Arrays.asList("com.android.settings"));
  public final ActivityManagerInternal mAmInternal =
      (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
  public final PersonaManagerInternal mPersonaManagerInternal =
      (PersonaManagerInternal) LocalServices.getService(PersonaManagerInternal.class);

  public SeamLessSwitchHandler(
      Context context,
      PackageManagerService packageManagerService,
      PersonaManagerService personaManagerService) {
    this.f1706c = context;
    this.f1707pm = context.getPackageManager();
    this.mPm = packageManagerService;
    this.personaManagerService = personaManagerService;
    this.f1705am = (ActivityManager) context.getSystemService("activity");
    this.semPersonaManager = (SemPersonaManager) context.getSystemService("persona");
    this.mUserManager = (UserManager) context.getSystemService("user");
    this.statusBarManager =
        (SemStatusBarManager) context.getSystemService(SemStatusBarManager.class);
    this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
    this.mLockPatternUtils = new LockPatternUtils(context);
  }

  public void launchSeamLessForSF() {
    packageExtraForSALog = null;
    List<ActivityManager.RunningTaskInfo> runningTasks = this.f1705am.getRunningTasks(1);
    if (runningTasks == null || runningTasks.size() == 0) {
      return;
    }
    ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
    ComponentName componentName = runningTaskInfo.topActivity;
    ComponentName componentName2 = runningTaskInfo.baseActivity;
    int launchUserId = getLaunchUserId(runningTaskInfo.userId);
    if (!SemEmergencyManager.isEmergencyMode(this.f1706c)
        && !SemEmergencyManager.isMinimalBatteryUseMode(this.f1706c)
        && (launchUserId == -1 || !this.mUserManager.isUserRunning(launchUserId))) {
      Log.d("SeamLessSwitchHandler", "Quick Switch abort! User is not running");
      return;
    }
    if (SemPersonaManager.isSecureFolderId(launchUserId)
        && this.mLockPatternUtils.getKeyguardStoredPasswordQuality(launchUserId) == 0) {
      Log.d("SeamLessSwitchHandler", "Quick Switch abort! User Lock type is not set");
      return;
    }
    if (!isSeamLessSwitchSupported(runningTaskInfo, componentName, launchUserId)) {
      Context context = this.f1706c;
      Toast.makeText(context, context.getText(17042797).toString(), 0).show();
      return;
    }
    KeyguardManager keyguardManager = this.mKeyguardManager;
    if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
      if (SemPersonaManager.isSecureFolderId(runningTaskInfo.userId)
          || isSettingsExceptionalCase(runningTaskInfo, componentName, launchUserId)) {
        return;
      }
      launchSecureFolderAppsAtLockscreen(componentName);
      return;
    }
    Log.d("SeamLessSwitchHandler", "Quick Switch is triggered for " + launchUserId);
    hideNotificationPanel();
    startActivityForSecureFolderUser(componentName2, launchUserId);
  }

  public final void launchSecureFolderAppsAtLockscreen(ComponentName componentName) {
    try {
      Iterator it = this.semPersonaManager.getKnoxIds(true).iterator();
      int i = -1;
      while (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        if (SemPersonaManager.isSecureFolderId(intValue)) {
          i = intValue;
        }
      }
      if (i == -1) {
        return;
      }
      Intent intent = new Intent("android.intent.action.MAIN");
      if (isAllowedAppsInLockscreen(componentName)) {
        Log.d("SeamLessSwitchHandler", "Allowed Apps In Lockscreen Case is Running");
        intent = getLaunchIntentForPackage(componentName.getPackageName(), i);
        if (intent == null) {
          return;
        }
        intent.setPackage(null);
        intent.addFlags(2097152);
        packageExtraForSALog = componentName.getPackageName();
      } else {
        intent.setClassName(
            "com.samsung.knox.securefolder",
            isSecureFolderHidden()
                ? "com.samsung.knox.launcher.home.view.HomeActivity"
                : "com.samsung.knox.securefolder.presentation.switcher.view.SecureFolderShortcutActivity");
        intent.putExtra("userId", i);
        if (!this.mPersonaManagerInternal.shouldConfirmCredentials(i)) {
          intent.putExtra("quick_switch", true);
        }
        intent.addFlags(32768);
        packageExtraForSALog = "com.samsung.knox.securefolder";
      }
      Intent intent2 = intent;
      intent2.addFlags(268451840);
      Intent intent3 = new Intent();
      intent3.putExtra("afterKeyguardGone", true);
      intent3.putExtra("ignoreKeyguardState", true);
      intent3.putExtra("dismissIfInsecure", true);
      Context context = this.f1706c;
      if (!isSecureFolderHidden() && !isAllowedAppsInLockscreen(componentName)) {
        i = 0;
      }
      this.mKeyguardManager.semSetPendingIntentAfterUnlock(
          PendingIntent.getActivityAsUser(context, 0, intent2, 201326592, null, new UserHandle(i)),
          intent3);
    } catch (Exception e) {
      Log.d("SeamLessSwitchHandler", "Exception in setting pending intent");
      e.printStackTrace();
    }
  }

  public final boolean isAllowedAppsInLockscreen(ComponentName componentName) {
    return isCameraRunning()
        || componentName.getPackageName().equals("com.sec.android.app.popupcalculator")
        || componentName.getPackageName().equals("com.sec.android.app.voicenote");
  }

  public final void hideNotificationPanel() {
    try {
      SemStatusBarManager semStatusBarManager = this.statusBarManager;
      if (semStatusBarManager == null || !semStatusBarManager.isPanelExpanded()) {
        return;
      }
      Log.d("SeamLessSwitchHandler", "Collapsing notification panel before quick switch");
      this.statusBarManager.collapsePanels();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final boolean isSeamLessSwitchSupported(
      ActivityManager.RunningTaskInfo runningTaskInfo, ComponentName componentName, int i) {
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (SemEmergencyManager.isEmergencyMode(this.f1706c)
        || SemEmergencyManager.isMinimalBatteryUseMode(this.f1706c)
        || this.SEAMLESS_NOTALLOWED_EXCEPTIONAL_LIST.contains(componentName.getPackageName())
        || runningTaskInfo.configuration.windowConfiguration.getWindowingMode() != 1
        || isPopupWindowRunning()
        || this.f1705am.isInLockTaskMode()) {
      return false;
    }
    if ((getLaunchUserId(runningTaskInfo.userId) != 0
            || !SemPersonaManager.isSecureFolderId(runningTaskInfo.userId))
        && !SemPersonaManager.isSecureFolderId(getLaunchUserId(runningTaskInfo.userId))) {
      return false;
    }
    if (isSettingsExceptionalCase(runningTaskInfo, componentName, i)) {
      KeyguardManager keyguardManager = this.mKeyguardManager;
      if (keyguardManager != null) {
        if (!keyguardManager.isKeyguardLocked()) {}
      }
      return false;
    }
    return true;
  }

  public final void startActivityForSecureFolderUser(ComponentName componentName, int i) {
    if (componentName == null) {
      return;
    }
    try {
      String packageName = componentName.getPackageName();
      if (isPackageEnabled(packageName, i)) {
        Intent launchIntentForPackage = getLaunchIntentForPackage(packageName, i);
        if (launchIntentForPackage != null && isAppLaunchable(packageName, i)) {
          launchIntentForPackage.setPackage(null);
          launchIntentForPackage.addFlags(270548992);
          this.f1706c.startActivityAsUser(launchIntentForPackage, new UserHandle(i));
          printToastDelayed(i, true);
          packageExtraForSALog = packageName;
          return;
        }
        Log.d("SeamLessSwitchHandler", "No launchable component for " + packageName);
        launchFolderContainerOrHome(i, false);
        return;
      }
      launchFolderContainerOrHome(i, false);
    } catch (Exception e) {
      Log.d("SeamLessSwitchHandler", "Exception in securefolder quick switch");
      e.printStackTrace();
    }
  }

  public final void printToastDelayed(final int i, boolean z) {
    new Handler(Looper.getMainLooper())
        .postDelayed(
            new Runnable() { // from class:
                             // com.android.server.knox.SeamLessSwitchHandler$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SeamLessSwitchHandler.this.lambda$printToastDelayed$0(i);
              }
            },
            z ? 500L : 300L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$printToastDelayed$0(int i) {
    if (!SemPersonaManager.isSecureFolderId(i)
        || this.mPersonaManagerInternal.shouldConfirmCredentials(i)) {
      return;
    }
    Context context = this.f1706c;
    Toast.makeText(
            context,
            String.format(
                context.getText(17042798).toString(),
                this.personaManagerService.getContainerName(i)),
            0)
        .show();
  }

  public final void launchFolderContainerOrHome(int i, boolean z) {
    Intent intent = new Intent("android.intent.action.MAIN");
    if (SemPersonaManager.isSecureFolderId(i) && !z) {
      intent.setClassName(
          "com.samsung.knox.securefolder",
          isSecureFolderHidden()
              ? "com.samsung.knox.launcher.home.view.HomeActivity"
              : "com.samsung.knox.securefolder.presentation.switcher.view.SecureFolderShortcutActivity");
      intent.putExtra("userId", i);
      if (!this.mPersonaManagerInternal.shouldConfirmCredentials(i)) {
        intent.putExtra("quick_switch", true);
      }
      intent.addFlags(268435456);
      intent.addFlags(49152);
      intent.addFlags(65536);
      this.f1706c.startActivityAsUser(intent, new UserHandle(isSecureFolderHidden() ? i : 0));
      printToastDelayed(i, true);
      packageExtraForSALog = "com.samsung.knox.securefolder";
      return;
    }
    intent.addCategory("android.intent.category.HOME");
    intent.setFlags(268435456);
    ResolveInfo resolveActivityAsUser =
        this.f1706c.getPackageManager().resolveActivityAsUser(intent, 65536, 0);
    if (resolveActivityAsUser == null) {
      return;
    }
    ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
    intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
    intent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
    this.f1706c.startActivityAsUser(intent, new UserHandle(0));
    packageExtraForSALog = resolveActivityAsUser.activityInfo.packageName;
  }

  public final int getLaunchUserId(int i) {
    Iterator it = this.semPersonaManager.getKnoxIds(true).iterator();
    int i2 = 0;
    boolean z = false;
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      if (SemPersonaManager.isSecureFolderId(intValue)) {
        z = true;
        i2 = intValue;
      }
    }
    int i3 = i == 0 ? i2 : 0;
    if (z) {
      return i3;
    }
    return -1;
  }

  public final boolean isPackageEnabled(String str, int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    boolean z = false;
    try {
      if ("com.samsung.knox.securefolder".equals(str) && i == 0) {
        return false;
      }
      if (this.LAUNCHSF_HOME_LIST.contains(str)) {
        return false;
      }
      PackageInfo packageInfo = getIPackageManager().getPackageInfo(str, 786432L, i);
      if (packageInfo != null) {
        if (packageInfo.applicationInfo.enabled) {
          z = true;
        }
      }
      return z;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final IPackageManager getIPackageManager() {
    return ActivityThread.getPackageManager();
  }

  public final boolean isPopupWindowRunning() {
    try {
      for (ActivityManager.RunningTaskInfo runningTaskInfo : this.f1705am.getRunningTasks(10)) {
        if (runningTaskInfo != null && runningTaskInfo.semIsFreeform()) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public final boolean isAppLaunchable(String str, int i) {
    List queryIntentActivitiesAsUser =
        this.f1707pm.queryIntentActivitiesAsUser(
            new Intent("android.intent.action.MAIN", (Uri) null)
                .addCategory("android.intent.category.LAUNCHER")
                .setPackage(str),
            786432,
            i);
    return (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() == 0)
        ? false
        : true;
  }

  public final boolean isSettingsExceptionalCase(
      ActivityManager.RunningTaskInfo runningTaskInfo, ComponentName componentName, int i) {
    try {
      if ((!SemPersonaManager.isSecureFolderId(i)
              && this.personaManagerService.getFocusedUser() != 0)
          || !"com.android.settings".equals(componentName.getPackageName())
          || !((ActivityTaskManagerService) ServiceManager.getService("activity_task"))
              .getPersonaActivityHelper()
              .isQuickSwitchExceptionalCase(runningTaskInfo.id)) {
        return false;
      }
      Log.d("SeamLessSwitchHandler", "Exceptional case quick switch! securefolder keyguard");
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public final boolean isCameraRunning() {
    return SystemProperties.get("service.camera.running", "0").equals("1");
  }

  public final Intent getLaunchIntentForPackage(String str, int i) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.INFO");
    intent.setPackage(str);
    List queryIntentActivitiesAsUser = this.f1707pm.queryIntentActivitiesAsUser(intent, 0, i);
    if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
      intent.removeCategory("android.intent.category.INFO");
      intent.addCategory("android.intent.category.LAUNCHER");
      intent.setPackage(str);
      queryIntentActivitiesAsUser = this.f1707pm.queryIntentActivitiesAsUser(intent, 0, i);
    }
    if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
      return null;
    }
    Intent intent2 = new Intent(intent);
    intent2.setFlags(268435456);
    intent2.setClassName(
        ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.packageName,
        ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.name);
    return intent2;
  }

  public final boolean isSecureFolderHidden() {
    return Settings.Secure.getIntForUser(
            this.f1706c.getContentResolver(), "hide_secure_folder_flag", 0, 0)
        == 1;
  }

  public void insertSALog(final String str, final String str2) {
    BackgroundThread.getHandler()
        .post(
            new Runnable() { // from class: com.android.server.knox.SeamLessSwitchHandler.1
              @Override // java.lang.Runnable
              public void run() {
                try {
                  Bundle bundle = new Bundle();
                  bundle.putString("tracking_id", "493-399-9953101");
                  bundle.putString("type", "ev");
                  bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
                  String str3 = str2;
                  if (str3 != null) {
                    bundle.putString("extra", str3);
                  }
                  Intent intent = new Intent();
                  intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
                  intent.putExtras(bundle);
                  intent.setPackage("com.sec.android.diagmonagent");
                  SeamLessSwitchHandler.this.f1706c.sendBroadcast(intent);
                  if (SeamLessSwitchHandler.DEBUG) {
                    Log.d(
                        "SeamLessSwitchHandler",
                        "insertSALog tracking_id=493-399-9953101, feature="
                            + str
                            + " ,extra="
                            + str2);
                  }
                } catch (Exception e) {
                  Log.d("SeamLessSwitchHandler", "insertSALog Exception");
                  e.printStackTrace();
                }
              }
            });
  }
}
