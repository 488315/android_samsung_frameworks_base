package com.android.server.enterprise.firewall;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.firewall.FirewallDefinitions;
import com.android.server.enterprise.storage.EdmStorageDefs;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knoxguard.KnoxGuardManager;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public abstract class FirewallUtils {
    public static final String[] PACKAGE_EXCEPTION_KEYWORDS = {".mdm.", ".knox.", ".elm.", ".edm.", "com.android.captiveportallogin", ".klm", ".spay", ".ucm", ".ucs"};

    public static Firewall.Direction convertDirection(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.Direction.ALL;
        }
        if (str.equals(Firewall.Direction.INPUT.toString())) {
            return Firewall.Direction.INPUT;
        }
        if (str.equals(Firewall.Direction.OUTPUT.toString())) {
            return Firewall.Direction.OUTPUT;
        }
        if (str.equals(Firewall.Direction.FORWARD.toString())) {
            return Firewall.Direction.FORWARD;
        }
        return Firewall.Direction.ALL;
    }

    public static Firewall.NetworkInterface convertNetworkInterface(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.NetworkInterface.ALL_NETWORKS;
        }
        if (str.equals(Firewall.NetworkInterface.MOBILE_DATA_ONLY.toString())) {
            return Firewall.NetworkInterface.MOBILE_DATA_ONLY;
        }
        if (str.equals(Firewall.NetworkInterface.WIFI_DATA_ONLY.toString())) {
            return Firewall.NetworkInterface.WIFI_DATA_ONLY;
        }
        return Firewall.NetworkInterface.ALL_NETWORKS;
    }

    public static Firewall.PortLocation convertPortLocation(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.PortLocation.ALL;
        }
        if (str.equals(Firewall.PortLocation.LOCAL.toString())) {
            return Firewall.PortLocation.LOCAL;
        }
        if (str.equals(Firewall.PortLocation.REMOTE.toString())) {
            return Firewall.PortLocation.REMOTE;
        }
        return Firewall.PortLocation.ALL;
    }

    public static Firewall.Protocol convertProtocol(String str) {
        if (str.equals(Firewall.Protocol.TCP.toString())) {
            return Firewall.Protocol.TCP;
        }
        if (str.equals(Firewall.Protocol.UDP.toString())) {
            return Firewall.Protocol.UDP;
        }
        return Firewall.Protocol.ALL;
    }

    public static FirewallRule.Status convertStatus(String str) {
        if (TextUtils.isEmpty(str)) {
            return FirewallRule.Status.DISABLED;
        }
        if (str.equals(FirewallRule.Status.ENABLED.toString())) {
            return FirewallRule.Status.ENABLED;
        }
        if (str.equals(FirewallRule.Status.PENDING.toString())) {
            return FirewallRule.Status.PENDING;
        }
        return FirewallRule.Status.DISABLED;
    }

    public static Firewall.AddressType convertAddressType(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.AddressType.IPV4;
        }
        if (str.equals(Firewall.AddressType.IPV4.toString())) {
            return Firewall.AddressType.IPV4;
        }
        if (str.equals(Firewall.AddressType.IPV6.toString())) {
            return Firewall.AddressType.IPV6;
        }
        return Firewall.AddressType.IPV4;
    }

    public static ContentValues getContentValuesFromRule(FirewallRule firewallRule, int i) {
        ContentValues contentValues = new ContentValues();
        if (i != 1000) {
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("status", firewallRule.getStatus().toString());
        }
        int i2 = AbstractC14341.f1692x6178b957[firewallRule.getRuleType().ordinal()];
        if (i2 == 1) {
            contentValues.put("ruleType", FirewallRule.RuleType.ALLOW.toString());
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("portLocation", firewallRule.getPortLocation().toString());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("networkInterface", firewallRule.getNetworkInterface().toString());
            contentValues.put("direction", firewallRule.getDirection().toString());
            contentValues.put("protocol", firewallRule.getProtocol().toString());
            contentValues.put("addressType", firewallRule.getAddressType().toString());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
        } else if (i2 == 2) {
            contentValues.put("ruleType", FirewallRule.RuleType.DENY.toString());
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("portLocation", firewallRule.getPortLocation().toString());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("networkInterface", firewallRule.getNetworkInterface().toString());
            contentValues.put("direction", firewallRule.getDirection().toString());
            contentValues.put("protocol", firewallRule.getProtocol().toString());
            contentValues.put("addressType", firewallRule.getAddressType().toString());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
        } else if (i2 == 3) {
            contentValues.put("ruleType", FirewallRule.RuleType.REDIRECT.toString());
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("networkInterface", firewallRule.getNetworkInterface().toString());
            contentValues.put("protocol", firewallRule.getProtocol().toString());
            contentValues.put("addressType", firewallRule.getAddressType().toString());
            contentValues.put("targetIpAddress", firewallRule.getTargetIpAddress());
            contentValues.put("targetPortNumber", firewallRule.getTargetPortNumber());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
        } else if (i2 == 4) {
            contentValues.put("ruleType", FirewallRule.RuleType.REDIRECT_EXCEPTION.toString());
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("networkInterface", firewallRule.getNetworkInterface().toString());
            contentValues.put("protocol", firewallRule.getProtocol().toString());
            contentValues.put("addressType", firewallRule.getAddressType().toString());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
        }
        return contentValues;
    }

    public static FirewallRule[] getRuleFromContentValues(List list, FirewallRule.RuleType ruleType) {
        FirewallRule[] firewallRuleArr = new FirewallRule[list.size()];
        int i = AbstractC14341.f1692x6178b957[ruleType.ordinal()];
        String str = "portLocation";
        String str2 = "packageName";
        String str3 = "portNumber";
        String str4 = "addressType";
        if (i == 1) {
            String str5 = "addressType";
            Iterator it = list.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String str6 = str5;
                FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.ALLOW, convertAddressType(contentValues.getAsString(str6)));
                firewallRule.setIpAddress(contentValues.getAsString("ipAddress"));
                firewallRule.setPortNumber(contentValues.getAsString(str3));
                firewallRule.setPortLocation(convertPortLocation(contentValues.getAsString("portLocation")));
                firewallRule.setApplication(new AppIdentity(contentValues.getAsString("packageName"), contentValues.getAsString("signature")));
                firewallRule.setNetworkInterface(convertNetworkInterface(contentValues.getAsString("networkInterface")));
                firewallRule.setDirection(convertDirection(contentValues.getAsString("direction")));
                firewallRule.setProtocol(convertProtocol(contentValues.getAsString("protocol")));
                firewallRule.setStatus(convertStatus(contentValues.getAsString("status")));
                firewallRule.setId(contentValues.getAsInteger("id").intValue());
                firewallRule.setStrNetworkInterface(contentValues.getAsString("networkInterfaceStr"));
                firewallRuleArr[i2] = firewallRule;
                i2++;
                it = it;
                str3 = str3;
                str5 = str6;
            }
        } else if (i == 2) {
            String str7 = "packageName";
            Iterator it2 = list.iterator();
            int i3 = 0;
            while (it2.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it2.next();
                String str8 = str4;
                FirewallRule firewallRule2 = new FirewallRule(FirewallRule.RuleType.DENY, convertAddressType(contentValues2.getAsString(str4)));
                firewallRule2.setIpAddress(contentValues2.getAsString("ipAddress"));
                firewallRule2.setPortNumber(contentValues2.getAsString("portNumber"));
                firewallRule2.setPortLocation(convertPortLocation(contentValues2.getAsString(str)));
                String str9 = str7;
                firewallRule2.setApplication(new AppIdentity(contentValues2.getAsString(str9), contentValues2.getAsString("signature")));
                firewallRule2.setNetworkInterface(convertNetworkInterface(contentValues2.getAsString("networkInterface")));
                firewallRule2.setDirection(convertDirection(contentValues2.getAsString("direction")));
                firewallRule2.setProtocol(convertProtocol(contentValues2.getAsString("protocol")));
                firewallRule2.setStatus(convertStatus(contentValues2.getAsString("status")));
                firewallRule2.setId(contentValues2.getAsInteger("id").intValue());
                firewallRule2.setStrNetworkInterface(contentValues2.getAsString("networkInterfaceStr"));
                firewallRuleArr[i3] = firewallRule2;
                i3++;
                it2 = it2;
                str = str;
                str7 = str9;
                str4 = str8;
            }
        } else if (i == 3) {
            int i4 = 0;
            for (Iterator it3 = list.iterator(); it3.hasNext(); it3 = it3) {
                ContentValues contentValues3 = (ContentValues) it3.next();
                FirewallRule firewallRule3 = new FirewallRule(FirewallRule.RuleType.REDIRECT, convertAddressType(contentValues3.getAsString("addressType")));
                firewallRule3.setIpAddress(contentValues3.getAsString("ipAddress"));
                firewallRule3.setPortNumber(contentValues3.getAsString("portNumber"));
                firewallRule3.setTargetIpAddress(contentValues3.getAsString("targetIpAddress"));
                firewallRule3.setTargetPortNumber(contentValues3.getAsString("targetPortNumber"));
                firewallRule3.setApplication(new AppIdentity(contentValues3.getAsString("packageName"), contentValues3.getAsString("signature")));
                firewallRule3.setNetworkInterface(convertNetworkInterface(contentValues3.getAsString("networkInterface")));
                firewallRule3.setProtocol(convertProtocol(contentValues3.getAsString("protocol")));
                firewallRule3.setStatus(convertStatus(contentValues3.getAsString("status")));
                firewallRule3.setId(contentValues3.getAsInteger("id").intValue());
                firewallRule3.setStrNetworkInterface(contentValues3.getAsString("networkInterfaceStr"));
                firewallRuleArr[i4] = firewallRule3;
                i4++;
            }
        } else if (i == 4) {
            int i5 = 0;
            for (Iterator it4 = list.iterator(); it4.hasNext(); it4 = it4) {
                ContentValues contentValues4 = (ContentValues) it4.next();
                FirewallRule firewallRule4 = new FirewallRule(FirewallRule.RuleType.REDIRECT_EXCEPTION, convertAddressType(contentValues4.getAsString("addressType")));
                firewallRule4.setIpAddress(contentValues4.getAsString("ipAddress"));
                firewallRule4.setPortNumber(contentValues4.getAsString("portNumber"));
                firewallRule4.setApplication(new AppIdentity(contentValues4.getAsString(str2), contentValues4.getAsString("signature")));
                firewallRule4.setNetworkInterface(convertNetworkInterface(contentValues4.getAsString("networkInterface")));
                firewallRule4.setProtocol(convertProtocol(contentValues4.getAsString("protocol")));
                firewallRule4.setStatus(convertStatus(contentValues4.getAsString("status")));
                firewallRule4.setId(contentValues4.getAsInteger("id").intValue());
                firewallRule4.setStrNetworkInterface(contentValues4.getAsString("networkInterfaceStr"));
                firewallRuleArr[i5] = firewallRule4;
                i5++;
                str2 = str2;
            }
        }
        return firewallRuleArr;
    }

    public static boolean isRuleEnabled(FirewallRule firewallRule, int i, EdmStorageProvider edmStorageProvider) {
        String obj;
        String[] strArr = {"status"};
        if (edmStorageProvider == null) {
            return false;
        }
        ContentValues contentValuesFromRule = getContentValuesFromRule(firewallRule, i);
        contentValuesFromRule.remove("status");
        List values = edmStorageProvider.getValues("FirewallRule", strArr, contentValuesFromRule);
        return (values == null || values.isEmpty() || (obj = ((ContentValues) values.get(0)).get("status").toString()) == null || !obj.equals(FirewallRule.Status.ENABLED.toString())) ? false : true;
    }

    public static boolean isRuleInDb(FirewallRule firewallRule, int i, EdmStorageProvider edmStorageProvider) {
        if (edmStorageProvider == null) {
            return false;
        }
        ContentValues contentValuesFromRule = getContentValuesFromRule(firewallRule, i);
        contentValuesFromRule.remove("status");
        return !edmStorageProvider.getValues("FirewallRule", EdmStorageDefs.FIREWALL_RULE_COLUMNS, contentValuesFromRule).isEmpty();
    }

    public static boolean verifyPackageName(String str, int i) {
        if ("*".equals(str)) {
            return true;
        }
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return asInterface.getApplicationInfo(str, 0L, i) != null;
        } catch (RemoteException e) {
            Log.e("FirewallUtils", "Remote Exception on get getApplicationInfo" + e.toString());
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x008c, code lost:
    
        if (r11 == android.os.UserHandle.getUserId(java.lang.Integer.valueOf(r4).intValue())) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String filterRulesByUser(int i, String str) {
        int userId;
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            return sb.toString();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            int indexOf = nextToken.indexOf("owner UID match");
            boolean z = true;
            if (indexOf != -1) {
                String trim = nextToken.substring(indexOf + 15).trim();
                try {
                    if (trim.contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                        String[] split = trim.split(PackageManagerShellCommandDataLoader.STDIN_PATH);
                        int userId2 = UserHandle.getUserId(Integer.valueOf(split[0]).intValue());
                        int indexOf2 = split[1].indexOf(" ");
                        if (indexOf2 <= -1) {
                            userId = UserHandle.getUserId(Integer.valueOf(split[1]).intValue());
                        } else {
                            userId = UserHandle.getUserId(Integer.valueOf(split[1].substring(0, indexOf2)).intValue());
                        }
                        if (i >= userId2 && i <= userId) {
                        }
                    }
                    z = false;
                } catch (NumberFormatException unused) {
                }
            }
            if (z) {
                sb.append(nextToken);
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            }
        }
        return sb.toString();
    }

    public static boolean validateDomain(String str) {
        boolean z;
        boolean z2;
        boolean z3;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String trim = str.trim();
        if (trim.startsWith("http:") || trim.startsWith("https:") || trim.length() > 255) {
            return false;
        }
        if ("*".equals(trim)) {
            return true;
        }
        if (trim.startsWith("*")) {
            if (trim.startsWith("*.")) {
                trim = trim.substring(2);
            } else {
                trim = trim.substring(1);
            }
            z = true;
        } else {
            z = false;
        }
        if (trim.endsWith("*")) {
            if (trim.endsWith(".*")) {
                trim = trim.substring(0, trim.length() - 2);
            } else {
                trim = trim.substring(0, trim.length() - 1);
            }
            z2 = true;
        } else {
            z2 = false;
        }
        String[] split = trim.split("\\.");
        if (!z) {
            for (int i = 0; i < split[0].length(); i++) {
                char charAt = split[0].charAt(i);
                if ((charAt >= 'a' && charAt <= 'z') || ((charAt >= 'A' && charAt <= 'Z') || (charAt >= '0' && charAt <= '9'))) {
                    z3 = true;
                    break;
                }
            }
            z3 = false;
            if (!z3) {
                return false;
            }
        }
        int i2 = 0;
        for (int i3 = 0; i3 < trim.length(); i3++) {
            if (trim.charAt(i3) == '.') {
                i2++;
            }
        }
        if (i2 >= split.length) {
            return false;
        }
        for (String str2 : split) {
            if (str2.length() > 63) {
                return false;
            }
        }
        for (String str3 : split) {
            if (!str3.matches("^[A-Za-z0-9-]+$") || ((str3.charAt(0) == '-' && !z) || (str3.charAt(str3.length() - 1) == '-' && !z2))) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: com.android.server.enterprise.firewall.FirewallUtils$1 */
    public abstract /* synthetic */ class AbstractC14341 {

        /* renamed from: $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table */
        public static final /* synthetic */ int[] f1691xb736b260;

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType */
        public static final /* synthetic */ int[] f1692x6178b957;

        static {
            int[] iArr = new int[FirewallDefinitions.Table.values().length];
            f1691xb736b260 = iArr;
            try {
                iArr[FirewallDefinitions.Table.FILTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1691xb736b260[FirewallDefinitions.Table.NAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[FirewallRule.RuleType.values().length];
            f1692x6178b957 = iArr2;
            try {
                iArr2[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1692x6178b957[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1692x6178b957[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1692x6178b957[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static boolean isIpv6SupportedForTable(FirewallDefinitions.Table table) {
        int i = AbstractC14341.f1691xb736b260[table.ordinal()];
        if (i == 1) {
            return Firewall.mHasIpv6FilterSupport;
        }
        if (i != 2) {
            return false;
        }
        return Firewall.mHasIpv6NatSupport;
    }

    public static FirewallDefinitions.Table getTableByRuletype(FirewallRule.RuleType ruleType) {
        if (FirewallRule.RuleType.ALLOW.equals(ruleType) || FirewallRule.RuleType.DENY.equals(ruleType)) {
            return FirewallDefinitions.Table.FILTER;
        }
        return FirewallDefinitions.Table.NAT;
    }

    public static ArrayList getAllUsers(Context context) {
        ArrayList arrayList = new ArrayList();
        UserManager userManager = (UserManager) context.getSystemService("user");
        if (userManager != null) {
            Iterator it = userManager.getUsers().iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).getUserHandle().getIdentifier()));
            }
        }
        return arrayList;
    }

    public static String getAppOrUserUid(FirewallRule firewallRule, ContextInfo contextInfo) {
        return getAppOrUserUid(firewallRule.getPackageName(), UserHandle.getUserId(contextInfo.mCallerUid), Integer.valueOf(firewallRule.getPackageUid()), false);
    }

    public static String getAppOrUserUid(String str, int i, boolean z) {
        return getAppOrUserUid(str, i, -1, z);
    }

    public static String getAppOrUserUid(String str, int i, Integer num, boolean z) {
        int i2;
        if (str == null) {
            return " -m owner --uid-owner ";
        }
        if ("*".equals(str)) {
            int i3 = 99999;
            if (i == 0) {
                i2 = z ? 10000 : 1001;
            } else {
                i2 = i * 100000;
                i3 = 99999 + i2;
            }
            return " -m owner --uid-owner " + i2 + PackageManagerShellCommandDataLoader.STDIN_PATH + i3;
        }
        Map map = FirewallDefinitions.EXEMPT_PACKAGE_LIST;
        if (map.containsKey(str)) {
            return " -m owner --uid-owner " + map.get(str);
        }
        if ("dns_tether".equals(str)) {
            return " -m owner --uid-owner 1052";
        }
        if (num == null || num.intValue() == -1) {
            num = getUidForApplication(str, i);
        }
        if (num == null) {
            return " -m owner --uid-owner ";
        }
        return " -m owner --uid-owner " + num;
    }

    public static Integer getUidForApplication(String str, int i) {
        ApplicationInfo applicationInfo;
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                applicationInfo = asInterface.getApplicationInfo(str, 0L, i);
            } catch (RemoteException e) {
                Log.e("FirewallUtils", "Remote Exception on get getApplicationInfo" + e.toString());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                applicationInfo = null;
            }
            if (applicationInfo == null) {
                return null;
            }
            return Integer.valueOf(applicationInfo.uid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static String convertNetworkInterfaceForIptables(Firewall.NetworkInterface networkInterface) {
        Set allNetworkInterfaces = getAllNetworkInterfaces();
        if (networkInterface.equals(Firewall.NetworkInterface.MOBILE_DATA_ONLY)) {
            return getBestEffortInterfaceName(allNetworkInterfaces, Arrays.asList("rmnet", "ccinet"), "net", "rmnet+");
        }
        return networkInterface.equals(Firewall.NetworkInterface.WIFI_DATA_ONLY) ? getBestEffortInterfaceName(allNetworkInterfaces, Arrays.asList("wlan"), "lan", "wlan+") : "";
    }

    public static Set getAllNetworkInterfaces() {
        HashSet hashSet = new HashSet();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if (nextElement.getName() != null && !nextElement.getName().isEmpty()) {
                        hashSet.add(nextElement.getName());
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("FirewallUtils", e.getMessage());
        }
        return hashSet;
    }

    public static String getBestEffortInterfaceName(Set set, List list, String str, String str2) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                String str4 = (String) it2.next();
                if (str3.startsWith(str4)) {
                    return str4 + "+";
                }
            }
        }
        Iterator it3 = set.iterator();
        while (it3.hasNext()) {
            String str5 = (String) it3.next();
            if (str5.contains(str)) {
                return FirewallDefinitions.PATTERN_NUMERAL_ENDING_SUFIX.matcher(str5).replaceFirst("+");
            }
        }
        return str2;
    }

    public static List getUidsToExemptForUser(int i, Context context) {
        PackageManager packageManager = context.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(64, i);
            if (installedPackagesAsUser == null || installedPackagesAsUser.isEmpty()) {
                Log.e("FirewallUtils", "packages list is either null or empty " + installedPackagesAsUser);
                return new ArrayList();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return getUidsToExemptFromPackages(installedPackagesAsUser, context, true);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static List getUidsToExemptFromPackages(List list, Context context, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("android", 64);
            if (packageInfo == null) {
                Log.e("FirewallUtils", "processPackageExceptionList() - systemInfo is null");
                return arrayList;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            HashSet hashSet = new HashSet();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                PackageInfo packageInfo2 = (PackageInfo) it.next();
                if (packageInfo2.applicationInfo.uid >= 10000 && Utils.isSystemApplication(packageInfo2, packageInfo.signatures)) {
                    for (String str : PACKAGE_EXCEPTION_KEYWORDS) {
                        if (packageInfo2.applicationInfo.packageName.contains(str)) {
                            hashSet.add(Integer.valueOf(packageInfo2.applicationInfo.uid));
                        }
                    }
                }
            }
            arrayList.addAll(hashSet);
            if (!z) {
                return arrayList;
            }
            Iterator it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                PackageInfo packageInfo3 = (PackageInfo) it2.next();
                if (KnoxCustomManagerService.KG_PKG_NAME.equals(packageInfo3.applicationInfo.packageName) && isKGExemptRuleRequired()) {
                    arrayList.add(Integer.valueOf(packageInfo3.applicationInfo.uid));
                    break;
                }
            }
            return arrayList;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FirewallUtils", "processPackageExceptionList() - failed to retrieve package info for system", e);
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isKGExemptRuleRequired() {
        return KnoxGuardManager.getInstance().isVpnExceptionRequired();
    }

    public static boolean isPackageExempt(String str) {
        if (KnoxCustomManagerService.KG_PKG_NAME.equals(str)) {
            return isKGExemptRuleRequired();
        }
        for (String str2 : PACKAGE_EXCEPTION_KEYWORDS) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    public static List getDomainsFromContentValues(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ContentValues) it.next()).getAsString("domain"));
        }
        return arrayList;
    }

    public static List getListFromDb(ContentValues contentValues, String str, EdmStorageProvider edmStorageProvider) {
        String asString = contentValues.getAsString("packageName");
        Integer asInteger = contentValues.getAsInteger("adminUid");
        Log.i("FirewallUtils", "getListFromDb() - packageName: " + asString + " userId " + asInteger);
        if (asInteger != null) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("adminUid", asInteger);
            contentValues2.put("packageName", asString);
            contentValues2.put("typeList", str);
            List values = edmStorageProvider.getValues("DomainFilterListTable", (String[]) null, contentValues2);
            if (values != null && !values.isEmpty()) {
                Log.i("FirewallUtils", "getListFromDb() - cvlist is not empty ");
                return getDomainsFromContentValues(values);
            }
        }
        Log.i("FirewallUtils", "getListFromDb() - containsKey(type) ");
        return new ArrayList();
    }
}
