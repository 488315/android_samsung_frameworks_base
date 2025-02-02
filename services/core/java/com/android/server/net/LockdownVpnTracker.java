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
import android.net.RouteInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.server.connectivity.Vpn;
import java.net.Inet4Address;
import java.util.Objects;

/* loaded from: classes2.dex */
public class LockdownVpnTracker {
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
    public LinkProperties mLinkProperties;
    public Network mNetwork;

    public NetworkCallback() {
      this.mNetwork = null;
      this.mLinkProperties = null;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
      boolean z;
      if (!LockdownVpnTracker.hasIPv4DefaultRoute(linkProperties)) {
        Log.e("LockdownVpnTracker", "onLinkPropertiesChanged: no IPv4 default gateway");
        return;
      }
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

    public Network getNetwork() {
      return this.mNetwork;
    }

    public LinkProperties getLinkProperties() {
      return this.mLinkProperties;
    }
  }

  public class VpnNetworkCallback extends NetworkCallback {
    public VpnNetworkCallback() {
      super();
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
      synchronized (LockdownVpnTracker.this.mStateLock) {
        LockdownVpnTracker.this.handleStateChangedLocked();
      }
    }

    @Override // com.android.server.net.LockdownVpnTracker.NetworkCallback,
              // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
      onAvailable(network);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public LockdownVpnTracker(Context context, Handler handler, Vpn vpn, VpnProfile vpnProfile) {
    Objects.requireNonNull(context);
    this.mContext = context;
    this.mCm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    Objects.requireNonNull(handler);
    this.mHandler = handler;
    Objects.requireNonNull(vpn);
    this.mVpn = vpn;
    Objects.requireNonNull(vpnProfile);
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

  public static boolean hasIPv4DefaultRoute(LinkProperties linkProperties) {
    for (RouteInfo routeInfo : linkProperties.getAllRoutes()) {
      if (routeInfo.isDefaultRoute() && (routeInfo.getGateway() instanceof Inet4Address)) {
        return true;
      }
    }
    return false;
  }

  public final void handleStateChangedLocked() {
    Network network = this.mDefaultNetworkCallback.getNetwork();
    LinkProperties linkProperties = this.mDefaultNetworkCallback.getLinkProperties();
    NetworkInfo networkInfo = this.mVpn.getNetworkInfo();
    VpnConfig legacyVpnConfig = this.mVpn.getLegacyVpnConfig();
    boolean z = true;
    boolean z2 = network == null;
    if (linkProperties != null
        && TextUtils.equals(this.mAcceptedEgressIface, linkProperties.getInterfaceName())) {
      z = false;
    }
    String interfaceName = linkProperties == null ? null : linkProperties.getInterfaceName();
    Log.d(
        "LockdownVpnTracker",
        "handleStateChanged: egress=" + this.mAcceptedEgressIface + "->" + interfaceName);
    if (z2 || z) {
      this.mAcceptedEgressIface = null;
      this.mVpn.stopVpnRunnerPrivileged();
    }
    if (z2) {
      hideNotification();
      return;
    }
    if (!networkInfo.isConnectedOrConnecting()) {
      if (!this.mProfile.isValidLockdownProfile()) {
        Log.e("LockdownVpnTracker", "Invalid VPN profile; requires IP-based server and DNS");
        showNotification(17043247, 17304615);
        return;
      }
      Log.d("LockdownVpnTracker", "Active network connected; starting VPN");
      showNotification(17043245, 17304615);
      this.mAcceptedEgressIface = interfaceName;
      try {
        this.mVpn.startLegacyVpnPrivileged(this.mProfile, network, linkProperties);
        return;
      } catch (IllegalStateException e) {
        this.mAcceptedEgressIface = null;
        Log.e("LockdownVpnTracker", "Failed to start VPN", e);
        showNotification(17043247, 17304615);
        return;
      }
    }
    if (!networkInfo.isConnected() || legacyVpnConfig == null) {
      return;
    }
    Log.d(
        "LockdownVpnTracker",
        "VPN connected using iface="
            + legacyVpnConfig.interfaze
            + ", sourceAddr="
            + legacyVpnConfig.addresses.toString());
    showNotification(17043244, 17304614);
  }

  public void init() {
    synchronized (this.mStateLock) {
      initLocked();
    }
  }

  public final void initLocked() {
    Log.d("LockdownVpnTracker", "initLocked()");
    this.mVpn.setEnableTeardown(false);
    this.mVpn.setLockdown(true);
    this.mCm.setLegacyLockdownVpnEnabled(true);
    handleStateChangedLocked();
    this.mCm.registerSystemDefaultNetworkCallback(this.mDefaultNetworkCallback, this.mHandler);
    this.mCm.registerNetworkCallback(
        new NetworkRequest.Builder().clearCapabilities().addTransportType(4).build(),
        this.mVpnNetworkCallback,
        this.mHandler);
  }

  public void shutdown() {
    synchronized (this.mStateLock) {
      shutdownLocked();
    }
  }

  public final void shutdownLocked() {
    Log.d("LockdownVpnTracker", "shutdownLocked()");
    this.mCm.unregisterNetworkCallback(this.mDefaultNetworkCallback);
    this.mCm.unregisterNetworkCallback(this.mVpnNetworkCallback);
    this.mAcceptedEgressIface = null;
    this.mVpn.stopVpnRunnerPrivileged();
    this.mVpn.setLockdown(false);
    this.mCm.setLegacyLockdownVpnEnabled(false);
    hideNotification();
    this.mVpn.setEnableTeardown(true);
  }

  public void reset() {
    Log.d("LockdownVpnTracker", "reset()");
    synchronized (this.mStateLock) {
      shutdownLocked();
      initLocked();
      handleStateChangedLocked();
    }
  }

  public final void showNotification(int i, int i2) {
    this.mNotificationManager.notify(
        null,
        20,
        new Notification.Builder(this.mContext, "VPN")
            .setWhen(0L)
            .setSmallIcon(i2)
            .setContentTitle(this.mContext.getString(i))
            .setContentText(this.mContext.getString(17043243))
            .setContentIntent(this.mConfigIntent)
            .setOngoing(true)
            .addAction(
                R.drawable.ic_menu_login, this.mContext.getString(17042406), this.mResetIntent)
            .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
            .build());
  }

  public final void hideNotification() {
    this.mNotificationManager.cancel(null, 20);
  }
}
