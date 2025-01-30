package com.samsung.android.knox.net.firewall;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FirewallRuleTranslator {
    public static String[] networkInterfaceOptions = {ImsProfile.PDN_WIFI, "data", "*"};
    public static String[] portLocationOptions = {"remote", "local", "*"};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.knox.net.firewall.FirewallRuleTranslator$1 */
    public static /* synthetic */ class C47251 {

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType */
        public static final /* synthetic */ int[] f491x6178b957;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            f491x6178b957 = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f491x6178b957[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f491x6178b957[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f491x6178b957[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FirewallRule translateAllowRule(String str) {
        Firewall.NetworkInterface networkInterface;
        String str2;
        if (str == null) {
            return null;
        }
        String[] split = str.split(";");
        if (split.length < 2 || split.length > 4) {
            return null;
        }
        Firewall.PortLocation portLocation = Firewall.PortLocation.REMOTE;
        Firewall.NetworkInterface networkInterface2 = Firewall.NetworkInterface.ALL_NETWORKS;
        int lastIndexOf = split[0].lastIndexOf(":");
        if (lastIndexOf == -1) {
            return null;
        }
        String substring = split[0].substring(0, lastIndexOf);
        String substring2 = split[0].substring(lastIndexOf + 1);
        Firewall.PortLocation portLocation2 = portLocationOptions[0].equals(split[1]) ? Firewall.PortLocation.REMOTE : portLocationOptions[1].equals(split[1]) ? Firewall.PortLocation.LOCAL : Firewall.PortLocation.ALL;
        if (split.length == 3) {
            if (networkInterfaceOptions[0].equals(split[2])) {
                networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(split[2])) {
                networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
            if (split.length <= 3) {
                str2 = split[2];
                if (networkInterfaceOptions[0].equals(split[3])) {
                    networkInterface2 = Firewall.NetworkInterface.WIFI_DATA_ONLY;
                } else if (networkInterfaceOptions[1].equals(split[3])) {
                    networkInterface2 = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
                }
            } else {
                str2 = "*";
                networkInterface2 = networkInterface;
            }
            FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
            AppIdentity appIdentity = new AppIdentity(str2, (String) null);
            firewallRule.setIpAddress(substring);
            firewallRule.setPortNumber(substring2);
            firewallRule.setPortLocation(portLocation2);
            firewallRule.setApplication(appIdentity);
            firewallRule.setNetworkInterface(networkInterface2);
            return firewallRule;
        }
        networkInterface = networkInterface2;
        if (split.length <= 3) {
        }
        FirewallRule firewallRule2 = new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
        AppIdentity appIdentity2 = new AppIdentity(str2, (String) null);
        firewallRule2.setIpAddress(substring);
        firewallRule2.setPortNumber(substring2);
        firewallRule2.setPortLocation(portLocation2);
        firewallRule2.setApplication(appIdentity2);
        firewallRule2.setNetworkInterface(networkInterface2);
        return firewallRule2;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FirewallRule translateDenyRule(String str) {
        Firewall.NetworkInterface networkInterface;
        String str2;
        if (str == null) {
            return null;
        }
        String[] split = str.split(";");
        if (split.length < 2 || split.length > 4) {
            return null;
        }
        Firewall.PortLocation portLocation = Firewall.PortLocation.REMOTE;
        Firewall.NetworkInterface networkInterface2 = Firewall.NetworkInterface.ALL_NETWORKS;
        int lastIndexOf = split[0].lastIndexOf(":");
        if (lastIndexOf == -1) {
            return null;
        }
        String substring = split[0].substring(0, lastIndexOf);
        String substring2 = split[0].substring(lastIndexOf + 1);
        Firewall.PortLocation portLocation2 = portLocationOptions[0].equals(split[1]) ? Firewall.PortLocation.REMOTE : portLocationOptions[1].equals(split[1]) ? Firewall.PortLocation.LOCAL : Firewall.PortLocation.ALL;
        if (split.length == 3) {
            if (networkInterfaceOptions[0].equals(split[2])) {
                networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(split[2])) {
                networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
            if (split.length <= 3) {
                str2 = split[2];
                if (networkInterfaceOptions[0].equals(split[3])) {
                    networkInterface2 = Firewall.NetworkInterface.WIFI_DATA_ONLY;
                } else if (networkInterfaceOptions[1].equals(split[3])) {
                    networkInterface2 = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
                }
            } else {
                str2 = "*";
                networkInterface2 = networkInterface;
            }
            FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.DENY, Firewall.AddressType.IPV4);
            AppIdentity appIdentity = new AppIdentity(str2, (String) null);
            firewallRule.setIpAddress(substring);
            firewallRule.setPortNumber(substring2);
            firewallRule.setPortLocation(portLocation2);
            firewallRule.setApplication(appIdentity);
            firewallRule.setNetworkInterface(networkInterface2);
            return firewallRule;
        }
        networkInterface = networkInterface2;
        if (split.length <= 3) {
        }
        FirewallRule firewallRule2 = new FirewallRule(FirewallRule.RuleType.DENY, Firewall.AddressType.IPV4);
        AppIdentity appIdentity2 = new AppIdentity(str2, (String) null);
        firewallRule2.setIpAddress(substring);
        firewallRule2.setPortNumber(substring2);
        firewallRule2.setPortLocation(portLocation2);
        firewallRule2.setApplication(appIdentity2);
        firewallRule2.setNetworkInterface(networkInterface2);
        return firewallRule2;
    }

    public static String translateFirewallRuleToOldFormat(FirewallRule firewallRule) {
        StringBuilder sb = new StringBuilder();
        int i = C47251.f491x6178b957[firewallRule.mRuleType.ordinal()];
        if (i == 1) {
            sb.append(firewallRule.mAddress);
            sb.append(":");
            sb.append(firewallRule.mPortNumber);
            sb.append(";");
            sb.append(convertPortLocation(firewallRule.getPortLocation()));
            if (!firewallRule.mNetworkInterface.equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.mNetworkInterface));
            }
        } else if (i == 2) {
            sb.append(firewallRule.mAddress);
            sb.append(":");
            sb.append(firewallRule.mPortNumber);
            sb.append(";");
            sb.append(convertPortLocation(firewallRule.getPortLocation()));
            if (!"*".equals(firewallRule.mAppIdentity.getPackageName()) || !firewallRule.mNetworkInterface.equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(firewallRule.mAppIdentity.getPackageName());
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.mNetworkInterface));
            }
        } else if (i == 3) {
            sb.append(firewallRule.mAddress);
            sb.append(":");
            sb.append(firewallRule.mPortNumber);
            sb.append(";");
            sb.append(firewallRule.getTargetIpAddress());
            sb.append(":");
            sb.append(firewallRule.getTargetPortNumber());
            if (!"*".equals(firewallRule.mAppIdentity.getPackageName()) || !firewallRule.mNetworkInterface.equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(firewallRule.mAppIdentity.getPackageName());
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.mNetworkInterface));
            }
        } else if (i == 4) {
            sb.append(firewallRule.mAddress);
            sb.append(":");
            sb.append(firewallRule.mPortNumber);
        }
        return sb.toString();
    }

    public static FirewallRule translateRedirectExceptionRule(String str) {
        int lastIndexOf;
        if (str == null) {
            return null;
        }
        String[] split = str.split(";");
        if (split.length < 1 || split.length > 2 || (lastIndexOf = split[0].lastIndexOf(":")) == -1) {
            return null;
        }
        String substring = split[0].substring(0, lastIndexOf);
        String substring2 = split[0].substring(lastIndexOf + 1);
        String str2 = split.length == 2 ? split[1] : "*";
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.REDIRECT_EXCEPTION, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(substring);
        firewallRule.setPortNumber(substring2);
        firewallRule.setApplication(appIdentity);
        return firewallRule;
    }

    public static FirewallRule translateRedirectRule(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        String[] split = str.split(";");
        if (split.length != 2 && split.length != 4) {
            return null;
        }
        Firewall.NetworkInterface networkInterface = Firewall.NetworkInterface.ALL_NETWORKS;
        int lastIndexOf = split[0].lastIndexOf(":");
        if (lastIndexOf == -1) {
            return null;
        }
        String substring = split[0].substring(0, lastIndexOf);
        String substring2 = split[0].substring(lastIndexOf + 1);
        int lastIndexOf2 = split[1].lastIndexOf(":");
        if (lastIndexOf2 == -1) {
            return null;
        }
        String substring3 = split[1].substring(0, lastIndexOf2);
        String substring4 = split[1].substring(lastIndexOf2 + 1);
        if (split.length == 4) {
            str2 = split[2];
            if (networkInterfaceOptions[0].equals(split[3])) {
                networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(split[3])) {
                networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
        } else {
            str2 = "*";
        }
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.REDIRECT, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(substring);
        firewallRule.setPortNumber(substring2);
        firewallRule.setTargetIpAddress(substring3);
        firewallRule.setTargetPortNumber(substring4);
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
