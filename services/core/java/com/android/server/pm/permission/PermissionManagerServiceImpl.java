package com.android.server.pm.permission;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.SigningDetails;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.permission.IOnPermissionsChangeListener;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.pm.permission.CompatibilityPermissionInfo;
import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.pm.pkg.component.ParsedPermissionUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IntPair;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.PermissionThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.Watchdog;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PmHook;
import com.android.server.pm.Settings;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerServiceImpl;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.policy.PermissionPolicyService;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.server.pm.PmLog;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

public final class PermissionManagerServiceImpl implements PermissionManagerServiceInterface {
    public static final long BACKUP_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60);
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Map FULLER_PERMISSION_MAP;
    public static final List NEARBY_DEVICES_PERMISSIONS;
    public static final List NOTIFICATION_PERMISSIONS;
    public static final Set READ_MEDIA_AURAL_PERMISSIONS;
    public static final Set READ_MEDIA_VISUAL_PERMISSIONS;
    public static final List STORAGE_PERMISSIONS;
    public final ApexManager mApexManager;
    public final Context mContext;
    public final AnonymousClass1 mDefaultPermissionCallback;
    public final int[] mGlobalGids;
    public final Handler mHandler;
    public final SparseBooleanArray mHasNoDelayedPermBackup;
    public final boolean mIsLeanback;
    public final PackageManagerTracedLock mLock;
    public final MetricsLogger mMetricsLogger;
    public final OnPermissionChangeListeners mOnPermissionChangeListeners;
    public final ArrayMap mPackageGrantedPermissions;
    public final PackageManagerInternal mPackageManagerInt;
    public PermissionControllerManager mPermissionControllerManager;
    public PermissionPolicyService.Internal mPermissionPolicyInternal;
    public final IPlatformCompat mPlatformCompat;
    public ArraySet mPrivappPermissionsViolations;
    public final ArraySet mPrivilegedPermissionAllowlistSourcePackageNames;
    public final PermissionRegistry mRegistry;
    public final DevicePermissionState mState;
    public final SparseArray mSystemPermissions;
    public boolean mSystemReady;
    public final UserManagerInternal mUserManagerInt;

    /* renamed from: com.android.server.pm.permission.PermissionManagerServiceImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends PermissionCallback {
        public AnonymousClass1() {
        }

        public final Optional getSecureInt(int i, String str) {
            try {
                return Optional.of(Integer.valueOf(Settings.Secure.getIntForUser(PermissionManagerServiceImpl.this.mContext.getContentResolver(), str, i)));
            } catch (Settings.SettingNotFoundException e) {
                int[] iArr = PermissionManagerServiceImpl.EMPTY_INT_ARRAY;
                Slog.i("PermissionManagerServiceImpl", "Setting " + str + " not found", e);
                return Optional.empty();
            }
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public final void onGidsChanged(int i, int i2) {
            PermissionManagerServiceImpl.this.mHandler.post(new PermissionManagerServiceImpl$$ExternalSyntheticLambda6(i, i2, 2));
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public final void onInstallPermissionGranted() {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public final void onInstallPermissionUpdated() {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public final void onPermissionGranted(int i, int i2) {
            PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
            permissionManagerServiceImpl.mOnPermissionChangeListeners.onPermissionsChanged(i);
            permissionManagerServiceImpl.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public final void onPermissionRevoked(final String str, final int i, boolean z, final int i2, final String str2) {
            PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
            permissionManagerServiceImpl.mOnPermissionChangeListeners.onPermissionsChanged(i);
            permissionManagerServiceImpl.mPackageManagerInt.writeSettings(false);
            if (z) {
                return;
            }
            permissionManagerServiceImpl.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    boolean booleanValue;
                    PermissionManagerServiceImpl.AnonymousClass1 anonymousClass1 = PermissionManagerServiceImpl.AnonymousClass1.this;
                    String str3 = str2;
                    int i3 = i;
                    String str4 = str;
                    int i4 = i2;
                    anonymousClass1.getClass();
                    if ("android.permission.POST_NOTIFICATIONS".equals(str3)) {
                        if (PermissionManagerServiceImpl.this.checkUidPermission(i3, "android.permission.BACKUP", "default:0") != 0) {
                            booleanValue = false;
                        } else {
                            int userId = UserHandle.getUserId(i3);
                            final int i5 = 0;
                            Optional map = anonymousClass1.getSecureInt(userId, "user_setup_complete").map(new Function() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda2
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    Integer num = (Integer) obj;
                                    switch (i5) {
                                        case 0:
                                            return Boolean.valueOf(num.intValue() == 0);
                                        default:
                                            return Boolean.valueOf(num.intValue() == 1);
                                    }
                                }
                            });
                            Boolean bool = Boolean.FALSE;
                            if (((Boolean) map.orElse(bool)).booleanValue()) {
                                booleanValue = true;
                            } else {
                                final int i6 = 1;
                                booleanValue = ((Boolean) anonymousClass1.getSecureInt(userId, "user_setup_personalization_state").map(new Function() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Integer num = (Integer) obj;
                                        switch (i6) {
                                            case 0:
                                                return Boolean.valueOf(num.intValue() == 0);
                                            default:
                                                return Boolean.valueOf(num.intValue() == 1);
                                        }
                                    }
                                }).orElse(bool)).booleanValue();
                            }
                        }
                        if (booleanValue) {
                            return;
                        }
                    }
                    int appId = UserHandle.getAppId(i3);
                    if (str4 == null) {
                        PermissionManagerServiceImpl.killUid(appId, i4, "permissions revoked");
                    } else {
                        PermissionManagerServiceImpl.killUid(appId, i4, str4);
                    }
                }
            });
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public final void onPermissionUpdated(int[] iArr, boolean z, int i) {
            int i2 = 0;
            while (true) {
                int length = iArr.length;
                PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
                if (i2 >= length) {
                    permissionManagerServiceImpl.mPackageManagerInt.writePermissionSettings(iArr, !z);
                    return;
                } else {
                    permissionManagerServiceImpl.mOnPermissionChangeListeners.onPermissionsChanged(UserHandle.getUid(iArr[i2], i));
                    i2++;
                }
            }
        }
    }

    public final class OnPermissionChangeListeners extends Handler {
        public final RemoteCallbackList mPermissionListeners;

        public OnPermissionChangeListeners(Looper looper) {
            super(looper);
            this.mPermissionListeners = new RemoteCallbackList();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            int i = message.arg1;
            int beginBroadcast = this.mPermissionListeners.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    try {
                        this.mPermissionListeners.getBroadcastItem(i2).onPermissionsChanged(i, "default:0");
                    } catch (RemoteException e) {
                        Log.e("PermissionManager", "Permission listener is dead", e);
                    }
                } finally {
                    this.mPermissionListeners.finishBroadcast();
                }
            }
        }

        public final void onPermissionsChanged(int i) {
            if (this.mPermissionListeners.getRegisteredCallbackCount() > 0) {
                obtainMessage(1, i, 0).sendToTarget();
            }
        }
    }

    public abstract class PermissionCallback {
        public abstract void onGidsChanged(int i, int i2);

        public abstract void onInstallPermissionGranted();

        public abstract void onInstallPermissionUpdated();

        public abstract void onPermissionGranted(int i, int i2);

        public abstract void onPermissionRevoked(String str, int i, boolean z, int i2, String str2);

        public abstract void onPermissionUpdated(int[] iArr, boolean z, int i);
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

    public PermissionManagerServiceImpl(Context context, ArrayMap arrayMap) {
        String str;
        ArraySet arraySet = new ArraySet();
        this.mPrivilegedPermissionAllowlistSourcePackageNames = arraySet;
        PackageManagerTracedLock packageManagerTracedLock = new PackageManagerTracedLock(null);
        this.mLock = packageManagerTracedLock;
        this.mState = new DevicePermissionState();
        this.mPackageGrantedPermissions = new ArrayMap();
        this.mMetricsLogger = new MetricsLogger();
        this.mPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        this.mRegistry = new PermissionRegistry();
        this.mHasNoDelayedPermBackup = new SparseBooleanArray();
        this.mDefaultPermissionCallback = new AnonymousClass1();
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
        Handler handler = new Handler(Watchdog$$ExternalSyntheticOutline0.m(10, "PermissionManager", true).getLooper());
        this.mHandler = handler;
        Watchdog.getInstance().addThread(handler);
        SystemConfig systemConfig = SystemConfig.getInstance();
        this.mSystemPermissions = systemConfig.mSystemPermissions;
        this.mGlobalGids = systemConfig.mGlobalGids;
        this.mOnPermissionChangeListeners = new OnPermissionChangeListeners(FgThread.get().getLooper());
        ArrayMap arrayMap2 = SystemConfig.getInstance().mPermissions;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            for (int i = 0; i < arrayMap2.size(); i++) {
                try {
                    SystemConfig.PermissionEntry permissionEntry = (SystemConfig.PermissionEntry) arrayMap2.valueAt(i);
                    Permission permission = this.mRegistry.getPermission(permissionEntry.name);
                    if (permission == null) {
                        permission = new Permission(1, permissionEntry.name, "android");
                        this.mRegistry.mPermissions.put(permission.mPermissionInfo.name, permission);
                    }
                    int[] iArr = permissionEntry.gids;
                    if (iArr != null) {
                        boolean z2 = permissionEntry.perUser;
                        permission.mGids = iArr;
                        permission.mGidsPerUser = z2;
                    }
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
        boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public static boolean canGrantOemPermission(PackageState packageState, String str) {
        if (!packageState.isOem()) {
            return false;
        }
        String packageName = packageState.getPackageName();
        ArrayMap arrayMap = (ArrayMap) SystemConfig.getInstance().mPermissionAllowlist.mOemAppAllowlist.get(packageState.getPackageName());
        Boolean bool = arrayMap == null ? null : (Boolean) arrayMap.get(str);
        if (bool != null) {
            return Boolean.TRUE == bool;
        }
        throw new IllegalStateException(XmlUtils$$ExternalSyntheticOutline0.m("OEM permission ", str, " requested by package ", packageName, " must be explicitly declared granted or not"));
    }

    public static int[] getAllUserIds$1() {
        return UserManagerService.getInstance().getUserIdsIncludingPreCreated();
    }

    public static int getAppOp(AndroidPackage androidPackage, String str, Permission permission) {
        int permissionToOpCode = "android".equals(permission.mPermissionInfo.packageName) ? AppOpsManager.permissionToOpCode(permission.mPermissionInfo.name) : -1;
        if (permissionToOpCode != -1) {
            return permissionToOpCode;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(permissionToOpCode, "Invalid appOp ", " for package ");
        m.append(androidPackage.getPackageName());
        m.append(" permission ");
        m.append(str);
        throw new RuntimeException(m.toString());
    }

    public static String getVolumeUuidForPackage(AndroidPackage androidPackage) {
        if (androidPackage != null && androidPackage.isExternalStorage()) {
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

    public static boolean isCompatPlatformPermissionForPackage(AndroidPackage androidPackage, String str) {
        int length = CompatibilityPermissionInfo.COMPAT_PERMS.length;
        for (int i = 0; i < length; i++) {
            CompatibilityPermissionInfo compatibilityPermissionInfo = CompatibilityPermissionInfo.COMPAT_PERMS[i];
            if (compatibilityPermissionInfo.getName().equals(str) && androidPackage.getTargetSdkVersion() < compatibilityPermissionInfo.getSdkVersion()) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Auto-granting ", str, " to old pkg ");
                m.append(androidPackage.getPackageName());
                Log.i("PermissionManager", m.toString());
                return true;
            }
        }
        return false;
    }

    public static boolean isPossibleToModify(int i, AndroidPackage androidPackage, UidPermissionState uidPermissionState, String str) {
        if ((uidPermissionState.getPermissionFlags(str) & 16) == 0) {
            return true;
        }
        List list = PersonaManagerService.containerCriticalApps;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() > 0) {
                arrayList.contains(androidPackage.getPackageName());
            }
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Cannot modify system fixed permission ", str, " for package ");
        m.append(androidPackage.getPackageName());
        m.append(" in user ");
        m.append(i);
        throw new RuntimeException(m.toString());
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

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
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
        return setAllowlistedRestrictedPermissions(i, str, i2, allowlistedRestrictedPermissions);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS", "addOnPermissionsChangeListener");
        this.mOnPermissionChangeListeners.mPermissionListeners.register(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean addPermission(PermissionInfo permissionInfo, boolean z) {
        boolean z2;
        boolean addToTree;
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            throw new SecurityException("Instant apps can't add permissions");
        }
        if (permissionInfo.labelRes == 0 && permissionInfo.nonLocalizedLabel == null) {
            throw new SecurityException("Label must be specified in permission");
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Permission enforcePermissionTree = this.mRegistry.enforcePermissionTree(callingUid, permissionInfo.name);
                Permission permission = this.mRegistry.getPermission(permissionInfo.name);
                z2 = permission == null;
                int fixProtectionLevel = PermissionInfo.fixProtectionLevel(permissionInfo.protectionLevel);
                enforcePermissionCapLocked(permissionInfo, enforcePermissionTree);
                if (z2) {
                    permission = new Permission(2, permissionInfo.name, enforcePermissionTree.mPermissionInfo.packageName);
                } else if (permission.mType != 2) {
                    throw new SecurityException("Not allowed to modify non-dynamic permission " + permissionInfo.name);
                }
                addToTree = permission.addToTree(fixProtectionLevel, permissionInfo, enforcePermissionTree);
                if (z2) {
                    this.mRegistry.mPermissions.put(permission.mPermissionInfo.name, permission);
                }
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        if (addToTree) {
            this.mPackageManagerInt.writeSettings(z);
        }
        return z2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException();
        }
        if (this.mUserManagerInt.exists(i2)) {
            return applyRuntimePermissionsInternalForMDM$1(null, null, i, i2, false);
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "Invalid User ID ", "PermissionManager");
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2) {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException();
        }
        if (!this.mUserManagerInt.exists(i2)) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "Invalid User ID ", "PermissionManager");
            return false;
        }
        if (str != null) {
            return applyRuntimePermissionsInternalForMDM$1(str, list, i, i2, true);
        }
        DualAppManagerService$$ExternalSyntheticOutline0.m("Invalid package name ", str, "PermissionManager");
        return false;
    }

    public final boolean applyRuntimePermissionsInternalForMDM$1(String str, List list, int i, int i2, boolean z) {
        List list2 = PersonaManagerService.containerCriticalApps;
        if (Binder.getCallingUid() != 1000 && str != null && list2 != null) {
            ArrayList arrayList = (ArrayList) list2;
            if (arrayList.size() > 0 && arrayList.contains(str)) {
                DualAppManagerService$$ExternalSyntheticOutline0.m("Package ", str, " is container critical application", "PermissionManager");
                return false;
            }
        }
        if (i == 0) {
            return updatePermissionStatesAndFlagsInternalForMDM(i, 0, i2, str, list, z);
        }
        if (i == 1 || i == 2) {
            return updatePermissionStatesAndFlagsInternalForMDM(i, 4, i2, str, list, z);
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Invalid Permission State ", "PermissionManager");
        return false;
    }

    public final byte[] backupRuntimePermissions(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        final CompletableFuture completableFuture = new CompletableFuture();
        this.mPermissionControllerManager.getRuntimePermissionBackup(UserHandle.of(i), PermissionThread.getExecutor(), new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda3
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

    public final boolean checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(String str) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    Slog.w("PermissionManager", "No such permissions: " + str);
                    return false;
                }
                PermissionInfo permissionInfo = permission.mPermissionInfo;
                String str2 = permissionInfo.packageName;
                int i = permissionInfo.flags;
                boolean z2 = ((i & 12) != 0) && (i & 16) != 0;
                int callingUid = Binder.getCallingUid();
                if (this.mPackageManagerInt.filterAppAccess(callingUid, UserHandle.getUserId(callingUid), str2, false)) {
                    EventLog.writeEvent(1397638484, "186404356", Integer.valueOf(callingUid), str);
                    return false;
                }
                if (!z2 || this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0) {
                    return true;
                }
                throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Cannot modify allowlisting of an immutably restricted permission: ", str));
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int checkPermission(String str, String str2, String str3, int i) {
        AndroidPackage androidPackage;
        if (this.mUserManagerInt.exists(i) && (androidPackage = this.mPackageManagerInt.getPackage(str)) != null) {
            return checkPermissionInternal(androidPackage, true, str2, i);
        }
        return -1;
    }

    public final int checkPermissionInternal(AndroidPackage androidPackage, boolean z, String str, int i) {
        Permission permission;
        int callingUid = Binder.getCallingUid();
        if (z || androidPackage.getSharedUserId() == null) {
            if (this.mPackageManagerInt.filterAppAccess(callingUid, i, androidPackage.getPackageName(), false)) {
                return -1;
            }
        } else if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return -1;
        }
        boolean z2 = this.mPackageManagerInt.getInstantAppPackageName(UserHandle.getUid(i, androidPackage.getUid())) != null;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                    return -1;
                }
                if (uidStateLocked.isPermissionGranted(str)) {
                    if (z2) {
                        Permission permission2 = this.mRegistry.getPermission(str);
                        if (permission2 != null && permission2.isInstant()) {
                        }
                    }
                    return 0;
                }
                String str2 = (String) ((HashMap) FULLER_PERMISSION_MAP).get(str);
                if (str2 != null && uidStateLocked.isPermissionGranted(str2) && (!z2 || ((permission = this.mRegistry.getPermission(str2)) != null && permission.isInstant()))) {
                    return 0;
                }
                return -1;
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int checkUidPermission(int i, String str, String str2) {
        ArraySet arraySet;
        if (!this.mUserManagerInt.exists(UserHandle.getUserId(i))) {
            return -1;
        }
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(i);
        if (androidPackage != null) {
            return checkPermissionInternal(androidPackage, false, str, UserHandle.getUserId(i));
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                ArraySet arraySet2 = (ArraySet) this.mSystemPermissions.get(i);
                if (arraySet2 == null || !arraySet2.contains(str)) {
                    String str3 = (String) ((HashMap) FULLER_PERMISSION_MAP).get(str);
                    if (str3 == null || (arraySet = (ArraySet) this.mSystemPermissions.get(i)) == null || !arraySet.contains(str3)) {
                        return -1;
                    }
                }
                return 0;
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }

    public final void enforceCrossUserPermission(int i, int i2, String str, boolean z) {
        if (i2 < 0) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Invalid userId "));
        }
        if (z && i == 2000) {
            if (i2 >= 0 && this.mUserManagerInt.hasUserRestriction("no_debugging_features", i2)) {
                throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Shell does not have permission to access user "));
            }
            if (i2 < 0) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "Unable to check shell permission for user ", "\n\t");
                m.append(Debug.getCallers(3));
                Slog.e("PermissionManagerServiceImpl", m.toString());
            }
        }
        if (i2 == UserHandle.getUserId(i) || i == 1000 || i == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0) {
            return;
        }
        if (SemDualAppManager.isDualAppId(UserHandle.getUserId(i)) && i2 == 0) {
            return;
        }
        String str2 = str + ": UID " + i + " requires android.permission.INTERACT_ACROSS_USERS_FULL to access user " + i2 + ".";
        Slog.w("PermissionManager", str2);
        throw new SecurityException(str2);
    }

    public final void enforceGrantRevokeRuntimePermissionPermissions(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") != 0) {
            throw new SecurityException(str.concat(" requires android.permission.GRANT_RUNTIME_PERMISSIONS or android.permission.REVOKE_RUNTIME_PERMISSIONS"));
        }
    }

    public final void enforcePermissionCapLocked(PermissionInfo permissionInfo, Permission permission) {
        if (permission.mUid != 1000) {
            int i = 0;
            for (Permission permission2 : this.mRegistry.mPermissions.values()) {
                i += permission.mUid == permission2.mUid ? permission2.mPermissionInfo.calculateFootprint() + permission2.mPermissionInfo.name.length() : 0;
            }
            if (permissionInfo.calculateFootprint() + i > 32768) {
                throw new SecurityException("Permission tree size cap exceeded");
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Map getAllAppOpPermissionPackages() {
        ArrayMap arrayMap;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                ArrayMap arrayMap2 = this.mRegistry.mAppOpPermissionPackages;
                arrayMap = new ArrayMap();
                int size = arrayMap2.size();
                for (int i = 0; i < size; i++) {
                    arrayMap.put((String) arrayMap2.keyAt(i), new ArraySet((ArraySet) arrayMap2.valueAt(i)));
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        return arrayMap;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllPermissionGroups(int i) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Iterator it = this.mRegistry.mPermissionGroups.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(PackageInfoUtils.generatePermissionGroupInfo((ParsedPermissionGroup) it.next(), i));
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        arrayList.removeIf(new PermissionManagerServiceImpl$$ExternalSyntheticLambda1(this, callingUid, UserHandle.getUserId(callingUid), 1));
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Map getAllPermissionStates(String str, String str2, int i) {
        throw new UnsupportedOperationException("This method is supported in newer implementation only");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllPermissionsWithProtection() {
        ArrayList arrayList = new ArrayList();
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                for (Permission permission : this.mRegistry.mPermissions.values()) {
                    if ((permission.mPermissionInfo.protectionLevel & 15) == 1) {
                        arrayList.add(permission.generatePermissionInfo(0, 10000));
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllPermissionsWithProtectionFlags() {
        ArrayList arrayList = new ArrayList();
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                for (Permission permission : this.mRegistry.mPermissions.values()) {
                    if ((permission.mPermissionInfo.protectionLevel & 64) == 64) {
                        arrayList.add(permission.generatePermissionInfo(0, 10000));
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllowlistedRestrictedPermissions(String str, int i, int i2) {
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
        if (this.mPackageManagerInt.filterAppAccess(callingUid, UserHandle.getCallingUserId(), str, false)) {
            return null;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInt).mService.snapshotComputer().isCallerInstallerOfRecord(androidPackage, callingUid);
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
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i2);
                ArrayList arrayList = null;
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                    return null;
                }
                int i3 = (i & 1) != 0 ? 4096 : 0;
                if ((i & 4) != 0) {
                    i3 |= 8192;
                }
                if ((i & 2) != 0) {
                    i3 |= 2048;
                }
                for (String str : androidPackage.getRequestedPermissions()) {
                    if ((uidStateLocked.getPermissionFlags(str) & i3) != 0) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(str);
                    }
                }
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                return arrayList;
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final String[] getAppOpPermissionPackages(String str) {
        String[] strArr;
        Objects.requireNonNull(str, "permissionName");
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mRegistry.mAppOpPermissionPackages.get(str);
                strArr = arraySet == null ? EmptyArray.STRING : (String[]) arraySet.toArray(new String[0]);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return strArr;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final String getDefaultPermissionGrantFingerprint(int i) {
        boolean z;
        Settings.RuntimePermissionPersistence runtimePermissionPersistence = PackageManagerService.this.mSettings.mRuntimePermissionsPersistence;
        synchronized (runtimePermissionPersistence.mLock) {
            z = runtimePermissionPersistence.mPermissionUpgradeNeeded.get(i, true);
        }
        if (z) {
            return null;
        }
        return Build.FINGERPRINT;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int[] getGidsForUid(int i) {
        int[] array;
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                UidPermissionState uidStateLocked = getUidStateLocked(appId, userId);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for app ID " + appId + " and user ID " + userId);
                    return EMPTY_INT_ARRAY;
                }
                IntArray wrap = IntArray.wrap(this.mGlobalGids);
                ArrayMap arrayMap = uidStateLocked.mPermissions;
                if (arrayMap == null) {
                    array = wrap.toArray();
                } else {
                    int size = arrayMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        PermissionState permissionState = (PermissionState) uidStateLocked.mPermissions.valueAt(i2);
                        if (permissionState.isGranted()) {
                            int[] computeGids = permissionState.mPermission.computeGids(userId);
                            if (computeGids.length != 0) {
                                wrap.addAll(computeGids);
                            }
                        }
                    }
                    array = wrap.toArray();
                }
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                return array;
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Set getGrantedPermissions(final int i, String str) {
        Set set;
        Objects.requireNonNull(str, "packageName");
        Preconditions.checkArgumentNonNegative(i, "userId");
        final PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return Collections.emptySet();
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                UidPermissionState uidStateLocked = getUidStateLocked(((PackageSetting) packageStateInternal).mAppId, i);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for " + str + " and user " + i);
                    set = Collections.emptySet();
                } else if (packageStateInternal.getUserStateOrDefault(i).isInstantApp()) {
                    ArraySet arraySet = new ArraySet(uidStateLocked.getGrantedPermissions());
                    arraySet.removeIf(new Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
                            int i2 = i;
                            PackageStateInternal packageStateInternal2 = packageStateInternal;
                            String str2 = (String) obj;
                            Permission permission = permissionManagerServiceImpl.mRegistry.getPermission(str2);
                            if (permission == null) {
                                return true;
                            }
                            if (permission.isInstant()) {
                                return false;
                            }
                            EventLog.writeEvent(1397638484, "140256621", Integer.valueOf(UserHandle.getUid(i2, packageStateInternal2.getAppId())), str2);
                            return true;
                        }
                    });
                    set = arraySet;
                } else {
                    set = uidStateLocked.getGrantedPermissions();
                }
            } finally {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            }
        }
        return set;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Set getInstalledPermissions(String str) {
        Objects.requireNonNull(str, "packageName");
        ArraySet arraySet = new ArraySet();
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                for (Permission permission : this.mRegistry.mPermissions.values()) {
                    if (Objects.equals(permission.mPermissionInfo.packageName, str)) {
                        arraySet.add(permission.mPermissionInfo.name);
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        return arraySet;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final LegacyPermissionState getLegacyPermissionState(int i) {
        LegacyPermissionState legacyPermissionState = new LegacyPermissionState();
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                DevicePermissionState devicePermissionState = this.mState;
                int size = devicePermissionState.mUserStates.size();
                int[] iArr = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = devicePermissionState.mUserStates.keyAt(i2);
                }
                for (int i3 = 0; i3 < size; i3++) {
                    int i4 = iArr[i3];
                    UidPermissionState uidStateLocked = getUidStateLocked(i, i4);
                    if (uidStateLocked == null) {
                        Slog.e("PermissionManager", "Missing permissions state for app ID " + i + " and user ID " + i4);
                    } else {
                        List permissionStates = uidStateLocked.getPermissionStates();
                        int size2 = permissionStates.size();
                        for (int i5 = 0; i5 < size2; i5++) {
                            PermissionState permissionState = (PermissionState) permissionStates.get(i5);
                            legacyPermissionState.putPermissionState(new LegacyPermissionState.PermissionState(permissionState.getName(), permissionState.mPermission.isRuntime(), permissionState.isGranted(), permissionState.getFlags()), i4);
                        }
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        return legacyPermissionState;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getLegacyPermissions() {
        ArrayList arrayList;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                arrayList = new ArrayList();
                for (Permission permission : this.mRegistry.mPermissions.values()) {
                    arrayList.add(new LegacyPermission(permission.mPermissionInfo, permission.mType, permission.mUid, permission.mGids));
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getPackageGrantedPermissionsForMDM(String str) {
        return (List) this.mPackageGrantedPermissions.get(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int getPermissionFlags(String str, String str2, String str3, int i) {
        return getPermissionFlagsInternal(Binder.getCallingUid(), i, str, str2);
    }

    public final int getPermissionFlagsInternal(int i, int i2, String str, String str2) {
        if (!this.mUserManagerInt.exists(i2)) {
            return 0;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.GET_RUNTIME_PERMISSIONS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") != 0) {
            throw new SecurityException("getPermissionFlags requires android.permission.GRANT_RUNTIME_PERMISSIONS or android.permission.REVOKE_RUNTIME_PERMISSIONS or android.permission.GET_RUNTIME_PERMISSIONS");
        }
        enforceCrossUserPermission(i, i2, "getPermissionFlags", false);
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null || this.mPackageManagerInt.filterAppAccess(i, i2, str, false)) {
            return 0;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                if (this.mRegistry.getPermission(str2) == null) {
                    return 0;
                }
                UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i2);
                if (uidStateLocked != null) {
                    return uidStateLocked.getPermissionFlags(str2);
                }
                Slog.e("PermissionManager", "Missing permissions state for " + str + " and user " + i2);
                return 0;
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int[] getPermissionGids(int i, String str) {
        int[] computeGids;
        Objects.requireNonNull(str, "permissionName");
        Preconditions.checkArgumentNonNegative(i, "userId");
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Permission permission = this.mRegistry.getPermission(str);
                computeGids = permission == null ? EmptyArray.INT : permission.computeGids(i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return computeGids;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                ParsedPermissionGroup parsedPermissionGroup = (ParsedPermissionGroup) this.mRegistry.mPermissionGroups.get(str);
                if (parsedPermissionGroup == null) {
                    return null;
                }
                PermissionGroupInfo generatePermissionGroupInfo = PackageInfoUtils.generatePermissionGroupInfo(parsedPermissionGroup, i);
                if (!this.mPackageManagerInt.filterAppAccess(callingUid, UserHandle.getUserId(callingUid), generatePermissionGroupInfo.packageName, false)) {
                    return generatePermissionGroupInfo;
                }
                EventLog.writeEvent(1397638484, "186113473", Integer.valueOf(callingUid), str);
                return null;
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final PermissionInfo getPermissionInfo(int i, String str, String str2) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str2);
        int appId = UserHandle.getAppId(callingUid);
        int i2 = 10000;
        if (appId != 0 && appId != 1000 && appId != 2000 && androidPackage != null) {
            i2 = androidPackage.getTargetSdkVersion();
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    return null;
                }
                PermissionInfo generatePermissionInfo = permission.generatePermissionInfo(i, i2);
                if (!this.mPackageManagerInt.filterAppAccess(callingUid, UserHandle.getUserId(callingUid), generatePermissionInfo.packageName, false)) {
                    return generatePermissionInfo;
                }
                EventLog.writeEvent(1397638484, "183122164", Integer.valueOf(callingUid), str);
                return null;
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Permission getPermissionTEMP(String str) {
        Permission permission;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                permission = this.mRegistry.getPermission(str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return permission;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getRequestedRuntimePermissionsForMDM(String str) {
        List requestedRuntimePermissionsLocked;
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            return new ArrayList();
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                requestedRuntimePermissionsLocked = getRequestedRuntimePermissionsLocked(androidPackage, null);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return requestedRuntimePermissionsLocked;
    }

    public final List getRequestedRuntimePermissionsLocked(AndroidPackage androidPackage, List list) {
        if (list == null || list.isEmpty()) {
            list = List.copyOf(androidPackage.getRequestedPermissions());
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

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getSplitPermissions() {
        return PermissionManager.splitPermissionInfoListToParcelableList(SystemConfig.getInstance().mSplitPermissions);
    }

    public final UidPermissionState getUidStateLocked(int i, int i2) {
        UserPermissionState userPermissionState = (UserPermissionState) this.mState.mUserStates.get(i2);
        if (userPermissionState == null) {
            return null;
        }
        return userPermissionState.getUidState(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void grantRuntimePermission(String str, String str2, String str3, int i) {
        int callingUid = Binder.getCallingUid();
        grantRuntimePermissionInternal(str, str2, checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "default:0") == 0, callingUid, i, this.mDefaultPermissionCallback);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void grantRuntimePermissionInternal(java.lang.String r24, java.lang.String r25, boolean r26, int r27, int r28, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback r29) {
        /*
            Method dump skipped, instructions count: 775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.grantRuntimePermissionInternal(java.lang.String, java.lang.String, boolean, int, int, com.android.server.pm.permission.PermissionManagerServiceImpl$PermissionCallback):void");
    }

    public final boolean handleDenyLocked(final int i, final AndroidPackage androidPackage, UidPermissionState uidPermissionState, String str, Permission permission, boolean z) {
        final int appId = UserHandle.getAppId(androidPackage.getUid());
        if (androidPackage.getTargetSdkVersion() <= 22) {
            uidPermissionState.updatePermissionFlags(permission, 8, 8);
            final int appOp = getAppOp(androidPackage, str, permission);
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
                    int i2 = i;
                    int i3 = appId;
                    int i4 = appOp;
                    AndroidPackage androidPackage2 = androidPackage;
                    permissionManagerServiceImpl.getClass();
                    int uid = UserHandle.getUid(i2, i3);
                    AppOpsManager appOpsManager = (AppOpsManager) permissionManagerServiceImpl.mContext.getSystemService(AppOpsManager.class);
                    if (i4 == -1 || appOpsManager.checkOp(i4, uid, androidPackage2.getPackageName()) != 0) {
                        return;
                    }
                    appOpsManager.setUidMode(i4, uid, 1);
                    PermissionManagerServiceImpl.killUid(i3, i2, "Permission related app op changed");
                }
            });
        } else {
            if (uidPermissionState.hasPermissionState(str) && !uidPermissionState.isPermissionGranted(str)) {
                return true;
            }
            if (!uidPermissionState.removePermissionState(permission.mPermissionInfo.name)) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Failed to deny the permission ", str, " to package ");
                m.append(androidPackage.getPackageName());
                Log.i("PermissionManager", m.toString());
                return false;
            }
            if (z) {
                this.mHandler.post(new PermissionManagerServiceImpl$$ExternalSyntheticLambda6(appId, i, 0));
            }
        }
        return true;
    }

    public final boolean handleGrantLocked(final int i, AndroidPackage androidPackage, UidPermissionState uidPermissionState, String str, Permission permission) {
        final int appId = UserHandle.getAppId(androidPackage.getUid());
        if (androidPackage.getTargetSdkVersion() <= 22) {
            final int appOp = getAppOp(androidPackage, str, permission);
            uidPermissionState.updatePermissionFlags(permission, 8, 0);
            final String packageName = androidPackage.getPackageName();
            final boolean z = true;
            final boolean z2 = true;
            this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl.3
                @Override // java.lang.Runnable
                public final void run() {
                    int uid = UserHandle.getUid(i, appId);
                    AppOpsManager appOpsManager = (AppOpsManager) PermissionManagerServiceImpl.this.mContext.getSystemService(AppOpsManager.class);
                    int i2 = appOp;
                    boolean z3 = i2 != -1 && appOpsManager.checkOp(i2, uid, packageName) == 0;
                    if (z && z3) {
                        return;
                    }
                    appOpsManager.setUidMode(appOp, uid, 0);
                    if (z2) {
                        PermissionManagerServiceImpl.killUid(appId, i, "Permission related app op changed");
                    }
                }
            });
            return true;
        }
        if (uidPermissionState.hasPermissionState(str) && uidPermissionState.isPermissionGranted(str)) {
            return true;
        }
        boolean grantPermission = uidPermissionState.grantPermission(permission);
        final int appOp2 = getAppOp(androidPackage, str, permission);
        final String packageName2 = androidPackage.getPackageName();
        final boolean z3 = false;
        final boolean z4 = false;
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl.3
            @Override // java.lang.Runnable
            public final void run() {
                int uid = UserHandle.getUid(i, appId);
                AppOpsManager appOpsManager = (AppOpsManager) PermissionManagerServiceImpl.this.mContext.getSystemService(AppOpsManager.class);
                int i2 = appOp2;
                boolean z32 = i2 != -1 && appOpsManager.checkOp(i2, uid, packageName2) == 0;
                if (z3 && z32) {
                    return;
                }
                appOpsManager.setUidMode(appOp2, uid, 0);
                if (z4) {
                    PermissionManagerServiceImpl.killUid(appId, i, "Permission related app op changed");
                }
            }
        });
        if (grantPermission) {
            if (permission.mGids.length == 0) {
                return true;
            }
            this.mDefaultPermissionCallback.onGidsChanged(UserHandle.getAppId(androidPackage.getUid()), i);
            return true;
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Failed to grant the permission ", str, " to package ");
        m.append(androidPackage.getPackageName());
        Log.i("PermissionManager", m.toString());
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean isPermissionRevokedByPolicy(int i, String str, String str2, String str3) {
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "isPermissionRevokedByPolicy for user " + i);
        }
        if (checkPermission(str, str2, "default:0", i) == 0) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(callingUid, i, str, false)) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (getPermissionFlagsInternal(callingUid, i, str, str2) & 4) != 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPermissionSplitFromNonRuntime(int i, String str) {
        boolean z = true;
        ArrayList arrayList = SystemConfig.getInstance().mSplitPermissions;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo = (PermissionManager.SplitPermissionInfo) arrayList.get(i2);
            if (i < splitPermissionInfo.getTargetSdk() && splitPermissionInfo.getNewPermissions().contains(str)) {
                PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        Permission permission = this.mRegistry.getPermission(splitPermissionInfo.getSplitPermission());
                        if (permission == null || permission.isRuntime()) {
                            z = false;
                        }
                    } catch (Throwable th) {
                        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
                return z;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean isPermissionsReviewRequired(int i, String str) {
        boolean z = true;
        Objects.requireNonNull(str, "packageName");
        AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null || androidPackage.getTargetSdkVersion() >= 23) {
            return false;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i);
                if (uidStateLocked == null) {
                    Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                    return false;
                }
                ArrayMap arrayMap = uidStateLocked.mPermissions;
                if (arrayMap != null) {
                    int size = arrayMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if ((((PermissionState) uidStateLocked.mPermissions.valueAt(i2)).getFlags() & 64) != 0) {
                            break;
                        }
                    }
                }
                z = false;
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                return z;
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onPackageAdded(PackageState packageState, boolean z, final AndroidPackage androidPackage) {
        PermissionInfo generatePermissionInfo;
        Permission permission;
        PackageStateInternal packageStateInternal;
        final AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        Objects.requireNonNull(androidPackage2);
        if (!androidPackage2.getAdoptPermissions().isEmpty()) {
            for (int size = androidPackage2.getAdoptPermissions().size() - 1; size >= 0; size--) {
                String str = (String) androidPackage2.getAdoptPermissions().get(size);
                PackageStateInternal packageStateInternal2 = this.mPackageManagerInt.getPackageStateInternal(str);
                if (packageStateInternal2 != null) {
                    PackageSetting packageSetting = (PackageSetting) packageStateInternal2;
                    if (!packageSetting.isSystem()) {
                        Slog.w("PermissionManager", "Unable to update from " + packageSetting.mName + " to " + androidPackage2.getPackageName() + ": old package not in system partition");
                    } else if (this.mPackageManagerInt.getPackage(packageSetting.mName) != null) {
                        Slog.w("PermissionManager", "Unable to update from " + packageSetting.mName + " to " + androidPackage2.getPackageName() + ": old package still exists");
                    } else {
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Adopting permissions from ", str, " to ");
                        m.append(androidPackage2.getPackageName());
                        Slog.i("PermissionManager", m.toString());
                        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock) {
                            try {
                                this.mRegistry.transferPermissions(str, androidPackage2.getPackageName());
                            } finally {
                                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                            }
                        }
                    }
                }
            }
        }
        ArrayList arrayList = null;
        if (z) {
            Slog.w("PermissionManager", "Permission groups from package " + androidPackage2.getPackageName() + " ignored: instant apps cannot define new permission groups.");
        } else {
            PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock2) {
                try {
                    int size2 = ArrayUtils.size(androidPackage2.getPermissionGroups());
                    for (int i = 0; i < size2; i++) {
                        ParsedPermissionGroup parsedPermissionGroup = (ParsedPermissionGroup) androidPackage2.getPermissionGroups().get(i);
                        ParsedPermissionGroup parsedPermissionGroup2 = (ParsedPermissionGroup) this.mRegistry.mPermissionGroups.get(parsedPermissionGroup.getName());
                        boolean equals = parsedPermissionGroup.getPackageName().equals(parsedPermissionGroup2 == null ? null : parsedPermissionGroup2.getPackageName());
                        if (parsedPermissionGroup2 != null && !equals) {
                            Slog.w("PermissionManager", "Permission group " + parsedPermissionGroup.getName() + " from package " + parsedPermissionGroup.getPackageName() + " ignored: original from " + parsedPermissionGroup2.getPackageName());
                        }
                        this.mRegistry.mPermissionGroups.put(parsedPermissionGroup.getName(), parsedPermissionGroup);
                    }
                } catch (Throwable th) {
                    boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
        }
        if (z) {
            Slog.w("PermissionManager", "Permissions from package " + androidPackage2.getPackageName() + " ignored: instant apps cannot define new permissions.");
        } else {
            int size3 = ArrayUtils.size(androidPackage2.getPermissions());
            arrayList = new ArrayList();
            for (int i2 = 0; i2 < size3; i2++) {
                ParsedPermission parsedPermission = (ParsedPermission) androidPackage2.getPermissions().get(i2);
                PackageManagerTracedLock packageManagerTracedLock3 = this.mLock;
                boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock3) {
                    try {
                        if (androidPackage2.getTargetSdkVersion() > 22) {
                            ComponentMutateUtils.setParsedPermissionGroup(parsedPermission, (ParsedPermissionGroup) this.mRegistry.mPermissionGroups.get(parsedPermission.getGroup()));
                        }
                        generatePermissionInfo = PackageInfoUtils.generatePermissionInfo(parsedPermission, 128L);
                        permission = parsedPermission.isTree() ? (Permission) this.mRegistry.mPermissionTrees.get(parsedPermission.getName()) : this.mRegistry.getPermission(parsedPermission.getName());
                    } catch (Throwable th2) {
                        boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th2;
                    }
                }
                boolean isSystem = (permission == null || Objects.equals(permission.mPermissionInfo.packageName, generatePermissionInfo.packageName) || !permission.mReconciled || (packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(permission.mPermissionInfo.packageName)) == null) ? false : ((PackageSetting) packageStateInternal).isSystem();
                synchronized (this.mLock) {
                    try {
                        Permission createOrUpdate = Permission.createOrUpdate(permission, generatePermissionInfo, packageState, this.mRegistry.mPermissionTrees.values(), isSystem);
                        if (parsedPermission.isTree()) {
                            this.mRegistry.mPermissionTrees.put(createOrUpdate.mPermissionInfo.name, createOrUpdate);
                        } else {
                            this.mRegistry.mPermissions.put(createOrUpdate.mPermissionInfo.name, createOrUpdate);
                        }
                        if (createOrUpdate.mDefinitionChanged) {
                            arrayList.add(parsedPermission.getName());
                            createOrUpdate.mDefinitionChanged = false;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                }
            }
        }
        final ArrayList arrayList2 = arrayList;
        final boolean z9 = androidPackage != null;
        final boolean z10 = !CollectionUtils.isEmpty(arrayList2);
        if (z9 || z10) {
            AsyncTask.execute(new Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    int i3;
                    PermissionInfo permissionInfo;
                    int i4;
                    int i5;
                    int i6;
                    int[] iArr;
                    boolean z11;
                    boolean z12;
                    int i7;
                    Permission permission2;
                    int i8;
                    int i9;
                    ArrayMap arrayMap;
                    boolean z13 = true;
                    final PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
                    boolean z14 = z9;
                    final AndroidPackage androidPackage3 = androidPackage2;
                    AndroidPackage androidPackage4 = androidPackage;
                    boolean z15 = z10;
                    List list = arrayList2;
                    permissionManagerServiceImpl.getClass();
                    if (z14) {
                        int size4 = ArrayUtils.size(androidPackage4.getPermissions());
                        ArrayMap arrayMap2 = new ArrayMap(size4);
                        for (int i10 = 0; i10 < size4; i10++) {
                            ParsedPermission parsedPermission2 = (ParsedPermission) androidPackage4.getPermissions().get(i10);
                            if (parsedPermission2.getParsedPermissionGroup() != null) {
                                arrayMap2.put(parsedPermission2.getName(), parsedPermission2.getParsedPermissionGroup().getName());
                            }
                        }
                        final int callingUid = Binder.getCallingUid();
                        int size5 = ArrayUtils.size(androidPackage3.getPermissions());
                        int i11 = 0;
                        while (i11 < size5) {
                            ParsedPermission parsedPermission3 = (ParsedPermission) androidPackage3.getPermissions().get(i11);
                            if ((ParsedPermissionUtils.getProtection(parsedPermission3) & 1) != 0) {
                                final String name = parsedPermission3.getName();
                                final String name2 = parsedPermission3.getParsedPermissionGroup() == null ? null : parsedPermission3.getParsedPermissionGroup().getName();
                                final String str2 = (String) arrayMap2.get(name);
                                if (name2 != null && !name2.equals(str2)) {
                                    final int[] userIds = permissionManagerServiceImpl.mUserManagerInt.getUserIds();
                                    i8 = i11;
                                    i9 = size5;
                                    arrayMap = arrayMap2;
                                    permissionManagerServiceImpl.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda20
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i12;
                                            int i13;
                                            PermissionManagerServiceImpl permissionManagerServiceImpl2;
                                            String str3;
                                            PermissionManagerServiceImpl permissionManagerServiceImpl3 = PermissionManagerServiceImpl.this;
                                            int[] iArr2 = userIds;
                                            String str4 = name;
                                            AndroidPackage androidPackage5 = androidPackage3;
                                            String str5 = str2;
                                            String str6 = name2;
                                            int i14 = callingUid;
                                            permissionManagerServiceImpl3.getClass();
                                            String packageName = ((AndroidPackage) obj).getPackageName();
                                            int length = iArr2.length;
                                            int i15 = 0;
                                            while (i15 < length) {
                                                int i16 = iArr2[i15];
                                                if (permissionManagerServiceImpl3.checkPermission(packageName, str4, "default:0", i16) == 0) {
                                                    EventLog.writeEvent(1397638484, "72710897", Integer.valueOf(androidPackage5.getUid()), BootReceiver$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("Revoking permission ", str4, " from package ", packageName, " as the group changed from "), str5, " to ", str6));
                                                    try {
                                                        PermissionManagerServiceImpl.AnonymousClass1 anonymousClass1 = permissionManagerServiceImpl3.mDefaultPermissionCallback;
                                                        PermissionManagerServiceImpl permissionManagerServiceImpl4 = permissionManagerServiceImpl3;
                                                        i12 = i15;
                                                        i13 = length;
                                                        permissionManagerServiceImpl2 = permissionManagerServiceImpl3;
                                                        str3 = packageName;
                                                        try {
                                                            permissionManagerServiceImpl4.revokeRuntimePermissionInternal(packageName, str4, false, false, i14, i16, null, anonymousClass1);
                                                        } catch (IllegalArgumentException e) {
                                                            e = e;
                                                            Slog.e("PermissionManager", BootReceiver$$ExternalSyntheticOutline0.m("Could not revoke ", str4, " from ", str3), e);
                                                            i15 = i12 + 1;
                                                            packageName = str3;
                                                            length = i13;
                                                            permissionManagerServiceImpl3 = permissionManagerServiceImpl2;
                                                        }
                                                    } catch (IllegalArgumentException e2) {
                                                        e = e2;
                                                        i12 = i15;
                                                        i13 = length;
                                                        permissionManagerServiceImpl2 = permissionManagerServiceImpl3;
                                                        str3 = packageName;
                                                    }
                                                } else {
                                                    i12 = i15;
                                                    i13 = length;
                                                    permissionManagerServiceImpl2 = permissionManagerServiceImpl3;
                                                    str3 = packageName;
                                                }
                                                i15 = i12 + 1;
                                                packageName = str3;
                                                length = i13;
                                                permissionManagerServiceImpl3 = permissionManagerServiceImpl2;
                                            }
                                        }
                                    });
                                    i11 = i8 + 1;
                                    arrayMap2 = arrayMap;
                                    size5 = i9;
                                }
                            }
                            i8 = i11;
                            i9 = size5;
                            arrayMap = arrayMap2;
                            i11 = i8 + 1;
                            arrayMap2 = arrayMap;
                            size5 = i9;
                        }
                        boolean z16 = androidPackage4.getTargetSdkVersion() >= 29 && androidPackage3.getTargetSdkVersion() < 29;
                        boolean z17 = (androidPackage4.getTargetSdkVersion() >= 29 || androidPackage3.getTargetSdkVersion() < 29) && !androidPackage4.isRequestLegacyExternalStorage() && androidPackage3.isRequestLegacyExternalStorage();
                        if (z17 || z16) {
                            int callingUid2 = Binder.getCallingUid();
                            int[] allUserIds$1 = PermissionManagerServiceImpl.getAllUserIds$1();
                            int length = allUserIds$1.length;
                            int i12 = 0;
                            while (i12 < length) {
                                int i13 = allUserIds$1[i12];
                                Iterator it = androidPackage3.getRequestedPermissions().iterator();
                                while (it.hasNext()) {
                                    PermissionInfo permissionInfo2 = permissionManagerServiceImpl.getPermissionInfo(0, (String) it.next(), androidPackage3.getPackageName());
                                    if (permissionInfo2 != null) {
                                        if (!((ArrayList) PermissionManagerServiceImpl.STORAGE_PERMISSIONS).contains(permissionInfo2.name)) {
                                            if (!((ArraySet) PermissionManagerServiceImpl.READ_MEDIA_AURAL_PERMISSIONS).contains(permissionInfo2.name)) {
                                                if (((ArraySet) PermissionManagerServiceImpl.READ_MEDIA_VISUAL_PERMISSIONS).contains(permissionInfo2.name)) {
                                                }
                                            }
                                        }
                                        if ((permissionManagerServiceImpl.getPermissionFlags(androidPackage3.getPackageName(), permissionInfo2.name, "default:0", i13) & 20) == 0) {
                                            EventLog.writeEvent(1397638484, "171430330", Integer.valueOf(androidPackage3.getUid()), "Revoking permission " + permissionInfo2.name + " from package " + androidPackage3.getPackageName() + " as either the sdk downgraded " + z16 + " or newly requested legacy full storage " + z17);
                                            try {
                                                permissionInfo = permissionInfo2;
                                                i4 = i13;
                                                i5 = i12;
                                                i6 = length;
                                                iArr = allUserIds$1;
                                                z11 = z17;
                                                z12 = z16;
                                                try {
                                                    permissionManagerServiceImpl.revokeRuntimePermissionInternal(androidPackage3.getPackageName(), permissionInfo2.name, false, false, callingUid2, i4, null, permissionManagerServiceImpl.mDefaultPermissionCallback);
                                                    i7 = i4;
                                                } catch (IllegalStateException | SecurityException e) {
                                                    e = e;
                                                    StringBuilder sb = new StringBuilder("unable to revoke ");
                                                    sb.append(permissionInfo.name);
                                                    sb.append(" for ");
                                                    sb.append(androidPackage3.getPackageName());
                                                    sb.append(" user ");
                                                    i7 = i4;
                                                    sb.append(i7);
                                                    Log.e("PermissionManager", sb.toString(), e);
                                                    i13 = i7;
                                                    i12 = i5;
                                                    z16 = z12;
                                                    length = i6;
                                                    allUserIds$1 = iArr;
                                                    z17 = z11;
                                                    z13 = true;
                                                }
                                            } catch (IllegalStateException | SecurityException e2) {
                                                e = e2;
                                                permissionInfo = permissionInfo2;
                                                i4 = i13;
                                                i5 = i12;
                                                i6 = length;
                                                iArr = allUserIds$1;
                                                z11 = z17;
                                                z12 = z16;
                                            }
                                            i13 = i7;
                                            i12 = i5;
                                            z16 = z12;
                                            length = i6;
                                            allUserIds$1 = iArr;
                                            z17 = z11;
                                            z13 = true;
                                        }
                                    }
                                }
                                i12++;
                            }
                        }
                        i3 = 0;
                        if (androidPackage4.getTargetSdkVersion() < 23 && androidPackage3.getTargetSdkVersion() >= 23 && androidPackage3.getRequestedPermissions().contains("android.permission.SYSTEM_ALERT_WINDOW")) {
                            PackageManagerTracedLock packageManagerTracedLock4 = permissionManagerServiceImpl.mLock;
                            boolean z18 = PackageManagerService.DEBUG_COMPRESSION;
                            synchronized (packageManagerTracedLock4) {
                                try {
                                    permission2 = permissionManagerServiceImpl.mRegistry.getPermission("android.permission.SYSTEM_ALERT_WINDOW");
                                } finally {
                                    boolean z19 = PackageManagerService.DEBUG_COMPRESSION;
                                }
                            }
                            if (!permissionManagerServiceImpl.shouldGrantPermissionByProtectionFlags(androidPackage3, permissionManagerServiceImpl.mPackageManagerInt.getPackageStateInternal(androidPackage3.getPackageName()), permission2, new ArraySet()) && !permissionManagerServiceImpl.shouldGrantPermissionBySignature(androidPackage3, permission2)) {
                                for (int i14 : PermissionManagerServiceImpl.getAllUserIds$1()) {
                                    try {
                                        permissionManagerServiceImpl.revokePermissionFromPackageForUser(androidPackage3.getPackageName(), "android.permission.SYSTEM_ALERT_WINDOW", false, i14, permissionManagerServiceImpl.mDefaultPermissionCallback);
                                    } catch (IllegalStateException | SecurityException e3) {
                                        Log.e("PermissionManager", "unable to revoke SYSTEM_ALERT_WINDOW for " + androidPackage3.getPackageName() + " user " + i14, e3);
                                    }
                                }
                            }
                        }
                    } else {
                        i3 = 0;
                    }
                    if (z15) {
                        final int[] userIds2 = permissionManagerServiceImpl.mUserManagerInt.getUserIds();
                        int size6 = list.size();
                        final int callingUid3 = Binder.getCallingUid();
                        for (int i15 = i3; i15 < size6; i15++) {
                            final String str3 = (String) list.get(i15);
                            PackageManagerTracedLock packageManagerTracedLock5 = permissionManagerServiceImpl.mLock;
                            boolean z20 = PackageManagerService.DEBUG_COMPRESSION;
                            synchronized (packageManagerTracedLock5) {
                                try {
                                    Permission permission3 = permissionManagerServiceImpl.mRegistry.getPermission(str3);
                                    if (permission3 != null && (permission3.isInternal() || permission3.isRuntime())) {
                                        final boolean isInternal = permission3.isInternal();
                                        permissionManagerServiceImpl.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda19
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i16;
                                                int i17;
                                                PermissionManagerServiceImpl permissionManagerServiceImpl2;
                                                int i18;
                                                int i19;
                                                String str4;
                                                PermissionManagerServiceImpl permissionManagerServiceImpl3 = PermissionManagerServiceImpl.this;
                                                int[] iArr2 = userIds2;
                                                String str5 = str3;
                                                boolean z21 = isInternal;
                                                int i20 = callingUid3;
                                                AndroidPackage androidPackage5 = (AndroidPackage) obj;
                                                permissionManagerServiceImpl3.getClass();
                                                String packageName = androidPackage5.getPackageName();
                                                int uid = androidPackage5.getUid();
                                                if (uid < 10000) {
                                                    return;
                                                }
                                                int length2 = iArr2.length;
                                                int i21 = 0;
                                                while (i21 < length2) {
                                                    int i22 = iArr2[i21];
                                                    int checkPermission = permissionManagerServiceImpl3.checkPermission(packageName, str5, "default:0", i22);
                                                    int permissionFlags = permissionManagerServiceImpl3.getPermissionFlags(packageName, str5, "default:0", i22);
                                                    if (checkPermission == 0 && (permissionFlags & 32820) == 0) {
                                                        int uid2 = UserHandle.getUid(i22, uid);
                                                        if (z21) {
                                                            i18 = i21;
                                                            EventLog.writeEvent(1397638484, "195338390", Integer.valueOf(uid2), XmlUtils$$ExternalSyntheticOutline0.m("Revoking permission ", str5, " from package ", packageName, " due to definition change"));
                                                            i19 = length2;
                                                        } else {
                                                            i18 = i21;
                                                            i19 = length2;
                                                            EventLog.writeEvent(1397638484, "154505240", Integer.valueOf(uid2), XmlUtils$$ExternalSyntheticOutline0.m("Revoking permission ", str5, " from package ", packageName, " due to definition change"));
                                                            EventLog.writeEvent(1397638484, "168319670", Integer.valueOf(uid2), XmlUtils$$ExternalSyntheticOutline0.m("Revoking permission ", str5, " from package ", packageName, " due to definition change"));
                                                        }
                                                        Slog.e("PermissionManager", XmlUtils$$ExternalSyntheticOutline0.m("Revoking permission ", str5, " from package ", packageName, " due to definition change"));
                                                        try {
                                                            PermissionManagerServiceImpl.AnonymousClass1 anonymousClass1 = permissionManagerServiceImpl3.mDefaultPermissionCallback;
                                                            PermissionManagerServiceImpl permissionManagerServiceImpl4 = permissionManagerServiceImpl3;
                                                            i16 = i18;
                                                            i17 = i19;
                                                            permissionManagerServiceImpl2 = permissionManagerServiceImpl3;
                                                            str4 = "PermissionManager";
                                                            try {
                                                                permissionManagerServiceImpl4.revokeRuntimePermissionInternal(packageName, str5, false, false, i20, i22, null, anonymousClass1);
                                                            } catch (Exception e4) {
                                                                e = e4;
                                                                Slog.e(str4, BootReceiver$$ExternalSyntheticOutline0.m("Could not revoke ", str5, " from ", packageName), e);
                                                                i21 = i16 + 1;
                                                                length2 = i17;
                                                                permissionManagerServiceImpl3 = permissionManagerServiceImpl2;
                                                            }
                                                        } catch (Exception e5) {
                                                            e = e5;
                                                            i16 = i18;
                                                            i17 = i19;
                                                            permissionManagerServiceImpl2 = permissionManagerServiceImpl3;
                                                            str4 = "PermissionManager";
                                                        }
                                                    } else {
                                                        i16 = i21;
                                                        i17 = length2;
                                                        permissionManagerServiceImpl2 = permissionManagerServiceImpl3;
                                                    }
                                                    i21 = i16 + 1;
                                                    length2 = i17;
                                                    permissionManagerServiceImpl3 = permissionManagerServiceImpl2;
                                                }
                                            }
                                        });
                                    }
                                } catch (Throwable th4) {
                                    throw th4;
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPackageInstalled(com.android.server.pm.pkg.AndroidPackage r25, com.android.server.pm.permission.PermissionManagerServiceInternal$PackageInstalledParams r26, int r27) {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.onPackageInstalled(com.android.server.pm.pkg.AndroidPackage, com.android.server.pm.permission.PermissionManagerServiceInternal$PackageInstalledParams, int):void");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onPackageRemoved(AndroidPackage androidPackage) {
        PermissionInfo permissionInfo;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                int size = ArrayUtils.size(androidPackage.getPermissions());
                for (int i = 0; i < size; i++) {
                    ParsedPermission parsedPermission = (ParsedPermission) androidPackage.getPermissions().get(i);
                    Permission permission = this.mRegistry.getPermission(parsedPermission.getName());
                    if (permission == null) {
                        permission = (Permission) this.mRegistry.mPermissionTrees.get(parsedPermission.getName());
                    }
                    if (permission != null && (permissionInfo = permission.mPermissionInfo) != null && Objects.equals(permissionInfo.packageName, parsedPermission.getPackageName()) && Objects.equals(permission.mPermissionInfo.name, parsedPermission.getName())) {
                        PermissionInfo permissionInfo2 = new PermissionInfo();
                        PermissionInfo permissionInfo3 = permission.mPermissionInfo;
                        permissionInfo2.name = permissionInfo3.name;
                        permissionInfo2.packageName = permissionInfo3.packageName;
                        permissionInfo2.protectionLevel = permissionInfo3.protectionLevel;
                        permission.mPermissionInfo = permissionInfo2;
                        permission.mReconciled = false;
                    }
                    if (ParsedPermissionUtils.isAppOp(parsedPermission)) {
                        PermissionRegistry permissionRegistry = this.mRegistry;
                        String name = parsedPermission.getName();
                        String packageName = androidPackage.getPackageName();
                        ArraySet arraySet = (ArraySet) permissionRegistry.mAppOpPermissionPackages.get(name);
                        if (arraySet != null && arraySet.remove(packageName) && arraySet.isEmpty()) {
                            permissionRegistry.mAppOpPermissionPackages.remove(name);
                        }
                    }
                }
                for (String str : androidPackage.getRequestedPermissions()) {
                    Permission permission2 = this.mRegistry.getPermission(str);
                    if (permission2 != null && (permission2.mPermissionInfo.protectionLevel & 64) != 0) {
                        PermissionRegistry permissionRegistry2 = this.mRegistry;
                        String packageName2 = androidPackage.getPackageName();
                        ArraySet arraySet2 = (ArraySet) permissionRegistry2.mAppOpPermissionPackages.get(str);
                        if (arraySet2 != null && arraySet2.remove(packageName2) && arraySet2.isEmpty()) {
                            permissionRegistry2.mAppOpPermissionPackages.remove(str);
                        }
                    }
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x017d, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0115, code lost:
    
        if (r5 == 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0121, code lost:
    
        if (r4.pkg.getRequestedPermissions().contains(r15) == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0124, code lost:
    
        r11 = r18.mLock;
        r12 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0128, code lost:
    
        monitor-enter(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0129, code lost:
    
        r12 = getUidStateLocked(r20, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x012d, code lost:
    
        if (r12 != null) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x012f, code lost:
    
        r15 = new java.lang.StringBuilder();
        r16 = r1;
        r15.append("Missing permissions state for ");
        r15.append(r22.getPackageName());
        r15.append(" and user ");
        r15.append(r3);
        android.util.Slog.e("PermissionManager", r15.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0153, code lost:
    
        monitor-exit(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0157, code lost:
    
        r16 = r1;
        r1 = r18.mRegistry.getPermission(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x015f, code lost:
    
        if (r1 != null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0161, code lost:
    
        monitor-exit(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0170, code lost:
    
        if (r12.removePermissionState(r1.mPermissionInfo.name) == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0175, code lost:
    
        if (r1.mGids.length == 0) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0177, code lost:
    
        r14 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0178, code lost:
    
        monitor-exit(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0155, code lost:
    
        r0 = move-exception;
     */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPackageUninstalled(java.lang.String r19, final int r20, com.android.server.pm.pkg.PackageState r21, com.android.server.pm.pkg.AndroidPackage r22, java.util.List r23, int r24) {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.onPackageUninstalled(java.lang.String, int, com.android.server.pm.pkg.PackageState, com.android.server.pm.pkg.AndroidPackage, java.util.List, int):void");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onStorageVolumeMounted(String str, boolean z) {
        updateAllPermissions(str, z);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onSystemReady() {
        updateAllPermissions(StorageManager.UUID_PRIVATE_INTERNAL, false);
        PermissionPolicyService.Internal internal = (PermissionPolicyService.Internal) LocalServices.getService(PermissionPolicyService.Internal.class);
        PermissionManagerServiceImpl$$ExternalSyntheticLambda15 permissionManagerServiceImpl$$ExternalSyntheticLambda15 = new PermissionManagerServiceImpl$$ExternalSyntheticLambda15(this);
        synchronized (PermissionPolicyService.this.mLock) {
            PermissionPolicyService.this.mOnInitializedCallback = permissionManagerServiceImpl$$ExternalSyntheticLambda15;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mSystemReady = true;
                if (this.mPrivappPermissionsViolations != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("We detected priv-permissions violations from below apps:");
                    Iterator it = this.mPrivappPermissionsViolations.iterator();
                    int i = 1;
                    while (it.hasNext()) {
                        sb.append("\n" + i + ") " + ((String) it.next()));
                        i++;
                    }
                    sb.append("\nPrivileged apps MUST put their 'signature|privileged' permissions into the allowlist.\n");
                    sb.append("Please ASSIGN this issue to above apps to apply.\n");
                    Slog.d("PermissionManager", sb.toString());
                    PmLog.logDebugInfo(sb.toString());
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        this.mPermissionControllerManager = new PermissionControllerManager(this.mContext, PermissionThread.getHandler());
        this.mPermissionPolicyInternal = (PermissionPolicyService.Internal) LocalServices.getService(PermissionPolicyService.Internal.class);
        PmHook.mSystemReady = true;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onUserCreated(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        updateAllPermissions(StorageManager.UUID_PRIVATE_INTERNAL, true);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onUserRemoved(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mState.mUserStates.delete(i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List queryPermissionsByGroup(int i, String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(10);
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                ParsedPermissionGroup parsedPermissionGroup = (ParsedPermissionGroup) this.mRegistry.mPermissionGroups.get(str);
                if (str != null && parsedPermissionGroup == null) {
                    return null;
                }
                for (Permission permission : this.mRegistry.mPermissions.values()) {
                    if (Objects.equals(permission.mPermissionInfo.group, str)) {
                        arrayList.add(permission.generatePermissionInfo(i, 10000));
                    }
                }
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                int userId = UserHandle.getUserId(callingUid);
                if (parsedPermissionGroup != null && this.mPackageManagerInt.filterAppAccess(callingUid, userId, parsedPermissionGroup.getPackageName(), false)) {
                    return null;
                }
                arrayList.removeIf(new PermissionManagerServiceImpl$$ExternalSyntheticLambda1(this, callingUid, userId, 0));
                return arrayList;
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void readLegacyPermissionStateTEMP() {
        this.mPackageManagerInt.forEachPackageState(new PermissionManagerServiceImpl$$ExternalSyntheticLambda10(this, getAllUserIds$1(), 0));
    }

    public final void readLegacyPermissionStatesLocked(UidPermissionState uidPermissionState, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            LegacyPermissionState.PermissionState permissionState = (LegacyPermissionState.PermissionState) it.next();
            String str = permissionState.mName;
            Permission permission = this.mRegistry.getPermission(str);
            if (permission == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Unknown permission: ", str, "PermissionManager");
            } else {
                boolean z = permissionState.mGranted;
                int i = permissionState.mFlags;
                uidPermissionState.getClass();
                String str2 = permission.mPermissionInfo.name;
                ArrayMap arrayMap = uidPermissionState.mPermissions;
                if (arrayMap == null) {
                    uidPermissionState.mPermissions = new ArrayMap();
                } else {
                    arrayMap.remove(str2);
                }
                PermissionState permissionState2 = new PermissionState(permission);
                if (z) {
                    permissionState2.grant();
                }
                permissionState2.updateFlags(i, i);
                uidPermissionState.mPermissions.put(str2, permissionState2);
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
        int i = 0;
        while (i < 2) {
            List permissions = i == 0 ? legacyPermissionSettings.getPermissions() : legacyPermissionSettings.getPermissionTrees();
            PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    int size = permissions.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        LegacyPermission legacyPermission = (LegacyPermission) permissions.get(i2);
                        Permission permission = new Permission(legacyPermission.mPermissionInfo, legacyPermission.mType);
                        if (i == 0) {
                            Permission permission2 = this.mRegistry.getPermission(permission.mPermissionInfo.name);
                            if (permission2 != null && permission2.mType == 1) {
                                int[] iArr = permission2.mGids;
                                boolean z2 = permission2.mGidsPerUser;
                                permission.mGids = iArr;
                                permission.mGidsPerUser = z2;
                            }
                            this.mRegistry.mPermissions.put(permission.mPermissionInfo.name, permission);
                        } else {
                            this.mRegistry.mPermissionTrees.put(permission.mPermissionInfo.name, permission);
                        }
                    }
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            i++;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) {
        List allowlistedRestrictedPermissions;
        Objects.requireNonNull(str2);
        if (checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(str2) && (allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(str, i, i2)) != null && allowlistedRestrictedPermissions.remove(str2)) {
            return setAllowlistedRestrictedPermissions(i, str, i2, allowlistedRestrictedPermissions);
        }
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        if (this.mPackageManagerInt.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            throw new SecurityException("Instant applications don't have access to this method");
        }
        this.mOnPermissionChangeListeners.mPermissionListeners.unregister(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void removePackageGrantedPermissionsForMDM(String str) {
        this.mPackageGrantedPermissions.remove(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void removePermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            throw new SecurityException("Instant applications don't have access to this method");
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mRegistry.enforcePermissionTree(callingUid, str);
                Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    return;
                }
                if (permission.mType != 2) {
                    Slog.wtf("PermissionManager", "Not allowed to modify non-dynamic permission ".concat(str));
                } else {
                    this.mRegistry.mPermissions.remove(str);
                    this.mPackageManagerInt.writeSettings(false);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void resetRuntimePermissions(AndroidPackage androidPackage, int i) {
        Objects.requireNonNull(androidPackage, "pkg");
        Preconditions.checkArgumentNonNegative(i, "userId");
        resetRuntimePermissionsInternal(androidPackage, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void resetRuntimePermissionsForUser(int i) {
        Preconditions.checkArgumentNonNegative(i, "userId");
        resetRuntimePermissionsInternal(null, i);
    }

    public final void resetRuntimePermissionsInternal(AndroidPackage androidPackage, final int i) {
        boolean[] zArr = new boolean[1];
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        ArraySet arraySet3 = new ArraySet();
        final ?? r11 = new PermissionCallback(arraySet, arraySet2, arraySet3, zArr) { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl.2
            public final /* synthetic */ ArraySet val$asyncUpdatedUsers;
            public final /* synthetic */ ArraySet val$revokedPermissions;
            public final /* synthetic */ ArraySet val$syncUpdatedUsers;

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public final void onGidsChanged(int i2, int i3) {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onGidsChanged(i2, i3);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public final void onInstallPermissionGranted() {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionGranted();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public final void onInstallPermissionUpdated() {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionUpdated();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public final void onPermissionGranted(int i2, int i3) {
                PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onPermissionGranted(i2, i3);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public final void onPermissionRevoked(String str, int i2, boolean z, int i3, String str2) {
                this.val$revokedPermissions.add(Long.valueOf(IntPair.of(i2, i3)));
                this.val$syncUpdatedUsers.add(Integer.valueOf(i3));
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public final void onPermissionUpdated(int[] iArr, boolean z, int i2) {
                PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i2);
                for (int i3 : iArr) {
                    if (z) {
                        this.val$syncUpdatedUsers.add(Integer.valueOf(i3));
                        this.val$asyncUpdatedUsers.remove(Integer.valueOf(i3));
                    } else if (this.val$syncUpdatedUsers.indexOf(Integer.valueOf(i3)) == -1) {
                        this.val$asyncUpdatedUsers.add(Integer.valueOf(i3));
                    }
                }
            }
        };
        if (androidPackage != null) {
            resetRuntimePermissionsInternal(androidPackage, i, r11);
        } else {
            this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PermissionManagerServiceImpl.this.resetRuntimePermissionsInternal((AndroidPackage) obj, i, (PermissionManagerServiceImpl.AnonymousClass2) r11);
                }
            });
        }
        if (zArr[0]) {
            PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(false);
        }
        if (!arraySet.isEmpty()) {
            int size = arraySet.size();
            for (int i2 = 0; i2 < size; i2++) {
                int first = IntPair.first(((Long) arraySet.valueAt(i2)).longValue());
                int second = IntPair.second(((Long) arraySet.valueAt(i2)).longValue());
                this.mOnPermissionChangeListeners.onPermissionsChanged(first);
                this.mHandler.post(new PermissionManagerServiceImpl$$ExternalSyntheticLambda6(first, second, 1));
            }
        }
        this.mPackageManagerInt.writePermissionSettings(ArrayUtils.convertToIntArray(arraySet2), false);
        this.mPackageManagerInt.writePermissionSettings(ArrayUtils.convertToIntArray(arraySet3), true);
    }

    public final void resetRuntimePermissionsInternal(AndroidPackage androidPackage, int i, AnonymousClass2 anonymousClass2) {
        int i2 = i;
        String packageName = androidPackage.getPackageName();
        for (String str : androidPackage.getRequestedPermissions()) {
            if (!this.mIsLeanback || !((ArrayList) NOTIFICATION_PERMISSIONS).contains(str)) {
                PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                boolean z = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock) {
                    try {
                        Permission permission = this.mRegistry.getPermission(str);
                        if (permission != null) {
                            if ((permission.mPermissionInfo.flags & 2) == 0) {
                                boolean isRuntime = permission.isRuntime();
                                String[] sharedUserPackagesForPackage = this.mPackageManagerInt.getSharedUserPackagesForPackage(i2, androidPackage.getPackageName());
                                int i3 = 0;
                                if (sharedUserPackagesForPackage.length > 0) {
                                    for (String str2 : sharedUserPackagesForPackage) {
                                        AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(str2);
                                        int i4 = (androidPackage2 == null || androidPackage2.getPackageName().equals(packageName) || !androidPackage2.getRequestedPermissions().contains(str)) ? i4 + 1 : 0;
                                    }
                                }
                                int permissionFlagsInternal = getPermissionFlagsInternal(1000, i2, packageName, str);
                                int uidTargetSdkVersion = this.mPackageManagerInt.getUidTargetSdkVersion(this.mPackageManagerInt.getPackageUid(packageName, 0L, i2));
                                if (uidTargetSdkVersion < 23 && isRuntime) {
                                    i3 = 72;
                                }
                                int i5 = i3;
                                updatePermissionFlagsInternal(packageName, str, 589899, i5, 1000, i, false, anonymousClass2);
                                if (isRuntime && (permissionFlagsInternal & 20) == 0) {
                                    if ((permissionFlagsInternal & 32) != 0 || (32768 & permissionFlagsInternal) != 0) {
                                        grantRuntimePermissionInternal(packageName, str, false, 1000, i, anonymousClass2);
                                    } else if ((i5 & 64) == 0 && !isPermissionSplitFromNonRuntime(uidTargetSdkVersion, str)) {
                                        revokeRuntimePermissionInternal(packageName, str, false, false, 1000, i, null, anonymousClass2);
                                    }
                                }
                                i2 = i;
                            }
                        }
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                }
            }
        }
    }

    public final void restoreDelayedRuntimePermissions(String str, final int i) {
        Objects.requireNonNull(str, "packageName");
        Preconditions.checkArgumentNonNegative(i, "userId");
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                if (this.mHasNoDelayedPermBackup.get(i, false)) {
                    return;
                }
                this.mPermissionControllerManager.applyStagedRuntimePermissionBackup(str, UserHandle.of(i), PermissionThread.getExecutor(), new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
                        int i2 = i;
                        permissionManagerServiceImpl.getClass();
                        if (((Boolean) obj).booleanValue()) {
                            return;
                        }
                        PackageManagerTracedLock packageManagerTracedLock2 = permissionManagerServiceImpl.mLock;
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock2) {
                            try {
                                permissionManagerServiceImpl.mHasNoDelayedPermBackup.put(i2, true);
                            } catch (Throwable th) {
                                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                                throw th;
                            }
                        }
                    }
                });
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:174:0x0359, code lost:
    
        if (((r3 & 16) != 0) != false) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x0586, code lost:
    
        if (r9 == false) goto L313;
     */
    /* JADX WARN: Code restructure failed: missing block: B:390:0x077b, code lost:
    
        if (r6.grantPermission(r2) != false) goto L458;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0726, code lost:
    
        if (r2.isRole() == false) goto L436;
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x0763, code lost:
    
        if (com.android.internal.util.CollectionUtils.contains(r11, r12) == false) goto L450;
     */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x0775, code lost:
    
        if (r5.isPermissionGranted(r12) == false) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x076f, code lost:
    
        if (r2.isRole() == false) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0107, code lost:
    
        if (r2 != null) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restorePermissionState(com.android.server.pm.pkg.AndroidPackage r44, boolean r45, java.lang.String r46, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback r47, int r48) {
        /*
            Method dump skipped, instructions count: 2211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.restorePermissionState(com.android.server.pm.pkg.AndroidPackage, boolean, java.lang.String, com.android.server.pm.permission.PermissionManagerServiceImpl$PermissionCallback, int):void");
    }

    public final void restoreRuntimePermissions(byte[] bArr, int i) {
        Objects.requireNonNull(bArr, "backup");
        Preconditions.checkArgumentNonNegative(i, "userId");
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mHasNoDelayedPermBackup.delete(i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        this.mPermissionControllerManager.stageAndApplyRuntimePermissionsBackup(bArr, UserHandle.of(i));
    }

    public final void revokePermissionFromPackageForUser(String str, String str2, boolean z, int i, PermissionCallback permissionCallback) {
        ApplicationInfo applicationInfo = this.mPackageManagerInt.getApplicationInfo(1000, 0, 0L, str);
        if ((applicationInfo == null || applicationInfo.targetSdkVersion >= 23) && checkPermission(str, str2, "default:0", i) == 0) {
            try {
                revokeRuntimePermissionInternal(str, str2, z, false, 1000, i, null, permissionCallback);
            } catch (IllegalArgumentException e) {
                Slog.e("PermissionManager", BootReceiver$$ExternalSyntheticOutline0.m("Failed to revoke ", str2, " from ", str), e);
            }
        }
    }

    public final int[] revokePermissionsNoLongerImplicitLocked(UidPermissionState uidPermissionState, Collection collection, int i, int i2, int[] iArr) {
        Permission permission;
        int i3;
        boolean z = i >= 23;
        for (String str : uidPermissionState.getGrantedPermissions()) {
            if (!collection.contains(str) && (permission = this.mRegistry.getPermission(str)) != null && permission.isRuntime()) {
                int permissionFlags = uidPermissionState.getPermissionFlags(str);
                if ((permissionFlags & 128) != 0) {
                    boolean z2 = ArrayUtils.contains(NEARBY_DEVICES_PERMISSIONS, str) && uidPermissionState.isPermissionGranted("android.permission.ACCESS_BACKGROUND_LOCATION") && (uidPermissionState.getPermissionFlags("android.permission.ACCESS_BACKGROUND_LOCATION") & 136) == 0;
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

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void revokePostNotificationPermissionWithoutKillForTest(String str, int i) {
        int callingUid = Binder.getCallingUid();
        boolean z = checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "default:0") == 0;
        this.mContext.enforceCallingPermission("android.permission.REVOKE_POST_NOTIFICATIONS_WITHOUT_KILL", "");
        revokeRuntimePermissionInternal(str, "android.permission.POST_NOTIFICATIONS", z, true, callingUid, i, "skip permission revoke app kill for notification test", this.mDefaultPermissionCallback);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void revokeRuntimePermission(String str, String str2, String str3, int i, String str4) {
        int callingUid = Binder.getCallingUid();
        revokeRuntimePermissionInternal(str, str2, checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "default:0") == 0, false, callingUid, i, str4, this.mDefaultPermissionCallback);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0088, code lost:
    
        if ((r2 == null ? false : java.util.Arrays.asList(r2).contains(r12.getPermissionControllerPackageName())) != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0182, code lost:
    
        if ((r7 & 4) != 0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x019e, code lost:
    
        throw new java.lang.SecurityException("Cannot revoke policy fixed permission " + r19 + " for package " + r18);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void revokeRuntimePermissionInternal(java.lang.String r18, java.lang.String r19, boolean r20, boolean r21, int r22, int r23, java.lang.String r24, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback r25) {
        /*
            Method dump skipped, instructions count: 621
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.revokeRuntimePermissionInternal(java.lang.String, java.lang.String, boolean, boolean, int, int, java.lang.String, com.android.server.pm.permission.PermissionManagerServiceImpl$PermissionCallback):void");
    }

    public final boolean setAllowlistedRestrictedPermissions(int i, String str, int i2, List list) {
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
        if (this.mPackageManagerInt.filterAppAccess(callingUid, UserHandle.getCallingUserId(), str, false)) {
            return false;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInt).mService.snapshotComputer().isCallerInstallerOfRecord(androidPackage, callingUid);
        if ((i & 1) != 0 && !z) {
            throw new SecurityException("Modifying system allowlist requires android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        if ((i & 4) != 0) {
            if (!z && !isCallerInstallerOfRecord) {
                throw new SecurityException("Modifying upgrade allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
            }
            List allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(androidPackage.getPackageName(), i, i2);
            if (!list.isEmpty()) {
                int size = list.size();
                for (int i3 = 0; i3 < size; i3++) {
                    if ((allowlistedRestrictedPermissions == null || !allowlistedRestrictedPermissions.contains(list.get(i3))) && !z) {
                        throw new SecurityException("Adding to upgrade allowlist requiresandroid.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                    }
                }
            } else if (allowlistedRestrictedPermissions == null || allowlistedRestrictedPermissions.isEmpty()) {
                return true;
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

    public final void setAllowlistedRestrictedPermissionsInternal(AndroidPackage androidPackage, List list, int i, int i2) {
        int i3;
        int i4;
        int myUid = Process.myUid();
        ArraySet arraySet = null;
        boolean z = false;
        for (String str : androidPackage.getRequestedPermissions()) {
            PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    Permission permission = this.mRegistry.getPermission(str);
                    if (permission != null && (permission.mPermissionInfo.flags & 12) != 0) {
                        UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i2);
                        if (uidStateLocked == null) {
                            Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                        } else {
                            boolean isPermissionGranted = uidStateLocked.isPermissionGranted(str);
                            if (isPermissionGranted) {
                                if (arraySet == null) {
                                    arraySet = new ArraySet();
                                }
                                arraySet.add(str);
                            }
                            ArraySet arraySet2 = arraySet;
                            int permissionFlagsInternal = getPermissionFlagsInternal(myUid, i2, androidPackage.getPackageName(), str);
                            int i5 = i;
                            int i6 = permissionFlagsInternal;
                            int i7 = 0;
                            while (i5 != 0) {
                                int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i5);
                                i5 &= ~numberOfTrailingZeros;
                                if (numberOfTrailingZeros == 1) {
                                    i7 |= 4096;
                                    i6 = (list == null || !list.contains(str)) ? i6 & (-4097) : i6 | 4096;
                                } else if (numberOfTrailingZeros == 2) {
                                    i7 |= 2048;
                                    i6 = (list == null || !list.contains(str)) ? i6 & (-2049) : i6 | 2048;
                                } else if (numberOfTrailingZeros == 4) {
                                    i7 |= 8192;
                                    i6 = (list == null || !list.contains(str)) ? i6 & (-8193) : i6 | 8192;
                                }
                            }
                            if (permissionFlagsInternal != i6) {
                                boolean z3 = (permissionFlagsInternal & 14336) != 0;
                                boolean z4 = (i6 & 14336) != 0;
                                if ((permissionFlagsInternal & 4) != 0 && !z4 && isPermissionGranted) {
                                    i7 |= 4;
                                    i6 &= -5;
                                }
                                if (androidPackage.getTargetSdkVersion() >= 23 || z3 || !z4) {
                                    i3 = i6;
                                    i4 = i7;
                                } else {
                                    i4 = i7 | 64;
                                    i3 = i6 | 64;
                                }
                                updatePermissionFlagsInternal(androidPackage.getPackageName(), str, i4, i3, myUid, i2, false, null);
                                z = true;
                            }
                            arraySet = arraySet2;
                        }
                    }
                } finally {
                }
            }
        }
        if (z) {
            restorePermissionState(androidPackage, false, androidPackage.getPackageName(), this.mDefaultPermissionCallback, i2);
            if (arraySet == null) {
                return;
            }
            int size = arraySet.size();
            for (int i8 = 0; i8 < size; i8++) {
                String str2 = (String) arraySet.valueAt(i8);
                PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
                boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                synchronized (packageManagerTracedLock2) {
                    try {
                        UidPermissionState uidStateLocked2 = getUidStateLocked(androidPackage.getUid(), i2);
                        if (uidStateLocked2 == null) {
                            Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                        } else {
                            boolean isPermissionGranted2 = uidStateLocked2.isPermissionGranted(str2);
                            if (!isPermissionGranted2) {
                                this.mDefaultPermissionCallback.onPermissionRevoked(null, UserHandle.getUid(i2, androidPackage.getUid()), false, i2, null);
                                return;
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void setDefaultPermissionGrantFingerprint(int i, String str) {
    }

    public final int[] setInitialGrantForNewImplicitPermissionsLocked(UidPermissionState uidPermissionState, UidPermissionState uidPermissionState2, AndroidPackage androidPackage, ArraySet arraySet, int i, int[] iArr) {
        androidPackage.getPackageName();
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList = SystemConfig.getInstance().mSplitPermissions;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo = (PermissionManager.SplitPermissionInfo) arrayList.get(i2);
            List newPermissions = splitPermissionInfo.getNewPermissions();
            int size2 = newPermissions.size();
            for (int i3 = 0; i3 < size2; i3++) {
                String str = (String) newPermissions.get(i3);
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
        for (int i4 = 0; i4 < size3; i4++) {
            String str2 = (String) arraySet.valueAt(i4);
            ArraySet arraySet3 = (ArraySet) arrayMap.get(str2);
            if (arraySet3 != null) {
                Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    throw new IllegalStateException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown new permission in split permission: ", str2));
                }
                if (permission.isRuntime()) {
                    if (!str2.equals("android.permission.ACTIVITY_RECOGNITION") && !((ArraySet) READ_MEDIA_AURAL_PERMISSIONS).contains(str2) && !((ArraySet) READ_MEDIA_VISUAL_PERMISSIONS).contains(str2)) {
                        uidPermissionState2.updatePermissionFlags(permission, 128, 128);
                    }
                    iArr2 = ArrayUtils.appendInt(iArr2, i);
                    if (uidPermissionState.mPermissions != null) {
                        int size4 = arraySet3.size();
                        for (int i5 = 0; i5 < size4; i5++) {
                            if (uidPermissionState.mPermissions.containsKey((String) arraySet3.valueAt(i5))) {
                                break;
                            }
                        }
                    }
                    for (int i6 = 0; i6 < arraySet3.size(); i6++) {
                        String str3 = (String) arraySet3.valueAt(i6);
                        Permission permission2 = this.mRegistry.getPermission(str3);
                        if (permission2 == null) {
                            throw new IllegalStateException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown source permission in split permission: ", str3));
                        }
                        if (!permission2.isRuntime()) {
                            androidPackage.getPackageName();
                            int size5 = arraySet3.size();
                            boolean z = false;
                            int i7 = 0;
                            for (int i8 = 0; i8 < size5; i8++) {
                                String str4 = (String) arraySet3.valueAt(i8);
                                if (uidPermissionState2.isPermissionGranted(str4)) {
                                    if (!z) {
                                        i7 = 0;
                                    }
                                    i7 |= uidPermissionState2.getPermissionFlags(str4);
                                    z = true;
                                } else if (!z) {
                                    i7 |= uidPermissionState2.getPermissionFlags(str4);
                                }
                            }
                            if (z) {
                                uidPermissionState2.grantPermission(this.mRegistry.getPermission(str2));
                            }
                            uidPermissionState2.updatePermissionFlags(this.mRegistry.getPermission(str2), i7, i7);
                        }
                    }
                }
            }
        }
        return iArr2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setLicensePermissionsForMDM(java.lang.String r14, java.util.Set r15) {
        /*
            Method dump skipped, instructions count: 596
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.setLicensePermissionsForMDM(java.lang.String, java.util.Set):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x0066, code lost:
    
        if (r10.isPrivileged() != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x006e, code lost:
    
        if (canGrantOemPermission(r10, r4) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldGrantPermissionByProtectionFlags(com.android.server.pm.pkg.AndroidPackage r9, com.android.server.pm.pkg.PackageStateInternal r10, com.android.server.pm.permission.Permission r11, android.util.ArraySet r12) {
        /*
            Method dump skipped, instructions count: 590
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.shouldGrantPermissionByProtectionFlags(com.android.server.pm.pkg.AndroidPackage, com.android.server.pm.pkg.PackageStateInternal, com.android.server.pm.permission.Permission, android.util.ArraySet):boolean");
    }

    public final boolean shouldGrantPermissionBySignature(AndroidPackage androidPackage, Permission permission) {
        AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage((String) ArrayUtils.firstOrNull(this.mPackageManagerInt.getKnownPackageNames(0, 0)));
        PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(permission.mPermissionInfo.packageName);
        return (packageStateInternal == null ? SigningDetails.UNKNOWN : ((PackageSetting) packageStateInternal).signatures.mSigningDetails).hasCommonSignerWithCapability(androidPackage.getSigningDetails(), 4) || androidPackage.getSigningDetails().hasAncestorOrSelf(androidPackage2.getSigningDetails()) || androidPackage2.getSigningDetails().checkCapability(androidPackage.getSigningDetails(), 4);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean shouldShowRequestPermissionRationale(int i, String str, String str2, String str3) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "canShowRequestPermissionRationale for user " + i);
        }
        if (UserHandle.getAppId(callingUid) != UserHandle.getAppId(this.mPackageManagerInt.getPackageUid(str, 268435456L, i)) || checkPermission(str, str2, "default:0", i) == 0) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int permissionFlagsInternal = getPermissionFlagsInternal(callingUid, i, str, str2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if ((permissionFlagsInternal & 22) != 0) {
                return false;
            }
            PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    Permission permission = this.mRegistry.getPermission(str2);
                    if (permission == null) {
                        return false;
                    }
                    if ((permission.mPermissionInfo.flags & 4) != 0 && (permissionFlagsInternal & 14336) == 0) {
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
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        } finally {
        }
    }

    public final void updateAllPermissions(String str, boolean z) {
        PackageManager.corkPackageInfoCache();
        try {
            updatePermissions(null, null, str, 1 | (z ? 6 : 0), this.mDefaultPermissionCallback);
        } finally {
            PackageManager.uncorkPackageInfoCache();
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3) {
        boolean z2;
        int callingUid = Binder.getCallingUid();
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

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void updatePermissionFlagsForAllApps(int i, int i2, final int i3) {
        int callingUid = Binder.getCallingUid();
        if (this.mUserManagerInt.exists(i3)) {
            enforceGrantRevokeRuntimePermissionPermissions("updatePermissionFlagsForAllApps");
            enforceCrossUserPermission(callingUid, i3, "updatePermissionFlagsForAllApps", true);
            if (callingUid == 1000) {
                i &= -17;
            }
            final int i4 = i;
            if (callingUid == 1000) {
                i2 &= -17;
            }
            final int i5 = i2;
            final boolean[] zArr = new boolean[1];
            this.mPackageManagerInt.forEachPackage(new Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ArrayMap arrayMap;
                    boolean z;
                    PermissionManagerServiceImpl permissionManagerServiceImpl = PermissionManagerServiceImpl.this;
                    int i6 = i3;
                    boolean[] zArr2 = zArr;
                    int i7 = i4;
                    int i8 = i5;
                    AndroidPackage androidPackage = (AndroidPackage) obj;
                    PackageManagerTracedLock packageManagerTracedLock = permissionManagerServiceImpl.mLock;
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock) {
                        try {
                            UidPermissionState uidStateLocked = permissionManagerServiceImpl.getUidStateLocked(androidPackage.getUid(), i6);
                            if (uidStateLocked == null) {
                                Slog.e("PermissionManager", "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i6);
                                return;
                            }
                            boolean z3 = zArr2[0];
                            if (i7 != 0 && (arrayMap = uidStateLocked.mPermissions) != null) {
                                z = false;
                                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                                    PermissionState permissionState = (PermissionState) uidStateLocked.mPermissions.valueAt(size);
                                    boolean updateFlags = permissionState.updateFlags(i7, i8);
                                    if (updateFlags && permissionState.isDefault()) {
                                        uidStateLocked.mPermissions.removeAt(size);
                                    }
                                    z |= updateFlags;
                                }
                                zArr2[0] = z3 | z;
                                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                                permissionManagerServiceImpl.mOnPermissionChangeListeners.onPermissionsChanged(androidPackage.getUid());
                            }
                            z = false;
                            zArr2[0] = z3 | z;
                            boolean z42 = PackageManagerService.DEBUG_COMPRESSION;
                            permissionManagerServiceImpl.mOnPermissionChangeListeners.onPermissionsChanged(androidPackage.getUid());
                        } catch (Throwable th) {
                            boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                            throw th;
                        }
                    }
                }
            });
            if (zArr[0]) {
                this.mPackageManagerInt.writePermissionSettings(new int[]{i3}, true);
            }
        }
    }

    public final void updatePermissionFlagsInternal(String str, String str2, int i, int i2, int i3, int i4, boolean z, PermissionCallback permissionCallback) {
        boolean z2 = true;
        if (this.mUserManagerInt.exists(i4)) {
            enforceGrantRevokeRuntimePermissionPermissions("updatePermissionFlags");
            enforceCrossUserPermission(i3, i4, "updatePermissionFlags", true);
            if ((i & 4) != 0 && !z) {
                throw new SecurityException("updatePermissionFlags requires android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
            }
            if (i3 != 1000) {
                i &= -49;
                i2 &= -30769;
            }
            AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
            if (androidPackage == null) {
                StorageManagerService$$ExternalSyntheticOutline0.m("Unknown package: ", str, "PermissionManager");
                return;
            }
            if (this.mPackageManagerInt.filterAppAccess(i3, i4, str, false)) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown package: ", str));
            }
            boolean contains = androidPackage.getRequestedPermissions().contains(str2);
            if (!contains) {
                for (String str3 : this.mPackageManagerInt.getSharedUserPackagesForPackage(i4, str)) {
                    AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(str3);
                    if (androidPackage2 != null && androidPackage2.getRequestedPermissions().contains(str2)) {
                        break;
                    }
                }
            }
            z2 = contains;
            PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    Permission permission = this.mRegistry.getPermission(str2);
                    if (permission == null) {
                        throw new IllegalArgumentException("Unknown permission: " + str2);
                    }
                    boolean isRuntime = permission.isRuntime();
                    UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i4);
                    if (uidStateLocked == null) {
                        Slog.e("PermissionManager", "Missing permissions state for " + str + " and user " + i4);
                        return;
                    }
                    if (!uidStateLocked.hasPermissionState(str2) && !z2) {
                        Log.e("PermissionManager", "Permission " + str2 + " isn't requested by package " + str);
                        return;
                    }
                    boolean updatePermissionFlags = uidStateLocked.updatePermissionFlags(permission, i, i2);
                    if (!updatePermissionFlags || permissionCallback == null) {
                        return;
                    }
                    if (isRuntime) {
                        permissionCallback.onPermissionUpdated(new int[]{i4}, false, androidPackage.getUid());
                    } else {
                        permissionCallback.onInstallPermissionUpdated();
                    }
                } catch (Throwable th) {
                    boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
    }

    public final boolean updatePermissionStatesAndFlagsInternalForMDM(int i, int i2, int i3, String str, List list, boolean z) {
        ArrayList arrayList;
        boolean z2;
        UidPermissionState uidPermissionState;
        Permission permission;
        boolean z3;
        try {
            if (str == null) {
                arrayList = new ArrayList(((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInt).mService.snapshotComputer().getPackageStates().values());
            } else {
                arrayList = new ArrayList();
                arrayList.add(this.mPackageManagerInt.getPackageStateInternal(str));
            }
            Iterator it = arrayList.iterator();
            int i4 = 1;
            boolean z4 = true;
            while (it.hasNext()) {
                try {
                    AndroidPackage androidPackage = ((PackageStateInternal) it.next()).getAndroidPackage();
                    Objects.requireNonNull(androidPackage, "AndroidPackage is null ");
                    PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                    boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock) {
                        try {
                            UidPermissionState uidStateLocked = getUidStateLocked(androidPackage.getUid(), i3);
                            try {
                                Objects.requireNonNull(uidStateLocked, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i3);
                                List requestedRuntimePermissionsLocked = getRequestedRuntimePermissionsLocked(androidPackage, list);
                                if (z) {
                                    ArrayList arrayList2 = (ArrayList) requestedRuntimePermissionsLocked;
                                    if (!arrayList2.contains("android.permission.READ_EXTERNAL_STORAGE")) {
                                        arrayList2.contains("android.permission.WRITE_EXTERNAL_STORAGE");
                                    }
                                }
                                Iterator it2 = ((ArrayList) requestedRuntimePermissionsLocked).iterator();
                                z2 = z4;
                                while (it2.hasNext()) {
                                    String str2 = (String) it2.next();
                                    try {
                                        permission = this.mRegistry.getPermission(str2);
                                        Objects.requireNonNull(permission, "BasePermission is null: " + str2);
                                        isPossibleToModify(i3, androidPackage, uidStateLocked, str2);
                                        if (i == i4) {
                                            z3 = handleGrantLocked(i3, androidPackage, uidStateLocked, str2, permission);
                                            uidPermissionState = uidStateLocked;
                                        } else if (i == 2) {
                                            uidPermissionState = uidStateLocked;
                                            try {
                                                z3 = handleDenyLocked(i3, androidPackage, uidStateLocked, str2, permission, z);
                                            } catch (NullPointerException e) {
                                                e = e;
                                                Log.i("PermissionManager", e.getMessage());
                                                z2 = false;
                                                uidStateLocked = uidPermissionState;
                                                i4 = 1;
                                            } catch (RuntimeException e2) {
                                                e = e2;
                                                Log.d("PermissionManager", e.getMessage());
                                                uidStateLocked = uidPermissionState;
                                                i4 = 1;
                                            }
                                        } else {
                                            uidPermissionState = uidStateLocked;
                                            z3 = true;
                                        }
                                    } catch (NullPointerException e3) {
                                        e = e3;
                                        uidPermissionState = uidStateLocked;
                                    } catch (RuntimeException e4) {
                                        e = e4;
                                        uidPermissionState = uidStateLocked;
                                    }
                                    if (z3) {
                                        try {
                                            try {
                                                uidPermissionState.updatePermissionFlags(permission, 4, i2);
                                            } catch (NullPointerException e5) {
                                                e = e5;
                                                Log.i("PermissionManager", e.getMessage());
                                                z2 = false;
                                                uidStateLocked = uidPermissionState;
                                                i4 = 1;
                                            } catch (RuntimeException e6) {
                                                e = e6;
                                                Log.d("PermissionManager", e.getMessage());
                                                uidStateLocked = uidPermissionState;
                                                i4 = 1;
                                            }
                                            uidStateLocked = uidPermissionState;
                                            i4 = 1;
                                        } catch (Throwable th) {
                                            th = th;
                                            boolean z6 = PackageManagerService.DEBUG_COMPRESSION;
                                            throw th;
                                        }
                                    } else {
                                        z2 = false;
                                        uidStateLocked = uidPermissionState;
                                        i4 = 1;
                                    }
                                }
                            } catch (NullPointerException e7) {
                                Slog.e("PermissionManager", e7.getMessage());
                                boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                                return false;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    try {
                        boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
                        z4 = z2;
                    } catch (NullPointerException e8) {
                        e = e8;
                        Log.i("PermissionManager", e.getMessage());
                        z4 = false;
                        i4 = 1;
                    }
                } catch (NullPointerException e9) {
                    e = e9;
                }
                i4 = 1;
            }
            this.mPackageManagerInt.writePermissionSettings(new int[]{i3}, true);
            return z4;
        } catch (Exception e10) {
            e10.printStackTrace();
            AudioServiceExt$ResetSettingsReceiver$$ExternalSyntheticOutline0.m(e10, new StringBuilder("Failed with Exception "), "PermissionManager");
            return false;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePermissions(final java.lang.String r17, final com.android.server.pm.pkg.AndroidPackage r18, final java.lang.String r19, int r20, final com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback r21) {
        /*
            Method dump skipped, instructions count: 631
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerServiceImpl.updatePermissions(java.lang.String, com.android.server.pm.pkg.AndroidPackage, java.lang.String, int, com.android.server.pm.permission.PermissionManagerServiceImpl$PermissionCallback):void");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void writeLegacyPermissionStateTEMP() {
        int[] iArr;
        int i = 1;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                DevicePermissionState devicePermissionState = this.mState;
                int size = devicePermissionState.mUserStates.size();
                iArr = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = devicePermissionState.mUserStates.keyAt(i2);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        this.mPackageManagerInt.forEachPackageSetting(new PermissionManagerServiceImpl$$ExternalSyntheticLambda10(this, iArr, i));
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void writeLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
        Collection<Permission> values;
        for (int i = 0; i < 2; i++) {
            ArrayList arrayList = new ArrayList();
            PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                if (i == 0) {
                    try {
                        values = this.mRegistry.mPermissions.values();
                    } catch (Throwable th) {
                        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                        throw th;
                    }
                } else {
                    values = this.mRegistry.mPermissionTrees.values();
                }
                for (Permission permission : values) {
                    arrayList.add(new LegacyPermission(permission.mPermissionInfo, permission.mType, 0, EmptyArray.INT));
                }
            }
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            if (i == 0) {
                legacyPermissionSettings.replacePermissions(arrayList);
            } else {
                legacyPermissionSettings.replacePermissionTrees(arrayList);
            }
        }
    }
}
