package com.android.server.devicepolicy;

import android.app.AppGlobals;
import android.app.admin.PackagePolicyKey;
import android.app.admin.PolicyKey;
import android.app.admin.UserRestrictionPolicyKey;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;

import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerInternalBase$$ExternalSyntheticLambda0;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.SuspendPackageHelper;
import com.android.server.pm.UserManagerInternal;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PolicyEnforcerCallbacks$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PolicyKey f$0;
    public final /* synthetic */ Boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PolicyEnforcerCallbacks$$ExternalSyntheticLambda3(
            PolicyKey policyKey, int i, Boolean bool) {
        this.$r8$classId = 2;
        this.f$0 = policyKey;
        this.f$2 = i;
        this.f$1 = bool;
    }

    public /* synthetic */ PolicyEnforcerCallbacks$$ExternalSyntheticLambda3(
            PolicyKey policyKey, Boolean bool, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = policyKey;
        this.f$1 = bool;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                PackagePolicyKey packagePolicyKey = this.f$0;
                Boolean bool = this.f$1;
                int i = this.f$2;
                if (!(packagePolicyKey instanceof PackagePolicyKey)) {
                    throw new IllegalArgumentException(
                            "policyKey is not of type PackagePolicyKey, passed in policyKey is: "
                                    + packagePolicyKey);
                }
                String packageName = packagePolicyKey.getPackageName();
                Objects.requireNonNull(packageName);
                IPackageManager packageManager = AppGlobals.getPackageManager();
                if (bool != null && bool.booleanValue()) {
                    z = true;
                }
                return Boolean.valueOf(
                        packageManager.setApplicationHiddenSettingAsUser(packageName, z, i));
            case 1:
                PackagePolicyKey packagePolicyKey2 = this.f$0;
                Boolean bool2 = this.f$1;
                int i2 = this.f$2;
                if (!(packagePolicyKey2 instanceof PackagePolicyKey)) {
                    throw new IllegalArgumentException(
                            "policyKey is not of type PackagePolicyKey, passed in policyKey is: "
                                    + packagePolicyKey2);
                }
                String packageName2 = packagePolicyKey2.getPackageName();
                Objects.requireNonNull(packageName2);
                if (bool2 != null && bool2.booleanValue()) {
                    z = true;
                }
                String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                Binder.withCleanCallingIdentity(
                        new DevicePolicyManagerService$$ExternalSyntheticLambda31(
                                packageName2, z, i2, 2));
                if (z) {
                    PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                            (PackageManagerService.PackageManagerInternalImpl)
                                    ((PackageManagerInternal)
                                            LocalServices.getService(PackageManagerInternal.class));
                    SuspendPackageHelper suspendPackageHelper =
                            PackageManagerService.this.mSuspendPackageHelper;
                    PackageManagerService packageManagerService =
                            packageManagerInternalImpl.mService;
                    suspendPackageHelper.removeSuspensionsBySuspendingPackage(
                            packageManagerService.snapshotComputer(),
                            new String[] {packageName2},
                            new PackageManagerInternalBase$$ExternalSyntheticLambda0(),
                            i2);
                    PackageManagerService.this.mDistractingPackageHelper
                            .removeDistractingPackageRestrictions(
                                    packageManagerService.snapshotComputer(),
                                    new String[] {packageName2},
                                    i2);
                    PackageManagerTracedLock packageManagerTracedLock =
                            PackageManagerService.this.mLock;
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    synchronized (packageManagerTracedLock) {
                        try {
                            PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(
                                    i2);
                        } catch (Throwable th) {
                            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                            throw th;
                        }
                    }
                }
                return Boolean.TRUE;
            default:
                UserRestrictionPolicyKey userRestrictionPolicyKey = this.f$0;
                int i3 = this.f$2;
                Boolean bool3 = this.f$1;
                if (!(userRestrictionPolicyKey instanceof UserRestrictionPolicyKey)) {
                    throw new IllegalArgumentException(
                            "policyKey is not of type UserRestrictionPolicyKey, passed in policyKey"
                                + " is: "
                                    + userRestrictionPolicyKey);
                }
                UserManagerInternal userManagerInternal =
                        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                String restriction = userRestrictionPolicyKey.getRestriction();
                if (bool3 != null && bool3.booleanValue()) {
                    z = true;
                }
                userManagerInternal.setUserRestriction(i3, restriction, z);
                return Boolean.TRUE;
        }
    }
}
