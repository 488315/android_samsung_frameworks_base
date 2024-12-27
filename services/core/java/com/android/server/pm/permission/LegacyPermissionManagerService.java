package com.android.server.pm.permission;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionInfo;
import android.os.Binder;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.permission.ILegacyPermissionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy.DelayingPackageManagerCache;
import com.samsung.android.rune.PMRune;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LegacyPermissionManagerService extends ILegacyPermissionManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final DefaultPermissionGrantPolicy mDefaultPermissionGrantPolicy;
    public final Injector mInjector;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
        public final Context mContext;
        public final PackageManagerInternal mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Internal {
        public Internal() {
        }

        public final void grantDefaultPermissions(int i) {
            int i2;
            List list;
            Iterator it;
            List list2;
            int i3;
            int i4;
            String str;
            String[] strArr;
            String str2;
            int i5;
            int i6;
            String[] strArr2;
            String str3;
            int i7;
            String str4;
            String[] strArr3;
            String str5;
            int i8;
            int i9;
            List list3;
            String[] strArr4;
            String str6;
            String str7;
            String str8;
            String str9;
            int i10;
            int i11;
            String[] strArr5;
            String str10;
            int i12;
            int i13;
            String[] strArr6;
            String str11;
            String str12;
            Iterator it2;
            String str13;
            String str14;
            int i14;
            int i15;
            String str15;
            String[] strArr7;
            String str16;
            int i16 = i;
            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy = LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
            defaultPermissionGrantPolicy.getClass();
            DefaultPermissionGrantPolicy.DelayingPackageManagerCache delayingPackageManagerCache = defaultPermissionGrantPolicy.new DelayingPackageManagerCache();
            DirEncryptService$$ExternalSyntheticOutline0.m(i16, "Granting permissions to platform components for user ", "DefaultPermGrantPolicy");
            int i17 = 0;
            List<PackageInfo> installedPackagesAsUser = defaultPermissionGrantPolicy.mContext.getPackageManager().getInstalledPackagesAsUser(536916096, 0);
            Iterator it3 = installedPackagesAsUser.iterator();
            while (it3.hasNext()) {
                PackageInfo packageInfo = (PackageInfo) it3.next();
                if (packageInfo != null) {
                    delayingPackageManagerCache.mPackageInfos.put(packageInfo.packageName, packageInfo);
                    if (delayingPackageManagerCache.isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo) && DefaultPermissionGrantPolicy.doesPackageSupportRuntimePermissions(packageInfo) && !ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
                        String str17 = "android.permission.ACCESS_FINE_LOCATION";
                        String str18 = "android.permission.ACCESS_COARSE_LOCATION";
                        if ("com.samsung.android.spayfw".equals(packageInfo.packageName)) {
                            String[] strArr8 = packageInfo.requestedPermissions;
                            int length = strArr8.length;
                            int i18 = i17;
                            while (i18 < length) {
                                String str19 = strArr8[i18];
                                if (str18.equals(str19)) {
                                    str14 = str19;
                                    i14 = i18;
                                    i15 = length;
                                    strArr7 = strArr8;
                                    str16 = str18;
                                    str15 = str17;
                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str18), false, false, i);
                                } else {
                                    str14 = str19;
                                    i14 = i18;
                                    i15 = length;
                                    str15 = str17;
                                    strArr7 = strArr8;
                                    str16 = str18;
                                }
                                if (str15.equals(str14)) {
                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str15), false, false, i);
                                }
                                i18 = i14 + 1;
                                str17 = str15;
                                str18 = str16;
                                length = i15;
                                strArr8 = strArr7;
                            }
                            i3 = i16;
                            list2 = installedPackagesAsUser;
                            it = it3;
                        } else {
                            String str20 = "android.permission.ACCESS_BACKGROUND_LOCATION";
                            if ("com.samsung.android.ipsgeofence".equals(packageInfo.packageName)) {
                                String[] strArr9 = packageInfo.requestedPermissions;
                                int length2 = strArr9.length;
                                int i19 = 0;
                                while (i19 < length2) {
                                    String str21 = strArr9[i19];
                                    if ("android.permission.ACCESS_COARSE_LOCATION".equals(str21)) {
                                        i12 = i19;
                                        i13 = length2;
                                        strArr6 = strArr9;
                                        str11 = str20;
                                        defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_COARSE_LOCATION"), false, false, i);
                                        str12 = str21;
                                    } else {
                                        i12 = i19;
                                        i13 = length2;
                                        strArr6 = strArr9;
                                        str11 = str20;
                                        str12 = str21;
                                    }
                                    if ("android.permission.ACCESS_FINE_LOCATION".equals(str12)) {
                                        it2 = it3;
                                        str13 = str12;
                                        defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_FINE_LOCATION"), false, false, i);
                                    } else {
                                        it2 = it3;
                                        str13 = str12;
                                    }
                                    if (str11.equals(str13)) {
                                        defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str11), false, false, i);
                                    }
                                    i19 = i12 + 1;
                                    str20 = str11;
                                    strArr9 = strArr6;
                                    length2 = i13;
                                    it3 = it2;
                                }
                                it = it3;
                            } else {
                                it = it3;
                                if ("com.samsung.android.networkdiagnostic".equals(packageInfo.packageName)) {
                                    String[] strArr10 = packageInfo.requestedPermissions;
                                    int length3 = strArr10.length;
                                    int i20 = 0;
                                    while (i20 < length3) {
                                        String str22 = strArr10[i20];
                                        if ("android.permission.ACCESS_COARSE_LOCATION".equals(str22)) {
                                            strArr5 = strArr10;
                                            str10 = str22;
                                            i10 = i20;
                                            i11 = length3;
                                            defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_COARSE_LOCATION"), false, false, i);
                                        } else {
                                            i10 = i20;
                                            i11 = length3;
                                            strArr5 = strArr10;
                                            str10 = str22;
                                        }
                                        if ("android.permission.ACCESS_FINE_LOCATION".equals(str10)) {
                                            defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_FINE_LOCATION"), false, false, i);
                                        }
                                        if ("android.permission.ACCESS_BACKGROUND_LOCATION".equals(str10)) {
                                            defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_BACKGROUND_LOCATION"), false, false, i);
                                        }
                                        i20 = i10 + 1;
                                        length3 = i11;
                                        strArr10 = strArr5;
                                    }
                                } else {
                                    String str23 = "android.permission.WRITE_EXTERNAL_STORAGE";
                                    String str24 = "android.permission.READ_EXTERNAL_STORAGE";
                                    if ("com.samsung.cmh".equals(packageInfo.packageName)) {
                                        String[] strArr11 = packageInfo.requestedPermissions;
                                        int length4 = strArr11.length;
                                        int i21 = 0;
                                        while (i21 < length4) {
                                            String str25 = strArr11[i21];
                                            if ("android.permission.READ_CONTACTS".equals(str25)) {
                                                i8 = i21;
                                                i9 = length4;
                                                strArr4 = strArr11;
                                                str6 = str24;
                                                list3 = installedPackagesAsUser;
                                                str7 = str23;
                                                defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_CONTACTS"), false, false, i);
                                                str8 = str25;
                                            } else {
                                                i8 = i21;
                                                i9 = length4;
                                                list3 = installedPackagesAsUser;
                                                strArr4 = strArr11;
                                                str6 = str24;
                                                str7 = str23;
                                                str8 = str25;
                                            }
                                            if (str6.equals(str8)) {
                                                str9 = str8;
                                                defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str6), false, false, i);
                                            } else {
                                                str9 = str8;
                                            }
                                            if (str7.equals(str9)) {
                                                defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str7), false, false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_IMAGES".equals(str9)) {
                                                defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_VIDEO".equals(str9)) {
                                                defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, false, i);
                                            }
                                            if ("android.permission.ACCESS_MEDIA_LOCATION".equals(str9)) {
                                                defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_MEDIA_LOCATION"), false, false, i);
                                            }
                                            i21 = i8 + 1;
                                            str23 = str7;
                                            str24 = str6;
                                            length4 = i9;
                                            installedPackagesAsUser = list3;
                                            strArr11 = strArr4;
                                        }
                                        list2 = installedPackagesAsUser;
                                    } else {
                                        list2 = installedPackagesAsUser;
                                        if ("com.samsung.faceservice".equals(packageInfo.packageName)) {
                                            String[] strArr12 = packageInfo.requestedPermissions;
                                            int length5 = strArr12.length;
                                            int i22 = 0;
                                            while (i22 < length5) {
                                                String str26 = strArr12[i22];
                                                if ("android.permission.READ_EXTERNAL_STORAGE".equals(str26)) {
                                                    i7 = i22;
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_EXTERNAL_STORAGE"), false, false, i);
                                                    str4 = str26;
                                                } else {
                                                    i7 = i22;
                                                    str4 = str26;
                                                }
                                                if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str4)) {
                                                    strArr3 = strArr12;
                                                    str5 = str4;
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.WRITE_EXTERNAL_STORAGE"), false, false, i);
                                                } else {
                                                    strArr3 = strArr12;
                                                    str5 = str4;
                                                }
                                                if ("android.permission.READ_MEDIA_IMAGES".equals(str5)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, false, i);
                                                }
                                                if ("android.permission.READ_MEDIA_VIDEO".equals(str5)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, false, i);
                                                }
                                                i22 = i7 + 1;
                                                strArr12 = strArr3;
                                            }
                                        } else if ("com.samsung.storyservice".equals(packageInfo.packageName)) {
                                            String[] strArr13 = packageInfo.requestedPermissions;
                                            int length6 = strArr13.length;
                                            int i23 = 0;
                                            while (i23 < length6) {
                                                String str27 = strArr13[i23];
                                                if ("android.permission.READ_EXTERNAL_STORAGE".equals(str27)) {
                                                    strArr2 = strArr13;
                                                    str3 = str27;
                                                    i5 = i23;
                                                    i6 = length6;
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_EXTERNAL_STORAGE"), false, false, i);
                                                } else {
                                                    i5 = i23;
                                                    i6 = length6;
                                                    strArr2 = strArr13;
                                                    str3 = str27;
                                                }
                                                if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str3)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.WRITE_EXTERNAL_STORAGE"), false, false, i);
                                                }
                                                if ("android.permission.READ_MEDIA_IMAGES".equals(str3)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, false, i);
                                                }
                                                if ("android.permission.READ_MEDIA_VIDEO".equals(str3)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, false, i);
                                                }
                                                if ("android.permission.ACCESS_MEDIA_LOCATION".equals(str3)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_MEDIA_LOCATION"), false, false, i);
                                                }
                                                i23 = i5 + 1;
                                                length6 = i6;
                                                strArr13 = strArr2;
                                            }
                                        } else if ("com.samsung.ipservice".equals(packageInfo.packageName)) {
                                            String[] strArr14 = packageInfo.requestedPermissions;
                                            int length7 = strArr14.length;
                                            int i24 = 0;
                                            while (i24 < length7) {
                                                String str28 = strArr14[i24];
                                                if ("android.permission.READ_EXTERNAL_STORAGE".equals(str28)) {
                                                    i4 = i24;
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_EXTERNAL_STORAGE"), false, false, i);
                                                    str = str28;
                                                } else {
                                                    i4 = i24;
                                                    str = str28;
                                                }
                                                if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str)) {
                                                    strArr = strArr14;
                                                    str2 = str;
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.WRITE_EXTERNAL_STORAGE"), false, false, i);
                                                } else {
                                                    strArr = strArr14;
                                                    str2 = str;
                                                }
                                                if ("android.permission.READ_MEDIA_IMAGES".equals(str2)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, false, i);
                                                }
                                                if ("android.permission.READ_MEDIA_VIDEO".equals(str2)) {
                                                    defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, false, i);
                                                }
                                                i24 = i4 + 1;
                                                strArr14 = strArr;
                                            }
                                        } else if (!"com.samsung.android.scloud".equals(packageInfo.packageName)) {
                                            i3 = i;
                                            defaultPermissionGrantPolicy.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache, i3, packageInfo, null);
                                        } else if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 31) {
                                            i3 = i;
                                            defaultPermissionGrantPolicy.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache, i3, packageInfo, null);
                                            PackageInfo systemPackageInfo = delayingPackageManagerCache.getSystemPackageInfo("com.samsung.android.scloud");
                                            if (systemPackageInfo != null && DefaultPermissionGrantPolicy.doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                                                for (String str29 : systemPackageInfo.requestedPermissions) {
                                                    PermissionInfo permissionInfo = delayingPackageManagerCache.getPermissionInfo(str29);
                                                    if (permissionInfo != null && permissionInfo.isRuntime()) {
                                                        delayingPackageManagerCache.updatePermissionFlags(str29, systemPackageInfo, 16, 0, UserHandle.of(i));
                                                    }
                                                }
                                            }
                                        } else {
                                            i3 = i;
                                            defaultPermissionGrantPolicy.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache, i3, packageInfo, null);
                                        }
                                    }
                                    i3 = i;
                                }
                            }
                            i3 = i16;
                            list2 = installedPackagesAsUser;
                        }
                        i16 = i3;
                        installedPackagesAsUser = list2;
                        it3 = it;
                        i17 = 0;
                    }
                }
            }
            int i25 = i16;
            for (PackageInfo packageInfo2 : installedPackagesAsUser) {
                if (packageInfo2 != null && DefaultPermissionGrantPolicy.doesPackageSupportRuntimePermissions(packageInfo2) && !ArrayUtils.isEmpty(packageInfo2.requestedPermissions) && delayingPackageManagerCache.isGranted("android.permission.READ_PRIVILEGED_PHONE_STATE", packageInfo2, UserHandle.of(i)) && delayingPackageManagerCache.isGranted("android.permission.READ_PHONE_STATE", packageInfo2, UserHandle.of(i)) && !delayingPackageManagerCache.isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo2)) {
                    delayingPackageManagerCache.updatePermissionFlags("android.permission.READ_PHONE_STATE", packageInfo2, 16, 0, UserHandle.of(i));
                }
            }
            if (PMRune.PM_NAL_GET_APP_LIST) {
                DirEncryptService$$ExternalSyntheticOutline0.m(i25, "Granting GET_APP_LIST to system components for user ", "DefaultPermGrantPolicy");
                for (PackageInfo packageInfo3 : defaultPermissionGrantPolicy.mContext.getPackageManager().getInstalledPackagesAsUser(536916096, 0)) {
                    if (packageInfo3 != null && packageInfo3.applicationInfo.isSystemApp()) {
                        ArraySet arraySet = new ArraySet();
                        arraySet.add("com.samsung.android.permission.GET_APP_LIST");
                        defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo3, arraySet, true, false, i);
                    }
                }
            }
            defaultPermissionGrantPolicy.grantDefaultSystemHandlerPermissions(delayingPackageManagerCache, i25);
            Log.i("DefaultPermGrantPolicy", "Granting Notification permissions to platform signature apps for user " + i25);
            for (PackageInfo packageInfo4 : defaultPermissionGrantPolicy.mContext.getPackageManager().getInstalledPackagesAsUser(536916096, 0)) {
                if (packageInfo4 != null && packageInfo4.applicationInfo.isSystemApp() && packageInfo4.applicationInfo.isSignedWithPlatformKey()) {
                    defaultPermissionGrantPolicy.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache, i25, packageInfo4, DefaultPermissionGrantPolicy.NOTIFICATION_PERMISSIONS);
                }
            }
            defaultPermissionGrantPolicy.mHandler.removeMessages(1);
            synchronized (defaultPermissionGrantPolicy.mLock) {
                try {
                    if (defaultPermissionGrantPolicy.mGrantExceptions == null) {
                        defaultPermissionGrantPolicy.mGrantExceptions = defaultPermissionGrantPolicy.readDefaultPermissionExceptionsLocked(delayingPackageManagerCache);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            int size = defaultPermissionGrantPolicy.mGrantExceptions.size();
            ArraySet arraySet2 = null;
            int i26 = 0;
            while (i26 < size) {
                PackageInfo packageInfo5 = delayingPackageManagerCache.getPackageInfo((String) defaultPermissionGrantPolicy.mGrantExceptions.keyAt(i26));
                List list4 = (List) defaultPermissionGrantPolicy.mGrantExceptions.valueAt(i26);
                int size2 = list4.size();
                ArraySet arraySet3 = arraySet2;
                int i27 = 0;
                while (i27 < size2) {
                    DefaultPermissionGrantPolicy.DefaultPermissionGrant defaultPermissionGrant = (DefaultPermissionGrantPolicy.DefaultPermissionGrant) list4.get(i27);
                    PermissionInfo permissionInfo2 = delayingPackageManagerCache.getPermissionInfo(defaultPermissionGrant.name);
                    if (permissionInfo2 != null && permissionInfo2.getProtection() == 1) {
                        if (arraySet3 == null) {
                            arraySet3 = new ArraySet();
                        } else {
                            arraySet3.clear();
                        }
                        ArraySet arraySet4 = arraySet3;
                        arraySet4.add(defaultPermissionGrant.name);
                        i2 = size2;
                        list = list4;
                        defaultPermissionGrantPolicy.grantRuntimePermissions(delayingPackageManagerCache, packageInfo5, arraySet4, defaultPermissionGrant.fixed, defaultPermissionGrant.whitelisted, i);
                        arraySet3 = arraySet4;
                    } else {
                        i2 = size2;
                        list = list4;
                        Log.w("DefaultPermGrantPolicy", "Ignoring permission " + defaultPermissionGrant.name + " which isn't dangerous");
                    }
                    i27++;
                    list4 = list;
                    size2 = i2;
                }
                i26++;
                arraySet2 = arraySet3;
            }
            PackageManager.corkPackageInfoCache();
            for (int i28 = 0; i28 < delayingPackageManagerCache.mDelayedPermissionState.size(); i28++) {
                for (int i29 = 0; i29 < ((ArrayMap) delayingPackageManagerCache.mDelayedPermissionState.valueAt(i28)).size(); i29++) {
                    try {
                        ((DefaultPermissionGrantPolicy.DelayingPackageManagerCache.PermissionState) ((ArrayMap) delayingPackageManagerCache.mDelayedPermissionState.valueAt(i28)).valueAt(i29)).apply();
                    } catch (IllegalArgumentException e) {
                        Slog.w("DefaultPermGrantPolicy", "Cannot set permission " + ((String) ((ArrayMap) delayingPackageManagerCache.mDelayedPermissionState.valueAt(i28)).keyAt(i29)) + " of uid " + delayingPackageManagerCache.mDelayedPermissionState.keyAt(i28), e);
                    }
                }
            }
            PackageManager.uncorkPackageInfoCache();
        }
    }

    public LegacyPermissionManagerService(Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mDefaultPermissionGrantPolicy = new DefaultPermissionGrantPolicy(context);
    }

    public final int checkDeviceIdentifierAccess(String str, String str2, String str3, int i, int i2) {
        verifyCallerCanCheckAccess(i, i2, str, str2);
        int appId = UserHandle.getAppId(i2);
        if (appId == 1000 || appId == 0 || this.mInjector.mContext.checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", i, i2) == 0) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (((AppOpsManager) this.mInjector.mContext.getSystemService("appops")).noteOpNoThrow("android:read_device_identifiers", i2, str, str3, str2) == 0) {
                return 0;
            }
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mInjector.mContext.getSystemService("device_policy");
            return (devicePolicyManager == null || !devicePolicyManager.hasDeviceIdentifierAccess(str, i, i2)) ? -1 : 0;
        } finally {
            this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int checkPermissionAndAppop(String str, String str2, String str3, int i, String str4, int i2, String str5) {
        if (str2 == null || this.mInjector.mContext.checkPermission(str2, i, i2) == 0) {
            return ((AppOpsManager) this.mInjector.mContext.getSystemService("appops")).noteOpNoThrow(str3, i2, str, str4, str5) != 0 ? 1 : 0;
        }
        return -1;
    }

    public final int checkPhoneNumberAccess(String str, String str2, String str3, int i, int i2) {
        verifyCallerCanCheckAccess(i, i2, str, str2);
        if (this.mInjector.mContext.checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", i, i2) == 0) {
            return 0;
        }
        int i3 = -1;
        if (str == null) {
            return -1;
        }
        try {
            if (this.mInjector.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, UserHandle.getUserHandleForUid(i2)).targetSdkVersion <= 29 && (i3 = checkPermissionAndAppop(str, "android.permission.READ_PHONE_STATE", "android:read_phone_state", i, str3, i2, str2)) == 0) {
                return i3;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        int i4 = i3;
        if (checkPermissionAndAppop(str, null, "android:write_sms", i, str3, i2, str2) == 0 || checkPermissionAndAppop(str, "android.permission.READ_PHONE_NUMBERS", "android:read_phone_numbers", i, str3, i2, str2) == 0 || checkPermissionAndAppop(str, "android.permission.READ_SMS", "android:read_sms", i, str3, i2, str2) == 0) {
            return 0;
        }
        return i4;
    }

    public final void grantDefaultPermissionsToActiveLuiApp(String str, int i) {
        PackageManagerServiceUtils.enforceSystemOrPhoneCaller(Binder.getCallingUid(), "grantDefaultPermissionsToActiveLuiApp");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda4(this, str, i, 0));
    }

    public final void grantDefaultPermissionsToCarrierServiceApp(String str, int i) {
        PackageManagerServiceUtils.enforceSystemOrRoot("grantDefaultPermissionsForCarrierServiceApp");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda4(this, str, i, 1));
    }

    public final void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) {
        PackageManagerServiceUtils.enforceSystemOrPhoneCaller(Binder.getCallingUid(), "grantPermissionsToEnabledCarrierApps");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda0(this, strArr, i, 1));
    }

    public final void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) {
        PackageManagerServiceUtils.enforceSystemOrPhoneCaller(Binder.getCallingUid(), "grantDefaultPermissionsToEnabledImsServices");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda0(this, strArr, i, 0));
    }

    public final void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) {
        PackageManagerServiceUtils.enforceSystemOrPhoneCaller(Binder.getCallingUid(), "grantDefaultPermissionsToEnabledTelephonyDataServices");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda0(this, strArr, i, 3));
    }

    public final void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) {
        PackageManagerServiceUtils.enforceSystemOrPhoneCaller(Binder.getCallingUid(), "revokeDefaultPermissionsFromDisabledTelephonyDataServices");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda0(this, strArr, i, 4));
    }

    public final void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) {
        PackageManagerServiceUtils.enforceSystemOrPhoneCaller(Binder.getCallingUid(), "revokeDefaultPermissionsFromLuiApps");
        Binder.withCleanCallingIdentity(new LegacyPermissionManagerService$$ExternalSyntheticLambda0(this, strArr, i, 2));
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0059 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void verifyCallerCanCheckAccess(int r11, int r12, java.lang.String r13, java.lang.String r14) {
        /*
            r10 = this;
            com.android.server.pm.permission.LegacyPermissionManagerService$Injector r0 = r10.mInjector
            r0.getClass()
            int r0 = android.os.Binder.getCallingUid()
            com.android.server.pm.permission.LegacyPermissionManagerService$Injector r1 = r10.mInjector
            r1.getClass()
            int r1 = android.os.Binder.getCallingPid()
            int r2 = android.os.UserHandle.getAppId(r0)
            r3 = 1
            r4 = 10000(0x2710, float:1.4013E-41)
            if (r2 < r4) goto L21
            if (r0 != r12) goto L1f
            if (r1 == r11) goto L21
        L1f:
            r2 = r3
            goto L22
        L21:
            r2 = 0
        L22:
            if (r13 == 0) goto L56
            int r5 = android.os.UserHandle.getAppId(r12)
            if (r5 < r4) goto L56
            com.android.server.pm.permission.LegacyPermissionManagerService$Injector r10 = r10.mInjector
            int r5 = android.os.UserHandle.getUserId(r12)
            android.content.pm.PackageManagerInternal r10 = r10.mPackageManagerInternal
            r6 = 0
            int r10 = r10.getPackageUid(r13, r6, r5)
            if (r12 == r10) goto L56
            int r10 = android.os.UserHandle.getAppId(r0)
            if (r10 < r4) goto L42
            r10 = r0
            goto L43
        L42:
            r10 = r12
        L43:
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            java.lang.String r2 = "Package uid mismatch"
            java.lang.String r4 = "193441322"
            java.lang.Object[] r10 = new java.lang.Object[]{r4, r10, r2}
            r2 = 1397638484(0x534e4554, float:8.859264E11)
            android.util.EventLog.writeEvent(r2, r10)
            goto L57
        L56:
            r3 = r2
        L57:
            if (r3 != 0) goto L5a
            return
        L5a:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r12)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r11)
            r6 = r13
            r9 = r14
            java.lang.Object[] r10 = new java.lang.Object[]{r4, r5, r6, r7, r8, r9}
            java.lang.String r11 = "Calling uid %d, pid %d cannot access for package %s (uid=%d, pid=%d): %s"
            java.lang.String r10 = java.lang.String.format(r11, r10)
            java.lang.String r11 = "PermissionManager"
            android.util.Log.w(r11, r10)
            java.lang.SecurityException r11 = new java.lang.SecurityException
            r11.<init>(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.LegacyPermissionManagerService.verifyCallerCanCheckAccess(int, int, java.lang.String, java.lang.String):void");
    }
}
