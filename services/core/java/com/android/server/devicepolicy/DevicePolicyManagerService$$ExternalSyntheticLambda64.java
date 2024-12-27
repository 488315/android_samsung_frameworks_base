package com.android.server.devicepolicy;

import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda64
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda64(
            int i,
            ComponentName componentName,
            DevicePolicyManagerService devicePolicyManagerService) {
        this.$r8$classId = 2;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = i;
        this.f$1 = componentName;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda64(
            DevicePolicyManagerService devicePolicyManagerService,
            ComponentName componentName,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = componentName;
        this.f$2 = i;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda64(
            DevicePolicyManagerService devicePolicyManagerService, byte[] bArr, int i) {
        this.$r8$classId = 3;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = bArr;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        Boolean valueOf;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                int i = this.f$2;
                devicePolicyManagerService.getClass();
                try {
                    return devicePolicyManagerService.mIPackageManager.getReceiverInfo(
                            componentName, 819328L, i);
                } catch (RemoteException e) {
                    Slogf.wtf("DevicePolicyManager", "Error getting receiver info", e);
                    return null;
                }
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                ComponentName componentName2 = (ComponentName) this.f$1;
                int i2 = this.f$2;
                devicePolicyManagerService2.getClass();
                ArrayList arrayList = new ArrayList();
                if (devicePolicyManagerService2.isDeviceOwner(i2, componentName2)) {
                    List aliveUsers = devicePolicyManagerService2.mUserManager.getAliveUsers();
                    for (int i3 = 0; i3 < aliveUsers.size(); i3++) {
                        int i4 = ((UserInfo) aliveUsers.get(i3)).id;
                        if (i4 != i2
                                && devicePolicyManagerService2.canUserBindToDeviceOwnerLocked(i4)) {
                            arrayList.add(UserHandle.of(i4));
                        }
                    }
                } else if (devicePolicyManagerService2.canUserBindToDeviceOwnerLocked(i2)) {
                    arrayList.add(
                            UserHandle.of(
                                    devicePolicyManagerService2.mOwners.getDeviceOwnerUserId()));
                }
                return arrayList;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                int i5 = this.f$2;
                ComponentName componentName3 = (ComponentName) this.f$1;
                if (((AccountManager)
                                        devicePolicyManagerService3
                                                .mContext
                                                .createContextAsUser(UserHandle.of(i5), 0)
                                                .getSystemService(AccountManager.class))
                                .getAccounts()
                                .length
                        == 0) {
                    return Boolean.FALSE;
                }
                synchronized (devicePolicyManagerService3.getLockObject()) {
                    if (componentName3 != null) {
                        if (devicePolicyManagerService3.isAdminTestOnlyLocked(i5, componentName3)) {
                            boolean booleanValue =
                                    devicePolicyManagerService3.mHasIncompatibleAccounts == null
                                            ? true
                                            : ((Boolean)
                                                            devicePolicyManagerService3
                                                                    .mHasIncompatibleAccounts
                                                                    .getOrDefault(
                                                                            Integer.valueOf(i5),
                                                                            Boolean.FALSE))
                                                    .booleanValue();
                            if (!booleanValue) {
                                Slogf.w("DevicePolicyManager", "All accounts are compatible");
                            } else {
                                Slogf.e("DevicePolicyManager", "Found incompatible accounts");
                            }
                            valueOf = Boolean.valueOf(booleanValue);
                        }
                    }
                    Slogf.w(
                            "DevicePolicyManager",
                            "Non test-only owner can't be installed with existing accounts.");
                    valueOf = Boolean.TRUE;
                }
                return valueOf;
            default:
                return Long.valueOf(
                        this.f$0.mLockPatternUtils.addEscrowToken(
                                (byte[]) this.f$1,
                                this.f$2,
                                (LockPatternUtils.EscrowTokenStateChangeCallback) null));
        }
    }
}
