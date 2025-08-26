package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.app.admin.WifiSsidPolicy;
import android.content.Context;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiSsid;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.core.os.BuildCompat;
import com.android.systemui.R;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil;
import com.samsung.android.wifitrackerlib.WifiQoSScoredCache;
import com.samsung.android.wifitrackerlib.WifiScoredNetwork;
import com.sec.ims.extensions.WiFiManagerExt;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class StandardWifiEntry extends WifiEntry {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mHasAddConfigUserRestriction;
    public boolean mIsAdminRestricted;
    public final boolean mIsEnhancedOpenSupported;
    public boolean mIsUserShareable;
    public final boolean mIsWpa3SaeSupported;
    public final boolean mIsWpa3SuiteBSupported;
    public final StandardWifiEntryKey mKey;
    public final Map mMatchingScanResults;
    public final Map mMatchingWifiConfigs;
    public SemWifiManager mSemWifiManager;
    public final boolean mShowBandSummary;
    public final List mTargetScanResults;
    public final List mTargetSecurityTypes;
    public WifiConfiguration mTargetWifiConfig;
    public final UserManager mUserManager;

    public class StandardWifiEntryKey {
        public final boolean mIsNetworkRequest;
        public final boolean mIsTargetingNewNetworks;
        public final ScanResultKey mScanResultKey;
        public final String mSuggestionProfileKey;

        public StandardWifiEntryKey(ScanResultKey scanResultKey) {
            this(scanResultKey, false);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                StandardWifiEntryKey standardWifiEntryKey = (StandardWifiEntryKey) obj;
                if (Objects.equals(this.mScanResultKey, standardWifiEntryKey.mScanResultKey) && TextUtils.equals(this.mSuggestionProfileKey, standardWifiEntryKey.mSuggestionProfileKey) && this.mIsNetworkRequest == standardWifiEntryKey.mIsNetworkRequest) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.mScanResultKey, this.mSuggestionProfileKey, Boolean.valueOf(this.mIsNetworkRequest));
        }

        public final String toString() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            try {
                ScanResultKey scanResultKey = this.mScanResultKey;
                if (scanResultKey != null) {
                    jSONObject.put("SCAN_RESULT_KEY", scanResultKey.toString());
                }
                String str = this.mSuggestionProfileKey;
                if (str != null) {
                    jSONObject.put("SUGGESTION_PROFILE_KEY", str);
                }
                boolean z = this.mIsNetworkRequest;
                if (z) {
                    jSONObject.put("IS_NETWORK_REQUEST", z);
                }
                boolean z2 = this.mIsTargetingNewNetworks;
                if (z2) {
                    jSONObject.put("IS_TARGETING_NEW_NETWORKS", z2);
                }
            } catch (JSONException e) {
                Log.wtf("StandardWifiEntry", "JSONException while converting StandardWifiEntryKey to string: " + e);
            }
            return "StandardWifiEntry:" + jSONObject.toString();
        }

        public StandardWifiEntryKey(ScanResultKey scanResultKey, boolean z) {
            this.mScanResultKey = scanResultKey;
            this.mIsTargetingNewNetworks = z;
        }

        public StandardWifiEntryKey(WifiConfiguration wifiConfiguration) {
            this(wifiConfiguration, false);
        }

        public StandardWifiEntryKey(WifiConfiguration wifiConfiguration, boolean z) {
            this.mIsTargetingNewNetworks = false;
            this.mScanResultKey = new ScanResultKey(wifiConfiguration);
            if (wifiConfiguration.fromWifiNetworkSuggestion) {
                this.mSuggestionProfileKey = new StringJoiner(",").add(wifiConfiguration.creatorName).add(String.valueOf(wifiConfiguration.carrierId)).add(String.valueOf(wifiConfiguration.subscriptionId)).toString();
            } else if (wifiConfiguration.fromWifiNetworkSpecifier) {
                this.mIsNetworkRequest = true;
            }
            this.mIsTargetingNewNetworks = z;
        }

        public StandardWifiEntryKey(String str) {
            this.mIsTargetingNewNetworks = false;
            this.mScanResultKey = new ScanResultKey();
            if (!str.startsWith("StandardWifiEntry:")) {
                Log.e("StandardWifiEntry", "String key does not start with key prefix!");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str.substring(18));
                if (jSONObject.has("SCAN_RESULT_KEY")) {
                    this.mScanResultKey = new ScanResultKey(jSONObject.getString("SCAN_RESULT_KEY"));
                }
                if (jSONObject.has("SUGGESTION_PROFILE_KEY")) {
                    this.mSuggestionProfileKey = jSONObject.getString("SUGGESTION_PROFILE_KEY");
                }
                if (jSONObject.has("IS_NETWORK_REQUEST")) {
                    this.mIsNetworkRequest = jSONObject.getBoolean("IS_NETWORK_REQUEST");
                }
                if (jSONObject.has("IS_TARGETING_NEW_NETWORKS")) {
                    this.mIsTargetingNewNetworks = jSONObject.getBoolean("IS_TARGETING_NEW_NETWORKS");
                }
            } catch (JSONException e) {
                Log.e("StandardWifiEntry", "JSONException while converting StandardWifiEntryKey to string: " + e);
            }
        }
    }

    public StandardWifiEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, StandardWifiEntryKey standardWifiEntryKey, WifiManager wifiManager, boolean z) {
        super(wifiTrackerInjector, handler, wifiManager, z);
        this.mMatchingScanResults = new ArrayMap();
        this.mMatchingWifiConfigs = new ArrayMap();
        this.mTargetScanResults = new ArrayList();
        this.mTargetSecurityTypes = new ArrayList();
        boolean z2 = false;
        this.mIsUserShareable = false;
        this.mIsAdminRestricted = false;
        this.mHasAddConfigUserRestriction = false;
        this.mKey = standardWifiEntryKey;
        WifiManager wifiManager2 = this.mWifiManager;
        if (SemWifiEntryFlags.isWpa3SaeSupported == -1 && wifiManager2.isWifiEnabled()) {
            SemWifiEntryFlags.isWpa3SaeSupported = wifiManager2.isWpa3SaeSupported() ? 1 : 0;
        }
        this.mIsWpa3SaeSupported = SemWifiEntryFlags.isWpa3SaeSupported == 1;
        WifiManager wifiManager3 = this.mWifiManager;
        if (SemWifiEntryFlags.isWpa3SuiteBSupported == -1 && wifiManager3.isWifiEnabled()) {
            SemWifiEntryFlags.isWpa3SuiteBSupported = wifiManager3.isWpa3SuiteBSupported() ? 1 : 0;
        }
        this.mIsWpa3SuiteBSupported = SemWifiEntryFlags.isWpa3SuiteBSupported == 1;
        WifiManager wifiManager4 = this.mWifiManager;
        if (SemWifiEntryFlags.isWpa3OweSupported == -1 && wifiManager4.isWifiEnabled()) {
            SemWifiEntryFlags.isWpa3OweSupported = wifiManager4.isEnhancedOpenSupported() ? 1 : 0;
        }
        this.mIsEnhancedOpenSupported = SemWifiEntryFlags.isWpa3OweSupported == 1;
        if (SemWifiEntryFlags.isWifiDeveloperOptionOn(this.mContext) && SemWifiEntryFlags.isShowBandInfoOn(this.mContext)) {
            z2 = true;
        }
        this.mShowBandSummary = z2;
        this.mSemFlags.setIsSupportedWifi7(this.mContext);
        this.mUserManager = wifiTrackerInjector.mUserManager;
        this.mDevicePolicyManager = wifiTrackerInjector.mDevicePolicyManager;
        updateSecurityTypes();
        updateAdminRestrictions();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean canConnect() {
        WifiConfiguration wifiConfiguration;
        WifiEnterpriseConfig wifiEnterpriseConfig;
        if (this.mScanResultLevel != -1 && getConnectedState() == 0) {
            if (hasAdminRestrictions()) {
                return false;
            }
            if (!((ArrayList) this.mTargetSecurityTypes).contains(3) || (wifiConfiguration = this.mTargetWifiConfig) == null || (wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) == null) {
                return true;
            }
            if (!wifiEnterpriseConfig.isAuthenticationSimBased()) {
                return true;
            }
            List<SubscriptionInfo> activeSubscriptionInfoList = ((SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class)).getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() != 0) {
                if (this.mTargetWifiConfig.carrierId == -1) {
                    return true;
                }
                Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                while (it.hasNext()) {
                    if (it.next().getCarrierId() == this.mTargetWifiConfig.carrierId) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final boolean canDisconnect() {
        return getConnectedState() == 2;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public boolean canSetAutoJoinEnabled() {
        return isSaved() || isSuggestion();
    }

    public boolean canSetMeteredChoice() {
        return getWifiConfiguration() != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean canShare() {
        if (this.mInjector.mIsDemoMode) {
            return false;
        }
        WifiConfiguration wifiConfiguration = getWifiConfiguration();
        if (wifiConfiguration == null) {
            return false;
        }
        int i = BuildCompat.$r8$clinit;
        if (this.mUserManager.hasUserRestrictionForUser("no_sharing_admin_configured_wifi", UserHandle.getUserHandleForUid(wifiConfiguration.creatorUid))) {
            if (Utils.isDeviceOrProfileOwner(this.mContext, wifiConfiguration.creatorName, wifiConfiguration.creatorUid)) {
                return false;
            }
        }
        ArrayList arrayList = (ArrayList) this.mTargetSecurityTypes;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            int iIntValue = ((Integer) obj).intValue();
            if (iIntValue == 0 || iIntValue == 1 || iIntValue == 2 || iIntValue == 4 || iIntValue == 6) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0015  */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized boolean canSignIn() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        if (this.mNetwork == null || (networkCapabilities = this.mNetworkCapabilities) == null) {
            z = false;
        } else if (networkCapabilities.hasCapability(17)) {
            z = true;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized void connect(final WifiEntry.ConnectCallback connectCallback) {
        List<SubscriptionInfo> activeSubscriptionInfoList;
        boolean zAnyMatch = true;
        final int i = 0;
        synchronized (this) {
            try {
                this.mConnectCallback = connectCallback;
                this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
                if (isSaved() || isSuggestion()) {
                    WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
                    List list = Utils.defaultSsidList;
                    WifiEnterpriseConfig wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig;
                    if (wifiEnterpriseConfig != null && wifiEnterpriseConfig.isAuthenticationSimBased()) {
                        Context context = this.mContext;
                        final int i2 = this.mTargetWifiConfig.carrierId;
                        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
                        if (subscriptionManager == null || (activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList()) == null || activeSubscriptionInfoList.isEmpty()) {
                            zAnyMatch = false;
                        } else if (i2 != -1) {
                            zAnyMatch = activeSubscriptionInfoList.stream().anyMatch(new Predicate() { // from class: com.android.wifitrackerlib.Utils$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i3 = i2;
                                    List list2 = Utils.defaultSsidList;
                                    return ((SubscriptionInfo) obj).getCarrierId() == i3;
                                }
                            });
                        }
                        if (!zAnyMatch) {
                            if (connectCallback != null) {
                                this.mCallbackHandler.post(new Runnable() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda5
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        int i3 = i;
                                        WifiEntry.ConnectCallback connectCallback2 = connectCallback;
                                        switch (i3) {
                                            case 0:
                                                int i4 = StandardWifiEntry.$r8$clinit;
                                                connectCallback2.onConnectResult(3);
                                                break;
                                            default:
                                                int i5 = StandardWifiEntry.$r8$clinit;
                                                connectCallback2.onConnectResult(1);
                                                break;
                                        }
                                    }
                                });
                            }
                            return;
                        }
                    }
                    if (this.mTargetWifiConfig.networkId < 0) {
                        Log.d("StandardWifiEntry", "failed to connect - invalid network ID");
                        return;
                    }
                    new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(this.mTargetWifiConfig);
                    this.mWifiManager.connect(this.mTargetWifiConfig.networkId, new WifiEntry.ConnectActionListener());
                    SemWifiManager semWifiManager$2 = getSemWifiManager$2();
                    WifiConfiguration wifiConfiguration2 = this.mTargetWifiConfig;
                    semWifiManager$2.notifyConnect(wifiConfiguration2.networkId, wifiConfiguration2.getKey());
                } else if (((ArrayList) this.mTargetSecurityTypes).contains(6)) {
                    WifiConfiguration wifiConfiguration3 = new WifiConfiguration();
                    wifiConfiguration3.SSID = "\"" + this.mKey.mScanResultKey.mSsid + "\"";
                    wifiConfiguration3.setSecurityParams(6);
                    wifiConfiguration3.hiddenSSID = Utils.shouldSetHiddenSsid(wifiConfiguration3.SSID, this.mWifiManager);
                    new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(this.mTargetWifiConfig);
                    this.mWifiManager.connect(wifiConfiguration3, new WifiEntry.ConnectActionListener());
                    this.mTargetWifiConfig = wifiConfiguration3;
                    getSemWifiManager$2().notifyConnect(wifiConfiguration3.networkId, wifiConfiguration3.getKey());
                    if (((ArrayList) this.mTargetSecurityTypes).contains(0)) {
                        WifiConfiguration wifiConfiguration4 = new WifiConfiguration();
                        wifiConfiguration4.SSID = "\"" + this.mKey.mScanResultKey.mSsid + "\"";
                        wifiConfiguration4.setSecurityParams(0);
                        wifiConfiguration4.hiddenSSID = Utils.shouldSetHiddenSsid(wifiConfiguration4.SSID, this.mWifiManager);
                        this.mWifiManager.save(wifiConfiguration4, null);
                    }
                } else if (((ArrayList) this.mTargetSecurityTypes).contains(0)) {
                    WifiConfiguration wifiConfiguration5 = new WifiConfiguration();
                    wifiConfiguration5.SSID = "\"" + this.mKey.mScanResultKey.mSsid + "\"";
                    wifiConfiguration5.setSecurityParams(0);
                    wifiConfiguration5.hiddenSSID = Utils.shouldSetHiddenSsid(wifiConfiguration5.SSID, this.mWifiManager);
                    new WifiIssueDetectorUtil(this.mContext).reportConnectNetwork(wifiConfiguration5);
                    this.mWifiManager.connect(wifiConfiguration5, new WifiEntry.ConnectActionListener());
                    this.mTargetWifiConfig = wifiConfiguration5;
                    getSemWifiManager$2().notifyConnect(wifiConfiguration5.networkId, wifiConfiguration5.getKey());
                } else if (connectCallback != null) {
                    Handler handler = this.mCallbackHandler;
                    final char c = 1 == true ? 1 : 0;
                    handler.post(new Runnable() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i3 = c;
                            WifiEntry.ConnectCallback connectCallback2 = connectCallback;
                            switch (i3) {
                                case 0:
                                    int i4 = StandardWifiEntry.$r8$clinit;
                                    connectCallback2.onConnectResult(3);
                                    break;
                                default:
                                    int i5 = StandardWifiEntry.$r8$clinit;
                                    connectCallback2.onConnectResult(1);
                                    break;
                            }
                        }
                    });
                }
            } finally {
            }
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            Iterator it = ((ArrayMap) this.mMatchingWifiConfigs).values().iterator();
            while (it.hasNext()) {
                if (((WifiConfiguration) it.next()).networkId == wifiInfo.getNetworkId()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public final synchronized String getBandString() {
        return Utils.getBandString(this.mBand, this.mContext);
    }

    public final String getBandSummary$1(int i, WifiInfo wifiInfo) {
        String string;
        if (i == 2) {
            setBand(wifiInfo.getFrequency());
            return getBandString();
        }
        if (((ArrayList) this.mTargetScanResults).isEmpty()) {
            return getBandString();
        }
        Context context = this.mContext;
        ScanResult scanResult = (ScanResult) ((ArrayList) this.mTargetScanResults).get(0);
        List list = Utils.defaultSsidList;
        synchronized (Utils.class) {
            try {
                StringJoiner stringJoiner = new StringJoiner(" + ");
                int wifiStandard = scanResult.getWifiStandard();
                int i2 = BuildCompat.$r8$clinit;
                if (wifiStandard == 8) {
                    Iterator<MloLink> it = scanResult.getAffiliatedMloLinks().iterator();
                    while (it.hasNext()) {
                        int band = it.next().getBand();
                        int i3 = 1;
                        if (band == 1) {
                            i3 = 0;
                        } else if (band != 2) {
                            i3 = band != 8 ? band != 16 ? -1 : 3 : 2;
                        }
                        stringJoiner.add(Utils.getBandString(i3, context));
                    }
                }
                string = stringJoiner.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        return TextUtils.isEmpty(string) ? getBandString() : string;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getKey() {
        return this.mKey.toString();
    }

    public synchronized int getMeteredChoice() {
        WifiConfiguration wifiConfiguration;
        if (!isSuggestion() && (wifiConfiguration = this.mTargetWifiConfig) != null) {
            int i = wifiConfiguration.meteredOverride;
            if (i == 1) {
                return 1;
            }
            if (i == 2) {
                return 2;
            }
        }
        return 0;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getNetworkSelectionDescription() {
        return Utils.getNetworkSelectionDescription(getWifiConfiguration());
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getScanResultDescription() {
        if (((ArrayMap) this.mMatchingScanResults).size() == 0) {
            return "";
        }
        return "[" + getScanResultDescription(2400, KnoxAiManagerInternal.CONN_MAX_WAIT_TIME) + ";" + getScanResultDescription(4900, 5900) + ";" + getScanResultDescription(5925, 7125) + ";" + getScanResultDescription(58320, 70200) + "]";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getSecurityString() {
        if (((ArrayList) this.mTargetSecurityTypes).size() == 0) {
            return "";
        }
        if (((ArrayList) this.mTargetSecurityTypes).size() == 1) {
            int iIntValue = ((Integer) ((ArrayList) this.mTargetSecurityTypes).get(0)).intValue();
            if (iIntValue == 9) {
                return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa3);
            }
            switch (iIntValue) {
                case 0:
                    return "";
                case 1:
                    return this.mContext.getString(R.string.wifitrackerlib_wifi_security_wep);
                case 2:
                    return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_wpa_wpa2);
                case 3:
                    return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa2_wpa3);
                case 4:
                    return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_sae);
                case 5:
                    return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap_suiteb);
                case 6:
                    return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_owe);
            }
        }
        if (((ArrayList) this.mTargetSecurityTypes).size() == 2) {
            if (((ArrayList) this.mTargetSecurityTypes).contains(0) && ((ArrayList) this.mTargetSecurityTypes).contains(6)) {
                return this.mContext.getString(R.string.wifi_security_short_none_owe);
            }
            if (((ArrayList) this.mTargetSecurityTypes).contains(2) && ((ArrayList) this.mTargetSecurityTypes).contains(4)) {
                return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_wpa2_wpa3);
            }
            if (((ArrayList) this.mTargetSecurityTypes).contains(3) && ((ArrayList) this.mTargetSecurityTypes).contains(9)) {
                return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa_wpa2_wpa3);
            }
        }
        Log.e("StandardWifiEntry", "Couldn't get string for security types: " + this.mTargetSecurityTypes);
        return "";
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized List getSecurityTypes() {
        return new ArrayList(this.mTargetSecurityTypes);
    }

    public final SemWifiManager getSemWifiManager$2() {
        if (this.mSemWifiManager == null) {
            this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        }
        return this.mSemWifiManager;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getSsid() {
        return this.mKey.mScanResultKey.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized String getStandardString() {
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            return Utils.getStandardString(wifiInfo.getWifiStandard(), this.mContext);
        }
        if (((ArrayList) this.mTargetScanResults).isEmpty()) {
            return "";
        }
        return Utils.getStandardString(((ScanResult) ((ArrayList) this.mTargetScanResults).get(0)).getWifiStandard(), this.mContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x016c A[Catch: all -> 0x0014, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:11:0x0017, B:15:0x0031, B:26:0x0081, B:28:0x0085, B:29:0x008e, B:31:0x0094, B:33:0x009d, B:35:0x00a9, B:36:0x00ac, B:39:0x00b2, B:41:0x00b8, B:43:0x00c4, B:44:0x00c7, B:46:0x00cd, B:48:0x00d7, B:50:0x00dd, B:52:0x00e3, B:54:0x00e7, B:57:0x00ed, B:60:0x00f2, B:69:0x0103, B:70:0x010b, B:71:0x0113, B:72:0x011b, B:73:0x0122, B:75:0x0128, B:76:0x012b, B:78:0x0131, B:80:0x0137, B:82:0x013d, B:84:0x0141, B:86:0x0145, B:90:0x014c, B:93:0x0166, B:95:0x016c, B:91:0x0158, B:97:0x0171, B:99:0x0177, B:101:0x018b, B:102:0x018f, B:104:0x0195, B:105:0x0199, B:107:0x01a5, B:108:0x01a8, B:110:0x01b0, B:112:0x01b6, B:115:0x01bd, B:117:0x01c6, B:118:0x01cb, B:120:0x01d1, B:121:0x01d9, B:123:0x01df, B:124:0x01e3, B:126:0x01e9, B:128:0x01ef, B:129:0x0205, B:131:0x0211, B:132:0x0214, B:16:0x0044, B:17:0x0051, B:21:0x005e, B:24:0x0074, B:25:0x0077), top: B:137:0x0003 }] */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized String getSummary(boolean z) {
        String disconnectedDescription;
        String string;
        int i;
        if (hasAdminRestrictions()) {
            return this.mContext.getString(R.string.wifitrackerlib_admin_restricted_network);
        }
        StringJoiner stringJoiner = new StringJoiner(this.mContext.getString(R.string.wifitrackerlib_summary_separator));
        int connectedState = getConnectedState();
        if (connectedState == 0) {
            disconnectedDescription = Utils.getDisconnectedDescription(this.mContext, this.mTargetWifiConfig, this.mSemFlags);
        } else if (connectedState == 1) {
            Context context = this.mContext;
            NetworkInfo networkInfo = this.mNetworkInfo;
            List list = Utils.defaultSsidList;
            if (context == null || networkInfo == null) {
                disconnectedDescription = "";
            } else {
                String[] stringArray = context.getResources().getStringArray(R.array.wifitrackerlib_wifi_status);
                int iOrdinal = NetworkInfo.DetailedState.CONNECTING.ordinal();
                if (iOrdinal < stringArray.length) {
                    disconnectedDescription = stringArray[iOrdinal];
                }
            }
        } else if (connectedState != 2) {
            Log.e("StandardWifiEntry", "getConnectedState() returned unknown state: " + connectedState);
            disconnectedDescription = null;
        } else {
            disconnectedDescription = Utils.getConnectedDescription(this.mContext, this.mWifiInfo, this.mTargetWifiConfig, this.mNetworkCapabilities);
        }
        if (this.mShowBandSummary) {
            stringJoiner.add(getBandSummary$1(connectedState, this.mWifiInfo));
        }
        if (!TextUtils.isEmpty(disconnectedDescription)) {
            stringJoiner.add(disconnectedDescription);
            if (semIsEphemeral()) {
                String verboseLoggingDescription = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
                if (!TextUtils.isEmpty(verboseLoggingDescription)) {
                    stringJoiner.add(verboseLoggingDescription);
                }
                return stringJoiner.toString();
            }
        }
        if (getConnectedState() == 2) {
            String warningDescription = Utils.getWarningDescription(this.mContext, this);
            if (!TextUtils.isEmpty(warningDescription)) {
                stringJoiner.add(warningDescription);
            }
        }
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        if (semWifiEntryFlags.networkScoringUiEnabled) {
            Context context2 = this.mContext;
            List list2 = Utils.defaultSsidList;
            String string2 = "";
            if (context2 != null && getConnectedState() == 0 && !isSaved()) {
                SemWifiConfiguration semWifiConfiguration = semWifiEntryFlags.semConfig;
                if ((semWifiConfiguration != null ? semWifiConfiguration.networkDisableReason : 0) != 5 && semWifiEntryFlags.networkType != 2) {
                    int i2 = this.mSpeed;
                    if (i2 == 5) {
                        string2 = context2.getString(R.string.wifitrackerlib_speed_label_slow);
                    } else if (i2 == 10) {
                        string2 = context2.getString(R.string.wifitrackerlib_speed_label_okay);
                    } else if (i2 == 20) {
                        string2 = context2.getString(R.string.wifitrackerlib_speed_label_fast);
                    } else if (i2 == 30) {
                        string2 = context2.getString(R.string.wifitrackerlib_speed_label_very_fast);
                    }
                }
            }
            if (!TextUtils.isEmpty(string2)) {
                stringJoiner.add(string2);
            }
            Context context3 = this.mContext;
            SemWifiEntryFlags semWifiEntryFlags2 = this.mSemFlags;
            if (context3 == null || getConnectedState() != 0 || isSaved() || (i = semWifiEntryFlags2.networkType) == 0) {
                string = "";
                if (!TextUtils.isEmpty(string)) {
                    stringJoiner.add(string);
                }
            } else {
                SemWifiConfiguration semWifiConfiguration2 = semWifiEntryFlags2.semConfig;
                if ((semWifiConfiguration2 != null ? semWifiConfiguration2.networkDisableReason : 0) == 5) {
                    string = "";
                    if (!TextUtils.isEmpty(string)) {
                    }
                } else {
                    string = i == 1 ? context3.getResources().getString(R.string.wifi_disabled_captive_portal) : context3.getResources().getString(R.string.wifi_disabled_no_internet);
                    if (!TextUtils.isEmpty(string)) {
                    }
                }
            }
        }
        if (connectedState == 0) {
            if (this.mSemFlags.isCarrierNetwork) {
                String carrierNetworkOffloadDescription = Utils.getCarrierNetworkOffloadDescription(this.mContext, this, this.mWifiManager);
                String autoConnectDescription = Utils.getAutoConnectDescription(this.mContext, this);
                if (!TextUtils.isEmpty(carrierNetworkOffloadDescription)) {
                    stringJoiner.add(carrierNetworkOffloadDescription);
                } else if (!TextUtils.isEmpty(autoConnectDescription)) {
                    stringJoiner.add(autoConnectDescription);
                }
            } else {
                String autoConnectDescription2 = Utils.getAutoConnectDescription(this.mContext, this);
                if (!TextUtils.isEmpty(autoConnectDescription2)) {
                    stringJoiner.add(autoConnectDescription2);
                }
            }
        }
        Context context4 = this.mContext;
        List list3 = Utils.defaultSsidList;
        String string3 = "";
        if (context4 != null && (canSetMeteredChoice() || getMeteredChoice() == 1)) {
            if (getMeteredChoice() == 1) {
                string3 = context4.getString(R.string.wifitrackerlib_wifi_metered_label);
            } else if (getMeteredChoice() == 2) {
                string3 = context4.getString(R.string.wifitrackerlib_wifi_unmetered_label);
            } else if (isMetered()) {
                string3 = context4.getString(R.string.wifitrackerlib_wifi_metered_label);
            }
        }
        if (stringJoiner.length() != 0 && !TextUtils.isEmpty(string3)) {
            StringJoiner stringJoiner2 = new StringJoiner(this.mContext.getString(R.string.wifitrackerlib_summary_separator));
            stringJoiner2.add(string3);
            stringJoiner2.add(stringJoiner.toString());
            stringJoiner = stringJoiner2;
        }
        String verboseLoggingDescription2 = Utils.getVerboseLoggingDescription(this, this.mSemFlags);
        if (!TextUtils.isEmpty(verboseLoggingDescription2)) {
            stringJoiner.add(verboseLoggingDescription2);
        }
        return stringJoiner.toString();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final String getTitle() {
        return this.mKey.mScanResultKey.mSsid;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized WifiConfiguration getWifiConfiguration() {
        return this.mTargetWifiConfig;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean hasAdminRestrictions() {
        try {
            if (!this.mHasAddConfigUserRestriction || isSaved() || isSuggestion()) {
                if (!this.mIsAdminRestricted) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean isAutoJoinEnabled() {
        WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
        if (wifiConfiguration == null) {
            return true;
        }
        return wifiConfiguration.allowAutojoin;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0013  */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean isMetered() {
        boolean z;
        z = true;
        if (getMeteredChoice() != 1) {
            WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
            if (wifiConfiguration != null) {
                if (!wifiConfiguration.meteredHint) {
                    z = false;
                }
            }
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean isSaved() {
        return this.mTargetWifiConfig != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x000d  */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean isSuggestion() {
        boolean z;
        WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
        if (wifiConfiguration != null) {
            z = wifiConfiguration.fromWifiNetworkSuggestion;
        }
        return z;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void onNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onNetworkCapabilitiesChanged(network, networkCapabilities);
        canSignIn();
    }

    public final synchronized void semUpdateScores(WifiQoSScoredCache wifiQoSScoredCache) {
        WifiScoredNetwork wifiScoredNetwork;
        ArrayList arrayList = (ArrayList) this.mTargetScanResults;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ScanResult scanResult = (ScanResult) obj;
            wifiQoSScoredCache.getClass();
            String str = scanResult.BSSID;
            if (TextUtils.isEmpty(str)) {
                wifiScoredNetwork = null;
            } else {
                synchronized (wifiQoSScoredCache.mLock) {
                    wifiScoredNetwork = (WifiScoredNetwork) ((HashMap) wifiQoSScoredCache.mCache).get(str);
                }
            }
            if (wifiScoredNetwork != null) {
                ((HashMap) this.mSemFlags.qosScoredNetworkCache).put(scanResult.BSSID, wifiScoredNetwork);
            }
        }
    }

    public final synchronized void semUpdateSemWifiConfig(Map map) {
        try {
            WifiConfiguration wifiConfiguration = this.mTargetWifiConfig;
            if (wifiConfiguration != null) {
                SemWifiConfiguration semWifiConfiguration = (SemWifiConfiguration) ((HashMap) map).get(wifiConfiguration.getKey());
                SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
                if (semWifiConfiguration != null) {
                    semWifiConfiguration.isLockDown();
                    semWifiEntryFlags.getClass();
                    semWifiConfiguration.isCaptivePortal();
                    semWifiEntryFlags.semConfig = semWifiConfiguration;
                } else {
                    semWifiEntryFlags.getClass();
                }
                notifyOnUpdated();
            }
            notifyOnUpdated();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0031, code lost:
    
        if (r0.getDisableReasonCounter(5) > 0) goto L24;
     */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized boolean shouldEditBeforeConnect() {
        try {
            WifiConfiguration wifiConfiguration = getWifiConfiguration();
            if (wifiConfiguration == null) {
                return false;
            }
            WifiConfiguration.NetworkSelectionStatus networkSelectionStatus = wifiConfiguration.getNetworkSelectionStatus();
            if (networkSelectionStatus.getNetworkSelectionStatus() != 0 || !networkSelectionStatus.hasEverConnected()) {
                if (networkSelectionStatus.getDisableReasonCounter(2) <= 0 && networkSelectionStatus.getDisableReasonCounter(8) <= 0) {
                }
                return true;
            }
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void updateAdminRestrictions() {
        int i;
        int i2 = BuildCompat.$r8$clinit;
        UserManager userManager = this.mUserManager;
        if (userManager != null) {
            this.mHasAddConfigUserRestriction = userManager.hasUserRestriction("no_add_wifi_config");
        }
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            int minimumRequiredWifiSecurityLevel = devicePolicyManager.getMinimumRequiredWifiSecurityLevel();
            if (minimumRequiredWifiSecurityLevel != 0) {
                ArrayList arrayList = (ArrayList) getSecurityTypes();
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    int iIntValue = ((Integer) obj).intValue();
                    List list = Utils.defaultSsidList;
                    switch (iIntValue) {
                        case 0:
                        case 6:
                            i = 0;
                            break;
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                            i = 1;
                            break;
                        case 3:
                        case 8:
                        case 9:
                        case 11:
                        case 12:
                            i = 2;
                            break;
                        case 5:
                            i = 3;
                            break;
                        case 10:
                        default:
                            i = -1;
                            break;
                    }
                    if (i != -1 && minimumRequiredWifiSecurityLevel <= i) {
                    }
                }
                this.mIsAdminRestricted = true;
                return;
            }
            DevicePolicyManager devicePolicyManager2 = this.mDevicePolicyManager;
            int i4 = BuildCompat.$r8$clinit;
            WifiSsidPolicy wifiSsidPolicy = devicePolicyManager2.getWifiSsidPolicy();
            if (wifiSsidPolicy != null) {
                int policyType = wifiSsidPolicy.getPolicyType();
                Set<WifiSsid> ssids = wifiSsidPolicy.getSsids();
                if (policyType == 0 && !ssids.contains(WifiSsid.fromBytes(getSsid().getBytes(StandardCharsets.UTF_8)))) {
                    this.mIsAdminRestricted = true;
                    return;
                } else if (policyType == 1 && ssids.contains(WifiSsid.fromBytes(getSsid().getBytes(StandardCharsets.UTF_8)))) {
                    this.mIsAdminRestricted = true;
                    return;
                }
            }
        }
        this.mIsAdminRestricted = false;
    }

    public final synchronized void updateConfig(List list) {
        if (list == null) {
            try {
                list = Collections.EMPTY_LIST;
            } catch (Throwable th) {
                throw th;
            }
        }
        ScanResultKey scanResultKey = this.mKey.mScanResultKey;
        String str = scanResultKey.mSsid;
        Set set = scanResultKey.mSecurityTypes;
        ((ArrayMap) this.mMatchingWifiConfigs).clear();
        Iterator it = list.iterator();
        while (true) {
            boolean z = true;
            if (it.hasNext()) {
                WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
                if (!TextUtils.equals(str, WifiInfo.sanitizeSsid(wifiConfiguration.SSID))) {
                    throw new IllegalArgumentException("Attempted to update with wrong SSID! Expected: " + str + ", Actual: " + WifiInfo.sanitizeSsid(wifiConfiguration.SSID) + ", Config: " + wifiConfiguration);
                }
                for (Integer num : Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration)) {
                    int iIntValue = num.intValue();
                    ArraySet arraySet = (ArraySet) set;
                    if (!arraySet.contains(num)) {
                        throw new IllegalArgumentException("Attempted to update with wrong security! Expected one of: " + arraySet + ", Actual: " + iIntValue + ", Config: " + wifiConfiguration);
                    }
                    if (iIntValue != 4 ? iIntValue != 5 ? iIntValue != 6 ? true : this.mIsEnhancedOpenSupported : this.mIsWpa3SuiteBSupported : this.mIsWpa3SaeSupported) {
                        ((ArrayMap) this.mMatchingWifiConfigs).put(num, wifiConfiguration);
                    }
                }
            } else {
                updateSecurityTypes();
                updateTargetScanResultInfo();
                WifiConfiguration wifiConfiguration2 = this.mTargetWifiConfig;
                if (wifiConfiguration2 != null) {
                    if (wifiConfiguration2.carrierId == -1 || !wifiConfiguration2.isEphemeral() || !wifiConfiguration2.fromWifiNetworkSuggestion) {
                        z = false;
                    }
                    this.mSemFlags.isCarrierNetwork = z;
                }
                notifyOnUpdated();
            }
        }
    }

    public final synchronized void updateScanResultInfo(List list) {
        if (list == null) {
            try {
                list = new ArrayList();
            } catch (Throwable th) {
                throw th;
            }
        }
        SemWifiEntryFlags semWifiEntryFlags = this.mSemFlags;
        semWifiEntryFlags.wifiStandard = 0;
        semWifiEntryFlags.has6EStandard = false;
        semWifiEntryFlags.staCount = -1;
        String str = this.mKey.mScanResultKey.mSsid;
        int i = 0;
        for (ScanResult scanResult : list) {
            if (!TextUtils.equals(scanResult.SSID, str)) {
                throw new IllegalArgumentException("Attempted to update with wrong SSID! Expected: " + str + ", Actual: " + scanResult.SSID + ", ScanResult: " + scanResult);
            }
            semUpdateFlags(scanResult);
            int i2 = scanResult.frequency;
            if (i < i2) {
                setBand(i2);
                this.mFrequency = i2;
                i = i2;
            }
        }
        ((ArrayMap) this.mMatchingScanResults).clear();
        Set set = this.mKey.mScanResultKey.mSecurityTypes;
        for (ScanResult scanResult2 : list) {
            List list2 = Utils.defaultSsidList;
            ArrayList arrayList = new ArrayList();
            for (int i3 : scanResult2.getSecurityTypes()) {
                arrayList.add(Integer.valueOf(i3));
            }
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList.get(i4);
                i4++;
                Integer num = (Integer) obj;
                int iIntValue = num.intValue();
                if (((ArraySet) set).contains(num)) {
                    if (iIntValue != 4 ? iIntValue != 5 ? iIntValue != 6 ? true : this.mIsEnhancedOpenSupported : this.mIsWpa3SuiteBSupported : this.mIsWpa3SaeSupported) {
                        if (!((ArrayMap) this.mMatchingScanResults).containsKey(num)) {
                            ((ArrayMap) this.mMatchingScanResults).put(num, new ArrayList());
                        }
                        ((List) ((ArrayMap) this.mMatchingScanResults).get(num)).add(scanResult2);
                    }
                }
            }
        }
        updateSecurityTypes();
        updateTargetScanResultInfo();
        notifyOnUpdated();
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized void updateSecurityTypes() {
        try {
            this.mTargetSecurityTypes.clear();
            WifiInfo wifiInfo = this.mWifiInfo;
            if (wifiInfo != null && wifiInfo.getCurrentSecurityType() != -1) {
                this.mTargetSecurityTypes.add(Integer.valueOf(this.mWifiInfo.getCurrentSecurityType()));
            }
            Set setKeySet = this.mMatchingWifiConfigs.keySet();
            if (this.mTargetSecurityTypes.isEmpty() && this.mKey.mIsTargetingNewNetworks) {
                Set setKeySet2 = this.mMatchingScanResults.keySet();
                Iterator it = setKeySet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        this.mTargetSecurityTypes.addAll(setKeySet2);
                        break;
                    }
                    Integer num = (Integer) it.next();
                    num.intValue();
                    if (setKeySet2.contains(num)) {
                        break;
                    }
                }
            }
            if (this.mTargetSecurityTypes.isEmpty()) {
                this.mTargetSecurityTypes.addAll(setKeySet);
            }
            if (this.mTargetSecurityTypes.isEmpty()) {
                this.mTargetSecurityTypes.addAll(this.mKey.mScanResultKey.mSecurityTypes);
            }
            this.mTargetWifiConfig = (WifiConfiguration) this.mMatchingWifiConfigs.get(Integer.valueOf(Utils.getSingleSecurityTypeFromMultipleSecurityTypes(this.mTargetSecurityTypes)));
            ArraySet arraySet = new ArraySet();
            for (Integer num2 : this.mTargetSecurityTypes) {
                num2.intValue();
                if (this.mMatchingScanResults.containsKey(num2)) {
                    arraySet.addAll((Collection) this.mMatchingScanResults.get(num2));
                }
            }
            this.mTargetScanResults.clear();
            this.mTargetScanResults.addAll(arraySet);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void updateTargetScanResultInfo() {
        try {
            ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(this.mTargetScanResults);
            if (bestScanResultByLevel != null) {
                updateBestRssi(bestScanResultByLevel);
                this.mBssid = bestScanResultByLevel.BSSID;
            }
            if (getConnectedState() == 0) {
                int iCalculateSignalLevel = bestScanResultByLevel != null ? SemWifiUtils.calculateSignalLevel(this.mRssi) : -1;
                this.mScanResultLevel = iCalculateSignalLevel;
                if (iCalculateSignalLevel == -1) {
                    this.mRssi = -127;
                }
            }
            semUpdateQoSInformation();
        } catch (Throwable th) {
            throw th;
        }
    }

    public class ScanResultKey {
        public final Set mSecurityTypes;
        public final String mSsid;

        public ScanResultKey() {
            this.mSecurityTypes = new ArraySet();
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ScanResultKey scanResultKey = (ScanResultKey) obj;
                if (TextUtils.equals(this.mSsid, scanResultKey.mSsid) && this.mSecurityTypes.equals(scanResultKey.mSecurityTypes)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.mSsid, this.mSecurityTypes);
        }

        public final String toString() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            try {
                String str = this.mSsid;
                if (str != null) {
                    jSONObject.put("SSID", str);
                }
                if (!this.mSecurityTypes.isEmpty()) {
                    JSONArray jSONArray = new JSONArray();
                    Iterator it = this.mSecurityTypes.iterator();
                    while (it.hasNext()) {
                        jSONArray.put(((Integer) it.next()).intValue());
                    }
                    jSONObject.put("SECURITY_TYPES", jSONArray);
                }
            } catch (JSONException e) {
                Log.e("StandardWifiEntry", "JSONException while converting ScanResultKey to string: " + e);
            }
            return jSONObject.toString();
        }

        public ScanResultKey(String str, List<Integer> list) {
            this.mSecurityTypes = new ArraySet();
            this.mSsid = str;
            for (Integer num : list) {
                int iIntValue = num.intValue();
                if (iIntValue == 0) {
                    this.mSecurityTypes.add(6);
                } else if (iIntValue == 6) {
                    this.mSecurityTypes.add(0);
                } else if (iIntValue == 9) {
                    this.mSecurityTypes.add(3);
                } else if (iIntValue == 2) {
                    this.mSecurityTypes.add(4);
                } else if (iIntValue == 3) {
                    this.mSecurityTypes.add(9);
                } else if (iIntValue == 4) {
                    this.mSecurityTypes.add(2);
                } else if (iIntValue == 11 || iIntValue == 12) {
                }
                this.mSecurityTypes.add(num);
            }
        }

        public ScanResultKey(ScanResult scanResult) {
            String str = scanResult.SSID;
            List list = Utils.defaultSsidList;
            ArrayList arrayList = new ArrayList();
            for (int i : scanResult.getSecurityTypes()) {
                arrayList.add(Integer.valueOf(i));
            }
            this(str, arrayList);
        }

        public ScanResultKey(WifiConfiguration wifiConfiguration) {
            this(WifiInfo.sanitizeSsid(wifiConfiguration.SSID), Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration));
        }

        public ScanResultKey(String str) throws JSONException {
            this.mSecurityTypes = new ArraySet();
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.mSsid = jSONObject.getString("SSID");
                JSONArray jSONArray = jSONObject.getJSONArray("SECURITY_TYPES");
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.mSecurityTypes.add(Integer.valueOf(jSONArray.getInt(i)));
                }
            } catch (JSONException e) {
                Log.wtf("StandardWifiEntry", "JSONException while constructing ScanResultKey from string: " + e);
            }
        }
    }

    public final synchronized String getScanResultDescription(final int i, final int i2) {
        final int i3 = 0;
        List list = (List) ((ArrayMap) this.mMatchingScanResults).values().stream().flatMap(new StandardWifiEntry$$ExternalSyntheticLambda0()).distinct().filter(new Predicate() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i4 = i;
                int i5 = i2;
                int i6 = StandardWifiEntry.$r8$clinit;
                int i7 = ((ScanResult) obj).frequency;
                return i7 >= i4 && i7 <= i5;
            }
        }).sorted(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                ScanResult scanResult = (ScanResult) obj;
                switch (i3) {
                    case 0:
                        int i4 = StandardWifiEntry.$r8$clinit;
                        return scanResult.level * (-1);
                    default:
                        int i5 = StandardWifiEntry.$r8$clinit;
                        return scanResult.level;
                }
            }
        })).collect(Collectors.toList());
        int size = list.size();
        if (size == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(size);
        sb.append(")");
        if (size > 4) {
            final int i4 = 1;
            int asInt = list.stream().mapToInt(new ToIntFunction() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda2
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    ScanResult scanResult = (ScanResult) obj;
                    switch (i4) {
                        case 0:
                            int i42 = StandardWifiEntry.$r8$clinit;
                            return scanResult.level * (-1);
                        default:
                            int i5 = StandardWifiEntry.$r8$clinit;
                            return scanResult.level;
                    }
                }
            }).max().getAsInt();
            sb.append("max=");
            sb.append(asInt);
            sb.append(",");
        }
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        list.forEach(new Consumer() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String string;
                StandardWifiEntry standardWifiEntry = this.f$0;
                StringBuilder sb2 = sb;
                long j = jElapsedRealtime;
                ScanResult scanResult = (ScanResult) obj;
                int i5 = StandardWifiEntry.$r8$clinit;
                synchronized (standardWifiEntry) {
                    try {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(" \n{");
                        sb3.append(scanResult.BSSID);
                        WifiInfo wifiInfo = standardWifiEntry.mWifiInfo;
                        if (wifiInfo != null && scanResult.BSSID.equals(wifiInfo.getBSSID())) {
                            sb3.append("*");
                        }
                        sb3.append("=");
                        sb3.append(scanResult.frequency);
                        sb3.append(",");
                        sb3.append(scanResult.level);
                        int wifiStandard = scanResult.getWifiStandard();
                        sb3.append(",");
                        sb3.append(Utils.getStandardString(wifiStandard, standardWifiEntry.mContext));
                        int i6 = BuildCompat.$r8$clinit;
                        if (wifiStandard == 8) {
                            sb3.append(",mldMac=");
                            sb3.append(scanResult.getApMldMacAddress());
                            sb3.append(",linkId=");
                            sb3.append(scanResult.getApMloLinkId());
                            sb3.append(",affLinks=");
                            StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
                            for (MloLink mloLink : scanResult.getAffiliatedMloLinks()) {
                                int band = mloLink.getBand();
                                int i7 = 1;
                                if (band != 1) {
                                    i7 = 2;
                                    if (band != 2) {
                                        if (band != 8) {
                                            i7 = 16;
                                            if (band != 16) {
                                                Log.e("StandardWifiEntry", "Unknown MLO link band: " + mloLink.getBand());
                                                i7 = -1;
                                            }
                                        } else {
                                            i7 = 8;
                                        }
                                    }
                                }
                                stringJoiner.add(new StringJoiner(",", "{", "}").add("apMacAddr=" + mloLink.getApMacAddress()).add("freq=" + ScanResult.convertChannelToFrequencyMhzIfSupported(mloLink.getChannel(), i7)).toString());
                            }
                            sb3.append(stringJoiner.toString());
                        }
                        int i8 = ((int) (j - (scanResult.timestamp / 1000))) / 1000;
                        sb3.append(",");
                        sb3.append(i8);
                        sb3.append("s");
                        sb3.append("}");
                        string = sb3.toString();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                sb2.append(string);
            }
        });
        return sb.toString();
    }

    public StandardWifiEntry(WifiTrackerInjector wifiTrackerInjector, Handler handler, StandardWifiEntryKey standardWifiEntryKey, List<WifiConfiguration> list, List<ScanResult> list2, WifiManager wifiManager, boolean z) throws IllegalArgumentException {
        this(wifiTrackerInjector, handler, standardWifiEntryKey, wifiManager, z);
        if (list != null && !list.isEmpty()) {
            updateConfig(list);
        }
        if (list2 == null || list2.isEmpty()) {
            return;
        }
        updateScanResultInfo(list2);
    }
}
