package com.android.server.pm;

import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.parsing.PackageInfoUtils;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.ddar.IDualDARPolicy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class PersonaServiceHelper {
    public static final List ALLOWED_BLUETOOTH_TARGET =
            new ArrayList(
                    Arrays.asList(
                            "com.android.bluetooth/com.android.bluetooth.hfp.HeadsetService",
                            "com.android.bluetooth/com.android.bluetooth.a2dp.A2dpService",
                            "com.android.bluetooth/com.android.bluetooth.a2dp.A2dpSinkService",
                            "com.android.bluetooth/com.android.bluetooth.hfpclient.HeadsetClientService"));
    public static IApplicationPolicy mApplicationPolicyService = null;
    public static IDualDARPolicy mDualDARPolicyService = null;
    public static UserManagerService mUserManager = null;
    public static final Map packagesForPOP =
            new HashMap() { // from class: com.android.server.pm.PersonaServiceHelper.1
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
    public static final Map packagesForSF =
            new HashMap() { // from class: com.android.server.pm.PersonaServiceHelper.2
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
    public static final Map packagesForDual =
            new HashMap() { // from class: com.android.server.pm.PersonaServiceHelper.3
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
                    put(
                            "com.google.android.overlay.modules.permissioncontroller.forframework",
                            bool2);
                    put("com.google.android.packageinstaller", bool2);
                    put("com.samsung.android.packageinstaller", bool2);
                    put("com.android.intentresolver", bool2);
                    put("com.facebook.appmanager", bool2);
                    put("com.google.android.apps.restore", bool2);
                    put("com.android.credentialmanager", bool2);
                }
            };
    public static final ArrayList DTI_PACKAGES =
            new ArrayList(
                    Arrays.asList(
                            "com.dti.samsung",
                            "com.smg.rt",
                            "com.aura.oobe.samsung.gl",
                            "com.aura.oobe.samsung",
                            "com.dti.telefonica",
                            "com.dti.tracfone",
                            "com.dti.att",
                            "com.aura.oobe.att",
                            "com.aura.jet.att",
                            "com.LogiaGroup.LogiaDeck",
                            "com.dti.attmx",
                            "com.dti.millicom",
                            "com.dti.cricket",
                            "com.dti.globe",
                            "com.dti.bouygues",
                            "com.dti.uscc",
                            "com.dti.amx",
                            "com.dti.wiko",
                            "com.dti.sliide",
                            "com.dti.karbonn",
                            "com.dti.intex",
                            "com.dti.itel",
                            "com.dti.gionee",
                            "com.at.tg.rt",
                            "com.applovin.array.apphub.samsung",
                            "com.dish.wireless.installer",
                            "com.dti.aon",
                            "com.tims.rt"));

    public static boolean canAddMoreManagedProfiles(int i, boolean z, List list) {
        Iterator it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo.isManagedProfile()) {
                if (userInfo.isSecureFolder()) {
                    i3++;
                } else {
                    i2++;
                }
            }
        }
        if ((i & 131072) != 0) {
            Log.d("PersonaServiceHelper", "creating user owned container");
            if (i3 >= 1) {
                Log.e(
                        "PersonaServiceHelper",
                        "creation failed when already exists user-owned container");
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

    public static Bundle getAppMetaData(PackageSetting packageSetting) {
        ApplicationInfo generateApplicationInfo;
        if (packageSetting == null) {
            return null;
        }
        try {
            AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
            if (androidPackageInternal != null
                    && (generateApplicationInfo =
                                    PackageInfoUtils.generateApplicationInfo(
                                            androidPackageInternal,
                                            128L,
                                            packageSetting.readUserState(-1),
                                            -1,
                                            packageSetting))
                            != null) {
                return generateApplicationInfo.metaData;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static IApplicationPolicy getApplicationPolicyService() {
        if (mApplicationPolicyService == null) {
            mApplicationPolicyService =
                    IApplicationPolicy.Stub.asInterface(
                            ServiceManager.getService("application_policy"));
        }
        return mApplicationPolicyService;
    }

    public static Optional getDualDARPolicyService() {
        if (mDualDARPolicyService == null) {
            mDualDARPolicyService =
                    IDualDARPolicy.Stub.asInterface(ServiceManager.getService("DualDARPolicy"));
        }
        return Optional.ofNullable(mDualDARPolicyService);
    }

    public static int getDualDARType(int i) {
        if (!SemPersonaManager.isDarDualEncryptionEnabled(i)) {
            return -1;
        }
        int i2 = SemPersonaManager.isDoEnabled(0) ? i == 0 ? 2 : 1 : 0;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i2, "DualDAR Container Type : ", "PersonaServiceHelper");
        return i2;
    }

    public static int getDualDARUser() {
        Iterator it = ((ArrayList) getUserManager().getUsers(true, false, true)).iterator();
        while (it.hasNext()) {
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo != null && SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                return userInfo.id;
            }
        }
        return -1;
    }

    public static UserManagerService getUserManager() {
        if (mUserManager == null) {
            mUserManager = (UserManagerService) ServiceManager.getService("user");
        }
        return mUserManager;
    }

    public static boolean isCallerApprovedToInstall(int i, int i2) {
        Log.i(
                "PersonaServiceHelper",
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        i, i2, "isCallerApprovedToInstall(uid:", " userHandle:", ")"));
        if (Binder.getCallingUid() == Process.myUid()
                && Binder.getCallingPid() == Process.myPid()) {
            Log.i("PersonaServiceHelper", "caller is system_service process....");
            return true;
        }
        if (SemPersonaManager.isKnoxId(i2)) {
            if (SemPersonaManager.isSecureFolderId(i2)) {
                Log.i(
                        "PersonaServiceHelper",
                        "Secure folder or BBD has no Knox app install enforcement");
                return true;
            }
        } else {
            if (i2 != 0) {
                Log.i("PersonaServiceHelper", "Sub user. No Knox app install enforcement");
                return true;
            }
            if (!SemPersonaManager.isDoEnabled(i2)) {
                Log.i("PersonaServiceHelper", "DO is not enabled. no Knox app install enforcement");
                return true;
            }
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

    public static boolean isDisallowedAppForKnox(int i, String str, Bundle bundle) {
        IPackageManager packageManager;
        String installerPackageName;
        if (bundle == null) {
            return false;
        }
        try {
            if (SemPersonaManager.isKnoxId(i)
                    && bundle.getBoolean(
                            "com.samsung.android.multiuser.disallowed_managed_profile", false)) {
                Log.d(
                        "PersonaServiceHelper",
                        "isDisallowedAppForKnox - METADATA_DISALLOWED_MANAGED_PROFILE is defined. "
                                + str
                                + " / "
                                + i);
                return true;
            }
            if (SemPersonaManager.isDoEnabled(i)) {
                try {
                    if (!SystemProperties.getBoolean(
                                    "persist.sys.knox.leave_all_system_apps_enabled", false)
                            && (packageManager = ActivityThread.getPackageManager()) != null
                            && (installerPackageName = packageManager.getInstallerPackageName(str))
                                    != null
                            && DTI_PACKAGES.contains(installerPackageName)) {
                        Log.d(
                                "PersonaServiceHelper",
                                "isDisallowedAppForKnox - DTI package : "
                                        + str
                                        + " / installer : "
                                        + installerPackageName);
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

    public static boolean isDualDAREnabled() {
        boolean z = true;
        Iterator it = ((ArrayList) getUserManager().getUsers(true, false, true)).iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo != null && SemPersonaManager.isDarDualEncryptionEnabled(userInfo.id)) {
                break;
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isDualDAREnabled result : ", "PersonaServiceHelper", z);
        return z;
    }

    public static boolean isPackageAllowlistedForDEAccessForDualDAR(
            Context context, String str, int i) {
        if (isSystemApp(i, str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "system app allowed - ", str, "PersonaServiceHelper");
            return true;
        }
        Bundle bundle =
                (Bundle)
                        getDualDARPolicyService()
                                .map(new PersonaServiceHelper$$ExternalSyntheticLambda0(i, 1))
                                .orElseGet(
                                        new PersonaServiceHelper$$ExternalSyntheticLambda2(i, 0));
        if (bundle == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (AppIdentity appIdentity :
                bundle.getParcelableArray("dualdar-config-datalock-whitelistpackages")) {
            arrayList.add(appIdentity);
        }
        return verifyPackageForDualDAR(context, i, str, arrayList);
    }

    public static boolean isRequiredAppForKnox(int i, String str, Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        try {
            if (!SemPersonaManager.isKnoxId(i)
                    || !bundle.getBoolean(
                            "com.samsung.android.multiuser.required_managed_profile", false)) {
                return false;
            }
            Log.d(
                    "PersonaServiceHelper",
                    "isRequiredAppForKnox - METADATA_REQUIRED_MANAGED_PROFILE is defined. "
                            + str
                            + " / "
                            + i);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSystemApp(int i, String str) {
        try {
            ApplicationInfo applicationInfo =
                    AppGlobals.getPackageManager().getApplicationInfo(str, 128L, i);
            if (applicationInfo == null) {
                return false;
            }
            return (applicationInfo.flags & 129) != 0;
        } catch (Exception e) {
            Log.e(
                    "PersonaServiceHelper",
                    "Unable to check is System App for: " + str + " in user: " + i);
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldBlockBluetooth(android.content.Context r4, int r5) {
        /*
            java.lang.String r0 = "PersonaServiceHelper"
            r1 = 0
            if (r5 == 0) goto L3c
            boolean r2 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r5)
            if (r2 != 0) goto Lc
            goto L3c
        Lc:
            com.samsung.android.knox.EnterpriseKnoxManager r2 = com.samsung.android.knox.EnterpriseKnoxManager.getInstance()
            r3 = 1
            com.samsung.android.knox.container.KnoxContainerManager r4 = r2.getKnoxContainerManager(r4, r5)     // Catch: java.lang.SecurityException -> L20
            if (r4 == 0) goto L32
            com.samsung.android.knox.container.ContainerConfigurationPolicy r4 = r4.getContainerConfigurationPolicy()     // Catch: java.lang.SecurityException -> L20
            boolean r4 = r4.isBluetoothEnabled()     // Catch: java.lang.SecurityException -> L20
            goto L33
        L20:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r2 = "isKnoxBluetoothEnabled(): SecurityException: "
            r5.<init>(r2)
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Slog.w(r0, r4)
        L32:
            r4 = r3
        L33:
            java.lang.String r5 = "BMS::isKnoxBluetoothEnabled = "
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r5, r0, r4)
            if (r4 == 0) goto L3b
            return r1
        L3b:
            return r3
        L3c:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaServiceHelper.shouldBlockBluetooth(android.content.Context,"
                    + " int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003f A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldBlockBluetoothHeadset(
            android.content.Context r1, java.lang.String r2, int r3, int r4) {
        /*
            java.util.List r0 = com.android.server.pm.PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            boolean r2 = r0.contains(r2)
            r0 = 0
            if (r2 == 0) goto L3b
            boolean r2 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r3)
            if (r2 == 0) goto L3b
            if (r4 != 0) goto L3b
            com.samsung.android.knox.EnterpriseKnoxManager r2 = com.samsung.android.knox.EnterpriseKnoxManager.getInstance()
            com.samsung.android.knox.container.KnoxContainerManager r1 = r2.getKnoxContainerManager(r1, r3)     // Catch: java.lang.SecurityException -> L26
            if (r1 == 0) goto L3b
            com.samsung.android.knox.container.ContainerConfigurationPolicy r1 = r1.getContainerConfigurationPolicy()     // Catch: java.lang.SecurityException -> L26
            boolean r1 = r1.isBluetoothEnabled()     // Catch: java.lang.SecurityException -> L26
            goto L3c
        L26:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "retrieveServiceLocked(): SecurityException: "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            java.lang.String r2 = "PersonaServiceHelper"
            android.util.Slog.w(r2, r1)
        L3b:
            r1 = r0
        L3c:
            if (r1 == 0) goto L3f
            return r0
        L3f:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaServiceHelper.shouldBlockBluetoothHeadset(android.content.Context,"
                    + " java.lang.String, int, int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0084 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldBlockUsbHostMode(android.content.Context r10) {
        /*
            int r0 = android.os.UserHandle.getCallingUserId()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r0)
            r1 = 0
            if (r0 == 0) goto L8a
            java.lang.String r0 = "PersonaServiceHelper"
            java.lang.String r2 = "Exception in isUsbHostModeEnabledForContainer:"
            java.lang.String r3 = "isUsbHostModeEnabledForContainer: Usb Host Mode sdcard is disabled for User-"
            java.lang.String r4 = "isUsbHostModeEnabledForContainer: userId-"
            int r5 = android.os.UserHandle.getCallingUserId()
            long r6 = android.os.Binder.clearCallingIdentity()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r8.<init>(r4)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r8.append(r5)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            java.lang.String r4 = r8.toString()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            android.util.Slog.d(r0, r4)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            com.android.server.enterprise.storage.EdmStorageProvider r4 = new com.android.server.enterprise.storage.EdmStorageProvider     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r4.<init>(r10)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            int r4 = r4.getMUMContainerOwnerUid(r5)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            com.samsung.android.knox.EnterpriseKnoxManager r8 = com.samsung.android.knox.EnterpriseKnoxManager.getInstance()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            com.samsung.android.knox.ContextInfo r9 = new com.samsung.android.knox.ContextInfo     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r9.<init>(r4, r5)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            com.samsung.android.knox.container.KnoxContainerManager r10 = r8.getKnoxContainerManager(r10, r9)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            if (r10 == 0) goto L6a
            com.samsung.android.knox.container.ContainerConfigurationPolicy r10 = r10.getContainerConfigurationPolicy()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            if (r10 == 0) goto L6a
            boolean r10 = r10.isUsbAccessEnabled()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            if (r10 != 0) goto L6b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            r4.append(r5)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            java.lang.String r3 = ". Ignoring USB device access request..."
            r4.append(r3)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            android.util.Slog.d(r0, r3)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L65
            goto L6b
        L63:
            r10 = move-exception
            goto L86
        L65:
            r3 = move-exception
            goto L6f
        L67:
            r3 = move-exception
            r10 = r1
            goto L6f
        L6a:
            r10 = r1
        L6b:
            android.os.Binder.restoreCallingIdentity(r6)
            goto L82
        L6f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L63
            r4.append(r3)     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L63
            android.util.Slog.d(r0, r2)     // Catch: java.lang.Throwable -> L63
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L63
            goto L6b
        L82:
            if (r10 != 0) goto L8a
            r10 = 1
            return r10
        L86:
            android.os.Binder.restoreCallingIdentity(r6)
            throw r10
        L8a:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PersonaServiceHelper.shouldBlockUsbHostMode(android.content.Context):boolean");
    }

    public static boolean verifyPackageForDualDAR(Context context, int i, String str, List list) {
        AppIdentity appIdentity;
        PackageInfo packageInfo;
        SigningInfo signingInfo;
        byte[] bArr;
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "verifyPackageForDualDAR package: ", str, "PersonaServiceHelper");
        Iterator it = ((ArrayList) list).iterator();
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
            StorageManagerService$$ExternalSyntheticOutline0.m(
                    "Package not whitelisted: ", str, "PersonaServiceHelper");
            return false;
        }
        if (appIdentity.getSignature() == null || appIdentity.getSignature().length() <= 0) {
            return true;
        }
        String packageName = appIdentity.getPackageName();
        String signature = appIdentity.getSignature();
        if (signature == null
                || packageName == null
                || (i == 0 && !DualDarManager.isOnDeviceOwnerEnabled())) {
            Log.d("PersonaServiceHelper", "Invalid arguments");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                packageInfo =
                        context.getPackageManager().getPackageInfoAsUser(packageName, 134217728, i);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                packageInfo = null;
            }
            if (packageInfo != null && (signingInfo = packageInfo.signingInfo) != null) {
                for (Signature signature2 : signingInfo.getApkContentsSigners()) {
                    if (signature2 != null) {
                        byte[] byteArray = signature2.toByteArray();
                        try {
                            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
                            messageDigest.update(byteArray);
                            bArr = messageDigest.digest();
                        } catch (NoSuchAlgorithmException unused) {
                            bArr = null;
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        for (byte b : bArr) {
                            stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
                        }
                        if (signature.equals(stringBuffer.toString())
                                || signature.equals(signature2.toCharsString())) {
                            return true;
                        }
                    }
                }
            }
            Log.e("PersonaServiceHelper", "FAIL to verify Pkg Signature");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
