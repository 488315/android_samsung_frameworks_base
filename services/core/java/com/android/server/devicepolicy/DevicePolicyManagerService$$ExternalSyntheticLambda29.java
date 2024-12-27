package com.android.server.devicepolicy;

import android.R;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.utils.Slogf;

import com.samsung.android.knox.analytics.activation.DevicePolicyListener;

import java.util.List;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda29
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda29(
            int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService =
                        (DevicePolicyManagerService) this.f$0;
                int profileParentId = devicePolicyManagerService.getProfileParentId(this.f$1);
                long maximumTimeToLockPolicyFromAdmins =
                        DevicePolicyManagerService.getMaximumTimeToLockPolicyFromAdmins(
                                devicePolicyManagerService
                                        .getActiveAdminsForLockscreenPoliciesLocked(
                                                profileParentId));
                DevicePolicyData userDataUnchecked =
                        devicePolicyManagerService.getUserDataUnchecked(profileParentId);
                if (userDataUnchecked.mLastMaximumTimeToLock == maximumTimeToLockPolicyFromAdmins) {
                    return;
                }
                userDataUnchecked.mLastMaximumTimeToLock = maximumTimeToLockPolicyFromAdmins;
                if (maximumTimeToLockPolicyFromAdmins != Long.MAX_VALUE) {
                    devicePolicyManagerService.mInjector.settingsGlobalPutInt(
                            "stay_on_while_plugged_in", 0);
                }
                devicePolicyManagerService.mInjector.getClass();
                ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class))
                        .setMaximumScreenOffTimeoutFromDeviceAdmin(
                                profileParentId, maximumTimeToLockPolicyFromAdmins);
                return;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 =
                        (DevicePolicyManagerService) this.f$0;
                ((WifiManager)
                                devicePolicyManagerService2.mInjector.mContext.getSystemService(
                                        WifiManager.class))
                        .notifyMinimumRequiredWifiSecurityLevelChanged(this.f$1);
                return;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService3 =
                        (DevicePolicyManagerService) this.f$0;
                devicePolicyManagerService3.mInjector.settingsGlobalPutInt(
                        "device_owner_type", this.f$1);
                return;
            case 3:
                DevicePolicyManagerService devicePolicyManagerService4 =
                        (DevicePolicyManagerService) this.f$0;
                int i = this.f$1;
                devicePolicyManagerService4.mUserManager.setUserEnabled(i);
                UserInfo profileParent =
                        devicePolicyManagerService4.mUserManager.getProfileParent(i);
                Intent intent = new Intent(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
                intent.putExtra("android.intent.extra.USER", new UserHandle(i));
                UserHandle userHandle = new UserHandle(profileParent.id);
                devicePolicyManagerService4.mLocalService.broadcastIntentToManifestReceivers(
                        intent, userHandle, true);
                intent.addFlags(1342177280);
                devicePolicyManagerService4.mContext.sendBroadcastAsUser(intent, userHandle);
                return;
            case 4:
                ((DevicePolicyManagerService) this.f$0)
                        .getActiveAdminForCallerLockedMDM(0, this.f$1, (ComponentName) null);
                return;
            case 5:
                DevicePolicyManagerService devicePolicyManagerService5 =
                        (DevicePolicyManagerService) this.f$0;
                int i2 = this.f$1;
                devicePolicyManagerService5.getClass();
                try {
                    List profiles = devicePolicyManagerService5.mUserManager.getProfiles(i2);
                    int size = profiles.size();
                    if (size <= 1) {
                        return;
                    }
                    String string =
                            devicePolicyManagerService5
                                    .mContext
                                    .getResources()
                                    .getString(R.string.error_handwriting_unsupported);
                    devicePolicyManagerService5.mIPackageManager.clearCrossProfileIntentFilters(
                            i2, devicePolicyManagerService5.mContext.getOpPackageName());
                    devicePolicyManagerService5.mIPackageManager.clearCrossProfileIntentFilters(
                            i2, string);
                    for (int i3 = 0; i3 < size; i3++) {
                        UserInfo userInfo = (UserInfo) profiles.get(i3);
                        devicePolicyManagerService5.mIPackageManager.clearCrossProfileIntentFilters(
                                userInfo.id,
                                devicePolicyManagerService5.mContext.getOpPackageName());
                        devicePolicyManagerService5.mIPackageManager.clearCrossProfileIntentFilters(
                                userInfo.id, string);
                        devicePolicyManagerService5.mUserManagerInternal
                                .setDefaultCrossProfileIntentFilters(i2, userInfo.id);
                    }
                    return;
                } catch (RemoteException e) {
                    Slogf.wtf(
                            "DevicePolicyManager",
                            "Error resetting default cross profile intent filters",
                            e);
                    return;
                }
            case 6:
                DevicePolicyManagerService devicePolicyManagerService6 =
                        (DevicePolicyManagerService) this.f$0;
                int i4 = this.f$1;
                devicePolicyManagerService6.getClass();
                devicePolicyManagerService6.mContext.sendBroadcastAsUser(
                        new Intent("android.app.action.RESET_PROTECTION_POLICY_CHANGED")
                                .addFlags(285212672),
                        UserHandle.getUserHandleForUid(i4),
                        "android.permission.MANAGE_FACTORY_RESET_PROTECTION");
                return;
            default:
                DevicePolicyManagerService.LocalService localService =
                        (DevicePolicyManagerService.LocalService) this.f$0;
                int i5 = this.f$1;
                synchronized (DevicePolicyManagerService.this.getLockObject()) {
                    DevicePolicyManagerService.this.updateMaximumTimeToLockLocked(i5);
                    DevicePolicyManagerService.this.updatePasswordQualityCacheForUserGroup(i5);
                }
                return;
        }
    }
}
