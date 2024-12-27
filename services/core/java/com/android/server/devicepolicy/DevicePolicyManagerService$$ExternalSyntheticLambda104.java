package com.android.server.devicepolicy;

import android.app.admin.PasswordPolicy;
import android.content.Intent;
import android.net.VpnManager;
import android.os.Bundle;
import android.os.ServiceSpecificException;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda104
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Object f$4;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda104(
            DevicePolicyManagerService devicePolicyManagerService,
            ActiveAdmin activeAdmin,
            int i,
            CallerIdentity callerIdentity,
            boolean z) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = activeAdmin;
        this.f$2 = i;
        this.f$4 = callerIdentity;
        this.f$3 = z;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda104(
            DevicePolicyManagerService devicePolicyManagerService,
            String str,
            int i,
            boolean z,
            List list) {
        this.f$0 = devicePolicyManagerService;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = z;
        this.f$4 = list;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda104(
            DevicePolicyManagerService devicePolicyManagerService,
            boolean z,
            Intent intent,
            int i,
            Bundle bundle) {
        this.f$0 = devicePolicyManagerService;
        this.f$3 = z;
        this.f$1 = intent;
        this.f$2 = i;
        this.f$4 = bundle;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                String str = (String) this.f$1;
                int i = this.f$2;
                boolean z = this.f$3;
                List<String> list = (List) this.f$4;
                devicePolicyManagerService.getClass();
                if (str != null && !devicePolicyManagerService.isPackageInstalledForUser(i, str)) {
                    Slogf.w(
                            "DevicePolicyManager",
                            "Non-existent VPN package specified: ".concat(str));
                    throw new ServiceSpecificException(1, str);
                }
                if (str != null && z && list != null) {
                    for (String str2 : list) {
                        if (!devicePolicyManagerService.isPackageInstalledForUser(i, str2)) {
                            Slogf.w(
                                    "DevicePolicyManager",
                                    "Non-existent package in VPN allowlist: " + str2);
                            throw new ServiceSpecificException(1, str2);
                        }
                    }
                }
                if (!((VpnManager)
                                devicePolicyManagerService.mInjector.mContext.getSystemService(
                                        VpnManager.class))
                        .setAlwaysOnVpnPackageForUser(i, str, z, list)) {
                    throw new UnsupportedOperationException();
                }
                return;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                boolean z2 = this.f$3;
                Intent intent = (Intent) this.f$1;
                int i2 = this.f$2;
                Bundle bundle = (Bundle) this.f$4;
                if (z2) {
                    devicePolicyManagerService2.mContext.sendBroadcast(intent);
                    return;
                } else {
                    devicePolicyManagerService2.mContext.sendBroadcastAsUser(
                            intent, new UserHandle(i2), null, bundle);
                    return;
                }
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                ActiveAdmin activeAdmin = (ActiveAdmin) this.f$1;
                int i3 = this.f$2;
                CallerIdentity callerIdentity = (CallerIdentity) this.f$4;
                boolean z3 = this.f$3;
                devicePolicyManagerService3.getClass();
                activeAdmin.mPasswordComplexity = i3;
                activeAdmin.mPasswordPolicy = new PasswordPolicy();
                devicePolicyManagerService3.updatePasswordValidityCheckpointLocked(
                        UserHandle.getUserId(callerIdentity.mUid), z3);
                int i4 = callerIdentity.mUid;
                devicePolicyManagerService3.updatePasswordQualityCacheForUserGroup(
                        UserHandle.getUserId(i4));
                devicePolicyManagerService3.saveSettingsLocked(
                        UserHandle.getUserId(i4), false, false, false);
                return;
        }
    }
}
