package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.app.admin.WifiSsidPolicy;
import android.content.Context;
import android.net.Network;
import android.net.NetworkCapabilities;
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
import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public final String toString() {
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
            int intValue = ((Integer) obj).intValue();
            if (intValue == 0 || intValue == 1 || intValue == 2 || intValue == 4 || intValue == 6) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public final synchronized boolean canSignIn() {
        boolean z;
        NetworkCapabilities networkCapabilities;
        if (this.mNetwork != null && (networkCapabilities = this.mNetworkCapabilities) != null) {
            z = networkCapabilities.hasCapability(17);
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x016e  */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void connect(final com.android.wifitrackerlib.WifiEntry.ConnectCallback r9) {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.StandardWifiEntry.connect(com.android.wifitrackerlib.WifiEntry$ConnectCallback):void");
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
        String stringJoiner;
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
                StringJoiner stringJoiner2 = new StringJoiner(" + ");
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
                        stringJoiner2.add(Utils.getBandString(i3, context));
                    }
                }
                stringJoiner = stringJoiner2.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        return TextUtils.isEmpty(stringJoiner) ? getBandString() : stringJoiner;
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
            int intValue = ((Integer) ((ArrayList) this.mTargetSecurityTypes).get(0)).intValue();
            if (intValue == 9) {
                return this.mContext.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa3);
            }
            switch (intValue) {
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

    /* JADX WARN: Removed duplicated region for block: B:82:0x016c A[Catch: all -> 0x0014, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:10:0x0017, B:14:0x0031, B:15:0x0081, B:17:0x0085, B:18:0x008e, B:20:0x0094, B:22:0x009d, B:24:0x00a9, B:25:0x00ac, B:28:0x00b2, B:30:0x00b8, B:32:0x00c4, B:33:0x00c7, B:35:0x00cd, B:37:0x00d7, B:39:0x00dd, B:41:0x00e3, B:43:0x00e7, B:45:0x00ed, B:48:0x00f2, B:57:0x0103, B:58:0x010b, B:59:0x0113, B:60:0x011b, B:62:0x0122, B:64:0x0128, B:65:0x012b, B:67:0x0131, B:69:0x0137, B:71:0x013d, B:73:0x0141, B:75:0x0145, B:79:0x014c, B:80:0x0166, B:82:0x016c, B:83:0x0158, B:86:0x0171, B:88:0x0177, B:90:0x018b, B:91:0x018f, B:93:0x0195, B:94:0x0199, B:96:0x01a5, B:97:0x01a8, B:99:0x01b0, B:101:0x01b6, B:104:0x01bd, B:106:0x01c6, B:107:0x01cb, B:109:0x01d1, B:110:0x01d9, B:112:0x01df, B:113:0x01e3, B:115:0x01e9, B:117:0x01ef, B:118:0x0205, B:120:0x0211, B:121:0x0214, B:124:0x0044, B:125:0x0051, B:129:0x005e, B:131:0x0074, B:133:0x0077), top: B:3:0x0003 }] */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String getSummary(boolean r11) {
        /*
            Method dump skipped, instructions count: 540
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.StandardWifiEntry.getSummary(boolean):java.lang.String");
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
            if (this.mHasAddConfigUserRestriction) {
                if (!isSaved()) {
                    if (isSuggestion()) {
                    }
                    return true;
                }
            }
            if (!this.mIsAdminRestricted) {
                return false;
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

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000e, code lost:
    
        if (r0.meteredHint != false) goto L13;
     */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isMetered() {
        /*
            r2 = this;
            monitor-enter(r2)
            int r0 = r2.getMeteredChoice()     // Catch: java.lang.Throwable -> L11
            r1 = 1
            if (r0 == r1) goto L14
            android.net.wifi.WifiConfiguration r0 = r2.mTargetWifiConfig     // Catch: java.lang.Throwable -> L11
            if (r0 == 0) goto L13
            boolean r0 = r0.meteredHint     // Catch: java.lang.Throwable -> L11
            if (r0 == 0) goto L13
            goto L14
        L11:
            r0 = move-exception
            goto L16
        L13:
            r1 = 0
        L14:
            monitor-exit(r2)
            return r1
        L16:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L11
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.StandardWifiEntry.isMetered():boolean");
    }

    @Override // com.android.wifitrackerlib.WifiEntry
    public synchronized boolean isSaved() {
        return this.mTargetWifiConfig != null;
    }

    @Override // com.android.wifitrackerlib.WifiEntry
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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0031, code lost:
    
        if (r0.getDisableReasonCounter(5) > 0) goto L24;
     */
    @Override // com.android.wifitrackerlib.WifiEntry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean shouldEditBeforeConnect() {
        /*
            r3 = this;
            monitor-enter(r3)
            android.net.wifi.WifiConfiguration r0 = r3.getWifiConfiguration()     // Catch: java.lang.Throwable -> L1b
            r1 = 0
            if (r0 != 0) goto La
            monitor-exit(r3)
            return r1
        La:
            android.net.wifi.WifiConfiguration$NetworkSelectionStatus r0 = r0.getNetworkSelectionStatus()     // Catch: java.lang.Throwable -> L1b
            int r2 = r0.getNetworkSelectionStatus()     // Catch: java.lang.Throwable -> L1b
            if (r2 != 0) goto L1d
            boolean r2 = r0.hasEverConnected()     // Catch: java.lang.Throwable -> L1b
            if (r2 != 0) goto L34
            goto L1d
        L1b:
            r0 = move-exception
            goto L39
        L1d:
            r2 = 2
            int r2 = r0.getDisableReasonCounter(r2)     // Catch: java.lang.Throwable -> L1b
            if (r2 > 0) goto L36
            r2 = 8
            int r2 = r0.getDisableReasonCounter(r2)     // Catch: java.lang.Throwable -> L1b
            if (r2 > 0) goto L36
            r2 = 5
            int r0 = r0.getDisableReasonCounter(r2)     // Catch: java.lang.Throwable -> L1b
            if (r0 <= 0) goto L34
            goto L36
        L34:
            monitor-exit(r3)
            return r1
        L36:
            monitor-exit(r3)
            r3 = 1
            return r3
        L39:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L1b
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.StandardWifiEntry.shouldEditBeforeConnect():boolean");
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
                    int intValue = ((Integer) obj).intValue();
                    List list = Utils.defaultSsidList;
                    switch (intValue) {
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
                    int intValue = num.intValue();
                    ArraySet arraySet = (ArraySet) set;
                    if (!arraySet.contains(num)) {
                        throw new IllegalArgumentException("Attempted to update with wrong security! Expected one of: " + arraySet + ", Actual: " + intValue + ", Config: " + wifiConfiguration);
                    }
                    if (intValue != 4 ? intValue != 5 ? intValue != 6 ? true : this.mIsEnhancedOpenSupported : this.mIsWpa3SuiteBSupported : this.mIsWpa3SaeSupported) {
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
                int intValue = num.intValue();
                if (((ArraySet) set).contains(num)) {
                    if (intValue != 4 ? intValue != 5 ? intValue != 6 ? true : this.mIsEnhancedOpenSupported : this.mIsWpa3SuiteBSupported : this.mIsWpa3SaeSupported) {
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
            Set keySet = this.mMatchingWifiConfigs.keySet();
            if (this.mTargetSecurityTypes.isEmpty() && this.mKey.mIsTargetingNewNetworks) {
                Set keySet2 = this.mMatchingScanResults.keySet();
                Iterator it = keySet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        this.mTargetSecurityTypes.addAll(keySet2);
                        break;
                    }
                    Integer num = (Integer) it.next();
                    num.intValue();
                    if (keySet2.contains(num)) {
                        break;
                    }
                }
            }
            if (this.mTargetSecurityTypes.isEmpty()) {
                this.mTargetSecurityTypes.addAll(keySet);
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
                int calculateSignalLevel = bestScanResultByLevel != null ? SemWifiUtils.calculateSignalLevel(this.mRssi) : -1;
                this.mScanResultLevel = calculateSignalLevel;
                if (calculateSignalLevel == -1) {
                    this.mRssi = -127;
                }
            }
            semUpdateQoSInformation();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public final String toString() {
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
                int intValue = num.intValue();
                if (intValue == 0) {
                    this.mSecurityTypes.add(6);
                } else if (intValue == 6) {
                    this.mSecurityTypes.add(0);
                } else if (intValue == 9) {
                    this.mSecurityTypes.add(3);
                } else if (intValue == 2) {
                    this.mSecurityTypes.add(4);
                } else if (intValue == 3) {
                    this.mSecurityTypes.add(9);
                } else if (intValue == 4) {
                    this.mSecurityTypes.add(2);
                } else if (intValue != 11 && intValue != 12) {
                }
                this.mSecurityTypes.add(num);
            }
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ScanResultKey(android.net.wifi.ScanResult r6) {
            /*
                r5 = this;
                java.lang.String r0 = r6.SSID
                java.util.List r1 = com.android.wifitrackerlib.Utils.defaultSsidList
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                int[] r6 = r6.getSecurityTypes()
                int r2 = r6.length
                r3 = 0
            Lf:
                if (r3 >= r2) goto L1d
                r4 = r6[r3]
                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                r1.add(r4)
                int r3 = r3 + 1
                goto Lf
            L1d:
                r5.<init>(r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.StandardWifiEntry.ScanResultKey.<init>(android.net.wifi.ScanResult):void");
        }

        public ScanResultKey(WifiConfiguration wifiConfiguration) {
            this(WifiInfo.sanitizeSsid(wifiConfiguration.SSID), Utils.getSecurityTypesFromWifiConfiguration(wifiConfiguration));
        }

        public ScanResultKey(String str) {
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
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        list.forEach(new Consumer() { // from class: com.android.wifitrackerlib.StandardWifiEntry$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String sb2;
                StandardWifiEntry standardWifiEntry = StandardWifiEntry.this;
                StringBuilder sb3 = sb;
                long j = elapsedRealtime;
                ScanResult scanResult = (ScanResult) obj;
                int i5 = StandardWifiEntry.$r8$clinit;
                synchronized (standardWifiEntry) {
                    try {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(" \n{");
                        sb4.append(scanResult.BSSID);
                        WifiInfo wifiInfo = standardWifiEntry.mWifiInfo;
                        if (wifiInfo != null && scanResult.BSSID.equals(wifiInfo.getBSSID())) {
                            sb4.append("*");
                        }
                        sb4.append("=");
                        sb4.append(scanResult.frequency);
                        sb4.append(",");
                        sb4.append(scanResult.level);
                        int wifiStandard = scanResult.getWifiStandard();
                        sb4.append(",");
                        sb4.append(Utils.getStandardString(wifiStandard, standardWifiEntry.mContext));
                        int i6 = BuildCompat.$r8$clinit;
                        if (wifiStandard == 8) {
                            sb4.append(",mldMac=");
                            sb4.append(scanResult.getApMldMacAddress());
                            sb4.append(",linkId=");
                            sb4.append(scanResult.getApMloLinkId());
                            sb4.append(",affLinks=");
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
                            sb4.append(stringJoiner.toString());
                        }
                        int i8 = ((int) (j - (scanResult.timestamp / 1000))) / 1000;
                        sb4.append(",");
                        sb4.append(i8);
                        sb4.append("s");
                        sb4.append("}");
                        sb2 = sb4.toString();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                sb3.append(sb2);
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
