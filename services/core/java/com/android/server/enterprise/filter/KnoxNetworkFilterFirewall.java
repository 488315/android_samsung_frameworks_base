package com.android.server.enterprise.filter;

import android.net.INetd;
import android.os.Binder;
import android.os.INetworkManagementService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.net.IOemNetd;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.utils.NetdHelper;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.net.Inet4Address;
import java.net.Inet6Address;
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

/* loaded from: classes2.dex */
public class KnoxNetworkFilterFirewall {
    public static boolean IS_CONNBYTE_EXTENSION_PRESENT = false;
    public static KnoxNetworkFilterFirewall mKnoxNwFilterFw;
    public INetd mNetdService;
    public IOemNetd mOemNetdService;
    public static final Map filterChains = createFilterMap();
    public static final Map natChains = createNatMap();
    public static final Map mangleChains = createMangleMapList();

    enum IpRestoreActionType {
        APPEND,
        INSERT,
        DELETE,
        REMOVE_CHAIN
    }

    public void flushAppGeneratedRedirectRules() {
    }

    public KnoxNetworkFilterFirewall() {
        createIpTableChains();
        isIptablesExtensionPresent("connbytes");
    }

    public static synchronized KnoxNetworkFilterFirewall getInstance() {
        KnoxNetworkFilterFirewall knoxNetworkFilterFirewall;
        synchronized (KnoxNetworkFilterFirewall.class) {
            if (mKnoxNwFilterFw == null) {
                mKnoxNwFilterFw = new KnoxNetworkFilterFirewall();
            }
            knoxNetworkFilterFirewall = mKnoxNwFilterFw;
        }
        return knoxNetworkFilterFirewall;
    }

    public final IOemNetd getOemNetdService() {
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            connectNativeNetdService();
        }
        INetd iNetd = this.mNetdService;
        if (iNetd != null) {
            try {
                this.mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
            } catch (RemoteException e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterFirewall", "Failed to get OemNetd listener " + e.getMessage());
            }
        }
        return this.mOemNetdService;
    }

    public final void connectNativeNetdService() {
        boolean z;
        INetd asInterface;
        try {
            asInterface = INetd.Stub.asInterface(ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
            this.mNetdService = asInterface;
        } catch (RemoteException unused) {
            this.mNetdService = null;
            z = false;
        }
        if (asInterface == null) {
            return;
        }
        z = asInterface.isAlive();
        if (z) {
            return;
        }
        Log.e("knoxNwFilter-KnoxNetworkFilterFirewall", "Can't connect to NativeNetdService netd");
    }

    public final INetworkManagementService getINetworkManagementService() {
        return INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    }

    public static Map createFilterMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(KnoxVpnFirewallHelper.OUTPUT_CHAIN, Arrays.asList("knox_nwfilter_unauth_drop", "knox_nwfilter_dot_drop", "knox_nwfilter_dns_drop", "knox_nwfilter_app_act"));
        return hashMap;
    }

    public static Map createNatMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(KnoxVpnFirewallHelper.OUTPUT_CHAIN, Arrays.asList("knox_nwfilter_udp_rdt", "knox_nwfilter_udp_exempt", "knox_nwfilter_dns_rdt", "knox_nwfilter_app_rdt", "knox_nwfilter_tcp_rdt", "knox_nwfilter_cp_rdt", "knox_nwfilter_dns_exempt", "knox_nwfilter_app_act"));
        return hashMap;
    }

    public static Map createMangleMapList() {
        HashMap hashMap = new HashMap();
        hashMap.put(KnoxVpnFirewallHelper.OUTPUT_CHAIN, Arrays.asList("knox_nwfilter_tcp_mark", "knox_nwfilter_udp_mark", "knox_nwfilter_dns_mark", "knox_nwfilter_udp_exempt", "knox_nwfilter_app_act"));
        hashMap.put(KnoxVpnFirewallHelper.PREROUTING_CHAIN, Arrays.asList("knox_nwfilter_tcp_rdt", "knox_nwfilter_udp_rdt", "knox_nwfilter_dns_rdt"));
        return hashMap;
    }

    public final void createIpTableChains() {
        Map map = filterChains;
        runIptablesRestoreCmd(removeChainsCmd(KnoxVpnFirewallHelper.TABLE_FILTER, map), 46);
        runIptablesRestoreCmd(createChainsCmd(KnoxVpnFirewallHelper.TABLE_FILTER, map), 46);
        Map map2 = natChains;
        runIptablesRestoreCmd(removeChainsCmd(KnoxVpnFirewallHelper.TABLE_NAT, map2), 4);
        runIptablesRestoreCmd(createChainsCmd(KnoxVpnFirewallHelper.TABLE_NAT, map2), 4);
        Map map3 = mangleChains;
        runIptablesRestoreCmd(removeChainsCmd(KnoxVpnFirewallHelper.TABLE_MANGLE, map3), 6);
        runIptablesRestoreCmd(createChainsCmd(KnoxVpnFirewallHelper.TABLE_MANGLE, map3), 6);
    }

    public final void runIptablesRestoreCmd(List list, int i) {
        runShellCommand(i, String.join("", list));
    }

    public final void runShellCommand(int i, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d("knoxNwFilter-KnoxNetworkFilterFirewall", "testing: command " + str + "target " + i);
                getINetworkManagementService().runKnoxFirewallRulesCommand(i, str);
            } catch (Exception e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterFirewall", "Failed to run cmd: " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class IpRestoreParam {
        public String actionChain;
        public IpRestoreActionType actionType;
        public String firstParam;
        public String jumpChain;
        public String secondParam;

        public IpRestoreParam(String str, String str2, String str3, String str4, IpRestoreActionType ipRestoreActionType) {
            this.actionChain = str;
            this.firstParam = str2;
            this.jumpChain = str3;
            this.secondParam = str4;
            this.actionType = ipRestoreActionType;
        }
    }

    public final void insertRule(boolean z, String str, String str2, IpRestoreParam ipRestoreParam, int i) {
        insertRules(z, str, Collections.singletonList(str2), Collections.singletonList(ipRestoreParam), i);
    }

    public final void insertRules(boolean z, String str, List list, List list2, int i) {
        List arrayList = new ArrayList();
        arrayList.addAll(createTableHeaderCmd(str, list));
        if (z) {
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                IpRestoreParam ipRestoreParam = (IpRestoreParam) it.next();
                if (ipRestoreParam != null) {
                    IpRestoreActionType ipRestoreActionType = ipRestoreParam.actionType;
                    List parseRuleCmd = parseRuleCmd(true, arrayList, ipRestoreParam);
                    parseRuleCmd.add(KnoxVpnFirewallHelper.COMMIT_CMD);
                    runIptablesRestoreCmd(parseRuleCmd, i);
                    ipRestoreParam.actionType = ipRestoreActionType;
                }
            }
        }
        if (list2 != null) {
            arrayList = parseRuleCmd(arrayList, list2);
        }
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        runIptablesRestoreCmd(arrayList, i);
    }

    public final List parseRuleCmd(boolean z, List list, IpRestoreParam ipRestoreParam) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            ipRestoreParam.actionType = IpRestoreActionType.DELETE;
        }
        arrayList.addAll(list);
        arrayList.add(parseIptablesRestoreCmd(ipRestoreParam));
        return arrayList;
    }

    public final List parseRuleCmd(List list, List list2) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            IpRestoreParam ipRestoreParam = (IpRestoreParam) it.next();
            if (ipRestoreParam != null) {
                arrayList.add(parseIptablesRestoreCmd(ipRestoreParam));
            }
        }
        return arrayList;
    }

    public final List createTableHeaderCmd(String str, List list) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append(str);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        arrayList.add(sb.toString());
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str2 != null) {
                    sb.setLength(0);
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                    sb.append(str2);
                    sb.append(" -");
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    arrayList.add(sb.toString());
                }
            }
        }
        return arrayList;
    }

    public final String parseIptablesRestoreCmd(IpRestoreParam ipRestoreParam) {
        StringBuilder sb = new StringBuilder();
        if (ipRestoreParam.actionType == null) {
            return "";
        }
        int i = AbstractC14201.f1683xf766e4bb[ipRestoreParam.actionType.ordinal()];
        if (i == 1) {
            sb.append("-A ");
            sb.append(ipRestoreParam.actionChain);
            sb.append(ipRestoreParam.firstParam);
            sb.append(" -j ");
            sb.append(ipRestoreParam.jumpChain);
            sb.append(ipRestoreParam.secondParam);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } else if (i == 2) {
            sb.append("-I ");
            sb.append(ipRestoreParam.actionChain);
            sb.append(ipRestoreParam.firstParam);
            sb.append(" -j ");
            sb.append(ipRestoreParam.jumpChain);
            sb.append(ipRestoreParam.secondParam);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } else if (i == 3) {
            sb.append("-D ");
            sb.append(ipRestoreParam.actionChain);
            sb.append(ipRestoreParam.firstParam);
            sb.append(" -j ");
            sb.append(ipRestoreParam.jumpChain);
            sb.append(ipRestoreParam.secondParam);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } else if (i != 4) {
            sb = null;
        } else {
            sb.append("-X ");
            sb.append(ipRestoreParam.actionChain);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }

    /* renamed from: com.android.server.enterprise.filter.KnoxNetworkFilterFirewall$1 */
    public abstract /* synthetic */ class AbstractC14201 {

        /* renamed from: $SwitchMap$com$android$server$enterprise$filter$KnoxNetworkFilterFirewall$IpRestoreActionType */
        public static final /* synthetic */ int[] f1683xf766e4bb;

        static {
            int[] iArr = new int[IpRestoreActionType.values().length];
            f1683xf766e4bb = iArr;
            try {
                iArr[IpRestoreActionType.APPEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1683xf766e4bb[IpRestoreActionType.INSERT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1683xf766e4bb[IpRestoreActionType.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1683xf766e4bb[IpRestoreActionType.REMOVE_CHAIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public final List createChainsCmd(String str, Map map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
        }
        List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
        for (Map.Entry entry : map.entrySet()) {
            Iterator it2 = ((List) entry.getValue()).iterator();
            while (it2.hasNext()) {
                createTableHeaderCmd.add(parseIptablesRestoreCmd(new IpRestoreParam((String) entry.getKey(), "", (String) it2.next(), "", IpRestoreActionType.INSERT)));
            }
        }
        createTableHeaderCmd.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return createTableHeaderCmd;
    }

    public final List removeChainsCmd(String str, Map map) {
        ArrayList arrayList = new ArrayList();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
        }
        List createTableHeaderCmd = createTableHeaderCmd(str, arrayList);
        for (Map.Entry entry : map.entrySet()) {
            Iterator it2 = ((List) entry.getValue()).iterator();
            while (it2.hasNext()) {
                createTableHeaderCmd.add(parseIptablesRestoreCmd(new IpRestoreParam((String) entry.getKey(), "", (String) it2.next(), "", IpRestoreActionType.DELETE)));
            }
        }
        Iterator it3 = map.entrySet().iterator();
        while (it3.hasNext()) {
            Iterator it4 = ((List) ((Map.Entry) it3.next()).getValue()).iterator();
            while (it4.hasNext()) {
                createTableHeaderCmd.add(parseIptablesRestoreCmd(new IpRestoreParam((String) it4.next(), null, null, null, IpRestoreActionType.REMOVE_CHAIN)));
            }
        }
        createTableHeaderCmd.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return createTableHeaderCmd;
    }

    public final void runSingleCommand(String str) {
        String[] splitCmds;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                splitCmds = NetdHelper.splitCmds(str);
            } catch (RemoteException e) {
                Log.e("knoxNwFilter-KnoxNetworkFilterFirewall", "runSingleCommand error " + e.getMessage());
            }
            if (splitCmds == null) {
                Log.e("knoxNwFilter-KnoxNetworkFilterFirewall", "Error splitting commands " + splitCmds);
                return;
            }
            for (String str2 : splitCmds) {
                getINetworkManagementService().runKnoxRulesCommand(NetdHelper.getCmdNumber(str2).intValue(), NetdHelper.getCmdParams(str2));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void allowAppGeneratedPackets(int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList.add(new IpRestoreParam("knox_nwfilter_app_act", " -m owner --uid-owner " + i, "ACCEPT", "", ipRestoreActionType));
        arrayList2.add(new IpRestoreParam("knox_nwfilter_app_act", " -m owner --uid-owner " + i, "ACCEPT", "", ipRestoreActionType));
        arrayList3.add(new IpRestoreParam("knox_nwfilter_app_act", " -m owner --uid-owner " + i, "ACCEPT", "", ipRestoreActionType));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
        insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList2, 46);
        insertRules(true, KnoxVpnFirewallHelper.TABLE_MANGLE, null, arrayList3, 6);
    }

    public void removeAppGeneratedPackets(int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.DELETE;
        arrayList.add(new IpRestoreParam("knox_nwfilter_app_act", " -m owner --uid-owner " + i, "ACCEPT", "", ipRestoreActionType));
        arrayList2.add(new IpRestoreParam("knox_nwfilter_app_act", " -m owner --uid-owner " + i, "ACCEPT", "", ipRestoreActionType));
        arrayList3.add(new IpRestoreParam("knox_nwfilter_app_act", " -m owner --uid-owner " + i, "ACCEPT", "", ipRestoreActionType));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
        insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList2, 46);
        insertRules(true, KnoxVpnFirewallHelper.TABLE_MANGLE, null, arrayList3, 6);
    }

    public void redirectDnsQuery(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IpRestoreParam("knox_nwfilter_dns_rdt", " -o lo -p udp --dport 53 ", "REDIRECT --to-port " + Integer.toString(i), "", IpRestoreActionType.INSERT));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
    }

    public void flushRedirectDnsQueryRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_NAT, "knox_nwfilter_dns_rdt", null, 4);
    }

    public void blockAppGeneratedDOT(int i) {
        int i2;
        int i3;
        if (i == 0) {
            i2 = 10000;
            i3 = 19999;
        } else {
            i2 = (100000 * i) + 1;
            i3 = (i2 - 1) + 99999;
        }
        String str = i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3;
        ArrayList arrayList = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList.add(new IpRestoreParam("knox_nwfilter_dot_drop", " -m owner --uid-owner " + str + " -p udp --dport 853 ", "DROP", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_dot_drop", " -m owner --uid-owner " + str + " -p tcp --dport 853 ", "DROP", "", ipRestoreActionType));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList, 46);
    }

    public void flushAppGeneratedDOTBlockRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_FILTER, "knox_nwfilter_dot_drop", null, 46);
    }

    public void redirectAppGeneratedTcpConn(int i, String str, int i2) {
        int i3;
        int i4;
        if (i == 0) {
            i3 = 10000;
            i4 = 19999;
        } else {
            i3 = (100000 * i) + 1;
            i4 = (i3 - 1) + 99999;
        }
        String str2 = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i4;
        ArrayList arrayList = new ArrayList();
        if (!str.isEmpty()) {
            IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
            arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_rdt", " -m owner --uid-owner " + str2 + " -o lo -p tcp -d " + str + " -m state --state NEW ", "REDIRECT --to-port " + Integer.toString(i2), "", ipRestoreActionType));
            arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_rdt", " -m owner --uid-owner " + str2 + " ! -o lo -p tcp -m state --state NEW ", "REDIRECT --to-port " + Integer.toString(i2), "", ipRestoreActionType));
        } else {
            arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_rdt", " -m owner --uid-owner " + str2 + " ! -o lo -p tcp -m state --state NEW ", "REDIRECT --to-port " + Integer.toString(i2), "", IpRestoreActionType.INSERT));
        }
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
    }

    public void flushAppGeneratedRedirectTcpConnRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_NAT, "knox_nwfilter_tcp_rdt", null, 4);
    }

    public void exemptDnsRulesFromNat(int i, List list) {
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        if (i == 0) {
            i2 = 10000;
            i3 = 19999;
        } else {
            i2 = (100000 * i) + 1;
            i3 = (i2 - 1) + 99999;
        }
        String str = i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3;
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList3.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p udp --dport 53 ", "DROP", "", ipRestoreActionType));
        arrayList4.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p udp --dport 53 ", "DROP", "", ipRestoreActionType));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            InetAddress inetAddress = (InetAddress) it.next();
            if (inetAddress instanceof Inet4Address) {
                IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.INSERT;
                arrayList.add(new IpRestoreParam("knox_nwfilter_dns_exempt", " -m owner --uid-owner " + str + " -p udp --dport 443 -d " + inetAddress.getHostAddress(), "ACCEPT", "", ipRestoreActionType2));
                arrayList.add(new IpRestoreParam("knox_nwfilter_dns_exempt", " -m owner --uid-owner " + str + " -p tcp --dport 443 -d " + inetAddress.getHostAddress(), "ACCEPT", "", ipRestoreActionType2));
                arrayList.add(new IpRestoreParam("knox_nwfilter_dns_exempt", " -m owner --uid-owner " + str + " -p tcp --dport 53 -d " + inetAddress.getHostAddress(), "ACCEPT", "", ipRestoreActionType2));
                StringBuilder sb = new StringBuilder();
                sb.append(" -m owner --uid-owner ");
                sb.append(str);
                sb.append(" -p udp --dport 53 ");
                arrayList.add(new IpRestoreParam("knox_nwfilter_dns_exempt", sb.toString(), "ACCEPT", "", ipRestoreActionType2));
                arrayList3.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p udp --dport 443 -d " + inetAddress.getHostAddress(), "DROP", "", ipRestoreActionType2));
                arrayList3.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p tcp --dport 443 -d " + inetAddress.getHostAddress(), "DROP", "", ipRestoreActionType2));
                arrayList3.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p tcp --dport 53 -d " + inetAddress.getHostAddress(), "DROP", "", ipRestoreActionType2));
            } else if (inetAddress instanceof Inet6Address) {
                IpRestoreActionType ipRestoreActionType3 = IpRestoreActionType.INSERT;
                arrayList2.add(new IpRestoreParam("knox_nwfilter_dns_exempt", " -m owner --uid-owner " + str + " -p udp --dport 443 -d " + inetAddress.getHostAddress(), "ACCEPT", "", ipRestoreActionType3));
                arrayList2.add(new IpRestoreParam("knox_nwfilter_dns_exempt", " -m owner --uid-owner " + str + " -p tcp --dport 443 -d " + inetAddress.getHostAddress(), "ACCEPT", "", ipRestoreActionType3));
                arrayList2.add(new IpRestoreParam("knox_nwfilter_dns_exempt", " -m owner --uid-owner " + str + " -p tcp --dport 53 -d " + inetAddress.getHostAddress(), "ACCEPT", "", ipRestoreActionType3));
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" -m owner --uid-owner ");
                sb2.append(str);
                sb2.append(" -p udp --dport 53 ");
                arrayList2.add(new IpRestoreParam("knox_nwfilter_dns_exempt", sb2.toString(), "ACCEPT", "", ipRestoreActionType3));
                arrayList4.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p udp --dport 443 -d " + inetAddress.getHostAddress(), "DROP", "", ipRestoreActionType3));
                arrayList4.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p tcp --dport 443 -d " + inetAddress.getHostAddress(), "DROP", "", ipRestoreActionType3));
                arrayList4.add(new IpRestoreParam("knox_nwfilter_dns_drop", " -m owner --uid-owner " + str + " -p tcp --dport 53 -d " + inetAddress.getHostAddress(), "DROP", "", ipRestoreActionType3));
            }
        }
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
        insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList3, 4);
        insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList4, 6);
    }

    public void flushExemptDnsRulesFromNat() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_NAT, "knox_nwfilter_dns_exempt", null, 4);
        insertRule(false, KnoxVpnFirewallHelper.TABLE_FILTER, "knox_nwfilter_dns_drop", null, 46);
    }

    public void exemptUdpPacketFromNwFilterRange(int i, int i2) {
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            i3 = 10000;
            i4 = 19999;
        } else {
            i3 = (100000 * i) + 1;
            i4 = (i3 - 1) + 99999;
        }
        String str = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i4;
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.INSERT;
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_exempt", " -m owner --uid-owner " + str + " -p udp --dport 5353 ", "ACCEPT", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_exempt", " -m owner --uid-owner " + str + " -p udp --dport 1900 ", "ACCEPT", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_exempt", " -m owner --uid-owner " + str + " -p udp -m pkttype --pkt-type multicast ", "ACCEPT", "", ipRestoreActionType));
        if (i2 == 2) {
            insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
        }
        if (i2 == 10) {
            insertRules(true, KnoxVpnFirewallHelper.TABLE_MANGLE, null, arrayList, 6);
        }
    }

    public void flushUdpPacketExemptRules(int i) {
        if (i == 2) {
            insertRule(false, KnoxVpnFirewallHelper.TABLE_NAT, "knox_nwfilter_udp_exempt", null, 4);
        }
        if (i == 10) {
            insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_udp_exempt", null, 6);
        }
    }

    public void redirectAppGeneratedUdpConn(int i, String str, int i2) {
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            i3 = 10000;
            i4 = 19999;
        } else {
            i3 = (100000 * i) + 1;
            i4 = (i3 - 1) + 99999;
        }
        String str2 = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i4;
        if (IS_CONNBYTE_EXTENSION_PRESENT) {
            if (!str.isEmpty()) {
                IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
                arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -m owner --uid-owner " + str2 + " -o lo -d " + str + " -p udp -m state --state NEW -m connbytes --connbytes 1:1 --connbytes-dir original --connbytes-mode packets ", "CONNMARK --set-mark " + Integer.toString(60), "", ipRestoreActionType));
                arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -m owner --uid-owner " + str2 + " ! -o lo  -p udp -m state --state NEW -m connbytes --connbytes 1:1 --connbytes-dir original --connbytes-mode packets ", "CONNMARK --set-mark " + Integer.toString(60), "", ipRestoreActionType));
            } else {
                arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -m owner --uid-owner " + str2 + " ! -o lo  -p udp -m state --state NEW -m connbytes --connbytes 1:1 --connbytes-dir original --connbytes-mode packets ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
            }
        } else if (!str.isEmpty()) {
            IpRestoreActionType ipRestoreActionType2 = IpRestoreActionType.APPEND;
            arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -m owner --uid-owner " + str2 + " -o lo -d " + str + " -p udp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", ipRestoreActionType2));
            arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -m owner --uid-owner " + str2 + " ! -o lo  -p udp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", ipRestoreActionType2));
        } else {
            arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -m owner --uid-owner " + str2 + " ! -o lo  -p udp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
        }
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -p udp -m connmark --mark " + Integer.toString(60), "REDIRECT --to-port " + Integer.toString(i2), "", IpRestoreActionType.APPEND));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
    }

    public void flushAppGeneratedRedirectUdpConnRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_NAT, "knox_nwfilter_udp_rdt", null, 4);
    }

    public void redirectAppGeneratedV6TcpConn(int i, String str, int i2) {
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            i3 = 10000;
            i4 = 19999;
        } else {
            i3 = (100000 * i) + 1;
            i4 = (i3 - 1) + 99999;
        }
        String str2 = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i4;
        if (!str.isEmpty()) {
            arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_mark", " -m owner --uid-owner " + str2 + " ! -o lo -p tcp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
        } else {
            arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_mark", " -m owner --uid-owner " + str2 + " ! -o lo -p tcp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
        }
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_mark", " -p tcp -m connmark --mark " + Integer.toString(60), "CONNMARK --restore-mark", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_mark", " -p tcp -m connmark --mark " + Integer.toString(60), "ACCEPT", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_tcp_rdt", " -p tcp --syn -i lo -m mark --mark " + Integer.toString(60), "TPROXY --on-port " + Integer.toString(i2), "", ipRestoreActionType));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_MANGLE, null, arrayList, 6);
        StringBuilder sb = new StringBuilder();
        sb.append(KnoxVpnFirewallHelper.IP6_ROUTE_CMD);
        sb.append(KnoxVpnFirewallHelper.ADD);
        sb.append(" local default dev lo table " + Integer.toString(60));
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        sb.append(KnoxVpnFirewallHelper.IP6_RULE_CMD);
        sb.append(KnoxVpnFirewallHelper.ADD);
        sb.append(" from all fwmark " + Integer.toString(60) + " table " + Integer.toString(60) + " prio 10");
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        runSingleCommand(sb.toString());
    }

    public void flushAppGeneratedRedirectV6TcpConnRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_tcp_mark", null, 6);
        insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_tcp_rdt", null, 6);
        StringBuilder sb = new StringBuilder();
        sb.append(KnoxVpnFirewallHelper.IP6_ROUTE_CMD);
        sb.append(KnoxVpnFirewallHelper.DEL);
        sb.append(" local default dev lo table " + Integer.toString(60));
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        sb.append(KnoxVpnFirewallHelper.IP6_RULE_CMD);
        sb.append(KnoxVpnFirewallHelper.DEL);
        sb.append(" from all fwmark " + Integer.toString(60) + " table " + Integer.toString(60) + " prio 10");
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        runSingleCommand(sb.toString());
    }

    public void redirectAppGeneratedV6UdpConn(int i, String str, int i2) {
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            i3 = 10000;
            i4 = 19999;
        } else {
            i3 = (100000 * i) + 1;
            i4 = (i3 - 1) + 99999;
        }
        String str2 = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i4;
        if (IS_CONNBYTE_EXTENSION_PRESENT) {
            if (!str.isEmpty()) {
                arrayList.add(new IpRestoreParam("knox_nwfilter_udp_mark", " -m owner --uid-owner " + str2 + " ! -o lo -p udp -m state --state NEW -m connbytes --connbytes 1:1 --connbytes-dir original --connbytes-mode packets ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
            } else {
                arrayList.add(new IpRestoreParam("knox_nwfilter_udp_mark", " -m owner --uid-owner " + str2 + " ! -o lo -p udp -m state --state NEW -m connbytes --connbytes 1:1 --connbytes-dir original --connbytes-mode packets ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
            }
        } else if (!str.isEmpty()) {
            arrayList.add(new IpRestoreParam("knox_nwfilter_udp_mark", " -m owner --uid-owner " + str2 + " ! -o lo -p udp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
        } else {
            arrayList.add(new IpRestoreParam("knox_nwfilter_udp_mark", " -m owner --uid-owner " + str2 + " ! -o lo -p udp -m state --state NEW ", "CONNMARK --set-mark " + Integer.toString(60), "", IpRestoreActionType.APPEND));
        }
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_mark", " -p udp -m connmark --mark " + Integer.toString(60), "CONNMARK --restore-mark", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_mark", " -p udp -m connmark --mark " + Integer.toString(60), "ACCEPT", "", ipRestoreActionType));
        arrayList.add(new IpRestoreParam("knox_nwfilter_udp_rdt", " -p udp -i lo -m mark --mark " + Integer.toString(60), "TPROXY --on-port " + Integer.toString(i2), "", ipRestoreActionType));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_MANGLE, null, arrayList, 6);
        StringBuilder sb = new StringBuilder();
        sb.append(KnoxVpnFirewallHelper.IP6_ROUTE_CMD);
        sb.append(KnoxVpnFirewallHelper.ADD);
        sb.append(" local default dev lo table " + Integer.toString(60));
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        sb.append(KnoxVpnFirewallHelper.IP6_RULE_CMD);
        sb.append(KnoxVpnFirewallHelper.ADD);
        sb.append(" from all fwmark " + Integer.toString(60) + " table " + Integer.toString(60) + " prio 10");
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        runSingleCommand(sb.toString());
    }

    public void flushAppGeneratedRedirectV6UdpConnRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_udp_mark", null, 6);
        insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_udp_rdt", null, 6);
        StringBuilder sb = new StringBuilder();
        sb.append(KnoxVpnFirewallHelper.IP6_ROUTE_CMD);
        sb.append(KnoxVpnFirewallHelper.DEL);
        sb.append(" local default dev lo table " + Integer.toString(60));
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        sb.append(KnoxVpnFirewallHelper.IP6_RULE_CMD);
        sb.append(KnoxVpnFirewallHelper.DEL);
        sb.append(" from all fwmark " + Integer.toString(60) + " table " + Integer.toString(60) + " prio 10");
        sb.append(KnoxVpnFirewallHelper.DELIMITER);
        runSingleCommand(sb.toString());
    }

    public void flushRedirectV6DnsQueryRules() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_dns_mark", null, 6);
        insertRule(false, KnoxVpnFirewallHelper.TABLE_MANGLE, "knox_nwfilter_dns_rdt", null, 6);
    }

    public void denyUnAuthorizedPackets(int i, String str, List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            if (str.equalsIgnoreCase("dns")) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String[] split = ((String) it.next()).split("_");
                    String str2 = split[0];
                    String str3 = split[1];
                    if (InetAddress.getByName(str2) instanceof Inet4Address) {
                        arrayList.add(new IpRestoreParam("knox_nwfilter_unauth_drop", " -p udp -d " + str2 + " --dport " + str3 + " -m owner ! --uid-owner " + Integer.toString(i), "DROP", "", IpRestoreActionType.INSERT));
                    } else {
                        arrayList2.add(new IpRestoreParam("knox_nwfilter_unauth_drop", " -p udp -d " + str2 + " --dport " + str3 + " -m owner ! --uid-owner " + Integer.toString(i), "DROP", "", IpRestoreActionType.INSERT));
                    }
                }
            }
            if (str.equalsIgnoreCase("tcp")) {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String[] split2 = ((String) it2.next()).split("_");
                    String str4 = split2[0];
                    String str5 = split2[1];
                    if (InetAddress.getByName(str4) instanceof Inet4Address) {
                        arrayList.add(new IpRestoreParam("knox_nwfilter_unauth_drop", " -p tcp -m state --state NEW -d " + str4 + " --dport " + str5 + " -m owner ! --uid-owner " + Integer.toString(i), "DROP", "", IpRestoreActionType.INSERT));
                    } else {
                        arrayList2.add(new IpRestoreParam("knox_nwfilter_unauth_drop", " -p tcp -m state --state NEW -d " + str4 + " --dport " + str5 + " -m owner ! --uid-owner " + Integer.toString(i), "DROP", "", IpRestoreActionType.INSERT));
                    }
                }
            }
            if (str.equalsIgnoreCase("udp")) {
                Iterator it3 = list.iterator();
                while (it3.hasNext()) {
                    String[] split3 = ((String) it3.next()).split("_");
                    String str6 = split3[0];
                    String str7 = split3[1];
                    if (InetAddress.getByName(str6) instanceof Inet4Address) {
                        arrayList.add(new IpRestoreParam("knox_nwfilter_unauth_drop", " -p udp -d " + str6 + " --dport " + str7 + " -m owner ! --uid-owner " + Integer.toString(i), "DROP", "", IpRestoreActionType.INSERT));
                    } else {
                        arrayList2.add(new IpRestoreParam("knox_nwfilter_unauth_drop", " -p udp -d " + str6 + " --dport " + str7 + " -m owner ! --uid-owner " + Integer.toString(i), "DROP", "", IpRestoreActionType.INSERT));
                    }
                }
            }
            insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList, 4);
            insertRules(true, KnoxVpnFirewallHelper.TABLE_FILTER, null, arrayList2, 6);
        } catch (UnknownHostException unused) {
        }
    }

    public void flushUnAuthorizedPackets() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_FILTER, "knox_nwfilter_unauth_drop", null, 46);
    }

    public void applyTcpRedirectRulesForCaptivePortal(int i, int i2) {
        if (i2 <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IpRestoreParam("knox_nwfilter_cp_rdt", " -m owner --uid-owner " + i + " -o lo -p tcp --dport 80 ", "REDIRECT --to-port " + Integer.toString(i2), "", IpRestoreActionType.APPEND));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
    }

    public void removeTcpRedirectRulesForCaptivePortal(int i, int i2) {
        if (i2 <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IpRestoreParam("knox_nwfilter_cp_rdt", " -m owner --uid-owner " + i + " -o lo -p tcp --dport 80 ", "REDIRECT --to-port " + Integer.toString(i2), "", IpRestoreActionType.DELETE));
        insertRules(true, KnoxVpnFirewallHelper.TABLE_NAT, null, arrayList, 4);
    }

    public void flushTcpRedirectRulesForCaptivePortal() {
        insertRule(false, KnoxVpnFirewallHelper.TABLE_NAT, "knox_nwfilter_cp_rdt", null, 4);
    }

    public final void isIptablesExtensionPresent(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                int isIptablesMatchEnabled = getOemNetdService().isIptablesMatchEnabled(str);
                Log.d("knoxNwFilter-KnoxNetworkFilterFirewall", "isIptablesExtensionPresent: " + str + " " + isIptablesMatchEnabled);
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
}
