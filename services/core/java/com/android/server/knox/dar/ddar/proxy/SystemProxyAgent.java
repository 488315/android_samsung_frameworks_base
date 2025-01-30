package com.android.server.knox.dar.ddar.proxy;

import android.content.Context;
import android.os.Bundle;
import com.android.server.knox.dar.ddar.DDCache;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.ddar.core.DualDarManagerProxy;
import com.android.server.knox.dar.ddar.fsm.StateMachineProxy;
import com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy;
import com.android.server.knox.dar.ddar.p009ta.TAProxy;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyAgent;

/* loaded from: classes2.dex */
public class SystemProxyAgent extends KnoxProxyAgent {
  public static SystemProxyAgent mInstance;
  public final Context mContext;

  public static synchronized SystemProxyAgent getInstance(Context context) {
    SystemProxyAgent systemProxyAgent;
    synchronized (SystemProxyAgent.class) {
      if (mInstance == null) {
        mInstance = new SystemProxyAgent(context);
      }
      systemProxyAgent = mInstance;
    }
    return systemProxyAgent;
  }

  public SystemProxyAgent(Context context) {
    this.mContext = context;
  }

  public void init() {
    super.onCreate();
    register("TA_PROXY_SERVICE", TAProxy.getInstance(this.mContext));
    register("DAEMON_PROXY_SERVICE", DualDARDaemonProxy.getInstance(this.mContext));
    register("STATE_MACHINE_SERVICE", StateMachineProxy.getInstance(this.mContext));
    register("DDAR_LOG_SERVICE", DDLog.LoggerProxy.getInstance(this.mContext));
    register("DDAR_CACHE_SERVICE", DDCache.getInstance(this.mContext));
    register("DDAR_PLATFORM_SERVICE", DualDARPlatformProxy.getInstance(this.mContext));
    register("DDAR_MANAGER_SERVICE", DualDarManagerProxy.getInstance(this.mContext));
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x0045  */
  /* JADX WARN: Removed duplicated region for block: B:18:0x005e A[Catch: Exception -> 0x0076, TRY_LEAVE, TryCatch #0 {Exception -> 0x0076, blocks: (B:3:0x001a, B:12:0x0047, B:15:0x004c, B:18:0x005e, B:19:0x002a, B:22:0x0034), top: B:2:0x001a }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Bundle relay(int i, String str, String str2, Bundle bundle) {
    char c;
    Bundle bundle2;
    DDLog.m33d("SystemProxyAgent", "relay to Service : " + str, new Object[0]);
    try {
      int hashCode = str2.hashCode();
      if (hashCode == 636043837) {
        if (str2.equals("INITIALIZE_SECURE_SESSION")) {
          c = 0;
          if (c != 0) {}
          return bundle2;
        }
        c = 65535;
        if (c != 0) {}
        return bundle2;
      }
      if (hashCode == 681038700 && str2.equals("TERMINATE_SECURE_SESSION")) {
        c = 1;
        if (c != 0) {
          String establishSecureSession =
              establishSecureSession(
                  i,
                  str,
                  bundle.getString("SECURE_CLIENT_ID"),
                  bundle.getString("SECURE_CLIENT_PUB_KEY"));
          bundle2 = new Bundle();
          bundle2.putString("dual_dar_response", establishSecureSession);
        } else if (c == 1) {
          boolean teardownSecureSession =
              teardownSecureSession(i, str, bundle.getString("SECURE_CLIENT_ID"));
          bundle2 = new Bundle();
          bundle2.putBoolean("dual_dar_response", teardownSecureSession);
        } else {
          return super.relay(i, str, str2, bundle);
        }
        return bundle2;
      }
      c = 65535;
      if (c != 0) {}
      return bundle2;
    } catch (Exception e) {
      DDLog.m34e(
          "SystemProxyAgent", "RemoteException: name:" + str + " command:" + str2, new Object[0]);
      e.printStackTrace();
      return null;
    }
  }
}
