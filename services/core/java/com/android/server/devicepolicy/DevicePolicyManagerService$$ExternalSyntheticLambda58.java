package com.android.server.devicepolicy;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.os.UserHandle;

import com.android.internal.notification.SystemNotificationChannels;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.Slogf;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda58
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda58(
            DevicePolicyManagerService devicePolicyManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        switch (i) {
            case 0:
                synchronized (devicePolicyManagerService.getLockObject()) {
                    try {
                        List users = devicePolicyManagerService.mUserManager.getUsers();
                        for (int size = users.size() - 1; size >= 0; size--) {
                            int i2 = ((UserInfo) users.get(size)).id;
                            UsageStatsService.this.mAppStandby.setActiveAdminApps(
                                    devicePolicyManagerService.getActiveAdminPackagesLocked(i2),
                                    i2);
                        }
                    } finally {
                    }
                }
                UsageStatsService.this.mAppStandby.onAdminDataAvailable();
                synchronized (devicePolicyManagerService.getLockObject()) {
                    try {
                        List users2 = devicePolicyManagerService.mUserManager.getUsers();
                        for (int size2 = users2.size() - 1; size2 >= 0; size2--) {
                            int i3 = ((UserInfo) users2.get(size2)).id;
                            devicePolicyManagerService.mInjector.getClass();
                            NetworkPolicyManagerService.this
                                    .mHandler
                                    .obtainMessage(
                                            17,
                                            i3,
                                            0,
                                            devicePolicyManagerService.getMeteredDisabledPackages(
                                                    i3))
                                    .sendToTarget();
                        }
                    } finally {
                    }
                }
                devicePolicyManagerService.mInjector.getClass();
                NetworkPolicyManagerService.this.mAdminDataAvailableLatch.countDown();
                return;
            case 1:
                devicePolicyManagerService.mInjector.getClass();
                PackageManagerInternal packageManagerInternal =
                        DevicePolicyManagerService.Injector.getPackageManagerInternal();
                Intent intent = new Intent("android.app.action.SHOW_DEVICE_MONITORING_DIALOG");
                intent.setPackage(
                        packageManagerInternal.getSystemUiServiceComponent().getPackageName());
                devicePolicyManagerService.mNetworkLoggingNotificationUserId =
                        devicePolicyManagerService.getCurrentForegroundUserId();
                PendingIntent broadcastAsUser =
                        PendingIntent.getBroadcastAsUser(
                                devicePolicyManagerService.mContext,
                                0,
                                intent,
                                67108864,
                                UserHandle.CURRENT);
                String updatableString =
                        devicePolicyManagerService.getUpdatableString(
                                "Core.NETWORK_LOGGING_TITLE",
                                R.string.scCellularNetworkSecurityTitle,
                                new Object[0]);
                String updatableString2 =
                        devicePolicyManagerService.getUpdatableString(
                                "Core.NETWORK_LOGGING_MESSAGE",
                                R.string.scCellularNetworkSecuritySummary,
                                new Object[0]);
                Notification build =
                        new Notification.Builder(
                                        devicePolicyManagerService.mContext,
                                        SystemNotificationChannels.DEVICE_ADMIN)
                                .setSmallIcon(R.drawable.ic_lockscreen_chevron_right)
                                .setContentTitle(updatableString)
                                .setContentText(updatableString2)
                                .setTicker(updatableString)
                                .setShowWhen(true)
                                .setContentIntent(broadcastAsUser)
                                .setStyle(new Notification.BigTextStyle().bigText(updatableString2))
                                .build();
                Slogf.i(
                        "DevicePolicyManager",
                        "Sending network logging notification to user %d",
                        Integer.valueOf(
                                devicePolicyManagerService.mNetworkLoggingNotificationUserId));
                devicePolicyManagerService
                        .mInjector
                        .getNotificationManager()
                        .notifyAsUser(
                                null,
                                1002,
                                build,
                                UserHandle.of(
                                        devicePolicyManagerService
                                                .mNetworkLoggingNotificationUserId));
                return;
            default:
                int i4 = devicePolicyManagerService.mNetworkLoggingNotificationUserId;
                if (i4 == -10000) {
                    Slogf.d(
                            "DevicePolicyManager",
                            "Not cancelling network logging notification for USER_NULL");
                    return;
                }
                Slogf.i(
                        "DevicePolicyManager",
                        "Cancelling network logging notification for user %d",
                        Integer.valueOf(i4));
                devicePolicyManagerService
                        .mInjector
                        .getNotificationManager()
                        .cancelAsUser(
                                null,
                                1002,
                                UserHandle.of(
                                        devicePolicyManagerService
                                                .mNetworkLoggingNotificationUserId));
                devicePolicyManagerService.mNetworkLoggingNotificationUserId = -10000;
                return;
        }
    }
}
