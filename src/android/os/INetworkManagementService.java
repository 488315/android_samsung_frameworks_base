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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INetworkManagementService)) {
                return (INetworkManagementService) queryLocalInterface;
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
                    INetworkManagementEventObserver asInterface = INetworkManagementEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    INetworkManagementEventObserver asInterface2 = INetworkManagementEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String[] listInterfaces = listInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listInterfaces);
                    return true;
                case 4:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    InterfaceConfiguration interfaceConfig = getInterfaceConfig(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(interfaceConfig, 1);
                    return true;
                case 5:
                    String readString2 = parcel.readString();
                    InterfaceConfiguration interfaceConfiguration = (InterfaceConfiguration) parcel.readTypedObject(InterfaceConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInterfaceConfig(readString2, interfaceConfiguration);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearInterfaceAddresses(readString3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInterfaceDown(readString4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInterfaceUp(readString5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString6 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterfaceIpv6PrivacyExtensions(readString6, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableIpv6(readString7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableIpv6(readString8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString9 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIPv6AddrGenMode(readString9, readInt);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString10 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInterfaceQuota(readString10, readLong);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeInterfaceQuota(readString11);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString12 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInterfaceAlert(readString12, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeInterfaceAlert(readString13);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUidOnMeteredNetworkDenylist(readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUidOnMeteredNetworkAllowlist(readInt3, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataSaverModeEnabled = setDataSaverModeEnabled(readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataSaverModeEnabled);
                    return true;
                case 21:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidCleartextNetworkPolicy(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean isBandwidthControlEnabled = isBandwidthControlEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBandwidthControlEnabled);
                    return true;
                case 23:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallEnabled(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean isFirewallEnabled = isFirewallEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirewallEnabled);
                    return true;
                case 25:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFirewallUidRule(readInt6, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt9 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setFirewallUidRules(readInt9, createIntArray, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallChainEnabled(readInt10, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt11 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSocketsForFreecess(readInt11, readString14);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    closeSocketsForUids(createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSocketsForUid(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    allowProtect(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    denyProtect(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNetworkRestricted = isNetworkRestricted(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNetworkRestricted);
                    return true;
                case 34:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    spegRestrictNetworkConnection(readInt16, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt17 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setDnsForwardersForKnoxVpn(readInt17, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt18 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkInfo(readInt18, readBoolean8, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addOrRemoveSystemAppFromDataSaverWhitelist(readBoolean9, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterExemptRules(readInt21, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int[] createIntArray4 = parcel.createIntArray();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterUserWideRules(createIntArray4, readInt23, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int[] createIntArray5 = parcel.createIntArray();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterAppWideRules(createIntArray5, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearEbpfMap(readInt27);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt28 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String runKnoxFirewallRulesCommand = runKnoxFirewallRulesCommand(readInt28, readString15);
                    parcel2.writeNoException();
                    parcel2.writeString(runKnoxFirewallRulesCommand);
                    return true;
                case 43:
                    int readInt29 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    runKnoxRulesCommand(readInt29, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableKnoxVpnFlagForTether(readBoolean10);
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
                    int readInt30 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKnoxVpn(readInt30, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String readString16 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startNetworkStatsOnPorts(readString16, readInt31, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String readString17 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopNetworkStatsOnPorts(readString17, readInt33, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString18 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long networkStatsVideoCall = getNetworkStatsVideoCall(readString18, readInt35, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeLong(networkStatsVideoCall);
                    return true;
                case 51:
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int prioritizeApp = prioritizeApp(readBoolean12, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioritizeApp);
                    return true;
                case 52:
                    boolean readBoolean13 = parcel.readBoolean();
                    String readString19 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addApeRule = addApeRule(readBoolean13, readString19, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeInt(addApeRule);
                    return true;
                case 53:
                    String readString20 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int replaceApeRule = replaceApeRule(readString20, readInt39, readInt40);
                    parcel2.writeNoException();
                    parcel2.writeInt(replaceApeRule);
                    return true;
                case 54:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startQbox(readString21);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    stopQbox();
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int readInt41 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setQboxUid(readInt41, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startL4s = startL4s(readString22);
                    parcel2.writeNoException();
                    parcel2.writeInt(startL4s);
                    return true;
                case 58:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int stopL4s = stopL4s(readString23);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopL4s);
                    return true;
                case 59:
                    int l4sConnCount = getL4sConnCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(l4sConnCount);
                    return true;
                case 60:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startTosMarker(readString24);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopTosMarker(readString25);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addTosPolicy(readInt42, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeTosPolicy(readInt44);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    clearTosMap();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int[] createIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] tcpLocalPorts = getTcpLocalPorts(createIntArray6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(tcpLocalPorts);
                    return true;
                case 66:
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableEpdg(readString26, readString27, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableEpdg(readString28, readString29);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgInterfaceDropRule(readString30, readString31, readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    Network network = (Network) parcel.readTypedObject(Network.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateDefaultGatewayForEpdg(network);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableDAD(readString32);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBlockAllDNSPackets(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockListIPs(readString33);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowListIPs(readString34);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockHostAlone(readString35);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowHostAlone(readString36);
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
                    String readString37 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockPorts(readString37, readInt45, readString38);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    cleanBlockPorts();
                    parcel2.writeNoException();
                    return true;
                case 80:
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOnlyAllowIPs(readString39);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    cleanOnlyAllowIPs();
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int readInt46 = parcel.readInt();
                    String readString40 = parcel.readString();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUrlFirewallRuleMobileData(readInt46, readString40, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int readInt47 = parcel.readInt();
                    String readString41 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUrlFirewallRuleWifi(readInt47, readString41, readBoolean19);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    buildFirewall();
                    parcel2.writeNoException();
                    return true;
                case 85:
                    int readInt48 = parcel.readInt();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallRuleWifi(readInt48, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int readInt49 = parcel.readInt();
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallRuleMobileData(readInt49, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPortFwdRules(readString42, readString43, readString44, readString45, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    String readString46 = parcel.readString();
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoConf(readString46, readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt51 = parcel.readInt();
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addLegacyRoute(readInt51, readString47, readString48, readString49, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    int readInt53 = parcel.readInt();
                    String readString50 = parcel.readString();
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeLegacyRoute(readInt53, readString50, readString51, readString52, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    boolean readBoolean23 = parcel.readBoolean();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int prioritizeMnxbApp = prioritizeMnxbApp(readBoolean23, readInt55);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioritizeMnxbApp);
                    return true;
                case 92:
                    boolean readBoolean24 = parcel.readBoolean();
                    String readString53 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addMnxbRule = addMnxbRule(readBoolean24, readString53, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeInt(addMnxbRule);
                    return true;
                case 93:
                    String readString54 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int replaceMnxbRule = replaceMnxbRule(readString54, readInt57, readInt58);
                    parcel2.writeNoException();
                    parcel2.writeInt(replaceMnxbRule);
                    return true;
                case 94:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdvertiseWindowSize(readInt59);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    long[] l4StatsGet = l4StatsGet();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(l4StatsGet);
                    return true;
                case 96:
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateClo(readString55);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deactivateClo(readString56);
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
                    ICloEventObserver asInterface3 = ICloEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCloEventObserver(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    unregisterCloEventObserver();
                    parcel2.writeNoException();
                    return true;
                case 102:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateGroFlushTime(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateGroPshOption(readInt60);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int readInt61 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activateCloHo(readInt61, readBoolean25);
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
                    String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpLink(readString57);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String readString58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpLink(readString58);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    String readString59 = parcel.readString();
                    String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addChain(readString59, readString60);
                    parcel2.writeNoException();
                    return true;
                case 110:
                    String readString61 = parcel.readString();
                    String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeChain(readString61, readString62);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    String readString63 = parcel.readString();
                    String readString64 = parcel.readString();
                    String readString65 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSocksRule(readString63, readString64, readString65, readInt62, readString66);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    String readString67 = parcel.readString();
                    String readString68 = parcel.readString();
                    String readString69 = parcel.readString();
                    int readInt63 = parcel.readInt();
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSocksRule(readString67, readString68, readString69, readInt63, readString70);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    String readString71 = parcel.readString();
                    String readString72 = parcel.readString();
                    String readString73 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUidSocksRule(readString71, readString72, readString73, readInt64, readInt65, readString74);
                    parcel2.writeNoException();
                    return true;
                case 114:
                    String readString75 = parcel.readString();
                    String readString76 = parcel.readString();
                    String readString77 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUidSocksRule(readString75, readString76, readString77, readInt66, readInt67, readString78);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    String readString79 = parcel.readString();
                    String readString80 = parcel.readString();
                    String readString81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSocksSkipRule(readString79, readString80, readString81);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    String readString82 = parcel.readString();
                    String readString83 = parcel.readString();
                    String readString84 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSocksSkipRule(readString82, readString83, readString84);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    String readString85 = parcel.readString();
                    String readString86 = parcel.readString();
                    String readString87 = parcel.readString();
                    int readInt68 = parcel.readInt();
                    String readString88 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSocksSkipRuleProto(readString85, readString86, readString87, readInt68, readString88);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    String readString89 = parcel.readString();
                    String readString90 = parcel.readString();
                    String readString91 = parcel.readString();
                    int readInt69 = parcel.readInt();
                    String readString92 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSocksSkipRuleProto(readString89, readString90, readString91, readInt69, readString92);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    String readString93 = parcel.readString();
                    String readString94 = parcel.readString();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidToChain(readString93, readString94, readInt70);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    String readString95 = parcel.readString();
                    String readString96 = parcel.readString();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidFromChain(readString95, readString96, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 121:
                    String readString97 = parcel.readString();
                    String readString98 = parcel.readString();
                    String readString99 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addIpAcceptRule(readString97, readString98, readString99);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    String readString100 = parcel.readString();
                    String readString101 = parcel.readString();
                    String readString102 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    delIpAcceptRule(readString100, readString101, readString102);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    String readString103 = parcel.readString();
                    String readString104 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTcpBufferSize(readString103, readString104);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    String readString105 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMptcpMtuValue(readString105, readInt72);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    String readString106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableMptcp(readString106);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    disableMptcp();
                    parcel2.writeNoException();
                    return true;
                case 127:
                    String readString107 = parcel.readString();
                    String readString108 = parcel.readString();
                    String readString109 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSourceRoute(readString107, readString108, readString109);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    String readString110 = parcel.readString();
                    String readString111 = parcel.readString();
                    String readString112 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    delSourceRoute(readString110, readString111, readString112);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    String readString113 = parcel.readString();
                    String readString114 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addSourcePortAcceptRule(readString113, readString114, readInt73);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    String readString115 = parcel.readString();
                    String readString116 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    delSourcePortAcceptRule(readString115, readString116, readInt74);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    boolean readBoolean26 = parcel.readBoolean();
                    String readString117 = parcel.readString();
                    String readString118 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateSourceRule(readBoolean26, readString117, readString118);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    boolean readBoolean27 = parcel.readBoolean();
                    String readString119 = parcel.readString();
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrivateIpRoute(readBoolean27, readString119, readInt75);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    boolean readBoolean28 = parcel.readBoolean();
                    String readString120 = parcel.readString();
                    String readString121 = parcel.readString();
                    int readInt76 = parcel.readInt();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDestinationBasedMarkRule(readBoolean28, readString120, readString121, readInt76, readInt77);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    boolean readBoolean29 = parcel.readBoolean();
                    String readString122 = parcel.readString();
                    int readInt78 = parcel.readInt();
                    String readString123 = parcel.readString();
                    String readString124 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUIDRoute(readBoolean29, readString122, readInt78, readString123, readString124);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkManagementEventObserver);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterObserver(INetworkManagementEventObserver iNetworkManagementEventObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkManagementEventObserver);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public String[] listInterfaces() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public InterfaceConfiguration getInterfaceConfig(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InterfaceConfiguration) obtain2.readTypedObject(InterfaceConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceConfig(String str, InterfaceConfiguration interfaceConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(interfaceConfiguration, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearInterfaceAddresses(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceDown(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceUp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceIpv6PrivacyExtensions(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableIpv6(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableIpv6(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setIPv6AddrGenMode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void shutdown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceQuota(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceQuota(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceAlert(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceAlert(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkDenylist(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean setDataSaverModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidCleartextNetworkPolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isBandwidthControlEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isFirewallEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRule(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallChainEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForFreecess(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForUids(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void closeSocketsForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void allowProtect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void denyProtect(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isNetworkRestricted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setDnsForwardersForKnoxVpn(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setNetworkInfo(int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addOrRemoveSystemAppFromDataSaverWhitelist(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterExemptRules(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearEbpfMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void runKnoxRulesCommand(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableKnoxVpnFlagForTether(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void registerNetdTetherEventListener() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterNetdTetherEventListener() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setKnoxVpn(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopNetworkStatsOnPorts(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public long getNetworkStatsVideoCall(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int prioritizeApp(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int addApeRule(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int replaceApeRule(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startQbox(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopQbox() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setQboxUid(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int startL4s(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int stopL4s(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int getL4sConnCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startTosMarker(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopTosMarker(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addTosPolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeTosPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearTosMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int[] getTcpLocalPorts(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableEpdg(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableEpdg(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setEpdgInterfaceDropRule(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateDefaultGatewayForEpdg(Network network) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(network, 0);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableDAD(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockAllDNSPackets(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockListIPs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAllowListIPs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockHostAlone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAllowHostAlone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanAllBlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockAllPackets() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setBlockPorts(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanBlockPorts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setOnlyAllowIPs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void cleanOnlyAllowIPs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUrlFirewallRuleMobileData(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUrlFirewallRuleWifi(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void buildFirewall() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallRuleWifi(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallRuleMobileData(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addPortFwdRules(String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAutoConf(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeLegacyRoute(int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int prioritizeMnxbApp(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int addMnxbRule(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int replaceMnxbRule(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setAdvertiseWindowSize(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public long[] l4StatsGet() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateClo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateClo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateCloGro() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateCloGro() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void registerCloEventObserver(ICloEventObserver iCloEventObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCloEventObserver);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterCloEventObserver() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateGroFlushTime(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateGroPshOption(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void activateCloHo(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void deactivateCloHo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public int[] getDeviceInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addMptcpLink(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeMptcpLink(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addChain(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeChain(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksRule(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeUidSocksRule(String str, String str2, String str3, int i, int i2, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksSkipRule(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksSkipRule(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeSocksSkipRuleProto(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addUidToChain(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeUidFromChain(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addIpAcceptRule(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delIpAcceptRule(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setTcpBufferSize(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setMptcpMtuValue(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableMptcp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableMptcp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSourceRoute(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delSourceRoute(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void addSourcePortAcceptRule(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void delSourcePortAcceptRule(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void updateSourceRule(boolean z, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setPrivateIpRoute(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setDestinationBasedMarkRule(boolean z, String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUIDRoute(boolean z, String str, int i, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
