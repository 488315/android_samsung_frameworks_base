package com.android.server.enterprise.vpn.knoxvpn.profile;

import android.net.ProxyInfo;
import android.net.Uri;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class VpnProfileInfo {
  public volatile int activateState;
  public volatile int admin_id;
  public volatile int chainingEnabled;
  public volatile String mInterfaceName;
  public volatile String mProfileName;
  public volatile String mVendorPkgName;
  public long mVpnStartTime;
  public long mVpnStartTimeToConnect;
  public volatile boolean markProfileForDeletion;
  public volatile int personaId;
  public volatile int profileId;
  public volatile String protocolType;
  public volatile int routeType;
  public volatile int uidPidSearchEnabled;
  public volatile int vpnConnectionType;
  public volatile boolean mIsRetry = false;
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
  public volatile boolean isProxySetupCompleted = false;
  public volatile int mUsbTethering = 0;
  public ConcurrentHashMap mPackageMap = new ConcurrentHashMap();
  public HashSet mPackageUidSet = new HashSet();
  public HashSet mExemptPackageList = new HashSet();
  public volatile String mIpChainName = null;
  public volatile int mDefaultNetworkId = 0;
  public volatile int isUsbTetheringAuthEnabled = 0;

  public String getProfileName() {
    return this.mProfileName;
  }

  public void setProfileName(String str) {
    this.mProfileName = str;
  }

  public String getInterfaceName() {
    return this.mInterfaceName;
  }

  public void setInterfaceName(String str) {
    this.mInterfaceName = str;
  }

  public String getVendorPkgName() {
    return this.mVendorPkgName;
  }

  public void setVendorPkgName(String str) {
    this.mVendorPkgName = str;
  }

  public int getNetId() {
    return this.mNetId;
  }

  public void setNetId(int i) {
    this.mNetId = i;
  }

  public int getAdminId() {
    return this.admin_id;
  }

  public void setAdminId(int i) {
    this.admin_id = i;
  }

  public int getPersonaId() {
    return this.personaId;
  }

  public void setPersonaId(int i) {
    this.personaId = i;
  }

  public int getProfileId() {
    return this.profileId;
  }

  public void setProfileId(int i) {
    this.profileId = i;
  }

  public int getRouteType() {
    return this.routeType;
  }

  public void setRouteType(int i) {
    this.routeType = i;
  }

  public String getProtocolType() {
    return this.protocolType;
  }

  public void setProtocolType(String str) {
    this.protocolType = str;
  }

  public int getActivateState() {
    return this.activateState;
  }

  public void setActivateState(int i) {
    this.activateState = i;
  }

  public int getChainingEnabled() {
    return this.chainingEnabled;
  }

  public void setChainingEnabled(int i) {
    this.chainingEnabled = i;
  }

  public int getUidPidSearchEnabled() {
    return this.uidPidSearchEnabled;
  }

  public void setUidPidSearchEnabled(int i) {
    this.uidPidSearchEnabled = i;
  }

  public int getVpnConnectionType() {
    return this.vpnConnectionType;
  }

  public void setVendorUid(int i) {
    this.mVendorUid = i;
  }

  public int getVendorUid() {
    return this.mVendorUid;
  }

  public void setVpnConnectionType(int i) {
    this.vpnConnectionType = i;
  }

  public void setMarkProfileForDeletion(boolean z) {
    this.markProfileForDeletion = z;
  }

  public Collection getPackageList() {
    return this.mPackageMap.values();
  }

  public VpnPackageInfo getPackage(String str) {
    if (this.mPackageMap.containsKey(str)) {
      return (VpnPackageInfo) this.mPackageMap.get(str);
    }
    return null;
  }

  public int getPackageCount() {
    return this.mPackageMap.size();
  }

  public void addPackageEntry(String str, int i, int i2) {
    this.mPackageMap.put(str, new VpnPackageInfo(str, i, i2));
    this.mPackageUidSet.add(Integer.valueOf(i));
  }

  public void removePackageEntry(String str) {
    if (this.mPackageMap.containsKey(str)) {
      this.mPackageUidSet.remove(
          Integer.valueOf(((VpnPackageInfo) this.mPackageMap.get(str)).getUid()));
      this.mPackageMap.remove(str);
    }
  }

  public void setProxyServer(String str) {
    this.mProxyServer = str;
  }

  public String getProxyServer() {
    return this.mProxyServer;
  }

  public void setProxyPort(int i) {
    this.mProxyPort = i;
  }

  public int getProxyPort() {
    return this.mProxyPort;
  }

  public synchronized void setProxyInfo(ProxyInfo proxyInfo) {
    this.mProxyInfo = proxyInfo;
  }

  public synchronized ProxyInfo getProxyInfo() {
    return this.mProxyInfo;
  }

  public String getProxyUsername() {
    return this.mProxyusername;
  }

  public void setProxyUsername(String str) {
    this.mProxyusername = str;
  }

  public String getProxyPassword() {
    return this.mProxyPassword;
  }

  public void setProxyPassword(String str) {
    this.mProxyPassword = str;
  }

  public Uri getPacurl() {
    return this.mPacurl;
  }

  public void setPacurl(String str) {
    if (str == null) {
      this.mPacurl = Uri.EMPTY;
    } else {
      this.mPacurl = Uri.parse(str);
    }
  }

  public void setInterfaceAddress(String str) {
    this.mInterfaceAddress = str;
  }

  public String getInterfaceAddress() {
    return this.mInterfaceAddress;
  }

  public void setDefaultInterface(String str) {
    this.mDefaultInterface = str;
  }

  public String getDefaultInterface() {
    return this.mDefaultInterface;
  }

  public void setV6InterfaceAddress(String str) {
    this.mInterfaceV6Address = str;
  }

  public String getV6InterfaceAddress() {
    return this.mInterfaceV6Address;
  }

  public void proxyCredentialsPreDefined(boolean z) {
    this.credentialsPredefined = z;
  }

  public boolean isproxyCredentialsPreDefined() {
    return this.credentialsPredefined;
  }

  public void setPacAuthRequired(int i) {
    this.proxyAuthRequried = i;
  }

  public int isProxyAuthRequired() {
    return this.proxyAuthRequried;
  }

  public void setVpnType(int i) {
    this.mVpnClientType = i;
  }

  public int getVpnType() {
    return this.mVpnClientType;
  }

  public void setInterfaceType(int i) {
    this.mInterface_type = i;
  }

  public int getInterfaceType() {
    return this.mInterface_type;
  }

  public void removeUidFromExemptList(int i) {
    if (this.mExemptPackageList.contains(Integer.valueOf(i))) {
      this.mExemptPackageList.remove(Integer.valueOf(i));
    }
  }

  public HashSet getExemptPackageList() {
    return this.mExemptPackageList;
  }

  public void setIpChainName(String str) {
    this.mIpChainName = str;
  }

  public String getIpChainName() {
    return this.mIpChainName;
  }

  public long getVpnStartTime() {
    return this.mVpnStartTime;
  }

  public void setVpnStartTime(long j) {
    this.mVpnStartTime = j;
  }

  public long getVpnStartTimeToConnect() {
    return this.mVpnStartTimeToConnect;
  }

  public void setVpnStartTimeToConnect(long j) {
    this.mVpnStartTimeToConnect = j;
  }

  public void setUsbTethering(int i) {
    this.mUsbTethering = i;
  }

  public int getUsbTethering() {
    return this.mUsbTethering;
  }

  public void setUsbTetherAuth(int i) {
    this.isUsbTetheringAuthEnabled = i;
  }

  public int getUsbtetherAuth() {
    return this.isUsbTetheringAuthEnabled;
  }
}
