package com.android.server.enterprise.vpn.knoxvpn.profile;

import android.os.Debug;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VpnProfileConfig {
    public static final boolean DBG = Debug.semIsProductDev();
    public static final String TAG = "FW-VpnProfileConfig";
    public static VpnProfileConfig vpnConfig = null;
    public ConcurrentHashMap vpnProfileInfoMap;

    public static synchronized VpnProfileConfig getInstance() {
        VpnProfileConfig vpnProfileConfig;
        synchronized (VpnProfileConfig.class) {
            try {
                if (vpnConfig == null) {
                    VpnProfileConfig vpnProfileConfig2 = new VpnProfileConfig();
                    vpnProfileConfig2.vpnProfileInfoMap = new ConcurrentHashMap();
                    vpnConfig = vpnProfileConfig2;
                }
                vpnProfileConfig = vpnConfig;
            } catch (Throwable th) {
                throw th;
            }
        }
        return vpnProfileConfig;
    }

    public final VpnProfileInfo getProfileEntry(String str) {
        if (str != null && this.vpnProfileInfoMap.containsKey(str)) {
            return (VpnProfileInfo) this.vpnProfileInfoMap.get(str);
        }
        return null;
    }

    public final synchronized String getProfileNameForPackage(String str) {
        if (str == null) {
            return null;
        }
        for (VpnProfileInfo vpnProfileInfo : this.vpnProfileInfoMap.values()) {
            String str2 = vpnProfileInfo.mProfileName;
            if (vpnProfileInfo.getPackage(str) != null) {
                return str2;
            }
        }
        return null;
    }

    public final Set getProfileNames() {
        return this.vpnProfileInfoMap.keySet();
    }
}
