package com.android.server.knox;

import android.R;
import android.app.ActivityThread;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper;
import com.android.server.enterprise.dualdar.DualDARStorageHelper;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.ddar.DDCache;
import com.android.server.pm.KnoxMUMContainerPolicyInternal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.AppControlInfo;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.container.ContainerCreationParams;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ContainerDependencyWrapper {
  public static Context context;
  public static UserManager mUserManager;
  public static ContainerDependencyWrapper sInstance;
  public DarManagerService mDarService = null;
  public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
  public static EdmStorageProvider mEdmStorageProvider = null;
  public static DevicePolicyManager mDevicePolicyManager = null;

  public static byte[] convertToGreyIcon(byte[] bArr) {
    return bArr;
  }

  public static String getECName(int i) {
    return null;
  }

  /* renamed from: getSecProductFeature_SEC_PRODUCT_FEATURE_KNOX_CONFIG_SECURE_FOLDER_VERSION */
  public static String m32x41c9ca1() {
    return "2";
  }

  public static boolean isSecureFolderPkgAvailable() {
    return true;
  }

  public void callOnCMFALocked(int i) {}

  public ContainerDependencyWrapper(Context context2) {
    context = context2;
  }

  public static ContainerDependencyWrapper getInstance(Context context2) {
    if (sInstance == null) {
      sInstance = new ContainerDependencyWrapper(context2);
    }
    return sInstance;
  }

  public static int checkCallerPermissionFor(Context context2, String str, String str2) {
    if (ServiceKeeper.isAuthorized(
            context2, Binder.getCallingPid(), Binder.getCallingUid(), str, str2)
        == 0) {
      return 0;
    }
    SecurityException securityException =
        new SecurityException(
            "Security Exception Occurred while pid["
                + Binder.getCallingPid()
                + "] with uid["
                + Binder.getCallingUid()
                + "] trying to access methodName ["
                + str2
                + "] in ["
                + str
                + "] service");
    securityException.printStackTrace();
    throw securityException;
  }

  public static boolean isDualDarDO(int i) {
    return DualDarManager.isOnDeviceOwner(i);
  }

  public static Bundle getDualDARProfile(Context context2) {
    return DualDARStorageHelper.getDualDARProfile(context2);
  }

  public static int setDualDARProfile(Context context2, Bundle bundle) {
    return DualDARStorageHelper.setDualDARProfile(context2, bundle);
  }

  public static boolean isDualDARTrialPeriod(Context context2) {
    String str = DDCache.getInstance(context2).get(0, "IS_DUAL_DAR_TRIAL_PERIOD");
    return !TextUtils.isEmpty(str) && str.equalsIgnoreCase("TRUE");
  }

  public static boolean isDualDARIntentProvision(Context context2) {
    String str = DDCache.getInstance(context2).get(0, "IS_DUAL_DAR_INTENT_PROVISIONING");
    return !TextUtils.isEmpty(str) && str.equalsIgnoreCase("TRUE");
  }

  public static int getDualDARType(int i) {
    return PersonaServiceHelper.getDualDARType(i);
  }

  public static EdmStorageProvider getEdmStorageProvider(Context context2) {
    if (mEdmStorageProvider == null) {
      mEdmStorageProvider = new EdmStorageProvider(context2);
    }
    return mEdmStorageProvider;
  }

  public static void addAppPackageNameToAllowList(Context context2, int i, List list) {
    ContextInfo contextInfo =
        new ContextInfo(new EdmStorageProvider(context2).getMUMContainerOwnerUid(i), i);
    try {
      IApplicationPolicy asInterface =
          IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
      if (asInterface == null) {
        return;
      }
      Iterator it = list.iterator();
      while (it.hasNext()) {
        String str = (String) it.next();
        Log.d("ContainerDependencyWrapper", "add package to Allowlist : " + str);
        asInterface.addAppPackageNameToWhiteList(contextInfo, str);
      }
    } catch (RemoteException e) {
      Log.e("ContainerDependencyWrapper", "Exception: " + e.getMessage());
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x0047  */
  /* JADX WARN: Removed duplicated region for block: B:24:0x0088  */
  /* JADX WARN: Removed duplicated region for block: B:47:0x00d5  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static void setApplicationBlackList(Context context2, List list, int i) {
    IApplicationPolicy iApplicationPolicy;
    int i2;
    HashMap hashMap = new HashMap();
    ContextInfo contextInfo =
        new ContextInfo(getEdmStorageProvider(context2).getMUMContainerOwnerUid(i), i);
    List list2 = null;
    try {
      iApplicationPolicy =
          IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
    } catch (RemoteException e) {
      e = e;
      iApplicationPolicy = null;
    }
    if (iApplicationPolicy == null) {
      return;
    }
    try {
      list2 = iApplicationPolicy.getAppPackageNamesAllBlackLists(contextInfo);
    } catch (RemoteException e2) {
      e = e2;
      Log.e("ContainerDependencyWrapper", "Exception: " + e.getMessage());
      if (list2 != null) {}
      String[] strArr = {"com.android.vending", "com.sec.android.app.samsungapps"};
      while (i2 < list.size()) {}
      while (r12.hasNext()) {}
    }
    if (list2 != null) {
      for (int i3 = 0; i3 < list2.size(); i3++) {
        for (int i4 = 0; i4 < ((AppControlInfo) list2.get(i3)).entries.size(); i4++) {
          hashMap.put((String) ((AppControlInfo) list2.get(i3)).entries.get(i4), 1);
        }
      }
    }
    String[] strArr2 = {"com.android.vending", "com.sec.android.app.samsungapps"};
    for (i2 = 0; i2 < list.size(); i2++) {
      boolean z = false;
      for (int i5 = 0; i5 < 2; i5++) {
        if (strArr2[i5].equals(list.get(i2))) {
          z = true;
        }
      }
      if (!z) {
        if (hashMap.containsKey(list.get(i2))) {
          hashMap.put((String) list.get(i2), 0);
        } else {
          hashMap.put((String) list.get(i2), -1);
        }
      }
    }
    for (Map.Entry entry : hashMap.entrySet()) {
      int intValue = ((Integer) entry.getValue()).intValue();
      if (intValue == -1) {
        try {
          iApplicationPolicy.addAppPackageNameToBlackList(contextInfo, (String) entry.getKey());
        } catch (RemoteException e3) {
          Log.e("ContainerDependencyWrapper", "Exception: " + e3.getMessage());
        }
      } else if (intValue == 1) {
        try {
          iApplicationPolicy.removeAppPackageNameFromBlackList(
              contextInfo, (String) entry.getKey());
        } catch (RemoteException e4) {
          Log.e("ContainerDependencyWrapper", "Exception: " + e4.getMessage());
        }
      }
    }
  }

  public static ComponentName getAdminComponentNameFromEdm(Context context2, int i) {
    EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context2);
    return edmStorageProvider.getComponentNameForUid(edmStorageProvider.getMUMContainerOwnerUid(i));
  }

  public static int getOwnerUidFromEdm(Context context2, int i) {
    return getEdmStorageProvider(context2).getMUMContainerOwnerUid(i);
  }

  public static boolean isSupportPrivateMode() {
    return SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportPrivateMode", false);
  }

  public static String getContactsPkgName() {
    return SemFloatingFeature.getInstance()
        .getString("SEC_FLOATING_FEATURE_CONTACTS_CONFIG_PACKAGE_NAME");
  }

  public static boolean setAttributes(UserManagerInternal userManagerInternal, int i, int i2) {
    return userManagerInternal.setAttributes(i, i2);
  }

  public static boolean clearAttributes(UserManagerInternal userManagerInternal, int i, int i2) {
    return userManagerInternal.clearAttributes(i, i2);
  }

  public static int getAttributes(UserManagerInternal userManagerInternal, int i) {
    return userManagerInternal.getAttributes(i);
  }

  public static boolean isPackageInstalled(
      PackageManagerService packageManagerService, String str) {
    return packageManagerService.isPackageInstalled(str);
  }

  public static int installExistingPackageForPersona(
      PackageManagerService packageManagerService, int i, String str) {
    return packageManagerService.installExistingPackageForPersona(i, str);
  }

  public static void deletePackageAsUserAndPersona(
      PackageManagerService packageManagerService,
      String str,
      IPackageDeleteObserver iPackageDeleteObserver,
      int i,
      int i2) {
    packageManagerService.deletePackageAsUserAndPersona(str, iPackageDeleteObserver, i, i2);
  }

  public static boolean setPackageSettingInstalled(
      PackageManagerService packageManagerService, String str, boolean z, int i) {
    return packageManagerService.setPackageSettingInstalled(str, z, i);
  }

  public static void setContainerInfo(UserManagerService userManagerService) {
    userManagerService.setContainerInfo();
  }

  public static boolean isKnoxWindowExist(int i, int i2, int i3) {
    return ((ActivityTaskManagerService) ServiceManager.getService("activity_task"))
        .getPersonaActivityHelper()
        .isKnoxWindowVisibleLocked(false, i, false, i2, i3);
  }

  public static void notifyWorkTaskStackChanged() {
    ((ActivityTaskManagerService) ServiceManager.getService("activity_task"))
        .getPersonaActivityHelper()
        .notifyTaskStackChanged();
  }

  public static void updateProfileActivityTimeFromKnox(
      PowerManagerInternal powerManagerInternal, int i, long j) {
    powerManagerInternal.updateProfileActivityTimeFromKnox(i, j);
  }

  public static void setMaximumScreenOffTimeoutFromKnox(
      PowerManagerInternal powerManagerInternal, int i, long j) {
    powerManagerInternal.setMaximumScreenOffTimeoutFromKnox(i, j);
  }

  public static void unsetTwoFactorValueIfNeeded(
      Context context2, LockPatternUtils lockPatternUtils, int i) {
    if ((Settings.Secure.getIntForUser(
                context2.getContentResolver(), "knox_finger_print_plus", 0, i)
            == 1)
        && lockPatternUtils.getBiometricType(i) == 0) {
      Settings.Secure.putIntForUser(context2.getContentResolver(), "knox_finger_print_plus", 0, i);
      Log.d("ContainerDependencyWrapper", "unset two-factor setting value.");
    }
  }

  public static void clearStorageForUser(LockSettingsInternal lockSettingsInternal, int i) {
    lockSettingsInternal.clearStorageForUser(i);
  }

  public static boolean isRequiredAppForKnox(String str, int i) {
    return PersonaServiceHelper.isRequiredAppForKnox(str, i);
  }

  public static boolean isDisallowedAppForKnox(String str, int i) {
    return PersonaServiceHelper.isDisallowedAppForKnox(str, i);
  }

  public static void handlePwdChangeNotificationForDeviceOwner(Context context2, int i) {
    if (SemPersonaManager.isKnoxId(i)) {
      return;
    }
    PendingIntent activity = PendingIntent.getActivity(context2, 0, new Intent(), 1275068416);
    String charSequence = context2.getText(R.string.heavy_weight_switcher_text).toString();
    String charSequence2 = context2.getText(R.string.heavy_weight_notification_detail).toString();
    Notification build =
        new Notification.Builder(context2, SystemNotificationChannels.DEVICE_ADMIN)
            .setSmallIcon(R.drawable.ic_test_badge_experiment)
            .setWhen(0L)
            .setOngoing(true)
            .setTicker(charSequence)
            .setColor(context2.getColor(R.color.system_notification_accent_color))
            .setContentTitle(charSequence)
            .setContentText(charSequence2)
            .setVisibility(1)
            .setContentIntent(activity)
            .setStyle(new Notification.BigTextStyle().bigText(charSequence2))
            .setAutoCancel(true)
            .build();
    build.semFlags |= 8;
    ((NotificationManager) context2.getSystemService("notification")).notify(1111, build);
  }

  public static boolean isTablet() {
    String str = SystemProperties.get("ro.build.characteristics");
    return str != null && str.contains("tablet");
  }

  public static void revokeSUWAgreements(UserManager userManager, Context context2) {
    String str;
    List<UserInfo> users = userManager.getUsers(true);
    if (users != null) {
      for (UserInfo userInfo : users) {
        if ((userInfo != null && userInfo.isManagedProfile() && !userInfo.isSecureFolder())
            || SemPersonaManager.isDoEnabled(0)) {
          int i =
              Settings.System.getInt(context2.getContentResolver(), "samsung_errorlog_agree", 0);
          int i2 = Settings.System.getInt(context2.getContentResolver(), "marketing_info_agree", 0);
          Log.e("ContainerDependencyWrapper", "1. errorLogAgree = " + i);
          Log.e("ContainerDependencyWrapper", "1. marketingInfoAgree = " + i2);
          if (i == 0 && i2 == 0) {
            return;
          }
          if (i != 0 && i2 != 0) {
            Settings.System.putInt(context2.getContentResolver(), "samsung_errorlog_agree", 0);
            Settings.System.putInt(context2.getContentResolver(), "marketing_info_agree", 0);
            Log.e(
                "ContainerDependencyWrapper",
                "2. errorLogAgree = "
                    + Settings.System.getInt(
                        context2.getContentResolver(), "samsung_errorlog_agree", 0));
            Log.e(
                "ContainerDependencyWrapper",
                "2. marketingInfoAgree = "
                    + Settings.System.getInt(
                        context2.getContentResolver(), "marketing_info_agree", 0));
            str =
                isTablet()
                    ? context2.getString(17042465)
                        + "\n - "
                        + context2.getString(R.string.gnss_time_update_service)
                        + "\n - "
                        + context2.getString(R.string.permdesc_sdcardRead)
                    : context2.getString(17042464)
                        + "\n - "
                        + context2.getString(R.string.gnss_time_update_service)
                        + "\n - "
                        + context2.getString(R.string.permdesc_sdcardRead);
          } else if (i != 0 && i2 == 0) {
            Settings.System.putInt(context2.getContentResolver(), "samsung_errorlog_agree", 0);
            Log.e(
                "ContainerDependencyWrapper",
                "3. errorLogAgree = "
                    + Settings.System.getInt(
                        context2.getContentResolver(), "samsung_errorlog_agree", 0));
            Log.e(
                "ContainerDependencyWrapper",
                "3. marketingInfoAgree = "
                    + Settings.System.getInt(
                        context2.getContentResolver(), "marketing_info_agree", 0));
            str =
                isTablet()
                    ? context2.getString(17042463)
                        + "\n - "
                        + context2.getString(R.string.gnss_time_update_service)
                    : context2.getString(17042462)
                        + "\n - "
                        + context2.getString(R.string.gnss_time_update_service);
          } else {
            Settings.System.putInt(context2.getContentResolver(), "marketing_info_agree", 0);
            Log.e(
                "ContainerDependencyWrapper",
                "4. errorLogAgree = "
                    + Settings.System.getInt(
                        context2.getContentResolver(), "samsung_errorlog_agree", 0));
            Log.e(
                "ContainerDependencyWrapper",
                "4. marketingInfoAgree = "
                    + Settings.System.getInt(
                        context2.getContentResolver(), "marketing_info_agree", 0));
            str =
                isTablet()
                    ? context2.getString(17042463)
                        + "\n - "
                        + context2.getString(R.string.permdesc_sdcardRead)
                    : context2.getString(17042462)
                        + "\n - "
                        + context2.getString(R.string.permdesc_sdcardRead);
          }
          Toast.makeText(context2, str, 0).show();
        }
      }
    }
  }

  public static ContextInfo getContextInfo() {
    return new ContextInfo();
  }

  public static ContextInfo getContextInfo(int i, int i2) {
    return new ContextInfo(i, i2);
  }

  public static boolean isKnoxProfileActivePasswordSufficientForParent(
      UserManager userManager, int i) {
    UserInfo profileParent;
    IPasswordPolicy asInterface;
    UserInfo userInfo = userManager.getUserInfo(i);
    if (userInfo == null
        || !userInfo.isManagedProfile()
        || !userInfo.isPremiumContainer()
        || (profileParent = userManager.getProfileParent(i)) == null
        || profileParent.id != 0
        || (asInterface =
                IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy")))
            == null) {
      return true;
    }
    ContextInfo contextInfo = getContextInfo();
    if (DEBUG) {
      try {
        Log.d(
            "ContainerDependencyWrapper",
            "isKnoxProfileActivePasswordSufficientForParent getForbiddenStrings = "
                + asInterface.getForbiddenStrings(contextInfo, true)
                + " getMaximumCharacterOccurences = "
                + asInterface.getMaximumCharacterOccurences(contextInfo)
                + " getMaximumCharacterSequenceLength = "
                + asInterface.getMaximumCharacterSequenceLength(contextInfo)
                + " getMaximumNumericSequenceLength = "
                + asInterface.getMaximumNumericSequenceLength(contextInfo)
                + " getMinimumCharacterChangeLength = "
                + asInterface.getMinimumCharacterChangeLength(contextInfo)
                + " getRequiredPwdPatternRestrictions = "
                + asInterface.getRequiredPwdPatternRestrictions(contextInfo, true)
                + " isMultifactorAuthenticationEnabled = "
                + asInterface.isMultifactorAuthenticationEnabled(contextInfo)
                + " getPasswordHistoryLength = "
                + asInterface.getPasswordHistoryLength(contextInfo, (ComponentName) null));
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
    try {
      if (asInterface.getForbiddenStrings(contextInfo, true) != null
          || asInterface.getMaximumCharacterOccurences(contextInfo) != 0
          || asInterface.getMaximumCharacterSequenceLength(contextInfo) != 0
          || asInterface.getMaximumNumericSequenceLength(contextInfo) != 0
          || asInterface.getMinimumCharacterChangeLength(contextInfo) != 0
          || asInterface.getRequiredPwdPatternRestrictions(contextInfo, true) != null
          || asInterface.isMultifactorAuthenticationEnabled(contextInfo)
          || asInterface.getPasswordHistoryLength(contextInfo, (ComponentName) null) != 0) {
        Log.d(
            "ContainerDependencyWrapper",
            "Not sufficient for knox profile active password for parent");
        return false;
      }
    } catch (RemoteException e2) {
      e2.printStackTrace();
    }
    return true;
  }

  public static Bundle getAppSeparationConfig() {
    return KnoxContainerManager.getAppSeparationConfig();
  }

  public static void applyDefaultPolicyForAppSeparation(Context context2, int i) {
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (ActivityThread.getPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) == 0) {
      Log.d("ContainerDependencyWrapper", "applyDefaultPolicyForAppSeparation");
      try {
        EnterpriseKnoxManager.getInstance()
            .getKnoxContainerManager(context2, getContextInfo(getOwnerUidFromEdm(context2, i), i))
            .getContainerConfigurationPolicy()
            .enableNFC(true, (Bundle) null);
        return;
      } catch (Exception e2) {
        Log.d("ContainerDependencyWrapper", "exception applyDefaultPolicyForAppSeparation" + e2);
        return;
      }
    }
    Log.e("ContainerDependencyWrapper", "applyDefaultPolicyForAppSeparation failed");
  }

  public static void setOwnership(Context context2, int i) {
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (ActivityThread.getPackageManager().checkUidSignatures(1000, Binder.getCallingUid()) == 0) {
      Log.d("ContainerDependencyWrapper", "setOwnership");
      try {
        ContainerCreationParams containerCreationParams = new ContainerCreationParams();
        containerCreationParams.setAdminParam("com.samsung.android.appseparation");
        containerCreationParams.setContainerId(i);
        containerCreationParams.setAdminUid(
            context2
                .getPackageManager()
                .getPackageUidAsUser("com.samsung.android.appseparation", i));
        ((KnoxMUMContainerPolicyInternal)
                LocalServices.getService(KnoxMUMContainerPolicyInternal.class))
            .setAppSeparationOwnership(containerCreationParams);
        return;
      } catch (Exception e2) {
        Log.d("ContainerDependencyWrapper", "exception setOwnership" + e2);
        return;
      }
    }
    Log.e("ContainerDependencyWrapper", "setOwnership failed");
  }

  public static UserManager getUserManager() {
    if (mUserManager == null) {
      mUserManager = (UserManager) context.getSystemService("user");
    }
    return mUserManager;
  }

  public static boolean isPasswordSufficientAfterKnoxProfileUnification(int i) {
    UserInfo profileParent;
    UserInfo userInfo = getUserManager().getUserInfo(i);
    if (userInfo != null
        && userInfo.isManagedProfile()
        && userInfo.isPremiumContainer()
        && (profileParent = getUserManager().getProfileParent(i)) != null
        && profileParent.id == 0) {
      try {
        PasswordPolicy passwordPolicy =
            EnterpriseKnoxManager.getInstance()
                .getKnoxContainerManager(context, getContextInfo(getOwnerUidFromEdm(context, i), i))
                .getPasswordPolicy();
        if (DEBUG) {
          Log.d(
              "ContainerDependencyWrapper",
              "isPasswordSufficientAfterKnoxProfileUnification getForbiddenStrings = "
                  + passwordPolicy.getForbiddenStrings(true)
                  + " getMaximumCharacterOccurences = "
                  + passwordPolicy.getMaximumCharacterOccurences()
                  + " getMaximumCharacterSequenceLength = "
                  + passwordPolicy.getMaximumCharacterSequenceLength()
                  + " getMaximumNumericSequenceLength = "
                  + passwordPolicy.getMaximumNumericSequenceLength()
                  + " getMinimumCharacterChangeLength = "
                  + passwordPolicy.getMinimumCharacterChangeLength()
                  + " getRequiredPwdPatternRestrictions = "
                  + passwordPolicy.getRequiredPwdPatternRestrictions(true)
                  + " isMultifactorAuthenticationEnabled = "
                  + passwordPolicy.isMultifactorAuthenticationEnabled());
        }
        if (passwordPolicy.getForbiddenStrings(true) != null
            || passwordPolicy.getMaximumCharacterOccurences() != 0
            || passwordPolicy.getMaximumCharacterSequenceLength() != 0
            || passwordPolicy.getMaximumNumericSequenceLength() != 0
            || passwordPolicy.getMinimumCharacterChangeLength() != 0
            || passwordPolicy.getRequiredPwdPatternRestrictions(true) != null
            || passwordPolicy.isMultifactorAuthenticationEnabled()) {
          Log.d("ContainerDependencyWrapper", "Not sufficient for current parent password");
          return false;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public static IEnterpriseDeviceManager getEdmService(Context context2) {
    return IEnterpriseDeviceManager.Stub.asInterface(
        ServiceManager.getService("enterprise_policy"));
  }

  public static boolean isSecureFolderAdminActive(Context context2, ComponentName componentName) {
    if (getEdmService(context2) == null) {
      return false;
    }
    try {
      return getEdmService(context2).isAdminActive(componentName);
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void deactivateSecureFolderAdmin(Context context2, ComponentName componentName) {
    try {
      IEnterpriseDeviceManager edmService = getEdmService(context2);
      if (edmService != null) {
        edmService.setAdminRemovable(getContextInfo(), true, "com.samsung.knox.securefolder");
        edmService.removeActiveAdmin(componentName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getCustomResource(int i, String str) {
    return KnoxContainerManager.getCustomResource(i, str);
  }

  public static boolean isExternalStorageEnabled(int i) {
    IKnoxContainerManager asInterface =
        IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy"));
    if (asInterface == null) {
      Log.e("ContainerDependencyWrapper", "ContainerPolicy Service is not yet ready!!!");
      return false;
    }
    try {
      return asInterface.isExternalStorageEnabled(getContextInfo(Binder.getCallingUid(), i));
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isPwdChangeRequested(int i) {
    try {
      IPasswordPolicy asInterface =
          IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
      if (asInterface != null) {
        return asInterface.isChangeRequestedAsUser(i) > 0;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
