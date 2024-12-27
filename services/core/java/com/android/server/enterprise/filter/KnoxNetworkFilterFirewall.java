package com.android.server.enterprise.filter;

import android.net.INetd;
import android.os.Binder;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.net.IOemNetd;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.enterprise.utils.NetdHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KnoxNetworkFilterFirewall {
    public static boolean IS_CONNBYTE_EXTENSION_PRESENT;
    public static KnoxNetworkFilterFirewall mKnoxNwFilterFw;
    public INetd mNetdService;
    public IOemNetd mOemNetdService;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class IpRestoreActionType {
        public static final /* synthetic */ IpRestoreActionType[] $VALUES;
        public static final IpRestoreActionType APPEND;
        public static final IpRestoreActionType DELETE;
        public static final IpRestoreActionType INSERT;
        public static final IpRestoreActionType REMOVE_CHAIN;

        static {
            IpRestoreActionType ipRestoreActionType = new IpRestoreActionType("APPEND", 0);
            APPEND = ipRestoreActionType;
            IpRestoreActionType ipRestoreActionType2 = new IpRestoreActionType("INSERT", 1);
            INSERT = ipRestoreActionType2;
            IpRestoreActionType ipRestoreActionType3 = new IpRestoreActionType("DELETE", 2);
            DELETE = ipRestoreActionType3;
            IpRestoreActionType ipRestoreActionType4 = new IpRestoreActionType("REMOVE_CHAIN", 3);
            REMOVE_CHAIN = ipRestoreActionType4;
            $VALUES =
                    new IpRestoreActionType[] {
                        ipRestoreActionType,
                        ipRestoreActionType2,
                        ipRestoreActionType3,
                        ipRestoreActionType4
                    };
        }

        public static IpRestoreActionType valueOf(String str) {
            return (IpRestoreActionType) Enum.valueOf(IpRestoreActionType.class, str);
        }

        public static IpRestoreActionType[] values() {
            return (IpRestoreActionType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IpRestoreParam {
        public final String actionChain;
        public IpRestoreActionType actionType;
        public final String firstParam;
        public final String jumpChain;
        public final String secondParam;

        public IpRestoreParam(
                String str,
                String str2,
                String str3,
                String str4,
                IpRestoreActionType ipRestoreActionType) {
            this.actionChain = str;
            this.firstParam = str2;
            this.jumpChain = str3;
            this.secondParam = str4;
            this.actionType = ipRestoreActionType;
        }
    }

    public KnoxNetworkFilterFirewall() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int isIptablesMatchEnabled =
                        getOemNetdService().isIptablesMatchEnabled("connbytes");
                Log.d(
                        "knoxNwFilter-KnoxNetworkFilterFirewall",
                        "isIptablesExtensionPresent: connbytes " + isIptablesMatchEnabled);
                if (isIptablesMatchEnabled == 0) {
                    IS_CONNBYTE_EXTENSION_PRESENT = true;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void applyTcpRedirectRulesForCaptivePortal(int i, int i2) {
        if (i2 <= 0) {
            return;
        }
        String str = UserHandle.getUserId(i) + "_knox_nwfilter_cp_rdt";
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new IpRestoreParam(
                        str,
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, " -m owner --uid-owner ", " -o lo -p tcp --dport 80 "),
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder("REDIRECT --to-port "), i2),
                        "",
                        IpRestoreActionType.APPEND));
        insertRules(4, "*nat", null, arrayList, true);
    }

    public static void blockAppGeneratedDOT(int i) {
        int i2;
        int i3;
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dot_drop");
        if (i == 0) {
            i3 = 10000;
            i2 = 19999;
        } else {
            int i4 = i * 100000;
            i2 = i4 + 99999;
            i3 = i4 + 1;
        }
        String str = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2;
        ArrayList arrayList = new ArrayList();
        String m2 =
                XmlUtils$$ExternalSyntheticOutline0.m(
                        " -m owner --uid-owner ", str, " -p udp --dport 853 ");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList.add(new IpRestoreParam(m, m2, "DROP", "", ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        m,
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                " -m owner --uid-owner ", str, " -p tcp --dport 853 "),
                        "DROP",
                        "",
                        ipRestoreActionType));
        insertRules(46, "*filter", null, arrayList, true);
    }

    public static List createChainsCmd(String str, Map map) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = (HashMap) map;
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
        }
        List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
        for (Map.Entry entry : hashMap.entrySet()) {
            Iterator it2 = ((List) entry.getValue()).iterator();
            while (it2.hasNext()) {
                ((ArrayList) createTableHeaderCmd)
                        .add(
                                parseIptablesRestoreCmd(
                                        new IpRestoreParam(
                                                (String) entry.getKey(),
                                                "",
                                                (String) it2.next(),
                                                "",
                                                IpRestoreActionType.INSERT)));
            }
        }
        ((ArrayList) createTableHeaderCmd).add("COMMIT\n");
        return createTableHeaderCmd;
    }

    public static Map createFilterMap(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(
                "OUTPUT",
                Arrays.asList(
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                i, "_knox_nwfilter_unauth_drop"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dot_drop"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dns_drop"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_app_act")));
        return hashMap;
    }

    public static void createIpTableChains(int i) {
        runIptablesRestoreCmd(46, removeChainsCmd("*filter", createFilterMap(i)));
        runIptablesRestoreCmd(46, createChainsCmd("*filter", createFilterMap(i)));
        runIptablesRestoreCmd(4, removeChainsCmd("*nat", createNatMap(i)));
        runIptablesRestoreCmd(4, createChainsCmd("*nat", createNatMap(i)));
        runIptablesRestoreCmd(6, removeChainsCmd("*mangle", createMangleMapList(i)));
        runIptablesRestoreCmd(6, createChainsCmd("*mangle", createMangleMapList(i)));
    }

    public static Map createMangleMapList(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(
                "OUTPUT",
                Arrays.asList(
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_tcp_mark"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_mark"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dns_mark"),
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                i, "_knox_nwfilter_udp_exempt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_app_act")));
        hashMap.put(
                "PREROUTING",
                Arrays.asList(
                        i + "_knox_nwfilter_tcp_rdt",
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_rdt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dns_rdt")));
        return hashMap;
    }

    public static Map createNatMap(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(
                "OUTPUT",
                Arrays.asList(
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_rdt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                i, "_knox_nwfilter_udp_exempt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dns_rdt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_app_rdt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_tcp_rdt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_cp_rdt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                i, "_knox_nwfilter_dns_exempt"),
                        NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_app_act")));
        return hashMap;
    }

    public static List createTableHeaderCmd(String str, List list) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append(str);
        sb.append("\n");
        arrayList.add(sb.toString());
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str2 != null) {
                    sb.setLength(0);
                    sb.append(":");
                    sb.append(str2);
                    sb.append(" -");
                    sb.append("\n");
                    arrayList.add(sb.toString());
                }
            }
        }
        return arrayList;
    }

    public static void denyUnAuthorizedPackets(int i, String str, List list) {
        Iterator it;
        String str2;
        String str3;
        String str4;
        String str5 = "*filter";
        if (list == null || list.isEmpty()) {
            return;
        }
        String str6 = UserHandle.getUserId(i) + "_knox_nwfilter_unauth_drop";
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            boolean equalsIgnoreCase = str.equalsIgnoreCase("dns");
            IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
            char c = 1;
            char c2 = 0;
            String str7 = " -m owner ! --uid-owner ";
            String str8 = " --dport ";
            if (equalsIgnoreCase) {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String[] split = ((String) it2.next()).split("_");
                    String str9 = split[c2];
                    String str10 = split[c];
                    if (InetAddress.getByName(str9) instanceof Inet4Address) {
                        str3 = str8;
                        str2 = str5;
                        str4 = str7;
                        arrayList.add(
                                new IpRestoreParam(
                                        str6,
                                        " -p udp -d "
                                                + str9
                                                + str8
                                                + str10
                                                + str7
                                                + Integer.toString(i),
                                        "DROP",
                                        "",
                                        ipRestoreActionType));
                    } else {
                        str2 = str5;
                        str3 = str8;
                        str4 = str7;
                        arrayList2.add(
                                new IpRestoreParam(
                                        str6,
                                        " -p udp -d "
                                                + str9
                                                + str3
                                                + str10
                                                + str4
                                                + Integer.toString(i),
                                        "DROP",
                                        "",
                                        ipRestoreActionType));
                    }
                    str7 = str4;
                    str8 = str3;
                    str5 = str2;
                    c = 1;
                    c2 = 0;
                }
            }
            String str11 = str5;
            String str12 = str8;
            String str13 = str7;
            if (str.equalsIgnoreCase("tcp")) {
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {
                    String[] split2 = ((String) it3.next()).split("_");
                    String str14 = split2[0];
                    String str15 = split2[1];
                    if (InetAddress.getByName(str14) instanceof Inet4Address) {
                        it = it3;
                        arrayList.add(
                                new IpRestoreParam(
                                        str6,
                                        " -p tcp -m state --state NEW -d "
                                                + str14
                                                + str12
                                                + str15
                                                + str13
                                                + Integer.toString(i),
                                        "DROP",
                                        "",
                                        ipRestoreActionType));
                    } else {
                        it = it3;
                        arrayList2.add(
                                new IpRestoreParam(
                                        str6,
                                        " -p tcp -m state --state NEW -d "
                                                + str14
                                                + str12
                                                + str15
                                                + str13
                                                + Integer.toString(i),
                                        "DROP",
                                        "",
                                        ipRestoreActionType));
                    }
                    it3 = it;
                }
            }
            if (str.equalsIgnoreCase("udp")) {
                Iterator it4 = list.iterator();
                while (it4.hasNext()) {
                    String[] split3 = ((String) it4.next()).split("_");
                    String str16 = split3[0];
                    String str17 = split3[1];
                    if (InetAddress.getByName(str16) instanceof Inet4Address) {
                        arrayList.add(
                                new IpRestoreParam(
                                        str6,
                                        " -p udp -d "
                                                + str16
                                                + str12
                                                + str17
                                                + str13
                                                + Integer.toString(i),
                                        "DROP",
                                        "",
                                        ipRestoreActionType));
                    } else {
                        arrayList2.add(
                                new IpRestoreParam(
                                        str6,
                                        " -p udp -d "
                                                + str16
                                                + str12
                                                + str17
                                                + str13
                                                + Integer.toString(i),
                                        "DROP",
                                        "",
                                        ipRestoreActionType));
                    }
                }
            }
            insertRules(4, str11, null, arrayList, true);
            insertRules(6, str11, null, arrayList2, true);
        } catch (UnknownHostException unused) {
        }
    }

    public static void exemptUdpPacketFromNwFilterRange(int i, int i2) {
        int i3;
        int i4;
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_exempt");
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            i4 = 10000;
            i3 = 19999;
        } else {
            int i5 = i * 100000;
            i3 = i5 + 99999;
            i4 = i5 + 1;
        }
        String str = i4 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3;
        String m2 =
                XmlUtils$$ExternalSyntheticOutline0.m(
                        " -m owner --uid-owner ", str, " -p udp --dport 5353 ");
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList.add(new IpRestoreParam(m, m2, "ACCEPT", "", ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        m,
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                " -m owner --uid-owner ", str, " -p udp --dport 1900 "),
                        "ACCEPT",
                        "",
                        ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        m,
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                " -m owner --uid-owner ",
                                str,
                                " -p udp -m pkttype --pkt-type multicast "),
                        "ACCEPT",
                        "",
                        ipRestoreActionType));
        if (i2 == 2) {
            insertRules(4, "*nat", null, arrayList, true);
        }
        if (i2 == 10) {
            insertRules(6, "*mangle", null, arrayList, true);
        }
    }

    public static void flushAppGeneratedDOTBlockRules(int i) {
        insertRule(46, "*filter", i + "_knox_nwfilter_dot_drop");
    }

    public static void flushAppGeneratedRedirectTcpConnRules(int i) {
        insertRule(4, "*nat", i + "_knox_nwfilter_tcp_rdt");
    }

    public static void flushAppGeneratedRedirectUdpConnRules(int i) {
        insertRule(4, "*nat", i + "_knox_nwfilter_udp_rdt");
    }

    public static void flushAppGeneratedRedirectV6TcpConnRules(int i) {
        insertRule(6, "*mangle", i + "_knox_nwfilter_tcp_mark");
        insertRule(6, "*mangle", i + "_knox_nwfilter_tcp_rdt");
        if (i == 0) {
            StringBuilder sb = new StringBuilder("ip -6 route del ");
            sb.append(" local default dev lo table " + Integer.toString(62));
            sb.append(";ip -6 rule del ");
            sb.append(
                    " from all fwmark "
                            + Integer.toString(62)
                            + " table "
                            + Integer.toString(62)
                            + " prio 10");
            sb.append(";");
            runSingleCommand(sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder("ip -6 route del ");
        sb2.append(" local default dev lo table " + Integer.toString(63));
        sb2.append(";ip -6 rule del ");
        sb2.append(
                " from all fwmark "
                        + Integer.toString(63)
                        + " table "
                        + Integer.toString(63)
                        + " prio 10");
        sb2.append(";");
        runSingleCommand(sb2.toString());
    }

    public static void flushAppGeneratedRedirectV6UdpConnRules(int i) {
        insertRule(6, "*mangle", i + "_knox_nwfilter_udp_mark");
        insertRule(6, "*mangle", i + "_knox_nwfilter_udp_rdt");
        if (i == 0) {
            StringBuilder sb = new StringBuilder("ip -6 route del ");
            sb.append(" local default dev lo table " + Integer.toString(62));
            sb.append(";ip -6 rule del ");
            sb.append(
                    " from all fwmark "
                            + Integer.toString(62)
                            + " table "
                            + Integer.toString(62)
                            + " prio 10");
            sb.append(";");
            runSingleCommand(sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder("ip -6 route del ");
        sb2.append(" local default dev lo table " + Integer.toString(63));
        sb2.append(";ip -6 rule del ");
        sb2.append(
                " from all fwmark "
                        + Integer.toString(63)
                        + " table "
                        + Integer.toString(63)
                        + " prio 10");
        sb2.append(";");
        runSingleCommand(sb2.toString());
    }

    public static void flushExemptDnsRulesFromNat(int i) {
        insertRule(4, "*nat", i + "_knox_nwfilter_dns_exempt");
        insertRule(46, "*filter", i + "_knox_nwfilter_dns_drop");
    }

    public static void flushRedirectDnsQueryRules(int i) {
        insertRule(4, "*nat", i + "_knox_nwfilter_dns_rdt");
    }

    public static void flushRedirectV6DnsQueryRules(int i) {
        insertRule(6, "*mangle", i + "_knox_nwfilter_dns_mark");
        insertRule(6, "*mangle", i + "_knox_nwfilter_dns_rdt");
    }

    public static void flushTcpRedirectRulesForCaptivePortal(int i) {
        insertRule(4, "*nat", i + "_knox_nwfilter_cp_rdt");
    }

    public static void flushUdpPacketExemptRules(int i, int i2) {
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_exempt");
        if (i2 == 2) {
            insertRule(4, "*nat", m);
        }
        if (i2 == 10) {
            insertRule(6, "*mangle", m);
        }
    }

    public static void flushUnAuthorizedPackets(int i) {
        insertRule(46, "*filter", i + "_knox_nwfilter_unauth_drop");
    }

    public static void insertRule(int i, String str, String str2) {
        insertRules(
                i, str, Collections.singletonList(str2), Collections.singletonList(null), false);
    }

    public static void insertRules(int i, String str, List list, List list2, boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(createTableHeaderCmd(str, list));
        if (z) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                IpRestoreParam ipRestoreParam = (IpRestoreParam) it.next();
                if (ipRestoreParam != null) {
                    IpRestoreActionType ipRestoreActionType = ipRestoreParam.actionType;
                    ArrayList arrayList2 = new ArrayList();
                    ipRestoreParam.actionType = IpRestoreActionType.DELETE;
                    arrayList2.addAll(arrayList);
                    arrayList2.add(parseIptablesRestoreCmd(ipRestoreParam));
                    arrayList2.add("COMMIT\n");
                    runIptablesRestoreCmd(i, arrayList2);
                    ipRestoreParam.actionType = ipRestoreActionType;
                }
            }
        }
        if (list2 != null) {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.addAll(arrayList);
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                IpRestoreParam ipRestoreParam2 = (IpRestoreParam) it2.next();
                if (ipRestoreParam2 != null) {
                    arrayList3.add(parseIptablesRestoreCmd(ipRestoreParam2));
                }
            }
            arrayList = arrayList3;
        }
        arrayList.add("COMMIT\n");
        runIptablesRestoreCmd(i, arrayList);
    }

    public static String parseIptablesRestoreCmd(IpRestoreParam ipRestoreParam) {
        StringBuilder sb = new StringBuilder();
        IpRestoreActionType ipRestoreActionType = ipRestoreParam.actionType;
        if (ipRestoreActionType == null) {
            return "";
        }
        int ordinal = ipRestoreActionType.ordinal();
        String str = ipRestoreParam.secondParam;
        String str2 = ipRestoreParam.jumpChain;
        String str3 = ipRestoreParam.firstParam;
        String str4 = ipRestoreParam.actionChain;
        if (ordinal == 0) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "-A ", str4, str3, " -j ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str2, str, "\n");
        } else if (ordinal == 1) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "-I ", str4, str3, " -j ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str2, str, "\n");
        } else if (ordinal == 2) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, "-D ", str4, str3, " -j ");
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, str2, str, "\n");
        } else if (ordinal != 3) {
            sb = null;
        } else {
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, "-X ", str4, "\n");
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }

    public static void redirectAppGeneratedTcpConn(int i, int i2, String str) {
        int i3;
        int i4;
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_tcp_rdt");
        if (i == 0) {
            i4 = 10000;
            i3 = 19999;
        } else {
            int i5 = i * 100000;
            i3 = i5 + 99999;
            i4 = i5 + 1;
        }
        String str2 = i4 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3;
        ArrayList arrayList = new ArrayList();
        boolean isEmpty = str.isEmpty();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        if (isEmpty) {
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str2,
                                    " ! -o lo -p tcp -m state --state NEW "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("REDIRECT --to-port "), i2),
                            "",
                            ipRestoreActionType));
        } else {
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str2,
                                    " -o lo -p tcp -d ",
                                    str,
                                    " -m state --state NEW "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("REDIRECT --to-port "), i2),
                            "",
                            ipRestoreActionType));
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str2,
                                    " ! -o lo -p tcp -m state --state NEW "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("REDIRECT --to-port "), i2),
                            "",
                            ipRestoreActionType));
        }
        insertRules(4, "*nat", null, arrayList, true);
    }

    public static void redirectAppGeneratedUdpConn(int i, int i2, String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_rdt");
        ArrayList arrayList = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        if (i == 0) {
            if (IS_CONNBYTE_EXTENSION_PRESENT) {
                if (str.isEmpty()) {
                    str5 = " -p udp -m connmark --mark ";
                    str4 = "REDIRECT --to-port ";
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    " -m owner --uid-owner 10000-19999 ! -o lo  -p udp -m state"
                                        + " --state NEW -m connbytes --connbytes 1:1"
                                        + " --connbytes-dir original --connbytes-mode packets ",
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                } else {
                    str5 = " -p udp -m connmark --mark ";
                    str4 = "REDIRECT --to-port ";
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner 10000-19999 -o lo -d ",
                                            str,
                                            " -p udp -m state --state NEW -m connbytes --connbytes"
                                                + " 1:1 --connbytes-dir original --connbytes-mode"
                                                + " packets "),
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    " -m owner --uid-owner 10000-19999 ! -o lo  -p udp -m state"
                                        + " --state NEW -m connbytes --connbytes 1:1"
                                        + " --connbytes-dir original --connbytes-mode packets ",
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                }
                str3 = str5;
            } else {
                str3 = " -p udp -m connmark --mark ";
                str4 = "REDIRECT --to-port ";
                if (str.isEmpty()) {
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    " -m owner --uid-owner 10000-19999 ! -o lo  -p udp -m state"
                                        + " --state NEW ",
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                } else {
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            " -m owner --uid-owner 10000-19999 -o lo -d ",
                                            str,
                                            " -p udp -m state --state NEW "),
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    " -m owner --uid-owner 10000-19999 ! -o lo  -p udp -m state"
                                        + " --state NEW ",
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                }
            }
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            ProcessList$$ExternalSyntheticOutline0.m(new StringBuilder(str3), 62),
                            ProcessList$$ExternalSyntheticOutline0.m(new StringBuilder(str4), i2),
                            "",
                            ipRestoreActionType));
            insertRules(4, "*nat", null, arrayList, true);
            return;
        }
        int i3 = i * 100000;
        String str6 = (i3 + 1) + PackageManagerShellCommandDataLoader.STDIN_PATH + (i3 + 99999);
        if (!IS_CONNBYTE_EXTENSION_PRESENT) {
            str2 = "*nat";
            if (str.isEmpty()) {
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str6,
                                        " ! -o lo  -p udp -m state --state NEW "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
            } else {
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str6,
                                        " -o lo -d ",
                                        str,
                                        " -p udp -m state --state NEW "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str6,
                                        " ! -o lo  -p udp -m state --state NEW "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
            }
        } else if (str.isEmpty()) {
            str2 = "*nat";
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str6,
                                    " ! -o lo  -p udp -m state --state NEW -m connbytes --connbytes"
                                        + " 1:1 --connbytes-dir original --connbytes-mode packets"
                                        + " "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("CONNMARK --set-mark "), 63),
                            "",
                            ipRestoreActionType));
        } else {
            str2 = "*nat";
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str6,
                                    " -o lo -d ",
                                    str,
                                    " -p udp -m state --state NEW -m connbytes --connbytes 1:1"
                                        + " --connbytes-dir original --connbytes-mode packets "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("CONNMARK --set-mark "), 63),
                            "",
                            ipRestoreActionType));
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str6,
                                    " ! -o lo  -p udp -m state --state NEW -m connbytes --connbytes"
                                        + " 1:1 --connbytes-dir original --connbytes-mode packets"
                                        + " "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("CONNMARK --set-mark "), 63),
                            "",
                            ipRestoreActionType));
        }
        arrayList.add(
                new IpRestoreParam(
                        m,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p udp -m connmark --mark "), 63),
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder("REDIRECT --to-port "), i2),
                        "",
                        ipRestoreActionType));
        insertRules(4, str2, null, arrayList, true);
    }

    public static void redirectAppGeneratedV6TcpConn(int i, int i2, String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_tcp_mark");
        String m2 = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_tcp_rdt");
        ArrayList arrayList = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        if (i == 0) {
            if (str.isEmpty()) {
                str7 = ";ip -6 rule add ";
                str8 = " from all fwmark ";
                str9 = " table ";
                str10 = " prio 10";
                str11 = ";";
                str12 = " local default dev lo table ";
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                " -m owner --uid-owner 10000-19999 ! -o lo -p tcp -m state --state"
                                    + " NEW ",
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 62),
                                "",
                                ipRestoreActionType));
            } else {
                str12 = " local default dev lo table ";
                str7 = ";ip -6 rule add ";
                str8 = " from all fwmark ";
                str9 = " table ";
                str10 = " prio 10";
                str11 = ";";
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                " -m owner --uid-owner 10000-19999 ! -o lo -p tcp -m state --state"
                                    + " NEW ",
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 62),
                                "",
                                ipRestoreActionType));
            }
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -p tcp -m connmark --mark "), 62),
                            "CONNMARK --restore-mark",
                            "",
                            ipRestoreActionType));
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -p tcp -m connmark --mark "), 62),
                            "ACCEPT",
                            "",
                            ipRestoreActionType));
            arrayList.add(
                    new IpRestoreParam(
                            m2,
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -p tcp --syn -i lo -m mark --mark "), 62),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("TPROXY --on-port "), i2),
                            "",
                            ipRestoreActionType));
            insertRules(6, "*mangle", null, arrayList, true);
            StringBuilder sb = new StringBuilder("ip -6 route add ");
            sb.append(str12 + Integer.toString(62));
            sb.append(str7);
            sb.append(str8 + Integer.toString(62) + str9 + Integer.toString(62) + str10);
            sb.append(str11);
            runSingleCommand(sb.toString());
            return;
        }
        int i3 = i * 100000;
        String str13 = (i3 + 1) + PackageManagerShellCommandDataLoader.STDIN_PATH + (i3 + 99999);
        if (str.isEmpty()) {
            str2 = "ip -6 route add ";
            str3 = "*mangle";
            str4 = " local default dev lo table ";
            str5 = ";ip -6 rule add ";
            str6 = " from all fwmark ";
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str13,
                                    " ! -o lo -p tcp -m state --state NEW "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("CONNMARK --set-mark "), 63),
                            "",
                            ipRestoreActionType));
        } else {
            str6 = " from all fwmark ";
            str5 = ";ip -6 rule add ";
            str4 = " local default dev lo table ";
            str2 = "ip -6 route add ";
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    " -m owner --uid-owner ",
                                    str13,
                                    " ! -o lo -p tcp -m state --state NEW "),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("CONNMARK --set-mark "), 63),
                            "",
                            ipRestoreActionType));
            str3 = "*mangle";
        }
        arrayList.add(
                new IpRestoreParam(
                        m,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p tcp -m connmark --mark "), 63),
                        "CONNMARK --restore-mark",
                        "",
                        ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        m,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p tcp -m connmark --mark "), 63),
                        "ACCEPT",
                        "",
                        ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        m2,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p tcp --syn -i lo -m mark --mark "), 63),
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder("TPROXY --on-port "), i2),
                        "",
                        ipRestoreActionType));
        insertRules(6, str3, null, arrayList, true);
        StringBuilder sb2 = new StringBuilder(str2);
        sb2.append(str4 + Integer.toString(63));
        sb2.append(str5);
        sb2.append(str6 + Integer.toString(63) + " table " + Integer.toString(63) + " prio 10");
        sb2.append(";");
        runSingleCommand(sb2.toString());
    }

    public static void redirectAppGeneratedV6UdpConn(int i, int i2, String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        String str12;
        String str13;
        String str14;
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_mark");
        String m2 = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_udp_rdt");
        ArrayList arrayList = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        if (i == 0) {
            if (!IS_CONNBYTE_EXTENSION_PRESENT) {
                str9 = ";ip -6 rule add ";
                str10 = " from all fwmark ";
                str11 = " table ";
                str12 = " prio 10";
                str13 = ";";
                str14 = " local default dev lo table ";
                if (str.isEmpty()) {
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    " -m owner --uid-owner 10000-19999 ! -o lo -p udp -m state"
                                        + " --state NEW ",
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                } else {
                    arrayList.add(
                            new IpRestoreParam(
                                    m,
                                    " -m owner --uid-owner 10000-19999 ! -o lo -p udp -m state"
                                        + " --state NEW ",
                                    ProcessList$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("CONNMARK --set-mark "), 62),
                                    "",
                                    ipRestoreActionType));
                }
            } else if (str.isEmpty()) {
                str9 = ";ip -6 rule add ";
                str10 = " from all fwmark ";
                str11 = " table ";
                str12 = " prio 10";
                str13 = ";";
                str14 = " local default dev lo table ";
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                " -m owner --uid-owner 10000-19999 ! -o lo -p udp -m state --state"
                                    + " NEW -m connbytes --connbytes 1:1 --connbytes-dir original"
                                    + " --connbytes-mode packets ",
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 62),
                                "",
                                ipRestoreActionType));
            } else {
                str14 = " local default dev lo table ";
                str9 = ";ip -6 rule add ";
                str10 = " from all fwmark ";
                str11 = " table ";
                str12 = " prio 10";
                str13 = ";";
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                " -m owner --uid-owner 10000-19999 ! -o lo -p udp -m state --state"
                                    + " NEW -m connbytes --connbytes 1:1 --connbytes-dir original"
                                    + " --connbytes-mode packets ",
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 62),
                                "",
                                ipRestoreActionType));
            }
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -p udp -m connmark --mark "), 62),
                            "CONNMARK --restore-mark",
                            "",
                            ipRestoreActionType));
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -p udp -m connmark --mark "), 62),
                            "ACCEPT",
                            "",
                            ipRestoreActionType));
            arrayList.add(
                    new IpRestoreParam(
                            m2,
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -p udp -i lo -m mark --mark "), 62),
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("TPROXY --on-port "), i2),
                            "",
                            ipRestoreActionType));
            insertRules(6, "*mangle", null, arrayList, true);
            StringBuilder sb = new StringBuilder("ip -6 route add ");
            sb.append(str14 + Integer.toString(62));
            sb.append(str9);
            sb.append(str10 + Integer.toString(62) + str11 + Integer.toString(62) + str12);
            sb.append(str13);
            runSingleCommand(sb.toString());
            return;
        }
        int i3 = i * 100000;
        String str15 = (i3 + 1) + PackageManagerShellCommandDataLoader.STDIN_PATH + (i3 + 99999);
        if (IS_CONNBYTE_EXTENSION_PRESENT) {
            if (str.isEmpty()) {
                str2 = " local default dev lo table ";
                str4 = m2;
                str5 = ";ip -6 rule add ";
                str6 = " from all fwmark ";
                str7 = "*mangle";
                str8 = "ip -6 route add ";
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str15,
                                        " ! -o lo -p udp -m state --state NEW -m connbytes"
                                            + " --connbytes 1:1 --connbytes-dir original"
                                            + " --connbytes-mode packets "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
            } else {
                str4 = m2;
                str6 = " from all fwmark ";
                str5 = ";ip -6 rule add ";
                str2 = " local default dev lo table ";
                str8 = "ip -6 route add ";
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str15,
                                        " ! -o lo -p udp -m state --state NEW -m connbytes"
                                            + " --connbytes 1:1 --connbytes-dir original"
                                            + " --connbytes-mode packets "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
                str7 = "*mangle";
            }
            str3 = str8;
        } else {
            str2 = " local default dev lo table ";
            str3 = "ip -6 route add ";
            str4 = m2;
            str5 = ";ip -6 rule add ";
            str6 = " from all fwmark ";
            str7 = "*mangle";
            if (str.isEmpty()) {
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str15,
                                        " ! -o lo -p udp -m state --state NEW "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
            } else {
                arrayList.add(
                        new IpRestoreParam(
                                m,
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        " -m owner --uid-owner ",
                                        str15,
                                        " ! -o lo -p udp -m state --state NEW "),
                                ProcessList$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("CONNMARK --set-mark "), 63),
                                "",
                                ipRestoreActionType));
            }
        }
        arrayList.add(
                new IpRestoreParam(
                        m,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p udp -m connmark --mark "), 63),
                        "CONNMARK --restore-mark",
                        "",
                        ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        m,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p udp -m connmark --mark "), 63),
                        "ACCEPT",
                        "",
                        ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        str4,
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder(" -p udp -i lo -m mark --mark "), 63),
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder("TPROXY --on-port "), i2),
                        "",
                        ipRestoreActionType));
        insertRules(6, str7, null, arrayList, true);
        StringBuilder sb2 = new StringBuilder(str3);
        sb2.append(str2 + Integer.toString(63));
        sb2.append(str5);
        sb2.append(str6 + Integer.toString(63) + " table " + Integer.toString(63) + " prio 10");
        sb2.append(";");
        runSingleCommand(sb2.toString());
    }

    public static void redirectDnsQuery(int i, int i2) {
        String m = NandswapManager$$ExternalSyntheticOutline0.m(i, "_knox_nwfilter_dns_rdt");
        ArrayList arrayList = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        if (i == 0) {
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            " -o lo -p udp --dport 53 -d 127.0.0.1",
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("REDIRECT --to-port "), i2),
                            "",
                            ipRestoreActionType));
        } else {
            arrayList.add(
                    new IpRestoreParam(
                            m,
                            " -o lo -p udp --dport 53 -d 127.0.0.2",
                            ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("REDIRECT --to-port "), i2),
                            "",
                            ipRestoreActionType));
        }
        insertRules(4, "*nat", null, arrayList, true);
    }

    public static List removeChainsCmd(String str, Map map) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = (HashMap) map;
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
        }
        List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
        for (Map.Entry entry : hashMap.entrySet()) {
            Iterator it2 = ((List) entry.getValue()).iterator();
            while (it2.hasNext()) {
                ((ArrayList) createTableHeaderCmd)
                        .add(
                                parseIptablesRestoreCmd(
                                        new IpRestoreParam(
                                                (String) entry.getKey(),
                                                "",
                                                (String) it2.next(),
                                                "",
                                                IpRestoreActionType.DELETE)));
            }
        }
        Iterator it3 = hashMap.entrySet().iterator();
        while (it3.hasNext()) {
            Iterator it4 = ((List) ((Map.Entry) it3.next()).getValue()).iterator();
            while (it4.hasNext()) {
                ((ArrayList) createTableHeaderCmd)
                        .add(
                                parseIptablesRestoreCmd(
                                        new IpRestoreParam(
                                                (String) it4.next(),
                                                null,
                                                null,
                                                null,
                                                IpRestoreActionType.REMOVE_CHAIN)));
            }
        }
        ((ArrayList) createTableHeaderCmd).add("COMMIT\n");
        return createTableHeaderCmd;
    }

    public static void removeIpTableChains(int i) {
        runIptablesRestoreCmd(46, removeChainsCmd("*filter", createFilterMap(i)));
        runIptablesRestoreCmd(4, removeChainsCmd("*nat", createNatMap(i)));
        runIptablesRestoreCmd(6, removeChainsCmd("*mangle", createMangleMapList(i)));
    }

    public static void removeTcpRedirectRulesForCaptivePortal(int i, int i2) {
        if (i2 <= 0) {
            return;
        }
        String str = UserHandle.getUserId(i) + "_knox_nwfilter_cp_rdt";
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new IpRestoreParam(
                        str,
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, " -m owner --uid-owner ", " -o lo -p tcp --dport 80 "),
                        ProcessList$$ExternalSyntheticOutline0.m(
                                new StringBuilder("REDIRECT --to-port "), i2),
                        "",
                        IpRestoreActionType.DELETE));
        insertRules(4, "*nat", null, arrayList, true);
    }

    public static void runIptablesRestoreCmd(int i, List list) {
        String join = String.join("", list);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d(
                        "knoxNwFilter-KnoxNetworkFilterFirewall",
                        "testing: command " + join + "target " + i);
                INetworkManagementService.Stub.asInterface(
                                ServiceManager.getService("network_management"))
                        .runKnoxFirewallRulesCommand(i, join);
            } catch (Exception e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterFirewall",
                        "Failed to run cmd: " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void runSingleCommand(String str) {
        String[] split;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Map map = NetdHelper.allowedCommands;
                split =
                        (str == null || str.isEmpty())
                                ? null
                                : str.trim().split(NetdHelper.CMD_DELIMITER);
            } catch (RemoteException e) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterFirewall",
                        "runSingleCommand error " + e.getMessage());
            }
            if (split == null) {
                Log.e(
                        "knoxNwFilter-KnoxNetworkFilterFirewall",
                        "Error splitting commands " + split);
            } else {
                for (String str2 : split) {
                    INetworkManagementService.Stub.asInterface(
                                    ServiceManager.getService("network_management"))
                            .runKnoxRulesCommand(
                                    NetdHelper.getCmdNumber(str2).intValue(),
                                    NetdHelper.getCmdParams(str2));
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IOemNetd getOemNetdService() {
        boolean z;
        INetd asInterface;
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            try {
                asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
                this.mNetdService = asInterface;
            } catch (RemoteException unused) {
                this.mNetdService = null;
                z = false;
            }
            if (asInterface != null) {
                z = asInterface.isAlive();
                if (!z) {
                    Log.e(
                            "knoxNwFilter-KnoxNetworkFilterFirewall",
                            "Can't connect to NativeNetdService netd");
                }
            }
        }
        INetd iNetd = this.mNetdService;
        if (iNetd != null) {
            try {
                this.mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Failed to get OemNetd listener "),
                        "knoxNwFilter-KnoxNetworkFilterFirewall");
            }
        }
        return this.mOemNetdService;
    }
}
