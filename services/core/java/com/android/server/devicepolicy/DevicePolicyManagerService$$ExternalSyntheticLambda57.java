package com.android.server.devicepolicy;

import android.app.admin.BooleanPolicyValue;
import android.app.admin.LockTaskPolicy;
import android.app.admin.PackageSetPolicyValue;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.util.ArraySet;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda57
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda57(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runOrThrow() {
        int i;
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService =
                        (DevicePolicyManagerService) obj;
                devicePolicyManagerService.mEsidCalculator =
                        new EnterpriseSpecificIdCalculator(
                                devicePolicyManagerService.mInjector.mContext);
                return;
            case 1:
                ((DevicePolicyManagerService) obj).mInjector.settingsGlobalPutInt("auto_time", 1);
                return;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService2 =
                        (DevicePolicyManagerService) obj;
                ActiveAdmin deviceOwnerAdminLocked =
                        devicePolicyManagerService2.getDeviceOwnerAdminLocked();
                if (deviceOwnerAdminLocked != null) {
                    int identifier = deviceOwnerAdminLocked.getUserHandle().getIdentifier();
                    DevicePolicyData userData = devicePolicyManagerService2.getUserData(identifier);
                    List list = userData.mLockTaskPackages;
                    int i3 = userData.mLockTaskFeatures;
                    ArrayList arrayList = (ArrayList) list;
                    if (!arrayList.isEmpty()) {
                        devicePolicyManagerService2.mDevicePolicyEngine.setLocalPolicy(
                                PolicyDefinition.LOCK_TASK,
                                EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                        deviceOwnerAdminLocked.info.getComponent(),
                                        identifier,
                                        deviceOwnerAdminLocked),
                                new LockTaskPolicy(new HashSet(arrayList), i3),
                                identifier,
                                false);
                    }
                }
                for (int i4 : devicePolicyManagerService2.mUserManagerInternal.getUserIds()) {
                    ActiveAdmin profileOwnerLocked =
                            devicePolicyManagerService2.getProfileOwnerLocked(i4);
                    if (profileOwnerLocked != null
                            && devicePolicyManagerService2.canDPCManagedUserUseLockTaskLocked(i4)) {
                        DevicePolicyData userData2 = devicePolicyManagerService2.getUserData(i4);
                        List list2 = userData2.mLockTaskPackages;
                        int i5 = userData2.mLockTaskFeatures;
                        ArrayList arrayList2 = (ArrayList) list2;
                        if (!arrayList2.isEmpty()) {
                            devicePolicyManagerService2.mDevicePolicyEngine.setLocalPolicy(
                                    PolicyDefinition.LOCK_TASK,
                                    EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                            profileOwnerLocked.info.getComponent(),
                                            i4,
                                            profileOwnerLocked),
                                    new LockTaskPolicy(new HashSet(arrayList2), i5),
                                    i4,
                                    false);
                        }
                    }
                }
                return;
            case 3:
                ((DevicePolicyManagerService) obj)
                        .mContext.sendBroadcastAsUser(
                                new Intent("android.app.action.SYSTEM_UPDATE_POLICY_CHANGED"),
                                UserHandle.SYSTEM);
                return;
            case 4:
                if (((DevicePolicyManagerService) obj)
                        .mUserManager
                        .getUserInfo(UserHandle.getCallingUserId())
                        .isMain()) {
                    return;
                }
                Slogf.w(
                        "DevicePolicyManager",
                        "Only the system update service in the main user can broadcast update"
                            + " information.");
                return;
            case 5:
                DevicePolicyManagerService devicePolicyManagerService3 =
                        (DevicePolicyManagerService) obj;
                for (UserInfo userInfo : devicePolicyManagerService3.mUserManager.getUsers()) {
                    ActiveAdmin profileOwnerOrDeviceOwnerLocked =
                            devicePolicyManagerService3.getProfileOwnerOrDeviceOwnerLocked(
                                    userInfo.id);
                    if (profileOwnerOrDeviceOwnerLocked != null) {
                        ComponentName component =
                                profileOwnerOrDeviceOwnerLocked.info.getComponent();
                        int i6 = userInfo.id;
                        EnforcingAdmin createEnterpriseEnforcingAdmin =
                                EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                        component, i6, profileOwnerOrDeviceOwnerLocked);
                        if (devicePolicyManagerService3.isDeviceOwner(
                                profileOwnerOrDeviceOwnerLocked)) {
                            i = 0;
                        } else if (devicePolicyManagerService3.isProfileOwner(i6, component)
                                && devicePolicyManagerService3
                                        .isProfileOwnerOfOrganizationOwnedDevice(i6)) {
                            i = 2;
                        } else {
                            if (!devicePolicyManagerService3.isProfileOwner(i6, component)) {
                                throw new IllegalStateException("Invalid DO/PO state");
                            }
                            i = 1;
                        }
                        int i7 = i;
                        Iterator<String> it =
                                profileOwnerOrDeviceOwnerLocked
                                        .ensureUserRestrictions()
                                        .keySet()
                                        .iterator();
                        while (it.hasNext()) {
                            devicePolicyManagerService3.setBackwardCompatibleUserRestrictionLocked(
                                    i7, createEnterpriseEnforcingAdmin, i6, it.next(), true, false);
                        }
                        Iterator<String> it2 =
                                profileOwnerOrDeviceOwnerLocked
                                        .getParentActiveAdmin()
                                        .ensureUserRestrictions()
                                        .keySet()
                                        .iterator();
                        while (it2.hasNext()) {
                            devicePolicyManagerService3.setBackwardCompatibleUserRestrictionLocked(
                                    i7, createEnterpriseEnforcingAdmin, i6, it2.next(), true, true);
                        }
                    }
                }
                return;
            case 6:
                DevicePolicyManagerService devicePolicyManagerService4 =
                        (DevicePolicyManagerService) obj;
                for (UserInfo userInfo2 : devicePolicyManagerService4.mUserManager.getUsers()) {
                    DeviceStateCacheImpl deviceStateCacheImpl =
                            devicePolicyManagerService4.mStateCache;
                    int i8 = userInfo2.id;
                    deviceStateCacheImpl.setHasAffiliationWithDevice(
                            i8,
                            Boolean.valueOf(
                                    devicePolicyManagerService4.isUserAffiliatedWithDeviceLocked(
                                            i8)));
                }
                return;
            case 7:
                DevicePolicyManagerService devicePolicyManagerService5 =
                        (DevicePolicyManagerService) obj;
                Iterator it3 = devicePolicyManagerService5.mUserManager.getUsers().iterator();
                while (it3.hasNext()) {
                    ActiveAdmin profileOwnerOrDeviceOwnerLocked2 =
                            devicePolicyManagerService5.getProfileOwnerOrDeviceOwnerLocked(
                                    ((UserInfo) it3.next()).id);
                    if (profileOwnerOrDeviceOwnerLocked2 != null
                            && profileOwnerOrDeviceOwnerLocked2.protectedPackages != null) {
                        EnforcingAdmin createEnterpriseEnforcingAdmin2 =
                                EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                        profileOwnerOrDeviceOwnerLocked2.info.getComponent(),
                                        profileOwnerOrDeviceOwnerLocked2
                                                .getUserHandle()
                                                .getIdentifier(),
                                        profileOwnerOrDeviceOwnerLocked2);
                        if (devicePolicyManagerService5.isDeviceOwner(
                                profileOwnerOrDeviceOwnerLocked2)) {
                            devicePolicyManagerService5.mDevicePolicyEngine.setGlobalPolicy(
                                    PolicyDefinition.USER_CONTROLLED_DISABLED_PACKAGES,
                                    createEnterpriseEnforcingAdmin2,
                                    new PackageSetPolicyValue(
                                            new HashSet(
                                                    profileOwnerOrDeviceOwnerLocked2
                                                            .protectedPackages)));
                        } else {
                            devicePolicyManagerService5.mDevicePolicyEngine.setLocalPolicy(
                                    PolicyDefinition.USER_CONTROLLED_DISABLED_PACKAGES,
                                    createEnterpriseEnforcingAdmin2,
                                    new PackageSetPolicyValue(
                                            new HashSet(
                                                    profileOwnerOrDeviceOwnerLocked2
                                                            .protectedPackages)),
                                    profileOwnerOrDeviceOwnerLocked2
                                            .getUserHandle()
                                            .getIdentifier(),
                                    false);
                        }
                    }
                }
                return;
            case 8:
                DevicePolicyManagerService devicePolicyManagerService6 =
                        (DevicePolicyManagerService) obj;
                Iterator it4 = devicePolicyManagerService6.mUserManager.getUsers().iterator();
                while (it4.hasNext()) {
                    ActiveAdmin profileOwnerOrDeviceOwnerLocked3 =
                            devicePolicyManagerService6.getProfileOwnerOrDeviceOwnerLocked(
                                    ((UserInfo) it4.next()).id);
                    if (profileOwnerOrDeviceOwnerLocked3 != null) {
                        EnforcingAdmin createEnterpriseEnforcingAdmin3 =
                                EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                        profileOwnerOrDeviceOwnerLocked3.info.getComponent(),
                                        profileOwnerOrDeviceOwnerLocked3
                                                .getUserHandle()
                                                .getIdentifier(),
                                        profileOwnerOrDeviceOwnerLocked3);
                        Iterator it5 =
                                ((ArraySet)
                                                profileOwnerOrDeviceOwnerLocked3
                                                        .accountTypesWithManagementDisabled)
                                        .iterator();
                        while (it5.hasNext()) {
                            devicePolicyManagerService6.mDevicePolicyEngine.setLocalPolicy(
                                    PolicyDefinition.ACCOUNT_MANAGEMENT_DISABLED(
                                            (String) it5.next()),
                                    createEnterpriseEnforcingAdmin3,
                                    new BooleanPolicyValue(true),
                                    profileOwnerOrDeviceOwnerLocked3
                                            .getUserHandle()
                                            .getIdentifier(),
                                    false);
                        }
                        if (profileOwnerOrDeviceOwnerLocked3.getParentActiveAdmin() != null) {
                            Iterator it6 =
                                    ((ArraySet)
                                                    profileOwnerOrDeviceOwnerLocked3
                                                            .getParentActiveAdmin()
                                                            .accountTypesWithManagementDisabled)
                                            .iterator();
                            while (it6.hasNext()) {
                                devicePolicyManagerService6.mDevicePolicyEngine.setLocalPolicy(
                                        PolicyDefinition.ACCOUNT_MANAGEMENT_DISABLED(
                                                (String) it6.next()),
                                        createEnterpriseEnforcingAdmin3,
                                        new BooleanPolicyValue(true),
                                        devicePolicyManagerService6.getProfileParentId(
                                                profileOwnerOrDeviceOwnerLocked3
                                                        .getUserHandle()
                                                        .getIdentifier()),
                                        false);
                            }
                        }
                    }
                }
                return;
            case 9:
                DevicePolicyManagerService devicePolicyManagerService7 =
                        (DevicePolicyManagerService) obj;
                Iterator it7 = devicePolicyManagerService7.mUserManager.getUsers().iterator();
                while (it7.hasNext()) {
                    ActiveAdmin profileOwnerOrDeviceOwnerLocked4 =
                            devicePolicyManagerService7.getProfileOwnerOrDeviceOwnerLocked(
                                    ((UserInfo) it7.next()).id);
                    if (profileOwnerOrDeviceOwnerLocked4 != null) {
                        EnforcingAdmin createEnterpriseEnforcingAdmin4 =
                                EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                        profileOwnerOrDeviceOwnerLocked4.info.getComponent(),
                                        profileOwnerOrDeviceOwnerLocked4
                                                .getUserHandle()
                                                .getIdentifier(),
                                        profileOwnerOrDeviceOwnerLocked4);
                        if (profileOwnerOrDeviceOwnerLocked4.permittedInputMethods != null) {
                            devicePolicyManagerService7.mDevicePolicyEngine.setLocalPolicy(
                                    PolicyDefinition.PERMITTED_INPUT_METHODS,
                                    createEnterpriseEnforcingAdmin4,
                                    new PackageSetPolicyValue(
                                            new HashSet(
                                                    profileOwnerOrDeviceOwnerLocked4
                                                            .permittedInputMethods)),
                                    profileOwnerOrDeviceOwnerLocked4
                                            .getUserHandle()
                                            .getIdentifier(),
                                    false);
                        }
                        if (profileOwnerOrDeviceOwnerLocked4.getParentActiveAdmin() != null
                                && profileOwnerOrDeviceOwnerLocked4.getParentActiveAdmin()
                                                .permittedInputMethods
                                        != null) {
                            devicePolicyManagerService7.mDevicePolicyEngine.setLocalPolicy(
                                    PolicyDefinition.PERMITTED_INPUT_METHODS,
                                    createEnterpriseEnforcingAdmin4,
                                    new PackageSetPolicyValue(
                                            new HashSet(
                                                    profileOwnerOrDeviceOwnerLocked4
                                                            .getParentActiveAdmin()
                                                            .permittedInputMethods)),
                                    devicePolicyManagerService7.getProfileParentId(
                                            profileOwnerOrDeviceOwnerLocked4
                                                    .getUserHandle()
                                                    .getIdentifier()),
                                    false);
                        }
                    }
                }
                return;
            case 10:
                ((DevicePolicyManagerService) obj)
                        .updateDialerAndSmsManagedShortcutsOverrideCache();
                return;
            case 11:
                DevicePolicyManagerService devicePolicyManagerService8 =
                        (DevicePolicyManagerService) obj;
                List aliveUsers = devicePolicyManagerService8.mUserManager.getAliveUsers();
                for (int size = aliveUsers.size() - 1; size >= 0; size--) {
                    int i9 = ((UserInfo) aliveUsers.get(size)).id;
                    if (!devicePolicyManagerService8.canDPCManagedUserUseLockTaskLocked(i9)) {
                        Iterator it8 =
                                new HashSet(
                                                devicePolicyManagerService8
                                                        .mDevicePolicyEngine
                                                        .getLocalPoliciesSetByAdmins(
                                                                PolicyDefinition.LOCK_TASK, i9)
                                                        .keySet())
                                        .iterator();
                        while (it8.hasNext()) {
                            EnforcingAdmin enforcingAdmin = (EnforcingAdmin) it8.next();
                            if (enforcingAdmin.hasAuthority("enterprise")) {
                                devicePolicyManagerService8.mDevicePolicyEngine.removeLocalPolicy(
                                        PolicyDefinition.LOCK_TASK, enforcingAdmin, i9);
                            }
                        }
                    }
                }
                return;
            case 12:
                DevicePolicyManagerService devicePolicyManagerService9 =
                        (DevicePolicyManagerService) obj;
                ActiveAdmin deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked =
                        devicePolicyManagerService9
                                .getDeviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked();
                if (deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked != null
                        && ((devicePolicyManagerService9.isDeviceOwner(
                                                deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked)
                                        && deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked
                                                .disableScreenCapture)
                                || (deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked
                                                        .getParentActiveAdmin()
                                                != null
                                        && deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked
                                                .getParentActiveAdmin()
                                                .disableScreenCapture))) {
                    devicePolicyManagerService9.mDevicePolicyEngine.setGlobalPolicy(
                            PolicyDefinition.SCREEN_CAPTURE_DISABLED,
                            EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                    deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked.info
                                            .getComponent(),
                                    deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked
                                            .getUserHandle()
                                            .getIdentifier(),
                                    deviceOwnerOrProfileOwnerOfOrganizationOwnedDeviceLocked),
                            new BooleanPolicyValue(true));
                }
                Iterator it9 = devicePolicyManagerService9.mUserManager.getUsers().iterator();
                while (it9.hasNext()) {
                    ActiveAdmin profileOwnerLocked2 =
                            devicePolicyManagerService9.getProfileOwnerLocked(
                                    ((UserInfo) it9.next()).id);
                    if (profileOwnerLocked2 != null && profileOwnerLocked2.disableScreenCapture) {
                        devicePolicyManagerService9.mDevicePolicyEngine.setLocalPolicy(
                                PolicyDefinition.SCREEN_CAPTURE_DISABLED,
                                EnforcingAdmin.createEnterpriseEnforcingAdmin(
                                        profileOwnerLocked2.info.getComponent(),
                                        profileOwnerLocked2.getUserHandle().getIdentifier(),
                                        profileOwnerLocked2),
                                new BooleanPolicyValue(true),
                                profileOwnerLocked2.getUserHandle().getIdentifier(),
                                false);
                    }
                }
                return;
            case 13:
                ((DevicePolicyManagerService) obj)
                        .updateDialerAndSmsManagedShortcutsOverrideCache();
                return;
            default:
                DevicePolicyManagerService.DevicePolicyConstantsObserver
                        devicePolicyConstantsObserver =
                                (DevicePolicyManagerService.DevicePolicyConstantsObserver) obj;
                devicePolicyConstantsObserver.getClass();
                Intent intent = new Intent("android.app.action.DEVICE_POLICY_CONSTANTS_CHANGED");
                intent.setFlags(1073741824);
                List aliveUsers2 = DevicePolicyManagerService.this.mUserManager.getAliveUsers();
                for (int i10 = 0; i10 < aliveUsers2.size(); i10++) {
                    DevicePolicyManagerService.this.mContext.sendBroadcastAsUser(
                            intent, UserHandle.of(((UserInfo) aliveUsers2.get(i10)).id));
                }
                return;
        }
    }
}
