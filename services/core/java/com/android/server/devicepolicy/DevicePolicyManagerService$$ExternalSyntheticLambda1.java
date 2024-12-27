package com.android.server.devicepolicy;

import android.app.admin.PackageSetPolicyValue;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.KeyChain;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

import java.util.HashSet;
import java.util.List;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ CallerIdentity f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda1(
            DevicePolicyManagerService devicePolicyManagerService,
            CallerIdentity callerIdentity,
            String str,
            ArrayMap arrayMap) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = callerIdentity;
        this.f$2 = str;
        this.f$3 = arrayMap;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda1(
            DevicePolicyManagerService devicePolicyManagerService,
            List list,
            CallerIdentity callerIdentity,
            EnforcingAdmin enforcingAdmin) {
        this.f$0 = devicePolicyManagerService;
        this.f$2 = list;
        this.f$1 = callerIdentity;
        this.f$3 = enforcingAdmin;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                CallerIdentity callerIdentity = this.f$1;
                String str = (String) this.f$2;
                ArrayMap arrayMap = (ArrayMap) this.f$3;
                devicePolicyManagerService.getClass();
                try {
                    try {
                        KeyChain.KeyChainConnection bindAsUser =
                                KeyChain.bindAsUser(
                                        devicePolicyManagerService.mContext,
                                        UserHandle.getUserHandleForUid(callerIdentity.mUid));
                        try {
                            int[] grants = bindAsUser.getService().getGrants(str);
                            PackageManager packageManager =
                                    devicePolicyManagerService.mInjector.getPackageManager(
                                            UserHandle.getUserId(callerIdentity.mUid));
                            for (int i : grants) {
                                String[] packagesForUid = packageManager.getPackagesForUid(i);
                                if (packagesForUid == null) {
                                    Slogf.wtf(
                                            "DevicePolicyManager",
                                            "No packages found for uid " + i);
                                } else {
                                    arrayMap.put(Integer.valueOf(i), new ArraySet(packagesForUid));
                                }
                            }
                            bindAsUser.close();
                            return;
                        } catch (Throwable th) {
                            if (bindAsUser != null) {
                                try {
                                    bindAsUser.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (InterruptedException e) {
                        Slogf.w(
                                "DevicePolicyManager",
                                "Interrupted while querying keypair grants",
                                e);
                        Thread.currentThread().interrupt();
                        return;
                    }
                } catch (RemoteException | AssertionError e2) {
                    Slogf.e("DevicePolicyManager", "Querying keypair grants", e2);
                    return;
                }
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                List list = (List) this.f$2;
                CallerIdentity callerIdentity2 = this.f$1;
                EnforcingAdmin enforcingAdmin = (EnforcingAdmin) this.f$3;
                devicePolicyManagerService2.getClass();
                if (list.isEmpty()) {
                    if (devicePolicyManagerService2.isDeviceOwner(callerIdentity2)) {
                        devicePolicyManagerService2.mDevicePolicyEngine.removeGlobalPolicy(
                                PolicyDefinition.USER_CONTROLLED_DISABLED_PACKAGES, enforcingAdmin);
                        return;
                    } else {
                        devicePolicyManagerService2.mDevicePolicyEngine.removeLocalPolicy(
                                PolicyDefinition.USER_CONTROLLED_DISABLED_PACKAGES,
                                enforcingAdmin,
                                UserHandle.getUserId(callerIdentity2.mUid));
                        return;
                    }
                }
                HashSet hashSet = new HashSet(list);
                if (devicePolicyManagerService2.isDeviceOwner(callerIdentity2)) {
                    devicePolicyManagerService2.mDevicePolicyEngine.setGlobalPolicy(
                            PolicyDefinition.USER_CONTROLLED_DISABLED_PACKAGES,
                            enforcingAdmin,
                            new PackageSetPolicyValue(hashSet));
                    return;
                } else {
                    devicePolicyManagerService2.mDevicePolicyEngine.setLocalPolicy(
                            PolicyDefinition.USER_CONTROLLED_DISABLED_PACKAGES,
                            enforcingAdmin,
                            new PackageSetPolicyValue(hashSet),
                            UserHandle.getUserId(callerIdentity2.mUid),
                            false);
                    return;
                }
        }
    }
}
