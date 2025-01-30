package com.android.server.devicepolicy;

import android.R;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.pm.ApexManager;
import com.samsung.android.feature.SemCscFeature;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class OverlayPackagesProvider {
  public static final Map sActionToMetadataKeyMap = new HashMap();
  public static final Set sAllowedActions = new HashSet();
  public final Context mContext;
  public final Injector mInjector;
  public final PackageManager mPm;
  public String salesCode;

  interface Injector {
    String getActiveApexPackageNameContainingPackage(String str);

    String getDevicePolicyManagementRoleHolderPackageName(Context context);

    List getInputMethodListAsUser(int i);
  }

  public final int getCarrierDisallowedAppsListArrayforManagedUser() {
    return R.array.config_biometric_sensors;
  }

  public final int getCarrierRequiredAppsListArrayforManagedUser() {
    return R.array.config_defaultImperceptibleKillingExemptionProcStates;
  }

  public OverlayPackagesProvider(Context context) {
    this(context, new DefaultInjector());
  }

  public final class DefaultInjector implements Injector {
    public DefaultInjector() {}

    @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
    public List getInputMethodListAsUser(int i) {
      return InputMethodManagerInternal.get().getInputMethodListAsUser(i);
    }

    @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
    public String getActiveApexPackageNameContainingPackage(String str) {
      return ApexManager.getInstance().getActiveApexPackageNameContainingPackage(str);
    }

    @Override // com.android.server.devicepolicy.OverlayPackagesProvider.Injector
    public String getDevicePolicyManagementRoleHolderPackageName(final Context context) {
      return (String)
          Binder.withCleanCallingIdentity(
              new FunctionalUtils.ThrowingSupplier() { // from class:
                // com.android.server.devicepolicy.OverlayPackagesProvider$DefaultInjector$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                  String lambda$getDevicePolicyManagementRoleHolderPackageName$0;
                  lambda$getDevicePolicyManagementRoleHolderPackageName$0 =
                      OverlayPackagesProvider.DefaultInjector
                          .lambda$getDevicePolicyManagementRoleHolderPackageName$0(context);
                  return lambda$getDevicePolicyManagementRoleHolderPackageName$0;
                }
              });
    }

    public static /* synthetic */ String lambda$getDevicePolicyManagementRoleHolderPackageName$0(
        Context context) {
      List roleHolders =
          ((RoleManager) context.getSystemService(RoleManager.class))
              .getRoleHolders("android.app.role.DEVICE_POLICY_MANAGEMENT");
      if (roleHolders.isEmpty()) {
        return null;
      }
      return (String) roleHolders.get(0);
    }
  }

  public OverlayPackagesProvider(Context context, Injector injector) {
    Map map = sActionToMetadataKeyMap;
    map.put("android.app.action.PROVISION_MANAGED_USER", "android.app.REQUIRED_APP_MANAGED_USER");
    map.put(
        "android.app.action.PROVISION_MANAGED_PROFILE", "android.app.REQUIRED_APP_MANAGED_PROFILE");
    map.put(
        "android.app.action.PROVISION_MANAGED_DEVICE", "android.app.REQUIRED_APP_MANAGED_DEVICE");
    Set set = sAllowedActions;
    set.add("android.app.action.PROVISION_MANAGED_USER");
    set.add("android.app.action.PROVISION_MANAGED_PROFILE");
    set.add("android.app.action.PROVISION_MANAGED_DEVICE");
    this.mContext = context;
    this.mPm = (PackageManager) Preconditions.checkNotNull(context.getPackageManager());
    this.mInjector = (Injector) Preconditions.checkNotNull(injector);
    this.salesCode = SemCscFeature.getInstance().getString("SalesCode", "");
  }

  public Set getNonRequiredApps(ComponentName componentName, int i, String str) {
    Objects.requireNonNull(componentName);
    Preconditions.checkArgument(sAllowedActions.contains(str));
    Set launchableApps = getLaunchableApps(i);
    launchableApps.removeAll(getRequiredApps(str, componentName.getPackageName()));
    launchableApps.removeAll(getSystemInputMethods(i));
    launchableApps.removeAll(getSmsPackage(str, i));
    launchableApps.addAll(getDisallowedApps(str));
    launchableApps.removeAll(getRequiredAppsMainlineModules(launchableApps, str));
    launchableApps.removeAll(getDeviceManagerRoleHolders());
    return launchableApps;
  }

  public final Set getDeviceManagerRoleHolders() {
    HashSet hashSet = new HashSet();
    String devicePolicyManagementRoleHolderPackageName =
        this.mInjector.getDevicePolicyManagementRoleHolderPackageName(this.mContext);
    if (devicePolicyManagementRoleHolderPackageName != null) {
      hashSet.add(devicePolicyManagementRoleHolderPackageName);
    }
    return hashSet;
  }

  public final Set getRequiredAppsMainlineModules(Set set, String str) {
    HashSet hashSet = new HashSet();
    Iterator it = set.iterator();
    while (it.hasNext()) {
      String str2 = (String) it.next();
      if (isMainlineModule(str2) && isRequiredAppDeclaredInMetadata(str2, str)) {
        hashSet.add(str2);
      }
    }
    return hashSet;
  }

  public final boolean isRequiredAppDeclaredInMetadata(String str, String str2) {
    try {
      PackageInfo packageInfo = this.mPm.getPackageInfo(str, 128);
      return packageInfo.applicationInfo.metaData.getBoolean(
          (String) sActionToMetadataKeyMap.get(str2));
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }

  public final boolean isMainlineModule(String str) {
    return isRegularMainlineModule(str) || isApkInApexMainlineModule(str);
  }

  public final boolean isRegularMainlineModule(String str) {
    try {
      this.mPm.getModuleInfo(str, 0);
      return true;
    } catch (PackageManager.NameNotFoundException unused) {
      return false;
    }
  }

  public final boolean isApkInApexMainlineModule(String str) {
    return this.mInjector.getActiveApexPackageNameContainingPackage(str) != null;
  }

  public final Set getLaunchableApps(int i) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.LAUNCHER");
    List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, 795136, i);
    ArraySet arraySet = new ArraySet();
    Iterator it = queryIntentActivitiesAsUser.iterator();
    while (it.hasNext()) {
      arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
    }
    return arraySet;
  }

  public final Set getSystemInputMethods(int i) {
    List<InputMethodInfo> inputMethodListAsUser = this.mInjector.getInputMethodListAsUser(i);
    ArraySet arraySet = new ArraySet();
    for (InputMethodInfo inputMethodInfo : inputMethodListAsUser) {
      if (inputMethodInfo.getServiceInfo().applicationInfo.isSystemApp()) {
        arraySet.add(inputMethodInfo.getPackageName());
      }
    }
    return arraySet;
  }

  public final Set getSmsPackage(String str, int i) {
    ArraySet arraySet = new ArraySet();
    if (str.equals("android.app.action.PROVISION_MANAGED_DEVICE")
        && !isPackageInstalledAsUser(i, "com.samsung.android.messaging")) {
      arraySet.add("com.google.android.apps.messaging");
    }
    return arraySet;
  }

  public final boolean isPackageInstalledAsUser(int i, String str) {
    try {
      return this.mPm.getPackageInfoAsUser(str, 64, i) != null;
    } catch (Exception e) {
      Slog.e("OverlayPackagesProvider", "isPackageInstalledAsUser exception -" + e);
      return false;
    }
  }

  public final Set getRequiredApps(String str, String str2) {
    ArraySet arraySet = new ArraySet();
    arraySet.addAll(getRequiredAppsSet(str));
    arraySet.addAll(getVendorRequiredAppsSet(str));
    arraySet.addAll(getCarrierRequiredAppsSet(str));
    arraySet.add(str2);
    return arraySet;
  }

  public final Set getDisallowedApps(String str) {
    ArraySet arraySet = new ArraySet();
    arraySet.addAll(getDisallowedAppsSet(str));
    arraySet.addAll(getVendorDisallowedAppsSet(str));
    arraySet.addAll(getCarrierDisallowedAppsSet(str));
    return arraySet;
  }

  public final Set getRequiredAppsSet(String str) {
    int i;
    str.hashCode();
    switch (str) {
      case "android.app.action.PROVISION_MANAGED_DEVICE":
        i = 17236450;
        break;
      case "android.app.action.PROVISION_MANAGED_USER":
        i = 17236452;
        break;
      case "android.app.action.PROVISION_MANAGED_PROFILE":
        i = 17236451;
        break;
      default:
        throw new IllegalArgumentException("Provisioning type " + str + " not supported.");
    }
    return new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(i)));
  }

  public final Set getDisallowedAppsSet(String str) {
    int i;
    str.hashCode();
    switch (str) {
      case "android.app.action.PROVISION_MANAGED_DEVICE":
        i = 17236428;
        break;
      case "android.app.action.PROVISION_MANAGED_USER":
        i = 17236430;
        break;
      case "android.app.action.PROVISION_MANAGED_PROFILE":
        i = 17236429;
        break;
      default:
        throw new IllegalArgumentException("Provisioning type " + str + " not supported.");
    }
    return new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(i)));
  }

  public final Set getVendorRequiredAppsSet(String str) {
    int i;
    str.hashCode();
    switch (str) {
      case "android.app.action.PROVISION_MANAGED_DEVICE":
        i = 17236469;
        break;
      case "android.app.action.PROVISION_MANAGED_USER":
        i = 17236471;
        break;
      case "android.app.action.PROVISION_MANAGED_PROFILE":
        i = 17236470;
        break;
      default:
        throw new IllegalArgumentException("Provisioning type " + str + " not supported.");
    }
    return new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(i)));
  }

  public final Set getVendorDisallowedAppsSet(String str) {
    int i;
    str.hashCode();
    switch (str) {
      case "android.app.action.PROVISION_MANAGED_DEVICE":
        i = 17236465;
        break;
      case "android.app.action.PROVISION_MANAGED_USER":
        i = 17236467;
        break;
      case "android.app.action.PROVISION_MANAGED_PROFILE":
        i = 17236466;
        break;
      default:
        throw new IllegalArgumentException("Provisioning type " + str + " not supported.");
    }
    return new ArraySet(Arrays.asList(this.mContext.getResources().getStringArray(i)));
  }

  public final Set getCarrierRequiredAppsSet(String str) {
    int carrierRequiredAppsListArrayforManagedDevice;
    str.hashCode();
    switch (str) {
      case "android.app.action.PROVISION_MANAGED_DEVICE":
        carrierRequiredAppsListArrayforManagedDevice =
            getCarrierRequiredAppsListArrayforManagedDevice();
        break;
      case "android.app.action.PROVISION_MANAGED_USER":
        carrierRequiredAppsListArrayforManagedDevice =
            getCarrierRequiredAppsListArrayforManagedUser();
        break;
      case "android.app.action.PROVISION_MANAGED_PROFILE":
        carrierRequiredAppsListArrayforManagedDevice =
            getCarrierRequiredAppsListArrayforManagedProfile();
        break;
      default:
        throw new IllegalArgumentException("Provisioning type " + str + " not supported.");
    }
    return new ArraySet(
        Arrays.asList(
            this.mContext
                .getResources()
                .getStringArray(carrierRequiredAppsListArrayforManagedDevice)));
  }

  public final Set getCarrierDisallowedAppsSet(String str) {
    int carrierDisallowedAppsListArrayforManagedDevice;
    str.hashCode();
    switch (str) {
      case "android.app.action.PROVISION_MANAGED_DEVICE":
        carrierDisallowedAppsListArrayforManagedDevice =
            getCarrierDisallowedAppsListArrayforManagedDevice();
        break;
      case "android.app.action.PROVISION_MANAGED_USER":
        carrierDisallowedAppsListArrayforManagedDevice =
            getCarrierDisallowedAppsListArrayforManagedUser();
        break;
      case "android.app.action.PROVISION_MANAGED_PROFILE":
        carrierDisallowedAppsListArrayforManagedDevice =
            getCarrierDisallowedAppsListArrayforManagedProfile();
        break;
      default:
        throw new IllegalArgumentException("Provisioning type " + str + " not supported.");
    }
    return new ArraySet(
        Arrays.asList(
            this.mContext
                .getResources()
                .getStringArray(carrierDisallowedAppsListArrayforManagedDevice)));
  }

  public final int getCarrierRequiredAppsListArrayforManagedDevice() {
    return ("ATT".equals(this.salesCode) || "AIO".equals(this.salesCode))
        ? R.array.config_builtInDisplayIsRoundArray
        : ("SPR".equals(this.salesCode)
                || "XAS".equals(this.salesCode)
                || "BST".equals(this.salesCode)
                || "VMU".equals(this.salesCode))
            ? R.array.config_cdma_home_system
            : ("TMB".equals(this.salesCode) || "TMK".equals(this.salesCode))
                ? R.array.config_cdma_international_roaming_indicators
                : ("VZW".equals(this.salesCode) || "VPP".equals(this.salesCode))
                    ? R.array.config_clockTickVibePattern
                    : "USC".equals(this.salesCode)
                        ? R.array.config_cell_retries_per_error_code
                        : "DCM".equals(this.salesCode)
                            ? R.array.config_callBarringMMI_for_ims
                            : "SBM".equals(this.salesCode)
                                ? R.array.config_cdma_dun_supported_types
                                : "KDI".equals(this.salesCode)
                                    ? R.array.config_cameraPrivacyLightColors
                                    : "CCT".equals(this.salesCode)
                                        ? R.array.config_callBarringMMI
                                        : R.array.config_cameraPrivacyLightAlsLuxThresholds;
  }

  public final int getCarrierRequiredAppsListArrayforManagedProfile() {
    return ("ATT".equals(this.salesCode) || "AIO".equals(this.salesCode))
        ? R.array.config_companionDeviceCerts
        : ("SPR".equals(this.salesCode)
                || "XAS".equals(this.salesCode)
                || "BST".equals(this.salesCode)
                || "VMU".equals(this.salesCode))
            ? R.array.config_defaultAmbientContextServices
            : "TMB".equals(this.salesCode)
                ? R.array.config_defaultCloudSearchServices
                : ("VZW".equals(this.salesCode) || "VPP".equals(this.salesCode))
                    ? R.array.config_defaultImperceptibleKillingExemptionPkgs
                    : "USC".equals(this.salesCode)
                        ? R.array.config_defaultFirstUserRestrictions
                        : "DCM".equals(this.salesCode)
                            ? R.array.config_companionPermSyncEnabledCerts
                            : "SBM".equals(this.salesCode)
                                ? R.array.config_convert_to_emergency_number_map
                                : "KDI".equals(this.salesCode)
                                    ? R.array.config_concurrentDisplayDeviceStates
                                    : "CCT".equals(this.salesCode)
                                        ? R.array.config_companionDevicePackages
                                        : R.array.config_companionPermSyncEnabledPackages;
  }

  public final int getCarrierDisallowedAppsListArrayforManagedDevice() {
    return ("ATT".equals(this.salesCode) || "AIO".equals(this.salesCode))
        ? R.array.config_autoBrightnessButtonBacklightValues
        : ("SPR".equals(this.salesCode)
                || "XAS".equals(this.salesCode)
                || "BST".equals(this.salesCode)
                || "VMU".equals(this.salesCode))
            ? R.array.config_autoBrightnessLevelsIdle
            : "TMB".equals(this.salesCode)
                ? R.array.config_autoKeyboardBacklightBrightnessValues
                : ("VZW".equals(this.salesCode) || "VPP".equals(this.salesCode))
                    ? R.array.config_autoKeyboardBacklightIncreaseLuxThreshold
                    : "USC".equals(this.salesCode)
                        ? R.array.config_autoKeyboardBacklightDecreaseLuxThreshold
                        : "DCM".equals(this.salesCode)
                            ? R.array.config_autoBrightnessDisplayValuesNitsIdle
                            : "SBM".equals(this.salesCode)
                                ? R.array.config_autoBrightnessLevels
                                : "KDI".equals(this.salesCode)
                                    ? R.array.config_autoBrightnessLcdBacklightValues_doze
                                    : "CCT".equals(this.salesCode)
                                        ? R.array.config_autoBrightnessDisplayValuesNits
                                        : R.array.config_autoBrightnessLcdBacklightValues;
  }

  public final int getCarrierDisallowedAppsListArrayforManagedProfile() {
    return ("ATT".equals(this.salesCode) || "AIO".equals(this.salesCode))
        ? R.array.config_autoRotationTiltTolerance
        : ("SPR".equals(this.salesCode)
                || "XAS".equals(this.salesCode)
                || "BST".equals(this.salesCode)
                || "VMU".equals(this.salesCode))
            ? R.array.config_bg_current_drain_high_threshold_to_bg_restricted
            : "TMB".equals(this.salesCode)
                ? R.array.config_bg_current_drain_high_threshold_to_restricted_bucket
                : ("VZW".equals(this.salesCode) || "VPP".equals(this.salesCode))
                    ? R.array.config_bg_current_drain_threshold_to_restricted_bucket
                    : "USC".equals(this.salesCode)
                        ? R.array.config_bg_current_drain_threshold_to_bg_restricted
                        : "DCM".equals(this.salesCode)
                            ? R.array.config_availableColorModes
                            : "SBM".equals(this.salesCode)
                                ? R.array.config_batteryPackageTypeSystem
                                : "KDI".equals(this.salesCode)
                                    ? R.array.config_batteryPackageTypeService
                                    : "CCT".equals(this.salesCode)
                                        ? R.array.config_autoTimeSourcesPriority
                                        : R.array.config_backGestureInsetScales;
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("OverlayPackagesProvider");
    indentingPrintWriter.increaseIndent();
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "required_apps_managed_device", 17236450);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "required_apps_managed_user", 17236452);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "required_apps_managed_profile", 17236451);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "disallowed_apps_managed_device", 17236428);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "disallowed_apps_managed_user", 17236430);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "disallowed_apps_managed_device", 17236428);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "vendor_required_apps_managed_device", 17236469);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "vendor_required_apps_managed_user", 17236471);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "vendor_required_apps_managed_profile", 17236470);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "vendor_disallowed_apps_managed_user", 17236467);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "vendor_disallowed_apps_managed_device", 17236465);
    DevicePolicyManagerService.dumpResources(
        indentingPrintWriter, this.mContext, "vendor_disallowed_apps_managed_profile", 17236466);
    indentingPrintWriter.decreaseIndent();
  }
}
