package com.android.server.permission.access;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.SystemProperties;
import android.permission.PermissionManager;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.appop.AppOpsCheckingServiceInterface;
import com.android.server.permission.PermissionManagerLocal;
import com.android.server.permission.access.appop.AppOpService;
import com.android.server.permission.access.immutable.IndexedListSet;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IntMap;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIntMap;
import com.android.server.permission.access.permission.PermissionManagerLocalImpl;
import com.android.server.permission.access.permission.PermissionService;
import com.android.server.permission.jarjar.kotlin.Pair;
import com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt;
import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.PermissionManagerServiceInterface;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AccessCheckingService extends SystemService {
    private AppOpService appOpService;
    private PackageManagerInternal packageManagerInternal;
    private PackageManagerLocal packageManagerLocal;
    private PermissionService permissionService;
    private final AccessPersistence persistence;
    private final AccessPolicy policy;
    private volatile AccessState state;
    private final Object stateLock;
    private SystemConfig systemConfig;
    private UserManagerService userManagerService;

    public AccessCheckingService(Context context) {
        super(context);
        this.stateLock = new Object();
        AccessPolicy accessPolicy = new AccessPolicy();
        this.policy = accessPolicy;
        this.persistence = new AccessPersistence(accessPolicy);
    }

    private final Pair getAllPackageStates(PackageManagerLocal packageManagerLocal) {
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                packageManagerLocal.withUnfilteredSnapshot();
        try {
            Pair pair =
                    new Pair(
                            withUnfilteredSnapshot.getPackageStates(),
                            withUnfilteredSnapshot.getDisabledSystemPackageStates());
            AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return pair;
        } finally {
        }
    }

    private final IndexedMap getImplicitToSourcePermissions(SystemConfig systemConfig) {
        MutableIndexedMap mutableIndexedMap = new MutableIndexedMap();
        Iterator it = systemConfig.mSplitPermissions.iterator();
        while (it.hasNext()) {
            PermissionManager.SplitPermissionInfo splitPermissionInfo =
                    (PermissionManager.SplitPermissionInfo) it.next();
            String splitPermission = splitPermissionInfo.getSplitPermission();
            for (String str : splitPermissionInfo.getNewPermissions()) {
                Object obj = mutableIndexedMap.map.get(str);
                if (obj == null) {
                    obj = new MutableIndexedListSet();
                    mutableIndexedMap.put(str, obj);
                }
                ((MutableIndexedListSet) obj).add(splitPermission);
            }
        }
        return mutableIndexedMap;
    }

    private final IntMap getKnownPackages(PackageManagerInternal packageManagerInternal) {
        MutableIntMap mutableIntMap = new MutableIntMap();
        mutableIntMap.array.put(2, packageManagerInternal.getKnownPackageNames(2, 0));
        mutableIntMap.array.put(7, packageManagerInternal.getKnownPackageNames(7, 0));
        mutableIntMap.array.put(4, packageManagerInternal.getKnownPackageNames(4, 0));
        mutableIntMap.array.put(1, packageManagerInternal.getKnownPackageNames(1, 0));
        mutableIntMap.array.put(6, packageManagerInternal.getKnownPackageNames(6, 0));
        mutableIntMap.array.put(10, packageManagerInternal.getKnownPackageNames(10, 0));
        mutableIntMap.array.put(11, packageManagerInternal.getKnownPackageNames(11, 0));
        mutableIntMap.array.put(12, packageManagerInternal.getKnownPackageNames(12, 0));
        mutableIntMap.array.put(15, packageManagerInternal.getKnownPackageNames(15, 0));
        mutableIntMap.array.put(16, packageManagerInternal.getKnownPackageNames(16, 0));
        mutableIntMap.array.put(17, packageManagerInternal.getKnownPackageNames(17, 0));
        return mutableIntMap;
    }

    private final IndexedListSet getPrivilegedPermissionAllowlistPackages(
            SystemConfig systemConfig) {
        MutableIndexedListSet mutableIndexedListSet = new MutableIndexedListSet();
        mutableIndexedListSet.add("android");
        if (systemConfig.mAvailableFeatures.containsKey("android.hardware.type.automotive")) {
            String str = SystemProperties.get("ro.android.car.carservice.package");
            if (str.length() > 0) {
                mutableIndexedListSet.add(str);
            }
        }
        return mutableIndexedListSet;
    }

    private final boolean isLeanback(SystemConfig systemConfig) {
        return systemConfig.mAvailableFeatures.containsKey("android.software.leanback");
    }

    public final SchemePolicy
            getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    String str, String str2) {
        IndexedMap indexedMap = (IndexedMap) this.policy.schemePolicies.map.get(str);
        SchemePolicy schemePolicy =
                indexedMap != null ? (SchemePolicy) indexedMap.map.get(str2) : null;
        if (schemePolicy != null) {
            return schemePolicy;
        }
        throw new IllegalStateException(
                XmlUtils$$ExternalSyntheticOutline0.m(
                                "Scheme policy for ", str, " and ", str2, " does not exist")
                        .toString());
    }

    public final Object
            getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    Function1 function1) {
        AccessState accessState = this.state;
        if (accessState == null) {
            Intrinsics.throwUninitializedPropertyAccessException(
                    LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            throw null;
        }
        function1.invoke(new GetStateScope(accessState));
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x03bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initialize() {
        /*
            Method dump skipped, instructions count: 1405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.AccessCheckingService.initialize():void");
    }

    public final void
            mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    Function1 function1) {
        synchronized (this.stateLock) {
            try {
                AccessState accessState = this.state;
                if (accessState == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                    throw null;
                }
                function1.invoke(new MutateStateScope(accessState, accessState.toMutable()));
                throw null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void
            onPackageAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    String str) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        Pair allPackageStates = getAllPackageStates(packageManagerLocal);
        Map map = (Map) allPackageStates.component1();
        Map map2 = (Map) allPackageStates.component2();
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onPackageAdded(
                    new MutateStateScope(accessState, mutable), map, map2, knownPackages, str);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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

    public final void
            onPackageInstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    String str, int i) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        Pair allPackageStates = getAllPackageStates(packageManagerLocal);
        Map map = (Map) allPackageStates.component1();
        Map map2 = (Map) allPackageStates.component2();
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onPackageInstalled(
                    new MutateStateScope(accessState, mutable), map, map2, knownPackages, str, i);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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

    public final void
            onPackageRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    String str, int i) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        Pair allPackageStates = getAllPackageStates(packageManagerLocal);
        Map map = (Map) allPackageStates.component1();
        Map map2 = (Map) allPackageStates.component2();
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onPackageRemoved(
                    new MutateStateScope(accessState, mutable), map, map2, knownPackages, str, i);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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

    public final void
            onPackageUninstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    String str, int i, int i2) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        Pair allPackageStates = getAllPackageStates(packageManagerLocal);
        Map map = (Map) allPackageStates.component1();
        Map map2 = (Map) allPackageStates.component2();
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onPackageUninstalled(
                    new MutateStateScope(accessState, mutable),
                    map,
                    map2,
                    knownPackages,
                    str,
                    i,
                    i2);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.appOpService = new AppOpService(this);
        this.permissionService = new PermissionService(this);
        AppOpService appOpService = this.appOpService;
        if (appOpService == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appOpService");
            throw null;
        }
        LocalServices.addService(AppOpsCheckingServiceInterface.class, appOpService);
        PermissionService permissionService = this.permissionService;
        if (permissionService == null) {
            Intrinsics.throwUninitializedPropertyAccessException("permissionService");
            throw null;
        }
        LocalServices.addService(PermissionManagerServiceInterface.class, permissionService);
        LocalManagerRegistry.addManager(
                PermissionManagerLocal.class, new PermissionManagerLocalImpl(this));
    }

    public final void
            onStorageVolumeMounted$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    String str, List list, boolean z) {
        PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            throw null;
        }
        Pair allPackageStates = getAllPackageStates(packageManagerLocal);
        Map map = (Map) allPackageStates.component1();
        Map map2 = (Map) allPackageStates.component2();
        PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            throw null;
        }
        IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onStorageVolumeMounted(
                    new MutateStateScope(accessState, mutable),
                    map,
                    map2,
                    knownPackages,
                    str,
                    list,
                    z);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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

    public final void
            onSystemReady$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            AccessPolicy accessPolicy = this.policy;
            accessPolicy.getClass();
            mutable.mutateExternalState().isSystemReady = true;
            IndexedMap indexedMap = accessPolicy.schemePolicies;
            int size = indexedMap.map.size();
            for (int i = 0; i < size; i++) {
                Object keyAt = indexedMap.map.keyAt(i);
                IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i);
                int size2 = indexedMap2.map.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ((SchemePolicy) indexedMap2.map.valueAt(i2)).onSystemReady();
                }
            }
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap3 = this.policy.schemePolicies;
            int size3 = indexedMap3.map.size();
            for (int i3 = 0; i3 < size3; i3++) {
                Object keyAt2 = indexedMap3.map.keyAt(i3);
                IndexedMap indexedMap4 = (IndexedMap) indexedMap3.map.valueAt(i3);
                int size4 = indexedMap4.map.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    ((SchemePolicy) indexedMap4.map.valueAt(i4)).onStateMutated();
                }
            }
        }
    }

    public final void
            onUserAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    int i) {
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onUserAdded(new MutateStateScope(accessState, mutable), i);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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

    public final void
            onUserRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    int i) {
        synchronized (this.stateLock) {
            AccessState accessState = this.state;
            if (accessState == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                throw null;
            }
            MutableAccessState mutable = accessState.toMutable();
            this.policy.onUserRemoved(new MutateStateScope(accessState, mutable), i);
            this.persistence.write(mutable);
            this.state = mutable;
            IndexedMap indexedMap = this.policy.schemePolicies;
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
}
