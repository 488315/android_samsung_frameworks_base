package com.android.server.permission.access.appop;

import android.app.AppOpsManager;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.Slog;

import com.android.internal.util.IntPair;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.appop.AppOpMigrationHelperImpl;
import com.android.server.appop.AppOpsService;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedListSet;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IndexedMapExtensionsKt;
import com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.util.PackageVersionMigration;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;

import java.util.Collections;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppIdAppOpPolicy extends BaseAppOpPolicy {
    public final AppIdAppOpMigration migration;
    public volatile MutableIndexedListSet onAppOpModeChangedListeners;
    public final Object onAppOpModeChangedListenersLock;
    public final AppIdAppOpUpgrade upgrade;

    public AppIdAppOpPolicy() {
        super(new AppIdAppOpPersistence());
        this.migration = new AppIdAppOpMigration();
        this.upgrade = new AppIdAppOpUpgrade(this);
        this.onAppOpModeChangedListeners = new MutableIndexedListSet();
        this.onAppOpModeChangedListenersLock = new Object();
    }

    public static int getAppOpMode(GetStateScope getStateScope, int i, int i2, String str) {
        MutableIntReferenceMap appIdAppOpModes;
        MutableUserState mutableUserState =
                (MutableUserState) getStateScope.state.getUserStates().get(i2);
        return ((Number)
                        IndexedMapExtensionsKt.getWithDefault(
                                (mutableUserState == null
                                                || (appIdAppOpModes =
                                                                mutableUserState
                                                                        .getAppIdAppOpModes())
                                                        == null)
                                        ? null
                                        : (IndexedMap) appIdAppOpModes.get(i),
                                str,
                                Integer.valueOf(AppOpsManager.opToDefaultMode(str))))
                .intValue();
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getSubjectScheme() {
        return "uid";
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
                    if (appOpMigrationHelperImpl.mAppIdAppOpModes == null) {
                        appOpMigrationHelperImpl.readLegacyAppOpState();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Map map =
                    (Map) appOpMigrationHelperImpl.mAppIdAppOpModes.get(i, Collections.emptyMap());
            int
                    version$frameworks__base__services__permission__android_common__services_permission_pre_jarjar =
                            PackageVersionMigration
                                    .getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                            i);
            MutableUserState mutateUserState = mutableAccessState.mutateUserState(i, 1);
            Intrinsics.checkNotNull(mutateUserState);
            MutableIntReferenceMap mutableIntReferenceMap =
                    (MutableIntReferenceMap) mutateUserState.appIdAppOpModesReference.mutate();
            for (Map.Entry entry : map.entrySet()) {
                Integer num = (Integer) entry.getKey();
                Map map2 = (Map) entry.getValue();
                IndexedListSet indexedListSet =
                        (IndexedListSet)
                                mutableAccessState
                                        .getExternalState()
                                        .getAppIdPackageNames()
                                        .get(num.intValue());
                if (indexedListSet != null || num.intValue() < 10000) {
                    MutableIndexedMap mutableIndexedMap = new MutableIndexedMap();
                    IntReferenceMapExtensionsKt.set(
                            mutableIntReferenceMap, num.intValue(), mutableIndexedMap);
                    for (Map.Entry entry2 : map2.entrySet()) {
                        mutableIndexedMap.put(
                                (String) entry2.getKey(), (Integer) entry2.getValue());
                    }
                    if (indexedListSet != null) {
                        MutableIndexedMap mutatePackageVersions =
                                mutateUserState.mutatePackageVersions();
                        int size = indexedListSet.list.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            mutatePackageVersions.put(
                                    (String) indexedListSet.list.get(i2),
                                    Integer.valueOf(
                                            version$frameworks__base__services__permission__android_common__services_permission_pre_jarjar));
                        }
                    }
                } else {
                    Slog.w(
                            "AppIdAppOpMigration",
                            "Dropping unknown app ID " + num + " when migrating app op state");
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onAppIdRemoved(MutateStateScope mutateStateScope, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            userStates.keyAt(i2);
            int indexOfKey =
                    ((MutableUserState) userStates.valueAt(i2))
                            .getAppIdAppOpModes()
                            .array
                            .indexOfKey(i);
            if (indexOfKey >= 0) {
                ((MutableIntReferenceMap)
                                MutableAccessState.mutateUserStateAt$default(mutableAccessState, i2)
                                        .appIdAppOpModesReference
                                        .mutate())
                        .removeAt$1(indexOfKey);
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onStateMutated() {
        MutableIndexedListSet mutableIndexedListSet = this.onAppOpModeChangedListeners;
        int size = mutableIndexedListSet.list.size();
        for (int i = 0; i < size; i++) {
            AppOpService.OnAppIdAppOpModeChangedListener onAppIdAppOpModeChangedListener =
                    (AppOpService.OnAppIdAppOpModeChangedListener)
                            mutableIndexedListSet.list.get(i);
            ArraySet arraySet = AppOpService.this.listeners;
            LongSparseArray longSparseArray = onAppIdAppOpModeChangedListener.pendingChanges;
            int size2 = longSparseArray.size();
            for (int i2 = 0; i2 < size2; i2++) {
                long keyAt = longSparseArray.keyAt(i2);
                ((Number) longSparseArray.valueAt(i2)).intValue();
                int size3 = arraySet.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    ((AppOpsService.AnonymousClass2) arraySet.valueAt(i3))
                            .onUidModeChanged(
                                    IntPair.first(keyAt), IntPair.second(keyAt), "default:0");
                }
            }
            onAppIdAppOpModeChangedListener.pendingChanges.clear();
        }
    }

    public final boolean setAppOpMode(
            MutateStateScope mutateStateScope, int i, int i2, String str, int i3) {
        if (!mutateStateScope.newState.getUserStates().array.contains(i2)) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i2, "Unable to set app op mode for missing user ", "AppIdAppOpPolicy");
            return false;
        }
        int opToDefaultMode = AppOpsManager.opToDefaultMode(str);
        Immutable immutable = mutateStateScope.newState.getUserStates().get(i2);
        Intrinsics.checkNotNull(immutable);
        if (((Number)
                                IndexedMapExtensionsKt.getWithDefault(
                                        (IndexedMap)
                                                ((MutableUserState) immutable)
                                                        .getAppIdAppOpModes()
                                                        .get(i),
                                        str,
                                        Integer.valueOf(opToDefaultMode)))
                        .intValue()
                == i3) {
            return false;
        }
        MutableUserState mutateUserState = mutateStateScope.newState.mutateUserState(i2, 1);
        Intrinsics.checkNotNull(mutateUserState);
        MutableIntReferenceMap mutableIntReferenceMap =
                (MutableIntReferenceMap) mutateUserState.appIdAppOpModesReference.mutate();
        Immutable mutate = mutableIntReferenceMap.mutate(i);
        if (mutate == null) {
            mutate = new MutableIndexedMap();
            mutableIntReferenceMap.put(i, mutate);
        }
        MutableIndexedMap mutableIndexedMap = (MutableIndexedMap) mutate;
        IndexedMapExtensionsKt.putWithDefault(
                mutableIndexedMap, str, Integer.valueOf(i3), Integer.valueOf(opToDefaultMode));
        if (mutableIndexedMap.map.isEmpty()) {
            IntReferenceMapExtensionsKt.minusAssign(mutableIntReferenceMap, i);
        }
        MutableIndexedListSet mutableIndexedListSet = this.onAppOpModeChangedListeners;
        int size = mutableIndexedListSet.list.size();
        for (int i4 = 0; i4 < size; i4++) {
            AppOpService.OnAppIdAppOpModeChangedListener onAppIdAppOpModeChangedListener =
                    (AppOpService.OnAppIdAppOpModeChangedListener)
                            mutableIndexedListSet.list.get(i4);
            onAppIdAppOpModeChangedListener.getClass();
            onAppIdAppOpModeChangedListener.pendingChanges.put(
                    IntPair.of(UserHandle.getUid(i2, i), AppOpsManager.strOpToOp(str)),
                    Integer.valueOf(i3));
        }
        return true;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void upgradePackageState(
            MutateStateScope mutateStateScope, PackageState packageState, int i, int i2) {
        AppIdAppOpPolicy appIdAppOpPolicy = this.upgrade.policy;
        if (i2 <= 2) {
            int appId = packageState.getAppId();
            appIdAppOpPolicy.getClass();
            appIdAppOpPolicy.setAppOpMode(
                    mutateStateScope,
                    packageState.getAppId(),
                    i,
                    "android:run_any_in_background",
                    getAppOpMode(mutateStateScope, appId, i, "android:run_in_background"));
        }
        if (i2 <= 13) {
            String opToPermission = AppOpsManager.opToPermission(107);
            AndroidPackage androidPackage = packageState.getAndroidPackage();
            Intrinsics.checkNotNull(androidPackage);
            if (androidPackage.getRequestedPermissions().contains(opToPermission)) {
                int appId2 = packageState.getAppId();
                appIdAppOpPolicy.getClass();
                if (getAppOpMode(mutateStateScope, appId2, i, "android:schedule_exact_alarm")
                        == AppOpsManager.opToDefaultMode(107)) {
                    appIdAppOpPolicy.setAppOpMode(
                            mutateStateScope,
                            packageState.getAppId(),
                            i,
                            "android:schedule_exact_alarm",
                            0);
                }
            }
        }
    }
}
