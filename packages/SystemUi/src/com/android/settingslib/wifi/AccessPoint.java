package com.android.settingslib.wifi;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.ScoredNetwork;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.internal.util.CollectionUtils;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.settings.ImsProfile;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessPoint implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String bssid;
    public WifiConfiguration mConfig;
    public final Context mContext;
    public final ArraySet mExtraScanResults;
    public WifiInfo mInfo;
    public boolean mIsOweTransitionMode;
    public boolean mIsPskSaeTransitionMode;
    public boolean mIsScoredNetworkMetered;
    public String mKey;
    public final Object mLock;
    public NetworkInfo mNetworkInfo;
    public String mOsuFailure;
    public final OsuProvider mOsuProvider;
    public boolean mOsuProvisioningComplete;
    public String mOsuStatus;
    public final int mPasspointConfigurationVersion;
    public final String mPasspointUniqueId;
    public final String mProviderFriendlyName;
    public int mRssi;
    public final ArraySet mScanResults;
    public final Map mScoredNetworkCache;
    public SemWifiManager mSemWifiManager;
    public int mSpeed;
    public final long mSubscriptionExpirationTimeInMillis;
    public WifiManager mWifiManager;
    public int networkId;
    public int pskType;
    public int security;
    public String smartApUserName;
    public String ssid;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class AccessPointProvisioningCallback extends ProvisioningCallback {
        public AccessPointProvisioningCallback() {
        }

        public final void onProvisioningComplete() {
            AccessPoint accessPoint = AccessPoint.this;
            accessPoint.mOsuProvisioningComplete = true;
            accessPoint.mOsuFailure = null;
            accessPoint.mOsuStatus = null;
            ThreadUtils.postOnMainThread(new RunnableC0942xb3759df5(this, 1));
            WifiManager wifiManager = AccessPoint.this.getWifiManager();
            PasspointConfiguration passpointConfiguration = (PasspointConfiguration) wifiManager.getMatchingPasspointConfigsForOsuProviders(Collections.singleton(AccessPoint.this.mOsuProvider)).get(AccessPoint.this.mOsuProvider);
            if (passpointConfiguration == null) {
                Log.e("SettingsLib.AccessPoint", "Missing PasspointConfiguration for newly provisioned network!");
                AccessPoint.this.getClass();
                return;
            }
            String uniqueId = passpointConfiguration.getUniqueId();
            for (Pair pair : wifiManager.getAllMatchingWifiConfigs(wifiManager.getScanResults())) {
                WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
                if (TextUtils.equals(wifiConfiguration.getKey(), uniqueId)) {
                    WifiConfiguration wifiConfiguration2 = new AccessPoint(AccessPoint.this.mContext, wifiConfiguration, (List) ((Map) pair.second).get(0), (List) ((Map) pair.second).get(1)).mConfig;
                    AccessPoint.this.getClass();
                    wifiManager.connect(wifiConfiguration2, null);
                    return;
                }
            }
            AccessPoint.this.getClass();
        }

        public final void onProvisioningFailure(int i) {
            AccessPoint accessPoint = AccessPoint.this;
            if (TextUtils.equals(accessPoint.mOsuStatus, accessPoint.mContext.getString(R.string.osu_completing_sign_up))) {
                AccessPoint accessPoint2 = AccessPoint.this;
                accessPoint2.mOsuFailure = accessPoint2.mContext.getString(R.string.osu_sign_up_failed);
            } else {
                AccessPoint accessPoint3 = AccessPoint.this;
                accessPoint3.mOsuFailure = accessPoint3.mContext.getString(R.string.osu_connect_failed);
            }
            AccessPoint accessPoint4 = AccessPoint.this;
            accessPoint4.mOsuStatus = null;
            accessPoint4.mOsuProvisioningComplete = false;
            ThreadUtils.postOnMainThread(new RunnableC0942xb3759df5(this, 2));
        }

        public final void onProvisioningStatus(int i) {
            String format;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    format = String.format(AccessPoint.this.mContext.getString(R.string.osu_opening_provider), AccessPoint.this.mOsuProvider.getFriendlyName());
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    format = AccessPoint.this.mContext.getString(R.string.osu_completing_sign_up);
                    break;
                default:
                    format = null;
                    break;
            }
            boolean z = !TextUtils.equals(AccessPoint.this.mOsuStatus, format);
            AccessPoint accessPoint = AccessPoint.this;
            accessPoint.mOsuStatus = format;
            accessPoint.mOsuFailure = null;
            accessPoint.mOsuProvisioningComplete = false;
            if (z) {
                ThreadUtils.postOnMainThread(new RunnableC0942xb3759df5(this, 0));
            }
        }
    }

    static {
        new AtomicInteger(0);
    }

    public AccessPoint(Context context, Bundle bundle) {
        this.mLock = new Object();
        ArraySet arraySet = new ArraySet();
        this.mScanResults = arraySet;
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        if (bundle.containsKey("key_config")) {
            this.mConfig = (WifiConfiguration) bundle.getParcelable("key_config");
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            loadConfig(wifiConfiguration);
        }
        if (bundle.containsKey("key_ssid")) {
            this.ssid = bundle.getString("key_ssid");
        }
        if (bundle.containsKey("key_security")) {
            this.security = bundle.getInt("key_security");
        }
        if (bundle.containsKey("key_speed")) {
            this.mSpeed = bundle.getInt("key_speed");
        }
        if (bundle.containsKey("key_psktype")) {
            this.pskType = bundle.getInt("key_psktype");
        }
        if (bundle.containsKey("eap_psktype")) {
            bundle.getInt("eap_psktype");
        }
        this.mInfo = (WifiInfo) bundle.getParcelable("key_wifiinfo");
        if (bundle.containsKey("key_networkinfo")) {
            this.mNetworkInfo = (NetworkInfo) bundle.getParcelable("key_networkinfo");
        }
        if (bundle.containsKey("key_scanresults")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("key_scanresults");
            arraySet.clear();
            for (Parcelable parcelable : parcelableArray) {
                this.mScanResults.add((ScanResult) parcelable);
            }
        }
        if (bundle.containsKey("key_scorednetworkcache")) {
            Iterator it = bundle.getParcelableArrayList("key_scorednetworkcache").iterator();
            while (it.hasNext()) {
                TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) it.next();
                this.mScoredNetworkCache.put(timestampedScoredNetwork.mScore.networkKey.wifiKey.bssid, timestampedScoredNetwork);
            }
        }
        if (bundle.containsKey("key_passpoint_unique_id")) {
            this.mPasspointUniqueId = bundle.getString("key_passpoint_unique_id");
        }
        if (bundle.containsKey("key_fqdn")) {
            bundle.getString("key_fqdn");
        }
        if (bundle.containsKey("key_provider_friendly_name")) {
            this.mProviderFriendlyName = bundle.getString("key_provider_friendly_name");
        }
        if (bundle.containsKey("key_subscription_expiration_time_in_millis")) {
            this.mSubscriptionExpirationTimeInMillis = bundle.getLong("key_subscription_expiration_time_in_millis");
        }
        if (bundle.containsKey("key_passpoint_configuration_version")) {
            this.mPasspointConfigurationVersion = bundle.getInt("key_passpoint_configuration_version");
        }
        if (bundle.containsKey("key_is_psk_sae_transition_mode")) {
            this.mIsPskSaeTransitionMode = bundle.getBoolean("key_is_psk_sae_transition_mode");
        }
        if (bundle.containsKey("key_is_owe_transition_mode")) {
            this.mIsOweTransitionMode = bundle.getBoolean("key_is_owe_transition_mode");
        }
        update(this.mConfig, this.mInfo, this.mNetworkInfo);
        updateKey();
        updateBestRssiInfo();
    }

    public static String getKey(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            return KeyAttributes$$ExternalSyntheticOutline0.m21m("PASSPOINT:", wifiConfiguration.getKey());
        }
        return getKey(getSecurity(wifiConfiguration), removeDoubleQuotes(wifiConfiguration.SSID), wifiConfiguration.BSSID);
    }

    public static int getSecurity(Context context, ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WEP");
        boolean contains2 = scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE);
        boolean contains3 = scanResult.capabilities.contains("PSK");
        boolean contains4 = scanResult.capabilities.contains("EAP_SUITE_B_192");
        boolean contains5 = scanResult.capabilities.contains("EAP");
        boolean contains6 = scanResult.capabilities.contains("OWE");
        boolean contains7 = scanResult.capabilities.contains("OWE_TRANSITION");
        if (contains2 && contains3) {
            return ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).isWpa3SaeSupported() ? 5 : 2;
        }
        if (contains7) {
            return ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).isEnhancedOpenSupported() ? 4 : 0;
        }
        if (contains) {
            return 1;
        }
        if (contains2) {
            return 5;
        }
        if (contains3) {
            return 2;
        }
        if (contains4) {
            return 6;
        }
        if (contains5) {
            return 3;
        }
        return contains6 ? 4 : 0;
    }

    public static String getSpeedLabel(int i, Context context) {
        if (i == 5) {
            return context.getString(R.string.speed_label_slow);
        }
        if (i == 10) {
            return context.getString(R.string.speed_label_okay);
        }
        if (i == 20) {
            return context.getString(R.string.speed_label_fast);
        }
        if (i != 30) {
            return null;
        }
        return context.getString(R.string.speed_label_very_fast);
    }

    public static String removeDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        return str.charAt(i) == '\"' ? str.substring(1, i) : str;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof AccessPoint) && compareTo((AccessPoint) obj) == 0;
    }

    public final NetworkInfo.DetailedState getDetailedState() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo != null) {
            return networkInfo.getDetailedState();
        }
        Log.w("SettingsLib.AccessPoint", "NetworkInfo is null, cannot return detailed state");
        return null;
    }

    public final int getLevel() {
        return getWifiManager().calculateSignalLevel(this.mRssi);
    }

    public final String getTitle() {
        if (isPasspoint() && !TextUtils.isEmpty(this.mConfig.providerFriendlyName)) {
            return this.mConfig.providerFriendlyName;
        }
        if ((this.mPasspointUniqueId != null && this.mConfig == null) && !TextUtils.isEmpty(this.mProviderFriendlyName)) {
            return this.mProviderFriendlyName;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        return (!(osuProvider != null) || TextUtils.isEmpty(osuProvider.getFriendlyName())) ? !TextUtils.isEmpty(this.ssid) ? this.ssid : "" : this.mOsuProvider.getFriendlyName();
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        }
        return this.mWifiManager;
    }

    public final int hashCode() {
        WifiInfo wifiInfo = this.mInfo;
        return (this.ssid.hashCode() * 29) + (this.networkId * 23) + (this.mRssi * 19) + (wifiInfo != null ? 0 + (wifiInfo.hashCode() * 13) : 0);
    }

    public final boolean isActive() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        return (networkInfo == null || (this.networkId == -1 && networkInfo.getState() == NetworkInfo.State.DISCONNECTED)) ? false : true;
    }

    public final boolean isPasspoint() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        return wifiConfiguration != null && wifiConfiguration.isPasspoint();
    }

    public void loadConfig(WifiConfiguration wifiConfiguration) {
        String str = wifiConfiguration.SSID;
        this.ssid = str == null ? "" : removeDoubleQuotes(str);
        this.bssid = wifiConfiguration.BSSID;
        this.security = getSecurity(wifiConfiguration);
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
    }

    public final boolean matches(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            return isPasspoint() && wifiConfiguration.getKey().equals(this.mConfig.getKey());
        }
        if (!this.ssid.equals(removeDoubleQuotes(wifiConfiguration.SSID))) {
            return false;
        }
        WifiConfiguration wifiConfiguration2 = this.mConfig;
        if (wifiConfiguration2 != null && wifiConfiguration2.shared != wifiConfiguration.shared) {
            return false;
        }
        int security = getSecurity(wifiConfiguration);
        if (this.mIsPskSaeTransitionMode && ((security == 5 && getWifiManager().isWpa3SaeSupported()) || security == 2)) {
            return true;
        }
        return (this.mIsOweTransitionMode && ((security == 4 && getWifiManager().isEnhancedOpenSupported()) || security == 0)) || this.security == getSecurity(wifiConfiguration);
    }

    public void setRssi(int i) {
        this.mRssi = i;
    }

    public final void setScanResults(Collection collection) {
        if (CollectionUtils.isEmpty(collection)) {
            Log.d("SettingsLib.AccessPoint", "Cannot set scan results to empty list");
            return;
        }
        if (this.mKey != null && !isPasspoint()) {
            if (!(this.mOsuProvider != null)) {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    ScanResult scanResult = (ScanResult) it.next();
                    if (!matches(scanResult)) {
                        Log.d("SettingsLib.AccessPoint", String.format("ScanResult %s\nkey of %s did not match current AP key %s", scanResult, getKey(getSecurity(this.mContext, scanResult), scanResult.SSID, scanResult.BSSID), this.mKey));
                        return;
                    }
                }
            }
        }
        int level = getLevel();
        synchronized (this.mLock) {
            this.mScanResults.clear();
            this.mScanResults.addAll(collection);
        }
        updateBestRssiInfo();
        int level2 = getLevel();
        if (level2 > 0 && level2 != level) {
            updateSpeed();
            ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 1));
        }
        ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 2));
    }

    public final void setScanResultsPasspoint(Collection collection, Collection collection2) {
        synchronized (this.mLock) {
            this.mExtraScanResults.clear();
            if (!CollectionUtils.isEmpty(collection)) {
                if (!CollectionUtils.isEmpty(collection2)) {
                    this.mExtraScanResults.addAll(collection2);
                }
                setScanResults(collection);
            } else if (!CollectionUtils.isEmpty(collection2)) {
                setScanResults(collection2);
            }
        }
    }

    public final String toString() {
        NetworkInfo networkInfo;
        StringBuilder sb = new StringBuilder("AccessPoint(");
        sb.append(this.ssid);
        if (this.bssid != null) {
            sb.append(":");
            sb.append(this.bssid);
        }
        boolean z = true;
        if (this.mConfig != null) {
            sb.append(",saved");
        }
        if (isActive()) {
            sb.append(",active");
        }
        WifiInfo wifiInfo = this.mInfo;
        if ((wifiInfo == null || !wifiInfo.isEphemeral() || (networkInfo = this.mNetworkInfo) == null || networkInfo.getState() == NetworkInfo.State.DISCONNECTED) ? false : true) {
            sb.append(",ephemeral");
        }
        if (getLevel() != -1 && getDetailedState() == null) {
            sb.append(",connectable");
        }
        int i = this.security;
        if (i != 0 && i != 4) {
            sb.append(',');
            int i2 = this.security;
            int i3 = this.pskType;
            sb.append(i2 == 1 ? "WEP" : i2 == 2 ? i3 == 1 ? "WPA" : i3 == 2 ? "WPA2" : i3 == 3 ? "WPA_WPA2" : "PSK" : i2 == 3 ? "EAP" : i2 == 5 ? WifiPolicy.SECURITY_TYPE_SAE : i2 == 6 ? "SUITE_B" : i2 == 4 ? "OWE" : PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE);
        }
        sb.append(",level=");
        sb.append(getLevel());
        if (this.mSpeed != 0) {
            sb.append(",speed=");
            sb.append(this.mSpeed);
        }
        sb.append(",metered=");
        if (!this.mIsScoredNetworkMetered && !WifiConfiguration.isMetered(this.mConfig, this.mInfo)) {
            z = false;
        }
        sb.append(z);
        if (WifiTracker.sVerboseLogging) {
            sb.append(",rssi=");
            sb.append(this.mRssi);
            synchronized (this.mLock) {
                sb.append(",scan cache size=");
                sb.append(this.mScanResults.size() + this.mExtraScanResults.size());
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public final boolean update(WifiNetworkScoreCache wifiNetworkScoreCache, boolean z, long j) {
        boolean z2;
        WifiInfo wifiInfo;
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (this.mLock) {
                try {
                    Iterator it = this.mScanResults.iterator();
                    while (it.hasNext()) {
                        ScanResult scanResult = (ScanResult) it.next();
                        ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(scanResult);
                        if (scoredNetwork != null) {
                            TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) ((HashMap) this.mScoredNetworkCache).get(scanResult.BSSID);
                            if (timestampedScoredNetwork == null) {
                                ((HashMap) this.mScoredNetworkCache).put(scanResult.BSSID, new TimestampedScoredNetwork(scoredNetwork, elapsedRealtime));
                            } else {
                                timestampedScoredNetwork.mScore = scoredNetwork;
                                timestampedScoredNetwork.mUpdatedTimestampMillis = elapsedRealtime;
                            }
                        }
                    }
                } finally {
                }
            }
            final long j2 = elapsedRealtime - j;
            final Iterator it2 = ((HashMap) this.mScoredNetworkCache).values().iterator();
            it2.forEachRemaining(new Consumer() { // from class: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    long j3 = j2;
                    Iterator it3 = it2;
                    if (((TimestampedScoredNetwork) obj).mUpdatedTimestampMillis < j3) {
                        it3.remove();
                    }
                }
            });
            z2 = updateSpeed();
        } else {
            z2 = false;
        }
        boolean z3 = this.mIsScoredNetworkMetered;
        this.mIsScoredNetworkMetered = false;
        if (!isActive() || (wifiInfo = this.mInfo) == null) {
            synchronized (this.mLock) {
                Iterator it3 = this.mScanResults.iterator();
                while (it3.hasNext()) {
                    ScoredNetwork scoredNetwork2 = wifiNetworkScoreCache.getScoredNetwork((ScanResult) it3.next());
                    if (scoredNetwork2 != null) {
                        this.mIsScoredNetworkMetered = scoredNetwork2.meteredHint | this.mIsScoredNetworkMetered;
                    }
                }
            }
        } else {
            ScoredNetwork scoredNetwork3 = wifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(wifiInfo));
            if (scoredNetwork3 != null) {
                this.mIsScoredNetworkMetered = scoredNetwork3.meteredHint | this.mIsScoredNetworkMetered;
            }
        }
        return (z3 != this.mIsScoredNetworkMetered) || z2;
    }

    public final void updateBestRssiInfo() {
        ScanResult scanResult;
        int i;
        int i2;
        if (isActive()) {
            return;
        }
        synchronized (this.mLock) {
            Iterator it = this.mScanResults.iterator();
            scanResult = null;
            i = Integer.MIN_VALUE;
            while (it.hasNext()) {
                ScanResult scanResult2 = (ScanResult) it.next();
                int i3 = scanResult2.level;
                if (i3 > i) {
                    scanResult = scanResult2;
                    i = i3;
                }
            }
        }
        int i4 = 2;
        if (i == Integer.MIN_VALUE || (i2 = this.mRssi) == Integer.MIN_VALUE) {
            this.mRssi = i;
        } else {
            this.mRssi = (i2 + i) / 2;
        }
        if (scanResult != null) {
            this.ssid = scanResult.SSID;
            this.bssid = scanResult.BSSID;
            int security = getSecurity(this.mContext, scanResult);
            this.security = security;
            if (security == 2 || security == 5) {
                boolean contains = scanResult.capabilities.contains("WPA-PSK");
                boolean contains2 = scanResult.capabilities.contains("RSN-PSK");
                boolean contains3 = scanResult.capabilities.contains("RSN-SAE");
                if (contains2 && contains) {
                    i4 = 3;
                } else if (!contains2) {
                    if (contains) {
                        i4 = 1;
                    } else {
                        if (!contains3) {
                            Log.w("SettingsLib.AccessPoint", "Received abnormal flag string: " + scanResult.capabilities);
                        }
                        i4 = 0;
                    }
                }
                this.pskType = i4;
            }
            if (this.security == 3 && !scanResult.capabilities.contains("RSN-EAP")) {
                scanResult.capabilities.contains("WPA-EAP");
            }
            this.mIsPskSaeTransitionMode = scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE);
            this.mIsOweTransitionMode = scanResult.capabilities.contains("OWE_TRANSITION");
        }
        if (isPasspoint()) {
            this.mConfig.SSID = PathParser$$ExternalSyntheticOutline0.m29m("\"", this.ssid, "\"");
        }
    }

    public final void updateKey() {
        if (isPasspoint()) {
            this.mKey = getKey(this.mConfig);
            return;
        }
        String str = this.mPasspointUniqueId;
        if (str != null && this.mConfig == null) {
            this.mKey = KeyAttributes$$ExternalSyntheticOutline0.m21m("PASSPOINT:", str);
            return;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        if (osuProvider != null) {
            this.mKey = getKey(osuProvider);
        } else {
            this.mKey = getKey(this.security, this.ssid, this.bssid);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateSpeed() {
        int i;
        int i2 = this.mSpeed;
        if (!((HashMap) this.mScoredNetworkCache).isEmpty()) {
            if (Log.isLoggable("SettingsLib.AccessPoint", 3)) {
                Log.d("SettingsLib.AccessPoint", String.format("Generating fallbackspeed for %s using cache: %s", this.ssid, this.mScoredNetworkCache));
            }
            Iterator it = ((HashMap) this.mScoredNetworkCache).values().iterator();
            int i3 = 0;
            int i4 = 0;
            while (it.hasNext()) {
                int calculateBadge = ((TimestampedScoredNetwork) it.next()).mScore.calculateBadge(this.mRssi);
                if (calculateBadge != 0) {
                    i3++;
                    i4 += calculateBadge;
                }
            }
            int i5 = i3 == 0 ? 0 : i4 / i3;
            if (WifiTracker.sVerboseLogging) {
                Log.i("SettingsLib.AccessPoint", String.format("%s generated fallback speed is: %d", this.ssid, Integer.valueOf(i5)));
            }
            i = 5;
            if (i5 >= 5) {
                if (i5 >= 7) {
                    i = i5 < 15 ? 10 : i5 < 25 ? 20 : 30;
                }
                this.mSpeed = i;
                boolean z = i2 != i;
                if (WifiTracker.sVerboseLogging && z) {
                    Log.i("SettingsLib.AccessPoint", String.format("%s: Set speed to %d", this.ssid, Integer.valueOf(i)));
                }
                return z;
            }
        }
        i = 0;
        this.mSpeed = i;
        if (i2 != i) {
        }
        if (WifiTracker.sVerboseLogging) {
            Log.i("SettingsLib.AccessPoint", String.format("%s: Set speed to %d", this.ssid, Integer.valueOf(i)));
        }
        return z;
    }

    @Override // java.lang.Comparable
    public final int compareTo(AccessPoint accessPoint) {
        if (isActive() && !accessPoint.isActive()) {
            return -1;
        }
        if (!isActive() && accessPoint.isActive()) {
            return 1;
        }
        int i = this.mRssi;
        if (i != Integer.MIN_VALUE) {
            if (!(accessPoint.mRssi != Integer.MIN_VALUE)) {
                return -1;
            }
        }
        if (!(i != Integer.MIN_VALUE)) {
            if (accessPoint.mRssi != Integer.MIN_VALUE) {
                return 1;
            }
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            if (!(accessPoint.mConfig != null)) {
                return -1;
            }
        }
        if (!(wifiConfiguration != null)) {
            if (accessPoint.mConfig != null) {
                return 1;
            }
        }
        int i2 = this.mSpeed;
        int i3 = accessPoint.mSpeed;
        if (i2 != i3) {
            return i3 - i2;
        }
        WifiManager wifiManager = getWifiManager();
        int calculateSignalLevel = wifiManager.calculateSignalLevel(accessPoint.mRssi) - wifiManager.calculateSignalLevel(this.mRssi);
        if (calculateSignalLevel != 0) {
            return calculateSignalLevel;
        }
        int compareToIgnoreCase = getTitle().compareToIgnoreCase(accessPoint.getTitle());
        return compareToIgnoreCase != 0 ? compareToIgnoreCase : this.ssid.compareTo(accessPoint.ssid);
    }

    public boolean matches(ScanResult scanResult) {
        String str;
        if (scanResult == null) {
            return false;
        }
        if (!isPasspoint()) {
            if (!(this.mOsuProvider != null)) {
                if (!(TextUtils.equals(this.ssid, scanResult.SSID) || ((str = scanResult.BSSID) != null && TextUtils.equals(this.bssid, str)))) {
                    return false;
                }
                if (this.mIsPskSaeTransitionMode) {
                    if ((scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE) && getWifiManager().isWpa3SaeSupported()) || scanResult.capabilities.contains("PSK")) {
                        return true;
                    }
                } else {
                    int i = this.security;
                    if (i == 5 || i == 2) {
                        if (scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE)) {
                            return true;
                        }
                    }
                }
                if (this.mIsOweTransitionMode) {
                    int security = getSecurity(this.mContext, scanResult);
                    if ((security == 4 && getWifiManager().isEnhancedOpenSupported()) || security == 0) {
                        return true;
                    }
                } else {
                    int i2 = this.security;
                    if ((i2 == 4 || i2 == 0) && scanResult.capabilities.contains("OWE_TRANSITION")) {
                        return true;
                    }
                }
                return this.security == getSecurity(this.mContext, scanResult);
            }
        }
        throw new IllegalStateException("Should not matches a Passpoint by ScanResult");
    }

    public static int getSecurity(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return 5;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(10)) {
            return 6;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return 4;
        }
        int i = wifiConfiguration.wepTxKeyIndex;
        if (i >= 0) {
            String[] strArr = wifiConfiguration.wepKeys;
            if (i < strArr.length && strArr[i] != null) {
                return 1;
            }
        }
        return 0;
    }

    public static String getKey(OsuProvider osuProvider) {
        return "OSU:" + osuProvider.getFriendlyName() + ',' + osuProvider.getServerUri();
    }

    public static String getKey(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder("AP:");
        if (TextUtils.isEmpty(str)) {
            sb.append(str2);
        } else {
            sb.append(str);
        }
        sb.append(',');
        sb.append(i);
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        if (r2 == r6.getNetworkId()) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005f, code lost:
    
        if ((android.text.TextUtils.equals(r4.ssid, removeDoubleQuotes(r6.getSSID())) || (r6.getBSSID() != null && android.text.TextUtils.equals(r4.bssid, r6.getBSSID()))) == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x009c, code lost:
    
        if (android.text.TextUtils.equals(r6.getPasspointProviderFriendlyName(), r4.mConfig.providerFriendlyName) != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00a7, code lost:
    
        if (r4.mOsuStatus != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo, NetworkInfo networkInfo) {
        boolean z;
        getLevel();
        boolean z2 = true;
        if (wifiInfo != null) {
            if (!wifiInfo.isOsuAp() && this.mOsuStatus == null) {
                if (!wifiInfo.isPasspointAp() && !isPasspoint()) {
                    int i = this.networkId;
                    if (i == -1) {
                        if (wifiConfiguration != null) {
                            if (!wifiConfiguration.isPasspoint()) {
                            }
                            z = matches(wifiConfiguration);
                        } else {
                            z = TextUtils.equals(removeDoubleQuotes(wifiInfo.getSSID()), this.ssid);
                        }
                    }
                    z = false;
                } else {
                    if (wifiInfo.isPasspointAp()) {
                        if (isPasspoint()) {
                            if (TextUtils.equals(wifiInfo.getPasspointFqdn(), this.mConfig.FQDN)) {
                            }
                        }
                    }
                    z = false;
                }
            } else {
                if (wifiInfo.isOsuAp()) {
                }
                z = false;
            }
            if (z) {
                boolean z3 = this.mInfo == null;
                if (!isPasspoint() && this.mConfig != wifiConfiguration) {
                    update(wifiConfiguration);
                }
                if (this.mRssi != wifiInfo.getRssi() && wifiInfo.getRssi() != -127) {
                    this.mRssi = wifiInfo.getRssi();
                } else {
                    NetworkInfo networkInfo2 = this.mNetworkInfo;
                    if (networkInfo2 == null || networkInfo == null || networkInfo2.getDetailedState() == networkInfo.getDetailedState()) {
                        z2 = z3;
                    }
                }
                this.mInfo = wifiInfo;
                this.mNetworkInfo = networkInfo;
                return z2;
            }
        }
        if (this.mInfo == null) {
            return false;
        }
        this.mInfo = null;
        this.mNetworkInfo = null;
        return true;
    }

    public final void update(WifiConfiguration wifiConfiguration) {
        this.mConfig = wifiConfiguration;
        if (wifiConfiguration != null && !isPasspoint()) {
            this.ssid = removeDoubleQuotes(this.mConfig.SSID);
        }
        this.networkId = wifiConfiguration != null ? wifiConfiguration.networkId : -1;
        ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 0));
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        loadConfig(wifiConfiguration);
        updateKey();
    }

    public AccessPoint(Context context, PasspointConfiguration passpointConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.mPasspointUniqueId = passpointConfiguration.getUniqueId();
        passpointConfiguration.getHomeSp().getFqdn();
        this.mProviderFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
        if (passpointConfiguration.isOsuProvisioned()) {
            this.mPasspointConfigurationVersion = 2;
        } else {
            this.mPasspointConfigurationVersion = 1;
        }
        updateKey();
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration, Collection<ScanResult> collection, Collection<ScanResult> collection2) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
        this.mPasspointUniqueId = wifiConfiguration.getKey();
        setScanResultsPasspoint(collection, collection2);
        updateKey();
    }

    public AccessPoint(Context context, OsuProvider osuProvider, Collection<ScanResult> collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.mOsuProvider = osuProvider;
        setScanResults(collection);
        updateKey();
    }

    public AccessPoint(Context context, Collection<ScanResult> collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet();
        this.mExtraScanResults = new ArraySet();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        setScanResults(collection);
        updateKey();
    }
}
