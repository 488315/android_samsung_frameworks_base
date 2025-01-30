package com.android.server.pm.permission;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.Context;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.SigningDetails;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.p005os.IInstalld;
import android.permission.IOnPermissionsChangeListener;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IntPair;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.PermissionThread;
import com.android.server.ServiceThread;
import com.android.server.SystemConfig;
import com.android.server.Watchdog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.ApexManager;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PmHook;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerServiceImpl;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.pkg.component.ComponentMutateUtils;
import com.android.server.pm.pkg.component.ParsedPermission;
import com.android.server.pm.pkg.component.ParsedPermissionGroup;
import com.android.server.pm.pkg.component.ParsedPermissionUtils;
import com.android.server.policy.PermissionPolicyInternal;
import com.android.server.policy.SoftRestrictedPermissionPolicy;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.PmLog;
import com.samsung.android.server.p025pm.permission.PermissionReconsiderUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class PermissionManagerServiceImpl implements PermissionManagerServiceInterface {
    public static final Map FULLER_PERMISSION_MAP;
    public static final List NEARBY_DEVICES_PERMISSIONS;
    public static final List NOTIFICATION_PERMISSIONS;
    public static final Set READ_MEDIA_AURAL_PERMISSIONS;
    public static final Set READ_MEDIA_VISUAL_PERMISSIONS;
    public static final List STORAGE_PERMISSIONS;
    public final ApexManager mApexManager;
    public final Context mContext;
    public final PermissionCallback mDefaultPermissionCallback;
    public final int[] mGlobalGids;
    public final Handler mHandler;
    public final HandlerThread mHandlerThread;
    public final SparseBooleanArray mHasNoDelayedPermBackup;
    public final boolean mIsLeanback;
    public final Object mLock;
    public final MetricsLogger mMetricsLogger;
    public final OnPermissionChangeListeners mOnPermissionChangeListeners;
    public final ArrayMap mPackageGrantedPermissions;
    public final PackageManagerInternal mPackageManagerInt;
    public PermissionControllerManager mPermissionControllerManager;
    public PermissionPolicyInternal mPermissionPolicyInternal;
    public final IPlatformCompat mPlatformCompat;
    public ArraySet mPrivappPermissionsViolations;
    public final ArraySet mPrivilegedPermissionAllowlistSourcePackageNames;
    public final PermissionRegistry mRegistry;
    public final ArrayList mRuntimePermissionStateChangedListeners;
    public final DevicePermissionState mState;
    public final SparseArray mSystemPermissions;
    public boolean mSystemReady;
    public final UserManagerInternal mUserManagerInt;
    public static final String LOG_TAG = PermissionManagerServiceImpl.class.getSimpleName();
    public static final long BACKUP_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60);
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    /* renamed from: -$$Nest$smkillUid, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m10009$$Nest$smkillUid(int i, int i2, String str) {
        killUid(i, i2, str);
    }

    static {
        ArrayList arrayList = new ArrayList();
        STORAGE_PERMISSIONS = arrayList;
        ArraySet arraySet = new ArraySet();
        READ_MEDIA_AURAL_PERMISSIONS = arraySet;
        ArraySet arraySet2 = new ArraySet();
        READ_MEDIA_VISUAL_PERMISSIONS = arraySet2;
        ArrayList arrayList2 = new ArrayList();
        NEARBY_DEVICES_PERMISSIONS = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        NOTIFICATION_PERMISSIONS = arrayList3;
        HashMap hashMap = new HashMap();
        FULLER_PERMISSION_MAP = hashMap;
        hashMap.put("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION");
        hashMap.put("android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_USERS_FULL");
        arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        arraySet.add("android.permission.READ_MEDIA_AUDIO");
        arraySet2.add("android.permission.READ_MEDIA_VIDEO");
        arraySet2.add("android.permission.READ_MEDIA_IMAGES");
        arraySet2.add("android.permission.ACCESS_MEDIA_LOCATION");
        arraySet2.add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        arrayList2.add("android.permission.BLUETOOTH_ADVERTISE");
        arrayList2.add("android.permission.BLUETOOTH_CONNECT");
        arrayList2.add("android.permission.BLUETOOTH_SCAN");
        arrayList3.add("android.permission.POST_NOTIFICATIONS");
    }

    /* renamed from: com.android.server.pm.permission.PermissionManagerServiceImpl$1 */
    public class C22261 extends PermissionCallback {
        public C22261() {
            super();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onGidsChanged(final int i, final int i2) {
            PermissionManagerServiceImpl.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl.m10009$$Nest$smkillUid(i, i2, "permission grant or revoke changed gids");
                }
            });
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionGranted(int i, int i2) {
            PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i);
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onInstallPermissionGranted() {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionRevoked(final int i, final int i2, final String str, boolean z, final String str2) {
            PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i);
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(false);
            if (z) {
                return;
            }
            PermissionManagerServiceImpl.this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl.C22261.this.lambda$onPermissionRevoked$1(str2, i, str, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPermissionRevoked$1(String str, int i, String str2, int i2) {
            if ("android.permission.POST_NOTIFICATIONS".equals(str) && isAppBackupAndRestoreRunning(i)) {
                return;
            }
            int appId = UserHandle.getAppId(i);
            if (str2 == null) {
                PermissionManagerServiceImpl.killUid(appId, i2, "permissions revoked");
            } else {
                PermissionManagerServiceImpl.killUid(appId, i2, str2);
            }
        }

        public final boolean isAppBackupAndRestoreRunning(int i) {
            if (PermissionManagerServiceImpl.this.checkUidPermission(i, "android.permission.BACKUP") != 0) {
                return false;
            }
            try {
                int userId = UserHandle.getUserId(i);
                return (Settings.Secure.getIntForUser(PermissionManagerServiceImpl.this.mContext.getContentResolver(), "user_setup_complete", userId) == 0) || (Settings.Secure.getIntForUser(PermissionManagerServiceImpl.this.mContext.getContentResolver(), "user_setup_personalization_state", userId) == 1);
            } catch (Settings.SettingNotFoundException e) {
                Slog.w(PermissionManagerServiceImpl.LOG_TAG, "Failed to check if the user is in restore: " + e);
                return false;
            }
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onInstallPermissionRevoked() {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionUpdated(int[] iArr, boolean z) {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writePermissionSettings(iArr, !z);
        }

        public void onInstallPermissionUpdated() {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionRemoved() {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(false);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionUpdatedNotifyListener(int[] iArr, boolean z, int i) {
            onPermissionUpdated(iArr, z);
            for (int i2 : iArr) {
                PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(UserHandle.getUid(i2, UserHandle.getAppId(i)));
            }
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onInstallPermissionUpdatedNotifyListener(int i) {
            onInstallPermissionUpdated();
            PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i);
        }
    }

    public PermissionManagerServiceImpl(Context context, ArrayMap arrayMap) {
        String str;
        ArraySet arraySet = new ArraySet();
        this.mPrivilegedPermissionAllowlistSourcePackageNames = arraySet;
        Object obj = new Object();
        this.mLock = obj;
        this.mState = new DevicePermissionState();
        this.mPackageGrantedPermissions = new ArrayMap();
        this.mMetricsLogger = new MetricsLogger();
        this.mPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        this.mRegistry = new PermissionRegistry();
        this.mHasNoDelayedPermBackup = new SparseBooleanArray();
        this.mRuntimePermissionStateChangedListeners = new ArrayList();
        this.mDefaultPermissionCallback = new C22261();
        PackageManager.invalidatePackageInfoCache();
        PermissionManager.disablePackageNamePermissionCache();
        this.mContext = context;
        this.mPackageManagerInt = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mUserManagerInt = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mIsLeanback = arrayMap.containsKey("android.software.leanback");
        this.mApexManager = ApexManager.getInstance();
        arraySet.add("android");
        if (arrayMap.containsKey("android.hardware.type.automotive") && (str = SystemProperties.get("ro.android.car.carservice.package", (String) null)) != null) {
            arraySet.add(str);
        }
        ServiceThread serviceThread = new ServiceThread("PermissionManager", 10, true);
        this.mHandlerThread = serviceThread;
        serviceThread.start();
        Handler handler = new Handler(serviceThread.getLooper());
        this.mHandler = handler;
        Watchdog.getInstance().addThread(handler);
        SystemConfig systemConfig = SystemConfig.getInstance();
        this.mSystemPermissions = systemConfig.getSystemPermissions();
        this.mGlobalGids = systemConfig.getGlobalGids();
        this.mOnPermissionChangeListeners = new OnPermissionChangeListeners(FgThread.get().getLooper());
        ArrayMap permissions = SystemConfig.getInstance().getPermissions();
        synchronized (obj) {
            for (int i = 0; i < permissions.size(); i++) {
                SystemConfig.PermissionEntry permissionEntry = (SystemConfig.PermissionEntry) permissions.valueAt(i);
                Permission permission = this.mRegistry.getPermission(permissionEntry.name);
                if (permission == null) {
                    permission = new Permission(permissionEntry.name, "android", 1);
                    this.mRegistry.addPermission(permission);
                }
                int[] iArr = permissionEntry.gids;
                if (iArr != null) {
                    permission.setGids(iArr, permissionEntry.perUser);
                }
            }
        }
    }

    public static void killUid(int i, int i2, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            IActivityManager service = ActivityManager.getService();
            if (service != null) {
                try {
                    service.killUidForPermissionChange(i, i2, str);
                } catch (RemoteException unused) {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String[] getAppOpPermissionPackagesInternal(String str) {
        synchronized (this.mLock) {
            ArraySet appOpPermissionPackages = this.mRegistry.getAppOpPermissionPackages(str);
            if (appOpPermissionPackages == null) {
                return EmptyArray.STRING;
            }
            return (String[]) appOpPermissionPackages.toArray(new String[0]);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getAllPermissionGroups(int i) {
        final int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            Iterator it = this.mRegistry.getPermissionGroups().iterator();
            while (it.hasNext()) {
                arrayList.add(PackageInfoUtils.generatePermissionGroupInfo((ParsedPermissionGroup) it.next(), i));
            }
        }
        final int userId = UserHandle.getUserId(callingUid);
        arrayList.removeIf(new Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getAllPermissionGroups$0;
                lambda$getAllPermissionGroups$0 = PermissionManagerServiceImpl.this.lambda$getAllPermissionGroups$0(callingUid, userId, (PermissionGroupInfo) obj);
                return lambda$getAllPermissionGroups$0;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getAllPermissionGroups$0(int i, int i2, PermissionGroupInfo permissionGroupInfo) {
        return this.mPackageManagerInt.filterAppAccess(permissionGroupInfo.packageName, i, i2, false);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        synchronized (this.mLock) {
            ParsedPermissionGroup permissionGroup = this.mRegistry.getPermissionGroup(str);
            if (permissionGroup == null) {
                return null;
            }
            PermissionGroupInfo generatePermissionGroupInfo = PackageInfoUtils.generatePermissionGroupInfo(permissionGroup, i);
            if (!this.mPackageManagerInt.filterAppAccess(generatePermissionGroupInfo.packageName, callingUid, UserHandle.getUserId(callingUid), false)) {
                return generatePermissionGroupInfo;
            }
            EventLog.writeEvent(1397638484, "186113473", Integer.valueOf(callingUid), str);
            return null;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public PermissionInfo getPermissionInfo(String str, int i, String str2) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        int permissionInfoCallingTargetSdkVersion = getPermissionInfoCallingTargetSdkVersion(this.mPackageManagerInt.getPackage(str2), callingUid);
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str);
            if (permission == null) {
                return null;
            }
            PermissionInfo generatePermissionInfo = permission.generatePermissionInfo(i, permissionInfoCallingTargetSdkVersion);
            if (!this.mPackageManagerInt.filterAppAccess(generatePermissionInfo.packageName, callingUid, UserHandle.getUserId(callingUid), false)) {
                return generatePermissionInfo;
            }
            EventLog.writeEvent(1397638484, "183122164", Integer.valueOf(callingUid), str);
            return null;
        }
    }

    public final int getPermissionInfoCallingTargetSdkVersion(AndroidPackage androidPackage, int i) {
        int appId = UserHandle.getAppId(i);
        if (appId == 0 || appId == 1000 || appId == 2000 || androidPackage == null) {
            return 10000;
        }
        return androidPackage.getTargetSdkVersion();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List queryPermissionsByGroup(String str, int i) {
        final int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(10);
        synchronized (this.mLock) {
            ParsedPermissionGroup permissionGroup = this.mRegistry.getPermissionGroup(str);
            if (str != null && permissionGroup == null) {
                return null;
            }
            for (Permission permission : this.mRegistry.getPermissions()) {
                if (Objects.equals(permission.getGroup(), str)) {
                    arrayList.add(permission.generatePermissionInfo(i));
                }
            }
            final int userId = UserHandle.getUserId(callingUid);
            if (permissionGroup != null && this.mPackageManagerInt.filterAppAccess(permissionGroup.getPackageName(), callingUid, userId, false)) {
                return null;
            }
            arrayList.removeIf(new Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$queryPermissionsByGroup$1;
                    lambda$queryPermissionsByGroup$1 = PermissionManagerServiceImpl.this.lambda$queryPermissionsByGroup$1(callingUid, userId, (PermissionInfo) obj);
                    return lambda$queryPermissionsByGroup$1;
                }
            });
            return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$queryPermissionsByGroup$1(int i, int i2, PermissionInfo permissionInfo) {
        return this.mPackageManagerInt.filterAppAccess(permissionInfo.packageName, i, i2, false);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addPermission(PermissionInfo permissionInfo, boolean z) {
        boolean z2;
        boolean addToTree;
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            throw new SecurityException("Instant apps can't add permissions");
        }
        if (permissionInfo.labelRes == 0 && permissionInfo.nonLocalizedLabel == null) {
            throw new SecurityException("Label must be specified in permission");
        }
        synchronized (this.mLock) {
            Permission enforcePermissionTree = this.mRegistry.enforcePermissionTree(permissionInfo.name, callingUid);
            Permission permission = this.mRegistry.getPermission(permissionInfo.name);
            z2 = permission == null;
            int fixProtectionLevel = PermissionInfo.fixProtectionLevel(permissionInfo.protectionLevel);
            enforcePermissionCapLocked(permissionInfo, enforcePermissionTree);
            if (z2) {
                permission = new Permission(permissionInfo.name, enforcePermissionTree.getPackageName(), 2);
            } else if (!permission.isDynamic()) {
                throw new SecurityException("Not allowed to modify non-dynamic permission " + permissionInfo.name);
            }
            addToTree = permission.addToTree(fixProtectionLevel, permissionInfo, enforcePermissionTree);
            if (z2) {
                this.mRegistry.addPermission(permission);
            }
        }
        if (addToTree) {
            this.mPackageManagerInt.writeSettings(z);
        }
        return z2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            throw new SecurityException("Instant applications don't have access to this method");
        }
        synchronized (this.mLock) {
            this.mRegistry.enforcePermissionTree(str, callingUid);
            Permission permission = this.mRegistry.getPermission(str);
            if (permission == null) {
                return;
            }
            if (!permission.isDynamic()) {
                Slog.wtf("PermissionManager", "Not allowed to modify non-dynamic permission " + str);
                return;
            }
            this.mRegistry.removePermission(str);
            this.mPackageManagerInt.writeSettings(false);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int getPermissionFlags(String str, String str2, int i) {
        return getPermissionFlagsInternal(str, str2, Binder.getCallingUid(), i);
    }

    public final int getPermissionFlagsInternal(String str, String str2, int i, int i2) {
        if (!this.mUserManagerInt.exists(i2)) {
            return 0;
        }
        enforceGrantRevokeGetRuntimePermissionPermissions("getPermissionFlags");
        enforceCrossUserPermission(i, i2, true, false, "getPermissionFlags");
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null || this.mPackageManagerInt.filterAppAccess(str, i, i2, false)) {
            return 0;
        }
        synchronized (this.mLock) {
            if (this.mRegistry.getPermission(str2) == null) {
                return 0;
            }
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + str + " and user " + i2);
                return 0;
            }
            return uidStateLocked.getPermissionFlags(str2);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, int i3) {
        boolean z2;
        int callingUid = Binder.getCallingUid();
        if ("android.permission.POST_NOTIFICATIONS".equals(str2) && (i & 32) != 0 && (i2 & 32) == 0) {
            int callingPid = Binder.getCallingPid();
            if (callingPid == Process.myPid()) {
                Slog.i("PermissionManager", "System try to remove GRANTED_BY_DEFAULT flag for " + str + " POST_NOTIFICATIONS Callers=" + Debug.getCallers(5));
            } else {
                Slog.i("PermissionManager", "Pid: " + callingPid + ", Uid: " + callingUid + " try to remove GRANTED_BY_DEFAULT flag for " + str + " POST_NOTIFICATIONS");
            }
        }
        if (callingUid == 1000 || callingUid == 0 || (i & 4) == 0) {
            z2 = false;
        } else {
            if (z) {
                this.mContext.enforceCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Need android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY to change policy flags");
            } else if (this.mPackageManagerInt.getUidTargetSdkVersion(callingUid) >= 29) {
                throw new IllegalArgumentException("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY needs  to be checked for packages targeting 29 or later when changing policy flags");
            }
            z2 = true;
        }
        updatePermissionFlagsInternal(str, str2, i, i2, callingUid, i3, z2, this.mDefaultPermissionCallback);
    }

    public final void updatePermissionFlagsInternal(String str, String str2, int i, int i2, int i3, int i4, boolean z, PermissionCallback permissionCallback) {
        if (this.mUserManagerInt.exists(i4)) {
            enforceGrantRevokeRuntimePermissionPermissions("updatePermissionFlags");
            enforceCrossUserPermission(i3, i4, true, true, "updatePermissionFlags");
            if ((i & 4) != 0 && !z) {
                throw new SecurityException("updatePermissionFlags requires android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
            }
            if (i3 != 1000) {
                i = i & (-17) & (-33);
                i2 = i2 & (-17) & (-33) & (-4097) & (-2049) & (-8193) & (-16385);
                if (!"android.permission.POST_NOTIFICATIONS".equals(str2) && i3 != 2000 && i3 != 0) {
                    i2 &= -65;
                }
            }
            AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
            if (androidPackage == null) {
                Log.e("PermissionManager", "Unknown package: " + str);
                return;
            }
            if (this.mPackageManagerInt.filterAppAccess(str, i3, i4, false)) {
                throw new IllegalArgumentException("Unknown package: " + str);
            }
            boolean contains = androidPackage.getRequestedPermissions().contains(str2);
            if (!contains) {
                String[] sharedUserPackagesForPackage = this.mPackageManagerInt.getSharedUserPackagesForPackage(str, i4);
                int length = sharedUserPackagesForPackage.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        break;
                    }
                    AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(sharedUserPackagesForPackage[i5]);
                    if (androidPackage2 != null && androidPackage2.getRequestedPermissions().contains(str2)) {
                        contains = true;
                        break;
                    }
                    i5++;
                }
            }
            synchronized (this.mLock) {
                Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    throw new IllegalArgumentException("Unknown permission: " + str2);
                }
                boolean isRuntime = permission.isRuntime();
                UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i4);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for " + str + " and user " + i4);
                    return;
                }
                if (!uidStateLocked.hasPermissionState(str2) && !contains) {
                    Log.e("PermissionManager", "Permission " + str2 + " isn't requested by package " + str);
                    return;
                }
                boolean updatePermissionFlags = uidStateLocked.updatePermissionFlags(permission, i, i2);
                if (updatePermissionFlags && isRuntime) {
                    notifyRuntimePermissionStateChanged(str, i4);
                }
                if (!updatePermissionFlags || permissionCallback == null) {
                    return;
                }
                if (!isRuntime) {
                    permissionCallback.onInstallPermissionUpdatedNotifyListener(UserHandle.getUid(i4, androidPackage.getUid()));
                } else {
                    permissionCallback.onPermissionUpdatedNotifyListener(new int[]{i4}, false, androidPackage.getUid());
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlagsForAllApps(int i, int i2, final int i3) {
        int callingUid = Binder.getCallingUid();
        if (this.mUserManagerInt.exists(i3)) {
            enforceGrantRevokeRuntimePermissionPermissions("updatePermissionFlagsForAllApps");
            enforceCrossUserPermission(callingUid, i3, true, true, "updatePermissionFlagsForAllApps");
            final int i4 = callingUid != 1000 ? i : i & (-17);
            final int i5 = callingUid != 1000 ? i2 : i2 & (-17);
            final boolean[] zArr = new boolean[1];
            this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerServiceImpl.this.lambda$updatePermissionFlagsForAllApps$2(i3, zArr, i4, i5, (AndroidPackage) obj);
                }
            });
            if (zArr[0]) {
                this.mPackageManagerInt.writePermissionSettings(new int[]{i3}, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissionFlagsForAllApps$2(int i, boolean[] zArr, int i2, int i3, AndroidPackage androidPackage) {
        synchronized (this.mLock) {
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                return;
            }
            zArr[0] = uidStateLocked.updatePermissionFlagsForAllPermissions(i2, i3) | zArr[0];
            this.mOnPermissionChangeListeners.onPermissionsChanged(androidPackage.getUid());
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkPermission(String str, String str2, int i) {
        AndroidPackage androidPackage;
        if (this.mUserManagerInt.exists(i) && (androidPackage = this.mPackageManagerInt.getPackage(str)) != null) {
            return checkPermissionInternal(androidPackage, true, str2, i);
        }
        return -1;
    }

    public final int checkPermissionInternal(AndroidPackage androidPackage, boolean z, String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (z || androidPackage.getSharedUserId() == null) {
            if (this.mPackageManagerInt.filterAppAccess(androidPackage.getPackageName(), callingUid, i, false)) {
                return -1;
            }
        } else if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return -1;
        }
        boolean z2 = this.mPackageManagerInt.getInstantAppPackageName(UserHandle.getUid(i, androidPackage.getUid())) != null;
        synchronized (this.mLock) {
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                return -1;
            }
            if (checkSinglePermissionInternalLocked(uidStateLocked, str, z2)) {
                return 0;
            }
            String str2 = (String) FULLER_PERMISSION_MAP.get(str);
            return (str2 == null || !checkSinglePermissionInternalLocked(uidStateLocked, str2, z2)) ? -1 : 0;
        }
    }

    public final boolean checkSinglePermissionInternalLocked(UidPermissionState uidPermissionState, String str, boolean z) {
        if (!uidPermissionState.isPermissionGranted(str)) {
            return false;
        }
        if (!z) {
            return true;
        }
        Permission permission = this.mRegistry.getPermission(str);
        return permission != null && permission.isInstant();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkUidPermission(int i, String str) {
        if (this.mUserManagerInt.exists(UserHandle.getUserId(i))) {
            return checkUidPermissionInternal(this.mPackageManagerInt.getPackage(i), i, str);
        }
        return -1;
    }

    public final int checkUidPermissionInternal(AndroidPackage androidPackage, int i, String str) {
        if (androidPackage != null) {
            return checkPermissionInternal(androidPackage, false, str, UserHandle.getUserId(i));
        }
        synchronized (this.mLock) {
            if (checkSingleUidPermissionInternalLocked(i, str)) {
                return 0;
            }
            String str2 = (String) FULLER_PERMISSION_MAP.get(str);
            return (str2 == null || !checkSingleUidPermissionInternalLocked(i, str2)) ? -1 : 0;
        }
    }

    public final boolean checkSingleUidPermissionInternalLocked(int i, String str) {
        ArraySet arraySet = (ArraySet) this.mSystemPermissions.get(i);
        return arraySet != null && arraySet.contains(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS", "addOnPermissionsChangeListener");
        this.mOnPermissionChangeListeners.addListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        if (this.mPackageManagerInt.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            throw new SecurityException("Instant applications don't have access to this method");
        }
        this.mOnPermissionChangeListeners.removeListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getAllowlistedRestrictedPermissions(String str, int i, int i2) {
        Objects.requireNonNull(str);
        Preconditions.checkFlagsArgument(i, 7);
        Preconditions.checkArgumentNonNegative(i2, (String) null);
        if (UserHandle.getCallingUserId() != i2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "getAllowlistedRestrictedPermissions for user " + i2);
        }
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(str, callingUid, UserHandle.getCallingUserId(), false)) {
            return null;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = this.mPackageManagerInt.isCallerInstallerOfRecord(androidPackage, callingUid);
        if ((i & 1) != 0 && !z) {
            throw new SecurityException("Querying system allowlist requires android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        if ((i & 6) != 0 && !z && !isCallerInstallerOfRecord) {
            throw new SecurityException("Querying upgrade or installer allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getAllowlistedRestrictedPermissionsInternal(androidPackage, i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getAllowlistedRestrictedPermissionsInternal(AndroidPackage androidPackage, int i, int i2) {
        synchronized (this.mLock) {
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
            ArrayList arrayList = null;
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                return null;
            }
            int i3 = (i & 1) != 0 ? IInstalld.FLAG_USE_QUOTA : 0;
            if ((i & 4) != 0) {
                i3 |= IInstalld.FLAG_FORCE;
            }
            if ((i & 2) != 0) {
                i3 |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
            }
            int size = ArrayUtils.size(androidPackage.getRequestedPermissions());
            for (int i4 = 0; i4 < size; i4++) {
                String str = (String) androidPackage.getRequestedPermissions().get(i4);
                if ((uidStateLocked.getPermissionFlags(str) & i3) != 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(str);
                }
            }
            return arrayList;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        Objects.requireNonNull(str2);
        if (!checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(str2)) {
            return false;
        }
        List allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(str, i, i2);
        if (allowlistedRestrictedPermissions == null) {
            allowlistedRestrictedPermissions = new ArrayList(1);
        }
        if (allowlistedRestrictedPermissions.indexOf(str2) >= 0) {
            return false;
        }
        allowlistedRestrictedPermissions.add(str2);
        return setAllowlistedRestrictedPermissions(str, allowlistedRestrictedPermissions, i, i2);
    }

    public final boolean checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(String str) {
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str);
            if (permission == null) {
                Slog.w("PermissionManager", "No such permissions: " + str);
                return false;
            }
            String packageName = permission.getPackageName();
            boolean z = permission.isHardOrSoftRestricted() && permission.isImmutablyRestricted();
            int callingUid = Binder.getCallingUid();
            if (this.mPackageManagerInt.filterAppAccess(packageName, callingUid, UserHandle.getUserId(callingUid), false)) {
                EventLog.writeEvent(1397638484, "186404356", Integer.valueOf(callingUid), str);
                return false;
            }
            if (!z || this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0) {
                return true;
            }
            throw new SecurityException("Cannot modify allowlisting of an immutably restricted permission: " + str);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        List allowlistedRestrictedPermissions;
        Objects.requireNonNull(str2);
        if (checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(str2) && (allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(str, i, i2)) != null && allowlistedRestrictedPermissions.remove(str2)) {
            return setAllowlistedRestrictedPermissions(str, allowlistedRestrictedPermissions, i, i2);
        }
        return false;
    }

    public final boolean setAllowlistedRestrictedPermissions(String str, List list, int i, int i2) {
        Objects.requireNonNull(str);
        Preconditions.checkFlagsArgument(i, 7);
        Preconditions.checkArgument(Integer.bitCount(i) == 1);
        Preconditions.checkArgumentNonNegative(i2, (String) null);
        if (UserHandle.getCallingUserId() != i2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "setAllowlistedRestrictedPermissions for user " + i2);
        }
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(str, callingUid, UserHandle.getCallingUserId(), false)) {
            return false;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = this.mPackageManagerInt.isCallerInstallerOfRecord(androidPackage, callingUid);
        if ((i & 1) != 0 && !z) {
            throw new SecurityException("Modifying system allowlist requires android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        if ((i & 4) != 0) {
            if (!z && !isCallerInstallerOfRecord) {
                throw new SecurityException("Modifying upgrade allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
            }
            List allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(androidPackage.getPackageName(), i, i2);
            if (list == null || list.isEmpty()) {
                if (allowlistedRestrictedPermissions == null || allowlistedRestrictedPermissions.isEmpty()) {
                    return true;
                }
            } else {
                int size = list.size();
                for (int i3 = 0; i3 < size; i3++) {
                    if ((allowlistedRestrictedPermissions == null || !allowlistedRestrictedPermissions.contains(list.get(i3))) && !z) {
                        throw new SecurityException("Adding to upgrade allowlist requiresandroid.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                    }
                }
            }
        }
        if ((i & 2) != 0 && !z && !isCallerInstallerOfRecord) {
            throw new SecurityException("Modifying installer allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            setAllowlistedRestrictedPermissionsInternal(androidPackage, list, i, i2);
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantRuntimePermission(String str, String str2, int i) {
        int callingUid = Binder.getCallingUid();
        grantRuntimePermissionInternal(str, str2, checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") == 0, callingUid, i, this.mDefaultPermissionCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void grantRuntimePermissionInternal(String str, String str2, boolean z, int i, int i2, PermissionCallback permissionCallback) {
        boolean isRole;
        boolean isSoftRestricted;
        if (!this.mUserManagerInt.exists(i2)) {
            Log.e("PermissionManager", "No such user:" + i2);
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS", "grantRuntimePermission");
        enforceCrossUserPermission(i, i2, true, true, "grantRuntimePermission");
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (androidPackage == null || packageStateInternal == null) {
            Log.e("PermissionManager", "Unknown package: " + str);
            return;
        }
        boolean z2 = false;
        if (this.mPackageManagerInt.filterAppAccess(str, i, i2, false)) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str2);
            if (permission == null) {
                throw new IllegalArgumentException("Unknown permission: " + str2);
            }
            isRole = permission.isRole();
            isSoftRestricted = permission.isSoftRestricted();
        }
        boolean z3 = isRole && mayManageRolePermission(i);
        if (isSoftRestricted && SoftRestrictedPermissionPolicy.forPermission(this.mContext, AndroidPackageUtils.generateAppInfoWithoutState(androidPackage), androidPackage, UserHandle.of(i2), str2).mayGrantPermission()) {
            z2 = true;
        }
        synchronized (this.mLock) {
            Permission permission2 = this.mRegistry.getPermission(str2);
            if (permission2 == null) {
                throw new IllegalArgumentException("Unknown permission: " + str2);
            }
            boolean isRuntime = permission2.isRuntime();
            boolean hasGids = permission2.hasGids();
            if (!isRuntime && !permission2.isDevelopment()) {
                if (!permission2.isRole()) {
                    throw new SecurityException("Permission " + str2 + " requested by " + androidPackage.getPackageName() + " is not a changeable permission type");
                }
                if (!z3) {
                    throw new SecurityException("Permission " + str2 + " is managed by role");
                }
            }
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                return;
            }
            if (!uidStateLocked.hasPermissionState(str2) && !androidPackage.getRequestedPermissions().contains(str2)) {
                throw new SecurityException("Package " + androidPackage.getPackageName() + " has not requested permission " + str2);
            }
            if (androidPackage.getTargetSdkVersion() < 23 && permission2.isRuntime()) {
                return;
            }
            int permissionFlags = uidStateLocked.getPermissionFlags(str2);
            if ((permissionFlags & 16) != 0) {
                Log.e("PermissionManager", "Cannot grant system fixed permission " + str2 + " for package " + str);
                return;
            }
            if (!z && (permissionFlags & 4) != 0) {
                Log.e("PermissionManager", "Cannot grant policy fixed permission " + str2 + " for package " + str);
                return;
            }
            if (permission2.isHardRestricted() && (permissionFlags & 14336) == 0) {
                Log.e("PermissionManager", "Cannot grant hard restricted non-exempt permission " + str2 + " for package " + str);
                return;
            }
            if (permission2.isSoftRestricted() && !z2) {
                Log.e("PermissionManager", "Cannot grant soft restricted permission " + str2 + " for package " + str);
                return;
            }
            if (!permission2.isDevelopment() && !permission2.isRole()) {
                if (packageStateInternal.getUserStateOrDefault(i2).isInstantApp() && !permission2.isInstant()) {
                    throw new SecurityException("Cannot grant non-ephemeral permission " + str2 + " for package " + str);
                }
                if (androidPackage.getTargetSdkVersion() < 23) {
                    Slog.w("PermissionManager", "Cannot grant runtime permission to a legacy app");
                    return;
                }
                if (!uidStateLocked.grantPermission(permission2)) {
                    return;
                }
                if (isRuntime) {
                    logPermission(1243, str2, str);
                }
                int uid = UserHandle.getUid(i2, androidPackage.getUid());
                if (permissionCallback != null) {
                    if (isRuntime) {
                        permissionCallback.onPermissionGranted(uid, i2);
                    } else {
                        permissionCallback.onInstallPermissionGranted();
                    }
                    if (hasGids) {
                        permissionCallback.onGidsChanged(UserHandle.getAppId(androidPackage.getUid()), i2);
                    }
                }
                if (isRuntime) {
                    return;
                }
                notifyRuntimePermissionStateChanged(str, i2);
                return;
            }
            if (!uidStateLocked.grantPermission(permission2)) {
                return;
            }
            if (isRuntime) {
            }
            int uid2 = UserHandle.getUid(i2, androidPackage.getUid());
            if (permissionCallback != null) {
            }
            if (isRuntime) {
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeRuntimePermission(String str, String str2, int i, String str3) {
        int callingUid = Binder.getCallingUid();
        revokeRuntimePermissionInternal(str, str2, checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") == 0, callingUid, i, str3, this.mDefaultPermissionCallback);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokePostNotificationPermissionWithoutKillForTest(String str, int i) {
        int callingUid = Binder.getCallingUid();
        boolean z = checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") == 0;
        this.mContext.enforceCallingPermission("android.permission.REVOKE_POST_NOTIFICATIONS_WITHOUT_KILL", "");
        revokeRuntimePermissionInternal(str, "android.permission.POST_NOTIFICATIONS", z, true, callingUid, i, "skip permission revoke app kill for notification test", this.mDefaultPermissionCallback);
    }

    public final void revokeRuntimePermissionInternal(String str, String str2, boolean z, int i, int i2, String str3, PermissionCallback permissionCallback) {
        revokeRuntimePermissionInternal(str, str2, z, false, i, i2, str3, permissionCallback);
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0204, code lost:
    
        if ((r6 & 4) != 0) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0225, code lost:
    
        throw new java.lang.SecurityException("Cannot revoke policy fixed permission " + r18 + " for package " + r17);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void revokeRuntimePermissionInternal(String str, String str2, boolean z, boolean z2, int i, int i2, String str3, PermissionCallback permissionCallback) {
        boolean isRole;
        boolean z3 = true;
        if ("android.permission.POST_NOTIFICATIONS".equals(str2)) {
            final StringBuilder sb = new StringBuilder("Revoke " + str2 + ", pkg: " + str + ", userId: " + i2 + ", reason: " + str3 + ", callingUid: " + i);
            if (Binder.getCallingPid() == Process.myPid()) {
                sb.append("\nstackTrace = " + Debug.getCallers(1, 7));
            }
            BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl.lambda$revokeRuntimePermissionInternal$3(sb);
                }
            });
        }
        if (!this.mUserManagerInt.exists(i2)) {
            Log.e("PermissionManager", "No such user:" + i2);
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", "revokeRuntimePermission");
        enforceCrossUserPermission(i, i2, true, true, "revokeRuntimePermission");
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            Log.e("PermissionManager", "Unknown package: " + str);
            return;
        }
        if (this.mPackageManagerInt.filterAppAccess(str, i, i2, false)) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str2);
            if (permission == null) {
                throw new IllegalArgumentException("Unknown permission: " + str2);
            }
            isRole = permission.isRole();
        }
        boolean z4 = isRole && (i == Process.myUid() || mayManageRolePermission(i));
        synchronized (this.mLock) {
            Permission permission2 = this.mRegistry.getPermission(str2);
            if (permission2 == null) {
                throw new IllegalArgumentException("Unknown permission: " + str2);
            }
            boolean isRuntime = permission2.isRuntime();
            if (!isRuntime && !permission2.isDevelopment()) {
                if (!permission2.isRole()) {
                    throw new SecurityException("Permission " + str2 + " requested by " + androidPackage.getPackageName() + " is not a changeable permission type");
                }
                if (!z4) {
                    throw new SecurityException("Permission " + str2 + " is managed by role");
                }
            }
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                return;
            }
            if (!uidStateLocked.hasPermissionState(str2) && !androidPackage.getRequestedPermissions().contains(str2)) {
                throw new SecurityException("Package " + androidPackage.getPackageName() + " has not requested permission " + str2);
            }
            if (androidPackage.getTargetSdkVersion() >= 23 || !permission2.isRuntime()) {
                int permissionFlags = uidStateLocked.getPermissionFlags(str2);
                int i3 = permissionFlags & 16;
                if (i3 != 0 && UserHandle.getCallingAppId() != 1000) {
                    throw new SecurityException("Non-System UID cannot revoke system fixed permission " + str2 + " for package " + str);
                }
                if (uidStateLocked.revokePermission(permission2)) {
                    if (i3 == 0) {
                        z3 = false;
                    }
                    if (isRuntime) {
                        if (z3 && (i2 == -1 || i2 == 0)) {
                            PmLog.logCriticalInfoAndLogcat(str2 + " system_fixed permission revoked for package " + str + ", pid: " + Binder.getCallingPid() + ", uid: " + Binder.getCallingUid() + ", " + Log.getStackTraceString(new Exception("revoke was called by")));
                        }
                        logPermission(1245, str2, str);
                    }
                    if (permissionCallback != null) {
                        if (isRuntime) {
                            permissionCallback.onPermissionRevoked(UserHandle.getUid(i2, androidPackage.getUid()), i2, str3, z2, str2);
                        } else {
                            this.mDefaultPermissionCallback.onInstallPermissionRevoked();
                        }
                    }
                    if (isRuntime) {
                        notifyRuntimePermissionStateChanged(str, i2);
                    }
                }
            }
        }
    }

    public static /* synthetic */ void lambda$revokeRuntimePermissionInternal$3(StringBuilder sb) {
        PmLog.logDebugInfoAndLogcat(sb.toString(), "PermissionManager");
    }

    public final boolean mayManageRolePermission(int i) {
        PackageManager packageManager = this.mContext.getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        return Arrays.asList(packagesForUid).contains(packageManager.getPermissionControllerPackageName());
    }

    public final void resetRuntimePermissionsInternal(AndroidPackage androidPackage, final int i) {
        final boolean[] zArr = new boolean[1];
        final ArraySet arraySet = new ArraySet();
        final ArraySet arraySet2 = new ArraySet();
        final ArraySet arraySet3 = new ArraySet();
        final PermissionCallback permissionCallback = new PermissionCallback() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onGidsChanged(int i2, int i3) {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onGidsChanged(i2, i3);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionGranted(int i2, int i3) {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onPermissionGranted(i2, i3);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onInstallPermissionGranted() {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionGranted();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionRevoked(int i2, int i3, String str, boolean z, String str2) {
                arraySet.add(Long.valueOf(IntPair.of(i2, i3)));
                arraySet2.add(Integer.valueOf(i3));
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onInstallPermissionRevoked() {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionRevoked();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionUpdated(int[] iArr, boolean z) {
                for (int i2 : iArr) {
                    if (z) {
                        arraySet2.add(Integer.valueOf(i2));
                        arraySet3.remove(Integer.valueOf(i2));
                    } else if (arraySet2.indexOf(Integer.valueOf(i2)) == -1) {
                        arraySet3.add(Integer.valueOf(i2));
                    }
                }
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionRemoved() {
                zArr[0] = true;
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionUpdatedNotifyListener(int[] iArr, boolean z, int i2) {
                onPermissionUpdated(iArr, z);
                PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i2);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onInstallPermissionUpdatedNotifyListener(int i2) {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionUpdatedNotifyListener(i2);
            }
        };
        if (androidPackage != null) {
            lambda$resetRuntimePermissionsInternal$4(androidPackage, i, permissionCallback);
        } else {
            this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerServiceImpl.this.lambda$resetRuntimePermissionsInternal$4(i, permissionCallback, (AndroidPackage) obj);
                }
            });
        }
        if (zArr[0]) {
            this.mDefaultPermissionCallback.onPermissionRemoved();
        }
        if (!arraySet.isEmpty()) {
            int size = arraySet.size();
            for (int i2 = 0; i2 < size; i2++) {
                final int first = IntPair.first(((Long) arraySet.valueAt(i2)).longValue());
                final int second = IntPair.second(((Long) arraySet.valueAt(i2)).longValue());
                this.mOnPermissionChangeListeners.onPermissionsChanged(first);
                this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        PermissionManagerServiceImpl.lambda$resetRuntimePermissionsInternal$5(first, second);
                    }
                });
            }
        }
        this.mPackageManagerInt.writePermissionSettings(ArrayUtils.convertToIntArray(arraySet2), false);
        this.mPackageManagerInt.writePermissionSettings(ArrayUtils.convertToIntArray(arraySet3), true);
    }

    public static /* synthetic */ void lambda$resetRuntimePermissionsInternal$5(int i, int i2) {
        killUid(UserHandle.getAppId(i), i2, "permissions revoked");
    }

    /* renamed from: resetRuntimePermissionsInternal, reason: merged with bridge method [inline-methods] */
    public final void lambda$resetRuntimePermissionsInternal$4(AndroidPackage androidPackage, int i, PermissionCallback permissionCallback) {
        boolean z;
        String packageName = androidPackage.getPackageName();
        int size = ArrayUtils.size(androidPackage.getRequestedPermissions());
        for (int i2 = 0; i2 < size; i2++) {
            String str = (String) androidPackage.getRequestedPermissions().get(i2);
            synchronized (this.mLock) {
                Permission permission = this.mRegistry.getPermission(str);
                if (permission != null) {
                    if (!permission.isRemoved()) {
                        boolean isRuntime = permission.isRuntime();
                        String[] sharedUserPackagesForPackage = this.mPackageManagerInt.getSharedUserPackagesForPackage(androidPackage.getPackageName(), i);
                        if (sharedUserPackagesForPackage.length > 0) {
                            int length = sharedUserPackagesForPackage.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length) {
                                    z = false;
                                    break;
                                }
                                AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(sharedUserPackagesForPackage[i3]);
                                if (androidPackage2 != null && !androidPackage2.getPackageName().equals(packageName) && androidPackage2.getRequestedPermissions().contains(str)) {
                                    z = true;
                                    break;
                                }
                                i3++;
                            }
                            if (z) {
                            }
                        }
                        int permissionFlagsInternal = getPermissionFlagsInternal(packageName, str, 1000, i);
                        int uidTargetSdkVersion = this.mPackageManagerInt.getUidTargetSdkVersion(this.mPackageManagerInt.getPackageUid(packageName, 0L, i));
                        int i4 = (uidTargetSdkVersion >= 23 || !isRuntime) ? 0 : 72;
                        updatePermissionFlagsInternal(packageName, str, 589899, i4, 1000, i, false, permissionCallback);
                        if (isRuntime && (permissionFlagsInternal & 20) == 0) {
                            if ((permissionFlagsInternal & 32) != 0 || (permissionFlagsInternal & 32768) != 0) {
                                grantRuntimePermissionInternal(packageName, str, false, 1000, i, permissionCallback);
                            } else if ((i4 & 64) == 0 && !isPermissionSplitFromNonRuntime(str, uidTargetSdkVersion)) {
                                revokeRuntimePermissionInternal(packageName, str, false, 1000, i, null, permissionCallback);
                            }
                        }
                    }
                }
            }
        }
    }

    public final boolean isPermissionSplitFromNonRuntime(String str, int i) {
        List splitPermissionInfos = getSplitPermissionInfos();
        int size = splitPermissionInfos.size();
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo = (PermissionManager.SplitPermissionInfo) splitPermissionInfos.get(i2);
            if (i < splitPermissionInfo.getTargetSdk() && splitPermissionInfo.getNewPermissions().contains(str)) {
                synchronized (this.mLock) {
                    Permission permission = this.mRegistry.getPermission(splitPermissionInfo.getSplitPermission());
                    if (permission != null && !permission.isRuntime()) {
                        z = true;
                    }
                }
                return z;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean shouldShowRequestPermissionRationale(String str, String str2, int i) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "canShowRequestPermissionRationale for user " + i);
        }
        if (UserHandle.getAppId(callingUid) != UserHandle.getAppId(this.mPackageManagerInt.getPackageUid(str, 268435456L, i)) || checkPermission(str, str2, i) == 0) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int permissionFlagsInternal = getPermissionFlagsInternal(str, str2, callingUid, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if ((permissionFlagsInternal & 22) != 0) {
                return false;
            }
            synchronized (this.mLock) {
                Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    return false;
                }
                if (permission.isHardRestricted() && (permissionFlagsInternal & 14336) == 0) {
                    return false;
                }
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (str2.equals("android.permission.ACCESS_BACKGROUND_LOCATION")) {
                            if (this.mPlatformCompat.isChangeEnabledByPackageName(147316723L, str, i)) {
                                return true;
                            }
                        }
                    } catch (RemoteException e) {
                        Log.e("PermissionManager", "Unable to check if compatibility change is enabled.", e);
                    }
                    return (permissionFlagsInternal & 1) != 0;
                } finally {
                }
            }
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionRevokedByPolicy(String str, String str2, int i) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "isPermissionRevokedByPolicy for user " + i);
        }
        if (checkPermission(str, str2, i) == 0) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(str, callingUid, i, false)) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (getPermissionFlagsInternal(str, str2, callingUid, i) & 4) != 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public byte[] backupRuntimePermissions(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        final CompletableFuture completableFuture = new CompletableFuture();
        this.mPermissionControllerManager.getRuntimePermissionBackup(UserHandle.of(i), PermissionThread.getExecutor(), new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                completableFuture.complete((byte[]) obj);
            }
        });
        try {
            return (byte[]) completableFuture.get(BACKUP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Slog.e("PermissionManager", "Cannot create permission backup for user " + i, e);
            return null;
        }
    }

    public void restoreRuntimePermissions(byte[] bArr, int i) {
        Objects.requireNonNull(bArr, "backup");
        Preconditions.checkArgumentNonNegative(i, "userId");
        synchronized (this.mLock) {
            this.mHasNoDelayedPermBackup.delete(i);
        }
        this.mPermissionControllerManager.stageAndApplyRuntimePermissionsBackup(bArr, UserHandle.of(i));
    }

    public void restoreDelayedRuntimePermissions(String str, final int i) {
        Objects.requireNonNull(str, "packageName");
        Preconditions.checkArgumentNonNegative(i, "userId");
        synchronized (this.mLock) {
            if (this.mHasNoDelayedPermBackup.get(i, false)) {
                return;
            }
            this.mPermissionControllerManager.applyStagedRuntimePermissionBackup(str, UserHandle.of(i), PermissionThread.getExecutor(), new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerServiceImpl.this.lambda$restoreDelayedRuntimePermissions$6(i, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreDelayedRuntimePermissions$6(int i, Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        synchronized (this.mLock) {
            this.mHasNoDelayedPermBackup.put(i, true);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnRuntimePermissionStateChangedListener(PermissionManagerServiceInternal.OnRuntimePermissionStateChangedListener onRuntimePermissionStateChangedListener) {
        synchronized (this.mLock) {
            this.mRuntimePermissionStateChangedListeners.add(onRuntimePermissionStateChangedListener);
        }
    }

    public final void notifyRuntimePermissionStateChanged(String str, int i) {
        FgThread.getHandler().sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda6
            public final void accept(Object obj, Object obj2, Object obj3) {
                ((PermissionManagerServiceImpl) obj).doNotifyRuntimePermissionStateChanged((String) obj2, ((Integer) obj3).intValue());
            }
        }, this, str, Integer.valueOf(i)));
    }

    public final void doNotifyRuntimePermissionStateChanged(String str, int i) {
        synchronized (this.mLock) {
            if (this.mRuntimePermissionStateChangedListeners.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList(this.mRuntimePermissionStateChangedListeners);
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((PermissionManagerServiceInternal.OnRuntimePermissionStateChangedListener) arrayList.get(i2)).onRuntimePermissionStateChanged(str, i);
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getRequestedRuntimePermissionsForMDM(String str) {
        List requestedRuntimePermissionsLocked;
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            return new ArrayList();
        }
        synchronized (this.mLock) {
            requestedRuntimePermissionsLocked = getRequestedRuntimePermissionsLocked(androidPackage, null);
        }
        return requestedRuntimePermissionsLocked;
    }

    public final boolean updatePermissionStatesAndFlagsInternalForMDM(String str, List list, int i, int i2, int i3, int i4, boolean z) {
        boolean z2;
        UidPermissionState uidPermissionState;
        Permission permission;
        boolean z3;
        int i5 = 1;
        try {
            Iterator it = getPackagesToUpdate(str, str == null).iterator();
            boolean z4 = true;
            while (it.hasNext()) {
                try {
                    AndroidPackage androidPackage = ((PackageStateInternal) it.next()).getAndroidPackage();
                    Objects.requireNonNull(androidPackage, "AndroidPackage is null ");
                    synchronized (this.mLock) {
                        try {
                            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i4);
                            try {
                                Objects.requireNonNull(uidStateLocked, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i4);
                                List<String> requestedRuntimePermissionsLocked = getRequestedRuntimePermissionsLocked(androidPackage, list);
                                if (z && !requestedRuntimePermissionsLocked.contains("android.permission.READ_EXTERNAL_STORAGE")) {
                                    requestedRuntimePermissionsLocked.contains("android.permission.WRITE_EXTERNAL_STORAGE");
                                }
                                z2 = z4;
                                for (String str2 : requestedRuntimePermissionsLocked) {
                                    try {
                                        permission = this.mRegistry.getPermission(str2);
                                        Objects.requireNonNull(permission, "BasePermission is null: " + str2);
                                    } catch (NullPointerException e) {
                                        e = e;
                                        uidPermissionState = uidStateLocked;
                                    } catch (RuntimeException e2) {
                                        e = e2;
                                        uidPermissionState = uidStateLocked;
                                    }
                                    if (!isPossibleToModify(i2, i4, androidPackage, uidStateLocked, str2)) {
                                        break;
                                    }
                                    if (i == i5) {
                                        z3 = handleGrantLocked(i4, androidPackage, uidStateLocked, str2, permission);
                                        uidPermissionState = uidStateLocked;
                                    } else if (i == 2) {
                                        uidPermissionState = uidStateLocked;
                                        try {
                                            z3 = handleDenyLocked(i4, androidPackage, uidStateLocked, str2, permission, z);
                                        } catch (NullPointerException e3) {
                                            e = e3;
                                            Log.i("PermissionManager", e.getMessage());
                                            z2 = false;
                                            uidStateLocked = uidPermissionState;
                                            i5 = 1;
                                        } catch (RuntimeException e4) {
                                            e = e4;
                                            Log.d("PermissionManager", e.getMessage());
                                            uidStateLocked = uidPermissionState;
                                            i5 = 1;
                                        }
                                    } else {
                                        uidPermissionState = uidStateLocked;
                                        z3 = true;
                                    }
                                    if (z3) {
                                        try {
                                            try {
                                                uidPermissionState.updatePermissionFlags(permission, i2, i3);
                                            } catch (Throwable th) {
                                                th = th;
                                                try {
                                                    throw th;
                                                } catch (NullPointerException e5) {
                                                    e = e5;
                                                    Log.i("PermissionManager", e.getMessage());
                                                    z4 = false;
                                                    i5 = 1;
                                                }
                                            }
                                        } catch (NullPointerException e6) {
                                            e = e6;
                                            Log.i("PermissionManager", e.getMessage());
                                            z2 = false;
                                            uidStateLocked = uidPermissionState;
                                            i5 = 1;
                                        } catch (RuntimeException e7) {
                                            e = e7;
                                            Log.d("PermissionManager", e.getMessage());
                                            uidStateLocked = uidPermissionState;
                                            i5 = 1;
                                        }
                                        uidStateLocked = uidPermissionState;
                                        i5 = 1;
                                    } else {
                                        z2 = false;
                                        uidStateLocked = uidPermissionState;
                                        i5 = 1;
                                    }
                                }
                            } catch (NullPointerException e8) {
                                Slog.e("PermissionManager", e8.getMessage());
                                return false;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    z4 = z2;
                } catch (NullPointerException e9) {
                    e = e9;
                }
                i5 = 1;
            }
            this.mPackageManagerInt.writePermissionSettings(new int[]{i4}, true);
            return z4;
        } catch (Exception e10) {
            e10.printStackTrace();
            Log.d("PermissionManager", "Failed with Exception " + e10.getMessage());
            return false;
        }
    }

    public final List getPackagesToUpdate(String str, boolean z) {
        if (z) {
            return new ArrayList(this.mPackageManagerInt.getPackageStates().values());
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mPackageManagerInt.getPackageStateInternal(str));
        return arrayList;
    }

    public final boolean isPossibleToModify(int i, int i2, AndroidPackage androidPackage, UidPermissionState uidPermissionState, String str) {
        if ((uidPermissionState.getPermissionFlags(str) & 16) == 0) {
            return true;
        }
        List containerCriticalApps = PersonaManagerService.getContainerCriticalApps();
        if (containerCriticalApps != null && containerCriticalApps.size() > 0 && containerCriticalApps.contains(androidPackage.getPackageName()) && i == 16) {
            return false;
        }
        throw new RuntimeException("Cannot modify system fixed permission " + str + " for package " + androidPackage.getPackageName() + " in user " + i2);
    }

    public final boolean handleGrantLocked(int i, AndroidPackage androidPackage, UidPermissionState uidPermissionState, String str, Permission permission) {
        int appId = UserHandle.getAppId(androidPackage.getUid());
        if (androidPackage.getTargetSdkVersion() > 22) {
            if (uidPermissionState.hasPermissionState(str) && uidPermissionState.isPermissionGranted(str)) {
                return true;
            }
            boolean grantPermission = uidPermissionState.grantPermission(permission);
            setUidMode(i, appId, getAppOp(androidPackage, str, permission), androidPackage.getPackageName(), false, false);
            if (!grantPermission) {
                Log.i("PermissionManager", "Failed to grant the permission " + str + " to package " + androidPackage.getPackageName());
                return false;
            }
            if (permission.hasGids()) {
                this.mDefaultPermissionCallback.onGidsChanged(UserHandle.getAppId(androidPackage.getUid()), i);
            }
        } else {
            int appOp = getAppOp(androidPackage, str, permission);
            uidPermissionState.updatePermissionFlags(permission, 8, 0);
            setUidMode(i, appId, appOp, androidPackage.getPackageName(), true, true);
        }
        return true;
    }

    public final boolean handleDenyLocked(final int i, final AndroidPackage androidPackage, UidPermissionState uidPermissionState, String str, Permission permission, boolean z) {
        final int appId = UserHandle.getAppId(androidPackage.getUid());
        if (androidPackage.getTargetSdkVersion() > 22) {
            if (uidPermissionState.hasPermissionState(str) && !uidPermissionState.isPermissionGranted(str)) {
                return true;
            }
            if (!uidPermissionState.removePermissionState(permission.getName())) {
                Log.i("PermissionManager", "Failed to deny the permission " + str + " to package " + androidPackage.getPackageName());
                return false;
            }
            if (z) {
                this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        PermissionManagerServiceImpl.killUid(appId, i, "permissions revoked");
                    }
                });
            }
        } else {
            uidPermissionState.updatePermissionFlags(permission, 8, 8);
            final int appOp = getAppOp(androidPackage, str, permission);
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl.this.lambda$handleDenyLocked$8(i, appId, appOp, androidPackage);
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDenyLocked$8(int i, int i2, int i3, AndroidPackage androidPackage) {
        int uid = UserHandle.getUid(i, i2);
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        if (i3 != -1 && appOpsManager.checkOp(i3, uid, androidPackage.getPackageName()) == 0) {
            appOpsManager.setUidMode(i3, uid, 1);
            killUid(i2, i, "Permission related app op changed");
        }
    }

    public final int getAppOp(AndroidPackage androidPackage, String str, Permission permission) {
        int permissionToOpCode = "android".equals(permission.getPackageName()) ? AppOpsManager.permissionToOpCode(permission.getName()) : -1;
        if (permissionToOpCode != -1) {
            return permissionToOpCode;
        }
        throw new RuntimeException("Invalid appOp " + permissionToOpCode + " for package " + androidPackage.getPackageName() + " permission " + str);
    }

    public final List getRequestedRuntimePermissionsLocked(AndroidPackage androidPackage, List list) {
        if (list == null || list.isEmpty()) {
            list = androidPackage.getRequestedPermissions();
        }
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            Permission permission = this.mRegistry.getPermission(str);
            if (permission != null && permission.isRuntime()) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final void setUidMode(final int i, final int i2, final int i3, final String str, final boolean z, final boolean z2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl.3
            @Override // java.lang.Runnable
            public void run() {
                int uid = UserHandle.getUid(i, i2);
                AppOpsManager appOpsManager = (AppOpsManager) PermissionManagerServiceImpl.this.mContext.getSystemService(AppOpsManager.class);
                int i4 = i3;
                boolean z3 = i4 != -1 && appOpsManager.checkOp(i4, uid, str) == 0;
                if (z && z3) {
                    return;
                }
                appOpsManager.setUidMode(i3, uid, 0);
                if (z2) {
                    PermissionManagerServiceImpl.killUid(i2, i, "Permission related app op changed");
                }
            }
        });
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException();
        }
        if (!this.mUserManagerInt.exists(i2)) {
            Log.d("PermissionManager", "Invalid User ID " + i2);
            return false;
        }
        if (str == null) {
            Log.d("PermissionManager", "Invalid package name " + str);
            return false;
        }
        return applyRuntimePermissionsInternalForMDM(str, list, i, i2, true);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException();
        }
        if (!this.mUserManagerInt.exists(i2)) {
            Log.d("PermissionManager", "Invalid User ID " + i2);
            return false;
        }
        return applyRuntimePermissionsInternalForMDM(null, null, i, i2, false);
    }

    public final boolean applyRuntimePermissionsInternalForMDM(String str, List list, int i, int i2, boolean z) {
        List containerCriticalApps = PersonaManagerService.getContainerCriticalApps();
        if (Binder.getCallingUid() != 1000 && str != null && containerCriticalApps != null && containerCriticalApps.size() > 0 && containerCriticalApps.contains(str)) {
            Log.d("PermissionManager", "Package " + str + " is container critical application");
            return false;
        }
        if (i == 0) {
            return updatePermissionStatesAndFlagsInternalForMDM(str, list, i, 4, 0, i2, z);
        }
        if (i == 1 || i == 2) {
            return updatePermissionStatesAndFlagsInternalForMDM(str, list, i, 4, 4, i2, z);
        }
        Log.d("PermissionManager", "Invalid Permission State " + i);
        return false;
    }

    public final void revokeLicensePermissionForMDM(int[] iArr, ArrayList arrayList, PackageStateInternal packageStateInternal) {
        synchronized (this.mLock) {
            for (int i : iArr) {
                UidPermissionState orCreateUidState = this.mState.getOrCreateUserState(i).getOrCreateUidState(packageStateInternal.getAppId());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    orCreateUidState.revokePermission((Permission) it.next());
                }
            }
        }
    }

    public final ArrayList grantLicensePermissionForMDM(int[] iArr, ArrayList arrayList, PackageStateInternal packageStateInternal) {
        ArrayList arrayList2 = new ArrayList();
        synchronized (this.mLock) {
            for (int i : iArr) {
                UidPermissionState orCreateUidState = this.mState.getOrCreateUserState(i).getOrCreateUidState(packageStateInternal.getAppId());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Permission permission = (Permission) it.next();
                    if (orCreateUidState.grantPermission(permission)) {
                        arrayList2.add(permission.getName());
                    }
                }
            }
        }
        return arrayList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x007e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0080  */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int setLicensePermissionsForMDM(String str, Set set) {
        boolean z;
        boolean z2;
        PackageStateInternal packageStateInternal;
        ApplicationInfo applicationInfo;
        Log.i("PermissionManager", "setLicensePermissions : packageName = " + str);
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        int[] allUserIds = getAllUserIds();
        if (androidPackage == null) {
            Log.i("PermissionManager", "setLicensePermissions : " + str + " is invalid (NULL)");
        }
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 0);
            z = applicationInfo.isSignedWithPlatformKey();
        } catch (PackageManager.NameNotFoundException e) {
            e = e;
            z = false;
        }
        try {
            z2 = applicationInfo.isPrivilegedApp();
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            Log.e("PermissionManager", "Package name: " + str + " cannot be found." + e);
            z2 = false;
            packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
            if (packageStateInternal != null) {
            }
        }
        packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (packageStateInternal != null) {
            return -3;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            String[] split = str2.split(",");
            if (split != null && split.length > 0) {
                for (String str3 : split) {
                    Permission permission = this.mRegistry.getPermission(str3);
                    if (permission != null && getSourcePackageSetting(permission) != null && (!z || !androidPackage.getRequestedPermissions().contains(str3))) {
                        arrayList.add(permission);
                    }
                }
            } else {
                Log.i("PermissionManager", "setLicensePermissions : " + str + " : invalid permission " + str2);
            }
        }
        revokeLicensePermissionForMDM(allUserIds, arrayList, packageStateInternal);
        Permission permission2 = this.mRegistry.getPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
        if (permission2 != null && getSourcePackageSetting(permission2) != null && !z2 && !z) {
            synchronized (this.mLock) {
                for (int i : allUserIds) {
                    this.mState.getOrCreateUserState(i).getOrCreateUidState(packageStateInternal.getAppId()).revokePermission(permission2);
                }
            }
        }
        List<String> eLMPermissions = EnterpriseLicenseManager.getInstance(this.mContext).getELMPermissions(str);
        if (eLMPermissions == null) {
            return -4;
        }
        new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str4 : eLMPermissions) {
            Permission permission3 = this.mRegistry.getPermission(str4);
            if (permission3 != null && getSourcePackageSetting(permission3) != null) {
                if (PmHook.hasSelectivePermissionsForMDM(androidPackage.getMetaData())) {
                    if (androidPackage.getRequestedPermissions().contains(str4)) {
                        arrayList2.add(permission3);
                        Log.d("PermissionManager", "hasSelectivePermissionsForMdm(): permission granted = " + str4);
                    }
                } else {
                    arrayList2.add(permission3);
                }
            }
        }
        this.mPackageGrantedPermissions.put(androidPackage.getPackageName(), grantLicensePermissionForMDM(allUserIds, arrayList2, packageStateInternal));
        return 0;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getPackageGrantedPermissionsForMDM(String str) {
        return (List) this.mPackageGrantedPermissions.get(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePackageGrantedPermissionsForMDM(String str) {
        this.mPackageGrantedPermissions.remove(str);
    }

    public final void revokeStoragePermissionsIfScopeExpandedInternal(AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        PermissionInfo permissionInfo;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z = androidPackage2.getTargetSdkVersion() >= 29 && androidPackage.getTargetSdkVersion() < 29;
        boolean z2 = ((androidPackage2.getTargetSdkVersion() < 29 && androidPackage.getTargetSdkVersion() >= 29) || androidPackage2.isRequestLegacyExternalStorage() || !androidPackage.isRequestLegacyExternalStorage()) ? false : true;
        if (z2 || z) {
            int callingUid = Binder.getCallingUid();
            int[] allUserIds = getAllUserIds();
            int length = allUserIds.length;
            int i7 = 0;
            while (i7 < length) {
                int i8 = allUserIds[i7];
                int size = androidPackage.getRequestedPermissions().size();
                int i9 = 0;
                while (i9 < size) {
                    PermissionInfo permissionInfo2 = getPermissionInfo((String) androidPackage.getRequestedPermissions().get(i9), 0, androidPackage.getPackageName());
                    if (permissionInfo2 != null) {
                        if (STORAGE_PERMISSIONS.contains(permissionInfo2.name) || READ_MEDIA_AURAL_PERMISSIONS.contains(permissionInfo2.name) || READ_MEDIA_VISUAL_PERMISSIONS.contains(permissionInfo2.name)) {
                            EventLog.writeEvent(1397638484, "171430330", Integer.valueOf(androidPackage.getUid()), "Revoking permission " + permissionInfo2.name + " from package " + androidPackage.getPackageName() + " as either the sdk downgraded " + z + " or newly requested legacy full storage " + z2);
                            try {
                                permissionInfo = permissionInfo2;
                                i = i9;
                                i2 = size;
                                i3 = i8;
                                i4 = i7;
                                i5 = length;
                                try {
                                    revokeRuntimePermissionInternal(androidPackage.getPackageName(), permissionInfo2.name, false, callingUid, i8, null, this.mDefaultPermissionCallback);
                                    i6 = i3;
                                } catch (IllegalStateException | SecurityException e) {
                                    e = e;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("unable to revoke ");
                                    sb.append(permissionInfo.name);
                                    sb.append(" for ");
                                    sb.append(androidPackage.getPackageName());
                                    sb.append(" user ");
                                    i6 = i3;
                                    sb.append(i6);
                                    Log.e("PermissionManager", sb.toString(), e);
                                    i9 = i + 1;
                                    i8 = i6;
                                    size = i2;
                                    length = i5;
                                    i7 = i4;
                                }
                            } catch (IllegalStateException | SecurityException e2) {
                                e = e2;
                                permissionInfo = permissionInfo2;
                                i = i9;
                                i2 = size;
                                i3 = i8;
                                i4 = i7;
                                i5 = length;
                            }
                            i9 = i + 1;
                            i8 = i6;
                            size = i2;
                            length = i5;
                            i7 = i4;
                        }
                    }
                    i = i9;
                    i2 = size;
                    i6 = i8;
                    i4 = i7;
                    i5 = length;
                    i9 = i + 1;
                    i8 = i6;
                    size = i2;
                    length = i5;
                    i7 = i4;
                }
                i7++;
            }
        }
    }

    public final void revokeSystemAlertWindowIfUpgradedPast23(AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        Permission permission;
        if (androidPackage2.getTargetSdkVersion() >= 23 || androidPackage.getTargetSdkVersion() < 23 || !androidPackage.getRequestedPermissions().contains("android.permission.SYSTEM_ALERT_WINDOW")) {
            return;
        }
        synchronized (this.mLock) {
            permission = this.mRegistry.getPermission("android.permission.SYSTEM_ALERT_WINDOW");
        }
        if (shouldGrantPermissionByProtectionFlags(androidPackage, this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName()), permission, new ArraySet()) || shouldGrantPermissionBySignature(androidPackage, permission)) {
            return;
        }
        for (int i : getAllUserIds()) {
            try {
                revokePermissionFromPackageForUser(androidPackage.getPackageName(), "android.permission.SYSTEM_ALERT_WINDOW", false, i, this.mDefaultPermissionCallback);
            } catch (IllegalStateException | SecurityException e) {
                Log.e("PermissionManager", "unable to revoke SYSTEM_ALERT_WINDOW for " + androidPackage.getPackageName() + " user " + i, e);
            }
        }
    }

    public final void revokeRuntimePermissionsIfGroupChangedInternal(final AndroidPackage androidPackage, AndroidPackage androidPackage2) {
        int size = ArrayUtils.size(androidPackage2.getPermissions());
        ArrayMap arrayMap = new ArrayMap(size);
        for (int i = 0; i < size; i++) {
            ParsedPermission parsedPermission = (ParsedPermission) androidPackage2.getPermissions().get(i);
            if (parsedPermission.getParsedPermissionGroup() != null) {
                arrayMap.put(parsedPermission.getName(), parsedPermission.getParsedPermissionGroup().getName());
            }
        }
        final int callingUid = Binder.getCallingUid();
        int size2 = ArrayUtils.size(androidPackage.getPermissions());
        for (int i2 = 0; i2 < size2; i2++) {
            ParsedPermission parsedPermission2 = (ParsedPermission) androidPackage.getPermissions().get(i2);
            if ((ParsedPermissionUtils.getProtection(parsedPermission2) & 1) != 0) {
                final String name = parsedPermission2.getName();
                final String name2 = parsedPermission2.getParsedPermissionGroup() == null ? null : parsedPermission2.getParsedPermissionGroup().getName();
                final String str = (String) arrayMap.get(name);
                if (name2 != null && !name2.equals(str)) {
                    final int[] userIds = this.mUserManagerInt.getUserIds();
                    this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda23
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PermissionManagerServiceImpl.this.lambda$revokeRuntimePermissionsIfGroupChangedInternal$9(userIds, name, androidPackage, str, name2, callingUid, (AndroidPackage) obj);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeRuntimePermissionsIfGroupChangedInternal$9(int[] iArr, String str, AndroidPackage androidPackage, String str2, String str3, int i, AndroidPackage androidPackage2) {
        String packageName = androidPackage2.getPackageName();
        for (int i2 : iArr) {
            if (checkPermission(packageName, str, i2) == 0) {
                EventLog.writeEvent(1397638484, "72710897", Integer.valueOf(androidPackage.getUid()), "Revoking permission " + str + " from package " + packageName + " as the group changed from " + str2 + " to " + str3);
                try {
                    revokeRuntimePermissionInternal(packageName, str, false, i, i2, null, this.mDefaultPermissionCallback);
                } catch (IllegalArgumentException e) {
                    Slog.e("PermissionManager", "Could not revoke " + str + " from " + packageName, e);
                }
            }
        }
    }

    public final void revokeRuntimePermissionsIfPermissionDefinitionChangedInternal(List list) {
        final int[] userIds = this.mUserManagerInt.getUserIds();
        int size = list.size();
        final int callingUid = Binder.getCallingUid();
        for (int i = 0; i < size; i++) {
            final String str = (String) list.get(i);
            synchronized (this.mLock) {
                Permission permission = this.mRegistry.getPermission(str);
                if (permission != null && (permission.isInternal() || permission.isRuntime())) {
                    final boolean isInternal = permission.isInternal();
                    this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda22
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PermissionManagerServiceImpl.this.m66x1b1d6bc0(userIds, str, isInternal, callingUid, (AndroidPackage) obj);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: lambda$revokeRuntimePermissionsIfPermissionDefinitionChangedInternal$10 */
    public /* synthetic */ void m66x1b1d6bc0(int[] iArr, String str, boolean z, int i, AndroidPackage androidPackage) {
        String str2;
        PermissionCallback permissionCallback;
        PermissionManagerServiceImpl permissionManagerServiceImpl = this;
        String packageName = androidPackage.getPackageName();
        int uid = androidPackage.getUid();
        if (uid < 10000) {
            return;
        }
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            int checkPermission = permissionManagerServiceImpl.checkPermission(packageName, str, i3);
            int permissionFlags = permissionManagerServiceImpl.getPermissionFlags(packageName, str, i3);
            if (checkPermission == 0 && (32820 & permissionFlags) == 0) {
                int uid2 = UserHandle.getUid(i3, uid);
                if (z) {
                    EventLog.writeEvent(1397638484, "195338390", Integer.valueOf(uid2), "Revoking permission " + str + " from package " + packageName + " due to definition change");
                } else {
                    EventLog.writeEvent(1397638484, "154505240", Integer.valueOf(uid2), "Revoking permission " + str + " from package " + packageName + " due to definition change");
                    EventLog.writeEvent(1397638484, "168319670", Integer.valueOf(uid2), "Revoking permission " + str + " from package " + packageName + " due to definition change");
                }
                Slog.e("PermissionManager", "Revoking permission " + str + " from package " + packageName + " due to definition change");
                try {
                    permissionCallback = permissionManagerServiceImpl.mDefaultPermissionCallback;
                    str2 = "PermissionManager";
                } catch (Exception e) {
                    e = e;
                    str2 = "PermissionManager";
                }
                try {
                    revokeRuntimePermissionInternal(packageName, str, false, i, i3, null, permissionCallback);
                } catch (Exception e2) {
                    e = e2;
                    Slog.e(str2, "Could not revoke " + str + " from " + packageName, e);
                    i2++;
                    permissionManagerServiceImpl = this;
                }
            }
            i2++;
            permissionManagerServiceImpl = this;
        }
    }

    public final List addAllPermissionsInternal(PackageState packageState, AndroidPackage androidPackage) {
        PermissionInfo generatePermissionInfo;
        Permission permissionTree;
        int size = ArrayUtils.size(androidPackage.getPermissions());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            ParsedPermission parsedPermission = (ParsedPermission) androidPackage.getPermissions().get(i);
            synchronized (this.mLock) {
                if (androidPackage.getTargetSdkVersion() > 22) {
                    ComponentMutateUtils.setParsedPermissionGroup(parsedPermission, this.mRegistry.getPermissionGroup(parsedPermission.getGroup()));
                }
                generatePermissionInfo = PackageInfoUtils.generatePermissionInfo(parsedPermission, 128L);
                permissionTree = parsedPermission.isTree() ? this.mRegistry.getPermissionTree(parsedPermission.getName()) : this.mRegistry.getPermission(parsedPermission.getName());
            }
            boolean isOverridingSystemPermission = Permission.isOverridingSystemPermission(permissionTree, generatePermissionInfo, this.mPackageManagerInt);
            synchronized (this.mLock) {
                Permission createOrUpdate = Permission.createOrUpdate(permissionTree, generatePermissionInfo, packageState, this.mRegistry.getPermissionTrees(), isOverridingSystemPermission);
                if (parsedPermission.isTree()) {
                    this.mRegistry.addPermissionTree(createOrUpdate);
                } else {
                    this.mRegistry.addPermission(createOrUpdate);
                }
                if (createOrUpdate.isDefinitionChanged()) {
                    arrayList.add(parsedPermission.getName());
                    createOrUpdate.setDefinitionChanged(false);
                }
            }
        }
        return arrayList;
    }

    public final void addAllPermissionGroupsInternal(AndroidPackage androidPackage) {
        synchronized (this.mLock) {
            int size = ArrayUtils.size(androidPackage.getPermissionGroups());
            for (int i = 0; i < size; i++) {
                ParsedPermissionGroup parsedPermissionGroup = (ParsedPermissionGroup) androidPackage.getPermissionGroups().get(i);
                ParsedPermissionGroup permissionGroup = this.mRegistry.getPermissionGroup(parsedPermissionGroup.getName());
                boolean equals = parsedPermissionGroup.getPackageName().equals(permissionGroup == null ? null : permissionGroup.getPackageName());
                if (permissionGroup != null && !equals) {
                    Slog.w("PermissionManager", "Permission group " + parsedPermissionGroup.getName() + " from package " + parsedPermissionGroup.getPackageName() + " ignored: original from " + permissionGroup.getPackageName());
                }
                this.mRegistry.addPermissionGroup(parsedPermissionGroup);
            }
        }
    }

    public final void removeAllPermissionsInternal(AndroidPackage androidPackage) {
        synchronized (this.mLock) {
            int size = ArrayUtils.size(androidPackage.getPermissions());
            for (int i = 0; i < size; i++) {
                ParsedPermission parsedPermission = (ParsedPermission) androidPackage.getPermissions().get(i);
                Permission permission = this.mRegistry.getPermission(parsedPermission.getName());
                if (permission == null) {
                    permission = this.mRegistry.getPermissionTree(parsedPermission.getName());
                }
                if (permission != null && permission.isPermission(parsedPermission)) {
                    permission.setPermissionInfo(null);
                }
                if (ParsedPermissionUtils.isAppOp(parsedPermission)) {
                    this.mRegistry.removeAppOpPermissionPackage(parsedPermission.getName(), androidPackage.getPackageName());
                }
            }
            int size2 = androidPackage.getRequestedPermissions().size();
            for (int i2 = 0; i2 < size2; i2++) {
                String str = (String) androidPackage.getRequestedPermissions().get(i2);
                Permission permission2 = this.mRegistry.getPermission(str);
                if (permission2 != null && permission2.isAppOp()) {
                    this.mRegistry.removeAppOpPermissionPackage(str, androidPackage.getPackageName());
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserRemoved(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        synchronized (this.mLock) {
            this.mState.removeUserState(i);
        }
    }

    public final Set getGrantedPermissionsInternal(String str, final int i) {
        final PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return Collections.emptySet();
        }
        synchronized (this.mLock) {
            UidPermissionState uidStateLocked = getUidStateLocked(packageStateInternal, i);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + str + " and user " + i);
                return Collections.emptySet();
            }
            if (!packageStateInternal.getUserStateOrDefault(i).isInstantApp()) {
                return uidStateLocked.getGrantedPermissions();
            }
            ArraySet arraySet = new ArraySet(uidStateLocked.getGrantedPermissions());
            arraySet.removeIf(new Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda20
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getGrantedPermissionsInternal$11;
                    lambda$getGrantedPermissionsInternal$11 = PermissionManagerServiceImpl.this.lambda$getGrantedPermissionsInternal$11(i, packageStateInternal, (String) obj);
                    return lambda$getGrantedPermissionsInternal$11;
                }
            });
            return arraySet;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getGrantedPermissionsInternal$11(int i, PackageStateInternal packageStateInternal, String str) {
        Permission permission = this.mRegistry.getPermission(str);
        if (permission == null) {
            return true;
        }
        if (permission.isInstant()) {
            return false;
        }
        EventLog.writeEvent(1397638484, "140256621", Integer.valueOf(UserHandle.getUid(i, packageStateInternal.getAppId())), str);
        return true;
    }

    public final int[] getPermissionGidsInternal(String str, int i) {
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str);
            if (permission == null) {
                return EmptyArray.INT;
            }
            return permission.computeGids(i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:233:0x0362, code lost:
    
        if (r12 == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x0517, code lost:
    
        if (r2.grantPermission(r14) == false) goto L321;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x04c7, code lost:
    
        if (r14.isRole() == false) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x04ff, code lost:
    
        if (com.android.internal.util.CollectionUtils.contains(r10, r7) == false) goto L308;
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x0511, code lost:
    
        if (r11.isPermissionGranted(r7) == false) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x050b, code lost:
    
        if (r14.isRole() == false) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x0588, code lost:
    
        if (r39.isSystem() == false) goto L338;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0237 A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02e4 A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x041c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0428 A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x042e A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0439 A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x052a A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x052f  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x048e A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /* JADX WARN: Removed duplicated region for block: B:312:0x0523  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x04cf  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x04d5  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x05aa  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0599  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0584 A[Catch: all -> 0x0601, TryCatch #1 {all -> 0x0601, blocks: (B:71:0x0150, B:73:0x016c, B:74:0x0170, B:76:0x0176, B:79:0x018b, B:81:0x019b, B:83:0x01a1, B:85:0x01a7, B:87:0x01ad, B:91:0x01bc, B:92:0x01b3, B:93:0x01c2, B:95:0x01c6, B:97:0x01d2, B:101:0x01de, B:109:0x01ed, B:111:0x0200, B:113:0x020e, B:114:0x0228, B:116:0x0237, B:121:0x025c, B:125:0x0536, B:126:0x026d, B:128:0x0273, B:130:0x027d, B:131:0x0280, B:135:0x0289, B:137:0x0293, B:138:0x02a1, B:140:0x02a7, B:142:0x02ad, B:144:0x02b3, B:146:0x02bd, B:149:0x02ca, B:151:0x02d0, B:153:0x02d6, B:156:0x02de, B:158:0x02e4, B:160:0x02f8, B:161:0x030a, B:164:0x031b, B:167:0x032a, B:169:0x032e, B:176:0x0424, B:178:0x0428, B:182:0x042e, B:183:0x0432, B:190:0x0348, B:192:0x034e, B:197:0x0364, B:198:0x036b, B:200:0x0373, B:202:0x0377, B:203:0x037b, B:205:0x037f, B:207:0x0389, B:208:0x03a0, B:210:0x03a4, B:212:0x03aa, B:214:0x03af, B:216:0x03b5, B:225:0x0396, B:227:0x039c, B:235:0x03c3, B:237:0x03cf, B:239:0x03d5, B:240:0x03da, B:242:0x03de, B:244:0x03ea, B:246:0x03f6, B:247:0x03fb, B:249:0x0405, B:257:0x0416, B:263:0x0439, B:264:0x046f, B:269:0x0513, B:272:0x0524, B:274:0x052a, B:275:0x0530, B:278:0x0488, B:280:0x048e, B:282:0x0494, B:285:0x04a4, B:287:0x04ac, B:289:0x04b2, B:292:0x04bd, B:294:0x04c3, B:296:0x04db, B:298:0x04e1, B:300:0x04e7, B:302:0x04ed, B:304:0x04f5, B:306:0x04fb, B:308:0x050d, B:310:0x051d, B:313:0x0501, B:315:0x0507, B:318:0x04c9, B:337:0x058a, B:339:0x0590, B:343:0x05a0, B:345:0x05ab, B:351:0x0578, B:353:0x0584, B:355:0x0218, B:357:0x021e), top: B:70:0x0150 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void restorePermissionState(AndroidPackage androidPackage, boolean z, String str, PermissionCallback permissionCallback, int i) {
        int[] iArr;
        ArraySet arraySet;
        ArraySet arraySet2;
        ArraySet arraySet3;
        Collection<String> collection;
        Collection collection2;
        int i2;
        ArraySet arraySet4;
        ArraySet arraySet5;
        SparseBooleanArray sparseBooleanArray;
        int i3;
        UidPermissionState uidPermissionState;
        int i4;
        UserPermissionState userPermissionState;
        PackageStateInternal packageStateInternal;
        UserPermissionState userPermissionState2;
        boolean z2;
        int[] iArr2;
        ArraySet arraySet6;
        boolean z3;
        UserPermissionState userPermissionState3;
        PackageStateInternal packageStateInternal2;
        SparseBooleanArray sparseBooleanArray2;
        ArraySet arraySet7;
        ArraySet arraySet8;
        ArraySet arraySet9;
        ArraySet arraySet10;
        int i5;
        boolean z4;
        int i6;
        boolean z5;
        Permission permission;
        boolean z6 = z;
        String str2 = str;
        PackageStateInternal packageStateInternal3 = this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal3 == null) {
            return;
        }
        if (i == -1) {
            iArr = getAllUserIds();
        } else {
            iArr = new int[]{i};
        }
        int[] iArr3 = iArr;
        int[] iArr4 = EMPTY_INT_ARRAY;
        ArraySet arraySet11 = new ArraySet();
        List requestedPermissions = androidPackage.getRequestedPermissions();
        int size = requestedPermissions.size();
        ArraySet arraySet12 = null;
        ArraySet arraySet13 = null;
        ArraySet arraySet14 = null;
        int i7 = 0;
        while (i7 < size) {
            String str3 = (String) androidPackage.getRequestedPermissions().get(i7);
            int[] iArr5 = iArr4;
            synchronized (this.mLock) {
                permission = this.mRegistry.getPermission(str3);
            }
            if (permission != null) {
                if (permission.isPrivileged() && checkPrivilegedPermissionAllowlist(androidPackage, packageStateInternal3, permission)) {
                    if (arraySet14 == null) {
                        arraySet14 = new ArraySet();
                    }
                    arraySet14.add(str3);
                }
                if (permission.isSignature() && (shouldGrantPermissionBySignature(androidPackage, permission) || shouldGrantPermissionByProtectionFlags(androidPackage, packageStateInternal3, permission, arraySet11))) {
                    if (arraySet12 == null) {
                        arraySet12 = new ArraySet();
                    }
                    arraySet12.add(str3);
                }
                if (permission.isInternal() && shouldGrantPermissionByProtectionFlags(androidPackage, packageStateInternal3, permission, arraySet11)) {
                    if (arraySet13 == null) {
                        arraySet13 = new ArraySet();
                    }
                    arraySet13.add(str3);
                }
            }
            i7++;
            iArr4 = iArr5;
        }
        int[] iArr6 = iArr4;
        SparseBooleanArray sparseBooleanArray3 = new SparseBooleanArray();
        if (this.mPermissionPolicyInternal != null) {
            int length = iArr3.length;
            int i8 = 0;
            while (i8 < length) {
                int i9 = iArr3[i8];
                int i10 = length;
                if (this.mPermissionPolicyInternal.isInitialized(i9)) {
                    sparseBooleanArray3.put(i9, true);
                }
                i8++;
                length = i10;
            }
        }
        if (!packageStateInternal3.hasSharedUser()) {
            List requestedPermissions2 = androidPackage.getRequestedPermissions();
            List implicitPermissions = androidPackage.getImplicitPermissions();
            i2 = androidPackage.getTargetSdkVersion();
            collection2 = implicitPermissions;
            arraySet3 = arraySet12;
            arraySet = arraySet13;
            arraySet2 = arraySet11;
            collection = requestedPermissions2;
        } else {
            ArraySet arraySet15 = new ArraySet();
            ArraySet arraySet16 = new ArraySet();
            arraySet = arraySet13;
            ArraySet sharedUserPackages = this.mPackageManagerInt.getSharedUserPackages(packageStateInternal3.getSharedUserAppId());
            int size2 = sharedUserPackages.size();
            arraySet2 = arraySet11;
            int i11 = 0;
            arraySet3 = arraySet12;
            int i12 = 10000;
            while (i11 < size2) {
                AndroidPackage androidPackage2 = ((PackageStateInternal) sharedUserPackages.valueAt(i11)).getAndroidPackage();
                if (androidPackage2 == null) {
                    arraySet4 = sharedUserPackages;
                } else {
                    arraySet4 = sharedUserPackages;
                    arraySet15.addAll(androidPackage2.getRequestedPermissions());
                    arraySet16.addAll(androidPackage2.getImplicitPermissions());
                    i12 = Math.min(i12, androidPackage2.getTargetSdkVersion());
                }
                i11++;
                sharedUserPackages = arraySet4;
            }
            collection = arraySet15;
            collection2 = arraySet16;
            i2 = i12;
        }
        Object obj = this.mLock;
        synchronized (obj) {
            try {
                try {
                    int length2 = iArr3.length;
                    PackageStateInternal packageStateInternal4 = obj;
                    int[] iArr7 = iArr6;
                    boolean z7 = false;
                    int i13 = 0;
                    boolean z8 = false;
                    while (i13 < length2) {
                        int i14 = length2;
                        try {
                            int i15 = iArr3[i13];
                            int i16 = i13;
                            UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(i15);
                            boolean z9 = z7;
                            UidPermissionState orCreateUidState = orCreateUserState.getOrCreateUidState(packageStateInternal3.getAppId());
                            int[] iArr8 = iArr3;
                            if (orCreateUidState.isMissing()) {
                                for (String str4 : collection) {
                                    ArraySet arraySet17 = arraySet14;
                                    Permission permission2 = this.mRegistry.getPermission(str4);
                                    if (permission2 == null) {
                                        arraySet14 = arraySet17;
                                    } else {
                                        SparseBooleanArray sparseBooleanArray4 = sparseBooleanArray3;
                                        if (Objects.equals(permission2.getPackageName(), "android") && permission2.isRuntime() && !permission2.isRemoved()) {
                                            if (permission2.isHardOrSoftRestricted() || permission2.isImmutablyRestricted()) {
                                                orCreateUidState.updatePermissionFlags(permission2, IInstalld.FLAG_FORCE, IInstalld.FLAG_FORCE);
                                            }
                                            if (i2 < 23) {
                                                orCreateUidState.updatePermissionFlags(permission2, 72, 72);
                                                orCreateUidState.grantPermission(permission2);
                                            }
                                        }
                                        if (PMRune.PM_NAL_GET_APP_LIST && Objects.equals(permission2.getPackageName(), "com.samsung.android.permissioncontroller") && "com.samsung.android.permission.GET_APP_LIST".equals(str4) && i2 < 23) {
                                            orCreateUidState.updatePermissionFlags(permission2, 72, 72);
                                            orCreateUidState.grantPermission(permission2);
                                        }
                                        arraySet14 = arraySet17;
                                        sparseBooleanArray3 = sparseBooleanArray4;
                                    }
                                }
                                arraySet5 = arraySet14;
                                sparseBooleanArray = sparseBooleanArray3;
                                orCreateUidState.setMissing(false);
                                iArr7 = ArrayUtils.appendInt(iArr7, i15);
                            } else {
                                arraySet5 = arraySet14;
                                sparseBooleanArray = sparseBooleanArray3;
                            }
                            if (z6) {
                                i3 = 0;
                                orCreateUserState.setInstallPermissionsFixed(packageStateInternal3.getPackageName(), false);
                                if (!packageStateInternal3.hasSharedUser()) {
                                    UidPermissionState uidPermissionState2 = new UidPermissionState(orCreateUidState);
                                    orCreateUidState.reset();
                                    uidPermissionState = uidPermissionState2;
                                } else if (revokeUnusedSharedUserPermissionsLocked(collection, orCreateUidState)) {
                                    iArr7 = ArrayUtils.appendInt(iArr7, i15);
                                    uidPermissionState = orCreateUidState;
                                    z8 = true;
                                }
                                ArraySet arraySet18 = new ArraySet();
                                androidPackage.getPackageName();
                                androidPackage.getUid();
                                int[] iArr9 = iArr7;
                                i4 = i3;
                                while (i3 < size) {
                                    int i17 = size;
                                    String str5 = (String) requestedPermissions.get(i3);
                                    Collection collection3 = collection;
                                    Permission permission3 = this.mRegistry.getPermission(str5);
                                    List list = requestedPermissions;
                                    int i18 = i2;
                                    boolean z10 = androidPackage.getTargetSdkVersion() >= 23;
                                    if (permission3 != null) {
                                        if (!uidPermissionState.hasPermissionState(str5) && androidPackage.getImplicitPermissions().contains(str5)) {
                                            arraySet18.add(str5);
                                        }
                                        if (!permission3.isRuntimeOnly() || z10) {
                                            String name = permission3.getName();
                                            if (permission3.isAppOp()) {
                                                arraySet6 = arraySet18;
                                                this.mRegistry.addAppOpPermissionPackage(name, androidPackage.getPackageName());
                                            } else {
                                                arraySet6 = arraySet18;
                                            }
                                            if (permission3.isNormal() && !uidPermissionState.isPermissionGranted(name) && !packageStateInternal3.isSystem() && orCreateUserState.areInstallPermissionsFixed(packageStateInternal3.getPackageName())) {
                                                if (!isCompatPlatformPermissionForPackage(name, androidPackage)) {
                                                    z3 = false;
                                                    if (!permission3.isNormal() && !permission3.isSignature() && !permission3.isInternal()) {
                                                        if (!permission3.isRuntime()) {
                                                            boolean isHardRestricted = permission3.isHardRestricted();
                                                            boolean isSoftRestricted = permission3.isSoftRestricted();
                                                            SparseBooleanArray sparseBooleanArray5 = sparseBooleanArray;
                                                            boolean z11 = sparseBooleanArray5.get(i15);
                                                            PermissionState permissionState = uidPermissionState.getPermissionState(name);
                                                            if (permissionState != null) {
                                                                userPermissionState3 = orCreateUserState;
                                                                sparseBooleanArray2 = sparseBooleanArray5;
                                                                i5 = permissionState.getFlags();
                                                            } else {
                                                                userPermissionState3 = orCreateUserState;
                                                                sparseBooleanArray2 = sparseBooleanArray5;
                                                                i5 = 0;
                                                            }
                                                            packageStateInternal2 = packageStateInternal3;
                                                            boolean z12 = (uidPermissionState.getPermissionFlags(permission3.getName()) & 14336) != 0;
                                                            boolean z13 = (uidPermissionState.getPermissionFlags(permission3.getName()) & 16384) != 0;
                                                            if (PMRune.PM_GRANT_ALL_RUNTIME_PERMISSION_UNPACK) {
                                                                i6 = i5 | 32;
                                                                if (!orCreateUidState.grantPermission(permission3)) {
                                                                    i5 = i6;
                                                                    z4 = false;
                                                                    if (z11 && (((!isHardRestricted && !isSoftRestricted) || z12) && z13)) {
                                                                        int i19 = i5 & (-16385);
                                                                        if (!z10) {
                                                                            i19 |= 64;
                                                                        }
                                                                        i5 = i19;
                                                                        z4 = true;
                                                                    }
                                                                    if (z4) {
                                                                        iArr9 = ArrayUtils.appendInt(iArr9, i15);
                                                                    }
                                                                    orCreateUidState.updatePermissionFlags(permission3, 261119, i5);
                                                                }
                                                                i5 = i6;
                                                                z4 = true;
                                                                if (z11) {
                                                                    int i192 = i5 & (-16385);
                                                                    if (!z10) {
                                                                    }
                                                                    i5 = i192;
                                                                    z4 = true;
                                                                }
                                                                if (z4) {
                                                                }
                                                                orCreateUidState.updatePermissionFlags(permission3, 261119, i5);
                                                            } else if (z10) {
                                                                if (z11 && isHardRestricted) {
                                                                    if (!z12) {
                                                                        z5 = permissionState != null && permissionState.isGranted() && orCreateUidState.revokePermission(permission3);
                                                                        if (!z13) {
                                                                            i5 |= 16384;
                                                                            z5 = true;
                                                                        }
                                                                    }
                                                                    z5 = false;
                                                                } else {
                                                                    if (z11) {
                                                                        if (isSoftRestricted) {
                                                                            if (!z12) {
                                                                            }
                                                                        }
                                                                    }
                                                                    z5 = false;
                                                                }
                                                                List list2 = NOTIFICATION_PERMISSIONS;
                                                                if (!list2.contains(name) && (i5 & 64) != 0) {
                                                                    i5 &= -65;
                                                                    z5 = true;
                                                                }
                                                                if ((i5 & 8) == 0 || isPermissionSplitFromNonRuntime(str5, androidPackage.getTargetSdkVersion())) {
                                                                    if ((!z11 || !isHardRestricted || z12) && permissionState != null && permissionState.isGranted()) {
                                                                        orCreateUidState.grantPermission(permission3);
                                                                    }
                                                                    i6 = i5;
                                                                } else {
                                                                    i6 = i5 & (-9);
                                                                    z5 = true;
                                                                }
                                                                if (this.mIsLeanback && list2.contains(str5)) {
                                                                    orCreateUidState.grantPermission(permission3);
                                                                    if ((permissionState == null || !permissionState.isGranted()) && orCreateUidState.grantPermission(permission3)) {
                                                                        i5 = i6;
                                                                        z4 = true;
                                                                        if (z11) {
                                                                        }
                                                                        if (z4) {
                                                                        }
                                                                        orCreateUidState.updatePermissionFlags(permission3, 261119, i5);
                                                                    }
                                                                }
                                                                i5 = i6;
                                                                z4 = z5;
                                                                if (z11) {
                                                                }
                                                                if (z4) {
                                                                }
                                                                orCreateUidState.updatePermissionFlags(permission3, 261119, i5);
                                                            } else {
                                                                if (permissionState == null) {
                                                                    if (!"android".equals(permission3.getPackageName()) || permission3.isRemoved()) {
                                                                        z4 = false;
                                                                    } else {
                                                                        i5 |= 72;
                                                                        z4 = true;
                                                                    }
                                                                    if (PMRune.PM_NAL_GET_APP_LIST && "com.samsung.android.permissioncontroller".equals(permission3.getPackageName()) && "com.samsung.android.permission.GET_APP_LIST".equals(permission3.getName())) {
                                                                        i5 |= 72;
                                                                        z4 = true;
                                                                    }
                                                                } else {
                                                                    z4 = false;
                                                                }
                                                                if (!orCreateUidState.isPermissionGranted(permission3.getName()) && orCreateUidState.grantPermission(permission3)) {
                                                                    z4 = true;
                                                                }
                                                                if (z11 && ((isHardRestricted || isSoftRestricted) && !z12 && !z13)) {
                                                                    i6 = i5 | 16384;
                                                                    i5 = i6;
                                                                    z4 = true;
                                                                }
                                                                if (z11) {
                                                                }
                                                                if (z4) {
                                                                }
                                                                orCreateUidState.updatePermissionFlags(permission3, 261119, i5);
                                                            }
                                                        } else {
                                                            userPermissionState3 = orCreateUserState;
                                                            packageStateInternal2 = packageStateInternal3;
                                                            sparseBooleanArray2 = sparseBooleanArray;
                                                            Slog.wtf(LOG_TAG, "Unknown permission protection " + permission3.getProtection() + " for permission " + permission3.getName());
                                                        }
                                                        arraySet10 = arraySet;
                                                        arraySet7 = arraySet3;
                                                        arraySet8 = arraySet2;
                                                        arraySet9 = arraySet5;
                                                        i3++;
                                                        arraySet3 = arraySet7;
                                                        arraySet5 = arraySet9;
                                                        arraySet2 = arraySet8;
                                                        arraySet = arraySet10;
                                                        collection = collection3;
                                                        size = i17;
                                                        requestedPermissions = list;
                                                        i2 = i18;
                                                        arraySet18 = arraySet6;
                                                        sparseBooleanArray = sparseBooleanArray2;
                                                        orCreateUserState = userPermissionState3;
                                                        packageStateInternal3 = packageStateInternal2;
                                                        str2 = str;
                                                    }
                                                    userPermissionState3 = orCreateUserState;
                                                    packageStateInternal2 = packageStateInternal3;
                                                    sparseBooleanArray2 = sparseBooleanArray;
                                                    if (permission3.isNormal() && z3) {
                                                        arraySet10 = arraySet;
                                                        arraySet7 = arraySet3;
                                                        arraySet8 = arraySet2;
                                                        arraySet9 = arraySet5;
                                                    }
                                                    if (permission3.isSignature()) {
                                                        arraySet7 = arraySet3;
                                                        arraySet8 = arraySet2;
                                                        arraySet9 = arraySet5;
                                                    } else {
                                                        if (permission3.isPrivileged()) {
                                                            arraySet9 = arraySet5;
                                                            if (!CollectionUtils.contains(arraySet9, str5)) {
                                                                arraySet7 = arraySet3;
                                                                arraySet8 = arraySet2;
                                                            }
                                                        } else {
                                                            arraySet9 = arraySet5;
                                                        }
                                                        arraySet7 = arraySet3;
                                                        if (CollectionUtils.contains(arraySet7, str5)) {
                                                            arraySet8 = arraySet2;
                                                        } else {
                                                            if (permission3.isPrivileged()) {
                                                                arraySet8 = arraySet2;
                                                                if (!CollectionUtils.contains(arraySet8, str5)) {
                                                                }
                                                                if (!uidPermissionState.isPermissionGranted(str5)) {
                                                                }
                                                            } else {
                                                                arraySet8 = arraySet2;
                                                            }
                                                            if (!permission3.isDevelopment()) {
                                                            }
                                                            if (!uidPermissionState.isPermissionGranted(str5)) {
                                                            }
                                                        }
                                                        arraySet10 = arraySet;
                                                    }
                                                    if (permission3.isInternal() || (permission3.isPrivileged() && !CollectionUtils.contains(arraySet9, str5))) {
                                                        arraySet10 = arraySet;
                                                    } else {
                                                        arraySet10 = arraySet;
                                                        if (!CollectionUtils.contains(arraySet10, str5)) {
                                                            if (permission3.isPrivileged()) {
                                                            }
                                                            if (!permission3.isDevelopment()) {
                                                            }
                                                        }
                                                    }
                                                    if (orCreateUidState.revokePermission(permission3)) {
                                                        i4 = 1;
                                                    }
                                                    PermissionState permissionState2 = uidPermissionState.getPermissionState(name);
                                                    orCreateUidState.updatePermissionFlags(permission3, 261119, permissionState2 == null ? permissionState2.getFlags() : 0);
                                                    i3++;
                                                    arraySet3 = arraySet7;
                                                    arraySet5 = arraySet9;
                                                    arraySet2 = arraySet8;
                                                    arraySet = arraySet10;
                                                    collection = collection3;
                                                    size = i17;
                                                    requestedPermissions = list;
                                                    i2 = i18;
                                                    arraySet18 = arraySet6;
                                                    sparseBooleanArray = sparseBooleanArray2;
                                                    orCreateUserState = userPermissionState3;
                                                    packageStateInternal3 = packageStateInternal2;
                                                    str2 = str;
                                                }
                                            }
                                            z3 = true;
                                            if (!permission3.isNormal()) {
                                                if (!permission3.isRuntime()) {
                                                }
                                                arraySet10 = arraySet;
                                                arraySet7 = arraySet3;
                                                arraySet8 = arraySet2;
                                                arraySet9 = arraySet5;
                                                i3++;
                                                arraySet3 = arraySet7;
                                                arraySet5 = arraySet9;
                                                arraySet2 = arraySet8;
                                                arraySet = arraySet10;
                                                collection = collection3;
                                                size = i17;
                                                requestedPermissions = list;
                                                i2 = i18;
                                                arraySet18 = arraySet6;
                                                sparseBooleanArray = sparseBooleanArray2;
                                                orCreateUserState = userPermissionState3;
                                                packageStateInternal3 = packageStateInternal2;
                                                str2 = str;
                                            }
                                            userPermissionState3 = orCreateUserState;
                                            packageStateInternal2 = packageStateInternal3;
                                            sparseBooleanArray2 = sparseBooleanArray;
                                            if (permission3.isNormal()) {
                                                arraySet10 = arraySet;
                                                arraySet7 = arraySet3;
                                                arraySet8 = arraySet2;
                                                arraySet9 = arraySet5;
                                            }
                                            if (permission3.isSignature()) {
                                            }
                                            if (permission3.isInternal()) {
                                            }
                                            arraySet10 = arraySet;
                                            if (orCreateUidState.revokePermission(permission3)) {
                                            }
                                            PermissionState permissionState22 = uidPermissionState.getPermissionState(name);
                                            orCreateUidState.updatePermissionFlags(permission3, 261119, permissionState22 == null ? permissionState22.getFlags() : 0);
                                            i3++;
                                            arraySet3 = arraySet7;
                                            arraySet5 = arraySet9;
                                            arraySet2 = arraySet8;
                                            arraySet = arraySet10;
                                            collection = collection3;
                                            size = i17;
                                            requestedPermissions = list;
                                            i2 = i18;
                                            arraySet18 = arraySet6;
                                            sparseBooleanArray = sparseBooleanArray2;
                                            orCreateUserState = userPermissionState3;
                                            packageStateInternal3 = packageStateInternal2;
                                            str2 = str;
                                        }
                                    } else if (str2 != null) {
                                        str2.equals(androidPackage.getPackageName());
                                    }
                                    userPermissionState3 = orCreateUserState;
                                    packageStateInternal2 = packageStateInternal3;
                                    arraySet6 = arraySet18;
                                    sparseBooleanArray2 = sparseBooleanArray;
                                    arraySet10 = arraySet;
                                    arraySet7 = arraySet3;
                                    arraySet8 = arraySet2;
                                    arraySet9 = arraySet5;
                                    i3++;
                                    arraySet3 = arraySet7;
                                    arraySet5 = arraySet9;
                                    arraySet2 = arraySet8;
                                    arraySet = arraySet10;
                                    collection = collection3;
                                    size = i17;
                                    requestedPermissions = list;
                                    i2 = i18;
                                    arraySet18 = arraySet6;
                                    sparseBooleanArray = sparseBooleanArray2;
                                    orCreateUserState = userPermissionState3;
                                    packageStateInternal3 = packageStateInternal2;
                                    str2 = str;
                                }
                                userPermissionState = orCreateUserState;
                                int i20 = i2;
                                int i21 = size;
                                packageStateInternal = packageStateInternal3;
                                ArraySet arraySet19 = arraySet18;
                                Collection collection4 = collection;
                                List list3 = requestedPermissions;
                                ArraySet arraySet20 = arraySet;
                                ArraySet arraySet21 = arraySet3;
                                ArraySet arraySet22 = arraySet2;
                                ArraySet arraySet23 = arraySet5;
                                SparseBooleanArray sparseBooleanArray6 = sparseBooleanArray;
                                if (i4 == 0 && !z) {
                                    userPermissionState2 = userPermissionState;
                                    if (!packageStateInternal.isUpdatedSystemApp()) {
                                        z2 = true;
                                        if (i4 != 0) {
                                            iArr2 = (str == null || !z) ? iArr9 : ArrayUtils.appendInt(iArr9, i15);
                                            z9 = z2;
                                        } else {
                                            iArr2 = iArr9;
                                        }
                                        PackageStateInternal packageStateInternal5 = packageStateInternal4;
                                        iArr7 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet19, i15, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), collection2, i20, i15, iArr2));
                                        i13 = i16 + 1;
                                        arraySet14 = arraySet23;
                                        length2 = i14;
                                        arraySet3 = arraySet21;
                                        arraySet = arraySet20;
                                        i2 = i20;
                                        size = i21;
                                        z7 = z9;
                                        collection = collection4;
                                        iArr3 = iArr8;
                                        requestedPermissions = list3;
                                        sparseBooleanArray3 = sparseBooleanArray6;
                                        z6 = z;
                                        arraySet2 = arraySet22;
                                        packageStateInternal4 = packageStateInternal5;
                                        packageStateInternal3 = packageStateInternal;
                                        str2 = str;
                                    }
                                    z2 = true;
                                    userPermissionState2.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                                    if (i4 != 0) {
                                    }
                                    PackageStateInternal packageStateInternal52 = packageStateInternal4;
                                    iArr7 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet19, i15, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), collection2, i20, i15, iArr2));
                                    i13 = i16 + 1;
                                    arraySet14 = arraySet23;
                                    length2 = i14;
                                    arraySet3 = arraySet21;
                                    arraySet = arraySet20;
                                    i2 = i20;
                                    size = i21;
                                    z7 = z9;
                                    collection = collection4;
                                    iArr3 = iArr8;
                                    requestedPermissions = list3;
                                    sparseBooleanArray3 = sparseBooleanArray6;
                                    z6 = z;
                                    arraySet2 = arraySet22;
                                    packageStateInternal4 = packageStateInternal52;
                                    packageStateInternal3 = packageStateInternal;
                                    str2 = str;
                                }
                                userPermissionState2 = userPermissionState;
                                if (!userPermissionState2.areInstallPermissionsFixed(packageStateInternal.getPackageName())) {
                                }
                                if (!packageStateInternal.isUpdatedSystemApp()) {
                                }
                                z2 = true;
                                userPermissionState2.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                                if (i4 != 0) {
                                }
                                PackageStateInternal packageStateInternal522 = packageStateInternal4;
                                iArr7 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet19, i15, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), collection2, i20, i15, iArr2));
                                i13 = i16 + 1;
                                arraySet14 = arraySet23;
                                length2 = i14;
                                arraySet3 = arraySet21;
                                arraySet = arraySet20;
                                i2 = i20;
                                size = i21;
                                z7 = z9;
                                collection = collection4;
                                iArr3 = iArr8;
                                requestedPermissions = list3;
                                sparseBooleanArray3 = sparseBooleanArray6;
                                z6 = z;
                                arraySet2 = arraySet22;
                                packageStateInternal4 = packageStateInternal522;
                                packageStateInternal3 = packageStateInternal;
                                str2 = str;
                            } else {
                                i3 = 0;
                            }
                            uidPermissionState = orCreateUidState;
                            ArraySet arraySet182 = new ArraySet();
                            androidPackage.getPackageName();
                            androidPackage.getUid();
                            int[] iArr92 = iArr7;
                            i4 = i3;
                            while (i3 < size) {
                            }
                            userPermissionState = orCreateUserState;
                            int i202 = i2;
                            int i212 = size;
                            packageStateInternal = packageStateInternal3;
                            ArraySet arraySet192 = arraySet182;
                            Collection collection42 = collection;
                            List list32 = requestedPermissions;
                            ArraySet arraySet202 = arraySet;
                            ArraySet arraySet212 = arraySet3;
                            ArraySet arraySet222 = arraySet2;
                            ArraySet arraySet232 = arraySet5;
                            SparseBooleanArray sparseBooleanArray62 = sparseBooleanArray;
                            if (i4 == 0) {
                                userPermissionState2 = userPermissionState;
                                if (!packageStateInternal.isUpdatedSystemApp()) {
                                }
                                z2 = true;
                                userPermissionState2.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                                if (i4 != 0) {
                                }
                                PackageStateInternal packageStateInternal5222 = packageStateInternal4;
                                iArr7 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet192, i15, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), collection2, i202, i15, iArr2));
                                i13 = i16 + 1;
                                arraySet14 = arraySet232;
                                length2 = i14;
                                arraySet3 = arraySet212;
                                arraySet = arraySet202;
                                i2 = i202;
                                size = i212;
                                z7 = z9;
                                collection = collection42;
                                iArr3 = iArr8;
                                requestedPermissions = list32;
                                sparseBooleanArray3 = sparseBooleanArray62;
                                z6 = z;
                                arraySet2 = arraySet222;
                                packageStateInternal4 = packageStateInternal5222;
                                packageStateInternal3 = packageStateInternal;
                                str2 = str;
                            }
                            userPermissionState2 = userPermissionState;
                            if (!userPermissionState2.areInstallPermissionsFixed(packageStateInternal.getPackageName())) {
                            }
                            if (!packageStateInternal.isUpdatedSystemApp()) {
                            }
                            z2 = true;
                            userPermissionState2.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                            if (i4 != 0) {
                            }
                            PackageStateInternal packageStateInternal52222 = packageStateInternal4;
                            iArr7 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet192, i15, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), collection2, i202, i15, iArr2));
                            i13 = i16 + 1;
                            arraySet14 = arraySet232;
                            length2 = i14;
                            arraySet3 = arraySet212;
                            arraySet = arraySet202;
                            i2 = i202;
                            size = i212;
                            z7 = z9;
                            collection = collection42;
                            iArr3 = iArr8;
                            requestedPermissions = list32;
                            sparseBooleanArray3 = sparseBooleanArray62;
                            z6 = z;
                            arraySet2 = arraySet222;
                            packageStateInternal4 = packageStateInternal52222;
                            packageStateInternal3 = packageStateInternal;
                            str2 = str;
                        } catch (Throwable th) {
                            th = th;
                            packageStateInternal3 = packageStateInternal4;
                            throw th;
                        }
                    }
                    boolean z14 = z7;
                    boolean z15 = z6;
                    int[] iArr10 = iArr3;
                    int[] checkIfLegacyStorageOpsNeedToBeUpdated = checkIfLegacyStorageOpsNeedToBeUpdated(androidPackage, z15, iArr10, iArr7);
                    if (permissionCallback != null) {
                        permissionCallback.onPermissionUpdated(checkIfLegacyStorageOpsNeedToBeUpdated, (str != null && z15 && z14) || z8);
                    }
                    for (int i22 : checkIfLegacyStorageOpsNeedToBeUpdated) {
                        notifyRuntimePermissionStateChanged(androidPackage.getPackageName(), i22);
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                packageStateInternal3 = obj;
            }
        }
    }

    public final int[] getAllUserIds() {
        return UserManagerService.getInstance().getUserIdsIncludingPreCreated();
    }

    public final int[] revokePermissionsNoLongerImplicitLocked(UidPermissionState uidPermissionState, String str, Collection collection, int i, int i2, int[] iArr) {
        Permission permission;
        int i3;
        boolean z = i >= 23;
        for (String str2 : uidPermissionState.getGrantedPermissions()) {
            if (!collection.contains(str2) && (permission = this.mRegistry.getPermission(str2)) != null && permission.isRuntime()) {
                int permissionFlags = uidPermissionState.getPermissionFlags(str2);
                if ((permissionFlags & 128) != 0) {
                    boolean z2 = ArrayUtils.contains(NEARBY_DEVICES_PERMISSIONS, str2) && uidPermissionState.isPermissionGranted("android.permission.ACCESS_BACKGROUND_LOCATION") && (uidPermissionState.getPermissionFlags("android.permission.ACCESS_BACKGROUND_LOCATION") & 136) == 0;
                    if ((permissionFlags & 52) == 0 && z && !z2) {
                        uidPermissionState.revokePermission(permission);
                        i3 = 131;
                    } else {
                        i3 = 128;
                    }
                    uidPermissionState.updatePermissionFlags(permission, i3, 0);
                    iArr = ArrayUtils.appendInt(iArr, i2);
                }
            }
        }
        return iArr;
    }

    public final void inheritPermissionStateToNewImplicitPermissionLocked(ArraySet arraySet, String str, UidPermissionState uidPermissionState, AndroidPackage androidPackage) {
        androidPackage.getPackageName();
        int size = arraySet.size();
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            String str2 = (String) arraySet.valueAt(i2);
            if (uidPermissionState.isPermissionGranted(str2)) {
                if (!z) {
                    i = 0;
                }
                i |= uidPermissionState.getPermissionFlags(str2);
                z = true;
            } else if (!z) {
                i |= uidPermissionState.getPermissionFlags(str2);
            }
        }
        if (z) {
            uidPermissionState.grantPermission(this.mRegistry.getPermission(str));
        }
        uidPermissionState.updatePermissionFlags(this.mRegistry.getPermission(str), i, i);
    }

    public final int[] checkIfLegacyStorageOpsNeedToBeUpdated(AndroidPackage androidPackage, boolean z, int[] iArr, int[] iArr2) {
        return (z && androidPackage.isRequestLegacyExternalStorage() && (androidPackage.getRequestedPermissions().contains("android.permission.READ_EXTERNAL_STORAGE") || androidPackage.getRequestedPermissions().contains("android.permission.WRITE_EXTERNAL_STORAGE"))) ? (int[]) iArr.clone() : iArr2;
    }

    public final int[] setInitialGrantForNewImplicitPermissionsLocked(UidPermissionState uidPermissionState, UidPermissionState uidPermissionState2, AndroidPackage androidPackage, ArraySet arraySet, int i, int[] iArr) {
        int i2;
        boolean z;
        androidPackage.getPackageName();
        ArrayMap arrayMap = new ArrayMap();
        List splitPermissionInfos = getSplitPermissionInfos();
        int size = splitPermissionInfos.size();
        for (int i3 = 0; i3 < size; i3++) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo = (PermissionManager.SplitPermissionInfo) splitPermissionInfos.get(i3);
            List newPermissions = splitPermissionInfo.getNewPermissions();
            int size2 = newPermissions.size();
            for (int i4 = 0; i4 < size2; i4++) {
                String str = (String) newPermissions.get(i4);
                ArraySet arraySet2 = (ArraySet) arrayMap.get(str);
                if (arraySet2 == null) {
                    arraySet2 = new ArraySet();
                    arrayMap.put(str, arraySet2);
                }
                arraySet2.add(splitPermissionInfo.getSplitPermission());
            }
        }
        int size3 = arraySet.size();
        int[] iArr2 = iArr;
        for (0; i2 < size3; i2 + 1) {
            String str2 = (String) arraySet.valueAt(i2);
            ArraySet arraySet3 = (ArraySet) arrayMap.get(str2);
            if (arraySet3 != null) {
                Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    throw new IllegalStateException("Unknown new permission in split permission: " + str2);
                }
                if (permission.isRuntime()) {
                    if (!str2.equals("android.permission.ACTIVITY_RECOGNITION") && !READ_MEDIA_AURAL_PERMISSIONS.contains(str2) && !READ_MEDIA_VISUAL_PERMISSIONS.contains(str2)) {
                        uidPermissionState2.updatePermissionFlags(permission, 128, 128);
                    }
                    iArr2 = ArrayUtils.appendInt(iArr2, i);
                    if (!uidPermissionState.hasPermissionState(arraySet3)) {
                        int i5 = 0;
                        while (true) {
                            if (i5 >= arraySet3.size()) {
                                z = false;
                                break;
                            }
                            String str3 = (String) arraySet3.valueAt(i5);
                            Permission permission2 = this.mRegistry.getPermission(str3);
                            if (permission2 == null) {
                                throw new IllegalStateException("Unknown source permission in split permission: " + str3);
                            }
                            if (!permission2.isRuntime()) {
                                z = true;
                                break;
                            }
                            i5++;
                        }
                        i2 = z ? 0 : i2 + 1;
                    }
                    inheritPermissionStateToNewImplicitPermissionLocked(arraySet3, str2, uidPermissionState2, androidPackage);
                }
            }
        }
        return iArr2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getSplitPermissions() {
        return PermissionManager.splitPermissionInfoListToParcelableList(getSplitPermissionInfos());
    }

    public final List getSplitPermissionInfos() {
        return SystemConfig.getInstance().getSplitPermissions();
    }

    public static boolean isCompatPlatformPermissionForPackage(String str, AndroidPackage androidPackage) {
        int length = CompatibilityPermissionInfo.COMPAT_PERMS.length;
        for (int i = 0; i < length; i++) {
            CompatibilityPermissionInfo compatibilityPermissionInfo = CompatibilityPermissionInfo.COMPAT_PERMS[i];
            if (compatibilityPermissionInfo.getName().equals(str) && androidPackage.getTargetSdkVersion() < compatibilityPermissionInfo.getSdkVersion()) {
                Log.i("PermissionManager", "Auto-granting " + str + " to old pkg " + androidPackage.getPackageName());
                return true;
            }
        }
        return false;
    }

    public final boolean checkPrivilegedPermissionAllowlist(AndroidPackage androidPackage, PackageStateInternal packageStateInternal, Permission permission) {
        if (RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_DISABLE) {
            return true;
        }
        String packageName = androidPackage.getPackageName();
        if (Objects.equals(packageName, "android") || !packageStateInternal.isSystem() || !packageStateInternal.isPrivileged() || !this.mPrivilegedPermissionAllowlistSourcePackageNames.contains(permission.getPackageName())) {
            return true;
        }
        String name = permission.getName();
        Boolean privilegedPermissionAllowlistState = getPrivilegedPermissionAllowlistState(packageStateInternal, name, this.mApexManager.getActiveApexPackageNameContainingPackage(packageName));
        if (privilegedPermissionAllowlistState != null) {
            return privilegedPermissionAllowlistState.booleanValue();
        }
        if (packageStateInternal.isUpdatedSystemApp()) {
            return true;
        }
        if (!this.mSystemReady && !packageStateInternal.isApkInUpdatedApex()) {
            Slog.w("PermissionManager", "Privileged permission " + name + " for package " + packageName + " (" + androidPackage.getPath() + ") not in privapp-permissions allowlist");
            if (RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE) {
                synchronized (this.mLock) {
                    if (this.mPrivappPermissionsViolations == null) {
                        this.mPrivappPermissionsViolations = new ArraySet();
                    }
                    this.mPrivappPermissionsViolations.add(packageName + " (" + androidPackage.getPath() + "): " + name);
                }
            }
        }
        return !RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE;
    }

    public final Boolean getPrivilegedPermissionAllowlistState(PackageState packageState, String str, String str2) {
        PermissionAllowlist permissionAllowlist = SystemConfig.getInstance().getPermissionAllowlist();
        String packageName = packageState.getPackageName();
        if (str2 != null) {
            Boolean privilegedAppAllowlistState = permissionAllowlist.getPrivilegedAppAllowlistState(packageName, str);
            if (privilegedAppAllowlistState != null) {
                Slog.w("PermissionManager", "Package " + packageName + " is an APK in APEX, but has permission allowlist on the system image. Please bundle the allowlist in the " + str2 + " APEX instead.");
            }
            Boolean apexPrivilegedAppAllowlistState = permissionAllowlist.getApexPrivilegedAppAllowlistState(this.mApexManager.getApexModuleNameForPackageName(str2), packageName, str);
            return apexPrivilegedAppAllowlistState != null ? apexPrivilegedAppAllowlistState : privilegedAppAllowlistState;
        }
        if (packageState.isVendor()) {
            return permissionAllowlist.getVendorPrivilegedAppAllowlistState(packageName, str);
        }
        if (packageState.isProduct()) {
            return permissionAllowlist.getProductPrivilegedAppAllowlistState(packageName, str);
        }
        if (packageState.isSystemExt()) {
            return permissionAllowlist.getSystemExtPrivilegedAppAllowlistState(packageName, str);
        }
        return permissionAllowlist.getPrivilegedAppAllowlistState(packageName, str);
    }

    public final boolean shouldGrantPermissionBySignature(AndroidPackage androidPackage, Permission permission) {
        AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage((String) ArrayUtils.firstOrNull(this.mPackageManagerInt.getKnownPackageNames(0, 0)));
        return getSourcePackageSigningDetails(permission).hasCommonSignerWithCapability(androidPackage.getSigningDetails(), 4) || androidPackage.getSigningDetails().hasAncestorOrSelf(androidPackage2.getSigningDetails()) || androidPackage2.getSigningDetails().checkCapability(androidPackage.getSigningDetails(), 4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x0059, code lost:
    
        if (r10.isPrivileged() != false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0061, code lost:
    
        if (canGrantOemPermission(r10, r4) != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldGrantPermissionByProtectionFlags(AndroidPackage androidPackage, PackageStateInternal packageStateInternal, Permission permission, ArraySet arraySet) {
        boolean z;
        boolean isPrivileged = permission.isPrivileged();
        boolean isOem = permission.isOem();
        if ((isPrivileged || isOem) && packageStateInternal.isSystem()) {
            String name = permission.getName();
            if (packageStateInternal.isUpdatedSystemApp()) {
                PackageStateInternal disabledSystemPackage = this.mPackageManagerInt.getDisabledSystemPackage(androidPackage.getPackageName());
                AndroidPackageInternal pkg = disabledSystemPackage == null ? null : disabledSystemPackage.getPkg();
                if (pkg != null && ((isPrivileged && disabledSystemPackage.isPrivileged()) || (isOem && canGrantOemPermission(disabledSystemPackage, name)))) {
                    if (!pkg.getRequestedPermissions().contains(name)) {
                        arraySet.add(name);
                    }
                    z = true;
                }
                z = false;
            } else {
                if (isPrivileged) {
                }
                if (isOem) {
                }
                z = false;
            }
            if (z && isPrivileged && !permission.isVendorPrivileged() && packageStateInternal.isVendor()) {
                Slog.w("PermissionManager", "Permission " + name + " cannot be granted to privileged vendor apk " + androidPackage.getPackageName() + " because it isn't a 'vendorPrivileged' permission.");
            }
            if (!z && permission.isPre23() && androidPackage.getTargetSdkVersion() < 23) {
                z = true;
            }
            if (!z && permission.isInstaller() && (ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(2, 0), androidPackage.getPackageName()) || ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(7, 0), androidPackage.getPackageName()))) {
                z = true;
            }
            if (!z && permission.isVerifier() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(4, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isPreInstalled() && packageStateInternal.isSystem()) {
                z = true;
            }
            if (!z && permission.isKnownSigner()) {
                z = androidPackage.getSigningDetails().hasAncestorOrSelfWithDigest(permission.getKnownCerts());
            }
            if (!z && permission.isSetup() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(1, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isSystemTextClassifier() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(6, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isConfigurator() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(10, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isIncidentReportApprover() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(11, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isAppPredictor() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(12, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isCompanion() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(15, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (!z && permission.isRetailDemo() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(16, 0), androidPackage.getPackageName()) && isProfileOwner(androidPackage.getUid())) {
                z = true;
            }
            if (!z && permission.isRecents() && ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(17, 0), androidPackage.getPackageName())) {
                z = true;
            }
            if (z && permission.isModule() && this.mApexManager.getActiveApexPackageNameContainingPackage(androidPackage.getPackageName()) != null) {
                return true;
            }
            return z;
        }
        z = false;
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = androidPackage.getSigningDetails().hasAncestorOrSelfWithDigest(permission.getKnownCerts());
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (!z) {
            z = true;
        }
        if (z) {
        }
        return z;
    }

    public final SigningDetails getSourcePackageSigningDetails(Permission permission) {
        PackageStateInternal sourcePackageSetting = getSourcePackageSetting(permission);
        if (sourcePackageSetting == null) {
            return SigningDetails.UNKNOWN;
        }
        return sourcePackageSetting.getSigningDetails();
    }

    public final PackageStateInternal getSourcePackageSetting(Permission permission) {
        return this.mPackageManagerInt.getPackageStateInternal(permission.getPackageName());
    }

    public static boolean canGrantOemPermission(PackageState packageState, String str) {
        if (!packageState.isOem()) {
            return false;
        }
        String packageName = packageState.getPackageName();
        Boolean oemAppAllowlistState = SystemConfig.getInstance().getPermissionAllowlist().getOemAppAllowlistState(packageState.getPackageName(), str);
        if (oemAppAllowlistState != null) {
            return Boolean.TRUE == oemAppAllowlistState;
        }
        throw new IllegalStateException("OEM permission " + str + " requested by package " + packageName + " must be explicitly declared granted or not");
    }

    public static boolean isProfileOwner(int i) {
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null) {
            return devicePolicyManagerInternal.isActiveProfileOwner(i) || devicePolicyManagerInternal.isActiveDeviceOwner(i);
        }
        return false;
    }

    public final boolean isPermissionsReviewRequiredInternal(String str, int i) {
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null || androidPackage.getTargetSdkVersion() >= 23) {
            return false;
        }
        synchronized (this.mLock) {
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                return false;
            }
            return uidStateLocked.isPermissionsReviewRequired();
        }
    }

    public final void grantRequestedPermissionsInternal(AndroidPackage androidPackage, ArrayMap arrayMap, int i) {
        boolean z = androidPackage.getTargetSdkVersion() >= 23;
        boolean isInstantApp = this.mPackageManagerInt.isInstantApp(androidPackage.getPackageName(), i);
        int myUid = Process.myUid();
        for (String str : androidPackage.getRequestedPermissions()) {
            Integer num = (Integer) arrayMap.get(str);
            if (num != null && num.intValue() != 0) {
                synchronized (this.mLock) {
                    Permission permission = this.mRegistry.getPermission(str);
                    if (permission != null) {
                        boolean z2 = (permission.isRuntime() || permission.isDevelopment()) && (!isInstantApp || permission.isInstant()) && ((z || !permission.isRuntimeOnly()) && num.intValue() == 1);
                        boolean isAppOp = permission.isAppOp();
                        int permissionFlagsInternal = getPermissionFlagsInternal(androidPackage.getPackageName(), str, myUid, i);
                        if (z2) {
                            if (z) {
                                if ((permissionFlagsInternal & 20) == 0) {
                                    grantRuntimePermissionInternal(androidPackage.getPackageName(), str, false, myUid, i, this.mDefaultPermissionCallback);
                                }
                            } else if ((permissionFlagsInternal & 72) != 0) {
                                updatePermissionFlagsInternal(androidPackage.getPackageName(), str, 72, 0, myUid, i, false, this.mDefaultPermissionCallback);
                            }
                        } else if (isAppOp && PackageInstallerService.INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS.contains(str) && (permissionFlagsInternal & 1) == 0) {
                            final int i2 = num.intValue() == 1 ? 0 : 2;
                            final int uid = UserHandle.getUid(i, androidPackage.getUid());
                            final String permissionToOp = AppOpsManager.permissionToOp(str);
                            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda15
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PermissionManagerServiceImpl.this.lambda$grantRequestedPermissionsInternal$12(permissionToOp, uid, i2);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantRequestedPermissionsInternal$12(String str, int i, int i2) {
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).setUidMode(str, i, i2);
    }

    public final void setAllowlistedRestrictedPermissionsInternal(AndroidPackage androidPackage, List list, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int size = androidPackage.getRequestedPermissions().size();
        int myUid = Process.myUid();
        boolean z = false;
        int i6 = 0;
        ArraySet arraySet = null;
        while (i6 < size) {
            String str = (String) androidPackage.getRequestedPermissions().get(i6);
            synchronized (this.mLock) {
                Permission permission = this.mRegistry.getPermission(str);
                if (permission != null && permission.isHardOrSoftRestricted()) {
                    UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
                    if (uidStateLocked == null) {
                        Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                        i3 = i6;
                    } else {
                        boolean isPermissionGranted = uidStateLocked.isPermissionGranted(str);
                        if (isPermissionGranted) {
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                            }
                            arraySet.add(str);
                        }
                        ArraySet arraySet2 = arraySet;
                        int permissionFlagsInternal = getPermissionFlagsInternal(androidPackage.getPackageName(), str, myUid, i2);
                        int i7 = i;
                        int i8 = permissionFlagsInternal;
                        int i9 = 0;
                        while (i7 != 0) {
                            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i7);
                            i7 &= ~numberOfTrailingZeros;
                            if (numberOfTrailingZeros == 1) {
                                i9 |= IInstalld.FLAG_USE_QUOTA;
                                i8 = (list == null || !list.contains(str)) ? i8 & (-4097) : i8 | IInstalld.FLAG_USE_QUOTA;
                            } else if (numberOfTrailingZeros == 2) {
                                i9 |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
                                i8 = (list == null || !list.contains(str)) ? i8 & (-2049) : i8 | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
                            } else if (numberOfTrailingZeros == 4) {
                                i9 |= IInstalld.FLAG_FORCE;
                                i8 = (list == null || !list.contains(str)) ? i8 & (-8193) : i8 | IInstalld.FLAG_FORCE;
                            }
                        }
                        if (permissionFlagsInternal == i8) {
                            i3 = i6;
                            arraySet = arraySet2;
                        } else {
                            boolean z2 = (permissionFlagsInternal & 14336) != 0;
                            boolean z3 = (i8 & 14336) != 0;
                            if ((permissionFlagsInternal & 4) != 0 && !z3 && isPermissionGranted) {
                                i9 |= 4;
                                i8 &= -5;
                            }
                            if (androidPackage.getTargetSdkVersion() >= 23 || z2 || !z3) {
                                i4 = i8;
                                i5 = i9;
                            } else {
                                i5 = i9 | 64;
                                i4 = i8 | 64;
                            }
                            i3 = i6;
                            updatePermissionFlagsInternal(androidPackage.getPackageName(), str, i5, i4, myUid, i2, false, null);
                            arraySet = arraySet2;
                            z = true;
                        }
                    }
                }
                i3 = i6;
            }
            i6 = i3 + 1;
        }
        if (z) {
            restorePermissionState(androidPackage, false, androidPackage.getPackageName(), this.mDefaultPermissionCallback, i2);
            if (PmHook.isPkgLicenseActivated(androidPackage.getPackageName())) {
                PmHook.updateAdminPermissions();
            }
            if (arraySet == null) {
                return;
            }
            int size2 = arraySet.size();
            for (int i10 = 0; i10 < size2; i10++) {
                String str2 = (String) arraySet.valueAt(i10);
                synchronized (this.mLock) {
                    UidPermissionState uidStateLocked2 = getUidStateLocked(androidPackage, i2);
                    if (uidStateLocked2 == null) {
                        Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                    } else {
                        boolean isPermissionGranted2 = uidStateLocked2.isPermissionGranted(str2);
                        if (!isPermissionGranted2) {
                            this.mDefaultPermissionCallback.onPermissionRevoked(androidPackage.getUid(), i2, null);
                            return;
                        }
                    }
                }
            }
        }
    }

    public final void revokeSharedUserPermissionsForLeavingPackageInternal(AndroidPackage androidPackage, final int i, List list, int i2) {
        boolean z;
        if (androidPackage == null) {
            Slog.i("PermissionManager", "Trying to update info for null package. Just ignoring");
            return;
        }
        if (list.isEmpty()) {
            return;
        }
        PackageStateInternal disabledSystemPackage = this.mPackageManagerInt.getDisabledSystemPackage(androidPackage.getPackageName());
        boolean z2 = disabledSystemPackage != null && disabledSystemPackage.getAppId() == androidPackage.getUid();
        boolean z3 = false;
        for (String str : androidPackage.getRequestedPermissions()) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                AndroidPackage androidPackage2 = (AndroidPackage) it.next();
                if (androidPackage2 != null && !androidPackage2.getPackageName().equals(androidPackage.getPackageName()) && androidPackage2.getRequestedPermissions().contains(str)) {
                    z = true;
                    break;
                }
            }
            if (!z && (!z2 || !disabledSystemPackage.getPkg().getRequestedPermissions().contains(str))) {
                synchronized (this.mLock) {
                    UidPermissionState uidStateLocked = getUidStateLocked(i, i2);
                    if (uidStateLocked == null) {
                        Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                    } else {
                        Permission permission = this.mRegistry.getPermission(str);
                        if (permission != null) {
                            if (uidStateLocked.removePermissionState(permission.getName()) && permission.hasGids()) {
                                z3 = true;
                            }
                        }
                    }
                }
            }
        }
        if (z3) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl.killUid(i, -1, "permission grant or revoke changed gids");
                }
            });
        }
    }

    public final boolean revokeUnusedSharedUserPermissionsLocked(Collection collection, UidPermissionState uidPermissionState) {
        Permission permission;
        List permissionStates = uidPermissionState.getPermissionStates();
        boolean z = false;
        for (int size = permissionStates.size() - 1; size >= 0; size--) {
            PermissionState permissionState = (PermissionState) permissionStates.get(size);
            if (!collection.contains(permissionState.getName()) && (permission = this.mRegistry.getPermission(permissionState.getName())) != null && uidPermissionState.removePermissionState(permission.getName()) && permission.isRuntime()) {
                z = true;
            }
        }
        return z;
    }

    public final void updatePermissions(String str, AndroidPackage androidPackage) {
        updatePermissions(str, androidPackage, getVolumeUuidForPackage(androidPackage), androidPackage == null ? 3 : 2, this.mDefaultPermissionCallback);
    }

    public final void updateAllPermissions(String str, boolean z) {
        PackageManager.corkPackageInfoCache();
        try {
            updatePermissions(null, null, str, 1 | (z ? 6 : 0), this.mDefaultPermissionCallback);
        } finally {
            PackageManager.uncorkPackageInfoCache();
        }
    }

    public final void updatePermissions(final String str, final AndroidPackage androidPackage, final String str2, int i, final PermissionCallback permissionCallback) {
        int i2;
        if (updatePermissionTreeSourcePackage(str, androidPackage) || updatePermissionSourcePackage(str, permissionCallback)) {
            Slog.i("PermissionManager", "Permission ownership changed. Updating all permissions.");
            i2 = i | 1;
        } else {
            i2 = i;
        }
        Trace.traceBegin(262144L, "restorePermissionState");
        int i3 = i2 & 1;
        if (i3 != 0) {
            final boolean z = (i2 & 4) != 0;
            this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerServiceImpl.this.lambda$updatePermissions$14(androidPackage, z, str2, str, permissionCallback, (AndroidPackage) obj);
                }
            });
        }
        if (androidPackage != null) {
            restorePermissionState(androidPackage, (i2 & 2) != 0 && Objects.equals(str2, getVolumeUuidForPackage(androidPackage)), str, permissionCallback, -1);
        }
        if (i3 != 0 || str == null || PmHook.isPkgLicenseActivated(str)) {
            try {
                PmHook.updateAdminPermissions();
            } catch (Exception e) {
                Log.e("PermissionManager", "UpdateAdminPermissions error - " + e.getMessage() + " as changingPackageName - " + str + " with update flag value - " + i2);
            }
        }
        Trace.traceEnd(262144L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissions$14(AndroidPackage androidPackage, boolean z, String str, String str2, PermissionCallback permissionCallback, AndroidPackage androidPackage2) {
        if (androidPackage2 == androidPackage) {
            return;
        }
        restorePermissionState(androidPackage2, z && Objects.equals(str, getVolumeUuidForPackage(androidPackage2)), str2, permissionCallback, -1);
    }

    public final boolean updatePermissionSourcePackage(String str, final PermissionCallback permissionCallback) {
        ArraySet<Permission> arraySet;
        boolean z;
        if (str == null) {
            return true;
        }
        synchronized (this.mLock) {
            arraySet = null;
            z = false;
            for (Permission permission : this.mRegistry.getPermissions()) {
                if (permission.isDynamic()) {
                    permission.updateDynamicPermission(this.mRegistry.getPermissionTrees());
                }
                if (str.equals(permission.getPackageName())) {
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                    }
                    arraySet.add(permission);
                    z = true;
                }
            }
        }
        if (arraySet != null) {
            AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
            for (final Permission permission2 : arraySet) {
                if (androidPackage == null || !hasPermission(androidPackage, permission2.getName())) {
                    if (!isPermissionDeclaredByDisabledSystemPkg(permission2)) {
                        Slog.i("PermissionManager", "Removing permission " + permission2.getName() + " that used to be declared by " + permission2.getPackageName());
                        if (permission2.isRuntime()) {
                            for (final int i : this.mUserManagerInt.getUserIds()) {
                                this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda24
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        PermissionManagerServiceImpl.this.lambda$updatePermissionSourcePackage$15(permission2, i, permissionCallback, (AndroidPackage) obj);
                                    }
                                });
                            }
                        } else {
                            this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda25
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    PermissionManagerServiceImpl.this.lambda$updatePermissionSourcePackage$16(permission2, (AndroidPackage) obj);
                                }
                            });
                        }
                    }
                    synchronized (this.mLock) {
                        this.mRegistry.removePermission(permission2.getName());
                    }
                } else {
                    AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(permission2.getPackageName());
                    PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(permission2.getPackageName());
                    synchronized (this.mLock) {
                        if (androidPackage2 == null || packageStateInternal == null) {
                            Slog.w("PermissionManager", "Removing dangling permission: " + permission2.getName() + " from package " + permission2.getPackageName());
                            this.mRegistry.removePermission(permission2.getName());
                        }
                    }
                }
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissionSourcePackage$15(Permission permission, int i, PermissionCallback permissionCallback, AndroidPackage androidPackage) {
        revokePermissionFromPackageForUser(androidPackage.getPackageName(), permission.getName(), true, i, permissionCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissionSourcePackage$16(Permission permission, AndroidPackage androidPackage) {
        int[] userIds = this.mUserManagerInt.getUserIds();
        synchronized (this.mLock) {
            for (int i : userIds) {
                UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                } else {
                    uidStateLocked.removePermissionState(permission.getName());
                }
            }
        }
    }

    public final boolean isPermissionDeclaredByDisabledSystemPkg(Permission permission) {
        PackageStateInternal disabledSystemPackage = this.mPackageManagerInt.getDisabledSystemPackage(permission.getPackageName());
        if (disabledSystemPackage == null || disabledSystemPackage.getPkg() == null) {
            return false;
        }
        String name = permission.getName();
        for (ParsedPermission parsedPermission : disabledSystemPackage.getPkg().getPermissions()) {
            if (TextUtils.equals(name, parsedPermission.getName()) && permission.getProtectionLevel() == parsedPermission.getProtectionLevel()) {
                return true;
            }
        }
        return false;
    }

    public final void revokePermissionFromPackageForUser(String str, String str2, boolean z, int i, PermissionCallback permissionCallback) {
        ApplicationInfo applicationInfo = this.mPackageManagerInt.getApplicationInfo(str, 0L, 1000, 0);
        if ((applicationInfo == null || applicationInfo.targetSdkVersion >= 23) && checkPermission(str, str2, i) == 0) {
            try {
                revokeRuntimePermissionInternal(str, str2, z, 1000, i, null, permissionCallback);
            } catch (IllegalArgumentException e) {
                Slog.e("PermissionManager", "Failed to revoke " + str2 + " from " + str, e);
            }
        }
    }

    public final boolean updatePermissionTreeSourcePackage(String str, AndroidPackage androidPackage) {
        boolean z;
        if (str == null) {
            return true;
        }
        synchronized (this.mLock) {
            Iterator it = this.mRegistry.getPermissionTrees().iterator();
            z = false;
            while (it.hasNext()) {
                Permission permission = (Permission) it.next();
                if (str.equals(permission.getPackageName())) {
                    if (androidPackage == null || !hasPermission(androidPackage, permission.getName())) {
                        Slog.i("PermissionManager", "Removing permission tree " + permission.getName() + " that used to be declared by " + permission.getPackageName());
                        it.remove();
                    }
                    z = true;
                }
            }
        }
        return z;
    }

    public final void enforceGrantRevokeRuntimePermissionPermissions(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") == 0) {
            return;
        }
        throw new SecurityException(str + " requires android.permission.GRANT_RUNTIME_PERMISSIONS or android.permission.REVOKE_RUNTIME_PERMISSIONS");
    }

    public final void enforceGrantRevokeGetRuntimePermissionPermissions(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.GET_RUNTIME_PERMISSIONS") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") == 0) {
            return;
        }
        throw new SecurityException(str + " requires android.permission.GRANT_RUNTIME_PERMISSIONS or android.permission.REVOKE_RUNTIME_PERMISSIONS or android.permission.GET_RUNTIME_PERMISSIONS");
    }

    public final void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, String str) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Invalid userId " + i2);
        }
        if (z2) {
            enforceShellRestriction("no_debugging_features", i, i2);
        }
        if (checkCrossUserPermission(i, UserHandle.getUserId(i), i2, z)) {
            return;
        }
        if (SemDualAppManager.isDualAppId(UserHandle.getUserId(i)) && i2 == 0) {
            return;
        }
        String buildInvalidCrossUserPermissionMessage = buildInvalidCrossUserPermissionMessage(i, i2, str, z);
        Slog.w("PermissionManager", buildInvalidCrossUserPermissionMessage);
        throw new SecurityException(buildInvalidCrossUserPermissionMessage);
    }

    public final void enforceShellRestriction(String str, int i, int i2) {
        if (i == 2000) {
            if (i2 >= 0 && this.mUserManagerInt.hasUserRestriction(str, i2)) {
                throw new SecurityException("Shell does not have permission to access user " + i2);
            }
            if (i2 < 0) {
                Slog.e(LOG_TAG, "Unable to check shell permission for user " + i2 + "\n\t" + Debug.getCallers(3));
            }
        }
    }

    public final boolean checkCrossUserPermission(int i, int i2, int i3, boolean z) {
        if (i3 == i2 || i == 1000 || i == 0) {
            return true;
        }
        if (z) {
            return checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        return checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") || checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS");
    }

    public final boolean checkCallingOrSelfPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public static String buildInvalidCrossUserPermissionMessage(int i, int i2, String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires ");
        sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or ");
            sb.append("android.permission.INTERACT_ACROSS_USERS");
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        return sb.toString();
    }

    public final int calculateCurrentPermissionFootprintLocked(Permission permission) {
        Iterator it = this.mRegistry.getPermissions().iterator();
        int i = 0;
        while (it.hasNext()) {
            i += permission.calculateFootprint((Permission) it.next());
        }
        return i;
    }

    public final void enforcePermissionCapLocked(PermissionInfo permissionInfo, Permission permission) {
        if (permission.getUid() != 1000 && calculateCurrentPermissionFootprintLocked(permission) + permissionInfo.calculateFootprint() > 32768) {
            throw new SecurityException("Permission tree size cap exceeded");
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onSystemReady() {
        updateAllPermissions(StorageManager.UUID_PRIVATE_INTERNAL, false);
        ((PermissionPolicyInternal) LocalServices.getService(PermissionPolicyInternal.class)).setOnInitializedCallback(new PermissionPolicyInternal.OnInitializedCallback() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda19
            @Override // com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback
            public final void onInitialized(int i) {
                PermissionManagerServiceImpl.this.lambda$onSystemReady$17(i);
            }
        });
        synchronized (this.mLock) {
            int i = 1;
            this.mSystemReady = true;
            if (this.mPrivappPermissionsViolations != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("We detected priv-permissions violations from below apps:");
                Iterator it = this.mPrivappPermissionsViolations.iterator();
                while (it.hasNext()) {
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + i + ") " + ((String) it.next()));
                    i++;
                }
                sb.append("\nPrivileged apps MUST put their 'signature|privileged' permissions into the allowlist.\n");
                sb.append("Please ASSIGN this issue to above apps to apply.\n");
                Slog.d("PermissionManager", sb.toString());
                PmLog.logDebugInfo(sb.toString());
            }
        }
        this.mPermissionControllerManager = new PermissionControllerManager(this.mContext, PermissionThread.getHandler());
        this.mPermissionPolicyInternal = (PermissionPolicyInternal) LocalServices.getService(PermissionPolicyInternal.class);
        try {
            if ((getPermissionFlags("android", "android.permission.POST_NOTIFICATIONS", 0) & 16) == 0) {
                grantRuntimePermission("android", "android.permission.POST_NOTIFICATIONS", 0);
                updatePermissionFlags("android", "android.permission.POST_NOTIFICATIONS", 4144, 4144, false, 0);
            }
        } catch (Exception unused) {
        }
        PmHook.onSystemReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$17(int i) {
        updateAllPermissions(StorageManager.UUID_PRIVATE_INTERNAL, false);
    }

    public static String getVolumeUuidForPackage(AndroidPackage androidPackage) {
        if (androidPackage == null) {
            return StorageManager.UUID_PRIVATE_INTERNAL;
        }
        if (androidPackage.isExternalStorage()) {
            return TextUtils.isEmpty(androidPackage.getVolumeUuid()) ? "primary_physical" : androidPackage.getVolumeUuid();
        }
        return StorageManager.UUID_PRIVATE_INTERNAL;
    }

    public static boolean hasPermission(AndroidPackage androidPackage, String str) {
        if (androidPackage.getPermissions().isEmpty()) {
            return false;
        }
        for (int size = androidPackage.getPermissions().size() - 1; size >= 0; size--) {
            if (((ParsedPermission) androidPackage.getPermissions().get(size)).getName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final void logPermission(int i, String str, String str2) {
        LogMaker logMaker = new LogMaker(i);
        logMaker.setPackageName(str2);
        logMaker.addTaggedData(1241, str);
        this.mMetricsLogger.write(logMaker);
    }

    public final UidPermissionState getUidStateLocked(PackageStateInternal packageStateInternal, int i) {
        return getUidStateLocked(packageStateInternal.getAppId(), i);
    }

    public final UidPermissionState getUidStateLocked(AndroidPackage androidPackage, int i) {
        return getUidStateLocked(androidPackage.getUid(), i);
    }

    public final UidPermissionState getUidStateLocked(int i, int i2) {
        UserPermissionState userState = this.mState.getUserState(i2);
        if (userState == null) {
            return null;
        }
        return userState.getUidState(i);
    }

    public final void removeUidStateAndResetPackageInstallPermissionsFixed(int i, String str, int i2) {
        synchronized (this.mLock) {
            UserPermissionState userState = this.mState.getUserState(i2);
            if (userState == null) {
                return;
            }
            userState.removeUidState(i);
            userState.setInstallPermissionsFixed(str, false);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionStateTEMP() {
        final int[] allUserIds = getAllUserIds();
        this.mPackageManagerInt.forEachPackageState(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PermissionManagerServiceImpl.this.lambda$readLegacyPermissionStateTEMP$18(allUserIds, (PackageStateInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readLegacyPermissionStateTEMP$18(int[] iArr, PackageStateInternal packageStateInternal) {
        LegacyPermissionState legacyPermissionState;
        int appId = packageStateInternal.getAppId();
        if (packageStateInternal.hasSharedUser()) {
            int sharedUserAppId = packageStateInternal.getSharedUserAppId();
            SharedUserApi sharedUserApi = this.mPackageManagerInt.getSharedUserApi(sharedUserAppId);
            if (sharedUserApi == null) {
                Slog.wtf("PermissionManager", "Missing shared user Api for " + sharedUserAppId);
                return;
            }
            legacyPermissionState = sharedUserApi.getSharedUserLegacyPermissionState();
        } else {
            legacyPermissionState = packageStateInternal.getLegacyPermissionState();
        }
        synchronized (this.mLock) {
            for (int i : iArr) {
                UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(i);
                orCreateUserState.setInstallPermissionsFixed(packageStateInternal.getPackageName(), packageStateInternal.isInstallPermissionsFixed());
                UidPermissionState orCreateUidState = orCreateUserState.getOrCreateUidState(appId);
                orCreateUidState.reset();
                orCreateUidState.setMissing(legacyPermissionState.isMissing(i));
                readLegacyPermissionStatesLocked(orCreateUidState, legacyPermissionState.getPermissionStates(i));
            }
        }
    }

    public final void readLegacyPermissionStatesLocked(UidPermissionState uidPermissionState, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            LegacyPermissionState.PermissionState permissionState = (LegacyPermissionState.PermissionState) it.next();
            String name = permissionState.getName();
            Permission permission = this.mRegistry.getPermission(name);
            if (permission == null) {
                Slog.w("PermissionManager", "Unknown permission: " + name);
            } else {
                uidPermissionState.putPermissionState(permission, permissionState.isGranted(), permissionState.getFlags());
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionStateTEMP() {
        final int[] userIds;
        synchronized (this.mLock) {
            userIds = this.mState.getUserIds();
        }
        this.mPackageManagerInt.forEachPackageSetting(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PermissionManagerServiceImpl.this.lambda$writeLegacyPermissionStateTEMP$19(userIds, (PackageSetting) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeLegacyPermissionStateTEMP$19(int[] iArr, PackageSetting packageSetting) {
        LegacyPermissionState legacyPermissionState;
        PermissionManagerServiceImpl permissionManagerServiceImpl = this;
        int i = 0;
        packageSetting.setInstallPermissionsFixed(false);
        if (packageSetting.hasSharedUser()) {
            int sharedUserAppId = packageSetting.getSharedUserAppId();
            SharedUserApi sharedUserApi = permissionManagerServiceImpl.mPackageManagerInt.getSharedUserApi(sharedUserAppId);
            if (sharedUserApi == null) {
                Slog.wtf("PermissionManager", "Missing shared user Api for " + sharedUserAppId);
                return;
            }
            legacyPermissionState = sharedUserApi.getSharedUserLegacyPermissionState();
        } else {
            legacyPermissionState = packageSetting.getLegacyPermissionState();
        }
        legacyPermissionState.reset();
        int appId = packageSetting.getAppId();
        synchronized (permissionManagerServiceImpl.mLock) {
            int length = iArr.length;
            int i2 = 0;
            while (i2 < length) {
                int i3 = iArr[i2];
                UserPermissionState userState = permissionManagerServiceImpl.mState.getUserState(i3);
                if (userState == null) {
                    Slog.e("PermissionManager", "Missing user state for " + i3);
                } else {
                    if (userState.areInstallPermissionsFixed(packageSetting.getPackageName())) {
                        packageSetting.setInstallPermissionsFixed(true);
                    }
                    UidPermissionState uidState = userState.getUidState(appId);
                    if (uidState == null) {
                        Slog.e("PermissionManager", "Missing permission state for " + packageSetting.getPackageName() + " and user " + i3);
                    } else {
                        legacyPermissionState.setMissing(uidState.isMissing(), i3);
                        List permissionStates = uidState.getPermissionStates();
                        int size = permissionStates.size();
                        for (int i4 = i; i4 < size; i4++) {
                            PermissionState permissionState = (PermissionState) permissionStates.get(i4);
                            legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(permissionState.getName(), permissionState.getPermission().isRuntime(), permissionState.isGranted(), permissionState.getFlags()), i3);
                        }
                    }
                }
                i2++;
                permissionManagerServiceImpl = this;
                i = 0;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
        List permissionTrees;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                permissionTrees = legacyPermissionSettings.getPermissions();
            } else {
                permissionTrees = legacyPermissionSettings.getPermissionTrees();
            }
            synchronized (this.mLock) {
                int size = permissionTrees.size();
                for (int i2 = 0; i2 < size; i2++) {
                    LegacyPermission legacyPermission = (LegacyPermission) permissionTrees.get(i2);
                    Permission permission = new Permission(legacyPermission.getPermissionInfo(), legacyPermission.getType());
                    if (i == 0) {
                        Permission permission2 = this.mRegistry.getPermission(permission.getName());
                        if (permission2 != null && permission2.getType() == 1) {
                            permission.setGids(permission2.getRawGids(), permission2.areGidsPerUser());
                        }
                        this.mRegistry.addPermission(permission);
                    } else {
                        this.mRegistry.addPermissionTree(permission);
                    }
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
        int i = 0;
        while (i < 2) {
            ArrayList arrayList = new ArrayList();
            synchronized (this.mLock) {
                for (Permission permission : i == 0 ? this.mRegistry.getPermissions() : this.mRegistry.getPermissionTrees()) {
                    arrayList.add(new LegacyPermission(permission.getPermissionInfo(), permission.getType(), 0, EmptyArray.INT));
                }
            }
            if (i == 0) {
                legacyPermissionSettings.replacePermissions(arrayList);
            } else {
                legacyPermissionSettings.replacePermissionTrees(arrayList);
            }
            i++;
        }
    }

    public final void onPackageAddedInternal(PackageState packageState, final AndroidPackage androidPackage, boolean z, final AndroidPackage androidPackage2) {
        List addAllPermissionsInternal;
        if (!androidPackage.getAdoptPermissions().isEmpty()) {
            for (int size = androidPackage.getAdoptPermissions().size() - 1; size >= 0; size--) {
                String str = (String) androidPackage.getAdoptPermissions().get(size);
                if (canAdoptPermissionsInternal(str, androidPackage)) {
                    Slog.i("PermissionManager", "Adopting permissions from " + str + " to " + androidPackage.getPackageName());
                    synchronized (this.mLock) {
                        this.mRegistry.transferPermissions(str, androidPackage.getPackageName());
                    }
                }
            }
        }
        if (z) {
            Slog.w("PermissionManager", "Permission groups from package " + androidPackage.getPackageName() + " ignored: instant apps cannot define new permission groups.");
        } else {
            addAllPermissionGroupsInternal(androidPackage);
        }
        if (z) {
            Slog.w("PermissionManager", "Permissions from package " + androidPackage.getPackageName() + " ignored: instant apps cannot define new permissions.");
            addAllPermissionsInternal = null;
        } else {
            addAllPermissionsInternal = addAllPermissionsInternal(packageState, androidPackage);
        }
        final List list = addAllPermissionsInternal;
        final boolean z2 = androidPackage2 != null;
        final boolean z3 = !CollectionUtils.isEmpty(list);
        if (z2 || z3) {
            AsyncTask.execute(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl.this.lambda$onPackageAddedInternal$20(z2, androidPackage, androidPackage2, z3, list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPackageAddedInternal$20(boolean z, AndroidPackage androidPackage, AndroidPackage androidPackage2, boolean z2, List list) {
        if (z) {
            revokeRuntimePermissionsIfGroupChangedInternal(androidPackage, androidPackage2);
            revokeStoragePermissionsIfScopeExpandedInternal(androidPackage, androidPackage2);
            revokeSystemAlertWindowIfUpgradedPast23(androidPackage, androidPackage2);
        }
        if (z2) {
            revokeRuntimePermissionsIfPermissionDefinitionChangedInternal(list);
        }
    }

    public final boolean canAdoptPermissionsInternal(String str, AndroidPackage androidPackage) {
        PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return false;
        }
        if (!packageStateInternal.isSystem()) {
            Slog.w("PermissionManager", "Unable to update from " + packageStateInternal.getPackageName() + " to " + androidPackage.getPackageName() + ": old package not in system partition");
            return false;
        }
        if (this.mPackageManagerInt.getPackage(packageStateInternal.getPackageName()) == null) {
            return true;
        }
        Slog.w("PermissionManager", "Unable to update from " + packageStateInternal.getPackageName() + " to " + androidPackage.getPackageName() + ": old package still exists");
        return false;
    }

    public final boolean isEffectivelyGranted(PermissionState permissionState) {
        int flags = permissionState.getFlags();
        if ((flags & 16) != 0) {
            return true;
        }
        if ((flags & 4) != 0) {
            return (flags & 8) == 0 && permissionState.isGranted();
        }
        if ((flags & 65608) != 0) {
            return false;
        }
        return permissionState.isGranted();
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0072, code lost:
    
        if (r2 == false) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair mergePermissionState(int i, PermissionState permissionState, PermissionState permissionState2) {
        int i2;
        int flags = permissionState2.getFlags();
        boolean isEffectivelyGranted = isEffectivelyGranted(permissionState2);
        int flags2 = permissionState.getFlags();
        boolean isEffectivelyGranted2 = isEffectivelyGranted(permissionState);
        int i3 = flags | flags2;
        int i4 = (524291 & flags) | 0 | (i3 & 14336);
        if ((i4 & 14336) == 0) {
            i4 |= 16384;
        }
        int i5 = i3 & 32820;
        int i6 = i4 | i5;
        if (i5 == 0) {
            i6 |= i3 & 128;
        }
        if ((i6 & 20) == 0) {
            if ((557091 & i6) == 0 && NOTIFICATION_PERMISSIONS.contains(permissionState.getName())) {
                i2 = i3 & 64;
            } else if ((32820 & i6) == 0) {
                i2 = flags & 64;
            }
            i6 |= i2;
        }
        boolean z = true;
        if ((i6 & 16) == 0) {
            if ((flags & 4) == 0) {
                if ((flags2 & 4) != 0) {
                    r5 = isEffectivelyGranted || isEffectivelyGranted2;
                    if (isEffectivelyGranted != isEffectivelyGranted2) {
                        i6 &= -5;
                    }
                } else if ((flags & 32800) == 0) {
                    if ((32800 & flags2) != 0) {
                        if (!isEffectivelyGranted) {
                        }
                    } else if ((flags & 128) == 0 && (flags2 & 128) != 0) {
                        r5 = isEffectivelyGranted || isEffectivelyGranted2;
                        if (isEffectivelyGranted) {
                            i6 &= -129;
                        }
                    }
                }
                int i7 = !r5 ? ((131072 & i3) | i6) & (-129) : i6 & (-65);
                if (r5 != isEffectivelyGranted) {
                    i7 &= -524292;
                }
                if (r5 && isPermissionSplitFromNonRuntime(permissionState.getName(), this.mPackageManagerInt.getUidTargetSdkVersion(i))) {
                    i7 |= 8;
                } else {
                    z = r5;
                }
                return new Pair(Boolean.valueOf(z), Integer.valueOf(i7));
            }
            r5 = isEffectivelyGranted;
            if (!r5) {
            }
            if (r5 != isEffectivelyGranted) {
            }
            if (r5) {
            }
            z = r5;
            return new Pair(Boolean.valueOf(z), Integer.valueOf(i7));
        }
        r5 = true;
        if (!r5) {
        }
        if (r5 != isEffectivelyGranted) {
        }
        if (r5) {
        }
        z = r5;
        return new Pair(Boolean.valueOf(z), Integer.valueOf(i7));
    }

    public final void handleAppIdMigration(AndroidPackage androidPackage, int i) {
        UidPermissionState uidState;
        PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal.hasSharedUser()) {
            synchronized (this.mLock) {
                for (int i2 : getAllUserIds()) {
                    UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(i2);
                    UidPermissionState uidState2 = orCreateUserState.getUidState(i);
                    if (uidState2 != null) {
                        UidPermissionState uidState3 = orCreateUserState.getUidState(packageStateInternal.getAppId());
                        if (uidState3 == null) {
                            orCreateUserState.createUidStateWithExisting(packageStateInternal.getAppId(), uidState2);
                        } else {
                            List permissionStates = uidState2.getPermissionStates();
                            int size = permissionStates.size();
                            for (int i3 = 0; i3 < size; i3++) {
                                PermissionState permissionState = (PermissionState) permissionStates.get(i3);
                                PermissionState permissionState2 = uidState3.getPermissionState(permissionState.getName());
                                if (permissionState2 != null) {
                                    Pair mergePermissionState = mergePermissionState(packageStateInternal.getAppId(), permissionState, permissionState2);
                                    uidState3.putPermissionState(permissionState.getPermission(), ((Boolean) mergePermissionState.first).booleanValue(), ((Integer) mergePermissionState.second).intValue());
                                } else {
                                    uidState3.putPermissionState(permissionState.getPermission(), permissionState.isGranted(), permissionState.getFlags());
                                }
                            }
                        }
                        orCreateUserState.removeUidState(i);
                    }
                }
            }
            return;
        }
        List packagesForAppId = this.mPackageManagerInt.getPackagesForAppId(i);
        synchronized (this.mLock) {
            for (int i4 : getAllUserIds()) {
                UserPermissionState userState = this.mState.getUserState(i4);
                if (userState != null && (uidState = userState.getUidState(i)) != null) {
                    userState.createUidStateWithExisting(packageStateInternal.getAppId(), uidState);
                    if (packagesForAppId.isEmpty()) {
                        removeUidStateAndResetPackageInstallPermissionsFixed(i, androidPackage.getPackageName(), i4);
                    } else {
                        revokeSharedUserPermissionsForLeavingPackageInternal(androidPackage, i, packagesForAppId, i4);
                    }
                }
            }
        }
    }

    public final void onPackageInstalledInternal(AndroidPackage androidPackage, int i, PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int[] iArr) {
        if (i != -1) {
            handleAppIdMigration(androidPackage, i);
        }
        updatePermissions(androidPackage.getPackageName(), androidPackage);
        for (int i2 : iArr) {
            addAllowlistedRestrictedPermissionsInternal(androidPackage, packageInstalledParams.getAllowlistedRestrictedPermissions(), 2, i2);
            grantRequestedPermissionsInternal(androidPackage, packageInstalledParams.getPermissionStates(), i2);
        }
    }

    public final void addAllowlistedRestrictedPermissionsInternal(AndroidPackage androidPackage, List list, int i, int i2) {
        List allowlistedRestrictedPermissionsInternal = getAllowlistedRestrictedPermissionsInternal(androidPackage, i, i2);
        if (allowlistedRestrictedPermissionsInternal != null) {
            ArraySet arraySet = new ArraySet(allowlistedRestrictedPermissionsInternal);
            arraySet.addAll(list);
            list = new ArrayList(arraySet);
        }
        setAllowlistedRestrictedPermissionsInternal(androidPackage, list, i, i2);
    }

    public final void onPackageRemovedInternal(AndroidPackage androidPackage) {
        removeAllPermissionsInternal(androidPackage);
    }

    public final void onPackageUninstalledInternal(String str, int i, PackageState packageState, AndroidPackage androidPackage, List list, int[] iArr) {
        int i2 = 0;
        if (packageState.isSystem() && androidPackage != null && this.mPackageManagerInt.getPackage(str) != null) {
            int length = iArr.length;
            while (i2 < length) {
                resetRuntimePermissionsInternal(androidPackage, iArr[i2]);
                i2++;
            }
            return;
        }
        updatePermissions(str, null);
        int length2 = iArr.length;
        while (i2 < length2) {
            int i3 = iArr[i2];
            if (list.isEmpty()) {
                removeUidStateAndResetPackageInstallPermissionsFixed(i, str, i3);
            } else {
                revokeSharedUserPermissionsForLeavingPackageInternal(androidPackage, i, list, i3);
            }
            i2++;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getLegacyPermissions() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList();
            for (Permission permission : this.mRegistry.getPermissions()) {
                arrayList.add(new LegacyPermission(permission.getPermissionInfo(), permission.getType(), permission.getUid(), permission.getRawGids()));
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public Map getAllAppOpPermissionPackages() {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            ArrayMap allAppOpPermissionPackages = this.mRegistry.getAllAppOpPermissionPackages();
            arrayMap = new ArrayMap();
            int size = allAppOpPermissionPackages.size();
            for (int i = 0; i < size; i++) {
                arrayMap.put((String) allAppOpPermissionPackages.keyAt(i), new ArraySet((ArraySet) allAppOpPermissionPackages.valueAt(i)));
            }
        }
        return arrayMap;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public LegacyPermissionState getLegacyPermissionState(int i) {
        LegacyPermissionState legacyPermissionState = new LegacyPermissionState();
        synchronized (this.mLock) {
            for (int i2 : this.mState.getUserIds()) {
                UidPermissionState uidStateLocked = getUidStateLocked(i, i2);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for app ID " + i + " and user ID " + i2);
                } else {
                    List permissionStates = uidStateLocked.getPermissionStates();
                    int size = permissionStates.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        PermissionState permissionState = (PermissionState) permissionStates.get(i3);
                        legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(permissionState.getName(), permissionState.getPermission().isRuntime(), permissionState.isGranted(), permissionState.getFlags()), i2);
                    }
                }
            }
        }
        return legacyPermissionState;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int[] getGidsForUid(int i) {
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        synchronized (this.mLock) {
            UidPermissionState uidStateLocked = getUidStateLocked(appId, userId);
            if (uidStateLocked == null) {
                Slog.e("PermissionManager", "Missing permissions state for app ID " + appId + " and user ID " + userId);
                return EMPTY_INT_ARRAY;
            }
            return uidStateLocked.computeGids(this.mGlobalGids, userId);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionsReviewRequired(String str, int i) {
        Objects.requireNonNull(str, "packageName");
        return isPermissionsReviewRequiredInternal(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public Set getInstalledPermissions(String str) {
        Objects.requireNonNull(str, "packageName");
        ArraySet arraySet = new ArraySet();
        synchronized (this.mLock) {
            for (Permission permission : this.mRegistry.getPermissions()) {
                if (Objects.equals(permission.getPackageName(), str)) {
                    arraySet.add(permission.getName());
                }
            }
        }
        return arraySet;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public Set getGrantedPermissions(String str, int i) {
        Objects.requireNonNull(str, "packageName");
        Preconditions.checkArgumentNonNegative(i, "userId");
        return getGrantedPermissionsInternal(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int[] getPermissionGids(String str, int i) {
        Objects.requireNonNull(str, "permissionName");
        Preconditions.checkArgumentNonNegative(i, "userId");
        return getPermissionGidsInternal(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public String[] getAppOpPermissionPackages(String str) {
        Objects.requireNonNull(str, "permissionName");
        return getAppOpPermissionPackagesInternal(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onStorageVolumeMounted(String str, boolean z) {
        if (str == StorageManager.UUID_PRIVATE_INTERNAL && !this.mSystemReady) {
            final PermissionReconsiderUtils permissionReconsiderUtils = new PermissionReconsiderUtils(this.mContext);
            permissionReconsiderUtils.runWhenRollbackPermission(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerServiceImpl.this.lambda$onStorageVolumeMounted$22((Long) obj);
                }
            });
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionReconsiderUtils.this.clearRollbackHistory();
                }
            }, 10000L);
        }
        updateAllPermissions(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStorageVolumeMounted$22(final Long l) {
        this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PermissionManagerServiceImpl.this.lambda$onStorageVolumeMounted$21(l, (AndroidPackage) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStorageVolumeMounted$21(Long l, AndroidPackage androidPackage) {
        resetIfUpdatedPostSnapshot(androidPackage, l.longValue());
    }

    public final void resetIfUpdatedPostSnapshot(AndroidPackage androidPackage, long j) {
        PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal == null) {
            return;
        }
        long lastUpdateTime = packageStateInternal.getLastUpdateTime();
        if (lastUpdateTime < j) {
            return;
        }
        PmLog.logCriticalInfoAndLogcat("Will reconsider permissions for " + packageStateInternal.getPackageName() + " / " + lastUpdateTime);
        synchronized (this.mLock) {
            UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(0);
            orCreateUserState.getOrCreateUidState(packageStateInternal.getAppId()).setMissing(true);
            orCreateUserState.setInstallPermissionsFixed(androidPackage.getPackageName(), false);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissions(AndroidPackage androidPackage, int i) {
        Objects.requireNonNull(androidPackage, "pkg");
        Preconditions.checkArgumentNonNegative(i, "userId");
        resetRuntimePermissionsInternal(androidPackage, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissionsForUser(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        resetRuntimePermissionsInternal(null, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public Permission getPermissionTEMP(String str) {
        Permission permission;
        synchronized (this.mLock) {
            permission = this.mRegistry.getPermission(str);
        }
        return permission;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getAllPermissionsWithProtection(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (Permission permission : this.mRegistry.getPermissions()) {
                if (permission.getProtection() == i) {
                    arrayList.add(permission.generatePermissionInfo(0));
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public List getAllPermissionsWithProtectionFlags(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (Permission permission : this.mRegistry.getPermissions()) {
                if ((permission.getProtectionFlags() & i) == i) {
                    arrayList.add(permission.generatePermissionInfo(0));
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserCreated(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        updateAllPermissions(StorageManager.UUID_PRIVATE_INTERNAL, true);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageAdded(PackageState packageState, boolean z, AndroidPackage androidPackage) {
        Objects.requireNonNull(packageState);
        AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        Objects.requireNonNull(androidPackage2);
        onPackageAddedInternal(packageState, androidPackage2, z, androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageInstalled(AndroidPackage androidPackage, int i, PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
        int[] iArr;
        Objects.requireNonNull(androidPackage, "pkg");
        Objects.requireNonNull(packageInstalledParams, "params");
        Preconditions.checkArgument(i2 >= 0 || i2 == -1, "userId");
        if (i2 == -1) {
            iArr = getAllUserIds();
        } else {
            iArr = new int[]{i2};
        }
        onPackageInstalledInternal(androidPackage, i, packageInstalledParams, iArr);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageRemoved(AndroidPackage androidPackage) {
        Objects.requireNonNull(androidPackage);
        onPackageRemovedInternal(androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageUninstalled(String str, int i, PackageState packageState, AndroidPackage androidPackage, List list, int i2) {
        int[] iArr;
        Objects.requireNonNull(packageState, "packageState");
        Objects.requireNonNull(str, "packageName");
        Objects.requireNonNull(list, "sharedUserPkgs");
        Preconditions.checkArgument(i2 >= 0 || i2 == -1, "userId");
        if (i2 == -1) {
            iArr = getAllUserIds();
        } else {
            iArr = new int[]{i2};
        }
        onPackageUninstalledInternal(str, i, packageState, androidPackage, list, iArr);
    }

    public abstract class PermissionCallback {
        public PermissionCallback() {
        }

        public abstract void onGidsChanged(int i, int i2);

        public abstract void onInstallPermissionGranted();

        public abstract void onInstallPermissionRevoked();

        public abstract void onInstallPermissionUpdatedNotifyListener(int i);

        public abstract void onPermissionGranted(int i, int i2);

        public abstract void onPermissionRemoved();

        public abstract void onPermissionRevoked(int i, int i2, String str, boolean z, String str2);

        public abstract void onPermissionUpdated(int[] iArr, boolean z);

        public abstract void onPermissionUpdatedNotifyListener(int[] iArr, boolean z, int i);

        public void onPermissionRevoked(int i, int i2, String str) {
            onPermissionRevoked(i, i2, str, false);
        }

        public void onPermissionRevoked(int i, int i2, String str, boolean z) {
            onPermissionRevoked(i, i2, str, false, null);
        }
    }

    public final class OnPermissionChangeListeners extends Handler {
        public final RemoteCallbackList mPermissionListeners;

        public OnPermissionChangeListeners(Looper looper) {
            super(looper);
            this.mPermissionListeners = new RemoteCallbackList();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            handleOnPermissionsChanged(message.arg1);
        }

        public void addListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
            this.mPermissionListeners.register(iOnPermissionsChangeListener);
        }

        public void removeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
            this.mPermissionListeners.unregister(iOnPermissionsChangeListener);
        }

        public void onPermissionsChanged(int i) {
            if (this.mPermissionListeners.getRegisteredCallbackCount() > 0) {
                obtainMessage(1, i, 0).sendToTarget();
            }
        }

        public final void handleOnPermissionsChanged(int i) {
            int beginBroadcast = this.mPermissionListeners.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    try {
                        this.mPermissionListeners.getBroadcastItem(i2).onPermissionsChanged(i);
                    } catch (RemoteException e) {
                        Log.e("PermissionManager", "Permission listener is dead", e);
                    }
                } finally {
                    this.mPermissionListeners.finishBroadcast();
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeInstallPermission(String str, String str2) {
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str2);
            if (permission == null) {
                throw new IllegalArgumentException("Unknown permission: " + str2);
            }
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, 0);
            if (uidStateLocked == null || (!uidStateLocked.hasPermissionState(str2) && !androidPackage.getRequestedPermissions().contains(str2))) {
                throw new SecurityException("Package " + androidPackage.getPackageName() + " has not requested permission " + str2);
            }
            if (!uidStateLocked.revokePermission(permission)) {
                throw new RuntimeException("Cannot revoke permission " + str2);
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantInstallPermission(String str, String str2) {
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        synchronized (this.mLock) {
            Permission permission = this.mRegistry.getPermission(str2);
            if (permission == null) {
                throw new IllegalArgumentException("Unknown permission: " + str2);
            }
            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, 0);
            if (uidStateLocked == null || (!uidStateLocked.hasPermissionState(str2) && !androidPackage.getRequestedPermissions().contains(str2))) {
                throw new SecurityException("Package " + androidPackage.getPackageName() + " has not requested permission " + str2);
            }
            if (!uidStateLocked.grantPermission(permission)) {
                throw new RuntimeException("Cannot grant permission " + str2);
            }
        }
    }
}
