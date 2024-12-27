package com.android.server.permission.access.permission;

import android.util.ArraySet;

import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedListSet;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IndexedMapExtensionsKt;
import com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedReferenceMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableReference;
import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DevicePermissionPolicy extends SchemePolicy {
    public final DevicePermissionPersistence persistence = new DevicePermissionPersistence();
    public volatile MutableIndexedListSet listeners = new MutableIndexedListSet();
    public final Object listenersLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnDevicePermissionFlagsChangedListener {
        void onDevicePermissionFlagsChanged(int i, int i2, int i3, String str, String str2, int i4);

        void onStateMutated();
    }

    public static int getPermissionFlags(
            int i, int i2, GetStateScope getStateScope, String str, String str2) {
        MutableIntReferenceMap appIdDevicePermissionFlags;
        MutableIndexedReferenceMap mutableIndexedReferenceMap;
        IndexedMap indexedMap;
        MutableUserState mutableUserState =
                (MutableUserState) getStateScope.state.getUserStates().get(i2);
        if (mutableUserState == null
                || (appIdDevicePermissionFlags = mutableUserState.getAppIdDevicePermissionFlags())
                        == null
                || (mutableIndexedReferenceMap =
                                (MutableIndexedReferenceMap) appIdDevicePermissionFlags.get(i))
                        == null
                || (indexedMap = (IndexedMap) mutableIndexedReferenceMap.get(str)) == null) {
            return 0;
        }
        return ((Number) IndexedMapExtensionsKt.getWithDefault(indexedMap, str2, 0)).intValue();
    }

    public static void onDeviceIdRemoved(MutateStateScope mutateStateScope, String str) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i = 0; i < size; i++) {
            int keyAt = userStates.array.keyAt(i);
            MutableIntReferenceMap appIdDevicePermissionFlags =
                    ((MutableUserState) userStates.valueAt(i)).getAppIdDevicePermissionFlags();
            for (int size2 = appIdDevicePermissionFlags.array.size() - 1; -1 < size2; size2--) {
                int keyAt2 = appIdDevicePermissionFlags.array.keyAt(size2);
                MutableUserState mutateUserState = mutableAccessState.mutateUserState(keyAt, 1);
                Intrinsics.checkNotNull(mutateUserState);
                MutableIndexedReferenceMap mutableIndexedReferenceMap =
                        (MutableIndexedReferenceMap)
                                ((MutableIntReferenceMap)
                                                mutateUserState.appIdDevicePermissionFlagsReference
                                                        .mutate())
                                        .mutate(keyAt2);
                if (mutableIndexedReferenceMap != null) {
                    mutableIndexedReferenceMap.remove$1(str);
                }
            }
        }
    }

    public static void trimDevicePermissionStates(MutateStateScope mutateStateScope, Set set) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i = 0; i < size; i++) {
            int keyAt = userStates.array.keyAt(i);
            MutableIntReferenceMap appIdDevicePermissionFlags =
                    ((MutableUserState) userStates.valueAt(i)).getAppIdDevicePermissionFlags();
            for (int size2 = appIdDevicePermissionFlags.array.size() - 1; -1 < size2; size2--) {
                int keyAt2 = appIdDevicePermissionFlags.array.keyAt(size2);
                MutableUserState mutateUserState = mutableAccessState.mutateUserState(keyAt, 1);
                Intrinsics.checkNotNull(mutateUserState);
                MutableIndexedReferenceMap mutableIndexedReferenceMap =
                        (MutableIndexedReferenceMap)
                                ((MutableIntReferenceMap)
                                                mutateUserState.appIdDevicePermissionFlagsReference
                                                        .mutate())
                                        .mutate(keyAt2);
                if (mutableIndexedReferenceMap != null) {
                    for (int size3 = mutableIndexedReferenceMap.map.size() - 1;
                            -1 < size3;
                            size3--) {
                        Object keyAt3 = mutableIndexedReferenceMap.map.keyAt(size3);
                        String str = (String) keyAt3;
                        if (!set.contains(str)) {
                            mutableIndexedReferenceMap.remove$1(str);
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getObjectScheme() {
        return "device-permission";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getSubjectScheme() {
        return "uid";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onAppIdRemoved(MutateStateScope mutateStateScope, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            userStates.keyAt(i2);
            if (((MutableUserState) userStates.valueAt(i2))
                    .getAppIdDevicePermissionFlags()
                    .array
                    .contains(i)) {
                IntReferenceMapExtensionsKt.minusAssign(
                        (MutableIntReferenceMap)
                                MutableAccessState.mutateUserStateAt$default(mutableAccessState, i2)
                                        .appIdDevicePermissionFlagsReference
                                        .mutate(),
                        i);
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageAdded(MutateStateScope mutateStateScope, PackageState packageState) {
        trimPermissionStates$1(mutateStateScope, packageState.getAppId());
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageRemoved(MutateStateScope mutateStateScope, String str, int i) {
        if (mutateStateScope.newState.getExternalState().getAppIdPackageNames().array.contains(i)) {
            trimPermissionStates$1(mutateStateScope, i);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageUninstalled(MutateStateScope mutateStateScope, String str, int i) {
        resetRuntimePermissions(mutateStateScope, str, i);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onStateMutated() {
        MutableIndexedListSet mutableIndexedListSet = this.listeners;
        int size = mutableIndexedListSet.list.size();
        for (int i = 0; i < size; i++) {
            ((OnDevicePermissionFlagsChangedListener) mutableIndexedListSet.list.get(i))
                    .onStateMutated();
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onStorageVolumeMounted(
            MutateStateScope mutateStateScope, List list, boolean z) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            PackageState packageState =
                    (PackageState)
                            mutateStateScope
                                    .newState
                                    .getExternalState()
                                    .packageStates
                                    .get((String) list.get(i));
            if (packageState != null) {
                trimPermissionStates$1(mutateStateScope, packageState.getAppId());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x0116, code lost:

       r17 = r15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x01f0, code lost:

       r2 = r19.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01f5, code lost:

       if (r2 == 1) goto L205;
    */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x01f7, code lost:

       if (r2 == r7) goto L206;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x01f9, code lost:

       if (r2 == 3) goto L207;
    */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x01fb, code lost:

       r7 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0252, code lost:

       r2 = r19.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0257, code lost:

       if (r2 == 1) goto L195;
    */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x0259, code lost:

       if (r2 == r6) goto L196;
    */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x025b, code lost:

       if (r2 == 3) goto L197;
    */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x025d, code lost:

       r6 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x00ab, code lost:

       r6 = 3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x028b, code lost:

       r0 = r3.array.size() - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x0294, code lost:

       if ((-1) >= r0) goto L254;
    */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x0296, code lost:

       r2 = r3.array.keyAt(r0);
       r4 = (com.android.server.permission.access.immutable.MutableIndexedReferenceMap) r3.valueAt(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x02b0, code lost:

       if (r20.getExternalState().getAppIdPackageNames().array.contains(r2) != false) goto L175;
    */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x02b2, code lost:

       com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0.m(r2, "Dropping unknown app ID ", " when parsing permission state", "DevicePermissionPersistence");
       r3.removeAt$1(r0);
       r1.requestWriteMode(1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x02c0, code lost:

       r0 = r0 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:240:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x018e, code lost:

       r2 = r19.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0193, code lost:

       if (r2 == 1) goto L215;
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0196, code lost:

       if (r2 == 2) goto L216;
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0198, code lost:

       if (r2 == 3) goto L217;
    */
    @Override // com.android.server.permission.access.SchemePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseUserState(
            com.android.modules.utils.BinaryXmlPullParser r19,
            com.android.server.permission.access.MutableAccessState r20,
            int r21) {
        /*
            Method dump skipped, instructions count: 708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.DevicePermissionPolicy.parseUserState(com.android.modules.utils.BinaryXmlPullParser,"
                    + " com.android.server.permission.access.MutableAccessState, int):void");
    }

    public final void resetRuntimePermissions(
            MutateStateScope mutateStateScope, String str, int i) {
        AndroidPackage androidPackage;
        MutableIndexedReferenceMap mutableIndexedReferenceMap;
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        PackageState packageState =
                (PackageState) mutableAccessState.getExternalState().packageStates.get(str);
        if (packageState == null || (androidPackage = packageState.getAndroidPackage()) == null) {
            return;
        }
        int appId = packageState.getAppId();
        MutableUserState mutableUserState =
                (MutableUserState) mutableAccessState.getUserStates().get(i);
        if (mutableUserState == null
                || (mutableIndexedReferenceMap =
                                (MutableIndexedReferenceMap)
                                        mutableUserState.getAppIdDevicePermissionFlags().get(appId))
                        == null) {
            return;
        }
        for (String str2 : androidPackage.getRequestedPermissions()) {
            Immutable immutable =
                    mutableAccessState.getExternalState().getAppIdPackageNames().get(appId);
            Intrinsics.checkNotNull(immutable);
            IndexedListSet indexedListSet = (IndexedListSet) immutable;
            int size = indexedListSet.list.size();
            int i2 = 0;
            while (true) {
                if (i2 < size) {
                    Object obj =
                            mutableAccessState
                                    .getExternalState()
                                    .packageStates
                                    .get((String) indexedListSet.list.get(i2));
                    Intrinsics.checkNotNull(obj);
                    PackageState packageState2 = (PackageState) obj;
                    if (packageState2.getAndroidPackage() != null
                            && !Intrinsics.areEqual(packageState2.getPackageName(), str)) {
                        AndroidPackage androidPackage2 = packageState2.getAndroidPackage();
                        Intrinsics.checkNotNull(androidPackage2);
                        if (androidPackage2.getRequestedPermissions().contains(str2)) {
                            break;
                        }
                    }
                    i2++;
                } else {
                    int size2 = mutableIndexedReferenceMap.map.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        Object keyAt = mutableIndexedReferenceMap.map.keyAt(i3);
                        setPermissionFlags(appId, i, 0, mutateStateScope, (String) keyAt, str2);
                    }
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        this.persistence.getClass();
        Immutable immutable = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        MutableIntReferenceMap appIdDevicePermissionFlags =
                ((MutableUserState) immutable).getAppIdDevicePermissionFlags();
        String str = null;
        binaryXmlSerializer.startTag((String) null, "app-id-device-permissions");
        int size = appIdDevicePermissionFlags.array.size();
        int i2 = 0;
        while (i2 < size) {
            int keyAt = appIdDevicePermissionFlags.array.keyAt(i2);
            MutableIndexedReferenceMap mutableIndexedReferenceMap =
                    (MutableIndexedReferenceMap) appIdDevicePermissionFlags.valueAt(i2);
            binaryXmlSerializer.startTag(str, "app-id");
            binaryXmlSerializer.attributeInt(str, "id", keyAt);
            int size2 = mutableIndexedReferenceMap.map.size();
            int i3 = 0;
            while (i3 < size2) {
                Object keyAt2 = mutableIndexedReferenceMap.map.keyAt(i3);
                IndexedMap indexedMap = (IndexedMap) mutableIndexedReferenceMap.valueAt(i3);
                binaryXmlSerializer.startTag(str, "device");
                binaryXmlSerializer.attributeInterned(str, "id", (String) keyAt2);
                int size3 = indexedMap.map.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object keyAt3 = indexedMap.map.keyAt(i4);
                    int intValue = ((Number) indexedMap.map.valueAt(i4)).intValue();
                    MutableIntReferenceMap mutableIntReferenceMap = appIdDevicePermissionFlags;
                    int i5 = size;
                    binaryXmlSerializer.startTag((String) null, "permission");
                    int i6 = size2;
                    binaryXmlSerializer.attributeInterned((String) null, "name", (String) keyAt3);
                    if (IntExtensionsKt.hasBits(intValue, 2097152)) {
                        intValue &= -17;
                    }
                    binaryXmlSerializer.attributeInt((String) null, "flags", intValue);
                    binaryXmlSerializer.endTag((String) null, "permission");
                    i4++;
                    size2 = i6;
                    appIdDevicePermissionFlags = mutableIntReferenceMap;
                    str = null;
                    size = i5;
                }
                int i7 = size;
                String str2 = str;
                binaryXmlSerializer.endTag(str2, "device");
                i3++;
                size2 = size2;
                appIdDevicePermissionFlags = appIdDevicePermissionFlags;
                str = str2;
                size = i7;
            }
            int i8 = size;
            String str3 = str;
            binaryXmlSerializer.endTag(str3, "app-id");
            i2++;
            appIdDevicePermissionFlags = appIdDevicePermissionFlags;
            str = str3;
            size = i8;
        }
        binaryXmlSerializer.endTag(str, "app-id-device-permissions");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.server.permission.access.immutable.Immutable] */
    public final boolean setPermissionFlags(
            int i, int i2, int i3, MutateStateScope mutateStateScope, String str, String str2) {
        if (!mutateStateScope.newState.getUserStates().array.contains(i2)) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i2,
                    "Unable to update permission flags for missing user ",
                    "DevicePermissionPolicy");
            return false;
        }
        Immutable immutable = mutateStateScope.newState.getUserStates().get(i2);
        Intrinsics.checkNotNull(immutable);
        MutableIndexedReferenceMap mutableIndexedReferenceMap =
                (MutableIndexedReferenceMap)
                        ((MutableUserState) immutable).getAppIdDevicePermissionFlags().get(i);
        int intValue =
                ((Number)
                                IndexedMapExtensionsKt.getWithDefault(
                                        mutableIndexedReferenceMap != null
                                                ? (IndexedMap) mutableIndexedReferenceMap.get(str)
                                                : null,
                                        str2,
                                        0))
                        .intValue();
        if (intValue == i3) {
            return false;
        }
        MutableUserState mutateUserState = mutateStateScope.newState.mutateUserState(i2, 1);
        Intrinsics.checkNotNull(mutateUserState);
        MutableIntReferenceMap mutableIntReferenceMap =
                (MutableIntReferenceMap)
                        mutateUserState.appIdDevicePermissionFlagsReference.mutate();
        Immutable mutate = mutableIntReferenceMap.mutate(i);
        if (mutate == null) {
            mutate = new MutableIndexedReferenceMap();
            mutableIntReferenceMap.put(i, mutate);
        }
        MutableIndexedReferenceMap mutableIndexedReferenceMap2 =
                (MutableIndexedReferenceMap) mutate;
        MutableReference mutableReference =
                (MutableReference) mutableIndexedReferenceMap2.map.get(str);
        MutableIndexedMap mutate2 = mutableReference != null ? mutableReference.mutate() : null;
        if (mutate2 == null) {
            mutate2 = new MutableIndexedMap();
            mutableIndexedReferenceMap2.put(str, mutate2);
        }
        MutableIndexedMap mutableIndexedMap = mutate2;
        IndexedMapExtensionsKt.putWithDefault(mutableIndexedMap, str2, Integer.valueOf(i3), 0);
        if (mutableIndexedMap.map.isEmpty()) {
            mutableIndexedReferenceMap2.remove$1(str);
            if (mutableIndexedReferenceMap2.map.isEmpty()) {
                IntReferenceMapExtensionsKt.minusAssign(mutableIntReferenceMap, i);
            }
        }
        MutableIndexedListSet mutableIndexedListSet = this.listeners;
        int size = mutableIndexedListSet.list.size();
        for (int i4 = 0; i4 < size; i4++) {
            ((OnDevicePermissionFlagsChangedListener) mutableIndexedListSet.list.get(i4))
                    .onDevicePermissionFlagsChanged(i, i2, intValue, str, str2, i3);
        }
        return true;
    }

    public final void trimPermissionStates$1(MutateStateScope mutateStateScope, int i) {
        int i2;
        IndexedMap indexedMap;
        int i3;
        ArraySet arraySet = new ArraySet();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        Immutable immutable = mutableAccessState.getExternalState().getAppIdPackageNames().get(i);
        Intrinsics.checkNotNull(immutable);
        IndexedListSet indexedListSet = (IndexedListSet) immutable;
        int size = indexedListSet.list.size();
        for (int i4 = 0; i4 < size; i4++) {
            Object obj =
                    mutableAccessState
                            .getExternalState()
                            .packageStates
                            .get((String) indexedListSet.list.get(i4));
            Intrinsics.checkNotNull(obj);
            PackageState packageState = (PackageState) obj;
            if (packageState.getAndroidPackage() != null) {
                AndroidPackage androidPackage = packageState.getAndroidPackage();
                Intrinsics.checkNotNull(androidPackage);
                Iterator it = androidPackage.getRequestedPermissions().iterator();
                while (it.hasNext()) {
                    arraySet.add(it.next());
                }
            }
        }
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size2 = userStates.array.size();
        for (int i5 = 0; i5 < size2; i5++) {
            int keyAt = userStates.array.keyAt(i5);
            MutableIndexedReferenceMap mutableIndexedReferenceMap =
                    (MutableIndexedReferenceMap)
                            ((MutableUserState) userStates.valueAt(i5))
                                    .getAppIdDevicePermissionFlags()
                                    .get(i);
            if (mutableIndexedReferenceMap != null) {
                int size3 = mutableIndexedReferenceMap.map.size() - 1;
                while (true) {
                    int i6 = -1;
                    if (-1 < size3) {
                        Object keyAt2 = mutableIndexedReferenceMap.map.keyAt(size3);
                        IndexedMap indexedMap2 =
                                (IndexedMap) mutableIndexedReferenceMap.valueAt(size3);
                        String str = (String) keyAt2;
                        int size4 = indexedMap2.map.size() - 1;
                        while (i6 < size4) {
                            Object keyAt3 = indexedMap2.map.keyAt(size4);
                            ((Number) indexedMap2.map.valueAt(size4)).intValue();
                            String str2 = (String) keyAt3;
                            if (arraySet.contains(str2)) {
                                i2 = size4;
                                indexedMap = indexedMap2;
                                i3 = i6;
                            } else {
                                i2 = size4;
                                indexedMap = indexedMap2;
                                i3 = i6;
                                setPermissionFlags(i, keyAt, 0, mutateStateScope, str, str2);
                            }
                            size4 = i2 - 1;
                            indexedMap2 = indexedMap;
                            i6 = i3;
                        }
                        size3--;
                    }
                }
            }
        }
    }
}
