package com.android.server.enterprise.firewall;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.enterprise.firewall.FirewallDefinitions;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knoxguard.KnoxGuardManager;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class FirewallUtils {
    public static final String[] PACKAGE_EXCEPTION_KEYWORDS = {".mdm.", ".knox.", ".elm.", ".edm.", "com.android.captiveportallogin", ".klm", ".spay", ".ucm", ".ucs"};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.firewall.FirewallUtils$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table;
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

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
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static Firewall.AddressType convertAddressType(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.AddressType.IPV4;
        }
        Firewall.AddressType addressType = Firewall.AddressType.IPV4;
        if (str.equals(addressType.toString())) {
            return addressType;
        }
        Firewall.AddressType addressType2 = Firewall.AddressType.IPV6;
        return str.equals(addressType2.toString()) ? addressType2 : addressType;
    }

    public static Firewall.Direction convertDirection(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.Direction.ALL;
        }
        Firewall.Direction direction = Firewall.Direction.INPUT;
        if (str.equals(direction.toString())) {
            return direction;
        }
        Firewall.Direction direction2 = Firewall.Direction.OUTPUT;
        if (str.equals(direction2.toString())) {
            return direction2;
        }
        Firewall.Direction direction3 = Firewall.Direction.FORWARD;
        return str.equals(direction3.toString()) ? direction3 : Firewall.Direction.ALL;
    }

    public static Firewall.NetworkInterface convertNetworkInterface(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.NetworkInterface.ALL_NETWORKS;
        }
        Firewall.NetworkInterface networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
        if (str.equals(networkInterface.toString())) {
            return networkInterface;
        }
        Firewall.NetworkInterface networkInterface2 = Firewall.NetworkInterface.WIFI_DATA_ONLY;
        return str.equals(networkInterface2.toString()) ? networkInterface2 : Firewall.NetworkInterface.ALL_NETWORKS;
    }

    public static String convertNetworkInterfaceForIptables(Firewall.NetworkInterface networkInterface) {
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
        return networkInterface.equals(Firewall.NetworkInterface.MOBILE_DATA_ONLY) ? getBestEffortInterfaceName(hashSet, Arrays.asList("rmnet", "ccinet"), "net", "rmnet+") : networkInterface.equals(Firewall.NetworkInterface.WIFI_DATA_ONLY) ? getBestEffortInterfaceName(hashSet, Arrays.asList("wlan"), "lan", "wlan+") : "";
    }

    public static Firewall.PortLocation convertPortLocation(String str) {
        if (TextUtils.isEmpty(str)) {
            return Firewall.PortLocation.ALL;
        }
        Firewall.PortLocation portLocation = Firewall.PortLocation.LOCAL;
        if (str.equals(portLocation.toString())) {
            return portLocation;
        }
        Firewall.PortLocation portLocation2 = Firewall.PortLocation.REMOTE;
        return str.equals(portLocation2.toString()) ? portLocation2 : Firewall.PortLocation.ALL;
    }

    public static Firewall.Protocol convertProtocol(String str) {
        Firewall.Protocol protocol = Firewall.Protocol.TCP;
        if (str.equals(protocol.toString())) {
            return protocol;
        }
        Firewall.Protocol protocol2 = Firewall.Protocol.UDP;
        return str.equals(protocol2.toString()) ? protocol2 : Firewall.Protocol.ALL;
    }

    public static FirewallRule.Status convertStatus(String str) {
        if (TextUtils.isEmpty(str)) {
            return FirewallRule.Status.DISABLED;
        }
        FirewallRule.Status status = FirewallRule.Status.ENABLED;
        if (str.equals(status.toString())) {
            return status;
        }
        FirewallRule.Status status2 = FirewallRule.Status.PENDING;
        return str.equals(status2.toString()) ? status2 : FirewallRule.Status.DISABLED;
    }

    public static String filterRulesByUser(int i, String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            return sb.toString();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\n");
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            int indexOf = nextToken.indexOf("owner UID match");
            if (indexOf != -1) {
                String trim = nextToken.substring(indexOf + 15).trim();
                try {
                    if (trim.contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                        String[] split = trim.split(PackageManagerShellCommandDataLoader.STDIN_PATH);
                        int userId = UserHandle.getUserId(Integer.valueOf(split[0]).intValue());
                        int indexOf2 = split[1].indexOf(" ");
                        int userId2 = indexOf2 <= -1 ? UserHandle.getUserId(Integer.valueOf(split[1]).intValue()) : UserHandle.getUserId(Integer.valueOf(split[1].substring(0, indexOf2)).intValue());
                        if (i >= userId && i <= userId2) {
                        }
                    } else if (i == UserHandle.getUserId(Integer.valueOf(trim).intValue())) {
                    }
                } catch (NumberFormatException unused) {
                }
            }
            sb.append(nextToken);
            sb.append("\n");
        }
        return sb.toString();
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

    public static String getAppOrUserUid(ContextInfo contextInfo, FirewallRule firewallRule) {
        return getAppOrUserUid(firewallRule.getPackageName(), UserHandle.getUserId(contextInfo.mCallerUid), Integer.valueOf(firewallRule.getPackageUid()), false);
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
            return ArrayUtils$$ExternalSyntheticOutline0.m(i2, i3, " -m owner --uid-owner ", PackageManagerShellCommandDataLoader.STDIN_PATH);
        }
        if ("SYSTEM_UIDS".equals(str)) {
            return " -m owner --uid-owner 0-1000";
        }
        if ("dns_tether".equals(str)) {
            return " -m owner --uid-owner 1052";
        }
        if (num.intValue() == -1) {
            num = getUidForApplication(i, str);
        }
        if (num == null) {
            return " -m owner --uid-owner ";
        }
        return " -m owner --uid-owner " + num;
    }

    public static String getBestEffortInterfaceName(Set set, List list, String str, String str2) {
        HashSet hashSet = (HashSet) set;
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                String str4 = (String) it2.next();
                if (str3.startsWith(str4)) {
                    return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str4, "+");
                }
            }
        }
        Iterator it3 = hashSet.iterator();
        while (it3.hasNext()) {
            String str5 = (String) it3.next();
            if (str5.contains(str)) {
                return FirewallDefinitions.PATTERN_NUMERAL_ENDING_SUFIX.matcher(str5).replaceFirst("+");
            }
        }
        return str2;
    }

    public static ContentValues getContentValuesFromRule(FirewallRule firewallRule, int i) {
        ContentValues contentValues = new ContentValues();
        if (i != 1000) {
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put(Constants.JSON_CLIENT_DATA_STATUS, firewallRule.getStatus().toString());
        }
        int i2 = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[firewallRule.getRuleType().ordinal()];
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

    public static List getListFromDb(ContentValues contentValues, String str, EdmStorageProvider edmStorageProvider) {
        String asString = contentValues.getAsString("packageName");
        Integer asInteger = contentValues.getAsInteger("adminUid");
        Log.i("FirewallUtils", "getListFromDb() - packageName: " + asString + " userId " + asInteger);
        if (asInteger != null) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("adminUid", asInteger);
            contentValues2.put("packageName", asString);
            contentValues2.put("typeList", str);
            ArrayList arrayList = (ArrayList) edmStorageProvider.getValues("DomainFilterListTable", null, contentValues2);
            if (!arrayList.isEmpty()) {
                Log.i("FirewallUtils", "getListFromDb() - cvlist is not empty ");
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(((ContentValues) it.next()).getAsString("domain"));
                }
                return arrayList2;
            }
        }
        Log.i("FirewallUtils", "getListFromDb() - containsKey(type) ");
        return new ArrayList();
    }

    public static List getPlatformUidsToExemptFromPackages(List list) {
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (FirewallDefinitions.EXEMPT_PACKAGE_LIST.contains(packageInfo.applicationInfo.packageName) && !"com.samsung.android.kgclient".equals(packageInfo.applicationInfo.packageName)) {
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if (isPlatformSigned(UserHandle.getUserId(applicationInfo.uid), applicationInfo.packageName)) {
                    hashSet.add(Integer.valueOf(packageInfo.applicationInfo.uid));
                }
            }
        }
        arrayList.addAll(hashSet);
        return arrayList;
    }

    public static FirewallRule[] getRuleFromContentValues(List list, FirewallRule.RuleType ruleType) {
        FirewallRule[] firewallRuleArr = new FirewallRule[list.size()];
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[ruleType.ordinal()];
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
                firewallRule.setStatus(convertStatus(contentValues.getAsString(Constants.JSON_CLIENT_DATA_STATUS)));
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
                firewallRule2.setStatus(convertStatus(contentValues2.getAsString(Constants.JSON_CLIENT_DATA_STATUS)));
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
                firewallRule3.setStatus(convertStatus(contentValues3.getAsString(Constants.JSON_CLIENT_DATA_STATUS)));
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
                firewallRule4.setStatus(convertStatus(contentValues4.getAsString(Constants.JSON_CLIENT_DATA_STATUS)));
                firewallRule4.setId(contentValues4.getAsInteger("id").intValue());
                firewallRule4.setStrNetworkInterface(contentValues4.getAsString("networkInterfaceStr"));
                firewallRuleArr[i5] = firewallRule4;
                i5++;
                str2 = str2;
            }
        }
        return firewallRuleArr;
    }

    public static FirewallDefinitions.Table getTableByRuletype(FirewallRule.RuleType ruleType) {
        return (FirewallRule.RuleType.ALLOW.equals(ruleType) || FirewallRule.RuleType.DENY.equals(ruleType)) ? FirewallDefinitions.Table.FILTER : FirewallDefinitions.Table.NAT;
    }

    public static Integer getUidForApplication(int i, String str) {
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

    public static List getUidsToExemptFromPackages(Context context, List list, boolean z) {
        Signature[] signatureArr;
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
                ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
                if (applicationInfo.uid >= 10000) {
                    Signature[] signatureArr2 = packageInfo.signatures;
                    char[] cArr = Utils.HEX_DIGITS;
                    if (signatureArr2 == null) {
                        Log.e("EnterpriseUtils", "invalid param, pkgInfo or signature null");
                    } else if ((applicationInfo.flags & 129) != 0 && (signatureArr = packageInfo2.signatures) != null && signatureArr.length == signatureArr2.length) {
                        int i = 0;
                        while (true) {
                            if (i >= signatureArr2.length) {
                                String[] strArr = PACKAGE_EXCEPTION_KEYWORDS;
                                for (int i2 = 0; i2 < 9; i2++) {
                                    if (packageInfo2.applicationInfo.packageName.contains(strArr[i2])) {
                                        hashSet.add(Integer.valueOf(packageInfo2.applicationInfo.uid));
                                    }
                                }
                            } else {
                                if (!signatureArr2[i].equals(packageInfo2.signatures[i])) {
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                }
            }
            if (!z) {
                arrayList.addAll(hashSet);
                return arrayList;
            }
            hashSet.addAll(getPlatformUidsToExemptFromPackages(list));
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                PackageInfo packageInfo3 = (PackageInfo) it2.next();
                if ("com.samsung.android.kgclient".equals(packageInfo3.applicationInfo.packageName) && isKGExemptRuleRequired()) {
                    hashSet.add(Integer.valueOf(packageInfo3.applicationInfo.uid));
                }
            }
            arrayList.addAll(hashSet);
            return arrayList;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FirewallUtils", "processPackageExceptionList() - failed to retrieve package info for system", e);
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isIpv6SupportedForTable(FirewallDefinitions.Table table) {
        int ordinal = table.ordinal();
        if (ordinal == 0) {
            return Firewall.mHasIpv6FilterSupport;
        }
        if (ordinal != 1) {
            return false;
        }
        boolean z = Firewall.mHasIpv6FilterSupport;
        return false;
    }

    public static boolean isKGExemptRuleRequired() {
        return KnoxGuardManager.getInstance().isVpnExceptionRequired();
    }

    public static boolean isPlatformSigned(int i, String str) {
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return asInterface.checkSignatures("android", str, i) == 0;
        } catch (RemoteException e) {
            Log.e("FirewallUtils", "Remote Exception on get checkSignatures" + e.toString());
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isRuleEnabled(FirewallRule firewallRule, int i, EdmStorageProvider edmStorageProvider) {
        String obj;
        String[] strArr = {Constants.JSON_CLIENT_DATA_STATUS};
        if (edmStorageProvider == null) {
            return false;
        }
        ContentValues contentValuesFromRule = getContentValuesFromRule(firewallRule, i);
        contentValuesFromRule.remove(Constants.JSON_CLIENT_DATA_STATUS);
        ArrayList arrayList = (ArrayList) edmStorageProvider.getValues("FirewallRule", strArr, contentValuesFromRule);
        return (arrayList.isEmpty() || (obj = ((ContentValues) arrayList.get(0)).get(Constants.JSON_CLIENT_DATA_STATUS).toString()) == null || !obj.equals(FirewallRule.Status.ENABLED.toString())) ? false : true;
    }

    public static boolean validateDomain(String str) {
        boolean z;
        boolean z2;
        int i;
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
            trim = trim.startsWith("*.") ? trim.substring(2) : trim.substring(1);
            z = true;
        } else {
            z = false;
        }
        if (trim.endsWith("*")) {
            trim = trim.endsWith(".*") ? DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(2, 0, trim) : DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(1, 0, trim);
            z2 = true;
        } else {
            z2 = false;
        }
        String[] split = trim.split("\\.");
        if (!z) {
            for (0; i < split[0].length(); i + 1) {
                char charAt = split[0].charAt(i);
                i = ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z') && (charAt < '0' || charAt > '9')) ? i + 1 : 0;
            }
            return false;
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

    public static boolean verifyPackageName(int i, String str) {
        if ("*".equals(str) || "SYSTEM_UIDS".equals(str)) {
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
}
