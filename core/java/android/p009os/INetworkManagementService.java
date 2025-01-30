package android.p009os;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.usb.UsbManager;
import android.net.INetworkManagementEventObserver;
import android.net.ITetheringStatsProvider;
import android.net.InterfaceConfiguration;
import android.net.Network;
import android.net.NetworkStats;
import android.net.RouteInfo;
import java.util.List;

/* loaded from: classes3.dex */
public interface INetworkManagementService extends IInterface {
  int addApeRule(boolean z, String str, int i) throws RemoteException;

  void addChain(String str, String str2) throws RemoteException;

  void addInterfaceToLocalNetwork(String str, List<RouteInfo> list) throws RemoteException;

  void addIpAcceptRule(String str, String str2, String str3) throws RemoteException;

  void addLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException;

  int addMnxbRule(boolean z, String str, int i) throws RemoteException;

  void addMptcpLink(String str) throws RemoteException;

  void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) throws RemoteException;

  void addPortFwdRules(String str, String str2, String str3, String str4, int i)
      throws RemoteException;

  void addRoute(int i, RouteInfo routeInfo) throws RemoteException;

  void addSocksRule(String str, String str2, String str3, int i, String str4)
      throws RemoteException;

  void addSocksSkipRule(String str, String str2, String str3) throws RemoteException;

  void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4)
      throws RemoteException;

  void addSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

  void addSourceRoute(String str, String str2, String str3) throws RemoteException;

  void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4)
      throws RemoteException;

  void addUidToChain(String str, String str2, int i) throws RemoteException;

  void allowProtect(int i) throws RemoteException;

  void buildFirewall() throws RemoteException;

  void cleanAllBlock() throws RemoteException;

  void cleanBlockPorts() throws RemoteException;

  void clearEbpfMap(int i) throws RemoteException;

  void clearInterfaceAddresses(String str) throws RemoteException;

  void closeSocketsForFreecess(int i, String str) throws RemoteException;

  void closeSocketsForUid(int i) throws RemoteException;

  void closeSocketsForUids(int[] iArr) throws RemoteException;

  void createNetworkGuardChain() throws RemoteException;

  void delIpAcceptRule(String str, String str2, String str3) throws RemoteException;

  void delSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

  void delSourceRoute(String str, String str2, String str3) throws RemoteException;

  void deleteNetworkGuardChain() throws RemoteException;

  void deleteNetworkGuardWhiteListRule() throws RemoteException;

  void denyProtect(int i) throws RemoteException;

  void disableDAD(String str) throws RemoteException;

  void disableEpdg(String str, String str2) throws RemoteException;

  void disableIpv6(String str) throws RemoteException;

  void disableMptcp() throws RemoteException;

  void disableNat(String str, String str2) throws RemoteException;

  void disableNetworkGuard() throws RemoteException;

  void enableEpdg(String str, String str2, boolean z) throws RemoteException;

  void enableIpv6(String str) throws RemoteException;

  void enableKnoxVpnFlagForTether(boolean z) throws RemoteException;

  void enableMptcp(String str) throws RemoteException;

  void enableNat(String str, String str2) throws RemoteException;

  void enableNetworkGuard(boolean z) throws RemoteException;

  String[] getDnsForwarders() throws RemoteException;

  InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException;

  boolean getIpForwardingEnabled() throws RemoteException;

  NetworkStats getNetworkStatsTethering(int i) throws RemoteException;

  long getNetworkStatsVideoCall(String str, int i, int i2) throws RemoteException;

  boolean isBandwidthControlEnabled() throws RemoteException;

  boolean isFirewallEnabled() throws RemoteException;

  boolean isNetworkRestricted(int i) throws RemoteException;

  boolean isTetheringStarted() throws RemoteException;

  long[] l4StatsGet() throws RemoteException;

  String[] listInterfaces() throws RemoteException;

  String[] listTetheredInterfaces() throws RemoteException;

  int prioritizeApp(boolean z, int i) throws RemoteException;

  int prioritizeMnxbApp(boolean z, int i) throws RemoteException;

  void registerNetdTetherEventListener() throws RemoteException;

  void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver)
      throws RemoteException;

  void registerTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider, String str)
      throws RemoteException;

  void removeChain(String str, String str2) throws RemoteException;

  void removeInterfaceAlert(String str) throws RemoteException;

  void removeInterfaceFromLocalNetwork(String str) throws RemoteException;

  void removeInterfaceQuota(String str) throws RemoteException;

  void removeLegacyRoute(int i, String str, String str2, String str3, int i2)
      throws RemoteException;

  void removeMptcpLink(String str) throws RemoteException;

  void removeRoute(int i, RouteInfo routeInfo) throws RemoteException;

  int removeRoutesFromLocalNetwork(List<RouteInfo> list) throws RemoteException;

  void removeSocksRule(String str, String str2, String str3, int i, String str4)
      throws RemoteException;

  void removeSocksSkipRule(String str, String str2, String str3) throws RemoteException;

  void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4)
      throws RemoteException;

  void removeUidFromChain(String str, String str2, int i) throws RemoteException;

  void removeUidSocksRule(String str, String str2, String str3, int i, int i2, String str4)
      throws RemoteException;

  int replaceApeRule(String str, int i, int i2) throws RemoteException;

  int replaceMnxbRule(String str, int i, int i2) throws RemoteException;

  String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException;

  void runKnoxRulesCommand(int i, String[] strArr) throws RemoteException;

  void setAdvertiseWindowSize(int i) throws RemoteException;

  void setAllowHostAlone(String str) throws RemoteException;

  void setAllowListIPs(String str) throws RemoteException;

  void setAutoConf(String str, boolean z) throws RemoteException;

  void setBlockAllDNSPackets(boolean z) throws RemoteException;

  void setBlockAllPackets() throws RemoteException;

  void setBlockHostAlone(String str) throws RemoteException;

  void setBlockListIPs(String str) throws RemoteException;

  void setBlockPorts(String str, int i, String str2) throws RemoteException;

  boolean setDataSaverModeEnabled(boolean z) throws RemoteException;

  void setDestinationBasedMarkRule(boolean z, String str, String str2, int i, int i2)
      throws RemoteException;

  void setDnsForwardersForKnoxVpn(int i, String[] strArr) throws RemoteException;

  void setEpdgInterfaceDropRule(String str, String str2, boolean z) throws RemoteException;

  void setFirewallChainEnabled(int i, boolean z) throws RemoteException;

  void setFirewallEnabled(boolean z) throws RemoteException;

  void setFirewallInterfaceRule(String str, boolean z) throws RemoteException;

  void setFirewallRuleMobileData(int i, boolean z) throws RemoteException;

  void setFirewallRuleWifi(int i, boolean z) throws RemoteException;

  void setFirewallUidRule(int i, int i2, int i3) throws RemoteException;

  void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws RemoteException;

  void setGlobalAlert(long j) throws RemoteException;

  void setIPv6AddrGenMode(String str, int i) throws RemoteException;

  void setInterfaceAlert(String str, long j) throws RemoteException;

  void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration)
      throws RemoteException;

  void setInterfaceDown(String str) throws RemoteException;

  void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException;

  void setInterfaceQuota(String str, long j) throws RemoteException;

  void setInterfaceUp(String str) throws RemoteException;

  void setIpForwardingEnabled(boolean z) throws RemoteException;

  void setKnoxGuardExemptRule(boolean z, String str, int i) throws RemoteException;

  void setKnoxVpn(int i, boolean z) throws RemoteException;

  void setMptcpMtuValue(String str, int i) throws RemoteException;

  void setNetworkGuardProtocolAcceptRule(int i) throws RemoteException;

  void setNetworkGuardUidRangeAcceptRule(int i, int i2) throws RemoteException;

  void setNetworkGuardUidRule(int i, boolean z, boolean z2) throws RemoteException;

  void setNetworkInfo(int i, boolean z, int i2) throws RemoteException;

  void setPrivateIpRoute(boolean z, String str, int i) throws RemoteException;

  void setQboxUid(int i, boolean z) throws RemoteException;

  void setTcpBufferSize(String str, String str2) throws RemoteException;

  void setUIDRoute(boolean z, String str, int i, String str2, String str3) throws RemoteException;

  void setUidCleartextNetworkPolicy(int i, int i2) throws RemoteException;

  void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws RemoteException;

  void setUidOnMeteredNetworkDenylist(int i, boolean z) throws RemoteException;

  void setUrlFirewallRuleMobileData(int i, String str, boolean z) throws RemoteException;

  void setUrlFirewallRuleWifi(int i, String str, boolean z) throws RemoteException;

  void shutdown() throws RemoteException;

  void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException;

  void startInterfaceForwarding(String str, String str2) throws RemoteException;

  void startNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException;

  void startQbox(String str) throws RemoteException;

  void startTethering(String[] strArr) throws RemoteException;

  void startTetheringWithConfiguration(boolean z, String[] strArr) throws RemoteException;

  void stopInterfaceForwarding(String str, String str2) throws RemoteException;

  void stopNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException;

  void stopQbox() throws RemoteException;

  void stopTethering() throws RemoteException;

  void tetherInterface(String str) throws RemoteException;

  void tetherLimitReached(ITetheringStatsProvider iTetheringStatsProvider) throws RemoteException;

  void unregisterNetdTetherEventListener() throws RemoteException;

  void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver)
      throws RemoteException;

  void unregisterTetheringStatsProvider(ITetheringStatsProvider iTetheringStatsProvider)
      throws RemoteException;

  void untetherInterface(String str) throws RemoteException;

  void updateDefaultGatewayForEpdg(Network network) throws RemoteException;

  void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException;

  void updateInputFilterExemptRules(int i, int i2) throws RemoteException;

  void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException;

  void updateSourceRule(boolean z, String str, String str2) throws RemoteException;

  public static class Default implements INetworkManagementService {
    @Override // android.p009os.INetworkManagementService
    public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void unregisterObserver(INetworkManagementEventObserver obs) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public String[] listInterfaces() throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public void setInterfaceConfig(String iface, InterfaceConfiguration cfg)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void clearInterfaceAddresses(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setInterfaceDown(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setInterfaceUp(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void disableIpv6(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void enableIpv6(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addRoute(int netId, RouteInfo route) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeRoute(int netId, RouteInfo route) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void shutdown() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public boolean getIpForwardingEnabled() throws RemoteException {
      return false;
    }

    @Override // android.p009os.INetworkManagementService
    public void setIpForwardingEnabled(boolean enabled) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void startTethering(String[] dhcpRanges) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void startTetheringWithConfiguration(boolean usingLegacyDnsProxy, String[] dhcpRanges)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void stopTethering() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public boolean isTetheringStarted() throws RemoteException {
      return false;
    }

    @Override // android.p009os.INetworkManagementService
    public void tetherInterface(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void untetherInterface(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public String[] listTetheredInterfaces() throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public String[] getDnsForwarders() throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public void startInterfaceForwarding(String fromIface, String toIface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void stopInterfaceForwarding(String fromIface, String toIface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void enableNat(String internalInterface, String externalInterface)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void disableNat(String internalInterface, String externalInterface)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void registerTetheringStatsProvider(ITetheringStatsProvider provider, String name)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void unregisterTetheringStatsProvider(ITetheringStatsProvider provider)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void tetherLimitReached(ITetheringStatsProvider provider) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public NetworkStats getNetworkStatsTethering(int how) throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeInterfaceQuota(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeInterfaceAlert(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setGlobalAlert(long alertBytes) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setUidOnMeteredNetworkDenylist(int uid, boolean enable) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setUidOnMeteredNetworkAllowlist(int uid, boolean enable) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
      return false;
    }

    @Override // android.p009os.INetworkManagementService
    public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public boolean isBandwidthControlEnabled() throws RemoteException {
      return false;
    }

    @Override // android.p009os.INetworkManagementService
    public void setFirewallEnabled(boolean enabled) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public boolean isFirewallEnabled() throws RemoteException {
      return false;
    }

    @Override // android.p009os.INetworkManagementService
    public void setFirewallInterfaceRule(String iface, boolean allow) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setFirewallUidRules(int chain, int[] uids, int[] rules) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void closeSocketsForFreecess(int chain, String chainName) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void closeSocketsForUids(int[] uids) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void closeSocketsForUid(int uid) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void allowProtect(int uid) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void denyProtect(int uid) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addInterfaceToLocalNetwork(String iface, List<RouteInfo> routes)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeInterfaceFromLocalNetwork(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public int removeRoutesFromLocalNetwork(List<RouteInfo> routes) throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public boolean isNetworkRestricted(int uid) throws RemoteException {
      return false;
    }

    @Override // android.p009os.INetworkManagementService
    public void spegRestrictNetworkConnection(int uid, boolean restrict) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setDnsForwardersForKnoxVpn(int netId, String[] dns) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setNetworkInfo(int netId, boolean chainedNetwork, int vpnClientUid)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean enable, int uid)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void updateInputFilterExemptRules(int uid, int update) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void updateInputFilterUserWideRules(int[] userIds, int ifaceIndex, int update)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void updateInputFilterAppWideRules(int[] uids, int ifaceIndex, int update)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void clearEbpfMap(int mapId) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public String runKnoxFirewallRulesCommand(int target, String cmd) throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public void runKnoxRulesCommand(int cmd, String[] params) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void enableKnoxVpnFlagForTether(boolean enabled) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void registerNetdTetherEventListener() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void unregisterNetdTetherEventListener() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setKnoxVpn(int netId, boolean isKnoxVpn) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addPortFwdRules(
        String externalIface, String interfanIface, String externalIp, String internalIp, int port)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void createNetworkGuardChain() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void deleteNetworkGuardChain() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void enableNetworkGuard(boolean isBlack) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void disableNetworkGuard() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void deleteNetworkGuardWhiteListRule() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setNetworkGuardUidRangeAcceptRule(int uidStart, int uidEnd)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setNetworkGuardUidRule(int uid, boolean mode, boolean isDrop)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setNetworkGuardProtocolAcceptRule(int protocol) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void startNetworkStatsOnPorts(String iface, int inport, int outport)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void stopNetworkStatsOnPorts(String iface, int inport, int outport)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public long getNetworkStatsVideoCall(String iface, int sport, int dport)
        throws RemoteException {
      return 0L;
    }

    @Override // android.p009os.INetworkManagementService
    public int prioritizeApp(boolean add, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public int addApeRule(boolean add, String infName, int bandwidthMbps) throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public int replaceApeRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
        throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public void startQbox(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void stopQbox() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setQboxUid(int uid, boolean allow) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void enableEpdg(String mobileInterface, String tunnelingInterface, boolean deleteSkip)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void disableEpdg(String mobileInterface, String tunnelingInterface)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setEpdgInterfaceDropRule(String iface, String src, boolean add)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void disableDAD(String ifName) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setBlockAllDNSPackets(boolean enabled) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setBlockListIPs(String addr) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setAllowListIPs(String addr) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setBlockHostAlone(String addr) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setAllowHostAlone(String addr) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void cleanAllBlock() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setBlockAllPackets() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setBlockPorts(String protocol, int directionBitMask, String ports)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void cleanBlockPorts() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setKnoxGuardExemptRule(boolean add, String ifaceName, int uid)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setUrlFirewallRuleMobileData(int uid, String url, boolean allow)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setUrlFirewallRuleWifi(int uid, String url, boolean allow) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void buildFirewall() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setFirewallRuleWifi(int uid, boolean allow) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setFirewallRuleMobileData(int uid, boolean allow) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addMptcpLink(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeMptcpLink(String iface) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addChain(String chain, String ip_type) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeChain(String chain, String ip_type) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addSocksRule(String iface, String chain, String proto, int port, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeSocksRule(String iface, String chain, String proto, int port, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addUidSocksRule(
        String iface, String chain, String proto, int port, int uid, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeUidSocksRule(
        String iface, String chain, String proto, int port, int uid, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addSocksSkipRule(String chain, String addr, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeSocksSkipRule(String chain, String addr, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addSocksSkipRuleProto(
        String chain, String addr, String proto, int port, String ip_type) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeSocksSkipRuleProto(
        String chain, String addr, String proto, int port, String ip_type) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addUidToChain(String chain, String proto, int uid) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeUidFromChain(String chain, String proto, int uid) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addIpAcceptRule(String chain, String dest, String proto) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void delIpAcceptRule(String chain, String dest, String proto) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setTcpBufferSize(String rmem, String wmem) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setMptcpMtuValue(String iface, int mtu) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void enableMptcp(String value) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void disableMptcp() throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addSourceRoute(String iface, String addr, String gateway) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void delSourceRoute(String iface, String addr, String gateway) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void addSourcePortAcceptRule(String chain, String proto, int port)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void delSourcePortAcceptRule(String chain, String proto, int port)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void updateSourceRule(boolean add, String ipAddr, String ifaceName)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setPrivateIpRoute(boolean add, String ifaceName, int mark) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setDestinationBasedMarkRule(
        boolean add, String addr, String outInterface, int mark, int uid) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setUIDRoute(boolean add, String iface, int uid, String pref, String ip_type)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void setAutoConf(String iface, boolean enable) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public int prioritizeMnxbApp(boolean add, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public int addMnxbRule(boolean add, String infName, int bandwidthMbps) throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public int replaceMnxbRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
        throws RemoteException {
      return 0;
    }

    @Override // android.p009os.INetworkManagementService
    public void setAdvertiseWindowSize(int newAdvertiseWindow) throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public long[] l4StatsGet() throws RemoteException {
      return null;
    }

    @Override // android.p009os.INetworkManagementService
    public void addLegacyRoute(
        int netId, String ifName, String destination, String nextHop, int uid)
        throws RemoteException {}

    @Override // android.p009os.INetworkManagementService
    public void removeLegacyRoute(
        int netId, String ifName, String destination, String nextHop, int uid)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INetworkManagementService {
    public static final String DESCRIPTOR = "android.os.INetworkManagementService";
    static final int TRANSACTION_addApeRule = 86;
    static final int TRANSACTION_addChain = 113;
    static final int TRANSACTION_addInterfaceToLocalNetwork = 55;
    static final int TRANSACTION_addIpAcceptRule = 125;
    static final int TRANSACTION_addLegacyRoute = 145;
    static final int TRANSACTION_addMnxbRule = 141;
    static final int TRANSACTION_addMptcpLink = 111;
    static final int TRANSACTION_addOrRemoveSystemAppFromDataSaverWhitelist = 62;
    static final int TRANSACTION_addPortFwdRules = 73;
    static final int TRANSACTION_addRoute = 13;
    static final int TRANSACTION_addSocksRule = 115;
    static final int TRANSACTION_addSocksSkipRule = 119;
    static final int TRANSACTION_addSocksSkipRuleProto = 121;
    static final int TRANSACTION_addSourcePortAcceptRule = 133;
    static final int TRANSACTION_addSourceRoute = 131;
    static final int TRANSACTION_addUidSocksRule = 117;
    static final int TRANSACTION_addUidToChain = 123;
    static final int TRANSACTION_allowProtect = 53;
    static final int TRANSACTION_buildFirewall = 108;
    static final int TRANSACTION_cleanAllBlock = 101;
    static final int TRANSACTION_cleanBlockPorts = 104;
    static final int TRANSACTION_clearEbpfMap = 66;
    static final int TRANSACTION_clearInterfaceAddresses = 6;
    static final int TRANSACTION_closeSocketsForFreecess = 50;
    static final int TRANSACTION_closeSocketsForUid = 52;
    static final int TRANSACTION_closeSocketsForUids = 51;
    static final int TRANSACTION_createNetworkGuardChain = 74;
    static final int TRANSACTION_delIpAcceptRule = 126;
    static final int TRANSACTION_delSourcePortAcceptRule = 134;
    static final int TRANSACTION_delSourceRoute = 132;
    static final int TRANSACTION_deleteNetworkGuardChain = 75;
    static final int TRANSACTION_deleteNetworkGuardWhiteListRule = 78;
    static final int TRANSACTION_denyProtect = 54;
    static final int TRANSACTION_disableDAD = 95;
    static final int TRANSACTION_disableEpdg = 92;
    static final int TRANSACTION_disableIpv6 = 10;
    static final int TRANSACTION_disableMptcp = 130;
    static final int TRANSACTION_disableNat = 29;
    static final int TRANSACTION_disableNetworkGuard = 77;
    static final int TRANSACTION_enableEpdg = 91;
    static final int TRANSACTION_enableIpv6 = 11;
    static final int TRANSACTION_enableKnoxVpnFlagForTether = 69;
    static final int TRANSACTION_enableMptcp = 129;
    static final int TRANSACTION_enableNat = 28;
    static final int TRANSACTION_enableNetworkGuard = 76;
    static final int TRANSACTION_getDnsForwarders = 25;
    static final int TRANSACTION_getInterfaceConfig = 4;
    static final int TRANSACTION_getIpForwardingEnabled = 16;
    static final int TRANSACTION_getNetworkStatsTethering = 33;
    static final int TRANSACTION_getNetworkStatsVideoCall = 84;
    static final int TRANSACTION_isBandwidthControlEnabled = 43;
    static final int TRANSACTION_isFirewallEnabled = 45;
    static final int TRANSACTION_isNetworkRestricted = 58;
    static final int TRANSACTION_isTetheringStarted = 21;
    static final int TRANSACTION_l4StatsGet = 144;
    static final int TRANSACTION_listInterfaces = 3;
    static final int TRANSACTION_listTetheredInterfaces = 24;
    static final int TRANSACTION_prioritizeApp = 85;
    static final int TRANSACTION_prioritizeMnxbApp = 140;
    static final int TRANSACTION_registerNetdTetherEventListener = 70;
    static final int TRANSACTION_registerObserver = 1;
    static final int TRANSACTION_registerTetheringStatsProvider = 30;
    static final int TRANSACTION_removeChain = 114;
    static final int TRANSACTION_removeInterfaceAlert = 37;
    static final int TRANSACTION_removeInterfaceFromLocalNetwork = 56;
    static final int TRANSACTION_removeInterfaceQuota = 35;
    static final int TRANSACTION_removeLegacyRoute = 146;
    static final int TRANSACTION_removeMptcpLink = 112;
    static final int TRANSACTION_removeRoute = 14;
    static final int TRANSACTION_removeRoutesFromLocalNetwork = 57;
    static final int TRANSACTION_removeSocksRule = 116;
    static final int TRANSACTION_removeSocksSkipRule = 120;
    static final int TRANSACTION_removeSocksSkipRuleProto = 122;
    static final int TRANSACTION_removeUidFromChain = 124;
    static final int TRANSACTION_removeUidSocksRule = 118;
    static final int TRANSACTION_replaceApeRule = 87;
    static final int TRANSACTION_replaceMnxbRule = 142;
    static final int TRANSACTION_runKnoxFirewallRulesCommand = 67;
    static final int TRANSACTION_runKnoxRulesCommand = 68;
    static final int TRANSACTION_setAdvertiseWindowSize = 143;
    static final int TRANSACTION_setAllowHostAlone = 100;
    static final int TRANSACTION_setAllowListIPs = 98;
    static final int TRANSACTION_setAutoConf = 139;
    static final int TRANSACTION_setBlockAllDNSPackets = 96;
    static final int TRANSACTION_setBlockAllPackets = 102;
    static final int TRANSACTION_setBlockHostAlone = 99;
    static final int TRANSACTION_setBlockListIPs = 97;
    static final int TRANSACTION_setBlockPorts = 103;
    static final int TRANSACTION_setDataSaverModeEnabled = 41;
    static final int TRANSACTION_setDestinationBasedMarkRule = 137;
    static final int TRANSACTION_setDnsForwardersForKnoxVpn = 60;
    static final int TRANSACTION_setEpdgInterfaceDropRule = 93;
    static final int TRANSACTION_setFirewallChainEnabled = 49;
    static final int TRANSACTION_setFirewallEnabled = 44;
    static final int TRANSACTION_setFirewallInterfaceRule = 46;
    static final int TRANSACTION_setFirewallRuleMobileData = 110;
    static final int TRANSACTION_setFirewallRuleWifi = 109;
    static final int TRANSACTION_setFirewallUidRule = 47;
    static final int TRANSACTION_setFirewallUidRules = 48;
    static final int TRANSACTION_setGlobalAlert = 38;
    static final int TRANSACTION_setIPv6AddrGenMode = 12;
    static final int TRANSACTION_setInterfaceAlert = 36;
    static final int TRANSACTION_setInterfaceConfig = 5;
    static final int TRANSACTION_setInterfaceDown = 7;
    static final int TRANSACTION_setInterfaceIpv6PrivacyExtensions = 9;
    static final int TRANSACTION_setInterfaceQuota = 34;
    static final int TRANSACTION_setInterfaceUp = 8;
    static final int TRANSACTION_setIpForwardingEnabled = 17;
    static final int TRANSACTION_setKnoxGuardExemptRule = 105;
    static final int TRANSACTION_setKnoxVpn = 72;
    static final int TRANSACTION_setMptcpMtuValue = 128;
    static final int TRANSACTION_setNetworkGuardProtocolAcceptRule = 81;
    static final int TRANSACTION_setNetworkGuardUidRangeAcceptRule = 79;
    static final int TRANSACTION_setNetworkGuardUidRule = 80;
    static final int TRANSACTION_setNetworkInfo = 61;
    static final int TRANSACTION_setPrivateIpRoute = 136;
    static final int TRANSACTION_setQboxUid = 90;
    static final int TRANSACTION_setTcpBufferSize = 127;
    static final int TRANSACTION_setUIDRoute = 138;
    static final int TRANSACTION_setUidCleartextNetworkPolicy = 42;
    static final int TRANSACTION_setUidOnMeteredNetworkAllowlist = 40;
    static final int TRANSACTION_setUidOnMeteredNetworkDenylist = 39;
    static final int TRANSACTION_setUrlFirewallRuleMobileData = 106;
    static final int TRANSACTION_setUrlFirewallRuleWifi = 107;
    static final int TRANSACTION_shutdown = 15;
    static final int TRANSACTION_spegRestrictNetworkConnection = 59;
    static final int TRANSACTION_startInterfaceForwarding = 26;
    static final int TRANSACTION_startNetworkStatsOnPorts = 82;
    static final int TRANSACTION_startQbox = 88;
    static final int TRANSACTION_startTethering = 18;
    static final int TRANSACTION_startTetheringWithConfiguration = 19;
    static final int TRANSACTION_stopInterfaceForwarding = 27;
    static final int TRANSACTION_stopNetworkStatsOnPorts = 83;
    static final int TRANSACTION_stopQbox = 89;
    static final int TRANSACTION_stopTethering = 20;
    static final int TRANSACTION_tetherInterface = 22;
    static final int TRANSACTION_tetherLimitReached = 32;
    static final int TRANSACTION_unregisterNetdTetherEventListener = 71;
    static final int TRANSACTION_unregisterObserver = 2;
    static final int TRANSACTION_unregisterTetheringStatsProvider = 31;
    static final int TRANSACTION_untetherInterface = 23;
    static final int TRANSACTION_updateDefaultGatewayForEpdg = 94;
    static final int TRANSACTION_updateInputFilterAppWideRules = 65;
    static final int TRANSACTION_updateInputFilterExemptRules = 63;
    static final int TRANSACTION_updateInputFilterUserWideRules = 64;
    static final int TRANSACTION_updateSourceRule = 135;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static INetworkManagementService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INetworkManagementService)) {
        return (INetworkManagementService) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "registerObserver";
        case 2:
          return "unregisterObserver";
        case 3:
          return "listInterfaces";
        case 4:
          return "getInterfaceConfig";
        case 5:
          return "setInterfaceConfig";
        case 6:
          return "clearInterfaceAddresses";
        case 7:
          return "setInterfaceDown";
        case 8:
          return "setInterfaceUp";
        case 9:
          return "setInterfaceIpv6PrivacyExtensions";
        case 10:
          return "disableIpv6";
        case 11:
          return "enableIpv6";
        case 12:
          return "setIPv6AddrGenMode";
        case 13:
          return "addRoute";
        case 14:
          return "removeRoute";
        case 15:
          return UsbManager.USB_FUNCTION_SHUTDOWN;
        case 16:
          return "getIpForwardingEnabled";
        case 17:
          return "setIpForwardingEnabled";
        case 18:
          return "startTethering";
        case 19:
          return "startTetheringWithConfiguration";
        case 20:
          return "stopTethering";
        case 21:
          return "isTetheringStarted";
        case 22:
          return "tetherInterface";
        case 23:
          return "untetherInterface";
        case 24:
          return "listTetheredInterfaces";
        case 25:
          return "getDnsForwarders";
        case 26:
          return "startInterfaceForwarding";
        case 27:
          return "stopInterfaceForwarding";
        case 28:
          return "enableNat";
        case 29:
          return "disableNat";
        case 30:
          return "registerTetheringStatsProvider";
        case 31:
          return "unregisterTetheringStatsProvider";
        case 32:
          return "tetherLimitReached";
        case 33:
          return "getNetworkStatsTethering";
        case 34:
          return "setInterfaceQuota";
        case 35:
          return "removeInterfaceQuota";
        case 36:
          return "setInterfaceAlert";
        case 37:
          return "removeInterfaceAlert";
        case 38:
          return "setGlobalAlert";
        case 39:
          return "setUidOnMeteredNetworkDenylist";
        case 40:
          return "setUidOnMeteredNetworkAllowlist";
        case 41:
          return "setDataSaverModeEnabled";
        case 42:
          return "setUidCleartextNetworkPolicy";
        case 43:
          return "isBandwidthControlEnabled";
        case 44:
          return "setFirewallEnabled";
        case 45:
          return "isFirewallEnabled";
        case 46:
          return "setFirewallInterfaceRule";
        case 47:
          return "setFirewallUidRule";
        case 48:
          return "setFirewallUidRules";
        case 49:
          return "setFirewallChainEnabled";
        case 50:
          return "closeSocketsForFreecess";
        case 51:
          return "closeSocketsForUids";
        case 52:
          return "closeSocketsForUid";
        case 53:
          return "allowProtect";
        case 54:
          return "denyProtect";
        case 55:
          return "addInterfaceToLocalNetwork";
        case 56:
          return "removeInterfaceFromLocalNetwork";
        case 57:
          return "removeRoutesFromLocalNetwork";
        case 58:
          return "isNetworkRestricted";
        case 59:
          return "spegRestrictNetworkConnection";
        case 60:
          return "setDnsForwardersForKnoxVpn";
        case 61:
          return "setNetworkInfo";
        case 62:
          return "addOrRemoveSystemAppFromDataSaverWhitelist";
        case 63:
          return "updateInputFilterExemptRules";
        case 64:
          return "updateInputFilterUserWideRules";
        case 65:
          return "updateInputFilterAppWideRules";
        case 66:
          return "clearEbpfMap";
        case 67:
          return "runKnoxFirewallRulesCommand";
        case 68:
          return "runKnoxRulesCommand";
        case 69:
          return "enableKnoxVpnFlagForTether";
        case 70:
          return "registerNetdTetherEventListener";
        case 71:
          return "unregisterNetdTetherEventListener";
        case 72:
          return "setKnoxVpn";
        case 73:
          return "addPortFwdRules";
        case 74:
          return "createNetworkGuardChain";
        case 75:
          return "deleteNetworkGuardChain";
        case 76:
          return "enableNetworkGuard";
        case 77:
          return "disableNetworkGuard";
        case 78:
          return "deleteNetworkGuardWhiteListRule";
        case 79:
          return "setNetworkGuardUidRangeAcceptRule";
        case 80:
          return "setNetworkGuardUidRule";
        case 81:
          return "setNetworkGuardProtocolAcceptRule";
        case 82:
          return "startNetworkStatsOnPorts";
        case 83:
          return "stopNetworkStatsOnPorts";
        case 84:
          return "getNetworkStatsVideoCall";
        case 85:
          return "prioritizeApp";
        case 86:
          return "addApeRule";
        case 87:
          return "replaceApeRule";
        case 88:
          return "startQbox";
        case 89:
          return "stopQbox";
        case 90:
          return "setQboxUid";
        case 91:
          return "enableEpdg";
        case 92:
          return "disableEpdg";
        case 93:
          return "setEpdgInterfaceDropRule";
        case 94:
          return "updateDefaultGatewayForEpdg";
        case 95:
          return "disableDAD";
        case 96:
          return "setBlockAllDNSPackets";
        case 97:
          return "setBlockListIPs";
        case 98:
          return "setAllowListIPs";
        case 99:
          return "setBlockHostAlone";
        case 100:
          return "setAllowHostAlone";
        case 101:
          return "cleanAllBlock";
        case 102:
          return "setBlockAllPackets";
        case 103:
          return "setBlockPorts";
        case 104:
          return "cleanBlockPorts";
        case 105:
          return "setKnoxGuardExemptRule";
        case 106:
          return "setUrlFirewallRuleMobileData";
        case 107:
          return "setUrlFirewallRuleWifi";
        case 108:
          return "buildFirewall";
        case 109:
          return "setFirewallRuleWifi";
        case 110:
          return "setFirewallRuleMobileData";
        case 111:
          return "addMptcpLink";
        case 112:
          return "removeMptcpLink";
        case 113:
          return "addChain";
        case 114:
          return "removeChain";
        case 115:
          return "addSocksRule";
        case 116:
          return "removeSocksRule";
        case 117:
          return "addUidSocksRule";
        case 118:
          return "removeUidSocksRule";
        case 119:
          return "addSocksSkipRule";
        case 120:
          return "removeSocksSkipRule";
        case 121:
          return "addSocksSkipRuleProto";
        case 122:
          return "removeSocksSkipRuleProto";
        case 123:
          return "addUidToChain";
        case 124:
          return "removeUidFromChain";
        case 125:
          return "addIpAcceptRule";
        case 126:
          return "delIpAcceptRule";
        case 127:
          return "setTcpBufferSize";
        case 128:
          return "setMptcpMtuValue";
        case 129:
          return "enableMptcp";
        case 130:
          return "disableMptcp";
        case 131:
          return "addSourceRoute";
        case 132:
          return "delSourceRoute";
        case 133:
          return "addSourcePortAcceptRule";
        case 134:
          return "delSourcePortAcceptRule";
        case 135:
          return "updateSourceRule";
        case 136:
          return "setPrivateIpRoute";
        case 137:
          return "setDestinationBasedMarkRule";
        case 138:
          return "setUIDRoute";
        case 139:
          return "setAutoConf";
        case 140:
          return "prioritizeMnxbApp";
        case 141:
          return "addMnxbRule";
        case 142:
          return "replaceMnxbRule";
        case 143:
          return "setAdvertiseWindowSize";
        case 144:
          return "l4StatsGet";
        case 145:
          return "addLegacyRoute";
        case 146:
          return "removeLegacyRoute";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              INetworkManagementEventObserver _arg0 =
                  INetworkManagementEventObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerObserver(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              INetworkManagementEventObserver _arg02 =
                  INetworkManagementEventObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterObserver(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              String[] _result = listInterfaces();
              reply.writeNoException();
              reply.writeStringArray(_result);
              return true;
            case 4:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              InterfaceConfiguration _result2 = getInterfaceConfig(_arg03);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 5:
              String _arg04 = data.readString();
              InterfaceConfiguration _arg1 =
                  (InterfaceConfiguration) data.readTypedObject(InterfaceConfiguration.CREATOR);
              data.enforceNoDataAvail();
              setInterfaceConfig(_arg04, _arg1);
              reply.writeNoException();
              return true;
            case 6:
              String _arg05 = data.readString();
              data.enforceNoDataAvail();
              clearInterfaceAddresses(_arg05);
              reply.writeNoException();
              return true;
            case 7:
              String _arg06 = data.readString();
              data.enforceNoDataAvail();
              setInterfaceDown(_arg06);
              reply.writeNoException();
              return true;
            case 8:
              String _arg07 = data.readString();
              data.enforceNoDataAvail();
              setInterfaceUp(_arg07);
              reply.writeNoException();
              return true;
            case 9:
              String _arg08 = data.readString();
              boolean _arg12 = data.readBoolean();
              data.enforceNoDataAvail();
              setInterfaceIpv6PrivacyExtensions(_arg08, _arg12);
              reply.writeNoException();
              return true;
            case 10:
              String _arg09 = data.readString();
              data.enforceNoDataAvail();
              disableIpv6(_arg09);
              reply.writeNoException();
              return true;
            case 11:
              String _arg010 = data.readString();
              data.enforceNoDataAvail();
              enableIpv6(_arg010);
              reply.writeNoException();
              return true;
            case 12:
              String _arg011 = data.readString();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              setIPv6AddrGenMode(_arg011, _arg13);
              reply.writeNoException();
              return true;
            case 13:
              int _arg012 = data.readInt();
              RouteInfo _arg14 = (RouteInfo) data.readTypedObject(RouteInfo.CREATOR);
              data.enforceNoDataAvail();
              addRoute(_arg012, _arg14);
              reply.writeNoException();
              return true;
            case 14:
              int _arg013 = data.readInt();
              RouteInfo _arg15 = (RouteInfo) data.readTypedObject(RouteInfo.CREATOR);
              data.enforceNoDataAvail();
              removeRoute(_arg013, _arg15);
              reply.writeNoException();
              return true;
            case 15:
              shutdown();
              reply.writeNoException();
              return true;
            case 16:
              boolean _result3 = getIpForwardingEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 17:
              boolean _arg014 = data.readBoolean();
              data.enforceNoDataAvail();
              setIpForwardingEnabled(_arg014);
              reply.writeNoException();
              return true;
            case 18:
              String[] _arg015 = data.createStringArray();
              data.enforceNoDataAvail();
              startTethering(_arg015);
              reply.writeNoException();
              return true;
            case 19:
              boolean _arg016 = data.readBoolean();
              String[] _arg16 = data.createStringArray();
              data.enforceNoDataAvail();
              startTetheringWithConfiguration(_arg016, _arg16);
              reply.writeNoException();
              return true;
            case 20:
              stopTethering();
              reply.writeNoException();
              return true;
            case 21:
              boolean _result4 = isTetheringStarted();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 22:
              String _arg017 = data.readString();
              data.enforceNoDataAvail();
              tetherInterface(_arg017);
              reply.writeNoException();
              return true;
            case 23:
              String _arg018 = data.readString();
              data.enforceNoDataAvail();
              untetherInterface(_arg018);
              reply.writeNoException();
              return true;
            case 24:
              String[] _result5 = listTetheredInterfaces();
              reply.writeNoException();
              reply.writeStringArray(_result5);
              return true;
            case 25:
              String[] _result6 = getDnsForwarders();
              reply.writeNoException();
              reply.writeStringArray(_result6);
              return true;
            case 26:
              String _arg019 = data.readString();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              startInterfaceForwarding(_arg019, _arg17);
              reply.writeNoException();
              return true;
            case 27:
              String _arg020 = data.readString();
              String _arg18 = data.readString();
              data.enforceNoDataAvail();
              stopInterfaceForwarding(_arg020, _arg18);
              reply.writeNoException();
              return true;
            case 28:
              String _arg021 = data.readString();
              String _arg19 = data.readString();
              data.enforceNoDataAvail();
              enableNat(_arg021, _arg19);
              reply.writeNoException();
              return true;
            case 29:
              String _arg022 = data.readString();
              String _arg110 = data.readString();
              data.enforceNoDataAvail();
              disableNat(_arg022, _arg110);
              reply.writeNoException();
              return true;
            case 30:
              ITetheringStatsProvider _arg023 =
                  ITetheringStatsProvider.Stub.asInterface(data.readStrongBinder());
              String _arg111 = data.readString();
              data.enforceNoDataAvail();
              registerTetheringStatsProvider(_arg023, _arg111);
              reply.writeNoException();
              return true;
            case 31:
              ITetheringStatsProvider _arg024 =
                  ITetheringStatsProvider.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterTetheringStatsProvider(_arg024);
              reply.writeNoException();
              return true;
            case 32:
              ITetheringStatsProvider _arg025 =
                  ITetheringStatsProvider.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              tetherLimitReached(_arg025);
              reply.writeNoException();
              return true;
            case 33:
              int _arg026 = data.readInt();
              data.enforceNoDataAvail();
              NetworkStats _result7 = getNetworkStatsTethering(_arg026);
              reply.writeNoException();
              reply.writeTypedObject(_result7, 1);
              return true;
            case 34:
              String _arg027 = data.readString();
              long _arg112 = data.readLong();
              data.enforceNoDataAvail();
              setInterfaceQuota(_arg027, _arg112);
              reply.writeNoException();
              return true;
            case 35:
              String _arg028 = data.readString();
              data.enforceNoDataAvail();
              removeInterfaceQuota(_arg028);
              reply.writeNoException();
              return true;
            case 36:
              String _arg029 = data.readString();
              long _arg113 = data.readLong();
              data.enforceNoDataAvail();
              setInterfaceAlert(_arg029, _arg113);
              reply.writeNoException();
              return true;
            case 37:
              String _arg030 = data.readString();
              data.enforceNoDataAvail();
              removeInterfaceAlert(_arg030);
              reply.writeNoException();
              return true;
            case 38:
              long _arg031 = data.readLong();
              data.enforceNoDataAvail();
              setGlobalAlert(_arg031);
              reply.writeNoException();
              return true;
            case 39:
              int _arg032 = data.readInt();
              boolean _arg114 = data.readBoolean();
              data.enforceNoDataAvail();
              setUidOnMeteredNetworkDenylist(_arg032, _arg114);
              reply.writeNoException();
              return true;
            case 40:
              int _arg033 = data.readInt();
              boolean _arg115 = data.readBoolean();
              data.enforceNoDataAvail();
              setUidOnMeteredNetworkAllowlist(_arg033, _arg115);
              reply.writeNoException();
              return true;
            case 41:
              boolean _arg034 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result8 = setDataSaverModeEnabled(_arg034);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 42:
              int _arg035 = data.readInt();
              int _arg116 = data.readInt();
              data.enforceNoDataAvail();
              setUidCleartextNetworkPolicy(_arg035, _arg116);
              reply.writeNoException();
              return true;
            case 43:
              boolean _result9 = isBandwidthControlEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 44:
              boolean _arg036 = data.readBoolean();
              data.enforceNoDataAvail();
              setFirewallEnabled(_arg036);
              reply.writeNoException();
              return true;
            case 45:
              boolean _result10 = isFirewallEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 46:
              String _arg037 = data.readString();
              boolean _arg117 = data.readBoolean();
              data.enforceNoDataAvail();
              setFirewallInterfaceRule(_arg037, _arg117);
              reply.writeNoException();
              return true;
            case 47:
              int _arg038 = data.readInt();
              int _arg118 = data.readInt();
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              setFirewallUidRule(_arg038, _arg118, _arg2);
              reply.writeNoException();
              return true;
            case 48:
              int _arg039 = data.readInt();
              int[] _arg119 = data.createIntArray();
              int[] _arg22 = data.createIntArray();
              data.enforceNoDataAvail();
              setFirewallUidRules(_arg039, _arg119, _arg22);
              reply.writeNoException();
              return true;
            case 49:
              int _arg040 = data.readInt();
              boolean _arg120 = data.readBoolean();
              data.enforceNoDataAvail();
              setFirewallChainEnabled(_arg040, _arg120);
              reply.writeNoException();
              return true;
            case 50:
              int _arg041 = data.readInt();
              String _arg121 = data.readString();
              data.enforceNoDataAvail();
              closeSocketsForFreecess(_arg041, _arg121);
              reply.writeNoException();
              return true;
            case 51:
              int[] _arg042 = data.createIntArray();
              data.enforceNoDataAvail();
              closeSocketsForUids(_arg042);
              reply.writeNoException();
              return true;
            case 52:
              int _arg043 = data.readInt();
              data.enforceNoDataAvail();
              closeSocketsForUid(_arg043);
              reply.writeNoException();
              return true;
            case 53:
              int _arg044 = data.readInt();
              data.enforceNoDataAvail();
              allowProtect(_arg044);
              reply.writeNoException();
              return true;
            case 54:
              int _arg045 = data.readInt();
              data.enforceNoDataAvail();
              denyProtect(_arg045);
              reply.writeNoException();
              return true;
            case 55:
              String _arg046 = data.readString();
              List<RouteInfo> _arg122 = data.createTypedArrayList(RouteInfo.CREATOR);
              data.enforceNoDataAvail();
              addInterfaceToLocalNetwork(_arg046, _arg122);
              reply.writeNoException();
              return true;
            case 56:
              String _arg047 = data.readString();
              data.enforceNoDataAvail();
              removeInterfaceFromLocalNetwork(_arg047);
              reply.writeNoException();
              return true;
            case 57:
              List<RouteInfo> _arg048 = data.createTypedArrayList(RouteInfo.CREATOR);
              data.enforceNoDataAvail();
              int _result11 = removeRoutesFromLocalNetwork(_arg048);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 58:
              int _arg049 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result12 = isNetworkRestricted(_arg049);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 59:
              int _arg050 = data.readInt();
              boolean _arg123 = data.readBoolean();
              data.enforceNoDataAvail();
              spegRestrictNetworkConnection(_arg050, _arg123);
              reply.writeNoException();
              return true;
            case 60:
              int _arg051 = data.readInt();
              String[] _arg124 = data.createStringArray();
              data.enforceNoDataAvail();
              setDnsForwardersForKnoxVpn(_arg051, _arg124);
              reply.writeNoException();
              return true;
            case 61:
              int _arg052 = data.readInt();
              boolean _arg125 = data.readBoolean();
              int _arg23 = data.readInt();
              data.enforceNoDataAvail();
              setNetworkInfo(_arg052, _arg125, _arg23);
              reply.writeNoException();
              return true;
            case 62:
              boolean _arg053 = data.readBoolean();
              int _arg126 = data.readInt();
              data.enforceNoDataAvail();
              addOrRemoveSystemAppFromDataSaverWhitelist(_arg053, _arg126);
              reply.writeNoException();
              return true;
            case 63:
              int _arg054 = data.readInt();
              int _arg127 = data.readInt();
              data.enforceNoDataAvail();
              updateInputFilterExemptRules(_arg054, _arg127);
              reply.writeNoException();
              return true;
            case 64:
              int[] _arg055 = data.createIntArray();
              int _arg128 = data.readInt();
              int _arg24 = data.readInt();
              data.enforceNoDataAvail();
              updateInputFilterUserWideRules(_arg055, _arg128, _arg24);
              reply.writeNoException();
              return true;
            case 65:
              int[] _arg056 = data.createIntArray();
              int _arg129 = data.readInt();
              int _arg25 = data.readInt();
              data.enforceNoDataAvail();
              updateInputFilterAppWideRules(_arg056, _arg129, _arg25);
              reply.writeNoException();
              return true;
            case 66:
              int _arg057 = data.readInt();
              data.enforceNoDataAvail();
              clearEbpfMap(_arg057);
              reply.writeNoException();
              return true;
            case 67:
              int _arg058 = data.readInt();
              String _arg130 = data.readString();
              data.enforceNoDataAvail();
              String _result13 = runKnoxFirewallRulesCommand(_arg058, _arg130);
              reply.writeNoException();
              reply.writeString(_result13);
              return true;
            case 68:
              int _arg059 = data.readInt();
              String[] _arg131 = data.createStringArray();
              data.enforceNoDataAvail();
              runKnoxRulesCommand(_arg059, _arg131);
              reply.writeNoException();
              return true;
            case 69:
              boolean _arg060 = data.readBoolean();
              data.enforceNoDataAvail();
              enableKnoxVpnFlagForTether(_arg060);
              reply.writeNoException();
              return true;
            case 70:
              registerNetdTetherEventListener();
              reply.writeNoException();
              return true;
            case 71:
              unregisterNetdTetherEventListener();
              reply.writeNoException();
              return true;
            case 72:
              int _arg061 = data.readInt();
              boolean _arg132 = data.readBoolean();
              data.enforceNoDataAvail();
              setKnoxVpn(_arg061, _arg132);
              reply.writeNoException();
              return true;
            case 73:
              String _arg062 = data.readString();
              String _arg133 = data.readString();
              String _arg26 = data.readString();
              String _arg3 = data.readString();
              int _arg4 = data.readInt();
              data.enforceNoDataAvail();
              addPortFwdRules(_arg062, _arg133, _arg26, _arg3, _arg4);
              reply.writeNoException();
              return true;
            case 74:
              createNetworkGuardChain();
              reply.writeNoException();
              return true;
            case 75:
              deleteNetworkGuardChain();
              reply.writeNoException();
              return true;
            case 76:
              boolean _arg063 = data.readBoolean();
              data.enforceNoDataAvail();
              enableNetworkGuard(_arg063);
              reply.writeNoException();
              return true;
            case 77:
              disableNetworkGuard();
              reply.writeNoException();
              return true;
            case 78:
              deleteNetworkGuardWhiteListRule();
              reply.writeNoException();
              return true;
            case 79:
              int _arg064 = data.readInt();
              int _arg134 = data.readInt();
              data.enforceNoDataAvail();
              setNetworkGuardUidRangeAcceptRule(_arg064, _arg134);
              reply.writeNoException();
              return true;
            case 80:
              int _arg065 = data.readInt();
              boolean _arg135 = data.readBoolean();
              boolean _arg27 = data.readBoolean();
              data.enforceNoDataAvail();
              setNetworkGuardUidRule(_arg065, _arg135, _arg27);
              reply.writeNoException();
              return true;
            case 81:
              int _arg066 = data.readInt();
              data.enforceNoDataAvail();
              setNetworkGuardProtocolAcceptRule(_arg066);
              reply.writeNoException();
              return true;
            case 82:
              String _arg067 = data.readString();
              int _arg136 = data.readInt();
              int _arg28 = data.readInt();
              data.enforceNoDataAvail();
              startNetworkStatsOnPorts(_arg067, _arg136, _arg28);
              reply.writeNoException();
              return true;
            case 83:
              String _arg068 = data.readString();
              int _arg137 = data.readInt();
              int _arg29 = data.readInt();
              data.enforceNoDataAvail();
              stopNetworkStatsOnPorts(_arg068, _arg137, _arg29);
              reply.writeNoException();
              return true;
            case 84:
              String _arg069 = data.readString();
              int _arg138 = data.readInt();
              int _arg210 = data.readInt();
              data.enforceNoDataAvail();
              long _result14 = getNetworkStatsVideoCall(_arg069, _arg138, _arg210);
              reply.writeNoException();
              reply.writeLong(_result14);
              return true;
            case 85:
              boolean _arg070 = data.readBoolean();
              int _arg139 = data.readInt();
              data.enforceNoDataAvail();
              int _result15 = prioritizeApp(_arg070, _arg139);
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 86:
              boolean _arg071 = data.readBoolean();
              String _arg140 = data.readString();
              int _arg211 = data.readInt();
              data.enforceNoDataAvail();
              int _result16 = addApeRule(_arg071, _arg140, _arg211);
              reply.writeNoException();
              reply.writeInt(_result16);
              return true;
            case 87:
              String _arg072 = data.readString();
              int _arg141 = data.readInt();
              int _arg212 = data.readInt();
              data.enforceNoDataAvail();
              int _result17 = replaceApeRule(_arg072, _arg141, _arg212);
              reply.writeNoException();
              reply.writeInt(_result17);
              return true;
            case 88:
              String _arg073 = data.readString();
              data.enforceNoDataAvail();
              startQbox(_arg073);
              reply.writeNoException();
              return true;
            case 89:
              stopQbox();
              reply.writeNoException();
              return true;
            case 90:
              int _arg074 = data.readInt();
              boolean _arg142 = data.readBoolean();
              data.enforceNoDataAvail();
              setQboxUid(_arg074, _arg142);
              reply.writeNoException();
              return true;
            case 91:
              String _arg075 = data.readString();
              String _arg143 = data.readString();
              boolean _arg213 = data.readBoolean();
              data.enforceNoDataAvail();
              enableEpdg(_arg075, _arg143, _arg213);
              reply.writeNoException();
              return true;
            case 92:
              String _arg076 = data.readString();
              String _arg144 = data.readString();
              data.enforceNoDataAvail();
              disableEpdg(_arg076, _arg144);
              reply.writeNoException();
              return true;
            case 93:
              String _arg077 = data.readString();
              String _arg145 = data.readString();
              boolean _arg214 = data.readBoolean();
              data.enforceNoDataAvail();
              setEpdgInterfaceDropRule(_arg077, _arg145, _arg214);
              reply.writeNoException();
              return true;
            case 94:
              Network _arg078 = (Network) data.readTypedObject(Network.CREATOR);
              data.enforceNoDataAvail();
              updateDefaultGatewayForEpdg(_arg078);
              reply.writeNoException();
              return true;
            case 95:
              String _arg079 = data.readString();
              data.enforceNoDataAvail();
              disableDAD(_arg079);
              reply.writeNoException();
              return true;
            case 96:
              boolean _arg080 = data.readBoolean();
              data.enforceNoDataAvail();
              setBlockAllDNSPackets(_arg080);
              reply.writeNoException();
              return true;
            case 97:
              String _arg081 = data.readString();
              data.enforceNoDataAvail();
              setBlockListIPs(_arg081);
              reply.writeNoException();
              return true;
            case 98:
              String _arg082 = data.readString();
              data.enforceNoDataAvail();
              setAllowListIPs(_arg082);
              reply.writeNoException();
              return true;
            case 99:
              String _arg083 = data.readString();
              data.enforceNoDataAvail();
              setBlockHostAlone(_arg083);
              reply.writeNoException();
              return true;
            case 100:
              String _arg084 = data.readString();
              data.enforceNoDataAvail();
              setAllowHostAlone(_arg084);
              reply.writeNoException();
              return true;
            case 101:
              cleanAllBlock();
              reply.writeNoException();
              return true;
            case 102:
              setBlockAllPackets();
              reply.writeNoException();
              return true;
            case 103:
              String _arg085 = data.readString();
              int _arg146 = data.readInt();
              String _arg215 = data.readString();
              data.enforceNoDataAvail();
              setBlockPorts(_arg085, _arg146, _arg215);
              reply.writeNoException();
              return true;
            case 104:
              cleanBlockPorts();
              reply.writeNoException();
              return true;
            case 105:
              boolean _arg086 = data.readBoolean();
              String _arg147 = data.readString();
              int _arg216 = data.readInt();
              data.enforceNoDataAvail();
              setKnoxGuardExemptRule(_arg086, _arg147, _arg216);
              reply.writeNoException();
              return true;
            case 106:
              int _arg087 = data.readInt();
              String _arg148 = data.readString();
              boolean _arg217 = data.readBoolean();
              data.enforceNoDataAvail();
              setUrlFirewallRuleMobileData(_arg087, _arg148, _arg217);
              reply.writeNoException();
              return true;
            case 107:
              int _arg088 = data.readInt();
              String _arg149 = data.readString();
              boolean _arg218 = data.readBoolean();
              data.enforceNoDataAvail();
              setUrlFirewallRuleWifi(_arg088, _arg149, _arg218);
              reply.writeNoException();
              return true;
            case 108:
              buildFirewall();
              reply.writeNoException();
              return true;
            case 109:
              int _arg089 = data.readInt();
              boolean _arg150 = data.readBoolean();
              data.enforceNoDataAvail();
              setFirewallRuleWifi(_arg089, _arg150);
              reply.writeNoException();
              return true;
            case 110:
              int _arg090 = data.readInt();
              boolean _arg151 = data.readBoolean();
              data.enforceNoDataAvail();
              setFirewallRuleMobileData(_arg090, _arg151);
              reply.writeNoException();
              return true;
            case 111:
              String _arg091 = data.readString();
              data.enforceNoDataAvail();
              addMptcpLink(_arg091);
              reply.writeNoException();
              return true;
            case 112:
              String _arg092 = data.readString();
              data.enforceNoDataAvail();
              removeMptcpLink(_arg092);
              reply.writeNoException();
              return true;
            case 113:
              String _arg093 = data.readString();
              String _arg152 = data.readString();
              data.enforceNoDataAvail();
              addChain(_arg093, _arg152);
              reply.writeNoException();
              return true;
            case 114:
              String _arg094 = data.readString();
              String _arg153 = data.readString();
              data.enforceNoDataAvail();
              removeChain(_arg094, _arg153);
              reply.writeNoException();
              return true;
            case 115:
              String _arg095 = data.readString();
              String _arg154 = data.readString();
              String _arg219 = data.readString();
              int _arg32 = data.readInt();
              String _arg42 = data.readString();
              data.enforceNoDataAvail();
              addSocksRule(_arg095, _arg154, _arg219, _arg32, _arg42);
              reply.writeNoException();
              return true;
            case 116:
              String _arg096 = data.readString();
              String _arg155 = data.readString();
              String _arg220 = data.readString();
              int _arg33 = data.readInt();
              String _arg43 = data.readString();
              data.enforceNoDataAvail();
              removeSocksRule(_arg096, _arg155, _arg220, _arg33, _arg43);
              reply.writeNoException();
              return true;
            case 117:
              String _arg097 = data.readString();
              String _arg156 = data.readString();
              String _arg221 = data.readString();
              int _arg34 = data.readInt();
              int _arg44 = data.readInt();
              String _arg5 = data.readString();
              data.enforceNoDataAvail();
              addUidSocksRule(_arg097, _arg156, _arg221, _arg34, _arg44, _arg5);
              reply.writeNoException();
              return true;
            case 118:
              String _arg098 = data.readString();
              String _arg157 = data.readString();
              String _arg222 = data.readString();
              int _arg35 = data.readInt();
              int _arg45 = data.readInt();
              String _arg52 = data.readString();
              data.enforceNoDataAvail();
              removeUidSocksRule(_arg098, _arg157, _arg222, _arg35, _arg45, _arg52);
              reply.writeNoException();
              return true;
            case 119:
              String _arg099 = data.readString();
              String _arg158 = data.readString();
              String _arg223 = data.readString();
              data.enforceNoDataAvail();
              addSocksSkipRule(_arg099, _arg158, _arg223);
              reply.writeNoException();
              return true;
            case 120:
              String _arg0100 = data.readString();
              String _arg159 = data.readString();
              String _arg224 = data.readString();
              data.enforceNoDataAvail();
              removeSocksSkipRule(_arg0100, _arg159, _arg224);
              reply.writeNoException();
              return true;
            case 121:
              String _arg0101 = data.readString();
              String _arg160 = data.readString();
              String _arg225 = data.readString();
              int _arg36 = data.readInt();
              String _arg46 = data.readString();
              data.enforceNoDataAvail();
              addSocksSkipRuleProto(_arg0101, _arg160, _arg225, _arg36, _arg46);
              reply.writeNoException();
              return true;
            case 122:
              String _arg0102 = data.readString();
              String _arg161 = data.readString();
              String _arg226 = data.readString();
              int _arg37 = data.readInt();
              String _arg47 = data.readString();
              data.enforceNoDataAvail();
              removeSocksSkipRuleProto(_arg0102, _arg161, _arg226, _arg37, _arg47);
              reply.writeNoException();
              return true;
            case 123:
              String _arg0103 = data.readString();
              String _arg162 = data.readString();
              int _arg227 = data.readInt();
              data.enforceNoDataAvail();
              addUidToChain(_arg0103, _arg162, _arg227);
              reply.writeNoException();
              return true;
            case 124:
              String _arg0104 = data.readString();
              String _arg163 = data.readString();
              int _arg228 = data.readInt();
              data.enforceNoDataAvail();
              removeUidFromChain(_arg0104, _arg163, _arg228);
              reply.writeNoException();
              return true;
            case 125:
              String _arg0105 = data.readString();
              String _arg164 = data.readString();
              String _arg229 = data.readString();
              data.enforceNoDataAvail();
              addIpAcceptRule(_arg0105, _arg164, _arg229);
              reply.writeNoException();
              return true;
            case 126:
              String _arg0106 = data.readString();
              String _arg165 = data.readString();
              String _arg230 = data.readString();
              data.enforceNoDataAvail();
              delIpAcceptRule(_arg0106, _arg165, _arg230);
              reply.writeNoException();
              return true;
            case 127:
              String _arg0107 = data.readString();
              String _arg166 = data.readString();
              data.enforceNoDataAvail();
              setTcpBufferSize(_arg0107, _arg166);
              reply.writeNoException();
              return true;
            case 128:
              String _arg0108 = data.readString();
              int _arg167 = data.readInt();
              data.enforceNoDataAvail();
              setMptcpMtuValue(_arg0108, _arg167);
              reply.writeNoException();
              return true;
            case 129:
              String _arg0109 = data.readString();
              data.enforceNoDataAvail();
              enableMptcp(_arg0109);
              reply.writeNoException();
              return true;
            case 130:
              disableMptcp();
              reply.writeNoException();
              return true;
            case 131:
              String _arg0110 = data.readString();
              String _arg168 = data.readString();
              String _arg231 = data.readString();
              data.enforceNoDataAvail();
              addSourceRoute(_arg0110, _arg168, _arg231);
              reply.writeNoException();
              return true;
            case 132:
              String _arg0111 = data.readString();
              String _arg169 = data.readString();
              String _arg232 = data.readString();
              data.enforceNoDataAvail();
              delSourceRoute(_arg0111, _arg169, _arg232);
              reply.writeNoException();
              return true;
            case 133:
              String _arg0112 = data.readString();
              String _arg170 = data.readString();
              int _arg233 = data.readInt();
              data.enforceNoDataAvail();
              addSourcePortAcceptRule(_arg0112, _arg170, _arg233);
              reply.writeNoException();
              return true;
            case 134:
              String _arg0113 = data.readString();
              String _arg171 = data.readString();
              int _arg234 = data.readInt();
              data.enforceNoDataAvail();
              delSourcePortAcceptRule(_arg0113, _arg171, _arg234);
              reply.writeNoException();
              return true;
            case 135:
              boolean _arg0114 = data.readBoolean();
              String _arg172 = data.readString();
              String _arg235 = data.readString();
              data.enforceNoDataAvail();
              updateSourceRule(_arg0114, _arg172, _arg235);
              reply.writeNoException();
              return true;
            case 136:
              boolean _arg0115 = data.readBoolean();
              String _arg173 = data.readString();
              int _arg236 = data.readInt();
              data.enforceNoDataAvail();
              setPrivateIpRoute(_arg0115, _arg173, _arg236);
              reply.writeNoException();
              return true;
            case 137:
              boolean _arg0116 = data.readBoolean();
              String _arg174 = data.readString();
              String _arg237 = data.readString();
              int _arg38 = data.readInt();
              int _arg48 = data.readInt();
              data.enforceNoDataAvail();
              setDestinationBasedMarkRule(_arg0116, _arg174, _arg237, _arg38, _arg48);
              reply.writeNoException();
              return true;
            case 138:
              boolean _arg0117 = data.readBoolean();
              String _arg175 = data.readString();
              int _arg238 = data.readInt();
              String _arg39 = data.readString();
              String _arg49 = data.readString();
              data.enforceNoDataAvail();
              setUIDRoute(_arg0117, _arg175, _arg238, _arg39, _arg49);
              reply.writeNoException();
              return true;
            case 139:
              String _arg0118 = data.readString();
              boolean _arg176 = data.readBoolean();
              data.enforceNoDataAvail();
              setAutoConf(_arg0118, _arg176);
              reply.writeNoException();
              return true;
            case 140:
              boolean _arg0119 = data.readBoolean();
              int _arg177 = data.readInt();
              data.enforceNoDataAvail();
              int _result18 = prioritizeMnxbApp(_arg0119, _arg177);
              reply.writeNoException();
              reply.writeInt(_result18);
              return true;
            case 141:
              boolean _arg0120 = data.readBoolean();
              String _arg178 = data.readString();
              int _arg239 = data.readInt();
              data.enforceNoDataAvail();
              int _result19 = addMnxbRule(_arg0120, _arg178, _arg239);
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 142:
              String _arg0121 = data.readString();
              int _arg179 = data.readInt();
              int _arg240 = data.readInt();
              data.enforceNoDataAvail();
              int _result20 = replaceMnxbRule(_arg0121, _arg179, _arg240);
              reply.writeNoException();
              reply.writeInt(_result20);
              return true;
            case 143:
              int _arg0122 = data.readInt();
              data.enforceNoDataAvail();
              setAdvertiseWindowSize(_arg0122);
              reply.writeNoException();
              return true;
            case 144:
              long[] _result21 = l4StatsGet();
              reply.writeNoException();
              reply.writeLongArray(_result21);
              return true;
            case 145:
              int _arg0123 = data.readInt();
              String _arg180 = data.readString();
              String _arg241 = data.readString();
              String _arg310 = data.readString();
              int _arg410 = data.readInt();
              data.enforceNoDataAvail();
              addLegacyRoute(_arg0123, _arg180, _arg241, _arg310, _arg410);
              reply.writeNoException();
              return true;
            case 146:
              int _arg0124 = data.readInt();
              String _arg181 = data.readString();
              String _arg242 = data.readString();
              String _arg311 = data.readString();
              int _arg411 = data.readInt();
              data.enforceNoDataAvail();
              removeLegacyRoute(_arg0124, _arg181, _arg242, _arg311, _arg411);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INetworkManagementService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.p009os.INetworkManagementService
      public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(obs);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void unregisterObserver(INetworkManagementEventObserver obs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(obs);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public String[] listInterfaces() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          InterfaceConfiguration _result =
              (InterfaceConfiguration) _reply.readTypedObject(InterfaceConfiguration.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setInterfaceConfig(String iface, InterfaceConfiguration cfg)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeTypedObject(cfg, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void clearInterfaceAddresses(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setInterfaceDown(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setInterfaceUp(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeBoolean(enable);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void disableIpv6(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void enableIpv6(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeInt(mode);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addRoute(int netId, RouteInfo route) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeTypedObject(route, 0);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeRoute(int netId, RouteInfo route) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeTypedObject(route, 0);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void shutdown() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public boolean getIpForwardingEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setIpForwardingEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void startTethering(String[] dhcpRanges) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStringArray(dhcpRanges);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void startTetheringWithConfiguration(boolean usingLegacyDnsProxy, String[] dhcpRanges)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(usingLegacyDnsProxy);
          _data.writeStringArray(dhcpRanges);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void stopTethering() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public boolean isTetheringStarted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void tetherInterface(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void untetherInterface(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public String[] listTetheredInterfaces() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public String[] getDnsForwarders() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void startInterfaceForwarding(String fromIface, String toIface)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(fromIface);
          _data.writeString(toIface);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void stopInterfaceForwarding(String fromIface, String toIface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(fromIface);
          _data.writeString(toIface);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void enableNat(String internalInterface, String externalInterface)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(internalInterface);
          _data.writeString(externalInterface);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void disableNat(String internalInterface, String externalInterface)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(internalInterface);
          _data.writeString(externalInterface);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void registerTetheringStatsProvider(ITetheringStatsProvider provider, String name)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(provider);
          _data.writeString(name);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void unregisterTetheringStatsProvider(ITetheringStatsProvider provider)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(provider);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void tetherLimitReached(ITetheringStatsProvider provider) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(provider);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public NetworkStats getNetworkStatsTethering(int how) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(how);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          NetworkStats _result = (NetworkStats) _reply.readTypedObject(NetworkStats.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeLong(quotaBytes);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeInterfaceQuota(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeLong(alertBytes);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeInterfaceAlert(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setGlobalAlert(long alertBytes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(alertBytes);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setUidOnMeteredNetworkDenylist(int uid, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(enable);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setUidOnMeteredNetworkAllowlist(int uid, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(enable);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(policy);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public boolean isBandwidthControlEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public boolean isFirewallEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallInterfaceRule(String iface, boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeBoolean(allow);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(chain);
          _data.writeInt(uid);
          _data.writeInt(rule);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallUidRules(int chain, int[] uids, int[] rules) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(chain);
          _data.writeIntArray(uids);
          _data.writeIntArray(rules);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(chain);
          _data.writeBoolean(enable);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void closeSocketsForFreecess(int chain, String chainName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(chain);
          _data.writeString(chainName);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void closeSocketsForUids(int[] uids) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeIntArray(uids);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void closeSocketsForUid(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void allowProtect(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void denyProtect(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addInterfaceToLocalNetwork(String iface, List<RouteInfo> routes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeTypedList(routes, 0);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeInterfaceFromLocalNetwork(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int removeRoutesFromLocalNetwork(List<RouteInfo> routes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedList(routes, 0);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public boolean isNetworkRestricted(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void spegRestrictNetworkConnection(int uid, boolean restrict) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(restrict);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setDnsForwardersForKnoxVpn(int netId, String[] dns) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeStringArray(dns);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setNetworkInfo(int netId, boolean chainedNetwork, int vpnClientUid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeBoolean(chainedNetwork);
          _data.writeInt(vpnClientUid);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean enable, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enable);
          _data.writeInt(uid);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void updateInputFilterExemptRules(int uid, int update) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(update);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void updateInputFilterUserWideRules(int[] userIds, int ifaceIndex, int update)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeIntArray(userIds);
          _data.writeInt(ifaceIndex);
          _data.writeInt(update);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void updateInputFilterAppWideRules(int[] uids, int ifaceIndex, int update)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeIntArray(uids);
          _data.writeInt(ifaceIndex);
          _data.writeInt(update);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void clearEbpfMap(int mapId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mapId);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public String runKnoxFirewallRulesCommand(int target, String cmd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(target);
          _data.writeString(cmd);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void runKnoxRulesCommand(int cmd, String[] params) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(cmd);
          _data.writeStringArray(params);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void enableKnoxVpnFlagForTether(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void registerNetdTetherEventListener() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void unregisterNetdTetherEventListener() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setKnoxVpn(int netId, boolean isKnoxVpn) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeBoolean(isKnoxVpn);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addPortFwdRules(
          String externalIface,
          String interfanIface,
          String externalIp,
          String internalIp,
          int port)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(externalIface);
          _data.writeString(interfanIface);
          _data.writeString(externalIp);
          _data.writeString(internalIp);
          _data.writeInt(port);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void createNetworkGuardChain() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void deleteNetworkGuardChain() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void enableNetworkGuard(boolean isBlack) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(isBlack);
          this.mRemote.transact(76, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void disableNetworkGuard() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(77, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void deleteNetworkGuardWhiteListRule() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setNetworkGuardUidRangeAcceptRule(int uidStart, int uidEnd)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uidStart);
          _data.writeInt(uidEnd);
          this.mRemote.transact(79, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setNetworkGuardUidRule(int uid, boolean mode, boolean isDrop)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(mode);
          _data.writeBoolean(isDrop);
          this.mRemote.transact(80, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setNetworkGuardProtocolAcceptRule(int protocol) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(protocol);
          this.mRemote.transact(81, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void startNetworkStatsOnPorts(String iface, int inport, int outport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeInt(inport);
          _data.writeInt(outport);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void stopNetworkStatsOnPorts(String iface, int inport, int outport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeInt(inport);
          _data.writeInt(outport);
          this.mRemote.transact(83, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public long getNetworkStatsVideoCall(String iface, int sport, int dport)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeInt(sport);
          _data.writeInt(dport);
          this.mRemote.transact(84, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int prioritizeApp(boolean add, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeInt(uid);
          this.mRemote.transact(85, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int addApeRule(boolean add, String infName, int bandwidthMbps) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(infName);
          _data.writeInt(bandwidthMbps);
          this.mRemote.transact(86, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int replaceApeRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(infName);
          _data.writeInt(oldBandwidthMbps);
          _data.writeInt(newBandwidthMbps);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void startQbox(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void stopQbox() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(89, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setQboxUid(int uid, boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(allow);
          this.mRemote.transact(90, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void enableEpdg(String mobileInterface, String tunnelingInterface, boolean deleteSkip)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(mobileInterface);
          _data.writeString(tunnelingInterface);
          _data.writeBoolean(deleteSkip);
          this.mRemote.transact(91, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void disableEpdg(String mobileInterface, String tunnelingInterface)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(mobileInterface);
          _data.writeString(tunnelingInterface);
          this.mRemote.transact(92, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setEpdgInterfaceDropRule(String iface, String src, boolean add)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(src);
          _data.writeBoolean(add);
          this.mRemote.transact(93, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(network, 0);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void disableDAD(String ifName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(ifName);
          this.mRemote.transact(95, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setBlockAllDNSPackets(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(96, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setBlockListIPs(String addr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(addr);
          this.mRemote.transact(97, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setAllowListIPs(String addr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(addr);
          this.mRemote.transact(98, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setBlockHostAlone(String addr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(addr);
          this.mRemote.transact(99, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setAllowHostAlone(String addr) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(addr);
          this.mRemote.transact(100, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void cleanAllBlock() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setBlockAllPackets() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setBlockPorts(String protocol, int directionBitMask, String ports)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(protocol);
          _data.writeInt(directionBitMask);
          _data.writeString(ports);
          this.mRemote.transact(103, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void cleanBlockPorts() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(104, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setKnoxGuardExemptRule(boolean add, String ifaceName, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(ifaceName);
          _data.writeInt(uid);
          this.mRemote.transact(105, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setUrlFirewallRuleMobileData(int uid, String url, boolean allow)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeString(url);
          _data.writeBoolean(allow);
          this.mRemote.transact(106, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setUrlFirewallRuleWifi(int uid, String url, boolean allow)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeString(url);
          _data.writeBoolean(allow);
          this.mRemote.transact(107, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void buildFirewall() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(108, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallRuleWifi(int uid, boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(allow);
          this.mRemote.transact(109, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setFirewallRuleMobileData(int uid, boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeBoolean(allow);
          this.mRemote.transact(110, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addMptcpLink(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(111, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeMptcpLink(String iface) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          this.mRemote.transact(112, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addChain(String chain, String ip_type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(ip_type);
          this.mRemote.transact(113, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeChain(String chain, String ip_type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(ip_type);
          this.mRemote.transact(114, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addSocksRule(String iface, String chain, String proto, int port, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(port);
          _data.writeString(ip_type);
          this.mRemote.transact(115, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeSocksRule(
          String iface, String chain, String proto, int port, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(port);
          _data.writeString(ip_type);
          this.mRemote.transact(116, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addUidSocksRule(
          String iface, String chain, String proto, int port, int uid, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(port);
          _data.writeInt(uid);
          _data.writeString(ip_type);
          this.mRemote.transact(117, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeUidSocksRule(
          String iface, String chain, String proto, int port, int uid, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(port);
          _data.writeInt(uid);
          _data.writeString(ip_type);
          this.mRemote.transact(118, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addSocksSkipRule(String chain, String addr, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(addr);
          _data.writeString(ip_type);
          this.mRemote.transact(119, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeSocksSkipRule(String chain, String addr, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(addr);
          _data.writeString(ip_type);
          this.mRemote.transact(120, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addSocksSkipRuleProto(
          String chain, String addr, String proto, int port, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(addr);
          _data.writeString(proto);
          _data.writeInt(port);
          _data.writeString(ip_type);
          this.mRemote.transact(121, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeSocksSkipRuleProto(
          String chain, String addr, String proto, int port, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(addr);
          _data.writeString(proto);
          _data.writeInt(port);
          _data.writeString(ip_type);
          this.mRemote.transact(122, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addUidToChain(String chain, String proto, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(uid);
          this.mRemote.transact(123, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeUidFromChain(String chain, String proto, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(uid);
          this.mRemote.transact(124, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addIpAcceptRule(String chain, String dest, String proto) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(dest);
          _data.writeString(proto);
          this.mRemote.transact(125, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void delIpAcceptRule(String chain, String dest, String proto) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(dest);
          _data.writeString(proto);
          this.mRemote.transact(126, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setTcpBufferSize(String rmem, String wmem) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(rmem);
          _data.writeString(wmem);
          this.mRemote.transact(127, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setMptcpMtuValue(String iface, int mtu) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeInt(mtu);
          this.mRemote.transact(128, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void enableMptcp(String value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(value);
          this.mRemote.transact(129, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void disableMptcp() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(130, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addSourceRoute(String iface, String addr, String gateway) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(addr);
          _data.writeString(gateway);
          this.mRemote.transact(131, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void delSourceRoute(String iface, String addr, String gateway) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeString(addr);
          _data.writeString(gateway);
          this.mRemote.transact(132, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addSourcePortAcceptRule(String chain, String proto, int port)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(port);
          this.mRemote.transact(133, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void delSourcePortAcceptRule(String chain, String proto, int port)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(chain);
          _data.writeString(proto);
          _data.writeInt(port);
          this.mRemote.transact(134, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void updateSourceRule(boolean add, String ipAddr, String ifaceName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(ipAddr);
          _data.writeString(ifaceName);
          this.mRemote.transact(135, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setPrivateIpRoute(boolean add, String ifaceName, int mark)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(ifaceName);
          _data.writeInt(mark);
          this.mRemote.transact(136, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setDestinationBasedMarkRule(
          boolean add, String addr, String outInterface, int mark, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(addr);
          _data.writeString(outInterface);
          _data.writeInt(mark);
          _data.writeInt(uid);
          this.mRemote.transact(137, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setUIDRoute(boolean add, String iface, int uid, String pref, String ip_type)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(iface);
          _data.writeInt(uid);
          _data.writeString(pref);
          _data.writeString(ip_type);
          this.mRemote.transact(138, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setAutoConf(String iface, boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(iface);
          _data.writeBoolean(enable);
          this.mRemote.transact(139, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int prioritizeMnxbApp(boolean add, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeInt(uid);
          this.mRemote.transact(140, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int addMnxbRule(boolean add, String infName, int bandwidthMbps)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(add);
          _data.writeString(infName);
          _data.writeInt(bandwidthMbps);
          this.mRemote.transact(141, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public int replaceMnxbRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(infName);
          _data.writeInt(oldBandwidthMbps);
          _data.writeInt(newBandwidthMbps);
          this.mRemote.transact(142, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void setAdvertiseWindowSize(int newAdvertiseWindow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(newAdvertiseWindow);
          this.mRemote.transact(143, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public long[] l4StatsGet() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(144, _data, _reply, 0);
          _reply.readException();
          long[] _result = _reply.createLongArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void addLegacyRoute(
          int netId, String ifName, String destination, String nextHop, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeString(ifName);
          _data.writeString(destination);
          _data.writeString(nextHop);
          _data.writeInt(uid);
          this.mRemote.transact(145, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.INetworkManagementService
      public void removeLegacyRoute(
          int netId, String ifName, String destination, String nextHop, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(netId);
          _data.writeString(ifName);
          _data.writeString(destination);
          _data.writeString(nextHop);
          _data.writeInt(uid);
          this.mRemote.transact(146, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    protected void shutdown_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.SHUTDOWN, getCallingPid(), getCallingUid());
    }

    protected void setDataSaverModeEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.NETWORK_SETTINGS, getCallingPid(), getCallingUid());
    }

    protected void isNetworkRestricted_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.OBSERVE_NETWORK_POLICY, getCallingPid(), getCallingUid());
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 145;
    }
  }
}
