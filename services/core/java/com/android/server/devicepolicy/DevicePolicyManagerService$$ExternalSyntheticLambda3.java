package com.android.server.devicepolicy;

import android.content.pm.PackageManagerInternal;

import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda3(
            int i, DevicePolicyManagerService devicePolicyManagerService, boolean z) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = z;
    }

    public final void runOrThrow() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mInjector.settingsGlobalPutInt("auto_time_zone", this.f$1 ? 1 : 0);
                return;
            case 1:
                this.f$0.mInjector.settingsGlobalPutInt(
                        "wifi_device_owner_configs_lockdown", this.f$1 ? 1 : 0);
                return;
            case 2:
                this.f$0.mInjector.settingsGlobalPutInt("auto_time", this.f$1 ? 1 : 0);
                return;
            default:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                boolean z2 = this.f$1;
                synchronized (devicePolicyManagerService.getLockObject()) {
                    z = false;
                    try {
                        if (z2) {
                            if (devicePolicyManagerService.mNetworkLogger == null) {
                                int networkLoggingAffectedUser =
                                        devicePolicyManagerService.getNetworkLoggingAffectedUser();
                                devicePolicyManagerService.mInjector.getClass();
                                PackageManagerInternal packageManagerInternal =
                                        DevicePolicyManagerService.Injector
                                                .getPackageManagerInternal();
                                if (networkLoggingAffectedUser == 0) {
                                    networkLoggingAffectedUser = -1;
                                }
                                devicePolicyManagerService.mNetworkLogger =
                                        new NetworkLogger(
                                                devicePolicyManagerService,
                                                packageManagerInternal,
                                                networkLoggingAffectedUser);
                            }
                            if (!devicePolicyManagerService.mNetworkLogger.startNetworkLogging()) {
                                devicePolicyManagerService.mNetworkLogger = null;
                                Slogf.wtf(
                                        "DevicePolicyManager",
                                        "Network logging could not be started due to the logging"
                                            + " service not being available yet.");
                            }
                            devicePolicyManagerService.maybePauseDeviceWideLoggingLocked();
                            devicePolicyManagerService.ensureLocked();
                            ActiveAdmin deviceOwnerAdminLocked =
                                    devicePolicyManagerService.getDeviceOwnerAdminLocked();
                            if (deviceOwnerAdminLocked != null
                                    && deviceOwnerAdminLocked.isNetworkLoggingEnabled
                                    && deviceOwnerAdminLocked.numNetworkLoggingNotifications < 2) {
                                long currentTimeMillis = System.currentTimeMillis();
                                if (currentTimeMillis
                                                - deviceOwnerAdminLocked
                                                        .lastNetworkLoggingNotificationTimeMs
                                        >= DevicePolicyManagerService.MS_PER_DAY) {
                                    int i =
                                            deviceOwnerAdminLocked.numNetworkLoggingNotifications
                                                    + 1;
                                    deviceOwnerAdminLocked.numNetworkLoggingNotifications = i;
                                    if (i >= 2) {
                                        deviceOwnerAdminLocked
                                                        .lastNetworkLoggingNotificationTimeMs =
                                                0L;
                                    } else {
                                        deviceOwnerAdminLocked
                                                        .lastNetworkLoggingNotificationTimeMs =
                                                currentTimeMillis;
                                    }
                                    devicePolicyManagerService.saveSettingsLocked(
                                            deviceOwnerAdminLocked.getUserHandle().getIdentifier(),
                                            false,
                                            false,
                                            false);
                                    z = true;
                                }
                            }
                        } else {
                            NetworkLogger networkLogger = devicePolicyManagerService.mNetworkLogger;
                            if (networkLogger != null && !networkLogger.stopNetworkLogging()) {
                                Slogf.wtf(
                                        "DevicePolicyManager",
                                        "Network logging could not be stopped due to the logging"
                                            + " service not being available yet.");
                            }
                            devicePolicyManagerService.mNetworkLogger = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!z2) {
                    devicePolicyManagerService.mHandler.post(
                            new DevicePolicyManagerService$$ExternalSyntheticLambda58(
                                    devicePolicyManagerService, 2));
                    return;
                } else {
                    if (z) {
                        devicePolicyManagerService.mHandler.post(
                                new DevicePolicyManagerService$$ExternalSyntheticLambda58(
                                        devicePolicyManagerService, 1));
                        return;
                    }
                    return;
                }
        }
    }
}
