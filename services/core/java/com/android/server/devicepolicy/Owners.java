package com.android.server.devicepolicy;

import android.app.ActivityManagerInternal;
import android.app.AppOpsManagerInternal;
import android.app.admin.DevicePolicyManager;
import android.app.admin.SystemUpdatePolicy;
import android.content.ComponentName;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.OwnersData.ProfileOwnerReadWriter;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda0;
import com.android.server.pm.ProtectedPackages;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public final class Owners {
    public final ActivityManagerInternal mActivityManagerInternal;
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public final OwnersData mData;
    public final DeviceStateCacheImpl mDeviceStateCache;
    public final PackageManagerInternal mPackageManagerInternal;
    public boolean mSystemReady;
    public final UserManager mUserManager;
    public final UserManagerInternal mUserManagerInternal;

    public Owners(
            UserManager userManager,
            UserManagerInternal userManagerInternal,
            PackageManagerInternal packageManagerInternal,
            ActivityTaskManagerInternal activityTaskManagerInternal,
            ActivityManagerInternal activityManagerInternal,
            DeviceStateCacheImpl deviceStateCacheImpl,
            PolicyPathProvider policyPathProvider) {
        this.mUserManager = userManager;
        this.mUserManagerInternal = userManagerInternal;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mDeviceStateCache = deviceStateCacheImpl;
        this.mData = new OwnersData(policyPathProvider);
    }

    public final ComponentName getDeviceOwnerComponent() {
        ComponentName componentName;
        synchronized (this.mData) {
            try {
                OwnersData.OwnerInfo ownerInfo = this.mData.mDeviceOwner;
                componentName = ownerInfo != null ? ownerInfo.admin : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return componentName;
    }

    public File getDeviceOwnerFile() {
        return this.mData.getDeviceOwnerFile();
    }

    public final String getDeviceOwnerPackageName() {
        String str;
        synchronized (this.mData) {
            try {
                OwnersData.OwnerInfo ownerInfo = this.mData.mDeviceOwner;
                str = ownerInfo != null ? ownerInfo.packageName : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return str;
    }

    public final int getDeviceOwnerType(String str) {
        synchronized (this.mData) {
            try {
                if (!isDeviceOwnerTypeSetForDeviceOwner(str)) {
                    return 0;
                }
                return ((Integer) this.mData.mDeviceOwnerTypes.get(str)).intValue();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getDeviceOwnerUidLocked() {
        OwnersData ownersData = this.mData;
        OwnersData.OwnerInfo ownerInfo = ownersData.mDeviceOwner;
        if (ownerInfo == null) {
            return -1;
        }
        return this.mPackageManagerInternal.getPackageUid(
                ownerInfo.packageName, 4333568L, ownersData.mDeviceOwnerUserId);
    }

    public final int getDeviceOwnerUserId() {
        int i;
        synchronized (this.mData) {
            i = this.mData.mDeviceOwnerUserId;
        }
        return i;
    }

    public final Pair getDeviceOwnerUserIdAndComponent() {
        synchronized (this.mData) {
            try {
                OwnersData ownersData = this.mData;
                if (ownersData.mDeviceOwner == null) {
                    return null;
                }
                return Pair.create(
                        Integer.valueOf(ownersData.mDeviceOwnerUserId),
                        this.mData.mDeviceOwner.admin);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ComponentName getProfileOwnerComponent(int i) {
        ComponentName componentName;
        synchronized (this.mData) {
            try {
                OwnersData.OwnerInfo ownerInfo =
                        (OwnersData.OwnerInfo) this.mData.mProfileOwners.get(Integer.valueOf(i));
                componentName = ownerInfo != null ? ownerInfo.admin : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return componentName;
    }

    public File getProfileOwnerFile(int i) {
        return this.mData.getProfileOwnerFile(i);
    }

    public final Set getProfileOwnerKeys() {
        Set keySet;
        synchronized (this.mData) {
            keySet = this.mData.mProfileOwners.keySet();
        }
        return keySet;
    }

    public final String getProfileOwnerPackage(int i) {
        String str;
        synchronized (this.mData) {
            try {
                OwnersData.OwnerInfo ownerInfo =
                        (OwnersData.OwnerInfo) this.mData.mProfileOwners.get(Integer.valueOf(i));
                str = ownerInfo != null ? ownerInfo.packageName : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return str;
    }

    public final Pair getSystemUpdateFreezePeriodRecord() {
        Pair pair;
        synchronized (this.mData) {
            OwnersData ownersData = this.mData;
            pair = new Pair(ownersData.mSystemUpdateFreezeStart, ownersData.mSystemUpdateFreezeEnd);
        }
        return pair;
    }

    public final SystemUpdatePolicy getSystemUpdatePolicy() {
        SystemUpdatePolicy systemUpdatePolicy;
        synchronized (this.mData) {
            systemUpdatePolicy = this.mData.mSystemUpdatePolicy;
        }
        return systemUpdatePolicy;
    }

    public final boolean hasDeviceOwner() {
        boolean z;
        synchronized (this.mData) {
            z = this.mData.mDeviceOwner != null;
        }
        return z;
    }

    public final boolean hasProfileOwner(int i) {
        boolean z;
        synchronized (this.mData) {
            z = getProfileOwnerComponent(i) != null;
        }
        return z;
    }

    public final boolean isDeviceOwnerTypeSetForDeviceOwner(String str) {
        boolean z;
        synchronized (this.mData) {
            try {
                z =
                        !this.mData.mDeviceOwnerTypes.isEmpty()
                                && this.mData.mDeviceOwnerTypes.containsKey(str);
            } finally {
            }
        }
        return z;
    }

    public final boolean isProfileOwnerOfOrganizationOwnedDevice(int i) {
        boolean z;
        synchronized (this.mData) {
            try {
                OwnersData.OwnerInfo ownerInfo =
                        (OwnersData.OwnerInfo) this.mData.mProfileOwners.get(Integer.valueOf(i));
                z = ownerInfo != null ? ownerInfo.isOrganizationOwnedDevice : false;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final void load() {
        synchronized (this.mData) {
            try {
                int[] array =
                        this.mUserManager.getAliveUsers().stream()
                                .mapToInt(new Owners$$ExternalSyntheticLambda0())
                                .toArray();
                this.mData.load(array);
                int i = 0;
                if (DeviceConfig.getBoolean(
                        "device_policy_manager",
                        "deprecate_usermanagerinternal_devicepolicy",
                        true)) {
                    if (hasDeviceOwner()) {
                        OwnersData ownersData = this.mData;
                        this.mDeviceStateCache.mDeviceOwnerType.set(
                                ((Integer)
                                                ownersData.mDeviceOwnerTypes.getOrDefault(
                                                        ownersData.mDeviceOwner.packageName, 0))
                                        .intValue());
                    } else {
                        this.mDeviceStateCache.mDeviceOwnerType.set(-1);
                    }
                    int length = array.length;
                    while (i < length) {
                        int i2 = array[i];
                        this.mDeviceStateCache.setHasProfileOwner(i2, hasProfileOwner(i2));
                        i++;
                    }
                } else {
                    this.mUserManagerInternal.setDeviceManaged(hasDeviceOwner());
                    int length2 = array.length;
                    while (i < length2) {
                        int i3 = array[i];
                        this.mUserManagerInternal.setUserManaged(i3, hasProfileOwner(i3));
                        i++;
                    }
                }
                notifyChangeLocked();
                pushDeviceOwnerUidToActivityTaskManagerLocked();
                pushProfileOwnerUidsToActivityTaskManagerLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyChangeLocked() {
        String[] strArr = DevicePolicyManagerService.DELEGATIONS;
        DevicePolicyManager.invalidateBinderCaches();
        SparseArray sparseArray = new SparseArray();
        for (int size = this.mData.mProfileOwners.size() - 1; size >= 0; size--) {
            sparseArray.put(
                    ((Integer) this.mData.mProfileOwners.keyAt(size)).intValue(),
                    ((OwnersData.OwnerInfo) this.mData.mProfileOwners.valueAt(size)).packageName);
        }
        OwnersData ownersData = this.mData;
        OwnersData.OwnerInfo ownerInfo = ownersData.mDeviceOwner;
        String str = null;
        String str2 = ownerInfo != null ? ownerInfo.packageName : null;
        PackageManagerInternal packageManagerInternal = this.mPackageManagerInternal;
        int i = ownersData.mDeviceOwnerUserId;
        PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
        ProtectedPackages protectedPackages = PackageManagerService.this.mProtectedPackages;
        synchronized (protectedPackages) {
            protectedPackages.mDeviceOwnerUserId = i;
            if (i != -10000) {
                str = str2;
            }
            protectedPackages.mDeviceOwnerPackage = str;
            protectedPackages.mProfileOwnerPackages = sparseArray.clone();
        }
        ArraySet arraySet = new ArraySet();
        if (str2 != null) {
            arraySet.add(Integer.valueOf(i));
        }
        int size2 = sparseArray.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (sparseArray.valueAt(i2) != null) {
                int keyAt = sparseArray.keyAt(i2);
                PackageManagerService packageManagerService = PackageManagerService.this;
                Computer snapshotComputer = packageManagerService.snapshotComputer();
                packageManagerService.mSuspendPackageHelper.removeSuspensionsBySuspendingPackage(
                        snapshotComputer,
                        snapshotComputer.getAllAvailablePackageNames(),
                        new PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda0(),
                        keyAt);
            }
        }
        pushToActivityManagerLocked();
        pushToAppOpsLocked();
    }

    public final void pushDeviceOwnerUidToActivityTaskManagerLocked() {
        ActivityTaskManagerInternal activityTaskManagerInternal = this.mActivityTaskManagerInternal;
        int deviceOwnerUidLocked = getDeviceOwnerUidLocked();
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityTaskManagerService.this.mDeviceOwnerUid = deviceOwnerUidLocked;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void pushProfileOwnerUidsToActivityTaskManagerLocked() {
        ActivityTaskManagerInternal activityTaskManagerInternal = this.mActivityTaskManagerInternal;
        ArraySet arraySet = new ArraySet();
        int i = 0;
        while (true) {
            OwnersData ownersData = this.mData;
            if (i >= ownersData.mProfileOwners.size()) {
                ActivityTaskManagerService.LocalService localService =
                        (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        ActivityTaskManagerService.this.mProfileOwnerUids = arraySet;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            }
            int intValue = ((Integer) ownersData.mProfileOwners.keyAt(i)).intValue();
            arraySet.add(
                    Integer.valueOf(
                            this.mPackageManagerInternal.getPackageUid(
                                    ((OwnersData.OwnerInfo) ownersData.mProfileOwners.valueAt(i))
                                            .packageName,
                                    4333568L,
                                    intValue)));
            i++;
        }
    }

    public final void pushToActivityManagerLocked() {
        this.mActivityManagerInternal.setDeviceOwnerUid(getDeviceOwnerUidLocked());
        ArraySet arraySet = new ArraySet();
        OwnersData ownersData = this.mData;
        for (int size = ownersData.mProfileOwners.size() - 1; size >= 0; size--) {
            int packageUid =
                    this.mPackageManagerInternal.getPackageUid(
                            ((OwnersData.OwnerInfo) ownersData.mProfileOwners.valueAt(size))
                                    .packageName,
                            4333568L,
                            ((Integer) ownersData.mProfileOwners.keyAt(size)).intValue());
            if (packageUid >= 0) {
                arraySet.add(Integer.valueOf(packageUid));
            }
        }
        this.mActivityManagerInternal.setProfileOwnerUid(arraySet);
    }

    public final void pushToAppOpsLocked() {
        int deviceOwnerUidLocked;
        OwnersData ownersData = this.mData;
        if (this.mSystemReady) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SparseIntArray sparseIntArray = new SparseIntArray();
                if (ownersData.mDeviceOwner != null
                        && (deviceOwnerUidLocked = getDeviceOwnerUidLocked()) >= 0) {
                    sparseIntArray.put(ownersData.mDeviceOwnerUserId, deviceOwnerUidLocked);
                }
                ArrayMap arrayMap = ownersData.mProfileOwners;
                if (arrayMap != null) {
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        int packageUid =
                                this.mPackageManagerInternal.getPackageUid(
                                        ((OwnersData.OwnerInfo)
                                                        ownersData.mProfileOwners.valueAt(size))
                                                .packageName,
                                        4333568L,
                                        ((Integer) ownersData.mProfileOwners.keyAt(size))
                                                .intValue());
                        if (packageUid >= 0) {
                            sparseIntArray.put(
                                    ((Integer) ownersData.mProfileOwners.keyAt(size)).intValue(),
                                    packageUid);
                        }
                    }
                }
                AppOpsManagerInternal appOpsManagerInternal =
                        (AppOpsManagerInternal)
                                LocalServices.getService(AppOpsManagerInternal.class);
                if (appOpsManagerInternal != null) {
                    if (sparseIntArray.size() <= 0) {
                        sparseIntArray = null;
                    }
                    appOpsManagerInternal.setDeviceAndProfileOwners(sparseIntArray);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void removeProfileOwner(int i) {
        synchronized (this.mData) {
            try {
                this.mData.mProfileOwners.remove(Integer.valueOf(i));
                if (DeviceConfig.getBoolean(
                        "device_policy_manager",
                        "deprecate_usermanagerinternal_devicepolicy",
                        true)) {
                    this.mDeviceStateCache.setHasProfileOwner(i, false);
                } else {
                    this.mUserManagerInternal.setUserManaged(i, false);
                }
                notifyChangeLocked();
                pushProfileOwnerUidsToActivityTaskManagerLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDeviceOwner(int i, ComponentName componentName) {
        if (i < 0) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Invalid user id for device owner user: ", "DevicePolicyManagerService");
            return;
        }
        synchronized (this.mData) {
            try {
                this.mData.mDeviceOwner = new OwnersData.OwnerInfo(componentName, null, null, true);
                this.mData.mDeviceOwnerUserId = i;
                if (DeviceConfig.getBoolean(
                        "device_policy_manager",
                        "deprecate_usermanagerinternal_devicepolicy",
                        true)) {
                    OwnersData ownersData = this.mData;
                    this.mDeviceStateCache.mDeviceOwnerType.set(
                            ((Integer)
                                            ownersData.mDeviceOwnerTypes.getOrDefault(
                                                    ownersData.mDeviceOwner.packageName, 0))
                                    .intValue());
                } else {
                    this.mUserManagerInternal.setDeviceManaged(true);
                }
                notifyChangeLocked();
                pushDeviceOwnerUidToActivityTaskManagerLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setProfileOwner(int i, ComponentName componentName) {
        synchronized (this.mData) {
            try {
                this.mData.mProfileOwners.put(
                        Integer.valueOf(i),
                        new OwnersData.OwnerInfo(componentName, null, null, false));
                if (DeviceConfig.getBoolean(
                        "device_policy_manager",
                        "deprecate_usermanagerinternal_devicepolicy",
                        true)) {
                    this.mDeviceStateCache.setHasProfileOwner(i, true);
                } else {
                    this.mUserManagerInternal.setUserManaged(i, true);
                }
                notifyChangeLocked();
                pushProfileOwnerUidsToActivityTaskManagerLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setSystemUpdateFreezePeriodRecord(
            LocalDate localDate, LocalDate localDate2) {
        boolean z;
        boolean z2;
        synchronized (this.mData) {
            try {
                z = true;
                if (Objects.equals(this.mData.mSystemUpdateFreezeStart, localDate)) {
                    z2 = false;
                } else {
                    this.mData.mSystemUpdateFreezeStart = localDate;
                    z2 = true;
                }
                if (Objects.equals(this.mData.mSystemUpdateFreezeEnd, localDate2)) {
                    z = z2;
                } else {
                    this.mData.mSystemUpdateFreezeEnd = localDate2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final void systemReady() {
        synchronized (this.mData) {
            this.mSystemReady = true;
            pushToActivityManagerLocked();
            pushToAppOpsLocked();
        }
    }

    public final void writeDeviceOwner() {
        synchronized (this.mData) {
            String[] strArr = DevicePolicyManagerService.DELEGATIONS;
            DevicePolicyManager.invalidateBinderCaches();
            this.mData.writeDeviceOwner();
        }
    }

    public final void writeProfileOwner(int i) {
        synchronized (this.mData) {
            String[] strArr = DevicePolicyManagerService.DELEGATIONS;
            DevicePolicyManager.invalidateBinderCaches();
            OwnersData ownersData = this.mData;
            ownersData.getClass();
            ownersData.new ProfileOwnerReadWriter(i).writeToFileLocked();
        }
    }
}
