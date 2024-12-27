package com.android.server;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.net.BaseNetworkObserver;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.net.IExtendedEthernetManager;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class ExtendedEthernetServiceImpl extends IExtendedEthernetManager.Stub {
    public ExtendedEthernetConfigStore mConfigStore;
    public final Context mContext;
    public EthernetManager mEthernetManager;
    public boolean mIsNotiShown;
    public final INetworkManagementService mNMService;
    public EthernetManager.TetheredInterfaceRequest mTetheredRequest;
    public TetheringManager mTetheringManager;
    public final Handler mHandler = new Handler();
    public final String mIfaceMatch = "eth\\d";
    public final ConcurrentHashMap mIpConfigurations = new ConcurrentHashMap();
    public String mLastIntentState = "Disconnected";
    public final AnonymousClass1 mEthernetCallback =
            new EthernetManager
                    .TetheredInterfaceCallback() { // from class:
                                                   // com.android.server.ExtendedEthernetServiceImpl.1

                /* renamed from: com.android.server.ExtendedEthernetServiceImpl$1$1, reason: invalid class name and collision with other inner class name */
                public final class C00041 implements TetheringManager.StartTetheringCallback {
                    public final void onTetheringFailed(int i) {
                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                i, "onTetheringFailed resultCode: ", "ExtendedEthernetServiceImpl");
                    }
                }

                public final void onAvailable(String str) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "TetheredInterfaceCallback onAvailable: ",
                            str,
                            "ExtendedEthernetServiceImpl");
                    if ("on"
                            .equals(
                                    Settings.System.getString(
                                            ExtendedEthernetServiceImpl.this.mContext
                                                    .getContentResolver(),
                                            "ETHERNET_TETHERING_MODE"))) {
                        C00041 c00041 = new C00041();
                        TetheringManager.TetheringRequest build =
                                new TetheringManager.TetheringRequest.Builder(5).build();
                        ExtendedEthernetServiceImpl extendedEthernetServiceImpl =
                                ExtendedEthernetServiceImpl.this;
                        TetheringManager tetheringManager =
                                extendedEthernetServiceImpl.mTetheringManager;
                        Handler handler = extendedEthernetServiceImpl.mHandler;
                        Objects.requireNonNull(handler);
                        tetheringManager.startTethering(
                                build,
                                new ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1(
                                        handler),
                                c00041);
                    }
                }

                public final void onUnavailable() {
                    Log.i("ExtendedEthernetServiceImpl", "TetheredInterfaceCallback onUnavailable");
                }
            };
    public final AnonymousClass2 mStateListener =
            new EthernetManager
                    .InterfaceStateListener() { // from class:
                                                // com.android.server.ExtendedEthernetServiceImpl.2
                public final void onInterfaceStateChanged(
                        String str, int i, int i2, IpConfiguration ipConfiguration) {
                    StringBuilder m =
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i2, i, "onInterfaceStateChanged ", ", state: ", ", conf: ");
                    m.append(ipConfiguration);
                    Log.i("ExtendedEthernetServiceImpl", m.toString());
                    ExtendedEthernetServiceImpl extendedEthernetServiceImpl =
                            ExtendedEthernetServiceImpl.this;
                    extendedEthernetServiceImpl.getClass();
                    if (i2 == 1) {
                        ExtendedEthernetServiceImpl.m63$$Nest$mupdateEthCableConnectNotification(
                                extendedEthernetServiceImpl, i == 2);
                    } else {
                        ExtendedEthernetServiceImpl.m63$$Nest$mupdateEthCableConnectNotification(
                                extendedEthernetServiceImpl, false);
                    }
                }
            };
    public final AnonymousClass3 mStateObserver =
            new ContentObserver(
                    new Handler()) { // from class: com.android.server.ExtendedEthernetServiceImpl.3
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    boolean z2 =
                            Settings.System.getIntForUser(
                                            ExtendedEthernetServiceImpl.this.mContext
                                                    .getContentResolver(),
                                            "eth_disabled",
                                            0,
                                            0)
                                    == 1;
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "ETH_DISABLED is changed to ", "ExtendedEthernetServiceImpl", z2);
                    if (z2) {
                        ExtendedEthernetServiceImpl.this.setLinkDown();
                    }
                }
            };

    public final class InterfaceObserver extends BaseNetworkObserver {
        public InterfaceObserver() {}

        public final void interfaceAdded(String str) {
            if (str.matches(ExtendedEthernetServiceImpl.this.mIfaceMatch)) {
                Log.i("ExtendedEthernetServiceImpl", "interfaceAdded: ".concat(str));
                ExtendedEthernetServiceImpl.this.mHandler.post(
                        new ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda1(
                                this, str, 0));
            }
        }

        public final void interfaceLinkStateChanged(final String str, final boolean z) {
            if (str.matches(ExtendedEthernetServiceImpl.this.mIfaceMatch)) {
                Log.i(
                        "ExtendedEthernetServiceImpl",
                        "interfaceLinkStateChanged: " + str + ", " + z);
                ExtendedEthernetServiceImpl.this.mHandler.post(
                        new Runnable(
                                str,
                                z) { // from class:
                                     // com.android.server.ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda0
                            public final /* synthetic */ boolean f$2;

                            {
                                this.f$2 = z;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                ExtendedEthernetServiceImpl.InterfaceObserver interfaceObserver =
                                        ExtendedEthernetServiceImpl.InterfaceObserver.this;
                                boolean z2 = this.f$2;
                                ExtendedEthernetServiceImpl extendedEthernetServiceImpl =
                                        ExtendedEthernetServiceImpl.this;
                                extendedEthernetServiceImpl.getClass();
                                String str2 = z2 ? "Connected" : "Disconnected";
                                if (str2.equals(extendedEthernetServiceImpl.mLastIntentState)) {
                                    return;
                                }
                                extendedEthernetServiceImpl.mLastIntentState = str2;
                                Intent m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                67108864, "samsung.net.ethernet.ETH_STATE_CHANGED");
                                m.putExtra(
                                        "eth_state", extendedEthernetServiceImpl.mLastIntentState);
                                m.setPackage(KnoxCustomManagerService.SETTING_PKG_NAME);
                                Log.i("ExtendedEthernetServiceImpl", "send intent: ".concat(str2));
                                extendedEthernetServiceImpl.mContext.sendBroadcastAsUser(
                                        m, UserHandle.ALL);
                            }
                        });
            }
        }

        public final void interfaceRemoved(String str) {
            if (str.matches(ExtendedEthernetServiceImpl.this.mIfaceMatch)) {
                Log.i("ExtendedEthernetServiceImpl", "interfaceRemoved: ".concat(str));
                ExtendedEthernetServiceImpl.this.mHandler.post(
                        new ExtendedEthernetServiceImpl$InterfaceObserver$$ExternalSyntheticLambda1(
                                this, str, 1));
            }
        }
    }

    /* renamed from: -$$Nest$mupdateEthCableConnectNotification, reason: not valid java name */
    public static void m63$$Nest$mupdateEthCableConnectNotification(
            ExtendedEthernetServiceImpl extendedEthernetServiceImpl, boolean z) {
        if (extendedEthernetServiceImpl.mIsNotiShown == z) {
            return;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "updateEthCableConnectNotification ", "ExtendedEthernetServiceImpl", z);
        NotificationManager notificationManager =
                (NotificationManager)
                        extendedEthernetServiceImpl.mContext.getSystemService("notification");
        if (notificationManager == null) {
            return;
        }
        extendedEthernetServiceImpl.mIsNotiShown = z;
        if (!z) {
            notificationManager.cancelAsUser(null, 1000, UserHandle.ALL);
            return;
        }
        String string =
                extendedEthernetServiceImpl
                        .mContext
                        .getResources()
                        .getString(R.string.input_method_binding_label);
        String string2 =
                extendedEthernetServiceImpl.mContext.getResources().getString(R.string.inputMethod);
        Intent intent = new Intent();
        intent.setClassName(
                KnoxCustomManagerService.SETTING_PKG_NAME,
                "com.android.settings.Settings$EthernetSettingsActivity");
        intent.setAction("com.samsung.settings.ETHERNET_SETTINGS");
        intent.setFlags(268468224);
        PendingIntent activityAsUser =
                PendingIntent.getActivityAsUser(
                        extendedEthernetServiceImpl.mContext,
                        0,
                        intent,
                        33554432,
                        null,
                        UserHandle.CURRENT);
        Notification.Builder builder =
                new Notification.Builder(
                        extendedEthernetServiceImpl.mContext, SystemNotificationChannels.ETHERNET);
        builder.setSmallIcon(17304434)
                .setWhen(0L)
                .setOngoing(true)
                .setTicker(string)
                .setDefaults(0)
                .setPriority(1)
                .setContentTitle(string)
                .setContentText(string2)
                .setContentIntent(activityAsUser);
        notificationManager.notifyAsUser(null, 1000, builder.build(), UserHandle.ALL);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.ExtendedEthernetServiceImpl$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.ExtendedEthernetServiceImpl$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.ExtendedEthernetServiceImpl$3] */
    public ExtendedEthernetServiceImpl(Context context) {
        this.mContext = context;
        INetworkManagementService asInterface =
                INetworkManagementService.Stub.asInterface(
                        ServiceManager.getService("network_management"));
        this.mNMService = asInterface;
        try {
            asInterface.registerObserver(new InterfaceObserver());
        } catch (RemoteException | NullPointerException e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "Could not register InterfaceObserver ", "ExtendedEthernetServiceImpl");
        }
    }

    public final IpConfiguration getConfiguration(String str) {
        ArrayMap arrayMap;
        DualAppManagerService$$ExternalSyntheticOutline0.m(
                "getConfiguration for: ", str, "ExtendedEthernetServiceImpl");
        this.mConfigStore.read();
        ExtendedEthernetConfigStore extendedEthernetConfigStore = this.mConfigStore;
        synchronized (extendedEthernetConfigStore.mSync) {
            arrayMap = new ArrayMap(extendedEthernetConfigStore.mIpConfigurations);
        }
        for (int i = 0; i < arrayMap.size(); i++) {
            this.mIpConfigurations.put(
                    (String) arrayMap.keyAt(i), (IpConfiguration) arrayMap.valueAt(i));
        }
        IpConfiguration ipConfiguration = (IpConfiguration) this.mIpConfigurations.get(str);
        if (ipConfiguration != null) {
            return ipConfiguration;
        }
        IpConfiguration ipConfiguration2 = new IpConfiguration();
        ipConfiguration2.setIpAssignment(IpConfiguration.IpAssignment.DHCP);
        ipConfiguration2.setProxySettings(IpConfiguration.ProxySettings.NONE);
        return ipConfiguration2;
    }

    public final void setLinkDown() {
        try {
            for (String str : this.mNMService.listInterfaces()) {
                Log.e("ExtendedEthernetServiceImpl", "iface: " + str);
                if (str.matches(this.mIfaceMatch)) {
                    Log.e("ExtendedEthernetServiceImpl", "call setInterfaceDown");
                    this.mNMService.setInterfaceDown(str);
                }
            }
        } catch (RemoteException | IllegalStateException | NullPointerException unused) {
        }
    }
}
