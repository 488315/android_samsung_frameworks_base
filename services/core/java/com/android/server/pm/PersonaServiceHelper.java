package com.android.server.pm;

import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.telephony.SmsApplication;
import com.android.server.enterprise.dualdar.DualDARPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.container.ContainerConfigurationPolicy;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import com.samsung.android.server.p025pm.PmServerUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class PersonaServiceHelper {
    public static final List ALLOWED_BLUETOOTH_TARGET = new ArrayList(Arrays.asList("com.android.bluetooth/com.android.bluetooth.hfp.HeadsetService", "com.android.bluetooth/com.android.bluetooth.a2dp.A2dpService", "com.android.bluetooth/com.android.bluetooth.a2dp.A2dpSinkService", "com.android.bluetooth/com.android.bluetooth.hfpclient.HeadsetClientService"));
    public static IApplicationPolicy mApplicationPolicyService = null;
    public static PersonaManagerService mPersonaManager = null;
    public static IEnterpriseDeviceManager mEdmService = null;
    public static IDualDARPolicy mDualDARPolicyService = null;
    public static UserManagerService mUserManager = null;
    public static Map packagesForPOP = new HashMap() { // from class: com.android.server.pm.PersonaServiceHelper.1
        {
            Boolean bool = Boolean.TRUE;
            put("com.google.android.providers.media.module", bool);
            put("com.android.providers.media.module", bool);
            put(KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME, bool);
            put("com.samsung.android.secsoundpicker", bool);
            put("com.google.android.overlay.gmsconfig.geotz", bool);
            put("android.auto_generated_rro_product__", bool);
            put("com.google.android.overlay.modules.modulemetadata.forframework", bool);
            put("com.android.permissioncontroller", bool);
        }
    };
    public static Map packagesForSF = new HashMap() { // from class: com.android.server.pm.PersonaServiceHelper.2
        {
            Boolean bool = Boolean.TRUE;
            put("com.google.android.providers.media.module", bool);
            put("com.samsung.knox.securefolder", bool);
            Boolean bool2 = Boolean.FALSE;
            put("com.samsung.android.forest", bool2);
            put("com.samsung.android.secsoundpicker", bool);
            put("com.samsung.advp.imssettings", bool2);
            put("com.sec.epdgtestapp", bool2);
            put("com.android.permissioncontroller", bool);
        }
    };
    public static Map packagesForDual = new HashMap() { // from class: com.android.server.pm.PersonaServiceHelper.3
        {
            Boolean bool = Boolean.FALSE;
            put("com.android.providers.media.module", bool);
            put("com.google.android.providers.media.module", bool);
            Boolean bool2 = Boolean.TRUE;
            put("com.android.phone", bool2);
            put("com.android.permissioncontroller", bool2);
            put("com.samsung.android.secsoundpicker", bool);
            put("com.google.android.gms", bool2);
            put("com.android.nfc", bool2);
            put("com.google.android.gsf", bool2);
            put("com.google.android.gsf.login", bool2);
            put("com.samsung.android.voc", bool);
            put("com.samsung.android.providers.media", bool);
            put("com.google.android.overlay.gmsconfig.geotz", bool2);
            put("android.auto_generated_rro_product__", bool2);
            put("com.google.android.overlay.modules.modulemetadata.forframework", bool2);
            put("com.google.android.overlay.modules.permissioncontroller", bool2);
            put("com.google.android.overlay.modules.permissioncontroller.forframework", bool2);
            put("com.google.android.packageinstaller", bool2);
            put("com.samsung.android.packageinstaller", bool2);
            put("com.android.intentresolver", bool2);
            put("com.facebook.appmanager", bool2);
            put("com.google.android.apps.restore", bool2);
        }
    };
    public static ArrayList DTI_PACKAGES = new ArrayList(Arrays.asList("com.dti.samsung", "com.smg.rt", "com.aura.oobe.samsung.gl", "com.aura.oobe.samsung", "com.dti.telefonica", "com.dti.tracfone", "com.dti.att", "com.LogiaGroup.LogiaDeck", "com.dti.attmx", "com.dti.millicom", "com.dti.cricket", "com.dti.globe", "com.dti.bouygues", "com.dti.uscc", "com.dti.amx", "com.dti.wiko", "com.dti.sliide", "com.dti.karbonn", "com.dti.intex", "com.dti.itel", "com.dti.gionee", "com.at.tg.rt", "com.applovin.array.apphub.samsung", "com.dish.wireless.installer", "com.dti.aon", "com.tims.rt"));

    public static boolean isSpfKnoxSupported() {
        return true;
    }

    public static UserManagerService getUserManager() {
        if (mUserManager == null) {
            mUserManager = (UserManagerService) ServiceManager.getService("user");
        }
        return mUserManager;
    }

    public static boolean isForKnoxNFC() {
        return SemPersonaManager.isKnoxVersionSupported(SemPersonaManager.KnoxContainerVersion.KNOX_CONTAINER_VERSION_2_4_0) && Binder.getCallingUid() == 1027;
    }

    public static ResolveInfo getPreferredInfoForUser(Computer computer, Intent intent, String str, long j, int i, PreferredActivityHelper preferredActivityHelper) {
        List list;
        try {
            list = getIPackageManager().queryIntentActivities(intent, str, j, i).getList();
        } catch (RemoteException e) {
            e.printStackTrace();
            list = null;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return preferredActivityHelper.findPreferredActivityNotLocked(computer, intent, str, j, list, true, false, true, i, UserHandle.getAppId(Binder.getCallingUid()) >= 10000);
    }

    public static boolean setLastChosenActivityForKnox(Computer computer, Intent intent, String str, long j, WatchedIntentFilter watchedIntentFilter, int i, ComponentName componentName, List list, PackageManagerService packageManagerService, PreferredActivityHelper preferredActivityHelper) {
        if (list.size() == 1) {
            int intValue = ((Integer) list.get(0)).intValue();
            UserInfo userInfo = getUserManager().getUserInfo(intValue);
            if (userInfo != null) {
                int i2 = userInfo.profileGroupId;
                if (i2 == -10000) {
                    Log.d("PersonaServiceHelper", "profile group id is not set for user " + intValue);
                    return false;
                }
                Log.d("PersonaServiceHelper", "setLastChosenActivityForKnox parent id " + i2);
                for (UserInfo userInfo2 : getUserManager().getUsers(true)) {
                    if ((userInfo2.isManagedProfile() && userInfo2.profileGroupId == i2) || userInfo2.id == i2) {
                        Log.d("PersonaServiceHelper", "setLastChosenActivityForKnox remove pref for " + userInfo2.id);
                        try {
                            preferredActivityHelper.findPreferredActivityNotLocked(computer, intent, str, j, getIPackageManager().queryIntentActivities(intent, str, j, userInfo2.id).getList(), false, true, false, userInfo2.id, UserHandle.getAppId(Binder.getCallingUid()) >= 10000);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.d("PersonaServiceHelper", "setLastChosenActivityForKnox setting preferred activity for " + intValue);
                preferredActivityHelper.addPreferredActivity(computer, watchedIntentFilter, i, null, componentName, false, intValue, "Setting last chosen", false);
            } else {
                Log.d("PersonaServiceHelper", "setLastChosenActivityForKnox invalid user " + intValue);
            }
        } else {
            Log.e("PersonaServiceHelper", "setLastChosenActivityForKnox invalid size for user list: " + list.size());
        }
        return true;
    }

    public static boolean isLastChosenActivity(Computer computer, Intent intent, String str, int i, WatchedIntentFilter watchedIntentFilter, int i2, ComponentName componentName, PackageManagerService packageManagerService, PreferredActivityHelper preferredActivityHelper) {
        ArrayList<Integer> integerArrayListExtra;
        if (!isForKnoxNFC() || (integerArrayListExtra = intent.getIntegerArrayListExtra("com.samsung.sec.knox.EXTRA_KNOX_ARRAY")) == null) {
            return false;
        }
        Log.d("PersonaServiceHelper", "Set last chosen activity with user list.");
        if (!setLastChosenActivityForKnox(computer, intent, str, i, watchedIntentFilter, i2, componentName, integerArrayListExtra, packageManagerService, preferredActivityHelper)) {
            return false;
        }
        Log.d("PersonaServiceHelper", "set last chosen activity has been handled by Knox logic");
        return true;
    }

    public static Pair getLastChosenActivity(Computer computer, Intent intent, String str, long j, PackageManagerService packageManagerService, PreferredActivityHelper preferredActivityHelper) {
        Object obj;
        List list;
        if (isForKnoxNFC()) {
            Log.d("PersonaServiceHelper", " getLastChosenActivity NFC " + intent);
            ArrayList<Integer> integerArrayListExtra = intent.getIntegerArrayListExtra("com.samsung.sec.knox.EXTRA_KNOX_ARRAY");
            if (integerArrayListExtra != null) {
                for (Integer num : integerArrayListExtra) {
                    Log.d("PersonaServiceHelper", "getLastChosenActivity NFC for " + num);
                    try {
                        list = getIPackageManager().queryIntentActivities(intent, str, j, num.intValue()).getList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        list = null;
                    }
                    if (list != null) {
                        ResolveInfo findPreferredActivityNotLocked = preferredActivityHelper.findPreferredActivityNotLocked(computer, intent, str, j, list, false, false, false, num.intValue(), UserHandle.getAppId(Binder.getCallingUid()) >= 10000);
                        Log.d("PersonaServiceHelper", "pms.getLastChosenActivity " + findPreferredActivityNotLocked + " for " + num);
                        if (findPreferredActivityNotLocked != null) {
                            return new Pair(Boolean.TRUE, findPreferredActivityNotLocked);
                        }
                    }
                }
                Log.d("PersonaServiceHelper", "getLastChosenActivity NFC return null as no user has one");
                return new Pair(Boolean.TRUE, null);
            }
            obj = null;
            Log.w("PersonaServiceHelper", "getLastChosenActivity NFC null integer array");
        } else {
            obj = null;
        }
        return new Pair(Boolean.FALSE, obj);
    }

    public static void trimPersonaFromInstallation(PackageSetting packageSetting, PackageSetting packageSetting2, int i, UserHandle userHandle, ParsedPackage parsedPackage) {
        List<UserInfo> list;
        boolean z;
        boolean z2 = (i & 4) != 0;
        boolean z3 = (i & 16) != 0;
        if (packageSetting == null) {
            Log.e("PersonaServiceHelper", "package setting is null");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                list = UserManagerService.getInstance().getUsers(false);
            } catch (NullPointerException e) {
                Log.e("PersonaServiceHelper", "user manager exception ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                list = null;
            }
            if (list != null) {
                for (UserInfo userInfo : list) {
                    if (userInfo.isManagedProfile() || userInfo.isDualAppProfile() || SemPersonaManager.isSecureFolderId(userInfo.id)) {
                        if (containsPackage(packageSetting.getPackageName(), true, userInfo)) {
                            packageSetting.setInstalled(true, userInfo.id);
                            Log.d("PersonaServiceHelper", "" + packageSetting.getPackageName() + " is installed in " + userInfo.id);
                            z = true;
                        } else {
                            z = false;
                        }
                        if (containsPackage(packageSetting.getPackageName(), false, userInfo)) {
                            packageSetting.setInstalled(false, userInfo.id);
                            Log.d("PersonaServiceHelper", "" + packageSetting.getPackageName() + " is uninstalled in " + userInfo.id);
                            z = true;
                        }
                        if (parsedPackage != null && parsedPackage.getPackageName() != null && parsedPackage.isOverlayIsStatic() && "android".equals(parsedPackage.getOverlayTarget())) {
                            Log.d("PersonaServiceHelper", "" + packageSetting.getPackageName() + " is (system overlay package) skip trim in " + userInfo.id);
                            z = true;
                        }
                        if (!z) {
                            if (userHandle == null || !UserHandle.ALL.equals(userHandle)) {
                                if (z2 && userHandle == null) {
                                    Log.d("PersonaServiceHelper", "New installation & target user = null or all\n set false for persona " + userInfo.id);
                                    packageSetting.setInstalled(false, userInfo.id);
                                } else if (z3 && (userHandle == null || UserHandle.ALL.equals(userHandle) || userInfo.isDualAppProfile())) {
                                    if (packageSetting2 == null) {
                                        Log.d("PersonaServiceHelper", "If no info about old packages trim it\n set false for persona " + userInfo.id);
                                        packageSetting.setInstalled(false, userInfo.id);
                                    } else if (!packageSetting2.getInstalled(userInfo.id)) {
                                        Log.d("PersonaServiceHelper", "if already not installed, then trim it\n set false for persona " + userInfo.id);
                                        packageSetting.setInstalled(false, userInfo.id);
                                    }
                                }
                            } else if (packageSetting2 != null && packageSetting2.getInstalled(userInfo.id)) {
                                Log.d("PersonaServiceHelper", "if already installed, then don't trim it\n set true for persona " + userInfo.id);
                                packageSetting.setInstalled(true, userInfo.id);
                            } else {
                                Log.d("PersonaServiceHelper", "in trim for persona " + userInfo.id + " setting false " + packageSetting2 + " " + packageSetting.getPackageName());
                                packageSetting.setInstalled(false, userInfo.id);
                            }
                        }
                    }
                    if (!packageSetting.getInstalled(userInfo.id) && SemPersonaManager.isKnoxId(userInfo.id) && isRequiredAppForKnox(packageSetting, userInfo.id)) {
                        packageSetting.setInstalled(true, userInfo.id);
                    }
                    if (!packageSetting.getInstalled(userInfo.id) && packageSetting2 != null && SemPersonaManager.isKnoxId(userInfo.id) && isRequiredAppForKnox(packageSetting2, userInfo.id)) {
                        packageSetting.setInstalled(true, userInfo.id);
                    }
                    if (packageSetting2 != null && packageSetting2.getInstalled(userInfo.id) && (SemPersonaManager.isKnoxId(userInfo.id) || SemPersonaManager.isDoEnabled(userInfo.id))) {
                        if (isDisallowedAppForKnox(packageSetting, userInfo.id)) {
                            packageSetting.setInstalled(false, userInfo.id);
                        }
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean containsPackage(String str, boolean z, UserInfo userInfo) {
        HashMap hashMap = new HashMap();
        if (userInfo.isDualAppProfile()) {
            hashMap.putAll(packagesForDual);
        } else if (SemPersonaManager.isSecureFolderId(userInfo.id)) {
            hashMap.putAll(packagesForSF);
        } else if (userInfo.isManagedProfile()) {
            hashMap.putAll(packagesForPOP);
        }
        return hashMap.containsKey(str) && ((Boolean) hashMap.get(str)).booleanValue() == z;
    }

    public static boolean isRequiredAppForKnox(PackageSetting packageSetting, int i) {
        Bundle appMetaData = getAppMetaData(packageSetting);
        if (appMetaData == null) {
            return false;
        }
        return isRequiredAppForKnox(appMetaData, i, packageSetting.getPackageName());
    }

    public static boolean isRequiredAppForKnox(Bundle bundle, int i, String str) {
        if (bundle == null) {
            return false;
        }
        try {
            if (!SemPersonaManager.isKnoxId(i) || !PmServerUtils.isMetaDataInBundle(bundle, "com.samsung.android.multiuser.required_managed_profile")) {
                return false;
            }
            Log.d("PersonaServiceHelper", "isRequiredAppForKnox - METADATA_REQUIRED_MANAGED_PROFILE is defined. " + str + " / " + i);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isRequiredAppForKnox(String str, int i) {
        Bundle bundle;
        try {
            if (SemPersonaManager.isKnoxId(i)) {
                ApplicationInfo applicationInfo = getIPackageManager().getApplicationInfo(str, 786560L, 0);
                if (applicationInfo == null) {
                    return false;
                }
                bundle = applicationInfo.metaData;
            } else {
                bundle = null;
            }
            return isRequiredAppForKnox(bundle, i, str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isDisallowedAppForKnox(PackageSetting packageSetting, int i) {
        Bundle appMetaData = getAppMetaData(packageSetting);
        if (appMetaData == null) {
            return false;
        }
        return isDisallowedAppForKnox(appMetaData, i, packageSetting.getPackageName());
    }

    public static boolean isDisallowedAppForKnox(String str, int i) {
        Bundle bundle;
        try {
            if (SemPersonaManager.isKnoxId(i)) {
                ApplicationInfo applicationInfo = getIPackageManager().getApplicationInfo(str, 786560L, i);
                if (applicationInfo == null) {
                    return false;
                }
                bundle = applicationInfo.metaData;
            } else {
                bundle = null;
            }
            return isDisallowedAppForKnox(bundle, i, str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bundle getAppMetaData(PackageSetting packageSetting) {
        try {
            return PmServerUtils.getAppMetaData(packageSetting);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isDisallowedAppForKnox(Bundle bundle, int i, String str) {
        IPackageManager iPackageManager;
        String installerPackageName;
        if (bundle == null) {
            return false;
        }
        try {
            if (SemPersonaManager.isKnoxId(i) && PmServerUtils.isMetaDataInBundle(bundle, "com.samsung.android.multiuser.disallowed_managed_profile")) {
                Log.d("PersonaServiceHelper", "isDisallowedAppForKnox - METADATA_DISALLOWED_MANAGED_PROFILE is defined. " + str + " / " + i);
                return true;
            }
            if (SemPersonaManager.isDoEnabled(i)) {
                try {
                    if (!SystemProperties.getBoolean("persist.sys.knox.leave_all_system_apps_enabled", false) && (iPackageManager = getIPackageManager()) != null && (installerPackageName = iPackageManager.getInstallerPackageName(str)) != null && DTI_PACKAGES.contains(installerPackageName)) {
                        Log.d("PersonaServiceHelper", "isDisallowedAppForKnox - DTI package : " + str + " / installer : " + installerPackageName);
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void deletePackageAsUserAndPersona(final String str, final IPackageDeleteObserver iPackageDeleteObserver, final int i, final int i2, final Context context, final PackageManagerService packageManagerService, final Handler handler, final Settings settings, boolean z, boolean z2) {
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("START PACKAGE DELETE: observer{");
            sb.append(iPackageDeleteObserver != null ? Integer.valueOf(iPackageDeleteObserver.hashCode()) : "null");
            sb.append("}\npkg{");
            sb.append(str);
            sb.append("}\nuser{");
            sb.append(i);
            sb.append("}\n");
            Log.d("PersonaServiceHelper", sb.toString());
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("START PACKAGE DELETE: observer{");
            sb2.append(iPackageDeleteObserver != null ? Integer.valueOf(iPackageDeleteObserver.hashCode()) : "null");
            sb2.append("}\npkg{<packageName>}\nuser{");
            sb2.append(i);
            sb2.append("}\n");
            Log.d("PersonaServiceHelper", sb2.toString());
        }
        context.enforceCallingOrSelfPermission("android.permission.DELETE_PACKAGES", null);
        int callingUid = Binder.getCallingUid();
        Log.d("PersonaServiceHelper", "deletePackageAsUser : uid = " + callingUid + " userId = " + i);
        if (UserHandle.getUserId(callingUid) != i) {
            context.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "deletePackage for user " + i);
        }
        if (packageManagerService.isUserRestricted(i, "no_uninstall_apps")) {
            if (iPackageDeleteObserver != null) {
                try {
                    iPackageDeleteObserver.packageDeleted(str, -3);
                    return;
                } catch (RemoteException unused) {
                    return;
                }
            }
            return;
        }
        if (z2) {
            Log.d("PersonaServiceHelper", "deletePackageAsUser: pkg=" + str + " user=" + i);
        }
        handler.post(new Runnable() { // from class: com.android.server.pm.PersonaServiceHelper.4
            @Override // java.lang.Runnable
            public void run() {
                int i3;
                handler.removeCallbacks(this);
                try {
                    if (PersonaServiceHelper.getApplicationPolicyService() != null && !PersonaServiceHelper.getApplicationPolicyService().getApplicationUninstallationEnabled(new ContextInfo(Binder.getCallingUid()), str)) {
                        Log.w("PersonaServiceHelper", "This app uninstallation is not allowed");
                        i3 = -1;
                    } else {
                        i3 = packageManagerService.deletePackageXForKnox(str, i, i2);
                        try {
                            if (str.contains("jp.co.mmbi.app")) {
                                settings.addSharedUserLPw("android.uid.mmbi", 9106, 1, 0);
                            }
                        } catch (RemoteException unused2) {
                        }
                    }
                } catch (RemoteException unused3) {
                    i3 = 1;
                }
                PmHook.uninstallLog(str, i3 >= 0, i);
                if (iPackageDeleteObserver != null) {
                    try {
                        Log.d("PersonaServiceHelper", "return delete result to caller: " + iPackageDeleteObserver.hashCode());
                        Log.d("PersonaServiceHelper", "returnCode: " + i3);
                        iPackageDeleteObserver.packageDeleted(str, i3);
                    } catch (RemoteException unused4) {
                        Log.i("PersonaServiceHelper", "Observer no longer exists.");
                    }
                }
                SmsApplication.getDefaultSmsApplication(context, true);
            }
        });
    }

    public static boolean isUsbHostModeEnabledForContainer(Context context) {
        ContainerConfigurationPolicy containerConfigurationPolicy;
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                Slog.d("PersonaServiceHelper", "isUsbHostModeEnabledForContainer: userId-" + callingUserId);
                KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(context, new ContextInfo(new EdmStorageProvider(context).getMUMContainerOwnerUid(callingUserId), callingUserId));
                if (knoxContainerManager != null && (containerConfigurationPolicy = knoxContainerManager.getContainerConfigurationPolicy()) != null && !(z = containerConfigurationPolicy.isUsbAccessEnabled())) {
                    Slog.d("PersonaServiceHelper", "isUsbHostModeEnabledForContainer: Usb Host Mode sdcard is disabled for User-" + callingUserId + ". Ignoring USB device access request...");
                }
            } catch (Exception e) {
                Slog.d("PersonaServiceHelper", "Exception in isUsbHostModeEnabledForContainer:" + e);
                e.printStackTrace();
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean shouldBlockUsbHostMode(Context context) {
        return SemPersonaManager.isKnoxId(UserHandle.getCallingUserId()) && !isUsbHostModeEnabledForContainer(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean shouldBlockBluetooth(Context context, int i) {
        boolean z;
        KnoxContainerManager knoxContainerManager;
        if (i == 0 || !SemPersonaManager.isKnoxId(i)) {
            return false;
        }
        try {
            knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(context, i);
        } catch (SecurityException e) {
            Slog.w("PersonaServiceHelper", "isKnoxBluetoothEnabled(): SecurityException: " + e);
        }
        if (knoxContainerManager != null) {
            z = knoxContainerManager.getContainerConfigurationPolicy().isBluetoothEnabled();
            Slog.d("PersonaServiceHelper", "BMS::isKnoxBluetoothEnabled = " + z);
            return z;
        }
        z = true;
        Slog.d("PersonaServiceHelper", "BMS::isKnoxBluetoothEnabled = " + z);
        if (z) {
        }
    }

    public static boolean shouldBlockUserStart(Context context, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.d("PersonaServiceHelper", "shouldBlockUserStart() " + i);
            UserInfo userInfo = getUserManager().getUserInfo(i);
            if (userInfo == null || userInfo.id == 0 || !userInfo.isManagedProfile() || !userInfo.isDeviceCompromised()) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            Log.w("PersonaServiceHelper", "blocking when device compromised : " + i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static Optional getDualDARPolicyService() {
        if (mDualDARPolicyService == null) {
            mDualDARPolicyService = IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
        }
        return Optional.ofNullable(mDualDARPolicyService);
    }

    public static IApplicationPolicy getApplicationPolicyService() {
        if (mApplicationPolicyService == null) {
            mApplicationPolicyService = IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
        }
        return mApplicationPolicyService;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean shouldBlockBluetoothHeadset(Context context, String str, int i, int i2) {
        boolean z;
        KnoxContainerManager knoxContainerManager;
        if (ALLOWED_BLUETOOTH_TARGET.contains(str) && SemPersonaManager.isKnoxId(i) && i2 == 0) {
            try {
                knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(context, i);
            } catch (SecurityException e) {
                Slog.w("PersonaServiceHelper", "retrieveServiceLocked(): SecurityException: " + e);
            }
            if (knoxContainerManager != null) {
                z = knoxContainerManager.getContainerConfigurationPolicy().isBluetoothEnabled();
                return z;
            }
        }
        z = false;
        if (z) {
        }
    }

    public static boolean isCallerApprovedToInstall(Context context, int i, int i2, boolean z) {
        Log.i("PersonaServiceHelper", "isCallerApprovedToInstall(uid:" + i + " userHandle:" + i2 + ")");
        if (!z && Binder.getCallingUid() == Process.myUid() && Binder.getCallingPid() == Process.myPid()) {
            Log.i("PersonaServiceHelper", "caller is system_service process....");
            return true;
        }
        if (SemPersonaManager.isKnoxId(i2)) {
            if (SemPersonaManager.isSecureFolderId(i2)) {
                Log.i("PersonaServiceHelper", "Secure folder or BBD has no Knox app install enforcement");
                return true;
            }
        } else if (i2 == 0) {
            if (!SemPersonaManager.isDoEnabled(i2)) {
                Log.i("PersonaServiceHelper", "DO is not enabled. no Knox app install enforcement");
                return true;
            }
        } else {
            Log.i("PersonaServiceHelper", "Sub user. No Knox app install enforcement");
            return true;
        }
        if (getApplicationPolicyService() == null) {
            Log.e("PersonaServiceHelper", "failed to find application policy service");
            return false;
        }
        try {
            return getApplicationPolicyService().isFromApprovedInstaller(i, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean canAddMoreManagedProfiles(Context context, boolean z, int i, List list) {
        Iterator it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo.isManagedProfile() && !userInfo.isDualAppProfile()) {
                if (userInfo.isSecureFolder()) {
                    i3++;
                } else {
                    i2++;
                }
            }
        }
        if ((131072 & i) != 0) {
            Log.d("PersonaServiceHelper", "creating user owned container");
            if (i3 >= 1) {
                Log.e("PersonaServiceHelper", "creation failed when already exists user-owned container");
                return false;
            }
        } else {
            Log.d("PersonaServiceHelper", "creating enterprise owned container");
            if (i2 >= 1 && !z) {
                Log.d("PersonaServiceHelper", "creation failed when not allowed to remove PO");
                return false;
            }
        }
        return true;
    }

    public static boolean isDualDAREnabled() {
        boolean z = false;
        Iterator it = getUserManager().getUsers(false).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo != null && SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                z = true;
                break;
            }
        }
        Log.d("PersonaServiceHelper", "isDualDAREnabled result : " + z);
        return z;
    }

    public static int getDualDARType(int i) {
        if (!SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            return -1;
        }
        int i2 = SemPersonaManager.isDoEnabled(0) ? i == 0 ? 2 : 1 : 0;
        Log.d("PersonaServiceHelper", "DualDAR Container Type : " + i2);
        return i2;
    }

    public static int getDualDARUser() {
        for (UserInfo userInfo : getUserManager().getUsers(false)) {
            if (userInfo != null && SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                return userInfo.id;
            }
        }
        return -1;
    }

    public static boolean isDERestrictionEnforced(final int i) {
        return ((Boolean) getDualDARPolicyService().map(new Function() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$isDERestrictionEnforced$0;
                lambda$isDERestrictionEnforced$0 = PersonaServiceHelper.lambda$isDERestrictionEnforced$0(i, (IDualDARPolicy) obj);
                return lambda$isDERestrictionEnforced$0;
            }
        }).orElse(Boolean.FALSE)).booleanValue();
    }

    public static /* synthetic */ Boolean lambda$isDERestrictionEnforced$0(int i, IDualDARPolicy iDualDARPolicy) {
        Bundle bundle;
        try {
            bundle = iDualDARPolicy.getConfig(new ContextInfo(Binder.getCallingUid(), i));
        } catch (RemoteException e) {
            Log.d("PersonaServiceHelper", "isDERestrictionEnforced exception " + e.getMessage());
            bundle = null;
        }
        if (bundle != null) {
            return Boolean.valueOf(bundle.getBoolean("dualdar-config-de-restriction", true));
        }
        return Boolean.FALSE;
    }

    public static boolean isDataLockTimeoutEnabled(final int i) {
        Bundle bundle;
        return i == 0 && (bundle = (Bundle) getDualDARPolicyService().map(new Function() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Bundle lambda$isDataLockTimeoutEnabled$1;
                lambda$isDataLockTimeoutEnabled$1 = PersonaServiceHelper.lambda$isDataLockTimeoutEnabled$1(i, (IDualDARPolicy) obj);
                return lambda$isDataLockTimeoutEnabled$1;
            }
        }).orElseGet(new Supplier() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                Bundle lambda$isDataLockTimeoutEnabled$2;
                lambda$isDataLockTimeoutEnabled$2 = PersonaServiceHelper.lambda$isDataLockTimeoutEnabled$2(i);
                return lambda$isDataLockTimeoutEnabled$2;
            }
        })) != null && bundle.getLong("dualdar-config-datalock-timeout") >= DualDARPolicy.getMinDataLockTimeoutManagedDevice();
    }

    public static /* synthetic */ Bundle lambda$isDataLockTimeoutEnabled$1(int i, IDualDARPolicy iDualDARPolicy) {
        try {
            return iDualDARPolicy.getConfig(new ContextInfo(Binder.getCallingUid(), i));
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ Bundle lambda$isDataLockTimeoutEnabled$2(int i) {
        Log.e("PersonaServiceHelper", "checkPackageStartable failed to acquire dualDARPolicy for user: " + i);
        return null;
    }

    public static boolean isSystemApp(int i, String str) {
        try {
            ApplicationInfo applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
            if (applicationInfo == null) {
                return false;
            }
            return (applicationInfo.flags & 129) != 0;
        } catch (Exception e) {
            Log.e("PersonaServiceHelper", "Unable to check is System App for: " + str + " in user: " + i);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPackageAllowlistedForDualDAR(Context context, String str, final int i) {
        ApplicationInfo applicationInfo;
        if (isSystemApp(i, str)) {
            Log.d("PersonaServiceHelper", "system app allowed - " + str);
            return true;
        }
        Bundle bundle = (Bundle) getDualDARPolicyService().map(new Function() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Bundle lambda$isPackageAllowlistedForDualDAR$3;
                lambda$isPackageAllowlistedForDualDAR$3 = PersonaServiceHelper.lambda$isPackageAllowlistedForDualDAR$3(i, (IDualDARPolicy) obj);
                return lambda$isPackageAllowlistedForDualDAR$3;
            }
        }).orElseGet(new Supplier() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                Bundle lambda$isPackageAllowlistedForDualDAR$4;
                lambda$isPackageAllowlistedForDualDAR$4 = PersonaServiceHelper.lambda$isPackageAllowlistedForDualDAR$4(i);
                return lambda$isPackageAllowlistedForDualDAR$4;
            }
        });
        if (bundle != null) {
            ArrayList arrayList = new ArrayList();
            for (AppIdentity appIdentity : bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages")) {
                arrayList.add(appIdentity);
            }
            try {
                applicationInfo = AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
            } catch (RemoteException e) {
                e.printStackTrace();
                applicationInfo = null;
            }
            if (applicationInfo != null && ((applicationInfo.isDirectBootAware() || applicationInfo.isPartiallyDirectBootAware()) && verifyPackageForDualDAR(context, i, str, arrayList))) {
                Log.d("PersonaServiceHelper", "checkPackageStartable direct boot aware and whitelisted allowed: " + str);
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ Bundle lambda$isPackageAllowlistedForDualDAR$3(int i, IDualDARPolicy iDualDARPolicy) {
        try {
            return iDualDARPolicy.getConfig(new ContextInfo(Binder.getCallingUid(), i));
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ Bundle lambda$isPackageAllowlistedForDualDAR$4(int i) {
        Log.e("PersonaServiceHelper", "checkPackageStartable failed to acquire dualDARPolicy for user: " + i);
        return null;
    }

    public static boolean isPackageAllowlistedForDEAccessForDualDAR(Context context, String str, final int i) {
        if (isSystemApp(i, str)) {
            Log.d("PersonaServiceHelper", "system app allowed - " + str);
            return true;
        }
        Bundle bundle = (Bundle) getDualDARPolicyService().map(new Function() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Bundle lambda$isPackageAllowlistedForDEAccessForDualDAR$5;
                lambda$isPackageAllowlistedForDEAccessForDualDAR$5 = PersonaServiceHelper.lambda$isPackageAllowlistedForDEAccessForDualDAR$5(i, (IDualDARPolicy) obj);
                return lambda$isPackageAllowlistedForDEAccessForDualDAR$5;
            }
        }).orElseGet(new Supplier() { // from class: com.android.server.pm.PersonaServiceHelper$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                Bundle lambda$isPackageAllowlistedForDEAccessForDualDAR$6;
                lambda$isPackageAllowlistedForDEAccessForDualDAR$6 = PersonaServiceHelper.lambda$isPackageAllowlistedForDEAccessForDualDAR$6(i);
                return lambda$isPackageAllowlistedForDEAccessForDualDAR$6;
            }
        });
        if (bundle == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (AppIdentity appIdentity : bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages")) {
            arrayList.add(appIdentity);
        }
        return verifyPackageForDualDAR(context, i, str, arrayList);
    }

    public static /* synthetic */ Bundle lambda$isPackageAllowlistedForDEAccessForDualDAR$5(int i, IDualDARPolicy iDualDARPolicy) {
        try {
            return iDualDARPolicy.getConfig(new ContextInfo(Binder.getCallingUid(), i));
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ Bundle lambda$isPackageAllowlistedForDEAccessForDualDAR$6(int i) {
        Log.e("PersonaServiceHelper", "checkPackageStartable failed to acquire dualDARPolicy for user: " + i);
        return null;
    }

    public static boolean verifyPackageForDualDAR(Context context, int i, String str, List list) {
        AppIdentity appIdentity;
        Log.d("PersonaServiceHelper", "verifyPackageForDualDAR package: " + str);
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                appIdentity = null;
                break;
            }
            appIdentity = (AppIdentity) it.next();
            if (str.compareTo(appIdentity.getPackageName()) == 0) {
                break;
            }
        }
        if (appIdentity == null) {
            Log.e("PersonaServiceHelper", "Package not whitelisted: " + str);
            return false;
        }
        if (appIdentity.getSignature() == null || appIdentity.getSignature().length() <= 0) {
            return true;
        }
        return comparePackageSignature(context, appIdentity.getPackageName(), appIdentity.getSignature(), i);
    }

    public static byte[] computeSha256DigestBytes(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static String bytesToHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static boolean comparePackageSignature(Context context, String str, String str2, int i) {
        PackageInfo packageInfo;
        SigningInfo signingInfo;
        if (str2 == null || str == null || (i == 0 && !DualDarManager.isOnDeviceOwnerEnabled())) {
            Log.d("PersonaServiceHelper", "Invalid arguments");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                packageInfo = context.getPackageManager().getPackageInfoAsUser(str, 134217728, i);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                packageInfo = null;
            }
            if (packageInfo != null && (signingInfo = packageInfo.signingInfo) != null) {
                for (Signature signature : signingInfo.getApkContentsSigners()) {
                    if (signature != null && (str2.equals(bytesToHex(computeSha256DigestBytes(signature.toByteArray()))) || str2.equals(signature.toCharsString()))) {
                        return true;
                    }
                }
            }
            Log.e("PersonaServiceHelper", "FAIL to verify Pkg Signature");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static IPackageManager getIPackageManager() {
        return ActivityThread.getPackageManager();
    }
}
