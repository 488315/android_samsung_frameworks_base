package com.android.server.knox.zt.networktrust;

import android.os.Binder;
import android.os.INetworkManagementService;
import android.os.ServiceManager;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxNetworkEventFirewall {
    public static KnoxNetworkEventFirewall mKnoxNwEventFw;
    public static final Map mangleChains;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EventType {
        public static final /* synthetic */ EventType[] $VALUES;
        public static final EventType ABNORMAL_PACKETS;
        public static final EventType INSECURE_PORTS;
        public static final EventType LOCAL_NW;

        static {
            EventType eventType = new EventType("INSECURE_PORTS", 0);
            INSECURE_PORTS = eventType;
            EventType eventType2 = new EventType("ABNORMAL_PACKETS", 1);
            ABNORMAL_PACKETS = eventType2;
            EventType eventType3 = new EventType("LOCAL_NW", 2);
            LOCAL_NW = eventType3;
            $VALUES = new EventType[] {eventType, eventType2, eventType3, new EventType("NONE", 3)};
        }

        public static EventType valueOf(String str) {
            return (EventType) Enum.valueOf(EventType.class, str);
        }

        public static EventType[] values() {
            return (EventType[]) $VALUES.clone();
        }
    }

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

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("POSTROUTING", Arrays.asList("knox_nw_event_event", "knox_nw_event_exempt"));
        mangleChains = hashMap;
    }

    public KnoxNetworkEventFirewall() {
        Map map = mangleChains;
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = (HashMap) map;
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll((Collection) ((Map.Entry) it.next()).getValue());
        }
        List createTableHeaderCmd = createTableHeaderCmd(arrayList);
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
        runIptablesRestoreCmd(46, createTableHeaderCmd);
        Map map2 = mangleChains;
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap2 = (HashMap) map2;
        Iterator it5 = hashMap2.entrySet().iterator();
        while (it5.hasNext()) {
            arrayList2.addAll((Collection) ((Map.Entry) it5.next()).getValue());
        }
        List createTableHeaderCmd2 = createTableHeaderCmd(arrayList2);
        for (Map.Entry entry2 : hashMap2.entrySet()) {
            Iterator it6 = ((List) entry2.getValue()).iterator();
            while (it6.hasNext()) {
                ((ArrayList) createTableHeaderCmd2)
                        .add(
                                parseIptablesRestoreCmd(
                                        new IpRestoreParam(
                                                (String) entry2.getKey(),
                                                "",
                                                (String) it6.next(),
                                                "",
                                                IpRestoreActionType.INSERT)));
            }
        }
        ((ArrayList) createTableHeaderCmd2).add("COMMIT\n");
        runIptablesRestoreCmd(46, createTableHeaderCmd2);
        insertRules(
                false,
                Arrays.asList("knox_nw_event_1", "knox_nw_event_2", "knox_nw_event_3"),
                null,
                46);
        insertRules(
                false,
                Arrays.asList("knox_nw_event_1_mch", "knox_nw_event_2_mch", "knox_nw_event_3_mch"),
                null,
                46);
        insertRules(
                false,
                Arrays.asList("knox_nw_event_1_act", "knox_nw_event_2_act", "knox_nw_event_3_act"),
                null,
                46);
        ArrayList arrayList3 = new ArrayList();
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList3.add(
                new IpRestoreParam(
                        "knox_nw_event_event", "", "knox_nw_event_1", "", ipRestoreActionType));
        arrayList3.add(
                new IpRestoreParam(
                        "knox_nw_event_event", "", "knox_nw_event_2", "", ipRestoreActionType));
        arrayList3.add(
                new IpRestoreParam(
                        "knox_nw_event_event", "", "knox_nw_event_3", "", ipRestoreActionType));
        arrayList3.add(
                new IpRestoreParam(
                        "knox_nw_event_1_act",
                        " -m bpf --object-pinned"
                            + " /sys/fs/bpf/prog_netlog_skfilter_insecureports_xtbpf",
                        "RETURN",
                        "",
                        ipRestoreActionType));
        arrayList3.add(
                new IpRestoreParam(
                        "knox_nw_event_2_act",
                        " -m bpf --object-pinned"
                            + " /sys/fs/bpf/prog_netlog_skfilter_abnormalpackets_xtbpf",
                        "RETURN",
                        "",
                        ipRestoreActionType));
        arrayList3.add(
                new IpRestoreParam(
                        "knox_nw_event_3_act",
                        " -m bpf --object-pinned"
                            + " /sys/fs/bpf/prog_netlog_skfilter_localnwpackets_xtbpf",
                        "RETURN",
                        "",
                        ipRestoreActionType));
        insertRules(true, null, arrayList3, 46);
    }

    public static List createTableHeaderCmd(List list) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append("*mangle\n");
        arrayList.add(sb.toString());
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (str != null) {
                    sb.setLength(0);
                    sb.append(":");
                    sb.append(str);
                    sb.append(" -");
                    sb.append("\n");
                    arrayList.add(sb.toString());
                }
            }
        }
        return arrayList;
    }

    public static void flushRulesForEvent(EventType eventType) {
        int ordinal = eventType.ordinal();
        if (ordinal == 0) {
            insertRule("knox_nw_event_1_mch");
            insertRule("knox_nw_event_1");
        } else if (ordinal == 1) {
            insertRule("knox_nw_event_2_mch");
            insertRule("knox_nw_event_2");
        } else {
            if (ordinal != 2) {
                return;
            }
            insertRule("knox_nw_event_3_mch");
            insertRule("knox_nw_event_3");
        }
    }

    public static void insertRule(String str) {
        insertRules(false, Collections.singletonList(str), Collections.singletonList(null), 46);
    }

    public static void insertRuleForInsecurePorts(int i) {
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            i3 = 10000;
            i2 = 19999;
        } else {
            int i4 = i * 100000;
            i2 = i4 + 99999;
            i3 = i4 + 1;
        }
        String str = i3 + PackageManagerShellCommandDataLoader.STDIN_PATH + i2;
        IpRestoreActionType ipRestoreActionType = IpRestoreActionType.APPEND;
        arrayList.add(
                new IpRestoreParam(
                        "knox_nw_event_1", "", "knox_nw_event_1_mch", "", ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        "knox_nw_event_1_mch",
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                " -m owner --uid-owner ",
                                str,
                                " -p tcp -m state --state NEW -m multiport --dports"
                                    + " 80,8080,20,21,23,25,110,143"),
                        "knox_nw_event_1_act",
                        "",
                        ipRestoreActionType));
        arrayList.add(
                new IpRestoreParam(
                        "knox_nw_event_1_mch",
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                " -m owner --uid-owner ",
                                str,
                                " -p udp -m state --state NEW -m multiport --dports"
                                    + " 1812,1813,1645,1646"),
                        "knox_nw_event_1_act",
                        "",
                        ipRestoreActionType));
        insertRules(true, null, arrayList, 46);
    }

    public static void insertRules(boolean z, List list, List list2, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(createTableHeaderCmd(list));
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

    public static void runIptablesRestoreCmd(int i, List list) {
        String join = String.join("", list);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.d(
                        "NetworkEventsLoggerService-KnoxNetworkEventFirewall",
                        "testing: command " + join + "target " + i);
                INetworkManagementService.Stub.asInterface(
                                ServiceManager.getService("network_management"))
                        .runKnoxFirewallRulesCommand(i, join);
            } catch (Exception e) {
                Log.e(
                        "NetworkEventsLoggerService-KnoxNetworkEventFirewall",
                        "Failed to run cmd: " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
