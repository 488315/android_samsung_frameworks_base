package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda20
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda20(
            int i, DevicePolicyManagerService devicePolicyManagerService, String str) {
        this.$r8$classId = 0;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
        this.f$2 = str;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda20(
            DevicePolicyManagerService devicePolicyManagerService, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = str;
        this.f$1 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                int i = this.f$1;
                String str = this.f$2;
                devicePolicyManagerService.getClass();
                try {
                    Context createPackageContextAsUser =
                            devicePolicyManagerService.mContext.createPackageContextAsUser(
                                    str, 0, UserHandle.of(i));
                    ApplicationInfo applicationInfo =
                            createPackageContextAsUser.getApplicationInfo();
                    CharSequence loadUnsafeLabel =
                            applicationInfo != null
                                    ? applicationInfo.loadUnsafeLabel(
                                            createPackageContextAsUser.getPackageManager())
                                    : null;
                    if (loadUnsafeLabel != null) {
                        return loadUnsafeLabel.toString();
                    }
                    return null;
                } catch (PackageManager.NameNotFoundException e) {
                    Slogf.w(
                            "DevicePolicyManager",
                            e,
                            "%s is not installed for user %d",
                            str,
                            Integer.valueOf(i));
                    return null;
                }
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                String str2 = this.f$2;
                int i2 = this.f$1;
                devicePolicyManagerService2.getClass();
                try {
                    devicePolicyManagerService2.mInjector.getClass();
                    PackageInfo packageInfo =
                            AppGlobals.getPackageManager().getPackageInfo(str2, 0L, i2);
                    return Boolean.valueOf(
                            (packageInfo == null || packageInfo.applicationInfo.flags == 0)
                                    ? false
                                    : true);
                } catch (RemoteException e2) {
                    throw new RuntimeException("Package manager has died", e2);
                }
            case 2:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                String str3 = this.f$2;
                int i3 = this.f$1;
                devicePolicyManagerService3.getClass();
                try {
                    return Integer.valueOf(
                            devicePolicyManagerService3
                                    .mContext
                                    .getPackageManager()
                                    .getApplicationInfoAsUser(str3, 0, i3)
                                    .uid);
                } catch (PackageManager.NameNotFoundException unused) {
                    return -1;
                }
            default:
                return Boolean.valueOf(
                        this.f$0.mIPackageManager.getApplicationHiddenSettingAsUser(
                                this.f$2, this.f$1));
        }
    }
}
