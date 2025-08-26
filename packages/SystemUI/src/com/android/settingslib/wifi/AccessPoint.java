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
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.util.CollectionUtils;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class AccessPoint implements Comparable {
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

    class AccessPointProvisioningCallback extends ProvisioningCallback {
        public AccessPointProvisioningCallback() {
        }

        public final void onProvisioningComplete() {
            AccessPoint accessPoint = AccessPoint.this;
            accessPoint.mOsuProvisioningComplete = true;
            accessPoint.mOsuFailure = null;
            accessPoint.mOsuStatus = null;
            ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 1));
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
            ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 3));
        }

        public final void onProvisioningStatus(int i) {
            String string;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    string = String.format(AccessPoint.this.mContext.getString(R.string.osu_opening_provider), AccessPoint.this.mOsuProvider.getFriendlyName());
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    string = AccessPoint.this.mContext.getString(R.string.osu_completing_sign_up);
                    break;
                default:
                    string = null;
                    break;
            }
            boolean zEquals = TextUtils.equals(AccessPoint.this.mOsuStatus, string);
            AccessPoint accessPoint = AccessPoint.this;
            accessPoint.mOsuStatus = string;
            accessPoint.mOsuFailure = null;
            accessPoint.mOsuProvisioningComplete = false;
            if (zEquals) {
                return;
            }
            ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 2));
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
        int i = 0;
        this.pskType = 0;
        this.mRssi = Integer.MIN_VALUE;
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
            ArrayList parcelableArrayList = bundle.getParcelableArrayList("key_scorednetworkcache");
            int size = parcelableArrayList.size();
            while (i < size) {
                Object obj = parcelableArrayList.get(i);
                i++;
                TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) obj;
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

    public static int getSecurity(Context context, ScanResult scanResult) {
        boolean zContains = scanResult.capabilities.contains("WEP");
        boolean zContains2 = scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE);
        boolean zContains3 = scanResult.capabilities.contains("PSK");
        boolean zContains4 = scanResult.capabilities.contains("EAP_SUITE_B_192");
        boolean zContains5 = scanResult.capabilities.contains("EAP");
        boolean zContains6 = scanResult.capabilities.contains("OWE");
        boolean zContains7 = scanResult.capabilities.contains("OWE_TRANSITION");
        if (zContains2 && zContains3) {
            return ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).isWpa3SaeSupported() ? 5 : 2;
        }
        if (zContains7) {
            return ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI)).isEnhancedOpenSupported() ? 4 : 0;
        }
        if (zContains) {
            return 1;
        }
        if (zContains2) {
            return 5;
        }
        if (zContains3) {
            return 2;
        }
        if (zContains4) {
            return 6;
        }
        if (zContains5) {
            return 3;
        }
        return zContains6 ? 4 : 0;
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

    public final int getLevel() {
        return getWifiManager().calculateSignalLevel(this.mRssi);
    }

    public final String getTitle() {
        if (isPasspoint() && !TextUtils.isEmpty(this.mConfig.providerFriendlyName)) {
            return this.mConfig.providerFriendlyName;
        }
        if (this.mPasspointUniqueId != null && this.mConfig == null && !TextUtils.isEmpty(this.mProviderFriendlyName)) {
            return this.mProviderFriendlyName;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        return (osuProvider == null || TextUtils.isEmpty(osuProvider.getFriendlyName())) ? !TextUtils.isEmpty(this.ssid) ? this.ssid : "" : this.mOsuProvider.getFriendlyName();
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI);
        }
        return this.mWifiManager;
    }

    public final int hashCode() {
        WifiInfo wifiInfo = this.mInfo;
        return (this.ssid.hashCode() * 29) + (this.networkId * 23) + (this.mRssi * 19) + (wifiInfo != null ? wifiInfo.hashCode() * 13 : 0);
    }

    public final boolean isActive() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo != null) {
            return (this.networkId == -1 && networkInfo.getState() == NetworkInfo.State.DISCONNECTED) ? false : true;
        }
        return false;
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
        WifiConfiguration wifiConfiguration2;
        if (wifiConfiguration.isPasspoint()) {
            return isPasspoint() && wifiConfiguration.getKey().equals(this.mConfig.getKey());
        }
        if (this.ssid.equals(removeDoubleQuotes(wifiConfiguration.SSID)) && ((wifiConfiguration2 = this.mConfig) == null || wifiConfiguration2.shared == wifiConfiguration.shared)) {
            int security = getSecurity(wifiConfiguration);
            if (this.mIsPskSaeTransitionMode && ((security == 5 && getWifiManager().isWpa3SaeSupported()) || security == 2)) {
                return true;
            }
            if ((this.mIsOweTransitionMode && ((security == 4 && getWifiManager().isEnhancedOpenSupported()) || security == 0)) || this.security == getSecurity(wifiConfiguration)) {
                return true;
            }
        }
        return false;
    }

    public void setRssi(int i) {
        this.mRssi = i;
    }

    public final void setScanResults(Collection collection) {
        if (CollectionUtils.isEmpty(collection)) {
            Log.d("SettingsLib.AccessPoint", "Cannot set scan results to empty list");
            return;
        }
        if (this.mKey != null && !isPasspoint() && this.mOsuProvider == null) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                ScanResult scanResult = (ScanResult) it.next();
                if (!matches(scanResult)) {
                    Context context = this.mContext;
                    String key = getKey(getSecurity(context, scanResult), scanResult.SSID, scanResult.BSSID);
                    String str = this.mKey;
                    StringBuilder sb = new StringBuilder("ScanResult ");
                    sb.append(scanResult);
                    sb.append("\nkey of ");
                    sb.append(key);
                    sb.append(" did not match current AP key ");
                    ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "SettingsLib.AccessPoint");
                    return;
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
            ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 0));
        }
        ThreadUtils.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 0));
    }

    public final void setScanResultsPasspoint(Collection collection, Collection collection2) {
        synchronized (this.mLock) {
            try {
                this.mExtraScanResults.clear();
                if (!CollectionUtils.isEmpty(collection)) {
                    if (!CollectionUtils.isEmpty(collection2)) {
                        this.mExtraScanResults.addAll(collection2);
                    }
                    setScanResults(collection);
                } else if (!CollectionUtils.isEmpty(collection2)) {
                    setScanResults(collection2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String toString() {
        NetworkInfo.DetailedState detailedState;
        NetworkInfo networkInfo;
        StringBuilder sb = new StringBuilder("AccessPoint(");
        sb.append(this.ssid);
        if (this.mConfig != null) {
            sb.append(",saved");
        }
        if (isActive()) {
            sb.append(",active");
        }
        WifiInfo wifiInfo = this.mInfo;
        if (wifiInfo != null && wifiInfo.isEphemeral() && (networkInfo = this.mNetworkInfo) != null && networkInfo.getState() != NetworkInfo.State.DISCONNECTED) {
            sb.append(",ephemeral");
        }
        if (getLevel() != -1) {
            NetworkInfo networkInfo2 = this.mNetworkInfo;
            if (networkInfo2 != null) {
                detailedState = networkInfo2.getDetailedState();
            } else {
                Log.w("SettingsLib.AccessPoint", "NetworkInfo is null, cannot return detailed state");
                detailedState = null;
            }
            if (detailedState == null) {
                sb.append(",connectable");
            }
        }
        int i = this.security;
        boolean z = true;
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
        boolean zUpdateSpeed;
        WifiInfo wifiInfo;
        if (z) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (this.mLock) {
                try {
                    Iterator it = this.mScanResults.iterator();
                    while (it.hasNext()) {
                        ScanResult scanResult = (ScanResult) it.next();
                        ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(scanResult);
                        if (scoredNetwork != null) {
                            TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) ((HashMap) this.mScoredNetworkCache).get(scanResult.BSSID);
                            if (timestampedScoredNetwork == null) {
                                ((HashMap) this.mScoredNetworkCache).put(scanResult.BSSID, new TimestampedScoredNetwork(scoredNetwork, jElapsedRealtime));
                            } else {
                                timestampedScoredNetwork.mScore = scoredNetwork;
                                timestampedScoredNetwork.mUpdatedTimestampMillis = jElapsedRealtime;
                            }
                        }
                    }
                } finally {
                }
            }
            final long j2 = jElapsedRealtime - j;
            final Iterator it2 = ((HashMap) this.mScoredNetworkCache).values().iterator();
            it2.forEachRemaining(new Consumer() { // from class: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    long j3 = j2;
                    Iterator it3 = it2;
                    int i = AccessPoint.$r8$clinit;
                    if (((TimestampedScoredNetwork) obj).mUpdatedTimestampMillis < j3) {
                        it3.remove();
                    }
                }
            });
            zUpdateSpeed = updateSpeed();
        } else {
            zUpdateSpeed = false;
        }
        boolean z2 = this.mIsScoredNetworkMetered;
        this.mIsScoredNetworkMetered = false;
        if (!isActive() || (wifiInfo = this.mInfo) == null) {
            synchronized (this.mLock) {
                try {
                    Iterator it3 = this.mScanResults.iterator();
                    while (it3.hasNext()) {
                        ScoredNetwork scoredNetwork2 = wifiNetworkScoreCache.getScoredNetwork((ScanResult) it3.next());
                        if (scoredNetwork2 != null) {
                            this.mIsScoredNetworkMetered = scoredNetwork2.meteredHint | this.mIsScoredNetworkMetered;
                        }
                    }
                } finally {
                }
            }
        } else {
            ScoredNetwork scoredNetwork3 = wifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(wifiInfo));
            if (scoredNetwork3 != null) {
                this.mIsScoredNetworkMetered = scoredNetwork3.meteredHint | this.mIsScoredNetworkMetered;
            }
        }
        return z2 != this.mIsScoredNetworkMetered || zUpdateSpeed;
    }

    public final void updateBestRssiInfo() {
        ScanResult scanResult;
        int i;
        int i2;
        if (isActive()) {
            return;
        }
        synchronized (this.mLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
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
            boolean z = false;
            if (security == 2 || security == 5) {
                boolean zContains = scanResult.capabilities.contains("WPA-PSK");
                boolean zContains2 = scanResult.capabilities.contains("RSN-PSK");
                boolean zContains3 = scanResult.capabilities.contains("RSN-SAE");
                if (zContains2 && zContains) {
                    i4 = 3;
                } else if (!zContains2) {
                    if (zContains) {
                        i4 = 1;
                    } else {
                        if (!zContains3) {
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
            if (scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE)) {
                z = true;
            }
            this.mIsPskSaeTransitionMode = z;
            this.mIsOweTransitionMode = scanResult.capabilities.contains("OWE_TRANSITION");
        }
        if (isPasspoint()) {
            this.mConfig.SSID = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\"", this.ssid, "\"");
        }
    }

    public final void updateKey() {
        String key;
        if (isPasspoint()) {
            WifiConfiguration wifiConfiguration = this.mConfig;
            if (wifiConfiguration.isPasspoint()) {
                key = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("PASSPOINT:", wifiConfiguration.getKey());
            } else {
                key = getKey(getSecurity(wifiConfiguration), removeDoubleQuotes(wifiConfiguration.SSID), wifiConfiguration.BSSID);
            }
            this.mKey = key;
            return;
        }
        String str = this.mPasspointUniqueId;
        if (str != null && this.mConfig == null) {
            this.mKey = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("PASSPOINT:", str);
            return;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        if (osuProvider == null) {
            this.mKey = getKey(this.security, this.ssid, this.bssid);
        } else {
            this.mKey = "OSU:" + osuProvider.getFriendlyName() + ',' + osuProvider.getServerUri();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000f  */
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
                int iCalculateBadge = ((TimestampedScoredNetwork) it.next()).mScore.calculateBadge(this.mRssi);
                if (iCalculateBadge != 0) {
                    i3++;
                    i4 += iCalculateBadge;
                }
            }
            int i5 = i3 == 0 ? 0 : i4 / i3;
            if (WifiTracker.sVerboseLogging) {
                Log.i("SettingsLib.AccessPoint", String.format("%s generated fallback speed is: %d", this.ssid, Integer.valueOf(i5)));
            }
            i = 5;
            if (i5 < 5) {
                i = 0;
            } else if (i5 >= 7) {
                i = i5 < 15 ? 10 : i5 < 25 ? 20 : 30;
            }
        }
        this.mSpeed = i;
        boolean z = i2 != i;
        if (WifiTracker.sVerboseLogging && z) {
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
        if (i != Integer.MIN_VALUE && accessPoint.mRssi == Integer.MIN_VALUE) {
            return -1;
        }
        if (i == Integer.MIN_VALUE && accessPoint.mRssi != Integer.MIN_VALUE) {
            return 1;
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null && accessPoint.mConfig == null) {
            return -1;
        }
        if (wifiConfiguration == null && accessPoint.mConfig != null) {
            return 1;
        }
        int i2 = this.mSpeed;
        int i3 = accessPoint.mSpeed;
        if (i2 != i3) {
            return i3 - i2;
        }
        WifiManager wifiManager = getWifiManager();
        int iCalculateSignalLevel = wifiManager.calculateSignalLevel(accessPoint.mRssi) - wifiManager.calculateSignalLevel(this.mRssi);
        if (iCalculateSignalLevel != 0) {
            return iCalculateSignalLevel;
        }
        int iCompareToIgnoreCase = getTitle().compareToIgnoreCase(accessPoint.getTitle());
        return iCompareToIgnoreCase != 0 ? iCompareToIgnoreCase : this.ssid.compareTo(accessPoint.ssid);
    }

    public boolean matches(ScanResult scanResult) {
        int i;
        int security;
        String str;
        if (scanResult == null) {
            return false;
        }
        if (!isPasspoint() && this.mOsuProvider == null) {
            if (!TextUtils.equals(this.ssid, scanResult.SSID) && ((str = scanResult.BSSID) == null || !TextUtils.equals(this.bssid, str))) {
                return false;
            }
            if (this.mIsPskSaeTransitionMode) {
                if ((!scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE) || !getWifiManager().isWpa3SaeSupported()) && !scanResult.capabilities.contains("PSK")) {
                }
                return true;
            }
            int i2 = this.security;
            if ((i2 == 5 || i2 == 2) && scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains(WifiPolicy.SECURITY_TYPE_SAE)) {
                return true;
            }
            if (!this.mIsOweTransitionMode ? !(((i = this.security) == 4 || i == 0) && scanResult.capabilities.contains("OWE_TRANSITION")) : !(((security = getSecurity(this.mContext, scanResult)) == 4 && getWifiManager().isEnhancedOpenSupported()) || security == 0)) {
                if (this.security != getSecurity(this.mContext, scanResult)) {
                    return false;
                }
            }
            return true;
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
        if (i < 0) {
            return 0;
        }
        String[] strArr = wifiConfiguration.wepKeys;
        return (i >= strArr.length || strArr[i] == null) ? 0 : 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo, NetworkInfo networkInfo) {
        boolean zEquals;
        getLevel();
        boolean z = true;
        if (wifiInfo != null) {
            if (!wifiInfo.isOsuAp() && this.mOsuStatus == null) {
                if (!wifiInfo.isPasspointAp() && !isPasspoint()) {
                    int i = this.networkId;
                    if (i != -1) {
                        if (i == wifiInfo.getNetworkId()) {
                        }
                    } else if (wifiConfiguration != null) {
                        if (wifiConfiguration.isPasspoint() || TextUtils.equals(this.ssid, removeDoubleQuotes(wifiInfo.getSSID())) || (wifiInfo.getBSSID() != null && TextUtils.equals(this.bssid, wifiInfo.getBSSID()))) {
                            zEquals = matches(wifiConfiguration);
                        }
                    } else {
                        zEquals = TextUtils.equals(removeDoubleQuotes(wifiInfo.getSSID()), this.ssid);
                    }
                } else if (!wifiInfo.isPasspointAp() || !isPasspoint() || !TextUtils.equals(wifiInfo.getPasspointFqdn(), this.mConfig.FQDN) || !TextUtils.equals(wifiInfo.getPasspointProviderFriendlyName(), this.mConfig.providerFriendlyName)) {
                }
            } else {
                zEquals = wifiInfo.isOsuAp() && this.mOsuStatus != null;
            }
            if (zEquals) {
                boolean z2 = this.mInfo == null;
                if (!isPasspoint() && this.mConfig != wifiConfiguration) {
                    update(wifiConfiguration);
                }
                if (this.mRssi != wifiInfo.getRssi() && wifiInfo.getRssi() != -127) {
                    this.mRssi = wifiInfo.getRssi();
                } else {
                    NetworkInfo networkInfo2 = this.mNetworkInfo;
                    if (networkInfo2 == null || networkInfo == null || networkInfo2.getDetailedState() == networkInfo.getDetailedState()) {
                        z = z2;
                    }
                }
                this.mInfo = wifiInfo;
                this.mNetworkInfo = networkInfo;
                return z;
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
        this.mRssi = Integer.MIN_VALUE;
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
        this.mRssi = Integer.MIN_VALUE;
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
        this.mRssi = Integer.MIN_VALUE;
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
        this.mRssi = Integer.MIN_VALUE;
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
        this.mRssi = Integer.MIN_VALUE;
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
