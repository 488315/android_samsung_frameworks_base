package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.server.utils.Slogf;

import java.util.function.Predicate;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda31
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda31(
            int i, DevicePolicyManagerService devicePolicyManagerService, boolean z) {
        this.$r8$classId = 1;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = i;
        this.f$1 = z;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda31(
            Object obj, boolean z, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = z;
        this.f$2 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService =
                        (DevicePolicyManagerService) this.f$0;
                boolean z = this.f$1;
                final int i = this.f$2;
                devicePolicyManagerService.getClass();
                Preconditions.checkCallAuthorization(
                        !z,
                        "User %s is a system user and cannot be removed",
                        new Object[] {Integer.valueOf(i)});
                Preconditions.checkState(
                        !(devicePolicyManagerService.getUserInfo(i).isFull()
                                && devicePolicyManagerService.mUserManager.getAliveUsers().stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                    // com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda212
                                                    @Override // java.util.function.Predicate
                                                    public final boolean test(Object obj) {
                                                        return ((UserInfo) obj)
                                                                        .getUserHandle()
                                                                        .getIdentifier()
                                                                != i;
                                                    }
                                                })
                                        .noneMatch(
                                                new DevicePolicyManagerService$$ExternalSyntheticLambda9(
                                                        2))),
                        "Removing user %s would leave the device without any active users. Consider"
                            + " factory resetting the device instead.",
                        new Object[] {Integer.valueOf(i)});
                return;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 =
                        (DevicePolicyManagerService) this.f$0;
                int i2 = this.f$2;
                boolean z2 = this.f$1;
                UserHandle profileParent =
                        devicePolicyManagerService2.mUserManager.getProfileParent(
                                UserHandle.of(i2));
                if (profileParent == null) {
                    throw new IllegalStateException(
                            String.format("User %d is not a profile", Integer.valueOf(i2)));
                }
                devicePolicyManagerService2.mUserManager.setUserRestriction(
                        "no_remove_managed_profile", z2, profileParent);
                devicePolicyManagerService2.mUserManager.setUserRestriction(
                        "no_add_user", z2, profileParent);
                return;
            default:
                try {
                    AppGlobals.getPackageManager()
                            .setBlockUninstallForUser((String) this.f$0, this.f$1, this.f$2);
                    return;
                } catch (RemoteException e) {
                    Slogf.e("DevicePolicyManager", "Failed to setBlockUninstallForUser", e);
                    return;
                }
        }
    }
}
