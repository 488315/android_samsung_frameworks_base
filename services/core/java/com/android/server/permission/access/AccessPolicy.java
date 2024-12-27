package com.android.server.permission.access;

import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.appop.AppIdAppOpPolicy;
import com.android.server.permission.access.appop.PackageAppOpPolicy;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IntMap;
import com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableIntSet;
import com.android.server.permission.access.permission.AppIdPermissionPolicy;
import com.android.server.permission.access.permission.DevicePermissionPolicy;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.pkg.PackageState;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AccessPolicy {
    public final IndexedMap schemePolicies;

    public AccessPolicy() {
        MutableIndexedMap mutableIndexedMap = new MutableIndexedMap();
        _init_$lambda$1$addPolicy(mutableIndexedMap, new AppIdPermissionPolicy());
        _init_$lambda$1$addPolicy(mutableIndexedMap, new DevicePermissionPolicy());
        _init_$lambda$1$addPolicy(mutableIndexedMap, new AppIdAppOpPolicy());
        _init_$lambda$1$addPolicy(mutableIndexedMap, new PackageAppOpPolicy());
        this.schemePolicies = mutableIndexedMap;
    }

    public static final void _init_$lambda$1$addPolicy(MutableIndexedMap mutableIndexedMap, SchemePolicy schemePolicy) {
        String subjectScheme = schemePolicy.getSubjectScheme();
        Object obj = mutableIndexedMap.map.get(subjectScheme);
        if (obj == null) {
            obj = new MutableIndexedMap();
            mutableIndexedMap.put(subjectScheme, obj);
        }
        ((MutableIndexedMap) obj).put(schemePolicy.getObjectScheme(), schemePolicy);
    }

    public final void onPackageAdded(MutateStateScope mutateStateScope, Map map, Map map2, IntMap intMap, String str) {
        boolean z;
        PackageState packageState = (PackageState) map.get(str);
        if (packageState == null) {
            throw new IllegalStateException(XmlUtils$$ExternalSyntheticOutline0.m("Added package ", str, " isn't found in packageStates in onPackageAdded()").toString());
        }
        int appId = packageState.getAppId();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableExternalState mutateExternalState = mutableAccessState.mutateExternalState();
        mutateExternalState.packageStates = map;
        mutateExternalState.disabledSystemPackageStates = map2;
        MutableIntReferenceMap mutableIntReferenceMap = (MutableIntReferenceMap) mutateExternalState.appIdPackageNamesReference.mutate();
        Immutable mutate = mutableIntReferenceMap.mutate(appId);
        if (mutate != null) {
            z = false;
        } else {
            mutate = new MutableIndexedListSet();
            mutableIntReferenceMap.put(appId, mutate);
            z = true;
        }
        ((MutableIndexedListSet) mutate).add(str);
        mutateExternalState.knownPackages = intMap;
        IndexedMap indexedMap = this.schemePolicies;
        if (z) {
            int size = indexedMap.map.size();
            for (int i = 0; i < size; i++) {
                Object keyAt = indexedMap.map.keyAt(i);
                IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i);
                int size2 = indexedMap2.map.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ((SchemePolicy) indexedMap2.map.valueAt(i2)).getClass();
                }
            }
        }
        int size3 = indexedMap.map.size();
        for (int i3 = 0; i3 < size3; i3++) {
            Object keyAt2 = indexedMap.map.keyAt(i3);
            IndexedMap indexedMap3 = (IndexedMap) indexedMap.map.valueAt(i3);
            int size4 = indexedMap3.map.size();
            for (int i4 = 0; i4 < size4; i4++) {
                ((SchemePolicy) indexedMap3.map.valueAt(i4)).onPackageAdded(mutateStateScope, packageState);
            }
        }
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size5 = userStates.array.size();
        for (int i5 = 0; i5 < size5; i5++) {
            int keyAt3 = userStates.array.keyAt(i5);
            upgradePackageVersion(mutateStateScope, packageState, keyAt3);
        }
    }

    public final void onPackageInstalled(MutateStateScope mutateStateScope, Map map, Map map2, IntMap intMap, String str, int i) {
        MutableExternalState mutateExternalState = mutateStateScope.newState.mutateExternalState();
        mutateExternalState.packageStates = map;
        mutateExternalState.disabledSystemPackageStates = map2;
        mutateExternalState.knownPackages = intMap;
        PackageState packageState = (PackageState) map.get(str);
        if (packageState == null) {
            throw new IllegalStateException(XmlUtils$$ExternalSyntheticOutline0.m("Installed package ", str, " isn't found in packageStates in onPackageInstalled()").toString());
        }
        IndexedMap indexedMap = this.schemePolicies;
        int size = indexedMap.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = indexedMap.map.keyAt(i2);
            IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
            int size2 = indexedMap2.map.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) indexedMap2.map.valueAt(i3)).onPackageInstalled(mutateStateScope, packageState, i);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPackageRemoved(com.android.server.permission.access.MutateStateScope r8, java.util.Map r9, java.util.Map r10, com.android.server.permission.access.immutable.IntMap r11, java.lang.String r12, int r13) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.AccessPolicy.onPackageRemoved(com.android.server.permission.access.MutateStateScope, java.util.Map, java.util.Map, com.android.server.permission.access.immutable.IntMap, java.lang.String, int):void");
    }

    public final void onPackageUninstalled(MutateStateScope mutateStateScope, Map map, Map map2, IntMap intMap, String str, int i, int i2) {
        MutableExternalState mutateExternalState = mutateStateScope.newState.mutateExternalState();
        mutateExternalState.packageStates = map;
        mutateExternalState.disabledSystemPackageStates = map2;
        mutateExternalState.knownPackages = intMap;
        IndexedMap indexedMap = this.schemePolicies;
        int size = indexedMap.map.size();
        for (int i3 = 0; i3 < size; i3++) {
            Object keyAt = indexedMap.map.keyAt(i3);
            IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i3);
            int size2 = indexedMap2.map.size();
            for (int i4 = 0; i4 < size2; i4++) {
                ((SchemePolicy) indexedMap2.map.valueAt(i4)).onPackageUninstalled(mutateStateScope, str, i2);
            }
        }
    }

    public final void onStorageVolumeMounted(MutateStateScope mutateStateScope, Map map, Map map2, IntMap intMap, String str, List list, boolean z) {
        IndexedMap indexedMap;
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableExternalState mutateExternalState = mutableAccessState.mutateExternalState();
        mutateExternalState.packageStates = map;
        mutateExternalState.disabledSystemPackageStates = map2;
        for (Map.Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey();
            PackageState packageState = (PackageState) entry.getValue();
            if (!packageState.isApex() && Intrinsics.areEqual(packageState.getVolumeUuid(), str)) {
                if (packageState.getAndroidPackage() != null && !list.contains(str2)) {
                    throw new IllegalStateException(XmlUtils$$ExternalSyntheticOutline0.m("Package ", str2, " on storage volume ", str, " didn't receive onPackageAdded() before onStorageVolumeMounted()").toString());
                }
                int appId = packageState.getAppId();
                MutableIntReferenceMap mutableIntReferenceMap = (MutableIntReferenceMap) mutateExternalState.appIdPackageNamesReference.mutate();
                Immutable mutate = mutableIntReferenceMap.mutate(appId);
                if (mutate == null) {
                    sparseBooleanArray.put(appId, true);
                    mutate = new MutableIndexedListSet();
                    mutableIntReferenceMap.put(appId, mutate);
                }
                ((MutableIndexedListSet) mutate).add(str2);
            }
        }
        mutateExternalState.knownPackages = intMap;
        int size = sparseBooleanArray.size();
        int i = 0;
        while (true) {
            indexedMap = this.schemePolicies;
            if (i >= size) {
                break;
            }
            sparseBooleanArray.keyAt(i);
            int size2 = indexedMap.map.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Object keyAt = indexedMap.map.keyAt(i2);
                IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
                int size3 = indexedMap2.map.size();
                int i3 = 0;
                while (i3 < size3) {
                    ((SchemePolicy) indexedMap2.map.valueAt(i3)).getClass();
                    i3++;
                    sparseBooleanArray = sparseBooleanArray;
                }
            }
            i++;
        }
        int size4 = indexedMap.map.size();
        for (int i4 = 0; i4 < size4; i4++) {
            Object keyAt2 = indexedMap.map.keyAt(i4);
            IndexedMap indexedMap3 = (IndexedMap) indexedMap.map.valueAt(i4);
            int size5 = indexedMap3.map.size();
            for (int i5 = 0; i5 < size5; i5++) {
                ((SchemePolicy) indexedMap3.map.valueAt(i5)).onStorageVolumeMounted(mutateStateScope, list, z);
            }
        }
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            PackageState packageState2 = (PackageState) ((Map.Entry) it.next()).getValue();
            if (!packageState2.isApex() && Intrinsics.areEqual(packageState2.getVolumeUuid(), str)) {
                MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
                int size6 = userStates.array.size();
                for (int i6 = 0; i6 < size6; i6++) {
                    int keyAt3 = userStates.array.keyAt(i6);
                    upgradePackageVersion(mutateStateScope, packageState2, keyAt3);
                }
            }
        }
    }

    public final void onUserAdded(MutateStateScope mutateStateScope, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        ((MutableIntSet) mutableAccessState.mutateExternalState().userIdsReference.mutate()).array.put(i, true);
        IntReferenceMapExtensionsKt.set((MutableIntReferenceMap) mutableAccessState.userStatesReference.mutate(), i, new MutableUserState());
        IndexedMap indexedMap = this.schemePolicies;
        int size = indexedMap.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = indexedMap.map.keyAt(i2);
            IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
            int size2 = indexedMap2.map.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) indexedMap2.map.valueAt(i3)).onUserAdded(mutateStateScope, i);
            }
        }
        Iterator it = mutableAccessState.getExternalState().packageStates.entrySet().iterator();
        while (it.hasNext()) {
            PackageState packageState = (PackageState) ((Map.Entry) it.next()).getValue();
            if (!packageState.isApex()) {
                upgradePackageVersion(mutateStateScope, packageState, i);
            }
        }
    }

    public final void onUserRemoved(MutateStateScope mutateStateScope, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        ((MutableIntSet) mutableAccessState.mutateExternalState().userIdsReference.mutate()).array.delete(i);
        IntReferenceMapExtensionsKt.minusAssign((MutableIntReferenceMap) mutableAccessState.userStatesReference.mutate(), i);
        IndexedMap indexedMap = this.schemePolicies;
        int size = indexedMap.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = indexedMap.map.keyAt(i2);
            IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
            int size2 = indexedMap2.map.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) indexedMap2.map.valueAt(i3)).getClass();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x0160, code lost:
    
        r0 = r17.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0165, code lost:
    
        if (r0 == 1) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0167, code lost:
    
        if (r0 == 2) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0169, code lost:
    
        if (r0 == 3) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00fb, code lost:
    
        r3 = r17.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0100, code lost:
    
        if (r3 == 1) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0102, code lost:
    
        if (r3 == 2) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0104, code lost:
    
        if (r3 == 3) goto L133;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseSystemState(com.android.modules.utils.BinaryXmlPullParser r17, com.android.server.permission.access.MutableAccessState r18) {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.AccessPolicy.parseSystemState(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:154:0x024e, code lost:
    
        r3 = r19.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0253, code lost:
    
        if (r3 == 1) goto L206;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0255, code lost:
    
        if (r3 == r5) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0257, code lost:
    
        if (r3 == 3) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0259, code lost:
    
        r5 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x007f, code lost:
    
        r16 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x02c5, code lost:
    
        r3 = r19.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x02ca, code lost:
    
        if (r3 == 1) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x02cc, code lost:
    
        if (r3 == r6) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x02ce, code lost:
    
        if (r3 == 3) goto L197;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x02d0, code lost:
    
        r6 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x014d, code lost:
    
        r3 = r19.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0152, code lost:
    
        if (r3 == 1) goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0155, code lost:
    
        if (r3 == 2) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0157, code lost:
    
        if (r3 == 3) goto L218;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseUserState(com.android.modules.utils.BinaryXmlPullParser r19, com.android.server.permission.access.MutableAccessState r20, int r21) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.AccessPolicy.parseUserState(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, int):void");
    }

    public final void serializeSystemState(BinaryXmlSerializer binaryXmlSerializer, AccessState accessState) {
        binaryXmlSerializer.startTag((String) null, "access");
        IndexedMap indexedMap = this.schemePolicies;
        int size = indexedMap.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = indexedMap.map.keyAt(i);
            IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i);
            int size2 = indexedMap2.map.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((SchemePolicy) indexedMap2.map.valueAt(i2)).serializeSystemState(binaryXmlSerializer, accessState);
            }
        }
        binaryXmlSerializer.endTag((String) null, "access");
    }

    public final void serializeUserState(BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        binaryXmlSerializer.startTag((String) null, "access");
        Immutable immutable = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        IndexedMap indexedMap = (IndexedMap) ((MutableUserState) immutable).packageVersionsReference.immutable;
        binaryXmlSerializer.startTag((String) null, "package-versions");
        int size = indexedMap.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = indexedMap.map.keyAt(i2);
            int intValue = ((Number) indexedMap.map.valueAt(i2)).intValue();
            binaryXmlSerializer.startTag((String) null, "package");
            binaryXmlSerializer.attributeInterned((String) null, "name", (String) keyAt);
            binaryXmlSerializer.attributeInt((String) null, "version", intValue);
            binaryXmlSerializer.endTag((String) null, "package");
        }
        binaryXmlSerializer.endTag((String) null, "package-versions");
        Immutable immutable2 = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable2);
        String str = ((MutableUserState) immutable2).defaultPermissionGrantFingerprint;
        if (str != null) {
            binaryXmlSerializer.startTag((String) null, "default-permission-grant");
            binaryXmlSerializer.attributeInterned((String) null, "fingerprint", str);
            binaryXmlSerializer.endTag((String) null, "default-permission-grant");
        }
        IndexedMap indexedMap2 = this.schemePolicies;
        int size2 = indexedMap2.map.size();
        for (int i3 = 0; i3 < size2; i3++) {
            Object keyAt2 = indexedMap2.map.keyAt(i3);
            IndexedMap indexedMap3 = (IndexedMap) indexedMap2.map.valueAt(i3);
            int size3 = indexedMap3.map.size();
            for (int i4 = 0; i4 < size3; i4++) {
                ((SchemePolicy) indexedMap3.map.valueAt(i4)).serializeUserState(binaryXmlSerializer, accessState, i);
            }
        }
        binaryXmlSerializer.endTag((String) null, "access");
    }

    public final void upgradePackageVersion(MutateStateScope mutateStateScope, PackageState packageState, int i) {
        if (packageState.getAndroidPackage() == null) {
            return;
        }
        String packageName = packageState.getPackageName();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        Immutable immutable = mutableAccessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        Integer num = (Integer) ((IndexedMap) ((MutableUserState) immutable).packageVersionsReference.immutable).map.get(packageName);
        if (num == null) {
            MutableUserState mutateUserState = mutableAccessState.mutateUserState(i, 1);
            Intrinsics.checkNotNull(mutateUserState);
            mutateUserState.mutatePackageVersions().put(packageName, 15);
            return;
        }
        if (num.intValue() >= 15) {
            if (num.intValue() != 15) {
                Slog.w("AccessPolicy", "Unexpected version " + num + " for package " + packageName + ",latest version is 15");
                return;
            }
            return;
        }
        IndexedMap indexedMap = this.schemePolicies;
        int size = indexedMap.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = indexedMap.map.keyAt(i2);
            IndexedMap indexedMap2 = (IndexedMap) indexedMap.map.valueAt(i2);
            int size2 = indexedMap2.map.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) indexedMap2.map.valueAt(i3)).upgradePackageState(mutateStateScope, packageState, i, num.intValue());
            }
        }
        MutableUserState mutateUserState2 = mutableAccessState.mutateUserState(i, 1);
        Intrinsics.checkNotNull(mutateUserState2);
        mutateUserState2.mutatePackageVersions().put(packageName, 15);
    }
}
