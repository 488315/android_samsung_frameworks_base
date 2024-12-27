package com.android.server.permission.access.appop;

import android.app.AppOpsManager;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.appop.AppOpsCheckingServiceInterface;
import com.android.server.appop.AppOpsService;
import com.android.server.permission.access.AccessCheckingService;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IndexedMapExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedReferenceMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.permission.AppIdPermissionPolicy;
import com.android.server.permission.access.permission.DevicePermissionPolicy;
import com.android.server.permission.access.permission.Permission;
import com.android.server.permission.access.permission.PermissionFlags;
import com.android.server.permission.access.permission.PermissionService;
import com.android.server.permission.jarjar.kotlin.Triple;
import com.android.server.permission.jarjar.kotlin.Unit;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpService implements AppOpsCheckingServiceInterface {
    public final AppIdAppOpPolicy appIdPolicy;
    public final ArrayMap backgroundToForegroundPermissionNames;
    public final Context context;
    public final DevicePermissionPolicy devicePermissionPolicy;
    public final ArrayMap foregroundToBackgroundPermissionName;
    public final SparseBooleanArray foregroundableOps;
    public volatile ArraySet listeners;
    public final Object listenersLock;
    public final PackageAppOpPolicy packagePolicy;
    public final AppIdPermissionPolicy permissionPolicy;
    public final SparseArray runtimeAppOpToPermissionNames;
    public final ArrayMap runtimePermissionNameToAppOp;
    public final AccessCheckingService service;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnAppIdAppOpModeChangedListener {
        public final LongSparseArray pendingChanges = new LongSparseArray();

        public OnAppIdAppOpModeChangedListener() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnPackageAppOpModeChangedListener {
        public final ArrayMap pendingChanges = new ArrayMap();

        public OnPackageAppOpModeChangedListener() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OnPermissionFlagsChangedListener
            implements AppIdPermissionPolicy.OnPermissionFlagsChangedListener,
                    DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener {
        public final ArrayMap pendingChanges = new ArrayMap();

        public OnPermissionFlagsChangedListener() {}

        public final void addPendingChangedModeIfNeeded(
                int i, int i2, String str, int i3, int i4, int i5, int i6, int i7) {
            AppOpService.this.getClass();
            int evaluateModeFromPermissionFlags =
                    AppOpService.evaluateModeFromPermissionFlags(i4, i5);
            int evaluateModeFromPermissionFlags2 =
                    AppOpService.evaluateModeFromPermissionFlags(i6, i7);
            if (evaluateModeFromPermissionFlags != evaluateModeFromPermissionFlags2) {
                this.pendingChanges.put(
                        new Triple(
                                Integer.valueOf(UserHandle.getUid(i2, i)),
                                str,
                                Integer.valueOf(i3)),
                        Integer.valueOf(evaluateModeFromPermissionFlags2));
            }
        }

        @Override // com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener
        public final void onDevicePermissionFlagsChanged(
                int i, int i2, int i3, String str, String str2, int i4) {
            Integer num;
            Integer num2;
            int i5;
            AppOpService appOpService = AppOpService.this;
            ArraySet arraySet =
                    (ArraySet) appOpService.backgroundToForegroundPermissionNames.get(str2);
            Unit unit = null;
            if (arraySet == null) {
                String str3 = (String) appOpService.foregroundToBackgroundPermissionName.get(str2);
                if (str3 != null
                        && (num2 = (Integer) appOpService.runtimePermissionNameToAppOp.get(str2))
                                != null) {
                    AccessState accessState = appOpService.service.state;
                    if (accessState == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    appOpService.permissionPolicy.getClass();
                    int permissionFlags =
                            AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str3);
                    addPendingChangedModeIfNeeded(
                            i, i2, str, num2.intValue(), i3, permissionFlags, i4, permissionFlags);
                    unit = Unit.INSTANCE;
                }
                if (unit != null
                        || (num = (Integer) appOpService.runtimePermissionNameToAppOp.get(str2))
                                == null) {
                    return;
                }
                addPendingChangedModeIfNeeded(i, i2, str, num.intValue(), i3, 16, i4, 16);
                return;
            }
            int size = arraySet.size();
            int i6 = 0;
            while (i6 < size) {
                String str4 = (String) arraySet.valueAt(i6);
                Integer num3 = (Integer) appOpService.runtimePermissionNameToAppOp.get(str4);
                if (num3 != null) {
                    AccessState accessState2 = appOpService.service.state;
                    if (accessState2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(
                                LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                        throw null;
                    }
                    appOpService.permissionPolicy.getClass();
                    int permissionFlags2 =
                            AppIdPermissionPolicy.getPermissionFlags(accessState2, i, i2, str4);
                    i5 = i6;
                    addPendingChangedModeIfNeeded(
                            i,
                            i2,
                            str,
                            num3.intValue(),
                            permissionFlags2,
                            i3,
                            permissionFlags2,
                            i4);
                } else {
                    i5 = i6;
                }
                i6 = i5 + 1;
            }
        }

        @Override // com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener
        public final void onPermissionFlagsChanged(int i, int i2, int i3, int i4, String str) {
            onDevicePermissionFlagsChanged(i, i2, i3, "default:0", str, i4);
        }

        @Override // com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener, com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener
        public final void onStateMutated() {
            ArraySet arraySet = AppOpService.this.listeners;
            ArrayMap arrayMap = this.pendingChanges;
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                Object keyAt = arrayMap.keyAt(i);
                ((Number) arrayMap.valueAt(i)).intValue();
                Triple triple = (Triple) keyAt;
                int size2 = arraySet.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ((AppOpsService.AnonymousClass2) arraySet.valueAt(i2))
                            .onUidModeChanged(
                                    ((Number) triple.getFirst()).intValue(),
                                    ((Number) triple.getThird()).intValue(),
                                    (String) triple.getSecond());
                }
            }
            this.pendingChanges.clear();
        }
    }

    public AppOpService(AccessCheckingService accessCheckingService) {
        this.service = accessCheckingService;
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "package", "app-op");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.appop.PackageAppOpPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar);
        this.packagePolicy =
                (PackageAppOpPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2 =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "uid", "app-op");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.appop.AppIdAppOpPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2);
        this.appIdPolicy =
                (AppIdAppOpPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2;
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3 =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "uid", "permission");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3);
        this.permissionPolicy =
                (AppIdPermissionPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3;
        SchemePolicy
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar4 =
                        accessCheckingService
                                .getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                        "uid", "device-permission");
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type"
                    + " com.android.server.permission.access.permission.DevicePermissionPolicy",
                schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar4);
        this.devicePermissionPolicy =
                (DevicePermissionPolicy)
                        schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar4;
        this.context = accessCheckingService.getContext();
        this.runtimeAppOpToPermissionNames = new SparseArray();
        this.runtimePermissionNameToAppOp = new ArrayMap();
        this.foregroundableOps = new SparseBooleanArray();
        this.foregroundToBackgroundPermissionName = new ArrayMap();
        this.backgroundToForegroundPermissionNames = new ArrayMap();
        this.listeners = new ArraySet();
        this.listenersLock = new Object();
    }

    public static int evaluateModeFromPermissionFlags(int i, int i2) {
        if (PermissionFlags.isAppOpGranted(i)) {
            return PermissionFlags.isAppOpGranted(i2) ? 0 : 4;
        }
        return 1;
    }

    public static SparseIntArray opNameMapToOpSparseArray(ArrayMap arrayMap) {
        if (arrayMap == null) {
            return new SparseIntArray();
        }
        SparseIntArray sparseIntArray = new SparseIntArray(arrayMap.size());
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = arrayMap.keyAt(i);
            sparseIntArray.put(
                    AppOpsManager.strOpToOp((String) keyAt),
                    ((Number) arrayMap.valueAt(i)).intValue());
        }
        return sparseIntArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean addAppOpsModeChangedListener(
            AppOpsService.AnonymousClass2 anonymousClass2) {
        boolean add;
        synchronized (this.listenersLock) {
            ArraySet arraySet = new ArraySet(this.listeners);
            add = arraySet.add(anonymousClass2);
            this.listeners = arraySet;
        }
        return add;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void clearAllModes() {}

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseBooleanArray getForegroundOps(int i) {
        MutableIntReferenceMap appIdAppOpModes;
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.appIdPolicy.getClass();
        MutableUserState mutableUserState =
                (MutableUserState) accessState.getUserStates().get(userId);
        IndexedMap indexedMap =
                (mutableUserState == null
                                || (appIdAppOpModes = mutableUserState.getAppIdAppOpModes())
                                        == null)
                        ? null
                        : (IndexedMap) appIdAppOpModes.get(appId);
        ArrayMap arrayMap = indexedMap != null ? indexedMap.map : null;
        if (arrayMap != null) {
            int size = arrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str = (String) arrayMap.keyAt(i2);
                if (((Number) arrayMap.valueAt(i2)).intValue() == 4) {
                    sparseBooleanArray.put(AppOpsManager.strOpToOp(str), true);
                }
            }
        }
        if (Flags.runtimePermissionAppopsMappingEnabled()) {
            SparseBooleanArray sparseBooleanArray2 = this.foregroundableOps;
            int size2 = sparseBooleanArray2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                int keyAt = sparseBooleanArray2.keyAt(i3);
                sparseBooleanArray2.valueAt(i3);
                if (getUidMode(i, keyAt, "default:0") == 4) {
                    sparseBooleanArray.put(keyAt, true);
                }
            }
        }
        return sparseBooleanArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseBooleanArray getForegroundOps(int i, String str) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        ArrayMap packageModes = getPackageModes(i, str);
        if (packageModes != null) {
            int size = packageModes.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) packageModes.keyAt(i2);
                if (((Number) packageModes.valueAt(i2)).intValue() == 4) {
                    sparseBooleanArray.put(AppOpsManager.strOpToOp(str2), true);
                }
            }
        }
        if (Flags.runtimePermissionAppopsMappingEnabled()) {
            SparseBooleanArray sparseBooleanArray2 = this.foregroundableOps;
            int size2 = sparseBooleanArray2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                int keyAt = sparseBooleanArray2.keyAt(i3);
                sparseBooleanArray2.valueAt(i3);
                if (getPackageMode(keyAt, i, str) == 4) {
                    sparseBooleanArray.put(keyAt, true);
                }
            }
        }
        return sparseBooleanArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseIntArray getNonDefaultPackageModes(int i, String str) {
        return opNameMapToOpSparseArray(getPackageModes(i, str));
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final SparseIntArray getNonDefaultUidModes(int i) {
        MutableIntReferenceMap appIdAppOpModes;
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        GetStateScope getStateScope = new GetStateScope(accessState);
        this.appIdPolicy.getClass();
        MutableUserState mutableUserState =
                (MutableUserState) accessState.getUserStates().get(userId);
        IndexedMap indexedMap =
                (mutableUserState == null
                                || (appIdAppOpModes = mutableUserState.getAppIdAppOpModes())
                                        == null)
                        ? null
                        : (IndexedMap) appIdAppOpModes.get(appId);
        SparseIntArray opNameMapToOpSparseArray =
                opNameMapToOpSparseArray(indexedMap != null ? indexedMap.map : null);
        if (Flags.runtimePermissionAppopsMappingEnabled()) {
            ArrayMap arrayMap = this.runtimePermissionNameToAppOp;
            int size = arrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object keyAt = arrayMap.keyAt(i2);
                int intValue = ((Number) arrayMap.valueAt(i2)).intValue();
                int uidModeFromPermissionState =
                        getUidModeFromPermissionState(
                                appId, userId, getStateScope, (String) keyAt, "default:0");
                if (uidModeFromPermissionState != AppOpsManager.opToDefaultMode(intValue)) {
                    opNameMapToOpSparseArray.put(intValue, uidModeFromPermissionState);
                }
            }
        }
        return opNameMapToOpSparseArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final int getPackageMode(int i, int i2, String str) {
        MutableIndexedReferenceMap packageAppOpModes;
        String opToPublicName = AppOpsManager.opToPublicName(i);
        AccessState accessState = this.service.state;
        IndexedMap indexedMap = null;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.packagePolicy.getClass();
        MutableUserState mutableUserState = (MutableUserState) accessState.getUserStates().get(i2);
        if (mutableUserState != null
                && (packageAppOpModes = mutableUserState.getPackageAppOpModes()) != null) {
            indexedMap = (IndexedMap) packageAppOpModes.get(str);
        }
        return ((Number)
                        IndexedMapExtensionsKt.getWithDefault(
                                indexedMap,
                                opToPublicName,
                                Integer.valueOf(AppOpsManager.opToDefaultMode(opToPublicName))))
                .intValue();
    }

    public final ArrayMap getPackageModes(int i, String str) {
        MutableIndexedReferenceMap packageAppOpModes;
        AccessState accessState = this.service.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.packagePolicy.getClass();
        MutableUserState mutableUserState = (MutableUserState) accessState.getUserStates().get(i);
        IndexedMap indexedMap =
                (mutableUserState == null
                                || (packageAppOpModes = mutableUserState.getPackageAppOpModes())
                                        == null)
                        ? null
                        : (IndexedMap) packageAppOpModes.get(str);
        if (indexedMap != null) {
            return indexedMap.map;
        }
        return null;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final int getUidMode(int i, int i2, String str) {
        MutableIntReferenceMap appIdAppOpModes;
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        String opToPublicName = AppOpsManager.opToPublicName(i2);
        String str2 = (String) this.runtimeAppOpToPermissionNames.get(i2);
        boolean runtimePermissionAppopsMappingEnabled =
                Flags.runtimePermissionAppopsMappingEnabled();
        AccessCheckingService accessCheckingService = this.service;
        IndexedMap indexedMap = null;
        if (runtimePermissionAppopsMappingEnabled && str2 != null) {
            AccessState accessState = accessCheckingService.state;
            if (accessState != null) {
                return getUidModeFromPermissionState(
                        appId, userId, new GetStateScope(accessState), str2, str);
            }
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        AccessState accessState2 = accessCheckingService.state;
        if (accessState2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.appIdPolicy.getClass();
        MutableUserState mutableUserState =
                (MutableUserState) accessState2.getUserStates().get(userId);
        if (mutableUserState != null
                && (appIdAppOpModes = mutableUserState.getAppIdAppOpModes()) != null) {
            indexedMap = (IndexedMap) appIdAppOpModes.get(appId);
        }
        return ((Number)
                        IndexedMapExtensionsKt.getWithDefault(
                                indexedMap,
                                opToPublicName,
                                Integer.valueOf(AppOpsManager.opToDefaultMode(opToPublicName))))
                .intValue();
    }

    public final int getUidModeFromPermissionState(
            int i, int i2, GetStateScope getStateScope, String str, String str2) {
        int permissionFlags;
        int i3;
        String str3;
        boolean z =
                !str2.equals("default:0")
                        && PermissionManager.DEVICE_AWARE_PERMISSIONS.contains(str);
        DevicePermissionPolicy devicePermissionPolicy = this.devicePermissionPolicy;
        AccessState accessState = getStateScope.state;
        AppIdPermissionPolicy appIdPermissionPolicy = this.permissionPolicy;
        if (z) {
            devicePermissionPolicy.getClass();
            permissionFlags =
                    DevicePermissionPolicy.getPermissionFlags(i, i2, getStateScope, str2, str);
        } else {
            appIdPermissionPolicy.getClass();
            permissionFlags = AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str);
        }
        String str4 = (String) this.foregroundToBackgroundPermissionName.get(str);
        if (str4 == null) {
            i3 = 16;
        } else if (z) {
            devicePermissionPolicy.getClass();
            i3 = DevicePermissionPolicy.getPermissionFlags(i, i2, getStateScope, str2, str4);
        } else {
            appIdPermissionPolicy.getClass();
            i3 = AppIdPermissionPolicy.getPermissionFlags(accessState, i, i2, str4);
        }
        int evaluateModeFromPermissionFlags = evaluateModeFromPermissionFlags(permissionFlags, i3);
        return (evaluateModeFromPermissionFlags == 1
                        && (str3 = (String) PermissionService.FULLER_PERMISSIONS.get(str)) != null)
                ? getUidModeFromPermissionState(i, i2, getStateScope, str3, str2)
                : evaluateModeFromPermissionFlags;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void readState() {}

    /* JADX WARN: Removed duplicated region for block: B:11:0x0073 A[Catch: all -> 0x00a9, TryCatch #0 {, blocks: (B:4:0x000c, B:6:0x0012, B:9:0x0058, B:11:0x0073, B:13:0x008c, B:22:0x002a, B:25:0x0041, B:26:0x00ab, B:27:0x00b2), top: B:3:0x000c }] */
    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean removePackage(int r9, java.lang.String r10) {
        /*
            r8 = this;
            com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef r0 = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$BooleanRef
            r0.<init>()
            com.android.server.permission.access.AccessCheckingService r1 = r8.service
            java.lang.Object r2 = com.android.server.permission.access.AccessCheckingService.access$getStateLock$p(r1)
            monitor-enter(r2)
            com.android.server.permission.access.AccessState r3 = com.android.server.permission.access.AccessCheckingService.access$getState$p(r1)     // Catch: java.lang.Throwable -> La9
            if (r3 == 0) goto Lab
            com.android.server.permission.access.MutableAccessState r3 = r3.toMutable()     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.appop.PackageAppOpPolicy r8 = r8.packagePolicy     // Catch: java.lang.Throwable -> La9
            r8.getClass()     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.MutableIntReferenceMap r8 = r3.getUserStates()     // Catch: java.lang.Throwable -> La9
            android.util.SparseArray r8 = r8.array     // Catch: java.lang.Throwable -> La9
            int r8 = r8.indexOfKey(r9)     // Catch: java.lang.Throwable -> La9
            r9 = 0
            if (r8 >= 0) goto L2a
        L28:
            r8 = r9
            goto L58
        L2a:
            com.android.server.permission.access.immutable.MutableIntReferenceMap r4 = r3.getUserStates()     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.Immutable r4 = r4.valueAt(r8)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.MutableUserState r4 = (com.android.server.permission.access.MutableUserState) r4     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.MutableIndexedReferenceMap r4 = r4.getPackageAppOpModes()     // Catch: java.lang.Throwable -> La9
            android.util.ArrayMap r4 = r4.map     // Catch: java.lang.Throwable -> La9
            int r10 = r4.indexOfKey(r10)     // Catch: java.lang.Throwable -> La9
            if (r10 >= 0) goto L41
            goto L28
        L41:
            com.android.server.permission.access.MutableUserState r8 = com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default(r3, r8)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.MutableReference r8 = r8.packageAppOpModesReference     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.Immutable r8 = r8.mutate()     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.MutableIndexedReferenceMap r8 = (com.android.server.permission.access.immutable.MutableIndexedReferenceMap) r8     // Catch: java.lang.Throwable -> La9
            android.util.ArrayMap r8 = r8.map     // Catch: java.lang.Throwable -> La9
            java.lang.Object r8 = r8.removeAt(r10)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.MutableReference r8 = (com.android.server.permission.access.immutable.MutableReference) r8     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.Immutable r8 = r8.immutable     // Catch: java.lang.Throwable -> La9
            r8 = 1
        L58:
            r0.element = r8     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.AccessPersistence r8 = com.android.server.permission.access.AccessCheckingService.access$getPersistence$p(r1)     // Catch: java.lang.Throwable -> La9
            r8.write(r3)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.AccessCheckingService.access$setState$p(r1, r3)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.AccessPolicy r8 = com.android.server.permission.access.AccessCheckingService.access$getPolicy$p(r1)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.IndexedMap r8 = r8.schemePolicies     // Catch: java.lang.Throwable -> La9
            android.util.ArrayMap r10 = r8.map     // Catch: java.lang.Throwable -> La9
            int r10 = r10.size()     // Catch: java.lang.Throwable -> La9
            r1 = r9
        L71:
            if (r1 >= r10) goto La5
            android.util.ArrayMap r3 = r8.map     // Catch: java.lang.Throwable -> La9
            java.lang.Object r3 = r3.keyAt(r1)     // Catch: java.lang.Throwable -> La9
            android.util.ArrayMap r4 = r8.map     // Catch: java.lang.Throwable -> La9
            java.lang.Object r4 = r4.valueAt(r1)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.immutable.IndexedMap r4 = (com.android.server.permission.access.immutable.IndexedMap) r4     // Catch: java.lang.Throwable -> La9
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> La9
            android.util.ArrayMap r3 = r4.map     // Catch: java.lang.Throwable -> La9
            int r3 = r3.size()     // Catch: java.lang.Throwable -> La9
            r5 = r9
        L8a:
            if (r5 >= r3) goto La2
            android.util.ArrayMap r6 = r4.map     // Catch: java.lang.Throwable -> La9
            java.lang.Object r6 = r6.keyAt(r5)     // Catch: java.lang.Throwable -> La9
            android.util.ArrayMap r7 = r4.map     // Catch: java.lang.Throwable -> La9
            java.lang.Object r7 = r7.valueAt(r5)     // Catch: java.lang.Throwable -> La9
            com.android.server.permission.access.SchemePolicy r7 = (com.android.server.permission.access.SchemePolicy) r7     // Catch: java.lang.Throwable -> La9
            java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Throwable -> La9
            r7.onStateMutated()     // Catch: java.lang.Throwable -> La9
            int r5 = r5 + 1
            goto L8a
        La2:
            int r1 = r1 + 1
            goto L71
        La5:
            monitor-exit(r2)
            boolean r8 = r0.element
            return r8
        La9:
            r8 = move-exception
            goto Lb3
        Lab:
            java.lang.String r8 = "state"
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r8)     // Catch: java.lang.Throwable -> La9
            r8 = 0
            throw r8     // Catch: java.lang.Throwable -> La9
        Lb3:
            monitor-exit(r2)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.appop.AppOpService.removePackage(int,"
                    + " java.lang.String):boolean");
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void removeUid(int i) {
        int indexOfKey;
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        AccessCheckingService accessCheckingService = this.service;
        synchronized (accessCheckingService.stateLock) {
            AccessState accessState = accessCheckingService.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.appIdPolicy.getClass();
            int indexOfKey2 = mutable.getUserStates().array.indexOfKey(userId);
            if (indexOfKey2 >= 0
                    && (indexOfKey =
                                    ((MutableUserState)
                                                    mutable.getUserStates().valueAt(indexOfKey2))
                                            .getAppIdAppOpModes()
                                            .array
                                            .indexOfKey(appId))
                            >= 0) {
                ((MutableIntReferenceMap)
                                MutableAccessState.mutateUserStateAt$default(mutable, indexOfKey2)
                                        .appIdAppOpModesReference
                                        .mutate())
                        .removeAt$1(indexOfKey);
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
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void setPackageMode(int i, int i2, int i3, String str) {
        String opToPublicName = AppOpsManager.opToPublicName(i);
        if (Flags.runtimePermissionAppopsMappingEnabled()
                && this.runtimeAppOpToPermissionNames.contains(i)) {
            Slog.w(
                    "AppOpService",
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                    i3,
                                    "(packageName=",
                                    str,
                                    ", userId=",
                                    ")'s appop state for runtime op "),
                            opToPublicName,
                            " should not be set directly."),
                    new RuntimeException());
            return;
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
            this.packagePolicy.setAppOpMode(
                    new MutateStateScope(accessState, mutable), str, i3, opToPublicName, i2);
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
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final boolean setUidMode(int i, int i2, int i3) {
        MutableIntReferenceMap appIdAppOpModes;
        int appId = UserHandle.getAppId(i);
        int userId = UserHandle.getUserId(i);
        String opToPublicName = AppOpsManager.opToPublicName(i2);
        IndexedMap indexedMap = null;
        if (!Flags.runtimePermissionAppopsMappingEnabled()
                || !this.runtimeAppOpToPermissionNames.contains(i2)) {
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            AccessCheckingService accessCheckingService = this.service;
            synchronized (accessCheckingService.stateLock) {
                AccessState accessState = accessCheckingService.state;
                if (accessState == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                MutableAccessState mutable = accessState.toMutable();
                ref$BooleanRef.element =
                        this.appIdPolicy.setAppOpMode(
                                new MutateStateScope(accessState, mutable),
                                appId,
                                userId,
                                opToPublicName,
                                i3);
                accessCheckingService.persistence.write(mutable);
                accessCheckingService.state = mutable;
                IndexedMap indexedMap2 = accessCheckingService.policy.schemePolicies;
                int size = indexedMap2.map.size();
                for (int i4 = 0; i4 < size; i4++) {
                    Object keyAt = indexedMap2.map.keyAt(i4);
                    IndexedMap indexedMap3 = (IndexedMap) indexedMap2.map.valueAt(i4);
                    int size2 = indexedMap3.map.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        ((SchemePolicy) indexedMap3.map.valueAt(i5)).onStateMutated();
                    }
                }
            }
            return ref$BooleanRef.element;
        }
        AccessState accessState2 = this.service.state;
        if (accessState2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        this.appIdPolicy.getClass();
        MutableUserState mutableUserState =
                (MutableUserState) accessState2.getUserStates().get(userId);
        if (mutableUserState != null
                && (appIdAppOpModes = mutableUserState.getAppIdAppOpModes()) != null) {
            indexedMap = (IndexedMap) appIdAppOpModes.get(appId);
        }
        int intValue =
                ((Number)
                                IndexedMapExtensionsKt.getWithDefault(
                                        indexedMap,
                                        opToPublicName,
                                        Integer.valueOf(
                                                AppOpsManager.opToDefaultMode(opToPublicName))))
                        .intValue();
        boolean z = intValue != i3;
        String str = z ? "Blocked" : "Ignored";
        String opToName = AppOpsManager.opToName(i2);
        String modeToName = AppOpsManager.modeToName(i3);
        int callingUid = Binder.getCallingUid();
        String modeToName2 = AppOpsManager.modeToName(intValue);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" setUidMode call for runtime permission app op: uid = ");
        sb.append(i);
        sb.append(", code = ");
        sb.append(opToName);
        sb.append(", mode = ");
        sb.append(modeToName);
        sb.append(", callingUid = ");
        sb.append(callingUid);
        String m = AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, ", oldMode = ", modeToName2);
        if (z) {
            Slog.e("AppOpService", m, new RuntimeException());
        } else {
            Slog.w("AppOpService", m);
        }
        return false;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {}

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public final void systemReady() {
        if (Flags.runtimePermissionAppopsMappingEnabled()) {
            AccessState accessState = this.service.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            AppIdPermissionPolicy appIdPermissionPolicy = this.permissionPolicy;
            appIdPermissionPolicy.getClass();
            IndexedMap permissions = accessState.getSystemState().getPermissions();
            for (int i = 0; i < 149; i++) {
                String opToPermission = AppOpsManager.opToPermission(i);
                if (opToPermission != null
                        && i == AppOpsManager.permissionToOpCode(opToPermission)) {
                    Object obj = permissions.map.get(opToPermission);
                    Intrinsics.checkNotNull(obj);
                    Permission permission = (Permission) obj;
                    if (permission.permissionInfo.getProtection() == 1) {
                        this.runtimePermissionNameToAppOp.put(opToPermission, Integer.valueOf(i));
                        this.runtimeAppOpToPermissionNames.set(i, opToPermission);
                        String str = permission.permissionInfo.backgroundPermission;
                        if (str != null) {
                            this.foregroundableOps.put(i, true);
                            this.foregroundToBackgroundPermissionName.put(opToPermission, str);
                            ArrayMap arrayMap = this.backgroundToForegroundPermissionNames;
                            Object obj2 = arrayMap.get(str);
                            if (obj2 == null) {
                                obj2 = new ArraySet();
                                arrayMap.put(str, obj2);
                            }
                            ((ArraySet) obj2).add(opToPermission);
                        }
                    }
                }
            }
            OnPermissionFlagsChangedListener onPermissionFlagsChangedListener =
                    new OnPermissionFlagsChangedListener();
            synchronized (appIdPermissionPolicy.onPermissionFlagsChangedListenersLock) {
                MutableIndexedListSet mutableIndexedListSet =
                        appIdPermissionPolicy.onPermissionFlagsChangedListeners;
                mutableIndexedListSet.getClass();
                MutableIndexedListSet mutableIndexedListSet2 =
                        new MutableIndexedListSet(new ArrayList(mutableIndexedListSet.list));
                mutableIndexedListSet2.add(onPermissionFlagsChangedListener);
                appIdPermissionPolicy.onPermissionFlagsChangedListeners = mutableIndexedListSet2;
            }
            DevicePermissionPolicy devicePermissionPolicy = this.devicePermissionPolicy;
            synchronized (devicePermissionPolicy.listenersLock) {
                MutableIndexedListSet mutableIndexedListSet3 = devicePermissionPolicy.listeners;
                mutableIndexedListSet3.getClass();
                MutableIndexedListSet mutableIndexedListSet4 =
                        new MutableIndexedListSet(new ArrayList(mutableIndexedListSet3.list));
                mutableIndexedListSet4.add(onPermissionFlagsChangedListener);
                devicePermissionPolicy.listeners = mutableIndexedListSet4;
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {}
}
