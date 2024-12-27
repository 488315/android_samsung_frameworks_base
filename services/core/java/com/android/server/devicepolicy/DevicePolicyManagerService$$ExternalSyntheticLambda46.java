package com.android.server.devicepolicy;

import android.app.ActivityManager;
import android.app.admin.WifiSsidPolicy;
import android.app.admin.flags.Flags;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.net.ProxyInfo;
import android.net.wifi.WifiManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.internal.util.FunctionalUtils;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import com.android.server.utils.Slogf;
import com.samsung.android.knox.IMiscPolicy;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda46 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda46(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void runOrThrow() {
        ComponentName profileOwnerComponent;
        switch (this.$r8$classId) {
            case 0:
                ((WifiManager) ((DevicePolicyManagerService) this.f$0).mInjector.mContext.getSystemService(WifiManager.class)).notifyWifiSsidPolicyChanged((WifiSsidPolicy) this.f$1);
                return;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService = (DevicePolicyManagerService) this.f$0;
                Intent intent = (Intent) this.f$1;
                synchronized (devicePolicyManagerService.getLockObject()) {
                    try {
                        if (devicePolicyManagerService.mOwners.hasDeviceOwner()) {
                            UserHandle of = UserHandle.of(devicePolicyManagerService.mOwners.getDeviceOwnerUserId());
                            intent.setComponent(devicePolicyManagerService.mOwners.getDeviceOwnerComponent());
                            devicePolicyManagerService.mContext.sendBroadcastAsUser(intent, of);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                try {
                    devicePolicyManagerService.mInjector.getClass();
                    for (int i : ActivityManager.getService().getRunningUserIds()) {
                        synchronized (devicePolicyManagerService.getLockObject()) {
                            profileOwnerComponent = devicePolicyManagerService.mOwners.getProfileOwnerComponent(i);
                        }
                        if (profileOwnerComponent != null) {
                            intent.setComponent(profileOwnerComponent);
                            devicePolicyManagerService.mContext.sendBroadcastAsUser(intent, UserHandle.of(i));
                        }
                        if (Flags.permissionMigrationForZeroTrustImplEnabled()) {
                            UserHandle of2 = UserHandle.of(i);
                            String roleHolderPackageNameOnUser = devicePolicyManagerService.getRoleHolderPackageNameOnUser(i, "android.app.role.DEVICE_POLICY_MANAGEMENT");
                            if (roleHolderPackageNameOnUser != null) {
                                devicePolicyManagerService.broadcastExplicitIntentToPackage(intent, of2, roleHolderPackageNameOnUser);
                            }
                        }
                    }
                    return;
                } catch (RemoteException e) {
                    Slogf.e("DevicePolicyManager", "Could not retrieve the list of running users", e);
                    return;
                }
            case 2:
                DevicePolicyManagerService devicePolicyManagerService2 = (DevicePolicyManagerService) this.f$0;
                String str = (String) this.f$1;
                devicePolicyManagerService2.mInjector.getClass();
                AlarmManagerService.this.setTimeZoneImpl(100, str, "DevicePolicyManager.setTimeZone()");
                return;
            case 3:
                DevicePolicyManagerService devicePolicyManagerService3 = (DevicePolicyManagerService) this.f$0;
                ProxyInfo proxyInfo = (ProxyInfo) this.f$1;
                KnoxPolicyHelper knoxPolicyHelper = devicePolicyManagerService3.mKnoxPolicyHelper;
                if (knoxPolicyHelper.mMiscService == null) {
                    knoxPolicyHelper.mMiscService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
                }
                IMiscPolicy iMiscPolicy = knoxPolicyHelper.mMiscService;
                if (iMiscPolicy != null) {
                    try {
                        iMiscPolicy.clearAllGlobalProxy();
                    } catch (RemoteException unused) {
                    }
                }
                ((ConnectivityManager) devicePolicyManagerService3.mInjector.mContext.getSystemService(ConnectivityManager.class)).setGlobalProxy(proxyInfo);
                return;
            case 4:
                DevicePolicyManagerService devicePolicyManagerService4 = (DevicePolicyManagerService) this.f$0;
                UserHandle userHandle = (UserHandle) this.f$1;
                devicePolicyManagerService4.mUserManager.setUserRestriction("no_remove_managed_profile", false, userHandle);
                devicePolicyManagerService4.mUserManager.setUserRestriction("no_add_user", false, userHandle);
                return;
            case 5:
                DevicePolicyManagerService devicePolicyManagerService5 = (DevicePolicyManagerService) this.f$0;
                CharSequence charSequence = (CharSequence) this.f$1;
                devicePolicyManagerService5.mLockPatternUtils.setDeviceOwnerInfo(charSequence != null ? charSequence.toString() : null);
                return;
            case 6:
                DevicePolicyManagerService devicePolicyManagerService6 = (DevicePolicyManagerService) this.f$0;
                BiConsumer biConsumer = (BiConsumer) this.f$1;
                for (UserInfo userInfo : devicePolicyManagerService6.mUserManager.getUsers()) {
                    ActiveAdmin profileOwnerOrDeviceOwnerLocked = devicePolicyManagerService6.getProfileOwnerOrDeviceOwnerLocked(userInfo.id);
                    if (profileOwnerOrDeviceOwnerLocked != null) {
                        biConsumer.accept(profileOwnerOrDeviceOwnerLocked, EnforcingAdmin.createEnterpriseEnforcingAdmin(profileOwnerOrDeviceOwnerLocked.info.getComponent(), userInfo.id, profileOwnerOrDeviceOwnerLocked));
                    }
                }
                return;
            case 7:
                ((DevicePolicyManagerService) this.f$0).resetGlobalProxyLocked((DevicePolicyData) this.f$1);
                return;
            default:
                DevicePolicyManagerService.DevicePolicyManagementRoleObserver devicePolicyManagementRoleObserver = (DevicePolicyManagerService.DevicePolicyManagementRoleObserver) this.f$0;
                Intent intent2 = (Intent) this.f$1;
                Iterator it = DevicePolicyManagerService.this.mUserManager.getUsers().iterator();
                while (it.hasNext()) {
                    UserHandle userHandle2 = ((UserInfo) it.next()).getUserHandle();
                    DevicePolicyManagerService devicePolicyManagerService7 = DevicePolicyManagerService.this;
                    String roleHolderPackageNameOnUser2 = devicePolicyManagerService7.getRoleHolderPackageNameOnUser(devicePolicyManagerService7.mContext, "android.app.role.SYSTEM_SUPERVISION", userHandle2);
                    if (roleHolderPackageNameOnUser2 != null) {
                        devicePolicyManagerService7.broadcastExplicitIntentToPackage(intent2, userHandle2, roleHolderPackageNameOnUser2);
                    }
                    DevicePolicyManagerService devicePolicyManagerService8 = DevicePolicyManagerService.this;
                    String roleHolderPackageNameOnUser3 = devicePolicyManagerService8.getRoleHolderPackageNameOnUser(devicePolicyManagerService8.mContext, "android.app.role.DEVICE_POLICY_MANAGEMENT", userHandle2);
                    if (roleHolderPackageNameOnUser3 != null) {
                        devicePolicyManagerService8.broadcastExplicitIntentToPackage(intent2, userHandle2, roleHolderPackageNameOnUser3);
                    }
                    ActiveAdmin deviceOrProfileOwnerAdminLocked = DevicePolicyManagerService.this.getDeviceOrProfileOwnerAdminLocked(userHandle2.getIdentifier());
                    if (deviceOrProfileOwnerAdminLocked != null) {
                        DevicePolicyManagerService devicePolicyManagerService9 = DevicePolicyManagerService.this;
                        ComponentName component = deviceOrProfileOwnerAdminLocked.info.getComponent();
                        int identifier = userHandle2.getIdentifier();
                        if ((!devicePolicyManagerService9.isProfileOwner(identifier, component) || !devicePolicyManagerService9.isProfileOwnerOfOrganizationOwnedDevice(identifier)) && !DevicePolicyManagerService.this.isDeviceOwner(deviceOrProfileOwnerAdminLocked)) {
                            if (DevicePolicyManagerService.this.isProfileOwner(userHandle2.getIdentifier(), deviceOrProfileOwnerAdminLocked.info.getComponent()) && deviceOrProfileOwnerAdminLocked.getUserHandle().isSystem()) {
                            }
                        }
                        if (!deviceOrProfileOwnerAdminLocked.info.getPackageName().equals(DevicePolicyManagerService.this.getRoleHolderPackageNameOnUser(devicePolicyManagementRoleObserver.mContext, "android.app.role.DEVICE_POLICY_MANAGEMENT", userHandle2))) {
                            DevicePolicyManagerService.this.broadcastExplicitIntentToPackage(intent2, deviceOrProfileOwnerAdminLocked.getUserHandle(), deviceOrProfileOwnerAdminLocked.info.getPackageName());
                        }
                    }
                }
                return;
        }
    }
}
