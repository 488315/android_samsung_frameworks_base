package android.net.util;

import android.net.INetd;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes.dex */
public class NetdService {
  private static final long BASE_TIMEOUT_MS = 100;
  private static final long MAX_TIMEOUT_MS = 1000;
  private static final String TAG = "NetdService";

  public interface NetdCommand {
    void run(INetd iNetd);
  }

  public static INetd getInstance() {
    INetd asInterface =
        INetd.Stub.asInterface(ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
    if (asInterface == null) {
      Log.w(TAG, "WARNING: returning null INetd instance.");
    }
    return asInterface;
  }

  public static INetd get(long j) {
    if (j == 0) {
      return getInstance();
    }
    long elapsedRealtime = j > 0 ? SystemClock.elapsedRealtime() + j : Long.MAX_VALUE;
    long j2 = 0;
    while (true) {
      INetd netdService = getInstance();
      if (netdService != null) {
        return netdService;
      }
      long elapsedRealtime2 = elapsedRealtime - SystemClock.elapsedRealtime();
      if (elapsedRealtime2 <= 0) {
        return null;
      }
      j2 = Math.min(Math.min(j2 + 100, 1000L), elapsedRealtime2);
      try {
        Thread.sleep(j2);
      } catch (InterruptedException unused) {
      }
    }
  }

  public static INetd get() {
    return get(-1L);
  }

  public static void run(NetdCommand netdCommand) {
    while (true) {
      try {
        netdCommand.run(get());
        return;
      } catch (RemoteException e) {
        Log.e(TAG, "error communicating with netd: " + e);
      }
    }
  }
}
