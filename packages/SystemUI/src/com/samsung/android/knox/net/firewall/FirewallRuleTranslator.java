package com.samsung.android.knox.net.firewall;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.sec.ims.settings.ImsProfile;

/* loaded from: classes4.dex */
public class FirewallRuleTranslator {
    public static String[] networkInterfaceOptions = {ImsProfile.PDN_WIFI, "data", "*"};
    public static String[] portLocationOptions = {"remote", "local", "*"};

    /* renamed from: com.samsung.android.knox.net.firewall.FirewallRuleTranslator$1, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static String convertNetworkInterface(Firewall.NetworkInterface networkInterface) {
        if (networkInterface == null) {
            return null;
        }
        return networkInterface.equals(Firewall.NetworkInterface.WIFI_DATA_ONLY) ? networkInterfaceOptions[0] : networkInterface.equals(Firewall.NetworkInterface.MOBILE_DATA_ONLY) ? networkInterfaceOptions[1] : networkInterfaceOptions[2];
    }

    public static String convertPortLocation(Firewall.PortLocation portLocation) {
        if (portLocation == null) {
            return null;
        }
        return portLocation.equals(Firewall.PortLocation.REMOTE) ? portLocationOptions[0] : portLocation.equals(Firewall.PortLocation.LOCAL) ? portLocationOptions[1] : portLocationOptions[2];
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FirewallRule translateAllowRule(String str) {
        Firewall.NetworkInterface networkInterface;
        String str2;
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(";");
        if (strArrSplit.length < 2 || strArrSplit.length > 4) {
            return null;
        }
        Firewall.PortLocation portLocation = Firewall.PortLocation.REMOTE;
        Firewall.NetworkInterface networkInterface2 = Firewall.NetworkInterface.ALL_NETWORKS;
        int iLastIndexOf = strArrSplit[0].lastIndexOf(":");
        if (iLastIndexOf == -1) {
            return null;
        }
        String strSubstring = strArrSplit[0].substring(0, iLastIndexOf);
        String strSubstring2 = strArrSplit[0].substring(iLastIndexOf + 1);
        Firewall.PortLocation portLocation2 = portLocationOptions[0].equals(strArrSplit[1]) ? Firewall.PortLocation.REMOTE : portLocationOptions[1].equals(strArrSplit[1]) ? Firewall.PortLocation.LOCAL : Firewall.PortLocation.ALL;
        if (strArrSplit.length != 3) {
            networkInterface = networkInterface2;
        } else if (networkInterfaceOptions[0].equals(strArrSplit[2])) {
            networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
        } else if (networkInterfaceOptions[1].equals(strArrSplit[2])) {
            networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
        }
        if (strArrSplit.length > 3) {
            str2 = strArrSplit[2];
            if (networkInterfaceOptions[0].equals(strArrSplit[3])) {
                networkInterface2 = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(strArrSplit[3])) {
                networkInterface2 = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
        } else {
            str2 = "*";
            networkInterface2 = networkInterface;
        }
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(strSubstring);
        firewallRule.setPortNumber(strSubstring2);
        firewallRule.setPortLocation(portLocation2);
        firewallRule.setApplication(appIdentity);
        firewallRule.setNetworkInterface(networkInterface2);
        return firewallRule;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FirewallRule translateDenyRule(String str) {
        Firewall.NetworkInterface networkInterface;
        String str2;
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(";");
        if (strArrSplit.length < 2 || strArrSplit.length > 4) {
            return null;
        }
        Firewall.PortLocation portLocation = Firewall.PortLocation.REMOTE;
        Firewall.NetworkInterface networkInterface2 = Firewall.NetworkInterface.ALL_NETWORKS;
        int iLastIndexOf = strArrSplit[0].lastIndexOf(":");
        if (iLastIndexOf == -1) {
            return null;
        }
        String strSubstring = strArrSplit[0].substring(0, iLastIndexOf);
        String strSubstring2 = strArrSplit[0].substring(iLastIndexOf + 1);
        Firewall.PortLocation portLocation2 = portLocationOptions[0].equals(strArrSplit[1]) ? Firewall.PortLocation.REMOTE : portLocationOptions[1].equals(strArrSplit[1]) ? Firewall.PortLocation.LOCAL : Firewall.PortLocation.ALL;
        if (strArrSplit.length != 3) {
            networkInterface = networkInterface2;
        } else if (networkInterfaceOptions[0].equals(strArrSplit[2])) {
            networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
        } else if (networkInterfaceOptions[1].equals(strArrSplit[2])) {
            networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
        }
        if (strArrSplit.length > 3) {
            str2 = strArrSplit[2];
            if (networkInterfaceOptions[0].equals(strArrSplit[3])) {
                networkInterface2 = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(strArrSplit[3])) {
                networkInterface2 = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
        } else {
            str2 = "*";
            networkInterface2 = networkInterface;
        }
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.DENY, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(strSubstring);
        firewallRule.setPortNumber(strSubstring2);
        firewallRule.setPortLocation(portLocation2);
        firewallRule.setApplication(appIdentity);
        firewallRule.setNetworkInterface(networkInterface2);
        return firewallRule;
    }

    public static String translateFirewallRuleToOldFormat(FirewallRule firewallRule) {
        StringBuilder sb = new StringBuilder();
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[firewallRule.getRuleType().ordinal()];
        if (i == 1) {
            sb.append(firewallRule.getIpAddress());
            sb.append(":");
            sb.append(firewallRule.getPortNumber());
            sb.append(";");
            sb.append(convertPortLocation(firewallRule.getPortLocation()));
            if (!firewallRule.getNetworkInterface().equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.getNetworkInterface()));
            }
        } else if (i == 2) {
            sb.append(firewallRule.getIpAddress());
            sb.append(":");
            sb.append(firewallRule.getPortNumber());
            sb.append(";");
            sb.append(convertPortLocation(firewallRule.getPortLocation()));
            if (!"*".equals(firewallRule.getApplication().getPackageName()) || !firewallRule.getNetworkInterface().equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(firewallRule.getApplication().getPackageName());
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.getNetworkInterface()));
            }
        } else if (i == 3) {
            sb.append(firewallRule.getIpAddress());
            sb.append(":");
            sb.append(firewallRule.getPortNumber());
            sb.append(";");
            sb.append(firewallRule.getTargetIpAddress());
            sb.append(":");
            sb.append(firewallRule.getTargetPortNumber());
            if (!"*".equals(firewallRule.getApplication().getPackageName()) || !firewallRule.getNetworkInterface().equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(firewallRule.getApplication().getPackageName());
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.getNetworkInterface()));
            }
        } else if (i == 4) {
            sb.append(firewallRule.getIpAddress());
            sb.append(":");
            sb.append(firewallRule.getPortNumber());
        }
        return sb.toString();
    }

    public static FirewallRule translateRedirectExceptionRule(String str) {
        int iLastIndexOf;
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(";");
        if (strArrSplit.length < 1 || strArrSplit.length > 2 || (iLastIndexOf = strArrSplit[0].lastIndexOf(":")) == -1) {
            return null;
        }
        String strSubstring = strArrSplit[0].substring(0, iLastIndexOf);
        String strSubstring2 = strArrSplit[0].substring(iLastIndexOf + 1);
        String str2 = strArrSplit.length == 2 ? strArrSplit[1] : "*";
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.REDIRECT_EXCEPTION, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(strSubstring);
        firewallRule.setPortNumber(strSubstring2);
        firewallRule.setApplication(appIdentity);
        return firewallRule;
    }

    public static FirewallRule translateRedirectRule(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(";");
        if (strArrSplit.length != 2 && strArrSplit.length != 4) {
            return null;
        }
        Firewall.NetworkInterface networkInterface = Firewall.NetworkInterface.ALL_NETWORKS;
        int iLastIndexOf = strArrSplit[0].lastIndexOf(":");
        if (iLastIndexOf == -1) {
            return null;
        }
        String strSubstring = strArrSplit[0].substring(0, iLastIndexOf);
        String strSubstring2 = strArrSplit[0].substring(iLastIndexOf + 1);
        int iLastIndexOf2 = strArrSplit[1].lastIndexOf(":");
        if (iLastIndexOf2 == -1) {
            return null;
        }
        String strSubstring3 = strArrSplit[1].substring(0, iLastIndexOf2);
        String strSubstring4 = strArrSplit[1].substring(iLastIndexOf2 + 1);
        if (strArrSplit.length == 4) {
            str2 = strArrSplit[2];
            if (networkInterfaceOptions[0].equals(strArrSplit[3])) {
                networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(strArrSplit[3])) {
                networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
        } else {
            str2 = "*";
        }
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.REDIRECT, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(strSubstring);
        firewallRule.setPortNumber(strSubstring2);
        firewallRule.setTargetIpAddress(strSubstring3);
        firewallRule.setTargetPortNumber(strSubstring4);
        firewallRule.setApplication(appIdentity);
        firewallRule.setNetworkInterface(networkInterface);
        return firewallRule;
    }

    public static FirewallRule translateRule(String str, FirewallRule.RuleType ruleType) {
        if (ruleType == FirewallRule.RuleType.ALLOW) {
            return translateAllowRule(str);
        }
        if (ruleType == FirewallRule.RuleType.DENY) {
            return translateDenyRule(str);
        }
        if (ruleType == FirewallRule.RuleType.REDIRECT) {
            return translateRedirectRule(str);
        }
        if (ruleType == FirewallRule.RuleType.REDIRECT_EXCEPTION) {
            return translateRedirectExceptionRule(str);
        }
        return null;
    }
}
