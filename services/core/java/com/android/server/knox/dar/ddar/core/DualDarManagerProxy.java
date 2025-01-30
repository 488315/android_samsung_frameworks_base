package com.android.server.knox.dar.ddar.core;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

/* loaded from: classes2.dex */
public class DualDarManagerProxy extends IProxyAgentService {
  public static final boolean DEBUG;
  public static DualDarManagerProxy sInstance;
  public Context mContext;
  public DualDarManagerImpl mDualDarManagerImpl;

  static {
    DEBUG =
        "eng".equals(SystemProperties.get("ro.build.type"))
            || "userdebug".equals(SystemProperties.get("ro.build.type"));
  }

  public DualDarManagerProxy(Context context) {
    DDLog.m33d("DualDarManagerProxy", "DualDarManagerProxy created", new Object[0]);
    this.mDualDarManagerImpl = new DualDarManagerImpl(context);
    this.mContext = context;
  }

  public static synchronized DualDarManagerProxy getInstance(Context context) {
    DualDarManagerProxy dualDarManagerProxy;
    synchronized (DualDarManagerProxy.class) {
      if (sInstance == null) {
        sInstance = new DualDarManagerProxy(context);
      }
      dualDarManagerProxy = sInstance;
    }
    return dualDarManagerProxy;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x00cf, code lost:

     return r0;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Bundle onMessage(int i, String str, Bundle bundle) {
    char c;
    try {
      if (DEBUG) {
        Log.d("DualDarManagerProxy", "onMessage() " + str);
      }
      Bundle bundle2 = new Bundle();
      switch (str.hashCode()) {
        case -1773691899:
          if (str.equals("SET_TRIAL_PERIOD_TIME")) {
            c = 5;
            break;
          }
          c = 65535;
          break;
        case -1745382117:
          if (str.equals("CANCEL_DATA_LOCK")) {
            c = 4;
            break;
          }
          c = 65535;
          break;
        case 113022256:
          if (str.equals("CLEAR_TRIAL_PERIOD_TIME")) {
            c = 6;
            break;
          }
          c = 65535;
          break;
        case 926296911:
          if (str.equals("IS_INNER_LAYER_UNLOCKED")) {
            c = 1;
            break;
          }
          c = 65535;
          break;
        case 1048019351:
          if (str.equals("ENSURE_DATA_UNLOCKED")) {
            c = 2;
            break;
          }
          c = 65535;
          break;
        case 1209819970:
          if (str.equals("ON_DEVICE_OWNER_PROVISIONING")) {
            c = 0;
            break;
          }
          c = 65535;
          break;
        case 1428632824:
          if (str.equals("SCHEDULE_DATA_LOCK")) {
            c = 3;
            break;
          }
          c = 65535;
          break;
        default:
          c = 65535;
          break;
      }
      switch (c) {
        case 0:
          enforceCallingUser(i);
          bundle2.putBoolean(
              "dual_dar_response", this.mDualDarManagerImpl.handleDeviceOwnerProvisioning(bundle));
          break;
        case 1:
          bundle2.putBoolean("dual_dar_response", this.mDualDarManagerImpl.isInnerLayerUnlocked());
          break;
        case 2:
          enforceCallingUser(i);
          bundle2.putBoolean(
              "dual_dar_response", this.mDualDarManagerImpl.handleEnsureDataUnlocked());
          break;
        case 3:
          enforceCallingUser(i);
          this.mDualDarManagerImpl.handleDataLock(bundle.getInt("user_id"));
          break;
        case 4:
          enforceCallingUser(i);
          this.mDualDarManagerImpl.cancelDataLock(bundle.getInt("user_id"));
          break;
        case 5:
          enforceCallingUser(i);
          bundle2.putBoolean(
              "dual_dar_response",
              this.mDualDarManagerImpl.handleScheduleDualDarTrialExpiryTimer(bundle));
          break;
        case 6:
          enforceCallingUser(i);
          bundle2.putBoolean(
              "dual_dar_response",
              this.mDualDarManagerImpl.handleClearDualDarTrialExpiryTimer(bundle));
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final void enforceCallingUser(int i) {
    if (UserHandle.getAppId(i) == 5250
        || UserHandle.getAppId(i) == 1000
        || UserHandle.getAppId(i) == Process.myUid()) {
      return;
    }
    throw new SecurityException("Can only be called by system user. callingUid: " + i);
  }
}
