package com.android.server.devicepolicy;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Pair;

import com.android.internal.notification.SystemNotificationChannels;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class RemoteBugreportManager {
    public final Context mContext;
    public final Handler mHandler;
    public final DevicePolicyManagerService.Injector mInjector;
    public final AnonymousClass1 mRemoteBugreportConsentReceiver;
    public final AnonymousClass1 mRemoteBugreportFinishedReceiver;
    public final DevicePolicyManagerService mService;
    public final SecureRandom mRng = new SecureRandom();
    public final AtomicLong mRemoteBugreportNonce = new AtomicLong();
    public final AtomicBoolean mRemoteBugreportServiceIsActive = new AtomicBoolean();
    public final AtomicBoolean mRemoteBugreportSharingAccepted = new AtomicBoolean();
    public final RemoteBugreportManager$$ExternalSyntheticLambda0 mRemoteBugreportTimeoutRunnable =
            new Runnable() { // from class:
                             // com.android.server.devicepolicy.RemoteBugreportManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteBugreportManager remoteBugreportManager = RemoteBugreportManager.this;
                    if (remoteBugreportManager.mRemoteBugreportServiceIsActive.get()) {
                        remoteBugreportManager.mRemoteBugreportServiceIsActive.set(false);
                        DevicePolicyManagerService.Injector injector =
                                remoteBugreportManager.mInjector;
                        injector.getClass();
                        SystemProperties.set("ctl.stop", "bugreportd");
                        remoteBugreportManager.mRemoteBugreportSharingAccepted.set(false);
                        DevicePolicyManagerService devicePolicyManagerService =
                                remoteBugreportManager.mService;
                        devicePolicyManagerService.setDeviceOwnerRemoteBugreportUriAndHash(
                                null, null);
                        injector.getNotificationManager()
                                .cancelAsUser("DevicePolicyManager", 678432343, UserHandle.ALL);
                        Bundle bundle = new Bundle();
                        bundle.putInt("android.app.extra.BUGREPORT_FAILURE_REASON", 0);
                        devicePolicyManagerService.sendDeviceOwnerCommand(
                                bundle, "android.app.action.BUGREPORT_FAILED");
                        remoteBugreportManager.mContext.unregisterReceiver(
                                remoteBugreportManager.mRemoteBugreportConsentReceiver);
                        remoteBugreportManager.mContext.unregisterReceiver(
                                remoteBugreportManager.mRemoteBugreportFinishedReceiver);
                    }
                }
            };

    public RemoteBugreportManager(
            DevicePolicyManagerService devicePolicyManagerService,
            DevicePolicyManagerService.Injector injector) {
        final int i = 0;
        this.mRemoteBugreportFinishedReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.devicepolicy.RemoteBugreportManager.1
                    public final /* synthetic */ RemoteBugreportManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                if ("android.intent.action.REMOTE_BUGREPORT_DISPATCH"
                                                .equals(intent.getAction())
                                        && this.this$0.mRemoteBugreportServiceIsActive.get()) {
                                    RemoteBugreportManager remoteBugreportManager = this.this$0;
                                    remoteBugreportManager.getClass();
                                    long longExtra =
                                            intent.getLongExtra(
                                                    "android.intent.extra.REMOTE_BUGREPORT_NONCE",
                                                    0L);
                                    if (longExtra != 0
                                            && remoteBugreportManager.mRemoteBugreportNonce.get()
                                                    == longExtra) {
                                        remoteBugreportManager.mHandler.removeCallbacks(
                                                remoteBugreportManager
                                                        .mRemoteBugreportTimeoutRunnable);
                                        remoteBugreportManager.mRemoteBugreportServiceIsActive.set(
                                                false);
                                        Uri data = intent.getData();
                                        String uri = data != null ? data.toString() : null;
                                        String stringExtra =
                                                intent.getStringExtra(
                                                        "android.intent.extra.REMOTE_BUGREPORT_HASH");
                                        if (remoteBugreportManager.mRemoteBugreportSharingAccepted
                                                .get()) {
                                            remoteBugreportManager
                                                    .shareBugreportWithDeviceOwnerIfExists(
                                                            uri, stringExtra);
                                            remoteBugreportManager
                                                    .mInjector
                                                    .getNotificationManager()
                                                    .cancelAsUser(
                                                            "DevicePolicyManager",
                                                            678432343,
                                                            UserHandle.ALL);
                                        } else {
                                            remoteBugreportManager.mService
                                                    .setDeviceOwnerRemoteBugreportUriAndHash(
                                                            uri, stringExtra);
                                            remoteBugreportManager.notify(3);
                                        }
                                        remoteBugreportManager.mContext.unregisterReceiver(
                                                remoteBugreportManager
                                                        .mRemoteBugreportFinishedReceiver);
                                        break;
                                    } else {
                                        Slogf.w(
                                                "DevicePolicyManager",
                                                "Invalid nonce provided, ignoring " + longExtra);
                                        break;
                                    }
                                }
                                break;
                            default:
                                String action = intent.getAction();
                                this.this$0
                                        .mInjector
                                        .getNotificationManager()
                                        .cancelAsUser(
                                                "DevicePolicyManager", 678432343, UserHandle.ALL);
                                if ("com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"
                                        .equals(action)) {
                                    RemoteBugreportManager remoteBugreportManager2 = this.this$0;
                                    remoteBugreportManager2.mRemoteBugreportSharingAccepted.set(
                                            true);
                                    Pair deviceOwnerRemoteBugreportUriAndHash =
                                            remoteBugreportManager2.mService
                                                    .getDeviceOwnerRemoteBugreportUriAndHash();
                                    if (deviceOwnerRemoteBugreportUriAndHash != null) {
                                        remoteBugreportManager2
                                                .shareBugreportWithDeviceOwnerIfExists(
                                                        (String)
                                                                deviceOwnerRemoteBugreportUriAndHash
                                                                        .first,
                                                        (String)
                                                                deviceOwnerRemoteBugreportUriAndHash
                                                                        .second);
                                    } else if (remoteBugreportManager2
                                            .mRemoteBugreportServiceIsActive.get()) {
                                        remoteBugreportManager2.notify(2);
                                    }
                                } else if ("com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"
                                        .equals(action)) {
                                    RemoteBugreportManager remoteBugreportManager3 = this.this$0;
                                    if (remoteBugreportManager3.mRemoteBugreportServiceIsActive
                                            .get()) {
                                        remoteBugreportManager3.mInjector.getClass();
                                        SystemProperties.set("ctl.stop", "bugreportd");
                                        remoteBugreportManager3.mRemoteBugreportServiceIsActive.set(
                                                false);
                                        remoteBugreportManager3.mHandler.removeCallbacks(
                                                remoteBugreportManager3
                                                        .mRemoteBugreportTimeoutRunnable);
                                        remoteBugreportManager3.mContext.unregisterReceiver(
                                                remoteBugreportManager3
                                                        .mRemoteBugreportFinishedReceiver);
                                    }
                                    remoteBugreportManager3.mRemoteBugreportSharingAccepted.set(
                                            false);
                                    DevicePolicyManagerService devicePolicyManagerService2 =
                                            remoteBugreportManager3.mService;
                                    devicePolicyManagerService2
                                            .setDeviceOwnerRemoteBugreportUriAndHash(null, null);
                                    devicePolicyManagerService2.sendDeviceOwnerCommand(
                                            null, "android.app.action.BUGREPORT_SHARING_DECLINED");
                                }
                                RemoteBugreportManager remoteBugreportManager4 = this.this$0;
                                remoteBugreportManager4.mContext.unregisterReceiver(
                                        remoteBugreportManager4.mRemoteBugreportConsentReceiver);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mRemoteBugreportConsentReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.devicepolicy.RemoteBugreportManager.1
                    public final /* synthetic */ RemoteBugreportManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                if ("android.intent.action.REMOTE_BUGREPORT_DISPATCH"
                                                .equals(intent.getAction())
                                        && this.this$0.mRemoteBugreportServiceIsActive.get()) {
                                    RemoteBugreportManager remoteBugreportManager = this.this$0;
                                    remoteBugreportManager.getClass();
                                    long longExtra =
                                            intent.getLongExtra(
                                                    "android.intent.extra.REMOTE_BUGREPORT_NONCE",
                                                    0L);
                                    if (longExtra != 0
                                            && remoteBugreportManager.mRemoteBugreportNonce.get()
                                                    == longExtra) {
                                        remoteBugreportManager.mHandler.removeCallbacks(
                                                remoteBugreportManager
                                                        .mRemoteBugreportTimeoutRunnable);
                                        remoteBugreportManager.mRemoteBugreportServiceIsActive.set(
                                                false);
                                        Uri data = intent.getData();
                                        String uri = data != null ? data.toString() : null;
                                        String stringExtra =
                                                intent.getStringExtra(
                                                        "android.intent.extra.REMOTE_BUGREPORT_HASH");
                                        if (remoteBugreportManager.mRemoteBugreportSharingAccepted
                                                .get()) {
                                            remoteBugreportManager
                                                    .shareBugreportWithDeviceOwnerIfExists(
                                                            uri, stringExtra);
                                            remoteBugreportManager
                                                    .mInjector
                                                    .getNotificationManager()
                                                    .cancelAsUser(
                                                            "DevicePolicyManager",
                                                            678432343,
                                                            UserHandle.ALL);
                                        } else {
                                            remoteBugreportManager.mService
                                                    .setDeviceOwnerRemoteBugreportUriAndHash(
                                                            uri, stringExtra);
                                            remoteBugreportManager.notify(3);
                                        }
                                        remoteBugreportManager.mContext.unregisterReceiver(
                                                remoteBugreportManager
                                                        .mRemoteBugreportFinishedReceiver);
                                        break;
                                    } else {
                                        Slogf.w(
                                                "DevicePolicyManager",
                                                "Invalid nonce provided, ignoring " + longExtra);
                                        break;
                                    }
                                }
                                break;
                            default:
                                String action = intent.getAction();
                                this.this$0
                                        .mInjector
                                        .getNotificationManager()
                                        .cancelAsUser(
                                                "DevicePolicyManager", 678432343, UserHandle.ALL);
                                if ("com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"
                                        .equals(action)) {
                                    RemoteBugreportManager remoteBugreportManager2 = this.this$0;
                                    remoteBugreportManager2.mRemoteBugreportSharingAccepted.set(
                                            true);
                                    Pair deviceOwnerRemoteBugreportUriAndHash =
                                            remoteBugreportManager2.mService
                                                    .getDeviceOwnerRemoteBugreportUriAndHash();
                                    if (deviceOwnerRemoteBugreportUriAndHash != null) {
                                        remoteBugreportManager2
                                                .shareBugreportWithDeviceOwnerIfExists(
                                                        (String)
                                                                deviceOwnerRemoteBugreportUriAndHash
                                                                        .first,
                                                        (String)
                                                                deviceOwnerRemoteBugreportUriAndHash
                                                                        .second);
                                    } else if (remoteBugreportManager2
                                            .mRemoteBugreportServiceIsActive.get()) {
                                        remoteBugreportManager2.notify(2);
                                    }
                                } else if ("com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"
                                        .equals(action)) {
                                    RemoteBugreportManager remoteBugreportManager3 = this.this$0;
                                    if (remoteBugreportManager3.mRemoteBugreportServiceIsActive
                                            .get()) {
                                        remoteBugreportManager3.mInjector.getClass();
                                        SystemProperties.set("ctl.stop", "bugreportd");
                                        remoteBugreportManager3.mRemoteBugreportServiceIsActive.set(
                                                false);
                                        remoteBugreportManager3.mHandler.removeCallbacks(
                                                remoteBugreportManager3
                                                        .mRemoteBugreportTimeoutRunnable);
                                        remoteBugreportManager3.mContext.unregisterReceiver(
                                                remoteBugreportManager3
                                                        .mRemoteBugreportFinishedReceiver);
                                    }
                                    remoteBugreportManager3.mRemoteBugreportSharingAccepted.set(
                                            false);
                                    DevicePolicyManagerService devicePolicyManagerService2 =
                                            remoteBugreportManager3.mService;
                                    devicePolicyManagerService2
                                            .setDeviceOwnerRemoteBugreportUriAndHash(null, null);
                                    devicePolicyManagerService2.sendDeviceOwnerCommand(
                                            null, "android.app.action.BUGREPORT_SHARING_DECLINED");
                                }
                                RemoteBugreportManager remoteBugreportManager4 = this.this$0;
                                remoteBugreportManager4.mContext.unregisterReceiver(
                                        remoteBugreportManager4.mRemoteBugreportConsentReceiver);
                                break;
                        }
                    }
                };
        this.mService = devicePolicyManagerService;
        this.mInjector = injector;
        this.mContext = devicePolicyManagerService.mContext;
        this.mHandler = devicePolicyManagerService.mHandler;
    }

    public final void notify(int i) {
        NotificationManager notificationManager = this.mInjector.getNotificationManager();
        Intent intent = new Intent("android.settings.SHOW_REMOTE_BUGREPORT_DIALOG");
        intent.addFlags(268468224);
        intent.putExtra("android.app.extra.bugreport_notification_type", i);
        ActivityInfo resolveActivityInfo =
                intent.resolveActivityInfo(this.mContext.getPackageManager(), 1048576);
        if (resolveActivityInfo != null) {
            intent.setComponent(resolveActivityInfo.getComponentName());
        } else {
            Slogf.wtf(
                    "DevicePolicyManager", "Failed to resolve intent for remote bugreport dialog");
        }
        Notification.Builder extend =
                new Notification.Builder(this.mContext, SystemNotificationChannels.DEVICE_ADMIN)
                        .setSmallIcon(17304445)
                        .setOngoing(true)
                        .setLocalOnly(true)
                        .setContentIntent(
                                PendingIntent.getActivityAsUser(
                                        this.mContext,
                                        i,
                                        intent,
                                        67108864,
                                        null,
                                        UserHandle.CURRENT))
                        .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
                        .extend(new Notification.TvExtender());
        if (i == 2) {
            extend.setContentTitle(this.mContext.getString(17043020)).setProgress(0, 0, true);
        } else if (i == 1) {
            extend.setContentTitle(this.mContext.getString(17043203)).setProgress(0, 0, true);
        } else if (i == 3) {
            PendingIntent broadcast =
                    PendingIntent.getBroadcast(
                            this.mContext,
                            678432343,
                            new Intent(
                                    "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"),
                            335544320);
            extend.addAction(
                            new Notification.Action.Builder(
                                            (Icon) null,
                                            this.mContext.getString(
                                                    R.string.fp_power_button_bp_title),
                                            PendingIntent.getBroadcast(
                                                    this.mContext,
                                                    678432343,
                                                    new Intent(
                                                            "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"),
                                                    335544320))
                                    .build())
                    .addAction(
                            new Notification.Action.Builder(
                                            (Icon) null,
                                            this.mContext.getString(17043014),
                                            broadcast)
                                    .build())
                    .setContentTitle(this.mContext.getString(17043016))
                    .setContentText(this.mContext.getString(17043015))
                    .setStyle(
                            new Notification.BigTextStyle()
                                    .bigText(this.mContext.getString(17043015)));
        }
        notificationManager.notifyAsUser(
                "DevicePolicyManager", 678432343, extend.build(), UserHandle.ALL);
    }

    public final void registerRemoteBugreportReceivers() {
        try {
            this.mContext.registerReceiver(
                    this.mRemoteBugreportFinishedReceiver,
                    new IntentFilter(
                            "android.intent.action.REMOTE_BUGREPORT_DISPATCH",
                            "application/vnd.android.bugreport"),
                    2);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Slogf.w(
                    "DevicePolicyManager",
                    e,
                    "Failed to set type %s",
                    "application/vnd.android.bugreport");
        }
        this.mContext.registerReceiver(
                this.mRemoteBugreportConsentReceiver,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED",
                        "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"));
    }

    public final void shareBugreportWithDeviceOwnerIfExists(String str, String str2) {
        DevicePolicyManagerService devicePolicyManagerService = this.mService;
        try {
            try {
            } catch (FileNotFoundException unused) {
                Bundle bundle = new Bundle();
                bundle.putInt("android.app.extra.BUGREPORT_FAILURE_REASON", 1);
                devicePolicyManagerService.sendDeviceOwnerCommand(
                        bundle, "android.app.action.BUGREPORT_FAILED");
            }
            if (str == null) {
                throw new FileNotFoundException();
            }
            devicePolicyManagerService.sendBugreportToDeviceOwner(Uri.parse(str), str2);
        } finally {
            this.mRemoteBugreportSharingAccepted.set(false);
            devicePolicyManagerService.setDeviceOwnerRemoteBugreportUriAndHash(null, null);
        }
    }
}
