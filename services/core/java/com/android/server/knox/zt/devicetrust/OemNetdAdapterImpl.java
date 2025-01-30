package com.android.server.knox.zt.devicetrust;

import android.net.INetd;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.net.IOemNetd;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes2.dex */
public class OemNetdAdapterImpl implements OemNetdAdapter {
  public static final String TAG = OemNetdAdapterImpl.class.getSimpleName() + ".ztd";

  public final IOemNetd getOemNetdService() {
    try {
      return IOemNetd.Stub.asInterface(
          INetd.Stub.asInterface(ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME))
              .getOemNetd());
    } catch (Throwable th) {
      th.printStackTrace();
      return null;
    }
  }

  @Override // com.android.server.knox.zt.devicetrust.OemNetdAdapter
  public int attachProbes(int i) {
    Log.i(TAG, "attachProbes(" + i + ")");
    if ((i & 64) > 0) {
      return enablePacketTracing();
    }
    return -2;
  }

  @Override // com.android.server.knox.zt.devicetrust.OemNetdAdapter
  public int detachProbes(int i) {
    Log.i(TAG, "detachProbes(" + i + ")");
    if ((i & 64) > 0) {
      return disablePacketTracing();
    }
    return -2;
  }

  /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int enablePacketTracing() {
    int enableTlsPacketTracing;
    IOemNetd oemNetdService = getOemNetdService();
    if (oemNetdService != null) {
      try {
        enableTlsPacketTracing = oemNetdService.enableTlsPacketTracing("wlan0");
      } catch (RemoteException e) {
        e.printStackTrace();
      }
      if (enableTlsPacketTracing != 0) {
        Log.e(TAG, "Failed to enable packet tracing(" + enableTlsPacketTracing + ")");
      }
      return enableTlsPacketTracing;
    }
    enableTlsPacketTracing = -6;
    if (enableTlsPacketTracing != 0) {}
    return enableTlsPacketTracing;
  }

  /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int disablePacketTracing() {
    int disableTlsPacketTracing;
    IOemNetd oemNetdService = getOemNetdService();
    if (oemNetdService != null) {
      try {
        disableTlsPacketTracing = oemNetdService.disableTlsPacketTracing("wlan0");
      } catch (RemoteException e) {
        e.printStackTrace();
      }
      if (disableTlsPacketTracing != 0) {
        Log.e(TAG, "Failed to disable packet tracing(" + disableTlsPacketTracing + ")");
      }
      return disableTlsPacketTracing;
    }
    disableTlsPacketTracing = -6;
    if (disableTlsPacketTracing != 0) {}
    return disableTlsPacketTracing;
  }
}
