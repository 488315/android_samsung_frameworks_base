package com.android.server.permission.access.appop;

import android.app.AppOpsManager;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.appop.AppOpMigrationHelperImpl;
import com.android.server.appop.AppOpsService;
import com.android.server.appop.AppOpsService$$ExternalSyntheticLambda15;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IndexedMapExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedReferenceMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableReference;
import com.android.server.permission.access.util.PackageVersionMigration;
import com.android.server.permission.jarjar.kotlin.Triple;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.pkg.PackageState;

import java.util.Collections;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageAppOpPolicy extends BaseAppOpPolicy {
    public final PackageAppOpMigration migration;
    public volatile MutableIndexedListSet onAppOpModeChangedListeners;
    public final Object onAppOpModeChangedListenersLock;
    public final PackageAppOpUpgrade upgrade;

    public PackageAppOpPolicy() {
        super(new PackageAppOpPersistence());
        this.migration = new PackageAppOpMigration();
        this.upgrade = new PackageAppOpUpgrade(this);
        this.onAppOpModeChangedListeners = new MutableIndexedListSet();
        this.onAppOpModeChangedListenersLock = new Object();
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getSubjectScheme() {
        return "package";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void migrateUserState(MutableAccessState mutableAccessState, int i) {
        this.migration.getClass();
        Object service = LocalServices.getService(AppOpMigrationHelperImpl.class);
        Intrinsics.checkNotNull(service);
        AppOpMigrationHelperImpl appOpMigrationHelperImpl = (AppOpMigrationHelperImpl) service;
        if (appOpMigrationHelperImpl.getLegacyAppOpVersion() > -1) {
            synchronized (appOpMigrationHelperImpl.mLock) {
                try {
                    if (appOpMigrationHelperImpl.mPackageAppOpModes == null) {
                        appOpMigrationHelperImpl.readLegacyAppOpState();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Map map =
                    (Map)
                            appOpMigrationHelperImpl.mPackageAppOpModes.get(
                                    i, Collections.emptyMap());
            int
                    version$frameworks__base__services__permission__android_common__services_permission_pre_jarjar =
                            PackageVersionMigration
                                    .getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                            i);
            MutableUserState mutateUserState = mutableAccessState.mutateUserState(i, 1);
            Intrinsics.checkNotNull(mutateUserState);
            MutableIndexedReferenceMap mutableIndexedReferenceMap =
                    (MutableIndexedReferenceMap)
                            mutateUserState.packageAppOpModesReference.mutate();
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                Map map2 = (Map) entry.getValue();
                if (mutableAccessState.getExternalState().packageStates.containsKey(str)) {
                    MutableIndexedMap mutableIndexedMap = new MutableIndexedMap();
                    mutableIndexedReferenceMap.put(str, mutableIndexedMap);
                    for (Map.Entry entry2 : map2.entrySet()) {
                        mutableIndexedMap.put(
                                (String) entry2.getKey(), (Integer) entry2.getValue());
                    }
                    mutateUserState
                            .mutatePackageVersions()
                            .put(
                                    str,
                                    Integer.valueOf(
                                            version$frameworks__base__services__permission__android_common__services_permission_pre_jarjar));
                } else {
                    PinnerService$$ExternalSyntheticOutline0.m(
                            "Dropping unknown package ",
                            str,
                            " when migrating app op state",
                            "PackageAppOpMigration");
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageRemoved(MutateStateScope mutateStateScope, String str, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            userStates.keyAt(i2);
            int indexOfKey =
                    ((MutableUserState) userStates.valueAt(i2))
                            .getPackageAppOpModes()
                            .map
                            .indexOfKey(str);
            if (indexOfKey >= 0) {
                Immutable immutable =
                        ((MutableReference)
                                        ((MutableIndexedReferenceMap)
                                                        MutableAccessState
                                                                .mutateUserStateAt$default(
                                                                        mutableAccessState, i2)
                                                                .packageAppOpModesReference
                                                                .mutate())
                                                .map.removeAt(indexOfKey))
                                .immutable;
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onStateMutated() {
        MutableIndexedListSet mutableIndexedListSet = this.onAppOpModeChangedListeners;
        int size = mutableIndexedListSet.list.size();
        int i = 0;
        while (i < size) {
            AppOpService.OnPackageAppOpModeChangedListener onPackageAppOpModeChangedListener =
                    (AppOpService.OnPackageAppOpModeChangedListener)
                            mutableIndexedListSet.list.get(i);
            ArraySet arraySet = AppOpService.this.listeners;
            ArrayMap arrayMap = onPackageAppOpModeChangedListener.pendingChanges;
            int size2 = arrayMap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Object keyAt = arrayMap.keyAt(i2);
                int intValue = ((Number) arrayMap.valueAt(i2)).intValue();
                Triple triple = (Triple) keyAt;
                int size3 = arraySet.size();
                int i3 = 0;
                while (i3 < size3) {
                    AppOpsService.AnonymousClass2 anonymousClass2 =
                            (AppOpsService.AnonymousClass2) arraySet.valueAt(i3);
                    String str = (String) triple.getFirst();
                    int intValue2 = ((Number) triple.getSecond()).intValue();
                    int intValue3 = ((Number) triple.getThird()).intValue();
                    AppOpsService appOpsService = AppOpsService.this;
                    appOpsService.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new AppOpsService$$ExternalSyntheticLambda15(1),
                                    appOpsService,
                                    str,
                                    Integer.valueOf(intValue3),
                                    Integer.valueOf(intValue),
                                    Integer.valueOf(intValue2)));
                    i3++;
                    mutableIndexedListSet = mutableIndexedListSet;
                    size = size;
                }
            }
            onPackageAppOpModeChangedListener.pendingChanges.clear();
            i++;
            mutableIndexedListSet = mutableIndexedListSet;
            size = size;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.server.permission.access.immutable.Immutable] */
    public final void setAppOpMode(
            MutateStateScope mutateStateScope, String str, int i, String str2, int i2) {
        if (!mutateStateScope.newState.getUserStates().array.contains(i)) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Unable to set app op mode for missing user ", "PackageAppOpPolicy");
            return;
        }
        int opToDefaultMode = AppOpsManager.opToDefaultMode(str2);
        Immutable immutable = mutateStateScope.newState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        if (((Number)
                                IndexedMapExtensionsKt.getWithDefault(
                                        (IndexedMap)
                                                ((MutableUserState) immutable)
                                                        .getPackageAppOpModes()
                                                        .get(str),
                                        str2,
                                        Integer.valueOf(opToDefaultMode)))
                        .intValue()
                == i2) {
            return;
        }
        MutableUserState mutateUserState = mutateStateScope.newState.mutateUserState(i, 1);
        Intrinsics.checkNotNull(mutateUserState);
        MutableIndexedReferenceMap mutableIndexedReferenceMap =
                (MutableIndexedReferenceMap) mutateUserState.packageAppOpModesReference.mutate();
        MutableReference mutableReference =
                (MutableReference) mutableIndexedReferenceMap.map.get(str);
        MutableIndexedMap mutate = mutableReference != null ? mutableReference.mutate() : null;
        if (mutate == null) {
            mutate = new MutableIndexedMap();
            mutableIndexedReferenceMap.put(str, mutate);
        }
        MutableIndexedMap mutableIndexedMap = mutate;
        IndexedMapExtensionsKt.putWithDefault(
                mutableIndexedMap, str2, Integer.valueOf(i2), Integer.valueOf(opToDefaultMode));
        if (mutableIndexedMap.map.isEmpty()) {
            mutableIndexedReferenceMap.remove$1(str);
        }
        MutableIndexedListSet mutableIndexedListSet = this.onAppOpModeChangedListeners;
        int size = mutableIndexedListSet.list.size();
        for (int i3 = 0; i3 < size; i3++) {
            AppOpService.OnPackageAppOpModeChangedListener onPackageAppOpModeChangedListener =
                    (AppOpService.OnPackageAppOpModeChangedListener)
                            mutableIndexedListSet.list.get(i3);
            onPackageAppOpModeChangedListener.getClass();
            onPackageAppOpModeChangedListener.pendingChanges.put(
                    new Triple(
                            str,
                            Integer.valueOf(i),
                            Integer.valueOf(AppOpsManager.strOpToOp(str2))),
                    Integer.valueOf(i2));
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void upgradePackageState(
            MutateStateScope mutateStateScope, PackageState packageState, int i, int i2) {
        MutableIndexedReferenceMap packageAppOpModes;
        PackageAppOpUpgrade packageAppOpUpgrade = this.upgrade;
        packageAppOpUpgrade.getClass();
        if (i2 <= 2) {
            String packageName = packageState.getPackageName();
            PackageAppOpPolicy packageAppOpPolicy = packageAppOpUpgrade.policy;
            packageAppOpPolicy.getClass();
            MutableUserState mutableUserState =
                    (MutableUserState) mutateStateScope.state.getUserStates().get(i);
            packageAppOpPolicy.setAppOpMode(
                    mutateStateScope,
                    packageState.getPackageName(),
                    i,
                    "android:run_any_in_background",
                    ((Number)
                                    IndexedMapExtensionsKt.getWithDefault(
                                            (mutableUserState == null
                                                            || (packageAppOpModes =
                                                                            mutableUserState
                                                                                    .getPackageAppOpModes())
                                                                    == null)
                                                    ? null
                                                    : (IndexedMap)
                                                            packageAppOpModes.get(packageName),
                                            "android:run_in_background",
                                            Integer.valueOf(
                                                    AppOpsManager.opToDefaultMode(
                                                            "android:run_in_background"))))
                            .intValue());
        }
    }
}
