package com.android.internal.net;

import android.net.ICloEventObserver;
import android.net.MBBStatsParcel;
import android.net.TetherStatsParcel;
import android.net.TrafficTimeStatsParcel;
import android.net.UidRangeParcel;
import android.net.UidStatsParcel;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IOemNetd extends IInterface {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IOemNetd {
        @Override // com.android.internal.net.IOemNetd
        public void activateClo(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void activateCloGro() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int addApeRule(boolean z, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int addIpToPrioList(List list, List list2, List list3, List list4, List list5)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int addMnxbRule(boolean z, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpChain(String str, String str2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpIpAcceptRule(String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpLinkIface(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpSocksRule(String str, String str2, String str3, int i, String str4)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpSocksSkipRule(String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpSocksSkipRuleProto(
                String str, String str2, String str3, int i, String str4) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpSourcePortAcceptRule(String str, String str2, int i)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpSourceRoute(String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addMptcpUidSocksRule(
                String str, String str2, String str3, int i, int i2, String str4)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addPortFwdRules(String str, String str2, String str3, String str4, int i)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addTosPolicy(int i, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addUidToMptcpChain(String str, int i, String str2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void addUserToNwFilterRange(int i) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public void cleanAllBlock() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void cleanBlockPorts() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void cleanOnlyAllowIPs() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void clearEbpfMap(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void clearKnoxNwFilterProxyEntries() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void clearNetworkFilterEntries(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int clearPriorityMap() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void clearTosMap() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void deactivateClo(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void deactivateCloGro() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int delIpToPrioList(List list, List list2, List list3, List list4, List list5)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void delMptcpIpAcceptRule(String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void delMptcpSourcePortAcceptRule(String str, String str2, int i)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void delMptcpSourceRoute(String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void disableMptcpMode() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int disableTlsPacketTracing(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void enableIpOptionModification(boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void enableKnoxVpnFlagForTether(boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void enableMptcpModes(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void enablePortInfoEntries(int i, int i2, int i3, boolean z)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int enableTlsPacketTracing(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void exemptUidFromNwFilterRange(UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void firewallBuild() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void firewallSetRuleMobileData(int i, boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void firewallSetRuleWifi(int i, boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int flushArpEntry(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public MBBStatsParcel[] getDataUsage(String str, List list) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public int getL4sConnCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public UidStatsParcel[] getMhsTrafficStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public String getNetworkFilterTcpV4Entry(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public String getNetworkFilterTcpV6Entry(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public String getNetworkFilterUdpV6Entry(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public int getNwFilterNetId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int[] getTcpLocalPorts(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public long getTotalDataUsage(String str) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.net.IOemNetd
        public TrafficTimeStatsParcel getTrafficTimeStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public UidStatsParcel[] getUidTrafficStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public TetherStatsParcel[] getVideoStats(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public void gmsCoreSetUidUrlMobileDataRule(int i, String str, int i2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void gmsCoreSetUidUrlWifiRule(int i, String str, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int hotspotOff(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int hotspotOn(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void interfaceSetAutoConf(String str, boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public boolean isAlive() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.net.IOemNetd
        public int isIptablesMatchEnabled(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int isMBBPathsPresent() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnBlockDnsQueriesForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnBlockUserWideDnsQuery(boolean z, int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnDestroyBlockedKnoxNetwork() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnExemptDnsQueryForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnExemptUidFromKnoxVpn(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnInsertUidForDnsAuthorization(int[] iArr) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnRemoveExemptUidFromKnoxVpn(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnRemoveExemptedDnsQueryForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnRemoveUidFromDnsAuthorization() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void knoxVpnUnblockDnsQueriesForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public long[] l4StatsGet() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public void makeBlockChildChain() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void makeVideoCallChain() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void modifyEpdg(boolean z, String str, String str2, boolean z2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void networkAddLegacyRoute(int i, String str, String str2, String str3, int i2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void networkAddUidRanges(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void networkRemoveLegacyRoute(int i, String str, String str2, String str3, int i2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void networkRemoveUidRanges(int i, UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int pauseDevice(String str, boolean z, String str2, long j) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int prioDevice(boolean z, List list, List list2, List list3, List list4)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int prioDisable(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int prioEnable(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int prioUpdate(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int prioritizeApp(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int prioritizeMnxbApp(boolean z, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void registerCloEventListener(ICloEventObserver iCloEventObserver)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void registerDomainFilterEventListener(
                IDomainFilterEventListener iDomainFilterEventListener) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void registerNetdTetherEventListener(
                INetdTetherEventListener iNetdTetherEventListener) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void registerOemUnsolicitedEventListener(
                IOemNetdUnsolicitedEventListener iOemNetdUnsolicitedEventListener)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeExemptUidFromNwFilterRange(UidRangeParcel[] uidRangeParcelArr)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeKnoxNwFilterProxyApp(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeMptcpChain(String str, String str2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeMptcpLinkIface(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeMptcpSocksRule(String str, String str2, String str3, int i, String str4)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeMptcpSocksSkipRule(String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeMptcpSocksSkipRuleProto(
                String str, String str2, String str3, int i, String str4) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeMptcpUidSocksRule(
                String str, String str2, String str3, int i, int i2, String str4)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeTosPolicy(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeUidFromMptcpChain(String str, int i, String str2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void removeUserFromNwFilterRange(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int replaceApeRule(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int replaceMnxbRule(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public String runKnoxRulesCommand(int i, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public String runTcpMonitorShellCommand(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public String runVpnRulesCommand(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.IOemNetd
        public void setAdvertiseWindowSize(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setAllowHostAlone(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setAllowListIPs(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setBlockAllDNSPackets(boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setBlockAllPackets() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setBlockHostAlone(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setBlockListIPs(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setBlockPorts(String str, int i, String str2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setDnsCacheStatus(int i, boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setEpdgInterfaceDropRule(String str, String str2, boolean z)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setHttpProxyPort(int i, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setKnoxNwFilterProxyApp(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setKnoxVpn(int i, boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setMptcpDestBaseMarkRule(boolean z, String str, String str2, int i, int i2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setMptcpPrivateIpRoute(boolean z, String str, int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setMptcpTcpBufferSize(String str, String str2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setMptcpUIDRoute(boolean z, String str, int i, String str2, String str3)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setMtuValueMptcp(String str, int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setNetworkInfo(int i, boolean z, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setNwFilterNetId(int i, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void setOnlyAllowIPs(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int setQboxUid(int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int startL4s(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int startQbox(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void startTosMarker(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int startTrafficStatsController(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void startVideoStats(String str, int i, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int stopL4s(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public int stopQbox() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void stopTosMarker(String str) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public int stopTrafficStatsController(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.net.IOemNetd
        public void stopVideoStats(String str, int i, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void tcSetTCRule(boolean z, String str, String str2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void unregisterCloEventListener() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void unregisterDomainFilterEventListener() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void unregisterNetdTetherEventListener() throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateDomainFilterCache(int i, String[] strArr) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateGroFlushTime(long j) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateGroPshOption(int i) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateInputFilterAppWideRules(int[] iArr, int i, int i2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateInputFilterExemptRules(int i, int i2) throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateInputFilterUserWideRules(int[] iArr, int i, int i2)
                throws RemoteException {}

        @Override // com.android.internal.net.IOemNetd
        public void updateMptcpSourceRule(boolean z, String str, String str2)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IOemNetd {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        class Proxy implements IOemNetd {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.android.internal.net.IOemNetd
            public void activateClo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void activateCloGro() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int addApeRule(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int addIpToPrioList(List list, List list2, List list3, List list4, List list5)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    obtain.writeStringList(list4);
                    obtain.writeStringList(list5);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int addMnxbRule(boolean z, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpChain(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpIpAcceptRule(String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpLinkIface(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpSocksRule(String str, String str2, String str3, int i, String str4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpSocksSkipRule(String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpSocksSkipRuleProto(
                    String str, String str2, String str3, int i, String str4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpSourcePortAcceptRule(String str, String str2, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpSourceRoute(String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(
                            FrameworkStatsLog
                                    .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_STARTED,
                            obtain,
                            obtain2,
                            0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addMptcpUidSocksRule(
                    String str, String str2, String str3, int i, int i2, String str4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addPortFwdRules(String str, String str2, String str3, String str4, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    this.mRemote.transact(95, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addTosPolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(70, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addUidToMptcpChain(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void addUserToNwFilterRange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.internal.net.IOemNetd
            public void cleanAllBlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(108, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void cleanBlockPorts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(111, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void cleanOnlyAllowIPs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(113, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void clearEbpfMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void clearKnoxNwFilterProxyEntries() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void clearNetworkFilterEntries(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int clearPriorityMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void clearTosMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(72, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void deactivateClo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void deactivateCloGro() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int delIpToPrioList(List list, List list2, List list3, List list4, List list5)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    obtain.writeStringList(list4);
                    obtain.writeStringList(list5);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void delMptcpIpAcceptRule(String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void delMptcpSourcePortAcceptRule(String str, String str2, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void delMptcpSourceRoute(String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(
                            FrameworkStatsLog
                                    .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED,
                            obtain,
                            obtain2,
                            0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void disableMptcpMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int disableTlsPacketTracing(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void enableIpOptionModification(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void enableKnoxVpnFlagForTether(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void enableMptcpModes(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void enablePortInfoEntries(int i, int i2, int i3, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int enableTlsPacketTracing(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void exemptUidFromNwFilterRange(UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void firewallBuild() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void firewallSetRuleMobileData(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void firewallSetRuleWifi(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int flushArpEntry(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public MBBStatsParcel[] getDataUsage(String str, List list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MBBStatsParcel[]) obtain2.createTypedArray(MBBStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return "com.android.internal.net.IOemNetd";
            }

            @Override // com.android.internal.net.IOemNetd
            public int getL4sConnCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public UidStatsParcel[] getMhsTrafficStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UidStatsParcel[]) obtain2.createTypedArray(UidStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String getNetworkFilterTcpV4Entry(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String getNetworkFilterTcpV6Entry(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String getNetworkFilterUdpV6Entry(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int getNwFilterNetId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int[] getTcpLocalPorts(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public long getTotalDataUsage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public TrafficTimeStatsParcel getTrafficTimeStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TrafficTimeStatsParcel)
                            obtain2.readTypedObject(TrafficTimeStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public UidStatsParcel[] getUidTrafficStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UidStatsParcel[]) obtain2.createTypedArray(UidStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public TetherStatsParcel[] getVideoStats(String str, int i, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TetherStatsParcel[])
                            obtain2.createTypedArray(TetherStatsParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void gmsCoreSetUidUrlMobileDataRule(int i, String str, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void gmsCoreSetUidUrlWifiRule(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int hotspotOff(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int hotspotOn(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void interfaceSetAutoConf(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public boolean isAlive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int isIptablesMatchEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int isMBBPathsPresent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnBlockDnsQueriesForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnBlockUserWideDnsQuery(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnDestroyBlockedKnoxNetwork() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnExemptDnsQueryForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnExemptUidFromKnoxVpn(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnInsertUidForDnsAuthorization(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnRemoveExemptUidFromKnoxVpn(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnRemoveExemptedDnsQueryForUid(
                    int i, UidRangeParcel[] uidRangeParcelArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnRemoveUidFromDnsAuthorization() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void knoxVpnUnblockDnsQueriesForUid(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public long[] l4StatsGet() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void makeBlockChildChain() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(102, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void makeVideoCallChain() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void modifyEpdg(boolean z, String str, String str2, boolean z2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(97, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void networkAddLegacyRoute(int i, String str, String str2, String str3, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void networkAddUidRanges(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void networkRemoveLegacyRoute(
                    int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void networkRemoveUidRanges(int i, UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int pauseDevice(String str, boolean z, String str2, long j)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int prioDevice(boolean z, List list, List list2, List list3, List list4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    obtain.writeStringList(list4);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int prioDisable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int prioEnable(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int prioUpdate(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int prioritizeApp(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int prioritizeMnxbApp(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void registerCloEventListener(ICloEventObserver iCloEventObserver)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeStrongInterface(iCloEventObserver);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void registerDomainFilterEventListener(
                    IDomainFilterEventListener iDomainFilterEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeStrongInterface(iDomainFilterEventListener);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void registerNetdTetherEventListener(
                    INetdTetherEventListener iNetdTetherEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeStrongInterface(iNetdTetherEventListener);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void registerOemUnsolicitedEventListener(
                    IOemNetdUnsolicitedEventListener iOemNetdUnsolicitedEventListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeStrongInterface(iOemNetdUnsolicitedEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeExemptUidFromNwFilterRange(UidRangeParcel[] uidRangeParcelArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeTypedArray(uidRangeParcelArr, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeKnoxNwFilterProxyApp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeMptcpChain(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeMptcpLinkIface(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeMptcpSocksRule(
                    String str, String str2, String str3, int i, String str4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeMptcpSocksSkipRule(String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeMptcpSocksSkipRuleProto(
                    String str, String str2, String str3, int i, String str4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeMptcpUidSocksRule(
                    String str, String str2, String str3, int i, int i2, String str4)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeTosPolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeUidFromMptcpChain(String str, int i, String str2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void removeUserFromNwFilterRange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int replaceApeRule(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int replaceMnxbRule(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String runKnoxRulesCommand(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String runTcpMonitorShellCommand(String str, String str2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public String runVpnRulesCommand(int i, String str, String str2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setAdvertiseWindowSize(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(117, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setAllowHostAlone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(107, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setAllowListIPs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(105, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setBlockAllDNSPackets(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(103, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setBlockAllPackets() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(109, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setBlockHostAlone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(106, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setBlockListIPs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(104, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setBlockPorts(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(110, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setDnsCacheStatus(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setEpdgInterfaceDropRule(String str, String str2, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(98, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setHttpProxyPort(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setKnoxNwFilterProxyApp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setKnoxVpn(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setMptcpDestBaseMarkRule(boolean z, String str, String str2, int i, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(
                            FrameworkStatsLog
                                    .DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE,
                            obtain,
                            obtain2,
                            0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setMptcpPrivateIpRoute(boolean z, String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setMptcpTcpBufferSize(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setMptcpUIDRoute(boolean z, String str, int i, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(
                            FrameworkStatsLog
                                    .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ORGANIZATION_OWNED_MANAGED_PROFILE,
                            obtain,
                            obtain2,
                            0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setMtuValueMptcp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setNetworkInfo(int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setNwFilterNetId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void setOnlyAllowIPs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(112, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int setQboxUid(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(94, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int startL4s(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int startQbox(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void startTosMarker(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int startTrafficStatsController(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void startVideoStats(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int stopL4s(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int stopQbox() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void stopTosMarker(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(69, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public int stopTrafficStatsController(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void stopVideoStats(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void tcSetTCRule(boolean z, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void unregisterCloEventListener() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void unregisterDomainFilterEventListener() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void unregisterNetdTetherEventListener() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateDomainFilterCache(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateGroFlushTime(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeLong(j);
                    this.mRemote.transact(125, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateGroPshOption(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    this.mRemote.transact(126, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateInputFilterAppWideRules(int[] iArr, int i, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateInputFilterExemptRules(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateInputFilterUserWideRules(int[] iArr, int i, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.net.IOemNetd
            public void updateMptcpSourceRule(boolean z, String str, String str2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.internal.net.IOemNetd");
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.internal.net.IOemNetd");
        }

        public static IOemNetd asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface("com.android.internal.net.IOemNetd");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IOemNetd))
                    ? new Proxy(iBinder)
                    : (IOemNetd) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.internal.net.IOemNetd");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.internal.net.IOemNetd");
                return true;
            }
            INetdTetherEventListener iNetdTetherEventListener = null;
            IDomainFilterEventListener iDomainFilterEventListener = null;
            switch (i) {
                case 1:
                    boolean isAlive = isAlive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlive);
                    return true;
                case 2:
                    IOemNetdUnsolicitedEventListener asInterface =
                            IOemNetdUnsolicitedEventListener.Stub.asInterface(
                                    parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerOemUnsolicitedEventListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    knoxVpnBlockDnsQueriesForUid(readInt, uidRangeParcelArr);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr2 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    knoxVpnUnblockDnsQueriesForUid(readInt2, uidRangeParcelArr2);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr3 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    knoxVpnExemptDnsQueryForUid(readInt3, uidRangeParcelArr3);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr4 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    knoxVpnRemoveExemptedDnsQueryForUid(readInt4, uidRangeParcelArr4);
                    return true;
                case 7:
                    knoxVpnDestroyBlockedKnoxNetwork();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr5 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    knoxVpnExemptUidFromKnoxVpn(readInt5, uidRangeParcelArr5);
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr6 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    knoxVpnRemoveExemptUidFromKnoxVpn(readInt6, uidRangeParcelArr6);
                    return true;
                case 10:
                    knoxVpnRemoveUidFromDnsAuthorization();
                    return true;
                case 11:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    knoxVpnInsertUidForDnsAuthorization(createIntArray);
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkInfo(readInt7, readBoolean, readInt8);
                    return true;
                case 13:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterExemptRules(readInt9, readInt10);
                    return true;
                case 14:
                    int[] createIntArray2 = parcel.createIntArray();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterUserWideRules(createIntArray2, readInt11, readInt12);
                    return true;
                case 15:
                    int[] createIntArray3 = parcel.createIntArray();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateInputFilterAppWideRules(createIntArray3, readInt13, readInt14);
                    return true;
                case 16:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearEbpfMap(readInt15);
                    return true;
                case 17:
                    int readInt16 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String runKnoxFirewallRulesCommand =
                            runKnoxFirewallRulesCommand(readInt16, readString);
                    parcel2.writeNoException();
                    parcel2.writeString(runKnoxFirewallRulesCommand);
                    return true;
                case 18:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableKnoxVpnFlagForTether(readBoolean2);
                    return true;
                case 19:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface =
                                readStrongBinder.queryLocalInterface(
                                        "com.android.internal.net.INetdTetherEventListener");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface instanceof INetdTetherEventListener)) {
                            INetdTetherEventListener$Stub$Proxy
                                    iNetdTetherEventListener$Stub$Proxy =
                                            new INetdTetherEventListener$Stub$Proxy();
                            iNetdTetherEventListener$Stub$Proxy.mRemote = readStrongBinder;
                            iNetdTetherEventListener = iNetdTetherEventListener$Stub$Proxy;
                        } else {
                            iNetdTetherEventListener =
                                    (INetdTetherEventListener) queryLocalInterface;
                        }
                    }
                    parcel.enforceNoDataAvail();
                    registerNetdTetherEventListener(iNetdTetherEventListener);
                    return true;
                case 20:
                    unregisterNetdTetherEventListener();
                    return true;
                case 21:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKnoxVpn(readInt17, readBoolean3);
                    return true;
                case 22:
                    int readInt18 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr7 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    networkAddUidRanges(readInt18, uidRangeParcelArr7);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt19 = parcel.readInt();
                    UidRangeParcel[] uidRangeParcelArr8 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    networkRemoveUidRanges(readInt19, uidRangeParcelArr8);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    knoxVpnBlockUserWideDnsQuery(readBoolean4, readInt20);
                    return true;
                case 25:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IInterface queryLocalInterface2 =
                                readStrongBinder2.queryLocalInterface(
                                        "com.android.internal.net.IDomainFilterEventListener");
                        if (queryLocalInterface2 == null
                                || !(queryLocalInterface2 instanceof IDomainFilterEventListener)) {
                            IDomainFilterEventListener$Stub$Proxy
                                    iDomainFilterEventListener$Stub$Proxy =
                                            new IDomainFilterEventListener$Stub$Proxy();
                            iDomainFilterEventListener$Stub$Proxy.mRemote = readStrongBinder2;
                            iDomainFilterEventListener = iDomainFilterEventListener$Stub$Proxy;
                        } else {
                            iDomainFilterEventListener =
                                    (IDomainFilterEventListener) queryLocalInterface2;
                        }
                    }
                    parcel.enforceNoDataAvail();
                    registerDomainFilterEventListener(iDomainFilterEventListener);
                    return true;
                case 26:
                    unregisterDomainFilterEventListener();
                    return true;
                case 27:
                    int readInt21 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateDomainFilterCache(readInt21, createStringArray);
                    return true;
                case 28:
                    int readInt22 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    String runKnoxRulesCommand = runKnoxRulesCommand(readInt22, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeString(runKnoxRulesCommand);
                    return true;
                case 29:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableIpOptionModification(readBoolean5);
                    return true;
                case 30:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enablePortInfoEntries(readInt23, readInt24, readInt25, readBoolean6);
                    return true;
                case 31:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String networkFilterTcpV4Entry =
                            getNetworkFilterTcpV4Entry(readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeString(networkFilterTcpV4Entry);
                    return true;
                case 32:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String networkFilterTcpV6Entry =
                            getNetworkFilterTcpV6Entry(readInt28, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeString(networkFilterTcpV6Entry);
                    return true;
                case 33:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String networkFilterUdpV6Entry =
                            getNetworkFilterUdpV6Entry(readInt30, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeString(networkFilterUdpV6Entry);
                    return true;
                case 34:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearNetworkFilterEntries(readInt32);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNwFilterNetId(readInt33, readInt34);
                    return true;
                case 36:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nwFilterNetId = getNwFilterNetId(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeInt(nwFilterNetId);
                    return true;
                case 37:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUserToNwFilterRange(readInt36);
                    return true;
                case 38:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUserFromNwFilterRange(readInt37);
                    return true;
                case 39:
                    UidRangeParcel[] uidRangeParcelArr9 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    exemptUidFromNwFilterRange(uidRangeParcelArr9);
                    return true;
                case 40:
                    UidRangeParcel[] uidRangeParcelArr10 =
                            (UidRangeParcel[]) parcel.createTypedArray(UidRangeParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeExemptUidFromNwFilterRange(uidRangeParcelArr10);
                    return true;
                case 41:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setKnoxNwFilterProxyApp(readInt38);
                    return true;
                case 42:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeKnoxNwFilterProxyApp(readInt39);
                    return true;
                case 43:
                    clearKnoxNwFilterProxyEntries();
                    return true;
                case 44:
                    int readInt40 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDnsCacheStatus(readInt40, readBoolean7);
                    return true;
                case 45:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHttpProxyPort(readInt41, readInt42);
                    return true;
                case 46:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isIptablesMatchEnabled = isIptablesMatchEnabled(readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(isIptablesMatchEnabled);
                    return true;
                case 47:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int enableTlsPacketTracing = enableTlsPacketTracing(readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableTlsPacketTracing);
                    return true;
                case 48:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int disableTlsPacketTracing = disableTlsPacketTracing(readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(disableTlsPacketTracing);
                    return true;
                case 49:
                    makeVideoCallChain();
                    return true;
                case 50:
                    String readString5 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startVideoStats(readString5, readInt43, readInt44);
                    return true;
                case 51:
                    String readString6 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopVideoStats(readString6, readInt45, readInt46);
                    return true;
                case 52:
                    String readString7 = parcel.readString();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TetherStatsParcel[] videoStats =
                            getVideoStats(readString7, readInt47, readInt48);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(videoStats, 1);
                    return true;
                case 53:
                    firewallBuild();
                    return true;
                case 54:
                    int readInt49 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    firewallSetRuleWifi(readInt49, readBoolean8);
                    return true;
                case 55:
                    int readInt50 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    firewallSetRuleMobileData(readInt50, readBoolean9);
                    return true;
                case 56:
                    int readInt51 = parcel.readInt();
                    String readString8 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gmsCoreSetUidUrlMobileDataRule(readInt51, readString8, readInt52);
                    return true;
                case 57:
                    int readInt53 = parcel.readInt();
                    String readString9 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gmsCoreSetUidUrlWifiRule(readInt53, readString9, readInt54);
                    return true;
                case 58:
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int prioritizeApp = prioritizeApp(readBoolean10, readInt55);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioritizeApp);
                    return true;
                case 59:
                    boolean readBoolean11 = parcel.readBoolean();
                    String readString10 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addApeRule = addApeRule(readBoolean11, readString10, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeInt(addApeRule);
                    return true;
                case 60:
                    String readString11 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int replaceApeRule = replaceApeRule(readString11, readInt57, readInt58);
                    parcel2.writeNoException();
                    parcel2.writeInt(replaceApeRule);
                    return true;
                case 61:
                    boolean readBoolean12 = parcel.readBoolean();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    tcSetTCRule(readBoolean12, readString12, readString13);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startQbox = startQbox(readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(startQbox);
                    return true;
                case 63:
                    int stopQbox = stopQbox();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopQbox);
                    return true;
                case 64:
                    int readInt59 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int qboxUid = setQboxUid(readInt59, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeInt(qboxUid);
                    return true;
                case 65:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startL4s = startL4s(readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(startL4s);
                    return true;
                case 66:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int stopL4s = stopL4s(readString16);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopL4s);
                    return true;
                case 67:
                    int l4sConnCount = getL4sConnCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(l4sConnCount);
                    return true;
                case 68:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startTosMarker(readString17);
                    return true;
                case 69:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopTosMarker(readString18);
                    return true;
                case 70:
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addTosPolicy(readInt60, readInt61);
                    return true;
                case 71:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeTosPolicy(readInt62);
                    return true;
                case 72:
                    clearTosMap();
                    return true;
                case 73:
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] tcpLocalPorts = getTcpLocalPorts(createIntArray4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(tcpLocalPorts);
                    return true;
                case 74:
                    String readString19 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    MBBStatsParcel[] dataUsage = getDataUsage(readString19, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dataUsage, 1);
                    return true;
                case 75:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long totalDataUsage = getTotalDataUsage(readString20);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalDataUsage);
                    return true;
                case 76:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int hotspotOn = hotspotOn(readString21);
                    parcel2.writeNoException();
                    parcel2.writeInt(hotspotOn);
                    return true;
                case 77:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int hotspotOff = hotspotOff(readString22);
                    parcel2.writeNoException();
                    parcel2.writeInt(hotspotOff);
                    return true;
                case 78:
                    String readString23 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    String readString24 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int pauseDevice =
                            pauseDevice(readString23, readBoolean14, readString24, readLong);
                    parcel2.writeNoException();
                    parcel2.writeInt(pauseDevice);
                    return true;
                case 79:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int flushArpEntry = flushArpEntry(readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(flushArpEntry);
                    return true;
                case 80:
                    int isMBBPathsPresent = isMBBPathsPresent();
                    parcel2.writeNoException();
                    parcel2.writeInt(isMBBPathsPresent);
                    return true;
                case 81:
                    String readString26 = parcel.readString();
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int prioEnable = prioEnable(readString26, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioEnable);
                    return true;
                case 82:
                    String readString27 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int prioUpdate = prioUpdate(readString27, readInt64);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioUpdate);
                    return true;
                case 83:
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int prioDisable = prioDisable(readString28);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioDisable);
                    return true;
                case 84:
                    int clearPriorityMap = clearPriorityMap();
                    parcel2.writeNoException();
                    parcel2.writeInt(clearPriorityMap);
                    return true;
                case 85:
                    boolean readBoolean15 = parcel.readBoolean();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int prioDevice =
                            prioDevice(
                                    readBoolean15,
                                    createStringArrayList2,
                                    createStringArrayList3,
                                    createStringArrayList4,
                                    createStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioDevice);
                    return true;
                case 86:
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList9 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList10 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int addIpToPrioList =
                            addIpToPrioList(
                                    createStringArrayList6,
                                    createStringArrayList7,
                                    createStringArrayList8,
                                    createStringArrayList9,
                                    createStringArrayList10);
                    parcel2.writeNoException();
                    parcel2.writeInt(addIpToPrioList);
                    return true;
                case 87:
                    ArrayList<String> createStringArrayList11 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList12 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList13 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList14 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList15 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int delIpToPrioList =
                            delIpToPrioList(
                                    createStringArrayList11,
                                    createStringArrayList12,
                                    createStringArrayList13,
                                    createStringArrayList14,
                                    createStringArrayList15);
                    parcel2.writeNoException();
                    parcel2.writeInt(delIpToPrioList);
                    return true;
                case 88:
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startTrafficStatsController = startTrafficStatsController(readString29);
                    parcel2.writeNoException();
                    parcel2.writeInt(startTrafficStatsController);
                    return true;
                case 89:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int stopTrafficStatsController = stopTrafficStatsController(readString30);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopTrafficStatsController);
                    return true;
                case 90:
                    UidStatsParcel[] uidTrafficStats = getUidTrafficStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(uidTrafficStats, 1);
                    return true;
                case 91:
                    UidStatsParcel[] mhsTrafficStats = getMhsTrafficStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(mhsTrafficStats, 1);
                    return true;
                case 92:
                    TrafficTimeStatsParcel trafficTimeStats = getTrafficTimeStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(trafficTimeStats, 1);
                    return true;
                case 93:
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String runTcpMonitorShellCommand =
                            runTcpMonitorShellCommand(readString31, readString32);
                    parcel2.writeNoException();
                    parcel2.writeString(runTcpMonitorShellCommand);
                    return true;
                case 94:
                    int readInt65 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    spegRestrictNetworkConnection(readInt65, readBoolean16);
                    return true;
                case 95:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addPortFwdRules(
                            readString33, readString34, readString35, readString36, readInt66);
                    return true;
                case 96:
                    int readInt67 = parcel.readInt();
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String runVpnRulesCommand =
                            runVpnRulesCommand(readInt67, readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeString(runVpnRulesCommand);
                    return true;
                case 97:
                    boolean readBoolean17 = parcel.readBoolean();
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    modifyEpdg(readBoolean17, readString39, readString40, readBoolean18);
                    return true;
                case 98:
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgInterfaceDropRule(readString41, readString42, readBoolean19);
                    return true;
                case 99:
                    String readString43 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    interfaceSetAutoConf(readString43, readBoolean20);
                    return true;
                case 100:
                    int readInt68 = parcel.readInt();
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    String readString46 = parcel.readString();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    networkAddLegacyRoute(
                            readInt68, readString44, readString45, readString46, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    int readInt70 = parcel.readInt();
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    networkRemoveLegacyRoute(
                            readInt70, readString47, readString48, readString49, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    makeBlockChildChain();
                    return true;
                case 103:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBlockAllDNSPackets(readBoolean21);
                    return true;
                case 104:
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockListIPs(readString50);
                    return true;
                case 105:
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowListIPs(readString51);
                    return true;
                case 106:
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockHostAlone(readString52);
                    return true;
                case 107:
                    String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAllowHostAlone(readString53);
                    return true;
                case 108:
                    cleanAllBlock();
                    return true;
                case 109:
                    setBlockAllPackets();
                    return true;
                case 110:
                    String readString54 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBlockPorts(readString54, readInt72, readString55);
                    return true;
                case 111:
                    cleanBlockPorts();
                    return true;
                case 112:
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOnlyAllowIPs(readString56);
                    return true;
                case 113:
                    cleanOnlyAllowIPs();
                    return true;
                case 114:
                    boolean readBoolean22 = parcel.readBoolean();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int prioritizeMnxbApp = prioritizeMnxbApp(readBoolean22, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeInt(prioritizeMnxbApp);
                    return true;
                case 115:
                    boolean readBoolean23 = parcel.readBoolean();
                    String readString57 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addMnxbRule = addMnxbRule(readBoolean23, readString57, readInt74);
                    parcel2.writeNoException();
                    parcel2.writeInt(addMnxbRule);
                    return true;
                case 116:
                    String readString58 = parcel.readString();
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int replaceMnxbRule = replaceMnxbRule(readString58, readInt75, readInt76);
                    parcel2.writeNoException();
                    parcel2.writeInt(replaceMnxbRule);
                    return true;
                case 117:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdvertiseWindowSize(readInt77);
                    return true;
                case 118:
                    long[] l4StatsGet = l4StatsGet();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(l4StatsGet);
                    return true;
                case 119:
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateClo(readString59);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deactivateClo(readString60);
                    parcel2.writeNoException();
                    return true;
                case 121:
                    activateCloGro();
                    parcel2.writeNoException();
                    return true;
                case 122:
                    deactivateCloGro();
                    parcel2.writeNoException();
                    return true;
                case 123:
                    ICloEventObserver asInterface2 =
                            ICloEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCloEventListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    unregisterCloEventListener();
                    parcel2.writeNoException();
                    return true;
                case 125:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateGroFlushTime(readLong2);
                    return true;
                case 126:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateGroPshOption(readInt78);
                    return true;
                case 127:
                    String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableMptcpModes(readString61);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    disableMptcpMode();
                    parcel2.writeNoException();
                    return true;
                case 129:
                    String readString62 = parcel.readString();
                    String readString63 = parcel.readString();
                    String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpSocksSkipRule(readString62, readString63, readString64);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    String readString65 = parcel.readString();
                    String readString66 = parcel.readString();
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpSocksSkipRule(readString65, readString66, readString67);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    String readString68 = parcel.readString();
                    String readString69 = parcel.readString();
                    String readString70 = parcel.readString();
                    int readInt79 = parcel.readInt();
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpSocksRule(
                            readString68, readString69, readString70, readInt79, readString71);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    String readString72 = parcel.readString();
                    String readString73 = parcel.readString();
                    String readString74 = parcel.readString();
                    int readInt80 = parcel.readInt();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpSocksRule(
                            readString72, readString73, readString74, readInt80, readString75);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpLinkIface(readString76);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpLinkIface(readString77);
                    parcel2.writeNoException();
                    return true;
                case 135:
                    String readString78 = parcel.readString();
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpChain(readString78, readString79);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    String readString80 = parcel.readString();
                    String readString81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpChain(readString80, readString81);
                    parcel2.writeNoException();
                    return true;
                case 137:
                    String readString82 = parcel.readString();
                    String readString83 = parcel.readString();
                    String readString84 = parcel.readString();
                    int readInt81 = parcel.readInt();
                    int readInt82 = parcel.readInt();
                    String readString85 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpUidSocksRule(
                            readString82,
                            readString83,
                            readString84,
                            readInt81,
                            readInt82,
                            readString85);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    String readString86 = parcel.readString();
                    String readString87 = parcel.readString();
                    String readString88 = parcel.readString();
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    String readString89 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpUidSocksRule(
                            readString86,
                            readString87,
                            readString88,
                            readInt83,
                            readInt84,
                            readString89);
                    parcel2.writeNoException();
                    return true;
                case 139:
                    String readString90 = parcel.readString();
                    int readInt85 = parcel.readInt();
                    String readString91 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addUidToMptcpChain(readString90, readInt85, readString91);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    String readString92 = parcel.readString();
                    int readInt86 = parcel.readInt();
                    String readString93 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeUidFromMptcpChain(readString92, readInt86, readString93);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    String readString94 = parcel.readString();
                    String readString95 = parcel.readString();
                    String readString96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpIpAcceptRule(readString94, readString95, readString96);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    String readString97 = parcel.readString();
                    String readString98 = parcel.readString();
                    String readString99 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    delMptcpIpAcceptRule(readString97, readString98, readString99);
                    parcel2.writeNoException();
                    return true;
                case 143:
                    String readString100 = parcel.readString();
                    String readString101 = parcel.readString();
                    String readString102 = parcel.readString();
                    int readInt87 = parcel.readInt();
                    String readString103 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpSocksSkipRuleProto(
                            readString100, readString101, readString102, readInt87, readString103);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    String readString104 = parcel.readString();
                    String readString105 = parcel.readString();
                    String readString106 = parcel.readString();
                    int readInt88 = parcel.readInt();
                    String readString107 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeMptcpSocksSkipRuleProto(
                            readString104, readString105, readString106, readInt88, readString107);
                    parcel2.writeNoException();
                    return true;
                case 145:
                    String readString108 = parcel.readString();
                    String readString109 = parcel.readString();
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addMptcpSourcePortAcceptRule(readString108, readString109, readInt89);
                    parcel2.writeNoException();
                    return true;
                case 146:
                    String readString110 = parcel.readString();
                    String readString111 = parcel.readString();
                    int readInt90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    delMptcpSourcePortAcceptRule(readString110, readString111, readInt90);
                    parcel2.writeNoException();
                    return true;
                case 147:
                    String readString112 = parcel.readString();
                    String readString113 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMptcpTcpBufferSize(readString112, readString113);
                    parcel2.writeNoException();
                    return true;
                case 148:
                    String readString114 = parcel.readString();
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMtuValueMptcp(readString114, readInt91);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    boolean readBoolean24 = parcel.readBoolean();
                    String readString115 = parcel.readString();
                    String readString116 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateMptcpSourceRule(readBoolean24, readString115, readString116);
                    parcel2.writeNoException();
                    return true;
                case 150:
                    boolean readBoolean25 = parcel.readBoolean();
                    String readString117 = parcel.readString();
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMptcpPrivateIpRoute(readBoolean25, readString117, readInt92);
                    parcel2.writeNoException();
                    return true;
                case FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE /* 151 */:
                    boolean readBoolean26 = parcel.readBoolean();
                    String readString118 = parcel.readString();
                    String readString119 = parcel.readString();
                    int readInt93 = parcel.readInt();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMptcpDestBaseMarkRule(
                            readBoolean26, readString118, readString119, readInt93, readInt94);
                    parcel2.writeNoException();
                    return true;
                case FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_STARTED /* 152 */:
                    String readString120 = parcel.readString();
                    String readString121 = parcel.readString();
                    String readString122 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addMptcpSourceRoute(readString120, readString121, readString122);
                    parcel2.writeNoException();
                    return true;
                case FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED /* 153 */:
                    String readString123 = parcel.readString();
                    String readString124 = parcel.readString();
                    String readString125 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    delMptcpSourceRoute(readString123, readString124, readString125);
                    parcel2.writeNoException();
                    return true;
                case FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ORGANIZATION_OWNED_MANAGED_PROFILE /* 154 */:
                    boolean readBoolean27 = parcel.readBoolean();
                    String readString126 = parcel.readString();
                    int readInt95 = parcel.readInt();
                    String readString127 = parcel.readString();
                    String readString128 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMptcpUIDRoute(
                            readBoolean27, readString126, readInt95, readString127, readString128);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void activateClo(String str) throws RemoteException;

    void activateCloGro() throws RemoteException;

    int addApeRule(boolean z, String str, int i) throws RemoteException;

    int addIpToPrioList(List list, List list2, List list3, List list4, List list5)
            throws RemoteException;

    int addMnxbRule(boolean z, String str, int i) throws RemoteException;

    void addMptcpChain(String str, String str2) throws RemoteException;

    void addMptcpIpAcceptRule(String str, String str2, String str3) throws RemoteException;

    void addMptcpLinkIface(String str) throws RemoteException;

    void addMptcpSocksRule(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void addMptcpSocksSkipRule(String str, String str2, String str3) throws RemoteException;

    void addMptcpSocksSkipRuleProto(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void addMptcpSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

    void addMptcpSourceRoute(String str, String str2, String str3) throws RemoteException;

    void addMptcpUidSocksRule(String str, String str2, String str3, int i, int i2, String str4)
            throws RemoteException;

    void addPortFwdRules(String str, String str2, String str3, String str4, int i)
            throws RemoteException;

    void addTosPolicy(int i, int i2) throws RemoteException;

    void addUidToMptcpChain(String str, int i, String str2) throws RemoteException;

    void addUserToNwFilterRange(int i) throws RemoteException;

    void cleanAllBlock() throws RemoteException;

    void cleanBlockPorts() throws RemoteException;

    void cleanOnlyAllowIPs() throws RemoteException;

    void clearEbpfMap(int i) throws RemoteException;

    void clearKnoxNwFilterProxyEntries() throws RemoteException;

    void clearNetworkFilterEntries(int i) throws RemoteException;

    int clearPriorityMap() throws RemoteException;

    void clearTosMap() throws RemoteException;

    void deactivateClo(String str) throws RemoteException;

    void deactivateCloGro() throws RemoteException;

    int delIpToPrioList(List list, List list2, List list3, List list4, List list5)
            throws RemoteException;

    void delMptcpIpAcceptRule(String str, String str2, String str3) throws RemoteException;

    void delMptcpSourcePortAcceptRule(String str, String str2, int i) throws RemoteException;

    void delMptcpSourceRoute(String str, String str2, String str3) throws RemoteException;

    void disableMptcpMode() throws RemoteException;

    int disableTlsPacketTracing(String str) throws RemoteException;

    void enableIpOptionModification(boolean z) throws RemoteException;

    void enableKnoxVpnFlagForTether(boolean z) throws RemoteException;

    void enableMptcpModes(String str) throws RemoteException;

    void enablePortInfoEntries(int i, int i2, int i3, boolean z) throws RemoteException;

    int enableTlsPacketTracing(String str) throws RemoteException;

    void exemptUidFromNwFilterRange(UidRangeParcel[] uidRangeParcelArr) throws RemoteException;

    void firewallBuild() throws RemoteException;

    void firewallSetRuleMobileData(int i, boolean z) throws RemoteException;

    void firewallSetRuleWifi(int i, boolean z) throws RemoteException;

    int flushArpEntry(String str) throws RemoteException;

    MBBStatsParcel[] getDataUsage(String str, List list) throws RemoteException;

    int getL4sConnCount() throws RemoteException;

    UidStatsParcel[] getMhsTrafficStats() throws RemoteException;

    String getNetworkFilterTcpV4Entry(int i, int i2) throws RemoteException;

    String getNetworkFilterTcpV6Entry(int i, int i2) throws RemoteException;

    String getNetworkFilterUdpV6Entry(int i, int i2) throws RemoteException;

    int getNwFilterNetId(int i) throws RemoteException;

    int[] getTcpLocalPorts(int[] iArr) throws RemoteException;

    long getTotalDataUsage(String str) throws RemoteException;

    TrafficTimeStatsParcel getTrafficTimeStats() throws RemoteException;

    UidStatsParcel[] getUidTrafficStats() throws RemoteException;

    TetherStatsParcel[] getVideoStats(String str, int i, int i2) throws RemoteException;

    void gmsCoreSetUidUrlMobileDataRule(int i, String str, int i2) throws RemoteException;

    void gmsCoreSetUidUrlWifiRule(int i, String str, int i2) throws RemoteException;

    int hotspotOff(String str) throws RemoteException;

    int hotspotOn(String str) throws RemoteException;

    void interfaceSetAutoConf(String str, boolean z) throws RemoteException;

    boolean isAlive() throws RemoteException;

    int isIptablesMatchEnabled(String str) throws RemoteException;

    int isMBBPathsPresent() throws RemoteException;

    void knoxVpnBlockDnsQueriesForUid(int i, UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    void knoxVpnBlockUserWideDnsQuery(boolean z, int i) throws RemoteException;

    void knoxVpnDestroyBlockedKnoxNetwork() throws RemoteException;

    void knoxVpnExemptDnsQueryForUid(int i, UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    void knoxVpnExemptUidFromKnoxVpn(int i, UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    void knoxVpnInsertUidForDnsAuthorization(int[] iArr) throws RemoteException;

    void knoxVpnRemoveExemptUidFromKnoxVpn(int i, UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    void knoxVpnRemoveExemptedDnsQueryForUid(int i, UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    void knoxVpnRemoveUidFromDnsAuthorization() throws RemoteException;

    void knoxVpnUnblockDnsQueriesForUid(int i, UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    long[] l4StatsGet() throws RemoteException;

    void makeBlockChildChain() throws RemoteException;

    void makeVideoCallChain() throws RemoteException;

    void modifyEpdg(boolean z, String str, String str2, boolean z2) throws RemoteException;

    void networkAddLegacyRoute(int i, String str, String str2, String str3, int i2)
            throws RemoteException;

    void networkAddUidRanges(int i, UidRangeParcel[] uidRangeParcelArr) throws RemoteException;

    void networkRemoveLegacyRoute(int i, String str, String str2, String str3, int i2)
            throws RemoteException;

    void networkRemoveUidRanges(int i, UidRangeParcel[] uidRangeParcelArr) throws RemoteException;

    int pauseDevice(String str, boolean z, String str2, long j) throws RemoteException;

    int prioDevice(boolean z, List list, List list2, List list3, List list4) throws RemoteException;

    int prioDisable(String str) throws RemoteException;

    int prioEnable(String str, int i) throws RemoteException;

    int prioUpdate(String str, int i) throws RemoteException;

    int prioritizeApp(boolean z, int i) throws RemoteException;

    int prioritizeMnxbApp(boolean z, int i) throws RemoteException;

    void registerCloEventListener(ICloEventObserver iCloEventObserver) throws RemoteException;

    void registerDomainFilterEventListener(IDomainFilterEventListener iDomainFilterEventListener)
            throws RemoteException;

    void registerNetdTetherEventListener(INetdTetherEventListener iNetdTetherEventListener)
            throws RemoteException;

    void registerOemUnsolicitedEventListener(
            IOemNetdUnsolicitedEventListener iOemNetdUnsolicitedEventListener)
            throws RemoteException;

    void removeExemptUidFromNwFilterRange(UidRangeParcel[] uidRangeParcelArr)
            throws RemoteException;

    void removeKnoxNwFilterProxyApp(int i) throws RemoteException;

    void removeMptcpChain(String str, String str2) throws RemoteException;

    void removeMptcpLinkIface(String str) throws RemoteException;

    void removeMptcpSocksRule(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void removeMptcpSocksSkipRule(String str, String str2, String str3) throws RemoteException;

    void removeMptcpSocksSkipRuleProto(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    void removeMptcpUidSocksRule(String str, String str2, String str3, int i, int i2, String str4)
            throws RemoteException;

    void removeTosPolicy(int i) throws RemoteException;

    void removeUidFromMptcpChain(String str, int i, String str2) throws RemoteException;

    void removeUserFromNwFilterRange(int i) throws RemoteException;

    int replaceApeRule(String str, int i, int i2) throws RemoteException;

    int replaceMnxbRule(String str, int i, int i2) throws RemoteException;

    String runKnoxFirewallRulesCommand(int i, String str) throws RemoteException;

    String runKnoxRulesCommand(int i, String[] strArr) throws RemoteException;

    String runTcpMonitorShellCommand(String str, String str2) throws RemoteException;

    String runVpnRulesCommand(int i, String str, String str2) throws RemoteException;

    void setAdvertiseWindowSize(int i) throws RemoteException;

    void setAllowHostAlone(String str) throws RemoteException;

    void setAllowListIPs(String str) throws RemoteException;

    void setBlockAllDNSPackets(boolean z) throws RemoteException;

    void setBlockAllPackets() throws RemoteException;

    void setBlockHostAlone(String str) throws RemoteException;

    void setBlockListIPs(String str) throws RemoteException;

    void setBlockPorts(String str, int i, String str2) throws RemoteException;

    void setDnsCacheStatus(int i, boolean z) throws RemoteException;

    void setEpdgInterfaceDropRule(String str, String str2, boolean z) throws RemoteException;

    void setHttpProxyPort(int i, int i2) throws RemoteException;

    void setKnoxNwFilterProxyApp(int i) throws RemoteException;

    void setKnoxVpn(int i, boolean z) throws RemoteException;

    void setMptcpDestBaseMarkRule(boolean z, String str, String str2, int i, int i2)
            throws RemoteException;

    void setMptcpPrivateIpRoute(boolean z, String str, int i) throws RemoteException;

    void setMptcpTcpBufferSize(String str, String str2) throws RemoteException;

    void setMptcpUIDRoute(boolean z, String str, int i, String str2, String str3)
            throws RemoteException;

    void setMtuValueMptcp(String str, int i) throws RemoteException;

    void setNetworkInfo(int i, boolean z, int i2) throws RemoteException;

    void setNwFilterNetId(int i, int i2) throws RemoteException;

    void setOnlyAllowIPs(String str) throws RemoteException;

    int setQboxUid(int i, boolean z) throws RemoteException;

    void spegRestrictNetworkConnection(int i, boolean z) throws RemoteException;

    int startL4s(String str) throws RemoteException;

    int startQbox(String str) throws RemoteException;

    void startTosMarker(String str) throws RemoteException;

    int startTrafficStatsController(String str) throws RemoteException;

    void startVideoStats(String str, int i, int i2) throws RemoteException;

    int stopL4s(String str) throws RemoteException;

    int stopQbox() throws RemoteException;

    void stopTosMarker(String str) throws RemoteException;

    int stopTrafficStatsController(String str) throws RemoteException;

    void stopVideoStats(String str, int i, int i2) throws RemoteException;

    void tcSetTCRule(boolean z, String str, String str2) throws RemoteException;

    void unregisterCloEventListener() throws RemoteException;

    void unregisterDomainFilterEventListener() throws RemoteException;

    void unregisterNetdTetherEventListener() throws RemoteException;

    void updateDomainFilterCache(int i, String[] strArr) throws RemoteException;

    void updateGroFlushTime(long j) throws RemoteException;

    void updateGroPshOption(int i) throws RemoteException;

    void updateInputFilterAppWideRules(int[] iArr, int i, int i2) throws RemoteException;

    void updateInputFilterExemptRules(int i, int i2) throws RemoteException;

    void updateInputFilterUserWideRules(int[] iArr, int i, int i2) throws RemoteException;

    void updateMptcpSourceRule(boolean z, String str, String str2) throws RemoteException;
}
