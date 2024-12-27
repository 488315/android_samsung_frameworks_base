package com.android.server.permission.access.permission;

import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.pm.pkg.component.ParsedPermission;
import com.android.internal.pm.pkg.component.ParsedPermissionGroup;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.permission.persistence.RuntimePermissionsState;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutableAccessState;
import com.android.server.permission.access.MutableExternalState;
import com.android.server.permission.access.MutableSystemState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.SchemePolicy;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedListSet;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.IndexedMapExtensionsKt;
import com.android.server.permission.access.immutable.IndexedSetExtensionsKt;
import com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt;
import com.android.server.permission.access.immutable.MutableIndexedListSet;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedSet;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.access.immutable.MutableIntSet;
import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.access.util.PackageVersionMigration;
import com.android.server.permission.jarjar.kotlin.collections.CollectionsKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$IntRef;
import com.android.server.permission.jarjar.kotlin.text.StringsKt__StringsJVMKt;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.SharedUserSetting;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.permission.LegacyPermissionSettings;
import com.android.server.pm.permission.PermissionMigrationHelper$LegacyPermissionState;
import com.android.server.pm.permission.PermissionMigrationHelperImpl;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageUserState;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.server.pm.PmLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class AppIdPermissionPolicy extends SchemePolicy {
    public volatile boolean isSignaturePermissionAllowlistForceEnforced;
    public static final MutableIndexedSet NO_IMPLICIT_FLAG_PERMISSIONS =
            IndexedSetExtensionsKt.indexedSetOf(
                    "android.permission.ACCESS_MEDIA_LOCATION",
                    "android.permission.ACTIVITY_RECOGNITION",
                    "android.permission.READ_MEDIA_AUDIO",
                    "android.permission.READ_MEDIA_IMAGES",
                    "android.permission.READ_MEDIA_VIDEO",
                    "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
    public static final MutableIndexedSet NEARBY_DEVICES_PERMISSIONS =
            IndexedSetExtensionsKt.indexedSetOf(
                    "android.permission.BLUETOOTH_ADVERTISE",
                    "android.permission.BLUETOOTH_CONNECT",
                    "android.permission.BLUETOOTH_SCAN",
                    "android.permission.NEARBY_WIFI_DEVICES");
    public static final MutableIndexedSet NOTIFICATIONS_PERMISSIONS =
            IndexedSetExtensionsKt.indexedSetOf("android.permission.POST_NOTIFICATIONS");
    public static final MutableIndexedSet STORAGE_AND_MEDIA_PERMISSIONS =
            IndexedSetExtensionsKt.indexedSetOf(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_MEDIA_AUDIO",
                    "android.permission.READ_MEDIA_VIDEO",
                    "android.permission.READ_MEDIA_IMAGES",
                    "android.permission.ACCESS_MEDIA_LOCATION",
                    "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
    public final AppIdPermissionPersistence persistence = new AppIdPermissionPersistence();
    public final AppIdPermissionMigration migration = new AppIdPermissionMigration();
    public final AppIdPermissionUpgrade upgrade = new AppIdPermissionUpgrade(this);
    public volatile MutableIndexedListSet onPermissionFlagsChangedListeners =
            new MutableIndexedListSet();
    public final Object onPermissionFlagsChangedListenersLock = new Object();
    public final MutableIndexedSet privilegedPermissionAllowlistViolations =
            new MutableIndexedSet();

    public interface OnPermissionFlagsChangedListener {
        void onPermissionFlagsChanged(int i, int i2, int i3, int i4, String str);

        void onStateMutated();
    }

    public static void addPermissionGroups(
            MutateStateScope mutateStateScope, PackageState packageState) {
        SparseArray userStates = packageState.getUserStates();
        int size = userStates.size();
        for (int i = 0; i < size; i++) {
            userStates.keyAt(i);
            if (!((PackageUserState) userStates.valueAt(i)).isInstantApp()) {
                AndroidPackage androidPackage = packageState.getAndroidPackage();
                Intrinsics.checkNotNull(androidPackage);
                List permissionGroups = androidPackage.getPermissionGroups();
                int size2 = permissionGroups.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    PermissionGroupInfo generatePermissionGroupInfo =
                            PackageInfoUtils.generatePermissionGroupInfo(
                                    (ParsedPermissionGroup) permissionGroups.get(i2), 128L);
                    Intrinsics.checkNotNull(generatePermissionGroupInfo);
                    String str = generatePermissionGroupInfo.name;
                    MutableAccessState mutableAccessState = mutateStateScope.newState;
                    PermissionGroupInfo permissionGroupInfo =
                            (PermissionGroupInfo)
                                    mutableAccessState
                                            .getSystemState()
                                            .getPermissionGroups()
                                            .map
                                            .get(str);
                    if (permissionGroupInfo != null
                            && !Intrinsics.areEqual(
                                    generatePermissionGroupInfo.packageName,
                                    permissionGroupInfo.packageName)) {
                        String str2 = generatePermissionGroupInfo.packageName;
                        String str3 = permissionGroupInfo.packageName;
                        if (packageState.isSystem()) {
                            PackageState packageState2 =
                                    (PackageState)
                                            mutableAccessState
                                                    .getExternalState()
                                                    .packageStates
                                                    .get(str3);
                            if (packageState2 == null || !packageState2.isSystem()) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                "Overriding permission group ",
                                                str,
                                                " with new declaration in system package ",
                                                str2,
                                                ": originally declared in another package "),
                                        str3,
                                        "AppIdPermissionPolicy");
                            } else {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                "Ignoring permission group ",
                                                str,
                                                " declared in system package ",
                                                str2,
                                                ": already declared in another system package "),
                                        str3,
                                        "AppIdPermissionPolicy");
                            }
                        } else {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                                            "Ignoring permission group ",
                                            str,
                                            " declared in package ",
                                            str2,
                                            ": already declared in another package "),
                                    str3,
                                    "AppIdPermissionPolicy");
                        }
                    }
                    ((MutableIndexedMap)
                                    MutableAccessState.mutateSystemState$default(mutableAccessState)
                                            .permissionGroupsReference
                                            .mutate())
                            .put(str, generatePermissionGroupInfo);
                }
                return;
            }
        }
        PinnerService$$ExternalSyntheticOutline0.m(
                "Ignoring permission groups declared in package ",
                packageState.getPackageName(),
                ": instant apps cannot declare permission groups",
                "AppIdPermissionPolicy");
    }

    public static void adoptPermissions(
            MutateStateScope mutateStateScope,
            PackageState packageState,
            MutableIndexedSet mutableIndexedSet) {
        AndroidPackage androidPackage = packageState.getAndroidPackage();
        Intrinsics.checkNotNull(androidPackage);
        List adoptPermissions = androidPackage.getAdoptPermissions();
        int size = adoptPermissions.size();
        for (int i = 0; i < size; i++) {
            String str = (String) adoptPermissions.get(i);
            String packageName = androidPackage.getPackageName();
            MutableAccessState mutableAccessState = mutateStateScope.newState;
            PackageState packageState2 =
                    (PackageState) mutableAccessState.getExternalState().packageStates.get(str);
            if (packageState2 != null) {
                if (!packageState2.isSystem()) {
                    Slog.w(
                            "AppIdPermissionPolicy",
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Unable to adopt permissions from ",
                                    str,
                                    " to ",
                                    packageName,
                                    ": original package not in system partition"));
                } else if (packageState2.getAndroidPackage() != null) {
                    Slog.w(
                            "AppIdPermissionPolicy",
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Unable to adopt permissions from ",
                                    str,
                                    " to ",
                                    packageName,
                                    ": original package still exists"));
                } else {
                    IndexedMap permissions = mutableAccessState.getSystemState().getPermissions();
                    int size2 = permissions.map.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        Object keyAt = permissions.map.keyAt(i2);
                        Permission permission = (Permission) permissions.map.valueAt(i2);
                        String str2 = (String) keyAt;
                        if (Intrinsics.areEqual(permission.permissionInfo.packageName, str)) {
                            PermissionInfo permissionInfo = new PermissionInfo();
                            PermissionInfo permissionInfo2 = permission.permissionInfo;
                            permissionInfo.name = permissionInfo2.name;
                            permissionInfo.packageName = packageName;
                            permissionInfo.protectionLevel = permissionInfo2.protectionLevel;
                            MutableAccessState.mutateSystemState$default(mutableAccessState)
                                    .mutatePermissions()
                                    .map
                                    .setValueAt(
                                            i2,
                                            Permission.copy$default(
                                                    permission, permissionInfo, false, 0));
                            mutableIndexedSet.add(str2);
                        }
                    }
                }
            }
        }
    }

    public static Permission findPermissionTree(GetStateScope getStateScope, String str) {
        Permission permission;
        IndexedMap permissionTrees = getStateScope.state.getSystemState().getPermissionTrees();
        int size = permissionTrees.map.size();
        int i = 0;
        while (true) {
            permission = null;
            if (i >= size) {
                break;
            }
            Object keyAt = permissionTrees.map.keyAt(i);
            Permission permission2 = (Permission) permissionTrees.map.valueAt(i);
            String str2 = (String) keyAt;
            if (StringsKt__StringsJVMKt.startsWith$default(str, str2)
                    && str.length() > str2.length()
                    && str.charAt(str2.length()) == '.') {
                permission = permission2;
            }
            if (permission != null) {
                break;
            }
            i++;
        }
        return permission;
    }

    public static int getPermissionFlags(AccessState accessState, int i, int i2, String str) {
        MutableIntReferenceMap appIdPermissionFlags;
        MutableUserState mutableUserState = (MutableUserState) accessState.getUserStates().get(i2);
        return ((Number)
                        IndexedMapExtensionsKt.getWithDefault(
                                (mutableUserState == null
                                                || (appIdPermissionFlags =
                                                                mutableUserState
                                                                        .getAppIdPermissionFlags())
                                                        == null)
                                        ? null
                                        : (IndexedMap) appIdPermissionFlags.get(i),
                                str,
                                0))
                .intValue();
    }

    public static boolean shouldGrantPrivilegedOrOemPermission(
            MutateStateScope mutateStateScope, PackageState packageState, Permission permission) {
        String str = permission.permissionInfo.name;
        String packageName = packageState.getPackageName();
        if (IntExtensionsKt.hasBits(permission.permissionInfo.getProtectionFlags(), 16)) {
            if (packageState.isPrivileged()) {
                if ((!packageState.isVendor() && !packageState.isOdm())
                        || IntExtensionsKt.hasBits(
                                permission.permissionInfo.getProtectionFlags(), 32768)) {
                    return true;
                }
                Slog.w(
                        "AppIdPermissionPolicy",
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Permission ",
                                str,
                                " cannot be granted to privileged vendor (or odm) app ",
                                packageName,
                                " because it isn't a vendorPrivileged permission"));
                return false;
            }
        } else if (IntExtensionsKt.hasBits(
                        permission.permissionInfo.getProtectionFlags(),
                        EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                && packageState.isOem()) {
            ArrayMap arrayMap =
                    (ArrayMap)
                            mutateStateScope
                                    .newState
                                    .getExternalState()
                                    .permissionAllowlist
                                    .mOemAppAllowlist
                                    .get(packageName);
            Boolean bool = arrayMap == null ? null : (Boolean) arrayMap.get(str);
            if (bool != null) {
                return bool.booleanValue();
            }
            throw new IllegalStateException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                                    "OEM permission ",
                                    str,
                                    " requested by package ",
                                    packageName,
                                    " must be explicitly declared granted or not")
                            .toString());
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x034f, code lost:

       if (r0.equals(r7.permissionInfo.group) == false) goto L117;
    */
    /* JADX WARN: Removed duplicated region for block: B:57:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x02f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addPermissions(
            com.android.server.permission.access.MutateStateScope r29,
            com.android.server.pm.pkg.PackageState r30,
            com.android.server.permission.access.immutable.MutableIndexedSet r31) {
        /*
            Method dump skipped, instructions count: 872
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy.addPermissions(com.android.server.permission.access.MutateStateScope,"
                    + " com.android.server.pm.pkg.PackageState,"
                    + " com.android.server.permission.access.immutable.MutableIndexedSet):void");
    }

    public final void evaluateAllPermissionStatesForPackage(
            MutateStateScope mutateStateScope,
            PackageState packageState,
            PackageState packageState2) {
        MutableIntSet userIds = mutateStateScope.newState.getExternalState().getUserIds();
        int size = userIds.array.size();
        for (int i = 0; i < size; i++) {
            evaluateAllPermissionStatesForPackageAndUser(
                    mutateStateScope, packageState, userIds.array.keyAt(i), packageState2);
        }
    }

    public final void evaluateAllPermissionStatesForPackageAndUser(
            MutateStateScope mutateStateScope,
            PackageState packageState,
            int i,
            PackageState packageState2) {
        Set requestedPermissions;
        AndroidPackage androidPackage = packageState.getAndroidPackage();
        if (androidPackage == null
                || (requestedPermissions = androidPackage.getRequestedPermissions()) == null) {
            return;
        }
        Iterator it = requestedPermissions.iterator();
        while (it.hasNext()) {
            evaluatePermissionState(
                    mutateStateScope,
                    packageState.getAppId(),
                    i,
                    (String) it.next(),
                    packageState2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:300:0x0404, code lost:

       if (r8.getRequestedPermissions().contains(r5.permissionInfo.name) != false) goto L253;
    */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x0516, code lost:

       if (r29.isSignaturePermissionAllowlistForceEnforced == false) goto L253;
    */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x05f7, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r12, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x0618, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r12, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x066d, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x068f, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x06b2, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x06d5, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x06f8, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x071b, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x073e, code lost:

       if (com.android.server.permission.jarjar.kotlin.collections.ArraysKt.contains((java.lang.Object[]) r9, r11) != false) goto L284;
    */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x05ab, code lost:

       if (r12 != false) goto L284;
    */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0828  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x09af  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x09cc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x09e0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x09e9  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0a11 A[LOOP:5: B:115:0x09e7->B:124:0x0a11, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0a1b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0836  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0993  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0996  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0317 A[LOOP:8: B:258:0x01a9->B:264:0x0317, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0315 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0342  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0532  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x075d A[LOOP:10: B:363:0x0530->B:374:0x075d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:375:0x075b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0764 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:450:0x0761 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x013e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00d8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x07fe  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x080a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void evaluatePermissionState(
            com.android.server.permission.access.MutateStateScope r30,
            int r31,
            int r32,
            java.lang.String r33,
            com.android.server.pm.pkg.PackageState r34) {
        /*
            Method dump skipped, instructions count: 2646
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy.evaluatePermissionState(com.android.server.permission.access.MutateStateScope,"
                    + " int, int, java.lang.String, com.android.server.pm.pkg.PackageState):void");
    }

    public final void evaluatePermissionStateForAllPackages(
            MutateStateScope mutateStateScope, String str) {
        int i;
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableExternalState externalState = mutableAccessState.getExternalState();
        MutableIntSet userIds = externalState.getUserIds();
        int size = userIds.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = userIds.array.keyAt(i2);
            MutableIntReferenceMap appIdPackageNames = externalState.getAppIdPackageNames();
            int size2 = appIdPackageNames.array.size();
            int i3 = 0;
            while (i3 < size2) {
                int keyAt2 = appIdPackageNames.array.keyAt(i3);
                Immutable immutable =
                        mutableAccessState.getExternalState().getAppIdPackageNames().get(keyAt2);
                Intrinsics.checkNotNull(immutable);
                IndexedListSet indexedListSet = (IndexedListSet) immutable;
                int size3 = indexedListSet.list.size();
                int i4 = 0;
                while (true) {
                    if (i4 >= size3) {
                        i = i3;
                        break;
                    }
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
                        if (androidPackage.getRequestedPermissions().contains(str)) {
                            i = i3;
                            evaluatePermissionState(mutateStateScope, keyAt2, keyAt, str, null);
                            break;
                        }
                    }
                    i4++;
                    i3 = i3;
                }
                i3 = i + 1;
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getObjectScheme() {
        return "permission";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final String getSubjectScheme() {
        return "uid";
    }

    public final void inheritImplicitPermissionStates(
            MutateStateScope mutateStateScope, int i, int i2) {
        IndexedListSet indexedListSet;
        ArraySet arraySet = new ArraySet();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        Immutable immutable = mutableAccessState.getExternalState().getAppIdPackageNames().get(i);
        Intrinsics.checkNotNull(immutable);
        IndexedListSet indexedListSet2 = (IndexedListSet) immutable;
        int size = indexedListSet2.list.size();
        int i3 = 10000;
        for (int i4 = 0; i4 < size; i4++) {
            Object obj =
                    mutableAccessState
                            .getExternalState()
                            .packageStates
                            .get((String) indexedListSet2.list.get(i4));
            Intrinsics.checkNotNull(obj);
            PackageState packageState = (PackageState) obj;
            if (packageState.getAndroidPackage() != null) {
                AndroidPackage androidPackage = packageState.getAndroidPackage();
                Intrinsics.checkNotNull(androidPackage);
                int targetSdkVersion = androidPackage.getTargetSdkVersion();
                if (i3 > targetSdkVersion) {
                    i3 = targetSdkVersion;
                }
                AndroidPackage androidPackage2 = packageState.getAndroidPackage();
                Intrinsics.checkNotNull(androidPackage2);
                Iterator it = androidPackage2.getImplicitPermissions().iterator();
                while (it.hasNext()) {
                    arraySet.add(it.next());
                }
            }
        }
        int size2 = arraySet.size();
        for (int i5 = 0; i5 < size2; i5++) {
            String str = (String) CollectionsKt.elementAt(arraySet, i5);
            Permission permission =
                    (Permission) mutableAccessState.getSystemState().getPermissions().map.get(str);
            if (permission == null) {
                throw new IllegalStateException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                        "Unknown implicit permission ",
                                        str,
                                        " in split permissions")
                                .toString());
            }
            if (permission.permissionInfo.getProtection() == 1
                    && getPermissionFlags(mutateStateScope.oldState, i, i2, str) == 0
                    && (indexedListSet =
                                    (IndexedListSet)
                                            mutableAccessState
                                                    .getExternalState()
                                                    .implicitToSourcePermissions
                                                    .map
                                                    .get(str))
                            != null) {
                AccessState accessState = mutateStateScope.state;
                int permissionFlags = getPermissionFlags(accessState, i, i2, str);
                int size3 = indexedListSet.list.size();
                int i6 = 0;
                while (i6 < size3) {
                    String str2 = (String) indexedListSet.list.get(i6);
                    IndexedListSet indexedListSet3 = indexedListSet;
                    if (((Permission)
                                    mutableAccessState
                                            .getSystemState()
                                            .getPermissions()
                                            .map
                                            .get(str2))
                            == null) {
                        throw new IllegalStateException(
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                                "Unknown source permission ",
                                                str2,
                                                " in split permissions")
                                        .toString());
                    }
                    int permissionFlags2 = getPermissionFlags(accessState, i, i2, str2);
                    boolean isPermissionGranted =
                            PermissionFlags.isPermissionGranted(permissionFlags2);
                    AccessState accessState2 = accessState;
                    boolean isPermissionGranted2 =
                            PermissionFlags.isPermissionGranted(permissionFlags);
                    boolean z = isPermissionGranted && !isPermissionGranted2;
                    if (isPermissionGranted == isPermissionGranted2 || z) {
                        if (z) {
                            permissionFlags = 0;
                        }
                        permissionFlags = (permissionFlags2 & 16777208) | permissionFlags;
                    }
                    i6++;
                    accessState = accessState2;
                    indexedListSet = indexedListSet3;
                }
                updatePermissionFlags(
                        mutateStateScope,
                        i,
                        i2,
                        str,
                        -1,
                        (i3 < 23 || !NO_IMPLICIT_FLAG_PERMISSIONS.set.contains(str))
                                ? permissionFlags | 4096
                                : permissionFlags & (-4097));
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void migrateSystemState(MutableAccessState mutableAccessState) {
        this.migration.getClass();
        Object service = LocalServices.getService(PermissionMigrationHelperImpl.class);
        Intrinsics.checkNotNull(service);
        LegacyPermissionSettings legacyPermissions =
                ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                        .getLegacyPermissions();
        if (((ArrayList) legacyPermissions.getPermissions()).isEmpty()
                && ((ArrayList) legacyPermissions.getPermissionTrees()).isEmpty()) {
            return;
        }
        MutableIndexedMap mutatePermissions =
                MutableAccessState.mutateSystemState$default(mutableAccessState)
                        .mutatePermissions();
        List permissions =
                ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                        .getLegacyPermissions()
                        .getPermissions();
        final ArrayMap arrayMap = new ArrayMap();
        ((ArrayList) permissions)
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Map map = arrayMap;
                                LegacyPermission legacyPermission = (LegacyPermission) obj;
                                PermissionInfo permissionInfo = legacyPermission.mPermissionInfo;
                                map.put(
                                        permissionInfo.name,
                                        new PermissionMigrationHelper$LegacyPermission(
                                                permissionInfo, legacyPermission.mType));
                            }
                        });
        AppIdPermissionMigration.migratePermissions(mutatePermissions, arrayMap);
        MutableIndexedMap mutableIndexedMap =
                (MutableIndexedMap)
                        MutableAccessState.mutateSystemState$default(mutableAccessState)
                                .permissionTreesReference
                                .mutate();
        List permissionTrees =
                ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                        .getLegacyPermissions()
                        .getPermissionTrees();
        final ArrayMap arrayMap2 = new ArrayMap();
        ((ArrayList) permissionTrees)
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Map map = arrayMap2;
                                LegacyPermission legacyPermission = (LegacyPermission) obj;
                                PermissionInfo permissionInfo = legacyPermission.mPermissionInfo;
                                map.put(
                                        permissionInfo.name,
                                        new PermissionMigrationHelper$LegacyPermission(
                                                permissionInfo, legacyPermission.mType));
                            }
                        });
        AppIdPermissionMigration.migratePermissions(mutableIndexedMap, arrayMap2);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void migrateUserState(MutableAccessState mutableAccessState, int i) {
        RuntimePermissionsState legacyPermissionsState;
        final int i2 = 0;
        final int i3 = 1;
        this.migration.getClass();
        Object service = LocalServices.getService(PermissionMigrationHelperImpl.class);
        Intrinsics.checkNotNull(service);
        final PermissionMigrationHelperImpl permissionMigrationHelperImpl =
                (PermissionMigrationHelperImpl) service;
        if (permissionMigrationHelperImpl.getLegacyPermissionStateVersion(i) <= -1) {
            return;
        }
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        final ArrayMap arrayMap = new ArrayMap();
        PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
        PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                legacyPermissionsState =
                        PackageManagerService.this.mSettings.getLegacyPermissionsState(i);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot =
                ((PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class))
                        .withUnfilteredSnapshot();
        try {
            final Map packageStates = withUnfilteredSnapshot.getPackageStates();
            legacyPermissionsState
                    .getPackagePermissions()
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    switch (i2) {
                                        case 0:
                                            PermissionMigrationHelperImpl
                                                    permissionMigrationHelperImpl2 =
                                                            permissionMigrationHelperImpl;
                                            Map map = packageStates;
                                            Map map2 = arrayMap;
                                            String str = (String) obj;
                                            List list = (List) obj2;
                                            permissionMigrationHelperImpl2.getClass();
                                            if (!list.isEmpty()) {
                                                PackageState packageState =
                                                        (PackageState) map.get(str);
                                                if (packageState == null) {
                                                    Log.w(
                                                            "PermissionMigrationHelperImpl",
                                                            "Package " + str + " not found.");
                                                    break;
                                                } else {
                                                    map2.put(
                                                            Integer.valueOf(
                                                                    packageState.getAppId()),
                                                            PermissionMigrationHelperImpl
                                                                    .toLegacyPermissionStates(
                                                                            list));
                                                    break;
                                                }
                                            }
                                            break;
                                        default:
                                            PermissionMigrationHelperImpl
                                                    permissionMigrationHelperImpl3 =
                                                            permissionMigrationHelperImpl;
                                            Map map3 = packageStates;
                                            Map map4 = arrayMap;
                                            String str2 = (String) obj;
                                            List list2 = (List) obj2;
                                            permissionMigrationHelperImpl3.getClass();
                                            if (!list2.isEmpty()) {
                                                SharedUserSetting sharedUserSetting =
                                                        (SharedUserSetting) map3.get(str2);
                                                if (sharedUserSetting == null) {
                                                    Log.w(
                                                            "PermissionMigrationHelperImpl",
                                                            "Shared user " + str2 + " not found.");
                                                    break;
                                                } else {
                                                    map4.put(
                                                            Integer.valueOf(
                                                                    sharedUserSetting.mAppId),
                                                            PermissionMigrationHelperImpl
                                                                    .toLegacyPermissionStates(
                                                                            list2));
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                }
                            });
            final Map sharedUsers = withUnfilteredSnapshot.getSharedUsers();
            legacyPermissionsState
                    .getSharedUserPermissions()
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    switch (i3) {
                                        case 0:
                                            PermissionMigrationHelperImpl
                                                    permissionMigrationHelperImpl2 =
                                                            permissionMigrationHelperImpl;
                                            Map map = sharedUsers;
                                            Map map2 = arrayMap;
                                            String str = (String) obj;
                                            List list = (List) obj2;
                                            permissionMigrationHelperImpl2.getClass();
                                            if (!list.isEmpty()) {
                                                PackageState packageState =
                                                        (PackageState) map.get(str);
                                                if (packageState == null) {
                                                    Log.w(
                                                            "PermissionMigrationHelperImpl",
                                                            "Package " + str + " not found.");
                                                    break;
                                                } else {
                                                    map2.put(
                                                            Integer.valueOf(
                                                                    packageState.getAppId()),
                                                            PermissionMigrationHelperImpl
                                                                    .toLegacyPermissionStates(
                                                                            list));
                                                    break;
                                                }
                                            }
                                            break;
                                        default:
                                            PermissionMigrationHelperImpl
                                                    permissionMigrationHelperImpl3 =
                                                            permissionMigrationHelperImpl;
                                            Map map3 = sharedUsers;
                                            Map map4 = arrayMap;
                                            String str2 = (String) obj;
                                            List list2 = (List) obj2;
                                            permissionMigrationHelperImpl3.getClass();
                                            if (!list2.isEmpty()) {
                                                SharedUserSetting sharedUserSetting =
                                                        (SharedUserSetting) map3.get(str2);
                                                if (sharedUserSetting == null) {
                                                    Log.w(
                                                            "PermissionMigrationHelperImpl",
                                                            "Shared user " + str2 + " not found.");
                                                    break;
                                                } else {
                                                    map4.put(
                                                            Integer.valueOf(
                                                                    sharedUserSetting.mAppId),
                                                            PermissionMigrationHelperImpl
                                                                    .toLegacyPermissionStates(
                                                                            list2));
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                }
                            });
            withUnfilteredSnapshot.close();
            int
                    version$frameworks__base__services__permission__android_common__services_permission_pre_jarjar =
                            PackageVersionMigration
                                    .getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                                            i);
            MutableUserState mutateUserState = mutableAccessState.mutateUserState(i, 1);
            Intrinsics.checkNotNull(mutateUserState);
            MutableIntReferenceMap mutableIntReferenceMap =
                    (MutableIntReferenceMap) mutateUserState.appIdPermissionFlagsReference.mutate();
            for (Map.Entry entry : arrayMap.entrySet()) {
                Integer num = (Integer) entry.getKey();
                Map map = (Map) entry.getValue();
                IndexedListSet indexedListSet =
                        (IndexedListSet)
                                mutableAccessState
                                        .getExternalState()
                                        .getAppIdPackageNames()
                                        .get(num.intValue());
                if (indexedListSet == null) {
                    Slog.w(
                            "AppIdPermissionMigration",
                            "Dropping unknown app ID " + num + " when migrating permission state");
                } else {
                    MutableIndexedMap mutableIndexedMap = new MutableIndexedMap();
                    IntReferenceMapExtensionsKt.set(
                            mutableIntReferenceMap, num.intValue(), mutableIndexedMap);
                    for (Map.Entry entry2 : map.entrySet()) {
                        String str = (String) entry2.getKey();
                        PermissionMigrationHelper$LegacyPermissionState
                                permissionMigrationHelper$LegacyPermissionState =
                                        (PermissionMigrationHelper$LegacyPermissionState)
                                                entry2.getValue();
                        Permission permission =
                                (Permission)
                                        mutableAccessState
                                                .getSystemState()
                                                .getPermissions()
                                                .map
                                                .get(str);
                        if (permission == null) {
                            Slog.w(
                                    "AppIdPermissionMigration",
                                    "Dropping unknown permission "
                                            + str
                                            + " for app ID "
                                            + num
                                            + " when migrating permission state");
                        } else {
                            int i4 = 2;
                            if (permission.permissionInfo.getProtection() == 0) {
                                if (permissionMigrationHelper$LegacyPermissionState.mGranted) {
                                    i4 = 1;
                                }
                            } else if (permission.permissionInfo.getProtection() == 2
                                    || permission.permissionInfo.getProtection() == 4) {
                                if (permissionMigrationHelper$LegacyPermissionState.mGranted) {
                                    i4 =
                                            (IntExtensionsKt.hasBits(
                                                                    permission.permissionInfo
                                                                            .getProtectionFlags(),
                                                                    32)
                                                            || IntExtensionsKt.hasBits(
                                                                    permission.permissionInfo
                                                                            .getProtectionFlags(),
                                                                    67108864))
                                                    ? 20
                                                    : 4;
                                }
                                i4 = 0;
                            } else {
                                if (permission.permissionInfo.getProtection() == 1
                                        && permissionMigrationHelper$LegacyPermissionState
                                                .mGranted) {
                                    i4 = 16;
                                }
                                i4 = 0;
                            }
                            int i5 = permissionMigrationHelper$LegacyPermissionState.mFlags;
                            mutableIndexedMap.put(
                                    str,
                                    Integer.valueOf(
                                            PermissionFlags.updateFlags(permission, i4, i5, i5)));
                        }
                    }
                    MutableIndexedMap mutatePackageVersions =
                            mutateUserState.mutatePackageVersions();
                    int size = indexedListSet.list.size();
                    for (int i6 = 0; i6 < size; i6++) {
                        mutatePackageVersions.put(
                                (String) indexedListSet.list.get(i6),
                                Integer.valueOf(
                                        version$frameworks__base__services__permission__android_common__services_permission_pre_jarjar));
                    }
                }
            }
        } finally {
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onAppIdRemoved(MutateStateScope mutateStateScope, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            userStates.keyAt(i2);
            if (((MutableUserState) userStates.valueAt(i2))
                    .getAppIdPermissionFlags()
                    .array
                    .contains(i)) {
                IntReferenceMapExtensionsKt.minusAssign(
                        (MutableIntReferenceMap)
                                MutableAccessState.mutateUserStateAt$default(mutableAccessState, i2)
                                        .appIdPermissionFlagsReference
                                        .mutate(),
                        i);
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageAdded(MutateStateScope mutateStateScope, PackageState packageState) {
        MutableIndexedSet mutableIndexedSet = new MutableIndexedSet();
        adoptPermissions(mutateStateScope, packageState, mutableIndexedSet);
        addPermissionGroups(mutateStateScope, packageState);
        addPermissions(mutateStateScope, packageState, mutableIndexedSet);
        trimPermissions(mutateStateScope, packageState.getPackageName(), mutableIndexedSet);
        trimPermissionStates(mutateStateScope, packageState.getAppId());
        revokePermissionsOnPackageUpdate(mutateStateScope, packageState.getAppId());
        int size = mutableIndexedSet.set.size();
        for (int i = 0; i < size; i++) {
            evaluatePermissionStateForAllPackages(
                    mutateStateScope, (String) CollectionsKt.elementAt(mutableIndexedSet.set, i));
        }
        evaluateAllPermissionStatesForPackage(mutateStateScope, packageState, packageState);
        MutableIntSet userIds = mutateStateScope.newState.getExternalState().getUserIds();
        int size2 = userIds.array.size();
        for (int i2 = 0; i2 < size2; i2++) {
            inheritImplicitPermissionStates(
                    mutateStateScope, packageState.getAppId(), userIds.array.keyAt(i2));
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageInstalled(
            MutateStateScope mutateStateScope, PackageState packageState, int i) {
        AndroidPackage androidPackage;
        if (packageState.isSystem()
                || (androidPackage = packageState.getAndroidPackage()) == null) {
            return;
        }
        int appId = packageState.getAppId();
        for (String str : androidPackage.getRequestedPermissions()) {
            MutableAccessState mutableAccessState = mutateStateScope.newState;
            Permission permission =
                    (Permission) mutableAccessState.getSystemState().getPermissions().map.get(str);
            if (permission != null
                    && (IntExtensionsKt.hasBits(permission.permissionInfo.flags, 4)
                            || IntExtensionsKt.hasBits(permission.permissionInfo.flags, 8))) {
                Immutable immutable =
                        mutableAccessState.getExternalState().getAppIdPackageNames().get(appId);
                Intrinsics.checkNotNull(immutable);
                IndexedListSet indexedListSet = (IndexedListSet) immutable;
                int size = indexedListSet.list.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        updatePermissionExemptFlags(
                                mutateStateScope, appId, i, permission, 131072, 0);
                        break;
                    }
                    Object obj =
                            mutableAccessState
                                    .getExternalState()
                                    .packageStates
                                    .get((String) indexedListSet.list.get(i2));
                    Intrinsics.checkNotNull(obj);
                    PackageState packageState2 = (PackageState) obj;
                    if (packageState2.getAndroidPackage() != null && packageState2.isSystem()) {
                        AndroidPackage androidPackage2 = packageState2.getAndroidPackage();
                        Intrinsics.checkNotNull(androidPackage2);
                        if (androidPackage2.getRequestedPermissions().contains(str)) {
                            break;
                        }
                    }
                    i2++;
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageRemoved(MutateStateScope mutateStateScope, String str, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        if (!(!mutableAccessState
                .getExternalState()
                .disabledSystemPackageStates
                .containsKey(str))) {
            throw new IllegalStateException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                                    "Package ",
                                    str,
                                    " reported as removed before disabled system package is"
                                        + " enabled")
                            .toString());
        }
        MutableIndexedSet mutableIndexedSet = new MutableIndexedSet();
        trimPermissions(mutateStateScope, str, mutableIndexedSet);
        if (mutableAccessState.getExternalState().getAppIdPackageNames().array.contains(i)) {
            trimPermissionStates(mutateStateScope, i);
        }
        int size = mutableIndexedSet.set.size();
        for (int i2 = 0; i2 < size; i2++) {
            evaluatePermissionStateForAllPackages(
                    mutateStateScope, (String) CollectionsKt.elementAt(mutableIndexedSet.set, i2));
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onPackageUninstalled(MutateStateScope mutateStateScope, String str, int i) {
        resetRuntimePermissions(mutateStateScope, str, i);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onStateMutated() {
        MutableIndexedListSet mutableIndexedListSet = this.onPermissionFlagsChangedListeners;
        int size = mutableIndexedListSet.list.size();
        for (int i = 0; i < size; i++) {
            ((OnPermissionFlagsChangedListener) mutableIndexedListSet.list.get(i)).onStateMutated();
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onStorageVolumeMounted(
            MutateStateScope mutateStateScope, List list, boolean z) {
        MutableAccessState mutableAccessState;
        MutableIndexedSet mutableIndexedSet = new MutableIndexedSet();
        int size = list.size();
        int i = 0;
        while (true) {
            mutableAccessState = mutateStateScope.newState;
            if (i >= size) {
                break;
            }
            PackageState packageState =
                    (PackageState)
                            mutableAccessState
                                    .getExternalState()
                                    .packageStates
                                    .get((String) list.get(i));
            if (packageState != null) {
                adoptPermissions(mutateStateScope, packageState, mutableIndexedSet);
                addPermissionGroups(mutateStateScope, packageState);
                addPermissions(mutateStateScope, packageState, mutableIndexedSet);
                trimPermissions(mutateStateScope, packageState.getPackageName(), mutableIndexedSet);
                trimPermissionStates(mutateStateScope, packageState.getAppId());
                revokePermissionsOnPackageUpdate(mutateStateScope, packageState.getAppId());
            }
            i++;
        }
        int size2 = mutableIndexedSet.set.size();
        for (int i2 = 0; i2 < size2; i2++) {
            evaluatePermissionStateForAllPackages(
                    mutateStateScope, (String) CollectionsKt.elementAt(mutableIndexedSet.set, i2));
        }
        int size3 = list.size();
        for (int i3 = 0; i3 < size3; i3++) {
            PackageState packageState2 =
                    (PackageState)
                            mutableAccessState
                                    .getExternalState()
                                    .packageStates
                                    .get((String) list.get(i3));
            if (packageState2 != null) {
                evaluateAllPermissionStatesForPackage(
                        mutateStateScope, packageState2, z ? packageState2 : null);
            }
        }
        int size4 = list.size();
        for (int i4 = 0; i4 < size4; i4++) {
            PackageState packageState3 =
                    (PackageState)
                            mutableAccessState
                                    .getExternalState()
                                    .packageStates
                                    .get((String) list.get(i4));
            if (packageState3 != null) {
                MutableIntSet userIds = mutableAccessState.getExternalState().getUserIds();
                int size5 = userIds.array.size();
                for (int i5 = 0; i5 < size5; i5++) {
                    inheritImplicitPermissionStates(
                            mutateStateScope, packageState3.getAppId(), userIds.array.keyAt(i5));
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onSystemReady() {
        MutableIndexedSet mutableIndexedSet = this.privilegedPermissionAllowlistViolations;
        if (mutableIndexedSet.set.isEmpty()) {
            return;
        }
        StringBuilder sb =
                new StringBuilder("We detected priv-permissions violations from below apps:");
        int size = mutableIndexedSet.set.size();
        for (int i = 0; i < size; i++) {
            sb.append((String) CollectionsKt.elementAt(mutableIndexedSet.set, i));
        }
        sb.append(
                "\\n"
                    + "Privileged apps MUST put their 'signature|privileged' permissions into the"
                    + " allowlist.\n"
                    + "Please ASSIGN this issue to above apps to apply.\n");
        Slog.d("AppIdPermissionPolicy", sb.toString());
        PmLog.logDebugInfo(sb.toString());
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void onUserAdded(MutateStateScope mutateStateScope, int i) {
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        Iterator it = mutableAccessState.getExternalState().packageStates.entrySet().iterator();
        while (it.hasNext()) {
            PackageState packageState = (PackageState) ((Map.Entry) it.next()).getValue();
            if (!packageState.isApex()) {
                evaluateAllPermissionStatesForPackageAndUser(
                        mutateStateScope, packageState, i, null);
            }
        }
        MutableIntReferenceMap appIdPackageNames =
                mutableAccessState.getExternalState().getAppIdPackageNames();
        int size = appIdPackageNames.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = appIdPackageNames.array.keyAt(i2);
            inheritImplicitPermissionStates(mutateStateScope, keyAt, i);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void parseSystemState(
            BinaryXmlPullParser binaryXmlPullParser, MutableAccessState mutableAccessState) {
        this.persistence.getClass();
        String name = binaryXmlPullParser.getName();
        if (Intrinsics.areEqual(name, "permission-trees")) {
            AppIdPermissionPersistence.parsePermissions(
                    binaryXmlPullParser, mutableAccessState, true);
        } else if (Intrinsics.areEqual(name, "permissions")) {
            AppIdPermissionPersistence.parsePermissions(
                    binaryXmlPullParser, mutableAccessState, false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x0181, code lost:

       r4 = r17.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0186, code lost:

       if (r4 == 1) goto L134;
    */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0188, code lost:

       if (r4 == r7) goto L135;
    */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x018a, code lost:

       if (r4 == 3) goto L136;
    */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x018c, code lost:

       r7 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x01b8, code lost:

       r0 = r3.array.size() - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x01c1, code lost:

       if ((-1) >= r0) goto L172;
    */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x01c3, code lost:

       r4 = r3.array.keyAt(r0);
       r5 = (com.android.server.permission.access.immutable.IndexedMap) r3.valueAt(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x01dd, code lost:

       if (r18.getExternalState().getAppIdPackageNames().array.contains(r4) != false) goto L118;
    */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x01df, code lost:

       com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0.m(r4, "Dropping unknown app ID ", " when parsing permission state", "AppIdPermissionPersistence");
       r3.removeAt$1(r0);
       r1.requestWriteMode(1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x01ed, code lost:

       r0 = r0 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:160:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0124, code lost:

       r6 = r17.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0129, code lost:

       if (r6 == 1) goto L145;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x012c, code lost:

       if (r6 == 2) goto L146;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x012e, code lost:

       if (r6 == 3) goto L147;
    */
    @Override // com.android.server.permission.access.SchemePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseUserState(
            com.android.modules.utils.BinaryXmlPullParser r17,
            com.android.server.permission.access.MutableAccessState r18,
            int r19) {
        /*
            Method dump skipped, instructions count: 497
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy.parseUserState(com.android.modules.utils.BinaryXmlPullParser,"
                    + " com.android.server.permission.access.MutableAccessState, int):void");
    }

    public final void resetRuntimePermissions(
            MutateStateScope mutateStateScope, String str, int i) {
        AndroidPackage androidPackage;
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        PackageState packageState =
                (PackageState) mutableAccessState.getExternalState().packageStates.get(str);
        if (packageState == null || (androidPackage = packageState.getAndroidPackage()) == null) {
            return;
        }
        int appId = packageState.getAppId();
        for (String str2 : androidPackage.getRequestedPermissions()) {
            Permission permission =
                    (Permission) mutableAccessState.getSystemState().getPermissions().map.get(str2);
            if (permission != null
                    && permission.permissionInfo.getProtection() == 1
                    && !IntExtensionsKt.hasBits(permission.permissionInfo.flags, 2)) {
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
                        int permissionFlags =
                                getPermissionFlags(mutateStateScope.state, appId, i, str2);
                        if (!IntExtensionsKt.hasAnyBit(
                                permissionFlags,
                                FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT)) {
                            int i3 =
                                    ((IntExtensionsKt.hasBits(permissionFlags, 8)
                                                            || IntExtensionsKt.hasBits(
                                                                    permissionFlags, 512))
                                                    ? permissionFlags | 16
                                                    : permissionFlags & (-17))
                                            & (-15728737);
                            if (IntExtensionsKt.hasBits(i3, 1024)) {
                                i3 |= 4096;
                            }
                            updatePermissionFlags(mutateStateScope, appId, i, str2, -1, i3);
                        }
                    }
                }
            }
        }
    }

    public final void revokePermissionsOnPackageUpdate(MutateStateScope mutateStateScope, int i) {
        boolean z;
        boolean z2;
        AccessState accessState = mutateStateScope.oldState;
        if (accessState.getExternalState().getAppIdPackageNames().array.contains(i)) {
            Immutable immutable = accessState.getExternalState().getAppIdPackageNames().get(i);
            Intrinsics.checkNotNull(immutable);
            IndexedListSet indexedListSet = (IndexedListSet) immutable;
            int size = indexedListSet.list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object obj =
                        accessState
                                .getExternalState()
                                .packageStates
                                .get((String) indexedListSet.list.get(i2));
                Intrinsics.checkNotNull(obj);
                if (((PackageState) obj).getAndroidPackage() != null) {
                    Immutable immutable2 =
                            accessState.getExternalState().getAppIdPackageNames().get(i);
                    Intrinsics.checkNotNull(immutable2);
                    IndexedListSet indexedListSet2 = (IndexedListSet) immutable2;
                    Ref$IntRef ref$IntRef = new Ref$IntRef();
                    ref$IntRef.element = 10000;
                    int size2 = indexedListSet2.list.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        Object obj2 = indexedListSet2.list.get(i3);
                        int i4 = ref$IntRef.element;
                        Object obj3 =
                                accessState.getExternalState().packageStates.get((String) obj2);
                        Intrinsics.checkNotNull(obj3);
                        PackageState packageState = (PackageState) obj3;
                        if (packageState.getAndroidPackage() != null) {
                            AndroidPackage androidPackage = packageState.getAndroidPackage();
                            Intrinsics.checkNotNull(androidPackage);
                            int targetSdkVersion = androidPackage.getTargetSdkVersion();
                            if (i4 > targetSdkVersion) {
                                i4 = targetSdkVersion;
                            }
                        }
                        ref$IntRef.element = i4;
                    }
                    int i5 = ref$IntRef.element;
                    MutableAccessState mutableAccessState = mutateStateScope.newState;
                    Immutable immutable3 =
                            mutableAccessState.getExternalState().getAppIdPackageNames().get(i);
                    Intrinsics.checkNotNull(immutable3);
                    IndexedListSet indexedListSet3 = (IndexedListSet) immutable3;
                    Ref$IntRef ref$IntRef2 = new Ref$IntRef();
                    ref$IntRef2.element = 10000;
                    int size3 = indexedListSet3.list.size();
                    for (int i6 = 0; i6 < size3; i6++) {
                        Object obj4 = indexedListSet3.list.get(i6);
                        int i7 = ref$IntRef2.element;
                        Object obj5 =
                                mutableAccessState
                                        .getExternalState()
                                        .packageStates
                                        .get((String) obj4);
                        Intrinsics.checkNotNull(obj5);
                        PackageState packageState2 = (PackageState) obj5;
                        if (packageState2.getAndroidPackage() != null) {
                            AndroidPackage androidPackage2 = packageState2.getAndroidPackage();
                            Intrinsics.checkNotNull(androidPackage2);
                            int targetSdkVersion2 = androidPackage2.getTargetSdkVersion();
                            if (i7 > targetSdkVersion2) {
                                i7 = targetSdkVersion2;
                            }
                        }
                        ref$IntRef2.element = i7;
                    }
                    int i8 = ref$IntRef2.element;
                    boolean z3 = i5 >= 29 && i8 < 29;
                    boolean z4 = i5 < 29 && i8 >= 29;
                    Immutable immutable4 =
                            accessState.getExternalState().getAppIdPackageNames().get(i);
                    Intrinsics.checkNotNull(immutable4);
                    IndexedListSet indexedListSet4 = (IndexedListSet) immutable4;
                    int size4 = indexedListSet4.list.size();
                    int i9 = 0;
                    while (true) {
                        if (i9 >= size4) {
                            z = false;
                            break;
                        }
                        Object obj6 =
                                accessState
                                        .getExternalState()
                                        .packageStates
                                        .get((String) indexedListSet4.list.get(i9));
                        Intrinsics.checkNotNull(obj6);
                        PackageState packageState3 = (PackageState) obj6;
                        if (packageState3.getAndroidPackage() != null) {
                            AndroidPackage androidPackage3 = packageState3.getAndroidPackage();
                            Intrinsics.checkNotNull(androidPackage3);
                            if (androidPackage3.isRequestLegacyExternalStorage()) {
                                z = true;
                                break;
                            }
                        }
                        i9++;
                    }
                    Immutable immutable5 =
                            mutableAccessState.getExternalState().getAppIdPackageNames().get(i);
                    Intrinsics.checkNotNull(immutable5);
                    IndexedListSet indexedListSet5 = (IndexedListSet) immutable5;
                    int size5 = indexedListSet5.list.size();
                    int i10 = 0;
                    while (true) {
                        if (i10 >= size5) {
                            z2 = false;
                            break;
                        }
                        Object obj7 =
                                mutableAccessState
                                        .getExternalState()
                                        .packageStates
                                        .get((String) indexedListSet5.list.get(i10));
                        Intrinsics.checkNotNull(obj7);
                        PackageState packageState4 = (PackageState) obj7;
                        if (packageState4.getAndroidPackage() != null) {
                            AndroidPackage androidPackage4 = packageState4.getAndroidPackage();
                            Intrinsics.checkNotNull(androidPackage4);
                            if (androidPackage4.isRequestLegacyExternalStorage()) {
                                z2 = true;
                                break;
                            }
                        }
                        i10++;
                    }
                    if ((z4 || z || !z2) && !z3) {
                        return;
                    }
                    MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
                    int size6 = userStates.array.size();
                    for (int i11 = 0; i11 < size6; i11++) {
                        int keyAt = userStates.array.keyAt(i11);
                        IndexedMap indexedMap =
                                (IndexedMap)
                                        ((MutableUserState) userStates.valueAt(i11))
                                                .getAppIdPermissionFlags()
                                                .get(i);
                        if (indexedMap != null) {
                            for (int size7 = indexedMap.map.size() - 1; -1 < size7; size7--) {
                                Object keyAt2 = indexedMap.map.keyAt(size7);
                                int intValue = ((Number) indexedMap.map.valueAt(size7)).intValue();
                                String str = (String) keyAt2;
                                if (STORAGE_AND_MEDIA_PERMISSIONS.set.contains(str)
                                        && IntExtensionsKt.hasBits(intValue, 16)
                                        && !IntExtensionsKt.hasAnyBit(
                                                intValue,
                                                FrameworkStatsLog
                                                        .NON_A11Y_TOOL_SERVICE_WARNING_REPORT)) {
                                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                                    i,
                                                    "Revoking storage permission: ",
                                                    str,
                                                    " for appId:  ",
                                                    " and user: "),
                                            keyAt,
                                            "AppIdPermissionPolicy");
                                    updatePermissionFlags(
                                            mutateStateScope,
                                            i,
                                            keyAt,
                                            str,
                                            -1,
                                            intValue & (-15728753));
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void serializeSystemState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState) {
        this.persistence.getClass();
        MutableSystemState systemState = accessState.getSystemState();
        AppIdPermissionPersistence.serializePermissions(
                binaryXmlSerializer, "permission-trees", systemState.getPermissionTrees());
        AppIdPermissionPersistence.serializePermissions(
                binaryXmlSerializer, "permissions", systemState.getPermissions());
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public final void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        this.persistence.getClass();
        Immutable immutable = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        MutableIntReferenceMap appIdPermissionFlags =
                ((MutableUserState) immutable).getAppIdPermissionFlags();
        binaryXmlSerializer.startTag((String) null, "app-id-permissions");
        int size = appIdPermissionFlags.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = appIdPermissionFlags.array.keyAt(i2);
            IndexedMap indexedMap = (IndexedMap) appIdPermissionFlags.valueAt(i2);
            binaryXmlSerializer.startTag((String) null, "app-id");
            binaryXmlSerializer.attributeInt((String) null, "id", keyAt);
            int size2 = indexedMap.map.size();
            for (int i3 = 0; i3 < size2; i3++) {
                Object keyAt2 = indexedMap.map.keyAt(i3);
                int intValue = ((Number) indexedMap.map.valueAt(i3)).intValue();
                binaryXmlSerializer.startTag((String) null, "permission");
                binaryXmlSerializer.attributeInterned((String) null, "name", (String) keyAt2);
                if (IntExtensionsKt.hasBits(intValue, 2097152)) {
                    intValue &= -17;
                }
                binaryXmlSerializer.attributeInt((String) null, "flags", intValue);
                binaryXmlSerializer.endTag((String) null, "permission");
            }
            binaryXmlSerializer.endTag((String) null, "app-id");
        }
        binaryXmlSerializer.endTag((String) null, "app-id-permissions");
    }

    public final void trimPermissionStates(MutateStateScope mutateStateScope, int i) {
        ArraySet arraySet = new ArraySet();
        MutableAccessState mutableAccessState = mutateStateScope.newState;
        Immutable immutable = mutableAccessState.getExternalState().getAppIdPackageNames().get(i);
        Intrinsics.checkNotNull(immutable);
        IndexedListSet indexedListSet = (IndexedListSet) immutable;
        int size = indexedListSet.list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object obj =
                    mutableAccessState
                            .getExternalState()
                            .packageStates
                            .get((String) indexedListSet.list.get(i2));
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
        for (int i3 = 0; i3 < size2; i3++) {
            int keyAt = userStates.array.keyAt(i3);
            IndexedMap indexedMap =
                    (IndexedMap)
                            ((MutableUserState) userStates.valueAt(i3))
                                    .getAppIdPermissionFlags()
                                    .get(i);
            if (indexedMap != null) {
                for (int size3 = indexedMap.map.size() - 1; -1 < size3; size3--) {
                    Object keyAt2 = indexedMap.map.keyAt(size3);
                    ((Number) indexedMap.map.valueAt(size3)).intValue();
                    String str = (String) keyAt2;
                    if (!arraySet.contains(str)) {
                        updatePermissionFlags(mutateStateScope, i, keyAt, str, -1, 0);
                    }
                }
            }
        }
    }

    public final void trimPermissions(
            MutateStateScope mutateStateScope, String str, MutableIndexedSet mutableIndexedSet) {
        int i;
        int i2;
        IndexedMap indexedMap;
        List permissions;
        Permission findPermissionTree;
        List permissions2;
        int i3;
        int i4;
        MutateStateScope mutateStateScope2 = mutateStateScope;
        MutableAccessState mutableAccessState = mutateStateScope2.newState;
        PackageState packageState =
                (PackageState) mutableAccessState.getExternalState().packageStates.get(str);
        AndroidPackage androidPackage =
                packageState != null ? packageState.getAndroidPackage() : null;
        if (packageState == null || androidPackage != null) {
            PackageState packageState2 =
                    (PackageState)
                            mutableAccessState
                                    .getExternalState()
                                    .disabledSystemPackageStates
                                    .get(str);
            AndroidPackage androidPackage2 =
                    packageState2 != null ? packageState2.getAndroidPackage() : null;
            IndexedMap permissionTrees = mutableAccessState.getSystemState().getPermissionTrees();
            boolean z = true;
            int size = permissionTrees.map.size() - 1;
            while (true) {
                if (-1 >= size) {
                    break;
                }
                String str2 = (String) permissionTrees.map.keyAt(size);
                if (Intrinsics.areEqual(
                        ((Permission) permissionTrees.map.valueAt(size)).permissionInfo.packageName,
                        str)) {
                    if (packageState != null) {
                        Intrinsics.checkNotNull(androidPackage);
                        List permissions3 = androidPackage.getPermissions();
                        int size2 = permissions3.size();
                        while (i4 < size2) {
                            ParsedPermission parsedPermission =
                                    (ParsedPermission) permissions3.get(i4);
                            i4 =
                                    (parsedPermission.isTree()
                                                    && Intrinsics.areEqual(
                                                            parsedPermission.getName(), str2))
                                            ? 0
                                            : i4 + 1;
                        }
                    }
                    if (androidPackage2 != null
                            && (permissions2 = androidPackage2.getPermissions()) != null) {
                        int size3 = permissions2.size();
                        while (i3 < size3) {
                            ParsedPermission parsedPermission2 =
                                    (ParsedPermission) permissions2.get(i3);
                            i3 =
                                    (parsedPermission2.isTree()
                                                    && Intrinsics.areEqual(
                                                            parsedPermission2.getName(), str2))
                                            ? 0
                                            : i3 + 1;
                        }
                    }
                    ((MutableIndexedMap)
                                    MutableAccessState.mutateSystemState$default(mutableAccessState)
                                            .permissionTreesReference
                                            .mutate())
                            .map.removeAt(size);
                }
                size--;
            }
            IndexedMap permissions4 = mutableAccessState.getSystemState().getPermissions();
            int size4 = permissions4.map.size() - 1;
            for (i = -1; i < size4; i = -1) {
                Object keyAt = permissions4.map.keyAt(size4);
                Permission permission = (Permission) permissions4.map.valueAt(size4);
                String str3 = (String) keyAt;
                if (permission.type == 2
                        && (findPermissionTree =
                                        findPermissionTree(
                                                mutateStateScope2, permission.permissionInfo.name))
                                != null) {
                    PermissionInfo permissionInfo = new PermissionInfo(permission.permissionInfo);
                    permissionInfo.packageName = findPermissionTree.permissionInfo.packageName;
                    permission =
                            Permission.copy$default(
                                    permission, permissionInfo, z, findPermissionTree.appId);
                }
                MutableAccessState.mutateSystemState$default(mutableAccessState)
                        .mutatePermissions()
                        .map
                        .setValueAt(size4, permission);
                if (Intrinsics.areEqual(permission.permissionInfo.packageName, str)) {
                    if (packageState != null) {
                        Intrinsics.checkNotNull(androidPackage);
                        List permissions5 = androidPackage.getPermissions();
                        int size5 = permissions5.size();
                        for (int i5 = 0; i5 < size5; i5++) {
                            ParsedPermission parsedPermission3 =
                                    (ParsedPermission) permissions5.get(i5);
                            if (!parsedPermission3.isTree()
                                    && Intrinsics.areEqual(parsedPermission3.getName(), str3)) {
                                break;
                            }
                        }
                    }
                    if (androidPackage2 != null
                            && (permissions = androidPackage2.getPermissions()) != null) {
                        int size6 = permissions.size();
                        for (int i6 = 0; i6 < size6; i6++) {
                            ParsedPermission parsedPermission4 =
                                    (ParsedPermission) permissions.get(i6);
                            if (parsedPermission4.isTree()
                                    || !Intrinsics.areEqual(parsedPermission4.getName(), str3)) {}
                        }
                    }
                    MutableIntSet userIds = mutableAccessState.getExternalState().getUserIds();
                    int size7 = userIds.array.size();
                    int i7 = 0;
                    while (i7 < size7) {
                        int keyAt2 = userIds.array.keyAt(i7);
                        MutableIntReferenceMap appIdPackageNames =
                                mutableAccessState.getExternalState().getAppIdPackageNames();
                        int size8 = appIdPackageNames.array.size();
                        int i8 = 0;
                        while (i8 < size8) {
                            int keyAt3 = appIdPackageNames.array.keyAt(i8);
                            updatePermissionFlags(mutateStateScope, keyAt3, keyAt2, str3, -1, 0);
                            i8++;
                            size4 = size4;
                            userIds = userIds;
                            permissions4 = permissions4;
                            appIdPackageNames = appIdPackageNames;
                            i7 = i7;
                            size7 = size7;
                            str3 = str3;
                        }
                        i7++;
                    }
                    i2 = size4;
                    indexedMap = permissions4;
                    MutableAccessState.mutateSystemState$default(mutableAccessState)
                            .mutatePermissions()
                            .map
                            .removeAt(i2);
                    mutableIndexedSet.add(str3);
                    size4 = i2 - 1;
                    mutateStateScope2 = mutateStateScope;
                    permissions4 = indexedMap;
                    z = true;
                }
                i2 = size4;
                indexedMap = permissions4;
                size4 = i2 - 1;
                mutateStateScope2 = mutateStateScope;
                permissions4 = indexedMap;
                z = true;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x015f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0160  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePermissionExemptFlags(
            com.android.server.permission.access.MutateStateScope r17,
            int r18,
            int r19,
            com.android.server.permission.access.permission.Permission r20,
            int r21,
            int r22) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy.updatePermissionExemptFlags(com.android.server.permission.access.MutateStateScope,"
                    + " int, int, com.android.server.permission.access.permission.Permission, int,"
                    + " int):void");
    }

    public final boolean updatePermissionFlags(
            MutateStateScope mutateStateScope, int i, int i2, String str, int i3, int i4) {
        if (!mutateStateScope.newState.getUserStates().array.contains(i2)) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i2,
                    "Unable to update permission flags for missing user ",
                    "AppIdPermissionPolicy");
            return false;
        }
        Immutable immutable = mutateStateScope.newState.getUserStates().get(i2);
        Intrinsics.checkNotNull(immutable);
        int intValue =
                ((Number)
                                IndexedMapExtensionsKt.getWithDefault(
                                        (IndexedMap)
                                                ((MutableUserState) immutable)
                                                        .getAppIdPermissionFlags()
                                                        .get(i),
                                        str,
                                        0))
                        .intValue();
        int i5 = (i3 & i4) | ((~i3) & intValue);
        if (intValue == i5) {
            return false;
        }
        MutableUserState mutateUserState = mutateStateScope.newState.mutateUserState(i2, 1);
        Intrinsics.checkNotNull(mutateUserState);
        MutableIntReferenceMap mutableIntReferenceMap =
                (MutableIntReferenceMap) mutateUserState.appIdPermissionFlagsReference.mutate();
        Immutable mutate = mutableIntReferenceMap.mutate(i);
        if (mutate == null) {
            mutate = new MutableIndexedMap();
            mutableIntReferenceMap.put(i, mutate);
        }
        MutableIndexedMap mutableIndexedMap = (MutableIndexedMap) mutate;
        IndexedMapExtensionsKt.putWithDefault(mutableIndexedMap, str, Integer.valueOf(i5), 0);
        if (mutableIndexedMap.map.isEmpty()) {
            IntReferenceMapExtensionsKt.minusAssign(mutableIntReferenceMap, i);
        }
        MutableIndexedListSet mutableIndexedListSet = this.onPermissionFlagsChangedListeners;
        int size = mutableIndexedListSet.list.size();
        for (int i6 = 0; i6 < size; i6++) {
            ((OnPermissionFlagsChangedListener) mutableIndexedListSet.list.get(i6))
                    .onPermissionFlagsChanged(i, i2, intValue, i5, str);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x023d A[ORIG_RETURN, RETURN] */
    @Override // com.android.server.permission.access.SchemePolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void upgradePackageState(
            com.android.server.permission.access.MutateStateScope r24,
            com.android.server.pm.pkg.PackageState r25,
            int r26,
            int r27) {
        /*
            Method dump skipped, instructions count: 574
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.AppIdPermissionPolicy.upgradePackageState(com.android.server.permission.access.MutateStateScope,"
                    + " com.android.server.pm.pkg.PackageState, int, int):void");
    }
}
