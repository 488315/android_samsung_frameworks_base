package com.android.server.pm.permission;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.permission.PermissionManager;
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
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.permission.LegacyPermissionManagerInternal;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.rune.PMRune;
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
import java.util.Objects;
import java.util.Set;
import java.util.function.IntFunction;
import libcore.util.HexEncoding;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class DefaultPermissionGrantPolicy {
    public static final Set ACTIVITY_RECOGNITION_PERMISSIONS;
    public static final Set ALWAYS_LOCATION_PERMISSIONS;
    public static final Set CALENDAR_PERMISSIONS;
    public static final Set CAMERA_PERMISSIONS;
    public static final Set COARSE_BACKGROUND_LOCATION_PERMISSIONS;
    public static final Set CONTACTS_PERMISSIONS;
    public static final Set FINE_LOCATION_PERMISSIONS;
    public static final Set FOREGROUND_LOCATION_PERMISSIONS;
    public static final Set MEET_CONTACTS_PERMISSIONS;
    public static final Set MICROPHONE_PERMISSIONS;
    public static final Set NEARBY_DEVICES_PERMISSIONS;
    public static final Set NOTIFICATION_PERMISSIONS;
    public static final Set PHONE_PERMISSIONS;
    public static final Set SENSORS_PERMISSIONS;
    public static final Set SMS_PERMISSIONS;
    public static final Set STORAGE_PERMISSIONS;
    public final Context mContext;
    public LegacyPermissionManagerInternal.PackagesProvider mDialerAppPackagesProvider;
    public ArrayMap mGrantExceptions;
    public final Handler mHandler;
    public LegacyPermissionManagerInternal.PackagesProvider mLocationExtraPackagesProvider;
    public LegacyPermissionManagerInternal.PackagesProvider mLocationPackagesProvider;
    public final PackageManagerInternal mServiceInternal;
    public LegacyPermissionManagerInternal.PackagesProvider mSimCallManagerPackagesProvider;
    public LegacyPermissionManagerInternal.PackagesProvider mSmsAppPackagesProvider;
    public LegacyPermissionManagerInternal.SyncAdapterPackagesProvider mSyncAdapterPackagesProvider;
    public LegacyPermissionManagerInternal.PackagesProvider mUseOpenWifiAppPackagesProvider;
    public LegacyPermissionManagerInternal.PackagesProvider mVoiceInteractionPackagesProvider;
    public Map mNotiBlockableDataByUid = new HashMap();
    public final Object mLock = new Object();
    public final PackageManagerWrapper NO_PM_CACHE = new PackageManagerWrapper() { // from class: com.android.server.pm.permission.DefaultPermissionGrantPolicy.1
        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public int getPermissionFlags(String str, PackageInfo packageInfo, UserHandle userHandle) {
            return DefaultPermissionGrantPolicy.this.mContext.getPackageManager().getPermissionFlags(str, packageInfo.packageName, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void updatePermissionFlags(String str, PackageInfo packageInfo, int i, int i2, UserHandle userHandle) {
            DefaultPermissionGrantPolicy.this.mContext.getPackageManager().updatePermissionFlags(str, packageInfo.packageName, i, i2, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void grantPermission(String str, PackageInfo packageInfo, UserHandle userHandle) {
            DefaultPermissionGrantPolicy.this.mContext.getPackageManager().grantRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void revokePermission(String str, PackageInfo packageInfo, UserHandle userHandle) {
            DefaultPermissionGrantPolicy.this.mContext.getPackageManager().revokeRuntimePermission(packageInfo.packageName, str, userHandle);
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public boolean isGranted(String str, PackageInfo packageInfo, UserHandle userHandle) {
            return DefaultPermissionGrantPolicy.this.mContext.createContextAsUser(userHandle, 0).getPackageManager().checkPermission(str, packageInfo.packageName) == 0;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public PermissionInfo getPermissionInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return DefaultPermissionGrantPolicy.this.mContext.getPackageManager().getPermissionInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w("DefaultPermGrantPolicy", "Permission not found: " + str);
                return null;
            }
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public PackageInfo getPackageInfo(String str) {
            if (str == null) {
                return null;
            }
            try {
                return DefaultPermissionGrantPolicy.this.mContext.getPackageManager().getPackageInfo(str, 536916096);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("DefaultPermGrantPolicy", "Package not found: " + str);
                return null;
            }
        }
    };

    public final boolean isFixedOrUserSet(int i) {
        return (i & 23) != 0;
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
        MEET_CONTACTS_PERMISSIONS = new ArraySet();
        arraySet2.add("android.permission.READ_CONTACTS");
        arraySet2.add("android.permission.WRITE_CONTACTS");
        ArraySet arraySet3 = new ArraySet();
        ALWAYS_LOCATION_PERMISSIONS = arraySet3;
        arraySet3.add("android.permission.ACCESS_FINE_LOCATION");
        arraySet3.add("android.permission.ACCESS_COARSE_LOCATION");
        arraySet3.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        ArraySet arraySet4 = new ArraySet();
        FOREGROUND_LOCATION_PERMISSIONS = arraySet4;
        arraySet4.add("android.permission.ACCESS_FINE_LOCATION");
        arraySet4.add("android.permission.ACCESS_COARSE_LOCATION");
        ArraySet arraySet5 = new ArraySet();
        COARSE_BACKGROUND_LOCATION_PERMISSIONS = arraySet5;
        arraySet5.add("android.permission.ACCESS_COARSE_LOCATION");
        arraySet5.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        ArraySet arraySet6 = new ArraySet();
        FINE_LOCATION_PERMISSIONS = arraySet6;
        arraySet6.add("android.permission.ACCESS_FINE_LOCATION");
        ArraySet arraySet7 = new ArraySet();
        ACTIVITY_RECOGNITION_PERMISSIONS = arraySet7;
        arraySet7.add("android.permission.ACTIVITY_RECOGNITION");
        ArraySet arraySet8 = new ArraySet();
        CALENDAR_PERMISSIONS = arraySet8;
        arraySet8.add("android.permission.READ_CALENDAR");
        arraySet8.add("android.permission.WRITE_CALENDAR");
        ArraySet arraySet9 = new ArraySet();
        SMS_PERMISSIONS = arraySet9;
        arraySet9.add("android.permission.SEND_SMS");
        arraySet9.add("android.permission.RECEIVE_SMS");
        arraySet9.add("android.permission.READ_SMS");
        arraySet9.add("android.permission.RECEIVE_WAP_PUSH");
        arraySet9.add("android.permission.RECEIVE_MMS");
        arraySet9.add("android.permission.READ_CELL_BROADCASTS");
        ArraySet arraySet10 = new ArraySet();
        MICROPHONE_PERMISSIONS = arraySet10;
        arraySet10.add("android.permission.RECORD_AUDIO");
        ArraySet arraySet11 = new ArraySet();
        CAMERA_PERMISSIONS = arraySet11;
        arraySet11.add("android.permission.CAMERA");
        ArraySet arraySet12 = new ArraySet();
        SENSORS_PERMISSIONS = arraySet12;
        arraySet12.add("android.permission.BODY_SENSORS");
        arraySet12.add("android.permission.BODY_SENSORS_BACKGROUND");
        ArraySet arraySet13 = new ArraySet();
        STORAGE_PERMISSIONS = arraySet13;
        arraySet13.add("android.permission.READ_EXTERNAL_STORAGE");
        arraySet13.add("android.permission.WRITE_EXTERNAL_STORAGE");
        arraySet13.add("android.permission.ACCESS_MEDIA_LOCATION");
        arraySet13.add("android.permission.READ_MEDIA_AUDIO");
        arraySet13.add("android.permission.READ_MEDIA_VIDEO");
        arraySet13.add("android.permission.READ_MEDIA_IMAGES");
        arraySet13.add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        ArraySet arraySet14 = new ArraySet();
        NEARBY_DEVICES_PERMISSIONS = arraySet14;
        arraySet14.add("android.permission.BLUETOOTH_ADVERTISE");
        arraySet14.add("android.permission.BLUETOOTH_CONNECT");
        arraySet14.add("android.permission.BLUETOOTH_SCAN");
        arraySet14.add("android.permission.UWB_RANGING");
        arraySet14.add("android.permission.NEARBY_WIFI_DEVICES");
        ArraySet arraySet15 = new ArraySet();
        NOTIFICATION_PERMISSIONS = arraySet15;
        arraySet15.add("android.permission.POST_NOTIFICATIONS");
    }

    public DefaultPermissionGrantPolicy(Context context) {
        this.mContext = context;
        ServiceThread serviceThread = new ServiceThread("DefaultPermGrantPolicy", 10, true);
        serviceThread.start();
        this.mHandler = new Handler(serviceThread.getLooper()) { // from class: com.android.server.pm.permission.DefaultPermissionGrantPolicy.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    synchronized (DefaultPermissionGrantPolicy.this.mLock) {
                        if (DefaultPermissionGrantPolicy.this.mGrantExceptions == null) {
                            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy = DefaultPermissionGrantPolicy.this;
                            defaultPermissionGrantPolicy.mGrantExceptions = defaultPermissionGrantPolicy.readDefaultPermissionExceptionsLocked(defaultPermissionGrantPolicy.NO_PM_CACHE);
                        }
                    }
                }
            }
        };
        this.mServiceInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    }

    public void setLocationPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mLocationPackagesProvider = packagesProvider;
        }
    }

    public void setLocationExtraPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mLocationExtraPackagesProvider = packagesProvider;
        }
    }

    public void setVoiceInteractionPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mVoiceInteractionPackagesProvider = packagesProvider;
        }
    }

    public void setSmsAppPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mSmsAppPackagesProvider = packagesProvider;
        }
    }

    public void setDialerAppPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mDialerAppPackagesProvider = packagesProvider;
        }
    }

    public void setSimCallManagerPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mSimCallManagerPackagesProvider = packagesProvider;
        }
    }

    public void setUseOpenWifiAppPackagesProvider(LegacyPermissionManagerInternal.PackagesProvider packagesProvider) {
        synchronized (this.mLock) {
            this.mUseOpenWifiAppPackagesProvider = packagesProvider;
        }
    }

    public void setSyncAdapterPackagesProvider(LegacyPermissionManagerInternal.SyncAdapterPackagesProvider syncAdapterPackagesProvider) {
        synchronized (this.mLock) {
            this.mSyncAdapterPackagesProvider = syncAdapterPackagesProvider;
        }
    }

    public void grantDefaultPermissions(int i) {
        DelayingPackageManagerCache delayingPackageManagerCache = new DelayingPackageManagerCache();
        grantPermissionsToSysComponentsAndPrivApps(delayingPackageManagerCache, i);
        if (PMRune.PM_NAL_GET_APP_LIST) {
            grantGetAppListToSystemApps(delayingPackageManagerCache, i);
        }
        grantDefaultSystemHandlerPermissions(delayingPackageManagerCache, i);
        grantSignatureAppsNotificationPermissions(delayingPackageManagerCache, i);
        grantDefaultPermissionExceptions(delayingPackageManagerCache, i);
        delayingPackageManagerCache.apply();
    }

    public final void grantGetAppListToSystemApps(DelayingPackageManagerCache delayingPackageManagerCache, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting GET_APP_LIST to system components for user " + i);
        for (PackageInfo packageInfo : this.mContext.getPackageManager().getInstalledPackagesAsUser(536916096, 0)) {
            if (packageInfo != null && packageInfo.applicationInfo.isSystemApp()) {
                ArraySet arraySet = new ArraySet();
                arraySet.add("com.samsung.android.permission.GET_APP_LIST");
                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, arraySet, true, i);
            }
        }
    }

    public final void grantSignatureAppsNotificationPermissions(PackageManagerWrapper packageManagerWrapper, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting Notification permissions to platform signature apps for user " + i);
        for (PackageInfo packageInfo : this.mContext.getPackageManager().getInstalledPackagesAsUser(536916096, 0)) {
            if (packageInfo != null && packageInfo.applicationInfo.isSystemApp() && packageInfo.applicationInfo.isSignedWithPlatformKey()) {
                grantRuntimePermissionsForSystemPackage(packageManagerWrapper, i, packageInfo, NOTIFICATION_PERMISSIONS);
            }
        }
    }

    public final void grantRuntimePermissionsForSystemPackage(PackageManagerWrapper packageManagerWrapper, int i, PackageInfo packageInfo) {
        grantRuntimePermissionsForSystemPackage(packageManagerWrapper, i, packageInfo, null);
    }

    public final void grantRuntimePermissionsForSystemPackage(PackageManagerWrapper packageManagerWrapper, int i, PackageInfo packageInfo, Set set) {
        if (ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
            return;
        }
        ArraySet arraySet = new ArraySet();
        for (String str : packageInfo.requestedPermissions) {
            PermissionInfo permissionInfo = packageManagerWrapper.getPermissionInfo(str);
            if (permissionInfo != null && ((set == null || set.contains(str)) && permissionInfo.isRuntime())) {
                arraySet.add(str);
            }
        }
        if (arraySet.isEmpty()) {
            return;
        }
        grantRuntimePermissions(packageManagerWrapper, packageInfo, arraySet, true, i);
    }

    public void scheduleReadDefaultPermissionExceptions() {
        this.mHandler.sendEmptyMessage(1);
    }

    public final void grantPermissionsToSysComponentsAndPrivApps(DelayingPackageManagerCache delayingPackageManagerCache, int i) {
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy;
        Iterator it;
        List list;
        DelayingPackageManagerCache delayingPackageManagerCache2;
        int i2;
        String str;
        String str2;
        String str3;
        List list2;
        String str4;
        String str5;
        String str6;
        int i3;
        int i4;
        Iterator it2;
        String str7;
        String str8;
        int i5;
        int i6;
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy2 = this;
        DelayingPackageManagerCache delayingPackageManagerCache3 = delayingPackageManagerCache;
        int i7 = i;
        Log.i("DefaultPermGrantPolicy", "Granting permissions to platform components for user " + i7);
        int i8 = 0;
        List<PackageInfo> installedPackagesAsUser = defaultPermissionGrantPolicy2.mContext.getPackageManager().getInstalledPackagesAsUser(536916096, 0);
        Iterator it3 = installedPackagesAsUser.iterator();
        while (it3.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it3.next();
            if (packageInfo != null) {
                delayingPackageManagerCache3.addPackageInfo(packageInfo.packageName, packageInfo);
                if (!delayingPackageManagerCache3.isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo) || !doesPackageSupportRuntimePermissions(packageInfo)) {
                    defaultPermissionGrantPolicy = defaultPermissionGrantPolicy2;
                } else if (!ArrayUtils.isEmpty(packageInfo.requestedPermissions)) {
                    if ("com.samsung.android.spayfw".equals(packageInfo.packageName)) {
                        String[] strArr = packageInfo.requestedPermissions;
                        int i9 = i8;
                        for (int length = strArr.length; i9 < length; length = i6) {
                            String str9 = strArr[i9];
                            if ("android.permission.ACCESS_COARSE_LOCATION".equals(str9)) {
                                str8 = str9;
                                i5 = i9;
                                i6 = length;
                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_COARSE_LOCATION"), false, i);
                            } else {
                                str8 = str9;
                                i5 = i9;
                                i6 = length;
                            }
                            if ("android.permission.ACCESS_FINE_LOCATION".equals(str8)) {
                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_FINE_LOCATION"), false, i);
                            }
                            i9 = i5 + 1;
                        }
                        defaultPermissionGrantPolicy = defaultPermissionGrantPolicy2;
                        delayingPackageManagerCache2 = delayingPackageManagerCache3;
                        i2 = i7;
                        list = installedPackagesAsUser;
                        it = it3;
                    } else {
                        if ("com.samsung.android.networkdiagnostic".equals(packageInfo.packageName)) {
                            String[] strArr2 = packageInfo.requestedPermissions;
                            int length2 = strArr2.length;
                            int i10 = 0;
                            while (i10 < length2) {
                                String str10 = strArr2[i10];
                                if ("android.permission.ACCESS_COARSE_LOCATION".equals(str10)) {
                                    it2 = it3;
                                    str7 = str10;
                                    i3 = i10;
                                    i4 = length2;
                                    grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_COARSE_LOCATION"), false, i);
                                } else {
                                    i3 = i10;
                                    i4 = length2;
                                    it2 = it3;
                                    str7 = str10;
                                }
                                if ("android.permission.ACCESS_FINE_LOCATION".equals(str7)) {
                                    grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_FINE_LOCATION"), false, i);
                                }
                                if ("android.permission.ACCESS_BACKGROUND_LOCATION".equals(str7)) {
                                    grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_BACKGROUND_LOCATION"), false, i);
                                }
                                i10 = i3 + 1;
                                length2 = i4;
                                it3 = it2;
                            }
                            it = it3;
                        } else {
                            it = it3;
                            if ("com.samsung.android.scloud".equals(packageInfo.packageName)) {
                                if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 31) {
                                    defaultPermissionGrantPolicy2.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache3, i7, packageInfo);
                                    PackageInfo systemPackageInfo = delayingPackageManagerCache3.getSystemPackageInfo("com.samsung.android.scloud");
                                    if (systemPackageInfo != null && doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                                        for (String str11 : systemPackageInfo.requestedPermissions) {
                                            PermissionInfo permissionInfo = delayingPackageManagerCache3.getPermissionInfo(str11);
                                            if (permissionInfo != null && permissionInfo.isRuntime()) {
                                                delayingPackageManagerCache.updatePermissionFlags(str11, systemPackageInfo, 16, 0, UserHandle.of(i));
                                            }
                                        }
                                    }
                                } else {
                                    defaultPermissionGrantPolicy2.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache3, i7, packageInfo);
                                }
                            } else {
                                String str12 = "android.permission.READ_MEDIA_VIDEO";
                                String str13 = "android.permission.READ_MEDIA_IMAGES";
                                String str14 = "android.permission.WRITE_EXTERNAL_STORAGE";
                                String str15 = "android.permission.READ_EXTERNAL_STORAGE";
                                if ("com.samsung.cmh".equals(packageInfo.packageName)) {
                                    String[] strArr3 = packageInfo.requestedPermissions;
                                    int length3 = strArr3.length;
                                    int i11 = 0;
                                    while (i11 < length3) {
                                        String str16 = strArr3[i11];
                                        if ("android.permission.READ_CONTACTS".equals(str16)) {
                                            str = str16;
                                            list2 = installedPackagesAsUser;
                                            str4 = str14;
                                            str2 = str13;
                                            str3 = str12;
                                            grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_CONTACTS"), false, i);
                                            str5 = str15;
                                        } else {
                                            str = str16;
                                            str2 = str13;
                                            str3 = str12;
                                            list2 = installedPackagesAsUser;
                                            str4 = str14;
                                            str5 = str15;
                                        }
                                        if (str5.equals(str)) {
                                            str6 = str5;
                                            grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str5), false, i);
                                        } else {
                                            str6 = str5;
                                        }
                                        if (str4.equals(str)) {
                                            grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str4), false, i);
                                        }
                                        if (str2.equals(str)) {
                                            grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str2), false, i);
                                        }
                                        if (str3.equals(str)) {
                                            grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton(str3), false, i);
                                        }
                                        if ("android.permission.ACCESS_MEDIA_LOCATION".equals(str)) {
                                            grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_MEDIA_LOCATION"), false, i);
                                        }
                                        i11++;
                                        str13 = str2;
                                        str12 = str3;
                                        str15 = str6;
                                        str14 = str4;
                                        installedPackagesAsUser = list2;
                                    }
                                    list = installedPackagesAsUser;
                                } else {
                                    list = installedPackagesAsUser;
                                    if ("com.samsung.faceservice".equals(packageInfo.packageName)) {
                                        for (String str17 : packageInfo.requestedPermissions) {
                                            if ("android.permission.READ_EXTERNAL_STORAGE".equals(str17)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_EXTERNAL_STORAGE"), false, i);
                                            }
                                            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str17)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.WRITE_EXTERNAL_STORAGE"), false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_IMAGES".equals(str17)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_VIDEO".equals(str17)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, i);
                                            }
                                        }
                                    } else if ("com.samsung.storyservice".equals(packageInfo.packageName)) {
                                        for (String str18 : packageInfo.requestedPermissions) {
                                            if ("android.permission.READ_EXTERNAL_STORAGE".equals(str18)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_EXTERNAL_STORAGE"), false, i);
                                            }
                                            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str18)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.WRITE_EXTERNAL_STORAGE"), false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_IMAGES".equals(str18)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_VIDEO".equals(str18)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, i);
                                            }
                                            if ("android.permission.ACCESS_MEDIA_LOCATION".equals(str18)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_MEDIA_LOCATION"), false, i);
                                            }
                                        }
                                    } else if ("com.samsung.ipservice".equals(packageInfo.packageName)) {
                                        for (String str19 : packageInfo.requestedPermissions) {
                                            if ("android.permission.READ_EXTERNAL_STORAGE".equals(str19)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_EXTERNAL_STORAGE"), false, i);
                                            }
                                            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str19)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.WRITE_EXTERNAL_STORAGE"), false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_IMAGES".equals(str19)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_IMAGES"), false, i);
                                            }
                                            if ("android.permission.READ_MEDIA_VIDEO".equals(str19)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.READ_MEDIA_VIDEO"), false, i);
                                            }
                                        }
                                    } else if ("com.samsung.android.ipsgeofence".equals(packageInfo.packageName)) {
                                        for (String str20 : packageInfo.requestedPermissions) {
                                            if ("android.permission.ACCESS_COARSE_LOCATION".equals(str20)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_COARSE_LOCATION"), false, i);
                                            }
                                            if ("android.permission.ACCESS_FINE_LOCATION".equals(str20)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_FINE_LOCATION"), false, i);
                                            }
                                            if ("android.permission.ACCESS_BACKGROUND_LOCATION".equals(str20)) {
                                                grantRuntimePermissions(delayingPackageManagerCache, packageInfo, Collections.singleton("android.permission.ACCESS_BACKGROUND_LOCATION"), false, i);
                                            }
                                        }
                                    } else {
                                        defaultPermissionGrantPolicy = this;
                                        delayingPackageManagerCache2 = delayingPackageManagerCache;
                                        i2 = i;
                                        defaultPermissionGrantPolicy.grantRuntimePermissionsForSystemPackage(delayingPackageManagerCache2, i2, packageInfo);
                                    }
                                }
                                defaultPermissionGrantPolicy = this;
                                delayingPackageManagerCache2 = delayingPackageManagerCache;
                                i2 = i;
                            }
                        }
                        defaultPermissionGrantPolicy = defaultPermissionGrantPolicy2;
                        delayingPackageManagerCache2 = delayingPackageManagerCache3;
                        i2 = i7;
                        list = installedPackagesAsUser;
                    }
                    i7 = i2;
                    it3 = it;
                    installedPackagesAsUser = list;
                    i8 = 0;
                    delayingPackageManagerCache3 = delayingPackageManagerCache2;
                }
                defaultPermissionGrantPolicy2 = defaultPermissionGrantPolicy;
            }
        }
        DelayingPackageManagerCache delayingPackageManagerCache4 = delayingPackageManagerCache3;
        for (PackageInfo packageInfo2 : installedPackagesAsUser) {
            if (packageInfo2 != null && doesPackageSupportRuntimePermissions(packageInfo2) && !ArrayUtils.isEmpty(packageInfo2.requestedPermissions) && delayingPackageManagerCache4.isGranted("android.permission.READ_PRIVILEGED_PHONE_STATE", packageInfo2, UserHandle.of(i)) && delayingPackageManagerCache4.isGranted("android.permission.READ_PHONE_STATE", packageInfo2, UserHandle.of(i)) && !delayingPackageManagerCache4.isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo2)) {
                delayingPackageManagerCache.updatePermissionFlags("android.permission.READ_PHONE_STATE", packageInfo2, 16, 0, UserHandle.of(i));
            }
        }
    }

    public final void grantIgnoringSystemPackage(PackageManagerWrapper packageManagerWrapper, String str, int i, Set... setArr) {
        grantPermissionsToPackage(packageManagerWrapper, str, i, true, true, setArr);
    }

    public final void grantSystemFixedPermissionsToSystemPackage(PackageManagerWrapper packageManagerWrapper, String str, int i, Set... setArr) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, true, setArr);
    }

    public final void grantPermissionsToSystemPackage(PackageManagerWrapper packageManagerWrapper, String str, int i, Set... setArr) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, false, setArr);
    }

    public final void grantPermissionsToSystemPackage(PackageManagerWrapper packageManagerWrapper, String str, int i, boolean z, Set... setArr) {
        if (packageManagerWrapper.isSystemPackage(str)) {
            grantPermissionsToPackage(packageManagerWrapper, packageManagerWrapper.getSystemPackageInfo(str), i, z, false, true, setArr);
        }
    }

    public final void grantPermissionsToPackage(PackageManagerWrapper packageManagerWrapper, String str, int i, boolean z, boolean z2, Set... setArr) {
        grantPermissionsToPackage(packageManagerWrapper, packageManagerWrapper.getPackageInfo(str), i, false, z, z2, setArr);
    }

    public final void grantPermissionsToPackage(PackageManagerWrapper packageManagerWrapper, PackageInfo packageInfo, int i, boolean z, boolean z2, boolean z3, Set... setArr) {
        if (packageInfo != null && doesPackageSupportRuntimePermissions(packageInfo)) {
            for (Set set : setArr) {
                grantRuntimePermissions(packageManagerWrapper, packageInfo, set, z, z2, z3, i);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x04bc  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x056e A[LOOP:5: B:105:0x056c->B:106:0x056e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0697  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x06a4  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x06bc  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x06e6  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0715  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x069a  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0810  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0820  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0442  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void grantDefaultSystemHandlerPermissions(PackageManagerWrapper packageManagerWrapper, int i) {
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider;
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider2;
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider3;
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider4;
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider5;
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider6;
        LegacyPermissionManagerInternal.PackagesProvider packagesProvider7;
        LegacyPermissionManagerInternal.SyncAdapterPackagesProvider syncAdapterPackagesProvider;
        Set set;
        Set set2;
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        String[] strArr4;
        String[] strArr5;
        boolean z;
        String str;
        Set set3;
        PackageInfo packageInfo;
        PackageInfo packageInfo2;
        PackageInfo packageInfo3;
        PackageInfo systemPackageInfo;
        PackageInfo systemPackageInfo2;
        PackageInfo packageInfo4;
        PackageInfo packageInfo5;
        PackageInfo packageInfo6;
        PackageInfo systemPackageInfo3;
        PackageManager packageManager;
        PackageManager packageManager2;
        PackageManager packageManager3;
        boolean z2;
        boolean z3;
        PackageManager packageManager4;
        PackageManager packageManager5;
        PackageInfo packageInfo7;
        ArraySet arraySet;
        PackageManager packageManager6;
        PackageManager packageManager7;
        Log.i("DefaultPermGrantPolicy", "Granting permissions to default platform handlers for user " + i);
        synchronized (this.mLock) {
            packagesProvider = this.mLocationPackagesProvider;
            packagesProvider2 = this.mLocationExtraPackagesProvider;
            packagesProvider3 = this.mVoiceInteractionPackagesProvider;
            packagesProvider4 = this.mSmsAppPackagesProvider;
            packagesProvider5 = this.mDialerAppPackagesProvider;
            packagesProvider6 = this.mSimCallManagerPackagesProvider;
            packagesProvider7 = this.mUseOpenWifiAppPackagesProvider;
            syncAdapterPackagesProvider = this.mSyncAdapterPackagesProvider;
        }
        String[] packages = packagesProvider3 != null ? packagesProvider3.getPackages(i) : null;
        String[] packages2 = packagesProvider != null ? packagesProvider.getPackages(i) : null;
        String[] packages3 = packagesProvider2 != null ? packagesProvider2.getPackages(i) : null;
        String[] packages4 = packagesProvider4 != null ? packagesProvider4.getPackages(i) : null;
        String[] packages5 = packagesProvider5 != null ? packagesProvider5.getPackages(i) : null;
        String[] packages6 = packagesProvider6 != null ? packagesProvider6.getPackages(i) : null;
        String[] packages7 = packagesProvider7 != null ? packagesProvider7.getPackages(i) : null;
        String[] packages8 = syncAdapterPackagesProvider != null ? syncAdapterPackagesProvider.getPackages("com.android.contacts", i) : null;
        String[] packages9 = syncAdapterPackagesProvider != null ? syncAdapterPackagesProvider.getPackages("com.android.calendar", i) : null;
        String permissionControllerPackageName = this.mContext.getPackageManager().getPermissionControllerPackageName();
        Set set4 = NOTIFICATION_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, permissionControllerPackageName, i, set4);
        String str2 = (String) ArrayUtils.firstOrNull(getKnownPackages(2, i));
        Set set5 = STORAGE_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str2, i, set5, set4);
        String str3 = (String) ArrayUtils.firstOrNull(getKnownPackages(4, i));
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str3, i, set5);
        Set set6 = PHONE_PERMISSIONS;
        String[] strArr6 = packages8;
        grantPermissionsToSystemPackage(packageManagerWrapper, str3, i, set6, SMS_PERMISSIONS, set4);
        String str4 = (String) ArrayUtils.firstOrNull(getKnownPackages(1, i));
        Set set7 = CONTACTS_PERMISSIONS;
        String[] strArr7 = packages4;
        Set set8 = ALWAYS_LOCATION_PERMISSIONS;
        String[] strArr8 = packages5;
        Set set9 = CAMERA_PERMISSIONS;
        String[] strArr9 = packages3;
        grantPermissionsToSystemPackage(packageManagerWrapper, str4, i, set6, set7, set8, set9, NEARBY_DEVICES_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str4, i, set4);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSearchSelectorPackage(), i, set4);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultCaptivePortalLoginPackage(), i, set4);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultDockManagerPackage(), i, set4);
        String defaultSystemHandlerActivityPackage = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.media.action.IMAGE_CAPTURE", i);
        Set set10 = MICROPHONE_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage, i, set9, set10, set5);
        PackageInfo packageInfo8 = packageManagerWrapper.getPackageInfo("com.samsung.android.fast");
        if (packageInfo8 == null || !doesPackageSupportRuntimePermissions(packageInfo8) || "KOREA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)) || (packageManager7 = this.mContext.getPackageManager()) == null || packageManager7.checkSignatures("com.samsung.android.fast", "android") != 0) {
            set = set5;
            set2 = set4;
            strArr = packages;
            strArr2 = packages2;
            strArr3 = strArr7;
            strArr4 = strArr8;
            strArr5 = strArr6;
            z = true;
        } else {
            ArraySet arraySet2 = new ArraySet();
            arraySet2.add("android.permission.ACCESS_FINE_LOCATION");
            arraySet2.add("android.permission.ACCESS_COARSE_LOCATION");
            set = set5;
            set2 = set4;
            strArr2 = packages2;
            z = true;
            strArr = packages;
            strArr3 = strArr7;
            strArr5 = strArr6;
            strArr4 = strArr8;
            grantRuntimePermissions(packageManagerWrapper, packageInfo8, arraySet2, false, i);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.provider.MediaStore.RECORD_SOUND", i), i, set10);
        Set set11 = set2;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultProviderAuthorityPackage("media", i), i, set, set11);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultProviderAuthorityPackage("downloads", i), i, set, set11);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.VIEW_DOWNLOADS", i), i, set);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultProviderAuthorityPackage("com.android.externalstorage.documents", i), i, set);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.credentials.INSTALL", i), i, set);
        if (strArr4 == null) {
            grantDefaultPermissionsToDefaultSystemDialerApp(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.DIAL", i), i);
        } else {
            for (String str5 : strArr4) {
                grantDefaultPermissionsToDefaultSystemDialerApp(packageManagerWrapper, str5, i);
            }
        }
        if (packages6 != null) {
            for (String str6 : packages6) {
                grantDefaultPermissionsToDefaultSystemSimCallManager(packageManagerWrapper, str6, i);
            }
        }
        if (packages7 != null) {
            for (String str7 : packages7) {
                grantDefaultPermissionsToDefaultSystemUseOpenWifiApp(packageManagerWrapper, str7, i);
            }
        }
        if (strArr3 == null) {
            grantDefaultPermissionsToDefaultSystemSmsApp(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_MESSAGING", i), i);
        } else {
            for (String str8 : strArr3) {
                grantDefaultPermissionsToDefaultSystemSmsApp(packageManagerWrapper, str8, i);
            }
        }
        String defaultSystemHandlerActivityPackage2 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.provider.Telephony.SMS_CB_RECEIVED", i);
        Set set12 = SMS_PERMISSIONS;
        Set set13 = NEARBY_DEVICES_PERMISSIONS;
        Set set14 = NOTIFICATION_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage2, i, set12, set13, set14);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerServicePackage(packageManagerWrapper, "android.provider.Telephony.SMS_CARRIER_PROVISION", i), i, set12);
        String defaultSystemHandlerActivityPackageForCategory = getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_CALENDAR", i);
        Set set15 = CALENDAR_PERMISSIONS;
        Set set16 = CONTACTS_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackageForCategory, i, set15, set16, set14);
        String defaultProviderAuthorityPackage = getDefaultProviderAuthorityPackage("com.android.calendar", i);
        Set set17 = STORAGE_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage, i, set16, set17);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage, i, set15);
        if (packages9 != null) {
            grantPermissionToEachSystemPackage(packageManagerWrapper, getHeadlessSyncAdapterPackages(packageManagerWrapper, packages9, i), i, set15);
        }
        String defaultSystemHandlerActivityPackageForCategory2 = getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_CONTACTS", i);
        Set set18 = PHONE_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackageForCategory2, i, set16, set18);
        if (strArr5 != null) {
            grantPermissionToEachSystemPackage(packageManagerWrapper, getHeadlessSyncAdapterPackages(packageManagerWrapper, strArr5, i), i, set16);
        }
        String defaultProviderAuthorityPackage2 = getDefaultProviderAuthorityPackage("com.android.contacts", i);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage2, i, set16, set18);
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultProviderAuthorityPackage2, i, set17);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.app.action.PROVISION_MANAGED_DEVICE", i), i, set16, set14);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0)) {
            grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_MAPS", i), i, FOREGROUND_LOCATION_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_EMAIL", i), i, set16, set15);
        String str9 = (String) ArrayUtils.firstOrNull(getKnownPackages(5, i));
        if (str9 == null) {
            str9 = getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.APP_BROWSER", i);
            if (!packageManagerWrapper.isSystemPackage(str9)) {
                str = null;
                grantPermissionsToPackage(packageManagerWrapper, str, i, false, true, FOREGROUND_LOCATION_PERMISSIONS);
                if (strArr != null) {
                    for (String str10 : strArr) {
                        grantPermissionsToSystemPackage(packageManagerWrapper, str10, i, CONTACTS_PERMISSIONS, CALENDAR_PERMISSIONS, MICROPHONE_PERMISSIONS, PHONE_PERMISSIONS, SMS_PERMISSIONS, COARSE_BACKGROUND_LOCATION_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS, NOTIFICATION_PERMISSIONS);
                        revokeRuntimePermissions(packageManagerWrapper, str10, FINE_LOCATION_PERMISSIONS, false, i);
                    }
                }
                if (ActivityManager.isLowRamDeviceStatic()) {
                    grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.search.action.GLOBAL_SEARCH", i), i, MICROPHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, NOTIFICATION_PERMISSIONS);
                }
                String defaultSystemHandlerServicePackage = getDefaultSystemHandlerServicePackage(packageManagerWrapper, new Intent("android.speech.RecognitionService").addCategory("android.intent.category.DEFAULT"), i);
                Set set19 = MICROPHONE_PERMISSIONS;
                grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerServicePackage, i, set19);
                if ("CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)) && (packageInfo7 = packageManagerWrapper.getPackageInfo("com.sec.android.easyMover.Agent")) != null && doesPackageSupportRuntimePermissions(packageInfo7)) {
                    arraySet = new ArraySet();
                    arraySet.add("android.permission.BLUETOOTH_SCAN");
                    arraySet.add("android.permission.BLUETOOTH_CONNECT");
                    arraySet.add("android.permission.BLUETOOTH_ADVERTISE");
                    packageManager6 = this.mContext.getPackageManager();
                    if (packageManager6 != null && packageManager6.checkSignatures("com.sec.android.easyMover.Agent", "android") == 0) {
                        grantRuntimePermissions(packageManagerWrapper, packageInfo7, arraySet, false, i);
                    }
                }
                if ("CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                    grantPermissionsToSystemPackage(packageManagerWrapper, "com.google.android.gms", i, CONTACTS_PERMISSIONS, CALENDAR_PERMISSIONS, set19, PHONE_PERMISSIONS, SMS_PERMISSIONS, CAMERA_PERMISSIONS, SENSORS_PERMISSIONS, STORAGE_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS);
                    grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.google.android.gms", i, ALWAYS_LOCATION_PERMISSIONS, ACTIVITY_RECOGNITION_PERMISSIONS);
                }
                grantPermissionsToSystemPackage(packageManagerWrapper, "com.google.android.apps.tachyon", i, set19, MEET_CONTACTS_PERMISSIONS, NOTIFICATION_PERMISSIONS, PHONE_PERMISSIONS);
                if (strArr2 != null) {
                    for (String str11 : strArr2) {
                        grantPermissionsToSystemPackage(packageManagerWrapper, str11, i, CONTACTS_PERMISSIONS, CALENDAR_PERMISSIONS, MICROPHONE_PERMISSIONS, PHONE_PERMISSIONS, SMS_PERMISSIONS, CAMERA_PERMISSIONS, SENSORS_PERMISSIONS, STORAGE_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS, NOTIFICATION_PERMISSIONS);
                        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str11, i, ALWAYS_LOCATION_PERMISSIONS, ACTIVITY_RECOGNITION_PERMISSIONS);
                    }
                }
                if (strArr9 != null) {
                    for (String str12 : strArr9) {
                        grantPermissionsToSystemPackage(packageManagerWrapper, str12, i, ALWAYS_LOCATION_PERMISSIONS, NEARBY_DEVICES_PERMISSIONS);
                        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str12, i, ACTIVITY_RECOGNITION_PERMISSIONS);
                    }
                }
                String defaultSystemHandlerActivityPackage3 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.VIEW").addCategory("android.intent.category.DEFAULT").setDataAndType(Uri.fromFile(new File("foo.mp3")), "audio/mpeg"), i);
                Set set20 = STORAGE_PERMISSIONS;
                grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage3, i, set20);
                String defaultSystemHandlerActivityPackage4 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").addCategory("android.intent.category.LAUNCHER_APP"), i);
                Set set21 = ALWAYS_LOCATION_PERMISSIONS;
                Set set22 = NOTIFICATION_PERMISSIONS;
                grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage4, i, set21, set22);
                if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
                    String defaultSystemHandlerActivityPackageForCategory3 = getDefaultSystemHandlerActivityPackageForCategory(packageManagerWrapper, "android.intent.category.HOME_MAIN", i);
                    grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackageForCategory3, i, CONTACTS_PERMISSIONS, MICROPHONE_PERMISSIONS, set21);
                    grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackageForCategory3, i, PHONE_PERMISSIONS, ACTIVITY_RECOGNITION_PERMISSIONS);
                    if (this.mContext.getResources().getBoolean(R.bool.config_startDreamImmediatelyOnDock)) {
                        Log.d("DefaultPermGrantPolicy", "Wear: Skipping permission grant for Default fitness tracker app : " + defaultSystemHandlerActivityPackageForCategory3);
                    } else {
                        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "com.android.fitness.TRACK", i), i, SENSORS_PERMISSIONS);
                    }
                }
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.printspooler", i, set21, set22);
                String defaultSystemHandlerActivityPackage5 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.telephony.action.EMERGENCY_ASSISTANCE", i);
                Set set23 = CONTACTS_PERMISSIONS;
                Set set24 = PHONE_PERMISSIONS;
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage5, i, set23, set24);
                grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.VIEW").setType("vnd.android.cursor.item/ndef_msg"), i), i, set23, set24);
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.os.storage.action.MANAGE_STORAGE", i), i, set20);
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.companiondevicemanager", i, set21, NEARBY_DEVICES_PERMISSIONS);
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.RINGTONE_PICKER", i), i, set20);
                for (String str13 : getKnownPackages(6, i)) {
                    grantPermissionsToSystemPackage(packageManagerWrapper, str13, i, COARSE_BACKGROUND_LOCATION_PERMISSIONS, CONTACTS_PERMISSIONS);
                }
                set3 = STORAGE_PERMISSIONS;
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.sharedstoragebackup", i, set3);
                grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.bluetoothmidiservice", i, NEARBY_DEVICES_PERMISSIONS);
                packageInfo = packageManagerWrapper.getPackageInfo("com.samsung.android.app.reminder");
                if (packageInfo != null && doesPackageSupportRuntimePermissions(packageInfo) && (packageManager5 = this.mContext.getPackageManager()) != null && packageManager5.checkSignatures("com.samsung.android.app.reminder", "android") == 0) {
                    grantRuntimePermissions(packageManagerWrapper, packageInfo, NOTIFICATION_PERMISSIONS, false, i);
                }
                packageInfo2 = packageManagerWrapper.getPackageInfo("com.samsung.android.calendar");
                if (packageInfo2 != null && doesPackageSupportRuntimePermissions(packageInfo2) && (packageManager4 = this.mContext.getPackageManager()) != null && packageManager4.checkSignatures("com.samsung.android.calendar", "android") == 0) {
                    ArraySet arraySet3 = new ArraySet();
                    if (!"CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                        arraySet3.add("android.permission.READ_CALENDAR");
                        arraySet3.add("android.permission.WRITE_CALENDAR");
                    }
                    arraySet3.add("android.permission.POST_NOTIFICATIONS");
                    grantRuntimePermissions(packageManagerWrapper, packageInfo2, arraySet3, false, i);
                }
                packageInfo3 = packageManagerWrapper.getPackageInfo("com.sec.android.app.clockpackage");
                if (packageInfo3 != null && doesPackageSupportRuntimePermissions(packageInfo3)) {
                    grantRuntimePermissions(packageManagerWrapper, packageInfo3, NOTIFICATION_PERMISSIONS, false, i);
                }
                systemPackageInfo = packageManagerWrapper.getSystemPackageInfo("com.samsung.android.dck.timesync");
                if (systemPackageInfo != null && doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                    ArraySet arraySet4 = new ArraySet();
                    packageManagerWrapper.updatePermissionFlags("android.permission.BLUETOOTH_CONNECT", systemPackageInfo, 16, 16, UserHandle.of(i));
                    ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).setUidMode(111, 1000, 0);
                    arraySet4.add("android.permission.BLUETOOTH_CONNECT");
                    grantRuntimePermissions(packageManagerWrapper, systemPackageInfo, arraySet4, true, i);
                }
                systemPackageInfo2 = packageManagerWrapper.getSystemPackageInfo("com.samsung.android.bixby.agent");
                if (systemPackageInfo2 != null && doesPackageSupportRuntimePermissions(systemPackageInfo2)) {
                    String str14 = SemSystemProperties.get("ro.build.characteristics");
                    z2 = (str14 == null && str14.contains("tablet")) ? z : false;
                    z3 = packageManagerWrapper.getSystemPackageInfo("com.samsung.android.dialer") == null ? z : false;
                    if (packageManagerWrapper.getSystemPackageInfo("com.samsung.android.messaging") == null) {
                        z = false;
                    }
                    ArraySet arraySet5 = new ArraySet();
                    arraySet5.add("android.permission.RECORD_AUDIO");
                    arraySet5.add("android.permission.READ_CALENDAR");
                    arraySet5.add("android.permission.WRITE_CALENDAR");
                    if (z3) {
                        arraySet5.add("android.permission.READ_CALL_LOG");
                        arraySet5.add("android.permission.WRITE_CALL_LOG");
                    }
                    arraySet5.add("android.permission.CAMERA");
                    arraySet5.add("android.permission.READ_CONTACTS");
                    arraySet5.add("android.permission.WRITE_CONTACTS");
                    arraySet5.add("android.permission.ACCESS_FINE_LOCATION");
                    arraySet5.add("android.permission.ACCESS_COARSE_LOCATION");
                    arraySet5.add("android.permission.ACCESS_BACKGROUND_LOCATION");
                    if (z) {
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
                    if (!z2) {
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
                    grantRuntimePermissions(packageManagerWrapper, systemPackageInfo2, arraySet5, false, i);
                    Settings.Global.putString(this.mContext.getContentResolver(), "bixby_pregranted_permissions", String.join(KnoxVpnFirewallHelper.DELIMITER, arraySet5));
                }
                packageInfo4 = packageManagerWrapper.getPackageInfo("com.sec.android.mimage.photoretouching");
                if (packageInfo4 != null && doesPackageSupportRuntimePermissions(packageInfo4) && (packageManager3 = this.mContext.getPackageManager()) != null && packageManager3.checkSignatures("com.sec.android.mimage.photoretouching", "android") == 0) {
                    grantRuntimePermissions(packageManagerWrapper, packageInfo4, set3, false, i);
                }
                grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerServicePackage(packageManagerWrapper, "android.adservices.AD_SERVICES_COMMON_SERVICE", i), i, NOTIFICATION_PERMISSIONS);
                packageInfo5 = packageManagerWrapper.getPackageInfo("com.samsung.android.video");
                if (packageInfo5 != null && doesPackageSupportRuntimePermissions(packageInfo5) && (packageManager2 = this.mContext.getPackageManager()) != null && packageManager2.checkSignatures("com.samsung.android.video", "android") == 0) {
                    grantRuntimePermissions(packageManagerWrapper, packageInfo5, set3, false, i);
                }
                packageInfo6 = packageManagerWrapper.getPackageInfo("com.samsung.app.newtrim");
                if (packageInfo6 != null && doesPackageSupportRuntimePermissions(packageInfo6) && (packageManager = this.mContext.getPackageManager()) != null && packageManager.checkSignatures("com.samsung.app.newtrim", "android") == 0) {
                    grantRuntimePermissions(packageManagerWrapper, packageInfo6, set3, false, i);
                }
                systemPackageInfo3 = packageManagerWrapper.getSystemPackageInfo("com.sec.android.app.samsungapps");
                if (systemPackageInfo3 != null && doesPackageSupportRuntimePermissions(systemPackageInfo3)) {
                    ArraySet arraySet6 = new ArraySet();
                    if (!ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                        arraySet6.add("android.permission.POST_NOTIFICATIONS");
                        grantRuntimePermissions(packageManagerWrapper, systemPackageInfo3, arraySet6, false, i);
                    } else if ("KOREA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
                        arraySet6.addAll(CONTACTS_PERMISSIONS);
                        arraySet6.add("android.permission.POST_NOTIFICATIONS");
                        grantRuntimePermissions(packageManagerWrapper, systemPackageInfo3, arraySet6, false, i);
                    } else {
                        arraySet6.add("android.permission.POST_NOTIFICATIONS");
                        grantRuntimePermissions(packageManagerWrapper, systemPackageInfo3, arraySet6, false, i);
                    }
                }
                PackageInfo systemPackageInfo4 = packageManagerWrapper.getSystemPackageInfo("com.sec.location.nfwlocationprivacy");
                if ("KOREA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)) || systemPackageInfo4 == null || !doesPackageSupportRuntimePermissions(systemPackageInfo4)) {
                    return;
                }
                ArraySet arraySet7 = new ArraySet();
                arraySet7.add("android.permission.ACCESS_FINE_LOCATION");
                arraySet7.add("android.permission.ACCESS_BACKGROUND_LOCATION");
                grantRuntimePermissions(packageManagerWrapper, systemPackageInfo4, arraySet7, true, i);
                return;
            }
        }
        str = str9;
        grantPermissionsToPackage(packageManagerWrapper, str, i, false, true, FOREGROUND_LOCATION_PERMISSIONS);
        if (strArr != null) {
        }
        if (ActivityManager.isLowRamDeviceStatic()) {
        }
        String defaultSystemHandlerServicePackage2 = getDefaultSystemHandlerServicePackage(packageManagerWrapper, new Intent("android.speech.RecognitionService").addCategory("android.intent.category.DEFAULT"), i);
        Set set192 = MICROPHONE_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerServicePackage2, i, set192);
        if ("CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            arraySet = new ArraySet();
            arraySet.add("android.permission.BLUETOOTH_SCAN");
            arraySet.add("android.permission.BLUETOOTH_CONNECT");
            arraySet.add("android.permission.BLUETOOTH_ADVERTISE");
            packageManager6 = this.mContext.getPackageManager();
            if (packageManager6 != null) {
                grantRuntimePermissions(packageManagerWrapper, packageInfo7, arraySet, false, i);
            }
        }
        if ("CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, "com.google.android.apps.tachyon", i, set192, MEET_CONTACTS_PERMISSIONS, NOTIFICATION_PERMISSIONS, PHONE_PERMISSIONS);
        if (strArr2 != null) {
        }
        if (strArr9 != null) {
        }
        String defaultSystemHandlerActivityPackage32 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.VIEW").addCategory("android.intent.category.DEFAULT").setDataAndType(Uri.fromFile(new File("foo.mp3")), "audio/mpeg"), i);
        Set set202 = STORAGE_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage32, i, set202);
        String defaultSystemHandlerActivityPackage42 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").addCategory("android.intent.category.LAUNCHER_APP"), i);
        Set set212 = ALWAYS_LOCATION_PERMISSIONS;
        Set set222 = NOTIFICATION_PERMISSIONS;
        grantPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage42, i, set212, set222);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
        }
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.printspooler", i, set212, set222);
        String defaultSystemHandlerActivityPackage52 = getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.telephony.action.EMERGENCY_ASSISTANCE", i);
        Set set232 = CONTACTS_PERMISSIONS;
        Set set242 = PHONE_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, defaultSystemHandlerActivityPackage52, i, set232, set242);
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.VIEW").setType("vnd.android.cursor.item/ndef_msg"), i), i, set232, set242);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.os.storage.action.MANAGE_STORAGE", i), i, set202);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.companiondevicemanager", i, set212, NEARBY_DEVICES_PERMISSIONS);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerActivityPackage(packageManagerWrapper, "android.intent.action.RINGTONE_PICKER", i), i, set202);
        while (r3 < r2) {
        }
        set3 = STORAGE_PERMISSIONS;
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.sharedstoragebackup", i, set3);
        grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, "com.android.bluetoothmidiservice", i, NEARBY_DEVICES_PERMISSIONS);
        packageInfo = packageManagerWrapper.getPackageInfo("com.samsung.android.app.reminder");
        if (packageInfo != null) {
            grantRuntimePermissions(packageManagerWrapper, packageInfo, NOTIFICATION_PERMISSIONS, false, i);
        }
        packageInfo2 = packageManagerWrapper.getPackageInfo("com.samsung.android.calendar");
        if (packageInfo2 != null) {
            ArraySet arraySet32 = new ArraySet();
            if (!"CHINA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            }
            arraySet32.add("android.permission.POST_NOTIFICATIONS");
            grantRuntimePermissions(packageManagerWrapper, packageInfo2, arraySet32, false, i);
        }
        packageInfo3 = packageManagerWrapper.getPackageInfo("com.sec.android.app.clockpackage");
        if (packageInfo3 != null) {
            grantRuntimePermissions(packageManagerWrapper, packageInfo3, NOTIFICATION_PERMISSIONS, false, i);
        }
        systemPackageInfo = packageManagerWrapper.getSystemPackageInfo("com.samsung.android.dck.timesync");
        if (systemPackageInfo != null) {
            ArraySet arraySet42 = new ArraySet();
            packageManagerWrapper.updatePermissionFlags("android.permission.BLUETOOTH_CONNECT", systemPackageInfo, 16, 16, UserHandle.of(i));
            ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).setUidMode(111, 1000, 0);
            arraySet42.add("android.permission.BLUETOOTH_CONNECT");
            grantRuntimePermissions(packageManagerWrapper, systemPackageInfo, arraySet42, true, i);
        }
        systemPackageInfo2 = packageManagerWrapper.getSystemPackageInfo("com.samsung.android.bixby.agent");
        if (systemPackageInfo2 != null) {
            String str142 = SemSystemProperties.get("ro.build.characteristics");
            if (str142 == null) {
            }
            if (packageManagerWrapper.getSystemPackageInfo("com.samsung.android.dialer") == null) {
            }
            if (packageManagerWrapper.getSystemPackageInfo("com.samsung.android.messaging") == null) {
            }
            ArraySet arraySet52 = new ArraySet();
            arraySet52.add("android.permission.RECORD_AUDIO");
            arraySet52.add("android.permission.READ_CALENDAR");
            arraySet52.add("android.permission.WRITE_CALENDAR");
            if (z3) {
            }
            arraySet52.add("android.permission.CAMERA");
            arraySet52.add("android.permission.READ_CONTACTS");
            arraySet52.add("android.permission.WRITE_CONTACTS");
            arraySet52.add("android.permission.ACCESS_FINE_LOCATION");
            arraySet52.add("android.permission.ACCESS_COARSE_LOCATION");
            arraySet52.add("android.permission.ACCESS_BACKGROUND_LOCATION");
            if (z) {
            }
            arraySet52.add("android.permission.READ_PHONE_STATE");
            arraySet52.add("android.permission.CALL_PHONE");
            arraySet52.add("android.permission.READ_EXTERNAL_STORAGE");
            arraySet52.add("android.permission.WRITE_EXTERNAL_STORAGE");
            if (!z2) {
            }
            arraySet52.add("android.permission.BLUETOOTH_ADVERTISE");
            arraySet52.add("android.permission.BLUETOOTH_CONNECT");
            arraySet52.add("android.permission.BLUETOOTH_SCAN");
            arraySet52.add("android.permission.READ_MEDIA_IMAGES");
            arraySet52.add("android.permission.READ_MEDIA_VIDEO");
            arraySet52.add("android.permission.READ_MEDIA_AUDIO");
            arraySet52.add("android.permission.POST_NOTIFICATIONS");
            grantRuntimePermissions(packageManagerWrapper, systemPackageInfo2, arraySet52, false, i);
            Settings.Global.putString(this.mContext.getContentResolver(), "bixby_pregranted_permissions", String.join(KnoxVpnFirewallHelper.DELIMITER, arraySet52));
        }
        packageInfo4 = packageManagerWrapper.getPackageInfo("com.sec.android.mimage.photoretouching");
        if (packageInfo4 != null) {
            grantRuntimePermissions(packageManagerWrapper, packageInfo4, set3, false, i);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, getDefaultSystemHandlerServicePackage(packageManagerWrapper, "android.adservices.AD_SERVICES_COMMON_SERVICE", i), i, NOTIFICATION_PERMISSIONS);
        packageInfo5 = packageManagerWrapper.getPackageInfo("com.samsung.android.video");
        if (packageInfo5 != null) {
            grantRuntimePermissions(packageManagerWrapper, packageInfo5, set3, false, i);
        }
        packageInfo6 = packageManagerWrapper.getPackageInfo("com.samsung.app.newtrim");
        if (packageInfo6 != null) {
            grantRuntimePermissions(packageManagerWrapper, packageInfo6, set3, false, i);
        }
        systemPackageInfo3 = packageManagerWrapper.getSystemPackageInfo("com.sec.android.app.samsungapps");
        if (systemPackageInfo3 != null) {
            ArraySet arraySet62 = new ArraySet();
            if (!ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            }
        }
        PackageInfo systemPackageInfo42 = packageManagerWrapper.getSystemPackageInfo("com.sec.location.nfwlocationprivacy");
        if ("KOREA".equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
        }
    }

    public final String getDefaultSystemHandlerActivityPackageForCategory(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        return getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent("android.intent.action.MAIN").addCategory(str), i);
    }

    public final String getDefaultSearchSelectorPackage() {
        return this.mContext.getString(R.string.days);
    }

    public final String getDefaultCaptivePortalLoginPackage() {
        return this.mContext.getString(R.string.data_usage_rapid_title);
    }

    public final String getDefaultDockManagerPackage() {
        return this.mContext.getString(R.string.date_picker_day_typeface);
    }

    public final void grantPermissionToEachSystemPackage(PackageManagerWrapper packageManagerWrapper, ArrayList arrayList, int i, Set... setArr) {
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            grantPermissionsToSystemPackage(packageManagerWrapper, (String) arrayList.get(i2), i, setArr);
        }
    }

    public final String[] getKnownPackages(int i, int i2) {
        return this.mServiceInternal.getKnownPackageNames(i, i2);
    }

    public final void grantDefaultPermissionsToDefaultSystemDialerApp(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        if (str == null) {
            return;
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch", 0)) {
            grantSystemFixedPermissionsToSystemPackage(packageManagerWrapper, str, i, PHONE_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        } else {
            grantPermissionsToSystemPackage(packageManagerWrapper, str, i, PHONE_PERMISSIONS);
        }
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, CONTACTS_PERMISSIONS, SMS_PERMISSIONS, MICROPHONE_PERMISSIONS, CAMERA_PERMISSIONS, NOTIFICATION_PERMISSIONS);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0)) {
            grantPermissionsToSystemPackage(packageManagerWrapper, str, i, NEARBY_DEVICES_PERMISSIONS);
        }
    }

    public final void grantDefaultPermissionsToDefaultSystemSmsApp(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, PHONE_PERMISSIONS, CONTACTS_PERMISSIONS, SMS_PERMISSIONS, STORAGE_PERMISSIONS, MICROPHONE_PERMISSIONS, CAMERA_PERMISSIONS, NOTIFICATION_PERMISSIONS);
    }

    public final void grantDefaultPermissionsToDefaultSystemUseOpenWifiApp(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        grantPermissionsToSystemPackage(packageManagerWrapper, str, i, ALWAYS_LOCATION_PERMISSIONS);
    }

    public void grantDefaultPermissionsToDefaultUseOpenWifiApp(String str, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting permissions to default Use Open WiFi app for user:" + i);
        grantIgnoringSystemPackage(this.NO_PM_CACHE, str, i, ALWAYS_LOCATION_PERMISSIONS);
    }

    public void grantDefaultPermissionsToDefaultSimCallManager(String str, int i) {
        grantDefaultPermissionsToDefaultSimCallManager(this.NO_PM_CACHE, str, i);
    }

    public final void grantDefaultPermissionsToDefaultSimCallManager(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        if (str == null) {
            return;
        }
        Log.i("DefaultPermGrantPolicy", "Granting permissions to sim call manager for user:" + i);
        grantPermissionsToPackage(packageManagerWrapper, str, i, false, true, PHONE_PERMISSIONS, MICROPHONE_PERMISSIONS);
    }

    public final void grantDefaultPermissionsToDefaultSystemSimCallManager(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        if (packageManagerWrapper.isSystemPackage(str)) {
            grantDefaultPermissionsToDefaultSimCallManager(packageManagerWrapper, str, i);
        }
    }

    public void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting permissions to enabled carrier apps for user:" + i);
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            grantPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, PHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, SMS_PERMISSIONS);
        }
    }

    public void grantDefaultPermissionsToEnabledImsServices(String[] strArr, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting permissions to enabled ImsServices for user:" + i);
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            grantPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, PHONE_PERMISSIONS, MICROPHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS, CAMERA_PERMISSIONS, CONTACTS_PERMISSIONS);
        }
    }

    public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting permissions to enabled data services for user:" + i);
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            grantSystemFixedPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, PHONE_PERMISSIONS, ALWAYS_LOCATION_PERMISSIONS);
        }
    }

    public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, int i) {
        Log.i("DefaultPermGrantPolicy", "Revoking permissions from disabled data services for user:" + i);
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            PackageInfo systemPackageInfo = this.NO_PM_CACHE.getSystemPackageInfo(str);
            if (this.NO_PM_CACHE.isSystemPackage(systemPackageInfo) && doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                revokeRuntimePermissions(this.NO_PM_CACHE, str, PHONE_PERMISSIONS, true, i);
                revokeRuntimePermissions(this.NO_PM_CACHE, str, ALWAYS_LOCATION_PERMISSIONS, true, i);
            }
        }
    }

    public void grantDefaultPermissionsToActiveLuiApp(String str, int i) {
        Log.i("DefaultPermGrantPolicy", "Granting permissions to active LUI app for user:" + i);
        grantSystemFixedPermissionsToSystemPackage(this.NO_PM_CACHE, str, i, CAMERA_PERMISSIONS, NOTIFICATION_PERMISSIONS);
    }

    public void revokeDefaultPermissionsFromLuiApps(String[] strArr, int i) {
        Log.i("DefaultPermGrantPolicy", "Revoke permissions from LUI apps for user:" + i);
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            PackageInfo systemPackageInfo = this.NO_PM_CACHE.getSystemPackageInfo(str);
            if (this.NO_PM_CACHE.isSystemPackage(systemPackageInfo) && doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                revokeRuntimePermissions(this.NO_PM_CACHE, str, CAMERA_PERMISSIONS, true, i);
            }
        }
    }

    public void grantDefaultPermissionsToCarrierServiceApp(String str, int i) {
        Log.i("DefaultPermGrantPolicy", "Grant permissions to Carrier Service app " + str + " for user:" + i);
        grantPermissionsToPackage(this.NO_PM_CACHE, str, i, false, true, NOTIFICATION_PERMISSIONS);
    }

    public final String getDefaultSystemHandlerActivityPackage(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        return getDefaultSystemHandlerActivityPackage(packageManagerWrapper, new Intent(str), i);
    }

    public final String getDefaultSystemHandlerActivityPackage(PackageManagerWrapper packageManagerWrapper, Intent intent, int i) {
        ActivityInfo activityInfo;
        ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent, 794624, i);
        if (resolveActivityAsUser == null || (activityInfo = resolveActivityAsUser.activityInfo) == null || this.mServiceInternal.isResolveActivityComponent(activityInfo)) {
            return null;
        }
        String str = resolveActivityAsUser.activityInfo.packageName;
        if (packageManagerWrapper.isSystemPackage(str)) {
            return str;
        }
        return null;
    }

    public final String getDefaultSystemHandlerServicePackage(PackageManagerWrapper packageManagerWrapper, String str, int i) {
        return getDefaultSystemHandlerServicePackage(packageManagerWrapper, new Intent(str), i);
    }

    public final String getDefaultSystemHandlerServicePackage(PackageManagerWrapper packageManagerWrapper, Intent intent, int i) {
        List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 794624, i);
        if (queryIntentServicesAsUser == null) {
            return null;
        }
        int size = queryIntentServicesAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo.packageName;
            if (packageManagerWrapper.isSystemPackage(str)) {
                return str;
            }
        }
        return null;
    }

    public final ArrayList getHeadlessSyncAdapterPackages(PackageManagerWrapper packageManagerWrapper, String[] strArr, int i) {
        ArrayList arrayList = new ArrayList();
        Intent addCategory = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        for (String str : strArr) {
            addCategory.setPackage(str);
            if (this.mContext.getPackageManager().resolveActivityAsUser(addCategory, 794624, i) == null && packageManagerWrapper.isSystemPackage(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final String getDefaultProviderAuthorityPackage(String str, int i) {
        ProviderInfo resolveContentProviderAsUser = this.mContext.getPackageManager().resolveContentProviderAsUser(str, 794624, i);
        if (resolveContentProviderAsUser != null) {
            return resolveContentProviderAsUser.packageName;
        }
        return null;
    }

    public final void grantRuntimePermissions(PackageManagerWrapper packageManagerWrapper, PackageInfo packageInfo, Set set, boolean z, int i) {
        grantRuntimePermissions(packageManagerWrapper, packageInfo, set, z, false, true, i);
    }

    public final void revokeRuntimePermissions(PackageManagerWrapper packageManagerWrapper, String str, Set set, boolean z, int i) {
        PackageInfo systemPackageInfo = packageManagerWrapper.getSystemPackageInfo(str);
        if (systemPackageInfo == null || ArrayUtils.isEmpty(systemPackageInfo.requestedPermissions)) {
            return;
        }
        ArraySet arraySet = new ArraySet(Arrays.asList(systemPackageInfo.requestedPermissions));
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (arraySet.contains(str2)) {
                UserHandle of = UserHandle.of(i);
                int permissionFlags = packageManagerWrapper.getPermissionFlags(str2, packageManagerWrapper.getPackageInfo(str), of);
                if ((permissionFlags & 32) != 0 && (permissionFlags & 4) == 0 && ((permissionFlags & 16) == 0 || z)) {
                    packageManagerWrapper.revokePermission(str2, systemPackageInfo, of);
                    packageManagerWrapper.updatePermissionFlags(str2, systemPackageInfo, 32, 0, of);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void grantRuntimePermissions(PackageManagerWrapper packageManagerWrapper, PackageInfo packageInfo, Set set, boolean z, boolean z2, boolean z3, int i) {
        String[] strArr;
        ArraySet arraySet;
        int i2;
        int i3;
        String str;
        String str2;
        ApplicationInfo applicationInfo;
        PackageInfo systemPackageInfo;
        UserHandle of = UserHandle.of(i);
        if (packageInfo == null) {
            return;
        }
        String[] strArr2 = packageInfo.requestedPermissions;
        if (ArrayUtils.isEmpty(strArr2)) {
            return;
        }
        String[] strArr3 = packageManagerWrapper.getPackageInfo(packageInfo.packageName).requestedPermissions;
        int length = strArr2.length;
        for (int i4 = 0; i4 < length; i4++) {
            if (!ArrayUtils.contains(strArr3, strArr2[i4])) {
                strArr2[i4] = null;
            }
        }
        String[] strArr4 = (String[]) ArrayUtils.filterNotNull(strArr2, new IntFunction() { // from class: com.android.server.pm.permission.DefaultPermissionGrantPolicy$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i5) {
                String[] lambda$grantRuntimePermissions$0;
                lambda$grantRuntimePermissions$0 = DefaultPermissionGrantPolicy.lambda$grantRuntimePermissions$0(i5);
                return lambda$grantRuntimePermissions$0;
            }
        });
        ArraySet arraySet2 = new ArraySet(set);
        ApplicationInfo applicationInfo2 = packageInfo.applicationInfo;
        int i5 = z ? 48 : 32;
        List splitPermissions = ((PermissionManager) this.mContext.getSystemService(PermissionManager.class)).getSplitPermissions();
        int size = splitPermissions.size();
        for (int i6 = 0; i6 < size; i6++) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo = (PermissionManager.SplitPermissionInfo) splitPermissions.get(i6);
            if (applicationInfo2 != null && applicationInfo2.targetSdkVersion < splitPermissionInfo.getTargetSdk() && set.contains(splitPermissionInfo.getSplitPermission())) {
                arraySet2.addAll(splitPermissionInfo.getNewPermissions());
            }
        }
        if (!z2 && applicationInfo2 != null && applicationInfo2.isUpdatedSystemApp() && (systemPackageInfo = packageManagerWrapper.getSystemPackageInfo(this.mServiceInternal.getDisabledSystemPackageName(packageInfo.packageName))) != null) {
            if (ArrayUtils.isEmpty(systemPackageInfo.requestedPermissions)) {
                return;
            }
            if (!Arrays.equals(strArr4, systemPackageInfo.requestedPermissions)) {
                ArraySet arraySet3 = new ArraySet(Arrays.asList(strArr4));
                strArr = systemPackageInfo.requestedPermissions;
                arraySet = arraySet3;
                int length2 = strArr.length;
                String[] strArr5 = new String[length2];
                int i7 = 0;
                int i8 = 0;
                for (String str3 : strArr) {
                    if (packageManagerWrapper.getBackgroundPermission(str3) != null) {
                        strArr5[i8] = str3;
                        i8++;
                    } else {
                        strArr5[(length2 - 1) - i7] = str3;
                        i7++;
                    }
                }
                for (String str4 : strArr) {
                    if ((arraySet == null || arraySet.contains(str4)) && arraySet2.contains(str4)) {
                        int permissionFlags = packageManagerWrapper.getPermissionFlags(str4, packageInfo, of);
                        boolean z4 = z && (permissionFlags & 16) != 0;
                        if (isFixedOrUserSet(permissionFlags) && !z2 && !z4) {
                            i2 = i5;
                            i3 = permissionFlags;
                            str2 = str4;
                        } else if ((permissionFlags & 4) == 0) {
                            i2 = i5 | (permissionFlags & 14336);
                            if (z3 && packageManagerWrapper.isPermissionRestricted(str4)) {
                                i3 = permissionFlags;
                                str = str4;
                                packageManagerWrapper.updatePermissionFlags(str4, packageInfo, IInstalld.FLAG_USE_QUOTA, IInstalld.FLAG_USE_QUOTA, of);
                            } else {
                                i3 = permissionFlags;
                                str = str4;
                            }
                            if (z4) {
                                packageManagerWrapper.updatePermissionFlags(str, packageInfo, i3, i3 & (-17), of);
                            }
                            String str5 = str;
                            if (!packageManagerWrapper.isGranted(str5, packageInfo, of)) {
                                packageManagerWrapper.grantPermission(str5, packageInfo, of);
                            }
                            if (!"android.uid.system".equals(packageInfo.sharedUserId) && !"android.uid.systemui".equals(packageInfo.sharedUserId) && str5.equals("android.permission.POST_NOTIFICATIONS") && (applicationInfo = packageInfo.applicationInfo) != null) {
                                if (this.mNotiBlockableDataByUid.get(Integer.valueOf(applicationInfo.uid)) == null || !((Boolean) this.mNotiBlockableDataByUid.get(Integer.valueOf(packageInfo.applicationInfo.uid))).booleanValue()) {
                                    Bundle bundle = packageInfo.applicationInfo.metaData;
                                    if (bundle != null && bundle.getBoolean("com.samsung.android.notification.blockable", false)) {
                                        this.mNotiBlockableDataByUid.put(Integer.valueOf(packageInfo.applicationInfo.uid), Boolean.TRUE);
                                    }
                                }
                                i2 &= -17;
                            }
                            str2 = str5;
                            packageManagerWrapper.updatePermissionFlags(str5, packageInfo, i2 | 64, i2, of);
                        }
                        if ((i3 & 32) != 0 && (i3 & 16) != 0 && !z) {
                            packageManagerWrapper.updatePermissionFlags(str2, packageInfo, 16, 0, of);
                        }
                        i5 = i2;
                    }
                }
            }
        }
        strArr = strArr4;
        arraySet = null;
        int length22 = strArr.length;
        String[] strArr52 = new String[length22];
        int i72 = 0;
        int i82 = 0;
        while (r2 < length22) {
        }
        while (r15 < length22) {
        }
    }

    public static /* synthetic */ String[] lambda$grantRuntimePermissions$0(int i) {
        return new String[i];
    }

    public final void grantDefaultPermissionExceptions(PackageManagerWrapper packageManagerWrapper, int i) {
        int i2;
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            if (this.mGrantExceptions == null) {
                this.mGrantExceptions = readDefaultPermissionExceptionsLocked(packageManagerWrapper);
            }
        }
        int size = this.mGrantExceptions.size();
        ArraySet arraySet = null;
        for (int i3 = 0; i3 < size; i3++) {
            PackageInfo packageInfo = packageManagerWrapper.getPackageInfo((String) this.mGrantExceptions.keyAt(i3));
            List list = (List) this.mGrantExceptions.valueAt(i3);
            int size2 = list.size();
            int i4 = 0;
            while (i4 < size2) {
                DefaultPermissionGrant defaultPermissionGrant = (DefaultPermissionGrant) list.get(i4);
                if (packageManagerWrapper.isPermissionDangerous(defaultPermissionGrant.name)) {
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                    } else {
                        arraySet.clear();
                    }
                    ArraySet arraySet2 = arraySet;
                    arraySet2.add(defaultPermissionGrant.name);
                    i2 = i4;
                    grantRuntimePermissions(packageManagerWrapper, packageInfo, arraySet2, defaultPermissionGrant.fixed, defaultPermissionGrant.whitelisted, true, i);
                    arraySet = arraySet2;
                } else {
                    Log.w("DefaultPermGrantPolicy", "Ignoring permission " + defaultPermissionGrant.name + " which isn't dangerous");
                    i2 = i4;
                }
                i4 = i2 + 1;
            }
        }
    }

    public final File[] getDefaultPermissionFiles() {
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
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.embedded", 0)) {
            File file6 = new File(Environment.getOemDirectory(), "etc/default-permissions");
            if (file6.isDirectory() && file6.canRead()) {
                Collections.addAll(arrayList, file6.listFiles());
            }
        }
        File file7 = new File(SystemProperties.get("mdc.sys.omc_etcpath", ""), "default-permissions");
        if (file7.isDirectory() && file7.canRead()) {
            Collections.addAll(arrayList, file7.listFiles());
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (File[]) arrayList.toArray(new File[0]);
    }

    public final ArrayMap readDefaultPermissionExceptionsLocked(PackageManagerWrapper packageManagerWrapper) {
        File[] defaultPermissionFiles = getDefaultPermissionFiles();
        if (defaultPermissionFiles == null) {
            return new ArrayMap(0);
        }
        ArrayMap arrayMap = new ArrayMap();
        for (File file : defaultPermissionFiles) {
            if (!file.getPath().endsWith(".xml")) {
                Slog.i("DefaultPermGrantPolicy", "Non-xml file " + file + " in " + file.getParent() + " directory, ignoring");
            } else if (file.canRead()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        parse(packageManagerWrapper, Xml.resolvePullParser(fileInputStream), arrayMap);
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
                    Slog.w("DefaultPermGrantPolicy", "Error reading default permissions file " + file, e);
                }
            } else {
                Slog.w("DefaultPermGrantPolicy", "Default permissions file " + file + " cannot be read");
            }
        }
        return arrayMap;
    }

    public final void parse(PackageManagerWrapper packageManagerWrapper, TypedXmlPullParser typedXmlPullParser, Map map) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if ("exceptions".equals(typedXmlPullParser.getName())) {
                    parseExceptions(packageManagerWrapper, typedXmlPullParser, map);
                } else {
                    Log.e("DefaultPermGrantPolicy", "Unknown tag " + typedXmlPullParser.getName());
                }
            }
        }
    }

    public final void parseExceptions(PackageManagerWrapper packageManagerWrapper, TypedXmlPullParser typedXmlPullParser, Map map) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if ("exception".equals(typedXmlPullParser.getName())) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "package");
                    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "cert");
                    List list = (List) map.get(attributeValue);
                    if (list == null) {
                        PackageInfo packageInfo = packageManagerWrapper.getPackageInfo(attributeValue);
                        if (packageInfo == null) {
                            Log.w("DefaultPermGrantPolicy", "No such package:" + attributeValue);
                            XmlUtils.skipCurrentTag(typedXmlPullParser);
                        } else if (!isSystemOrPlatformSignedOrCertificateMatchingPackage(packageInfo, attributeValue2)) {
                            Log.w("DefaultPermGrantPolicy", "Not system or platform signed or certificate-matching package: " + attributeValue);
                            XmlUtils.skipCurrentTag(typedXmlPullParser);
                        } else if (!doesPackageSupportRuntimePermissions(packageInfo)) {
                            Log.w("DefaultPermGrantPolicy", "Skipping non supporting runtime permissions package:" + attributeValue);
                            XmlUtils.skipCurrentTag(typedXmlPullParser);
                        } else {
                            list = new ArrayList();
                            map.put(attributeValue, list);
                        }
                    }
                    parsePermission(typedXmlPullParser, list);
                } else {
                    Log.e("DefaultPermGrantPolicy", "Unknown tag " + typedXmlPullParser.getName() + "under <exceptions>");
                }
            }
        }
    }

    public final void parsePermission(TypedXmlPullParser typedXmlPullParser, List list) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if ("permission".contains(typedXmlPullParser.getName())) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                    if (attributeValue == null) {
                        Log.w("DefaultPermGrantPolicy", "Mandatory name attribute missing for permission tag");
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    } else {
                        list.add(new DefaultPermissionGrant(attributeValue, typedXmlPullParser.getAttributeBoolean((String) null, "fixed", false), typedXmlPullParser.getAttributeBoolean((String) null, "whitelisted", false)));
                    }
                } else {
                    Log.e("DefaultPermGrantPolicy", "Unknown tag " + typedXmlPullParser.getName() + "under <exception>");
                }
            }
        }
    }

    public final boolean isSystemOrPlatformSignedOrCertificateMatchingPackage(PackageInfo packageInfo, String str) {
        if (str == null) {
            return packageInfo.applicationInfo.isSystemApp() || this.mServiceInternal.isPlatformSigned(packageInfo.packageName);
        }
        return this.mContext.getPackageManager().hasSigningCertificate(packageInfo.packageName, HexEncoding.decode(str.replace(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR, "")), 1);
    }

    public static boolean doesPackageSupportRuntimePermissions(PackageInfo packageInfo) {
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        return applicationInfo != null && applicationInfo.targetSdkVersion > 22;
    }

    public abstract class PackageManagerWrapper {
        public abstract PackageInfo getPackageInfo(String str);

        public abstract int getPermissionFlags(String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract PermissionInfo getPermissionInfo(String str);

        public abstract void grantPermission(String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract boolean isGranted(String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract void revokePermission(String str, PackageInfo packageInfo, UserHandle userHandle);

        public abstract void updatePermissionFlags(String str, PackageInfo packageInfo, int i, int i2, UserHandle userHandle);

        public PackageManagerWrapper() {
        }

        public PackageInfo getSystemPackageInfo(String str) {
            PackageInfo packageInfo = getPackageInfo(str);
            if (packageInfo == null || !packageInfo.applicationInfo.isSystemApp()) {
                return null;
            }
            return packageInfo;
        }

        public boolean isPermissionRestricted(String str) {
            PermissionInfo permissionInfo = getPermissionInfo(str);
            if (permissionInfo == null) {
                return false;
            }
            return permissionInfo.isRestricted();
        }

        public boolean isPermissionDangerous(String str) {
            PermissionInfo permissionInfo = getPermissionInfo(str);
            return permissionInfo != null && permissionInfo.getProtection() == 1;
        }

        public String getBackgroundPermission(String str) {
            PermissionInfo permissionInfo = getPermissionInfo(str);
            if (permissionInfo == null) {
                return null;
            }
            return permissionInfo.backgroundPermission;
        }

        public boolean isSystemPackage(String str) {
            return isSystemPackage(getPackageInfo(str));
        }

        public boolean isSystemPackage(PackageInfo packageInfo) {
            return (packageInfo == null || !packageInfo.applicationInfo.isSystemApp() || isSysComponentOrPersistentPlatformSignedPrivApp(packageInfo)) ? false : true;
        }

        public boolean isSysComponentOrPersistentPlatformSignedPrivApp(PackageInfo packageInfo) {
            if (UserHandle.getAppId(packageInfo.applicationInfo.uid) < 10000) {
                return true;
            }
            if (!packageInfo.applicationInfo.isPrivilegedApp()) {
                return false;
            }
            PackageInfo systemPackageInfo = getSystemPackageInfo(DefaultPermissionGrantPolicy.this.mServiceInternal.getDisabledSystemPackageName(packageInfo.applicationInfo.packageName));
            if (systemPackageInfo != null) {
                ApplicationInfo applicationInfo = systemPackageInfo.applicationInfo;
                if (applicationInfo != null && (applicationInfo.flags & 8) == 0) {
                    return false;
                }
            } else if ((packageInfo.applicationInfo.flags & 8) == 0) {
                return false;
            }
            return DefaultPermissionGrantPolicy.this.mServiceInternal.isPlatformSigned(packageInfo.packageName);
        }
    }

    public class DelayingPackageManagerCache extends PackageManagerWrapper {
        public SparseArray mDelayedPermissionState;
        public ArrayMap mPackageInfos;
        public ArrayMap mPermissionInfos;
        public SparseArray mUserContexts;

        public DelayingPackageManagerCache() {
            super();
            this.mDelayedPermissionState = new SparseArray();
            this.mUserContexts = new SparseArray();
            this.mPermissionInfos = new ArrayMap();
            this.mPackageInfos = new ArrayMap();
        }

        public void apply() {
            PackageManager.corkPackageInfoCache();
            for (int i = 0; i < this.mDelayedPermissionState.size(); i++) {
                for (int i2 = 0; i2 < ((ArrayMap) this.mDelayedPermissionState.valueAt(i)).size(); i2++) {
                    try {
                        ((PermissionState) ((ArrayMap) this.mDelayedPermissionState.valueAt(i)).valueAt(i2)).apply();
                    } catch (IllegalArgumentException e) {
                        Slog.w("DefaultPermGrantPolicy", "Cannot set permission " + ((String) ((ArrayMap) this.mDelayedPermissionState.valueAt(i)).keyAt(i2)) + " of uid " + this.mDelayedPermissionState.keyAt(i), e);
                    }
                }
            }
            PackageManager.uncorkPackageInfoCache();
        }

        public void addPackageInfo(String str, PackageInfo packageInfo) {
            this.mPackageInfos.put(str, packageInfo);
        }

        public final Context createContextAsUser(UserHandle userHandle) {
            int indexOfKey = this.mUserContexts.indexOfKey(userHandle.getIdentifier());
            if (indexOfKey >= 0) {
                return (Context) this.mUserContexts.valueAt(indexOfKey);
            }
            Context createContextAsUser = DefaultPermissionGrantPolicy.this.mContext.createContextAsUser(userHandle, 0);
            this.mUserContexts.put(userHandle.getIdentifier(), createContextAsUser);
            return createContextAsUser;
        }

        public final PermissionState getPermissionState(String str, PackageInfo packageInfo, UserHandle userHandle) {
            ArrayMap arrayMap;
            int uid = UserHandle.getUid(userHandle.getIdentifier(), UserHandle.getAppId(packageInfo.applicationInfo.uid));
            int indexOfKey = this.mDelayedPermissionState.indexOfKey(uid);
            if (indexOfKey >= 0) {
                arrayMap = (ArrayMap) this.mDelayedPermissionState.valueAt(indexOfKey);
            } else {
                ArrayMap arrayMap2 = new ArrayMap();
                this.mDelayedPermissionState.put(uid, arrayMap2);
                arrayMap = arrayMap2;
            }
            int indexOfKey2 = arrayMap.indexOfKey(str);
            if (indexOfKey2 >= 0) {
                PermissionState permissionState = (PermissionState) arrayMap.valueAt(indexOfKey2);
                if (ArrayUtils.contains(permissionState.mPkgRequestingPerm.requestedPermissions, str) || !ArrayUtils.contains(packageInfo.requestedPermissions, str)) {
                    return permissionState;
                }
                permissionState.mPkgRequestingPerm = packageInfo;
                return permissionState;
            }
            PermissionState permissionState2 = new PermissionState(str, packageInfo, userHandle);
            arrayMap.put(str, permissionState2);
            return permissionState2;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public int getPermissionFlags(String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initFlags();
            return permissionState.newFlags.intValue();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void updatePermissionFlags(String str, PackageInfo packageInfo, int i, int i2, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initFlags();
            permissionState.newFlags = Integer.valueOf((permissionState.newFlags.intValue() & (~i)) | (i2 & i));
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void grantPermission(String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            permissionState.newGranted = Boolean.TRUE;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public void revokePermission(String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            permissionState.newGranted = Boolean.FALSE;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public boolean isGranted(String str, PackageInfo packageInfo, UserHandle userHandle) {
            PermissionState permissionState = getPermissionState(str, packageInfo, userHandle);
            permissionState.initGranted();
            return permissionState.newGranted.booleanValue();
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public PermissionInfo getPermissionInfo(String str) {
            int indexOfKey = this.mPermissionInfos.indexOfKey(str);
            if (indexOfKey >= 0) {
                return (PermissionInfo) this.mPermissionInfos.valueAt(indexOfKey);
            }
            PermissionInfo permissionInfo = DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPermissionInfo(str);
            this.mPermissionInfos.put(str, permissionInfo);
            return permissionInfo;
        }

        @Override // com.android.server.pm.permission.DefaultPermissionGrantPolicy.PackageManagerWrapper
        public PackageInfo getPackageInfo(String str) {
            int indexOfKey = this.mPackageInfos.indexOfKey(str);
            if (indexOfKey >= 0) {
                return (PackageInfo) this.mPackageInfos.valueAt(indexOfKey);
            }
            PackageInfo packageInfo = DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPackageInfo(str);
            this.mPackageInfos.put(str, packageInfo);
            return packageInfo;
        }

        public class PermissionState {
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

            public void apply() {
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
                if (i2 != 0) {
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(this.mPermission, this.mPkgRequestingPerm, i2, 0, this.mUser);
                }
                int i3 = i & 14336;
                if (i3 != 0) {
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(this.mPermission, this.mPkgRequestingPerm, i3, -1, this.mUser);
                }
                Boolean bool = this.newGranted;
                if (bool != null && !Objects.equals(bool, this.mOriginalGranted)) {
                    if (this.newGranted.booleanValue()) {
                        DefaultPermissionGrantPolicy.this.NO_PM_CACHE.grantPermission(this.mPermission, this.mPkgRequestingPerm, this.mUser);
                    } else {
                        DefaultPermissionGrantPolicy.this.NO_PM_CACHE.revokePermission(this.mPermission, this.mPkgRequestingPerm, this.mUser);
                    }
                }
                int i4 = i & (-14337);
                if (i4 != 0) {
                    DefaultPermissionGrantPolicy.this.NO_PM_CACHE.updatePermissionFlags(this.mPermission, this.mPkgRequestingPerm, i4, -1, this.mUser);
                }
            }

            public void initFlags() {
                if (this.newFlags == null) {
                    Integer valueOf = Integer.valueOf(DefaultPermissionGrantPolicy.this.NO_PM_CACHE.getPermissionFlags(this.mPermission, this.mPkgRequestingPerm, this.mUser));
                    this.mOriginalFlags = valueOf;
                    this.newFlags = valueOf;
                }
            }

            public void initGranted() {
                if (this.newGranted == null) {
                    Boolean valueOf = Boolean.valueOf(DelayingPackageManagerCache.this.createContextAsUser(this.mUser).getPackageManager().checkPermission(this.mPermission, this.mPkgRequestingPerm.packageName) == 0);
                    this.mOriginalGranted = valueOf;
                    this.newGranted = valueOf;
                }
            }
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
}
