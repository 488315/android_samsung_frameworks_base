package android.os;

import android.Manifest;
import android.app.ActivityThread;
import android.hardware.usb.UsbManager;
import android.net.ICloEventObserver;
import android.net.INetworkManagementEventObserver;
import android.net.InterfaceConfiguration;
import android.net.Network;

/* loaded from: classes3.dex */
public interface INetworkManagementService extends IInterface {
    void activateClo(String str) throws RemoteException;

    void activateCloGro() throws RemoteException;

    int addApeRule(boolean z, String str, int i) throws RemoteException;

    void addChain(String str, String str2) throws RemoteException;

    void addIpAcceptRule(String str, String str2, String str3) throws RemoteException;

    void addLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException;

    int addMnxbRule(boolean z, String str, int i) throws RemoteException;

    void addMptcpLink(String str) throws RemoteException;

    void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) throws RemoteException;

    void addPortFwdRules(String str, String str2, String str3, String str4, int i)
            throws RemoteException;

    void addSocksRule(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void addSocksSkipRule(String str, String str2, String str3) throws RemoteException;

    void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void addSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

    void addSourceRoute(String str, String str2, String str3) throws RemoteException;

    void addTosPolicy(int i, int i2) throws RemoteException;

    void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4)
            throws RemoteException;

    void addUidToChain(String str, String str2, int i) throws RemoteException;

    void allowProtect(int i) throws RemoteException;

    void buildFirewall() throws RemoteException;

    void cleanAllBlock() throws RemoteException;

    void cleanBlockPorts() throws RemoteException;

    void cleanOnlyAllowIPs() throws RemoteException;

    void clearEbpfMap(int i) throws RemoteException;

    void clearInterfaceAddresses(String str) throws RemoteException;

    void clearTosMap() throws RemoteException;

    void closeSocketsForFreecess(int i, String str) throws RemoteException;

    void closeSocketsForUid(int i) throws RemoteException;

    void closeSocketsForUids(int[] iArr) throws RemoteException;

    void deactivateClo(String str) throws RemoteException;

    void deactivateCloGro() throws RemoteException;

    void delIpAcceptRule(String str, String str2, String str3) throws RemoteException;

    void delSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

    void delSourceRoute(String str, String str2, String str3) throws RemoteException;

    void denyProtect(int i) throws RemoteException;

    void disableDAD(String str) throws RemoteException;

    void disableEpdg(String str, String str2) throws RemoteException;

    void disableIpv6(String str) throws RemoteException;

    void disableMptcp() throws RemoteException;

    void disableNat(String str, String str2) throws RemoteException;

    void enableEpdg(String str, String str2, boolean z) throws RemoteException;

    void enableIpv6(String str) throws RemoteException;

    void enableKnoxVpnFlagForTether(boolean z) throws RemoteException;

    void enableMptcp(String str) throws RemoteException;

    void enableNat(String str, String str2) throws RemoteException;

    InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException;

    boolean getIpForwardingEnabled() throws RemoteException;

    int getL4sConnCount() throws RemoteException;

    long getNetworkStatsVideoCall(String str, int i, int i2) throws RemoteException;

    int[] getTcpLocalPorts(int[] iArr) throws RemoteException;

    boolean isBandwidthControlEnabled() throws RemoteException;

    boolean isFirewallEnabled() throws RemoteException;

    boolean isNetworkRestricted(int i) throws RemoteException;

    boolean isTetheringStarted() throws RemoteException;

    long[] l4StatsGet() throws RemoteException;

    String[] listInterfaces() throws RemoteException;

    String[] listTetheredInterfaces() throws RemoteException;

    int prioritizeApp(boolean z, int i) throws RemoteException;

    int prioritizeMnxbApp(boolean z, int i) throws RemoteException;

    void registerCloEventObserver(ICloEventObserver iCloEventObserver) throws RemoteException;

    void registerNetdTetherEventListener() throws RemoteException;

    void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver)
            throws RemoteException;

    void removeChain(String str, String str2) throws RemoteException;

    void removeInterfaceAlert(String str) throws RemoteException;

    void removeInterfaceQuota(String str) throws RemoteException;

    void removeLegacyRoute(int i, String str, String str2, String str3, int i2)
            throws RemoteException;

    void removeMptcpLink(String str) throws RemoteException;

    void removeSocksRule(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void removeSocksSkipRule(String str, String str2, String str3) throws RemoteException;

    void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void removeTosPolicy(int i) throws RemoteException;

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

    void setFirewallRuleMobileData(int i, boolean z) throws RemoteException;

    void setFirewallRuleWifi(int i, boolean z) throws RemoteException;

    void setFirewallUidRule(int i, int i2, int i3) throws RemoteException;

    void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws RemoteException;

    void setIPv6AddrGenMode(String str, int i) throws RemoteException;

    void setInterfaceAlert(String str, long j) throws RemoteException;

    void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration)
            throws RemoteException;

    void setInterfaceDown(String str) throws RemoteException;

    void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException;

    void setInterfaceQuota(String str, long j) throws RemoteException;

    void setInterfaceUp(String str) throws RemoteException;

    void setIpForwardingEnabled(boolean z) throws RemoteException;

    void setKnoxVpn(int i, boolean z) throws RemoteException;

    void setMptcpMtuValue(String str, int i) throws RemoteException;

    void setNetworkInfo(int i, boolean z, int i2) throws RemoteException;

    void setOnlyAllowIPs(String str) throws RemoteException;

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

    int startL4s(String str) throws RemoteException;

    void startNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException;

    void startQbox(String str) throws RemoteException;

    void startTethering(String[] strArr) throws RemoteException;

    void startTosMarker(String str) throws RemoteException;

    int stopL4s(String str) throws RemoteException;

    void stopNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException;

    void stopQbox() throws RemoteException;

    void stopTethering() throws RemoteException;

    void stopTosMarker(String str) throws RemoteException;

    void tetherInterface(String str) throws RemoteException;

    void unregisterCloEventObserver() throws RemoteException;

    void unregisterNetdTetherEventListener() throws RemoteException;

    void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver)
            throws RemoteException;

    void untetherInterface(String str) throws RemoteException;

    void updateDefaultGatewayForEpdg(Network network) throws RemoteException;

    void updateGroFlushTime(long j) throws RemoteException;

    void updateGroPshOption(int i) throws RemoteException;

    void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException;

    void updateInputFilterExemptRules(int i, int i2) throws RemoteException;

    void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException;

    void updateSourceRule(boolean z, String str, String str2) throws RemoteException;

    public static class Default implements INetworkManagementService {
        @Override // android.os.INetworkManagementService
        public void registerObserver(INetworkManagementEventObserver obs) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void unregisterObserver(INetworkManagementEventObserver obs)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public String[] listInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceConfig(String iface, InterfaceConfiguration cfg)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void clearInterfaceAddresses(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setInterfaceDown(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setInterfaceUp(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setInterfaceIpv6PrivacyExtensions(String iface, boolean enable)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void disableIpv6(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void enableIpv6(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setIPv6AddrGenMode(String iface, int mode) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void shutdown() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public boolean getIpForwardingEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setIpForwardingEnabled(boolean enabled) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void startTethering(String[] dhcpRanges) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void stopTethering() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public boolean isTetheringStarted() throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void tetherInterface(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void untetherInterface(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public String[] listTetheredInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void enableNat(String internalInterface, String externalInterface)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void disableNat(String internalInterface, String externalInterface)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeInterfaceQuota(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeInterfaceAlert(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setUidOnMeteredNetworkDenylist(int uid, boolean enable)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setUidOnMeteredNetworkAllowlist(int uid, boolean enable)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public boolean isBandwidthControlEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallEnabled(boolean enabled) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public boolean isFirewallEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setFirewallUidRules(int chain, int[] uids, int[] rules)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void closeSocketsForFreecess(int chain, String chainName) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void closeSocketsForUids(int[] uids) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void closeSocketsForUid(int uid) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void allowProtect(int uid) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void denyProtect(int uid) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public boolean isNetworkRestricted(int uid) throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void spegRestrictNetworkConnection(int uid, boolean restrict)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setDnsForwardersForKnoxVpn(int netId, String[] dns) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setNetworkInfo(int netId, boolean chainedNetwork, int vpnClientUid)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean enable, int uid)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateInputFilterExemptRules(int uid, int update) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateInputFilterUserWideRules(int[] userIds, int ifaceIndex, int update)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateInputFilterAppWideRules(int[] uids, int ifaceIndex, int update)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void clearEbpfMap(int mapId) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public String runKnoxFirewallRulesCommand(int target, String cmd) throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void runKnoxRulesCommand(int cmd, String[] params) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void enableKnoxVpnFlagForTether(boolean enabled) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void registerNetdTetherEventListener() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void unregisterNetdTetherEventListener() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setKnoxVpn(int netId, boolean isKnoxVpn) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void startNetworkStatsOnPorts(String iface, int inport, int outport)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void stopNetworkStatsOnPorts(String iface, int inport, int outport)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public long getNetworkStatsVideoCall(String iface, int sport, int dport)
                throws RemoteException {
            return 0L;
        }

        @Override // android.os.INetworkManagementService
        public int prioritizeApp(boolean add, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int addApeRule(boolean add, String infName, int bandwidthMbps)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int replaceApeRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void startQbox(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void stopQbox() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setQboxUid(int uid, boolean allow) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public int startL4s(String iface) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int stopL4s(String iface) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int getL4sConnCount() throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void startTosMarker(String ifname) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void stopTosMarker(String ifname) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addTosPolicy(int uid, int tosValue) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeTosPolicy(int uid) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void clearTosMap() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public int[] getTcpLocalPorts(int[] uids) throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void enableEpdg(
                String mobileInterface, String tunnelingInterface, boolean deleteSkip)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void disableEpdg(String mobileInterface, String tunnelingInterface)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setEpdgInterfaceDropRule(String iface, String src, boolean add)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void disableDAD(String ifName) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setBlockAllDNSPackets(boolean enabled) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setBlockListIPs(String addr) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setAllowListIPs(String addr) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setBlockHostAlone(String addr) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setAllowHostAlone(String addr) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void cleanAllBlock() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setBlockAllPackets() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setBlockPorts(String protocol, int directionBitMask, String ports)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void cleanBlockPorts() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setOnlyAllowIPs(String addr) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void cleanOnlyAllowIPs() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setUrlFirewallRuleMobileData(int uid, String url, boolean allow)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setUrlFirewallRuleWifi(int uid, String url, boolean allow)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void buildFirewall() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setFirewallRuleWifi(int uid, boolean allow) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setFirewallRuleMobileData(int uid, boolean allow) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addPortFwdRules(
                String externalIface,
                String interfanIface,
                String externalIp,
                String internalIp,
                int port)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setAutoConf(String iface, boolean enable) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addLegacyRoute(
                int netId, String ifName, String destination, String nextHop, int uid)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeLegacyRoute(
                int netId, String ifName, String destination, String nextHop, int uid)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public int prioritizeMnxbApp(boolean add, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int addMnxbRule(boolean add, String infName, int bandwidthMbps)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int replaceMnxbRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void setAdvertiseWindowSize(int newAdvertiseWindow) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public long[] l4StatsGet() throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void activateClo(String ifaceName) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void deactivateClo(String ifaceName) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void activateCloGro() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void deactivateCloGro() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void registerCloEventObserver(ICloEventObserver observer) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void unregisterCloEventObserver() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateGroFlushTime(long flushTime) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateGroPshOption(int pshOption) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addMptcpLink(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeMptcpLink(String iface) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addChain(String chain, String ip_type) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeChain(String chain, String ip_type) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addSocksRule(String iface, String chain, String proto, int port, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeSocksRule(
                String iface, String chain, String proto, int port, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addUidSocksRule(
                String iface, String chain, String proto, int port, int uid, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeUidSocksRule(
                String iface, String chain, String proto, int port, int uid, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addSocksSkipRule(String chain, String addr, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeSocksSkipRule(String chain, String addr, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addSocksSkipRuleProto(
                String chain, String addr, String proto, int port, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeSocksSkipRuleProto(
                String chain, String addr, String proto, int port, String ip_type)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addUidToChain(String chain, String proto, int uid) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void removeUidFromChain(String chain, String proto, int uid)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addIpAcceptRule(String chain, String dest, String proto)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void delIpAcceptRule(String chain, String dest, String proto)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setTcpBufferSize(String rmem, String wmem) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setMptcpMtuValue(String iface, int mtu) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void enableMptcp(String value) throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void disableMptcp() throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addSourceRoute(String iface, String addr, String gateway)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void delSourceRoute(String iface, String addr, String gateway)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void addSourcePortAcceptRule(String chain, String proto, int port)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void delSourcePortAcceptRule(String chain, String proto, int port)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void updateSourceRule(boolean add, String ipAddr, String ifaceName)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setPrivateIpRoute(boolean add, String ifaceName, int mark)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setDestinationBasedMarkRule(
                boolean add, String addr, String outInterface, int mark, int uid)
                throws RemoteException {}

        @Override // android.os.INetworkManagementService
        public void setUIDRoute(boolean add, String iface, int uid, String pref, String ip_type)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements INetworkManagementService {
        public static final String DESCRIPTOR = "android.os.INetworkManagementService";
        static final int TRANSACTION_activateClo = 106;
        static final int TRANSACTION_activateCloGro = 108;
        static final int TRANSACTION_addApeRule = 62;
        static final int TRANSACTION_addChain = 116;
        static final int TRANSACTION_addIpAcceptRule = 128;
        static final int TRANSACTION_addLegacyRoute = 99;
        static final int TRANSACTION_addMnxbRule = 102;
        static final int TRANSACTION_addMptcpLink = 114;
        static final int TRANSACTION_addOrRemoveSystemAppFromDataSaverWhitelist = 47;
        static final int TRANSACTION_addPortFwdRules = 97;
        static final int TRANSACTION_addSocksRule = 118;
        static final int TRANSACTION_addSocksSkipRule = 122;
        static final int TRANSACTION_addSocksSkipRuleProto = 124;
        static final int TRANSACTION_addSourcePortAcceptRule = 136;
        static final int TRANSACTION_addSourceRoute = 134;
        static final int TRANSACTION_addTosPolicy = 72;
        static final int TRANSACTION_addUidSocksRule = 120;
        static final int TRANSACTION_addUidToChain = 126;
        static final int TRANSACTION_allowProtect = 41;
        static final int TRANSACTION_buildFirewall = 94;
        static final int TRANSACTION_cleanAllBlock = 86;
        static final int TRANSACTION_cleanBlockPorts = 89;
        static final int TRANSACTION_cleanOnlyAllowIPs = 91;
        static final int TRANSACTION_clearEbpfMap = 51;
        static final int TRANSACTION_clearInterfaceAddresses = 6;
        static final int TRANSACTION_clearTosMap = 74;
        static final int TRANSACTION_closeSocketsForFreecess = 38;
        static final int TRANSACTION_closeSocketsForUid = 40;
        static final int TRANSACTION_closeSocketsForUids = 39;
        static final int TRANSACTION_deactivateClo = 107;
        static final int TRANSACTION_deactivateCloGro = 109;
        static final int TRANSACTION_delIpAcceptRule = 129;
        static final int TRANSACTION_delSourcePortAcceptRule = 137;
        static final int TRANSACTION_delSourceRoute = 135;
        static final int TRANSACTION_denyProtect = 42;
        static final int TRANSACTION_disableDAD = 80;
        static final int TRANSACTION_disableEpdg = 77;
        static final int TRANSACTION_disableIpv6 = 10;
        static final int TRANSACTION_disableMptcp = 133;
        static final int TRANSACTION_disableNat = 23;
        static final int TRANSACTION_enableEpdg = 76;
        static final int TRANSACTION_enableIpv6 = 11;
        static final int TRANSACTION_enableKnoxVpnFlagForTether = 54;
        static final int TRANSACTION_enableMptcp = 132;
        static final int TRANSACTION_enableNat = 22;
        static final int TRANSACTION_getInterfaceConfig = 4;
        static final int TRANSACTION_getIpForwardingEnabled = 14;
        static final int TRANSACTION_getL4sConnCount = 69;
        static final int TRANSACTION_getNetworkStatsVideoCall = 60;
        static final int TRANSACTION_getTcpLocalPorts = 75;
        static final int TRANSACTION_isBandwidthControlEnabled = 32;
        static final int TRANSACTION_isFirewallEnabled = 34;
        static final int TRANSACTION_isNetworkRestricted = 43;
        static final int TRANSACTION_isTetheringStarted = 18;
        static final int TRANSACTION_l4StatsGet = 105;
        static final int TRANSACTION_listInterfaces = 3;
        static final int TRANSACTION_listTetheredInterfaces = 21;
        static final int TRANSACTION_prioritizeApp = 61;
        static final int TRANSACTION_prioritizeMnxbApp = 101;
        static final int TRANSACTION_registerCloEventObserver = 110;
        static final int TRANSACTION_registerNetdTetherEventListener = 55;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_removeChain = 117;
        static final int TRANSACTION_removeInterfaceAlert = 27;
        static final int TRANSACTION_removeInterfaceQuota = 25;
        static final int TRANSACTION_removeLegacyRoute = 100;
        static final int TRANSACTION_removeMptcpLink = 115;
        static final int TRANSACTION_removeSocksRule = 119;
        static final int TRANSACTION_removeSocksSkipRule = 123;
        static final int TRANSACTION_removeSocksSkipRuleProto = 125;
        static final int TRANSACTION_removeTosPolicy = 73;
        static final int TRANSACTION_removeUidFromChain = 127;
        static final int TRANSACTION_removeUidSocksRule = 121;
        static final int TRANSACTION_replaceApeRule = 63;
        static final int TRANSACTION_replaceMnxbRule = 103;
        static final int TRANSACTION_runKnoxFirewallRulesCommand = 52;
        static final int TRANSACTION_runKnoxRulesCommand = 53;
        static final int TRANSACTION_setAdvertiseWindowSize = 104;
        static final int TRANSACTION_setAllowHostAlone = 85;
        static final int TRANSACTION_setAllowListIPs = 83;
        static final int TRANSACTION_setAutoConf = 98;
        static final int TRANSACTION_setBlockAllDNSPackets = 81;
        static final int TRANSACTION_setBlockAllPackets = 87;
        static final int TRANSACTION_setBlockHostAlone = 84;
        static final int TRANSACTION_setBlockListIPs = 82;
        static final int TRANSACTION_setBlockPorts = 88;
        static final int TRANSACTION_setDataSaverModeEnabled = 30;
        static final int TRANSACTION_setDestinationBasedMarkRule = 140;
        static final int TRANSACTION_setDnsForwardersForKnoxVpn = 45;
        static final int TRANSACTION_setEpdgInterfaceDropRule = 78;
        static final int TRANSACTION_setFirewallChainEnabled = 37;
        static final int TRANSACTION_setFirewallEnabled = 33;
        static final int TRANSACTION_setFirewallRuleMobileData = 96;
        static final int TRANSACTION_setFirewallRuleWifi = 95;
        static final int TRANSACTION_setFirewallUidRule = 35;
        static final int TRANSACTION_setFirewallUidRules = 36;
        static final int TRANSACTION_setIPv6AddrGenMode = 12;
        static final int TRANSACTION_setInterfaceAlert = 26;
        static final int TRANSACTION_setInterfaceConfig = 5;
        static final int TRANSACTION_setInterfaceDown = 7;
        static final int TRANSACTION_setInterfaceIpv6PrivacyExtensions = 9;
        static final int TRANSACTION_setInterfaceQuota = 24;
        static final int TRANSACTION_setInterfaceUp = 8;
        static final int TRANSACTION_setIpForwardingEnabled = 15;
        static final int TRANSACTION_setKnoxVpn = 57;
        static final int TRANSACTION_setMptcpMtuValue = 131;
        static final int TRANSACTION_setNetworkInfo = 46;
        static final int TRANSACTION_setOnlyAllowIPs = 90;
        static final int TRANSACTION_setPrivateIpRoute = 139;
        static final int TRANSACTION_setQboxUid = 66;
        static final int TRANSACTION_setTcpBufferSize = 130;
        static final int TRANSACTION_setUIDRoute = 141;
        static final int TRANSACTION_setUidCleartextNetworkPolicy = 31;
        static final int TRANSACTION_setUidOnMeteredNetworkAllowlist = 29;
        static final int TRANSACTION_setUidOnMeteredNetworkDenylist = 28;
        static final int TRANSACTION_setUrlFirewallRuleMobileData = 92;
        static final int TRANSACTION_setUrlFirewallRuleWifi = 93;
        static final int TRANSACTION_shutdown = 13;
        static final int TRANSACTION_spegRestrictNetworkConnection = 44;
        static final int TRANSACTION_startL4s = 67;
        static final int TRANSACTION_startNetworkStatsOnPorts = 58;
        static final int TRANSACTION_startQbox = 64;
        static final int TRANSACTION_startTethering = 16;
        static final int TRANSACTION_startTosMarker = 70;
        static final int TRANSACTION_stopL4s = 68;
        static final int TRANSACTION_stopNetworkStatsOnPorts = 59;
        static final int TRANSACTION_stopQbox = 65;
        static final int TRANSACTION_stopTethering = 17;
        static final int TRANSACTION_stopTosMarker = 71;
        static final int TRANSACTION_tetherInterface = 19;
        static final int TRANSACTION_unregisterCloEventObserver = 111;
        static final int TRANSACTION_unregisterNetdTetherEventListener = 56;
        static final int TRANSACTION_unregisterObserver = 2;
        static final int TRANSACTION_untetherInterface = 20;
        static final int TRANSACTION_updateDefaultGatewayForEpdg = 79;
        static final int TRANSACTION_updateGroFlushTime = 112;
        static final int TRANSACTION_updateGroPshOption = 113;
        static final int TRANSACTION_updateInputFilterAppWideRules = 50;
        static final int TRANSACTION_updateInputFilterExemptRules = 48;
        static final int TRANSACTION_updateInputFilterUserWideRules = 49;
        static final int TRANSACTION_updateSourceRule = 138;
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

        @Override // android.os.IInterface
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
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 14:
                    return "getIpForwardingEnabled";
                case 15:
                    return "setIpForwardingEnabled";
                case 16:
                    return "startTethering";
                case 17:
                    return "stopTethering";
                case 18:
                    return "isTetheringStarted";
                case 19:
                    return "tetherInterface";
                case 20:
                    return "untetherInterface";
                case 21:
                    return "listTetheredInterfaces";
                case 22:
                    return "enableNat";
                case 23:
                    return "disableNat";
                case 24:
                    return "setInterfaceQuota";
                case 25:
                    return "removeInterfaceQuota";
                case 26:
                    return "setInterfaceAlert";
                case 27:
                    return "removeInterfaceAlert";
                case 28:
                    return "setUidOnMeteredNetworkDenylist";
                case 29:
                    return "setUidOnMeteredNetworkAllowlist";
                case 30:
                    return "setDataSaverModeEnabled";
                case 31:
                    return "setUidCleartextNetworkPolicy";
                case 32:
                    return "isBandwidthControlEnabled";
                case 33:
                    return "setFirewallEnabled";
                case 34:
                    return "isFirewallEnabled";
                case 35:
                    return "setFirewallUidRule";
                case 36:
                    return "setFirewallUidRules";
                case 37:
                    return "setFirewallChainEnabled";
                case 38:
                    return "closeSocketsForFreecess";
                case 39:
                    return "closeSocketsForUids";
                case 40:
                    return "closeSocketsForUid";
                case 41:
                    return "allowProtect";
                case 42:
                    return "denyProtect";
                case 43:
                    return "isNetworkRestricted";
                case 44:
                    return "spegRestrictNetworkConnection";
                case 45:
                    return "setDnsForwardersForKnoxVpn";
                case 46:
                    return "setNetworkInfo";
                case 47:
                    return "addOrRemoveSystemAppFromDataSaverWhitelist";
                case 48:
                    return "updateInputFilterExemptRules";
                case 49:
                    return "updateInputFilterUserWideRules";
                case 50:
                    return "updateInputFilterAppWideRules";
                case 51:
                    return "clearEbpfMap";
                case 52:
                    return "runKnoxFirewallRulesCommand";
                case 53:
                    return "runKnoxRulesCommand";
                case 54:
                    return "enableKnoxVpnFlagForTether";
                case 55:
                    return "registerNetdTetherEventListener";
                case 56:
                    return "unregisterNetdTetherEventListener";
                case 57:
                    return "setKnoxVpn";
                case 58:
                    return "startNetworkStatsOnPorts";
                case 59:
                    return "stopNetworkStatsOnPorts";
                case 60:
                    return "getNetworkStatsVideoCall";
                case 61:
                    return "prioritizeApp";
                case 62:
                    return "addApeRule";
                case 63:
                    return "replaceApeRule";
                case 64:
                    return "startQbox";
                case 65:
                    return "stopQbox";
                case 66:
                    return "setQboxUid";
                case 67:
                    return "startL4s";
                case 68:
                    return "stopL4s";
                case 69:
                    return "getL4sConnCount";
                case 70:
                    return "startTosMarker";
                case 71:
                    return "stopTosMarker";
                case 72:
                    return "addTosPolicy";
                case 73:
                    return "removeTosPolicy";
                case 74:
                    return "clearTosMap";
                case 75:
                    return "getTcpLocalPorts";
                case 76:
                    return "enableEpdg";
                case 77:
                    return "disableEpdg";
                case 78:
                    return "setEpdgInterfaceDropRule";
                case 79:
                    return "updateDefaultGatewayForEpdg";
                case 80:
                    return "disableDAD";
                case 81:
                    return "setBlockAllDNSPackets";
                case 82:
                    return "setBlockListIPs";
                case 83:
                    return "setAllowListIPs";
                case 84:
                    return "setBlockHostAlone";
                case 85:
                    return "setAllowHostAlone";
                case 86:
                    return "cleanAllBlock";
                case 87:
                    return "setBlockAllPackets";
                case 88:
                    return "setBlockPorts";
                case 89:
                    return "cleanBlockPorts";
                case 90:
                    return "setOnlyAllowIPs";
                case 91:
                    return "cleanOnlyAllowIPs";
                case 92:
                    return "setUrlFirewallRuleMobileData";
                case 93:
                    return "setUrlFirewallRuleWifi";
                case 94:
                    return "buildFirewall";
                case 95:
                    return "setFirewallRuleWifi";
                case 96:
                    return "setFirewallRuleMobileData";
                case 97:
                    return "addPortFwdRules";
                case 98:
                    return "setAutoConf";
                case 99:
                    return "addLegacyRoute";
                case 100:
                    return "removeLegacyRoute";
                case 101:
                    return "prioritizeMnxbApp";
                case 102:
                    return "addMnxbRule";
                case 103:
                    return "replaceMnxbRule";
                case 104:
                    return "setAdvertiseWindowSize";
                case 105:
                    return "l4StatsGet";
                case 106:
                    return "activateClo";
                case 107:
                    return "deactivateClo";
                case 108:
                    return "activateCloGro";
                case 109:
                    return "deactivateCloGro";
                case 110:
                    return "registerCloEventObserver";
                case 111:
                    return "unregisterCloEventObserver";
                case 112:
                    return "updateGroFlushTime";
                case 113:
                    return "updateGroPshOption";
                case 114:
                    return "addMptcpLink";
                case 115:
                    return "removeMptcpLink";
                case 116:
                    return "addChain";
                case 117:
                    return "removeChain";
                case 118:
                    return "addSocksRule";
                case 119:
                    return "removeSocksRule";
                case 120:
                    return "addUidSocksRule";
                case 121:
                    return "removeUidSocksRule";
                case 122:
                    return "addSocksSkipRule";
                case 123:
                    return "removeSocksSkipRule";
                case 124:
                    return "addSocksSkipRuleProto";
                case 125:
                    return "removeSocksSkipRuleProto";
                case 126:
                    return "addUidToChain";
                case 127:
                    return "removeUidFromChain";
                case 128:
                    return "addIpAcceptRule";
                case 129:
                    return "delIpAcceptRule";
                case 130:
                    return "setTcpBufferSize";
                case 131:
                    return "setMptcpMtuValue";
                case 132:
                    return "enableMptcp";
                case 133:
                    return "disableMptcp";
                case 134:
                    return "addSourceRoute";
                case 135:
                    return "delSourceRoute";
                case 136:
                    return "addSourcePortAcceptRule";
                case 137:
                    return "delSourcePortAcceptRule";
                case 138:
                    return "updateSourceRule";
                case 139:
                    return "setPrivateIpRoute";
                case 140:
                    return "setDestinationBasedMarkRule";
                case 141:
                    return "setUIDRoute";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    INetworkManagementEventObserver _arg0 =
                            INetworkManagementEventObserver.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerObserver(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    INetworkManagementEventObserver _arg02 =
                            INetworkManagementEventObserver.Stub.asInterface(
                                    data.readStrongBinder());
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
                            (InterfaceConfiguration)
                                    data.readTypedObject(InterfaceConfiguration.CREATOR);
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
                    shutdown();
                    reply.writeNoException();
                    return true;
                case 14:
                    boolean _result3 = getIpForwardingEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 15:
                    boolean _arg012 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setIpForwardingEnabled(_arg012);
                    reply.writeNoException();
                    return true;
                case 16:
                    String[] _arg013 = data.createStringArray();
                    data.enforceNoDataAvail();
                    startTethering(_arg013);
                    reply.writeNoException();
                    return true;
                case 17:
                    stopTethering();
                    reply.writeNoException();
                    return true;
                case 18:
                    boolean _result4 = isTetheringStarted();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 19:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    tetherInterface(_arg014);
                    reply.writeNoException();
                    return true;
                case 20:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    untetherInterface(_arg015);
                    reply.writeNoException();
                    return true;
                case 21:
                    String[] _result5 = listTetheredInterfaces();
                    reply.writeNoException();
                    reply.writeStringArray(_result5);
                    return true;
                case 22:
                    String _arg016 = data.readString();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    enableNat(_arg016, _arg14);
                    reply.writeNoException();
                    return true;
                case 23:
                    String _arg017 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    disableNat(_arg017, _arg15);
                    reply.writeNoException();
                    return true;
                case 24:
                    String _arg018 = data.readString();
                    long _arg16 = data.readLong();
                    data.enforceNoDataAvail();
                    setInterfaceQuota(_arg018, _arg16);
                    reply.writeNoException();
                    return true;
                case 25:
                    String _arg019 = data.readString();
                    data.enforceNoDataAvail();
                    removeInterfaceQuota(_arg019);
                    reply.writeNoException();
                    return true;
                case 26:
                    String _arg020 = data.readString();
                    long _arg17 = data.readLong();
                    data.enforceNoDataAvail();
                    setInterfaceAlert(_arg020, _arg17);
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg021 = data.readString();
                    data.enforceNoDataAvail();
                    removeInterfaceAlert(_arg021);
                    reply.writeNoException();
                    return true;
                case 28:
                    int _arg022 = data.readInt();
                    boolean _arg18 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUidOnMeteredNetworkDenylist(_arg022, _arg18);
                    reply.writeNoException();
                    return true;
                case 29:
                    int _arg023 = data.readInt();
                    boolean _arg19 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUidOnMeteredNetworkAllowlist(_arg023, _arg19);
                    reply.writeNoException();
                    return true;
                case 30:
                    boolean _arg024 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result6 = setDataSaverModeEnabled(_arg024);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 31:
                    int _arg025 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    setUidCleartextNetworkPolicy(_arg025, _arg110);
                    reply.writeNoException();
                    return true;
                case 32:
                    boolean _result7 = isBandwidthControlEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 33:
                    boolean _arg026 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFirewallEnabled(_arg026);
                    reply.writeNoException();
                    return true;
                case 34:
                    boolean _result8 = isFirewallEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 35:
                    int _arg027 = data.readInt();
                    int _arg111 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    setFirewallUidRule(_arg027, _arg111, _arg2);
                    reply.writeNoException();
                    return true;
                case 36:
                    int _arg028 = data.readInt();
                    int[] _arg112 = data.createIntArray();
                    int[] _arg22 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setFirewallUidRules(_arg028, _arg112, _arg22);
                    reply.writeNoException();
                    return true;
                case 37:
                    int _arg029 = data.readInt();
                    boolean _arg113 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFirewallChainEnabled(_arg029, _arg113);
                    reply.writeNoException();
                    return true;
                case 38:
                    int _arg030 = data.readInt();
                    String _arg114 = data.readString();
                    data.enforceNoDataAvail();
                    closeSocketsForFreecess(_arg030, _arg114);
                    reply.writeNoException();
                    return true;
                case 39:
                    int[] _arg031 = data.createIntArray();
                    data.enforceNoDataAvail();
                    closeSocketsForUids(_arg031);
                    reply.writeNoException();
                    return true;
                case 40:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    closeSocketsForUid(_arg032);
                    reply.writeNoException();
                    return true;
                case 41:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    allowProtect(_arg033);
                    reply.writeNoException();
                    return true;
                case 42:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    denyProtect(_arg034);
                    reply.writeNoException();
                    return true;
                case 43:
                    int _arg035 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = isNetworkRestricted(_arg035);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 44:
                    int _arg036 = data.readInt();
                    boolean _arg115 = data.readBoolean();
                    data.enforceNoDataAvail();
                    spegRestrictNetworkConnection(_arg036, _arg115);
                    reply.writeNoException();
                    return true;
                case 45:
                    int _arg037 = data.readInt();
                    String[] _arg116 = data.createStringArray();
                    data.enforceNoDataAvail();
                    setDnsForwardersForKnoxVpn(_arg037, _arg116);
                    reply.writeNoException();
                    return true;
                case 46:
                    int _arg038 = data.readInt();
                    boolean _arg117 = data.readBoolean();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    setNetworkInfo(_arg038, _arg117, _arg23);
                    reply.writeNoException();
                    return true;
                case 47:
                    boolean _arg039 = data.readBoolean();
                    int _arg118 = data.readInt();
                    data.enforceNoDataAvail();
                    addOrRemoveSystemAppFromDataSaverWhitelist(_arg039, _arg118);
                    reply.writeNoException();
                    return true;
                case 48:
                    int _arg040 = data.readInt();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    updateInputFilterExemptRules(_arg040, _arg119);
                    reply.writeNoException();
                    return true;
                case 49:
                    int[] _arg041 = data.createIntArray();
                    int _arg120 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    updateInputFilterUserWideRules(_arg041, _arg120, _arg24);
                    reply.writeNoException();
                    return true;
                case 50:
                    int[] _arg042 = data.createIntArray();
                    int _arg121 = data.readInt();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    updateInputFilterAppWideRules(_arg042, _arg121, _arg25);
                    reply.writeNoException();
                    return true;
                case 51:
                    int _arg043 = data.readInt();
                    data.enforceNoDataAvail();
                    clearEbpfMap(_arg043);
                    reply.writeNoException();
                    return true;
                case 52:
                    int _arg044 = data.readInt();
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    String _result10 = runKnoxFirewallRulesCommand(_arg044, _arg122);
                    reply.writeNoException();
                    reply.writeString(_result10);
                    return true;
                case 53:
                    int _arg045 = data.readInt();
                    String[] _arg123 = data.createStringArray();
                    data.enforceNoDataAvail();
                    runKnoxRulesCommand(_arg045, _arg123);
                    reply.writeNoException();
                    return true;
                case 54:
                    boolean _arg046 = data.readBoolean();
                    data.enforceNoDataAvail();
                    enableKnoxVpnFlagForTether(_arg046);
                    reply.writeNoException();
                    return true;
                case 55:
                    registerNetdTetherEventListener();
                    reply.writeNoException();
                    return true;
                case 56:
                    unregisterNetdTetherEventListener();
                    reply.writeNoException();
                    return true;
                case 57:
                    int _arg047 = data.readInt();
                    boolean _arg124 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setKnoxVpn(_arg047, _arg124);
                    reply.writeNoException();
                    return true;
                case 58:
                    String _arg048 = data.readString();
                    int _arg125 = data.readInt();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    startNetworkStatsOnPorts(_arg048, _arg125, _arg26);
                    reply.writeNoException();
                    return true;
                case 59:
                    String _arg049 = data.readString();
                    int _arg126 = data.readInt();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    stopNetworkStatsOnPorts(_arg049, _arg126, _arg27);
                    reply.writeNoException();
                    return true;
                case 60:
                    String _arg050 = data.readString();
                    int _arg127 = data.readInt();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result11 = getNetworkStatsVideoCall(_arg050, _arg127, _arg28);
                    reply.writeNoException();
                    reply.writeLong(_result11);
                    return true;
                case 61:
                    boolean _arg051 = data.readBoolean();
                    int _arg128 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result12 = prioritizeApp(_arg051, _arg128);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 62:
                    boolean _arg052 = data.readBoolean();
                    String _arg129 = data.readString();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result13 = addApeRule(_arg052, _arg129, _arg29);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 63:
                    String _arg053 = data.readString();
                    int _arg130 = data.readInt();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result14 = replaceApeRule(_arg053, _arg130, _arg210);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 64:
                    String _arg054 = data.readString();
                    data.enforceNoDataAvail();
                    startQbox(_arg054);
                    reply.writeNoException();
                    return true;
                case 65:
                    stopQbox();
                    reply.writeNoException();
                    return true;
                case 66:
                    int _arg055 = data.readInt();
                    boolean _arg131 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setQboxUid(_arg055, _arg131);
                    reply.writeNoException();
                    return true;
                case 67:
                    String _arg056 = data.readString();
                    data.enforceNoDataAvail();
                    int _result15 = startL4s(_arg056);
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 68:
                    String _arg057 = data.readString();
                    data.enforceNoDataAvail();
                    int _result16 = stopL4s(_arg057);
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 69:
                    int _result17 = getL4sConnCount();
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 70:
                    String _arg058 = data.readString();
                    data.enforceNoDataAvail();
                    startTosMarker(_arg058);
                    reply.writeNoException();
                    return true;
                case 71:
                    String _arg059 = data.readString();
                    data.enforceNoDataAvail();
                    stopTosMarker(_arg059);
                    reply.writeNoException();
                    return true;
                case 72:
                    int _arg060 = data.readInt();
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    addTosPolicy(_arg060, _arg132);
                    reply.writeNoException();
                    return true;
                case 73:
                    int _arg061 = data.readInt();
                    data.enforceNoDataAvail();
                    removeTosPolicy(_arg061);
                    reply.writeNoException();
                    return true;
                case 74:
                    clearTosMap();
                    reply.writeNoException();
                    return true;
                case 75:
                    int[] _arg062 = data.createIntArray();
                    data.enforceNoDataAvail();
                    int[] _result18 = getTcpLocalPorts(_arg062);
                    reply.writeNoException();
                    reply.writeIntArray(_result18);
                    return true;
                case 76:
                    String _arg063 = data.readString();
                    String _arg133 = data.readString();
                    boolean _arg211 = data.readBoolean();
                    data.enforceNoDataAvail();
                    enableEpdg(_arg063, _arg133, _arg211);
                    reply.writeNoException();
                    return true;
                case 77:
                    String _arg064 = data.readString();
                    String _arg134 = data.readString();
                    data.enforceNoDataAvail();
                    disableEpdg(_arg064, _arg134);
                    reply.writeNoException();
                    return true;
                case 78:
                    String _arg065 = data.readString();
                    String _arg135 = data.readString();
                    boolean _arg212 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setEpdgInterfaceDropRule(_arg065, _arg135, _arg212);
                    reply.writeNoException();
                    return true;
                case 79:
                    Network _arg066 = (Network) data.readTypedObject(Network.CREATOR);
                    data.enforceNoDataAvail();
                    updateDefaultGatewayForEpdg(_arg066);
                    reply.writeNoException();
                    return true;
                case 80:
                    String _arg067 = data.readString();
                    data.enforceNoDataAvail();
                    disableDAD(_arg067);
                    reply.writeNoException();
                    return true;
                case 81:
                    boolean _arg068 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBlockAllDNSPackets(_arg068);
                    reply.writeNoException();
                    return true;
                case 82:
                    String _arg069 = data.readString();
                    data.enforceNoDataAvail();
                    setBlockListIPs(_arg069);
                    reply.writeNoException();
                    return true;
                case 83:
                    String _arg070 = data.readString();
                    data.enforceNoDataAvail();
                    setAllowListIPs(_arg070);
                    reply.writeNoException();
                    return true;
                case 84:
                    String _arg071 = data.readString();
                    data.enforceNoDataAvail();
                    setBlockHostAlone(_arg071);
                    reply.writeNoException();
                    return true;
                case 85:
                    String _arg072 = data.readString();
                    data.enforceNoDataAvail();
                    setAllowHostAlone(_arg072);
                    reply.writeNoException();
                    return true;
                case 86:
                    cleanAllBlock();
                    reply.writeNoException();
                    return true;
                case 87:
                    setBlockAllPackets();
                    reply.writeNoException();
                    return true;
                case 88:
                    String _arg073 = data.readString();
                    int _arg136 = data.readInt();
                    String _arg213 = data.readString();
                    data.enforceNoDataAvail();
                    setBlockPorts(_arg073, _arg136, _arg213);
                    reply.writeNoException();
                    return true;
                case 89:
                    cleanBlockPorts();
                    reply.writeNoException();
                    return true;
                case 90:
                    String _arg074 = data.readString();
                    data.enforceNoDataAvail();
                    setOnlyAllowIPs(_arg074);
                    reply.writeNoException();
                    return true;
                case 91:
                    cleanOnlyAllowIPs();
                    reply.writeNoException();
                    return true;
                case 92:
                    int _arg075 = data.readInt();
                    String _arg137 = data.readString();
                    boolean _arg214 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUrlFirewallRuleMobileData(_arg075, _arg137, _arg214);
                    reply.writeNoException();
                    return true;
                case 93:
                    int _arg076 = data.readInt();
                    String _arg138 = data.readString();
                    boolean _arg215 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUrlFirewallRuleWifi(_arg076, _arg138, _arg215);
                    reply.writeNoException();
                    return true;
                case 94:
                    buildFirewall();
                    reply.writeNoException();
                    return true;
                case 95:
                    int _arg077 = data.readInt();
                    boolean _arg139 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFirewallRuleWifi(_arg077, _arg139);
                    reply.writeNoException();
                    return true;
                case 96:
                    int _arg078 = data.readInt();
                    boolean _arg140 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFirewallRuleMobileData(_arg078, _arg140);
                    reply.writeNoException();
                    return true;
                case 97:
                    String _arg079 = data.readString();
                    String _arg141 = data.readString();
                    String _arg216 = data.readString();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    addPortFwdRules(_arg079, _arg141, _arg216, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 98:
                    String _arg080 = data.readString();
                    boolean _arg142 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAutoConf(_arg080, _arg142);
                    reply.writeNoException();
                    return true;
                case 99:
                    int _arg081 = data.readInt();
                    String _arg143 = data.readString();
                    String _arg217 = data.readString();
                    String _arg32 = data.readString();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    addLegacyRoute(_arg081, _arg143, _arg217, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 100:
                    int _arg082 = data.readInt();
                    String _arg144 = data.readString();
                    String _arg218 = data.readString();
                    String _arg33 = data.readString();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    removeLegacyRoute(_arg082, _arg144, _arg218, _arg33, _arg43);
                    reply.writeNoException();
                    return true;
                case 101:
                    boolean _arg083 = data.readBoolean();
                    int _arg145 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result19 = prioritizeMnxbApp(_arg083, _arg145);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 102:
                    boolean _arg084 = data.readBoolean();
                    String _arg146 = data.readString();
                    int _arg219 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result20 = addMnxbRule(_arg084, _arg146, _arg219);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 103:
                    String _arg085 = data.readString();
                    int _arg147 = data.readInt();
                    int _arg220 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result21 = replaceMnxbRule(_arg085, _arg147, _arg220);
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 104:
                    int _arg086 = data.readInt();
                    data.enforceNoDataAvail();
                    setAdvertiseWindowSize(_arg086);
                    reply.writeNoException();
                    return true;
                case 105:
                    long[] _result22 = l4StatsGet();
                    reply.writeNoException();
                    reply.writeLongArray(_result22);
                    return true;
                case 106:
                    String _arg087 = data.readString();
                    data.enforceNoDataAvail();
                    activateClo(_arg087);
                    reply.writeNoException();
                    return true;
                case 107:
                    String _arg088 = data.readString();
                    data.enforceNoDataAvail();
                    deactivateClo(_arg088);
                    reply.writeNoException();
                    return true;
                case 108:
                    activateCloGro();
                    reply.writeNoException();
                    return true;
                case 109:
                    deactivateCloGro();
                    reply.writeNoException();
                    return true;
                case 110:
                    ICloEventObserver _arg089 =
                            ICloEventObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCloEventObserver(_arg089);
                    reply.writeNoException();
                    return true;
                case 111:
                    unregisterCloEventObserver();
                    reply.writeNoException();
                    return true;
                case 112:
                    long _arg090 = data.readLong();
                    data.enforceNoDataAvail();
                    updateGroFlushTime(_arg090);
                    reply.writeNoException();
                    return true;
                case 113:
                    int _arg091 = data.readInt();
                    data.enforceNoDataAvail();
                    updateGroPshOption(_arg091);
                    reply.writeNoException();
                    return true;
                case 114:
                    String _arg092 = data.readString();
                    data.enforceNoDataAvail();
                    addMptcpLink(_arg092);
                    reply.writeNoException();
                    return true;
                case 115:
                    String _arg093 = data.readString();
                    data.enforceNoDataAvail();
                    removeMptcpLink(_arg093);
                    reply.writeNoException();
                    return true;
                case 116:
                    String _arg094 = data.readString();
                    String _arg148 = data.readString();
                    data.enforceNoDataAvail();
                    addChain(_arg094, _arg148);
                    reply.writeNoException();
                    return true;
                case 117:
                    String _arg095 = data.readString();
                    String _arg149 = data.readString();
                    data.enforceNoDataAvail();
                    removeChain(_arg095, _arg149);
                    reply.writeNoException();
                    return true;
                case 118:
                    String _arg096 = data.readString();
                    String _arg150 = data.readString();
                    String _arg221 = data.readString();
                    int _arg34 = data.readInt();
                    String _arg44 = data.readString();
                    data.enforceNoDataAvail();
                    addSocksRule(_arg096, _arg150, _arg221, _arg34, _arg44);
                    reply.writeNoException();
                    return true;
                case 119:
                    String _arg097 = data.readString();
                    String _arg151 = data.readString();
                    String _arg222 = data.readString();
                    int _arg35 = data.readInt();
                    String _arg45 = data.readString();
                    data.enforceNoDataAvail();
                    removeSocksRule(_arg097, _arg151, _arg222, _arg35, _arg45);
                    reply.writeNoException();
                    return true;
                case 120:
                    String _arg098 = data.readString();
                    String _arg152 = data.readString();
                    String _arg223 = data.readString();
                    int _arg36 = data.readInt();
                    int _arg46 = data.readInt();
                    String _arg5 = data.readString();
                    data.enforceNoDataAvail();
                    addUidSocksRule(_arg098, _arg152, _arg223, _arg36, _arg46, _arg5);
                    reply.writeNoException();
                    return true;
                case 121:
                    String _arg099 = data.readString();
                    String _arg153 = data.readString();
                    String _arg224 = data.readString();
                    int _arg37 = data.readInt();
                    int _arg47 = data.readInt();
                    String _arg52 = data.readString();
                    data.enforceNoDataAvail();
                    removeUidSocksRule(_arg099, _arg153, _arg224, _arg37, _arg47, _arg52);
                    reply.writeNoException();
                    return true;
                case 122:
                    String _arg0100 = data.readString();
                    String _arg154 = data.readString();
                    String _arg225 = data.readString();
                    data.enforceNoDataAvail();
                    addSocksSkipRule(_arg0100, _arg154, _arg225);
                    reply.writeNoException();
                    return true;
                case 123:
                    String _arg0101 = data.readString();
                    String _arg155 = data.readString();
                    String _arg226 = data.readString();
                    data.enforceNoDataAvail();
                    removeSocksSkipRule(_arg0101, _arg155, _arg226);
                    reply.writeNoException();
                    return true;
                case 124:
                    String _arg0102 = data.readString();
                    String _arg156 = data.readString();
                    String _arg227 = data.readString();
                    int _arg38 = data.readInt();
                    String _arg48 = data.readString();
                    data.enforceNoDataAvail();
                    addSocksSkipRuleProto(_arg0102, _arg156, _arg227, _arg38, _arg48);
                    reply.writeNoException();
                    return true;
                case 125:
                    String _arg0103 = data.readString();
                    String _arg157 = data.readString();
                    String _arg228 = data.readString();
                    int _arg39 = data.readInt();
                    String _arg49 = data.readString();
                    data.enforceNoDataAvail();
                    removeSocksSkipRuleProto(_arg0103, _arg157, _arg228, _arg39, _arg49);
                    reply.writeNoException();
                    return true;
                case 126:
                    String _arg0104 = data.readString();
                    String _arg158 = data.readString();
                    int _arg229 = data.readInt();
                    data.enforceNoDataAvail();
                    addUidToChain(_arg0104, _arg158, _arg229);
                    reply.writeNoException();
                    return true;
                case 127:
                    String _arg0105 = data.readString();
                    String _arg159 = data.readString();
                    int _arg230 = data.readInt();
                    data.enforceNoDataAvail();
                    removeUidFromChain(_arg0105, _arg159, _arg230);
                    reply.writeNoException();
                    return true;
                case 128:
                    String _arg0106 = data.readString();
                    String _arg160 = data.readString();
                    String _arg231 = data.readString();
                    data.enforceNoDataAvail();
                    addIpAcceptRule(_arg0106, _arg160, _arg231);
                    reply.writeNoException();
                    return true;
                case 129:
                    String _arg0107 = data.readString();
                    String _arg161 = data.readString();
                    String _arg232 = data.readString();
                    data.enforceNoDataAvail();
                    delIpAcceptRule(_arg0107, _arg161, _arg232);
                    reply.writeNoException();
                    return true;
                case 130:
                    String _arg0108 = data.readString();
                    String _arg162 = data.readString();
                    data.enforceNoDataAvail();
                    setTcpBufferSize(_arg0108, _arg162);
                    reply.writeNoException();
                    return true;
                case 131:
                    String _arg0109 = data.readString();
                    int _arg163 = data.readInt();
                    data.enforceNoDataAvail();
                    setMptcpMtuValue(_arg0109, _arg163);
                    reply.writeNoException();
                    return true;
                case 132:
                    String _arg0110 = data.readString();
                    data.enforceNoDataAvail();
                    enableMptcp(_arg0110);
                    reply.writeNoException();
                    return true;
                case 133:
                    disableMptcp();
                    reply.writeNoException();
                    return true;
                case 134:
                    String _arg0111 = data.readString();
                    String _arg164 = data.readString();
                    String _arg233 = data.readString();
                    data.enforceNoDataAvail();
                    addSourceRoute(_arg0111, _arg164, _arg233);
                    reply.writeNoException();
                    return true;
                case 135:
                    String _arg0112 = data.readString();
                    String _arg165 = data.readString();
                    String _arg234 = data.readString();
                    data.enforceNoDataAvail();
                    delSourceRoute(_arg0112, _arg165, _arg234);
                    reply.writeNoException();
                    return true;
                case 136:
                    String _arg0113 = data.readString();
                    String _arg166 = data.readString();
                    int _arg235 = data.readInt();
                    data.enforceNoDataAvail();
                    addSourcePortAcceptRule(_arg0113, _arg166, _arg235);
                    reply.writeNoException();
                    return true;
                case 137:
                    String _arg0114 = data.readString();
                    String _arg167 = data.readString();
                    int _arg236 = data.readInt();
                    data.enforceNoDataAvail();
                    delSourcePortAcceptRule(_arg0114, _arg167, _arg236);
                    reply.writeNoException();
                    return true;
                case 138:
                    boolean _arg0115 = data.readBoolean();
                    String _arg168 = data.readString();
                    String _arg237 = data.readString();
                    data.enforceNoDataAvail();
                    updateSourceRule(_arg0115, _arg168, _arg237);
                    reply.writeNoException();
                    return true;
                case 139:
                    boolean _arg0116 = data.readBoolean();
                    String _arg169 = data.readString();
                    int _arg238 = data.readInt();
                    data.enforceNoDataAvail();
                    setPrivateIpRoute(_arg0116, _arg169, _arg238);
                    reply.writeNoException();
                    return true;
                case 140:
                    boolean _arg0117 = data.readBoolean();
                    String _arg170 = data.readString();
                    String _arg239 = data.readString();
                    int _arg310 = data.readInt();
                    int _arg410 = data.readInt();
                    data.enforceNoDataAvail();
                    setDestinationBasedMarkRule(_arg0117, _arg170, _arg239, _arg310, _arg410);
                    reply.writeNoException();
                    return true;
                case 141:
                    boolean _arg0118 = data.readBoolean();
                    String _arg171 = data.readString();
                    int _arg240 = data.readInt();
                    String _arg311 = data.readString();
                    String _arg411 = data.readString();
                    data.enforceNoDataAvail();
                    setUIDRoute(_arg0118, _arg171, _arg240, _arg311, _arg411);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INetworkManagementService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.INetworkManagementService
            public void registerObserver(INetworkManagementEventObserver obs)
                    throws RemoteException {
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

            @Override // android.os.INetworkManagementService
            public void unregisterObserver(INetworkManagementEventObserver obs)
                    throws RemoteException {
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
            public InterfaceConfiguration getInterfaceConfig(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    InterfaceConfiguration _result =
                            (InterfaceConfiguration)
                                    _reply.readTypedObject(InterfaceConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
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

            @Override // android.os.INetworkManagementService
            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean getIpForwardingEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setIpForwardingEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startTethering(String[] dhcpRanges) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(dhcpRanges);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopTethering() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isTetheringStarted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void tetherInterface(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void untetherInterface(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public String[] listTetheredInterfaces() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableNat(String internalInterface, String externalInterface)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(internalInterface);
                    _data.writeString(externalInterface);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableNat(String internalInterface, String externalInterface)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(internalInterface);
                    _data.writeString(externalInterface);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceQuota(String iface, long quotaBytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(quotaBytes);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceQuota(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceAlert(String iface, long alertBytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeLong(alertBytes);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceAlert(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkDenylist(int uid, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkAllowlist(int uid, boolean enable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean setDataSaverModeEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidCleartextNetworkPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isBandwidthControlEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isFirewallEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRule(int chain, int uid, int rule) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeInt(uid);
                    _data.writeInt(rule);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRules(int chain, int[] uids, int[] rules)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeIntArray(uids);
                    _data.writeIntArray(rules);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallChainEnabled(int chain, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForFreecess(int chain, String chainName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(chain);
                    _data.writeString(chainName);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForUids(int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void allowProtect(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void denyProtect(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isNetworkRestricted(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void spegRestrictNetworkConnection(int uid, boolean restrict)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(restrict);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setDnsForwardersForKnoxVpn(int netId, String[] dns) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeStringArray(dns);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setNetworkInfo(int netId, boolean chainedNetwork, int vpnClientUid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeBoolean(chainedNetwork);
                    _data.writeInt(vpnClientUid);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean enable, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(uid);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterExemptRules(int uid, int update) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(update);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterUserWideRules(int[] userIds, int ifaceIndex, int update)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(userIds);
                    _data.writeInt(ifaceIndex);
                    _data.writeInt(update);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterAppWideRules(int[] uids, int ifaceIndex, int update)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    _data.writeInt(ifaceIndex);
                    _data.writeInt(update);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearEbpfMap(int mapId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mapId);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public String runKnoxFirewallRulesCommand(int target, String cmd)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(target);
                    _data.writeString(cmd);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void runKnoxRulesCommand(int cmd, String[] params) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeStringArray(params);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableKnoxVpnFlagForTether(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void registerNetdTetherEventListener() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterNetdTetherEventListener() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setKnoxVpn(int netId, boolean isKnoxVpn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(netId);
                    _data.writeBoolean(isKnoxVpn);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startNetworkStatsOnPorts(String iface, int inport, int outport)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(inport);
                    _data.writeInt(outport);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopNetworkStatsOnPorts(String iface, int inport, int outport)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(inport);
                    _data.writeInt(outport);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public long getNetworkStatsVideoCall(String iface, int sport, int dport)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(sport);
                    _data.writeInt(dport);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int prioritizeApp(boolean add, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeInt(uid);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int addApeRule(boolean add, String infName, int bandwidthMbps)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeString(infName);
                    _data.writeInt(bandwidthMbps);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int replaceApeRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(infName);
                    _data.writeInt(oldBandwidthMbps);
                    _data.writeInt(newBandwidthMbps);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startQbox(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopQbox() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setQboxUid(int uid, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int startL4s(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int stopL4s(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int getL4sConnCount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startTosMarker(String ifname) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifname);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopTosMarker(String ifname) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifname);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addTosPolicy(int uid, int tosValue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(tosValue);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeTosPolicy(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearTosMap() throws RemoteException {
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

            @Override // android.os.INetworkManagementService
            public int[] getTcpLocalPorts(int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableEpdg(
                    String mobileInterface, String tunnelingInterface, boolean deleteSkip)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mobileInterface);
                    _data.writeString(tunnelingInterface);
                    _data.writeBoolean(deleteSkip);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableEpdg(String mobileInterface, String tunnelingInterface)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mobileInterface);
                    _data.writeString(tunnelingInterface);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setEpdgInterfaceDropRule(String iface, String src, boolean add)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(src);
                    _data.writeBoolean(add);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(network, 0);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableDAD(String ifName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifName);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockAllDNSPackets(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockListIPs(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAllowListIPs(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockHostAlone(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAllowHostAlone(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanAllBlock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockAllPackets() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockPorts(String protocol, int directionBitMask, String ports)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(protocol);
                    _data.writeInt(directionBitMask);
                    _data.writeString(ports);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanBlockPorts() throws RemoteException {
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

            @Override // android.os.INetworkManagementService
            public void setOnlyAllowIPs(String addr) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(addr);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanOnlyAllowIPs() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUrlFirewallRuleMobileData(int uid, String url, boolean allow)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(url);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUrlFirewallRuleWifi(int uid, String url, boolean allow)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(url);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void buildFirewall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallRuleWifi(int uid, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallRuleMobileData(int uid, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAutoConf(String iface, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int prioritizeMnxbApp(boolean add, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeInt(uid);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int addMnxbRule(boolean add, String infName, int bandwidthMbps)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeString(infName);
                    _data.writeInt(bandwidthMbps);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int replaceMnxbRule(String infName, int oldBandwidthMbps, int newBandwidthMbps)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(infName);
                    _data.writeInt(oldBandwidthMbps);
                    _data.writeInt(newBandwidthMbps);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAdvertiseWindowSize(int newAdvertiseWindow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newAdvertiseWindow);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public long[] l4StatsGet() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateClo(String ifaceName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateClo(String ifaceName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ifaceName);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateCloGro() throws RemoteException {
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

            @Override // android.os.INetworkManagementService
            public void deactivateCloGro() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void registerCloEventObserver(ICloEventObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterCloEventObserver() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateGroFlushTime(long flushTime) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(flushTime);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateGroPshOption(int pshOption) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pshOption);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addMptcpLink(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeMptcpLink(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addChain(String chain, String ip_type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(ip_type);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeChain(String chain, String ip_type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(ip_type);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksRule(
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
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksSkipRule(String chain, String addr, String ip_type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(addr);
                    _data.writeString(ip_type);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksSkipRule(String chain, String addr, String ip_type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(addr);
                    _data.writeString(ip_type);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(125, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addUidToChain(String chain, String proto, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(proto);
                    _data.writeInt(uid);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeUidFromChain(String chain, String proto, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(proto);
                    _data.writeInt(uid);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addIpAcceptRule(String chain, String dest, String proto)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(dest);
                    _data.writeString(proto);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delIpAcceptRule(String chain, String dest, String proto)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(dest);
                    _data.writeString(proto);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setTcpBufferSize(String rmem, String wmem) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rmem);
                    _data.writeString(wmem);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setMptcpMtuValue(String iface, int mtu) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(mtu);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableMptcp(String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(value);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableMptcp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(133, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSourceRoute(String iface, String addr, String gateway)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(addr);
                    _data.writeString(gateway);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delSourceRoute(String iface, String addr, String gateway)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeString(addr);
                    _data.writeString(gateway);
                    this.mRemote.transact(135, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSourcePortAcceptRule(String chain, String proto, int port)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(proto);
                    _data.writeInt(port);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delSourcePortAcceptRule(String chain, String proto, int port)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(chain);
                    _data.writeString(proto);
                    _data.writeInt(port);
                    this.mRemote.transact(137, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateSourceRule(boolean add, String ipAddr, String ifaceName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeString(ipAddr);
                    _data.writeString(ifaceName);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setPrivateIpRoute(boolean add, String ifaceName, int mark)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeString(ifaceName);
                    _data.writeInt(mark);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setDestinationBasedMarkRule(
                    boolean add, String addr, String outInterface, int mark, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(add);
                    _data.writeString(addr);
                    _data.writeString(outInterface);
                    _data.writeInt(mark);
                    _data.writeInt(uid);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
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
                    this.mRemote.transact(141, _data, _reply, 0);
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

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 140;
        }
    }
}
