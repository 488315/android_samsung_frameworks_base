package com.android.server.pm.permission;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.content.ContentService$$ExternalSyntheticLambda0;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.AndroidPackage;

import libcore.util.HexEncoding;

import com.samsung.android.knox.analytics.activation.ActivationMonitor;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DefaultPermissionGrantPolicy {
    public static final Set ACTIVITY_RECOGNITION_PERMISSIONS;
    public static final Set ALWAYS_LOCATION_PERMISSIONS;
    public static final Set CALENDAR_PERMISSIONS;
    public static final Set CALL_LOG_PERMISSIONS;
    public static final Set CAMERA_PERMISSIONS;
    public static final Set COARSE_BACKGROUND_LOCATION_PERMISSIONS;
    public static final Set CONTACTS_PERMISSIONS;
    public static final Set FINE_LOCATION_PERMISSIONS;
    public static final Set FOREGROUND_LOCATION_PERMISSIONS;
    public static final Set MICROPHONE_PERMISSIONS;
    public static final Set NEARBY_DEVICES_PERMISSIONS;
    public static final Set NOTIFICATION_PERMISSIONS;
    public static final Set PHONE_PERMISSIONS;
    public static final Set SENSORS_PERMISSIONS;
    public static final Set SMS_PERMISSIONS;
    public static final Set STORAGE_PERMISSIONS;
    public final Context mContext;
    public LegacyPermissionManagerInternal$PackagesProvider mDialerAppPackagesProvider;
    public ArrayMap mGrantExceptions;
    public LegacyPermissionManagerInternal$PackagesProvider mLocationExtraPackagesProvider;
    public LegacyPermissionManagerInternal$PackagesProvider mLocationPackagesProvider;
    public LegacyPermissionManagerInternal$PackagesProvider mSimCallManagerPackagesProvider;
    public LegacyPermissionManagerInternal$PackagesProvider mSmsAppPackagesProvider;
    public ContentService$$ExternalSyntheticLambda0 mSyncAdapterPackagesProvider;
    public LegacyPermissionManagerInternal$PackagesProvider mUseOpenWifiAppPackagesProvider;
    public LegacyPermissionManagerInternal$PackagesProvider mVoiceInteractionPackagesProvider;
    public final Map mNotiBlockableDataByUid = new HashMap();
    public final Object mLock = new Object();
    public final AnonymousClass1 NO_PM_CACHE = new AnonymousClass1();
    public final AnonymousClass2 mHandler =
            new Handler(
                    Watchdog$$ExternalSyntheticOutline0.m(10, "DefaultPermGrantPolicy", true)
                            .getLooper()) { // from class:
                                            // com.android.server.pm.permission.DefaultPermissionGrantPolicy.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what == 1) {
                        synchronized (DefaultPermissionGrantPolicy.this.mLock) {
                            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                                    DefaultPermissionGrantPolicy.this;
                            if (defaultPermissionGrantPolicy.mGrantExceptions == null) {
                                defaultPermissionGrantPolicy.mGrantExceptions =
                                        defaultPermissionGrantPolicy
                                                .readDefaultPermissionExceptionsLocked(
                                                        defaultPermissionGrantPolicy.NO_PM_CACHE);
                            }
                        }
                    }
                }
            };
    public final PackageManagerInternal mServiceInternal =
            (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);

    /* renamed from: com.android.server.pm.permission.DefaultPermissionGrantPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends PackageManagerWrapper {
        public AnonymousClass1() {
            super();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final PackageInfo getPackageInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return DefaultPermissionGrantPolicy.this
                        .mContext
                        .getPackageManager()
                        .getPackageInfo(str, 536916096);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("DefaultPermGrantPolicy", "Package not found: ".concat(str));
                return null;
            }
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final int getPermissionFlags(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            return DefaultPermissionGrantPolicy.this
                    .mContext
                    .getPackageManager()
                    .getPermissionFlags(str, packageInfo.packageName, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final PermissionInfo getPermissionInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return DefaultPermissionGrantPolicy.this
                        .mContext
                        .getPackageManager()
                        .getPermissionInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w("DefaultPermGrantPolicy", "Permission not found: ".concat(str));
                return null;
            }
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final void grantPermission(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            DefaultPermissionGrantPolicy.this
                    .mContext
                    .getPackageManager()
                    .grantRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final boolean isGranted(String str, PackageInfo packageInfo, UserHandle userHandle) {
            return DefaultPermissionGrantPolicy.this
                            .mContext
                            .createContextAsUser(userHandle, 0)
                            .getPackageManager()
                            .checkPermission(str, packageInfo.packageName)
                    == 0;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final void revokePermission(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            DefaultPermissionGrantPolicy.this
                    .mContext
                    .getPackageManager()
                    .revokeRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final void updatePermissionFlags(
                String str, PackageInfo packageInfo, int i, int i2, UserHandle userHandle) {
            DefaultPermissionGrantPolicy.this
                    .mContext
                    .getPackageManager()
                    .updatePermissionFlags(str, packageInfo.packageName, i, i2, userHandle);
        }
    }

    public final class DefaultPermissionGrant {
        public final boolean fixed;
        public final String name;
        public final boolean whitelisted;

        public DefaultPermissionGrant(String str, boolean z, boolean z2) {
            this.name = str;
            this.fixed = z;
            this.whitelisted = z2;
        }
    }

    public final class DelayingPackageManagerCache extends PackageManagerWrapper {
        public final SparseArray mDelayedPermissionState;
        public final ArrayMap mPackageInfos;
        public final ArrayMap mPermissionInfos;
        public final SparseArray mUserContexts;

        public final class PermissionState {
            public Integer mOriginalFlags;
            public Boolean mOriginalGranted;
            public final String mPermission;
            public PackageInfo mPkgRequestingPerm;
            public final UserHandle mUser;
            public Integer newFlags;
            public Boolean newGranted;

            public PermissionState(String str, PackageInfo packageInfo, UserHandle userHandle) {
                this.mPermission = str;
                this.mPkgRequestingPerm = packageInfo;
                this.mUser = userHandle;
            }

            public final void apply() {
                int i;
                int i2;
                Integer num = this.newFlags;
                if (num != null) {
                    i = num.intValue() & (~this.mOriginalFlags.intValue());
                    i2 = this.mOriginalFlags.intValue() & (~this.newFlags.intValue());
                } else {
                    i = 0;
                    i2 = 0;
                }
                DelayingPackageManagerCache delayingPackageManagerCache =
                        DelayingPackageManagerCache.this;
                if (i2 != 0) {
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(
                            this.mPermission, this.mPkgRequestingPerm, i2, 0, this.mUser);
                }
                int i3 = i & 14336;
                if (i3 != 0) {
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(
                            this.mPermission, this.mPkgRequestingPerm, i3, -1, this.mUser);
                }
                Boolean bool = this.newGranted;
                if (bool != null && !bool.equals(this.mOriginalGranted)) {
                    boolean booleanValue = this.newGranted.booleanValue();
                    String str = this.mPermission;
                    if (booleanValue) {
                        DefaultPermissionGrantPolicy.this.NO_PM_CACHE.grantPermission(
                                str, this.mPkgRequestingPerm, this.mUser);
                    } else {
                        DefaultPermissionGrantPolicy.this.NO_PM_CACHE.revokePermission(
                                str, this.mPkgRequestingPerm, this.mUser);
                    }
                }
                int i4 = i & (-14337);
                if (i4 != 0) {
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(
                            this.mPermission, this.mPkgRequestingPerm, i4, -1, this.mUser);
                }
            }

            public final void initFlags() {
                if (this.newFlags == null) {
                    Integer valueOf =
                            Integer.valueOf(
                                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE
                                            .getPermissionFlags(
                                                    this.mPermission,
                                                    this.mPkgRequestingPerm,
                                                    this.mUser));
                    this.mOriginalFlags = valueOf;
                    this.newFlags = valueOf;
                }
            }

            public final void initGranted() {
                Context context;
                if (this.newGranted == null) {
                    UserHandle userHandle = this.mUser;
                    DelayingPackageManagerCache delayingPackageManagerCache =
                            DelayingPackageManagerCache.this;
                    int indexOfKey =
                            delayingPackageManagerCache.mUserContexts.indexOfKey(
                                    userHandle.getIdentifier());
                    if (indexOfKey >= 0) {
                        context =
                                (Context)
                                        delayingPackageManagerCache.mUserContexts.valueAt(
                                                indexOfKey);
                    } else {
                        Context createContextAsUser =
                                DefaultPermissionGrantPolicy.this.mContext.createContextAsUser(
                                        userHandle, 0);
                        delayingPackageManagerCache.mUserContexts.put(
                                userHandle.getIdentifier(), createContextAsUser);
                        context = createContextAsUser;
                    }
                    Boolean valueOf =
                            Boolean.valueOf(
                                    context.getPackageManager()
                                                    .checkPermission(
                                                            this.mPermission,
                                                            this.mPkgRequestingPerm.packageName)
                                            == 0);
                    this.mOriginalGranted = valueOf;
                    this.newGranted = valueOf;
                }
            }
        }

        public DelayingPackageManagerCache() {
            super();
            this.mDelayedPermissionState = new SparseArray();
            this.mUserContexts = new SparseArray();
            this.mPermissionInfos = new ArrayMap();
            this.mPackageInfos = new ArrayMap();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final PackageInfo getPackageInfo(String str) {
            int indexOfKey = this.mPackageInfos.indexOfKey(str);
            if (indexOfKey >= 0) {
                return (PackageInfo) this.mPackageInfos.valueAt(indexOfKey);
            }
            PackageInfo packageInfo =
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPackageInfo(str);
            this.mPackageInfos.put(str, packageInfo);
            return packageInfo;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final int getPermissionFlags(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initFlags();
            return permissionState.newFlags.intValue();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final PermissionInfo getPermissionInfo(String str) {
            int indexOfKey = this.mPermissionInfos.indexOfKey(str);
            if (indexOfKey >= 0) {
                return (PermissionInfo) this.mPermissionInfos.valueAt(indexOfKey);
            }
            PermissionInfo permissionInfo =
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPermissionInfo(str);
            this.mPermissionInfos.put(str, permissionInfo);
            return permissionInfo;
        }

        public final PermissionState getPermissionState(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            ArrayMap arrayMap;
            int uid =
                    UserHandle.getUid(
                            userHandle.getIdentifier(),
                            UserHandle.getAppId(packageInfo.applicationInfo.uid));
            int indexOfKey = this.mDelayedPermissionState.indexOfKey(uid);
            if (indexOfKey >= 0) {
                arrayMap = (ArrayMap) this.mDelayedPermissionState.valueAt(indexOfKey);
            } else {
                ArrayMap arrayMap2 = new ArrayMap();
                this.mDelayedPermissionState.put(uid, arrayMap2);
                arrayMap = arrayMap2;
            }
            int indexOfKey2 = arrayMap.indexOfKey(str);
            if (indexOfKey2 < 0) {
                PermissionState permissionState = new PermissionState(str, packageInfo, userHandle);
                arrayMap.put(str, permissionState);
                return permissionState;
            }
            PermissionState permissionState2 = (PermissionState) arrayMap.valueAt(indexOfKey2);
            if (ArrayUtils.contains(permissionState2.mPkgRequestingPerm.requestedPermissions, str)
                    || !ArrayUtils.contains(packageInfo.requestedPermissions, str)) {
                return permissionState2;
            }
            permissionState2.mPkgRequestingPerm = packageInfo;
            return permissionState2;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final void grantPermission(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            permissionState.newGranted = Boolean.TRUE;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final boolean isGranted(String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            return permissionState.newGranted.booleanValue();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final void revokePermission(
                String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            permissionState.newGranted = Boolean.FALSE;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public final void updatePermissionFlags(
                String str, PackageInfo packageInfo, int i, int i2, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initFlags();
            permissionState.newFlags =
                    Integer.valueOf((permissionState.newFlags.intValue() & (~i)) | (i2 & i));
        }
    }

    public abstract class PackageManagerWrapper {
        public PackageManagerWrapper() {}

        public abstract PackageInfo getPackageInfo(String str);

        public abstract int getPermissionFlags(
                String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract PermissionInfo getPermissionInfo(String str);

        public final PackageInfo getSystemPackageInfo(String str) {
            PackageInfo packageInfo = getPackageInfo(str);
            if (packageInfo == null || !packageInfo.applicationInfo.isSystemApp()) {
                return null;
            }
            return packageInfo;
        }

        public abstract void grantPermission(
                String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract boolean isGranted(
                String str, PackageInfo packageInfo, UserHandle userHandle);

        public final boolean isSysComponentOrPersistentPlatformSignedPrivApp(
                PackageInfo packageInfo) {
            if (UserHandle.getAppId(packageInfo.applicationInfo.uid) < 10000) {
                return true;
            }
            if (!packageInfo.applicationInfo.isPrivilegedApp()) {
                return false;
            }
            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                    DefaultPermissionGrantPolicy.this;
            PackageSetting disabledSystemPackage =
                    ((PackageManagerService.PackageManagerInternalImpl)
                                    defaultPermissionGrantPolicy.mServiceInternal)
                            .mService
                            .snapshotComputer()
                            .getDisabledSystemPackage(packageInfo.applicationInfo.packageName);
            AndroidPackage androidPackage =
                    disabledSystemPackage == null ? null : disabledSystemPackage.pkg;
            PackageInfo systemPackageInfo =
                    getSystemPackageInfo(
                            androidPackage != null ? androidPackage.getPackageName() : null);
            if (systemPackageInfo != null) {
                ApplicationInfo applicationInfo = systemPackageInfo.applicationInfo;
                if (applicationInfo != null && (applicationInfo.flags & 8) == 0) {
                    return false;
                }
            } else if ((packageInfo.applicationInfo.flags & 8) == 0) {
                return false;
            }
            return defaultPermissionGrantPolicy.mServiceInternal.isPlatformSigned(
                    packageInfo.packageName);
        }

        public final boolean isSystemPackage(PackageInfo packageInfo) {
            return (packageInfo == null
                            || !packageInfo.applicationInfo.isSystemApp()
                            || isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo))
                    ? false
                    : true;
        }

        public final boolean isSystemPackage(String str) {
            return isSystemPackage(getPackageInfo(str));
        }

        public abstract void revokePermission(
                String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract void updatePermissionFlags(
                String str, PackageInfo packageInfo, int i, int i2, UserHandle userHandle);
    }

    static {
        ArraySet arraySet = new ArraySet();
        PHONE_PERMISSIONS = arraySet;
        arraySet.add("android.permission.READ_PHONE_STATE");
        arraySet.add("android.permission.CALL_PHONE");
        arraySet.add("android.permission.READ_CALL_LOG");
        arraySet.add("android.permission.WRITE_CALL_LOG");
        arraySet.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        arraySet.add("android.permission.USE_SIP");
        arraySet.add("android.permission.PROCESS_OUTGOING_CALLS");
        ArraySet arraySet2 = new ArraySet();
        CONTACTS_PERMISSIONS = arraySet2;
        arraySet2.add("android.permission.READ_CONTACTS");
        arraySet2.add("android.permission.WRITE_CONTACTS");
        arraySet2.add("android.permission.GET_ACCOUNTS");
        ArraySet arraySet3 = new ArraySet();
        CALL_LOG_PERMISSIONS = arraySet3;
        arraySet3.add("android.permission.READ_CALL_LOG");
        arraySet3.add("android.permission.WRITE_CALL_LOG");
        ArraySet arraySet4 = new ArraySet();
        ALWAYS_LOCATION_PERMISSIONS = arraySet4;
        arraySet4.add("android.permission.ACCESS_FINE_LOCATION");
        arraySet4.add("android.permission.ACCESS_COARSE_LOCATION");
        arraySet4.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        ArraySet arraySet5 = new ArraySet();
        FOREGROUND_LOCATION_PERMISSIONS = arraySet5;
        arraySet5.add("android.permission.ACCESS_FINE_LOCATION");
        arraySet5.add("android.permission.ACCESS_COARSE_LOCATION");
        ArraySet arraySet6 = new ArraySet();
        COARSE_BACKGROUND_LOCATION_PERMISSIONS = arraySet6;
        arraySet6.add("android.permission.ACCESS_COARSE_LOCATION");
        arraySet6.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        ArraySet arraySet7 = new ArraySet();
        FINE_LOCATION_PERMISSIONS = arraySet7;
        arraySet7.add("android.permission.ACCESS_FINE_LOCATION");
        ArraySet arraySet8 = new ArraySet();
        ACTIVITY_RECOGNITION_PERMISSIONS = arraySet8;
        arraySet8.add("android.permission.ACTIVITY_RECOGNITION");
        ArraySet arraySet9 = new ArraySet();
        CALENDAR_PERMISSIONS = arraySet9;
        arraySet9.add("android.permission.READ_CALENDAR");
        arraySet9.add("android.permission.WRITE_CALENDAR");
        ArraySet arraySet10 = new ArraySet();
        SMS_PERMISSIONS = arraySet10;
        arraySet10.add("android.permission.SEND_SMS");
        arraySet10.add("android.permission.RECEIVE_SMS");
        arraySet10.add("android.permission.READ_SMS");
        arraySet10.add("android.permission.RECEIVE_WAP_PUSH");
        arraySet10.add("android.permission.RECEIVE_MMS");
        arraySet10.add("android.permission.READ_CELL_BROADCASTS");
        ArraySet arraySet11 = new ArraySet();
        MICROPHONE_PERMISSIONS = arraySet11;
        arraySet11.add("android.permission.RECORD_AUDIO");
        ArraySet arraySet12 = new ArraySet();
        CAMERA_PERMISSIONS = arraySet12;
        arraySet12.add("android.permission.CAMERA");
        ArraySet arraySet13 = new ArraySet();
        SENSORS_PERMISSIONS = arraySet13;
        arraySet13.add("android.permission.BODY_SENSORS");
        arraySet13.add("android.permission.BODY_SENSORS_BACKGROUND");
        ArraySet arraySet14 = new ArraySet();
        STORAGE_PERMISSIONS = arraySet14;
        arraySet14.add("android.permission.READ_EXTERNAL_STORAGE");
        arraySet14.add("android.permission.WRITE_EXTERNAL_STORAGE");
        arraySet14.add("android.permission.ACCESS_MEDIA_LOCATION");
        arraySet14.add("android.permission.READ_MEDIA_AUDIO");
        arraySet14.add("android.permission.READ_MEDIA_VIDEO");
        arraySet14.add("android.permission.READ_MEDIA_IMAGES");
        arraySet14.add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        ArraySet arraySet15 = new ArraySet();
        NEARBY_DEVICES_PERMISSIONS = arraySet15;
        arraySet15.add("android.permission.BLUETOOTH_ADVERTISE");
        arraySet15.add("android.permission.BLUETOOTH_CONNECT");
        arraySet15.add("android.permission.BLUETOOTH_SCAN");
        arraySet15.add("android.permission.UWB_RANGING");
        arraySet15.add("android.permission.NEARBY_WIFI_DEVICES");
        ArraySet arraySet16 = new ArraySet();
        NOTIFICATION_PERMISSIONS = arraySet16;
        arraySet16.add("android.permission.POST_NOTIFICATIONS");
    }

    public DefaultPermissionGrantPolicy(Context context) {
        this.mContext = context;
    }

    public static boolean doesPackageSupportRuntimePermissions(PackageInfo packageInfo) {
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        return applicationInfo != null && applicationInfo.targetSdkVersion > 22;
    }

    public static void revokeRuntimePermissions(
            PackageManagerWrapper packageManagerWrapper, String str, Set set, boolean z, int i) {
        PackageInfo systemPackageInfo = packageManagerWrapper.getSystemPackageInfo(str);
        if (systemPackageInfo == null
                || ArrayUtils.isEmpty(systemPackageInfo.requestedPermissions)) {
            return;
        }
        ArraySet arraySet = new ArraySet(Arrays.asList(systemPackageInfo.requestedPermissions));
        Iterator it = ((ArraySet) set).iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (arraySet.contains(str2)) {
                UserHandle of = UserHandle.of(i);
                int permissionFlags =
                        packageManagerWrapper.getPermissionFlags(
                                str2, packageManagerWrapper.getPackageInfo(str), of);
                if ((permissionFlags & 32) != 0
                        && (permissionFlags & 4) == 0
                        && ((permissionFlags & 16) == 0 || z)) {
                    packageManagerWrapper.revokePermission(str2, systemPackageInfo, of);
                    packageManagerWrapper.updatePermissionFlags(str2, systemPackageInfo, 32, 0, of);
                }
            }
        }
    }

    public final String getDefaultProviderAuthorityPackage(int i, String str) {
        ProviderInfo resolveContentProviderAsUser =
                this.mContext.getPackageManager().resolveContentProviderAsUser(str, 794624, i);
        if (resolveContentProviderAsUser != null) {
            return resolveContentProviderAsUser.packageName;
        }
        return null;
    }

    public final String getDefaultSystemHandlerActivityPackage(
            DelayingPackageManagerCache delayingPackageManagerCache, Intent intent, int i) {
        ActivityInfo activityInfo;
        ResolveInfo resolveActivityAsUser =
                this.mContext.getPackageManager().resolveActivityAsUser(intent, 794624, i);
        if (resolveActivityAsUser == null
                || (activityInfo = resolveActivityAsUser.activityInfo) == null) {
            return null;
        }
        PackageManagerService packageManagerService = PackageManagerService.this;
        if (packageManagerService.mResolveActivity.packageName.equals(
                        ((ComponentInfo) activityInfo).packageName)
                && packageManagerService.mResolveActivity.name.equals(
                        ((ComponentInfo) activityInfo).name)) {
            return null;
        }
        String str = resolveActivityAsUser.activityInfo.packageName;
        if (delayingPackageManagerCache.isSystemPackage(str)) {
            return str;
        }
        return null;
    }

    public final String getDefaultSystemHandlerActivityPackage(
            DelayingPackageManagerCache delayingPackageManagerCache, String str, int i) {
        return getDefaultSystemHandlerActivityPackage(
                delayingPackageManagerCache, new Intent(str), i);
    }

    public final String getDefaultSystemHandlerActivityPackageForCategory(
            DelayingPackageManagerCache delayingPackageManagerCache, String str, int i) {
        return getDefaultSystemHandlerActivityPackage(
                delayingPackageManagerCache,
                new Intent("android.intent.action.MAIN").addCategory(str),
                i);
    }

    public final String getDefaultSystemHandlerServicePackage(
            DelayingPackageManagerCache delayingPackageManagerCache, Intent intent, int i) {
        List queryIntentServicesAsUser =
                this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 794624, i);
        if (queryIntentServicesAsUser == null) {
            return null;
        }
        int size = queryIntentServicesAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo.packageName;
            if (delayingPackageManagerCache.isSystemPackage(str)) {
                return str;
            }
        }
        return null;
    }

    public final ArrayList getHeadlessSyncAdapterPackages(
            DelayingPackageManagerCache delayingPackageManagerCache, String[] strArr, int i) {
        ArrayList arrayList = new ArrayList();
        Intent addCategory =
                new Intent("android.intent.action.MAIN")
                        .addCategory("android.intent.category.LAUNCHER");
        for (String str : strArr) {
            addCategory.setPackage(str);
            if (this.mContext.getPackageManager().resolveActivityAsUser(addCategory, 794624, i)
                            == null
                    && delayingPackageManagerCache.isSystemPackage(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final void grantDefaultPermissionsToDefaultSimCallManager(
            PackageManagerWrapper packageManagerWrapper, String str, int i) {
        if (str == null) {
            return;
        }
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "Granting permissions to sim call manager for user:", "DefaultPermGrantPolicy");
        grantPermissionsToPackage(
                packageManagerWrapper,
                packageManagerWrapper.getPackageInfo(str),
                i,
                false,
                false,
                PHONE_PERMISSIONS,
                MICROPHONE_PERMISSIONS);
    }

    public final void grantDefaultPermissionsToDefaultSystemDialerApp(
            DelayingPackageManagerCache delayingPackageManagerCache, String str, int i) {
        if (str == null) {
            return;
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache,
                    str,
                    i,
                    true,
                    PHONE_PERMISSIONS,
                    NOTIFICATION_PERMISSIONS);
        } else {
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache, str, i, false, PHONE_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                str,
                i,
                false,
                CONTACTS_PERMISSIONS,
                SMS_PERMISSIONS,
                MICROPHONE_PERMISSIONS,
                CAMERA_PERMISSIONS,
                NOTIFICATION_PERMISSIONS);
        if (this.mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.type.automotive", 0)) {
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache, str, i, false, NEARBY_DEVICES_PERMISSIONS);
        }
    }

    public final void grantDefaultPermissionsToDefaultSystemSmsApp(
            DelayingPackageManagerCache delayingPackageManagerCache, String str, int i) {
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                str,
                i,
                false,
                PHONE_PERMISSIONS,
                CONTACTS_PERMISSIONS,
                SMS_PERMISSIONS,
                STORAGE_PERMISSIONS,
                MICROPHONE_PERMISSIONS,
                CAMERA_PERMISSIONS,
                NOTIFICATION_PERMISSIONS);
    }

    public final void grantDefaultSystemHandlerPermissions(
            DelayingPackageManagerCache delayingPackageManagerCache, int i) {
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider2;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider3;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider4;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider5;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider6;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider7;
        ContentService$$ExternalSyntheticLambda0 contentService$$ExternalSyntheticLambda0;
        String[] strArr;
        int i2;
        char c;
        PackageManager packageManager;
        PackageManager packageManager2;
        PackageInfo packageInfo;
        PackageManager packageManager3;
        PackageManager packageManager4;
        PackageManager packageManager5;
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i,
                "Granting permissions to default platform handlers for user ",
                "DefaultPermGrantPolicy");
        synchronized (this.mLock) {
            legacyPermissionManagerInternal$PackagesProvider = this.mLocationPackagesProvider;
            legacyPermissionManagerInternal$PackagesProvider2 = this.mLocationExtraPackagesProvider;
            legacyPermissionManagerInternal$PackagesProvider3 =
                    this.mVoiceInteractionPackagesProvider;
            legacyPermissionManagerInternal$PackagesProvider4 = this.mSmsAppPackagesProvider;
            legacyPermissionManagerInternal$PackagesProvider5 = this.mDialerAppPackagesProvider;
            legacyPermissionManagerInternal$PackagesProvider6 =
                    this.mSimCallManagerPackagesProvider;
            legacyPermissionManagerInternal$PackagesProvider7 =
                    this.mUseOpenWifiAppPackagesProvider;
            contentService$$ExternalSyntheticLambda0 = this.mSyncAdapterPackagesProvider;
        }
        String[] packages =
                legacyPermissionManagerInternal$PackagesProvider3 != null
                        ? legacyPermissionManagerInternal$PackagesProvider3.getPackages(i)
                        : null;
        String[] packages2 =
                legacyPermissionManagerInternal$PackagesProvider != null
                        ? legacyPermissionManagerInternal$PackagesProvider.getPackages(i)
                        : null;
        String[] packages3 =
                legacyPermissionManagerInternal$PackagesProvider2 != null
                        ? legacyPermissionManagerInternal$PackagesProvider2.getPackages(i)
                        : null;
        String[] packages4 =
                legacyPermissionManagerInternal$PackagesProvider4 != null
                        ? legacyPermissionManagerInternal$PackagesProvider4.getPackages(i)
                        : null;
        String[] packages5 =
                legacyPermissionManagerInternal$PackagesProvider5 != null
                        ? legacyPermissionManagerInternal$PackagesProvider5.getPackages(i)
                        : null;
        String[] packages6 =
                legacyPermissionManagerInternal$PackagesProvider6 != null
                        ? legacyPermissionManagerInternal$PackagesProvider6.getPackages(i)
                        : null;
        String[] packages7 =
                legacyPermissionManagerInternal$PackagesProvider7 != null
                        ? legacyPermissionManagerInternal$PackagesProvider7.getPackages(i)
                        : null;
        String[] syncAdapterPackagesForAuthorityAsUser =
                contentService$$ExternalSyntheticLambda0 != null
                        ? contentService$$ExternalSyntheticLambda0.f$0
                                .getSyncAdapterPackagesForAuthorityAsUser("com.android.contacts", i)
                        : null;
        String[] syncAdapterPackagesForAuthorityAsUser2 =
                contentService$$ExternalSyntheticLambda0 != null
                        ? contentService$$ExternalSyntheticLambda0.f$0
                                .getSyncAdapterPackagesForAuthorityAsUser("com.android.calendar", i)
                        : null;
        String permissionControllerPackageName =
                this.mContext.getPackageManager().getPermissionControllerPackageName();
        Set set = NOTIFICATION_PERMISSIONS;
        String[] strArr2 = syncAdapterPackagesForAuthorityAsUser;
        String[] strArr3 = packages4;
        String[] strArr4 = packages5;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, permissionControllerPackageName, i, true, set);
        String str =
                (String) ArrayUtils.firstOrNull(this.mServiceInternal.getKnownPackageNames(2, i));
        Set set2 = STORAGE_PERMISSIONS;
        grantPermissionsToSystemPackage(delayingPackageManagerCache, str, i, true, set2, set);
        String str2 =
                (String) ArrayUtils.firstOrNull(this.mServiceInternal.getKnownPackageNames(4, i));
        grantPermissionsToSystemPackage(delayingPackageManagerCache, str2, i, true, set2);
        Set set3 = PHONE_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, str2, i, false, set3, SMS_PERMISSIONS, set);
        String str3 =
                (String) ArrayUtils.firstOrNull(this.mServiceInternal.getKnownPackageNames(1, i));
        Set set4 = CAMERA_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                str3,
                i,
                false,
                set3,
                CONTACTS_PERMISSIONS,
                ALWAYS_LOCATION_PERMISSIONS,
                set4,
                NEARBY_DEVICES_PERMISSIONS);
        grantPermissionsToSystemPackage(delayingPackageManagerCache, str3, i, true, set);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                this.mContext.getString(R.string.demo_restarting_message),
                i,
                false,
                set);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                this.mContext.getString(R.string.date_picker_dialog_title),
                i,
                false,
                set);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                this.mContext.getString(R.string.db_default_journal_mode),
                i,
                false,
                set);
        String defaultSystemHandlerActivityPackage =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache, "android.media.action.IMAGE_CAPTURE", i);
        Set set5 = MICROPHONE_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackage,
                i,
                false,
                set4,
                set5,
                set2);
        PackageInfo packageInfo2 =
                delayingPackageManagerCache.getPackageInfo("com.samsung.android.fast");
        if (packageInfo2 == null
                || !doesPackageSupportRuntimePermissions(packageInfo2)
                || "KOREA"
                        .equalsIgnoreCase(
                                SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))
                || (packageManager5 = this.mContext.getPackageManager()) == null
                || packageManager5.checkSignatures("com.samsung.android.fast", "android") != 0) {
            strArr = packages6;
        } else {
            ArraySet arraySet = new ArraySet();
            arraySet.add("android.permission.ACCESS_FINE_LOCATION");
            arraySet.add("android.permission.ACCESS_COARSE_LOCATION");
            strArr = packages6;
            grantRuntimePermissions(
                    delayingPackageManagerCache, packageInfo2, arraySet, false, false, i);
        }
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache, "android.provider.MediaStore.RECORD_SOUND", i),
                i,
                false,
                set5);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultProviderAuthorityPackage(i, "media"),
                i,
                true,
                set2,
                set);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultProviderAuthorityPackage(i, "downloads"),
                i,
                true,
                set2,
                set);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache, "android.intent.action.VIEW_DOWNLOADS", i),
                i,
                true,
                set2);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultProviderAuthorityPackage(i, "com.android.externalstorage.documents"),
                i,
                true,
                set2);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache, "android.credentials.INSTALL", i),
                i,
                true,
                set2);
        if (strArr4 == null) {
            grantDefaultPermissionsToDefaultSystemDialerApp(
                    delayingPackageManagerCache,
                    getDefaultSystemHandlerActivityPackage(
                            delayingPackageManagerCache, "android.intent.action.DIAL", i),
                    i);
        } else {
            for (String str4 : strArr4) {
                grantDefaultPermissionsToDefaultSystemDialerApp(
                        delayingPackageManagerCache, str4, i);
            }
        }
        String[] strArr5 = strArr;
        if (strArr5 != null) {
            for (String str5 : strArr5) {
                if (delayingPackageManagerCache.isSystemPackage(str5)) {
                    grantDefaultPermissionsToDefaultSimCallManager(
                            delayingPackageManagerCache, str5, i);
                }
            }
        }
        if (packages7 != null) {
            int i3 = 0;
            for (int length = packages7.length; i3 < length; length = length) {
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache,
                        packages7[i3],
                        i,
                        false,
                        ALWAYS_LOCATION_PERMISSIONS);
                i3++;
            }
        }
        if (strArr3 == null) {
            grantDefaultPermissionsToDefaultSystemSmsApp(
                    delayingPackageManagerCache,
                    getDefaultSystemHandlerActivityPackageForCategory(
                            delayingPackageManagerCache,
                            "android.intent.category.APP_MESSAGING",
                            i),
                    i);
        } else {
            for (String str6 : strArr3) {
                grantDefaultPermissionsToDefaultSystemSmsApp(delayingPackageManagerCache, str6, i);
            }
        }
        String defaultSystemHandlerActivityPackage2 =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache,
                        "android.provider.Telephony.SMS_CB_RECEIVED",
                        i);
        char c2 = 3;
        Set set6 = SMS_PERMISSIONS;
        Set set7 = NOTIFICATION_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackage2,
                i,
                true,
                set6,
                NEARBY_DEVICES_PERMISSIONS,
                set7);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerServicePackage(
                        delayingPackageManagerCache,
                        new Intent("android.provider.Telephony.SMS_CARRIER_PROVISION"),
                        i),
                i,
                false,
                set6);
        String defaultSystemHandlerActivityPackageForCategory =
                getDefaultSystemHandlerActivityPackageForCategory(
                        delayingPackageManagerCache, "android.intent.category.APP_CALENDAR", i);
        Set set8 = CALENDAR_PERMISSIONS;
        Set set9 = CONTACTS_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackageForCategory,
                i,
                false,
                set8,
                set9,
                set7);
        String defaultProviderAuthorityPackage =
                getDefaultProviderAuthorityPackage(i, "com.android.calendar");
        Set set10 = STORAGE_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultProviderAuthorityPackage,
                i,
                false,
                set9,
                set10);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, defaultProviderAuthorityPackage, i, true, set8);
        if (syncAdapterPackagesForAuthorityAsUser2 != null) {
            ArrayList headlessSyncAdapterPackages =
                    getHeadlessSyncAdapterPackages(
                            delayingPackageManagerCache, syncAdapterPackagesForAuthorityAsUser2, i);
            Set[] setArr = {set8};
            int i4 = 0;
            for (int size = headlessSyncAdapterPackages.size(); i4 < size; size = size) {
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache,
                        (String) headlessSyncAdapterPackages.get(i4),
                        i,
                        false,
                        setArr);
                i4++;
                setArr = setArr;
            }
        }
        PackageInfo systemPackageInfo =
                delayingPackageManagerCache.getSystemPackageInfo("com.samsung.android.scs");
        if (systemPackageInfo == null || !doesPackageSupportRuntimePermissions(systemPackageInfo)) {
            i2 = 2;
        } else {
            ArraySet arraySet2 = new ArraySet();
            arraySet2.add("android.permission.READ_CONTACTS");
            arraySet2.add("android.permission.READ_CALENDAR");
            arraySet2.add("android.permission.READ_CALL_LOG");
            arraySet2.add("android.permission.READ_SMS");
            arraySet2.add("android.permission.READ_EXTERNAL_STORAGE");
            i2 = 2;
            grantRuntimePermissions(
                    delayingPackageManagerCache, systemPackageInfo, arraySet2, false, false, i);
        }
        String defaultSystemHandlerActivityPackageForCategory2 =
                getDefaultSystemHandlerActivityPackageForCategory(
                        delayingPackageManagerCache, "android.intent.category.APP_CONTACTS", i);
        Set[] setArr2 = new Set[i2];
        setArr2[0] = set9;
        Set set11 = PHONE_PERMISSIONS;
        setArr2[1] = set11;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackageForCategory2,
                i,
                false,
                setArr2);
        if (strArr2 != null) {
            ArrayList headlessSyncAdapterPackages2 =
                    getHeadlessSyncAdapterPackages(delayingPackageManagerCache, strArr2, i);
            Set[] setArr3 = {set9};
            int size2 = headlessSyncAdapterPackages2.size();
            int i5 = 0;
            while (i5 < size2) {
                Set[] setArr4 = setArr3;
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache,
                        (String) headlessSyncAdapterPackages2.get(i5),
                        i,
                        false,
                        setArr4);
                i5++;
                size2 = size2;
                headlessSyncAdapterPackages2 = headlessSyncAdapterPackages2;
                setArr3 = setArr4;
            }
        }
        String defaultProviderAuthorityPackage2 =
                getDefaultProviderAuthorityPackage(i, "com.android.contacts");
        Set[] setArr5 = new Set[3];
        setArr5[0] = set9;
        setArr5[1] = set11;
        setArr5[i2] = CALL_LOG_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, defaultProviderAuthorityPackage2, i, true, setArr5);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, defaultProviderAuthorityPackage2, i, false, set10);
        String defaultSystemHandlerActivityPackage3 =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache,
                        "android.app.action.PROVISION_MANAGED_DEVICE",
                        i);
        Set[] setArr6 = new Set[i2];
        setArr6[0] = set9;
        setArr6[1] = set7;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackage3,
                i,
                false,
                setArr6);
        if (this.mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.type.automotive", 0)) {
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache,
                    getDefaultSystemHandlerActivityPackageForCategory(
                            delayingPackageManagerCache, "android.intent.category.APP_MAPS", i),
                    i,
                    false,
                    FOREGROUND_LOCATION_PERMISSIONS);
        }
        String defaultSystemHandlerActivityPackageForCategory3 =
                getDefaultSystemHandlerActivityPackageForCategory(
                        delayingPackageManagerCache, "android.intent.category.APP_EMAIL", i);
        Set[] setArr7 = new Set[i2];
        setArr7[0] = set9;
        setArr7[1] = set8;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackageForCategory3,
                i,
                false,
                setArr7);
        String str7 =
                (String) ArrayUtils.firstOrNull(this.mServiceInternal.getKnownPackageNames(5, i));
        if (str7 == null) {
            str7 =
                    getDefaultSystemHandlerActivityPackageForCategory(
                            delayingPackageManagerCache, "android.intent.category.APP_BROWSER", i);
            if (!delayingPackageManagerCache.isSystemPackage(str7)) {
                str7 = null;
            }
        }
        grantPermissionsToPackage(
                delayingPackageManagerCache,
                delayingPackageManagerCache.getPackageInfo(str7),
                i,
                false,
                false,
                FOREGROUND_LOCATION_PERMISSIONS);
        char c3 = 7;
        int i6 = 8;
        int i7 = 6;
        if (packages != null) {
            int length2 = packages.length;
            int i8 = 0;
            while (i8 < length2) {
                String str8 = packages[i8];
                Set[] setArr8 = new Set[i6];
                setArr8[0] = CONTACTS_PERMISSIONS;
                setArr8[1] = CALENDAR_PERMISSIONS;
                setArr8[i2] = MICROPHONE_PERMISSIONS;
                setArr8[c2] = PHONE_PERMISSIONS;
                setArr8[4] = SMS_PERMISSIONS;
                setArr8[5] = COARSE_BACKGROUND_LOCATION_PERMISSIONS;
                setArr8[i7] = NEARBY_DEVICES_PERMISSIONS;
                setArr8[c3] = NOTIFICATION_PERMISSIONS;
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache, str8, i, false, setArr8);
                revokeRuntimePermissions(
                        delayingPackageManagerCache, str8, FINE_LOCATION_PERMISSIONS, false, i);
                i8++;
                i7 = i7;
                i6 = i6;
                length2 = length2;
                c3 = 7;
                c2 = 3;
            }
        }
        int i9 = i7;
        int i10 = i6;
        if (ActivityManager.isLowRamDeviceStatic()) {
            String defaultSystemHandlerActivityPackage4 =
                    getDefaultSystemHandlerActivityPackage(
                            delayingPackageManagerCache, "android.search.action.GLOBAL_SEARCH", i);
            Set[] setArr9 = new Set[5];
            setArr9[0] = MICROPHONE_PERMISSIONS;
            setArr9[1] = ALWAYS_LOCATION_PERMISSIONS;
            setArr9[i2] = NOTIFICATION_PERMISSIONS;
            setArr9[3] = PHONE_PERMISSIONS;
            setArr9[4] = CALENDAR_PERMISSIONS;
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache,
                    defaultSystemHandlerActivityPackage4,
                    i,
                    false,
                    setArr9);
        }
        String defaultSystemHandlerServicePackage =
                getDefaultSystemHandlerServicePackage(
                        delayingPackageManagerCache,
                        new Intent("android.speech.RecognitionService")
                                .addCategory("android.intent.category.DEFAULT"),
                        i);
        Set set12 = MICROPHONE_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, defaultSystemHandlerServicePackage, i, false, set12);
        if ("CHINA"
                .equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            Set[] setArr10 = new Set[9];
            setArr10[0] = CONTACTS_PERMISSIONS;
            setArr10[1] = CALENDAR_PERMISSIONS;
            setArr10[i2] = set12;
            setArr10[3] = PHONE_PERMISSIONS;
            setArr10[4] = SMS_PERMISSIONS;
            setArr10[5] = CAMERA_PERMISSIONS;
            setArr10[i9] = SENSORS_PERMISSIONS;
            setArr10[7] = STORAGE_PERMISSIONS;
            setArr10[i10] = NEARBY_DEVICES_PERMISSIONS;
            c = '\t';
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache, "com.google.android.gms", i, false, setArr10);
            Set[] setArr11 = new Set[i2];
            setArr11[0] = ALWAYS_LOCATION_PERMISSIONS;
            setArr11[1] = ACTIVITY_RECOGNITION_PERMISSIONS;
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache, "com.google.android.gms", i, true, setArr11);
        } else {
            c = '\t';
        }
        if (packages2 != null) {
            int length3 = packages2.length;
            int i11 = 0;
            while (i11 < length3) {
                String str9 = packages2[i11];
                Set[] setArr12 = new Set[10];
                setArr12[0] = CONTACTS_PERMISSIONS;
                setArr12[1] = CALENDAR_PERMISSIONS;
                setArr12[i2] = MICROPHONE_PERMISSIONS;
                setArr12[3] = PHONE_PERMISSIONS;
                setArr12[4] = SMS_PERMISSIONS;
                setArr12[5] = CAMERA_PERMISSIONS;
                setArr12[i9] = SENSORS_PERMISSIONS;
                setArr12[7] = STORAGE_PERMISSIONS;
                setArr12[i10] = NEARBY_DEVICES_PERMISSIONS;
                setArr12[c] = NOTIFICATION_PERMISSIONS;
                int i12 = length3;
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache, str9, i, false, setArr12);
                Set[] setArr13 = new Set[i2];
                setArr13[0] = ALWAYS_LOCATION_PERMISSIONS;
                setArr13[1] = ACTIVITY_RECOGNITION_PERMISSIONS;
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache, str9, i, true, setArr13);
                i11++;
                length3 = i12;
            }
        }
        if (packages3 != null) {
            for (String str10 : packages3) {
                Set[] setArr14 = new Set[i2];
                setArr14[0] = ALWAYS_LOCATION_PERMISSIONS;
                setArr14[1] = NEARBY_DEVICES_PERMISSIONS;
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache, str10, i, false, setArr14);
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache,
                        str10,
                        i,
                        true,
                        ACTIVITY_RECOGNITION_PERMISSIONS);
            }
        }
        String defaultSystemHandlerActivityPackage5 =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache,
                        new Intent("android.intent.action.VIEW")
                                .addCategory("android.intent.category.DEFAULT")
                                .setDataAndType(Uri.fromFile(new File("foo.mp3")), "audio/mpeg"),
                        i);
        Set set13 = STORAGE_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, defaultSystemHandlerActivityPackage5, i, false, set13);
        String defaultSystemHandlerActivityPackage6 =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache,
                        new Intent("android.intent.action.MAIN")
                                .addCategory("android.intent.category.HOME")
                                .addCategory("android.intent.category.LAUNCHER_APP"),
                        i);
        Set[] setArr15 = new Set[i2];
        Set set14 = ALWAYS_LOCATION_PERMISSIONS;
        setArr15[0] = set14;
        Set set15 = NOTIFICATION_PERMISSIONS;
        setArr15[1] = set15;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackage6,
                i,
                false,
                setArr15);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
            String defaultSystemHandlerActivityPackageForCategory4 =
                    getDefaultSystemHandlerActivityPackageForCategory(
                            delayingPackageManagerCache, "android.intent.category.HOME_MAIN", i);
            Set[] setArr16 = new Set[3];
            setArr16[0] = CONTACTS_PERMISSIONS;
            setArr16[1] = MICROPHONE_PERMISSIONS;
            setArr16[i2] = set14;
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache,
                    defaultSystemHandlerActivityPackageForCategory4,
                    i,
                    false,
                    setArr16);
            Set[] setArr17 = new Set[i2];
            setArr17[0] = PHONE_PERMISSIONS;
            setArr17[1] = ACTIVITY_RECOGNITION_PERMISSIONS;
            grantPermissionsToSystemPackage(
                    delayingPackageManagerCache,
                    defaultSystemHandlerActivityPackageForCategory4,
                    i,
                    true,
                    setArr17);
            if (this.mContext.getResources().getBoolean(R.bool.config_useLegacySplit)) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "Wear: Skipping permission grant for Default fitness tracker app : ",
                        defaultSystemHandlerActivityPackageForCategory4,
                        "DefaultPermGrantPolicy");
            } else {
                grantPermissionsToSystemPackage(
                        delayingPackageManagerCache,
                        getDefaultSystemHandlerActivityPackage(
                                delayingPackageManagerCache, "com.android.fitness.TRACK", i),
                        i,
                        false,
                        SENSORS_PERMISSIONS);
            }
        }
        Set[] setArr18 = new Set[i2];
        setArr18[0] = set14;
        setArr18[1] = set15;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, "com.android.printspooler", i, true, setArr18);
        String defaultSystemHandlerActivityPackage7 =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache,
                        "android.telephony.action.EMERGENCY_ASSISTANCE",
                        i);
        Set[] setArr19 = new Set[i2];
        Set set16 = CONTACTS_PERMISSIONS;
        setArr19[0] = set16;
        Set set17 = PHONE_PERMISSIONS;
        setArr19[1] = set17;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackage7,
                i,
                true,
                setArr19);
        String defaultSystemHandlerActivityPackage8 =
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache,
                        new Intent("android.intent.action.VIEW")
                                .setType("vnd.android.cursor.item/ndef_msg"),
                        i);
        Set[] setArr20 = new Set[i2];
        setArr20[0] = set16;
        setArr20[1] = set17;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                defaultSystemHandlerActivityPackage8,
                i,
                false,
                setArr20);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache, "android.os.storage.action.MANAGE_STORAGE", i),
                i,
                true,
                set13);
        String string = this.mContext.getString(R.string.csd_momentary_exposure_warning);
        Set[] setArr21 = new Set[i2];
        setArr21[0] = set14;
        setArr21[1] = NEARBY_DEVICES_PERMISSIONS;
        grantPermissionsToSystemPackage(delayingPackageManagerCache, string, i, true, setArr21);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerActivityPackage(
                        delayingPackageManagerCache, "android.intent.action.RINGTONE_PICKER", i),
                i,
                true,
                set13);
        for (String str11 : this.mServiceInternal.getKnownPackageNames(i9, i)) {
            Set[] setArr22 = new Set[i2];
            setArr22[0] = COARSE_BACKGROUND_LOCATION_PERMISSIONS;
            setArr22[1] = CONTACTS_PERMISSIONS;
            grantPermissionsToSystemPackage(delayingPackageManagerCache, str11, i, false, setArr22);
        }
        Set set18 = STORAGE_PERMISSIONS;
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache, "com.android.sharedstoragebackup", i, true, set18);
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                "com.android.bluetoothmidiservice",
                i,
                true,
                NEARBY_DEVICES_PERMISSIONS);
        PackageInfo packageInfo3 =
                delayingPackageManagerCache.getPackageInfo("com.sec.android.app.clockpackage");
        if (packageInfo3 != null && doesPackageSupportRuntimePermissions(packageInfo3)) {
            grantRuntimePermissions(
                    delayingPackageManagerCache,
                    packageInfo3,
                    NOTIFICATION_PERMISSIONS,
                    false,
                    false,
                    i);
        }
        PackageInfo systemPackageInfo2 =
                delayingPackageManagerCache.getSystemPackageInfo(
                        "com.samsung.android.dck.timesync");
        if (systemPackageInfo2 != null
                && doesPackageSupportRuntimePermissions(systemPackageInfo2)) {
            ArraySet arraySet3 = new ArraySet();
            delayingPackageManagerCache.updatePermissionFlags(
                    "android.permission.BLUETOOTH_CONNECT",
                    systemPackageInfo2,
                    16,
                    16,
                    UserHandle.of(i));
            ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class))
                    .setUidMode(111, 1000, 0);
            arraySet3.add("android.permission.BLUETOOTH_CONNECT");
            grantRuntimePermissions(
                    delayingPackageManagerCache, systemPackageInfo2, arraySet3, true, false, i);
        }
        grantPermissionsToSystemPackage(
                delayingPackageManagerCache,
                getDefaultSystemHandlerServicePackage(
                        delayingPackageManagerCache,
                        new Intent("android.adservices.AD_SERVICES_COMMON_SERVICE"),
                        i),
                i,
                false,
                NOTIFICATION_PERMISSIONS);
        PackageInfo systemPackageInfo3 =
                delayingPackageManagerCache.getSystemPackageInfo("com.sec.android.app.samsungapps");
        if (systemPackageInfo3 != null
                && doesPackageSupportRuntimePermissions(systemPackageInfo3)) {
            ArraySet arraySet4 = new ArraySet();
            if (ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(
                    SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                arraySet4.add("android.permission.POST_NOTIFICATIONS");
                grantRuntimePermissions(
                        delayingPackageManagerCache,
                        systemPackageInfo3,
                        arraySet4,
                        false,
                        false,
                        i);
            } else if ("KOREA"
                    .equalsIgnoreCase(
                            SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                arraySet4.addAll(CONTACTS_PERMISSIONS);
                arraySet4.add("android.permission.POST_NOTIFICATIONS");
                grantRuntimePermissions(
                        delayingPackageManagerCache,
                        systemPackageInfo3,
                        arraySet4,
                        false,
                        false,
                        i);
            } else {
                arraySet4.add("android.permission.POST_NOTIFICATIONS");
                grantRuntimePermissions(
                        delayingPackageManagerCache,
                        systemPackageInfo3,
                        arraySet4,
                        false,
                        false,
                        i);
            }
        }
        PackageInfo packageInfo4 =
                delayingPackageManagerCache.getPackageInfo("com.samsung.android.video");
        if (packageInfo4 != null
                && doesPackageSupportRuntimePermissions(packageInfo4)
                && (packageManager4 = this.mContext.getPackageManager()) != null
                && packageManager4.checkSignatures("com.samsung.android.video", "android") == 0) {
            grantRuntimePermissions(
                    delayingPackageManagerCache, packageInfo4, set18, false, false, i);
        }
        if (!"CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))
                && (packageInfo =
                                delayingPackageManagerCache.getPackageInfo(
                                        "com.samsung.app.newtrim"))
                        != null
                && doesPackageSupportRuntimePermissions(packageInfo)
                && (packageManager3 = this.mContext.getPackageManager()) != null
                && packageManager3.checkSignatures("com.samsung.app.newtrim", "android") == 0) {
            grantRuntimePermissions(
                    delayingPackageManagerCache, packageInfo, set18, false, false, i);
        }
        PackageInfo systemPackageInfo4 =
                delayingPackageManagerCache.getSystemPackageInfo("com.samsung.android.bixby.agent");
        if (systemPackageInfo4 != null
                && doesPackageSupportRuntimePermissions(systemPackageInfo4)) {
            String str12 = SemSystemProperties.get("ro.build.characteristics");
            boolean z = str12 != null && str12.contains("tablet");
            boolean z2 =
                    delayingPackageManagerCache.getSystemPackageInfo("com.samsung.android.dialer")
                            != null;
            boolean z3 =
                    delayingPackageManagerCache.getSystemPackageInfo(
                                    "com.samsung.android.messaging")
                            != null;
            ArraySet arraySet5 = new ArraySet();
            arraySet5.add("android.permission.RECORD_AUDIO");
            arraySet5.add("android.permission.READ_CALENDAR");
            arraySet5.add("android.permission.WRITE_CALENDAR");
            if (z2) {
                arraySet5.add("android.permission.READ_CALL_LOG");
                arraySet5.add("android.permission.WRITE_CALL_LOG");
            }
            arraySet5.add("android.permission.CAMERA");
            arraySet5.add("android.permission.READ_CONTACTS");
            arraySet5.add("android.permission.WRITE_CONTACTS");
            arraySet5.add("android.permission.ACCESS_FINE_LOCATION");
            arraySet5.add("android.permission.ACCESS_COARSE_LOCATION");
            arraySet5.add("android.permission.ACCESS_BACKGROUND_LOCATION");
            if (z3) {
                arraySet5.add("android.permission.SEND_SMS");
                arraySet5.add("android.permission.READ_SMS");
                arraySet5.add("android.permission.RECEIVE_SMS");
                arraySet5.add("android.permission.RECEIVE_WAP_PUSH");
                arraySet5.add("android.permission.RECEIVE_MMS");
            }
            arraySet5.add("android.permission.READ_PHONE_STATE");
            arraySet5.add("android.permission.CALL_PHONE");
            arraySet5.add("android.permission.READ_EXTERNAL_STORAGE");
            arraySet5.add("android.permission.WRITE_EXTERNAL_STORAGE");
            if (!z) {
                arraySet5.add("android.permission.BODY_SENSORS");
                arraySet5.add("android.permission.ACTIVITY_RECOGNITION");
            }
            arraySet5.add("android.permission.BLUETOOTH_ADVERTISE");
            arraySet5.add("android.permission.BLUETOOTH_CONNECT");
            arraySet5.add("android.permission.BLUETOOTH_SCAN");
            arraySet5.add("android.permission.READ_MEDIA_IMAGES");
            arraySet5.add("android.permission.READ_MEDIA_VIDEO");
            arraySet5.add("android.permission.READ_MEDIA_AUDIO");
            arraySet5.add("android.permission.POST_NOTIFICATIONS");
            grantRuntimePermissions(
                    delayingPackageManagerCache, systemPackageInfo4, arraySet5, false, false, i);
            Settings.Global.putString(
                    this.mContext.getContentResolver(),
                    "bixby_pregranted_permissions",
                    String.join(";", arraySet5));
        }
        PackageInfo packageInfo5 =
                delayingPackageManagerCache.getPackageInfo("com.sec.android.app.vepreload");
        if (packageInfo5 != null
                && doesPackageSupportRuntimePermissions(packageInfo5)
                && (packageManager2 = this.mContext.getPackageManager()) != null
                && packageManager2.checkSignatures("com.sec.android.app.vepreload", "android") == 0
                && !ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(
                        SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            grantRuntimePermissions(
                    delayingPackageManagerCache, packageInfo5, set18, false, false, i);
        }
        PackageInfo systemPackageInfo5 =
                delayingPackageManagerCache.getSystemPackageInfo(
                        "com.sec.location.nfwlocationprivacy");
        if ("KOREA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))
                && systemPackageInfo5 != null
                && doesPackageSupportRuntimePermissions(systemPackageInfo5)) {
            ArraySet arraySet6 = new ArraySet();
            arraySet6.add("android.permission.ACCESS_FINE_LOCATION");
            arraySet6.add("android.permission.ACCESS_BACKGROUND_LOCATION");
            grantRuntimePermissions(
                    delayingPackageManagerCache, systemPackageInfo5, arraySet6, true, false, i);
        }
        PackageInfo packageInfo6 =
                delayingPackageManagerCache.getPackageInfo(
                        "com.sec.android.mimage.photoretouching");
        if (packageInfo6 == null
                || !doesPackageSupportRuntimePermissions(packageInfo6)
                || (packageManager = this.mContext.getPackageManager()) == null
                || packageManager.checkSignatures(
                                "com.sec.android.mimage.photoretouching", "android")
                        != 0
                || ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(
                        SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            return;
        }
        grantRuntimePermissions(delayingPackageManagerCache, packageInfo6, set18, false, false, i);
    }

    public final void grantPermissionsToPackage(
            PackageManagerWrapper packageManagerWrapper,
            PackageInfo packageInfo,
            int i,
            boolean z,
            boolean z2,
            Set... setArr) {
        if (packageInfo != null && doesPackageSupportRuntimePermissions(packageInfo)) {
            for (Set set : setArr) {
                grantRuntimePermissions(packageManagerWrapper, packageInfo, set, z, z2, i);
            }
        }
    }

    public final void grantPermissionsToSystemPackage(
            PackageManagerWrapper packageManagerWrapper,
            String str,
            int i,
            boolean z,
            Set... setArr) {
        if (packageManagerWrapper.isSystemPackage(str)) {
            grantPermissionsToPackage(
                    packageManagerWrapper,
                    packageManagerWrapper.getSystemPackageInfo(str),
                    i,
                    z,
                    false,
                    setArr);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x01c1, code lost:

       if (((java.lang.Boolean) ((java.util.HashMap) r20.mNotiBlockableDataByUid).get(java.lang.Integer.valueOf(r22.applicationInfo.uid))).booleanValue() != false) goto L107;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void grantRuntimePermissions(
            com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper r21,
            android.content.pm.PackageInfo r22,
            java.util.Set r23,
            boolean r24,
            boolean r25,
            int r26) {
        /*
            Method dump skipped, instructions count: 533
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.permission.DefaultPermissionGrantPolicy.grantRuntimePermissions(com.android.server.pm.permission.DefaultPermissionGrantPolicy$PackageManagerWrapper,"
                    + " android.content.pm.PackageInfo, java.util.Set, boolean, boolean,"
                    + " int):void");
    }

    public final void grantRuntimePermissionsForSystemPackage(
            DelayingPackageManagerCache delayingPackageManagerCache,
            int i,
            PackageInfo packageInfo,
            Set set) {
        if (ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
            return;
        }
        ArraySet arraySet = new ArraySet();
        for (String str : packageInfo.requestedPermissions) {
            PermissionInfo permissionInfo = delayingPackageManagerCache.getPermissionInfo(str);
            if (permissionInfo != null
                    && ((set == null || ((ArraySet) set).contains(str))
                            && permissionInfo.isRuntime())) {
                arraySet.add(str);
            }
        }
        if (arraySet.isEmpty()) {
            return;
        }
        grantRuntimePermissions(delayingPackageManagerCache, packageInfo, arraySet, true, false, i);
    }

    public final void parse(
            PackageManagerWrapper packageManagerWrapper,
            TypedXmlPullParser typedXmlPullParser,
            Map map) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            int i = 1;
            if (next == 1) {
                return;
            }
            int i2 = 3;
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3) {
                int i3 = 4;
                if (next != 4) {
                    if ("exceptions".equals(typedXmlPullParser.getName())) {
                        int depth2 = typedXmlPullParser.getDepth();
                        while (true) {
                            int next2 = typedXmlPullParser.next();
                            if (next2 == i
                                    || (next2 == i2 && typedXmlPullParser.getDepth() <= depth2)) {
                                break;
                            }
                            if (next2 != i2 && next2 != i3) {
                                if ("exception".equals(typedXmlPullParser.getName())) {
                                    String attributeValue =
                                            typedXmlPullParser.getAttributeValue(
                                                    (String) null, "package");
                                    String attributeValue2 =
                                            typedXmlPullParser.getAttributeValue(
                                                    (String) null, "cert");
                                    ArrayMap arrayMap = (ArrayMap) map;
                                    List list = (List) arrayMap.get(attributeValue);
                                    if (list == null) {
                                        PackageInfo packageInfo =
                                                packageManagerWrapper.getPackageInfo(
                                                        attributeValue);
                                        if (packageInfo == null) {
                                            Log.w(
                                                    "DefaultPermGrantPolicy",
                                                    "No such package:" + attributeValue);
                                            XmlUtils.skipCurrentTag(typedXmlPullParser);
                                        } else {
                                            if ((attributeValue2 == null
                                                            ? (packageInfo.applicationInfo
                                                                                    .isSystemApp()
                                                                            || this.mServiceInternal
                                                                                    .isPlatformSigned(
                                                                                            packageInfo
                                                                                                    .packageName))
                                                                    ? i
                                                                    : 0
                                                            : this.mContext
                                                                    .getPackageManager()
                                                                    .hasSigningCertificate(
                                                                            packageInfo.packageName,
                                                                            HexEncoding.decode(
                                                                                    attributeValue2
                                                                                            .replace(
                                                                                                    ":",
                                                                                                    "")),
                                                                            i))
                                                    == 0) {
                                                Log.w(
                                                        "DefaultPermGrantPolicy",
                                                        "Not system or platform signed or"
                                                            + " certificate-matching package: "
                                                                + attributeValue);
                                                XmlUtils.skipCurrentTag(typedXmlPullParser);
                                            } else if (doesPackageSupportRuntimePermissions(
                                                    packageInfo)) {
                                                list = new ArrayList();
                                                arrayMap.put(attributeValue, list);
                                            } else {
                                                Log.w(
                                                        "DefaultPermGrantPolicy",
                                                        "Skipping non supporting runtime"
                                                            + " permissions package:"
                                                                + attributeValue);
                                                XmlUtils.skipCurrentTag(typedXmlPullParser);
                                            }
                                            i2 = 3;
                                            i3 = 4;
                                        }
                                    }
                                    int depth3 = typedXmlPullParser.getDepth();
                                    while (true) {
                                        int next3 = typedXmlPullParser.next();
                                        if (next3 == i
                                                || (next3 == 3
                                                        && typedXmlPullParser.getDepth()
                                                                <= depth3)) {
                                            break;
                                        }
                                        if (next3 != 3 && next3 != 4) {
                                            if ("permission"
                                                    .contains(typedXmlPullParser.getName())) {
                                                String attributeValue3 =
                                                        typedXmlPullParser.getAttributeValue(
                                                                (String) null, "name");
                                                if (attributeValue3 == null) {
                                                    Log.w(
                                                            "DefaultPermGrantPolicy",
                                                            "Mandatory name attribute missing for"
                                                                + " permission tag");
                                                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                                                } else {
                                                    list.add(
                                                            new DefaultPermissionGrant(
                                                                    attributeValue3,
                                                                    typedXmlPullParser
                                                                            .getAttributeBoolean(
                                                                                    (String) null,
                                                                                    "fixed",
                                                                                    false),
                                                                    typedXmlPullParser
                                                                            .getAttributeBoolean(
                                                                                    (String) null,
                                                                                    "whitelisted",
                                                                                    false)));
                                                }
                                            } else {
                                                Log.e(
                                                        "DefaultPermGrantPolicy",
                                                        "Unknown tag "
                                                                + typedXmlPullParser.getName()
                                                                + "under <exception>");
                                            }
                                            i = 1;
                                        }
                                    }
                                    i3 = 4;
                                } else {
                                    Log.e(
                                            "DefaultPermGrantPolicy",
                                            "Unknown tag "
                                                    + typedXmlPullParser.getName()
                                                    + "under <exceptions>");
                                }
                                i = 1;
                                i2 = 3;
                            }
                        }
                    } else {
                        Log.e(
                                "DefaultPermGrantPolicy",
                                "Unknown tag " + typedXmlPullParser.getName());
                    }
                }
            }
        }
    }

    public final ArrayMap readDefaultPermissionExceptionsLocked(
            PackageManagerWrapper packageManagerWrapper) {
        ArrayList arrayList = new ArrayList();
        File file = new File(Environment.getRootDirectory(), "etc/default-permissions");
        if (file.isDirectory() && file.canRead()) {
            Collections.addAll(arrayList, file.listFiles());
        }
        File file2 = new File(Environment.getVendorDirectory(), "etc/default-permissions");
        if (file2.isDirectory() && file2.canRead()) {
            Collections.addAll(arrayList, file2.listFiles());
        }
        File file3 = new File(Environment.getOdmDirectory(), "etc/default-permissions");
        if (file3.isDirectory() && file3.canRead()) {
            Collections.addAll(arrayList, file3.listFiles());
        }
        File file4 = new File(Environment.getProductDirectory(), "etc/default-permissions");
        if (file4.isDirectory() && file4.canRead()) {
            Collections.addAll(arrayList, file4.listFiles());
        }
        File file5 = new File(Environment.getSystemExtDirectory(), "etc/default-permissions");
        if (file5.isDirectory() && file5.canRead()) {
            Collections.addAll(arrayList, file5.listFiles());
        }
        File file6 = new File(Environment.getOemDirectory(), "etc/default-permissions");
        if (file6.isDirectory() && file6.canRead()) {
            Collections.addAll(arrayList, file6.listFiles());
        }
        File file7 =
                new File(SystemProperties.get("mdc.sys.omc_etcpath", ""), "default-permissions");
        if (file7.isDirectory() && file7.canRead()) {
            Collections.addAll(arrayList, file7.listFiles());
        }
        File[] fileArr = arrayList.isEmpty() ? null : (File[]) arrayList.toArray(new File[0]);
        if (fileArr == null) {
            return new ArrayMap(0);
        }
        ArrayMap arrayMap = new ArrayMap();
        for (File file8 : fileArr) {
            if (!file8.getPath().endsWith(".xml")) {
                Slog.i(
                        "DefaultPermGrantPolicy",
                        "Non-xml file "
                                + file8
                                + " in "
                                + file8.getParent()
                                + " directory, ignoring");
            } else if (file8.canRead()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file8);
                    try {
                        parse(
                                packageManagerWrapper,
                                Xml.resolvePullParser(fileInputStream),
                                arrayMap);
                        fileInputStream.close();
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException | XmlPullParserException e) {
                    Slog.w(
                            "DefaultPermGrantPolicy",
                            "Error reading default permissions file " + file8,
                            e);
                }
            } else {
                Slog.w(
                        "DefaultPermGrantPolicy",
                        "Default permissions file " + file8 + " cannot be read");
            }
        }
        return arrayMap;
    }
}
