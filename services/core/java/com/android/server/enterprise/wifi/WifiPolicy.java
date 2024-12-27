package com.android.server.enterprise.wifi;

import android.R;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.admin.WifiSsidPolicy;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiSsid;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.adapterlayer.WifiManagerAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.proxy.LocalProxyManager;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.NetworkUtils;
import com.android.server.enterprise.utils.Utils;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.pm.UserManagerInternal;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import com.samsung.android.knox.net.wifi.WifiControlInfo;
import com.samsung.android.wifi.SemWifiManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public final class WifiPolicy extends IWifiPolicy.Stub implements EnterpriseServiceCallback {
    public ApplicationPolicy mAppPolicy;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final LocalProxyManager mLocalProxyManager;
    public final UserManager mUserManager;
    public final UserManagerInternal mUserManagerInternal;
    public WifiManagerAdapter mWifiAdapter;
    public static final String[] EAP_STRINGS = {
        "PEAP", "TLS", "TTLS", "PWD", "SIM", "AKA", "AKA'", "WFA-UNAUTH-TLS", "WAPI_CERT"
    };
    public static final String[] PHASE2_STRINGS = {
        "NULL", "PAP", "MSCHAP", "MSCHAPV2", "GTC", "SIM", "AKA", "AKA'"
    };
    public static final String[] ACCEPTABLE_SECURITY_LEVELS = {
        "NONE",
        "WEP",
        "PSK",
        "",
        "",
        "EAP-PEAP",
        "EAP-TTLS",
        "EAP-TLS",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "EAP-PWD",
        "",
        "",
        "EAP-SIM",
        "",
        "",
        "EAP-AKA",
        "",
        "",
        "EAP-AKA'",
        "",
        "",
        "SAE"
    };
    public static final String[] SECURITY_LEVELS = {
        "NONE",
        "WEP",
        "PSK",
        "EAP-LEAP",
        "EAP-FAST",
        "EAP-PEAP",
        "EAP-TTLS",
        "EAP-TLS",
        "FT-PSK",
        "EAP-PEAP-FT",
        "EAP-PEAP-CCKM",
        "EAP-TTLS-FT",
        "EAP-TTLS-CCKM",
        "EAP-TLS-FT",
        "EAP-TLS-CCKM",
        "EAP-LEAP-FT",
        "EAP-LEAP-CCKM",
        "EAP-FAST-FT",
        "EAP-FAST-CCKM",
        "EAP-PWD",
        "EAP-PWD-FT",
        "EAP-PWD-CCKM",
        "EAP-SIM",
        "EAP-SIM-FT",
        "EAP-SIM-CCKM",
        "EAP-AKA",
        "EAP-AKA-FT",
        "EAP-AKA-CCKM",
        "EAP-AKA'",
        "EAP-AKA'-FT",
        "EAP-AKA'-CCKM",
        "SAE"
    };
    public static final Pattern HOSTNAME_PATTERN =
            Pattern.compile(
                    "^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$");
    public static final Pattern EXCLLIST_PATTERN =
            Pattern.compile(
                    "^$|^[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*(,[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*)*$");
    public WifiManager mWifiManager = null;
    public volatile boolean mIsAPSettingFromAdmin = false;
    public final AtomicInteger mWifiState = new AtomicInteger(4);
    public EnterpriseDeviceManager mEDM = null;

    public WifiPolicy(Context context) {
        this.mAppPolicy = null;
        this.mUserManagerInternal = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
                    // com.android.server.enterprise.wifi.WifiPolicy.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Map allNetworksByAdminUid;
                        String action = intent.getAction();
                        Log.d("WifiPolicyService", action);
                        if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            int intExtra = intent.getIntExtra("wifi_state", 4);
                            if ((intExtra == 2 || intExtra == 0)
                                    && intExtra != WifiPolicy.this.mWifiState.get()) {
                                WifiPolicy.this.mWifiState.set(intExtra);
                                AuditLog.logEvent(intExtra != 2 ? 3 : 2, new Object[0]);
                                return;
                            }
                            return;
                        }
                        if (!action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                            if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"
                                    .equals(action)) {
                                int intExtra2 =
                                        intent.getIntExtra(
                                                "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                                                0);
                                WifiPolicy wifiPolicy = WifiPolicy.this;
                                wifiPolicy.setWifiStateChangeAllowedSystemUI(
                                        intExtra2, wifiPolicy.isWifiStateChangeAllowed(null));
                                wifiPolicy.setWifiAllowedSystemUI(
                                        intExtra2, wifiPolicy.isWifiAllowed(null, false));
                                return;
                            }
                            if (action.equals("android.net.wifi.CONFIGURED_NETWORKS_CHANGE")) {
                                int intExtra3 = intent.getIntExtra("changeReason", -1);
                                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                        intExtra3, "reason = ", "WifiPolicyService");
                                if (intExtra3 == 1) {
                                    new Thread(
                                                    new Runnable() { // from class:
                                                        // com.android.server.enterprise.wifi.WifiPolicy.1.1
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            WifiPolicy wifiPolicy2 =
                                                                    WifiPolicy.this;
                                                            String[] strArr =
                                                                    WifiPolicy.EAP_STRINGS;
                                                            wifiPolicy2.getClass();
                                                            Log.i(
                                                                    "WifiPolicyService",
                                                                    "evaluateNetworkFromDatabase -"
                                                                        + " START");
                                                            Map allNetworksByAdminUid2 =
                                                                    wifiPolicy2
                                                                            .getAllNetworksByAdminUid();
                                                            if (allNetworksByAdminUid2 != null) {
                                                                ArrayMap arrayMap =
                                                                        (ArrayMap)
                                                                                allNetworksByAdminUid2;
                                                                if (!arrayMap.isEmpty()) {
                                                                    Log.i(
                                                                            "WifiPolicyService",
                                                                            "evaluateNetworkFromDatabase"
                                                                                + " - network map"
                                                                                + " size: "
                                                                                    + arrayMap
                                                                                            .size());
                                                                    for (Map.Entry entry :
                                                                            arrayMap.entrySet()) {
                                                                        if (entry != null
                                                                                && entry.getKey()
                                                                                        != null
                                                                                && ((Integer)
                                                                                                        entry
                                                                                                                .getKey())
                                                                                                .intValue()
                                                                                        != -1
                                                                                && entry.getValue()
                                                                                        != null
                                                                                && !((List)
                                                                                                entry
                                                                                                        .getValue())
                                                                                        .isEmpty()) {
                                                                            for (WifiConfiguration
                                                                                    wifiConfiguration :
                                                                                            (List)
                                                                                                    entry
                                                                                                            .getValue()) {
                                                                                if (wifiConfiguration
                                                                                                != null
                                                                                        && wifiPolicy2
                                                                                                        .getNetworkFromWifiModule(
                                                                                                                wifiConfiguration
                                                                                                                        .SSID,
                                                                                                                WifiPolicy
                                                                                                                        .makeString(
                                                                                                                                wifiConfiguration
                                                                                                                                        .allowedKeyManagement,
                                                                                                                                WifiConfiguration
                                                                                                                                        .KeyMgmt
                                                                                                                                        .strings))
                                                                                                == null) {
                                                                                    Log.i(
                                                                                            "WifiPolicyService",
                                                                                            "SSID from"
                                                                                                + " admin"
                                                                                                + " "
                                                                                                    + entry
                                                                                                            .getKey()
                                                                                                    + " does"
                                                                                                    + " not exist"
                                                                                                    + " anymore"
                                                                                                    + " on Wi-Fi"
                                                                                                    + " module");
                                                                                    wifiPolicy2
                                                                                            .removeNetworkConfigurationMDM(
                                                                                                    ((Integer)
                                                                                                                    entry
                                                                                                                            .getKey())
                                                                                                            .intValue(),
                                                                                                    wifiConfiguration
                                                                                                            .SSID);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    Log.i(
                                                                            "WifiPolicyService",
                                                                            "evaluateNetworkFromDatabase"
                                                                                + " - END");
                                                                    return;
                                                                }
                                                            }
                                                            Log.w(
                                                                    "WifiPolicyService",
                                                                    "Network list is null or"
                                                                        + " empty");
                                                        }
                                                    })
                                            .start();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        String[] strArr = WifiPolicy.EAP_STRINGS;
                        WifiPolicy wifiPolicy2 = WifiPolicy.this;
                        wifiPolicy2.getClass();
                        Log.i("WifiPolicyService", "migrateWifiNetworkIfNeeded");
                        try {
                            if (!wifiPolicy2.isWifiMigrationNeeded("wifi_network_migration")) {
                                Log.i(
                                        "WifiPolicyService",
                                        "Wi-Fi network migration is not needed, skipping...");
                                return;
                            }
                            try {
                                allNetworksByAdminUid = wifiPolicy2.getAllNetworksByAdminUid();
                            } catch (Exception e) {
                                Log.e("WifiPolicyService", "migrateWifiNetworkIfNeeded", e);
                            }
                            if (allNetworksByAdminUid != null) {
                                ArrayMap arrayMap = (ArrayMap) allNetworksByAdminUid;
                                if (!arrayMap.isEmpty()) {
                                    Log.i(
                                            "WifiPolicyService",
                                            "migrateWifiNetworkIfNeeded - network map size: "
                                                    + arrayMap.size());
                                    for (Map.Entry entry : arrayMap.entrySet()) {
                                        if (entry != null
                                                && entry.getKey() != null
                                                && ((Integer) entry.getKey()).intValue() != -1
                                                && entry.getValue() != null
                                                && !((List) entry.getValue()).isEmpty()) {
                                            Pair callerInfoFromUid =
                                                    wifiPolicy2.getCallerInfoFromUid(
                                                            ((Integer) entry.getKey()).intValue());
                                            if (callerInfoFromUid == null) {
                                                Log.e(
                                                        "WifiPolicyService",
                                                        "Could not get network creator information"
                                                            + " for "
                                                                + entry.getKey()
                                                                + " uid");
                                            } else {
                                                int intValue =
                                                        ((Integer) callerInfoFromUid.first)
                                                                .intValue();
                                                String packageName =
                                                        ((ComponentName) callerInfoFromUid.second)
                                                                .getPackageName();
                                                DevicePolicyManager devicePolicyManager =
                                                        (DevicePolicyManager)
                                                                wifiPolicy2.mContext
                                                                        .getSystemService(
                                                                                "device_policy");
                                                if (devicePolicyManager == null
                                                        || (!devicePolicyManager.isDeviceOwnerApp(
                                                                        packageName)
                                                                && UserHandle.getUserId(intValue)
                                                                        == 0)) {
                                                    Log.i(
                                                            "WifiPolicyService",
                                                            "Do not migrate networks from Device"
                                                                + " Admin - uid = "
                                                                    + callerInfoFromUid.first);
                                                } else {
                                                    int i = 0;
                                                    for (WifiConfiguration wifiConfiguration :
                                                            (List) entry.getValue()) {
                                                        if (wifiConfiguration != null) {
                                                            WifiConfiguration
                                                                    networkFromWifiModule =
                                                                            wifiPolicy2
                                                                                    .getNetworkFromWifiModule(
                                                                                            wifiConfiguration
                                                                                                    .SSID,
                                                                                            WifiPolicy
                                                                                                    .makeString(
                                                                                                            wifiConfiguration
                                                                                                                    .allowedKeyManagement,
                                                                                                            WifiConfiguration
                                                                                                                    .KeyMgmt
                                                                                                                    .strings));
                                                            if (networkFromWifiModule != null) {
                                                                StringBuilder sb =
                                                                        new StringBuilder();
                                                                sb.append("Migrating network ");
                                                                i++;
                                                                sb.append(i);
                                                                sb.append(" from uid ");
                                                                sb.append(callerInfoFromUid.first);
                                                                Log.i(
                                                                        "WifiPolicyService",
                                                                        sb.toString());
                                                                WifiManagerAdapter
                                                                        wifiManagerAdapter =
                                                                                wifiPolicy2
                                                                                        .mWifiAdapter;
                                                                int intValue2 =
                                                                        ((Integer)
                                                                                        callerInfoFromUid
                                                                                                .first)
                                                                                .intValue();
                                                                String packageName2 =
                                                                        ((ComponentName)
                                                                                        callerInfoFromUid
                                                                                                .second)
                                                                                .getPackageName();
                                                                wifiManagerAdapter.getClass();
                                                                WifiManagerAdapter.save(
                                                                        networkFromWifiModule,
                                                                        intValue2,
                                                                        packageName2);
                                                            } else {
                                                                Log.e(
                                                                        "WifiPolicyService",
                                                                        "Could not find wifi"
                                                                            + " network from uid "
                                                                                + callerInfoFromUid
                                                                                        .first);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    wifiPolicy2.updateWifiMigrationFlag("wifi_network_migration");
                                }
                            }
                            Log.w(
                                    "WifiPolicyService",
                                    "Network list is null or empty, network migration is not"
                                        + " required");
                            wifiPolicy2.updateWifiMigrationFlag("wifi_network_migration");
                        } catch (Throwable th) {
                            wifiPolicy2.updateWifiMigrationFlag("wifi_network_migration");
                            throw th;
                        }
                    }
                };
        this.mContext = context;
        context.getPackageManager();
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mAppPolicy =
                (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction(
                "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        this.mLocalProxyManager = LocalProxyManager.getInstance(context);
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    public static int computeprefixLength(InetAddress inetAddress) {
        int i = 0;
        for (byte b : inetAddress.getAddress()) {
            for (int i2 = 0; i2 < 8; i2++) {
                if (((1 << i2) & b) != 0) {
                    i++;
                }
            }
        }
        return i;
    }

    public static String convertArrayToString(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : strArr) {
            stringBuffer.append(str + ",");
        }
        stringBuffer.setLength(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    public static String[] convertStringToArray(String str) {
        return (str == null || str.length() == 0) ? new String[0] : str.split(",");
    }

    public static String convertToQuotedString(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length > 1 && str.charAt(0) == '\"' && str.charAt(length - 1) == '\"') {
            return str;
        }
        return "\"" + str + '\"';
    }

    public static ProxyProperties createProxyProperties(
            String str, int i, String str2, List list, List list2, int i2) {
        ProxyProperties proxyProperties = new ProxyProperties();
        if (i2 == 1) {
            proxyProperties.setHostname(str);
            proxyProperties.setPortNumber(i);
            proxyProperties.setAuthConfigList(list);
            proxyProperties.setExclusionList(list2);
            return proxyProperties;
        }
        if (i2 != 2) {
            return null;
        }
        proxyProperties.setPacFileUrl(str2);
        proxyProperties.setAuthConfigList(list);
        proxyProperties.setExclusionList(list2);
        return proxyProperties;
    }

    public static StaticIpConfiguration createStaticIpConfigurationFromSourceAndGateway(
            StaticIpConfiguration staticIpConfiguration, InetAddress inetAddress) {
        StaticIpConfiguration.Builder domains =
                new StaticIpConfiguration.Builder()
                        .setIpAddress(staticIpConfiguration.getIpAddress())
                        .setGateway(inetAddress)
                        .setDomains(staticIpConfiguration.getDomains());
        if (staticIpConfiguration.getDnsServers() != null) {
            domains.setDnsServers(staticIpConfiguration.getDnsServers());
        }
        return domains.build();
    }

    public static StaticIpConfiguration createStaticIpConfigurationFromSourceAndIpAddress(
            StaticIpConfiguration staticIpConfiguration, LinkAddress linkAddress) {
        StaticIpConfiguration.Builder domains =
                new StaticIpConfiguration.Builder()
                        .setIpAddress(linkAddress)
                        .setGateway(staticIpConfiguration.getGateway())
                        .setDomains(staticIpConfiguration.getDomains());
        if (staticIpConfiguration.getDnsServers() != null) {
            domains.setDnsServers(staticIpConfiguration.getDnsServers());
        }
        return domains.build();
    }

    public static int getSecurityLevel(int i) {
        if (i != 19) {
            if (i != 22 && i != 25 && i != 28) {
                if (i == 31) {
                    return 4;
                }
                switch (i) {
                    case 1:
                        return 2;
                    case 2:
                        return 3;
                    case 3:
                        break;
                    case 4:
                    case 5:
                        return 6;
                    case 6:
                    case 7:
                        break;
                    default:
                        return 1;
                }
            }
            return 7;
        }
        return 5;
    }

    public static String getValidStr$3(String str) {
        if (str != null && str.trim().length() > 0) {
            return str;
        }
        return null;
    }

    public static String makeString(BitSet bitSet, String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        BitSet bitSet2 = bitSet.get(0, strArr.length);
        int i = -1;
        while (true) {
            i = bitSet2.nextSetBit(i + 1);
            if (i == -1) {
                break;
            }
            stringBuffer.append(strArr[i].replace('_', '-'));
            stringBuffer.append(' ');
        }
        if (bitSet2.cardinality() > 0) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static boolean setDns1AndDns2(
            StaticIpConfiguration staticIpConfiguration, String str, String str2) {
        InetAddress parseNumericAddress;
        if (TextUtils.isEmpty(str)) {
            parseNumericAddress = InetAddresses.parseNumericAddress("8.8.8.8");
            if (!staticIpConfiguration.getDnsServers().isEmpty()) {
                parseNumericAddress = staticIpConfiguration.getDnsServers().get(0);
            }
        } else {
            try {
                parseNumericAddress = InetAddresses.parseNumericAddress(str);
            } catch (IllegalArgumentException e) {
                Log.e("WifiPolicyService", "", e);
                return false;
            }
        }
        if (parseNumericAddress != null) {
            if (staticIpConfiguration.getDnsServers().isEmpty()) {
                staticIpConfiguration.getDnsServers().add(parseNumericAddress);
            } else {
                staticIpConfiguration.getDnsServers().set(0, parseNumericAddress);
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            try {
                InetAddress parseNumericAddress2 = InetAddresses.parseNumericAddress(str2);
                if (staticIpConfiguration.getDnsServers().size() == 1) {
                    staticIpConfiguration.getDnsServers().add(parseNumericAddress2);
                } else if (staticIpConfiguration.getDnsServers().size() == 2) {
                    staticIpConfiguration.getDnsServers().set(1, parseNumericAddress2);
                }
            } catch (IllegalArgumentException e2) {
                Log.e("WifiPolicyService", "", e2);
                return false;
            }
        }
        return true;
    }

    public static StaticIpConfiguration setGateway(
            StaticIpConfiguration staticIpConfiguration, String str) {
        if (TextUtils.isEmpty(str)) {
            return staticIpConfiguration.getGateway() == null
                    ? createStaticIpConfigurationFromSourceAndGateway(
                            staticIpConfiguration, InetAddresses.parseNumericAddress("192.168.1.1"))
                    : staticIpConfiguration;
        }
        try {
            return createStaticIpConfigurationFromSourceAndGateway(
                    staticIpConfiguration, InetAddresses.parseNumericAddress(str));
        } catch (IllegalArgumentException e) {
            Log.e("WifiPolicyService", "", e);
            return null;
        }
    }

    public static StaticIpConfiguration setIpAndSubnetMask(
            StaticIpConfiguration staticIpConfiguration, String str, String str2) {
        int i = 24;
        if (TextUtils.isEmpty(str)) {
            InetAddress parseNumericAddress = InetAddresses.parseNumericAddress("192.168.1.100");
            if (staticIpConfiguration.getIpAddress() != null) {
                parseNumericAddress = staticIpConfiguration.getIpAddress().getAddress();
                i = staticIpConfiguration.getIpAddress().getPrefixLength();
            }
            if (str2 != null) {
                i = computeprefixLength(InetAddresses.parseNumericAddress(str2));
            }
            return createStaticIpConfigurationFromSourceAndIpAddress(
                    staticIpConfiguration, new LinkAddress(parseNumericAddress, i));
        }
        try {
            InetAddress parseNumericAddress2 = InetAddresses.parseNumericAddress(str);
            if (!TextUtils.isEmpty(str2)) {
                i = computeprefixLength(InetAddresses.parseNumericAddress(str2));
            } else if (staticIpConfiguration.getIpAddress() != null) {
                i = staticIpConfiguration.getIpAddress().getPrefixLength();
            }
            return createStaticIpConfigurationFromSourceAndIpAddress(
                    staticIpConfiguration, new LinkAddress(parseNumericAddress2, i));
        } catch (IllegalArgumentException e) {
            Log.e("WifiPolicyService", "", e);
            return null;
        }
    }

    public static void setMinimumRequiredWifiSecurityLevel(
            ComponentName componentName, int i, int i2) {
        if (componentName == null) {
            Log.e(
                    "WifiPolicyService",
                    "setMinimumRequiredWifiSecurityLevel - component name is null");
            return;
        }
        int i3 = 0;
        if (i2 != 0) {
            if (i2 != 1 && i2 != 2) {
                if (i2 == 5 || i2 == 6 || i2 == 7 || i2 == 19 || i2 == 22 || i2 == 25 || i2 == 28) {
                    i3 = 2;
                } else if (i2 != 31) {
                    Log.d("WifiPolicyService", "security level not mapped! Set as open");
                }
            }
            i3 = 1;
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal =
                (DevicePolicyManagerInternal)
                        LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            Log.e(
                    "WifiPolicyService",
                    "setMinimumRequiredWifiSecurityLevel - fail to retrieve dpmi object");
            return;
        }
        devicePolicyManagerInternal.setMinimumRequiredWifiSecurityLevel(componentName, i3, i);
        StringBuilder sb = new StringBuilder("setMinimumRequiredWifiSecurityLevel - who: ");
        sb.append(componentName);
        sb.append(", userId: ");
        sb.append(i);
        sb.append(", securityLevel: ");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, i3, "WifiPolicyService");
    }

    public static WifiConfiguration updateEnterpriseFieldValue(
            WifiConfiguration wifiConfiguration, String str, String str2) {
        if (str2 == null || TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        if (str.equals("phase2")) {
            int i = 0;
            while (true) {
                if (i < 8) {
                    if (str2.equals(PHASE2_STRINGS[i])) {
                        wifiConfiguration.enterpriseConfig.setPhase2Method(i);
                        break;
                    }
                    i++;
                } else {
                    if (!str2.equalsIgnoreCase(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG)) {
                        return null;
                    }
                    wifiConfiguration.enterpriseConfig.setPhase2Method(0);
                }
            }
        } else if (str.equals("phase1")) {
            Log.e("WifiPolicyService", "PHASE1 is no longer supported");
        } else if (str.equals("identity")) {
            if (str2.length() > 200) {
                Log.w("WifiPolicyService", "Identity value excedes maximum length 200");
                return null;
            }
            wifiConfiguration.enterpriseConfig.setIdentity(str2);
        } else if (str.equals("anonymous_identity")) {
            wifiConfiguration.enterpriseConfig.setAnonymousIdentity(str2);
        } else if (str.equals("password")) {
            if (str2.length() > 200) {
                Log.w("WifiPolicyService", "Password excedes maximum length 200");
                return null;
            }
            wifiConfiguration.enterpriseConfig.setPassword(str2);
        } else if (str.equals("client_cert") || str.equals("key_id")) {
            wifiConfiguration.enterpriseConfig.setClientCertificateAlias(str2);
        } else {
            if (!str.equals("ca_cert")) {
                return null;
            }
            wifiConfiguration.enterpriseConfig.setCaCertificateAliases(new String[] {str2});
        }
        return wifiConfiguration;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:

       if (r3 != null) goto L17;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:

       r2 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0051, code lost:

       if (r3 != null) goto L17;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007d, code lost:

       if (r3 != null) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.net.wifi.WifiConfiguration updateNetworkAddress(
            android.net.wifi.WifiConfiguration r6, java.lang.String r7, java.lang.String r8) {
        /*
            java.lang.String r0 = r6.SSID
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            r1 = 0
            if (r0 != 0) goto L8a
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r2 = 0
            if (r0 == 0) goto L12
            r0 = r2
            goto L20
        L12:
            java.lang.String r0 = "(([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\.){3}([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            java.util.regex.Matcher r0 = r0.matcher(r7)
            boolean r0 = r0.matches()
        L20:
            if (r0 != 0) goto L23
            goto L8a
        L23:
            android.net.IpConfiguration r0 = r6.getIpConfiguration()
            android.net.StaticIpConfiguration r3 = r0.getStaticIpConfiguration()
            if (r3 != 0) goto L32
            android.net.StaticIpConfiguration r3 = new android.net.StaticIpConfiguration
            r3.<init>()
        L32:
            java.lang.String r4 = "networkStaticIp"
            boolean r4 = r8.equals(r4)
            r5 = 1
            if (r4 == 0) goto L44
            android.net.StaticIpConfiguration r3 = setIpAndSubnetMask(r3, r7, r1)
            if (r3 == 0) goto L80
        L42:
            r2 = r5
            goto L80
        L44:
            java.lang.String r4 = "networkStaticGateway"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L54
            android.net.StaticIpConfiguration r3 = setGateway(r3, r7)
            if (r3 == 0) goto L80
            goto L42
        L54:
            java.lang.String r4 = "networkStaticPrimaryDns"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L62
            boolean r2 = setDns1AndDns2(r3, r7, r1)
            goto L80
        L62:
            java.lang.String r4 = "networkStaticSecondaryDns"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L70
            boolean r2 = setDns1AndDns2(r3, r1, r7)
            goto L80
        L70:
            java.lang.String r4 = "networkStaticSubnetMask"
            boolean r8 = r8.equals(r4)
            if (r8 == 0) goto L80
            android.net.StaticIpConfiguration r3 = setIpAndSubnetMask(r3, r1, r7)
            if (r3 == 0) goto L80
            goto L42
        L80:
            if (r2 != 0) goto L83
            return r1
        L83:
            r0.setStaticIpConfiguration(r3)
            r6.setIpConfiguration(r0)
            return r6
        L8a:
            java.lang.String r6 = "Invalid ip address provided: "
            java.lang.String r8 = "WifiPolicyService"
            com.android.server.StorageManagerService$$ExternalSyntheticOutline0.m(r6, r7, r8)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.updateNetworkAddress(android.net.wifi.WifiConfiguration,"
                    + " java.lang.String, java.lang.String):android.net.wifi.WifiConfiguration");
    }

    public static void updateNetworkProxyInfo(
            WifiConfiguration wifiConfiguration, ProxyInfo proxyInfo) {
        IpConfiguration ipConfiguration = wifiConfiguration.getIpConfiguration();
        if (ipConfiguration == null) {
            ipConfiguration = new IpConfiguration();
        }
        ipConfiguration.setHttpProxy(proxyInfo);
        wifiConfiguration.setIpConfiguration(ipConfiguration);
    }

    public static WifiConfiguration updateNetworkWEPKey(
            WifiConfiguration wifiConfiguration, int i, String str) {
        if (TextUtils.isEmpty(wifiConfiguration.SSID) || str == null || i < 0 || i > 3) {
            return null;
        }
        if (i == 0 && str.isEmpty()) {
            return null;
        }
        int length = str.length();
        if ((length == 10 || length == 26 || length == 58) && str.matches("[0-9A-Fa-f]*")) {
            wifiConfiguration.wepKeys[i] = str;
        } else {
            wifiConfiguration.wepKeys[i] = convertToQuotedString(str);
        }
        return wifiConfiguration;
    }

    public static List validateSSIDList(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (list.removeAll(Collections.singleton(null))) {
            Log.i("WifiPolicyService", "validateSSIDList() : ssid list removed null ");
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str == null || TextUtils.isEmpty(str)) {
                Log.i("WifiPolicyService", "isvalidSSIDName() : SSID is invalid");
            } else {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (callerInfoFromUid == null) {
            Log.e(
                    "WifiPolicyService",
                    "activateWifiSsidRestriction - not a valid caller, aborting!");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "WIFI",
                        enforceOwnerOnlyAndWifiPermission.mCallerUid,
                        z,
                        0,
                        "wifiSsidRestriction");
        if (!putBoolean) {
            Log.e(
                    "WifiPolicyService",
                    "activateWifiSsidRestriction - fail to store value to database");
            return putBoolean;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                    68,
                    new Object[] {
                        Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                        Boolean.valueOf(z)
                    });
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i(
                    "WifiPolicyService",
                    "activateWifiSsidRestriction - caller uid: "
                            + callerInfoFromUid.first
                            + ", enforced caller uid: "
                            + enforceOwnerOnlyAndWifiPermission.mCallerUid
                            + ", enable = "
                            + z);
            return setSsidAllowDenyList(
                    (ComponentName) callerInfoFromUid.second,
                    UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()),
                    enforceOwnerOnlyAndWifiPermission.mCallerUid,
                    z);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int addNetworkWithRandomizationState(
            WifiConfiguration wifiConfiguration, boolean z) {
        if (wifiConfiguration == null) {
            Log.i("WifiPolicyService", "addNetworkWithRandomizationState() : config is null");
            return -1;
        }
        this.mContext.enforceCallingPermission(
                "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (z) {
            try {
                try {
                    wifiConfiguration.macRandomizationSetting = 0;
                } catch (Exception e) {
                    Log.e("WifiPolicyService", "addNetworkWithRandomizationState() failed", e);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        int addNetwork = this.mWifiManager.addNetwork(wifiConfiguration);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addNetwork;
    }

    public final boolean addWifiSsidToBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "addWifiSsidToBlackList - not a valid caller, aborting!");
            return false;
        }
        List validateSSIDList = validateSSIDList(list);
        if (validateSSIDList != null) {
            ArrayList arrayList = (ArrayList) validateSSIDList;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                boolean z2 = true;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    ContentValues m =
                            AccountManagerService$$ExternalSyntheticOutline0.m("WIFI_SSID", str);
                    KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                            enforceOwnerOnlyAndWifiPermission.mCallerUid,
                            m,
                            "adminUid",
                            "WIFI_LIST_TYPE",
                            "BLACKLIST");
                    z2 =
                            z2
                                    && this.mEdmStorageProvider.putValuesNoUpdate(
                                            "WIFI_SSID_BLACK_WHITE_LIST", m);
                    if (z2) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logEventAsUser(
                                    UserHandle.getUserId(
                                            enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                    62,
                                    new Object[] {
                                        Integer.valueOf(
                                                enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                        str
                                    });
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
                    return z2;
                }
                Log.i(
                        "WifiPolicyService",
                        "addWifiSsidToBlackList - caller uid: "
                                + callerInfoFromUid.first
                                + ", enforced caller uid: "
                                + enforceOwnerOnlyAndWifiPermission.mCallerUid
                                + ", list size: "
                                + arrayList.size());
                if (z2
                        && setSsidAllowDenyList(
                                (ComponentName) callerInfoFromUid.second,
                                UserHandle.getUserId(
                                        ((Integer) callerInfoFromUid.first).intValue()),
                                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                                true)) {
                    z = true;
                }
                return z;
            }
        }
        Log.e("WifiPolicyService", "addWifiSsidToBlackList - failed with invalid request");
        return false;
    }

    public final boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "addWifiSsidToWhiteList - not a valid caller, aborting!");
            return false;
        }
        List validateSSIDList = validateSSIDList(list);
        if (validateSSIDList != null) {
            ArrayList arrayList = (ArrayList) validateSSIDList;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                boolean z2 = true;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    ContentValues m =
                            AccountManagerService$$ExternalSyntheticOutline0.m("WIFI_SSID", str);
                    KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                            enforceOwnerOnlyAndWifiPermission.mCallerUid,
                            m,
                            "adminUid",
                            "WIFI_LIST_TYPE",
                            "WHITELIST");
                    z2 =
                            z2
                                    && this.mEdmStorageProvider.putValuesNoUpdate(
                                            "WIFI_SSID_BLACK_WHITE_LIST", m);
                    if (z2) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logEventAsUser(
                                    UserHandle.getUserId(
                                            enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                    65,
                                    new Object[] {
                                        Integer.valueOf(
                                                enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                        str
                                    });
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
                    return z2;
                }
                Log.i(
                        "WifiPolicyService",
                        "addWifiSsidToWhiteList - caller uid: "
                                + callerInfoFromUid.first
                                + ", enforced caller uid: "
                                + enforceOwnerOnlyAndWifiPermission.mCallerUid
                                + ", list size: "
                                + arrayList.size());
                if (z2
                        && setSsidAllowDenyList(
                                (ComponentName) callerInfoFromUid.second,
                                UserHandle.getUserId(
                                        ((Integer) callerInfoFromUid.first).intValue()),
                                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                                true)) {
                    z = true;
                }
                return z;
            }
        }
        Log.e("WifiPolicyService", "addWifiSsidToWhiteList - failed with invalid request");
        return false;
    }

    public final boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) {
        int wifiApState;
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "allowOpenWifiAp - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", enable: "
                        + z);
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WifiConfiguration wifiApConfiguration = this.mWifiManager.getWifiApConfiguration();
                if (wifiApConfiguration != null
                        && wifiApConfiguration.allowedKeyManagement.get(0)
                        && ((wifiApState = this.mWifiManager.getWifiApState()) == 13
                                || wifiApState == 12)) {
                    SemWifiManager semWifiManager =
                            (SemWifiManager) this.mContext.getSystemService("sem_wifi");
                    semWifiManager.setWifiApEnabled(semWifiManager.getSoftApConfiguration(), false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return this.mEdmStorageProvider.putBoolean(
                "WIFI", enforceOwnerOnlyAndWifiPermission.mCallerUid, z, 0, "allowOpenWifi");
    }

    public final boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "allowWifiApSettingUserModification - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", enable: "
                        + z);
        return this.mEdmStorageProvider.putBoolean(
                "WIFI",
                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                z,
                0,
                "allowWifiApSettingModification");
    }

    public final boolean allowWifiScanning(ContextInfo contextInfo, boolean z) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        ContextInfo enforceOwnerOnlyAndActiveAdminPermission =
                this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.sec.enterprise.knox.permission.KNOX_RESTRICTION",
                                        "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "WIFI",
                        enforceOwnerOnlyAndActiveAdminPermission.mCallerUid,
                        z,
                        0,
                        "allowWifiScanning");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                Settings.Global.putInt(contentResolver, "wifi_scan_always_enabled", 0);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Log.d("WifiPolicyService", "allowWifiScanning - " + z);
        return putBoolean;
    }

    public final boolean clearWifiSsidBlackList(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "clearWifiSsidBlackList - not a valid caller, aborting!");
            return false;
        }
        boolean deleteDataByFields =
                this.mEdmStorageProvider.deleteDataByFields(
                        "WIFI_SSID_BLACK_WHITE_LIST",
                        new String[] {"adminUid", "WIFI_LIST_TYPE"},
                        new String[] {
                            String.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                            "BLACKLIST"
                        });
        if (deleteDataByFields) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logEventAsUser(
                        UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                        64,
                        new Object[] {
                            Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid)
                        });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return deleteDataByFields;
        }
        StringBuilder sb = new StringBuilder("clearWifiSsidBlackList - caller uid: ");
        sb.append(callerInfoFromUid.first);
        sb.append(", enforced caller uid: ");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                sb, enforceOwnerOnlyAndWifiPermission.mCallerUid, "WifiPolicyService");
        if (deleteDataByFields
                && setSsidAllowDenyList(
                        (ComponentName) callerInfoFromUid.second,
                        UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()),
                        enforceOwnerOnlyAndWifiPermission.mCallerUid,
                        true)) {
            z = true;
        }
        return z;
    }

    public final boolean clearWifiSsidWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "clearWifiSsidWhiteList - not a valid caller, aborting!");
            return false;
        }
        boolean deleteDataByFields =
                this.mEdmStorageProvider.deleteDataByFields(
                        "WIFI_SSID_BLACK_WHITE_LIST",
                        new String[] {"adminUid", "WIFI_LIST_TYPE"},
                        new String[] {
                            String.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                            "WHITELIST"
                        });
        if (deleteDataByFields) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logEventAsUser(
                        UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                        67,
                        new Object[] {
                            Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid)
                        });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return deleteDataByFields;
        }
        StringBuilder sb = new StringBuilder("clearWifiSsidWhiteList - caller uid: ");
        sb.append(callerInfoFromUid.first);
        sb.append(", enforced caller uid: ");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                sb, enforceOwnerOnlyAndWifiPermission.mCallerUid, "WifiPolicyService");
        if (deleteDataByFields
                && setSsidAllowDenyList(
                        (ComponentName) callerInfoFromUid.second,
                        UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()),
                        enforceOwnerOnlyAndWifiPermission.mCallerUid,
                        true)) {
            z = true;
        }
        return z;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ArrayList arrayList;
        ArrayList arrayList2;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump WifiPolicyService");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[WifiPolicyService]" + System.lineSeparator());
        StringBuilder sb2 = new StringBuilder("  mWifiAllowed : ");
        sb2.append(isWifiAllowed(null, false));
        sb2.append(System.lineSeparator());
        sb.append(sb2.toString());
        sb.append(
                "  mAllowStateChange : " + isWifiStateChangeAllowed(null) + System.lineSeparator());
        sb.append(
                "  mAllowAutomaticConnections : "
                        + getAutomaticConnectionToWifi(null)
                        + System.lineSeparator());
        sb.append(
                "  mAllowUserChanges : "
                        + getAllowUserPolicyChanges(null)
                        + System.lineSeparator());
        sb.append(
                "  mPromptCredentialsEnabled : "
                        + getPromptCredentialsEnabled(null)
                        + System.lineSeparator());
        sb.append(
                "  mAllowUserProfiles : "
                        + getAllowUserProfiles(null, false, 0)
                        + System.lineSeparator());
        StringBuilder sb3 = new StringBuilder("  ssidBlockList : ");
        StringBuilder sb4 = new StringBuilder();
        TreeSet treeSet =
                new TreeSet(
                        this.mEdmStorageProvider.getIntListAsUser(
                                0, 0, "WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            arrayList = new ArrayList();
        } else {
            HashMap hashMap = new HashMap(treeSet.size());
            Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), true));
            }
            ArrayList arrayList3 = new ArrayList(hashMap.keySet().size());
            for (Map.Entry entry : hashMap.entrySet()) {
                WifiControlInfo wifiControlInfo = new WifiControlInfo();
                wifiControlInfo.adminPackageName =
                        this.mEdmStorageProvider.getPackageNameForUid(
                                ((Integer) entry.getKey()).intValue());
                wifiControlInfo.entries =
                        new ArrayList((Collection) ((Map) entry.getValue()).get("BLACKLIST"));
                arrayList3.add(wifiControlInfo);
            }
            arrayList = arrayList3;
        }
        if (arrayList.size() == 0) {
            sb4.append("No item found");
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                sb4.append(
                        ((WifiControlInfo) arrayList.get(i)).adminPackageName
                                + " : "
                                + ((WifiControlInfo) arrayList.get(i)).entries
                                + "  ");
            }
        }
        sb3.append(sb4.toString());
        sb3.append(System.lineSeparator());
        sb.append(sb3.toString());
        StringBuilder sb5 = new StringBuilder("  ssidAllowList : ");
        StringBuilder sb6 = new StringBuilder();
        TreeSet treeSet2 =
                new TreeSet(
                        this.mEdmStorageProvider.getIntListAsUser(
                                0, 0, "WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet2.isEmpty()) {
            arrayList2 = new ArrayList();
        } else {
            HashMap hashMap2 = new HashMap(treeSet2.size());
            Iterator it2 = treeSet2.iterator();
            while (it2.hasNext()) {
                Integer num2 = (Integer) it2.next();
                hashMap2.put(num2, loadWifiNetworkSsidBlackWhitelist(num2.intValue(), false));
            }
            ArrayList arrayList4 = new ArrayList(hashMap2.keySet().size());
            for (Map.Entry entry2 : hashMap2.entrySet()) {
                WifiControlInfo wifiControlInfo2 = new WifiControlInfo();
                wifiControlInfo2.adminPackageName =
                        this.mEdmStorageProvider.getPackageNameForUid(
                                ((Integer) entry2.getKey()).intValue());
                wifiControlInfo2.entries =
                        new ArrayList((Collection) ((Map) entry2.getValue()).get("WHITELIST"));
                arrayList4.add(wifiControlInfo2);
            }
            arrayList2 = arrayList4;
        }
        if (arrayList2.size() == 0) {
            sb6.append("No item found");
        } else {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                sb6.append(
                        ((WifiControlInfo) arrayList2.get(i2)).adminPackageName
                                + " : "
                                + ((WifiControlInfo) arrayList2.get(i2)).entries
                                + "  ");
            }
        }
        sb5.append(sb6.toString());
        sb5.append(System.lineSeparator());
        sb.append(sb5.toString());
        printWriter.write(sb.toString());
    }

    public final void enableNetworkAndMaybeConnect(int i) {
        WifiManager wifiManager;
        if (-1 == i
                || (wifiManager = this.mWifiManager) == null
                || wifiManager.getWifiState() != 3) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWifiAdapter.getClass();
            try {
                WifiManagerAdapter.mWifiManager.enableNetwork(i, false);
            } catch (Exception e) {
                Log.e("WifiManagerAdapter", "Failed to enable network " + e.getMessage());
            }
            if (!((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class))
                    .getNetworkInfo(1)
                    .isConnected()) {
                this.mWifiAdapter.getClass();
                WifiManagerAdapter.connect(i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ContextInfo enforceOwnerOnlyAndWifiPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_WIFI")));
    }

    public final void evaluateAndMigrateDPMAPIs(Cursor cursor) {
        Log.i("WifiPolicyService", "evaluateAndMigrateDPMAPIs - START");
        try {
            try {
                cursor.moveToFirst();
                do {
                    int i = cursor.getInt(cursor.getColumnIndex("adminUid"));
                    Pair callerInfoFromUid = getCallerInfoFromUid(i);
                    if (callerInfoFromUid != null) {
                        int i2 = cursor.getInt(cursor.getColumnIndex("minimumRequiredSecurity"));
                        if (i2 != 0) {
                            setMinimumRequiredWifiSecurityLevel(
                                    (ComponentName) callerInfoFromUid.second,
                                    UserHandle.getUserId(
                                            ((Integer) callerInfoFromUid.first).intValue()),
                                    i2);
                        }
                        if (cursor.getInt(cursor.getColumnIndex("wifiSsidRestriction")) == 1) {
                            setSsidAllowDenyList(
                                    (ComponentName) callerInfoFromUid.second,
                                    UserHandle.getUserId(
                                            ((Integer) callerInfoFromUid.first).intValue()),
                                    i,
                                    true);
                        }
                    }
                } while (cursor.moveToNext());
            } catch (Exception e) {
                Log.e("WifiPolicyService", "evaluateAndMigrateDPMAPIs", e);
            }
            Log.i("WifiPolicyService", "evaluateAndMigrateDPMAPIs - END");
        } catch (Throwable th) {
            Log.i("WifiPolicyService", "evaluateAndMigrateDPMAPIs - END");
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004c A[Catch: all -> 0x002e, Exception -> 0x0030, TryCatch #0 {Exception -> 0x0030, blocks: (B:3:0x000b, B:4:0x0014, B:6:0x0021, B:9:0x0033, B:12:0x0040, B:14:0x004c, B:15:0x0057, B:19:0x005f, B:21:0x0064, B:24:0x006b, B:26:0x0071), top: B:2:0x000b, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void evaluateAndMigrateUserRestrictionAPIs(android.database.Cursor r11) {
        /*
            r10 = this;
            java.lang.String r0 = "evaluateAndMigrateUserRestrictionAPIs - END"
            java.lang.String r1 = "WifiPolicyService"
            java.lang.String r2 = "evaluateAndMigrateUserRestrictionAPIs - START"
            android.util.Log.i(r1, r2)
            r11.moveToFirst()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r2 = -1
            r3 = 0
            r7 = r2
            r4 = r3
            r5 = r4
            r6 = r5
        L14:
            java.lang.String r8 = "allowWifi"
            int r8 = r11.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            int r8 = r11.getInt(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r9 = 1
            if (r8 == 0) goto L32
            java.lang.String r8 = "allowWifiStateChanges"
            int r8 = r11.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            int r8 = r11.getInt(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            if (r8 != 0) goto L33
            goto L32
        L2e:
            r10 = move-exception
            goto L90
        L30:
            r10 = move-exception
            goto L88
        L32:
            r4 = r9
        L33:
            java.lang.String r8 = "allowUserProfiles"
            int r8 = r11.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            int r8 = r11.getInt(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            if (r8 != 0) goto L40
            r5 = r9
        L40:
            java.lang.String r8 = "allowAutomaticConnection"
            int r8 = r11.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            int r8 = r11.getInt(r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            if (r8 != 0) goto L57
            java.lang.String r6 = "adminUid"
            int r6 = r11.getColumnIndex(r6)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            int r7 = r11.getInt(r6)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r6 = r9
        L57:
            boolean r8 = r11.moveToNext()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            if (r8 != 0) goto L14
            if (r4 == 0) goto L62
            r10.setChangeWifiStateUserRestriction(r9)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
        L62:
            if (r5 == 0) goto L67
            r10.setAddWifiConfigUserRestriction(r9)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
        L67:
            if (r6 == 0) goto L84
            if (r7 == r2) goto L84
            android.util.Pair r11 = r10.getCallerInfoFromUid(r7)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            if (r11 == 0) goto L84
            java.lang.Object r2 = r11.first     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            java.lang.Object r11 = r11.second     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            android.content.ComponentName r11 = (android.content.ComponentName) r11     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            java.lang.String r11 = r11.getPackageName()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r10.setAllowAutojoinGlobal(r2, r11, r3)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
        L84:
            android.util.Log.i(r1, r0)
            goto L8f
        L88:
            java.lang.String r11 = "evaluateAndMigrateUserRestrictionAPIs"
            android.util.Log.e(r1, r11, r10)     // Catch: java.lang.Throwable -> L2e
            goto L84
        L8f:
            return
        L90:
            android.util.Log.i(r1, r0)
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.evaluateAndMigrateUserRestrictionAPIs(android.database.Cursor):void");
    }

    public final Map getAllNetworksByAdminUid() {
        Integer asInteger;
        String str;
        String str2;
        String str3 = "adminUid";
        String str4 = "networkSSID";
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "WIFI_CONF",
                                new String[] {"adminUid", "networkSSID", "networkKeyMgmt"},
                                null);
        if (arrayList.isEmpty()) {
            Log.i("WifiPolicyService", "getAllNetworksByAdminUid - cv is empty or null");
            return null;
        }
        Log.i("WifiPolicyService", "getAllNetworksByAdminUid - cv entries: " + arrayList.size());
        ArrayMap arrayMap = new ArrayMap();
        int i = 0;
        while (i < arrayList.size()) {
            ContentValues contentValues = (ContentValues) arrayList.get(i);
            if (contentValues != null && (asInteger = contentValues.getAsInteger(str3)) != null) {
                String asString = contentValues.getAsString(str4);
                if (!TextUtils.isEmpty(asString)) {
                    String asString2 = contentValues.getAsString("networkKeyMgmt");
                    if (!TextUtils.isEmpty(asString2)) {
                        WifiConfiguration wifiConfiguration = new WifiConfiguration();
                        wifiConfiguration.SSID = asString;
                        String[] strArr = WifiConfiguration.KeyMgmt.strings;
                        BitSet bitSet = new BitSet();
                        if (!TextUtils.isEmpty(asString2)) {
                            String[] split = asString2.split(" ");
                            int length = split.length;
                            int i2 = 0;
                            while (i2 < length) {
                                String str5 = split[i2];
                                int length2 = strArr.length;
                                String str6 = str3;
                                String str7 = str4;
                                String replace = str5.replace('-', '_');
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= length2) {
                                        i3 = -1;
                                        break;
                                    }
                                    if (replace.equals(strArr[i3])) {
                                        break;
                                    }
                                    i3++;
                                }
                                if (i3 >= 0) {
                                    bitSet.set(i3);
                                }
                                i2++;
                                str3 = str6;
                                str4 = str7;
                            }
                        }
                        str = str3;
                        str2 = str4;
                        wifiConfiguration.allowedKeyManagement = bitSet;
                        if (arrayMap.get(asInteger) == null) {
                            arrayMap.put(asInteger, new ArrayList());
                        }
                        Log.i(
                                "WifiPolicyService",
                                "getAllNetworksByAdminUid - adding network for admin: "
                                        + asInteger);
                        ((List) arrayMap.get(asInteger)).add(wifiConfiguration);
                        i++;
                        str3 = str;
                        str4 = str2;
                    }
                }
            }
            str = str3;
            str2 = str4;
            i++;
            str3 = str;
            str4 = str2;
        }
        return arrayMap;
    }

    public final List getAllWifiSsidBlackLists(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        TreeSet treeSet =
                new TreeSet(
                        this.mEdmStorageProvider.getIntListAsUser(
                                0, 0, "WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap(treeSet.size());
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), false));
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet().size());
        for (Map.Entry entry : hashMap.entrySet()) {
            WifiControlInfo wifiControlInfo = new WifiControlInfo();
            wifiControlInfo.adminPackageName =
                    this.mEdmStorageProvider.getPackageNameForUid(
                            ((Integer) entry.getKey()).intValue());
            wifiControlInfo.entries =
                    new ArrayList((Collection) ((Map) entry.getValue()).get("BLACKLIST"));
            arrayList.add(wifiControlInfo);
        }
        Log.i("WifiPolicyService", "getAllWifiSsidBlackLists: list size = " + arrayList.size());
        return arrayList;
    }

    public final List getAllWifiSsidWhiteLists(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        TreeSet treeSet =
                new TreeSet(
                        this.mEdmStorageProvider.getIntListAsUser(
                                0, 0, "WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap(treeSet.size());
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), false));
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet().size());
        for (Map.Entry entry : hashMap.entrySet()) {
            WifiControlInfo wifiControlInfo = new WifiControlInfo();
            wifiControlInfo.adminPackageName =
                    this.mEdmStorageProvider.getPackageNameForUid(
                            ((Integer) entry.getKey()).intValue());
            wifiControlInfo.entries =
                    new ArrayList((Collection) ((Map) entry.getValue()).get("WHITELIST"));
            arrayList.add(wifiControlInfo);
        }
        Log.i("WifiPolicyService", "getAllWifiSsidWhiteLists: list size = " + arrayList.size());
        return arrayList;
    }

    public final boolean getAllowUserPolicyChanges(ContextInfo contextInfo) {
        boolean z;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowUserChanges")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getAllowUserPolicyChanges: ", "WifiPolicyService", z);
        return z;
    }

    public final boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) {
        boolean z2;
        if (i == -1) {
            i = Utils.getCallingOrCurrentUserId(contextInfo);
        }
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(i, "WIFI", "allowUserProfiles")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        if (!z2 && z) {
            RestrictionToastManager.show(R.string.indeterminate_progress_02);
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getAllowUserProfiles: ", "WifiPolicyService", z2);
        return z2;
    }

    public final List getAuthConfigFromDb(String str) {
        ArrayList arrayList = new ArrayList();
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("networkSSID", str);
        Iterator it =
                ((ArrayList)
                                this.mEdmStorageProvider.getValues(
                                        "WifiProxyAuthTable",
                                        new String[] {"host", "port", "username", "password"},
                                        m))
                        .iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString("host");
            Integer asInteger = contentValues.getAsInteger("port");
            String asString2 = contentValues.getAsString("username");
            String asString3 = contentValues.getAsString("password");
            if (asInteger == null) {
                asInteger = Integer.valueOf(AuthConfig.ANY_PORT);
            }
            arrayList.add(new AuthConfig(asString, asInteger.intValue(), asString2, asString3));
        }
        return arrayList;
    }

    public final boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) {
        boolean z;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowAutomaticConnection")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getAutomaticConnectionToWifi: ", "WifiPolicyService", z);
        return z;
    }

    public final Set getBlockedList$1(int i) {
        String string = this.mEdmStorageProvider.getString(i, 0, "WIFI", "blockedSSIDList");
        HashSet hashSet = new HashSet();
        if (string != null) {
            for (String str : string.split(",")) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public final List getBlockedNetworks(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        LinkedList linkedList = new LinkedList();
        List stringListAsUser =
                this.mEdmStorageProvider.getStringListAsUser(0, "WIFI", "blockedSSIDList");
        HashSet hashSet = new HashSet();
        Iterator it = ((ArrayList) stringListAsUser).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!str.isEmpty()) {
                hashSet.addAll(Arrays.asList(str.split(",")));
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            linkedList.add(NetworkUtils.removeDoubleQuotes((String) it2.next()));
        }
        return linkedList;
    }

    public final Pair getCallerInfoFromUid(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "getCallerInfoFromUid START - parentUid = ", "WifiPolicyService");
        ArrayList dataByFields =
                this.mEdmStorageProvider.getDataByFields(
                        "ADMIN_INFO",
                        new String[] {"adminUid"},
                        new String[] {String.valueOf(i)},
                        new String[] {"adminName", "isPseudoAdmin"});
        if (dataByFields.isEmpty()) {
            Log.e("WifiPolicyService", "getCallerInfoFromUid - cv is empty or null");
            return null;
        }
        String asString = ((ContentValues) dataByFields.get(0)).getAsString("adminName");
        Boolean asBoolean = ((ContentValues) dataByFields.get(0)).getAsBoolean("isPseudoAdmin");
        if (asBoolean != null && asBoolean.booleanValue()) {
            ArrayList dataByFields2 =
                    this.mEdmStorageProvider.getDataByFields(
                            "ADMIN_INFO",
                            new String[] {"adminName", "isPseudoAdmin"},
                            new String[] {asString, "0"},
                            new String[] {"adminUid"});
            if (dataByFields2.isEmpty()) {
                Log.e("WifiPolicyService", "getWorkProfileUid - cv is empty or null");
                i = -1;
            } else {
                Integer asInteger = ((ContentValues) dataByFields2.get(0)).getAsInteger("adminUid");
                i = asInteger == null ? -1 : asInteger.intValue();
            }
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(asString);
        Log.i(
                "WifiPolicyService",
                "getCallerInfoFromUid END - profileUid = "
                        + i
                        + ", componentName = "
                        + unflattenFromString);
        if (unflattenFromString == null || i == -1) {
            return null;
        }
        return new Pair(Integer.valueOf(i), unflattenFromString);
    }

    public final int getMinimumRequiredSecurity(ContextInfo contextInfo) {
        int i = 0;
        Iterator it =
                this.mEdmStorageProvider
                        .getIntListAsUser(0, 0, "WIFI", "minimumRequiredSecurity")
                        .iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (getSecurityLevel(intValue) > getSecurityLevel(i)) {
                i = intValue;
            }
        }
        DirEncryptService$$ExternalSyntheticOutline0.m(
                i, "getMinimumRequiredSecurity: ", "WifiPolicyService");
        return i;
    }

    public final WifiConfiguration getNetworkConfiguration(int i, String str) {
        ArrayList dataByFields =
                this.mEdmStorageProvider.getDataByFields(
                        "WIFI_CONF",
                        new String[] {"adminUid", "networkSSID"},
                        new String[] {String.valueOf(i), NetworkUtils.removeDoubleQuotes(str)},
                        new String[] {
                            "networkSSID",
                            "networkKeyMgmt",
                            "networkProxyState",
                            "networProxyName",
                            "networkProxyPort",
                            "networkProxyExclList",
                            "networkProxyPacUrl"
                        });
        if (dataByFields.isEmpty()) {
            Log.i("WifiPolicyService", "getNetworkConfiguration - cv is empty or null");
            return null;
        }
        for (int i2 = 0; i2 < dataByFields.size(); i2++) {
            ContentValues contentValues = (ContentValues) dataByFields.get(i2);
            if (contentValues != null) {
                String asString = contentValues.getAsString("networkSSID");
                if (TextUtils.isEmpty(asString)) {
                    continue;
                } else {
                    String asString2 = contentValues.getAsString("networkKeyMgmt");
                    if (!TextUtils.isEmpty(asString2)) {
                        WifiConfiguration networkFromWifiModule =
                                getNetworkFromWifiModule(asString, asString2);
                        Integer asInteger = contentValues.getAsInteger("networkProxyState");
                        if (asInteger != null && networkFromWifiModule.getHttpProxy() != null) {
                            Integer asInteger2 = contentValues.getAsInteger("networkProxyPort");
                            networkFromWifiModule.setHttpProxy(
                                    NetworkUtils.convertToProxyInfo(
                                            createProxyProperties(
                                                    contentValues.getAsString("networProxyName"),
                                                    asInteger2 != null ? asInteger2.intValue() : 0,
                                                    contentValues.getAsString("networkProxyPacUrl"),
                                                    null,
                                                    Arrays.asList(
                                                            convertStringToArray(
                                                                    contentValues.getAsString(
                                                                            "networkProxyExclList"))),
                                                    asInteger.intValue())));
                        }
                        return networkFromWifiModule;
                    }
                }
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                i,
                "getNetworkConfiguration - network not found in database - callingUid: ",
                "WifiPolicyService");
        return null;
    }

    public final WifiConfiguration getNetworkFromWifiModule(String str, String str2) {
        WifiConfiguration wifiConfiguration = null;
        if (this.mWifiAdapter == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWifiAdapter.getClass();
            List configuredNetworks = WifiManagerAdapter.getConfiguredNetworks();
            if (configuredNetworks != null) {
                Iterator it = configuredNetworks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WifiConfiguration wifiConfiguration2 = (WifiConfiguration) it.next();
                    if (wifiConfiguration2.SSID != null
                            && !wifiConfiguration2.isEphemeral()
                            && !wifiConfiguration2.isPasspoint()
                            && wifiConfiguration2.SSID.equals(convertToQuotedString(str))
                            && !TextUtils.isEmpty(str2)
                            && str2.equals(
                                    makeString(
                                            wifiConfiguration2.allowedKeyManagement,
                                            WifiConfiguration.KeyMgmt.strings))) {
                        wifiConfiguration = wifiConfiguration2;
                        break;
                    }
                }
            }
            return wifiConfiguration;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getNetworkSSIDList(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields =
                this.mEdmStorageProvider.getDataByFields(
                        "WIFI_CONF", null, null, new String[] {"networkSSID"});
        for (int i = 0; i < dataByFields.size(); i++) {
            arrayList.add(((ContentValues) dataByFields.get(i)).getAsString("networkSSID"));
        }
        return arrayList;
    }

    public final boolean getPasswordHidden(ContextInfo contextInfo) {
        boolean z = false;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "passwordHidden")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                z = booleanValue;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getPasswordHidden: ", "WifiPolicyService", z);
        return z;
    }

    public final boolean getPromptCredentialsEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "promptCredentials")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getPromptCredentialsEnabled: ", "WifiPolicyService", z);
        return z;
    }

    public final WifiConfiguration getWifiApSetting(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWifiAdapter.getClass();
            SoftApConfiguration softApConfiguration =
                    WifiManagerAdapter.mSemWifiManager.getSoftApConfiguration();
            WifiConfiguration wifiConfiguration = null;
            if (softApConfiguration != null) {
                WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
                wifiConfiguration2.SSID = softApConfiguration.getSsid();
                int securityType = softApConfiguration.getSecurityType();
                if (securityType == 0) {
                    wifiConfiguration2.allowedKeyManagement.set(0);
                } else if (securityType == 1) {
                    wifiConfiguration2.allowedKeyManagement.set(4);
                } else {
                    if (securityType != 3) {
                        Log.e(
                                "WifiPolicyService",
                                "Convert fail, unsupported security type :"
                                        + softApConfiguration.getSecurityType());
                        return null;
                    }
                    wifiConfiguration2.allowedKeyManagement.set(8);
                }
                wifiConfiguration = wifiConfiguration2;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("WifiPolicyService", "getWifiApSetting");
            return wifiConfiguration;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:192:0x021f, code lost:

       if (r6 == 4) goto L76;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.wifi.WifiAdminProfile getWifiProfile(
            com.samsung.android.knox.ContextInfo r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 835
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.getWifiProfile(com.samsung.android.knox.ContextInfo,"
                    + " java.lang.String):com.samsung.android.knox.net.wifi.WifiAdminProfile");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleUpgradeUserRestrictionAPIs(android.database.Cursor r8) {
        /*
            r7 = this;
            java.lang.String r0 = "upgradeUserRestrictionAPIs - END"
            java.lang.String r1 = "upgradeUserRestrictionAPIs - START"
            java.lang.String r2 = "WifiPolicyService"
            android.util.Log.i(r2, r1)
            r8.moveToFirst()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r1 = 0
            r3 = r1
            r4 = r3
        L11:
            java.lang.String r5 = "allowWifi"
            int r5 = r8.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            int r5 = r8.getInt(r5)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r6 = 1
            if (r5 == 0) goto L2f
            java.lang.String r5 = "allowWifiStateChanges"
            int r5 = r8.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            int r5 = r8.getInt(r5)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            if (r5 != 0) goto L30
            goto L2f
        L2b:
            r7 = move-exception
            goto L69
        L2d:
            r7 = move-exception
            goto L61
        L2f:
            r3 = r6
        L30:
            java.lang.String r5 = "allowUserProfiles"
            int r5 = r8.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            int r5 = r8.getInt(r5)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            if (r5 != 0) goto L3d
            r4 = r6
        L3d:
            boolean r5 = r8.moveToNext()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            if (r5 != 0) goto L11
            if (r3 == 0) goto L50
            android.os.UserManager r8 = r7.mUserManager     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.String r3 = "no_change_wifi_state"
            r8.setUserRestriction(r3, r1)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r7.setChangeWifiStateUserRestriction(r6)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
        L50:
            if (r4 == 0) goto L5d
            android.os.UserManager r8 = r7.mUserManager     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.String r3 = "no_add_wifi_config"
            r8.setUserRestriction(r3, r1)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r7.setAddWifiConfigUserRestriction(r6)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
        L5d:
            android.util.Log.i(r2, r0)
            goto L68
        L61:
            java.lang.String r8 = "exception inside handleUpgradeUserRestrictionAPIs"
            android.util.Log.e(r2, r8, r7)     // Catch: java.lang.Throwable -> L2b
            goto L5d
        L68:
            return
        L69:
            android.util.Log.i(r2, r0)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.handleUpgradeUserRestrictionAPIs(android.database.Cursor):void");
    }

    public final boolean isOpenWifiApAllowed(ContextInfo contextInfo) {
        boolean z;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowOpenWifi")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isOpenWifiApAllowed: ", "WifiPolicyService", z);
        return z;
    }

    public final boolean isSecurityLevelSupported(String str) {
        if (str.equals("SAE") && !this.mWifiManager.isWpa3SaeSupported()) {
            Log.w("WifiPolicyService", "SAE is not supported on this device");
            return false;
        }
        String[] strArr = ACCEPTABLE_SECURITY_LEVELS;
        if (((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)) == null
                ? false
                : !r5.isDataCapable()) {
            strArr[22] = "";
            strArr[23] = "";
            strArr[25] = "";
            strArr[26] = "";
            strArr[28] = "";
            strArr[29] = "";
        }
        if (Arrays.asList(strArr).contains(str)) {
            return true;
        }
        Log.d("WifiPolicyService", "security level not supported");
        return false;
    }

    public final boolean isWifiAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Iterator it =
                this.mEdmStorageProvider.getBooleanListAsUser(0, "WIFI", "allowWifi").iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isWifiAllowed: ", "WifiPolicyService", z2);
        return z2;
    }

    public final boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) {
        boolean z = true;
        if (this.mIsAPSettingFromAdmin) {
            return true;
        }
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowWifiApSettingModification")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                z = booleanValue;
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isWifiApSettingUserModificationAllowed: ", "WifiPolicyService", z);
        return z;
    }

    public final boolean isWifiMigrationNeeded(String str) {
        Log.i("WifiPolicyService", "isWifiMigrationNeeded - type = ".concat(str));
        if (!"wifi_network_migration".equals(str) && !"wifi_policy_migration".equals(str)) {
            return false;
        }
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(0, str);
        if (genericValueAsUser == null || !"ok".equals(genericValueAsUser)) {
            Log.i("WifiPolicyService", "Migration is needed");
            return true;
        }
        Log.i("WifiPolicyService", "Migration is not needed");
        return false;
    }

    public final boolean isWifiScanningAllowed(ContextInfo contextInfo) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowWifiScanning")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final boolean isWifiSsidRestrictionActive(int i) {
        try {
            boolean z = this.mEdmStorageProvider.getBoolean(i, 0, "WIFI", "wifiSsidRestriction");
            Log.i("WifiPolicyService", "isWifiSsidRestrictionActive(" + i + "): " + z);
            return z;
        } catch (SettingNotFoundException e) {
            Log.e("WifiPolicyService", e.getMessage());
            return false;
        }
    }

    public final boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) {
        return isWifiSsidRestrictionActive(
                enforceOwnerOnlyAndWifiPermission(contextInfo).mCallerUid);
    }

    public final boolean isWifiStateChangeAllowed(ContextInfo contextInfo) {
        boolean z;
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowWifiStateChanges")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "isWifiStateChangeAllowed: ", "WifiPolicyService", z);
        return z;
    }

    public final synchronized Map loadWifiNetworkSsidBlackWhitelist(int i, boolean z) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            List valuesList =
                    this.mEdmStorageProvider.getValuesList(
                            "WIFI_SSID_BLACK_WHITE_LIST",
                            new String[] {"WIFI_LIST_TYPE", "WIFI_SSID"},
                            contentValues);
            TreeSet treeSet = new TreeSet();
            if (z) {
                Iterator it = ((HashSet) getBlockedList$1(i)).iterator();
                while (it.hasNext()) {
                    treeSet.add(NetworkUtils.removeDoubleQuotes((String) it.next()));
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("BLACKLIST", treeSet);
            hashMap.put("WHITELIST", new TreeSet());
            ArrayList arrayList = (ArrayList) valuesList;
            if (arrayList.isEmpty()) {
                Log.i("WifiPolicyService", "Black/white list table is empty - uid: " + i);
                return hashMap;
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it2.next();
                ((Set) hashMap.get(contentValues2.getAsString("WIFI_LIST_TYPE")))
                        .add(contentValues2.getAsString("WIFI_SSID"));
            }
            return hashMap;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            setWifiStateChangeAllowedSystemUI(
                    callingOrCurrentUserId, isWifiStateChangeAllowed(null));
            setWifiAllowedSystemUI(callingOrCurrentUserId, isWifiAllowed(null, false));
            this.mLocalProxyManager.updateWifiProxy(null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0086, code lost:

       r13 = "Cursor is null";
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0092, code lost:

       if (r8 != null) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00a1, code lost:

       if (0 == 0) goto L40;
    */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006c  */
    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPreAdminRemoval(int r14) {
        /*
            r13 = this;
            java.lang.String r0 = "resetWifiPolicyAPIs - START"
            java.lang.String r1 = "WifiPolicyService"
            android.util.Log.i(r1, r0)
            java.lang.String r0 = "resetUserRestrictionAPIs - END"
            java.lang.String r2 = "resetUserRestrictionAPIs - "
            java.lang.String r3 = "resetUserRestrictionAPIs - START"
            android.util.Log.i(r1, r3)
            java.lang.String r3 = "adminUid"
            java.lang.String r4 = "allowWifi"
            java.lang.String r5 = "allowWifiStateChanges"
            java.lang.String r6 = "allowUserProfiles"
            java.lang.String[] r7 = new java.lang.String[]{r3, r4, r5, r6}
            r8 = 0
            com.android.server.enterprise.storage.EdmStorageProvider r9 = r13.mEdmStorageProvider     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            java.lang.String r10 = "WIFI"
            android.database.Cursor r8 = r9.getCursor(r10, r7, r8)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r8 == 0) goto L84
            int r7 = r8.getCount()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r7 > 0) goto L32
            goto L84
        L32:
            r8.moveToFirst()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            r2 = 0
            r7 = r2
            r9 = r7
        L38:
            int r10 = r8.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            int r10 = r8.getInt(r10)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            int r11 = r8.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            int r11 = r8.getInt(r11)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            r12 = -1
            if (r11 == 0) goto L5b
            int r11 = r8.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            int r11 = r8.getInt(r11)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r11 != 0) goto L62
            goto L5b
        L56:
            r13 = move-exception
            goto Lbf
        L59:
            r13 = move-exception
            goto L9b
        L5b:
            if (r7 != 0) goto L5f
            r7 = r10
            goto L62
        L5f:
            if (r7 == r12) goto L62
            r7 = r12
        L62:
            int r11 = r8.getColumnIndex(r6)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            int r11 = r8.getInt(r11)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r11 != 0) goto L73
            if (r9 != 0) goto L70
            r9 = r10
            goto L73
        L70:
            if (r9 == r12) goto L73
            r9 = r12
        L73:
            boolean r10 = r8.moveToNext()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r10 != 0) goto L38
            if (r7 != r14) goto L7e
            r13.setChangeWifiStateUserRestriction(r2)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
        L7e:
            if (r9 != r14) goto L94
            r13.setAddWifiConfigUserRestriction(r2)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            goto L94
        L84:
            if (r8 != 0) goto L89
            java.lang.String r13 = "Cursor is null"
            goto L8b
        L89:
            java.lang.String r13 = "Cursor is empty"
        L8b:
            java.lang.String r13 = r2.concat(r13)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            android.util.Log.i(r1, r13)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
            if (r8 == 0) goto L97
        L94:
            r8.close()
        L97:
            android.util.Log.i(r1, r0)
            goto La4
        L9b:
            java.lang.String r14 = "resetUserRestrictionAPIs"
            android.util.Log.e(r1, r14, r13)     // Catch: java.lang.Throwable -> L56
            if (r8 == 0) goto L97
            goto L94
        La4:
            java.lang.Class<android.app.admin.DevicePolicyManagerInternal> r13 = android.app.admin.DevicePolicyManagerInternal.class
            java.lang.Object r13 = com.android.server.LocalServices.getService(r13)
            android.app.admin.DevicePolicyManagerInternal r13 = (android.app.admin.DevicePolicyManagerInternal) r13
            if (r13 != 0) goto Lb5
            java.lang.String r13 = "resetWifiPolicyAPIs - fail to retrieve dpmi object"
            android.util.Log.e(r1, r13)
            goto Lb8
        Lb5:
            r13.notifyChangesOnWifiPolicy()
        Lb8:
            java.lang.String r13 = "resetWifiPolicyAPIs - END"
            android.util.Log.i(r1, r13)
            return
        Lbf:
            if (r8 == 0) goto Lc4
            r8.close()
        Lc4:
            android.util.Log.i(r1, r0)
            throw r13
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.onPreAdminRemoval(int):void");
    }

    public final boolean removeBlockedNetwork(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        if (str == null) {
            return false;
        }
        int i = enforceOwnerOnlyAndWifiPermission.mCallerUid;
        String convertToQuotedString = convertToQuotedString(str);
        HashSet hashSet = (HashSet) getBlockedList$1(i);
        hashSet.remove(convertToQuotedString);
        StringBuilder sb = new StringBuilder();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()) + ",");
        }
        this.mEdmStorageProvider.putString(i, 0, "WIFI", "blockedSSIDList", sb.toString());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                    60,
                    new Object[] {
                        Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str
                    });
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) {
        return removeNetworkConfigurationInternal(contextInfo, str);
    }

    public final boolean removeNetworkConfigurationInternal(ContextInfo contextInfo, String str) {
        boolean z;
        Log.v("WifiPolicyService", ">>> removeNetworkConfiguration ");
        String removeDoubleQuotes = NetworkUtils.removeDoubleQuotes(str);
        if (TextUtils.isEmpty(removeDoubleQuotes)) {
            removeDoubleQuotes = null;
        }
        boolean z2 = false;
        if (removeDoubleQuotes == null) {
            return false;
        }
        int adminByField =
                UserHandle.getAppId(Binder.getCallingUid()) != 1000
                        ? enforceOwnerOnlyAndWifiPermission(contextInfo).mCallerUid
                        : this.mEdmStorageProvider.getAdminByField(
                                "WIFI_CONF", "networkSSID", removeDoubleQuotes);
        if (adminByField == -1) {
            Log.d("WifiPolicyService", "No admin found for the given SSID");
        }
        if (adminByField == -1) {
            return false;
        }
        if (Binder.getCallingPid() == Process.myPid()) {
            return removeNetworkConfigurationMDM(adminByField, removeDoubleQuotes);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WifiConfiguration networkConfiguration =
                    getNetworkConfiguration(adminByField, removeDoubleQuotes);
            if (networkConfiguration != null && networkConfiguration.SSID != null) {
                boolean removeNetworkConfigurationMDM =
                        removeNetworkConfigurationMDM(adminByField, removeDoubleQuotes);
                if (!removeNetworkConfigurationMDM) {
                    Log.i("WifiPolicyService", "Could not remove network from database");
                    return false;
                }
                String[] caCertificateAliases =
                        networkConfiguration.enterpriseConfig.getCaCertificateAliases();
                boolean z3 = true;
                if (caCertificateAliases == null
                        || caCertificateAliases.length <= 0
                        || TextUtils.isEmpty(caCertificateAliases[0])) {
                    z = false;
                } else {
                    networkConfiguration.enterpriseConfig.setCaCertificateAliases(
                            new String[] {""});
                    z = true;
                }
                if (TextUtils.isEmpty(
                        networkConfiguration.enterpriseConfig.getClientCertificateAlias())) {
                    z3 = z;
                } else {
                    networkConfiguration.enterpriseConfig.setClientCertificateAlias("");
                }
                if (networkConfiguration.networkId >= 0) {
                    if (z3) {
                        networkConfiguration.networkId = Integer.MAX_VALUE;
                        save(networkConfiguration, -1);
                    }
                    this.mWifiAdapter.getClass();
                    try {
                        z2 =
                                WifiManagerAdapter.mWifiManager.removeNetwork(
                                        networkConfiguration.networkId);
                    } catch (Exception unused) {
                        Log.e("WifiManagerAdapter", "forget - failed to get wifi service");
                    }
                    Log.i(
                            "WifiPolicyService",
                            "Removing network id "
                                    + networkConfiguration.networkId
                                    + ", ret = "
                                    + z2);
                    removeNetworkConfigurationMDM = z2;
                }
                return removeNetworkConfigurationMDM;
            }
            Log.d("WifiPolicyService", "Not a valid MDM SSID");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeNetworkConfigurationMDM(int i, String str) {
        boolean removeByAdminAndField =
                this.mEdmStorageProvider.removeByAdminAndField(i, "WIFI_CONF", "networkSSID", str);
        if (removeByAdminAndField) {
            this.mEdmStorageProvider.delete(
                    "WifiProxyAuthTable",
                    AccountManagerService$$ExternalSyntheticOutline0.m("networkSSID", str));
            ContentValues contentValues = new ContentValues();
            contentValues.put("networkSSID", str);
            this.mEdmStorageProvider.delete("WifiProxyTable", contentValues);
            this.mLocalProxyManager.getClass();
            Log.d("LocalProxyManager", "Removing wifi proxy for ssid " + str);
            synchronized (LocalProxyManager.mProxyLock) {
                ((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache).remove(str);
            }
        }
        return removeByAdminAndField;
    }

    public final boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e(
                    "WifiPolicyService",
                    "removeWifiSsidFromBlackList - not a valid caller, aborting!");
            return false;
        }
        List validateSSIDList = validateSSIDList(list);
        if (validateSSIDList != null) {
            ArrayList arrayList = (ArrayList) validateSSIDList;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                boolean z2 = true;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    z2 =
                            z2
                                    && this.mEdmStorageProvider.deleteDataByFields(
                                            "WIFI_SSID_BLACK_WHITE_LIST",
                                            new String[] {
                                                "adminUid", "WIFI_SSID", "WIFI_LIST_TYPE"
                                            },
                                            new String[] {
                                                String.valueOf(
                                                        enforceOwnerOnlyAndWifiPermission
                                                                .mCallerUid),
                                                str,
                                                "BLACKLIST"
                                            });
                    if (z2) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logEventAsUser(
                                    UserHandle.getUserId(
                                            enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                    63,
                                    new Object[] {
                                        Integer.valueOf(
                                                enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                        str
                                    });
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
                    return z2;
                }
                Log.i(
                        "WifiPolicyService",
                        "removeWifiSsidFromBlackList - caller uid: "
                                + callerInfoFromUid.first
                                + ", enforced caller uid: "
                                + enforceOwnerOnlyAndWifiPermission.mCallerUid
                                + ", list size: "
                                + arrayList.size());
                if (z2
                        && setSsidAllowDenyList(
                                (ComponentName) callerInfoFromUid.second,
                                UserHandle.getUserId(
                                        ((Integer) callerInfoFromUid.first).intValue()),
                                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                                true)) {
                    z = true;
                }
                return z;
            }
        }
        Log.e("WifiPolicyService", "removeWifiSsidFromBlackList - failed with invalid request");
        return false;
    }

    public final boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e(
                    "WifiPolicyService",
                    "removeWifiSsidFromWhiteList - not a valid caller, aborting!");
            return false;
        }
        List validateSSIDList = validateSSIDList(list);
        if (validateSSIDList != null) {
            ArrayList arrayList = (ArrayList) validateSSIDList;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                boolean z2 = true;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    z2 =
                            z2
                                    && this.mEdmStorageProvider.deleteDataByFields(
                                            "WIFI_SSID_BLACK_WHITE_LIST",
                                            new String[] {
                                                "adminUid", "WIFI_SSID", "WIFI_LIST_TYPE"
                                            },
                                            new String[] {
                                                String.valueOf(
                                                        enforceOwnerOnlyAndWifiPermission
                                                                .mCallerUid),
                                                str,
                                                "WHITELIST"
                                            });
                    if (z2) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logEventAsUser(
                                    UserHandle.getUserId(
                                            enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                    66,
                                    new Object[] {
                                        Integer.valueOf(
                                                enforceOwnerOnlyAndWifiPermission.mCallerUid),
                                        str
                                    });
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
                    return z2;
                }
                Log.i(
                        "WifiPolicyService",
                        "removeWifiSsidFromWhiteList - caller uid: "
                                + callerInfoFromUid.first
                                + ", enforced caller uid: "
                                + enforceOwnerOnlyAndWifiPermission.mCallerUid
                                + ", list size: "
                                + arrayList.size());
                if (z2
                        && setSsidAllowDenyList(
                                (ComponentName) callerInfoFromUid.second,
                                UserHandle.getUserId(
                                        ((Integer) callerInfoFromUid.first).intValue()),
                                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                                true)) {
                    z = true;
                }
                return z;
            }
        }
        Log.e("WifiPolicyService", "removeWifiSsidFromWhiteList - failed with invalid request");
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0080, code lost:

       r9 = "Cursor is null";
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009b, code lost:

       if (0 == 0) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resetAutomaticConnectionPolicy(int r10) {
        /*
            r9 = this;
            java.lang.String r0 = "resetAutomaticConnectionPolicy - END"
            java.lang.String r1 = "resetAutomaticConnectionPolicy - "
            int r2 = android.os.Binder.getCallingPid()
            int r3 = android.os.Process.myPid()
            if (r2 != r3) goto La8
            java.lang.String r2 = "WifiPolicyService"
            java.lang.String r3 = "resetAutomaticConnectionPolicy - START"
            android.util.Log.i(r2, r3)
            java.lang.String r3 = "adminUid"
            java.lang.String r4 = "allowAutomaticConnection"
            java.lang.String[] r5 = new java.lang.String[]{r3, r4}
            r6 = 0
            com.android.server.enterprise.storage.EdmStorageProvider r7 = r9.mEdmStorageProvider     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.String r8 = "WIFI"
            android.database.Cursor r6 = r7.getCursor(r8, r5, r6)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r6 == 0) goto L7e
            int r5 = r6.getCount()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r5 > 0) goto L32
            goto L7e
        L32:
            r6.moveToFirst()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            r1 = 0
        L36:
            int r5 = r6.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            int r5 = r6.getInt(r5)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            int r7 = r6.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            int r7 = r6.getInt(r7)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r7 != 0) goto L50
            if (r1 != 0) goto L4c
        L4a:
            r1 = r5
            goto L50
        L4c:
            r5 = -1
            if (r1 == r5) goto L50
            goto L4a
        L50:
            boolean r5 = r6.moveToNext()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r5 != 0) goto L36
            if (r1 != r10) goto L77
            android.util.Pair r10 = r9.getCallerInfoFromUid(r1)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r10 == 0) goto L77
            java.lang.Object r1 = r10.first     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            int r1 = r1.intValue()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.Object r10 = r10.second     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            android.content.ComponentName r10 = (android.content.ComponentName) r10     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            java.lang.String r10 = r10.getPackageName()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            r3 = 1
            r9.setAllowAutojoinGlobal(r1, r10, r3)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            goto L77
        L73:
            r9 = move-exception
            goto L9f
        L75:
            r9 = move-exception
            goto L95
        L77:
            r6.close()
        L7a:
            android.util.Log.i(r2, r0)
            goto L9e
        L7e:
            if (r6 != 0) goto L83
            java.lang.String r9 = "Cursor is null"
            goto L85
        L83:
            java.lang.String r9 = "Cursor is empty"
        L85:
            java.lang.String r9 = r1.concat(r9)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            android.util.Log.i(r2, r9)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L75
            if (r6 == 0) goto L91
            r6.close()
        L91:
            android.util.Log.i(r2, r0)
            return
        L95:
            java.lang.String r10 = "resetAutomaticConnectionPolicy"
            android.util.Log.e(r2, r10, r9)     // Catch: java.lang.Throwable -> L73
            if (r6 == 0) goto L7a
            goto L77
        L9e:
            return
        L9f:
            if (r6 == 0) goto La4
            r6.close()
        La4:
            android.util.Log.i(r2, r0)
            throw r9
        La8:
            java.lang.SecurityException r9 = new java.lang.SecurityException
            java.lang.String r10 = "Can only be called by system process"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.resetAutomaticConnectionPolicy(int):void");
    }

    public final int save(WifiConfiguration wifiConfiguration, int i) {
        Pair callerInfoFromUid;
        Log.i("WifiPolicyService", "getNetworkCreatorInfo - START");
        if (i == -1) {
            String str = wifiConfiguration.SSID;
            String makeString =
                    makeString(
                            wifiConfiguration.allowedKeyManagement,
                            WifiConfiguration.KeyMgmt.strings);
            Log.i("WifiPolicyService", "getNetworkUidFromDatabase - START");
            String[] strArr = {NetworkUtils.removeDoubleQuotes(str), makeString};
            ArrayList dataByFields =
                    this.mEdmStorageProvider.getDataByFields(
                            "WIFI_CONF",
                            new String[] {"networkSSID", "networkKeyMgmt"},
                            strArr,
                            new String[] {"adminUid"});
            if (dataByFields.isEmpty()) {
                Log.e("WifiPolicyService", "getNetworkUidFromDatabase - cv is empty or null");
            } else {
                Integer asInteger = ((ContentValues) dataByFields.get(0)).getAsInteger("adminUid");
                if (asInteger != null) {
                    i = asInteger.intValue();
                }
            }
            i = -1;
        }
        if (i == -1) {
            Log.e("WifiPolicyService", "getNetworkCreatorInfo - Invalid adminUid, aborting...");
            callerInfoFromUid = null;
        } else {
            callerInfoFromUid = getCallerInfoFromUid(i);
        }
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "save - Could not get network creator information");
            return -1;
        }
        int intValue = ((Integer) callerInfoFromUid.first).intValue();
        String packageName = ((ComponentName) callerInfoFromUid.second).getPackageName();
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager == null
                || (!devicePolicyManager.isDeviceOwnerApp(packageName)
                        && UserHandle.getUserId(intValue) == 0)) {
            intValue = 1000;
        }
        WifiManagerAdapter wifiManagerAdapter = this.mWifiAdapter;
        String packageName2 = ((ComponentName) callerInfoFromUid.second).getPackageName();
        wifiManagerAdapter.getClass();
        return WifiManagerAdapter.save(wifiConfiguration, intValue, packageName2);
    }

    public final void setAddWifiConfigUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUserManagerInternal.setUserRestriction(-1, "no_add_wifi_config", z);
            Log.i("WifiPolicyService", "setAddWifiConfigUserRestriction - value = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setAllowAutojoinGlobal(int i, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WifiManagerAdapter.retrieveWifiManagerObjectWithAttributionSource(this.mContext, str, i)
                    .allowAutojoinGlobal(z);
            Log.i(
                    "WifiPolicyService",
                    "setAllowAutojoinGlobal - uid = "
                            + i
                            + ", packageName = "
                            + str
                            + ", allowAutojoin = "
                            + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "setAllowUserPolicyChanges - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", enable: "
                        + z);
        return this.mEdmStorageProvider.putBoolean(
                "WIFI", enforceOwnerOnlyAndWifiPermission.mCallerUid, z, 0, "allowUserChanges");
    }

    public final boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) {
        boolean z2;
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        ContextInfo enforceActiveAdminPermissionByContext =
                this.mEDM.enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList("com.samsung.android.knox.permission.KNOX_WIFI")));
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceActiveAdminPermissionByContext);
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (SemPersonaManager.isKnoxId(callingOrCurrentUserId)) {
            return false;
        }
        Log.i(
                "WifiPolicyService",
                "setAllowUserProfiles - caller uid: "
                        + enforceActiveAdminPermissionByContext.mCallerUid
                        + ", enable: "
                        + z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "WIFI",
                        enforceActiveAdminPermissionByContext.mCallerUid,
                        z,
                        0,
                        "allowUserProfiles");
        if (!putBoolean) {
            Log.e("WifiPolicyService", "setAllowUserProfiles - fail to store value to database");
            return putBoolean;
        }
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "WIFI", "allowUserProfiles")
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = true;
                break;
            }
            z2 = ((Boolean) it.next()).booleanValue();
            if (!z2) {
                break;
            }
        }
        setAddWifiConfigUserRestriction(!z2);
        return true;
    }

    public final boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (callerInfoFromUid == null) {
            Log.e(
                    "WifiPolicyService",
                    "setAutomaticConnectionToWifi - not a valid caller, aborting!");
            return false;
        }
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "WIFI",
                        enforceOwnerOnlyAndWifiPermission.mCallerUid,
                        z,
                        0,
                        "allowAutomaticConnection");
        if (putBoolean) {
            setAllowAutojoinGlobal(
                    ((Integer) callerInfoFromUid.first).intValue(),
                    ((ComponentName) callerInfoFromUid.second).getPackageName(),
                    getAutomaticConnectionToWifi(null));
            return true;
        }
        Log.e(
                "WifiPolicyService",
                "setAutomaticConnectionToWifi - fail to store value to database");
        return putBoolean;
    }

    public final void setChangeWifiStateUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUserManagerInternal.setUserRestriction(-1, "no_change_wifi_state", z);
            Log.i("WifiPolicyService", "setChangeWifiStateUserRestriction - value = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (callerInfoFromUid == null) {
            Log.e(
                    "WifiPolicyService",
                    "setMinimumRequiredSecurity - not a valid caller, aborting!");
            return false;
        }
        if (i < 0 || i > 31 || !isSecurityLevelSupported(ACCEPTABLE_SECURITY_LEVELS[i])) {
            Log.e("WifiPolicyService", "setMinimumRequiredSecurity - security level not supported");
            return false;
        }
        boolean putInt =
                this.mEdmStorageProvider.putInt(
                        enforceOwnerOnlyAndWifiPermission.mCallerUid,
                        0,
                        i,
                        "WIFI",
                        "minimumRequiredSecurity");
        if (!putInt) {
            Log.e(
                    "WifiPolicyService",
                    "setMinimumRequiredSecurity - fail to store security type to database");
            return putInt;
        }
        Log.i(
                "WifiPolicyService",
                "setMinimumRequiredSecurity - caller uid: "
                        + callerInfoFromUid.first
                        + ", enforced caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", secType: "
                        + i);
        setMinimumRequiredWifiSecurityLevel(
                (ComponentName) callerInfoFromUid.second,
                UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()),
                i);
        return true;
    }

    public final boolean setPasswordHidden(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "setPasswordHidden - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", value: "
                        + z);
        return this.mEdmStorageProvider.putBoolean(
                "WIFI", enforceOwnerOnlyAndWifiPermission.mCallerUid, z, 0, "passwordHidden");
    }

    public final boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "setPromptCredentialsEnabled - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", enable: "
                        + z);
        return this.mEdmStorageProvider.putBoolean(
                "WIFI", enforceOwnerOnlyAndWifiPermission.mCallerUid, z, 0, "promptCredentials");
    }

    public final boolean setSsidAllowDenyList(
            ComponentName componentName, int i, int i2, boolean z) {
        if (componentName == null) {
            Log.e("WifiPolicyService", "setSsidAllowDenyList - component name is null");
            return false;
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal =
                (DevicePolicyManagerInternal)
                        LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            Log.e("WifiPolicyService", "setSsidAllowDenyList - fail to retrieve dpmi object");
            return false;
        }
        WifiSsidPolicy wifiSsidPolicy = null;
        if (z) {
            HashMap hashMap = (HashMap) loadWifiNetworkSsidBlackWhitelist(i2, true);
            Set set = (Set) hashMap.get("WHITELIST");
            Set set2 = (Set) hashMap.get("BLACKLIST");
            if (!set.isEmpty()) {
                if (!set.contains("*")) {
                    ArraySet arraySet = new ArraySet();
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        arraySet.add(
                                WifiSsid.fromBytes(
                                        ((String) it.next()).getBytes(StandardCharsets.UTF_8)));
                    }
                    wifiSsidPolicy = new WifiSsidPolicy(0, arraySet);
                }
                devicePolicyManagerInternal.setWifiSsidPolicy(componentName, wifiSsidPolicy, i);
                StringBuilder sb = new StringBuilder("setWifiSsidPolicy(ALLOWLIST) - who: ");
                sb.append(componentName);
                sb.append(", userId: ");
                sb.append(i);
                sb.append(", enforced caller uid: ");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, i2, "WifiPolicyService");
            } else if (set2.isEmpty()) {
                devicePolicyManagerInternal.setWifiSsidPolicy(
                        componentName, (WifiSsidPolicy) null, i);
                Log.i(
                        "WifiPolicyService",
                        "setWifiSsidPolicy(null) - who: "
                                + componentName
                                + ", userId: "
                                + i
                                + ", enforced caller uid: "
                                + i2
                                + ", ssid restriction is activated but there is no allow/deny list"
                                + " in database");
            } else {
                if (!set2.contains("*")) {
                    ArraySet arraySet2 = new ArraySet();
                    Iterator it2 = set2.iterator();
                    while (it2.hasNext()) {
                        arraySet2.add(
                                WifiSsid.fromBytes(
                                        ((String) it2.next()).getBytes(StandardCharsets.UTF_8)));
                    }
                    wifiSsidPolicy = new WifiSsidPolicy(1, arraySet2);
                }
                devicePolicyManagerInternal.setWifiSsidPolicy(componentName, wifiSsidPolicy, i);
                StringBuilder sb2 = new StringBuilder("setWifiSsidPolicy(DENYLIST) - who: ");
                sb2.append(componentName);
                sb2.append(", userId: ");
                sb2.append(i);
                sb2.append(", enforced caller uid: ");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb2, i2, "WifiPolicyService");
            }
        } else {
            devicePolicyManagerInternal.setWifiSsidPolicy(componentName, (WifiSsidPolicy) null, i);
            Log.i(
                    "WifiPolicyService",
                    "setWifiSsidPolicy(null) - who: "
                            + componentName
                            + ", userId: "
                            + i
                            + ", enforced caller uid: "
                            + i2
                            + ", ssid restriction is not activated");
        }
        return true;
    }

    public final boolean setWifi(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)
                && Utils.isDexActivated(this.mContext)
                && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider)
                        == enforceOwnerOnlyAndWifiPermission.mCallerUid) {
            Log.d("WifiPolicyService", "failed to setwifi due to Ethernet only mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean putBoolean =
                    this.mEdmStorageProvider.putBoolean(
                            "WIFI",
                            enforceOwnerOnlyAndWifiPermission.mCallerUid,
                            z,
                            0,
                            "allowWifi");
            boolean isWifiAllowed = isWifiAllowed(enforceOwnerOnlyAndWifiPermission, false);
            if (z && isWifiAllowed) {
                this.mWifiManager.setWifiEnabled(true);
            } else {
                this.mWifiManager.setWifiEnabled(false);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int callingOrCurrentUserId =
                    Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndWifiPermission);
            if (putBoolean && callingOrCurrentUserId == 0) {
                setWifiAllowedSystemUI(callingOrCurrentUserId, isWifiAllowed);
            }
            return putBoolean;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setWifiAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2;
        long clearCallingIdentity;
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "setWifiAllowed - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", enable: "
                        + z);
        if (!isWifiStateChangeAllowed(null)) {
            return false;
        }
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider)
                && Utils.isDexActivated(this.mContext)
                && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider)
                        == enforceOwnerOnlyAndWifiPermission.mCallerUid) {
            Log.d("WifiPolicyService", "failed to setWifiAllowed due to Ethernet only mode");
            return false;
        }
        if (z) {
            z2 = true;
        } else {
            if (this.mWifiManager == null) {
                Log.i("WifiPolicyService", "setWifiAllowed - mWifiManager = null");
                boolean putBoolean =
                        this.mEdmStorageProvider.putBoolean(
                                "WIFI",
                                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                                z,
                                0,
                                "allowWifi");
                if (putBoolean) {
                    setChangeWifiStateUserRestriction(!isWifiAllowed(null, false));
                    return true;
                }
                Log.e("WifiPolicyService", "setWifiAllowed - fail to store value to database");
                return putBoolean;
            }
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean wifiEnabled = this.mWifiManager.setWifiEnabled(z);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = wifiEnabled;
            } finally {
            }
        }
        boolean putBoolean2 =
                this.mEdmStorageProvider.putBoolean(
                                "WIFI",
                                enforceOwnerOnlyAndWifiPermission.mCallerUid,
                                z,
                                0,
                                "allowWifi")
                        & z2;
        if (!putBoolean2) {
            Log.e("WifiPolicyService", "setWifiAllowed - fail to store value to database");
            return putBoolean2;
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                    61,
                    new Object[] {
                        Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                        Boolean.valueOf(z)
                    });
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setChangeWifiStateUserRestriction(!isWifiAllowed(null, false));
            int callingOrCurrentUserId =
                    Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndWifiPermission);
            if (callingOrCurrentUserId == 0) {
                setWifiAllowedSystemUI(
                        callingOrCurrentUserId,
                        isWifiAllowed(enforceOwnerOnlyAndWifiPermission, false));
            }
            return true;
        } finally {
        }
    }

    public final void setWifiAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setWifiAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("WifiPolicyService", "setWifiAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setWifiApSetting(
            ContextInfo contextInfo, String str, String str2, String str3) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "setWifiApSetting");
        if (TextUtils.isEmpty(str)) {
            Log.w("WifiPolicyService", "SSID is empty");
            return false;
        }
        int length = str.getBytes(StandardCharsets.UTF_8).length;
        if (length < 1 || length > 32) {
            Log.e("WifiPolicyService", "SSID has more than 32 characters");
            return false;
        }
        if (!str2.equals("Open") && !str2.equals("WPA2_PSK") && !str2.equals("SAE")) {
            Log.e("WifiPolicyService", "Security Type (" + str2 + ") is not valid");
            return false;
        }
        SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
        if (semWifiManager == null) {
            Log.d("WifiPolicyService", "semWifiManager is null");
        } else if (str2.equals("SAE") && !semWifiManager.isWifiApWpa3Supported()) {
            Log.e("WifiPolicyService", "Wi-Fi AP WPA3 is not supported");
            return false;
        }
        if (!isOpenWifiApAllowed(contextInfo) && str2.equals("Open")) {
            Log.e("WifiPolicyService", "Security Type (" + str2 + ") is not valid");
            return false;
        }
        if ((str2.equals("WPA2_PSK") || str2.equals("SAE"))
                && (str3 == null || str3.length() < 8 || str3.length() > 63)) {
            Log.e(
                    "WifiPolicyService",
                    "Password does not meet requirements for " + str2 + " security type");
            return false;
        }
        this.mIsAPSettingFromAdmin = true;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWifiAdapter.getClass();
            SoftApConfiguration.Builder builder =
                    new SoftApConfiguration.Builder(
                            WifiManagerAdapter.mSemWifiManager.getSoftApConfiguration());
            builder.setSsid(str);
            builder.setHiddenSsid(false);
            if (str2.equals("WPA2_PSK")) {
                builder.setPassphrase(str3, 1);
            } else if (str2.equals("SAE")) {
                builder.setPassphrase(str3, 3);
            } else {
                builder.setPassphrase((String) null, 0);
            }
            int i =
                    Settings.Secure.getInt(
                            this.mContext.getContentResolver(), "wifi_ap_last_2g_channel", 0);
            if (i == 0) {
                builder.setBand(1);
            } else {
                builder.setChannel(i, 1);
            }
            WifiManagerAdapter wifiManagerAdapter = this.mWifiAdapter;
            SoftApConfiguration build = builder.build();
            wifiManagerAdapter.getClass();
            WifiManagerAdapter.mSemWifiManager.setSoftApConfiguration(build);
            this.mWifiAdapter.getClass();
            int wifiApState = WifiManagerAdapter.mSemWifiManager.getWifiApState();
            if (wifiApState == 13 || wifiApState == 12) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.mWifiAdapter.getClass();
                WifiManagerAdapter.mSemWifiManager.resetSoftAp(new Message());
            }
            ApplicationRestrictionsManager applicationRestrictionsManager =
                    ApplicationRestrictionsManager.getInstance(this.mContext);
            if (applicationRestrictionsManager != null
                    && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                if (this.mAppPolicy == null) {
                    this.mAppPolicy =
                            (ApplicationPolicy)
                                    EnterpriseService.getPolicyService("application_policy");
                }
                this.mAppPolicy.stopApp(
                        new ContextInfo(Binder.getCallingUid()),
                        KnoxCustomManagerService.SETTING_PKG_NAME);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mIsAPSettingFromAdmin = false;
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mIsAPSettingFromAdmin = false;
            throw th;
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0391  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0399  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x050f  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x056e  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x06fb  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x06fd  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x0716  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x071d  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x0a30  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0a99  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x0b89  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x0b90  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x0aa1  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0540  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setWifiProfile(
            com.samsung.android.knox.ContextInfo r29,
            com.samsung.android.knox.net.wifi.WifiAdminProfile r30) {
        /*
            Method dump skipped, instructions count: 3081
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.setWifiProfile(com.samsung.android.knox.ContextInfo,"
                    + " com.samsung.android.knox.net.wifi.WifiAdminProfile):boolean");
    }

    public final boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission =
                enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i(
                "WifiPolicyService",
                "setWifiStateChangeAllowed - caller uid: "
                        + enforceOwnerOnlyAndWifiPermission.mCallerUid
                        + ", allow: "
                        + z);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "WIFI",
                        enforceOwnerOnlyAndWifiPermission.mCallerUid,
                        z,
                        0,
                        "allowWifiStateChanges");
        if (!putBoolean) {
            Log.e(
                    "WifiPolicyService",
                    "setWifiStateChangeAllowed - fail to store value to database");
            return putBoolean;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(
                    UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                    69,
                    new Object[] {
                        Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid),
                        Boolean.valueOf(z)
                    });
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setChangeWifiStateUserRestriction(!isWifiStateChangeAllowed(null));
            int callingOrCurrentUserId =
                    Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndWifiPermission);
            if (callingOrCurrentUserId == 0) {
                setWifiStateChangeAllowedSystemUI(
                        callingOrCurrentUserId,
                        isWifiStateChangeAllowed(enforceOwnerOnlyAndWifiPermission));
            }
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setWifiStateChangeAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setWifiStateChangeAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("WifiPolicyService", "setWifiStateChangeAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x021d, code lost:

       if (r5 == null) goto L120;
    */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x027b, code lost:

       if (r3 == null) goto L112;
    */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x027d, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0280, code lost:

       updateWifiMigrationFlag("wifi_policy_migration");
    */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x028e, code lost:

       if (r3 == null) goto L112;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x020c, code lost:

       if (r5 != null) goto L81;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02ef  */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.enterprise.proxy.LocalProxyManager] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.util.ArrayMap] */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.server.enterprise.storage.EdmStorageProvider, com.android.server.enterprise.storage.EdmStorageProviderBase] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.server.enterprise.storage.EdmStorageProvider, com.android.server.enterprise.storage.EdmStorageProviderBase] */
    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void systemReady() {
        /*
            Method dump skipped, instructions count: 760
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.wifi.WifiPolicy.systemReady():void");
    }

    public final void updateWifiMigrationFlag(String str) {
        Log.i("WifiPolicyService", "Updating Wi-Fi migration flag - type = ".concat(str));
        if ("wifi_network_migration".equals(str) || "wifi_policy_migration".equals(str)) {
            this.mEdmStorageProvider.putGenericValueAsUser(0, str, "ok");
        }
    }
}
