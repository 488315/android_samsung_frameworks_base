package com.android.server.devicepolicy;

import android.content.ComponentName;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Telephony;
import android.util.SparseArray;

import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda33
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda33(
            DevicePolicyManagerService devicePolicyManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
    }

    private final Object
            getOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda49() {
        long j;
        NetworkLoggingHandler networkLoggingHandler =
                this.f$0.mNetworkLogger.mNetworkLoggingHandler;
        synchronized (networkLoggingHandler) {
            try {
                long nanoTime =
                        (networkLoggingHandler.mLastFinalizationNanos
                                        + NetworkLoggingHandler.FORCE_FETCH_THROTTLE_NS)
                                - System.nanoTime();
                j = 0;
                if (nanoTime > 0) {
                    j = 1 + TimeUnit.NANOSECONDS.toMillis(nanoTime);
                } else {
                    Bundle finalizeBatchAndBuildAdminMessageLocked =
                            networkLoggingHandler.finalizeBatchAndBuildAdminMessageLocked();
                    if (finalizeBatchAndBuildAdminMessageLocked != null) {
                        networkLoggingHandler.notifyDeviceOwnerOrProfileOwner(
                                finalizeBatchAndBuildAdminMessageLocked);
                    }
                }
            } finally {
            }
        }
        return Long.valueOf(j);
    }

    private final Object
            getOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda63() {
        Boolean bool;
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        devicePolicyManagerService.getClass();
        try {
            synchronized (devicePolicyManagerService.getLockObject()) {
                Slogf.i(
                        "DevicePolicyManager",
                        "Started device policies migration to the device policy engine.");
                Binder.withCleanCallingIdentity(
                        new DevicePolicyManagerService$$ExternalSyntheticLambda57(
                                9, devicePolicyManagerService));
                Binder.withCleanCallingIdentity(
                        new DevicePolicyManagerService$$ExternalSyntheticLambda57(
                                8, devicePolicyManagerService));
                Binder.withCleanCallingIdentity(
                        new DevicePolicyManagerService$$ExternalSyntheticLambda57(
                                7, devicePolicyManagerService));
                Owners owners = devicePolicyManagerService.mOwners;
                synchronized (owners.mData) {
                    OwnersData ownersData = owners.mData;
                    ownersData.mMigratedToPolicyEngine = true;
                    ownersData.writeDeviceOwner();
                }
                bool = Boolean.TRUE;
            }
            return bool;
        } catch (Exception e) {
            DevicePolicyEngine devicePolicyEngine = devicePolicyManagerService.mDevicePolicyEngine;
            devicePolicyEngine.clear();
            devicePolicyEngine.write();
            Slogf.e(
                    "DevicePolicyManager",
                    e,
                    "Error occurred during device policy migration, will reattempt on the next"
                        + " system server restart.",
                    new Object[0]);
            return Boolean.FALSE;
        }
    }

    private final Object
            getOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda76() {
        boolean isUserAffiliatedWithDeviceLocked;
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        int currentForegroundUserId = devicePolicyManagerService.getCurrentForegroundUserId();
        synchronized (devicePolicyManagerService.getLockObject()) {
            isUserAffiliatedWithDeviceLocked =
                    devicePolicyManagerService.isUserAffiliatedWithDeviceLocked(
                            currentForegroundUserId);
        }
        if (!isUserAffiliatedWithDeviceLocked) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(UserHandle.of(currentForegroundUserId));
        return arrayList;
    }

    public final Object getOrThrow() {
        boolean z;
        SparseArray sparseArray;
        boolean z2;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                devicePolicyManagerService.getClass();
                try {
                    if (devicePolicyManagerService.isManagedKioskInternal()) {
                        devicePolicyManagerService.mInjector.getClass();
                        if (((PowerManagerInternal)
                                        LocalServices.getService(PowerManagerInternal.class))
                                .wasDeviceIdleFor(30000L)) {
                            z = true;
                            return Boolean.valueOf(z);
                        }
                    }
                    z = false;
                    return Boolean.valueOf(z);
                } catch (RemoteException e) {
                    throw new IllegalStateException(e);
                }
            case 1:
                return Boolean.valueOf(
                        this.f$0.mInjector.settingsGlobalGetInt(
                                        "wifi_device_owner_configs_lockdown")
                                > 0);
            case 2:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                List<UserInfo> aliveUsers =
                        UserManager.get(devicePolicyManagerService2.mInjector.mContext)
                                .getAliveUsers();
                ArrayList arrayList = new ArrayList();
                for (UserInfo userInfo : aliveUsers) {
                    UserHandle userHandle = userInfo.getUserHandle();
                    if (!userHandle.isSystem()
                            && !devicePolicyManagerService2.isManagedProfile(
                                    userHandle.getIdentifier())) {
                        arrayList.add(userInfo.getUserHandle());
                    }
                }
                return arrayList;
            case 3:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                for (UserInfo userInfo2 : devicePolicyManagerService3.mUserManager.getUsers()) {
                    synchronized (devicePolicyManagerService3.getLockObject()) {
                        try {
                            List<ComponentName> activeAdmins =
                                    devicePolicyManagerService3.getActiveAdmins(userInfo2.id);
                            if (activeAdmins != null) {
                                for (ComponentName componentName : activeAdmins) {
                                    if (!devicePolicyManagerService3.isAdminTestOnlyLocked(
                                            userInfo2.id, componentName)) {
                                        devicePolicyManagerService3.mInjector.getClass();
                                        KnoxPolicyHelper knoxPolicyHelper =
                                                devicePolicyManagerService3.mKnoxPolicyHelper;
                                        String packageName = componentName.getPackageName();
                                        knoxPolicyHelper.getClass();
                                        if (!"com.samsung.android.kgclient".equals(packageName)
                                                && !"com.nttdocomo.android.remotelock"
                                                        .equals(packageName)
                                                && !"com.nttdocomo.android.wipe"
                                                        .equals(packageName)) {
                                            return Boolean.TRUE;
                                        }
                                    }
                                }
                                break;
                            }
                        } finally {
                        }
                    }
                }
                return Boolean.FALSE;
            case 4:
                DevicePolicyManagerService devicePolicyManagerService4 = this.f$0;
                Owners owners = devicePolicyManagerService4.mOwners;
                owners.getClass();
                ArrayList arrayList2 = new ArrayList();
                synchronized (owners.mData) {
                    try {
                        OwnersData ownersData = owners.mData;
                        OwnersData.OwnerInfo ownerInfo = ownersData.mDeviceOwner;
                        if (ownerInfo != null) {
                            arrayList2.add(
                                    new OwnerShellData(
                                            ownersData.mDeviceOwnerUserId,
                                            -10000,
                                            ownerInfo.admin,
                                            true,
                                            false,
                                            false));
                        }
                        for (int i = 0; i < owners.mData.mProfileOwners.size(); i++) {
                            arrayList2.add(
                                    new OwnerShellData(
                                            ((Integer) owners.mData.mProfileOwners.keyAt(i))
                                                    .intValue(),
                                            -10000,
                                            ((OwnersData.OwnerInfo)
                                                            owners.mData.mProfileOwners.valueAt(i))
                                                    .admin,
                                            false,
                                            true,
                                            false));
                        }
                    } finally {
                    }
                }
                synchronized (devicePolicyManagerService4.getLockObject()) {
                    for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                        try {
                            OwnerShellData ownerShellData = (OwnerShellData) arrayList2.get(i2);
                            ownerShellData.isAffiliated =
                                    devicePolicyManagerService4.isUserAffiliatedWithDeviceLocked(
                                            ownerShellData.userId);
                        } finally {
                        }
                    }
                    sparseArray = devicePolicyManagerService4.mUserData;
                }
                for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                    DevicePolicyData devicePolicyData =
                            (DevicePolicyData) devicePolicyManagerService4.mUserData.valueAt(i3);
                    int keyAt = sparseArray.keyAt(i3);
                    int profileParentId =
                            devicePolicyManagerService4.mUserManagerInternal.getProfileParentId(
                                    keyAt);
                    if (profileParentId != keyAt) {
                        for (int i4 = 0; i4 < devicePolicyData.mAdminList.size(); i4++) {
                            arrayList2.add(
                                    new OwnerShellData(
                                            keyAt,
                                            profileParentId,
                                            ((ActiveAdmin) devicePolicyData.mAdminList.get(i4))
                                                    .info.getComponent(),
                                            false,
                                            false,
                                            true));
                        }
                    }
                }
                return arrayList2;
            case 5:
                Owners owners2 = this.f$0.mOwners;
                synchronized (owners2.mData) {
                    z2 = owners2.mData.mMigratedToPolicyEngine;
                }
                return Boolean.valueOf(!z2);
            case 6:
                return getOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda63();
            case 7:
                return Integer.valueOf(this.f$0.getHeadlessDeviceOwnerModeForDeviceOwner());
            case 8:
                DevicePolicyManagerService devicePolicyManagerService5 = this.f$0;
                List aliveUsers2 = devicePolicyManagerService5.mUserManager.getAliveUsers();
                for (int i5 = 0; i5 < aliveUsers2.size(); i5++) {
                    int i6 = ((UserInfo) aliveUsers2.get(i5)).id;
                    if (!devicePolicyManagerService5.isUserAffiliatedWithDeviceLocked(i6)) {
                        Slogf.d("DevicePolicyManager", "User id " + i6 + " not affiliated.");
                        return Boolean.FALSE;
                    }
                }
                return Boolean.TRUE;
            case 9:
                DevicePolicyManagerService devicePolicyManagerService6 = this.f$0;
                if (devicePolicyManagerService6.getUserData(0)
                        .mBypassDevicePolicyManagementRoleQualifications) {
                    return Boolean.TRUE;
                }
                return Boolean.valueOf(
                        devicePolicyManagerService6.nonTestNonPrecreatedUsersExist()
                                ? false
                                : !devicePolicyManagerService6.hasIncompatibleAccountsOnAnyUser());
            case 10:
                DevicePolicyManagerService devicePolicyManagerService7 = this.f$0;
                UserHandle mainUser = devicePolicyManagerService7.mUserManager.getMainUser();
                return Integer.valueOf(
                        mainUser == null
                                ? -10000
                                : devicePolicyManagerService7.getManagedUserId(
                                        mainUser.getIdentifier()));
            case 11:
                return this.f$0
                        .mContext
                        .getContentResolver()
                        .query(Telephony.Carriers.ENFORCE_MANAGED_URI, null, null, null, null);
            case 12:
                DevicePolicyManagerService devicePolicyManagerService8 = this.f$0;
                for (UserInfo userInfo3 : devicePolicyManagerService8.mUserManager.getUsers()) {
                    if (userInfo3.isManagedProfile()
                            && devicePolicyManagerService8.getProfileOwnerAsUser(userInfo3.id)
                                    != null
                            && devicePolicyManagerService8.isProfileOwnerOfOrganizationOwnedDevice(
                                    userInfo3.id)) {
                        return devicePolicyManagerService8.getActiveAdminUncheckedLocked(
                                userInfo3.id,
                                devicePolicyManagerService8.getProfileOwnerAsUser(userInfo3.id));
                    }
                }
                return null;
            case 13:
                return getOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda49();
            case 14:
                return Integer.valueOf(this.f$0.getCurrentForegroundUserId());
            case 15:
                return getOrThrow$com$android$server$devicepolicy$DevicePolicyManagerService$$ExternalSyntheticLambda76();
            default:
                return this.f$0.mLockPatternUtils.getDeviceOwnerInfo();
        }
    }
}
