package com.android.server.net;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.connectivity.Vpn;

import java.util.List;
import java.util.Objects;

public final class LockdownVpnTracker {
    public String mAcceptedEgressIface;
    public final ConnectivityManager mCm;
    public final PendingIntent mConfigIntent;
    public final Context mContext;
    public final Handler mHandler;
    public final NotificationManager mNotificationManager;
    public final VpnProfile mProfile;
    public final PendingIntent mResetIntent;
    public final Vpn mVpn;
    public final Object mStateLock = new Object();
    public final NetworkCallback mDefaultNetworkCallback = new NetworkCallback();
    public final VpnNetworkCallback mVpnNetworkCallback = new VpnNetworkCallback();

    public class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public Network mNetwork = null;
        public LinkProperties mLinkProperties = null;

        public NetworkCallback() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            boolean z;
            if (network.equals(this.mNetwork)) {
                z = false;
            } else {
                this.mNetwork = network;
                z = true;
            }
            this.mLinkProperties = linkProperties;
            if (z) {
                synchronized (LockdownVpnTracker.this.mStateLock) {
                    LockdownVpnTracker.this.handleStateChangedLocked();
                }
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            this.mNetwork = null;
            this.mLinkProperties = null;
            synchronized (LockdownVpnTracker.this.mStateLock) {
                LockdownVpnTracker.this.handleStateChangedLocked();
            }
        }
    }

    public final class VpnNetworkCallback extends NetworkCallback {
        public VpnNetworkCallback() {
            super();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            synchronized (LockdownVpnTracker.this.mStateLock) {
                LockdownVpnTracker.this.handleStateChangedLocked();
            }
        }

        @Override // com.android.server.net.LockdownVpnTracker.NetworkCallback,
                  // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            onAvailable(network);
        }
    }

    public LockdownVpnTracker(Context context, Handler handler, Vpn vpn, VpnProfile vpnProfile) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mCm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        Objects.requireNonNull(handler);
        this.mHandler = handler;
        this.mVpn = vpn;
        this.mProfile = vpnProfile;
        this.mNotificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mConfigIntent =
                PendingIntent.getActivity(
                        context, 0, new Intent("android.settings.VPN_SETTINGS"), 67108864);
        Intent intent = new Intent("com.android.server.action.LOCKDOWN_RESET");
        intent.addFlags(1073741824);
        this.mResetIntent = PendingIntent.getBroadcast(context, 0, intent, 67108864);
    }

    public final void handleStateChangedLocked() {
        NetworkCallback networkCallback = this.mDefaultNetworkCallback;
        Network network = networkCallback.mNetwork;
        LinkProperties linkProperties = networkCallback.mLinkProperties;
        Vpn vpn = this.mVpn;
        NetworkInfo networkInfo = vpn.mNetworkInfo;
        VpnConfig legacyVpnConfig = vpn.getLegacyVpnConfig();
        boolean z = network == null;
        boolean z2 =
                linkProperties == null
                        || !TextUtils.equals(
                                this.mAcceptedEgressIface, linkProperties.getInterfaceName());
        String interfaceName = linkProperties == null ? null : linkProperties.getInterfaceName();
        Log.d(
                "LockdownVpnTracker",
                "handleStateChanged: egress=" + this.mAcceptedEgressIface + "->" + interfaceName);
        if (z || z2) {
            this.mAcceptedEgressIface = null;
            vpn.stopVpnRunnerPrivileged();
        }
        if (z) {
            this.mNotificationManager.cancel(null, 20);
            return;
        }
        if (networkInfo.isConnectedOrConnecting()) {
            if (!networkInfo.isConnected() || legacyVpnConfig == null) {
                return;
            }
            String str = legacyVpnConfig.interfaze;
            List list = legacyVpnConfig.addresses;
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "VPN connected using iface=", str, ", sourceAddr=");
            m.append(list.toString());
            Log.d("LockdownVpnTracker", m.toString());
            showNotification(17043466, 17304844);
            return;
        }
        if (!this.mProfile.isValidLockdownProfile()) {
            Log.e("LockdownVpnTracker", "Invalid VPN profile; requires IP-based server and DNS");
            showNotification(17043469, 17304845);
            return;
        }
        Log.d("LockdownVpnTracker", "Active network connected; starting VPN");
        showNotification(17043467, 17304845);
        this.mAcceptedEgressIface = interfaceName;
        try {
            vpn.startLegacyVpnPrivileged(this.mProfile, network, linkProperties);
        } catch (IllegalStateException e) {
            this.mAcceptedEgressIface = null;
            Log.e("LockdownVpnTracker", "Failed to start VPN", e);
            showNotification(17043469, 17304845);
        }
    }

    public final void initLocked() {
        Log.d("LockdownVpnTracker", "initLocked()");
        this.mVpn.mEnableTeardown = false;
        this.mVpn.setLockdown(true);
        this.mCm.setLegacyLockdownVpnEnabled(true);
        handleStateChangedLocked();
        this.mCm.registerSystemDefaultNetworkCallback(this.mDefaultNetworkCallback, this.mHandler);
        this.mCm.registerNetworkCallback(
                new NetworkRequest.Builder().clearCapabilities().addTransportType(4).build(),
                this.mVpnNetworkCallback,
                this.mHandler);
    }

    public final void showNotification(int i, int i2) {
        this.mNotificationManager.notify(
                null,
                20,
                new Notification.Builder(this.mContext, "VPN")
                        .setWhen(0L)
                        .setSmallIcon(i2)
                        .setContentTitle(this.mContext.getString(i))
                        .setContentText(this.mContext.getString(17043465))
                        .setContentIntent(this.mConfigIntent)
                        .setOngoing(true)
                        .addAction(
                                R.drawable.ic_perm_group_microphone,
                                this.mContext.getString(17042564),
                                this.mResetIntent)
                        .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
                        .build());
    }

    public final void shutdownLocked() {
        Log.d("LockdownVpnTracker", "shutdownLocked()");
        this.mCm.unregisterNetworkCallback(this.mDefaultNetworkCallback);
        this.mCm.unregisterNetworkCallback(this.mVpnNetworkCallback);
        this.mAcceptedEgressIface = null;
        this.mVpn.stopVpnRunnerPrivileged();
        this.mVpn.setLockdown(false);
        this.mCm.setLegacyLockdownVpnEnabled(false);
        this.mNotificationManager.cancel(null, 20);
        this.mVpn.mEnableTeardown = true;
    }
}
