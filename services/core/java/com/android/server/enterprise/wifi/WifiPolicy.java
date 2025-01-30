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
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiSsid;
import android.os.Binder;
import android.os.IBinder;
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
import com.android.net.module.util.Inet4AddressUtils;
import com.android.server.LocalServices;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.adapterlayer.WifiManagerAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.proxy.LocalProxyManager;
import com.android.server.enterprise.restriction.RestrictionPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.NetworkUtils;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.UserManagerInternal;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;
import com.samsung.android.knox.net.wifi.IWifiPolicy;
import com.samsung.android.knox.net.wifi.WifiAdminProfile;
import com.samsung.android.knox.net.wifi.WifiControlInfo;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
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

/* loaded from: classes2.dex */
public class WifiPolicy extends IWifiPolicy.Stub implements EnterpriseServiceCallback {
    public ApplicationPolicy mAppPolicy;
    public BroadcastReceiver mBroadcastReceiver;
    public Context mContext;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public volatile boolean mIsAPSettingFromAdmin;
    public LocalProxyManager mLocalProxyManager;
    public UserManager mUserManager;
    public UserManagerInternal mUserManagerInternal;
    public WifiManagerAdapter mWifiAdapter;
    public WifiManager mWifiManager;
    public Map mWifiNetworkSsid;
    public final AtomicInteger mWifiState;

    /* renamed from: pm */
    public final PackageManager f1700pm;
    public static final String[] EAP_STRINGS = {"PEAP", "TLS", "TTLS", "PWD", "SIM", "AKA", "AKA'", "WFA-UNAUTH-TLS", "WAPI_CERT"};
    public static final String[] PHASE2_STRINGS = {"NULL", "PAP", "MSCHAP", "MSCHAPV2", "GTC", "SIM", "AKA", "AKA'"};
    public static boolean isBootCompleted = false;
    public static final String[] mSecurityLevel = {"Open", "WEP", "WPA/WPA2/FT PSK", "802.1x EAP-LEAP", "802.1x EAP-FAST", "802.1x EAP-PEAP", "802.1x EAP-TTLS", "802.1x EAP-TLS", "FT-PSK", "802.1x EAP-PEAP-FT", "802.1x EAP-PEAP-CCKM", "802.1x EAP-TTLS-FT", "802.1x EAP-TTLS-CCKM", "802.1x EAP-TLS-FT", "802.1x EAP-TLS-CCKM", "802.1x EAP-LEAP-FT", "802.1x EAP-LEAP-CCKM", "802.1x EAP-FAST-FT", "802.1x EAP-FAST-CCKM", "802.1x EAP-PWD", "802.1x EAP-PWD-FT", "802.1x EAP-PWD-CCKM", "802.1x EAP-SIM", "802.1x EAP-SIM-FT", "802.1x EAP-SIM-CCKM", "802.1x EAP-AKA", "802.1x EAP-AKA-FT", "802.1x EAP-AKA-CCKM", "802.1x EAP-AKA'", "802.1x EAP-AKA'-FT", "802.1x EAP-AKA'-CCKM", "SAE"};
    public static final String[] ACCEPTABLE_SECURITY_LEVELS = {"NONE", "WEP", "PSK", "", "", "EAP-PEAP", "EAP-TTLS", "EAP-TLS", "", "", "", "", "", "", "", "", "", "", "", "EAP-PWD", "", "", "EAP-SIM", "", "", "EAP-AKA", "", "", "EAP-AKA'", "", "", "SAE"};
    public static final String[] SECURITY_LEVELS = {"NONE", "WEP", "PSK", "EAP-LEAP", "EAP-FAST", "EAP-PEAP", "EAP-TTLS", "EAP-TLS", "FT-PSK", "EAP-PEAP-FT", "EAP-PEAP-CCKM", "EAP-TTLS-FT", "EAP-TTLS-CCKM", "EAP-TLS-FT", "EAP-TLS-CCKM", "EAP-LEAP-FT", "EAP-LEAP-CCKM", "EAP-FAST-FT", "EAP-FAST-CCKM", "EAP-PWD", "EAP-PWD-FT", "EAP-PWD-CCKM", "EAP-SIM", "EAP-SIM-FT", "EAP-SIM-CCKM", "EAP-AKA", "EAP-AKA-FT", "EAP-AKA-CCKM", "EAP-AKA'", "EAP-AKA'-FT", "EAP-AKA'-CCKM", "SAE"};
    public static final Pattern HOSTNAME_PATTERN = Pattern.compile("^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$");
    public static final Pattern EXCLLIST_PATTERN = Pattern.compile("^$|^[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*(,[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*)*$");

    enum mWepKeyIndex {
        wepkey1,
        wepkey2,
        wepkey3,
        wepkey4
    }

    public final int getSecurityLevel(int i) {
        if (i != 19) {
            if (i != 22 && i != 25 && i != 28) {
                if (i == 31) {
                    return 4;
                }
                switch (i) {
                    case 0:
                    default:
                        return 1;
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
                }
            }
            return 7;
        }
        return 5;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    public final IPersonaManagerAdapter getPersonaManagerAdapter() {
        return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
    }

    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public EdmStorageProvider getStorageProvider() {
            return new EdmStorageProvider(this.mContext);
        }

        public PackageManager getPackageManager() {
            return this.mContext.getPackageManager();
        }

        public ApplicationPolicy getApplicationPolicy() {
            return (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
    }

    public WifiPolicy(Context context) {
        this(new Injector(context));
    }

    public WifiPolicy(Injector injector) {
        this.mAppPolicy = null;
        this.mWifiManager = null;
        this.mWifiNetworkSsid = null;
        this.mIsAPSettingFromAdmin = false;
        this.mUserManagerInternal = null;
        this.mWifiState = new AtomicInteger(4);
        this.mEDM = null;
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.wifi.WifiPolicy.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("WifiPolicyService", action);
                if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                    int intExtra = intent.getIntExtra("wifi_state", 4);
                    if ((intExtra == 2 || intExtra == 0) && intExtra != WifiPolicy.this.mWifiState.get()) {
                        WifiPolicy.this.mWifiState.set(intExtra);
                        AuditLog.log(5, 5, true, Process.myPid(), "WifiPolicy", intExtra == 2 ? "Enabling Wifi" : "Disabling Wifi");
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                    WifiPolicy.isBootCompleted = true;
                    WifiPolicy.this.migrateWifiNetworkIfNeeded();
                    return;
                }
                if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(action)) {
                    WifiPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                    return;
                }
                if (action.equals("android.net.wifi.CONFIGURED_NETWORKS_CHANGE")) {
                    int intExtra2 = intent.getIntExtra("changeReason", -1);
                    Log.d("WifiPolicyService", "reason = " + intExtra2);
                    if (intExtra2 == 1) {
                        new Thread(new Runnable() { // from class: com.android.server.enterprise.wifi.WifiPolicy.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                WifiPolicy.this.evaluateNetworkFromDatabase();
                            }
                        }).start();
                    }
                }
            }
        };
        this.mContext = injector.mContext;
        this.f1700pm = injector.getPackageManager();
        this.mEdmStorageProvider = injector.getStorageProvider();
        this.mAppPolicy = injector.getApplicationPolicy();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        intentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        this.mLocalProxyManager = LocalProxyManager.getInstance(this.mContext);
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        this.mWifiAdapter = WifiManagerAdapter.getInstance(this.mContext);
        loadWifiManager();
        initializeWifiProxyListCache();
        if (!migrateWifiRelatedAPIs()) {
            enforceAutomaticConnectionIfNeeded();
        }
        if (isWifiRestrictionMigrationNeeded()) {
            migrateWifiUserRestriction();
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
            this.mLocalProxyManager.updateWifiProxy(null);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        resetWifiPolicyAPIs(i);
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ApplicationPolicy getApplicationPolicy() {
        if (this.mAppPolicy == null) {
            this.mAppPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        }
        return this.mAppPolicy;
    }

    public final ContextInfo enforceWifiPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_WIFI")));
    }

    public final ContextInfo enforceOwnerOnlyAndWifiPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_WIFI")));
    }

    public final ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.sec.enterprise.knox.permission.KNOX_RESTRICTION", "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public final void enforceSystemProcess() {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new SecurityException("Can only be called by system process");
        }
    }

    public final void enforceKnoxExceptionPermission() {
        this.mContext.enforceCallingPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", null);
    }

    public final WifiConfiguration createNetworkSSID(int i, String str) {
        Log.d("WifiPolicyService", "createNetworkSSID - START");
        if (TextUtils.isEmpty(str)) {
            Log.w("WifiPolicyService", "SSID is empty");
            return null;
        }
        if (str.getBytes(StandardCharsets.UTF_8).length > 32) {
            Log.w("WifiPolicyService", "SSID exceeds maximum length 32");
            return null;
        }
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = convertToQuotedString(str);
        wifiConfiguration.networkId = -1;
        wifiConfiguration.hiddenSSID = true;
        if (wifiConfiguration.allowedKeyManagement.isEmpty()) {
            wifiConfiguration.allowedKeyManagement.set(0);
        }
        Log.d("WifiPolicyService", "createNetworkSSID - exited");
        return wifiConfiguration;
    }

    public final int createEntepriseProfile(WifiConfiguration wifiConfiguration, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List<WifiConfiguration> configuredNetworks = this.mWifiAdapter.getConfiguredNetworks();
            if (configuredNetworks != null) {
                for (WifiConfiguration wifiConfiguration2 : configuredNetworks) {
                    if (wifiConfiguration2.SSID.equals(wifiConfiguration.SSID) && !isWifiEphemeralOrPasspoint(wifiConfiguration2)) {
                        String[] caCertificateAliases = wifiConfiguration2.enterpriseConfig.getCaCertificateAliases();
                        boolean z = true;
                        boolean z2 = false;
                        if (caCertificateAliases != null && caCertificateAliases.length > 0 && !TextUtils.isEmpty(caCertificateAliases[0])) {
                            wifiConfiguration2.enterpriseConfig.setCaCertificateAliases(new String[]{""});
                            z2 = true;
                        }
                        if (TextUtils.isEmpty(wifiConfiguration2.enterpriseConfig.getClientCertificateAlias())) {
                            z = z2;
                        } else {
                            wifiConfiguration2.enterpriseConfig.setClientCertificateAlias("");
                        }
                        if (z) {
                            wifiConfiguration2.networkId = Integer.MAX_VALUE;
                            save(wifiConfiguration2, i);
                        }
                        this.mWifiAdapter.forget(wifiConfiguration2);
                    }
                }
            }
            wifiConfiguration.networkId = -1;
            int save = save(wifiConfiguration, i);
            Log.d("WifiPolicyService", "Added network to wifi - id " + save);
            return save;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isDPMNetworkSSID(String str) {
        if (str == null) {
            return false;
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List<WifiConfiguration> configuredNetworks = this.mWifiAdapter.getConfiguredNetworks();
            if (configuredNetworks != null) {
                String removeDoubleQuotes = NetworkUtils.removeDoubleQuotes(str);
                for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                    if (removeDoubleQuotes.equals(NetworkUtils.removeDoubleQuotes(wifiConfiguration.SSID)) && devicePolicyManagerInternal != null && devicePolicyManagerInternal.isActiveDeviceOwner(wifiConfiguration.creatorUid)) {
                        Log.e("WifiPolicyService", "The profile creator is a Device Owner");
                        return true;
                    }
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getNetworkSSIDList(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("WIFI_CONF", null, null, new String[]{"networkSSID"});
        for (int i = 0; i < dataByFields.size(); i++) {
            arrayList.add(((ContentValues) dataByFields.get(i)).getAsString("networkSSID"));
        }
        return arrayList;
    }

    public boolean removeNetworkConfiguration(ContextInfo contextInfo, String str) {
        return removeNetworkConfigurationInternal(contextInfo, str, true);
    }

    public final boolean removeNetworkConfigurationInternal(ContextInfo contextInfo, String str, boolean z) {
        int callingUid;
        Log.v("WifiPolicyService", ">>> removeNetworkConfiguration ");
        String validSsid = getValidSsid(str);
        boolean z2 = false;
        if (validSsid == null || (callingUid = getCallingUid(contextInfo, validSsid)) == -1) {
            return false;
        }
        if (Binder.getCallingPid() != Process.myPid()) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WifiConfiguration networkConfiguration = getNetworkConfiguration(callingUid, validSsid);
                if (networkConfiguration != null && networkConfiguration.SSID != null) {
                    boolean removeNetworkConfigurationMDM = removeNetworkConfigurationMDM(validSsid, callingUid, z);
                    if (!removeNetworkConfigurationMDM) {
                        Log.i("WifiPolicyService", "Could not remove network from database");
                        return false;
                    }
                    String[] caCertificateAliases = networkConfiguration.enterpriseConfig.getCaCertificateAliases();
                    boolean z3 = true;
                    if (caCertificateAliases != null && caCertificateAliases.length > 0 && !TextUtils.isEmpty(caCertificateAliases[0])) {
                        networkConfiguration.enterpriseConfig.setCaCertificateAliases(new String[]{""});
                        z2 = true;
                    }
                    if (TextUtils.isEmpty(networkConfiguration.enterpriseConfig.getClientCertificateAlias())) {
                        z3 = z2;
                    } else {
                        networkConfiguration.enterpriseConfig.setClientCertificateAlias("");
                    }
                    if (networkConfiguration.networkId >= 0) {
                        if (z3) {
                            networkConfiguration.networkId = Integer.MAX_VALUE;
                            save(networkConfiguration, -1);
                        }
                        removeNetworkConfigurationMDM = this.mWifiAdapter.forget(networkConfiguration);
                        Log.i("WifiPolicyService", "Removing network id " + networkConfiguration.networkId + ", ret = " + removeNetworkConfigurationMDM);
                    }
                    return removeNetworkConfigurationMDM;
                }
                Log.d("WifiPolicyService", "Not a valid MDM SSID");
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return removeNetworkConfigurationMDM(validSsid, callingUid, z);
    }

    public final String getValidSsid(String str) {
        String removeDoubleQuotes = NetworkUtils.removeDoubleQuotes(str);
        if (TextUtils.isEmpty(removeDoubleQuotes)) {
            return null;
        }
        return removeDoubleQuotes;
    }

    public final int getCallingUid(ContextInfo contextInfo, String str) {
        int adminByField;
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            adminByField = enforceOwnerOnlyAndWifiPermission(contextInfo).mCallerUid;
        } else {
            adminByField = this.mEdmStorageProvider.getAdminByField("WIFI_CONF", "networkSSID", str);
        }
        if (adminByField == -1) {
            Log.d("WifiPolicyService", "No admin found for the given SSID");
        }
        return adminByField;
    }

    public final boolean removeNetworkConfigurationMDM(String str, int i, boolean z) {
        boolean removeByAdminAndField = this.mEdmStorageProvider.removeByAdminAndField("WIFI_CONF", i, "networkSSID", str) & true;
        if (removeByAdminAndField && z) {
            clearAuthConfigFromDb(str);
            clearProxyInfoFromDb(str);
        }
        return removeByAdminAndField;
    }

    public final boolean isSecurityLevelSupported(String str) {
        if (str.equals("SAE") && !this.mWifiManager.isWpa3SaeSupported()) {
            Log.w("WifiPolicyService", "SAE is not supported on this device");
            return false;
        }
        String[] strArr = ACCEPTABLE_SECURITY_LEVELS;
        if (isWifiOnly(this.mContext)) {
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

    public final WifiConfiguration updateWifiConfigLinkSecurity(WifiConfiguration wifiConfiguration, String str) {
        if (str == null) {
            return null;
        }
        if (TextUtils.isEmpty(wifiConfiguration.SSID)) {
            Log.d("WifiPolicyService", "specified profile not found");
            return null;
        }
        if (!isSecurityLevelSupported(str)) {
            return null;
        }
        Log.d("WifiPolicyService", "updateWifiConfigLinkSecurity: " + str);
        String[] strArr = ACCEPTABLE_SECURITY_LEVELS;
        if (str.contentEquals(strArr[0])) {
            wifiConfiguration.setSecurityParams(0);
        } else if (str.contentEquals(strArr[1])) {
            wifiConfiguration.setSecurityParams(1);
        } else if (str.contentEquals(strArr[2])) {
            wifiConfiguration.setSecurityParams(2);
        } else if (str.contentEquals(strArr[31])) {
            wifiConfiguration.setSecurityParams(4);
        } else {
            wifiConfiguration.setSecurityParams(3);
            if (str.contentEquals(strArr[6])) {
                wifiConfiguration.enterpriseConfig.setEapMethod(2);
            } else if (str.contentEquals(strArr[7])) {
                wifiConfiguration.enterpriseConfig.setEapMethod(1);
            } else if (str.contentEquals(strArr[19])) {
                wifiConfiguration.enterpriseConfig.setEapMethod(3);
            } else if (str.contentEquals(strArr[22])) {
                wifiConfiguration.enterpriseConfig.setEapMethod(4);
            } else if (str.contentEquals(strArr[25])) {
                wifiConfiguration.enterpriseConfig.setEapMethod(5);
            } else if (str.contentEquals(strArr[28])) {
                wifiConfiguration.enterpriseConfig.setEapMethod(6);
            } else {
                wifiConfiguration.enterpriseConfig.setEapMethod(0);
            }
        }
        return wifiConfiguration;
    }

    public final String getNetworkLinkSecurityWifiProfile(WifiConfiguration wifiConfiguration) {
        int securityForWifiProfile = getSecurityForWifiProfile(wifiConfiguration);
        if (securityForWifiProfile != -1) {
            return SECURITY_LEVELS[securityForWifiProfile];
        }
        return null;
    }

    public static int getSecurityForWifiProfile(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration == null) {
            return -1;
        }
        if (wifiConfiguration.allowedKeyManagement.get(0)) {
            return wifiConfiguration.allowedAuthAlgorithms.get(1) ? 1 : 0;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(6)) {
            return 8;
        }
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return 31;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2)) {
            int eapMethod = wifiConfiguration.enterpriseConfig.getEapMethod();
            if (eapMethod == 0) {
                return 5;
            }
            if (eapMethod == 2) {
                return 6;
            }
            if (eapMethod == 1) {
                return 7;
            }
            if (eapMethod == 19) {
                return 3;
            }
            if (eapMethod == 18) {
                return 4;
            }
            if (eapMethod == 3) {
                return 19;
            }
            if (eapMethod == 5) {
                return 25;
            }
            if (eapMethod == 6) {
                return 28;
            }
            if (eapMethod == 4) {
                return 22;
            }
        } else if (wifiConfiguration.allowedKeyManagement.get(7)) {
            int eapMethod2 = wifiConfiguration.enterpriseConfig.getEapMethod();
            if (eapMethod2 == 0) {
                return 9;
            }
            if (eapMethod2 == 2) {
                return 11;
            }
            if (eapMethod2 == 1) {
                return 13;
            }
            if (eapMethod2 == 19) {
                return 15;
            }
            if (eapMethod2 == 18) {
                return 17;
            }
            if (eapMethod2 == 3) {
                return 20;
            }
            if (eapMethod2 == 5) {
                return 26;
            }
            if (eapMethod2 == 6) {
                return 29;
            }
            if (eapMethod2 == 4) {
                return 23;
            }
        } else if (wifiConfiguration.allowedKeyManagement.get(24)) {
            int eapMethod3 = wifiConfiguration.enterpriseConfig.getEapMethod();
            if (eapMethod3 == 0) {
                return 10;
            }
            if (eapMethod3 == 2) {
                return 12;
            }
            if (eapMethod3 == 1) {
                return 14;
            }
            if (eapMethod3 == 19) {
                return 16;
            }
            if (eapMethod3 == 18) {
                return 18;
            }
            if (eapMethod3 == 3) {
                return 21;
            }
            if (eapMethod3 == 5) {
                return 27;
            }
            if (eapMethod3 == 6) {
                return 30;
            }
            if (eapMethod3 == 4) {
                return 24;
            }
        }
        return -1;
    }

    public final WifiConfiguration updateNetworkWEPKeyId(WifiConfiguration wifiConfiguration, int i) {
        if (i < 1 || i > 4 || TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        wifiConfiguration.wepTxKeyIndex = i - 1;
        return wifiConfiguration;
    }

    public final WifiConfiguration updateNetworkWEPKey(WifiConfiguration wifiConfiguration, int i, String str) {
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

    public final WifiConfiguration updateNetworkPSK(WifiConfiguration wifiConfiguration, String str) {
        if (str == null) {
            Log.w("WifiPolicyService", "updateNetworkPSK - value is null");
            return null;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            if (str.length() < 8 || str.length() > 64) {
                Log.w("WifiPolicyService", "Invalid PSK length for PSK security type");
                return null;
            }
        } else if (wifiConfiguration.allowedKeyManagement.get(8) && (str.length() < 1 || str.length() >= 64)) {
            Log.w("WifiPolicyService", "Invalid PSK length for SAE security type");
            return null;
        }
        if (!TextUtils.isEmpty(wifiConfiguration.SSID)) {
            if (str.matches("[0-9A-Fa-f]{64}")) {
                wifiConfiguration.preSharedKey = str;
            } else {
                wifiConfiguration.preSharedKey = convertToQuotedString(str);
            }
            return wifiConfiguration;
        }
        Log.e("WifiPolicyService", "No SSID provided");
        return null;
    }

    public final WifiConfiguration updateEnterpriseFieldValue(WifiConfiguration wifiConfiguration, String str, String str2) {
        boolean z;
        if (str2 == null || TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        if (str.equals("phase2")) {
            int i = 0;
            while (true) {
                String[] strArr = PHASE2_STRINGS;
                if (i >= strArr.length) {
                    z = false;
                    break;
                }
                if (str2.equals(strArr[i])) {
                    wifiConfiguration.enterpriseConfig.setPhase2Method(i);
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                if (!str2.equalsIgnoreCase("None")) {
                    return null;
                }
                wifiConfiguration.enterpriseConfig.setPhase2Method(0);
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
            wifiConfiguration.enterpriseConfig.setCaCertificateAliases(new String[]{str2});
        }
        return wifiConfiguration;
    }

    public final boolean checkSecurityLevel(WifiAdminProfile wifiAdminProfile) {
        String str = wifiAdminProfile.security;
        if (str == null) {
            Log.d("WifiPolicyService", "Profile not set, security is null");
            return false;
        }
        Log.v("WifiPolicyService", "setWifiProfile - sec " + wifiAdminProfile.security);
        if (!isSecurityLevelSupported(str)) {
            return false;
        }
        if ((str.contains("PSK") || str.equals("SAE")) && getValidStr(wifiAdminProfile.psk) == null) {
            Log.w("WifiPolicyService", "PSK not set");
            return false;
        }
        if (str.equals("WEP")) {
            int i = wifiAdminProfile.wepKeyId;
            if (i < 1 || i > 4) {
                Log.w("WifiPolicyService", "WebKeyId invalid");
                return false;
            }
            if (i == 1 && TextUtils.isEmpty(wifiAdminProfile.wepKey1)) {
                Log.w("WifiPolicyService", "wepkey1 not set but index is set to 1");
                return false;
            }
            if (wifiAdminProfile.wepKeyId == 2 && TextUtils.isEmpty(wifiAdminProfile.wepKey2)) {
                Log.w("WifiPolicyService", "wepkey2 not set but index is set to 2");
                return false;
            }
            if (wifiAdminProfile.wepKeyId == 3 && TextUtils.isEmpty(wifiAdminProfile.wepKey3)) {
                Log.w("WifiPolicyService", "wepkey3 not set but index is set to 3");
                return false;
            }
            if (wifiAdminProfile.wepKeyId == 4 && TextUtils.isEmpty(wifiAdminProfile.wepKey4)) {
                Log.w("WifiPolicyService", "wepkey4 not set but index is set to 4");
                return false;
            }
        }
        return true;
    }

    public boolean setWifiProfile(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) {
        Log.d("WifiPolicyService", "setWifiProfile - entered");
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        if (!hasValidSsid(wifiAdminProfile)) {
            return false;
        }
        int i = enforceOwnerOnlyAndWifiPermission.mCallerUid;
        boolean isWifiProfileOwner = isWifiProfileOwner(i, wifiAdminProfile.ssid);
        Log.i("WifiPolicyService", "Is admin editing its previous configuration ? " + isWifiProfileOwner);
        if ((!isWifiProfileOwner && !isAllowedToCreateProfileForSsid(i, wifiAdminProfile.ssid)) || !checkDeprecatedAndInvalidFields(enforceOwnerOnlyAndWifiPermission, wifiAdminProfile)) {
            return false;
        }
        WifiConfiguration convertToWifiConfiguration = convertToWifiConfiguration(wifiAdminProfile, i);
        if (convertToWifiConfiguration == null) {
            Log.e("WifiPolicyService", "Wifi configuration cannot be created, some invalid configuration was provided");
            return false;
        }
        String removeDoubleQuotes = NetworkUtils.removeDoubleQuotes(convertToWifiConfiguration.SSID);
        if (isWifiProfileOwner) {
            Log.i("WifiPolicyService", "Removing previous config");
            if (!removeNetworkConfigurationInternal(enforceOwnerOnlyAndWifiPermission, convertToWifiConfiguration.SSID, true)) {
                Log.e("WifiPolicyService", "Failed to remove previous configuration");
                return false;
            }
        }
        ProxyProperties createProxyProperties = convertToWifiConfiguration.getHttpProxy() != null ? createProxyProperties(wifiAdminProfile.proxyHostname, wifiAdminProfile.proxyPort, wifiAdminProfile.proxyPacUrl, wifiAdminProfile.proxyAuthConfigList, wifiAdminProfile.proxyBypassList, wifiAdminProfile.proxyState) : null;
        int createEntepriseProfile = createEntepriseProfile(convertToWifiConfiguration, i);
        if (createEntepriseProfile != -1) {
            if (!persistWifiConfiguration(i, convertToWifiConfiguration, wifiAdminProfile, createProxyProperties)) {
                Log.e("WifiPolicyService", "Enterprise wifi network persist failed");
                return false;
            }
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put(removeDoubleQuotes, createProxyProperties);
            this.mLocalProxyManager.updateWifiProxy(arrayMap);
            enableNetworkAndMaybeConnect(createEntepriseProfile);
            logProfileCreation(i, wifiAdminProfile);
            Log.d("WifiPolicyService", "setWifiProfile - exited ");
            return true;
        }
        Log.e("WifiPolicyService", "Enterprise wifi network creation failed");
        return false;
    }

    public final boolean isAllowedToCreateProfileForSsid(int i, String str) {
        if (isEnterpriseNetwork(str)) {
            Log.e("WifiPolicyService", "Network belongs to another admin - cannot edit it");
            return false;
        }
        if (isDPMNetworkSSID(str)) {
            String packageNameForUid = getPackageNameForUid(i);
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
            if (!(devicePolicyManager != null && devicePolicyManager.isDeviceOwnerAppOnCallingUser(packageNameForUid))) {
                Log.e("WifiPolicyService", "Attempt to modify/override network created by device owner");
                return false;
            }
        }
        return true;
    }

    public final boolean checkDeprecatedAndInvalidFields(ContextInfo contextInfo, WifiAdminProfile wifiAdminProfile) {
        if (1 == wifiAdminProfile.getEngineId()) {
            Log.e("WifiPolicyService", "WifiAdminProfile::ENGINE_SECPKCS11 engine is no longer supported!");
            return false;
        }
        if (getValidStr(wifiAdminProfile.privateKey) != null) {
            Log.d("WifiPolicyService", "WifiAdminProfile::privateKey is deprecated and shall not be used. Please use WifiAdminProfile::clientCertification instead.");
            return false;
        }
        if (getValidStr(wifiAdminProfile.proxyUsername) != null) {
            Log.d("WifiPolicyService", "WifiAdminProfile::proxyUsername is deprecated and shall not be used. Please use WifiAdminProfile::proxyAuthConfigList instead.");
            return false;
        }
        if (getValidStr(wifiAdminProfile.proxyPassword) != null) {
            Log.d("WifiPolicyService", "WifiAdminProfile::proxyPassword is deprecated and shall not be used. Please use WifiAdminProfile::proxyAuthConfigList instead.");
            return false;
        }
        if (!checkSecurityLevel(wifiAdminProfile)) {
            return false;
        }
        if (2 == wifiAdminProfile.getEngineId()) {
            Log.e("WifiPolicyService", "WifiAdminProfile::ENGINE_UCM is no longer supported from Tiramisu!");
            return false;
        }
        if (wifiAdminProfile.proxyState == 2 && TextUtils.isEmpty(wifiAdminProfile.proxyPacUrl)) {
            Log.e("WifiPolicyService", "Empty proxyPacUrl provided with PROXY_STATE_AUTO_CONFIGURE");
            return false;
        }
        List list = wifiAdminProfile.proxyAuthConfigList;
        if (list != null && !list.isEmpty()) {
            for (AuthConfig authConfig : wifiAdminProfile.proxyAuthConfigList) {
                if (authConfig == null || !authConfig.isValid()) {
                    Log.e("WifiPolicyService", "Invalid auth config provided in proxyAuthConfigList");
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isWifiProfileOwner(int i, String str) {
        return i == this.mEdmStorageProvider.getAdminByField("WIFI_CONF", "networkSSID", str);
    }

    public final boolean hasValidSsid(WifiAdminProfile wifiAdminProfile) {
        if (wifiAdminProfile == null) {
            Log.e("WifiPolicyService", "WifiAdminProfile provided is null");
            return false;
        }
        if (!TextUtils.isEmpty(wifiAdminProfile.ssid)) {
            return true;
        }
        Log.e("WifiPolicyService", "WifiAdminProfile::ssid provided is empty/null");
        return false;
    }

    public final WifiConfiguration convertToWifiConfiguration(WifiAdminProfile wifiAdminProfile, int i) {
        List list;
        int i2;
        WifiConfiguration createNetworkSSID = createNetworkSSID(i, wifiAdminProfile.ssid);
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.wepKey1) != null) {
            createNetworkSSID = updateNetworkWEPKey(createNetworkSSID, mWepKeyIndex.wepkey1.ordinal(), wifiAdminProfile.wepKey1);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.wepKey2) != null) {
            createNetworkSSID = updateNetworkWEPKey(createNetworkSSID, mWepKeyIndex.wepkey2.ordinal(), wifiAdminProfile.wepKey2);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.wepKey3) != null) {
            createNetworkSSID = updateNetworkWEPKey(createNetworkSSID, mWepKeyIndex.wepkey3.ordinal(), wifiAdminProfile.wepKey3);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.wepKey4) != null) {
            createNetworkSSID = updateNetworkWEPKey(createNetworkSSID, mWepKeyIndex.wepkey4.ordinal(), wifiAdminProfile.wepKey4);
        }
        if (createNetworkSSID != null && (i2 = wifiAdminProfile.wepKeyId) != -1) {
            createNetworkSSID = updateNetworkWEPKeyId(createNetworkSSID, i2);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.password) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "password", wifiAdminProfile.password);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.security) != null) {
            createNetworkSSID = updateWifiConfigLinkSecurity(createNetworkSSID, wifiAdminProfile.security);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.psk) != null) {
            createNetworkSSID = updateNetworkPSK(createNetworkSSID, wifiAdminProfile.psk);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.caCertificate) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "ca_cert", wifiAdminProfile.caCertificate);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.clientCertification) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "client_cert", wifiAdminProfile.clientCertification);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.phase1) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "phase1", wifiAdminProfile.phase1);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.phase2) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "phase2", wifiAdminProfile.phase2);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.userIdentity) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "identity", wifiAdminProfile.userIdentity);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.anonymousIdentity) != null) {
            createNetworkSSID = updateEnterpriseFieldValue(createNetworkSSID, "anonymous_identity", wifiAdminProfile.anonymousIdentity);
        }
        if (createNetworkSSID != null) {
            createNetworkSSID = updateNetworkDHCPEnabled(createNetworkSSID, wifiAdminProfile.staticIpEnabled);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.staticIp) != null) {
            createNetworkSSID = updateNetworkAddress(createNetworkSSID, wifiAdminProfile.staticIp, "networkStaticIp");
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.staticGateway) != null) {
            createNetworkSSID = updateNetworkAddress(createNetworkSSID, wifiAdminProfile.staticGateway, "networkStaticGateway");
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.staticPrimaryDns) != null) {
            createNetworkSSID = updateNetworkAddress(createNetworkSSID, wifiAdminProfile.staticPrimaryDns, "networkStaticPrimaryDns");
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.staticSecondaryDns) != null) {
            createNetworkSSID = updateNetworkAddress(createNetworkSSID, wifiAdminProfile.staticSecondaryDns, "networkStaticSecondaryDns");
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.staticSubnetMask) != null) {
            createNetworkSSID = updateNetworkAddress(createNetworkSSID, wifiAdminProfile.staticSubnetMask, "networkStaticSubnetMask");
        }
        if (createNetworkSSID != null) {
            createNetworkSSID = updateNetworkProxyState(createNetworkSSID, wifiAdminProfile.proxyState);
        }
        if (createNetworkSSID != null && getValidStr(wifiAdminProfile.proxyHostname) != null && wifiAdminProfile.proxyState == 1) {
            createNetworkSSID = updateNetworkProxyHostName(createNetworkSSID, wifiAdminProfile.proxyHostname);
        }
        if (createNetworkSSID != null && createNetworkSSID.getIpConfiguration().getProxySettings() != IpConfiguration.ProxySettings.PAC && wifiAdminProfile.proxyState == 1) {
            createNetworkSSID = updateNetworkProxyPort(createNetworkSSID, wifiAdminProfile.proxyPort);
        }
        if (createNetworkSSID != null && (list = wifiAdminProfile.proxyBypassList) != null && !list.isEmpty()) {
            createNetworkSSID = updateUrlForNetworkProxyBypassList(createNetworkSSID, convertArrayToString((String[]) wifiAdminProfile.proxyBypassList.toArray(new String[wifiAdminProfile.proxyBypassList.size()])));
        }
        return (createNetworkSSID == null || getValidStr(wifiAdminProfile.proxyPacUrl) == null) ? createNetworkSSID : updateNetworkProxyPacFileUrl(createNetworkSSID, wifiAdminProfile.proxyPacUrl);
    }

    public final boolean persistWifiConfiguration(int i, WifiConfiguration wifiConfiguration, WifiAdminProfile wifiAdminProfile, ProxyProperties proxyProperties) {
        String removeDoubleQuotes = NetworkUtils.removeDoubleQuotes(wifiConfiguration.SSID);
        if (!setNetworkConfiguration(i, wifiConfiguration)) {
            Log.e("WifiPolicyService", "Failed to persist new configuration");
            return false;
        }
        List list = wifiAdminProfile.proxyAuthConfigList;
        if (list != null && !list.isEmpty() && !saveAuthConfigToDb(i, removeDoubleQuotes, wifiAdminProfile.proxyAuthConfigList)) {
            Log.e("WifiPolicyService", "Failed to persist auth config list");
            return false;
        }
        if (proxyProperties == null || saveProxyInfoToDb(i, removeDoubleQuotes, NetworkUtils.convertToProxyInfo(proxyProperties))) {
            return true;
        }
        Log.e("WifiPolicyService", "Failed to persist proxy info");
        return false;
    }

    public final void enableNetworkAndMaybeConnect(int i) {
        if (-1 == i || !isWifiStateEnabled()) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWifiAdapter.enableNetwork(i, false);
            if (!((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class)).getNetworkInfo(1).isConnected()) {
                this.mWifiAdapter.connect(i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void logProfileCreation(int i, WifiAdminProfile wifiAdminProfile) {
        String str;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String str2 = "";
            if (wifiAdminProfile.caCertificate != null) {
                str = " CA certificate: " + wifiAdminProfile.caCertificate;
            } else {
                str = "";
            }
            if (wifiAdminProfile.clientCertification != null) {
                str2 = " Client credentials " + wifiAdminProfile.clientCertification;
            }
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has set a new wifi profile: SSID: %s Security type: %s %s %s", Integer.valueOf(i), wifiAdminProfile.ssid, wifiAdminProfile.security, str, str2), UserHandle.getUserId(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ContentValues convertAuthConfigToContentValues(int i, String str, AuthConfig authConfig) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("networkSSID", str);
        contentValues.put("host", authConfig.getHost());
        contentValues.put("port", Integer.valueOf(authConfig.getPort()));
        contentValues.put("username", authConfig.getUsername());
        contentValues.put("password", authConfig.getPassword());
        return contentValues;
    }

    public final ContentValues convertProxyInfoToContentValues(int i, String str, ProxyInfo proxyInfo) {
        ContentValues contentValues = new ContentValues();
        String host = !TextUtils.isEmpty(proxyInfo.getHost()) ? proxyInfo.getHost() : "";
        String uri = proxyInfo.getPacFileUrl() != null ? proxyInfo.getPacFileUrl().toString() : "";
        String exclusionListAsString = proxyInfo.getExclusionList() != null ? NetworkUtils.getExclusionListAsString(proxyInfo.getExclusionList()) : "";
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("networkSSID", str);
        contentValues.put("host", host);
        contentValues.put("port", Integer.valueOf(proxyInfo.getPort()));
        contentValues.put("pacfile", uri);
        contentValues.put("exclusion", exclusionListAsString);
        return contentValues;
    }

    public final boolean saveAuthConfigToDb(int i, String str, List list) {
        Iterator it = list.iterator();
        boolean z = true;
        while (it.hasNext()) {
            if (this.mEdmStorageProvider.insert("WifiProxyAuthTable", convertAuthConfigToContentValues(i, str, (AuthConfig) it.next())) == -1) {
                z = false;
            }
        }
        return z;
    }

    public final boolean saveProxyInfoToDb(int i, String str, ProxyInfo proxyInfo) {
        return this.mEdmStorageProvider.insert("WifiProxyTable", convertProxyInfoToContentValues(i, str, proxyInfo)) != -1;
    }

    public final void clearProxyInfoFromDb(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("networkSSID", str);
        this.mEdmStorageProvider.delete("WifiProxyTable", contentValues);
        this.mLocalProxyManager.removeWifiProxy(str);
    }

    public final void clearAuthConfigFromDb(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("networkSSID", str);
        this.mEdmStorageProvider.delete("WifiProxyAuthTable", contentValues);
    }

    public final List getAuthConfigFromDb(String str) {
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("networkSSID", str);
        List<ContentValues> values = this.mEdmStorageProvider.getValues("WifiProxyAuthTable", new String[]{"host", "port", "username", "password"}, contentValues);
        if (values == null) {
            return arrayList;
        }
        for (ContentValues contentValues2 : values) {
            String asString = contentValues2.getAsString("host");
            Integer asInteger = contentValues2.getAsInteger("port");
            String asString2 = contentValues2.getAsString("username");
            String asString3 = contentValues2.getAsString("password");
            if (asInteger == null) {
                asInteger = Integer.valueOf(AuthConfig.ANY_PORT);
            }
            arrayList.add(new AuthConfig(asString, asInteger.intValue(), asString2, asString3));
        }
        return arrayList;
    }

    public final ProxyProperties createProxyProperties(String str, int i, String str2, List list, List list2, int i2) {
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

    public final String getValidStr(String str) {
        if (str != null && str.trim().length() > 0) {
            return str;
        }
        return null;
    }

    public WifiAdminProfile getWifiProfile(ContextInfo contextInfo, String str) {
        WifiConfiguration networkConfiguration;
        StaticIpConfiguration staticIpConfiguration;
        int i = enforceOwnerOnlyAndWifiPermission(contextInfo).mCallerUid;
        if (TextUtils.isEmpty(str) || (networkConfiguration = getNetworkConfiguration(i, str)) == null) {
            return null;
        }
        WifiAdminProfile wifiAdminProfile = new WifiAdminProfile(0);
        String clientCertificateAlias = networkConfiguration.enterpriseConfig.getClientCertificateAlias();
        if (clientCertificateAlias != null) {
            if (UniversalCredentialUtil.isUcsStoreUri(clientCertificateAlias)) {
                String source = UniversalCredentialUtil.getSource(clientCertificateAlias);
                if (source != null) {
                    wifiAdminProfile = new WifiAdminProfile(source);
                    wifiAdminProfile.clientCertification = UniversalCredentialUtil.getRawAlias(clientCertificateAlias);
                }
            } else {
                wifiAdminProfile.clientCertification = clientCertificateAlias.replace("keystore://USRCERT_", "");
            }
        }
        String removeDoubleQuotes = NetworkUtils.removeDoubleQuotes(networkConfiguration.SSID);
        if (!TextUtils.isEmpty(removeDoubleQuotes)) {
            wifiAdminProfile.ssid = removeDoubleQuotes;
        }
        String str2 = networkConfiguration.preSharedKey;
        if (!TextUtils.isEmpty(str2)) {
            wifiAdminProfile.psk = str2;
        }
        String str3 = networkConfiguration.wepKeys[0];
        if (!TextUtils.isEmpty(str3)) {
            wifiAdminProfile.wepKey1 = str3;
        }
        String str4 = networkConfiguration.wepKeys[1];
        if (!TextUtils.isEmpty(str4)) {
            wifiAdminProfile.wepKey2 = str4;
        }
        String str5 = networkConfiguration.wepKeys[2];
        if (!TextUtils.isEmpty(str5)) {
            wifiAdminProfile.wepKey3 = str5;
        }
        String str6 = networkConfiguration.wepKeys[3];
        if (!TextUtils.isEmpty(str6)) {
            wifiAdminProfile.wepKey4 = str6;
        }
        wifiAdminProfile.wepKeyId = networkConfiguration.wepTxKeyIndex + 1;
        String identity = networkConfiguration.enterpriseConfig.getIdentity();
        if (identity != null) {
            wifiAdminProfile.userIdentity = identity;
        }
        String anonymousIdentity = networkConfiguration.enterpriseConfig.getAnonymousIdentity();
        if (anonymousIdentity != null) {
            wifiAdminProfile.anonymousIdentity = anonymousIdentity;
        }
        String str7 = PHASE2_STRINGS[networkConfiguration.enterpriseConfig.getPhase2Method()];
        if (str7 != null) {
            if (str7.equals("NULL") || str7.equals("")) {
                wifiAdminProfile.phase2 = "None";
            } else {
                wifiAdminProfile.phase2 = str7.replace("auth=", "");
            }
        }
        String[] caCertificateAliases = networkConfiguration.enterpriseConfig.getCaCertificateAliases();
        if (caCertificateAliases != null && caCertificateAliases.length > 0) {
            String str8 = caCertificateAliases[0];
            wifiAdminProfile.caCertificate = str8;
            if (str8 != null) {
                wifiAdminProfile.caCertificate = str8.replace("keystore://CACERT_", "");
            }
        }
        String clientCertificateAlias2 = networkConfiguration.enterpriseConfig.getClientCertificateAlias();
        if (clientCertificateAlias2 != null) {
            wifiAdminProfile.privateKey = clientCertificateAlias2;
            if (clientCertificateAlias2.startsWith("keystore://")) {
                wifiAdminProfile.privateKey = wifiAdminProfile.privateKey.replace("keystore://USRPKEY_", "");
            } else if (UniversalCredentialUtil.isUcsStoreUri(wifiAdminProfile.privateKey)) {
                wifiAdminProfile.privateKey = UniversalCredentialUtil.getRawAlias(wifiAdminProfile.privateKey);
            } else {
                wifiAdminProfile.privateKey = wifiAdminProfile.privateKey.replace("USRPKEY_", "");
            }
        }
        wifiAdminProfile.security = getNetworkLinkSecurityWifiProfile(networkConfiguration);
        IpConfiguration ipConfiguration = networkConfiguration.getIpConfiguration();
        boolean z = ipConfiguration.getIpAssignment() == IpConfiguration.IpAssignment.STATIC;
        wifiAdminProfile.staticIpEnabled = z;
        if (z && (staticIpConfiguration = ipConfiguration.getStaticIpConfiguration()) != null) {
            if (staticIpConfiguration.getIpAddress() != null && staticIpConfiguration.getIpAddress().getAddress().getHostAddress() != null) {
                wifiAdminProfile.staticIp = staticIpConfiguration.getIpAddress().getAddress().getHostAddress();
                wifiAdminProfile.staticSubnetMask = Inet4AddressUtils.intToInet4AddressHTL(Inet4AddressUtils.prefixLengthToV4NetmaskIntHTL(staticIpConfiguration.getIpAddress().getPrefixLength())).getHostAddress();
            }
            InetAddress gateway = staticIpConfiguration.getGateway();
            if (gateway != null) {
                wifiAdminProfile.staticGateway = gateway.getHostAddress();
            }
            Iterator<InetAddress> it = staticIpConfiguration.getDnsServers().iterator();
            if (it.hasNext()) {
                wifiAdminProfile.staticPrimaryDns = it.next().getHostAddress();
            }
            if (it.hasNext()) {
                wifiAdminProfile.staticSecondaryDns = it.next().getHostAddress();
            }
        }
        wifiAdminProfile.proxyState = getProxyState(networkConfiguration);
        ProxyInfo httpProxy = networkConfiguration.getHttpProxy();
        if (httpProxy != null) {
            String host = httpProxy.getHost();
            if (!TextUtils.isEmpty(host)) {
                wifiAdminProfile.proxyHostname = host;
            }
            int port = httpProxy.getPort();
            if (port != -1) {
                wifiAdminProfile.proxyPort = port;
            }
            String convertArrayToString = convertArrayToString(httpProxy.getExclusionList());
            if (!TextUtils.isEmpty(convertArrayToString)) {
                wifiAdminProfile.proxyBypassList = Arrays.asList(convertStringToArray(convertArrayToString));
            }
            String uri = httpProxy.getPacFileUrl().toString();
            if (!TextUtils.isEmpty(uri)) {
                wifiAdminProfile.proxyPacUrl = uri;
            }
            List authConfigFromDb = getAuthConfigFromDb(NetworkUtils.removeDoubleQuotes(str));
            if (authConfigFromDb != null && !authConfigFromDb.isEmpty()) {
                wifiAdminProfile.proxyAuthConfigList = authConfigFromDb;
            }
        }
        return wifiAdminProfile;
    }

    public final int getProxyState(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.getHttpProxy() == null) {
            return 0;
        }
        if (wifiConfiguration.getHttpProxy().getPacFileUrl() == null || TextUtils.isEmpty(wifiConfiguration.getHttpProxy().getPacFileUrl().getHost())) {
            return !TextUtils.isEmpty(wifiConfiguration.getHttpProxy().getHost()) ? 1 : 0;
        }
        return 2;
    }

    public boolean removeBlockedNetwork(ContextInfo contextInfo, String str) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        if (str == null) {
            return false;
        }
        removeFromBlocked(enforceOwnerOnlyAndWifiPermission.mCallerUid, convertToQuotedString(str));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has allowed access to Wifi SSID: %s", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public List getBlockedNetworks(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        LinkedList linkedList = new LinkedList();
        Iterator it = getAllBlockedList().iterator();
        while (it.hasNext()) {
            linkedList.add(NetworkUtils.removeDoubleQuotes((String) it.next()));
        }
        return linkedList;
    }

    public final WifiConfiguration updateNetworkDHCPEnabled(WifiConfiguration wifiConfiguration, boolean z) {
        if (TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        IpConfiguration ipConfiguration = wifiConfiguration.getIpConfiguration();
        if (z) {
            ipConfiguration.setIpAssignment(IpConfiguration.IpAssignment.STATIC);
            StaticIpConfiguration staticIpConfigurationIfEnabled = getStaticIpConfigurationIfEnabled(ipConfiguration);
            if (staticIpConfigurationIfEnabled == null) {
                return null;
            }
            ipConfiguration.setStaticIpConfiguration(staticIpConfigurationIfEnabled);
        } else {
            ipConfiguration.setIpAssignment(IpConfiguration.IpAssignment.DHCP);
        }
        wifiConfiguration.setIpConfiguration(ipConfiguration);
        return wifiConfiguration;
    }

    public final StaticIpConfiguration getStaticIpConfigurationIfEnabled(IpConfiguration ipConfiguration) {
        StaticIpConfiguration staticIpConfiguration = ipConfiguration.getStaticIpConfiguration();
        if (staticIpConfiguration == null) {
            staticIpConfiguration = new StaticIpConfiguration();
        }
        StaticIpConfiguration ipAndSubnetMask = setIpAndSubnetMask(staticIpConfiguration, null, null);
        if (ipAndSubnetMask != null) {
            ipAndSubnetMask = setGateway(ipAndSubnetMask, null);
        }
        if (ipAndSubnetMask != null && setDns1AndDns2(ipAndSubnetMask, null, null)) {
            return ipAndSubnetMask;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002f, code lost:
    
        if (r2 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0032, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
    
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
    
        if (r2 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
    
        if (r2 != null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WifiConfiguration updateNetworkAddress(WifiConfiguration wifiConfiguration, String str, String str2) {
        if (TextUtils.isEmpty(wifiConfiguration.SSID) || !isIpAddress(str)) {
            Log.e("WifiPolicyService", "Invalid ip address provided: " + str);
            return null;
        }
        IpConfiguration ipConfiguration = wifiConfiguration.getIpConfiguration();
        StaticIpConfiguration staticIpConfiguration = ipConfiguration.getStaticIpConfiguration();
        if (staticIpConfiguration == null) {
            staticIpConfiguration = new StaticIpConfiguration();
        }
        boolean z = true;
        boolean z2 = false;
        if (str2.equals("networkStaticIp")) {
            staticIpConfiguration = setIpAndSubnetMask(staticIpConfiguration, str, null);
        } else if (str2.equals("networkStaticGateway")) {
            staticIpConfiguration = setGateway(staticIpConfiguration, str);
        } else if (str2.equals("networkStaticPrimaryDns")) {
            z2 = setDns1AndDns2(staticIpConfiguration, str, null);
        } else if (str2.equals("networkStaticSecondaryDns")) {
            z2 = setDns1AndDns2(staticIpConfiguration, null, str);
        } else if (str2.equals("networkStaticSubnetMask")) {
            staticIpConfiguration = setIpAndSubnetMask(staticIpConfiguration, null, str);
        }
        if (!z2) {
            return null;
        }
        ipConfiguration.setStaticIpConfiguration(staticIpConfiguration);
        wifiConfiguration.setIpConfiguration(ipConfiguration);
        return wifiConfiguration;
    }

    public boolean setWifi(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider) && Utils.isDexActivated(this.mContext) && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider) == enforceOwnerOnlyAndWifiPermission.mCallerUid) {
            Log.d("WifiPolicyService", "failed to setwifi due to Ethernet only mode");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowWifi", z);
            if (putBoolean) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has changed Wifi enabled to %s", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            }
            boolean isWifiAllowed = isWifiAllowed(enforceOwnerOnlyAndWifiPermission, false);
            if (z && isWifiAllowed) {
                this.mWifiManager.setWifiEnabled(true);
            } else {
                this.mWifiManager.setWifiEnabled(false);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndWifiPermission);
            if (putBoolean && callingOrCurrentUserId == 0) {
                setWifiAllowedSystemUI(callingOrCurrentUserId, isWifiAllowed);
            }
            return putBoolean;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setWifiAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2;
        long clearCallingIdentity;
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "setWifiAllowed - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", enable: " + z);
        if (!isWifiStateChangeAllowed(null)) {
            return false;
        }
        if (Utils.isEthernetOnlyApplied(this.mEdmStorageProvider) && Utils.isDexActivated(this.mContext) && Utils.getAdminUidForEthernetOnly(this.mEdmStorageProvider) == enforceOwnerOnlyAndWifiPermission.mCallerUid) {
            Log.d("WifiPolicyService", "failed to setWifiAllowed due to Ethernet only mode");
            return false;
        }
        if (z) {
            z2 = true;
        } else {
            if (this.mWifiManager == null) {
                Log.i("WifiPolicyService", "setWifiAllowed - mWifiManager = null");
                boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowWifi", z) & true;
                if (!putBoolean) {
                    Log.e("WifiPolicyService", "setWifiAllowed - fail to store value to database");
                    return putBoolean;
                }
                setChangeWifiStateUserRestriction(!isWifiAllowed(null, false));
                return true;
            }
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                z2 = this.mWifiManager.setWifiEnabled(z) & true;
            } finally {
            }
        }
        boolean putBoolean2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowWifi", z) & z2;
        if (!putBoolean2) {
            Log.e("WifiPolicyService", "setWifiAllowed - fail to store value to database");
            return putBoolean2;
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has changed Wifi allowed to %s", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setChangeWifiStateUserRestriction(!isWifiAllowed(null, false));
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndWifiPermission);
            if (callingOrCurrentUserId == 0) {
                setWifiAllowedSystemUI(callingOrCurrentUserId, isWifiAllowed(enforceOwnerOnlyAndWifiPermission, false));
            }
            return true;
        } finally {
        }
    }

    public boolean isWifiAllowed(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowWifi").iterator();
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
        Log.i("WifiPolicyService", "isWifiAllowed: " + z2);
        return z2;
    }

    public boolean setAllowUserProfiles(ContextInfo contextInfo, boolean z) {
        boolean z2;
        ContextInfo enforceWifiPermission = enforceWifiPermission(contextInfo);
        if (getPersonaManagerAdapter().isValidKnoxId(Utils.getCallingOrCurrentUserId(enforceWifiPermission))) {
            return false;
        }
        Log.i("WifiPolicyService", "setAllowUserProfiles - caller uid: " + enforceWifiPermission.mCallerUid + ", enable: " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceWifiPermission.mCallerUid, "WIFI", "allowUserProfiles", z);
        if (!putBoolean) {
            Log.e("WifiPolicyService", "setAllowUserProfiles - fail to store value to database");
            return putBoolean;
        }
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowUserProfiles").iterator();
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

    public final void setAddWifiConfigUserRestriction(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUserManagerInternal.setUserRestriction(-1, "no_add_wifi_config", z);
            Log.i("WifiPolicyService", "setAddWifiConfigUserRestriction - value = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getAllowUserProfiles(ContextInfo contextInfo, boolean z, int i) {
        boolean z2;
        if (i == -1) {
            i = Utils.getCallingOrCurrentUserId(contextInfo);
        }
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser("WIFI", "allowUserProfiles", i).iterator();
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
            RestrictionToastManager.show(R.string.httpErrorConnect);
        }
        Log.i("WifiPolicyService", "getAllowUserProfiles: " + z2);
        return z2;
    }

    public boolean setAutomaticConnectionToWifi(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "setAutomaticConnectionToWifi - not a valid caller, aborting!");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowAutomaticConnection", z);
        if (!putBoolean) {
            Log.e("WifiPolicyService", "setAutomaticConnectionToWifi - fail to store value to database");
            return putBoolean;
        }
        setAllowAutojoinGlobal(((Integer) callerInfoFromUid.first).intValue(), ((ComponentName) callerInfoFromUid.second).getPackageName(), getAutomaticConnectionToWifi(null));
        return true;
    }

    public final void setAllowAutojoinGlobal(int i, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WifiManagerAdapter.retrieveWifiManagerObjectWithAttributionSource(this.mContext, i, str).allowAutojoinGlobal(z);
            Log.i("WifiPolicyService", "setAllowAutojoinGlobal - uid = " + i + ", packageName = " + str + ", allowAutojoin = " + z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean getAutomaticConnectionToWifi(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowAutomaticConnection").iterator();
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
        Log.i("WifiPolicyService", "getAutomaticConnectionToWifi: " + z);
        return z;
    }

    public boolean setAllowUserPolicyChanges(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "setAllowUserPolicyChanges - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", enable: " + z);
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowUserChanges", z);
    }

    public boolean getAllowUserPolicyChanges(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowUserChanges").iterator();
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
        Log.i("WifiPolicyService", "getAllowUserPolicyChanges: " + z);
        return z;
    }

    public boolean setPromptCredentialsEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "setPromptCredentialsEnabled - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", enable: " + z);
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "promptCredentials", z);
    }

    public boolean getPromptCredentialsEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "promptCredentials").iterator();
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
        Log.i("WifiPolicyService", "getPromptCredentialsEnabled: " + z);
        return z;
    }

    public boolean setMinimumRequiredSecurity(ContextInfo contextInfo, int i) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "setMinimumRequiredSecurity - not a valid caller, aborting!");
            return false;
        }
        if (i < 0 || i > 31 || !isSecurityLevelSupported(ACCEPTABLE_SECURITY_LEVELS[i])) {
            Log.e("WifiPolicyService", "setMinimumRequiredSecurity - security level not supported");
            return false;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "minimumRequiredSecurity", i);
        if (!putInt) {
            Log.e("WifiPolicyService", "setMinimumRequiredSecurity - fail to store security type to database");
            return putInt;
        }
        Log.i("WifiPolicyService", "setMinimumRequiredSecurity - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", secType: " + i);
        setMinimumRequiredWifiSecurityLevel((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), i);
        return true;
    }

    public final void setMinimumRequiredWifiSecurityLevel(ComponentName componentName, int i, int i2) {
        if (componentName == null) {
            Log.e("WifiPolicyService", "setMinimumRequiredWifiSecurityLevel - component name is null");
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
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            Log.e("WifiPolicyService", "setMinimumRequiredWifiSecurityLevel - fail to retrieve dpmi object");
            return;
        }
        devicePolicyManagerInternal.setMinimumRequiredWifiSecurityLevel(componentName, i3, i);
        Log.i("WifiPolicyService", "setMinimumRequiredWifiSecurityLevel - who: " + componentName + ", userId: " + i + ", securityLevel: " + i3);
    }

    public int getMinimumRequiredSecurity(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getIntList("WIFI", "minimumRequiredSecurity").iterator();
        int i = 0;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (getSecurityLevel(intValue) > getSecurityLevel(i)) {
                i = intValue;
            }
        }
        Log.i("WifiPolicyService", "getMinimumRequiredSecurity: " + i);
        return i;
    }

    public boolean setPasswordHidden(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "setPasswordHidden - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", value: " + z);
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "passwordHidden", z);
    }

    public boolean getPasswordHidden(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "passwordHidden").iterator();
        boolean z = false;
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                z = booleanValue;
            }
        }
        Log.i("WifiPolicyService", "getPasswordHidden: " + z);
        return z;
    }

    public final void loadWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
    }

    public final boolean isWifiStateEnabled() {
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null && wifiManager.getWifiState() == 3;
    }

    public final boolean isIpAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("(([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])\\.){3}([2][5][0-5]|[2][0-4][0-9]|[0-1][0-9][0-9]|[0-9][0-9]|[0-9])").matcher(str).matches();
    }

    public final Set getAllBlockedList() {
        List<String> stringList = this.mEdmStorageProvider.getStringList("WIFI", "blockedSSIDList");
        HashSet hashSet = new HashSet();
        for (String str : stringList) {
            if (!str.isEmpty()) {
                hashSet.addAll(Arrays.asList(str.split(",")));
            }
        }
        return hashSet;
    }

    public final Set getBlockedList(int i) {
        String string = this.mEdmStorageProvider.getString(i, "WIFI", "blockedSSIDList");
        HashSet hashSet = new HashSet();
        if (string != null) {
            for (String str : string.split(",")) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public final void saveBlockedList(int i, Set set) {
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()) + ",");
        }
        this.mEdmStorageProvider.putString(i, "WIFI", "blockedSSIDList", sb.toString());
    }

    public final void removeFromBlocked(int i, String str) {
        Set blockedList = getBlockedList(i);
        blockedList.remove(str);
        saveBlockedList(i, blockedList);
    }

    public static String convertToQuotedString(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length > 1 && str.charAt(0) == '\"' && str.charAt(length - 1) == '\"') {
            return str;
        }
        return '\"' + str + '\"';
    }

    public static int lookupString(String str, String[] strArr) {
        int length = strArr.length;
        String replace = str.replace('-', '_');
        for (int i = 0; i < length; i++) {
            if (replace.equals(strArr[i])) {
                return i;
            }
        }
        return -1;
    }

    public final String makeString(BitSet bitSet, String[] strArr) {
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

    public final BitSet makeBitSet(String str, String[] strArr) {
        BitSet bitSet = new BitSet();
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split(" ")) {
                int lookupString = lookupString(str2, strArr);
                if (lookupString >= 0) {
                    bitSet.set(lookupString);
                }
            }
        }
        return bitSet;
    }

    public boolean isEnterpriseNetwork(String str) {
        return getAllEnterpriseSSIDs().contains(NetworkUtils.removeDoubleQuotes(str));
    }

    public final List getAllEnterpriseSSIDs() {
        return this.mEdmStorageProvider.getStringList("WIFI_CONF", "networkSSID");
    }

    public final WifiConfiguration getNetworkFromWifiModule(String str, String str2) {
        WifiConfiguration wifiConfiguration = null;
        if (this.mWifiAdapter == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List configuredNetworks = this.mWifiAdapter.getConfiguredNetworks();
            if (configuredNetworks != null) {
                Iterator it = configuredNetworks.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WifiConfiguration wifiConfiguration2 = (WifiConfiguration) it.next();
                    if (wifiConfiguration2.SSID != null && !isWifiEphemeralOrPasspoint(wifiConfiguration2) && wifiConfiguration2.SSID.equals(convertToQuotedString(str)) && !TextUtils.isEmpty(str2) && str2.equals(makeString(wifiConfiguration2.allowedKeyManagement, WifiConfiguration.KeyMgmt.strings))) {
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

    /* JADX WARN: Removed duplicated region for block: B:40:0x0210  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setNetworkConfiguration(int i, WifiConfiguration wifiConfiguration) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        ContentValues contentValues = new ContentValues();
        contentValues.put("networkSSID", NetworkUtils.removeDoubleQuotes(wifiConfiguration.SSID));
        contentValues.put("networkBSSID", wifiConfiguration.BSSID);
        contentValues.put("networkKeyMgmt", makeString(wifiConfiguration.allowedKeyManagement, WifiConfiguration.KeyMgmt.strings));
        contentValues.put("networkAllowedProtos", makeString(wifiConfiguration.allowedProtocols, WifiConfiguration.Protocol.strings));
        contentValues.put("networkAuthAlg", makeString(wifiConfiguration.allowedAuthAlgorithms, WifiConfiguration.AuthAlgorithm.strings));
        contentValues.put("networkAllowedPairwiseCiphers", makeString(wifiConfiguration.allowedPairwiseCiphers, WifiConfiguration.PairwiseCipher.strings));
        contentValues.put("networkAllowedGroupProtocols", makeString(wifiConfiguration.allowedGroupCiphers, WifiConfiguration.GroupCipher.strings));
        contentValues.put("networkPSK", wifiConfiguration.preSharedKey);
        contentValues.put("networkWEPKey1", wifiConfiguration.wepKeys[0]);
        int i2 = 1;
        contentValues.put("networkWEPKey2", wifiConfiguration.wepKeys[1]);
        contentValues.put("networkWEPKey3", wifiConfiguration.wepKeys[2]);
        contentValues.put("networkWEPKey4", wifiConfiguration.wepKeys[3]);
        contentValues.put("networkWEPKeyId", Integer.valueOf(wifiConfiguration.wepTxKeyIndex));
        if (wifiConfiguration.enterpriseConfig.getEapMethod() >= 0) {
            contentValues.put("networkEAP", EAP_STRINGS[wifiConfiguration.enterpriseConfig.getEapMethod()]);
        }
        String str6 = PHASE2_STRINGS[wifiConfiguration.enterpriseConfig.getPhase2Method()];
        if (str6.equals("NULL") || str6.equals("")) {
            contentValues.put("networkPhase2", "None");
        } else {
            contentValues.put("networkPhase2", str6.replace("auth=", ""));
        }
        contentValues.put("networkIdentity", wifiConfiguration.enterpriseConfig.getIdentity());
        contentValues.put("networkAnonymousId", wifiConfiguration.enterpriseConfig.getAnonymousIdentity());
        contentValues.put("networkPassword", wifiConfiguration.enterpriseConfig.getPassword());
        contentValues.put("networkClientCertification", wifiConfiguration.enterpriseConfig.getClientCertificateAlias());
        contentValues.put("networkPrivateKey", wifiConfiguration.enterpriseConfig.getClientCertificateAlias());
        String[] caCertificateAliases = wifiConfiguration.enterpriseConfig.getCaCertificateAliases();
        if (caCertificateAliases == null || caCertificateAliases.length <= 0) {
            contentValues.put("networkCaCertificate", (String) null);
        } else {
            contentValues.put("networkCaCertificate", caCertificateAliases[0]);
        }
        contentValues.put("networkStaticIpEnabled", Integer.valueOf(wifiConfiguration.getIpConfiguration().getIpAssignment() == IpConfiguration.IpAssignment.STATIC ? 1 : 0));
        StaticIpConfiguration staticIpConfiguration = wifiConfiguration.getIpConfiguration().getStaticIpConfiguration();
        if (staticIpConfiguration != null) {
            LinkAddress ipAddress = getIpAddress(staticIpConfiguration);
            if (ipAddress != null) {
                str5 = ipAddress.getAddress().getHostAddress();
                str2 = Inet4AddressUtils.intToInet4AddressHTL(Inet4AddressUtils.prefixLengthToV4NetmaskIntHTL(ipAddress.getPrefixLength())).getHostAddress();
            } else {
                str2 = null;
                str5 = null;
            }
            InetAddress gateway = staticIpConfiguration.getGateway();
            str3 = gateway != null ? gateway.getHostAddress() : null;
            Iterator<InetAddress> it = staticIpConfiguration.getDnsServers().iterator();
            str4 = it.hasNext() ? it.next().getHostAddress() : null;
            str = it.hasNext() ? it.next().getHostAddress() : null;
            r6 = str5;
        } else {
            str = null;
            str2 = null;
            str3 = null;
            str4 = null;
        }
        contentValues.put("networkStaticIp", r6);
        contentValues.put("networkStaticGateway", str3);
        contentValues.put("networkStaticPrimaryDns", str4);
        contentValues.put("networkStaticSecondaryDns", str);
        contentValues.put("networkStaticSubnetMask", str2);
        IpConfiguration.ProxySettings proxySettings = wifiConfiguration.getIpConfiguration().getProxySettings();
        contentValues.put("networkManualProxyEnabled", Integer.valueOf(proxySettings == IpConfiguration.ProxySettings.STATIC ? 1 : 0));
        if (proxySettings != IpConfiguration.ProxySettings.NONE) {
            if (proxySettings != IpConfiguration.ProxySettings.STATIC) {
                if (proxySettings == IpConfiguration.ProxySettings.PAC) {
                    i2 = 2;
                }
            }
            contentValues.put("networkProxyState", Integer.valueOf(i2));
            if (wifiConfiguration.getHttpProxy() != null) {
                contentValues.put("networProxyName", wifiConfiguration.getHttpProxy().getHost());
                contentValues.put("networkProxyPort", Integer.valueOf(wifiConfiguration.getHttpProxy().getPort()));
                contentValues.put("networkProxyExclList", convertArrayToString(wifiConfiguration.getHttpProxy().getExclusionList()));
                contentValues.put("networkProxyPacUrl", wifiConfiguration.getHttpProxy().getPacFileUrl().toString());
            }
            contentValues.put("networkEngineID", (Integer) 0);
            return this.mEdmStorageProvider.putValuesForAdminAndField("WIFI_CONF", i, "networkSSID", NetworkUtils.removeDoubleQuotes(wifiConfiguration.SSID), contentValues);
        }
        i2 = 0;
        contentValues.put("networkProxyState", Integer.valueOf(i2));
        if (wifiConfiguration.getHttpProxy() != null) {
        }
        contentValues.put("networkEngineID", (Integer) 0);
        return this.mEdmStorageProvider.putValuesForAdminAndField("WIFI_CONF", i, "networkSSID", NetworkUtils.removeDoubleQuotes(wifiConfiguration.SSID), contentValues);
    }

    public final WifiConfiguration getNetworkConfiguration(int i, String str) {
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("WIFI_CONF", new String[]{"adminUid", "networkSSID"}, new String[]{String.valueOf(i), NetworkUtils.removeDoubleQuotes(str)}, new String[]{"networkSSID", "networkKeyMgmt", "networkProxyState", "networProxyName", "networkProxyPort", "networkProxyExclList", "networkProxyPacUrl"});
        if (dataByFields == null || dataByFields.isEmpty()) {
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
                        WifiConfiguration networkFromWifiModule = getNetworkFromWifiModule(asString, asString2);
                        Integer asInteger = contentValues.getAsInteger("networkProxyState");
                        if (asInteger != null && networkFromWifiModule.getHttpProxy() != null) {
                            Integer asInteger2 = contentValues.getAsInteger("networkProxyPort");
                            networkFromWifiModule.setHttpProxy(NetworkUtils.convertToProxyInfo(createProxyProperties(contentValues.getAsString("networProxyName"), asInteger2 != null ? asInteger2.intValue() : 0, contentValues.getAsString("networkProxyPacUrl"), null, Arrays.asList(convertStringToArray(contentValues.getAsString("networkProxyExclList"))), asInteger.intValue())));
                        }
                        return networkFromWifiModule;
                    }
                }
            }
        }
        Log.e("WifiPolicyService", "getNetworkConfiguration - network not found in database - callingUid: " + i);
        return null;
    }

    public final StaticIpConfiguration setIpAndSubnetMask(StaticIpConfiguration staticIpConfiguration, String str, String str2) {
        int i = 24;
        if (!TextUtils.isEmpty(str)) {
            try {
                InetAddress inetAddr = getInetAddr(str);
                if (!TextUtils.isEmpty(str2)) {
                    i = computeprefixLength(getInetAddr(str2));
                } else if (getIpAddress(staticIpConfiguration) != null) {
                    i = getIpAddress(staticIpConfiguration).getPrefixLength();
                }
                return createStaticIpConfigurationFromSourceAndIpAddress(staticIpConfiguration, new LinkAddress(inetAddr, i));
            } catch (IllegalArgumentException e) {
                Log.e("WifiPolicyService", "", e);
                return null;
            }
        }
        InetAddress inetAddr2 = getInetAddr("192.168.1.100");
        if (getIpAddress(staticIpConfiguration) != null) {
            inetAddr2 = getIpAddress(staticIpConfiguration).getAddress();
            i = getIpAddress(staticIpConfiguration).getPrefixLength();
        }
        if (str2 != null) {
            i = computeprefixLength(getInetAddr(str2));
        }
        return createStaticIpConfigurationFromSourceAndIpAddress(staticIpConfiguration, new LinkAddress(inetAddr2, i));
    }

    public final StaticIpConfiguration createStaticIpConfigurationFromSourceAndIpAddress(StaticIpConfiguration staticIpConfiguration, LinkAddress linkAddress) {
        StaticIpConfiguration.Builder domains = new StaticIpConfiguration.Builder().setIpAddress(linkAddress).setGateway(staticIpConfiguration.getGateway()).setDomains(staticIpConfiguration.getDomains());
        if (staticIpConfiguration.getDnsServers() != null) {
            domains.setDnsServers(staticIpConfiguration.getDnsServers());
        }
        return domains.build();
    }

    public final StaticIpConfiguration createStaticIpConfigurationFromSourceAndGateway(StaticIpConfiguration staticIpConfiguration, InetAddress inetAddress) {
        StaticIpConfiguration.Builder domains = new StaticIpConfiguration.Builder().setIpAddress(staticIpConfiguration.getIpAddress()).setGateway(inetAddress).setDomains(staticIpConfiguration.getDomains());
        if (staticIpConfiguration.getDnsServers() != null) {
            domains.setDnsServers(staticIpConfiguration.getDnsServers());
        }
        return domains.build();
    }

    public final LinkAddress getIpAddress(StaticIpConfiguration staticIpConfiguration) {
        return staticIpConfiguration.getIpAddress();
    }

    public final InetAddress getInetAddr(String str) {
        return InetAddresses.parseNumericAddress(str);
    }

    public final StaticIpConfiguration setGateway(StaticIpConfiguration staticIpConfiguration, String str) {
        if (TextUtils.isEmpty(str)) {
            return staticIpConfiguration.getGateway() == null ? createStaticIpConfigurationFromSourceAndGateway(staticIpConfiguration, getInetAddr("192.168.1.1")) : staticIpConfiguration;
        }
        try {
            return createStaticIpConfigurationFromSourceAndGateway(staticIpConfiguration, getInetAddr(str));
        } catch (IllegalArgumentException e) {
            Log.e("WifiPolicyService", "", e);
            return null;
        }
    }

    public final boolean setDns1AndDns2(StaticIpConfiguration staticIpConfiguration, String str, String str2) {
        InetAddress inetAddr;
        if (!TextUtils.isEmpty(str)) {
            try {
                inetAddr = getInetAddr(str);
            } catch (IllegalArgumentException e) {
                Log.e("WifiPolicyService", "", e);
                return false;
            }
        } else {
            inetAddr = getInetAddr("8.8.8.8");
            if (!staticIpConfiguration.getDnsServers().isEmpty()) {
                inetAddr = staticIpConfiguration.getDnsServers().get(0);
            }
        }
        if (inetAddr != null) {
            if (staticIpConfiguration.getDnsServers().isEmpty()) {
                staticIpConfiguration.getDnsServers().add(inetAddr);
            } else {
                staticIpConfiguration.getDnsServers().set(0, inetAddr);
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            try {
                InetAddress inetAddr2 = getInetAddr(str2);
                if (staticIpConfiguration.getDnsServers().size() == 1) {
                    staticIpConfiguration.getDnsServers().add(inetAddr2);
                } else if (staticIpConfiguration.getDnsServers().size() == 2) {
                    staticIpConfiguration.getDnsServers().set(1, inetAddr2);
                }
            } catch (IllegalArgumentException e2) {
                Log.e("WifiPolicyService", "", e2);
                return false;
            }
        }
        return true;
    }

    public final int computeprefixLength(InetAddress inetAddress) {
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

    public boolean setWifiApSetting(ContextInfo contextInfo, String str, String str2, String str3) {
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
        if (semWifiManager != null) {
            if (str2.equals("SAE") && !semWifiManager.isWifiApWpa3Supported()) {
                Log.e("WifiPolicyService", "Wi-Fi AP WPA3 is not supported");
                return false;
            }
        } else {
            Log.d("WifiPolicyService", "semWifiManager is null");
        }
        if (!isOpenWifiApAllowed(contextInfo) && str2.equals("Open")) {
            Log.e("WifiPolicyService", "Security Type (" + str2 + ") is not valid");
            return false;
        }
        if ((str2.equals("WPA2_PSK") || str2.equals("SAE")) && (str3 == null || str3.length() < 8 || str3.length() > 63)) {
            Log.e("WifiPolicyService", "Password does not meet requirements for " + str2 + " security type");
            return false;
        }
        this.mIsAPSettingFromAdmin = true;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SoftApConfiguration.Builder builder = new SoftApConfiguration.Builder(this.mWifiAdapter.getSoftApConfiguration());
            builder.setSsid(str);
            builder.setHiddenSsid(false);
            if (str2.equals("WPA2_PSK")) {
                builder.setPassphrase(str3, 1);
            } else if (str2.equals("SAE")) {
                builder.setPassphrase(str3, 3);
            } else {
                builder.setPassphrase((String) null, 0);
            }
            int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "wifi_ap_last_2g_channel", 0);
            if (i == 0) {
                builder.setBand(1);
            } else {
                builder.setChannel(i, 1);
            }
            this.mWifiAdapter.setSoftApConfiguration(builder.build());
            int wifiApState = this.mWifiAdapter.getWifiApState();
            if (wifiApState == 13 || wifiApState == 12) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.mWifiAdapter.resetSoftAp();
            }
            ApplicationRestrictionsManager applicationRestrictionsManager = ApplicationRestrictionsManager.getInstance(this.mContext);
            if (applicationRestrictionsManager != null && !applicationRestrictionsManager.isSettingPolicyApplied()) {
                getApplicationPolicy().stopApp(new ContextInfo(Binder.getCallingUid()), "com.android.settings");
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mIsAPSettingFromAdmin = false;
        }
    }

    public WifiConfiguration getWifiApSetting(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SoftApConfiguration softApConfiguration = this.mWifiAdapter.getSoftApConfiguration();
            WifiConfiguration wifiConfiguration = null;
            if (softApConfiguration != null) {
                WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
                wifiConfiguration2.SSID = softApConfiguration.getSsid();
                int securityType = softApConfiguration.getSecurityType();
                if (securityType == 0) {
                    wifiConfiguration2.allowedKeyManagement.set(0);
                } else if (securityType == 1) {
                    wifiConfiguration2.allowedKeyManagement.set(4);
                } else if (securityType == 3) {
                    wifiConfiguration2.allowedKeyManagement.set(8);
                } else {
                    Log.e("WifiPolicyService", "Convert fail, unsupported security type :" + softApConfiguration.getSecurityType());
                    return null;
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

    public boolean allowWifiApSettingUserModification(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "allowWifiApSettingUserModification - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", enable: " + z);
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowWifiApSettingModification", z);
    }

    public boolean isWifiApSettingUserModificationAllowed(ContextInfo contextInfo) {
        boolean z = true;
        if (this.mIsAPSettingFromAdmin) {
            return true;
        }
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowWifiApSettingModification").iterator();
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
        Log.i("WifiPolicyService", "isWifiApSettingUserModificationAllowed: " + z);
        return z;
    }

    public boolean allowOpenWifiAp(ContextInfo contextInfo, boolean z) {
        int wifiApState;
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "allowOpenWifiAp - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", enable: " + z);
        if (!z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WifiConfiguration wifiApConfiguration = this.mWifiManager.getWifiApConfiguration();
                if (wifiApConfiguration != null && wifiApConfiguration.allowedKeyManagement.get(0) && ((wifiApState = this.mWifiManager.getWifiApState()) == 13 || wifiApState == 12)) {
                    SemWifiManager semWifiManager = (SemWifiManager) this.mContext.getSystemService("sem_wifi");
                    semWifiManager.setWifiApEnabled(semWifiManager.getSoftApConfiguration(), false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowOpenWifi", z);
    }

    public boolean isOpenWifiApAllowed(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowOpenWifi").iterator();
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
        Log.i("WifiPolicyService", "isOpenWifiApAllowed: " + z);
        return z;
    }

    public final synchronized Map loadWifiNetworkSsidBlackWhitelist(int i, boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        List<ContentValues> valuesList = this.mEdmStorageProvider.getValuesList("WIFI_SSID_BLACK_WHITE_LIST", new String[]{"WIFI_LIST_TYPE", "WIFI_SSID"}, contentValues);
        TreeSet treeSet = new TreeSet();
        if (z) {
            Iterator it = getBlockedList(i).iterator();
            while (it.hasNext()) {
                treeSet.add(NetworkUtils.removeDoubleQuotes((String) it.next()));
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("BLACKLIST", treeSet);
        hashMap.put("WHITELIST", new TreeSet());
        if (valuesList.isEmpty()) {
            Log.i("WifiPolicyService", "Black/white list table is empty - uid: " + i);
            return hashMap;
        }
        for (ContentValues contentValues2 : valuesList) {
            ((Set) hashMap.get(contentValues2.getAsString("WIFI_LIST_TYPE"))).add(contentValues2.getAsString("WIFI_SSID"));
        }
        return hashMap;
    }

    public boolean addWifiSsidToBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "addWifiSsidToBlackList - not a valid caller, aborting!");
            return false;
        }
        List<String> validateSSIDList = validateSSIDList(list);
        if (validateSSIDList == null || validateSSIDList.isEmpty()) {
            Log.e("WifiPolicyService", "addWifiSsidToBlackList - failed with invalid request");
            return false;
        }
        boolean z2 = true;
        for (String str : validateSSIDList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("WIFI_SSID", str);
            contentValues.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            contentValues.put("WIFI_LIST_TYPE", "BLACKLIST");
            z2 = z2 && this.mEdmStorageProvider.putValuesNoUpdate("WIFI_SSID_BLACK_WHITE_LIST", contentValues);
            if (z2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has added SSID %s to the restriction blacklist.", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return z2;
        }
        Log.i("WifiPolicyService", "addWifiSsidToBlackList - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", list size: " + validateSSIDList.size());
        if (z2 && setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, true)) {
            z = true;
        }
        return z;
    }

    public boolean removeWifiSsidFromBlackList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "removeWifiSsidFromBlackList - not a valid caller, aborting!");
            return false;
        }
        List<String> validateSSIDList = validateSSIDList(list);
        if (validateSSIDList == null || validateSSIDList.isEmpty()) {
            Log.e("WifiPolicyService", "removeWifiSsidFromBlackList - failed with invalid request");
            return false;
        }
        boolean z2 = true;
        for (String str : validateSSIDList) {
            z2 = z2 && this.mEdmStorageProvider.deleteDataByFields("WIFI_SSID_BLACK_WHITE_LIST", new String[]{"adminUid", "WIFI_SSID", "WIFI_LIST_TYPE"}, new String[]{String.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str, "BLACKLIST"});
            if (z2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has removed SSID %s from the restriction blacklist.", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return z2;
        }
        Log.i("WifiPolicyService", "removeWifiSsidFromBlackList - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", list size: " + validateSSIDList.size());
        if (z2 && setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, true)) {
            z = true;
        }
        return z;
    }

    public boolean clearWifiSsidBlackList(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "clearWifiSsidBlackList - not a valid caller, aborting!");
            return false;
        }
        boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("WIFI_SSID_BLACK_WHITE_LIST", new String[]{"adminUid", "WIFI_LIST_TYPE"}, new String[]{String.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), "BLACKLIST"});
        if (deleteDataByFields) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has removed all SSIDs from the restriction blacklist.", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return deleteDataByFields;
        }
        Log.i("WifiPolicyService", "clearWifiSsidBlackList - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (deleteDataByFields && setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, true)) {
            z = true;
        }
        return z;
    }

    public List getAllWifiSsidBlackLists(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        TreeSet<Integer> treeSet = new TreeSet(this.mEdmStorageProvider.getIntList("WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap(treeSet.size());
        for (Integer num : treeSet) {
            hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), false));
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet().size());
        for (Map.Entry entry : hashMap.entrySet()) {
            WifiControlInfo wifiControlInfo = new WifiControlInfo();
            wifiControlInfo.adminPackageName = getPackageNameForUid(((Integer) entry.getKey()).intValue());
            wifiControlInfo.entries = new ArrayList((Collection) ((Map) entry.getValue()).get("BLACKLIST"));
            arrayList.add(wifiControlInfo);
        }
        Log.i("WifiPolicyService", "getAllWifiSsidBlackLists: list size = " + arrayList.size());
        return arrayList;
    }

    public final List getAllWifiSsidBlackListsForDump() {
        TreeSet<Integer> treeSet = new TreeSet(this.mEdmStorageProvider.getIntList("WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap(treeSet.size());
        for (Integer num : treeSet) {
            hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), true));
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet().size());
        for (Map.Entry entry : hashMap.entrySet()) {
            WifiControlInfo wifiControlInfo = new WifiControlInfo();
            wifiControlInfo.adminPackageName = getPackageNameForUid(((Integer) entry.getKey()).intValue());
            wifiControlInfo.entries = new ArrayList((Collection) ((Map) entry.getValue()).get("BLACKLIST"));
            arrayList.add(wifiControlInfo);
        }
        return arrayList;
    }

    public boolean addWifiSsidToWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "addWifiSsidToWhiteList - not a valid caller, aborting!");
            return false;
        }
        List<String> validateSSIDList = validateSSIDList(list);
        if (validateSSIDList == null || validateSSIDList.isEmpty()) {
            Log.e("WifiPolicyService", "addWifiSsidToWhiteList - failed with invalid request");
            return false;
        }
        boolean z2 = true;
        for (String str : validateSSIDList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("WIFI_SSID", str);
            contentValues.put("adminUid", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            contentValues.put("WIFI_LIST_TYPE", "WHITELIST");
            z2 = z2 && this.mEdmStorageProvider.putValuesNoUpdate("WIFI_SSID_BLACK_WHITE_LIST", contentValues);
            if (z2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has added SSID %s to the restriction whitelist.", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return z2;
        }
        Log.i("WifiPolicyService", "addWifiSsidToWhiteList - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", list size: " + validateSSIDList.size());
        if (z2 && setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, true)) {
            z = true;
        }
        return z;
    }

    public boolean removeWifiSsidFromWhiteList(ContextInfo contextInfo, List list) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "removeWifiSsidFromWhiteList - not a valid caller, aborting!");
            return false;
        }
        List<String> validateSSIDList = validateSSIDList(list);
        if (validateSSIDList == null || validateSSIDList.isEmpty()) {
            Log.e("WifiPolicyService", "removeWifiSsidFromWhiteList - failed with invalid request");
            return false;
        }
        boolean z2 = true;
        for (String str : validateSSIDList) {
            z2 = z2 && this.mEdmStorageProvider.deleteDataByFields("WIFI_SSID_BLACK_WHITE_LIST", new String[]{"adminUid", "WIFI_SSID", "WIFI_LIST_TYPE"}, new String[]{String.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str, "WHITELIST"});
            if (z2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has removed SSID %s from the restriction whitelist.", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), str), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return z2;
        }
        Log.i("WifiPolicyService", "removeWifiSsidFromWhiteList - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", list size: " + validateSSIDList.size());
        if (z2 && setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, true)) {
            z = true;
        }
        return z;
    }

    public boolean clearWifiSsidWhiteList(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        boolean z = false;
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "clearWifiSsidWhiteList - not a valid caller, aborting!");
            return false;
        }
        boolean deleteDataByFields = this.mEdmStorageProvider.deleteDataByFields("WIFI_SSID_BLACK_WHITE_LIST", new String[]{"adminUid", "WIFI_LIST_TYPE"}, new String[]{String.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), "WHITELIST"});
        if (deleteDataByFields) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has removed all SSIDs from the restriction whitelist.", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (!isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission.mCallerUid)) {
            return deleteDataByFields;
        }
        Log.i("WifiPolicyService", "clearWifiSsidWhiteList - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (deleteDataByFields && setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, true)) {
            z = true;
        }
        return z;
    }

    public List getAllWifiSsidWhiteLists(ContextInfo contextInfo) {
        enforceOwnerOnlyAndWifiPermission(contextInfo);
        TreeSet<Integer> treeSet = new TreeSet(this.mEdmStorageProvider.getIntList("WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap(treeSet.size());
        for (Integer num : treeSet) {
            hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), false));
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet().size());
        for (Map.Entry entry : hashMap.entrySet()) {
            WifiControlInfo wifiControlInfo = new WifiControlInfo();
            wifiControlInfo.adminPackageName = getPackageNameForUid(((Integer) entry.getKey()).intValue());
            wifiControlInfo.entries = new ArrayList((Collection) ((Map) entry.getValue()).get("WHITELIST"));
            arrayList.add(wifiControlInfo);
        }
        Log.i("WifiPolicyService", "getAllWifiSsidWhiteLists: list size = " + arrayList.size());
        return arrayList;
    }

    public final List getAllWifiSsidWhiteListsForDump() {
        TreeSet<Integer> treeSet = new TreeSet(this.mEdmStorageProvider.getIntList("WIFI_SSID_BLACK_WHITE_LIST", "adminUid"));
        if (treeSet.isEmpty()) {
            return new ArrayList();
        }
        HashMap hashMap = new HashMap(treeSet.size());
        for (Integer num : treeSet) {
            hashMap.put(num, loadWifiNetworkSsidBlackWhitelist(num.intValue(), false));
        }
        ArrayList arrayList = new ArrayList(hashMap.keySet().size());
        for (Map.Entry entry : hashMap.entrySet()) {
            WifiControlInfo wifiControlInfo = new WifiControlInfo();
            wifiControlInfo.adminPackageName = getPackageNameForUid(((Integer) entry.getKey()).intValue());
            wifiControlInfo.entries = new ArrayList((Collection) ((Map) entry.getValue()).get("WHITELIST"));
            arrayList.add(wifiControlInfo);
        }
        return arrayList;
    }

    public boolean activateWifiSsidRestriction(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Pair callerInfoFromUid = getCallerInfoFromUid(enforceOwnerOnlyAndWifiPermission.mCallerUid);
        if (callerInfoFromUid == null) {
            Log.e("WifiPolicyService", "activateWifiSsidRestriction - not a valid caller, aborting!");
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "wifiSsidRestriction", z);
        if (!putBoolean) {
            Log.e("WifiPolicyService", "activateWifiSsidRestriction - fail to store value to database");
            return putBoolean;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has changed Wifi SSID restriction to %s", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Log.i("WifiPolicyService", "activateWifiSsidRestriction - caller uid: " + callerInfoFromUid.first + ", enforced caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", enable = " + z);
            return setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), enforceOwnerOnlyAndWifiPermission.mCallerUid, z);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setSsidAllowDenyList(ComponentName componentName, int i, int i2, boolean z) {
        if (componentName == null) {
            Log.e("WifiPolicyService", "setSsidAllowDenyList - component name is null");
            return false;
        }
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            Log.e("WifiPolicyService", "setSsidAllowDenyList - fail to retrieve dpmi object");
            return false;
        }
        WifiSsidPolicy wifiSsidPolicy = null;
        if (z) {
            Map loadWifiNetworkSsidBlackWhitelist = loadWifiNetworkSsidBlackWhitelist(i2, true);
            Set set = (Set) loadWifiNetworkSsidBlackWhitelist.get("WHITELIST");
            Set set2 = (Set) loadWifiNetworkSsidBlackWhitelist.get("BLACKLIST");
            if (!set.isEmpty()) {
                if (!set.contains("*")) {
                    ArraySet arraySet = new ArraySet();
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        arraySet.add(WifiSsid.fromBytes(((String) it.next()).getBytes(StandardCharsets.UTF_8)));
                    }
                    wifiSsidPolicy = new WifiSsidPolicy(0, arraySet);
                }
                devicePolicyManagerInternal.setWifiSsidPolicy(componentName, wifiSsidPolicy, i);
                Log.i("WifiPolicyService", "setWifiSsidPolicy(ALLOWLIST) - who: " + componentName + ", userId: " + i + ", enforced caller uid: " + i2);
            } else if (!set2.isEmpty()) {
                if (!set2.contains("*")) {
                    ArraySet arraySet2 = new ArraySet();
                    Iterator it2 = set2.iterator();
                    while (it2.hasNext()) {
                        arraySet2.add(WifiSsid.fromBytes(((String) it2.next()).getBytes(StandardCharsets.UTF_8)));
                    }
                    wifiSsidPolicy = new WifiSsidPolicy(1, arraySet2);
                }
                devicePolicyManagerInternal.setWifiSsidPolicy(componentName, wifiSsidPolicy, i);
                Log.i("WifiPolicyService", "setWifiSsidPolicy(DENYLIST) - who: " + componentName + ", userId: " + i + ", enforced caller uid: " + i2);
            } else {
                devicePolicyManagerInternal.setWifiSsidPolicy(componentName, (WifiSsidPolicy) null, i);
                Log.i("WifiPolicyService", "setWifiSsidPolicy(null) - who: " + componentName + ", userId: " + i + ", enforced caller uid: " + i2 + ", ssid restriction is activated but there is no allow/deny list in database");
            }
        } else {
            devicePolicyManagerInternal.setWifiSsidPolicy(componentName, (WifiSsidPolicy) null, i);
            Log.i("WifiPolicyService", "setWifiSsidPolicy(null) - who: " + componentName + ", userId: " + i + ", enforced caller uid: " + i2 + ", ssid restriction is not activated");
        }
        return true;
    }

    public boolean isWifiSsidRestrictionActive(ContextInfo contextInfo) {
        return isWifiSsidRestrictionActive(enforceOwnerOnlyAndWifiPermission(contextInfo).mCallerUid);
    }

    public final boolean isWifiSsidRestrictionActive(int i) {
        try {
            boolean z = this.mEdmStorageProvider.getBoolean(i, "WIFI", "wifiSsidRestriction");
            Log.i("WifiPolicyService", "isWifiSsidRestrictionActive(" + i + "): " + z);
            return z;
        } catch (SettingNotFoundException e) {
            Log.e("WifiPolicyService", e.getMessage());
            return false;
        }
    }

    public final String getPackageNameForUid(int i) {
        return this.mEdmStorageProvider.getPackageNameForUid(i);
    }

    public boolean setWifiStateChangeAllowed(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndWifiPermission = enforceOwnerOnlyAndWifiPermission(contextInfo);
        Log.i("WifiPolicyService", "setWifiStateChangeAllowed - caller uid: " + enforceOwnerOnlyAndWifiPermission.mCallerUid + ", allow: " + z);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndWifiPermission.mCallerUid, "WIFI", "allowWifiStateChanges", z);
        if (!putBoolean) {
            Log.e("WifiPolicyService", "setWifiStateChangeAllowed - fail to store value to database");
            return putBoolean;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "WifiPolicy", String.format("Admin %d has changed Wifi state change allowed to %s", Integer.valueOf(enforceOwnerOnlyAndWifiPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndWifiPermission.mCallerUid));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            setChangeWifiStateUserRestriction(!isWifiStateChangeAllowed(null));
            int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndWifiPermission);
            if (callingOrCurrentUserId == 0) {
                setWifiStateChangeAllowedSystemUI(callingOrCurrentUserId, isWifiStateChangeAllowed(enforceOwnerOnlyAndWifiPermission));
            }
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
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

    public boolean isWifiStateChangeAllowed(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowWifiStateChanges").iterator();
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
        Log.i("WifiPolicyService", "isWifiStateChangeAllowed: " + z);
        return z;
    }

    public final WifiConfiguration updateNetworkProxyState(WifiConfiguration wifiConfiguration, int i) {
        if (TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        IpConfiguration ipConfiguration = wifiConfiguration.getIpConfiguration();
        if (i == 1) {
            ipConfiguration.setProxySettings(IpConfiguration.ProxySettings.STATIC);
        } else if (i == 0) {
            ipConfiguration.setProxySettings(IpConfiguration.ProxySettings.NONE);
        } else {
            if (i != 2) {
                return null;
            }
            ipConfiguration.setProxySettings(IpConfiguration.ProxySettings.PAC);
        }
        wifiConfiguration.setIpConfiguration(ipConfiguration);
        return wifiConfiguration;
    }

    public final WifiConfiguration updateNetworkProxyHostName(WifiConfiguration wifiConfiguration, String str) {
        ProxyInfo buildDirectProxy;
        if (TextUtils.isEmpty(wifiConfiguration.SSID) || TextUtils.isEmpty(str)) {
            return null;
        }
        if (!HOSTNAME_PATTERN.matcher(str).matches()) {
            Log.e("WifiPolicyService", "Invalid host name provided for proxy, doesn't match expected format");
            return null;
        }
        ProxyInfo httpProxy = wifiConfiguration.getHttpProxy();
        if (httpProxy == null) {
            buildDirectProxy = ProxyInfo.buildDirectProxy(str, 0);
        } else if (httpProxy.getExclusionList() == null || httpProxy.getExclusionList().length == 0) {
            buildDirectProxy = ProxyInfo.buildDirectProxy(str, httpProxy.getPort());
        } else {
            buildDirectProxy = ProxyInfo.buildDirectProxy(str, httpProxy.getPort(), Arrays.asList(httpProxy.getExclusionList()));
        }
        return updateNetworkProxyInfo(wifiConfiguration, buildDirectProxy);
    }

    public final WifiConfiguration updateNetworkProxyPort(WifiConfiguration wifiConfiguration, int i) {
        ProxyInfo buildDirectProxy;
        if (TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        if (wifiConfiguration.getIpConfiguration().getProxySettings() == IpConfiguration.ProxySettings.STATIC && (i <= 0 || i > 65535)) {
            Log.e("WifiPolicyService", "Invalid port provided for proxy");
            return null;
        }
        ProxyInfo httpProxy = wifiConfiguration.getHttpProxy();
        if (httpProxy == null) {
            buildDirectProxy = ProxyInfo.buildDirectProxy("192.168.1.100", i);
        } else {
            String host = TextUtils.isEmpty(httpProxy.getHost()) ? "192.168.1.100" : httpProxy.getHost();
            if (httpProxy.getExclusionList() != null && httpProxy.getExclusionList().length > 0) {
                buildDirectProxy = ProxyInfo.buildDirectProxy(host, i, Arrays.asList(httpProxy.getExclusionList()));
            } else {
                buildDirectProxy = ProxyInfo.buildDirectProxy(host, i);
            }
        }
        return updateNetworkProxyInfo(wifiConfiguration, buildDirectProxy);
    }

    public final WifiConfiguration updateUrlForNetworkProxyBypassList(WifiConfiguration wifiConfiguration, String str) {
        ProxyInfo buildDirectProxy;
        if (TextUtils.isEmpty(wifiConfiguration.SSID) || TextUtils.isEmpty(str)) {
            return null;
        }
        List<String> asList = Arrays.asList(str.split(","));
        Iterator it = asList.iterator();
        while (it.hasNext()) {
            if (!EXCLLIST_PATTERN.matcher((String) it.next()).matches()) {
                return null;
            }
        }
        List arrayList = new ArrayList();
        ProxyInfo httpProxy = wifiConfiguration.getHttpProxy();
        if (httpProxy != null) {
            String[] exclusionList = httpProxy.getExclusionList();
            if (exclusionList != null) {
                arrayList = Arrays.asList(exclusionList);
            }
            arrayList = new ArrayList(arrayList);
        }
        for (String str2 : asList) {
            if (!arrayList.contains(str2)) {
                arrayList.add(str2);
            }
        }
        if (httpProxy == null) {
            buildDirectProxy = ProxyInfo.buildDirectProxy("192.168.1.100", 0, arrayList);
        } else {
            buildDirectProxy = ProxyInfo.buildDirectProxy(httpProxy.getHost(), httpProxy.getPort(), arrayList);
        }
        return updateNetworkProxyInfo(wifiConfiguration, buildDirectProxy);
    }

    public final String convertArrayToString(String[] strArr) {
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

    public final String[] convertStringToArray(String str) {
        return (str == null || str.length() == 0) ? new String[0] : str.split(",");
    }

    public final WifiConfiguration updateNetworkProxyPacFileUrl(WifiConfiguration wifiConfiguration, String str) {
        if (TextUtils.isEmpty(wifiConfiguration.SSID)) {
            return null;
        }
        return wifiConfiguration.getIpConfiguration().getProxySettings() != IpConfiguration.ProxySettings.STATIC ? updateNetworkProxyInfo(wifiConfiguration, ProxyInfo.buildPacProxy(Uri.parse(str))) : wifiConfiguration;
    }

    public final List validateSSIDList(List list) {
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
            if (isvalidSSIDName(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final boolean isvalidSSIDName(String str) {
        if (str != null && !TextUtils.isEmpty(str)) {
            return true;
        }
        Log.i("WifiPolicyService", "isvalidSSIDName() : SSID is invalid");
        return false;
    }

    public final void updateSystemUIMonitor(int i) {
        setWifiStateChangeAllowedSystemUI(i, isWifiStateChangeAllowed(null));
        setWifiAllowedSystemUI(i, isWifiAllowed(null, false));
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

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump WifiPolicyService");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[WifiPolicyService]" + System.lineSeparator());
        sb.append("  mWifiAllowed : " + isWifiAllowed(null, false) + System.lineSeparator());
        sb.append("  mAllowStateChange : " + isWifiStateChangeAllowed(null) + System.lineSeparator());
        sb.append("  mAllowAutomaticConnections : " + getAutomaticConnectionToWifi(null) + System.lineSeparator());
        sb.append("  mAllowUserChanges : " + getAllowUserPolicyChanges(null) + System.lineSeparator());
        sb.append("  mPromptCredentialsEnabled : " + getPromptCredentialsEnabled(null) + System.lineSeparator());
        sb.append("  mAllowUserProfiles : " + getAllowUserProfiles(null, false, 0) + System.lineSeparator());
        sb.append("  ssidBlockList : " + getAllSsidBlockList() + System.lineSeparator());
        sb.append("  ssidAllowList : " + getAllSsidAllowList() + System.lineSeparator());
        printWriter.write(sb.toString());
    }

    public final String getAllSsidBlockList() {
        StringBuilder sb = new StringBuilder();
        List allWifiSsidBlackListsForDump = getAllWifiSsidBlackListsForDump();
        if (allWifiSsidBlackListsForDump == null || allWifiSsidBlackListsForDump.size() == 0) {
            sb.append("No item found");
        } else {
            for (int i = 0; i < allWifiSsidBlackListsForDump.size(); i++) {
                sb.append(((WifiControlInfo) allWifiSsidBlackListsForDump.get(i)).adminPackageName + " : " + ((WifiControlInfo) allWifiSsidBlackListsForDump.get(i)).entries + "  ");
            }
        }
        return sb.toString();
    }

    public final String getAllSsidAllowList() {
        StringBuilder sb = new StringBuilder();
        List allWifiSsidWhiteListsForDump = getAllWifiSsidWhiteListsForDump();
        if (allWifiSsidWhiteListsForDump == null || allWifiSsidWhiteListsForDump.size() == 0) {
            sb.append("No item found");
        } else {
            for (int i = 0; i < allWifiSsidWhiteListsForDump.size(); i++) {
                sb.append(((WifiControlInfo) allWifiSsidWhiteListsForDump.get(i)).adminPackageName + " : " + ((WifiControlInfo) allWifiSsidWhiteListsForDump.get(i)).entries + "  ");
            }
        }
        return sb.toString();
    }

    public final WifiConfiguration updateNetworkProxyInfo(WifiConfiguration wifiConfiguration, ProxyInfo proxyInfo) {
        IpConfiguration ipConfiguration = wifiConfiguration.getIpConfiguration();
        if (ipConfiguration == null) {
            ipConfiguration = new IpConfiguration();
        }
        ipConfiguration.setHttpProxy(proxyInfo);
        wifiConfiguration.setIpConfiguration(ipConfiguration);
        return wifiConfiguration;
    }

    public final boolean isWifiEphemeralOrPasspoint(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration != null) {
            return wifiConfiguration.isEphemeral() || wifiConfiguration.isPasspoint();
        }
        return false;
    }

    public boolean allowWifiScanning(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "WIFI", "allowWifiScanning", z);
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

    public boolean isWifiScanningAllowed(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("WIFI", "allowWifiScanning").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public static boolean isWifiOnly(Context context) {
        if (((TelephonyManager) context.getSystemService(TelephonyManager.class)) == null) {
            return false;
        }
        return !r1.isDataCapable();
    }

    public int addNetworkWithRandomizationState(WifiConfiguration wifiConfiguration, boolean z) {
        if (wifiConfiguration == null) {
            Log.i("WifiPolicyService", "addNetworkWithRandomizationState() : config is null");
            return -1;
        }
        enforceKnoxExceptionPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                wifiConfiguration.macRandomizationSetting = 0;
            }
            return this.mWifiManager.addNetwork(wifiConfiguration);
        } catch (Exception e) {
            Log.e("WifiPolicyService", "addNetworkWithRandomizationState() failed", e);
            return -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void initializeWifiProxyListCache() {
        this.mLocalProxyManager.updateWifiProxy(getWifiProxyFromDatabase());
        this.mLocalProxyManager.updateWifiBackCompatCredentialsCache(getBackwardsProxyCredentialsFromDatabase());
    }

    public final Map getWifiProxyFromDatabase() {
        ProxyInfo buildDirectProxy;
        ArrayMap arrayMap = new ArrayMap();
        List<ContentValues> values = this.mEdmStorageProvider.getValues("WifiProxyTable", new String[]{"networkSSID", "host", "port", "pacfile", "exclusion"}, (ContentValues) null);
        if (values == null) {
            return Collections.EMPTY_MAP;
        }
        for (ContentValues contentValues : values) {
            String asString = contentValues.getAsString("networkSSID");
            String asString2 = contentValues.getAsString("host");
            int intValue = contentValues.getAsInteger("port") == null ? 0 : contentValues.getAsInteger("port").intValue();
            String asString3 = contentValues.getAsString("pacfile");
            String asString4 = contentValues.getAsString("exclusion");
            if (TextUtils.isEmpty(asString2) && TextUtils.isEmpty(asString3)) {
                return Collections.EMPTY_MAP;
            }
            if (!TextUtils.isEmpty(asString3)) {
                buildDirectProxy = ProxyInfo.buildPacProxy(Uri.parse(asString3));
            } else {
                buildDirectProxy = ProxyInfo.buildDirectProxy(asString2, intValue, Arrays.asList(convertStringToArray(asString4)));
            }
            if (buildDirectProxy.isValid()) {
                ProxyProperties proxyProperties = new ProxyProperties();
                proxyProperties.setHostname(asString2);
                proxyProperties.setPortNumber(intValue);
                List authConfigFromDb = getAuthConfigFromDb(asString);
                if (!authConfigFromDb.isEmpty()) {
                    proxyProperties.setAuthConfigList(authConfigFromDb);
                }
                proxyProperties.setExclusionList(Arrays.asList(buildDirectProxy.getExclusionList()));
                proxyProperties.setPacFileUrl(asString3);
                arrayMap.put(asString, proxyProperties);
            } else {
                Log.d("WifiPolicyService", "Invalid proxy properties, ignoring: " + buildDirectProxy.toString());
            }
        }
        return arrayMap;
    }

    public final Map getBackwardsProxyCredentialsFromDatabase() {
        List<ContentValues> values = this.mEdmStorageProvider.getValues("WIFI_CONF", new String[]{"networkProxyUsername", "networkProxyPassword", "networkSSID"}, (ContentValues) null);
        if (values == null) {
            return Collections.EMPTY_MAP;
        }
        ArrayMap arrayMap = new ArrayMap();
        for (ContentValues contentValues : values) {
            String asString = contentValues.getAsString("networkProxyUsername");
            String asString2 = contentValues.getAsString("networkProxyPassword");
            String asString3 = contentValues.getAsString("networkSSID");
            if (!TextUtils.isEmpty(asString3) && !TextUtils.isEmpty(asString) && !TextUtils.isEmpty(asString2)) {
                arrayMap.put(asString3, new AuthConfig(asString, asString2));
            }
        }
        return arrayMap;
    }

    public final boolean isWifiRestrictionMigrationNeeded() {
        String genericValue = this.mEdmStorageProvider.getGenericValue("wifi_restriction_policy_version");
        boolean z = genericValue == null || !"version_1".equals(genericValue);
        Log.i("WifiPolicyService", "isWifiRestrictionMigrationNeeded : " + z);
        return z;
    }

    public final void migrateWifiUserRestriction() {
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("WIFI", new String[]{"allowWifi", "allowWifiStateChanges", "allowUserProfiles"}, null, null);
                handleUpgradeUserRestrictionAPIs(cursor);
                this.mEdmStorageProvider.putGenericValue("wifi_restriction_policy_version", "version_1");
                if (cursor == null) {
                    return;
                }
            } catch (Exception e) {
                Log.e("WifiPolicyService", "Exception inside migrateWifiUserRestriction", e);
                if (cursor == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void handleUpgradeUserRestrictionAPIs(Cursor cursor) {
        Log.i("WifiPolicyService", "upgradeUserRestrictionAPIs - START");
        try {
            try {
                cursor.moveToFirst();
                boolean z = false;
                boolean z2 = false;
                do {
                    if (cursor.getInt(cursor.getColumnIndex("allowWifi")) == 0 || cursor.getInt(cursor.getColumnIndex("allowWifiStateChanges")) == 0) {
                        z = true;
                    }
                    if (cursor.getInt(cursor.getColumnIndex("allowUserProfiles")) == 0) {
                        z2 = true;
                    }
                } while (cursor.moveToNext());
                if (z) {
                    this.mUserManager.setUserRestriction("no_change_wifi_state", false);
                    setChangeWifiStateUserRestriction(true);
                }
                if (z2) {
                    this.mUserManager.setUserRestriction("no_add_wifi_config", false);
                    setAddWifiConfigUserRestriction(true);
                }
            } catch (Exception e) {
                Log.e("WifiPolicyService", "exception inside handleUpgradeUserRestrictionAPIs", e);
            }
        } finally {
            Log.i("WifiPolicyService", "upgradeUserRestrictionAPIs - END");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0070, code lost:
    
        if (r3 == null) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0081, code lost:
    
        updateWifiMigrationFlag("wifi_policy_migration");
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0085, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007e, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007c, code lost:
    
        if (0 == 0) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean migrateWifiRelatedAPIs() {
        if (!isWifiMigrationNeeded("wifi_policy_migration")) {
            Log.i("WifiPolicyService", "Wi-Fi policy migration is not needed, skipping...");
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("WIFI", new String[]{"adminUid", "allowWifi", "allowWifiStateChanges", "allowUserProfiles", "allowAutomaticConnection", "minimumRequiredSecurity", "wifiSsidRestriction"}, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    evaluateAndMigrateUserRestrictionAPIs(cursor);
                    evaluateAndMigrateDPMAPIs(cursor);
                    RestrictionPolicy restrictionPolicy = (RestrictionPolicy) EnterpriseService.getPolicyService("restriction_policy");
                    if (restrictionPolicy == null) {
                        Log.e("WifiPolicyService", "migrateWifiRelatedAPIs - restrictionPolicy is null");
                    } else {
                        restrictionPolicy.evaluateAndMigrateWifiRelatedPolicyAPIs();
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("migrateWifiRelatedAPIs - ");
                sb.append(cursor == null ? "Cursor is null" : "Cursor is empty");
                Log.i("WifiPolicyService", sb.toString());
            } catch (Exception e) {
                Log.e("WifiPolicyService", "migrateWifiRelatedAPIs", e);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            updateWifiMigrationFlag("wifi_policy_migration");
            throw th;
        }
    }

    public final void resetWifiPolicyAPIs(int i) {
        Log.i("WifiPolicyService", "resetWifiPolicyAPIs - START");
        resetUserRestrictionAPIs(i);
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            Log.e("WifiPolicyService", "resetWifiPolicyAPIs - fail to retrieve dpmi object");
        } else {
            devicePolicyManagerInternal.notifyChangesOnWifiPolicy();
        }
        Log.i("WifiPolicyService", "resetWifiPolicyAPIs - END");
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a3, code lost:
    
        if (0 == 0) goto L44;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resetUserRestrictionAPIs(int i) {
        Log.i("WifiPolicyService", "resetUserRestrictionAPIs - START");
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("WIFI", new String[]{"adminUid", "allowWifi", "allowWifiStateChanges", "allowUserProfiles"}, null, null);
            } catch (Exception e) {
                Log.e("WifiPolicyService", "resetUserRestrictionAPIs", e);
            }
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int i2 = 0;
                int i3 = 0;
                do {
                    int i4 = cursor.getInt(cursor.getColumnIndex("adminUid"));
                    if (cursor.getInt(cursor.getColumnIndex("allowWifi")) == 0 || cursor.getInt(cursor.getColumnIndex("allowWifiStateChanges")) == 0) {
                        if (i2 == 0) {
                            i2 = i4;
                        } else if (i2 != -1) {
                            i2 = -1;
                        }
                    }
                    if (cursor.getInt(cursor.getColumnIndex("allowUserProfiles")) == 0) {
                        if (i3 == 0) {
                            i3 = i4;
                        } else if (i3 != -1) {
                            i3 = -1;
                        }
                    }
                } while (cursor.moveToNext());
                if (i2 == i) {
                    setChangeWifiStateUserRestriction(false);
                }
                if (i3 == i) {
                    setAddWifiConfigUserRestriction(false);
                }
                cursor.close();
                Log.i("WifiPolicyService", "resetUserRestrictionAPIs - END");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("resetUserRestrictionAPIs - ");
            sb.append(cursor == null ? "Cursor is null" : "Cursor is empty");
            Log.i("WifiPolicyService", sb.toString());
            if (cursor != null) {
                cursor.close();
            }
            Log.i("WifiPolicyService", "resetUserRestrictionAPIs - END");
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            Log.i("WifiPolicyService", "resetUserRestrictionAPIs - END");
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0097, code lost:
    
        if (0 == 0) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void resetAutomaticConnectionPolicy(int i) {
        Pair callerInfoFromUid;
        enforceSystemProcess();
        Log.i("WifiPolicyService", "resetAutomaticConnectionPolicy - START");
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("WIFI", new String[]{"adminUid", "allowAutomaticConnection"}, null, null);
            } catch (Exception e) {
                Log.e("WifiPolicyService", "resetAutomaticConnectionPolicy", e);
            }
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int i2 = 0;
                do {
                    int i3 = cursor.getInt(cursor.getColumnIndex("adminUid"));
                    if (cursor.getInt(cursor.getColumnIndex("allowAutomaticConnection")) == 0) {
                        if (i2 != 0) {
                            i3 = -1;
                            if (i2 != -1) {
                            }
                        }
                        i2 = i3;
                    }
                } while (cursor.moveToNext());
                if (i2 == i && (callerInfoFromUid = getCallerInfoFromUid(i2)) != null) {
                    setAllowAutojoinGlobal(((Integer) callerInfoFromUid.first).intValue(), ((ComponentName) callerInfoFromUid.second).getPackageName(), true);
                }
                cursor.close();
                Log.i("WifiPolicyService", "resetAutomaticConnectionPolicy - END");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("resetAutomaticConnectionPolicy - ");
            sb.append(cursor == null ? "Cursor is null" : "Cursor is empty");
            Log.i("WifiPolicyService", sb.toString());
            if (cursor != null) {
                cursor.close();
            }
            Log.i("WifiPolicyService", "resetAutomaticConnectionPolicy - END");
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            Log.i("WifiPolicyService", "resetAutomaticConnectionPolicy - END");
            throw th;
        }
    }

    public final void evaluateAndMigrateUserRestrictionAPIs(Cursor cursor) {
        Pair callerInfoFromUid;
        Log.i("WifiPolicyService", "evaluateAndMigrateUserRestrictionAPIs - START");
        try {
            try {
                cursor.moveToFirst();
                int i = -1;
                boolean z = false;
                boolean z2 = false;
                boolean z3 = false;
                do {
                    if (cursor.getInt(cursor.getColumnIndex("allowWifi")) == 0 || cursor.getInt(cursor.getColumnIndex("allowWifiStateChanges")) == 0) {
                        z = true;
                    }
                    if (cursor.getInt(cursor.getColumnIndex("allowUserProfiles")) == 0) {
                        z2 = true;
                    }
                    if (cursor.getInt(cursor.getColumnIndex("allowAutomaticConnection")) == 0) {
                        i = cursor.getInt(cursor.getColumnIndex("adminUid"));
                        z3 = true;
                    }
                } while (cursor.moveToNext());
                if (z) {
                    setChangeWifiStateUserRestriction(true);
                }
                if (z2) {
                    setAddWifiConfigUserRestriction(true);
                }
                if (z3 && i != -1 && (callerInfoFromUid = getCallerInfoFromUid(i)) != null) {
                    setAllowAutojoinGlobal(((Integer) callerInfoFromUid.first).intValue(), ((ComponentName) callerInfoFromUid.second).getPackageName(), false);
                }
            } catch (Exception e) {
                Log.e("WifiPolicyService", "evaluateAndMigrateUserRestrictionAPIs", e);
            }
        } finally {
            Log.i("WifiPolicyService", "evaluateAndMigrateUserRestrictionAPIs - END");
        }
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
                            setMinimumRequiredWifiSecurityLevel((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), i2);
                        }
                        if (cursor.getInt(cursor.getColumnIndex("wifiSsidRestriction")) == 1) {
                            setSsidAllowDenyList((ComponentName) callerInfoFromUid.second, UserHandle.getUserId(((Integer) callerInfoFromUid.first).intValue()), i, true);
                        }
                    }
                } while (cursor.moveToNext());
            } catch (Exception e) {
                Log.e("WifiPolicyService", "evaluateAndMigrateDPMAPIs", e);
            }
        } finally {
            Log.i("WifiPolicyService", "evaluateAndMigrateDPMAPIs - END");
        }
    }

    public final int save(WifiConfiguration wifiConfiguration, int i) {
        Pair networkCreatorInfo = getNetworkCreatorInfo(wifiConfiguration, i);
        if (networkCreatorInfo == null) {
            Log.e("WifiPolicyService", "save - Could not get network creator information");
            return -1;
        }
        int intValue = ((Integer) networkCreatorInfo.first).intValue();
        if (!isDOOrPO(intValue, ((ComponentName) networkCreatorInfo.second).getPackageName())) {
            intValue = 1000;
        }
        return this.mWifiAdapter.save(wifiConfiguration, intValue, ((ComponentName) networkCreatorInfo.second).getPackageName());
    }

    public final boolean isDOOrPO(int i, String str) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        return devicePolicyManager != null && (devicePolicyManager.isDeviceOwnerApp(str) || UserHandle.getUserId(i) != 0);
    }

    public final Pair getNetworkCreatorInfo(WifiConfiguration wifiConfiguration, int i) {
        Log.i("WifiPolicyService", "getNetworkCreatorInfo - START");
        if (i == -1) {
            i = getNetworkUidFromDatabase(wifiConfiguration.SSID, makeString(wifiConfiguration.allowedKeyManagement, WifiConfiguration.KeyMgmt.strings));
        }
        if (i == -1) {
            Log.e("WifiPolicyService", "getNetworkCreatorInfo - Invalid adminUid, aborting...");
            return null;
        }
        return getCallerInfoFromUid(i);
    }

    public final int getNetworkUidFromDatabase(String str, String str2) {
        Log.i("WifiPolicyService", "getNetworkUidFromDatabase - START");
        String[] strArr = {NetworkUtils.removeDoubleQuotes(str), str2};
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        ArrayList dataByFields = edmStorageProvider.getDataByFields("WIFI_CONF", new String[]{"networkSSID", "networkKeyMgmt"}, strArr, new String[]{"adminUid"});
        if (dataByFields == null || dataByFields.isEmpty()) {
            Log.e("WifiPolicyService", "getNetworkUidFromDatabase - cv is empty or null");
            return -1;
        }
        Integer asInteger = ((ContentValues) dataByFields.get(0)).getAsInteger("adminUid");
        if (asInteger == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final Pair getCallerInfoFromUid(int i) {
        Log.i("WifiPolicyService", "getCallerInfoFromUid START - parentUid = " + i);
        String[] strArr = {String.valueOf(i)};
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("ADMIN_INFO", new String[]{"adminUid"}, strArr, new String[]{"adminName", "isPseudoAdmin"});
        if (dataByFields == null || dataByFields.isEmpty()) {
            Log.e("WifiPolicyService", "getCallerInfoFromUid - cv is empty or null");
            return null;
        }
        String asString = ((ContentValues) dataByFields.get(0)).getAsString("adminName");
        Boolean asBoolean = ((ContentValues) dataByFields.get(0)).getAsBoolean("isPseudoAdmin");
        if (asBoolean != null && asBoolean.booleanValue()) {
            i = getWorkProfileUid(asString);
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(asString);
        Log.i("WifiPolicyService", "getCallerInfoFromUid END - profileUid = " + i + ", componentName = " + unflattenFromString);
        if (unflattenFromString == null || i == -1) {
            return null;
        }
        return new Pair(Integer.valueOf(i), unflattenFromString);
    }

    public final int getWorkProfileUid(String str) {
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        ArrayList dataByFields = edmStorageProvider.getDataByFields("ADMIN_INFO", new String[]{"adminName", "isPseudoAdmin"}, new String[]{str, "0"}, new String[]{"adminUid"});
        if (dataByFields == null || dataByFields.isEmpty()) {
            Log.e("WifiPolicyService", "getWorkProfileUid - cv is empty or null");
            return -1;
        }
        Integer asInteger = ((ContentValues) dataByFields.get(0)).getAsInteger("adminUid");
        if (asInteger == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final void migrateWifiNetworkIfNeeded() {
        Map allNetworksByAdminUid;
        Log.i("WifiPolicyService", "migrateWifiNetworkIfNeeded");
        try {
            if (!isWifiMigrationNeeded("wifi_network_migration")) {
                Log.i("WifiPolicyService", "Wi-Fi network migration is not needed, skipping...");
                return;
            }
            try {
                allNetworksByAdminUid = getAllNetworksByAdminUid();
            } catch (Exception e) {
                Log.e("WifiPolicyService", "migrateWifiNetworkIfNeeded", e);
            }
            if (allNetworksByAdminUid != null && !allNetworksByAdminUid.isEmpty()) {
                Log.i("WifiPolicyService", "migrateWifiNetworkIfNeeded - network map size: " + allNetworksByAdminUid.size());
                for (Map.Entry entry : allNetworksByAdminUid.entrySet()) {
                    if (entry != null && entry.getKey() != null && ((Integer) entry.getKey()).intValue() != -1 && entry.getValue() != null && !((List) entry.getValue()).isEmpty()) {
                        Pair callerInfoFromUid = getCallerInfoFromUid(((Integer) entry.getKey()).intValue());
                        if (callerInfoFromUid == null) {
                            Log.e("WifiPolicyService", "Could not get network creator information for " + entry.getKey() + " uid");
                        } else if (isDOOrPO(((Integer) callerInfoFromUid.first).intValue(), ((ComponentName) callerInfoFromUid.second).getPackageName())) {
                            int i = 0;
                            for (WifiConfiguration wifiConfiguration : (List) entry.getValue()) {
                                if (wifiConfiguration != null) {
                                    WifiConfiguration networkFromWifiModule = getNetworkFromWifiModule(wifiConfiguration.SSID, makeString(wifiConfiguration.allowedKeyManagement, WifiConfiguration.KeyMgmt.strings));
                                    if (networkFromWifiModule != null) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Migrating network ");
                                        i++;
                                        sb.append(i);
                                        sb.append(" from uid ");
                                        sb.append(callerInfoFromUid.first);
                                        Log.i("WifiPolicyService", sb.toString());
                                        this.mWifiAdapter.save(networkFromWifiModule, ((Integer) callerInfoFromUid.first).intValue(), ((ComponentName) callerInfoFromUid.second).getPackageName());
                                    } else {
                                        Log.e("WifiPolicyService", "Could not find wifi network from uid " + callerInfoFromUid.first);
                                    }
                                }
                            }
                        } else {
                            Log.i("WifiPolicyService", "Do not migrate networks from Device Admin - uid = " + callerInfoFromUid.first);
                        }
                    }
                }
                return;
            }
            Log.w("WifiPolicyService", "Network list is null or empty, network migration is not required");
        } finally {
            updateWifiMigrationFlag("wifi_network_migration");
        }
    }

    public final Map getAllNetworksByAdminUid() {
        Integer asInteger;
        List values = this.mEdmStorageProvider.getValues("WIFI_CONF", new String[]{"adminUid", "networkSSID", "networkKeyMgmt"}, (ContentValues) null);
        if (values == null || values.isEmpty()) {
            Log.i("WifiPolicyService", "getAllNetworksByAdminUid - cv is empty or null");
            return null;
        }
        Log.i("WifiPolicyService", "getAllNetworksByAdminUid - cv entries: " + values.size());
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < values.size(); i++) {
            ContentValues contentValues = (ContentValues) values.get(i);
            if (contentValues != null && (asInteger = contentValues.getAsInteger("adminUid")) != null) {
                String asString = contentValues.getAsString("networkSSID");
                if (!TextUtils.isEmpty(asString)) {
                    String asString2 = contentValues.getAsString("networkKeyMgmt");
                    if (!TextUtils.isEmpty(asString2)) {
                        WifiConfiguration wifiConfiguration = new WifiConfiguration();
                        wifiConfiguration.SSID = asString;
                        wifiConfiguration.allowedKeyManagement = makeBitSet(asString2, WifiConfiguration.KeyMgmt.strings);
                        if (arrayMap.get(asInteger) == null) {
                            arrayMap.put(asInteger, new ArrayList());
                        }
                        Log.i("WifiPolicyService", "getAllNetworksByAdminUid - adding network for admin: " + asInteger);
                        ((List) arrayMap.get(asInteger)).add(wifiConfiguration);
                    }
                }
            }
        }
        return arrayMap;
    }

    public final boolean isWifiMigrationNeeded(String str) {
        Log.i("WifiPolicyService", "isWifiMigrationNeeded - type = " + str);
        if (!"wifi_network_migration".equals(str) && !"wifi_policy_migration".equals(str)) {
            return false;
        }
        String genericValue = this.mEdmStorageProvider.getGenericValue(str);
        if (genericValue != null && "ok".equals(genericValue)) {
            Log.i("WifiPolicyService", "Migration is not needed");
            return false;
        }
        Log.i("WifiPolicyService", "Migration is needed");
        return true;
    }

    public void updateWifiMigrationFlag(String str) {
        Log.i("WifiPolicyService", "Updating Wi-Fi migration flag - type = " + str);
        if ("wifi_network_migration".equals(str) || "wifi_policy_migration".equals(str)) {
            this.mEdmStorageProvider.putGenericValue(str, "ok");
        }
    }

    public final void evaluateNetworkFromDatabase() {
        Log.i("WifiPolicyService", "evaluateNetworkFromDatabase - START");
        Map allNetworksByAdminUid = getAllNetworksByAdminUid();
        if (allNetworksByAdminUid == null || allNetworksByAdminUid.isEmpty()) {
            Log.w("WifiPolicyService", "Network list is null or empty");
            return;
        }
        Log.i("WifiPolicyService", "evaluateNetworkFromDatabase - network map size: " + allNetworksByAdminUid.size());
        for (Map.Entry entry : allNetworksByAdminUid.entrySet()) {
            if (entry != null && entry.getKey() != null && ((Integer) entry.getKey()).intValue() != -1 && entry.getValue() != null && !((List) entry.getValue()).isEmpty()) {
                for (WifiConfiguration wifiConfiguration : (List) entry.getValue()) {
                    if (wifiConfiguration != null && getNetworkFromWifiModule(wifiConfiguration.SSID, makeString(wifiConfiguration.allowedKeyManagement, WifiConfiguration.KeyMgmt.strings)) == null) {
                        Log.i("WifiPolicyService", "SSID from admin " + entry.getKey() + " does not exist anymore on Wi-Fi module");
                        removeNetworkConfigurationMDM(wifiConfiguration.SSID, ((Integer) entry.getKey()).intValue(), true);
                    }
                }
            }
        }
        Log.i("WifiPolicyService", "evaluateNetworkFromDatabase - END");
    }

    public final void enforceAutomaticConnectionIfNeeded() {
        int i;
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursor("WIFI", new String[]{"adminUid", "allowAutomaticConnection"}, null, null);
            } catch (Exception e) {
                Log.e("WifiPolicyService", "enforceAutomaticConnectionIfNeeded", e);
                if (0 == 0) {
                    return;
                }
            }
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (true) {
                    if (cursor.getInt(cursor.getColumnIndex("allowAutomaticConnection")) == 0) {
                        i = cursor.getInt(cursor.getColumnIndex("adminUid"));
                        break;
                    } else if (!cursor.moveToNext()) {
                        i = -1;
                        break;
                    }
                }
                if (i == -1) {
                    Log.i("WifiPolicyService", "enforceAutomaticConnectionIfNeeded - no enforcement");
                } else {
                    Pair callerInfoFromUid = getCallerInfoFromUid(i);
                    if (callerInfoFromUid != null) {
                        Log.i("WifiPolicyService", "enforceAutomaticConnectionIfNeeded - enforcing after reboot");
                        setAllowAutojoinGlobal(((Integer) callerInfoFromUid.first).intValue(), ((ComponentName) callerInfoFromUid.second).getPackageName(), false);
                    }
                }
                cursor.close();
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("enforceAutomaticConnectionIfNeeded - ");
            sb.append(cursor == null ? "Cursor is null" : "Cursor is empty");
            Log.i("WifiPolicyService", sb.toString());
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }
}
