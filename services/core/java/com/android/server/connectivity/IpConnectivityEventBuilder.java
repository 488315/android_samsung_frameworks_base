package com.android.server.connectivity;

import android.net.ConnectivityMetricsEvent;
import android.net.metrics.ApfProgramEvent;
import android.net.metrics.ApfStats;
import android.net.metrics.ConnectStats;
import android.net.metrics.DefaultNetworkEvent;
import android.net.metrics.DhcpClientEvent;
import android.net.metrics.DhcpErrorEvent;
import android.net.metrics.DnsEvent;
import android.net.metrics.IpManagerEvent;
import android.net.metrics.IpReachabilityEvent;
import android.net.metrics.NetworkEvent;
import android.net.metrics.RaEvent;
import android.net.metrics.ValidationProbeEvent;
import android.net.metrics.WakeupStats;
import android.util.SparseIntArray;

import com.android.server.connectivity.metrics.nano.IpConnectivityLogClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class IpConnectivityEventBuilder {
    public static final int[] IFNAME_LINKLAYERS;
    public static final String[] IFNAME_PREFIXES;
    public static final SparseIntArray TRANSPORT_LINKLAYER_MAP;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        TRANSPORT_LINKLAYER_MAP = sparseIntArray;
        sparseIntArray.append(0, 2);
        sparseIntArray.append(1, 4);
        sparseIntArray.append(2, 1);
        sparseIntArray.append(3, 3);
        sparseIntArray.append(4, 0);
        sparseIntArray.append(5, 8);
        sparseIntArray.append(6, 9);
        IFNAME_PREFIXES = new String[] {"rmnet", "wlan", "bt-pan", "p2p", "aware", "eth", "wpan"};
        IFNAME_LINKLAYERS = new int[] {2, 4, 1, 7, 8, 3, 9};
    }

    public static IpConnectivityLogClass.IpConnectivityEvent buildEvent(int i, String str, long j) {
        IpConnectivityLogClass.IpConnectivityEvent ipConnectivityEvent =
                new IpConnectivityLogClass.IpConnectivityEvent();
        ipConnectivityEvent.networkId = i;
        ipConnectivityEvent.transports = j;
        if (str != null) {
            ipConnectivityEvent.ifName = str;
        }
        int i2 = 0;
        if (j != 0) {
            int bitCount = Long.bitCount(j);
            if (bitCount != 0) {
                if (bitCount != 1) {
                    i2 = 6;
                } else {
                    i2 = TRANSPORT_LINKLAYER_MAP.get(Long.numberOfTrailingZeros(j), 0);
                }
            }
        } else {
            String str2 = ipConnectivityEvent.ifName;
            if (str2 != null) {
                int i3 = 0;
                while (true) {
                    if (i3 >= 7) {
                        break;
                    }
                    if (str2.startsWith(IFNAME_PREFIXES[i3])) {
                        i2 = IFNAME_LINKLAYERS[i3];
                        break;
                    }
                    i3++;
                }
            }
        }
        if (i2 != 0) {
            ipConnectivityEvent.linkLayer = i2;
            ipConnectivityEvent.ifName = "";
        }
        return ipConnectivityEvent;
    }

    public static byte[] serialize(int i, List list) {
        IpConnectivityLogClass.IpConnectivityLog ipConnectivityLog =
                new IpConnectivityLogClass.IpConnectivityLog();
        ArrayList arrayList = (ArrayList) list;
        IpConnectivityLogClass.IpConnectivityEvent[] ipConnectivityEventArr =
                (IpConnectivityLogClass.IpConnectivityEvent[])
                        arrayList.toArray(
                                new IpConnectivityLogClass.IpConnectivityEvent[arrayList.size()]);
        ipConnectivityLog.events = ipConnectivityEventArr;
        ipConnectivityLog.droppedEvents = i;
        if (ipConnectivityEventArr.length > 0 || i > 0) {
            ipConnectivityLog.version = 2;
        }
        return IpConnectivityLogClass.IpConnectivityLog.toByteArray(ipConnectivityLog);
    }

    public static IpConnectivityLogClass.Pair[] toPairArray(SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        IpConnectivityLogClass.Pair[] pairArr = new IpConnectivityLogClass.Pair[size];
        for (int i = 0; i < size; i++) {
            IpConnectivityLogClass.Pair pair = new IpConnectivityLogClass.Pair();
            pair.key = sparseIntArray.keyAt(i);
            pair.value = sparseIntArray.valueAt(i);
            pairArr[i] = pair;
        }
        return pairArr;
    }

    public static IpConnectivityLogClass.IpConnectivityEvent toProto(ConnectStats connectStats) {
        IpConnectivityLogClass.ConnectStatistics connectStatistics =
                new IpConnectivityLogClass.ConnectStatistics();
        connectStatistics.connectCount = connectStats.connectCount;
        connectStatistics.connectBlockingCount = connectStats.connectBlockingCount;
        connectStatistics.ipv6AddrCount = connectStats.ipv6ConnectCount;
        connectStatistics.latenciesMs = connectStats.latencies.toArray();
        connectStatistics.errnosCounters = toPairArray(connectStats.errnos);
        IpConnectivityLogClass.IpConnectivityEvent buildEvent =
                buildEvent(connectStats.netId, null, connectStats.transports);
        buildEvent.setConnectStatistics(connectStatistics);
        return buildEvent;
    }

    public static IpConnectivityLogClass.IpConnectivityEvent toProto(
            DefaultNetworkEvent defaultNetworkEvent) {
        IpConnectivityLogClass.DefaultNetworkEvent defaultNetworkEvent2 =
                new IpConnectivityLogClass.DefaultNetworkEvent();
        defaultNetworkEvent2.finalScore = defaultNetworkEvent.finalScore;
        defaultNetworkEvent2.initialScore = defaultNetworkEvent.initialScore;
        boolean z = defaultNetworkEvent.ipv4;
        defaultNetworkEvent2.ipSupport =
                (z && defaultNetworkEvent.ipv6) ? 3 : defaultNetworkEvent.ipv6 ? 2 : z ? 1 : 0;
        defaultNetworkEvent2.defaultNetworkDurationMs = defaultNetworkEvent.durationMs;
        defaultNetworkEvent2.validationDurationMs = defaultNetworkEvent.validatedMs;
        long j = defaultNetworkEvent.previousTransports;
        int bitCount = Long.bitCount(j);
        defaultNetworkEvent2.previousDefaultNetworkLinkLayer =
                bitCount != 0
                        ? bitCount != 1
                                ? 6
                                : TRANSPORT_LINKLAYER_MAP.get(Long.numberOfTrailingZeros(j), 0)
                        : 0;
        IpConnectivityLogClass.IpConnectivityEvent buildEvent =
                buildEvent(defaultNetworkEvent.netId, null, defaultNetworkEvent.transports);
        if (defaultNetworkEvent.transports == 0) {
            buildEvent.linkLayer = 5;
        }
        buildEvent.setDefaultNetworkEvent(defaultNetworkEvent2);
        return buildEvent;
    }

    public static IpConnectivityLogClass.IpConnectivityEvent toProto(DnsEvent dnsEvent) {
        IpConnectivityLogClass.DNSLookupBatch dNSLookupBatch =
                new IpConnectivityLogClass.DNSLookupBatch();
        dnsEvent.resize(dnsEvent.eventCount);
        byte[] bArr = dnsEvent.eventTypes;
        int[] iArr = new int[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            iArr[i] = bArr[i] & 255;
        }
        dNSLookupBatch.eventTypes = iArr;
        byte[] bArr2 = dnsEvent.returnCodes;
        int[] iArr2 = new int[bArr2.length];
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            iArr2[i2] = bArr2[i2] & 255;
        }
        dNSLookupBatch.returnCodes = iArr2;
        dNSLookupBatch.latenciesMs = dnsEvent.latenciesMs;
        IpConnectivityLogClass.IpConnectivityEvent buildEvent =
                buildEvent(dnsEvent.netId, null, dnsEvent.transports);
        buildEvent.setDnsLookupBatch(dNSLookupBatch);
        return buildEvent;
    }

    public static IpConnectivityLogClass.IpConnectivityEvent toProto(WakeupStats wakeupStats) {
        IpConnectivityLogClass.WakeupStats wakeupStats2 = new IpConnectivityLogClass.WakeupStats();
        wakeupStats.updateDuration();
        wakeupStats2.durationSec = wakeupStats.durationSec;
        wakeupStats2.totalWakeups = wakeupStats.totalWakeups;
        wakeupStats2.rootWakeups = wakeupStats.rootWakeups;
        wakeupStats2.systemWakeups = wakeupStats.systemWakeups;
        wakeupStats2.nonApplicationWakeups = wakeupStats.nonApplicationWakeups;
        wakeupStats2.applicationWakeups = wakeupStats.applicationWakeups;
        wakeupStats2.noUidWakeups = wakeupStats.noUidWakeups;
        wakeupStats2.l2UnicastCount = wakeupStats.l2UnicastCount;
        wakeupStats2.l2MulticastCount = wakeupStats.l2MulticastCount;
        wakeupStats2.l2BroadcastCount = wakeupStats.l2BroadcastCount;
        wakeupStats2.ethertypeCounts = toPairArray(wakeupStats.ethertypes);
        wakeupStats2.ipNextHeaderCounts = toPairArray(wakeupStats.ipNextHeaders);
        IpConnectivityLogClass.IpConnectivityEvent buildEvent =
                buildEvent(0, wakeupStats.iface, 0L);
        buildEvent.setWakeupStats(wakeupStats2);
        return buildEvent;
    }

    public static List toProto(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ConnectivityMetricsEvent connectivityMetricsEvent =
                    (ConnectivityMetricsEvent) it.next();
            IpConnectivityLogClass.IpConnectivityEvent buildEvent =
                    buildEvent(
                            connectivityMetricsEvent.netId,
                            connectivityMetricsEvent.ifname,
                            connectivityMetricsEvent.transports);
            buildEvent.timeMs = connectivityMetricsEvent.timestamp;
            DhcpErrorEvent dhcpErrorEvent = connectivityMetricsEvent.data;
            if (dhcpErrorEvent instanceof DhcpErrorEvent) {
                IpConnectivityLogClass.DHCPEvent dHCPEvent = new IpConnectivityLogClass.DHCPEvent();
                dHCPEvent.setErrorCode(dhcpErrorEvent.errorCode);
                buildEvent.setDhcpEvent(dHCPEvent);
            } else if (dhcpErrorEvent instanceof DhcpClientEvent) {
                DhcpClientEvent dhcpClientEvent = (DhcpClientEvent) dhcpErrorEvent;
                IpConnectivityLogClass.DHCPEvent dHCPEvent2 =
                        new IpConnectivityLogClass.DHCPEvent();
                dHCPEvent2.setStateTransition(dhcpClientEvent.msg);
                dHCPEvent2.durationMs = dhcpClientEvent.durationMs;
                buildEvent.setDhcpEvent(dHCPEvent2);
            } else if (dhcpErrorEvent instanceof IpManagerEvent) {
                IpManagerEvent ipManagerEvent = (IpManagerEvent) dhcpErrorEvent;
                IpConnectivityLogClass.IpProvisioningEvent ipProvisioningEvent =
                        new IpConnectivityLogClass.IpProvisioningEvent();
                ipProvisioningEvent.eventType = ipManagerEvent.eventType;
                ipProvisioningEvent.latencyMs = (int) ipManagerEvent.durationMs;
                buildEvent.setIpProvisioningEvent(ipProvisioningEvent);
            } else if (dhcpErrorEvent instanceof IpReachabilityEvent) {
                IpConnectivityLogClass.IpReachabilityEvent ipReachabilityEvent =
                        new IpConnectivityLogClass.IpReachabilityEvent();
                ipReachabilityEvent.eventType = ((IpReachabilityEvent) dhcpErrorEvent).eventType;
                buildEvent.setIpReachabilityEvent(ipReachabilityEvent);
            } else if (dhcpErrorEvent instanceof NetworkEvent) {
                NetworkEvent networkEvent = (NetworkEvent) dhcpErrorEvent;
                IpConnectivityLogClass.NetworkEvent networkEvent2 =
                        new IpConnectivityLogClass.NetworkEvent();
                networkEvent2.eventType = networkEvent.eventType;
                networkEvent2.latencyMs = (int) networkEvent.durationMs;
                buildEvent.setNetworkEvent(networkEvent2);
            } else if (dhcpErrorEvent instanceof ValidationProbeEvent) {
                ValidationProbeEvent validationProbeEvent = (ValidationProbeEvent) dhcpErrorEvent;
                IpConnectivityLogClass.ValidationProbeEvent validationProbeEvent2 =
                        new IpConnectivityLogClass.ValidationProbeEvent();
                validationProbeEvent2.latencyMs = (int) validationProbeEvent.durationMs;
                validationProbeEvent2.probeType = validationProbeEvent.probeType;
                validationProbeEvent2.probeResult = validationProbeEvent.returnCode;
                buildEvent.setValidationProbeEvent(validationProbeEvent2);
            } else if (dhcpErrorEvent instanceof ApfProgramEvent) {
                ApfProgramEvent apfProgramEvent = (ApfProgramEvent) dhcpErrorEvent;
                IpConnectivityLogClass.ApfProgramEvent apfProgramEvent2 =
                        new IpConnectivityLogClass.ApfProgramEvent();
                apfProgramEvent2.lifetime = apfProgramEvent.lifetime;
                apfProgramEvent2.effectiveLifetime = apfProgramEvent.actualLifetime;
                apfProgramEvent2.filteredRas = apfProgramEvent.filteredRas;
                apfProgramEvent2.currentRas = apfProgramEvent.currentRas;
                apfProgramEvent2.programLength = apfProgramEvent.programLength;
                int i = apfProgramEvent.flags;
                if ((i & 1) != 0) {
                    apfProgramEvent2.dropMulticast = true;
                }
                if ((i & 2) != 0) {
                    apfProgramEvent2.hasIpv4Addr = true;
                }
                buildEvent.setApfProgramEvent(apfProgramEvent2);
            } else if (dhcpErrorEvent instanceof ApfStats) {
                ApfStats apfStats = (ApfStats) dhcpErrorEvent;
                IpConnectivityLogClass.ApfStatistics apfStatistics =
                        new IpConnectivityLogClass.ApfStatistics();
                apfStatistics.durationMs = apfStats.durationMs;
                apfStatistics.receivedRas = apfStats.receivedRas;
                apfStatistics.matchingRas = apfStats.matchingRas;
                apfStatistics.droppedRas = apfStats.droppedRas;
                apfStatistics.zeroLifetimeRas = apfStats.zeroLifetimeRas;
                apfStatistics.parseErrors = apfStats.parseErrors;
                apfStatistics.programUpdates = apfStats.programUpdates;
                apfStatistics.programUpdatesAll = apfStats.programUpdatesAll;
                apfStatistics.programUpdatesAllowingMulticast =
                        apfStats.programUpdatesAllowingMulticast;
                apfStatistics.maxProgramSize = apfStats.maxProgramSize;
                buildEvent.setApfStatistics(apfStatistics);
            } else if (dhcpErrorEvent instanceof RaEvent) {
                RaEvent raEvent = (RaEvent) dhcpErrorEvent;
                IpConnectivityLogClass.RaEvent raEvent2 = new IpConnectivityLogClass.RaEvent();
                raEvent2.routerLifetime = raEvent.routerLifetime;
                raEvent2.prefixValidLifetime = raEvent.prefixValidLifetime;
                raEvent2.prefixPreferredLifetime = raEvent.prefixPreferredLifetime;
                raEvent2.routeInfoLifetime = raEvent.routeInfoLifetime;
                raEvent2.rdnssLifetime = raEvent.rdnssLifetime;
                raEvent2.dnsslLifetime = raEvent.dnsslLifetime;
                buildEvent.setRaEvent(raEvent2);
            } else {
                buildEvent = null;
            }
            if (buildEvent != null) {
                arrayList.add(buildEvent);
            }
        }
        return arrayList;
    }
}
