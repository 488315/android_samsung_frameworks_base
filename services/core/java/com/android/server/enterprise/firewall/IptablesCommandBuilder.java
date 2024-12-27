package com.android.server.enterprise.firewall;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.text.TextUtils;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class IptablesCommandBuilder {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.firewall.IptablesCommandBuilder$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[]
                $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table;
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallDefinitions.Table.values().length];
            $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table = iArr;
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr2;
            try {
                iArr2[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.DENY.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.REDIRECT.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static List createAllowOrDenyCommands(
            FirewallRule firewallRule,
            ContextInfo contextInfo,
            String str,
            FirewallRule.RuleType ruleType) {
        boolean z;
        boolean z2;
        boolean z3;
        String str2;
        String str3;
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        String str4 = " -j REJECT";
        String str5 = "firewall_deny-forward";
        String str6 = "firewall_deny-output";
        String str7 = "firewall_deny-input";
        if (userId == 0) {
            if (ruleType.equals(FirewallRule.RuleType.ALLOW)) {
                str4 = " -j ACCEPT";
                str5 = "firewall_allow-forward";
                str6 = "firewall_allow-output";
                str7 = "firewall_allow-input";
            }
        } else if (ruleType.equals(FirewallRule.RuleType.ALLOW)) {
            str6 = VibrationParam$1$$ExternalSyntheticOutline0.m(userId, "firewall_allow-output");
            str4 = " -j ACCEPT";
            str5 = "firewall_allow-forward";
            str7 = "firewall_allow-input";
        } else {
            str6 = VibrationParam$1$$ExternalSyntheticOutline0.m(userId, "firewall_deny-output");
        }
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        if (firewallRule.getDirection().equals(Firewall.Direction.INPUT)) {
            sb.append(str + " " + str7 + " ");
            z = true;
        } else {
            z = false;
        }
        if (firewallRule.getDirection().equals(Firewall.Direction.ALL)
                || firewallRule.getDirection().equals(Firewall.Direction.OUTPUT)) {
            sb2.append(str + " " + str6 + " ");
            z2 = true;
        } else {
            z2 = false;
        }
        if (firewallRule.getDirection().equals(Firewall.Direction.FORWARD)) {
            sb3.append(str + " " + str5 + " ");
            sb4.append(
                    OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), str, " ", str5, " "));
            z3 = true;
        } else {
            z3 = false;
        }
        if ("*".equals(firewallRule.getIpAddress())) {
            str2 = str4;
        } else if (firewallRule
                .getIpAddress()
                .contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
            sb.append("-m iprange --src-range " + firewallRule.getIpAddress());
            StringBuilder sb5 = new StringBuilder("-m iprange --dst-range  ");
            str2 = str4;
            sb5.append(firewallRule.getIpAddress());
            sb2.append(sb5.toString());
            sb3.append("-m iprange --src-range  " + firewallRule.getIpAddress());
            sb4.append("-m iprange --dst-range  " + firewallRule.getIpAddress());
        } else {
            str2 = str4;
            sb.append("-s " + firewallRule.getIpAddress());
            sb2.append("-d " + firewallRule.getIpAddress());
            sb3.append("-s " + firewallRule.getIpAddress());
            sb4.append("-d " + firewallRule.getIpAddress());
        }
        if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
            if (z) {
                sb.append(" -i " + firewallRule.getStrNetworkInterface());
            }
            if (z2) {
                sb2.append(" -o " + firewallRule.getStrNetworkInterface());
            }
        } else if (!firewallRule
                .getNetworkInterface()
                .equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
            if (z) {
                sb.append(
                        " -i "
                                + FirewallUtils.convertNetworkInterfaceForIptables(
                                        firewallRule.getNetworkInterface()));
            }
            if (z2) {
                sb2.append(
                        " -o "
                                + FirewallUtils.convertNetworkInterfaceForIptables(
                                        firewallRule.getNetworkInterface()));
            }
        }
        if ("*".equals(firewallRule.getPortNumber())) {
            if (firewallRule.getProtocol().equals(Firewall.Protocol.TCP)) {
                if (z) {
                    hashSet.add(sb.toString() + " -p tcp");
                }
                if (z2) {
                    hashSet.add(sb2.toString() + " -p tcp");
                }
                if (z3) {
                    hashSet.add(sb3.toString() + " -p tcp");
                    hashSet.add(sb4.toString() + " -p tcp");
                }
            } else if (firewallRule.getProtocol().equals(Firewall.Protocol.UDP)) {
                if (z) {
                    hashSet.add(sb.toString() + " -p udp");
                }
                if (z2) {
                    hashSet.add(sb2.toString() + " -p udp");
                }
                if (z3) {
                    hashSet.add(sb3.toString() + " -p udp");
                    hashSet.add(sb4.toString() + " -p udp");
                }
            } else {
                if (z) {
                    hashSet.add(sb.toString());
                }
                if (z2) {
                    hashSet.add(sb2.toString());
                }
                if (z3) {
                    hashSet.add(sb3.toString());
                    hashSet.add(sb4.toString());
                }
            }
        } else if (z3) {
            if (firewallRule.getProtocol().equals(Firewall.Protocol.TCP)
                    || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
                hashSet.add(sb3.toString() + " -p tcp --dport ");
                hashSet.add(sb3.toString() + " -p tcp --sport ");
                hashSet.add(sb4.toString() + " -p tcp --dport ");
                hashSet.add(sb4.toString() + " -p tcp --sport ");
            }
            if (firewallRule.getProtocol().equals(Firewall.Protocol.UDP)
                    || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
                hashSet.add(sb3.toString() + " -p udp --dport ");
                hashSet.add(sb3.toString() + " -p udp --sport ");
                hashSet.add(sb4.toString() + " -p udp --dport ");
                hashSet.add(sb4.toString() + " -p udp --sport ");
            }
        } else {
            Firewall.PortLocation portLocation = firewallRule.getPortLocation();
            Firewall.PortLocation portLocation2 = Firewall.PortLocation.ALL;
            if (portLocation.equals(portLocation2)
                    || firewallRule.getPortLocation().equals(Firewall.PortLocation.LOCAL)) {
                if (firewallRule.getProtocol().equals(Firewall.Protocol.TCP)
                        || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
                    if (z) {
                        hashSet.add(sb.toString() + " -p tcp --dport ");
                    }
                    if (z2) {
                        hashSet.add(sb2.toString() + " -p tcp --sport ");
                    }
                }
                if (firewallRule.getProtocol().equals(Firewall.Protocol.UDP)
                        || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
                    if (z) {
                        hashSet.add(sb.toString() + " -p udp --dport ");
                    }
                    if (z2) {
                        hashSet.add(sb2.toString() + " -p udp --sport ");
                    }
                }
            }
            if (firewallRule.getPortLocation().equals(portLocation2)
                    || firewallRule.getPortLocation().equals(Firewall.PortLocation.REMOTE)) {
                if (firewallRule.getProtocol().equals(Firewall.Protocol.TCP)
                        || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
                    if (z) {
                        hashSet.add(sb.toString() + " -p tcp --sport ");
                    }
                    if (z2) {
                        hashSet.add(sb2.toString() + " -p tcp --dport ");
                    }
                }
                if (firewallRule.getProtocol().equals(Firewall.Protocol.UDP)
                        || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
                    if (z) {
                        hashSet.add(sb.toString() + " -p udp --sport ");
                    }
                    if (z2) {
                        hashSet.add(sb2.toString() + " -p udp --dport ");
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            StringBuilder sb6 = new StringBuilder((String) it.next());
            if (!"*".equals(firewallRule.getPortNumber())) {
                String portNumber = firewallRule.getPortNumber();
                if (firewallRule
                        .getPortNumber()
                        .contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                    portNumber = portNumber.replace('-', ':');
                }
                sb6.append(portNumber);
            }
            if (sb6.indexOf("output") != -1) {
                sb6.append(" ");
                sb6.append(FirewallUtils.getAppOrUserUid(contextInfo, firewallRule));
            }
            if (z && ruleType.equals(FirewallRule.RuleType.DENY) && sb6.indexOf("input") != -1) {
                sb6.append(" -j DROP");
                str3 = str2;
                arrayList.add(sb6.toString());
                str2 = str3;
            }
            str3 = str2;
            sb6.append(str3);
            arrayList.add(sb6.toString());
            str2 = str3;
        }
        return arrayList;
    }

    public static List createAppendDeleteDomainCommands(
            DomainFilterRule domainFilterRule, String str, ContextInfo contextInfo, boolean z) {
        Integer uidForApplication;
        ArrayList arrayList = new ArrayList();
        String packageName = domainFilterRule.getApplication().getPackageName();
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if ("*".equals(packageName)) {
            uidForApplication = Integer.valueOf(userId);
        } else {
            uidForApplication = FirewallUtils.getUidForApplication(userId, packageName);
            if (uidForApplication == null) {
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        "Failed to get uid for ", packageName, "FirewallCommandBuilder");
                return arrayList;
            }
        }
        String domainFilterChainNameForApp =
                getDomainFilterChainNameForApp(
                        packageName, uidForApplication, Integer.valueOf(userId), true);
        String domainFilterChainNameForApp2 =
                getDomainFilterChainNameForApp(
                        packageName, uidForApplication, Integer.valueOf(userId), false);
        String str2 = "-A".equals(str) ? "-I" : str;
        for (String str3 : domainFilterRule.getAllowDomains()) {
            if (str3.contains("*")) {
                str3 = str3.replaceAll("\\*", "%");
            }
            arrayList.add(
                    str2
                            + " "
                            + domainFilterChainNameForApp2
                            + " -m domainfilter --whitelist "
                            + str3
                            + " -j ACCEPT");
        }
        for (String str4 : domainFilterRule.getDenyDomains()) {
            if (str4.contains("*")) {
                str4 = str4.replaceAll("\\*", "%");
            }
            arrayList.add(
                    str
                            + " "
                            + domainFilterChainNameForApp2
                            + " -m domainfilter --blacklist "
                            + str4
                            + " -j REJECT");
        }
        if (z) {
            arrayList.add(str2 + " " + domainFilterChainNameForApp + " -j ACCEPT");
        }
        return arrayList;
    }

    public static List createIcmpAllowRuleCommands(int i, boolean z) {
        String str = z ? "-I" : "-D";
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("*filter");
        StringBuilder sb = new StringBuilder();
        if (i == 4) {
            sb.append(
                    str.concat(
                            " firewall_allow-input -p icmp -m icmp --icmp-type 3 -m state --state"
                                + " RELATED,ESTABLISHED -j ACCEPT"));
        } else if (i == 6) {
            sb.append(
                    str.concat(
                            " firewall_allow-input -p icmpv6 -m icmpv6 --icmpv6-type 1 -m state"
                                + " --state RELATED,ESTABLISHED -j ACCEPT"));
        }
        m.add(sb.toString());
        m.add("COMMIT\n");
        return m;
    }

    public static String getDomainFilterBaseChainNameForUser(int i, boolean z) {
        return z
                ? "domain_filter-input"
                : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "domain_filter-output", i == 0 ? "" : Integer.toString(i));
    }

    public static String getDomainFilterChainNameForApp(
            String str, Integer num, Integer num2, boolean z) {
        if (z) {
            return "domain_input-all";
        }
        if ((str == null || !"*".equals(str)) && (num == null || num.intValue() != -1)) {
            return num != null
                    ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "domain_output-uid", Integer.toString(num.intValue()))
                    : "";
        }
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                "domain_output-all", num2.intValue() != 0 ? Integer.toString(num2.intValue()) : "");
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x0228, code lost:

       if (r17.getProtocol().equals(com.samsung.android.knox.net.firewall.Firewall.Protocol.ALL) != false) goto L76;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getIptablesCommand(
            com.samsung.android.knox.net.firewall.FirewallRule r17,
            com.samsung.android.knox.ContextInfo r18,
            java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.IptablesCommandBuilder.getIptablesCommand(com.samsung.android.knox.net.firewall.FirewallRule,"
                    + " com.samsung.android.knox.ContextInfo, java.lang.String):java.util.List");
    }

    public static List getListIptablesRestoreCommand(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("*" + str + "\n");
        arrayList.add("-L -n -v --line-numbers");
        arrayList.add("COMMIT\n");
        return arrayList;
    }
}
