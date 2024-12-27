package com.android.server.devicepolicy;

import android.app.admin.flags.Flags;
import android.content.ComponentName;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda30 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ ComponentName f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda30(DevicePolicyManagerService devicePolicyManagerService, int i, ComponentName componentName, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = i;
        this.f$2 = componentName;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda30(DevicePolicyManagerService devicePolicyManagerService, ComponentName componentName, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = componentName;
        this.f$1 = i;
    }

    public final void runOrThrow() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                int i = this.f$1;
                ComponentName componentName = this.f$2;
                devicePolicyManagerService.getClass();
                boolean z2 = true;
                boolean z3 = i == 0;
                if (Flags.headlessSingleUserFixes() && devicePolicyManagerService.getHeadlessDeviceOwnerModeForDeviceOwner() == 2) {
                    z3 = i == devicePolicyManagerService.getMainUserId();
                }
                String str = z3 ? "no_factory_reset" : devicePolicyManagerService.isManagedProfile(i) ? "no_remove_managed_profile" : "no_remove_user";
                if (devicePolicyManagerService.isAdminAffectedByRestriction(componentName, i, str)) {
                    throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Cannot wipe data. ", str, " restriction is set for user "));
                }
                if (i == 0) {
                    devicePolicyManagerService.mInjector.getClass();
                    KnoxPolicyHelper knoxPolicyHelper = devicePolicyManagerService.mKnoxPolicyHelper;
                    knoxPolicyHelper.getClass();
                    try {
                        if (knoxPolicyHelper.getService() != null) {
                            boolean isFactoryResetAllowed = knoxPolicyHelper.mRestrictionService.isFactoryResetAllowed(new ContextInfo(Binder.getCallingUid(), i));
                            Log.d("KnoxPolicyHelper", "isFactoryResetAllowed = " + isFactoryResetAllowed);
                            z2 = isFactoryResetAllowed;
                        }
                    } catch (RemoteException e) {
                        Log.w("KnoxPolicyHelper", "Failed talking with Restriction Policy", e);
                    }
                    if (z2) {
                        return;
                    }
                    AuditLogHelper auditLogHelper = devicePolicyManagerService.mAuditLogHelper;
                    Object[] objArr = new Object[0];
                    auditLogHelper.getClass();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        AuditLog.logEventForComponent("DevicePolicyManager", 43, objArr);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw new SecurityException("Cannot wipe data. Factory reset is not allowed from Restriction Policy.");
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                return;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                int i2 = this.f$1;
                ComponentName componentName2 = this.f$2;
                for (int i3 : devicePolicyManagerService2.mUserManagerInternal.getUserIds()) {
                    devicePolicyManagerService2.mUserManager.setUserRestriction("no_add_managed_profile", true, UserHandle.of(i3));
                    devicePolicyManagerService2.mUserManager.setUserRestriction("no_add_clone_profile", true, UserHandle.of(i3));
                    devicePolicyManagerService2.mUserManager.setUserRestriction("no_add_private_profile", true, UserHandle.of(i3));
                }
                devicePolicyManagerService2.mInjector.getClass();
                devicePolicyManagerService2.mKnoxAnalyticsHelper.sendOwnerChangedBroadcastWithExtra(i2, componentName2.getPackageName(), true);
                return;
            case 2:
                this.f$0.removeActiveAdminLocked(this.f$1, this.f$2);
                return;
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                ComponentName componentName3 = this.f$2;
                int i4 = this.f$1;
                synchronized (devicePolicyManagerService3.getLockObject()) {
                    try {
                        if (!devicePolicyManagerService3.isAdminTestOnlyLocked(i4, componentName3)) {
                            throw new SecurityException("Attempt to remove non-test admin " + componentName3 + " " + i4);
                        }
                        if (devicePolicyManagerService3.isDeviceOwner(i4, componentName3)) {
                            devicePolicyManagerService3.clearDeviceOwnerLocked(i4, devicePolicyManagerService3.getDeviceOwnerAdminLocked());
                        }
                        if (devicePolicyManagerService3.isProfileOwner(i4, componentName3)) {
                            z = devicePolicyManagerService3.isProfileOwnerOfOrganizationOwnedDevice(i4);
                            devicePolicyManagerService3.clearProfileOwnerLocked(i4, devicePolicyManagerService3.getActiveAdminUncheckedLocked(i4, componentName3, false));
                        } else {
                            z = false;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                devicePolicyManagerService3.removeAdminArtifacts(i4, componentName3);
                if (z) {
                    UserHandle of = UserHandle.of(devicePolicyManagerService3.getProfileParentId(i4));
                    devicePolicyManagerService3.mUserManager.setUserRestriction("no_remove_managed_profile", false, of);
                    devicePolicyManagerService3.mUserManager.setUserRestriction("no_add_user", false, of);
                    devicePolicyManagerService3.clearOrgOwnedProfileOwnerDeviceWidePolicies(of.getIdentifier());
                }
                Slogf.i("DevicePolicyManager", "Admin " + componentName3 + " removed from user " + i4);
                return;
        }
    }
}
