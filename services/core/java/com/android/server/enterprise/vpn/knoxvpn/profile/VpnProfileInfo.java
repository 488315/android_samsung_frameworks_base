package com.android.server.enterprise.vpn.knoxvpn.profile;

import android.net.ProxyInfo;
import android.net.Uri;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public final class VpnProfileInfo {
    public volatile int activateState;
    public volatile int admin_id;
    public volatile int chainingEnabled;
    public volatile String mInterfaceName;
    public volatile String mProfileName;
    public volatile String mVendorPkgName;
    public long mVpnStartTimeToConnect;
    public volatile int personaId;
    public volatile int profileId;
    public volatile String protocolType;
    public volatile int routeType;
    public volatile int uidPidSearchEnabled;
    public volatile int vpnConnectionType;
    public volatile int mVendorUid = -1;
    public volatile int mNetId = 0;
    public volatile String mProxyServer = null;
    public volatile int mProxyPort = -1;
    public ProxyInfo mProxyInfo = null;
    public volatile String mProxyusername = null;
    public volatile String mProxyPassword = null;
    public volatile Uri mPacurl = Uri.EMPTY;
    public volatile String mInterfaceAddress = null;
    public volatile String mDefaultInterface = null;
    public volatile String mInterfaceV6Address = null;
    public volatile boolean credentialsPredefined = false;
    public volatile int proxyAuthRequried = 0;
    public volatile int mVpnClientType = 0;
    public volatile int mInterface_type = 0;
    public volatile int mUsbTethering = 0;
    public final ConcurrentHashMap mPackageMap = new ConcurrentHashMap();
    public final HashSet mPackageUidSet = new HashSet();
    public final HashSet mExemptPackageList = new HashSet();
    public volatile String mIpChainName = null;
    public volatile int isUsbTetheringAuthEnabled = 0;

    public final void addPackageEntry(int i, int i2, String str) {
        VpnPackageInfo vpnPackageInfo = new VpnPackageInfo();
        vpnPackageInfo.mPersonaedPackageName = str;
        vpnPackageInfo.mUid = i;
        vpnPackageInfo.mCid = i2;
        this.mPackageMap.put(str, vpnPackageInfo);
        this.mPackageUidSet.add(Integer.valueOf(i));
    }

    public final VpnPackageInfo getPackage(String str) {
        if (this.mPackageMap.containsKey(str)) {
            return (VpnPackageInfo) this.mPackageMap.get(str);
        }
        return null;
    }

    public final String getProfileName() {
        return this.mProfileName;
    }

    public final synchronized ProxyInfo getProxyInfo() {
        return this.mProxyInfo;
    }

    public final void removePackageEntry(String str) {
        if (this.mPackageMap.containsKey(str)) {
            this.mPackageUidSet.remove(
                    Integer.valueOf(((VpnPackageInfo) this.mPackageMap.get(str)).getUid()));
            this.mPackageMap.remove(str);
        }
    }
}
