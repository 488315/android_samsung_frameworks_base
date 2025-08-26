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

    public static class Default implements INetworkManagementService {
        @Override // android.os.INetworkManagementService
        public void activateClo(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void activateCloGro() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void activateCloHo(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public int addApeRule(boolean z, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void addChain(String str, String str2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addIpAcceptRule(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public int addMnxbRule(boolean z, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void addMptcpLink(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addPortFwdRules(String str, String str2, String str3, String str4, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addSocksSkipRule(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addSourcePortAcceptRule(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addSourceRoute(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addTosPolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void addUidToChain(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void allowProtect(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void buildFirewall() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void cleanAllBlock() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void cleanBlockPorts() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void cleanOnlyAllowIPs() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void clearEbpfMap(int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void clearInterfaceAddresses(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void clearTosMap() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void closeSocketsForFreecess(int i, String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void closeSocketsForUid(int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void closeSocketsForUids(int[] iArr) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void deactivateClo(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void deactivateCloGro() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void deactivateCloHo() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void delIpAcceptRule(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void delSourcePortAcceptRule(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void delSourceRoute(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void denyProtect(int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void disableDAD(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void disableEpdg(String str, String str2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void disableIpv6(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void disableMptcp() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void enableEpdg(String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void enableIpv6(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void enableKnoxVpnFlagForTether(boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void enableMptcp(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public int[] getDeviceInfo() throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public int getL4sConnCount() throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public long getNetworkStatsVideoCall(String str, int i, int i2) throws RemoteException {
            return 0L;
        }

        @Override // android.os.INetworkManagementService
        public int[] getTcpLocalPorts(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public boolean isBandwidthControlEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public boolean isFirewallEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public boolean isNetworkRestricted(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public long[] l4StatsGet() throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public String[] listInterfaces() throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public int prioritizeApp(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int prioritizeMnxbApp(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void registerCloEventObserver(ICloEventObserver iCloEventObserver) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void registerNetdTetherEventListener() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeChain(String str, String str2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeInterfaceAlert(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeInterfaceQuota(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeMptcpLink(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeSocksSkipRule(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeTosPolicy(int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeUidFromChain(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public int replaceApeRule(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public int replaceMnxbRule(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void runKnoxRulesCommand(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setAdvertiseWindowSize(int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setAllowHostAlone(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setAllowListIPs(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setAutoConf(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setBlockAllDNSPackets(boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setBlockAllPackets() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setBlockHostAlone(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setBlockListIPs(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setBlockPorts(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean setDataSaverModeEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setDestinationBasedMarkRule(boolean z, String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setDnsForwardersForKnoxVpn(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setEpdgInterfaceDropRule(String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallChainEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallEnabled(boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallRuleMobileData(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallRuleWifi(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallUidRule(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setIPv6AddrGenMode(String str, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceAlert(String str, long j) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceDown(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceQuota(String str, long j) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceUp(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setKnoxVpn(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setMptcpMtuValue(String str, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setNetworkInfo(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setOnlyAllowIPs(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setPrivateIpRoute(boolean z, String str, int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setQboxUid(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setTcpBufferSize(String str, String str2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUIDRoute(boolean z, String str, int i, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUidCleartextNetworkPolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUidOnMeteredNetworkDenylist(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUrlFirewallRuleMobileData(int i, String str, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUrlFirewallRuleWifi(int i, String str, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void shutdown() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public int startL4s(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void startNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void startQbox(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void startTosMarker(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public int stopL4s(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.INetworkManagementService
        public void stopNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void stopQbox() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void stopTosMarker(String str) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void unregisterCloEventObserver() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void unregisterNetdTetherEventListener() throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateGroFlushTime(long j) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateGroPshOption(int i) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateInputFilterExemptRules(int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void updateSourceRule(boolean z, String str, String str2) throws RemoteException {
        }
    }

    void activateClo(String str) throws RemoteException;

    void activateCloGro() throws RemoteException;

    void activateCloHo(int i, boolean z) throws RemoteException;

    int addApeRule(boolean z, String str, int i) throws RemoteException;

    void addChain(String str, String str2) throws RemoteException;

    void addIpAcceptRule(String str, String str2, String str3) throws RemoteException;

    void addLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException;

    int addMnxbRule(boolean z, String str, int i) throws RemoteException;

    void addMptcpLink(String str) throws RemoteException;

    void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) throws RemoteException;

    void addPortFwdRules(String str, String str2, String str3, String str4, int i) throws RemoteException;

    void addSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException;

    void addSocksSkipRule(String str, String str2, String str3) throws RemoteException;

    void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException;

    void addSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

    void addSourceRoute(String str, String str2, String str3) throws RemoteException;

    void addTosPolicy(int i, int i2) throws RemoteException;

    void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException;

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

    void deactivateCloHo() throws RemoteException;

    void delIpAcceptRule(String str, String str2, String str3) throws RemoteException;

    void delSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

    void delSourceRoute(String str, String str2, String str3) throws RemoteException;

    void denyProtect(int i) throws RemoteException;

    void disableDAD(String str) throws RemoteException;

    void disableEpdg(String str, String str2) throws RemoteException;

    void disableIpv6(String str) throws RemoteException;

    void disableMptcp() throws RemoteException;

    void enableEpdg(String str, String str2, boolean z) throws RemoteException;

    void enableIpv6(String str) throws RemoteException;

    void enableKnoxVpnFlagForTether(boolean z) throws RemoteException;

    void enableMptcp(String str) throws RemoteException;

    int[] getDeviceInfo() throws RemoteException;

    InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException;

    int getL4sConnCount() throws RemoteException;

    long getNetworkStatsVideoCall(String str, int i, int i2) throws RemoteException;

    int[] getTcpLocalPorts(int[] iArr) throws RemoteException;

    boolean isBandwidthControlEnabled() throws RemoteException;

    boolean isFirewallEnabled() throws RemoteException;

    boolean isNetworkRestricted(int i) throws RemoteException;

    long[] l4StatsGet() throws RemoteException;

    String[] listInterfaces() throws RemoteException;

    int prioritizeApp(boolean z, int i) throws RemoteException;

    int prioritizeMnxbApp(boolean z, int i) throws RemoteException;

    void registerCloEventObserver(ICloEventObserver iCloEventObserver) throws RemoteException;

    void registerNetdTetherEventListener() throws RemoteException;

    void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException;

    void removeChain(String str, String str2) throws RemoteException;

    void removeInterfaceAlert(String str) throws RemoteException;

    void removeInterfaceQuota(String str) throws RemoteException;

    void removeLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException;

    void removeMptcpLink(String str) throws RemoteException;

    void removeSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException;

    void removeSocksSkipRule(String str, String str2, String str3) throws RemoteException;

    void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException;

    void removeTosPolicy(int i) throws RemoteException;

    void removeUidFromChain(String str, String str2, int i) throws RemoteException;

    void removeUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException;

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

    void setDestinationBasedMarkRule(boolean z, String str, String str2, int i, int i2) throws RemoteException;

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

    void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration) throws RemoteException;

    void setInterfaceDown(String str) throws RemoteException;

    void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException;

    void setInterfaceQuota(String str, long j) throws RemoteException;

    void setInterfaceUp(String str) throws RemoteException;

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

    void startTosMarker(String str) throws RemoteException;

    int stopL4s(String str) throws RemoteException;

    void stopNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException;

    void stopQbox() throws RemoteException;

    void stopTosMarker(String str) throws RemoteException;

    void unregisterCloEventObserver() throws RemoteException;

    void unregisterNetdTetherEventListener() throws RemoteException;

    void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException;

    void updateDefaultGatewayForEpdg(Network network) throws RemoteException;

    void updateGroFlushTime(long j) throws RemoteException;

    void updateGroPshOption(int i) throws RemoteException;

    void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException;

    void updateInputFilterExemptRules(int i, int i2) throws RemoteException;

    void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException;

    void updateSourceRule(boolean z, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkManagementService {
        public static final String DESCRIPTOR = "android.os.INetworkManagementService";
        static final int TRANSACTION_activateClo = 96;
        static final int TRANSACTION_activateCloGro = 98;
        static final int TRANSACTION_activateCloHo = 104;
        static final int TRANSACTION_addApeRule = 52;
        static final int TRANSACTION_addChain = 109;
        static final int TRANSACTION_addIpAcceptRule = 121;
        static final int TRANSACTION_addLegacyRoute = 89;
        static final int TRANSACTION_addMnxbRule = 92;
        static final int TRANSACTION_addMptcpLink = 107;
        static final int TRANSACTION_addOrRemoveSystemAppFromDataSaverWhitelist = 37;
        static final int TRANSACTION_addPortFwdRules = 87;
        static final int TRANSACTION_addSocksRule = 111;
        static final int TRANSACTION_addSocksSkipRule = 115;
        static final int TRANSACTION_addSocksSkipRuleProto = 117;
        static final int TRANSACTION_addSourcePortAcceptRule = 129;
        static final int TRANSACTION_addSourceRoute = 127;
        static final int TRANSACTION_addTosPolicy = 62;
        static final int TRANSACTION_addUidSocksRule = 113;
        static final int TRANSACTION_addUidToChain = 119;
        static final int TRANSACTION_allowProtect = 31;
        static final int TRANSACTION_buildFirewall = 84;
        static final int TRANSACTION_cleanAllBlock = 76;
        static final int TRANSACTION_cleanBlockPorts = 79;
        static final int TRANSACTION_cleanOnlyAllowIPs = 81;
        static final int TRANSACTION_clearEbpfMap = 41;
        static final int TRANSACTION_clearInterfaceAddresses = 6;
        static final int TRANSACTION_clearTosMap = 64;
        static final int TRANSACTION_closeSocketsForFreecess = 28;
        static final int TRANSACTION_closeSocketsForUid = 30;
        static final int TRANSACTION_closeSocketsForUids = 29;
        static final int TRANSACTION_deactivateClo = 97;
        static final int TRANSACTION_deactivateCloGro = 99;
        static final int TRANSACTION_deactivateCloHo = 105;
        static final int TRANSACTION_delIpAcceptRule = 122;
        static final int TRANSACTION_delSourcePortAcceptRule = 130;
        static final int TRANSACTION_delSourceRoute = 128;
        static final int TRANSACTION_denyProtect = 32;
        static final int TRANSACTION_disableDAD = 70;
        static final int TRANSACTION_disableEpdg = 67;
        static final int TRANSACTION_disableIpv6 = 10;
        static final int TRANSACTION_disableMptcp = 126;
        static final int TRANSACTION_enableEpdg = 66;
        static final int TRANSACTION_enableIpv6 = 11;
        static final int TRANSACTION_enableKnoxVpnFlagForTether = 44;
        static final int TRANSACTION_enableMptcp = 125;
        static final int TRANSACTION_getDeviceInfo = 106;
        static final int TRANSACTION_getInterfaceConfig = 4;
        static final int TRANSACTION_getL4sConnCount = 59;
        static final int TRANSACTION_getNetworkStatsVideoCall = 50;
        static final int TRANSACTION_getTcpLocalPorts = 65;
        static final int TRANSACTION_isBandwidthControlEnabled = 22;
        static final int TRANSACTION_isFirewallEnabled = 24;
        static final int TRANSACTION_isNetworkRestricted = 33;
        static final int TRANSACTION_l4StatsGet = 95;
        static final int TRANSACTION_listInterfaces = 3;
        static final int TRANSACTION_prioritizeApp = 51;
        static final int TRANSACTION_prioritizeMnxbApp = 91;
        static final int TRANSACTION_registerCloEventObserver = 100;
        static final int TRANSACTION_registerNetdTetherEventListener = 45;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_removeChain = 110;
        static final int TRANSACTION_removeInterfaceAlert = 17;
        static final int TRANSACTION_removeInterfaceQuota = 15;
        static final int TRANSACTION_removeLegacyRoute = 90;
        static final int TRANSACTION_removeMptcpLink = 108;
        static final int TRANSACTION_removeSocksRule = 112;
        static final int TRANSACTION_removeSocksSkipRule = 116;
        static final int TRANSACTION_removeSocksSkipRuleProto = 118;
        static final int TRANSACTION_removeTosPolicy = 63;
        static final int TRANSACTION_removeUidFromChain = 120;
        static final int TRANSACTION_removeUidSocksRule = 114;
        static final int TRANSACTION_replaceApeRule = 53;
        static final int TRANSACTION_replaceMnxbRule = 93;
        static final int TRANSACTION_runKnoxFirewallRulesCommand = 42;
        static final int TRANSACTION_runKnoxRulesCommand = 43;
        static final int TRANSACTION_setAdvertiseWindowSize = 94;
        static final int TRANSACTION_setAllowHostAlone = 75;
        static final int TRANSACTION_setAllowListIPs = 73;
        static final int TRANSACTION_setAutoConf = 88;
        static final int TRANSACTION_setBlockAllDNSPackets = 71;
        static final int TRANSACTION_setBlockAllPackets = 77;
        static final int TRANSACTION_setBlockHostAlone = 74;
        static final int TRANSACTION_setBlockListIPs = 72;
        static final int TRANSACTION_setBlockPorts = 78;
        static final int TRANSACTION_setDataSaverModeEnabled = 20;
        static final int TRANSACTION_setDestinationBasedMarkRule = 133;
        static final int TRANSACTION_setDnsForwardersForKnoxVpn = 35;
        static final int TRANSACTION_setEpdgInterfaceDropRule = 68;
        static final int TRANSACTION_setFirewallChainEnabled = 27;
        static final int TRANSACTION_setFirewallEnabled = 23;
        static final int TRANSACTION_setFirewallRuleMobileData = 86;
        static final int TRANSACTION_setFirewallRuleWifi = 85;
        static final int TRANSACTION_setFirewallUidRule = 25;
        static final int TRANSACTION_setFirewallUidRules = 26;
        static final int TRANSACTION_setIPv6AddrGenMode = 12;
        static final int TRANSACTION_setInterfaceAlert = 16;
        static final int TRANSACTION_setInterfaceConfig = 5;
        static final int TRANSACTION_setInterfaceDown = 7;
        static final int TRANSACTION_setInterfaceIpv6PrivacyExtensions = 9;
        static final int TRANSACTION_setInterfaceQuota = 14;
        static final int TRANSACTION_setInterfaceUp = 8;
        static final int TRANSACTION_setKnoxVpn = 47;
        static final int TRANSACTION_setMptcpMtuValue = 124;
        static final int TRANSACTION_setNetworkInfo = 36;
        static final int TRANSACTION_setOnlyAllowIPs = 80;
        static final int TRANSACTION_setPrivateIpRoute = 132;
        static final int TRANSACTION_setQboxUid = 56;
        static final int TRANSACTION_setTcpBufferSize = 123;
        static final int TRANSACTION_setUIDRoute = 134;
        static final int TRANSACTION_setUidCleartextNetworkPolicy = 21;
        static final int TRANSACTION_setUidOnMeteredNetworkAllowlist = 19;
        static final int TRANSACTION_setUidOnMeteredNetworkDenylist = 18;
        static final int TRANSACTION_setUrlFirewallRuleMobileData = 82;
        static final int TRANSACTION_setUrlFirewallRuleWifi = 83;
        static final int TRANSACTION_shutdown = 13;
        static final int TRANSACTION_spegRestrictNetworkConnection = 34;
        static final int TRANSACTION_startL4s = 57;
        static final int TRANSACTION_startNetworkStatsOnPorts = 48;
        static final int TRANSACTION_startQbox = 54;
        static final int TRANSACTION_startTosMarker = 60;
        static final int TRANSACTION_stopL4s = 58;
        static final int TRANSACTION_stopNetworkStatsOnPorts = 49;
        static final int TRANSACTION_stopQbox = 55;
        static final int TRANSACTION_stopTosMarker = 61;
        static final int TRANSACTION_unregisterCloEventObserver = 101;
        static final int TRANSACTION_unregisterNetdTetherEventListener = 46;
        static final int TRANSACTION_unregisterObserver = 2;
        static final int TRANSACTION_updateDefaultGatewayForEpdg = 69;
        static final int TRANSACTION_updateGroFlushTime = 102;
        static final int TRANSACTION_updateGroPshOption = 103;
        static final int TRANSACTION_updateInputFilterAppWideRules = 40;
        static final int TRANSACTION_updateInputFilterExemptRules = 38;
        static final int TRANSACTION_updateInputFilterUserWideRules = 39;
        static final int TRANSACTION_updateSourceRule = 131;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 133;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static INetworkManagementService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetworkManagementService)) {
                return (INetworkManagementService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "setInterfaceQuota";
                case 15:
                    return "removeInterfaceQuota";
                case 16:
                    return "setInterfaceAlert";
                case 17:
                    return "removeInterfaceAlert";
                case 18:
                    return "setUidOnMeteredNetworkDenylist";
                case 19:
                    return "setUidOnMeteredNetworkAllowlist";
                case 20:
                    return "setDataSaverModeEnabled";
                case 21:
                    return "setUidCleartextNetworkPolicy";
                case 22:
                    return "isBandwidthControlEnabled";
                case 23:
                    return "setFirewallEnabled";
                case 24:
                    return "isFirewallEnabled";
                case 25:
                    return "setFirewallUidRule";
                case 26:
                    return "setFirewallUidRules";
                case 27:
                    return "setFirewallChainEnabled";
                case 28:
                    return "closeSocketsForFreecess";
                case 29:
                    return "closeSocketsForUids";
                case 30:
                    return "closeSocketsForUid";
                case 31:
                    return "allowProtect";
                case 32:
                    return "denyProtect";
                case 33:
                    return "isNetworkRestricted";
                case 34:
                    return "spegRestrictNetworkConnection";
                case 35:
                    return "setDnsForwardersForKnoxVpn";
                case 36:
                    return "setNetworkInfo";
                case 37:
                    return "addOrRemoveSystemAppFromDataSaverWhitelist";
                case 38:
                    return "updateInputFilterExemptRules";
                case 39:
                    return "updateInputFilterUserWideRules";
                case 40:
                    return "updateInputFilterAppWideRules";
                case 41:
                    return "clearEbpfMap";
                case 42:
                    return "runKnoxFirewallRulesCommand";
                case 43:
                    return "runKnoxRulesCommand";
                case 44:
                    return "enableKnoxVpnFlagForTether";
                case 45:
                    return "registerNetdTetherEventListener";
                case 46:
                    return "unregisterNetdTetherEventListener";
                case 47:
                    return "setKnoxVpn";
                case 48:
                    return "startNetworkStatsOnPorts";
                case 49:
                    return "stopNetworkStatsOnPorts";
                case 50:
                    return "getNetworkStatsVideoCall";
                case 51:
                    return "prioritizeApp";
                case 52:
                    return "addApeRule";
                case 53:
                    return "replaceApeRule";
                case 54:
                    return "startQbox";
                case 55:
                    return "stopQbox";
                case 56:
                    return "setQboxUid";
                case 57:
                    return "startL4s";
                case 58:
                    return "stopL4s";
                case 59:
                    return "getL4sConnCount";
                case 60:
                    return "startTosMarker";
                case 61:
                    return "stopTosMarker";
                case 62:
                    return "addTosPolicy";
                case 63:
                    return "removeTosPolicy";
                case 64:
                    return "clearTosMap";
                case 65:
                    return "getTcpLocalPorts";
                case 66:
                    return "enableEpdg";
                case 67:
                    return "disableEpdg";
                case 68:
                    return "setEpdgInterfaceDropRule";
                case 69:
                    return "updateDefaultGatewayForEpdg";
                case 70:
                    return "disableDAD";
                case 71:
                    return "setBlockAllDNSPackets";
                case 72:
                    return "setBlockListIPs";
                case 73:
                    return "setAllowListIPs";
                case 74:
                    return "setBlockHostAlone";
                case 75:
                    return "setAllowHostAlone";
                case 76:
                    return "cleanAllBlock";
                case 77:
                    return "setBlockAllPackets";
                case 78:
                    return "setBlockPorts";
                case 79:
                    return "cleanBlockPorts";
                case 80:
                    return "setOnlyAllowIPs";
                case 81:
                    return "cleanOnlyAllowIPs";
                case 82:
                    return "setUrlFirewallRuleMobileData";
                case 83:
                    return "setUrlFirewallRuleWifi";
                case 84:
                    return "buildFirewall";
                case 85:
                    return "setFirewallRuleWifi";
                case 86:
                    return "setFirewallRuleMobileData";
                case 87:
                    return "addPortFwdRules";
                case 88:
                    return "setAutoConf";
                case 89:
                    return "addLegacyRoute";
                case 90:
                    return "removeLegacyRoute";
                case 91:
                    return "prioritizeMnxbApp";
                case 92:
                    return "addMnxbRule";
                case 93:
                    return "replaceMnxbRule";
                case 94:
                    return "setAdvertiseWindowSize";
                case 95:
                    return "l4StatsGet";
                case 96:
                    return "activateClo";
                case 97:
                    return "deactivateClo";
                case 98:
                    return "activateCloGro";
                case 99:
                    return "deactivateCloGro";
                case 100:
                    return "registerCloEventObserver";
                case 101:
                    return "unregisterCloEventObserver";
                case 102:
                    return "updateGroFlushTime";
                case 103:
                    return "updateGroPshOption";
                case 104:
                    return "activateCloHo";
                case 105:
                    return "deactivateCloHo";
                case 106:
                    return "getDeviceInfo";
                case 107:
                    return "addMptcpLink";
                case 108:
                    return "removeMptcpLink";
                case 109:
                    return "addChain";
                case 110:
                    return "removeChain";
                case 111:
                    return "addSocksRule";
                case 112:
                    return "removeSocksRule";
                case 113:
                    return "addUidSocksRule";
                case 114:
                    return "removeUidSocksRule";
                case 115:
                    return "addSocksSkipRule";
                case 116:
                    return "removeSocksSkipRule";
                case 117:
                    return "addSocksSkipRuleProto";
                case 118:
                    return "removeSocksSkipRuleProto";
                case 119:
                    return "addUidToChain";
                case 120:
                    return "removeUidFromChain";
                case 121:
                    return "addIpAcceptRule";
                case 122:
                    return "delIpAcceptRule";
                case 123:
                    return "setTcpBufferSize";
                case 124:
                    return "setMptcpMtuValue";
                case 125:
                    return "enableMptcp";
                case 126:
                    return "disableMptcp";
                case 127:
                    return "addSourceRoute";
                case 128:
                    return "delSourceRoute";
                case 129:
                    return "addSourcePortAcceptRule";
                case 130:
                    return "delSourcePortAcceptRule";
                case 131:
                    return "updateSourceRule";
                case 132:
                    return "setPrivateIpRoute";
                case 133:
                    return "setDestinationBasedMarkRule";
                case 134:
                    return "setUIDRoute";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    INetworkManagementEventObserver iNetworkManagementEventObserverAsInterface = INetworkManagementEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerObserver(iNetworkManagementEventObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    INetworkManagementEventObserver iNetworkManagementEventObserverAsInterface2 = INetworkManagementEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterObserver(iNetworkManagementEventObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String[] strArrListInterfaces = listInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListInterfaces);
                    return true;
                case 4:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    InterfaceConfiguration interfaceConfig = getInterfaceConfig(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(interfaceConfig, 1);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    InterfaceConfiguration interfaceConfiguration = (InterfaceConfiguration) parcel.readTypedObject(InterfaceConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInterfaceConfig(string2, interfaceConfiguration);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearInterfaceAddresses(string3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInterfaceDown(string4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInterfaceUp(string5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string6 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterfaceIpv6PrivacyExtensions(string6, z);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableIpv6(string7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableIpv6(string8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string9 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIPv6AddrGenMode(string9, i3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string10 = parcel.readString();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInterfaceQuota(string10, j);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeInterfaceQuota(string11);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string12 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInterfaceAlert(string12, j2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeInterfaceAlert(string13);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUidOnMeteredNetworkDenylist(i4, z2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i5 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUidOnMeteredNetworkAllowlist(i5, z3);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataSaverModeEnabled = setDataSaverModeEnabled(z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataSaverModeEnabled);
                    return true;
                case 21:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidCleartextNetworkPolicy(i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean zIsBandwidthControlEnabled = isBandwidthControlEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBandwidthControlEnabled);
                    return true;
                case 23:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallEnabled(z5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean zIsFirewallEnabled = isFirewallEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFirewallEnabled);
                    return true;
                case 25:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFirewallUidRule(i8, i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i11 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setFirewallUidRules(i11, iArrCreateIntArray, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i12 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallChainEnabled(i12, z6);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i13 = parcel.readInt();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSocketsForFreecess(i13, string14);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    closeSocketsForUids(iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSocketsForUid(i14);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    allowProtect(i15);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    denyProtect(i16);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNetworkRestricted = isNetworkRestricted(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNetworkRestricted);
                    return true;
                case 34:
                    int i18 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    spegRestrictNetworkConnection(i18, z7);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i19 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setDnsForwardersForKnoxVpn(i19, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i20 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkInfo(i20, z8, i21);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    boolean z9 = parcel.readBoolean();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOrRemoveSystemAppFromDataSaverWhitelist(z9, i22);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterExemptRules(i23, i24);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterUserWideRules(iArrCreateIntArray4, i25, i26);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterAppWideRules(iArrCreateIntArray5, i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearEbpfMap(i29);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i30 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strRunKnoxFirewallRulesCommand = runKnoxFirewallRulesCommand(i30, string15);
                    parcel2.writeNoException();
                    parcel2.writeString(strRunKnoxFirewallRulesCommand);
                    return true;
                case 43:
                    int i31 = parcel.readInt();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    runKnoxRulesCommand(i31, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableKnoxVpnFlagForTether(z10);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    registerNetdTetherEventListener();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    unregisterNetdTetherEventListener();
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i32 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKnoxVpn(i32, z11);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String string16 = parcel.readString();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startNetworkStatsOnPorts(string16, i33, i34);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string17 = parcel.readString();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopNetworkStatsOnPorts(string17, i35, i36);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String string18 = parcel.readString();
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long networkStatsVideoCall = getNetworkStatsVideoCall(string18, i37, i38);
                    parcel2.writeNoException();
                    parcel2.writeLong(networkStatsVideoCall);
                    return true;
                case 51:
                    boolean z12 = parcel.readBoolean();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iPrioritizeApp = prioritizeApp(z12, i39);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPrioritizeApp);
                    return true;
                case 52:
                    boolean z13 = parcel.readBoolean();
                    String string19 = parcel.readString();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddApeRule = addApeRule(z13, string19, i40);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddApeRule);
                    return true;
                case 53:
                    String string20 = parcel.readString();
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iReplaceApeRule = replaceApeRule(string20, i41, i42);
                    parcel2.writeNoException();
                    parcel2.writeInt(iReplaceApeRule);
                    return true;
                case 54:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startQbox(string21);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    stopQbox();
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int i43 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setQboxUid(i43, z14);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStartL4s = startL4s(string22);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartL4s);
                    return true;
                case 58:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStopL4s = stopL4s(string23);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopL4s);
                    return true;
                case 59:
                    int l4sConnCount = getL4sConnCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(l4sConnCount);
                    return true;
                case 60:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startTosMarker(string24);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopTosMarker(string25);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addTosPolicy(i44, i45);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeTosPolicy(i46);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    clearTosMap();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int[] iArrCreateIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] tcpLocalPorts = getTcpLocalPorts(iArrCreateIntArray6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(tcpLocalPorts);
                    return true;
                case 66:
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableEpdg(string26, string27, z15);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableEpdg(string28, string29);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgInterfaceDropRule(string30, string31, z16);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    Network network = (Network) parcel.readTypedObject(Network.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDefaultGatewayForEpdg(network);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableDAD(string32);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBlockAllDNSPackets(z17);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockListIPs(string33);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowListIPs(string34);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockHostAlone(string35);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowHostAlone(string36);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    cleanAllBlock();
                    parcel2.writeNoException();
                    return true;
                case 77:
                    setBlockAllPackets();
                    parcel2.writeNoException();
                    return true;
                case 78:
                    String string37 = parcel.readString();
                    int i47 = parcel.readInt();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockPorts(string37, i47, string38);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    cleanBlockPorts();
                    parcel2.writeNoException();
                    return true;
                case 80:
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOnlyAllowIPs(string39);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    cleanOnlyAllowIPs();
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int i48 = parcel.readInt();
                    String string40 = parcel.readString();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUrlFirewallRuleMobileData(i48, string40, z18);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int i49 = parcel.readInt();
                    String string41 = parcel.readString();
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUrlFirewallRuleWifi(i49, string41, z19);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    buildFirewall();
                    parcel2.writeNoException();
                    return true;
                case 85:
                    int i50 = parcel.readInt();
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallRuleWifi(i50, z20);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int i51 = parcel.readInt();
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallRuleMobileData(i51, z21);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    String string45 = parcel.readString();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPortFwdRules(string42, string43, string44, string45, i52);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    String string46 = parcel.readString();
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoConf(string46, z22);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int i53 = parcel.readInt();
                    String string47 = parcel.readString();
                    String string48 = parcel.readString();
                    String string49 = parcel.readString();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addLegacyRoute(i53, string47, string48, string49, i54);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    int i55 = parcel.readInt();
                    String string50 = parcel.readString();
                    String string51 = parcel.readString();
                    String string52 = parcel.readString();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeLegacyRoute(i55, string50, string51, string52, i56);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    boolean z23 = parcel.readBoolean();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iPrioritizeMnxbApp = prioritizeMnxbApp(z23, i57);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPrioritizeMnxbApp);
                    return true;
                case 92:
                    boolean z24 = parcel.readBoolean();
                    String string53 = parcel.readString();
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddMnxbRule = addMnxbRule(z24, string53, i58);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddMnxbRule);
                    return true;
                case 93:
                    String string54 = parcel.readString();
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iReplaceMnxbRule = replaceMnxbRule(string54, i59, i60);
                    parcel2.writeNoException();
                    parcel2.writeInt(iReplaceMnxbRule);
                    return true;
                case 94:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdvertiseWindowSize(i61);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    long[] jArrL4StatsGet = l4StatsGet();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(jArrL4StatsGet);
                    return true;
                case 96:
                    String string55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateClo(string55);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deactivateClo(string56);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    activateCloGro();
                    parcel2.writeNoException();
                    return true;
                case 99:
                    deactivateCloGro();
                    parcel2.writeNoException();
                    return true;
                case 100:
                    ICloEventObserver iCloEventObserverAsInterface = ICloEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCloEventObserver(iCloEventObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    unregisterCloEventObserver();
                    parcel2.writeNoException();
                    return true;
                case 102:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateGroFlushTime(j3);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateGroPshOption(i62);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int i63 = parcel.readInt();
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activateCloHo(i63, z25);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    deactivateCloHo();
                    parcel2.writeNoException();
                    return true;
                case 106:
                    int[] deviceInfo = getDeviceInfo();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(deviceInfo);
                    return true;
                case 107:
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpLink(string57);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String string58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpLink(string58);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    String string59 = parcel.readString();
                    String string60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addChain(string59, string60);
                    parcel2.writeNoException();
                    return true;
                case 110:
                    String string61 = parcel.readString();
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeChain(string61, string62);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    String string63 = parcel.readString();
                    String string64 = parcel.readString();
                    String string65 = parcel.readString();
                    int i64 = parcel.readInt();
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSocksRule(string63, string64, string65, i64, string66);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    String string67 = parcel.readString();
                    String string68 = parcel.readString();
                    String string69 = parcel.readString();
                    int i65 = parcel.readInt();
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSocksRule(string67, string68, string69, i65, string70);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    String string71 = parcel.readString();
                    String string72 = parcel.readString();
                    String string73 = parcel.readString();
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    String string74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUidSocksRule(string71, string72, string73, i66, i67, string74);
                    parcel2.writeNoException();
                    return true;
                case 114:
                    String string75 = parcel.readString();
                    String string76 = parcel.readString();
                    String string77 = parcel.readString();
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    String string78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUidSocksRule(string75, string76, string77, i68, i69, string78);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    String string79 = parcel.readString();
                    String string80 = parcel.readString();
                    String string81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSocksSkipRule(string79, string80, string81);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    String string82 = parcel.readString();
                    String string83 = parcel.readString();
                    String string84 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSocksSkipRule(string82, string83, string84);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    String string85 = parcel.readString();
                    String string86 = parcel.readString();
                    String string87 = parcel.readString();
                    int i70 = parcel.readInt();
                    String string88 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSocksSkipRuleProto(string85, string86, string87, i70, string88);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    String string89 = parcel.readString();
                    String string90 = parcel.readString();
                    String string91 = parcel.readString();
                    int i71 = parcel.readInt();
                    String string92 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSocksSkipRuleProto(string89, string90, string91, i71, string92);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    String string93 = parcel.readString();
                    String string94 = parcel.readString();
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidToChain(string93, string94, i72);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    String string95 = parcel.readString();
                    String string96 = parcel.readString();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidFromChain(string95, string96, i73);
                    parcel2.writeNoException();
                    return true;
                case 121:
                    String string97 = parcel.readString();
                    String string98 = parcel.readString();
                    String string99 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addIpAcceptRule(string97, string98, string99);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    String string100 = parcel.readString();
                    String string101 = parcel.readString();
                    String string102 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    delIpAcceptRule(string100, string101, string102);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    String string103 = parcel.readString();
                    String string104 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTcpBufferSize(string103, string104);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    String string105 = parcel.readString();
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMptcpMtuValue(string105, i74);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    String string106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableMptcp(string106);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    disableMptcp();
                    parcel2.writeNoException();
                    return true;
                case 127:
                    String string107 = parcel.readString();
                    String string108 = parcel.readString();
                    String string109 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSourceRoute(string107, string108, string109);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    String string110 = parcel.readString();
                    String string111 = parcel.readString();
                    String string112 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    delSourceRoute(string110, string111, string112);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    String string113 = parcel.readString();
                    String string114 = parcel.readString();
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSourcePortAcceptRule(string113, string114, i75);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    String string115 = parcel.readString();
                    String string116 = parcel.readString();
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    delSourcePortAcceptRule(string115, string116, i76);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    boolean z26 = parcel.readBoolean();
                    String string117 = parcel.readString();
                    String string118 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateSourceRule(z26, string117, string118);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    boolean z27 = parcel.readBoolean();
                    String string119 = parcel.readString();
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrivateIpRoute(z27, string119, i77);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    boolean z28 = parcel.readBoolean();
                    String string120 = parcel.readString();
                    String string121 = parcel.readString();
                    int i78 = parcel.readInt();
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDestinationBasedMarkRule(z28, string120, string121, i78, i79);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    boolean z29 = parcel.readBoolean();
                    String string122 = parcel.readString();
                    int i80 = parcel.readInt();
                    String string123 = parcel.readString();
                    String string124 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUIDRoute(z29, string122, i80, string123, string124);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INetworkManagementService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.INetworkManagementService
            public void registerObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNetworkManagementEventObserver);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNetworkManagementEventObserver);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public String[] listInterfaces() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InterfaceConfiguration) parcelObtain2.readTypedObject(InterfaceConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(interfaceConfiguration, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearInterfaceAddresses(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceDown(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceUp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableIpv6(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableIpv6(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setIPv6AddrGenMode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void shutdown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceQuota(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceQuota(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceAlert(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceAlert(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkDenylist(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean setDataSaverModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidCleartextNetworkPolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isBandwidthControlEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isFirewallEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRule(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallChainEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForFreecess(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForUids(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void allowProtect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void denyProtect(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isNetworkRestricted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setDnsForwardersForKnoxVpn(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setNetworkInfo(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterExemptRules(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearEbpfMap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void runKnoxRulesCommand(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableKnoxVpnFlagForTether(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void registerNetdTetherEventListener() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterNetdTetherEventListener() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setKnoxVpn(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public long getNetworkStatsVideoCall(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int prioritizeApp(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int addApeRule(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int replaceApeRule(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startQbox(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopQbox() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setQboxUid(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int startL4s(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int stopL4s(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int getL4sConnCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startTosMarker(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopTosMarker(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addTosPolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeTosPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearTosMap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int[] getTcpLocalPorts(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableEpdg(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableEpdg(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setEpdgInterfaceDropRule(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(network, 0);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableDAD(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockAllDNSPackets(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockListIPs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAllowListIPs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockHostAlone(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAllowHostAlone(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanAllBlock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockAllPackets() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockPorts(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanBlockPorts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setOnlyAllowIPs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanOnlyAllowIPs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUrlFirewallRuleMobileData(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUrlFirewallRuleWifi(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void buildFirewall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallRuleWifi(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallRuleMobileData(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addPortFwdRules(String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAutoConf(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int prioritizeMnxbApp(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int addMnxbRule(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int replaceMnxbRule(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAdvertiseWindowSize(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public long[] l4StatsGet() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateClo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateClo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateCloGro() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateCloGro() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void registerCloEventObserver(ICloEventObserver iCloEventObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCloEventObserver);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterCloEventObserver() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateGroFlushTime(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateGroPshOption(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateCloHo(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateCloHo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int[] getDeviceInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addMptcpLink(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeMptcpLink(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addChain(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeChain(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksSkipRule(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksSkipRule(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addUidToChain(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeUidFromChain(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addIpAcceptRule(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delIpAcceptRule(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setTcpBufferSize(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setMptcpMtuValue(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableMptcp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableMptcp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSourceRoute(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delSourceRoute(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSourcePortAcceptRule(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delSourcePortAcceptRule(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateSourceRule(boolean z, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setPrivateIpRoute(boolean z, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setDestinationBasedMarkRule(boolean z, String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUIDRoute(boolean z, String str, int i, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void shutdown_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SHUTDOWN, getCallingPid(), getCallingUid());
        }

        protected void setDataSaverModeEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.NETWORK_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isNetworkRestricted_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OBSERVE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }
    }
}
