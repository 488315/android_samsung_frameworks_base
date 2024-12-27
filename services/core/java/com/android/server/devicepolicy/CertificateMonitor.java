package com.android.server.devicepolicy;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.KeyChain;
import android.util.ArraySet;
import android.util.PluralsMessageFormatter;

import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.Preconditions;
import com.android.server.utils.Slogf;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CertificateMonitor {
    public final Handler mHandler;
    public final DevicePolicyManagerService.Injector mInjector;
    public final AnonymousClass1 mRootCaReceiver;
    public final DevicePolicyManagerService mService;

    public CertificateMonitor(
            DevicePolicyManagerService devicePolicyManagerService,
            DevicePolicyManagerService.Injector injector,
            Handler handler) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.devicepolicy.CertificateMonitor.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        CertificateMonitor.this.updateInstalledCertificates(
                                UserHandle.of(
                                        intent.getIntExtra(
                                                "android.intent.extra.user_handle",
                                                getSendingUserId())));
                    }
                };
        this.mService = devicePolicyManagerService;
        this.mInjector = injector;
        this.mHandler = handler;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.security.action.TRUST_STORE_CHANGED");
        intentFilter.setPriority(1000);
        injector.mContext.registerReceiverAsUser(
                broadcastReceiver, UserHandle.ALL, intentFilter, null, handler);
    }

    public final List getInstalledCaCertificates(UserHandle userHandle) {
        try {
            KeyChain.KeyChainConnection bindAsUser =
                    KeyChain.bindAsUser(this.mInjector.mContext, userHandle);
            try {
                List list = bindAsUser.getService().getUserCaAliases().getList();
                bindAsUser.close();
                return list;
            } finally {
            }
        } catch (AssertionError e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e2);
        }
    }

    public final void updateInstalledCertificates(UserHandle userHandle) {
        Set set;
        String string;
        int i;
        int i2;
        String str;
        int identifier = userHandle.getIdentifier();
        if (UserManager.get(this.mInjector.mContext).isUserUnlocked(identifier)) {
            try {
                List installedCaCertificates = getInstalledCaCertificates(userHandle);
                DevicePolicyManagerService devicePolicyManagerService = this.mService;
                Notification notification = null;
                if (devicePolicyManagerService.mHasFeature) {
                    devicePolicyManagerService.getCallerIdentity(null, null);
                    Preconditions.checkCallAuthorization(
                            devicePolicyManagerService.hasCallingOrSelfPermission(
                                    "android.permission.MANAGE_USERS"));
                    synchronized (devicePolicyManagerService.getLockObject()) {
                        try {
                            DevicePolicyData userData =
                                    devicePolicyManagerService.getUserData(
                                            userHandle.getIdentifier());
                            if (((ArraySet) userData.mOwnerInstalledCaCerts)
                                            .retainAll(installedCaCertificates)
                                    | userData.mAcceptedCaCertificates.retainAll(
                                            installedCaCertificates)) {
                                devicePolicyManagerService.saveSettingsLocked(
                                        userHandle.getIdentifier(), false, false, false);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
                int size = installedCaCertificates.size();
                DevicePolicyManagerService devicePolicyManagerService2 = this.mService;
                if (devicePolicyManagerService2.mHasFeature) {
                    synchronized (devicePolicyManagerService2.getLockObject()) {
                        set =
                                devicePolicyManagerService2.getUserData(userHandle.getIdentifier())
                                        .mAcceptedCaCertificates;
                    }
                } else {
                    set = Collections.emptySet();
                }
                int size2 = size - set.size();
                if (size2 == 0) {
                    this.mInjector
                            .getNotificationManager()
                            .cancelAsUser("DevicePolicyManager", 33, userHandle);
                    return;
                }
                DevicePolicyManagerService.Injector injector = this.mInjector;
                try {
                    Context createPackageContextAsUser =
                            injector.mContext.createPackageContextAsUser(
                                    injector.mContext.getPackageName(), 0, userHandle);
                    Resources resources = injector.mContext.getResources();
                    int identifier2 = userHandle.getIdentifier();
                    int identifier3 = userHandle.getIdentifier();
                    DevicePolicyManagerService devicePolicyManagerService3 = this.mService;
                    int i3 = 17304475;
                    if (devicePolicyManagerService3.getProfileOwnerAsUser(identifier3) != null) {
                        str =
                                resources.getString(
                                        17043108,
                                        devicePolicyManagerService3.getProfileOwnerName(
                                                userHandle.getIdentifier()));
                        i2 =
                                devicePolicyManagerService3.getProfileParentId(
                                        userHandle.getIdentifier());
                        i = 17304475;
                    } else {
                        if (devicePolicyManagerService3.getDeviceOwnerUserId()
                                == userHandle.getIdentifier()) {
                            string =
                                    resources.getString(
                                            17043108,
                                            devicePolicyManagerService3.getDeviceOwnerName());
                        } else {
                            string = resources.getString(17043107);
                            i3 = R.drawable.stat_sys_warning;
                        }
                        i = i3;
                        String str2 = string;
                        i2 = identifier2;
                        str = str2;
                    }
                    Intent intent = new Intent("com.android.settings.MONITORING_CERT_INFO");
                    intent.setFlags(268468224);
                    intent.putExtra("android.settings.extra.number_of_certificates", size2);
                    intent.putExtra("android.intent.extra.USER_ID", userHandle.getIdentifier());
                    ActivityInfo resolveActivityInfo =
                            intent.resolveActivityInfo(
                                    injector.mContext.getPackageManager(), 1048576);
                    if (resolveActivityInfo != null) {
                        intent.setComponent(resolveActivityInfo.getComponentName());
                    }
                    PendingIntent activityAsUser =
                            PendingIntent.getActivityAsUser(
                                    createPackageContextAsUser,
                                    0,
                                    intent,
                                    201326592,
                                    null,
                                    UserHandle.of(i2));
                    HashMap hashMap = new HashMap();
                    hashMap.put("count", Integer.valueOf(size2));
                    notification =
                            new Notification.Builder(
                                            createPackageContextAsUser,
                                            SystemNotificationChannels.SECURITY)
                                    .setSmallIcon(i)
                                    .setContentTitle(
                                            PluralsMessageFormatter.format(
                                                    resources, hashMap, 17043109))
                                    .setContentText(str)
                                    .setContentIntent(activityAsUser)
                                    .setShowWhen(false)
                                    .setColor(R.color.system_notification_accent_color)
                                    .build();
                } catch (PackageManager.NameNotFoundException e) {
                    Slogf.e("DevicePolicyManager", e, "Create context as %s failed", userHandle);
                }
                this.mInjector
                        .getNotificationManager()
                        .notifyAsUser("DevicePolicyManager", 33, notification, userHandle);
            } catch (RemoteException | RuntimeException e2) {
                Slogf.e(
                        "DevicePolicyManager",
                        e2,
                        "Could not retrieve certificates from KeyChain service for user %d",
                        Integer.valueOf(identifier));
            }
        }
    }
}
