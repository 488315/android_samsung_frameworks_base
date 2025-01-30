package com.android.server.knox;

import android.app.ActivityThread;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PersonaManagerService;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.license.IEnterpriseLicense;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class IKnoxAnalyticsContainerImpl implements IKnoxAnalyticsContainer {
  public final String TAG = "IFKnoxAnalyticsContainer";
  public DevicePolicyManager devicePolicyManager;
  public final Context mContext;
  public final PackageManagerService mPm;
  public final PersonaManagerService personaManagerService;
  public UserManager userManager;

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isLegacyClId(int i) {
    return false;
  }

  public final UserManager getUserManager() {
    if (this.userManager == null) {
      this.userManager = (UserManager) this.mContext.getSystemService("user");
    }
    return this.userManager;
  }

  public final DevicePolicyManager getDevicePolicyManager() {
    if (this.devicePolicyManager == null) {
      this.devicePolicyManager =
          (DevicePolicyManager) this.mContext.getSystemService("device_policy");
    }
    return this.devicePolicyManager;
  }

  public IKnoxAnalyticsContainerImpl(
      Context context,
      PackageManagerService packageManagerService,
      PersonaManagerService personaManagerService) {
    this.mPm = packageManagerService;
    this.personaManagerService = personaManagerService;
    this.mContext = context;
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public long getSeparatedAppsContainerId() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        return getUserManager()
            .getUserInfo(this.personaManagerService.getAppSeparationId())
            .creationTime;
      } catch (Exception e) {
        e.printStackTrace();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return 0L;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getSeparatedAppsUserId() {
    return this.personaManagerService.getAppSeparationId();
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public void sendAnalyticsLog(KnoxAnalyticsData knoxAnalyticsData) {
    if (knoxAnalyticsData == null
        || knoxAnalyticsData.getPayload() == null
        || knoxAnalyticsData.getPayload().getInt("cTp") != -1) {
      KnoxAnalytics.log(knoxAnalyticsData);
    }
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isSecureFolderId(int i) {
    return SemPersonaManager.isSecureFolderId(i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isKnoxId(int i) {
    return SemPersonaManager.isKnoxId(i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public PackageInfo getPackageInfo(String str, int i) {
    try {
      return getIPackageManager().getPackageInfo(str, 0L, i);
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean hasUserRestriction(String str, int i) {
    return getUserManager().hasUserRestriction(str, new UserHandle(i));
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isSeparatedAppsIndepdentApp(PackageInfo packageInfo) {
    return this.personaManagerService.isAppSeparationIndepdentApp(packageInfo);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public Bundle getSeparatedAppsConfig() {
    return KnoxContainerManager.getAppSeparationConfig();
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public String getInstallerPackageName(String str) {
    try {
      return getIPackageManager().getInstallerPackageName(str);
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isPremiumLicenseActivated(int i) {
    return isKnoxMode(i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isDoEnabled(int i) {
    return SemPersonaManager.isDoEnabled(i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public boolean isOrganizationOwned() {
    return "true".equals(SystemProperties.get("ro.organization_owned"));
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getKnoxScreenTimeOut(int i) {
    if (SemPersonaManager.isSecureFolderId(i)) {
      return Settings.System.getIntForUser(
          this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
    }
    return Settings.Secure.getIntForUser(
        this.mContext.getContentResolver(), "knox_screen_off_timeout", -1, i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getKnoxFingerprintPlus(int i) {
    return Settings.System.getIntForUser(
        this.mContext.getContentResolver(), "knox_finger_print_plus", 0, i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public String getDisabledPrintServices(int i) {
    return Settings.Secure.getStringForUser(
        this.mContext.getContentResolver(), "disabled_print_services", i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getLockScreenAllowPrivateNotification(int i) {
    return Settings.Secure.getIntForUser(
        this.mContext.getContentResolver(), "lock_screen_allow_private_notifications", 0, i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getLocationProvidersAllowed(int i) {
    return Settings.Secure.getIntForUser(
        this.mContext.getContentResolver(), "location_providers_allowed", 0, i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getActivePasswordQuality(int i) {
    return new LockPatternUtils(this.mContext).getActivePasswordQuality(i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int getCallerIdToShow(int i) {
    UserInfo userInfo = getUserManager().getUserInfo(i);
    return Settings.System.getIntForUser(
        this.mContext.getContentResolver(), "caller_id_to_show_" + userInfo.name, 1, i);
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public ComponentName getAdminComponentForLegacy(int i) {
    return ContainerDependencyWrapper.getAdminComponentNameFromEdm(this.mContext, i);
  }

  public final boolean isKnoxMode(int i) {
    String packageName =
        getDevicePolicyManager().getProfileOwnerAsUser(new UserHandle(i)).getPackageName();
    String deviceOwnerPackage = getDeviceOwnerPackage();
    boolean hasKnoxPermission = packageName != null ? hasKnoxPermission(packageName) : true;
    boolean hasKnoxPermission2 =
        deviceOwnerPackage != null ? hasKnoxPermission(deviceOwnerPackage) : true;
    Log.d(
        "IFKnoxAnalyticsContainer",
        "["
            + packageName
            + "] = "
            + hasKnoxPermission
            + " , ["
            + deviceOwnerPackage
            + "] : "
            + deviceOwnerPackage);
    return hasKnoxPermission & hasKnoxPermission2;
  }

  public final boolean hasKnoxPermission(String str) {
    try {
      IEnterpriseLicense asInterface =
          IEnterpriseLicense.Stub.asInterface(
              ServiceManager.getService("enterprise_license_policy"));
      if (asInterface != null) {
        List eLMPermissions = asInterface.getELMPermissions(str);
        if (eLMPermissions != null) {
          return eLMPermissions.size() != 0;
        }
        return false;
      }
    } catch (Exception e) {
      Log.d("IFKnoxAnalyticsContainer", "hasKnoxPermission error : " + e);
    }
    Log.d("IFKnoxAnalyticsContainer", "EnterpriseLicenseService is null");
    return false;
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public String getDeviceOwnerPackage() {
    IDevicePolicyManager asInterface =
        IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
    String str = null;
    if (asInterface != null) {
      try {
        ComponentName deviceOwnerComponent = asInterface.getDeviceOwnerComponent(false);
        if (deviceOwnerComponent != null) {
          str = deviceOwnerComponent.getPackageName();
        }
      } catch (Exception e) {
        Log.e("IFKnoxAnalyticsContainer", "getDeviceOwnerPackage exception -" + e);
      }
    }
    Log.d("IFKnoxAnalyticsContainer", "getDeviceOwnerPackage packageName -" + str);
    return str;
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public int isIMPackage(String str, int i) {
    List queryIntentServicesAsUser =
        this.mContext
            .getPackageManager()
            .queryIntentServicesAsUser(new Intent("android.view.InputMethod"), 8422016, i);
    for (int i2 = 0; i2 < queryIntentServicesAsUser.size(); i2++) {
      ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
      if ("android.permission.BIND_INPUT_METHOD".equals(serviceInfo.permission)
          && serviceInfo.packageName.equals(str)) {
        return 1;
      }
    }
    return 0;
  }

  @Override // com.android.server.knox.IKnoxAnalyticsContainer
  public Set getVisibleApps(int i) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.LAUNCHER");
    List queryIntentActivitiesAsUser =
        this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, 786432, i);
    ArraySet arraySet = new ArraySet();
    Iterator it = queryIntentActivitiesAsUser.iterator();
    while (it.hasNext()) {
      arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
    }
    return arraySet;
  }

  public final IPackageManager getIPackageManager() {
    return ActivityThread.getPackageManager();
  }
}
