package com.android.server.pm;

import android.app.AppOpsManager;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserPackage;
import android.os.Binder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.pm.pkg.SuspendParams;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import com.android.server.utils.WatchedArrayMap;
import com.samsung.android.server.pm.PmLog;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SuspendPackageHelper {
    public final BroadcastHelper mBroadcastHelper;
    public final PackageManagerServiceInjector mInjector;
    public final PackageManagerService mPm;
    public final ProtectedPackages mProtectedPackages;

    public SuspendPackageHelper(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector, BroadcastHelper broadcastHelper, ProtectedPackages protectedPackages) {
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mBroadcastHelper = broadcastHelper;
        this.mProtectedPackages = protectedPackages;
    }

    public static Bundle getSuspendedPackageAppExtras(int i, int i2, Computer computer, String str) {
        PersistableBundle persistableBundle;
        PackageSetting packageStateInternal = computer.getPackageStateInternal(i2, str);
        if (packageStateInternal == null) {
            return null;
        }
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        Bundle bundle = new Bundle();
        if (userStateOrDefault.isSuspended()) {
            for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().mStorage.size(); i3++) {
                SuspendParams suspendParams = (SuspendParams) userStateOrDefault.getSuspendParams().mStorage.valueAt(i3);
                if (suspendParams != null && (persistableBundle = suspendParams.mAppExtras) != null) {
                    bundle.putAll(persistableBundle);
                }
            }
        }
        if (bundle.size() > 0) {
            return bundle;
        }
        return null;
    }

    public static UserPackage getSuspendingPackage(int i, int i2, Computer computer, String str) {
        PackageSetting packageStateInternal = computer.getPackageStateInternal(i2, str);
        UserPackage userPackage = null;
        if (packageStateInternal == null) {
            return null;
        }
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isSuspended()) {
            return null;
        }
        UserPackage userPackage2 = null;
        UserPackage userPackage3 = null;
        for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().mStorage.size(); i3++) {
            userPackage3 = (UserPackage) userStateOrDefault.getSuspendParams().mStorage.keyAt(i3);
            SuspendParams suspendParams = (SuspendParams) userStateOrDefault.getSuspendParams().mStorage.valueAt(i3);
            if ("android".equals(userPackage3.packageName)) {
                userPackage2 = userPackage3;
            }
            if (suspendParams.mQuarantined && userPackage == null) {
                userPackage = userPackage3;
            }
        }
        return userPackage != null ? userPackage : userPackage2 != null ? userPackage2 : userPackage3;
    }

    public final boolean[] canSuspendPackageForUser(Computer computer, String[] strArr, int i, int i2) {
        String deviceOwnerOrProfileOwnerPackage;
        long j;
        boolean[] zArr;
        String str;
        AndroidPackage androidPackage;
        boolean z;
        String[] strArr2 = strArr;
        PackageManagerService packageManagerService = this.mPm;
        PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        boolean[] zArr2 = new boolean[strArr2.length];
        ProtectedPackages protectedPackages = this.mProtectedPackages;
        boolean z2 = i2 == 1000 || ((deviceOwnerOrProfileOwnerPackage = protectedPackages.getDeviceOwnerOrProfileOwnerPackage(i)) != null && i2 == computer.getPackageUidInternal(i, i2, 0L, deviceOwnerOrProfileOwnerPackage));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DefaultAppProvider defaultAppProvider = (DefaultAppProvider) packageManagerServiceInjector.mDefaultAppProviderProducer.get(packageManagerServiceInjector.mPackageManager, packageManagerServiceInjector);
            String defaultHome = defaultAppProvider.getDefaultHome(i);
            String roleHolder = defaultAppProvider.getRoleHolder(i, "android.app.role.DIALER");
            String knownPackageName = getKnownPackageName(computer, 2, i);
            String knownPackageName2 = getKnownPackageName(computer, 3, i);
            String knownPackageName3 = getKnownPackageName(computer, 4, i);
            j = clearCallingIdentity;
            try {
                String knownPackageName4 = getKnownPackageName(computer, 7, i);
                int i3 = 0;
                while (i3 < strArr2.length) {
                    zArr2[i3] = false;
                    String str2 = strArr2[i3];
                    int i4 = i3;
                    if (packageManagerService.isPackageDeviceAdmin(i, str2)) {
                        zArr = zArr2;
                        Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": has an active device admin");
                    } else {
                        zArr = zArr2;
                        if (str2.equals(defaultHome)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": contains the active launcher");
                        } else if (str2.equals(knownPackageName)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": required for package installation");
                        } else if (str2.equals(knownPackageName2)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": required for package uninstallation");
                        } else if (str2.equals(knownPackageName3)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": required for package verification");
                        } else if (str2.equals(roleHolder)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": is the default dialer");
                        } else if (str2.equals(knownPackageName4)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": required for permissions management");
                        } else if (protectedPackages.isPackageStateProtected(i, str2)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": protected package");
                        } else if (!z2 && computer.getBlockUninstall(i, str2)) {
                            Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": blocked by admin");
                        } else if (packageManagerService.isRequiredSystemPackage(computer, str2, i)) {
                            PmLog.logDebugInfoAndLogcat("Cannot suspend package \"" + str2 + "\": required system package");
                        } else {
                            PackageSetting packageStateInternal = computer.getPackageStateInternal(str2);
                            if (packageStateInternal == null) {
                                androidPackage = null;
                                str = knownPackageName4;
                            } else {
                                str = knownPackageName4;
                                androidPackage = packageStateInternal.pkg;
                            }
                            if (androidPackage != null) {
                                int uid = UserHandle.getUid(i, packageStateInternal.mAppId);
                                z = z2;
                                if (androidPackage.isSdkLibrary()) {
                                    Slog.w("PackageManager", "Cannot suspend package: " + str2 + " providing SDK library: " + androidPackage.getSdkLibraryName());
                                } else if (androidPackage.isStaticSharedLibrary()) {
                                    Slog.w("PackageManager", "Cannot suspend package: " + str2 + " providing static shared library: " + androidPackage.getStaticSharedLibraryName());
                                } else if (((AppOpsManager) packageManagerServiceInjector.mGetSystemServiceProducer.produce(AppOpsManager.class)).checkOpNoThrow(124, uid, str2) == 0) {
                                    Slog.w("PackageManager", "Cannot suspend package \"" + str2 + "\": has OP_SYSTEM_EXEMPT_FROM_SUSPENSION set");
                                }
                                i3 = i4 + 1;
                                strArr2 = strArr;
                                knownPackageName4 = str;
                                zArr2 = zArr;
                                z2 = z;
                            } else {
                                z = z2;
                            }
                            if ("android".equals(str2)) {
                                Slog.w("PackageManager", "Cannot suspend the platform package: " + str2);
                            } else {
                                zArr[i4] = true;
                            }
                            i3 = i4 + 1;
                            strArr2 = strArr;
                            knownPackageName4 = str;
                            zArr2 = zArr;
                            z2 = z;
                        }
                    }
                    str = knownPackageName4;
                    z = z2;
                    i3 = i4 + 1;
                    strArr2 = strArr;
                    knownPackageName4 = str;
                    zArr2 = zArr;
                    z2 = z;
                }
                boolean[] zArr3 = zArr2;
                Binder.restoreCallingIdentity(j);
                return zArr3;
            } catch (Throwable th) {
                th = th;
                Binder.restoreCallingIdentity(j);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            j = clearCallingIdentity;
        }
    }

    public final String getKnownPackageName(Computer computer, int i, int i2) {
        String[] knownPackageNamesInternal = this.mPm.getKnownPackageNamesInternal(computer, i, i2);
        if (knownPackageNamesInternal.length > 0) {
            return knownPackageNamesInternal[0];
        }
        return null;
    }

    public final boolean isSuspendAllowedForUser(Computer computer, int i, int i2) {
        String deviceOwnerOrProfileOwnerPackage;
        UserManagerService userManagerService = this.mInjector.getUserManagerService();
        return i2 == 1000 || ((deviceOwnerOrProfileOwnerPackage = this.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(i)) != null && i2 == computer.getPackageUidInternal(i, i2, 0L, deviceOwnerOrProfileOwnerPackage)) || !(userManagerService.hasUserRestriction("no_control_apps", i) || userManagerService.hasUserRestriction("no_uninstall_apps", i));
    }

    public final void removeSuspensionsBySuspendingPackage(Computer computer, String[] strArr, Predicate predicate, final int i) {
        ArraySet arraySet;
        String[] strArr2 = strArr;
        ArrayList arrayList = new ArrayList();
        IntArray intArray = new IntArray();
        final ArrayMap arrayMap = new ArrayMap();
        int length = strArr2.length;
        int i2 = 0;
        while (i2 < length) {
            String str = strArr2[i2];
            PackageSetting packageStateInternal = computer.getPackageStateInternal(str);
            PackageUserStateInternal userStateOrDefault = packageStateInternal == null ? null : packageStateInternal.getUserStateOrDefault(i);
            if (userStateOrDefault != null && userStateOrDefault.isSuspended()) {
                WatchedArrayMap suspendParams = userStateOrDefault.getSuspendParams();
                int i3 = 0;
                for (int i4 = 0; i4 < suspendParams.mStorage.size(); i4++) {
                    UserPackage userPackage = (UserPackage) suspendParams.mStorage.keyAt(i4);
                    if (predicate.test(userPackage)) {
                        ArraySet arraySet2 = (ArraySet) arrayMap.get(str);
                        if (arraySet2 == null) {
                            arraySet = new ArraySet();
                            arrayMap.put(str, arraySet);
                        } else {
                            arraySet = arraySet2;
                        }
                        arraySet.add(userPackage);
                        i3++;
                    }
                }
                if (i3 == suspendParams.mStorage.size()) {
                    arrayList.add(packageStateInternal.mName);
                    intArray.add(UserHandle.getUid(i, packageStateInternal.mAppId));
                }
            }
            i2++;
            strArr2 = strArr;
        }
        Consumer consumer = new Consumer() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WatchedArrayMap watchedArrayMap;
                ArrayMap arrayMap2 = arrayMap;
                int i5 = i;
                PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                for (int i6 = 0; i6 < arrayMap2.size(); i6++) {
                    String str2 = (String) arrayMap2.keyAt(i6);
                    ArraySet arraySet3 = (ArraySet) arrayMap2.valueAt(i6);
                    PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper userState = packageStateMutator.forPackage(str2).userState(i5);
                    for (int i7 = 0; i7 < arraySet3.size(); i7++) {
                        UserPackage userPackage2 = (UserPackage) arraySet3.valueAt(i7);
                        PackageUserStateImpl packageUserStateImpl = userState.mUserState;
                        if (packageUserStateImpl != null && (watchedArrayMap = packageUserStateImpl.mSuspendParams) != null) {
                            watchedArrayMap.remove(userPackage2);
                            packageUserStateImpl.onChanged$4();
                        }
                    }
                }
            }
        };
        PackageManagerService packageManagerService = this.mPm;
        packageManagerService.commitPackageStateMutation(consumer);
        packageManagerService.scheduleWritePackageRestrictions(i);
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        if (arrayList.isEmpty()) {
            return;
        }
        String[] strArr3 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        BroadcastHelper broadcastHelper = this.mBroadcastHelper;
        broadcastHelper.getClass();
        broadcastHelper.mHandler.post(new BroadcastHelper$$ExternalSyntheticLambda5(broadcastHelper, false, i, strArr3, snapshotComputer, "android.intent.action.MY_PACKAGE_UNSUSPENDED"));
        this.mBroadcastHelper.sendPackagesSuspendedOrUnsuspendedForUser(snapshotComputer, "android.intent.action.PACKAGES_UNSUSPENDED", strArr3, intArray.toArray(), false, i);
    }

    public final String[] setPackagesSuspended(Computer computer, String[] strArr, final boolean z, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, final UserPackage userPackage, final int i, int i2, boolean z2) {
        IntArray intArray;
        boolean[] zArr;
        Computer computer2 = computer;
        String[] strArr2 = strArr;
        int i3 = i2;
        if (ArrayUtils.isEmpty(strArr)) {
            return strArr2;
        }
        if (z && !z2 && !isSuspendAllowedForUser(computer2, i, i3)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Cannot suspend due to restrictions on user ", "PackageManager");
            return strArr2;
        }
        SuspendParams suspendParams = z ? new SuspendParams(suspendDialogInfo, persistableBundle, persistableBundle2, z2) : null;
        ArrayList arrayList = new ArrayList(strArr2.length);
        ArrayList arrayList2 = new ArrayList(strArr2.length);
        IntArray intArray2 = new IntArray(strArr2.length);
        final ArraySet arraySet = new ArraySet(strArr2.length);
        IntArray intArray3 = new IntArray(strArr2.length);
        boolean[] canSuspendPackageForUser = z ? canSuspendPackageForUser(computer2, strArr2, i, i3) : null;
        int i4 = 0;
        while (i4 < strArr2.length) {
            String str = strArr2[i4];
            if (userPackage.packageName.equals(str) && userPackage.userId == i) {
                StringBuilder sb = new StringBuilder("Suspending package: ");
                sb.append(userPackage);
                sb.append(" trying to ");
                intArray = intArray3;
                ProfileService$1$$ExternalSyntheticOutline0.m(sb, z ? "" : "un", "suspend itself. Ignoring", "PackageManager");
                arrayList.add(str);
            } else {
                intArray = intArray3;
                PackageSetting packageStateInternal = computer2.getPackageStateInternal(str);
                if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(i).isInstalled() || computer2.shouldFilterApplication(packageStateInternal, i3, i)) {
                    intArray3 = intArray;
                    zArr = canSuspendPackageForUser;
                    PinnerService$$ExternalSyntheticOutline0.m("Could not find package setting for package: ", str, ". Skipping suspending/un-suspending.", "PackageManager");
                    arrayList.add(str);
                } else if (canSuspendPackageForUser == null || canSuspendPackageForUser[i4]) {
                    WatchedArrayMap suspendParams2 = packageStateInternal.getUserStateOrDefault(i).getSuspendParams();
                    boolean z3 = !Objects.equals(suspendParams2 == null ? null : (SuspendParams) suspendParams2.mStorage.get(userPackage), suspendParams);
                    if (!z || z3) {
                        zArr = canSuspendPackageForUser;
                        boolean z4 = !z && CollectionUtils.size(suspendParams2) == 1 && suspendParams2.mStorage.containsKey(userPackage);
                        if (z || z4) {
                            arrayList2.add(str);
                            intArray2.add(UserHandle.getUid(i, packageStateInternal.mAppId));
                        }
                        if (z3) {
                            arraySet.add(str);
                            intArray3 = intArray;
                            intArray3.add(UserHandle.getUid(i, packageStateInternal.mAppId));
                        } else {
                            intArray3 = intArray;
                            PinnerService$$ExternalSyntheticOutline0.m("No change is needed for package: ", str, ". Skipping suspending/un-suspending.", "PackageManager");
                        }
                    } else {
                        arrayList2.add(str);
                        intArray2.add(UserHandle.getUid(i, packageStateInternal.mAppId));
                    }
                } else {
                    arrayList.add(str);
                }
                i4++;
                computer2 = computer;
                strArr2 = strArr;
                canSuspendPackageForUser = zArr;
                i3 = i2;
            }
            intArray3 = intArray;
            zArr = canSuspendPackageForUser;
            i4++;
            computer2 = computer;
            strArr2 = strArr;
            canSuspendPackageForUser = zArr;
            i3 = i2;
        }
        final SuspendParams suspendParams3 = suspendParams;
        Consumer consumer = new Consumer() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WatchedArrayMap watchedArrayMap;
                ArraySet arraySet2 = arraySet;
                int i5 = i;
                boolean z5 = z;
                UserPackage userPackage2 = userPackage;
                SuspendParams suspendParams4 = suspendParams3;
                PackageStateMutator packageStateMutator = (PackageStateMutator) obj;
                int size = arraySet2.size();
                for (int i6 = 0; i6 < size; i6++) {
                    PackageStateMutator.StateWriteWrapper.UserStateWriteWrapper userState = packageStateMutator.forPackage((String) arraySet2.valueAt(i6)).userState(i5);
                    if (z5) {
                        PackageUserStateImpl packageUserStateImpl = userState.mUserState;
                        if (packageUserStateImpl != null) {
                            if (packageUserStateImpl.mSuspendParams == null) {
                                WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(0);
                                packageUserStateImpl.mSuspendParams = watchedArrayMap2;
                                watchedArrayMap2.registerObserver(packageUserStateImpl.mSnapshot);
                            }
                            if (!packageUserStateImpl.mSuspendParams.mStorage.containsKey(userPackage2) || !Objects.equals(packageUserStateImpl.mSuspendParams.mStorage.get(userPackage2), suspendParams4)) {
                                packageUserStateImpl.mSuspendParams.put(userPackage2, suspendParams4);
                                packageUserStateImpl.onChanged$4();
                            }
                        }
                    } else {
                        PackageUserStateImpl packageUserStateImpl2 = userState.mUserState;
                        if (packageUserStateImpl2 != null && (watchedArrayMap = packageUserStateImpl2.mSuspendParams) != null) {
                            watchedArrayMap.remove(userPackage2);
                            packageUserStateImpl2.onChanged$4();
                        }
                    }
                }
            }
        };
        PackageManagerService packageManagerService = this.mPm;
        packageManagerService.commitPackageStateMutation(consumer);
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        if (!arrayList2.isEmpty()) {
            String[] strArr3 = (String[]) arrayList2.toArray(new String[0]);
            this.mBroadcastHelper.sendPackagesSuspendedOrUnsuspendedForUser(snapshotComputer, z ? "android.intent.action.PACKAGES_SUSPENDED" : "android.intent.action.PACKAGES_UNSUSPENDED", strArr3, intArray2.toArray(), z2, i);
            BroadcastHelper broadcastHelper = this.mBroadcastHelper;
            broadcastHelper.getClass();
            broadcastHelper.mHandler.post(new BroadcastHelper$$ExternalSyntheticLambda5(broadcastHelper, z, i, strArr3, snapshotComputer, z ? "android.intent.action.MY_PACKAGE_SUSPENDED" : "android.intent.action.MY_PACKAGE_UNSUSPENDED"));
            packageManagerService.scheduleWritePackageRestrictions(i);
        }
        if (!arraySet.isEmpty()) {
            this.mBroadcastHelper.sendPackagesSuspendedOrUnsuspendedForUser(snapshotComputer, "android.intent.action.PACKAGES_SUSPENSION_CHANGED", (String[]) arraySet.toArray(new String[0]), intArray3.toArray(), z2, i);
        }
        return (String[]) arrayList.toArray(new String[0]);
    }
}
