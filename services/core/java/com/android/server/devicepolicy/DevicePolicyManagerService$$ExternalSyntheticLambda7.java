package com.android.server.devicepolicy;

import android.app.AppOpsManager;
import android.app.backup.IBackupManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.VpnManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.internal.util.FunctionalUtils;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda7
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ CallerIdentity f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda7(
            DevicePolicyManagerService devicePolicyManagerService,
            CallerIdentity callerIdentity,
            int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = callerIdentity;
    }

    public final Object getOrThrow() {
        Boolean valueOf;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                CallerIdentity callerIdentity = this.f$1;
                synchronized (devicePolicyManagerService.getLockObject()) {
                    try {
                        try {
                            devicePolicyManagerService.mInjector.getClass();
                            IBackupManager asInterface =
                                    IBackupManager.Stub.asInterface(
                                            ServiceManager.getService("backup"));
                            valueOf =
                                    Boolean.valueOf(
                                            asInterface != null
                                                    && asInterface.isBackupServiceActive(
                                                            UserHandle.getUserId(
                                                                    callerIdentity.mUid)));
                        } catch (RemoteException e) {
                            throw new IllegalStateException(
                                    "Failed requesting backup service state.", e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return valueOf;
            case 1:
                return Boolean.valueOf(
                        UserManager.get(this.f$0.mInjector.mContext)
                                .isUserEphemeral(UserHandle.getUserId(this.f$1.mUid)));
            case 2:
                return Boolean.valueOf(
                        ((VpnManager)
                                        this.f$0.mInjector.mContext.getSystemService(
                                                VpnManager.class))
                                .isVpnLockdownEnabled(UserHandle.getUserId(this.f$1.mUid)));
            case 3:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                CallerIdentity callerIdentity2 = this.f$1;
                AppOpsManager appOpsManager =
                        devicePolicyManagerService2.mInjector.getAppOpsManager();
                if (appOpsManager == null) {
                    return Boolean.FALSE;
                }
                return Boolean.valueOf(
                        appOpsManager.noteOpNoThrow(
                                        104,
                                        callerIdentity2.mUid,
                                        callerIdentity2.mPackageName,
                                        (String) null,
                                        (String) null)
                                == 0);
            case 4:
                return ((VpnManager) this.f$0.mInjector.mContext.getSystemService(VpnManager.class))
                        .getVpnLockdownAllowlist(UserHandle.getUserId(this.f$1.mUid));
            case 5:
                return ((VpnManager) this.f$0.mInjector.mContext.getSystemService(VpnManager.class))
                        .getAlwaysOnVpnPackageForUser(UserHandle.getUserId(this.f$1.mUid));
            case 6:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                CallerIdentity callerIdentity3 = this.f$1;
                devicePolicyManagerService3.getClass();
                new Intent("android.app.action.CHECK_POLICY_COMPLIANCE")
                        .setPackage(callerIdentity3.mPackageName);
                return Boolean.valueOf(
                        !devicePolicyManagerService3
                                .mInjector
                                .mContext
                                .getPackageManager()
                                .queryIntentActivitiesAsUser(
                                        r1, 0, UserHandle.getUserId(callerIdentity3.mUid))
                                .isEmpty());
            case 7:
                DevicePolicyManagerService devicePolicyManagerService4 = this.f$0;
                CallerIdentity callerIdentity4 = this.f$1;
                devicePolicyManagerService4.getClass();
                List<ComponentName> activeAdmins =
                        devicePolicyManagerService4.getActiveAdmins(
                                UserHandle.getUserId(callerIdentity4.mUid));
                if (activeAdmins != null) {
                    for (ComponentName componentName : activeAdmins) {
                        if (componentName.getPackageName().equals(callerIdentity4.mPackageName)) {
                            return devicePolicyManagerService4.getActiveAdminUncheckedLocked(
                                    UserHandle.getUserId(callerIdentity4.mUid), componentName);
                        }
                    }
                }
                return null;
            case 8:
                return Boolean.valueOf(
                        (this.f$0.mLockPatternUtils.getStrongAuthForUser(
                                                UserHandle.getUserId(this.f$1.mUid))
                                        & 512)
                                != 0);
            default:
                DevicePolicyManagerService devicePolicyManagerService5 = this.f$0;
                CallerIdentity callerIdentity5 = this.f$1;
                devicePolicyManagerService5.getClass();
                return devicePolicyManagerService5.getProfileOwnerAsUser(
                        UserHandle.getUserId(callerIdentity5.mUid));
        }
    }
}
