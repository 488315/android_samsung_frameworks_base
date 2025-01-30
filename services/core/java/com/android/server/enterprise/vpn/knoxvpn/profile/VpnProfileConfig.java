package com.android.server.enterprise.vpn.knoxvpn.profile;

import android.os.Debug;
import android.os.UserHandle;
import android.util.Log;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class VpnProfileConfig {
  public static final boolean DBG = Debug.semIsProductDev();
  public static String TAG = "FW-VpnProfileConfig";
  public static VpnProfileConfig vpnConfig = null;
  public ConcurrentHashMap vpnProfileInfoMap = new ConcurrentHashMap();

  public static synchronized VpnProfileConfig getInstance() {
    VpnProfileConfig vpnProfileConfig;
    synchronized (VpnProfileConfig.class) {
      if (vpnConfig == null) {
        vpnConfig = new VpnProfileConfig();
      }
      vpnProfileConfig = vpnConfig;
    }
    return vpnProfileConfig;
  }

  public Set getProfileNames() {
    return this.vpnProfileInfoMap.keySet();
  }

  public Collection getProfileEntries() {
    return this.vpnProfileInfoMap.values();
  }

  public VpnProfileInfo getProfileEntry(String str) {
    if (str != null && this.vpnProfileInfoMap.containsKey(str)) {
      return (VpnProfileInfo) this.vpnProfileInfoMap.get(str);
    }
    return null;
  }

  public int getProfileCount() {
    return this.vpnProfileInfoMap.size();
  }

  public synchronized VpnProfileInfo addProfileEntry(String str, VpnProfileInfo vpnProfileInfo) {
    if (str == null || vpnProfileInfo == null) {
      return null;
    }
    return (VpnProfileInfo) this.vpnProfileInfoMap.put(str, vpnProfileInfo);
  }

  public synchronized VpnProfileInfo removeProfileEntry(String str) {
    if (str == null) {
      return null;
    }
    if (!this.vpnProfileInfoMap.containsKey(str)) {
      return null;
    }
    return (VpnProfileInfo) this.vpnProfileInfoMap.remove(str);
  }

  public boolean containsProfileEntry(String str) {
    if (str == null) {
      return false;
    }
    return this.vpnProfileInfoMap.containsKey(str);
  }

  public synchronized String getProfileNameForPackage(String str) {
    if (str == null) {
      return null;
    }
    for (VpnProfileInfo vpnProfileInfo : this.vpnProfileInfoMap.values()) {
      String profileName = vpnProfileInfo.getProfileName();
      if (vpnProfileInfo.getPackage(str) != null) {
        return profileName;
      }
    }
    return null;
  }

  public int checkAdminForProfile(int i, String str) {
    if (str == null) {
      Log.d(TAG, "KnoxVPN : Profile null :" + str);
      return -1;
    }
    VpnProfileInfo profileEntry = getProfileEntry(str);
    if (profileEntry == null) {
      Log.d(TAG, "KnoxVpn: Profile does not exist. profile : " + str);
      return 1;
    }
    if (i == profileEntry.getAdminId() || UserHandle.getAppId(i) == 1000) {
      if (DBG) {
        Log.d(TAG, "KnoxVpn: Profile admin validation success. profile : " + str);
      }
      return 0;
    }
    Log.d(TAG, "KnoxVpn: Admin does not have permissions for this profile : " + str);
    return -1;
  }
}
