package com.android.server.permission.access.permission;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.metrics.LogMaker;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.IOnPermissionsChangeListener;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.internal.compat.IPlatformCompat;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PermissionThread;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemConfig;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.companion.virtual.VirtualDeviceManagerService;
import com.android.server.permission.access.AccessCheckingService;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutableSystemState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IndexedMapExtensionsKt;
import com.android.server.permission.access.immutable.IndexedSetExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedReferenceMap;
import com.android.server.permission.access.immutable.MutableIndexedSet;
import com.android.server.permission.access.immutable.MutableIntMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableIntSet;
import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.jarjar.kotlin.collections.CollectionsKt;
import com.android.server.permission.jarjar.kotlin.collections.EmptyList;
import com.android.server.permission.jarjar.kotlin.collections.EmptySet;
import com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt;
import com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef;
import com.android.server.permission.jarjar.kotlin.text.StringsKt__AppendableKt;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PmHook;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.LegacyPermission;
import com.android.server.pm.permission.LegacyPermissionSettings;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.permission.PermissionManagerServiceInterface;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;

import libcore.util.EmptyArray;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.license.IEnterpriseLicense;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PermissionService implements PermissionManagerServiceInterface {
    public static final long BACKUP_TIMEOUT_MILLIS;
    public static final ArrayMap FULLER_PERMISSIONS;
    public static final ArraySet NOTIFICATIONS_PERMISSIONS;
    public final Context context;
    public final DevicePermissionPolicy devicePolicy;
    public Handler handler;
    public ServiceThread handlerThread;
    public final SparseBooleanArray isDelayedPermissionBackupFinished;
    public final ArrayMap mPackageGrantedPermissions;
    public MetricsLogger metricsLogger;
    public final ArraySet mountedStorageVolumes;
    public OnPermissionFlagsChangedListener onPermissionFlagsChangedListener;
    public OnPermissionsChangeListeners onPermissionsChangeListeners;
    public PackageManagerInternal packageManagerInternal;
    public PackageManagerLocal packageManagerLocal;
    public PermissionControllerManager permissionControllerManager;
    public IPlatformCompat platformCompat;
    public final AppIdPermissionPolicy policy;
    public final AccessCheckingService service;
    public final Object storageVolumeLock;
    public final ArrayMap storageVolumePackageNames;
    public SystemConfig systemConfig;
    public UserManagerInternal userManagerInternal;
    public UserManagerService userManagerService;
    public VirtualDeviceManagerInternal virtualDeviceManagerInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnPermissionFlagsChangedListener
            implements AppIdPermissionPolicy.OnPermissionFlagsChangedListener,
                    DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener {
        public boolean isKillRuntimePermissionRevokedUidsSkipped;
        public boolean isPermissionFlagsChanged;
        public final MutableIntMap runtimePermissionChangedUidDevices = new MutableIntMap();
        public final SparseBooleanArray runtimePermissionRevokedUids = new SparseBooleanArray();
        public final MutableIntSet gidsChangedUids = new MutableIntSet();
        public final ArraySet killRuntimePermissionRevokedUidsReasons = new ArraySet();

        public OnPermissionFlagsChangedListener() {}

        public final Integer getSecureInt(int i, String str) {
            try {
                return Integer.valueOf(
                        Settings.Secure.getIntForUser(
                                PermissionService.this.context.getContentResolver(), str, i));
            } catch (Settings.SettingNotFoundException e) {
                ArrayMap arrayMap = PermissionService.FULLER_PERMISSIONS;
                Slog.i("PermissionService", "Setting " + str + " not found", e);
                return null;
            }
        }

        @Override // com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener
        public final void onDevicePermissionFlagsChanged(
                int i, int i2, int i3, String str, String str2, int i4) {
            this.isPermissionFlagsChanged = true;
            int uid = UserHandle.getUid(i2, i);
            PermissionService permissionService = PermissionService.this;
            AccessState accessState = permissionService.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            permissionService.policy.getClass();
            Permission permission =
                    (Permission) accessState.getSystemState().getPermissions().map.get(str2);
            if (permission == null) {
                return;
            }
            boolean isPermissionGranted = PermissionFlags.isPermissionGranted(i3);
            boolean isPermissionGranted2 = PermissionFlags.isPermissionGranted(i4);
            if (permission.permissionInfo.getProtection() == 1) {
                if (isPermissionGranted && !isPermissionGranted2) {
                    this.runtimePermissionRevokedUids.put(
                            uid,
                            PermissionService.NOTIFICATIONS_PERMISSIONS.contains(str2)
                                    && this.runtimePermissionRevokedUids.get(uid, true));
                }
                MutableIntMap mutableIntMap = this.runtimePermissionChangedUidDevices;
                Object obj = mutableIntMap.array.get(uid);
                if (obj == null) {
                    obj = new LinkedHashSet();
                    SparseArray sparseArray = mutableIntMap.array;
                    int indexOfKey = sparseArray.indexOfKey(uid);
                    if (indexOfKey >= 0) {
                        sparseArray.valueAt(indexOfKey);
                        sparseArray.setValueAt(indexOfKey, obj);
                    } else {
                        sparseArray.put(uid, obj);
                    }
                }
                ((Collection) obj).add(str);
            }
            if ((!(permission.gids.length == 0)) && !isPermissionGranted && isPermissionGranted2) {
                this.gidsChangedUids.array.put(uid, true);
            }
        }

        @Override // com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener
        public final void onPermissionFlagsChanged(int i, int i2, int i3, int i4, String str) {
            onDevicePermissionFlagsChanged(i, i2, i3, "default:0", str, i4);
        }

        @Override // com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener, com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener
        public final void onStateMutated() {
            String str;
            if (this.isPermissionFlagsChanged) {
                PackageManager.invalidatePackageInfoCache();
                this.isPermissionFlagsChanged = false;
            }
            MutableIntMap mutableIntMap = this.runtimePermissionChangedUidDevices;
            int size = mutableIntMap.array.size();
            int i = 0;
            while (true) {
                final PermissionService permissionService = PermissionService.this;
                if (i >= size) {
                    mutableIntMap.array.clear();
                    if (!this.isKillRuntimePermissionRevokedUidsSkipped) {
                        if (!this.killRuntimePermissionRevokedUidsReasons.isEmpty()) {
                            ArraySet arraySet = this.killRuntimePermissionRevokedUidsReasons;
                            Intrinsics.checkNotNullParameter("<this>", arraySet);
                            StringBuilder sb = new StringBuilder();
                            sb.append((CharSequence) "");
                            int i2 = 0;
                            for (Object obj : arraySet) {
                                i2++;
                                if (i2 > 1) {
                                    sb.append((CharSequence) ", ");
                                }
                                StringsKt__AppendableKt.appendElement(sb, obj, null);
                            }
                            sb.append((CharSequence) "");
                            str = sb.toString();
                            Intrinsics.checkNotNullExpressionValue("toString(...)", str);
                        } else {
                            str = "permissions revoked";
                        }
                        final String str2 = str;
                        SparseBooleanArray sparseBooleanArray = this.runtimePermissionRevokedUids;
                        int size2 = sparseBooleanArray.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            final int keyAt = sparseBooleanArray.keyAt(i3);
                            final boolean valueAt = sparseBooleanArray.valueAt(i3);
                            Handler handler = permissionService.handler;
                            if (handler == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("handler");
                                throw null;
                            }
                            handler.post(
                                    new Runnable() { // from class:
                                        // com.android.server.permission.access.permission.PermissionService$OnPermissionFlagsChangedListener$onStateMutated$2$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            if (valueAt) {
                                                PermissionService.OnPermissionFlagsChangedListener
                                                        onPermissionFlagsChangedListener = this;
                                                int i4 = keyAt;
                                                if (PermissionService.this.checkUidPermission(
                                                                i4,
                                                                "android.permission.BACKUP",
                                                                "default:0")
                                                        == 0) {
                                                    int userId = UserHandle.getUserId(i4);
                                                    Integer secureInt =
                                                            onPermissionFlagsChangedListener
                                                                    .getSecureInt(
                                                                            userId,
                                                                            "user_setup_complete");
                                                    if (secureInt != null
                                                            && secureInt.intValue() == 0) {
                                                        return;
                                                    }
                                                    Integer secureInt2 =
                                                            onPermissionFlagsChangedListener
                                                                    .getSecureInt(
                                                                            userId,
                                                                            "user_setup_personalization_state");
                                                    if (secureInt2 != null
                                                            && secureInt2.intValue() == 1) {
                                                        return;
                                                    }
                                                }
                                            }
                                            PermissionService.access$killUid(
                                                    permissionService, keyAt, str2);
                                        }
                                    });
                        }
                    }
                    this.runtimePermissionRevokedUids.clear();
                    MutableIntSet mutableIntSet = this.gidsChangedUids;
                    int size3 = mutableIntSet.array.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        final int keyAt2 = mutableIntSet.array.keyAt(i4);
                        Handler handler2 = permissionService.handler;
                        if (handler2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("handler");
                            throw null;
                        }
                        handler2.post(
                                new Runnable() { // from class:
                                                 // com.android.server.permission.access.permission.PermissionService$OnPermissionFlagsChangedListener$onStateMutated$3$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        PermissionService.access$killUid(
                                                PermissionService.this,
                                                keyAt2,
                                                "permission grant or revoke changed gids");
                                    }
                                });
                    }
                    mutableIntSet.array.clear();
                    this.isKillRuntimePermissionRevokedUidsSkipped = false;
                    this.killRuntimePermissionRevokedUidsReasons.clear();
                    return;
                }
                int keyAt3 = mutableIntMap.array.keyAt(i);
                for (String str3 : (Set) mutableIntMap.array.valueAt(i)) {
                    OnPermissionsChangeListeners onPermissionsChangeListeners =
                            permissionService.onPermissionsChangeListeners;
                    if (onPermissionsChangeListeners == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                "onPermissionsChangeListeners");
                        throw null;
                    }
                    if (onPermissionsChangeListeners.listeners.getRegisteredCallbackCount() > 0) {
                        onPermissionsChangeListeners
                                .obtainMessage(1, keyAt3, 0, str3)
                                .sendToTarget();
                    }
                }
                i++;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnPermissionsChangeListeners extends Handler {
        public final RemoteCallbackList listeners;

        public OnPermissionsChangeListeners(Looper looper) {
            super(looper);
            this.listeners = new RemoteCallbackList();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                int i = message.arg1;
                Object obj = message.obj;
                Intrinsics.checkNotNull("null cannot be cast to non-null type kotlin.String", obj);
                this.listeners.broadcast(
                        new PermissionService$restoreDelayedRuntimePermissions$3(i, (String) obj));
            }
        }
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION");
        arrayMap.put(
                "android.permission.INTERACT_ACROSS_USERS",
                "android.permission.INTERACT_ACROSS_USERS_FULL");
        FULLER_PERMISSIONS = arrayMap;
        List asList = Arrays.asList("android.permission.POST_NOTIFICATIONS");
        Intrinsics.checkNotNullExpressionValue("asList(...)", asList);
        NOTIFICATIONS_PERMISSIONS = new ArraySet(asList);
        BACKUP_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
    }

    public PermissionService(AccessCheckingService accessCheckingService) {
        this.service = accessCheckingService;
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "uid", "permission");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar);
        this.policy =
                (AppIdPermissionPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2 =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "uid", "device-permission");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.permission.DevicePermissionPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2);
        this.devicePolicy =
                (DevicePermissionPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2;
        this.mPackageGrantedPermissions = new ArrayMap();
        this.context = accessCheckingService.getContext();
        this.storageVolumeLock = new Object();
        this.mountedStorageVolumes = new ArraySet();
        this.storageVolumePackageNames = new ArrayMap();
        this.isDelayedPermissionBackupFinished = new SparseBooleanArray();
    }

    public static final void access$enforcePermissionTreeSize(
            PermissionService permissionService,
            MutateStateScope mutateStateScope,
            PermissionInfo permissionInfo,
            Permission permission) {
        permissionService.getClass();
        int i = permission.appId;
        if (i != 1000) {
            permissionService.policy.getClass();
            IndexedMap permissions = mutateStateScope.state.getSystemState().getPermissions();
            int size = permissions.map.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                Object keyAt = permissions.map.keyAt(i3);
                Permission permission2 = (Permission) permissions.map.valueAt(i3);
                if (i == permission2.appId) {
                    i2 =
                            permission2.permissionInfo.calculateFootprint()
                                    + permission2.permissionInfo.name.length()
                                    + i2;
                }
            }
            if (permissionInfo.calculateFootprint() + i2 > 32768) {
                throw new SecurityException("Permission tree size cap exceeded");
            }
        }
    }

    public static final Permission access$getAndEnforcePermissionTree(
            PermissionService permissionService, MutateStateScope mutateStateScope, String str) {
        permissionService.getClass();
        int callingUid = Binder.getCallingUid();
        permissionService.policy.getClass();
        Permission findPermissionTree =
                AppIdPermissionPolicy.findPermissionTree(mutateStateScope, str);
        if (findPermissionTree != null) {
            if (findPermissionTree.appId == UserHandle.getAppId(callingUid)) {
                return findPermissionTree;
            }
        }
        throw new SecurityException(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        callingUid,
                        "Calling UID ",
                        " is not allowed to add to or remove from the permission tree"));
    }

    public static final boolean access$handlePermissionStateInternal(
            PermissionService permissionService,
            MutateStateScope mutateStateScope,
            int i,
            int i2,
            int i3,
            Permission permission,
            int i4) {
        AppIdPermissionPolicy appIdPermissionPolicy = permissionService.policy;
        String str = permission.permissionInfo.name;
        appIdPermissionPolicy.getClass();
        AccessState accessState = mutateStateScope.state;
        int permissionFlags = AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str);
        boolean isPermissionGranted =
                i3 != 1
                        ? i3 != 2 ? PermissionFlags.isPermissionGranted(permissionFlags) : false
                        : true;
        permissionService.policy.updatePermissionFlags(
                mutateStateScope,
                i,
                i2,
                permission.permissionInfo.name,
                -1,
                PermissionFlags.updateFlags(
                        permission,
                        isPermissionGranted ? permissionFlags | 16 : permissionFlags & (-17),
                        4,
                        i4));
        AppIdPermissionPolicy appIdPermissionPolicy2 = permissionService.policy;
        String str2 = permission.permissionInfo.name;
        appIdPermissionPolicy2.getClass();
        return isPermissionGranted
                == PermissionFlags.isPermissionGranted(
                        AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str2));
    }

    public static final void access$killUid(
            PermissionService permissionService, int i, String str) {
        permissionService.getClass();
        IActivityManager service = ActivityManager.getService();
        if (service != null) {
            int appId = UserHandle.getAppId(i);
            int userId = UserHandle.getUserId(i);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                service.killUidForPermissionChange(appId, userId, str);
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static final void access$setRuntimePermissionGranted(
            PermissionService permissionService,
            MutateStateScope mutateStateScope,
            PackageState packageState,
            int i,
            String str,
            String str2,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            String str3) {
        permissionService.policy.getClass();
        Permission permission =
                (Permission) mutateStateScope.state.getSystemState().getPermissions().map.get(str);
        if (permission == null) {
            if (z4) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Unknown permission ", str));
            }
            return;
        }
        AndroidPackage androidPackage = packageState.getAndroidPackage();
        Intrinsics.checkNotNull(androidPackage);
        String packageName = packageState.getPackageName();
        if (!IntExtensionsKt.hasBits(permission.permissionInfo.getProtectionFlags(), 32)) {
            if (IntExtensionsKt.hasBits(permission.permissionInfo.getProtectionFlags(), 67108864)) {
                if (!z2) {
                    if (z4) {
                        throw new SecurityException(
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        "Permission ", str, " is managed by role"));
                    }
                    return;
                }
            } else if (permission.permissionInfo.getProtection() != 1) {
                if (z4) {
                    throw new SecurityException(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Permission ",
                                    str,
                                    " requested by package ",
                                    packageName,
                                    " is not a changeable permission type"));
                }
                return;
            } else {
                if (androidPackage.getTargetSdkVersion() < 23) {
                    return;
                }
                if (z
                        && packageState.getUserStateOrDefault(i).isInstantApp()
                        && !IntExtensionsKt.hasBits(
                                permission.permissionInfo.getProtectionFlags(), 4096)) {
                    if (z4) {
                        throw new SecurityException(
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        "Cannot grant non-instant permission ",
                                        str,
                                        " to package ",
                                        packageName));
                    }
                    return;
                }
            }
        }
        int appId = packageState.getAppId();
        int permissionFlagsWithPolicy =
                permissionService.getPermissionFlagsWithPolicy(
                        appId, i, mutateStateScope, str, str2);
        if (!androidPackage.getRequestedPermissions().contains(str)
                && permissionFlagsWithPolicy == 0) {
            if (z4) {
                Slog.e(
                        "PermissionService",
                        "Permission " + str + " isn't requested by package " + packageName);
                return;
            }
            return;
        }
        if (IntExtensionsKt.hasBits(permissionFlagsWithPolicy, 256)) {
            if (z4) {
                Slog.e(
                        "PermissionService",
                        str3
                                + ": Cannot change system fixed permission "
                                + str
                                + " for package "
                                + packageName);
                return;
            }
            return;
        }
        if (IntExtensionsKt.hasBits(permissionFlagsWithPolicy, 128) && !z3) {
            if (z4) {
                Slog.e(
                        "PermissionService",
                        str3
                                + ": Cannot change policy fixed permission "
                                + str
                                + " for package "
                                + packageName);
                return;
            }
            return;
        }
        if (z && IntExtensionsKt.hasBits(permissionFlagsWithPolicy, 262144)) {
            if (z4) {
                Slog.e(
                        "PermissionService",
                        str3
                                + ": Cannot grant hard-restricted non-exempt permission "
                                + str
                                + " to package "
                                + packageName);
                return;
            }
            return;
        }
        if (z && IntExtensionsKt.hasBits(permissionFlagsWithPolicy, 524288)) {
            if (z4) {
                Slog.e(
                        "PermissionService",
                        str3
                                + ": Cannot grant soft-restricted non-exempt permission "
                                + str
                                + " to package "
                                + packageName);
                return;
            }
            return;
        }
        int i2 = z ? permissionFlagsWithPolicy | 16 : permissionFlagsWithPolicy & (-17);
        if (permissionFlagsWithPolicy == i2) {
            return;
        }
        permissionService.setPermissionFlagsWithPolicy(appId, i, i2, mutateStateScope, str, str2);
        if (permission.permissionInfo.getProtection() == 1) {
            LogMaker logMaker = new LogMaker(z ? 1243 : 1245);
            logMaker.setPackageName(packageName);
            logMaker.addTaggedData(1241, str);
            MetricsLogger metricsLogger = permissionService.metricsLogger;
            if (metricsLogger != null) {
                metricsLogger.write(logMaker);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("metricsLogger");
                throw null;
            }
        }
    }

    public static final boolean access$updateLicensePermissionInternalMDM(
            PermissionService permissionService,
            MutateStateScope mutateStateScope,
            String str,
            String str2,
            int i,
            boolean z) {
        permissionService.getClass();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        if (!mutableAccessState.getUserStates().array.contains(i)) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        PackageManagerLocal packageManagerLocal = permissionService.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            PackageManagerLocal.FilteredSnapshot filtered =
                    withUnfilteredSnapshot.filtered(callingUid, UserHandle.of(i));
            try {
                PackageState packageState = filtered.getPackageState(str);
                AutoCloseableKt.closeFinally(filtered, null);
                ref$ObjectRef.element = packageState;
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                Object obj = ref$ObjectRef.element;
                Intrinsics.checkNotNull(obj);
                int appId = ((PackageState) obj).getAppId();
                Immutable immutable = mutableAccessState.getUserStates().get(i);
                Intrinsics.checkNotNull(immutable);
                int intValue =
                        ((Number)
                                        IndexedMapExtensionsKt.getWithDefault(
                                                (IndexedMap)
                                                        ((MutableUserState) immutable)
                                                                .getAppIdPermissionFlags()
                                                                .get(appId),
                                                str2,
                                                0))
                                .intValue();
                permissionService.policy.updatePermissionFlags(
                        mutateStateScope, appId, i, str2, -1, z ? intValue | 16 : intValue & (-17));
                permissionService.policy.getClass();
                int permissionFlags =
                        AppIdPermissionPolicy.getPermissionFlags(
                                mutateStateScope.state, appId, i, str2);
                if (!IntExtensionsKt.hasBits(permissionFlags, 1)) {
                    if (IntExtensionsKt.hasBits(permissionFlags, 2)) {
                        return false;
                    }
                    if (!IntExtensionsKt.hasBits(permissionFlags, 4)
                            && !IntExtensionsKt.hasBits(permissionFlags, 1024)
                            && !IntExtensionsKt.hasBits(permissionFlags, 2048)) {
                        if (IntExtensionsKt.hasBits(permissionFlags, 262144)) {
                            return false;
                        }
                        return IntExtensionsKt.hasBits(permissionFlags, 16);
                    }
                }
                return true;
            } finally {
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, th);
                throw th2;
            }
        }
    }

    public static final void access$updatePermissionFlags(
            PermissionService permissionService,
            MutateStateScope mutateStateScope,
            int i,
            int i2,
            String str,
            String str2,
            int i3,
            int i4,
            boolean z,
            boolean z2,
            boolean z3,
            String str3,
            String str4) {
        int i5;
        int i6;
        permissionService.getClass();
        if (z) {
            i5 = i3;
            i6 = i4;
        } else {
            i5 = i3 & (-30769);
            i6 = i4 & (-30769);
        }
        int i7 = i5;
        int i8 = i6;
        permissionService.policy.getClass();
        Permission permission =
                (Permission) mutateStateScope.state.getSystemState().getPermissions().map.get(str);
        if (permission == null) {
            if (z2) {
                throw new IllegalArgumentException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Unknown permission ", str));
            }
            return;
        }
        int permissionFlagsWithPolicy =
                permissionService.getPermissionFlagsWithPolicy(i, i2, mutateStateScope, str, str2);
        if (z3 || permissionFlagsWithPolicy != 0) {
            permissionService.setPermissionFlagsWithPolicy(
                    i,
                    i2,
                    PermissionFlags.updateFlags(permission, permissionFlagsWithPolicy, i7, i8),
                    mutateStateScope,
                    str,
                    str2);
            return;
        }
        Slog.w(
                "PermissionService",
                str3 + ": Permission " + str + " isn't requested by package " + str4);
    }

    public static void dumpAppIdState(
            IndentingPrintWriter indentingPrintWriter,
            int i,
            AccessState accessState,
            MutableIndexedSet mutableIndexedSet) {
        indentingPrintWriter.println("App ID: " + i);
        indentingPrintWriter.increaseIndent();
        MutableIntReferenceMap userStates = accessState.getUserStates();
        int size = userStates.array.size();
        int i2 = 0;
        while (i2 < size) {
            int keyAt = userStates.array.keyAt(i2);
            MutableUserState mutableUserState = (MutableUserState) userStates.valueAt(i2);
            indentingPrintWriter.println("User: " + keyAt);
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Permissions:");
            indentingPrintWriter.increaseIndent();
            IndexedMap indexedMap = (IndexedMap) mutableUserState.getAppIdPermissionFlags().get(i);
            if (indexedMap != null) {
                int size2 = indexedMap.map.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Object keyAt2 = indexedMap.map.keyAt(i3);
                    int intValue = ((Number) indexedMap.map.valueAt(i3)).intValue();
                    indentingPrintWriter.println(
                            ((String) keyAt2)
                                    + ": granted="
                                    + PermissionFlags.isPermissionGranted(intValue)
                                    + ", flags="
                                    + PermissionFlags.toString(intValue));
                }
            }
            indentingPrintWriter.decreaseIndent();
            MutableIndexedReferenceMap mutableIndexedReferenceMap =
                    (MutableIndexedReferenceMap)
                            mutableUserState.getAppIdDevicePermissionFlags().get(i);
            if (mutableIndexedReferenceMap != null) {
                int size3 = mutableIndexedReferenceMap.map.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    Object keyAt3 = mutableIndexedReferenceMap.map.keyAt(i4);
                    IndexedMap indexedMap2 = (IndexedMap) mutableIndexedReferenceMap.valueAt(i4);
                    indentingPrintWriter.println("Permissions (Device " + ((String) keyAt3) + "):");
                    indentingPrintWriter.increaseIndent();
                    int size4 = indexedMap2.map.size();
                    int i5 = 0;
                    while (i5 < size4) {
                        Object keyAt4 = indexedMap2.map.keyAt(i5);
                        MutableIntReferenceMap mutableIntReferenceMap = userStates;
                        int intValue2 = ((Number) indexedMap2.map.valueAt(i5)).intValue();
                        int i6 = size;
                        indentingPrintWriter.println(
                                ((String) keyAt4)
                                        + ": granted="
                                        + PermissionFlags.isPermissionGranted(intValue2)
                                        + ", flags="
                                        + PermissionFlags.toString(intValue2));
                        i5++;
                        userStates = mutableIntReferenceMap;
                        size = i6;
                        mutableIndexedReferenceMap = mutableIndexedReferenceMap;
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            }
            MutableIntReferenceMap mutableIntReferenceMap2 = userStates;
            int i7 = size;
            indentingPrintWriter.println("App ops:");
            indentingPrintWriter.increaseIndent();
            IndexedMap indexedMap3 = (IndexedMap) mutableUserState.getAppIdAppOpModes().get(i);
            if (indexedMap3 != null) {
                int size5 = indexedMap3.map.size();
                for (int i8 = 0; i8 < size5; i8++) {
                    Object keyAt5 = indexedMap3.map.keyAt(i8);
                    indentingPrintWriter.println(
                            ((String) keyAt5)
                                    + ": mode="
                                    + AppOpsManager.modeToName(
                                            ((Number) indexedMap3.map.valueAt(i8)).intValue()));
                }
            }
            indentingPrintWriter.decreaseIndent();
            if (mutableIndexedSet != null) {
                int size6 = mutableIndexedSet.set.size();
                for (int i9 = 0; i9 < size6; i9++) {
                    String str = (String) CollectionsKt.elementAt(mutableIndexedSet.set, i9);
                    indentingPrintWriter.println("Package: " + str);
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println(
                            "version="
                                    + ((IndexedMap)
                                                    mutableUserState
                                                            .packageVersionsReference
                                                            .immutable)
                                            .map.get(str));
                    indentingPrintWriter.println("App ops:");
                    indentingPrintWriter.increaseIndent();
                    IndexedMap indexedMap4 =
                            (IndexedMap) mutableUserState.getPackageAppOpModes().get(str);
                    if (indexedMap4 != null) {
                        int size7 = indexedMap4.map.size();
                        for (int i10 = 0; i10 < size7; i10++) {
                            Object keyAt6 = indexedMap4.map.keyAt(i10);
                            indentingPrintWriter.println(
                                    ((String) keyAt6)
                                            + ": mode="
                                            + AppOpsManager.modeToName(
                                                    ((Number) indexedMap4.map.valueAt(i10))
                                                            .intValue()));
                        }
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                }
            }
            indentingPrintWriter.decreaseIndent();
            i2++;
            userStates = mutableIntReferenceMap2;
            size = i7;
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static PermissionInfo generatePermissionInfo(Permission permission, int i, int i2) {
        int protection;
        PermissionInfo permissionInfo = new PermissionInfo(permission.permissionInfo);
        permissionInfo.flags |= 1073741824;
        if (!IntExtensionsKt.hasBits(i, 128)) {
            permissionInfo.metaData = null;
        }
        if (i2 < 26 && (protection = permissionInfo.getProtection()) != 2) {
            permissionInfo.protectionLevel = protection;
        }
        return permissionInfo;
    }

    public static boolean isPackageVisibleToUid(
            PackageManagerLocal.UnfilteredSnapshot unfilteredSnapshot, String str, int i) {
        PackageManagerLocal.FilteredSnapshot filtered =
                unfilteredSnapshot.filtered(i, UserHandle.of(UserHandle.getUserId(i)));
        try {
            boolean z = filtered.getPackageState(str) != null;
            AutoCloseableKt.closeFinally(filtered, null);
            return z;
        } finally {
        }
    }

    public static boolean isRootOrSystemUid(int i) {
        int appId = UserHandle.getAppId(i);
        return appId == 0 || appId == 1000;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setRuntimePermissionGranted$default(
            com.android.server.permission.access.permission.PermissionService r17,
            java.lang.String r18,
            int r19,
            java.lang.String r20,
            java.lang.String r21,
            boolean r22,
            boolean r23,
            java.lang.String r24,
            int r25) {
        /*
            Method dump skipped, instructions count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.PermissionService.setRuntimePermissionGranted$default(com.android.server.permission.access.permission.PermissionService,"
                    + " java.lang.String, int, java.lang.String, java.lang.String, boolean,"
                    + " boolean, java.lang.String, int):void");
    }

    public static List toLegacyPermissions(IndexedMap indexedMap) {
        ArrayList arrayList = new ArrayList();
        int size = indexedMap.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = indexedMap.map.keyAt(i);
            Permission permission = (Permission) indexedMap.map.valueAt(i);
            arrayList.add(
                    new LegacyPermission(
                            permission.permissionInfo, permission.type, 0, EmptyArray.INT));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0052 -> B:31:0x0069). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x0049 -> B:31:0x0069). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x0057 -> B:31:0x0069). Please report as a decompilation issue!!! */
    public static void updateAdminPermissionsInternal(String str) {
        String str2;
        IEnterpriseLicense asInterface;
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "updateAdminPermissionsInternal", str, "PermissionService");
        if (str != null) {
            if (!PmHook.mSystemReady
                    || (asInterface =
                                    IEnterpriseLicense.Stub.asInterface(
                                            ServiceManager.getService("enterprise_license_policy")))
                            == null) {
                return;
            }
            try {
                if (asInterface.getInstanceId(str) == null) {
                    return;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            } catch (SecurityException e2) {
                e2.printStackTrace();
                return;
            }
        }
        try {
            boolean z = PmHook.mSystemReady;
            str2 = z;
            if (z) {
                IEnterpriseLicense asInterface2 =
                        IEnterpriseLicense.Stub.asInterface(
                                ServiceManager.getService("enterprise_license_policy"));
                str2 = "PM_HOOK";
                try {
                    if (asInterface2 != null) {
                        asInterface2.updateAdminPermissions();
                        str2 = str2;
                    } else {
                        Log.d("PM_HOOK", "ENTERPRISE_LICENSE_POLICY_SERVICE is null");
                        str2 = str2;
                    }
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                    str2 = str2;
                } catch (NullPointerException e4) {
                    Log.e(str2, "NPE occurs for EnterpriseLicense", e4);
                    e4.printStackTrace();
                    str2 = str2;
                }
            }
        } catch (Exception e5) {
            StringBuilder sb =
                    new StringBuilder("updateAdminPermissionsInternal error as PackageName - ");
            sb.append(str);
            str = sb.toString();
            Slog.e("PermissionService", str, e5);
            str2 = sb;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean addAllowlistedRestrictedPermission(
            String str, String str2, int i, int i2) {
        if (str2 == null) {
            throw new IllegalArgumentException("permissionName cannot be null".toString());
        }
        if (!enforceRestrictedPermission(str2)) {
            return false;
        }
        ArrayList allowlistedRestrictedPermissions =
                getAllowlistedRestrictedPermissions(i, i2, str);
        if (allowlistedRestrictedPermissions == null) {
            allowlistedRestrictedPermissions = new ArrayList(1);
        }
        ArrayList arrayList = allowlistedRestrictedPermissions;
        if (arrayList.contains(str2)) {
            return false;
        }
        arrayList.add(str2);
        return setAllowlistedRestrictedPermissions(str, arrayList, i, i2, true);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void addOnPermissionsChangeListener(
            IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.context.enforceCallingOrSelfPermission(
                "android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS",
                "addOnPermissionsChangeListener");
        OnPermissionsChangeListeners onPermissionsChangeListeners =
                this.onPermissionsChangeListeners;
        if (onPermissionsChangeListeners != null) {
            onPermissionsChangeListeners.listeners.register(iOnPermissionsChangeListener);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("onPermissionsChangeListeners");
            throw null;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean addPermission(PermissionInfo permissionInfo, boolean z) {
        String str = permissionInfo.name;
        if (str == null) {
            throw new IllegalArgumentException("permissionName cannot be null".toString());
        }
        int callingUid = Binder.getCallingUid();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            boolean isUidInstantApp = isUidInstantApp(callingUid);
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            if (isUidInstantApp) {
                throw new SecurityException("Instant apps cannot add permissions");
            }
            if (permissionInfo.labelRes == 0 && permissionInfo.nonLocalizedLabel == null) {
                throw new SecurityException("Label must be specified in permission");
            }
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                try {
                    AccessState accessState = accessCheckingService.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    MutableAccessState mutable = accessState.toMutable();
                    MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                    Permission access$getAndEnforcePermissionTree =
                            access$getAndEnforcePermissionTree(this, mutateStateScope, str);
                    access$enforcePermissionTreeSize(
                            this,
                            mutateStateScope,
                            permissionInfo,
                            access$getAndEnforcePermissionTree);
                    this.policy.getClass();
                    Permission permission =
                            (Permission) mutable.getSystemState().getPermissions().map.get(str);
                    ref$ObjectRef.element = permission;
                    int i = 2;
                    if (permission != null && permission.type != 2) {
                        throw new SecurityException(
                                "Not allowed to modify non-dynamic permission ".concat(str));
                    }
                    permissionInfo.packageName =
                            access$getAndEnforcePermissionTree.permissionInfo.packageName;
                    permissionInfo.protectionLevel =
                            PermissionInfo.fixProtectionLevel(permissionInfo.protectionLevel);
                    Permission permission2 =
                            new Permission(
                                    permissionInfo,
                                    true,
                                    2,
                                    access$getAndEnforcePermissionTree.appId);
                    boolean z2 = !z;
                    this.policy.getClass();
                    if (!z2) {
                        i = 1;
                    }
                    MutableSystemState mutableSystemState =
                            (MutableSystemState) mutable.systemStateReference.mutate();
                    mutableSystemState.writeMode = Math.max(mutableSystemState.writeMode, i);
                    mutableSystemState.mutatePermissions().put(permissionInfo.name, permission2);
                    accessCheckingService.persistence.write(mutable);
                    accessCheckingService.state = mutable;
                    IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                    int size = indexedMap.map.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        Object keyAt = indexedMap.map.keyAt(i2);
                        IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
                        int size2 = indexedMap2.map.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            ((SchemePolicy) indexedMap2.map.valueAt(i3)).onStateMutated();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return ref$ObjectRef.element == null;
        } catch (Throwable th2) {
            try {
                throw th2;
            } catch (Throwable th3) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, th2);
                throw th3;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean applyRuntimePermissionsForAllApplicationsForMDM(int i, int i2) {
        if (Binder.getCallingPid() == Process.myPid()) {
            UserManagerInternal userManagerInternal = this.userManagerInternal;
            if (userManagerInternal == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
                throw null;
            }
            if (userManagerInternal.exists(i2)) {
                return applyRuntimePermissionsInternalForMDM(i, null, i2, null);
            }
        }
        throw new SecurityException();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean applyRuntimePermissionsForMDM(String str, List list, int i, int i2) {
        if (Binder.getCallingPid() == Process.myPid()) {
            UserManagerInternal userManagerInternal = this.userManagerInternal;
            if (userManagerInternal == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
                throw null;
            }
            if (userManagerInternal.exists(i2) && str != null) {
                return applyRuntimePermissionsInternalForMDM(i, str, i2, list);
            }
        }
        throw new SecurityException();
    }

    public final boolean applyRuntimePermissionsInternalForMDM(
            int i, String str, int i2, List list) {
        List list2 = PersonaManagerService.containerCriticalApps;
        if (Binder.getCallingPid() != 1000
                && str != null
                && list2 != null
                && ((ArrayList) list2).contains(str)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "Package : ", str, " is container critical application", "PermissionService");
            return false;
        }
        if (i == 0) {
            return updatePermissionStatesAndFlagsInternalForMDM(str, i, 0, i2, list);
        }
        if (i == 1 || i == 2) {
            return updatePermissionStatesAndFlagsInternalForMDM(str, i, 4, i2, list);
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "Invalid Permission State ", "PermissionService");
        return true;
    }

    public final byte[] backupRuntimePermissions(int i) {
        Preconditions.checkArgumentNonnegative(i, "userId cannot be null");
        CompletableFuture completableFuture = new CompletableFuture();
        PermissionControllerManager permissionControllerManager = this.permissionControllerManager;
        if (permissionControllerManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("permissionControllerManager");
            throw null;
        }
        permissionControllerManager.getRuntimePermissionBackup(
                UserHandle.of(i),
                PermissionThread.getExecutor(),
                new PermissionService$onSystemReady$2(1, completableFuture));
        try {
            return (byte[]) completableFuture.get(BACKUP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            if (!(e instanceof TimeoutException
                    ? true
                    : e instanceof InterruptedException ? true : e instanceof ExecutionException)) {
                throw e;
            }
            Slog.e("PermissionService", "Cannot create permission backup for user " + i, e);
            return null;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int checkPermission(String str, String str2, String str3, int i) {
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(i)) {
            return -1;
        }
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot(Binder.getCallingUid(), UserHandle.of(i));
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null) {
                return -1;
            }
            AccessState accessState = this.service.state;
            if (accessState != null) {
                return isPermissionGranted(
                                new GetStateScope(accessState), packageState, i, str2, str3)
                        ? 0
                        : -1;
            }
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(withFilteredSnapshot, th);
                throw th2;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int checkUidPermission(int i, String str, String str2) {
        String str3;
        int userId = UserHandle.getUserId(i);
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(userId)) {
            return -1;
        }
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        AndroidPackage androidPackage = packageManagerInternal.getPackage(i);
        if (androidPackage == null) {
            SystemConfig systemConfig = this.systemConfig;
            if (systemConfig == null) {
                Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
                throw null;
            }
            ArraySet arraySet = (ArraySet) systemConfig.mSystemPermissions.get(i);
            if (arraySet == null) {
                return -1;
            }
            return (!arraySet.contains(str)
                            && ((str3 = (String) FULLER_PERMISSIONS.get(str)) == null
                                    || !arraySet.contains(str3)))
                    ? -1
                    : 0;
        }
        PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
        if (packageManagerInternal2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        PackageStateInternal packageStateInternal =
                packageManagerInternal2.getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal == null) {
            Slog.e(
                    "PermissionService",
                    "checkUidPermission: PackageState not found for AndroidPackage "
                            + androidPackage);
            return -1;
        }
        AccessState accessState = this.service.state;
        if (accessState != null) {
            return isPermissionGranted(
                            new GetStateScope(accessState), packageStateInternal, userId, str, str2)
                    ? 0
                    : -1;
        }
        Intrinsics.throwUninitializedPropertyAccessException(
                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        throw null;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.context, "PermissionService", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            int i = 2;
            if (strArr != null && strArr.length != 0 && !Intrinsics.areEqual(strArr[0], "-a")) {
                if (Intrinsics.areEqual(strArr[0], "--app-id") && strArr.length == 2) {
                    int parseInt = Integer.parseInt(strArr[1]);
                    AccessState accessState = this.service.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    MutableIndexedMap allAppIdPackageNames = getAllAppIdPackageNames(accessState);
                    if (allAppIdPackageNames.map.containsKey(Integer.valueOf(parseInt))) {
                        dumpAppIdState(
                                indentingPrintWriter,
                                parseInt,
                                accessState,
                                (MutableIndexedSet)
                                        allAppIdPackageNames.map.get(Integer.valueOf(parseInt)));
                        return;
                    }
                    indentingPrintWriter.println("Unknown app ID " + parseInt + ".");
                    return;
                }
                if (!Intrinsics.areEqual(strArr[0], "--package") || strArr.length != 2) {
                    indentingPrintWriter.println(
                            "Usage: dumpsys permissionmgr [--app-id <APP_ID>] [--package"
                                + " <PACKAGE_NAME>]");
                    return;
                }
                String str = strArr[1];
                AccessState accessState2 = this.service.state;
                if (accessState2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                PackageState packageState =
                        (PackageState) accessState2.getExternalState().packageStates.get(str);
                if (packageState != null) {
                    dumpAppIdState(
                            indentingPrintWriter,
                            packageState.getAppId(),
                            accessState2,
                            IndexedSetExtensionsKt.indexedSetOf(str));
                    return;
                }
                indentingPrintWriter.println("Unknown package " + str + ".");
                return;
            }
            AccessState accessState3 = this.service.state;
            if (accessState3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            indentingPrintWriter.println("Permissions:");
            indentingPrintWriter.increaseIndent();
            IndexedMap permissions = accessState3.getSystemState().getPermissions();
            int size = permissions.map.size();
            int i2 = 0;
            while (i2 < size) {
                Object keyAt = permissions.map.keyAt(i2);
                Permission permission = (Permission) permissions.map.valueAt(i2);
                String protectionToString =
                        PermissionInfo.protectionToString(
                                permission.permissionInfo.protectionLevel);
                String str2 = permission.permissionInfo.name;
                int i3 = permission.type;
                String valueOf =
                        i3 != 0 ? i3 != i ? String.valueOf(i3) : "TYPE_DYNAMIC" : "TYPE_MANIFEST";
                String str3 = permission.permissionInfo.packageName;
                String arrays = Arrays.toString(permission.gids);
                Intrinsics.checkNotNullExpressionValue("toString(...)", arrays);
                String flagsToString =
                        PermissionInfo.flagsToString(permission.permissionInfo.flags);
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(": type=");
                sb.append(valueOf);
                sb.append(", packageName=");
                sb.append(str3);
                sb.append(", appId=");
                AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(
                        permission.appId, ", gids=", arrays, ", protectionLevel=[", sb);
                sb.append(protectionToString);
                sb.append("], flags=");
                sb.append(flagsToString);
                indentingPrintWriter.println(sb.toString());
                i2++;
                i = 2;
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Permission groups:");
            indentingPrintWriter.increaseIndent();
            IndexedMap permissionGroups = accessState3.getSystemState().getPermissionGroups();
            int size2 = permissionGroups.map.size();
            for (int i4 = 0; i4 < size2; i4++) {
                Object keyAt2 = permissionGroups.map.keyAt(i4);
                PermissionGroupInfo permissionGroupInfo =
                        (PermissionGroupInfo) permissionGroups.map.valueAt(i4);
                indentingPrintWriter.println(
                        permissionGroupInfo.name
                                + ": packageName="
                                + permissionGroupInfo.packageName);
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Permission trees:");
            indentingPrintWriter.increaseIndent();
            IndexedMap permissionTrees = accessState3.getSystemState().getPermissionTrees();
            int size3 = permissionTrees.map.size();
            for (int i5 = 0; i5 < size3; i5++) {
                Object keyAt3 = permissionTrees.map.keyAt(i5);
                Permission permission2 = (Permission) permissionTrees.map.valueAt(i5);
                PermissionInfo permissionInfo = permission2.permissionInfo;
                indentingPrintWriter.println(
                        permissionInfo.name
                                + ": packageName="
                                + permissionInfo.packageName
                                + ", appId="
                                + permission2.appId);
            }
            indentingPrintWriter.decreaseIndent();
            MutableIndexedMap allAppIdPackageNames2 = getAllAppIdPackageNames(accessState3);
            int size4 = allAppIdPackageNames2.map.size();
            for (int i6 = 0; i6 < size4; i6++) {
                Object keyAt4 = allAppIdPackageNames2.map.keyAt(i6);
                MutableIndexedSet mutableIndexedSet =
                        (MutableIndexedSet) allAppIdPackageNames2.map.valueAt(i6);
                int intValue = ((Number) keyAt4).intValue();
                if (intValue != -1) {
                    dumpAppIdState(indentingPrintWriter, intValue, accessState3, mutableIndexedSet);
                }
            }
        }
    }

    public final void enforceCallingOrSelfAnyPermission(String str, String... strArr) {
        for (String str2 : strArr) {
            if (this.context.checkCallingOrSelfPermission(str2) == 0) {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append("Neither user ");
        sb.append(Binder.getCallingUid());
        sb.append(" nor current process has any of ");
        sb.append((CharSequence) "");
        int i = 0;
        for (String str3 : strArr) {
            i++;
            if (i > 1) {
                sb.append((CharSequence) ", ");
            }
            StringsKt__AppendableKt.appendElement(sb, str3, null);
        }
        sb.append((CharSequence) "");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue("toString(...)", sb2);
        throw new SecurityException(sb2);
    }

    public final void enforceCallingOrSelfCrossUserPermission(
            String str, boolean z, boolean z2, int i) {
        if (i < 0) {
            throw new IllegalArgumentException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    i, "userId ", " is invalid")
                            .toString());
        }
        int callingUid = Binder.getCallingUid();
        if (i != UserHandle.getUserId(callingUid)) {
            String str2 =
                    z
                            ? "android.permission.INTERACT_ACROSS_USERS_FULL"
                            : "android.permission.INTERACT_ACROSS_USERS";
            if (this.context.checkCallingOrSelfPermission(str2) != 0) {
                String str3 =
                        str
                                + ": Neither user "
                                + callingUid
                                + " nor current process has "
                                + str2
                                + " to access user "
                                + i;
                Intrinsics.checkNotNullExpressionValue("toString(...)", str3);
                throw new SecurityException(str3);
            }
        }
        if (z2 && UserHandle.getAppId(callingUid) == 2000) {
            UserManagerInternal userManagerInternal = this.userManagerInternal;
            if (userManagerInternal == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
                throw null;
            }
            if (userManagerInternal.hasUserRestriction("no_debugging_features", i)) {
                String str4 = str + ": Shell is disallowed to access user " + i;
                Intrinsics.checkNotNullExpressionValue("toString(...)", str4);
                throw new SecurityException(str4);
            }
        }
    }

    public final boolean enforceRestrictedPermission(String str) {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        Permission permission =
                (Permission) accessState.getSystemState().getPermissions().map.get(str);
        if (permission == null) {
            PinnerService$$ExternalSyntheticOutline0.m(
                    "permission definition for ", str, " does not exist", "PermissionService");
            return false;
        }
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot();
        try {
            PackageState packageState =
                    withFilteredSnapshot.getPackageState(permission.permissionInfo.packageName);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null) {
                return false;
            }
            if ((IntExtensionsKt.hasBits(permission.permissionInfo.flags, 4)
                            || IntExtensionsKt.hasBits(permission.permissionInfo.flags, 8))
                    && IntExtensionsKt.hasBits(permission.permissionInfo.flags, 16)
                    && this.context.checkCallingOrSelfPermission(
                                    "android.permission.WHITELIST_RESTRICTED_PERMISSIONS")
                            != 0) {
                throw new SecurityException(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Cannot modify allowlist of an immutably restricted permission: ",
                                permission.permissionInfo.name));
            }
            return true;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(withFilteredSnapshot, th);
                throw th2;
            }
        }
    }

    public final MutableIndexedMap getAllAppIdPackageNames(AccessState accessState) {
        ArraySet arraySet = new ArraySet();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            Map packageStates = withUnfilteredSnapshot.getPackageStates();
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            MutableIntReferenceMap userStates = accessState.getUserStates();
            int size = userStates.array.size();
            for (int i = 0; i < size; i++) {
                userStates.keyAt(i);
                MutableUserState mutableUserState = (MutableUserState) userStates.valueAt(i);
                MutableIntReferenceMap appIdPermissionFlags =
                        mutableUserState.getAppIdPermissionFlags();
                int size2 = appIdPermissionFlags.array.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    int keyAt = appIdPermissionFlags.array.keyAt(i2);
                    arraySet.add(Integer.valueOf(keyAt));
                }
                MutableIntReferenceMap appIdAppOpModes = mutableUserState.getAppIdAppOpModes();
                int size3 = appIdAppOpModes.array.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    int keyAt2 = appIdAppOpModes.array.keyAt(i3);
                    arraySet.add(Integer.valueOf(keyAt2));
                }
                IndexedMap indexedMap =
                        (IndexedMap) mutableUserState.packageVersionsReference.immutable;
                int size4 = indexedMap.map.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    Object keyAt3 = indexedMap.map.keyAt(i4);
                    ((Number) indexedMap.map.valueAt(i4)).intValue();
                    PackageState packageState = (PackageState) packageStates.get((String) keyAt3);
                    if (packageState != null) {
                        arraySet.add(Integer.valueOf(packageState.getAppId()));
                    }
                }
                MutableIndexedReferenceMap packageAppOpModes =
                        mutableUserState.getPackageAppOpModes();
                int size5 = packageAppOpModes.map.size();
                for (int i5 = 0; i5 < size5; i5++) {
                    Object keyAt4 = packageAppOpModes.map.keyAt(i5);
                    PackageState packageState2 = (PackageState) packageStates.get((String) keyAt4);
                    if (packageState2 != null) {
                        arraySet.add(Integer.valueOf(packageState2.getAppId()));
                    }
                }
            }
            MutableIndexedMap mutableIndexedMap = new MutableIndexedMap();
            Iterator it = packageStates.entrySet().iterator();
            while (it.hasNext()) {
                PackageState packageState3 = (PackageState) ((Map.Entry) it.next()).getValue();
                if (!packageState3.isApex()) {
                    Integer valueOf = Integer.valueOf(packageState3.getAppId());
                    Object obj = mutableIndexedMap.map.get(valueOf);
                    if (obj == null) {
                        obj = new MutableIndexedSet();
                        mutableIndexedMap.put(valueOf, obj);
                    }
                    ((MutableIndexedSet) obj).add(packageState3.getPackageName());
                }
            }
            int size6 = arraySet.size();
            for (int i6 = 0; i6 < size6; i6++) {
                Integer valueOf2 =
                        Integer.valueOf(
                                ((Number) CollectionsKt.elementAt(arraySet, i6)).intValue());
                if (mutableIndexedMap.map.get(valueOf2) == null) {
                    mutableIndexedMap.put(valueOf2, new MutableIndexedSet());
                }
            }
            return mutableIndexedMap;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Map getAllAppOpPermissionPackages() {
        AndroidPackage androidPackage;
        ArrayMap arrayMap = new ArrayMap();
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        IndexedMap permissions = accessState.getSystemState().getPermissions();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            Iterator it = withUnfilteredSnapshot.getPackageStates().entrySet().iterator();
            while (it.hasNext()) {
                PackageState packageState = (PackageState) ((Map.Entry) it.next()).getValue();
                if (!packageState.isApex()
                        && (androidPackage = packageState.getAndroidPackage()) != null) {
                    for (String str : androidPackage.getRequestedPermissions()) {
                        Permission permission = (Permission) permissions.map.get(str);
                        if (permission != null
                                && IntExtensionsKt.hasBits(
                                        permission.permissionInfo.getProtectionFlags(), 64)) {
                            Object obj = arrayMap.get(str);
                            if (obj == null) {
                                obj = new ArraySet();
                                arrayMap.put(str, obj);
                            }
                            ((ArraySet) obj).add(androidPackage.getPackageName());
                        }
                    }
                }
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return arrayMap;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllPermissionGroups(int i) {
        PermissionGroupInfo permissionGroupInfo;
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = Binder.getCallingUid();
            if (isUidInstantApp(callingUid)) {
                EmptyList emptyList = EmptyList.INSTANCE;
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return emptyList;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            this.policy.getClass();
            IndexedMap permissionGroups = accessState.getSystemState().getPermissionGroups();
            ArrayList arrayList = new ArrayList();
            int size = permissionGroups.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object keyAt = permissionGroups.map.keyAt(i2);
                PermissionGroupInfo permissionGroupInfo2 =
                        (PermissionGroupInfo) permissionGroups.map.valueAt(i2);
                if (isPackageVisibleToUid(
                        withUnfilteredSnapshot, permissionGroupInfo2.packageName, callingUid)) {
                    permissionGroupInfo = new PermissionGroupInfo(permissionGroupInfo2);
                    if (!IntExtensionsKt.hasBits(i, 128)) {
                        permissionGroupInfo.metaData = null;
                    }
                } else {
                    permissionGroupInfo = null;
                }
                if (permissionGroupInfo != null) {
                    arrayList.add(permissionGroupInfo);
                }
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Map getAllPermissionStates(String str, String str2, int i) {
        MutableIntReferenceMap appIdDevicePermissionFlags;
        MutableIndexedReferenceMap mutableIndexedReferenceMap;
        MutableIntReferenceMap appIdPermissionFlags;
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        IndexedMap indexedMap = null;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(i)) {
            Slog.w("PermissionService", "getAllPermissionStates: Unknown user " + i);
            return MapsKt__MapsKt.emptyMap();
        }
        enforceCallingOrSelfCrossUserPermission("getAllPermissionStates", true, false, i);
        enforceCallingOrSelfAnyPermission(
                "getAllPermissionStates",
                "android.permission.GRANT_RUNTIME_PERMISSIONS",
                "android.permission.REVOKE_RUNTIME_PERMISSIONS",
                "android.permission.GET_RUNTIME_PERMISSIONS");
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot();
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null) {
                Slog.w("PermissionService", "getAllPermissionStates: Unknown package " + str);
                return MapsKt__MapsKt.emptyMap();
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            GetStateScope getStateScope = new GetStateScope(accessState);
            if (Intrinsics.areEqual(str2, "default:0")) {
                AppIdPermissionPolicy appIdPermissionPolicy = this.policy;
                int appId = packageState.getAppId();
                appIdPermissionPolicy.getClass();
                MutableUserState mutableUserState =
                        (MutableUserState) accessState.getUserStates().get(i);
                if (mutableUserState != null
                        && (appIdPermissionFlags = mutableUserState.getAppIdPermissionFlags())
                                != null) {
                    indexedMap = (IndexedMap) appIdPermissionFlags.get(appId);
                }
            } else {
                DevicePermissionPolicy devicePermissionPolicy = this.devicePolicy;
                int appId2 = packageState.getAppId();
                devicePermissionPolicy.getClass();
                MutableUserState mutableUserState2 =
                        (MutableUserState) accessState.getUserStates().get(i);
                if (mutableUserState2 != null
                        && (appIdDevicePermissionFlags =
                                        mutableUserState2.getAppIdDevicePermissionFlags())
                                != null
                        && (mutableIndexedReferenceMap =
                                        (MutableIndexedReferenceMap)
                                                appIdDevicePermissionFlags.get(appId2))
                                != null) {
                    indexedMap = (IndexedMap) mutableIndexedReferenceMap.get(str2);
                }
            }
            IndexedMap indexedMap2 = indexedMap;
            if (indexedMap2 == null) {
                return MapsKt__MapsKt.emptyMap();
            }
            ArrayMap arrayMap = new ArrayMap();
            int size = indexedMap2.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str3 = (String) indexedMap2.map.keyAt(i2);
                arrayMap.put(
                        str3,
                        new PermissionManager.PermissionState(
                                isPermissionGranted(getStateScope, packageState, i, str3, str2),
                                PermissionFlags.toApiFlags(
                                        ((Number) indexedMap2.map.valueAt(i2)).intValue())));
            }
            return arrayMap;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllPermissionsWithProtection() {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        IndexedMap permissions = accessState.getSystemState().getPermissions();
        ArrayList arrayList = new ArrayList();
        int size = permissions.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = permissions.map.keyAt(i);
            Permission permission = (Permission) permissions.map.valueAt(i);
            PermissionInfo generatePermissionInfo =
                    permission.permissionInfo.getProtection() == 1
                            ? generatePermissionInfo(permission, 0, 10000)
                            : null;
            if (generatePermissionInfo != null) {
                arrayList.add(generatePermissionInfo);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getAllPermissionsWithProtectionFlags() {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        IndexedMap permissions = accessState.getSystemState().getPermissions();
        ArrayList arrayList = new ArrayList();
        int size = permissions.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = permissions.map.keyAt(i);
            Permission permission = (Permission) permissions.map.valueAt(i);
            PermissionInfo generatePermissionInfo =
                    IntExtensionsKt.hasBits(permission.permissionInfo.getProtectionFlags(), 64)
                            ? generatePermissionInfo(permission, 0, 10000)
                            : null;
            if (generatePermissionInfo != null) {
                arrayList.add(generatePermissionInfo);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final ArrayList getAllowlistedRestrictedPermissions(int i, int i2, String str) {
        AndroidPackage androidPackage;
        if (str == null) {
            throw new IllegalArgumentException("packageName cannot be null".toString());
        }
        Preconditions.checkFlagsArgument(i, 7);
        Preconditions.checkArgumentNonnegative(i2, "userId cannot be null");
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(i2)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "AllowlistedRestrictedPermission api: Unknown user ", "PermissionService");
            return null;
        }
        enforceCallingOrSelfCrossUserPermission(
                "getAllowlistedRestrictedPermissions", false, false, i2);
        int callingUid = Binder.getCallingUid();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot(callingUid, UserHandle.of(i2));
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null
                    || (androidPackage = packageState.getAndroidPackage()) == null) {
                return null;
            }
            boolean z =
                    this.context.checkCallingOrSelfPermission(
                                    "android.permission.WHITELIST_RESTRICTED_PERMISSIONS")
                            == 0;
            if (IntExtensionsKt.hasBits(i, 1) && !z) {
                throw new SecurityException(
                        "Querying system allowlist requires"
                            + " android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
            }
            PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
            if (packageManagerInternal == null) {
                Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                throw null;
            }
            boolean isCallerInstallerOfRecord =
                    ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal)
                            .mService
                            .snapshotComputer()
                            .isCallerInstallerOfRecord(androidPackage, callingUid);
            if (!IntExtensionsKt.hasAnyBit(i, 6) || z || isCallerInstallerOfRecord) {
                return getAllowlistedRestrictedPermissionsUnchecked(packageState.getAppId(), i, i2);
            }
            throw new SecurityException(
                    "Querying upgrade or installer allowlist requires being installer on record or"
                        + " android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        } finally {
        }
    }

    public final ArrayList getAllowlistedRestrictedPermissionsUnchecked(int i, int i2, int i3) {
        MutableIntReferenceMap appIdPermissionFlags;
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        MutableUserState mutableUserState = (MutableUserState) accessState.getUserStates().get(i3);
        IndexedMap indexedMap =
                (mutableUserState == null
                                || (appIdPermissionFlags =
                                                mutableUserState.getAppIdPermissionFlags())
                                        == null)
                        ? null
                        : (IndexedMap) appIdPermissionFlags.get(i);
        if (indexedMap == null) {
            return null;
        }
        int i4 =
                IntExtensionsKt.hasBits(i2, 1)
                        ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                        : 0;
        if (IntExtensionsKt.hasBits(i2, 4)) {
            i4 |= 131072;
        }
        if (IntExtensionsKt.hasBits(i2, 2)) {
            i4 |= 32768;
        }
        ArrayList arrayList = new ArrayList();
        int size = indexedMap.map.size();
        for (int i5 = 0; i5 < size; i5++) {
            String str = (String) indexedMap.map.keyAt(i5);
            if (!IntExtensionsKt.hasAnyBit(((Number) indexedMap.map.valueAt(i5)).intValue(), i4)) {
                str = null;
            }
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final String[] getAppOpPermissionPackages(String str) {
        AndroidPackage androidPackage;
        if (str == null) {
            throw new IllegalArgumentException("permissionName cannot be null".toString());
        }
        ArraySet arraySet = new ArraySet();
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        Permission permission =
                (Permission) accessState.getSystemState().getPermissions().map.get(str);
        if (permission == null
                || !IntExtensionsKt.hasBits(permission.permissionInfo.getProtectionFlags(), 64)) {
            arraySet.toArray(new String[0]);
        }
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            Iterator it = withUnfilteredSnapshot.getPackageStates().entrySet().iterator();
            while (it.hasNext()) {
                PackageState packageState = (PackageState) ((Map.Entry) it.next()).getValue();
                if (!packageState.isApex()
                        && (androidPackage = packageState.getAndroidPackage()) != null
                        && androidPackage.getRequestedPermissions().contains(str)) {
                    arraySet.add(androidPackage.getPackageName());
                }
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return (String[]) arraySet.toArray(new String[0]);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final String getDefaultPermissionGrantFingerprint(int i) {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        Immutable immutable = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        return ((MutableUserState) immutable).defaultPermissionGrantFingerprint;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int[] getGidsForUid(int i) {
        MutableIntReferenceMap appIdPermissionFlags;
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        SystemConfig systemConfig = this.systemConfig;
        IndexedMap indexedMap = null;
        if (systemConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            throw null;
        }
        int[] iArr = systemConfig.mGlobalGids;
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        MutableUserState mutableUserState =
                (MutableUserState) accessState.getUserStates().get(userId);
        if (mutableUserState != null
                && (appIdPermissionFlags = mutableUserState.getAppIdPermissionFlags()) != null) {
            indexedMap = (IndexedMap) appIdPermissionFlags.get(appId);
        }
        if (indexedMap == null) {
            int[] copyOf = Arrays.copyOf(iArr, iArr.length);
            Intrinsics.checkNotNullExpressionValue("copyOf(...)", copyOf);
            return copyOf;
        }
        IntArray wrap = IntArray.wrap(iArr);
        int size = indexedMap.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = indexedMap.map.keyAt(i2);
            int intValue = ((Number) indexedMap.map.valueAt(i2)).intValue();
            String str = (String) keyAt;
            boolean z = true;
            if (!IntExtensionsKt.hasBits(intValue, 1)) {
                if (!IntExtensionsKt.hasBits(intValue, 2)) {
                    if (!IntExtensionsKt.hasBits(intValue, 4)
                            && !IntExtensionsKt.hasBits(intValue, 1024)
                            && !IntExtensionsKt.hasBits(intValue, 2048)) {
                        if (!IntExtensionsKt.hasBits(intValue, 262144)) {
                            z = IntExtensionsKt.hasBits(intValue, 16);
                        }
                    }
                }
                z = false;
            }
            if (z) {
                this.policy.getClass();
                Permission permission =
                        (Permission) accessState.getSystemState().getPermissions().map.get(str);
                if (permission != null) {
                    int[] gidsForUser = permission.getGidsForUser(userId);
                    if (gidsForUser.length != 0) {
                        wrap.addAll(gidsForUser);
                    }
                }
            }
        }
        return wrap.toArray();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Set getGrantedPermissions(int i, String str) {
        MutableIntReferenceMap appIdPermissionFlags;
        if (str == null) {
            throw new IllegalArgumentException("packageName cannot be null".toString());
        }
        Preconditions.checkArgumentNonnegative(i, "userId");
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            PackageState packageState =
                    (PackageState) withUnfilteredSnapshot.getPackageStates().get(str);
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            if (packageState == null) {
                Slog.w("PermissionService", "getGrantedPermissions: Unknown package ".concat(str));
                return EmptySet.INSTANCE;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            GetStateScope getStateScope = new GetStateScope(accessState);
            AppIdPermissionPolicy appIdPermissionPolicy = this.policy;
            int appId = packageState.getAppId();
            appIdPermissionPolicy.getClass();
            MutableUserState mutableUserState =
                    (MutableUserState) accessState.getUserStates().get(i);
            IndexedMap indexedMap =
                    (mutableUserState == null
                                    || (appIdPermissionFlags =
                                                    mutableUserState.getAppIdPermissionFlags())
                                            == null)
                            ? null
                            : (IndexedMap) appIdPermissionFlags.get(appId);
            if (indexedMap == null) {
                return EmptySet.INSTANCE;
            }
            ArraySet arraySet = new ArraySet();
            int size = indexedMap.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object keyAt = indexedMap.map.keyAt(i2);
                ((Number) indexedMap.map.valueAt(i2)).intValue();
                String str2 = (String) keyAt;
                if (!isPermissionGranted(getStateScope, packageState, i, str2, "default:0")) {
                    str2 = null;
                }
                if (str2 != null) {
                    arraySet.add(str2);
                }
            }
            return arraySet;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final Set getInstalledPermissions(String str) {
        if (str == null) {
            throw new IllegalArgumentException("packageName cannot be null".toString());
        }
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        IndexedMap permissions = accessState.getSystemState().getPermissions();
        ArraySet arraySet = new ArraySet();
        int size = permissions.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = permissions.map.keyAt(i);
            Permission permission = (Permission) permissions.map.valueAt(i);
            String str2 =
                    Intrinsics.areEqual(permission.permissionInfo.packageName, str)
                            ? permission.permissionInfo.name
                            : null;
            if (str2 != null) {
                arraySet.add(str2);
            }
        }
        return arraySet;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final LegacyPermissionState getLegacyPermissionState(int i) {
        MutableIntReferenceMap appIdPermissionFlags;
        PermissionService permissionService = this;
        LegacyPermissionState legacyPermissionState = new LegacyPermissionState();
        UserManagerService userManagerService = permissionService.userManagerService;
        if (userManagerService == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
            throw null;
        }
        int[] userIdsIncludingPreCreated = userManagerService.getUserIdsIncludingPreCreated();
        AccessState accessState = permissionService.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        permissionService.policy.getClass();
        IndexedMap permissions = accessState.getSystemState().getPermissions();
        int length = userIdsIncludingPreCreated.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = userIdsIncludingPreCreated[i2];
            permissionService.policy.getClass();
            MutableUserState mutableUserState =
                    (MutableUserState) accessState.getUserStates().get(i3);
            IndexedMap indexedMap =
                    (mutableUserState == null
                                    || (appIdPermissionFlags =
                                                    mutableUserState.getAppIdPermissionFlags())
                                            == null)
                            ? null
                            : (IndexedMap) appIdPermissionFlags.get(i);
            if (indexedMap != null) {
                int size = indexedMap.map.size();
                for (int i4 = 0; i4 < size; i4++) {
                    Object keyAt = indexedMap.map.keyAt(i4);
                    int intValue = ((Number) indexedMap.map.valueAt(i4)).intValue();
                    String str = (String) keyAt;
                    Permission permission = (Permission) permissions.map.get(str);
                    if (permission != null) {
                        legacyPermissionState.putPermissionState(
                                new LegacyPermissionState.PermissionState(
                                        str,
                                        permission.permissionInfo.getProtection() == 1,
                                        PermissionFlags.isPermissionGranted(intValue),
                                        PermissionFlags.toApiFlags(intValue)),
                                i3);
                    }
                }
            }
            i2++;
            permissionService = this;
        }
        return legacyPermissionState;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getLegacyPermissions() {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        IndexedMap permissions = accessState.getSystemState().getPermissions();
        ArrayList arrayList = new ArrayList();
        int size = permissions.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = permissions.map.keyAt(i);
            Permission permission = (Permission) permissions.map.valueAt(i);
            arrayList.add(
                    new LegacyPermission(
                            permission.permissionInfo,
                            permission.type,
                            permission.appId,
                            permission.gids));
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getPackageGrantedPermissionsForMDM(String str) {
        ArrayList arrayList = (ArrayList) this.mPackageGrantedPermissions.get(str);
        if (arrayList == null) {
            return null;
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int getPermissionFlags(String str, String str2, String str3, int i) {
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "getPermissionFlags: Unknown user ", "PermissionService");
            return 0;
        }
        enforceCallingOrSelfCrossUserPermission("getPermissionFlags", true, false, i);
        enforceCallingOrSelfAnyPermission(
                "getPermissionFlags",
                "android.permission.GRANT_RUNTIME_PERMISSIONS",
                "android.permission.REVOKE_RUNTIME_PERMISSIONS",
                "android.permission.GET_RUNTIME_PERMISSIONS");
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot();
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "getPermissionFlags: Unknown package ", str, "PermissionService");
                return 0;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            GetStateScope getStateScope = new GetStateScope(accessState);
            this.policy.getClass();
            if (((Permission) accessState.getSystemState().getPermissions().map.get(str2))
                    != null) {
                return PermissionFlags.toApiFlags(
                        getPermissionFlagsWithPolicy(
                                packageState.getAppId(), i, getStateScope, str2, str3));
            }
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "getPermissionFlags: Unknown permission ", str2, "PermissionService");
            return 0;
        } finally {
        }
    }

    public final int getPermissionFlagsWithPolicy(
            int i, int i2, GetStateScope getStateScope, String str, String str2) {
        boolean deviceAwarePermissionApisEnabled = Flags.deviceAwarePermissionApisEnabled();
        AccessState accessState = getStateScope.state;
        if (!deviceAwarePermissionApisEnabled || Intrinsics.areEqual(str2, "default:0")) {
            this.policy.getClass();
            return AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str);
        }
        if (PermissionManager.DEVICE_AWARE_PERMISSIONS.contains(str)) {
            this.devicePolicy.getClass();
            return DevicePermissionPolicy.getPermissionFlags(i, i2, getStateScope, str2, str);
        }
        Slog.i(
                "PermissionService",
                str + " is not device aware permission,  get the flags for default device.");
        this.policy.getClass();
        return AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final int[] getPermissionGids(int i, String str) {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        Permission permission =
                (Permission) accessState.getSystemState().getPermissions().map.get(str);
        return permission == null ? EmptyArray.INT : permission.getGidsForUser(i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final PermissionGroupInfo getPermissionGroupInfo(String str, int i) {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = Binder.getCallingUid();
            if (isUidInstantApp(callingUid)) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            this.policy.getClass();
            PermissionGroupInfo permissionGroupInfo =
                    (PermissionGroupInfo)
                            accessState.getSystemState().getPermissionGroups().map.get(str);
            if (permissionGroupInfo == null) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            ref$ObjectRef.element = permissionGroupInfo;
            if (!isPackageVisibleToUid(
                    withUnfilteredSnapshot, permissionGroupInfo.packageName, callingUid)) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            PermissionGroupInfo permissionGroupInfo2 =
                    new PermissionGroupInfo((PermissionGroupInfo) ref$ObjectRef.element);
            if (!IntExtensionsKt.hasBits(i, 128)) {
                permissionGroupInfo2.metaData = null;
            }
            return permissionGroupInfo2;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final PermissionInfo getPermissionInfo(int i, String str, String str2) {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = Binder.getCallingUid();
            if (isUidInstantApp(callingUid)) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            this.policy.getClass();
            Permission permission =
                    (Permission) accessState.getSystemState().getPermissions().map.get(str);
            if (permission == null) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            ref$ObjectRef.element = permission;
            if (!isPackageVisibleToUid(
                    withUnfilteredSnapshot, permission.permissionInfo.packageName, callingUid)) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            PackageState packageState =
                    (PackageState) withUnfilteredSnapshot.getPackageStates().get(str2);
            AndroidPackage androidPackage =
                    packageState != null ? packageState.getAndroidPackage() : null;
            int i2 = 10000;
            if (!isRootOrSystemUid(callingUid)
                    && UserHandle.getAppId(callingUid) != 2000
                    && androidPackage != null) {
                i2 = androidPackage.getTargetSdkVersion();
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return generatePermissionInfo((Permission) ref$ObjectRef.element, i, i2);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final com.android.server.pm.permission.Permission getPermissionTEMP(String str) {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        Permission permission =
                (Permission) accessState.getSystemState().getPermissions().map.get(str);
        if (permission == null) {
            return null;
        }
        com.android.server.pm.permission.Permission permission2 =
                new com.android.server.pm.permission.Permission(
                        permission.permissionInfo, permission.type);
        permission2.mReconciled = permission.isReconciled;
        permission2.mUid = permission.appId;
        permission2.mGids = permission.gids;
        permission2.mGidsPerUser = permission.areGidsPerUser;
        return permission2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getRequestedRuntimePermissionsForMDM(String str) {
        if (str == null) {
            return new ArrayList();
        }
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        AndroidPackage androidPackage = packageManagerInternal.getPackage(str);
        Intrinsics.checkNotNull(androidPackage);
        AccessState accessState = this.service.state;
        if (accessState != null) {
            return getRequestedRuntimePermissionsInternal(
                    new GetStateScope(accessState), androidPackage, null);
        }
        Intrinsics.throwUninitializedPropertyAccessException(
                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        throw null;
    }

    public final List getRequestedRuntimePermissionsInternal(
            GetStateScope getStateScope, AndroidPackage androidPackage, List list) {
        if (list == null || list.isEmpty()) {
            list = new ArrayList(androidPackage.getRequestedPermissions());
        }
        ArrayList arrayList = new ArrayList();
        this.policy.getClass();
        IndexedMap permissions = getStateScope.state.getSystemState().getPermissions();
        for (String str : list) {
            Permission permission = (Permission) permissions.map.get(str);
            if (permission != null && permission.permissionInfo.getProtection() == 1) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final PackageStateInternal getSourcePackageSetting(Permission permission) {
        String str = permission.permissionInfo.packageName;
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal != null) {
            return packageManagerInternal.getPackageStateInternal(str);
        }
        Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
        throw null;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List getSplitPermissions() {
        SystemConfig systemConfig = this.systemConfig;
        if (systemConfig != null) {
            return PermissionManager.splitPermissionInfoListToParcelableList(
                    systemConfig.mSplitPermissions);
        }
        Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
        throw null;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void grantRuntimePermission(String str, String str2, String str3, int i) {
        setRuntimePermissionGranted$default(this, str, i, str2, str3, true, false, null, 96);
    }

    public final boolean isPermissionGranted(
            GetStateScope getStateScope,
            PackageState packageState,
            int i,
            String str,
            String str2) {
        int appId = packageState.getAppId();
        boolean isInstantApp = packageState.getUserStateOrDefault(i).isInstantApp();
        if (isSinglePermissionGranted(getStateScope, appId, i, isInstantApp, str, str2)) {
            return true;
        }
        String str3 = (String) FULLER_PERMISSIONS.get(str);
        return str3 != null
                && isSinglePermissionGranted(getStateScope, appId, i, isInstantApp, str3, str2);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean isPermissionRevokedByPolicy(int i, String str, String str2, String str3) {
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "isPermissionRevokedByPolicy: Unknown user ", "PermissionService");
            return false;
        }
        enforceCallingOrSelfCrossUserPermission("isPermissionRevokedByPolicy", true, false, i);
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot(Binder.getCallingUid(), UserHandle.of(i));
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null) {
                return false;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            GetStateScope getStateScope = new GetStateScope(accessState);
            if (isPermissionGranted(getStateScope, packageState, i, str2, str3)) {
                return false;
            }
            return IntExtensionsKt.hasBits(
                    getPermissionFlagsWithPolicy(
                            packageState.getAppId(), i, getStateScope, str2, str3),
                    128);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean isPermissionsReviewRequired(int i, String str) {
        MutableIntReferenceMap appIdPermissionFlags;
        if (str == null) {
            throw new IllegalArgumentException("packageName cannot be null".toString());
        }
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        IndexedMap indexedMap = null;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            PackageState packageState =
                    (PackageState) withUnfilteredSnapshot.getPackageStates().get(str);
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            if (packageState == null) {
                return false;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            AppIdPermissionPolicy appIdPermissionPolicy = this.policy;
            int appId = packageState.getAppId();
            appIdPermissionPolicy.getClass();
            MutableUserState mutableUserState =
                    (MutableUserState) accessState.getUserStates().get(i);
            if (mutableUserState != null
                    && (appIdPermissionFlags = mutableUserState.getAppIdPermissionFlags())
                            != null) {
                indexedMap = (IndexedMap) appIdPermissionFlags.get(appId);
            }
            if (indexedMap == null) {
                return false;
            }
            int size = indexedMap.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (IntExtensionsKt.hasBits(
                        ((Number) indexedMap.map.valueAt(i2)).intValue(), 5120)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, th);
                throw th2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSinglePermissionGranted(
            com.android.server.permission.access.GetStateScope r7,
            int r8,
            int r9,
            boolean r10,
            java.lang.String r11,
            java.lang.String r12) {
        /*
            r6 = this;
            r0 = r6
            r1 = r8
            r2 = r9
            r3 = r7
            r4 = r11
            r5 = r12
            int r8 = r0.getPermissionFlagsWithPolicy(r1, r2, r3, r4, r5)
            r9 = 1
            boolean r12 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r9)
            r0 = 0
            if (r12 == 0) goto L14
        L12:
            r8 = r9
            goto L45
        L14:
            r12 = 2
            boolean r12 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r12)
            if (r12 == 0) goto L1d
        L1b:
            r8 = r0
            goto L45
        L1d:
            r12 = 4
            boolean r12 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r12)
            if (r12 == 0) goto L25
            goto L12
        L25:
            r12 = 1024(0x400, float:1.435E-42)
            boolean r12 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r12)
            if (r12 != 0) goto L12
            r12 = 2048(0x800, float:2.87E-42)
            boolean r12 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r12)
            if (r12 == 0) goto L36
            goto L12
        L36:
            r12 = 262144(0x40000, float:3.67342E-40)
            boolean r12 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r12)
            if (r12 == 0) goto L3f
            goto L1b
        L3f:
            r12 = 16
            boolean r8 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r8, r12)
        L45:
            if (r8 != 0) goto L48
            return r0
        L48:
            if (r10 == 0) goto L73
            com.android.server.permission.access.permission.AppIdPermissionPolicy r6 = r6.policy
            r6.getClass()
            com.android.server.permission.access.AccessState r6 = r7.state
            com.android.server.permission.access.MutableSystemState r6 = r6.getSystemState()
            com.android.server.permission.access.immutable.IndexedMap r6 = r6.getPermissions()
            android.util.ArrayMap r6 = r6.map
            java.lang.Object r6 = r6.get(r11)
            com.android.server.permission.access.permission.Permission r6 = (com.android.server.permission.access.permission.Permission) r6
            if (r6 != 0) goto L64
            return r0
        L64:
            android.content.pm.PermissionInfo r6 = r6.permissionInfo
            int r6 = r6.getProtectionFlags()
            r7 = 4096(0x1000, float:5.74E-42)
            boolean r6 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r6, r7)
            if (r6 != 0) goto L73
            return r0
        L73:
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.PermissionService.isSinglePermissionGranted(com.android.server.permission.access.GetStateScope,"
                    + " int, int, boolean, java.lang.String, java.lang.String):boolean");
    }

    public final boolean isUidInstantApp(int i) {
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal != null) {
            return packageManagerInternal.getInstantAppPackageName(i) != null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
        throw null;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onPackageAdded(
            PackageState packageState, boolean z, AndroidPackage androidPackage) {
        if (packageState.isApex()) {
            return;
        }
        synchronized (this.storageVolumeLock) {
            ArrayMap arrayMap = this.storageVolumePackageNames;
            String volumeUuid = packageState.getVolumeUuid();
            Object obj = arrayMap.get(volumeUuid);
            if (obj == null) {
                obj = new ArrayList();
                arrayMap.put(volumeUuid, obj);
            }
            ((Collection) obj).add(packageState.getPackageName());
            if (this.mountedStorageVolumes.contains(packageState.getVolumeUuid())) {
                this.service
                        .onPackageAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                packageState.getPackageName());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x009a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPackageInstalled(
            com.android.server.pm.pkg.AndroidPackage r31,
            com.android.server.pm.permission.PermissionManagerServiceInternal$PackageInstalledParams
                    r32,
            int r33) {
        /*
            Method dump skipped, instructions count: 706
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.PermissionService.onPackageInstalled(com.android.server.pm.pkg.AndroidPackage,"
                    + " com.android.server.pm.permission.PermissionManagerServiceInternal$PackageInstalledParams,"
                    + " int):void");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onPackageRemoved(AndroidPackage androidPackage) {}

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onPackageUninstalled(
            String str,
            int i,
            PackageState packageState,
            AndroidPackage androidPackage,
            List list,
            int i2) {
        int[] iArr;
        if (packageState.isApex()) {
            return;
        }
        if (i2 == -1) {
            UserManagerService userManagerService = this.userManagerService;
            if (userManagerService == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
                throw null;
            }
            iArr = userManagerService.getUserIdsIncludingPreCreated();
        } else {
            iArr = new int[] {i2};
        }
        for (int i3 : iArr) {
            this.service
                    .onPackageUninstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                            str, i, i3);
        }
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        if (((PackageStateInternal)
                        ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal)
                                .mService
                                .snapshotComputer()
                                .getPackageStates()
                                .get(str))
                == null) {
            this.service
                    .onPackageRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                            str, i);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onStorageVolumeMounted(String str, boolean z) {
        List list;
        synchronized (this.storageVolumeLock) {
            list = (List) this.storageVolumePackageNames.remove(str);
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
            this.mountedStorageVolumes.add(str);
        }
        PackageManager.corkPackageInfoCache();
        try {
            this.service
                    .onStorageVolumeMounted$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                            str, list, z);
            PackageManager.uncorkPackageInfoCache();
            Slog.d("PermissionService", "onStorageVolumeMounted called");
            updateAdminPermissionsInternal(null);
        } catch (Throwable th) {
            PackageManager.uncorkPackageInfoCache();
            throw th;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onSystemReady() {
        Set allPersistentDeviceIds;
        this.service
                .onSystemReady$frameworks__base__services__permission__android_common__services_permission_pre_jarjar();
        VirtualDeviceManagerInternal virtualDeviceManagerInternal =
                (VirtualDeviceManagerInternal)
                        LocalServices.getService(VirtualDeviceManagerInternal.class);
        this.virtualDeviceManagerInternal = virtualDeviceManagerInternal;
        if (virtualDeviceManagerInternal != null
                && (allPersistentDeviceIds =
                                virtualDeviceManagerInternal.getAllPersistentDeviceIds())
                        != null) {
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                AccessState accessState = accessCheckingService.state;
                if (accessState == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                MutableAccessState mutable = accessState.toMutable();
                MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                this.devicePolicy.getClass();
                DevicePermissionPolicy.trimDevicePermissionStates(
                        mutateStateScope, allPersistentDeviceIds);
                accessCheckingService.persistence.write(mutable);
                accessCheckingService.state = mutable;
                IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                int size = indexedMap.map.size();
                for (int i = 0; i < size; i++) {
                    Object keyAt = indexedMap.map.keyAt(i);
                    IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i);
                    int size2 = indexedMap2.map.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((SchemePolicy) indexedMap2.map.valueAt(i2)).onStateMutated();
                    }
                }
            }
        }
        VirtualDeviceManagerInternal virtualDeviceManagerInternal2 =
                this.virtualDeviceManagerInternal;
        if (virtualDeviceManagerInternal2 != null) {
            PermissionService$onSystemReady$2 permissionService$onSystemReady$2 =
                    new PermissionService$onSystemReady$2(0, this);
            VirtualDeviceManagerService.LocalService localService =
                    (VirtualDeviceManagerService.LocalService) virtualDeviceManagerInternal2;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                localService.mPersistentDeviceIdRemovedListeners.add(
                        permissionService$onSystemReady$2);
            }
        }
        this.permissionControllerManager =
                new PermissionControllerManager(this.context, PermissionThread.getHandler());
        PmHook.mSystemReady = true;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onUserCreated(int i) {
        PackageManager.corkPackageInfoCache();
        try {
            this.service
                    .onUserAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                            i);
        } finally {
            PackageManager.uncorkPackageInfoCache();
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void onUserRemoved(int i) {
        this.service
                .onUserRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                        i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final List queryPermissionsByGroup(int i, String str) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = Binder.getCallingUid();
            if (isUidInstantApp(callingUid)) {
                AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                return null;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            if (str != null) {
                this.policy.getClass();
                PermissionGroupInfo permissionGroupInfo =
                        (PermissionGroupInfo)
                                accessState.getSystemState().getPermissionGroups().map.get(str);
                if (permissionGroupInfo == null) {
                    AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                    return null;
                }
                if (!isPackageVisibleToUid(
                        withUnfilteredSnapshot, permissionGroupInfo.packageName, callingUid)) {
                    AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
                    return null;
                }
            }
            this.policy.getClass();
            IndexedMap permissions = accessState.getSystemState().getPermissions();
            ArrayList arrayList = new ArrayList();
            int size = permissions.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object keyAt = permissions.map.keyAt(i2);
                Permission permission = (Permission) permissions.map.valueAt(i2);
                PermissionInfo generatePermissionInfo =
                        (Intrinsics.areEqual(permission.permissionInfo.group, str)
                                        && isPackageVisibleToUid(
                                                withUnfilteredSnapshot,
                                                permission.permissionInfo.packageName,
                                                callingUid))
                                ? generatePermissionInfo(permission, i, 10000)
                                : null;
                if (generatePermissionInfo != null) {
                    arrayList.add(generatePermissionInfo);
                }
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void readLegacyPermissionStateTEMP() {}

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void readLegacyPermissionsTEMP(LegacyPermissionSettings legacyPermissionSettings) {
        this.service.initialize();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean removeAllowlistedRestrictedPermission(
            String str, String str2, int i, int i2) {
        ArrayList allowlistedRestrictedPermissions;
        if (str2 == null) {
            throw new IllegalArgumentException("permissionName cannot be null".toString());
        }
        if (enforceRestrictedPermission(str2)
                && (allowlistedRestrictedPermissions =
                                getAllowlistedRestrictedPermissions(i, i2, str))
                        != null
                && allowlistedRestrictedPermissions.remove(str2)) {
            return setAllowlistedRestrictedPermissions(
                    str, allowlistedRestrictedPermissions, i, i2, false);
        }
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void removeOnPermissionsChangeListener(
            IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.context.enforceCallingOrSelfPermission(
                "android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS",
                "removeOnPermissionsChangeListener");
        OnPermissionsChangeListeners onPermissionsChangeListeners =
                this.onPermissionsChangeListeners;
        if (onPermissionsChangeListeners != null) {
            onPermissionsChangeListeners.listeners.unregister(iOnPermissionsChangeListener);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("onPermissionsChangeListeners");
            throw null;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void removePackageGrantedPermissionsForMDM(String str) {
        this.mPackageGrantedPermissions.remove(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void removePermission(String str) {
        int callingUid = Binder.getCallingUid();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            boolean isUidInstantApp = isUidInstantApp(callingUid);
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            if (isUidInstantApp) {
                throw new SecurityException(
                        "Instant applications don't have access to this method");
            }
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                AccessState accessState = accessCheckingService.state;
                if (accessState == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                MutableAccessState mutable = accessState.toMutable();
                access$getAndEnforcePermissionTree(
                        this, new MutateStateScope(accessState, mutable), str);
                this.policy.getClass();
                Permission permission =
                        (Permission) mutable.getSystemState().getPermissions().map.get(str);
                if (permission != null) {
                    if (permission.type != 2) {
                        throw new SecurityException(
                                "Not allowed to modify non-dynamic permission " + str);
                    }
                    this.policy.getClass();
                    MutableAccessState.mutateSystemState$default(mutable)
                            .mutatePermissions()
                            .map
                            .remove(permission.permissionInfo.name);
                }
                accessCheckingService.persistence.write(mutable);
                accessCheckingService.state = mutable;
                IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                int size = indexedMap.map.size();
                for (int i = 0; i < size; i++) {
                    Object keyAt = indexedMap.map.keyAt(i);
                    IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i);
                    int size2 = indexedMap2.map.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((SchemePolicy) indexedMap2.map.valueAt(i2)).onStateMutated();
                    }
                }
            }
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void resetRuntimePermissions(AndroidPackage androidPackage, int i) {
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
            this.policy.resetRuntimePermissions(
                    mutateStateScope, androidPackage.getPackageName(), i);
            this.devicePolicy.resetRuntimePermissions(
                    mutateStateScope, androidPackage.getPackageName(), i);
            accessCheckingService.persistence.write(mutable);
            accessCheckingService.state = mutable;
            IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
            int size = indexedMap.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object keyAt = indexedMap.map.keyAt(i2);
                IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
                int size2 = indexedMap2.map.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((SchemePolicy) indexedMap2.map.valueAt(i3)).onStateMutated();
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void resetRuntimePermissionsForUser(int i) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                try {
                    AccessState accessState = accessCheckingService.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    MutableAccessState mutable = accessState.toMutable();
                    MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                    Iterator it = withUnfilteredSnapshot.getPackageStates().entrySet().iterator();
                    while (it.hasNext()) {
                        PackageState packageState =
                                (PackageState) ((Map.Entry) it.next()).getValue();
                        if (!packageState.isApex()) {
                            this.policy.resetRuntimePermissions(
                                    mutateStateScope, packageState.getPackageName(), i);
                            this.devicePolicy.resetRuntimePermissions(
                                    mutateStateScope, packageState.getPackageName(), i);
                        }
                    }
                    accessCheckingService.persistence.write(mutable);
                    accessCheckingService.state = mutable;
                    IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                    int size = indexedMap.map.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        Object keyAt = indexedMap.map.keyAt(i2);
                        IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
                        int size2 = indexedMap2.map.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            ((SchemePolicy) indexedMap2.map.valueAt(i3)).onStateMutated();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
        } finally {
        }
    }

    public final void restoreDelayedRuntimePermissions(String str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("packageName".toString());
        }
        Preconditions.checkArgumentNonnegative(i, "userId");
        synchronized (this.isDelayedPermissionBackupFinished) {
            if (this.isDelayedPermissionBackupFinished.get(i, false)) {
                return;
            }
            PermissionControllerManager permissionControllerManager =
                    this.permissionControllerManager;
            if (permissionControllerManager != null) {
                permissionControllerManager.applyStagedRuntimePermissionBackup(
                        str,
                        UserHandle.of(i),
                        PermissionThread.getExecutor(),
                        new PermissionService$restoreDelayedRuntimePermissions$3(this, i));
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("permissionControllerManager");
                throw null;
            }
        }
    }

    public final void restoreRuntimePermissions(byte[] bArr, int i) {
        if (bArr == null) {
            throw new IllegalArgumentException("backup".toString());
        }
        Preconditions.checkArgumentNonnegative(i, "userId");
        synchronized (this.isDelayedPermissionBackupFinished) {
            this.isDelayedPermissionBackupFinished.delete(i);
        }
        PermissionControllerManager permissionControllerManager = this.permissionControllerManager;
        if (permissionControllerManager != null) {
            permissionControllerManager.stageAndApplyRuntimePermissionsBackup(
                    bArr, UserHandle.of(i));
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("permissionControllerManager");
            throw null;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void revokePostNotificationPermissionWithoutKillForTest(String str, int i) {
        setRuntimePermissionGranted$default(
                this,
                str,
                i,
                "android.permission.POST_NOTIFICATIONS",
                "default:0",
                false,
                true,
                null,
                64);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void revokeRuntimePermission(
            String str, String str2, String str3, int i, String str4) {
        setRuntimePermissionGranted$default(this, str, i, str2, str3, false, false, str4, 32);
    }

    public final boolean setAllowlistedRestrictedPermissions(
            String str, List list, int i, int i2, boolean z) {
        Preconditions.checkArgument(Integer.bitCount(i) == 1);
        boolean z2 =
                this.context.checkCallingOrSelfPermission(
                                "android.permission.WHITELIST_RESTRICTED_PERMISSIONS")
                        == 0;
        int callingUid = Binder.getCallingUid();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot(callingUid, UserHandle.of(i2));
        try {
            PackageState packageState =
                    (PackageState) withFilteredSnapshot.getPackageStates().get(str);
            if (packageState == null) {
                AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
                return false;
            }
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            AndroidPackage androidPackage = packageState.getAndroidPackage();
            if (androidPackage == null) {
                return false;
            }
            PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
            if (packageManagerInternal == null) {
                Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                throw null;
            }
            boolean isCallerInstallerOfRecord =
                    ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal)
                            .mService
                            .snapshotComputer()
                            .isCallerInstallerOfRecord(androidPackage, callingUid);
            if (IntExtensionsKt.hasBits(i, 4)) {
                if (!z2 && !isCallerInstallerOfRecord) {
                    throw new SecurityException(
                            "Modifying upgrade allowlist requires being installer on record or"
                                + " android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                }
                if (z && !z2) {
                    throw new SecurityException(
                            "Adding to upgrade allowlist"
                                + " requiresandroid.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                }
            }
            setAllowlistedRestrictedPermissionsUnchecked(
                    androidPackage, packageState.getAppId(), list, i, i2);
            return true;
        } finally {
        }
    }

    public final void setAllowlistedRestrictedPermissionsUnchecked(
            AndroidPackage androidPackage, int i, List list, int i2, int i3) {
        int i4 =
                IntExtensionsKt.hasBits(i2, 1)
                        ? EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT
                        : 0;
        if (IntExtensionsKt.hasBits(i2, 4)) {
            i4 |= 131072;
        }
        if (IntExtensionsKt.hasBits(i2, 2)) {
            i4 |= 32768;
        }
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            try {
                AccessState accessState = accessCheckingService.state;
                if (accessState == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                MutableAccessState mutable = accessState.toMutable();
                MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                AppIdPermissionPolicy appIdPermissionPolicy = this.policy;
                appIdPermissionPolicy.getClass();
                IndexedMap permissions = mutable.getSystemState().getPermissions();
                int i5 = 0;
                for (Object obj : androidPackage.getRequestedPermissions()) {
                    int i6 = i5 + 1;
                    if (i5 < 0) {
                        throw new ArithmeticException("Index overflow has happened.");
                    }
                    String str = (String) obj;
                    Permission permission = (Permission) permissions.map.get(str);
                    if (permission != null
                            && (IntExtensionsKt.hasBits(permission.permissionInfo.flags, 4)
                                    || IntExtensionsKt.hasBits(
                                            permission.permissionInfo.flags, 8))) {
                        appIdPermissionPolicy.updatePermissionExemptFlags(
                                mutateStateScope,
                                i,
                                i3,
                                permission,
                                i4,
                                list.contains(str) ? i4 : 0);
                    }
                    i5 = i6;
                }
                accessCheckingService.persistence.write(mutable);
                accessCheckingService.state = mutable;
                IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                int size = indexedMap.map.size();
                for (int i7 = 0; i7 < size; i7++) {
                    Object keyAt = indexedMap.map.keyAt(i7);
                    IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i7);
                    int size2 = indexedMap2.map.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        ((SchemePolicy) indexedMap2.map.valueAt(i8)).onStateMutated();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Slog.d(
                "PermissionService",
                "setAllowlistedRestrictedPermissionsUnchecked called for package - "
                        + androidPackage.getPackageName());
        updateAdminPermissionsInternal(androidPackage.getPackageName());
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void setDefaultPermissionGrantFingerprint(int i, String str) {
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            MutableUserState mutateUserState = mutable.mutateUserState(i, 1);
            Intrinsics.checkNotNull(mutateUserState);
            mutateUserState.defaultPermissionGrantFingerprint = str;
            accessCheckingService.persistence.write(mutable);
            accessCheckingService.state = mutable;
            IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
            int size = indexedMap.map.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object keyAt = indexedMap.map.keyAt(i2);
                IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
                int size2 = indexedMap2.map.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((SchemePolicy) indexedMap2.map.valueAt(i3)).onStateMutated();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x018c  */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setLicensePermissionsForMDM(java.lang.String r20, java.util.Set r21) {
        /*
            Method dump skipped, instructions count: 887
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.PermissionService.setLicensePermissionsForMDM(java.lang.String,"
                    + " java.util.Set):int");
    }

    public final void setPermissionFlagsWithPolicy(
            int i, int i2, int i3, MutateStateScope mutateStateScope, String str, String str2) {
        if (!Flags.deviceAwarePermissionApisEnabled() || Intrinsics.areEqual(str2, "default:0")) {
            this.policy.updatePermissionFlags(mutateStateScope, i, i2, str, -1, i3);
            return;
        }
        if (PermissionManager.DEVICE_AWARE_PERMISSIONS.contains(str)) {
            this.devicePolicy.setPermissionFlags(i, i2, i3, mutateStateScope, str2, str);
            return;
        }
        Slog.i(
                "PermissionService",
                str + " is not device aware permission,  set the flags for default device.");
        this.policy.updatePermissionFlags(mutateStateScope, i, i2, str, -1, i3);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final boolean shouldShowRequestPermissionRationale(
            int i, String str, String str2, String str3) {
        int appId;
        IPlatformCompat iPlatformCompat;
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        boolean z = false;
        if (!userManagerInternal.exists(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "shouldShowRequestPermissionRationale: Unknown user ", "PermissionService");
            return false;
        }
        enforceCallingOrSelfCrossUserPermission(
                "shouldShowRequestPermissionRationale", true, false, i);
        int callingUid = Binder.getCallingUid();
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                packageManagerLocal.withFilteredSnapshot(callingUid, UserHandle.of(i));
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            AutoCloseableKt.closeFinally(withFilteredSnapshot, null);
            if (packageState == null
                    || UserHandle.getAppId(callingUid) != (appId = packageState.getAppId())) {
                return false;
            }
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            GetStateScope getStateScope = new GetStateScope(accessState);
            if (isPermissionGranted(getStateScope, packageState, i, str2, str3)) {
                return false;
            }
            int permissionFlagsWithPolicy =
                    getPermissionFlagsWithPolicy(appId, i, getStateScope, str2, str3);
            if (IntExtensionsKt.hasAnyBit(permissionFlagsWithPolicy, 262592)) {
                return false;
            }
            if (Intrinsics.areEqual(str2, "android.permission.ACCESS_BACKGROUND_LOCATION")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        iPlatformCompat = this.platformCompat;
                    } catch (RemoteException e) {
                        Slog.e(
                                "PermissionService",
                                "shouldShowRequestPermissionRationale: Unable to check if"
                                    + " compatibility change is enabled",
                                e);
                    }
                    if (iPlatformCompat == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("platformCompat");
                        throw null;
                    }
                    z = iPlatformCompat.isChangeEnabledByPackageName(147316723L, str, i);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (z) {
                        return true;
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            return IntExtensionsKt.hasBits(permissionFlagsWithPolicy, 32);
        } finally {
        }
    }

    public final ArrayList updateLicensePermissionMDM(
            int[] iArr, ArrayList arrayList, String str, boolean z) {
        boolean access$updateLicensePermissionInternalMDM;
        int[] iArr2 = iArr;
        ArrayList arrayList2 = new ArrayList();
        int length = iArr2.length;
        int i = 0;
        while (i < length) {
            int i2 = iArr2[i];
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Permission permission = (Permission) it.next();
                AccessCheckingService accessCheckingService = this.service;
                synchronized (accessCheckingService.stateLock) {
                    AccessState accessState = accessCheckingService.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    MutableAccessState mutable = accessState.toMutable();
                    access$updateLicensePermissionInternalMDM =
                            access$updateLicensePermissionInternalMDM(
                                    this,
                                    new MutateStateScope(accessState, mutable),
                                    str,
                                    permission.permissionInfo.name,
                                    i2,
                                    z);
                    accessCheckingService.persistence.write(mutable);
                    accessCheckingService.state = mutable;
                    IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                    int size = indexedMap.map.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        Object keyAt = indexedMap.map.keyAt(i3);
                        IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i3);
                        int size2 = indexedMap2.map.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            ((SchemePolicy) indexedMap2.map.valueAt(i4)).onStateMutated();
                        }
                    }
                }
                if (access$updateLicensePermissionInternalMDM) {
                    arrayList2.add(permission.permissionInfo.name);
                }
            }
            i++;
            iArr2 = iArr;
        }
        return arrayList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00db A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePermissionFlags(
            java.lang.String r19,
            java.lang.String r20,
            int r21,
            int r22,
            boolean r23,
            java.lang.String r24,
            int r25) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.PermissionService.updatePermissionFlags(java.lang.String,"
                    + " java.lang.String, int, int, boolean, java.lang.String, int):void");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        AndroidPackage androidPackage;
        int callingUid = Binder.getCallingUid();
        UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            throw null;
        }
        if (!userManagerInternal.exists(i3)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i3, "updatePermissionFlagsForAllApps: Unknown user ", "PermissionService");
            return;
        }
        enforceCallingOrSelfCrossUserPermission("updatePermissionFlagsForAllApps", true, true, i3);
        enforceCallingOrSelfAnyPermission(
                "updatePermissionFlagsForAllApps",
                "android.permission.GRANT_RUNTIME_PERMISSIONS",
                "android.permission.REVOKE_RUNTIME_PERMISSIONS");
        boolean isRootOrSystemUid = isRootOrSystemUid(callingUid);
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            Map packageStates = withUnfilteredSnapshot.getPackageStates();
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                try {
                    AccessState accessState = accessCheckingService.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    MutableAccessState mutable = accessState.toMutable();
                    MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                    for (Map.Entry entry : packageStates.entrySet()) {
                        String str = (String) entry.getKey();
                        PackageState packageState = (PackageState) entry.getValue();
                        if (!packageState.isApex()
                                && (androidPackage = packageState.getAndroidPackage()) != null) {
                            Iterator it = androidPackage.getRequestedPermissions().iterator();
                            while (it.hasNext()) {
                                access$updatePermissionFlags(
                                        this,
                                        mutateStateScope,
                                        packageState.getAppId(),
                                        i3,
                                        (String) it.next(),
                                        "default:0",
                                        i,
                                        i2,
                                        isRootOrSystemUid,
                                        false,
                                        true,
                                        "updatePermissionFlagsForAllApps",
                                        str);
                                accessCheckingService = accessCheckingService;
                                mutateStateScope = mutateStateScope;
                                mutable = mutable;
                            }
                        }
                        accessCheckingService = accessCheckingService;
                        mutateStateScope = mutateStateScope;
                        mutable = mutable;
                    }
                    MutableAccessState mutableAccessState = mutable;
                    AccessCheckingService accessCheckingService2 = accessCheckingService;
                    accessCheckingService2.persistence.write(mutableAccessState);
                    accessCheckingService2.state = mutableAccessState;
                    IndexedMap indexedMap = accessCheckingService2.policy.schemePolicies;
                    int size = indexedMap.map.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        Object keyAt = indexedMap.map.keyAt(i4);
                        IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i4);
                        int size2 = indexedMap2.map.size();
                        for (int i5 = 0; i5 < size2; i5++) {
                            ((SchemePolicy) indexedMap2.map.valueAt(i5)).onStateMutated();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
        }
    }

    public final boolean updatePermissionStatesAndFlagsInternalForMDM(
            String str, int i, int i2, int i3, List list) {
        ArrayList<PackageStateInternal> arrayList;
        boolean z;
        boolean z2;
        boolean z3;
        int appId;
        Object obj;
        if (str == null) {
            PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
            if (packageManagerInternal == null) {
                Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                throw null;
            }
            arrayList =
                    new ArrayList(
                            ((PackageManagerService.PackageManagerInternalImpl)
                                            packageManagerInternal)
                                    .mService
                                    .snapshotComputer()
                                    .getPackageStates()
                                    .values());
        } else {
            ArrayList arrayList2 = new ArrayList();
            PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
            if (packageManagerInternal2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                throw null;
            }
            PackageStateInternal packageStateInternal =
                    packageManagerInternal2.getPackageStateInternal(str);
            Intrinsics.checkNotNull(packageStateInternal);
            arrayList2.add(packageStateInternal);
            arrayList = arrayList2;
        }
        try {
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                AccessState accessState = accessCheckingService.state;
                if (accessState == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                MutableAccessState mutable = accessState.toMutable();
                MutateStateScope mutateStateScope = new MutateStateScope(accessState, mutable);
                this.policy.getClass();
                IndexedMap permissions = mutable.getSystemState().getPermissions();
                boolean z4 = true;
                z = true;
                for (PackageStateInternal packageStateInternal2 : arrayList) {
                    try {
                        Intrinsics.checkNotNull(packageStateInternal2);
                        AndroidPackage androidPackage = packageStateInternal2.getAndroidPackage();
                        Intrinsics.checkNotNull(androidPackage);
                        Iterator it =
                                ((ArrayList)
                                                getRequestedRuntimePermissionsInternal(
                                                        mutateStateScope, androidPackage, list))
                                        .iterator();
                        boolean z5 = z;
                        while (it.hasNext()) {
                            try {
                                String str2 = (String) it.next();
                                try {
                                    appId = packageStateInternal2.getAppId();
                                    obj = permissions.map.get(str2);
                                    Intrinsics.checkNotNull(obj);
                                    z3 = z4;
                                } catch (NullPointerException e) {
                                    e = e;
                                    z3 = z4;
                                } catch (RuntimeException e2) {
                                    e = e2;
                                    z3 = z4;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                z3 = z4;
                            }
                            try {
                                try {
                                    z5 =
                                            access$handlePermissionStateInternal(
                                                    this,
                                                    mutateStateScope,
                                                    appId,
                                                    i3,
                                                    i,
                                                    (Permission) obj,
                                                    i2);
                                } catch (NullPointerException e4) {
                                    e = e4;
                                    try {
                                        Slog.e(
                                                "PermissionService",
                                                "updatePermissionState perm failed nullptr"
                                                    + " exception ",
                                                e);
                                        z4 = z3;
                                        z5 = false;
                                    } catch (Exception e5) {
                                        e = e5;
                                        z = false;
                                        Slog.e(
                                                "PermissionService",
                                                "updatePermissionState state failed : ",
                                                e);
                                        z4 = z3;
                                    }
                                } catch (RuntimeException e6) {
                                    e = e6;
                                    Slog.e(
                                            "PermissionService",
                                            "updatePermissionState perm failed runtime exception ",
                                            e);
                                    z4 = z3;
                                }
                                z4 = z3;
                            } catch (Exception e7) {
                                e = e7;
                                z = z5;
                                Slog.e(
                                        "PermissionService",
                                        "updatePermissionState state failed : ",
                                        e);
                                z4 = z3;
                            }
                        }
                        z = z5;
                    } catch (Exception e8) {
                        e = e8;
                        z3 = z4;
                    }
                }
                z2 = z4;
                accessCheckingService.persistence.write(mutable);
                accessCheckingService.state = mutable;
                IndexedMap indexedMap = accessCheckingService.policy.schemePolicies;
                int size = indexedMap.map.size();
                for (int i4 = 0; i4 < size; i4++) {
                    Object keyAt = indexedMap.map.keyAt(i4);
                    IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i4);
                    int size2 = indexedMap2.map.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        ((SchemePolicy) indexedMap2.map.valueAt(i5)).onStateMutated();
                    }
                }
            }
            PackageManagerInternal packageManagerInternal3 = this.packageManagerInternal;
            if (packageManagerInternal3 != null) {
                packageManagerInternal3.writePermissionSettings(new int[] {i3}, z2);
                return z;
            }
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        } catch (Exception e9) {
            Slog.e("PermissionService", "updatePermissionState Failed with exception", e9);
            return false;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void writeLegacyPermissionStateTEMP() {}

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public final void writeLegacyPermissionsTEMP(
            LegacyPermissionSettings legacyPermissionSettings) {
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.policy.getClass();
        legacyPermissionSettings.replacePermissions(
                toLegacyPermissions(accessState.getSystemState().getPermissions()));
        this.policy.getClass();
        legacyPermissionSettings.replacePermissionTrees(
                toLegacyPermissions(accessState.getSystemState().getPermissionTrees()));
    }
}
