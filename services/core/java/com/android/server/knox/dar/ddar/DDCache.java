package com.android.server.knox.dar.ddar;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

/* loaded from: classes2.dex */
public class DDCache extends IProxyAgentService {
  public static volatile DDCache _instance;
  public Context context;
  public ISemPersonaManager semPersonaManager;

  public static synchronized DDCache getInstance(Context context) {
    DDCache dDCache;
    synchronized (DDCache.class) {
      if (_instance == null) {
        _instance = new DDCache(context);
      }
      dDCache = _instance;
    }
    return dDCache;
  }

  public DDCache(Context context) {
    this.context = context;
  }

  public final ISemPersonaManager getPersonaService() {
    if (this.semPersonaManager == null) {
      try {
        this.semPersonaManager =
            ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return this.semPersonaManager;
  }

  public void set(int i, String str, String str2) {
    try {
      getPersonaService().updatePersonaCache(constructuctCacheKey(i, str), str2);
    } catch (Exception e) {
      e.printStackTrace();
      DDLog.m34e("DDCache", "set value failed", new Object[0]);
    }
  }

  public String get(int i, String str) {
    try {
      return getPersonaService().getPersonaCacheValue(constructuctCacheKey(i, str));
    } catch (Exception e) {
      e.printStackTrace();
      DDLog.m34e("DDCache", "get value failed", new Object[0]);
      return null;
    }
  }

  public final String constructuctCacheKey(int i, String str) {
    return "DUALDAR_User_" + i + "_" + str;
  }

  /* JADX WARN: Removed duplicated region for block: B:13:0x005e  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x0088 A[Catch: Exception -> 0x009e, TRY_LEAVE, TryCatch #0 {Exception -> 0x009e, blocks: (B:3:0x0001, B:15:0x0063, B:18:0x0075, B:19:0x0088, B:20:0x0036, B:23:0x003f, B:26:0x0049), top: B:2:0x0001 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public Bundle onMessage(int i, String str, Bundle bundle) {
    try {
      char c = 0;
      DDLog.m33d("DDCache", "onMessage() " + str, new Object[0]);
      Bundle bundle2 = new Bundle();
      int hashCode = str.hashCode();
      if (hashCode == -227631335) {
        if (str.equals("DELETE_DATA_CMD")) {
          c = 2;
          if (c != 0) {}
          return bundle2;
        }
        c = 65535;
        if (c != 0) {}
        return bundle2;
      }
      if (hashCode == 180589038) {
        if (str.equals("GET_DATA_CMD")) {
          c = 1;
          if (c != 0) {}
          return bundle2;
        }
        c = 65535;
        if (c != 0) {}
        return bundle2;
      }
      if (hashCode == 1729581666 && str.equals("SET_DATA_CMD")) {
        if (c != 0) {
          enforceCallingUser(i);
          set(
              bundle.getInt("DUAL_DAR_USER_ID"),
              bundle.getString("DUAL_DAR_KEY"),
              bundle.getString("DUAL_DAR_VALUE"));
          bundle2.putBoolean("dual_dar_response", true);
        } else if (c == 1) {
          bundle2.putString(
              "DUAL_DAR_VALUE",
              get(bundle.getInt("DUAL_DAR_USER_ID"), bundle.getString("DUAL_DAR_KEY")));
          bundle2.putBoolean("dual_dar_response", true);
        } else if (c == 2) {
          enforceCallingUser(i);
          set(bundle.getInt("DUAL_DAR_USER_ID"), bundle.getString("DUAL_DAR_KEY"), null);
          bundle2.putBoolean("dual_dar_response", true);
        }
        return bundle2;
      }
      c = 65535;
      if (c != 0) {}
      return bundle2;
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
